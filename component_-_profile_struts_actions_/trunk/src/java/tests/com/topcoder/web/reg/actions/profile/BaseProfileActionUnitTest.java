/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.shared.security.User;
import com.topcoder.util.log.Log;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.BaseUnitTest;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.mock.MockFactory;

/**
 * <p>
 * This class contains Unit tests for BaseProfileAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseProfileActionUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents action name for testing.
     * </p>
     */
    private static final String ACTION_NAME = "/baseProfileAction";

    /**
     * <p>
     * Represents BaseProfileAction instance for testing.
     * </p>
     */
    private BaseProfileAction action;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        action = (BaseProfileAction) getBean("baseProfileAction");
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void tearDown() throws Exception {
        super.tearDown();
        action = null;
    }

    /**
     * <p>
     * Tests BaseProfileAction constructor.
     * </p>
     * <p>
     * BaseProfileAction instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("BaseProfileAction instance should be created successfully.", action);
    }

    /**
     * <p>
     * Tests BaseProfileAction#getLogger() method.
     * </p>
     * <p>
     * logger should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetLogger() {
        Log logger = mock(Log.class);
        action.setLogger(logger);
        assertSame("getLogger() doesn't work properly.", logger, action.getLogger());
    }

    /**
     * <p>
     * Tests BaseProfileAction#setLogger(Log) method.
     * </p>
     * <p>
     * logger should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetLogger() {
        Log logger = mock(Log.class);
        action.setLogger(logger);
        assertSame("setLogger() doesn't work properly.", logger, action.getLogger());
    }

    /**
     * <p>
     * Tests BaseProfileAction#getAuditDAO() method.
     * </p>
     * <p>
     * auditDAO should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetAuditDAO() {
        AuditDAO auditDAO = mock(AuditDAO.class);
        action.setAuditDAO(auditDAO);
        assertSame("getAuditDAO() doesn't work properly.", auditDAO, action.getAuditDAO());
    }

    /**
     * <p>
     * Tests BaseProfileAction#setAuditDAO(AuditDAO) method.
     * </p>
     * <p>
     * auditDAO should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetAuditDAO() {
        AuditDAO auditDAO = mock(AuditDAO.class);
        action.setAuditDAO(auditDAO);
        assertSame("setAuditDAO() doesn't work properly.", auditDAO, action.getAuditDAO());
    }

    /**
     * <p>
     * Tests BaseProfileAction#getAuthenticationSessionKey() method.
     * </p>
     * <p>
     * authenticationSessionKey should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetAuthenticationSessionKey() {
        String authenticationSessionKey = "test";
        action.setAuthenticationSessionKey(authenticationSessionKey);
        assertSame("getAuthenticationSessionKey() doesn't work properly.", authenticationSessionKey,
                action.getAuthenticationSessionKey());
    }

    /**
     * <p>
     * Tests BaseProfileAction#setAuthenticationSessionKey(String) method.
     * </p>
     * <p>
     * authenticationSessionKey should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetAuthenticationSessionKey() {
        String authenticationSessionKey = "test";
        action.setAuthenticationSessionKey(authenticationSessionKey);
        assertSame("setAuthenticationSessionKey() doesn't work properly.", authenticationSessionKey,
                action.getAuthenticationSessionKey());
    }

    /**
     * <p>
     * Tests BaseProfileAction#getSession() method.
     * </p>
     * <p>
     * session should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetSession() {
        Map<String, Object> session = new HashMap<String, Object>();
        action.setSession(session);
        assertSame("getSession() doesn't work properly.", session, action.getSession());
    }

    /**
     * <p>
     * Tests BaseProfileAction#setSession(Map) method.
     * </p>
     * <p>
     * session should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetSession() {
        Map<String, Object> session = new HashMap<String, Object>();
        action.setSession(session);
        assertSame("setSession() doesn't work properly.", session, action.getSession());
    }

    /**
     * <p>
     * Tests BaseProfileAction#getUserDAO() method.
     * </p>
     * <p>
     * userDAO should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetUserDAO() {
        UserDAO userDAO = mock(UserDAO.class);
        action.setUserDAO(userDAO);
        assertSame("getUserDAO() doesn't work properly.", userDAO, action.getUserDAO());
    }

    /**
     * <p>
     * Tests BaseProfileAction#setUserDAO(UserDAO) method.
     * </p>
     * <p>
     * userDAO should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetUserDAO() {
        UserDAO userDAO = mock(UserDAO.class);
        action.setUserDAO(userDAO);
        assertSame("setUserDAO() doesn't work properly.", userDAO, action.getUserDAO());
    }

    /**
     * <p>
     * Tests BaseProfileAction#getServletRequest() method.
     * </p>
     * <p>
     * request should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetRequest() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        action.setServletRequest(request);
        assertSame("getServletRequest() doesn't work properly.", request, action.getServletRequest());
    }

    /**
     * <p>
     * Tests BaseProfileAction#setRequest(HttpServletRequest) method.
     * </p>
     * <p>
     * request should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetRequest() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        action.setServletRequest(request);
        assertSame("setRequest() doesn't work properly.", request, action.getServletRequest());
    }

    /**
     * <p>
     * Tests BaseProfileAction#getAuditOperationType() method.
     * </p>
     * <p>
     * auditOperationType should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetAuditOperationType() {
        String auditOperationType = "test";
        action.setAuditOperationType(auditOperationType);
        assertSame("getAuditOperationType() doesn't work properly.", auditOperationType,
                action.getAuditOperationType());
    }

    /**
     * <p>
     * Tests BaseProfileAction#setAuditOperationType(String) method.
     * </p>
     * <p>
     * auditOperationType should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetAuditOperationType() {
        String auditOperationType = "test";
        action.setAuditOperationType(auditOperationType);
        assertSame("setAuditOperationType() doesn't work properly.", auditOperationType,
                action.getAuditOperationType());
    }

    /**
     * <p>
     * Tests BaseProfileAction#getProfileCompletenessRetriever() method.
     * </p>
     * <p>
     * profileCompletenessRetriever should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetProfileCompletenessRetriever() {
        ProfileCompletenessRetriever profileCompletenessRetriever = mock(ProfileCompletenessRetriever.class);
        action.setProfileCompletenessRetriever(profileCompletenessRetriever);
        assertSame("getProfileCompletenessRetriever() doesn't work properly.", profileCompletenessRetriever,
                action.getProfileCompletenessRetriever());
    }

    /**
     * <p>
     * Tests BaseProfileAction#setProfileCompletenessRetriever(ProfileCompletenessRetriever) method.
     * </p>
     * <p>
     * profileCompletenessRetriever should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetProfileCompletenessRetriever() {
        ProfileCompletenessRetriever profileCompletenessRetriever = mock(ProfileCompletenessRetriever.class);
        action.setProfileCompletenessRetriever(profileCompletenessRetriever);
        assertSame("setProfileCompletenessRetriever() doesn't work properly.", profileCompletenessRetriever,
                action.getProfileCompletenessRetriever());
    }

    /**
     * <p>
     * Tests BaseProfileAction#getProfileCompletenessReport() method.
     * </p>
     * <p>
     * profileCompletenessReport should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetProfileCompletenessReport() {
        ProfileCompletenessReport profileCompletenessReport = new ProfileCompletenessReport();
        action.setProfileCompletenessReport(profileCompletenessReport);
        assertSame("getProfileCompletenessReport() doesn't work properly.", profileCompletenessReport,
                action.getProfileCompletenessReport());
    }

    /**
     * <p>
     * Tests BaseProfileAction#setProfileCompletenessReport(ProfileCompletenessReport) method.
     * </p>
     * <p>
     * profileCompletenessReport should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetProfileCompletenessReport() {
        ProfileCompletenessReport profileCompletenessReport = new ProfileCompletenessReport();
        action.setProfileCompletenessReport(profileCompletenessReport);
        assertSame("setProfileCompletenessReport() doesn't work properly.", profileCompletenessReport,
                action.getProfileCompletenessReport());
    }

    /**
     * <p>
     * Tests BaseProfileAction#checkInitialization() method.
     * </p>
     * <p>
     * checkInitialization() should be done successfully. No exception is expected.
     * </p>
     */
    public void testCheckInitialization() {
        action.checkInitialization();
    }

    /**
     * <p>
     * Tests BaseProfileAction#checkInitialization() method with null logger.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     */
    public void testCheckInitialization_Null_logger() {
        action.setLogger(null);
        try {
            action.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BaseProfileAction#checkInitialization() method with null auditDAO.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     */
    public void testCheckInitialization_Null_auditDAO() {
        action.setAuditDAO(null);
        try {
            action.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BaseProfileAction#checkInitialization() method with null authenticationSessionKey.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     */
    public void testCheckInitialization_Null_authenticationSessionKey() {
        action.setAuthenticationSessionKey(null);
        try {
            action.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BaseProfileAction#checkInitialization() method with empty authenticationSessionKey.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     */
    public void testCheckInitialization_Empty_authenticationSessionKey() {
        action.setAuthenticationSessionKey("");
        try {
            action.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BaseProfileAction#checkInitialization() method with null userDAO.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     */
    public void testCheckInitialization_Null_userDAO() {
        action.setUserDAO(null);
        try {
            action.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BaseProfileAction#checkInitialization() method with null auditOperationType.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     */
    public void testCheckInitialization_Null_auditOperationType() {
        action.setAuditOperationType(null);
        try {
            action.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BaseProfileAction#checkInitialization() method with empty auditOperationType.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     */
    public void testCheckInitialization_Empty_auditOperationType() {
        action.setAuditOperationType("");
        try {
            action.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BaseProfileAction#checkInitialization() method with null profileCompletenessRetriever.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     */
    public void testCheckInitialization_Null_profileCompletenessRetriever() {
        action.setProfileCompletenessRetriever(null);
        try {
            action.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BaseProfileAction#getAuthentication() method with authentication present in session.
     * </p>
     * <p>
     * WebAuthentication should be retrieved successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetAuthentication() throws Exception {
        ActionProxy actionProxy = getActionProxy(ACTION_NAME);
        action = (BaseProfileAction) actionProxy.getAction();
        WebAuthentication authentication =
                MockFactory.createWebAuthentication(MockFactory.createSecurityUser(1L, "user", "password"));
        action.setSession(new HashMap<String, Object>());
        action.getSession().put(action.getAuthenticationSessionKey(), authentication);
        WebAuthentication actualAuthentication = action.getAuthentication();
        assertNotNull("WebAuthentication should be retrieved successfully.", actualAuthentication);
    }

    /**
     * <p>
     * Tests BaseProfileAction#getAuthentication() method with authentication not present in session.
     * </p>
     * <p>
     * null should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetAuthentication_Null() {
        ActionProxy actionProxy = getActionProxy(ACTION_NAME);
        action = (BaseProfileAction) actionProxy.getAction();
        action.setSession(new HashMap<String, Object>());
        WebAuthentication authentication = action.getAuthentication();
        assertNull("null should be retrieved successfully.", authentication);
    }

    /**
     * <p>
     * Tests BaseProfileAction#getAuthentication() method with object of different type present for authentication key.
     * </p>
     * <p>
     * null should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetAuthentication_Null1() {
        ActionProxy actionProxy = getActionProxy(ACTION_NAME);
        action = (BaseProfileAction) actionProxy.getAction();
        action.setSession(new HashMap<String, Object>());
        action.getSession().put(action.getAuthenticationSessionKey(), 1);
        WebAuthentication authentication = action.getAuthentication();
        assertNull("null should be retrieved successfully.", authentication);
    }

    /**
     * <p>
     * Tests BaseProfileAction#getAuthentication() method with authentication present in session.
     * </p>
     * <p>
     * User should be retrieved successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetAuthenticationUser() throws Exception {
        ActionProxy actionProxy = getActionProxy(ACTION_NAME);
        action = (BaseProfileAction) actionProxy.getAction();
        WebAuthentication authentication =
                MockFactory.createWebAuthentication(MockFactory.createSecurityUser(1L, "user", "password"));
        action.setSession(new HashMap<String, Object>());
        action.getSession().put(action.getAuthenticationSessionKey(), authentication);
        User user = action.getAuthenticationUser();
        assertNotNull("User should be retrieved successfully.", user);
    }

    /**
     * <p>
     * Tests BaseProfileAction#getAuthenticationUser() method with authentication not present in session.
     * </p>
     * <p>
     * null should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetAuthenticationUser_Null() {
        ActionProxy actionProxy = getActionProxy(ACTION_NAME);
        action = (BaseProfileAction) actionProxy.getAction();
        action.setSession(new HashMap<String, Object>());
        User user = action.getAuthenticationUser();
        assertNull("null should be retrieved successfully.", user);
    }

    /**
     * <p>
     * Tests BaseProfileAction#getPreparedAuditRecord() method with authentication present in session.
     * </p>
     * <p>
     * AuditRecord should be retrieved successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetPreparedAuditRecord() throws Exception {
        ActionProxy actionProxy = getActionProxy(ACTION_NAME);
        action = (BaseProfileAction) actionProxy.getAction();
        action.setServletRequest(MockFactory.createServletRequest());
        String handle = "user";
        WebAuthentication authentication =
                MockFactory.createWebAuthentication(MockFactory.createSecurityUser(1L, handle, "password"));
        action.setSession(new HashMap<String, Object>());
        action.getSession().put(action.getAuthenticationSessionKey(), authentication);
        AuditRecord actualRecord = action.getPreparedAuditRecord();
        assertNotNull("WebAuthentication should be retrieved successfully.", actualRecord);
        assertEquals("User handle should be set successfully.", handle, actualRecord.getHandle());
    }
}
