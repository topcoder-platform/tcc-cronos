/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.failuretests;

import java.io.File;
import java.lang.reflect.Field;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.XMLFilePersistence;

/**
 * Defines utilities used in failure test cases.
 * 
 * @author Yeung
 * @version 1.0
 */
public class FailureTestHelper {
    /**
     * Private constructor to prevent this class being instantiated.
     */
    private FailureTestHelper() {
    }

    /**
     * Gets the configuration object used for testing.
     * 
     * @return The configuration object.
     * @throws Exception
     *             to JUnit.
     */
    public static ConfigurationObject getConfig(String configName) throws Exception {
        ConfigurationObject obj = new XMLFilePersistence().loadFile("FailureTestHelper", new File(
                "test_files/FailureTests.xml"));

        return obj.getChild("FailureTestHelper").getChild(configName);
    }

    /**
     * Gets value for field of given object.
     * 
     * @param obj
     *            The given object.
     * @param field
     *            The field name.
     * @return The field value.
     */
    public static Object getField(Object obj, String field) {
        Object value = null;
        try {
            Field declaredField = null;
            try {
                declaredField = obj.getClass().getDeclaredField(field);
            } catch (NoSuchFieldException e) {
                // Ignore
            }
            if (declaredField == null) {
                // From the superclass
                declaredField = obj.getClass().getSuperclass().getDeclaredField(field);
            }

            declaredField.setAccessible(true);

            value = declaredField.get(obj);

            declaredField.setAccessible(false);
        } catch (IllegalArgumentException e) {
            // Ignore
        } catch (SecurityException e) {
            // Ignore
        } catch (IllegalAccessException e) {
            // Ignore
        } catch (NoSuchFieldException e) {
            // Ignore
        }

        return value;
    }

}
