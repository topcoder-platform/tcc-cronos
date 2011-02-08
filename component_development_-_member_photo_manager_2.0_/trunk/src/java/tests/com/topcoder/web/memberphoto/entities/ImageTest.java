/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.entities;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link Image} class.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class ImageTest extends TestCase {

    /**
     * Represents the <code>Image</code> instance to test.
     */
    private Image image;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void setUp() throws Exception {
        image = new Image();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void tearDown() throws Exception {
        image = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(ImageTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link Image#Image()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_Image() {
        //check for null
        assertNotNull("Image creation failed", image);
    }

    /**
     * <p>
     * Accuracy test for {@link Image#getId()} and {@link Image#setId(long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is 0.
     * </p>
     *
     */
    public void test_accuracy_getId() {
        //set the value to test
        image.setId(0);
        assertEquals("getId and setId failure occurred", 0, image.getId());
    }

    /**
     * <p>
     * Accuracy test for {@link Image#setId(long)} and {@link Image#getId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is 1.
     * </p>
     *
     */
    public void test_accuracy_setId() {
        //set the value to test
        image.setId(1);
        assertEquals("getId and setId failure occurred", 1, image.getId());
    }

    /**
     * <p>
     * Accuracy test for {@link Image#getFileName()} and {@link Image#setFileName(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     *
     */
    public void test_accuracy_getFileName() {
        //set the value to test
        image.setFileName(null);
        assertEquals("getFileName and setFileName failure occurred", null, image.getFileName());
    }

    /**
     * <p>
     * Accuracy test for {@link Image#setFileName(String)} and {@link Image#getFileName()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     *
     */
    public void test_accuracy_setFileName() {
        //set the value to test
        image.setFileName("test");
        assertEquals("getFileName and setFileName failure occurred", "test", image.getFileName());
    }
}
