/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import com.cronos.onlinereview.external.ExternalUser;
import com.cronos.onlinereview.external.RetrievalException;
import com.cronos.onlinereview.phases.lookup.NotificationTypeLookupUtility;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.management.resource.search.ResourceFilterBuilder;
import com.topcoder.management.resource.search.ResourceRoleFilterBuilder;

import com.topcoder.message.email.AddressException;
import com.topcoder.message.email.EmailEngine;
import com.topcoder.message.email.SendingException;
import com.topcoder.message.email.TCSEmailMessage;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;

import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.Template;
import com.topcoder.util.file.TemplateDataFormatException;
import com.topcoder.util.file.TemplateFormatException;
import com.topcoder.util.file.fieldconfig.Condition;
import com.topcoder.util.file.fieldconfig.Field;
import com.topcoder.util.file.fieldconfig.Loop;
import com.topcoder.util.file.fieldconfig.Node;
import com.topcoder.util.file.fieldconfig.NodeList;
import com.topcoder.util.file.fieldconfig.TemplateFields;
import com.topcoder.util.file.templatesource.FileTemplateSource;
import com.topcoder.util.file.templatesource.TemplateSourceException;

import java.sql.Connection;
import java.sql.SQLException;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * This abstract class is used as a base class for all phase handlers. This class contains logic in the constructor to
 * load configuration settings for a phase handler. Settings includes database connection, email template and email
 * related information.
 * </p>
 *
 * <p>
 * Sample configuration file is given below:
 * <pre>
 *    &lt;Config name="com.cronos.onlinereview.phases.AbstractPhaseHandler"&gt;
 *        &lt;Property name="ConnectionName"&gt;
 *            &lt;Value&gt;informix_connection&lt;/Value&gt;
 *        &lt;/Property&gt;
 *        &lt;Property name="ManagerHelperNamespace"&gt;
 *            &lt;Value&gt;com.cronos.onlinereview.phases.ManagerHelper&lt;/Value&gt;
 *        &lt;/Property&gt;
 *        &lt;Property name="ConnectionFactoryNS"&gt;
 *            &lt;Value&gt;com.topcoder.db.connectionfactory.DBConnectionFactoryImpl&lt;/Value&gt;
 *        &lt;/Property&gt;
 *        &lt;Property name="Schemes"&gt;
 *            &lt;Property name="DefaultScheme"&gt;
 *                &lt;Value&gt;*&lt;/Value&gt;
 *            &lt;/Property&gt;
 *            &lt;Property name="ExtendedScheme"&gt;
 *                &lt;Value&gt;Manager&lt;/Value&gt;
 *                &lt;Value&gt;Observer&lt;/Value&gt;
 *            &lt;/Property&gt;
 *        &lt;/Property&gt;
 *        &lt;Property name="DefaultScheme"&gt;
 *            &lt;Property name="StartPhaseEmail"&gt;
 *                &lt;Property name="SendEmail"&gt;
 *                    &lt;Value&gt;yes&lt;/Value&gt;
 *                &lt;/Property&gt;
 *                &lt;Property name="EmailTemplateName"&gt;
 *                    &lt;Value&gt;&phasesEmailTemplate;&lt;/Value&gt;
 *                &lt;/Property&gt;
 *                &lt;Property name="EmailSubject"&gt;
 *                    &lt;Value&gt;Phase Start&lt;/Value&gt;
 *                &lt;/Property&gt;
 *                &lt;Property name="EmailFromAddress"&gt;
 *                    &lt;Value&gt;&notificationEmailFromAddress;&lt;/Value&gt;
 *                &lt;/Property&gt;
 *                &lt;Property name="Priority"&gt;
 *                    &lt;Value&gt;0&lt;/Value&gt;
 *                &lt;/Property&gt;
 *            &lt;/Property&gt;
 *            &lt;Property name="EndPhaseEmail"&gt;
 *                &lt;Property name="SendEmail"&gt;
 *                    &lt;Value&gt;yes&lt;/Value&gt;
 *                &lt;/Property&gt;
 *                &lt;Property name="EmailTemplateName"&gt;
 *                    &lt;Value&gt;&phasesEmailTemplate;&lt;/Value&gt;
 *                &lt;/Property&gt;
 *                &lt;Property name="EmailSubject"&gt;
 *                    &lt;Value&gt;Phase End&lt;/Value&gt;
 *                &lt;/Property&gt;
 *                &lt;Property name="EmailFromAddress"&gt;
 *                    &lt;Value&gt;&notificationEmailFromAddress;&lt;/Value&gt;
 *                &lt;/Property&gt;
 *                &lt;Property name="Priority"&gt;
 *                    &lt;Value&gt;0&lt;/Value&gt;
 *                &lt;/Property&gt;
 *            &lt;/Property&gt;
 *        &lt;/Property&gt;
 *        &lt;Property name="ExtendedScheme"&gt;
 *            &lt;Property name="StartPhaseEmail"&gt;
 *                &lt;Property name="SendEmail"&gt;
 *                    &lt;Value&gt;yes&lt;/Value&gt;
 *                &lt;/Property&gt;
 *                &lt;Property name="EmailTemplateName"&gt;
 *                    &lt;Value&gt;&managerNotificationEmailTemplatesBase;/registration/start.txt&lt;/Value&gt;
 *                &lt;/Property&gt;
 *                &lt;Property name="EmailSubject"&gt;
 *                    &lt;Value&gt;Phase Start&lt;/Value&gt;
 *                &lt;/Property&gt;
 *                &lt;Property name="EmailFromAddress"&gt;
 *                    &lt;Value&gt;&notificationEmailFromAddress;&lt;/Value&gt;
 *                &lt;/Property&gt;
 *                &lt;Property name="Priority"&gt;
 *                    &lt;Value&gt;1&lt;/Value&gt;
 *                &lt;/Property&gt;
 *            &lt;/Property&gt;
 *            &lt;Property name="EndPhaseEmail"&gt;
 *                &lt;Property name="SendEmail"&gt;
 *                    &lt;Value&gt;yes&lt;/Value&gt;
 *                &lt;/Property&gt;
 *                &lt;Property name="EmailTemplateName"&gt;
 *                    &lt;Value&gt;&managerNotificationEmailTemplatesBase;/registration/end.txt&lt;/Value&gt;
 *                &lt;/Property&gt;
 *                &lt;Property name="EmailSubject"&gt;
 *                    &lt;Value&gt;Phase End&lt;/Value&gt;
 *                &lt;/Property&gt;
 *                &lt;Property name="EmailFromAddress"&gt;
 *                    &lt;Value&gt;&notificationEmailFromAddress;&lt;/Value&gt;
 *                &lt;/Property&gt;
 *                &lt;Property name="Priority"&gt;
 *                    &lt;Value&gt;1&lt;/Value&gt;
 *                &lt;/Property&gt;
 *            &lt;/Property&gt;
 *        &lt;/Property&gt;
 *    &lt;/Config&gt;
 * </pre>
 * </p>
 *
 * <p>
 * Sample email template is given below:
 * <pre>
 * %PHASE_TIMESTAMP{Phase timestamp}%&lt;br/&gt;
 * Handle\: %USER_HANDLE{User handle}%&lt;br/&gt;
 * Contest\: &lt;a href\="%OR_LINK%"&gt;%PROJECT_NAME{Project name}%&lt;/a&gt;&lt;br/&gt;
 * Version\: %PROJECT_VERSION{Project version}%&lt;br/&gt;
 * This is the notification about %PHASE_OPERATION{The phase operation - start/end}% of
 *  the %PHASE_TYPE{Phase type}% phase.&lt;br/&gt;
 * </pre>
 * </p>
 *
 * <p>
 * Thread safety: This class is thread safe because it is immutable.
 * </p>
 *
 * <p>
 * Version 1.1 (Appeals Early Completion Release Assembly 1.0) Change notes:
 * <ol>
 * <li>
 * Changed timeline notification emails subject.
 * </li>
 * <li>
 * Added new fields to timeline notification emails.
 * </li>
 * <li>
 * Property projectDetailsBaseURL added.
 * </li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.2 changes note:
 * <ul>
 * <li>
 * Added capability to support different email template for different role (e.g. Submitter, Reviewer, Manager, etc).
 * </li>
 * <li>
 * Supporting Document Generator 2.1, which has the Loop and Condition.
 * </li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.6 changes note:
 * <ul>
 * <li>
 * Added link to studio contest for email template.
 * </li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.6.1 changes note:
 * <ul>
 * <li>
 * Email templates now depend on the project category type.
 * </li>
 * </ul>
 * </p>
 * @author tuenm, bose_java, pulky, argolite, waits, FireIce, microsky, lmmortal, VolodymyrK
 * @version 1.6.1
 */
public abstract class AbstractPhaseHandler implements PhaseHandler {
    /** constant for "Project Name" project info. */
    private static final String PROJECT_NAME = "Project Name";

    /** constant for "Project Version" project info. */
    private static final String PROJECT_VERSION = "Project Version";

    /** constant for lookup value for notification type id. */
    private static final String NOTIFICATION_TYPE_TIMELINE_NOTIFICATION = "Timeline Notification";

    /** Property name constant for connection factory namespace. */
    private static final String PROP_CONNECTION_FACTORY_NS = "ConnectionFactoryNS";

    /** Property name constant for connection name. */
    private static final String PROP_CONNECTION_NAME = "ConnectionName";

    /** Property name constant for manager helper namespace. */
    private static final String PROP_MANAGER_HELPER_NAMESPACE = "ManagerHelperNamespace";

    /** Format for property name constant for "XXPhaseEmail", XX could be 'Start' or 'End'. */
    private static final String PROP_PHASE_EMAIL = "{0}PhaseEmail";

    /** Format for property name constant for "XXPhaseEmail.EmailTemplateName", XX could be 'Start' or 'End'. */
    private static final String PROP_EMAIL_TEMPLATE_NAME = "{0}PhaseEmail.EmailTemplateName";

    /** Format for property name constant for "XXPhaseEmail.EmailSubject", XX could be 'Start' or 'End'. */
    private static final String PROP_EMAIL_SUBJECT = "{0}PhaseEmail.EmailSubject";

    /** Format for property name constant for "XXPhaseEmail.EmailFromAddress", XX could be 'Start' or 'End'. */
    private static final String PROP_EMAIL_FROM_ADDRESS = "{0}PhaseEmail.EmailFromAddress";

    /** Format for property name constant for "XXPhaseEmail.SendEmail", XX could be 'Start' or 'End'. */
    private static final String PROP_SEND_EMAIL = "{0}PhaseEmail.SendEmail";

    /** Constant for start phase. */
    private static final String START = "Start";

    /** Constant for end phase. */
    private static final String END = "End";

    /** format for the email timestamp. Will format as "Fri, Jul 28, 2006 01:34 PM EST". */
    private static final String EMAIL_TIMESTAMP_FORMAT = "EEE, MMM d, yyyy hh:mm a z";

    /**
     * The factory instance used to create connection to the database. It is initialized in the constructor using
     * DBConnectionFactory component and never changed after that. It will be used in various persistence methods of
     * this project. This field is never null.
     */
    private final DBConnectionFactory factory;

    /**
     * <p>
     * This field is used to retrieve manager instances to use in phase handlers. It is initialized in the constructor
     * and never change after that. It is never null.
     * </p>
     */
    private final ManagerHelper managerHelper;

    /**
     * Represents the connection name used to create connection to the database using DBConnectionFactory. This
     * variable can be null. When it is null, default connection is be created. This variable can be initialized in
     * the constructor and never change after that.
     */
    private final String connectionName;

    /**
     * This constant stores Online Review's project details page URL.
     *
     * @since 1.1
     */
    private final String projectDetailsBaseURL;

    /**
     * This constant stores Studio project details page URL.
     *
     * @since 1.6
     */
    private final String studioProjectDetailsBaseURL;

    /**
     * <p>
     * The list of the configured email schemes.
     * </p>
     *
     * @since 1.6.1
     */
    private final List<EmailScheme> emailSchemes;

    /**
     * <p>
     * Creates a new instance of AbstractPhaseHandler using the given namespace for loading configuration settings.
     * </p>
     *
     * <p>
     * It initializes the DB connection factory, connection name, Manager Helper, start and end phase email variables
     * from configuration properties
     * </p>
     *
     * <p>
     * Update in version 1.2: Now each role can have its own email options. And there will be some default setting
     * for 'start'/'end' phases.
     * </p>
     *
     * @param namespace the namespace to load configuration settings from.
     *
     * @throws ConfigurationException if errors occurred while loading configuration settings or required properties
     *         missing.
     * @throws IllegalArgumentException if the input is null or empty string.
     */
    protected AbstractPhaseHandler(String namespace) throws ConfigurationException {
        PhasesHelper.checkString(namespace, "namespace");

        // initialize DBConnectionFactory from given namespace, throw exception
        // if property is missing.
        this.factory = PhasesHelper.createDBConnectionFactory(namespace, PROP_CONNECTION_FACTORY_NS);

        // initialize ManagerHelper from given namespace if provided.
        String managerHelperNamespace = PhasesHelper.getPropertyValue(namespace, PROP_MANAGER_HELPER_NAMESPACE, false);

        if (PhasesHelper.isStringNullOrEmpty(managerHelperNamespace)) {
            this.managerHelper = new ManagerHelper();
        } else {
            this.managerHelper = new ManagerHelper(managerHelperNamespace);
        }

        // initialize connectionName with property value if provided.
        String connName = PhasesHelper.getPropertyValue(namespace, PROP_CONNECTION_NAME, false);

        if (!PhasesHelper.isStringNullOrEmpty(connName)) {
            this.connectionName = connName;
        } else {
            this.connectionName = null;
        }

        // load the 'Schemes' property
        emailSchemes = getEmailSchemes(namespace);

        // get project details base url
        projectDetailsBaseURL = managerHelper.getProjectDetailsBaseURL();

        // get studio project details base url
        studioProjectDetailsBaseURL = managerHelper.getStudioProjectDetailsBaseURL();
    }

    /**
     * <p>
     * Send email to the external users that are registered to be notified on the phase change.
     * </p>
     *
     * <p>
     * Now each role can have its own email options. If not set for that role, using the default setting.
     * </p>
     *
     * @param phase The current phase. must not be null.
     * @param values The values map. This is a map from String into Object. The key is the template field name and the
     *        value is the template field value.
     *
     * @throws IllegalArgumentException if any argument is null or map contains empty/null key/value.
     * @throws PhaseHandlingException if there was a problem when sending email.
     * @since 1.2
     */
    public void sendEmail(Phase phase, Map<String, Object> values) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkValuesMap(values);

        // Do not send any e-mails for phases whose duration is zero
        if (phase.getLength() <= 0) {
            return;
        }

        final Date scheduledStartDate = phase.getScheduledStartDate();
        final Date scheduledEndDate = phase.getScheduledEndDate();

        // Check phase's scheduled start & end dates to be sure
        if ((scheduledStartDate != null) && (scheduledEndDate != null)
                && !scheduledStartDate.before(scheduledEndDate)) {
            return;
        }

        // Determine whether phase is starting or ending...
        PhaseStatus status = phase.getPhaseStatus();

        if (PhasesHelper.isPhaseToStart(status)) {
            sendEmail(phase, values, true);
        } else if (PhasesHelper.isPhaseToEnd(status)) {
            sendEmail(phase, values, false);
        }
    }

    /**
     * <p>
     * Sends email at the start/end of a phase. If the phase is in "Scheduled" state, start phase email will be sent.
     * If the phase is in "Open" state, end phase email will be sent. If the phase is in any other state, this method
     * does nothing. Besides, this method will not send any email if it is not configured to do so.
     * </p>
     *
     * <p>
     * This method first retrieves all the ExternalUsers to whom the notification is to be sent as well as the project
     * information. It then instantiates document generator to create the email body content and sends out an email to
     * each user using the Email Engine component.
     * </p>
     *
     * <p>
     * This method will directly invoking the sendEmail(Phase, Map) method with empty map.
     * </p>
     *
     * <p>
     * Update in version 1.2: Now each role can have its own email options. If not set for that role, using the
     * default setting.
     * </p>
     *
     * @param phase phase instance.
     *
     * @throws IllegalArgumentException if the input is null or empty string.
     * @throws PhaseHandlingException if there was a problem when sending email.
     * @see AbstractPhaseHandler#sendEmail(Phase, Map)
     */
    protected void sendEmail(Phase phase) throws PhaseHandlingException {
        sendEmail(phase, new HashMap<String, Object>());
    }

    /**
     * <p>
     * This method is used by the subclass to create the connection to access database. The connection needs to be
     * closed after use.
     * </p>
     *
     * @return The database connection.
     *
     * @throws PhaseHandlingException if connection could not be created.
     */
    protected Connection createConnection() throws PhaseHandlingException {
        try {
            if (connectionName == null) {
                return factory.createConnection();
            } else {
                return factory.createConnection(connectionName);
            }
        } catch (DBConnectionException ex) {
            throw new PhaseHandlingException("Could not create connection", ex);
        }
    }

    /**
     * <p>
     * This method is used by the subclass to get the ManagerHelper instance.
     * </p>
     *
     * @return the ManagerHelper instance.
     */
    protected ManagerHelper getManagerHelper() {
        return managerHelper;
    }

    /**
     * Sends email at the start/end of a phase. This method first retrieves all the ExternalUsers to whom the
     * notification is to be sent as well as the project information. It then instantiates document generator to
     * create the email body content and sends out an email to each user using the Email Engine component.
     *
     * <p>
     * Update in version 1.2: Now each role can have its own email options. If not set for that role, using the
     * default setting.
     * </p>
     *
     * @param phase phase instance.
     * @param values the values map to look up the fields in template
     * @param bStart true if phase is to start, false if phase is to end.
     *
     * @throws PhaseHandlingException if there was an error retrieving information or sending email.
     */
    private void sendEmail(Phase phase, Map<String, Object> values, boolean bStart)
        throws PhaseHandlingException {
        Connection conn = createConnection();

        Project project = null;

        // maps user IDs to EmailScheme
        Map<Long, EmailScheme> userEmailSchemes = new HashMap<Long, EmailScheme>();
        ResourceManager rm = managerHelper.getResourceManager();

        try {
            long projectId = phase.getProject().getId();

            // Lookup notification type id for "Timeline Notification"
            long notificationTypeId = NotificationTypeLookupUtility.lookUpId(conn,
                    NOTIFICATION_TYPE_TIMELINE_NOTIFICATION);
            // retrieve users to be notified
            long[] externalIds = rm.getNotifications(projectId, notificationTypeId);
            // retrieve project information
            project = managerHelper.getProjectManager().getProject(projectId);
            long projectTypeId = project.getProjectCategory().getProjectType().getId();

            Resource[] resources = rm.searchResources(ResourceFilterBuilder.createProjectIdFilter(projectId));

            for (Resource resource : resources) {
                long externalId = PhasesHelper.getIntegerProp(resource, PhasesHelper.EXTERNAL_REFERENCE_ID);
                if (!contains(externalIds, externalId)) {
                    continue;
                }
                
                EmailScheme emailScheme = getEmailSchemeForResource(resource, projectTypeId);
                if (emailScheme == null) {
                    continue;
                }
                
                // since one user could have more than one role in project, we only need to set out
                // one email for all the roles of the same user,
                // we need to find the email scheme with largest priority here
                EmailScheme oldScheme = userEmailSchemes.get(externalId);
                if (oldScheme == null || oldScheme.getPriority() < emailScheme.getPriority()) {
                    userEmailSchemes.put(externalId, emailScheme);
                }
            }
        } catch (SQLException ex) {
            throw new PhaseHandlingException("Could not lookup project info type id for "
                    + NOTIFICATION_TYPE_TIMELINE_NOTIFICATION, ex);
        } catch (ResourcePersistenceException ex) {
            throw new PhaseHandlingException("There was a problem with resource retrieval", ex);
        } catch (PersistenceException ex) {
            throw new PhaseHandlingException("There was a problem with project retrieval", ex);
        } catch (SearchBuilderConfigurationException e) {
            throw new PhaseHandlingException("There was a problem with project retrieval", e);
        } catch (SearchBuilderException e) {
            throw new PhaseHandlingException("There was a problem with project retrieval", e);
        } catch (Exception e) {
            throw new PhaseHandlingException("Problem with retrieving information.", e);
        } finally {
            PhasesHelper.closeConnection(conn);
        }


        try {
            // prepare email content and send email each user
            for (long userID : userEmailSchemes.keySet()) {
                EmailScheme emailScheme = userEmailSchemes.get(userID);
                EmailOptions options = bStart ? emailScheme.getStartEmailOptions()
                                              : emailScheme.getEndEmailOptions();

                if (options == null || !options.isSend()) {
                    continue;
                }

                // instantiate document generator instance
                DocumentGenerator docGenerator = new DocumentGenerator();
                docGenerator.setDefaultTemplateSource(new FileTemplateSource());

                Template template = docGenerator.getTemplate(options.getTemplateName());
                ExternalUser user = managerHelper.getUserRetrieval().retrieveUser(userID);

                // for each external user, set field values
                TemplateFields root = setTemplateFieldValues(docGenerator.getFields(template), user, project, phase,
                        values, bStart);

                TCSEmailMessage message = new TCSEmailMessage();
                message.setSubject(options.getSubject() + ": " + (String) project.getProperty(PROJECT_NAME));
                message.setBody(docGenerator.applyTemplate(root));
                message.setFromAddress(options.getFromAddress());
                message.setToAddress(user.getEmail(), TCSEmailMessage.TO);
                message.setContentType("text/html");
                EmailEngine.send(message);
            }
        } catch (PhaseHandlingException e) {
            throw e;
        } catch (ConfigManagerException e) {
            throw new PhaseHandlingException("There was a configuration error", e);
        } catch (TemplateSourceException e) {
            throw new PhaseHandlingException("Problem with template source", e);
        } catch (TemplateFormatException e) {
            throw new PhaseHandlingException("Problem with template format", e);
        } catch (TemplateDataFormatException e) {
            throw new PhaseHandlingException("Problem with template data format", e);
        } catch (AddressException e) {
            throw new PhaseHandlingException("Problem with email address", e);
        } catch (SendingException e) {
            throw new PhaseHandlingException("Problem with sending email", e);
        } catch (RetrievalException e) {
            throw new PhaseHandlingException("Fail to retrieve the user info when sending email.", e);
        } catch (Exception e) {
            throw new PhaseHandlingException("Problem with sending email", e);
        }
    }
    
	/**
	* Finds email scheme for passed resource.
	*
    * @param resource
	*       the resource to get email shceme for
	* @param projectTypeId
	*       the project type id
	* @return the email scheme for resource. null if not found.
	* @since 1.6.1.
	*/
    private EmailScheme getEmailSchemeForResource(Resource resource, long projectTypeId) {
        EmailScheme priorityEmailScheme = null;
        for (EmailScheme emailScheme : emailSchemes) {
            boolean containsProjectType = emailScheme.getProjectTypes().contains("*") ||
                emailScheme.getProjectTypes().contains(""+projectTypeId);
            boolean containsRole = emailScheme.getRoles().contains("*") ||
                emailScheme.getRoles().contains(resource.getResourceRole().getName());
                
            if (containsProjectType && containsRole) {
                if (priorityEmailScheme == null || priorityEmailScheme.getPriority() < emailScheme.getPriority()) {
                    priorityEmailScheme = emailScheme;
                }
            }
        }
        
        return priorityEmailScheme;
    }
    
    /**
     * This method sets the values of the template fields with user, project information and lookup values
     * based on bStart variable which is true if phase is to start, false if phase is to end.
     *
     * @param root template fields.
     * @param user the user to be notified.
     * @param project project instance.
     * @param phase phase instance.
     * @param values the values map to look up
     * @param bStart true if phase is to start, false if phase is to end.
     *
     * @return template fields with data set.
     * @throws PhaseHandlingException if the values element for the loop is invalid
     */
    private TemplateFields setTemplateFieldValues(TemplateFields root, ExternalUser user, Project project, Phase phase,
        Map<String, Object> values, boolean bStart) throws PhaseHandlingException {
        setNodes(root.getNodes(), user, project, phase, values, bStart);

        return root;
    }
    /**
     * This method sets the values of the nodes with user, project information and lookup values
     * based on bStart variable which is true if phase is to start, false if phase is to end.
     *
     * @param nodes the nodes in template
     * @param user the user to be notified.
     * @param project project instance.
     * @param phase phase instance.
     * @param values the values map to look up
     * @param bStart true if phase is to start, false if phase is to end.
     *
     * @throws PhaseHandlingException if the values element for the loop is invalid
     */
    private void setNodes(Node[] nodes, ExternalUser user, Project project,
        Phase phase, Map<String, Object> values, boolean bStart) throws PhaseHandlingException {
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] instanceof Field) {
                setField((Field) nodes[i], user, project, phase, values, bStart);
            } else if (nodes[i] instanceof Loop) {
                setLoopItems((Loop) nodes[i], user, project, phase, values, bStart);
            } else if (nodes[i] instanceof Condition) {
                Condition condition = ((Condition) nodes[i]);

                if (values.containsKey(condition.getName())) {
                    condition.setValue(values.get(condition.getName()).toString());
                }

                setNodes(condition.getSubNodes().getNodes(), user, project, phase, values, bStart);
            }
        }
    }
    /**
     * This method sets the values of the Loop with user, project information and lookup values
     * based on bStart variable which is true if phase is to start, false if phase is to end.
     *
     * @param loop the Loop in template
     * @param user the user to be notified.
     * @param project project instance.
     * @param phase phase instance.
     * @param values the values map to look up
     * @param bStart true if phase is to start, false if phase is to end.
     * @throws PhaseHandlingException if the values element for the loop is invalid
     */
    @SuppressWarnings("unchecked")
    private void setLoopItems(Loop loop, ExternalUser user, Project project,
        Phase phase, Map<String, Object> values, boolean bStart) throws PhaseHandlingException {
        try {
            List<?> loopItems = (List<?>) values.get(loop.getLoopElement());
            if (loopItems == null) {
                throw new PhaseHandlingException("For loop :" + loop.getLoopElement()
                                                  + ", the value in look up maps should not be null.");
            }
            for (int t = 0; t < loopItems.size(); t++) {
                NodeList item = loop.insertLoopItem(t);
                setNodes(item.getNodes(), user, project, phase, (Map<String, Object>) loopItems.get(t), bStart);
            }
        } catch (ClassCastException cce) {
            throw new PhaseHandlingException("For loop :" + loop.getLoopElement()
                    + ", the value in look up maps should be of List<Map<String, Object>> type.");
        }
    }
    /**
     * This method sets the values of the Field with user, project information and lookup values
     * based on bStart variable which is true if phase is to start, false if phase is to end.
     *
     * <p>
     * Changes in version 1.6: a new field called STUDIO_LINK is added, which can be referenced in email template.
     * </p>
     *
     * @param field the Field in template
     * @param user the user to be notified.
     * @param project project instance.
     * @param phase phase instance.
     * @param values the values map to look up
     * @param bStart true if phase is to start, false if phase is to end.
     */
    private void setField(Field field, ExternalUser user, Project project,
        Phase phase, Map<String, Object> values, boolean bStart) {
        if ("PHASE_TIMESTAMP".equals(field.getName())) {
            field.setValue(formatDate(new Date()));
        } else if ("USER_FIRST_NAME".equals(field.getName())) {
            field.setValue(user.getFirstName());
        } else if ("USER_LAST_NAME".equals(field.getName())) {
            field.setValue(user.getLastName());
        } else if ("USER_HANDLE".equals(field.getName())) {
            field.setValue(user.getHandle());
        } else if ("PROJECT_NAME".equals(field.getName())) {
            field.setValue((String) project.getProperty(PROJECT_NAME));
        } else if ("PROJECT_VERSION".equals(field.getName())) {
            field.setValue((String) project.getProperty(PROJECT_VERSION));
        } else if ("PROJECT_CATEGORY".equals(field.getName())) {
            field.setValue((String) project.getProjectCategory().getName());
        } else if ("PHASE_OPERATION".equals(field.getName())) {
            field.setValue(bStart ? "start" : "end");
        } else if ("PHASE_TYPE".equals(field.getName())) {
            field.setValue(phase.getPhaseType().getName());
        } else if ("OR_LINK".equals(field.getName())) {
            field.setValue(projectDetailsBaseURL + project.getId());
        } else if ("STUDIO_LINK".equals(field.getName())) {
            field.setValue(studioProjectDetailsBaseURL + project.getId());
        } else if (values.containsKey(field.getName())) {
            if (values.get(field.getName()) != null) {
                field.setValue(values.get(field.getName()).toString());
            }
        }
    }



    /**
     * <p>
     * Creates the Empty EmailOptions with Not Send email.
     * </p>
     * @return EmailOptions instance
     */
    private static EmailOptions createNotSendEmailOptions() {
        EmailOptions options = new EmailOptions();
        options.setSend(Boolean.FALSE);

        return options;
    }

    /**
     * <p>
     * Gets the properties from the given namespace and creates the EmailOptions instance with these values.
     * </p>
     *
     * @param namespace the namespace
     * @param propertyPrefix the prefix of property to retrieve from
     * @return EmailOptions instance with the setting from property
     * @throws ConfigurationException if any error occurs during retrieving
     */
    private static EmailOptions createEmailOptions(String namespace, String propertyPrefix)
        throws ConfigurationException {
        EmailOptions options = new EmailOptions();
        options.setFromAddress(PhasesHelper.getPropertyValue(namespace,
                format(PROP_EMAIL_FROM_ADDRESS, propertyPrefix), true));
        options.setTemplateName(PhasesHelper.getPropertyValue(namespace,
                format(PROP_EMAIL_TEMPLATE_NAME, propertyPrefix), true));
        options.setSubject(PhasesHelper.getPropertyValue(namespace, format(PROP_EMAIL_SUBJECT, propertyPrefix), true));

        //'SendEmail' is optional, the value could be 'Yes' or 'No', or 'True' or 'False', case-insenstive
        options.setSend(parseSendEmailPropValue(PhasesHelper.getPropertyValue(namespace,
                    format(PROP_SEND_EMAIL, propertyPrefix), false)));

        return options;
    }

    /**
     * <p>
     * Parses the value of property 'SendEmail' to Boolean type from String type. The allowed string value could be
     * 'yes'/'no' or 'true'/'false' in any case.
     * </p>
     * @param value the value to parse, can be null, then false will be returned
     * @return true if it is 'yes' or 'true', false otherwise
     * @throws ConfigurationException if the value is not in allowed list
     */
    private static Boolean parseSendEmailPropValue(String value)
        throws ConfigurationException {
        if (value == null) {
            return Boolean.FALSE;
        }

        if (value.trim().equalsIgnoreCase("yes") || value.trim().equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        }

        if (value.trim().equalsIgnoreCase("no") || value.trim().equalsIgnoreCase("false")) {
            return Boolean.FALSE;
        }

        throw new ConfigurationException(
            "The value for 'SendEmail' should be 'true'/'false' or 'yes'/'no', case-insensitive");
    }

    /**
     * <p>
     * Gets the 'Schemes' property values from the given namespace. If there is no such property, empty list will
     * be returned.
     * </p>
     * @param namespace the namespace to retrieve the values from
     * @return List of EmailScheme objects. Not null, can be empty.
     * @throws ConfigurationException if the namespace does not exist
     */
    private static List<EmailScheme> getEmailSchemes(String namespace)
        throws ConfigurationException {
        
        try {
            com.topcoder.util.config.Property schemesProperty =
                ConfigManager.getInstance().getPropertyObject(namespace, "Schemes");
            if (schemesProperty == null) {
                return new ArrayList<EmailScheme>();
            }

            java.util.Enumeration<?> schemeNames = schemesProperty.propertyNames();
            List<EmailScheme> schemes = new ArrayList<EmailScheme>();

            while (schemeNames.hasMoreElements()) {
                String schemeName = (String) schemeNames.nextElement();
                if (schemeName != null) {
                    EmailScheme emailScheme = new EmailScheme();
                    
                    String[] rolesArray = schemesProperty.getProperty(schemeName).getProperty("Roles").getValues();
                    if (rolesArray != null && rolesArray.length > 0) {
                        emailScheme.setRoles(java.util.Arrays.asList(rolesArray));
                    }
                    
                    String[] projectTypesArray = schemesProperty.getProperty(schemeName).getProperty("ProjectTypes").getValues();
                    if (projectTypesArray != null && projectTypesArray.length > 0) {
                        emailScheme.setProjectTypeIDs(java.util.Arrays.asList(projectTypesArray));
                    }
                    
                    String priority = schemesProperty.getProperty(schemeName).getProperty("Priority").getValue();
                    if (priority != null) {
                        try {
                            emailScheme.setPriority(Integer.parseInt(priority));
                        } catch (NumberFormatException nfe) {
                            throw new ConfigurationException("Can't parse priority value : " + priority, nfe);
                        }
                    }
                    
                    //look up 'Scheme/xxPhaseEmail/xx'
                    if (PhasesHelper.doesPropertyExist(namespace, schemeName)) {
                        //if the configuration does not exist, create not send email options
                        boolean exists = PhasesHelper.doesPropertyObjectExist(namespace,
                                schemeName + "." + format(PROP_PHASE_EMAIL, START));
                        EmailOptions startEmailOption =  exists ? createEmailOptions(namespace, schemeName + "." + START)
                                                                : createNotSendEmailOptions();

                        //if the configuration does not exist, create not send email options
                        exists = PhasesHelper.doesPropertyObjectExist(namespace, schemeName + "." + format(PROP_PHASE_EMAIL, END));
                        EmailOptions endEmailOption = exists ? createEmailOptions(namespace, schemeName + "." + END)
                                                              : createNotSendEmailOptions();

                        emailScheme.setStartEmailOptions(startEmailOption);
                        emailScheme.setEndEmailOptions(endEmailOption);
                        schemes.add(emailScheme);                                                              
                    }
                }
            }

            return schemes;
        } catch (UnknownNamespaceException e) {
            throw new ConfigurationException("The namespace '" + namespace + "' does not exist.", e);
        }
    }

    /**
     * Returns the date formatted as "Fri, Jul 28, 2006 01:34 PM EST" for example.
     *
     * @param dt date to be formatted.
     *
     * @return formatted date string.
     */
    private static String formatDate(Date dt) {
        SimpleDateFormat formatter = new SimpleDateFormat(EMAIL_TIMESTAMP_FORMAT);

        return formatter.format(dt);
    }

    /**
     * Formats the String using the pattern and the value.
     *
     * @param pattern the pattern to format
     * @param value the value
     * @return formatted result
     */
    private static String format(String pattern, String value) {
        return MessageFormat.format(pattern, value);
    }

    /**
     * Checks if the id exists in the id array.
     *
     * @param ids the id array
     * @param id the id to check
     *
     * @return true if exists
     */
    private static boolean contains(long[] ids, long id) {
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] == id) {
                return true;
            }
        }

        return false;
    }
}
