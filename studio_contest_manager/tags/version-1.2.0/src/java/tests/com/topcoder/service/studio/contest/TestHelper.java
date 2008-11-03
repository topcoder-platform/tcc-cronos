/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

/**
 * <p>
 * Test helper class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class TestHelper {
    /**
     * <p>
     * Private ctor.
     * </p>
     */
    private TestHelper() {
        // do nothing
    }

    /**
     * <p>
     * Sets up contest data.
     * </p>
     *
     * @param entityManager entity manager
     * @param entities to store objects created
     */
    public static void initContests(EntityManager entityManager, List entities) {
        // set up data
        entityManager.getTransaction().begin();
        StudioFileType fileType = new StudioFileType();
        fileType.setDescription("desc");
        fileType.setExtension("jpeg");
        fileType.setImageFile(false);
        fileType.setSort(new Integer(10));
        entityManager.persist(fileType);
        entities.add(fileType);

        ContestChannel contestChannel = new ContestChannel();
        contestChannel.setDescription("desc");
        contestChannel.setName("channel");
        contestChannel.setFileType(fileType);
        contestChannel.setParentChannelId(new Long(1));
        entityManager.persist(contestChannel);
        entities.add(contestChannel);

        ContestType contestType = new ContestType();
        contestType.setDescription("desc");
        contestType.setRequirePreviewFile(false);
        contestType.setRequirePreviewImage(false);
        entityManager.persist(contestType);
        entities.add(contestType);

        ContestStatus status = new ContestStatus();
        status.setDescription("description");
        status.setName("status");
        entityManager.persist(status);
        entities.add(status);

        status = new ContestStatus();
        status.setDescription("description");
        status.setName("another_status");
        entityManager.persist(status);
        entities.add(status);

        entityManager.flush();

        Contest contest = new Contest();
        contest.setName("contest1");
        contest.setCreatedUser(new Long(1));
        contest.setContestChannel(contestChannel);
        contest.setContestType(contestType);
        contest.setEndDate(new Date());
        contest.setEventId(new Long(1));
        contest.setForumId(new Long(1));
        contest.setProjectId(new Long(1));
        contest.setTcDirectProjectId(new Long(1));
        contest.setStartDate(new Date());
        contest.setWinnerAnnoucementDeadline(new Date());
        contest.setStatus(status);
        entityManager.persist(contest);
        entities.add(contest);

        contest = new Contest();
        contest.setName("contest2");
        contest.setCreatedUser(new Long(2));
        contest.setContestChannel(contestChannel);
        contest.setContestType(contestType);
        contest.setEndDate(new Date());
        contest.setEventId(new Long(2));
        contest.setForumId(new Long(2));
        contest.setProjectId(new Long(1));
        contest.setTcDirectProjectId(new Long(1));
        contest.setStartDate(new Date());
        contest.setWinnerAnnoucementDeadline(new Date());
        contest.setStatus(status);
        entityManager.persist(contest);
        entities.add(contest);

        entityManager.getTransaction().commit();
    }

    /**
     * <p>
     * Remove data previously set.
     * </p>
     *
     * @param entityManager entity manager
     * @param entities contains objects to be removed
     */
    public static void removeContests(EntityManager entityManager, List entities) {
        if (entities.size() == 0) {
            return;
        }

        Collections.reverse(entities);
        entityManager.getTransaction().begin();
        for (Object entity : entities) {
            entityManager.remove(entity);
        }
        entityManager.getTransaction().commit();
    }

}
