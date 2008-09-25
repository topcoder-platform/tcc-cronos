package com.topcoder.clients.webservices.accuracytests.mock;

import java.security.Principal;

public class MockPrincipal implements Principal {

    public String getName() {
        return "admin";
    }

}
