package com.narola.finalproject.servlet.customer;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.Cart;
import com.narola.finalproject.model.CartItem;
import com.narola.finalproject.model.RestaurantMenu;
import com.narola.finalproject.model.User;
import com.narola.finalproject.service.CartService;
import com.narola.finalproject.service.RestaurantMenuService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;


public class AddToCartServlet extends HttpServlet {
    private CartService cartService = new CartService();
    private RestaurantMenuService restaurantMenuService = new RestaurantMenuService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String itemId = request.getParameter("itemId");
            String restaurantId = request.getParameter("restaurantId");
            if (itemId == null || restaurantId == null) {
                System.out.println(itemId);
                System.out.println(restaurantId);
                request.setAttribute("error", "Error adding item to cart.Try to add later");
                getServletContext().getRequestDispatcher("/errorPage.jsp").include(request, response);
            } else {
                HttpSession httpSession = request.getSession();
                User user = (User) httpSession.getAttribute("user");
                getRestaurantMenu(request, Integer.parseInt(restaurantId));

                Cart cart = new Cart();
                cart.setCustomerId(user.getUserId());
                cart.setRestaurantId(Integer.parseInt(restaurantId));

                CartItem cartItem = new CartItem();
                cartItem.setItemId(Integer.parseInt(itemId));
                cartService.doAddCartItem(cart, cartItem);
                response.sendRedirect("/viewRestaurantMenu?id="+restaurantId);
            }
        } catch (DAOLayerException e) {
            request.setAttribute("error", "Error occurred while adding item to cart.");
            getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void getRestaurantMenu(HttpServletRequest request, int restaurantId ) throws DAOLayerException {
        List<RestaurantMenu> restaurantMenuList = restaurantMenuService.getRestaurantMenu(restaurantId);
        request.setAttribute("restaurantMenuList", restaurantMenuList);
    }
}
