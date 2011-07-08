/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import junit.framework.TestCase;

import com.topcoder.web.common.HibernateUtils;

/**
 * Unit test cases for {@link AssignmentDocumentHistoryAction} class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AssignmentDocumentHistoryActionTest extends TestCase {

    /**
     * An instance of AssignmentDocumentHistoryAction class used for testing.
     */
    private AssignmentDocumentHistoryAction instance;

    /**
     * Sets up the test environment.
     */
    public void setUp() {
        instance = new AssignmentDocumentHistoryAction();
    }

    /**
     * Tears down the test environment.
     */
    public void tearDown() {
        instance = null;
    }

    /**
     * Accuracy test case for {@link AssignmentDocumentHistoryAction#AssignmentDocumentHistoryAction()} constructor.
     */
    public void testAssignmentDocumentHistoryAction() {
        assertNotNull("Error instantiating the class", instance);
    }

    /**
     * Accuracy test case for {@link AssignmentDocumentHistoryAction#execute()} method.
     * @throws Exception
     *             to JUnit
     */
    public void testExecute() throws Exception {
        instance = (AssignmentDocumentHistoryAction) TestHelper.setFieldsInBaseAction(instance);
        HibernateUtils.begin();

        assertTrue("Error executing AssignmentDocumentHistoryAction", "success".equals(instance.execute()));
        TestHelper.closeHibernateSession();
        TestHelper.clearDatabase();
    }
    
    /**
     * Failure test case for {@link AssignmentDocumentHistoryAction#execute()} method.
     * Sets startRank to 0 and expects an exception.
     * @throws Exception
     *             to JUnit
     */
    public void testExecuteFail1() throws Exception {
        instance = (AssignmentDocumentHistoryAction) TestHelper.setFieldsInBaseAction(instance);
        instance.setStartRank(0);
        HibernateUtils.begin();
        try {
            instance.setFullList(true);
            instance.execute();
            fail("Exception should be thrown");
        } catch (UserDocumentationManagementActionsException e) {
            // pass
        }
        TestHelper.closeHibernateSession();
        TestHelper.clearDatabase();
    }
    
    /**
     * Failure test case for {@link AssignmentDocumentHistoryAction#execute()} method.
     * @throws Exception
     *             to JUnit
     */
    public void testExecuteFail() throws Exception {
        instance = (AssignmentDocumentHistoryAction) TestHelper.setFieldsInBaseAction(instance);
        HibernateUtils.begin();
        try {
            instance.setFullList(true);
            instance.execute();
            fail("Exception should be thrown");
        } catch (UserDocumentationManagementActionsException e) {
            // pass
        }
        TestHelper.closeHibernateSession();
        TestHelper.clearDatabase();
    }

    /**
     * Accuracy test case for {@link AssignmentDocumentHistoryAction#execute()} method.
     * @throws Exception
     *             to JUnit
     */
    public void testExecute1() throws Exception {
        instance = (AssignmentDocumentHistoryAction) TestHelper.setFieldsInBaseAction(instance);
        HibernateUtils.begin();

        // sort by submission title
        instance.setSortColumn(1);
        assertTrue("Error executing AssignmentDocumentHistoryAction", "success".equals(instance.execute()));

        // sort by time left
        instance.setSortColumn(2);
        assertTrue("Error executing AssignmentDocumentHistoryAction", "success".equals(instance.execute()));

        // sort by status
        instance.setSortColumn(3);
        assertTrue("Error executing AssignmentDocumentHistoryAction", "success".equals(instance.execute()));

        TestHelper.closeHibernateSession();
        TestHelper.clearDatabase();
    }

    /**
     * Accuracy test case for {@link AssignmentDocumentHistoryAction#getAssignmentDocuments()} method.
     * @throws Exception
     *             to JUnit
     */
    public void testGetAssignmentDocuments() throws Exception {
        assertNull("Error getting AssignmentDocuments", instance.getAssignmentDocuments());

        instance = (AssignmentDocumentHistoryAction) TestHelper.setFieldsInBaseAction(instance);
        HibernateUtils.begin();
        instance.setSortColumn(1);
        instance.execute();

        assertNotNull("Error getting AssignmentDocuments", instance.getAssignmentDocuments());
        assertEquals("Error getting AssignmentDocuments", 2, instance.getAssignmentDocuments().size());

        TestHelper.closeHibernateSession();
        TestHelper.clearDatabase();
    }
}
