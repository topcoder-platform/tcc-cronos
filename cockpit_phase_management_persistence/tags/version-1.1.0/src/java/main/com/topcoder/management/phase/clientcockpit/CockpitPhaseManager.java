/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit;

import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.phase.PhaseManagementException;
import com.topcoder.management.phase.PhaseOperationEnum;
import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.management.phase.HandlerRegistryInfo;
import com.topcoder.management.phase.PhaseValidator;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;
import com.topcoder.util.config.Property;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.log.Log;
import com.topcoder.util.cache.Cache;
import com.topcoder.project.phases.Project;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.Phase;
import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.naming.jndiutility.JNDIUtil;
import com.topcoder.naming.jndiutility.ConfigurationException;

import javax.naming.NamingException;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.TextOutputCallback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.rmi.PortableRemoteObject;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.IOException;

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

    private CockpitPhaseManagerInterface delegate;

    /**
     * <p>
     * This is the default constructor.
     * </p>
     */
    public CockpitPhaseManager() {
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
        // loads the configuration from the namespace
        String username = getPropertyValue(namespace, "EJBContextUsername", null);
        String password = getPropertyValue(namespace, "EJBContextPassword", null);
        String clientLoginDomain = getPropertyValue(namespace, "ClientLoginAppPolicyName", null);
        try {
            LoginContext loginContext = new LoginContext(clientLoginDomain, new Subject(),
                                                         new CallbackHandlerImpl(username, password));
            loginContext.login();
        } catch (LoginException e) {
            e.printStackTrace();
            throw new CockpitPhaseManagementException("Could not establish security context", e);
        }

        loadConfiguration(namespace);
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
//    public CockpitPhaseManager(ContestManager contestManager, String logName, Cache cacheContestStatuses) {
//        this();
////        checkNull(contestManager, "contestManager");
//        this.contestManager = contestManager;
//
//        if (logName != null) {
//            checkEmpty(logName, "logName should not be empty");
//            log = LogManager.getLog(logName);
//        }
//
//        this.cachedContestStatuses = cacheContestStatuses;
//    }

    /**
     * <p>
     * Synchronizes the current state of the specified project with persistent storage. This method does
     * nothing.
     * </p>
     * @param project project for which to update phases. It is unused.
     * @param operator the operator performing the action. It is unused.
     */
    public void updatePhases(Project project, String operator) throws PhaseManagementException {
        this.delegate.updatePhases(project, operator);
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
    public Project getPhases(long contestId) throws PhaseManagementException {
        return this.delegate.getPhases(contestId);
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
    public Project[] getPhases(long[] contestIds) throws PhaseManagementException {
        return this.delegate.getPhases(contestIds);
    }

    /**
     * <p>
     * Returns an array of all contest statuses mapped to phase types.
     * </p>
     * @return an array of all contest statuses mapped to phase types.
     * @throws CockpitPhaseManagementException if any error occurs while retrieving contest status from
     *         contest manager.
     */
    public PhaseType[] getAllPhaseTypes() throws PhaseManagementException {
        return this.delegate.getAllPhaseTypes();
    }

    /**
     * <p>
     * Returns an array of all phase statuses which can be used by a phase.
     * </p>
     * @return an array of all phase statuses which can be used by a phase.
     */
    public PhaseStatus[] getAllPhaseStatuses() throws PhaseManagementException {
        return this.delegate.getAllPhaseStatuses();
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
    public boolean canStart(Phase phase) throws PhaseManagementException {
        return this.delegate.canStart(phase);
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
    public void start(Phase phase, String operator) throws PhaseManagementException {
        this.delegate.start(phase, operator);
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
    public boolean canEnd(Phase phase) throws PhaseManagementException {
        return this.delegate.canEnd(phase);
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
    public void end(Phase phase, String operator) throws PhaseManagementException {
        this.delegate.end(phase, operator);
    }

    /**
     * <p>
     * Determines whether it is possible to cancel the specified phase. This method always returns
     * <code>false</code>.
     * </p>
     * @param phase a phase to test. This is unused.
     * @return <code>false</code>.
     */
    public boolean canCancel(Phase phase) throws PhaseManagementException {
        return this.delegate.canCancel(phase);
    }

    /**
     * <p>
     * Cancels the specified phase. This method does nothing.
     * </p>
     * @param phase the phase to end. This is unused.
     * @param operator the operator of the operation. This is unused.
     */
    public void cancel(Phase phase, String operator) throws PhaseManagementException {
        this.delegate.cancel(phase, operator);
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
        this.delegate.registerHandler(handler, type, operation);
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
        return this.delegate.unregisterHandler(type, operation);
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
        return this.delegate.getAllHandlers();
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
        return this.delegate.getHandlerRegistrationInfo(handler);
    }

    /**
     * <p>
     * Sets the current phase validator for this manager. This method does nothing.
     * </p>
     * @param phaseValidator the validator to use for this manager. This is unused.
     */
    public void setPhaseValidator(PhaseValidator phaseValidator) {
        this.delegate.setPhaseValidator(phaseValidator);
    }

    /**
     * <p>
     * Returns the current phase validator. This method always returns <code>null</code>.
     * </p>
     * @return <code>null</code>.
     */
    public PhaseValidator getPhaseValidator() {
        return this.delegate.getPhaseValidator();
    }

    /**
     * <p>
     * Returns the contest manager which is configured or set in this manager.
     * </p>
     * @return the contest manager in this manager.
     */
    public ContestManager getContestManager() {
        return this.delegate.getContestManager();
    }

    /**
     * <p>
     * Sets the contest manager to be used in this manager.
     * </p>
     * @param contestManager the contestManager to set.
     * @throws IllegalArgumentException if <code>contestManager</code> is <code>null</code>.
     */
    public void setContestManager(ContestManager contestManager) {
        this.delegate.setContestManager(contestManager);
    }

    /**
     * <p>
     * Returns the log used by this manager.
     * </p>
     * @return the possibly <code>null</code> log used by this manager.
     */
    public Log getLog() {
        return this.delegate.getLog();
    }

    /**
     * <p>
     * Sets the log. It can be null which means no logging is performed.
     * </p>
     * @param log the log to set, can be <code>null</code>.
     */
    public void setLog(Log log) {
        this.delegate.setLog(log);
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
        this.delegate.setLog(logName);
    }

    /**
     * <p>
     * Returns the cache used for ContestStatus instances. If it is not null and not empty, the cache will
     * contain 1 object with key "contestStatuses".
     * </p>
     * @return the possibly <code>null</code> cache used for ContestStatuses instances.
     */
    public Cache getCachedContestStatuses() {
        return this.delegate.getCachedContestStatuses();
    }

    /**
     * <p>
     * Sets the cache used for ContestStatus instances.
     * </p>
     * @param cachedContestStatuses the cache used for ContestStatus instances, can be <code>null</code>.
     */
    public void setCachedContestStatuses(Cache cachedContestStatuses) {
        this.delegate.setCachedContestStatuses(cachedContestStatuses);
    }

    /**
     * <p>
     * Returns the handlers map. A shallow copy is returned.
     * </p>
     * @return the handlers.
     */
    public Map<HandlerRegistryInfo, PhaseHandler> getHandlers() {
        return this.delegate.getHandlers();
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
        this.delegate.setHandlers(handlers);
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
            delegate = (CockpitPhaseManagerInterface) createObject(objectFactory, contestManagerKey, ContestManager.class);
        }

        // creates the Log if it is configured
        String logName = getPropertyValue(namespace, "logName", "");

        if (logName.length() > 0) {
//            this.delegate.setLog(LogManager.getLog(logName));
        }

        // creates the Cache if it is configured
        String cachedContestStatusesKey = getPropertyValue(namespace, "cachedContestStatusesKey", "");

        if (cachedContestStatusesKey.length() > 0) {
//            this.delegate.setCachedContestStatuses((Cache) createObject(objectFactory, cachedContestStatusesKey, Cache.class));
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

            Object value2 = PortableRemoteObject.narrow(value, CockpitPhaseManagerInterface.class);

            // verifies if it is a ContestManager instance
            if (!(value2 instanceof CockpitPhaseManagerInterface)) {
                throw new CockpitPhaseManagementException("Expecting object in a type of "
                    + ContestManager.class.getName() + " bound to the name '" + contestManagerName
                    + "' in JNDI context, but found '" + (value2 == null ? null : value2.getClass().getName()) + "'");
            }

            delegate = (CockpitPhaseManagerInterface) value;
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
            this.delegate.registerHandler(phaseHandler, info.getType(), info.getOperation());
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
        Pattern pattern = Pattern.compile("^(\\d+),([[\\w*][\\W*]]+),(\\w+)$");

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
     * <p>A callback handler which always provides the same username/password pair (as set by initial parameters of
     * <code>Cockpit</code> web application context) for authenticating to EJB container.</p>
     *
     * @author isv
     * @version 1.0
     */
    private static class CallbackHandlerImpl implements CallbackHandler {

        /**
         * <p>A <code>String</code> providing the username to be used for authenticating to EJB container.</p>
         */
        private final String username;

        /**
         * <p>A <code>String</code> providing the password to be used for authenticating to EJB container.</p>
         */
        private final String password;

        /**
         * <p>Constructs new <code>CallbackHandlerImpl</code> instance to be set the callbacks with specified user
         * name and password.</p>
         *
         * @param username a <code>String</code> providing the username to be used for authenticating to EJB container.
         * @param password a <code>String</code> providing the password to be used for authenticating to EJB container.
         */
        private CallbackHandlerImpl(String username, String password) {
            this.username = username;
            this.password = password;
        }

        /**
         * <p>Handles the callbacks. Sets the username and password to be used for autthenticating to EJB container.</p>
         *
         * @param callbacks an array of <code>Callback</code> objects provided by an underlying security service which
         *        contains the information requested to be retrieved or displayed.
         * @throws IOException if an input or output error occurs.
         * @throws UnsupportedCallbackException if the implementation of this method does not support one or more of the
         *         callbacks specified in the <code>callbacks</code> parameter.
         */
        public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
            for (int i = 0; i < callbacks.length; i++) {
                if (callbacks[i] instanceof TextOutputCallback) {
                } else if (callbacks[i] instanceof NameCallback) {
                    // prompt the user for a username
                    NameCallback nc = (NameCallback) callbacks[i];
                    nc.setName(this.username);
                } else if (callbacks[i] instanceof PasswordCallback) {
                    // prompt the user for sensitive information
                    PasswordCallback pc = (PasswordCallback) callbacks[i];
                    pc.setPassword(this.password.toCharArray());
                } else {
                    throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
                }
            }
        }
    }
}
