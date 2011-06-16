/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.failuretests;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.topcoder.web.tc.ContestServicesConfigurationException;
import com.topcoder.web.tc.ContestStatusManagerException;
import com.topcoder.web.tc.dto.ContestStatusFilter;
import com.topcoder.web.tc.impl.ContestStatusManagerImpl;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for ContestStatusManagerImpl.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class ContestStatusManagerImplFailureTests extends TestCase {
    /**
     * <P>
     * The ContestStatusManagerImpl instance for testing.
     * </p>
     */
    private ContestStatusManagerImpl instance;

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

        instance = new ContestStatusManagerImpl();
        instance.setProjectNameInfoId(1);
        instance.setRegistrationPhaseTypeId(1);
        instance.setSubmissionPhaseTypeId(1);
        instance.setFinalReviewPhaseTypeId(1);
        instance.setOpenPhaseStatusId(1);
        instance.setWinnerIdInfoId(1);
        instance.setExternalReferenceIdInfoId(1);
        instance.setHandleInfoId(1);
        instance.setSecondPlaceIdInfoId(1);
        instance.setFirstPlaceCostInfoId(1);
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
        return new TestSuite(ContestStatusManagerImplFailureTests.class);
    }

    /**
     * <p>
     * Tests ContestStatusManagerImpl#retrieveContestStatuses(ContestStatusFilter) for failure.
     * It tests the case that when filter is null and expects IllegalArgumentException.
     * </p>
     * @throws ContestStatusManagerException to JUnit
     */
    public void testRetrieveContestStatuses1_NullFilter() throws ContestStatusManagerException {
        try {
            instance.retrieveContestStatuses(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ContestStatusManagerImpl#retrieveContestStatuses(ContestStatusFilter) for failure.
     * Expects ContestStatusManagerException.
     * </p>
     */
    public void testRetrieveContestStatuses1_ContestStatusManagerException() {
        try {
            instance.retrieveContestStatuses(new ContestStatusFilter());
            fail("ContestStatusManagerException expected.");
        } catch (ContestStatusManagerException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ContestStatusManagerImpl#retrieveContestStatuses(String,SortingOrder,int,int,ContestStatusFilter) for failure.
     * It tests the case that when filter is null and expects IllegalArgumentException.
     * </p>
     * @throws ContestStatusManagerException to JUnit
     */
    public void testRetrieveContestStatuses2_NullFilter() throws ContestStatusManagerException {
        try {
            instance.retrieveContestStatuses(null, null, 1, 1, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ContestStatusManagerImpl#retrieveContestStatuses(String,SortingOrder,int,int,ContestStatusFilter) for failure.
     * It tests the case that when pageNumber is invalid and expects IllegalArgumentException.
     * </p>
     * @throws ContestStatusManagerException to JUnit
     */
    public void testRetrieveContestStatuses2_InvalidpageNumber() throws ContestStatusManagerException {
        try {
            instance.retrieveContestStatuses(null, null, -5, 1, new ContestStatusFilter());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ContestStatusManagerImpl#retrieveContestStatuses(String,SortingOrder,int,int,ContestStatusFilter) for failure.
     * It tests the case that when pageSize is invalid and expects IllegalArgumentException.
     * </p>
     * @throws ContestStatusManagerException to JUnit
     */
    public void testRetrieveContestStatuses2_InvalidpageSize() throws ContestStatusManagerException {
        try {
            instance.retrieveContestStatuses(null, null, 1, -5, new ContestStatusFilter());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ContestStatusManagerImpl#retrieveContestStatuses(String,SortingOrder,int,int,ContestStatusFilter) for failure.
     * Expects ContestStatusManagerException.
     * </p>
     */
    public void testRetrieveContestStatuses2_ContestStatusManagerException() {
        try {
            instance.retrieveContestStatuses(null, null, 1, 1, new ContestStatusFilter());
            fail("ContestStatusManagerException expected.");
        } catch (ContestStatusManagerException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ContestStatusManagerImpl#checkConfiguration() for failure.
     * It tests the case that when externalReferenceIdInfoId is invalid and expects ContestServicesConfigurationException.
     * </p>
     */
    public void testcheckConfiguration_InvalidexternalReferenceIdInfoId() {
        try {
            instance.setExternalReferenceIdInfoId(-5);
            instance.checkConfiguration();
            fail("ContestServicesConfigurationException expected.");
        } catch (ContestServicesConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ContestStatusManagerImpl#checkConfiguration() for failure.
     * It tests the case that when finalReviewPhaseTypeId is invalid and expects ContestServicesConfigurationException.
     * </p>
     */
    public void testcheckConfiguration_InvalidfinalReviewPhaseTypeId() {
        try {
            instance.setFinalReviewPhaseTypeId(-5);
            instance.checkConfiguration();
            fail("ContestServicesConfigurationException expected.");
        } catch (ContestServicesConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ContestStatusManagerImpl#checkConfiguration() for failure.
     * It tests the case that when handleInfoId is invalid and expects ContestServicesConfigurationException.
     * </p>
     */
    public void testcheckConfiguration_InvalidhandleInfoId() {
        try {
            instance.setHandleInfoId(-5);
            instance.checkConfiguration();
            fail("ContestServicesConfigurationException expected.");
        } catch (ContestServicesConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ContestStatusManagerImpl#checkConfiguration() for failure.
     * It tests the case that when openPhaseStatusId is invalid and expects ContestServicesConfigurationException.
     * </p>
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
}