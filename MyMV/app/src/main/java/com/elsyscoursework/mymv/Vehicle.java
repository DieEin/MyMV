package com.elsyscoursework.mymv;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by Tomi on 15.1.2017 г..
 */

public class Vehicle extends SugarRecord implements Serializable{

    private String type;
    private String manufacturer;
    private String model;

    public Vehicle() {

    }

    public Vehicle(String type, String manufacturer, String model) {
        this.type = type;
        this.manufacturer = manufacturer;
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }
}
