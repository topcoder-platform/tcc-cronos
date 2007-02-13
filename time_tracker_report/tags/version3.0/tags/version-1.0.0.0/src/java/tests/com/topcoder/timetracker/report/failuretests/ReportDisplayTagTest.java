/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.failuretests;

import com.topcoder.timetracker.report.ReportDisplayTag;
import com.topcoder.timetracker.report.failuretests.impl.PageContextImpl;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.io.File;

/**
 * <p>A failure test for {@link com.topcoder.timetracker.report.ReportDisplayTag} class.</p>
 *
 * @author  isv
 * @version 1.0
 */
public class ReportDisplayTagTest extends TestCase {

    /**
     * <p>An instance of {@link com.topcoder.timetracker.report.ReportDisplayTag} which is tested. This instance is
     * initialized in {@link #setUp()} method and released in {@link #tearDown()} method.<p>
     */
    private ReportDisplayTag testedInstance = null;

    /**
     * <p>A <code>PageContext</code> to be used for testing.</p>
     */
    private PageContext pageContext = null;

    /**
     * <p>Gets the test suite for {@link com.topcoder.timetracker.report.ReportDisplayTag} class.</p>
     *
     * @return a <code>TestSuite</code> providing the tests for {@link com.topcoder.timetracker.report.ReportDisplayTag} class.
     */
    public static Test suite() {
        return new TestSuite(ReportDisplayTagTest.class);
    }

    /**
     * <p>Sets up the fixture. This method is called before a test is executed.</p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        ConfigHelper.releaseNamespaces();
        ConfigHelper.loadConfiguration(new File("failure/FailureTestsConfig.xml"));
        this.pageContext = new PageContextImpl();
        this.testedInstance = new ReportDisplayTag();
        this.testedInstance.setPageContext(this.pageContext);
    }

    /**
     * <p>Tears down the fixture. This method is called after a test is executed.</p>
     *
     * @throws Exception if any error occurs.
     */
    protected void tearDown() throws Exception {
        this.testedInstance = null;
        this.pageContext = null;
        ConfigHelper.releaseNamespaces();
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#setNamespace(String)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#ZERO_LENGTH_STRING} as <code>namespace</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetNamespace_String_namespace_ZERO_LENGTH_STRING() {
        try {
            this.testedInstance.setNamespace(TestDataFactory.ZERO_LENGTH_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#setNamespace(String)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#WHITESPACE_ONLY_STRING} as <code>namespace</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetNamespace_String_namespace_WHITESPACE_ONLY_STRING() {
        try {
            this.testedInstance.setNamespace(TestDataFactory.WHITESPACE_ONLY_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#setType(String)} method for proper handling the invalid input
     * arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#ZERO_LENGTH_STRING} as <code>type</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetType_String_type_ZERO_LENGTH_STRING() {
        try {
            this.testedInstance.setType(TestDataFactory.ZERO_LENGTH_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#setType(String)} method for proper handling the invalid input
     * arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#WHITESPACE_ONLY_STRING} as <code>type</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetType_String_type_WHITESPACE_ONLY_STRING() {
        try {
            this.testedInstance.setType(TestDataFactory.WHITESPACE_ONLY_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#setCategory(String)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#ZERO_LENGTH_STRING} as <code>category</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetCategory_String_category_ZERO_LENGTH_STRING() {
        try {
            this.testedInstance.setCategory(TestDataFactory.ZERO_LENGTH_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#setCategory(String)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#WHITESPACE_ONLY_STRING} as <code>category</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetCategory_String_category_WHITESPACE_ONLY_STRING() {
        try {
            this.testedInstance.setCategory(TestDataFactory.WHITESPACE_ONLY_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#setClientFilter(String)} method for proper handling the
     * invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#ZERO_LENGTH_STRING} as <code>clientFilter</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetClientFilter_String_clientFilter_ZERO_LENGTH_STRING() {
        try {
            this.testedInstance.setClientFilter(TestDataFactory.ZERO_LENGTH_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#setClientFilter(String)} method for proper handling the
     * invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#WHITESPACE_ONLY_STRING} as <code>clientFilter</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetClientFilter_String_clientFilter_WHITESPACE_ONLY_STRING() {
        try {
            this.testedInstance.setClientFilter(TestDataFactory.WHITESPACE_ONLY_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#setEmployeeFilter(String)} method for proper handling the
     * invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#ZERO_LENGTH_STRING} as <code>employeeFilter</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetEmployeeFilter_String_employeeFilter_ZERO_LENGTH_STRING() {
        try {
            this.testedInstance.setEmployeeFilter(TestDataFactory.ZERO_LENGTH_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#setEmployeeFilter(String)} method for proper handling the
     * invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#WHITESPACE_ONLY_STRING} as <code>employeeFilter</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetEmployeeFilter_String_employeeFilter_WHITESPACE_ONLY_STRING() {
        try {
            this.testedInstance.setEmployeeFilter(TestDataFactory.WHITESPACE_ONLY_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#setProjectFilter(String)} method for proper handling the
     * invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#ZERO_LENGTH_STRING} as <code>projectFilter</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetProjectFilter_String_projectFilter_ZERO_LENGTH_STRING() {
        try {
            this.testedInstance.setProjectFilter(TestDataFactory.ZERO_LENGTH_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#setProjectFilter(String)} method for proper handling the
     * invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#WHITESPACE_ONLY_STRING} as <code>projectFilter</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetProjectFilter_String_projectFilter_WHITESPACE_ONLY_STRING() {
        try {
            this.testedInstance.setProjectFilter(TestDataFactory.WHITESPACE_ONLY_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#setBillableFilter(String)} method for proper handling the
     * invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#ZERO_LENGTH_STRING} as <code>billableFilter</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetBillableFilter_String_billableFilter_ZERO_LENGTH_STRING() {
        try {
            this.testedInstance.setBillableFilter(TestDataFactory.ZERO_LENGTH_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#setBillableFilter(String)} method for proper handling the
     * invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#WHITESPACE_ONLY_STRING} as <code>billableFilter</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetBillableFilter_String_billableFilter_WHITESPACE_ONLY_STRING() {
        try {
            this.testedInstance.setBillableFilter(TestDataFactory.WHITESPACE_ONLY_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#setStartDateFilter(String)} method for proper handling the
     * invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#ZERO_LENGTH_STRING} as <code>startDateFilter</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetStartDateFilter_String_startDateFilter_ZERO_LENGTH_STRING() {
        try {
            this.testedInstance.setStartDateFilter(TestDataFactory.ZERO_LENGTH_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#setStartDateFilter(String)} method for proper handling the
     * invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#WHITESPACE_ONLY_STRING} as <code>startDateFilter</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetStartDateFilter_String_startDateFilter_WHITESPACE_ONLY_STRING() {
        try {
            this.testedInstance.setStartDateFilter(TestDataFactory.WHITESPACE_ONLY_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#setEndDateFilter(String)} method for proper handling the
     * invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#ZERO_LENGTH_STRING} as <code>endDateFilter</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetEndDateFilter_String_endDateFilter_ZERO_LENGTH_STRING() {
        try {
            this.testedInstance.setEndDateFilter(TestDataFactory.ZERO_LENGTH_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#setEndDateFilter(String)} method for proper handling the
     * invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#WHITESPACE_ONLY_STRING} as <code>endDateFilter</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetEndDateFilter_String_endDateFilter_WHITESPACE_ONLY_STRING() {
        try {
            this.testedInstance.setEndDateFilter(TestDataFactory.WHITESPACE_ONLY_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#doStartTag} for proper behavior if the required instance
     * property is not set.</p>
     *
     * <p>Does not set the <code>namespace</code> property of the tested instance and expects the
     * <code>JspException</code> to be thrown.</p>
     */
    public void testDoStartTag_NamespaceNotSet() {
        this.testedInstance.setBillableFilter(TestDataFactory.VALID_BILLABLE_FILTER_PARAM_NAME);
        this.testedInstance.setCategory(TestDataFactory.VALID_REPORT_CATEGORY_PARAM_NAME);
        this.testedInstance.setClientFilter(TestDataFactory.VALID_CLIENT_FILTER_PARAM_NAME);
        this.testedInstance.setEmployeeFilter(TestDataFactory.VALID_EMPLOYEE_FILTER_PARAM_NAME);
        this.testedInstance.setEndDateFilter(TestDataFactory.VALID_END_DATE_FILTER_PARAM_NAME);
        this.testedInstance.setProjectFilter(TestDataFactory.VALID_PROJECT_FILTER_PARAM_NAME);
        this.testedInstance.setStartDateFilter(TestDataFactory.VALID_START_DATE_FILTER_PARAM_NAME);
        this.testedInstance.setType(TestDataFactory.VALID_REPORT_TYPE_PARAM_NAME);

        try {
            this.testedInstance.doStartTag();
            Assert.fail("JspException should have been thrown");
        } catch (JspException e) {
            // expected behavior
        } catch (Exception e) {
            fail("JspException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#doStartTag} for proper behavior if the required instance
     * property is not set.</p>
     *
     * <p>Does not set the <code>category</code> property of the tested instance and expects the
     * <code>JspException</code> to be thrown.</p>
     */
    public void testDoStartTag_CategoryNotSet() {
        this.testedInstance.setBillableFilter(TestDataFactory.VALID_BILLABLE_FILTER_PARAM_NAME);
        this.testedInstance.setClientFilter(TestDataFactory.VALID_CLIENT_FILTER_PARAM_NAME);
        this.testedInstance.setEmployeeFilter(TestDataFactory.VALID_EMPLOYEE_FILTER_PARAM_NAME);
        this.testedInstance.setEndDateFilter(TestDataFactory.VALID_END_DATE_FILTER_PARAM_NAME);
        this.testedInstance.setNamespace(TestDataFactory.VALID_REPORT_CONFIGURATION_NAMESPACE);
        this.testedInstance.setProjectFilter(TestDataFactory.VALID_PROJECT_FILTER_PARAM_NAME);
        this.testedInstance.setStartDateFilter(TestDataFactory.VALID_START_DATE_FILTER_PARAM_NAME);
        this.testedInstance.setType(TestDataFactory.VALID_REPORT_TYPE_PARAM_NAME);

        try {
            this.testedInstance.doStartTag();
            Assert.fail("JspException should have been thrown");
        } catch (JspException e) {
            // expected behavior
        } catch (Exception e) {
            fail("JspException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#doStartTag} for proper behavior if the required instance
     * property is not set.</p>
     *
     * <p>Does not set the <code>type</code> property of the tested instance and expects the <code>JspException</code>
     * to be thrown.</p>
     */
    public void testDoStartTag_TypeNotSet() {
        this.testedInstance.setBillableFilter(TestDataFactory.VALID_BILLABLE_FILTER_PARAM_NAME);
        this.testedInstance.setCategory(TestDataFactory.VALID_REPORT_CATEGORY_PARAM_NAME);
        this.testedInstance.setClientFilter(TestDataFactory.VALID_CLIENT_FILTER_PARAM_NAME);
        this.testedInstance.setEmployeeFilter(TestDataFactory.VALID_EMPLOYEE_FILTER_PARAM_NAME);
        this.testedInstance.setEndDateFilter(TestDataFactory.VALID_END_DATE_FILTER_PARAM_NAME);
        this.testedInstance.setNamespace(TestDataFactory.VALID_REPORT_CONFIGURATION_NAMESPACE);
        this.testedInstance.setProjectFilter(TestDataFactory.VALID_PROJECT_FILTER_PARAM_NAME);
        this.testedInstance.setStartDateFilter(TestDataFactory.VALID_START_DATE_FILTER_PARAM_NAME);

        try {
            this.testedInstance.doStartTag();
            Assert.fail("JspException should have been thrown");
        } catch (JspException e) {
            // expected behavior
        } catch (Exception e) {
            fail("JspException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#doStartTag} for proper behavior if the required instance
     * property is not set correctly.</p>
     *
     * <p>Sets the <code>namespace</code> property of the tested instance to value invalid in current context and
     * expects the <code>JspException</code> to be thrown.</p>
     */
    public void testDoStartTag_UnknownNamespace() {
        this.testedInstance.setBillableFilter(TestDataFactory.VALID_BILLABLE_FILTER_PARAM_NAME);
        this.testedInstance.setCategory(TestDataFactory.VALID_REPORT_CATEGORY_PARAM_NAME);
        this.testedInstance.setClientFilter(TestDataFactory.VALID_CLIENT_FILTER_PARAM_NAME);
        this.testedInstance.setEmployeeFilter(TestDataFactory.VALID_EMPLOYEE_FILTER_PARAM_NAME);
        this.testedInstance.setEndDateFilter(TestDataFactory.VALID_END_DATE_FILTER_PARAM_NAME);
        this.testedInstance.setNamespace("NonExistingNamespace");
        this.testedInstance.setProjectFilter(TestDataFactory.VALID_PROJECT_FILTER_PARAM_NAME);
        this.testedInstance.setStartDateFilter(TestDataFactory.VALID_START_DATE_FILTER_PARAM_NAME);
        this.testedInstance.setType(TestDataFactory.VALID_REPORT_TYPE_PARAM_NAME);

        try {
            this.testedInstance.doStartTag();
            Assert.fail("JspException should have been thrown");
        } catch (JspException e) {
            // expected behavior
        } catch (Exception e) {
            fail("JspException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#doStartTag} for proper behavior if the required instance
     * property is not set correctly.</p>
     *
     * <p>Sets the <code>category</code> property of the tested instance to value invalid in current context and expects
     * the <code>JspException</code> to be thrown.</p>
     */
    public void testDoStartTag_UnknownCategory() {
        this.pageContext.setAttribute("NonExistingCategory", "NonExistingCategory");
        this.pageContext.setAttribute(TestDataFactory.VALID_REPORT_TYPE_PARAM_NAME,
                                      TestDataFactory.VALID_REPORT_TYPE.getType());

        this.testedInstance.setBillableFilter(TestDataFactory.VALID_BILLABLE_FILTER_PARAM_NAME);
        this.testedInstance.setCategory("NonExistingCategory");
        this.testedInstance.setClientFilter(TestDataFactory.VALID_CLIENT_FILTER_PARAM_NAME);
        this.testedInstance.setEmployeeFilter(TestDataFactory.VALID_EMPLOYEE_FILTER_PARAM_NAME);
        this.testedInstance.setEndDateFilter(TestDataFactory.VALID_END_DATE_FILTER_PARAM_NAME);
        this.testedInstance.setNamespace(TestDataFactory.VALID_REPORT_CONFIGURATION_NAMESPACE);
        this.testedInstance.setProjectFilter(TestDataFactory.VALID_PROJECT_FILTER_PARAM_NAME);
        this.testedInstance.setStartDateFilter(TestDataFactory.VALID_START_DATE_FILTER_PARAM_NAME);
        this.testedInstance.setType(TestDataFactory.VALID_REPORT_TYPE_PARAM_NAME);

        try {
            this.testedInstance.doStartTag();
            Assert.fail("JspException should have been thrown");
        } catch (JspException e) {
            // expected behavior
        } catch (Exception e) {
            fail("JspException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#doStartTag} for proper behavior if the required instance
     * property is not set correctly.</p>
     *
     * <p>Sets the <code>type</code> property of the tested instance to value invalid in current context and expects the
     * <code>JspException</code> to be thrown.</p>
     */
    public void testDoStartTag_UnknownType() {
        this.pageContext.setAttribute(TestDataFactory.VALID_REPORT_CATEGORY_PARAM_NAME,
                                      TestDataFactory.VALID_REPORT_CATEGORY.getCategory());
        this.pageContext.setAttribute("NonExistingType", "NonExistingType");

        this.testedInstance.setBillableFilter(TestDataFactory.VALID_BILLABLE_FILTER_PARAM_NAME);
        this.testedInstance.setCategory(TestDataFactory.VALID_REPORT_CATEGORY_PARAM_NAME);
        this.testedInstance.setClientFilter(TestDataFactory.VALID_CLIENT_FILTER_PARAM_NAME);
        this.testedInstance.setEmployeeFilter(TestDataFactory.VALID_EMPLOYEE_FILTER_PARAM_NAME);
        this.testedInstance.setEndDateFilter(TestDataFactory.VALID_END_DATE_FILTER_PARAM_NAME);
        this.testedInstance.setNamespace(TestDataFactory.VALID_REPORT_CONFIGURATION_NAMESPACE);
        this.testedInstance.setProjectFilter(TestDataFactory.VALID_PROJECT_FILTER_PARAM_NAME);
        this.testedInstance.setStartDateFilter(TestDataFactory.VALID_START_DATE_FILTER_PARAM_NAME);
        this.testedInstance.setType("NonExistingType");

        try {
            this.testedInstance.doStartTag();
            Assert.fail("JspException should have been thrown");
        } catch (JspException e) {
            // expected behavior
        } catch (Exception e) {
            fail("JspException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#doStartTag} for proper behavior if the required instance
     * property is not set correctly.</p>
     *
     * <p>Sets the <code>category</code> property of the tested instance to value invalid in current context and expects
     * the <code>JspException</code> to be thrown.</p>
     */
    public void testDoStartTag_NonStringCategory() {
        this.pageContext.setAttribute("NonStringCategory", new Object());
        this.pageContext.setAttribute(TestDataFactory.VALID_REPORT_TYPE_PARAM_NAME,
                                      TestDataFactory.VALID_REPORT_TYPE.getType());

        this.testedInstance.setBillableFilter(TestDataFactory.VALID_BILLABLE_FILTER_PARAM_NAME);
        this.testedInstance.setCategory("NonStringCategory");
        this.testedInstance.setClientFilter(TestDataFactory.VALID_CLIENT_FILTER_PARAM_NAME);
        this.testedInstance.setEmployeeFilter(TestDataFactory.VALID_EMPLOYEE_FILTER_PARAM_NAME);
        this.testedInstance.setEndDateFilter(TestDataFactory.VALID_END_DATE_FILTER_PARAM_NAME);
        this.testedInstance.setNamespace(TestDataFactory.VALID_REPORT_CONFIGURATION_NAMESPACE);
        this.testedInstance.setProjectFilter(TestDataFactory.VALID_PROJECT_FILTER_PARAM_NAME);
        this.testedInstance.setStartDateFilter(TestDataFactory.VALID_START_DATE_FILTER_PARAM_NAME);
        this.testedInstance.setType(TestDataFactory.VALID_REPORT_TYPE_PARAM_NAME);

        try {
            this.testedInstance.doStartTag();
            Assert.fail("JspException should have been thrown");
        } catch (JspException e) {
            // expected behavior
        } catch (Exception e) {
            fail("JspException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#doStartTag} for proper behavior if the required instance
     * property is not set correctly.</p>
     *
     * <p>Sets the <code>type</code> property of the tested instance to value invalid in current context and expects
     * the <code>JspException</code> to be thrown.</p>
     */
    public void testDoStartTag_NonStringType() {
        this.pageContext.setAttribute(TestDataFactory.VALID_REPORT_CATEGORY_PARAM_NAME,
                                      TestDataFactory.VALID_REPORT_CATEGORY.getCategory());
        this.pageContext.setAttribute("NonStringType", new Object());

        this.testedInstance.setBillableFilter(TestDataFactory.VALID_BILLABLE_FILTER_PARAM_NAME);
        this.testedInstance.setCategory(TestDataFactory.VALID_REPORT_CATEGORY_PARAM_NAME);
        this.testedInstance.setClientFilter(TestDataFactory.VALID_CLIENT_FILTER_PARAM_NAME);
        this.testedInstance.setEmployeeFilter(TestDataFactory.VALID_EMPLOYEE_FILTER_PARAM_NAME);
        this.testedInstance.setEndDateFilter(TestDataFactory.VALID_END_DATE_FILTER_PARAM_NAME);
        this.testedInstance.setNamespace(TestDataFactory.VALID_REPORT_CONFIGURATION_NAMESPACE);
        this.testedInstance.setProjectFilter(TestDataFactory.VALID_PROJECT_FILTER_PARAM_NAME);
        this.testedInstance.setStartDateFilter(TestDataFactory.VALID_START_DATE_FILTER_PARAM_NAME);
        this.testedInstance.setType("NonStringType");

        try {
            this.testedInstance.doStartTag();
            Assert.fail("JspException should have been thrown");
        } catch (JspException e) {
            // expected behavior
        } catch (Exception e) {
            fail("JspException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#doStartTag} for proper behavior if the required instance
     * property is not set correctly.</p>
     *
     * <p>Sets the <code>category</code> property of the tested instance to value invalid in current context and expects
     * the <code>JspException</code> to be thrown.</p>
     */
    public void testDoStartTag_EmptyCategory() {
        this.pageContext.setAttribute("EmptyCategory", TestDataFactory.ZERO_LENGTH_STRING);
        this.pageContext.setAttribute(TestDataFactory.VALID_REPORT_TYPE_PARAM_NAME,
                                      TestDataFactory.VALID_REPORT_TYPE.getType());

        this.testedInstance.setBillableFilter(TestDataFactory.VALID_BILLABLE_FILTER_PARAM_NAME);
        this.testedInstance.setCategory("EmptyCategory");
        this.testedInstance.setClientFilter(TestDataFactory.VALID_CLIENT_FILTER_PARAM_NAME);
        this.testedInstance.setEmployeeFilter(TestDataFactory.VALID_EMPLOYEE_FILTER_PARAM_NAME);
        this.testedInstance.setEndDateFilter(TestDataFactory.VALID_END_DATE_FILTER_PARAM_NAME);
        this.testedInstance.setNamespace(TestDataFactory.VALID_REPORT_CONFIGURATION_NAMESPACE);
        this.testedInstance.setProjectFilter(TestDataFactory.VALID_PROJECT_FILTER_PARAM_NAME);
        this.testedInstance.setStartDateFilter(TestDataFactory.VALID_START_DATE_FILTER_PARAM_NAME);
        this.testedInstance.setType(TestDataFactory.VALID_REPORT_TYPE_PARAM_NAME);

        try {
            this.testedInstance.doStartTag();
            Assert.fail("JspException should have been thrown");
        } catch (JspException e) {
            // expected behavior
        } catch (Exception e) {
            fail("JspException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportDisplayTag#doStartTag} for proper behavior if the required instance
     * property is not set correctly.</p>
     *
     * <p>Sets the <code>category</code> property of the tested instance to value invalid in current context and expects
     * the <code>JspException</code> to be thrown.</p>
     */
    public void testDoStartTag_EmptyType() {
        this.pageContext.setAttribute(TestDataFactory.VALID_REPORT_CATEGORY_PARAM_NAME,
                                      TestDataFactory.VALID_REPORT_CATEGORY.getCategory());
        this.pageContext.setAttribute("EmptyType", TestDataFactory.ZERO_LENGTH_STRING);

        this.testedInstance.setBillableFilter(TestDataFactory.VALID_BILLABLE_FILTER_PARAM_NAME);
        this.testedInstance.setCategory(TestDataFactory.VALID_REPORT_CATEGORY_PARAM_NAME);
        this.testedInstance.setClientFilter(TestDataFactory.VALID_CLIENT_FILTER_PARAM_NAME);
        this.testedInstance.setEmployeeFilter(TestDataFactory.VALID_EMPLOYEE_FILTER_PARAM_NAME);
        this.testedInstance.setEndDateFilter(TestDataFactory.VALID_END_DATE_FILTER_PARAM_NAME);
        this.testedInstance.setNamespace(TestDataFactory.VALID_REPORT_CONFIGURATION_NAMESPACE);
        this.testedInstance.setProjectFilter(TestDataFactory.VALID_PROJECT_FILTER_PARAM_NAME);
        this.testedInstance.setStartDateFilter(TestDataFactory.VALID_START_DATE_FILTER_PARAM_NAME);
        this.testedInstance.setType("EmptyType");

        try {
            this.testedInstance.doStartTag();
            Assert.fail("JspException should have been thrown");
        } catch (JspException e) {
            // expected behavior
        } catch (Exception e) {
            fail("JspException was expected but the original exception is : " + e);
        }
    }
}
