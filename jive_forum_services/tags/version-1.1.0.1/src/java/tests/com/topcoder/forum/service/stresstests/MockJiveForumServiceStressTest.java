/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service.stresstests;

import junit.framework.TestCase;

import com.topcoder.forum.service.CategoryConfiguration;
import com.topcoder.forum.service.EntityType;
import com.topcoder.forum.service.UserRole;
import com.topcoder.forum.service.ejb.mock.MockJiveForumService;

/**
 * <p>
 * Stress tests for <code>MockJiveForumService</code> class.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.1
 */
public class MockJiveForumServiceStressTest extends TestCase {
    /**
     * <p>
     * Represents the stress test count.
     * </p>
     */
    private static long COUNT = 500;

    /**
     * <p>
     * Represents the start time milliseconds.
     * </p>
     */
    private long start;

    /**
     * Instance to test.
     */
    private MockJiveForumService service;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     * 
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        service = new MockJiveForumService();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * 
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
        service = null;
    }

    /**
     * <p>
     * Prints the test result.
     * </p>
     * 
     * @param method the method to test
     */
    private void printTestResult(String method) {
        System.out.println("Stress test for the " + method + " " + COUNT + " times, cost "
            + (System.currentTimeMillis() - start) + " milliseconds");
    }

    /**
     * <p>
     * Stress test for the <code>watch</code> and <code>isWatch</code> methods.
     * </p>
     * 
     * @throws Exception to junit
     */
    public void testManageWatch() throws Exception {
        start = System.currentTimeMillis();

        for (int i = 0; i < COUNT; ++i) {
            service.watch(99999, 12345, EntityType.FORUM_CATEGORY);
            assertTrue("True is expected.", service.isWatched(99999, 12345,
                EntityType.FORUM_CATEGORY));
        }

        this.printTestResult("watch/isWatched");
    }

    /**
     * <p>
     * Stress test for the <code>setUserRole</code> and <code>getUserRole</code> methods.
     * </p>
     * 
     * @throws Exception to junit
     */
    public void testManageUserRole() throws Exception {
        start = System.currentTimeMillis();
        UserRole userRole = UserRole.MODERATOR;
        for (int i = 0; i < COUNT; ++i) {
            service.setUserRole(99990, 99991, UserRole.CONTRIBUTOR);
            userRole = service.getUserRole(99990, 99991);
            assertEquals("UserRole.CONTRIBUTOR is expected.", UserRole.CONTRIBUTOR, userRole);
        }

        this.printTestResult("setUserRole/getUserRole");
    }

    /**
     * <p>
     * Stress test for the <code>createCategory</code> methods.
     * </p>
     * 
     * @throws Exception to junit
     */
    public void testCreateCategory() throws Exception {
        CategoryConfiguration categoryConfig = new CategoryConfiguration();
        categoryConfig.setName("Test");
        categoryConfig.setDescription("Test Desc");
        categoryConfig.setRootCategoryId(10);
        categoryConfig.setComponentId(1);
        categoryConfig.setVersionText("v1.0");
        categoryConfig.setVersionId(1);
        categoryConfig.setTemplateCategoryId(1);
        categoryConfig.setModeratorUserId(99998);

        start = System.currentTimeMillis();

        for (int i = 0; i < COUNT; ++i) {
            long id = service.createCategory(categoryConfig);
            assertEquals("the result of createCategory is invalid.", i + 1, id);
        }

        this.printTestResult("createCategory");
    }
}