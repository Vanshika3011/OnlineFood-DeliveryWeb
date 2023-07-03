package com.narola.finalproject.servlet;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.Error;
import com.narola.finalproject.model.User;
import com.narola.finalproject.service.UserService;
import com.narola.finalproject.validation.UserValidation;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class LoginServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        try {
            User user = new User();
            String username = request.getParameter("username");
            user.setUsername(username);
            String password = request.getParameter("password");
            user.setPassword(password);

            List<Error> errorList = UserValidation.validationForLogin(user);

            if (!errorList.isEmpty()) {
                getErrors(errorList, request);
                getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                user = userService.doLogin(username, password);
                if (user == null) {
                    request.setAttribute("error", "Invalid Credentials!");
                    getServletContext().getRequestDispatcher("/login.jsp").include(request, response);
                } else {
                    HttpSession httpSession = request.getSession();
                    httpSession.setAttribute("user", user);
                    if (!user.isVerified()) {
                        response.sendRedirect("/verifyUser");
                    } else {
                        response.sendRedirect("/homePage");
                    }
                }
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }


    private void getErrors(List<Error> errorList, HttpServletRequest request) {
        for (Error error : errorList) {
            request.setAttribute(error.getFieldError(), error.getMessage());
        }
    }

}
