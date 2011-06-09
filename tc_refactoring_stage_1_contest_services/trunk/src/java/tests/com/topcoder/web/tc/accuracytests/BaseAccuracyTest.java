/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.topcoder.web.tc.dto.ActiveContestDTO;
import com.topcoder.web.tc.dto.ContestStatusDTO;
import com.topcoder.web.tc.dto.PastContestDTO;
import com.topcoder.web.tc.impl.ActiveContestsManagerImpl;
import com.topcoder.web.tc.impl.ContestStatusManagerImpl;
import com.topcoder.web.tc.impl.PastContestsManagerImpl;

/**
 * <p>
 * This is base Accuracy class for this component.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class BaseAccuracyTest {

    /**
     * <p>
     * Represents hibernate template bean name in context.
     * </p>
     */
    private static final String HIBERNATE_TEMPLATE_BEAN_NAME = "hibernateTemplateBean";

    /**
     * <p>
     * Represents application context path.
     * </p>
     */
    private static final String APPLICATION_CONTEXT_PATH = System.getProperty("user.dir") + File.separator
            + "test_files" + File.separator + "accuracytests" + File.separator + "applicationContext.xml";

    /**
     * <p>
     * Represents delta constant for comparing doubles for testing.
     * </p>
     */
    protected static final double DELTA = 1e-5;

    /**
     * <p>
     * Represents HibernateTemplate instance for testing.
     * </p>
     */
    private HibernateTemplate hibernateTemplate;

    /**
     * <p>
     * Represents ApplicationContext instance for testing.
     * </p>
     */
    private ApplicationContext applicationContext;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        applicationContext = new FileSystemXmlApplicationContext(APPLICATION_CONTEXT_PATH);
        hibernateTemplate = (HibernateTemplate) applicationContext.getBean(HIBERNATE_TEMPLATE_BEAN_NAME);
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     */
    @After
    public void tearDown() {
        hibernateTemplate = null;
        applicationContext = null;
    }

    /**
     * <p>
     * Retrieves HibernateTemplate instance from context.
     * </p>
     * @return HibernateTemplate instance from context
     */
    protected HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    /**
     * <p>
     * Checks whether given project has valid values.
     * </p>
     * @param project the project to check
     */
    protected void checkActiveProject1(ActiveContestDTO project) {
        assertEquals("First prize should be retrieved successfully.", 500.0, project.getFirstPrize(), DELTA);
        assertEquals("Contest name should be retrieved successfully.", "Project1", project.getContestName());
        assertEquals("Digital run points should be retrieved successfully.", 225.0, project.getDigitalRunPoints(),
                DELTA);
        assertEquals("Type should be retrieved successfully.", "Design", project.getType());
        assertEquals("Sub should be retrieved successfully.", "Architecture", project.getSubType());
        assertEquals("Number of registrants should be retrieved successfully.", 3, project.getNumberOfRegistrants());
        assertEquals("Number of submissions should be retrieved successfully.", 4, project.getNumberOfSubmissions());
    }

    /**
     * <p>
     * Checks whether given project has valid values.
     * </p>
     * @param project the project to check
     */
    protected void checkActiveProject2(ActiveContestDTO project) {
        assertEquals("First prize should be retrieved successfully.", 800.0, project.getFirstPrize(), DELTA);
        assertEquals("Contest name should be retrieved successfully.", "Project2", project.getContestName());
        assertNull("Digital run points should be retrieved successfully.", project.getDigitalRunPoints());
        assertEquals("Type should be retrieved successfully.", "Design", project.getType());
        assertEquals("Sub should be retrieved successfully.", "Component Design", project.getSubType());
        assertEquals("Number of registrants should be retrieved successfully.", 1, project.getNumberOfRegistrants());
        assertEquals("Number of submissions should be retrieved successfully.", 1, project.getNumberOfSubmissions());
    }

    /**
     * <p>
     * Checks whether given project has valid values.
     * </p>
     * @param project the project to check
     */
    protected void checkPastProject3(PastContestDTO project) {
        assertEquals("Winner score should be retrieved successfully.", 85.0, project.getWinnerScore(), DELTA);
        assertEquals("Contest name should be retrieved successfully.", "Project3", project.getContestName());
        assertEquals("Type should be retrieved successfully.", "Design", project.getType());
        assertEquals("Sub should be retrieved successfully.", "Architecture", project.getSubType());
        assertEquals("Number of registrants should be retrieved successfully.", 1, project.getNumberOfRegistrants());
        assertEquals("Number of submissions should be retrieved successfully.", 1, project.getNumberOfSubmissions());
        assertEquals("Number of passed screening should be retrieved successfully.", 1,
                project.getPassedScreeningCount());
        assertEquals("Winner link should be retrieved successfully.", "topcode.com/handle=winnerProject3",
                project.getWinnerProfileLink());
        assertEquals("Completion date should be retrieved successfully.",
                new GregorianCalendar(2010, 4, 15, 12, 0, 0).getTime(), project.getCompletionDate());
    }

    /**
     * <p>
     * Checks whether given project has valid values.
     * </p>
     * @param project the project to check
     */
    protected void checkPastProject4(PastContestDTO project) {
        assertEquals("Winner score should be retrieved successfully.", 90.0, project.getWinnerScore(), DELTA);
        assertEquals("Contest name should be retrieved successfully.", "Project4", project.getContestName());
        assertEquals("Type should be retrieved successfully.", "Design", project.getType());
        assertEquals("Sub should be retrieved successfully.", "Component Design", project.getSubType());
        assertEquals("Number of registrants should be retrieved successfully.", 2, project.getNumberOfRegistrants());
        assertEquals("Number of submissions should be retrieved successfully.", 2, project.getNumberOfSubmissions());
        assertEquals("Number of passed screening should be retrieved successfully.", 1,
                project.getPassedScreeningCount());
        assertEquals("Winner link should be retrieved successfully.", "topcode.com/handle=winnerProject4",
                project.getWinnerProfileLink());
        assertEquals("Completion date should be retrieved successfully.",
                new GregorianCalendar(2011, 4, 15, 12, 0, 0).getTime(), project.getCompletionDate());
    }

    /**
     * <p>
     * Checks whether given project has valid values.
     * </p>
     * @param project the project to check
     */
    protected void checkProjectStatus1(ContestStatusDTO project) {
        assertEquals("Type should be retrieved successfully.", "Design", project.getType());
        assertEquals("Sub should be retrieved successfully.", "Architecture", project.getSubType());
        assertNull("Winner handle be retrieved successfully.", project.getFirstPlaceHandle());
        assertNull("Second place handle be retrieved successfully.", project.getSecondPlaceHandle());
        assertEquals("Current be retrieved successfully.", "Submission", project.getCurrentPhase());
        assertEquals("Submission due date be retrieved successfully.",
                new GregorianCalendar(2011, 4, 15, 12, 0).getTime(), project.getSubmissionDueDate());
        assertEquals("Final review due date be retrieved successfully.",
                new GregorianCalendar(2011, 4, 15, 12, 0).getTime(), project.getFinalReviewDueDate());
    }

    /**
     * <p>
     * Checks whether given project has valid values.
     * </p>
     * @param project the project to check
     */
    protected void checkProjectStatus2(ContestStatusDTO project) {
        assertEquals("Type should be retrieved successfully.", "Design", project.getType());
        assertEquals("Sub should be retrieved successfully.", "Component Design", project.getSubType());
        assertEquals("Winner handle be retrieved successfully.", "winnerHandle11", project.getFirstPlaceHandle());
        assertNull("Second place handle be retrieved successfully.", project.getSecondPlaceHandle());
        assertEquals("Current be retrieved successfully.", "Registration", project.getCurrentPhase());
        assertEquals("Submission due date be retrieved successfully.",
                new GregorianCalendar(2011, 4, 15, 12, 0).getTime(), project.getSubmissionDueDate());
        assertEquals("Final review due date be retrieved successfully.",
                new GregorianCalendar(2011, 4, 15, 12, 0).getTime(), project.getFinalReviewDueDate());
    }
    /**
     * <p>
     * Creates ActiveContestsManagerImpl instance with predefined properties.
     * </p>
     * @return created ActiveContestsManagerImpl instance with predefined properties
     */
    protected ActiveContestsManagerImpl createActiveContestsManagerImpl() {
        ActiveContestsManagerImpl activeContestsManager = new ActiveContestsManagerImpl();
        // Project status
        activeContestsManager.setActiveStatusId(1);
        // Submission statuses
        activeContestsManager.setActiveSubmissionStatusId(1);
        activeContestsManager.setFailedScreeningSubmissionStatusId(2);
        activeContestsManager.setFailedReviewSubmissionStatusId(3);
        activeContestsManager.setCompletedWithoutWinSubmissionStatusId(4);
        activeContestsManager.setContestSubmissionTypeId(1);
        // ProjectPhase table
        activeContestsManager.setRegistrationPhaseTypeId(1);
        activeContestsManager.setSubmissionPhaseTypeId(11);
        activeContestsManager.setFinalReviewPhaseTypeId(3);
        // open phase id = 1
        activeContestsManager.setOpenPhaseStatusId(1);
        // ProjectInfo table
        activeContestsManager.setProjectNameInfoId(1);
        activeContestsManager.setFirstPlaceCostInfoId(2);
        activeContestsManager.setReliabilityBonusCostInfoId(3);
        activeContestsManager.setDigitalRunPointInfoId(4);
        activeContestsManager.setPaymentsInfoId(5);
        activeContestsManager.setDigitalRunFlagInfoId(6);
        // Submission table
        activeContestsManager.setSubmitterRoleId(1);
        activeContestsManager.setHibernateTemplate(getHibernateTemplate());
        return activeContestsManager;
    }

    /**
     * <p>
     * Creates PastContestsManagerImpl instance with predefined properties.
     * </p>
     * @return created PastContestsManagerImpl instance with predefined properties
     */
    protected PastContestsManagerImpl createPastContestsManagerImpl() {
        PastContestsManagerImpl pastContestsManager = new PastContestsManagerImpl();
        // Project status
        pastContestsManager.setCompletedStatusId(2);
        // Submission statuses
        pastContestsManager.setActiveSubmissionStatusId(1);
        pastContestsManager.setFailedScreeningSubmissionStatusId(2);
        pastContestsManager.setFailedReviewSubmissionStatusId(3);
        pastContestsManager.setCompletedWithoutWinSubmissionStatusId(4);
        pastContestsManager.setContestSubmissionTypeId(1);
        // ProjectPhase table
        pastContestsManager.setRegistrationPhaseTypeId(1);
        pastContestsManager.setSubmissionPhaseTypeId(11);
        pastContestsManager.setApprovalPhaseTypeId(4);
        pastContestsManager.setPassingScreeningScore(66.6);
        // ProjectInfo table
        pastContestsManager.setProjectNameInfoId(1);
        pastContestsManager.setWinnerIdInfoId(7);
        pastContestsManager.setExternalReferenceIdInfoId(2);
        pastContestsManager.setHandleInfoId(3);
        // Submission table
        pastContestsManager.setSubmitterRoleId(1);
        pastContestsManager.setHibernateTemplate(getHibernateTemplate());
        pastContestsManager.setWinnerProfileLinkTemplate("topcode.com/handle=%id%");
        return pastContestsManager;
    }

    /**
     * <p>
     * Creates ContestStatusManagerImpl instance with predefined properties.
     * </p>
     * @return created ContestStatusManagerImpl instance with predefined properties
     */
    protected ContestStatusManagerImpl createContestStatusManagerImpl() {
        ContestStatusManagerImpl pastContestsManager = new ContestStatusManagerImpl();
        pastContestsManager.setOpenPhaseStatusId(1);
        // ProjectPhase table
        pastContestsManager.setRegistrationPhaseTypeId(1);
        pastContestsManager.setSubmissionPhaseTypeId(11);
        pastContestsManager.setFinalReviewPhaseTypeId(3);
        // ProjectInfo table
        pastContestsManager.setProjectNameInfoId(1);
        pastContestsManager.setWinnerIdInfoId(7);
        pastContestsManager.setExternalReferenceIdInfoId(2);
        pastContestsManager.setHandleInfoId(3);
        pastContestsManager.setFirstPlaceCostInfoId(2);
        pastContestsManager.setSecondPlaceIdInfoId(8);
        // Submission table
        pastContestsManager.setHibernateTemplate(getHibernateTemplate());
        return pastContestsManager;
    }
}
