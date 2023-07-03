package com.narola.finalproject.servlet.restaurantAdmin;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.service.RestaurantMenuService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;


public class DeleteMenuItemServlet extends HttpServlet {
    private RestaurantMenuService restaurantMenuService = new RestaurantMenuService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String itemId = request.getParameter("id");
            if (itemId.isEmpty()) {
                request.setAttribute("error", "Error while deleting item from menu.");
                getServletContext().getRequestDispatcher("/errorPage.jsp").include(request, response);
            } else {
                restaurantMenuService.deleteMenuItem(Integer.parseInt(itemId));
                response.sendRedirect("/menuManager");
            }
        } catch (DAOLayerException e) {
            request.setAttribute("error", "Error while deleting item from menu.");
            getServletContext().getRequestDispatcher("/errorPage.jsp").include(request, response);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
