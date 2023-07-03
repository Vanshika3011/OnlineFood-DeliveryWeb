package com.narola.finalproject.service;

import com.narola.finalproject.dao.RestaurantMenuDao;
import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.RestaurantMenu;
import com.narola.finalproject.service.SortSpecification.RestaurantMenuSortSpecification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class RestaurantMenuService {
    private RestaurantMenuDao restaurantMenuDao = new RestaurantMenuDao();

    public List<RestaurantMenu> getRestaurantMenu(int restaurantId) throws DAOLayerException {
        List<RestaurantMenu> restaurantMenuList = restaurantMenuDao.getMenuDetails(restaurantId);
        return restaurantMenuList;
    }

    public RestaurantMenu getMenuItemDetails(int itemId) throws DAOLayerException {
        RestaurantMenu restaurantMenu = restaurantMenuDao.getMenuItemDetails(itemId);
        return restaurantMenu;
    }

    public List<RestaurantMenu> getAllMenuItems() throws DAOLayerException {
        List<RestaurantMenu> restaurantMenus = restaurantMenuDao.getAllMenuItems();
        return restaurantMenus;
    }

    public void editMenuItems(RestaurantMenu restaurantMenu) throws DAOLayerException {
        restaurantMenuDao.updateMenuItemDetails(restaurantMenu);
    }

    public void addMenuItems(RestaurantMenu restaurantMenu, int ownerId, String imageUrl) throws DAOLayerException {
        restaurantMenuDao.addMenuItem(restaurantMenu, ownerId, imageUrl);
    }

    public void deleteMenuItem(int itemId) throws DAOLayerException {
        restaurantMenuDao.deleteMenuItem(itemId);
    }

    public List<RestaurantMenu> getMenuListSorted(List<RestaurantMenu> restaurantMenuList, String sortOption) {
        if(sortOption == null){
            return restaurantMenuList;
        }
        switch (sortOption) {
            case "nameAsc":
                Collections.sort(restaurantMenuList);
                break;
            case "nameDesc":
                Collections.sort(restaurantMenuList, Comparator.reverseOrder());
                break;
            case "priceLow":
                Collections.sort(restaurantMenuList, new RestaurantMenuSortSpecification.SortByPriceAsc());
                break;
            case "priceHigh":
                Collections.sort(restaurantMenuList, new RestaurantMenuSortSpecification.SortByPriceDesc());
                break;
            case "latest":
                Collections.sort(restaurantMenuList, new RestaurantMenuSortSpecification.SortByLatestAdded());
                break;
            default:
                return restaurantMenuList;
        }
        return restaurantMenuList;
    }

    public List<RestaurantMenu> getItemsByCuisine(List<RestaurantMenu> menuList, int cuisineId) {
        List<RestaurantMenu> filteredList = new ArrayList<>();

        for (RestaurantMenu menu : menuList) {
            if (cuisineId != 0 && menu.getCategoryId() != cuisineId) {
                continue;
            }
            filteredList.add(menu);
        }

        return filteredList;
    }

    public List<RestaurantMenu> getItemsByCategoryId(List<RestaurantMenu> menuList, int cuisineId) {
        if (cuisineId == 0) {
            return menuList;
        } else {
            List<RestaurantMenu> filteredList = new ArrayList<>();

            for (RestaurantMenu menu : menuList) {
                if (cuisineId == menu.getCategoryId()) {
                    filteredList.add(menu);
                }
            }
            return filteredList;
        }
    }

    public List<RestaurantMenu> getItemsByVegType(List<RestaurantMenu> menuList, String foodType) {
        if (foodType.trim() == "all") {
            return menuList;
        } else {
            List<RestaurantMenu> filteredList = new ArrayList<>();

            for (RestaurantMenu menu : menuList) {
                if (menu.isVeg() == Boolean.parseBoolean(foodType)) {
                    filteredList.add(menu);
                }
            }
            return filteredList;
        }
    }

    public List<RestaurantMenu> getItemsByAvailability(List<RestaurantMenu> menuList, String isAvailable) {
        if (isAvailable.trim() == "all") {
            return menuList;
        } else {
            List<RestaurantMenu> filteredList = new ArrayList<>();
            for (RestaurantMenu menu : menuList) {
                if (menu.isAvailability() == Boolean.parseBoolean(isAvailable)) {
                    filteredList.add(menu);
                }
            }
            return filteredList;
        }
    }

}
