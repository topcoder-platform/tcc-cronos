/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit.accuracytests;

import com.topcoder.management.phase.HandlerRegistryInfo;
import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.phase.PhaseOperationEnum;
import com.topcoder.management.phase.clientcockpit.CockpitPhaseManager;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.service.studio.contest.ContestStatus;

import com.topcoder.util.cache.Cache;
import com.topcoder.util.cache.SimpleCache;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * The accuracy test for the class {@link CockpitPhaseManager}.
 *
 * @author LostHunter
 * @version 1.0
 */
public class CockpitPhaseManagerTests extends TestCase {
    /**
     * Represent an instance of CockpitPhaseManager for testing use.
     */
    private CockpitPhaseManager manager;

    /**
     * Represent an instance of ContestManager for testing use.
     */
    private ContestManager contestManager;

    /**
     * Represent an instance of Cache for testing use.
     */
    private Cache cache;

    /**
     * set up the test environment.
     *
     * @throws Exception all exception throw to JUnit.
     */
    protected void setUp() throws Exception {
        // instantiate the variable
        contestManager = new MockContestManager();
        cache = new SimpleCache();
        manager = new CockpitPhaseManager(contestManager, "testLog", cache);

        TestHelper.loadXMLConfig();
    }

    /**
     * tear down the test environment.
     *
     * @throws Exception to JUnit if any error occurs
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
    }

    /**
     * Test the method CockpitPhaseManager() for accuracy.
     *
     * @throws Exception to JUnit if any error occurs
     */
    public void testCtor1() throws Exception {
        manager = new CockpitPhaseManager();
        // check the class definition
        assertTrue("CockpitPhaseManager Should be instance of PhaseManager.", manager instanceof PhaseManager);

        // check the inner member
        assertNull("The member should be null.", manager.getLog());
        assertNull("The member should be null.", manager.getContestManager());
        assertNull("The member should be null.", manager.getCachedContestStatuses());
        assertEquals("The member should be empty.", 0, manager.getHandlers().size());

        // check the type name
        checkDefaultTypeNames();
    }

    /**
     * Test the method CockpitPhaseManager(ContestManager, String, Cache) for accuracy.
     *
     * @throws Exception to JUnit if any error occurs
     */
    public void testCtor3() throws Exception {
        assertNotNull("The manager should not be null.", manager);
        // check the members
        assertSame(contestManager, manager.getContestManager());
        assertSame(cache, manager.getCachedContestStatuses());
        assertNotNull(manager.getLog());
        // check the type name
        checkDefaultTypeNames();

        // test with null parameters
        manager = new CockpitPhaseManager(contestManager, null, null);
        assertSame(contestManager, manager.getContestManager());
        assertNull(manager.getCachedContestStatuses());
        assertNull(manager.getLog());

        // check the type name
        checkDefaultTypeNames();
    }

    /**
     * Test the method CockpitPhaseManager(String) for accuracy.
     *
     * @throws Exception to JUnit if any error occurs
     */
    public void testCtor2() throws Exception {
        manager = new CockpitPhaseManager("CockpitPhaseManager.AccuracyReview.NoJNDI");
        assertNotNull("The manager should not be null.", manager);

        // check the members
        assertNotNull(manager.getContestManager());
        assertNotNull(manager.getCachedContestStatuses());
        assertNotNull(manager.getLog());

        // check the handlers is created as expected
        Map<HandlerRegistryInfo, PhaseHandler> handlers = manager.getHandlers();
        assertEquals(3, handlers.size());

        String[] names = {"draft", "draft", "scheduled"};
        long[] ids = {1, 1, 2};
        PhaseOperationEnum[] enums = {PhaseOperationEnum.START, PhaseOperationEnum.END, PhaseOperationEnum.START};

        for (int i = 0; i < names.length; ++i) {
            assertTrue(handlers.get(new HandlerRegistryInfo(new PhaseType(ids[i], names[i]), enums[i])) instanceof PhaseHandler);
        }

        ;

        // check the type names at last
        assertEquals("The type name is not correct", "Newdraft",
            TestHelper.getFieldValue(CockpitPhaseManager.class, "draftPhaseTypeName", manager));
        assertEquals("The type name is not correct", "Scheduled",
            TestHelper.getFieldValue(CockpitPhaseManager.class, "scheduledPhaseTypeName", manager));
        assertEquals("The type name is not correct", "Active",
            TestHelper.getFieldValue(CockpitPhaseManager.class, "activePhaseTypeName", manager));
        assertEquals("The type name is not correct", "Action Required",
            TestHelper.getFieldValue(CockpitPhaseManager.class, "actionRequiredPhaseTypeName", manager));
        assertEquals("The type name is not correct", "In Danger",
            TestHelper.getFieldValue(CockpitPhaseManager.class, "inDangerPhaseTypeName", manager));
        assertEquals("The type name is not correct", "Insufficient Submissions - ReRun Possible",
            TestHelper.getFieldValue(CockpitPhaseManager.class,
                "insufficientSubmissionsReRunPossiblePhaseTypeName", manager));
        assertEquals("The type name is not correct", "NewExtended",
            TestHelper.getFieldValue(CockpitPhaseManager.class, "extendedPhaseTypeName", manager));
        assertEquals("The type name is not correct", "Repost",
            TestHelper.getFieldValue(CockpitPhaseManager.class, "repostPhaseTypeName", manager));
        assertEquals("The type name is not correct", "Insufficient Submissions",
            TestHelper.getFieldValue(CockpitPhaseManager.class, "insufficientSubmissionsPhaseTypeName", manager));
        assertEquals("The type name is not correct", "No Winner Chosen",
            TestHelper.getFieldValue(CockpitPhaseManager.class, "noWinnerChosenPhaseTypeName", manager));
        assertEquals("The type name is not correct", "Completed",
            TestHelper.getFieldValue(CockpitPhaseManager.class, "completedPhaseTypeName", manager));
        assertEquals("The type name is not correct", "NewAbandoned",
            TestHelper.getFieldValue(CockpitPhaseManager.class, "abandonedPhaseTypeName", manager));
        assertEquals("The type name is not correct", "Cancelled",
            TestHelper.getFieldValue(CockpitPhaseManager.class, "cancelledPhaseTypeName", manager));
    }

    /**
     * Test the method getPhases(long) for accuracy.
     *
     * @throws Exception to JUnit if any error occurs
     */
    public void testGetPhases() throws Exception {
        // no such phase,
        assertNull(manager.getPhases(1000));

        // get an exist project and check its value
        Project proj = manager.getPhases(1);
        checkProject(proj);
    }

    /**
     * Test the method getPhases(long[]) for accuracy.
     *
     * @throws Exception to JUnit if any error occurs
     */
    public void testGetPhasesArray() throws Exception {
        // empty array
        assertTrue(manager.getPhases(new long[0]).length == 0);

        long[] ids = {1, 1000, 1};

        // get an exist project and check its value
        Project[] proj = manager.getPhases(ids);
        checkProject(proj[0]);
        assertNull(proj[1]);
        checkProject(proj[2]);
    }

    /**
     * Test the method getAllPhaseTypes() for accuracy.
     *
     * @throws Exception to JUnit if any error occurs
     */
    public void testGetAllPhaseTypes() throws Exception {
        PhaseType[] types = manager.getAllPhaseTypes();
        ContestStatus[] statuses = contestManager.getAllContestStatuses().toArray(new ContestStatus[0]);
        assertEquals(statuses.length, types.length);

        for (int i = 0; i < types.length; ++i) {
            assertEquals(statuses[i].getContestStatusId().longValue(), types[i].getId());
            assertEquals(statuses[i].getName(), types[i].getName());
        }
    }

    /**
     * Test the method canStart(Phase) for accuracy.
     *
     * @throws Exception to JUnit
     */
    public void testCanStart() throws Exception {
        // get the phase to test
        Project proj = manager.getPhases(1);
        Phase[] phases = proj.getAllPhases();
        Phase draftPhase = null;
        Phase phase = null;

        for (int i = 0; i < phases.length; ++i) {
            if (phases[i].getId() == 1) {
                phase = phases[i];
            }

            if (phases[i].getId() == 0) {
                draftPhase = phases[i];
            }
        }

        assertNotNull(draftPhase);
        assertNotNull(phase);

        manager.registerHandler(new MockPhaseHandler(),
            new PhaseType(phase.getPhaseType().getId(), phase.getPhaseType().getName()), PhaseOperationEnum.START);
        // the status is updated
        assertTrue(manager.canStart(phase));
        assertFalse(manager.canStart(draftPhase));
    }

    /**
     * Test the method start(Phase, String) for accuracy.
     *
     * @throws Exception to JUnit
     */
    public void testStart() throws Exception {
        // get the phase to test
        Project proj = manager.getPhases(1);
        Phase[] phases = proj.getAllPhases();
        Phase draftPhase = null;
        Phase phase = null;

        for (int i = 0; i < phases.length; ++i) {
            if (phases[i].getId() == 1) {
                phase = phases[i];
            }

            if (phases[i].getId() == 0) {
                draftPhase = phases[i];
            }
        }

        assertNotNull(draftPhase);
        assertNotNull(phase);

        manager.registerHandler(new MockPhaseHandler(),
            new PhaseType(phase.getPhaseType().getId(), phase.getPhaseType().getName()), PhaseOperationEnum.START);
        // the status is updated
        manager.start(phase, "operator");
        assertEquals(1, contestManager.getContest(1).getStatus().getContestStatusId().longValue());
        assertEquals(PhaseStatus.OPEN, phase.getPhaseStatus());

        manager.start(draftPhase, "operator");
        // the status is not updated
        assertEquals(1, contestManager.getContest(1).getStatus().getContestStatusId().longValue());
    }

    /**
     * Test the method canEnd(Phase) for accuracy.
     *
     * @throws Exception to JUnit
     */
    public void testCanEnd() throws Exception {
        // get the phase to test
        Project proj = manager.getPhases(1);
        Phase[] phases = proj.getAllPhases();
        Phase draftPhase = null;
        Phase phase = null;

        for (int i = 0; i < phases.length; ++i) {
            if (phases[i].getId() == 1) {
                phase = phases[i];
            }

            if (phases[i].getId() == 0) {
                draftPhase = phases[i];
            }
        }

        manager.registerHandler(new MockPhaseHandler(),
            new PhaseType(phase.getPhaseType().getId(), phase.getPhaseType().getName()), PhaseOperationEnum.END);
        assertNotNull(draftPhase);
        assertNotNull(phase);

        // the status is updated
        assertTrue(manager.canEnd(phase));
        assertFalse(manager.canEnd(draftPhase));
    }

    /**
     * Test the method end(Phase, String) for accuracy.
     *
     * @throws Exception to JUnit
     */
    public void testEnd() throws Exception {
        // get the phase to test
        Project proj = manager.getPhases(1);
        Phase[] phases = proj.getAllPhases();
        Phase draftPhase = null;
        Phase phase = null;

        for (int i = 0; i < phases.length; ++i) {
            if (phases[i].getId() == 1) {
                phase = phases[i];
            }

            if (phases[i].getId() == 0) {
                draftPhase = phases[i];
            }
        }

        assertNotNull(draftPhase);
        assertNotNull(phase);
        manager.registerHandler(new MockPhaseHandler(),
            new PhaseType(phase.getPhaseType().getId(), phase.getPhaseType().getName()), PhaseOperationEnum.END);

        // the status is updated
        manager.end(phase, "operator");
        assertEquals(1, contestManager.getContest(1).getStatus().getContestStatusId().longValue());
        assertEquals(PhaseStatus.CLOSED, phase.getPhaseStatus());

        manager.end(draftPhase, "operator");
        // the status is not updated
        assertEquals(1, contestManager.getContest(1).getStatus().getContestStatusId().longValue());
    }

    /**
     * Test the method registerHandler(PhaseHandler, PhaseType, PhaseOperationEnum) for accuracy.
     *
     * @throws Exception to JUnit
     */
    public void testRegisterHandler() {
        PhaseHandler handler = new MockPhaseHandler();
        manager.registerHandler(handler, new PhaseType(12, "Draft"), PhaseOperationEnum.END);
        assertSame(handler,
            manager.getHandlers().get(new HandlerRegistryInfo(new PhaseType(12, "Draft"), PhaseOperationEnum.END)));

        // test that the old one would be replaced
        handler = new MockPhaseHandler();
        manager.registerHandler(handler, new PhaseType(12, "Draft"), PhaseOperationEnum.END);
        assertSame(handler,
            manager.getHandlers().get(new HandlerRegistryInfo(new PhaseType(12, "Draft"), PhaseOperationEnum.END)));
    }

    /**
     * Test the method unregisterHandler(PhaseType, PhaseOperationEnum) for accuracy.
     *
     * @throws Exception to JUnit
     */
    public void testUnregisterHandler() {
        PhaseHandler handler = new MockPhaseHandler();
        manager.registerHandler(handler, new PhaseType(12, "Draft"), PhaseOperationEnum.END);

        // remove the handler
        assertSame(handler, manager.unregisterHandler(new PhaseType(12, "Draft"), PhaseOperationEnum.END));
        // remove it again
        assertNull(manager.unregisterHandler(new PhaseType(12, "Draft"), PhaseOperationEnum.END));
    }

    /**
     * Test the method getAllHandlers() for accuracy.
     *
     * @throws Exception to JUnit
     */
    public void testGetAllHandlers() {
        // clear the handlers
        manager.setHandlers(new HashMap<HandlerRegistryInfo, PhaseHandler>());

        // add some handlers
        PhaseHandler handler = new MockPhaseHandler();
        manager.registerHandler(handler, new PhaseType(12, "Draft"), PhaseOperationEnum.END);
        manager.registerHandler(handler, new PhaseType(14, "Draft"), PhaseOperationEnum.END);
        handler = new MockPhaseHandler();
        manager.registerHandler(handler, new PhaseType(32, "Draft"), PhaseOperationEnum.END);
        manager.registerHandler(handler, new PhaseType(24, "Draft"), PhaseOperationEnum.END);
        handler = new MockPhaseHandler();
        manager.registerHandler(handler, new PhaseType(1, "Draft"), PhaseOperationEnum.END);

        assertEquals(3, manager.getAllHandlers().length);
    }

    /**
     * Test the method getHandlerRegistrationInfo(PhaseHandler) for accuracy.
     *
     * @throws Exception to JUnit
     */
    public void testGetHandlerRegistrationInfo() {
        // clear the handlers
        manager.setHandlers(new HashMap<HandlerRegistryInfo, PhaseHandler>());

        // add some handlers
        PhaseHandler handler = new MockPhaseHandler();
        manager.registerHandler(handler, new PhaseType(12, "Draft"), PhaseOperationEnum.END);
        manager.registerHandler(handler, new PhaseType(14, "Draft"), PhaseOperationEnum.END);
        handler = new MockPhaseHandler();
        manager.registerHandler(handler, new PhaseType(32, "Draft"), PhaseOperationEnum.END);
        manager.registerHandler(handler, new PhaseType(24, "Draft"), PhaseOperationEnum.END);
        handler = new MockPhaseHandler();
        manager.registerHandler(handler, new PhaseType(1, "Draft"), PhaseOperationEnum.END);
        manager.registerHandler(handler, new PhaseType(2, "Draft"), PhaseOperationEnum.END);

        // test and verify the results
        List<HandlerRegistryInfo> list = Arrays.asList(manager.getHandlerRegistrationInfo(handler));
        assertEquals(2, list.size());
        assertTrue(list.contains(new HandlerRegistryInfo(new PhaseType(1, "Draft"), PhaseOperationEnum.END)));
        assertTrue(list.contains(new HandlerRegistryInfo(new PhaseType(2, "Draft"), PhaseOperationEnum.END)));
        assertTrue((manager.getHandlerRegistrationInfo(new MockPhaseHandler())).length == 0);
    }

    /**
     * Test the method getHandlers() for accuracy.
     *
     * @throws Exception to JUnit
     */
    public void testGetHandlers() {
        // clear the handlers
        manager.setHandlers(new HashMap<HandlerRegistryInfo, PhaseHandler>());

        // add some handlers
        PhaseHandler handler = new MockPhaseHandler();
        manager.registerHandler(handler, new PhaseType(12, "Draft"), PhaseOperationEnum.END);
        manager.registerHandler(handler, new PhaseType(14, "Draft"), PhaseOperationEnum.END);
        handler = new MockPhaseHandler();
        manager.registerHandler(handler, new PhaseType(32, "Draft"), PhaseOperationEnum.END);
        manager.registerHandler(handler, new PhaseType(24, "Draft"), PhaseOperationEnum.END);
        handler = new MockPhaseHandler();
        manager.registerHandler(handler, new PhaseType(1, "Draft"), PhaseOperationEnum.END);
        manager.registerHandler(handler, new PhaseType(2, "Draft"), PhaseOperationEnum.END);

        assertEquals(6, manager.getHandlers().size());
        // check that the shallow copy is made
        assertNotSame(manager.getHandlers(), manager.getHandlers());
    }

    /**
     * Method to check the value of the default type names
     *
     * @throws Exception if any error occurs
     */
    private void checkDefaultTypeNames() throws Exception {
        assertEquals("The type name is not correct", "Draft",
            TestHelper.getFieldValue(CockpitPhaseManager.class, "draftPhaseTypeName", manager));
        assertEquals("The type name is not correct", "Scheduled",
            TestHelper.getFieldValue(CockpitPhaseManager.class, "scheduledPhaseTypeName", manager));
        assertEquals("The type name is not correct", "Active",
            TestHelper.getFieldValue(CockpitPhaseManager.class, "activePhaseTypeName", manager));
        assertEquals("The type name is not correct", "Action Required",
            TestHelper.getFieldValue(CockpitPhaseManager.class, "actionRequiredPhaseTypeName", manager));
        assertEquals("The type name is not correct", "In Danger",
            TestHelper.getFieldValue(CockpitPhaseManager.class, "inDangerPhaseTypeName", manager));
        assertEquals("The type name is not correct", "Insufficient Submissions - ReRun Possible",
            TestHelper.getFieldValue(CockpitPhaseManager.class,
                "insufficientSubmissionsReRunPossiblePhaseTypeName", manager));
        assertEquals("The type name is not correct", "Extended",
            TestHelper.getFieldValue(CockpitPhaseManager.class, "extendedPhaseTypeName", manager));
        assertEquals("The type name is not correct", "Repost",
            TestHelper.getFieldValue(CockpitPhaseManager.class, "repostPhaseTypeName", manager));
        assertEquals("The type name is not correct", "Insufficient Submissions",
            TestHelper.getFieldValue(CockpitPhaseManager.class, "insufficientSubmissionsPhaseTypeName", manager));
        assertEquals("The type name is not correct", "No Winner Chosen",
            TestHelper.getFieldValue(CockpitPhaseManager.class, "noWinnerChosenPhaseTypeName", manager));
        assertEquals("The type name is not correct", "Completed",
            TestHelper.getFieldValue(CockpitPhaseManager.class, "completedPhaseTypeName", manager));
        assertEquals("The type name is not correct", "Abandoned",
            TestHelper.getFieldValue(CockpitPhaseManager.class, "abandonedPhaseTypeName", manager));
        assertEquals("The type name is not correct", "Cancelled",
            TestHelper.getFieldValue(CockpitPhaseManager.class, "cancelledPhaseTypeName", manager));
    }

    /**
     * Check wether the project is retrieved correctly from the contest.
     *
     * @param project the project to check
     * @throws Exception to JUnit
     */
    private void checkProject(Project project) throws Exception {
        // check the id
        Contest contest = contestManager.getContest(1);
        assertEquals(contest.getContestId().longValue(), project.getId());

        // check other field
        assertEquals(contest.getContestChannel(), project.getAttribute("contestContestChannel"));
        assertEquals(contest.getContestType(), project.getAttribute("contestContestType"));
        assertEquals(contest.getCreatedUser(), project.getAttribute("contestCreatedUser"));
        assertEquals(contest.getEndDate(), project.getAttribute("contestEndDate"));
        assertEquals(contest.getEventId(), project.getAttribute("contestEventId"));
        assertEquals(contest.getForumId(), project.getAttribute("contestForumId"));
        assertEquals(contest.getName(), project.getAttribute("contestName"));
        assertEquals(contest.getProjectId(), project.getAttribute("contestProjectId"));
        assertEquals(contest.getStartDate(), project.getAttribute("contestStartDate"));
        assertEquals(contest.getTcDirectProjectId(), project.getAttribute("contestTcDirectProjectId"));
        assertEquals(contest.getWinnerAnnoucementDeadline(),
            project.getAttribute("contestWinnerAnnoucementDeadline"));

        // check the collection field
        Set set = (Set) project.getAttribute("contestDocuments");
        assertTrue(set.size() == 1);
        assertSame(contest.getDocuments().iterator().next(), set.iterator().next());

        set = (Set) project.getAttribute("contestFileTypes");
        assertTrue(set.size() == 1);
        assertSame(contest.getFileTypes().iterator().next(), set.iterator().next());

        set = (Set) project.getAttribute("contestResults");
        assertTrue(set.size() == 1);
        assertSame(contest.getResults().iterator().next(), set.iterator().next());

        set = (Set) project.getAttribute("contestSubmissions");
        assertTrue(set.size() == 1);
        assertSame(contest.getSubmissions().iterator().next(), set.iterator().next());

        // check the Config
        assertEquals("description1", project.getAttribute("testConfig 1"));
        assertEquals("description2", project.getAttribute("testConfig 2"));

        // check the phases
        Phase[] phases = project.getAllPhases();
        assertEquals(2, phases.length);

        for (int i = 0; i < phases.length; ++i) {
            Phase phase = phases[i];
            assertEquals(PhaseStatus.OPEN, phase.getPhaseStatus());

            if (phase.getId() == 0) {
                // draft phase
                assertEquals(0, phase.getPhaseType().getId());
                assertEquals("Draft", phase.getPhaseType().getName());
                assertEquals("desDraft", phase.getAttribute("description"));
                assertEquals("Draft", phase.getAttribute("name"));
            } else if (phase.getId() == 1) {
                // Scheduled phase
                assertEquals(1, phase.getPhaseType().getId());
                assertEquals("Scheduled", phase.getPhaseType().getName());
                assertEquals("desScheduled", phase.getAttribute("description"));
                assertEquals("Scheduled", phase.getAttribute("name"));
                assertTrue(phase.getAttribute("ScheduledStatuses") instanceof List);
            } else {
                // should not reach here
                fail("Should not reach here.");
            }
        }
    }
}
