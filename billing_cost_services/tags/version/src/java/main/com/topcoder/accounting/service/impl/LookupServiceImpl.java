/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.service.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.topcoder.accounting.entities.dao.PaymentArea;
import com.topcoder.accounting.service.BillingCostServiceException;
import com.topcoder.accounting.service.LookupService;
import com.topcoder.commons.utils.LoggingWrapperUtility;

/**
 * <p>
 * This class is an implementation of LookupService that uses Hibernate to get the PaymentArea data. Logs with the
 * Log from the Logging Wrapper.
 * </p>
 * <p>
 * Thread Safety: This class is mutable but effectively thread-safe.
 * </p>
 * <b>Sample Config:</b>
 * <p>
 *
 * <pre>
 *     &lt;bean id="lookupService" class="com.topcoder.accounting.service.impl.LookupServiceImpl"&lt;
 *         &lt;property name="hibernateTemplate" ref="hibernateTemplate" /&lt;
 *         &lt;property name="logger" ref="logger" /&lt;
 *     &lt;/bean&lt;
 * </pre>
 *
 * </p>
 * <b>Usage:</b>
 * <p>
 *
 * <pre>
 * // Get all payment areas
 * List&lt;PaymentArea&gt; paymentAreas = lookupService.getPaymentAreas();
 * // The list would contain all payment areas, which in our case, would be 3.
 * </pre>
 *
 * </p>
 *
 * @author argolite, stevenfrog
 * @version 1.0
 */
public class LookupServiceImpl extends BaseService implements LookupService {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = LookupServiceImpl.class.getName();

    /**
     * Empty constructor.
     */
    public LookupServiceImpl() {
        // Empty
    }

    /**
     * This method gets all available payment areas. If none found, and empty list s returned.
     *
     * @return all available payment areas
     * @throws BillingCostServiceException
     *             If there are any errors during the execution of this method
     */
    @SuppressWarnings({"cast", "unchecked"})
    public List<PaymentArea> getPaymentAreas() throws BillingCostServiceException {
        String signature = CLASS_NAME + ".getPaymentAreas()";
        // log entrance
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);

        try {
            // Get all payment areas
            List<PaymentArea> result = (List<PaymentArea>) getHibernateTemplate().loadAll(PaymentArea.class);

            // log exit
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {Helper.getListString(result)});

            return result;
        } catch (DataAccessException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new BillingCostServiceException(
                "DataAccessException occurs while accessing to db", e));
        }
    }
}
