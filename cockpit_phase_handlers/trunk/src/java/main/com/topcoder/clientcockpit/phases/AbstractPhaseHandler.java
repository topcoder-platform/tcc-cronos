/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.logging.Level;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;

import com.topcoder.clientcockpit.phases.messagegenerators.DefaultEmailMessageGenerator;
import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.message.email.EmailEngine;
import com.topcoder.message.email.TCSEmailMessage;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.submission.Submission;
import com.topcoder.service.studio.submission.SubmissionManager;
import com.topcoder.service.studio.submission.Prize;
import com.topcoder.service.studio.submission.SubmissionManagementException;
import com.topcoder.service.studio.submission.SubmissionPayment;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.errorhandling.BaseException;
import com.topcoder.util.errorhandling.ExceptionUtils;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.Template;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.log4j.Log4jLogFactory;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.UnknownConnectionException;
import com.topcoder.db.connectionfactory.producers.JNDIConnectionProducer;

/**
 * <p>
 * This class is an abstract handler that will be extended by all the other concrete handlers.
 * </p>
 *
 * <p>
 * It uses Configuration Manager component for configuration purposes.
 * </p>
 *
 * <p>
 * It uses Document Generator component to create the body of the message and Email Engine to send the email.
 * </p>
 *
 * <p>
 * It has methods that will send an email to resources associated with time-line notification for
 * the project, in case the phase ended or started. It has two other methods that will be used to send an email
 * to the client in case there are less than eight(or one) hours before the deadline for the winner announcement.
 * </p>
 *
 * <p>
 *     <strong>Configuration Parameters:</strong>
 *     <table border="1">
 *         <tr><th><b>Parameter</b></th><th><b>Values</b></th><th><b>Description</b></th></tr>
 *         <tr><td>bean_name</td><td>non empty string</td>
 *         <td>The name used with JNDI to retrieve the bean. It is <b>required</b> if the constructor without
 *         ContestManager parameter is used.</td></tr>
 *         <tr><td>use_cache</td><td>"true" or "false"(case insensitive).</td>
 *         <td>Indicates weather contest statuses will be cached. <b>Optional. If not present default to true.</b>
 *         </td></tr>
 *         <tr><td>send_start_phase_email</td><td>"true" or "false"(case insensitive).</td>
 *         <td>Indicates if an email should be sent when a phase starts. <b>Optional. If not present default
 *         to true.</b></td></tr>
 *         <tr><td>start_email_template_source</td><td>non empty string</td>
 *         <td>Identifies the source from where the template will be retrieved. <b>Optional</b>.</td></tr>
 *         <tr><td>start_email_template_name</td><td>non empty string</td>
 *         <td>The name of the template. <b>Required If send_start_phase_email results true</b>.</td></tr>
 *         <tr><td>start_email_subject</td><td>non empty string</td>
 *         <td>The subject of the email. <b>Required If send_start_phase_email results true</b>.</td></tr>
 *         <tr><td>start_email_from_address</td><td>non empty string</td>
 *         <td>The address from which the email is sent. <b>Required If send_start_phase_email results true</b>.
 *         </td></tr>
 *         <tr><td>send_end_phase_email</td><td>"true" or "false"(case insensitive).</td>
 *         <td>Indicates if an email should be sent when a phase ends. <b>Optional. If not present default
 *         to true.</b></td></tr>
 *         <tr><td>end_email_template_source</td><td>non empty string</td>
 *         <td>Identifies the source from where the template will be retrieved. <b>Optional</b>.</td></tr>
 *         <tr><td>end_email_template_name</td><td>non empty string</td>
 *         <td>The name of the template. <b>Required If send_end_phase_email results true</b>.</td></tr>
 *         <tr><td>end_email_subject</td><td>non empty string</td>
 *         <td>The subject of the email. <b>Required If send_end_phase_email results true</b>.</td></tr>
 *         <tr><td>end_email_from_address</td><td>non empty string</td>
 *         <td>The address from which the email is sent. <b>Required If send_end_phase_email results true</b>.</td></tr>
 *         <tr><td>send_eight_hours_phase_email</td><td>"true" or "false"(case insensitive).</td>
 *         <td>Indicates if an email should be sent when there less than eight hours before the winner
 *         announcement deadline. <b>Optional. If not present default to true.</b></td></tr>
 *         <tr><td>eight_hours_email_template_source</td><td>non empty string</td>
 *         <td>Identifies the source from where the template will be retrieved. <b>Optional</b>.</td></tr>
 *         <tr><td>eight_hours_email_template_name</td><td>non empty string</td>
 *         <td>The name of the template. <b>Required If send_eight_hours_phase_email results true</b>.</td></tr>
 *         <tr><td>eight_hours_email_subject</td><td>non empty string</td>
 *         <td>The subject of the email. <b>Required If send_eight_hours_phase_email results true</b>.</td></tr>
 *         <tr><td>eight_hours_email_from_address</td><td>non empty string</td>
 *         <td>The address from which the email is sent. <b>Required If send_eight_hours_phase_email results true
 *         </b>.</td></tr>
 *         <tr><td>send_one_hour_phase_email</td><td>"true" or "false"(case insensitive).</td>
 *         <td>Indicates if an email should be sent when there less than one hour before the winner announcement
 *         deadline. <b>Optional. If not present default to true.</b></td></tr>
 *         <tr><td>one_hour_email_template_source</td><td>non empty string</td>
 *         <td>Identifies the source from where the template will be retrieved. <b>Optional</b>.</td></tr>
 *         <tr><td>one_hour_email_template_name</td><td>non empty string</td>
 *         <td>The name of the template. <b>Required If send_one_hour_phase_email results true</b>.</td></tr>
 *         <tr><td>one_hour_email_subject</td><td>non empty string</td>
 *         <td>The subject of the email. <b>Required If send_one_hour_phase_email results true</b>.</td></tr>
 *         <tr><td>one_hour_email_from_address</td><td>non empty string</td>
 *         <td>The address from which the email is sent. <b>Required If send_one_hour_phase_email results true
 *         </b>.</td></tr>
 *         <tr><td>message_generator_key</td><td>non empty string</td>
 *         <td>The key used with Object Factory to create an EmailMessageGenerator object. <b>Optional</b>.</td></tr>
 *         <tr><td>specification_factory_namespace</td><td>non empty string</td>
 *         <td>The namespace used to create a ConfigManagerSpecificationFactory instance. <b>Optional</b>.</td></tr>
 *     </table>
 * </p>
 *
 * <p>
 *     <strong>Sample Configuration:</strong>
 *     <pre>
 *     &lt;Config name="com.topcoder.clientcockpit.phases"&gt;
 *
 *       &lt;Property name="bean_name"&gt;
 *           &lt;Value&gt;beanName&lt;/Value&gt;
 *        &lt;/Property&gt;
 *
 *        &lt;Property name="send_start_phase_email"&gt;
 *            &lt;Value&gt;true&lt;/Value&gt;
 *        &lt;/Property&gt;
 *        &lt;Property name="start_email_template_source"&gt;
 *            &lt;Value&gt;startTemplateSource&lt;/Value&gt;
 *        &lt;/Property&gt;
 *        &lt;Property name="start_email_template_name"&gt;
 *            &lt;Value&gt;startTemplateName&lt;/Value&gt;
 *        &lt;/Property&gt;
 *        &lt;Property name="start_email_subject"&gt;
 *            &lt;Value&gt;startEmailSubject&lt;/Value&gt;
 *        &lt;/Property&gt;
 *        &lt;Property name="start_email_from_address"&gt;
 *            &lt;Value&gt;address@yahoo.com&lt;/Value&gt;
 *        &lt;/Property&gt;
 *
 *        &lt;Property name="send_end_phase_email"&gt;
 *            &lt;Value&gt;true&lt;/Value&gt;
 *        &lt;/Property&gt;
 *        &lt;Property name="end_email_template_source"&gt;
 *            &lt;Value&gt;endTemplateSource&lt;/Value&gt;
 *        &lt;/Property&gt;
 *        &lt;Property name="end_email_template_name"&gt;
 *            &lt;Value&gt;endTemplateName&lt;/Value&gt;
 *        &lt;/Property&gt;
 *        &lt;Property name="end_email_subject"&gt;
 *            &lt;Value&gt;endEmailSubject&lt;/Value&gt;
 *        &lt;/Property&gt;
 *        &lt;Property name="end_email_from_address"&gt;
 *            &lt;Value&gt;address@yahoo.com&lt;/Value&gt;
 *        &lt;/Property&gt;
 *
 *        &lt;Property name="send_eight_hours_phase_email"&gt;
 *            &lt;Value&gt;true&lt;/Value&gt;
 *        &lt;/Property&gt;
 *        &lt;Property name="eight_hours_email_template_source"&gt;
 *            &lt;Value&gt;eightHoursTemplateSource&lt;/Value&gt;
 *        &lt;/Property&gt;
 *        &lt;Property name="eight_hours_email_template_name"&gt;
 *            &lt;Value&gt;eightHoursTemplateName&lt;/Value&gt;
 *        &lt;/Property&gt;
 *        &lt;Property name="eight_hours_email_subject"&gt;
 *            &lt;Value&gt;eightHoursEmailSubject&lt;/Value&gt;
 *        &lt;/Property&gt;
 *        &lt;Property name="eight_hours_email_from_address"&gt;
 *            &lt;Value&gt;address@yahoo.com&lt;/Value&gt;
 *        &lt;/Property&gt;
 *
 *        &lt;Property name="send_one_hour_phase_email"&gt;
 *            &lt;Value&gt;true&lt;/Value&gt;
 *        &lt;/Property&gt;
 *        &lt;Property name="one_hour_email_template_source"&gt;
 *            &lt;Value&gt;oneHourTemplateSource&lt;/Value&gt;
 *        &lt;/Property&gt;
 *        &lt;Property name="one_hour_email_template_name"&gt;
 *            &lt;Value&gt;oneHourTemplateName&lt;/Value&gt;
 *        &lt;/Property&gt;
 *        &lt;Property name="one_hour_email_subject"&gt;
 *            &lt;Value&gt;oneHourEmailSubject&lt;/Value&gt;
 *        &lt;/Property&gt;
 *        &lt;Property name="one_hour_email_from_address"&gt;
 *            &lt;Value&gt;address@yahoo.com&lt;/Value&gt;
 *        &lt;/Property&gt;
 *
 *        &lt;Property name="message_generator_key"&gt;
 *            &lt;Value&gt;messageGeneratorKey&lt;/Value&gt;
 *        &lt;/Property&gt;
 *
 *        &lt;Property name="specification_factory_namespace"&gt;
 *            &lt;Value&gt;specificationFactoryNamespace&lt;/Value&gt;
 *        &lt;/Property&gt;
 *
 *    &lt;/Config&gt;
 *     </pre>
 * </p>
 *
 * <p>
 *     <strong>Thread Safety:</strong>
 *     The fields are initialized in the constructor and never changed so there are no thread safety issues.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public abstract class AbstractPhaseHandler implements PhaseHandler, Serializable {

    /**
     * <p>A <code>Logger</code> to be used for logging the errors encountered while handler performs it's action.</p>
     */
    private static final Logger alertLog = Logger.getLogger("ALERT." + AbstractPhaseHandler.class.getName());

    /**
     * <p>
     * The default namespace from where the configured properties will be loaded if another namespace is not specified.
     * Its value is: <b>com.topcoder.clientcockpit.phases</b>.
     * </p>
     */
    private static final String DEFAULT_NAMESPACE = AbstractPhaseHandler.class.getPackage().getName();

    /**
     * <p>
     * The attribute name of contest within project attributes.
     * </p>
     */
    private static final String PROJECT_ATTR_CONTEST = "contest";

    /**
     * <p>
     * The attribute name of resource email within project attributes.
     * </p>
     */
    private static final String PROJECT_ATTR_RESOURCE_EMAILS = "ResourceEmails";

    /**
     * <p>
     * The attribute name of minimum submissions within project attributes.
     * </p>
     */
    private static final String PROJECT_ATTR_MINIMUM_SUBMISSIONS = "MinimumSubmissions";

    /**
     * <p>
     * Represents the property: "bean_name".
     * </p>
     */
    private static final String BEAN_NAME = "bean_name";

    /**
     * <p>
     * Represents the property: "submission_manager_bean_name".
     * </p>
     */
    private static final String SUBMISSION_MANAGER_BEAN_NAME = "submission_manager_bean_name";

    /**
     * <p>
     * Represents the property: "use_cache".
     * </p>
     */
    private static final String USE_CACHE = "use_cache";

    /**
     * <p>
     * Represents the property: "message_generator_key".
     * </p>
     */
    private static final String MESSAGE_GENERATOR_KEY = "message_generator_key";

    /**
     * <p>
     * Represents the property: "specification_factory_namespace".
     * </p>
     */
    private static final String SPECIFICATION_FACTORY_NAMESPACE = "specification_factory_namespace";

    /**
     * <p>
     * Represents the property: "send_start_phase_email".
     * </p>
     */
    private static final String SEND_START_PHASE_EMAIL = "send_start_phase_email";

    /**
     * <p>
     * Represents the property: "start_email_template_source".
     * </p>
     */
    private static final String START_EMAIL_TEMPLATE_SOURCE = "start_email_template_source";

    /**
     * <p>
     * Represents the property: "start_email_template_name".
     * </p>
     */
    private static final String START_EMAIL_TEMPLATE_NAME = "start_email_template_name";

    /**
     * <p>
     * Represents the property: "start_email_subject".
     * </p>
     */
    private static final String START_EMAIL_SUBJECT = "start_email_subject";

    /**
     * <p>
     * Represents the property: "start_email_from_address".
     * </p>
     */
    private static final String START_EMAIL_FROM_ADDRESS = "start_email_from_address";

    /**
     * <p>
     * Represents the property: "send_end_phase_email".
     * </p>
     */
    private static final String SEND_END_PHASE_EMAIL = "send_end_phase_email";

    /**
     * <p>
     * Represents the property: "end_email_template_source".
     * </p>
     */
    private static final String END_EMAIL_TEMPLATE_SOURCE = "end_email_template_source";

    /**
     * <p>
     * Represents the property: "end_email_template_name".
     * </p>
     */
    private static final String END_EMAIL_TEMPLATE_NAME = "end_email_template_name";

    /**
     * <p>
     * Represents the property: "end_email_subject".
     * </p>
     */
    private static final String END_EMAIL_SUBJECT = "end_email_subject";

    /**
     * <p>
     * Represents the property: "end_email_from_address".
     * </p>
     */
    private static final String END_EMAIL_FROM_ADDRESS = "end_email_from_address";

    /**
     * <p>
     * Represents the property: "send_eight_hours_phase_email".
     * </p>
     */
    private static final String SEND_EIGHT_HOURS_PHASE_EMAIL = "send_eight_hours_phase_email";

    /**
     * <p>
     * Represents the property: "eight_hours_email_template_source".
     * </p>
     */
    private static final String EIGHT_HOURS_EMAIL_TEMPLATE_SOURCE = "eight_hours_email_template_source";

    /**
     * <p>
     * Represents the property: "eight_hours_email_template_name".
     * </p>
     */
    private static final String EIGHT_HOURS_EMAIL_TEMPLATE_NAME = "eight_hours_email_template_name";

    /**
     * <p>
     * Represents the property: "eight_hours_email_subject".
     * </p>
     */
    private static final String EIGHT_HOURS_EMAIL_SUBJECT = "eight_hours_email_subject";

    /**
     * <p>
     * Represents the property: "eight_hours_email_from_address".
     * </p>
     */
    private static final String EIGHT_HOURS_EMAIL_FROM_ADDRESS = "eight_hours_email_from_address";

    /**
     * <p>
     * Represents the property: "send_one_hour_phase_email".
     * </p>
     */
    private static final String SEND_ONE_HOUR_PHASE_EMAIL = "send_one_hour_phase_email";

    /**
     * <p>
     * Represents the property: "one_hour_email_template_source".
     * </p>
     */
    private static final String ONE_HOUR_EMAIL_TEMPLATE_SOURCE = "one_hour_email_template_source";

    /**
     * <p>
     * Represents the property: "one_hour_email_template_name".
     * </p>
     */
    private static final String ONE_HOUR_EMAIL_TEMPLATE_NAME = "one_hour_email_template_name";

    /**
     * <p>
     * Represents the property: "one_hour_email_subject".
     * </p>
     */
    private static final String ONE_HOUR_EMAIL_SUBJECT = "one_hour_email_subject";

    /**
     * <p>
     * Represents the property: "one_hour_email_from_address".
     * </p>
     */
    private static final String ONE_HOUR_EMAIL_FROM_ADDRESS = "one_hour_email_from_address";

    /**
     * <p>
     * Represents the property: "use_cache".
     * </p>
     */
    private static final String FORUM_BEAN_PROVIDER_URL = "forum_bean_provider_url";

    /** Property name constant for studio jndi name. */
    private static final String STUDIO_JNDI_NAME = "studio_jndi_name";

    /**
     * <p>
     * The bean used to retrieve information regarding contests.
     * It will be initialized in the constructor and never changed afterwards.
     * It cannot be null after initialization. It has a getter.
     * </p>
     */
    private final ContestManager bean;

    /**
     * <p>
     * The bean used to retrieve information regarding submissions.
     * It will be initialized in the constructor and never changed afterwards.
     * It cannot be null after initialization. It has a getter.
     * </p>
     */
    private final SubmissionManager submissionManager;

    /**
     * <p>
     * Indicates whether an email should be sent when a phase has started.
     * It will be initialized in the constructor and never changed afterwards.
     * </p>
     */
    private final boolean sendStartPhaseEmail;

    /**
     * <p>
     * The identifier of the source from which the template(used to send an email if a phase has started)
     * will be retrieved.
     * It will be initialized in the constructor, if provided, and never changed afterwards.
     * It cannot be empty string but it can be null if it is not provided in the configuration.
     * </p>
     */
    private final String startEmailTemplateSource;

    /**
     * <p>
     * The name of the template used to send an email if a phase has started.
     * It will be initialized in the constructor and never changed afterwards.
     * It cannot be null or empty string after initialization.
     * </p>
     */
    private final String startEmailTemplateName;

    /**
     * <p>
     * The subject of the email sent if a phase has started.
     * It will be initialized in the constructor and never changed afterwards.
     * It cannot be null or empty string after initialization.
     * </p>
     */
    private final String startEmailSubject;

    /**
     * <p>
     * The address from which the email(sent if a phase has started) will be sent.
     * It will be initialized in the constructor and never changed afterwards.
     * It cannot be null or empty string after initialization.
     * </p>
     */
    private final String startEmailFromAddress;

    /**
     * <p>
     * Indicates whether an email should be sent when a phase has ended.
     * It will be initialized in the constructor and never changed afterwards.
     * </p>
     */
    private final boolean sendEndPhaseEmail;

    /**
     * <p>
     * The identifier of the source from which the template(used to send an email if a phase has ended)
     * will be retrieved.
     * It will be initialized in the constructor, if provided, and never changed afterwards.
     * It cannot be empty string but it can be null if it is not provided in the configuration.
     * </p>
     */
    private final String endEmailTemplateSource;

    /**
     * <p>
     * The name of the template used to send an email if a phase has ended.
     * It will be initialized in the constructor and never changed afterwards.
     * It cannot be null or empty string after initialization.
     * </p>
     */
    private final String endEmailTemplateName;

    /**
     * <p>
     * The subject of the email sent if a phase has ended.
     * It will be initialized in the constructor and never changed afterwards.
     * It cannot be null or empty string after initialization.
     * </p>
     */
    private final String endEmailSubject;

    /**
     * <p>
     * The address from which the email(sent if a phase has ended) will be sent.
     * It will be initialized in the constructor and never changed afterwards.
     * It cannot be null or empty string after initialization.
     * </p>
     */
    private final String endEmailFromAddress;

    /**
     * <p>
     * Indicates whether an email should be sent when there are less than 8 hours remaining before winner
     * announcement deadline(<code>Contest.winnerAnnouncementDeadline</code>).
     * It will be initialized in the constructor and never changed afterwards.
     * </p>
     */
    private final boolean sendEightHoursReminderEmail;

    /**
     * <p>
     * The identifier of the source from which the template(used to send an email if there are less than 8 hours before
     * the winner announcement deadline) will be retrieved.
     * It will be initialized in the constructor, if provided, and never changed afterwards.
     * It cannot be empty string but it can be null if it is not provided in the configuration.
     * </p>
     */
    private final String eightHoursReminderEmailTemplateSource;

    /**
     * <p>
     * The name of the template used to send an email if there are less than 8 hours before the winner announcement
     * deadline. It will be initialized in the constructor and never changed afterwards.
     * It cannot be null or empty string after initialization.
     * </p>
     */
    private final String eightHoursReminderEmailTemplateName;

    /**
     * <p>
     * The subject of the email sent if there are less than 8 hours before the winner announcement deadline.
     * It will be initialized in the constructor and never changed afterwards.
     * It cannot be null or empty string after initialization.
     * </p>
     */
    private final String eightHoursReminderEmailSubject;

    /**
     * <p>
     * The address from which the email(sent if there are less than 8 hours before the winner announcement deadline)
     * will be sent. It will be initialized in the constructor and never changed afterwards.
     * It cannot be null or empty string after initialization.
     * </p>
     */
    private final String eightHoursReminderEmailFromAddress;

    /**
     * <p>
     * Indicates whether an email should be sent when there is less then one hour remaining before winner announcement
     * deadline(<code>Contest.winnerAnnouncementDeadline</code>).
     * It will be initialized in the constructor and never changed afterwards.
     * </p>
     */
    private final boolean sendOneHourReminderEmail;

    /**
     * <p>
     * The identifier of the source from which the template(used to send an email if there is less than 1 hour before
     * the winner announcement deadline) will be retrieved.
     * It will be initialized in the constructor, if provided, and never changed afterwards.
     * It cannot be empty string but it can be null if it is not provided in the configuration.
     * </p>
     */
    private final String oneHourReminderEmailTemplateSource;

    /**
     * <p>
     * The name of the template used to send an email if there is less than 1 hour before the winner announcement
     * deadline. It will be initialized in the constructor and never changed afterwards.
     * It cannot be null or empty string after initialization.
     * </p>
     */
    private final String oneHourReminderEmailTemplateName;

    /**
     * <p>
     * The subject of the email sent if there is less than 1 hour before the winner announcement deadline.
     * It will be initialized in the constructor and never changed afterwards.
     * It cannot be null or empty string after initialization.
     * </p>
     */
    private final String oneHourReminderEmailSubject;

    /**
     * <p>
     * The address from which the email(sent if there is less than 1 hour before the winner announcement deadline)
     * will be sent. It will be initialized in the constructor and never changed afterwards. It cannot be null or
     * empty string after initialization.
     * </p>
     */
    private final String oneHourReminderEmailFromAddress;

    /**
     * <p>
     * The email message generator.
     * It will be initialized in the constructor and never changed afterwards.
     * It cannot be null after it is initialized. It will be used to generate the email message to be sent.
     * </p>
     */
    private final EmailMessageGenerator emailMessageGenerator;

    /**
     * <p>
     * Indicates if the contest statuses should be cached or not. It will be set in the
     * constructors (if it is not present in the configuration file it will be set to true in
     * constructors).
     * </p>
     */
    private final boolean useCache;

    /**
     * <p>
     * This map will hold the contest statuses. The key is of <code>String</code> type and represents the
     * status name. The value is of <code>ContestStatus</code> type and represent the contest status.
     * It will be initialized to a new <code>HashMap</code> in the constructors if <code>useCache</code>
     * was not set to false through configuration. After initialization it cannot be null. The reference
     * is final but its contents can change (once,when the statuses are added in the perform method).
     * The keys cannot be null and empty string and the values cannot be null.
     * </p>
     */
    private final Map < String, ContestStatus > statusesCache;

    /**
     * <p>A <code>String</code> providing the URL for the <code>Forum EJB</code> provider.</p>
     */
    private String forumBeanProviderUrl;

    /**
     * Data Source
     */
    private final DataSource dataSource;

      /**
     * <p>A <code>String</code> providing studio jndi name</p>
     */
    private String studioJndiName;

    /**
     * <p>A <code>Log</code> to be used for logging the events occurred while advancing the projects to phases.</p>
     */
    private transient Log log;

    /**
     * <p>
     * This is a private common constructor called by all the other four public constructors.
     * </p>
     *
     * @param namespace The configuration namespace.
     * @param bean The <code>ContestManager</code> bean.
     * @param useGivenBean Indicates whether to use the given <code>ContestManager</code> bean.
     *
     * @throws IllegalArgumentException If given namespace is null or empty(trimmed).
     *         Or if given <code>useGivenBean</code> is true and given <code>ContestManager</code> is null.
     * @throws PhaseHandlerConfigurationException If error while loading properties, or any required property
     *         is missing, or if error while creating <code>EmailMessageGenerator</code> by <code>ObjectFactory</code>.
     * @throws PhaseHandlingException If error while looking up <code>ContestManager</code> by JNDI.
     */
    private AbstractPhaseHandler(String namespace, ContestManager bean, boolean useGivenBean)
        throws PhaseHandlingException {

        LogManager.setLogFactory(new Log4jLogFactory());
        this.log = LogManager.getLog();

        //Check namespace
        ExceptionUtils.checkNullOrEmpty(namespace, null, null, "Namespace should not be empty(trimmed) or null.");

        if (useGivenBean) {
            //Use given bean, Check it is not null
            ExceptionUtils.checkNull(bean, null, null, "Given ContestManager should not be null.");
            this.bean = bean;
            this.submissionManager = null;
        } else {
            String beanName = loadProperty(namespace, BEAN_NAME, !useGivenBean);
            String submissionManagerBeanName = loadProperty(namespace, SUBMISSION_MANAGER_BEAN_NAME, !useGivenBean);
            //Lookup bean by JNDI
            try {
                InitialContext ctx = new InitialContext();
                this.bean = (ContestManager) ctx.lookup(beanName);
                //Just in case the looked up bean is null
                checkNull(this.bean, "The ContestManager under context name " + beanName);
                this.submissionManager = (SubmissionManager) ctx.lookup(submissionManagerBeanName);
                checkNull(this.submissionManager, "The SubmissionManager under context name "
                                                  + submissionManagerBeanName);
            } catch (NamingException e) {
                throw new PhaseHandlingException("Error while looking up ContestManager", e);
            } catch (ClassCastException e) {
                throw new PhaseHandlingException(
                    "The object under context name " + beanName + " is not type of ContestManager.", e);
            }
        }

        //Load USE_CACHE property
        this.useCache = loadBooleanProperty(namespace, USE_CACHE);
        this.forumBeanProviderUrl = loadProperty(namespace, FORUM_BEAN_PROVIDER_URL, true);
        this.statusesCache = this.useCache ? new HashMap < String, ContestStatus >() : null;

        //Load start email related properties
        this.sendStartPhaseEmail = loadBooleanProperty(namespace, SEND_START_PHASE_EMAIL);
        this.startEmailSubject = this.sendStartPhaseEmail
            ? loadProperty(namespace, START_EMAIL_SUBJECT, true) : null;
        this.startEmailFromAddress = this.sendStartPhaseEmail
            ? loadProperty(namespace, START_EMAIL_FROM_ADDRESS, true) : null;
        this.startEmailTemplateName = this.sendStartPhaseEmail
            ? loadProperty(namespace, START_EMAIL_TEMPLATE_NAME, true) : null;
        this.startEmailTemplateSource = this.sendStartPhaseEmail
            ? loadProperty(namespace, START_EMAIL_TEMPLATE_SOURCE, false) : null;

        //Load end email related properties
        this.sendEndPhaseEmail = loadBooleanProperty(namespace, SEND_END_PHASE_EMAIL);
        this.endEmailSubject = this.sendEndPhaseEmail
            ? loadProperty(namespace, END_EMAIL_SUBJECT, true) : null;
        this.endEmailFromAddress = this.sendEndPhaseEmail
            ? loadProperty(namespace, END_EMAIL_FROM_ADDRESS, true) : null;
        this.endEmailTemplateName = this.sendEndPhaseEmail
            ? loadProperty(namespace, END_EMAIL_TEMPLATE_NAME, true) : null;
        this.endEmailTemplateSource = this.sendEndPhaseEmail
            ? loadProperty(namespace, END_EMAIL_TEMPLATE_SOURCE, false) : null;

        //Load one hour email related properties
        this.sendOneHourReminderEmail = loadBooleanProperty(namespace, SEND_ONE_HOUR_PHASE_EMAIL);
        this.oneHourReminderEmailSubject = this.sendOneHourReminderEmail
            ? loadProperty(namespace, ONE_HOUR_EMAIL_SUBJECT, true) : null;
        this.oneHourReminderEmailFromAddress = this.sendOneHourReminderEmail
            ? loadProperty(namespace, ONE_HOUR_EMAIL_FROM_ADDRESS, true) : null;
        this.oneHourReminderEmailTemplateName = this.sendOneHourReminderEmail
            ? loadProperty(namespace, ONE_HOUR_EMAIL_TEMPLATE_NAME, true) : null;
        this.oneHourReminderEmailTemplateSource = this.sendOneHourReminderEmail
            ? loadProperty(namespace, ONE_HOUR_EMAIL_TEMPLATE_SOURCE, false) : null;

        //Load eight hours email related properties
        this.sendEightHoursReminderEmail = loadBooleanProperty(namespace, SEND_EIGHT_HOURS_PHASE_EMAIL);
        this.eightHoursReminderEmailSubject = this.sendEightHoursReminderEmail
            ? loadProperty(namespace, EIGHT_HOURS_EMAIL_SUBJECT, true) : null;
        this.eightHoursReminderEmailFromAddress = this.sendEightHoursReminderEmail
            ? loadProperty(namespace, EIGHT_HOURS_EMAIL_FROM_ADDRESS, true) : null;
        this.eightHoursReminderEmailTemplateName = this.sendEightHoursReminderEmail
            ? loadProperty(namespace, EIGHT_HOURS_EMAIL_TEMPLATE_NAME, true) : null;
        this.eightHoursReminderEmailTemplateSource = this.sendEightHoursReminderEmail
            ? loadProperty(namespace, EIGHT_HOURS_EMAIL_TEMPLATE_SOURCE, false) : null;

        String messageGeneratorKey = loadProperty(namespace, MESSAGE_GENERATOR_KEY, false);
        if (messageGeneratorKey == null) {
            //if message_generator_key is not present initialize the emailMessageGeneraor field to a
            //DefaultEmailMessageGenerator instance
            this.emailMessageGenerator = new DefaultEmailMessageGenerator();
        } else {
            String factoryNamespace = loadProperty(namespace, SPECIFICATION_FACTORY_NAMESPACE, false);
            //if specification_factory_namespace is not present use DEFAULT_NAMESPACE
            factoryNamespace = factoryNamespace == null ? DEFAULT_NAMESPACE : factoryNamespace;
            try {
                this.emailMessageGenerator = (EmailMessageGenerator) new ObjectFactory(
                    new ConfigManagerSpecificationFactory(factoryNamespace)).createObject(messageGeneratorKey);
            } catch (BaseException e) {
                throw e instanceof PhaseHandlerConfigurationException ? (PhaseHandlerConfigurationException) e
                    : new PhaseHandlerConfigurationException(
                        "Error while creating EmailMessageGenerator by ObjectFactory.", e);
            } catch (ClassCastException e) {
                throw new PhaseHandlerConfigurationException(
                    "Object created by ObjectFactory is not type of EmailMessageGenerator.", e);
            }
        }

        // initialize connectionName with property value if provided.
        String studioJndiName = loadProperty(namespace, STUDIO_JNDI_NAME, true);

        try
        {
             InitialContext cxt = new InitialContext();
             this.dataSource = (DataSource)cxt.lookup(studioJndiName);

        }
        catch (Exception e)
        {
             throw new PhaseHandlerConfigurationException("Error lookup studio ds", e);
        }
       


    }

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
    protected AbstractPhaseHandler() throws PhaseHandlingException {
        this(DEFAULT_NAMESPACE, null, false);
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
     */
    protected AbstractPhaseHandler(ContestManager bean) throws PhaseHandlingException {
        this(DEFAULT_NAMESPACE, bean, true);
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
    protected AbstractPhaseHandler(String namespace) throws PhaseHandlingException {
        this(namespace, null, false);
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
     */
    protected AbstractPhaseHandler(String namespace, ContestManager bean) throws PhaseHandlingException {
        this(namespace, bean, true);
    }

    /**
     * <p>
     * Load boolean value from configuration. The value is expected to be either 'true' or 'false'(case insensitive).
     * </p>
     *
     * <p>
     * All the boolean values are optional. If not defined, <code>true</code> is used as default.
     * </p>
     *
     * @param namespace The configuration namespace.
     * @param propertyName The name of property to load its value.
     *
     * @return The boolean value. If property is missing, it will be default to <code>true</code>.
     *
     * @throws PhaseHandlerConfigurationException - If given namespace is unknown; Or the property value is empty;
     *         Or the property has multiple values; Or the property value is neither 'true' nor 'false'(case
     *         insensitive).
     *
     * @see #loadProperty(String, String, boolean)
     */
    private static boolean loadBooleanProperty(String namespace, String propertyName)
        throws PhaseHandlerConfigurationException {
        //Boolean values are optional
        String property = loadProperty(namespace, propertyName, false);
        //Default to true if not defined
        if (property == null) {
            return true;
        }
        if (Boolean.TRUE.toString().equalsIgnoreCase(property)) {
            return true;
        } else if (Boolean.FALSE.toString().equalsIgnoreCase(property)) {
            return false;
        }
        throw new PhaseHandlerConfigurationException(
            propertyName + " must be either 'true' or 'false'(case insensitive). But is: " + property);
    }

    /**
     * <p>
     * Get property value from configuration.
     * </p>
     *
     * <p>
     * If property is required, then it cannot be null. If property value is empty(trimmed), exception will be raised.
     * </p>
     *
     * @param namespace The configuration namespace.
     * @param propertyName The name of property to load its value.
     * @param required Indicates whether this property is required.
     *
     * @return The property value. May be null if property is optional and not present.
     *
     * @throws PhaseHandlerConfigurationException - If given namespace is unknown; Or the property value is empty;
     *         Or the property has multiple values; Or the property is required but missing.
     */
    private static String loadProperty(String namespace, String propertyName, boolean required)
        throws PhaseHandlerConfigurationException {
        try {
            String property = null;
            String[] properties = ConfigManager.getInstance().getStringArray(namespace, propertyName);

            if (properties != null) {
                if (properties.length == 1) {
                    property = properties[0];
                } else {
                    //Throw error if property has multiple values
                    throw new PhaseHandlerConfigurationException(propertyName + " should not have multiple values.");
                }
            }

            validateProperty(property, required, "Value of property " + propertyName);

            return property;
        } catch (UnknownNamespaceException e) {
            throw new PhaseHandlerConfigurationException("Given namespace is unknown.", e);
        } catch (IllegalArgumentException e) {
            throw new PhaseHandlerConfigurationException("Property value is invalid.", e);
        }
    }

    /**
     * <p>
     * Validates given property.
     * </p>
     *
     * <p>
     *     <strong>Validation Rules:</strong>
     *     <ul>
     *         <li>If given property is required, then its value must be non-null and
     *         non-empty(trimmed)</li>
     *         <li>If given property is optional, then its value may be null, but must be
     *         non-empty(trimmed)</li>
     *     </ul>
     * </p>
     *
     * @param property The property value to be validated.
     * @param required Indicates whether the property is required.
     * @param usage What the property is intent to represent.
     *
     * @throws IllegalArgumentException - If the given property is empty string (trimmed).
     *         Or if it is required but is missing(value is null).
     */
    private static void validateProperty(String property, boolean required,
        String usage) {
        if (!required && property == null) {
            //Property is optional and not present, simply return
            return;
        }

        ExceptionUtils.checkNullOrEmpty(property, null, null,
            usage + " should not be empty(trimmed)" + (required ? " or null." : "."));
    }

    /**
     * <p>
     * Wrap the given error while sending email and re throw it.
     * </p>
     *
     * <p>
     * If given <code>messageGenerated</code> is false, then this error occurs while generating email
     * message and thus an <code>EmailMessageGenerationException</code> will be thrown. Otherwise it
     * means the error occurs while sending email and thus an <code>EmailSendingException</code> will
     * be thrown.
     * </p>
     *
     * @param e The root error cause to be wrapped and re thrown.
     * @param messageGenerated Indicates whether the email message has been generated successfully.
     *
     * @param phase
     * @throws EmailMessageGenerationException If <code>messageGenerated</code> is false.
     * @throws EmailSendingException If <code>messageGenerated</code> is true.
     */
    private static void rethrowEmailError(Throwable e, boolean messageGenerated, Phase phase)
        throws EmailMessageGenerationException, EmailSendingException {
        Contest contest = null;
        try {
            contest = (Contest) phase.getProject().getAttribute("contest");
            if (messageGenerated) {
                throw e instanceof EmailSendingException ? (EmailSendingException) e
                    : new EmailSendingException("Error while sending email.", e);
            } else {
                throw e instanceof EmailMessageGenerationException ? (EmailMessageGenerationException) e
                    : new EmailMessageGenerationException("Error while generating email to be sent.", e);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            alertLog.log(Level.SEVERE, "*** Could not generate or send an email to creator of contest ["
                                       + contest.getName() + "]. Contest ID: " + contest.getContestId(), e1);
        }
    }

    /**
     * <p>
     * Send email.
     * </p>
     *
     * <p>
     * The <code>phase.project.attributes["ResourceEmails"]</code> attribute is expected to be the list of valid
     * recipient email addresses(according to RFC822).
     * </p>
     *
     * @param templateSource The template source. May be null.
     * @param templateName The name of the template.
     * @param subject The subject of email.
     * @param fromAddr The from address of email.
     * @param phase The phase.
     *
     * @throws EmailMessageGenerationException If errors occur while generating the message to be sent.
     * @throws EmailSendingException If errors occur while sending the email message.
     */
    protected void sendEmail(String templateSource, String templateName, String subject, String fromAddr, Phase phase)
        throws EmailMessageGenerationException, EmailSendingException {

        boolean messageGenerated = false;

        try {

            //Generate the message body first
            DocumentGenerator documentGenerator = DocumentGenerator.getInstance();
            Template template = templateSource == null
                ? documentGenerator.getTemplate(templateName)
                : documentGenerator.getTemplate(templateSource, templateName);

            String messageBody = this.emailMessageGenerator.generateMessage(template, phase);

            if (this.log == null) {
                this.log = LogManager.getLog();
            }

            if (this.log.isEnabled(com.topcoder.util.log.Level.DEBUG)) {
                this.log.log(com.topcoder.util.log.Level.DEBUG,
                             "Generated following email message of subject [" + subject + "] to be sent to ["
                             + fromAddr + "] \n" + messageBody);
            }

            //Create a TCSEmailMessage to be sent
            TCSEmailMessage email = new TCSEmailMessage();

            //Set subject, from address and message body.
            email.setSubject(subject);
            email.setFromAddress(fromAddr);
            email.setBody(messageBody);

            List toAddresses = getProjectAttribute(phase, List.class, PROJECT_ATTR_RESOURCE_EMAILS, true);

            if (toAddresses.isEmpty()) {
                throw new IllegalArgumentException(
                    "Project attribute 'ResourceEmails' should not be an empty list.");
            }

            //Add to addresses
            for (Object toAddress : toAddresses) {
                ExceptionUtils.checkNull(toAddress, null, null, "To address must be non-null.");
                email.addToAddress(toAddress.toString(), TCSEmailMessage.TO);
            }
            email.addToAddress("cockpit-admins@topcoder.com", TCSEmailMessage.BCC);


            //Now the email message is generated successfully
            messageGenerated = true;

            //Send email
            EmailEngine.send(email);
            if (this.log.isEnabled(com.topcoder.util.log.Level.DEBUG)) {
                this.log.log(com.topcoder.util.log.Level.DEBUG,
                             "Sent email message of subject [" + subject + "] to [" + fromAddr + "]");
            }
        } catch (BaseException e) {
            rethrowEmailError(e, messageGenerated, phase);
        } catch (ConfigManagerException e) {
            rethrowEmailError(e, messageGenerated, phase);
        } catch (IllegalArgumentException e) {
            rethrowEmailError(e, messageGenerated, phase);
        }
    }

    /**
     * <p>
     * This method will be used by subclasses to send an email to the resources associated with time-line notification
     * for the project.
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
     * @param phase The phase.
     *
     * @throws IllegalArgumentException If phase is null.
     * @throws PhaseHandlingException If phase's <code>phaseType</code> or <code>phaseStatus</code> property is null.
     *         Or if its project does not contain a valid <em>contest</em> attribute.
     * @throws EmailMessageGenerationException If errors occur while generating the message to be sent.
     * @throws EmailSendingException If errors occur while sending the email message.
     *
     * @see #validatePhase(Phase, String, Object[])
     * @see #sendEmail(String, String, String, String, Phase)
     */
    protected void sendEmail(Phase phase) throws PhaseHandlingException {
        validatePhase(phase, null);
        String phaseStatus = phase.getPhaseStatus().getName();

        //When PhaseStatus is SCHEDULED, it means the phase is about to be started, so send start email
        if (PhaseStatus.SCHEDULED.getName().equals(phaseStatus) && this.sendStartPhaseEmail) {
            if (phase.getPhaseType().getName().equals(CockpitPhase.ACTIVE.getPhaseType())) {
                this.sendEmail(this.startEmailTemplateSource, "templates/contestActive.txt",
                               "[TopCoder Cockpit] Your Contest is Active", this.startEmailFromAddress, phase);
            } else if (phase.getPhaseType().getName().equals(CockpitPhase.ACTION_REQUIRED.getPhaseType())) {
                this.sendEmail(this.startEmailTemplateSource, "templates/contestActionRequired.txt",
                               "[TopCoder Cockpit] Your Action is Required", this.startEmailFromAddress, phase);
            } else if (phase.getPhaseType().getName().equals(CockpitPhase.COMPLETED.getPhaseType())) {
                this.sendEmail(this.startEmailTemplateSource, "templates/contestCompleted.txt",
                               "[TopCoder Cockpit] Contest Closed", this.startEmailFromAddress, phase);
            } else {
// temporarily removed phase start/end emails
//                this.sendEmail(this.startEmailTemplateSource, this.startEmailTemplateName, this.startEmailSubject,
//                    this.startEmailFromAddress, phase);
            }
        } else if (PhaseStatus.OPEN.getName().equals(phaseStatus) && this.sendEndPhaseEmail) {
        //When PhaseStatus is OPEN, it means the phase is about to be ended, so send end email
// temporarily removed phase start/end emails
//            this.sendEmail(this.endEmailTemplateSource, this.endEmailTemplateName, this.endEmailSubject,
//                this.endEmailFromAddress, phase);
        }

        //Otherwise, do not send email.
    }

    /**
     * <p>
     * This method will be used by subclasses to send an email to the client if there are less than 8 hours
     * before the winner announcement deadline.
     * </p>
     *
     * <p>
     * The <code>phase.project.attributes["ResourceEmails"]</code> attribute is expected to be the list of valid
     * recipient email addresses(according to RFC822).
     * </p>
     *
     * @param phase The phase.
     *
     * @throws IllegalArgumentException If phase is null.
     * @throws PhaseHandlingException If phase's <code>phaseType</code> or <code>phaseStatus</code> property is null.
     *         Or if its project does not contain a valid <em>contest</em> attribute.
     * @throws EmailMessageGenerationException If errors occur while generating the message to be sent.
     * @throws EmailSendingException If errors occur while sending the email message.
     *
     * @see #validatePhase(Phase, String, Object[])
     */
    protected void sendEightHoursReminderEmail(Phase phase) throws PhaseHandlingException {
        validatePhase(phase, null);
        if (this.sendEightHoursReminderEmail) {
            this.sendEmail(this.eightHoursReminderEmailTemplateSource, this.eightHoursReminderEmailTemplateName,
                this.eightHoursReminderEmailSubject, this.eightHoursReminderEmailFromAddress, phase);
        }
    }

    /**
     * <p>
     * This method will be used by subclasses to send an email to the client if there are less than 8 hours
     * before the winner announcement deadline.
     * </p>
     *
     * <p>
     * The <code>phase.project.attributes["ResourceEmails"]</code> attribute is expected to be the list of valid
     * recipient email addresses(according to RFC822).
     * </p>
     *
     * @param phase The phase.
     *
     * @throws IllegalArgumentException If phase is null.
     * @throws PhaseHandlingException If phase's <code>phaseType</code> or <code>phaseStatus</code> property is null.
     *         Or if its project does not contain a valid <em>contest</em> attribute.
     * @throws EmailMessageGenerationException If errors occur while generating the message to be sent.
     * @throws EmailSendingException If errors occur while sending the email message.
     *
     * @see #validatePhase(Phase, String, Object[])
     */
    protected void sendTwentyFourHoursReminderEmail(Phase phase) throws PhaseHandlingException {
        validatePhase(phase, null);
        this.sendEmail(this.eightHoursReminderEmailTemplateSource, "templates/contest24HoursLeft.txt",
                       "[TopCoder Cockpit] Your Contest is in Danger", this.eightHoursReminderEmailFromAddress,
                       phase);
    }

    /**
     * <p>
     * This method will be used by subclasses to send an email to the client if there is less than 1 hour before the
     * winner announcement deadline.
     * </p>
     *
     * <p>
     * The <code>phase.project.attributes["ResourceEmails"]</code> attribute is expected to be the list of valid
     * recipient email addresses(according to RFC822).
     * </p>
     *
     * @param phase The phase.
     *
     * @throws IllegalArgumentException If phase is null.
     * @throws PhaseHandlingException If phase's <code>phaseType</code> or <code>phaseStatus</code> property is null.
     *         Or if its project does not contain a valid <em>contest</em> attribute.
     * @throws EmailMessageGenerationException If errors occur while generating the message to be sent.
     * @throws EmailSendingException If errors occur while sending the email message.
     *
     * @see #validatePhase(Phase, String, Object[])
     */
    protected void sendOneHourReminderEmail(Phase phase) throws PhaseHandlingException {
        validatePhase(phase, null);
        if (this.sendOneHourReminderEmail) {
            this.sendEmail(this.oneHourReminderEmailTemplateSource, this.oneHourReminderEmailTemplateName,
                this.oneHourReminderEmailSubject, this.oneHourReminderEmailFromAddress, phase);
        }
    }

    /**
     * <p>
     * Getter for the <code>ContestManager</code>.
     * </p>
     *
     * @return the <code>ContestManager</code>. Will never be null.
     */
    protected ContestManager getContestManager() {
        return this.bean;
    }

    /**
     * <p>Gets the URL for the provider of the <code>Forum EJB</code>.</p>
     *
     * @return a <code>String</code> providing the URL for the <code>Forum EJB</code> provider.
     */
    protected String getForumBeanProviderUrl() {
        return this.forumBeanProviderUrl;
    }

    /**
     * <p>
     * Checks if contest statuses can be cached.
     * </p>
     *
     * @return true if contest statuses can be cached, false otherwise.
     */
    protected boolean useCache() {
        return this.useCache;
    }

    /**
     * <p>
     * Getter for the statuses cache.
     * </p>
     *
     * @return the statuses cache. May be null if statuses are not cached.
     *
     * @see #useCache()
     */
    protected Map < String, ContestStatus > getStatusesCache() {
        return this.statusesCache;
    }

    /**
     * <p>
     * Check given object is not null.
     * </p>
     *
     * @param object to be checked.
     * @param usage The usage of the given object.
     *
     * @throws PhaseHandlingException If given object is null.
     */
    private static void checkNull(Object object, String usage) throws PhaseHandlingException {
        if (object == null) {
            throw new PhaseHandlingException(usage + " should not be null.");
        }
    }

    /**
     * <p>
     * Validate given phase is valid.
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
     * If given <code>desiredType</code> is not null, then the phase type must match.
     * </p>
     *
     * <p>
     *   <strong>Note:</strong>
     *   If <code>doNotValidateContest</code> parameter is present, the <em>contest</em> attribute will not be
     *   validated.
     * </p>
     *
     * @param phase to be validated.
     * @param desiredType If given(not null), validate the phase is as the desired type.
     * @param doNotValidateContest Special case: the Draft phase does not have a contest associated when not yet
     *        started.
     *
     * @throws IllegalArgumentException If phase is null, or if phase type does not match given desired type(if given).
     *
     * @throws PhaseHandlingException If phase's <code>phaseType</code>, or <code>phaseStatus</code> property is null,
     *         or the phase's project does not have a valid <em>contest</em> attribute and <code>doNotValidateContest
     *         </code> parameter is not given.
     */
    protected static void validatePhase(Phase phase, String desiredType, Object... doNotValidateContest)
        throws PhaseHandlingException {
        ExceptionUtils.checkNull(phase, null, null, "Phase should not be null.");

        //Phase.project will never be null

        checkNull(phase.getPhaseType(), "Phase.phaseType");
        checkNull(phase.getPhaseStatus(), "Phase.phaseStatus");

        if (desiredType != null && !desiredType.equals(phase.getPhaseType().getName())) {
            throw new IllegalArgumentException(
                "Phase should be type: " + desiredType + ", but is: " + phase.getPhaseType().getName());
        }

        if (doNotValidateContest == null || doNotValidateContest.length == 0) {
            getProjectAttribute(phase, Contest.class, PROJECT_ATTR_CONTEST, true);
        }
        //Do not validate contest when Draft phase has not yet started
    }

    /**
     * <p>
     * Get attribute from <code>Project</code>.
     * </p>
     *
     * <p>
     * The implementation should ensure non-null parameters are passed in.
     * </p>
     *
     * @param <T> Generic type.
     * @param phase The phase.
     * @param clazz The class of attribute value.
     * @param key The attribute key.
     * @param required Indicates whether the attribute value is required.
     *
     * @return The attribute value.
     *
     * @throws IllegalArgumentException If given phase or clazz is null, or key is null or empty.
     * @throws PhaseHandlingException If attribute is required but does not exist, or if it is not desired type.
     */
    protected static < T > T getProjectAttribute(Phase phase, Class < T > clazz, String key, boolean required)
        throws PhaseHandlingException {
        ExceptionUtils.checkNull(phase, null, null, "Phase should not be null.");
        ExceptionUtils.checkNull(clazz, null, null, "Class should not be null.");
        Serializable value = phase.getProject().getAttribute(key);
        if (value == null && !required) {
            return null;
        }
        checkNull(value, "Project's attribute [" + key + "]");
        try {
            return clazz.cast(value);
        } catch (ClassCastException e) {
            throw new PhaseHandlingException("Project attribute " + value + " is not desired type: " + clazz, e);
        }
    }

    /**
     * <p>
     * Compute the remain time from current to <code>Contest.winnerAnnoucementDeadline</code>.
     * </p>
     *
     * @param contest The <code>phase.project.attributes["contest"]</code> attribute.
     *
     * @return the remain time from current to <code>Contest.winnerAnnoucementDeadline</code>.
     *
     * @throws IllegalArgumentException If given contest is null.
     * @throws PhaseHandlingException If <code>Contest.winnerAnnoucementDeadline</code> is null.
     */
    protected static long computeWinnerAnnouncementDeadlineRemain(Contest contest) throws PhaseHandlingException {
        ExceptionUtils.checkNull(contest, null, null, "Contest should not be null.");
        checkNull(contest.getWinnerAnnoucementDeadline(), "Contest.winnerAnnoucementDeadline");

        return contest.getWinnerAnnoucementDeadline().getTime() - System.currentTimeMillis();
    }

    /**
     * <p>
     * Check whether contest end date has been reached.
     * </p>
     *
     * @param contest to check whether its end date has been reached.
     *
     * @return true if contest end date has been reached.
     *
     * @throws IllegalArgumentException If given contest is null.
     * @throws PhaseHandlingException If <code>Contest.endDate</code> is null.
     */
    protected static boolean isEndDateReached(Contest contest) throws PhaseHandlingException {
        ExceptionUtils.checkNull(contest, null, null, "Contest should not be null.");
        checkNull(contest.getEndDate(), "Contest.endDate");

        return System.currentTimeMillis() >= contest.getEndDate().getTime();
    }

    /**
     * <p>
     * Check whether contest start date has been reached.
     * </p>
     *
     * @param contest to check whether its start date has been reached.
     *
     * @return true if contest start date has been reached.
     *
     * @throws IllegalArgumentException If given contest is null.
     * @throws PhaseHandlingException If <code>Contest.endDate</code> is null.
     */
    protected static boolean isStartDateReached(Contest contest) throws PhaseHandlingException {
        ExceptionUtils.checkNull(contest, null, null, "Contest should not be null.");
        checkNull(contest.getStartDate(), "Contest.startDate");

        return System.currentTimeMillis() >= contest.getStartDate().getTime();
    }

    /**
     * <p>
     * Check whether contest has received enough submissions.
     * </p>
     *
     * @param phase The phase.
     * @param contest The contest to check.
     *
     * @return true if enough submissions has been received.
     *
     * @throws IllegalArgumentException If given phase or contest is null.
     * @throws PhaseHandlingException If phase's project does not contain
     *         a valid <em>MinimumSubmissions</em> attribute(which should be a non-null <code>Number</code>).
     */
    protected static boolean isEnoughSubmissionsReceived(Phase phase, Contest contest) throws PhaseHandlingException {
        ExceptionUtils.checkNull(phase, null, null, "Phase should not be null.");
        ExceptionUtils.checkNull(contest, null, null, "Contest should not be null.");

        // ISV : TODO : Restore the code once the Contest entities are populated with MinimumSubmissions properties
        // For now 1 is used as minimum submissions number
//        Number requiredMinimum = getProjectAttribute(phase, Number.class, PROJECT_ATTR_MINIMUM_SUBMISSIONS, true);
        Number requiredMinimum = new Long(1);

        return contest.getSubmissions() != null && contest.getSubmissions().size() >= requiredMinimum.intValue();
    }

    /**
     * <p>
     * Check whether the client has chosen a winner.
     * </p>
     *
     * @param contest The contest to check.
     *
     * @return true if the client has chosen a winner.
     *
     * @throws IllegalArgumentException If given contest is null.
     */
    protected static boolean isWinnerChosen(Contest contest) {
        ExceptionUtils.checkNull(contest, null, null, "Contest should not be null.");

        return contest.getResults() != null && !contest.getResults().isEmpty();
    }

    /**
     * <p>Checks whether the client has paid for the winning submission.</p>
     *
     * @param contest a <code>Contest</code> representing the contest to check the payment for winning submission for.
     * @return <code>true</code> if the client has paid for the winning submission; <code>false</code> otherwise.
     * @throws IllegalArgumentException if specified <code>contest</code> is <code>null</code>.
     * @throws SubmissionManagementException if an error occurs while 
     */
    protected boolean isWinningSubmissionPaid(Contest contest) throws SubmissionManagementException {
        ExceptionUtils.checkNull(contest, null, null, "Contest should not be null.");
        Set<Submission> submissions = contest.getSubmissions();
        if (submissions != null) {
            for (Submission s : submissions) {
                Set<Prize> prizes = s.getPrizes();
                for (Prize p : prizes) {
                    if (p.getPlace() == 1) {
                        SubmissionPayment payment = this.submissionManager.getSubmissionPayment(s.getSubmissionId());
                        if (payment != null) {
                            if (payment.getPayPalOrderId() != null) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * <p>
     * Check whether the contest status match.
     * </p>
     *
     * @param contest The contest to check.
     * @param cockpitPhase The <code>CockpitPhase</code> to match.
     *
     * @return true If contest status match.
     *
     * @throws IllegalArgumentException If given <code>cockpitPhase</code> or contest is null.
     * @throws PhaseHandlingException If <code>Contest.status</code> is null.
     */
    protected static boolean isContestStatusMatch(Contest contest, CockpitPhase cockpitPhase)
        throws PhaseHandlingException {
        ExceptionUtils.checkNull(cockpitPhase, null, null, "CockpitPhase should not be null.");
        ExceptionUtils.checkNull(contest, null, null, "Contest should not be null.");

        checkNull(contest.getStatus(), "Contest.status");

        return cockpitPhase.getContestStatus().equals(contest.getStatus().getName());
    }

    /**
     * <p>
     * This method abstracts the common behavior.
     * It is called by concrete implementations to send email and update new contest status.
     * </p>
     *
     * @param phase The phase to apply an operation to.
     * @param cockpitPhase The <code>CockpitPhase</code> enum identified by implementation. The implementation should
     *                     pass in a non-null value.
     *
     * @throws IllegalArgumentException If phase is null or if the phase type does not match. Or if
     *         <code>cockpitPhase</code> is null.
     * @throws PhaseHandlingException If its <code>phaseType</code> or <code>phaseStatus</code> property is null.
     *         Or if its project does not contain a valid <em>contest</em> attribute.
     *         Or if errors occur while performing the phase's contest status transition.
     * @throws EmailMessageGenerationException If errors occur while generating the message to be sent.
     * @throws EmailSendingException If errors occur while sending the email message.
     */
    protected void perform(Phase phase, CockpitPhase cockpitPhase)
        throws PhaseHandlingException {

        ExceptionUtils.checkNull(cockpitPhase, null, null, "CockpitPhase should not be null.");
        validatePhase(phase, cockpitPhase.getPhaseType());

        Contest contest = getProjectAttribute(phase, Contest.class, PROJECT_ATTR_CONTEST, true);

        String phaseStatus = phase.getPhaseStatus().getName();

        if (PhaseStatus.OPEN.getName().equals(phaseStatus)) {
            //Phase is about to end, send email
            this.sendEmail(phase);
        } else if (PhaseStatus.SCHEDULED.getName().equals(phaseStatus)) {
            //Phase is about to start, update the contest status
            try {
                Map < String, ContestStatus > statusMap;
                if (!this.useCache() || this.getStatusesCache().isEmpty()) {
                    //Either cache is not enabled or cache has not been fulfilled
                    List < ContestStatus > statusList = this.getContestManager().getAllContestStatuses();
                    checkNull(statusList, "The list of all contest statuses");
                    statusMap = this.useCache() ? this.getStatusesCache() : new HashMap < String, ContestStatus >();
                    for (ContestStatus status : statusList) {
                        statusMap.put(status.getName(), status);
                    }
                } else {
                    //Cache is enabled and has been fulfilled, Use cached map
                    statusMap = this.getStatusesCache();
                }

                ContestStatus newContestStatus = statusMap.get(cockpitPhase.getContestStatus());
                checkNull(newContestStatus, "Contest status entity : " + cockpitPhase.getContestStatus());

                contest.setStatus(newContestStatus);
                this.getContestManager().updateContestStatus(contest.getContestId(),
                                                             newContestStatus.getContestStatusId());
            } catch (ContestManagementException e) {
                throw new PhaseHandlingException("Error while updating contest status.", e);
            }

            //Phase is about to start, send email to notify
            this.sendEmail(phase);
        }

        //Else the PhaseStatus is neither SCHEDULED nor OPEN, does nothing...
    }

    /**
     * <p>
     * This method abstracts the common behavior
     * It is called by concrete implementations to determine whether phase can start or end.
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
     *     <strong>PhaseType:</strong>
     *     If given phase type does not match the <code>CockpitPhase.phaseType</code>, false is returned.
     * </p>
     *
     * @param phase The phase to determine whether it can start or end.
     * @param cockpitPhase The <code>CockpitPhase</code> enum identified by implementation. The implementation should
     *                     pass in a non-null value.
     *
     * @return true If the phase can be started or ended.
     *
     * @throws IllegalArgumentException If phase is null or if <code>cockpitPhase</code> is null.
     *
     * @throws PhaseHandlingException If phase's <code>phaseType</code> or <code>phaseStatus</code> property is null,
     *         or if phase's project does not contain a valid <em>contest</em> attribute.
     *
     * @see #validatePhase(Phase, String, Object[])
     * @see #canEnd(Phase, Contest)
     * @see #canStart(Phase, Contest)
     */
    protected boolean canPerform(Phase phase, CockpitPhase cockpitPhase) throws PhaseHandlingException {

        ExceptionUtils.checkNull(cockpitPhase, null, null, "CockpitPhase should not be null.");

        //Validate phase
        validatePhase(phase, null);

        //PhaseType does not match, return false
        if (!cockpitPhase.getPhaseType().equals(phase.getPhaseType().getName())) {
            return false;
        }

        Contest contest = getProjectAttribute(phase, Contest.class, PROJECT_ATTR_CONTEST, true);

        String phaseStatus = phase.getPhaseStatus().getName();

        return (PhaseStatus.OPEN.getName().equals(phaseStatus) && this.canEnd(phase, contest))
            || (PhaseStatus.SCHEDULED.getName().equals(phaseStatus) && this.canStart(phase, contest));
    }

    /**
     * <p>
     * The implementation should override this method as necessary to determine whether phase can start.
     * </p>
     *
     * @param phase The phase to determine whether it can start.
     * @param contest The <code>phase.project.attributes["contest"]</code> attribute.
     *
     * @return true always. The implementation should override this method as necessary.
     *
     * @throws PhaseHandlingException may be thrown by implementation.
     *
     * @see #canPerform(Phase, CockpitPhase)
     */
    protected boolean canStart(Phase phase, Contest contest) throws PhaseHandlingException {
        return true;
    }

    /**
     * <p>
     * The implementation should override this method as necessary to determine whether phase can end.
     * </p>
     *
     * @param phase The phase to determine whether it can end.
     * @param contest The <code>phase.project.attributes["contest"]</code> attribute.
     *
     * @return true always. The implementation should override this method as necessary.
     *
     * @throws PhaseHandlingException may be thrown by implementation.
     *
     * @see #canPerform(Phase, CockpitPhase)
     */
    protected boolean canEnd(Phase phase, Contest contest) throws PhaseHandlingException {
        return true;
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

                return dataSource.getConnection();
        } catch (SQLException ex) {
            throw new PhaseHandlingException("Could not create connection", ex);
        }
    }

    
    /**
     * Returns true if given string is either null or empty, false otherwise.
     *
     * @param str string to check.
     *
     * @return true if given string is either null or empty, false otherwise.
     */
    protected boolean isStringNullOrEmpty(String str) {
        return ((str == null) || (str.trim().length() == 0));
    }


    /**
     * A helper method to create an instance of DBConnectionFactory. This method
     * retrieves the value for connection factory namespace from the given
     * property name and namespace and uses the same to create an instance of
     * DBConnectionFactoryImpl.
     *
     * @param namespace configuration namespace to use.
     * @param connFactoryNSPropName name of property which holds connection
     *        factory namespace value.
     *
     * @return DBConnectionFactory instance.
     *
     * @throws ConfigurationException if property is missing or if there was an
     *         error when instantiating DBConnectionFactory.
     */
    protected DBConnectionFactory createDBConnectionFactory(String namespace,
                    String connFactoryNSPropName) throws PhaseHandlingException {
        String connectionFactoryNS = loadProperty(namespace, connFactoryNSPropName, true);

        try {
            return new DBConnectionFactoryImpl(connectionFactoryNS);
        } catch (UnknownConnectionException ex) {
            throw new PhaseHandlingException(
                            "Could not instantiate DBConnectionFactoryImpl", ex);
        } catch (com.topcoder.db.connectionfactory.ConfigurationException ex) {
            throw new PhaseHandlingException(
                            "Could not instantiate DBConnectionFactoryImpl", ex);
        }
    }



    /**
     * Close the connection.
     * @param conn the connection to close
     * @throws PersistenceException if error occurs when closing the connection
     */
    protected void closeConnection(Connection conn) throws PhaseHandlingException {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new PhaseHandlingException(
                        "Error occurs when closing the connection.", e);
            }
        }
    }

    /**
     * Close the prepared statement.
     * @param ps the prepared statement to close
     * @throws PersistenceException error occurs when closing the prepared
     *             statement
     */
    protected void closeStatement(PreparedStatement ps)
        throws PhaseHandlingException {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new PhaseHandlingException(
                        "Error occurs when closing the prepared statement.", e);
            }
        }
    }

    /**
     * Close the result set.
     * @param rs the result set to close
     * @throws PersistenceException error occurs when closing the result set.
     */
    protected void closeResultSet(ResultSet rs) throws PhaseHandlingException {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new PhaseHandlingException(
                        "Error occurs when closing the result set.", e);
            }
        }
    }

}
