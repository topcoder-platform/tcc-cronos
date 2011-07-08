/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import junit.framework.TestCase;

import com.topcoder.web.common.HibernateUtils;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.dao.hibernate.AuditDAOHibernate;
import com.topcoder.web.common.dao.hibernate.UserDAOHibernate;

/**
 * Unit test cases for {@link AffidavitHistoryAction} class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AffidavitHistoryActionTest extends TestCase {

    /**
     * An instance of AffidavitHistoryAction used for testing.
     */
    private AffidavitHistoryAction instance;

    /**
     * Sets up the test environment.
     * @throws Exception
     *             to JUnit
     */
    public void setUp() throws Exception {
        instance = new AffidavitHistoryAction();
    }

    /**
     * Tears down the test environment.
     */
    public void tearDown() {
        instance = null;
    }

    /**
     * Accuracy test case for {@link AffidavitHistoryAction#AffidavitHistoryAction()} constructor. Checks for null after
     * initialization.
     */
    public void testAffidavitHistoryAction() {
        assertNotNull("Error instantiating the class", instance);
    }

    /**
     * Accuracy test case for {@link AffidavitHistoryAction#execute()} method.
     * @throws Exception
     *             to JUnit
     */
    public void testExecute() throws Exception {
        AuditDAO auditDAO = new AuditDAOHibernate();
        UserDAO userDAO = new UserDAOHibernate();
        HibernateUtils.begin();

        instance.setServletRequest(TestHelper.getHttpServletRequest());

        instance.setOperationType("affidavitHistoryAction");
        instance.setUserDAO(userDAO);
        instance.setSession(TestHelper.getSession());
        instance.setAuditDAO(auditDAO);

        assertTrue("Error executing the action", "success".equals(instance.execute()));

        TestHelper.closeHibernateSession();
    }

    /**
     * Accuracy test case for {@link AffidavitHistoryAction#getResult()} method.
     * @throws Exception
     *             to JUnit
     */
    public void testGetResult() throws Exception {
        assertNull("Error getting the result object", instance.getResult());

        testExecute();
        assertNotNull("Error getting the result object", instance.getResult());
    }
    
    /**
     * Failure Test method for {@link com.topcoder.web.reg.actions.miscellaneous.AffidavitHistoryAction#execute()}.
     */
    public void testExecuteFail() {
        try {
            instance.execute();
            fail("UserDocumentationManagementActionsException expected");
        }catch (UserDocumentationManagementActionsException e) {
        }
    }
}
