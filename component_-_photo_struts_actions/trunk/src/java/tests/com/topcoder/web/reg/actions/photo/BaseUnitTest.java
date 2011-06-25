/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.photo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import junit.framework.TestCase;

/**
 * <p>
 * BaseUnitTest class for the tests.
 * </p>
 * <p>
 * Thread safe: This class has no state, and thus it is thread safe.
 * </p>
 *
 * @author mumujava
 * @version 1.0
 */
public class BaseUnitTest extends TestCase {
    /**
     * <p>
     * An empty string.
     * </p>
     */
    public static final String EMPTY = " \n\t";

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        clearDir("test_files/removed");
        clearDir("test_files/previewed");
    }

    /**
     * Delete all files in the directory.
     *
     * @param dir the directory to delete
     * @throws Exception to JUnit
     */
    protected static void clearDir(String dir) throws Exception {
        File[] files = new File(dir).listFiles();
        for (File file : files) {
            file.delete();
        }
    }

    /**
     * <p>Outjects the value of the given instance.</p>
     *
     * @param clazz clazz of where the field is declared
     * @param instance the instance whose value to outject
     * @param fieldName the field whose value to outject
     * @return the value
     * @throws Exception if anything wrong
     */
    public static Object outject(Class<?> clazz, Object instance, String fieldName) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        try {
            field.setAccessible(true);
            return field.get(instance);
        } finally {
            if (field != null) {
                field.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * move file.
     * </p>
     * @param oldPath the old path.
     * @param newPath the new path.
     * @throws IOException when move file.
     */
    void fileCopy(String oldPath, String newPath) throws IOException {
        InputStream is = null;
        FileOutputStream os = null;
        try {
            int byteread = 0;
            File oldFile = new File(oldPath);
            File newFile = new File(newPath);
            if (oldFile.exists()) {
                is = new FileInputStream(oldFile);
                os = new FileOutputStream(newFile);
                byte[] buffer = new byte[1024];
                while ((byteread = is.read(buffer)) != -1) {
                    os.write(buffer, 0, byteread);
                }
            }
        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }
}