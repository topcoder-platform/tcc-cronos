/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.profilecompleteness.actions;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.model.Coder;
import com.topcoder.web.common.model.UserProfile;
import com.topcoder.web.reg.profilecompleteness.actions.mock.MockFactory;

/**
 * <p>
 * This class aggregates all accuracy test cases for
 * com.topcoder.web.reg.profilecompleteness.actions.CompleteProfileAction.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class CompleteProfileActionAccUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents CompleteProfileAction instance to test.
     * </p>
     */
    private CompleteProfileAction action;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any exception occurred
     */
    public void setUp() throws Exception {
        super.setUp();
        ActionProxy actionProxy = getActionProxy("/getUserProfileAllFields");
        action = (CompleteProfileAction) actionProxy.getAction();
        MockFactory.populateCompleteProfileAction(action, true);
        MockFactory.resetDAOs();
        MockFactory.initDAOs();
        action.setServletRequest(request);
        action.setServletResponse(response);
        request.getSession().setAttribute(
                (String) getFieldValue("incomingRequestURIKey", action, CompleteProfileAction.class),
                MockFactory.REDIRECTED_URL);
        setFieldValue("user", null, action, CompleteProfileAction.class);
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any exception occurred
     */
    public void tearDown() throws Exception {
        super.tearDown();
        action.clearFieldErrors();
        action.clearActionErrors();
        action = null;
    }

    /**
     * <p>
     * Tests CompleteProfileAction constructor.
     * </p>
     * <p>
     * CompleteProfileAction instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("CompleteProfileAction instance should be created successfully.", action);
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() with properly set attributes.
     * </p>
     * <p>
     * No exception is expected.
     * </p>
     */
    public void testCheckConfiguration() {
        action.checkConfiguration();
    }

    /**
     * <p>
     * Tests CompleteProfileAction#getUserProfile() with valid attributes set.
     * </p>
     * <p>
     * User profile should be retrieved successfully and audit should be done.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetUserProfile() throws Exception {
        setFieldValue("user", null, action, CompleteProfileAction.class);
        action.getUserProfile();
        assertSame("User profile should be retrieved successfully.", MockFactory.getUser(), action.getUser());
        verify(MockFactory.getAuditDAO(), times(1)).audit(any(AuditRecord.class));
    }

    /**
     * <p>
     * Tests CompleteProfileAction#saveUserProfile() with valid attributes and parameters set.
     * </p>
     * <p>
     * User profile should be saved successfully, audit should be done.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testSaveUserProfile1() throws Exception {
        setFieldValue("user", MockFactory.getUser(), action, CompleteProfileAction.class);
        assertNull("Result should be null.", action.saveUserProfile());
        assertSame("User profile should be retrieved successfully.", MockFactory.getUser(), action.getUser());
        assertEquals("Redirected url should be set.", MockFactory.REDIRECTED_URL, response.getRedirectedUrl());
        verify(MockFactory.getUserDAO(), times(1)).saveOrUpdate(MockFactory.getUser());
        verify(MockFactory.getAuditDAO(), times(1)).audit(any(AuditRecord.class));
    }

    /**
     * <p>
     * Tests CompleteProfileAction#saveUserProfile() with valid user without userProfile and parameters set.
     * </p>
     * <p>
     * User profile should be saved successfully, audit should be done.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testSaveUserProfile2() throws Exception {
        // set new empty user profile
        UserProfile userProfile = new UserProfile();
        userProfile.setCoder(new Coder());
        MockFactory.getUser().setUserProfile(userProfile);
        MockFactory.getUser().setPhoneNumbers(null);
        MockFactory.getUser().setAddresses(null);
        MockFactory.getUser().setDemographicResponses(null);
        MockFactory.getUser().setUserSecurityKey(null);
        setFieldValue("user", MockFactory.getUser(), action, CompleteProfileAction.class);
        assertNull("Result should be null.", action.saveUserProfile());
        assertSame("User profile should be retrieved successfully.", MockFactory.getUser(), action.getUser());
        assertEquals("Redirected url should be set.", MockFactory.REDIRECTED_URL, response.getRedirectedUrl());
        verify(MockFactory.getUserDAO(), times(1)).saveOrUpdate(MockFactory.getUser());
        verify(MockFactory.getAuditDAO(), times(1)).audit(any(AuditRecord.class));
    }

    /**
     * <p>
     * Tests CompleteProfileAction#saveUserProfile() with valid user with empty userProfile and parameters set.
     * </p>
     * <p>
     * User profile should be saved successfully, audit should be done.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testSaveUserProfile3() throws Exception {
        // set new empty user profile
        UserProfile userProfile = new UserProfile();
        userProfile.setCoder(new Coder());
        MockFactory.getUser().setUserProfile(userProfile);
        setFieldValue("user", MockFactory.getUser(), action, CompleteProfileAction.class);
        assertNull("Result should be null.", action.saveUserProfile());
        assertSame("User profile should be retrieved successfully.", MockFactory.getUser(), action.getUser());
        assertEquals("Redirected url should be set.", MockFactory.REDIRECTED_URL, response.getRedirectedUrl());
        verify(MockFactory.getUserDAO(), times(1)).saveOrUpdate(MockFactory.getUser());
        verify(MockFactory.getAuditDAO(), times(1)).audit(any(AuditRecord.class));
    }

    /**
     * <p>
     * Tests CompleteProfileAction#saveUserProfile() with valid user without userProfile and only required fields set.
     * </p>
     * <p>
     * User profile should be saved successfully, audit should be done.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testSaveUserProfile4() throws Exception {
        // set new empty user profile
        UserProfile userProfile = new UserProfile();
        userProfile.setCoder(new Coder());
        MockFactory.getUser().setUserProfile(userProfile);
        MockFactory.getUser().setPhoneNumbers(null);
        MockFactory.getUser().setDemographicResponses(null);
        MockFactory.getUser().setAddresses(null);
        MockFactory.getUser().setUserSecurityKey(null);
        setFieldValue("user", MockFactory.getUser(), action, CompleteProfileAction.class);
        // unset not required fields
        action.setJobTitle(null);
        action.setTimezone(null);
        action.setState(null);
        action.setState(null);
        action.setCoderTypeId(null);
        action.setCountryToRepresent(null);
        action.setAge(null);
        action.setGender(null);
        assertNull("Result should be null.", action.saveUserProfile());
        assertSame("User profile should be retrieved successfully.", MockFactory.getUser(), action.getUser());
        assertEquals("Redirected url should be set.", MockFactory.REDIRECTED_URL, response.getRedirectedUrl());
        verify(MockFactory.getUserDAO(), times(1)).saveOrUpdate(MockFactory.getUser());
        verify(MockFactory.getAuditDAO(), times(1)).audit(any(AuditRecord.class));
    }

    /**
     * <p>
     * Tests CompleteProfileAction#validate() with null state field and null phoneNumber.
     * </p>
     * <p>
     * No field error should be added.
     * </p>
     */
    public void testValidate_Null_PhoneNumberAndState() {
        action.setState(null);
        action.setPhoneNumber(null);
        action.validate();
        assertEquals("Field error should be added.", 0, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#validate() with empty state field and null phoneNumber.
     * </p>
     * <p>
     * No field error should be added.
     * </p>
     */
    public void testValidate_Null_PhoneNumber_Empty_State() {
        action.setState("");
        action.setPhoneNumber(null);
        action.validate();
        assertEquals("Field error should be added.", 0, action.getFieldErrors().size());
    }
}