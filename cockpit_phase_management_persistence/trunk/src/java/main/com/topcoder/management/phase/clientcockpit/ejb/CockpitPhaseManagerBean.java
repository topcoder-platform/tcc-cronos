/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit.ejb;

import com.topcoder.management.phase.PhaseOperationEnum;
import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.management.phase.HandlerRegistryInfo;
import com.topcoder.management.phase.PhaseValidator;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.phase.clientcockpit.CockpitPhaseManagementException;
import com.topcoder.management.phase.clientcockpit.CockpitPhaseManagerInterface;
import com.topcoder.project.phases.Project;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.Phase;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestProperty;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.service.studio.contest.ContestManagerLocal;
import com.topcoder.service.studio.contest.EntityNotFoundException;
import com.topcoder.service.studio.submission.Submission;
import com.topcoder.service.studio.submission.ContestResult;
import com.topcoder.date.workdays.Workdays;
import com.topcoder.date.workdays.DefaultWorkdaysFactory;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.cache.Cache;
import com.topcoder.user.manager.UserPersistence;
import com.topcoder.user.manager.User;
import com.topcoder.user.manager.UserPersistenceException;
import com.topcoder.cockpit.security.CockpitUserPersistenceImpl;

import javax.annotation.Resource;
import javax.annotation.PostConstruct;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.NameNotFoundException;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.lang.reflect.Field;
import java.io.Serializable;

/**
 * <p></p>
 * RunAs("Cockpit Administrator")
 * RolesAllowed("Cockpit User")
 * DeclareRoles( { "Cockpit User", "Cockpit Administrator" })
 * @author isv
 * @version 1.0
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Stateless
public class CockpitPhaseManagerBean implements CockpitPhaseManagerInterface {
    /**
     * <p>
     * Represents default phase type name for Completed phase.
     * </p>
     */
    private static final String COMPLETED = "Completed";

    /**
     * <p>
     * Represents default phase type name for No Winner Chosen phase.
     * </p>
     */
    private static final String NO_WINNER_CHOSEN = "No Winner Chosen";

    /**
     * <p>
     * Represents default phase type name for Insufficient Submissions phase.
     * </p>
     */
    private static final String INSUFFICIENT_SUBMISSIONS = "Insufficient Submissions";

    /**
     * <p>
     * Represents default phase type name for Repost phase.
     * </p>
     */
    private static final String REPOST = "No Submissions - Repost";

    /**
     * <p>
     * Represents default phase type name for Extended phase.
     * </p>
     */
    private static final String EXTENDED = "Extended";

    /**
     * <p>
     * Represents default phase type name for Insufficient Submissions - ReRun Possible phase.
     * </p>
     */
    private static final String INSUFFICIENT_SUBMISSIONS_RE_RUN_POSSIBLE = "Insufficient Submissions - ReRun Possible";

    /**
     * <p>
     * Represents default phase type name for In Danger phase.
     * </p>
     */
    private static final String IN_DANGER = "In Danger";

    /**
     * <p>
     * Represents default phase type name for Action Required phase.
     * </p>
     */
    private static final String ACTION_REQUIRED = "Action Required";

    /**
     * <p>
     * Represents default phase type name for Active Required phase.
     * </p>
     */
    private static final String ACTIVE = "Active";

    /**
     * <p>
     * Represents default phase type name for Scheduled phase.
     * </p>
     */
    private static final String SCHEDULED = "Scheduled";

    /**
     * <p>
     * Represents default phase type name for Draft phase.
     * </p>
     */
    private static final String DRAFT = "Draft";

    /**
     * <p>
     * Represents default phase type name for Abandoned phase.
     * </p>
     */
    private static final String ABANDONED = "Abandoned";

    /**
     * <p>
     * Represents default phase type name for Cancelled phase.
     * </p>
     */
    private static final String CANCELLED = "Cancelled";

    /**
     * <p>
     * Represents default phase type name for Cancelled phase.
     * </p>
     */
    private static final String TERMINATED = "Terminated";

    /**
     * <p>
     * Mapping from HandlerRegistryInfo keys to PhaseHandler values.
     * </p>
     * <p>
     * This is used to look up phase handlers when performing operations. It is not mutable and and its value
     * is an empty map. This map is filled in the constructor with the phase handlers and it is modified in
     * the register and unregister method or in the setter. The keys and the values can not be null.
     * </p>
     */
//    private final Map<HandlerRegistryInfo, PhaseHandler> handlers = new HashMap<HandlerRegistryInfo, PhaseHandler>();

    /**
     * <p>
     * Mapping from ContestStatus name keys to PhaseType name values.
     * </p>
     * <p>
     * This is used to look up mapped phase type name when mapping the ContestStatus to a Phase operation. It
     * is not mutable and and its value is an empty map. This map is filled in the constructor with the
     * mapping specified in class documentation. The keys and the values can not be null.
     * </p>
     */
    private final Map<String, String[]> statusMapping = new HashMap<String, String[]>();

    /**
     * <p>
     * Represents the ContestManager adapted by this phase manager. All methods delegate the work to the
     * methods of this instance.
     * </p>
     * <p>
     * It is initialized to null. And can be set in the setter or defined in constructor. It is accessed and
     * modified in corresponding getter and setter. It can not be null.
     * </p>
     */
    @EJB
    private ContestManagerLocal contestManager;

    /**
     * <p>
     * Represents the log used to log the operations.
     * </p>
     * <p>
     * It is initialized to null. Or it can be defined in constructor. It is accessed and modified in
     * corresponding getter and setter. It can be null.
     * </p>
     */
    private Log log;

    /**
     * <p>
     * Represents the cache used to cache the contest statuses. This cache will contains only one object with
     * the key "contestStatuses".
     * </p>
     * <p>
     * Only one Object is used because all contest statuses are loaded in a single operation. This object is a
     * List of ContestStatus instances, they are all contest status instances from the contest manager.
     * </p>
     * <p>
     * These instances are loaded in lazy mode: if a method needs these statuses and they are null then they
     * are loaded in the cacheContestStatuses. This cache is optional, therefore if it's null then it is not
     * used.
     * </p>
     * <p>
     * It is initialized to null or it can be defined in constructor. It is accessed and modified in
     * corresponding getter and setter. It is utilized in methods which need the contest statuses. It can be
     * null, it's optional.
     * </p>
     */
//    private Cache cachedContestStatuses;

    /**
     * <p>
     * This is the phase type name for the Draft PhaseType/ContestStatus. It is used to map the contest status
     * to the phase type.
     * </p>
     * <p>
     * It is defined at beginning and it doesn't change. It is configurable. It can't be null or empty. Since
     * this fields is final, the initial value is only the default value if the field is not configured in the
     * constructor.
     * </p>
     */
    private final String draftPhaseTypeName;

    /**
     * <P>
     * This is the phase type name for the Scheduled PhaseType/ContestStatus. It is used to map the contest
     * status to the phase type.
     * </p>
     * <p>
     * It is defined at beginning and it doesn't change. It is configurable. It can't be null or empty. Since
     * this fields is final, the initial value is only the default value if the field is not configured in the
     * constructor.
     * </p>
     */
    private final String scheduledPhaseTypeName;

    /**
     * <p>
     * This is the phase type name for the Active PhaseType/ContestStatus. It is used to map the contest
     * status to the phase type.
     * </p>
     * <p>
     * It is defined at beginning and it doesn't change. It is configurable. It can't be null or empty. Since
     * this fields is final, the initial value is only the default value if the field is not configured in the
     * constructor.
     * </p>
     */
    private final String activePhaseTypeName;

    /**
     * <p>
     * This is the phase type name for the Action Required PhaseType/ContestStatus. It is used to map the
     * contest status to the phase type.
     * </p>
     * <p>
     * It is defined at beginning and it doesn't change. It is configurable. It can't be null or empty. Since
     * this fields is final, the initial value is only the default value if the field is not configured in the
     * constructor.
     * </p>
     */
    private final String actionRequiredPhaseTypeName;

    /**
     * <p>
     * This is the phase type name for the In Danger PhaseType/ContestStatus. It is used to map the contest
     * status to the phase type.
     * </p>
     * <p>
     * It is defined at beginning and it doesn't change. It is configurable. It can't be null or empty. Since
     * this fields is final, the initial value is only the default value if the field is not configured in the
     * constructor.
     * </p>
     */
    private final String inDangerPhaseTypeName;

    /**
     * <p>
     * This is the phase type name for the Insufficient Submission - ReRun Possible PhaseType/ContestStatus.
     * It is used to map the contest status to the phase type.
     * </p>
     * <p>
     * It is defined at beginning and it doesn't change. It is configurable. It can't be null or empty. Since
     * this fields is final, the initial value is only the default value if the field is not configured in the
     * constructor.
     * </p>
     */
    private final String insufficientSubmissionsReRunPossiblePhaseTypeName;

    /**
     * <p>
     * This is the phase type name for the Extended PhaseType/ContestStatus. It is used to map the contest
     * status to the phase type.
     * </p>
     * <p>
     * It is defined at beginning and it doesn't change. It is configurable. It can't be null or empty. Since
     * this fields is final, the initial value is only the default value if the field is not configured in the
     * constructor.
     * </p>
     */
    private final String extendedPhaseTypeName;

    /**
     * <p>
     * This is the phase type name for the Repost PhaseType/ContestStatus. It is used to map the contest
     * status to the phase type.
     * </p>
     * <p>
     * It is defined at beginning and it doesn't change. It is configurable. It can't be null or empty. Since
     * this fields is final, the initial value is only the default value if the field is not configured in the
     * constructor.
     * </p>
     */
    private final String repostPhaseTypeName;

    /**
     * <p>
     * This is the phase type name for the Insufficient Submissions PhaseType/ContestStatus. It is used to map
     * the contest status to the phase type.
     * </p>
     * <p>
     * It is defined at beginning and it doesn't change. It is configurable. It can't be null or empty. Since
     * this fields is final, the initial value is only the default value if the field is not configured in the
     * constructor.
     * </p>
     */
    private final String insufficientSubmissionsPhaseTypeName;

    /**
     * <p>
     * This is the phase type name for the No Winner Chosen PhaseType/ContestStatus. It is used to map the
     * contest status to the phase type.
     * </p>
     * <p>
     * It is defined at beginning and it doesn't change. It is configurable. It can't be null or empty. Since
     * this fields is final, the initial value is only the default value if the field is not configured in the
     * constructor.
     * </p>
     */
    private final String noWinnerChosenPhaseTypeName;

    /**
     * <p>
     * This is the phase type name for the Completed PhaseType/ContestStatus. It is used to map the contest
     * status to the phase type.
     * </p>
     * <p>
     * It is defined at beginning and it doesn't change. It is configurable. It can't be null or empty. Since
     * this fields is final, the initial value is only the default value if the field is not configured in the
     * constructor.
     * </p>
     */
    private final String completedPhaseTypeName;

    /**
     * <p>
     * This is the phase type name for the Abandoned PhaseType/ContestStatus. It is used to map the contest
     * status to the phase type.
     * </p>
     * <p>
     * It is defined at beginning and it doesn't change. It is configurable. It can't be null or empty. Since
     * this fields is final, the initial value is only the default value if the field is not configured in the
     * constructor.
     * </p>
     */
    private final String abandonedPhaseTypeName;

    /**
     * <p>
     * This is the phase type name for the Cancelled PhaseType/ContestStatus. It is used to map the contest
     * status to the phase type.
     * </p>
     * <p>
     * It is defined at beginning and it doesn't change. It is configurable. It can't be null or empty. Since
     * this fields is final, the initial value is only the default value if the field is not configured in the
     * constructor.
     * </p>
     */
    private final String cancelledPhaseTypeName;

    private final String terminatedPhaseTypeName;

    @Resource
    private SessionContext sessionContext;

    private Context jndiContext;

    @Resource(name = "unitName")
    private String unitName;

    public CockpitPhaseManagerBean() {
        draftPhaseTypeName = DRAFT;
        scheduledPhaseTypeName = SCHEDULED;
        activePhaseTypeName = ACTIVE;
        actionRequiredPhaseTypeName = ACTION_REQUIRED;
        inDangerPhaseTypeName = IN_DANGER;
        insufficientSubmissionsReRunPossiblePhaseTypeName = INSUFFICIENT_SUBMISSIONS_RE_RUN_POSSIBLE;
        extendedPhaseTypeName = EXTENDED;
        repostPhaseTypeName = REPOST;
        insufficientSubmissionsPhaseTypeName = INSUFFICIENT_SUBMISSIONS;
        noWinnerChosenPhaseTypeName = NO_WINNER_CHOSEN;
        completedPhaseTypeName = COMPLETED;
        abandonedPhaseTypeName = ABANDONED;
        cancelledPhaseTypeName = CANCELLED;
        terminatedPhaseTypeName = TERMINATED;

        initStatusMapping();
    }

    /**
     * <p>
     * This is method is performed after the construction of the bean, at this
     * point all the bean's resources will be ready.
     * </p>
     */
    @PostConstruct
    private void init(){
        try{
            InitialContext ctx = new InitialContext();
            this.jndiContext = ctx;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * <p>
     * Synchronizes the current state of the specified project with persistent storage. This method does
     * nothing.
     * </p>
     * @param project project for which to update phases. It is unused.
     * @param operator the operator performing the action. It is unused.
     */
    public void updatePhases(Project project, String operator) {
        // does nothing.
    }

    /**
     * <p>
     * Returns a <code>Project</code> entity which is mapped from the <code>Contest</code> corresponding
     * to the specified ID. If no such contest exists, <code>null</code> is returned.
     * </p>
     * @param contestId the id of contest to fetch.
     * @return the contest corresponding to the specified ID mapped to project, or<code>null</code> if no
     *         such contest exists.
     * @throws IllegalStateException if no contest manager has been set up before.
     * @throws CockpitPhaseManagementException if any error occurs when retrieving a contest from contest
     *         manager, or if a required contest status can not be found in contest manager for mapping the
     *         project's phase, or if no start date specified in the contest.
     */
    public Project getPhases(long contestId) throws CockpitPhaseManagementException {
        // checks if a contest manager has been set.
        checkContestManager();

        Project project = null;

        try {
            // retrieves contest with specified Id.
            Contest contest = contestManager.getContest(contestId);

            // if contest exists
            if (contest != null) {
                // creates project workdays using default workdays.
                Workdays workdays = new DefaultWorkdaysFactory().createWorkdaysInstance();

                // The contest startDate will be the project's start date
                Date startDate = contest.getStartDate();

                if (startDate == null) {
                    throw new CockpitPhaseManagementException("contest Start Date is not specified");
                }

                // creates the project with id of contestId
                project = new Project(startDate, workdays);
                project.setId(contestId);
                contest.getStatus();
                contest.getSubmissions();
                contest.getResults();
                project.setAttribute("contest", contest);
                // Get author handle and email
                UserPersistence userPersistence = getCockpitUserPersistence();
                User author = userPersistence.getUser(contest.getCreatedUser());
                String email = (String) author.getProfiles()[0].getAttribute("email").getValue();
                String handle = (String) author.getProfiles()[0].getAttribute("user").getValue();
                ArrayList emails = new ArrayList();
                emails.add(email);
                project.setAttribute("AuthorHandle", handle);
                project.setAttribute("AuthorEmail", email);
                project.setAttribute("ResourceEmails", emails);
                project.setAttribute("ContestID", contest.getContestId());
                project.setAttribute("ContestName", contest.getName());
                project.setAttribute("ContestWinnerAnouncementTime", contest.getWinnerAnnoucementDeadline());
                project.setAttribute("ContestStartTime", contest.getStartDate());
                project.setAttribute("ContestEndTime", contest.getEndDate());
                Set<Submission> submissions = contest.getSubmissions();
                if (submissions == null) {
                    project.setAttribute("ContestSubmissionsCount", 0L);
                } else {
                    project.setAttribute("ContestSubmissionsCount", new Long(submissions.size()));
                }
                Long forumID = contest.getForumId();
                if (forumID == null) {
                    project.setAttribute("ContestForumID", "0");
                } else {
                    project.setAttribute("ContestForumID", String.valueOf(forumID));
                }

                // gets all the Contest fields reflectively
                Field[] fields = Contest.class.getDeclaredFields();

                for (Field field : fields) {
                    String fieldName = field.getName();

                    if (!fieldName.equals("serialVersionUID") && !fieldName.equals("contestId")) {
                        field.setAccessible(true);

                        // gets the fieldValue
                        Object fieldValue = field.get(contest);

                        if (fieldName.equals("config")) {
                            // for Contest configs
                            // map each of configs into separate attribute.
                            Set<ContestConfig> configs = (Set<ContestConfig>) fieldValue;

                            if (configs != null) {
                                for (ContestConfig config : configs) {
                                    if (config != null) {
                                        ContestProperty property = config.getId().getProperty();

                                        // the attribute is: config.value + " " + property.propertId
                                        // -> property.description
                                        project.setAttribute(config.getValue() + " " + property.getPropertyId(),
                                            property.getDescription());
                                    }
                                }
                            }
                        } else if (fieldName.equals("status")) {
                            // the status is mapped to a phase
                            if (fieldValue == null) {
                                throw new CockpitPhaseManagementException(
                                    "Missing contest status from contest with given id '" + contestId + "'");
                            }

                            // maps the status to required phases
                            mapContestStatusToPhases(project, getPhaseLength(contest), (ContestStatus) fieldValue);
                        } else if (fieldValue != null) {
                            // for other Contest property simply add them as the
                            // project's attribute
                            if (fieldValue instanceof Set) {
                                // if the Contest property is a Set, it is
                                // wrapped with HashSet
                                fieldValue = new HashSet((Collection) fieldValue);
                            }

                            // ISV : We never use those attributes
//                            project.setAttribute(formatAttributeName(fieldName), (Serializable) fieldValue);
                        }
                    }
                }
            }
        } catch (ContestManagementException e) {
            throw new CockpitPhaseManagementException("A contest management exception is encountered"
                + " while retrieving contest from ContestManager", e);
        } catch (IllegalArgumentException e) {
            // should never happen since the passed object is always an instance
            // of ContestStatus
        } catch (IllegalAccessException e) {
            // should never happen since the field has been made accessible
        } catch (UserPersistenceException e) {
            throw new CockpitPhaseManagementException("A contest management exception is encountered"
                + " while retrieving contest from ContestManager", e);
        }

        return project;
    }
    /**
     * <p>
     * Similar to {@link #getPhases(long)}, except this method returns multiple <code>Contest</code> mapped
     * to <code>Project</code>s in one call. Indices in the returned array correspond to indices in the
     * input array. If a specified project cannot be found, a <code>null</code> will be returned in the
     * corresponding array position. If the <code>contestIds</code> is empty then return an empty array.
     * </p>
     * @param contestIds the contest IDs to look up, can be empty.
     * @return the <code>Contest</code> mapped to Project instances corresponding to the specified contest
     *         IDs
     * @throws IllegalArgumentException if <code>contestIds</code> is <code>null</code>.
     * @throws IllegalStateException if no contest manager has been set.
     * @throws CockpitPhaseManagementException if any error occurs when retrieving a contest from contest
     *         manager, or if a required contest status can not be found in contest manager for mapping the
     *         project's phase, or if no start date specified in the contest.
     */
    public Project[] getPhases(long[] contestIds) throws CockpitPhaseManagementException {
        checkNull(contestIds, "contestIds");

        checkContestManager();

        Project[] projects = new Project[contestIds.length];

        for (int i = 0; i < projects.length; i++) {
            projects[i] = getPhases(contestIds[i]);
        }

        return projects;
    }

    /**
     * <p>
     * Returns an array of all contest statuses mapped to phase types.
     * </p>
     * @return an array of all contest statuses mapped to phase types.
     * @throws CockpitPhaseManagementException if any error occurs while retrieving contest status from
     *         contest manager.
     */
    public PhaseType[] getAllPhaseTypes() throws CockpitPhaseManagementException {
        checkContestManager();

        List<ContestStatus> statuses = getAllContestStatuses();
        PhaseType[] types = new PhaseType[statuses.size()];

        int count = 0;

        for (ContestStatus status : statuses) {
            PhaseType phaseType = new PhaseType(status.getContestStatusId(), status.getName());
            types[count++] = phaseType;
        }

        return types;
    }

    /**
     * <p>
     * Returns an array of all phase statuses which can be used by a phase.
     * </p>
     * @return an array of all phase statuses which can be used by a phase.
     */
    public PhaseStatus[] getAllPhaseStatuses() {
        return new PhaseStatus[] {PhaseStatus.OPEN, PhaseStatus.CLOSED, PhaseStatus.SCHEDULED};
    }

    /**
     * <p>
     * Determines whether it is possible to start the specified phase by the name of phase type.
     * </p>
     * @param phase phase to test for starting.
     * @return <code>true</code> if the specified phase can be started; <code>false</code> otherwise
     * @throws IllegalArgumentException if <code>phase</code> is <code>null</code>, or if
     *         <code>phase</code> does not have a phase type.
     * @throws CockpitPhaseManagementException if any error occurs while testing the phase for starting.
     */
    public boolean canStart(Phase phase) throws CockpitPhaseManagementException {
        refreshPhase(phase);
        return canPerform(phase, PhaseOperationEnum.START);
    }

    /**
     * <p>
     * Starts the specified phase and update the contest associated to the phase to the new status. The phase
     * status is set to <code>PhaseStatus#OPEN</code>.
     * </p>
     * @param phase the phase to start.
     * @param operator the operator of the operation.
     * @throws IllegalArgumentException if any argument is <code>null</code>, or if <code>phase</code>
     *         does not have a phase type, or if <code>operator</code> is empty.
     * @throws CockpitPhaseManagementException if any error occurs when starting the phase.
     */
    public void start(Phase phase, String operator) throws CockpitPhaseManagementException {
        refreshPhase(phase);
        perform(phase, operator, PhaseOperationEnum.START, PhaseStatus.OPEN);
    }

    /**
     * <p>
     * Determines whether it is possible to end the specified phase.
     * </p>
     * @param phase phase to test for ending.
     * @return <code>true</code> if the specified phase can be ended; <code>false</code> otherwise
     * @throws IllegalArgumentException if <code>phase</code> is <code>null</code>, or if
     *         <code>phase</code> does not have a phase type.
     * @throws CockpitPhaseManagementException if any error occurs while testing the phase for ending.
     */
    public boolean canEnd(Phase phase) throws CockpitPhaseManagementException {
        refreshPhase(phase);
        return canPerform(phase, PhaseOperationEnum.END);
    }

    /**
     * <p>
     * Ends the specified phase. The phase status is set to <code>PhaseStatus#CLOSED</code>.
     * </p>
     * @param phase the phase to end.
     * @param operator the operator of the operation.
     * @throws IllegalArgumentException if any argument is <code>null</code>, or if <code>phase</code>
     *         does not have a phase type, or if <code>operator</code> is empty.
     * @throws CockpitPhaseManagementException if any error occurs when end the phase.
     */
    public void end(Phase phase, String operator) throws CockpitPhaseManagementException {
        refreshPhase(phase);
        perform(phase, operator, PhaseOperationEnum.END, PhaseStatus.CLOSED);
    }

    /**
     * <p>
     * Determines whether it is possible to cancel the specified phase. This method always returns
     * <code>false</code>.
     * </p>
     * @param phase a phase to test. This is unused.
     * @return <code>false</code>.
     */
    public boolean canCancel(Phase phase) {
        return false;
    }

    /**
     * <p>
     * Cancels the specified phase. This method does nothing.
     * </p>
     * @param phase the phase to end. This is unused.
     * @param operator the operator of the operation. This is unused.
     */
    public void cancel(Phase phase, String operator) {
        // does nothing.
    }

    /**
     * <p>
     * Registers a custom handler for the specified phase type and operation. If a handler already exists for
     * the specified type/operation combination, it will be replaced by the specified handler.
     * </p>
     * <p>
     * Note that <code>type</code> is stored in the registry by reference (rather than copied) so the caller
     * should take care not to subsequently modify the type. Doing so may cause the registry to become
     * inconsistent.
     * </p>
     * @param handler the handler.
     * @param type the phase type to associate with the handler.
     * @param operation the operation to associate with the handler.
     * @throws IllegalArgumentException if any argument is <code>null</code>.
     */
    public void registerHandler(PhaseHandler handler, PhaseType type, PhaseOperationEnum operation) {
        checkNull(handler, "handler");

        HandlerRegistryInfo info = new HandlerRegistryInfo(type, operation);
        Map<HandlerRegistryInfo, PhaseHandler> handlers = getHandlersFromContext();
        handlers.put(info, handler);
    }

    /**
     * <p>
     * Unregisters the handler (if any) associated with the specified phase type and operation and returns a
     * reference to the handler. Returns <code>null</code> if no handler is associated with the specified
     * type/operation combination.
     * </p>
     * @param type the phase type associated with the handler to unregister
     * @param operation the operation associated with the handler to unregister.
     * @return the previously registered handler, or <code>null</code> if no handler was registered.
     * @throws IllegalArgumentException if any argument is <code>null</code>.
     */
    public PhaseHandler unregisterHandler(PhaseType type, PhaseOperationEnum operation) {
        HandlerRegistryInfo info = new HandlerRegistryInfo(type, operation);
        Map<HandlerRegistryInfo, PhaseHandler> handlers = getHandlersFromContext();
        return handlers.remove(info);
    }

    /**
     * <p>
     * Returns an array of all the currently registered phase handlers. If a handler is registered more than
     * one (for different phase/operation combinations), it will appear only once in the array. If there are
     * no handlers in the map then an empty array is returned.
     * </p>
     * @return all of the currently registered phase handlers.
     */
    public PhaseHandler[] getAllHandlers() {
        Set<PhaseHandler> handlerSet = new HashSet<PhaseHandler>();

        Map<HandlerRegistryInfo, PhaseHandler> handlers = getHandlersFromContext();
        for (PhaseHandler handler : handlers.values()) {
            handlerSet.add(handler);
        }

        return handlerSet.toArray(new PhaseHandler[handlerSet.size()]);
    }

    /**
     * <p>
     * Returns the phase type(s) and operation(s) associated with the specified handler in the handler
     * registry. An empty array is returned if the handler is not registered.
     * </p>
     * @param handler handler of interest.
     * @return the registration entries associated with the handler.
     * @throws IllegalArgumentException if <code>handler</code> is <code>null</code>.
     */
    public HandlerRegistryInfo[] getHandlerRegistrationInfo(PhaseHandler handler) {
        checkNull(handler, "handler");

        List<HandlerRegistryInfo> infoList = new ArrayList<HandlerRegistryInfo>();

        Map<HandlerRegistryInfo, PhaseHandler> handlers = getHandlersFromContext();
        for (Map.Entry<HandlerRegistryInfo, PhaseHandler> entry : handlers.entrySet()) {
            if (handler.equals(entry.getValue())) {
                infoList.add(entry.getKey());
            }
        }

        return infoList.toArray(new HandlerRegistryInfo[infoList.size()]);
    }

    /**
     * <p>
     * Sets the current phase validator for this manager. This method does nothing.
     * </p>
     * @param phaseValidator the validator to use for this manager. This is unused.
     */
    public void setPhaseValidator(PhaseValidator phaseValidator) {
        // does nothing.
    }

    /**
     * <p>
     * Returns the current phase validator. This method always returns <code>null</code>.
     * </p>
     * @return <code>null</code>.
     */
    public PhaseValidator getPhaseValidator() {
        return null;
    }
    


    /**
     * <p>
     * Fills the status mapping which is used to hold the mapping between ContestStatus name to PhaseType name
     * as specified in class documentation.
     * </p>
     */
    private void initStatusMapping() {
        // Scheduled is removed from list of next allowable statuses for Draft since application will assign Scheduled
        // status to contest on it's own when the payment is received. For now this is not a responsibility of
        // Auto-Pilot to switch contest to Scheduled status
        statusMapping.put(draftPhaseTypeName, new String[] {});
        
        statusMapping.put(scheduledPhaseTypeName, new String[] {activePhaseTypeName});

        // ISV : Temporary Fix : For now Insufficient Submissions - Re-Run Possible phase is not supported. Therefore
        // if there are no sufficient number submissions received the contest is closed
//        statusMapping.put(activePhaseTypeName,
//            new String[] {actionRequiredPhaseTypeName, insufficientSubmissionsReRunPossiblePhaseTypeName});
        statusMapping.put(activePhaseTypeName, new String[] {actionRequiredPhaseTypeName, completedPhaseTypeName});

        // ISV : Re-Post phase is currently removed as application does not provide UI for re-posting the contest
//        statusMapping.put(actionRequiredPhaseTypeName, new String[] {completedPhaseTypeName, inDangerPhaseTypeName,
//                                                                     repostPhaseTypeName, noWinnerChosenPhaseTypeName});
        statusMapping.put(actionRequiredPhaseTypeName,
                          new String[] {completedPhaseTypeName, inDangerPhaseTypeName});

//        statusMapping.put(inDangerPhaseTypeName, new String[] {completedPhaseTypeName, abandonedPhaseTypeName});
        statusMapping.put(inDangerPhaseTypeName, new String[] {completedPhaseTypeName, abandonedPhaseTypeName});

        // ISV : Insufficient Submissions, Extended, Insufficient Submissions - Rerun possible, Repost phases are
        // not currently supported
//        statusMapping.put(insufficientSubmissionsReRunPossiblePhaseTypeName,
//            new String[] {extendedPhaseTypeName, abandonedPhaseTypeName});
//        statusMapping.put(extendedPhaseTypeName,
//            new String[] {actionRequiredPhaseTypeName, insufficientSubmissionsPhaseTypeName});
//        statusMapping.put(repostPhaseTypeName,
//            new String[] {actionRequiredPhaseTypeName, insufficientSubmissionsReRunPossiblePhaseTypeName});
//        statusMapping.put(insufficientSubmissionsPhaseTypeName,
//            new String[] {abandonedPhaseTypeName});

        // ISV : No Winner Chosen is a final state of contest
//        statusMapping.put(noWinnerChosenPhaseTypeName, new String[] {abandonedPhaseTypeName, repostPhaseTypeName});
        statusMapping.put(noWinnerChosenPhaseTypeName, new String[] {});
        statusMapping.put(abandonedPhaseTypeName, new String[] {});

        statusMapping.put(completedPhaseTypeName, new String[] {});
    }

    /**
     * <p>
     * This helper method calculates the phase length from the contest endDate. The phase length is calculated
     * as contest.endDate - System.currentTimeMillis(). In case of the result is negative or endDate is null,
     * the length will be zero.
     * @param contest the contest.
     * @return phase length
     */
    private long getPhaseLength(Contest contest) {
        Date endDate = contest.getEndDate();
        long length = -1;

        if (endDate != null) {
            length = endDate.getTime() - System.currentTimeMillis();
        }

        if (length < 0) {
            length = 0;
        }

        return length;
//        return contest.getEndDate().getTime() - contest.getStartDate().getTime();
    }


    /**
     * <p>
     * Determines whether it is possible to perform the given operation on the specified phase.
     * </p>
     * @param phase the phase to test.
     * @param operation the operation to be performed.
     * @return <code>true</code> if the specified phase can be performed; <code>false</code> otherwise
     * @throws CockpitPhaseManagementException if any error occurs while testing the phase.
     */
    private boolean canPerform(Phase phase, PhaseOperationEnum operation)
        throws CockpitPhaseManagementException {
        checkNull(phase, "phase");

        // checks if the given phase has a phase type
        PhaseType phaseType = phase.getPhaseType();
        checkNull(phaseType, "The phase type of given phase");

        // creates HandlerRegistryInfo as a key to get the handler.
        HandlerRegistryInfo info = new HandlerRegistryInfo(phaseType, operation);
        Map<HandlerRegistryInfo, PhaseHandler> handlers = getHandlersFromContext();
        PhaseHandler handler = handlers.get(info);

        if (handler != null) {
            try {
                return handler.canPerform(phase);
            } catch (PhaseHandlingException e) {
                throw new CockpitPhaseManagementException("A phase handling exception is encountered"
                    + " when determining if the phase can be handled or not", e);
            }
        }

        return false;
    }

    /**
     * <p>
     * This helper method handles start/end operation on given phase.
     * </p>
     * @param phase the phase to handle.
     * @param operator the operator of the operation.
     * @param operation the operation mode (start or end).
     * @param phaseStatus the phase status to be assigned to phase.
     * @throws CockpitPhaseManagementException if any error occurs when handling the phase.
     */
    private void perform(Phase phase, String operator, PhaseOperationEnum operation, PhaseStatus phaseStatus)
        throws CockpitPhaseManagementException {
        checkNull(phase, "phase");
        checkNullOrEmpty(operator, "operator");

        checkContestManager();

        // checks if the given phase has a phase type
        PhaseType phaseType = phase.getPhaseType();
        checkNull(phaseType, "The phase type of given phase");

        // gets the Project which the phase belongs to
        Project project = phase.getProject();
        long projectId = project.getId();

        try {
            // creates HandlerRegistryInfo with the phaseType and operation to
            // get the handler
            HandlerRegistryInfo info = new HandlerRegistryInfo(phaseType, operation);
            Map<HandlerRegistryInfo, PhaseHandler> handlers = getHandlersFromContext();
            PhaseHandler handler = handlers.get(info);

            if (handler != null) {
                // find a ContestStatus which is the phase type is mapped to.
                ContestStatus contestStatus = findContestStatusByPhaseType(getAllContestStatuses(), phaseType);

                // updates the contest status
                if (operation == PhaseOperationEnum.START) {
                    logInfo("updating the contest status", phase.getId(), phaseType.getName(), projectId, true);
                    contestManager.updateContestStatus(projectId, contestStatus.getContestStatusId());
                }

                logInfo(operation.getName() + " the phase", phase.getId(), phaseType.getName(), projectId, false);
                // handles the phase
                handler.perform(phase, operator);

                // set the phase status
                phase.setPhaseStatus(phaseStatus);
            }
        } catch (ContestManagementException e) {
            throw new CockpitPhaseManagementException("A contest management exception is encountered"
                + " when updating the contest status", e);
        } catch (PhaseHandlingException e) {
            throw new CockpitPhaseManagementException("A phase handling exception is encountered"
                + " when starting the phase", e);
        }
    }

    /**
     * <p>
     * This helper methods formats the given field name, capitalizes the first character. It is used in
     * getPhase() method when setting project attributes.
     * </p>
     * @param fieldName the field name to format.
     * @return formatted field name.
     */
    private String formatAttributeName(String fieldName) {
        StringBuilder builder = new StringBuilder("contest");
        builder.append(Character.toUpperCase(fieldName.charAt(0)));
        builder.append(fieldName.substring(1));

        return builder.toString();
    }

    /**
     * <p>
     * This helper method logs the given message and value with INFO level.
     * </p>
     * @param message the log message.
     * @param phaseId the phase id.
     * @param phaseTypeName the phase type name.
     * @param projectId the project id.
     * @param contestStatus whether to log contest status or phase.
     */
    private void logInfo(String message, long phaseId, String phaseTypeName, long projectId, boolean contestStatus) {
        if (log != null) {
            Object[] params = new Object[] {message, phaseId, phaseTypeName, projectId};
            String format = contestStatus ? "{0} with contest status [id={1}, name={2}] in contest with id of {3}"
                                          : "{0} with phase type [id={1}, name={2}] in project with id of {3}";
            log.log(Level.INFO, format, params);
        }
    }

    /**
     * <p>
     * This helper method checks if a contest manager has been set in this manager.
     * </p>
     * @throws IllegalStateException if no contest manager is set.
     */
    private void checkContestManager() {
        if (contestManager == null) {
            throw new IllegalStateException("No contest manager has been set up for this cockpit phase manager");
        }
    }

    /**
     * Checks if the given object is not null.
     * @param value the object to check.
     * @param name the name of object.
     * @throws IllegalArgumentException if value is null.
     */
    private void checkNull(Object value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(name + " should not be null");
        }
    }

    /**
     * Checks if the given String is not null and not empty.
     * @param value the string to check.
     * @param name the name of object.
     * @throws IllegalArgumentException if value is null or empty.
     */
    private void checkNullOrEmpty(String value, String name) {
        checkNull(value, name);
        checkEmpty(value, name);
    }

    /**
     * Checks if the given String is not empty.
     * @param value the string to check.
     * @param name the name of object.
     * @throws IllegalArgumentException if value is empty.
     */
    private void checkEmpty(String value, String name) {
        if (value.trim().length() == 0) {
            throw new IllegalArgumentException(name + " should not be empty");
        }
    }

    /**
     * <p>
     * This helper method retrieves all contest statuses from ContestManager. If the cache is not null then
     * the statuses are put into the cache for later use
     * </p>
     * @return a list of contest statuses
     * @throws CockpitPhaseManagementException if any error occurs while retrieving contest status from
     *         contest manager.
     */
    private List<ContestStatus> getAllContestStatuses()
        throws CockpitPhaseManagementException {
        // if cached is specified and it is not empty
        // simply retrieves statuses from the cache
        Cache cachedContestStatuses = getCachedContestStatuses();
        if (cachedContestStatuses != null && cachedContestStatuses.getSize() > 0) {
            return (List<ContestStatus>) cachedContestStatuses.get("contestStatuses");
        } else {
            // otherwise, obtains statuses from contest manager
            try {
                List<ContestStatus> list = contestManager.getAllContestStatuses();

                // if there is a cache, put the statuses to it
                if (cachedContestStatuses != null) {
                    cachedContestStatuses.put("contestStatuses", list);
                }

                return list;
            } catch (ContestManagementException e) {
                throw new CockpitPhaseManagementException("A contest management exception is encountered"
                    + " while getting all contest statuses", e);
            }
        }
    }

    /**
     * <p>
     * This helper method maps the contest status into project's phases. A status of a Contest is mapped to
     * many phase in Project.
     * </p>
     * @param project the project which the phases belong to.
     * @param length the length of the phase.
     * @param contestStatus the contest status to map.
     * @throws CockpitPhaseManagementException if a required contest status is not found in ContestManager.
     */
    private void mapContestStatusToPhases(Project project, long length, ContestStatus contestStatus)
        throws CockpitPhaseManagementException {
        List<ContestStatus> contestStatuses = getAllContestStatuses();

        Phase phase = mapContestStatusToPhase(project, contestStatus, length);
        project.addPhase(phase);

        String[] mappedTypes = statusMapping.get(contestStatus.getName());

        for (String typeName : mappedTypes) {
            contestStatus = findContestStatusByName(contestStatuses, typeName);
            phase = mapContestStatusToPhase(project, contestStatus, length);
            phase.setPhaseStatus(PhaseStatus.SCHEDULED);
            project.addPhase(phase);
        }
    }

    /**
     * <p>
     * This helper method finds a contest status by specified name in the given list.
     * </p>
     * @param contestStatuses the list of contest status.
     * @param name the name of contest status to find.
     * @return ContestStatus instance.
     * @throws CockpitPhaseManagementException if no contest status is found.
     */
    private ContestStatus findContestStatusByName(List<ContestStatus> contestStatuses, String name)
        throws CockpitPhaseManagementException {
        for (ContestStatus status : contestStatuses) {
            if (status.getName().equals(name)) {
                return status;
            }
        }

        throw new CockpitPhaseManagementException("No contest status found in ContestManager with name of '"
            + name + "'");
    }

    /**
     * <p>
     * This helper method finds a contest status by specified phaseType in the given list.
     * </p>
     * @param contestStatuses the list of contest status.
     * @param phaseType the phaseType which maps to contest status to find.
     * @return ContestStatus instance.
     * @throws CockpitPhaseManagementException if no contest status is found.
     */
    private ContestStatus findContestStatusByPhaseType(List<ContestStatus> contestStatuses, PhaseType phaseType)
        throws CockpitPhaseManagementException {
        for (ContestStatus status : contestStatuses) {
            if (status.getName().equals(phaseType.getName()) && status.getContestStatusId() == phaseType.getId()) {
                return status;
            }
        }

        throw new CockpitPhaseManagementException("No contest status found in ContestManager with phase type of ["
            + phaseType.getId() + ":" + phaseType.getName() + "]");
    }

    /**
     * <p>
     * This helper method maps a ContestStatus to a Phase.
     * </p>
     * @param project the project which the phase belong to.
     * @param contestStatus the contest status to map.
     * @param length the length of the phase.
     * @return the mapped Phase instance.
     */
    private Phase mapContestStatusToPhase(Project project, ContestStatus contestStatus, long length) {
        String statusName = contestStatus.getName();
        Long statusId = contestStatus.getContestStatusId();

        // creates Phase in the project with given length
        Phase phase = new Phase(project, length);

        // phase id is contestStatusId
        phase.setId(statusId);

        // map the status to PhaseType
        PhaseType phaseType = new PhaseType(statusId, statusName);
        phase.setPhaseType(phaseType);

        // sets the phase attribute
        phase.setAttribute("name", statusName);
        phase.setAttribute("description", contestStatus.getDescription());
        phase.setAttribute(statusName + "Statuses", new ArrayList<ContestStatus>(contestStatus.getStatuses()));
        phase.setPhaseStatus(PhaseStatus.OPEN);

        return phase;
    }

    /**
     * <p>
     * Returns the contest manager which is configured or set in this manager.
     * </p>
     * @return the contest manager in this manager.
     */
    public ContestManager getContestManager() {
        return contestManager;
    }

    /**
     * <p>
     * Sets the contest manager to be used in this manager.
     * </p>
     * @param contestManager the contestManager to set.
     * @throws IllegalArgumentException if <code>contestManager</code> is <code>null</code>.
     */
    public void setContestManager(ContestManager contestManager) {
        checkNull(contestManager, "contestManager");
        this.contestManager = (ContestManagerLocal) contestManager;
    }

    /**
     * <p>
     * Returns the log used by this manager.
     * </p>
     * @return the possibly <code>null</code> log used by this manager.
     */
    public Log getLog() {
        return log;
    }

    /**
     * <p>
     * Sets the log. It can be null which means no logging is performed.
     * </p>
     * @param log the log to set, can be <code>null</code>.
     */
    public void setLog(Log log) {
        this.log = log;
    }

    /**
     * <p>
     * Sets the log by the name. If <code>logName</code> is not <code>null</code>, then a log is
     * retrieved from <code>LogManager</code> by the name, otherwise no logging is performed in this
     * manager.
     * </p>
     * @param logName the name of log.
     * @throws IllegalArgumentException if <code>logName</code> is empty.
     */
    public void setLog(String logName) {
        if (logName != null) {
            checkEmpty(logName, "logName");
            log = LogManager.getLog(logName);
        }
    }

    /**
     * <p>
     * Returns the cache used for ContestStatus instances. If it is not null and not empty, the cache will
     * contain 1 object with key "contestStatuses".
     * </p>
     * @return the possibly <code>null</code> cache used for ContestStatuses instances.
     */
    public Cache getCachedContestStatuses() {
        final String name = "CockpitPhaseStatusesCache";
        try {
            Object cache = this.jndiContext.lookup(name);
            return (Cache) cache;
        } catch (NamingException e) {
            return null;
        }
    }

    /**
     * <p>
     * Sets the cache used for ContestStatus instances.
     * </p>
     * @param cachedContestStatuses the cache used for ContestStatus instances, can be <code>null</code>.
     */
    public void setCachedContestStatuses(Cache cachedContestStatuses) {
        final String name = "CockpitPhaseStatusesCache";
        try {
            this.jndiContext.bind(name, cachedContestStatuses);
        } catch (NamingException e) {
            throw new RuntimeException("Could not store statuses cache map in JNDI context under " + name + " name", e);
        }
    }

    /**
     * <p>
     * Returns the handlers map. A shallow copy is returned.
     * </p>
     * @return the handlers.
     */
    public Map<HandlerRegistryInfo, PhaseHandler> getHandlers() {
        Map<HandlerRegistryInfo, PhaseHandler> handlers = getHandlersFromContext();
        return new HashMap<HandlerRegistryInfo, PhaseHandler>(handlers);
    }

    /**
     * <p>
     * Set the handlers. A shallow copy is used.
     * </p>
     * @param newHandlers the handlers to set.
     * @throws IllegalArgumentException if <code>handlers</code> is <code>null</code>, or if
     *         <code>handlers</code> contains <code>null</code> keys or values.
     */
    public void setHandlers(Map<HandlerRegistryInfo, PhaseHandler> newHandlers) {
        checkNull(newHandlers, "handlers");

        Map<HandlerRegistryInfo, PhaseHandler> handlers = getHandlersFromContext();
        handlers.clear();

        for (Map.Entry<HandlerRegistryInfo, PhaseHandler> entry : newHandlers.entrySet()) {
            HandlerRegistryInfo key = entry.getKey();
            checkNull(key, "key in handlers");

            PhaseHandler value = entry.getValue();
            checkNull(value, "value in handlers");
            handlers.put(key, value);
        }
    }

    /**
     * <p>
     * Returns the handlers map. A shallow copy is returned.
     * </p>
     * @return the handlers.
     */
    private Map<HandlerRegistryInfo, PhaseHandler> getHandlersFromContext() {
        final String name = "CockpitPhaseHandlers";
        try {
            Object handlers;
            try {
                handlers = this.jndiContext.lookup(name);
            } catch (NameNotFoundException e) {
                handlers  = new HashMap<HandlerRegistryInfo, PhaseHandler>();
                this.jndiContext.bind(name, handlers);
            }
            return (Map<HandlerRegistryInfo, PhaseHandler>) handlers;
        } catch (NamingException e) {
            throw new RuntimeException("Could not access/store handlers map in JNDI context under " + name  + " name",
                                       e);
        }
    }

    private UserPersistence getCockpitUserPersistence() {
        return new CockpitUserPersistenceImpl();
    }

    /**
     * <p>
     * Returns the <code>EntityManager</code> looked up from the session
     * context.
     * </p>
     *
     * @return the EntityManager looked up from the session context
     * @throws ContestManagementException
     *             if fail to get the EntityManager from the sessionContext.
     */
    private EntityManager getEntityManager() throws CockpitPhaseManagementException {
        try {
            Object obj = sessionContext.lookup(unitName);

            if (obj == null) {
                throw wrapContestManagementException("The object for jndi name '" + unitName + "' doesn't exist.");
            }

            return (EntityManager) obj;
        } catch (ClassCastException e) {
            throw wrapContestManagementException(e, "The jndi name for '" + unitName
                    + "' should be EntityManager instance.");
        }
    }

    /**
     * <p>
     * Creates an <code>EntityNotFoundException</code> with specified message,
     * and log the exception, set the sessionContext to correct state.
     * </p>
     *
     * @param message
     *            the error message
     * @return the created EntityNotFoundException
     */
    private EntityNotFoundException wrapEntityNotFoundException(String message) {
        EntityNotFoundException e = new EntityNotFoundException(message);
        logException(e, message);
        sessionContext.setRollbackOnly();

        return e;
    }

    /**
     * <p>
     * Creates a <code>ContestManagementException</code> with the error message.
     * It will log the exception, and set the sessionContext to rollback only.
     * </p>
     *
     * @param message
     *            the error message
     * @return the created exception
     */
    private CockpitPhaseManagementException wrapContestManagementException(String message) {
        CockpitPhaseManagementException e = new CockpitPhaseManagementException(message);
        logException(e, message);
        sessionContext.setRollbackOnly();

        return e;
    }

    /**
     * <p>
     * Creates a <code>ContestManagementException</code> with inner exception
     * and message. It will log the exception, and set the sessionContext to
     * rollback only.
     * </p>
     *
     * @param e
     *            the inner exception
     * @param message
     *            the error message
     * @return the created exception
     */
    private CockpitPhaseManagementException wrapContestManagementException(Exception e, String message) {
        CockpitPhaseManagementException ce = new CockpitPhaseManagementException(message, e);
        logException(ce, message);
        sessionContext.setRollbackOnly();

        return ce;
    }

    /**
     * <p>
     * Log the exception.
     * </p>
     *
     * @param e
     *            the exception to log
     * @param message
     *            the string message
     */
    private void logException(Throwable e, String message) {
        if (log != null) {
            // This will log the message and StackTrace of the exception.
            log.log(Level.ERROR, e, message);

            while (e != null) {
                log.log(Level.ERROR, "INNER: " + e.getMessage());
                e = e.getCause();
            }
        }
    }

    private void refreshPhase(Phase phase) throws CockpitPhaseManagementException {
//        EntityManager entityManager = getEntityManager();
//        Contest contest = (Contest) phase.getProject().getAttribute("contest");
//        contest = entityManager.merge(contest);
//        entityManager.refresh(contest);
//        phase.getProject().setAttribute("contest", contest);
    }
}
