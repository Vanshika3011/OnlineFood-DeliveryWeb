package com.narola.finalproject.model;

import java.time.LocalDateTime;

public class RestaurantAddress {
    private int restaurantAddressId;
    private String restaurantAddLine1;
    private String restaurantAddLine2;
    private int cityId;
    private int stateId;
    private int pincode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int createdBy;
    private int updatedBy;
    private City city;
    private State state;

    public int getRestaurantAddressId() {
        return restaurantAddressId;
    }

    public void setRestaurantAddressId(int restaurantAddressId) {
        this.restaurantAddressId = restaurantAddressId;
    }

    public String getRestaurantAddLine1() {
        return restaurantAddLine1;
    }

    public void setRestaurantAddLine1(String restaurantAddLine1) {
        this.restaurantAddLine1 = restaurantAddLine1;
    }

    public String getRestaurantAddLine2() {
        return restaurantAddLine2;
    }

    public void setRestaurantAddLine2(String restaurantAddLine2) {
        this.restaurantAddLine2 = restaurantAddLine2;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
