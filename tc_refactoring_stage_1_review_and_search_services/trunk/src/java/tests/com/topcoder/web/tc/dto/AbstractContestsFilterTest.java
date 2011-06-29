/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.dto;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Tests the AbstractContestsFilter class.
 * </p>
 * @author duxiaoyang
 * @version 1.0
 */
public class AbstractContestsFilterTest {

    /**
     * Represents an instance of AbstractContestsFilter for test.
     */
    private AbstractContestsFilter dto;

    /**
     * <p>
     * Sets up testing environment. Initializes variable.
     * </p>
     */
    @Before
    public void setUp() {
        dto = new DummyContestsFilter();
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
     * Tests that AbstractContestsFilter is extended from ContestNameEntity.
     * </p>
     */
    @Test
    public void testExtends() {
        assertTrue("AbstractContestsFilter should extends from ContestNameEntity", dto instanceof ContestNameEntity);
    }

    /**
     * <p>
     * Tests that AbstractContestsFilter implements Filterable.
     * </p>
     */
    @Test
    public void testImplements() {
        assertTrue("AbstractContestsFilter should implement Filterable", dto instanceof Filterable);
    }

    /**
     * <p>
     * Tests the default constructor. It verifies that all fields are initialized correctly.
     * </p>
     */
    @Test
    public void testCtor() {
        dto = new DummyContestsFilter();
        assertNull("type should be initialized to null", dto.getType());
        assertNull("subtype should be initialized to null", dto.getSubtype());
        assertNull("catalog should be initialized to null", dto.getCatalog());
        assertNull("registration start date should be initialized to null", dto.getRegistrationStart());
        assertNull("submission end date should be initialized to null", dto.getSubmissionEnd());
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
        dto.setType(new ArrayList<String>());
        assertEquals("type should be set", 0, dto.getType().size());
        // non-null, non-empty
        List<String> type = new ArrayList<String>();
        type.add("type");
        dto.setType(type);
        assertEquals("type should be set", 1, dto.getType().size());
        assertEquals("type should be set", "type", dto.getType().get(0));
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
        dto.setSubtype(new ArrayList<String>());
        assertEquals("subtype should be set", 0, dto.getSubtype().size());
        // non-null, non-empty
        List<String> subtype = new ArrayList<String>();
        subtype.add("subtype");
        dto.setSubtype(subtype);
        assertEquals("subtype should be set", 1, dto.getSubtype().size());
        assertEquals("subtype should be set", "subtype", dto.getSubtype().get(0));
    }

    /**
     * <p>
     * Tests the setCatalog and getCatalog methods. It verifies that the field can be accessed correctly.
     * </p>
     */
    @Test
    public void testSetGetCatalog() {
        // null
        dto.setCatalog(null);
        assertNull("catalog should be set", dto.getCatalog());
        // empty
        dto.setCatalog(new ArrayList<String>());
        assertEquals("catalog should be set", 0, dto.getCatalog().size());
        // non-null, non-empty
        List<String> catalog = new ArrayList<String>();
        catalog.add("catalog");
        dto.setCatalog(catalog);
        assertEquals("catalog should be set", 1, dto.getCatalog().size());
        assertEquals("catalog should be set", "catalog", dto.getCatalog().get(0));
    }

    /**
     * <p>
     * Tests the setRegistrationStart and getRegistrationStart methods. It verifies that the field can be accessed
     * correctly.
     * </p>
     */
    @Test
    public void testSetGetRegistrationStart() {
        // null
        dto.setRegistrationStart(null);
        assertNull("registration start date should be set", dto.getRegistrationStart());
        // non-null
        DateIntervalSpecification date = new DateIntervalSpecification();
        dto.setRegistrationStart(date);
        assertEquals("registration start date should be set", date, dto.getRegistrationStart());
    }

    /**
     * <p>
     * Tests the setSubmissionEnd and getSubmissionEnd methods. It verifies that the field can be accessed correctly.
     * </p>
     */
    @Test
    public void testSetGetSubmissionEnd() {
        // null
        dto.setSubmissionEnd(null);
        assertNull("submission end date should be set", dto.getSubmissionEnd());
        // non-null
        DateIntervalSpecification date = new DateIntervalSpecification();
        dto.setSubmissionEnd(date);
        assertEquals("submission end date should be set", date, dto.getSubmissionEnd());
    }

    /**
     * <p>
     * This class is a dummy sub-class of AbstractContestsFilter used for test.
     * </p>
     * @author TCSDEVELOPER
     * @version 1.0
     */
    private class DummyContestsFilter extends AbstractContestsFilter {
    }
}
