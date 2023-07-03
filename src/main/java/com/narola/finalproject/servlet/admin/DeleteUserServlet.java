package com.narola.finalproject.servlet.admin;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class DeleteUserServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String userId = request.getParameter("id");
            if (userId == null || userId.isEmpty()) {
                request.setAttribute("error", "Error while deleting user.");
                getServletContext().getRequestDispatcher("/errorPage.jsp").include(request, response);
            } else {
                userService.deleteUser(Integer.parseInt(userId));
                response.sendRedirect("/userManagement");
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
