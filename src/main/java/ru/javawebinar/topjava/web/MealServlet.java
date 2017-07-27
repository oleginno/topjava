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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MealServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private ConfigurableApplicationContext appCtx;
    private MealRestController mealRestController;

    private List<MealWithExceed> meals;

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

        int userId = 0;
        if (request.getAttribute("userId") != null) {
            userId = (Integer) request.getAttribute("userId");
        } else {
            meals = new ArrayList<>();
        }

        String action = request.getParameter("action");

        updateMealList(userId);

        if (action != null) {
            Integer userIdParam = Integer.valueOf(request.getParameter("userId"));
            //request.setAttribute("userId", userIdParam);

            switch (action) {
                case "delete":
                    mealRestController.remove(userIdParam, getId(request));
                    log.info("Delete button pressed for meal ID: {}", getId(request));
                    break;
                case "create":
                    mealRestController.create(createMeal(userIdParam, request));
                    log.info("Added new meal");
                    break;
                case "edit":
                    mealRestController.update(userIdParam, getId(request), createMeal(userIdParam, request));
                    log.info("Changed meal with ID:{}, for User with ID:{}", getId(request), userIdParam);
                    break;
                default:
                    log.info("Something wrong with ACTION parameter!");
                    throw new IllegalArgumentException("Action " + action + " is illegal!");
            }
            updateMealList(userIdParam);
        }

        request.setAttribute("meals", meals);
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
        if (paramId.length() < 1) {
            return null;
        } else {
            return Integer.valueOf(paramId);
        }
    }

    private void updateMealList(int uId) {
        meals = MealsUtil.getFilteredWithExceeded(mealRestController.getAllByUser(uId),
                MealsUtil.DEFAULT_CALORIES_PER_DAY);
        meals.sort((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));
    }
}