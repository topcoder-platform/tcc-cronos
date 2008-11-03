/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.accuracytests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.topcoder.clientcockpit.phases.AbandonedPhaseHandler;
import com.topcoder.clientcockpit.phases.ActionRequiredPhaseHandler;
import com.topcoder.clientcockpit.phases.ActivePhaseHandler;
import com.topcoder.clientcockpit.phases.CancelledPhaseHandler;
import com.topcoder.clientcockpit.phases.CompletedPhaseHandler;
import com.topcoder.clientcockpit.phases.DraftPhaseHandler;
import com.topcoder.clientcockpit.phases.ExtendedPhaseHandler;
import com.topcoder.clientcockpit.phases.InDangerPhaseHandler;
import com.topcoder.clientcockpit.phases.InsufficientSubmissionsPhaseHandler;
import com.topcoder.clientcockpit.phases.InsufficientSubmissionsRerunPossiblePhaseHandler;
import com.topcoder.clientcockpit.phases.NoWinnerChosenPhaseHandler;
import com.topcoder.clientcockpit.phases.RePostPhaseHandler;
import com.topcoder.clientcockpit.phases.ScheduledPhaseHandler;
import com.topcoder.clientcockpit.phases.TerminatedPhaseHandler;
import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.date.workdays.Workdays;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * The base test case of Unit test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 * @see TestHelper
 * @see ConfigHelper
 */
public class BaseTestCase extends TestCase {

    /**
     * <p>
     * The milliseconds of one hour.
     * </p>
     */
    public static final long ONE_HOUR = 60 * 60 * 1000;

    /**
     * <p>
     * A <code>Date</code>.
     * </p>
     */
    public static final Date DATE = new Date();

    /**
     * <p>
     * <code>DraftPhaseHandler</code> to test.
     * </p>
     */
    private static DraftPhaseHandler draftPhaseHandler;

    /**
     * <p>
     * <code>ScheduledPhaseHandler</code> to test.
     * </p>
     */
    private static ScheduledPhaseHandler scheduledPhaseHandler;

    /**
     * <p>
     * <code>ActivePhaseHandler</code> to test.
     * </p>
     */
    private static ActivePhaseHandler activePhaseHandler;

    /**
     * <p>
     * <code>ActionRequiredPhaseHandler</code> to test.
     * </p>
     */
    private static ActionRequiredPhaseHandler actionRequiredPhaseHandler;

    /**
     * <p>
     * <code>InDangerPhaseHandler</code> to test.
     * </p>
     */
    private static InDangerPhaseHandler inDangerPhaseHandler;

    /**
     * <p>
     * <code>InsufficientSubmissionsRerunPossiblePhaseHandler</code> to test.
     * </p>
     */
    private static InsufficientSubmissionsRerunPossiblePhaseHandler insufficientSubmissionsRerunPossiblePhaseHandler;

    /**
     * <p>
     * <code>ExtendedPhaseHandler</code> to test.
     * </p>
     */
    private static ExtendedPhaseHandler extendedPhaseHandler;

    /**
     * <p>
     * <code>InsufficientSubmissionsPhaseHandler</code> to test.
     * </p>
     */
    private static InsufficientSubmissionsPhaseHandler insufficientSubmissionsPhaseHandler;

    /**
     * <p>
     * <code>NoWinnerChosenPhaseHandler</code> to test.
     * </p>
     */
    private static NoWinnerChosenPhaseHandler noWinnerChosenPhaseHandler;

    /**
     * <p>
     * <code>RePostPhaseHandler</code> to test.
     * </p>
     */
    private static RePostPhaseHandler rePostPhaseHandler;

    /**
     * <p>
     * <code>TerminatedPhaseHandler</code> to test.
     * </p>
     */
    private static TerminatedPhaseHandler terminatedPhaseHandler;

    /**
     * <p>
     * <code>CancelledPhaseHandler</code> to test.
     * </p>
     */
    private static CancelledPhaseHandler cancelledPhaseHandler;

    /**
     * <p>
     * <code>CompletedPhaseHandler</code> to test.
     * </p>
     */
    private static CompletedPhaseHandler completedPhaseHandler;

    /**
     * <p>
     * <code>AbandonedPhaseHandler</code> to test.
     * </p>
     */
    private static AbandonedPhaseHandler abandonedPhaseHandler;

    /**
     * <p>
     * Set up the test case. Initialize the config manager.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ConfigHelper.initialConfigManager();
    }

    /**
     * <p>
     * Tear down the test case. Clear the config manager.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        ConfigHelper.clearAllNamespace();
        super.tearDown();
    }

    /**
     * <p>
     * Get <code>AbandonedPhaseHandler</code> to test.
     * </p>
     *
     * @return <code>AbandonedPhaseHandler</code>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected AbandonedPhaseHandler getAbandonedPhaseHandler() throws Exception {
        if (abandonedPhaseHandler == null) {
            abandonedPhaseHandler = new AbandonedPhaseHandler();
        }
        return abandonedPhaseHandler;
    }

    /**
     * <p>
     * Get <code>CompletedPhaseHandler</code> to test.
     * </p>
     *
     * @return <code>CompletedPhaseHandler</code>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected CompletedPhaseHandler getCompletedPhaseHandler() throws Exception {
        if (completedPhaseHandler == null) {
            completedPhaseHandler = new CompletedPhaseHandler();
        }
        return completedPhaseHandler;
    }

    /**
     * <p>
     * Get <code>CancelledPhaseHandler</code> to test.
     * </p>
     *
     * @return <code>CancelledPhaseHandler</code>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected CancelledPhaseHandler getCancelledPhaseHandler() throws Exception {
        if (cancelledPhaseHandler == null) {
            cancelledPhaseHandler = new CancelledPhaseHandler();
        }
        return cancelledPhaseHandler;
    }

    /**
     * <p>
     * Get <code>TerminatedPhaseHandler</code> to test.
     * </p>
     *
     * @return <code>TerminatedPhaseHandler</code>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected TerminatedPhaseHandler getTerminatedPhaseHandler() throws Exception {
        if (terminatedPhaseHandler == null) {
            terminatedPhaseHandler = new TerminatedPhaseHandler();
        }
        return terminatedPhaseHandler;
    }

    /**
     * <p>
     * Get <code>RePostPhaseHandler</code> to test.
     * </p>
     *
     * @return <code>RePostPhaseHandler</code>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected RePostPhaseHandler getRePostPhaseHandler() throws Exception {
        if (rePostPhaseHandler == null) {
            rePostPhaseHandler = new RePostPhaseHandler();
        }
        return rePostPhaseHandler;
    }

    /**
     * <p>
     * Get <code>NoWinnerChosenPhaseHandler</code> to test.
     * </p>
     *
     * @return <code>NoWinnerChosenPhaseHandler</code>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected NoWinnerChosenPhaseHandler getNoWinnerChosenPhaseHandler() throws Exception {
        if (noWinnerChosenPhaseHandler == null) {
            noWinnerChosenPhaseHandler = new NoWinnerChosenPhaseHandler();
        }
        return noWinnerChosenPhaseHandler;
    }

    /**
     * <p>
     * Get <code>InsufficientSubmissionsPhaseHandler</code> to test.
     * </p>
     *
     * @return <code>InsufficientSubmissionsPhaseHandler</code>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected InsufficientSubmissionsPhaseHandler getInsufficientSubmissionsPhaseHandler() throws Exception {
        if (insufficientSubmissionsPhaseHandler == null) {
            insufficientSubmissionsPhaseHandler = new InsufficientSubmissionsPhaseHandler();
        }
        return insufficientSubmissionsPhaseHandler;
    }

    /**
     * <p>
     * Get <code>ExtendedPhaseHandler</code> to test.
     * </p>
     *
     * @return <code>ExtendedPhaseHandler</code>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected ExtendedPhaseHandler getExtendedPhaseHandler() throws Exception {
        if (extendedPhaseHandler == null) {
            extendedPhaseHandler = new ExtendedPhaseHandler();
        }
        return extendedPhaseHandler;
    }

    /**
     * <p>
     * Get <code>DraftPhaseHandler</code> to test.
     * </p>
     *
     * @return <code>DraftPhaseHandler</code>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected DraftPhaseHandler getDraftPhaseHandler() throws Exception {
        if (draftPhaseHandler == null) {
            draftPhaseHandler = new DraftPhaseHandler();
        }
        return draftPhaseHandler;
    }

    /**
     * <p>
     * Get <code>ScheduledPhaseHandler</code> to test.
     * </p>
     *
     * @return <code>ScheduledPhaseHandler</code>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected ScheduledPhaseHandler getScheduledPhaseHandler() throws Exception {
        if (scheduledPhaseHandler == null) {
            scheduledPhaseHandler = new ScheduledPhaseHandler();
        }
        return scheduledPhaseHandler;
    }

    /**
     * <p>
     * Get <code>ActivePhaseHandler</code> to test.
     * </p>
     *
     * @return <code>ActivePhaseHandler</code>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected ActivePhaseHandler getActivePhaseHandler() throws Exception {
        if (activePhaseHandler == null) {
            activePhaseHandler = new ActivePhaseHandler();
        }
        return activePhaseHandler;
    }

    /**
     * <p>
     * Get <code>ActionRequiredPhaseHandler</code> to test.
     * </p>
     *
     * @return <code>ActionRequiredPhaseHandler</code>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected ActionRequiredPhaseHandler getActionRequiredPhaseHandler() throws Exception {
        if (actionRequiredPhaseHandler == null) {
            actionRequiredPhaseHandler = new ActionRequiredPhaseHandler();
        }
        return actionRequiredPhaseHandler;
    }

    /**
     * <p>
     * Get <code>InDangerPhaseHandler</code> to test.
     * </p>
     *
     * @return <code>InDangerPhaseHandler</code>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected InDangerPhaseHandler getInDangerPhaseHandler() throws Exception {
        if (inDangerPhaseHandler == null) {
            inDangerPhaseHandler = new InDangerPhaseHandler();
        }
        return inDangerPhaseHandler;
    }

    /**
     * <p>
     * Get <code>InsufficientSubmissionsRerunPossiblePhaseHandler</code> to test.
     * </p>
     *
     * @return <code>InsufficientSubmissionsRerunPossiblePhaseHandler</code>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected InsufficientSubmissionsRerunPossiblePhaseHandler getInsufficientSubmissionsRerunPossiblePhaseHandler()
            throws Exception {
        if (insufficientSubmissionsRerunPossiblePhaseHandler == null) {
            insufficientSubmissionsRerunPossiblePhaseHandler = new InsufficientSubmissionsRerunPossiblePhaseHandler();
        }
        return insufficientSubmissionsRerunPossiblePhaseHandler;
    }

    /**
     * <p>
     * Create a <code>Contest</code>.
     * </p>
     *
     * @param startDate
     *            start date.
     * @param endDate
     *            end date.
     * @param winnerAnnoucementDeadline
     *            winner pickup dead line.
     * @param status
     *            contest status.
     *
     * @return instance of <code>Contest</code> created.
     */
    protected Contest createContest(Date startDate, Date endDate, Date winnerAnnoucementDeadline, ContestStatus status) {
        Contest contest = new Contest();
        contest.setStartDate(startDate);
        contest.setEndDate(endDate);
        contest.setWinnerAnnoucementDeadline(winnerAnnoucementDeadline);
        contest.setStatus(status);
        return contest;
    }

    /**
     * <p>
     * Create a <code>Phase</code>.
     * </p>
     *
     * @param phaseStatus
     *            phase status.
     * @param phaseType
     *            phase type.
     * @param contest
     *            contest.
     *
     * @return instance of <code>Phase</code> created.
     */
    protected Phase createPhase(PhaseStatus phaseStatus, PhaseType phaseType, Contest contest) {
        Phase phase = this.createPhase(phaseStatus, phaseType);
        if (contest != null) {
            phase.getProject().setAttribute("contest", contest);
        }
        return phase;
    }

    /**
     * <p>
     * Create a <code>Phase</code>.
     * </p>
     *
     * @param phaseStatus
     *            phase status.
     * @param phaseType
     *            phase type.
     *
     * @return instance of <code>Phase</code> created.
     */
    protected Phase createPhase(PhaseStatus phaseStatus, PhaseType phaseType) {
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, System.currentTimeMillis());
        project.addPhase(phase);

        if (phaseStatus != null) {
            phase.setPhaseStatus(phaseStatus);
        }
        if (phaseType != null) {
            phase.setPhaseType(phaseType);
        }

        return phase;
    }

    /**
     * <p>
     * Mocks the <code>ContestManager.getAllContestStatuses()</code> and
     * <code>ContestManager.updateContest(Contest)</code>.
     * </p>
     *
     * @param throwError
     *            Indicates whether need raise exception when <code>ContestManager.updateContest(Contest)</code>.
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void mockContestManager(boolean throwError) throws Exception {
        EasyMock.reset(TestHelper.CONTEST_MANAGER);

        EasyMock.expect(TestHelper.CONTEST_MANAGER.getAllContestStatuses()).andStubReturn(
                TestHelper.ALL_CONTEST_STATUSES);

        if (throwError) {
            TestHelper.CONTEST_MANAGER.updateContest(new Contest());
            EasyMock.expectLastCall().andStubThrow(new ContestManagementException("Mock Exception"));
        }

        EasyMock.replay(TestHelper.CONTEST_MANAGER);
    }

    /**
     * <p>
     * Assert the email raw content, include the from address, to addresses and content.
     * </p>
     */
    private void assertEmailRawContent() {

        EmailChecker emailChecker = TestHelper.getEmailChecker();
        assertTrue(emailChecker.getReceived().indexOf("From: address@yahoo.com") > 0);
        assertTrue(emailChecker.getReceived().indexOf("To: smith@topcoder.com, john@topcoder.com") > 0);
        assertTrue(emailChecker.getReceived().indexOf("Date is " + DATE + ", operator is guy, cost is 2$.") > 0);
    }

    /**
     * <p>
     * Assert start email and one hour email have been sent.
     * </p>
     */
    protected void assertStartEmailAndOneHourSent() {
        EmailChecker emailChecker = TestHelper.getEmailChecker();
        assertTrue("Start email should be sent.", emailChecker.isStartEmailSent());
        assertTrue("One hour email should be sent.", emailChecker.isOneHourEmailSent());
        assertFalse("End email should not be sent.", emailChecker.isEndEmailSent());
        assertFalse("Eight hours email should not be sent.", emailChecker.isEightHoursEmailSent());

        this.assertEmailRawContent();
    }

    /**
     * <p>
     * Assert start email and eight hours email have been sent.
     * </p>
     */
    protected void assertStartEmailAndEightHoursSent() {
        EmailChecker emailChecker = TestHelper.getEmailChecker();
        assertTrue("Start email should be sent.", emailChecker.isStartEmailSent());
        assertTrue("Eight hours email should be sent.", emailChecker.isEightHoursEmailSent());
        assertFalse("End email should not be sent.", emailChecker.isEndEmailSent());
        assertFalse("One hour email should not be sent.", emailChecker.isOneHourEmailSent());

        this.assertEmailRawContent();
    }

    /**
     * <p>
     * Assert start email has been sent.
     * </p>
     */
    protected void assertStartEmailSent() {
        EmailChecker emailChecker = TestHelper.getEmailChecker();
        assertTrue("Start email should be sent.", emailChecker.isStartEmailSent());
        assertFalse("End email should not be sent.", emailChecker.isEndEmailSent());
        assertFalse("One hour email should not be sent.", emailChecker.isOneHourEmailSent());
        assertFalse("Eight hours email should not be sent.", emailChecker.isEightHoursEmailSent());

        this.assertEmailRawContent();
    }

    /**
     * <p>
     * Assert end email has been sent.
     * </p>
     */
    protected void assertEndEmailSent() {
        EmailChecker emailChecker = TestHelper.getEmailChecker();
        assertTrue("End email should be sent.", emailChecker.isEndEmailSent());
        assertFalse("Start email should not be sent.", emailChecker.isStartEmailSent());
        assertFalse("One hour email should not be sent.", emailChecker.isOneHourEmailSent());
        assertFalse("Eight hours email should not be sent.", emailChecker.isEightHoursEmailSent());

        this.assertEmailRawContent();
    }

    /**
     * <p>
     * Assert one hour email has been sent.
     * </p>
     */
    protected void assertOneHourEmailSent() {
        EmailChecker emailChecker = TestHelper.getEmailChecker();
        assertTrue("One hour email should be sent.", emailChecker.isOneHourEmailSent());
        assertFalse("Start email should not be sent.", emailChecker.isStartEmailSent());
        assertFalse("End email should not be sent.", emailChecker.isEndEmailSent());
        assertFalse("Eight hours email should not be sent.", emailChecker.isEightHoursEmailSent());

        this.assertEmailRawContent();
    }

    /**
     * <p>
     * Assert start email has been sent.
     * </p>
     */
    protected void assertEightHoursEmailSent() {
        EmailChecker emailChecker = TestHelper.getEmailChecker();
        assertTrue("Eight hours email should be sent.", emailChecker.isEightHoursEmailSent());
        assertFalse("Start email should not be sent.", emailChecker.isStartEmailSent());
        assertFalse("End email should not be sent.", emailChecker.isEndEmailSent());
        assertFalse("One hour email should not be sent.", emailChecker.isOneHourEmailSent());

        this.assertEmailRawContent();
    }

    /**
     * <p>
     * Start SMTP server.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void startSMTPServer() throws Exception {
        TestHelper.getEmailChecker().clear();
        TestHelper.getTestSMTPServer().start();
    }

    /**
     * <p>
     * Stop SMTP server.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void stopSMTPServer() throws Exception {
        TestHelper.getTestSMTPServer().stop();
        TestHelper.getEmailChecker().clear();
    }

    /**
     * <p>
     * Populate attributes.
     * </p>
     *
     * @param phase
     *            to populate attributes.
     * @param contest
     *            contest.
     *
     * @see {@link MockTemplateSource#getTemplate(String)}
     */
    protected void populateAttributes(Phase phase, Contest contest) {
        phase.setAttribute("date", DATE);
        phase.setAttribute("operator", "guy");

        phase.getProject().setAttribute("cost", 2L);

        List<String> toAddresses = new ArrayList<String>();
        toAddresses.add("smith@topcoder.com");
        toAddresses.add("john@topcoder.com");

        phase.getProject().setAttribute("ResourceEmails", (ArrayList<String>) toAddresses);

        phase.getProject().setAttribute("contest", contest);
    }

    /**
     * <p>
     * Remove a property.
     * </p>
     *
     * @param namespace
     *            The namespace to remove property from.
     * @param property
     *            The property name.
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void removeProperty(String namespace, String property) throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.createTemporaryProperties(namespace);
        cm.removeProperty(namespace, property);
        cm.commit(namespace, "TCSDEVELOPER");
    }

    /**
     * <p>
     * Set property value(if property does not exist, it will be created).
     * </p>
     *
     * @param namespace
     *            The namespace to set property into.
     * @param property
     *            The property name.
     * @param value
     *            The property value.
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void setProperty(String namespace, String property, String value) throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.createTemporaryProperties(namespace);
        cm.setProperty(namespace, property, value);
        cm.commit(namespace, "TCSDEVELOPER");
    }

    /**
     * <p>
     * Set property values(if property does not exist, it will be created).
     * </p>
     *
     * @param namespace
     *            The namespace to set property into.
     * @param property
     *            The property name.
     * @param values
     *            The property values.
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void setProperty(String namespace, String property, String[] values) throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.createTemporaryProperties(namespace);
        cm.setProperty(namespace, property, values);
        cm.commit(namespace, "TCSDEVELOPER");
    }
}
