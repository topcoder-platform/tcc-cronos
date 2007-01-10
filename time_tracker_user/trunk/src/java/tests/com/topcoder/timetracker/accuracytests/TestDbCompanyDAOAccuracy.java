/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.accuracytests;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.topcoder.timetracker.common.Address;
import com.topcoder.timetracker.common.Contact;
import com.topcoder.timetracker.common.EncryptionRepository;
import com.topcoder.timetracker.common.State;
import com.topcoder.timetracker.company.Company;
import com.topcoder.timetracker.company.CompanySearchBuilder;
import com.topcoder.timetracker.company.DbCompanyDAO;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Accuracy test for class <code>DbCompanyDAO</code>.
 *
 * @author Chenhong
 * @version 2.0
 */
public class TestDbCompanyDAOAccuracy extends TestCase {

    /**
     * Represents the DbCompanyDAO instance for test.
     */
    private DbCompanyDAO test = null;

    /**
     * Represents the connectName.
     */
    private static final String connectName = "sysuser";

    /**
     * Represents the id name.
     */
    private static final String idName = "sysuser";

    /**
     * The algorithm name.
     */
    private static final String alg = "simple";

    /**
     * This HashSet is help to create unique passcode.
     */
    private Set set = new HashSet();

    /**
     * Set up the enviroment. Create DbCompanyDAO instance.
     *
     * @throws Exception
     *             to junit.
     */
    public void setUp() throws Exception {
        Util.clearNamespace();

        ConfigManager cm = ConfigManager.getInstance();
        cm.add("accuracytests/DBConnectionFactory.xml");
        cm.add("accuracytests/Logging.xml");

        String namespace = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";
        DBConnectionFactory factory = new DBConnectionFactoryImpl(namespace);

        EncryptionRepository.getInstance().registerAlgorithm("simple", new SimpleAlgorithm());

        Util.insertRecordsIntoState_NameTable(100);

        test = new DbCompanyDAO(factory, connectName, alg, idName);
    }

    /**
     * Tear down the enviroment.
     *
     * @throws Exception
     *             to junit.
     */
    public void tearDown() throws Exception {

        Util.clearTables();

        Util.clearNamespace();

        test = null;
    }

    /**
     * Test constructor.
     *
     */
    public void testDbCompanyDAO() {
        assertNotNull("Should not be null.", test);
    }

    /**
     * Test method <code> Company createCompany(Company company, String user) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testCreateCompany() throws Exception {
        Company c = getCompany(1);

        Company ret = test.createCompany(c, "user");

        assertEquals("Equal is expected.", c, ret);
    }

    /**
     * Test method <code>Company retrieveCompany(long id) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testRetrieveCompany() throws Exception {
        Company c = getCompany(1);

        test.createCompany(c, "user");

        Company ret = test.retrieveCompany(c.getId());

        assertEquals("Equal is expected.", c, ret);
    }

    /**
     * Test method <code>void updateCompany(Company company, String user) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateCompany() throws Exception {
        Company c = getCompany(1);

        test.createCompany(c, "user");

        c.setCompanyName("tc");

        // update company.
        test.updateCompany(c, "user");

        Company ret = test.retrieveCompany(c.getId());

        assertEquals("Equal is expected.", "tc", ret.getCompanyName());
    }

    /**
     * Test method <code>void deleteCompany(Company company) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testDeleteCompany_1() throws Exception {
        Company c = getCompany(1);

        test.createCompany(c, "user");

        Company ret = test.retrieveCompany(c.getId());

        assertNotNull("Should not be null.", ret);

        // delete this company.

        test.deleteCompany(c);

        ret = test.retrieveCompany(c.getId());

        assertNull("Now it has been deleted.", ret);
    }

    /**
     * Test method <code>Company[] listCompanies() </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testListCompanies_1() throws Exception {
        Company[] c = test.listCompanies();
        assertEquals("Equal  is expected.", 0, c.length);
    }

    /**
     * Test method <code>Company[] listCompanies() </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testListCompanies_2() throws Exception {
        Company company = getCompany(1);

        test.createCompany(company, "user");

        Company[] c = test.listCompanies();

        assertEquals("Equal  is expected.", 1, c.length);
    }

    /**
     * Test method <code>Company[] searchCompanies(Filter filter) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchCompanies_1() throws Exception {
        Company company = getCompany(1);

        test.createCompany(company, "user");

        // create the builder
        CompanySearchBuilder builder = new CompanySearchBuilder();

        Date start = new Date(System.currentTimeMillis() - 1000000000);
        Date end = new Date(System.currentTimeMillis() + 1000000000);

        builder.createdWithinDateRange(start, end);
        builder.hasCompanyName("topcoder");

        Filter filter = builder.buildFilter();

        Company[] ret = test.searchCompanies(filter);

        // one company should be found.
        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method <code>Company[] searchCompanies(Filter filter) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchCompanies_2() throws Exception {
        Company company = getCompany(1);

        test.createCompany(company, "user");

        // create the builder
        CompanySearchBuilder builder = new CompanySearchBuilder();

        Date start = new Date(System.currentTimeMillis() - 1000000000);
        Date end = new Date(System.currentTimeMillis() - 1000000);

        builder.createdWithinDateRange(start, end);
        builder.hasCompanyName("topcoder");

        Filter filter = builder.buildFilter();

        Company[] ret = test.searchCompanies(filter);

        // no company should be found.
        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Test method <code>Company[] searchCompanies(Filter filter) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchCompanies_3() throws Exception {
        Company company = getCompany(1);

        test.createCompany(company, "user");

        // create the builder
        CompanySearchBuilder builder = new CompanySearchBuilder();

        Date start = new Date(System.currentTimeMillis() + 1000000);
        Date end = new Date(System.currentTimeMillis() + 1000000000);

        builder.createdWithinDateRange(start, end);
        builder.hasCompanyName("topcoder");

        Filter filter = builder.buildFilter();

        Company[] ret = test.searchCompanies(filter);

        // no company should be found.
        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Test method <code>Company[] createCompanies(Company[] companies, String user, boolean atomicBatchMode) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testCreateCompanies_1() throws Exception {
        Company[] c = new Company[1];
        c[0] = getCompany(1);

        test.createCompanies(c, "user", true);

        Company[] ret = test.listCompanies();
        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method <code>Company[] createCompanies(Company[] companies, String user, boolean atomicBatchMode) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testCreateCompanies_2() throws Exception {
        Company[] c = new Company[2];
        c[0] = getCompany(1);
        c[1] = getCompany(2);

        test.createCompanies(c, "user", false);

        Company[] ret = test.listCompanies();
        assertEquals("Equal is expected.", 2, ret.length);
    }

    /**
     * Test method <code>Company[] createCompanies(Company[] companies, String user, boolean atomicBatchMode) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testCreateCompanies_3() throws Exception {
        Company[] c = new Company[2];
        c[0] = getCompany(1);
        c[1] = getCompany(2);

        test.createCompanies(c, "user", true);

        Company[] ret = test.listCompanies();
        assertEquals("Equal is expected.", 2, ret.length);
    }

    /**
     * Test method <code>Company[] retrieveCompanies(long[] ids) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testRetrieveCompanies_1() throws Exception {
        Company[] c = new Company[1];
        c[0] = getCompany(1);

        test.createCompanies(c, "user", false);

        long[] ids = new long[1];
        ids[0] = c[0].getId();

        Company[] ret = test.retrieveCompanies(ids);

        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method <code>Company[] retrieveCompanies(long[] ids) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testRetrieveCompanies_2() throws Exception {
        Company[] c = new Company[2];
        c[0] = getCompany(1);
        c[1] = getCompany(2);

        test.createCompanies(c, "user", true);

        long[] ids = new long[2];
        ids[0] = c[0].getId();
        ids[1] = c[1].getId();

        Company[] ret = test.retrieveCompanies(ids);

        assertEquals("Equal is expected.", 2, ret.length);
    }

    /**
     * Test method <code>void updateCompanies(Company[] companies, String user, boolean atomicBatchMode) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateCompanies() throws Exception {
        Company[] c = new Company[1];
        c[0] = getCompany(1);

        // create companies.
        test.createCompanies(c, "user", false);

        c[0].setCompanyName("topcoder_1");

        // update
        test.updateCompanies(c, "user", false);

        long[] ids = new long[1];
        ids[0] = c[0].getId();

        Company[] ret = test.retrieveCompanies(ids);

        assertEquals("Equal is expected.", 1, ret.length);

        assertEquals("Equal is expected.", "topcoder_1", ret[0].getCompanyName());
    }

    /**
     * Test method <code>void updateCompanies(Company[] companies, String user, boolean atomicBatchMode) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateCompanies_2() throws Exception {
        Company[] c = new Company[2];
        c[0] = getCompany(1);
        c[1] = getCompany(100);

        // create companies.
        test.createCompanies(c, "user", false);

        c[0].setCompanyName("topcoder_1");
        c[1].setCompanyName("topcoder_100");

        // update
        test.updateCompanies(c, "user", true);

        long[] ids = new long[2];
        ids[0] = c[0].getId();
        ids[1] = c[1].getId();

        Company[] ret = test.retrieveCompanies(ids);

        assertEquals("Equal is expected.", 2, ret.length);

        assertEquals("Equal is expected.", "topcoder_1", ret[0].getCompanyName());
        assertEquals("Equal is expected.", "topcoder_100", ret[1].getCompanyName());
    }

    /**
     * Test method <code>void deleteCompanies(Company[] companies, boolean atomicBatchMode) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testDeleteCompanies() throws Exception {
        Company[] c = new Company[1];
        c[0] = getCompany(1);

        // create companies.
        test.createCompanies(c, "user", false);

        long[] ids = new long[1];
        ids[0] = c[0].getId();

        Company[] ret = test.retrieveCompanies(ids);

        assertEquals("Equal is expected.", 1, ret.length);

        // delete;
        test.deleteCompanies(c, false);
        ret = test.listCompanies();

        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Test method <code>void deleteCompanies(Company[] companies, boolean atomicBatchMode) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testDeleteCompanies_2() throws Exception {
        Company[] c = new Company[3];
        c[0] = getCompany(1);
        c[1] = getCompany(2);
        c[2] = getCompany(3);

        // create companies.
        test.createCompanies(c, "user", true);

        long[] ids = new long[3];
        ids[0] = c[0].getId();
        ids[1] = c[1].getId();
        ids[2] = c[2].getId();

        Company[] ret = test.retrieveCompanies(ids);

        assertEquals("Equal is expected.", 3, ret.length);

        // delete;
        test.deleteCompanies(c, true);
        ret = test.listCompanies();

        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Create a Company instance for test.
     *
     * @param id
     *            the company id.
     * @return Company instance.
     */
    private Company getCompany(int id) {
        Company company = new Company();

        // create Address instance.
        Address address = new Address();
        address.setId(id);
        address.setLine1("line1");
        address.setLine2("line2");
        address.setCity("city");
        address.setCreationDate(new Date(System.currentTimeMillis() - 100));
        address.setCreationUser("user");
        address.setZipCode("510275");

        // create State instance and set it to Address instance.
        State state = new State();
        state.setId(100);
        state.setAbbreviation("new york");
        address.setState(state);

        company.setAddress(address);

        // create Contact instance and set to Company
        Contact contact = new Contact();
        contact.setFirstName("john");
        contact.setLastName("smith");
        contact.setEmailAddress("ivern@topcoder.com");
        contact.setPhoneNumber("011-512");

        company.setContact(contact);

        company.setCompanyName("topcoder" + id);

        company.setCreationUser("reviewer" + id);
        company.setAlgorithmName("simple");

        StringBuffer sb = new StringBuffer();
        sb.append(id);
        Random rand = new Random();
        int n = rand.nextInt(1000);

        while (set.contains(new Integer(n))) {
            n = rand.nextInt(1000);
        }
        set.add(new Integer(n));
        sb.append(n);

        company.setPasscode(sb.toString());

        return company;
    }
}
