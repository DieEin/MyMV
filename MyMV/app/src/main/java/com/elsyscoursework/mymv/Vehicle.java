package com.elsyscoursework.mymv;

import com.orm.SugarRecord;

/**
 * Created by Tomi on 15.1.2017 г..
 */

public class Vehicle extends SugarRecord {

    String type;
    String manufacturer;
    String model;

    public Vehicle() {

    }

    public Vehicle(String type, String manufacturer, String model) {
        this.type = type;
        this.manufacturer = manufacturer;
        this.model = model;
    }
}