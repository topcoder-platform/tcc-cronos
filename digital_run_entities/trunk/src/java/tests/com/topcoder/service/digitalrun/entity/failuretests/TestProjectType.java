/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.digitalrun.entity.failuretests;


import com.topcoder.service.digitalrun.entity.ProjectType;

import junit.framework.TestCase;


/**
 * <p>
 * This class contains the failure test cases for ProjectType class.
 * </p>
 *
 * @author akhil_bansal
 * @version 1.0
 */
public class TestProjectType extends TestCase {

    /**
     * <p>
     * Represents the instance of ProjectType to be used.
     * </p>
     */
    private ProjectType projectType = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *         throws exception if any, raise it to JUnit.
     */
    @Override
    public void setUp() throws Exception {
        this.projectType = new ProjectType();
    }

    /**
     * <p>
     * This method tests the setName() method to throw IllegalArgumentException
     * for null name.
     * </p>
     */
    public void testSetNameNull() {
        try {
            this.projectType.setName(null);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setCreationUser() method to throw IllegalArgumentException
     * for null creation user.
     * </p>
     */
    public void testSetCreationUserNull() {
        try {
            this.projectType.setCreationUser(null);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setCreationUser() method to throw IllegalArgumentException
     * for empty creation user.
     * </p>
     */
    public void testSetCreationUserEmpty() {
        try {
            this.projectType.setCreationUser("");
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setCreationUser() method to throw IllegalArgumentException
     * for trimmed empty creation user.
     * </p>
     */
    public void testSetCreationUserTrimmedEmpty() {
        try {
            this.projectType.setCreationUser("  \t   \n");
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setModificationUser() method to throw IllegalArgumentException
     * for null modification user.
     * </p>
     */
    public void testSetModificationUserNull() {
        try {
            this.projectType.setModificationUser(null);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setModificationUser() method to throw IllegalArgumentException
     * for empty modification user.
     * </p>
     */
    public void testSetModificationUserEmpty() {
        try {
            this.projectType.setModificationUser("");
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setModificationUser() method to throw IllegalArgumentException
     * for trimmed empty modification user.
     * </p>
     */
    public void testSetModificationUserTrimmedEmpty() {
        try {
            this.projectType.setModificationUser("  \t   \n");
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

}
