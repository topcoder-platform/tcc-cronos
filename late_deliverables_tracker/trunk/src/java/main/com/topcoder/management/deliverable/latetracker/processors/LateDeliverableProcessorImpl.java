/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.latetracker.processors;

import com.cronos.onlinereview.external.ExternalUser;
import com.cronos.onlinereview.external.RetrievalException;
import com.cronos.onlinereview.external.UserRetrieval;

import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;

import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.UnknownConnectionException;

import com.topcoder.management.deliverable.Deliverable;
import com.topcoder.management.deliverable.latetracker.Helper;
import com.topcoder.management.deliverable.latetracker.LateDeliverable;
import com.topcoder.management.deliverable.latetracker.LateDeliverableProcessor;
import com.topcoder.management.deliverable.latetracker.LateDeliverablesProcessingException;
import com.topcoder.management.deliverable.latetracker.LateDeliverablesTrackerConfigurationException;
import com.topcoder.management.project.Project;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseType;

import com.topcoder.util.errorhandling.ExceptionUtils;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;
import com.topcoder.util.objectfactory.ObjectFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * This class is an implementation of <code>LateDeliverableProcessor</code> that uses
 * pluggable <code>ResourceManager</code> and <code>UserRetrieval</code> instances to
 * retrieve an additional information about the user who has a late deliverable, and DB
 * Connection Factory component to perform auditing of all late deliverables and last sent
 * notifications in the database. To send warning email messages to the users this class
 * uses <code>EmailSendingUtility</code>. This class performs the logging of errors and
 * debug information using Logging Wrapper component.
 * </p>
 * <p>
 * Sample config:
 *
 * <pre>
 * &lt;?xml version=&quot;1.0&quot;?&gt;
 *  &lt;CMConfig&gt;
 *  &lt;Config name=
 *  &quot;com.topcoder.management.deliverable.latetracker.processors.LateDeliverableProcessorImpl&quot;&gt;
 *  &lt;Property name=&quot;loggerName&quot;&gt;
 *  &lt;Value&gt;myLogger&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;connectionName&quot;&gt;
 *  &lt;Value&gt;informix_connection&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;objectFactoryConfig&quot;&gt;
 *  &lt;property name=&quot;resourceManager&quot;&gt;
 *  &lt;property name=&quot;type&quot;&gt;
 *  &lt;value&gt;com.topcoder.management.resource.persistence.PersistenceResourceManager&lt;/value&gt;
 *  &lt;/property&gt;
 *  &lt;Property name=&quot;params&quot;&gt;
 *  &lt;Property name=&quot;param1&quot;&gt;
 *  &lt;Property name=&quot;name&quot;&gt;
 *  &lt;Value&gt;ResourcePersistence&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;param2&quot;&gt;
 *  &lt;Property name=&quot;name&quot;&gt;
 *  &lt;Value&gt;SearchBundleManager&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;/property&gt;
 *  &lt;property name=&quot;ResourcePersistence&quot;&gt;
 *  &lt;property name=&quot;type&quot;&gt;
 *  &lt;value&gt;com.topcoder.management.resource.persistence.sql.SqlResourcePersistence&lt;/value&gt;
 *  &lt;/property&gt;
 *  &lt;Property name=&quot;params&quot;&gt;
 *  &lt;Property name=&quot;param1&quot;&gt;
 *  &lt;Property name=&quot;name&quot;&gt;
 *  &lt;Value&gt;DBConnectionFactory&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;/property&gt;
 *  &lt;property name=&quot;DBConnectionFactory&quot;&gt;
 *  &lt;property name=&quot;type&quot;&gt;
 *  &lt;value&gt;com.topcoder.db.connectionfactory.DBConnectionFactoryImpl&lt;/value&gt;
 *  &lt;/property&gt;
 *  &lt;Property name=&quot;params&quot;&gt;
 *  &lt;Property name=&quot;param1&quot;&gt;
 *  &lt;Property name=&quot;type&quot;&gt;
 *  &lt;Value&gt;String&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;value&quot;&gt;
 *  &lt;Value&gt;com.topcoder.db.connectionfactory.DBConnectionFactoryImpl&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;/property&gt;
 *  &lt;property name=&quot;SearchBundleManager&quot;&gt;
 *  &lt;property name=&quot;type&quot;&gt;
 *  &lt;value&gt;com.topcoder.search.builder.SearchBundleManager&lt;/value&gt;
 *  &lt;/property&gt;
 *  &lt;Property name=&quot;params&quot;&gt;
 *  &lt;Property name=&quot;param1&quot;&gt;
 *  &lt;Property name=&quot;type&quot;&gt;
 *  &lt;Value&gt;String&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;value&quot;&gt;
 *  &lt;Value&gt;com.topcoder.search.builder.SearchBundleManager&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;/property&gt;
 *  &lt;property name=&quot;userRetrieval&quot;&gt;
 *  &lt;property name=&quot;type&quot;&gt;
 *  &lt;value&gt;com.cronos.onlinereview.external.impl.DBUserRetrieval&lt;/value&gt;
 *  &lt;/property&gt;
 *  &lt;Property name=&quot;params&quot;&gt;
 *  &lt;Property name=&quot;param1&quot;&gt;
 *  &lt;Property name=&quot;type&quot;&gt;
 *  &lt;Value&gt;String&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;value&quot;&gt;
 *  &lt;Value&gt;com.topcoder.db.connectionfactory.DBConnectionFactoryImpl&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;/property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;dbConnectionFactoryConfig&quot;&gt;
 *  &lt;Property name=&quot;com.topcoder.db.connectionfactory.DBConnectionFactoryImpl&quot;&gt;
 *  &lt;Property name=&quot;connections&quot;&gt;
 *  &lt;Property name=&quot;informix_connection&quot;&gt;
 *  &lt;Property name=&quot;producer&quot;&gt;
 *  &lt;Value&gt;com.topcoder.db.connectionfactory.producers.JDBCConnectionProducer&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;parameters&quot;&gt;
 *  &lt;Property name=&quot;jdbc_driver&quot;&gt;
 *  &lt;Value&gt;com.informix.jdbc.IfxDriver&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;jdbc_url&quot;&gt;
 *  &lt;Value&gt;jdbc:informix-sqli://192.168.1.3:9000/online_review:informixserver=tcs&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;user&quot;&gt;
 *  &lt;Value&gt;informix&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;password&quot;&gt;
 *  &lt;Value&gt;123456&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;emailSubjectForDeliverable3&quot;&gt;
 *  &lt;Value&gt;WARNING\: You are late when providing a deliverable for %PROJECT_NAME%&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;emailBodyForDeliverable3&quot;&gt;
 *  &lt;Value&gt;test_files/warn_email_template.html&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;notificationDeliverableIds&quot;&gt;
 *  &lt;Value&gt;4&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;defaultEmailSubjectTemplateText&quot;&gt;
 *  &lt;Value&gt;WARNING\: You are late when providing a deliverable for %PROJECT_NAME%&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;defaultEmailBodyTemplatePath&quot;&gt;
 *  &lt;Value&gt;test_files/warn_email_template.html&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;emailSender&quot;&gt;
 *  &lt;Value&gt;service@topcoder.com&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;resourceManagerKey&quot;&gt;
 *  &lt;Value&gt;resourceManager&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;userRetrievalKey&quot;&gt;
 *  &lt;Value&gt;userRetrieval&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;timestampFormat&quot;&gt;
 *  &lt;Value&gt;yyyy-MM-dd HH:mm:ss&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;notificationInterval&quot;&gt;
 *  &lt;Value&gt;10&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Config&gt;
 *  &lt;/CMConfig&gt;
 * </pre>
 * </p>
 * <p>
 * Thread Safety: This class is not thread safe since it uses <code>ResourceManager</code>
 * instance that is not thread safe. It's assumed that{@link #configure(ConfigurationObject)}
 * method will be called just once right after instantiation, before calling any business
 * methods. <code>LateDeliverableProcessorImpl</code> uses transactions when inserting
 * or updating data in persistence.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class LateDeliverableProcessorImpl implements LateDeliverableProcessor {
    /**
     * <p>
     * Represents the name of this class used for logging.
     * </p>
     */
    private static final String CLASS_NAME = LateDeliverableProcessorImpl.class.getName();

    /**
     * <p>
     * Represents the default time format pattern.
     * </p>
     */
    private static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * <p>
     * Represents the sql statement to get last dead line for the late deliverable.
     * </p>
     */
    private static final String SELECT_MAX_DEADLINE_SQL = "select max(deadline) from late_deliverable"
        + " where project_phase_id = ? and resource_id = ?";

    /**
     * <p>
     * Represents the sql statement to retrieve last notified time and forgive flag.
     * </p>
     */
    private static final String SELECT_LAST_NOTIFICATION_TIME_FORGIVE_SQL = "select last_notified,"
        + " forgive_ind from late_deliverable where project_phase_id = ? and resource_id = ? and deadline = ?";

    /**
     * <p>
     * Represents the sql statement to insert a record with last notified time.
     * </p>
     */
    private static final String INSERT_WITH_LAST_NOTIFICATION_SQL = "insert into late_deliverable"
        + " (project_phase_id, resource_id, deliverable_id, deadline, create_date, forgive_ind,"
        + " last_notified) values (?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the sql statement to insert a record without last notified time.
     * </p>
     */
    private static final String INSERT_SQL = "insert into late_deliverable (project_phase_id, resource_id,"
        + " deliverable_id, deadline, create_date, forgive_ind) values (?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the sql statement to update last notified time for the late deliverable.
     * </p>
     */
    private static final String UPDATE_SQL = "update late_deliverable set last_notified = ? where"
        + " project_phase_id = ? and resource_id = ? and deadline = ?";

    /**
     * <p>
     * Represents &quot;connectionName&quot; property key in configuration.
     * </p>
     */
    private static final String CONNECTION_NAME_KEY = "connectionName";

    /**
     * <p>
     * Represents &quot;dbConnectionFactoryConfig&quot; child configuration key in
     * configuration.
     * </p>
     */
    private static final String DB_CONNECTION_FACTORY_CONFIG = "dbConnectionFactoryConfig";

    /**
     * <p>
     * Represents &quot;notificationDeliverableIds&quot; property key in configuration.
     * </p>
     */
    private static final String NOTIFICATION_DELIVERABLE_IDS = "notificationDeliverableIds";

    /**
     * <p>
     * Represents &quot;defaultEmailSubjectTemplateText&quot; property key in
     * configuration.
     * </p>
     */
    private static final String DEFAULT_EMAIL_SUBJECT_TEMPLATE_TEXT = "defaultEmailSubjectTemplateText";

    /**
     * <p>
     * Represents &quot;defaultEmailBodyTemplatePath&quot; property key in configuration.
     * </p>
     */
    private static final String DEFAULT_EMAIL_BODY_TEMPLATE_PATH = "defaultEmailBodyTemplatePath";

    /**
     * <p>
     * Represents &quot;emailSender&quot; property key in configuration.
     * </p>
     */
    private static final String EMAIL_SENDER_KEY = "emailSender";

    /**
     * <p>
     * Represents &quot;resourceManagerKey&quot; property key in configuration.
     * </p>
     */
    private static final String RESOURCE_MANAGER_KEY = "resourceManagerKey";

    /**
     * <p>
     * Represents &quot;userRetrievalKey&quot; property key in configuration.
     * </p>
     */
    private static final String USER_RETRIEVAL_KEY = "userRetrievalKey";

    /**
     * <p>
     * Represents &quot;timestampFormat&quot; property key in configuration.
     * </p>
     */
    private static final String TIMESTAMP_FORMAT_KEY = "timestampFormat";

    /**
     * <p>
     * Represents &quot;notificationInterval&quot; property key in configuration.
     * </p>
     */
    private static final String NOTIFICATION_INTERVAL_KEY = "notificationInterval";

    /**
     * <p>
     * Represents &quot;emailSubjectForDeliverable&quot; prefix of property in
     * configuration.
     * </p>
     */
    private static final String EMAIL_SUBJECT_PREFIX = "emailSubjectForDeliverable";

    /**
     * <p>
     * Represents &quot;emailBodyForDeliverable&quot; prefix of property in configuration.
     * </p>
     */
    private static final String EMAIL_BODY_PREFIX = "emailBodyForDeliverable";

    /**
     * Represents one minute time in long.
     */
    private static final long MINUTE = 60000;

    /**
     * <p>
     * Represents one second in long value.
     * </p>
     */
    private static final long THOUSAND = 1000;

    /**
     * Represents constant value 60.
     */
    private static final int SIXTY = 60;

    /**
     * Represents constant value 24.
     */
    private static final int TWENTY_FOUR = 24;

    /**
     * <p>
     * The database connection factory to be used by this class.
     * </p>
     * <p>
     * Is initialized in {@link #configure(ConfigurationObject)} and never changed after
     * that.
     * </p>
     * <p>
     * Cannot be null after initialization.
     * </p>
     * <p>
     * Is used in {@link #processLateDeliverable(LateDeliverable)}.
     * </p>
     */
    private DBConnectionFactory dbConnectionFactory;

    /**
     * <p>
     * The connection name to be passed to the connection factory.
     * </p>
     * <p>
     * If null, the default connection is used.
     * </p>
     * <p>
     * Is initialized in {@link #configure(ConfigurationObject)} and never changed after
     * that.
     * </p>
     * <p>
     * Cannot be empty after initialization.
     * </p>
     * <p>
     * Is used in {@link #processLateDeliverable(LateDeliverable)}.
     * </p>
     */
    private String connectionName;

    /**
     * <p>
     * The set of deliverable IDs for which sending of notifications must be performed.
     * </p>
     * <p>
     * Is initialized in {@link #configure(ConfigurationObject)} and never changed after
     * that.
     * </p>
     * <p>
     * Cannot be null, cannot contain null or not positive element after initialization.
     * </p>
     * <p>
     * Is used in {@link #processLateDeliverable(LateDeliverable)}.
     * </p>
     */
    private Set<Long> notificationDeliverableIds;

    /**
     * <p>
     * The mapping from deliverable ID to email subject template text to be used for late
     * deliverables with this ID.
     * </p>
     * <p>
     * Is initialized in {@link #configure(ConfigurationObject)} and never changed after
     * that.
     * </p>
     * <p>
     * Cannot be null, cannot contain null/not positive key or null value after
     * initialization.
     * </p>
     * <p>
     * Is used in {@link #processLateDeliverable(LateDeliverable)}.
     * </p>
     */
    private Map<Long, String> emailSubjectTemplateTexts;

    /**
     * <p>
     * The mapping from deliverable ID to email body template path (resource path or file
     * path) to be used for late deliverables with this ID.
     * </p>
     * <p>
     * Is initialized in {@link #configure(ConfigurationObject)} and never changed after
     * that.
     * </p>
     * <p>
     * Cannot be null, cannot contain null/not positive key or null/empty value after
     * initialization.
     * </p>
     * <p>
     * Is used in {@link #processLateDeliverable(LateDeliverable)}.
     * </p>
     */
    private Map<Long, String> emailBodyTemplatePaths;

    /**
     * <p>
     * The default email subject template text to be used for all deliverable IDs not
     * configured in emailSubjectTemplateTexts collection.
     * </p>
     * <p>
     * Is initialized in {@link #configure(ConfigurationObject)} and never changed after
     * that.
     * </p>
     * <p>
     * Cannot be null after initialization.
     * </p>
     * <p>
     * Is used in {@link #processLateDeliverable(LateDeliverable)}.
     * </p>
     */
    private String defaultEmailSubjectTemplateText;

    /**
     * <p>
     * The default email body template path (resource path or file path) to be used for
     * all deliverable IDs not configured in emailBodyTemplatePaths collection.
     * </p>
     * <p>
     * Is initialized in {@link #configure(ConfigurationObject)} and never changed after
     * that.
     * </p>
     * <p>
     * Cannot be null/empty after initialization.
     * </p>
     * <p>
     * Is used in {@link #processLateDeliverable(LateDeliverable)}.
     * </p>
     */
    private String defaultEmailBodyTemplatePath;

    /**
     * <p>
     * The email sending utility to be used by this class.
     * </p>
     * <p>
     * Is initialized in {@link #configure(ConfigurationObject)} and never changed after
     * that.
     * </p>
     * <p>
     * Cannot be null after initialization.
     * </p>
     * <p>
     * Is used in {@link #processLateDeliverable(LateDeliverable)}.
     * </p>
     */
    private EmailSendingUtility emailSendingUtility;

    /**
     * <p>
     * The resource manager to be used by this class for retrieving user ID by resource
     * ID.
     * </p>
     * <p>
     * Is initialized in {@link #configure(ConfigurationObject)} and never changed after
     * that.
     * </p>
     * <p>
     * Cannot be null after initialization.
     * </p>
     * <p>
     * Is used in {@link #getEmailAddressForResource(long)}.
     * </p>
     */
    private ResourceManager resourceManager;

    /**
     * <p>
     * The user retrieval service to be used by this class for retrieving user email
     * address by user ID.
     * </p>
     * <p>
     * Is initialized in {@link #configure(ConfigurationObject)} and never changed after
     * that.
     * </p>
     * <p>
     * Cannot be null after initialization.
     * </p>
     * <p>
     * Is used in {@link #getEmailAddressForResource(long)}.
     * </p>
     */
    private UserRetrieval userRetrieval;

    /**
     * <p>
     * The timestamp format to be used for formatting timestamp in the email message.
     * </p>
     * <p>
     * Is initialized in {@link #configure(ConfigurationObject)} and never changed after
     * that.
     * </p>
     * <p>
     * Cannot be null after initialization.
     * </p>
     * <p>
     * Is used in {@link #prepareParameters(Project, Phase, Deliverable)}.
     * </p>
     */
    private DateFormat timestampFormat;

    /**
     * <p>
     * The interval in seconds between sending notifications to the user about the same
     * late deliverable. Note that notifications are not sent when &quot;forgive_ind&quot;
     * flag in the database is set for the late deliverable to &quot;true&quot;.
     * </p>
     * <p>
     * Is initialized in {@link #configure(ConfigurationObject)} and never changed after
     * that.
     * </p>
     * <p>
     * Cannot be negative after initialization. If equal to 0, notifications are not sent
     * repeatedly.
     * </p>
     * <p>
     * Is used in {@link #processLateDeliverable(LateDeliverable)}.
     * </p>
     */
    private long notificationInterval;

    /**
     * <p>
     * The logger used by this class for logging errors and debug information.
     * </p>
     * <p>
     * Is initialized in the {@link #configure(ConfigurationObject)} method and never
     * changed after that. If is null after initialization, logging is not performed.
     * </p>
     * <p>
     * Is used in {@link #processLateDeliverable(LateDeliverable)}.
     * </p>
     */
    private Log log;

    /**
     * Creates an instance of <code>LateDeliverableProcessorImpl</code>.
     */
    public LateDeliverableProcessorImpl() {
    }

    /**
     * Configures this instance with use of the given configuration object.
     *
     * @param config
     *            the configuration object
     * @throws IllegalArgumentException
     *             if <code>config</code> is <code>null</code>.
     * @throws LateDeliverablesTrackerConfigurationException
     *             if some error occurred when initializing an instance using the given
     *             configuration.
     */
    public void configure(ConfigurationObject config) {
        ExceptionUtils.checkNull(config, null, null, "The parameter 'config' should not be null.");

        // create log
        String loggerName = Helper.getPropertyValue(config, Helper.LOGGER_NAME_KEY, false, false);
        this.log = (loggerName == null) ? null : LogFactory.getLog(loggerName);

        this.connectionName = Helper.getPropertyValue(config, CONNECTION_NAME_KEY, false, false);

        // Create database connection factory using the extracted configuration
        dbConnectionFactory = createDBConnectionFactory(config);

        // Get notification deliverable IDs string from config
        String notificationDeliverableIdsStr = Helper.getPropertyValue(config,
            NOTIFICATION_DELIVERABLE_IDS, false, true);
        // Create set for parsed notification deliverable IDs
        notificationDeliverableIds = new HashSet<Long>();

        // Split ID substrings in the comma separated string
        if ((notificationDeliverableIdsStr != null) && (notificationDeliverableIdsStr.trim().length() > 0)) {
            String[] notificationDeliverableIdsArray = notificationDeliverableIdsStr.split(Helper.COMMA);

            for (String notificationDeliverableIdStr : notificationDeliverableIdsArray) {
                // Parse and add ID to the set
                notificationDeliverableIds.add(Helper.parseLong(notificationDeliverableIdStr,
                    NOTIFICATION_DELIVERABLE_IDS));
            }
        }

        // Create map for email subject templates
        emailSubjectTemplateTexts = new HashMap<Long, String>();
        // // Create map for email body templates
        emailBodyTemplatePaths = new HashMap<Long, String>();

        String[] allKeys = getAllKeys(config);

        for (String propertyKey : allKeys) {
            initMap(config, emailSubjectTemplateTexts, propertyKey, EMAIL_SUBJECT_PREFIX);
            initMap(config, emailBodyTemplatePaths, propertyKey, EMAIL_BODY_PREFIX);
        }

        // Get default email subject template from config
        defaultEmailSubjectTemplateText = Helper.getPropertyValue(config,
            DEFAULT_EMAIL_SUBJECT_TEMPLATE_TEXT, true, true);
        // Get default email body template path from config
        defaultEmailBodyTemplatePath = Helper.getPropertyValue(config, DEFAULT_EMAIL_BODY_TEMPLATE_PATH,
            true, false);

        // Get email sender from config
        String emailSender = Helper.getPropertyValue(config, EMAIL_SENDER_KEY, true, false);
        // Create email sending utility
        emailSendingUtility = new EmailSendingUtility(emailSender, log);

        // create object factory
        ObjectFactory objectFactory = Helper.createObjectFactory(config);

        // Create resource manager with OF
        resourceManager = Helper.createObject(config, objectFactory, RESOURCE_MANAGER_KEY,
            ResourceManager.class);

        // Create user retrieval with OF
        userRetrieval = Helper.createObject(config, objectFactory, USER_RETRIEVAL_KEY,
            UserRetrieval.class);

        // Get timestamp format to be used in the email
        String timestampFormatStr = Helper.getPropertyValue(config, TIMESTAMP_FORMAT_KEY, false, false);

        // Create SimpleDateFormat using the obtained format string
        if (timestampFormatStr == null) {
            timestampFormatStr = DEFAULT_TIME_FORMAT;
        }

        try {
            timestampFormat = new SimpleDateFormat(timestampFormatStr);
        } catch (IllegalArgumentException e) {
            throw new LateDeliverablesTrackerConfigurationException("Invaid timestamp format value.", e);
        }

        // Read and parse notification interval
        String notificationIntervalStr = Helper.getPropertyValue(config, NOTIFICATION_INTERVAL_KEY,
            false, false);

        if (notificationIntervalStr != null) {
            notificationInterval = Helper.parseLong(notificationIntervalStr,
                NOTIFICATION_INTERVAL_KEY);
        }
    }

    /**
     * Initializes the given map. The map will be email subject templates or email body
     * templates.
     *
     * @param config
     *            the configuration object.
     * @param map
     *            the map to be initialized.
     * @param propertyKey
     *            the property key.
     * @param key
     *            the prefix of the key to get value.
     * @throws LateDeliverablesTrackerConfigurationException
     *             if configuration value is invalid.
     */
    private static void initMap(ConfigurationObject config, Map<Long, String> map, String propertyKey,
        String key) {
        if (propertyKey.startsWith(key)) {
            // Parse deliverable ID
            long deliverableId = Helper.parseLong(propertyKey.substring(key.length()),
                key + propertyKey);

            // Get email subject/body template text for this deliverable ID
            // will always exist, not matter required or not
            String templateText = Helper.getPropertyValue(config, propertyKey, true, false);
            // Put (deliverable ID; subject/body template) pair to the map
            map.put(deliverableId, templateText);
        }
    }

    /**
     * Creates <code>DBConnectionFactory</code> from configuration.
     *
     * @param config
     *            the configuration object.
     * @return the created <code>DBConnectionFactory</code> instance.
     * @throws LateDeliverablesTrackerConfigurationException
     *             if any error occurs when creating <code>DBConnectionFactory</code>.
     */
    private static DBConnectionFactory createDBConnectionFactory(ConfigurationObject config) {
        ConfigurationObject dbConnectionFactoryConfig = Helper.getChildConfig(config,
            DB_CONNECTION_FACTORY_CONFIG);

        // Create database connection factory using the extracted configuration
        try {
            return new DBConnectionFactoryImpl(dbConnectionFactoryConfig);
        } catch (UnknownConnectionException e) {
            throw new LateDeliverablesTrackerConfigurationException(
                "Fails to create database connection factory.", e);
        } catch (ConfigurationException e) {
            throw new LateDeliverablesTrackerConfigurationException(
                "Fails to create database connection factory.", e);
        }
    }

    /**
     * Gets all property keys.
     *
     * @param config
     *            the configuration object.
     * @return all property keys in configuration.
     * @throws LateDeliverablesTrackerConfigurationException
     *             if error occurs when accessing the configuration object.
     */
    private static String[] getAllKeys(ConfigurationObject config) {
        try {
            return config.getAllPropertyKeys();
        } catch (ConfigurationAccessException e) {
            throw new LateDeliverablesTrackerConfigurationException(
                "Fails to access the configuration object.", e);
        }
    }

    /**
     * Processes the given late deliverable. The actual actions to be performed depend on
     * the implementation.
     *
     * @param lateDeliverable
     *            the late deliverable to be processed.
     * @throws IllegalArgumentException
     *             if lateDeliverable is null, or any one of phase, project and
     *             deliverable of lateDeliverable is null, or the scheduled end
     *             date of phase is null.
     * @throws IllegalStateException
     *             if this class was not configured properly with use of
     *             {@link #configure(ConfigurationObject)} method.
     * @throws EmailSendingException
     *             if some error occurred when sending a notification email message.
     * @throws LateDeliverablesProcessingException
     *             if some other error occurred when processing a late deliverable.
     */
    public void processLateDeliverable(LateDeliverable lateDeliverable)
        throws LateDeliverablesProcessingException {
        final long start = System.currentTimeMillis();
        final String signature = CLASS_NAME + ".processLateDeliverable(LateDeliverable lateDeliverable)";
        Helper.logEntrance(log, signature, new String[] {"lateDeliverable"},
            new Object[] {lateDeliverable});

        // check argument
        checkArgument(lateDeliverable, signature);
        // check state
        checkState(signature);

        Deliverable deliverable = lateDeliverable.getDeliverable();
        Phase phase = lateDeliverable.getPhase();
        Date currentDeadline = phase.getScheduledEndDate();
        Project project = lateDeliverable.getProject();
        long deliverableId = deliverable.getId();
        boolean needToNotify = notificationDeliverableIds.contains(deliverableId);

        Connection connection = getConnection(signature);

        try {
            connection.setAutoCommit(false);

            long phaseId = phase.getId();
            long resourceId = deliverable.getResource();

            Object[] result = doQuery(SELECT_MAX_DEADLINE_SQL, connection, new Object[] {phaseId,
                resourceId}, false);

            boolean alreadyTracked = result[0] != null;
            boolean canSendNotification = false;
            boolean addTrackingRecord = false;
            Date recordDeadline = currentDeadline;

            if (alreadyTracked) {
                Date oldDeadline = (Timestamp) result[0];

                if (oldDeadline.compareTo(currentDeadline) < 0) {
                    // deadline was extended, but the user is late again
                    addTrackingRecord = true;
                    canSendNotification = true;
                } else if (needToNotify && (notificationInterval != 0)) {
                    recordDeadline = oldDeadline;
                    // Prepare statement for retrieving last notification time and
                    // "forgive" flag for this late deliverable
                    result = doQuery(SELECT_LAST_NOTIFICATION_TIME_FORGIVE_SQL, connection,
                        new Object[] {phaseId, resourceId, new Timestamp(recordDeadline.getTime())},
                        true);

                    // Get the previous notification timestamp
                    Date previousNotificationTime = (Timestamp) result[0];

                    // Get value of the "forgive" flag
                    boolean forgiveInd = (Boolean) result[1];

                    if (!forgiveInd
                        && (previousNotificationTime != null)
                        && ((System.currentTimeMillis() - previousNotificationTime.getTime())
                        >= (notificationInterval * THOUSAND))) {
                        // notificationInterval passed, need to send one more
                        // notification
                        canSendNotification = true;
                    }
                }
            } else {
                // the user is late for the first time with this deliverable
                addTrackingRecord = true;
                canSendNotification = true;
            }

            canSendNotification = canSendNotification && needToNotify;

            if (addTrackingRecord) {
                if (canSendNotification) {
                    doDMLQuery(INSERT_WITH_LAST_NOTIFICATION_SQL, connection, new Object[] {phaseId,
                        resourceId, deliverableId, new Timestamp(currentDeadline.getTime()),
                        new Timestamp(System.currentTimeMillis()), false,
                        new Timestamp(System.currentTimeMillis())});
                } else {
                    doDMLQuery(INSERT_SQL, connection, new Object[] {phaseId, resourceId, deliverableId,
                        new Timestamp(currentDeadline.getTime()),
                        new Timestamp(System.currentTimeMillis()), false});
                }

                // log data for record added to late_deliverables table
                Helper.logInfo(log, "late deliverable data : project id[" + project.getId() + "], phase id["
                    + phaseId + "]," + " resource id[" + resourceId + "], deliverable id[" + deliverableId + "].");
            } else if (canSendNotification) {
                doDMLQuery(UPDATE_SQL, connection, new Object[] {
                    new Timestamp(System.currentTimeMillis()), phaseId, resourceId,
                    new Timestamp(recordDeadline.getTime())});
            }

            if (!canSendNotification) {
                connection.commit();
                return;
            }

            sendEmail(resourceId, deliverableId, project, phase, deliverable);

            connection.commit();
        } catch (SQLException e) {
            rollback(connection, signature);
            throw Helper.logException(log, signature, new LateDeliverablesProcessingException(
                "SQL error occurs when processing the late deliverable.", e));
        } catch (LateDeliverablesProcessingException e) {
            rollback(connection, signature);
            throw Helper.logException(log, signature, e);
        } finally {
            closeConnection(connection, signature);
        }

        Helper.logExit(log, signature, null, start);
    }

    /**
     * Checks the given <code>lateDeliverable</code>.
     *
     * @param signature
     *            the method name.
     * @param lateDeliverable
     *            the late deliverable to be processed.
     * @throws IllegalArgumentException
     *             if lateDeliverable is null, or any one of phase, project and
     *             deliverable of lateDeliverable is null, or the scheduled end
     *             date of phase is null.
     */
    private void checkArgument(LateDeliverable lateDeliverable, String signature) {
        try {
            ExceptionUtils.checkNull(lateDeliverable, null, null,
                "The parameter 'lateDeliverable' should not be null.");
            ExceptionUtils.checkNull(lateDeliverable.getPhase(), null, null,
                "The phase of lateDeliverable should not be null.");
            ExceptionUtils.checkNull(lateDeliverable.getProject(), null, null,
                "The project of lateDeliverable should not be null.");
            ExceptionUtils.checkNull(lateDeliverable.getDeliverable(), null, null,
            "The deliverable of lateDeliverable should not be null.");
            ExceptionUtils.checkNull(lateDeliverable.getPhase().getScheduledEndDate(), null, null,
                "The scheduledEndDate of phase of lateDeliverable should not be null.");
        } catch (IllegalArgumentException e) {
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Checks all required fields have been set.
     *
     * @param signature
     *            the method name.
     * @throws IllegalStateException
     *             if this class was not configured properly with use of
     *             {@link #configure(ConfigurationObject)} method.
     */
    private void checkState(String signature) {
        Helper.checkState(timestampFormat, "timestampFormat", log, signature);
        Helper.checkState(dbConnectionFactory, "dbConnectionFactory", log, signature);
        Helper.checkState(emailSendingUtility, "emailSendingUtility", log, signature);
        Helper.checkState(notificationDeliverableIds, "notificationDeliverableIds", log, signature);
        Helper.checkState(emailSubjectTemplateTexts, "emailSubjectTemplateTexts", log, signature);
        Helper.checkState(emailBodyTemplatePaths, "emailBodyTemplatePaths", log, signature);
        Helper.checkState(defaultEmailSubjectTemplateText, "defaultEmailSubjectTemplateText", log,
            signature);
        Helper.checkState(defaultEmailBodyTemplatePath, "defaultEmailBodyTemplatePath", log, signature);
        Helper.checkState(resourceManager, "resourceManager", log, signature);
        Helper.checkState(userRetrieval, "userRetrieval", log, signature);
    }

    /**
     * Does select query and return result as object array. The result will always
     * contains two elements, if the <code>hasSecondColumn</code> is false, the second
     * element of result is null.
     *
     * @param query
     *            the query statement.
     * @param connection
     *            the database connection.
     * @param parameters
     *            the parameters to set.
     * @param hasSecondColumn
     *            whether the result set has second column value.
     * @return the result from the query.
     * @throws SQLException
     *             if any error occurs.
     * @throws LateDeliverablesProcessingException
     *             if query result does not contain record if <code>hasSecondColumn</code>
     *             is <code>true</code>.
     */
    private static Object[] doQuery(String query, Connection connection, Object[] parameters,
        boolean hasSecondColumn) throws SQLException, LateDeliverablesProcessingException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(query);

            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i] instanceof Timestamp) {
                    ps.setTimestamp(i + 1, (Timestamp) parameters[i]);
                } else {
                    ps.setLong(i + 1, (Long) parameters[i]);
                }
            }

            rs = ps.executeQuery();

            if (!rs.next() && hasSecondColumn) {
                throw new LateDeliverablesProcessingException("Get last notified and"
                    + " forgive query should contain one result.");
            }

            Object[] result = new Object[2];
            result[0] = rs.getTimestamp(1);

            if (hasSecondColumn) {
                result[1] = rs.getBoolean(2);
            }

            return result;
        } finally {
            try {
                closeResultSet(rs);
            } finally {
                closeStatement(ps);
            }
        }
    }

    /**
     * Does update or insert query.
     *
     * @param query
     *            the sql statement to do.
     * @param connection
     *            the database connection.
     * @param parameters
     *            the parameters to set.
     * @throws SQLException
     *             if any error occurs.
     */
    private static void doDMLQuery(String query, Connection connection, Object[] parameters)
        throws SQLException {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(query);

            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i] instanceof Timestamp) {
                    ps.setTimestamp(i + 1, (Timestamp) parameters[i]);
                } else if (parameters[i] instanceof Long) {
                    ps.setLong(i + 1, (Long) parameters[i]);
                } else {
                    ps.setBoolean(i + 1, (Boolean) parameters[i]);
                }
            }

            ps.executeUpdate();
        } finally {
            closeStatement(ps);
        }
    }

    /**
     * Closes given statement.
     *
     * @param statement
     *            the statement to close.
     * @throws SQLException
     *             if any error occurs.
     */
    private static void closeStatement(PreparedStatement statement) throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }

    /**
     * Closes given result set.
     *
     * @param resultSet
     *            the result set to close.
     * @throws SQLException
     *             if any error occurs.
     */
    private static void closeResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
    }

    /**
     * Closes the given database connection.
     *
     * @param connection
     *            the database connection.
     * @param signature
     *            the method name.
     * @throws LateDeliverablesProcessingException
     *             if SQL error occurs when closing connection.
     */
    private void closeConnection(Connection connection, String signature)
        throws LateDeliverablesProcessingException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw Helper.logException(log, signature, new LateDeliverablesProcessingException(
                    "Fails to close connection.", e));
            }
        }
    }

    /**
     * Undoes all changes in current transaction.
     *
     * @param connection
     *            the database connection.
     * @param signature
     *            the method name.
     */
    private void rollback(Connection connection, String signature) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            // log and ignore
            Helper.logException(log, signature, e);
        }
    }

    /**
     * Gets necessary information of email and send it.
     *
     * @param resourceId
     *            the id of resource.
     * @param deliverableId
     *            the id of deliverable.
     * @param project
     *            the project.
     * @param phase
     *            the phase.
     * @param deliverable
     *            the deliverable.
     * @throws EmailSendingException
     *             if some error occurred when sending a notification email message.
     * @throws LateDeliverablesProcessingException
     *             if fails to get the required information of email.
     */
    private void sendEmail(long resourceId, long deliverableId, Project project, Phase phase,
        Deliverable deliverable) throws LateDeliverablesProcessingException {
        String recipient = getEmailAddressForResource(resourceId);
        Map<String, String> params = prepareParameters(project, phase, deliverable);

        String subjectTemplateText;

        if (emailSubjectTemplateTexts.containsKey(deliverableId)) {
            // Get email subject template to be used
            subjectTemplateText = emailSubjectTemplateTexts.get(deliverableId);
        } else {
            subjectTemplateText = defaultEmailSubjectTemplateText;
        }

        String bodyTemplatePath;

        if (emailBodyTemplatePaths.containsKey(deliverableId)) {
            // Get email body template path to be used:
            bodyTemplatePath = emailBodyTemplatePaths.get(deliverableId);
        } else {
            bodyTemplatePath = defaultEmailBodyTemplatePath;
        }

        // Send warning email to the user
        emailSendingUtility.sendEmail(subjectTemplateText, bodyTemplatePath, recipient, params);
    }

    /**
     * Gets the database connection.
     *
     * @param signature
     *            the method name.
     * @return the database connection
     * @throws LateDeliverablesProcessingException
     *             if any error occurs.
     */
    private Connection getConnection(String signature) throws LateDeliverablesProcessingException {
        try {
            if (connectionName == null) {
                return this.dbConnectionFactory.createConnection();
            } else {
                return this.dbConnectionFactory.createConnection(connectionName);
            }
        } catch (DBConnectionException e) {
            throw Helper.logException(log, signature, new LateDeliverablesProcessingException(
                "Fails to create database connection.", e));
        }
    }

    /**
     * Retrieves the email address for resource with the given ID.
     *
     * @param resourceId
     *            the ID of the resource.
     * @return the retrieved email address for resource (not null).
     * @throws LateDeliverablesProcessingException
     *             if any error occurred.
     */
    private String getEmailAddressForResource(long resourceId)
        throws LateDeliverablesProcessingException {
        Resource resource;

        try {
            resource = resourceManager.getResource(resourceId);
        } catch (ResourcePersistenceException e) {
            throw new LateDeliverablesProcessingException("Fails to get resource.", e);
        }

        if (resource == null) {
            throw new LateDeliverablesProcessingException("Resource with id[" + resourceId
                + "] not exist.");
        }

        long userId;

        try {
            userId = Long.parseLong((String) resource.getProperty("External Reference ID"));
        } catch (NumberFormatException e) {
            throw new LateDeliverablesProcessingException("External Reference ID property value"
                + " of resource can not parse to long.", e);
        } catch (ClassCastException e) {
            throw new LateDeliverablesProcessingException("External Reference ID property value"
                + " is not type of String.", e);
        }

        ExternalUser user;

        try {
            user = userRetrieval.retrieveUser(userId);
        } catch (RetrievalException e) {
            throw new LateDeliverablesProcessingException("Fails to retrieve external user for id : "
                + userId, e);
        }

        if (user == null) {
            throw new LateDeliverablesProcessingException("External user with id[" + userId
                + "] not exist.");
        }

        String email = user.getEmail();

        if (email == null) {
            throw new LateDeliverablesProcessingException("email address of resource is null.");
        }

        return email;
    }

    /**
     * Prepares the parameters to be used in the email subject/body template.
     *
     * @param project
     *            the project for the late deliverable
     * @param phase
     *            the phase for the late deliverable
     * @param deliverable
     *            the late deliverable
     * @return the map with prepared parameters (keys are parameter names, map values
     *         parameter values; not null, doesn't contain null/empty key or null value).
     * @throws LateDeliverablesProcessingException
     *             if any error occurred.
     */
    private Map<String, String> prepareParameters(Project project, Phase phase, Deliverable deliverable)
        throws LateDeliverablesProcessingException {
        Map<String, String> result = new HashMap<String, String>();

        // Get project name from the properties:
        String projectName = getPropertyValue(project, "Project Name");
        result.put("PROJECT_NAME", projectName);

        // Get project version from the properties:
        String projectVersion = getPropertyValue(project, "Project Version");
        result.put("PROJECT_VERSION", projectVersion);

        long projectId = project.getId();
        result.put("PROJECT_ID", String.valueOf(projectId));

        PhaseType phaseType = phase.getPhaseType();
        checkNull(phaseType, "phaseType of phase");

        String phaseName = phaseType.getName();
        checkNull(phaseName, "name of phaseType");
        result.put("PHASE_NAME", phaseName);

        // Get deliverable name:
        String deliverableName = deliverable.getName();
        checkNull(deliverableName, "name of deliverable");
        result.put("DELIVERABLE_NAME", deliverableName);

        // Get scheduled end date of the phase (deadline):
        Date phaseEndDate = phase.getScheduledEndDate();
        checkNull(phaseEndDate, "scheduledEndDate of phase");

        // Convert date/time to string:
        String phaseEndDateStr = timestampFormat.format(phaseEndDate);
        result.put("DEADLINE", phaseEndDateStr);
        // Construct and put delay string to the result map
        result.put("DELAY", getCurrentDelayString(phaseEndDate));

        return result;
    }

    /**
     * Gets the project property value for the specified property name.
     *
     * @param project
     *            the project.
     * @param propertyName
     *            the property name.
     * @return the property value.
     * @throws LateDeliverablesProcessingException
     *             if the value is <code>null</code> or the value is not
     *             <code>String</code> type.
     */
    private static String getPropertyValue(Project project, String propertyName)
        throws LateDeliverablesProcessingException {
        try {
            String result = (String) project.getProperty(propertyName);

            if (result == null) {
                throw new LateDeliverablesProcessingException("The property value of [" + propertyName
                    + "] should not be null.");
            }

            return result;
        } catch (ClassCastException e) {
            throw new LateDeliverablesProcessingException("The property value of [" + propertyName
                + "] is not of String type.", e);
        }
    }

    /**
     * Checks the given value for null.
     *
     * @param value
     *            the value to check.
     * @param name
     *            the name of the value.
     * @throws LateDeliverablesProcessingException
     *             if the given value is <code>null</code>.
     */
    private static void checkNull(Object value, String name) throws LateDeliverablesProcessingException {
        if (value == null) {
            throw new LateDeliverablesProcessingException("The " + name + " should not be null.");
        }
    }

    /**
     * Retrieves the delay string for the specified deadline. The delay string has format
     * &quot;[[X day[s] ]Y hour[s] ]Z minute[s]&quot; and is counted as a difference
     * between the deadline and the current time. The minimum delay is assumed to be equal
     * to one minute.
     *
     * @param deadline
     *            the deadline.
     * @return the constructed current delay string (not null).
     */
    private static String getCurrentDelayString(Date deadline) {
        // delay in milliseconds
        long delay = System.currentTimeMillis() - deadline.getTime();

        delay /= MINUTE; // delay in minutes;

        if (delay == 0) {
            delay = 1;
        }

        long delayMinutes = delay % SIXTY;
        delay /= SIXTY; // delay in hours

        long delayHours = delay % TWENTY_FOUR;
        delay /= TWENTY_FOUR; // delay in days

        long delayDays = delay;
        StringBuilder sb = new StringBuilder();

        if (delayDays != 0) {
            sb.append(delayDays).append(' ').append("day");

            if (delayDays != 1) {
                sb.append('s');
            }

            sb.append(' ');
        }

        if (delayHours != 0) {
            sb.append(delayHours).append(' ').append("hour");

            if (delayHours != 1) {
                sb.append('s');
            }

            sb.append(' ');
        }

        if (delayMinutes != 0) {
            sb.append(delayMinutes).append(' ').append("minute");

            if (delayMinutes != 1) {
                sb.append('s');
            }
        }

        return sb.toString().trim();
    }
}
