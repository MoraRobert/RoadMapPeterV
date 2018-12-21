package com.Robert.model;

public class Point {

    private int coordX;
    private int coordY;
    private String elementName;

    public Point(int coordX, int coordY, String elementName) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.elementName = elementName;
    }

    public Point(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public int getCoordX() {
        return coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    @Override
    public String toString() {
        return coordX + " " + coordY;
    }
}
