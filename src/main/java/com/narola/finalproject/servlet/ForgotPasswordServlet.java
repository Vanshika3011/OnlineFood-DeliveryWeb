package com.narola.finalproject.servlet;


import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.exception.MailException;
import com.narola.finalproject.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.UUID;

public class ForgotPasswordServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/forgotPassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter("email");
            if (email == null || email.isEmpty()) {
                request.setAttribute("error", "Please enter valid email.");
                getServletContext().getRequestDispatcher("/forgotPassword.jsp").forward(request, response);
            } else {
                if(userService.isEmailExist(email)){
                    String passwordResetToken = UUID.randomUUID().toString();
                    userService.addPasswordToken(email, passwordResetToken );
                    userService.sendMailForPassword(email, generateURL(request,passwordResetToken));
                    request.setAttribute("message", "Please check your mail to update password.");
                    getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
                }else{
                    request.setAttribute("error", "No such email exist.");
                    getServletContext().getRequestDispatcher("/forgotPassword.jsp").forward(request, response);
                }
            }
        } catch (DAOLayerException | MailException e) {
            e.printStackTrace();
        }
    }

    private String generateURL(HttpServletRequest request, String token){
        StringBuilder url = new StringBuilder();
        url.append(request.getScheme());
        url.append("://");
        url.append(request.getServerName());
        url.append(":");
        url.append(request.getServerPort());
        url.append("/resetPassword?id=");
        url.append(token);
        return url.toString();
    }


}
