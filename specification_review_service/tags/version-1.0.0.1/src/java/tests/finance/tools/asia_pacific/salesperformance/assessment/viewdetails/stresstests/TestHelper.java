/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package finance.tools.asia_pacific.salesperformance.assessment.viewdetails.stresstests;


import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * <p>
 * TestHelper class for the test.
 * </p>
 * <p>
 * Thread safe: This class has no state, and thus it is thread safe.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class TestHelper {
    /**
     * <p>
     * properties
     * </p>
     */
    public static final Properties properties = new Properties();
    static {
        /**
         * Initialize some static resources.
         */
        try {
            properties.load(new FileInputStream("test_files/stressConfig.properties"));
        } catch (IOException e) {
            throw new RuntimeException("error occurred during initializing", e);
        }
    }

    /**
     * <p>
     * This private constructor prevents to create a new instance.
     * </p>
     */
    private TestHelper() {
        // empty
    }

    /**
     * <p>
     * Obtain datasource.
     * </p>
     * 
     * @return the data source
     */
    public static BasicDataSource obtainDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(properties.getProperty("driverClassName"));
        dataSource.setUrl(properties.getProperty("db.url"));
        dataSource.setUsername(properties.getProperty("db.user"));
        dataSource.setPassword(properties.getProperty("db.password"));
        return dataSource;
    }

    /**
     * <p>
     * Sets the value of a private field in the given class.
     * </p>
     * 
     * @param instance
     *            the instance which the private field belongs to
     * @param name
     *            the name of the private field to be set
     * @param value
     *            the value to set
     * @param classType
     *            the class to get field
     * @throws RuntimeException
     *             if any error occurred when calling this method
     */
    public static void setPrivateField(Class<?> classType, Object instance, String name, Object value) {
        Field field = null;
        try {
            // get the reflection of the field
            field = classType.getDeclaredField(name);

            // set the field accessible
            field.setAccessible(true);

            // set the value
            field.set(instance, value);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred when calling setPrivateField method.", e);
        }
    }

}