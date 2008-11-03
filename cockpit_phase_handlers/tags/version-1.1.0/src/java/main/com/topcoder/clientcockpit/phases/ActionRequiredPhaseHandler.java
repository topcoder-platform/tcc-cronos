/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases;

import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.project.phases.Phase;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestManager;


/**
 * <p>
 * This phase handler will be used by the <code>PhaseManager</code> implementations.
 * </p>
 *
 * <p>
 * The {@link Phase#getPhaseStatus()} is used to determine the intent of <code>canPerform()</code> method is to
 * check whether the phase can start or is to check whether the phase can end:
 *    <ul>
 *        <li>If its status is <code>PhaseStatus.SCHEDULED</code>, then <code>canPerform()</code> method will check
 *        whether the phase can start;
 *        </li>
 *        <li>If its status is <code>PhaseStatus.OPEN</code>, then <code>canPerform()</code> method will check
 *        whether the phase can end;</li>
 *        <li>If its status is <code>PhaseStatus.CLOSED</code>, then <code>canPerform()</code> method will return false.
 *        </li>
 *    </ul>
 * </p>
 *
 * <p>
 * The {@link Phase#getPhaseStatus()} is also used to determine the desired behavior when <code>perform()</code> method
 * is called:
 *    <ul>
 *        <li>If its status is <code>PhaseStatus.SCHEDULED</code>, it means the phase is about to be started,
 *        so an email will be sent to notify the start of the phase. And the status of the contest will be
 *        changed to "ActionRequired".</li>
 *        <li>If its status is <code>PhaseStatus.OPEN</code>, it means the phase is about to be closed,
 *        so an email will be sent to notify the end of the phase.</li>
 *    </ul>
 * </p>
 *
 * <p>
 *     <strong>Sample Usage:</strong>
 *     <pre>
 *     PhaseManager manager = ...;
 *
 *     PhaseType actionRequiredType = ...;
 *
 *     //Create handler
 *     ActionRequiredPhaseHandler handler = new ActionRequiredPhaseHandler();
 *
 *     //Register handler to start and end operations
 *     manager.registerHandler(handler, actionRequiredType, PhaseOperationEnum.START);
 *     manager.registerHandler(handler, actionRequiredType, PhaseOperationEnum.END);
 *
 *     Phase phase = ...;
 *     //Set phase type
 *     phase.setPhaseType(actionRequiredType);
 *
 *     //Initial the phase status to SCHEDULED
 *     phase.setPhaseStatus(PhaseStatus.SCHEDULED);
 *
 *     //Start
 *     if (manager.canStart(phase)) {//handler.canPerform() will be called
 *         manager.start(phase, "ivern");//handler.perform() will be called
 *     }
 *
 *     //Now the phase status has been changed to by manager to be OPEN
 *     //And the email is sent by handler to notify the start of phase
 *     //And the phase.project.contest.status has been updated by handler to "ActionRequired"
 *
 *     //End
 *     if (manager.canEnd(phase)) {//handler.canPerform() will be called
 *         manager.end(phase, "TCSDEVELOPER");//handler.perform() will be called
 *     }
 *
 *     //Now the phase status has been changed by manager to be END
 *     //And the email is sent by handler to notify the end of phase.
 *     </pre>
 * </p>
 *
 * <p>
 *     <strong>Thread Safety:</strong>
 *     This class is not completely thread safe because, when the cache is empty, two calls of
 *     perform method, at the same time, may cause unexpected results. Also, two calls of perform
 *     method, at the same time, for the same phase object may also cause unexpected results.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 * @see AbstractPhaseHandler
 */
public class ActionRequiredPhaseHandler extends AbstractPhaseHandler {

    /**
     * <p>
     * The milliseconds of one hour.
     * </p>
     */
    public static final long ONE_HOUR = 60 * 60 * 1000;

    /**
     * <p>
     * The milliseconds of one day.
     * </p>
     */
    public static final long ONE_DAY = 24 * ONE_HOUR;

    /**
     * <p>
     * Creates a new instance. The {@link AbstractPhaseHandler#DEFAULT_NAMESPACE} is used as the namespace to
     * load properties.
     * </p>
     *
     * <p>
     * The <code>ContestManager</code> will be retrieved by JNDI using <code>bean_name</code> property.
     * </p>
     *
     * @throws PhaseHandlerConfigurationException If error while loading properties, or any required property
     *         is missing, or if error while creating <code>EmailMessageGenerator</code> by <code>ObjectFactory</code>.
     * @throws PhaseHandlingException If error while looking up <code>ContestManager</code> by JNDI.
     */
    public ActionRequiredPhaseHandler() throws PhaseHandlingException {
        super();
    }

    /**
     * <p>
     * Creates a new instance. The {@link AbstractPhaseHandler#DEFAULT_NAMESPACE} is used as the namespace to
     * load properties.
     * </p>
     *
     * <p>
     * The given <code>ContestManager</code> will be used, and the <code>bean_name</code> property is ignored and
     * JNDI looking up will not be performed.
     * </p>
     *
     * @param bean Non-null instance of <code>ContestManager</code> used to work with contests.
     *
     * @throws IllegalArgumentException If given <code>ContestManager</code> is null.
     * @throws PhaseHandlerConfigurationException If error while loading properties, or any required property
     *         is missing, or if error while creating <code>EmailMessageGenerator</code> by <code>ObjectFactory</code>.
     *
     */
    public ActionRequiredPhaseHandler(ContestManager bean) throws PhaseHandlingException {
        super(bean);
    }

    /**
     * <p>
     * Creates a new instance. The given namespace is used to load properties.
     * </p>
     *
     * <p>
     * The <code>ContestManager</code> will be retrieved by JNDI using <code>bean_name</code> property.
     * </p>
     *
     * @param namespace Non-null and non-empty(trimmed) namespace to load properties.
     *
     * @throws IllegalArgumentException If given namespace is null or empty(trimmed).
     * @throws PhaseHandlerConfigurationException If error while loading properties, or any required property
     *         is missing, or if error while creating <code>EmailMessageGenerator</code> by <code>ObjectFactory</code>.
     * @throws PhaseHandlingException If error while looking up <code>ContestManager</code> by JNDI.
     */
    public ActionRequiredPhaseHandler(String namespace) throws PhaseHandlingException {
        super(namespace);
    }

    /**
     * <p>
     * Creates a new instance. The given namespace is used to load properties.
     * </p>
     *
     * <p>
     * The given <code>ContestManager</code> will be used, and the <code>bean_name</code> property is ignored and
     * JNDI looking up will not be performed.
     * </p>
     *
     * @param namespace Non-null and non-empty(trimmed) namespace to load properties.
     * @param bean Non-null instance of <code>ContestManager</code> used to work with contests.
     *
     * @throws IllegalArgumentException If given namespace is null or empty(trimmed).
     *         Or if given <code>ContestManager</code> is null.
     * @throws PhaseHandlerConfigurationException If error while loading properties, or any required property
     *         is missing, or if error while creating <code>EmailMessageGenerator</code> by <code>ObjectFactory</code>.
     *
     */
    public ActionRequiredPhaseHandler(String namespace, ContestManager bean) throws PhaseHandlingException {
        super(namespace, bean);
    }

    /**
     * <p>
     * This method will check if the start or end operations can be applied for the input phase.
     * </p>
     *
     * <p>
     *     <strong>PhaseType:</strong>
     *     If given phase type is not "Action Required", false is returned.
     * </p>
     *
     * <p>
     * The {@link Phase#getPhaseStatus()} is used to determine the intent is to
     * check whether the phase can start or is to check whether the phase can end:
     *    <ul>
     *        <li>If its status is <code>PhaseStatus.SCHEDULED</code>, then this method will check
     *        whether the phase can start;
     *        </li>
     *        <li>If its status is <code>PhaseStatus.OPEN</code>, then this method will check
     *        whether the phase can end;</li>
     *        <li>If its status is <code>PhaseStatus.CLOSED</code>, then this method will return false.</li>
     *    </ul>
     * </p>
     *
     * <p>
     * Whether the phase can start or end is determined by <code>phase.project.attributes["contest"]</code> attribute:
     * <ul>
     *     <li>If contest ends and the minimum number of submissions has been met, the phase can start;
     *     </li>
     *     <li>If the client has chosen a winner (<code>Contest.results</code> is not empty), then the phase can end;
     *     </li>
     *     <li>When less than 24 hours remains before the <code>Contest.winnerAnnouncementDeadline</code>, then the
     *     phase can end.
     *     </li>
     * </ul>
     * </p>
     *
     * <p>
     * The <code>Contest</code> attribute must be non-null and contains non-null <code>endDate</code> and
     * <code>winnerAnnoucementDeadline</code> properties.
     * </p>
     *
     * <p>
     * In practice, given a phase, it only makes sense:
     * <ul>
     *     <li>If it is not null;</li>
     *     <li>and its project has a non-null <em>contest</em> attribute(except a Draft phase not yet started);</li>
     *     <li>and it has a non-null <em>phaseType</em> property, such as "Draft", "Active", "Completed", etc...;</li>
     *     <li>and it has a non-null <em>phaseStatus</em> property, such as "Scheduled" or "Open".</li>
     * </ul>
     * </p>
     *
     * @param phase The phase.
     *
     * @throws IllegalArgumentException If phase is null.
     * @throws PhaseHandlingException If phase's <code>phaseType</code> or <code>phaseStatus</code> property is null.
     *         Or if its project does not contain a valid <em>contest</em> attribute. Or if its project does not contain
     *         a valid <em>MinimumSubmissions</em> attribute(which should be a non-null <code>Number</code>).
     *
     * @return true If the phase can be started or ended.
     */
    public boolean canPerform(Phase phase) throws PhaseHandlingException {
        return super.canPerform(phase, CockpitPhase.ACTION_REQUIRED);
    }

    /**
     * <p>
     * Override the base method to determine whether the Action Required phase can start.
     * </p>
     *
     * @param phase The phase to determine whether it can start.
     * @param contest The <code>phase.project.attributes["contest"]</code> attribute.
     *
     * @return true If the phase can start.
     *
     * @throws IllegalArgumentException If given phase or contest is null.
     * @throws PhaseHandlingException If <code>Contest.endDate</code> is null, or if project does not contain
     *         a valid <em>MinimumSubmissions</em> attribute(which should be a non-null <code>Number</code>).
     */
    @Override
    protected boolean canStart(Phase phase, Contest contest) throws PhaseHandlingException {

        //Start case: The "Action Required" phase will start as soon as a contest ends and the minimum number of
        //submissions has been met

        return isEndDateReached(contest) && isEnoughSubmissionsReceived(phase, contest);
    }

    /**
     * <p>
     * Override the base method to determine whether the Action Required phase can end.
     * </p>
     *
     * @param phase The phase to determine whether it can end.
     * @param contest The <code>phase.project.attributes["contest"]</code> attribute.
     *
     * @return true If the phase can end.
     *
     * @throws IllegalArgumentException If given contest is null.
     * @throws PhaseHandlingException If <code>Contest.winnerAnnoucementDeadline</code> is null.
     */
    @Override
    protected boolean canEnd(Phase phase, Contest contest) throws PhaseHandlingException {

        long remain = computeWinnerAnnouncementDeadlineRemain(contest);

        //End case 1: When less than 24 hours remains before the Contest.winnerAnnouncementDeadline
        if (remain > 0 && remain <= ONE_DAY) {
            return true;
        }

        //End case 2: if the client has chosen a winner (Contest.results is not empty)
        return isWinnerChosen(contest);
    }

    /**
     * <p>
     * This method will execute extra logic if the phase was started or ended. The phase must be an "Action Required"
     * phase.
     * </p>
     *
     * <p>
     * The {@link Phase#getPhaseStatus()} is used to determine whether phase starts or ends:
     *    <ul>
     *        <li>If its status is <code>PhaseStatus.SCHEDULED</code>, it means the phase is about to be started,
     *        so an email will be sent to notify the start of the phase. And the status of the contest will be
     *        changed to "ActionRequired".</li>
     *        <li>If its status is <code>PhaseStatus.OPEN</code>, it means the phase is about to be closed,
     *        so an email will be sent to notify the end of the phase.</li>
     *    </ul>
     * </p>
     *
     * <p>
     * The <code>phase.project.attributes["ResourceEmails"]</code> attribute is expected to be the list of valid
     * recipient email addresses(according to RFC822).
     * </p>
     *
     * <p>
     * In practice, given a phase, it only makes sense:
     * <ul>
     *     <li>If it is not null;</li>
     *     <li>and its project has a non-null <em>contest</em> attribute(except a Draft phase not yet started);</li>
     *     <li>and it has a non-null <em>phaseType</em> property, such as "Draft", "Active", "Completed", etc...;</li>
     *     <li>and it has a non-null <em>phaseStatus</em> property, such as "Scheduled" or "Open".</li>
     * </ul>
     * </p>
     *
     * @param phase The phase.
     * @param operator The operator(ignored).
     *
     * @throws IllegalArgumentException If phase is null or if the phase is not an Action Required phase
     *         (<code>phase.phaseType.name</code> is not "Action Required").
     * @throws PhaseHandlingException If phase's <code>phaseType</code> or <code>phaseStatus</code> property is null.
     *         Or if its project does not contain a valid <em>contest</em> attribute.
     *         Or if errors occur while performing the phase's contest status transition.
     * @throws PhaseHandlingException If errors occur while performing the phase's contest status transition.
     * @throws EmailMessageGenerationException If errors occur while generating the message to be sent.
     * @throws EmailSendingException If errors occur while sending the email message.
     */
    public void perform(Phase phase, String operator) throws PhaseHandlingException {
        super.perform(phase, CockpitPhase.ACTION_REQUIRED);
    }
}
