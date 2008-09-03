/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.dao;

import java.util.List;

import com.topcoder.clients.manager.CompanyEntityNotFoundException;
import com.topcoder.clients.manager.CompanyManagerException;
import com.topcoder.clients.manager.ManagerConfigurationException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.search.builder.filter.EqualToFilter;

import junit.framework.TestCase;

/**
 * Unit test cases for class <code>DAOCompanyManager </code>.
 *
 * @author Chenhong
 * @version 1.0
 *
 */
public class TestDAOCompanyManager extends TestCase {

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
        Util.clearConfigManager();
        Util.loadConfiguration();

        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "test");
        obj.setPropertyValue("logger_name", "System.out");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);
        obj.setPropertyValue("company_dao", "com.topcoder.clients.manager.MockCompanyDAO");

        manager = new DAOCompanyManager(obj);
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOCompanyManager.DAOCompanyManager()'.
     * <p>
     * DAOCompanyManager should be created.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOCompanyManager() throws Exception {
        manager = new DAOCompanyManager();
        assertNotNull("The DAOCompanyManager should be created.");
    }

    /**
     * Test method for 'DAOCompanyManager(ConfigurationObject)'.
     * <p>
     * The DAOCompanyManager should be created with given ConfigurationObject
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOCompanyManagerConfigurationObject_Acc() throws Exception {
        assertNotNull("The DAOCompanyManager should be created.", manager);
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
    public void testDAOCompanyManagerConfigurationObject_F_1() throws Exception {
        try {

            new DAOCompanyManager(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
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
    public void testDAOCompanyManagerConfigurationObject_F_2() throws Exception {
        try {
            ConfigurationObject obj = new DefaultConfigurationObject("root");
            obj.setPropertyValue("id_generator_name", "test");
            obj.setPropertyValue("logger_name", "System.out");

            obj.setPropertyValue("company_dao", "com.topcoder.clients.manager.MockCompanyDAO");

            new DAOCompanyManager(obj);
            fail("ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {
            // ok
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
    public void testDAOCompanyManagerConfigurationObject_F_3() throws Exception {
        try {
            ConfigurationObject obj = new DefaultConfigurationObject("root");
            obj.setPropertyValue("id_generator_name", "test");
            obj.setPropertyValue("logger_name", "System.out");

            ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

            obj.addChild(child);

            new DAOCompanyManager(obj);
            fail("ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {
            // ok
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
    public void testDAOCompanyManagerConfigurationObject_F_4() throws Exception {
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
            // ok
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
    public void testDAOCompanyManagerConfigurationObject_F_5() throws Exception {
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
            // ok
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
    public void testDAOCompanyManagerConfigurationObject_F_6() throws Exception {
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
            // ok
        }
    }

    /**
     * Test method for 'DAOCompanyManager(String, String)'.
     *
     * <p>
     * There are many test case to test the function which parse configuration file to get configurationObject. So I
     * only write one test case to test if the DAOCompanyManager can be created with filename and namespace.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOCompanyManagerStringString_Accuracy() throws Exception {
        manager = new DAOCompanyManager("test_files/daocompany.properties", "daoCompany");
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
    public void testCreateCompany_1() throws Exception {
        try {
            manager.createCompany(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
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
    public void testCreateCompany_2() throws Exception {
        try {
            Company c = new Company();
            c.setPasscode(null);
            c.setDeleted(false);

            manager.createCompany(c);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
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
    public void testCreateCompany_3() throws Exception {
        try {
            Company c = new Company();
            c.setPasscode("  ");
            c.setDeleted(false);

            manager.createCompany(c);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
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
    public void testCreateCompany_4() throws Exception {
        try {
            Company c = new Company();
            c.setPasscode("a");
            c.setDeleted(true);

            manager.createCompany(c);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
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
    public void testCreateCompany_5() throws Exception {
        try {
            Company c = new Company();
            c.setName("DAOException");
            c.setPasscode("a");
            c.setDeleted(false);

            manager.createCompany(c);
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // ok
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
    public void testCreateCompany_6() throws Exception {
        try {
            Company c = new Company();
            c.setName("DAOConfigurationException");
            c.setPasscode("a");
            c.setDeleted(false);

            manager.createCompany(c);
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'createCompany(Company)'.
     *
     * <p>
     * Inner ClassCastException will be thrown, CompanyManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateCompany_7() throws Exception {
        try {
            Company c = new Company();
            c.setName("ClassCastException");
            c.setPasscode("a");
            c.setDeleted(false);

            manager.createCompany(c);
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'createCompany(Company)'.
     *
     * <p>
     * The company will be created.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateCompany_acc() throws Exception {

        Company c = new Company();
        c.setName("ok");
        c.setPasscode("a");
        c.setDeleted(false);

        Company ret = manager.createCompany(c);

        assertTrue("The id should be generated.", ret.getId() > 0);

        assertEquals("Equal to 'test'", "test", ret.getCreateUsername());
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
    public void testUpdateCompany_F_1() throws Exception {
        try {
            manager.updateCompany(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
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
    public void testUpdateCompany_F_2() throws Exception {
        try {
            Company c = new Company();
            c.setPasscode(null);
            c.setId(1L);
            manager.updateCompany(c);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
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
    public void testUpdateCompany_F_3() throws Exception {
        try {
            Company c = new Company();
            c.setPasscode("");
            c.setId(1L);
            manager.updateCompany(c);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
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
    public void testUpdateCompany_F_4() throws Exception {
        try {
            Company c = new Company();
            c.setPasscode("code");
            c.setId(1L);
            c.setDeleted(true);
            manager.updateCompany(c);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
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
    public void testUpdateCompany_F_5() throws Exception {
        try {
            Company c = new Company();
            c.setPasscode("code");
            c.setId(-1L);
            c.setDeleted(false);
            manager.updateCompany(c);
            fail("CompanyEntityNotFoundException is expected.");
        } catch (CompanyEntityNotFoundException e) {
            // ok
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
    public void testUpdateCompany_F_6() throws Exception {
        try {
            Company c = new Company();
            c.setPasscode("code");
            c.setId(1L);
            c.setDeleted(false);
            manager.updateCompany(c);
            fail("CompanyEntityNotFoundException is expected.");
        } catch (CompanyEntityNotFoundException e) {
            // ok
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
    public void testUpdateCompany_F_6_2() throws Exception {
        try {
            Company c = new Company();
            c.setPasscode("code");
            c.setId(-1L);
            c.setDeleted(false);
            manager.updateCompany(c);
            fail("CompanyEntityNotFoundException is expected.");
        } catch (CompanyEntityNotFoundException e) {
            // ok
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
    public void testUpdateCompany_F_6_3() throws Exception {
        try {
            Company c = new Company();
            c.setPasscode("code");
            c.setId(6L);
            c.setDeleted(false);
            manager.updateCompany(c);
            fail("CompanyEntityNotFoundException is expected.");
        } catch (CompanyEntityNotFoundException e) {
            // ok
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
    public void testUpdateCompany_F_7() throws Exception {
        try {
            Company c = new Company();
            c.setPasscode("code");
            c.setName("DAOException");
            c.setId(2L);
            c.setDeleted(false);
            manager.updateCompany(c);
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // ok
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
    public void testUpdateCompany_F_8() throws Exception {
        try {
            Company c = new Company();
            c.setPasscode("code");
            c.setName("DAOConfigurationException");
            c.setId(2L);
            c.setDeleted(false);
            manager.updateCompany(c);
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'updateCompany(Company)'.
     *
     * <p>
     * Inner ClassCastException will be thrown, CompanyManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateCompany_F_9() throws Exception {
        try {
            Company c = new Company();
            c.setPasscode("code");
            c.setName("ClassCastException");
            c.setId(2L);
            c.setDeleted(false);
            manager.updateCompany(c);
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'updateCompany(Company)'.
     *
     * <p>
     * The company should be updated.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateCompany_Acc() throws Exception {
        Company c = new Company();
        c.setPasscode("code");
        c.setName("ok");
        c.setId(2L);
        c.setDeleted(false);
        Company ret = manager.updateCompany(c);
        assertEquals("Equal to 'test'", "test", ret.getCreateUsername());

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
    public void testDeleteCompany_F_1() throws Exception {
        try {
            manager.deleteCompany(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
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
    public void testDeleteCompany_F_2() throws Exception {
        try {
            Company c = new Company();
            c.setDeleted(true);
            manager.deleteCompany(c);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
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
    public void testDeleteCompany_F_3() throws Exception {
        try {
            Company c = new Company();
            c.setName("EntityNotFoundException");
            c.setDeleted(false);
            manager.deleteCompany(c);
            fail("CompanyEntityNotFoundException is expected.");
        } catch (CompanyEntityNotFoundException e) {
            // ok
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
    public void testDeleteCompany_F_4() throws Exception {
        try {
            Company c = new Company();
            c.setName("DAOException");
            c.setDeleted(false);
            manager.deleteCompany(c);
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // ok
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
    public void testDeleteCompany_F_5() throws Exception {
        try {
            Company c = new Company();
            c.setName("DAOConfigurationException");
            c.setDeleted(false);
            manager.deleteCompany(c);
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'deleteCompany(Company)'.
     * <p>
     * The company should be deleted.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteCompany_Accu() throws Exception {
        Company c = new Company();
        c.setName("ok");
        c.setDeleted(false);

        Company ret = manager.deleteCompany(c);

        assertTrue("deleted.", ret.isDeleted());
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
    public void testRetrieveCompany_F_1() throws Exception {
        try {
            manager.retrieveCompany(-1);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
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
    public void testRetrieveCompany_F_2() throws Exception {
        try {
            manager.retrieveCompany(4L);
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // ok
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
    public void testRetrieveCompany_F_3() throws Exception {
        try {
            manager.retrieveCompany(5L);
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'retrieveCompany(long)'.
     * <p>
     * A company should be returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveCompany_Acc() throws Exception {

        Company ret = manager.retrieveCompany(2L);

        assertEquals("Equal to 'code'", "code", ret.getPasscode());
    }

    /**
     * Test method for 'retrieveAllCompanies()'.
     *
     * <p>
     * An empty list will be returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveAllCompanies() throws Exception {
        List<Company> ret = manager.retrieveAllCompanies();

        assertTrue("Empty", ret.isEmpty());
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
    public void testSearchCompaniesByName_F_1() throws Exception {
        try {
            manager.searchCompaniesByName(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
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
    public void testSearchCompaniesByName_F_2() throws Exception {
        try {
            manager.searchCompaniesByName("  ");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
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
    public void testSearchCompaniesByName_F_3() throws Exception {
        try {
            manager.searchCompaniesByName("DAOException");
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // ok
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
    public void testSearchCompaniesByName_F_4() throws Exception {
        try {
            manager.searchCompaniesByName("DAOConfigurationException");
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'searchCompaniesByName(String)'.
     * <p>
     * An empty list will be returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchCompaniesByName_Acc() throws Exception {

        List company = manager.searchCompaniesByName("ok");

        assertTrue("Empty.", company.isEmpty());
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
    public void testSearchCompanies_F_1() throws Exception {
        try {
            manager.searchCompanies(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
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
    public void testSearchCompanies_F_2() throws Exception {
        try {
            EqualToFilter f = new EqualToFilter("DAOException", new Boolean(false));

            manager.searchCompanies(f);
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // ok
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
    public void testSearchCompanies_F_3() throws Exception {
        try {
            EqualToFilter f = new EqualToFilter("DAOConfigurationException", new Boolean(false));

            manager.searchCompanies(f);
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'searchCompanies(Filter)'.
     * <p>
     * An empty list will be returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchCompanies_Acc() throws Exception {
        EqualToFilter f = new EqualToFilter("ok", new Boolean(false));

        List company = manager.searchCompanies(f);

        assertTrue("Empty.", company.isEmpty());
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
    public void testGetClientsForCompany_F_1() throws Exception {
        try {
            manager.getClientsForCompany(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
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
    public void testGetClientsForCompany_F_2() throws Exception {
        try {
            Company c = new Company();
            c.setId(-1);
            manager.getClientsForCompany(c);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
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
    public void testGetClientsForCompany_F_3() throws Exception {
        try {
            Company c = new Company();
            c.setId(1L);
            manager.getClientsForCompany(c);
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // ok
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
    public void testGetClientsForCompany_F_4() throws Exception {
        try {
            Company c = new Company();
            c.setId(2L);
            manager.getClientsForCompany(c);
            fail("CompanyManagerException is expected.");
        } catch (CompanyManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'getClientsForCompany(Company)'.
     * <p>
     * An empty list will be returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetClientsForCompany_Acc() throws Exception {
        Company c = new Company();
        c.setId(3L);
        List<Client> ret = manager.getClientsForCompany(c);

        assertTrue("Empty.", ret.isEmpty());
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
    public void testGetProjectsForCompany_F_1() throws Exception {
        try {
            manager.getProjectsForCompany(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
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
    public void testGetProjectsForCompany_F_2() throws Exception {
        try {
            Company c = new Company();
            c.setId(-1);

            manager.getProjectsForCompany(c);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
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
    public void testGetProjectsForCompany_F_3() throws Exception {
        try {
            Company c = new Company();
            c.setId(1);

            manager.getProjectsForCompany(c);
            fail("CompanyManagerException  is expected.");
        } catch (CompanyManagerException e) {
            // ok
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
    public void testGetProjectsForCompany_F_4() throws Exception {
        try {
            Company c = new Company();
            c.setId(2);

            manager.getProjectsForCompany(c);
            fail("CompanyManagerException  is expected.");
        } catch (CompanyManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'getProjectsForCompany(Company)'.
     *
     * <p>
     * An empty list will be returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetProjectsForCompany_Acc() throws Exception {
        Company c = new Company();
        c.setId(3L);
        List<Project> ret = manager.getProjectsForCompany(c);

        assertTrue("Empty.", ret.isEmpty());

    }

}
