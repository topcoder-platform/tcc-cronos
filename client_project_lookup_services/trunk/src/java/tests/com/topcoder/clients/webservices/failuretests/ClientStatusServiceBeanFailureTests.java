/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.failuretests;

import junit.framework.TestCase;

import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.webservices.beans.ClientStatusServiceBean;

/**
 * Failure tests for {@link ClientStatusServiceBean}.
 *
 * @author iRabbit
 * @version 1.0
 */
public class ClientStatusServiceBeanFailureTests extends TestCase {

    /**
     * {@link ClientStatusServiceBean} used in testing.
     */
    private ClientStatusServiceBean instance = new ClientStatusServiceBean();

    /**
     * Failure test for {@link ClientStatusServiceBean#createClientStatus(ClientStatus)}.
     *
     * @throws Exception to junit
     */
    public void testCreateClientStatus_Failure1() throws Exception {
        try {
            instance.createClientStatus(null);
            fail("IAE is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for {@link ClientStatusServiceBean#createClientStatus(ClientStatus)}.
     *
     * @throws Exception to junit
     */
    public void testCreateClientStatus_Failure2() throws Exception {
        try {
            instance.createClientStatus(new ClientStatus());
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link ClientStatusServiceBean#updateClientStatus(ClientStatus)}.
     *
     * @throws Exception to junit
     */
    public void testUpdateClientStatus_Failure1() throws Exception {
        try {
            instance.updateClientStatus(null);
            fail("IAE is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for {@link ClientStatusServiceBean#updateClientStatus(ClientStatus)}.
     *
     * @throws Exception to junit
     */
    public void testUpdateClientStatus_Failure2() throws Exception {
        try {
            instance.updateClientStatus(new ClientStatus());
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link ClientStatusServiceBean#deleteClientStatus(ClientStatus)}.
     *
     * @throws Exception to junit
     */
    public void testDeleteClientStatus_Failure1() throws Exception {
        try {
            instance.deleteClientStatus(null);
            fail("IAE is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for {@link ClientStatusServiceBean#deleteClientStatus(ClientStatus)}.
     *
     * @throws Exception to junit
     */
    public void testDeleteClientStatus_Failure2() throws Exception {
        try {
            instance.deleteClientStatus(new ClientStatus());
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }
}
