/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import com.topcoder.service.studio.submission.MilestonePrize;
import com.topcoder.service.studio.submission.PrizeType;

/**
 * <p>
 * Test helper class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.3
 * @since 1.0
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
     * @return the contest.
     */
    public static Contest initContests(EntityManager entityManager, List entities) {

        entityManager.getTransaction().begin();

        StudioFileType fileType = new StudioFileType();
        populateStudioFileType(fileType);
        entityManager.persist(fileType);
        entities.add(fileType);

        ContestChannel channel = new ContestChannel();
        populateContestChannel(channel);
        entityManager.persist(channel);
        entities.add(channel);

        ContestType contestType = new ContestType();
        populateContestType(contestType);
        contestType.setContestType(1L);
        entityManager.persist(contestType);
        entities.add(contestType);

        ContestStatus status = new ContestStatus();
        status.setDescription("description");
        status.setName("Name");
        status.setContestStatusId(10L);
        status.setStatusId(1L);
        entityManager.persist(status);
        entities.add(status);

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
        entityManager.persist(prizeType);
        entities.add(prizeType);

        MilestonePrize milestonePrize = new MilestonePrize();
        milestonePrize.setAmount(10.0);
        milestonePrize.setCreateDate(new Date());
        milestonePrize.setNumberOfSubmissions(1);
        milestonePrize.setType(prizeType);

        Contest entity = new Contest();

        populateContest(entity, date, channel, contestType, status,
                generalInfo, multiRoundInformation, specifications,
                milestonePrize);

        Set<Medium> media = new HashSet<Medium>();
        Medium medium1 = new Medium();
        medium1.setMediumId(1L);
        medium1.setDescription("Web");
        entityManager.persist(medium1);
        media.add(medium1);
        Medium medium2 = new Medium();
        medium2.setMediumId(2L);
        medium2.setDescription("Application");
        entityManager.persist(medium2);
        media.add(medium2);
        entity.setMedia(media);

        // save the resource
        ContestResource resource1 = new ContestResource();
        resource1.setName("myresource");
        resource1.setResourceId(1L);
        //entityManager.persist(resource1);
        ContestResource resource2 = new ContestResource();
        resource2.setName("myresource");
        resource2.setResourceId(2L);
        //entityManager.persist(resource2);

        Set<ContestResource> resources = new HashSet<ContestResource>();
        resources.add(resource1);
        resources.add(resource2);
        entity.setResources(resources);

        Contest entity2 = new Contest();
        populateContest(entity2, date, channel, contestType, status,
                generalInfo, multiRoundInformation, specifications,
                milestonePrize);
        entity2.setMedia(media);
        entity2.setResources(resources);

        // save the entity
        entityManager.persist(entity);
        entityManager.persist(entity2);
        entities.add(entity);
        entities.add(entity2);

        entityManager.getTransaction().commit();

        return entity;
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

    /**
     * Sets all required values for contest.
     *
     * @param entity
     *            the contest entity
     * @param date
     *            date to set
     * @param contestChannel
     *            category to set
     * @param contestType
     *            contest type
     * @param status
     *            contest status
     * @param generalInfo
     *            general info
     * @param multiRoundInformation
     *            multi-round info
     * @param specifications
     *            contest specification
     * @param milestonePrize
     *            the milestone prize
     */
    public static void populateContest(Contest entity, Date date,
            ContestChannel contestChannel, ContestType contestType,
            ContestStatus status, ContestGeneralInfo generalInfo,
            ContestMultiRoundInformation multiRoundInformation,
            ContestSpecifications specifications, MilestonePrize milestonePrize) {
        entity.setContestChannel(contestChannel);
        entity.setContestType(contestType);
        entity.setCreatedUser(10L);
        entity.setEndDate(date);
        entity.setEventId(101L);
        entity.setForumId(1000L);
        entity.setName("name");
        entity.setProjectId(101L);
        entity.setStartDate(date);
        entity.setStatus(status);
        entity.setStatusId(1L);
        entity.setTcDirectProjectId(1L);
        entity.setWinnerAnnoucementDeadline(date);
        entity.setGeneralInfo(generalInfo);
        entity.setSpecifications(specifications);
        entity.setMultiRoundInformation(multiRoundInformation);
        entity.setMilestonePrize(milestonePrize);
    }

    /**
     * Sets all the required values studio file type.
     *
     * @param entity
     *            the entity to set
     */
    public static void populateStudioFileType(StudioFileType entity) {
        entity.setDescription("description");
        entity.setExtension("extension");
        entity.setImageFile(true);
        entity.setSort(10);
    }

    /**
     * Sets all the required values for contest category.
     *
     * @param entity
     *            the entity to be set
     */
    public static void populateContestChannel(ContestChannel entity) {
        entity.setDescription("description");
        entity.setContestChannelId(18L);
    }

    /**
     * Sets all the required values for contest type.
     *
     * @param contestType
     *            the entity to be set.
     */
    public static void populateContestType(ContestType contestType) {
        contestType.setDescription("description");
        contestType.setRequirePreviewFile(true);
        contestType.setRequirePreviewImage(true);
        contestType.setContestType(3311L);
    }

}
