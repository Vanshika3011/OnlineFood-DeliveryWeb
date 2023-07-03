package com.narola.finalproject.dao;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.ItemsImage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemImageDao {

    public List<ItemsImage> getAllItemsImage() throws DAOLayerException {
        List<ItemsImage> itemsImageList = new ArrayList<>();
        try {
            String query = "Select * from items_image";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ItemsImage itemsImage = new ItemsImage();

                itemsImage.setImageId(rs.getInt(1));
                itemsImage.setImageUrl(rs.getString(2));
                itemsImage.setItemId(rs.getInt(3));

                itemsImageList.add(itemsImage);
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching items image details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching items image details", e);
        }
        return itemsImageList;
    }

    public ItemsImage getAllItemsImageById(int itemId) throws DAOLayerException {
        try {
            ItemsImage itemsImage = new ItemsImage();
            String query = "Select * from items_image where item_id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                itemsImage.setImageId(rs.getInt(1));
                itemsImage.setImageUrl(rs.getString(2));
                itemsImage.setItemId(rs.getInt(3));
            }
            return itemsImage;
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching items image details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching items image details", e);
        }
    }


}

