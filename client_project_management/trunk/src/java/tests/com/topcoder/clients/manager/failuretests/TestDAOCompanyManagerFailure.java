/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.failuretests;

import com.topcoder.clients.manager.CompanyEntityNotFoundException;
import com.topcoder.clients.manager.CompanyManagerException;
import com.topcoder.clients.manager.ManagerConfigurationException;
import com.topcoder.clients.manager.dao.DAOCompanyManager;
import com.topcoder.clients.model.Company;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.search.builder.filter.EqualToFilter;

import junit.framework.TestCase;

/**
 * Failure test cases for class <code>DAOCompanyManager </code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 *
 */
public class TestDAOCompanyManagerFailure extends TestCase {

    /**
     * Represents the DAOCompanyManager instance for testing.
     */
    private DAOCompanyManager manager = null;

    /**
     * Set up the environment.
     *
     * @throws Exception
     *             to junit
     */
    public void setUp() throws Exception {
        TestHelper.clearConfigManager();
        TestHelper.loadConfiguration();

        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "test");
        obj.setPropertyValue("logger_name", "System.out");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);
        obj.setPropertyValue("company_dao", "com.topcoder.clients.manager.MockCompanyDAO");

        manager = new DAOCompanyManager(obj);
    }

    /**
     * Test method for 'DAOCompanyManager(ConfigurationObject)'.
     * <p>
     * The configuration is null, IAE is expected
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOCompanyManagerConfigurationObjectFailure1() throws Exception {
        try {

            new DAOCompanyManager(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test method for 'DAOCompanyManager(ConfigurationObject)'.
     * <p>
     * The configuration is has no child configuration, ManagerConfigurationException is expected
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOCompanyManagerConfigurationObjectFailure2() throws Exception {
        try {
            ConfigurationObject obj = new DefaultConfigurationObject("root");
            obj.setPropertyValue("id_generator_name", "test");
            obj.setPropertyValue("logger_name", "System.out");

            obj.setPropertyValue("company_dao", "com.topcoder.clients.manager.MockCompanyDAO");

            new DAOCompanyManager(obj);
            fail("ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * Test method for 'DAOCompanyManager(ConfigurationObject)'.
     * <p>
     * The configuration for companyDAO is missing, ManagerConfigurationException is expected
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOCompanyManagerConfigurationObjectFailure3() throws Exception {
        try {
            ConfigurationObject obj = new DefaultConfigurationObject("root");
            obj.setPropertyValue("id_generator_name", "test");
            obj.setPropertyValue("logger_name", "System.out");

            ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

            obj.addChild(child);

            new DAOCompanyManager(obj);
            fail("ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * Test method for 'DAOCompanyManager(ConfigurationObject)'.
     * <p>
     * The configuration for companyDAO is not valid, ManagerConfigurationException is expected
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOCompanyManagerConfigurationObjectFailure4() throws Exception {
        try {
            ConfigurationObject obj = new DefaultConfigurationObject("root");
            obj.setPropertyValue("id_generator_name", "test");
            obj.setPropertyValue("logger_name", "System.out");

            ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

            obj.addChild(child);
            obj.setPropertyValue("company_dao", "");
            new DAOCompanyManager(obj);
            fail("ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * Test method for 'DAOCompanyManager(ConfigurationObject)'.
     * <p>
     * The configuration for companyDAO is not valid, ManagerConfigurationException is expected
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOCompanyManagerConfigurationObjectFailure5() throws Exception {
        try {
            ConfigurationObject obj = new DefaultConfigurationObject("root");
            obj.setPropertyValue("id_generator_name", "test");
            obj.setPropertyValue("logger_name", "System.out");

            ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

            obj.addChild(child);
            obj.setPropertyValue("company_dao", "com.topcoder.clients.manager.MockClientDAO");
            new DAOCompanyManager(obj);
            fail("ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * Test method for 'DAOCompanyManager(ConfigurationObject)'.
     * <p>
     * The configuration for companyDAO is not valid, ManagerConfigurationException is expected
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOCompanyManagerConfigurationObjectFailure6() throws Exception {
        try {
            ConfigurationObject obj = new DefaultConfigurationObject("root");
            obj.setPropertyValue("id_generator_name", "test");
            obj.setPropertyValue("logger_name", "System.out");

            ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

            obj.addChild(child);
            obj.setPropertyValue("company_dao", "abc");
            new DAOCompanyManager(obj);
            fail("ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * Test method for 'createCompany(Company)'.
     *
     * <p>
     * The param is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateCompanyFailure1() throws Exception {
        try {
            manager.createCompany(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test method for 'createCompany(Company)'.
     *
     * <p>
     * The passcode is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateCompanyFailure2() throws Exception {
        try {
            Company c = new Company();
            c.setPasscode(null);
            c.setDeleted(false);

            manager.createCompany(c);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test method for 'createCompany(Company)'.
     *
     * <p>
     * The passcode is empty, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateCompanyFailure3() throws Exception {
        try {
            Company c = new Company();
            c.setPasscode("  ");
            c.setDeleted(false);

            manager.createCompany(c);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test method for 'createCompany(Company)'.
     *
     * <p>
     * The isDeleted is true, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateCompanyFailure4() throws Exception {
        try {
            Company c = new Company();
            c.setPasscode("a");
            c.setDeleted(true);

            manager.createCompany(c);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test method for 'createCompany(Company)'.
     *
     * <p>
     * Inner DAOException will be thrown, CompanyManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateCompanyFailure5() throws Exception {
        try {
            Company c = new Company();
            c.setName("DAOException");
            c.setPasscode("a");
            c.setDeleted(false);

            manager.createCompany(c);
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // expected
        }
    }

    /**
     * Test method for 'createCompany(Company)'.
     *
     * <p>
     * Inner DAOConfigurationException will be thrown, CompanyManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateCompanyFailure6() throws Exception {
        try {
            Company c = new Company();
            c.setName("DAOConfigurationException");
            c.setPasscode("a");
            c.setDeleted(false);

            manager.createCompany(c);
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // expected
        }
    }

    /**
     * Test method for 'updateCompany(Company)'.
     *
     * <p>
     * the company is not valid(null), IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateCompanyFailure1() throws Exception {
        try {
            manager.updateCompany(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test method for 'updateCompany(Company)'.
     *
     * <p>
     * the company is not valid(passcode is null), IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateCompanyFailure2() throws Exception {
        try {
            Company c = new Company();
            c.setPasscode(null);
            c.setId(1L);
            manager.updateCompany(c);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test method for 'updateCompany(Company)'.
     *
     * <p>
     * the company is not valid(passcode is empty), IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateCompanyFailure3() throws Exception {
        try {
            Company c = new Company();
            c.setPasscode("");
            c.setId(1L);
            manager.updateCompany(c);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test method for 'updateCompany(Company)'.
     *
     * <p>
     * the company is not valid(isDeleted is true), IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateCompanyFailure4() throws Exception {
        try {
            Company c = new Company();
            c.setPasscode("code");
            c.setId(1L);
            c.setDeleted(true);
            manager.updateCompany(c);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test method for 'updateCompany(Company)'.
     *
     * <p>
     * the company can not be found in the persistence, CompanyEntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateCompanyFailure5() throws Exception {
        try {
            Company c = new Company();
            c.setPasscode("code");
            c.setId(-1L);
            c.setDeleted(false);
            manager.updateCompany(c);
            fail("CompanyEntityNotFoundException is expected.");
        } catch (CompanyEntityNotFoundException e) {
            // expected
        }
    }

    /**
     * Test method for 'updateCompany(Company)'.
     *
     * <p>
     * the company can not be found, CompanyEntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateCompanyFailure6() throws Exception {
        try {
            Company c = new Company();
            c.setPasscode("code");
            c.setId(1L);
            c.setDeleted(false);
            manager.updateCompany(c);
            fail("CompanyEntityNotFoundException is expected.");
        } catch (CompanyEntityNotFoundException e) {
            // expected
        }
    }

    /**
     * Test method for 'updateCompany(Company)'.
     *
     * <p>
     * Inner DAOException will be thrown, CompanyManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateCompanyFailure7() throws Exception {
        try {
            Company c = new Company();
            c.setPasscode("code");
            c.setName("DAOException");
            c.setId(2L);
            c.setDeleted(false);
            manager.updateCompany(c);
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // expected
        }
    }

    /**
     * Test method for 'updateCompany(Company)'.
     *
     * <p>
     * Inner DAOConfigurationException will be thrown, CompanyManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateCompanyFailure8() throws Exception {
        try {
            Company c = new Company();
            c.setPasscode("code");
            c.setName("DAOConfigurationException");
            c.setId(2L);
            c.setDeleted(false);
            manager.updateCompany(c);
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // expected
        }
    }

    /**
     * Test method for 'deleteCompany(Company)'.
     * <p>
     * The param is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteCompanyFailure1() throws Exception {
        try {
            manager.deleteCompany(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test method for 'deleteCompany(Company)'.
     * <p>
     * The company has isDeleted true, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteCompanyFailure2() throws Exception {
        try {
            Company c = new Company();
            c.setDeleted(true);
            manager.deleteCompany(c);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test method for 'deleteCompany(Company)'.
     * <p>
     * The company does not found, CompanyEntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteCompanyFailure3() throws Exception {
        try {
            Company c = new Company();
            c.setName("EntityNotFoundException");
            c.setDeleted(false);
            manager.deleteCompany(c);
            fail("CompanyEntityNotFoundException is expected.");
        } catch (CompanyEntityNotFoundException e) {
            // expected
        }
    }

    /**
     * Test method for 'deleteCompany(Company)'.
     * <p>
     * Inner DAOException thrown , CompanyManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteCompanyFailure4() throws Exception {
        try {
            Company c = new Company();
            c.setName("DAOException");
            c.setDeleted(false);
            manager.deleteCompany(c);
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // expected
        }
    }

    /**
     * Test method for 'deleteCompany(Company)'.
     * <p>
     * Inner DAOConfigurationException thrown , CompanyManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteCompanyFailure5() throws Exception {
        try {
            Company c = new Company();
            c.setName("DAOConfigurationException");
            c.setDeleted(false);
            manager.deleteCompany(c);
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // expected
        }
    }

    /**
     * Test method for 'retrieveCompany(long)'.
     * <p>
     * The id is not positive, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveCompanyFailure1() throws Exception {
        try {
            manager.retrieveCompany(-1);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test method for 'retrieveCompany(long)'.
     * <p>
     * Inner DAOException is thrown, CompanyManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveCompanyFailure2() throws Exception {
        try {
            manager.retrieveCompany(4L);
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // expected
        }
    }

    /**
     * Test method for 'retrieveCompany(long)'.
     * <p>
     * Inner DAOConfigurationException is thrown, CompanyManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveCompanyFailure3() throws Exception {
        try {
            manager.retrieveCompany(5L);
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // expected
        }
    }

    /**
     * Test method for 'retrieveCompany(long)'.
     * <p>
     * Non-company is returned, CompanyManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveCompanyFailure4() throws Exception {
        try {
            manager.retrieveCompany(3L);
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // expected
        }
    }

    /**
     * Test method for 'searchCompaniesByName(String)'.
     * <p>
     * name is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchCompaniesByNameFailure1() throws Exception {
        try {
            manager.searchCompaniesByName(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test method for 'searchCompaniesByName(String)'.
     * <p>
     * name is empty, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchCompaniesByNameFailure2() throws Exception {
        try {
            manager.searchCompaniesByName("  ");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test method for 'searchCompaniesByName(String)'.
     * <p>
     * Inner DAOException is thrown, CompanyManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchCompaniesByNameFailure3() throws Exception {
        try {
            manager.searchCompaniesByName("DAOException");
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // expected
        }
    }

    /**
     * Test method for 'searchCompaniesByName(String)'.
     * <p>
     * Inner DAOConfigurationException is thrown, CompanyManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchCompaniesByNameFailure4() throws Exception {
        try {
            manager.searchCompaniesByName("DAOConfigurationException");
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // expected
        }
    }

    /**
     * Test method for 'searchCompanies(Filter)'.
     * <p>
     * if the filter is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchCompaniesFailure1() throws Exception {
        try {
            manager.searchCompanies(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test method for 'searchCompanies(Filter)'.
     * <p>
     * Inner DAOException thrown, CompanyManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchCompaniesFailure2() throws Exception {
        try {
            EqualToFilter f = new EqualToFilter("DAOException", new Boolean(false));

            manager.searchCompanies(f);
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // expected
        }
    }

    /**
     * Test method for 'searchCompanies(Filter)'.
     * <p>
     * Inner DAOConfigurationException thrown, CompanyManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchCompaniesFailure3() throws Exception {
        try {
            EqualToFilter f = new EqualToFilter("DAOConfigurationException", new Boolean(false));

            manager.searchCompanies(f);
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // expected
        }
    }

    /**
     * Test method for 'getClientsForCompany(Company)'.
     * <p>
     * the param is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetClientsForCompanyFailure1() throws Exception {
        try {
            manager.getClientsForCompany(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test method for 'getClientsForCompany(Company)'.
     * <p>
     * the id is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetClientsForCompanyFailure2() throws Exception {
        try {
            Company c = new Company();
            c.setId(-1);
            manager.getClientsForCompany(c);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test method for 'getClientsForCompany(Company)'.
     * <p>
     * Inner DAOException will be thrown, CompanyManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetClientsForCompanyFailure3() throws Exception {
        try {
            Company c = new Company();
            c.setId(1L);
            manager.getClientsForCompany(c);
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // expected
        }
    }

    /**
     * Test method for 'getClientsForCompany(Company)'.
     * <p>
     * Inner DAOConfigurationException will be thrown, CompanyManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetClientsForCompanyFailure4() throws Exception {
        try {
            Company c = new Company();
            c.setId(2L);
            manager.getClientsForCompany(c);
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // expected
        }
    }

    /**
     * Test method for 'getProjectsForCompany(Company)'.
     *
     * <p>
     * Company is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetProjectsForCompanyFailure1() throws Exception {
        try {
            manager.getProjectsForCompany(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test method for 'getProjectsForCompany(Company)'.
     *
     * <p>
     * Company id is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetProjectsForCompanyFailure2() throws Exception {
        try {
            Company c = new Company();
            c.setId(-1);

            manager.getProjectsForCompany(c);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test method for 'getProjectsForCompany(Company)'.
     *
     * <p>
     * Inner DAOException will be thrown, CompanyManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetProjectsForCompanyFailure3() throws Exception {
        try {
            Company c = new Company();
            c.setId(1);

            manager.getProjectsForCompany(c);
            fail("CompanyManagerException  is expected.");
        } catch (CompanyManagerException e) {
            // expected
        }
    }

    /**
     * Test method for 'getProjectsForCompany(Company)'.
     *
     * <p>
     * Inner DAOConfigurationException will be thrown, CompanyManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetProjectsForCompanyFailure4() throws Exception {
        try {
            Company c = new Company();
            c.setId(2);

            manager.getProjectsForCompany(c);
            fail("CompanyManagerException  is expected.");
        } catch (CompanyManagerException e) {
            // expected
        }
    }
}
