package com.narola.finalproject.dao;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.CuisineCategory;
import com.narola.finalproject.model.ItemsImage;
import com.narola.finalproject.model.Restaurant;
import com.narola.finalproject.model.RestaurantMenu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantMenuDao {

    public void addMenuItem(RestaurantMenu restaurantMenu, int ownerId, String imageUrl) throws DAOLayerException {
        try {
            String query = "Insert into `menu` ( item_name, restaurant_id, category_id, is_veg, price, ingredients, created_by, updated_by) " +
                    "values(?,?,?,?,?,?,?,?)";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, restaurantMenu.getItemName());
            stmt.setInt(2, restaurantMenu.getRestaurantId());
            stmt.setInt(3, restaurantMenu.getCategoryId());
            stmt.setBoolean(4, restaurantMenu.isVeg());
            stmt.setDouble(5, restaurantMenu.getPrice());
            stmt.setString(6, restaurantMenu.getIngredients());
            stmt.setInt(7, ownerId);
            stmt.setInt(8,ownerId);

            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            int generatedId = -1;
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
            }
            System.out.println(generatedId);
            addItemImage(generatedId, imageUrl, ownerId);

        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while inserting menu details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while inserting menu details", e);
        }
    }
    public void addItemImage(int itemId, String imageUrl, int ownerId) throws DAOLayerException {
        try {
            System.out.println(itemId);
            String query = "Insert into `items_image` (image_url, item_id, created_by, updated_by) values(?,?,?,?)";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1,imageUrl);
            stmt.setInt(2, itemId);
            stmt.setInt(3, ownerId);
            stmt.setInt(4, ownerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while inserting menu image ", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while inserting menu image ", e);
        }
    }

    public List<RestaurantMenu> getMenuDetails(int restaurantId) throws DAOLayerException {
        List<RestaurantMenu> restaurantMenuList = new ArrayList<>();
        try {
            String query = "Select m.item_id, m.item_name, m.category_id, m.is_veg, m.price, m.ingredients, m.availibility, c.cuisine_name, r.restaurant_name, m.restaurant_id, i.image_id, i.image_url from menu m  join items_image i on i.item_id = m.item_id join cuisine_category c on m.category_id = c.cuisine_category_id join restaurant r on r.id = m.restaurant_id where m.restaurant_id = ? and m.isDelete = 0";

            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, restaurantId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RestaurantMenu restaurantMenu = new RestaurantMenu();
                CuisineCategory cuisineCategory = new CuisineCategory();
                Restaurant restaurant = new Restaurant();
                ItemsImage itemsImage = new ItemsImage();

                restaurantMenu.setItemId(rs.getInt(1));
                restaurantMenu.setItemName(rs.getString(2));
                restaurantMenu.setRestaurantId(restaurantId);
                restaurantMenu.setCategoryId(rs.getInt(3));
                restaurantMenu.setVeg(rs.getBoolean(4));
                restaurantMenu.setPrice(rs.getDouble(5));
                restaurantMenu.setIngredients(rs.getString(6));
                restaurantMenu.setAvailability(rs.getBoolean(7));

                cuisineCategory.setCuisineName(rs.getString(8));
                restaurant.setRestaurantName(rs.getString(9));
                itemsImage.setImageUrl(rs.getString(12));
                itemsImage.setImageId(rs.getInt(11));

                restaurantMenu.setCuisineCategory(cuisineCategory);
                restaurantMenu.setRestaurant(restaurant);
                restaurantMenu.setItemsImage(itemsImage);

                restaurantMenuList.add(restaurantMenu);
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while retrieving menu details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while retrieving menu details", e);
        }
        return restaurantMenuList;
    }

    public void deleteMenuItem(int itemId) throws DAOLayerException {
        try {
            String query = "Update menu SET isDelete = 1 where item_id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, itemId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while deleting menu item", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while deleting menu item", e);
        }
    }

    public RestaurantMenu getMenuItemDetails(int itemId) throws DAOLayerException {
        RestaurantMenu restaurantMenu = new RestaurantMenu();
        ItemsImage itemsImage = new ItemsImage();
        try {
            String query = "Select m.item_id, m.item_name, m.category_id, m.is_veg, m.price, m.ingredients, m.availibility, m.restaurant_id, i.image_id, i.image_url from menu m  join items_image i on i.item_id = m.item_id where m.item_Id = ? and m.isDelete = 0";

            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, itemId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                restaurantMenu.setItemId(rs.getInt(1));
                restaurantMenu.setItemName(rs.getString(2));
                restaurantMenu.setRestaurantId(rs.getInt(8));
                restaurantMenu.setCategoryId(rs.getInt(3));
                restaurantMenu.setVeg(rs.getBoolean(4));
                restaurantMenu.setPrice(rs.getDouble(5));
                restaurantMenu.setIngredients(rs.getString(6));
                restaurantMenu.setAvailability(rs.getBoolean(7));

                itemsImage.setImageUrl(rs.getString(10));
                itemsImage.setImageId(rs.getInt(9));

                restaurantMenu.setItemsImage(itemsImage);
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while retrieving menu item details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while retrieving menu item details", e);
        }
        return restaurantMenu;
    }

    public void updateMenuItemDetails(RestaurantMenu restaurantMenu) throws DAOLayerException {
        try {
            String query = "Update menu SET  item_name = ?, category_id = ?, is_veg = ?, price = ?, ingredients = ?, availibility = ?, updated_at =default where item_id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, restaurantMenu.getItemName());
            stmt.setInt(2, restaurantMenu.getCategoryId());
            stmt.setBoolean(3,restaurantMenu.isVeg());
            stmt.setDouble(4,restaurantMenu.getPrice());
            stmt.setString(5,restaurantMenu.getIngredients());
            stmt.setBoolean(6,restaurantMenu.isAvailability());
            stmt.setInt(7,restaurantMenu.getItemId());

            stmt.executeUpdate();

            updateMenuItemImage(restaurantMenu);
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while updating item", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating item", e);
        }
    }

    public void updateMenuItemImage(RestaurantMenu restaurantMenu) throws DAOLayerException {
        try {
            String query = "Update items_image SET image_url=?,updated_at =default where item_id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, restaurantMenu.getItemsImage().getImageUrl());
            stmt.setInt(2, restaurantMenu.getItemId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while updating item image", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating item image", e);
        }
    }

    public List<RestaurantMenu> getAllMenuItems() throws DAOLayerException {
        List<RestaurantMenu> restaurantMenuList = new ArrayList<>();
        try {
            String query = "Select m.item_id, m.item_name, m.category_id, m.is_veg, m.price, m.ingredients, m.availibility, c.cuisine_name, r.restaurant_name ,m.restaurant_id, i.image_id, i.image_url   from menu m\n" +
                    "join cuisine_category c on m.category_id = c.cuisine_category_id\n" +
                    "join items_image i on i.item_id = m.item_id\n" +
                    "join restaurant r on r.id = m.restaurant_id\n" +
                    "where m.isDelete = 0";

            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RestaurantMenu restaurantMenu = new RestaurantMenu();
                CuisineCategory cuisineCategory = new CuisineCategory();
                Restaurant restaurant = new Restaurant();
                ItemsImage itemsImage = new ItemsImage();

                restaurantMenu.setItemId(rs.getInt(1));
                restaurantMenu.setItemName(rs.getString(2));
                restaurantMenu.setRestaurantId(rs.getInt(10));
                restaurantMenu.setCategoryId(rs.getInt(3));
                restaurantMenu.setVeg(rs.getBoolean(4));
                restaurantMenu.setPrice(rs.getDouble(5));
                restaurantMenu.setIngredients(rs.getString(6));
                restaurantMenu.setAvailability(rs.getBoolean(7));

                cuisineCategory.setCuisineName(rs.getString(8));
                restaurantMenu.setCuisineCategory(cuisineCategory);
                restaurant.setRestaurantName(rs.getString(9));
                itemsImage.setImageId(rs.getInt(11));
                itemsImage.setImageUrl(rs.getString(12));

                restaurantMenu.setItemsImage(itemsImage);

                restaurantMenu.setRestaurant(restaurant);
                restaurantMenuList.add(restaurantMenu);

            }
            return restaurantMenuList;
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while retrieving menu details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while retrieving menu details", e);
        }
    }

    public int getRestaurantIdForMenuItem(int itemId) throws DAOLayerException {
        int restId = 0;
        try {
            String query = "Select restaurant_id from menu where item_id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, itemId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                restId = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while retrieving menu details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while retrieving menu details", e);
        }
        return restId;
    }

    public int getPriceForMenuItem(int itemId) throws DAOLayerException {
        try {
            String query = "Select price from menu where item_id = ?";

            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, itemId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while retrieving menu details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while retrieving menu details", e);
        }
        return 0;
    }
}
