/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.accuracytests;

import com.topcoder.timetracker.report.ReportDisplayTag;

import com.topcoder.util.config.ConfigManager;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.cactus.JspTestCase;
import org.apache.cactus.WebRequest;
import org.apache.cactus.WebResponse;

import java.lang.reflect.Field;


/**
 * Accuracy test for <code>ReportDisplayTag</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestReportDisplayTag extends JspTestCase {
    /** DB Connection Factory configuration file. */
    private static final String DB_CONFIG_FILE = "accuracyTests/DB.xml";

    /** The time tracker reprot configuration file. */
    private static final String TTR_CONFIG_FILE = "accuracyTests/TimeTrackerReport.xml";

    /** The value of the namespace used for test. */
    private static final String NAMESPACE = "namespace";

    /** The string value used for test. */
    private static final String VALUE = "VALUE";

    /** The value of the type attribute used for test. */
    private static final String TYPE = "type";

    /** The value of the category attribute used for test. */
    private static final String CATEGORY = "category";

    /** The value of the employeeFilter attribute used for test. */
    private static final String EMPLOYEE_FILTER = "employeeFilter";

    /** The value of the clientFilter attribute used for test. */
    private static final String CLIENT_FILTER = "clientFilter";

    /** The value of the projectFilter attribute used for test. */
    private static final String PROJECT_FILTER = "projectFilter";

    /** The value of the billableFilter attribute used for test. */
    private static final String BILLABLE_FILTER = "billableFilter";

    /** The value of the startDateFilter attribute used for test. */
    private static final String START_DATE_FILTER = "startDateFilter";

    /** The value of the endDateFilter attribute used for test. */
    private static final String END_DATE_FILTER = "endDateFilter";

    /** The ReportDisplayTag instance to test against. */
    private ReportDisplayTag tag = null;

    /**
     * See javadoc for junit.framework.TestCase#setUp().
     *
     * @throws Exception exception that occurs during the setup process.
     */
    protected void setUp() throws Exception {
        AccuracyBaseTest.clearCM();

        ConfigManager manager = ConfigManager.getInstance();

        manager.add(DB_CONFIG_FILE);
        manager.add(TTR_CONFIG_FILE);

        AccuracyBaseTest.clearTables();

        AccuracyBaseTest.fillTables();

        tag = new ReportDisplayTag();

        tag.setPageContext(pageContext);
        tag.setNamespace("com.topcoder.timetracker.report.CustomConfiguration");
        tag.setType(TYPE);
        tag.setCategory(CATEGORY);
        tag.setEmployeeFilter(EMPLOYEE_FILTER);

        pageContext.setAttribute(TYPE, "EMPLOYEE");
        pageContext.setAttribute(CATEGORY, "TIME");
        pageContext.setAttribute(EMPLOYEE_FILTER, "admin");
    }

    /**
     * See javadoc for junit.framework.TestCase#tearDown().
     *
     * @throws Exception exception that occurs during the tear down process.
     */
    protected void tearDown() throws Exception {
        AccuracyBaseTest.clearTables();
        AccuracyBaseTest.clearCM();
    }

    /**
     * Returns the suit to run the test.
     *
     * @return suite to run the test.
     */
    public static Test suite() {
        return new TestSuite(AccuracyTestReportDisplayTag.class);
    }

    /**
     * Tests setBillableFilter().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testSetBillableFilter() throws Exception {
        tag.setBillableFilter(VALUE);

        Field field = tag.getClass().getDeclaredField(BILLABLE_FILTER);
        field.setAccessible(true);
        assertEquals(VALUE, field.get(tag));
    }

    /**
     * Tests setClientFilter().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testSetClientFilter() throws Exception {
        tag.setClientFilter(VALUE);

        Field field = tag.getClass().getDeclaredField(CLIENT_FILTER);
        field.setAccessible(true);
        assertEquals(VALUE, field.get(tag));
    }

    /**
     * Tests setEmployeeFilter().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testSetEmployeeFilter() throws Exception {
        tag.setEmployeeFilter(VALUE);

        Field field = tag.getClass().getDeclaredField(EMPLOYEE_FILTER);
        field.setAccessible(true);
        assertEquals(VALUE, field.get(tag));
    }

    /**
     * Tests setStartDateFilter().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testSetStartDateFilter() throws Exception {
        tag.setStartDateFilter(VALUE);

        Field field = tag.getClass().getDeclaredField(START_DATE_FILTER);
        field.setAccessible(true);
        assertEquals(VALUE, field.get(tag));
    }

    /**
     * This method tests {@link tag#setEndDateFilter(String)} for correct behavior.
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testSetEndDateFilter() throws Exception {
        tag.setEndDateFilter(VALUE);

        Field field = tag.getClass().getDeclaredField(END_DATE_FILTER);
        field.setAccessible(true);
        assertEquals(VALUE, field.get(tag));
    }

    /**
     * This method tests {@link tag#setProjectFilter(String)} for correct behavior.
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testSetProjectFilter() throws Exception {
        tag.setProjectFilter(VALUE);

        Field field = tag.getClass().getDeclaredField(PROJECT_FILTER);
        field.setAccessible(true);
        assertEquals(VALUE, field.get(tag));
    }

    /**
     * Tests setCategory().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testSetCategory() throws Exception {
        tag.setCategory(VALUE);

        Field field = tag.getClass().getDeclaredField(CATEGORY);
        field.setAccessible(true);
        assertEquals(VALUE, field.get(tag));
    }

    /**
     * Tests setNamespace().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testSetNamespace() throws Exception {
        tag.setNamespace(VALUE);

        Field field = tag.getClass().getDeclaredField(NAMESPACE);
        field.setAccessible(true);
        assertEquals(VALUE, field.get(tag));
    }

    /**
     * Tests setType().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testSetType() throws Exception {
        tag.setType(VALUE);

        Field field = tag.getClass().getDeclaredField(TYPE);
        field.setAccessible(true);
        assertEquals(VALUE, field.get(tag));
    }

    /**
     * Tests doStartTag() with the employee filter.
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testDoStartTag1() throws Exception {
        assertTrue("SKIP_BODY should be returned.", ReportDisplayTag.SKIP_BODY == tag.doStartTag());
    }

    /**
     * Checks the output HTML string.
     *
     * @param response the response object to check output.
     */
    public void endDoStartTag1(WebResponse response) {
        System.out.println("The output message from AccuracyTestReportDisplayTag#endDoStartTag1():");
        System.out.println(response.getText());
    }

    /**
     * Tests doStartTag() with the client filter.
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testDoStartTag2() throws Exception {
        pageContext.setAttribute(CLIENT_FILTER, "Client 1");
        assertTrue("SKIP_BODY should be returned.", ReportDisplayTag.SKIP_BODY == tag.doStartTag());
    }

    /**
     * Checks the output HTML string.
     *
     * @param response the response object to check output.
     */
    public void endDoStartTag2(WebResponse response) {
        System.out.println("The output message from AccuracyTestReportDisplayTag#endDoStartTag2():");
        System.out.println(response.getText());
    }

    /**
     * Tests doStartTag() with the project filter.
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testDoStartTag3() throws Exception {
        pageContext.setAttribute(CLIENT_FILTER, "Project Name 1");
        assertTrue("SKIP_BODY should be returned.", ReportDisplayTag.SKIP_BODY == tag.doStartTag());
    }

    /**
     * Checks the output HTML string.
     *
     * @param response the response object to check output.
     */
    public void endDoStartTag3(WebResponse response) {
        System.out.println("The output message from AccuracyTestReportDisplayTag#endDoStartTag3():");
        System.out.println(response.getText());
    }

    /**
     * Tests doStartTag() with the date filter.
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testDoStartTag4() throws Exception {
        pageContext.setAttribute(START_DATE_FILTER, "2005-12-01");
        pageContext.setAttribute(END_DATE_FILTER, "2006-02-01");
        assertTrue("SKIP_BODY should be returned.", ReportDisplayTag.SKIP_BODY == tag.doStartTag());
    }

    /**
     * Checks the output HTML string.
     *
     * @param response the response object to check output.
     */
    public void endDoStartTag4(WebResponse response) {
        System.out.println("The output message from AccuracyTestReportDisplayTag#endDoStartTag4():");
        System.out.println(response.getText());
    }

    /**
     * Passes the employee filter value in the request parameter.
     *
     * @param request the request object on the client side.
     */
    public void beginDoStartTag5(WebRequest request) {
        request.addParameter(EMPLOYEE_FILTER, "admin");
    }

    /**
     * Tests doStartTag() with the employee filter from the request parameter.
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testDoStartTag5() throws Exception {
        pageContext.removeAttribute(EMPLOYEE_FILTER);
        assertEquals("Fails to return SKIP_BODY", ReportDisplayTag.SKIP_BODY, tag.doStartTag());
    }

    /**
     * Checks the output HTML string.
     *
     * @param response the response object to check output.
     */
    public void endDoStartTag5(WebResponse response) {
        System.out.println("The output message from AccuracyTestReportDisplayTag#endDoStartTag5():");
        System.out.println(response.getText());
    }
}
