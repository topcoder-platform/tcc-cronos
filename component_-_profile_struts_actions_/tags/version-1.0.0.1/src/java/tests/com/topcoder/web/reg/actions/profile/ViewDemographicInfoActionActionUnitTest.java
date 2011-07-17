/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.web.common.dao.DemographicQuestionDAO;
import com.topcoder.web.common.model.DemographicQuestion;
import com.topcoder.web.reg.BaseUnitTest;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.ProfileActionException;
import com.topcoder.web.reg.mock.MockFactory;

/**
 * <p>
 * This class contains Unit tests for ViewDemographicInfoAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ViewDemographicInfoActionActionUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents ViewDemographicInfoAction instance for testing.
     * </p>
     */
    private ViewDemographicInfoAction action;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        MockFactory.initDAOs();
        action = (ViewDemographicInfoAction) getBean("viewDemographicInfoAction");
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
        action = null;
    }

    /**
     * <p>
     * Tests ViewDemographicInfoAction constructor.
     * </p>
     * <p>
     * ViewDemographicInfoAction instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("ViewDemographicInfoAction instance should be created successfully.", action);
    }

    /**
     * <p>
     * Tests ViewDemographicInfoAction#checkInitialization() method with valid fields.
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
     * Tests ViewDemographicInfoAction#checkInitialization() method with null demographicQuestionDAO.
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
     * Tests ViewDemographicInfoAction#processOutputData(User) method with valid fields and passed user.
     * </p>
     * <p>
     * Displayed user should be changed. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testProcessOutputData() throws Exception {
        com.topcoder.web.common.model.User user = MockFactory.createUser(1L, "first name", "last name", "handle");
        action.processOutputData(user);
        assertEquals("Displayed user should be changed.", user, action.getDisplayedUser());
    }

    /**
     * <p>
     * Tests ViewDemographicInfoAction#performAdditionalTasks(User) method with valid fields and passed user.
     * </p>
     * <p>
     * Questions should be set successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPerformAdditionalTasks() throws Exception {
        com.topcoder.web.common.model.User user = MockFactory.createUser(1L, "first name", "last name", "handle");
        action.performAdditionalTasks(user);
        assertEquals("Questions should be set successfully.", 4, action.getDemographicQuestions().size());
    }

    /**
     * <p>
     * Tests ViewDemographicInfoAction#performAdditionalTasks(User) method with valid fields and passed user, but
     * exception occurred in underlying DAO.
     * </p>
     * <p>
     * ProfileActionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPerformAdditionalTasks_Exception() throws Exception {
        com.topcoder.web.common.model.User user = MockFactory.createUser(1L, "first name", "last name", "handle");
        when(action.getDemographicQuestionDAO().getQuestions()).thenThrow(new RuntimeException("just for testing."));
        try {
            action.performAdditionalTasks(user);
            fail("ProfileActionException exception is expected.");
        } catch (ProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests ViewDemographicInfoAction#getDemographicQuestionDAO() method.
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
     * Tests ViewDemographicInfoAction#setDemographicQuestionDAO(DemographicQuestionDAO) method.
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

    /**
     * <p>
     * Tests ViewDemographicInfoAction#getDemographicQuestions() method.
     * </p>
     * <p>
     * demographicQuestions should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetDemographicQuestions() {
        List<DemographicQuestion> demographicQuestions = new ArrayList<DemographicQuestion>();
        action.setDemographicQuestions(demographicQuestions);
        assertSame("getDemographicQuestions() doesn't work properly.", demographicQuestions,
                action.getDemographicQuestions());
    }

    /**
     * <p>
     * Tests ViewDemographicInfoAction#setDemographicQuestions(List) method.
     * </p>
     * <p>
     * demographicQuestions should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetDemographicQuestions() {
        List<DemographicQuestion> demographicQuestions = new ArrayList<DemographicQuestion>();
        action.setDemographicQuestions(demographicQuestions);
        assertSame("setDemographicQuestions() doesn't work properly.", demographicQuestions,
                action.getDemographicQuestions());
    }
}
