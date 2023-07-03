package com.narola.finalproject.servlet.customer;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.CartDetails;
import com.narola.finalproject.model.User;
import com.narola.finalproject.service.CartService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;


public class ViewCartServlet extends HttpServlet {

    private CartService cartService = new CartService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");
        try {
            List<CartDetails> cartDetailsList = cartService.getUserCartDetails(user.getUserId());
            request.setAttribute("cartDetailsList", cartDetailsList);

            CartDetails cartDetails = cartService.getUserCartDetailsWithTotal(user.getUserId());
            request.setAttribute("cartDetails", cartDetails);

            getServletContext().getRequestDispatcher("/customer/viewCartDetails.jsp").forward(request,response);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
