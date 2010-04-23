/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.failuretests;

import org.springframework.test.context.ContextConfiguration;

import com.topcoder.service.actions.PayByBillingAccountAction;

/**
 * Mock <code>PayByBillingAction</code>.
 * @author moon.river
 * @version 1.0
 */
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class FailurePayByBillingAccountAction extends PayByBillingAccountAction {

    /**
     * Po number.
     */
    public static String poNumber;

    /**
     * Client id.
     */
    public static long clientId;

    /**
     * Project id.
     */
    public static long projectId;


    /**
     * @return the poNumber
     */
    public String getPoNumber() {
        return poNumber;
    }


    /**
     * @return the clientId
     */
    public long getClientId() {
        return clientId;
    }


    /**
     * @return the projectId
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Prepares the action.
     */
    public void prepare() {
        super.prepare();
        this.setProjectId(projectId);
        this.setClientId(clientId);
        this.setPoNumber(poNumber);
        
    }
}
