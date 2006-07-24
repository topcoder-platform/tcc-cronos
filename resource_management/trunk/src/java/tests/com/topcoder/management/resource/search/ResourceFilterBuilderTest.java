/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.search;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

import junit.framework.TestCase;

/**
 * Unit tests for the class: ResourceFilterBuilder.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ResourceFilterBuilderTest extends TestCase {

    /**
     * Tests method: createResourceIdFilter(long).
     *
     * With a non-positive parameter, IllegalArgumentException should be thrown.
     */
    public void testCreateResourceIdFilter_NonPositive() {
        try {
            ResourceFilterBuilder.createResourceIdFilter(0);
            fail("The parameter must be positive.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: testCreateResourceIdFilter(long).
     *
     * Checks if the filter is properly created.
     */
    public void testCreateResourceIdFilter_Accuracy() {
        // create the filter
        Filter filter = ResourceFilterBuilder.createResourceIdFilter(1);
        // It should be an instance of EqualToFilter
        assertTrue("It should be an instance of EqualToFilter.", filter instanceof EqualToFilter);
        // check the name
        assertEquals("The name is not property set.",
                ResourceFilterBuilder.RESOURCE_ID_FIELD_NAME, ((EqualToFilter) filter).getName());
        // check the value
        assertEquals("The value is not property set.",
                1, ((Long) ((EqualToFilter) filter).getValue()).longValue());

    }

    /**
     * Tests method: createProjectIdFilter(long).
     *
     * With a non-positive parameter, IllegalArgumentException should be thrown.
     */
    public void testCreateProjectIdFilter_NonPositive() {
        try {
            ResourceFilterBuilder.createProjectIdFilter(0);
            fail("The parameter must be positive.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: createProjectIdFilter(long).
     *
     * Checks if the filter is properly created.
     */
    public void testCreateProjectIdFilter_Accuracy() {
        // create the filter
        Filter filter = ResourceFilterBuilder.createProjectIdFilter(1);
        // It should be an instance of EqualToFilter
        assertTrue("It should be an instance of EqualToFilter.", filter instanceof EqualToFilter);
        // check the name
        assertEquals("The name is not property set.",
                ResourceFilterBuilder.PROJECT_ID_FIELD_NAME, ((EqualToFilter) filter).getName());
        // check the value
        assertEquals("The value is not property set.",
                1, ((Long) ((EqualToFilter) filter).getValue()).longValue());

    }

    /**
     * Tests method: createPhaseIdFilter(long).
     *
     * With a non-positive parameter, IllegalArgumentException should be thrown.
     */
    public void testCreatePhaseIdFilter_NonPositive() {
        try {
            ResourceFilterBuilder.createPhaseIdFilter(0);
            fail("The parameter must be positive.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: createPhaseIdFilter(long).
     *
     * Checks if the filter is properly created.
     */
    public void testCreatePhaseIdFilter_Accuracy() {
        // create the filter
        Filter filter = ResourceFilterBuilder.createPhaseIdFilter(1);
        // It should be an instance of EqualToFilter
        assertTrue("It should be an instance of EqualToFilter.", filter instanceof EqualToFilter);
        // check the name
        assertEquals("The name is not property set.",
                ResourceFilterBuilder.PHASE_ID_FIELD_NAME, ((EqualToFilter) filter).getName());
        // check the value
        assertEquals("The value is not property set.",
                1, ((Long) ((EqualToFilter) filter).getValue()).longValue());

    }

    /**
     * Tests method: createSubmissionIdFilter(long).
     *
     * With a non-positive parameter, IllegalArgumentException should be thrown.
     */
    public void testCreateSubmissionIdFilter_NonPositive() {
        try {
            ResourceFilterBuilder.createSubmissionIdFilter(0);
            fail("The parameter must be positive.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: createSubmissionIdFilter(long).
     *
     * Checks if the filter is properly created.
     */
    public void testCreateSubmissionIdFilter_Accuracy() {
        // create the filter
        Filter filter = ResourceFilterBuilder.createSubmissionIdFilter(1);
        // It should be an instance of EqualToFilter
        assertTrue("It should be an instance of EqualToFilter.", filter instanceof EqualToFilter);
        // check the name
        assertEquals("The name is not property set.",
                ResourceFilterBuilder.SUBMISSION_ID_FIELD_NAME, ((EqualToFilter) filter).getName());
        // check the value
        assertEquals("The value is not property set.",
                1, ((Long) ((EqualToFilter) filter).getValue()).longValue());

    }

    /**
     * Tests method: createResourceRoleIdFilter(long).
     *
     * With a non-positive parameter, IllegalArgumentException should be thrown.
     */
    public void testCreateResourceRoleIdFilter_NonPositive() {
        try {
            ResourceFilterBuilder.createResourceRoleIdFilter(0);
            fail("The parameter must be positive.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: createResourceRoleIdFilter(long).
     *
     * Checks if the filter is properly created.
     */
    public void testCreateResourceRoleIdFilter_Accuracy() {
        // create the filter
        Filter filter = ResourceFilterBuilder.createResourceRoleIdFilter(1);
        // It should be an instance of EqualToFilter
        assertTrue("It should be an instance of EqualToFilter.", filter instanceof EqualToFilter);
        // check the name
        assertEquals("The name is not property set.",
                ResourceFilterBuilder.RESOURCE_ROLE_ID_FIELD_NAME, ((EqualToFilter) filter).getName());
        // check the value
        assertEquals("The value is not property set.",
                1, ((Long) ((EqualToFilter) filter).getValue()).longValue());

    }


    /**
     * Tests method createNoProjectFilter().
     *
     * Null will return because it is not supported yet.
     */
    public void testCreateNoProjectFilter() {
        assertNull("Not supported", ResourceFilterBuilder.createNoProjectFilter());
    }

    /**
     * Tests method createNoSubmissionFilter().
     *
     * Null will return because it is not supported yet.
     */
    public void testCreateNoSubmissionFilter() {
        assertNull("Not supported", ResourceFilterBuilder.createNoSubmissionFilter());
    }

    /**
     * Tests method createNoPhaseFilter().
     *
     * Null will return because it is not supported yet.
     */
    public void testCreateNoPhaseFilter() {
        assertNull("Not supported", ResourceFilterBuilder.createNoPhaseFilter());
    }

    /**
     * Tests method: createExtensionPropertyValueFilter(String).
     *
     * With a null parameter, IllegalArgumentException should be thrown.
     */
    public void testCreateExtensionPropertyValueFilter_Null() {
        try {
            ResourceFilterBuilder.createExtensionPropertyValueFilter(null);
            fail("The parameter cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: createExtensionPropertyValueFilter(String).
     *
     * Checks if the filter is properly created.
     */
    public void testCreateExtensionPropertyValueFilter_Accuracy() {
        // create the filter
        Filter filter = ResourceFilterBuilder.createExtensionPropertyValueFilter("This is a name");
        // It should be an instance of EqualToFilter
        assertTrue("It should be an instance of EqualToFilter.", filter instanceof EqualToFilter);
        // check the name
        assertEquals("The name is not property set.",
                ResourceFilterBuilder.EXTENSION_PROPERTY_VALUE_FIELD_NAME, ((EqualToFilter) filter).getName());
        // check the value
        assertEquals("The value is not property set.",
                "This is a name", (String) ((EqualToFilter) filter).getValue());

    }

    /**
     * Tests method: createExtensionPropertyNameFilter(String).
     *
     * With a null parameter, IllegalArgumentException should be thrown.
     */
    public void testCreateExtensionPropertyNameFilter_Null() {
        try {
            ResourceFilterBuilder.createExtensionPropertyNameFilter(null);
            fail("The parameter cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: createExtensionPropertyNameFilter(String).
     *
     * Checks if the filter is properly created.
     */
    public void testCreateExtensionPropertyNameFilter_Accuracy() {
        // create the filter
        Filter filter = ResourceFilterBuilder.createExtensionPropertyNameFilter("This is a name");
        // It should be an instance of EqualToFilter
        assertTrue("It should be an instance of EqualToFilter.", filter instanceof EqualToFilter);
        // check the name
        assertEquals("The name is not property set.",
                ResourceFilterBuilder.EXTENSION_PROPERTY_NAME_FIELD_NAME, ((EqualToFilter) filter).getName());
        // check the value
        assertEquals("The value is not property set.",
                "This is a name", (String) ((EqualToFilter) filter).getValue());

    }

}
