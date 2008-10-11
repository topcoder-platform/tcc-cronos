/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.webserviceclients;

import com.topcoder.clients.webservices.LookupService;

/**
 * <p>
 * Mock class for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class LookupServiceClient {
    /**
     * <p>
     * Simply return the LookupService web service endpoint interface.
     * </p>
     *
     * @return the LookupService web service
     * @throws WebServiceException
     *             if appears any problem during retrieving the endpoint interface
     */
    public LookupService getLookupServicePort() {
        return new LookupService();
    }

}
