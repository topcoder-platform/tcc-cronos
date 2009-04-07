/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases;

import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.project.phases.Phase;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.web.ejb.forums.Forums;
import com.topcoder.web.ejb.forums.ForumsHome;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Logger;
import java.util.logging.Level;


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
 *        so an email will be sent to notify the start of the phase.</li>
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
 *     PhaseType scheduledType = ...;
 *
 *     //Create handler
 *     ScheduledPhaseHandler handler = new ScheduledPhaseHandler();
 *
 *     //Register handler to start and end operations
 *     manager.registerHandler(handler, scheduledType, PhaseOperationEnum.START);
 *     manager.registerHandler(handler, scheduledType, PhaseOperationEnum.END);
 *
 *     Phase phase = ...;
 *     //Set phase type
 *     phase.setPhaseType(scheduledType);
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
 *
 *     //The phase.project.contest.status is handled externally
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
public class ScheduledPhaseHandler extends AbstractPhaseHandler {


	    /**
     * <p>A <code>Logger</code> to be used for logging the errors encountered while handler performs it's action.</p>
     */
    private static final Logger alertLog = Logger.getLogger("ALERT." + ActivePhaseHandler.class.getName());

    /**
     * <p>
     * The attribute name of contest within project attributes.
     * </p>
     */
    private static final String PROJECT_ATTR_CONTEST = "contest";

	private Random randomizer = new Random(System.currentTimeMillis());

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
    public ScheduledPhaseHandler() throws PhaseHandlingException {
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
    public ScheduledPhaseHandler(ContestManager bean) throws PhaseHandlingException {
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
    public ScheduledPhaseHandler(String namespace) throws PhaseHandlingException {
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
    public ScheduledPhaseHandler(String namespace, ContestManager bean) throws PhaseHandlingException {
        super(namespace, bean);
    }

    /**
     * <p>
     * This method will check if the start or end operations can be applied for the input phase.
     * </p>
     *
     * <p>
     *     <strong>PhaseType:</strong>
     *     If given phase type is not "Scheduled", false is returned.
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
     *     <li>If contest's status is "Draft", then the phase can be started;</li>
     *     <li>If <code>Contest.startDate</code> time has been reached and <code>Contest.endDate</code> time has not
     *     been reached, then the phase can be ended.</li>
     * </ul>
     * </p>
     *
     * <p>
     * The <code>Contest</code> attribute should be non-null and contains non-null <code>status</code>, <code>startDate
     * </code> and <code>endDate</code> properties.
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
     * @return true If the phase can be started or ended.
     *
     * @throws IllegalArgumentException If phase is null.
     * @throws PhaseHandlingException If phase's <code>phaseType</code> or <code>phaseStatus</code> property is null.
     *         Or if its project does not contain a valid <em>contest</em> attribute.
     */
    public boolean canPerform(Phase phase) throws PhaseHandlingException {
        return super.canPerform(phase, CockpitPhase.SCHEDULED);
    }

    /**
     * <p>
     * Override the base method to determine whether the Scheduled phase can start.
     * </p>
     *
     * @param phase The phase to determine whether it can start.
     * @param contest The <code>phase.project.attributes["contest"]</code> attribute.
     *
     * @return true If the phase can start.
     *
     * @throws IllegalArgumentException If given contest is null.
     * @throws PhaseHandlingException If <code>Contest.status</code> is null.
     */
    @Override
    protected boolean canStart(Phase phase, Contest contest) throws PhaseHandlingException {
        //Start case: The Scheduled phase will start as soon as a contest with "Draft" status has been paid for

        return isContestStatusMatch(contest, CockpitPhase.DRAFT);
    }

    /**
     * <p>
     * Override the base method to determine whether the Scheduled phase can end.
     * </p>
     *
     * @param phase The phase to determine whether it can end.
     * @param contest The <code>phase.project.attributes["contest"]</code> attribute.
     *
     * @return true If the phase can end.
     *
     * @throws IllegalArgumentException If given contest is null.
     * @throws PhaseHandlingException If <code>Contest.startDate</code> or <code>Contest.endDate</code> is null.
     */
    @Override
    protected boolean canEnd(Phase phase, Contest contest) throws PhaseHandlingException {

        //End case: if Contest.startDate has been reached and Contest.endDate has not been reached

        return isStartDateReached(contest) && !isEndDateReached(contest);
    }

    /**
     * <p>
     * This method will execute extra logic if the phase was started or ended. The phase must be a "Scheduled" phase.
     * </p>
     *
     * <p>
     * The {@link Phase#getPhaseStatus()} is used to determine whether phase starts or ends:
     *    <ul>
     *        <li>If its status is <code>PhaseStatus.SCHEDULED</code>, it means the phase is about to be started,
     *        so an email will be sent to notify the start of the phase.</li>
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
     * <p>
     *     <strong>Note:</strong>
     *     This method will not update contest status, it's handled externally.
     * </p>
     *
     * @param phase The phase.
     * @param operator The operator(ignored).
     *
     * @throws IllegalArgumentException If phase is null or if the phase is not a Scheduled phase
     *         (<code>phase.phaseType.name</code> is not "Scheduled").
     * @throws PhaseHandlingException If phase's <code>phaseType</code> or <code>phaseStatus</code> property is null.
     *         Or if its project does not contain a valid <em>contest</em> attribute.
     * @throws EmailMessageGenerationException If errors occur while generating the message to be sent.
     * @throws EmailSendingException If errors occur while sending the email message.
     */
    public void perform(Phase phase, String operator) throws PhaseHandlingException {
        validatePhase(phase, CockpitPhase.SCHEDULED.getPhaseType());
		
		// mashannon168 03-30-2009, create forum here
		// copied from ActivePhaseHandler
		Contest contest = getProjectAttribute(phase, Contest.class, PROJECT_ATTR_CONTEST, true);
        if (contest.getForumId() == null) {
            long forumId = createForum(contest.getName(), contest.getCreatedUser());
            if (forumId > 0) {
                contest.setForumId(forumId);
                try {
                    getContestManager().updateContest(contest, this.randomizer.nextInt(), operator, false);
                } catch (ContestManagementException e) {
                    alertLog.log(Level.SEVERE, "Failed to save ID [" + forumId + "] of forum created for contest "
                                               + "[" + contest.getContestId() + "]", e);
                }
            } else {
                alertLog.log(Level.SEVERE, "*** Could not create a forum for contest [" + contest.getName() + "]. " +
                                           "Contest ID: " + contest.getContestId());
            }
        }
		
        this.sendEmail(phase);

        //Just send the email, The contest status update will be handled externally
        //http://forums.topcoder.com/?module=Thread&threadID=611190&start=0
    }


	/**
     * <p>Creates</p>
     *
     * @param name a <code>String</code> providing the name of the forum to be created.
     * @param userId a <code>long</code> providing the ID of a user on whose behalf the forum is to be created.
     * @return a <code>long</code> providing the ID of created forum.
     */
    private long createForum(String name, long userId) {
        try {
			Properties p = new Properties();
			p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			p.put(Context.URL_PKG_PREFIXES,"org.jboss.naming:org.jnp.interfaces");
			p.put(Context.PROVIDER_URL, getForumBeanProviderUrl());

			Context c = new InitialContext(p);
			ForumsHome forumsHome = (ForumsHome) c.lookup(ForumsHome.EJB_REF_NAME);

			Forums forums = forumsHome.create();

			long forumId = forums.createStudioForum(name);
			if (forumId < 0) {
                throw new Exception("createStudioForum returned negative forum ID: " + forumId);
            }
			forums.createForumWatch(userId, forumId);
			return forumId;
		} catch (Exception e) {
            alertLog.log(Level.SEVERE, "*** Could not create a forum for contest [" + name + "]", e);
			return -1;
		}
	}
}
