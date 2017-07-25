package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Oleh Savych
 * 25.07.17
 */
public class AuthorizationServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                                                        throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        try (ConfigurableApplicationContext appCtx =
                     new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email",
                    "password", Role.ROLE_ADMIN));
        }

        request.setAttribute("meals", meals);
        request.getRequestDispatcher("Meals.jsp").forward(request, response);
        log.debug("redirect to meals");
    }
}
