/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.failuretests;

import java.io.FileInputStream;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * The helper class used by failure test case suite.
 * 
 * @author Xuchen, mumujava
 * @version 2.0
 */
public final class FailureTestHelper {
    /**
     * The EntityManager instance used for testing.
     */
    private static EntityManager entityManager = null;
    
    /**
     * Get the entity manager used for testing.
     * 
     * @return valid entity manager
     * @throws Exception to JUnit
     */
    public static EntityManager getEntityManager() throws Exception {
        if (entityManager == null) {
            Properties properties = new Properties();
            properties.load(new FileInputStream("test_files/failuretest/validPersistenceUnitName.properties"));
            String persistenceUnitName = properties.getProperty("persistenceUnitName");        
            entityManager = Persistence.createEntityManagerFactory(persistenceUnitName).createEntityManager();            
        }
        
        return entityManager;
    } 
}
