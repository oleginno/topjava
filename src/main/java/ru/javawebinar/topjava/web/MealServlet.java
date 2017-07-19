package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.storage.MemoryStorage;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Oleh Savych
 * 15.07.17
 */
public class MealServlet extends HttpServlet {
    //private static final Logger log = getLogger(MealServlet.class);

    private List<MealWithExceed> meals;

    private int caloriesLimit;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                                                                          throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        if (request.getParameter("caloriesLimit") != null) {
            caloriesLimit = Integer.parseInt(request.getParameter("caloriesLimit"));
        }

        String id = request.getParameter("id");

        String action = request.getParameter("action");

        if (id != null && action != null) {
            switch (action) {
                case "delete":
                    MemoryStorage.get().delete(id);
                    System.out.println("DEL");
                    break;
                case "add":
                    break;
                case "edit":
                    String dateTime = request.getParameter("dateTime");
                    String description = request.getParameter("description");
                    String calories = request.getParameter("calories");
                    Meal ttttt = new Meal(id, DateTimeUtil.getLocalDateTime(dateTime),
                            description, Integer.parseInt(calories));
                    System.out.println(ttttt);
                    MemoryStorage.get().update(id, new Meal(id, DateTimeUtil.getLocalDateTime(dateTime),
                            description, Integer.parseInt(calories)));
                    break;
                default:
                    throw new IllegalArgumentException("Action " + action + " is illegal");
            }
        }

        updateMealList(request);

        request.getRequestDispatcher("meals.jsp").forward(request, response);

        //request.getRequestDispatcher("/topjava/meals.jsp").forward(request, response);

        //log.debug("redirect to meals");
    }

    private void updateMealList(HttpServletRequest request) {
        meals = MealsUtil.getFilteredWithExceeded(MemoryStorage.get().getList(), caloriesLimit);
        meals.sort((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));
        request.setAttribute("meals", meals);
    }
}
