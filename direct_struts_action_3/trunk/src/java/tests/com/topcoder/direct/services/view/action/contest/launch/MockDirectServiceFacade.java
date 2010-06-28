/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.topcoder.service.facade.direct.ProjectBudget;
import com.topcoder.service.facade.direct.SpecReviewState;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.project.StudioCompetition;

/**
 * <p>
 * The mock <code>DirectServiceFacade</code> used in the unit tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockDirectServiceFacade implements DirectServiceFacade {

    /**
     * Represents the additional email addresses the contest receipt was sent to.
     */
    private static String[] additionalEmailAddresses;

    /**
     * Represents contest prizes used for unit testing.
     */
    private static Map<Long, ContestPrize> contestPrizes = new HashMap<Long, ContestPrize>();

    /**
     * Sets the additional email addresses the contest receipt was sent to.
     *
     * @param additionalEmailAddresses the additional email addresses the contest receipt was sent to
     */
    public static void setAdditionalEmailAddresses(String[] additionalEmailAddresses) {
        MockDirectServiceFacade.additionalEmailAddresses = additionalEmailAddresses;
    }

    /**
     * Gets the additional email addresses the contest receipt was sent to.
     *
     * @return The additional email addresses the contest receipt was sent to
     */
    public static String[] getadditionalEmailAddresses() {
        return additionalEmailAddresses;
    }

    /**
     * Prepares the mock data to use for unit testing.
     */
    public static void initMockData() {
        // load the contest prizes
        ContestPrize prize = new ContestPrize();
        prize.setContestId(1);
        prize.setContestPrizes(new double[] {100.25, 20.25});
        prize.setMilestonePrizes(new double[] {100.35, 80.35});
        prize.setStudio(true);
        contestPrizes.put(prize.getContestId(), prize);

        prize = new ContestPrize();
        prize.setContestId(2);
        prize.setContestPrizes(new double[] {109.32, 57.89});
        prize.setMilestonePrizes(new double[] {100.0, 100.0});
        prize.setStudio(false);
        contestPrizes.put(prize.getContestId(), prize);

        prize = new ContestPrize();
        prize.setContestId(3);
        prize.setContestPrizes(new double[] {1000.0, 500.0});
        prize.setStudio(true);
        contestPrizes.put(prize.getContestId(), prize);

        prize = new ContestPrize();
        prize.setContestId(4);
        prize.setContestPrizes(new double[] {1000.0, 500.0});
        prize.setMilestonePrizes(new double[0]);
        prize.setStudio(true);
        contestPrizes.put(prize.getContestId(), prize);

        additionalEmailAddresses = null;
    }

    /**
     * Clears mock data used for unit testing.
     */
    public static void clearMockData() {
        contestPrizes.clear();
        additionalEmailAddresses = null;
    }

    /**
     * Gets the contest prize from the map for unit testing.
     *
     * @param contestId the contest ID
     * @return the contest prize
     * @throws ContestNotFoundException if contest is not found
     */
    public static ContestPrize getContestPrize(long contestId) throws ContestNotFoundException {
        checkContestExists(contestId, false);
        return contestPrizes.get(contestId);
    }

    /**
     * <p>
     * Creates new version for design/development contest.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param contestId
     *            the ID of the original contest
     * @return the ID of the newly created upgrade contest (positive)
     */
    public long createNewVersionForDesignDevContest(TCSubject tcSubject, long contestId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Deletes the contest with the given ID.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param contestId
     *            the ID of the contest to be deleted
     * @param studio
     *            true for a studio contest, false - for a software contest
     */
    public void deleteContest(TCSubject tcSubject, long contestId, boolean studio) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Extends the active contest schedule.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param extension
     *            the contest schedule extension data
     * @throws ContestNotFoundException
     *             if studio/software contest with the ID specified in ContestScheduleExtension cannot be
     *             found
     * @throws DirectServiceFacadeException
     *             if some other error occurred
     */
    public void extendActiveContestSchedule(TCSubject tcSubject, ContestScheduleExtension extension)
        throws DirectServiceFacadeException {

        checkContestExists(extension.getContestId(), extension.isStudio());

        // prepare the extension data and store it
        String extensionData = "extendRegistrationBy=" + extension.getExtendRegistrationBy() + " "
            + "extendMilestoneBy=" + extension.getExtendMilestoneBy() + " " + "extendSubmissionBy="
            + extension.getExtendSubmissionBy();

        if (extension.isStudio()) {
            StudioCompetition comp = MockContestServiceFacade.getStudioCompetitions().get(extension.getContestId());
            comp.setNotes(extensionData);
        } else {
            SoftwareCompetition comp = MockContestServiceFacade.getSoftwareCompetitions().get(
                extension.getContestId());
            comp.setNotes(extensionData);
        }
    }

    /**
     * <p>
     * Retrieves the child projects for the project with the given ID.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param projectId
     *            the ID of the project
     * @return the retrieved child projects (not null, doesn't contain null, contains instances of Project)
     */
    public List<Project> getChildProjects(TCSubject tcSubject, long projectId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Retrieves the contest prize for contest with the given ID.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param contestId
     *            the ID of the contest
     * @param studio
     *            true for a studio contest, false - for a software contest
     * @return the retrieved contest prize data (not null)
     * @throws ContestNotFoundException
     *             if studio/software contest with the given ID cannot be found
     * @throws DirectServiceFacadeException
     *             if some other error occurred
     */
    public ContestPrize getContestPrize(TCSubject tcSubject, long contestId, boolean studio)
        throws DirectServiceFacadeException {

        if (contestPrizes.containsKey(contestId)) {
            return contestPrizes.get(contestId);
        } else if (contestId == 999) {
            throw new DirectServiceFacadeException("test");
        }
        throw new ContestNotFoundException("test", contestId);
    }

    /**
     * <p>
     * Retrieves the contest receipt data.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param contestId
     *            the ID of the contest
     * @param isStudio
     *            true for a studio contest, false - for a software contest
     * @return the retrieved contest receipt data (not null)
     * @throws ContestNotFoundException
     *             if studio/software contest with the given ID cannot be found
     * @throws DirectServiceFacadeException
     *             if some other error occurred
     */
    public ContestReceiptData getContestReceiptData(TCSubject tcSubject, long contestId, boolean isStudio)
        throws DirectServiceFacadeException {

        checkContestExists(contestId, isStudio);

        // construct the contest receipt based on type of competition
        if (isStudio) {
            StudioCompetition comp = MockContestServiceFacade.getStudioCompetitions().get(contestId);
            ContestReceiptData data = new ContestReceiptData();
            data.setContestId(comp.getId());
            data.setContestName(comp.getContestData().getName());
            return data;
        } else {
            SoftwareCompetition comp = MockContestServiceFacade.getSoftwareCompetitions().get(contestId);
            ContestReceiptData data = new ContestReceiptData();
            data.setContestId(comp.getId());
            data.setContestName(comp.getAssetDTO().getName());
            return data;
        }
    }

    /**
     * <p>
     * Retrieves the contest schedule.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param contestId
     *            the ID of the contest
     * @param studio
     *            true for a studio contest, false - for a software contest
     * @return the retrieved contest schedule (not null)
     * @throws ContestNotFoundException
     *             if studio/software contest with the given ID cannot be found
     */
    public ContestSchedule getContestSchedule(TCSubject tcSubject, long contestId, boolean studio)
        throws ContestNotFoundException {

        checkContestExists(contestId, studio);

        ContestSchedule contestSchedule = new ContestSchedule();
        contestSchedule.setStudio(studio);
        contestSchedule.setContestId(contestId);
        return contestSchedule;
    }

    /**
     * <p>
     * Retrieves the parent projects for the project with the given ID.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param projectId
     *            the ID of the project
     * @return the retrieved parent projects (not null, doesn't contain null, contains instances of
     *         com.topcoder.management.project.Project)
     */
    public List<Project> getParentProjects(TCSubject tcSubject, long projectId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Retrieves the project budget for the project with the specified billing ID.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param billingProjectId
     *            the ID of the billing project
     * @return the retrieved project budget
     */
    public double getProjectBudget(TCSubject tcSubject, long billingProjectId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Retrieves the project game plan.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param projectId
     *            the ID of the project
     * @return the retrieved project game plan (not null, doesn't contain null)
     * @throws DirectServiceFacadeException if some error occurs getting the game plan
     */
    public List<ContestPlan> getProjectGamePlan(TCSubject tcSubject, long projectId)
        throws DirectServiceFacadeException {
        if (projectId == 999) {
            throw new DirectServiceFacadeException("test");
        }

        List<ContestPlan> list = new ArrayList<ContestPlan>();

        ContestPlan plan;

        plan = new ContestPlan();
        plan.setName("contest1");
        list.add(plan);

        plan = new ContestPlan();
        plan.setName("contest2");
        list.add(plan);

        plan = new ContestPlan();
        plan.setName("contest3");
        list.add(plan);

        return list;
    }

    /**
     * <p>
     * Retrieves the specification review state.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param contestId
     *            the ID of the contest
     * @return the retrieved specification review state (not null)
     */
    public SpecReviewState getSpecReviewState(TCSubject tcSubject, long contestId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Reposts the software contest.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param contestId
     *            the ID of the original contest
     * @return the ID of the newly created repost contest (positive)
     */
    public long repostSoftwareContest(TCSubject tcSubject, long contestId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Sends contest receipt by email to contest creator and the specified email addresses.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param contestId
     *            the ID of the contest
     * @param isStudio
     *            true for a studio contest, false - for a software contest
     * @param additionalEmailAddrs
     *            the additional email addresses
     * @return the retrieved contest receipt data (not null)
     * @throws IllegalArgumentException
     *             if tcSubject is null, contestId < 0 or additionalEmailAddrs contains null/empty element
     * @throws ContestNotFoundException
     *             if studio/software contest with the given ID cannot be found
     * @throws DirectServiceFacadeException
     *             if some other error occurred
     */
    public ContestReceiptData sendContestReceiptByEmail(TCSubject tcSubject, long contestId, boolean isStudio,
        String[] additionalEmailAddrs) throws DirectServiceFacadeException {

        // store the email addresses so they can be validated for unit test
        setAdditionalEmailAddresses(additionalEmailAddrs);

        // return the receipt data
        return getContestReceiptData(tcSubject, contestId, isStudio);
    }

    /**
     * <p>
     * Updates the given active contest prize data in the persistence.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param contestPrize
     *            the updated contest prize data
     * @throws IllegalArgumentException
     *             if tcSubject or contestPrize is null, contestPrize.getContestId() <= 0,
     *             contestPrize.getContestPrizes() is null/empty, contestPrize.getContestPrizes() or
     *             contestPrize.getMilestonePrizes() contains negative element
     * @throws ContestNotFoundException
     *             if studio/software contest with the ID specified in ContestPrize cannot be found
     * @throws DirectServiceFacadeException
     *             if some other error occurred
     */
    public void updateActiveContestPrize(TCSubject tcSubject, ContestPrize contestPrize)
        throws DirectServiceFacadeException {

        // store in the static map for unit testing
        contestPrizes.put(contestPrize.getContestId(), contestPrize);
    }

    /**
     * <p>
     * Updates project budget.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param billingProjectId
     *            the ID of the billing project
     * @param changedAmount
     *            the changed amount (negative if the budget decreased)
     * @return the project budget data (not null)
     */
    public ProjectBudget updateProjectBudget(TCSubject tcSubject, long billingProjectId, double changedAmount) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Updates project links for the project with the given ID.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param projectId
     *            the ID of the project
     * @param parentProjectIds
     *            the IDs of the parent projects
     * @param childProjectIds
     *            the IDs of the child projects
     */
    public void updateProjectLinks(TCSubject tcSubject, long projectId, long[] parentProjectIds,
        long[] childProjectIds) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks that contest exists.
     *
     * @param contestId the contest ID
     * @param isStudio whether contest is a studio contest
     * @throws ContestNotFoundException if contest wasn't found
     */
    private static void checkContestExists(long contestId, boolean isStudio) throws ContestNotFoundException {
        if (isStudio && MockContestServiceFacade.getStudioCompetitions().get(contestId) == null) {
            throw new ContestNotFoundException("test", 1);
        } else if (MockContestServiceFacade.getSoftwareCompetitions().get(contestId) == null) {
            throw new ContestNotFoundException("test", 1);
        }
    }
}
