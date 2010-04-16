/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.catalog.entity.Category;
import com.topcoder.service.facade.contest.ContestServiceException;

/**
 * <p>
 * Unit tests for <code>GetContestCategoriesAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GetContestCategoriesActionUnitTest extends TestCase {

    /**
     * The instance used in testing.
     */
    private GetContestCategoriesAction instance;

    /**
     * Sets up the environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        TestHelper.cleanupEnvironment();
        instance = new GetContestCategoriesAction();
        instance.setContestServiceFacade(new MockContestServiceFacade());
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
        TestHelper.assertSuperclass(instance.getClass(), ContestAction.class);
    }

    /**
     * Accuracy test for constructor. Verifies the new instance is created.
     */
    public void testConstructor() {
        assertNotNull("unable to create instance", instance);
    }

    /**
     * Accuracy test for executeAction. The categories should be put into model successfully.
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test
    public void test_executeAction_Accuracy1() throws Exception {
        instance.executeAction();

        // make sure categories are present in model
        Object result = instance.getResult();
        assertNotNull("result should not be null", result);
        List<Category> categories = null;
        try {
            categories = (List<Category>) result;
        } catch (ClassCastException e) {
            fail("result is of wrong type");
        }

        assertEquals("wrong number of categories fetched", 2, categories.size());
        assertEquals("wrong ID for first category", (long) 1, (long) categories.get(0).getId());
        assertEquals("wrong ID for second category", (long) 2, (long) categories.get(1).getId());
    }

    /**
     * Failure test for executeAction. A service error occurred, so ContestServiceException is expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_Failure1() throws Exception {
        try {
            MockContestServiceFacade.setFailGetCategories(true);
            instance.executeAction();
            fail("ContestServiceException is expected");
        } catch (ContestServiceException e) {
            // success
        }
    }

    /**
     * Failure test for executeAction. The <code>contestServiceFacade</code> hasn't been injected, so
     * IllegalStateException is expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_Failure2() throws Exception {
        try {
            new GetContestCategoriesAction().executeAction();
            fail("IllegalStateException is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

}
