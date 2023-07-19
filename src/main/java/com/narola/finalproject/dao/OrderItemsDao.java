package com.narola.finalproject.dao;
import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.CartDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class OrderItemsDao {

    public void addItemsToOrder(CartDetails cartList, int orderId, int customerId) throws DAOLayerException {
        try {
            String query = "Insert into order_items(order_id, item_id, quantity, price, created_by, updated_by)" +
                    "values(?,?,?,?,?,?)";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, orderId);
            stmt.setInt(2, cartList.getItemId());
            stmt.setInt(3, cartList.getQuantity());
            stmt.setDouble(4, cartList.getPrice());
            stmt.setInt(5, customerId);
            stmt.setInt(6, customerId);


            stmt.executeUpdate();
        } catch (
                SQLException e) {
            throw new DAOLayerException("Exception occurred while adding order items details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while adding order items details", e);
        }

    }
}