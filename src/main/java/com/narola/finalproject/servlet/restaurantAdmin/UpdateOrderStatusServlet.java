package com.narola.finalproject.servlet.restaurantAdmin;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.Order;
import com.narola.finalproject.model.Restaurant;
import com.narola.finalproject.service.OrderService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class UpdateOrderStatusServlet extends HttpServlet {
    private OrderService orderService = new OrderService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession httpSession = request.getSession();
            Restaurant restaurant = (Restaurant) httpSession.getAttribute("restaurant1");

            String orderId= request.getParameter("id");
            String statusId= request.getParameter("statusId");
            if(orderId == null || statusId == null || orderId.isEmpty() || statusId.isEmpty()){
                request.setAttribute("error", "No such order id found.");
                getServletContext().getRequestDispatcher("/restaurantAdmin/viewOrders.jsp").forward(request,response);
            }else{
                orderService.updateOrderStatusId(Integer.parseInt(orderId), Integer.parseInt(statusId));
                List<Order> orderList = orderService.getAllRestaurantOrder(restaurant.getRestaurantId());
                request.setAttribute("orderList", orderList);
                getServletContext().getRequestDispatcher("/restaurantAdmin/viewOrders.jsp").forward(request,response);
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
