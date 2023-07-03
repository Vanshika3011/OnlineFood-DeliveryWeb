package com.narola.finalproject.dao;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.CuisineCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CuisineCategoryDao {

    public List<CuisineCategory> getCategory() throws DAOLayerException {
        List<CuisineCategory> cuisineCategoryList = new ArrayList<>();
        try {
            String query = "Select * from cuisine_category where isDelete = 0";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CuisineCategory cuisineCategory = new CuisineCategory();
                cuisineCategory.setCuisineId(rs.getInt(1));
                cuisineCategory.setCuisineName(rs.getString(2));
                cuisineCategory.setCuisineImageURL(rs.getString(3));

                cuisineCategoryList.add(cuisineCategory);
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching cuisine details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching cuisine details", e);
        }
        return cuisineCategoryList;
    }

    public void addCuisineCategory(String cuisineName, String filePath) throws DAOLayerException{
        try{
            String query = "Insert into cuisine_category(cuisine_name, cuisine_img_url) values(?,?)";
            Connection conn = DBConnection.getInstance().getConnection();

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,cuisineName);
            stmt.setString(2,filePath);
            stmt.executeUpdate();

        }catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while updating cuisine details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating cuisine details", e);
        }
    }

    public boolean isCuisineExist(String cuisineName) throws DAOLayerException {
        try {
            int count = 0;
            String query = "select count(*) from cuisine_category where cuisine_name = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, cuisineName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return count > 0;
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while verifying cuisine details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while verifying cuisine details", e);
        }
    }

    public void deleteCuisineItem(int cuisineId) throws DAOLayerException {
        try {
            String query = "Update cuisine_category SET isDelete = 1 where cuisine_category_id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, cuisineId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while deleting cuisine item", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while deleting cuisine item", e);
        }
    }

    public void updateCuisineItem(int cuisineId,String cuisineName, String filePath) throws DAOLayerException {
        try {
            String query = "Update cuisine_category SET cuisine_name = ?, cuisine_img_url = ? where cuisine_category_id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, cuisineName);
            stmt.setString(2, filePath);
            stmt.setInt(3, cuisineId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while updating cuisine item", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating cuisine item", e);
        }
    }

    public CuisineCategory getCuisineDetails(int cuisineId) throws DAOLayerException {
        CuisineCategory cuisineCategory = new CuisineCategory();
        try {
            String query = "select * from cuisine_category where cuisine_category_id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query,  ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.setInt(1, cuisineId);
            ResultSet rs = stmt.executeQuery();

            rs.previous();
            while (rs.next()) {
                cuisineCategory.setCuisineId(cuisineId);
                cuisineCategory.setCuisineName(rs.getString(2));
                cuisineCategory.setCuisineImageURL(rs.getString(3));
            }
            return cuisineCategory;
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching cuisine details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching cuisine details", e);
        }
    }
}
