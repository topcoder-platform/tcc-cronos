/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * Unit test cases for class <code>ClientManagerException</code>.
 *
 * @author Chenhong
 * @version 1.0
 *
 */
public class TestClientManagerException extends TestCase {

    /**
     * Test method for 'ClientManagerException(Client, ClientStatus)'.
     */
    public void testClientManagerExceptionClientClientStatus() {
        ClientManagerException e = new ClientManagerException(null, null);

        assertNull("The client is null.", e.getClient());
        assertNull("The clientStatus is null.", e.getClientStatus());
    }

    /**
     * Test method for 'ClientManagerException(String, Client, ClientStatus)'.
     */
    public void testClientManagerExceptionStringClientClientStatus() {
        Client client = new Client();
        ClientStatus status = new ClientStatus();

        ClientManagerException e = new ClientManagerException("error", client, status);

        assertEquals("Equal to 'error'", "error", e.getMessage());

        assertEquals("Equal to client.", client, e.getClient());
        assertEquals("Equal to clientstatus.", status, e.getClientStatus());

    }

    /**
     * Test method for 'ClientManagerException(String, Throwable, Client, ClientStatus)'.
     */
    public void testClientManagerExceptionStringThrowableClientClientStatus() {
        Client client = new Client();
        ClientStatus status = new ClientStatus();

        Exception cause = new NullPointerException("NPE");
        ClientManagerException e = new ClientManagerException("error", cause, client, status);

        assertEquals("Equal to 'error'", "error", e.getMessage());

        assertEquals("Equal to client.", client, e.getClient());
        assertEquals("Equal to clientstatus.", status, e.getClientStatus());

        assertEquals("The cause should be npe.", cause, e.getCause());

    }

    /**
     * Test method for 'ClientManagerException(String, Throwable, ExceptionData, Client, ClientStatus)'.
     */
    public void testClientManagerExceptionStringThrowableExceptionDataClientClientStatus() {
        Client client = new Client();
        ClientStatus status = new ClientStatus();

        ExceptionData data = new ExceptionData();
        data.setApplicationCode("code");

        Exception cause = new NullPointerException("NPE");
        ClientManagerException e = new ClientManagerException("error", cause, data, client, status);

        assertEquals("Equal to 'error'", "error", e.getMessage());

        assertEquals("Equal to client.", client, e.getClient());
        assertEquals("Equal to clientstatus.", status, e.getClientStatus());

        assertEquals("The cause should be npe.", cause, e.getCause());

        assertEquals("Equal to 'code'", "code", e.getApplicationCode());
    }

    /**
     * Test method for 'getClient()'.
     */
    public void testGetClient() {
        Client client = new Client();
        ClientStatus status = new ClientStatus();

        ExceptionData data = new ExceptionData();
        data.setApplicationCode("code");

        Exception cause = new NullPointerException("NPE");
        ClientManagerException e = new ClientManagerException("error", cause, data, client, status);

        assertEquals("Equal to client.", client, e.getClient());
    }

    /**
     * Test method for 'sgetClientStatus()'.
     */
    public void testGetClientStatus() {
        Client client = new Client();
        ClientStatus status = new ClientStatus();

        ExceptionData data = new ExceptionData();
        data.setApplicationCode("code");

        Exception cause = new NullPointerException("NPE");
        ClientManagerException e = new ClientManagerException("error", cause, data, client, status);

        assertEquals("Equal to clientstatus.", status, e.getClientStatus());

    }

}
