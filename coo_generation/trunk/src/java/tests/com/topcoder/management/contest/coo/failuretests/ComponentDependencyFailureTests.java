/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.failuretests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.management.contest.coo.ComponentDependency;

/**
 * <p>
 * Set of failure tests for ComponentDependency,
 * </p>
 * @author vilain
 * @version 1.0
 */
public class ComponentDependencyFailureTests {

    /**
     * Instance of BaseDBConnector for testing.
     */
    private ComponentDependency componentDependency;

    /**
     * Setting up environment.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        componentDependency = new ComponentDependency();
    }

    /**
     * Method under test ComponentDependency.setCategory(DependencyCategory).
     * Failure testing of exception in case category is null.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSetCategoryFailureNull() throws Exception {
        try {
            componentDependency.setCategory(null);
            Assert.fail("IllegalArgumentException is expected since category is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test ComponentDependency.setComponent(Component). Failure
     * testing of exception in case component is null.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSetComponentFailureNull() throws Exception {
        try {
            componentDependency.setComponent(null);
            Assert.fail("IllegalArgumentException is expected since component is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test ComponentDependency.setType(DependencyType). Failure
     * testing of exception in case type is null.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSetTypeFailureNull() throws Exception {
        try {
            componentDependency.setType(null);
            Assert.fail("IllegalArgumentException is expected since type is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}