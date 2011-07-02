/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.profilecompleteness.actions;

import static com.topcoder.web.reg.profilecompleteness.actions.mock.MockFactory.REDIRECTED_URL;
import static com.topcoder.web.reg.profilecompleteness.actions.mock.MockFactory.getCountryDAO;
import static com.topcoder.web.reg.profilecompleteness.actions.mock.MockFactory.getDemographicQuestionDAO;
import static com.topcoder.web.reg.profilecompleteness.actions.mock.MockFactory.getStateDAO;
import static com.topcoder.web.reg.profilecompleteness.actions.mock.MockFactory.getTimeZoneDAO;
import static com.topcoder.web.reg.profilecompleteness.actions.mock.MockFactory.getUser;
import static com.topcoder.web.reg.profilecompleteness.actions.mock.MockFactory.getUserDAO;
import static com.topcoder.web.reg.profilecompleteness.actions.mock.MockFactory.getUserIdRetriever;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.profilecompleteness.actions.mock.MockFactory;

/**
 * <p>
 * This class aggregates all failure test cases for
 * com.topcoder.web.reg.profilecompleteness.actions.CompleteProfileAction.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class CompleteProfileActionFailureUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents CompleteProfileAction instance to test.
     * </p>
     */
    private CompleteProfileAction action;

    /**
     * <p>
     * Represents state for testing.
     * </p>
     */
    private String state;

    /**
     * <p>
     * Represents phone number for testing.
     * </p>
     */
    private String phoneNumber;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any exception occurred
     */
    public void setUp() throws Exception {
        super.setUp();
        state = "LA";
        phoneNumber = "(+000) 000 000-000";
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
        phoneNumber = null;
        state = null;
    }

    /**
     * <p>
     * Tests CompleteProfileAction#saveUserProfile() with user is not logged in.
     * </p>
     * <p>
     * LOGIN is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testSaveUserProfile_UserNotLoggedIn() throws Exception {
        when(getUserIdRetriever().getUserId(request)).thenReturn(null);
        assertEquals("LOGIN is expected.", ActionSupport.LOGIN, action.saveUserProfile());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#saveUserProfile() with user not found for given ID.
     * </p>
     * <p>
     * CompleteProfileActionException exception is expected.
     * </p>
     */
    public void testSaveUserProfile_UserNotFound() {
        when(getUserDAO().find(1L)).thenReturn(null);
        try {
            action.saveUserProfile();
            fail("CompleteProfileActionException exception is expected.");
        } catch (CompleteProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#saveUserProfile() with state not found for given ID.
     * </p>
     * <p>
     * CompleteProfileActionException exception is expected.
     * </p>
     */
    public void testSaveUserProfile_StateNotFound() {
        when(getStateDAO().find(anyString())).thenReturn(null);
        try {
            action.saveUserProfile();
            fail("CompleteProfileActionException exception is expected.");
        } catch (CompleteProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#saveUserProfile() with timezone not found for given ID.
     * </p>
     * <p>
     * CompleteProfileActionException exception is expected.
     * </p>
     */
    public void testSaveUserProfile_TimezoneNotFound() {
        when(getTimeZoneDAO().find(anyString())).thenReturn(null);
        try {
            action.saveUserProfile();
            fail("CompleteProfileActionException exception is expected.");
        } catch (CompleteProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#saveUserProfile() with country not found for given ID.
     * </p>
     * <p>
     * CompleteProfileActionException exception is expected.
     * </p>
     */
    public void testSaveUserProfile_CountryNotFound() {
        when(getCountryDAO().find(anyString())).thenReturn(null);
        try {
            action.saveUserProfile();
            fail("CompleteProfileActionException exception is expected.");
        } catch (CompleteProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#saveUserProfile() with demographic question not found for given ID.
     * </p>
     * <p>
     * CompleteProfileActionException exception is expected.
     * </p>
     */
    public void testSaveUserProfile_DemographicQuestionNotFound() {
        when(getDemographicQuestionDAO().find(anyLong())).thenReturn(null);
        try {
            action.saveUserProfile();
            fail("CompleteProfileActionException exception is expected.");
        } catch (CompleteProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#saveUserProfile() with exception occurred in underlying userDAO.
     * </p>
     * <p>
     * CompleteProfileActionException exception is expected.
     * </p>
     */
    public void testSaveUserProfile_UserDAOException() {
        doThrow(new RuntimeException("just for testing.")).when(getUserDAO()).saveOrUpdate(any(User.class));
        try {
            action.saveUserProfile();
            fail("CompleteProfileActionException exception is expected.");
        } catch (CompleteProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#saveUserProfile() with exception occurred in underlying countryDAO.
     * </p>
     * <p>
     * CompleteProfileActionException exception is expected.
     * </p>
     */
    public void testSaveUserProfile_CountryDAOException() {
        doThrow(new RuntimeException("just for testing.")).when(getCountryDAO()).find(anyString());
        try {
            action.saveUserProfile();
            fail("CompleteProfileActionException exception is expected.");
        } catch (CompleteProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#saveUserProfile() with exception occurred in underlying stateDAO.
     * </p>
     * <p>
     * CompleteProfileActionException exception is expected.
     * </p>
     */
    public void testSaveUserProfile_StateDAOException() {
        doThrow(new RuntimeException("just for testing.")).when(getStateDAO()).find(anyString());
        try {
            action.saveUserProfile();
            fail("CompleteProfileActionException exception is expected.");
        } catch (CompleteProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#saveUserProfile() with exception occurred in underlying timeZoneDAO.
     * </p>
     * <p>
     * CompleteProfileActionException exception is expected.
     * </p>
     */
    public void testSaveUserProfile_TimeZoneDAOException() {
        doThrow(new RuntimeException("just for testing.")).when(MockFactory.getTimeZoneDAO()).find(anyString());
        try {
            action.saveUserProfile();
            fail("CompleteProfileActionException exception is expected.");
        } catch (CompleteProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#saveUserProfile() with exception occurred in underlying demographicQuestionDAO.
     * </p>
     * <p>
     * CompleteProfileActionException exception is expected.
     * </p>
     */
    public void testSaveUserProfile_DemographicQuestionDAO() {
        doThrow(new RuntimeException("just for testing.")).when(MockFactory.getDemographicQuestionDAO()).find(
                anyLong());
        try {
            action.saveUserProfile();
            fail("CompleteProfileActionException exception is expected.");
        } catch (CompleteProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#saveUserProfile() with no demographic answer for given answer text.
     * </p>
     * <p>
     * CompleteProfileActionException exception is expected.
     * </p>
     */
    public void testSaveUserProfile_NoDemographicAnswer() {
        action.setAge("NotExistingAnswer");
        try {
            action.saveUserProfile();
            fail("CompleteProfileActionException exception is expected.");
        } catch (CompleteProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#saveUserProfile() with null coder.
     * </p>
     * <p>
     * CompleteProfileActionException exception is expected.
     * </p>
     */
    public void testSaveUserProfile_Null_Coder() {
        getUser().setUserProfile(null);
        try {
            action.saveUserProfile();
            fail("CompleteProfileActionException exception is expected.");
        } catch (CompleteProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#saveUserProfile() with missing attribute in session for incomingRequestURIKey.
     * </p>
     * <p>
     * CompleteProfileActionException exception is expected.
     * </p>
     */
    public void testSaveUserProfile_MissingAttribute() {
        request.getSession().removeAttribute("incomingRequestURIKey");
        try {
            action.saveUserProfile();
            fail("CompleteProfileActionException exception is expected.");
        } catch (CompleteProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#saveUserProfile() with not string attribute value in session for
     * incomingRequestURIKey.
     * </p>
     * <p>
     * CompleteProfileActionException exception is expected.
     * </p>
     */
    public void testSaveUserProfile_InvalidAttribute() {
        request.getSession().setAttribute("incomingRequestURIKey", 1);
        try {
            action.saveUserProfile();
            fail("CompleteProfileActionException exception is expected.");
        } catch (CompleteProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#saveUserProfile() with exception occurs in underlying servlet while redirecting.
     * </p>
     * <p>
     * CompleteProfileActionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testSaveUserProfile_ServletException() throws Exception {
        HttpServletResponse servletResponse = mock(HttpServletResponse.class);
        doThrow(new IOException("just for testing.")).when(servletResponse).sendRedirect(REDIRECTED_URL);
        action.setServletResponse(servletResponse);
        try {
            action.saveUserProfile();
            fail("CompleteProfileActionException exception is expected.");
        } catch (CompleteProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#getUserProfile() with user is not logged in.
     * </p>
     * <p>
     * LOGIN is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetUserProfile_UserNotLoggedIn() throws Exception {
        when(getUserIdRetriever().getUserId(request)).thenReturn(null);
        assertEquals("LOGIN is expected.", ActionSupport.LOGIN, action.getUserProfile());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#getUserProfile() with user not found for given ID.
     * </p>
     * <p>
     * CompleteProfileActionException exception is expected.
     * </p>
     */
    public void testGetUserProfile_UserNotFound() {
        when(getUserDAO().find(1L)).thenReturn(null);
        try {
            action.getUserProfile();
            fail("CompleteProfileActionException exception is expected.");
        } catch (CompleteProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#validte() with invalid state field.
     * </p>
     * <p>
     * Field error should be added.
     * </p>
     */
    public void testValidate_InvalidState() {
        action.setState(state);
        when(MockFactory.getStateDAO().find(state)).thenReturn(null);
        action.validate();
        assertEquals("Field error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#validte() with valid state field, but underlying exception occurs.
     * </p>
     * <p>
     * Action error should be added.
     * </p>
     */
    public void testValidate_StateDAOException() {
        action.setState(state);
        when(MockFactory.getStateDAO().find(state)).thenThrow(new RuntimeException("just for testing."));
        action.validate();
        assertEquals("Action error should be added.", 1, action.getActionErrors().size());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#validte() with invalid phone number. First part is invalid.
     * </p>
     * <p>
     * Field error should be added.
     * </p>
     */
    public void testValidate_InvalidPhoneNumber11() {
        phoneNumber = "(+000 000 000-000";
        action.setPhoneNumber(phoneNumber);
        action.validate();
        assertEquals("Field error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#validte() with invalid phone number. First part is invalid.
     * </p>
     * <p>
     * Field error should be added.
     * </p>
     */
    public void testValidate_InvalidPhoneNumber12() {
        phoneNumber = "000) 000 000-000";
        action.setPhoneNumber(phoneNumber);
        action.validate();
        assertEquals("Field error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#validte() with invalid phone number. First part is invalid.
     * </p>
     * <p>
     * Field error should be added.
     * </p>
     */
    public void testValidate_InvalidPhoneNumber13() {
        phoneNumber = "(+) 000 000-000";
        action.setPhoneNumber(phoneNumber);
        action.validate();
        assertEquals("Field error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#validte() with invalid phone number. First part is invalid.
     * </p>
     * <p>
     * Field error should be added.
     * </p>
     */
    public void testValidate_InvalidPhoneNumber14() {
        phoneNumber = "(+x) 000 000-000";
        action.setPhoneNumber(phoneNumber);
        action.validate();
        assertEquals("Field error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#validte() with invalid phone number. Second part is invalid.
     * </p>
     * <p>
     * Field error should be added.
     * </p>
     */
    public void testValidate_InvalidPhoneNumber21() {
        phoneNumber = "(+000) x 000-000";
        action.setPhoneNumber(phoneNumber);
        action.validate();
        assertEquals("Field error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#validte() with invalid phone number. Third part is invalid.
     * </p>
     * <p>
     * Field error should be added.
     * </p>
     */
    public void testValidate_InvalidPhoneNumber31() {
        phoneNumber = "(+000) 000 -000";
        action.setPhoneNumber(phoneNumber);
        action.validate();
        assertEquals("Field error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#validte() with invalid phone number. Third part is invalid.
     * </p>
     * <p>
     * Field error should be added.
     * </p>
     */
    public void testValidate_InvalidPhoneNumber32() {
        phoneNumber = "(+000) 000 0-";
        action.setPhoneNumber(phoneNumber);
        action.validate();
        assertEquals("Field error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#validte() with invalid phone number. Third part is invalid.
     * </p>
     * <p>
     * Field error should be added.
     * </p>
     */
    public void testValidate_InvalidPhoneNumber33() {
        phoneNumber = "(+000) 000 00";
        action.setPhoneNumber(phoneNumber);
        action.validate();
        assertEquals("Field error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#validte() with invalid phone number. Third part is invalid.
     * </p>
     * <p>
     * Field error should be added.
     * </p>
     */
    public void testValidate_InvalidPhoneNumber34() {
        phoneNumber = "(+000) 000 0-x";
        action.setPhoneNumber(phoneNumber);
        action.validate();
        assertEquals("Field error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#validte() with invalid phone number. Third part is invalid.
     * </p>
     * <p>
     * Field error should be added.
     * </p>
     */
    public void testValidate_InvalidPhoneNumber35() {
        phoneNumber = "(+000) 000 x-0";
        action.setPhoneNumber(phoneNumber);
        action.validate();
        assertEquals("Field error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#validte() with invalid phone number with not exactly 3 parts.
     * </p>
     * <p>
     * Field error should be added.
     * </p>
     */
    public void testValidate_InvalidPhoneNumber_NotEnoughParts() {
        phoneNumber = "(+000) 000";
        action.setPhoneNumber(phoneNumber);
        action.validate();
        assertEquals("Field error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() with null auditDAO.
     * </p>
     * <p>
     * CompleteProfileActionConfigurationException exception is expected.
     * </p>
     */
    public void testCheckConfiguration_Null_AuditDAO() {
        action = getCompleteProfileAction();
        action.setAuditDAO(null);
        try {
            action.checkConfiguration();
            fail("CompleteProfileActionConfigurationException exception is expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() with null userDAO.
     * </p>
     * <p>
     * CompleteProfileActionConfigurationException exception is expected.
     * </p>
     */
    public void testCheckConfiguration_Null_UserDAO() {
        action = getCompleteProfileAction();
        action.setUserDAO(null);
        try {
            action.checkConfiguration();
            fail("CompleteProfileActionConfigurationException exception is expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() with null coderTypeDAO.
     * </p>
     * <p>
     * CompleteProfileActionConfigurationException exception is expected.
     * </p>
     */
    public void testCheckConfiguration_Null_CoderTypeDAO() {
        action = getCompleteProfileAction();
        action.setCoderTypeDAO(null);
        try {
            action.checkConfiguration();
            fail("CompleteProfileActionConfigurationException exception is expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() with null userIdRetriever.
     * </p>
     * <p>
     * CompleteProfileActionConfigurationException exception is expected.
     * </p>
     */
    public void testCheckConfiguration_Null_UserIdRetriever() {
        action = getCompleteProfileAction();
        action.setUserIdRetriever(null);
        try {
            action.checkConfiguration();
            fail("CompleteProfileActionConfigurationException exception is expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() with null timeZoneDAO.
     * </p>
     * <p>
     * CompleteProfileActionConfigurationException exception is expected.
     * </p>
     */
    public void testCheckConfiguration_Null_TimeZoneDAO() {
        action = getCompleteProfileAction();
        action.setTimeZoneDAO(null);
        try {
            action.checkConfiguration();
            fail("CompleteProfileActionConfigurationException exception is expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() with null countryDAO.
     * </p>
     * <p>
     * CompleteProfileActionConfigurationException exception is expected.
     * </p>
     */
    public void testCheckConfiguration_Null_CountryDAO() {
        action = getCompleteProfileAction();
        action.setCountryDAO(null);
        try {
            action.checkConfiguration();
            fail("CompleteProfileActionConfigurationException exception is expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() with null demographicQuestionDAO.
     * </p>
     * <p>
     * CompleteProfileActionConfigurationException exception is expected.
     * </p>
     */
    public void testCheckConfiguration_Null_DemographicQuestionDAO() {
        action = getCompleteProfileAction();
        action.setDemographicQuestionDAO(null);
        try {
            action.checkConfiguration();
            fail("CompleteProfileActionConfigurationException exception is expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() with null stateDAO.
     * </p>
     * <p>
     * CompleteProfileActionConfigurationException exception is expected.
     * </p>
     */
    public void testCheckConfiguration_Null_StateDAO() {
        action = getCompleteProfileAction();
        action.setStateDAO(null);
        try {
            action.checkConfiguration();
            fail("CompleteProfileActionConfigurationException exception is expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() with negative phoneTypeId.
     * </p>
     * <p>
     * CompleteProfileActionConfigurationException exception is expected.
     * </p>
     */
    public void testCheckConfiguration_Negative_PhoneTypeId() {
        action = getCompleteProfileAction();
        action.setPhoneTypeId(-1);
        try {
            action.checkConfiguration();
            fail("CompleteProfileActionConfigurationException exception is expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() with negative securityKeyTypeId.
     * </p>
     * <p>
     * CompleteProfileActionConfigurationException exception is expected.
     * </p>
     */
    public void testCheckConfiguration_Negative_securityKeyTypeId() {
        action = getCompleteProfileAction();
        action.setSecurityKeyTypeId(-1);
        try {
            action.checkConfiguration();
            fail("CompleteProfileActionConfigurationException exception is expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() with negative genderQuestionId.
     * </p>
     * <p>
     * CompleteProfileActionConfigurationException exception is expected.
     * </p>
     */
    public void testCheckConfiguration_Negative_GenderQuestionId() {
        action = getCompleteProfileAction();
        action.setGenderQuestionId(-1);
        try {
            action.checkConfiguration();
            fail("CompleteProfileActionConfigurationException exception is expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() with negative ageQuestionId.
     * </p>
     * <p>
     * CompleteProfileActionConfigurationException exception is expected.
     * </p>
     */
    public void testCheckConfiguration_Negative_AgeQuestionId() {
        action = getCompleteProfileAction();
        action.setAgeQuestionId(-1);
        try {
            action.checkConfiguration();
            fail("CompleteProfileActionConfigurationException exception is expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() with null getUserProfileOperationType.
     * </p>
     * <p>
     * CompleteProfileActionConfigurationException exception is expected.
     * </p>
     */
    public void testCheckConfiguration_Null_GetUserProfileOperationType() {
        action = getCompleteProfileAction();
        action.setGetUserProfileOperationType(null);
        try {
            action.checkConfiguration();
            fail("CompleteProfileActionConfigurationException exception is expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() with null incomingRequestURIKey.
     * </p>
     * <p>
     * CompleteProfileActionConfigurationException exception is expected.
     * </p>
     */
    public void testCheckConfiguration_Null_IncomingRequestURIKey() {
        action = getCompleteProfileAction();
        action.setIncomingRequestURIKey(null);
        try {
            action.checkConfiguration();
            fail("CompleteProfileActionConfigurationException exception is expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() with null saveUserProfileOperationType.
     * </p>
     * <p>
     * CompleteProfileActionConfigurationException exception is expected.
     * </p>
     */
    public void testCheckConfiguration_Null_SaveUserProfileOperationType() {
        action = getCompleteProfileAction();
        action.setSaveUserProfileOperationType(null);
        try {
            action.checkConfiguration();
            fail("CompleteProfileActionConfigurationException exception is expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() with empty getUserProfileOperationType.
     * </p>
     * <p>
     * CompleteProfileActionConfigurationException exception is expected.
     * </p>
     */
    public void testCheckConfiguration_Empty_GetUserProfileOperationType() {
        action = getCompleteProfileAction();
        action.setGetUserProfileOperationType("");
        try {
            action.checkConfiguration();
            fail("CompleteProfileActionConfigurationException exception is expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() with empty incomingRequestURIKey.
     * </p>
     * <p>
     * CompleteProfileActionConfigurationException exception is expected.
     * </p>
     */
    public void testCheckConfiguration_Empty_IncomingRequestURIKey() {
        action = getCompleteProfileAction();
        action.setIncomingRequestURIKey("");
        try {
            action.checkConfiguration();
            fail("CompleteProfileActionConfigurationException exception is expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() with empty saveUserProfileOperationType.
     * </p>
     * <p>
     * CompleteProfileActionConfigurationException exception is expected.
     * </p>
     */
    public void testCheckConfiguration_Empty_SaveUserProfileOperationType() {
        action = getCompleteProfileAction();
        action.setSaveUserProfileOperationType("");
        try {
            action.checkConfiguration();
            fail("CompleteProfileActionConfigurationException exception is expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            // expected
        }
    }
}