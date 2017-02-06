package com.elsyscoursework.mymv;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tomi on 6.2.2017 г..
 */

public class WholeVehicle implements Serializable {

    Vehicle vehicle;
    History history;
    Oil oil;
    List<Maintenance> maintenance;

    public WholeVehicle(Vehicle vehicle, History history, Oil oil, List<Maintenance> maintenance) {
        this.vehicle = vehicle;
        this.history = history;
        this.oil = oil;
        this.maintenance = maintenance;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public Oil getOil() {
        return oil;
    }

    public void setOil(Oil oil) {
        this.oil = oil;
    }

    public List<Maintenance> getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(List<Maintenance> maintenance) {
        this.maintenance = maintenance;
    }
}
