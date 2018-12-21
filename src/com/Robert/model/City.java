package com.Robert.model;

import java.awt.*;

public class City implements DrawableGeographicElement {

    private String name;
    private Point place;
    //    //private int countyId;

    public City(Point place, String name) {
        this.place = place;
        this.name = name;
    }

    public Point getPlace() {
        return place;
    }

    public void setPlace(Point place) {
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void draw(Graphics g) {

    }

//    public int getCountyId() {
//        return countyId;
//    }
//
//    public void setCountyId(int countyId) {
//        this.countyId = countyId;
//    }
}
