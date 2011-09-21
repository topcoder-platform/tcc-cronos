/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action;

import com.topcoder.accounting.entities.dao.BillingCostExport;
import com.topcoder.accounting.entities.dto.BillingCostExportHistoryCriteria;
import com.topcoder.accounting.entities.dto.PagedResult;
import com.topcoder.accounting.service.BillingCostAuditService;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This action is responsible for getting a list of billing cost export records
 * by searching criteria given by BillingCostExportHistoryCriteria. It supports
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
public class BillingCostExportHistoryAction extends BaseBillingCostAuditAction {
    /**
     * The Log object used for logging. It's a constant. So it can only be that
     * constant value It is final and won't change once it is initialized as
     * part of variable.It is used throughout the class wherever logging is
     * needed.
     */
    private static final Log LOG = LogManager
            .getLog(BillingCostExportHistoryAction.class.toString());
    /**
     * The criteria for the search of export history. It has both getter and
     * setter. It is nullable. It does not need to be initialized when the
     * instance is created.
     */
    private BillingCostExportHistoryCriteria criteria;
    /**
     * The billing cost exports matching the search criteria. It is accessed
     * through getter and doesn't have a setter. It is nullable. After the
     * struts execution, it won't be null. It does not need to be initialized
     * when the instance is created.
     */
    private PagedResult<BillingCostExport> billingCostExports;

    /**
     * This is the default constructor for the class.
     *
     */
    public BillingCostExportHistoryAction() {
    }

    /**
     * Execute the action logic of getting billing cost export history entry
     * list.
     *
     * @return SUCCESS if no error occurs
     * @throws Exception if any error occurs
     */
    public String execute() throws Exception {
        final String methodName = "BillingCostExportHistoryAction.execute";
        Helper.logEnterMethod(LOG, methodName);
        try {
            // Perform auditing of this action call
            audit();
            if (criteria == null) {
                criteria = new BillingCostExportHistoryCriteria();
            }
            // get service
            BillingCostAuditService service = getBillingCostAuditService();
            // perform search
            billingCostExports = service.getBillingCostExportHistory(criteria,
                    getPageNumber(), getPageSize());
        } catch (Exception e) {
            throw Helper.logAndThrow(LOG, methodName, e);
        }
        Helper.logExitMethod(LOG, methodName, SUCCESS);
        return SUCCESS;
    }

    /**
     * Getter for the cost export history criteria.
     *
     * @return the cost export history criteria.
     */
    public BillingCostExportHistoryCriteria getCriteria() {
        return criteria;
    }

    /**
     * Getter for the cost export result.
     *
     * @return the cost export result.
     */
    public PagedResult<BillingCostExport> getBillingCostExports() {
        return billingCostExports;
    }

    /**
     * Setter for the cost export history criteria.
     *
     * @param criteria The criteria for the search of export history.
     */
    public void setCriteria(BillingCostExportHistoryCriteria criteria) {
        this.criteria = criteria;
    }
}
