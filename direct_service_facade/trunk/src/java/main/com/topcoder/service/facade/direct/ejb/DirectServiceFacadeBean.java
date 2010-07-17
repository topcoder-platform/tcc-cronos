/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct.ejb;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.CommonProjectContestData;
import com.topcoder.service.facade.contest.ContestServiceException;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.facade.direct.ContestNotFoundException;
import com.topcoder.service.facade.direct.ContestPlan;
import com.topcoder.service.facade.direct.ContestPrize;
import com.topcoder.service.facade.direct.ContestReceiptData;
import com.topcoder.service.facade.direct.ContestSchedule;
import com.topcoder.service.facade.direct.ContestScheduleExtension;
import com.topcoder.service.facade.direct.DirectServiceFacadeConfigurationException;
import com.topcoder.service.facade.direct.DirectServiceFacadeException;
import com.topcoder.service.facade.direct.EmailSendingException;
import com.topcoder.service.facade.direct.ProjectBudget;
import com.topcoder.service.facade.direct.ProjectNotFoundException;
import com.topcoder.service.facade.direct.SpecReviewState;
import com.topcoder.service.facade.direct.SpecReviewStatus;
import com.topcoder.service.project.Competition;
import com.topcoder.service.project.CompetitionPrize;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.ContestMultiRoundInformationData;
import com.topcoder.service.studio.ContestPaymentData;
import com.topcoder.service.studio.ContestStatusData;
import com.topcoder.service.studio.MilestonePrizeData;
import com.topcoder.service.user.User;
import com.topcoder.service.user.UserService;
import com.topcoder.service.user.UserServiceException;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.Template;
import com.topcoder.util.file.TemplateDataFormatException;
import com.topcoder.util.file.TemplateFormatException;
import com.topcoder.util.file.fieldconfig.Field;
import com.topcoder.util.file.fieldconfig.Node;
import com.topcoder.util.file.fieldconfig.TemplateFields;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.dao.ProjectDAO;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Company;
import com.topcoder.management.phase.PhaseManagementException;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.project.ContestSale;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.project.ProjectPropertyType;
import com.topcoder.management.project.ProjectSpec;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.link.ProjectLink;
import com.topcoder.management.project.link.ProjectLinkManager;
import com.topcoder.management.project.link.ProjectLinkType;
import com.topcoder.message.email.AddressException;
import com.topcoder.message.email.EmailEngine;
import com.topcoder.message.email.SendingException;
import com.topcoder.message.email.TCSEmailMessage;

/**
 * <p>
 * This class is an EJB that implements DirectServiceFacade business interface. This bean uses Logging Wrapper
 * component to log exceptions and debug information and Email Engine component to send email messages. This class
 * uses pluggable implementations of ContestServiceFacade, UserService, ProjectDAO, ProjectManager, ProjectLinkManager
 * and PhaseManager interfaces to access data in persistence.
 * </p>
 *
 * <p>
 *
 * <strong>Sample ejb-jar.xml</strong>
 *
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;
 * &lt;ejb-jar xmlns="http://java.sun.com/xml/ns/javaee"
 *          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 *          xsi:schemaLocation=
 *     "http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/ejb-jar_3_0.xsd"
 *          version="3.0"&gt;
 *     &lt;description&gt;Direct Service Facade EJB&lt;/description&gt;
 *     &lt;display-name&gt;Direct Service Facade EJB&lt;/display-name&gt;
 *     &lt;enterprise-beans&gt;
 *         &lt;session&gt;
 *             &lt;ejb-name&gt;DirectServiceFacadeBean&lt;/ejb-name&gt;
 *             &lt;remote&gt;
 *               com.topcoder.service.facade.direct.ejb.DirectServiceFacadeRemote
 *             &lt;/remote&gt;
 *             &lt;local&gt;
 *               com.topcoder.service.facade.direct.ejb.DirectServiceFacadeLocal
 *             &lt;/local&gt;
 *             &lt;ejb-class&gt;
 *               com.topcoder.service.facade.direct.ejb.DirectServiceFacadeBean
 *             &lt;/ejb-class&gt;
 *             &lt;session-type&gt;Stateless&lt;/session-type&gt;
 *             &lt;transaction-type&gt;Container&lt;/transaction-type&gt;
 *             &lt;env-entry&gt;
 *                 &lt;env-entry-name&gt;loggerName&lt;/env-entry-name&gt;
 *                 &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                 &lt;env-entry-value&gt;direct_service_facade_log&lt;/env-entry-value&gt;
 *             &lt;/env-entry&gt;
 *             &lt;env-entry&gt;
 *                 &lt;env-entry-name&gt;projectManagerClassName&lt;/env-entry-name&gt;
 *                 &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                 &lt;env-entry-value&gt;
 *                   com.topcoder.management.project.ProjectManagerImpl
 *                 &lt;/env-entry-value&gt;
 *             &lt;/env-entry&gt;
 *             &lt;env-entry&gt;
 *                 &lt;env-entry-name&gt;projectLinkManagerClassName&lt;/env-entry-name&gt;
 *                 &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                 &lt;env-entry-value&gt;
 *                   com.topcoder.management.project.link.ProjectLinkManagerImpl
 *                 &lt;/env-entry-value&gt;
 *             &lt;/env-entry&gt;
 *             &lt;env-entry&gt;
 *                 &lt;env-entry-name&gt;phaseManagerClassName&lt;/env-entry-name&gt;
 *                 &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                 &lt;env-entry-value&gt;
 *                   com.topcoder.management.phase.DefaultPhaseManager
 *                 &lt;/env-entry-value&gt;
 *             &lt;/env-entry&gt;
 *             &lt;env-entry&gt;
 *                 &lt;env-entry-name&gt;receiptEmailTitleTemplateText&lt;/env-entry-name&gt;
 *                 &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                 &lt;env-entry-value&gt;Receipt for contest "%contestName%"&lt;/env-entry-value&gt;
 *             &lt;/env-entry&gt;
 *             &lt;env-entry&gt;
 *                 &lt;env-entry-name&gt;receiptEmailBodyTemplateText&lt;/env-entry-name&gt;
 *                 &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                 &lt;env-entry-value&gt;Project Name: %projectName%
 * Contest Fee: %contestFee%
 * Start Date: %startDate%
 * Prizes: %prizes%
 * Total Cost: %contestTotalCost%
 * See details here: http://topcoder.com/tc?module=ContestDetails&id=%contestId%
 *                 &lt;/env-entry-value&gt;
 *             &lt;/env-entry&gt;
 *             &lt;env-entry&gt;
 *                 &lt;env-entry-name&gt;budgetUpdateEmailTitleTemplateText&lt;/env-entry-name&gt;
 *                 &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                 &lt;env-entry-value&gt;Budget Updated&lt;/env-entry-value&gt;
 *             &lt;/env-entry&gt;
 *             &lt;env-entry&gt;
 *                 &lt;env-entry-name&gt;budgetUpdateEmailBodyTemplateText&lt;/env-entry-name&gt;
 *                 &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                 &lt;env-entry-value&gt;Project Name: %billingProjectName%
 * Old Budget Amount: %oldBudget%
 * New Budget Amount: %newBudget%
 * &lt;/env-entry-value&gt;
 *             &lt;/env-entry&gt;
 *             &lt;env-entry&gt;
 *                 &lt;env-entry-name&gt;emailSender&lt;/env-entry-name&gt;
 *                 &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                 &lt;env-entry-value&gt;service@topcoder.com&lt;/env-entry-value&gt;
 *             &lt;/env-entry&gt;
 *             &lt;ejb-ref&gt;
 *                 &lt;ejb-ref-name&gt;ejb/ContestServiceFacade&lt;/ejb-ref-name&gt;
 *                 &lt;ejb-ref-type&gt;Session&lt;/ejb-ref-type&gt;
 *                 &lt;remote&gt;
 *                   com.topcoder.service.facade.contest.ejb.ContestServiceFacadeRemote
 *                 &lt;/remote&gt;
 *             &lt;/ejb-ref&gt;
 *             &lt;ejb-ref&gt;
 *                 &lt;ejb-ref-name&gt;ejb/UserService&lt;/ejb-ref-name&gt;
 *                 &lt;ejb-ref-type&gt;Session&lt;/ejb-ref-type&gt;
 *                 &lt;remote&gt;com.topcoder.service.user.UserServiceRemote&lt;/remote&gt;
 *             &lt;/ejb-ref&gt;
 *             &lt;ejb-ref&gt;
 *                 &lt;ejb-ref-name&gt;ejb/ProjectDAO&lt;/ejb-ref-name&gt;
 *                 &lt;ejb-ref-type&gt;Session&lt;/ejb-ref-type&gt;
 *                 &lt;remote&gt;com.topcoder.clients.dao.ejb3.ProjectDAORemote&lt;/remote&gt;
 *             &lt;/ejb-ref&gt;
 *         &lt;/session&gt;
 *     &lt;/enterprise-beans&gt;
 * &lt;/ejb-jar&gt;
 * </pre>
 *
 * <strong>Sample API usage:</strong>
 *
 * <pre>
 * // Get direct service facade
 * DirectServiceFacade directServiceFacade = TestHelper.geDirectServiceFacadeBean();
 *
 * TCSubject tcSubject = new TCSubject(1);
 *
 * // Retrieve the contest receipt data for studio contest with ID=1001
 * directServiceFacade.getContestReceiptData(tcSubject, 1, true);
 *
 * // Send contest receipt by email for this contest
 * String[] additionalEmailAddrs = new String[] {&quot;user@mail.com&quot;};
 * directServiceFacade.sendContestReceiptByEmail(tcSubject, 1, true, additionalEmailAddrs);
 *
 * // Retrieve the contest prize for software contest with ID=2001
 * ContestPrize contestPrize = directServiceFacade.getContestPrize(tcSubject, 1, true);
 *
 * // Update first place prize for this contest
 * double contestPrizes[] = contestPrize.getContestPrizes();
 * contestPrizes[0] += 100;
 * contestPrize.setContestPrizes(contestPrizes);
 * directServiceFacade.updateActiveContestPrize(tcSubject, contestPrize);
 *
 * // Retrieve the contest schedule
 * directServiceFacade.getContestSchedule(tcSubject, 2001, false);
 *
 * // Extend registration phase by 24 hours
 * ContestScheduleExtension extension = new ContestScheduleExtension();
 * extension.setContestId(1);
 * extension.setExtendMilestoneBy(1);
 * extension.setExtendRegistrationBy(24);
 * extension.setExtendSubmissionBy(3);
 * directServiceFacade.extendActiveContestSchedule(tcSubject, extension);
 *
 * // Repost software contest with ID=2001
 * directServiceFacade.repostSoftwareContest(tcSubject, 2001);
 *
 * // Create new version for design contest with ID=2002
 * directServiceFacade.createNewVersionForDesignDevContest(tcSubject, 2002);
 *
 * // Delete software contest with ID=2002
 * directServiceFacade.deleteContest(tcSubject, 2002, false);
 *
 * // Retrieve the project game plan for project with ID=1
 * directServiceFacade.getProjectGamePlan(tcSubject, 1);
 *
 * // Retrieve the parent projects for project with ID=2001
 * directServiceFacade.getParentProjects(tcSubject, 2001);
 *
 * // Retrieve the child projects for project with ID=2001
 * directServiceFacade.getChildProjects(tcSubject, 2001);
 *
 * // Update project links (remove all links)
 * long[] parentProjectIds = new long[0];
 * long[] childProjectIds = new long[0];
 * directServiceFacade.updateProjectLinks(tcSubject, 2001, parentProjectIds, childProjectIds);
 *
 * // Retrieve the project budget for billing project with ID=1
 * directServiceFacade.getProjectBudget(tcSubject, 1);
 *
 * // Update project budget (increase by 1000)
 * directServiceFacade.updateProjectBudget(tcSubject, 1, 1000);
 *
 * // Retrieve the spec review state for contest with ID=2001
 * directServiceFacade.getSpecReviewState(tcSubject, 2001);
 * </pre>
 *
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable and not thread safe. But it is always used in thread safe
 * manner in EJB container because its state doesn't change after initialization. Instances of ContestServiceFacade,
 * UserService and ProjectDAO used by this class are thread safe. Instances of ProjectManager, ProjectLinkManager and
 * PhaseManager are not thread safe, thus all call of their methods are synchronized in this class. This bean assumes
 * that transactions are managed by the container.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
@Stateless
@WebService(endpointInterface = "com.topcoder.service.facade.contest.ContestServiceFacade")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DirectServiceFacadeBean implements DirectServiceFacadeLocal, DirectServiceFacadeRemote {

    /**
     * <p>
     * The message format pattern used to log the entrance of method.
     * </p>
     */
    private static final String ENTRANCE_METHOD_PATTERN = "Entering method {0}.\n\tMethod arguments: [{1}].";

    /**
     * <p>
     * The message format pattern used to log the exit of method.
     * </p>
     */
    private static final String EXIT_METHOD_PATTERN = "Exiting method {0}.\n\tReturn value: [{1}].\n\t"
            + "Duration time: {2} milliseconds.";

    /**
     * <p>
     * The message format pattern used to log the exception.
     * </p>
     */
    private static final String EXCEPTION_PATTERN = "Error in method {0}.\n\tException message: {1}.";

    /**
     * <p>
     * The contest service facade used by this class. Is initialized via EJB container injection. Cannot be null after
     * initialization. Is used in getContestReceiptData(), updateActiveContestPrize(), getContestPrize(),
     * getContestSchedule(), extendActiveContestSchedule(), repostSoftwareContest(),
     * createNewVersionForDesignDevContest(), deleteContest() and getProjectGamePlan().
     * </p>
     */
    @EJB(name = "ejb/ContestServiceFacade")
    private ContestServiceFacade contestServiceFacade;

    /**
     * <p>
     * The user service used by this class. Is initialized via EJB container injection. Cannot be null after
     * initialization. Is used in getContestReceiptData().
     * </p>
     */
    @EJB(name = "ejb/UserService")
    private UserService userService;

    /**
     * <p>
     * The project DAO used by this class. Is initialized via EJB container injection. Cannot be null after
     * initialization. Is used in getContestReceiptData(), getProjectBudget() and updateProjectBudget().
     * </p>
     */
    @EJB(name = "ejb/ProjectDAO")
    private ProjectDAO projectDAO;

    /**
     * <p>
     * The name of the Logging Wrapper logger to be used by this class for logging errors and debug information. Is
     * null if logging is not required. Can be modified with EJB container injection. Is used in initialize().
     * </p>
     */
    @Resource(name = "loggerName")
    private String loggerName;

    /**
     * <p>
     * The full class name of project manager to be used by this class. Is initialized via EJB container injection.
     * Cannot be null or empty after initialization. Is used in initialize().
     * </p>
     */
    @Resource(name = "projectManagerClassName")
    private String projectManagerClassName;

    /**
     * <p>
     * The namespace of project manager to be used by this class. Is initialized via EJB container injection. If null,
     * the default constructor is used, otherwise this namespace is passed as the only string argument to the
     * constructor. Is used in initialize().
     * </p>
     */
    @Resource(name = "projectManagerNamespace")
    private String projectManagerNamespace;

    /**
     * <p>
     * The full class name of project link manager to be used by this class. Is initialized via EJB container
     * injection. Cannot be null or empty after initialization. Is used in initialize().
     * </p>
     */
    @Resource(name = "projectLinkManagerClassName")
    private String projectLinkManagerClassName;

    /**
     * <p>
     * The namespace of project link manager to be used by this class. Is initialized via EJB container injection. If
     * null, the default constructor is used, otherwise this namespace is passed as the only string argument to the
     * constructor. Is used in initialize().
     * </p>
     */
    @Resource(name = "projectLinkManagerNamespace")
    private String projectLinkManagerNamespace;

    /**
     * <p>
     * The full class name of phase manager to be used by this class. Is initialized via EJB container injection.
     * Cannot be null or empty after initialization. Is used in initialize().
     * </p>
     */
    @Resource(name = "phaseManagerClassName")
    private String phaseManagerClassName;

    /**
     * <p>
     * The namespace of phase manager to be used by this class. Is initialized via EJB container injection. If null,
     * the default constructor is used, otherwise this namespace is passed as the only string argument to the
     * constructor. Is used in initialize().
     * </p>
     */
    @Resource(name = "phaseManagerNamespace")
    private String phaseManagerNamespace;

    /**
     * <p>
     * The template text of receipt email title. Is initialized via EJB container injection. Cannot be null after
     * initialization. Is used in sendContestReceiptByEmail().
     * </p>
     */
    @Resource(name = "receiptEmailTitleTemplateText")
    private String receiptEmailTitleTemplateText;

    /**
     * <p>
     * The template text of receipt email body. Is initialized via EJB container injection. Cannot be null after
     * initialization. Is used in sendContestReceiptByEmail().
     * </p>
     */
    @Resource(name = "receiptEmailBodyTemplateText")
    private String receiptEmailBodyTemplateText;

    /**
     * <p>
     * The template text of budget update notification email title. Is initialized via EJB container injection. Cannot
     * be null after initialization. Is used in updateProjectBudget().
     * </p>
     */
    @Resource(name = "budgetUpdateEmailTitleTemplateText")
    private String budgetUpdateEmailTitleTemplateText;

    /**
     * <p>
     * The template text of budget update notification email body. Is initialized via EJB container injection. Cannot
     * be null after initialization. Is used in updateProjectBudget().
     * </p>
     */
    @Resource(name = "budgetUpdateEmailBodyTemplateText")
    private String budgetUpdateEmailBodyTemplateText;

    /**
     * <p>
     * The address of the email sender. Is initialized via EJB container injection. Cannot be null or empty after
     * initialization. Is used in sendEmail().
     * </p>
     */
    @Resource(name = "emailSender")
    private String emailSender;

    /**
     * <p>
     * The logger instance to be used by this class. Can be set in initialize(). Is null if logging is not required.
     * Is used in all public methods.
     * </p>
     */
    private Log log;

    /**
     * <p>
     * The project manager used by this class. Is initialized in initialize(). Cannot be null after initialization. Is
     * used in getContestReceiptData() and getSpecReviewState().
     * </p>
     *
     * <p>
     * Implementations of ProjectManager are not required to be thread safe. Thus to make DirectServiceFacadeBean
     * thread safe, all calls to projectManager's methods must be synchronized on "this" instance.
     * </p>
     */
    private ProjectManager projectManager;

    /**
     * <p>
     * The project link manager used by this class. Is initialized in initialize(). Cannot be null after
     * initialization. Is used in getParentProjects(), getChildProjects() and updateProjectLinks().
     * </p>
     *
     * <p>
     * Implementations of ProjectLinkManager are not required to be thread safe. Thus to make DirectServiceFacadeBean
     * thread safe, all calls to projectLinkManager's methods must be synchronized on "this" instance.
     * </p>
     */
    private ProjectLinkManager projectLinkManager;

    /**
     * <p>
     * The phase manager used by this class. Is initialized in initialize(). Cannot be null after initialization. Is
     * used in getSpecReviewState().
     * </p>
     *
     * <p>
     * Implementations of PhaseManager are not required to be thread safe. Thus to make DirectServiceFacadeBean thread
     * safe, all calls to phaseManager's methods must be synchronized on "this" instance.
     * </p>
     */
    private PhaseManager phaseManager;

    /**
     * <p>
     * Creates an instance of DirectServiceFacadeBean.
     * </p>
     */
    public DirectServiceFacadeBean() {
        // Do nothing.
    }

    /**
     * <p>
     * Initializes this bean.
     * </p>
     *
     * @throws DirectServiceFacadeConfigurationException
     *             if error occurred when initializing this bean
     */
    @PostConstruct
    protected void initialize() {
        validateParameter(loggerName, "loggerName", false, false);
        validateParameter(projectManagerClassName, "projectManagerClassName", true, false);
        validateParameter(projectManagerNamespace, "projectManagerNamespace", false, true);
        validateParameter(projectLinkManagerClassName, "projectLinkManagerClassName", true, false);
        validateParameter(projectLinkManagerNamespace, "projectLinkManagerNamespace", false, true);
        validateParameter(phaseManagerClassName, "phaseManagerClassName", true, false);
        validateParameter(phaseManagerNamespace, "phaseManagerNamespace", false, true);
        validateParameter(receiptEmailTitleTemplateText, "receiptEmailTitleTemplateText", true, true);
        validateParameter(receiptEmailBodyTemplateText, "receiptEmailBodyTemplateText", true, true);
        validateParameter(budgetUpdateEmailTitleTemplateText, "budgetUpdateEmailTitleTemplateText", true, true);
        validateParameter(budgetUpdateEmailBodyTemplateText, "budgetUpdateEmailBodyTemplateText", true, true);
        validateParameter(emailSender, "emailSender", true, false);

        if (loggerName != null) {
            log = LogManager.getLog(loggerName);
        }

        // Create projectManager using projectManagerClassName and projectManagerNamespace.
        projectManager = (ProjectManager) createInstance(ProjectManager.class, projectManagerClassName,
                projectManagerNamespace);
        // 5. Similarly create projectLinkManager:ProjectLinkManager using projectLinkManagerClassName and
        // projectLinkManagerNamespace.
        projectLinkManager = (ProjectLinkManager) createInstance(ProjectLinkManager.class,
                projectLinkManagerClassName, projectLinkManagerNamespace);

        // 6. Similarly create phaseManager:PhaseManager using phaseManagerClassName and phaseManagerNamespace.
        phaseManager = (PhaseManager) createInstance(PhaseManager.class, phaseManagerClassName, phaseManagerNamespace);
    }

    /**
     * <p>
     * Retrieves the contest receipt data.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param contestId
     *            the ID of the contest
     * @param isStudio
     *            true for a studio contest, false for a software contest
     * @return the retrieved contest receipt data (not null)
     * @throws IllegalArgumentException
     *             if tcSubject is null or contestId <= 0
     * @throws IllegalStateException
     *             if this bean was not properly initialized (any bean or resource to be used was not properly
     *             injected)
     * @throws ContestNotFoundException
     *             if studio/software contest with the given ID cannot be found
     * @throws DirectServiceFacadeException
     *             if some other error occurred
     */
    public ContestReceiptData getContestReceiptData(TCSubject tcSubject, long contestId, boolean isStudio)
            throws DirectServiceFacadeException {
        long timestamp = System.currentTimeMillis();
        String methodName = "ContestReceiptData getContestReceiptData("
                + "TCSubject tcSubject, long contestId, boolean isStudio)";
        String methodArguments = "tcSubject=" + tcSubject + ", contestId=" + contestId + ", isStudio=" + isStudio;
        logEntrance(log, methodName, methodArguments);

        checkNull(log, methodName, tcSubject, "tcSubject");
        checkId(log, methodName, contestId, "contestId");
        checkState(log, methodName);

        Competition competition = null;
        String contestName = null;
        String contestType = null;
        String status = null;
        XMLGregorianCalendar milestoneDate = null;
        String referenceNo = null;
        long billingProjectId = 0;

        if (isStudio) {
            StudioCompetition studioCompetition = getStudioCompetition(tcSubject, contestId, methodName);
            competition = studioCompetition;
            ContestData contestData = studioCompetition.getContestData();
            contestName = contestData.getName();
            contestType = studioCompetition.getType().toString() + " - " + studioCompetition.getCategory();
            milestoneDate = contestData.getMultiRoundData().getMilestoneDate();
            ContestStatusData contestStatusData = studioCompetition.getStatus();
            // skip if contestStatusData is null
            if (contestStatusData != null) {
                status = contestStatusData.getName();
            }
            billingProjectId = contestData.getBillingProject();
            List<ContestPaymentData> payments = contestData.getPayments();
            if (payments.size() > 0) {
                referenceNo = payments.get(0).getPaymentReferenceId();
            }
        } else {
            SoftwareCompetition softwareCompetition = getSoftwareCompetition(tcSubject, contestId, methodName);
            competition = softwareCompetition;
            com.topcoder.management.project.Project project = softwareCompetition.getProjectHeader();
            if (project != null) {
                // skip this step if project is null
                contestName = project.getTcDirectProjectName();
            }
            contestType = softwareCompetition.getType().toString();
            status = softwareCompetition.getStatus().toString();

            try {
                billingProjectId = Long.parseLong(project
                        .getProperty(ProjectPropertyType.BILLING_PROJECT_PROJECT_PROPERTY_KEY));
            } catch (NumberFormatException e) {
                logException(log, methodName, e);
                throw new DirectServiceFacadeException(
                        "NumberFormatException occurs while getting billing project id.", e);
            }

            try {
                List<ContestSale> contestSales = projectManager.getContestSales(project.getId());
                if (contestSales.size() > 0) {
                    referenceNo = contestSales.get(0).getSaleReferenceId();
                }
            } catch (PersistenceException e) {
                logException(log, methodName, e);
                throw new DirectServiceFacadeException("PersistenceException occurs while getting contest sales.", e);
            }
        }

        ContestReceiptData contestReceiptData = getContestReceiptData(contestId, isStudio, competition, contestName,
                contestType, status, milestoneDate, referenceNo, billingProjectId);
        logExit(log, methodName, contestReceiptData, System.currentTimeMillis() - timestamp);
        return contestReceiptData;
    }

    /**
     * <p>
     * Sends contest receipt by email to contest creator and the specified email addresses.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param contestId
     *            the ID of the contest
     * @param isStudio
     *            true for a studio contest, false - for a software contest
     * @param additionalEmailAddrs
     *            the additional email addresses
     * @return the retrieved contest receipt data (not null)
     * @throws IllegalArgumentException
     *             if tcSubject is null, contestId <= 0 or additionalEmailAddrs contains null/empty element
     * @throws IllegalStateException
     *             if this bean was not properly initialized (any bean or resource to be used was not properly
     *             injected)
     * @throws ContestNotFoundException
     *             if studio/software contest with the given ID cannot be found
     * @throws EmailSendingException
     *             if some error occurred when sending an email message
     * @throws DirectServiceFacadeException
     *             if some other error occurred
     */
    public ContestReceiptData sendContestReceiptByEmail(TCSubject tcSubject, long contestId, boolean isStudio,
            String[] additionalEmailAddrs) throws DirectServiceFacadeException {
        long timestamp = System.currentTimeMillis();
        String methodName = "ContestReceiptData sendContestReceiptByEmail(TCSubject tcSubject, long contestId,"
                + " boolean isStudio, String[] additionalEmailAddrs)";
        String methodArguments = "tcSubject=" + tcSubject + ", contestId=" + contestId + ", isStudio=" + isStudio
                + ", additionalEmailAddrs=" + additionalEmailAddrs;
        logEntrance(log, methodName, methodArguments);

        checkNull(log, methodName, tcSubject, "tcSubject");
        checkId(log, methodName, contestId, "contestId");
        checkState(log, methodName);

        // check if additionalEmailAddrs contains null/empty element
        if (additionalEmailAddrs != null) {
            for (String additionalEmailAddr : additionalEmailAddrs) {
                checkNullOrEmpty(log, methodName, additionalEmailAddr, "element in additionalEmailAddrs");
            }
        }

        ContestReceiptData contestReceiptData = getContestReceiptData(tcSubject, contestId, isStudio);
        List<String> recipients = new ArrayList<String>();

        String email = contestReceiptData.getEmail();
        if (email != null) {
            recipients.add(email);
        }

        if (additionalEmailAddrs != null) {
            for (String additionalEmailAddress : additionalEmailAddrs) {
                recipients.add(additionalEmailAddress);
            }
        }
        Map<String, String> params = new HashMap<String, String>();
        // Repeat for all ContestReceiptData properties.
        params.put("contestId", Long.toString(contestReceiptData.getContestId()));
        params.put("studio", Boolean.toString(contestReceiptData.isStudio()));
        params.put("projectName", contestReceiptData.getProjectName());
        params.put("contestName", contestReceiptData.getContestName());
        params.put("contestFee", "" + contestReceiptData.getContestFee());
        params.put("startDate", formatDate(contestReceiptData.getStartDate()));
        params.put("contestType", contestReceiptData.getContestType());
        double[] prizes = contestReceiptData.getPrizes();
        String prizesString = "";
        StringBuffer sb = new StringBuffer();
        for (double prize : prizes) {
            sb.append(", " + prize);
        }
        if (sb.length() > 2) {
            prizesString = sb.substring(2);
        }
        params.put("prizes", prizesString);
        params.put("milestoneDate", formatDate(contestReceiptData.getMilestoneDate()));
        params.put("status", contestReceiptData.getStatus());
        params.put("contestTotalCost", "" + contestReceiptData.getContestTotalCost());
        params.put("endDate", formatDate(contestReceiptData.getEndDate()));
        params.put("companyName", contestReceiptData.getCompanyName());
        params.put("address", contestReceiptData.getAddress());
        params.put("city", contestReceiptData.getCity());
        params.put("state", contestReceiptData.getState());
        params.put("zipCode", contestReceiptData.getZipCode());
        params.put("country", contestReceiptData.getCountry());
        params.put("phone", contestReceiptData.getPhone());
        params.put("email", contestReceiptData.getEmail());
        params.put("purchaseOrderNo", contestReceiptData.getPurchaseOrderNo());
        params.put("invoiceTerms", contestReceiptData.getInvoiceTerms());
        params.put("referenceNo", contestReceiptData.getReferenceNo());

        sendEmail(receiptEmailTitleTemplateText, receiptEmailBodyTemplateText, recipients, params, methodName);

        // Return contestReceiptData.
        logExit(log, methodName, contestReceiptData, System.currentTimeMillis() - timestamp);
        return contestReceiptData;
    }

    /**
     * <p>
     * Updates the given active contest prize data in the persistence.
     * </p>
     *
     * <p>
     * Annotation: @TransactionAttribute(TransactionAttributeType.REQUIRED).
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param contestPrize
     *            the updated contest prize data
     * @throws IllegalArgumentException
     *             if tcSubject or contestPrize is null, contestPrize.getContestId() <= 0,
     *             contestPrize.getContestPrizes() is null/empty, contestPrize.getContestPrizes() or
     *             contestPrize.getMilestonePrizes() contains negative element, second place prize is too low
     * @throws IllegalStateException
     *             if this bean was not properly initialized (any bean or resource to be used was not properly
     *             injected)
     * @throws ContestNotFoundException
     *             if studio/software contest with the ID specified in ContestPrize cannot be found
     * @throws DirectServiceFacadeException
     *             if some other error occurred
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateActiveContestPrize(TCSubject tcSubject, ContestPrize contestPrize)
            throws DirectServiceFacadeException {
        long timestamp = System.currentTimeMillis();
        String methodName = "void updateActiveContestPrize(TCSubject tcSubject, ContestPrize contestPrize)";
        String methodArguments = "tcSubject=" + tcSubject + ", contestPrize=" + contestPrize;
        logEntrance(log, methodName, methodArguments);

        checkNull(log, methodName, tcSubject, "tcSubject");
        checkNull(log, methodName, contestPrize, "contestPrize");
        checkState(log, methodName, contestServiceFacade, "contestServiceFacade");

        // check if contestPrize.getContestPrizes() is null/empty, or contains negative element,
        // or second place prize is too low
        double[] contestPrizes = contestPrize.getContestPrizes();
        if (contestPrizes == null || contestPrizes.length == 0) {
            IllegalArgumentException e = new IllegalArgumentException(
                    "contestPrize.getContestPrizes() should not be null or empty.");
            logException(log, methodName, e);
            throw e;
        }
        for (double prize : contestPrizes) {
            checkNegative(log, methodName, prize, "element in contestPrizes");
        }
        if (contestPrizes.length >= 2) {
            if (contestPrizes[1] < contestPrizes[0] * 0.2) {
                IllegalArgumentException e = new IllegalArgumentException("second place prize is too low.");
                logException(log, methodName, e);
                throw e;
            }
        }

        long contestId = contestPrize.getContestId();
        checkId(log, methodName, contestId, "contestId");

        boolean studio = contestPrize.isStudio();
        if (studio) {
            // check if contestPrize.getMilestonePrizes() contains negative element
            double[] milestonePrizes = contestPrize.getMilestonePrizes();
            if (milestonePrizes != null) {
                for (double milestonePrize : milestonePrizes) {
                    checkNegative(log, methodName, milestonePrize, "element in milestonePrizes");
                }
            }

            StudioCompetition contest = getStudioCompetition(tcSubject, contestId, methodName);
            List<CompetitionPrize> prizes = new ArrayList<CompetitionPrize>();
            for (int i = 0; i < contestPrizes.length; i++) {
                CompetitionPrize competitionPrize = new CompetitionPrize();
                competitionPrize.setPlace(i + 1);
                competitionPrize.setAmount(contestPrizes[i]);
            }
            contest.setPrizes(prizes);

            ContestData contestData = contest.getContestData();
            MilestonePrizeData milestonePrizeData = contestData.getMilestonePrizeData();
            int milestonePrizeNum = (milestonePrizes == null) ? 0 : milestonePrizes.length;
            milestonePrizeData.setNumberOfSubmissions(milestonePrizeNum);
            double milestonePrize = (milestonePrizeNum == 0) ? 0 : milestonePrizes[0];
            milestonePrizeData.setAmount(milestonePrize);

            // update contest
            updateContest(tcSubject, contest, methodName);
        } else {
            SoftwareCompetition contest = getSoftwareCompetition(tcSubject, contestId, methodName);
            List<CompetitionPrize> prizes = new ArrayList<CompetitionPrize>();

            for (int i = 0; i < contestPrizes.length; i++) {
                CompetitionPrize competitionPrize = new CompetitionPrize();
                competitionPrize.setPlace(i + 1);
                competitionPrize.setAmount(contestPrizes[i]);
            }
            contest.setPrizes(prizes);
            com.topcoder.management.project.Project project = contest.getProjectHeader();
            updateSoftwareContest(tcSubject, contest, project.getTcDirectProjectId(), methodName);
        }

        logExit(log, methodName, "void", System.currentTimeMillis() - timestamp);
    }

    /**
     * <p>
     * Retrieves the contest prize for contest with the given ID.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param contestId
     *            the ID of the contest
     * @param studio
     *            true for a studio contest, false - for a software contest
     * @return the retrieved contest prize data (not null)
     * @throws IllegalArgumentException
     *             if tcSubject is null or contestId <= 0
     * @throws IllegalStateException
     *             if this bean was not properly initialized (any bean or resource to be used was not properly
     *             injected)
     * @throws ContestNotFoundException
     *             if studio/software contest with the given ID cannot be found
     * @throws DirectServiceFacadeException
     *             if some other error occurred
     */
    public ContestPrize getContestPrize(TCSubject tcSubject, long contestId, boolean studio)
            throws DirectServiceFacadeException {
        long timestamp = System.currentTimeMillis();
        String methodName = "ContestPrize getContestPrize(TCSubject tcSubject, long contestId, boolean studio)";
        String methodArguments = "tcSubject=" + tcSubject + ", contestId=" + contestId + ", studio=" + studio;
        logEntrance(log, methodName, methodArguments);

        checkNull(log, methodName, tcSubject, "tcSubject");
        checkId(log, methodName, contestId, "contestId");
        checkState(log, methodName);

        ContestPrize result = new ContestPrize();
        result.setContestId(contestId);
        result.setStudio(studio);

        if (studio) {
            StudioCompetition contest = getStudioCompetition(tcSubject, contestId, methodName);

            // set contest prizes
            List<CompetitionPrize> competitionPrizes = contest.getPrizes();
            result.setContestPrizes(getContestPrizes(competitionPrizes));

            // set milestone prizes
            ContestData contestData = contest.getContestData();
            MilestonePrizeData milestonePrizeData = contestData.getMilestonePrizeData();
            Double amount = milestonePrizeData.getAmount();
            double[] milestonePrizes = new double[0];
            if (amount != null) {
                int numberOfSubmissions = milestonePrizeData.getNumberOfSubmissions();
                milestonePrizes = new double[numberOfSubmissions];
                Arrays.fill(milestonePrizes, amount);
            }
            result.setMilestonePrizes(milestonePrizes);
        } else {
            SoftwareCompetition contest = getSoftwareCompetition(tcSubject, contestId, methodName);

            // set contest prizes
            List<CompetitionPrize> competitionPrizes = contest.getPrizes();
            result.setContestPrizes(getContestPrizes(competitionPrizes));
        }

        logExit(log, methodName, result, System.currentTimeMillis() - timestamp);
        return result;
    }

    /**
     * <p>
     * Retrieves the contest schedule.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param contestId
     *            the ID of the contest
     * @param studio
     *            true for a studio contest, false - for a software contest
     * @return the retrieved contest schedule (not null)
     * @throws IllegalArgumentException
     *             if tcSubject is null or contestId <= 0
     * @throws IllegalStateException
     *             if this bean was not properly initialized (any bean or resource to be used was not properly
     *             injected)
     * @throws ContestNotFoundException
     *             if studio/software contest with the given ID cannot be found
     * @throws DirectServiceFacadeException
     *             if some other error occurred
     */
    public ContestSchedule getContestSchedule(TCSubject tcSubject, long contestId, boolean studio)
            throws DirectServiceFacadeException {
        long timestamp = System.currentTimeMillis();
        String methodName = "ContestSchedule getContestSchedule(TCSubject tcSubject, long contestId, boolean studio)";
        String methodArguments = "tcSubject=" + tcSubject + ", contestId=" + contestId + ", studio=" + studio;
        logEntrance(log, methodName, methodArguments);

        checkNull(log, methodName, tcSubject, "tcSubject");
        checkId(log, methodName, contestId, "contestId");
        checkState(log, methodName);

        ContestSchedule result = new ContestSchedule();
        result.setContestId(contestId);
        result.setStudio(studio);

        if (studio) {
            StudioCompetition contest = getStudioCompetition(tcSubject, contestId, methodName);
            ContestData contestData = contest.getContestData();
            ContestMultiRoundInformationData multiRoundData = contestData.getMultiRoundData();

            if (multiRoundData != null) {
                result.setMilestoneDeadline(multiRoundData.getMilestoneDate());
                long multiRoundDataMillis = multiRoundData.getMilestoneDate().toGregorianCalendar().getTimeInMillis();
                long launchDateAndTimeMillis = contestData.getLaunchDateAndTime().toGregorianCalendar()
                        .getTimeInMillis();
                int duration = (int) ((multiRoundDataMillis - launchDateAndTimeMillis) / 3600000);
                result.setMilestoneDuration(duration);
            }

            int duration = (int) contestData.getDurationInHours();
            Date deadline = new Date(contestData.getLaunchDateAndTime().toGregorianCalendar().getTimeInMillis()
                    + duration * 3600000);
            result.setSubmissionDeadline(getXMLGregorianCalendar(deadline));
            result.setSubmissionDuration(duration);
            result.setRegistrationDeadline(result.getSubmissionDeadline());
            result.setRegistrationDuration(result.getSubmissionDuration());
        } else {
            SoftwareCompetition contest = getSoftwareCompetition(tcSubject, contestId, methodName);
            com.topcoder.project.phases.Project project = contest.getProjectPhases();
            Phase[] allPhases = project.getAllPhases();
            Phase registrationPhase = null;
            Phase submissionPhase = null;
            for (Phase phase : allPhases) {
                if (PhaseType.REGISTRATION.equals(phase.getPhaseType().getName())) {
                    registrationPhase = phase;
                    break;
                } else if (PhaseType.SUBMISSION.equals(phase.getPhaseType().getName())) {
                    submissionPhase = phase;
                    break;
                }
            }

            if (registrationPhase != null) {
                result.setRegistrationDeadline(getXMLGregorianCalendar(registrationPhase.getActualEndDate()));
                // milliseconds are converted to hours
                result.setRegistrationDuration((int) (registrationPhase.getLength() / 3600000));
            }
            if (submissionPhase != null) {
                result.setSubmissionDeadline(getXMLGregorianCalendar(submissionPhase.getActualEndDate()));
                // milliseconds are converted to hours
                result.setSubmissionDuration((int) (submissionPhase.getLength() / 3600000));
            }
        }

        // Return result.
        logExit(log, methodName, "void", System.currentTimeMillis() - timestamp);
        return result;
    }

    /**
     * <p>
     * Extends the active contest schedule.
     * </p>
     *
     * <p>
     * Annotation: @TransactionAttribute(TransactionAttributeType.REQUIRED).
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param extension
     *            the contest schedule extension data
     * @throws IllegalArgumentException
     *             if tcSubject or extension is null, extension.getContestId() <= 0,
     *             extension.getExtendRegistrationBy(), extension.getExtendMilestoneBy() or
     *             extension.getExtendSubmissionBy() is negative
     * @throws IllegalStateException
     *             if this bean was not properly initialized (any bean or resource to be used was not properly
     *             injected)
     * @throws ContestNotFoundException
     *             if studio/software contest with the ID specified in ContestScheduleExtension cannot be found
     * @throws DirectServiceFacadeException
     *             if some other error occurred
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void extendActiveContestSchedule(TCSubject tcSubject, ContestScheduleExtension extension)
            throws DirectServiceFacadeException {
        long timestamp = System.currentTimeMillis();
        String methodName = "void extendActiveContestSchedule(TCSubject tcSubject, ContestScheduleExtension extension)";
        String methodArguments = "tcSubject=" + tcSubject + ", extension=" + extension;
        logEntrance(log, methodName, methodArguments);

        checkNull(log, methodName, tcSubject, "tcSubject");
        checkNull(log, methodName, extension, "extension");
        checkState(log, methodName);

        long contestId = extension.getContestId();
        Integer extendMilestoneBy = extension.getExtendMilestoneBy();
        Integer extendSubmissionBy = extension.getExtendSubmissionBy();
        Integer extendRegistrationBy = extension.getExtendRegistrationBy();

        checkId(log, methodName, contestId, "extension.getContestId()");
        checkNegative(log, methodName, extendMilestoneBy, "extension.getExtendMilestoneBy()");
        checkNegative(log, methodName, extendRegistrationBy, "extension.getExtendRegistrationBy()");
        checkNegative(log, methodName, extendSubmissionBy, "extension.getExtendSubmissionBy()");

        boolean studio = extension.isStudio();

        if (studio) {
            if (extendRegistrationBy != null) {
                if (extendSubmissionBy != null && extendRegistrationBy != extendSubmissionBy) {
                    DirectServiceFacadeException e = new DirectServiceFacadeException(
                            "extendSubmissionBy != null && extendRegistrationBy != extendSubmissionBy");
                    logException(log, methodName, e);
                }
                extendSubmissionBy = extendRegistrationBy;
            }

            StudioCompetition contest = getStudioCompetition(tcSubject, contestId, methodName);
            ContestData contestData = contest.getContestData();
            ContestMultiRoundInformationData multiRoundData = contestData.getMultiRoundData();
            if (multiRoundData != null && extendMilestoneBy != null) {
                XMLGregorianCalendar milestoneDate = multiRoundData.getMilestoneDate();
                try {
                    milestoneDate.add(DatatypeFactory.newInstance().newDuration(extendMilestoneBy));
                } catch (DatatypeConfigurationException e) {
                    logException(log, methodName, e);
                    throw new DirectServiceFacadeException(
                            "DatatypeConfigurationException occurs while adding duration.", e);
                }

                if (milestoneDate.toGregorianCalendar().getTimeInMillis() < contestData.getLaunchDateAndTime()
                        .toGregorianCalendar().getTimeInMillis()) {
                    DirectServiceFacadeException e = new DirectServiceFacadeException(
                            "milestoneDate should not less than contestData.getLaunchDateAndTime().");
                    logException(log, methodName, e);
                    throw e;
                }
            }
            if (extendSubmissionBy != null) {
                double durationInHours = contestData.getDurationInHours();
                durationInHours += extendSubmissionBy;
                if (durationInHours <= 0) {
                    DirectServiceFacadeException e = new DirectServiceFacadeException(
                            "durationInHours should be positive.");
                    logException(log, methodName, e);
                    throw e;
                }
                contestData.setDurationInHours(durationInHours);
            }
            updateContest(tcSubject, contest, methodName);
        } else {
            SoftwareCompetition contest = getSoftwareCompetition(tcSubject, contestId, methodName);
            com.topcoder.project.phases.Project project = contest.getProjectPhases();
            Phase[] allPhases = project.getAllPhases();
            Phase registrationPhase = null;
            Phase submissionPhase = null;

            for (Phase phase : allPhases) {
                if (PhaseType.REGISTRATION.equals(phase.getPhaseType().getName())) {
                    registrationPhase = phase;
                    break;
                } else if (PhaseType.SUBMISSION.equals(phase.getPhaseType().getName())) {
                    submissionPhase = phase;
                    break;
                }
            }

            // set registrationPhase phase length
            setPhaseLength(registrationPhase, extendRegistrationBy, methodName);
            // set submissionPhase phase length
            setPhaseLength(submissionPhase, extendSubmissionBy, methodName);

            // calculate end date
            project.calcEndDate();

            if (registrationPhase != null && extendRegistrationBy != null) {
                if (new Date().after(registrationPhase.getActualEndDate())
                        && PhaseStatus.CLOSED == registrationPhase.getPhaseStatus()) {
                    registrationPhase.setPhaseStatus(PhaseStatus.OPEN);
                }
            }

            com.topcoder.management.project.Project projectHeader = contest.getProjectHeader();
            updateSoftwareContest(tcSubject, contest, projectHeader.getTcDirectProjectId(), methodName);
        }

        logExit(log, methodName, "void", System.currentTimeMillis() - timestamp);
    }

    /**
     * <p>
     * Reposts the software contest.
     * </p>
     *
     * <p>
     * Annotation: @TransactionAttribute(TransactionAttributeType.REQUIRED).
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param contestId
     *            the ID of the original contest
     * @return the ID of the newly created repost contest (positive)
     * @throws IllegalArgumentException
     *             if tcSubject is null or contestId <= 0
     * @throws IllegalStateException
     *             if this bean was not properly initialized (any bean or resource to be used was not properly
     *             injected)
     * @throws ContestNotFoundException
     *             if studio/software contest with the given ID cannot be found
     * @throws DirectServiceFacadeException
     *             if some other error occurred
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public long repostSoftwareContest(TCSubject tcSubject, long contestId) throws DirectServiceFacadeException {
        long timestamp = System.currentTimeMillis();
        String methodName = "long repostSoftwareContest(TCSubject tcSubject, long contestId)";
        String methodArguments = "tcSubject=" + tcSubject + ", contestId=" + contestId;
        logEntrance(log, methodName, methodArguments);

        checkNull(log, methodName, tcSubject, "tcSubject");
        checkId(log, methodName, contestId, "contestId");
        checkState(log, methodName);

        SoftwareCompetition softwareCompetition = getSoftwareCompetition(tcSubject, contestId, methodName);
        Project projectHeader = softwareCompetition.getProjectHeader();
        long newContestId = reOpenSoftwareContest(tcSubject, contestId, projectHeader.getTcDirectProjectId(),
                methodName);

        // update new software contest.
        updateNewSoftwareContest(tcSubject, newContestId, methodName, projectHeader);

        // Return newContestId.
        logExit(log, methodName, newContestId, System.currentTimeMillis() - timestamp);
        return newContestId;
    }

    /**
     * <p>
     * Creates new version for design/development contest.
     * </p>
     *
     * <p>
     * Annotation: @TransactionAttribute(TransactionAttributeType.REQUIRED).
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param contestId
     *            the ID of the original contest
     * @return the ID of the newly created upgrade contest (positive)
     * @throws IllegalArgumentException
     *             if tcSubject is null or contestId <= 0
     * @throws IllegalStateException
     *             if this bean was not properly initialized (any bean or resource to be used was not properly
     *             injected)
     * @throws ContestNotFoundException
     *             if studio/software contest with the given ID cannot be found
     * @throws DirectServiceFacadeException
     *             if some other error occurred
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public long createNewVersionForDesignDevContest(TCSubject tcSubject, long contestId)
            throws DirectServiceFacadeException {
        long timestamp = System.currentTimeMillis();
        String methodName = "long createNewVersionForDesignDevContest(TCSubject tcSubject, long contestId)";
        String methodArguments = "tcSubject=" + tcSubject + ", contestId=" + contestId;
        logEntrance(log, methodName, methodArguments);

        checkNull(log, methodName, tcSubject, "tcSubject");
        checkId(log, methodName, contestId, "contestId");
        checkState(log, methodName);

        SoftwareCompetition softwareCompetition = getSoftwareCompetition(tcSubject, contestId, methodName);
        com.topcoder.management.project.Project projectHeader = softwareCompetition.getProjectHeader();
        long newContestId = createNewVersionForDesignDevContest(tcSubject, contestId, projectHeader
                .getTcDirectProjectId(), methodName);

        // update new software contest.
        updateNewSoftwareContest(tcSubject, newContestId, methodName, projectHeader);

        // Return newContestId.
        logExit(log, methodName, newContestId, System.currentTimeMillis() - timestamp);
        return newContestId;
    }

    /**
     * <p>
     * Deletes the contest with the given ID.
     * </p>
     *
     * <p>
     * Annotation: @TransactionAttribute(TransactionAttributeType.REQUIRED).
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param contestId
     *            the ID of the contest to be deleted
     * @param studio
     *            true for a studio contest, false - for a software contest
     * @throws IllegalArgumentException
     *             if tcSubject is null or contestId <= 0
     * @throws IllegalStateException
     *             if this bean was not properly initialized (any bean or resource to be used was not properly
     *             injected)
     * @throws ContestNotFoundException
     *             if studio/software contest with the given ID cannot be found
     * @throws DirectServiceFacadeException
     *             if some other error occurred
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteContest(TCSubject tcSubject, long contestId, boolean studio)
            throws DirectServiceFacadeException {
        long timestamp = System.currentTimeMillis();
        String methodName = "void deleteContest(TCSubject tcSubject, long contestId, boolean studio)";
        String methodArguments = "tcSubject=" + tcSubject + ", contestId=" + contestId + ", studio=" + studio;
        logEntrance(log, methodName, methodArguments);

        checkNull(log, methodName, tcSubject, "tcSubject");
        checkId(log, methodName, contestId, "contestId");
        checkState(log, methodName);

        if (studio) {
            StudioCompetition contest = getStudioCompetition(tcSubject, contestId, methodName);
            ContestData contestData = contest.getContestData();
            long oldStatusId = contestData.getStatusId();
            // If oldStatusId == 2 (Active) then
            if (oldStatusId == 2) {
                contestData.setStatusId(16); // Canceled
            } else {
                contestData.setStatusId(14); // Abandoned
            }
            contestData.setDetailedStatusId(contestData.getStatusId());
            updateContest(tcSubject, contest, methodName);
        } else {
            SoftwareCompetition contest = getSoftwareCompetition(tcSubject, contestId, methodName);
            com.topcoder.management.project.Project project = contest.getProjectHeader();

            if (ProjectStatus.ACTIVE == project.getProjectStatus()) {
                project.setProjectStatus(ProjectStatus.CANCELLED_CLIENT_REQUEST);
            } else {
                project.setProjectStatus(ProjectStatus.DELETED);
            }
            updateSoftwareContest(tcSubject, contest, project.getTcDirectProjectId(), methodName);
        }

        logExit(log, methodName, "void", System.currentTimeMillis() - timestamp);
    }

    /**
     * <p>
     * Retrieves the project game plan.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param projectId
     *            the ID of the project
     * @return the retrieved project game plan (not null, doesn't contain null)
     * @throws IllegalArgumentException
     *             if tcSubject is null or projectId <= 0
     * @throws IllegalStateException
     *             if this bean was not properly initialized (any bean or resource to be used was not properly
     *             injected)
     * @throws DirectServiceFacadeException
     *             if some other error occurred
     */
    public List<ContestPlan> getProjectGamePlan(TCSubject tcSubject, long projectId)
            throws DirectServiceFacadeException {
        long timestamp = System.currentTimeMillis();
        String methodName = "List<ContestPlan> getProjectGamePlan(TCSubject tcSubject, long projectId)";
        String methodArguments = "tcSubject=" + tcSubject + ", projectId=" + projectId;
        logEntrance(log, methodName, methodArguments);

        checkNull(log, methodName, tcSubject, "tcSubject");
        checkId(log, methodName, projectId, "projectId");
        checkState(log, methodName, contestServiceFacade, "contestServiceFacade");

        List<CommonProjectContestData> commonProjectContestDataList;

        try {
            commonProjectContestDataList = contestServiceFacade
                    .getCommonProjectContestDataByPID(tcSubject, projectId);
        } catch (com.topcoder.service.studio.PersistenceException e) {
            logException(log, methodName, e);
            throw new DirectServiceFacadeException(
                    "PersistenceException occurs while getting common project contest data.", e);
        }

        List<ContestPlan> result = new ArrayList<ContestPlan>();

        // For each commonProjectContestData from commonProjectContestDataList do:
        for (CommonProjectContestData commonProjectContestData : commonProjectContestDataList) {
            ContestPlan contestPlan = new ContestPlan();
            contestPlan.setStudio("Studio".equals(commonProjectContestData.getType()));
            contestPlan.setContestId(commonProjectContestData.getContestId());
            contestPlan.setName(commonProjectContestData.getCname());
            contestPlan.setStartDate(commonProjectContestData.getStartDate());
            contestPlan.setEndDate(commonProjectContestData.getEndDate());
            result.add(contestPlan);
        }

        // Return result.
        logExit(log, methodName, result, System.currentTimeMillis() - timestamp);
        return result;
    }

    /**
     * <p>
     * Retrieves the parent projects for the project with the given ID.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param projectId
     *            the ID of the project
     * @return the retrieved parent projects (not null, doesn't contain null, contains instances of
     *         com.topcoder.management.project.Project)
     * @throws IllegalArgumentException
     *             if tcSubject is null or projectId <= 0
     * @throws IllegalStateException
     *             if this bean was not properly initialized (any bean or resource to be used was not properly
     *             injected)
     * @throws DirectServiceFacadeException
     *             if some other error occurred
     */
    public List<Project> getParentProjects(TCSubject tcSubject, long projectId) throws DirectServiceFacadeException {
        long timestamp = System.currentTimeMillis();
        String methodName = "List<Project> getParentProjects(TCSubject tcSubject, long projectId)";
        String methodArguments = "tcSubject=" + tcSubject + ", projectId=" + projectId;
        logEntrance(log, methodName, methodArguments);

        checkNull(log, methodName, tcSubject, "tcSubject");
        checkId(log, methodName, projectId, "projectId");
        checkState(log, methodName, projectLinkManager, "projectLinkManager");

        ProjectLink[] sourceProjectLinks;

        try {
            sourceProjectLinks = projectLinkManager.getSourceProjectLinks(projectId);
        } catch (PersistenceException e) {
            logException(log, methodName, e);
            throw new DirectServiceFacadeException("PersistenceException occurs while getting parent projects.", e);
        }

        List<Project> result = new ArrayList<Project>();
        for (ProjectLink sourceProjectLink : sourceProjectLinks) {
            Project sourceProject = sourceProjectLink.getSourceProject();
            result.add(sourceProject);
        }

        // Return result.
        logExit(log, methodName, result, System.currentTimeMillis() - timestamp);
        return result;
    }

    /**
     * <p>
     * Retrieves the child projects for the project with the given ID.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param projectId
     *            the ID of the project
     * @return the retrieved child projects (not null, doesn't contain null, contains instances of
     *         com.topcoder.management.project.Project)
     * @throws IllegalArgumentException
     *             if tcSubject is null or projectId <= 0
     * @throws IllegalStateException
     *             if this bean was not properly initialized (any bean or resource to be used was not properly
     *             injected)
     * @throws DirectServiceFacadeException
     *             if some other error occurred
     */
    public List<Project> getChildProjects(TCSubject tcSubject, long projectId) throws DirectServiceFacadeException {
        long timestamp = System.currentTimeMillis();
        String methodName = "List<Project> getChildProjects(TCSubject tcSubject, long projectId)";
        String methodArguments = "tcSubject=" + tcSubject + ", projectId=" + projectId;
        logEntrance(log, methodName, methodArguments);

        checkNull(log, methodName, tcSubject, "tcSubject");
        checkId(log, methodName, projectId, "projectId");
        checkState(log, methodName, projectLinkManager, "projectLinkManager");

        ProjectLink[] destProjectLinks;
        try {
            destProjectLinks = projectLinkManager.getDestProjectLinks(projectId);
        } catch (PersistenceException e) {
            logException(log, methodName, e);
            throw new DirectServiceFacadeException("PersistenceException occurs while getting child projects.", e);
        }

        List<Project> result = new ArrayList<Project>();
        for (ProjectLink destProjectLink : destProjectLinks) {
            Project destProject = destProjectLink.getDestProject();
            result.add(destProject);
        }

        // Return result.
        logExit(log, methodName, result, System.currentTimeMillis() - timestamp);
        return result;
    }

    /**
     * <p>
     * Updates project links for the project with the given ID.
     * </p>
     *
     * <p>
     * Annotation: @TransactionAttribute(TransactionAttributeType.REQUIRED).
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param projectId
     *            the ID of the project
     * @param parentProjectIds
     *            the IDs of the parent projects
     * @param childProjectIds
     *            the IDs of the child projects
     * @throws IllegalArgumentException
     *             if tcSubject is null or projectId <= 0, parentProjectIds or childProjectIds contains not positive
     *             element
     * @throws IllegalStateException
     *             if this bean was not properly initialized (any bean or resource to be used was not properly
     *             injected)
     * @throws DirectServiceFacadeException
     *             if some other error occurred
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateProjectLinks(TCSubject tcSubject, long projectId, long[] parentProjectIds,
            long[] childProjectIds) throws DirectServiceFacadeException {
        long timestamp = System.currentTimeMillis();
        String methodName = "void updateProjectLinks(TCSubject tcSubject, long projectId, long[] parentProjectIds, long[] childProjectIds)";
        String methodArguments = "tcSubject=" + tcSubject + ", projectId=" + projectId + ", parentProjectIds="
                + parentProjectIds + ", childProjectIds=" + childProjectIds;
        logEntrance(log, methodName, methodArguments);

        checkNull(log, methodName, tcSubject, "tcSubject");
        checkId(log, methodName, projectId, "projectId");
        checkState(log, methodName, projectLinkManager, "projectLinkManager");

        if (parentProjectIds == null) {
            parentProjectIds = new long[0];
        } else {
            for (long parentProjectId : parentProjectIds) {
                checkId(log, methodName, parentProjectId, "element in parentProjectIds");
            }
        }

        if (childProjectIds == null) {
            childProjectIds = new long[0];
        } else {
            for (long childProjectId : childProjectIds) {
                checkId(log, methodName, childProjectId, "element in childProjectIds");
            }
        }

        long[] projectIds = new long[parentProjectIds.length + childProjectIds.length];
        long[] linkTypeIds = new long[projectIds.length];

        for (int i = 0; i < parentProjectIds.length; i++) {
            projectIds[i] = parentProjectIds[i];
            linkTypeIds[i] = ProjectLinkType.DEPENDS_ON;
        }
        for (int i = 0; i < childProjectIds.length; i++) {
            projectIds[i + parentProjectIds.length] = childProjectIds[i];
            linkTypeIds[i + parentProjectIds.length] = ProjectLinkType.IS_RELATED_TO;
        }

        try {
            projectLinkManager.updateProjectLinks(projectId, projectIds, linkTypeIds);
        } catch (PersistenceException e) {
            logException(log, methodName, e);
            throw new DirectServiceFacadeException("PersistenceException occurs while updating project links.", e);
        }

        logExit(log, methodName, "void", System.currentTimeMillis() - timestamp);
    }

    /**
     * <p>
     * Retrieves the project budget for the project with the specified billing ID.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param billingProjectId
     *            the ID of the billing project
     * @return the retrieved project budget
     * @throws IllegalArgumentException
     *             if tcSubject is null or billingProjectId <= 0
     * @throws IllegalStateException
     *             if this bean was not properly initialized (any bean or resource to be used was not properly
     *             injected)
     * @throws ProjectNotFoundException
     *             if billing project with the given ID cannot be found
     * @throws DirectServiceFacadeException
     *             if some other error occurred
     */
    public double getProjectBudget(TCSubject tcSubject, long billingProjectId) throws DirectServiceFacadeException {
        long timestamp = System.currentTimeMillis();
        String methodName = "double getProjectBudget(TCSubject tcSubject, long billingProjectId)";
        String methodArguments = "tcSubject=" + tcSubject + ", billingProjectId=" + billingProjectId;
        logEntrance(log, methodName, methodArguments);

        checkNull(log, methodName, tcSubject, "tcSubject");
        checkId(log, methodName, billingProjectId, "billingProjectId");
        checkState(log, methodName, projectDAO, "projectDAO");

        // get project
        com.topcoder.clients.model.Project project = retrieveProjectById(billingProjectId, methodName);

        // return project budget
        double budget = project.getBudget();
        logExit(log, methodName, budget, System.currentTimeMillis() - timestamp);
        return budget;
    }

    /**
     * <p>
     * Updates project budget.
     * </p>
     *
     * <p>
     * Annotation: @TransactionAttribute(TransactionAttributeType.REQUIRED).
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param billingProjectId
     *            the ID of the billing project
     * @param changedAmount
     *            the changed amount (negative if the budget decreased)
     * @return the project budget data (not null)
     * @throws IllegalArgumentException
     *             if tcSubject is null or billingProjectId <= 0
     * @throws IllegalStateException
     *             if this bean was not properly initialized (any bean or resource to be used was not properly
     *             injected)
     * @throws ProjectNotFoundException
     *             if billing project with the given ID cannot be found
     * @throws EmailSendingException
     *             if some error occurred when sending an email message
     * @throws DirectServiceFacadeException
     *             if some other error occurred
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ProjectBudget updateProjectBudget(TCSubject tcSubject, long billingProjectId, double changedAmount)
            throws DirectServiceFacadeException {
        long timestamp = System.currentTimeMillis();
        String methodName = "ProjectBudget updateProjectBudget("
                + "TCSubject tcSubject, long billingProjectId, double changedAmount)";
        String methodArguments = "tcSubject=" + tcSubject + ", billingProjectId=" + billingProjectId
                + ", changedAmount" + changedAmount;
        logEntrance(log, methodName, methodArguments);

        checkNull(log, methodName, tcSubject, "tcSubject");
        checkId(log, methodName, billingProjectId, "billingProjectId");
        checkState(log, methodName, projectDAO, "projectDAO");

        // get project
        com.topcoder.clients.model.Project project = retrieveProjectById(billingProjectId, methodName);

        double oldBudget = project.getBudget();
        double newBudget = oldBudget + changedAmount;
        if (newBudget < 0) {
            DirectServiceFacadeException e = new DirectServiceFacadeException("newBudget should not be negative.");
            logException(log, methodName, e);
            throw e;
        }

        ProjectBudget result = new ProjectBudget();
        result.setBillingProjectName(project.getName());
        result.setOldBudget(oldBudget);
        result.setNewBudget(newBudget);
        result.setChangedAmount(changedAmount);

        List<String> recipients = null;
        try {
            // update project budget
            projectDAO.updateProjectBudget(tcSubject.toString(), billingProjectId, changedAmount);

            recipients = projectDAO.getUsersByProject(billingProjectId);
        } catch (DAOException e) {
            logException(log, methodName, e);
            throw new DirectServiceFacadeException("DAOException occurs while updating project budgets.", e);
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("billingProjectName", project.getName());
        params.put("oldBudget", Double.toString(oldBudget));
        params.put("newBudget", Double.toString(newBudget));
        params.put("changedAmount", Double.toString(changedAmount));

        sendEmail(budgetUpdateEmailTitleTemplateText, budgetUpdateEmailBodyTemplateText, recipients, params,
                methodName);

        // Return result.
        logExit(log, methodName, result, System.currentTimeMillis() - timestamp);
        return result;
    }

    /**
     * <p>
     * Retrieves the specification review state.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param contestId
     *            the ID of the contest
     * @return the retrieved specification review state (not null)
     * @throws IllegalArgumentException
     *             if tcSubject is null or contestId <= 0
     * @throws IllegalStateException
     *             if this bean was not properly initialized (any bean or resource to be used was not properly
     *             injected)
     * @throws ContestNotFoundException
     *             if contest with the given ID cannot be found
     * @throws DirectServiceFacadeException
     *             if some other error occurred
     */
    public SpecReviewState getSpecReviewState(TCSubject tcSubject, long contestId)
            throws DirectServiceFacadeException {
        long timestamp = System.currentTimeMillis();
        String methodName = "SpecReviewState getSpecReviewState(TCSubject tcSubject, long contestId)";
        String methodArguments = "tcSubject=" + tcSubject + ", contestId=" + contestId;
        logEntrance(log, methodName, methodArguments);

        checkNull(log, methodName, tcSubject, "tcSubject");
        checkId(log, methodName, contestId, "contestId");
        checkState(log, methodName, projectManager, "projectManager");
        checkState(log, methodName, phaseManager, "phaseManager");

        SpecReviewState result = new SpecReviewState();

        Project project;
        try {
            project = projectManager.getProject(contestId);
        } catch (PersistenceException e) {
            logException(log, methodName, e);
            throw new DirectServiceFacadeException("PersistenceException occurs while getting project.", e);
        }

        // get ProjectSpec
        ProjectSpec projectSpec = project.getProjectSpec();
        long projectSpecId = projectSpec.getProjectSpecId();
        com.topcoder.project.phases.Project phasesProject;

        try {
            phasesProject = phaseManager.getPhases(projectSpecId);
        } catch (PhaseManagementException e) {
            logException(log, methodName, e);
            throw new DirectServiceFacadeException("PhaseManagementException occurs while getting phases.", e);
        }

        Phase[] allPhases = phasesProject.getAllPhases();
        Phase reviewPhase = null;
        SpecReviewStatus status = null;

        for (Phase phase : allPhases) {
            if ("Review".equals(phase.getPhaseType().getName())) {
                reviewPhase = phase;
                break;
            }
        }

        if (reviewPhase == null) {
            DirectServiceFacadeException e = new DirectServiceFacadeException("No review phase is found.");
            logException(log, methodName, e);
            throw e;
        }

        Date endDate = reviewPhase.getScheduledEndDate();
        result.setDeadline(getXMLGregorianCalendar(endDate));

        if (PhaseStatus.OPEN == reviewPhase.getPhaseStatus()) {
            status = SpecReviewStatus.REVIEW_STARTED;
            Date curDate = new Date();
            result.setDelayed(curDate.getTime() > endDate.getTime());
            if (result.isDelayed()) {
                result.setDelayedHour((int) ((curDate.getTime() - endDate.getTime()) / 3600000));
            }
        } else if (PhaseStatus.SCHEDULED == reviewPhase.getPhaseStatus()) {
            status = SpecReviewStatus.NOBODY_REGISTERED;
        } else {
            status = getOtherState(allPhases);
        }

        // set status
        result.setStatus(status);

        // Return result.
        logExit(log, methodName, result, System.currentTimeMillis() - timestamp);
        return result;
    }

    /**
     * <p>
     * Sends an email message generated from templates to the specified recipients.
     * </p>
     *
     * @param titleTemplateText
     *            the template text of the message title
     * @param bodyTemplateText
     *            the template text of the message body
     * @param recipients
     *            the email addresses of recipients
     * @param params
     *            the template parameters
     * @param methodName
     *            the method name
     * @throws IllegalStateException
     *             if emailSender is not set
     * @throws EmailSendingException
     *             if some error occurred when sending an email message
     */
    private void sendEmail(String titleTemplateText, String bodyTemplateText, List<String> recipients,
            Map<String, String> params, String methodName) throws EmailSendingException {
        checkState(log, methodName, emailSender, "emailSender");

        // if recipients is empty, just return
        if (recipients.size() == 0) {
            return;
        }

        // Get document generator
        DocumentGenerator documentGenerator = new DocumentGenerator();
        try {
            // Parse email title template:
            Template emailTitleTemplate = documentGenerator.parseTemplate(titleTemplateText);
            // Parse email body template:
            Template emailBodyTemplate = documentGenerator.parseTemplate(bodyTemplateText);
            // Construct nodes for template variables:
            Node[] nodes = new Node[params.size()];

            int paramNum = 0;
            for (Entry<String, String> entry : params.entrySet()) {
                // Create template field instance:
                nodes[paramNum] = new Field(entry.getKey(), entry.getValue(), null, true);
                paramNum++;
            }

            // Create template fields data:
            TemplateFields data = new TemplateFields(nodes, emailTitleTemplate);
            // Generate email title from template:
            String emailTitle = documentGenerator.applyTemplate(data);
            // Create template fields data:
            data = new TemplateFields(nodes, emailBodyTemplate);
            // Generate email body from template:
            String emailBody = documentGenerator.applyTemplate(data);
            // Create email message:
            TCSEmailMessage message = new TCSEmailMessage();
            // Set message parameters:
            message.setSubject(emailTitle);
            message.setBody(emailBody);
            message.setFromAddress(emailSender);

            // Add first recipient:
            message.addToAddress(recipients.get(0), TCSEmailMessage.TO);
            for (int i = 1; i < recipients.size(); i++) {
                message.addToAddress(recipients.get(i), TCSEmailMessage.BCC);
            }

            // Send email message:
            EmailEngine.send(message);
        } catch (TemplateFormatException e) {
            logException(log, methodName, e);
            throw new EmailSendingException("TemplateFormatException occurs while sending email message.", e);
        } catch (TemplateDataFormatException e) {
            logException(log, methodName, e);
            throw new EmailSendingException("TemplateDataFormatException occurs while sending email message.", e);
        } catch (AddressException e) {
            logException(log, methodName, e);
            throw new EmailSendingException("AddressException occurs while sending email message.", e);
        } catch (ConfigManagerException e) {
            logException(log, methodName, e);
            throw new EmailSendingException("ConfigManagerException occurs while sending email message.", e);
        } catch (SendingException e) {
            logException(log, methodName, e);
            throw new EmailSendingException("SendingException occurs while sending email message.", e);
        }
    }

    /**
     * <p>
     * Gets contest receipt data.
     * </p>
     *
     * @param contestId
     *            the contest id
     * @param isStudio
     *            the isStudio flag
     * @param competition
     *            the competition
     * @param contestName
     *            the contest name
     * @param contestType
     *            the contest type
     * @param status
     *            the status
     * @param milestoneDate
     *            the milestone date
     * @param referenceNo
     *            the referenceNo
     * @param billingProjectId
     *            the billing project id
     * @return the contest receipt data
     * @throws ContestNotFoundException
     *             if studio/software contest with the given ID cannot be found
     * @throws DirectServiceFacadeException
     *             if some other error occurred
     */
    private ContestReceiptData getContestReceiptData(long contestId, boolean isStudio, Competition competition,
            String contestName, String contestType, String status, XMLGregorianCalendar milestoneDate,
            String referenceNo, long billingProjectId) throws DirectServiceFacadeException {
        String methodName = "ContestReceiptData getContestReceiptData("
                + "TCSubject tcSubject, long contestId, boolean isStudio)";

        ContestReceiptData result = new ContestReceiptData();
        // Set contest ID to result:
        result.setContestId(contestId);
        // Set studio/software flag to result:
        result.setStudio(isStudio);

        // Set project name to result:
        com.topcoder.service.project.Project project = competition.getProject();
        // don't set if project is null
        if (project != null) {
            result.setProjectName(project.getName());
        }

        // Set contest name to result:
        result.setContestName(contestName);
        // Set contest fee to result:
        result.setContestFee(competition.getContestFee());
        // Set start date to result:
        result.setStartDate(competition.getStartTime());
        // Set contest type to result:
        result.setContestType(contestType);

        double totalCost = competition.getContestFee();
        double[] prizes = new double[0];
        List<CompetitionPrize> competitionPrizes = competition.getPrizes();
        // use 0 if competition.getPrizes() is null
        if (competitionPrizes != null) {
            prizes = new double[competitionPrizes.size()];

            for (int i = 0; i < prizes.length; i++) {
                prizes[i] = competitionPrizes.get(i).getAmount();
                totalCost += prizes[i];
            }
        }

        // Set prizes to result:
        result.setPrizes(prizes);
        // Set milestone date to result:
        result.setMilestoneDate(milestoneDate);
        // Set status to result:
        result.setStatus(status);
        // Set contest total cost to result:
        result.setContestTotalCost(totalCost);
        // Set end date to result:
        result.setEndDate(competition.getEndTime());

        try {
            // Set phone to result:
            User user = userService.getUser(competition.getCreatorUserId());
            result.setPhone(user.getPhone());

            // Set email to result:
            String emailAddress = userService.getEmailAddress(user.getUserId());
            result.setEmail(emailAddress);

            // If billingProjectId != 0 then
            if (billingProjectId != 0) {
                com.topcoder.clients.model.Project billingProject = projectDAO.retrieveById(billingProjectId, true);
                Client client = billingProject.getClient();
                Company company = client.getCompany();
                result.setCompanyName(company.getName());
                // Set purchase order no to result:
                result.setPurchaseOrderNo(billingProject.getPOBoxNumber());
                // Set invoice terms to result:
                result.setInvoiceTerms(Long.toString(billingProject.getPaymentTermsId()));
            }
        } catch (UserServiceException e) {
            logException(log, methodName, e);
            throw new DirectServiceFacadeException("UserServiceException occurs while getting contest receipt data.",
                    e);
        } catch (EntityNotFoundException e) {
            logException(log, methodName, e);
            throw new ContestNotFoundException("EntityNotFoundException occurs while getting contest receipt data.",
                    e, billingProjectId);
        } catch (DAOException e) {
            logException(log, methodName, e);
            throw new DirectServiceFacadeException("DAOException occurs while getting contest receipt data.", e);
        }

        // Set reference no to result:
        result.setReferenceNo(referenceNo);

        return result;
    }

    /**
     * <p>
     * Gets studio competition.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param contestId
     *            the ID of the contest
     * @param methodName
     *            the method name
     * @return the studio competition
     * @throws ContestNotFoundException
     *             if studio contest with the given ID cannot be found
     * @throws DirectServiceFacadeException
     *             if some other error occurred
     */
    private StudioCompetition getStudioCompetition(TCSubject tcSubject, long contestId, String methodName)
            throws DirectServiceFacadeException {
        try {
            return contestServiceFacade.getContest(tcSubject, contestId);
        } catch (com.topcoder.service.studio.PersistenceException e) {
            logException(log, methodName, e);
            throw new DirectServiceFacadeException("PersistenceException occurs while getting studio competition.", e);
        } catch (com.topcoder.service.studio.ContestNotFoundException e) {
            logException(log, methodName, e);
            throw new ContestNotFoundException("ContestNotFoundException occurs while getting studio competition.",
                    e, contestId);
        }
    }

    /**
     * <p>
     * Gets software competition.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param contestId
     *            the ID of the contest
     * @param methodName
     *            the method name
     * @return the software competition
     * @throws DirectServiceFacadeException
     *             if any error occurred
     */
    private SoftwareCompetition getSoftwareCompetition(TCSubject tcSubject, long contestId, String methodName)
            throws DirectServiceFacadeException {
        try {
            return contestServiceFacade.getSoftwareContestByProjectId(tcSubject, contestId);
        } catch (ContestServiceException e) {
            logException(log, methodName, e);
            throw new DirectServiceFacadeException(
                    "ContestServiceException occurs while getting software competition.", e);
        }
    }

    /**
     * <p>
     * Updates the contest.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param contest
     *            the contest
     * @param methodName
     *            the method name
     * @throws ContestNotFoundException
     *             if studio contest with the given ID cannot be found
     * @throws DirectServiceFacadeException
     *             if some other error occurred
     */
    private void updateContest(TCSubject tcSubject, StudioCompetition contest, String methodName)
            throws DirectServiceFacadeException {
        try {
            contestServiceFacade.updateContest(tcSubject, contest);
        } catch (com.topcoder.service.studio.PersistenceException e) {
            logException(log, methodName, e);
            throw new DirectServiceFacadeException("PersistenceException occurs while updating contest.", e);
        } catch (com.topcoder.service.studio.ContestNotFoundException e) {
            logException(log, methodName, e);
            throw new ContestNotFoundException("ContestNotFoundException occurs while updating contest.", e, contest
                    .getId());
        }
    }

    /**
     * <p>
     * Updates the software contest.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param contest
     *            the contest
     * @param projectId
     *            the project id
     * @param methodName
     *            the method name
     * @throws DirectServiceFacadeException
     *             if any error occurred
     */
    private void updateSoftwareContest(TCSubject tcSubject, SoftwareCompetition contest, long projectId,
            String methodName) throws DirectServiceFacadeException {
        try {
            contestServiceFacade.updateSoftwareContest(tcSubject, contest, projectId);
        } catch (ContestServiceException e) {
            logException(log, methodName, e);
            throw new DirectServiceFacadeException("ContestServiceException occurs while updating software contest.",
                    e);
        }
    }

    /**
     * <p>
     * Re-open software contest.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param contestId
     *            the contest id
     * @param tcDirectProjectId
     *            the TC direct project id
     * @param methodName
     *            the method name
     * @return the newly created contest id
     * @throws DirectServiceFacadeException
     *             if any error occurred
     */
    private long reOpenSoftwareContest(TCSubject tcSubject, long contestId, long tcDirectProjectId, String methodName)
            throws DirectServiceFacadeException {
        try {
            return contestServiceFacade.reOpenSoftwareContest(tcSubject, contestId, tcDirectProjectId);
        } catch (ContestServiceException e) {
            logException(log, methodName, e);
            throw new DirectServiceFacadeException(
                    "ContestServiceException occurs while reopening software contest.", e);
        }
    }

    /**
     * <p>
     * Create new version for design or development contest..
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param contestId
     *            the contest id
     * @param tcDirectProjectId
     *            the TC direct project id
     * @param methodName
     *            the method name
     * @return the newly created contest id
     * @throws DirectServiceFacadeException
     *             if any error occurred
     */
    private long createNewVersionForDesignDevContest(TCSubject tcSubject, long contestId, long tcDirectProjectId,
            String methodName) throws DirectServiceFacadeException {
        try {
            return contestServiceFacade.createNewVersionForDesignDevContest(tcSubject, contestId, tcDirectProjectId,
                    true, true);
        } catch (ContestServiceException e) {
            logException(log, methodName, e);
            throw new DirectServiceFacadeException(
                    "ContestServiceException occurs while reopening software contest.", e);
        }
    }

    /**
     * <p>
     * Updates new software contest.
     * </p>
     *
     * @param tcSubject
     *            the TC subject
     * @param newContestId
     *            the new contest id
     * @param methodName
     *            the method name
     * @param projectHeader
     *            the project header
     * @throws DirectServiceFacadeException
     *             if some other error occurred
     */
    private void updateNewSoftwareContest(TCSubject tcSubject, long newContestId, String methodName,
            Project projectHeader) throws DirectServiceFacadeException {
        SoftwareCompetition newSoftwareCompetition = getSoftwareCompetition(tcSubject, newContestId, methodName);

        FullProjectData projectData = newSoftwareCompetition.getProjectData();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.add(Calendar.DATE, 1);
        gregorianCalendar.set(Calendar.HOUR, 9);
        gregorianCalendar.set(Calendar.MINUTE, 0);
        gregorianCalendar.set(Calendar.SECOND, 0);
        gregorianCalendar.set(Calendar.MILLISECOND, 0);
        projectData.setStartDate(gregorianCalendar.getTime());

        // update software contest
        updateSoftwareContest(tcSubject, newSoftwareCompetition, projectHeader.getTcDirectProjectId(), methodName);
    }

    /**
     * <p>
     * Retrieves the project with the specified billing ID.
     * </p>
     *
     * @param billingProjectId
     *            the ID of the billing project
     * @param methodName
     *            the method name
     * @return the retrieved project
     * @throws ProjectNotFoundException
     *             if project with the given ID cannot be found
     * @throws DirectServiceFacadeException
     *             if some other error occurred
     */
    private com.topcoder.clients.model.Project retrieveProjectById(Long billingProjectId, String methodName)
            throws DirectServiceFacadeException {
        try {
            return projectDAO.retrieveById(billingProjectId, false);
        } catch (EntityNotFoundException e) {
            logException(log, methodName, e);
            throw new ProjectNotFoundException("EntityNotFoundException occurs while getting project.", e,
                    billingProjectId);
        } catch (DAOException e) {
            logException(log, methodName, e);
            throw new DirectServiceFacadeException("DAOException occurs while getting project.", e);
        }
    }

    /**
     * <p>
     * Checks if this bean is properly initialized.
     * </p>
     *
     * @param log
     *            the log
     * @param methodName
     *            the method name
     * @throws IllegalStateException
     *             if this bean is not properly initialized
     */
    private void checkState(Log log, String methodName) {
        checkState(log, methodName, contestServiceFacade, "contestServiceFacade");
        checkState(log, methodName, userService, "userService");
        checkState(log, methodName, projectDAO, "projectDAO");
        checkState(log, methodName, projectManager, "projectManager");
    }

    /**
     * <p>
     * Sets phase length.
     * </p>
     *
     * @param phase
     *            the phase
     * @param extendBy
     *            the extendBy
     * @param methodName
     *            the method name
     * @throws DirectServiceFacadeException
     *             if phaseLength <= 0
     */
    private void setPhaseLength(Phase phase, Integer extendBy, String methodName) throws DirectServiceFacadeException {
        if (phase != null && extendBy != null) {
            long phaseLength = phase.getLength();
            System.out.println("phaseLength=" + phaseLength + "\textendBy=" + extendBy);
            phaseLength += extendBy * 3600000;
            System.out.println("phaseLength=" + phaseLength);
            if (phaseLength <= 0) {
                DirectServiceFacadeException e = new DirectServiceFacadeException("phaseLength should be positive.");
                logException(log, methodName, e);
                throw e;
            }
            phase.setLength(phaseLength);
        }
    }

    /**
     * <p>
     * Gets contest prizes.
     * </p>
     *
     * @param competitionPrizes
     *            the competition prizes
     * @return the contest prizes
     */
    private static double[] getContestPrizes(List<CompetitionPrize> competitionPrizes) {
        double[] contestPrizes = new double[competitionPrizes.size()];
        for (int i = 0; i < contestPrizes.length; i++) {
            CompetitionPrize competitionPrize = competitionPrizes.get(i);
            contestPrizes[competitionPrize.getPlace() - 1] = competitionPrize.getAmount();
        }

        return contestPrizes;
    }

    /**
     * <p>
     * Format date string.
     * </p>
     *
     * @param calendar
     *            the calendar
     * @return formated string
     */
    private static String formatDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(calendar.toGregorianCalendar().getTime());
    }

    /**
     * <p>
     * Checks whether the given Object is null.
     * </p>
     *
     * @param log
     *            the log
     * @param methodName
     *            the method name
     * @param arg
     *            the argument to check
     * @param name
     *            the name of the argument to check
     * @throws IllegalArgumentException
     *             if the given Object is null
     */
    private static void checkNull(Log log, String methodName, Object arg, String name) {
        if (arg == null) {
            IllegalArgumentException e = new IllegalArgumentException(name + " should not be null.");
            logException(log, methodName, e);
            throw e;
        }
    }

    /**
     * <p>
     * Checks whether the given String is null or empty.
     * </p>
     *
     * @param log
     *            the log
     * @param methodName
     *            the method name
     * @param arg
     *            the String to check
     * @param name
     *            the name of the String argument to check
     * @throws IllegalArgumentException
     *             if the given string is null or empty
     */
    private static void checkNullOrEmpty(Log log, String methodName, String arg, String name) {
        checkNull(log, methodName, arg, name);

        if (arg.trim().length() == 0) {
            IllegalArgumentException e = new IllegalArgumentException(name + " should not be empty.");
            logException(log, methodName, e);
            throw e;
        }
    }

    /**
     * <p>
     * Checks whether the id <= 0.
     * </p>
     *
     * @param log
     *            the log
     * @param methodName
     *            the method name
     * @param id
     *            the id
     * @param name
     *            the name
     * @throws IllegalArgumentException
     *             if id <= 0
     */
    private static void checkId(Log log, String methodName, long id, String name) {
        if (id <= 0) {
            IllegalArgumentException e = new IllegalArgumentException(name + " should be positive number.");
            logException(log, methodName, e);
            throw e;
        }
    }

    /**
     * <p>
     * Checks whether the arg is negative.
     * </p>
     *
     * @param log
     *            the log
     * @param methodName
     *            the method name
     * @param arg
     *            the arg
     * @param name
     *            the name
     * @throws IllegalArgumentException
     *             if arg is negative
     */
    private static void checkNegative(Log log, String methodName, double arg, String name) {
        if (arg < 0) {
            IllegalArgumentException e = new IllegalArgumentException(name + " should not be negative.");
            logException(log, methodName, e);
            throw e;
        }
    }

    /**
     * <p>
     * Checks whether the state is null.
     * </p>
     *
     * @param log
     *            the log
     * @param methodName
     *            the method name
     * @param state
     *            the state
     * @param name
     *            the name
     * @throws IllegalStateException
     *             if state is null
     */
    private static void checkState(Log log, String methodName, Object state, String name) {
        if (state == null) {
            IllegalStateException e = new IllegalStateException(name + " should not be null.");
            logException(log, methodName, e);
            throw e;
        }
    }

    /**
     * <p>
     * Validates the configuration parameter.
     * </p>
     *
     * @param param
     *            the configuration parameter value
     * @param name
     *            the parameter name
     * @param required
     *            the configuration parameter is required or not
     * @param allowedEmpty
     *            the configuration parameter can be empty or not
     * @return the configuration parameter value
     * @throws DirectServiceFacadeConfigurationException
     *             if param is empty, or required param is null
     */
    private static String validateParameter(String param, String name, boolean required, boolean allowedEmpty)
            throws DirectServiceFacadeConfigurationException {
        if (param == null) {
            if (required) {
                throw new DirectServiceFacadeConfigurationException("The required configuration parameter [" + name
                        + "] should not be null.");
            }

            return null;
        } else if (!allowedEmpty && param.trim().length() == 0) {
            throw new DirectServiceFacadeConfigurationException("The configuration parameter [" + name
                    + "] should not be empty.");
        }

        return param;
    }

    /**
     * <p>
     * Creates instance using reflection.
     * </p>
     *
     * @param targetType
     *            the target class type
     * @param className
     *            the name of the class
     * @param configNamespace
     *            the config namespace
     * @return instance of target class
     * @throws DirectServiceFacadeConfigurationException
     *             if any error occurs during creating instance
     */
    private static Object createInstance(Class<?> targetType, String className, String configNamespace)
            throws DirectServiceFacadeConfigurationException {
        try {
            Class<?> classType = Class.forName(className);

            if (!targetType.isAssignableFrom(classType)) {
                throw new DirectServiceFacadeConfigurationException("The class [" + className
                        + "] should be type of [" + targetType.getName() + "].");
            }

            if (configNamespace == null) {
                return classType.newInstance();
            } else {
                Constructor<?> constructor = classType.getConstructor(String.class);
                return constructor.newInstance(configNamespace);
            }
        } catch (ClassNotFoundException e) {
            throw new DirectServiceFacadeConfigurationException(
                    "ClassNotFoundException occurs while creating the instance of " + className, e);
        } catch (SecurityException e) {
            throw new DirectServiceFacadeConfigurationException(
                    "SecurityException occurs while creating the instance of " + className, e);
        } catch (NoSuchMethodException e) {
            throw new DirectServiceFacadeConfigurationException(
                    "NoSuchMethodException occurs while creating the instance of " + className, e);
        } catch (IllegalArgumentException e) {
            throw new DirectServiceFacadeConfigurationException(
                    "IllegalArgumentException occurs while creating the instance of " + className, e);
        } catch (InstantiationException e) {
            throw new DirectServiceFacadeConfigurationException(
                    "InstantiationException occurs while creating the instance of " + className, e);
        } catch (IllegalAccessException e) {
            throw new DirectServiceFacadeConfigurationException(
                    "IllegalAccessException occurs while creating the instance of " + className, e);
        } catch (InvocationTargetException e) {
            throw new DirectServiceFacadeConfigurationException(
                    "InvocationTargetException occurs while creating the instance of " + className, e);
        }
    }

    /**
     * <p>
     * Converts specified <code>Date</code> instance into <code>XMLGregorianCalendar</code> instance.
     * </p>
     *
     * @param date
     *            a <code>Date</code> representing the date to be converted.
     * @return a <code>XMLGregorianCalendar</code> providing the converted value of specified date or
     *         <code>null</code> if specified <code>date</code> is <code>null</code> or if it can't be converted to
     *         calendar.
     */
    private static XMLGregorianCalendar getXMLGregorianCalendar(Date date) {
        if (date == null) {
            return null;
        }

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException ex) {
            return null;
        }
    }

    /**
     * <p>
     * Gets other state.
     * </p>
     *
     * @param allPhases
     *            all phases
     * @return the state
     */
    private static SpecReviewStatus getOtherState(Phase[] allPhases) {
        boolean hasOpenOrScheduledFinalFixPhase = false;
        boolean hasClosedFinalFixPhase = false;
        boolean hasOpenOrScheduledFinalReviewPhase = false;
        boolean hasClosedFinalReviewPhase = false;
        for (Phase phase : allPhases) {
            if ("Final Fix".equals(phase.getPhaseType().getName())) {
                if (PhaseStatus.CLOSED == phase.getPhaseStatus()) {
                    hasClosedFinalFixPhase = true;
                } else {
                    hasOpenOrScheduledFinalFixPhase = true;
                }
                break;
            } else if ("Final Review".equals(phase.getPhaseType().getName())) {
                if (PhaseStatus.CLOSED == phase.getPhaseStatus()) {
                    hasClosedFinalReviewPhase = true;
                } else {
                    hasOpenOrScheduledFinalReviewPhase = true;
                }
                break;
            }
        }

        SpecReviewStatus status = SpecReviewStatus.REVIEW_SCORECARD_PROVIDED;
        if (!hasOpenOrScheduledFinalFixPhase && !hasOpenOrScheduledFinalReviewPhase) {
            status = SpecReviewStatus.FINAL_FIX_ACCEPTED;
        } else if (hasClosedFinalFixPhase && hasOpenOrScheduledFinalReviewPhase) {
            status = SpecReviewStatus.FINAL_FIX_REVIEW;
        } else if (hasClosedFinalReviewPhase && hasOpenOrScheduledFinalFixPhase) {
            status = SpecReviewStatus.FINAL_FIX_REJECTED;
        }

        return status;
    }

    /**
     * <p>
     * Logs for entrance into every public methods with {@link Level#DEBUG}.
     * </p>
     *
     * @param log
     *            the log
     * @param methodName
     *            the name of method entranced into.
     * @param methodArguments
     *            the method arguments
     */
    private static void logEntrance(Log log, String methodName, String methodArguments) {
        doLog(log, Level.DEBUG, null, ENTRANCE_METHOD_PATTERN, new Object[] {methodName, methodArguments});
    }

    /**
     * <p>
     * Logs for exit from every public methods with {@link Level#DEBUG}.
     * </p>
     *
     * @param log
     *            the log
     * @param methodName
     *            the name of method existed from.
     * @param returnValue
     *            the return value
     * @param durationTime
     *            the duration time
     */
    private static void logExit(Log log, String methodName, Object returnValue, long durationTime) {
        doLog(log, Level.DEBUG, null, EXIT_METHOD_PATTERN, new Object[] {methodName, returnValue, durationTime});
    }

    /**
     * <p>
     * Logs the given <code>Throwable</code> with {@link Level#ERROR}.
     * </p>
     *
     * @param log
     *            the log
     * @param methodName
     *            the name of method where the exception occurs.
     * @param e
     *            the <code>Throwable</code> caught to be logged.
     */
    private static void logException(Log log, String methodName, Throwable e) {
        doLog(log, Level.ERROR, e, EXCEPTION_PATTERN, new Object[] {methodName, e.getMessage()});
    }

    /**
     * <p>
     * Logs the given exception and message with given level.
     * </p>
     *
     * @param log
     *            the log
     * @param level
     *            The log level.
     * @param throwable
     *            A possibly null Throwable to be logged.
     * @param format
     *            The format of the logged message.
     * @param messages
     *            The messages to be logged.
     */
    private static void doLog(Log log, Level level, Throwable throwable, String format, Object[] messages) {
        if (log != null) {
            log.log(level, throwable, format, messages);
        }
    }
}
