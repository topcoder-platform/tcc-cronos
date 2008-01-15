/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.controlpanel.clientassociations.stresstests;

import javax.naming.InitialContext;

import com.topcoder.controlpanel.clientassociations.ClientAssociationServiceRemote;
import com.topcoder.controlpanel.clientassociations.dao.hibernate.Client;

import junit.framework.TestCase;

/**
 * <p>
 * Stress test for class ClientAssociationServiceStatelessSessionBean.
 * </p>
 * <p>
 * <strong>Thread Safety:</strong>
 * </p>
 * 
 * @author smallka
 * @version 1.0
 */
public class ClientAssociationServiceStatelessSessionBeanStressTest extends TestCase {

    /**
     * <p>
     * The times to run each method.
     * </p>
     */
    private static final long TIMES = 100;

    /**
     * <p>
     * A Remote instance that related to the stateless session bean for testing.
     * </p>
     */
    private ClientAssociationServiceRemote cas;

    /**
     * <p>
     * Sets up the testing environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        StressTestHelper.clearDB();

        StressTestHelper.saveEntity(new Client(1, "ivern"));
        StressTestHelper.saveEntity(new Client(2, "mess"));

        // create a new context
        InitialContext ctx = new InitialContext();

        // look up the session bean
        cas =
            (ClientAssociationServiceRemote) ctx
                .lookup("ClientAssociationServiceStatelessSessionBean");
        Thread.sleep(200);

    }

    /**
     * <p>
     * Tears down the testing environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        StressTestHelper.clearDB();

        super.tearDown();
    }

    /**
     * 
     * <p>
     * Tests method assignComponentStress.
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    public void testAssignComponentStress() throws Exception {
        // warm up
        cas.assignComponent(0, 1);

        long start = System.currentTimeMillis();
        for (int i = 1; i <= TIMES; i++) {
            cas.assignComponent(i, 1);
        }
        long stop = System.currentTimeMillis();
        // display benchmark
        System.out.println("method assignComponent: " + 1.0 * (stop - start) / TIMES + " ms");
    }

    /**
     * 
     * <p>
     * Tests method assignUser.
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    public void testAssignUserStress() throws Exception {
        // warm up
        cas.assignUser(0, 1, true);

        long start = System.currentTimeMillis();
        for (int i = 1; i <= TIMES; i++) {
            cas.assignUser(i, 1, true);
        }
        long stop = System.currentTimeMillis();
        // display benchmark
        System.out.println("method assignUser: " + 1.0 * (stop - start) / TIMES + " ms");
    }

    /**
     * 
     * <p>
     * Tests method removeComponent.
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveComponentStress() throws Exception {
        // prepare the data
        for (int i = 1; i <= TIMES; i++) {
            cas.assignComponent(i, 1);
        }
        Thread.sleep(200);

        long start = System.currentTimeMillis();
        for (int i = 1; i <= TIMES; i++) {
            cas.removeComponent(i, 1);
        }
        long stop = System.currentTimeMillis();
        // display benchmark
        System.out.println("method removeComponent: " + 1.0 * (stop - start) / TIMES + "ms");
    }

    /**
     * 
     * <p>
     * Tests method removeUser.
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveUserStress() throws Exception {
        // prepare the data
        for (int i = 1; i <= TIMES; i++) {
            cas.assignUser(i, 1, true);
        }
        Thread.sleep(200);

        long start = System.currentTimeMillis();
        for (int i = 1; i <= TIMES; i++) {
            cas.removeUser(i, 1);
        }
        long stop = System.currentTimeMillis();
        // display benchmark
        System.out.println("method removeUser: " + 1.0 * (stop - start) / TIMES + "ms");
    }

    /**
     * 
     * <p>
     * Tests method getUsers.
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    public void testGetUsersStress() throws Exception {
        // prepare the data
        for (int i = 1; i <= TIMES / 2; i++) {
            cas.assignUser(i, 1, true);
        }
        Thread.sleep(200);
        for (int i = 1; i <= TIMES / 2; i++) {
            cas.assignUser(i, 2, true);
        }
        Thread.sleep(200);

        long start = System.currentTimeMillis();
        for (int i = 1; i <= TIMES / 2; i++) {
            cas.getUsers(1);
            cas.getUsers(2);
        }
        long stop = System.currentTimeMillis();
        // display benchmark
        System.out.println("method getUsers: " + 1.0 * (stop - start) / TIMES + "ms");
    }

    /**
     * 
     * <p>
     * Tests method isAdminUser.
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    public void testIsAdminUserStress() throws Exception {
        // prepare the data
        for (int i = 1; i <= TIMES; i++) {
            cas.assignUser(i, 1, (i % 2 == 0));
        }
        Thread.sleep(200);

        long start = System.currentTimeMillis();
        for (int i = 1; i <= TIMES; i++) {
            cas.isAdminUser(i, 1);
        }
        long stop = System.currentTimeMillis();
        // display benchmark
        System.out.println("method isAdminUser: " + 1.0 * (stop - start) / TIMES + "ms");
    }

    /**
     * 
     * <p>
     * Tests method getClientsByComponent.
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    public void testGetClientsByComponentStress() throws Exception {
        // prepare the data
        for (int i = 1; i <= TIMES; i++) {
            cas.assignComponent(i, 1);
        }
        Thread.sleep(200);
        for (int i = 1; i <= TIMES; i++) {
            cas.assignComponent(i, 2);
        }
        Thread.sleep(200);

        long start = System.currentTimeMillis();
        for (int i = 1; i <= TIMES; i++) {
            cas.getClientsByComponent(i);
        }
        long stop = System.currentTimeMillis();
        // display benchmark
        System.out.println("method getClientsByComponent: " + 1.0 * (stop - start) / TIMES + "ms");
    }

    /**
     * 
     * <p>
     * Tests method getClientsByUser.
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    public void testGetClientsByUserStress() throws Exception {
        // prepare the data
        for (int i = 1; i <= TIMES; i++) {
            cas.assignUser(i, 1, true);
        }
        Thread.sleep(200);
        for (int i = 1; i <= TIMES; i++) {
            cas.assignUser(i, 2, false);
        }
        Thread.sleep(200);

        long start = System.currentTimeMillis();
        for (int i = 1; i <= TIMES; i++) {
            cas.getClientsByUser(i);
        }
        long stop = System.currentTimeMillis();
        // display benchmark
        System.out.println("method getClientsByUser: " + 1.0 * (stop - start) / TIMES + "ms");
    }

    /**
     * 
     * <p>
     * Tests method getComponentsByClient.
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    public void testGetComponentsByClientStress() throws Exception {
        // prepare the data
        for (int i = 1; i <= 5; i++) {
            cas.assignComponent(i, 1);
        }
        Thread.sleep(200);

        long start = System.currentTimeMillis();
        for (int i = 1; i <= TIMES / 2; i++) {
            cas.getComponentsByClient(1);
            cas.getComponentsByClient(2);
        }
        long stop = System.currentTimeMillis();
        // display benchmark
        System.out.println("method getComponentsByClient: " + 1.0 * (stop - start) / TIMES + "ms");
    }

    /**
     * 
     * <p>
     * Tests method getComponentsByUser.
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    public void testGetComponentsByUserStress() throws Exception {
        // prepare the data
        for (int i = 1; i <= TIMES; i++) {
            cas.assignComponent(i, 1);
        }
        Thread.sleep(200);
        for (int i = 1; i <= TIMES; i++) {
            cas.assignUser(i, 1, true);
        }
        Thread.sleep(200);

        long start = System.currentTimeMillis();
        for (int i = 1; i <= TIMES; i++) {
            cas.getComponentsByUser(i);
        }
        long stop = System.currentTimeMillis();
        // display benchmark
        System.out.println("method getComponentsByUser: " + 1.0 * (stop - start) / TIMES + "ms");
    }
}
