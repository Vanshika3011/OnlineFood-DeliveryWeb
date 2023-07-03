package com.narola.finalproject.service.SortSpecification;

import com.narola.finalproject.model.RestaurantMenu;

import java.util.Comparator;

public class RestaurantMenuSortSpecification {

    public static class SortByPriceAsc implements Comparator<RestaurantMenu> {
        @Override
        public int compare(RestaurantMenu item1, RestaurantMenu item2) {
            return Double.compare(item1.getPrice(), item2.getPrice());
        }
    }

    public static class SortByPriceDesc implements Comparator<RestaurantMenu> {
        @Override
        public int compare(RestaurantMenu item1, RestaurantMenu item2) {
            return Double.compare(item2.getPrice(), item1.getPrice());
        }
    }

    public static class SortByLatestAdded implements Comparator<RestaurantMenu> {
        @Override
        public int compare(RestaurantMenu item1, RestaurantMenu item2) {
            return (item2.getItemId() - item1.getItemId());
        }
    }

//    list by restaurant
    public static class SortByRestaurantId implements Comparator<RestaurantMenu> {
        @Override
        public int compare(RestaurantMenu item1, RestaurantMenu item2) {
            return (item1.getRestaurantId() - item2.getRestaurantId());
        }
    }

//    Veg/Non-veg
    public static class SortByFoodType implements Comparator<RestaurantMenu> {
        @Override
        public int compare(RestaurantMenu item1, RestaurantMenu item2) {
            return item1.isVeg()?-1:1;
        }

    }

}
