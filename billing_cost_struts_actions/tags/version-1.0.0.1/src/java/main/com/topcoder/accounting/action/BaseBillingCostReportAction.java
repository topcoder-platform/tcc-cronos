/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action;

import com.topcoder.accounting.service.BillingCostDataService;

/**
 * <p>
 * This is the base class for actions about billing cost report, including
 * getting the report and exporting the report. It simply has a
 * BillingCostDataService that subclasses can use. And because the
 * BillingCostDataService it uses is thread-safe, so the action is effectively
 * thread-safe.
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
public abstract class BaseBillingCostReportAction extends BaseAction {
    /**
     * The BillingCostDataService used by the subclasses to get and export
     * billing cost report entries. It has both getter and setter. It cannot be
     * null. It does not need to be initialized when the instance is created.
     */
    private BillingCostDataService billingCostDataService;

    /**
     * This is the default constructor for the class.
     *
     */
    protected BaseBillingCostReportAction() {
    }

    /**
     * Getter for the billing cost data service.
     *
     * @return the billing cost data service.
     */
    protected BillingCostDataService getBillingCostDataService() {
        return billingCostDataService;
    }

    /**
     * Setter for the billing cost data service.
     *
     * @param billingCostDataService The BillingCostDataService used by the
     *            subclasses to get and export billing cost report entries
     */
    public void setBillingCostDataService(
            BillingCostDataService billingCostDataService) {
        this.billingCostDataService = billingCostDataService;
    }

    /**
     * This method is called right after the dependency of this class is fully
     * injected. It checks if the injected values are valid.
     *
     * @throws BillingCostActionConfigurationException If any of the injected
     *             values is invalid.
     *
     */
    public void checkConfiguration() {
        super.checkConfiguration();
        // Check the billing cost data service.
        Helper.checkConfig(billingCostDataService, "billingCostDataService");
    }
}
