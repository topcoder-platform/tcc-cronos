/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import java.util.Date;
import java.util.HashSet;

import junit.framework.TestCase;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestGeneralInfo;
import com.topcoder.service.studio.contest.ContestMultiRoundInformation;
import com.topcoder.service.studio.contest.ContestResource;
import com.topcoder.service.studio.contest.ContestSpecifications;
import com.topcoder.service.studio.submission.MilestonePrize;
import com.topcoder.service.studio.submission.PrizeType;
import com.topcoder.service.studio.submission.Submission;


/**
 * <p>
 * The base stress test class used for all stress test cases.
 * </p>
 *
 * @author WN, morehappiness
 * @version 1.2
 *
 */
public class BaseStressTest extends TestCase {
	private static Configuration config;
    /**
     * The Session instance used for tests.
     */
    protected SessionFactory factory = null;

    /**
     * The Session instance used for tests.
     */
    protected Session session = null;

    /**
     * <p>
     * Sets up the necessary environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        factory = createSessionFactory();
        session = factory.openSession();
    }

    /**
     * <p>
     * Tears down the environment.
     * </P>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        clearTables();
        session.close();
        factory.close();
    }

    /**
     * <p>
     * Creates a SessionFactory instance used for tests.
     * </p>
     *
     * @return the SessionFactory instance
     *
     * @throws Exception
     *             to JUnit.
     */
    private SessionFactory createSessionFactory() throws Exception {
        if(config == null) config = new Configuration().configure(
                "hibernate.cfg.xml");

        return config.buildSessionFactory();
    }

    /**
     * <p>
     * Clears all tables in database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    private void clearTables() throws Exception {
        SessionFactory factory = createSessionFactory();
        Session session = factory.openSession();

        Transaction t = session.beginTransaction();
        String[] tables = new String[] {
            "delete from submission", "delete from submission_type_lu",
            "delete from submission_status_lu",
            "delete from submission_review",
            "delete from submission_prize_xref",
            "delete from review_status_lu", "delete from prize",
            "delete from prize_type_lu",
            "delete from path", "delete from mime_type_lu",
            "delete from document",
            "delete from document_type_lu", "delete from contest",
            "delete from contest_type_lu", "delete from contest_type_config",
            "delete from contest_status_relation",
            "delete from contest_result",
            "delete from contest_registration",
            "delete from contest_prize_xref",
            "delete from contest_file_type_xref",
            "delete from contest_document_xref",
            "delete from contest_config", "delete from contest_channel_lu",
            "delete from contest_property_lu", "delete from file_type_lu",
            "delete from resource", "delete from contest_multi_round_information",
            "delete from contest_general_info", "delete from contest_specifications",
            "delete from contest_milestone_prize"
        };

        for (int i = 0; i < tables.length; i++) {
            Query query = session.createSQLQuery(tables[i]);
            query.executeUpdate();
        }

        t.commit();
        session.close();
        factory.close();
    }

    /**
     * <p>
     * Create a ContestGeneralInfo instance used for tests.
     * </p>
     *
     * @param i used for loop index
     * @return the ContestGeneralInfo instance
     */
    public ContestGeneralInfo createContestGeneralInfo(int i) {
        ContestGeneralInfo info = new ContestGeneralInfo();
 
        info.setBrandingGuidelines("brandingGuidelines" + i);
        info.setContestGeneralInfoId(i + 0L);
        info.setDislikedDesignsWebsites("dislikedDesignsWebsites" + i);
        info.setGoals("goals" + i);
        info.setOtherInstructions("otherInstructions" + i);
        info.setTargetAudience("targetAudience" + i);
        info.setWinningCriteria("winningCriteria" + i);
        return info;
    }

    /**
     * <p>
     * Create a MilestonePrize instance used for tests.
     * </p>
     *
     * @param i used for loop index
     * @return the MilestonePrize instance
     */
    public MilestonePrize createMilestonePrize(int i) {
        MilestonePrize prize = new MilestonePrize();
 
        prize.setMilestonePrizeId(i + 0L);
        prize.setAmount(i + 0.0);
        prize.setSubmissions(new HashSet<Submission>());
        prize.setCreateDate(new Date());
        prize.setNumberOfSubmissions(i);

        PrizeType prizeType = new PrizeType();
        prizeType.setDescription("prize");
        prizeType.setPrizeTypeId(i + 0L);
        prize.setType(prizeType);

        return prize;
    }
    
    /**
     * <p>
     * Create a ContestResource instance used for tests.
     * </p>
     *
     * @param i used for loop index
     * @return the ContestResource instance
     */
    public ContestResource createContestResource(int i) {
        ContestResource resource = new ContestResource();
 

        resource.setResourceId(i + 0L);
        resource.setName("resource" + i);

        return resource;
    }

    /**
     * <p>
     * Create a ContestSpecifications instance used for tests.
     * </p>
     *
     * @param i used for loop index
     * @return the ContestSpecifications instance
     */
    public ContestSpecifications createContestSpecifications(int i) {
        ContestSpecifications specifications = new ContestSpecifications();

        specifications.setContestSpecificationsId(i + 0L);
        specifications.setAdditionalRequirementsAndRestrictions("additionalRequirementsAndRestrictions" + i);
        specifications.setColors("color" + i);
        specifications.setFonts("font" + i);
        specifications.setLayoutAndSize("layout" + i);

        return specifications;
    }
    
    /**
     * <p>
     * Create a ContestMultiRoundInformation instance used for tests.
     * </p>
     *
     * @param i used for loop index
     * @return the ContestMultiRoundInformation instance
     * @since 1.2
     * @version 1.2
     */
    public ContestMultiRoundInformation createContestMultiRoundInformation(int i) {
        ContestMultiRoundInformation info = new ContestMultiRoundInformation();

        info.setContestMultiRoundInformationId(i + 0L);
        info.setMilestoneDate(new Date());
        info.setRoundOneIntroduction("roundOneIntroduction" + i);
        info.setRoundTwoIntroduction("roundTwoIntroduction" + i);
        info.setSubmittersLockedBetweenRounds(i % 2 == 0 ? true : false);

        return info;
    }
}
