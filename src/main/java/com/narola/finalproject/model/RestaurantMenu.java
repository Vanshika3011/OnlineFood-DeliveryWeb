package com.narola.finalproject.model;

import java.time.LocalDateTime;

public class RestaurantMenu implements Comparable<RestaurantMenu>{

    private int itemId;
    private String itemName;
    private int restaurantId;
    private int categoryId;
    private boolean isVeg;
    private double price;
    private String ingredients;
    private boolean availability;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int createdBy;
    private int updatedBy;
    private String foodType;
    private CuisineCategory cuisineCategory;
    private Restaurant restaurant;
    private String foodAvailability;
    private ItemsImage itemsImage;
    private int quantity;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isVeg() {
        return isVeg;
    }

    public void setVeg(boolean veg) {
        isVeg = veg;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public CuisineCategory getCuisineCategory() {
        return cuisineCategory;
    }

    public void setCuisineCategory(CuisineCategory cuisineCategory) {
        this.cuisineCategory = cuisineCategory;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getFoodAvailability() {
        return foodAvailability;
    }

    public void setFoodAvailability(String foodAvailability) {
        this.foodAvailability = foodAvailability;
    }

    public ItemsImage getItemsImage() {
        return itemsImage;
    }

    public void setItemsImage(ItemsImage itemsImage) {
        this.itemsImage = itemsImage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int compareTo(RestaurantMenu o) {
        return this.getItemName().compareTo(o.getItemName());
    }
}
