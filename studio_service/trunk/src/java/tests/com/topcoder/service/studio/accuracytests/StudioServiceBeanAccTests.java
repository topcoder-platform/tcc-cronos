/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.accuracytests;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import junit.framework.TestCase;

import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.ContestGeneralInfoData;
import com.topcoder.service.studio.ContestMultiRoundInformationData;
import com.topcoder.service.studio.ContestSpecificationsData;
import com.topcoder.service.studio.MilestonePrizeData;
import com.topcoder.service.studio.SubmissionData;
import com.topcoder.service.studio.ejb.StudioServiceBean;

/**
 * Accuracy tests for <code>StudioServiceBean</code> class, mainly tests the methods
 * added in version 1.3.
 * 
 * @author myxgyy
 * @version 1.3
 */
public class StudioServiceBeanAccTests extends TestCase {
    /**
     * The target instance to be tested.
     */
    private StudioServiceBean bean;

    /**
     * The submission manager used for testing.
     */
    private SubmissionManagerImpl submissionManager;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     * 
     * @throws Exception
     *             to junit
     */
    public void setUp() throws Exception {
        bean = new StudioServiceBean();
        submissionManager = new SubmissionManagerImpl();
        setPrivateField("sessionContext", new MockSessionContext());
        setPrivateField("submissionManager", submissionManager);
        setPrivateField("contestManager", new ContestManagerImpl());
        callInit();
    }

    /**
     * Used to set contents of private field in tested bean (inject resources).
     * 
     * @param fieldName
     *            name of the field to set
     * @param value
     *            value to set in the field
     * @throws Exception
     *             to JUnit.
     */
    private void setPrivateField(String fieldName, Object value)
            throws Exception {
        Field f = StudioServiceBean.class.getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(bean, value);
    }

    /**
     * Simple routine to call private method init through reflection api.
     * 
     * @throws Exception
     *             to JUnit.
     */
    private void callInit() throws Exception {
        Method m = bean.getClass().getDeclaredMethod("init");
        m.setAccessible(true);
        m.invoke(bean);
    }

    /**
     * Tests <code>getUserContests(String)</code> method for accuracy.
     * 
     * @throws Exception
     *             to JUnit.
     */
    public void testGetUserContestsAcc1() throws Exception {
        List<ContestData> result = bean.getUserContests("myxgyy");
        assertEquals(1, result.size());
        ContestData data = result.get(0);
        assertEquals(data.getDetailedStatusId(), 1002L);

        ContestGeneralInfoData info = data.getGeneralInfo();
        assertEquals(info.getBrandingGuidelines(), "brandingGuidelines");
        assertEquals(info.getDislikedDesignsWebsites(),
                "dislikedDesignsWebsites");
        assertEquals(info.getGoals(), "goals");
        assertEquals(info.getOtherInstructions(), "otherInstructions");
        assertEquals(info.getTargetAudience(), "targetAudience");

        MilestonePrizeData prize = data.getMilestonePrizeData();
        assertEquals(prize.getAmount(), 20.02d, 0.0001);
        assertEquals(prize.getNumberOfSubmissions().intValue(), 10);

        ContestSpecificationsData spe = data.getSpecifications();
        assertEquals(spe.getAdditionalRequirementsAndRestrictions(), "not null");
        assertEquals(spe.getColors(), "red");
        assertEquals(spe.getFonts(), "Arial");
        assertEquals(spe.getLayoutAndSize(), "10px");

        ContestMultiRoundInformationData multi = data.getMultiRoundData();
        assertEquals(multi.getRoundOneIntroduction(), "round one");
        assertEquals(multi.getRoundTwoIntroduction(), "round two");
        assertTrue(multi.getSubmittersLockedBetweenRounds());
    }

    /**
     * Tests <code>getUserContests(String)</code> method for accuracy. Expected empty
     * list returned.
     * 
     * @throws Exception
     *             to JUnit.
     */
    public void testGetUserContestsAcc2() throws Exception {
        List<ContestData> result = bean.getUserContests("not myxgyy");
        assertEquals(result.size(), 0);
    }

    /**
     * Tests <code>getMilestoneSubmissionsForContest(long)</code> method for accuracy.
     * 
     * @throws Exception
     *             to JUnit.
     */
    public void testGetMilestoneSubmissionsForContestAcc1() throws Exception {
        List<SubmissionData> result = bean.getMilestoneSubmissionsForContest(1);
        assertEquals(1, result.size());
        SubmissionData data = result.get(0);
        assertEquals(data.getSubmissionType(), "Milestone");
    }

    /**
     * Tests <code>getMilestoneSubmissionsForContest(long)</code> method for accuracy.
     * Expected empty list returned.
     * 
     * @throws Exception
     *             to JUnit.
     */
    public void testGetMilestoneSubmissionsForContestAcc2() throws Exception {
        List<SubmissionData> result = bean.getMilestoneSubmissionsForContest(2);
        assertEquals(0, result.size());
    }

    /**
     * Tests <code>getFinalSubmissionsForContest(long contestId)</code> method for
     * accuracy.
     * 
     * @throws Exception
     *             to JUnit.
     */
    public void testGetFinalSubmissionsForContestAcc1() throws Exception {
        List<SubmissionData> result = bean.getFinalSubmissionsForContest(1);
        assertEquals(1, result.size());
        SubmissionData data = result.get(0);
        assertEquals(data.getSubmissionType(), "final fix");
    }

    /**
     * Tests <code>getFinalSubmissionsForContest(long contestId)</code> method for
     * accuracy. Expected empty list returned.
     * 
     * @throws Exception
     *             to JUnit.
     */
    public void testGetFinalSubmissionsForContestAcc2() throws Exception {
        List<SubmissionData> result = bean.getFinalSubmissionsForContest(2);
        assertEquals(0, result.size());
    }

    /**
     * Tests <code>setSubmissionMilestonePrize(long, long)</code> method for accuracy.
     * 
     * @throws Exception
     *             to JUnit.
     */
    public void testSetSubmissionMilestonePrize() throws Exception {
        bean.setSubmissionMilestonePrize(1, 1);
        assertTrue(submissionManager.getMilestonePrize().containsKey(new Long(1)));
        assertEquals(submissionManager.getMilestonePrize().get(new Long(1)).longValue(), 1);
    }
}
