/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.gameplan;

import com.topcoder.service.gameplan.ejb.GamePlanServiceBeanTests;
import com.topcoder.service.gameplan.ejb.GamePlanServiceBeanWithJBossTests;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author FireIce
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({GamePlanServiceExceptionTests.class, GamePlanServiceConfigurationExceptionTests.class,
        GamePlanPersistenceExceptionTests.class, GamePlanServiceBeanTests.class, DemoTests.class,
        GamePlanServiceBeanWithJBossTests.class })
public class UnitTests {
    /**
     * Setup the readonly database by inserting the test data.
     *
     * @throws Exception  pass any unexpected exception to JUnit.
     */
    @BeforeClass
    public static void setUpDatabase() throws Exception {
        executeSQLFile(TestHelper.getSoftwareEntityManager(), "test_files/clean_tcs_catalog.sql");
        executeSQLFile(TestHelper.getStudioEntityManager(), "test_files/clean_studio_oltp.sql");
        executeSQLFile(TestHelper.getSoftwareEntityManager(), "test_files/testData_tcs_catalog.sql");
        executeSQLFile(TestHelper.getStudioEntityManager(), "test_files/testData_studio_oltp.sql");
    }

    /**
     * Cleans the database by removing the test data.
     *
     * @throws Exception  pass any unexpected exception to JUnit.
     */
    @AfterClass
    public static void cleanDatabase() throws Exception {
        executeSQLFile(TestHelper.getSoftwareEntityManager(), "test_files/clean_tcs_catalog.sql");
        executeSQLFile(TestHelper.getStudioEntityManager(), "test_files/clean_studio_oltp.sql");
    }


    /**
     * Executes the sqls in the sql file line by line.
     *
     * @param entityManager the EntityManager instance.,
     * @param filename the sql file name
     * @throws Exception  pass any unexpected exception to JUnit.
     */
    private static void executeSQLFile(EntityManager entityManager, String filename) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));

        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.length() != 0) {
                if (line.endsWith(";")) {
                    line = line.substring(0, line.length() - 1);
                }
                entityManager.createNativeQuery(line).executeUpdate();
            }
        }

        entityManager.getTransaction().commit();
    }
}
