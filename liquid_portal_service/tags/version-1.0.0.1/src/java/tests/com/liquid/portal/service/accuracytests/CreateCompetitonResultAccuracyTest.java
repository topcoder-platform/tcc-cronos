/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service.accuracytests;

import java.io.Serializable;

import junit.framework.TestCase;

import com.liquid.portal.service.CreateCompetitonResult;
import com.topcoder.service.project.Competition;
import com.topcoder.service.project.SoftwareCompetition;

/**
 * Accuracy tests for {@link CreateCompetitonResult}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CreateCompetitonResultAccuracyTest extends TestCase {
    /**
     * <p>
     * Represents the CreateCompetitonResult. Just for test.
     * </p>
     */
    private CreateCompetitonResult instance;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void setUp() throws Exception {
        super.setUp();
        instance = new CreateCompetitonResult();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void tearDown() throws Exception {
        instance = null;
        super.tearDown();
    }

    /**
     * <p>
     * Accuracy test for constructor. It verifies the new instance is created.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("should not be null", instance);
        assertTrue("should be true", instance instanceof Serializable);
    }

    /**
     * <p>
     * Accuracy test for setter and getter for competition filed.
     * </p>
     */
    public void testSetterAndGetterFor_competition() {
        Competition competition = new SoftwareCompetition();
        instance.setCompetition(competition);
        assertTrue("should be true", instance.getCompetition().equals(competition));
    }
}