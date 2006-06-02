/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

import org.apache.cactus.WebResponse;

import javax.servlet.jsp.JspException;
import java.lang.reflect.Field;


/**
 * This class contains the unit tests for {@link ReportDisplayTag}.
 *
 * 2006-4-25
 * Bug fix for TT-1980, allow null parameter; and bug fix for TT-1979 "Specifiy start date and end date.".
 *
 * Remove testDoStartTagFailEndDateFilterNullWhileStartDateFilterDefined() and
 * testDoStartTagFailStartDateFilterNullWhileEndDateFilterDefined() methods.
 *
 * Add
 * testDoStartTagSuccessEndDateFilterAndStartDateFilterDefined(),
 * testDoStartTagSuccessEndDateFilterNullWhileStartDateFilterDefined(),
 * testDoStartTagSuccessStartDateFilterNullWhileEndDateFilterDefined(),
 * testDoStartTagSuccessEndDateFilterContaindNullStartDateFilterDefined(),
 * testDoStartTagSuccessStartDateFilterContaindNullEndDateFilterDefined(),
 * testDoStartTagSuccessBillableFilterContextValueNullInStringArray(),
 * testDoStartTagSuccessEmployeeFilterContextValueNullInStringArray(),
 * testDoStartTagSuccessProjectFilterContextValueNullInStringArray(),
 * testDoStartTagSuccessStartDateFilterContextValueNullInStringArray(),
 * and testDoStartTagSuccessEndDateFilterContextValueNullInStringArray() methods.
 *
 * @author TCSDEVELOPER
 * @author Xuchen
 * @version 1.0
 */
public class ReportDisplayTagTest extends BaseTimeTrackerReportTest {
    /**
     * This is a constant of be used as filter value in several test cases.
     */
    protected static final String VALUE = "VALUE";
    /**
     * This is the String value that is used as argument to {@link ReportDisplayTag#setCategory(String)} on the {@link
     * #reportDisplayTag} instance in {@link #setUp()}.
     * <p/>
     * Thus this will be the context lookup key used by the tested instance.
     */
    protected static final String CATEGORY_STRING = "CATEGORY";
    /**
     * This is the String value that is used as argument to {@link ReportDisplayTag#setType(String)} on the {@link
     * #reportDisplayTag} instance in {@link #setUp()}.
     * <p/>
     * Thus this will be the context lookup key used by the tested instance.
     */
    protected static final String TYPE_STRING = "TYPE";

    /**
     * This is the String value that is used as argument to {@link ReportDisplayTag#setClientFilter(String)} on the
     * {@link #reportDisplayTag} instance in {@link #setUp()}.
     * <p/>
     * Thus this will be the context lookup key used by the tested instance.
     */
    protected static final String CLIENT_FILTER_STRING = "CLIENT_FILTER";
    /**
     * This is the String value that is used as argument to {@link ReportDisplayTag#setBillableFilter(String)} on the
     * {@link #reportDisplayTag} instance in {@link #setUp()}.
     * <p/>
     * Thus this will be the context lookup key used by the tested instance.
     */

    protected static final String BILLABLE_FILTER_STRING = "BILLABLE_FILTER";
    /**
     * This is the String value that is used as argument to {@link ReportDisplayTag#setProjectFilter(String)} on the
     * {@link #reportDisplayTag} instance in {@link #setUp()}.
     * <p/>
     * Thus this will be the context lookup key used by the tested instance.
     */

    protected static final String PROJECT_FILTER_STRING = "PROJECT_FILTER";
    /**
     * This is the String value that is used as argument to {@link ReportDisplayTag#setEmployeeFilter(String)} on the
     * {@link #reportDisplayTag} instance in {@link #setUp()}.
     * <p/>
     * Thus this will be the context lookup key used by the tested instance.
     */

    protected static final String EMPLOYEE_FILTER_STRING = "EMPLOYEE_FILTER";
    /**
     * This is the String value that is used as argument to {@link ReportDisplayTag#setStartDateFilter(String)} on the
     * {@link #reportDisplayTag} instance in {@link #setUp()}.
     * <p/>
     * Thus this will be the context lookup key used by the tested instance.
     */

    protected static final String START_DATE_FILTER_STRING = "START_DATE_FILTER";
    /**
     * This is the String value that is used as argument to {@link ReportDisplayTag#setEndDateFilter(String)} on the
     * {@link #reportDisplayTag} instance in {@link #setUp()}.
     * <p/>
     * Thus this will be the context lookup key used by the tested instance.
     */

    protected static final String END_DATE_FILTER_STRING = "END_DATE_FILTER";

    /**
     * This is the {@link ReportDisplayTag} instance used in the test cases. It is instantiated in {@link #setUp()}.
     */
    private ReportDisplayTag reportDisplayTag;

    /**
     * This method tests {@link ReportDisplayTag#setBillableFilter(String)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: given value is an empty String
     */
    public void testSetBillableFilterFail() {
        try {
            reportDisplayTag.setBillableFilter("  ");
            fail("should throw");
        } catch (IllegalArgumentException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link ReportDisplayTag#setCategory(String)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: given value is an empty String
     */
    public void testSetCategoryFail() {
        try {
            reportDisplayTag.setCategory("  ");
            fail("should throw");
        } catch (IllegalArgumentException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link ReportDisplayTag#setClientFilter(String)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: given value is an empty String
     */
    public void testSetClientFilterFail() {
        try {
            reportDisplayTag.setClientFilter("  ");
            fail("should throw");
        } catch (IllegalArgumentException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link ReportDisplayTag#setEmployeeFilter(String)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: given value is an empty String
     */
    public void testSetEmployeeFilterFail() {
        try {
            reportDisplayTag.setEmployeeFilter("  ");
            fail("should throw");
        } catch (IllegalArgumentException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link ReportDisplayTag#setEndDateFilter(String)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: given value is an empty String
     */
    public void testSetEndDateFilterFail() {
        try {
            reportDisplayTag.setEndDateFilter("  ");
            fail("should throw");
        } catch (IllegalArgumentException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link ReportDisplayTag#setNamespace(String)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: given value is an empty String
     */
    public void testSetNamespaceFail() {
        try {
            reportDisplayTag.setNamespace("  ");
            fail("should throw");
        } catch (IllegalArgumentException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link ReportDisplayTag#setProjectFilter(String)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: given value is an empty String
     */
    public void testSetProjectFilterFail() {
        try {
            reportDisplayTag.setProjectFilter("  ");
            fail("should throw");
        } catch (IllegalArgumentException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link ReportDisplayTag#setStartDateFilter(String)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: given value is an empty String
     */
    public void testSetStartDateFilterFail() {
        try {
            reportDisplayTag.setStartDateFilter("  ");
            fail("should throw");
        } catch (IllegalArgumentException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link ReportDisplayTag#setType(String)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: given value is an empty String
     */
    public void testSetTypeFail() {
        try {
            reportDisplayTag.setType("  ");
            fail("should throw");
        } catch (IllegalArgumentException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link ReportDisplayTag#setBillableFilter(String)} for correct behavior.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testSetBillableFilter() throws Exception {
        reportDisplayTag.setBillableFilter(VALUE);
        final Field field = reportDisplayTag.getClass().getDeclaredField("billableFilter");
        field.setAccessible(true);
        assertEquals(VALUE, field.get(reportDisplayTag));
    }

    /**
     * This method tests {@link ReportDisplayTag#setClientFilter(String)} for correct behavior.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testSetClientFilter() throws Exception {
        reportDisplayTag.setClientFilter(VALUE);
        final Field field = reportDisplayTag.getClass().getDeclaredField("clientFilter");
        field.setAccessible(true);
        assertEquals(VALUE, field.get(reportDisplayTag));
    }

    /**
     * This method tests {@link ReportDisplayTag#setEmployeeFilter(String)} for correct behavior.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testSetEmployeeFilter() throws Exception {
        reportDisplayTag.setEmployeeFilter(VALUE);
        final Field field = reportDisplayTag.getClass().getDeclaredField("employeeFilter");
        field.setAccessible(true);
        assertEquals(VALUE, field.get(reportDisplayTag));
    }

    /**
     * This method tests {@link ReportDisplayTag#setStartDateFilter(String)} for correct behavior.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testSetStartDateFilter() throws Exception {
        reportDisplayTag.setStartDateFilter(VALUE);
        final Field field = reportDisplayTag.getClass().getDeclaredField("startDateFilter");
        field.setAccessible(true);
        assertEquals(VALUE, field.get(reportDisplayTag));
    }

    /**
     * This method tests {@link ReportDisplayTag#setEndDateFilter(String)} for correct behavior.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testSetEndDateFilter() throws Exception {
        reportDisplayTag.setEndDateFilter(VALUE);
        final Field field = reportDisplayTag.getClass().getDeclaredField("endDateFilter");
        field.setAccessible(true);
        assertEquals(VALUE, field.get(reportDisplayTag));
    }

    /**
     * This method tests {@link ReportDisplayTag#setProjectFilter(String)} for correct behavior.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testSetProjectFilter() throws Exception {
        reportDisplayTag.setProjectFilter(VALUE);
        final Field field = reportDisplayTag.getClass().getDeclaredField("projectFilter");
        field.setAccessible(true);
        assertEquals(VALUE, field.get(reportDisplayTag));
    }

    /**
     * This method tests {@link ReportDisplayTag#setCategory(String)} for correct behavior.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testSetCategory() throws Exception {
        reportDisplayTag.setCategory(VALUE);
        final Field field = reportDisplayTag.getClass().getDeclaredField("category");
        field.setAccessible(true);
        assertEquals(VALUE, field.get(reportDisplayTag));
    }

    /**
     * This method tests {@link ReportDisplayTag#setNamespace(String)} for correct behavior.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testSetNamespace() throws Exception {
        reportDisplayTag.setNamespace(VALUE);
        final Field field = reportDisplayTag.getClass().getDeclaredField("namespace");
        field.setAccessible(true);
        assertEquals(VALUE, field.get(reportDisplayTag));
    }

    /**
     * This method tests {@link ReportDisplayTag#setType(String)} for correct behavior.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testSetType() throws Exception {
        reportDisplayTag.setType(VALUE);
        final Field field = reportDisplayTag.getClass().getDeclaredField("type");
        field.setAccessible(true);
        assertEquals(VALUE, field.get(reportDisplayTag));
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: category is <tt>null</tt>
     */
    public void testDoStartTagFailCategoryNull() {
        try {
            reportDisplayTag = new ReportDisplayTag();
            reportDisplayTag.setPageContext(pageContext);
            reportDisplayTag.setNamespace(DEFAULT_CONFIGURATION_NAMESPACE);
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: type is <tt>null</tt>
     */
    public void testDoStartTagFailTypeNull() {
        try {
            reportDisplayTag = new ReportDisplayTag();
            reportDisplayTag.setPageContext(pageContext);
            reportDisplayTag.setNamespace(DEFAULT_CONFIGURATION_NAMESPACE);
            reportDisplayTag.setCategory(CATEGORY_STRING);
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: category context value is <tt>null</tt>
     */
    public void testDoStartTagFailCategoryContextValueNull() {
        try {
            pageContext.removeAttribute(CATEGORY_STRING);
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);
        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: category context value is  an empty String
     */
    public void testDoStartTagFailCategoryContextValueEmpty() {
        try {
            pageContext.setAttribute(CATEGORY_STRING, "  ");
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: category context value is not of type {@link String}
     */
    public void testDoStartTagFailCategoryContextValueNoString() {
        try {
            pageContext.setAttribute(CATEGORY_STRING, new Object());
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: category context value is no valid {@link ReportCategory} name
     */
    public void testDoStartTagFailCategoryContextValueNoCategoryName() {
        try {
            pageContext.setAttribute(CATEGORY_STRING, "blah");
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: type context value is <tt>null</tt>
     */
    public void testDoStartTagFailTypeContextValueNull() {
        try {
            pageContext.removeAttribute(TYPE_STRING);
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: type context value is  an empty String
     */
    public void testDoStartTagFailTypeContextValueEmpty() {
        try {
            pageContext.setAttribute(TYPE_STRING, "  ");
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: type context value is not of type {@link String}
     */
    public void testDoStartTagFailTypeContextValueNoString() {
        try {
            pageContext.setAttribute(TYPE_STRING, new Object());
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: type context value is no valid {@link ReportType} name
     */
    public void testDoStartTagFailTypeContextValueNoTypeName() {
        try {
            pageContext.setAttribute(TYPE_STRING, "blah");
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: no filters are defined
     */
    public void testDoStartTagFailNoFilters() {
        try {
            pageContext.removeAttribute(CLIENT_FILTER_STRING);
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: client filter context value is empty String
     */
    public void testDoStartTagFailClientFilterContextValueEmptyString() {
        try {
            pageContext.setAttribute(CLIENT_FILTER_STRING, "   ");
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: client filter context value is a String[] containing an empty String
     */
    public void testDoStartTagFailClientFilterContextValueEmptyStringInStringArray() {
        try {
            pageContext.setAttribute(CLIENT_FILTER_STRING, new String []{"  "});
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: client filter context value is not of type {@link String} or {@link String}[]
     */
    public void testDoStartTagFailClientFilterContextValueWrongType() {
        try {
            pageContext.setAttribute(CLIENT_FILTER_STRING, new Object());
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: billable filter context value is empty String
     */
    public void testDoStartTagFailBillableFilterContextValueEmptyString() {
        try {
            pageContext.setAttribute(BILLABLE_FILTER_STRING, "   ");
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: billable filter context value is a String[] containing an empty String
     */
    public void testDoStartTagFailBillableFilterContextValueEmptyStringInStringArray() {
        try {
            pageContext.setAttribute(BILLABLE_FILTER_STRING, new String []{"  "});
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: billable filter context value is not of type {@link String} or {@link String}[]
     */
    public void testDoStartTagFailBillableFilterContextValueWrongType() {
        try {
            pageContext.setAttribute(BILLABLE_FILTER_STRING, new Object());
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: employee filter context value is empty String
     */
    public void testDoStartTagFailEmployeeFilterContextValueEmptyString() {
        try {
            pageContext.setAttribute(EMPLOYEE_FILTER_STRING, "   ");
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: employee filter context value is a String[] containing an empty String
     */
    public void testDoStartTagFailEmployeeFilterContextValueEmptyStringInStringArray() {
        try {
            pageContext.setAttribute(EMPLOYEE_FILTER_STRING, new String []{"  "});
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: employee filter context value is not of type {@link String} or {@link String}[]
     */
    public void testDoStartTagFailEmployeeFilterContextValueWrongType() {
        try {
            pageContext.setAttribute(EMPLOYEE_FILTER_STRING, new Object());
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: project filter context value is empty String
     */
    public void testDoStartTagFailProjectFilterContextValueEmptyString() {
        try {
            pageContext.setAttribute(PROJECT_FILTER_STRING, "   ");
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: project filter context value is a String[] containing an empty String
     */
    public void testDoStartTagFailProjectFilterContextValueEmptyStringInStringArray() {
        try {
            pageContext.setAttribute(PROJECT_FILTER_STRING, new String []{"  "});
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: project filter context value is not of type {@link String} or {@link String}[]
     */
    public void testDoStartTagFailProjectFilterContextValueWrongType() {
        try {
            pageContext.setAttribute(PROJECT_FILTER_STRING, new Object());
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: end date filter context value is empty String
     */
    public void testDoStartTagFailStartDateFilterContextValueEmptyString() {
        try {
            pageContext.setAttribute(START_DATE_FILTER_STRING, "   ");
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: end date filter context value is a String[] containing an empty String
     */
    public void testDoStartTagFailStartDateFilterContextValueEmptyStringInStringArray() {
        try {
            pageContext.setAttribute(START_DATE_FILTER_STRING, new String []{"  "});
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: end date filter context value is not of type {@link String} or {@link String}[]
     */
    public void testDoStartTagFailStartDateFilterContextValueWrongType() {
        try {
            pageContext.setAttribute(START_DATE_FILTER_STRING, new Object());
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: end date filter context value is empty String
     */
    public void testDoStartTagFailEndDateFilterContextValueEmptyString() {
        try {
            pageContext.setAttribute(END_DATE_FILTER_STRING, "   ");
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: end date filter context value is a String[] containing an empty String
     */
    public void testDoStartTagFailEndDateFilterContextValueEmptyStringInStringArray() {
        try {
            pageContext.setAttribute(END_DATE_FILTER_STRING, new String []{"  "});
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: end date filter context value is not of type {@link String} or {@link String}[]
     */
    public void testDoStartTagFailEndDateFilterContextValueWrongType() {
        try {
            pageContext.setAttribute(END_DATE_FILTER_STRING, new Object());
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct behavior.
     * Both end date and start date are specified. It should be valid.
     * @throws Exception to JUnit
     */
    public void testDoStartTagSuccessEndDateFilterAndStartDateFilterDefined() throws Exception {
        pageContext.setAttribute(END_DATE_FILTER_STRING, "12-31-2007");
        pageContext.setAttribute(START_DATE_FILTER_STRING, "12-31-2006");
        reportDisplayTag.doStartTag();
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct behavior.
     * End date has not been specified, while start date is specified. It should be valid.
     * @throws Exception to JUnit
     */
    public void testDoStartTagSuccessEndDateFilterNullWhileStartDateFilterDefined() throws Exception {
        pageContext.removeAttribute(END_DATE_FILTER_STRING);
        pageContext.setAttribute(START_DATE_FILTER_STRING, "12-31-2006");
        reportDisplayTag.doStartTag();
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct behavior.
     * Start date has not been specified, while end date is specified. It should be valid.
     * @throws Exception to JUnit
     */
    public void testDoStartTagSuccessStartDateFilterNullWhileEndDateFilterDefined() throws Exception {
        pageContext.removeAttribute(START_DATE_FILTER_STRING);
        pageContext.setAttribute(END_DATE_FILTER_STRING, "12-31-2006");
        reportDisplayTag.doStartTag();
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct behavior.
     * End date filter's value contains null (not been specified), and start date is specified. It should be valid.
     * @throws Exception to JUnit
     */
    public void testDoStartTagSuccessEndDateFilterContaindNullStartDateFilterDefined() throws Exception {
        pageContext.setAttribute(END_DATE_FILTER_STRING, new String[]{null});
        pageContext.setAttribute(START_DATE_FILTER_STRING, "12-31-2006");
        reportDisplayTag.doStartTag();
    }


    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct behavior.
     * End date filter's value contains null (not been specified), and start date is specified. It should be valid.
     * @throws Exception to JUnit
     */
    public void testDoStartTagSuccessStartDateFilterContaindNullEndDateFilterDefined() throws Exception {
        pageContext.setAttribute(START_DATE_FILTER_STRING, new String[]{null});
        pageContext.setAttribute(END_DATE_FILTER_STRING, "12-31-2006");
        reportDisplayTag.doStartTag();
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct behavior.
     * It should allow null parameter.
     * @throws Exception to JUnit
     */
    public void testDoStartTagSuccessBillableFilterContextValueNullInStringArray() throws Exception {
        pageContext.setAttribute(BILLABLE_FILTER_STRING, new String []{null});
        reportDisplayTag.doStartTag();
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct behavior.
     * It should allow null parameter.
     * @throws Exception to JUnit
     */
    public void testDoStartTagSuccessEmployeeFilterContextValueNullInStringArray() throws Exception {
        pageContext.setAttribute(EMPLOYEE_FILTER_STRING, new String []{null});
        reportDisplayTag.doStartTag();
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct behavior.
     * It should allow null parameter.
     * @throws Exception to JUnit
     */
    public void testDoStartTagSuccessProjectFilterContextValueNullInStringArray() throws Exception {
        pageContext.setAttribute(PROJECT_FILTER_STRING, new String []{null});
        reportDisplayTag.doStartTag();
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct behavior.
     * It should allow null parameter.
     * @throws Exception to JUnit
     */
    public void testDoStartTagSuccessStartDateFilterContextValueNullInStringArray() throws Exception {
        pageContext.setAttribute(START_DATE_FILTER_STRING, new String []{null});
        reportDisplayTag.doStartTag();
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct behavior.
     * It should allow null parameter.
     * @throws Exception to JUnit
     */
    public void testDoStartTagSuccessEndDateFilterContextValueNullInStringArray() throws Exception {
        pageContext.setAttribute(END_DATE_FILTER_STRING, new String []{null});
        reportDisplayTag.doStartTag();
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: start date filter context value is and end date filter context value  are
     * <tt>String[]</tt>s of different length
     */
    public void testDoStartTagFailStartDateFilterAndEndDateFilterListHaveDifferentLength() {
        try {
            pageContext.setAttribute(START_DATE_FILTER_STRING, new String[]{VALUE, VALUE});
            pageContext.setAttribute(END_DATE_FILTER_STRING, new String[]{VALUE, VALUE, VALUE});
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: client filter context value was an empty <tt>String[]</tt>
     */
    public void testDoStartTagFailClientFilterContextValueEmptyStringArray() {
        try {
            pageContext.setAttribute(CLIENT_FILTER_STRING, new String[]{});
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);
        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: billable filter context value was an empty <tt>String[]</tt>
     */
    public void testDoStartTagFailBillableFilterContextValueEmptyStringArray() {
        try {
            pageContext.setAttribute(BILLABLE_FILTER_STRING, new String[]{});
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);
        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: employee filter context value was an empty <tt>String[]</tt>
     */
    public void testDoStartTagFailEmployeeFilterContextValueEmptyStringArray() {
        try {
            pageContext.setAttribute(EMPLOYEE_FILTER_STRING, new String[]{});
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);
        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: project filter context value was an empty <tt>String[]</tt>
     */
    public void testDoStartTagFailProjectFilterContextValueEmptyStringArray() {
        try {
            pageContext.setAttribute(PROJECT_FILTER_STRING, new String[]{});
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);

        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: start date filter context value was an empty <tt>String[]</tt>
     */
    public void testDoStartTagFailStartDateFilterContextValueEmptyStringArray() {
        try {
            pageContext.setAttribute(START_DATE_FILTER_STRING, new String[]{});
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);
        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: end date filter context value was an empty <tt>String[]</tt>
     */
    public void testDoStartTagFailEndDateFilterContextValueEmptyStringArray() {
        try {
            pageContext.setAttribute(END_DATE_FILTER_STRING, new String[]{});
            reportDisplayTag.doStartTag();
            fail("should throw");
        } catch (JspException expected) {
            checkWrappedExceptionHasCorrectType(expected);
        }
    }

    /**
     * This method tests {@link ReportDisplayTag#doStartTag()} for correct behavior.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testDoStartTag() throws Exception {
        reportDisplayTag.doStartTag();
    }

    /**
     * This method checks the result of {@link #testDoStartTag()}. It is called by the cactus framework with the
     * response written by {@link #testDoStartTag()}.
     *
     * @param response the response written by {@link #testDoStartTag()}
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void endDoStartTag(final WebResponse response) throws Exception {
        String rendered = response.getText();

        // some synthetic root element needs to be around the rendered data,
        // as the data contains multiple elements on root level, and multiple
        // roots are no valid XML, so for being able to check the equality
        // using XML parsers, a synthetic root element needs to be added.

        rendered = "<BODY>" + rendered + "</BODY>";

        checkContentsAreEqual("expectedTagRenderResult.xml", rendered);
    }

    /**
     * This method checks whether the given Exception as a {@link ReportConfigurationException} as rootCause.
     *
     * @param expected the exception to be checked
     *
     * @throws junit.framework.AssertionFailedError
     *          in case the exception's root cause is of different type
     */
    private void checkWrappedExceptionHasCorrectType(final JspException expected) {
        final Throwable rootCause = expected.getRootCause();
        if (!(rootCause instanceof ReportConfigurationException)) {
            fail("should have thrown wrapped ReportConfigurationException.");
        }
    }

    /**
     * This method does the test setup needed.
     *
     * @throws Exception in case some unexpected Exception occurs
     */
    protected void setUp() throws Exception {
        super.setUp();
        reportDisplayTag = new ReportDisplayTag();
        reportDisplayTag.setPageContext(pageContext);
        reportDisplayTag.setNamespace(DEFAULT_CONFIGURATION_NAMESPACE);
        reportDisplayTag.setCategory(CATEGORY_STRING);
        reportDisplayTag.setType(TYPE_STRING);
        reportDisplayTag.setClientFilter(CLIENT_FILTER_STRING);
        reportDisplayTag.setBillableFilter(BILLABLE_FILTER_STRING);
        reportDisplayTag.setProjectFilter(PROJECT_FILTER_STRING);
        reportDisplayTag.setEmployeeFilter(EMPLOYEE_FILTER_STRING);
        reportDisplayTag.setStartDateFilter(START_DATE_FILTER_STRING);
        reportDisplayTag.setEndDateFilter(END_DATE_FILTER_STRING);

        pageContext.setAttribute(CATEGORY_STRING, ReportCategory.EXPENSE.getCategory());
        pageContext.setAttribute(TYPE_STRING, ReportType.CLIENT.getType());
        pageContext.setAttribute(CLIENT_FILTER_STRING, "client1");
    }
}