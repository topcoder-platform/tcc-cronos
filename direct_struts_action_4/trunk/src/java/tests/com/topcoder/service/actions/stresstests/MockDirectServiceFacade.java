/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */



package com.topcoder.service.actions.stresstests;

import com.topcoder.management.project.Project;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.direct.ContestNotFoundException;
import com.topcoder.service.facade.direct.ContestPlan;
import com.topcoder.service.facade.direct.ContestPrize;
import com.topcoder.service.facade.direct.ContestReceiptData;
import com.topcoder.service.facade.direct.ContestSchedule;
import com.topcoder.service.facade.direct.ContestScheduleExtension;
import com.topcoder.service.facade.direct.DirectServiceFacade;
import com.topcoder.service.facade.direct.DirectServiceFacadeException;
import com.topcoder.service.facade.direct.EmailSendingException;
import com.topcoder.service.facade.direct.ProjectBudget;
import com.topcoder.service.facade.direct.ProjectNotFoundException;
import com.topcoder.service.facade.direct.SpecReviewState;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This is an Mock implementation of <code>Direct Service Facade</code> web service in form of
 * stateless session EJB.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockDirectServiceFacade implements DirectServiceFacade {
    /**
     * <p>
     * Creates new version for design/development contest.
     * </p>
     * @param tcSubject the TC subject
     * @param contestId the ID of the original contest
     * @return the ID of the newly created upgrade contest (positive)
     * @throws IllegalArgumentException if tcSubject is null or contestId <= 0
     * @throws IllegalStateException if this bean was not properly initialized (any bean or resource
     *         to be used was not properly injected)
     * @throws ContestNotFoundException if studio/software contest with the given ID cannot be found
     * @throws DirectServiceFacadeException if some other error occurred
     */
    public long createNewVersionForDesignDevContest(TCSubject tcSubject, long contestId)
            throws DirectServiceFacadeException {
        return contestId + 1;
    }

    /**
     * <p>
     * Deletes the contest with the given ID.
     * </p>
     * @param tcSubject the TC subject
     * @param contestId the ID of the contest to be deleted
     * @param studio true for a studio contest, false - for a software contest
     * @throws IllegalArgumentException if tcSubject is null or contestId <= 0
     * @throws ContestNotFoundException if studio/software contest with the given ID cannot be found
     * @throws DirectServiceFacadeException if some other error occurred
     */
    public void deleteContest(TCSubject tcSubject, long contestId, boolean studio)
            throws DirectServiceFacadeException {}

    /**
     * <p>
     * Extends the active contest schedule.
     * </p>
     * @param tcSubject the TC subject
     * @param extension the contest schedule extension data
     * @throws IllegalArgumentException if tcSubject or extension is null, extension.getContestId() <=
     *         0, extension.getExtendRegistrationBy(), extension.getExtendMilestoneBy() or
     *         extension.getExtendSubmissionBy() is negative
     * @throws ContestNotFoundException if studio/software contest with the ID specified in
     *         ContestScheduleExtension cannot be found
     * @throws DirectServiceFacadeException if some other error occurred
     */
    public void extendActiveContestSchedule(TCSubject tcSubject, ContestScheduleExtension extension)
            throws DirectServiceFacadeException {}

    /**
     * <p>
     * Retrieves the child projects for the project with the given ID.
     * </p>
     * @param tcSubject the TC subject
     * @param projectId the ID of the project
     * @return the retrieved child projects (not null, doesn't contain null, contains instances of
     *         Project)
     * @throws IllegalArgumentException if tcSubject is null or projectId <= 0
     * @throws ProjectNotFoundException if project with the given ID cannot be found
     * @throws DirectServiceFacadeException if some other error occurred
     */
    public List<Project> getChildProjects(TCSubject tcSubject, long projectId) throws DirectServiceFacadeException {
        Project project = new Project();

        project.setTcDirectProjectName("child");
        List<Project> ret = new ArrayList<Project>();

        ret.add(project);

        return ret;
    }

    /**
     * <p>
     * Retrieves the contest prize for contest with the given ID.
     * </p>
     * @param tcSubject the TC subject
     * @param contestId the ID of the contest
     * @param studio true for a studio contest, false - for a software contest
     * @return the retrieved contest prize data (not null)
     * @throws IllegalArgumentException if tcSubject is null or contestId <= 0
     * @throws ContestNotFoundException if studio/software contest with the given ID cannot be found
     * @throws DirectServiceFacadeException if some other error occurred
     */
    public ContestPrize getContestPrize(TCSubject tcSubject, long contestId, boolean studio)
            throws DirectServiceFacadeException {
        ContestPrize result = new ContestPrize();

        result.setContestId(contestId);
        result.setStudio(studio);

        double[] milestonePrizes = new double[0];

        result.setMilestonePrizes(milestonePrizes);
        result.setContestPrizes(milestonePrizes);

        return result;
    }

    /**
     * <p>
     * Retrieves the contest receipt data.
     * </p>
     * @param tcSubject the TC subject
     * @param contestId the ID of the contest
     * @param isStudio true for a studio contest, false - for a software contest
     * @return the retrieved contest receipt data (not null)
     * @throws IllegalArgumentException if tcSubject is null or contestId <= 0
     * @throws ContestNotFoundException if studio/software contest with the given ID cannot be found
     * @throws DirectServiceFacadeException if some other error occurred
     */
    public ContestReceiptData getContestReceiptData(TCSubject tcSubject, long contestId, boolean isStudio)
            throws DirectServiceFacadeException {
        return null;
    }

    /**
     * <p>
     * Retrieves the contest schedule.
     * </p>
     * @param tcSubject the TC subject
     * @param contestId the ID of the contest
     * @param studio true for a studio contest, false - for a software contest
     * @return the retrieved contest schedule (not null)
     * @throws IllegalArgumentException if tcSubject is null or contestId <= 0
     * @throws ContestNotFoundException if studio/software contest with the given ID cannot be found
     * @throws DirectServiceFacadeException if some other error occurred
     */
    public ContestSchedule getContestSchedule(TCSubject tcSubject, long contestId, boolean studio)
            throws DirectServiceFacadeException {
        ContestSchedule result = new ContestSchedule();

        result.setContestId(contestId);
        result.setStudio(studio);

        return result;
    }

    /**
     * <p>
     * Retrieves the parent projects for the project with the given ID.
     * </p>
     * @param tcSubject the TC subject
     * @param projectId the ID of the project
     * @return the retrieved parent projects (not null, doesn't contain null, contains instances of
     *         com.topcoder.management.project.Project)
     * @throws IllegalArgumentException if tcSubject is null or projectId <= 0
     * @throws ProjectNotFoundException if project with the given ID cannot be found
     * @throws DirectServiceFacadeException if some other error occurred
     */
    public List<Project> getParentProjects(TCSubject tcSubject, long projectId) throws DirectServiceFacadeException {
        Project project = new Project();

        project.setTcDirectProjectName("parent");
        List<Project> ret = new ArrayList<Project>();

        ret.add(project);

        return ret;
    }

    /**
     * <p>
     * Retrieves the project budget for the project with the specified billing ID.
     * </p>
     * @param tcSubject the TC subject
     * @param billingProjectId the ID of the billing project
     * @return the retrieved project budget
     * @throws IllegalArgumentException if tcSubject is null or billingProjectId <= 0
     * @throws ProjectNotFoundException if billing project with the given ID cannot be found
     * @throws DirectServiceFacadeException if some other error occurred
     */
    public double getProjectBudget(TCSubject tcSubject, long billingProjectId) throws DirectServiceFacadeException {
        return 0;
    }

    /**
     * <p>
     * Retrieves the project game plan.
     * </p>
     * @param tcSubject the TC subject
     * @param projectId the ID of the project
     * @return the retrieved project game plan (not null, doesn't contain null)
     * @throws IllegalArgumentException if tcSubject is null or projectId <= 0
     * @throws ProjectNotFoundException if project with the given ID cannot be found
     * @throws DirectServiceFacadeException if some other error occurred
     */
    public List<ContestPlan> getProjectGamePlan(TCSubject tcSubject, long projectId)
            throws DirectServiceFacadeException {
        List<ContestPlan> result = new ArrayList<ContestPlan>();

        return result;
    }

    /**
     * <p>
     * Retrieves the specification review state.
     * </p>
     * @param tcSubject the TC subject
     * @param contestId the ID of the contest
     * @return the retrieved specification review state (not null)
     * @throws IllegalArgumentException if tcSubject is null or contestId <= 0
     * @throws ContestNotFoundException if contest with the given ID cannot be found
     * @throws DirectServiceFacadeException if some other error occurred
     */
    public SpecReviewState getSpecReviewState(TCSubject tcSubject, long contestId) throws DirectServiceFacadeException {
        return null;
    }

    /**
     * <p>
     * Reposts the software contest.
     * </p>
     * @param tcSubject the TC subject
     * @param contestId the ID of the original contest
     * @return the ID of the newly created repost contest (positive)
     * @throws IllegalArgumentException if tcSubject is null or contestId <= 0
     * @throws ContestNotFoundException if studio/software contest with the given ID cannot be found
     * @throws DirectServiceFacadeException if some other error occurred
     */
    public long repostSoftwareContest(TCSubject tcSubject, long contestId) throws DirectServiceFacadeException {
        return contestId + 1;
    }

    /**
     * <p>
     * Sends contest receipt by email to contest creator and the specified email addresses.
     * </p>
     * @param tcSubject the TC subject
     * @param contestId the ID of the contest
     * @param isStudio true for a studio contest, false - for a software contest
     * @param additionalEmailAddrs the additional email addresses
     * @return the retrieved contest receipt data (not null)
     * @throws IllegalArgumentException if tcSubject is null, contestId < 0 or additionalEmailAddrs
     *         contains null/empty element
     * @throws ContestNotFoundException if studio/software contest with the given ID cannot be found
     * @throws EmailSendingException if some error occurred when sending an email message
     * @throws DirectServiceFacadeException if some other error occurred
     */
    public ContestReceiptData sendContestReceiptByEmail(TCSubject tcSubject, long contestId, boolean isStudio,
            String[] additionalEmailAddrs)
            throws DirectServiceFacadeException {
        ContestReceiptData result = new ContestReceiptData();

        result.setContestId(contestId);
        result.setStudio(isStudio);

        return result;
    }

    /**
     * <p>
     * Updates the given active contest prize data in the persistence.
     * </p>
     * @param tcSubject the TC subject
     * @param contestPrize the updated contest prize data
     * @throws IllegalArgumentException if tcSubject or contestPrize is null,
     *         contestPrize.getContestId() <= 0, contestPrize.getContestPrizes() is null/empty,
     *         contestPrize.getContestPrizes() or contestPrize.getMilestonePrizes() contains
     *         negative element
     * @throws ContestNotFoundException if studio/software contest with the ID specified in
     *         ContestPrize cannot be found
     * @throws DirectServiceFacadeException if some other error occurred
     */
    public void updateActiveContestPrize(TCSubject tcSubject, ContestPrize contestPrize)
            throws DirectServiceFacadeException {}

    /**
     * <p>
     * Updates project budget.
     * </p>
     * @param tcSubject the TC subject
     * @param billingProjectId the ID of the billing project
     * @param changedAmount the changed amount (negative if the budget decreased)
     * @return the project budget data (not null)
     * @throws IllegalArgumentException if tcSubject is null or billingProjectId <= 0
     * @throws ProjectNotFoundException if billing project with the given ID cannot be found
     * @throws DirectServiceFacadeException if some other error occurred
     */
    public ProjectBudget updateProjectBudget(TCSubject tcSubject, long billingProjectId, double changedAmount)
            throws DirectServiceFacadeException {
        ProjectBudget ret = new ProjectBudget();

        return ret;
    }

    /**
     * <p>
     * Updates project links for the project with the given ID.
     * </p>
     * @param tcSubject the TC subject
     * @param projectId the ID of the project
     * @param parentProjectIds the IDs of the parent projects
     * @param childProjectIds the IDs of the child projects
     * @throws IllegalArgumentException if tcSubject is null or projectId <= 0, parentProjectIds or
     *         childProjectIds contains not positive element
     * @throws ProjectNotFoundException if some project with the given ID cannot be found
     * @throws DirectServiceFacadeException if some other error occurred
     */
    public void updateProjectLinks(TCSubject tcSubject, long projectId, long[] parentProjectIds, long[] childProjectIds)
            throws DirectServiceFacadeException {}
}
