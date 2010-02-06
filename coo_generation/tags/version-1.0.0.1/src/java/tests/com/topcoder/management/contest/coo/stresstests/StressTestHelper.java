/**
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.contest.coo.stresstests;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * <p>
 * This is the helper class used by stress tests.
 * </p>
 *
 * @author yuanyeyuanye
 * @version 1.0
 */
public class StressTestHelper {

    /**
     * <p>
     * Project id used for stress test cases.
     * </p>
     */
    public static final int TEST_PROJECT_ID;

    static {
        Properties config = new Properties();
        InputStream configInput = null;
        try {
            configInput = new FileInputStream("test_files/stress/stress_configuration.properties");
            config.load(configInput);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (configInput != null) {
                try {
                    configInput.close();
                } catch (IOException e) {
                    // Ignore.
                }
            }
        }

        TEST_PROJECT_ID = Integer.parseInt(config.getProperty("TEST_PROJECT_ID"));
    }

    /**
     * <p>
     * Sets the value of a private field in the given class.
     * </p>
     *
     * @param type the class which the private field belongs to
     * @param instance the instance which the private field belongs to
     * @param name the name of the private field to be set
     * @param value the value to set
     */
    public static void setPrivateField(Class<?> type, Object instance, String name, Object value) {
        Field field = null;

        try {
            // get the reflection of the field
            field = type.getDeclaredField(name);

            // set the field accessible
            field.setAccessible(true);

            // set the value
            field.set(instance, value);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // reset the accessibility
                field.setAccessible(false);
            }
        }
    }

    /**
     * Gets the value of a private field in the given class. The field has the given name. The value is
     * retrieved from the given instance. If the instance is null, the field is a static field. If any error
     * occurs, null is returned.
     *
     * @param type the class which the private field belongs to
     * @param instance the instance which the private field belongs to
     * @param name the name of the private field to be retrieved
     * @return the value of the private field
     */
    public static Object getPrivateField(Class<?> type, Object instance, String name) {
        Field field = null;
        Object obj = null;
        try {
            // get the reflection of the field
            field = type.getDeclaredField(name);

            // set the field accessible.
            field.setAccessible(true);

            // get the value
            obj = field.get(instance);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // reset the accessibility
                field.setAccessible(false);
            }
        }
        return obj;
    }
}
