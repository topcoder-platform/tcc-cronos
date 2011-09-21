/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action;

import com.topcoder.accounting.entities.dao.BillingCostExportDetail;
import com.topcoder.accounting.entities.dto.PagedResult;
import com.topcoder.accounting.service.BillingCostAuditService;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This action is responsible for getting the billing cost export details of a
 * particular billing cost export. It supports pagination.
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
public class BillingCostExportDetailsAction extends BaseBillingCostAuditAction {
    /**
     * The Log object used for logging. It's a constant. So it can only be that
     * constant value It is final and won't change once it is initialized as
     * part of variable. It is used throughout the class wherever logging is
     * needed.
     */
    private static final Log LOG = LogManager
            .getLog(BillingCostExportDetailsAction.class.toString());
    /**
     * The id of the billing cost export for which the details are retrieved. It
     * has both getter and setter. It can be any numeric value. It does not need
     * to be initialized when the instance is created.
     */
    private long billingCostExportId;
    /**
     * The billing cost export details of the given billing cost export. It is
     * accessed through getter and doesn't have a setter. It is nullable. After
     * the struts execution, it won't be null. It does not need to be
     * initialized when the instance is created.
     */
    private PagedResult<BillingCostExportDetail> billingCostExportDetails;

    /**
     * This is the default constructor for the class.
     */
    public BillingCostExportDetailsAction() {
    }

    /**
     * Execute the action logic of getting billing cost export details of a
     * particular billing cost export.
     *
     * @return SUCCESS if no error occurs
     * @throws Exception if any error occurs
     *
     */
    public String execute() throws Exception {
        final String methodName = "BillingCostExportDetailsAction.execute";
        Helper.logEnterMethod(LOG, methodName);
        try {
            audit();
            BillingCostAuditService service = getBillingCostAuditService();
            billingCostExportDetails = service.getBillingCostExportDetails(
                    billingCostExportId, getPageNumber(), getPageSize());
        } catch (Exception e) {
            throw Helper.logAndThrow(LOG, methodName, e);
        }
        Helper.logExitMethod(LOG, methodName, SUCCESS);
        return SUCCESS;
    }

    /**
     * Getter for the billing cost export details.
     *
     * @return The billing cost export details.
     */
    public PagedResult<BillingCostExportDetail> getBillingCostExportDetails() {
        return billingCostExportDetails;
    }

    /**
     * Getter for the billing cost export id.
     *
     * @return The id of the billing cost export for which the details are
     *         retrieved.
     */
    public long getBillingCostExportId() {
        return billingCostExportId;
    }

    /**
     * Setter for the billing cost export id.
     *
     * @param billingCostExportId The id of the billing cost export for which
     *            the details are retrieved.
     */
    public void setBillingCostExportId(long billingCostExportId) {
        this.billingCostExportId = billingCostExportId;
    }
}
