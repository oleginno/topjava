package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;


public class MealServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private ConfigurableApplicationContext appCtx;
    private MealRestController mealRestController;

    @Override
    public void init() throws ServletException {
        super.init();
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = appCtx.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        List<MealWithExceed> meals;

        int userId = 0;
        if (request.getAttribute("userId") != null) {
            userId = (Integer) request.getAttribute("userId");
        }

        String action = request.getParameter("action");
        if (action != null) {
            Integer userIdParam = Integer.valueOf(request.getParameter("userId"));
            request.setAttribute("userId", userIdParam);

            switch (action) {
                case "delete":
                    mealRestController.remove(userIdParam, Objects.requireNonNull(getId(request)));
                    log.info("Delete button pressed for meal ID: {}", getId(request));
                    break;
                case "create":
                    mealRestController.create(createMeal(userIdParam, request));
                    log.info("Added new meal");
                    break;
                case "edit":
                    mealRestController.update(userIdParam, Objects.requireNonNull(getId(request)),
                            createMeal(userIdParam, request));
                    log.info("Changed meal with ID:{}, for User with ID:{}", getId(request), userIdParam);
                    break;
                case "filter":
                    String fromDate = request.getParameter("fromDate");
                    String toDate = request.getParameter("toDate");
                    String fromTime = request.getParameter("fromTime");
                    String toTime = request.getParameter("toTime");

                    if (!fromDate.trim().isEmpty() && !toDate.trim().isEmpty()
                            && !fromTime.trim().isEmpty() && !toTime.trim().isEmpty()) {

                        LocalDateTime start = LocalDateTime.of(
                                LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                LocalTime.parse(fromTime, DateTimeFormatter.ofPattern("HH:mm")));
                        LocalDateTime end = LocalDateTime.of(
                                LocalDate.parse(toDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                LocalTime.parse(toTime, DateTimeFormatter.ofPattern("HH:mm")));

                        if (end.isAfter(start)) {
                            meals = MealsUtil.getFilteredWithExceeded(mealRestController.getAllByUser(userIdParam),
                                    start, end, MealsUtil.DEFAULT_CALORIES_PER_DAY);
                            sortAndSetMeals(meals, request);
                        } else {
                            throw new IllegalArgumentException("Error of date/time precedence");
                        }
                    } else if (!fromDate.trim().isEmpty() && !toDate.trim().isEmpty()
                            && fromTime.trim().isEmpty() && toTime.trim().isEmpty()) {

                        LocalDate start = LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        LocalDate end = LocalDate.parse(toDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                        if (end.isAfter(start)) {
                            meals = MealsUtil.getFilteredWithExceeded(mealRestController.getAllByUser(userIdParam),
                                    start, end, MealsUtil.DEFAULT_CALORIES_PER_DAY);
                            sortAndSetMeals(meals, request);
                        } else {
                            throw new IllegalArgumentException("Error of date precedence");
                        }
                    } else if (fromDate.trim().isEmpty() && toDate.trim().isEmpty()
                            && !fromTime.trim().isEmpty() && !toTime.trim().isEmpty()) {

                        LocalTime start = LocalTime.parse(fromTime, DateTimeFormatter.ofPattern("HH:mm"));
                        LocalTime end = LocalTime.parse(toTime, DateTimeFormatter.ofPattern("HH:mm"));

                        if (end.isAfter(start)) {
                            meals = MealsUtil.getFilteredWithExceeded(mealRestController.getAllByUser(userIdParam),
                                    start, end, MealsUtil.DEFAULT_CALORIES_PER_DAY);
                            sortAndSetMeals(meals, request);
                        } else {
                            throw new IllegalArgumentException("Error of date precedence");
                        }
                    } else {
                        throw new IllegalArgumentException("Problem/problems with dates and/or times!");
                    }
                    log.info("Filtering by date and time");
                    break;
                default:
                    log.info("Something wrong with ACTION parameter!");
                    throw new IllegalArgumentException("Action " + action + " is illegal!");
            }
            if (!"filter".equals(action)) {
                meals = updateMealList(userIdParam);
                request.setAttribute("meals", meals);
            }
        } else {
            meals = updateMealList(userId);

            request.setAttribute("userId", userId);
            request.setAttribute("meals", meals);
        }

        request.getRequestDispatcher("meals.jsp").forward(request, response);
        log.debug("redirect to meals");
    }

    @Override
    public void destroy() {
        super.destroy();
        appCtx.close();
    }


    private Meal createMeal(int userId, HttpServletRequest request) {
        return new Meal(
                getId(request), userId,
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
    }

    private Integer getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return paramId.length() < 1 ? null : Integer.valueOf(paramId);
    }

    private List<MealWithExceed> updateMealList(int userId) {
        List<MealWithExceed> meals = MealsUtil.getWithExceeded(mealRestController.getAllByUser(userId),
                MealsUtil.DEFAULT_CALORIES_PER_DAY);
        meals.sort((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));
        return meals;
    }

    private void sortAndSetMeals(List<MealWithExceed> meals, HttpServletRequest request) {
        meals.sort((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));
        request.setAttribute("meals", meals);
    }
}