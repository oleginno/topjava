package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.storage.MemoryDao;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Oleh Savych
 * 15.07.17
 */
public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);

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

        if (action != null) {
            switch (action) {

                case "delete":
                    MemoryDao.get().delete(id);
                    System.out.println("DEL");
                    break;

                case "add":
                    MemoryDao.get().save(new Meal(
                            UUID.randomUUID().toString(),
                            DateTimeUtil.getLocalDateTime(request.getParameter("dateTime"),
                                    DateTimeUtil.formatterHtml5),
                            request.getParameter("description"),
                            Integer.parseInt(request.getParameter("calories"))));
                    break;

                case "edit":
                    MemoryDao.get().update(id, new Meal(id,
                            DateTimeUtil.getLocalDateTime(request.getParameter("dateTime"), DateTimeUtil.formatter),
                            request.getParameter("description"),
                            Integer.parseInt(request.getParameter("calories"))));
                    break;

                default:
                    throw new IllegalArgumentException("Action " + action + " is illegal");
            }
        }

        List<MealWithExceed> meals = MealsUtil.getFilteredWithExceeded(MemoryDao.get().getList(), caloriesLimit);
        meals.sort((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));

        request.setAttribute("meals", meals);
        request.getRequestDispatcher("meals.jsp").forward(request, response);

        log.debug("redirect to meals");
    }
}
