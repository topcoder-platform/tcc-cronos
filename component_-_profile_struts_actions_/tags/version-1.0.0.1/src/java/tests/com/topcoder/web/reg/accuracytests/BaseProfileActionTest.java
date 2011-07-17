/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.util.log.Log;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.reg.actions.profile.BaseProfileAction;
import com.topcoder.web.reg.actions.profile.ProfileCompletenessReport;
import com.topcoder.web.reg.actions.profile.ProfileCompletenessRetriever;
import com.topcoder.web.reg.actions.profile.ViewAccountInfoAction;

/**
 * <p>
 * Accuracy tests for the {@link BaseProfileAction}.
 * </p>
 *
 * @author extra
 * @version 1.0
 */
public class BaseProfileActionTest {
    /** Represents the auditDAO used to test again. */
    private AuditDAO auditDAOValue;

    /** Represents the auditOperationType used to test again. */
    private String auditOperationTypeValue;

    /** Represents the authenticationSessionKey used to test again. */
    private String authenticationSessionKeyValue;

    /** Represents the logger used to test again. */
    private Log loggerValue;

    /** Represents the profileCompletenessReport used to test again. */
    private ProfileCompletenessReport profileCompletenessReportValue;

    /** Represents the profileCompletenessRetriever used to test again. */
    private ProfileCompletenessRetriever profileCompletenessRetrieverValue;

    /** Represents the servletRequest used to test again. */
    private HttpServletRequest servletRequestValue;

    /** Represents the session used to test again. */
    private Map sessionValue;

    /** Represents the userDAO used to test again. */
    private UserDAO userDAOValue;

    /** Represents the instance used to test again. */
    private BaseProfileAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new ViewAccountInfoAction();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @After
    public void tearDown() throws Exception {
        testInstance = null;
    }

    /**
     * <p>
     * Accuracy test for {@link BaseProfileAction#BaseProfileAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void testBaseProfileAction() throws Exception {
        new ViewAccountInfoAction();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link BaseProfileAction#getAuditDAO()}
     * </p>
     * <p>
     * The value of <code>auditDAO</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getAuditDAO() throws Exception {
        assertNull("Old value", testInstance.getAuditDAO());
    }

    /**
     * <p>
     * Accuracy test {@link BaseProfileAction#setAuditDAO(AuditDAO)}.
     * </p>
     * <p>
     * The value of <code>auditDAO</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setAuditDAO() throws Exception {
        testInstance.setAuditDAO(auditDAOValue);
        assertEquals("New value", auditDAOValue, testInstance.getAuditDAO());
    }

    /**
     * <p>
     * Accuracy test for {@link BaseProfileAction#getAuditOperationType()}
     * </p>
     * <p>
     * The value of <code>auditOperationType</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getAuditOperationType() throws Exception {
        assertNull("Old value", testInstance.getAuditOperationType());
    }

    /**
     * <p>
     * Accuracy test {@link BaseProfileAction#setAuditOperationType(String)}.
     * </p>
     * <p>
     * The value of <code>auditOperationType</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setAuditOperationType() throws Exception {
        testInstance.setAuditOperationType(auditOperationTypeValue);
        assertEquals("New value", auditOperationTypeValue, testInstance.getAuditOperationType());
    }

    /**
     * <p>
     * Accuracy test for {@link BaseProfileAction#getAuthenticationSessionKey()}
     * </p>
     * <p>
     * The value of <code>authenticationSessionKey</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getAuthenticationSessionKey() throws Exception {
        assertNull("Old value", testInstance.getAuthenticationSessionKey());
    }

    /**
     * <p>
     * Accuracy test {@link BaseProfileAction#setAuthenticationSessionKey(String)}.
     * </p>
     * <p>
     * The value of <code>authenticationSessionKey</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setAuthenticationSessionKey() throws Exception {
        testInstance.setAuthenticationSessionKey(authenticationSessionKeyValue);
        assertEquals("New value", authenticationSessionKeyValue, testInstance.getAuthenticationSessionKey());
    }

    /**
     * <p>
     * Accuracy test for {@link BaseProfileAction#getLogger()}
     * </p>
     * <p>
     * The value of <code>logger</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getLogger() throws Exception {
        assertNull("Old value", testInstance.getLogger());
    }

    /**
     * <p>
     * Accuracy test {@link BaseProfileAction#setLogger(Log)}.
     * </p>
     * <p>
     * The value of <code>logger</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setLogger() throws Exception {
        testInstance.setLogger(loggerValue);
        assertEquals("New value", loggerValue, testInstance.getLogger());
    }

    /**
     * <p>
     * Accuracy test for {@link BaseProfileAction#getProfileCompletenessReport()}
     * </p>
     * <p>
     * The value of <code>profileCompletenessReport</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getProfileCompletenessReport() throws Exception {
        assertNull("Old value", testInstance.getProfileCompletenessReport());
    }

    /**
     * <p>
     * Accuracy test {@link BaseProfileAction#setProfileCompletenessReport(ProfileCompletenessReport)}.
     * </p>
     * <p>
     * The value of <code>profileCompletenessReport</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setProfileCompletenessReport() throws Exception {
        testInstance.setProfileCompletenessReport(profileCompletenessReportValue);
        assertEquals("New value", profileCompletenessReportValue, testInstance.getProfileCompletenessReport());
    }

    /**
     * <p>
     * Accuracy test for {@link BaseProfileAction#getProfileCompletenessRetriever()}
     * </p>
     * <p>
     * The value of <code>profileCompletenessRetriever</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getProfileCompletenessRetriever() throws Exception {
        assertNull("Old value", testInstance.getProfileCompletenessRetriever());
    }

    /**
     * <p>
     * Accuracy test {@link BaseProfileAction#setProfileCompletenessRetriever(ProfileCompletenessRetriever)}.
     * </p>
     * <p>
     * The value of <code>profileCompletenessRetriever</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setProfileCompletenessRetriever() throws Exception {
        testInstance.setProfileCompletenessRetriever(profileCompletenessRetrieverValue);
        assertEquals("New value", profileCompletenessRetrieverValue, testInstance.getProfileCompletenessRetriever());
    }

    /**
     * <p>
     * Accuracy test for {@link BaseProfileAction#getServletRequest()}
     * </p>
     * <p>
     * The value of <code>servletRequest</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getServletRequest() throws Exception {
        assertNull("Old value", testInstance.getServletRequest());
    }

    /**
     * <p>
     * Accuracy test {@link BaseProfileAction#setServletRequest(HttpServletRequest)}.
     * </p>
     * <p>
     * The value of <code>servletRequest</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setServletRequest() throws Exception {
        testInstance.setServletRequest(servletRequestValue);
        assertEquals("New value", servletRequestValue, testInstance.getServletRequest());
    }

    /**
     * <p>
     * Accuracy test for {@link BaseProfileAction#getSession()}
     * </p>
     * <p>
     * The value of <code>session</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getSession() throws Exception {
        assertNull("Old value", testInstance.getSession());
    }

    /**
     * <p>
     * Accuracy test {@link BaseProfileAction#setSession(Map)}.
     * </p>
     * <p>
     * The value of <code>session</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setSession() throws Exception {
        testInstance.setSession(sessionValue);
        assertEquals("New value", sessionValue, testInstance.getSession());
    }

    /**
     * <p>
     * Accuracy test for {@link BaseProfileAction#getUserDAO()}
     * </p>
     * <p>
     * The value of <code>userDAO</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getUserDAO() throws Exception {
        assertNull("Old value", testInstance.getUserDAO());
    }

    /**
     * <p>
     * Accuracy test {@link BaseProfileAction#setUserDAO(UserDAO)}.
     * </p>
     * <p>
     * The value of <code>userDAO</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setUserDAO() throws Exception {
        testInstance.setUserDAO(userDAOValue);
        assertEquals("New value", userDAOValue, testInstance.getUserDAO());
    }

}