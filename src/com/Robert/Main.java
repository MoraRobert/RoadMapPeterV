package com.Robert;

import com.Robert.dao.DataSource;
import com.Robert.dao.RoadsAndBordersDao;
import com.Robert.dao.RoadsAndBordersDaoFactory;
import com.Robert.dao.impl.RoadsAndBordersDaoJdbcImpl;
import com.Robert.model.City;
import com.Robert.model.Point;

import java.awt.geom.Line2D;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.awt.*;
import javax.swing.*;

public class Main {

    public static final String DB_NAME = "citiesRoadsCounties.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\Robi\\IdeaProjects\\RoadMapPeterV\\" + DB_NAME;

    public static final String TABLE_POINTS = "Points";
    public static final String TABLE_CITIES = "Cities";
    public static final String TABLE_ROADS = "Roads";
    public static final String TABLE_COUNTIES = "Counties";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_COORDX = "coordX";
    public static final String COLUMN_COORDY = "coordY";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_NAME_OF_ELEMENT = "nameOfElement"; //City_Road_or_County_name in Points table = foreign key

    public static final RoadsAndBordersDao ourCountyRoadmapJdbc = RoadsAndBordersDaoFactory.createJdbcDao();

    public static void main(String[] args) {

        try {
            ourCountyRoadmapJdbc.insertPoint(88, 155, "A56");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ourCountyRoadmapJdbc.close(); //TODO: continue from here!


        try (Connection conn = DriverManager
                .getConnection(CONNECTION_STRING);
             //conn.setAutoCommit(false);
            Statement statement = conn.createStatement()) {

            statement.execute("DROP TABLE IF EXISTS " + TABLE_POINTS);
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_POINTS +
                    "(" +   COLUMN_COORDX + " INTEGER, " +
                            COLUMN_COORDY + " INTEGER, " +
                    COLUMN_NAME_OF_ELEMENT + " TEXT" +
                    ")");
            statement.execute("DROP TABLE IF EXISTS " + TABLE_CITIES);
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_CITIES +
                    "(name TEXT)");
            statement.execute("DROP TABLE IF EXISTS " + TABLE_ROADS);
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_ROADS +
                    "(name TEXT, colour TEXT)");
            statement.execute("DROP TABLE IF EXISTS " + TABLE_COUNTIES);
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_COUNTIES +
                    "(name TEXT)");



            insertPoint(statement, 130,50, "Kualalumpur" );
            insertPoint(statement, 140,190, "Beyrut");
            insertPoint(statement, 600,70, "Budapest");

            insertPoint(statement, 130,50, "A56");
            insertPoint(statement, 140,190, "A56");
            insertPoint(statement, 130,50, "B99");
            insertPoint(statement, 600,70, "B99");
            insertPoint(statement, 140,190, "B99");

            insertPoint(statement, 100,100, "Popocatapetl");
            insertPoint(statement, 110,0, "Popocatapetl");
            insertPoint(statement, 900,40, "Popocatapetl");
            insertPoint(statement, 400,350, "Popocatapetl");
            insertPoint(statement, 0,200, "Popocatapetl");

            statement.execute("INSERT INTO Cities (name)" +
                    "VALUES('Kualalumpur')");
            statement.execute("INSERT INTO Cities (name)" +
                    "VALUES('Beyrut')");
            statement.execute("INSERT INTO Cities (name)" +
                    "VALUES('Budapest')");
            statement.execute("INSERT INTO Roads (name, colour)" +
                    "VALUES('A56', 'WHITE' )");
            statement.execute("INSERT INTO Roads (name, colour)" +
                    "VALUES('B99', 'RED' )");
            statement.execute("INSERT INTO Counties (name)" +
                    "VALUES('Popocatapetl')");

//            statement.execute("UPDATE Points SET coordX= 11 WHERE nameOfElement='B99' AND coordY= 7");
//            statement.execute("DELETE FROM Points WHERE nameOfElement='B99'");
//
            statement.execute("SELECT * FROM Points");
            ResultSet results = statement.getResultSet();
//            ResultSet results = statement.executeQuery("SELECT * FROM People");
            while (results.next()) {
                System.out.println(results.getInt("coordX") + " " +
                        results.getInt("coordY") + " " +
                        results.getString("nameOfElement"));
            }
            results.close();

//            statement.close();
//            conn.close();

        } catch (SQLException e) {
            System.out.println("Something went wrong with the connection. " + e.getMessage());
        }



        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                LinesEx ex = new LinesEx();
                ex.setVisible(true);
            }
        });


    }

    private static void insertPoint(Statement statement, int coordX, int coordY, String nameOfElement) throws SQLException {

        statement.execute("INSERT INTO " + TABLE_POINTS +
                "(" +   COLUMN_COORDX + ", " +
                COLUMN_COORDY + ", " +
                COLUMN_NAME_OF_ELEMENT +
                " )" +
                "VALUES('" + coordX + "', " + coordY + ", '" + nameOfElement + "')");
    }

    static class Surface extends JPanel {

        private void doDrawing(Graphics g) {

            Graphics2D g2d = (Graphics2D) g;

            DataSource dataSource = new DataSource();
            dataSource.open();
            //List<Point> graphOnTheMap = dataSource.queryGraphElementPoints("B99");

            RoadsAndBordersDao ourCountyRoadmapJdbc = RoadsAndBordersDaoFactory.createJdbcDao();

            drawRoads(g2d, dataSource, ourCountyRoadmapJdbc);
            drawCities(g2d, ourCountyRoadmapJdbc);
            drawCounties(g2d, dataSource);

            dataSource.close();

//            graphOnTheMap = dataSource.queryGraphElementPoints("Budapest");
//            Point city = new Point(graphOnTheMap.get(0).getCoordX(), graphOnTheMap.get(0).getCoordY());
//            g2d.drawOval(city.getCoordX(),city.getCoordY(),10,10);
//            g2d.drawString("Budapest", city.getCoordX(), city.getCoordY());
//
//            graphOnTheMap = dataSource.queryGraphElementPoints("Kualalumpur");
//            city = new Point(graphOnTheMap.get(0).getCoordX(), graphOnTheMap.get(0).getCoordY(),
//                    graphOnTheMap.get(0).getElementName());
//            g2d.drawOval(city.getCoordX(),city.getCoordY(),10,10);
//            g2d.drawString("Kualalumpur", city.getCoordX(), city.getCoordY());
//
//            graphOnTheMap = dataSource.queryGraphElementPoints("Beyrut");
//            city = new Point(graphOnTheMap.get(0).getCoordX(), graphOnTheMap.get(0).getCoordY());
//            g2d.drawOval(city.getCoordX(),city.getCoordY(),10,10);
//            g2d.drawString("Beyrout", city.getCoordX(), city.getCoordY());

        }

        private void drawRoads(Graphics2D g2d, DataSource dataSource, RoadsAndBordersDao ourCountyRoadmapJdbc) {
            List<Point> graphOnTheMap = ourCountyRoadmapJdbc.queryGraphElementPoints("B99");

            g2d.setColor(Color.RED);
            for (int i = 0; i < graphOnTheMap.size() - 1; i++) {
                Point pointS = new Point(graphOnTheMap.get(i).getCoordX(), graphOnTheMap.get(i).getCoordY());
                Point pointE = new Point(graphOnTheMap.get(i+1).getCoordX(), graphOnTheMap.get(i+1).getCoordY());
                g2d.drawLine(pointS.getCoordX(),pointS.getCoordY(),pointE.getCoordX(),pointE.getCoordY());
            }

            graphOnTheMap = dataSource.queryGraphElementPoints("A56");

            g2d.setColor(Color.BLUE);
            for (int i = 0; i < graphOnTheMap.size() - 1; i++) {
                Point pointS = new Point(graphOnTheMap.get(i).getCoordX(), graphOnTheMap.get(i).getCoordY());
                Point pointE = new Point(graphOnTheMap.get(i+1).getCoordX(), graphOnTheMap.get(i+1).getCoordY());
                g2d.drawLine(pointS.getCoordX(),pointS.getCoordY(),pointE.getCoordX(),pointE.getCoordY());
            }
        }

        private void drawCounties(Graphics2D g2d, DataSource dataSource) {
            List<Point> graphOnTheMap;
            graphOnTheMap = dataSource.queryGraphElementPoints("Popocatapetl");
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(10));
            for (int i = 0; i < graphOnTheMap.size() - 1; i++) {
                Point pointS = new Point(graphOnTheMap.get(i).getCoordX(), graphOnTheMap.get(i).getCoordY());
                Point pointE = new Point(graphOnTheMap.get(i+1).getCoordX(), graphOnTheMap.get(i+1).getCoordY());
                g2d.drawLine(pointS.getCoordX(),pointS.getCoordY(),pointE.getCoordX(),pointE.getCoordY());
            }
            Point pointL = new Point(graphOnTheMap.get(graphOnTheMap.size()-1).getCoordX(),
                    graphOnTheMap.get(graphOnTheMap.size()-1).getCoordY());
            Point point0 = new Point(graphOnTheMap.get(0).getCoordX(),
                    graphOnTheMap.get(0).getCoordY());
            g2d.drawLine(pointL.getCoordX(), pointL.getCoordY(), point0.getCoordX(), point0.getCoordY());
        }

        private void drawCities(Graphics2D g2d, RoadsAndBordersDao ourCountyRoadmapJdbc) {
            List<Point> graphOnTheMap;
            g2d.setColor(Color.BLACK);
            List<String> cities = ourCountyRoadmapJdbc.queryTablesOfCitiesRoadsCounties("Cities");
            for (String cityToDraw : cities) {
                graphOnTheMap = ourCountyRoadmapJdbc.queryGraphElementPoints(cityToDraw);
                Point city = new Point(graphOnTheMap.get(0).getCoordX(), graphOnTheMap.get(0).getCoordY());
                g2d.drawOval(city.getCoordX(),city.getCoordY(),10,10);
                g2d.drawString(cityToDraw, city.getCoordX(), city.getCoordY());
            }
        }

        @Override
        public void paintComponent(Graphics g) {

            super.paintComponent(g);
            doDrawing(g);
        }
    }

    public static class LinesEx extends JFrame {

        public LinesEx() {

            initUI();
        }

        private void initUI() {

            add(new Surface());

            setTitle("OurCounty");
            setSize(350, 250);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

    }
}
