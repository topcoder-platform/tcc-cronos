/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.stresstests;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import junit.framework.TestCase;

import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.webservices.beans.ClientManagerMock;
import com.topcoder.clients.webservices.beans.ClientStatusServiceBean;
import com.topcoder.clients.webservices.beans.SessionContextMock;
import com.topcoder.security.auth.module.UserProfilePrincipal;
import com.topcoder.security.facade.BaseUserProfile;


/**
 * Stress test for class <code>ClientStatusServiceBean</code>.
 *
 * @author PE
 * @version 1.0
 */
public class ClientStatusServiceBeanStressTest extends TestCase {
    /**
     * <p>
     * Represents the <code>ClientStatusServiceBean</code> to be tested.
     * </p>
     */
    private ClientStatusServiceBean bean;

    /**
     * Set up the environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        bean = new ClientStatusServiceBean();

        bean.setVerboseLogging(true);

        // Initialize required resources
        this.setField("clientManagerFile", "config.properties");
        this.setField("clientManagerNamespace", "ClientStatusServiceBean");

        Method method = ClientStatusServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
        method.setAccessible(true);
        method.invoke(bean, new Object[0]);

        // Initialize SessionContext
        SessionContextMock context = new SessionContextMock();
        Principal principal = new UserProfilePrincipal(new BaseUserProfile(), 1, "testUser");
        SessionContextMock.setPrincipal(principal);
        this.setField("sessionContext", context);

        // Set default manager behavior
        ClientManagerMock.setFail(false);
    }

    /**
     * Clean up the environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        bean = null;
    }

    /**
     * Stress Test for the functionality of class <code>ClientStatusServiceBean</code>.
     *
     * @throws Exception to JUnit
     */
    public void testClientStatusServiceBean_Stress() throws Exception {
        List<ClientStatus> entities = new ArrayList<ClientStatus>();
        Random random = new Random();

        StressTestHelper.start();

        for (int i = 0; i < StressTestHelper.TIMES; i++) {
            int v = Math.abs(random.nextInt()) % 3;

            if (v == 0) {
                entities.add(bean.createClientStatus(this.createClientStatus()));
            } else if (v == 1) {
                if (!entities.isEmpty()) {
                    entities.set(0, bean.updateClientStatus(entities.get(0)));
                }
            } else if (v == 2) {
                if (!entities.isEmpty()) {
                    bean.deleteClientStatus(entities.remove(0));
                }
            }
        }

        StressTestHelper.printResult("testClientStatusServiceBean_Stress");

        for (ClientStatus entity : entities) {
            bean.deleteClientStatus(entity);
        }
    }

    /**
     * Creates the entity for test.
     *
     * @return the entity for test.
     */
    private ClientStatus createClientStatus() {
        ClientStatus status = new ClientStatus();
        status.setDescription("Test entity");

        return status;
    }

    /**
     * <p>
     * Set the value into private field.
     * </p>
     *
     * @param fieldName The name of field.
     * @param fieldValue The field value to be set.
     *
     * @throws Exception to JUnit
     */
    private void setField(String fieldName, Object fieldValue)
        throws Exception {
        Field field = ClientStatusServiceBean.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(this.bean, fieldValue);
    }
}
