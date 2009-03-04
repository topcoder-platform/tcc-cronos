/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.clients.webservices.accuracytests;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.topcoder.clients.model.AuditableEntity;

import junit.framework.TestCase;

/**
 * <p>
 * Base test cases for unit tests.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyBaseTest extends TestCase {

    /**
     * An EntityManagerFactory instance used in tests to get EntityManager's.
     */
    private static EntityManagerFactory managerFactory;
    
    /**
     * An EntityManager instance used in tests.
     */
    private static EntityManager manager;

    /**
     * Sets up the environment.
     * 
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        getEntityManager();
        executeScript("/data.sql");
    }

    /**
     * Tears down the environment.
     * 
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
        executeScript("/clear.sql");
        releaseEntityManager();
    }

    /**
     * Get EntityManager.
     * 
     * @return EntityManager
     */
    protected EntityManager getEntityManager() {
        if (manager == null || !manager.isOpen()) {
            // create manager
            try {
                if (managerFactory == null || !managerFactory.isOpen()) {
                    managerFactory = Persistence.createEntityManagerFactory("persistenceUnitLocal");
                }
                manager = managerFactory.createEntityManager();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e.getCause());
            }
        }

        manager.clear();
        return manager;
    }
    
    /**
     * Releases the entity manager and entity manager factory.
     */
    protected void releaseEntityManager() {
        try {
            if (manager != null && manager.isOpen()) {
                manager.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        
        try {
            if (managerFactory != null || managerFactory.isOpen()) {
                managerFactory.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * <p>
     * Executes the sql script.
     * </p>
     * 
     * @param fileName The file name of sql script to be executed.
     * @throws Exception to JUnit
     */
    private void executeScript(String fileName) throws Exception {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();

        for (String sql : buildSqlScript(fileName).split(";;")) {
            em.createNativeQuery(sql).executeUpdate();
        }
        em.getTransaction().commit();
    }

    /**
     * <p>
     * Build the sql script from file.
     * </p>
     * 
     * @param file The sql file having the content.
     * @return The built sql statement.
     * @throws Exception to JUnit
     */
    private static String buildSqlScript(String file) throws Exception {
        StringBuffer sql = new StringBuffer();

        BufferedReader in = null;
        try {
            InputStream is = AccuracyBaseTest.class.getResourceAsStream(file);

            in = new BufferedReader(new InputStreamReader(is));

            String s = in.readLine();

            while (s != null) {
                sql.append(s);
                s = in.readLine();
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }

        return sql.toString();
    }

    /**
     * Check the ids are in list.
     * 
     * @param entities the list of the sub class of AuditableEntity class
     * @param ids the id array
     */
    protected static void checkIds(List<? extends AuditableEntity> entities, Long... ids) {
        List<Long> entityIds = new ArrayList<Long>();
        for (AuditableEntity e : entities) {
            entityIds.add(e.getId());
        }
        for (Long id : ids) {
            assertTrue("Specified id does not in list.", entityIds.contains(id));
        }
    }

}
