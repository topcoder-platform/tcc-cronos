package com.topcoder.clients.webservices.webserviceclients;

import com.topcoder.clients.webservices.ClientService;

public class ClientServiceClient {

    public ClientService getClientServicePort() {
        return new ClientService();
    }

}
