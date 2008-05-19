/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence.stresstests;

import java.util.Properties;

import com.topcoder.registration.entities.Contest;
import com.topcoder.registration.entities.ContestRole;
import com.topcoder.registration.entities.User;
import com.topcoder.registration.persistence.MockResourceManager;
import com.topcoder.registration.persistence.ResourceManagerRegistrationPersistence;
import com.topcoder.registration.persistence.client.ResourceManagerServiceClient;
import com.topcoder.registration.persistence.converter.RegistrationEntitiesToResourceConverterImpl;

import junit.framework.TestCase;
/**
 * <p>
 * The stress test of the ResourceManagerBeanInitializationException class.
 * </p>
 *
 * @author telly12
 * @version 1.0
 */
public class ResourceManagerRegistrationPersistenceStessTests extends TestCase {
    /**
     * The stress test of the method register, the overload set to 500.
     * The time cost is calculated.
     *
     * @throws Exception to JUnit
     */
    public void test_stress_register() throws Exception {
        Properties properties = new Properties();
        properties.load(ResourceManagerRegistrationPersistenceStessTests.class.getResourceAsStream("/url.properties"));
        ResourceManagerServiceClient client = new ResourceManagerServiceClient(properties.getProperty("WSDL"));
        ResourceManagerRegistrationPersistence persistence = new ResourceManagerRegistrationPersistence(client,
                new RegistrationEntitiesToResourceConverterImpl());
        Contest contest = StressTestHelper.createContest(1L);
        User user = StressTestHelper.createUser(MockResourceManager.CONVERTED_RESOURCE_ID);
        ContestRole contestRole = StressTestHelper.createContestRole(1L);


        long start = System.currentTimeMillis();
        for (int i = 0; i < 500; i ++) {
            persistence.register(contest, user, contestRole);
        }
        long end = System.currentTimeMillis();
        System.out.println("process register in persistence for 500 times takes:" + (end - start) + " ms.");
    }
    /**
     * The stress test of the method unregister, the overload set to 500.
     * The time cost is calculated.
     *
     * @throws Exception to JUnit
     */
    public void test_stress_unregister() throws Exception {
        Properties properties = new Properties();
        properties.load(ResourceManagerRegistrationPersistenceStessTests.class.getResourceAsStream("/url.properties"));
        ResourceManagerServiceClient client = new ResourceManagerServiceClient(properties.getProperty("WSDL"));
        ResourceManagerRegistrationPersistence persistence = new ResourceManagerRegistrationPersistence(client,
                new RegistrationEntitiesToResourceConverterImpl());
        Contest contest = StressTestHelper.createContest(1L);
        User user = StressTestHelper.createUser(MockResourceManager.CONVERTED_RESOURCE_ID);
        ContestRole contestRole = StressTestHelper.createContestRole(1L);


        long start = System.currentTimeMillis();
        for (int i = 0; i < 500; i ++) {
            persistence.unregister(contest, user, contestRole);
        }
        long end = System.currentTimeMillis();
        System.out.println("process unregister in persistence for 500 times takes:" + (end - start) + " ms.");
    }
}
