/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.stresstests;

import java.util.List;

import com.topcoder.clients.manager.dao.DAOCompanyManager;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.search.builder.filter.EqualToFilter;

import junit.framework.TestCase;

/**
 * <p>
 * Stress tests for <code>DAOCompanyManager</code>.
 * </p>
 * @author mayday
 * @version 1.0
 *
 */
public class DAOCompanyManagerStressTest extends TestCase {

    /**
     * Number of repeat.
     */
    private static final int REPEAT_NUMBER = 10;

    /**
     * <p>
     * DAOCompanyManager used in test.
     * </p>
     */
    private DAOCompanyManager manager;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        StressTestHelper.loadConfig();

        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "test");
        obj.setPropertyValue("logger_name", "logger");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);
        obj.setPropertyValue("company_dao", "com.topcoder.clients.manager.stresstests.MockCompanyDAOStress");

        manager = new DAOCompanyManager(obj);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        StressTestHelper.clearConfig();
        manager = null;
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOCompanyManager#
     * createCompany(com.topcoder.clients.model.Company)}.
     *
     * Test create company and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testCreateCompany() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            Company ret = manager.createCompany(createCompany());
            assertEquals("should be company", "company", ret.getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOCompanyManager#
     * updateCompany(com.topcoder.clients.model.Company)}.
     *
     * Test update company and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateCompany() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            Company ret = manager.updateCompany(createCompany());
            assertEquals("should be stress", "stress", ret.getModifyUsername());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOCompanyManager#
     * deleteCompany(com.topcoder.clients.model.Company)}.
     *
     * Test delete company and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteCompany() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            Company ret = manager.deleteCompany(createCompany());
            assertEquals("should be stress", "stress", ret.getModifyUsername());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOCompanyManager#retrieveCompany(long)}.
     *
     * Test retrieve company and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveCompany() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            Company ret = manager.retrieveCompany(2);
            assertEquals("should be company", "company", ret.getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOCompanyManager#retrieveAllCompanies()}.
     *
     * Test retrieve all company list and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveAllCompanies() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            List<Company> ret = manager.retrieveAllCompanies();
            assertTrue("should be 1", ret.size() == 1);
            assertEquals("should be company", "company", ret.get(0).getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOCompanyManager#
     * searchCompaniesByName(java.lang.String)}.
     *
     * Test search company by name and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testSearchCompaniesByName() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            List<Company> ret = manager.searchCompaniesByName("company");
            assertTrue("should be 1", ret.size() == 1);
            assertEquals("should be company", "company", ret.get(0).getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOCompanyManager#
     * searchCompanies(com.topcoder.search.builder.filter.Filter)}.
     *
     * Test search company and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testSearchCompanies() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            List<Company> ret = manager.searchCompanies(new EqualToFilter("company", false));
            assertTrue("should be 1", ret.size() == 1);
            assertEquals("should be company", "company", ret.get(0).getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOCompanyManager#
     * getClientsForCompany(com.topcoder.clients.model.Company)}.
     *
     * Test get clients for company and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testGetClientsForCompany() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            List<Client> ret = manager.getClientsForCompany(createCompany());
            assertTrue("should be 1", ret.size() == 1);
            assertEquals("should be client", "client", ret.get(0).getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOCompanyManager#
     * getProjectsForCompany(com.topcoder.clients.model.Company)}.
     *
     * Test get projects for company and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testGetProjectsForCompany() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            List<Project> ret = manager.getProjectsForCompany(createCompany());
            assertTrue("should be 1", ret.size() == 1);
            assertEquals("should be project", "project", ret.get(0).getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Create a company for test.
     * @return the created company.
     */
    private Company createCompany() {
        Company c = new Company();
        c.setName("company");
        c.setDeleted(false);
        c.setPasscode("passcode");
        c.setId(2L);
        return c;
    }

}
