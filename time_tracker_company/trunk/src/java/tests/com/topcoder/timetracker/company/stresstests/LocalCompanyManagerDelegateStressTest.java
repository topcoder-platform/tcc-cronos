/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.stresstests;

import java.sql.Connection;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.TestCase;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.company.BatchCompanyDAOException;
import com.topcoder.timetracker.company.Company;
import com.topcoder.timetracker.company.CompanyDAOException;
import com.topcoder.timetracker.company.ejb.CompanyBean;
import com.topcoder.timetracker.company.ejb.CompanyHomeLocal;
import com.topcoder.timetracker.company.ejb.CompanyLocal;
import com.topcoder.timetracker.company.ejb.LocalCompanyManagerDelegate;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Stress Test for <code>LocalCompanyManagerDelegate</code>.
 * </p>
 *
 * @author Achilles_2005
 * @version 3.2
 */
public class LocalCompanyManagerDelegateStressTest extends TestCase {

    /**
     * <p>
     * The LocalCompanyManagerDelegate instance for test.
     * </p>
     */
    private static final String NAMESPACE =
        "com.topcoder.timetracker.company.ejb.LocalCompanyManagerDelegate";

    /**
     * <p>
     * The times to run the cases.
     * </p>
     */
    private static final int TIMES = 100;

    /**
     * <p>
     * The creator for the Company instance.
     * </p>
     */
    private String creator = "achilles";

    /**
     * <p>
     * The modifier for the Company instance.
     * </p>
     */
    private String modifier = "accelerate";

    /**
     * <p>
     * An String representing the company name.
     * </p>
     */
    private String companyName = "TopCoder";

    /**
     * <p>
     * The pass code of the company.
     * </p>
     */
    private String passCode = "passcode";

    /**
     * <p>
     * The Company instance for DbCompanyDAO.
     * </p>
     */
    private Company company;

    /**
     * <p>
     * The Company instances for DbCompanyDAO.
     * </p>
     */
    private Company[] companies;

    /**
     * <p>
     * The LocalCompanyManagerDelegate instance for test.
     * </p>
     */
    private LocalCompanyManagerDelegate localCompanyManagerDelegate;

    /**
     * <p>
     * Set up the environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        loadNamespaces();
        company = new Company();
        company.setAddress(new Address());
        company.setContact(new Contact());
        company.setCompanyName(companyName);

        Map env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, MockContextFactory.class.getName());

        MockContextFactory.setAsInitial();
        Context context = new InitialContext();
        context.rebind("java:comp/env/SpecificationNamespace", "com.topcoder.timetracker.company");
        context.rebind("java:comp/env/CompanyDAOKey", "DbcompanyDao");

        MockContainer mockContainer = new MockContainer(context);

        SessionBeanDescriptor sessionBeanDescriptor =
            new SessionBeanDescriptor("ejb/companybean", CompanyHomeLocal.class, CompanyLocal.class,
                CompanyBean.class);
        sessionBeanDescriptor.setStateful(false);

        mockContainer.deploy(sessionBeanDescriptor);
        localCompanyManagerDelegate = new LocalCompanyManagerDelegate(NAMESPACE);
    }

    /**
     * <p>
     * Clean the environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        cleanDatabase();
        clearNamespaces();
        MockContextFactory.revertSetAsInitial();
    }

    /**
     * <p>
     * Clean the database.
     * </p>
     *
     * @throws Exception to JUnit
     */
    private void cleanDatabase() throws Exception {
        Connection connection =
            new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("delete from company");
        statement.close();
        connection.close();
    }

    /**
     * <p>
     * Load the namespaces from the config files.
     * </p>
     *
     * @throws Exception unexpected exception.
     */
    private void loadNamespaces() throws Exception {
        clearNamespaces();

        ConfigManager configManager = ConfigManager.getInstance();
        configManager.add("stresstests/config.xml");
    }

    /**
     * <p>
     * Remove the namespaces.
     * </p>
     *
     * @throws Exception unexpected exception.
     */
    private static void clearNamespaces() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        for (Iterator iter = configManager.getAllNamespaces(); iter.hasNext();) {
            configManager.removeNamespace((String) iter.next());
        }
    }

    /**
     * <p>
     * Stress test for <code>createCompany(Company, String, boolean)</code>.
     * </p>
     *
     * @throws CompanyDAOException to JUnit
     *
     */
    public void testCreateCompany() throws CompanyDAOException {
        for (int i = 0; i < TIMES; i++) {
            company.setId(0);
            company.setPassCode(passCode + i);
            localCompanyManagerDelegate.createCompany(company, creator, true);
            assertCompanyEqual(company, localCompanyManagerDelegate.retrieveCompany(company.getId()));
        }
    }

    /**
     * <p>
     * Compared the two company instances.
     * </p>
     *
     * @param company1 the company to be compared
     * @param company2 the company to be compared
     * @return true if the two instances are considered to be equal
     */
    private void assertCompanyEqual(Company company1, Company company2) {
        assertEquals("The companyName is not correct.", company1.getCompanyName(), company2.getCompanyName());
        assertEquals("The companyId is not correct.", company1.getId(), company2.getId());
        assertEquals("The CreationUser is not correct.", company1.getCreationUser(), company2
            .getCreationUser());
        assertEquals("The ModificationUser is not correct.", company1.getModificationUser(), company2
            .getModificationUser());
    }

    /**
     * <p>
     * Stress test for <code>retrieveCompany(long id)</code>.
     * </p>
     *
     * @throws CompanyDAOException to JUnit
     */
    public void testRetrieveCompany() throws CompanyDAOException {
        for (int i = 0; i < TIMES; i++) {
            company.setId(0);
            company.setPassCode(passCode + i);
            localCompanyManagerDelegate.createCompany(company, creator, true);
            assertCompanyEqual(company, localCompanyManagerDelegate.retrieveCompany(company.getId()));
        }
    }

    /**
     * <p>
     * Stress test for <code>updateCompany(Company, String, boolean)</code>.
     * </p>
     *
     * @throws CompanyDAOException to JUnit
     */
    public void testUpdateCompany() throws Exception {
        for (int i = 0; i < TIMES; i++) {
            company.setId(0);
            company.setPassCode(passCode + i);
            localCompanyManagerDelegate.createCompany(company, creator, true);
            company.setPassCode("newPassCode" + i);
            company.setCompanyName("microsoft");
            localCompanyManagerDelegate.updateCompany(company, modifier, true);
            assertCompanyEqual(company, localCompanyManagerDelegate.retrieveCompany(company.getId()));
        }
    }

    /**
     * <p>
     * Stress test for <code>deleteCompany(Company, boolean, String)</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteCompany() throws Exception {
        for (int i = 0; i < TIMES; i++) {
            company.setId(0);
            company.setPassCode(passCode + i);
            localCompanyManagerDelegate.createCompany(company, creator, true);
            localCompanyManagerDelegate.deleteCompany(company, true, modifier);
            assertNull("The company is not successfully deleted.", localCompanyManagerDelegate
                .retrieveCompany(company.getId()));
        }
    }

    /**
     * <p>
     * Stress test for <code>listCompanies()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testListCompanies() throws Exception {
        Set set = new HashSet();
        for (int i = 1; i <= TIMES; i++) {
            Company compani = new Company();
            compani.setAddress(new Address());
            compani.setContact(new Contact());
            compani.setCompanyName(companyName);
            compani.setPassCode(passCode + i);
            compani.setId(0);
            localCompanyManagerDelegate.createCompany(compani, creator, true);
            set.add(compani);
        }
        for (int i = 0; i < 10; i++) {
            companies = localCompanyManagerDelegate.listCompanies();
            assertEquals("The listCompanies() does not work properly.", TIMES, companies.length);
            for (int j = 0; j < TIMES; j++) {
                assertTrue("The listCompanies() failed to retrieve some records.", set.contains(companies[j]));
            }
        }
    }

    /**
     * <p>
     * Stress test for <code>createCompanies(Company[], String, boolean, boolean)</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateCompanies() throws Exception {
        companies = new Company[TIMES];

        for (int i = 0; i < TIMES; i++) {
            companies[i] = new Company();
            companies[i].setAddress(new Address());
            companies[i].setContact(new Contact());
            companies[i].setCompanyName(companyName);
        }
        for (int i = 1; i <= 10; i++) {
            for (int j = 0; j < TIMES; j++) {
                companies[j].setId(i * TIMES + j + 1);
                companies[j].setPassCode(passCode + companies[j].getId());
            }

            // AtomicBatchMode and non-atomicBatchMode will be executed
            try {
                localCompanyManagerDelegate.createCompanies(companies, creator, i % 2 == 0, true);
            } catch (BatchCompanyDAOException e) {
                break;
            }
        }
        assertEquals("The create batch method failed.", 10 * TIMES, localCompanyManagerDelegate
            .listCompanies().length);
    }

    /**
     * <p>
     * Stress test for <code>.retrieveCompanies(long[])</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveCompanies() throws Exception {
        Set set = new HashSet();
        long even[] = new long[TIMES / 2];
        long odd[] = new long[TIMES / 2];
        for (int i = 1; i <= TIMES; i++) {
            if (i % 2 == 0) {
                even[i / 2 - 1] = i;
            } else {
                odd[i / 2] = i;
            }
            Company compani = new Company();
            compani.setAddress(new Address());
            compani.setContact(new Contact());
            compani.setCompanyName(companyName);
            compani.setPassCode(passCode + i);
            compani.setId(i);
            localCompanyManagerDelegate.createCompany(compani, creator, true);
            set.add(compani);
        }
        for (int i = 0; i < 10; i++) {
            companies = localCompanyManagerDelegate.retrieveCompanies(even);
            assertEquals("The listCompanies() does not work properly.", TIMES / 2, companies.length);
            for (int j = 0; j < TIMES / 2; j++) {
                assertTrue("The listCompanies() failed to retrieve some records.", set.contains(companies[j]));
            }
        }
        for (int i = 0; i < 10; i++) {
            Company[] companies = localCompanyManagerDelegate.retrieveCompanies(odd);
            assertEquals("The listCompanies() does not work properly.", TIMES / 2, companies.length);
            for (int j = 0; j < TIMES / 2; j++) {
                assertTrue("The listCompanies() failed to retrieve some records.", set.contains(companies[j]));
            }
        }
    }

    /**
     * <p>
     * Stress test for <code>updateCompanies(Company[], String, boolean, boolean)</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateCompanies() throws Exception {
        companies = new Company[TIMES];

        for (int i = 0; i < TIMES; i++) {
            companies[i] = new Company();
            companies[i].setAddress(new Address());
            companies[i].setContact(new Contact());
            companies[i].setCompanyName(companyName);
            companies[i].setPassCode(passCode + i);
        }
        localCompanyManagerDelegate.createCompanies(companies, creator, false, false);

        for (int i = 1; i <= 10; i++) {
            Set set = new HashSet();
            for (int j = 0; j < TIMES; j++) {
                companies[j].setPassCode(String.valueOf(i * TIMES + j));
                set.add(companies[j].getPassCode());
            }

            // AtomicBatchMode and non-atomicBatchMode will be executed
            localCompanyManagerDelegate.updateCompanies(companies, modifier, i % 2 == 0, false);
            Company[] companiez = localCompanyManagerDelegate.listCompanies();
            assertEquals("The company is not updated properly.", TIMES, companiez.length);
            for (int j = 0; j < companiez.length; j++) {
                assertTrue("The companies is not properly updated.", set.contains(companiez[j].getPassCode()));
            }
        }
    }

    /**
     * <p>
     * Stress test for <code>deleteCompanies(Company[], boolean, boolean, String)</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteCompanies() throws Exception {
        companies = new Company[TIMES];

        for (int i = 0; i < TIMES; i++) {
            companies[i] = new Company();
            companies[i].setAddress(new Address());
            companies[i].setContact(new Contact());
            companies[i].setCompanyName(companyName);
        }
        for (int i = 1; i <= 10; i++) {
            for (int j = 0; j < TIMES; j++) {
                companies[j].setId(i * TIMES + j + 1);
                companies[j].setPassCode(passCode + companies[j].getId());
            }
            localCompanyManagerDelegate.createCompanies(companies, creator, true, true);
        }
        for (int i = 1; i <= 10; i++) {
            for (int j = 0; j < TIMES; j++) {
                companies[j].setId(i * TIMES + j + 1);
            }
            // AtomicBatchMode and non-atomicBatchMode will be executed
            localCompanyManagerDelegate.deleteCompanies(companies, i % 2 == 0, true, modifier);
            assertEquals("The delete batch method failed.", (10 - i) * TIMES, localCompanyManagerDelegate
                .listCompanies().length);
        }
    }

}
