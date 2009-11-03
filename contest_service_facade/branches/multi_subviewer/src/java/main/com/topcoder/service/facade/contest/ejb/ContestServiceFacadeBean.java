/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.contest.ejb;

import com.cronos.onlinereview.services.uploads.ConfigurationException;
import com.cronos.onlinereview.services.uploads.UploadExternalServices;
import com.cronos.onlinereview.services.uploads.UploadServicesException;
import com.cronos.onlinereview.services.uploads.impl.DefaultUploadExternalServices;
import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.CompDocumentation;
import com.topcoder.catalog.entity.CompForum;
import com.topcoder.catalog.entity.Phase;
import com.topcoder.catalog.entity.Status;
import com.topcoder.catalog.entity.Technology;
import com.topcoder.catalog.service.AssetDTO;
import com.topcoder.catalog.service.CatalogService;
import com.topcoder.catalog.service.EntityNotFoundException;
import com.topcoder.catalog.service.SearchCriteria;
import com.topcoder.clientcockpit.phases.EmailMessageGenerationException;
import com.topcoder.clientcockpit.phases.EmailMessageGenerator;
import com.topcoder.clientcockpit.phases.EmailSendingException;
import com.topcoder.clientcockpit.phases.messagegenerators.DefaultEmailMessageGenerator;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.ProjectDAO;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.management.project.Project;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.message.email.EmailEngine;
import com.topcoder.message.email.TCSEmailMessage;
import com.topcoder.project.service.ContestSaleData;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.project.service.ProjectServicesException;
import com.topcoder.security.auth.module.UserProfilePrincipal;
import com.topcoder.service.facade.contest.CommonProjectContestData;
import com.topcoder.service.facade.contest.CommonProjectPermissionData;
import com.topcoder.service.facade.contest.ContestPaymentResult;
import com.topcoder.service.facade.contest.ContestServiceException;
import com.topcoder.service.facade.contest.ContestServiceFilter;
import com.topcoder.service.facade.contest.SoftwareContestPaymentResult;
import com.topcoder.service.payment.CreditCardPaymentData;
import com.topcoder.service.payment.PaymentData;
import com.topcoder.service.payment.PaymentException;
import com.topcoder.service.payment.PaymentProcessor;
import com.topcoder.service.payment.PaymentResult;
import com.topcoder.service.payment.PaymentType;
import com.topcoder.service.payment.TCPurhcaseOrderPaymentData;
import com.topcoder.service.payment.paypal.PayflowProPaymentProcessor;
import com.topcoder.service.permission.Permission;
import com.topcoder.service.permission.PermissionService;
import com.topcoder.service.permission.PermissionServiceException;
import com.topcoder.service.permission.PermissionType;
import com.topcoder.service.project.CompetionType;
import com.topcoder.service.project.Competition;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.specreview.SpecReview;
import com.topcoder.service.specreview.SpecReviewService;
import com.topcoder.service.specreview.SpecReviewServiceException;
import com.topcoder.service.specreview.UpdatedSpecSectionData;
import com.topcoder.service.studio.ChangeHistoryData;
import com.topcoder.service.studio.CompletedContestData;
import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.ContestMultiRoundInformationData;
import com.topcoder.service.studio.ContestNotFoundException;
import com.topcoder.service.studio.ContestPaymentData;
import com.topcoder.service.studio.ContestStatusData;
import com.topcoder.service.studio.ContestTypeData;
import com.topcoder.service.studio.DocumentNotFoundException;
import com.topcoder.service.studio.IllegalArgumentWSException;
import com.topcoder.service.studio.MediumData;
import com.topcoder.service.studio.PersistenceException;
import com.topcoder.service.studio.PrizeData;
import com.topcoder.service.studio.ProjectNotFoundException;
import com.topcoder.service.studio.StatusNotAllowedException;
import com.topcoder.service.studio.StatusNotFoundException;
import com.topcoder.service.studio.StudioService;
import com.topcoder.service.studio.SubmissionData;
import com.topcoder.service.studio.SubmissionFeedback;
import com.topcoder.service.studio.SubmissionPaymentData;
import com.topcoder.service.studio.UploadedDocument;
import com.topcoder.service.studio.UserNotAuthorizedException;
import com.topcoder.service.studio.contest.DocumentType;
import com.topcoder.service.studio.contest.SimpleContestData;
import com.topcoder.service.studio.contest.SimpleProjectContestData;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.contest.User;
import com.topcoder.service.user.UserService;
import com.topcoder.service.user.UserServiceException;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.errorhandling.BaseException;
import com.topcoder.util.errorhandling.ExceptionUtils;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.DocumentGeneratorFactory;
import com.topcoder.util.file.Template;
import com.topcoder.web.ejb.forums.Forums;
import com.topcoder.web.ejb.forums.ForumsHome;

import org.jboss.logging.Logger;

import org.jboss.ws.annotation.EndpointConfig;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.HashSet;

import javax.activation.DataHandler;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;

import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import javax.jws.WebService;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>
 * This is an implementation of <code>Contest Service Facade</code> web service
 * in form of stateless session EJB. It holds a reference to
 * {@link StudioService} which is delegated the fulfillment of requests.
 * </p>
 *
 * <p>
 * Module Cockpit Contest Service Enhancement Assembly change: Several new
 * methods related to the permission and permission type are added.
 * </p>
 *
 * <p>
 * Module Cockpit Share Submission Integration Assembly change: Added method to
 * retrieve all permissions by projectId.
 * </p>
 * Version 1.0.1 (Cockpit Release Assembly 5 v1.0) Change Notes:
 *  - Added method to retrieve contest fees by given billing project id.
 * </p>
 * </p>
 * 
 * Version 1.0.2 (Spec Reviews Finishing Touches v1.0) Change Notes:
 *  - Made the getSpecReviews method return instance of SpecReview rather than a list.
 *  - Added the methods to mark ready for review, review done and resubmit for review.
 * </p>
 *
 * <p>
 * Version 1.0.3 (Cockpit Software Contest Payments v1.0) Change notes:
 *  - For software contest, payment is made for the sum of various costs.
 *  - While doing so, only the increased amount is paid (if earlier payments were made).
 *  - Introduced constants for new cost types
 * </p>
 *
 * <p>
 * Version 1.0.4 
 *  - Add 'Applications'/'Components' to resource for project
 * </p>
 *
 * * -----------------------changed in the version 1.1-----------------
 *
 * Four methods are added
 * setSubmissionMilestonePrize(submissionId:long,milestonePrizeId:long):void
 * getUserContests(userName:String):List<StudioCompetition>
 * getMilestoneSubmissionsForContest(contestId:long):List<SubmissionData>
 * getFinalSubmissionsForContest(contestId:long):List<SubmissionData>
 *
 * -----
 *
 * @author snow01, TCS Deveoper, TCS Designer
 * <p>
 * Changes in v1.2 (Studio Multi-Rounds Assembly - Launch Contest): Added default milestone date when contest is
 * created.
 * </p>
 * <p>
 * Changes in v1.2.1 updated to set creator user as Observer
 * created.
 * </p>
 * <p>
 * Changes in v1.2.2 
 * Modified getCommonProjectContestDataByPID and getCommonProjectContestData
 * Handle milestoneDate and submissionEndDate for Cockpit Release Assembly 10 - My Projects v1.0
 * </p>
 * <p>
 * Changes in v1.3 (Prototype Conversion Studio Multi-Rounds Assembly - Submission Viewer UI):
 * - Added a flag to updateSubmissionUserRank method to support ranking milestone submissions.
 * - Added support for milestone prizes payment.
 * </p>
 * 
 * @author snow01, pulky, murphydog
 * @version 1.3
 */
@Stateless
@WebService
@EndpointConfig(configName = "Standard WSSecurity Endpoint")
@DeclareRoles({"Cockpit User"})
@RolesAllowed({"Cockpit User"})
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ContestServiceFacadeBean implements ContestServiceFacadeLocal,
    ContestServiceFacadeRemote {
    /**
     * Private constant specifying non-active not yet published status id.
     */
    private static final long CONTEST_STATUS_UNACTIVE_NOT_YET_PUBLISHED = 1;

    /**
     * Private constant specifying draft status id.
     */
    private static final long CONTEST_DETAILED_STATUS_DRAFT = 15;

    /**
     * Private constant specifying active & public status id.
     */
    private static final long CONTEST_STATUS_ACTIVE_PUBLIC = 2;

    /**
     * Private constant specifying active & public status id.
     */
    private static final long CONTEST_DETAILED_STATUS_ACTIVE_PUBLIC = 2;

    /**
     * Private constant specifying active & public status id.
     */
    private static final long CONTEST_DETAILED_STATUS_SCHEDULED = 9;

    /**
     * Private constant specifying active & public status id.
     */
    private static final long CONTEST_PAYMENT_STATUS_PAID = 1;

    /**
     * Private constant specifying active & public status id.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final long CONTEST_SALE_STATUS_PAID = 1;
    private static final long CONTEST_COMPLETED_STATUS = 8;

    /**
     * Private constant specifying active & public status id.
     */
    private static final long PAYMENT_TYPE_PAYPAL_PAYFLOW = 1;

    /**
     * Private constant specifying active & public status id.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final long SALE_TYPE_PAYPAL_PAYFLOW = 1;

    /**
     * Private constant specifying active & public status id.
     */
    private static final long PAYMENT_TYPE_TC_PURCHASE_ORDER = 2;

    /**
     * Private constant specifying active & public status id.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final long SALE_TYPE_TC_PURCHASE_ORDER = 2;

    /**
     * Private constant specifying payments project info type.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final String PAYMENTS_PROJECT_INFO_TYPE = "Payments";
    
    /**
     * Private constant specifying project type info's component id key.
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String PROJECT_TYPE_INFO_COMPONENT_ID_KEY = "Component ID";

    /**
     * Private constant specifying project type info's version id key.
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String PROJECT_TYPE_INFO_VERSION_ID_KEY = "Version ID";

    /**
     * Private constant specifying project type info's version id key.
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String PROJECT_TYPE_INFO_EXTERNAL_REFERENCE_KEY = "External Reference ID";

    /**
     * Private constant specifying project type info's forum id key.
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String PROJECT_TYPE_INFO_DEVELOPER_FORUM_ID_KEY = "Developer Forum ID";

    /**
     * Private constant specifying project type info's SVN Module key.
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String PROJECT_TYPE_INFO_SVN_MODULE_KEY = "SVN Module";

    /**
     * Private constant specifying project type info's Notes key.
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String PROJECT_TYPE_INFO_NOTES_KEY = "Notes";

    /**
     * Private constant specifying project type info's project name key.
     *
     * @since Cockpit Release Assembly for Receipts
     */
    private static final String PROJECT_TYPE_INFO_PROJECT_NAME_KEY = "Project Name";

    /**
     * Private constant specifying resource role manager id
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final long RESOURCE_ROLE_MANAGER_ID = 13;

    /**
     * Private constant specifying resource role manager name
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String RESOURCE_ROLE_MANAGER_NAME = "Manger";

    /**
     * Private constant specifying resource role manager desc
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String RESOURCE_ROLE_MANAGER_DESC = "Manger";


    /**
     * Private constant specifying resource role manager id
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final long RESOURCE_ROLE_OBSERVER_ID = 12;

    /**
     * Private constant specifying resource role manager name
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String RESOURCE_ROLE_OBSERVER_NAME = "Observer";

    /**
     * Private constant specifying resource role manager desc
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String RESOURCE_ROLE_OBSERVER_DESC = "Observer";

    /**
     * Private constant specifying resource ext ref id
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String RESOURCE_INFO_EXTERNAL_REFERENCE_ID = "External Reference ID";


    /**
     * Private constant specifying resource handle
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String RESOURCE_INFO_HANDLE = "Handle";

    /**
     * Private constant specifying resource handle
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String RESOURCE_INFO_HANDLE_APPLICATIONS = "Applications";

    /**
     * Private constant specifying resource handle
     *
     * @since 1.0.4
     */
    private static final String RESOURCE_INFO_HANDLE_COMPONENTS = "Components";

      /**
     * Represents the project category id for development contests.
     * 
     * @since 1.0.1
     */
    private static final int DEVELOPMENT_PROJECT_CATEGORY_ID = 2;


    /**
     * Represents the project category id for development contests.
     * 
     * @since 1.0.4
     */
    private static final int DESIGN_PROJECT_CATEGORY_ID = 1;


	/**
     * Private constant specifying resource pay
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String RESOURCE_INFO_PAYMENT_STATUS = "Payment Status";

    /**
     * Private constant specifying resource pay
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String RESOURCE_INFO_PAYMENT_STATUS_NA = "N/A";

    /**
     * Email file template source key that is used by email generator.
     */
    private static final String EMAIL_FILE_TEMPLATE_SOURCE_KEY = "fileTemplateSource";


     /**
     * Private constant specifying administrator role.
     */
    private static final String TC_STAFF_ROLE = "TC Staff";

    /**
     * Private constant specifying cost for first place.
     *
     * @since 1.0.3
     */
	private static final String FIRST_PLACE_COST_PROJECT_INFO_TYPE = "First Place Cost";
	
	/**
     * Private constant specifying cost for second place.
     *
     * @since 1.0.3
     */
	private static final String SECOND_PLACE_COST_PROJECT_INFO_TYPE = "Second Place Cost";
	
	/**
     * Private constant specifying cost for reliability bonus.
     *
     * @since 1.0.3
     */
	private static final String RELIABILITY_BONUS_COST_PROJECT_INFO_TYPE = "Reliability Bonus Cost";
	
	/**
     * Private constant specifying cost for milestone bonus
     *
     * @since 1.0.3
     */
	private static final String MILESTONE_BONUS_COST_PROJECT_INFO_TYPE = "Milestone Bonus Cost";
	
	/**
     * Private constant specifying cost for admin fee.
     */
	private static final String ADMIN_FEE_PROJECT_INFO_TYPE = "Admin Fee";
	
	/**
     * Private constant specifying cost for review cost.
     *
     * @since 1.0.3
     */
	private static final String REVIEW_COST_PROJECT_INFO_TYPE = "Review Cost";
	
	/**
     * Private constant specifying cost for dr point cost.
     *
     * @since 1.0.3
     */
	private static final String DR_POINT_COST_PROJECT_INFO_TYPE = "DR points";


    /**
     * <p>
     * A <code>StudioService</code> providing access to available
     * <code>Studio Service EJB</code>. This bean is delegated to process the
     * calls to the methods inherited from <code>Studio Service</code>
     * component.
     * </p>
     */
    @EJB(name = "ejb/StudioService")
    private StudioService studioService = null;

    /**
     * <p>
     * A <code>CatalogService</code> providing access to available
     * <code>Category Services EJB</code>. This bean is delegated to process the
     * calls to the methods inherited from <code>Category Services</code>
     * component.
     * </p>
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    @EJB(name = "ejb/CatalogService")
    private CatalogService catalogService = null;

    /**
     * <p>
     * A <code>ProjectServices</code> providing access to available
     * <code>Project Services EJB</code>. This bean is delegated to process the
     * calls to the methods inherited from <code>Project Services</code>
     * component.
     * </p>
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    @EJB(name = "ejb/ProjectServicesBean")
    private ProjectServices projectServices = null;

    /**
     * <p>
     * A <code>PermissionService</code> providing access to available
     * <code>Permission Service EJB</code>. This bean is delegated to process
     * the calls for CRUD on permissions.
     * </p>
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    @EJB(name = "ejb/PermissionService")
    private PermissionService permissionService = null;

    /**
     * <p>
     * A <code>UserService</code> providing access to available
     * <code>User Service EJB</code>.
     * </p>
     *
     * @since Cockpit Release Assembly for Receipts
     */
    @EJB(name = "ejb/UserService")
    private UserService userService = null;

	/**
     * <p>A <code>SpecReviewService</code> providing access to available <code>Spec Review Service</code>. This bean is
     * delegated to process the calls for CRUD spec reviews.</p>
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    @EJB(name = "ejb/SpecReviewService")
    private SpecReviewService specReviewService = null;
    
    /**
     * <p>A <code>ProjectDAO</code> providing access to available billing project related methods like retrieving 
     * contest fee for given billing project.</p> 
     *
     * @since 1.0.1
     */
    @EJB(name = "ejb/ProjectDAOBean")
    private ProjectDAO billingProjectDAO = null;

    /**
     * PayPal API UserName
     */
    @Resource(name = "payPalApiUserName")
    private String apiUserName;

    /**
     * PayPal API Password
     */
    @Resource(name = "payPalApiPassword")
    private String apiPassword;

    /**
     * PayPal API Signature.
     */
    @Resource(name = "payPalApiSignature")
    private String apiSignature;

    /**
     * PayPal API Environment
     */
    @Resource(name = "payPalApiEnvironment")
    private String apiEnvironment;

    /**
     * A flag indicating whether or not create the forum. It's injected, used in
     * the createSoftwareContest method. In the old version, this variable
     * misses the document, it's added in the version 1.1
     */
    @Resource(name = "createForum")
    private boolean createForum = false;

    /**
     * forumBeanProviderUrl is used in the jndi context to get the forum bean in
     * the createForum method. It's injected, non-null and non-empty after set.
     * In the old version, this variable misses the document, it's added in the
     * version 1.1
     */
    @Resource(name = "forumBeanProviderUrl")
    private String forumBeanProviderUrl;

    /**
     * <p>
     * A <code>PaymentProcessor</code> instance of payment processor
     * implementing class. All payment requests are processed through this
     * instance.
     * </p>
     */
    private PaymentProcessor paymentProcessor = null;

    /**
     * <p>
     * A <code>UploadExternalServices</code> instance of Online Review Upload
     * Services to expose its methods.
     * </p>
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    private UploadExternalServices uploadExternalServices = null;


    /**
     * <p>
     * Represents the sessionContext of the ejb.
     * </p>
     */
    @Resource
    private SessionContext sessionContext;

    /**
     * Host address. Use pilot-payflowpro.paypal.com for testing and
     * payflowpro.paypal.com for production.
     *
     * @since BUGR-1239
     */
    @Resource(name = "payFlowHostAddress")
    private String payFlowHostAddress;

    /**
     * PayFlow username.
     *
     * @since BUGR-1239
     */
    @Resource(name = "payFlowUser")
    private String payFlowUser;

    /**
     * PayFlow partner name.
     *
     * @since BUGR-1239
     */
    @Resource(name = "payFlowPartner")
    private String payFlowPartner;

    /**
     * PayFlow vendor name.
     *
     * @since BUGR-1239
     */
    @Resource(name = "payFlowVendor")
    private String payFlowVendor;

    /**
     * PayFlow password.
     *
     * @since BUGR-1239
     */
    @Resource(name = "payFlowPassword")
    private String payFlowPassword;

    /**
     * Document manager config file location.
     *
     * @since Cockpit Release Assembly for Receipts
     */
    @Resource(name = "documentManagerConfigFile")
    private String documentManagerConfigFile;

    /**
     * Email template file path for Activate Contest Receipt.
     *
     * @since Cockpit Release Assembly for Receipts
     */
    @Resource(name = "activateContestReceiptEmailTemplatePath")
    private String activateContestReceiptEmailTemplatePath;

    /**
     * BCC Address for Activate Contest Receipt Email
     *
     * @since Cockpit Release Assembly for Receipts
     */
    @Resource(name = "activateContestReceiptEmailBCCAddr")
    private String activateContestReceiptEmailBCCAddr;

    /**
     * From Address for Activate Contest Receipt Email
     *
     * @since Cockpit Release Assembly for Receipts
     */
    @Resource(name = "activateContestReceiptEmailFromAddr")
    private String activateContestReceiptEmailFromAddr;

    /**
     * Subject line for Activate Contest Receipt Email
     *
     * @since Cockpit Release Assembly for Receipts
     */
    @Resource(name = "activateContestReceiptEmailSubject")
    private String activateContestReceiptEmailSubject;

    /**
     * Email template file path for Purchase Submission Receipt Email.
     *
     * @since Cockpit Release Assembly for Receipts
     */
    @Resource(name = "purchaseSubmissionReceiptEmailTemplatePath")
    private String purchaseSubmissionReceiptEmailTemplatePath;

    /**
     * BCC Address for Purchase Submission Receipt Email
     *
     * @since Cockpit Release Assembly for Receipts
     */
    @Resource(name = "purchaseSubmissionReceiptEmailBCCAddr")
    private String purchaseSubmissionReceiptEmailBCCAddr;

    /**
     * From Address for Purchase Submission Receipt Email
     *
     * @since Cockpit Release Assembly for Receipts
     */
    @Resource(name = "purchaseSubmissionReceiptEmailFromAddr")
    private String purchaseSubmissionReceiptEmailFromAddr;

    /**
     * Subject line for Purchase Submission Receipt Email
     *
     * @since Cockpit Release Assembly for Receipts
     */
    @Resource(name = "purchaseSubmissionReceiptEmailSubject")
    private String purchaseSubmissionReceiptEmailSubject;

    /**
     * Email template file path for Spec Review Notification Email
     * 
     * @since 1.0.2
     */
    @Resource(name = "specReviewNotificationEmailTemplatePath")
    private String specReviewNotificationEmailTemplatePath;
    
    /**
     * BCC Address for Spec Review Notification Email
     * 
     * @since 1.0.2
     */
    @Resource(name = "specReviewNotificationEmailBCCAddr")
    private String specReviewNotificationEmailBCCAddr;
    
    /**
     * From Address for Spec Review Notification Email
     * 
     * @since 1.0.2
     */
    @Resource(name = "specReviewNotificationEmailFromAddr")
    private String specReviewNotificationEmailFromAddr;
    
    /**
     * Subject line for Spec Review Notification Email
     * 
     * @since 1.0.2
     */
    @Resource(name = "specReviewNotificationEmailSubject")
    private String specReviewNotificationEmailSubject;
    
    /**
     * Document generator that stores email templates.
     *
     * @since Cockpit Release Assembly for Receipts
     */
    private DocumentGenerator documentGenerator;

    /**
     * Email generator that generates email message from given template.
     *
     * @since Cockpit Release Assembly for Receipts
     */
    private EmailMessageGenerator emailMessageGenerator;

      
    /**
     * user id for Applications
     * 
     * @since 1.0.4
     */
    private long applications_user_id;


     /**
     * user id for Components
     * 
     * @since 1.0.4
     */
    private long components_user_id;

    /**
     * The logger instance for logging the information in
     * ContestServiceFacadeBean.
     *
     * @since 1.1
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * <p>
     * Constructs new <code>ContestServiceFacadeBean</code> instance. This
     * implementation instantiates new instance of payment processor. Current
     * implementation just support processing through PayPalCreditCard. When
     * multiple processors are desired the implementation should use factory
     * design pattern to get the right instance of the payment processor.
     * </p>
     *
     * @throws PaymentException
     *             exception when instantiating PaymentProcessor.
     *             PaymentProcessor usually do merchant authentication etc at
     *             initialization time, if this fails it is thrown as exception.
     */
    public ContestServiceFacadeBean() throws PaymentException {
    }

    /**
     * <p>
     * This initializes the API Profile to the <code>CallerServices</code>. The
     * API profile are the merchant's (in this case TopCoder) PayPal API
     * details.
     * </p>
     *
     * <p>
     * TopCoder Service Layer Integration 3 Assembly change: new instance of the
     * DefaultUploadServices for exposing its methods.
     * </p>
     *
     * <p>
     * Updated for Cockpit Release Assembly for Receipts - documentGenerator and
     * emailMessageGenerator instance created.
     * </p>
     *
     * @throws IllegalStateException
     *             it throws this exception on any issues during caller services
     *             initialization. Issues can be: wrong authentication
     *             information, invalid information etc.
     */
    @PostConstruct
    public void init() {
        if (logger == null) {
            logger = Logger.getLogger(this.getClass());
        }

        logger.debug("Initializing PayflowProPaymentProcessor");
        // BUGR-1239
        paymentProcessor = new PayflowProPaymentProcessor(payFlowHostAddress,
                payFlowUser, payFlowPartner, payFlowVendor, payFlowPassword);

        // TopCoder Service Layer Integration 3 Assembly
        try {
            uploadExternalServices = new DefaultUploadExternalServices();
        } catch (ConfigurationException e) {
            throw new IllegalStateException("Failed to create the DefaultUploadExternalServices instance.",
                e);
        }

        try {
            documentGenerator = getDocumentGenerator();
        } catch (PersistenceException e) {
            throw new IllegalStateException("Failed to create the documentGenerator instance.",
                e);
        }

        // the default email message generator.
        emailMessageGenerator = new DefaultEmailMessageGenerator();

        
        try
        {
            components_user_id = userService.getUserId(RESOURCE_INFO_HANDLE_COMPONENTS);

            applications_user_id = userService.getUserId(RESOURCE_INFO_HANDLE_APPLICATIONS);
        }
        catch (UserServiceException e) {
			throw new IllegalStateException("Failed to get components/applications user id.", e);
		}
       
    	
    }

    /**
     * Creates new instance of DocumentGenerator
     *
     * @return the new instance of DocumentGenerator
     * @throws PersistenceException
     *             if any error during instance creation.
     *
     * @since Cockpit Release Assembly for Receipts
     */
    private DocumentGenerator getDocumentGenerator()
        throws PersistenceException {
        try {
            ConfigurationFileManager cfManager = new ConfigurationFileManager(documentManagerConfigFile);

            String docGenNamespace = DocumentGenerator.class.getPackage()
                                                            .getName();
            ConfigurationObject confObj = cfManager.getConfiguration(docGenNamespace)
                                                   .getChild(docGenNamespace);

            return DocumentGeneratorFactory.getDocumentGenerator(confObj);
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage(), e,
                "Error in creating document generator instance");
        }
    }

    /**
     * <p>
     * Creates new contest for specified project. Upon creation an unique ID is
     * generated and assigned to returned contest.
     * </p>
     *
     * @param contest
     *            a <code>StudioCompetition</code> providing the data for new
     *            contest to be created.
     * @param tcDirectProjectId
     *            a <code>long</code> providing the ID of a project the new
     *            competition belongs to.
     * @return a <code>StudioCompetition</code> providing the data for created
     *         contest and having the ID auto-generated.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException
     *             if specified <code>competition</code> is <code>null</code> or
     *             if the specified <code>tcDirectProjectId</code> is negative.
     */
    public StudioCompetition createContest(StudioCompetition contest,
        long tcDirectProjectId) throws PersistenceException {
        logger.debug("createContest");

        ContestData contestData = convertToContestData(contest);

        // contestData.setStatusId(CONTEST_STATUS_UNACTIVE_NOT_YET_PUBLISHED);
        // contestData.setDetailedStatusId(CONTEST_DETAILED_STATUS_DRAFT);
        double total = 0;

        for (PrizeData prize : contestData.getPrizes()) {
            total += prize.getAmount();
        }

        // contestData.setContestAdministrationFee(total*0.2);
        // contestData.setDrPoints(total*0.1);
        if (contestData.getLaunchDateAndTime() == null) { // BUGR-1445
                                                          /*
             * - start: current time + 1 hour (round the minutes up to the
             * nearest 15) - end: start time + 3 days
             */

            GregorianCalendar startDate = new GregorianCalendar();
            startDate.setTime(new Date());
            startDate.add(Calendar.HOUR, 1);

            int m = startDate.get(Calendar.MINUTE);
            startDate.add(Calendar.MINUTE, m + ((15 - (m % 15)) % 15));
            contestData.setLaunchDateAndTime(getXMLGregorianCalendar(
                    startDate.getTime()));
            contestData.setDurationInHours(24 * 3); // 3 days
        }

        // BUGR-1088
        Date startDate = getDate(contestData.getLaunchDateAndTime());
        Date endDate = new Date((long) (startDate.getTime() +
                (60L * 60 * 1000 * contestData.getDurationInHours())));
        Date winnerAnnouncementDeadlineDate;

        if (contestData.getDurationInHours() <= 24) {
            winnerAnnouncementDeadlineDate = new Date((long) (endDate.getTime() +
                    (60L * 60 * 1000 * 24)));
        } else {
            winnerAnnouncementDeadlineDate = new Date((long) (endDate.getTime() +
                    (60L * 60 * 1000 * contestData.getDurationInHours())));
        }

        if (contestData.isMultiRound()) {
            if (contestData.getMultiRoundData() == null) {
            	ContestMultiRoundInformationData multiRoundData = new ContestMultiRoundInformationData();
            	multiRoundData.setMilestoneDate(getXMLGregorianCalendar(new Date((startDate.getTime() + 
            	    endDate.getTime())/2)));
            	
            	contestData.setMultiRoundData(multiRoundData);
            } else if (contestData.getMultiRoundData().getMilestoneDate() == null) {
            	contestData.getMultiRoundData().setMilestoneDate(getXMLGregorianCalendar(new Date((startDate.getTime() + 
            	    endDate.getTime())/2)));
            }
        }

        contestData.setWinnerAnnoucementDeadline(getXMLGregorianCalendar(
                winnerAnnouncementDeadlineDate));

        ContestData createdContestData = this.studioService.createContest(contestData,
                tcDirectProjectId);
        logger.debug("Exit createContest");

        return (StudioCompetition) convertToCompetition(CompetionType.STUDIO,
            createdContestData);
    }

    /**
     * <p>
     * Gets the contest referenced by the specified ID.
     * </p>
     *
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to get
     *            details for.
     * @return a <code>StudioCompetition</code> providing the data for the
     *         requested contest.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws ContestNotFoundException
     *             if requested contest is not found.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException
     *             if specified <code>contestId</code> is negative.
     */
    public StudioCompetition getContest(long contestId)
        throws PersistenceException, ContestNotFoundException {
        logger.debug("getContest (" + contestId + ")");

        ContestData studioContest = this.studioService.getContest(contestId);

        // BUGR-1363
        StudioCompetition competition = (StudioCompetition) convertToCompetition(CompetionType.STUDIO,
                studioContest);
        competition.getContestData().setPayments(getContestPayments(contestId));
        logger.info("for contest " + contestId + ", payments #: " +
            competition.getContestData().getPayments().size());
        logger.debug("Exit getContest (" + contestId + ")");

        return competition;
    }

    /**
     * <p>
     * Gets the contests for the given project.
     * </p>
     *
     * @param tcDirectProjectId
     *            a <code>long</code> providing the ID of a project to get the
     *            list of associated contests for.
     * @return a <code>List</code> providing the contests for specified project.
     *         Empty list is returned if there are no such contests found.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws ProjectNotFoundException
     *             if requested project is not found.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException
     *             if specified <code>tcDirectProjectId</code> is negative.
     */
    public List<StudioCompetition> getContestsForProject(long tcDirectProjectId)
        throws PersistenceException, ProjectNotFoundException {
        logger.debug("getContestFroPorject (" + tcDirectProjectId + ")");

        List<ContestData> studioContests = this.studioService.getContestsForProject(tcDirectProjectId);
        logger.debug("Exit getContestFroPorject (" + tcDirectProjectId + ")");

        return convertToCompetitions(CompetionType.STUDIO, studioContests);
    }

    /**
     * <p>
     * Updates the specified contest.
     * </p>
     *
     * @param contest
     *            a <code>StudioCompetition</code> providing the contest data to
     *            update.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws ContestNotFoundException
     *             if requested contest is not found.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException
     *             if specified <code>contest</code> is <code>null</code>.
     */
    public void updateContest(StudioCompetition contest)
        throws PersistenceException, ContestNotFoundException {
        logger.debug("updateContest (" + contest.getId() + ")");

        ContestData studioContest = convertToContestData(contest);

        // BUGR-1363
        double total = 0;

        for (PrizeData prize : studioContest.getPrizes()) {
            total += prize.getAmount();
        }

        // studioContest.setContestAdministrationFee(total * 0.2);
        // studioContest.setDrPoints(total * 0.1);
        Date startDate = getDate(studioContest.getLaunchDateAndTime());
        Date endDate = new Date((long) (startDate.getTime() +
                (60L * 60 * 1000 * studioContest.getDurationInHours())));
        Date winnerAnnouncementDeadlineDate;

        if (studioContest.getDurationInHours() <= 24) {
            winnerAnnouncementDeadlineDate = new Date((long) (endDate.getTime() +
                    (60L * 60 * 1000 * 24)));
        } else {
            winnerAnnouncementDeadlineDate = new Date((long) (endDate.getTime() +
                    (60L * 60 * 1000 * studioContest.getDurationInHours())));
        }

        studioContest.setWinnerAnnoucementDeadline(getXMLGregorianCalendar(
                winnerAnnouncementDeadlineDate));

        this.studioService.updateContest(studioContest);
        logger.debug("Exit updateContest (" + contest.getId() + ")");
    }

    /**
     * <p>
     * Sets the status of contest referenced by the specified ID to specified
     * value.
     * </p>
     *
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to update
     *            status for.
     * @param newStatusId
     *            a <code>long</code> providing the ID of a new contest status
     *            to set.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws StatusNotAllowedException
     *             if the specified status is not allowed to be set for
     *             specified contest.
     * @throws StatusNotFoundException
     *             if specified status is not found.
     * @throws ContestNotFoundException
     *             if specified contest is not found.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException
     *             if any of specified IDs is negative.
     */
    public void updateContestStatus(long contestId, long newStatusId)
        throws PersistenceException, StatusNotAllowedException,
            StatusNotFoundException, ContestNotFoundException {
        logger.debug("updateContestStatus (" + contestId + "," + newStatusId +
            ")");
        this.studioService.updateContestStatus(contestId, newStatusId);
        logger.debug("Exit updateContestStatus (" + contestId + "," +
            newStatusId + ")");
    }

    /**
     * <p>
     * Uploads the specified document and associates it with assigned contest.
     * </p>
     *
     * @param uploadedDocument
     *            an <code>UploadedDocument</code> providing the data for the
     *            uploaded document.
     * @return an <code>UploadedDocument</code> passed as argument and having
     *         the document ID auto-generated and set.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws ContestNotFoundException
     *             if requested contest is not found.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException
     *             if the argument is <code>null</code>.
     */
    public UploadedDocument uploadDocumentForContest(
        UploadedDocument uploadedDocument)
        throws PersistenceException, ContestNotFoundException {
        logger.debug("uploadDocumentForContest");

        return this.studioService.uploadDocumentForContest(uploadedDocument);
    }

    /**
     * <p>
     * Uploads the specified document without associating it with any contest.
     * </p>
     *
     * @param uploadedDocument
     *            an <code>UploadedDocument</code> providing the data for the
     *            uploaded document.
     * @return an <code>UploadedDocument</code> passed as argument and having
     *         the document ID auto-generated and set.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException
     *             if the argument is <code>null</code>.
     */
    public UploadedDocument uploadDocument(UploadedDocument uploadedDocument)
        throws PersistenceException {
        logger.debug("uploadDocument");

        return this.studioService.uploadDocument(uploadedDocument);
    }

    /**
     * <p>
     * Associates the specified document with specified contest.
     * </p>
     *
     * @param documentId
     *            a <code>long</code> providing the ID of a document to be
     *            associated with specified contest.
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to associate
     *            the specified document with.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws ContestNotFoundException
     *             if any of requested contest or document is not found.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException
     *             if any of specified IDs is negative.
     */
    public void addDocumentToContest(long documentId, long contestId)
        throws PersistenceException, ContestNotFoundException {
        logger.debug("addDocumentToContest (" + documentId + "," + contestId +
            ")");
        this.studioService.addDocumentToContest(documentId, contestId);
        logger.debug("Exit addDocumentToContest (" + documentId + "," +
            contestId + ")");
    }

    /**
     * <p>
     * Removes the specified document from specified contest.
     * </p>
     *
     * @param document
     *            an <code>UploadedDocument</code> representing the document to
     *            be removed.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws DocumentNotFoundException
     *             if the specified document is not found.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException
     *             if the specified argument is <code>null</code>.
     */
    public void removeDocumentFromContest(UploadedDocument document)
        throws PersistenceException, DocumentNotFoundException {
        logger.debug("removeDocumentToContest (" + document.getDocumentId() +
            ")");
        this.studioService.removeDocumentFromContest(document);
        logger.debug("Exit removeDocumentToContest (" +
            document.getDocumentId() + ")");
    }

    /**
     * <p>
     * Retrieves the list of submissions for the specified contest.
     * </p>
     *
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to get the
     *            list of submissions for.
     * @return a <code>List</code> providing the details for the submissions
     *         associated with the specified contest. Empty list is returned if
     *         there are no submissions found.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws ContestNotFoundException
     *             if requested contest is not found.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException
     *             if the specified ID is negative.
     */
    public List<SubmissionData> retrieveSubmissionsForContest(long contestId)
        throws PersistenceException, ContestNotFoundException {
        logger.debug("retrieveSubmissionsForContest (" + contestId + ")");

        return this.studioService.retrieveSubmissionsForContest(contestId);
    }

    /**
     * <p>
     * Retrieves the list of submissions for the specified user.
     * </p>
     *
     * @param userId
     *            a <code>long</code> providing the ID of a user to get the list
     *            of submissions for.
     * @return a <code>List</code> providing the details for the submissions
     *         associated with the specified user. Empty list is returned if
     *         there are no submissions found.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException
     *             if the specified ID is negative.
     */
    public List<SubmissionData> retrieveAllSubmissionsByMember(long userId)
        throws PersistenceException, UserNotAuthorizedException {
        logger.debug("retrieveSubmissionsForContest (" + userId + ")");

        return this.studioService.retrieveAllSubmissionsByMember(userId);
    }

    /**
     * <p>
     * Updates specified submission.
     * </p>
     *
     * @param submission
     *            a <code>SubmissionData</code> providing the data for
     *            submission to be updated.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException
     *             if the specified argument is <code>null</code>.
     */
    public void updateSubmission(SubmissionData submission)
        throws PersistenceException {
        logger.debug("updateSubmission (" + submission.getSubmissionId() + ")");
        this.studioService.updateSubmission(submission);
        logger.debug("Exit updateSubmission (" + submission.getSubmissionId() +
            ")");
    }

    /**
     * <p>
     * Removes specified submission.
     * </p>
     *
     * @param submissionId
     *            a <code>long</code> providing the ID of a submission to
     *            remove.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException
     *             if the <code>submissionId</code> is negative.
     */
    public void removeSubmission(long submissionId) throws PersistenceException {
        logger.debug("removeSubmission (" + submissionId + ")");
        this.studioService.removeSubmission(submissionId);
        logger.debug("Exit removeSubmission (" + submissionId + ")");
    }

    /**
     * <p>
     * Gets existing contest statuses.
     * </p>
     *
     * @return a <code>List</code> listing available contest statuses. Empty
     *         list is returned if there are no statuses.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     */
    public List<ContestStatusData> getStatusList() throws PersistenceException {
        logger.debug("getStatusList");

        return this.studioService.getStatusList();
    }

    /**
     * <p>
     * Gets the list of existing submission types.
     * </p>
     *
     * @return a <code>String</code> providing the comma-separated list of
     *         submission types.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     */
    public String getSubmissionFileTypes() throws PersistenceException {
        logger.debug("getSubmissionFileTypes");

        return this.studioService.getSubmissionFileTypes();
    }

    /**
     * <p>
     * Gets the list of all existing contests.
     * </p>
     *
     * @return a <code>List</code> listing all existing contests. Empty list is
     *         returned if there are no contests found.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
     @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<StudioCompetition> getAllContests() throws PersistenceException {
        List<ContestData> studioContests = this.studioService.getAllContests();
        logger.debug("getAllContests");

        return convertToCompetitions(CompetionType.STUDIO, studioContests);
    }

    /**
     * <p>
     * Gets the list of all existing contests for contest monitor widget.
     * </p>
     *
     * @return a <code>List</code> listing all existing contests. Empty list is
     *         returned if there are no contests found.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
     @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SimpleContestData> getSimpleContestData()
        throws PersistenceException {
        logger.debug("getSimpleContestData");

        return studioService.getSimpleContestData();
    }

    /**
     * <p>
     * Gets the list of all existing contests related to given project for my
     * project widget.
     * </p>
     *
     * @param pid
     *            given project id
     * @return a <code>List</code> listing all existing contests. Empty list is
     *         returned if there are no contests found.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SimpleProjectContestData> getSimpleProjectContestDataByPID(
        long pid) throws PersistenceException {
        logger.debug("getSimpleProjectContestDataByPID");

        return studioService.getSimpleProjectContestData(pid);
    }

    /**
     * <p>
     * Gets the list of all existing contests related to given project for
     * contest monitor widget .
     * </p>
     *
     * @param pid
     *            the given project id
     * @return a <code>List</code> listing all existing contests. Empty list is
     *         returned if there are no contests found.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
     @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SimpleContestData> getSimpleContestDataByPID(long pid)
        throws PersistenceException {
        logger.debug("getSimpleContestDataByPID");

        return studioService.getSimpleContestData(pid);
    }

    /**
     * <p>
     * This is going to fetch all the currently available contests for my
     * project widget.
     * </p>
     *
     * @return the list of all available contents (or empty if none found)
     *
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SimpleContestData> getContestDataOnly()
        throws PersistenceException {
        logger.debug("getContestDataOnly");

        return studioService.getContestDataOnly();
    }

    /**
     * <p>
     * This is going to fetch all the currently available contests for my
     * project widget related to given project.
     * </p>
     *
     * @param pid
     *            given project id
     * @return the list of all available contents (or empty if none found)
     *
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SimpleContestData> getContestDataOnlyByPID(long pid)
        throws PersistenceException {
        logger.debug("getContestDataOnlyByPID");

        return studioService.getContestDataOnly(pid);
    }

    /**
     * <p>
     * Gets the list of all existing contests for my project widget.
     * </p>
     *
     * @return a <code>List</code> listing all existing contests. Empty list is
     *         returned if there are no contests found.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
     @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SimpleProjectContestData> getSimpleProjectContestData()
        throws PersistenceException {
        logger.debug("getSimpleProjectContestData");

        return studioService.getSimpleProjectContestData();
    }

    /**
     * <p>
     * Gets the list of existing contests matching the specified criteria.
     * </p>
     *
     * @param filter
     *            a <code>Filter</code> providing the criteria for searching for
     *            contests.
     * @return a <code>List</code> listing all existing contests matching the
     *         specified criteria. Empty list is returned if there are no
     *         matching contests found.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     * @throws IllegalArgumentException
     *             if specified <code>filter</code> is <code>null</code> or if
     *             it is not supported by implementor.
     */
    public List<StudioCompetition> searchContests(ContestServiceFilter filter)
        throws PersistenceException {
        logger.debug("searchContests");

        List<ContestData> studioContests = this.studioService.searchContests(filter.getFilter());
        logger.debug("Exit searchContests");

        return convertToCompetitions(CompetionType.STUDIO, studioContests);
    }

    /**
     * <p>
     * Gets the list of existing contests associated with the client referenced
     * by the specified ID.
     * </p>
     *
     * @param clientId
     *            a <code>long</code> providing the ID of a client to get the
     *            associated contests for.
     * @return a <code>List</code> providing the contests associated with the
     *         requested client. Empty list is returned if there are no matching
     *         contests found.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
    public List<StudioCompetition> getContestsForClient(long clientId)
        throws PersistenceException {
        logger.debug("getContestsForClient");

        List<ContestData> studioContests = this.studioService.getContestsForClient(clientId);
        logger.debug("Exit getContestsForClient");

        return convertToCompetitions(CompetionType.STUDIO, studioContests);
    }

    /**
     * <p>
     * Gets the submission referenced by the specified ID.
     * </p>
     *
     * @param submissionId
     *            a <code>long</code> providing the ID of a submission to get
     *            details for.
     * @return a <code>SubmissionData</code> providing details for the
     *         submission referenced by the specified ID or <code>null</code> if
     *         such a submission is not found.
     * @throws PersistenceException
     *             if any error occurs during the retrieval.
     */
    public SubmissionData retrieveSubmission(long submissionId)
        throws PersistenceException {
        logger.debug("retrieveSubmission");

        return this.studioService.retrieveSubmissionData(submissionId);
    }

    /**
     * <p>
     * Gets existing contest types.
     * </p>
     *
     * @return a <code>List</code> listing available contest types. Empty list
     *         is returned if there are no types.
     * @throws PersistenceException
     *             if some persistence errors occur.
     */
    public List<ContestTypeData> getAllContestTypes()
        throws PersistenceException {
        logger.debug("getAllContestTypes");

        return this.studioService.getAllContestTypes();
    }

    /**
     * <p>
     * Removes the document referenced by the specified ID.
     * </p>
     *
     * @param documentId
     *            a <code>long</code> providing the ID of a document to remove.
     * @return <code>true</code> if requested document was removed successfully;
     *         <code>false</code> otherwise.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws IllegalArgumentWSException
     *             if specified <code>documentId</code> is negative.
     */
    public boolean removeDocument(long documentId) throws PersistenceException {
        logger.debug("removeDocument");

        return this.studioService.removeDocument(documentId);
    }

    /**
     * <p>
     * Gest the MIME type matching the specified context type.
     * </p>
     *
     * @param contentType
     *            a <code>String</code> providing the content type to get the
     *            matching MIME type for.
     * @return a <code>long</code> providing the ID of MIME type matching the
     *         specified content type.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws IllegalArgumentWSException
     *             if the specified <code>contentType</code> is
     *             <code>null</code> or empty.
     */
    public long getMimeTypeId(String contentType) throws PersistenceException {
        logger.debug("getMimeTypeId");

        return this.studioService.getMimeTypeId(contentType);
    }

    /**
     * <p>
     * Purchases the specified submission. E.g. records a fact that submission
     * referenced by specified ID has been paid
     * </p>
     *
     * @param submissionId
     *            a <code>long</code> providing the ID of a submission which has
     *            been paid for.
     * @param submissionPaymentData
     *            a <code>SubmissionPaymentData</code> providing the data of
     *            successfully purchased submission.
     * @param securityToken
     *            a <code>String</code> providing the security token to be used
     *            for tracking the payment and prevent fraud.
     * @throws PersistenceException
     *             if any error occurs when purchasing submission.
     * @throws IllegalArgumentWSException
     *             if specified <code>submissionId</code> is negative.
     */
    public void purchaseSubmission(long submissionId,
        SubmissionPaymentData submissionPaymentData, String securityToken)
        throws PersistenceException {
        logger.debug("purchaseSubmission");
        this.studioService.purchaseSubmission(submissionId,
            submissionPaymentData, securityToken);
        logger.debug("Exit purchaseSubmission");
    }

    /**
     * <p>
     * Creates a new contest payment. Upon creation an unique ID is generated
     * and assigned to returned payment.
     * </p>
     *
     * @param contestPayment
     *            a <code>ContestPaymentData</code> providing the data for the
     *            contest payment to be created.
     * @param securityToken
     *            a <code>String</code> providing the security token to be used
     *            for tracking the payment and prevent fraud.
     * @return a <code>ContestPaymentData</code> providing the details for
     *         created contest payment and having the ID for contest payment
     *         auto-generated and set.
     * @throws IllegalArgumentException
     *             if any of specified arguments is <code>null</code>.
     */
    public ContestPaymentData createContestPayment(
        ContestPaymentData contestPayment, String securityToken)
        throws PersistenceException {
        logger.debug("createContestPayment");

        return this.studioService.createContestPayment(contestPayment,
            securityToken);
    }

    /**
     * <p>
     * Gets the contest payment referenced by specified contest ID.
     * </p>
     *
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to get
     *            payment details for.
     * @return a <code>ContestPaymentData</code> representing the contest
     *         payment matching the specified ID; or <code>null</code> if there
     *         is no such contest.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
    public List<ContestPaymentData> getContestPayments(long contestId)
        throws PersistenceException {
        logger.debug("getContestPayments");

        return this.studioService.getContestPayments(contestId);
    }

    /**
     * <p>
     * Updates specified contest payment data.
     * </p>
     *
     * @param contestPayment
     *            a <code>ContestPaymentData</code> providing the details for
     *            the contest payment to be updated.
     * @throws PersistenceException
     *             if any error occurs when updating contest.
     * @throws IllegalArgumentException
     *             if the specified argument is <code>null</code>.
     */
    public void editContestPayment(ContestPaymentData contestPayment)
        throws PersistenceException {
        logger.debug("editContestPayments");
        this.studioService.editContestPayment(contestPayment);
    }

    /**
     * <p>
     * Removes the contest payment referenced by the specified contest ID.
     * </p>
     *
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to remove
     *            payment details for.
     * @return <code>true</code> if requested contest payment was removed
     *         successfully; <code>false</code> otherwise.
     * @throws PersistenceException
     *             if any error occurs when removing contest.
     */
    public boolean removeContestPayment(long contestId)
        throws PersistenceException {
        logger.debug("removeContestPayments");

        return this.studioService.removeContestPayment(contestId);
    }

    /**
     * <p>
     * Gets existing medium types.
     * </p>
     *
     * @return a <code>List</code> listing available medium types. Empty list is
     *         returned if there are no types.
     * @throws PersistenceException
     *             if some persistence errors occur.
     */
    public List<MediumData> getAllMediums() throws PersistenceException {
        logger.debug("getAllMediums");

        return this.studioService.getAllMediums();
    }

    /**
     * <p>
     * Sets the placement for the specified submission.
     * </p>
     *
     * @param submissionId
     *            a <code>long</code> providing the ID of a submission to set
     *            the placement for.
     * @param placement
     *            an <code>int</code> providing the submission placement.
     * @throws PersistenceException
     *             if any error occurs when setting placement.
     */
    public void setSubmissionPlacement(long submissionId, int placement)
        throws PersistenceException {
        logger.debug("setSubmissionPlacement");
        this.studioService.setSubmissionPlacement(submissionId, placement);
        logger.debug("Exit setSubmissionPlacement");
    }

    /**
     * <p>
     * Associates the specified submission with the specified prize.
     * </p>
     *
     * @param submissionId
     *            a <code>long</code> providing the ID of a submission.
     * @param prizeId
     *            a <code>long</code> providing the ID of a prize.
     * @throws PersistenceException
     *             if any error occurs when setting submission prize.
     */
    public void setSubmissionPrize(long submissionId, long prizeId)
        throws PersistenceException {
        logger.debug("setSubmissionPrize");
        this.studioService.setSubmissionPlacement(submissionId, prizeId);
        logger.debug("Exit setSubmissionPrize");
    }

    /**
     * <p>
     * Marks the specified submission for purchse.
     * </p>
     *
     * @param submissionId
     *            a <code>long</code> providing the ID of a submission to be
     *            marked for purchase.
     * @throws PersistenceException
     *             if any error occurs when marking for purchase.
     */
    public void markForPurchase(long submissionId) throws PersistenceException {
        logger.debug("markForPurchase");
        this.studioService.markForPurchase(submissionId);
        logger.debug("Exit markForPurchase");
    }

    /**
     * <p>
     * Adss the specified list of history data for the associated contest.
     * </p>
     *
     * @param history
     *            a <code>List</code> of history data for a contest.
     * @throws PersistenceException
     *             if any other error occurs.
     */
    public void addChangeHistory(List<ChangeHistoryData> history)
        throws PersistenceException {
        logger.debug("adChangeHistory");
        this.studioService.addChangeHistory(history);
        logger.debug("Exit adChangeHistory");
    }

    /**
     * <p>
     * Gets the history data for the specified contest.
     * </p>
     *
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to get
     *            history data for.
     * @return a <code>List</code> of history data for specified contest.
     * @throws PersistenceException
     *             if any other error occurs.
     */
    public List<ChangeHistoryData> getChangeHistory(long contestId)
        throws PersistenceException {
        logger.debug("getChangeHistory");

        return this.studioService.getChangeHistory(contestId);
    }

    /**
     * <p>
     * Gets the most history data for the most recent changes to specified
     * contest.
     * </p>
     *
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to get
     *            history data for.
     * @return a <code>List</code> of history data for the most recent change
     *         for specified contest.
     * @throws PersistenceException
     *             if any other error occurs.
     */
    public List<ChangeHistoryData> getLatestChanges(long contestId)
        throws PersistenceException {
        logger.debug("getLatestChanges");

        return this.studioService.getLatestChanges(contestId);
    }

    /**
     * <p>
     * Deletes the contest referenced by the specified ID.
     * </p>
     *
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to delete.
     * @throws PersistenceException
     *             if any other error occurs.
     */
    public void deleteContest(long contestId) throws PersistenceException {
        logger.debug("deleteContest");
        this.studioService.deleteContest(contestId);
        logger.debug("Exit deleteContest");
    }

    /**
     * <p>
     * Gets the list of all existing contests.
     * </p>
     *
     * @return a <code>List</code> listing all existing contests. Empty list is
     *         returned if there are no contests found.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
    public List<StudioCompetition> getAllContestHeaders()
        throws PersistenceException {
        logger.debug("getAllContestHeaders");

        List<ContestData> studioContests = this.studioService.getAllContestHeaders();
        logger.debug("Exit getAllContestHeaders");

        return convertToCompetitions(CompetionType.STUDIO, studioContests);
    }

    /**
     * <p>
     * Sends payments to <code>PACTS</code> application for all unpaid
     * submussions with a prize already assigned. This service is not atomic. If
     * it fails, you'll have to check what payments where actually done trough
     * the <code>submussion.paid</code> flag.
     * </p>
     *
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to process
     *            missing payments for.
     * @throws PersistenceException
     *             if any error occurs when processing a payment.
     */
    public void processMissingPayments(long contestId)
        throws PersistenceException {
        logger.debug("processMissingPayments");
        this.studioService.processMissingPayments(contestId);
        logger.debug("Exit processMissingPayments");
    }

    /**
     * <p>
     * Gets all studio file types to return. If no studio file type exists,
     * return an empty list
     * </p>
     *
     * @return a list of studio file types
     * @throws PersistenceException
     *             if any error occurs when getting studio file types.
     */
    public List<StudioFileType> getAllStudioFileTypes()
        throws PersistenceException {
        logger.debug("getAllStudioFileTYpes");

        return this.studioService.getAllStudioFileTypes();
    }

    /**
     * <p>
     * Get all the DocumentType objects.
     * </p>
     *
     * @return the list of all available DocumentType
     *
     * @throws PersistenceException
     *             if any error occurs when getting contest
     *
     * @since 1.1.2
     */
    public List<DocumentType> getAllDocumentTypes() throws PersistenceException {
        logger.debug("getAllDocumentTypes");

        return this.studioService.getAllDocumentTypes();
    }

    /**
     * <p>
     * This method retrieve all the permissions that the user owned for any
     * projects. Returns empty list if no permission found.
     * </p>
     *
     * @param userid
     *            user id to look for
     *
     * @return all the permissions that the user owned for any projects.
     *
     * @throws IllegalArgumentWSException
     *             if the argument is invalid
     * @throws PermissionServiceException
     *             if any error occurs when getting permissions.
     *
     * @since Module Cockpit Contest Service Enhancement Assembly
     */
    public List<Permission> getPermissionsByUser(long userid)
        throws PermissionServiceException {
        logger.debug("getPermissionsByUser");

        return this.permissionService.getPermissionsByUser(userid);
    }

    /**
     * <p>
     * This method retrieve all the permissions that various users own for a
     * given project. Returns empty list if no permission found.
     * </p>
     *
     * @param projectid
     *            project id to look for
     *
     * @return all the permissions that various users own for a given project.
     *
     * @throws IllegalArgumentWSException
     *             if the argument is invalid
     * @throws PermissionServiceException
     *             if any error occurs when getting permissions.
     *
     * @since Cockpit Share Submission Integration
     */
    public List<Permission> getPermissionsByProject(long projectid)
        throws PermissionServiceException {
        logger.debug("getPermissionsByProject");

        return this.permissionService.getPermissionsByProject(projectid);
    }

    /**
     * <p>
     * This method retrieve all the permissions that the user own for a given
     * project. Returns empty list if no permission found.
     * </p>
     *
     * @param userid
     *            user id to look for
     * @param projectid
     *            project id to look for
     *
     * @return all the permissions that the user own for a given project.
     *
     * @throws IllegalArgumentWSException
     *             if the argument is invalid
     * @throws PermissionServiceException
     *             if any error occurs when getting permissions.
     *
     * @since Module Cockpit Contest Service Enhancement Assembly
     */
    public List<Permission> getPermissions(long userid, long projectid)
        throws PermissionServiceException {
        logger.debug("getPermissions");

        return this.permissionService.getPermissions(userid, projectid);
    }

    /**
     * <p>
     * This method retrieve all the permission types.
     * </p>
     *
     * @return all the permission types.
     *
     * @throws IllegalArgumentWSException
     *             if the argument is invalid
     * @throws PermissionServiceException
     *             if any error occurs when getting permission types.
     *
     * @since Module Cockpit Contest Service Enhancement Assembly
     */
    public List<PermissionType> getAllPermissionType()
        throws PermissionServiceException {
        logger.debug("getAllPermissionType");

        return this.permissionService.getAllPermissionType();
    }

    /**
     * <p>
     * This method will add a permission type, and return the added type entity.
     * </p>
     *
     * @param type
     *            the permission type to add.
     *
     * @return the added permission type entity
     *
     * @throws IllegalArgumentWSException
     *             if the argument is invalid
     * @throws PermissionServiceException
     *             if any error occurs when adding the permission type.
     *
     * @since Module Cockpit Contest Service Enhancement Assembly
     */
    public PermissionType addPermissionType(PermissionType type)
        throws PermissionServiceException {
        return this.permissionService.addPermissionType(type);
    }

    /**
     * <p>
     * This method will update permission type data.
     * </p>
     *
     * @param type
     *            the permission type to update.
     *
     * @throws IllegalArgumentWSException
     *             if the argument is invalid
     * @throws PermissionServiceException
     *             if any error occurs when updating the permission type.
     *
     * @since Module Cockpit Contest Service Enhancement Assembly
     */
    public void updatePermissionType(PermissionType type)
        throws PermissionServiceException {
        logger.debug("updatePermissionType");
        this.permissionService.updatePermissionType(type);
        logger.debug("Exit updatePermissionType");
    }

    /**
     * <p>
     * This method will update permission type data, return true if the
     * permission type data exists and removed successfully, return false if it
     * doesn't exist.
     * </p>
     *
     * @param typeid
     *            the permission type to delete.
     *
     * @return true if the permission type data exists and removed successfully.
     *
     * @throws IllegalArgumentWSException
     *             if the argument is invalid
     * @throws PermissionServiceException
     *             if any error occurs when deleting the permission.
     *
     * @since Module Cockpit Contest Service Enhancement Assembly
     */
    public boolean deletePermissionType(long typeid)
        throws PermissionServiceException {
        logger.debug("deletePermissionType");

        return this.permissionService.deletePermissionType(typeid);
    }

    /**
     * <p>
     * Converts the specified <code>ContestData</code> instance to
     * <code>ContestData</code> instance which could be passed from
     * <code>Studio Service</code> to <code>Contest Service Facade</code>.
     * </p>
     *
     * @param type
     *            a <code>CompetionType</code> specifying the type of the
     *            contest.
     * @param contestData
     *            a <code>ContestData</code> instance to be converted.
     * @return a <code>Competition</code> providing the converted data.
     */
    private Competition convertToCompetition(CompetionType type,
        ContestData contestData) {
        if (type == CompetionType.STUDIO) {
            StudioCompetition data = new StudioCompetition(contestData);
            data.setAdminFee(contestData.getContestAdministrationFee());
            data.setCompetitionId(contestData.getContestId());
            data.setId(contestData.getContestId());
            data.setStartTime(contestData.getLaunchDateAndTime());
            data.setEndTime(calculateEndTime(contestData));
            data.setProject(null); // Projects are not retrieved as for now
            data.setType(type);

            return data;
        } else {
            throw new IllegalArgumentWSException("Unsupported contest type",
                "Contest type is not supported: " + type);
        }
    }

    /**
     * <p>
     * Converts the specified <code>StudioCompetition</code> instance to
     * <code>ContestData</code> instance which could be passed to
     * <code>Studio Service</code>.
     * </p>
     *
     * @param contest
     *            a <code>Competition</code> instance to be converted.
     * @return a <code>ContestData</code> providing the converted data.
     */
    private ContestData convertToContestData(Competition contest) {
        if (contest.getType() == CompetionType.STUDIO) {
            StudioCompetition studioContest = (StudioCompetition) contest;

            return studioContest.getContestData();
        } else {
            throw new IllegalArgumentWSException("Unsupported contest type",
                "Contest type is not supported: " + contest.getType());
        }
    }

    /**
     * <p>
     * Calculates the end time for the specified contest.
     * </p>
     *
     * @param contest
     *            a <code>ContestData</code> representing the contest to
     *            calculate the end time for.
     * @return an <code>XMLGregorianCalendar</code> providing the end time for
     *         the specified contest.
     */
    private XMLGregorianCalendar calculateEndTime(ContestData contest) {
        Date startTime = getDate(contest.getLaunchDateAndTime());
        double durationInHours = contest.getDurationInHours();
        Date endTime = new Date(startTime.getTime() +
                (long) (durationInHours * 1000 * 60 * 60));

        return getXMLGregorianCalendar(endTime);
    }

    /**
     * <p>
     * Converts the specified contests to <code>StudioCompetition</code>
     * objects.
     * </p>
     *
     * @param type
     *            a <code>CompetionType</code> referencing the type of contests
     *            to be converted.
     * @param customContests
     *            a <code>List</code> providing the details for contests of
     *            specified type to be converted to
     *            <code>StudioCompetition</code> objects.
     * @return a <code>List</code> providing the converted data for specified
     *         contests.
     */
    private List<StudioCompetition> convertToCompetitions(CompetionType type,
        List<ContestData> customContests) {
        List<StudioCompetition> contests = new ArrayList<StudioCompetition>();

        for (ContestData studioContest : customContests) {
            StudioCompetition contest = (StudioCompetition) convertToCompetition(type,
                    studioContest);
            contests.add(contest);
        }

        return contests;
    }

    /**
     * <p>
     * Converts specified <code>XMLGregorianCalendar</code> instance into
     * <code>Date</code> instance.
     * </p>
     *
     * @param calendar
     *            an <code>XMLGregorianCalendar</code> representing the date to
     *            be converted.
     * @return a <code>Date</code> providing the converted value of specified
     *         calendar or <code>null</code> if specified <code>calendar</code>
     *         is <code>null</code>.
     */
    private Date getDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }

        return calendar.toGregorianCalendar().getTime();
    }

    /**
     * <p>
     * Converts specified <code>Date</code> instance into
     * <code>XMLGregorianCalendar</code> instance.
     * </p>
     *
     * @param date
     *            a <code>Date</code> representing the date to be converted.
     * @return a <code>XMLGregorianCalendar</code> providing the converted value
     *         of specified date or <code>null</code> if specified
     *         <code>date</code> is <code>null</code> or if it can't be
     *         converted to calendar.
     */
    private XMLGregorianCalendar getXMLGregorianCalendar(Date date) {
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
     * Processes the contest payment. It does following steps:
     * <ul>
     * <li>Checks contest id to decide whether to create new contest or update
     * existing contest</li>
     * <li>It processes the payment through <code>PaymentProcessor</code></li>
     * <li>On successful processing -
     * <ul>
     * <li>set contests to CONTEST_STATUS_ACTIVE_PUBLIC = 2</li>
     * <li>set detailed contests to CONTEST_DETAILED_STATUS_ACTIVE_PUBLIC = 2</li>
     * <li>set payment reference number and type</li>
     * <li>Creates new forum for the contest, forum name being contest name. It
     * uses studio service for doing the same.</li>
     * </ul>
     * </li>
     * </ul>
     * </p>
     *
     * @param competition <code>StudioCompetition</code> data that recognizes a contest.
     * @param paymentData <code>PaymentData</code> payment information (credit card/po
     *        details) that need to be processed.
     * @return a <code>PaymentResult</code> result of the payment processing.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     * @throws ContestNotFoundException
     *             if contest is not found while update.
     * @throws IllegalArgumentException
     *             if specified <code>filter</code> is <code>null</code> or if
     *             it is not supported by implementor.
     * @since BUGR-1494 returns ContestPaymentResult instead of PaymentResult
     */
    public ContestPaymentResult processContestCreditCardPayment(
        StudioCompetition competition, CreditCardPaymentData paymentData)
        throws PersistenceException, PaymentException, ContestNotFoundException {
        logger.debug("processContestCreditCardPayment");
        logger.info("StudioCompetition: " + competition);
        logger.info("PaymentData: " + paymentData);
        logger.debug("Exit processContestCreditCardPayment");

        return processContestPaymentInternal(competition, paymentData);
    }

    /**
     * <p>
     * Processes the contest payment. It does following steps:
     * <ul>
     * <li>Checks contest id to decide whether to create new contest or update
     * existing contest</li>
     * <li>Right-now this method doesn't process PO payments.</li>
     * <li>On successful processing -
     * <ul>
     * <li>set contests to CONTEST_STATUS_ACTIVE_PUBLIC = 2</li>
     * <li>set detailed contests to CONTEST_DETAILED_STATUS_ACTIVE_PUBLIC = 2</li>
     * <li>set payment reference number and type</li>
     * <li>Creates new forum for the contest, forum name being contest name. It
     * uses studio service for doing the same.</li>
     * </ul>
     * </li>
     * </ul>
     * </p>
     *
     * @param competition <code>StudioCompetition</code> data that recognizes a contest.
     * @param paymentData <code>PaymentData</code> payment information (credit card/po
     *        details) that need to be processed.
     * @return a <code>PaymentResult</code> result of the payment processing.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     * @throws ContestNotFoundException
     *             if contest is not found while update.
     * @throws IllegalArgumentException
     *             if specified <code>filter</code> is <code>null</code> or if
     *             it is not supported by implementor.
     * @since BUGR-1494 returns ContestPaymentResult instead of PaymentResult
     */
    public ContestPaymentResult processContestPurchaseOrderPayment(
        StudioCompetition competition, TCPurhcaseOrderPaymentData paymentData)
        throws PersistenceException, PaymentException, ContestNotFoundException {
        logger.debug("processContestPurchaseOrderPayment");
        logger.info("StudioCompetition: " + competition);
        logger.info("PaymentData: " + paymentData);

        return processContestPaymentInternal(competition, paymentData);
    }

    /**
     * <p>
     * Processes the contest payment. It does following steps:
     * <ul>
     * <li>Checks contest id to decide whether to create new contest or update
     * existing contest</li>
     * <li>If payment type is credit card then it processes the payment through
     * <code>PaymentProcessor</code></li>
     * <li>Right-now this method doesn't process PO payments.</li>
     * <li>On successful processing -
     * <ul>
     * <li>set contests to CONTEST_STATUS_ACTIVE_PUBLIC = 2</li>
     * <li>set detailed contests to CONTEST_DETAILED_STATUS_ACTIVE_PUBLIC = 2</li>
     * <li>set payment reference number and type</li>
     * <li>Creates new forum for the contest, forum name being contest name. It
     * uses studio service for doing the same.</li>
     * </ul>
     * </li>
     * </ul>
     * </p>
     *
     * <p>
     * Updated for Cockpit Release Assembly for Receipts - Added email
     * notification for payment receipts.
     * </p>
     *
     * @param competition <code>StudioCompetition</code> data that recognizes a contest.
     * @param paymentData <code>PaymentData</code> payment information (credit card/po
     *        details) that need to be processed.
     * @return a <code>PaymentResult</code> result of the payment processing.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     * @throws ContestNotFoundException
     *             if contest is not found while update.
     * @throws IllegalArgumentException
     *             if specified <code>filter</code> is <code>null</code> or if
     *             it is not supported by implementor.
     * @since BUGR-1494 returns ContestPaymentResult instead of PaymentResult
     */
    private ContestPaymentResult processContestPaymentInternal(
        StudioCompetition competition, PaymentData paymentData)
        throws PersistenceException, PaymentException, ContestNotFoundException {
        logger.info("StudioCompetition: " + competition);
        logger.info("PaymentData: " + paymentData);

        ContestPaymentResult contestPaymentResult = null;

        PaymentResult result = null;

        try {
            long contestId = competition.getContestData().getContestId();

            StudioCompetition tobeUpdatedCompetition = null;

            if (contestId >= 0) {
                try {
                    tobeUpdatedCompetition = getContest(contestId);
                } catch (ContestNotFoundException cnfe) {
                    // if not contest is found then simply ignore it.
                }
            }

            if (tobeUpdatedCompetition == null) {
                tobeUpdatedCompetition = createContest(competition,
                        competition.getContestData().getTcDirectProjectId());
            } else {
                tobeUpdatedCompetition = competition;
            }

            // BUGR-1363
            List<ContestPaymentData> payments = tobeUpdatedCompetition.getContestData()
                                                                      .getPayments();
            double paymentAmount;

            // how much user already paid
            double paidFee = 0.0;

            for (ContestPaymentData cpd : payments) {
                paidFee += cpd.getPrice();
            }

            // calculate current contest fee
            double currentFee = competition.getContestData()
                                           .getContestAdministrationFee();
            /*
             * for (PrizeData prize : competition.getContestData().getPrizes())
             * { currentFee += prize.getAmount(); } currentFee *= 0.2;
             */

            // calculate the difference which user has to pay
            paymentAmount = currentFee - paidFee;
            logger.info("extra payment is: " + paymentAmount);

            if (Double.compare(paymentAmount, 0.0) <= 0 && paidFee != 0) {
                throw new PersistenceException("cannot decrease prize amount at this time",
                    "");
            }

            

            if (paymentData instanceof TCPurhcaseOrderPaymentData) {
                // processing purchase order is not in scope of this assembly.
                result = new PaymentResult();
                result.setReferenceNumber(((TCPurhcaseOrderPaymentData) paymentData).getPoNumber());
            } else if (paymentData instanceof CreditCardPaymentData) {
                // ideally client should be sending the amount,
                // but as client has some inconsistency
                // so in this case we would use the amount from contest data.
                CreditCardPaymentData creditCardPaymentData = (CreditCardPaymentData) paymentData;

                creditCardPaymentData.setAmount(Double.toString(paymentAmount)); // BUGR-1363

                if (Double.compare(paidFee, 0.0) > 0) { // BUGR-1363
                    creditCardPaymentData.setComment1(
                        "Contest Fee (Prize Adjustment)");
                } else {
                    // BUGR-1239
                    creditCardPaymentData.setComment1("Contest Fee");
                }

                creditCardPaymentData.setComment2(String.valueOf(
                        tobeUpdatedCompetition.getContestData().getContestId()));
                result = paymentProcessor.process(paymentData);
            }

            tobeUpdatedCompetition.getContestData()
                                  .setStatusId(CONTEST_STATUS_ACTIVE_PUBLIC);
            tobeUpdatedCompetition.getContestData()
                                  .setDetailedStatusId(CONTEST_DETAILED_STATUS_SCHEDULED);

            ContestPaymentData contestPaymentData = new ContestPaymentData();
            contestPaymentData.setPaypalOrderId(result.getReferenceNumber());
            contestPaymentData.setPaymentReferenceId(result.getReferenceNumber());

            if (paymentData instanceof TCPurhcaseOrderPaymentData) {
                contestPaymentData.setPaymentTypeId(PAYMENT_TYPE_TC_PURCHASE_ORDER);
            }
            // TODO, how relate to payflow
            else if (paymentData instanceof CreditCardPaymentData) {
                contestPaymentData.setPaymentTypeId(PAYMENT_TYPE_PAYPAL_PAYFLOW);
            }

            contestPaymentData.setContestId(tobeUpdatedCompetition.getContestData()
                                                                  .getContestId());
            contestPaymentData.setPaymentStatusId(CONTEST_PAYMENT_STATUS_PAID);
            contestPaymentData.setPrice(paymentAmount);

            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            String userId = Long.toString(p.getUserId());

            createContestPayment(contestPaymentData, userId);

            // DONOT create for now
            // create forum for the contest.
            // long forumid =
            // this.studioService.createForum(tobeUpdatedCompetition
            // .getContestData().getName(), p.getUserId());
            // tobeUpdatedCompetition.getContestData().setForumId(forumid);

            // update contest.
            updateContest(tobeUpdatedCompetition);

            // BUGR-1494
            contestPaymentResult = new ContestPaymentResult();
            contestPaymentResult.setPaymentResult(result);
            contestPaymentResult.setContestData(getContest(
                    tobeUpdatedCompetition.getContestData().getContestId())
                                                    .getContestData());

            //
            // Added for Cockpit Release Assembly for Receipts
            //
            String competitionType = tobeUpdatedCompetition.getType().toString();
            String projectName = tobeUpdatedCompetition.getContestData()
                                                       .getTcDirectProjectName();

            if (projectName == null) {
                projectName = Long.toString(tobeUpdatedCompetition.getContestData()
                                                                  .getTcDirectProjectId());
            }

            String toAddr = "";
            String purchasedByUser = p.getName();

            if (paymentData instanceof TCPurhcaseOrderPaymentData) {
				String currentUserEmailAddress = this.userService.getEmailAddress(p.getUserId());
				logger.debug("Current User Email Address: " + currentUserEmailAddress);

                toAddr=currentUserEmailAddress; 
            } else if (paymentData instanceof CreditCardPaymentData){
                CreditCardPaymentData cc = (CreditCardPaymentData) paymentData;
                toAddr = cc.getEmail();
            }

            sendActivateContestReceiptEmail(toAddr, purchasedByUser,
                paymentData, competitionType,
                tobeUpdatedCompetition.getContestData().getName(), projectName,
                competition.getStartTime().toGregorianCalendar().getTime(),
                paymentAmount, paymentAmount, result.getReferenceNumber());

            return contestPaymentResult;

        } catch (PersistenceException e) {
            voidPayment(paymentProcessor, result, paymentData);
            sessionContext.setRollbackOnly();
            throw e;
        } catch (PaymentException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (ContestNotFoundException e) {
            voidPayment(paymentProcessor, result, paymentData);
            sessionContext.setRollbackOnly();
            throw e;
        } catch (EmailMessageGenerationException e) {
            logger.error("Error duing email message generation", e);
        } catch (EmailSendingException e) {
            logger.error("Error duing email sending", e);
        } catch (Exception e) {
            voidPayment(paymentProcessor, result, paymentData);
            sessionContext.setRollbackOnly();
            throw new PaymentException(e.getMessage(), e);
        }

        return contestPaymentResult;
    }

    /**
     * <p>
     * Processes the contest sale.
     * </p>
     *
     * @param competition
     *            data that recognizes a contest.
     * @param paymentData
     *            payment information (credit card/po details) that need to be
     *            processed.
     *
     * @return a <code>SoftwareContestPaymentResult</code> result of the payment
     *         processing.
     *
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     * @since BUGR-1682 changed return value
     */
    public SoftwareContestPaymentResult processContestCreditCardSale(
        SoftwareCompetition competition, CreditCardPaymentData paymentData)
        throws ContestServiceException {
        logger.debug("processContestCreditCardSale");

        return processContestSaleInternal(competition, paymentData);
    }

    /**
     * <p>
     * Processes the contest sale.
     * </p>
     *
     * @param competition
     *            data that recognizes a contest.
     * @param paymentData
     *            payment information (credit card/po details) that need to be
     *            processed.
     *
     * @return a <code>SoftwareContestPaymentResult</code> result of the payment
     *         processing.
     *
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     * @since BUGR-1682 changed return value
     */
    public SoftwareContestPaymentResult processContestPurchaseOrderSale(
        SoftwareCompetition competition, TCPurhcaseOrderPaymentData paymentData)
        throws ContestServiceException {
        logger.debug("processPurchaseOrderSale");

        return processContestSaleInternal(competition, paymentData);
    }

    /**
     * <p>
     * Processes the contest sale.
     * </p>
     *
     * <p>
     * Updated for Cockpit Release Assembly for Receipts - Added code snippet to
     * send email receipts on successful purchase.
     * </p>
     * 
     * <p>
     * Updated for Version 1.0.3
     *      - For software contest, payment is made for the sum of various costs.
     *      - While doing so, only the increased amount is paid (if earlier payments were made).
     * </p>
     *
     * @param competition
     *            data that recognizes a contest.
     * @param paymentData
     *            payment information (credit card/po details) that need to be
     *            processed.
     *
     * @return a <code>SoftwareContestPaymentResult</code> result of the payment
     *         processing.
     *
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     * @since BUGR-1682 changed return value
     */
    private SoftwareContestPaymentResult processContestSaleInternal(
        SoftwareCompetition competition, PaymentData paymentData)
        throws ContestServiceException {
        logger.info("SoftwareCompetition: " + competition);
        logger.info("PaymentData: " + paymentData);

        SoftwareContestPaymentResult softwareContestPaymentResult = null;

         PaymentResult result = null;

        try {
            long contestId = competition.getProjectHeader().getId();
        	double pastPayment=0;

            SoftwareCompetition tobeUpdatedCompetition = null;

            if (contestId > 0) { // BUGR-1682
                tobeUpdatedCompetition = this.getSoftwareContestByProjectId(contestId); // BUGR-1682
                
                // calculate the past payment to calculate the differential cost.
                List<ContestSaleData> sales = tobeUpdatedCompetition.getProjectData().getContestSales();
                if (sales != null) {
                    for (ContestSaleData sale : sales) {
                        pastPayment += sale.getPrice();
                    }
                }
            }

            if (tobeUpdatedCompetition == null) {
                tobeUpdatedCompetition = this.createSoftwareContest(competition,
                        competition.getProjectHeader().getTcDirectProjectId());
            } else {
                competition.setProjectHeaderReason("User Update");
                tobeUpdatedCompetition = this.updateSoftwareContest(competition,
                        competition.getProjectHeader().getTcDirectProjectId());
                ;
            }

            Project contest = tobeUpdatedCompetition.getProjectHeader();

           

			double fee =  Double.parseDouble((String) contest.getProperty(ADMIN_FEE_PROJECT_INFO_TYPE)) 
			    + Double.parseDouble((String) contest.getProperty(FIRST_PLACE_COST_PROJECT_INFO_TYPE))
			    + Double.parseDouble((String) contest.getProperty(SECOND_PLACE_COST_PROJECT_INFO_TYPE))
			    + Double.parseDouble((String) contest.getProperty(RELIABILITY_BONUS_COST_PROJECT_INFO_TYPE))
			    + Double.parseDouble((String) contest.getProperty(MILESTONE_BONUS_COST_PROJECT_INFO_TYPE))
			    + Double.parseDouble((String) contest.getProperty(REVIEW_COST_PROJECT_INFO_TYPE))
			    + Double.parseDouble((String) contest.getProperty(DR_POINT_COST_PROJECT_INFO_TYPE));
				
			fee = fee - pastPayment;
			
            if (paymentData instanceof TCPurhcaseOrderPaymentData) {
                // processing purchase order is not in scope of this assembly.
                result = new PaymentResult();
                result.setReferenceNumber(((TCPurhcaseOrderPaymentData) paymentData).getPoNumber());
            } else if (paymentData instanceof CreditCardPaymentData) {
                // ideally client should be sending the amount,
                // but as client has some inconsistency
                // so in this case we would use the amount from contest data.
                CreditCardPaymentData creditCardPaymentData = (CreditCardPaymentData) paymentData;

                creditCardPaymentData.setAmount(String.valueOf(fee));
                creditCardPaymentData.setComment1("Contest Fee");
                creditCardPaymentData.setComment2(String.valueOf(
                        contest.getId()));
                result = paymentProcessor.process(paymentData);
            }

            // TODO, to be fixed later
            // tobeUpdatedCompetition.getContestData().setStatusId(
            // CONTEST_STATUS_ACTIVE_PUBLIC);
            // tobeUpdatedCompetition.getContestData().setDetailedStatusId(
            // CONTEST_DETAILED_STATUS_SCHEDULED);
            ContestSaleData contestSaleData = new ContestSaleData();
            contestSaleData.setPaypalOrderId(result.getReferenceNumber());
            contestSaleData.setSaleReferenceId(result.getReferenceNumber());

            if (paymentData instanceof TCPurhcaseOrderPaymentData) {
                contestSaleData.setSaleTypeId(SALE_TYPE_TC_PURCHASE_ORDER);
            }
            // TODO, how relate to payflow
            else if (paymentData instanceof CreditCardPaymentData) {
                contestSaleData.setSaleTypeId(SALE_TYPE_PAYPAL_PAYFLOW);
            }

            contestSaleData.setContestId(contest.getId());
            contestSaleData.setSaleStatusId(CONTEST_SALE_STATUS_PAID);
            contestSaleData.setPrice(fee);

            this.projectServices.createContestSale(contestSaleData);

            // DONOT create for now
            // create forum for the contest.
            // long forumid =
            // this.studioService.createForum(tobeUpdatedCompetition
            // .getContestData().getName(), p.getUserId());
            // tobeUpdatedCompetition.getContestData().setForumId(forumid);

            // update contest
            // COMMENT OUT FOR NOW, nothing to update
            // tobeUpdatedCompetition.setProjectHeaderReason(
            // "Updated for Contest Sale");
            // tobeUpdatedCompetition =
            // this.updateSoftwareContest(tobeUpdatedCompetition,
            // contest.getTcDirectProjectId());

            // return result;
            // BUGR-1682
            softwareContestPaymentResult = new SoftwareContestPaymentResult();
            softwareContestPaymentResult.setPaymentResult(result);
            /*
             * for(com.topcoder.project.phases.Phase p :
             * tobeUpdatedCompetition.getProjectPhases().getAllPhases()) {
             * p.setProject(null); }
             */
            softwareContestPaymentResult.setSoftwareCompetition(tobeUpdatedCompetition);

            //
            // Added for Cockpit Release Assembly for Receipts
            //
            String competitionType = tobeUpdatedCompetition.getProjectHeader()
                                                           .getProjectCategory()
                                                           .getName();
            String projectName = competition.getProjectHeader()
                                            .getTcDirectProjectName();

            String toAddr = "";

            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            String purchasedByUser = p.getName();

            if (paymentData instanceof TCPurhcaseOrderPaymentData) {
				String currentUserEmailAddress = this.userService.getEmailAddress(p.getUserId());
                toAddr = currentUserEmailAddress;
            } else if (paymentData instanceof CreditCardPaymentData) {
                CreditCardPaymentData cc = (CreditCardPaymentData) paymentData;
                toAddr = cc.getEmail();
            }

            sendActivateContestReceiptEmail(toAddr, purchasedByUser,
                paymentData, competitionType,
                tobeUpdatedCompetition.getProjectHeader()
                                      .getProperty(PROJECT_TYPE_INFO_PROJECT_NAME_KEY),
                projectName,
                competition.getAssetDTO().getProductionDate()
                           .toGregorianCalendar().getTime(), fee, fee,
                result.getReferenceNumber());

            return softwareContestPaymentResult;
        } catch (ContestServiceException e) {
            voidPayment(paymentProcessor, result, paymentData);
            sessionContext.setRollbackOnly();
            throw e;
        } catch (EmailMessageGenerationException e) {
            logger.error("Error duing email message generation", e);
        } catch (EmailSendingException e) {
            logger.error("Error duing email sending", e);
        } catch (Exception e) {
            voidPayment(paymentProcessor, result, paymentData);
            sessionContext.setRollbackOnly();
            throw new ContestServiceException(e.getMessage(), e);
        }

        return softwareContestPaymentResult;
    }

    /**
     * <p>
     * Processes the submission payment. It does following steps:
     * <ul>
     * <li>Checks submissionId to see if is available, if not then it throws
     * PaymentException.</li>
     * <li>It processes the payment through <code>PaymentProcessor</code></li>
     * <li>On successful processing -
     * <ul>
     * <li>it calls <code>this.purchaseSubmission(...)</code></li>
     * </ul>
     * </li>
     * </ul>
     * </p>
     *
     * @param completedContestData
     *            data of completed contest.
     * @param paymentData
     *            a <code>CreditCardPaymentData</code> payment information
     *            (credit card) that need to be processed.
     * @return a <code>PaymentResult</code> result of the payment processing.
     * @throws PaymentException
     *             if any errors occurs in processing the payment or submission
     *             is not valid.
     * @throws PersistenceException
     *             if any error occurs when retrieving the submission.
     */
    public PaymentResult processSubmissionCreditCardPayment(
        CompletedContestData completedContestData,
        CreditCardPaymentData paymentData)
        throws PaymentException, PersistenceException {
        logger.debug("processSubmissionCreditCardPayment");

        return processSubmissionPaymentInternal(completedContestData,
            paymentData);
    }

    /**
     * <p>
     * Processes the submission payment. It does following steps:
     * <ul>
     * <li>Checks submissionId to see if is available, if not then it throws
     * PaymentException.</li>
     * <li>Right-now this method doesn't process PO payments.</li>
     * <li>On successful processing -
     * <ul>
     * <li>it calls <code>this.purchaseSubmission(...)</code></li>
     * </ul>
     * </li>
     * </ul>
     * </p>
     *
     * @param completedContestData
     *            data of completed contest.
     * @param paymentData
     *            a <code>TCPurhcaseOrderPaymentData</code> payment information
     *            (po details) that need to be processed.
     * @return a <code>PaymentResult</code> result of the payment processing.
     * @throws PaymentException
     *             if any errors occurs in processing the payment or submission
     *             is not valid.
     * @throws PersistenceException
     *             if any error occurs when retrieving the submission.
     */
    public PaymentResult processSubmissionPurchaseOrderPayment(
        CompletedContestData completedContestData,
        TCPurhcaseOrderPaymentData paymentData)
        throws PaymentException, PersistenceException {
        logger.debug("processSubmissionPurchaseOrderPayment");

        return processSubmissionPaymentInternal(completedContestData,
            paymentData);
    }

    /**
     * <p>
     * Processes the submission payment. It does following steps:
     * <ul>
     * <li>Checks submissionId to see if is available, if not then it throws
     * PaymentException.</li>
     * <li>If payment type is credit card then it processes the payment through
     * <code>PaymentProcessor</code></li>
     * <li>Right-now this method doesn't process PO payments.</li>
     * <li>On successful processing -
     * <ul>
     * <li>it calls <code>this.purchaseSubmission(...)</code></li>
     * </ul>
     * </li>
     * </ul>
     * </p>
     *
     * <p>
     * Updated for Cockpit Release Assembly for Receipts - Added code snippet to
     * send email notification on successful purchase.
     * </p>
     *
     * <p>
     * Updated for Prototype Conversion Studio Multi-Rounds Assembly - Submission Viewer UI:
     * Added support for milestone prizes payment.
     * </p>
     *
     * @param completedContestData
     *            data of completed contest.
     * @param paymentData
     *            a <code>PaymentData</code> payment information (credit card/po
     *            details) that need to be processed.
     * @return a <code>PaymentResult</code> result of the payment processing.
     * @throws PaymentException
     *             if any errors occurs in processing the payment or submission
     *             is not valid.
     * @throws PersistenceException
     *             if any error occurs when retrieving the submission.
     */
    private PaymentResult processSubmissionPaymentInternal(
        CompletedContestData completedContestData, PaymentData paymentData)
        throws PaymentException, PersistenceException {
        PaymentResult result = null;

        try {
            logger.info("CompletedContestData: " + completedContestData);
            logger.info("PaymentData: " + paymentData + "," +
                paymentData.getType());

            List<SubmissionData> submissionDatas = this.retrieveSubmissionsForContest(completedContestData.getContestId());
            int totalSubmissions = submissionDatas.size();

            for (int i = 0; i < completedContestData.getSubmissions().length;
                    i++) {
                SubmissionPaymentData submissionPaymentData = completedContestData.getSubmissions()[i];
                long submissionId = submissionPaymentData.getId();

                int j = 0;

                for (SubmissionData s : submissionDatas) {
                    if (submissionId == s.getSubmissionId()) {
                        break;
                    }

                    j++;
                }

                if (j >= totalSubmissions) {
                    throw new PaymentException(
                        "Error in processing payment for submission: " +
                        submissionId + ". Submission is not found");
                }
            }

            logger.info("-------contest id ---" +
                completedContestData.getContestId());

            if (paymentData.getType().equals(PaymentType.TCPurchaseOrder)) {
                // processing purchase order is not in scope of this assembly.
                result = new PaymentResult();
                result.setReferenceNumber(((TCPurhcaseOrderPaymentData) paymentData).getPoNumber());
            } else if (paymentData.getType().equals(PaymentType.PayPalCreditCard)) {
                // BUGR-1239
                CreditCardPaymentData creditCardPaymentData = (CreditCardPaymentData) paymentData;
                creditCardPaymentData.setComment1("Submission Fee");

                long[] submissionIds = new long[completedContestData.getSubmissions().length];

                for (int i = 0;
                        i < completedContestData.getSubmissions().length;
                        i++) {
                    SubmissionPaymentData submissionPaymentData = completedContestData.getSubmissions()[i];
                    long submissionId = submissionPaymentData.getId();
                    submissionIds[i] = submissionId;
                }

                creditCardPaymentData.setComment2(Arrays.toString(submissionIds));
                result = paymentProcessor.process(paymentData);
            }

            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            String userId = Long.toString(p.getUserId());

            // purchase or rank submission.
            for (int i = 0; i < completedContestData.getSubmissions().length;
                    i++) {
                SubmissionPaymentData submissionPaymentData = completedContestData.getSubmissions()[i];
                long submissionId = submissionPaymentData.getId();

                if (submissionPaymentData.isPurchased() || submissionPaymentData.getAwardMilestonePrize()) {
                    if (submissionPaymentData.isPurchased()) {
                        this.markForPurchase(submissionId);
                    }
                    submissionPaymentData.setPaymentReferenceNumber(result.getReferenceNumber());

                    if (paymentData instanceof TCPurhcaseOrderPaymentData) {
                        submissionPaymentData.setPaymentTypeId(PAYMENT_TYPE_TC_PURCHASE_ORDER);
                    }
                    // TODO, how relate to payflow
                    else if (paymentData instanceof CreditCardPaymentData) {
                        submissionPaymentData.setPaymentTypeId(PAYMENT_TYPE_PAYPAL_PAYFLOW);
                    }

                    submissionPaymentData.setPaymentStatusId(CONTEST_PAYMENT_STATUS_PAID);
                }

                this.studioService.rankAndPurchaseSubmission(submissionId,
                    submissionPaymentData.isRanked() ? submissionPaymentData.getRank() : 0,
                    (submissionPaymentData.isPurchased() || submissionPaymentData.getAwardMilestonePrize())
                        ? submissionPaymentData : null, userId);
            }

            // update contest status to complete.
            this.updateContestStatus(completedContestData.getContestId(),
                CONTEST_COMPLETED_STATUS);

            //
            // Added for Cockpit Release Assembly for Receipts
            //
            long contestId = completedContestData.getContestId();
            ContestData contestData = studioService.getContest(contestId);

            String competitionType = CompetionType.STUDIO.toString();
            String projectName = contestData.getTcDirectProjectName();

            String toAddr = "";
            String purchasedByUser = p.getName();

            if (paymentData instanceof TCPurhcaseOrderPaymentData) {
				String currentUserEmailAddress = this.userService.getEmailAddress(p.getUserId());
                toAddr = currentUserEmailAddress;
            } else if (paymentData instanceof CreditCardPaymentData) {
                CreditCardPaymentData cc = (CreditCardPaymentData) paymentData;
                toAddr = cc.getEmail();
            }

            sendPurchaseSubmissionReceiptEmail(toAddr, purchasedByUser,
                paymentData, competitionType, contestData.getName(),
                projectName, completedContestData.getSubmissions(),
                result.getReferenceNumber());

            return result;
        } catch (PersistenceException e) {
            voidPayment(paymentProcessor, result, paymentData);
            sessionContext.setRollbackOnly();
            throw e;
        } catch (PaymentException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (EmailMessageGenerationException e) {
            logger.error("Error duing email message generation", e);
        } catch (EmailSendingException e) {
            logger.error("Error duing email sending", e);
        } catch (Exception e) {
            voidPayment(paymentProcessor, result, paymentData);
            sessionContext.setRollbackOnly();
            throw new PaymentException(e.getMessage(), e);
        }

        return result;
    }

    /**
     * <p>
     * Ranks the submissions, given submission identifiers in the rank order.
     * </p>
     *
     * @param submissionIdsInRankOrder
     *            an array of long submission identifier in the rank order.
     * @return a <code>boolean</code> true if successful, else false.
     * @throws PersistenceException
     *             if any error occurs when retrieving/updating the data.
     */
    public boolean rankSubmissions(long[] submissionIdsInRankOrder)
        throws PersistenceException {
        logger.debug("rankSubmissions");

        try {
            for (int i = 0; i < submissionIdsInRankOrder.length; i++) {
                this.studioService.setSubmissionPlacement(submissionIdsInRankOrder[i],
                    i + 1);
            }

            logger.debug("Exit rankSubmissions");

            return true;
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * <p>
     * Updates the submission feedback.
     * </p>
     *
     * @param feedbacks
     *            an array of <code>SubmissionFeedback</code>
     * @return a <code>boolean</code> true if successful, else false.
     * @throws PersistenceException
     *             if any error occurs when retrieving/updating the data.
     */
    public boolean updateSubmissionsFeedback(SubmissionFeedback[] feedbacks)
        throws PersistenceException {
        logger.debug("updateSubmissionsFeedback");

        try {
            for (SubmissionFeedback f : feedbacks) {
                this.studioService.updateSubmissionFeedback(f);
            }

            logger.debug("Exit updateSubmissionsFeedback");

            return true;
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * <p>
     * Returns a list containing all active <code>Categories</code>.
     * </p>
     *
     * @return a list containing all active <code>Categories</code>. It can be
     *         empty if no objects found.
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public List<Category> getActiveCategories() throws ContestServiceException {
        logger.debug("getActiveCategories");

        try {
            return catalogService.getActiveCategories();
        } catch (com.topcoder.catalog.service.PersistenceException e) {
            logger.error("Operation failed in the catalogService.", e);
            throw new ContestServiceException("Operation failed in the catalogService.",
                e);
        }
    }

    /**
     * <p>
     * Returns a list containing all active <code>Technologies</code>.
     * </p>
     *
     * @return a list containing all active <code>Categories</code>. It can be
     *         empty if no objects found.
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public List<Technology> getActiveTechnologies()
        throws ContestServiceException {
        logger.debug("getActiveTechnologies");

        try {
            return catalogService.getActiveTechnologies();
        } catch (com.topcoder.catalog.service.PersistenceException e) {
            logger.error("Operation failed in the catalogService", e);
            throw new ContestServiceException("Operation failed in the catalogService.",
                e);
        }
    }

    /**
     * <p>
     * Returns a list containing all <code>Phases</code>.
     * </p>
     *
     * @return a list containing all active <code>Categories</code>. It can be
     *         empty if no objects found.
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public List<Phase> getPhases() throws ContestServiceException {
        logger.debug("getPhase");

        try {
            return catalogService.getPhases();
        } catch (com.topcoder.catalog.service.PersistenceException e) {
            logger.error("Operation failed in the catalogService", e);
            throw new ContestServiceException("Operation failed in the catalogService.",
                e);
        }
    }

    /**
     * <p>
     * Assigns a specified user to a specified <code>assetDTO</code>.
     * </p>
     *
     * <p>
     * If the user already assigned to the asset, this method simply does
     * nothing.
     * </p>
     *
     * @param userId
     *            the id of the user
     * @param assetId
     *            the id of the assetDTO
     *
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public void assignUserToAsset(long userId, long assetId)
        throws ContestServiceException {
        logger.debug("assignUserToAsset");

        try {
            catalogService.assignUserToAsset(userId, assetId);
            logger.debug("Exit assignUserToAsset");
        } catch (EntityNotFoundException e) {
            logger.error("Operation failed in the catalogService.", e);
            throw new ContestServiceException("Operation failed in the catalogService.",
                e);
        } catch (com.topcoder.catalog.service.PersistenceException e) {
            logger.error("Operation failed in the catalogService.", e);
            throw new ContestServiceException("Operation failed in the catalogService.",
                e);
        }
    }

    /**
     * <p>
     * Removes a specified user from a specified <code>assetDTO</code>.
     * </p>
     *
     * @param userId
     *            the id of the user
     * @param assetId
     *            the id of the asset
     *
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public void removeUserFromAsset(long userId, long assetId)
        throws ContestServiceException {
        logger.debug("removeUserFromAsset");

        try {
            catalogService.removeUserFromAsset(userId, assetId);
            logger.debug("Exit removeUserFromAsset");
        } catch (EntityNotFoundException e) {
            logger.error("Operation failed in the catalogService.", e);
            throw new ContestServiceException("Operation failed in the catalogService.",
                e);
        } catch (com.topcoder.catalog.service.PersistenceException e) {
            logger.error("Operation failed in the catalogService.", e);
            throw new ContestServiceException("Operation failed in the catalogService.",
                e);
        }
    }

    /**
     * <p>
     * This method finds all tc direct projects. Returns empty array if no
     * projects found.
     * </p>
     *
     * @return Project array with project info, or empty array if none found
     *
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition[] findAllTcDirectProjects()
        throws ContestServiceException {
        logger.debug("findAllTcDirectProjects");

        try {
            Project[] projects = projectServices.findAllTcDirectProjects();

            SoftwareCompetition[] ret = new SoftwareCompetition[projects.length];

            for (int i = 0; i < projects.length; i++) {
                FullProjectData projectData = new FullProjectData();
                projectData.setProjectHeader(projects[i]);

                ret[i] = new SoftwareCompetition();
                ret[i].setProjectData(projectData);
                ret[i].setType(CompetionType.SOFTWARE);
                ret[i].setId(projectData.getProjectHeader().getId());
            }

            logger.debug("Exit findAllTcDirectProjects");

            return ret;
        } catch (ProjectServicesException e) {
            logger.error("Operation failed in the projectServices.", e);
            throw new ContestServiceException("Operation failed in the projectServices.",
                e);
        }
    }

    /**
     * <p>
     * This method finds all given user tc direct projects . Returns empty array
     * if no projects found.
     * </p>
     *
     * @param operator
     *            The user to search for projects
     *
     * @return Project array with project info, or empty array if none found
     *
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition[] findAllTcDirectProjectsForUser(String operator)
        throws ContestServiceException {
        logger.debug("findAllTcDirectProjectsForUser");

        try {
            Project[] projects = projectServices.findAllTcDirectProjectsForUser(operator);

            SoftwareCompetition[] ret = new SoftwareCompetition[projects.length];

            for (int i = 0; i < projects.length; i++) {
                FullProjectData projectData = new FullProjectData();
                projectData.setProjectHeader(projects[i]);

                ret[i] = new SoftwareCompetition();
                ret[i].setProjectData(projectData);
                ret[i].setType(CompetionType.SOFTWARE);
                ret[i].setId(projectData.getProjectHeader().getId());
            }

            logger.debug("Exit findAllTcDirectProjectsForUser");

            return ret;
        } catch (ProjectServicesException e) {
            logger.error("Operation failed in the projectServices.", e);
            throw new ContestServiceException("Operation failed in the projectServices.",
                e);
        }
    }

    /**
     * <p>
     * This method retrieves the project along with all known associated
     * information. Returns null if not found.
     * </p>
     *
     * @param projectId
     *            The ID of the project to retrieve
     *
     * @return the project along with all known associated information
     *
     * @throws IllegalArgumentException
     *             If projectId is negative
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition getFullProjectData(long projectId)
        throws ContestServiceException {
        logger.debug("getFullProjectData");

        try {
            FullProjectData projectData = projectServices.getFullProjectData(projectId);

            if (projectData == null) {
                return null;
            }

            com.topcoder.project.phases.Phase[] allPhases = projectData.getAllPhases();

            for (int i = 0; i < allPhases.length; i++) {
                allPhases[i].setProject(null);
            }

            SoftwareCompetition contest = new SoftwareCompetition();
            contest.setProjectData(projectData);
            contest.setType(CompetionType.SOFTWARE);
            contest.setId(projectData.getProjectHeader().getId());
            logger.debug("Exit getFullProjectData");

            return contest;
        } catch (ProjectServicesException e) {
            logger.error("Operation failed in the projectServices.", e);
            throw new ContestServiceException("Operation failed in the projectServices.",
                e);
        }
    }

    /**
     * <p>
     * Creates a new <code>SoftwareCompetition</code> in the persistence.
     * </p>
     * 
     * Updated for Version 1.0.1
     *      - BUGR-2185: For development contests, if asset (or component) exists from design contests then that is used
     *        to create a new contest. Otherwise a new asset is also created.
     *
     * @param contest the <code>SoftwareCompetition</code> to create as a contest
     * @param tcDirectProjectId the TC direct project id.
     *
     * @return the created <code>SoftwareCompetition</code> as a contest
     *
     * @throws IllegalArgumentException
     *             if the input argument is invalid.
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition createSoftwareContest(
        SoftwareCompetition contest, long tcDirectProjectId)
        throws ContestServiceException {
        logger.debug("createSoftwareContest");

        try {
            AssetDTO assetDTO = contest.getAssetDTO();
            long forumId = 0;

            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();

            XMLGregorianCalendar productionDate = null;

            if (assetDTO != null) {
                
                boolean isDevContest = contest.getProjectHeader().getProjectCategory().getId() == DEVELOPMENT_PROJECT_CATEGORY_ID;
                boolean useExistingAsset=false;
                
                if (isDevContest) {
                    String componentName = assetDTO.getName();
                    SearchCriteria searchCriteria = new SearchCriteria(null, null, null, componentName, null, null, null, null, null);
                    List<AssetDTO> foundDTOs = this.catalogService.findAssets(searchCriteria, false);
                    if (foundDTOs != null && foundDTOs.size() > 0) {
                        System.out.println("createSoftwareContest ====================> FoundAssetDTO for DEV: " + foundDTOs);
                        useExistingAsset=true;
                        assetDTO=foundDTOs.get(0);
                        
                        // re-get it, as the one found from findAsset seem to be shallow instance.
                        assetDTO=this.catalogService.getAssetByVersionId(assetDTO.getCompVersionId());
                        
                        if (assetDTO.getProductionDate() == null) {
                            GregorianCalendar startDate = new GregorianCalendar();
                            startDate.setTime(new Date());
                            startDate.add(Calendar.HOUR, 24 * 14);
                            int m = startDate.get(Calendar.MINUTE);
                            startDate.add(Calendar.MINUTE, m + (15 - m % 15) % 15);
                            assetDTO.setProductionDate(getXMLGregorianCalendar(startDate.getTime()));                            
                        }
                     
                        productionDate = assetDTO.getProductionDate();
                        assetDTO.setProductionDate(null);
                    }
                }
                
                if (!useExistingAsset) {
                    if (assetDTO.getProductionDate() == null) { // BUGR-1445
                        /*
                         * - start: current time + 24 hour (round the minutes up to the nearest 15)
                         */
                        GregorianCalendar startDate = new GregorianCalendar();
                        startDate.setTime(new Date());
                        startDate.add(Calendar.HOUR, 24 * 14); // BUGR-1789
                        int m = startDate.get(Calendar.MINUTE);
                        startDate.add(Calendar.MINUTE, m + (15 - m % 15) % 15);
                        assetDTO.setProductionDate(getXMLGregorianCalendar(startDate.getTime()));
                    }

                // product date is used to pass the project start date
                // bcoz we need to use XMLGregorianCalendar and project start
                // date
                // is Date and since it is not DTO and hard to change, we use
                // product date for now, but we need to set it null so it will
                // not
                // saved in catalog
                productionDate = assetDTO.getProductionDate();
                assetDTO.setProductionDate(null);

                    if (contest.getProjectHeader() != null) {
                        // comp development, set phase to dev
                        if (contest.getProjectHeader().getProjectCategory().getId() == DEVELOPMENT_PROJECT_CATEGORY_ID) {
                            assetDTO.setPhase("Development");
                        }
                        // else set to design
                        else {
                            assetDTO.setPhase("Design");
                        }
                    }

                    assetDTO = this.catalogService.createAsset(assetDTO);
                    contest.setAssetDTO(assetDTO);
                }

                // create forum
                if (createForum) {
                    if (useExistingAsset && assetDTO.getForum() != null) {
                        forumId = assetDTO.getForum().getJiveCategoryId();
                    } else {
                        forumId = createForum(assetDTO, p.getUserId(), contest.getProjectHeader().getProjectCategory()
                            .getId());
                    }
                }

                // if forum created
                if (forumId > 0 && (!useExistingAsset || assetDTO.getForum() == null)) {
                    // create a comp forum
                    CompForum compForum = new CompForum();
                    compForum.setJiveCategoryId(forumId);
                    assetDTO.setForum(compForum);
                    assetDTO = this.catalogService.updateAsset(assetDTO);
                    // avoid cycle
                    assetDTO.getForum().setCompVersion(null);
                }
            }

            if (contest.getProjectHeader() != null) {
                //
                // since: Flex Cockpit Launch Contest - Integrate Software
                // Contests v1.0
                // set the project properties.
                //
                if (assetDTO != null) {
                    /*
                     * contest.getProjectHeader().setProperty(PROJECT_TYPE_INFO_VERSION_ID_KEY
                     * , assetDTO.getVersionId().toString());
                     */
                    contest.getProjectHeader()
                           .setProperty(PROJECT_TYPE_INFO_EXTERNAL_REFERENCE_KEY,
                        assetDTO.getCompVersionId().toString());
                    contest.getProjectHeader()
                           .setProperty(PROJECT_TYPE_INFO_COMPONENT_ID_KEY,
                        assetDTO.getId().toString());
                    contest.getProjectHeader()
                           .setProperty(PROJECT_TYPE_INFO_SVN_MODULE_KEY, "");
                    contest.getProjectHeader()
                           .setProperty(PROJECT_TYPE_INFO_NOTES_KEY, "");

                    if (forumId > 0) {
                        contest.getProjectHeader()
                               .setProperty(PROJECT_TYPE_INFO_DEVELOPER_FORUM_ID_KEY,
                            String.valueOf(forumId));
                    }

                    contest.getProjectPhases()
                           .setStartDate(getDate(productionDate));
                }

                com.topcoder.management.resource.Resource[] resources = new com.topcoder.management.resource.Resource[2];
                resources[0] = new com.topcoder.management.resource.Resource();
                resources[0].setId(com.topcoder.management.resource.Resource.UNSET_ID);

                ResourceRole manager_role = new ResourceRole();
                manager_role.setId(RESOURCE_ROLE_MANAGER_ID);
                manager_role.setName(RESOURCE_ROLE_MANAGER_NAME);
                manager_role.setDescription(RESOURCE_ROLE_MANAGER_DESC);

                ResourceRole observer_role = new ResourceRole();
                observer_role.setId(RESOURCE_ROLE_OBSERVER_ID);
                observer_role.setName(RESOURCE_ROLE_OBSERVER_NAME);
                observer_role.setDescription(RESOURCE_ROLE_OBSERVER_DESC);

                boolean tcstaff = sessionContext.isCallerInRole(TC_STAFF_ROLE);
                // tc staff add as manager, other as observer
                if (tcstaff)
                {
                    resources[0].setResourceRole(manager_role);
                }
                else
                {
                    resources[0].setResourceRole(observer_role);
                }


                resources[0].setProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID,
                    String.valueOf(p.getUserId()));
                resources[0].setProperty(RESOURCE_INFO_HANDLE,
                    String.valueOf(p.getName()));
                resources[0].setProperty(RESOURCE_INFO_PAYMENT_STATUS,
                    RESOURCE_INFO_PAYMENT_STATUS_NA);

                if (contest.getProjectHeader() != null) 
                {
                    // design/dev, add Components
                    if (contest.getProjectHeader().getProjectCategory().getId() == DEVELOPMENT_PROJECT_CATEGORY_ID 
                         || contest.getProjectHeader().getProjectCategory().getId() == DESIGN_PROJECT_CATEGORY_ID) {

                        resources[1] = new com.topcoder.management.resource.Resource();
                        resources[1].setId(com.topcoder.management.resource.Resource.UNSET_ID);
                        resources[1].setResourceRole(manager_role);
                        resources[1].setProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID, Long.toString(components_user_id));
                        resources[1].setProperty(RESOURCE_INFO_HANDLE, RESOURCE_INFO_HANDLE_COMPONENTS);
                        resources[1].setProperty(RESOURCE_INFO_PAYMENT_STATUS, RESOURCE_INFO_PAYMENT_STATUS_NA);
                    }
                    // else add Applications
                    else {
                        resources[1] = new com.topcoder.management.resource.Resource();
                        resources[1].setId(com.topcoder.management.resource.Resource.UNSET_ID);
                        resources[1].setResourceRole(manager_role);
                        resources[1].setProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID, Long.toString(applications_user_id));
                        resources[1].setProperty(RESOURCE_INFO_HANDLE, RESOURCE_INFO_HANDLE_APPLICATIONS);
                        resources[1].setProperty(RESOURCE_INFO_PAYMENT_STATUS, RESOURCE_INFO_PAYMENT_STATUS_NA);
                    }
                }

				

				contest.setProjectResources(resources);

                contest.getProjectHeader()
                       .setTcDirectProjectId(tcDirectProjectId);

                FullProjectData projectData = projectServices.createProjectWithTemplate(contest.getProjectHeader(),
                        contest.getProjectPhases(),
                        contest.getProjectResources(),
                        String.valueOf(p.getUserId()));

                com.topcoder.project.phases.Phase[] allPhases = projectData.getAllPhases();

                // TODO for now have to do these to avoid cycle
                for (int i = 0; i < allPhases.length; i++) {
                    allPhases[i].setProject(null);
                    allPhases[i].clearDependencies();

                    // allPhases[i].clearAttributes();
                }

                contest.setProjectHeader(projectData.getProjectHeader());
                contest.setProjectPhases(projectData);
                contest.setProjectResources(projectData.getResources());
                contest.setProjectData(projectData);
                contest.setId(projectData.getProjectHeader().getId());
                contest.setAssetDTO(assetDTO);

		           // set null to avoid cycle
                contest.getAssetDTO().setDependencies(null);
                if (contest.getAssetDTO().getForum() != null)
                {
                    contest.getAssetDTO().getForum().setCompVersion(null);
                }
                if (contest.getAssetDTO().getLink() != null)
                {
                    contest.getAssetDTO().getLink().setCompVersion(null);
                }
                if (contest.getAssetDTO().getDocumentation() != null && contest.getAssetDTO().getDocumentation().size() > 0)
                {
                    for (CompDocumentation doc : contest.getAssetDTO().getDocumentation())
                    {
                        doc.setCompVersion(null);
                    }
                }

                // set project start date in production date
                contest.getAssetDTO()
                       .setProductionDate(getXMLGregorianCalendar(
                        contest.getProjectPhases().getStartDate()));
            }

            logger.debug("Exit createSoftwareContest");

            return contest;
        } catch (com.topcoder.catalog.service.PersistenceException e) {
            sessionContext.setRollbackOnly();
            logger.error("Operation failed in the contest service facade.", e);
            throw new ContestServiceException("Operation failed in the contest service facade.",
                e);
        } catch (ProjectServicesException e) {
            sessionContext.setRollbackOnly();
            logger.error("Operation failed in the contest service facade.", e);
            throw new ContestServiceException("Operation failed in the contest service facade.",
                e);
        } catch (Exception e) {
            sessionContext.setRollbackOnly();
            logger.error("Operation failed in the contest service facade.", e);
            throw new ContestServiceException("Operation failed in the contest service facade.",
                e);
        }
    }

    /**
     * <p>
     * Updates a <code>SoftwareCompetition</code> in the persistence.
     * </p>
     *
     * @param contest
     *            the <code>SoftwareCompetition</code> to update as a contest
     * @param tcDirectProjectId
     *            the TC direct project id.
     *
     * @throws IllegalArgumentException
     *             if the input argument is invalid.
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition updateSoftwareContest(
        SoftwareCompetition contest, long tcDirectProjectId)
        throws ContestServiceException {
        logger.debug("updateSoftwareContest");

        try {
            XMLGregorianCalendar productionDate = null;

            if (contest.getAssetDTO() != null) {
                // product date is used to pass the project start date
                // bcoz we need to use XMLGregorianCalendar and project start
                // date
                // is Date and since it is not DTO and hard to change, we use
                // product date for now, but we need to set it null so it will
                // not
                // saved in catalog
                productionDate = contest.getAssetDTO().getProductionDate();
                contest.getAssetDTO().setProductionDate(null);

                // TODO: for some reason, versionid is not passed
                contest.getAssetDTO()
                       .setCompVersionId(contest.getAssetDTO().getVersionNumber());
                contest.setAssetDTO(this.catalogService.updateAsset(
                        contest.getAssetDTO()));
            }

            if (contest.getProjectHeader() != null) {
                UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();

                Set phaseset = contest.getProjectPhases().getPhases();
                com.topcoder.project.phases.Phase[] phases = (com.topcoder.project.phases.Phase[]) phaseset.toArray(new com.topcoder.project.phases.Phase[phaseset.size()]);

                // add back project on phase
                for (int i = 0; i < phases.length; i++) {
                    phases[i].setProject(contest.getProjectPhases());
                }

                contest.getProjectPhases().setStartDate(getDate(productionDate));

                contest.getProjectHeader()
                       .setTcDirectProjectId(tcDirectProjectId);

                FullProjectData projectData = projectServices.updateProject(contest.getProjectHeader(),
                        contest.getProjectHeaderReason(),
                        contest.getProjectPhases(),
                        contest.getProjectResources(),
                        String.valueOf(p.getUserId()));

                // TCCC-1438 - it's better to refetch from backend.
		projectData.setContestSales(projectServices.getContestSales(projectData.getProjectHeader().getId()));

                contest.setProjectHeader(projectData.getProjectHeader());
                contest.setProjectPhases(projectData);
                contest.setProjectResources(projectData.getResources());
                contest.setProjectData(projectData);
                contest.setId(projectData.getProjectHeader().getId());

                com.topcoder.project.phases.Phase[] allPhases = projectData.getAllPhases();

                // this is to avoid cycle
                for (int i = 0; i < allPhases.length; i++) {
                    allPhases[i].setProject(null);
                    allPhases[i].clearDependencies();
                }
            }

            // set project start date in production date
            contest.getAssetDTO()
                   .setProductionDate(getXMLGregorianCalendar(
                    contest.getProjectPhases().getStartDate()));
            logger.debug("Exit updateSoftwareContest");

            return contest;
        } catch (com.topcoder.catalog.service.PersistenceException e) {
            sessionContext.setRollbackOnly();
            logger.error("Operation failed in the contest service facade.", e);
            throw new ContestServiceException("Operation failed in the contest service facade.",
                e);
        } catch (ProjectServicesException e) {
            sessionContext.setRollbackOnly();
            logger.error("Operation failed in the contest service facade.", e);
            throw new ContestServiceException("Operation failed in the contest service facade.",
                e);
        } catch (EntityNotFoundException e) {
            sessionContext.setRollbackOnly();
            logger.error("Operation failed in the contest service facade.", e);
            throw new ContestServiceException("Operation failed in the contest service facade.",
                e);
        } catch (Exception e) {
            sessionContext.setRollbackOnly();
            logger.error("Operation failed in the contest service facade.", e);
            throw new ContestServiceException("Operation failed in the contest service facade.",
                e);
        }
    }

    /**
     * <p>
     * Adds a new submission for an user in a particular project.
     * </p>
     *
     * <p>
     * If the project allows multiple submissions for users, it will add the new
     * submission and return. If multiple submission are not allowed for the
     * project, firstly it will add the new submission, secondly mark previous
     * submissions as deleted and then return.
     * </p>
     *
     * @param projectId
     *            the project's id
     * @param filename
     *            the file name to use
     * @param submission
     *            the submission file data
     *
     * @return the id of the new submission
     *
     * @throws IllegalArgumentException
     *             if any id is &lt; 0, if any argument is <code>null</code> or
     *             trim to empty
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public long uploadSubmission(long projectId, String filename,
        DataHandler submission) throws ContestServiceException {
        logger.debug("uploadSubmission");

        try {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            logger.debug("Exit updateSoftwareContest");

            return uploadExternalServices.uploadSubmission(projectId,
                p.getUserId(), filename, submission);
        } catch (UploadServicesException e) {
            logger.error("Operation failed in the uploadExternalServices.", e);
            throw new ContestServiceException("Operation failed in the uploadExternalServices.",
                e);
        } catch (RemoteException e) {
            logger.error("Operation failed in the uploadExternalServices.", e);
            throw new ContestServiceException("Operation failed in the uploadExternalServices.",
                e);
        }
    }

    /**
     * <p>
     * Adds a new final fix upload for an user in a particular project. This
     * submission always overwrite the previous ones.
     * </p>
     *
     * @param projectId
     *            the project's id
     * @param filename
     *            the file name to use
     * @param finalFix
     *            the final fix file data
     *
     * @return the id of the created final fix submission
     *
     * @throws IllegalArgumentException
     *             if any id is &lt; 0, if any argument is <code>null</code> or
     *             trim to empty
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public long uploadFinalFix(long projectId, String filename,
        DataHandler finalFix) throws ContestServiceException {
        logger.debug("uploadFinalFix");

        try {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            logger.debug("Exit uploadFinalFix");

            return uploadExternalServices.uploadFinalFix(projectId,
                p.getUserId(), filename, finalFix);
        } catch (UploadServicesException e) {
            logger.error("Operation failed in the uploadExternalServices.", e);
            throw new ContestServiceException("Operation failed in the uploadExternalServices.",
                e);
        } catch (RemoteException e) {
            logger.error("Operation failed in the uploadExternalServices.", e);
            throw new ContestServiceException("Operation failed in the uploadExternalServices.",
                e);
        }
    }

    /**
     * <p>
     * Adds a new test case upload for an user in a particular project. This
     * submission always overwrite the previous ones.
     * </p>
     *
     * @param projectId
     *            the project's id
     * @param filename
     *            the file name to use
     * @param testCases
     *            the test cases data
     *
     * @return the id of the created test cases submission
     *
     * @throws IllegalArgumentException
     *             if any id is &lt; 0, if any argument is <code>null</code> or
     *             trim to empty
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public long uploadTestCases(long projectId, String filename,
        DataHandler testCases) throws ContestServiceException {
        logger.debug("uploadTestCases");

        try {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            logger.debug("Exit uploadTestCases");

            return uploadExternalServices.uploadTestCases(projectId,
                p.getUserId(), filename, testCases);
        } catch (UploadServicesException e) {
            logger.error("Operation failed in the uploadExternalServices.", e);
            throw new ContestServiceException("Operation failed in the uploadExternalServices.",
                e);
        } catch (RemoteException e) {
            logger.error("Operation failed in the uploadExternalServices.", e);
            throw new ContestServiceException("Operation failed in the uploadExternalServices.",
                e);
        }
    }

    /**
     * <p>
     * Sets the status of a existing submission.
     * </p>
     *
     * @param submissionId
     *            the submission's id
     * @param submissionStatusId
     *            the submission status id
     * @param operator
     *            the operator which execute the operation
     *
     * @throws IllegalArgumentException
     *             if any id is &lt; 0 or if operator is null or trim to empty
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public void setSubmissionStatus(long submissionId, long submissionStatusId,
        String operator) throws ContestServiceException {
        logger.debug("setSubmissionStatus");

        try {
            uploadExternalServices.setSubmissionStatus(submissionId,
                submissionStatusId, operator);
            logger.debug("Exit setSubmissionStatus");
        } catch (UploadServicesException e) {
            logger.error("Operation failed in the uploadExternalServices.", e);
            throw new ContestServiceException("Operation failed in the uploadExternalServices.",
                e);
        } catch (RemoteException e) {
            logger.error("Operation failed in the uploadExternalServices.", e);
            throw new ContestServiceException("Operation failed in the uploadExternalServices.",
                e);
        }
    }

    /**
     * Adds the given user as a new submitter to the given project id.
     *
     * @param projectId
     *            the project to which the user needs to be added
     * @param userId
     *            the user to be added
     *
     * @return the added resource id
     *
     * @throws IllegalArgumentException
     *             if any id is &lt; 0
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public long addSubmitter(long projectId, long userId)
        throws ContestServiceException {
        logger.debug("AddSubmitter (" + projectId + "," + userId + ")");

        try {
            return uploadExternalServices.addSubmitter(projectId, userId);
        } catch (UploadServicesException e) {
            logger.error("Operation failed in the uploadExternalServices.", e);
            throw new ContestServiceException("Operation failed in the uploadExternalServices.",
                e);
        } catch (RemoteException e) {
            logger.error("Operation failed in the uploadExternalServices.", e);
            throw new ContestServiceException("Operation failed in the uploadExternalServices.",
                e);
        }
    }

    /**
     * create forum with given parameters. It will lookup the ForumsHome
     * interface, and ceate the forum by the ejb home interface. In the old
     * version, this method misses the document, it's added in the version 1.1
     *
     *
     * @param asset
     *            The asset DTO to user
     * @param userId
     *            userId The user id to use
     * @param projectCategoryId
     *            The project category id to
     * @return The long id of the created forum
     */
    public long createForum(AssetDTO asset, long userId, long projectCategoryId) {
        long forumId = -1;
        logger.debug("createForum (" + userId + ")");

        try {
            Properties p = new Properties();
            p.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.jnp.interfaces.NamingContextFactory");
            p.put(Context.URL_PKG_PREFIXES,
                "org.jboss.naming:org.jnp.interfaces");
            p.put(Context.PROVIDER_URL, forumBeanProviderUrl);

            Context c = new InitialContext(p);
            ForumsHome forumsHome = (ForumsHome) c.lookup(ForumsHome.EJB_REF_NAME);

            Forums forums = forumsHome.create();

            long phaseId = 0;

            try {
                phaseId = Long.parseLong(asset.getPhase());
            } catch (Exception ee) {
            }

            forumId = forums.createSoftwareComponentForums(asset.getName(),
                    asset.getId(), asset.getCompVersionId(), phaseId,
                    Status.REQUESTED.getStatusId(),
                    asset.getRootCategory().getId(),
                    asset.getShortDescription(), asset.getVersionText(), false,
                    projectCategoryId);

            if (forumId < 0) {
                throw new Exception("createStudioForum returned -1");
            }

            logger.error("Created forum " + forumId + " for " +
                asset.getName());

            forums.assignRole(userId, "Software_Moderators_" + forumId); //BUGR-
                                                                         // 1677

            forums.createForumWatch(userId, forumId);
            logger.debug("Exit createForum (" + userId + ")");

            return forumId;
        } catch (Exception e) {
            logger.error("*** Could not create a forum for " + asset.getName());
            logger.error(e);

            return forumId;
        }
    }

    /**
     * <p>
     * Ranks the submissions, given submission identifiers and the rank. If the isRankingMilestone flag is true,
     * the rank will target milestone submissions.
     * </p>
     *
     * @param submissionId
     *            identifier of the submission.
     * @param rank
     *            rank of the submission.
     * @param isRankingMilestone
     *            true if the user is ranking milestone submissions.
     *
     * @return a <code>boolean</code> true if successful, else false.
     * @throws PersistenceException
     *             if any error occurs when retrieving/updating the data.
     * @since TCCC-1219
     */
    public boolean updateSubmissionUserRank(long submissionId, int rank, Boolean isRankingMilestone)
        throws PersistenceException {
        logger.debug("updateSubmissionUserRank (" + submissionId + "," + rank + "," + isRankingMilestone +
            ")");

        try {
            this.studioService.updateSubmissionUserRank(submissionId, rank, isRankingMilestone);
            logger.debug("Exit updateSubmissionUserRank (" + submissionId +
                "," + rank + "," + isRankingMilestone + ")");

            return true;
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            logger.error(e.getMessage());
            throw e;
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CommonProjectContestData> getCommonProjectContestDataByPID(
        long pid) throws PersistenceException {
        logger.debug("getCommonProjectContestDataByPID (" + pid + ")");

        List<CommonProjectContestData> ret = new ArrayList<CommonProjectContestData>();

        for (SimpleProjectContestData data : studioService.getSimpleProjectContestData(
                pid)) {
            if (data != null) {
                CommonProjectContestData newData = new CommonProjectContestData();
                newData.setCname(data.getCname());
                newData.setContestId(data.getContestId());
                newData.setProjectId(data.getProjectId());
                newData.setPname(data.getPname());
                newData.setDescription(data.getDescription());
                newData.setEndDate(data.getEndDate());
                newData.setForumId(data.getForumId());
                newData.setNum_for(data.getNum_for());
                newData.setNum_reg(data.getNum_reg());
                newData.setNum_sub(data.getNum_sub());
                newData.setProjectId(data.getProjectId());
                newData.setSname(data.getSname());
                newData.setStartDate(data.getStartDate());
                // studio set 'Studio' for now
                newData.setType("Studio");
                newData.setCreateUser(data.getCreateUser());
                newData.setPperm(data.getPperm());
                newData.setCperm(data.getCperm());
                newData.setSpecReviewStatus(data.getSpecReviewStatus());
                newData.setMilestoneDate(data.getMilestoneDate());
		ret.add(newData);
            }
        }

        for (com.topcoder.management.project.SimpleProjectContestData data : projectServices.getSimpleProjectContestData(
                pid)) {
            CommonProjectContestData newData = new CommonProjectContestData();
            newData.setCname(data.getCname());
            newData.setContestId(data.getContestId());
            newData.setProjectId(data.getProjectId());
            newData.setPname(data.getPname());
            newData.setDescription(data.getDescription());
            newData.setEndDate(getXMLGregorianCalendar(data.getEndDate()));
            newData.setForumId(data.getForumId());
            newData.setNum_for(data.getNum_for());
            newData.setNum_reg(data.getNum_reg());
            newData.setNum_sub(data.getNum_sub());
            newData.setProjectId(data.getProjectId());
            newData.setSname(data.getSname());
            newData.setStartDate(getXMLGregorianCalendar(data.getStartDate()));
            newData.setType(data.getType());
            newData.setCreateUser(data.getCreateUser());
            newData.setPperm(data.getPperm());
            newData.setCperm(data.getCperm());
 	    newData.setSpecReviewStatus(data.getSpecReviewStatus());	
            newData.setSubmissionEndDate(getXMLGregorianCalendar(data.getSubmissionEndDate()));
            ret.add(newData);
        }

        // sort/group by project id
        Collections.sort(ret,
            new Comparator() {
                public int compare(Object o1, Object o2) {
                    CommonProjectContestData p1 = (CommonProjectContestData) o1;
                    CommonProjectContestData p2 = (CommonProjectContestData) o2;

                    return p1.getProjectId().compareTo(p2.getProjectId());
                }
            });
        logger.debug("Exit getCommonProjectContestDataByPID (" + pid + ")");

        return ret;
    }

    /**
     * <p>
     * Gets the list of all existing contests for my project widget.
     * </p>
     *
     * @return a <code>List</code> listing all existing contests. Empty list is
     *         returned if there are no contests found.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CommonProjectContestData> getCommonProjectContestData()
        throws PersistenceException {
        logger.debug("getCommonProjectContestDataByContestData");

        List<CommonProjectContestData> ret = new ArrayList<CommonProjectContestData>();

        for (SimpleProjectContestData data : studioService.getSimpleProjectContestData()) {
            if (data != null) {
                CommonProjectContestData newData = new CommonProjectContestData();
                newData.setCname(data.getCname());
                newData.setContestId(data.getContestId());
                newData.setProjectId(data.getProjectId());
                newData.setPname(data.getPname());
                newData.setDescription(data.getDescription());
                newData.setEndDate(data.getEndDate());
                newData.setForumId(data.getForumId());
                newData.setNum_for(data.getNum_for());
                newData.setNum_reg(data.getNum_reg());
                newData.setNum_sub(data.getNum_sub());
                newData.setProjectId(data.getProjectId());
                newData.setSname(data.getSname());
                newData.setStartDate(data.getStartDate());
                // studio set 'Studio' for now
                newData.setType("Studio");
                newData.setCreateUser(data.getCreateUser());
                newData.setPperm(data.getPperm());
                newData.setCperm(data.getCperm());
		newData.setSpecReviewStatus(data.getSpecReviewStatus());
                newData.setMilestoneDate(data.getMilestoneDate());
                ret.add(newData);
            }
        }

        for (com.topcoder.management.project.SimpleProjectContestData data : projectServices.getSimpleProjectContestData()) {
            CommonProjectContestData newData = new CommonProjectContestData();
            newData.setCname(data.getCname());
            newData.setContestId(data.getContestId());
            newData.setProjectId(data.getProjectId());
            newData.setPname(data.getPname());
            newData.setDescription(data.getDescription());
            newData.setEndDate(getXMLGregorianCalendar(data.getEndDate()));
            newData.setForumId(data.getForumId());
            newData.setNum_for(data.getNum_for());
            newData.setNum_reg(data.getNum_reg());
            newData.setNum_sub(data.getNum_sub());
            newData.setProjectId(data.getProjectId());
            newData.setSname(data.getSname());
            newData.setStartDate(getXMLGregorianCalendar(data.getStartDate()));
            newData.setType(data.getType());
            newData.setCreateUser(data.getCreateUser());
            newData.setPperm(data.getPperm());
            newData.setCperm(data.getCperm());
	    newData.setSpecReviewStatus(data.getSpecReviewStatus());
            newData.setSubmissionEndDate(getXMLGregorianCalendar(data.getSubmissionEndDate()));
            ret.add(newData);
        }

        // sort/group by project id
        Collections.sort(ret,
            new Comparator() {
                public int compare(Object o1, Object o2) {
                    CommonProjectContestData p1 = (CommonProjectContestData) o1;
                    CommonProjectContestData p2 = (CommonProjectContestData) o2;

                    return p1.getProjectId().compareTo(p2.getProjectId());
                }
            });
        logger.debug("Exit getCommonProjectContestDataByContestData");

        return ret;
    }

    /**
     * <p>
     * BURG-1716: We need to add a method to get software contest by project id,
     * the method wil get all OR project related data, then from project
     * property to get comp version id then to call getAssetByVersionId to get
     * assetDTO, please check create software contest to see what data need to
     * be returned.
     * </p>
     *
     * @param projectId
     *            the OR Project Id
     * @return SoftwareCompetition
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since BURG-1716
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SoftwareCompetition getSoftwareContestByProjectId(long projectId)
        throws ContestServiceException {
        logger.debug("getSoftwareContestByProjectId (" + projectId + ")");

        SoftwareCompetition contest = new SoftwareCompetition();

        try {
            FullProjectData fullProjectData = this.projectServices.getFullProjectData(projectId);
            Long compVersionId = Long.parseLong(fullProjectData.getProjectHeader()
                                                               .getProperty(PROJECT_TYPE_INFO_EXTERNAL_REFERENCE_KEY));
            contest.setAssetDTO(this.catalogService.getAssetByVersionId(
                    compVersionId));
            contest.setProjectHeader(fullProjectData.getProjectHeader());
            contest.setProjectData(fullProjectData);
            contest.setProjectPhases(fullProjectData);
            contest.getProjectPhases()
                   .setId(fullProjectData.getProjectHeader().getId());
            contest.setId(projectId);
            contest.setProjectResources(fullProjectData.getResources());

            com.topcoder.project.phases.Phase[] allPhases = fullProjectData.getAllPhases();

            // this is to avoid cycle
            for (int i = 0; i < allPhases.length; i++) {
                allPhases[i].setProject(null);
                allPhases[i].clearDependencies();
            }

            // set project start date in production date
            contest.getAssetDTO()
                   .setProductionDate(getXMLGregorianCalendar(
                    contest.getProjectPhases().getStartDate()));

            // set null to avoid cycle
            contest.getAssetDTO().setDependencies(null);

            if (contest.getAssetDTO().getForum() != null) {
                contest.getAssetDTO().getForum().setCompVersion(null);
            }

            if (contest.getAssetDTO().getLink() != null) {
                contest.getAssetDTO().getLink().setCompVersion(null);
            }

            if ((contest.getAssetDTO().getDocumentation() != null) &&
                    (contest.getAssetDTO().getDocumentation().size() > 0)) {
                for (CompDocumentation doc : contest.getAssetDTO()
                                                    .getDocumentation()) {
                    doc.setCompVersion(null);
                }
            }

            logger.debug("Exit getSoftwareContestByProjectId (" + projectId +
                ")");

            return contest;
        } catch (ProjectServicesException pse) {
            logger.error("Fail to get project data from project services.", pse);
			sessionContext.setRollbackOnly();
            throw new ContestServiceException("Fail to get project data from project services.",
                pse);
        } catch (NumberFormatException nfe) {
            logger.error("the properites 'Version ID' is not of Long value in project.",
                nfe);
			sessionContext.setRollbackOnly();
            throw new ContestServiceException("the properites 'Version ID' is not of Long value in project.",
                nfe);
        } catch (EntityNotFoundException e) {
            logger.error("the version id does not exist.", e);
			sessionContext.setRollbackOnly();
            throw new ContestServiceException("the version id does not exist.",
                e);
        } catch (com.topcoder.catalog.service.PersistenceException e) {
            logger.error("Fail to get project asset.", e);
			sessionContext.setRollbackOnly();
            throw new ContestServiceException("Fail to get project asset.", e);
        }
    }

    /**
     * <p>
     * Gets the list of project and their permissions (including permissions for
     * the parent tc project)
     * </p>
     *
     * <p>
     * Updated for Cockpit Project Admin Release Assembly v1.0 - software
     * projects also included.
     * </p>
     *
     * @param createdUser
     *            user for which to get the permissions
     * @return list of project and their permissions.
     *
     * @since TCCC-1329
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CommonProjectPermissionData> getCommonProjectPermissionDataForUser(
        long createdUser) throws PersistenceException {
        logger.debug("getCommonProjectPermissionDataForUser (" + createdUser +
            ")");

        List<com.topcoder.service.studio.contest.SimpleProjectPermissionData> studioPermissions =
            studioService.getSimpleProjectPermissionDataForUser(createdUser);
        List<com.topcoder.management.project.SimpleProjectPermissionData> softwarePermissions =
            projectServices.getSimpleProjectPermissionDataForUser(createdUser);

        List<CommonProjectPermissionData> ret = new ArrayList<CommonProjectPermissionData>();

        for (com.topcoder.service.studio.contest.SimpleProjectPermissionData data : studioPermissions) {
            CommonProjectPermissionData newdata = new CommonProjectPermissionData();
            newdata.setContestId(data.getContestId());
            newdata.setProjectId(data.getProjectId());
            newdata.setCfull(data.getCfull());
            newdata.setCname(data.getCname());
            newdata.setCread(data.getCread());
            newdata.setCwrite(data.getCwrite());
            newdata.setPfull(data.getPfull());
            newdata.setPname(data.getPname());
            newdata.setPread(data.getPread());
            newdata.setPwrite(data.getPwrite());
            newdata.setStudio(data.isStudio());
            ret.add(newdata);
        }

        for (com.topcoder.management.project.SimpleProjectPermissionData data : softwarePermissions) {
            CommonProjectPermissionData newdata = new CommonProjectPermissionData();
            newdata.setContestId(data.getContestId());
            newdata.setProjectId(data.getProjectId());
            newdata.setCfull(data.getCfull());
            newdata.setCname(data.getCname());
            newdata.setCread(data.getCread());
            newdata.setCwrite(data.getCwrite());
            newdata.setPfull(data.getPfull());
            newdata.setPname(data.getPname());
            newdata.setPread(data.getPread());
            newdata.setPwrite(data.getPwrite());
            newdata.setStudio(data.isStudio());
            ret.add(newdata);
        }

        logger.debug("Exit getCommonProjectPermissionDataForUser (" +
            createdUser + ")");

        return ret;
    }

    /**
     * <p>
     * Searches the user with the given key.
     * </p>
     *
     * @return list of matching users, empty list if none matches.
     * @since TCCC-1329
     */
    public List<User> searchUser(String key) throws PersistenceException {
        logger.debug("searchUser (" + key + ")");

        return studioService.searchUser(key);
    }

    /**
     * <p>
     * This method updates array of permissions to the persistence.
     * </p>
     *
     * @param permissions
     *            the permissions to update.
     *
     * @throws IllegalArgumentWSException
     *             if the argument is invalid
     * @throws PermissionServiceException
     *             if any error occurs when updating the permission.
     *
     * @since Cockpit Project Admin Release Assembly.
     */
    public void updatePermissions(Permission[] permissions)
        throws PermissionServiceException {
        logger.debug("updatePermissions");
        this.permissionService.updatePermissions(permissions);
        logger.debug("Exit updatePermissions");
    }

    /**
     * <p>
     * Sends the email for specified template to specified to, cc, bcc address
     * from specified from address.
     * </p>
     *
     * @param templateSource
     *            the template source
     * @param templateName
     *            the template name or the file path of the template.
     * @param subject
     *            the email subject line
     * @param toAddr
     *            the to-address of the email
     * @param ccAddr
     *            the from-address of the email
     * @param bccAddr
     *            the bcc-address of the email
     * @param fromAddr
     *            the from-address of the email
     * @param phase
     *            the phase data
     * @throws EmailMessageGenerationException
     *             thrown if error during email generation
     * @throws EmailSendingException
     *             thrown if error during email sending.
     *
     * @since Cockpit Release Assembly for Receipts.
     */
    private void sendEmail(String templateSource, String templateName, String subject, String[] toAddrs, String ccAddr, String bccAddr, String fromAddr, com.topcoder.project.phases.Phase phase)
        throws EmailMessageGenerationException, EmailSendingException {
        boolean messageGenerated = false;

        try {
            // Generate the message body first
            Template template = (templateSource == null)
                ? documentGenerator.getTemplate(templateName)
                : documentGenerator.getTemplate(templateSource, templateName);
            String messageBody = this.emailMessageGenerator.generateMessage(documentGenerator,
                    template, phase);

            logger.debug("Generated following email message of subject [" +
                subject + "] to be sent to [" + fromAddr + "] \n" +
                messageBody);

            // Create a TCSEmailMessage to be sent
            TCSEmailMessage email = new TCSEmailMessage();

            // Set subject, from address and message body.
            email.setSubject(subject);
            email.setFromAddress(fromAddr);
            email.setBody(messageBody);

            ExceptionUtils.checkNull(toAddrs, null, null, "To address must be non-null.");
            for (String toAddr : toAddrs) {
                email.addToAddress(toAddr, TCSEmailMessage.TO);
            }
            
            if (ccAddr != null) {
                email.addToAddress(ccAddr, TCSEmailMessage.CC);
            }

            if (bccAddr != null) {
                email.addToAddress(bccAddr, TCSEmailMessage.BCC);
            }

            // Now the email message is generated successfully
            messageGenerated = true;

            // Send email
            EmailEngine.send(email);
            logger.debug("Sent email message of subject [" + subject +
                "] to [" + fromAddr + "]");
        } catch (BaseException e) {
            rethrowEmailError(e, messageGenerated);
        } catch (ConfigManagerException e) {
            rethrowEmailError(e, messageGenerated);
        } catch (IllegalArgumentException e) {
            rethrowEmailError(e, messageGenerated);
        }
    }

    /**
     * <p>
     * Wrap the given error while sending email and re throw it.
     * </p>
     *
     * <p>
     * If given <code>messageGenerated</code> is false, then this error occurs
     * while generating email message and thus an
     * <code>EmailMessageGenerationException</code> will be thrown. Otherwise it
     * means the error occurs while sending email and thus an
     * <code>EmailSendingException</code> will be thrown.
     * </p>
     *
     * @param e
     *            The root error cause to be wrapped and re thrown.
     * @param messageGenerated
     *            Indicates whether the email message has been generated
     *            successfully.
     *
     * @param phase
     * @throws EmailMessageGenerationException
     *             If <code>messageGenerated</code> is false.
     * @throws EmailSendingException
     *             If <code>messageGenerated</code> is true.
     *
     * @since Cockpit Release Assembly for Receipts.
     */
    private void rethrowEmailError(Throwable e, boolean messageGenerated)
        throws EmailMessageGenerationException, EmailSendingException {
        try {
            if (messageGenerated) {
                throw (e instanceof EmailSendingException)
                ? (EmailSendingException) e
                : new EmailSendingException("Error while sending email.", e);
            } else {
                throw (e instanceof EmailMessageGenerationException)
                ? (EmailMessageGenerationException) e
                : new EmailMessageGenerationException("Error while generating email to be sent.",
                    e);
            }
        } catch (Exception e1) {
            logger.error("*** Could not generate or send an email to creator of contest",
                e1);
        }
    }

    /**
     * Creates and sends email for the activate contest receipt email.
     *
     * @param toAddr
     *            the to address for email send.
     * @param purchasedBy
     *            the name of the person who purchased.
     * @param paymentData
     *            the payment data. it is one of TCPurhcaseOrderPaymentData or
     *            CreditCardPaymentData
     * @param competitionType
     *            the competition type, person activated.
     * @param competitionTitle
     *            the competition title, person activated.
     * @param projectName
     *            the project name, person activated.
     * @param launchTime
     *            the launch of the competition.
     * @param price
     *            the price the person paid
     * @param totalCost
     *            the total price the person paid
     * @param orderNumber
     *            the order number of the purchase.
     *
     * @throws EmailMessageGenerationException
     *             throws if error during email message generation
     * @throws EmailSendingException
     *             throws if error during email sending.
     *
     * @since Cockpit Release Assembly for Receipts
     */
    private void sendActivateContestReceiptEmail(String toAddr,
        String purchasedBy, PaymentData paymentData, String competitionType,
        String competitionTitle, String projectName, Date launchTime,
        Double price, Double totalCost, String orderNumber)
        throws EmailMessageGenerationException, EmailSendingException {
        com.topcoder.project.phases.Phase phase = new com.topcoder.project.phases.Phase();

        setReceiptEmailCommonProperties(phase, purchasedBy, paymentData,
            competitionType, competitionTitle, projectName);

        phase.setAttribute("LAUNCH_TIME", launchTime);
        phase.setAttribute("CONTEST_COST", price);
        phase.setAttribute("TOTAL_COST", totalCost);

        phase.setAttribute("FROM_ADDRESS", activateContestReceiptEmailFromAddr);

        String file = Thread.currentThread().getContextClassLoader().getResource(
                activateContestReceiptEmailTemplatePath).getFile();
        Logger.getLogger(this.getClass()).debug("File name for template: " + file);
        
        sendEmail(EMAIL_FILE_TEMPLATE_SOURCE_KEY, file, activateContestReceiptEmailSubject.replace("%ORDER_NUMBER%",
                orderNumber), new String[] {toAddr}, null, activateContestReceiptEmailBCCAddr, activateContestReceiptEmailFromAddr,
                phase);
    }

    /**
     * Creates and sends email for the purchase submission receipt email.
     *
     * @param toAddr
     *            the to address for email send.
     * @param purchasedBy
     *            the name of the person who purchased.
     * @param paymentData
     *            the payment data. it is one of TCPurhcaseOrderPaymentData or
     *            CreditCardPaymentData
     * @param competitionType
     *            the competition type
     * @param competitionTitle
     *            the competition title
     * @param projectName
     *            the project name
     * @param subPaymentDatas
     *            the submission payment datas.
     * @param orderNumber
     *            the order number of the purchase.
     *
     * @throws EmailMessageGenerationException
     *             throws if error during email message generation
     * @throws EmailSendingException
     *             throws if error during email sending.
     *
     * @since Cockpit Release Assembly for Receipts
     */
    private void sendPurchaseSubmissionReceiptEmail(String toAddr,
        String purchasedBy, PaymentData paymentData, String competitionType,
        String competitionTitle, String projectName,
        SubmissionPaymentData[] subPaymentDatas, String orderNumber)
        throws EmailMessageGenerationException, EmailSendingException {
        com.topcoder.project.phases.Phase phase = new com.topcoder.project.phases.Phase();

        setReceiptEmailCommonProperties(phase, purchasedBy, paymentData,
            competitionType, competitionTitle, projectName);

        // TODO: let the commented code be here, once document generator is
        // fixed to allow if/else and loop construct we should use that.
        // LinkedList<Map<String, Serializable>> subPrices = new
        // LinkedList<Map<String, Serializable>>();
        double totalCost = 0;
        int j = 0;
        StringBuffer sb = new StringBuffer();

        for (SubmissionPaymentData submissionPaymentData : subPaymentDatas) {
            long submissionId = submissionPaymentData.getId();

            if (submissionPaymentData.isPurchased()) {
                j++;
                // Map<String, Serializable> subPrice = new HashMap<String,
                // Serializable>();
                // subPrice.put("SUB_ID", Long.toString(submissionId));
                // phase.setAttribute("SUB_ID-" + j,
                // Long.toString(submissionId));
                // subPrice.put("PRICE", submissionPaymentData.getAmount());
                // phase.setAttribute("PRICE-" + j,
                // submissionPaymentData.getAmount());
                // phase.setAttribute("SUB_PRICES", subPrices);
                totalCost += submissionPaymentData.getAmount();

                if (j > 0) {
                    sb.append("\n");
                }

                sb.append(Long.toString(submissionId)).append(" - ")
                  .append(submissionPaymentData.getAmount());

                // subPrices.add(subPrice);
            }
        }

        // phase.setAttribute("SUB_PRICES", subPrices);
        phase.setAttribute("SUB_PURCHASE_LIST", sb.toString());
        phase.setAttribute("TOTAL_COST", totalCost);
        phase.setAttribute("FROM_ADDRESS",
            purchaseSubmissionReceiptEmailFromAddr);

        String file = Thread.currentThread().getContextClassLoader().getResource(
                purchaseSubmissionReceiptEmailTemplatePath).getFile();
        Logger.getLogger(this.getClass()).debug("File name for template: " + file);
        sendEmail(EMAIL_FILE_TEMPLATE_SOURCE_KEY, file, purchaseSubmissionReceiptEmailSubject.replace(
                "%ORDER_NUMBER%", orderNumber), new String[] {toAddr}, null, purchaseSubmissionReceiptEmailBCCAddr,
                purchaseSubmissionReceiptEmailFromAddr, phase);
    }

    /**
     * Sets the common properties for the receipt email
     *
     * @param phase
     *            the phase object in which properties need to be set.
     * @param purchasedBy
     *            the name of the person who purchased.
     * @param paymentData
     *            the payment data. it is one of TCPurhcaseOrderPaymentData or
     *            CreditCardPaymentData
     * @param competitionType
     *            the competition type
     * @param competitionTitle
     *            the competition title
     * @param projectName
     *            the project name
     *
     * @since Cockpit Release Assembly for Receipts
     */
    private void setReceiptEmailCommonProperties(
        com.topcoder.project.phases.Phase phase, String purchasedBy,
        PaymentData paymentData, String competitionType,
        String competitionTitle, String projectName) {
        // TODO: keep the commented portion, once if/else start working in
        // document generator we should switch to it.
        StringBuffer sb = new StringBuffer();

        if (paymentData instanceof TCPurhcaseOrderPaymentData) {
            TCPurhcaseOrderPaymentData po = (TCPurhcaseOrderPaymentData) paymentData;

            sb.append("Client Name:").append(po.getClientName());
            sb.append("\n    ");
            sb.append("Project Name:").append(po.getProjectName());
            sb.append("\n    ");
            sb.append("PO #:").append(po.getPoNumber());
        } else if (paymentData instanceof CreditCardPaymentData) {
            CreditCardPaymentData cc = (CreditCardPaymentData) paymentData;

            sb.append(cc.getFirstName());
            sb.append("\n    ");
            sb.append(cc.getAddress());
            sb.append("\n    ");
            sb.append(cc.getCity()).append(", ").append(cc.getState())
              .append(cc.getZipCode());
            sb.append("\n    ");
            sb.append(cc.getCountry());
        }

        phase.setAttribute("PURCHASER_DETAILS", sb.toString());

        phase.setAttribute("PURCHASED_BY", purchasedBy);
        phase.setAttribute("COMPETITION_TYPE", competitionType);
        phase.setAttribute("COMPETITION_TITLE", competitionTitle);
        phase.setAttribute("PROJECT_NAME", projectName);
    }

	/**
     * Gets the spec reviews for specified contest id.
     * 
     * @param contestId
     *            the contest id
     * @param studio
     *            indicates whether the specified contest id is for studio contests.
     * 
     * @return the spec review that matches the specified contest id.
     * 
     * @throws ContestServiceException
     *             if any error during retrieval/save from persistence
     * @since Cockpit Launch Contest - Inline Spec Review Part 2
     */
    public SpecReview getSpecReviews(long contestId, boolean studio) throws ContestServiceException {
        try {
            return this.specReviewService.getSpecReviews(contestId, studio);
        } catch (SpecReviewServiceException e) {
            throw new ContestServiceException("Error during getSpecReviews", e);
        }
    }

    /**
     * Save specified review comment and review status for specified section and specified contest id to persistence.
     * 
     * @param contestId
     *            the contest id
     * @param studio
     *            indicates whether the specified contest id is for studio contests.
     * @param sectionName
     *            the section name
     * @param comment
     *            the comment
     * @param isPass
     *            the is pass
     * @param role
     *            the user role type           
     * 
     * @throws ContestServiceException
     *             if any error during retrieval/save from persistence
     * @since Cockpit Launch Contest - Inline Spec Review Part 2
     */
    public void saveReviewStatus(long contestId, boolean studio, String sectionName, String comment, boolean isPass, String role)
            throws ContestServiceException {
        try {
            this.specReviewService.saveReviewStatus(contestId, studio, sectionName, comment, isPass, role);
        } catch (SpecReviewServiceException e) {
            throw new ContestServiceException("Error during saveReviewStatus", e);
        }
    }

    /**
     * Save specified review comment for specified section and specified contest id to persistence.
     * 
     * @param contestId
     *            the contest id
     * @param studio
     *            indicates whether the specified contest id is for studio contests.
     * @param sectionName
     *            the section name
     * @param comment
     *            the comment
     * @param role
     *            the user role type           
     * 
     * @throws ContestServiceException
     *             if any error during retrieval/save from persistence
     * @since Cockpit Launch Contest - Inline Spec Review Part 2
     */
    public void saveReviewComment(long contestId, boolean studio, String sectionName, String comment, String role)
            throws ContestServiceException {
        try {
            this.specReviewService.saveReviewComment(contestId, studio, sectionName, comment, role);
        } catch (SpecReviewServiceException e) {
            throw new ContestServiceException("Error during saveReviewComment", e);
        }
    }

    /**
     * Mark review comment with specified comment id as seen.
     * 
     * @param commentId
     *            the comment id
     * 
     * @throws ContestServiceException
     *             if any error during retrieval/save from persistence
     * @since Cockpit Launch Contest - Inline Spec Review Part 2
     */
    public void markReviewCommentSeen(long commentId) throws ContestServiceException {
        try {
            this.specReviewService.markReviewCommentSeen(commentId);
        } catch (SpecReviewServiceException e) {
            throw new ContestServiceException("Error during markReviewCommentSeen", e);
        }
    }
    
    /**
     * Marks 'review done' by reviewer of the specs for specified contest.
     * Persistence is updated and all end users having write/full permission on the contest are notified by email.
     * 
     * @param contestId
     *            the specified contest id.
     * @param contestName
     *            the contest name
     * @param studio
     *            whether contest is studio or not.
     * @tcDirectProjectId
     *            the tc direct project id.            
     * @throws ContestServiceException
     *             if any error during retrieval/save from persistence
     * @since 1.0.1
     */
    public void markReviewDone(long contestId, String contestName, boolean studio, long tcDirectProjectId) throws ContestServiceException {
        try {
            // get updates.
            List<UpdatedSpecSectionData> updates = this.specReviewService.getReviewerUpdates(contestId, studio);
            
            this.specReviewService.markReviewDone(contestId, studio);
            
            // notify all users who have write permission by email.
            Set<String> toAddresses = new HashSet<String>();
            
            List<Permission> permissions = this.permissionService.getPermissionsByProject(contestId);
            for (Permission p : permissions) {
                if (p.getPermissionType().getPermissionTypeId() == 6 || p.getPermissionType().getPermissionTypeId() == 5) {
                    String toAddr = this.userService.getEmailAddress(p.getUserHandle());
                    toAddresses.add(toAddr);
                }
            }
            
            permissions = this.permissionService.getPermissionsByProject(tcDirectProjectId);
            for (Permission p : permissions) {
                if (p.getPermissionType().getPermissionTypeId() == 2 || p.getPermissionType().getPermissionTypeId() == 3) {
                    String toAddr = this.userService.getEmailAddress(p.getUserHandle());
                    toAddresses.add(toAddr);
                }
            }
            
            // send email to all toAddresses.
            sendSpecReviewNotificationEmail(toAddresses.toArray(new String[0]), updates, contestName);
        } catch (SpecReviewServiceException e) {
            throw new ContestServiceException("Error during markReviewDone", e);
        } catch (PermissionServiceException e) {
            throw new ContestServiceException("Error during retrieving permissions", e);
        } catch (UserServiceException e) {
            throw new ContestServiceException("Error during retrieving permissions", e);
        } catch (EmailMessageGenerationException e) {
            // ignore email error.
            Logger.getLogger(this.getClass()).error("Email Error : "+e);
        } catch (EmailSendingException e) {
            // ignore email error.
            Logger.getLogger(this.getClass()).error("Email Error : "+e);
        }
    }

    /**
     * Marks 'ready for review' by the writer of the specs for specified contest.
     * Persistence is updated, on update the spec would appear as review opportunity on tc site.
     * 
     * @param contestId
     *            the specified contest id.
     * @param studio
     *            whether contest is studio or not.
     * @throws ContestServiceException
     *             if any error during retrieval/save from persistence
     * @since 1.0.1
     */
    public void markReadyForReview(long contestId, boolean studio) throws ContestServiceException {
        try {
            this.specReviewService.markReadyForReview(contestId, studio);
        } catch (SpecReviewServiceException e) {
            throw new ContestServiceException("Error during markReadyForReview", e);
        }
    }

    /**
     * Marks 'resubmit for review' by the writer of the specs for specified contest.
     * Persistence is updated. Reviewer (if any) is notified about the updates.
     * 
     * @param contestId
     *            the specified contest id.
     * @param contestName
     *            the contest name
     * @param studio
     *            whether contest is studio or not.
     * @param reviewerUserId
     *            reviewer user id.
     * @throws ContestServiceException
     *             if any error during retrieval/save from persistence
     * @since 1.0.1
     */
    public void resubmitForReview(long contestId, String contestName, boolean studio, long reviewerUserId) throws ContestServiceException {
        try {
            // get updates.
            List<UpdatedSpecSectionData> updates = this.specReviewService.getReviewerUpdates(contestId, studio);
            
            this.specReviewService.resubmitForReview(contestId, studio);
          
            // do not send email if no updates are there.
            if (updates.size() <= 0) {
                return;
            }
            
            // notify the reviewer about updates.
            String reviewerEmail = this.userService.getEmailAddress(reviewerUserId);

            if (reviewerEmail != null) {
                sendSpecReviewNotificationEmail(new String[] {reviewerEmail}, updates, contestName);
            }
        } catch (SpecReviewServiceException e) {
            throw new ContestServiceException("Error during resubmit for review.", e);
        } catch (UserServiceException e) {
            throw new ContestServiceException("Error during retrieving email for reviewer.", e);
        } catch (EmailMessageGenerationException e) {
            // ignore any email errors.
            Logger.getLogger(this.getClass()).error("Email Error : "+e);
        } catch (EmailSendingException e) {
            // ignore any email errors.
            Logger.getLogger(this.getClass()).error("Email Error : "+e);
        }
    }
    
    /**
     * Sends spec review notification email
     * 
     * @param toAddrs
     *            array of to addresses to which spec review notification email should be sent.
     * @param updates
     *            the data about updated sections.
     * @param contestName
     *            the name of the contest
     * @throws EmailMessageGenerationException
     *             thrown if error during email message generation
     * @throws EmailSendingException
     *             thrown if error during email sending.
     * @since 1.0.2             
     */
    private void sendSpecReviewNotificationEmail(String[] toAddrs, List<UpdatedSpecSectionData> updates,
            String contestName) throws EmailMessageGenerationException, EmailSendingException {
        com.topcoder.project.phases.Phase phase = new com.topcoder.project.phases.Phase();

        StringBuffer sb = new StringBuffer();
        boolean first = true;
        for (UpdatedSpecSectionData d : updates) {
            StringBuffer s = new StringBuffer();
            s.append("Section: ").append(d.getSectionName()).append("\n");
            s.append("Review Status: ").append(d.getStatus()).append("\n");
            s.append("Updated By: ").append(d.getUser()).append("\n");
            s.append("Comment: ").append(d.getComment());

            if (!first) {
                sb.append("\n\n");
            }

            sb.append(s.toString());
            first = false;
        }

        phase.setAttribute("SECTIONS", sb.toString());

        String file = Thread.currentThread().getContextClassLoader().getResource(
                specReviewNotificationEmailTemplatePath).getFile();
        Logger.getLogger(this.getClass()).debug("File name for template: " + file);
        sendEmail(EMAIL_FILE_TEMPLATE_SOURCE_KEY, file, specReviewNotificationEmailSubject.replace(
                "%CONTEST_NAME%", contestName), toAddrs, null, specReviewNotificationEmailBCCAddr,
                specReviewNotificationEmailFromAddr, phase);
    }
    
    /**
     * Gets all contest fees by billing project id.
     * 
     * @param projectId the billing project id
     * @return the list of project contest fees for the given project id
     * @throws ContestServiceException  if any persistence or other error occurs
     * @since 1.0.1
     */
    public List<ProjectContestFee> getContestFeesByProject(long projectId) throws ContestServiceException {
        try {
            return this.billingProjectDAO.getContestFeesByProject(projectId);
        } catch(DAOException e) {
            throw new ContestServiceException("Error in retrieving contest fees by project: " + projectId, e);
        }
    }

    /**
     * Get the user contest by user name Return empty list if none found
     *
     * @param userName
     *            the user name to get the user contest
     * @return a list of matching studio competitions
     * @throws IllegalArgumentException
     *             if userName is null or empty
     * @throws ContestServiceException
     *             if any other error occurs
     * @since 1.1
     */
    public List<StudioCompetition> getUserContests(String userName)
        throws ContestServiceException {
        String methodName = "getUserContests";
        logger.info("Enter: " + methodName);

        if ((userName == null) || userName.trim().equals("")) {
            throw new IllegalArgumentException(
                "The userName could not be null or empty");
        }

        try {
            List<StudioCompetition> studioCompetitions = convertToCompetitions(CompetionType.STUDIO,
                    studioService.getUserContests(userName));
            logger.info("Exit: " + methodName);

            return studioCompetitions;
        } catch (IllegalArgumentWSException iae) {
            /* The exception occured in converting to stuidoCompetition phase */
            logger.error(iae.getMessage(), iae);
            throw new ContestServiceException(iae.getMessage(), iae);
        } catch (PersistenceException pe) {
            logger.error(pe.getMessage(), pe);
            throw new ContestServiceException(pe.getMessage(), pe);
        }
    }

    /**
     * get milestone submissions for contest
     *
     * @return empty list of none submission found for the given contest id.
     * @param contestId
     *            The contest id to get the milestone submissions.
     * @throws IllegalArgumentException
     *             if long argument is negative
     * @throws ContestServiceException
     *             if any other error occurs
     * @since 1.1
     */
    public List<SubmissionData> getMilestoneSubmissionsForContest(
        long contestId) throws ContestServiceException {
        String methodName = "getMilestoneSubmissionsForContest";
        logger.info("Enter: " + methodName);

        if (contestId < 0L) {
            throw new IllegalArgumentException(
                "contestId could not be a negative (" + contestId + ")");
        }

        try {
            /*
             * The return list dose not be null, the studioserivce will return
             * the empty list when not found(Based on studio service 1.3 design)
             */
            List<SubmissionData> submissions = studioService.getMilestoneSubmissionsForContest(contestId);
            logger.info("Exit: " + methodName);

            return submissions;
        } catch (PersistenceException pe) {
            logger.error(pe.getMessage(), pe);
            throw new ContestServiceException(pe.getMessage(), pe);
        }
    }

    /**
     * get final submissions for contest
     *
     * @return empty list of none submission found for the given contest id.
     * @param contestId
     *            The contest id to get the final submissions
     * @throws IllegalArgumentException
     *             if long argument is negative
     * @throws ContestServiceException
     *             if any other error occurs
     * @since 1.1
     */
    public List<SubmissionData> getFinalSubmissionsForContest(long contestId)
        throws ContestServiceException {
        String methodName = "getFinalSubmissionsForContest";
        logger.info("Enter: " + methodName);

        if (contestId < 0L) {
            throw new IllegalArgumentException(
                "contestId could not be a negative (" + contestId + ")");
        }

        try {
            /*
             * The return list dose not be null, the studioserivce will return
             * the empty list when not found(Based on studio service 1.3 design)
             */
            List<SubmissionData> submissions = studioService.getFinalSubmissionsForContest(contestId);
            logger.info("Exit: " + methodName);

            return submissions;
        } catch (PersistenceException pe) {
            logger.error(pe.getMessage(), pe);
            throw new ContestServiceException(pe.getMessage(), pe);
        }
    }

    /**
     * set submission milestone prize If given submission has already been
     * associated with the given milestone prize before, ContestServiceException
     * will be thrown. It's required that the contest field of the submission of
     * given id is one of the contests set of the milestone prize, otherwise,
     * ContestServiceException will be thrown. If the MilestonePrize with given
     * id has reached the max number of submissions, ContestServiceException
     * will be thrown.
     *
     * @param submissionId
     *            The submission id
     * @param milestonePrizeId
     *            The milestone prize id
     * @throws IllegalArgumentException
     *             if long argument is negative
     * @throws ContestServiceException
     *             if any other error occurs
     * @since 1.1
     */
    public void setSubmissionMilestonePrize(long submissionId,
        long milestonePrizeId) throws ContestServiceException {
        String methodName = "setSubmissionMilestonePrize";
        logger.info("Enter: " + methodName);

        if ((submissionId < 0L) || (milestonePrizeId < 0)) {
            throw new IllegalArgumentException(
                "submission Id and milestonePrizeId could not be a negative (" +
                submissionId + "," + milestonePrizeId + ")");
        }

        try {
            studioService.setSubmissionMilestonePrize(submissionId,
                milestonePrizeId);
        } catch (PersistenceException pe) {
            logger.error(pe.getMessage(), pe);
            throw new ContestServiceException(pe.getMessage(), pe);
        }

        logger.info("Exit: " + methodName);
    }

    
    /**
     * <p>
     * Void a previous payment
     *
     * @param processor
     * @param result
     */
    private void voidPayment(PaymentProcessor processor, PaymentResult result, PaymentData paymentData)
    {
        try
        {
            if (result == null)
            {
                return;
            }

            if (paymentData instanceof TCPurhcaseOrderPaymentData)
            {
                return;
            }

            processor.voidPayment(result.getReferenceNumber());
        }
        catch (Exception e)
        {
            logger.error("Error voiding " + result.getReferenceNumber() + ": " +e.getMessage(), e);
        }
        
    }
}
