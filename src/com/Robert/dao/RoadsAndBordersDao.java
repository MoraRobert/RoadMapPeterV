package com.Robert.dao;

import com.Robert.model.Point;

import java.sql.SQLException;
import java.util.List;

public interface RoadsAndBordersDao {

    void createTable();
    void insertPoint(int coordX, int coordY, String nameOfElement) throws SQLException;
    List<Point> queryGraphElementPoints(String nameOfElement);
    List<String> queryTablesOfCitiesRoadsCounties(String nameOfTable);
    void close();

}
