/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Query;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.studio.submission.ContestResult;
import com.topcoder.service.studio.submission.MilestonePrize;
import com.topcoder.service.studio.submission.PrizeType;
import com.topcoder.service.studio.submission.Submission;

/**
 * <p>
 * Tests the functionality of {@link Contest} class.
 * </p>
 *
 * @author cyberjag, TCSDEVELOPER
 * @version 1.2
 */
public class ContestTest extends TestCase {

    /**
     * Represents the <code>Contest</code> instance to test.
     */
    private Contest contest = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        contest = new Contest();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        contest = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#Contest()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_Contest() {
        // check for null
        assertNotNull("Contest creation failed", contest);
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#getContestId()} and
     * {@link Contest#setContestId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new
     * Long(1).
     * </p>
     */
    public void test_accuracy_getContestId() {
        // set the value to test
        contest.setContestId(new Long(1));
        assertEquals("getContestId and setContestId failure occured", new Long(1), contest.getContestId());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#setContestId(Long)} and
     * {@link Contest#getContestId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * Valid.
     * </p>
     */
    public void test_accuracy_setContestId() {
        // set the value to test
        contest.setContestId(1L);
        assertEquals("getContestId and setContestId failure occured", 1L, (long) contest.getContestId());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#getName()} and
     * {@link Contest#setName(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * null.
     * </p>
     */
    public void test_accuracy_getName() {
        // set the value to test
        contest.setName(null);
        assertEquals("getName and setName failure occured", null, contest.getName());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#setName(String)} and
     * {@link Contest#getName()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * "test".
     * </p>
     */
    public void test_accuracy_setName() {
        // set the value to test
        contest.setName("test");
        assertEquals("getName and setName failure occured", "test", contest.getName());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#getContestChannel()} and
     * {@link Contest#setContestChannel(ContestChannel)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * null.
     * </p>
     */
    public void test_accuracy_getContestCategory() {
        // set the value to test
        contest.setContestChannel(null);
        assertEquals("getContestCategory and setContestCategory failure occured", null, contest.getContestChannel());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#setContestChannel(ContestChannel)} and
     * {@link Contest#getContestChannel()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * Valid.
     * </p>
     */
    public void test_accuracy_setContestCategory() {
        // set the value to test
        ContestChannel category = new ContestChannel();
        category.setContestChannelId(1L);
        contest.setContestChannel(category);
        assertEquals("getContestCategory and setContestCategory failure occured", category.getContestChannelId(),
                contest.getContestChannel().getContestChannelId());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#getProjectId()} and
     * {@link Contest#setProjectId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new
     * Long(1).
     * </p>
     */
    public void test_accuracy_getProjectId() {
        // set the value to test
        contest.setProjectId(new Long(1));
        assertEquals("getProjectId and setProjectId failure occured", new Long(1), contest.getProjectId());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#setProjectId(Long)} and
     * {@link Contest#getProjectId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * Valid.
     * </p>
     */
    public void test_accuracy_setProjectId() {
        // set the value to test
        contest.setProjectId(1L);
        assertEquals("getProjectId and setProjectId failure occured", 1L, (long) contest.getProjectId());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#getTcDirectProjectId()} and
     * {@link Contest#setTcDirectProjectId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new
     * Long(1).
     * </p>
     */
    public void test_accuracy_getTcDirectProjectId() {
        // set the value to test
        contest.setTcDirectProjectId(new Long(1));
        assertEquals("getTcDirectProjectId and setTcDirectProjectId failure occured", new Long(1), contest
                .getTcDirectProjectId());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#setTcDirectProjectId(Long)} and
     * {@link Contest#getTcDirectProjectId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * Valid.
     * </p>
     */
    public void test_accuracy_setTcDirectProjectId() {
        // set the value to test
        contest.setTcDirectProjectId(1L);
        assertEquals("getTcDirectProjectId and setTcDirectProjectId failure occured", 1L, (long) contest
                .getTcDirectProjectId());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#getStatus()} and
     * {@link Contest#setStatus(ContestStatus)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * null.
     * </p>
     */
    public void test_accuracy_getStatus() {
        // set the value to test
        contest.setStatus(null);
        assertEquals("getStatus and setStatus failure occured", null, contest.getStatus());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#setStatus(ContestStatus)} and
     * {@link Contest#getStatus()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * Valid.
     * </p>
     */
    public void test_accuracy_setStatus() {
        // set the value to test
        ContestStatus status = new ContestStatus();
        status.setContestStatusId(1L);
        contest.setStatus(status);
        assertEquals("getStatus and setStatus failure occured", status.getContestStatusId(), contest.getStatus()
                .getContestStatusId());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#getForumId()} and
     * {@link Contest#setForumId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new
     * Long(1).
     * </p>
     */
    public void test_accuracy_getForumId() {
        // set the value to test
        contest.setForumId(new Long(1));
        assertEquals("getForumId and setForumId failure occured", new Long(1), contest.getForumId());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#setForumId(Long)} and
     * {@link Contest#getForumId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * Valid.
     * </p>
     */
    public void test_accuracy_setForumId() {
        // set the value to test
        contest.setForumId(1L);
        assertEquals("getForumId and setForumId failure occured", 1L, (long) contest.getForumId());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#getEventId()} and
     * {@link Contest#setEventId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new
     * Long(1).
     * </p>
     */
    public void test_accuracy_getEventId() {
        // set the value to test
        contest.setEventId(new Long(1));
        assertEquals("getEventId and setEventId failure occured", new Long(1), contest.getEventId());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#setEventId(Long)} and
     * {@link Contest#getEventId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * Valid.
     * </p>
     */
    public void test_accuracy_setEventId() {
        // set the value to test
        contest.setEventId(1L);
        assertEquals("getEventId and setEventId failure occured", 1L, (long) contest.getEventId());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#getSubmissions()} and {@link
     * Contest#setSubmissions(Set)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * null.
     * </p>
     */
    public void test_accuracy_getSubmissions() {
        // set the value to test
        contest.setSubmissions(null);
        assertEquals("getSubmissions and setSubmissions failure occured", null, contest.getSubmissions());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#setSubmissions(Set)} and
     * {@link Contest#getSubmissions()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * Valid.
     * </p>
     */
    public void test_accuracy_setSubmissions() {
        // set the value to test
        Set<Submission> subs = new HashSet<Submission>();
        subs.add(new Submission());
        contest.setSubmissions(subs);
        assertEquals("getSubmissions and setSubmissions failure occured", subs.size(), contest.getSubmissions().size());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#getFileTypes()} and {@link
     * Contest#setFileTypes(Set)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * null.
     * </p>
     */
    public void test_accuracy_getFileTypes() {
        // set the value to test
        contest.setFileTypes(null);
        assertEquals("getFileTypes and setFileTypes failure occured", null, contest.getFileTypes());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#setFileTypes(Set)} and
     * {@link Contest#getFileTypes()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * Valid.
     * </p>
     */
    public void test_accuracy_setFileTypes() {
        // set the value to test
        Set<StudioFileType> types = new HashSet<StudioFileType>();
        types.add(new StudioFileType());
        contest.setFileTypes(types);
        assertEquals("getFileTypes and setFileTypes failure occured", types.size(), contest.getFileTypes().size());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#getResults()} and {@link
     * Contest#setResults(Set)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * null.
     * </p>
     */
    public void test_accuracy_getResults() {
        // set the value to test
        contest.setResults(null);
        assertEquals("getResults and setResults failure occured", null, contest.getResults());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#setResults(Set)} and
     * {@link Contest#getResults()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * Valid.
     * </p>
     */
    public void test_accuracy_setResults() {
        // set the value to test
        Set<ContestResult> results = new HashSet<ContestResult>();
        results.add(new ContestResult());
        contest.setResults(results);
        assertEquals("getResults and setResults failure occured", results.size(), contest.getResults().size());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#getDocuments()} and {@link
     * Contest#setDocuments(Set)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * null.
     * </p>
     */
    public void test_accuracy_getDocuments() {
        // set the value to test
        contest.setDocuments(null);
        assertEquals("getDocuments and setDocuments failure occured", null, contest.getDocuments());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#setDocuments(Set)} and
     * {@link Contest#getDocuments()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * Valid.
     * </p>
     */
    public void test_accuracy_setDocuments() {
        // set the value to test
        Set<Document> docs = new HashSet<Document>();
        docs.add(new Document());
        contest.setDocuments(docs);
        assertEquals("getDocuments and setDocuments failure occured", docs.size(), contest.getDocuments().size());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#getConfig()} and {@link
     * Contest#setConfig(Set)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * null.
     * </p>
     */
    public void test_accuracy_getConfig() {
        // set the value to test
        contest.setConfig(null);
        assertEquals("getConfig and setConfig failure occured", null, contest.getConfig());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#setConfig(Set)} and
     * {@link Contest#getConfig()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * Valid.
     * </p>
     */
    public void test_accuracy_setConfig() {
        // set the value to test
        Set<ContestConfig> config = new HashSet<ContestConfig>();
        config.add(new ContestConfig());

        contest.setConfig(config);
        assertEquals("getConfig and setConfig failure occured", config.size(), contest.getConfig().size());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#getContestType()} and
     * {@link Contest#setContestType(ContestType)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * null.
     * </p>
     */
    public void test_accuracy_getContestType() {
        // set the value to test
        contest.setContestType(null);
        assertEquals("getContestType and setContestType failure occured", null, contest.getContestType());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#setContestType(ContestType)} and
     * {@link Contest#getContestType()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * Valid.
     * </p>
     */
    public void test_accuracy_setContestType() {
        // set the value to test
        ContestType type = new ContestType();
        type.setContestType(1L);
        contest.setContestType(type);
        assertEquals("getContestType and setContestType failure occured", type.getContestType(), contest
                .getContestType().getContestType());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#getStartDate()} and
     * {@link Contest#setStartDate(Date)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * null.
     * </p>
     */
    public void test_accuracy_getStartDate() {
        // set the value to test
        contest.setStartDate(null);
        assertEquals("getStartDate and setStartDate failure occured", null, contest.getStartDate());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#setStartDate(Date)} and
     * {@link Contest#getStartDate()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * Valid.
     * </p>
     */
    public void test_accuracy_setStartDate() {
        // set the value to test
        Date date = new Date();
        contest.setStartDate(date);
        assertEquals("getStartDate and setStartDate failure occured", date, contest.getStartDate());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#getEndDate()} and
     * {@link Contest#setEndDate(Date)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * null.
     * </p>
     */
    public void test_accuracy_getEndDate() {
        // set the value to test
        contest.setEndDate(null);
        assertEquals("getEndDate and setEndDate failure occured", null, contest.getEndDate());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#setEndDate(Date)} and
     * {@link Contest#getEndDate()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * Valid.
     * </p>
     */
    public void test_accuracy_setEndDate() {
        // set the value to test
        Date date = new Date();
        contest.setEndDate(date);
        assertEquals("getEndDate and setEndDate failure occured", date, contest.getEndDate());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#getWinnerAnnoucementDeadline()} and
     * {@link Contest#setWinnerAnnoucementDeadline(Date)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * null.
     * </p>
     */
    public void test_accuracy_getWinnerAnnoucementDeadline() {
        // set the value to test
        contest.setWinnerAnnoucementDeadline(null);
        assertEquals("getWinnerAnnoucementDeadline and setWinnerAnnoucementDeadline failure occured", null, contest
                .getWinnerAnnoucementDeadline());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#setWinnerAnnoucementDeadline(Date)} and
     * {@link Contest#getWinnerAnnoucementDeadline()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * Valid.
     * </p>
     */
    public void test_accuracy_setWinnerAnnoucementDeadline() {
        // set the value to test
        Date date = new Date();
        contest.setWinnerAnnoucementDeadline(date);
        assertEquals("getWinnerAnnoucementDeadline and setWinnerAnnoucementDeadline failure occured", date, contest
                .getWinnerAnnoucementDeadline());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#getCreatedUser()} and
     * {@link Contest#setCreatedUser(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new
     * Long(1).
     * </p>
     */
    public void test_accuracy_getCreatedUser() {
        // set the value to test
        contest.setCreatedUser(new Long(1));
        assertEquals("getCreatedUser and setCreatedUser failure occured", new Long(1), contest.getCreatedUser());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#setCreatedUser(Long)} and
     * {@link Contest#getCreatedUser()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * Valid.
     * </p>
     */
    public void test_accuracy_setCreatedUser() {
        // set the value to test
        contest.setCreatedUser(1L);
        assertEquals("getCreatedUser and setCreatedUser failure occured", 1L, (long) contest.getCreatedUser());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        Contest contest1 = new Contest();
        contest1.setContestId(1L);
        contest.setContestId(1L);
        assertTrue("failed equals", contest1.equals(contest));
        assertTrue("failed hashCode", contest1.hashCode() == contest.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        Contest contest1 = new Contest();
        contest1.setContestId(2L);
        contest.setContestId(1L);
        assertFalse("failed equals", contest1.equals(contest));
        assertFalse("failed hashCode", contest1.hashCode() == contest.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link Contest#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object contest1 = new Object();
        contest.setContestId(1L);
        assertFalse("failed equals", contest.equals(contest1));
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link Contest}</code>.
     * </p>
     */
    public void test_persistence() {
        try {
            HibernateUtil.getManager().getTransaction().begin();

            StudioFileType fileType = new StudioFileType();
            TestHelper.populateStudioFileType(fileType);
            HibernateUtil.getManager().persist(fileType);

            ContestChannel channel = new ContestChannel();
            TestHelper.populateContestChannel(channel);
            HibernateUtil.getManager().persist(channel);

            ContestType contestType = new ContestType();
            TestHelper.populateContestType(contestType);
            contestType.setContestType(1L);
            HibernateUtil.getManager().persist(contestType);

            ContestStatus status = new ContestStatus();
            status.setDescription("description");
            status.setName("Name");
            status.setContestStatusId(10L);
            status.setStatusId(1L);
            HibernateUtil.getManager().persist(status);

            Date date = new Date();
            ContestGeneralInfo generalInfo = new ContestGeneralInfo();
            generalInfo.setBrandingGuidelines("guideline");
            generalInfo.setDislikedDesignsWebsites("disklike");
            generalInfo.setGoals("goal");
            generalInfo.setOtherInstructions("instruction");
            generalInfo.setTargetAudience("target audience");
            generalInfo.setWinningCriteria("winning criteria");

            ContestMultiRoundInformation multiRoundInformation = new ContestMultiRoundInformation();
            multiRoundInformation.setMilestoneDate(new Date());
            multiRoundInformation.setRoundOneIntroduction("round one");
            multiRoundInformation.setRoundTwoIntroduction("round two");

            ContestSpecifications specifications = new ContestSpecifications();
            specifications.setAdditionalRequirementsAndRestrictions("none");
            specifications.setColors("white");
            specifications.setFonts("Arial");
            specifications.setLayoutAndSize("10px");

            PrizeType prizeType = new PrizeType();
            prizeType.setDescription("Good");
            prizeType.setPrizeTypeId(1L);
            HibernateUtil.getManager().persist(prizeType);

            MilestonePrize milestonePrize = new MilestonePrize();
            milestonePrize.setAmount(10.0);
            milestonePrize.setCreateDate(new Date());
            milestonePrize.setNumberOfSubmissions(1);
            milestonePrize.setType(prizeType);

            Contest entity = new Contest();

            TestHelper.populateContest(entity, date, channel, contestType, status, generalInfo, multiRoundInformation,
                    specifications, milestonePrize);

            Set<Medium> media = new HashSet<Medium>();
            Medium medium1 = new Medium();
            medium1.setMediumId(1L);
            medium1.setDescription("Web");
            HibernateUtil.getManager().persist(medium1);
            media.add(medium1);
            Medium medium2 = new Medium();
            medium2.setMediumId(2L);
            medium2.setDescription("Application");
            HibernateUtil.getManager().persist(medium2);
            media.add(medium2);
            entity.setMedia(media);
            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            Contest persisted = (Contest) HibernateUtil.getManager().find(Contest.class, entity.getContestId());
            assertEquals("Failed to persist - contestCategory mismatch", entity.getContestChannel(), persisted
                    .getContestChannel());
            assertEquals("Failed to persist - name contestType", entity.getContestType(), persisted.getContestType());
            assertEquals("Failed to persist - createdUser mismatch", entity.getCreatedUser(), persisted
                    .getCreatedUser());
            assertEquals("Failed to persist - endDate mismatch", entity.getEndDate(), persisted.getEndDate());
            assertEquals("Failed to persist - eventId mismatch", entity.getEventId(), persisted.getEventId());
            assertEquals("Failed to persist - forumId mismatch", entity.getForumId(), persisted.getForumId());
            assertEquals("Failed to persist - projectId mismatch", entity.getProjectId(), persisted.getProjectId());
            assertEquals("Failed to persist - name mismatch", entity.getName(), persisted.getName());
            assertEquals("Failed to persist - startDate mismatch", entity.getStartDate(), persisted.getStartDate());
            assertEquals("Failed to persist - status mismatch", entity.getStatus(), persisted.getStatus());
            assertEquals("Failed to persist - tcDirectProjectId mismatch", entity.getTcDirectProjectId(), persisted
                    .getTcDirectProjectId());
            assertEquals("Failed to persist - winnerAnnoucementDeadline mismatch", entity
                    .getWinnerAnnoucementDeadline(), persisted.getWinnerAnnoucementDeadline());
            assertNotNull("Failed to persist - milestone prize mismatch", persisted.getMilestonePrize());
            assertEquals("Failed to persist - milestone prize mismatch", entity.getMilestonePrize()
                    .getNumberOfSubmissions(), persisted.getMilestonePrize().getNumberOfSubmissions());
            assertNotNull("Failed to persist - contest genreral info mismatch", persisted.getGeneralInfo());
            assertEquals("Failed to persist - goals mismatch", entity.getGeneralInfo().getGoals(), persisted
                    .getGeneralInfo().getGoals());
            assertEquals("Failed to persist - audience mismatch", entity.getGeneralInfo().getTargetAudience(),
                    persisted.getGeneralInfo().getTargetAudience());
            assertEquals("Failed to persist - guideline mismatch", entity.getGeneralInfo().getBrandingGuidelines(),
                    persisted.getGeneralInfo().getBrandingGuidelines());
            assertEquals("Failed to persist - websites mismatch", entity.getGeneralInfo().getDislikedDesignsWebsites(),
                    persisted.getGeneralInfo().getDislikedDesignsWebsites());
            assertEquals("Failed to persist - other instruction mismatch", entity.getGeneralInfo()
                    .getOtherInstructions(), persisted.getGeneralInfo().getOtherInstructions());
            assertEquals("Failed to persist - criteria mismatch", entity.getGeneralInfo().getWinningCriteria(),
                    persisted.getGeneralInfo().getWinningCriteria());
            assertNotNull("Failed to persist - specifications mismatch", persisted.getSpecifications());
            assertEquals("Failed to persist - colors mismatch", entity.getSpecifications().getColors(), persisted
                    .getSpecifications().getColors());
            assertEquals("Failed to persist - fonts mismatch", entity.getSpecifications().getFonts(), persisted
                    .getSpecifications().getFonts());
            assertEquals("Failed to persist - layout and size mismatch", entity.getSpecifications().getLayoutAndSize(),
                    persisted.getSpecifications().getLayoutAndSize());
            assertEquals("Failed to persist - restriction mismatch", entity.getSpecifications()
                    .getAdditionalRequirementsAndRestrictions(), persisted.getSpecifications()
                    .getAdditionalRequirementsAndRestrictions());
            assertNotNull("Failed to persist - multi-round information mismatch", persisted.getMultiRoundInformation());
            assertEquals("Failed to persist - multi-round information mismatch", entity.getMultiRoundInformation()
                    .getRoundOneIntroduction(), persisted.getMultiRoundInformation().getRoundOneIntroduction());
            assertEquals("Failed to persist - fonts mismatch", entity.getMultiRoundInformation()
                    .getRoundTwoIntroduction(), persisted.getMultiRoundInformation().getRoundTwoIntroduction());
            assertEquals("Failed to persist - media mismatch", 2, persisted.getMedia().size());


            // update the entity
            entity.setName("new name");
            entity.getGeneralInfo().setGoals("new goal");
            HibernateUtil.getManager().merge(entity);

            persisted = (Contest) HibernateUtil.getManager().find(Contest.class, entity.getContestId());
            assertEquals("Failed to update - name mismatch", entity.getName(), persisted.getName());
            assertEquals("Failed to update - goal mismatch", entity.getGeneralInfo().getGoals(), persisted
                    .getGeneralInfo().getGoals());

            // delete the entity
            HibernateUtil.getManager().remove(entity);

            // verify cascade delete
            Query query = HibernateUtil.getManager().createQuery("FROM ContestGeneralInfo");
            assertEquals("general info is deleted cascadingly.", 0, query.getResultList().size());
            query = HibernateUtil.getManager().createQuery("FROM MilestonePrize");
            assertEquals("milestone prize is deleted cascadingly.", 0, query.getResultList().size());
            query = HibernateUtil.getManager().createQuery("FROM ContestSpecifications");
            assertEquals("contest specifications is deleted cascadingly.", 0, query.getResultList().size());
            query = HibernateUtil.getManager().createQuery("FROM ContestMultiRoundInformation");
            assertEquals("contest multi-round information is deleted cascadingly.", 0, query.getResultList().size());
            HibernateUtil.getManager().remove(channel);
            HibernateUtil.getManager().remove(fileType);
            HibernateUtil.getManager().remove(contestType);
            HibernateUtil.getManager().remove(status);
            HibernateUtil.getManager().remove(prizeType);
            HibernateUtil.getManager().remove(medium1);
            HibernateUtil.getManager().remove(medium2);

        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
