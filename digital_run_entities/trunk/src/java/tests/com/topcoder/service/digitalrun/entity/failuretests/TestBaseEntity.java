/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.digitalrun.entity.failuretests;


import com.topcoder.service.digitalrun.entity.BaseEntity;
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
public class TestBaseEntity extends TestCase {

    /**
     * <p>
     * Represents the instance of BaseEntity to be used.
     * </p>
     */
    private BaseEntity baseEntity = null;

    /**
     * <p>
     * Sets up test environment. This method initializes the instance of base
     * entity with a subclass implementation (ProjectType).
     * </p>
     *
     * @throws Exception
     *         throws exception if any, raise it to JUnit.
     */
    @Override
    public void setUp() throws Exception {
        this.baseEntity = new ProjectType();
    }

    /**
     * <p>
     * This method tests the setId() method to throw IllegalArgumentException
     * for negative id.
     * </p>
     */
    public void testSetUserIdNegative() {
        try {
            this.baseEntity.setId(-1);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setId() method to throw IllegalArgumentException
     * for zero id.
     * </p>
     */
    public void testSetIdZero() {
        try {
            this.baseEntity.setId(0);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setCreationDate() method to throw IllegalArgumentException
     * for null creationDate.
     * </p>
     */
    public void testSetCreationDateNull() {
        try {
            this.baseEntity.setCreationDate(null);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setModificationDate() method to throw IllegalArgumentException
     * for null modificationDate.
     * </p>
     */
    public void testSetModificationDateNull() {
        try {
            this.baseEntity.setModificationDate(null);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setDescription() method to throw IllegalArgumentException
     * for null description.
     * </p>
     */
    public void testSetDescriptionNull() {
        try {
            this.baseEntity.setDescription(null);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setDescription() method to throw IllegalArgumentException
     * for empty description.
     * </p>
     */
    public void testSetDescriptionEmpty() {
        try {
            this.baseEntity.setDescription("");
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setDescription() method to throw IllegalArgumentException
     * for trimmed empty description.
     * </p>
     */
    public void testSetDescriptionTrimmedEmpty() {
        try {
            this.baseEntity.setDescription("  \t   \n");
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

}
