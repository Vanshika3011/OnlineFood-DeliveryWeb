package com.narola.finalproject.servlet.admin;

import com.narola.finalproject.enums.UserRolesEnum;
import com.narola.finalproject.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class AdminHomePageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");
        request.setAttribute("firstName", user.getFirstName());
        request.setAttribute("lastName", user.getLastName());
        getServletContext().getRequestDispatcher("/admin/adminHomePage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
