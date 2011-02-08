/*
 * Copyright (C) 2008, 2011 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.web.memberphoto.manager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import com.topcoder.web.memberphoto.entities.Image;
import com.topcoder.web.memberphoto.entities.MemberImage;

/**
 * <p>
 * Helper used for testing.
 * </p>
 *
 * @author cyberjag, sparemax
 * @version 2.0
 */
public final class TestHelper {
    /**
     * <p>
     * Represents the empty string.
     * </p>
     *
     * @since 2.0
     */
    public static final String EMPTY_STRING = " \t ";

    /**
     * <p>
     * Represents the path of test files.
     * </p>
     *
     * @since 2.0
     */
    public static final String TEST_FILES = "test_files" + File.separator;

    /**
     * Represents the bad entity manager.
     *
     * @since 2.0
     */
    public static final EntityManager ENTITY_MANAGER_BAD;

    /**
     * Represents the closed entity manager.
     *
     * @since 2.0
     */
    public static final EntityManager ENTITY_MANAGER_CLOSED;

    /**
     * Represents the entity manager used for CRUD operations on entity.
     *
     * @since 2.0
     */
    public static final EntityManager ENTITY_MANAGER;

    /**
     * <p>
     * Initialization.
     * </p>
     */
    static {
        ENTITY_MANAGER_BAD = Persistence.createEntityManagerFactory("badMemberPhotoManager").createEntityManager();

        ENTITY_MANAGER_CLOSED = Persistence.createEntityManagerFactory("memberPhotoManager").createEntityManager();
        ENTITY_MANAGER_CLOSED.close();

        ENTITY_MANAGER = Persistence.createEntityManagerFactory("memberPhotoManager").createEntityManager();
    }

    /**
     * No instances allowed.
     */
    private TestHelper() {
        // empty
    }

    /**
     * Creates the MemberImage for testing.
     *
     * @param image
     *            the associated image
     * @return the member image
     */
    public static MemberImage createMemberImage(Image image) {
        MemberImage memberImage = new MemberImage();
        memberImage.setMemberId(201);
        memberImage.setImage(image);
        return memberImage;
    }

    /**
     * Creates the image for testing.
     *
     * @return the image
     */
    public static Image createImage() {
        Image image = new Image();
        image.setFileName("member_photo.jpg");
        return image;
    }

    /**
     * <p>
     * Clears the database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     *
     * @since 2.0
     */
    public static void clearDB() throws Exception {
        executeSQL(TEST_FILES + "DBClear.sql");
    }

    /**
     * <p>
     * Loads data into the database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     *
     * @since 2.0
     */
    public static void loadDB() throws Exception {
        executeSQL(TEST_FILES + "DBData.sql");
    }

    /**
     * <p>
     * Gets value for field of given object.
     * </p>
     *
     * @param instance
     *            the instance.
     * @param field
     *            the field name.
     *
     * @return the field value.
     *
     * @since 2.0
     */
    public static Object getField(Object instance, String field) {
        Object value = null;
        try {
            Field fieldObj = getFieldObj(instance, field);
            fieldObj.setAccessible(true);

            value = fieldObj.get(instance);

            fieldObj.setAccessible(false);
        } catch (IllegalAccessException e) {
            // Ignore
        }

        return value;
    }

    /**
     * <p>
     * Executes the SQL statements in the file. Lines that are empty will be ignore.
     * </p>
     *
     * @param file
     *            the file.
     *
     * @throws Exception
     *             to JUnit.
     *
     * @since 2.0
     */
    public static void executeSQL(String file) throws Exception {
        String[] values = readFile(file).split(";");

        List<String> sqlList = new ArrayList<String>();
        for (int i = 0; i < values.length; i++) {
            String str = values[i].trim();
            if (str.length() != 0) {
                sqlList.add(str);
            }
        }

        EntityManager em = ENTITY_MANAGER;
        em.clear();
        em.getTransaction().begin();
        for (String sql : sqlList) {
            em.createNativeQuery(sql).executeUpdate();
        }
        em.getTransaction().commit();
        em.clear();

    }

    /**
     * <p>
     * Reads the content of a given file.
     * </p>
     *
     * @param fileName
     *            the name of the file to read.
     *
     * @return a string represents the content.
     *
     * @throws IOException
     *             if any error occurs during reading.
     *
     * @since 2.0
     */
    private static String readFile(String fileName) throws IOException {
        Reader reader = new FileReader(fileName);

        try {
            // Create a StringBuilder instance
            StringBuilder sb = new StringBuilder();

            // Buffer for reading
            char[] buffer = new char[1024];

            // Number of read chars
            int k = 0;

            // Read characters and append to string builder
            while ((k = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, k);
            }

            // Return read content
            return sb.toString().replace("\r\n", "\n");
        } finally {
            try {
                reader.close();
            } catch (IOException ioe) {
                // Ignore
            }
        }
    }

    /**
     * <p>
     * Gets a Field object of the instance.
     * </p>
     *
     * @param instance
     *            the instance.
     * @param field
     *            the field name.
     *
     * @return the Field object.
     *
     * @since 2.0
     */
    private static Field getFieldObj(Object instance, String field) {
        Field fieldObj = null;
        try {
            try {
                fieldObj = instance.getClass().getDeclaredField(field);
            } catch (NoSuchFieldException e) {
                // Ignore
            }
            if (fieldObj == null) {
                // From the superclass
                fieldObj = instance.getClass().getSuperclass().getDeclaredField(field);
            }
        } catch (IllegalArgumentException e) {
            // Ignore
        } catch (SecurityException e) {
            // Ignore
        } catch (NoSuchFieldException e) {
            // Ignore
        }

        return fieldObj;
    }
}
