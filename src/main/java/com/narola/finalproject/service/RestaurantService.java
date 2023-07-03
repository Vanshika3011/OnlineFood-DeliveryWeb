package com.narola.finalproject.service;

import com.narola.finalproject.dao.RestaurantDao;
import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.Restaurant;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RestaurantService {
    private RestaurantDao restaurantDao = new RestaurantDao();

    public List<Restaurant> getRestaurantList() throws DAOLayerException {
        List<Restaurant> restaurantList = restaurantDao.getAllRestaurants();
        return restaurantList;
    }

    public Restaurant getRestaurantDetails(int ownerId) throws DAOLayerException {
        Restaurant restaurant = restaurantDao.getAllRestaurantsDetailsByOwnerID(ownerId);
        return restaurant;
    }

    public Restaurant getRestaurantDetailsByRestaurantId(int restaurantId) throws DAOLayerException {
        Restaurant restaurant = restaurantDao.getAllRestaurantsDetailsByRestaurantID(restaurantId);
        return restaurant;
    }

    public List<Restaurant> getRestaurantsByCategory(int cuisineId) throws DAOLayerException {
        List<Restaurant> restaurantList = restaurantDao.getAllRestaurantsByCategory(cuisineId);

        Set<Restaurant> restaurants = new HashSet<>();
        restaurants.addAll(restaurantList);
        restaurantList.clear();
        restaurantList.addAll(restaurants);

        return restaurantList;
    }

    public boolean doesUserRestaurantExists(int ownerId) throws DAOLayerException {
        return restaurantDao.doesUserOwnRestaurant(ownerId);
    }

    public void addRestaurant(Restaurant restaurant) throws DAOLayerException {
        restaurantDao.doAddRestaurantAndAddressDetails(restaurant);
    }

    public void editRestaurantDetails(Restaurant restaurant, int restaurantId) throws DAOLayerException {
        restaurantDao.editRestaurantDetails(restaurant, restaurantId);
    }

    public void deleteRestaurant(int restaurantId) throws DAOLayerException {
        restaurantDao.deleteRestaurant(restaurantId);
    }

    public int getOwnerId(int restaurantId) throws DAOLayerException {
        return restaurantDao.getOwnerIdOfRestaurant(restaurantId);
    }
}
