/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.rmi.RemoteException;
import java.util.HashMap;

import com.topcoder.security.GeneralSecurityException;
import com.topcoder.security.TCSubject;
import com.topcoder.security.UserPrincipal;
import com.topcoder.security.admin.PrincipalMgrRemote;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.model.Coder;
import com.topcoder.web.common.model.CoderReferral;
import com.topcoder.web.common.model.Referral;
import com.topcoder.web.common.model.SecretQuestion;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserSecurityKey;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.BaseUnitTest;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.ProfileActionException;
import com.topcoder.web.reg.mock.MockFactory;

/**
 * <p>
 * This class contains Unit tests for SaveAccountInfoAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SaveAccountInfoActionUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents action name for testing.
     * </p>
     */
    private static final String ACTION_NAME = "/saveAccountInfoAction";

    /**
     * <p>
     * Represents SaveAccountInfoAction instance for testing.
     * </p>
     */
    private SaveAccountInfoAction action;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        MockFactory.initDAOs();
        action = (SaveAccountInfoAction) getActionProxy(ACTION_NAME).getAction();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void tearDown() throws Exception {
        super.tearDown();
        MockFactory.resetDAOs();
        action.clearFieldErrors();
        action.clearActionErrors();
        action = null;
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction constructor.
     * </p>
     * <p>
     * SaveAccountInfoAction instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("SaveAccountInfoAction instance should be created successfully.", action);
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#processInputData() method with valid fields and user doesn't have any primary field.
     * </p>
     * <p>
     * All values should be set successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testProcessInputData() throws Exception {
        action.setServletRequest(MockFactory.createServletRequest());
        long id = 1L;
        WebAuthentication authentication =
                MockFactory.createWebAuthentication(MockFactory.createSecurityUser(id, "user", "password"));
        action.setSession(new HashMap<String, Object>());
        action.getSession().put(action.getAuthenticationSessionKey(), authentication);
        User user = MockFactory.createUser(id, "first name", "last name", "handle");
        when(action.getUserDAO().find(id)).thenReturn(user);
        User savedUser = MockFactory.getUser();
        savedUser.setSecretQuestion(new SecretQuestion());
        CoderReferral coderReferral = new CoderReferral();
        Referral referral = new Referral();
        referral.setId(1);
        coderReferral.setReferral(referral);
        savedUser.getCoder().setCoderReferral(coderReferral);
        // new values
        action.setPassword("password");
        action.setSavedUser(savedUser);
        // process input data
        action.processInputData(user);
        assertEquals("Values should be updated.", savedUser.getUserSecurityKey().getSecurityKey(), user
                .getUserSecurityKey().getSecurityKey());
        assertEquals("Values should be updated.", savedUser.getSecretQuestion(), user.getSecretQuestion());
        assertEquals("Values should be updated.", savedUser.getCoder().getCoderReferral().getReferral(), user
                .getCoder().getCoderReferral().getReferral());
        verify(action.getAuditDAO(), times(1)).audit(any(AuditRecord.class));
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#processInputData() method with valid fields and user has some fields preset.
     * </p>
     * <p>
     * All values should be set successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testProcessInputData_HasValues() throws Exception {
        action.setServletRequest(MockFactory.createServletRequest());
        long id = 1L;
        WebAuthentication authentication =
                MockFactory.createWebAuthentication(MockFactory.createSecurityUser(id, "user", "password"));
        action.setSession(new HashMap<String, Object>());
        action.getSession().put(action.getAuthenticationSessionKey(), authentication);
        // clear all primary values
        User user = MockFactory.getUser();
        user.setCoder(new Coder());
        user.setSecretQuestion(new SecretQuestion());
        when(action.getUserDAO().find(id)).thenReturn(user);
        User savedUser = MockFactory.createUser(id, "first name", "last name", "handle");
        // new values
        action.setSavedUser(savedUser);
        // process input data
        action.processInputData(user);
        assertNull("Values should be updated.", user.getSecretQuestion());
        assertNull("Values should be updated.", user.getCoder().getCoderType());
        verify(action.getAuditDAO(), times(1)).audit(any(AuditRecord.class));
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#processInputData() method with valid fields and user has some fields preset.
     * </p>
     * <p>
     * All values should be set successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testProcessInputData_HasValues1() throws Exception {
        action.setServletRequest(MockFactory.createServletRequest());
        long id = 1L;
        WebAuthentication authentication =
                MockFactory.createWebAuthentication(MockFactory.createSecurityUser(id, "user", "password"));
        action.setSession(new HashMap<String, Object>());
        action.getSession().put(action.getAuthenticationSessionKey(), authentication);
        // clear all primary values
        User user = MockFactory.createUser(id, "first name", "last name", "handle");
        when(action.getUserDAO().find(id)).thenReturn(user);
        User savedUser = MockFactory.createUser(1L, "first", "last", "handle");
        savedUser.setSecretQuestion(new SecretQuestion());
        // new values
        action.setSavedUser(savedUser);
        // process input data
        action.processInputData(user);
        assertNotNull("Values should be updated.", user.getSecretQuestion());
        assertNull("Values should be updated.", user.getCoder());
        verify(action.getAuditDAO(), times(1)).audit(any(AuditRecord.class));
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#processInputData() method with valid fields and user has some fields preset.
     * </p>
     * <p>
     * All values should be set successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testProcessInputData_HasValues2() throws Exception {
        action.setServletRequest(MockFactory.createServletRequest());
        long id = 1L;
        WebAuthentication authentication =
                MockFactory.createWebAuthentication(MockFactory.createSecurityUser(id, "user", "password"));
        action.setSession(new HashMap<String, Object>());
        action.getSession().put(action.getAuthenticationSessionKey(), authentication);
        // clear all primary values
        User user = MockFactory.getUser();
        user.setCoder(new Coder());
        user.setSecretQuestion(new SecretQuestion());
        when(action.getUserDAO().find(id)).thenReturn(user);
        User savedUser = MockFactory.createUser(id, "first name", "last name", "handle");
        savedUser.setCoder(new Coder());
        // new values
        action.setSavedUser(savedUser);
        // process input data
        action.processInputData(user);
        assertNull("Values should be updated.", user.getSecretQuestion());
        assertNull("Values should be updated.", user.getCoder().getCoderType());
        verify(action.getAuditDAO(), times(1)).audit(any(AuditRecord.class));
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#processInputData() method with valid fields and user doesn't have any primary
     * addresses, but remote exception occurred.
     * </p>
     * <p>
     * ProfileActionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testProcessInputData_ExceptionRemote() throws Exception {
        action.setServletRequest(MockFactory.createServletRequest());
        long id = 1L;
        WebAuthentication authentication =
                MockFactory.createWebAuthentication(MockFactory.createSecurityUser(id, "user", "password"));
        action.setSession(new HashMap<String, Object>());
        action.getSession().put(action.getAuthenticationSessionKey(), authentication);
        // clear all primary values
        User user = MockFactory.createUser(id, "first name", "last name", "handle");
        when(action.getUserDAO().find(id)).thenReturn(user);
        User savedUser = MockFactory.getUser();
        // remote exception
        when(
                action.getPrincipalMgr().editPassword(any(UserPrincipal.class), anyString(), any(TCSubject.class),
                        anyString())).thenThrow(new RemoteException("just for testing."));
        // new values
        action.setSavedUser(savedUser);
        // process input data
        try {
            action.processInputData(user);
            fail("ProfileActionException exception is expected.");
        } catch (ProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#processInputData() method with valid fields and user doesn't have any primary
     * addresses, but general security exception occurred.
     * </p>
     * <p>
     * ProfileActionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testProcessInputData_ExceptionGeneralSecurity() throws Exception {
        action.setServletRequest(MockFactory.createServletRequest());
        long id = 1L;
        WebAuthentication authentication =
                MockFactory.createWebAuthentication(MockFactory.createSecurityUser(id, "user", "password"));
        action.setSession(new HashMap<String, Object>());
        action.getSession().put(action.getAuthenticationSessionKey(), authentication);
        // clear all primary values
        User user = MockFactory.createUser(id, "first name", "last name", "handle");
        when(action.getUserDAO().find(id)).thenReturn(user);
        User savedUser = MockFactory.getUser();
        // general security exception
        when(
                action.getPrincipalMgr().editPassword(any(UserPrincipal.class), anyString(), any(TCSubject.class),
                        anyString())).thenThrow(new GeneralSecurityException("just for testing."));
        // new values
        action.setSavedUser(savedUser);
        // process input data
        try {
            action.processInputData(user);
            fail("ProfileActionException exception is expected.");
        } catch (ProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#validate() method with valid fields.
     * </p>
     * <p>
     * Validation should ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate() throws Exception {
        action.setServletRequest(MockFactory.createServletRequest());
        long id = 1L;
        WebAuthentication authentication =
                MockFactory.createWebAuthentication(MockFactory.createSecurityUser(id, "user", "password"));
        action.setSession(new HashMap<String, Object>());
        action.getSession().put(action.getAuthenticationSessionKey(), authentication);
        String confirmPassword = "password";
        String referralUserHandle = "referralUserHandle";
        when(action.getPrincipalMgr().getPassword(1L)).thenReturn(confirmPassword);
        when(action.getUserDAO().find(referralUserHandle, true)).thenReturn(
                MockFactory.createUser(2L, "first2", "last2", referralUserHandle));
        User savedUser = MockFactory.getUser();
        when(action.getUserDAO().canChangeHandle("user")).thenReturn(true);
        action.setConfirmPassword(confirmPassword);
        action.setPassword(confirmPassword);
        action.setReferralUserHandle(referralUserHandle);
        // new values
        action.setSavedUser(savedUser);
        // process input data
        action.validate();
        assertEquals("There should be no field errors.", 0, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#validate() method with not unique handle.
     * </p>
     * <p>
     * Validation should ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_NotUniqueHandle() throws Exception {
        action.setServletRequest(MockFactory.createServletRequest());
        long id = 1L;
        WebAuthentication authentication =
                MockFactory.createWebAuthentication(MockFactory.createSecurityUser(id, "user", "password"));
        action.setSession(new HashMap<String, Object>());
        action.getSession().put(action.getAuthenticationSessionKey(), authentication);
        String confirmPassword = "password";
        String referralUserHandle = "referralUserHandle";
        when(action.getUserDAO().find(referralUserHandle, true)).thenReturn(
                MockFactory.createUser(2L, "first2", "last2", referralUserHandle));
        User savedUser = MockFactory.getUser();
        String handle = "notUniqueHandle";
        savedUser.setHandle(handle);
        when(action.getUserDAO().find(handle, true)).thenReturn(new User());
        when(action.getUserDAO().canChangeHandle("user")).thenReturn(true);
        action.setConfirmPassword(confirmPassword);
        action.setPassword(confirmPassword);
        action.setReferralUserHandle(referralUserHandle);
        // new values
        action.setSavedUser(savedUser);
        // process input data
        action.validate();
        assertEquals("There should be no field errors.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#validate() method with user that cannot change handle.
     * </p>
     * <p>
     * Validation should ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_CannotChangeHandle() throws Exception {
        action.setServletRequest(MockFactory.createServletRequest());
        long id = 1L;
        WebAuthentication authentication =
                MockFactory.createWebAuthentication(MockFactory.createSecurityUser(id, "user", "password"));
        action.setSession(new HashMap<String, Object>());
        action.getSession().put(action.getAuthenticationSessionKey(), authentication);
        String confirmPassword = "password";
        String referralUserHandle = "referralUserHandle";
        when(action.getUserDAO().find(referralUserHandle, true)).thenReturn(
                MockFactory.createUser(2L, "first2", "last2", referralUserHandle));
        User savedUser = MockFactory.getUser();
        when(action.getUserDAO().canChangeHandle("user")).thenReturn(false);
        action.setConfirmPassword(confirmPassword);
        action.setPassword(confirmPassword);
        action.setReferralUserHandle(referralUserHandle);
        // new values
        action.setSavedUser(savedUser);
        // process input data
        action.validate();
        assertEquals("There should be no field errors.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#validate() method with self referral handle.
     * </p>
     * <p>
     * Validation should ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_SelfReferral() throws Exception {
        action.setServletRequest(MockFactory.createServletRequest());
        long id = 1L;
        User savedUser = MockFactory.getUser();
        WebAuthentication authentication =
                MockFactory.createWebAuthentication(MockFactory.createSecurityUser(id, savedUser.getHandle(),
                        "password"));
        action.setSession(new HashMap<String, Object>());
        action.getSession().put(action.getAuthenticationSessionKey(), authentication);
        String confirmPassword = "password";
        // self referral
        String referralUserHandle = savedUser.getHandle();
        when(action.getUserDAO().find(referralUserHandle, true)).thenReturn(
                MockFactory.createUser(2L, "first2", "last2", referralUserHandle));
        when(action.getUserDAO().canChangeHandle("user")).thenReturn(true);
        action.setConfirmPassword(confirmPassword);
        action.setPassword(confirmPassword);
        action.setReferralUserHandle(referralUserHandle);
        when(action.getUserDAO().find(referralUserHandle, true)).thenReturn(new User());
        // new values
        action.setSavedUser(savedUser);
        // process input data
        action.validate();
        assertEquals("There should be no field errors.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#validate() method with referral not found for handle.
     * </p>
     * <p>
     * Validation should ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_ReferralNotFound() throws Exception {
        action.setServletRequest(MockFactory.createServletRequest());
        long id = 1L;
        User savedUser = MockFactory.getUser();
        WebAuthentication authentication =
                MockFactory.createWebAuthentication(MockFactory.createSecurityUser(id, savedUser.getHandle(),
                        "password"));
        action.setSession(new HashMap<String, Object>());
        action.getSession().put(action.getAuthenticationSessionKey(), authentication);
        String confirmPassword = "password";
        // self referral
        String referralUserHandle = "referral";
        when(action.getUserDAO().find(referralUserHandle, true)).thenReturn(
                MockFactory.createUser(2L, "first2", "last2", referralUserHandle));
        when(action.getUserDAO().canChangeHandle("user")).thenReturn(true);
        action.setConfirmPassword(confirmPassword);
        action.setPassword(confirmPassword);
        action.setReferralUserHandle(referralUserHandle);
        when(action.getUserDAO().find(referralUserHandle, true)).thenReturn(null);
        // new values
        action.setSavedUser(savedUser);
        // process input data
        action.validate();
        assertEquals("There should be no field errors.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#validate() method with not equal passwords.
     * </p>
     * <p>
     * Validation should ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_NotEqualPasswords() throws Exception {
        action.setServletRequest(MockFactory.createServletRequest());
        long id = 1L;
        User savedUser = MockFactory.getUser();
        WebAuthentication authentication =
                MockFactory.createWebAuthentication(MockFactory.createSecurityUser(id, savedUser.getHandle(),
                        "password"));
        action.setSession(new HashMap<String, Object>());
        action.getSession().put(action.getAuthenticationSessionKey(), authentication);
        String confirmPassword = "password";
        // self referral
        String referralUserHandle = null;
        when(action.getUserDAO().find(referralUserHandle, true)).thenReturn(
                MockFactory.createUser(2L, "first2", "last2", referralUserHandle));
        when(action.getUserDAO().canChangeHandle("user")).thenReturn(true);
        action.setConfirmPassword(confirmPassword);
        action.setPassword("some other password");
        action.setReferralUserHandle(referralUserHandle);
        // new values
        action.setSavedUser(savedUser);
        // process input data
        action.validate();
        assertEquals("There should be no field errors.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#validate() method with user not logged in to session.
     * </p>
     * <p>
     * Validation should ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_UserNotInSession() throws Exception {
        action.setServletRequest(MockFactory.createServletRequest());
        User savedUser = MockFactory.getUser();
        action.setSession(new HashMap<String, Object>());
        String confirmPassword = "password";
        // self referral
        String referralUserHandle = null;
        when(action.getUserDAO().find(referralUserHandle, true)).thenReturn(
                MockFactory.createUser(2L, "first2", "last2", referralUserHandle));
        when(action.getUserDAO().canChangeHandle("user")).thenReturn(true);
        action.setConfirmPassword(confirmPassword);
        action.setPassword("some other password");
        action.setReferralUserHandle(referralUserHandle);
        // new values
        action.setSavedUser(savedUser);
        // process input data
        action.validate();
        assertEquals("There should be no action errors.", 1, action.getActionErrors().size());
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#validate() method with underlying exception occurred in DAO
     * </p>
     * <p>
     * Validation should ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_ExceptionDAO() throws Exception {
        action.setServletRequest(MockFactory.createServletRequest());
        User savedUser = MockFactory.getUser();
        long id = 1L;
        WebAuthentication authentication =
                MockFactory.createWebAuthentication(MockFactory.createSecurityUser(id, "user", "password"));
        action.setSession(new HashMap<String, Object>());
        action.getSession().put(action.getAuthenticationSessionKey(), authentication);
        // underlying exception
        when(action.getUserDAO().canChangeHandle("user")).thenThrow(new RuntimeException("just for testing."));
        // new values
        action.setSavedUser(savedUser);
        // process input data
        action.validate();
        assertEquals("There should be no action errors.", 1, action.getActionErrors().size());
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#generateEmailTemplateData(User) method with valid fields and user passed.
     * </p>
     * <p>
     * Template should be retrieved successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGenerateEmailTemplate1() throws Exception {
        action.setPassword("password");
        action.setReferralUserHandle("referralUser");
        User user = MockFactory.getUser();
        user.setDemographicResponses(MockFactory.getDemographicResponses());
        user.setSecretQuestion(new SecretQuestion());
        user.setUserSecurityKey(new UserSecurityKey());
        // new values
        String actualTemplate = action.generateEmailTemplateData(user);
        assertNotNull("Template should be generated successfully.", actualTemplate);
        assertTrue("Template should be generated successfully.", actualTemplate.startsWith("<DATA>"));
        assertTrue("Template should be generated successfully.", actualTemplate.endsWith("</DATA>"));
        assertTrue("Template should be generated successfully.", actualTemplate.contains("<HANDLE>handle</HANDLE>"));
        assertTrue("Template should be generated successfully.", actualTemplate.contains("<ID>1</ID>"));
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#generateEmailTemplateData(User) method with valid fields and user passed.
     * </p>
     * <p>
     * Template should be retrieved successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGenerateEmailTemplate2() throws Exception {
        action.setPassword("password");
        action.setReferralUserHandle("referralUser");
        User user = MockFactory.createUser(1L, "first", "last", "handle");
        user.setDemographicResponses(MockFactory.getDemographicResponses());
        user.setCoder(new Coder());
        // new values
        String actualTemplate = action.generateEmailTemplateData(user);
        assertNotNull("Template should be generated successfully.", actualTemplate);
        assertTrue("Template should be generated successfully.", actualTemplate.startsWith("<DATA>"));
        assertTrue("Template should be generated successfully.", actualTemplate.endsWith("</DATA>"));
        assertTrue("Template should be generated successfully.", actualTemplate.contains("<HANDLE>handle</HANDLE>"));
        assertTrue("Template should be generated successfully.", actualTemplate.contains("<ID>1</ID>"));
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#generateEmailTemplateData(User) method with valid fields and user passed.
     * </p>
     * <p>
     * Template should be retrieved successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGenerateEmailTemplate3() throws Exception {
        action.setPassword("password");
        action.setReferralUserHandle("referralUser");
        User user = MockFactory.createUser(1L, "first", "last", "handle");
        user.setDemographicResponses(MockFactory.getDemographicResponses());
        // new values
        String actualTemplate = action.generateEmailTemplateData(user);
        assertNotNull("Template should be generated successfully.", actualTemplate);
        assertTrue("Template should be generated successfully.", actualTemplate.startsWith("<DATA>"));
        assertTrue("Template should be generated successfully.", actualTemplate.endsWith("</DATA>"));
        assertTrue("Template should be generated successfully.", actualTemplate.contains("<HANDLE>handle</HANDLE>"));
        assertTrue("Template should be generated successfully.", actualTemplate.contains("<ID>1</ID>"));
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#getPrincipalMgr() method.
     * </p>
     * <p>
     * principalMgr should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetPrincipalMgr() {
        PrincipalMgrRemote principalMgr = MockFactory.getPrincipalMgr();
        action.setPrincipalMgr(principalMgr);
        assertSame("getPrincipalMgr() doesn't work properly.", principalMgr, action.getPrincipalMgr());
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#setPrincipalMgr(PrincipalMgrRemote) method.
     * </p>
     * <p>
     * principalMgr should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetPrincipalMgr() {
        PrincipalMgrRemote principalMgr = MockFactory.getPrincipalMgr();
        action.setPrincipalMgr(principalMgr);
        assertSame("setPrincipalMgr() doesn't work properly.", principalMgr, action.getPrincipalMgr());
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#getReferralUserHandle() method.
     * </p>
     * <p>
     * referralUserHandle should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetReferralUserHandle() {
        String referralUserHandle = "test";
        action.setReferralUserHandle(referralUserHandle);
        assertSame("getReferralUserHandle() doesn't work properly.", referralUserHandle,
                action.getReferralUserHandle());
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#setReferralUserHandle(String) method.
     * </p>
     * <p>
     * referralUserHandle should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetReferralUserHandle() {
        String referralUserHandle = "test";
        action.setReferralUserHandle(referralUserHandle);
        assertSame("setReferralUserHandle() doesn't work properly.", referralUserHandle,
                action.getReferralUserHandle());
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#getConfirmPassword() method.
     * </p>
     * <p>
     * confirmPassword should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetConfirmPassword() {
        String confirmPassword = "test";
        action.setConfirmPassword(confirmPassword);
        assertSame("getConfirmPassword() doesn't work properly.", confirmPassword, action.getConfirmPassword());
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#setConfirmPassword(String) method.
     * </p>
     * <p>
     * confirmPassword should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetConfirmPassword() {
        String confirmPassword = "test";
        action.setConfirmPassword(confirmPassword);
        assertSame("setConfirmPassword() doesn't work properly.", confirmPassword, action.getConfirmPassword());
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#getPassword() method.
     * </p>
     * <p>
     * password should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetPassword() {
        String password = "test";
        action.setPassword(password);
        assertSame("getPassword() doesn't work properly.", password, action.getPassword());
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#setPassword(String) method.
     * </p>
     * <p>
     * password should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetPassword() {
        String password = "test";
        action.setPassword(password);
        assertSame("setPassword() doesn't work properly.", password, action.getPassword());
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#checkInitialization() method with valid fields.
     * </p>
     * <p>
     * check initialization should pass successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization() throws Exception {
        action.checkInitialization();
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#checkInitialization() method with null principalMgr.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization_Null_PrincipalMgr() throws Exception {
        action.setPrincipalMgr(null);
        try {
            action.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }
}
