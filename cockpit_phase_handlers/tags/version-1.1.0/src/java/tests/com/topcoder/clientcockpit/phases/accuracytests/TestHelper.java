/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.accuracytests;

import static org.easymock.EasyMock.createNiceMock;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.topcoder.project.phases.PhaseType;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.smtp.server.test.TestSMTPServer;
import com.topcoder.util.config.ConfigManagerException;

/**
 * <p>
 * Helper class used for testing purpose.
 * </p>
 *
 * <p>
 * This class can only be used after the config manager have been initialized.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestHelper {
    /**
     * <p>
     * The attribute name of contest within project attributes.
     * </p>
     */
    public static final String PROJECT_ATTR_CONTEST = "contest";

    /**
     * <p>
     * The attribute name of resource email within project attributes.
     * </p>
     */
    public static final String PROJECT_ATTR_RESOURCE_EMAILS = "ResourceEmails";

    /**
     * <p>
     * The attribute name of minimum submissions within project attributes.
     * </p>
     */
    public static final String PROJECT_ATTR_MINIMUM_SUBMISSIONS = "MinimumSubmissions";

    /**
     * <p>
     * Another namespace configuration for phase handler. See /test_files/PhaseHanlder.xml.
     * </p>
     */
    public static final String ANOTHER_NAMESPACE = "AnotherNamespaceForPhaseHandler";

    /**
     * <p>
     * Represents a mock instance of <code>ContestManager</code> for unit testing.
     * </p>
     */
    public static final ContestManager CONTEST_MANAGER = createNiceMock(ContestManager.class);

    /**
     * <p>
     * The list of all <code>ContestStatus</code>.
     * </p>
     */
    @SuppressWarnings("unchecked")
    public static final List<ContestStatus> ALL_CONTEST_STATUSES = new ArrayList();

    /**
     * <p>
     * The <code>TestSMTPServer</code> to mock the SMTP server.
     * </p>
     */
    private static TestSMTPServer testSMTPServer;

    /**
     * <p>
     * The <code>EmailChecker</code> to check email sent.
     * </p>
     */
    private static EmailChecker emailChecker;

    /**
     * <p>
     * The id of contest status.
     * </p>
     */
    private static long id = 1L;

    /**
     * <p>
     * Enumerate all the CockPit phases.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    public interface CockpitPhaseType {

        /**
         * <p>
         * Represents the "Draft" phase.
         * </p>
         */
        public static final PhaseType DRAFT = new PhaseType(1, "Draft");

        /**
         * <p>
         * Represents the "Scheduled" phase.
         * </p>
         */
        public static final PhaseType SCHEDULED = new PhaseType(2, "Scheduled");

        /**
         * <p>
         * Represents the "Active" phase.
         * </p>
         */
        public static final PhaseType ACTIVE = new PhaseType(3, "Active");

        /**
         * <p>
         * Represents the "Action Required" phase.
         * </p>
         */
        public static final PhaseType ACTION_REQUIRED = new PhaseType(4, "Action Required");

        /**
         * <p>
         * Represents the "In Danger" phase.
         * </p>
         */
        public static final PhaseType IN_DANGER = new PhaseType(5, "In Danger");

        /**
         * <p>
         * Represents the "Insufficient Submissions - ReRun Possible" phase.
         * </p>
         */
        public static final PhaseType INSUFFICIENT_SUBMISSIONS_RE_RUN_POSSIBLE = new PhaseType(6,
                "Insufficient Submissions - ReRun Possible");

        /**
         * <p>
         * Represents the "Extended" phase.
         * </p>
         */
        public static final PhaseType EXTENDED = new PhaseType(7, "Extended");

        /**
         * <p>
         * Represents the "Repost" phase.
         * </p>
         */
        public static final PhaseType REPOST = new PhaseType(8, "Repost");

        /**
         * <p>
         * Represents the "Insufficient Submissions" phase.
         * </p>
         */
        public static final PhaseType INSUFFICIENT_SUBMISSIONS = new PhaseType(9, "Insufficient Submissions");

        /**
         * <p>
         * Represents the "No Winner Chosen" phase.
         * </p>
         */
        public static final PhaseType NO_WINNER_CHOSEN = new PhaseType(10, "No Winner Chosen");

        /**
         * <p>
         * Represents the "Completed" phase.
         * </p>
         */
        public static final PhaseType COMPLETED = new PhaseType(11, "Completed");

        /**
         * <p>
         * Represents the "Abandoned" phase.
         * </p>
         */
        public static final PhaseType ABANDONED = new PhaseType(12, "Abandoned");

        /**
         * <p>
         * Represents the "Cancelled" phase.
         * </p>
         */
        public static final PhaseType CANCELLED = new PhaseType(13, "Cancelled");

        /**
         * <p>
         * Represents the "Terminated" phase.
         * </p>
         */
        public static final PhaseType TERMINATED = new PhaseType(14, "Terminated");

        /**
         * <p>
         * Represents an invalid phase type.
         * </p>
         */
        public static final PhaseType NO_SUCH_PHASE = new PhaseType(15, "NoSuchPhase");

        /**
         * <p>
         * Place holder. No need to be implemented.
         * </p>
         */
        void placeHolder();
    }

    /**
     * <p>
     * Enumerate all the CockPit contest status.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    public interface CockpitContestStatus {

        /**
         * <p>
         * Represents the "Draft" status.
         * </p>
         */
        public static final ContestStatus DRAFT = new ContestStatus();

        /**
         * <p>
         * Represents the "Scheduled" status.
         * </p>
         */
        public static final ContestStatus SCHEDULED = new ContestStatus();

        /**
         * <p>
         * Represents the "Active" status.
         * </p>
         */
        public static final ContestStatus ACTIVE = new ContestStatus();

        /**
         * <p>
         * Represents the "ActionRequired" status.
         * </p>
         */
        public static final ContestStatus ACTION_REQUIRED = new ContestStatus();

        /**
         * <p>
         * Represents the "InDanger" status.
         * </p>
         */
        public static final ContestStatus IN_DANGER = new ContestStatus();

        /**
         * <p>
         * Represents the "InsufficientSubmissionsReRunPossible" status.
         * </p>
         */
        public static final ContestStatus INSUFFICIENT_SUBMISSIONS_RE_RUN_POSSIBLE = new ContestStatus();

        /**
         * <p>
         * Represents the "Extended" status.
         * </p>
         */
        public static final ContestStatus EXTENDED = new ContestStatus();

        /**
         * <p>
         * Represents the "Repost" status.
         * </p>
         */
        public static final ContestStatus REPOST = new ContestStatus();

        /**
         * <p>
         * Represents the "Rerun" status.
         * </p>
         */
        public static final ContestStatus RERUN = new ContestStatus();

        /**
         * <p>
         * Represents the "InsufficientSubmissions" status.
         * </p>
         */
        public static final ContestStatus INSUFFICIENT_SUBMISSIONS = new ContestStatus();

        /**
         * <p>
         * Represents the "NoWinnerChosen" status.
         * </p>
         */
        public static final ContestStatus NO_WINNER_CHOSEN = new ContestStatus();

        /**
         * <p>
         * Represents the "Completed" status.
         * </p>
         */
        public static final ContestStatus COMPLETED = new ContestStatus();

        /**
         * <p>
         * Represents the "Abandoned" status.
         * </p>
         */
        public static final ContestStatus ABANDONED = new ContestStatus();

        /**
         * <p>
         * Represents the "Cancelled" status.
         * </p>
         */
        public static final ContestStatus CANCELLED = new ContestStatus();

        /**
         * <p>
         * Represents the "Terminated" status.
         * </p>
         */
        public static final ContestStatus TERMINATED = new ContestStatus();

        /**
         * <p>
         * Place holder. No need to be implemented.
         * </p>
         */
        void placeHolder();
    }

    static {

        // Initialize the all the ContestStatuses

        CockpitContestStatus.DRAFT.setContestStatusId(id++);
        CockpitContestStatus.DRAFT.setName("Draft");

        CockpitContestStatus.SCHEDULED.setContestStatusId(id++);
        CockpitContestStatus.SCHEDULED.setName("Scheduled");

        CockpitContestStatus.ACTIVE.setContestStatusId(id++);
        CockpitContestStatus.ACTIVE.setName("Active");

        CockpitContestStatus.ACTION_REQUIRED.setContestStatusId(id++);
        CockpitContestStatus.ACTION_REQUIRED.setName("ActionRequired");

        CockpitContestStatus.IN_DANGER.setContestStatusId(id++);
        CockpitContestStatus.IN_DANGER.setName("InDanger");

        CockpitContestStatus.INSUFFICIENT_SUBMISSIONS_RE_RUN_POSSIBLE.setContestStatusId(id++);
        CockpitContestStatus.INSUFFICIENT_SUBMISSIONS_RE_RUN_POSSIBLE.setName("InsufficientSubmissionsReRunPossible");

        CockpitContestStatus.EXTENDED.setContestStatusId(id++);
        CockpitContestStatus.EXTENDED.setName("Extended");

        CockpitContestStatus.REPOST.setContestStatusId(id++);
        CockpitContestStatus.REPOST.setName("Repost");

        CockpitContestStatus.RERUN.setContestStatusId(id++);
        CockpitContestStatus.RERUN.setName("Rerun");

        CockpitContestStatus.INSUFFICIENT_SUBMISSIONS.setContestStatusId(id++);
        CockpitContestStatus.INSUFFICIENT_SUBMISSIONS.setName("InsufficientSubmissions");

        CockpitContestStatus.NO_WINNER_CHOSEN.setContestStatusId(id++);
        CockpitContestStatus.NO_WINNER_CHOSEN.setName("NoWinnerChosen");

        CockpitContestStatus.COMPLETED.setContestStatusId(id++);
        CockpitContestStatus.COMPLETED.setName("Completed");

        CockpitContestStatus.ABANDONED.setContestStatusId(id++);
        CockpitContestStatus.ABANDONED.setName("Abandoned");

        CockpitContestStatus.CANCELLED.setContestStatusId(id++);
        CockpitContestStatus.CANCELLED.setName("Cancelled");

        CockpitContestStatus.TERMINATED.setContestStatusId(id++);
        CockpitContestStatus.TERMINATED.setName("Terminated");

    }

    static {

        try {
            for (Field field : CockpitContestStatus.class.getFields()) {
                ALL_CONTEST_STATUSES.add((ContestStatus) field.get(null));
            }
        } catch (IllegalAccessException e) {
            // should not happen
            throw new RuntimeException(e);
        }

        // Create TestSMTPServer
        try {
            testSMTPServer = new TestSMTPServer();
            emailChecker = new EmailChecker(testSMTPServer.getCommandHandler());
            testSMTPServer.setCommandHandler(emailChecker);
        } catch (ConfigManagerException e) {
            throw new RuntimeException("Error while configure TestSMTPServer.", e);
        }
    }

    /**
     * <p>
     * Private empty constructor.
     * </p>
     */
    private TestHelper() {
        // empty
    }

    /**
     * <p>
     * Get the <code>TestSMTPServer</code>.
     * </p>
     *
     * @return <code>TestSMTPServer</code>.
     */
    public static TestSMTPServer getTestSMTPServer() {
        return testSMTPServer;
    }

    /**
     * <p>
     * Get the <code>EmailChecker</code>.
     * </p>
     *
     * @return <code>EmailChecker</code>.
     */
    public static EmailChecker getEmailChecker() {
        return emailChecker;
    }
}
