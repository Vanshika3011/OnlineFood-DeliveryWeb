package com.narola.finalproject.dao;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserAddressDao {

    public void doAddUserAddress(UserAddress userAddress) throws DAOLayerException {
        try {
            String query = "Insert into user_address(user_id, type, street1, street2, landmark, state_id, city_id, pincode, created_by, updated_by) values(?,?,?,?,?,?,?,?,?,?)";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1,userAddress.getUserId());
            stmt.setString(2, userAddress.getAddressType());
            stmt.setString(3,userAddress.getStreetLine1());
            stmt.setString(4,userAddress.getStreetLine2());
            stmt.setString(5,userAddress.getLandmark());
            stmt.setInt(6, userAddress.getStateId());
            stmt.setInt(7, userAddress.getCityId());
            stmt.setInt(8,userAddress.getPincode());
            stmt.setInt(9, userAddress.getUserId());
            stmt.setInt(10, userAddress.getUserId());

            stmt.executeUpdate();


        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while updating user address details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating user address details", e);
        }
    }

    public List<UserAddress> getUserAddressDetails(int userId) throws DAOLayerException {
        List<UserAddress> userAddressList = new ArrayList<>();
        try {
            String query = "Select u.user_id,u.type, u.street1, u.street2, u.landmark, u.state_id, u.city_id, u.pincode, c.city_name, s.state_name, u.user_address_id from user_address u join city c on u.city_id = c.id join state s on u.state_id = s.id where user_id = ? ";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }

            rs.previous();
            while (rs.next()) {
                UserAddress userAddress = new UserAddress();
                City city = new City();
                State state = new State();

                userAddress.setUserId(rs.getInt(1));
                userAddress.setAddressType(rs.getString(2));
                userAddress.setStreetLine1(rs.getString(3));
                userAddress.setStreetLine2(rs.getString(4));
                userAddress.setLandmark(rs.getString(5));
                userAddress.setPincode(rs.getInt(8));
                userAddress.setUserAddressId(rs.getInt(11));

                state.setStateId(6);
                state.setStateName(rs.getString(10));

                city.setCityId(rs.getInt(7));
                city.setCityName(rs.getString(9));

                userAddress.setCity(city);
                userAddress.setState(state);

                userAddressList.add(userAddress);
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching user address details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching user address details", e);
        }
        return userAddressList;
    }
}
