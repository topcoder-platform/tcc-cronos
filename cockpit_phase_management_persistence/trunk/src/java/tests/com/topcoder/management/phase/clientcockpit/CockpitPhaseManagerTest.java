/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.date.workdays.Workdays;

import com.topcoder.management.phase.HandlerRegistryInfo;
import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.management.phase.PhaseOperationEnum;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.service.studio.contest.ContestProperty;
import com.topcoder.service.studio.contest.ContestStatus;

import com.topcoder.util.cache.Cache;
import com.topcoder.util.cache.SimpleCache;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;

import java.lang.reflect.Field;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


/**
 * <p>
 * Unit test case of {@link CockpitPhaseManager}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CockpitPhaseManagerTest extends TestCase {
    /**
     * <p>
     * Represents JNDI name of ContestManager in configuration used for testing.
     * </p>
     */
    public static final String CONTEST_MANAGER_JNDI_NAME = "contestManagerRemote";

    /**
     * <p>
     * Represents configuration namespace used for testing.
     * </p>
     */
    private static final String JNDI_NAMESPACE = "CockpitPhaseManagerJNDI";

    /**
     * <p>
     * Represents configuration namespace used for testing.
     * </p>
     */
    private static final String OF_NAMESPACE = "CockpitPhaseManagerOF";

    /**
     * <p>
     * Represents CockpitPhaseManager instance to test against.
     * </p>
     */
    private CockpitPhaseManager manager;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(CockpitPhaseManagerTest.class);

        return suite;
    }

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "cockpit-jndi.xml");
        TestHelper.loadXMLConfig("test_files" + File.separator + "cockpit-of.xml");
        TestHelper.loadXMLConfig("test_files" + File.separator + "object-factory.xml");
        TestHelper.loadXMLConfig("test_files" + File.separator + "jndi-utils.xml");
        TestHelper.loadXMLConfig("test_files" + File.separator + "memory-usage.xml");
        TestHelper.loadXMLConfig("test_files" + File.separator + "cockpit-invalid.xml");

        manager = new CockpitPhaseManager(JNDI_NAMESPACE);
        TestHelper.initContestStatuses(manager.getContestManager());
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
        manager = null;
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManager()</code> constructor for accuracy.
     * </p>
     * <p>
     * Verify if <code>CockpitPhaseManager</code> instance is created and the underlying contest manager is
     * <code>null</code>.
     * </p>
     */
    public void testCtor1() {
        manager = new CockpitPhaseManager();
        assertNotNull("Unable to instantiate CockpitPhaseManager", manager);
        assertNull("Underlying contest manager should be null", manager.getContestManager());
        assertNull("Underlying logger should be null", manager.getLog());
        assertNull("Underlying cache should be null", manager.getCachedContestStatuses());
        assertTrue("Underlying handlers should be empty", manager.getHandlers().isEmpty());
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManager(String namespace)</code> method for accuracy when underlying
     * <code>ContestManager</code> instance is obtained via JNDI.
     * </p>
     * <p>
     * Verify if <code>CockpitPhaseManager</code> instance is created and the configuration values are
     * loaded properly.
     * </p>
     */
    public void testCtor2_JNDI() {
        assertNotNull("Unable to instantiate CockpitPhaseManager", manager);

        ContestManager contestManager = manager.getContestManager();
        assertNotNull("Contest manager should not be null", contestManager);
        assertTrue("Incorrect contest manager type", contestManager instanceof MockContestManager);
        assertNotNull("Log should not be null", manager.getLog());
        assertNotNull("Cache should not be null", manager.getCachedContestStatuses());
        assertTrue("Incorrect number of registered handlers", manager.getHandlers().size() == 3);
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManager(String namespace)</code> method for accuracy when underlying
     * <code>ContestManager</code> instance is created by ObjectFactory.
     * </p>
     * <p>
     * Verify if <code>CockpitPhaseManager</code> instance is created and the configuration values are
     * loaded properly.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCtor2_OF() throws Exception {
        manager = new CockpitPhaseManager(OF_NAMESPACE);
        assertNotNull("Unable to instantiate CockpitPhaseManager", manager);

        ContestManager contestManager = manager.getContestManager();
        assertNotNull("Contest manager should not be null", contestManager);
        assertTrue("Incorrect contest manager type", contestManager instanceof MockContestManager);
        assertNull("Log should be null", manager.getLog());
        assertNull("Cache should be null", manager.getCachedContestStatuses());
        assertTrue("Incorrect number of registered handlers", manager.getHandlers().size() == 3);
    }

    /**
     * <p>
     * Tests
     * <code>CockpitPhaseManager(ContestManager contestManager, String logName, Cache cachedContestStatuses)</code>
     * constructor for accuracy.
     * </p>
     * <p>
     * Verify if <code>CockpitPhaseManager</code> instance is created and the configuration values are
     * loaded properly.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCtor3() throws Exception {
        ContestManager contestManager = new MockContestManager();
        manager = new CockpitPhaseManager(contestManager, "myLog", null);
        assertNotNull("Unable to instantiate CockpitPhaseManager", manager);

        assertEquals("Incorrect contest manager", contestManager, manager.getContestManager());
        assertNotNull("Log should be null", manager.getLog());
        assertNull("Cache should be null", manager.getCachedContestStatuses());
        assertTrue("Incorrect number of registered handlers", manager.getHandlers().isEmpty());
    }

    /**
     * <p>
     * Tests <code>updatePhases(Project project, String operator)</code> method for accuracy. This method
     * does nothing.
     * </p>
     */
    public void testUpdatePhases() {
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        manager.updatePhases(project, "TCS");
    }

    /**
     * <p>
     * Tests <code>getPhases(long contestId)</code> method for accuracy when the contest status is Draft.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetPhases_Draft() throws Exception {
        ContestStatus draft = manager.getContestManager().getContestStatus(1);
        performGetPhaseTest(draft, 2);
    }

    /**
     * <p>
     * Tests <code>getPhases(long contestId)</code> method for accuracy when the contest status is
     * Scheduled.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetPhases_Scheduled() throws Exception {
        ContestStatus scheduled = manager.getContestManager().getContestStatus(2);
        performGetPhaseTest(scheduled, 2);
    }

    /**
     * <p>
     * Tests <code>getPhases(long contestId)</code> method for accuracy when the contest status is Active.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetPhases_Active() throws Exception {
        ContestStatus active = manager.getContestManager().getContestStatus(3);
        performGetPhaseTest(active, 3);
    }

    /**
     * <p>
     * Tests <code>getPhases(long contestId)</code> method for accuracy when the contest status is Action
     * Required.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetPhases_ActionRequired() throws Exception {
        ContestStatus actionRequired = manager.getContestManager().getContestStatus(4);
        performGetPhaseTest(actionRequired, 3);
    }

    /**
     * <p>
     * Tests <code>getPhases(long contestId)</code> method for accuracy when the contest status is In
     * Danger.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetPhases_InDanger() throws Exception {
        ContestStatus inDanger = manager.getContestManager().getContestStatus(7);
        performGetPhaseTest(inDanger, 3);
    }

    /**
     * <p>
     * Tests <code>getPhases(long contestId)</code> method for accuracy when the contest status is
     * Insufficient Submissions - ReRun Possible.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetPhases_InsufficientSubmissionsRP()
        throws Exception {
        ContestStatus isrp = manager.getContestManager().getContestStatus(5);
        performGetPhaseTest(isrp, 3);
    }

    /**
     * <p>
     * Tests <code>getPhases(long contestId)</code> method for accuracy when the contest status is Extended.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetPhases_Extended() throws Exception {
        ContestStatus extended = manager.getContestManager().getContestStatus(9);
        performGetPhaseTest(extended, 3);
    }

    /**
     * <p>
     * Tests <code>getPhases(long contestId)</code> method for accuracy when the contest status is Repost.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetPhases_Repost() throws Exception {
        ContestStatus repost = manager.getContestManager().getContestStatus(11);
        performGetPhaseTest(repost, 3);
    }

    /**
     * <p>
     * Tests <code>getPhases(long contestId)</code> method for accuracy when the contest status is
     * Insufficient Submissions.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetPhases_InsufficientSubmissions()
        throws Exception {
        ContestStatus is = manager.getContestManager().getContestStatus(10);
        performGetPhaseTest(is, 3);
    }

    /**
     * <p>
     * Tests <code>getPhases(long contestId)</code> method for accuracy when the contest status is No Winner
     * Chosen.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetPhases_NoWinnerChosen() throws Exception {
        ContestStatus noWinnerChosen = manager.getContestManager().getContestStatus(13);
        performGetPhaseTest(noWinnerChosen, 4);
    }

    /**
     * <p>
     * Tests <code>getPhases(long contestId)</code> method for accuracy when all the contest statuses has
     * been cached.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetPhases_FromCache() throws Exception {
        ContestStatus noWinnerChosen = manager.getContestManager().getContestStatus(13);
        performGetPhaseTest(noWinnerChosen, 4);

        ContestStatus is = manager.getContestManager().getContestStatus(10);
        performGetPhaseTest(is, 3);
    }

    /**
     * <p>
     * Tests <code>getPhases(long contestId)</code> method for accuracy when no contest is found for given
     * contest id.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetPhases_NoContest() throws Exception {
        assertNull("Project should be null for no contest", manager.getPhases(1));
    }

    /**
     * <p>
     * This helper method performs the accuracy test for getPhases(long) method.
     * </p>
     *
     * @param status the contest status.
     * @param phaseNums the expected number of phases.
     * @throws Exception to jUnit.
     */
    private void performGetPhaseTest(ContestStatus status, int phaseNums)
        throws Exception {
        Contest contest = TestHelper.createContest(1, TestHelper.createDate(2008, 3, 20), status);
        ContestManager contestManager = manager.getContestManager();
        contest = contestManager.createContest(contest);

        Project project = manager.getPhases(1);
        assertProjectMapped(contest, project);

        Phase[] phases = project.getAllPhases();
        assertEquals("Incorrect number of phases", phaseNums, phases.length);

        for (Phase phase : phases) {
            assertEquals("Incorrect phase length", 0, phase.getLength());
            assertPhaseMapped(contestManager.getContestStatus(phase.getId()), phase);
        }
    }

    /**
     * <p>
     * Asserts that contest status is correctly mapped to phase.
     * </p>
     *
     * @param status the contest status.
     * @param phase the phase.
     */
    private void assertPhaseMapped(ContestStatus status, Phase phase) {
        String statusName = status.getName();
        long statusId = status.getContestStatusId().longValue();
        assertEquals("Incorrect phase id", statusId, phase.getId());
        assertPhaseTypeMapped(status, phase.getPhaseType());
        assertEquals("Incorrect name attribute", statusName, phase.getAttribute("name"));
        assertEquals("Incorrect description attribute", status.getDescription(), phase.getAttribute("description"));
        assertEquals("Incorrect statuses attribute", status.getStatuses(),
            phase.getAttribute(statusName + "Statuses"));
        assertEquals("Incorrect phase status", PhaseStatus.OPEN, phase.getPhaseStatus());
    }

    /**
     * <p>
     * Asserts that contest status is correctly mapped to the phase type.
     * </p>
     *
     * @param status the contest status.
     * @param phaseType the phase type.
     */
    private void assertPhaseTypeMapped(ContestStatus status, PhaseType phaseType) {
        assertEquals("Incorrect phase type id", status.getContestStatusId().longValue(), phaseType.getId());
        assertEquals("Incorrect phase type name", status.getName(), phaseType.getName());
    }

    /**
     * <p>
     * Asserts that the Contest is correctly mapped to the project.
     * </p>
     *
     * @param contest the contest.
     * @param project the project.
     * @throws Exception to jUnit.
     */
    private void assertProjectMapped(Contest contest, Project project)
        throws Exception {
        assertNotNull("The mapped project should not be null", project);

        assertEquals("Incorrect project id", contest.getContestId().longValue(), project.getId());
        assertEquals("Incorrect startDate", contest.getStartDate(), project.getStartDate());

        for (ContestConfig config : contest.getConfig()) {
            ContestProperty property = config.getId().getProperty();
            assertEquals("Incorrect config attribute", property.getDescription(),
                project.getAttribute(config.getValue() + " " + property.getPropertyId()));
        }

        for (Iterator iter = project.getAttributes().entrySet().iterator(); iter.hasNext();) {
            Map.Entry entry = (Entry) iter.next();
            String key = (String) entry.getKey();

            if (key.startsWith("contest")) {
                Field field = Contest.class.getDeclaredField(formatFieldName(key));
                field.setAccessible(true);
                assertEquals("Incorrect project's attribute '" + key + "'", entry.getValue(), field.get(contest));
            }
        }
    }

    /**
     * <p>
     * Formats the given project attribute value as proper field name (camel case).
     * </p>
     *
     * @param value the attribute value.
     * @return the project field name.
     */
    private String formatFieldName(String value) {
        // from position 0 to 6 is "contest" string
        StringBuilder builder = new StringBuilder();
        builder.append(Character.toLowerCase(value.charAt(7)));
        builder.append(value.substring(8));

        return builder.toString();
    }

    /**
     * <p>
     * Tests <code>getPhases(long[] contestIds)</code> method for accuracy.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetPhases2() throws Exception {
        ContestManager contestManager = manager.getContestManager();
        ContestStatus draft = contestManager.getContestStatus(1);
        Contest contest = TestHelper.createContest(1, TestHelper.createDate(2008, 3, 20), draft);

        contest = contestManager.createContest(contest);

        Project[] projects = manager.getPhases(new long[] {1, 2});
        assertEquals("Incorrect number of projects", 2, projects.length);
        assertProjectMapped(contest, projects[0]);
        assertNull("Incorrect project-2", projects[1]);

        Phase[] phases = projects[0].getAllPhases();
        assertEquals("Incorrect number of phases", 2, phases.length);

        for (Phase phase : phases) {
            assertEquals("Incorrect phase length", 0, phase.getLength());
            assertPhaseMapped(contestManager.getContestStatus(phase.getId()), phase);
        }
    }

    /**
     * <p>
     * Tests <code>getPhases(long[] contestIds)</code> method for accuracy when <code>contestIds</code> is
     * empty.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetPhases2_EmptyArray() throws Exception {
        Project[] projects = manager.getPhases(new long[0]);
        assertTrue("The projects should be empty", projects.length == 0);
    }

    /**
     * <p>
     * Tests <code>getAllPhaseTypes()</code> method for accuracy.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetAllPhaseTypes() throws Exception {
        PhaseType[] phaseTypes = manager.getAllPhaseTypes();
        ContestManager contestManager = manager.getContestManager();
        assertEquals("Incorrect number of phase types", contestManager.getAllContestStatuses().size(),
            phaseTypes.length);

        for (PhaseType phaseType : phaseTypes) {
            assertPhaseTypeMapped(contestManager.getContestStatus(phaseType.getId()), phaseType);
        }
    }

    /**
     * <p>
     * Tests <code>getAllPhaseStatuses()</code> method for accuracy.
     * </p>
     * <p>
     * Verify if the returned array contains 3 PhaseStatuses defined in <code>PhaseStatus</code> class.
     * </p>
     */
    public void testGetAllPhaseStatuses() {
        PhaseStatus[] statuses = manager.getAllPhaseStatuses();
        assertEquals("Incorrect number of phase statuses", 3, statuses.length);

        List<PhaseStatus> list = Arrays.asList(statuses);
        assertTrue("Should contain PhaseStatus.OPEN", list.contains(PhaseStatus.OPEN));
        assertTrue("Should contain PhaseStatus.SCHEDULED", list.contains(PhaseStatus.SCHEDULED));
        assertTrue("Should contain PhaseStatus.CLOSED", list.contains(PhaseStatus.CLOSED));
    }

    /**
     * <p>
     * Tests <code>canStart(Phase phase)</code> method for accuracy when a phase handler is found for given
     * phase.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCanStart1() throws Exception {
        // create a phase which can be started
        PhaseType type = new PhaseType(1, "Draft");
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 10);
        phase.setPhaseType(type);
        assertTrue("Phase should be able to start", manager.canStart(phase));
    }

    /**
     * <p>
     * Tests <code>canStart(Phase phase)</code> method for accuracy when none phase handler is found for
     * given phase.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCanStart2() throws Exception {
        // create a phase which can be started
        PhaseType type = new PhaseType(2, "Active");
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 10);
        phase.setPhaseType(type);
        assertFalse("Phase should not be able to start", manager.canStart(phase));
    }

    /**
     * <p>
     * Tests <code>start(Phase phase, String operator)</code> method for accuracy when a handler is found
     * for the phase.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testStart() throws Exception {
        ContestManager contestManager = manager.getContestManager();
        ContestStatus draft = contestManager.getContestStatus(1);
        Contest contest = TestHelper.createContest(1, TestHelper.createDate(2008, 3, 20), draft);
        contest = contestManager.createContest(contest);

        Project project = manager.getPhases(1);
        Phase phase = TestHelper.findPhaseByTypeName(project.getAllPhases(), "Draft");

        manager.start(phase, "TCS");

        // verify if the handler has performed
        Map<HandlerRegistryInfo, PhaseHandler> handlers = manager.getHandlers();
        PhaseHandler handler = handlers.get(new HandlerRegistryInfo(phase.getPhaseType(), PhaseOperationEnum.START));
        assertTrue("The handler should have performed", ((MockPhaseHandler) handler).isPerformed());

        // verify if the contest status is updated
        Contest contest2 = contestManager.getContest(project.getId());
        ContestStatus status = contest2.getStatus();
        assertEquals("Contest status should be updated", status.getContestStatusId().longValue(),
            phase.getPhaseType().getId());
    }

    /**
     * <p>
     * Tests <code>start(Phase phase, String operator)</code> method for accuracy when no handler is found
     * for the phase.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testStart_NoHandler() throws Exception {
        ContestManager contestManager = manager.getContestManager();
        ContestStatus draft = contestManager.getContestStatus(1);
        Contest contest = TestHelper.createContest(1, TestHelper.createDate(2008, 3, 20), draft);
        contest = contestManager.createContest(contest);

        Project project = manager.getPhases(1);
        Phase phase = TestHelper.findPhaseByTypeName(project.getAllPhases(), "Scheduled");
        manager.start(phase, "TCS");
    }

    /**
     * <p>
     * Tests <code>canEnd(Phase phase)</code> method for accuracy when a phase handler is found for given
     * phase.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCanEnd1() throws Exception {
        // create a phase which can be started
        PhaseType type = new PhaseType(2, "Scheduled");
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 10);
        phase.setPhaseType(type);
        assertTrue("Phase should be able to start", manager.canEnd(phase));
    }

    /**
     * <p>
     * Tests <code>canEnd(Phase phase)</code> method for accuracy when none phase handler is found for given
     * phase.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCanEnd2() throws Exception {
        // create a phase which can be started
        PhaseType type = new PhaseType(3, "Active");
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 10);
        phase.setPhaseType(type);
        assertFalse("Phase should not be able to start", manager.canEnd(phase));
    }

    /**
     * <p>
     * Tests <code>end(Phase phase, String operator)</code> method for accuracy when a handler is found for
     * the phase.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testEnd() throws Exception {
        ContestManager contestManager = manager.getContestManager();
        ContestStatus scheduled = contestManager.getContestStatus(2);
        Contest contest = TestHelper.createContest(1, TestHelper.createDate(2008, 3, 20), scheduled);
        contest = contestManager.createContest(contest);

        Project project = manager.getPhases(1);
        Phase phase = TestHelper.findPhaseByTypeName(project.getAllPhases(), "Scheduled");

        manager.end(phase, "TCS");

        // verify if the handler has performed
        Map<HandlerRegistryInfo, PhaseHandler> handlers = manager.getHandlers();
        PhaseHandler handler = handlers.get(new HandlerRegistryInfo(phase.getPhaseType(), PhaseOperationEnum.END));
        assertTrue("The handler should have performed", ((MockPhaseHandler) handler).isPerformed());

        // verify if the contest status is updated
        Contest contest2 = contestManager.getContest(project.getId());
        ContestStatus status = contest2.getStatus();
        assertEquals("Contest status should be updated", status.getContestStatusId().longValue(),
            phase.getPhaseType().getId());
    }

    /**
     * <p>
     * Tests <code>end(Phase phase, String operator)</code> method for accuracy when no handler is found for
     * the phase.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testEnd_NoHandler() throws Exception {
        ContestManager contestManager = manager.getContestManager();
        ContestStatus draft = contestManager.getContestStatus(1);
        Contest contest = TestHelper.createContest(1, TestHelper.createDate(2008, 3, 20), draft);
        contest = contestManager.createContest(contest);

        Project project = manager.getPhases(1);
        Phase phase = TestHelper.findPhaseByTypeName(project.getAllPhases(), "Draft");

        manager.end(phase, "TCS");
    }

    /**
     * <p>
     * Tests <code>canCancel(Phase phase)</code> method for accuracy.
     * </p>
     * <p>
     * Verify if <code>false</code> is returned since the method always returns <code>false</code>.
     * </p>
     */
    public void testCanCancel() {
        assertFalse("Incorrect canCancel return value", manager.canCancel(null));
    }

    /**
     * <p>
     * Tests <code>cancel(Phase phase, String operator)</code> method for accuracy. This method does
     * nothing.
     * </p>
     */
    public void testCancel() {
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 1);
        manager.cancel(phase, "TCS");
    }

    /**
     * <p>
     * Tests <code>registerHandler(PhaseHandler handler, PhaseType type, PhaseOperationEnum op)</code>
     * method for accuracy.
     * </p>
     */
    public void testRegisterHandler() {
        // clear the current handlers.
        manager.setHandlers(new HashMap<HandlerRegistryInfo, PhaseHandler>());

        // create a handler
        PhaseHandler phaseHandler1 = new MockPhaseHandler();
        PhaseType type = new PhaseType(1, "Repost");
        HandlerRegistryInfo info = new HandlerRegistryInfo(type, PhaseOperationEnum.START);

        manager.registerHandler(phaseHandler1, info.getType(), info.getOperation());

        // get all handlers
        Map<HandlerRegistryInfo, PhaseHandler> handlers = manager.getHandlers();

        // verify if the handler is registered
        assertEquals("Incorrect number of all handlers", 1, handlers.size());
        assertEquals("Incorrect phase handler-1", phaseHandler1, handlers.get(info));

        // create another handler
        PhaseHandler phaseHandler2 = new MockPhaseHandler();

        // replace the oldOne
        manager.registerHandler(phaseHandler2, info.getType(), info.getOperation());
        handlers = manager.getHandlers();

        // verify if the newly registered handler replaces the old one
        assertEquals("Incorrect number of all handlers", 1, handlers.size());
        assertEquals("Incorrect phase handler-2", phaseHandler2, handlers.get(info));
    }

    /**
     * <p>
     * Tests <code>unregisterHandler(PhaseType type, PhaseOperationEnum op)</code> method for accuracy.
     * </p>
     */
    public void testUnregisterHandler() {
        // clear the current handlers.
        manager.setHandlers(new HashMap<HandlerRegistryInfo, PhaseHandler>());

        // create a handler
        PhaseHandler phaseHandler1 = new MockPhaseHandler();
        PhaseType type = new PhaseType(1, "Repost");
        HandlerRegistryInfo info = new HandlerRegistryInfo(type, PhaseOperationEnum.START);

        // try to unregister non-exist handler
        assertNull("Unregister non-exist handler, should be null",
            manager.unregisterHandler(type, info.getOperation()));

        // register a handler
        manager.registerHandler(phaseHandler1, info.getType(), info.getOperation());

        // get all handlers
        Map<HandlerRegistryInfo, PhaseHandler> handlers = manager.getHandlers();

        // verify if the handler is registered
        assertEquals("Incorrect number of all handlers", 1, handlers.size());
        assertEquals("Incorrect phase handler-1", phaseHandler1, handlers.get(info));

        // unregister the handler
        PhaseHandler handler = manager.unregisterHandler(type, info.getOperation());

        handlers = manager.getHandlers();
        // verify if the handler is unregistered
        assertTrue("Incorrect number of all handlers", handlers.isEmpty());
        assertEquals("Incorrect removed handler", phaseHandler1, handler);
    }

    /**
     * <p>
     * Tests <code>getAllHandlers()</code> method for accuracy.
     * </p>
     */
    public void testGetAllHandlers() {
        // clear the current handlers.
        manager.setHandlers(new HashMap<HandlerRegistryInfo, PhaseHandler>());

        // create a handler
        PhaseHandler phaseHandler = new MockPhaseHandler();

        // register the handler for two different phase type
        manager.registerHandler(phaseHandler, new PhaseType(1, "Repost"), PhaseOperationEnum.CANCEL);
        manager.registerHandler(phaseHandler, new PhaseType(2, "In Danger"), PhaseOperationEnum.END);

        // get all handlers
        PhaseHandler[] allHandlers = manager.getAllHandlers();

        // verify if only one handler is returned
        assertEquals("Incorrect number of all handlers", 1, allHandlers.length);
        assertEquals("Incorrect phase handler type", phaseHandler, allHandlers[0]);
    }

    /**
     * <p>
     * Tests <code>getHandlerRegistrationInfo(PhaseHandler handler)</code> method for accuracy.
     * </p>
     */
    public void testGetHandlerRegistrationInfo() {
        // clear the current handlers.
        manager.setHandlers(new HashMap<HandlerRegistryInfo, PhaseHandler>());

        // create a handler
        PhaseHandler phaseHandler = new MockPhaseHandler();

        // register the handler for two different phase type
        PhaseType type1 = new PhaseType(1, "Repost");
        PhaseType type2 = new PhaseType(2, "In Danger");
        manager.registerHandler(phaseHandler, type1, PhaseOperationEnum.CANCEL);
        manager.registerHandler(phaseHandler, type2, PhaseOperationEnum.END);

        HandlerRegistryInfo[] infos = manager.getHandlerRegistrationInfo(phaseHandler);
        assertEquals("Incorrect number of handler registration infos", 2, infos.length);
        assertEquals("Incorrect type-1", type1, infos[0].getType());
        assertEquals("Incorrect operation-1", PhaseOperationEnum.CANCEL, infos[0].getOperation());
        assertEquals("Incorrect type-2", type2, infos[1].getType());
        assertEquals("Incorrect operation-2", PhaseOperationEnum.END, infos[1].getOperation());
    }

    /**
     * <p>
     * Tests <code>setPhaseValidator(PhaseValidator phaseValidator)</code> method for accuracy. This method
     * does nothing.
     * </p>
     */
    public void testSetPhaseValidator() {
        manager.setPhaseValidator(null);
    }

    /**
     * <p>
     * Tests <code>getPhaseValidator()</code> method for accuracy.
     * </p>
     * <p>
     * <code>null</code> is always returned.
     * </p>
     */
    public void testGetPhaseValidator() {
        assertNull("Phase validator should be null", manager.getPhaseValidator());
    }

    /**
     * <p>
     * Tests <code>getContestManager()</code> method for accuracy.
     * </p>
     */
    public void testGetContestManager() {
        ContestManager contestManager = manager.getContestManager();
        assertNotNull("Contest manager should not be null", contestManager);
        assertTrue("Incorrect type of contest manager", contestManager instanceof MockContestManager);
    }

    /**
     * <p>
     * Tests <code>setContestManager(ContestManager contestManager)</code> method for accuracy.
     * </p>
     */
    public void testSetContestManager1() {
        ContestManager contestManager = new MockContestManager();
        manager.setContestManager(contestManager);
        assertEquals("Incorrect assigned contest manager", contestManager, manager.getContestManager());
    }

    /**
     * <p>
     * Tests <code>setContestManager(String contestManagerName)</code> method for accuracy.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testSetContestManager2() throws Exception {
        manager.setContestManager(CONTEST_MANAGER_JNDI_NAME);

        ContestManager contestManager = manager.getContestManager();
        assertNotNull("Contest manager should not be null", contestManager);
        assertTrue("Incorrect type of contest manager", contestManager instanceof MockContestManager);
    }

    /**
     * <p>
     * Tests <code>getLog()</code> method for accuracy.
     * </p>
     */
    public void testGetLog() {
        assertNotNull("Incorrect log, should not be null", manager.getLog());
    }

    /**
     * <p>
     * Tests <code>setLog(Log log)</code> method for accuracy.
     * </p>
     */
    public void testSetLog1() {
        // set to null value
        manager.setLog((Log) null);

        // verify if it is null
        assertNull("Incorrect log, should be null", manager.getLog());

        // set to a logger
        manager.setLog(LogManager.getLog());
        assertNotNull("Incorrect log, should not be null", manager.getLog());
    }

    /**
     * <p>
     * Tests <code>setLog(String logName)</code> method for accuracy.
     * </p>
     */
    public void testSetLog2() {
        // set to a name
        manager.setLog("logger");
        assertNotNull("Incorrect log, should not be null", manager.getLog());
    }

    /**
     * <p>
     * Tests <code>getCachedContestStatuses()</code> method for accuracy.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetCachedContestStatuses() throws Exception {
        // at the beginning, cache is empty
        Cache cache = manager.getCachedContestStatuses();
        assertNotNull("The cache should not be null", cache);
        assertTrue("Incorrect cache type", cache instanceof SimpleCache);
        assertTrue("Cache should be empty", cache.getSize() == 0);

        // get the phases
        ContestManager contestManager = manager.getContestManager();
        ContestStatus draft = contestManager.getContestStatus(1);
        Contest contest = TestHelper.createContest(1, TestHelper.createDate(2008, 3, 20), draft);
        contest = contestManager.createContest(contest);

        manager.getPhases(1);
        manager.getPhases(1);

        // the cache should be filled with all contest statuses
        cache = manager.getCachedContestStatuses();
        assertNotNull("The cache should not be null", cache);
        assertTrue("Cache should contain 1 entry", cache.getSize() == 1);

        Object value = cache.get("contestStatuses");
        assertEquals("Incorrect cached value", contestManager.getAllContestStatuses(), value);
    }

    /**
     * <p>
     * Tests <code>setCachedContestStatuses(Cache cache)</code> method for accuracy.
     * </p>
     */
    public void testSetCachedContestStatuses() {
        // set to null value
        manager.setCachedContestStatuses(null);

        // verify if it is null
        assertNull("Incorrect cache, should be null", manager.getCachedContestStatuses());

        // set to a cache instance
        manager.setCachedContestStatuses(new SimpleCache());
        assertNotNull("Incorrect cache, should not be null", manager.getCachedContestStatuses());
    }

    /**
     * <p>
     * Tests <code>getHandlers()</code> method for accuracy.
     * </p>
     */
    public void testGetHandlers() {
        Map<HandlerRegistryInfo, PhaseHandler> handlers = manager.getHandlers();
        assertEquals("Incorrect number of handlers", 3, handlers.size());

        Set<HandlerRegistryInfo> keySet = handlers.keySet();
        HandlerRegistryInfo key = new HandlerRegistryInfo(new PhaseType(1, "Draft"), PhaseOperationEnum.START);
        assertTrue("Incorrect handler registry info-1", keySet.contains(key));
        assertTrue("Incorrect phase handler-1", handlers.get(key) instanceof MockPhaseHandler);

        key = new HandlerRegistryInfo(new PhaseType(2, "Scheduled"), PhaseOperationEnum.END);
        assertTrue("Incorrect handler registry info-2", keySet.contains(key));
        assertTrue("Incorrect phase handler-2", handlers.get(key) instanceof MockPhaseHandler);

        key = new HandlerRegistryInfo(new PhaseType(3, "Active"), PhaseOperationEnum.CANCEL);
        assertTrue("Incorrect handler registry info-3", keySet.contains(key));
        assertTrue("Incorrect phase handler-3", handlers.get(key) instanceof MockPhaseHandler);
    }

    /**
     * <p>
     * Tests <code>setHandlers(Map handlers)</code> method for accuracy.
     * </p>
     */
    public void testSetHandlers() {
        Map<HandlerRegistryInfo, PhaseHandler> newHandlers = new HashMap<HandlerRegistryInfo, PhaseHandler>();
        PhaseType phaseType = new PhaseType(1, "Extended");
        HandlerRegistryInfo info = new HandlerRegistryInfo(phaseType, PhaseOperationEnum.START);
        PhaseHandler phaseHandler = new MockPhaseHandler();
        newHandlers.put(info, phaseHandler);

        // set the new handlers
        manager.setHandlers(newHandlers);

        assertEquals("Incorrect handlers map", newHandlers, manager.getHandlers());
    }
}
