/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action;

import java.util.Date;

import com.topcoder.accounting.entities.dto.BillingCostReportCriteria;
import com.topcoder.accounting.entities.dto.BillingCostReportEntry;
import com.topcoder.accounting.entities.dto.PagedResult;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This action is responsible for getting a list of billing cost report entries
 * by searching criteria given by BillingCostReportCriteria. It supports
 * pagination.
 * </p>
 * <p>
 * <strong> Thread Safety:</strong> This class is not thread-safe because it's
 * mutable. However, dedicated instance of struts action will be created by the
 * struts framework to process the user request, so the struts actions don't
 * need to be thread-safe.
 * </p>
 *
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class BillingCostReportAction extends BaseBillingCostReportAction {
    /**
     * The Log object used for logging. It's a constant. So it can only be that
     * constant value It is final and won't change once it is initialized as
     * part of variable. It is used throughout the class wherever logging is
     * needed.
     */
    private static final Log LOG = LogManager
            .getLog(BillingCostReportAction.class.toString());
    /**
     * The default duration in milliseconds between start date and end date of
     * the search criteria when start date is not given. It is set through
     * setter and doesn't have a getter. It must be non-negative. It does not
     * need to be initialized when the instance is created.
     */
    private long defaultDuration;
    /**
     * The criteria for the search of billing cost report entries. It has both
     * getter and setter. It is nullable. It does not need to be initialized
     * when the instance is created.
     */
    private BillingCostReportCriteria criteria;
    /**
     * The page number. It has both getter and setter. It must be non-negative.
     * It does not need to be initialized when the instance is created.
     */
    private int pageNumber;
    /**
     * The page size. It has both getter and setter. It must be non-negative. It
     * does not need to be initialized when the instance is created.
     */
    private int pageSize;
    /**
     * The billing cost report entries matching the search criteria. It is
     * accessed through getter and doesn't have a setter. It is nullable. After
     * the struts execution, it won't be null. It does not need to be
     * initialized when the instance is created. It is used in execute(),
     * getBillingCostReportEntries().
     */
    private PagedResult<BillingCostReportEntry> billingCostReportEntries;

    /**
     * This is the default constructor for the class.
     *
     */
    public BillingCostReportAction() {
    }

    /**
     * Execute the action logic of getting a list of billing cost report
     * entries.
     *
     * @return SUCCESS if no error occurs
     * @throws Exception if any error occurs
     *
     */
    public String execute() throws Exception {
        final String methodName = "BillingCostReportAction.execute";
        Helper.logEnterMethod(LOG, methodName);
        try {
            // Perform auditing of this action call
            audit();
            if (criteria == null) {
                criteria = new BillingCostReportCriteria();
            }
            Date now = new Date();
            if (criteria.getStartDate() == null) {
                // Set the default start date.
                criteria.setStartDate(new Date(now.getTime()
                        - (defaultDuration)));
            }
            if (criteria.getEndDate() == null) {
                // Set the end date to now.
                criteria.setEndDate(now);
            }
            // Perform the search
            billingCostReportEntries = getBillingCostDataService()
                    .getBillingCostReport(criteria, pageNumber, pageSize);
        } catch (Exception e) {
            throw Helper.logAndThrow(LOG, methodName, e);
        }
        Helper.logExitMethod(LOG, methodName, SUCCESS);
        return SUCCESS;
    }

    /**
     * Getter for the search of billing cost report entries.
     *
     * @return The criteria for the search of billing cost report entries.
     */
    public BillingCostReportCriteria getCriteria() {
        return criteria;
    }

    /**
     * Getter for the page number.
     *
     * @return the page number
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Getter for the page size.
     *
     * @return page size.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Getter for the search of billing cost report entries.
     *
     * @return The criteria for the search of billing cost report entries.
     */
    public PagedResult<BillingCostReportEntry> getBillingCostReportEntries() {
        return billingCostReportEntries;
    }

    /**
     * Setter for the search of billing cost report entries.
     *
     * @param criteria The criteria for the search of billing cost report
     *            entries.
     */
    public void setCriteria(BillingCostReportCriteria criteria) {
        this.criteria = criteria;
    }

    /**
     * Setter for the page number.
     *
     * @param pageNumber The page number.
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * Setter for the page size.
     *
     * @param pageSize The page size.
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Setter for the default duration in milliseconds.
     *
     * @param defaultDuration The default duration in milliseconds between start
     *            date and end date of the search criteria when start date is
     *            not given.
     */
    public void setDefaultDuration(long defaultDuration) {
        this.defaultDuration = defaultDuration;
    }

    /**
     * This method is called right after the dependency of this class is fully
     * injected. It checks if the injected values are valid.
     *
     * @throws BillingCostActionConfigurationException if any of the injected
     *             values is invalid.
     *
     */
    public void checkConfiguration() {
        super.checkConfiguration();
        if (defaultDuration < 0) {
            throw new BillingCostActionConfigurationException(
                    "defaultDuration should be non-negative.");
        }
    }
}
