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
 * This class contains a convenience helper for inserting test data into the database, so that the user can see data in
 * the demo wep page.
 *
 * @author TCSDEVOPER
 * @version 1.0
 */
public class InsertTestDataDemoConvenienceHelper {
    /**
     * This method inserts the test data into the database.
     *
     * @param args are ignored
     */
    public static void main(String[] args) {
        try {
            System.out.println("Filling demo data into database");
            ConfigManager.getInstance().add("demoWebapp/res/Time_Tracker_Report.xml");
            DBConnectionFactoryImpl factory = new DBConnectionFactoryImpl(
                "com.cronos.timetracker.report.Informix");
            Connection jdbcConnection = factory.createConnection(
                "InformixConnection");
            System.out.println("database is = " + jdbcConnection.getMetaData().getURL());

            //write data
            DatabaseConnection databaseConnection = new DatabaseConnection(jdbcConnection);
            FlatXmlDataSet fullDataSet = new FlatXmlDataSet(
                InsertTestDataDemoConvenienceHelper.class.getClassLoader().getResourceAsStream("resources/full.xml"));
            DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, fullDataSet);
            System.out.println("Filled demo data into database");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}