package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;
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

        String login = request.getParameter("login").trim();
        String password = request.getParameter("password").trim();

        if (login.length() < 1 || password.length() < 1) {
            throw new NotFoundException("Name or/and password field is empty");
        }

        try (ConfigurableApplicationContext appCtx =
                     new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);

            User authorizedUser = adminUserController.getAll().stream()
                    .filter(user -> login.equals(user.getName()) && password.equals(user.getPassword()))
                    .findFirst()
                    .orElse(null);

            if (authorizedUser == null) {
                throw new NotFoundException("Name or/and password is incorrect!");
            }

            request.setAttribute("userId", authorizedUser.getId());
            request.getRequestDispatcher("meals").forward(request, response);

            log.debug("redirect to meals");
        }
    }
}