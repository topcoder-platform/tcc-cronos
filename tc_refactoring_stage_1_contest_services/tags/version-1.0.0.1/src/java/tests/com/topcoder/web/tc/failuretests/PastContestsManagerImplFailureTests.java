/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.failuretests;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.topcoder.web.tc.ContestServicesConfigurationException;
import com.topcoder.web.tc.PastContestsManagerException;
import com.topcoder.web.tc.dto.PastContestFilter;
import com.topcoder.web.tc.impl.PastContestsManagerImpl;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for PastContestsManagerImpl.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class PastContestsManagerImplFailureTests extends TestCase {
    /**
     * <P>
     * The PastContestsManagerImpl instance for testing.
     * </p>
     */
    private PastContestsManagerImpl instance;

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

        instance = new PastContestsManagerImpl();
        instance.setCompletedStatusId(1);
        instance.setProjectNameInfoId(1);
        instance.setContestSubmissionTypeId(1);
        instance.setActiveSubmissionStatusId(1);
        instance.setRegistrationPhaseTypeId(1);
        instance.setSubmissionPhaseTypeId(1);
        instance.setFailedScreeningSubmissionStatusId(1);
        instance.setFailedReviewSubmissionStatusId(1);
        instance.setCompletedWithoutWinSubmissionStatusId(1);
        instance.setSubmitterRoleId(1);
        instance.setApprovalPhaseTypeId(1);
        instance.setPassingScreeningScore(75);
        instance.setWinnerIdInfoId(1);
        instance.setExternalReferenceIdInfoId(1);
        instance.setHandleInfoId(1);
        instance.setWinnerProfileLinkTemplate("TC%id%");
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
        return new TestSuite(PastContestsManagerImplFailureTests.class);
    }

    /**
     * <p>
     * Tests PastContestsManagerImpl#retrievePastContests(PastContestFilter) for failure.
     * It tests the case that when filter is null and expects IllegalArgumentException.
     * </p>
     * @throws PastContestsManagerException to JUnit
     */
    public void testRetrievePastContests1_NullFilter() throws PastContestsManagerException {
        try {
            instance.retrievePastContests(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests PastContestsManagerImpl#retrievePastContests(PastContestFilter) for failure.
     * Expects PastContestsManagerException.
     * </p>
     */
    public void testRetrievePastContests1_PastContestsManagerException() {
        try {
            instance.retrievePastContests(new PastContestFilter());
            fail("PastContestsManagerException expected.");
        } catch (PastContestsManagerException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests PastContestsManagerImpl#retrievePastContests(String,SortingOrder,int,int,PastContestFilter) for failure.
     * It tests the case that when filter is null and expects IllegalArgumentException.
     * </p>
     * @throws PastContestsManagerException to JUnit
     */
    public void testRetrievePastContests2_NullFilter() throws PastContestsManagerException {
        try {
            instance.retrievePastContests(null, null, 1, 1, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests PastContestsManagerImpl#retrievePastContests(String,SortingOrder,int,int,PastContestFilter) for failure.
     * It tests the case that when pageNumber is invalid and expects IllegalArgumentException.
     * </p>
     * @throws PastContestsManagerException to JUnit
     */
    public void testRetrievePastContests2_InvalidpageNumber() throws PastContestsManagerException {
        try {
            instance.retrievePastContests(null, null, -5, 1, new PastContestFilter());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests PastContestsManagerImpl#retrievePastContests(String,SortingOrder,int,int,PastContestFilter) for failure.
     * It tests the case that when pageSize is invalid and expects IllegalArgumentException.
     * </p>
     * @throws PastContestsManagerException to JUnit
     */
    public void testRetrievePastContests2_InvalidpageSize() throws PastContestsManagerException {
        try {
            instance.retrievePastContests(null, null, 1, -5, new PastContestFilter());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests PastContestsManagerImpl#retrievePastContests(String,SortingOrder,int,int,PastContestFilter) for failure.
     * Expects PastContestsManagerException.
     * </p>
     */
    public void testRetrievePastContests2_PastContestsManagerException() {
        try {
            instance.retrievePastContests(null, null, 1, 1, new PastContestFilter());
            fail("PastContestsManagerException expected.");
        } catch (PastContestsManagerException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests PastContestsManagerImpl#checkConfiguration() for failure.
     * It tests the case that when winnerProfileLinkTemplate is null and expects ContestServicesConfigurationException.
     * </p>
     */
    public void testcheckConfiguration_NullWinnerProfileLinkTemplate() {
        try {
            instance.setWinnerProfileLinkTemplate(null);
            instance.checkConfiguration();
            fail("ContestServicesConfigurationException expected.");
        } catch (ContestServicesConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests PastContestsManagerImpl#checkConfiguration() for failure.
     * It tests the case that when winnerProfileLinkTemplate is empty and expects ContestServicesConfigurationException.
     * </p>
     */
    public void testcheckConfiguration_EmptyWinnerProfileLinkTemplate() {
        try {
            instance.setWinnerProfileLinkTemplate(" ");
            instance.checkConfiguration();
            fail("ContestServicesConfigurationException expected.");
        } catch (ContestServicesConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests PastContestsManagerImpl#checkConfiguration() for failure.
     * It tests the case that when winnerProfileLinkTemplate is invalid and expects ContestServicesConfigurationException.
     * </p>
     */
    public void testcheckConfiguration_InvalidWinnerProfileLinkTemplate() {
        try {
            instance.setWinnerProfileLinkTemplate("TC");
            instance.checkConfiguration();
            fail("ContestServicesConfigurationException expected.");
        } catch (ContestServicesConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests PastContestsManagerImpl#checkConfiguration() for failure.
     * It tests the case that when completedStatusId is invalid and expects ContestServicesConfigurationException.
     * </p>
     */
    public void testcheckConfiguration_InvalidcompletedStatusId() {
        try {
            instance.setCompletedStatusId(-5);
            instance.checkConfiguration();
            fail("ContestServicesConfigurationException expected.");
        } catch (ContestServicesConfigurationException e) {
            //good
        }
    }

}