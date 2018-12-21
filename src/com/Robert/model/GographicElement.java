package com.Robert.model;

abstract class GeographicElement {

    private String name;

    public GeographicElement(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }
}
