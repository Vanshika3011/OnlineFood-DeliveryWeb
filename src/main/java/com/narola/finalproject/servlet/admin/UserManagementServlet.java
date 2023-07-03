package com.narola.finalproject.servlet.admin;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.User;
import com.narola.finalproject.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class UserManagementServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<User> userList = userService.getUserList();

            String filterBy = request.getParameter("filterBy");
            if(filterBy == null){
                filterBy = "default";
            }
            List<User> usersList = userService.getFilteredUserList(filterBy, userList);
            request.setAttribute("userList", usersList);
            getServletContext().getRequestDispatcher("/admin/userManagement.jsp").forward(request,response);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


}
