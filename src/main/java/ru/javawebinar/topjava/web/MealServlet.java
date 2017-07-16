package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.storage.MemoryStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Oleh Savych
 * 15.07.17
 */
public class MealServlet extends HttpServlet {
    //private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                          IOException {
        request.setCharacterEncoding("UTF-8");

        request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(MemoryStorage.get().getList(),
                Integer.parseInt(request.getParameter("caloriesLimit"))));

        request.getRequestDispatcher("/topjava/meals.jsp").forward(request, response);

        //log.debug("redirect to meals");
    }
}
