package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.storage.MemoryStorage;
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                                                                         throws ServletException, IOException {

        String id = request.getParameter("id");
        String action = request.getParameter("action");

        switch (action) {
            case "delete":
                MemoryStorage.get().delete(id);
                //response.sendRedirect("/topjava/meals.jsp");
                break;
//            case "add":
//            case "edit":
//                r = Resume.EMPTY;
//                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }

        updateMealList(request);
        //request.getRequestDispatcher("meals.jsp").forward(request, response);
        response.sendRedirect("/topjava/meals.jsp");

        //request.setAttribute("resume", r);

//        request.getRequestDispatcher(("view2".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/editMeal.jsp"))
//                .forward(request, response);
//
//        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                                                                          throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        caloriesLimit = Integer.parseInt(request.getParameter("caloriesLimit"));

        updateMealList(request);

        request.getRequestDispatcher("/topjava/meals.jsp").forward(request, response);

        //log.debug("redirect to meals");
    }


    private void updateMealList(HttpServletRequest request) {
        meals = MealsUtil.getFilteredWithExceeded(MemoryStorage.get().getList(), caloriesLimit);
        meals.sort((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));
        //request.setAttribute("meals", meals);
        request.getSession(true).setAttribute("meals", meals);
    }
}
