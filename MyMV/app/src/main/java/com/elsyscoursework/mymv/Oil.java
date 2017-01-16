package com.elsyscoursework.mymv;

import com.orm.SugarRecord;

/**
 * Created by Tomi on 16.1.2017 г..
 */

public class Oil extends SugarRecord {

    private int changedAt;
    private int nextChangeAt;

    public Oil() {

    }

    public Oil(int changedAt, int nextChangeAt) {
        this.changedAt = changedAt;
        this.nextChangeAt = nextChangeAt;
    }

    public int getChangedAt() {
        return changedAt;
    }

    public int getNextChangeAt() {
        return nextChangeAt;
    }

    public void setChangedAt(int changedAt) {
        this.changedAt = changedAt;
    }

    public void setNextChangeAt(int nextChangeAt) {
        this.nextChangeAt = nextChangeAt;
    }
}
