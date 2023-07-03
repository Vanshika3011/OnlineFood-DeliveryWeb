package com.narola.finalproject.servlet.customer;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.User;
import com.narola.finalproject.service.CartService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class IncreaseItemCountServlet extends HttpServlet {
    private CartService cartService = new CartService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession httpSession = request.getSession();
            User user = (User) httpSession.getAttribute("user");
            String itemId = request.getParameter("itemId");
            cartService.increaseItemCount(Integer.parseInt(itemId), user.getUserId());

            response.sendRedirect("/viewCartDetails");
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
