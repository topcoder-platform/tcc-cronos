/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.dto;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Tests the ContestNameEntity class.
 * </p>
 * @author duxiaoyang
 * @version 1.0
 */
public class ContestNameEntityTest {

    /**
     * Represents an instance of ContestNameEntity for test.
     */
    private ContestNameEntity entity;

    /**
     * <p>
     * Sets up testing environment. Initializes variable.
     * </p>
     */
    @Before
    public void setUp() {
        entity = new DummyContestNameEntity();
    }

    /**
     * <p>
     * Tears down testing environment. Sets variable to null.
     * </p>
     */
    @After
    public void tearDown() {
        entity = null;
    }

    /**
     * <p>
     * Tests the default constructor. It verifies that all fields are initialized correctly.
     * </p>
     */
    @Test
    public void testCtor() {
        entity = new DummyContestNameEntity();
        assertNull("contest name should be initialized to null", entity.getContestName());
    }

    /**
     * <p>
     * Tests the setContestName and getContestName methods. It verifies that the field can be accessed correctly.
     * </p>
     */
    @Test
    public void testSetGetContestName() {
        // null
        entity.setContestName(null);
        assertNull("contest name should be set", entity.getContestName());
        // empty
        entity.setContestName("");
        assertEquals("contest name should be set", "", entity.getContestName());
        // non-null, non-empty
        entity.setContestName("contest");
        assertEquals("contest name should be set", "contest", entity.getContestName());
    }

    /**
     * <p>
     * This class is a dummy sub-class of ContestNameEntity used for test.
     * </p>
     * @author TCSDEVELOPER
     * @version 1.0
     */
    private class DummyContestNameEntity extends ContestNameEntity {
    }
}
