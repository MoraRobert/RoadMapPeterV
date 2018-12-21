package com.Robert.model;

import java.util.List;

public class County {

    private int id;
    private String name;
    private List<Point> border;

    public County(String name, List<Point> border) {
        this.name = name;
        this.border = border;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Point> getBorder() {
        return border;
    }

    public void setBorder(List<Point> border) {
        this.border = border;
    }
}
