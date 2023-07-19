package com.narola.finalproject.service;

import com.narola.finalproject.dao.CartDao;
import com.narola.finalproject.dao.OrderDao;
import com.narola.finalproject.dao.OrderItemsDao;
import com.narola.finalproject.dao.OrderPaymentDetails;
import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.CartDetails;
import com.narola.finalproject.model.Order;
import com.narola.finalproject.model.RazorpayPaymentDetails;


import java.util.List;


public class OrderService {
    private OrderDao orderDao = new OrderDao();
    private CartDao cartDao = new CartDao();
    private OrderItemsDao orderItemsDao = new OrderItemsDao();


    public int placeOrder(int customerId, CartDetails cartDetails, int addressId) throws DAOLayerException {
        List<CartDetails> cartDetailsList = cartDao.getUserCartDetails(customerId);

        int orderId = orderDao.placeOrder(customerId, cartDetails.getTotalPrice(), addressId, cartDetails.getRestaurantId());

        for (CartDetails cartList : cartDetailsList) {
            orderItemsDao.addItemsToOrder(cartList, orderId, customerId);
        }
        return orderId;
    }

    public void addOrderId(String razorPayId, int orderId) throws DAOLayerException {
        orderDao.updateRazorPayOrderId(razorPayId, orderId);
    }

    public OrderPaymentDetails getOrderDetails(int orderId) throws DAOLayerException {
        return orderDao.getOrderDetailsForPayment(orderId);
    }

    public String getRazorPayOrderId(int customerId, int orderId) throws DAOLayerException {
        return orderDao.getRazorPayOrderId(customerId, orderId);
    }

    public void updateOrderPaymentDetails(RazorpayPaymentDetails razorpayPaymentDetails) throws DAOLayerException {
        orderDao.updateOrderPaymentDetails(razorpayPaymentDetails);
    }

    public List<Order> getAllUserOrders(int customerId) throws DAOLayerException {
        return orderDao.getAllUserOrders(customerId);
    }

    public int getOrderId(int customerId) throws DAOLayerException{
        return orderDao.getOrderId(customerId);
    }

    public List<Order> getAllRestaurantOrder(int restaurantId) throws DAOLayerException {
        return orderDao.getAllRestaurantsOrders(restaurantId);
    }

    public Order getOrdersDetails(int orderId) throws DAOLayerException {
        return orderDao.getOrderDetails(orderId);
    }

    public void updateOrderStatusId(int orderId, int orderStatusId) throws DAOLayerException {
        orderDao.updateOrderStatus(orderId, orderStatusId);
    }

}