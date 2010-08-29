/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package finance.tools.asia_pacific.salesperformance.assessment.viewdetails.failuretests;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * Failure test helper class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class FailureTestHelper {
    /**
     * <p>
     * Helper method to get a data source to be used in the tests.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    public static void setInvalidDataSource() throws Exception {
        Properties prop = loadProperties();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(prop.getProperty("driverClassName"));
        dataSource.setUrl(prop.getProperty("db.url"));
        dataSource.setUsername(prop.getProperty("db.userid"));
        dataSource.setPassword(prop.getProperty("db.password") + "_invalid");
    }

    /**
     * Load properties from file.
     *
     * @throws Exception
     *             to jUnit.
     */
    private static Properties loadProperties() throws Exception {
        Properties prop = new Properties();
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("test_files/failure/config.properties");
            prop.load(inputStream);
        } finally {
            inputStream.close();
        }

        return prop;
    }
}