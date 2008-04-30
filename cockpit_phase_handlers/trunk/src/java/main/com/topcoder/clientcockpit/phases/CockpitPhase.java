/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases;

import com.topcoder.util.errorhandling.ExceptionUtils;


/**
 * <p>
 * This is a package scoped class only used in this component to enumerate the phases and corresponding contest
 * status. See RS 1.2.4.
 * </p>
 *
 * <p>
 *   <strong>Thread Safety:</strong>
 *   All the properties are final and thus poses no thread-safety issues.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
class CockpitPhase {

    /**
     * <p>
     * Represents the "Draft" phase.
     * </p>
     */
    static final CockpitPhase DRAFT = new CockpitPhase("Draft", "Draft");

    /**
     * <p>
     * Represents the "Scheduled" phase.
     * </p>
     */
    static final CockpitPhase SCHEDULED = new CockpitPhase("Scheduled", "Scheduled");

    /**
     * <p>
     * Represents the "Active" phase.
     * </p>
     */
    static final CockpitPhase ACTIVE = new CockpitPhase("Active", "Active");

    /**
     * <p>
     * Represents the "Action Required" phase.
     * </p>
     */
    static final CockpitPhase ACTION_REQUIRED = new CockpitPhase("Action Required", "ActionRequired");

    /**
     * <p>
     * Represents the "In Danger" phase.
     * </p>
     */
    static final CockpitPhase IN_DANGER = new CockpitPhase("In Danger", "InDanger");

    /**
     * <p>
     * Represents the "Insufficient Submissions - ReRun Possible" phase.
     * </p>
     */
    static final CockpitPhase INSUFFICIENT_SUBMISSIONS_RE_RUN_POSSIBLE = new CockpitPhase(
        "Insufficient Submissions - ReRun Possible", "InsufficientSubmissionsReRunPossible");

    /**
     * <p>
     * Represents the "Extended" phase.
     * </p>
     */
    static final CockpitPhase EXTENDED = new CockpitPhase("Extended", "Extended");

    /**
     * <p>
     * Represents the "Repost" phase.
     * </p>
     */
    static final CockpitPhase REPOST = new CockpitPhase("Repost", "Repost");

    /**
     * <p>
     * Represents the "Rerun" phase.
     * </p>
     */
    static final CockpitPhase RERUN = new CockpitPhase("Rerun", "Rerun");

    /**
     * <p>
     * Represents the "Insufficient Submissions" phase.
     * </p>
     */
    static final CockpitPhase INSUFFICIENT_SUBMISSIONS = new CockpitPhase(
        "Insufficient Submissions", "InsufficientSubmissions");

    /**
     * <p>
     * Represents the "No Winner Chosen" phase.
     * </p>
     */
    static final CockpitPhase NO_WINNER_CHOSEN = new CockpitPhase("No Winner Chosen", "NoWinnerChosen");

    /**
     * <p>
     * Represents the "Completed" phase.
     * </p>
     */
    static final CockpitPhase COMPLETED = new CockpitPhase("Completed", "Completed");

    /**
     * <p>
     * Represents the "Abandoned" phase.
     * </p>
     */
    static final CockpitPhase ABANDONED = new CockpitPhase("Abandoned", "Abandoned");

    /**
     * <p>
     * Represents the "Cancelled" phase.
     * </p>
     */
    static final CockpitPhase CANCELLED = new CockpitPhase("Cancelled", "Cancelled");

    /**
     * <p>
     * Represents the "Terminated" phase.
     * </p>
     */
    static final CockpitPhase TERMINATED = new CockpitPhase("Terminated", "Terminated");

    /**
     * <p>
     * Represents the type of the phase which matches <code>PhaseType.name</code>.
     * </p>
     */
    private final String phaseType;

    /**
     * <p>
     * Represents corresponding contest status which matches <code>ContestStatus.name</code>.
     * </p>
     */
    private final String contestStatus;

    /**
     * <p>
     * Private constructor to prevent instantiation 'from the outside'. When called will assign the
     * <code>phaseType</code> to <code>contestStatus</code>.
     * </p>
     *
     * @throws IllegalArgumentException if any parameter is null or empty (should never happen though).
     *
     * @param phaseType The type of the phase.
     * @param contestStatus The corresponding contest status.
     */
    private CockpitPhase(String phaseType, String contestStatus) {
        ExceptionUtils.checkNullOrEmpty(phaseType, null, null, "Phase type should be non-null and non-empty.");
        ExceptionUtils.checkNullOrEmpty(contestStatus, null, null, "Contest status should be non-null and non-empty.");
        this.phaseType = phaseType;
        this.contestStatus = contestStatus;
    }

    /**
     * <p>
     * Get the contest status.
     * </p>
     *
     * @return the contest status. Will never be null or empty.
     */
    String getContestStatus() {
        return contestStatus;
    }

    /**
     * <p>
     * Get the phase type.
     * </p>
     *
     * @return the phase type. Will never be null or empty.
     */
    String getPhaseType() {
        return phaseType;
    }
}
