package com.example.server.ResourcesObjects;

public class Address {

    private String city;
    private String street;
    private String buildingNum;

    public Address() {
    }

    public Address(String city, String street, String buildingNum) {
        this.city = city;
        this.street = street;
        this.buildingNum = buildingNum;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuildingNum() {
        return buildingNum;
    }

    public void setBuildingNum(String buildingNum) {
        this.buildingNum = buildingNum;
    }
}
