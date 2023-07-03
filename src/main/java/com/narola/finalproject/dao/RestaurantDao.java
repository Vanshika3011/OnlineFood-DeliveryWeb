package com.narola.finalproject.dao;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDao {
    public int doAddRestaurant(Restaurant restaurant, int generatedId) throws DAOLayerException {
        try {
            String query = "Insert into restaurant(restaurant_name, restaurant_address_id, restaurant_contact, restaurant_email, owner_id, opens_at, closes_at, gst_number, created_by, updated_by,image_url, is_dining_available)" +
                    "values(?,?,?,?,?,?,?,?,?,?,?,?)";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, restaurant.getRestaurantName());
            stmt.setInt(2, generatedId);
            stmt.setString(3, restaurant.getRestaurantContact());
            stmt.setString(4, restaurant.getRestaurantEmail());
            stmt.setInt(5, restaurant.getRestaurantOwnerId());
            stmt.setString(6, (restaurant.getOpensAt()));
            stmt.setString(7, (restaurant.getClosesAt()));
            stmt.setString(8, restaurant.getGstNo());
            stmt.setInt(9, restaurant.getRestaurantOwnerId());
            stmt.setInt(10, restaurant.getRestaurantOwnerId());
            stmt.setString(11,restaurant.getRestaurantImageUrl());
            stmt.setBoolean(12, restaurant.isDiningAvailable());

            return stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while inserting restaurant details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while inserting restaurant details", e);
        }
    }

    public void doAddRestaurantAndAddressDetails(Restaurant restaurant) throws DAOLayerException {
        try {
            String query = "Insert into restaurant_address(restaurant_address_line1, restaurant_address_line2, city, state, pincode, created_by, updated_by) values(?,?,?,?,?,?,?)";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, restaurant.getRestaurantAddress().getRestaurantAddLine1());
            stmt.setString(2, restaurant.getRestaurantAddress().getRestaurantAddLine2());
            stmt.setInt(3, restaurant.getRestaurantAddress().getCityId());
            stmt.setInt(4, restaurant.getRestaurantAddress().getStateId());
            stmt.setInt(5, restaurant.getRestaurantAddress().getPincode());
            stmt.setInt(6, restaurant.getRestaurantOwnerId());
            stmt.setInt(7, restaurant.getRestaurantOwnerId());

            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            int generatedId = -1;
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
            }
            doAddRestaurant(restaurant, generatedId);
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while updating restaurant details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating restaurant details", e);
        }
    }


    public List<Restaurant> getAllRestaurants() throws DAOLayerException {
        List<Restaurant> restaurantList = new ArrayList<>();
        try {
            String query = "Select * from restaurant where is_delete = 0";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setRestaurantId(rs.getInt(1));
                restaurant.setRestaurantName(rs.getString(2));
                restaurant.setRestaurantAddressId(rs.getInt(3));
                restaurant.setRestaurantContact(rs.getString(4));
                restaurant.setRestaurantEmail(rs.getString(5));
                restaurant.setRestaurantOwnerId(rs.getInt(6));
                restaurant.setOpensAt(rs.getString(7));
                restaurant.setClosesAt(rs.getString(8));
                restaurant.setGstNo(rs.getString(9));
                restaurant.setDiningAvailable(rs.getBoolean(10));
                restaurant.setDelete(rs.getBoolean(12));
                restaurant.setRestaurantImageUrl(rs.getString(11));

                restaurantList.add(restaurant);
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching restaurant details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching restaurant details", e);
        }
        return restaurantList;
    }

    public Restaurant getAllRestaurantsDetailsByOwnerID(int ownerId) throws DAOLayerException {
        Restaurant restaurant = new Restaurant();
        try {
            String query = "Select r.restaurant_name, r.restaurant_contact, r.restaurant_email, r.opens_at, r.closes_at, r.gst_number, a.restaurant_address_line1, a.restaurant_address_line2, a.pincode, c.city_name, s.state_name, r.restaurant_address_id, r.id, r.image_url, r.is_dining_available, u.firstName, u.lastName from restaurant r  join restaurant_address a on  r.restaurant_address_id = a.id join city c on a.city = c.id join state s on a.state = s.id join user u on u.id= r.owner_id where owner_id = ? ";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.setInt(1, ownerId);

            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }

            RestaurantAddress restaurantAddress = new RestaurantAddress();
            City city = new City();
            State state = new State();
            User user = new User();

            rs.previous();
            if (rs.next()) {

                restaurant.setRestaurantName(rs.getString(1));
                restaurant.setRestaurantContact(rs.getString(2));
                restaurant.setRestaurantEmail(rs.getString(3));
                restaurant.setOpensAt(rs.getString(4));
                restaurant.setClosesAt(rs.getString(5));
                restaurant.setGstNo(rs.getString(6));
                restaurant.setRestaurantId(rs.getInt(13));

                restaurantAddress.setRestaurantAddLine1(rs.getString(7));
                restaurantAddress.setRestaurantAddLine2(rs.getString(8));
                restaurantAddress.setPincode(rs.getInt(9));

                city.setCityName(rs.getString(10));
                state.setStateName(rs.getString(11));
                restaurant.setRestaurantAddressId(rs.getInt(12));
                restaurant.setRestaurantId(rs.getInt(13));
                restaurant.setRestaurantImageUrl(rs.getString(14));
                restaurant.setDiningAvailable(rs.getBoolean(15));

                user.setFirstName(rs.getString(16));
                user.setLastName(rs.getString(17));

                restaurant.setUser(user);
                restaurantAddress.setCity(city);
                restaurantAddress.setState(state);

                restaurant.setRestaurantAddress(restaurantAddress);

                return restaurant;
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching restaurant details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching restaurant details", e);
        }
        return restaurant;
    }

    public boolean doesUserOwnRestaurant(int ownerId) throws DAOLayerException {
        try {
            String query = "Select count(*) from restaurant where owner_id = ? ";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, ownerId);

            ResultSet rs = stmt.executeQuery();

            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return count > 0;

        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while updating restaurant details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating restaurant details", e);
        }
    }

    public void deleteRestaurant(int restaurantId) throws DAOLayerException {
        try {
            String query = "Update  restaurant Set is_delete = 1 where id = ? ";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, restaurantId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while deleting restaurant", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while deleting restaurant", e);
        }
    }

    public Restaurant getAllRestaurantsDetailsByRestaurantID(int restaurantId) throws DAOLayerException {
        Restaurant restaurant = new Restaurant();
        try {
            String query = "Select r.restaurant_name, r.restaurant_contact, r.restaurant_email, r.opens_at, r.closes_at, r.gst_number, a.restaurant_address_line1, a.restaurant_address_line2, a.pincode, c.city_name, s.state_name, r.restaurant_address_id, r.id, r.image_url, r.is_dining_available, u.firstName, u.lastName, s.id, c.id from restaurant r  join restaurant_address a on  r.restaurant_address_id = a.id join city c on a.city = c.id join state s on a.state = s.id join user u on u.id= r.owner_id where r.id = ? ";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.setInt(1, restaurantId);

            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }

            RestaurantAddress restaurantAddress = new RestaurantAddress();
            City city = new City();
            State state = new State();
            User user = new User();

            rs.previous();
            if (rs.next()) {

                restaurant.setRestaurantName(rs.getString(1));
                restaurant.setRestaurantContact(rs.getString(2));
                restaurant.setRestaurantEmail(rs.getString(3));
                restaurant.setOpensAt(rs.getString(4));
                restaurant.setClosesAt(rs.getString(5));
                restaurant.setGstNo(rs.getString(6));
                restaurant.setRestaurantId(restaurantId);

                restaurantAddress.setRestaurantAddLine1(rs.getString(7));
                restaurantAddress.setRestaurantAddLine2(rs.getString(8));
                restaurantAddress.setPincode(rs.getInt(9));

                city.setCityId(rs.getInt(19));
                city.setCityName(rs.getString(10));

                state.setStateId(18);
                state.setStateName(rs.getString(11));

                restaurant.setRestaurantAddressId(rs.getInt(12));
                restaurant.setRestaurantId(rs.getInt(13));
                restaurant.setRestaurantImageUrl(rs.getString(14));
                restaurant.setDiningAvailable(rs.getBoolean(15));

                user.setFirstName(rs.getString(16));
                user.setLastName(rs.getString(17));

                restaurant.setUser(user);
                restaurantAddress.setCity(city);
                restaurantAddress.setState(state);

                restaurant.setRestaurantAddress(restaurantAddress);

                return restaurant;
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching restaurant details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching restaurant details", e);
        }
        return restaurant;
    }

    public int editRestaurantDetails(Restaurant restaurant, int restaurantId) throws DAOLayerException {
        try {
            String query1 = "Update restaurant SET restaurant_name = ?, restaurant_contact = ?, restaurant_email = ?,opens_at = ?, closes_at = ?, updated_by = ?,image_url = ? ,updated_at = default where id=? and owner_id=?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query1);

            stmt.setString(1, restaurant.getRestaurantName());
            stmt.setString(2, restaurant.getRestaurantContact());
            stmt.setString(3, restaurant.getRestaurantEmail());
            stmt.setString(4, restaurant.getOpensAt());
            stmt.setString(5, restaurant.getClosesAt());
            stmt.setInt(6, restaurant.getRestaurantOwnerId());
            stmt.setString(7,restaurant.getRestaurantImageUrl());
            stmt.setInt(8, restaurantId);
            stmt.setInt(9, restaurant.getRestaurantOwnerId());

            editRestaurantAddress(restaurant);

            return stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while updating restaurant details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating restaurant details", e);
        }
    }

    public int editRestaurantAddress(Restaurant restaurant) throws DAOLayerException {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "Update restaurant_address SET restaurant_address_line1 = ?, restaurant_address_line2 = ?, pincode = ?, updated_at = default, updated_by = ? where id=? and (? =(select owner_id from restaurant r where id = ?))";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, restaurant.getRestaurantAddress().getRestaurantAddLine1());
            stmt.setString(2, restaurant.getRestaurantAddress().getRestaurantAddLine2());
            stmt.setInt(3, restaurant.getRestaurantAddress().getPincode());
            stmt.setInt(4, restaurant.getRestaurantOwnerId());
            stmt.setInt(5, restaurant.getRestaurantAddressId());
            stmt.setInt(6, restaurant.getRestaurantOwnerId());
            stmt.setInt(7, restaurant.getRestaurantId());

            return stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while updating restaurant address details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating restaurant address details", e);
        }
    }

    public int getOwnerIdOfRestaurant(int restaurantId) throws DAOLayerException {
        try {
            String query = "Select owner_id from restaurant where id = ? ";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, restaurantId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching restaurant details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching restaurant details", e);
        }
        return 0;
    }

    public List<Restaurant> getAllRestaurantsByCategory(int cuisineId) throws DAOLayerException {
        List<Restaurant> restaurantList = new ArrayList<>();
        try {
            String query = "Select * from restaurant r join restaurant_address a on r.restaurant_address_id = a.id  join city c on a.city = c.id join state s on a.state = s.id join menu m on r.id = m.restaurant_id where r.is_delete = 0 and m.category_id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, cuisineId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Restaurant restaurant = new Restaurant();
                RestaurantAddress restaurantAddress = new RestaurantAddress();
                City city = new City();
                State state = new State();

                restaurant.setRestaurantId(rs.getInt(1));
                restaurant.setRestaurantName(rs.getString(2));
                restaurant.setRestaurantAddressId(rs.getInt(3));
                restaurant.setRestaurantContact(rs.getString(4));
                restaurant.setRestaurantEmail(rs.getString(5));
                restaurant.setRestaurantOwnerId(rs.getInt(6));
                restaurant.setOpensAt(rs.getString(7));
                restaurant.setClosesAt(rs.getString(8));
                restaurant.setGstNo(rs.getString(9));
                restaurant.setDiningAvailable(rs.getBoolean(10));
                restaurant.setDelete(rs.getBoolean(12));
                restaurant.setRestaurantImageUrl(rs.getString(11));

                restaurantAddress.setRestaurantAddLine1(rs.getString(18));
                restaurantAddress.setRestaurantAddLine2(rs.getString(19));
                restaurantAddress.setPincode(rs.getInt(22));

                city.setCityName(rs.getString(28));
                state.setStateName(rs.getString(35));

                restaurantAddress.setCity(city);
                restaurantAddress.setState(state);

                restaurant.setRestaurantAddress(restaurantAddress);
                restaurantList.add(restaurant);
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching restaurant details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching restaurant details", e);
        }
        return restaurantList;
    }
}
