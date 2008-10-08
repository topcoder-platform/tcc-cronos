/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

import java.io.File;
import java.security.Principal;
import java.util.ArrayList;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.webservices.accuracytests.AccuracyTestHelper;
import com.topcoder.clients.webservices.accuracytests.mock.MockPrincipal;
import com.topcoder.clients.webservices.accuracytests.mock.MockSessionContext;
import com.topcoder.clients.webservices.accuracytests.mock.MockUserMappingRetriever;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

import junit.framework.TestCase;

/**
 * This class contains unit tests for <code>CompanyServiceBean</code> class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestCompanyServiceBean extends TestCase {
    /**
     * <p>
     * Represents the instance of CompanyServiceBean used for test.
     * </p>
     */
    private CompanyServiceBean bean = null;

    /**
     * <p>
     * Represents the instance of Company used for test.
     * </p>
     */
    private Company company;

    /**
     * Set Up the test environment before testing.
     * 
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        bean = new CompanyServiceBean();

        AccuracyTestHelper.setField(bean, "companyManagerFile", new File(
            "test_files/accuracy/configuration.properties").getAbsolutePath());
        AccuracyTestHelper.setField(bean, "companyManagerNamespace", "CompanyServiceBean");

        MockSessionContext context = new MockSessionContext();
        Principal principal = new MockPrincipal();
        MockSessionContext.setPrincipal(principal);
        AccuracyTestHelper.setField(bean, "sessionContext", context);
        MockSessionContext.setRoles(new String[] { Roles.ADMIN, Roles.USER });

        bean.setVerboseLogging(true);
        
        bean.initialize();

        company = new Company();
        company.setName("name");
    }

    /**
     * Clean up the test environment after testing.
     * 
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        bean = null;
        MockSessionContext.setRoles(null);
        MockSessionContext.setPrincipal(null);
        MockUserMappingRetriever.setClients(new ArrayList<Client>());
        MockUserMappingRetriever.setProjects(new ArrayList<Project>());
        MockUserMappingRetriever.setFail(false);
    }

    /**
     * Function test : Tests <code>CompanyServiceBean()</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testCompanyServiceBeanAccuracy() throws Exception {

        assertNotNull("Should not be null.", bean);
    }

    /**
     * Function test : Tests <code>createCompany(Company company)</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testCreateCompanyAccuracy() throws Exception {
        Company result = bean.createCompany(company);

        assertNotNull("Should not be null.", result);
        assertEquals("Should be AAAAA.", "AAAAA", result.getName());
    }

    /**
     * Function test : Tests <code>updateCompany(Company company)</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testUpdateCompanyAccuracy() throws Exception {
        Company result = bean.updateCompany(company);
        assertNotNull("Should not be null.", result);
        assertEquals("Should be CCCCC.", "CCCCC", result.getName());
    }

    /**
     * Function test : Tests <code>deleteCompany(Company company)</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testDeleteCompanyAccuracy() throws Exception {
        Company result = bean.deleteCompany(company);

        assertNotNull("Should not be null.", result);
        assertEquals("Should be BBBBB.", "BBBBB", result.getName());
    }

    /**
     * Function test : Tests <code>isVerboseLogging()</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testIsVerboseLoggingAccuracy() throws Exception {
        assertTrue("should be true", bean.isVerboseLogging());
    }

    /**
     * Function test : Tests <code>setVerboseLogging(boolean verboseLogging)</code> method for
     * accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testSetVerboseLoggingAccuracy() throws Exception {
        bean.setVerboseLogging(false);
        assertFalse("should be false", bean.isVerboseLogging());
    }

    /**
     * Function test : Tests <code>getLog()</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testGetLogAccuracy() throws Exception {
        assertNotNull("shouldn't be null.", bean.getLog());
    }

    /**
     * Function test : Tests <code>setLog(Log log)</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testSetLogAccuracy() throws Exception {
        Log log = LogManager.getLog("name");
        bean.setLog(log);
        assertEquals("should be log.", log, bean.getLog());
    }

}