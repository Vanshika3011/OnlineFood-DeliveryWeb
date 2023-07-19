package com.narola.finalproject.dao;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.Cart;
import com.narola.finalproject.model.CartDetails;
import com.narola.finalproject.model.CartItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDao {

    public int createCart(Cart cart) throws DAOLayerException {
        try {
            String query = "Insert into cart(customer_id, restaurant_id, created_by, updated_by)" +
                    "values(?,?,?,?)";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, cart.getCustomerId());
            stmt.setInt(2, cart.getRestaurantId());
            stmt.setInt(3, cart.getCustomerId());
            stmt.setInt(4, cart.getCustomerId());

            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            int generatedId = -1;
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
            }

            return generatedId;

        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while creating cart details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while creating cart details", e);
        }
    }

    public boolean doesUserCartAlreadyExist(int restaurantId, int userId) throws DAOLayerException {
        try {
            int count = 0;

            String query = "Select count(*) from cart where customer_id = ? and restaurant_id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setInt(2, restaurantId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return count > 0;

        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching cart items", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching cart items", e);
        }
    }


    public void doAddItemsToCart(CartItem cartItem, int cartId, int customerId, int itemPrice) throws DAOLayerException {
        try {
            String query = "Insert into cart_items(cart_id, item_id, price, created_by, updated_by) values(?,?,?,?,?)";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, cartId);
            stmt.setInt(2, cartItem.getItemId());
            stmt.setDouble(3, itemPrice);
            stmt.setInt(4, customerId);
            stmt.setInt(5, customerId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while adding cart items", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while adding cart items", e);
        }
    }

    public void deleteUserCart(int userId) throws DAOLayerException {
        try {
            String query = "Delete from cart where customer_id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, userId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while deleting cart items", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while deleting cart items", e);
        }
    }

    public int getCartIdOfUser(int userId, int restaurantId) throws DAOLayerException {
        try {
            int cartId = 0;
            String query = "Select cart_id from cart where customer_id = ? and restaurant_id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, userId);
            stmt.setInt(2, restaurantId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cartId = rs.getInt(1);
            }
            return cartId;
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching cart items", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching cart items", e);
        }
    }

    public List<CartDetails> getUserCartDetails(int userId) throws DAOLayerException {
        List<CartDetails> cartDetailsList = new ArrayList<>();
        try {
            String query = "Select i.cart_id, i.quantity, i.price, r.restaurant_name, m.item_name, r.id, i.cart_item_id, i.item_id, p.image_url from cart_items i join cart c on c.cart_id = i.cart_id join restaurant r on c.restaurant_id = r.id join menu m on m.item_id = i.item_id join items_image p on p.item_id = i.item_id where c.customer_id = ? ";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return cartDetailsList;
            }

            rs.previous();
            while (rs.next()) {
                CartDetails cartDetails = new CartDetails();

                cartDetails.setCartId(rs.getInt(1));
                cartDetails.setQuantity(rs.getInt(2));
                cartDetails.setPrice(rs.getDouble(3));
                cartDetails.setRestaurantName(rs.getString(4));
                cartDetails.setItemName(rs.getString(5));
                cartDetails.setRestaurantId(rs.getInt(6));
                cartDetails.setCartItemId(rs.getInt(7));
                cartDetails.setItemId(rs.getInt(8));
                cartDetails.setImageUrl(rs.getString(9));
                cartDetailsList.add(cartDetails);
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching cart items details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching cart items details", e);
        }
        return cartDetailsList;
    }

    public CartDetails getUserCartDetailsWithSum(int customerId) throws DAOLayerException {
        try {
            CartDetails cartDetails = new CartDetails();
            String query = "select c.cart_id,c.restaurant_id,r.restaurant_name,sum(i.quantity * i.price ) from cart_items i join cart c on c.cart_id = i.cart_id join restaurant r on r.id = c.restaurant_id where c.customer_id = ? ";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.setInt(1, customerId);

            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return cartDetails;
            }

            rs.previous();
            if (rs.next()) {
                cartDetails.setCartId(rs.getInt(1));
                cartDetails.setRestaurantId(rs.getInt(2));
                cartDetails.setRestaurantName(rs.getString(3));
                cartDetails.setTotalPrice((rs.getDouble(4)));
            }
            return cartDetails;
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching cart items details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching cart items details", e);
        }
    }

    public int getCartItemCount(int userId) throws DAOLayerException {
        try {
            int cartCount = 0;
            String query = "select count(*) from cart_items i join cart c on i.cart_id = c.cart_id where c.customer_id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cartCount = rs.getInt(1);
            }
            return cartCount;
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching cart items", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching cart items", e);
        }
    }
}
