/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.digitalrun.entity.TrackContest;
import com.topcoder.service.digitalrun.entity.TrackContestResultCalculator;
import com.topcoder.service.digitalrun.entity.TrackContestType;
import com.topcoder.util.errorhandling.BaseException;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigurationObjectSpecificationFactory;


/**
 * <p>
 * This bean class implements the <code>DigitalRunContestManagerLocal</code> as well as the
 * <code>DigitalRunContestManagerRemote</code> interface to provide the specific <code>
 * DigitalRunContestManager</code> functionality.
 * </p>
 *
 * <p>
 * It is a SLSB with "@Stateless" annotation. The transaction is managed by the container.
 * </p>
 *
 * <p>
 * This bean is nothing more than a transactional delegate and all the CRUD operations are
 * done by the specialized DAOs.
 * </p>
 *
 * <p>
 *     <strong>Logging:</strong>
 *     All defined operations will be logged. Logging strategy:
 *     <ol>
 *         <li>Entrance and exit of methods will be logged at the INFO level</li>
 *         <li>Exception will be logged at the ERROR level</li>
 *     </ol>
 * </p>
 *
 * <p>
 *     <strong>Sample ejb-jar.xml:</strong>
 *     <pre>
 *    &lt;session&gt;
 *      &lt;ejb-name&gt;DigitalRunContestManagerBean&lt;/ejb-name&gt;
 *      &lt;remote&gt;com.topcoder.service.digitalrun.contest.DigitalRunContestManagerRemote&lt;/remote&gt;
 *      &lt;local&gt;com.topcoder.service.digitalrun.contest.DigitalRunContestManagerLocal&lt;/local&gt;
 *      &lt;ejb-class&gt;com.topcoder.service.digitalrun.contest.DigitalRunContestManagerBean&lt;/ejb-class&gt;
 *      &lt;session-type&gt;Stateless&lt;/session-type&gt;
 *      &lt;transaction-type&gt;Container&lt;/transaction-type&gt;
 *      &lt;env-entry&gt;
 *        &lt;env-entry-name&gt;unit_name&lt;/env-entry-name&gt;
 *        &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *        &lt;env-entry-value&gt;HibernateDRContestPersistence&lt;/env-entry-value&gt;
 *      &lt;/env-entry&gt;
 *      &lt;env-entry&gt;
 *        &lt;env-entry-name&gt;config_file_name&lt;/env-entry-name&gt;
 *        &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *        &lt;env-entry-value&gt;config.properties&lt;/env-entry-value&gt;
 *      &lt;/env-entry&gt;
 *      &lt;env-entry&gt;
 *        &lt;env-entry-name&gt;config_namespace&lt;/env-entry-name&gt;
 *        &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *        &lt;env-entry-value&gt;DigitalRunContestManagerBean&lt;/env-entry-value&gt;
 *      &lt;/env-entry&gt;
 *      &lt;persistence-context-ref&gt;
 *          &lt;persistence-context-ref-name&gt;
 *              HibernateDRContestPersistence
 *          &lt;/persistence-context-ref-name&gt;
 *          &lt;persistence-unit-name&gt;HibernateDRContestPersistence&lt;/persistence-unit-name&gt;
 *          &lt;persistence-context-type&gt;Transaction&lt;/persistence-context-type&gt;
 *      &lt;/persistence-context-ref&gt;
 *    &lt;/session&gt;
 *     </pre>
 * </p>
 *
 * <p>
 *     <strong>Sample configuration:</strong>
 *     <pre>
 *      &lt;Property name="log_name"&gt;
 *            &lt;Value&gt;logger&lt;/Value&gt;
 *        &lt;/Property&gt;
 *
 *        &lt;Property name="track_contest_dao_token"&gt;
 *            &lt;Value&gt;TrackContestDAO&lt;/Value&gt;
 *        &lt;/Property&gt;
 *        &lt;Property name="track_contest_type_dao_token"&gt;
 *            &lt;Value&gt;TrackContestTypeDAO&lt;/Value&gt;
 *        &lt;/Property&gt;
 *        &lt;Property name="result_calculator_dao_token"&gt;
 *            &lt;Value&gt;ResultCalculatorDAO&lt;/Value&gt;
 *        &lt;/Property&gt;
 *
 *        &lt;Property name="object_factory_config"&gt;
 *            &lt;Property name="TrackContestDAO"&gt;
 *                &lt;Property name="type"&gt;
 *                    &lt;Value&gt;
 *                    com.topcoder.service.digitalrun.contest.persistence.HibernateContestTrackDAO
 *                    &lt;/Value&gt;
 *                &lt;/Property&gt;
 *                &lt;Property name="params"&gt;
 *                    &lt;Property name="param1"&gt;
 *                      &lt;Property name="type"&gt;
 *                        &lt;Value&gt;String&lt;/Value&gt;
 *                      &lt;/Property&gt;
 *                      &lt;Property name="value"&gt;
 *                        &lt;Value&gt;DRSearchBundleManager&lt;/Value&gt;
 *                      &lt;/Property&gt;
 *                    &lt;/Property&gt;
 *                    &lt;Property name="param2"&gt;
 *                      &lt;Property name="type"&gt;
 *                        &lt;Value&gt;String&lt;/Value&gt;
 *                      &lt;/Property&gt;
 *                      &lt;Property name="value"&gt;
 *                        &lt;Value&gt;TrackContestSearch&lt;/Value&gt;
 *                      &lt;/Property&gt;
 *                    &lt;/Property&gt;
 *                &lt;/Property&gt;
 *            &lt;/Property&gt;
 *            &lt;Property name="TrackContestTypeDAO"&gt;
 *                &lt;Property name="type"&gt;
 *                    &lt;Value&gt;
 *                    com.topcoder.service.digitalrun.contest.persistence.HibernateTrackContestTypeDAO
 *                    &lt;/Value&gt;
 *                &lt;/Property&gt;
 *            &lt;/Property&gt;
 *            &lt;Property name="ResultCalculatorDAO"&gt;
 *                &lt;Property name="type"&gt;
 *                    &lt;Value&gt;
 *                    com.topcoder.service.digitalrun.contest.persistence.HibernateTrackContestResultCalculatorDAO
 *                    &lt;/Value&gt;
 *                &lt;/Property&gt;
 *            &lt;/Property&gt;
 *        &lt;/Property&gt;
 *     </pre>
 * </p>
 *
 * <p>
 *     <strong>Sample Code:</strong>
 *     <pre>
 *        // lookup the bean from JNDI
 *        DigitalRunContestManagerRemote manager = (DigitalRunContestManagerRemote)
 *            new InitialContext().lookup(EAR_NAME + "/DigitalRunContestManagerBean/remote");
 *
 *        // create a new contest type
 *        TrackContestType entity = new TrackContestType();
 *        entity.setDescription("description");
 *        TrackContestType contestType = manager.createTrackContestType(entity);
 *        // get contest type
 *        contestType = manager.getTrackContestType(contestType.getId());
 *        // update contest type
 *        manager.updateTrackContestType(contestType);
 *
 *        // get all contest types
 *        List < TrackContestType > contestTypes = manager.getAllTrackContestTypes();
 *
 *        // create a new calculator
 *        TrackContestResultCalculator rc = new TrackContestResultCalculator();
 *        rc.setClassName("className");
 *        rc.setDescription("description");
 *        TrackContestResultCalculator calculator = manager.createTrackContestResultCalculator(rc);
 *        // get calculator
 *        calculator = manager.getTrackContestResultCalculator(calculator.getId());
 *        // update calculator
 *        manager.updateTrackContestResultCalculator(calculator);
 *
 *        // get all calculators
 *        List < TrackContestResultCalculator > calculators = manager.getAllTrackContestResultCalculators();
 *
 *        // create a new contest
 *        TrackContest contest = new TrackContest();
 *        contest.setDescription("description");
 *        contest.setTrack(track);
 *        contest.setTrackContestResultCalculator(calculator);
 *        contest.setTrackContestType(contestType);
 *        contest = manager.createTrackContest(contest);
 *        // get contest
 *        contest = manager.getTrackContest(contest.getId());
 *        // update contest
 *        manager.updateTrackContest(contest);
 *
 *        // delete contest
 *        manager.removeTrackContest(contest.getId());
 *
 *        // delete contest type
 *        manager.removeTrackContestType(contestType.getId());
 *
 *        // delete calculator
 *        manager.removeTrackContestResultCalculator(calculator.getId());
 *     </pre>
 * </p>
 *
 * <p>
 *     <strong>Thread Safety:</strong>
 *     The variables in this class are initialized once in the initialize method after the
 *     bean is instantiated by EJB container. They would be never be changed afterwards.
 *     So they won't affect the thread-safety of this class when its EJB methods are called.
 *     So this class will be thread-safe in EJB container.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
@Stateless
public class DigitalRunContestManagerBean
    implements DigitalRunContestManagerRemote, DigitalRunContestManagerLocal {

    /**
     * <p>
     * The class name.
     * </p>
     */
    private static final String CLASS_NAME = DigitalRunContestManagerBean.class.getSimpleName();

    /**
     * <p>
     * The "object_factory_config" configuration child.
     * </p>
     */
    private static final String OBJECT_FACTORY_CONFIG = "object_factory_config";

    /**
     * <p>
     * The "log_name" configuration property.
     * </p>
     */
    private static final String LOG_NAME = "log_name";

    /**
     * <p>
     * The "track_contest_dao_token" configuration property.
     * </p>
     */
    private static final String TRACK_CONTEST_DAO_TOKEN = "track_contest_dao_token";

    /**
     * <p>
     * The "track_contest_type_dao_token" configuration property.
     * </p>
     */
    private static final String TRACK_CONTEST_TYPE_DAO_TOKEN = "track_contest_type_dao_token";

    /**
     * <p>
     * The "result_calculator_dao_token" configuration property.
     * </p>
     */
    private static final String RESULT_CALCULATOR_DAO_TOKEN = "result_calculator_dao_token";

    /**
     * <p>
     * Represents the "UnitName" field.
     * </p>
     */
    private static final String UNIT_NAME_FIELD = "UnitName";

    /**
     * <p>
     * Represents the "SessionContext" field.
     * </p>
     */
    private static final String SESSION_CONTEXT_FIELD = "SessionContext";

    /**
     * <p>
     * Represents the "Logger" field.
     * </p>
     */
    private static final String LOGGER_FIELD = "Logger";

    /**
     * <p>
     * Represents the <code>SessionContext</code> injected by the EJB container automatically.
     * </p>
     *
     * <p>
     * It is marked with "@Resource" annotation.
     * </p>
     *
     * <p>
     * It's non-null after injected when this bean is instantiated. And its reference is not changed afterwards.
     * </p>
     *
     * <p>
     * It can be used for example in the <code>initialize()</code> method to lookup JNDI resources.
     * </p>
     */
    @Resource
    private SessionContext sessionContext;

    /**
     * <p>
     * This is a logger which will be used to log actions and exception for this manager bean.
     * </p>
     *
     * <p>
     * This is initialized through the <code>initialize()</code> method, and never changed afterwards.
     * </p>
     *
     * <p>
     * Can be null.
     * </p>
     *
     * <p>
     * This is used in all public methods and logs the actions/exceptions.
     * </p>
     */
    private Log logger;

    /**
     * <p>
     * Represents the <code>TrackContestDAO</code> implementation that will be used to perform CRUD
     * operations for <code>TrackContest</code> entities.
     * </p>
     *
     * <p>
     * This is initialized through the <code>initialize()</code> method, and never changed afterwards.
     * </p>
     *
     * <p>
     * Cannot be null.
     * </p>
     *
     * <p>
     * This is used in all the <code>*TrackContest(...)</code> methods.
     * </p>
     */
    private TrackContestDAO trackContestDAO;

    /**
     * <p>
     * Represents the <code>TrackContestTypeDAO</code> implementation that will be used to perform CRUD
     * operations for <code>TrackContestType</code> entities.
     * </p>
     *
     * <p>
     * This is initialized through the <code>initialize()</code> method, and never changed afterwards.
     * </p>
     *
     * <p>
     * Cannot be null.
     * </p>
     *
     * <p>
     * This is used in all the <code>*TrackContestType(...)</code> methods.
     * </p>
     */
    private TrackContestTypeDAO trackContestTypeDAO;

    /**
     * <p>
     * Represents the <code>TrackContestResultCalculatorDAO</code> implementation that will be used to perform CRUD
     * operations for <code>TrackContestResultCalculator</code> entities.
     * </p>
     *
     * <p>
     * This is initialized through the <code>initialize()</code> method, and never changed afterwards.
     * </p>
     *
     * <p>
     * Cannot be null.
     * </p>
     *
     * <p>
     * This is used in all the <code>*TrackContestResultCalculator(...)</code> methods.
     * </p>
     */
    private TrackContestResultCalculatorDAO trackContestResultCalculatorDAO;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public DigitalRunContestManagerBean() {
        // empty
    }

    /**
     * <p>
     * Configures this bean with values from the JNDI ENC.
     * </p>
     *
     * <p>
     * This method is marked with "@PostConstruct" annotation and is called after this bean is constructed by
     * the EJB container.
     * </p>
     *
     * <p>
     * It obtains the value of the "unit_name", "config_file_name", and "config_namespace" from session context.
     * </p>
     *
     * <p>
     * It will use the configuration to get an instance of <code>Log</code> from the <code>LogManager</code> and
     * create DAO implementations instances with <code>ObjectFactory</code>.
     * </p>
     *
     * @throws ContestManagerConfigurationException If "unitName" or "config_namespace" or "config_file_name"
     *         parameter in the JNDI ENC is missing or invalid, or if there is error when obtaining log, or if
     *         any dao tokens are missing or invalid, or if Object Factory cannot be initialized or cannot create
     *         a specific object.
     */
    @PostConstruct
    public void initialize() {
        String unitName = Helper.lookupENC(sessionContext, Helper.UNIT_NAME);

        String configFile = Helper.lookupENC(sessionContext, Helper.CONFIG_FILE_NAME);
        String namespace = Helper.lookupENC(sessionContext, Helper.CONFIG_NAMESPACE);

        try {
            ConfigurationFileManager cfm = new ConfigurationFileManager(configFile);

            // The ConfigurationFileManager#getConfiguration never returns null
            // It throws UnrecognizedNamespaceException if given namespace does not exist

            ConfigurationObject co = Helper.getChild(cfm.getConfiguration(namespace), namespace);

            String logName = Helper.getConfigString(co, LOG_NAME, false);

            String tcdt = Helper.getConfigString(co, TRACK_CONTEST_DAO_TOKEN, true);
            String tctdt = Helper.getConfigString(co, TRACK_CONTEST_TYPE_DAO_TOKEN, true);
            String rcdt = Helper.getConfigString(co, RESULT_CALCULATOR_DAO_TOKEN, true);

            ConfigurationObject ofo = Helper.getChild(co, OBJECT_FACTORY_CONFIG);

            ObjectFactory of = new ObjectFactory(new ConfigurationObjectSpecificationFactory(ofo));

            logger = logName == null ? null : LogManager.getLogWithExceptions(logName);

            trackContestDAO = (TrackContestDAO) of.createObject(tcdt);

            trackContestTypeDAO = (TrackContestTypeDAO) of.createObject(tctdt);

            trackContestResultCalculatorDAO = (TrackContestResultCalculatorDAO) of.createObject(rcdt);

            if (logger != null) {
                inject(trackContestDAO, LOGGER_FIELD, Log.class, logger);
                inject(trackContestTypeDAO, LOGGER_FIELD, Log.class, logger);
                inject(trackContestResultCalculatorDAO, LOGGER_FIELD, Log.class, logger);
            }

            inject(trackContestDAO, UNIT_NAME_FIELD, String.class, unitName);
            inject(trackContestTypeDAO, UNIT_NAME_FIELD, String.class, unitName);
            inject(trackContestResultCalculatorDAO, UNIT_NAME_FIELD, String.class, unitName);

            inject(trackContestDAO, SESSION_CONTEXT_FIELD, SessionContext.class, sessionContext);
            inject(trackContestTypeDAO, SESSION_CONTEXT_FIELD, SessionContext.class, sessionContext);
            inject(trackContestResultCalculatorDAO, SESSION_CONTEXT_FIELD, SessionContext.class, sessionContext);

        } catch (BaseException e) {
            throw new ContestManagerConfigurationException("Error occurs while initializing " + this.getClass(), e);
        } catch (IOException e) {
            throw new ContestManagerConfigurationException("I/O Error occurs while initializing " + this.getClass(), e);
        } catch (ClassCastException e) {
            throw new ContestManagerConfigurationException(
                "ClassCastException occurs while initializing  " + this.getClass(), e);
        }
    }

    /**
     * <p>
     * Inject value.
     * </p>
     *
     * @param target The injection target.
     * @param fieldName The field name.
     * @param fieldClazz The class of field.
     * @param fieldValue The field value.
     *
     * @throws BaseException if the underlying setter method throws an exception.
     */
    private void inject(Object target, String fieldName, Class fieldClazz,
        Object fieldValue) throws BaseException {
        try {
            Method method = target.getClass().getMethod("set" + fieldName, fieldClazz);
            method.invoke(target, fieldValue);
        } catch (InvocationTargetException e) {
            // InvocationTargetException if the underlying method throws an exception, propagate it
            Helper.logException(logger, e);
            throw new BaseException("Error occurs while injection.", e);
        } catch (NoSuchMethodException e) {
            Helper.logException(logger, e);
        } catch (IllegalAccessException e) {
            Helper.logException(logger, e);
        } catch (IllegalArgumentException e) {
            Helper.logException(logger, e);
        } catch (SecurityException e) {
            Helper.logException(logger, e);
        }
    }

    /**
     * <p>
     * Creates a new <code>TrackContest</code> entity in persistence.
     * </p>
     *
     * <p>
     * It accepts an instance that has not yet been created in persistence, and persists it.
     * </p>
     *
     * <p>
     * This method is marked with "@TransactionAttribute(TransactionAttributeType.REQUIRED)"
     * to indicate the transaction is required.
     * </p>
     *
     * @param trackContest The track contest we want to create.
     *
     * @return The created track contest (with possibly updated id).
     *
     * @throws IllegalArgumentException if the input is null.
     * @throws EntityExistsException if an entity with this id already exists.
     * @throws DigitalRunContestManagerException if there was a failure in creation.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public TrackContest createTrackContest(TrackContest trackContest) throws DigitalRunContestManagerException {

        final String method = "createTrackContest(TrackContest)";

        Helper.logEnter(logger, CLASS_NAME, method);

        try {
            return trackContestDAO.createTrackContest(trackContest);
        } catch (PersistenceException e) {
            throw new DigitalRunContestManagerException(
                "Persistence error occurs while creating track contest.", Helper.logException(logger, e));
        } finally {
            Helper.logExit(logger, CLASS_NAME, method);
        }
    }

    /**
     * <p>
     * Updates an existing <code>TrackContest</code> entity in persistence.
     * </p>
     *
     * <p>
     * It accepts an instance that should already be present in persistence, and persist the update.
     * </p>
     *
     * <p>
     * This method is marked with "@TransactionAttribute(TransactionAttributeType.REQUIRED)"
     * to indicate the transaction is required.
     * </p>
     *
     * @param trackContest The track contest we want to update.
     *
     * @throws IllegalArgumentException if the input is null.
     * @throws EntityNotFoundException if the input entity doesn't exist in persistence.
     * @throws DigitalRunContestManagerException if there was a failure in the update.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateTrackContest(TrackContest trackContest) throws DigitalRunContestManagerException {

        final String method = "updateTrackContest(TrackContest)";

        Helper.logEnter(logger, CLASS_NAME, method);

        try {
            trackContestDAO.updateTrackContest(trackContest);
        } catch (PersistenceException e) {
            throw new DigitalRunContestManagerException(
                "Persistence error occurs while updating track contest.", Helper.logException(logger, e));
        } finally {
            Helper.logExit(logger, CLASS_NAME, method);
        }
    }

    /**
     * <p>
     * Removes an existing <code>TrackContest</code> entity from persistence.
     * </p>
     *
     * <p>
     * It accepts an id of an instance that should already be present in persistence, and
     * removes that entity from the persistence.
     * </p>
     *
     * <p>
     * This method is marked with "@TransactionAttribute(TransactionAttributeType.REQUIRED)"
     * to indicate the transaction is required.
     * </p>
     *
     * @param trackContestId The track contest (by id) we want to remove.
     *
     * @throws EntityNotFoundException if the input entity id doesn't exist in persistence.
     * @throws DigitalRunContestManagerException if there was a failure in the removal.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void removeTrackContest(long trackContestId) throws DigitalRunContestManagerException {

        final String method = "removeTrackContest(long)";

        Helper.logEnter(logger, CLASS_NAME, method);

        try {
            trackContestDAO.removeTrackContest(trackContestId);
        } catch (PersistenceException e) {
            throw new DigitalRunContestManagerException(
                "Persistence error occurs while removing track contest.", Helper.logException(logger, e));
        } finally {
            Helper.logExit(logger, CLASS_NAME, method);
        }
    }

    /**
     * <p>
     * Fetches a <code>TrackContest</code> entity from persistence based in the input id.
     * </p>
     *
     * @param trackContestId The track contest (by id) we want to fetch.
     *
     * @return The <code>TrackContest</code> entity if found or null of not found.
     *
     * @throws DigitalRunContestManagerException if there was a failure in the fetching.
     */
    public TrackContest getTrackContest(long trackContestId) throws DigitalRunContestManagerException {

        final String method = "getTrackContest(long)";

        Helper.logEnter(logger, CLASS_NAME, method);

        try {
            return trackContestDAO.getTrackContest(trackContestId);
        } catch (PersistenceException e) {
            throw new DigitalRunContestManagerException(
                "Persistence error occurs while fetching track contest.", Helper.logException(logger, e));
        } finally {
            Helper.logExit(logger, CLASS_NAME, method);
        }
    }

    /**
     * <p>
     * This will search the persistence for matching <code>TrackContest</code> entities based on the
     * specific search criteria provided.
     * </p>
     *
     * @param filter The search criteria to be used.
     *
     * @return List of <code>TrackContest</code> entities that are matched by the provided filter, empty
     *         list if none were found.
     *
     * @throws IllegalArgumentException if filter is null.
     * @throws DigitalRunContestManagerException exception if there was a failure in the searching.
     */
    public List < TrackContest > searchTrackContests(Filter filter) throws DigitalRunContestManagerException {

        final String method = "searchTrackContests(Filter)";

        Helper.logEnter(logger, CLASS_NAME, method);

        try {
            return trackContestDAO.searchTrackContests(filter);
        } catch (PersistenceException e) {
            throw new DigitalRunContestManagerException(
                "Persistence error occurs while searching track contests.", Helper.logException(logger, e));
        } finally {
            Helper.logExit(logger, CLASS_NAME, method);
        }
    }

    /**
     * <p>
     * Creates a new <code>TrackContestType</code> entity in persistence.
     * </p>
     *
     * <p>
     * It accepts an instance that has not yet been created in persistence, and persists it.
     * </p>
     *
     * <p>
     * This method is marked with "@TransactionAttribute(TransactionAttributeType.REQUIRED)"
     * to indicate the transaction is required.
     * </p>
     *
     * @param trackContestType The track contest type we want to create.
     *
     * @return The created track contest type (with possibly updated id).
     *
     * @throws IllegalArgumentException if the input is null.
     * @throws EntityExistsException if an entity with this id already exists.
     * @throws DigitalRunContestManagerException if there was a failure in creation.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public TrackContestType createTrackContestType(
        TrackContestType trackContestType) throws DigitalRunContestManagerException {

        final String method = "createTrackContestType(TrackContestType)";

        Helper.logEnter(logger, CLASS_NAME, method);

        try {
            return trackContestTypeDAO.createTrackContestType(trackContestType);
        } catch (PersistenceException e) {
            throw new DigitalRunContestManagerException(
                "Persistence error occurs while creating track contest type.", Helper.logException(logger, e));
        } finally {
            Helper.logExit(logger, CLASS_NAME, method);
        }
    }

    /**
     * <p>
     * Updates an existing <code>TrackContestType</code> entity in persistence.
     * </p>
     *
     * <p>
     * It accepts an instance that should already be present in persistence, and persist the update.
     * </p>
     *
     * <p>
     * This method is marked with "@TransactionAttribute(TransactionAttributeType.REQUIRED)"
     * to indicate the transaction is required.
     * </p>
     *
     * @param trackContestType The track contest type we want to update.
     *
     * @throws IllegalArgumentException if the input is null.
     * @throws EntityNotFoundException if the input entity doesn't exist in persistence.
     * @throws DigitalRunContestManagerException if there was a failure in the update.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateTrackContestType(TrackContestType trackContestType) throws DigitalRunContestManagerException {

        final String method = "updateTrackContestType(TrackContestType)";

        Helper.logEnter(logger, CLASS_NAME, method);

        try {
            trackContestTypeDAO.updateTrackContestType(trackContestType);
        } catch (PersistenceException e) {
            throw new DigitalRunContestManagerException(
                "Persistence error occurs while updating track contest type.", Helper.logException(logger, e));
        } finally {
            Helper.logExit(logger, CLASS_NAME, method);
        }
    }

    /**
     * <p>
     * Removes an existing <code>TrackContestType</code> entity from persistence.
     * </p>
     *
     * <p>
     * It accepts an id of an instance that should already be present in persistence, and
     * removes that entity from the persistence.
     * </p>
     *
     * <p>
     * This method is marked with "@TransactionAttribute(TransactionAttributeType.REQUIRED)"
     * to indicate the transaction is required.
     * </p>
     *
     * @param trackContestTypeId The track contest type (by id) we want to remove.
     *
     * @throws EntityNotFoundException if the input entity id doesn't exist in persistence.
     * @throws DigitalRunContestManagerException if there was a failure in the removal.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void removeTrackContestType(long trackContestTypeId) throws DigitalRunContestManagerException {

        final String method = "removeTrackContestType(long)";

        Helper.logEnter(logger, CLASS_NAME, method);

        try {
            trackContestTypeDAO.removeTrackContestType(trackContestTypeId);
        } catch (PersistenceException e) {
            throw new DigitalRunContestManagerException(
                "Persistence error occurs while removing track contest type.", Helper.logException(logger, e));
        } finally {
            Helper.logExit(logger, CLASS_NAME, method);
        }
    }

    /**
     * <p>
     * Fetches a <code>TrackContestType</code> entity from persistence based in the input id.
     * </p>
     *
     * @param trackContestTypeId The track contest type (by id) we want to fetch.
     *
     * @return The <code>TrackContestType</code> entity if found or null of not found.
     *
     * @throws DigitalRunContestManagerException if there was a failure in the fetching.
     */
    public TrackContestType getTrackContestType(long trackContestTypeId) throws DigitalRunContestManagerException {

        final String method = "getTrackContestType(long)";

        Helper.logEnter(logger, CLASS_NAME, method);

        try {
            return trackContestTypeDAO.getTrackContestType(trackContestTypeId);
        } catch (PersistenceException e) {
            throw new DigitalRunContestManagerException(
                "Persistence error occurs while fetching track contest type.", Helper.logException(logger, e));
        } finally {
            Helper.logExit(logger, CLASS_NAME, method);
        }
    }

    /**
     * <p>
     * Fetches all available <code>TrackContestType</code> entities from persistence.
     * </p>
     *
     * @return a list of all the <code>TrackContestType</code> entities found in persistence,
     *         or an empty list of none were found.
     *
     * @throws DigitalRunContestManagerException if there was a failure in the fetching.
     */
    public List < TrackContestType > getAllTrackContestTypes() throws DigitalRunContestManagerException {

        final String method = "getAllTrackContestTypes()";

        Helper.logEnter(logger, CLASS_NAME, method);

        try {
            return trackContestTypeDAO.getAllTrackContestTypes();
        } catch (PersistenceException e) {
            throw new DigitalRunContestManagerException(
                "Persistence error occurs while fetching all track contest types.", Helper.logException(logger, e));
        } finally {
            Helper.logExit(logger, CLASS_NAME, method);
        }
    }

    /**
     * <p>
     * Creates a new <code>TrackContestResultCalculator</code> entity in persistence.
     * </p>
     *
     * <p>
     * It accepts an instance that has not yet been created in persistence, and persists it.
     * </p>
     *
     * <p>
     * This method is marked with "@TransactionAttribute(TransactionAttributeType.REQUIRED)"
     * to indicate the transaction is required.
     * </p>
     *
     * @param trackContestResultCalculator The track contest result calculator we want to create.
     *
     * @return The created track contest result calculator (with possibly updated id).
     *
     * @throws IllegalArgumentException if the input is null.
     * @throws EntityExistsException if an entity with this id already exists.
     * @throws DigitalRunContestManagerException if there was a failure in creation.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public TrackContestResultCalculator createTrackContestResultCalculator(
        TrackContestResultCalculator trackContestResultCalculator) throws DigitalRunContestManagerException {

        final String method = "createTrackContestResultCalculator(TrackContestResultCalculator)";

        Helper.logEnter(logger, CLASS_NAME, method);

        try {
            return trackContestResultCalculatorDAO.createTrackContestResultCalculator(trackContestResultCalculator);
        } catch (PersistenceException e) {
            throw new DigitalRunContestManagerException(
                "Persistence error occurs while creating track contest result calculator.",
                Helper.logException(logger, e));
        } finally {
            Helper.logExit(logger, CLASS_NAME, method);
        }
    }

    /**
     * <p>
     * Updates an existing <code>TrackContestResultCalculator</code> entity in persistence.
     * </p>
     *
     * <p>
     * It accepts an instance that should already be present in persistence, and persist the update.
     * </p>
     *
     * <p>
     * This method is marked with "@TransactionAttribute(TransactionAttributeType.REQUIRED)"
     * to indicate the transaction is required.
     * </p>
     *
     * @param trackContestResultCalculator The track contest result calculator we want to update.
     *
     * @throws IllegalArgumentException if the input is null.
     * @throws EntityNotFoundException if the input entity doesn't exist in persistence.
     * @throws DigitalRunContestManagerException if there was a failure in the update.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateTrackContestResultCalculator(
        TrackContestResultCalculator trackContestResultCalculator) throws DigitalRunContestManagerException {

        final String method = "updateTrackContestResultCalculator(TrackContestResultCalculator)";

        Helper.logEnter(logger, CLASS_NAME, method);

        try {
            trackContestResultCalculatorDAO.updateTrackContestResultCalculator(trackContestResultCalculator);
        } catch (PersistenceException e) {
            throw new DigitalRunContestManagerException(
                "Persistence error occurs while updating track contest result calculator.",
                Helper.logException(logger, e));
        } finally {
            Helper.logExit(logger, CLASS_NAME, method);
        }
    }

    /**
     * <p>
     * Removes an existing <code>TrackContestResultCalculator</code> entity from persistence.
     * </p>
     *
     * <p>
     * It accepts an id of an instance that should already be present in persistence, and
     * removes that entity from the persistence.
     * </p>
     *
     * <p>
     * This method is marked with "@TransactionAttribute(TransactionAttributeType.REQUIRED)"
     * to indicate the transaction is required.
     * </p>
     *
     * @param trackContestResultCalculatorId The track contest result calculator (by id) we want to remove.
     *
     * @throws EntityNotFoundException if the input entity id doesn't exist in persistence.
     * @throws DigitalRunContestManagerException if there was a failure in the removal.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void removeTrackContestResultCalculator(
        long trackContestResultCalculatorId) throws DigitalRunContestManagerException {

        final String method = "removeTrackContestResultCalculator(long)";

        Helper.logEnter(logger, CLASS_NAME, method);

        try {
            trackContestResultCalculatorDAO.removeTrackContestResultCalculator(trackContestResultCalculatorId);
        } catch (PersistenceException e) {
            throw new DigitalRunContestManagerException(
                "Persistence error occurs while removing track contest result calculator.",
                Helper.logException(logger, e));
        } finally {
            Helper.logExit(logger, CLASS_NAME, method);
        }
    }

    /**
     * <p>
     * Fetches a <code>TrackContestResultCalculator</code> entity from persistence based in the input id.
     * </p>
     *
     * @param trackContestResultCalculatorId The track contest result calculator (by id) we want to fetch.
     *
     * @return The <code>TrackContestResultCalculator</code> entity if found or null of not found.
     *
     * @throws DigitalRunContestManagerException if there was a failure in the fetching.
     */
    public TrackContestResultCalculator getTrackContestResultCalculator(
        long trackContestResultCalculatorId) throws DigitalRunContestManagerException {

        final String method = "getTrackContestResultCalculator(long)";

        Helper.logEnter(logger, CLASS_NAME, method);

        try {
            return trackContestResultCalculatorDAO.getTrackContestResultCalculator(trackContestResultCalculatorId);
        } catch (PersistenceException e) {
            throw new DigitalRunContestManagerException(
                "Persistence error occurs while fetching track contest result calculator.",
                Helper.logException(logger, e));
        } finally {
            Helper.logExit(logger, CLASS_NAME, method);
        }
    }

    /**
     * <p>
     * Fetches all available <code>TrackContestResultCalculator</code> entities from persistence.
     * </p>
     *
     * @return a list of all the <code>TrackContestResultCalculator</code> entities found in persistence,
     *         or an empty list of none were found.
     *
     * @throws DigitalRunContestManagerException if there was a failure in the fetching.
     */
    public List < TrackContestResultCalculator > getAllTrackContestResultCalculators()
        throws DigitalRunContestManagerException {

        final String method = "getAllTrackContestResultCalculators()";

        Helper.logEnter(logger, CLASS_NAME, method);

        try {
            return trackContestResultCalculatorDAO.getAllTrackContestResultCalculators();
        } catch (PersistenceException e) {
            throw new DigitalRunContestManagerException(
                "Persistence error occurs while fetching all track contest result calculators.",
                Helper.logException(logger, e));
        } finally {
            Helper.logExit(logger, CLASS_NAME, method);
        }
    }
}
