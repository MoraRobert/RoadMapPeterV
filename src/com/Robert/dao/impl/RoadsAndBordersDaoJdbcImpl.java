package com.Robert.dao.impl;

import com.Robert.dao.RoadsAndBordersDao;
import com.Robert.model.City;
import com.Robert.model.County;
import com.Robert.model.Point;
import com.Robert.model.Road;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoadsAndBordersDaoJdbcImpl implements RoadsAndBordersDao{

    private Connection conn;
    private List<City> cities = null;
    private List<Road> roads = null;
    private List<County> counties = null;

    public static final String DB_NAME = "citiesRoadsCounties.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\Robi\\IdeaProjects\\RoadMapPeterV\\" + DB_NAME;

    public static final String TABLE_POINTS = "Points";
    public static final String TABLE_CITIES = "Cities";
    public static final String TABLE_ROADS = "Roads";
    public static final String TABLE_COUNTIES = "Counties";

    public static final String COLUMN_COORDX = "coordX";
    public static final String COLUMN_COORDY = "coordY";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_NAME_OF_ELEMENT = "nameOfElement";

    public static final String QUERY_GRAPH_ELEMENT_POINTS =
            "SELECT " + COLUMN_COORDX + ", " + COLUMN_COORDY +
                    " FROM " + TABLE_POINTS +
                    " WHERE " + COLUMN_NAME_OF_ELEMENT + " = \"";

    public static final String QUERY_TABLE_CITIES_ROADS_COUNTIES =
            "SELECT " + COLUMN_NAME +
                    " FROM " + " \'";

    public RoadsAndBordersDaoJdbcImpl() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
        }
    }

    @Override
    public void createTable() {

    }

    @Override
    public void insertPoint(int coordX, int coordY, String nameOfElement) {

        try {
            conn.prepareStatement("INSERT INTO " + TABLE_POINTS +
                    "(" +   COLUMN_COORDX + ", " +
                    COLUMN_COORDY + ", " +
                    COLUMN_NAME_OF_ELEMENT +
                    " )" +
                    "VALUES('" + coordX + "', " + coordY + ", '" + nameOfElement + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Point> queryGraphElementPoints(String nameOfElement) {
        StringBuilder sb = new StringBuilder(QUERY_GRAPH_ELEMENT_POINTS);
        sb.append(nameOfElement);
        sb.append("\"");

        System.out.println("SQL Statement = " + sb.toString());

        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery((sb.toString()))) {

            List<Point> points = new ArrayList<>();
            while (results.next()) {
                Point pointToDraw = new Point(results.getInt("coordX"), results.getInt("coordY"));
                points.add(pointToDraw);
            }
            return points;
        } catch (SQLException e) {
            System.out.println("Query error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<String> queryTablesOfCitiesRoadsCounties(String nameOfTable) {
        StringBuilder sb = new StringBuilder(QUERY_TABLE_CITIES_ROADS_COUNTIES);
        sb.append(nameOfTable);
        sb.append("'");

        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery((sb.toString()))) {

            List<String> tableContent = new ArrayList<>();
            while (results.next()) {
                tableContent.add(results.getString("name"));
            }
            return tableContent;
        } catch (SQLException e) {
            System.out.println("Query error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void close() {
        try {
            conn.close();
        } catch (SQLException ex) {
            throw new IllegalStateException("Couldn't close connection", ex);
        }
    }
}
