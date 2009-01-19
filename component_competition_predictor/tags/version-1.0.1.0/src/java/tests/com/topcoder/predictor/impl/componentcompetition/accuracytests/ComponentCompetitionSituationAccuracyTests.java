/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.accuracytests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;
import com.topcoder.predictor.impl.componentcompetition.Participant;
import com.topcoder.predictor.impl.componentcompetition.Technology;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for <code>{@link ComponentCompetitionSituation}</code> class.
 * </p>
 *
 * @author morehappiness
 * @version 1.0
 */
public class ComponentCompetitionSituationAccuracyTests extends TestCase {
    /**
     * <p>
     * Represents the <code>ComponentCompetitionSituation</code> used in tests.
     * </p>
     */
    private ComponentCompetitionSituation instance = null;

    /**
     * <p>
     * Sets up the tests.
     * </p>
     */
    public void setUp() {
        instance = new ComponentCompetitionSituation();
    }

    /**
     * <p>
     * Tests accuracy of <code>ComponentCompetitionSituation()</code> constructor.<br>
     * Instance should be correctly created.
     * </p>
     */
    public void testCtor() {
        assertNull("ComponentId should be correct.", instance.getComponentId());
        assertNull("ComponentVersionId should be correct.", instance.getComponentVersionId());
        assertNull("ProjectId should be correct.", instance.getProjectId());
        assertNull("Catalog should be correct.", instance.getCatalog());
        assertNull("Component should be correct.", instance.getComponent());
        assertNull("Version should be correct.", instance.getVersion());
        assertNull("ProjectCategory should be correct.", instance.getProjectCategory());
        assertNull("ProjectStatus should be correct.", instance.getProjectStatus());
        assertNull("PostingDate should be correct.", instance.getPostingDate());
        assertNull("EndDate should be correct.", instance.getEndDate());
        assertNull("ScorecardId should be correct.", instance.getScorecardId());
        assertNull("FinalFixCount should be correct.", instance.getFinalFixCount());
        assertNull("Prize should be correct.", instance.getPrize());
        assertNull("Rated should be correct.", instance.getRated());
        assertNull("DigitalRun should be correct.", instance.getDigitalRun());
        assertNull("DigitalRunPoints should be correct.", instance.getDigitalRunPoints());
        assertNull("Description should be correct.", instance.getDescription());
        assertEquals("Keywords should be correct.", 0, instance.getKeywords().size());
        assertEquals("Technologies should be correct.", 0, instance.getTechnologies().size());
        assertEquals("Participants should be correct.", 0, instance.getParticipants().size());
    }

    /**
     * <p>
     * Tests accuracy of <code>getComponentId()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetComponentId() {
        instance.setComponentId(1);

        assertEquals("'getComponentId' should be correct.", 1, instance.getComponentId().intValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setComponentId(Integer)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetComponentId1() {
        instance.setComponentId(1);

        assertEquals("'setComponentId' should be correct.", 1, instance.getComponentId().intValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setComponentId(Integer)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetComponentId2() {
        instance.setComponentId(null);

        assertNull("'setComponentId' should be correct.", instance.getComponentId());
    }

    /**
     * <p>
     * Tests accuracy of <code>getComponentVersionId()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetComponentVersionId() {
        instance.setComponentVersionId(1);

        assertEquals("'getComponentVersionId' should be correct.", 1, instance.getComponentVersionId().intValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setComponentVersionId(Integer)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetComponentVersionId1() {
        instance.setComponentVersionId(1);

        assertEquals("'setComponentVersionId' should be correct.", 1, instance.getComponentVersionId().intValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setComponentVersionId(Integer)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetComponentVersionId2() {
        instance.setComponentVersionId(null);

        assertNull("'setComponentVersionId' should be correct.", instance.getComponentVersionId());
    }

    /**
     * <p>
     * Tests accuracy of <code>getProjectId()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetProjectId() {
        instance.setProjectId(1);

        assertEquals("'getProjectId' should be correct.", 1, instance.getProjectId().intValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setProjectId(Integer)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetProjectId1() {
        instance.setProjectId(1);

        assertEquals("'setProjectId' should be correct.", 1, instance.getProjectId().intValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setProjectId(Integer)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetProjectId2() {
        instance.setProjectId(null);

        assertNull("'setProjectId' should be correct.", instance.getProjectId());
    }

    /**
     * <p>
     * Tests accuracy of <code>getCatalog()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetCatalog() {
        instance.setCatalog("new_value");

        assertEquals("'getCatalog' should be correct.", "new_value", instance.getCatalog());
    }

    /**
     * <p>
     * Tests accuracy of <code>setCatalog(String)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetCatalog1() {
        instance.setCatalog("new_value");

        assertEquals("'setCatalog' should be correct.", "new_value", instance.getCatalog());
    }

    /**
     * <p>
     * Tests accuracy of <code>setCatalog(String)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetCatalog2() {
        instance.setCatalog(null);

        assertNull("'setCatalog' should be correct.", instance.getCatalog());
    }

    /**
     * <p>
     * Tests accuracy of <code>getComponent()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetComponent() {
        instance.setComponent("new_value");

        assertEquals("'getComponent' should be correct.", "new_value", instance.getComponent());
    }

    /**
     * <p>
     * Tests accuracy of <code>setComponent(String)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetComponent1() {
        instance.setComponent("new_value");

        assertEquals("'setComponent' should be correct.", "new_value", instance.getComponent());
    }

    /**
     * <p>
     * Tests accuracy of <code>setComponent(String)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetComponent2() {
        instance.setComponent(null);

        assertNull("'setComponent' should be correct.", instance.getComponent());
    }

    /**
     * <p>
     * Tests accuracy of <code>getVersion()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetVersion() {
        instance.setVersion("new_value");

        assertEquals("'getVersion' should be correct.", "new_value", instance.getVersion());
    }

    /**
     * <p>
     * Tests accuracy of <code>setVersion(String)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetVersion1() {
        instance.setVersion("new_value");

        assertEquals("'setVersion' should be correct.", "new_value", instance.getVersion());
    }

    /**
     * <p>
     * Tests accuracy of <code>setVersion(String)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetVersion2() {
        instance.setVersion(null);

        assertNull("'setVersion' should be correct.", instance.getVersion());
    }

    /**
     * <p>
     * Tests accuracy of <code>getProjectCategory()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetProjectCategory() {
        instance.setProjectCategory("new_value");

        assertEquals("'getProjectCategory' should be correct.", "new_value", instance.getProjectCategory());
    }

    /**
     * <p>
     * Tests accuracy of <code>setProjectCategory(String)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetProjectCategory1() {
        instance.setProjectCategory("new_value");

        assertEquals("'setProjectCategory' should be correct.", "new_value", instance.getProjectCategory());
    }

    /**
     * <p>
     * Tests accuracy of <code>setProjectCategory(String)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetProjectCategory2() {
        instance.setProjectCategory(null);

        assertNull("'setProjectCategory' should be correct.", instance.getProjectCategory());
    }

    /**
     * <p>
     * Tests accuracy of <code>getProjectStatus()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetProjectStatus() {
        instance.setProjectStatus("new_value");

        assertEquals("'getProjectStatus' should be correct.", "new_value", instance.getProjectStatus());
    }

    /**
     * <p>
     * Tests accuracy of <code>setProjectStatus(String)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetProjectStatus1() {
        instance.setProjectStatus("new_value");

        assertEquals("'setProjectStatus' should be correct.", "new_value", instance.getProjectStatus());
    }

    /**
     * <p>
     * Tests accuracy of <code>setProjectStatus(String)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetProjectStatus2() {
        instance.setProjectStatus(null);

        assertNull("'setProjectStatus' should be correct.", instance.getProjectStatus());
    }

    /**
     * <p>
     * Tests accuracy of <code>getPostingDate()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @SuppressWarnings("deprecation")
    public void testGetPostingDate() {
        assertNull("'getPostingDate' should be correct.", instance.getPostingDate());
    }

    /**
     * <p>
     * Tests accuracy of <code>setPostingDate(Date)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @SuppressWarnings({"deprecation", "deprecation"})
    public void testSetPostingDate1() {
        instance.setEndDate(new Date(2008, 10, 2));

        Date date = new Date(2008, 10, 1);
        instance.setPostingDate(date);

        assertEquals("'setPostingDate' should be correct.", date, instance.getPostingDate());
    }

    /**
     * <p>
     * Tests accuracy of <code>setPostingDate(Date)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetPostingDate2() {
        instance.setPostingDate(null);

        assertNull("'setPostingDate' should be correct.", instance.getPostingDate());
    }

    /**
     * <p>
     * Tests accuracy of <code>getEndDate()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @SuppressWarnings("deprecation")
    public void testGetEndDate() {
        Date date = new Date(2008, 10, 1);
        instance.setEndDate(date);

        assertEquals("'getEndDate' should be correct.", date, instance.getEndDate());
    }

    /**
     * <p>
     * Tests accuracy of <code>setEndDate(Date)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @SuppressWarnings("deprecation")
    public void testSetEndDate1() {
        Date date = new Date(2008, 10, 2);
        instance.setEndDate(date);

        assertEquals("'setEndDate' should be correct.", date, instance.getEndDate());
    }

    /**
     * <p>
     * Tests accuracy of <code>setEndDate(Date)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @SuppressWarnings("deprecation")
    public void testSetEndDate2() {
        instance.setPostingDate(new Date(2008, 10, 1));

        Date date = new Date(2008, 10, 2);
        instance.setEndDate(date);

        assertEquals("'setEndDate' should be correct.", date, instance.getEndDate());
    }

    /**
     * <p>
     * Tests accuracy of <code>setEndDate(Date)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetEndDate3() {
        instance.setEndDate(null);

        assertNull("'setEndDate' should be correct.", instance.getEndDate());
    }

    /**
     * <p>
     * Tests accuracy of <code>getScorecardId()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetScorecardId() {
        instance.setScorecardId(1);

        assertEquals("'getScorecardId' should be correct.", 1, instance.getScorecardId().intValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setScorecardId(Integer)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetScorecardId1() {
        instance.setScorecardId(1);

        assertEquals("'setScorecardId' should be correct.", 1, instance.getScorecardId().intValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setScorecardId(Integer)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetScorecardId2() {
        instance.setScorecardId(null);

        assertNull("'setScorecardId' should be correct.", instance.getScorecardId());
    }

    /**
     * <p>
     * Tests accuracy of <code>getFinalFixCount()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetFinalFixCount() {
        instance.setFinalFixCount(1);

        assertEquals("'getFinalFixCount' should be correct.", 1, instance.getFinalFixCount().intValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setFinalFixCount(Integer)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetFinalFixCount1() {
        instance.setFinalFixCount(0);

        assertEquals("'setFinalFixCount' should be correct.", 0, instance.getFinalFixCount().intValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setFinalFixCount(Integer)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetFinalFixCount2() {
        instance.setFinalFixCount(null);

        assertNull("'setFinalFixCount' should be correct.", instance.getFinalFixCount());
    }

    /**
     * <p>
     * Tests accuracy of <code>getPrize()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetPrize() {
        instance.setPrize(1.0);

        assertEquals("'getPrize' should be correct.", 1.0, instance.getPrize().doubleValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setPrize(Double)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetPrize1() {
        instance.setPrize(0.0);

        assertEquals("'setPrize' should be correct.", 0.0, instance.getPrize().doubleValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setPrize(Double)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetPrize2() {
        instance.setPrize(null);

        assertNull("'setPrize' should be correct.", instance.getPrize());
    }

    /**
     * <p>
     * Tests accuracy of <code>getRated()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetRated() {
        instance.setRated(true);

        assertTrue("'getRated' should be correct.", instance.getRated());
    }

    /**
     * <p>
     * Tests accuracy of <code>setRated(Boolean)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetRated1() {
        instance.setRated(true);

        assertTrue("'setRated' should be correct.", instance.getRated());
    }

    /**
     * <p>
     * Tests accuracy of <code>setRated(Boolean)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetRated2() {
        instance.setRated(null);

        assertNull("'setRated' should be correct.", instance.getRated());
    }

    /**
     * <p>
     * Tests accuracy of <code>getDigitalRun()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetDigitalRun() {
        instance.setDigitalRun(true);

        assertTrue("'getDigitalRun' should be correct.", instance.getDigitalRun());
    }

    /**
     * <p>
     * Tests accuracy of <code>setDigitalRun(Boolean)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetDigitalRun1() {
        instance.setDigitalRun(true);

        assertTrue("'setDigitalRun' should be correct.", instance.getDigitalRun());
    }

    /**
     * <p>
     * Tests accuracy of <code>setDigitalRun(Boolean)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetDigitalRun2() {
        instance.setDigitalRun(null);

        assertNull("'setDigitalRun' should be correct.", instance.getDigitalRun());
    }

    /**
     * <p>
     * Tests accuracy of <code>getDigitalRunPoints()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetDigitalRunPoints() {
        instance.setDigitalRunPoints(1);

        assertEquals("'getDigitalRunPoints' should be correct.", 1, instance.getDigitalRunPoints().intValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setDigitalRunPoints(Integer)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetDigitalRunPoints1() {
        instance.setDigitalRunPoints(0);

        assertEquals("'setDigitalRunPoints' should be correct.", 0, instance.getDigitalRunPoints().intValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setDigitalRunPoints(Integer)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetDigitalRunPoints2() {
        instance.setDigitalRunPoints(null);

        assertNull("'setDigitalRunPoints' should be correct.", instance.getDigitalRunPoints());
    }

    /**
     * <p>
     * Tests accuracy of <code>getDescription()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetDescription() {
        instance.setDescription("new_value");

        assertEquals("'getDescription' should be correct.", "new_value", instance.getDescription());
    }

    /**
     * <p>
     * Tests accuracy of <code>setDescription(String)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetDescription1() {
        instance.setDescription("new_value");

        assertEquals("'setDescription' should be correct.", "new_value", instance.getDescription());
    }

    /**
     * <p>
     * Tests accuracy of <code>setDescription(String)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetDescription2() {
        instance.setDescription(null);

        assertNull("'setDescription' should be correct.", instance.getDescription());
    }

    /**
     * <p>
     * Tests accuracy of <code>getKeywords()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetKeywords() {
        List<String> keywords = new ArrayList<String>();
        keywords.add("kw1");
        keywords.add("kw2");

        instance.setKeywords(keywords);

        List<String> res = instance.getKeywords();

        assertTrue("'getKeywords' should be correct.", Arrays.equals(keywords.toArray(), res.toArray()));
    }

    /**
     * <p>
     * Tests accuracy of <code>setKeywords(List&lt;String&gt;)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetKeywords1() {
        List<String> keywords = new ArrayList<String>();
        keywords.add("kw1");
        keywords.add("kw2");

        instance.setKeywords(keywords);

        List<String> res = instance.getKeywords();

        assertTrue("'setKeywords' should be correct.", Arrays.equals(keywords.toArray(), res.toArray()));
    }

    /**
     * <p>
     * Tests accuracy of <code>setKeywords(List&lt;String&gt;)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetKeywords2() {
        instance.setKeywords(null);

        assertEquals("'setKeywords' should be correct.", 0, instance.getKeywords().size());
    }

    /**
     * <p>
     * Tests accuracy of <code>getTechnologies()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetTechnologies() {
        List<Technology> technologies = new ArrayList<Technology>();
        Technology technology = new Technology();
        technology.setName("technology1");
        technologies.add(technology);
        technology = new Technology();
        technology.setName("technology2");
        technologies.add(technology);

        instance.setTechnologies(technologies);

        List<Technology> res = instance.getTechnologies();

        assertTrue("'getTechnologies' should be correct.", Arrays.equals(technologies.toArray(), res.toArray()));
    }

    /**
     * <p>
     * Tests accuracy of <code>setTechnologies(List&lt;Technology&gt;)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetTechnologies1() {
        List<Technology> technologies = new ArrayList<Technology>();
        Technology technology = new Technology();
        technology.setName("technology1");
        technologies.add(technology);
        technology = new Technology();
        technology.setName("technology2");
        technologies.add(technology);

        instance.setTechnologies(technologies);

        List<Technology> res = instance.getTechnologies();

        assertTrue("'setTechnologies' should be correct.", Arrays.equals(technologies.toArray(), res.toArray()));
    }

    /**
     * <p>
     * Tests accuracy of <code>setTechnologies(List&lt;Technology&gt;)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetTechnologies2() {
        instance.setTechnologies(null);

        assertEquals("'setTechnologies' should be correct.", 0, instance.getTechnologies().size());
    }

    /**
     * <p>
     * Tests accuracy of <code>getParticipants()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetParticipants() {
        List<Participant> participants = new ArrayList<Participant>();
        Participant participant = new Participant();
        participant.setUserId(1);
        participants.add(participant);
        participant = new Participant();
        participant.setUserId(2);
        participants.add(participant);

        instance.setParticipants(participants);

        List<Participant> res = instance.getParticipants();

        assertTrue("'getParticipants' should be correct.", Arrays.equals(participants.toArray(), res.toArray()));
    }

    /**
     * <p>
     * Tests accuracy of <code>setParticipants(List&lt;Participant&gt;)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetParticipants1() {
        List<Participant> participants = new ArrayList<Participant>();
        Participant participant = new Participant();
        participant.setUserId(1);
        participants.add(participant);
        participant = new Participant();
        participant.setUserId(2);
        participants.add(participant);

        instance.setParticipants(participants);

        List<Participant> res = instance.getParticipants();

        assertTrue("'setParticipants' should be correct.", Arrays.equals(participants.toArray(), res.toArray()));
    }

    /**
     * <p>
     * Tests accuracy of <code>setParticipants(List&lt;Participant&gt;)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetParticipants2() {
        instance.setParticipants(null);

        assertEquals("'setParticipants' should be correct.", 0, instance.getParticipants().size());
    }

    /**
     * <p>
     * Tests accuracy of <code>clone()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @SuppressWarnings("deprecation")
    public void testClone() {
        instance.setComponentId(1);
        instance.setComponentVersionId(2);
        instance.setProjectId(3);
        instance.setCatalog("catalog");
        instance.setComponent("component");
        instance.setVersion("version");
        instance.setProjectCategory("projectCategory");
        instance.setProjectStatus("projectStatus");
        Date postingDate = new Date(2008, 10, 1);
        instance.setPostingDate(postingDate);
        Date endDate = new Date(2008, 10, 2);
        instance.setEndDate(endDate);
        instance.setScorecardId(4);
        instance.setFinalFixCount(5);
        instance.setPrize(0.1);
        instance.setRated(true);
        instance.setDigitalRun(true);
        instance.setDigitalRunPoints(6);
        instance.setDescription("description");

        List<String> keywords = new ArrayList<String>();
        keywords.add("kw");
        List<Technology> technologies = new ArrayList<Technology>();
        Technology technology = new Technology();
        technology.setName("technology");
        technologies.add(technology);
        List<Participant> participants = new ArrayList<Participant>();
        Participant participant = new Participant();
        participant.setUserId(1);
        participants.add(participant);

        instance.setKeywords(keywords);
        instance.setTechnologies(technologies);
        instance.setParticipants(participants);

        ComponentCompetitionSituation cloneObj = (ComponentCompetitionSituation) instance.clone();

        assertEquals("'clone' should be correct.", 1, cloneObj.getComponentId().intValue());
        assertEquals("'clone' should be correct.", 2, cloneObj.getComponentVersionId().intValue());
        assertEquals("'clone' should be correct.", 3, cloneObj.getProjectId().intValue());
        assertEquals("'clone' should be correct.", "catalog", cloneObj.getCatalog());
        assertEquals("'clone' should be correct.", "component", cloneObj.getComponent());
        assertEquals("'clone' should be correct.", "version", cloneObj.getVersion());
        assertEquals("'clone' should be correct.", "projectCategory", cloneObj.getProjectCategory());
        assertEquals("'clone' should be correct.", "projectStatus", cloneObj.getProjectStatus());
        assertEquals("'clone' should be correct.", postingDate, cloneObj.getPostingDate());
        assertEquals("'clone' should be correct.", endDate, cloneObj.getEndDate());
        assertEquals("'clone' should be correct.", 4, cloneObj.getScorecardId().intValue());
        assertEquals("'clone' should be correct.", 5, cloneObj.getFinalFixCount().intValue());
        assertEquals("'clone' should be correct.", 0.1, cloneObj.getPrize().doubleValue());
        assertTrue("'clone' should be correct.", cloneObj.getRated());
        assertTrue("'clone' should be correct.", cloneObj.getDigitalRun());
        assertEquals("'clone' should be correct.", 6, cloneObj.getDigitalRunPoints().intValue());
        assertEquals("'clone' should be correct.", "description", cloneObj.getDescription());
        assertEquals("'clone' should be correct.", 1, cloneObj.getKeywords().size());
        assertEquals("'clone' should be correct.", "kw", cloneObj.getKeywords().get(0));
        // Clone
        assertTrue("'clone' should be correct.", technology != cloneObj.getTechnologies().get(0));
        // Clone
        assertTrue("'clone' should be correct.", participant != cloneObj.getParticipants().get(0));
    }
}
