/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import javax.annotation.PostConstruct;

import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.web.tc.Constants;

/**
 * <p>
 * This is the base class for all actions that can get a range of full or pending data sorted on certain column. It
 * simply defines the common properties that subclasses require, and provides a method to set default values for
 * startRank and endRank if they are not present.
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> This class is not thread-safe because it's mutable. However, dedicated instance of
 * struts action will be created by the struts framework to process the user request, so the struts actions don’t need
 * to be thread-safe.
 * </p>
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.0
 */
public abstract class BaseRangeAction extends BaseAction {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -2973985005560948663L;
    /**
     * <p>
     * The default sort column index. It is set through setter and doesn't have a getter.It must be positive. (Note that
     * the above statement applies only after the setter has been called as part of the IoC initialization. This field's
     * value has no restriction before the IoC initialization). It does not need to be initialized when the instance is
     * created.It is used in setDefaultSortColumn(), setDefaultSortColumnValue().
     * </p>
     */
    private int defaultSortColumn;
    /**
     * <p>
     * This is a flag that if true, means the full list of data should be returned. It has both getter and setter. It
     * does not need to be initialized when the instance is created.It is used in isFullList(), setFullList().
     * </p>
     */
    private boolean fullList;
    /**
     * <p>
     * The starting index (from 1) of the data to return. Only the data within the range of [startRank, endRank] will be
     * returned. It has both getter and setter.It is nullable. It must be positive. (Note that the above statement
     * applies only after the field has passed Struts validation. This field's value has no restriction before then).It
     * does not need to be initialized when the instance is created.It is used in setDefaultStartAndEndRankValues(),
     * getStartRank(), validate(), setStartRank().
     * </p>
     */
    private Integer startRank;
    /**
     * <p>
     * The ending index (from 1) of the data to return. Only the data within the range of [startRank, endRank] will be
     * returned. It has both getter and setter.It is nullable. It must be positive. (Note that the above statement
     * applies only after the field has passed Struts validation. This field's value has no restriction before then).It
     * does not need to be initialized when the instance is created.It is used in setDefaultStartAndEndRankValues(),
     * getEndRank(), validate(), setEndRank().
     * </p>
     */
    private Integer endRank;
    /**
     * <p>
     * The sort column index, starting from 1. It has both getter and setter.It is nullable. It must be positive. (Note
     * that the above statement applies only after the field has passed Struts validation. This field's value has no
     * restriction before then).It does not need to be initialized when the instance is created.It is used in
     * getSortColumn(), setSortColumn(), setDefaultSortColumnValue().
     * </p>
     */
    private Integer sortColumn;
    /**
     * <p>
     * This means the sorting will be in ascending order if true. It has both getter and setter. It can be changed after
     * it is initialized as part of variable declaration to: true. It is used in setAscending(), isAscending().
     * </p>
     */
    private boolean ascending = true;

    /**
     * <p>
     * This is the default constructor for the class.
     * </p>
     */
    protected BaseRangeAction() {
        // do nothing
    }

    /**
     * <p>
     * Set the default values for startRank and endRank if they are not present.
     * </p>
     * @param pageSize
     *            the size of the page to return.
     * @throws IllegalArgumentException
     *             if pageSize is not positive
     */
    protected void setDefaultStartAndEndRankValues(int pageSize) {
        ParameterCheckUtility.checkPositive(pageSize, "pageSize");
        if (startRank == null) {
            startRank = 1;
        }

        if (endRank == null) {
            endRank = startRank + pageSize - 1;
        } else if (endRank - startRank > Constants.MAX_HISTORY) {
            endRank = startRank + Constants.MAX_HISTORY;
        }
    }

    /**
     * <p>
     * Set the default sort column value if it's not present.
     * </p>
     */
    protected void setDefaultSortColumnValue() {
        if (sortColumn == null) {
            sortColumn = defaultSortColumn;
        }
    }

    /**
     * <p>
     * Validate the input parameters.
     * </p>
     */
    public void validate() {
        if (startRank > endRank) {
            this.addActionError(this.getText("startRankLargerThanEndRank"));
        }
    }

    /**
     * <p>
     * Getter for the namesake instance variable.
     * </p>
     * @return the fullList flag
     */
    protected boolean isFullList() {
        return fullList;
    }

    /**
     * <p>
     * Getter for the namesake instance variable.
     * </p>
     * @return the value of sortColumn
     */
    public Integer getSortColumn() {
        return sortColumn;
    }

    /**
     * <p>
     * Getter for the namesake instance variable.
     * </p>
     * @return the ascnding flag
     */
    public boolean isAscending() {
        return ascending;
    }

    /**
     * <p>
     * Getter for the namesake instance variable.
     * </p>
     * @return the start rank
     */
    public Integer getStartRank() {
        return startRank;
    }

    /**
     * <p>
     * Getter for the namesake instance variable.
     * </p>
     * @return end rank
     */
    public Integer getEndRank() {
        return endRank;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param fullList
     *            This is a flag that if true, means the full list of data should be returned.
     */
    public void setFullList(boolean fullList) {
        this.fullList = fullList;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param startRank
     *            The starting index (from 1) of the data to return. Only the data within the range of [startRank,
     *            endRank] will be returned.
     */
    public void setStartRank(Integer startRank) {
        this.startRank = startRank;
    }

    /**
     * <p>
     * Setter for the namesake instance variable. Simply assign the value to the instance variable.
     * </p>
     * @param endRank
     *            The ending index (from 1) of the data to return. Only the data within the range of [startRank,
     *            endRank] will be returned.
     */
    public void setEndRank(Integer endRank) {
        this.endRank = endRank;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param ascending
     *            This means the sorting will be in ascending order if true.
     */
    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param defaultSortColumn
     *            The default sort column index.
     */
    public void setDefaultSortColumn(int defaultSortColumn) {
        this.defaultSortColumn = defaultSortColumn;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param sortColumn
     *            The sort column index, starting from 1.
     */
    public void setSortColumn(Integer sortColumn) {
        this.sortColumn = sortColumn;
    }

    /**
     * <p>
     * This method is called right after the dependency of this class is fully injected. It checks if the injected
     * values are valid.
     * </p>
     * @throws UserDocumentationManagementActionsConfigurationException
     *             if any of the injected values is invalid. Notes: Call super.checkConfiguration() Check the value of
     *             the following fields according to their legal value specification in the field variable
     *             documentation: defaultSortColumn
     */
    @PostConstruct
    public void checkConfiguration() {
        super.checkConfiguration();
        ValidationUtility.checkPositive(defaultSortColumn, "defaultSortColumn",
                UserDocumentationManagementActionsConfigurationException.class);
    }
}
