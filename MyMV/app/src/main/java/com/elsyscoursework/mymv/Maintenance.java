package com.elsyscoursework.mymv;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by Tomi on 6.2.2017 г..
 */

public class Maintenance extends SugarRecord implements Serializable {

    private String type;
    private String name;
    private String date;
    private int price;
    private int vehicleId;

    public Maintenance(String type, String name, String date, int price, int vehicleId) {
        this.type = type;
        this.name = name;
        this.date = date;
        this.price = price;
        this.vehicleId = vehicleId;
    }

    public Maintenance() {

    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }
}
