/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.failuretests;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.topcoder.web.tc.ActiveContestsManagerException;
import com.topcoder.web.tc.ContestServicesConfigurationException;
import com.topcoder.web.tc.dto.ActiveContestFilter;
import com.topcoder.web.tc.impl.ActiveContestsManagerImpl;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for ActiveContestsManagerImpl.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class ActiveContestsManagerImplFailureTests extends TestCase {
    /**
     * <P>
     * The ActiveContestsManagerImpl instance for testing.
     * </p>
     */
    private ActiveContestsManagerImpl instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("test_files/failure/ApplicationContext.xml");
        SessionFactory sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
        HibernateTemplate hibernateTemplate = new HibernateTemplate(sessionFactory);

        instance = new ActiveContestsManagerImpl();
        instance.setActiveStatusId(1);
        instance.setProjectNameInfoId(1);
        instance.setContestSubmissionTypeId(1);
        instance.setActiveSubmissionStatusId(1);
        instance.setFailedScreeningSubmissionStatusId(1);
        instance.setFailedReviewSubmissionStatusId(1);
        instance.setCompletedWithoutWinSubmissionStatusId(1);
        instance.setSubmitterRoleId(1);
        instance.setRegistrationPhaseTypeId(1);
        instance.setSubmissionPhaseTypeId(1);
        instance.setFinalReviewPhaseTypeId(1);
        instance.setFirstPlaceCostInfoId(1);
        instance.setReliabilityBonusCostInfoId(1);
        instance.setDigitalRunPointInfoId(1);
        instance.setPaymentsInfoId(1);
        instance.setDigitalRunFlagInfoId(1);
        instance.setOpenPhaseStatusId(1);
        instance.setHibernateTemplate(hibernateTemplate);
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ActiveContestsManagerImplFailureTests.class);
    }

    /**
     * <p>
     * Tests ActiveContestsManagerImpl#retrieveActiveContests(ActiveContestFilter) for failure.
     * It tests the case that when filter is null and expects IllegalArgumentException.
     * </p>
     * @throws ActiveContestsManagerException to JUnit
     */
    public void testRetrieveActiveContests1_NullFilter() throws ActiveContestsManagerException {
        try {
            instance.retrieveActiveContests(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ActiveContestsManagerImpl#retrieveActiveContests(ActiveContestFilter) for failure.
     * Expects ActiveContestsManagerException.
     * </p>
     */
    public void testRetrieveActiveContests1_ActiveContestsManagerException() {
        try {
            instance.retrieveActiveContests(new ActiveContestFilter());
            fail("ActiveContestsManagerException expected.");
        } catch (ActiveContestsManagerException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ActiveContestsManagerImpl#retrieveActiveContests(String,SortingOrder,int,int,ActiveContestFilter) for failure.
     * It tests the case that when filter is null and expects IllegalArgumentException.
     * </p>
     * @throws ActiveContestsManagerException to JUnit
     */
    public void testRetrieveActiveContests2_NullFilter() throws ActiveContestsManagerException {
        try {
            instance.retrieveActiveContests(null, null, 1, 1, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ActiveContestsManagerImpl#retrieveActiveContests(String,SortingOrder,int,int,ActiveContestFilter) for failure.
     * It tests the case that when pageNumber is invalid and expects IllegalArgumentException.
     * </p>
     * @throws ActiveContestsManagerException to JUnit
     */
    public void testRetrieveActiveContests2_InvalidpageNumber() throws ActiveContestsManagerException {
        try {
            instance.retrieveActiveContests(null, null, -10, 1, new ActiveContestFilter());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ActiveContestsManagerImpl#retrieveActiveContests(String,SortingOrder,int,int,ActiveContestFilter) for failure.
     * It tests the case that when pageSize is invalid and expects IllegalArgumentException.
     * </p>
     * @throws ActiveContestsManagerException to JUnit
     */
    public void testRetrieveActiveContests2_InvalidpageSize() throws ActiveContestsManagerException {
        try {
            instance.retrieveActiveContests(null, null, 1, -81, new ActiveContestFilter());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ActiveContestsManagerImpl#retrieveActiveContests(String,SortingOrder,int,int,ActiveContestFilter) for failure.
     * Expects ActiveContestsManagerException.
     * </p>
     */
    public void testRetrieveActiveContests2_ActiveContestsManagerException() {
        try {
            instance.retrieveActiveContests(null, null, 1, 1, new ActiveContestFilter());
            fail("ActiveContestsManagerException expected.");
        } catch (ActiveContestsManagerException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ActiveContestsManagerImpl#checkConfiguration() for failure.
     * It tests the case that when activeSubmissionStatusId is invalid and expects ContestServicesConfigurationException.
     * </p>
     * @throws ActiveContestsManagerException to JUnit
     */
    public void testcheckConfiguration_InvalidactiveSubmissionStatusId() {
        try {
            instance.setActiveSubmissionStatusId(-5);
            instance.checkConfiguration();
            fail("ContestServicesConfigurationException expected.");
        } catch (ContestServicesConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ActiveContestsManagerImpl#checkConfiguration() for failure.
     * It tests the case that when completedWithoutWinSubmissionStatusId is invalid and expects ContestServicesConfigurationException.
     * </p>
     * @throws ActiveContestsManagerException to JUnit
     */
    public void testcheckConfiguration_InvalidcompletedWithoutWinSubmissionStatusId() {
        try {
            instance.setCompletedWithoutWinSubmissionStatusId(-5);
            instance.checkConfiguration();
            fail("ContestServicesConfigurationException expected.");
        } catch (ContestServicesConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ActiveContestsManagerImpl#checkConfiguration() for failure.
     * It tests the case that when digitalRunFlagInfoId is invalid and expects ContestServicesConfigurationException.
     * </p>
     * @throws ActiveContestsManagerException to JUnit
     */
    public void testcheckConfiguration_InvaliddigitalRunFlagInfoId() {
        try {
            instance.setDigitalRunFlagInfoId(-5);
            instance.checkConfiguration();
            fail("ContestServicesConfigurationException expected.");
        } catch (ContestServicesConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ActiveContestsManagerImpl#checkConfiguration() for failure.
     * It tests the case that when failedReviewSubmissionStatusId is invalid and expects ContestServicesConfigurationException.
     * </p>
     * @throws ActiveContestsManagerException to JUnit
     */
    public void testcheckConfiguration_InvalidfailedReviewSubmissionStatusId() {
        try {
            instance.setFailedReviewSubmissionStatusId(-5);
            instance.checkConfiguration();
            fail("ContestServicesConfigurationException expected.");
        } catch (ContestServicesConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ActiveContestsManagerImpl#checkConfiguration() for failure.
     * It tests the case that when openPhaseStatusId is invalid and expects ContestServicesConfigurationException.
     * </p>
     * @throws ActiveContestsManagerException to JUnit
     */
    public void testcheckConfiguration_InvalidopenPhaseStatusId() {
        try {
            instance.setOpenPhaseStatusId(-5);
            instance.checkConfiguration();
            fail("ContestServicesConfigurationException expected.");
        } catch (ContestServicesConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ActiveContestsManagerImpl#checkConfiguration() for failure.
     * It tests the case that when paymentsInfoId is invalid and expects ContestServicesConfigurationException.
     * </p>
     * @throws ActiveContestsManagerException to JUnit
     */
    public void testcheckConfiguration_InvalidpaymentsInfoId() {
        try {
            instance.setPaymentsInfoId(-5);
            instance.checkConfiguration();
            fail("ContestServicesConfigurationException expected.");
        } catch (ContestServicesConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ActiveContestsManagerImpl#checkConfiguration() for failure.
     * It tests the case that when registrationPhaseTypeId is invalid and expects ContestServicesConfigurationException.
     * </p>
     * @throws ActiveContestsManagerException to JUnit
     */
    public void testcheckConfiguration_InvalidregistrationPhaseTypeId() {
        try {
            instance.setRegistrationPhaseTypeId(-5);
            instance.checkConfiguration();
            fail("ContestServicesConfigurationException expected.");
        } catch (ContestServicesConfigurationException e) {
            //good
        }
    }

}