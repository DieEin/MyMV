package com.elsyscoursework.mymv;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by Tomi on 16.1.2017 г..
 */

public class History extends SugarRecord implements Serializable {

    private String owner;
    private int productionYear;
    private int previousOwners;
    private int kilometerage;

    public History() {

    }

    public History(String owner, int productionYear, int previousOwners, int kilometerage) {
        this.owner = owner;
        this.productionYear = productionYear;
        this.previousOwners = previousOwners;
        this.kilometerage = kilometerage;
    }

    public String getOwner() {
        return owner;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public int getPreviousOwners() {
        return previousOwners;
    }

    public int getKilometerage() {
        return kilometerage;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public void setPreviousOwners(int previousOwners) {
        this.previousOwners = previousOwners;
    }

    public void setKilometerage(int kilometerage) {
        this.kilometerage = kilometerage;
    }
}
