/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.accuracytests;

import com.topcoder.clients.manager.CompanyManager;
import com.topcoder.clients.manager.dao.DAOCompanyManager;
import com.topcoder.clients.manager.dao.Util;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;

import com.topcoder.search.builder.filter.EqualToFilter;

import junit.framework.TestCase;

import java.util.List;


/**
 * Accuracy test for CompanyManager implementation.
 *
 * @author onsky
 * @version 1.0
 */
public class CompanyManagerTests extends TestCase {
    /** Represents the CompanyManager instance for testing. */
    private CompanyManager manager = null;

    /**
     * Set up the environment.
     *
     * @throws Exception to junit
     */
    public void setUp() throws Exception {
        Util.clearConfigManager();
        Util.loadConfiguration();

        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "test");
        obj.setPropertyValue("logger_name", "testlog");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);
        obj.setPropertyValue("company_dao", "com.topcoder.clients.manager.accuracytests.MockCompanyDAO");

        manager = new DAOCompanyManager(obj);
    }

    /**
     * Clear the ConfigManager.
     *
     * @throws Exception to junit
     */
    public void tearDown() throws Exception {
        Util.clearConfigManager();
    }

    /**
     * <p>Accuracy test for the constructor <code>DAOClientManager()</code>, expects the instance is created
     * properly.</p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor1Accuracy() throws Exception {
        manager = new DAOCompanyManager();
        assertNotNull("Failed to create the DAOCompanyManager instance.", manager);
        assertNotNull("Failed to create the DAOCompanyManager instance.",
                TestHelper.getPropertyByReflection(manager, "companyDAO"));
    }

    /**
     * <p>Accuracy test for the constructor <code>DAOCompanyManager(ConfigurationObject)</code>, expects the
     * instance is created properly.</p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor2Accuracy() throws Exception {
        assertNotNull("Failed to create the DAOCompanyManager instance.", manager);
        assertNotNull("Failed to create the DAOCompanyManager instance.",
                TestHelper.getPropertyByReflection(manager, "companyDAO"));
    }

    /**
     * <p>Accuracy test for method createCompany.</p>
     *
     * @throws Exception to junit
     */
    public void testCreateCompany() throws Exception {
        // create company
        Company company = new Company();
        company.setPasscode("passcode");
        company.setName("company name");
        company.setDeleted(false);

        Company created = manager.createCompany(company);
        assertTrue("company must be create with an generated id", created.getId() > 0);
    }

    /**
     * <p>Accuracy test for method retrieveCompany and updateCompany.</p>
     *
     * @throws Exception to junit
     */
    public void testFindAndUpdateCompany() throws Exception {
        // find company by id
        Company found = manager.retrieveCompany(1L);
        assertNotNull("company with given id must exist", found);
        // update company
        found.setName("update company name");

        Company updated = manager.updateCompany(found);
        assertEquals("company must be updated", "update company name", updated.getName());
    }

    /**
     * <p>Accuracy test for method deleteCompany.</p>
     *
     * @throws Exception to junit
     */
    public void testDeleteCompany() throws Exception {
        // find company by id
        Company found = manager.retrieveCompany(1L);

        // delete company
        Company deleted = manager.deleteCompany(found);
        assertTrue("the company must be deleted", deleted.isDeleted());
    }

    /**
     * <p>Accuracy test for method retrieveAllCompanies.</p>
     *
     * @throws Exception to junit
     */
    public void testRetrieveAllCompanies() throws Exception {
        // get all companys
        List<Company> companys = manager.retrieveAllCompanies();
        assertEquals("there must be 3 companys in all", 3, companys.size());
    }

    /**
     * <p>Accuracy test for method searchCompaniesByName.</p>
     *
     * @throws Exception to junit
     */
    public void testSearchCompaniesByName() throws Exception {
        // search company by name
        List<Company> companies = manager.searchCompaniesByName("test");
        assertEquals("one record must return", 1, companies.size());
        assertEquals("company name must be correct", "test", companies.get(0).getName());
    }

    /**
     * <p>Accuracy test for method searchCompanysByName.</p>
     *
     * @throws Exception to junit
     */
    public void testSearchCompanys() throws Exception {
        // search companies by filter
        List<Company> companies = manager.searchCompanies(new EqualToFilter("test", new Boolean(false)));
        assertEquals("two record must return", 2, companies.size());
    }

    /**
     * <p>Accuracy test for method getClientsForCompany.</p>
     *
     * @throws Exception to junit
     */
    public void testGetClientsForCompany() throws Exception {
        Company company = new Company();
        company.setPasscode("passcode");
        company.setName("company name");
        company.setDeleted(false);

        List<Client> clients = manager.getClientsForCompany(company);
        assertEquals("1 record must return", 1, clients.size());
    }

    /**
     * <p>Accuracy test for method getProjectsForCompany.</p>
     *
     * @throws Exception to junit
     */
    public void testGetProjectsForCompany() throws Exception {
        Company company = new Company();
        company.setPasscode("passcode");
        company.setName("company name");
        company.setDeleted(false);

        List<Project> list = manager.getProjectsForCompany(company);
        assertEquals("Not Record for this company", 0, list.size());
    }
}
