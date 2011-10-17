/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.service;

import java.util.List;

import com.topcoder.accounting.entities.dao.PaymentArea;

/**
 * <p>
 * This interface defines the service contract for the retrieval of all available payment areas.
 * </p>
 * <p>
 * Thread Safety: Implementations are expected to be effectively thread-safe.
 * </p>
 *
 * @author argolite, stevenfrog
 * @version 1.0
 */
public interface LookupService {
    /**
     * This method gets all available payment areas. If none found, and empty list returned.
     *
     * @return all available payment areas
     * @throws BillingCostServiceException
     *             If there are any errors during the execution of this method
     */
    public List<PaymentArea> getPaymentAreas() throws BillingCostServiceException;
}
