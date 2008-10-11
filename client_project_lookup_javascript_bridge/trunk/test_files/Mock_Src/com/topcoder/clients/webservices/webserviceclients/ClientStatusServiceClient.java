/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.webserviceclients;

import com.topcoder.clients.webservices.ClientStatusService;

/**
 * <p>
 * Mock class for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ClientStatusServiceClient {
    /**
     * <p>
     * Simply return the ClientStatusService web service endpoint interface.
     * </p>
     *
     * @return the ClientStatusService web service
     * @throws WebServiceException
     *             if appears any problem during retrieving the endpoint interface
     */
    public ClientStatusService getClientStatusServicePort() {
        return new ClientStatusService();
    }

}
