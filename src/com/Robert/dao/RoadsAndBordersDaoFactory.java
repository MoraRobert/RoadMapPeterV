package com.Robert.dao;

import com.Robert.dao.RoadsAndBordersDao;
import com.Robert.dao.impl.RoadsAndBordersDaoJdbcImpl;

public class RoadsAndBordersDaoFactory {

    public static RoadsAndBordersDao createJdbcDao() {

        return new RoadsAndBordersDaoJdbcImpl();
    }
}
