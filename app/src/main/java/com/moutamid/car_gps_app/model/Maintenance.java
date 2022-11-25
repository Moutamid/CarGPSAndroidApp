package com.moutamid.car_gps_app.model;

public class Maintenance {

    private String id;
    private String name;
    private String title;
    private String date;
    private String startDate;
    private String endDate;
    private String startKm;
    private String endKm;
    private String price;

    public Maintenance(){

    }

    public Maintenance(String id, String name, String title, String date, String startDate, String endDate, String startKm, String endKm, String price) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.date = date;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startKm = startKm;
        this.endKm = endKm;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartKm() {
        return startKm;
    }

    public void setStartKm(String startKm) {
        this.startKm = startKm;
    }

    public String getEndKm() {
        return endKm;
    }

    public void setEndKm(String endKm) {
        this.endKm = endKm;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
