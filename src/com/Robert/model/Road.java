package com.Robert.model;

import com.Robert.config.Colours;

import java.util.List;

public class Road {

    private List<City> track;
    private Colours colour;

    public Road(List<City> track, Colours colour) {
        this.track = track;
        this.colour = colour;
    }

}
