/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.service.facade.contest.ContestServiceException;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.studio.ContestNotFoundException;

/**
 * <p>
 * Unit tests for <code>PayContestAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PayContestActionUnitTest extends TestCase {

    /**
     * The instance used in testing.
     */
    private PayContestAction instance;

    /**
     * Sets up the environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        TestHelper.cleanupEnvironment();
        instance = new MockPayByBillingAccountAction();
        instance.prepare();
    }

    /**
     * Tears down the environment.
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        TestHelper.cleanupEnvironment();
        instance = null;
    }

    /**
     * Tests the class inheritance to make sure it is correct.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testInheritance() throws Exception {
        TestHelper.assertSuperclass(instance.getClass().getSuperclass().getSuperclass(),
            StudioOrSoftwareContestAction.class);
    }

    /**
     * Accuracy test for constructor. Verifies the new instance is created.
     */
    public void testConstructor() {
        assertNotNull("unable to create instance", instance);
    }

    /**
     * Accuracy test for executeAction. The payment should be processed successfully for the studio
     * competition and no exception should occur.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_Accuracy1() throws Exception {
        StudioCompetition comp = new StudioCompetition();
        comp.setId(1);
        MockContestServiceFacade.addStudioCompetition(comp);
        instance.setContestId(comp.getId());
        instance.setProjectId(0);
        instance.executeAction();
    }

    /**
     * Failure test for executeAction. The studio competition wasn't found, so ContestNotFoundException is
     * expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_Failure1() throws Exception {
        try {
            instance.setContestId(1);
            instance.setProjectId(0);
            instance.executeAction();
            fail("ContestNotFoundException is expected");
        } catch (ContestNotFoundException e) {
            // success
        }
    }

    /**
     * Failure test for executeAction. The software competition wasn't found, so ContestServiceException is
     * expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_Failure2() throws Exception {
        try {
            instance.setContestId(0);
            instance.setProjectId(1);
            instance.executeAction();
            fail("ContestServiceException is expected");
        } catch (ContestServiceException e) {
            // success
        }
    }
}
