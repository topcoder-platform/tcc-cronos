/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.studio.contest;

import java.util.Date;

import com.topcoder.service.studio.submission.MilestonePrize;
import com.topcoder.service.studio.submission.PrizeType;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Demo for one entity class <code>{@link Contest}</code> is given. All other entities can be used in a similar
 * fashion.
 * </p>
 *
 * @author cyberjag, TCSDEVELOPER
 * @version 1.2
 */
public class Demo extends TestCase {

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(Demo.class);
    }

    /**
     * <p>
     * Demo showing the CRUD operations on <code>{@link FilePath}</code> entity.
     * </p>
     */
    public void testDemo() {

        try {
            HibernateUtil.getManager().getTransaction().begin();

            Contest contest = new Contest();

            // initialize the contest
            // ....

            // save the entity
            HibernateUtil.getManager().persist(contest);

            // load the persisted object
            Contest persisted = (Contest) HibernateUtil.getManager().find(Contest.class, contest.getContestId());

            // update the entity
            contest.setForumId(100L);
            HibernateUtil.getManager().merge(contest);

            persisted = (Contest) HibernateUtil.getManager().find(Contest.class, contest.getContestId());

            // delete the entity
            HibernateUtil.getManager().remove(contest);

        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }

    }

    /**
     * <p>
     * Demonstrates the usage of javabeans.
     * </p>
     */
    public void testJavaBean() {

        // create studio file type
        StudioFileType fileType = new StudioFileType();
        fileType.setDescription("description");
        fileType.setExtension("extension");
        fileType.setImageFile(true);
        fileType.setSort(10);

        // create contest channel
        ContestChannel channel = new ContestChannel();
        channel.setDescription("description");

        // create contest type
        ContestType contestType = new ContestType();
        TestHelper.populateContestType(contestType);
        contestType.setContestType(1L);

        // create contest status
        ContestStatus status = new ContestStatus();
        status.setDescription("description");
        status.setName("Name");
        status.setContestStatusId(10L);
        status.setStatusId(1L);

        // create contest general information
        ContestGeneralInfo generalInfo = new ContestGeneralInfo();
        generalInfo.setBrandingGuidelines("guideline");
        generalInfo.setDislikedDesignsWebsites("disklike");
        generalInfo.setGoals("goal");
        generalInfo.setOtherInstructions("instruction");
        generalInfo.setTargetAudience("target audience");
        generalInfo.setWinningCriteria("winning criteria");

        // create contest multi-round information
        ContestMultiRoundInformation multiRoundInformation = new ContestMultiRoundInformation();
        multiRoundInformation.setMilestoneDate(new Date());
        multiRoundInformation.setRoundOneIntroduction("round one");
        multiRoundInformation.setRoundTwoIntroduction("round two");

        // create contest specificatoins
        ContestSpecifications specifications = new ContestSpecifications();
        specifications.setAdditionalRequirementsAndRestrictions("none");
        specifications.setColors("white");
        specifications.setFonts("Arial");
        specifications.setLayoutAndSize("10px");

        // create prize type
        PrizeType prizeType = new PrizeType();
        prizeType.setDescription("Good");
        prizeType.setPrizeTypeId(1L);

        // create milestone prize
        MilestonePrize milestonePrize = new MilestonePrize();
        milestonePrize.setAmount(10.0);
        milestonePrize.setCreateDate(new Date());
        milestonePrize.setNumberOfSubmissions(1);
        milestonePrize.setType(prizeType);

        Date date = new Date();

        // create Contest
        Contest contest = new Contest();

        // setting properties of the contest
        contest.setContestChannel(channel);
        contest.setContestType(contestType);
        contest.setCreatedUser(10L);
        contest.setEndDate(date);
        contest.setEventId(101L);
        contest.setForumId(1000L);
        contest.setName("name");
        contest.setProjectId(101L);
        contest.setStartDate(date);
        contest.setStatus(status);
        contest.setStatusId(1L);
        contest.setTcDirectProjectId(1101L);
        contest.setWinnerAnnoucementDeadline(date);
        contest.setGeneralInfo(generalInfo);
        contest.setSpecifications(specifications);
        contest.setMultiRoundInformation(multiRoundInformation);
        contest.setMilestonePrize(milestonePrize);
        contest.setStatus(status);
    }
}
