/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import junit.framework.TestCase;

import com.topcoder.web.common.HibernateUtils;
import com.topcoder.web.reg.actions.miscellaneous.mock.MockVisaLetterEventDAO;
import com.topcoder.web.reg.actions.miscellaneous.mock.MockVisaLetterRequestDAO;

/**
 * Unit test cases for {@link VisaLetterStatusAction} class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class VisaLetterStatusActionTest extends TestCase {

    /**
     * An instance of VisaLetterStatusAction used for testing.
     */
    private VisaLetterStatusAction instance;

    /**
     * Sets up the test environment.
     */
    public void setUp() {
        instance = new VisaLetterStatusAction();
    }

    /**
     * Tears down the test environment.
     */
    public void tearDown() {
        instance = null;
    }

    /**
     * Accuracy, Failure test case for {@link VisaLetterStatusAction#checkConfiguration()} method.
     */
    public void testCheckConfiguration() {
        instance = (VisaLetterStatusAction) TestHelper.setFieldsInBaseAction(instance);
        instance.setVisaLetterEventDAO(new MockVisaLetterEventDAO());
        instance.setVisaLetterRequestDAO(new MockVisaLetterRequestDAO());
        instance.checkConfiguration();
    }
    
    /**
     * Failure test case for {@link VisaLetterStatusAction#checkConfiguration()} method.
     * Sets VisaLetterEventDAO to null and expects an exception.
     */
    public void testCheckConfiguationFail() {
        instance = (VisaLetterStatusAction) TestHelper.setFieldsInBaseAction(instance);
        try {
            instance.setVisaLetterEventDAO(null);
            instance.checkConfiguration();
            fail("Exception should be thrown");
        } catch (UserDocumentationManagementActionsConfigurationException e) {
            // pass
        }
    }

    /**
     * Accuracy test case for {@link VisaLetterStatusAction#VisaLetterStatusAction()} constructor. Checks for null
     * instance.
     */
    public void testVisaLetterStatusAction() {
        assertNotNull("Error instantiating the class", instance);
    }

    /**
     * Accuracy test case for {@link VisaLetterStatusAction#execute()} method.
     * @throws Exception
     *             to JUnit
     */
    public void testExecute() throws Exception {
        instance = (VisaLetterStatusAction) TestHelper.setFieldsInBaseAction(instance);
        instance.setVisaLetterEventDAO(new MockVisaLetterEventDAO());
        instance.setVisaLetterRequestDAO(new MockVisaLetterRequestDAO());
        HibernateUtils.begin();

        assertTrue("Error executing VisaLetterStatusAction", "success".equals(instance.execute()));
        assertNotNull("Error getting VisaLetterEvents", instance.getVisaLetterEvents());
        assertEquals("Error getting VisaLetterEvents", 2, instance.getVisaLetterEvents().size());

        TestHelper.closeHibernateSession();
        TestHelper.clearDatabase();
    }

    /**
     * Failure test case for {@link VisaLetterStatusAction#execute()} method.
     * @throws Exception
     *             to JUnit
     */
    public void testExecuteFail() throws Exception {
        instance = (VisaLetterStatusAction) TestHelper.setFieldsInBaseAction(instance);
        instance.setSession(null);
        try {
            instance.execute();
            fail("Exception should be thrown");
        } catch (UserDocumentationManagementActionsException e) {
            // pass
        }
    }
}
