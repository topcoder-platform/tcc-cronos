/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.failuretests;

import com.topcoder.clients.model.Company;
import com.topcoder.clients.webservices.CompanyServiceException;
import com.topcoder.clients.webservices.beans.CompanyServiceBean;
import com.topcoder.clients.webservices.beans.CompanyServiceBeanConfigurationException;
import com.topcoder.clients.webservices.beans.Roles;
import com.topcoder.clients.webservices.failuretests.mock.MockCompanyManager;
import com.topcoder.clients.webservices.failuretests.mock.MockUserMappingRetriever;

import com.topcoder.security.auth.module.UserProfilePrincipal;
import com.topcoder.security.facade.BaseUserProfile;

import junit.framework.TestCase;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.security.Principal;


/**
 * This is a test case for <code>CompanyServiceBean</code>.
 *
 * @author PE
 * @version 1.0
 */
public class CompanyServiceBeanFailureTest extends TestCase {
    /** Represents an instance of CompanyServiceBean. */
    private CompanyServiceBean bean;

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        bean = new CompanyServiceBean();

        FailureTestHelper.setField(bean, "companyManagerFile", "failure/config.properties");
        FailureTestHelper.setField(bean, "companyManagerNamespace", "CompanyServiceBean");

        initialize();

        // Initialize SessionContext
        SessionContextMock context = new SessionContextMock();
        Principal principal = new UserProfilePrincipal(new BaseUserProfile(), 1, "testUser");
        SessionContextMock.setPrincipal(principal);
        SessionContextMock.setRoles(new String[] { Roles.ADMIN, Roles.USER });
        FailureTestHelper.setField(bean, "sessionContext", context);

        // Set default managers behavior
        MockUserMappingRetriever.setFail(false);
        MockCompanyManager.throwCME(false);
        MockCompanyManager.throwIAE(false);
    }

    /**
     * <p>
     * initialize.
     * </p>
     *
     * @throws Exception to JUnit
     */
    private void initialize() throws Exception {
        Method method = CompanyServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
        method.setAccessible(true);
        method.invoke(bean, new Object[0]);
    }

    /**
     * <p>
     * Test for <code>initialize</code> method.
     * </p>
     * 
     * <p>
     * Tests it against the invalid configuration, expects CompanyServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig1() throws Throwable {
        FailureTestHelper.setField(bean, "companyManagerFile", null);

        checkCompanyServiceBeanConfigurationException();
    }

    /**
     * <p>
     * Checks the CompanyServiceBeanConfigurationException for invalid configuration.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    private void checkCompanyServiceBeanConfigurationException()
        throws Throwable {
        try {
            try {
                initialize();
            } catch (InvocationTargetException e) {
                throw e.getTargetException();
            }

            fail("CompanyServiceBeanConfigurationException should be thrown.");
        } catch (CompanyServiceBeanConfigurationException ex) {
            // success
        }
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects CompanyServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig2() throws Throwable {
        FailureTestHelper.setField(bean, "companyManagerFile", " ");

        checkCompanyServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects CompanyServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig3() throws Throwable {
        FailureTestHelper.setField(bean, "companyManagerFile", "invalid");

        checkCompanyServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects CompanyServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig4() throws Throwable {
        FailureTestHelper.setField(bean, "companyManagerNamespace", null);

        checkCompanyServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects CompanyServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig5() throws Throwable {
        FailureTestHelper.setField(bean, "companyManagerNamespace", " ");

        checkCompanyServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects CompanyServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig6() throws Throwable {
        FailureTestHelper.setField(bean, "companyManagerNamespace", "invalid");

        checkCompanyServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects CompanyServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig7() throws Throwable {
        FailureTestHelper.setField(bean, "companyManagerNamespace", "InvalidCompanyServiceBean1");

        checkCompanyServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects CompanyServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig8() throws Throwable {
        FailureTestHelper.setField(bean, "companyManagerNamespace", "InvalidCompanyServiceBean2");

        checkCompanyServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects CompanyServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig9() throws Throwable {
        FailureTestHelper.setField(bean, "companyManagerNamespace", "InvalidCompanyServiceBean3");

        checkCompanyServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects CompanyServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig10() throws Throwable {
        FailureTestHelper.setField(bean, "companyManagerNamespace", "InvalidCompanyServiceBean4");

        checkCompanyServiceBeanConfigurationException();
    }

    /**
     * Test for <code>createCompany</code> method.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testCreateCompany_InvalidArguments1() throws Throwable {
        try {
            bean.createCompany(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>createCompany</code> method.
     * 
     * <p>
     * Tests it against the invalid states, expects IllegalStateException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testCreateCompany_InvalidStates1() throws Throwable {
        FailureTestHelper.setField(bean, "companyManager", null);

        try {
            bean.createCompany(new Company());
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException ex) {
            // success
        }
    }

    /**
     * Test for <code>createCompany</code> method.
     * 
     * <p>
     * Tests it against the errors, expects CompanyServiceException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testCreateCompany_Errors1() throws Throwable {
        MockCompanyManager.throwIAE(true);

        try {
            bean.createCompany(new Company());
            fail("CompanyServiceException should be thrown.");
        } catch (CompanyServiceException ex) {
            // success
        }
    }

    /**
     * Test for <code>createCompany</code> method.
     * 
     * <p>
     * Tests it against the errors, expects CompanyServiceException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testCreateCompany_Errors2() throws Throwable {
        MockCompanyManager.throwCME(true);

        try {
            bean.createCompany(new Company());
            fail("CompanyServiceException should be thrown.");
        } catch (CompanyServiceException ex) {
            // success
        }
    }

    /**
     * Test for <code>updateCompany</code> method.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testUpdateCompany_InvalidArguments1() throws Throwable {
        try {
            bean.updateCompany(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>updateCompany</code> method.
     * 
     * <p>
     * Tests it against the invalid states, expects IllegalStateException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testUpdateCompany_InvalidStates1() throws Throwable {
        FailureTestHelper.setField(bean, "companyManager", null);

        try {
            bean.updateCompany(new Company());
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException ex) {
            // success
        }
    }

    /**
     * Test for <code>updateCompany</code> method.
     * 
     * <p>
     * Tests it against the errors, expects CompanyServiceException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testUpdateCompany_Errors1() throws Throwable {
        MockCompanyManager.throwIAE(true);

        try {
            bean.updateCompany(new Company());
            fail("CompanyServiceException should be thrown.");
        } catch (CompanyServiceException ex) {
            // success
        }
    }

    /**
     * Test for <code>updateCompany</code> method.
     * 
     * <p>
     * Tests it against the errors, expects CompanyServiceException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testUpdateCompany_Errors2() throws Throwable {
        MockCompanyManager.throwCME(true);

        try {
            bean.updateCompany(new Company());
            fail("CompanyServiceException should be thrown.");
        } catch (CompanyServiceException ex) {
            // success
        }
    }

    /**
     * Test for <code>deleteCompany</code> method.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testDeleteCompany_InvalidArguments1() throws Throwable {
        try {
            bean.deleteCompany(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>deleteCompany</code> method.
     * 
     * <p>
     * Tests it against the invalid states, expects IllegalStateException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testDeleteCompany_InvalidStates1() throws Throwable {
        FailureTestHelper.setField(bean, "companyManager", null);

        try {
            bean.deleteCompany(new Company());
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException ex) {
            // success
        }
    }

    /**
     * Test for <code>deleteCompany</code> method.
     * 
     * <p>
     * Tests it against the errors, expects CompanyServiceException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testDeleteCompany_Errors1() throws Throwable {
        MockCompanyManager.throwIAE(true);

        try {
            bean.deleteCompany(new Company());
            fail("CompanyServiceException should be thrown.");
        } catch (CompanyServiceException ex) {
            // success
        }
    }

    /**
     * Test for <code>deleteCompany</code> method.
     * 
     * <p>
     * Tests it against the errors, expects CompanyServiceException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testDeleteCompany_Errors2() throws Throwable {
        MockCompanyManager.throwCME(true);

        try {
            bean.deleteCompany(new Company());
            fail("CompanyServiceException should be thrown.");
        } catch (CompanyServiceException ex) {
            // success
        }
    }
}
