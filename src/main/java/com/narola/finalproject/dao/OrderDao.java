package com.narola.finalproject.dao;

import com.narola.finalproject.constant.AppConstant;
import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    public int placeOrder(int customerId, double totalAmount, int addressId, int restaurantId) throws DAOLayerException {
        try {
            String query = "Insert into `order`(user_id, total_amount, created_by, updated_by, address_id, restaurant_id) values(?,?,?,?,?,?)";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, customerId);
            stmt.setDouble(2, totalAmount);
            stmt.setDouble(3, customerId);
            stmt.setInt(4, customerId);
            stmt.setInt(5, addressId);
            stmt.setInt(6, restaurantId);

            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            int generatedId = -1;
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
            }
            return generatedId;

        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while placing order", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while placing order", e);
        }
    }

    public void updateRazorPayOrderId(String razorPayOrderId, int orderId) throws DAOLayerException {
        try {
            String query = "update `order`set razorpay_oder_id=? where order_id= ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, razorPayOrderId);
            stmt.setInt(2, orderId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while adding orderId", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while adding orderId", e);
        }
    }

    public OrderPaymentDetails getOrderDetailsForPayment(int orderId) throws DAOLayerException {
        try {
            OrderPaymentDetails orderPaymentDetails = new OrderPaymentDetails();
            String query = "Select o.order_id, o.user_id, o.total_amount, o.address_id,o.restaurant_id, o.razorpay_oder_id, r.restaurant_name, u.firstName, u.lastName, u.contact, u.email, a.street1, a.street2, a.landmark, s.state_name, c.city_name from `order` o " +
                    "join user u on u.id = o.user_id join user_address a on a.user_address_id = o.address_id  \n" +
                    "join state s on a.state_id = s.id\n" +
                    "join city c on c.id = a.city_id\n" +
                    "join restaurant r on restaurant_id = r.id\n" +
                    "where o.order_id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                UserAddress userAddress = new UserAddress();
                State state = new State();
                City city = new City();
                orderPaymentDetails.setOrderId(rs.getInt(1));
                orderPaymentDetails.setAmount(rs.getDouble(3));
                orderPaymentDetails.setRazorPayOrderId(rs.getString(6));
                orderPaymentDetails.setCustomerFirstName(rs.getString(8));
                orderPaymentDetails.setCustomerLastName(rs.getString(9));
                orderPaymentDetails.setContact(rs.getString(10));
                orderPaymentDetails.setEmail(rs.getString(11));

                userAddress.setStreetLine1(rs.getString(12));
                userAddress.setStreetLine2(rs.getString(13));
                userAddress.setLandmark(rs.getString(14));

                state.setStateName(rs.getString(15));
                city.setCityName(rs.getString(16));

                userAddress.setState(state);
                userAddress.setCity(city);
                orderPaymentDetails.setUserAddress(userAddress);
            }
            return orderPaymentDetails;
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching order details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching order details", e);
        }
    }

    public String getRazorPayOrderId(int customerId, int orderId) throws DAOLayerException {
        try {
            String query = "Select razorpay_oder_id from `order` where user_id = ? and order_id =? order by order_date DESC LIMIT 1";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, customerId);
            stmt.setInt(2, orderId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching order details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching order details", e);
        }
        return null;
    }

    public void updateOrderPaymentDetails(RazorpayPaymentDetails razorpayPaymentDetails) throws DAOLayerException {
        try {
            String query = "Update `order` SET razorpay_payment_id =?, razorpay_signature=?, currency=?, payment_status=?, payment_method=?, payment_details=?, error_source=?, error_details=?,order_status_id=? where order_id = ? and razorpay_oder_id=?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, razorpayPaymentDetails.getPaymentId());
            stmt.setString(2, razorpayPaymentDetails.getPaymentSignature());
            stmt.setString(3, razorpayPaymentDetails.getCurrency());
            stmt.setString(4, razorpayPaymentDetails.getStatus());
            stmt.setString(5, razorpayPaymentDetails.getPaymentMethod());
            stmt.setString(6, razorpayPaymentDetails.getPaymentDetails());
            stmt.setString(7, razorpayPaymentDetails.getErrorSource());
            stmt.setString(8, razorpayPaymentDetails.getErrorDetails());
            stmt.setInt(9, razorpayPaymentDetails.getOrderStatusId());
            stmt.setInt(10, razorpayPaymentDetails.getOrderId());
            stmt.setString(11, razorpayPaymentDetails.getRazorPayOrderId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while updating order payment details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating order payment details", e);
        }
    }

    public List<Order> getAllUserOrders(int customerId) throws DAOLayerException {
        List<Order> orderList = new ArrayList<>();
        try {
            String query = "select r.restaurant_name, o.restaurant_id, o.order_id, o.total_amount, o.order_date, o.razorpay_payment_id, o.currency,\n" +
                    "o.payment_status, o.payment_method, o.payment_details, u.firstName, u.lastName, a.type, a.street1, a.street2, a.landmark, \n" +
                    "c.city_name,a.pincode, s.state_name, o.user_id, d.order_status_name, o.order_status_id from `order` o \n" +
                    "join user u on o.user_id = u.id\n" +
                    "join restaurant r on r.id = o.restaurant_id \n" +
                    "join user_address a on a.user_address_id = o.address_id\n" +
                    "join city c on c.id = a.city_id\n" +
                    "join state s on s.id = a.state_id\n" +
                    "join order_status d on d.order_status_id = o.order_status_id\n" +
                    "where  o.user_id = ?";

            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                UserAddress userAddress = new UserAddress();
                RazorpayPaymentDetails razorpayPaymentDetails = new RazorpayPaymentDetails();
                City city = new City();
                State state = new State();
                int orderId = rs.getInt(3);

                order.setRestaurantName(rs.getString(1));
                order.setOrderId(rs.getInt(3));
                order.setTotalAmount(rs.getDouble(4));
                order.setOrderDate(rs.getTimestamp(5).toLocalDateTime());
                order.setUserId(rs.getInt(20));
                order.setOrderStatusName(rs.getString(21));
                order.setOrderStatusId(rs.getInt(22));

                razorpayPaymentDetails.setPaymentId(rs.getString(6));
                razorpayPaymentDetails.setCurrency(rs.getString(7));
                razorpayPaymentDetails.setStatus(rs.getString(8));
                razorpayPaymentDetails.setPaymentMethod(rs.getString(9));
                razorpayPaymentDetails.setPaymentDetails(rs.getString(10));
                order.setRazorpayPaymentDetails(razorpayPaymentDetails);

                order.setCustomerName(rs.getString(11) + " " + rs.getString(12));

                userAddress.setAddressType(rs.getString(13));
                userAddress.setStreetLine2(rs.getString(15));
                userAddress.setStreetLine1(rs.getString(14));
                userAddress.setLandmark(rs.getString(16));

                city.setCityName(rs.getString(17));
                userAddress.setCity(city);
                userAddress.setPincode(rs.getInt(18));
                state.setStateName(rs.getString(19));
                userAddress.setState(state);
                order.setUserAddress(userAddress);

                order.setOrderDetailList(getAllOrderDetails(orderId, customerId));

                orderList.add(order);
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching order details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching order details", e);
        }
        return orderList;
    }

    public List<OrderDetail> getAllOrderDetails(int orderId, int userId) throws DAOLayerException {
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            String query = "select i.order_item_id, i.item_id, i.quantity, i.price,m.item_name, c.cuisine_name, g.image_url from order_items i join menu m on m.item_id = i.item_id join items_image g on g.item_id = m.item_id join cuisine_category c on m.category_id = c.cuisine_category_id where  i.order_id =? and i.created_by = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, orderId);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderItemId(rs.getInt(1));
                orderDetail.setItemId(rs.getInt(2));
                orderDetail.setQuantity(rs.getInt(3));
                orderDetail.setPrice(rs.getDouble(4));
                orderDetail.setItemName(rs.getString(5));
                orderDetail.setCuisineName(rs.getString(6));
                orderDetail.setImageUrl(rs.getString(7));

                orderDetailList.add(orderDetail);
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching order details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching order details", e);
        }
        return orderDetailList;
    }

    public int getOrderId(int customerId) throws DAOLayerException {
        try {
            String query = "Select order_id from `order` where user_id = ? order by order_date DESC LIMIT 1";
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

    public List<Order> getAllRestaurantsOrders(int restaurantId) throws DAOLayerException {
        List<Order> orderList = new ArrayList<>();
        try {
            String query = "select r.restaurant_name, o.restaurant_id, o.order_id, o.total_amount, o.order_date, o.razorpay_payment_id, o.currency,\n" +
                    "o.payment_status, o.payment_method, o.payment_details, u.firstName, u.lastName, a.type, a.street1, a.street2, a.landmark, \n" +
                    "c.city_name,a.pincode, s.state_name, o.user_id, d.order_status_name, o.order_status_id from `order` o \n" +
                    "join user u on o.user_id = u.id\n" +
                    "join restaurant r on r.id = o.restaurant_id \n" +
                    "join user_address a on a.user_address_id = o.address_id\n" +
                    "join city c on c.id = a.city_id\n" +
                    "join state s on s.id = a.state_id\n" +
                    "join order_status d on d.order_status_id = o.order_status_id\n" +
                    "where  r.id = ?";

            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, restaurantId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                UserAddress userAddress = new UserAddress();
                RazorpayPaymentDetails razorpayPaymentDetails = new RazorpayPaymentDetails();
                City city = new City();
                State state = new State();
                int orderId = rs.getInt(3);

                order.setRestaurantName(rs.getString(1));
                order.setOrderId(rs.getInt(3));
                order.setTotalAmount(rs.getDouble(4));
                order.setOrderDate(rs.getTimestamp(5).toLocalDateTime());
                order.setUserId(rs.getInt(20));
                order.setOrderStatusName(rs.getString(21));
                order.setOrderStatusId(rs.getInt(22));

                razorpayPaymentDetails.setPaymentId(rs.getString(6));
                razorpayPaymentDetails.setCurrency(rs.getString(7));
                razorpayPaymentDetails.setStatus(rs.getString(8));
                razorpayPaymentDetails.setPaymentMethod(rs.getString(9));
                razorpayPaymentDetails.setPaymentDetails(rs.getString(10));
                order.setRazorpayPaymentDetails(razorpayPaymentDetails);

                order.setCustomerName(rs.getString(11) + " " + rs.getString(12));

                userAddress.setAddressType(rs.getString(13));
                userAddress.setStreetLine2(rs.getString(15));
                userAddress.setStreetLine1(rs.getString(14));
                userAddress.setLandmark(rs.getString(16));

                city.setCityName(rs.getString(17));
                userAddress.setCity(city);
                userAddress.setPincode(rs.getInt(18));
                state.setStateName(rs.getString(19));
                userAddress.setState(state);
                order.setUserAddress(userAddress);

                orderList.add(order);
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching order details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching order details", e);
        }
        return orderList;
    }

    public Order getOrderDetails(int orderId) throws DAOLayerException {
        try {
            Order order = new Order();
            String query = "select r.restaurant_name, o.restaurant_id, o.order_id, o.total_amount, o.order_date, o.razorpay_payment_id, o.currency,\n" +
                    "o.payment_status, o.payment_method, o.payment_details, u.firstName, u.lastName, a.type, a.street1, a.street2, a.landmark, \n" +
                    "c.city_name,a.pincode, s.state_name, o.user_id, d.order_status_name, o.order_status_id from `order` o \n" +
                    "join user u on o.user_id = u.id\n" +
                    "join restaurant r on r.id = o.restaurant_id \n" +
                    "join user_address a on a.user_address_id = o.address_id\n" +
                    "join city c on c.id = a.city_id\n" +
                    "join state s on s.id = a.state_id\n" +
                    "join order_status d on d.order_status_id = o.order_status_id\n" +
                    "where  o.order_id = ?";

            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                UserAddress userAddress = new UserAddress();
                RazorpayPaymentDetails razorpayPaymentDetails = new RazorpayPaymentDetails();
                City city = new City();
                State state = new State();

                order.setRestaurantName(rs.getString(1));
                order.setOrderId(rs.getInt(3));
                order.setTotalAmount(rs.getDouble(4));
                order.setOrderDate(rs.getTimestamp(5).toLocalDateTime());
                order.setUserId(rs.getInt(20));
                order.setOrderStatusName(rs.getString(21));
                order.setOrderStatusId(rs.getInt(22));

                razorpayPaymentDetails.setPaymentId(rs.getString(6));
                razorpayPaymentDetails.setCurrency(rs.getString(7));
                razorpayPaymentDetails.setStatus(rs.getString(8));
                razorpayPaymentDetails.setPaymentMethod(rs.getString(9));
                razorpayPaymentDetails.setPaymentDetails(rs.getString(10));
                order.setRazorpayPaymentDetails(razorpayPaymentDetails);

                order.setCustomerName(rs.getString(11) + " " + rs.getString(12));

                userAddress.setAddressType(rs.getString(13));
                userAddress.setStreetLine2(rs.getString(15));
                userAddress.setStreetLine1(rs.getString(14));
                userAddress.setLandmark(rs.getString(16));

                city.setCityName(rs.getString(17));
                userAddress.setCity(city);
                userAddress.setPincode(rs.getInt(18));
                state.setStateName(rs.getString(19));
                userAddress.setState(state);
                order.setUserAddress(userAddress);

                order.setOrderDetailList(getAllOrderDetails(orderId, rs.getInt(20)));
            }
            return order;
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching order details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching order details", e);
        }
    }

    public void updateOrderStatus( int orderId, int statusId) throws DAOLayerException {
        try {
            String query = "update `order`set order_status_id=? where order_id= ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, statusId);
            stmt.setInt(2, orderId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while adding order status", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while adding order status   ", e);
        }
    }
}



