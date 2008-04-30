/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases;

import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
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
 *     <strong>The <code>canPerform()</code> method will send reminder email when the phase is ALREADY started
 *     and CAN NOT be ended:</strong>
 *            <ul>
 *              <li>If the time remains for picking a winner is less than or equals one hour, an one hour
 *              reminder email will be sent;</li>
 *              <li>If the time remains for picking a winner is less than or equals eight hours, an eight hours
 *              reminder email will be sent.</li>
 *            </ul>
 * </p>
 *
 * <p>
 * The {@link Phase#getPhaseStatus()} is also used to determine the desired behavior when <code>perform()</code> method
 * is called:
 *    <ul>
 *        <li>If its status is <code>PhaseStatus.SCHEDULED</code>, it means the phase is about to be started,
 *        the status of the contest will be changed to "InDanger", and an email will be sent to notify the
 *        start of the phase. Also, base on the time remains for picking a winner, the reminder email may be
 *        sent at this point;
 *        </li>
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
 *     PhaseType inDangerType = ...;
 *
 *     //Create handler
 *     InDangerPhaseHandler handler = new InDangerPhaseHandler();
 *
 *     //Register handler to start and end operations
 *     manager.registerHandler(handler, inDangerType, PhaseOperationEnum.START);
 *     manager.registerHandler(handler, inDangerType, PhaseOperationEnum.END);
 *
 *     Phase phase = ...;
 *     //Set phase type
 *     phase.setPhaseType(inDangerType);
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
 *     //The one hour email/eight hours email may be sent to remind the client the time line to pick a winner
 *     //And the phase.project.contest.status has been updated by handler to "InDanger"
 *
 *     //Try to End
 *     if (manager.canEnd(phase)
 *     //handler.canPerform() will be called, but the phase can not be ended at this point,
 *     //the reminder email will be sent again
 *     ) {
 *         manager.end(phase, "TCSDEVELOPER");//This line is not reached
 *     }
 *
 *     //Try to End again
 *     if (manager.canEnd(phase)) {//Now the phase can be ended
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
 *     This class is not completely thread safe because, when the cache is empty, two calls
 *     of perform method, at the same time, may cause unexpected results. Also, two calls of
 *     perform method, at the same time, for the same phase object may also cause unexpected
 *     results.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 * @see AbstractPhaseHandler
 */
public class InDangerPhaseHandler extends AbstractPhaseHandler {

    /**
     * <p>
     * The attribute name of contest within project attributes.
     * </p>
     */
    private static final String PROJECT_ATTR_CONTEST = "contest";

    /**
     * <p>
     * The milliseconds of eight hours.
     * </p>
     */
    private static final long EIGHT_HOURS = 8 * ActionRequiredPhaseHandler.ONE_HOUR;

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
    public InDangerPhaseHandler() throws PhaseHandlingException {
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
    public InDangerPhaseHandler(ContestManager bean) throws PhaseHandlingException {
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
    public InDangerPhaseHandler(String namespace) throws PhaseHandlingException {
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
    public InDangerPhaseHandler(String namespace, ContestManager bean) throws PhaseHandlingException {
        super(namespace, bean);
    }

    /**
     * <p>
     * This method will check if the start or end operations can be applied for the input phase.
     * </p>
     *
     * <p>
     *     <strong>PhaseType:</strong>
     *     If given phase type is not "In Danger", false is returned.
     * </p>
     *
     * <p>
     * The {@link Phase#getPhaseStatus()} is used to determine the intent is to check whether the phase can start
     * or is to check whether the phase can end:
     *    <ul>
     *        <li>If its status is <code>PhaseStatus.SCHEDULED</code>, then this method will check whether the phase
     *        can start;
     *        </li>
     *        <li>If its status is <code>PhaseStatus.OPEN</code>, then this method will check whether the phase
     *        can end;</li>
     *        <li>If its status is <code>PhaseStatus.CLOSED</code>, then this method will return false.</li>
     *    </ul>
     * </p>
     *
     * <p>
     * Whether the phase can start or end is determined by <code>phase.project.attributes["contest"]</code> attribute:
     * <ul>
     *     <li>When a contest has less than 24 hours before the client is supposed to pick a winner, the phase can
     *     start;
     *     </li>
     *     <li>If the client has chosen a winner (<code>Contest.results</code> is not empty), then the phase can end;
     *     </li>
     *     <li>If <code>Contest.winnerAnnouncementDeadline</code> has been reached without a winner chosen, then the
     *     phase can end.
     *     </li>
     * </ul>
     * </p>
     *
     * <p>
     *     <strong>This method will send reminder email when the phase is ALREADY started but CAN NOT be ended:</strong>
     *            <ul>
     *              <li>If the time remains for picking a winner is less than or equals one hour, an one hour
     *              reminder email will be sent;</li>
     *              <li>If the time remains for picking a winner is less than or equals eight hours, an eight hours
     *              reminder email will be sent.</li>
     *            </ul>
     * </p>
     *
     * <p>
     * The <code>phase.project.attributes["ResourceEmails"]</code> attribute is expected to be the list of valid
     * recipient email addresses(according to RFC822).
     * </p>
     *
     * <p>
     * The <code>Contest</code> attribute must be non-null and contains non-null <code>winnerAnnoucementDeadline</code>
     * property.
     * </p>
     *
     * <p>
     * Given a phase, it only makes sense:
     * <ul>
     *     <li>If it is not null;</li>
     *     <li>and its project has a non-null <em>contest</em> attribute(except a Draft phase not yet started);</li>
     *     <li>and it has a non-null <em>phaseType</em> property, such as "Draft", "Active", "Completed", etc...;</li>
     *     <li>and it has a non-null <em>phaseStatus</em> property, such as "Scheduled" or "Open".</li>
     * </ul>
     * </p>
     *
     * @param phase The phase.
     * @return true If the phase can be started or ended.
     *
     * @throws IllegalArgumentException If phase is null.
     *
     * @throws PhaseHandlingException If phase's <code>phaseType</code> or <code>phaseStatus</code> property is null.
     *         Or if its project does not contain a valid <em>contest</em> attribute.
     * @throws EmailMessageGenerationException If errors occur while generating the message to be sent.
     * @throws EmailSendingException If errors occur while sending the email message.
     */
    public boolean canPerform(Phase phase) throws PhaseHandlingException {
        return super.canPerform(phase, CockpitPhase.IN_DANGER);
    }

    /**
     * <p>
     * Override the base method to determine whether the In Danger phase can start.
     * </p>
     *
     * @param phase The phase to determine whether it can start.
     * @param contest The <code>phase.project.attributes["contest"]</code> attribute.
     *
     * @return true If the phase can start.
     *
     * @throws IllegalArgumentException If given contest is null.
     * @throws PhaseHandlingException If <code>Contest.winnerAnnoucementDeadline</code> is null.
     */
    @Override
    protected boolean canStart(Phase phase, Contest contest) throws PhaseHandlingException {

        long remain = computeWinnerAnnouncementDeadlineRemain(contest);

        //Start case: when a contest has less than 24 hours before the client is supposed to pick a winner

        return (remain > 0 && remain <= ActionRequiredPhaseHandler.ONE_DAY)
            && !isWinnerChosen(contest);
    }

    /**
     * <p>
     * Override the base method to determine whether the In Danger phase can end.
     * </p>
     *
     * <p>
     *     <strong>This method may send reminder email if the phase can not be ended:</strong>
     *            <ul>
     *              <li>If the time remains for picking a winner is less than or equals one hour, an one hour
     *              reminder email will be sent;</li>
     *              <li>If the time remains for picking a winner is less than or equals eight hours, an eight hours
     *              reminder email will be sent.</li>
     *            </ul>
     * </p>
     *
     * @param phase The phase to determine whether it can end.
     * @param contest The <code>phase.project.attributes["contest"]</code> attribute.
     *
     * @return true If the phase can end.
     *
     * @throws IllegalArgumentException If given contest is null.
     * @throws PhaseHandlingException If <code>Contest.winnerAnnoucementDeadline</code> is null.
     * @throws EmailMessageGenerationException If errors occur while generating the message to be sent.
     * @throws EmailSendingException If errors occur while sending the email message.
     */
    @Override
    protected boolean canEnd(Phase phase, Contest contest) throws PhaseHandlingException {

        long remain = computeWinnerAnnouncementDeadlineRemain(contest);

        //End case 1: Contest.winnerAnnouncementDeadline has been reached without a winner chosen
        //End case 2: if the client has chosen a winner (Contest.results is not empty)
        boolean canEnd = (remain <= 0 && !isWinnerChosen(contest)) || isWinnerChosen(contest);

        if (!canEnd) {
            //The phase is already started but can not be ended, we need send reminder email again
            this.sendReminderEmail(phase, remain);
        }
        return canEnd;
    }

    /**
     * <p>
     * This method will execute extra logic if the phase was started or ended. The phase must be an "In Danger"
     * phase.
     * </p>
     *
     * <p>
     * The {@link Phase#getPhaseStatus()} is used to determine whether phase starts or ends:
     *    <ul>
     *        <li>If its status is <code>PhaseStatus.SCHEDULED</code>, it means the phase is about to be started,
     *        the status of the contest will be changed to "InDanger", and an email will be sent to notify the
     *        start of the phase;
     *        </li>
     *        <li>If its status is <code>PhaseStatus.OPEN</code>, it means the phase is about to be closed,
     *        so an email will be sent to notify the end of the phase.</li>
     *    </ul>
     * </p>
     *
     * <p>
     *     <strong>Send reminder email:</strong>
     *     When the phase starts, and the time remains for picking a winner is found to be less than one/eight hour(s),
     *     then the reminder email will be sent to remind the user about the time left to time line.
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
     * @throws IllegalArgumentException If phase is null or if the phase is not an In Danger phase
     *         (<code>phase.phaseType.name</code> is not "In Danger"). Or if its project does not contain a valid
     *         <em>contest</em> attribute.
     * @throws PhaseHandlingException If phase's <code>phaseType</code> or <code>phaseStatus</code> property is null.
     *         Or if its project does not contain a valid <em>contest</em> attribute.
     *         Or if errors occur while performing the phase's contest status transition.
     *         Or if <code>Contest.winnerAnnoucementDeadline</code> is null when sending reminder email.
     * @throws EmailMessageGenerationException If errors occur while generating the message to be sent.
     * @throws EmailSendingException If errors occur while sending the email message.
     */
    public void perform(Phase phase, String operator) throws PhaseHandlingException {
        super.perform(phase, CockpitPhase.IN_DANGER);

        String phaseStatus = phase.getPhaseStatus().getName();
        if (PhaseStatus.SCHEDULED.getName().equals(phaseStatus)) {

            //When phase starts, we also send reminder email
            long remain = computeWinnerAnnouncementDeadlineRemain(getProjectAttribute(phase, Contest.class,
                PROJECT_ATTR_CONTEST, true));

            this.sendReminderEmail(phase, remain);
        }
    }

    /**
     * <p>
     * Send the reminder email.
     * </p>
     *
     * @param phase The phase.
     * @param remain The time remains for picking a winner.
     *
     * @throws PhaseHandlingException If phase's <code>phaseType</code>, or <code>phaseStatus</code> property is null,
     *         or the phase's project does not have a valid <em>contest</em> attribute.
     * @throws EmailMessageGenerationException If errors occur while generating the message to be sent.
     * @throws EmailSendingException If errors occur while sending the email message.
     */
    private void sendReminderEmail(Phase phase, long remain) throws PhaseHandlingException {

        if (remain > 0 && remain <= ActionRequiredPhaseHandler.ONE_HOUR) {
            //Send one hour reminder email
            this.sendOneHourReminderEmail(phase);
        } else if (remain > ActionRequiredPhaseHandler.ONE_HOUR && remain <= EIGHT_HOURS) {
            //Send eight hours reminder email
            this.sendEightHoursReminderEmail(phase);
        }
    }
}
