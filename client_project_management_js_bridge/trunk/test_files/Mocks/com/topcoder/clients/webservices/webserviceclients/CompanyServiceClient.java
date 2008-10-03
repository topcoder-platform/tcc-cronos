package com.topcoder.clients.webservices.webserviceclients;

import com.topcoder.clients.webservices.CompanyService;

public class CompanyServiceClient {

    public CompanyService getCompanyServicePort() {
        return new CompanyService();
    }

}
