package com.narola.finalproject.servlet;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class ResetPasswordServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String token = request.getParameter("id");
            if (token == null || token.isEmpty()) {
                request.setAttribute("error", "Something went wrong. Please try again.");
                getServletContext().getRequestDispatcher("/forgotPassword.jsp").forward(request, response);
            } else {
                String email = userService.getUserEmail(token);
                if (email == null || email.isEmpty()) {
                    request.setAttribute("error", "Invalid link.");
                    getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
                }
                userService.removePasswordToken(email);
                request.setAttribute("email", email);
                getServletContext().getRequestDispatcher("/resetPassword.jsp").forward(request, response);
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");

            if (email == null || password == null || confirmPassword == null || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                request.setAttribute("error", "Something went wrong. Please try again.");
                getServletContext().getRequestDispatcher("/forgotPassword.jsp").forward(request, response);
            } else {
                if (password.equals(confirmPassword)) {
                    userService.updatePassword(email, password);
                    request.setAttribute("message", "Password updated successfully.");
                    getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "Add valid password.");
                    request.setAttribute("email", email);
                    getServletContext().getRequestDispatcher("/resetPassword.jsp").forward(request, response);
                }
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }
}
