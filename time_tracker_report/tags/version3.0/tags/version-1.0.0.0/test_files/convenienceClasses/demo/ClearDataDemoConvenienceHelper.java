/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package demo;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;

import java.sql.Connection;


/**
 * This class contains a convenience helper for clearing all data from the database, after the demo.
 *
 * @author TCSDEVOPER
 * @version 1.0
 */
public class ClearDataDemoConvenienceHelper {
    /**
     * This method clears all data from the database.
     *
     * @param args are ignored
     */
    public static void main(String[] args) {
        try {
            System.out.println("clearing all data from database");
            ConfigManager.getInstance().add("demoWebapp/res/Time_Tracker_Report.xml");
            DBConnectionFactoryImpl factory = new DBConnectionFactoryImpl(
                "com.topcoder.timetracker.report.Informix");
            Connection jdbcConnection = factory.createConnection("InformixConnection");
            System.out.println("database is = " + jdbcConnection.getMetaData().getURL());
            //clear data
            DatabaseConnection databaseConnection = new DatabaseConnection(jdbcConnection);
            FlatXmlDataSet fullDataSet = new FlatXmlDataSet(
                ClearDataDemoConvenienceHelper.class.getClassLoader().getResourceAsStream("resources/full.xml"));
            DatabaseOperation.DELETE_ALL.execute(databaseConnection, fullDataSet);
            System.out.println("Cleared all data from database");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}