/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.accuracytests.dto;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.tc.dto.AbstractContestsFilter;
import com.topcoder.web.tc.dto.ContestNameEntity;
import com.topcoder.web.tc.dto.DateIntervalSpecification;
import com.topcoder.web.tc.dto.Filterable;

/**
 * <p>
 * Accuracy test for AbstractContestsFilter class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AbstractContestsFilterAccuracyTest {
    /**
     * Represents the instance of AbstractContestsFilter used in test.
     */
    private AbstractContestsFilter filter;

    /**
     * <p>
     * Set up for each test.
     * </p>
     */
    @Before
    public void setUp() {
        filter = new AbstractContestsFilter() {
        };
    }

    /**
     * Accuracy test for AbstractContestsFilter(). The instance should be created.
     */
    @Test
    public void testCtor() {
        assertNotNull("The instance should be created.", filter);
        assertEquals("The base class is incorrect.", filter.getClass().getSuperclass().getSuperclass(), ContestNameEntity.class);
        assertTrue("The base class is incorrect.", filter instanceof Filterable);
    }

    /**
     * <p>
     * Accuracy test for type property.
     * </p>
     */
    @Test
    public void testType() {
        List<String> type = new ArrayList<String>();
        filter.setType(type);
        assertEquals("The subtype is inocrrect.", type, filter.getType());
    }

    /**
     * <p>
     * Accuracy test for subtype property.
     * </p>
     */
    @Test
    public void testSubtype() {
        List<String> subtype = new ArrayList<String>();
        filter.setSubtype(subtype);
        assertEquals("The subtype is inocrrect.", subtype, filter.getSubtype());
    }

    /**
     * <p>
     * Accuracy test for catalog property.
     * </p>
     */
    @Test
    public void testCatalog() {
        List<String> catalog = new ArrayList<String>();
        filter.setCatalog(catalog);
        assertEquals("The catalog is inocrrect.", catalog, filter.getCatalog());
    }

    /**
     * <p>
     * Accuracy test for registrationStart property.
     * </p>
     */
    @Test
    public void testRegistrationStart() {
        DateIntervalSpecification registrationStart = new DateIntervalSpecification();
        filter.setRegistrationStart(registrationStart);
        assertEquals("The registrationStart is inocrrect.", registrationStart, filter.getRegistrationStart());
    }

    /**
     * <p>
     * Accuracy test for registrationStart property.
     * </p>
     */
    @Test
    public void testSubmissionEnd() {
        DateIntervalSpecification submissionEnd = new DateIntervalSpecification();
        filter.setSubmissionEnd(submissionEnd);
        assertEquals("The submissionEnd is inocrrect.", submissionEnd, filter.getSubmissionEnd());
    }
}