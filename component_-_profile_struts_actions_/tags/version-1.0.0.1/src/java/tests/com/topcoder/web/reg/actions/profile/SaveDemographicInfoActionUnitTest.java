/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.HashSet;

import com.topcoder.web.common.dao.DemographicQuestionDAO;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.model.DemographicResponse;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.BaseUnitTest;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.mock.MockFactory;

/**
 * <p>
 * This class contains Unit tests for SaveDemographicInfoAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SaveDemographicInfoActionUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents action name for testing.
     * </p>
     */
    private static final String ACTION_NAME = "/saveDemographicInfoAction";

    /**
     * <p>
     * Represents SaveDemographicInfoAction instance for testing.
     * </p>
     */
    private SaveDemographicInfoAction action;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        MockFactory.initDAOs();
        action = (SaveDemographicInfoAction) getActionProxy(ACTION_NAME).getAction();
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
        MockFactory.initDAOs();
        action.clearFieldErrors();
        action = null;
    }

    /**
     * <p>
     * Tests SaveDemographicInfoAction constructor.
     * </p>
     * <p>
     * SaveDemographicInfoAction instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("SaveDemographicInfoAction instance should be created successfully.", action);
    }

    /**
     * <p>
     * Tests SaveDemographicInfoAction#checkInitialization() method with valid fields.
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
     * Tests SaveDemographicInfoAction#checkInitialization() method with null demographicQuestionDAO.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization_Null_DemographicQuestionDAO() throws Exception {
        action.setDemographicQuestionDAO(null);
        try {
            action.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests SaveDemographicInfoAction#processInputData() method with valid fields and user doesn't have any primary
     * addresses.
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
        // clear all primary values
        User user = MockFactory.createUser(id, "first name", "last name", "handle");
        when(action.getUserDAO().find(id)).thenReturn(user);
        User savedUser = MockFactory.createUser(id, "first name", "last name", "handle");
        // new values
        savedUser.setDemographicResponses(MockFactory.getDemographicResponses());
        action.setSavedUser(savedUser);
        // process input data
        action.processInputData(user);
        assertEquals("Demographic responses should be set.", 4, user.getDemographicResponses().size());
        verify(action.getAuditDAO(), times(1)).audit(any(AuditRecord.class));
    }

    /**
     * <p>
     * Tests SaveDemographicInfoAction#processInputData() method with valid fields and user has all primary addresses.
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
        User user = MockFactory.createUser(id, "first name", "last name", "handle");
        when(action.getUserDAO().find(id)).thenReturn(user);
        User savedUser = MockFactory.createUser(id, "first name", "last name", "handle");
        // new values
        savedUser.setDemographicResponses(MockFactory.getDemographicResponses());
        user.setDemographicResponses(MockFactory.getDemographicResponses());
        action.setSavedUser(savedUser);
        // process input data
        action.processInputData(user);
        verify(action.getAuditDAO(), times(1)).audit(any(AuditRecord.class));
    }

    /**
     * <p>
     * Tests SaveDemographicInfoAction#validate() method with valid fields.
     * </p>
     * <p>
     * Validation should ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate() throws Exception {
        action.setServletRequest(MockFactory.createServletRequest());
        long id = 1L;
        User savedUser = MockFactory.createUser(id, "first name", "last name", "handle");
        // new values
        savedUser.setDemographicResponses(MockFactory.getDemographicResponses());
        action.setSavedUser(savedUser);
        // process input data
        action.validate();
    }

    /**
     * <p>
     * Tests SaveDemographicInfoAction#validate() method with invalid GPA.
     * </p>
     * <p>
     * Field error should be added successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_InvalidGPA() throws Exception {
        action.setServletRequest(MockFactory.createServletRequest());
        long id = 1L;
        User savedUser = MockFactory.createUser(id, "first name", "last name", "handle");
        // new values
        savedUser.setDemographicResponses(new HashSet<DemographicResponse>());
        // add invalid GPA
        savedUser.addDemographicResponse(MockFactory.createDemographicResponse(1, "GPA", new String[] {"Unknown"},
                "Unknown"));
        action.setSavedUser(savedUser);
        // process input data
        action.validate();
        assertNotNull("Field error should be added.", action.getFieldErrors().get("GPA"));
    }

    /**
     * <p>
     * Tests SaveDemographicInfoAction#validate() method with invalid college description.
     * </p>
     * <p>
     * Field error should be added successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_InvalidCollegeDescription() throws Exception {
        action.setServletRequest(MockFactory.createServletRequest());
        String value = "Unknown";
        // generate long string value
        for (int i = 0; i < 10; i++) {
            value += value;
        }
        long id = 1L;
        User savedUser = MockFactory.createUser(id, "first name", "last name", "handle");
        // new values
        savedUser.setDemographicResponses(new HashSet<DemographicResponse>());
        // add invalid GPA
        savedUser.addDemographicResponse(MockFactory.createDemographicResponse(1, "College Major Description",
                new String[] {value}, value));
        action.setSavedUser(savedUser);
        // process input data
        action.validate();
        assertNotNull("Field error should be added.", action.getFieldErrors().get("College Major Description"));
    }

    /**
     * <p>
     * Tests SaveDemographicInfoAction#validate() method with invalid field length.
     * </p>
     * <p>
     * Field error should be added successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_InvalidFieldLength() throws Exception {
        action.setServletRequest(MockFactory.createServletRequest());
        String value = "Unknown";
        // generate long string value
        for (int i = 0; i < 10; i++) {
            value += value;
        }
        long id = 1L;
        User savedUser = MockFactory.createUser(id, "first name", "last name", "handle");
        // new values
        savedUser.setDemographicResponses(new HashSet<DemographicResponse>());
        // add invalid GPA
        savedUser.addDemographicResponse(MockFactory.createDemographicResponse(1, "Age", new String[] {value}, value));
        action.setSavedUser(savedUser);
        // process input data
        action.validate();
        assertNotNull("Field error should be added.", action.getFieldErrors().get("Age"));
    }

    /**
     * <p>
     * Tests SaveDemographicInfoAction#generateEmailTemplateData(User) method with valid fields and user passed.
     * </p>
     * <p>
     * Template should be retrieved successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGenerateEmailTemplate() throws Exception {
        long id = 1L;
        // clear all primary values
        User user = MockFactory.createUser(id, "first name", "last name", "handle");
        // new values
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
     * Tests SaveDemographicInfoAction#getDemographicQuestionDAO() method.
     * </p>
     * <p>
     * demographicQuestionDAO should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetDemographicQuestionDAO() {
        DemographicQuestionDAO demographicQuestionDAO = MockFactory.getDemographicQuestionDAO();
        action.setDemographicQuestionDAO(demographicQuestionDAO);
        assertSame("getDemographicQuestionDAO() doesn't work properly.", demographicQuestionDAO,
                action.getDemographicQuestionDAO());
    }

    /**
     * <p>
     * Tests SaveDemographicInfoAction#setDemographicQuestionDAO(DemographicQuestionDAO) method.
     * </p>
     * <p>
     * demographicQuestionDAO should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetDemographicQuestionDAO() {
        DemographicQuestionDAO demographicQuestionDAO = MockFactory.getDemographicQuestionDAO();
        action.setDemographicQuestionDAO(demographicQuestionDAO);
        assertSame("setDemographicQuestionDAO() doesn't work properly.", demographicQuestionDAO,
                action.getDemographicQuestionDAO());
    }
}
