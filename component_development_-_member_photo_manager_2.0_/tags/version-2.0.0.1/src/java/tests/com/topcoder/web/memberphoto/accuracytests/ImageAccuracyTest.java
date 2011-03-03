/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.accuracytests;

import com.topcoder.web.memberphoto.entities.Image;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy test for the <code>Image</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class ImageAccuracyTest extends TestCase {

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
     * Accuracy test for <code>Image()</code> constructor.
     * </p>
     */
    public void testCtorAccuracy() {
        assertNotNull("Image creation failed", image);
    }

    /**
     * <p>
     * Accuracy test for <code>getId</code> method.
     * </p>
     */
    public void testGetIdAccuracy() {
        assertEquals("The id is not correct.", 0, image.getId());
    }

    /**
     * <p>
     * Accuracy test for <code>setId</code> method.
     * </p>
     */
    public void testSetIdAccuracy() {
        image.setId(1);
        assertEquals("The id is not correct.", 1, image.getId());
    }

    /**
     * <p>
     * Accuracy test for <code>getFileName</code> method.
     * </p>
     */
    public void testGetFileNameAccuracy() {
        assertNull("The fileName is not correct.", image.getFileName());
    }

    /**
     * <p>
     * Accuracy test for <code>setFileName</code> method.
     * </p>
     */
    public void testSetFileNameAccuracy() {
        image.setFileName("test");
        assertEquals("The fileName is not correct.", "test", image.getFileName());
    }
}
