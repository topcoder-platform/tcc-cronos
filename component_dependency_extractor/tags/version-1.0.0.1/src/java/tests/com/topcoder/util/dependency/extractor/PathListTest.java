/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.extractor;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit tests for <code>PathList</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PathListTest extends TestCase {
    /**
     * <p>
     * <code>PathList</code> instance to be tested.
     * </p>
     */
    private PathList pathList;

    /**
     * <p>
     * Sets up the fixture. This method is called before a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        super.setUp();
        pathList = new PathList();
    }

    /**
     * <p>
     * Tears down the fixture. This method is called after a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Gets the test suite for <code>PathList</code> class.
     * </p>
     *
     * @return a <code>TestSuite</code> providing the tests for <code>PathList</code> class.
     */
    public static Test suite() {
        return new TestSuite(PathListTest.class);
    }

    /**
     * <p>
     * Accuracy test for constructor <code>PathList()</code>.
     * </p>
     * <p>
     * Uses the constructor to create a non-null instance. No exception should be thrown.
     * </p>
     */
    public void testCtor1_Accuracy() {
        assertNotNull("It should return non-null instance.", new PathList());
    }

    /**
     * <p>
     * Accuracy test for <code>addPath(String)</code>.
     * </p>
     * <p>
     * Passes in a path value. No exception should be thrown.
     * </p>
     */
    public void testAddPath_Accuracy() {
        // NOTE: this is used internally and no checking is performed and caller should be responsible for checking
        pathList.addPath("path");
    }

    /**
     * <p>
     * Accuracy test for <code>getFilePaths()</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testGetFilePaths_Accuracy() {
        pathList.addPath("path1");
        pathList.addPath("path2");
        assertEquals("it should have 2 path elements.", 2, pathList.getFilePaths().size());
    }
}
