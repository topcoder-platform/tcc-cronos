/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit;

import com.topcoder.date.workdays.DefaultWorkdaysFactory;
import com.topcoder.date.workdays.Workdays;

import com.topcoder.management.phase.HandlerRegistryInfo;
import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.phase.PhaseOperationEnum;
import com.topcoder.management.phase.PhaseValidator;

import com.topcoder.naming.jndiutility.ConfigurationException;
import com.topcoder.naming.jndiutility.JNDIUtil;
import com.topcoder.naming.jndiutility.JNDIUtils;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.service.studio.contest.ContestProperty;
import com.topcoder.service.studio.contest.ContestStatus;

import com.topcoder.util.cache.Cache;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.Property;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NamingException;


/**
 * <p>
 * This is main (and only) class of this component. It is an adapter of the <code>PhaseManager</code>
 * interface to the part of <code>ContestManager</code> interface related to <code>ContestStatus</code>.
 * This manager will be used in the Cockpit application.
 * </p>
 * <p>
 * The methods of this class map the <code>Project</code> and <code>Phase</code> instance to
 * <code>Contest</code> and <code>ContestStatus</code>. Some methods do nothing, there is not a mapping
 * of these methods for this cockpit manager implementation. This class uses an instance of contest manager to
 * delegate the work. The handlers will be the handlers from the Cockpit Phase handlers component. They are
 * used to perform the methods related to the phases: start/canStart and end/canEnd methods.
 * </p>
 * <em>Contest --> Project</em>
 * <ul>
 * <li> The <code>Contest</code> entity is mapped to the <code>Project</code> Entity. This is the table of
 * mapping: <table border="1">
 * <tr>
 * <td><em>Contest</em></td>
 * <td><em>Project</em></td>
 * </tr>
 * <tr>
 * <td>contestId</td>
 * <td>id</td>
 * </tr>
 * </table> </li>
 * <li> The other fields of the <code>Contest</code> are added to <code>Project</code> as attribute. The
 * key of the attribute is the name of the field in the <code>Contest</code> entity as <code>String</code>
 * plus the prefix "contest", the value of the attribute will be the value of the field. The camelCase is
 * used. </li>
 * <li> The collection are added directly except for the config attribute. The <code>ContestConfig</code>
 * instances must be added with: <code>contestConfig.value contestConfig.property.propertyId</code> -->
 * <code>contestConfig.property.description</code>. </li>
 * </ul>
 * <em>ContestStatus --> Phase</em>
 * <ul>
 * <li> The <code>ContesStatus</code> is mapped to the <code>Phase</code> entity. This is the table of
 * mapping: <table border="1">
 * <tr>
 * <td><em>ContestStatus</em></td>
 * <td><em>Phase</em></td>
 * </tr>
 * <tr>
 * <td>ContestStatus.contestStatusId</td>
 * <td>Phase.id</td>
 * </tr>
 * <tr>
 * <td>ContestStatus.name</td>
 * <td>Phase.PhaseType.name</td>
 * </tr>
 * <tr>
 * <td>ContestStatus.contestStatusId</td>
 * <td>Phase.PhaseType.id</td>
 * </tr>
 * </table> </li>
 * <li> The other fields of the <code>ContestStatus</code> (description,name) must be added to
 * <code>Phase</code> as attribute. The key of the attribute will be the name of the field in the Contest
 * entity as String, the value of the attribute will be the value of the field, with the same mechanism
 * described for the <code>Contest</code> entity. Add the <code>contestStatus.statuses</code> item to the
 * attributes using: status.name + "Statuses" --> ContestStatus (directly the <code>ContestStatus</code>
 * without conversion) </li>
 * </ul>
 * In the case of <code>Contest.status</code>, the status is mapped to many phases entities in the
 * <code>Project</code> entity, these phases are defined by <code>PhaseType.name</code>. The following
 * table is used to create the related <code>Phase</code> entities in the <code>Project.phases</code>
 * collection: <table border="1" cellspacing="3" cellpadding="2">
 * <tr>
 * <td> Contest.status.name</td>
 * <td>Phase to create and add to the Project: PhaseType.name</td>
 * </tr>
 * <tr>
 * <td>Draft</td>
 * <td>Draft<br/>Scheduled</td>
 * </tr>
 * <tr>
 * <td>Scheduled</td>
 * <td>Scheduled<br/>Active</td>
 * </tr>
 * <tr>
 * <td>Active</td>
 * <td>Active<br/>Action Required<br/>Insufficient Submissions - ReRun Possible</td>
 * </tr>
 * <tr>
 * <td>Action Required</td>
 * <td>Action Required<br/>Completed<br/>In Danger</td>
 * </tr>
 * <tr>
 * <td>In Danger</td>
 * <td>In Danger<br/>Completed<br/>Abandoned</td>
 * </tr>
 * <tr>
 * <td>Insufficient Submissions - ReRun Possible</td>
 * <td>Insufficient Submissions - ReRun Possible<br/>Extended<br/>Abandoned</td>
 * </tr>
 * <tr>
 * <td>Extended</td>
 * <td>Extended<br/>Action Required<br/>Insufficient Submissions</td>
 * </tr>
 * <tr>
 * <td>Repost</td>
 * <td>Repost<br/>Action Required<br/>Insufficient Submissions - ReRun Possible</td>
 * </tr>
 * <tr>
 * <td>Insufficient Submissions</td>
 * <td>Insufficient Submissions<br/>Cancelled<br/>Abandoned</td>
 * </tr>
 * <tr>
 * <td>No Winner Chosen</td>
 * <td>No Winner Chosen<br/>Cancelled<br/>Abandoned<br/>Repost</td>
 * </tr>
 * </table>
 * <p>
 * Sample configuration for creating CockpitPhaseManager using configuration:
 *
 * <pre>
 *      &lt;Config name=&quot;CockpitPhaseManagerJNDI&quot;&gt;
 *         &lt;Property name=&quot;objectFactoryNamespace&quot;&gt;
 *             &lt;Value&gt;ObjectFactory&lt;/Value&gt;
 *         &lt;/Property&gt;
 *         &lt;Property name=&quot;contestManagerName&quot;&gt;
 *             &lt;Value&gt;contestManagerRemote&lt;/Value&gt;
 *         &lt;/Property&gt;
 *         &lt;Property name=&quot;logName&quot;&gt;
 *            &lt;Value&gt;myLog&lt;/Value&gt;
 *         &lt;/Property&gt;
 *         &lt;Property name=&quot;cachedContestStatusesKey&quot;&gt;
 *             &lt;Value&gt;myCache&lt;/Value&gt;
 *         &lt;/Property&gt;
 *         &lt;!-- Define the phase handlers --&gt;
 *         &lt;Property name=&quot;handlers&quot;&gt;
 *             &lt;Property name=&quot;1,Draft,start&quot;&gt;
 *                 &lt;Value&gt;mockPhaseHandler&lt;/Value&gt;
 *             &lt;/Property&gt;
 *             &lt;Property name=&quot;2,Scheduled,end&quot;&gt;
 *                 &lt;Value&gt;mockPhaseHandler&lt;/Value&gt;
 *             &lt;/Property&gt;
 *             &lt;Property name=&quot;3,Active,cancel&quot;&gt;
 *                 &lt;Value&gt;mockPhaseHandler&lt;/Value&gt;
 *             &lt;/Property&gt;
 *         &lt;/Property&gt;
 *         &lt;Property name=&quot;cancelledPhaseTypeName&quot;&gt;
 *             &lt;Value&gt;Cancelled&lt;/Value&gt;
 *         &lt;/Property&gt;
 *         &lt;Property name=&quot;abandonedPhaseTypeName&quot;&gt;
 *             &lt;Value&gt;Abandoned&lt;/Value&gt;
 *          &lt;/Property&gt;
 *       &lt;/Config&gt;
 * </pre>
 *
 * </p>
 * <p>
 * Sample usage of CockpitPhaseManager:
 *
 * <pre>
 * PhaseManager manager = new CockpitPhaseManager(&quot;CockpitPhaseManager&quot;);
 * ContestManager contestManager = ((CockpitPhaseManager) manager)
 *         .getContestManager();
 * TestHelper.initContestStatuses(contestManager);
 * ContestStatus draft = contestManager.getContestStatus(1);
 * Contest contest = TestHelper.createContest(17, TestHelper.createDate(2008, 3,
 *         20), draft);
 * contest.setName(&quot;Kyx Persistence&quot;);
 * contest.setEventId(1L);
 * contestManager.createContest(contest);
 * // Get the phases, get the contest data
 * Project project = manager.getPhases(17);
 * // the data of the contest is in the Project entity
 * // get the phases
 * Phase[] phases = project.getAllPhases();
 * // the phases contains two items: &quot;Draft&quot; and &quot;Scheduled&quot; phases
 * // these phases have the ids equal to the ContestStatus &quot;Draft&quot; and &quot;Scheduled&quot;
 * // get the attributes
 * Map attributes = project.getAttributes();
 * // get all phase types
 * PhaseType[] allPhaseTypes = manager.getAllPhaseTypes();
 * // get the all phase status
 * PhaseStatus[] phaseStatus = manager.getAllPhaseStatuses();
 * // Suppose that the scheduled phase is going to be managed.
 * Phase scheduledPhase = TestHelper.findPhaseByTypeName(phases, &quot;Scheduled&quot;);
 * // start the &quot;Scheduled&quot; phase
 * manager.start(scheduledPhase, &quot;TCS&quot;);
 * // end the &quot;Scheduled&quot; phase
 * manager.end(scheduledPhase, &quot;TCS&quot;);
 * </pre>
 *
 * </p>
 * <p>
 * A cache is used to cache the contest statuses: they change rarely, anyway it is optional. The logging is
 * optional. The class provides also the phase types name configurable: these fields has the default value
 * defined (it's the "initial value"). The class provide an empty constructor and the setters and getters for
 * the instance. This will be useful for a setter injection of the instances used by this class.
 * </p>
 * <p>
 * <strong>Thread-safety:</strong> The setters and getters are not thread safe, but it is supposed that they
 * are used in a single thread, when the manager is constructed. The register/unregister methods will be used
 * in a single thread too. The thread safety of the other methods depends from the thread safety of the
 * <code>ContestManager</code> and the cache interface. Supposing that they are thread safe (in their
 * documentation they are required to be thread safe) then this class is thread safe.
 * </p>
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.0
 */
public class CockpitPhaseManager implements PhaseManager {
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
    private static final String REPOST = "Repost";

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
     * Mapping from HandlerRegistryInfo keys to PhaseHandler values.
     * </p>
     * <p>
     * This is used to look up phase handlers when performing operations. It is not mutable and and its value
     * is an empty map. This map is filled in the constructor with the phase handlers and it is modified in
     * the register and unregister method or in the setter. The keys and the values can not be null.
     * </p>
     */
    private final Map<HandlerRegistryInfo, PhaseHandler> handlers = new HashMap<HandlerRegistryInfo, PhaseHandler>();

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
    private ContestManager contestManager;

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
    private Cache cachedContestStatuses;

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

    /**
     * <p>
     * This is the default constructor.
     * </p>
     */
    public CockpitPhaseManager() {
        // init all the phase type names with default value.
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

        initStatusMapping();
    }

    /**
     * <p>
     * Constructs the manager from the configuration. It load the handlers and the contest manager from the
     * configuration.
     * </p>
     * @param namespace the namespace used to load the configuration.
     * @throws IllegalArgumentException if <code>namespace</code> is <code>null</code> or an empty string.
     * @throws CockpitConfigurationException if any error occurs while loading the configuration values. This
     *         is including missing required property, invalid property format or value and invalid
     *         configuration in the namespace.
     * @throws CockpitPhaseManagementException if fails on getting object from JNDI, or if the bound object in
     *         JNDI for contestManagerName is not a type of ContestManager.
     */
    public CockpitPhaseManager(String namespace)
        throws CockpitConfigurationException, CockpitPhaseManagementException {
        checkNullOrEmpty(namespace, "namespace");

        // loads the configuration from the namespace
        loadConfiguration(namespace);

        // init the phaseType/contestStatus name from configuration
        // if it is not configured, the default value will be used.
        draftPhaseTypeName = getPropertyValue(namespace, "draftPhaseTypeName", DRAFT);
        scheduledPhaseTypeName = getPropertyValue(namespace, "scheduledPhaseTypeName", SCHEDULED);
        activePhaseTypeName = getPropertyValue(namespace, "activePhaseTypeName", ACTIVE);
        actionRequiredPhaseTypeName = getPropertyValue(namespace, "actionRequiredPhaseTypeName", ACTION_REQUIRED);
        inDangerPhaseTypeName = getPropertyValue(namespace, "inDangerPhaseTypeName", IN_DANGER);
        insufficientSubmissionsReRunPossiblePhaseTypeName = getPropertyValue(namespace,
                "insufficientSubmissionsReRunPossiblePhaseTypeName", INSUFFICIENT_SUBMISSIONS_RE_RUN_POSSIBLE);
        extendedPhaseTypeName = getPropertyValue(namespace, "extendedPhaseTypeName", EXTENDED);
        repostPhaseTypeName = getPropertyValue(namespace, "repostPhaseTypeName", REPOST);
        insufficientSubmissionsPhaseTypeName = getPropertyValue(namespace, "insufficientSubmissionsPhaseTypeName",
                INSUFFICIENT_SUBMISSIONS);
        noWinnerChosenPhaseTypeName = getPropertyValue(namespace, "noWinnerChosenPhaseTypeName", NO_WINNER_CHOSEN);
        completedPhaseTypeName = getPropertyValue(namespace, "completedPhaseTypeName", COMPLETED);
        abandonedPhaseTypeName = getPropertyValue(namespace, "abandonedPhaseTypeName", ABANDONED);
        cancelledPhaseTypeName = getPropertyValue(namespace, "cancelledPhaseTypeName", CANCELLED);

        initStatusMapping();
    }

    /**
     * <p>
     * Creates the manager from specified ContestManager, log name and the cache. If the log name is not
     * <code>null</code>, then it is used to obtain a log from <code>LogManager</code>, otherwise no
     * logging is performed in this manager.
     * </p>
     * @param contestManager the contest manager which will be used for the contest's operations.
     * @param logName the name of log, can be <code>null</code>.
     * @param cacheContestStatuses the cache which will be used for the contestStatuses, can be
     *        <code>null</code>.
     * @throws IllegalArgumentException if <code>contestManager</code> is <code>null</code>, or if
     *         <code>logName</code> is empty.
     */
    public CockpitPhaseManager(ContestManager contestManager, String logName, Cache cacheContestStatuses) {
        this();
        checkNull(contestManager, "contestManager");
        this.contestManager = contestManager;

        if (logName != null) {
            checkEmpty(logName, "logName should not be empty");
            log = LogManager.getLog(logName);
        }

        this.cachedContestStatuses = cacheContestStatuses;
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
                                        ContestProperty property = config.getProperty();

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

                            project.setAttribute(formatAttributeName(fieldName), (Serializable) fieldValue);
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
        }

        return project;
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
        this.contestManager = contestManager;
    }

    /**
     * <p>
     * Sets the contestManager using JNDI.
     * </p>
     * <p>
     * In this case a JNDIUtil instance is created with default namespace <code>JNDIUtils#NAMESPACE</code>
     * </p>
     * @param contestManagerName the contestManager's name used to retrieve the contest manager
     * @throws IllegalArgumentException if <code>contestManagerName</code> is <code>null</code> or an
     *         empty string.
     * @throws CockpitPhaseManagementException if any error occurs while creating JNDIUtil or while retrieving
     *         <code>ContestManager</code> from a JNDI context.
     */
    public void setContestManager(String contestManagerName)
        throws CockpitPhaseManagementException {
        checkNullOrEmpty(contestManagerName, "contestManagerName");

        try {
            createContestManager(contestManagerName, null);
        } catch (CockpitConfigurationException e) {
            throw new CockpitPhaseManagementException(e.getMessage());
        }
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
        return cachedContestStatuses;
    }

    /**
     * <p>
     * Sets the cache used for ContestStatus instances.
     * </p>
     * @param cachedContestStatuses the cache used for ContestStatus instances, can be <code>null</code>.
     */
    public void setCachedContestStatuses(Cache cachedContestStatuses) {
        this.cachedContestStatuses = cachedContestStatuses;
    }

    /**
     * <p>
     * Returns the handlers map. A shallow copy is returned.
     * </p>
     * @return the handlers.
     */
    public Map<HandlerRegistryInfo, PhaseHandler> getHandlers() {
        return new HashMap<HandlerRegistryInfo, PhaseHandler>(handlers);
    }

    /**
     * <p>
     * Set the handlers. A shallow copy is used.
     * </p>
     * @param handlers the handlers to set.
     * @throws IllegalArgumentException if <code>handlers</code> is <code>null</code>, or if
     *         <code>handlers</code> contains <code>null</code> keys or values.
     */
    public void setHandlers(Map<HandlerRegistryInfo, PhaseHandler> handlers) {
        checkNull(handlers, "handlers");

        this.handlers.clear();

        for (Map.Entry<HandlerRegistryInfo, PhaseHandler> entry : handlers.entrySet()) {
            HandlerRegistryInfo key = entry.getKey();
            checkNull(key, "key in handlers");

            PhaseHandler value = entry.getValue();
            checkNull(value, "value in handlers");
            this.handlers.put(key, value);
        }
    }

    /**
     * <p>
     * This helper method loads needed configuration values to create the ContestManager instance, the phase
     * handler and the optional Log and cache.
     * </p>
     * @param namespace the namespace used to load the configuration.
     * @throws CockpitConfigurationException if any error occurs while loading the configuration values. This
     *         is including missing required property, invalid property format or value and invalid
     *         configuration in the namespace.
     * @throws CockpitPhaseManagementException if fails on getting object from JNDI, or if the bound object in
     *         JNDI for contestManagerName is not a type of ContestManager.
     */
    private void loadConfiguration(String namespace)
        throws CockpitConfigurationException, CockpitPhaseManagementException {
        // gets 'objectFactoryNamespace' property value
        // and creates the ObjectFactory instance
        String objectFactoryNamespace = getPropertyValue(namespace, "objectFactoryNamespace", null);
        ObjectFactory objectFactory = createObjectFactory(objectFactoryNamespace);

        // gets 'contestManagerName' property value.
        String contestManagerName = getPropertyValue(namespace, "contestManagerName", "");

        // if it is specified, create ContestManager via JNDI
        if (contestManagerName.length() > 0) {
            String jndiUtilNamespace = getPropertyValue(namespace, "jndiUtilNamespace", JNDIUtils.NAMESPACE);
            createContestManager(contestManagerName, jndiUtilNamespace);
        } else {
            // otherwise, use ObjectFactory to create the ContestManager
            String contestManagerKey = getPropertyValue(namespace, "contestManagerKey", null);
            contestManager = (ContestManager) createObject(objectFactory, contestManagerKey, ContestManager.class);
        }

        // creates the Log if it is configured
        String logName = getPropertyValue(namespace, "logName", "");

        if (logName.length() > 0) {
            log = LogManager.getLog(logName);
        }

        // creates the Cache if it is configured
        String cachedContestStatusesKey = getPropertyValue(namespace, "cachedContestStatusesKey", "");

        if (cachedContestStatusesKey.length() > 0) {
            cachedContestStatuses = (Cache) createObject(objectFactory, cachedContestStatusesKey, Cache.class);
        }

        // loads phase handlers configuration values
        loadHandlers(namespace, objectFactory);
    }

    /**
     * <p>
     * This helper method obtains ContestManager instance from a JNDI context using JNDIUtil.
     * </p>
     * @param contestManagerName the name of the ContestManager to look up in initial context.
     * @param jndiUtilNamespace the JNDIUtil configuration namespace. If it is null, then JNDIUtils#NAMESPACE
     *        is used.
     * @throws CockpitConfigurationException if any error occurs when creating JNDIUtil from configuration.
     * @throws CockpitPhaseManagementException if fails on getting object from JNDI, or if the bound object in
     *         JNDI for contestManagerName is not a type of ContestManager.
     */
    private void createContestManager(String contestManagerName, String jndiUtilNamespace)
        throws CockpitConfigurationException, CockpitPhaseManagementException {
        // creates JNDIUtil instance
        JNDIUtil util = createJNDIUtil(jndiUtilNamespace);

        try {
            // retrieves ContestManager from context
            Object value = util.getObject(contestManagerName);

            // verifies if it is a ContestManager instance
            if (!(value instanceof ContestManager)) {
                throw new CockpitPhaseManagementException("Expecting object in a type of "
                    + ContestManager.class.getName() + " bound to the name '" + contestManagerName
                    + "' in JNDI context, but found '" + (value == null ? null : value.getClass().getName()) + "'");
            }

            contestManager = (ContestManager) value;
        } catch (NamingException e) {
            throw new CockpitPhaseManagementException("A naming exception is encountered"
                + " while retrieving contest manager bean from jndi context", e);
        }
    }

    /**
     * <p>
     * This helpers method loads all configured handlers specified in property 'handlers' in configuration.
     * </p>
     * @param namespace the namespace used to load the configuration.
     * @param objectFactory the ObjectFactory instance to create PhaseHandler.
     * @throws CockpitConfigurationException if 'handlers' property is missing from configuration, or if
     *         'handlers' property is empty, or if it contains invalid sub-property format or values.
     */
    private void loadHandlers(String namespace, ObjectFactory objectFactory)
        throws CockpitConfigurationException {
        // gets 'handlers' property
        Property handlersProp = getHandlersPropertyObject(namespace);

        // iterates all sub-properties
        for (Iterator<?> iter = handlersProp.list().iterator(); iter.hasNext();) {
            Property handlerProp = (Property) iter.next();

            // creates the HandlerRegistryInfo based on property name
            String handlerPropName = handlerProp.getName();
            HandlerRegistryInfo info = createHandlerRegistryInfo(handlerPropName);

            // creates the PhaseHandler based on property value.
            String handlerPropValue = handlerProp.getValue();

            if (handlerPropValue.trim().length() == 0) {
                throw new CockpitConfigurationException("The value of '" + handlerPropName
                    + "' should not be empty");
            }

            PhaseHandler phaseHandler = (PhaseHandler) createObject(objectFactory, handlerPropValue,
                    PhaseHandler.class);

            // put the mapping to the handlers.
            handlers.put(info, phaseHandler);
        }
    }

    /**
     * <p>
     * This helper methods parses the property name and creates HandlerRegistryInfo instance.
     * </p>
     * @param propName the property name to be parsed as HandlerRegistryInfo parameters.
     * @return HandlerRegistryInfo instance.
     * @throws CockpitConfigurationException if property name has invalid format or value.
     */
    private HandlerRegistryInfo createHandlerRegistryInfo(String propName)
        throws CockpitConfigurationException {
        // compile the regex pattern for
        // 'phaseTypeId,phaseTypeName,phaseOperationEnumName' format.
        Pattern pattern = Pattern.compile("^(\\d+),(\\w+),(\\w+)$");

        // create matcher
        Matcher matcher = pattern.matcher(propName);

        if (matcher.find()) {
            // creates PhaseType instance
            long phaseTypeId = Long.parseLong(matcher.group(1));
            PhaseType phaseType = new PhaseType(phaseTypeId, matcher.group(2));

            // creates HandlerRegistryInfo instance with the phase type and
            // corresponding phase operation
            String opName = matcher.group(3);

            if (PhaseOperationEnum.START.getName().equalsIgnoreCase(opName)) {
                return new HandlerRegistryInfo(phaseType, PhaseOperationEnum.START);
            } else if (PhaseOperationEnum.END.getName().equalsIgnoreCase(opName)) {
                return new HandlerRegistryInfo(phaseType, PhaseOperationEnum.END);
            } else if (PhaseOperationEnum.CANCEL.getName().equalsIgnoreCase(opName)) {
                return new HandlerRegistryInfo(phaseType, PhaseOperationEnum.CANCEL);
            } else {
                // the phase operation is unknown
                throw new CockpitConfigurationException("The third field of child property name '" + propName
                    + "' in 'handlers' property should be a name defined in '" + PhaseOperationEnum.class
                    + "', [start, end or cancel]");
            }
        } else {
            throw new CockpitConfigurationException(
                "A child property name in 'handlers' property should be in format of "
                + "'phaseTypeId,phaseTypeName,phaseOperationEnumName' where phaseTypeId is not negative long value");
        }
    }

    /**
     * <p>
     * Gets a property value from configuration namespace under given property name.
     * </p>
     * <p>
     * When the given defaultValue argument is null, it means the property is required, otherwise it means the
     * property is optional.
     * </p>
     * <p>
     * If the property is missing but the property is optional, the default value is returned.
     * </p>
     * @param namespace the configuration namespace.
     * @param propertyName the name of property to get the value.
     * @param defaultValue the default value of the property. Null value indicates that the property is
     *        required.
     * @return a String representing property value.
     * @throws CockpitConfigurationException if a required property is not found in config file, or if a
     *         property has an empty String value, or if any error occurs while reading property values from
     *         configuration file.
     */
    private String getPropertyValue(String namespace, String propertyName, String defaultValue)
        throws CockpitConfigurationException {
        try {
            String prop = ConfigManager.getInstance().getString(namespace, propertyName);

            // Property is missing
            if (prop == null) {
                // the property is required
                if (defaultValue == null) {
                    throw new CockpitConfigurationException("Error in configuration. Missing required property '"
                        + propertyName + "'");
                } else {
                    // the property is optional
                    return defaultValue;
                }
            }

            // Empty property value is not allowed
            if (prop.trim().length() == 0) {
                throw new CockpitConfigurationException("Error in configuration. Property value: '" + propertyName
                    + "' is empty");
            }

            return prop;
        } catch (UnknownNamespaceException e) {
            throw new CockpitConfigurationException("The namespace '" + namespace
                + "' is missing in configuration", e);
        }
    }

    /**
     * <p>
     * Creates ObjectFactory instance with given ConfigManagerSpecificationFactory namespace.
     * </p>
     * @param objectFactoryNamespace the namespace of ConfigManagerSpecificationFactory.
     * @return the ObjectFactory instance.
     * @throws CockpitConfigurationException if any error occurs while creating ObjectFactory instance.
     */
    private ObjectFactory createObjectFactory(String objectFactoryNamespace)
        throws CockpitConfigurationException {
        try {
            return new ObjectFactory(new ConfigManagerSpecificationFactory(objectFactoryNamespace));
        } catch (IllegalReferenceException e) {
            throw new CockpitConfigurationException(
                "IllegalReferenceException occurs while creating ObjectFactory instance using namespace "
                + objectFactoryNamespace, e);
        } catch (SpecificationConfigurationException e) {
            throw new CockpitConfigurationException(
                "SpecificationConfigurationException occurs while creating ObjectFactory instance using namespace "
                + objectFactoryNamespace, e);
        }
    }

    /**
     * <p>
     * This helper method creates JNDIUtil instance using specified configuration namespace. If the given
     * namespace is null then JNDIUtils#NAMESPACE is used.
     * </p>
     * @param jndiUtilNamespace the config namespace for JNDIUtil.
     * @return JNDIUtil instance.
     * @throws CockpitConfigurationException if any error occurs when creating JNDIUtil.
     */
    private JNDIUtil createJNDIUtil(String jndiUtilNamespace)
        throws CockpitConfigurationException {
        try {
            return jndiUtilNamespace == null ? new JNDIUtil() : new JNDIUtil(jndiUtilNamespace);
        } catch (ConfigurationException e) {
            throw new CockpitConfigurationException("Fails to create JNDIUtil", e);
        }
    }

    /**
     * <p>
     * Creates object using ObjectFactory for given object key.
     * </p>
     * @param objectFactory the ObjectFactory instance.
     * @param objectKey the object key.
     * @param type the expected Class type for the created object. Used for validation.
     * @return an Object created by ObjectFactory component.
     * @throws CockpitConfigurationException if any error occurs while creating an object using ObjectFactory.
     */
    private Object createObject(ObjectFactory objectFactory, String objectKey, Class<?> type)
        throws CockpitConfigurationException {
        try {
            Object obj = objectFactory.createObject(objectKey);

            if (!type.isInstance(obj)) {
                throw new CockpitConfigurationException("The configured object for key [" + objectKey
                    + "] is not an instance of " + type.getName());
            }

            return obj;
        } catch (InvalidClassSpecificationException e) {
            throw new CockpitConfigurationException(
                "InvalidClassSpecificationException occurs while creating object with key [" + objectKey
                + "] using object factory", e);
        }
    }

    /**
     * <p>
     * Gets the handlers property from configuration namespace as Property object.
     * </p>
     * @param namespace the configuration namespace.
     * @return a Property object representing handlers property in configuration.
     * @throws CockpitConfigurationException if any exception occurs while retrieving property, this will
     *         include unknown namespace.
     */
    private Property getHandlersPropertyObject(String namespace)
        throws CockpitConfigurationException {
        Property handlersProp;

        try {
            handlersProp = ConfigManager.getInstance().getPropertyObject(namespace, "handlers");
        } catch (UnknownNamespaceException e) {
            throw new CockpitConfigurationException("Fails on reading handlers from unknown namespace '"
                + namespace + "'", e);
        }

        if (handlersProp == null) {
            throw new CockpitConfigurationException("Missing 'handlers' property from namespace '" + namespace
                + "'");
        }

        return handlersProp;
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
            PhaseHandler handler = handlers.get(info);

            if (handler != null) {
                // find a ContestStatus which is the phase type is mapped to.
                ContestStatus contestStatus = findContestStatusByPhaseType(getAllContestStatuses(), phaseType);

                // updates the contest status
                logInfo("updating the contest status", phase.getId(), phaseType.getName(), projectId, true);
                contestManager.updateContestStatus(projectId, contestStatus.getContestStatusId());

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
     * Fills the status mapping which is used to hold the mapping between ContestStatus name to PhaseType name
     * as specified in class documentation.
     * </p>
     */
    private void initStatusMapping() {
        statusMapping.put(draftPhaseTypeName, new String[] {scheduledPhaseTypeName});
        statusMapping.put(scheduledPhaseTypeName, new String[] {activePhaseTypeName});
        statusMapping.put(activePhaseTypeName,
            new String[] {actionRequiredPhaseTypeName, insufficientSubmissionsReRunPossiblePhaseTypeName});
        statusMapping.put(actionRequiredPhaseTypeName, new String[] {completedPhaseTypeName, inDangerPhaseTypeName});
        statusMapping.put(inDangerPhaseTypeName, new String[] {completedPhaseTypeName, abandonedPhaseTypeName});
        statusMapping.put(insufficientSubmissionsReRunPossiblePhaseTypeName,
            new String[] {extendedPhaseTypeName, abandonedPhaseTypeName});
        statusMapping.put(extendedPhaseTypeName,
            new String[] {actionRequiredPhaseTypeName, insufficientSubmissionsPhaseTypeName});
        statusMapping.put(repostPhaseTypeName,
            new String[] {actionRequiredPhaseTypeName, insufficientSubmissionsReRunPossiblePhaseTypeName});
        statusMapping.put(insufficientSubmissionsPhaseTypeName,
            new String[] {cancelledPhaseTypeName, abandonedPhaseTypeName});
        statusMapping.put(noWinnerChosenPhaseTypeName,
            new String[] {cancelledPhaseTypeName, abandonedPhaseTypeName, repostPhaseTypeName});
    }
}
