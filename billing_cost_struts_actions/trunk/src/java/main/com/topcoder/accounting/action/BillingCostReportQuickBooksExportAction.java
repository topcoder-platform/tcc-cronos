/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.topcoder.accounting.entities.dto.PaymentIdentifier;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.TCSubject;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This action is responsible for exporting a list of billing cost report
 * entries to Quick Books. The list of billing cost report entries to export are
 * identified by a list of PaymentIdentifier object.
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
public class BillingCostReportQuickBooksExportAction extends
        BaseBillingCostReportAction {
    /**
     * The Log object used for logging. It's a constant. So it can only be that
     * constant value It is final and won't change once it is initialized.
     */
    private static final Log LOG = LogManager
            .getLog(BillingCostReportQuickBooksExportAction.class.toString());
    /**
     * The payment ids that identify the list of billing cost report entries to
     * export. It has both getter and setter. It can be null or empty. The
     * contained values can't be null.
     */
    private List<PaymentIdentifier> paymentIds;
    /**
     * The payment area id used to identify the billing cost report entries to
     * export. It has both getter and setter. It can be any numeric value. It
     * does not need to be initialized when the instance is created.
     */
    private long paymentAreaId;

    /**
     * This is the default constructor for the class.
     *
     */
    public BillingCostReportQuickBooksExportAction() {
    }

    /**
     * Execute the action logic of exporting billing cost report entries to
     * Quick Books.
     *
     * @return SUCCESS if no error occurs
     * @throws Exception if any error occurs
     */
    public String execute() throws Exception {
        final String methodName = "BillingCostReportQuickBooksExportAction.execute";
        Helper.logEnterMethod(LOG, methodName);
        try {
            // Perform auditing of this action call
            audit();
            if (paymentIds == null) {
                Helper.logExitMethod(LOG, methodName, SUCCESS);
                // Nothing to export, return immediately
                return SUCCESS;
            }
            // Get the session
            HttpSession session = getServletRequest().getSession();
            SessionData sessionData = new SessionData(session);
            // Get the Topcoder subject from sessionData
            TCSubject tcSubject = sessionData.getCurrentUser();
            // Export the billing cost data
            getBillingCostDataService().exportBillingCostData(paymentIds,
                    paymentAreaId, tcSubject);
        } catch (Exception e) {
            throw Helper.logAndThrow(LOG, methodName, e);
        }
        Helper.logExitMethod(LOG, methodName, SUCCESS);
        return SUCCESS;
    }

    /**
     * Validate the payment ids programmatically.
     *
     */
    public void validate() {
        final String methodName = "BillingCostReportQuickBooksExportAction.validate";
        Helper.logEnterMethod(LOG, methodName);
        if (paymentIds == null) {
            Helper.logExitMethod(LOG, methodName, null);
            // Nothing to export, return directly
            return;
        }
        // validate the payment identifier
        for (PaymentIdentifier id : paymentIds) {
            if (id.getPaymentDetailId() != null) {
                continue;
            }
            if (id.getContestId() != null && id.getProjectInfoTypeId() != null) {
                // (this is needed because these 2 together identify a billing
                // cost report entry to export)
                continue;
            }
            // This payment identifier is invalid if the program reaches here,
            // so add validation message
            addFieldError("paymentIds", "Invalid PaymentIdentifier: "
                    + id.toJSONString());
        }
        Helper.logExitMethod(LOG, methodName, null);
    }

    /**
     * Getter for the payment area id.
     *
     * @return the payment area id.
     */
    public long getPaymentAreaId() {
        return paymentAreaId;
    }

    /**
     * Getter for the payment area id.
     *
     * @return the payment id
     */
    public List<PaymentIdentifier> getPaymentIds() {
        return paymentIds;
    }

    /**
     * Setter for the payment area id used to identify the billing cost report entries to export.
     *
     * @param paymentAreaId The payment area id used to identify the billing
     *            cost report entries to export.
     */
    public void setPaymentAreaId(long paymentAreaId) {
        this.paymentAreaId = paymentAreaId;
    }

    /**
     * Setter for the payment ids that identify the list of billing cost report entries to export.
     *
     * @param paymentIds The payment ids that identify the list of billing cost
     *            report entries to export.
     */
    public void setPaymentIds(List<PaymentIdentifier> paymentIds) {
        this.paymentIds = paymentIds;
    }
}
