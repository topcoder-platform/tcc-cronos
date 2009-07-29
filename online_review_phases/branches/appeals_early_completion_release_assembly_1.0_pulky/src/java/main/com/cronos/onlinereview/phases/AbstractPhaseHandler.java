/*
 * Copyright (C) 2004 - 2009 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cronos.onlinereview.external.ExternalUser;
import com.cronos.onlinereview.external.RetrievalException;
import com.cronos.onlinereview.phases.lookup.NotificationTypeLookupUtility;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.message.email.AddressException;
import com.topcoder.message.email.EmailEngine;
import com.topcoder.message.email.SendingException;
import com.topcoder.message.email.TCSEmailMessage;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.InvalidConfigException;
import com.topcoder.util.file.Template;
import com.topcoder.util.file.TemplateDataFormatException;
import com.topcoder.util.file.TemplateFormatException;
import com.topcoder.util.file.fieldconfig.Field;
import com.topcoder.util.file.fieldconfig.Node;
import com.topcoder.util.file.fieldconfig.TemplateFields;
import com.topcoder.util.file.templatesource.TemplateSourceException;


/**
 * <p>This abstract class is used as a base class for all phase handlers. This class contains logic in the
 * constructor to load configuration settings for a phase handler. Settings includes database connection, email
 * template and email related information.</p>
 * <p>Sample configuration file is given below:<pre>
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
 *        &lt;Property name="StartPhaseEmail"&gt;
 *            &lt;Property name="EmailTemplateSource"&gt;
 *                &lt;Value&gt;file&lt;/Value&gt;
 *            &lt;/Property&gt;
 *            &lt;Property name="EmailTemplateName"&gt;
 *                &lt;Value&gt;test_files/valid_email_template.txt&lt;/Value&gt;
 *            &lt;/Property&gt;
 *            &lt;Property name="EmailSubject"&gt;
 *                &lt;Value&gt;Phase Start&lt;/Value&gt;
 *            &lt;/Property&gt;
 *            &lt;Property name="EmailFromAddress"&gt;
 *                &lt;Value&gt;admin@topcoder.com&lt;/Value&gt;
 *            &lt;/Property&gt;
 *        &lt;/Property&gt;
 *        &lt;Property name="EndPhaseEmail"&gt;
 *            &lt;Property name="EmailTemplateSource"&gt;
 *                &lt;Value&gt;file&lt;/Value&gt;
 *            &lt;/Property&gt;
 *            &lt;Property name="EmailTemplateName"&gt;
 *                &lt;Value&gt;test_files/valid_email_template.txt&lt;/Value&gt;
 *            &lt;/Property&gt;
 *            &lt;Property name="EmailSubject"&gt;
 *                &lt;Value&gt;Phase End&lt;/Value&gt;
 *            &lt;/Property&gt;
 *            &lt;Property name="EmailFromAddress"&gt;
 *                &lt;Value&gt;admin@topcoder.com&lt;/Value&gt;
 *            &lt;/Property&gt;
 *        &lt;/Property&gt;
 *    &lt;/Config&gt;
 * </pre></p>
 * <p>Sample email template is given below:<pre>
 * %PHASE_TIMESTAMP{Phase timestamp}%
 * Hello %USER_FIRST_NAME{User first name}% %USER_LAST_NAME{User last name}%,
 * Handle\: %USER_HANDLE{User handle}%
 * This is the notification on project\: %PROJECT_NAME{Project name}%
 * Version\: %PROJECT_VERSION{Project version}%
 * This is the %PHASE_OPERATION{The phase operation - start/end}% of the %PHASE_TYPE{Phase type}% phase.
 * </pre></p>
 * <p>Thread safety: This class is thread safe because it is immutable.</p>
 *
 * <p>
 *   Version 1.1 (Appeals Early Completion Release Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Changed timeline notification emails subject.</li>
 *     <li>Added new fields to timeline notification emails.</li>
 *   </ol>
 * </p>
 *
 * @author tuenm, bose_java, TCSDEVELOPER
 * @version 1.1
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

    /** Property name constant for "StartPhaseEmail". */
    private static final String PROP_START_PHASE_EMAIL = "StartPhaseEmail";

    /** Property name constant for "StartPhaseEmail.EmailTemplateSource". */
    private static final String PROP_START_EMAIL_TEMPLATE_SOURCE = "StartPhaseEmail.EmailTemplateSource";

    /** Property name constant for "StartPhaseEmail.EmailTemplateName". */
    private static final String PROP_START_EMAIL_TEMPLATE_NAME = "StartPhaseEmail.EmailTemplateName";

    /** Property name constant for "StartPhaseEmail.EmailSubject". */
    private static final String PROP_START_EMAIL_SUBJECT = "StartPhaseEmail.EmailSubject";

    /** Property name constant for "StartPhaseEmail.EmailFromAddress". */
    private static final String PROP_START_EMAIL_FROM_ADDRESS = "StartPhaseEmail.EmailFromAddress";

    /** Property name constant for "EndPhaseEmail". */
    private static final String PROP_END_PHASE_EMAIL = "EndPhaseEmail";

    /** Property name constant for "EndPhaseEmail.EmailTemplateSource". */
    private static final String PROP_END_EMAIL_TEMPLATE_SOURCE = "EndPhaseEmail.EmailTemplateSource";

    /** Property name constant for "EndPhaseEmail.EmailTemplateName". */
    private static final String PROP_END_EMAIL_TEMPLATE_NAME = "EndPhaseEmail.EmailTemplateName";

    /** Property name constant for "EndPhaseEmail.EmailSubject". */
    private static final String PROP_END_EMAIL_SUBJECT = "EndPhaseEmail.EmailSubject";

    /** Property name constant for "EndPhaseEmail.EmailFromAddress". */
    private static final String PROP_END_EMAIL_FROM_ADDRESS = "EndPhaseEmail.EmailFromAddress";

    /** format for the email timestamp. Will format as "Fri, Jul 28, 2006 01:34 PM EST". */
    private static final String EMAIL_TIMESTAMP_FORMAT = "EEE, MMM d, yyyy hh:mm a z";

    /**
     * This constant stores Online Review's project details page url
     *
     * @since 1.1
     */
    private static final String PROJECT_DETAILS_URL = 
        "http://software.topcoder.com/review/actions/ViewProjectDetails.do?method=viewProjectDetails&pid=";

    /**
     * The factory instance used to create connection to the database. It is initialized in the constructor
     * using DBConnectionFactory component and never changed after that. It will be used in various persistence
     * methods of this project. This field is never null.
     */
    private final DBConnectionFactory factory;

    /**
     * <p>This field is used to retrieve manager instances to use in phase handlers. It is initialized in the
     * constructor and never change after that. It is never null.</p>
     */
    private final ManagerHelper managerHelper;

    /**
     * Reperesents the connection name used to create connection to the database using DBConnectionFactory.
     * This variable can be null. When it is null, default connection is be created. This variable can be initialized
     * in the constructor and never change after that.
     */
    private final String connectionName;

    /**
     * This flag indicates whether to send start phase email. is initialized in the constructor and never
     * change after that. Default value is false.
     */
    private final boolean sendStartPhaseEmail;

    /**
     * Represents the template source to load email template for sending start phase email. Template sources
     * can be file or database, etc. This field is initialized in the constructor and never change after that. It can
     * be null but not empty. It is null when there is no configuration seting for sending email.
     */
    private final String startEmailTemplateSource;

    /**
     * Represents the template name to load email template for sending start phase email. Template name can be
     * a file name for example. This field is initialized in the constructor and never change after that. It can be
     * null but not empty. It is null when there is no configuration seting for sending email.
     */
    private final String startEmailTemplateName;

    /**
     * Represents the email subject for sending start phase email. This field is initialized in the constructor
     * and never change after that. It can be null but not empty. It is null when there is no configuration seting for
     * sending email.
     */
    private final String startEmailSubject;

    /**
     * Represents the email address for sending start phase email. This field is initialized in the constructor
     * and never change after that. It can be null but not empty. It is null when there is no configuration seting for
     * sending email.
     */
    private final String startEmailFromAddress;

    /**
     * This flag indicates whether to send end phase email. is initialized in the constructor and never change
     * after that. Default value is false.
     */
    private final boolean sendEndPhaseEmail;

    /**
     * Represents the template source to load email template for sending end phase email. Template sources can
     * be file or database, etc. This field is initialized in the constructor and never change after that. It can be
     * null but not empty. It is null when there is no configuration seting for sending email.
     */
    private final String endEmailTemplateSource;

    /**
     * Represents the template name to load email template for sending end phase email. Template name can be a
     * file name for example. This field is initialized in the constructor and never change after that.  It can be
     * null but not empty. It is null when there is no configuration seting for sending email.
     */
    private final String endEmailTemplateName;

    /**
     * Represents the email subject for sending end phase email. This field is initialized in the constructor
     * and never change after that. It can be null but not empty. It is null when there is no configuration seting for
     * sending email.
     */
    private final String endEmailSubject;

    /**
     * Represents the email address for sending end phase email. This field is initialized in the constructor
     * and never change after that. It can be null but not empty. It is null when there is no configuration seting for
     * sending email.
     */
    private final String endEmailFromAddress;

    /**
     * <p>Creates a new instance of AbstractPhaseHandler using the given namespace for loading configuration
     * settings.</p>
     * <p>It initializes the DB connection factory, connection name, Manager Helper, start and end phase email variables
     * from configuration properties</p>
     *
     * @param namespace the namespace to load configuration settings from.
     * @throws ConfigurationException if errors occurred while loading configuration settings or required properties
     * missing.
     * @throws IllegalArgumentException if the input is null or empty string.
     */
    protected AbstractPhaseHandler(String namespace) throws ConfigurationException {
        PhasesHelper.checkString(namespace, "namespace");

        //initialize DBConnectionFactory from given namespace, throw exception if property is missing.
        this.factory = PhasesHelper.createDBConnectionFactory(namespace, PROP_CONNECTION_FACTORY_NS);

        //initialize ManagerHelper from given namespace if provided.
        String managerHelperNamespace = PhasesHelper.getPropertyValue(namespace, PROP_MANAGER_HELPER_NAMESPACE, false);

        if (PhasesHelper.isStringNullOrEmpty(managerHelperNamespace)) {
            this.managerHelper = new ManagerHelper();
        } else {
            this.managerHelper = new ManagerHelper(managerHelperNamespace);
        }

        //initialize connectionName with property value if provided.
        String connName = PhasesHelper.getPropertyValue(namespace, PROP_CONNECTION_NAME, false);

        if (!PhasesHelper.isStringNullOrEmpty(connName)) {
            this.connectionName = connName;
        } else {
            this.connectionName = null;
        }

        //initialize start phase email variables with property values if provided.
        this.sendStartPhaseEmail = PhasesHelper.doesPropertyExist(namespace, PROP_START_PHASE_EMAIL);

        if (this.sendStartPhaseEmail) {
            this.startEmailTemplateSource = PhasesHelper.getPropertyValue(namespace, PROP_START_EMAIL_TEMPLATE_SOURCE,
                    true);
            this.startEmailTemplateName = PhasesHelper.getPropertyValue(namespace, PROP_START_EMAIL_TEMPLATE_NAME,
                    true);
            this.startEmailSubject = PhasesHelper.getPropertyValue(namespace, PROP_START_EMAIL_SUBJECT, true);
            this.startEmailFromAddress = PhasesHelper.getPropertyValue(namespace, PROP_START_EMAIL_FROM_ADDRESS, true);
        } else {
            this.startEmailTemplateSource = null;
            this.startEmailTemplateName = null;
            this.startEmailSubject = null;
            this.startEmailFromAddress = null;
        }

        //initialize end phase email variables with property values if provided.
        this.sendEndPhaseEmail = PhasesHelper.doesPropertyExist(namespace, PROP_END_PHASE_EMAIL);

        if (this.sendEndPhaseEmail) {
            this.endEmailTemplateSource = PhasesHelper.getPropertyValue(namespace, PROP_END_EMAIL_TEMPLATE_SOURCE,
                    true);
            this.endEmailTemplateName = PhasesHelper.getPropertyValue(namespace, PROP_END_EMAIL_TEMPLATE_NAME, true);
            this.endEmailSubject = PhasesHelper.getPropertyValue(namespace, PROP_END_EMAIL_SUBJECT, true);
            this.endEmailFromAddress = PhasesHelper.getPropertyValue(namespace, PROP_END_EMAIL_FROM_ADDRESS, true);
        } else {
            this.endEmailTemplateSource = null;
            this.endEmailTemplateName = null;
            this.endEmailSubject = null;
            this.endEmailFromAddress = null;
        }
    }

    /**
     * <p>Sends email at the start/end of a phase. If the phase is in "Scheduled" state, start phase email will
     * be sent. If the phase is in "Open" state, end phase email will be sent. If the phase is in any other state,
     * this method does nothing. Besides, this method will not send any email if it is not configured to do so.</p>
     *  <p>This method first retrieves all the ExternalUsers to whom the notification is to be sent as well as
     * the project information. It then instantiates document generator to create the email body content and sends out
     * an email to each user using the Email Engine component.</p>
     *
     * @param phase phase instance.
     *
     * @throws IllegalArgumentException if the input is null or empty string.
     * @throws PhaseHandlingException if there was a problem when sending email.
     */
    protected void sendEmail(Phase phase) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");

        // Do not send any e-mails for phases whose duration is zero
        if (phase.getLength() <= 0L) {
            return;
        }

        final Date scheduledStartDate = phase.getScheduledStartDate();
        final Date scheduledEndDate = phase.getScheduledEndDate();

        // Check phase's scheduled start & end dates to be sure
        if (scheduledStartDate != null && scheduledEndDate != null && !scheduledStartDate.before(scheduledEndDate)) {
            return;
        }

        // Determine whether phase is starting or ending...
        PhaseStatus status = phase.getPhaseStatus();

        if (PhasesHelper.isPhaseToStart(status) && sendStartPhaseEmail) {
            sendEmail(phase, true);
        } else if (PhasesHelper.isPhaseToEnd(status) && sendEndPhaseEmail) {
            sendEmail(phase, false);
        }
    }

    /**
     * <p>This method is used by the subclass to create the connection to access database. The connection needs
     * to be closed after use.</p>
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
     * <p>This method is used by the subclass to get the ManagerHelper instance.</p>
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
     * @param phase phase instance.
     * @param bStart true if phase is to start, false if phase is to end.
     *
     * @throws PhaseHandlingException if there was an error retrieving information or sending email.
     */
    private void sendEmail(Phase phase, boolean bStart)
        throws PhaseHandlingException {
        Connection conn = createConnection();
        ExternalUser[] users = null;
        Project project = null;

        try {
            long projectId = phase.getProject().getId();

            //Lookup notification type id for "Timeline Notification"
            long notificationTypeId = NotificationTypeLookupUtility.lookUpId(conn,
                NOTIFICATION_TYPE_TIMELINE_NOTIFICATION);

            //retrieve users to be notified
            long[] externalIds = managerHelper.getResourceManager().getNotifications(projectId, notificationTypeId);
            users = managerHelper.getUserRetrieval().retrieveUsers(externalIds);

            //retrieve project information
            project = managerHelper.getProjectManager().getProject(projectId);
        } catch (SQLException ex) {
            throw new PhaseHandlingException("Could not lookup project info type id for " +
                NOTIFICATION_TYPE_TIMELINE_NOTIFICATION, ex);
        } catch (ResourcePersistenceException ex) {
            throw new PhaseHandlingException("There was a problem with resource retrieval", ex);
        } catch (RetrievalException ex) {
            throw new PhaseHandlingException("There was a problem with user retrieval", ex);
        } catch (PersistenceException ex) {
            throw new PhaseHandlingException("There was a problem with project retrieval", ex);
        } finally {
            PhasesHelper.closeConnection(conn);
        }

        try {
            //instantiate document generator instance
            DocumentGenerator docGenerator = DocumentGenerator.getInstance();
            Template template = null;

            if (bStart) {
                template = docGenerator.getTemplate(startEmailTemplateSource, startEmailTemplateName);
            } else {
                template = docGenerator.getTemplate(endEmailTemplateSource, endEmailTemplateName);
            }

            //prepare email content and send email to each user...
            for (int iUser = 0; iUser < users.length; iUser++) {
                ExternalUser user = users[iUser];

                //for each external user, set field values
                TemplateFields root = setTemplateFieldValues(docGenerator.getFields(template), user, project, phase,
                        bStart);
                String emailContent = docGenerator.applyTemplate(root);

                TCSEmailMessage message = new TCSEmailMessage();
                message.setSubject((bStart ? startEmailSubject : endEmailSubject) + ": " +
                    (String) project.getProperty(PROJECT_NAME));
                message.setBody(emailContent);
                message.setFromAddress(bStart ? startEmailFromAddress : endEmailFromAddress);
                message.setToAddress(user.getEmail(), TCSEmailMessage.TO);
                EmailEngine.send(message);
            }
        } catch (ConfigManagerException e) {
            throw new PhaseHandlingException("There was a configuration error", e);
        } catch (InvalidConfigException e) {
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
        } catch (Exception e) {
            throw new PhaseHandlingException("Problem with sending email", e);
        }
    }

    /**
     * This method sets the values of the template fields with user and project information based on bStart
     * variable which is true if phase is to start, false if phase is to end.
     *
     * @param root template fields.
     * @param user the user to be notified.
     * @param project project instance.
     * @param phase phase instance.
     * @param bStart true if phase is to start, false if phase is to end.
     *
     * @return template fields with data set.
     */
    private TemplateFields setTemplateFieldValues(TemplateFields root, ExternalUser user, Project project,
        Phase phase, boolean bStart) {
        Node[] nodes = root.getNodes();

        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] instanceof Field) {
                Field field = (Field) nodes[i];

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
                } else if ("PHASE_OPERATION".equals(field.getName())) {
                    if (bStart) {
                        field.setValue("start");
                    } else {
                        field.setValue("end");
                    }
                } else if ("PHASE_TYPE".equals(field.getName())) {
                    field.setValue(phase.getPhaseType().getName());
                } else if ("OR_LINK".equals(field.getName())) {
                    field.setValue("<![CDATA[" + PROJECT_DETAILS_URL + project.getId() + "]]>");
                }
            }
        }

        return root;
    }

    /**
     * Returns the date formatted as "Fri, Jul 28, 2006 01:34 PM EST" for example.
     *
     * @param dt date to be formatted.
     *
     * @return formatted date string.
     */
    private String formatDate(Date dt) {
        SimpleDateFormat formatter = new SimpleDateFormat(EMAIL_TIMESTAMP_FORMAT);

        return formatter.format(dt);
    }
}
