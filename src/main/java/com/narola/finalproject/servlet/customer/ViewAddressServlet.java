package com.narola.finalproject.servlet.customer;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.User;
import com.narola.finalproject.model.UserAddress;
import com.narola.finalproject.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;


public class ViewAddressServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession httpSession = request.getSession();
            User user = (User) httpSession.getAttribute("user");

            List<UserAddress> userAddressList = userService.getUserAddressList(user.getUserId());
            if (userAddressList != null && !userAddressList.isEmpty() ) {
                request.setAttribute("userAddressList", userAddressList);
            }
            getServletContext().getRequestDispatcher("/customer/viewAddress.jsp").forward(request, response);

        } catch (DAOLayerException e) {
            request.setAttribute("error", "Error while Fetching Address.");
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
