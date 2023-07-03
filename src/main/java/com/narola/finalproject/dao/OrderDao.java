package com.narola.finalproject.dao;


import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.Order;
import com.narola.finalproject.model.OrderDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    public int placeOrder(int customerId, double totalAmount) throws DAOLayerException {
        try {
            String query = "Insert into `order`(user_id, total_amount, created_by, updated_by) values(?,?,?,?)";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, customerId);
            stmt.setDouble(2, totalAmount);

            stmt.setDouble(3, customerId);
            stmt.setInt(4, customerId);

            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            int generatedId = -1;
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
            }
            return generatedId;

        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while adding cart items", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while adding cart items", e);
        }
    }

    public List<OrderDetail> getAllOrderDetails(int orderId) throws DAOLayerException {
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            String query = "select oi.order_item_id, oi.order_id, oi.item_id, oi.quantity, oi.price, o.order_id, o.user_id, o.total_amount, o.order_date, o.order_status_id, m.item_name, r.restaurant_name, os.order_status_name from order_items oi join `order` o  on oi.order_id = o.order_id join order_status os on o.order_status_id = os.order_status_id join menu m on m.item_id = oi.item_id join restaurant r on r.id = m.restaurant_id where oi.order_id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderDetail orderDetail = new OrderDetail();

                orderDetail.setOrderItemId(rs.getInt(1));
                orderDetail.setOrderId(rs.getInt(2));
                orderDetail.setItemId(rs.getInt(3));
                orderDetail.setQuantity(rs.getInt(4));
                orderDetail.setPrice(rs.getDouble(5));
                orderDetail.setCustomerId(rs.getInt(7));
                orderDetail.setTotalAmount(rs.getDouble(8));
                orderDetail.setOrderDateTime(rs.getTimestamp(9).toLocalDateTime());
                orderDetail.setOrderStatusId(rs.getInt(10));
                orderDetail.setItemName(rs.getString(11));
                orderDetail.setRestaurantName(rs.getString(12));
                orderDetail.setOrderStatusName(rs.getString(13));

                orderDetailList.add(orderDetail);
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching order details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching order details", e);
        }
        return orderDetailList;
    }

    public List<Order> getAllUserOrders(int customerId) throws DAOLayerException {
        List<Order> orderList = new ArrayList<>();
        try {
            String query = "select  o.order_id, o.total_amount, o.order_date, o.order_status_id, os.order_status_name from `order` o join order_status os on o.order_status_id = os.order_status_id where o.user_id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt(1));
                order.setTotalAmount(rs.getDouble(2));
                order.setOrderDate(rs.getTimestamp(3).toLocalDateTime());
                order.setOrderStatusId(rs.getInt(4));
                order.setOrderStatusName(rs.getString(5));
                orderList.add(order);
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching order details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching order details", e);
        }
        return orderList;
    }

    public double getTotalAmountForOrder(int customerId) throws DAOLayerException {
        try {
            String query = "select sum(quantity*price) as total_amount from cart_items group by (created_by) having created_by = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, customerId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching order details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching order details", e);
        }
        return 0;
    }
}



