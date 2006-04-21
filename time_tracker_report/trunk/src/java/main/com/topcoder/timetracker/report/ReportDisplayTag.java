/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.ArrayList;
import java.util.List;


/**
 * The ReportDisplayTag is a custom JSP Tag that is responsible for outputting the ReportData as a HTML table along with
 * the summarization information.
 * <p/>
 * Note: This tag is not a container tag and hence cannot contain subtags within this tag. This tag will render itself
 * when the JSPContainer will process the start of this tag by calling the {@link #doStartTag()} method.
 * <p/>
 * The attributes of this Tag are: <ul><li>namespace: (required=true): The namespace to load report configuration.
 * (Using the ConfigManager) </li>  <li>type: (required=true): The parameter name in the HttpRequest object or the
 * attribute in the HttpSession object, whose value specifies the type of the report(Employee,Project or Client Report).
 * </li> <li>category: (required=true): The parameter name in the HttpRequest object or the attribute in the HttpSession
 * object, whose value specifies the category of the report(Time,Expense or TimeExpense Report). </li> <li>clientFilter:
 * (required=false): The parameter name in the HttpRequest object or the attribute in the HttpSession object, whose
 * value(s) specifies the Client Name to be used as a filter for the Report. </li> <li>employeeFilter: (required=false):
 * The parameter name in the HttpRequest object or the attribute in the HttpSession object, whose value(s) specifies the
 * Employee Name to be used as a filter for the Report. </li> <li>projectFilter: (required=false) : The parameter name
 * in the HttpRequest object or the attribute in the HttpSession object, whose value(s) specifies the Project Name to be
 * used as a filter for the Report. </li>  <li>billableFilter: (required=false) : The parameter name in the HttpRequest
 * object or the attribute in the HttpSession object, whose value specifies if only Billable entries needs to be
 * considered for the Report. </li> <li>startdateFilter: (required=false): The parameter name in the HttpRequest object
 * or the attribute in the HttpSession object, whose value specifies the Start range for the Date Entries that need be
 * considered for the Report. </li>  <li>enddateFilter: (required=false): The parameter name in the HttpRequest object
 * or the attribute in the HttpSession object, whose value specifies the End range for the Date Entries that need be
 * considered for the Report. </li></ul>
 * <p/>
 * This class is as such not thread safe, but this class is invoked by the JSP Container and the thread-safe access to
 * the class depends on the handling by the JSPContainer.
 *
 * @author fastprogrammer, traugust
 * @version 1.0
 * 
 * @author Xuchen
 * 2006-4-21
 * Bug fix for TT-1980: safeCreateEqualityFilter(), safeCreateRangeFilter(), and safeLookupContextValueArray() to allow
 * null input parameters.
 */
public class ReportDisplayTag extends TagSupport {

    /**
     * This is a constant and defines the format in which the report is expected for display. This constant is passed
     * during the fetch of a Report instance from the Report factory, along with the category of the Report.
     */
    private static final String FORMAT = "HTML";

    /**
     * Represents the reportFactory instance used in this tag. This is a static field, as the instance is to be shared
     * between all instances of ReportDisplayTag.
     * <p/>
     * <b>Note:</b> As the initialization of this field is executed lazily upon first usage, it is necessary that all
     * instances call {@link #getReportFactory()} to obtain or create (depending on whether the caller is the first to
     * call this method) the value of this field instead of accessing the field directly.
     * <p/>
     * This static field holds a ReportFactory instance, which is used in the {@link #doStartTag()} method to obtain an
     * appropriate instance of the Report.
     */
    private static ReportFactory reportFactory;

    /**
     * Represents the namespace in {@link com.topcoder.util.config.ConfigManager}, from which the configuration
     * properties for the {@link ReportConfiguration} are looked up.
     * <p/>
     * This tag attribute is required.
     * <p/>
     * Initially this member is initialized to <tt>null</tt>, but before the call to the {@link #doStartTag()} method,
     * this member will be initialized with a value by the JSP container, by calling the {@link #setNamespace(String)}
     * method.
     */
    private String namespace = null;

    /**
     * Represents the key to be used for looking up the type of Report(as String representing a {@link ReportType}) from
     * all contexts (Page, Request, Session, Application) using {@link javax.servlet.jsp.PageContext
     * #findAttribute(String)}.
     * <p/>
     * This tag attribute is required.
     * <p/>
     * Initially this member is initialized to <tt>null</tt>, but before the call to the {@link #doStartTag()} method,
     * this member will be initialized with a value by the JSP container, by calling the {@link #setType(String)}
     * method.
     */
    private String type = null;

    /**
     * Represents the key to be used for looking up the category of Report(as String representing a {@link
     * ReportCategory}) from all contexts (Page,Request,Session,Application) using {@link
     * javax.servlet.jsp.PageContext#findAttribute(String)}.
     * <p/>
     * This tag attribute is required.
     * <p/>
     * Initially this member is initialized to <tt>null</tt>, but before the call to the {@link #doStartTag()} method,
     * this member will be initialized with a value by the JSP container, by calling the {@link #setCategory(String)}
     * method
     */
    private String category = null;

    /**
     * Represents the key to be used for looking up the optional client filter of the Report(as String representing a
     * value in an {@link EqualityFilter} for column {@link Column#CLIENT}) from all contexts
     * (Page,Request,Session,Application) using {@link javax.servlet.jsp.PageContext#findAttribute(String)}.
     * <p/>
     * This field can hold <tt>null</tt> value indicating that the filter is not set.
     */
    private String clientFilter = null;

    /**
     * Represents the key to be used for looking up the optional employee filter of the Report(as String representing a
     * value in an {@link EqualityFilter} for column {@link Column#EMPLOYEE}) from all contexts
     * (Page,Request,Session,Application) using {@link javax.servlet.jsp.PageContext#findAttribute(String)}.
     * <p/>
     * This field can hold <tt>null</tt> value indicating that the filter is not set.
     */
    private String employeeFilter = null;

    /**
     * Represents the key to be used for looking up the optional project filter of the Report(as String representing a
     * value in an {@link EqualityFilter} for column {@link Column#PROJECT}) from all contexts
     * (Page,Request,Session,Application) using {@link javax.servlet.jsp.PageContext#findAttribute(String)}.
     * <p/>
     * This field can hold <tt>null</tt> value indicating that the filter is not set.
     */
    private String projectFilter = null;

    /**
     * Represents the key to be used for looking up the optional billable filter of the Report(as String representing a
     * value in an {@link EqualityFilter} for column {@link Column#BILLABLE}) from all contexts
     * (Page,Request,Session,Application) using {@link javax.servlet.jsp.PageContext#findAttribute(String)}.
     * <p/>
     * This field can hold <tt>null</tt> value indicating that the filter is not set.
     */
    private String billableFilter = null;

    /**
     * Represents the key to be used for looking up the lower bound of an optional date filter of the Report(as String
     * representing a lowerValue in a {@link RangeFilter} for column {@link Column#DATE}) from all contexts
     * (Page,Request,Session,Application) using {@link javax.servlet.jsp.PageContext#findAttribute(String)}.
     * <p/>
     * This field can hold <tt>null</tt> value indicating that the filter is not set.
     */
    private String startDateFilter = null;

    /**
     * Represents the key to be used for looking up the upper bound of an optional date filter of the Report(as String
     * representing an upperValue in a {@link RangeFilter} for column {@link Column#DATE}) from all contexts
     * (Page,Request,Session,Application) using {@link javax.servlet.jsp.PageContext#findAttribute(String)}.
     * <p/>
     * This field can hold <tt>null</tt> value indicating that the filter is not set.
     */
    private String endDateFilter = null;

    /**
     * Empty constructor.
     */
    public ReportDisplayTag() {
    }

    /**
     * This method outputs the ReportData in the HTML Table format along with the summarization notes.
     *
     * @return {@link javax.servlet.jsp.tagext.Tag#SKIP_BODY} (since this is not a container tag and the body of this
     *         tag is not supposed to contain anything)
     *
     * @throws JspException if there are problems during the processing of the tag
     */
    public int doStartTag() throws JspException {
        try {
            if (namespace == null) {
                throw new ReportConfigurationException("The required tag attribute named [namespace] was null.");
            }
            //empty string not possible as setter doesn't allow this

            //lookup context values. all illegal arg processing is made inside the method
            final String categoryValue = safeLookupValidStringContextValue("category", category);
            final String typeValue = safeLookupValidStringContextValue("type", type);

            //create filters list
            final List filters = createFilters();

            //find the report
            final ReportCategory reportCategory = lookupReportCategory(categoryValue);
            final Report report = getReportFactory().getReport(FORMAT, reportCategory);

            // execute the report
            final ReportType reportType = lookupReportType(typeValue);
            final String output = report.execute(namespace, reportType, filters);

            // write result to page
            pageContext.getOut().write(output);

            return SKIP_BODY;
        } catch (Exception e) {
            throw new JspException("Unable to render the report.", e);
        }
    }

    /**
     * Sets the <em>namespace</em> attribute. This method is called by the JSP container if the attribute
     * <em>namespace</em> is specified in the tag usage.
     *
     * @param namespace namespace in ConfigManager, from which the configuration properties for the ReportConfiguration
     *                  are read
     *
     * @throws IllegalArgumentException if the namespace passed is an empty (trim'd) String
     */
    public void setNamespace(final String namespace) {
        if (namespace.trim().length() == 0) {
            throw new IllegalArgumentException("The given tag attribute named [namespace] was an empty String.");
        }

        this.namespace = namespace;
    }

    /**
     * Sets the <em>type</em> attribute. This method is called by the JSP container if the attribute <em>type</em> is
     * specified in the tag usage.
     *
     * @param type the name of the context variable whose value specifies the type of the report(Employee,Project or
     *             Client Report).
     *
     * @throws IllegalArgumentException if the type passed is an empty (trim'd) String
     */
    public void setType(final String type) {
        if (type.trim().length() == 0) {
            throw new IllegalArgumentException("The given tag attribute named [type] was an empty String.");
        }

        this.type = type;
    }

    /**
     * Sets the <em>category</em> attribute.This method is called by the JSP container if the attribute
     * <em>category</em> is specified in the tag usage.
     *
     * @param category the name of the context variable whose value specifies the category of the report(Time,Expense or
     *                 TimeExpense Report).
     *
     * @throws IllegalArgumentException if the category passed is an empty (trim'd) String
     */
    public void setCategory(final String category) {
        if (category.trim().length() == 0) {
            throw new IllegalArgumentException("The given tag attribute named [category] was an empty String.");
        }

        this.category = category;
    }

    /**
     * Sets the <em>clientfilter</em> attribute. This method is called by the JSP container if the attribute
     * <em>clientfilter</em> is specified in the tag usage.
     *
     * @param clientFilter the name of the context variable whose value(s) specifies the Client Name(s) to be used as a
     *                     filter for the Report.
     *
     * @throws IllegalArgumentException if the clientFilter passed is an empty (trim'd) String
     */
    public void setClientFilter(final String clientFilter) {
        if (clientFilter.trim().length() == 0) {
            throw new IllegalArgumentException("The given tag attribute named [clientFilter] was an empty String.");
        }

        this.clientFilter = clientFilter;
    }

    /**
     * Sets the <em>employeeFilter</em> attribute. This method is called by the JSP container if the attribute
     * <em>employeeFilter</em> is specified in the tag usage.
     *
     * @param employeeFilter the name of the context variable whose value(s) specifies the Employee Name(s) to be used
     *                       as a filter for the Report.
     *
     * @throws IllegalArgumentException if the employeeFilter passed is an empty (trim'd) String
     */
    public void setEmployeeFilter(final String employeeFilter) {
        if (employeeFilter.trim().length() == 0) {
            throw new IllegalArgumentException("The given tag attribute named [employeeFilter] was an empty String.");
        }

        this.employeeFilter = employeeFilter;
    }

    /**
     * Sets the <em>projectFilter</em> attribute. This method is called by the JSP container if the attribute
     * <em>projectFilter</em> is specified in the tag usage.
     *
     * @param projectFilter the name of the context variable whose value(s) specifies the Project Name(s) to be used as
     *                      a filter for the Report.
     *
     * @throws IllegalArgumentException if the projectFilter passed is an empty (trim'd) String
     */
    public void setProjectFilter(final String projectFilter) {
        if (projectFilter.trim().length() == 0) {
            throw new IllegalArgumentException("The given tag attribute named [projectFilter] was an empty String.");
        }

        this.projectFilter = projectFilter;
    }

    /**
     * Sets the <em>billableFilter</em> attribute. This method is called by the JSP container if the attribute
     * <em>billableFilter</em> is specified in the tag usage.
     *
     * @param billableFilter the name of the context variable whose value specifies if only Billable entries needs to be
     *                       considered for the Report.
     *
     * @throws IllegalArgumentException if the billableFilter passed is an empty (trim'd) String
     */
    public void setBillableFilter(final String billableFilter) {
        if (billableFilter.trim().length() == 0) {
            throw new IllegalArgumentException("The given tag attribute named [billableFilter] was an empty String.");
        }

        this.billableFilter = billableFilter;
    }

    /**
     * Sets the <em>startdateFilter</em> attribute. This method is called by the JSP container if the attribute
     * <em>startdateFilter</em> is specified in the tag usage.
     *
     * @param startDateFilter the name of the context variable whose value specifies the lower bound for the Date
     *                        Entries that need be considered for the Report.
     *
     * @throws IllegalArgumentException if the startDateFilter passed is an empty (trim'd) String
     */
    public void setStartDateFilter(final String startDateFilter) {
        if (startDateFilter.trim().length() == 0) {
            throw new IllegalArgumentException("The given tag attribute named [startDateFilter] was an empty String.");
        }

        this.startDateFilter = startDateFilter;
    }

    /**
     * Sets the <em>enddateFilter</em> attribute. This method is called by the JSP container if the attribute
     * <em>enddateFilter</em> is specified in the tag usage.
     *
     * @param endDateFilter the name of the context variable whose value specifies the upper bound for the Date Entries
     *                      that need be considered for the Report.
     *
     * @throws IllegalArgumentException if the endDateFilter passed is an empty (trim'd) String
     */
    public void setEndDateFilter(final String endDateFilter) {
        if (endDateFilter.trim().length() == 0) {
            throw new IllegalArgumentException("The given tag attribute named [endDateFilter] was an empty String.");
        }

        this.endDateFilter = endDateFilter;
    }

    /**
     * Creates the {@link Filter} objects from the context data according to this instance's field values and returns a
     * List containing all the Filters created.
     * <p/>
     * This method will basically create the Filter objects without bothering about the fact that the Filters are
     * actually valid for the Report type and category specified. This validation will be done when the
     * ReportConfiguration object for the Report is created. Basically this method will create Filter objects if the
     * value for the specific {@link FilterCategory} if available in the coming input Request.
     * <p/>
     * For every call to this method a new List is created containing the Filter objects and returned.
     *
     * @return A List of Filter objects for the Report. The Filter object may or may not be valid for the Report under
     *         consideration. This check will be done during the creation of ReportConfiguration.
     *
     * @throws ReportConfigurationException if there were errors during the creation of Filter or there was not at least
     *                                      one filter created
     */
    private List createFilters() throws ReportConfigurationException {
        // your code here
        final List ret = new ArrayList();
        final EqualityFilter clientFilterInstance = safeCreateEqualityFilter(clientFilter, Column.CLIENT,
            FilterCategory.CLIENT);
        if (clientFilterInstance != null) {
            ret.add(clientFilterInstance);
        }
        final EqualityFilter employeeFilterInstance = safeCreateEqualityFilter(employeeFilter, Column.EMPLOYEE,
            FilterCategory.EMPLOYEE);
        if (employeeFilterInstance != null) {
            ret.add(employeeFilterInstance);
        }
        final EqualityFilter projectFilterInstance = safeCreateEqualityFilter(projectFilter, Column.PROJECT,
            FilterCategory.PROJECT);
        if (projectFilterInstance != null) {
            ret.add(projectFilterInstance);
        }
        final EqualityFilter billableFilterInstance = safeCreateEqualityFilter(billableFilter, Column.BILLABLE,
            FilterCategory.BILLABLE);
        if (billableFilterInstance != null) {
            ret.add(billableFilterInstance);
        }
        final RangeFilter dateFilterInstance = safeCreateRangeFilter(startDateFilter, endDateFilter, Column.DATE,
            FilterCategory.DATE);
        if (dateFilterInstance != null) {
            ret.add(dateFilterInstance);
        }
        if (ret.isEmpty()) {
            throw new ReportConfigurationException(
                "At least one filter needs to be defined for rendering a report.");
        }
        return ret;
    }

    /**
     * This method creates a new {@link EqualityFilter} for the given {@link Column} if the given filterValueAttribute
     * is a non-<tt>null</tt>, non-empty (trim'd) String that refers to a non-<tt>null</tt> request parameter or context
     * variable of type {@link String} or <tt>String[]</tt>.
     *
     * @param filterValueAttribute the lookup key with which to lookup the actual filter value(s) in request parameters
     *                             or the context of this {@link javax.servlet.jsp.tagext.Tag}.
     * @param column               the column for which to create the filter
     * @param filterCategory       the category of the filter to be created
     *
     * @return the filter created or <tt>null</tt> if no filter value(s) were found for the given filterValueAttribute
     *
     * @throws ReportConfigurationException in case the value looked up from context is not of type {@link String} or
     *                                      <tt>String[]</tt> or the <tt>String[]</tt> found contained
     *                                      <tt>null</tt>-values or empty (trim'd) Strings
     */
    private EqualityFilter safeCreateEqualityFilter(final String filterValueAttribute, final Column column,
                                                    final FilterCategory filterCategory)
        throws ReportConfigurationException {
        if (filterValueAttribute != null && filterValueAttribute.trim().length() == 0) {
            return null;
        }

        final String[] filterValues = safeLookupContextValueArray(filterValueAttribute);
        if (filterValues == null) {
            return null;
        }

        final EqualityFilter filter = new EqualityFilter(column, filterCategory);
        for (int i = 0; i < filterValues.length; i++) {
            filter.addFilterValue(filterValues[i]);
        }
        return filter;

    }

    /**
     * This method creates a new {@link EqualityFilter} for the given {@link Column} if the given lowerBoundAttribute
     * and upperBoundAttribute are both non-<tt>null</tt>, non-empty (trim'd) Strings that both refers to
     * non-<tt>null</tt> request parameters or context variables of type {@link String} or <tt>String[]</tt>.
     *
     * @param lowerBoundAttribute the lookup key with which to lookup the actual lower bound value(s) in request
     *                            parameters or the context of this {@link javax.servlet.jsp.tagext.Tag}.
     * @param upperBoundAttribute the lookup key with which to lookup the actual upper bound value(s) in request
     *                            parameters or the context of this {@link javax.servlet.jsp.tagext.Tag}.
     * @param column              the column for which to create the filter
     * @param filterCategory      the category for the filter to be created
     *
     * @return the filter created or <tt>null</tt> if no filter values were found for the given lowerBoundAttribute and
     *         upperBoundAttribute
     *
     * @throws ReportConfigurationException in case the any of the values looked up from context is not of type {@link
     *                                      String} or <tt>String[]</tt> or a <tt>String[]</tt> found contained
     *                                      <tt>null</tt>-values or empty (trim'd) Strings or the <tt>String[]</tt>s
     *                                      found did not contain se same amount of elements or only one of the values
     *                                      was found during lookup
     */
    private RangeFilter safeCreateRangeFilter(final String lowerBoundAttribute, final String upperBoundAttribute,
                                              final Column column,
                                              final FilterCategory filterCategory)
        throws ReportConfigurationException {
        if ((lowerBoundAttribute != null && lowerBoundAttribute.trim().length() == 0)
            || (upperBoundAttribute != null && upperBoundAttribute.trim().length() == 0)) {
            return null;
        }
        final String[] lowerBoundValues = safeLookupContextValueArray(lowerBoundAttribute);
        final String[] upperBoundValues = safeLookupContextValueArray(upperBoundAttribute);

        if (lowerBoundValues == null) {
            if (upperBoundValues != null) {
                throw new ReportConfigurationException("The context value for [" + upperBoundAttribute
                    + "] was defined while the context value for [" + lowerBoundAttribute
                    + "] was undefined, so no lower bound(s) exist for creation of range filter(s).");
            } else {
                return null;
            }
        }
        if (upperBoundValues == null) {
            throw new ReportConfigurationException("The context value for [" + lowerBoundAttribute
                + "] was defined while the context value for [" + upperBoundAttribute
                + "] was undefined, so no upper bound(s) exist for creation of range filter(s).");
        }
        if (upperBoundValues.length != lowerBoundValues.length) {
            throw new ReportConfigurationException("The context value for [" + lowerBoundAttribute + "] had length ["
                + lowerBoundValues.length + "] while the context value for [" + upperBoundAttribute + "]  had length ["
                + upperBoundValues.length
                + "], so no complete bound(s) pair(s) exist for creation of range filter(s).");
        }

        final RangeFilter filter = new RangeFilter(column, filterCategory);
        for (int i = 0; i < lowerBoundValues.length; i++) {
            final String lowerBoundValue = lowerBoundValues[i];
            final String upperBoundValue = upperBoundValues[i];
            filter.addFilterRange(lowerBoundValue, upperBoundValue);
        }

        return filter;
    }

    /**
     * This method converts the given String value into a {@link ReportCategory}.
     *
     * @param categoryValue the value to be converted into the {@link ReportCategory} value corresponding to the given
     *                      name
     *
     * @return the {@link ReportCategory} looked up from the given name
     *
     * @throws ReportConfigurationException in case the given name is <tt>null</tt>, an empty (trim'd) String or does
     *                                      not correspond to any of the enum values defined by {@link ReportCategory}
     */
    private ReportCategory lookupReportCategory(final String categoryValue) throws ReportConfigurationException {
        if (categoryValue == null) {
            throw new ReportConfigurationException("The context value specifying the ReportCategory was null.");
        }
        if (categoryValue.trim().length() == 0) {
            throw new ReportConfigurationException(
                "The context value specifying the ReportCategory was an empty String.");
        }

        final ReportCategory ret = (ReportCategory) ReportCategory.getEnumByStringValue(categoryValue,
            ReportCategory.class);

        if (ret == null) {
            throw new ReportConfigurationException("The context value specifying the ReportCategory ([" + categoryValue
                + "]) was not a valid ReportCategory.");
        }
        return ret;
    }

    /**
     * This method converts the given String value into a {@link ReportType}.
     *
     * @param typeValue the value to be converted into the {@link ReportType} value corresponding to the given name
     *
     * @return the {@link ReportType} looked up from the given name
     *
     * @throws ReportConfigurationException in case the given name is <tt>null</tt>, an empty (trim'd) String or does
     *                                      not correspond to any of the enum values defined by {@link ReportType}
     */
    private ReportType lookupReportType(final String typeValue) throws ReportConfigurationException {
        if (typeValue == null) {
            throw new ReportConfigurationException("The context value specifying the ReportType was null.");
        }
        if (typeValue.trim().length() == 0) {
            throw new ReportConfigurationException(
                "The context value specifying the ReportType was an empty String.");
        }

        final ReportType ret = (ReportType) ReportType.getEnumByStringValue(typeValue, ReportType.class);

        if (ret == null) {
            throw new ReportConfigurationException("The context value specifying the ReportType ([" + typeValue
                + "]) was not a valid ReportType.");
        }
        return ret;
    }

    /**
     * This method looks up a String value from the request parameters or context using the given attrValue.
     *
     * @param attrName  contextual information about the source of the attrValue, used to construct meaningful exception
     *                  messages in case this method throws an exception.
     * @param attrValue the lookup key with which to lookup the actual value in request parameters or the context of
     *                  this {@link javax.servlet.jsp.tagext.Tag}.
     *
     * @return the value found
     *
     * @throws ReportConfigurationException in case the given attrValue was <tt>null</tt> or an empty (trim'd) String,
     *                                      the value looked up from context is not of type {@link String}, is
     *                                      <tt>null</tt> or an empty (trim'd) String
     */
    private String safeLookupValidStringContextValue(final String attrName, final String attrValue)
        throws ReportConfigurationException {
        if (attrValue == null) {
            throw new ReportConfigurationException("The required tag attribute named [" + attrName + "] was null.");
        }
        //empty string not possible as setter doesn't allow this

        Object typeValue = pageContext.getRequest().getParameter(attrValue);
        if (typeValue == null) {
            typeValue = pageContext.findAttribute(attrValue);
        }
        if (typeValue == null) {
            throw new ReportConfigurationException(
                "The required attribute named [" + attrValue
                    + "] was not found in any of page,request,session or application scope.");
        }

        if (!(typeValue instanceof String)) {
            throw new ReportConfigurationException(
                "The value of the attribute named [" + attrValue + "] was not of type String, but was of type ["
                    + typeValue.getClass().getName() + "].");
        }

        if (((String) typeValue).trim().length() == 0) {
            throw new ReportConfigurationException(
                "The value of the attribute named [" + attrValue + "] was an empty String.");
        }

        return (String) typeValue;
    }

    /**
     * This method looks up a or {@link String} <tt>String[]</tt> value from the request parameters or context using the
     * given attrValue. If a singe String is found it is returned as a <tt>String[]</tt> containing the value found.
     *
     * @param attrValue the lookup key with which to lookup the actual value in request parameters or the context of
     *                  this {@link javax.servlet.jsp.tagext.Tag}.
     *
     * @return the value found or <tt>null</tt> to indicate that the given attrValue was <tt>null</tt> or an empty
     *         String or the value looked up was <tt>null</tt>
     *
     * @throws ReportConfigurationException in case the value looked up from context is not of type {@link String} or
     *                                      <tt>String[]</tt> or the <tt>String[]</tt> found contained
     *                                      <tt>null</tt>-values or empty (trim'd) Strings
     */
    private String[] safeLookupContextValueArray(final String attrValue) throws ReportConfigurationException {
        if (attrValue != null && attrValue.trim().length() == 0) {
            return null;
        }

        Object typeValue = pageContext.getRequest().getParameterValues(attrValue);
        if (typeValue == null) {
            typeValue = pageContext.findAttribute(attrValue);
        }

        if (typeValue == null) {
            return null;
        }

        if (typeValue instanceof String) {
            if (((String) typeValue).trim().length() == 0) {
                throw new ReportConfigurationException(
                    "The value of the attribute named [" + attrValue + "] was an empty String.");
            }
            return new String[]{(String) typeValue};
        } else if (typeValue instanceof String[]) {
            final String[] asArray = (String[]) typeValue;
            if (asArray.length == 0) {
                throw new ReportConfigurationException("The value of the attribute named [" + attrValue
                    + "] was an empty String[].");

            }
            for (int i = 0; i < asArray.length; i++) {
                final String s = asArray[i];
                if (s == null) {
                    throw new ReportConfigurationException("The value of the attribute named [" + attrValue
                        + "] was a String[] that contained a null value.");
                }
                if (s.trim().length() == 0) {
                    throw new ReportConfigurationException("The value of the attribute named [" + attrValue
                        + "] was a String[] that contained an empty String.");
                }

            }
            return asArray;
        } else {
            throw new ReportConfigurationException(
                "The value of the attribute named [" + attrValue + "] was not of type String or String[], "
                    + "but was of type [" + typeValue.getClass().getName() + "].");
        }
    }

    /**
     * looks up the {@link ReportFactory} instances shred among all instances of ReportDisplayTag. In case the shared
     * instance has not yet been created, it will be created in this call and stored into the static field {@link
     * #reportFactory} for later shared re-use.
     *
     * @return the shared ReportFactory instance
     *
     * @throws ReportException in case the creation of the ReportFactory instance fails
     */
    private static ReportFactory getReportFactory() throws ReportException {
        //fast access outside the synchronized block
        if (reportFactory != null) {
            return reportFactory;
        }
        //if still null create the reportFactory
        synchronized (ReportDisplayTag.class) {
            // This is checked a second time inside the synchronized-block
            // as another thread could have created the instance while
            // we were waiting on the monitor to enter the synchronized-block
            if (reportFactory != null) {
                return reportFactory;
            }
            reportFactory = new ReportFactory();
            return reportFactory;
        }
    }
}
