/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.dto;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Tests the AbstractContestDTO class.
 * </p>
 * @author duxiaoyang
 * @version 1.0
 */
public class AbstractContestDTOTest {

    /**
     * Represents an instance of AbstractContestDTO for test.
     */
    private AbstractContestDTO dto;

    /**
     * <p>
     * Sets up testing environment. Initializes variable.
     * </p>
     */
    @Before
    public void setUp() {
        dto = new DummyContestDTO();
    }

    /**
     * <p>
     * Tears down testing environment. Sets variable to null.
     * </p>
     */
    @After
    public void tearDown() {
        dto = null;
    }

    /**
     * <p>
     * Tests that AbstractContestDTO is extended from ContestNameEntity.
     * </p>
     */
    @Test
    public void testExtends() {
        assertTrue("AbstractContestDTO should extends from ContestNameEntity", dto instanceof ContestNameEntity);
    }

    /**
     * <p>
     * Tests the default constructor. It verifies that all fields are initialized correctly.
     * </p>
     */
    @Test
    public void testCtor() {
        dto = new DummyContestDTO();
        assertNull("type should be initialized to null", dto.getType());
        assertNull("subtype should be initialized to null", dto.getSubtype());
    }

    /**
     * <p>
     * Tests the setType and getType methods. It verifies that the field can be accessed correctly.
     * </p>
     */
    @Test
    public void testSetGetType() {
        // null
        dto.setType(null);
        assertNull("type should be set", dto.getType());
        // empty
        dto.setType("");
        assertEquals("type should be set", "", dto.getType());
        // non-null, non-empty
        dto.setType("type");
        assertEquals("type should be set", "type", dto.getType());
    }

    /**
     * <p>
     * Tests the setSubtype and getSubtype methods. It verifies that the field can be accessed correctly.
     * </p>
     */
    @Test
    public void testSetGetSubtype() {
        // null
        dto.setSubtype(null);
        assertNull("subtype should be set", dto.getSubtype());
        // empty
        dto.setSubtype("");
        assertEquals("subtype should be set", "", dto.getSubtype());
        // non-null, non-empty
        dto.setSubtype("subtype");
        assertEquals("subtype should be set", "subtype", dto.getSubtype());
    }

    /**
     * <p>
     * This class is a dummy sub-class of AbstractContestDTO used for test.
     * </p>
     * @author TCSDEVELOPER
     * @version 1.0
     */
    private class DummyContestDTO extends AbstractContestDTO {
    }
}
