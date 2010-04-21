/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.Technology;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.pipeline.CompetitionType;
import com.topcoder.service.project.CompetitionPrize;
import com.topcoder.service.studio.ContestMultiRoundInformationData;
import com.topcoder.service.studio.ContestSpecificationsData;
import com.topcoder.service.studio.MediumData;
import com.topcoder.service.studio.MilestonePrizeData;
import com.topcoder.service.studio.PrizeData;

/**
 * <p>
 * Unit test for <code>{@link SaveDraftContestAction}</code> class.
 * </p>
 *
 * @author FireIce
 * @version 1.0
 */
public class SaveDraftContestActionUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>SaveDraftContestAction</code> instance.
     * </p>
     */
    private SaveDraftContestAction saveDraftContestAction;

    /**
     * <p>
     * Represents the <code>ContestServiceFacade</code> instance.
     * </p>
     */
    private ContestServiceFacade contestServiceFacade;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(SaveDraftContestActionUnitTests.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        saveDraftContestAction = new SaveDraftContestAction();
        contestServiceFacade = new MockContestServiceFacade();
        saveDraftContestAction.setContestServiceFacade(contestServiceFacade);

        saveDraftContestAction.prepare();
    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        saveDraftContestAction = null;

        super.tearDown();
    }

    /**
     * <p>
     * Tests the <code>SaveDraftContestAction()</code> constructor.
     * </p>
     * <p>
     * The constructor simply do nothing, instance should be created.
     * </p>
     */
    public void testCtor() {
        assertNotNull("instance should not null", saveDraftContestAction);

        assertTrue("invalid super class.", saveDraftContestAction instanceof ContestAction);
    }

    /**
     * <p>
     * Tests the <code>getProjectId()</code> method.
     * </p>
     * <p>
     * By default, The project id is 0.
     * </p>
     */
    public void testGetProjectId_default() {
        assertEquals("The projectId should be 0", 0, saveDraftContestAction.getProjectId());
    }

    /**
     * <p>
     * Tests the <code>setProjectId(long)</code> method.
     * </p>
     * <p>
     * The projectId should be set internally.
     * </p>
     */
    public void testSetProjectId_accuracy() {
        long projectId = 100;
        saveDraftContestAction.setProjectId(projectId);

        assertEquals("field not set.", projectId, saveDraftContestAction.getProjectId());
    }

    /**
     * <p>
     * Tests the <code>getContestId()</code> method.
     * </p>
     * <p>
     * By default, The project id is 0.
     * </p>
     */
    public void testGetContestId_default() {
        assertEquals("The projectId should be 0", 0, saveDraftContestAction.getContestId());
    }

    /**
     * <p>
     * Tests the <code>setContestId(long)</code> method.
     * </p>
     * <p>
     * The projectId should be set internally.
     * </p>
     */
    public void testSetContestId_accuracy() {
        long contestId = 100;
        saveDraftContestAction.setContestId(contestId);

        assertEquals("field not set.", contestId, saveDraftContestAction.getContestId());
    }

    /**
     * <p>
     * Tests the <code>getCompetitionType()</code> method.
     * </p>
     * <p>
     * By default, The competitionType is null.
     * </p>
     */
    public void testGetCompetitionType_default() {
        assertNull("The competitionType should be null.", saveDraftContestAction.getCompetitionType());
    }

    /**
     * <p>
     * Tests the <code>setCompetitionType(CompetitionType)</code> method.
     * </p>
     * <p>
     * The competitionType should be set internally.
     * </p>
     */
    public void testSetCompetitionType_accuracy() {
        CompetitionType competitionType = CompetitionType.STUDIO;
        saveDraftContestAction.setCompetitionType(competitionType);

        assertEquals("field not set.", competitionType, saveDraftContestAction.getCompetitionType());
    }

    /**
     * <p>
     * Tests the <code>getStartDate()</code> method.
     * </p>
     * <p>
     * By default, The startDate is null.
     * </p>
     */
    public void testGetStartDate_default() {
        assertNull("The startDate should be null.", saveDraftContestAction.getStartDate());
    }

    /**
     * <p>
     * Tests the <code>setStartDate(StartDate)</code> method.
     * </p>
     * <p>
     * The startDate should be set internally.
     * </p>
     */
    public void testSetStartDate_accuracy() {
        Date startDate = new Date();
        saveDraftContestAction.setStartDate(startDate);

        assertEquals("field not set.", startDate, saveDraftContestAction.getStartDate());
    }

    /**
     * <p>
     * Tests the <code>getStartDateHour()</code> method.
     * </p>
     * <p>
     * By default, The startDateHour is 0.
     * </p>
     */
    public void testGetStartDateHour_default() {
        assertEquals("The startDateHour should be 0.", 0, saveDraftContestAction.getStartDateHour());
    }

    /**
     * <p>
     * Tests the <code>setStartDateHour(int)</code> method.
     * </p>
     * <p>
     * The startDateHour should be set internally.
     * </p>
     */
    public void testSetStartDateHour_accuracy() {
        saveDraftContestAction.setStartDateHour(1);

        assertEquals("field not set.", 1, saveDraftContestAction.getStartDateHour());
    }

    /**
     * <p>
     * Tests the <code>getStartDateMinute()</code> method.
     * </p>
     * <p>
     * By default, The startDateMinute is 0.
     * </p>
     */
    public void testGetStartDateMinute_default() {
        assertEquals("The startDateMinute should be 0.", 0, saveDraftContestAction.getStartDateMinute());
    }

    /**
     * <p>
     * Tests the <code>setStartDateMinute(int)</code> method.
     * </p>
     * <p>
     * The startDateMinute should be set internally.
     * </p>
     */
    public void testSetStartDateMinute_accuracy() {
        saveDraftContestAction.setStartDateMinute(1);

        assertEquals("field not set.", 1, saveDraftContestAction.getStartDateMinute());
    }

    /**
     * <p>
     * Tests the <code>getEndDate()</code> method.
     * </p>
     * <p>
     * By default, The endDate is null.
     * </p>
     */
    public void testGetEndDate_default() {
        assertNull("The endDate should be null.", saveDraftContestAction.getEndDate());
    }

    /**
     * <p>
     * Tests the <code>setEndDate(EndDate)</code> method.
     * </p>
     * <p>
     * The endDate should be set internally.
     * </p>
     */
    public void testSetEndDate_accuracy() {
        Date endDate = new Date();
        saveDraftContestAction.setEndDate(endDate);

        assertEquals("field not set.", endDate, saveDraftContestAction.getEndDate());
    }

    /**
     * <p>
     * Tests the <code>getEndDateHour()</code> method.
     * </p>
     * <p>
     * By default, The endDateHour is 0.
     * </p>
     */
    public void testGetEndDateHour_default() {
        assertEquals("The endDateHour should be 0.", 0, saveDraftContestAction.getEndDateHour());
    }

    /**
     * <p>
     * Tests the <code>setEndDateHour(int)</code> method.
     * </p>
     * <p>
     * The endDateHour should be set internally.
     * </p>
     */
    public void testSetEndDateHour_accuracy() {
        saveDraftContestAction.setEndDateHour(1);

        assertEquals("field not set.", 1, saveDraftContestAction.getEndDateHour());
    }

    /**
     * <p>
     * Tests the <code>getEndDateMinute()</code> method.
     * </p>
     * <p>
     * By default, The endDateMinute is 0.
     * </p>
     */
    public void testGetEndDateMinute_default() {
        assertEquals("The endDateMinute should be 0.", 0, saveDraftContestAction.getEndDateMinute());
    }

    /**
     * <p>
     * Tests the <code>setEndDateMinute(int)</code> method.
     * </p>
     * <p>
     * The endDateMinute should be set internally.
     * </p>
     */
    public void testSetEndDateMinute_accuracy() {
        saveDraftContestAction.setEndDateMinute(1);

        assertEquals("field not set.", 1, saveDraftContestAction.getEndDateMinute());
    }

    /**
     * <p>
     * Tests the <code>getContestName()</code> method.
     * </p>
     * <p>
     * By default, The contestName is null.
     * </p>
     */
    public void testGetContestName_default() {
        assertNull("The contestName should be null.", saveDraftContestAction.getContestName());
    }

    /**
     * <p>
     * Tests the <code>setContestName(String)</code> method.
     * </p>
     * <p>
     * The contestName should be set internally.
     * </p>
     */
    public void testSetContestName_accuracy() {
        saveDraftContestAction.setContestName("TopCoder");

        assertEquals("field not set.", "TopCoder", saveDraftContestAction.getContestName());
    }

    /**
     * <p>
     * Tests the <code>getTcDirectProjectId()</code> method.
     * </p>
     * <p>
     * By default, The tcDirectProjectId is 0.
     * </p>
     */
    public void testGetStudioType_default() {
        assertEquals("The tcDirectProjectId should be 0.", 0, saveDraftContestAction.getTcDirectProjectId());
    }

    /**
     * <p>
     * Tests the <code>setTcDirectProjectId(long)</code> method.
     * </p>
     * <p>
     * The tcDirectProjectId should be set internally.
     * </p>
     */
    public void testSetTcDirectProjectId_accuracy() {
        saveDraftContestAction.setTcDirectProjectId(1);

        assertEquals("field not set.", 1, saveDraftContestAction.getTcDirectProjectId());
    }

    /**
     * <p>
     * Tests the <code>getCategory()</code> method.
     * </p>
     * <p>
     * By default, The category is null.
     * </p>
     */
    public void testGetCategory_default() {
        assertNull("The category should be null.", saveDraftContestAction.getCategory());
    }

    /**
     * <p>
     * Tests the <code>setCategory(String)</code> method.
     * </p>
     * <p>
     * The category should be set internally.
     * </p>
     */
    public void testSetCategory_accuracy() {
        saveDraftContestAction.setCategory("TopCoder");

        assertEquals("field not set.", "TopCoder", saveDraftContestAction.getCategory());
    }

    /**
     * <p>
     * Tests the <code>getReviewPayment()</code> method.
     * </p>
     * <p>
     * By default, The reviewPayment is 0.
     * </p>
     */
    public void testGetReviewPayment_default() {
        assertEquals("The reviewPayment should be 0.", 0, saveDraftContestAction.getReviewPayment(), 1e-4);
    }

    /**
     * <p>
     * Tests the <code>setReviewPayment(double)</code> method.
     * </p>
     * <p>
     * The reviewPayment should be set internally.
     * </p>
     */
    public void testSetReviewPayment_accuracy() {
        saveDraftContestAction.setReviewPayment(1f);

        assertEquals("field not set.", 1f, saveDraftContestAction.getReviewPayment(), 1e-4);
    }

    /**
     * <p>
     * Tests the <code>getSpecificationReviewPayment()</code> method.
     * </p>
     * <p>
     * By default, The specificationReviewPayment is 0.
     * </p>
     */
    public void testGetSpecificationReviewPayment_default() {
        assertEquals("The specificationReviewPayment should be 0.", 0, saveDraftContestAction
                .getSpecificationReviewPayment(), 1e-4);
    }

    /**
     * <p>
     * Tests the <code>setSpecificationReviewPayment(double)</code> method.
     * </p>
     * <p>
     * The specificationReviewPayment should be set internally.
     * </p>
     */
    public void testSetSpecificationReviewPayment_accuracy() {
        saveDraftContestAction.setSpecificationReviewPayment(1f);

        assertEquals("field not set.", 1f, saveDraftContestAction.getSpecificationReviewPayment(), 1e-4);
    }

    /**
     * <p>
     * Tests the <code>isHasWikiSpecification()</code> method.
     * </p>
     * <p>
     * By default, The hasWikiSpecification is false.
     * </p>
     */
    public void testIsHasWikiSpecification_default() {
        assertFalse("The hasWikiSpecification should be false.", saveDraftContestAction.isHasWikiSpecification());
    }

    /**
     * <p>
     * Tests the <code>setHasWikiSpecification(boolean)</code> method.
     * </p>
     * <p>
     * The hasWikiSpecification should be set internally.
     * </p>
     */
    public void testSetHasWikiSpecification_accuracy() {
        saveDraftContestAction.setHasWikiSpecification(true);

        assertTrue("field not set.", saveDraftContestAction.isHasWikiSpecification());
    }

    /**
     * <p>
     * Tests the <code>getNotes()</code> method.
     * </p>
     * <p>
     * By default, The notes is null.
     * </p>
     */
    public void testGetNotes_default() {
        assertNull("The notes should be null.", saveDraftContestAction.getNotes());
    }

    /**
     * <p>
     * Tests the <code>setNotes(String)</code> method.
     * </p>
     * <p>
     * The notes should be set internally.
     * </p>
     */
    public void testSetNotes_accuracy() {
        saveDraftContestAction.setNotes("TopCoder");

        assertEquals("field not set.", "TopCoder", saveDraftContestAction.getNotes());
    }

    /**
     * <p>
     * Tests the <code>getDrPoints()</code> method.
     * </p>
     * <p>
     * By default, The drPoints is 0.
     * </p>
     */
    public void testGetDrPoints_default() {
        assertEquals("The drPoints should be 0.", 0, saveDraftContestAction.getDrPoints(), 1e-4);
    }

    /**
     * <p>
     * Tests the <code>setDrPoints(double)</code> method.
     * </p>
     * <p>
     * The drPoints should be set internally.
     * </p>
     */
    public void testSetDrPoints_accuracy() {
        saveDraftContestAction.setDrPoints(1f);

        assertEquals("field not set.", 1f, saveDraftContestAction.getDrPoints(), 1e-4);
    }

    /**
     * <p>
     * Tests the <code>getShortDescription()</code> method.
     * </p>
     * <p>
     * By default, The shortDescription is null.
     * </p>
     */
    public void testGetShortDescription_default() {
        assertNull("The shortDescription should be null.", saveDraftContestAction.getShortDescription());
    }

    /**
     * <p>
     * Tests the <code>setShortDescription(String)</code> method.
     * </p>
     * <p>
     * The shortDescription should be set internally.
     * </p>
     */
    public void testSetShortDescription_accuracy() {
        saveDraftContestAction.setShortDescription("TopCoder");

        assertEquals("field not set.", "TopCoder", saveDraftContestAction.getShortDescription());
    }

    /**
     * <p>
     * Tests the <code>getDetailedDescription()</code> method.
     * </p>
     * <p>
     * By default, The detailedDescription is null.
     * </p>
     */
    public void testGetDetailedDescription_default() {
        assertNull("The detailedDescription should be null.", saveDraftContestAction.getDetailedDescription());
    }

    /**
     * <p>
     * Tests the <code>setDetailedDescription(String)</code> method.
     * </p>
     * <p>
     * The detailedDescription should be set internally.
     * </p>
     */
    public void testSetDetailedDescription_accuracy() {
        saveDraftContestAction.setDetailedDescription("TopCoder");

        assertEquals("field not set.", "TopCoder", saveDraftContestAction.getDetailedDescription());
    }

    /**
     * <p>
     * Tests the <code>getShortSummary()</code> method.
     * </p>
     * <p>
     * By default, The shortSummary is null.
     * </p>
     */
    public void testGetShortSummary_default() {
        assertNull("The shortSummary should be null.", saveDraftContestAction.getShortSummary());
    }

    /**
     * <p>
     * Tests the <code>setShortSummary(String)</code> method.
     * </p>
     * <p>
     * The shortSummary should be set internally.
     * </p>
     */
    public void testSetShortSummary_accuracy() {
        saveDraftContestAction.setShortSummary("TopCoder");

        assertEquals("field not set.", "TopCoder", saveDraftContestAction.getShortSummary());
    }

    /**
     * <p>
     * Tests the <code>getPrizes()</code> method.
     * </p>
     * <p>
     * By default, The prizes is not null.
     * </p>
     */
    public void testGetPrizes_default() {
        assertNotNull("The prizes should be not null.", saveDraftContestAction.getPrizes());
    }

    /**
     * <p>
     * Tests the <code>setPrizes(List)</code> method.
     * </p>
     * <p>
     * The prizes should be set internally.
     * </p>
     */
    public void testSetPrizes_accuracy() {
        List<CompetitionPrize> prizes = new ArrayList<CompetitionPrize>();
        saveDraftContestAction.setPrizes(prizes);

        assertSame("field not set.", prizes, saveDraftContestAction.getPrizes());
    }

    /**
     * <p>
     * Tests the <code>getFunctionalDescription()</code> method.
     * </p>
     * <p>
     * By default, The functionalDescription is null.
     * </p>
     */
    public void testGetFunctionalDescription_default() {
        assertNull("The functionalDescription should be null.", saveDraftContestAction.getFunctionalDescription());
    }

    /**
     * <p>
     * Tests the <code>setFunctionalDescription(String)</code> method.
     * </p>
     * <p>
     * The functionalDescription should be set internally.
     * </p>
     */
    public void testSetFunctionalDescription_accuracy() {
        saveDraftContestAction.setFunctionalDescription("TopCoder");

        assertEquals("field not set.", "TopCoder", saveDraftContestAction.getFunctionalDescription());
    }

    /**
     * <p>
     * Tests the <code>getCategories()</code> method.
     * </p>
     * <p>
     * By default, The categories is not null.
     * </p>
     */
    public void testGetCategories_default() {
        assertNotNull("The categories should be not null.", saveDraftContestAction.getCategories());
    }

    /**
     * <p>
     * Tests the <code>setCategories(List)</code> method.
     * </p>
     * <p>
     * The categories should be set internally.
     * </p>
     */
    public void testSetCategories_accuracy() {
        List<Category> categories = new ArrayList<Category>();
        saveDraftContestAction.setCategories(categories);

        assertSame("field not set.", categories, saveDraftContestAction.getCategories());
    }

    /**
     * <p>
     * Tests the <code>getTechnologies()</code> method.
     * </p>
     * <p>
     * By default, The technologies is not null.
     * </p>
     */
    public void testGetTechnologies_default() {
        assertNotNull("The technologies should be not null.", saveDraftContestAction.getTechnologies());
    }

    /**
     * <p>
     * Tests the <code>setTechnologies(List)</code> method.
     * </p>
     * <p>
     * The technologies should be set internally.
     * </p>
     */
    public void testSetTechnologies_accuracy() {
        List<Technology> technologies = new ArrayList<Technology>();
        saveDraftContestAction.setTechnologies(technologies);

        assertSame("field not set.", technologies, saveDraftContestAction.getTechnologies());
    }

    /**
     * <p>
     * Tests the <code>getPrizeDescription()</code> method.
     * </p>
     * <p>
     * By default, The prizeDescription is null.
     * </p>
     */
    public void testGetPrizeDescription_default() {
        assertNull("The prizeDescription should be null.", saveDraftContestAction.getPrizeDescription());
    }

    /**
     * <p>
     * Tests the <code>setPrizeDescription(String)</code> method.
     * </p>
     * <p>
     * The prizeDescription should be set internally.
     * </p>
     */
    public void testSetPrizeDescription_accuracy() {
        saveDraftContestAction.setPrizeDescription("TopCoder");

        assertEquals("field not set.", "TopCoder", saveDraftContestAction.getPrizeDescription());
    }

    /**
     * <p>
     * Tests the <code>getMaximumSubmissions()</code> method.
     * </p>
     * <p>
     * By default, The maximumSubmissions is 0.
     * </p>
     */
    public void testGetMaximumSubmissions_default() {
        assertEquals("The maximumSubmissions should be 0.", 0, saveDraftContestAction.getMaximumSubmissions());
    }

    /**
     * <p>
     * Tests the <code>setMaximumSubmissions(long)</code> method.
     * </p>
     * <p>
     * The maximumSubmissions should be set internally.
     * </p>
     */
    public void testSetMaximumSubmissions_accuracy() {
        saveDraftContestAction.setMaximumSubmissions(1);

        assertEquals("field not set.", 1, saveDraftContestAction.getMaximumSubmissions());
    }

    /**
     * <p>
     * Tests the <code>getPrizesData()</code> method.
     * </p>
     * <p>
     * By default, The prizesData is null.
     * </p>
     */
    public void testGetPrizesData_default() {
        assertNotNull("The prizesData should be null.", saveDraftContestAction.getPrizesData());
    }

    /**
     * <p>
     * Tests the <code>setPrizesData(List)</code> method.
     * </p>
     * <p>
     * if the prizes is null, should throw IllegalArgumentException.
     * </p>
     */
    public void testSetPrizesData_null() {
        try {
            saveDraftContestAction.setPrizesData(null);
            fail("if the prizes is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>setPrizesData(List)</code> method.
     * </p>
     * <p>
     * The prizesData should be set internally.
     * </p>
     */
    public void testSetPrizesData_accuracy() {
        List<PrizeData> prizes = new ArrayList<PrizeData>();
        saveDraftContestAction.setPrizesData(prizes);

        assertTrue("field not set.", saveDraftContestAction.getPrizesData().containsAll(prizes));
        assertTrue("field not set.", prizes.containsAll(saveDraftContestAction.getPrizesData()));
    }

    /**
     * <p>
     * Tests the <code>getMedia()</code> method.
     * </p>
     * <p>
     * By default, The media is null.
     * </p>
     */
    public void testGetMedia_default() {
        assertNotNull("The media should be null.", saveDraftContestAction.getMedia());
    }

    /**
     * <p>
     * Tests the <code>setMedia(List)</code> method.
     * </p>
     * <p>
     * The media should be set internally.
     * </p>
     */
    public void testSetMedia_accuracy() {
        List<MediumData> media = new ArrayList<MediumData>();
        saveDraftContestAction.setMedia(media);

        assertTrue("field not set.", saveDraftContestAction.getMedia().containsAll(media));
        assertTrue("field not set.", media.containsAll(saveDraftContestAction.getMedia()));
    }

    /**
     * <p>
     * Tests the <code>getContestDescriptionAndRequirements()</code> method.
     * </p>
     * <p>
     * By default, The contestDescriptionAndRequirements is null.
     * </p>
     */
    public void testGetContestDescriptionAndRequirements_default() {
        assertNull("The contestDescriptionAndRequirements should be null.", saveDraftContestAction
                .getContestDescriptionAndRequirements());
    }

    /**
     * <p>
     * Tests the <code>setContestDescriptionAndRequirements(String)</code> method.
     * </p>
     * <p>
     * The contestDescriptionAndRequirements should be set internally.
     * </p>
     */
    public void testSetContestDescriptionAndRequirements_accuracy() {
        saveDraftContestAction.setContestDescriptionAndRequirements("TopCoder");

        assertEquals("field not set.", "TopCoder", saveDraftContestAction.getContestDescriptionAndRequirements());
    }

    /**
     * <p>
     * Tests the <code>getRequiredOrRestrictedColors()</code> method.
     * </p>
     * <p>
     * By default, The requiredOrRestrictedColors is null.
     * </p>
     */
    public void testGetRequiredOrRestrictedColors_default() {
        assertNull("The requiredOrRestrictedColors should be null.", saveDraftContestAction
                .getRequiredOrRestrictedColors());
    }

    /**
     * <p>
     * Tests the <code>setRequiredOrRestrictedColors(String)</code> method.
     * </p>
     * <p>
     * The requiredOrRestrictedColors should be set internally.
     * </p>
     */
    public void testSetRequiredOrRestrictedColors_accuracy() {
        saveDraftContestAction.setRequiredOrRestrictedColors("TopCoder");

        assertEquals("field not set.", "TopCoder", saveDraftContestAction.getRequiredOrRestrictedColors());
    }

    /**
     * <p>
     * Tests the <code>getRequiredOrRestrictedFonts()</code> method.
     * </p>
     * <p>
     * By default, The requiredOrRestrictedFonts is null.
     * </p>
     */
    public void testGetRequiredOrRestrictedFonts_default() {
        assertNull("The requiredOrRestrictedFonts should be null.", saveDraftContestAction
                .getRequiredOrRestrictedFonts());
    }

    /**
     * <p>
     * Tests the <code>setRequiredOrRestrictedFonts(String)</code> method.
     * </p>
     * <p>
     * The requiredOrRestrictedFonts should be set internally.
     * </p>
     */
    public void testSetRequiredOrRestrictedFonts_accuracy() {
        saveDraftContestAction.setRequiredOrRestrictedFonts("TopCoder");

        assertEquals("field not set.", "TopCoder", saveDraftContestAction.getRequiredOrRestrictedFonts());
    }

    /**
     * <p>
     * Tests the <code>getSizeRequirements()</code> method.
     * </p>
     * <p>
     * By default, The sizeRequirements is null.
     * </p>
     */
    public void testGetSizeRequirements_default() {
        assertNull("The sizeRequirements should be null.", saveDraftContestAction.getSizeRequirements());
    }

    /**
     * <p>
     * Tests the <code>setSizeRequirements(String)</code> method.
     * </p>
     * <p>
     * The sizeRequirements should be set internally.
     * </p>
     */
    public void testSetSizeRequirements_accuracy() {
        saveDraftContestAction.setSizeRequirements("TopCoder");

        assertEquals("field not set.", "TopCoder", saveDraftContestAction.getSizeRequirements());
    }

    /**
     * <p>
     * Tests the <code>getOtherRequirementsOrRestrictions()</code> method.
     * </p>
     * <p>
     * By default, The otherRequirementsOrRestrictions is null.
     * </p>
     */
    public void testGetOtherRequirementsOrRestrictions_default() {
        assertNull("The otherRequirementsOrRestrictions should be null.", saveDraftContestAction
                .getOtherRequirementsOrRestrictions());
    }

    /**
     * <p>
     * Tests the <code>setOtherRequirementsOrRestrictions(String)</code> method.
     * </p>
     * <p>
     * The otherRequirementsOrRestrictions should be set internally.
     * </p>
     */
    public void testSetOtherRequirementsOrRestrictions_accuracy() {
        saveDraftContestAction.setOtherRequirementsOrRestrictions("TopCoder");

        assertEquals("field not set.", "TopCoder", saveDraftContestAction.getOtherRequirementsOrRestrictions());
    }

    /**
     * <p>
     * Tests the <code>getFinalFileFormat()</code> method.
     * </p>
     * <p>
     * By default, The finalFileFormat is null.
     * </p>
     */
    public void testGetFinalFileFormat_default() {
        assertNull("The finalFileFormat should be null.", saveDraftContestAction.getFinalFileFormat());
    }

    /**
     * <p>
     * Tests the <code>setFinalFileFormat(String)</code> method.
     * </p>
     * <p>
     * The finalFileFormat should be set internally.
     * </p>
     */
    public void testSetFinalFileFormat_accuracy() {
        saveDraftContestAction.setFinalFileFormat("TopCoder");

        assertEquals("field not set.", "TopCoder", saveDraftContestAction.getFinalFileFormat());
    }

    /**
     * <p>
     * Tests the <code>getOtherFileFormats()</code> method.
     * </p>
     * <p>
     * By default, The otherFileFormats is null.
     * </p>
     */
    public void testGetOtherFileFormats_default() {
        assertNull("The otherFileFormats should be null.", saveDraftContestAction.getOtherFileFormats());
    }

    /**
     * <p>
     * Tests the <code>setOtherFileFormats(String)</code> method.
     * </p>
     * <p>
     * The otherFileFormats should be set internally.
     * </p>
     */
    public void testSetOtherFileFormats_accuracy() {
        saveDraftContestAction.setOtherFileFormats("TopCoder");

        assertEquals("field not set.", "TopCoder", saveDraftContestAction.getOtherFileFormats());
    }

    /**
     * <p>
     * Tests the <code>isLaunchImmediately()</code> method.
     * </p>
     * <p>
     * By default, The launchImmediately is false.
     * </p>
     */
    public void testIsLaunchImmediately_default() {
        assertFalse("The launchImmediately should be false.", saveDraftContestAction.isLaunchImmediately());
    }

    /**
     * <p>
     * Tests the <code>setLaunchImmediately(boolean)</code> method.
     * </p>
     * <p>
     * The launchImmediately should be set internally.
     * </p>
     */
    public void testSetLaunchImmediately_accuracy() {
        saveDraftContestAction.setLaunchImmediately(true);

        assertTrue("field not set.", saveDraftContestAction.isLaunchImmediately());
    }

    /**
     * <p>
     * Tests the <code>isMultiRound()</code> method.
     * </p>
     * <p>
     * By default, The multiRound is false.
     * </p>
     */
    public void testIsMultiRound_default() {
        assertFalse("The multiRound should be false.", saveDraftContestAction.isMultiRound());
    }

    /**
     * <p>
     * Tests the <code>setMultiRound(boolean)</code> method.
     * </p>
     * <p>
     * The multiRound should be set internally.
     * </p>
     */
    public void testSetMultiRound_accuracy() {
        saveDraftContestAction.setMultiRound(true);

        assertTrue("field not set.", saveDraftContestAction.isMultiRound());
    }

    /**
     * <p>
     * Tests the <code>getSpecifications()</code> method.
     * </p>
     * <p>
     * By default, The specifications should be set in prepare() method.
     * </p>
     */
    public void testGetSpecifications_default() {
        assertNotNull("The specifications should be set.", saveDraftContestAction.getSpecifications());
    }

    /**
     * <p>
     * Tests the <code>setSpecifications(ContestSpecificationsData)</code> method.
     * </p>
     * <p>
     * The specifications should be set internally.
     * </p>
     */
    public void testSetSpecifications_accuracy() {
        ContestSpecificationsData specifications = new ContestSpecificationsData();
        saveDraftContestAction.setSpecifications(specifications);

        assertSame("field not set.", specifications, saveDraftContestAction.getSpecifications());
    }

    /**
     * <p>
     * Tests the <code>getMultiRoundData()</code> method.
     * </p>
     * <p>
     * By default, The multiRoundData should be set in prepare() method.
     * </p>
     */
    public void testGetMultiRoundData_default() {
        assertNotNull("The multiRoundData should be set.", saveDraftContestAction.getMultiRoundData());
    }

    /**
     * <p>
     * Tests the <code>setMultiRoundData(String)</code> method.
     * </p>
     * <p>
     * The multiRoundData should be set internally.
     * </p>
     */
    public void testSetMultiRoundData_accuracy() {
        ContestMultiRoundInformationData multiRoundData = new ContestMultiRoundInformationData();
        saveDraftContestAction.setMultiRoundData(multiRoundData);

        assertSame("field not set.", multiRoundData, saveDraftContestAction.getMultiRoundData());
    }

    /**
     * <p>
     * Tests the <code>getMilestonePrizeData()</code> method.
     * </p>
     * <p>
     * By default, The milestonePrizeData should be set in prepare() method.
     * </p>
     */
    public void testGetMilestonePrizeData_default() {
        assertNotNull("The milestonePrizeData should be set.", saveDraftContestAction.getMilestonePrizeData());
    }

    /**
     * <p>
     * Tests the <code>setMilestonePrizeData(String)</code> method.
     * </p>
     * <p>
     * The milestonePrizeData should be set internally.
     * </p>
     */
    public void testSetMilestonePrizeData_accuracy() {
        MilestonePrizeData milestonePrizeData = new MilestonePrizeData();
        saveDraftContestAction.setMilestonePrizeData(milestonePrizeData);

        assertSame("field not set.", milestonePrizeData, saveDraftContestAction.getMilestonePrizeData());
    }

    /**
     * <p>
     * Tests the <code>prepare()</code> method.
     * </p>
     * <p>
     * If contestServiceFacade is null, should throw IllegalStateException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testPrepare_ISE() throws Exception {
        saveDraftContestAction = new SaveDraftContestAction();
        saveDraftContestAction.setProjectId(1l);

        try {
            saveDraftContestAction.prepare();
            fail("If contestServiceFacade is null, should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>prepare()</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testPrepare_accuracy1() throws Exception {
        saveDraftContestAction = new SaveDraftContestAction();
        saveDraftContestAction.setContestServiceFacade(new MockContestServiceFacade());
        saveDraftContestAction.setProjectId(1l);

        saveDraftContestAction.prepare();
    }

    /**
     * <p>
     * Tests the <code>prepare()</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testPrepare_accuracy2() throws Exception {
        saveDraftContestAction = new SaveDraftContestAction();
        saveDraftContestAction.setContestServiceFacade(new MockContestServiceFacade());
        saveDraftContestAction.setContestId(1l);

        saveDraftContestAction.prepare();
    }

    /**
     * <p>
     * Tests the <code>executeAction()</code> method.
     * </p>
     * <p>
     * If the contestServiceFacade is not set, should throw IllegalStateException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testExecuteAction_contestServiceFacade_notSet() throws Exception {
        saveDraftContestAction = new SaveDraftContestAction();

        try {
            saveDraftContestAction.executeAction();

            fail("If the contestServiceFacade is not set, should throw IllegalStateException.");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>executeAction()</code> method.
     * </p>
     * <p>
     * The updated software competition should be set as result.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testExecuteAction_updateSoftwareCompetition() throws Exception {
        saveDraftContestAction.setProjectId(1l);
        saveDraftContestAction.setStartDate(new Date());
        saveDraftContestAction.setEndDate(new Date());

        saveDraftContestAction.executeAction();

        assertNotNull("result should be set", saveDraftContestAction.getResult());
    }

    /**
     * <p>
     * Tests the <code>executeAction()</code> method.
     * </p>
     * <p>
     * The created software competition should be set as result.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testExecuteAction_createSoftwareCompetition() throws Exception {
        saveDraftContestAction.setStartDate(new Date());
        saveDraftContestAction.setEndDate(new Date());
        saveDraftContestAction.setCompetitionType(CompetitionType.SOFTWARE);

        saveDraftContestAction.executeAction();

        assertNotNull("result should be set", saveDraftContestAction.getResult());
    }

    /**
     * <p>
     * Tests the <code>executeAction()</code> method.
     * </p>
     * <p>
     * The updated studio competition should be set as result.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testExecuteAction_updateStudioCompetition() throws Exception {
        saveDraftContestAction.setContestId(1l);
        saveDraftContestAction.setStartDate(new Date());
        saveDraftContestAction.setEndDate(new Date());

        saveDraftContestAction.executeAction();

        assertNotNull("result should be set", saveDraftContestAction.getResult());
    }

    /**
     * <p>
     * Tests the <code>executeAction()</code> method.
     * </p>
     * <p>
     * The created studio competition should be set as result.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testExecuteAction_createStudioCompetition() throws Exception {
        saveDraftContestAction.setStartDate(new Date());
        saveDraftContestAction.setEndDate(new Date());
        saveDraftContestAction.setCompetitionType(CompetitionType.STUDIO);

        saveDraftContestAction.executeAction();

        assertNotNull("result should be set", saveDraftContestAction.getResult());
    }

    /**
     * <p>
     * Tests the <code>executeAction()</code> method.
     * </p>
     * <p>
     * If the competitionType is unknown, should set field error.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testExecuteAction_unkownToCreate() throws Exception {
        saveDraftContestAction.setStartDate(new Date());
        saveDraftContestAction.setEndDate(new Date());

        saveDraftContestAction.executeAction();

        assertFalse("field error should be set", saveDraftContestAction.getFieldErrors().isEmpty());
    }

}
