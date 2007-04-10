/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.OrFilter;

import com.topcoder.timetracker.company.ejb.LocalCompanyManagerDelegate;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.State;

import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;

import junit.framework.TestCase;

import java.sql.Connection;

import javax.ejb.EJBException;


/**
 * <p>
 * Demonstrates the usage of this component. The usage include two parts. One part is for Time Tracker Company
 * management and searching, and the second part is for the usage of DbCompanyDAO.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DemoTest extends TestCase {
    /** Represents the connection instance for testing. */
    private Connection connection = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created. The test configuration namespace will be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        UnitTestHelper.addConfig();
        connection = UnitTestHelper.getConnection();
        UnitTestHelper.prepareData(connection);

        // create delegate
        UnitTestHelper.deployEJB();
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared. The test data in the table will
     * be cleared.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        try {
            UnitTestHelper.clearConfig();
            UnitTestHelper.clearDatabase(connection);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        UnitTestHelper.undeployEJB();
    }

    /**
     * <p>
     * A simple demonstration of Time Tracker Company management and searching.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testTrackerCompanyManagement_Usage() throws Exception {
        CompanyManager manager = new LocalCompanyManagerDelegate(LocalCompanyManagerDelegate.class.getName());

        // the id will be auto generated.
        Company companyToCreate = new Company();
        companyToCreate.setId(1);
        companyToCreate.setCompanyName("new Company");
        companyToCreate.setPassCode("newPwd");

        // create the address and contact
        Address addy = new Address();
        addy.setId(1);
        addy.setLine1("Palm Street");
        addy.setLine2("Maple Drive");
        addy.setCity("Florida City");

        State state = new State();
        state.setName("floridaState");
        addy.setState(state);

        Contact contact = new Contact();
        contact.setId(4);
        contact.setFirstName("Mr.");
        contact.setLastName("User");
        contact.setPhoneNumber("555-5555");
        contact.setEmailAddress("user@user.com");

        companyToCreate.setContact(contact);
        companyToCreate.setAddress(addy);

        // Create new company and perform audit
        manager.createCompany(companyToCreate, "adminUser", true);

        // companyToCreate should now have an assigned id and modification date,user & creation date,user assigned.
        System.out.println(companyToCreate.getId());

        // Get all companies in the datastore
        Company[] companyList = manager.listCompanies();
        System.out.println("Company count: " + companyList.length);

        // Update a company, no audit
        companyToCreate.setCompanyName("newCompanyName");
        manager.updateCompany(companyToCreate, "admin", false);

        // Delete a company from the datastore
        // Perform audit, the deletion is done by a user with username "admin"
        manager.deleteCompany(companyToCreate, true, "admin");

        // Retrieve a company with id = 10
        manager.retrieveCompany(10);

        // Delete companies: companyToCreate
        // Use batch mode deletion. Perform audit. The deletion is done by a user
        // with username "admin". The deletion will be atomic.
        Company[] companiesToDelete = {companyToCreate};
        try {
            manager.deleteCompanies(companiesToDelete, true, true, "admin");
            fail("Should throw EJBException here as the company to delete does not exist.");
        } catch (EJBException e) {
            // good
        }

        // Retrieve companies with id = 1, 2, 5
        long[] ids = new long[] {1, 2, 5};
        manager.retrieveCompanies(ids);

        // Create 2 new companies
        // Add them to datastore with batch mode and no audit, the batch mode is not atomic
        Company[] companiesToAdd = {companyToCreate};
        companiesToAdd = manager.createCompanies(companiesToAdd, "admin", false, false);

        // Search for all companies with whose name contains "Jo" that is in New York.
        // Similar approach can be used to search for other criteria
        CompanySearchBuilder builder = new CompanySearchBuilder();
        builder.hasCompanyName("Jo");
        builder.hasCity("New York");

        Filter searchFilter1 = builder.buildFilter();

        // Now the manager can be used to search:
        Company[] results = manager.search(searchFilter1);

        // Search companies who either satisfy the existing criteria or was created by a
        // user with username = "babut"
        CompanySearchBuilder builder2 = new CompanySearchBuilder();
        builder2.createdByUser("babut");

        Filter searchFilter2 = builder2.buildFilter();

        Filter combinedFilter = new OrFilter(searchFilter1, searchFilter2);

        // Search the manager with the combined criteria
        manager.search(combinedFilter);

        // All companies in New York have relocated to Old York
        for (int x = 0; x < results.length; x++) {
            results[x].getAddress().setCity("Old York");
        }

        // Perform a batch update on those companies, no audit
        manager.updateCompanies(results, "admin", true, false);

        // The builder can be reset to perform additional searches.
        builder.reset();
    }

    /**
     * <p>
     * A simple demonstration of the DbCompanyDAO.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO_Usage() throws Exception {
        // The Object Factory 2.0 may be used to create DbCompanyDAO
        ObjectFactory daoFactory = new ObjectFactory(new ConfigManagerSpecificationFactory(
                    "com.topcoder.timetracker.company.ejb.objectfactory"));
        CompanyDAO companyDAO = (CompanyDAO) daoFactory.createObject("DbCompanyDAO");

        // create the company
        Company company = new Company();
        company.setCompanyName("new Company");
        company.setPassCode("pwd");

        Address addy = new Address();
        addy.setId(1);
        addy.setLine1("Palm Street");
        addy.setLine2("Maple Drive");
        addy.setCity("Florida City");

        State state = new State();
        state.setName("floridaState");
        addy.setState(state);

        Contact contact = new Contact();
        contact.setId(2);
        contact.setFirstName("Mr.");
        contact.setLastName("User");
        contact.setPhoneNumber("555-5555");
        contact.setEmailAddress("user@user.com");

        company.setContact(contact);
        company.setAddress(addy);
        companyDAO.createCompany(company, "admin", true);

        // update the company
        company.setPassCode("newPwd");
        companyDAO.updateCompany(company, "admin", true);

        // retrieve the company and show the new pass code
        Company persisted = companyDAO.retrieveCompany(company.getId());
        System.out.println(persisted.getPassCode());

        // retrieve all the companies
        companyDAO.listCompanies();

        // delete the company
        companyDAO.deleteCompany(company, true, "admin");

        // batch create
        Company[] companies = new Company[] {company};
        companyDAO.createCompanies(companies, "admin", true);

        // batch update
        companyDAO.updateCompanies(companies, "admin", true);

        // batch delete
        companyDAO.deleteCompanies(companies, true, "admin");
    }
}
