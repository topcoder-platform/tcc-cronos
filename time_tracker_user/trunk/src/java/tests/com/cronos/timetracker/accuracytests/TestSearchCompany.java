/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.accuracytests;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.cronos.timetracker.common.Address;
import com.cronos.timetracker.common.Contact;
import com.cronos.timetracker.common.EncryptionRepository;
import com.cronos.timetracker.common.State;
import com.cronos.timetracker.company.Company;
import com.cronos.timetracker.company.CompanySearchBuilder;
import com.cronos.timetracker.company.DbCompanyDAO;
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
public class TestSearchCompany extends TestCase {

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
     * Test method searchCompanies.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchCompanies_1() throws Exception {
        Company company = getCompany(1);

        test.createCompany(company, "user");

        // create the builder
        CompanySearchBuilder builder = new CompanySearchBuilder();

        Date start = new Date(System.currentTimeMillis() - 100000000);
        Date end = new Date(System.currentTimeMillis() + 1000000000);

        builder.modifiedWithinDateRange(start, end);

        Filter filter = builder.buildFilter();

        Company[] ret = test.searchCompanies(filter);

        assertEquals("Equal is expected.", 1, ret.length);

    }

    /**
     * Test method searchCompanies.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchCompanies_2() throws Exception {
        Company company = getCompany(1);

        test.createCompany(company, "user");

        // create the builder
        CompanySearchBuilder builder = new CompanySearchBuilder();

        builder.hasCompanyName("topcoder1");

        Filter filter = builder.buildFilter();

        Company[] ret = test.searchCompanies(filter);

        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method searchCompanies.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchCompanies_3() throws Exception {
        Company company = getCompany(1);

        test.createCompany(company, "user");

        // create the builder
        CompanySearchBuilder builder = new CompanySearchBuilder();

        builder.hasCompanyName("tc");

        Filter filter = builder.buildFilter();

        Company[] ret = test.searchCompanies(filter);

        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Test method searchCompanies.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchCompanies_4() throws Exception {
        Company company = getCompany(1);

        test.createCompany(company, "user");

        // create the builder
        CompanySearchBuilder builder = new CompanySearchBuilder();

        builder.createdByUser("user");

        Filter filter = builder.buildFilter();

        Company[] ret = test.searchCompanies(filter);

        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method searchCompanies.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchCompanies_5() throws Exception {
        Company company = getCompany(1);

        test.createCompany(company, "user");

        // create the builder
        CompanySearchBuilder builder = new CompanySearchBuilder();

        builder.createdByUser("topcoder");

        Filter filter = builder.buildFilter();

        Company[] ret = test.searchCompanies(filter);

        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Test method searchCompanies.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchCompanies_6() throws Exception {
        Company company = getCompany(1);

        test.createCompany(company, "user");

        // create the builder
        CompanySearchBuilder builder = new CompanySearchBuilder();

        builder.hasCity("city");

        Filter filter = builder.buildFilter();

        Company[] ret = test.searchCompanies(filter);

        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method searchCompanies.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchCompanies_7() throws Exception {
        Company company = getCompany(1);

        test.createCompany(company, "user");

        // create the builder
        CompanySearchBuilder builder = new CompanySearchBuilder();

        builder.hasCity("topcoder");

        Filter filter = builder.buildFilter();

        Company[] ret = test.searchCompanies(filter);

        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Test method searchCompanies.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchCompanies_8() throws Exception {
        Company company = getCompany(1);

        test.createCompany(company, "user");

        // create the builder
        CompanySearchBuilder builder = new CompanySearchBuilder();

        builder.hasContactEmail("myEmail@address");

        Filter filter = builder.buildFilter();

        Company[] ret = test.searchCompanies(filter);

        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Test method searchCompanies.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchCompanies_9() throws Exception {
        Company company = getCompany(1);

        test.createCompany(company, "user");

        // create the builder
        CompanySearchBuilder builder = new CompanySearchBuilder();

        builder.hasContactEmail("ivern@topcoder.com");

        Filter filter = builder.buildFilter();

        Company[] ret = test.searchCompanies(filter);

        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method searchCompanies.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchCompanies_10() throws Exception {
        Company company = getCompany(1);

        test.createCompany(company, "user");

        // create the builder
        CompanySearchBuilder builder = new CompanySearchBuilder();

        builder.hasContactFirstName("john");

        Filter filter = builder.buildFilter();

        Company[] ret = test.searchCompanies(filter);

        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method searchCompanies.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchCompanies_11() throws Exception {
        Company company = getCompany(1);

        test.createCompany(company, "user");

        // create the builder
        CompanySearchBuilder builder = new CompanySearchBuilder();

        builder.hasContactLastName("smith");

        Filter filter = builder.buildFilter();

        Company[] ret = test.searchCompanies(filter);

        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method searchCompanies.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchCompanies_12() throws Exception {
        Company company = getCompany(1);

        test.createCompany(company, "user");

        // create the builder
        CompanySearchBuilder builder = new CompanySearchBuilder();

        builder.hasZipCode("510275");

        Filter filter = builder.buildFilter();

        Company[] ret = test.searchCompanies(filter);

        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method searchCompanies.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchCompanies_13() throws Exception {
        Company company = getCompany(1);

        test.createCompany(company, "user");

        // create the builder
        CompanySearchBuilder builder = new CompanySearchBuilder();

        builder.hasZipCode("not exist");

        Filter filter = builder.buildFilter();

        Company[] ret = test.searchCompanies(filter);

        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Test method searchCompanies.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchCompanies_14() throws Exception {
        Company company = getCompany(1);

        test.createCompany(company, "user");

        // create the builder
        CompanySearchBuilder builder = new CompanySearchBuilder();

        builder.hasContactPhoneNumber("011-512");

        Filter filter = builder.buildFilter();

        Company[] ret = test.searchCompanies(filter);

        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method searchCompanies.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchCompanies_15() throws Exception {
        Company company = getCompany(1);

        test.createCompany(company, "user");

        // create the builder
        CompanySearchBuilder builder = new CompanySearchBuilder();

        builder.hasContactPhoneNumber("not exist");

        Filter filter = builder.buildFilter();

        Company[] ret = test.searchCompanies(filter);

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
