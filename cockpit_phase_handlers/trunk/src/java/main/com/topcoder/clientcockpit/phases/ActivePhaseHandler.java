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

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


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
 *        changed to "Active".</li>
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
 *     PhaseType activeType = ...;
 *
 *     //Create handler
 *     ActivePhaseHandler handler = new ActivePhaseHandler();
 *
 *     //Register handler to start and end operations
 *     manager.registerHandler(handler, activeType, PhaseOperationEnum.START);
 *     manager.registerHandler(handler, activeType, PhaseOperationEnum.END);
 *
 *     Phase phase = ...;
 *     //Set phase type
 *     phase.setPhaseType(activeType);
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
 *     //And the phase.project.contest.status has been updated by handler to "Active"
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
public class ActivePhaseHandler extends AbstractPhaseHandler {

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

    private static final long ONE_DAY = 24*60*60*1000;

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
    public ActivePhaseHandler() throws PhaseHandlingException {
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
    public ActivePhaseHandler(ContestManager bean) throws PhaseHandlingException {
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
    public ActivePhaseHandler(String namespace) throws PhaseHandlingException {
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
    public ActivePhaseHandler(String namespace, ContestManager bean) throws PhaseHandlingException {
        super(namespace, bean);
    }

    /**
     * <p>
     * This method will check if the start or end operations can be applied for the input phase.
     * </p>
     *
     * <p>
     *     <strong>PhaseType:</strong>
     *     If given phase type is not "Active", false is returned.
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
     *     <li>If contest's status is "Scheduled" and has its <em>startDate</em> reached, then the phase can be started;
     *     </li>
     *     <li>If <code>Contest.endDate</code> time has been reached, then the phase can be ended.</li>
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
        return super.canPerform(phase, CockpitPhase.ACTIVE);
    }

    /**
     * <p>
     * Override the base method to determine whether the Active phase can start.
     * </p>
     *
     * @param phase The phase to determine whether it can start.
     * @param contest The <code>phase.project.attributes["contest"]</code> attribute.
     *
     * @return true If the phase can start.
     *
     * @throws IllegalArgumentException If given contest is null.
     * @throws PhaseHandlingException If <code>Contest.status</code> or <code>Contest.startDate</code> is null.
     */
    @Override
    protected boolean canStart(Phase phase, Contest contest) throws PhaseHandlingException {

        //Start case: The Active phase can start as soon as a contest with "Scheduled" status has its startDate property
        //time reached.
        return isStartDateReached(contest) && isContestStatusMatch(contest, CockpitPhase.SCHEDULED);
    }

    /**
     * <p>
     * Override the base method to determine whether the Active phase can end.
     * </p>
     *
     * @param phase The phase to determine whether it can end.
     * @param contest The <code>phase.project.attributes["contest"]</code> attribute.
     *
     * @return true If the phase can end.
     *
     * @throws IllegalArgumentException If given contest is null.
     * @throws PhaseHandlingException If <code>Contest.endDate</code> is null.
     */
    @Override
    protected boolean canEnd(Phase phase, Contest contest) throws PhaseHandlingException {

        //End case: When the Contest.endDate has been reached
        return isEndDateReached(contest);
    }

    /**
     * <p>
     * This method will execute extra logic if the phase was started or ended. The phase must be an "Active" phase.
     * </p>
     *
     * <p>
     * The {@link Phase#getPhaseStatus()} is used to determine whether phase starts or ends:
     *    <ul>
     *        <li>If its status is <code>PhaseStatus.SCHEDULED</code>, it means the phase is about to be started,
     *        so an email will be sent to notify the start of the phase. And the status of the contest will be
     *        changed to "Active".</li>
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
     * @throws IllegalArgumentException If phase is null or if the phase is not an Active phase
     *         (<code>phase.phaseType.name</code> is not "Active").
     * @throws PhaseHandlingException If phase's <code>phaseType</code> or <code>phaseStatus</code> property is null.
     *         Or if its project does not contain a valid <em>contest</em> attribute.
     *         Or if errors occur while performing the phase's contest status transition.
     * @throws EmailMessageGenerationException If errors occur while generating the message to be sent.
     * @throws EmailSendingException If errors occur while sending the email message.
     */
    public void perform(Phase phase, String operator) throws PhaseHandlingException {
        
        Contest contest = getProjectAttribute(phase, Contest.class, PROJECT_ATTR_CONTEST, true);
        if (contest.getForumId() == null) {
            long forumId = createForum(contest.getName(), contest.getCreatedUser());
            if (forumId > 0) {
                contest.setForumId(forumId);
				phase.getProject().setAttribute("ContestForumID", String.valueOf(forumId));
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

		super.perform(phase, CockpitPhase.ACTIVE);
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


    /**
     * check if spec review is passed
     *
     * @param contest contest
     * @return boolean whether spec review passed
     */
    private boolean passedSpecReview(Contest contest) throws PhaseHandlingException
    {

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try
        {
            conn = createConnection();
            
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("select 'passed' from spec_review  ");
            queryBuffer.append(" where contest_id = ").append(contest.getContestId()).append(" and is_studio = 1 and review_status_type_id = 1 ");

            preparedStatement = conn.prepareStatement(queryBuffer.toString());
         
            // execute the query and build the result into a list
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                return true;
            }
            alertLog.log(Level.INFO, "Spec Review is not completed for "+contest.getContestId());

            // spec review not passed, push dates 24 hrs
            if (isStartDateReached(contest) && !isEndDateReached(contest))
            {
                contest.getStartDate().setTime(contest.getStartDate().getTime() + ONE_DAY);
                contest.getEndDate().setTime(contest.getEndDate().getTime() + ONE_DAY);
                contest.getWinnerAnnoucementDeadline().setTime(contest.getWinnerAnnoucementDeadline().getTime() + ONE_DAY);

                try {
                    getContestManager().updateContest(contest, this.randomizer.nextInt(), "AutoPilot", false);
                } catch (ContestManagementException e) {
                    alertLog.log(Level.SEVERE, "Failed to update timeline for contest " + "[" + contest.getContestId() + "]", e);
                }
            }
            
            return false;
        
        } catch (SQLException e) {
            throw new PhaseHandlingException(
                    "Error occurs while executing query ");
        } 
        finally {
            closeResultSet(resultSet);
            closeStatement(preparedStatement);
            if (conn != null) {
                closeConnection(conn);
            }
        }
        
    }

    
}
