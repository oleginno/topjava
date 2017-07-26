package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


public class MealServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

//    private MealRestController mealRestController;
//
//    @Override
//    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
//        try (ConfigurableApplicationContext appCtx =
//                     new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
//            mealRestController = appCtx.getBean(MealRestController.class);
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//                                                        throws ServletException, IOException {
//
//        request.setCharacterEncoding("UTF-8");
//
//        String id = request.getParameter("id");
//        Integer userId = (Integer) request.getAttribute("userId");
//
//        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
//                userId,
//                LocalDateTime.parse(request.getParameter("dateTime")),
//                request.getParameter("description"),
//                Integer.valueOf(request.getParameter("calories")));
//
//        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
//
//        mealRestController.create(meal);
//
//        response.sendRedirect("meals");
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//                                                        throws ServletException, IOException {
//
//        String action = request.getParameter("action");
//
//        Integer userId = (Integer) request.getAttribute("userId");
//
//        switch (action == null ? "all" : action) {
//            case "delete":
//                int id = getId(request);
//                log.info("Delete {}", id);
//                mealRestController.remove((Integer) (request.getAttribute("userId")), id);
//                response.sendRedirect("meals");
//                break;
//            case "create":
//            case "update":
//                final Meal meal = "create".equals(action) ?
//                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 333) :
//                        mealRestController.get(userId, getId(request));
//                request.setAttribute("meal", meal);
//                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
//                break;
//            case "all":
//            default:
//                log.info("getAll");
//
//                System.out.println(">" + mealRestController.getAllByUser(userId).size() + "<");
//
//                request.setAttribute("meals",
//                        MealsUtil.getFilteredWithExceeded(mealRestController.getAllByUser(userId),
//                                MealsUtil.DEFAULT_CALORIES_PER_DAY));
//                request.getRequestDispatcher("/meals.jsp").forward(request, response);
//                break;
//        }
//    }
//
//    private int getId(HttpServletRequest request) {
//        String paramId = Objects.requireNonNull(request.getParameter("id"));
//        return Integer.valueOf(paramId);
//    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                                                            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String userId = (String) request.getAttribute("userId");

        String id = request.getParameter("id");
        String action = request.getParameter("action");

        try (ConfigurableApplicationContext appCtx =
                     new ClassPathXmlApplicationContext("spring/spring-app.xml")) {

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);

            if (action != null) {
                switch (action) {
                    case "delete":
                        InMemoryMealRepositoryImpl.get().delete(id);
                        break;
                    case "add":
                        InMemoryMealRepositoryImpl.get().save(new Meal(
                                UUID.randomUUID().toString(),
                                DateTimeUtil.getLocalDateTime(request.getParameter("dateTime"),
                                        DateTimeUtil.FORMATTER_HTML_5),
                                request.getParameter("description"),
                                Integer.parseInt(request.getParameter("calories"))));
                        break;
                    case "edit":
                        InMemoryMealRepositoryImpl.get().update(id, new Meal(id,
                                DateTimeUtil.getLocalDateTime(request.getParameter("dateTime"), DateTimeUtil.FORMATTER),
                                request.getParameter("description"),
                                Integer.parseInt(request.getParameter("calories"))));
                        break;
                    default:
                        throw new IllegalArgumentException("Action " + action + " is illegal");
                }
            }
            List<MealWithExceed> meals = MealsUtil.getFilteredWithExceeded(InMemoryMealRepositoryImpl.get().getList(), caloriesLimit);
            meals.sort((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));

            request.setAttribute("meals", meals);
            request.getRequestDispatcher("Meals.jsp").forward(request, response);
            log.debug("redirect to meals");
        }
    }

}
