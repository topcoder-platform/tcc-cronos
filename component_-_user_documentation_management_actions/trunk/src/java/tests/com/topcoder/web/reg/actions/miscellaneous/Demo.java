/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.topcoder.web.common.HibernateUtils;

import junit.framework.TestCase;

/**
 * <p>
 * Demonstrates the usage of this component.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends TestCase {

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    public void setUp() {
        HibernateUtils.begin();
    }

    /**
     * <p>
     * Tear down the test environment.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void tearDown() throws Exception {
        HibernateUtils.getSession().clear();
        HibernateUtils.close();
        TestHelper.clearDatabase();
    }

    /**
     * <p>
     * The Demo API.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testDemoAPI() throws Exception {
        // Load the Spring context file
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Get AffidavitHistoryAction from spring and execute
        AffidavitHistoryAction affidavitHistoryAction = (AffidavitHistoryAction) applicationContext
                .getBean("affidavitHistoryAction");
        affidavitHistoryAction.execute();

        // Get AffirmAffidavitAction from spring and execute
        AffirmAffidavitAction affirmAffidavitAction = (AffirmAffidavitAction) applicationContext
                .getBean("affirmAffidavitAction");
        affirmAffidavitAction.execute();

        // Get AssignmentDocumentHistoryAction from spring and execute
        AssignmentDocumentHistoryAction assignmentDocumentHistoryAction = (AssignmentDocumentHistoryAction) applicationContext
                .getBean("assignmentDocumentHistoryAction");
        assignmentDocumentHistoryAction.execute();

        // Get PaymentHistoryAction from spring and execute
        PaymentHistoryAction paymentHistoryAction = (PaymentHistoryAction) applicationContext
                .getBean("paymentHistoryAction");
        paymentHistoryAction.execute();

        // Get PaymentHistoryAction from spring and execute
        VisaLetterStatusAction visaLetterStatusAction = (VisaLetterStatusAction) applicationContext
                .getBean("visaLetterStatusAction");
        visaLetterStatusAction.execute();
    }
}
