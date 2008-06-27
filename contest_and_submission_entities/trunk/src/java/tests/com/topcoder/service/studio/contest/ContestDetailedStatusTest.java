/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.util.Date;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link ContestDetailedStatus} class.
 * </p>
 * 
 * @author superZZ
 * @version 1.0
 */
public class ContestDetailedStatusTest extends TestCase {
    /**
     * Represents the <code>ContestDetailedStatus</code> instance to test.
     */
    private ContestDetailedStatus contestDetailedStatus = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        contestDetailedStatus = new ContestDetailedStatus();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        contestDetailedStatus = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     * 
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestDetailedStatusTest.class);
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link ContestStatus}</code>.
     * </p>
     */
    public void test_persistence() {
        try {
            HibernateUtil.getManager().getTransaction().begin();

            StudioFileType fileType = new StudioFileType();
            TestHelper.populateStudioFileType(fileType);
            HibernateUtil.getManager().persist(fileType);

            ContestChannel contestCategory = new ContestChannel();
            TestHelper.populateContestCategory(contestCategory, fileType);
            contestCategory.setContestChannelId(1L);
            HibernateUtil.getManager().persist(contestCategory);

            ContestType contestType = new ContestType();
            TestHelper.populateContestType(contestType);
            contestType.setContestType(1L);
            HibernateUtil.getManager().persist(contestType);

            ContestStatus status = new ContestStatus();
            status.setDescription("description");
            status.setName("name");
            status.setContestStatusId(1L);
            HibernateUtil.getManager().persist(status);

            Date date = new Date();
            Contest contest = new Contest();

            TestHelper.populateContest(contest, date, contestCategory, contestType, status);
            contest.setContestChannel(contestCategory);

            // save the entity
            HibernateUtil.getManager().persist(contest);

            ContestDetailedStatus entity = new ContestDetailedStatus();
            entity.setDescription("description");
            entity.setContestStatus(status);

            // save the entity
            HibernateUtil.getManager().persist(entity);

            Set<ContestDetailedStatus> contestDetailedStatuses = contest.getContestDetailedStatuses();
            contestDetailedStatuses.add(entity);

            // load the persisted object
            ContestDetailedStatus persisted = (ContestDetailedStatus) HibernateUtil.getManager().find(
                    ContestDetailedStatus.class, entity.getContestDetailedStatusId());
            assertEquals("Failed to persist - description mismatch", entity.getDescription(), persisted
                    .getDescription());

            contest = (Contest) HibernateUtil.getManager().find(Contest.class, contest.getContestId());
            assertEquals(1, contest.getContestDetailedStatuses().size());

            // delete the entity
            HibernateUtil.getManager().remove(entity);
            HibernateUtil.getManager().remove(contest);
            HibernateUtil.getManager().remove(contestCategory);
            HibernateUtil.getManager().remove(fileType);
            HibernateUtil.getManager().remove(contestType);
            HibernateUtil.getManager().remove(status);

        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
