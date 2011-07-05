/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.profilecompleteness.actions.failuretests;

import static org.mockito.Mockito.mock;

import javax.servlet.http.HttpServletRequest;

import com.topcoder.reg.profilecompleteness.filter.impl.UserIdRetriever;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.CoderTypeDAO;
import com.topcoder.web.common.dao.CountryDAO;
import com.topcoder.web.common.dao.DemographicQuestionDAO;
import com.topcoder.web.common.dao.StateDAO;
import com.topcoder.web.common.dao.TimeZoneDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.reg.profilecompleteness.actions.CompleteProfileAction;
import com.topcoder.web.reg.profilecompleteness.actions.CompleteProfileActionConfigurationException;
import com.topcoder.web.reg.profilecompleteness.actions.CompleteProfileActionException;
import static org.mockito.Mockito.when;
import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for CompleteProfileAction.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class CompleteProfileActionFailureTests extends TestCase {

    /**
     * <p>
     * The CompleteProfileAction instance for testing.
     * </p>
     */
    private CompleteProfileAction instance;

    /**
     * <p>
     * The UserDAO instance for testing.
     * </p>
     */
    private UserDAO userDAO;

    /**
     * <p>
     * The AuditDAO instance for testing.
     * </p>
     */
    private AuditDAO auditDAO;

    /**
     * <p>
     * The UserIdRetriever instance for testing.
     * </p>
     */
    private UserIdRetriever userIdRetriever;

    /**
     * <p>
     * The CoderTypeDAO instance for testing.
     * </p>
     */
    private CoderTypeDAO coderTypeDAO;

    /**
     * <p>
     * The TimeZoneDAO instance for testing.
     * </p>
     */
    private TimeZoneDAO timeZoneDAO;

    /**
     * <p>
     * The StateDAO instance for testing.
     * </p>
     */
    private StateDAO stateDAO;

    /**
     * <p>
     * The DemographicQuestionDAO instance for testing.
     * </p>
     */
    private DemographicQuestionDAO demographicQuestionDAO;

    /**
     * <p>
     * The CountryDAO instance for testing.
     * </p>
     */
    private CountryDAO countryDAO;

    /**
     * <p>
     * The HttpServletRequest instance for testing.
     * </p>
     */
    private HttpServletRequest request;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new CompleteProfileAction();
        userDAO = mock(UserDAO.class);
        instance.setUserDAO(userDAO);
        auditDAO = mock(AuditDAO.class);
        instance.setAuditDAO(auditDAO);
        userIdRetriever = mock(UserIdRetriever.class);
        instance.setUserIdRetriever(userIdRetriever);
        coderTypeDAO = mock(CoderTypeDAO.class);
        instance.setCoderTypeDAO(coderTypeDAO);
        timeZoneDAO = mock(TimeZoneDAO.class);
        instance.setTimeZoneDAO(timeZoneDAO);
        stateDAO = mock(StateDAO.class);
        instance.setStateDAO(stateDAO);
        demographicQuestionDAO = mock(DemographicQuestionDAO.class);
        instance.setDemographicQuestionDAO(demographicQuestionDAO);
        countryDAO = mock(CountryDAO.class);
        instance.setCountryDAO(countryDAO);
        instance.setPhoneTypeId(1);
        instance.setSecurityKeyTypeId(1);
        instance.setGenderQuestionId(1);
        instance.setAgeQuestionId(1);
        instance.setGetUserProfileOperationType("getUserProfileOperationType");
        instance.setIncomingRequestURIKey("incomingRequestURIKey");
        instance.setSaveUserProfileOperationType("saveUserProfileOperationType");

        request = mock(HttpServletRequest.class);
        instance.setServletRequest(request);
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(CompleteProfileActionFailureTests.class);
    }

    /**
     * <p>
     * Tests CompleteProfileAction#getUserProfile() for failure.
     * Expects CompleteProfileActionException.
     * </p>
     */
    public void testGetUserProfile_CompleteProfileActionException() {
        when(userIdRetriever.getUserId(request)).thenReturn(1L);
        when(userDAO.find(1L)).thenReturn(null);
        try {
            instance.getUserProfile();
            fail("CompleteProfileActionException expected.");
        } catch (CompleteProfileActionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#saveUserProfile() for failure.
     * Expects CompleteProfileActionException.
     * </p>
     */
    public void testSaveUserProfile_CompleteProfileActionException() {
        when(userIdRetriever.getUserId(request)).thenReturn(1L);
        when(userDAO.find(1L)).thenReturn(null);
        try {
            instance.saveUserProfile();
            fail("CompleteProfileActionException expected.");
        } catch (CompleteProfileActionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() for failure.
     * It tests the case that when auditDAO is null and expects CompleteProfileActionConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NullAuditDAO() {
        instance.setAuditDAO(null);
        try {
            instance.checkConfiguration();
            fail("CompleteProfileActionConfigurationException expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() for failure.
     * It tests the case that when userDAO is null and expects CompleteProfileActionConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NullUserDAO() {
        instance.setUserDAO(null);
        try {
            instance.checkConfiguration();
            fail("CompleteProfileActionConfigurationException expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() for failure.
     * It tests the case that when userIdRetriever is null and expects CompleteProfileActionConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NulluserIdRetriever() {
        instance.setUserIdRetriever(null);
        try {
            instance.checkConfiguration();
            fail("CompleteProfileActionConfigurationException expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() for failure.
     * It tests the case that when coderTypeDAO is null and expects CompleteProfileActionConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NullcoderTypeDAO() {
        instance.setCoderTypeDAO(null);
        try {
            instance.checkConfiguration();
            fail("CompleteProfileActionConfigurationException expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() for failure.
     * It tests the case that when timeZoneDAO is null and expects CompleteProfileActionConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NulltimeZoneDAO() {
        instance.setTimeZoneDAO(null);
        try {
            instance.checkConfiguration();
            fail("CompleteProfileActionConfigurationException expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() for failure.
     * It tests the case that when stateDAO is null and expects CompleteProfileActionConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NullstateDAO() {
        instance.setStateDAO(null);
        try {
            instance.checkConfiguration();
            fail("CompleteProfileActionConfigurationException expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() for failure.
     * It tests the case that when demographicQuestionDAO is null and expects CompleteProfileActionConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NulldemographicQuestionDAO() {
        instance.setDemographicQuestionDAO(null);
        try {
            instance.checkConfiguration();
            fail("CompleteProfileActionConfigurationException expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() for failure.
     * It tests the case that when countryDAO is null and expects CompleteProfileActionConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NullcountryDAO() {
        instance.setCountryDAO(null);
        try {
            instance.checkConfiguration();
            fail("CompleteProfileActionConfigurationException expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() for failure.
     * It tests the case that when phoneTypeId is negative and expects CompleteProfileActionConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NegativePhoneTypeId() {
        instance.setPhoneTypeId(-5);
        try {
            instance.checkConfiguration();
            fail("CompleteProfileActionConfigurationException expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() for failure.
     * It tests the case that when securityKeyTypeId is negative and expects CompleteProfileActionConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NegativesecurityKeyTypeId() {
        instance.setSecurityKeyTypeId(-5);
        try {
            instance.checkConfiguration();
            fail("CompleteProfileActionConfigurationException expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() for failure.
     * It tests the case that when genderQuestionId is negative and expects CompleteProfileActionConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NegativegenderQuestionId() {
        instance.setGenderQuestionId(-5);
        try {
            instance.checkConfiguration();
            fail("CompleteProfileActionConfigurationException expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() for failure.
     * It tests the case that when ageQuestionId is negative and expects CompleteProfileActionConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NegativeageQuestionId() {
        instance.setAgeQuestionId(-5);
        try {
            instance.checkConfiguration();
            fail("CompleteProfileActionConfigurationException expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() for failure.
     * It tests the case that when getUserProfileOperationType is null and expects CompleteProfileActionConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NullgetUserProfileOperationType() {
        instance.setGetUserProfileOperationType(null);
        try {
            instance.checkConfiguration();
            fail("CompleteProfileActionConfigurationException expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() for failure.
     * It tests the case that when getUserProfileOperationType is empty and expects CompleteProfileActionConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_EmptygetUserProfileOperationType() {
        instance.setGetUserProfileOperationType(" ");
        try {
            instance.checkConfiguration();
            fail("CompleteProfileActionConfigurationException expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() for failure.
     * It tests the case that when incomingRequestURIKey is null and expects CompleteProfileActionConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NullincomingRequestURIKey() {
        instance.setIncomingRequestURIKey(null);
        try {
            instance.checkConfiguration();
            fail("CompleteProfileActionConfigurationException expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() for failure.
     * It tests the case that when incomingRequestURIKey is empty and expects CompleteProfileActionConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_EmptyincomingRequestURIKey() {
        instance.setIncomingRequestURIKey(" ");
        try {
            instance.checkConfiguration();
            fail("CompleteProfileActionConfigurationException expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() for failure.
     * It tests the case that when saveUserProfileOperationType is null and expects CompleteProfileActionConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NullsaveUserProfileOperationType() {
        instance.setSaveUserProfileOperationType(null);
        try {
            instance.checkConfiguration();
            fail("CompleteProfileActionConfigurationException expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CompleteProfileAction#checkConfiguration() for failure.
     * It tests the case that when saveUserProfileOperationType is empty and expects CompleteProfileActionConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_EmptysaveUserProfileOperationType() {
        instance.setSaveUserProfileOperationType(" ");
        try {
            instance.checkConfiguration();
            fail("CompleteProfileActionConfigurationException expected.");
        } catch (CompleteProfileActionConfigurationException e) {
            //good
        }
    }
}