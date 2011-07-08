/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.util.Map;

import junit.framework.TestCase;

import com.topcoder.web.common.HibernateUtils;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.hibernate.AuditDAOHibernate;
import com.topcoder.web.reg.actions.miscellaneous.mock.MockBaseAction;

/**
 * Unit test cases for {@link BaseAction} class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseActionTest extends TestCase {

    /**
     * An instance of BaseAction used for testing.
     */
    private BaseAction instance;

    /**
     * Sets up the test environment.
     */
    public void setUp() {
        instance = new MockBaseAction();
        instance = TestHelper.setFieldsInBaseAction(instance);
    }

    /**
     * Tears down the test environment.
     */
    public void tearDown() {
        instance = null;
    }

    /**
     * Accuracy test case for {@link BaseAction#BaseAction()} constructor.
     */
    public void testBaseAction() {
        assertNotNull("Error instantiating the class", instance);
    }

    /**
     * Accuracy test case for {@link BaseAction#audit()} method. Calls the audit method and verifies if the record is
     * inserted properly.
     * @throws Exception
     *             to JUnit
     */
    public void testAudit() throws Exception {
        HibernateUtils.begin();

        instance.audit();
        AuditDAO auditDAO = new AuditDAOHibernate();
        assertTrue("Audit Record not inserted properly", auditDAO.hasOperation(TestHelper.USER,
                TestHelper.OPERATION_TYPE));

        TestHelper.closeHibernateSession();

        TestHelper.clearDatabase();
    }
    
    /**
     * Failure test case for {@link BaseAction#audit()} method.
     * @throws Exception
     *             to JUnit
     */
    public void testAuditFail() throws Exception {
        instance.setSession(null);
        try{
            instance.audit();
            fail("Exception should be thrown");
        }catch(UserDocumentationManagementActionsException e){
             // pass
        }
    }

    /**
     * Accuracy test case for {@link BaseAction#getUserId()} method.
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserId() throws Exception {
        assertEquals("Error getting user_id", 20, instance.getUserId());
    }
    
    /**
     * Failure test case for {@link BaseAction#getUserId()} method.
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserIdFail() throws Exception {
        instance.setSession(null);
        try{
            instance.getUserId();
            fail("Exception should be thrown");
        }catch(UserDocumentationManagementActionsException e){
            // pass
        }
    }

    /**
     * Accuracy test case for {@link BaseAction#getSession()} method.
     */
    public void testGetSession() {
        Map < String, Object > session = TestHelper.getSession();
        instance.setSession(session);

        assertNotNull("Error geting session", instance.getSession());
        assertEquals("Wrong instance of session returned", session, instance.getSession());
    }

    /**
     * Accuracy test case for {@link BaseAction#checkConfiguration()} method.
     */
    public void testCheckConfiguration() {
        // exception should not be thrown as all the fields have valid values
        instance.checkConfiguration();
    }

    /**
     * Failure test case for {@link BaseAction#checkConfiguration()} method. Sets a null value and expects an exception.
     */
    public void testCheckConfigurationFail() {
        instance.setUserDAO(null);
        try {
            instance.checkConfiguration();
            fail("Exception should be thrown");
        } catch (UserDocumentationManagementActionsConfigurationException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link BaseAction#checkConfiguration()} method. Sets an empty value and expects an
     * exception.
     */
    public void testCheckConfigurationFail2() {
        instance.setOperationType("");
        try {
            instance.checkConfiguration();
            fail("Exception should be thrown");
        } catch (UserDocumentationManagementActionsConfigurationException e) {
            // pass
        }
    }

}
