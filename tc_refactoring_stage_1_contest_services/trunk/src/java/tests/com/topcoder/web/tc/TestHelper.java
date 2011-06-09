/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This class provides methods for testing this component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestHelper {
    /**
     * <p>
     * Represents the path of test files.
     * </p>
     */
    public static final String TEST_FILES = "test_files" + File.separator;

    /**
     * <p>
     * Private constructor to prevent this class being instantiated.
     * </p>
     */
    private TestHelper() {
        // empty
    }

    /**
     * <p>
     * Gets value for field of given object.
     * </p>
     *
     * @param clazz
     *            the class type of the object
     * @param obj
     *            the given object.
     * @param fieldName
     *            the field name.
     * @return the field value.
     * @throws Exception
     *             if any error occurs
     */
    public static Object getField(Class<?> clazz, Object obj, String fieldName) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);

        Object value = field.get(obj);

        return value;
    }

    /**
     * <p>
     * Sets value for field of given object.
     * </p>
     *
     * @param clazz
     *            the class type of the object
     * @param obj
     *            the given object.
     * @param fieldName
     *            the field name.
     * @param value
     *            the field value.
     * @throws Exception
     *             if any error occurs
     */
    public static void setField(Class<?> clazz, Object obj, String fieldName, Object value) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);

        field.set(obj, value);
    }

    /**
     * <p>
     * Read the content form the specified file.
     * </p>
     *
     * @param file
     *            the file
     * @return the file content
     * @throws IOException
     *             IO exception while reading content
     */
    public static String[] readFileContent(File file) throws IOException {
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();
        List<String> results = new ArrayList<String>();
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;
                }
                sb.append(line);
                if (line.endsWith(";")) {
                    sb.deleteCharAt(sb.length() - 1);
                    results.add(sb.toString());
                    sb = new StringBuffer();
                }
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
        return results.toArray(new String[0]);
    }
}
