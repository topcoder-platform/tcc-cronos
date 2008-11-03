/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit.accuracytests;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Iterator;

import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * A helper class to perform those common operations which are helpful for the test.
 * </p>
 *
 * @author LostHunter
 * @version 1.0
 */
public final class TestHelper {

    /**
     * <p>
     * Private constructor to prevent initialization.
     * </p>
     *
     */
    private TestHelper() {
    }

    /**
     * Get the object's field value with the given name.
     *
     * @param clazz the class type of the object
     * @param fieldName the name of the field
     * @param obj the object whose field value to get
     * @return the field value
     * @throws SecurityException if any error occurs
     * @throws NoSuchFieldException if any error occurs
     * @throws IllegalArgumentException if any error occurs
     * @throws IllegalAccessException if any error occurs
     */
    public static Object getFieldValue(Class clazz, String fieldName, Object obj) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Object value = null;
        // get the field
        Field field = clazz.getDeclaredField(fieldName);
        boolean accessFlag = field.isAccessible();
        field.setAccessible(true);
        // get its value
        value = field.get(obj);
        field.setAccessible(accessFlag);
        return value;
    }

    /**
     * <p>
     * Uses the given file to config the configuration manager.
     * </p>
     *
     * @throws Exception when any exception occurs
     */
    public static void loadXMLConfig() throws Exception {
        //set up environment
        ConfigManager config = ConfigManager.getInstance();
        File file = new File("test_files" + File.separator + "accuracy" + File.separator + "config.xml");
        config.add(file.getCanonicalPath());
    }

    /**
     * <p>
     * Clears all the namespaces from the configuration manager.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator i = cm.getAllNamespaces(); i.hasNext();) {
            cm.removeNamespace((String) i.next());
        }
    }
}
