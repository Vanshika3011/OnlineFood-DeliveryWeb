package com.narola.finalproject.dao;

import com.narola.finalproject.exception.DAOLayerException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartItemDao {
    public boolean doesItemAlreadyExist(int itemId, int cartId) throws DAOLayerException {
        try {
            int count = 0;
            String query = "Select count(*) from cart_items where item_id = ? and cart_id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, itemId);
            stmt.setInt(2, cartId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return count > 0;

        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching cart items details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching cart items details", e);
        }
    }

    public void updateItemCount(int itemId, int customerId) throws DAOLayerException {
        try {
            String query = "Update cart_items Set quantity = quantity+1 where item_id = ? and created_by = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, itemId);
            stmt.setInt(2, customerId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while updating cart items quantity", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating cart items quantity", e);
        }
    }

    public void reduceItemCount(int itemId, int customerId) throws DAOLayerException {
        try {
            String query = "Update cart_items Set quantity = quantity-1 where item_id = ? and created_by = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, itemId);
            stmt.setInt(2, customerId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while updating cart items quantity", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating cart items quantity", e);
        }
    }

    public void removeItem(int itemId, int customerId) throws DAOLayerException {
        try {
            String query = "Delete from cart_items where item_id = ? and created_by = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, itemId);
            stmt.setInt(2, customerId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while updating cart items quantity", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating cart items quantity", e);
        }
    }

    public int getItemQuantity(int itemId, int customerId) throws DAOLayerException {
        try {
            int count = 0;
            String query = "Select quantity from cart_items where item_id = ? and created_by = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, itemId);
            stmt.setInt(2, customerId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching cart items details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching cart items details", e);
        }
    }
}
