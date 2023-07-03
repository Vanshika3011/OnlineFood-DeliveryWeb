package com.narola.finalproject.dao;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.Restaurant;
import com.narola.finalproject.model.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDao {

    public List<UserRole> getAllUserRoles() throws DAOLayerException {
        List<UserRole> userRoleList = new ArrayList<>();
        try {
            String query = "Select * from user_role";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                UserRole userRole = new UserRole();

                userRole.setUserRoleId(rs.getInt(1));
                userRole.setUserRoleName(rs.getString(2));
                userRoleList.add(userRole);
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching user roles details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching user roles details", e);
        }
        return userRoleList;
    }
}
