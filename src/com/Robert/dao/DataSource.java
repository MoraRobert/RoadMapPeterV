package com.Robert.dao;

import com.Robert.model.Point;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataSource {

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

    private Connection conn;

    public void open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }

        } catch (SQLException e) {
            System.out.println("Couldn't colose connection: " + e.getMessage());
        }
    }

    public List<Point> queryGraphElementPoints(String nameOfElement) {
        StringBuilder sb = new StringBuilder(QUERY_GRAPH_ELEMENT_POINTS);
        sb.append(nameOfElement);
        sb.append("\"");

        System.out.println("SQL Statement = " + sb.toString());

        try (   Statement statement = conn.createStatement();
                ResultSet results = statement.executeQuery((sb.toString()))) {

            List<Point> points = new ArrayList<>();
            while (results.next()) {
                Point X = new Point(results.getInt("coordX"), results.getInt("coordY"));
                points.add(X);
            }
            return points;
        } catch (SQLException e) {
            System.out.println("Query error: " + e.getMessage());
            return null;
        }
    }
}



















