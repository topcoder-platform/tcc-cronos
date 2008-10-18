/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition;

import junit.framework.TestCase;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * <p>
 * Unit tests for ComponentCompetitionSituation class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ComponentCompetitionSituationUnitTests extends TestCase {

    /**
     * An instance of ComponentCompetitionSituation for the following tests.
     */
    private ComponentCompetitionSituation tester = null;

    /**
     * Initialize the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        tester = new ComponentCompetitionSituation();
    }

    /**
     * Clear the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        tester = null;
    }

    /**
     * <p>
     * Accuracy test for the constructor. No exception is thrown.
     * </p>
     */
    public void test_ctor() {
        // no exception
        new ComponentCompetitionSituation();
    }

    /**
     * <p>
     * Accuracy test for the componentId property. It should be got/set properly.
     * </p>
     */
    public void test_componentId() {
        assertNull("Initial value should be null.", tester.getComponentId());
        tester.setComponentId(123);
        assertEquals("The componentId is not got/set properly.", 123, tester.getComponentId().intValue());
    }

    /**
     * <p>
     * Accuracy test for the componentVersionId property. It should be got/set properly.
     * </p>
     */
    public void test_componentVersionId() {
        assertNull("Initial value should be null.", tester.getComponentVersionId());
        tester.setComponentVersionId(111);
        assertEquals("The xxx is not got/set properly.", 111, tester.getComponentVersionId().intValue());
    }

    /**
     * <p>
     * Accuracy test for the projectId property. It should be got/set properly.
     * </p>
     */
    public void test_projectId() {
        assertNull("Initial value should be null.", tester.getProjectId());
        tester.setProjectId(123);
        assertEquals("The projectId is not got/set properly.", 123, tester.getProjectId().intValue());
    }

    /**
     * <p>
     * Accuracy test for the catalog property. It should be got/set properly.
     * </p>
     */
    public void test_catalog() {
        assertNull("Initial value should be null.", tester.getCatalog());
        tester.setCatalog("cat");
        assertEquals("The catalog is not got/set properly.", "cat", tester.getCatalog());
    }

    /**
     * <p>
     * Accuracy test for the component property. It should be got/set properly.
     * </p>
     */
    public void test_component() {
        assertNull("Initial value should be null.", tester.getComponent());
        tester.setComponent("comp");
        assertEquals("The component is not got/set properly.", "comp", tester.getComponent());
    }

    /**
     * <p>
     * Accuracy test for the version property. It should be got/set properly.
     * </p>
     */
    public void test_version() {
        assertNull("Initial value should be null.", tester.getVersion());
        tester.setVersion("ver");
        assertEquals("The version is not got/set properly.", "ver", tester.getVersion());
    }

    /**
     * <p>
     * Accuracy test for the projectCategory property. It should be got/set properly.
     * </p>
     */
    public void test_projectCategory() {
        assertNull("Initial value should be null.", tester.getProjectCategory());
        tester.setProjectCategory("cat");
        assertEquals("The projectCategory is not got/set properly.", "cat", tester.getProjectCategory());
    }

    /**
     * <p>
     * Accuracy test for the projectStatus property. It should be got/set properly.
     * </p>
     */
    public void test_projectStatus() {
        assertNull("Initial value should be null.", tester.getProjectStatus());
        tester.setProjectStatus("status");
        assertEquals("The projectStatus is not got/set properly.", "status", tester.getProjectStatus());
    }

    /**
     * <p>
     * Accuracy test for the postingDate property. It should be got/set properly.
     * </p>
     */
    public void test_postingDate() {
        assertNull("Initial value should be null.", tester.getPostingDate());
        Date d = new Date();
        tester.setPostingDate(d);
        assertEquals("The postingDate is not got/set properly.", d, tester.getPostingDate());
    }

    /**
     * <p>
     * Accuracy test for the endDate property. It should be got/set properly.
     * </p>
     */
    public void test_endDate() {
        assertNull("Initial value should be null.", tester.getEndDate());
        Date d = new Date();
        tester.setEndDate(d);
        assertEquals("The endDate is not got/set properly.", d, tester.getEndDate());
    }

    /**
     * <p>
     * Accuracy test for the scorecardId property. It should be got/set properly.
     * </p>
     */
    public void test_scorecardId() {
        assertNull("Initial value should be null.", tester.getScorecardId());
        tester.setScorecardId(123);
        assertEquals("The scorecardId is not got/set properly.", 123, tester.getScorecardId().intValue());
    }

    /**
     * <p>
     * Accuracy test for the finalFixCount property. It should be got/set properly.
     * </p>
     */
    public void test_finalFixCount() {
        assertNull("Initial value should be null.", tester.getFinalFixCount());
        tester.setFinalFixCount(4);
        assertEquals("The finalFixCount is not got/set properly.", 4, tester.getFinalFixCount().intValue());
    }

    /**
     * <p>
     * Accuracy test for the prize property. It should be got/set properly.
     * </p>
     */
    public void test_prize() {
        assertNull("Initial value should be null.", tester.getPrize());
        tester.setPrize(123.4);
        assertEquals("The prize is not got/set properly.", 123.4, tester.getPrize().doubleValue());
    }

    /**
     * <p>
     * Accuracy test for the rated property. It should be got/set properly.
     * </p>
     */
    public void test_rated() {
        assertNull("Initial value should be null.", tester.getRated());
        tester.setRated(true);
        assertEquals("The rated is not got/set properly.", true, tester.getRated().booleanValue());
    }

    /**
     * <p>
     * Accuracy test for the digitalRun property. It should be got/set properly.
     * </p>
     */
    public void test_digitalRun() {
        assertNull("Initial value should be null.", tester.getDigitalRun());
        tester.setDigitalRun(false);
        assertEquals("The digitalRun is not got/set properly.", false, tester.getDigitalRun().booleanValue());
    }

    /**
     * <p>
     * Accuracy test for the digitalRunPoints property. It should be got/set properly.
     * </p>
     */
    public void test_digitalRunPoints() {
        assertNull("Initial value should be null.", tester.getDigitalRunPoints());
        tester.setDigitalRunPoints(500);
        assertEquals("The digitalRunPoints is not got/set properly.", 500, tester.getDigitalRunPoints().intValue());
    }

    /**
     * <p>
     * Accuracy test for the description property. It should be got/set properly.
     * </p>
     */
    public void test_description() {
        assertNull("Initial value should be null.", tester.getDescription());
        tester.setDescription("desc");
        assertEquals("The description is not got/set properly.", "desc", tester.getDescription());
    }

    /**
     * <p>
     * Accuracy test for the keywords property. It should be got/set properly.
     * </p>
     */
    public void test_keywords() {
        assertEquals("Initial value should be empty.", 0, tester.getKeywords().size());
        List<String> keywords = new ArrayList<String>();
        keywords.add("keyword1");
        keywords.add("keyword2");
        tester.setKeywords(keywords);
        assertEquals("The keywords are not got/set properly.", 2, tester.getKeywords().size());
        assertEquals("The keywords are not got/set properly.", "keyword1", tester.getKeywords().get(0));
        assertEquals("The keywords are not got/set properly.", "keyword2", tester.getKeywords().get(1));
    }

    /**
     * <p>
     * Accuracy test for the technologies property. It should be got/set properly.
     * </p>
     */
    public void test_technologies() {
        assertEquals("Initial value should be empty.", 0, tester.getTechnologies().size());
        List<Technology> technologies = new ArrayList<Technology>();
        technologies.add(new Technology());
        technologies.add(new Technology());
        tester.setTechnologies(technologies);
        assertEquals("The technologies are not got/set properly.", 2, tester.getTechnologies().size());
    }

    /**
     * <p>
     * Accuracy test for the participants property. It should be got/set properly.
     * </p>
     */
    public void test_participants() {
        assertEquals("Initial value should be empty.", 0, tester.getParticipants().size());
        List<Participant> participants = new ArrayList<Participant>();
        participants.add(new Participant());
        tester.setParticipants(participants);
        assertEquals("The keywords are not got/set properly.", 1, tester.getParticipants().size());
    }

    /**
     * <p>
     * Accuracy test for the clone method, the cloned object should be same as the original object.
     * </p>
     */
    public void test_clone() {
        // set properties
        tester.setCatalog("cat");
        tester.setComponent("comp");
        tester.setComponentId(111);
        tester.setComponentVersionId(222);
        tester.setDescription("desc");
        tester.setDigitalRun(true);
        tester.setDigitalRunPoints(500);
        Date d1 = new Date();
        tester.setEndDate(d1);
        tester.setFinalFixCount(2);
        List<String> keywords = new ArrayList<String>();
        keywords.add("keyword1");
        keywords.add("keyword2");
        tester.setKeywords(keywords);
        List<Participant> participants = new ArrayList<Participant>();
        participants.add(new Participant());
        tester.setParticipants(participants);
        Date d2 = new Date(d1.getTime() - 10000);
        tester.setPostingDate(d2);
        tester.setPrize(300.0);
        tester.setProjectCategory("project cat");
        tester.setProjectId(333);
        tester.setProjectStatus("status");
        tester.setRated(true);
        tester.setScorecardId(444);
        List<Technology> technologies = new ArrayList<Technology>();
        technologies.add(new Technology());
        technologies.add(new Technology());
        technologies.add(new Technology());
        tester.setTechnologies(technologies);
        tester.setVersion("version");

        // verify cloned object
        Object obj = tester.clone();
        assertTrue("Cloned object should be type of ComponentCompetitionSituation.",
                        obj instanceof ComponentCompetitionSituation);
        ComponentCompetitionSituation s = (ComponentCompetitionSituation) obj;
        assertEquals("The catalog is incorrect.", "cat", s.getCatalog());
        assertEquals("The component is incorrect.", "comp", s.getComponent());
        assertEquals("The component id is incorrect.", 111, s.getComponentId().intValue());
        assertEquals("The component version is incorrect.", 222, s.getComponentVersionId().intValue());
        assertEquals("The description is incorrect.", "desc", s.getDescription());
        assertEquals("The digital run is incorrect.", true, s.getDigitalRun().booleanValue());
        assertEquals("The digital run points is incorrect.", 500, s.getDigitalRunPoints().intValue());
        assertEquals("The end date is incorrect.", d1, s.getEndDate());
        assertEquals("The final fix count is incorrect.", 2, s.getFinalFixCount().intValue());
        assertEquals("The keywords is incorrect.", 2, s.getKeywords().size());
        assertEquals("The participants is incorrect.", 1, s.getParticipants().size());
        assertEquals("The posting date is incorrect.", d2, s.getPostingDate());
        assertEquals("The prize is incorrect.", 300.0, s.getPrize().doubleValue());
        assertEquals("The project category is incorrect.", "project cat", s.getProjectCategory());
        assertEquals("The project id is incorrect.", 333, s.getProjectId().intValue());
        assertEquals("The project status is incorrect.", "status", s.getProjectStatus());
        assertEquals("The rated is incorrect.", true, s.getRated().booleanValue());
        assertEquals("The scorecard id is incorrect.", 444, s.getScorecardId().intValue());
        assertEquals("The technologies is incorrect.", 3, s.getTechnologies().size());
        assertEquals("The version is incorrect.", "version", s.getVersion());
    }

    /**
     * <p>
     * Failure test for the postingDate property. IllegalArgumentException should be thrown if posting date is later
     * than end date.
     * </p>
     */
    public void test_postingDate_failure() {
        Date d = new Date();
        tester.setEndDate(d);
        try {
            tester.setPostingDate(new Date(d.getTime() + 10000));
            fail("IllegalArgumentException should be thrown if posting date is later than end date.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the endDate property. IllegalArgumentException should be thrown if posting date is later than
     * end date.
     * </p>
     */
    public void test_endDate_failure() {
        Date d = new Date();
        tester.setPostingDate(d);
        try {
            tester.setEndDate(new Date(d.getTime() - 10000));
            fail("IllegalArgumentException should be thrown if posting date is later than end date.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the finalFixCount property. IllegalArgumentException should be thrown if finalFixCount is
     * negative.
     * </p>
     */
    public void test_finalFixCount_failure() {
        try {
            tester.setFinalFixCount(-1);
            fail("IllegalArgumentException should be thrown if finalFixCount is negative.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the prize property. IllegalArgumentException should be thrown if prize is negative.
     * </p>
     */
    public void test_prize_failure() {
        try {
            tester.setPrize(-1.0);
            fail("IllegalArgumentException should be thrown if prize is negative.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the digitalRunPoints property. IllegalArgumentException should be thrown if digitalRunPoints is
     * negative.
     * </p>
     */
    public void test_digitalRunPoints_failure() {
        try {
            tester.setDigitalRunPoints(-1);
            fail("IllegalArgumentException should be thrown if digitalRunPoints is negative.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the keywords property. IllegalArgumentException should be thrown if keywords contain null
     * element.
     * </p>
     */
    public void test_keywords_failure1() {
        List<String> keywords = new ArrayList<String>();
        keywords.add(null);
        try {
            tester.setKeywords(keywords);
            fail("IllegalArgumentException should be thrown if keywords contain null element.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the keywords property. IllegalArgumentException should be thrown if keywords contain empty
     * string element.
     * </p>
     */
    public void test_keywords_failure2() {
        List<String> keywords = new ArrayList<String>();
        keywords.add("  \r \t \n  ");
        try {
            tester.setKeywords(keywords);
            fail("IllegalArgumentException should be thrown if keywords contain empty string element.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the technologies property. IllegalArgumentException should be thrown for null element.
     * </p>
     */
    public void test_technologies_failure() {
        List<Technology> technologies = new ArrayList<Technology>();
        technologies.add(null);
        try {
            tester.setTechnologies(technologies);
            fail("IllegalArgumentException should be thrown for null element.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the participants property. IllegalArgumentException should be thrown for null element.
     * </p>
     */
    public void test_participants_failure() {
        List<Participant> participants = new ArrayList<Participant>();
        participants.add(null);
        try {
            tester.setParticipants(participants);
            fail("IllegalArgumentException should be thrown for null element.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

}
