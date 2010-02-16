/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.contest.ejb;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Enumeration;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
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
import javax.xml.datatype.Duration;

import org.jboss.logging.Logger;
import org.jboss.ws.annotation.EndpointConfig;

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

import com.topcoder.management.project.DesignComponents;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectPropertyType;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.review.data.Comment;
import com.topcoder.message.email.EmailEngine;
import com.topcoder.message.email.TCSEmailMessage;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.service.ContestSaleData;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.project.service.ProjectServicesException;
import com.topcoder.project.service.ScorecardReviewData;
import com.topcoder.security.auth.module.UserProfilePrincipal;
import com.topcoder.service.contest.eligibility.ContestEligibility;
import com.topcoder.service.contest.eligibility.GroupContestEligibility;
import com.topcoder.service.contest.eligibility.dao.ContestEligibilityManager;
import com.topcoder.service.contest.eligibility.dao.ContestEligibilityPersistenceException;
import com.topcoder.service.contest.eligibilityvalidation.ContestEligibilityValidationManager;
import com.topcoder.service.contest.eligibilityvalidation.ContestEligibilityValidationManagerException;
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
import com.topcoder.service.studio.MilestonePrizeData;
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
import com.topcoder.web.ejb.user.UserTermsOfUse;
import com.topcoder.web.ejb.user.UserTermsOfUseHome;
import com.topcoder.web.ejb.project.ProjectRoleTermsOfUse;
import com.topcoder.web.ejb.project.ProjectRoleTermsOfUseHome;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.Property;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.management.resource.search.ResourceFilterBuilder;

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
 * Changes in v1.2.2 - Cockpit Release Assembly 11
 * Add method getDesignComponents to get design components.
 * </p>
	* <p>
 * Changes in v1.3 (Prototype Conversion Studio Multi-Rounds Assembly - Submission Viewer UI):
 * - Added a flag to updateSubmissionUserRank method to support ranking milestone submissions.
 * - Added support for milestone prizes payment.
 * </p>
 * 
 * <p>
 * Changes in v1.3.1 Added elegibility services.
 * </p>
 * 
 * <p>
 * Changes in v1.3.2 Added support for eligibility services.
 * </p>
 * <p>
 * Changes in v1.3.3 Added permission check.
 * </p>
 * <p>
 * Changes in v1.4 (Cockpit Spec Review Backend Service Update v1.0):
 * - Added method to create specification review project for an existing project.
 * - Added method to get scorecard and review information for a specific project.
 * - Added method to upload a mock submission / final fixes to the associated specification review of a project
 *   to make it ready for review.
 * - Added method to add comments to an existing review.
 * </p>
 * <p>
 * Change in v1.4.1 (Cockpit Spec Review -stage 2 v1.0)
 * - Add spec review project id
 * - After activiation of contests, create spec review project
 * </p>
 * <p>
 * Changes in v1.5 (Cockpit Release Assembly - Contest Repost and New Version v1.0):
 * - Added method to re open failed software contest.
 * - Added method to create new version for development or design contest.
 * - Refactor the create/update-software-contest methods
 * </p>
 * @author snow01, pulky, murphydog, waits
 * @version 1.5
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

    //TODO, please start move these constants to the right classes.

    /**
     * Private constant specifying payments project info type.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final String PAYMENTS_PROJECT_INFO_TYPE = "Payments";
    
    

    /**
     * <p>
     * Represents on value for the autopilot option project property
     * </p>
     *
     * @since 1.3
     */
    private static final String PROJECT_TYPE_INFO_AUTOPILOT_OPTION_VALUE_ON = "On";

    /**
     * Private constant specifying resource role manager id
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     * @deprecated // use constants in com.topcoder.management.resource.ResourceRole
     */
    private static final long RESOURCE_ROLE_MANAGER_ID = 13;

    /**
     * Private constant specifying resource role manager name
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     * @deprecated // use constants in com.topcoder.management.resource.ResourceRole
     */
    private static final String RESOURCE_ROLE_MANAGER_NAME = "Manger";

    /**
     * Private constant specifying resource role manager desc
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     * @deprecated // use constants in com.topcoder.management.resource.ResourceRole
     */
    private static final String RESOURCE_ROLE_MANAGER_DESC = "Manger";


    /**
     * Private constant specifying resource role manager id
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     * @deprecated // use constants in com.topcoder.management.resource.ResourceRole
     */
    private static final long RESOURCE_ROLE_OBSERVER_ID = 12;

    /**
     * Private constant specifying resource role manager name
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     * @deprecated // use constants in com.topcoder.management.resource.ResourceRole
     */
    private static final String RESOURCE_ROLE_OBSERVER_NAME = "Observer";

    /**
     * Private constant specifying resource role manager desc
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     * @deprecated // use constants in com.topcoder.management.resource.ResourceRole
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
     * The const string for configuration files.
     * @since 1.2.2
     */
    private static final String CONTEST_ELIGIBILITY_MAPPING_PREFIX = "ContestEligibilityMapping";

    /**
     * The const string for configuration name sapce.
     * @since 1.2.2
     */
    private static final String CONTEST_ELIGIBILITY_MAPPING_NAMESPACE
    = "com.topcoder.service.facade.contest.ejb.ContestServiceFacadeBean";

    /**
     * The const string for configuration EligibilityName key.
     * @since 1.2.2
     */
    private static final String ELIGIBILITY_NAME = "EligibilityName";
    
    /**
     * The const string for configuration EligibilityGroupId key.
     * @since 1.2.2
     */
    private static final String ELIGIBILITY_ID = "EligibilityGroupId";
    

    /**
     * Private constant specifying administrator role.
     */
    private static final String ADMIN_ROLE = "Cockpit Administrator";



    /**
     * Private constant specifying project submission phase name.
     *
     * @since 1.4
     */
    private static final String PROJECT_SUBMISSION_PHASE_NAME = "Submission";

    /**
     * Private constant specifying project final fix phase name.
     *
     * @since 1.4
     */
    private static final String PROJECT_FINAL_FIX_PHASE_NAME = "Final Fix";
    
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
     * A <code>ContestEligibilityValidationManager</code> providing access to available
     * <code>Contest Eligibility Validation EJB</code>.
     * </p>
     */
    @EJB(name = "ejb/ContestEligibilityValidation")
    private ContestEligibilityValidationManager contestEligibilityValidationManager = null;

    /**
     * <p>
     * A <code>ContestEligibilityManager</code> providing access to available
     * <code>Contest Eligibility Persistence EJB</code>.
     * </p>
     */
    @EJB(name = "ejb/ContestEligibilityPersistence")
    private ContestEligibilityManager contestEligibilityManager = null;

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
     * userBeanProviderUrl is used in the jndi context to get the user bean.
     * It's injected, non-null and non-empty after set.
     */
    @Resource(name = "userBeanProviderUrl")
    private String userBeanProviderUrl;

    /**
     * userBeanProviderUrl is used in the jndi context to get the user bean.
     * It's injected, non-null and non-empty after set.
     */
    @Resource(name = "projectBeanProviderUrl")
    private String projectBeanProviderUrl;



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
     * The default prize for spec reviews
     *
     * @since 1.4
     */
    @Resource(name = "specReviewPrize")
    private Double specReviewPrize;

    /**
     * The mock file path to use for submissions
     *
     * @since 1.4
     */
    @Resource(name = "mockSubmissionFilePath")
    private String mockSubmissionFilePath;

    /**
     * The mock file name to use for submissions
     *
     * @since 1.4
     */
    @Resource(name = "mockSubmissionFileName")
    private String mockSubmissionFileName;

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
     *            a <code>long</code> providing the ID of a client the new
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
    public StudioCompetition createContest(StudioCompetition contest, long tcDirectProjectId) 
        throws PersistenceException {
        logger.debug("createContest");

        try
        {

            ContestData contestData = convertToContestData(contest);
            //checks the permission
            checkStudioProjectPermission(tcDirectProjectId);
            checkStudioBillingProjectPermission(contestData);            

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

            ContestData createdContestData = studioService.createContest(contestData, tcDirectProjectId);

            if (contestData.getBillingProject() > 0) {
                persistContestEligility(createdContestData.getContestId(), contestData.getBillingProject(), null, true);
            }

            logger.debug("Exit createContest");

            return (StudioCompetition) convertToCompetition(CompetionType.STUDIO,
                createdContestData);
        }
        catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
        catch (DAOException e) {
            sessionContext.setRollbackOnly();
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (Exception e) {
            sessionContext.setRollbackOnly();
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
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

        if (!sessionContext.isCallerInRole(ADMIN_ROLE))
        {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            long userId = p.getUserId();
            if (!studioService.checkContestPermission(contestId, true, userId))
            {
                throw new PersistenceException("No read permission on contest");
            }
        }

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

        if (!sessionContext.isCallerInRole(ADMIN_ROLE))
        {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            long userId = p.getUserId();
            if (!studioService.checkProjectPermission(tcDirectProjectId, true, userId))
            {
                throw new PersistenceException("No read permission on project");
            }
        }

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
        logger.debug("updateContest (" + contest.getContestData().getContestId() + ")");

        try
        {

            ContestData studioContest = convertToContestData(contest);

            if (!sessionContext.isCallerInRole(ADMIN_ROLE))
            {
                UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
                long userId = p.getUserId();
                String userName = p.getName();
                if (!studioService.checkContestPermission(contest.getContestData().getContestId(), contest.getContestData().getTcDirectProjectId(), false, userId))
                {
                    throw new PersistenceException("No write permission on contest");
                }

                if (studioContest.getBillingProject() > 0)
                { 
                    if (!billingProjectDAO.checkClientProjectPermission(userName, studioContest.getBillingProject()))
                    {
                        throw new PersistenceException("No permission on billing project " + studioContest.getBillingProject());
                    }
                }
            }

            

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
            logger.debug("Exit updateContest (" + contest.getContestData().getContestId()  + ")");
        }
        catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
        catch (DAOException e) {
            sessionContext.setRollbackOnly();
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (Exception e) {
            sessionContext.setRollbackOnly();
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
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

        if (!sessionContext.isCallerInRole(ADMIN_ROLE))
        {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            long userId = p.getUserId();
            if (!studioService.checkContestPermission(contestId, false, userId))
            {
                throw new PersistenceException("No write permission on contest");
            }
        }

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

        if (!sessionContext.isCallerInRole(ADMIN_ROLE))
        {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            long userId = p.getUserId();
            if (!studioService.checkContestPermission(contestId, false, userId))
            {
                throw new PersistenceException("No write permission on contest");
            }
        }

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

        if (!sessionContext.isCallerInRole(ADMIN_ROLE))
        {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            long userId = p.getUserId();
            if (!studioService.checkContestPermission(document.getContestId(), false, userId))
            {
                throw new PersistenceException("No write permission on contest");
            }
        }

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

        if (!sessionContext.isCallerInRole(ADMIN_ROLE))
        {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            long userId = p.getUserId();
            if (!studioService.checkContestPermission(contestId, true, userId))
            {
                throw new PersistenceException("No read permission on contest");
            }
        }

        return this.studioService.retrieveSubmissionsForContest(contestId);
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

        if (!sessionContext.isCallerInRole(ADMIN_ROLE))
        {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            long userId = p.getUserId();
            if (!studioService.checkSubmissionPermission(submission.getSubmissionId(), false, userId))
            {
                throw new PersistenceException("No read permission on contest");
            }
        }

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

        if (!sessionContext.isCallerInRole(ADMIN_ROLE))
        {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            long userId = p.getUserId();
            if (!studioService.checkSubmissionPermission(submissionId, false, userId))
            {
                throw new PersistenceException("No write permission on contest");
            }
        }

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

        if (!sessionContext.isCallerInRole(ADMIN_ROLE))
        {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            long userId = p.getUserId();
            if (!studioService.checkSubmissionPermission(submissionId, true, userId))
            {
                throw new PersistenceException("No read permission on contest");
            }
        }

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

        if (!sessionContext.isCallerInRole(ADMIN_ROLE))
        {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            long userId = p.getUserId();
            if (!studioService.checkSubmissionPermission(submissionId, false, userId))
            {
                throw new PersistenceException("No write permission on contest");
            }
        }

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
    private ContestPaymentData createContestPayment(
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
    private List<ContestPaymentData> getContestPayments(long contestId)
        throws PersistenceException {
        logger.debug("getContestPayments");

        if (!sessionContext.isCallerInRole(ADMIN_ROLE))
        {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            long userId = p.getUserId();
            if (!studioService.checkContestPermission(contestId, true, userId))
            {
                throw new PersistenceException("No read permission on contest");
            }
        }

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
    private void editContestPayment(ContestPaymentData contestPayment)
        throws PersistenceException {
        logger.debug("editContestPayments");

        if (!sessionContext.isCallerInRole(ADMIN_ROLE))
        {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            long userId = p.getUserId();
            if (!studioService.checkContestPermission(contestPayment.getContestId(), false, userId))
            {
                throw new PersistenceException("No write permission on contest");
            }
        }

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

        if (!sessionContext.isCallerInRole(ADMIN_ROLE))
        {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            long userId = p.getUserId();
            if (!studioService.checkContestPermission(contestId, false, userId))
            {
                throw new PersistenceException("No write permission on contest");
            }
        }

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

        if (!sessionContext.isCallerInRole(ADMIN_ROLE))
        {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            long userId = p.getUserId();
            if (!studioService.checkSubmissionPermission(submissionId, false, userId))
            {
                throw new PersistenceException("No write permission on contest");
            }
        }

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

        if (!sessionContext.isCallerInRole(ADMIN_ROLE))
        {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            long userId = p.getUserId();
            if (!studioService.checkSubmissionPermission(submissionId, true, userId))
            {
                throw new PersistenceException("No read permission on contest");
            }
        }

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

        if (!sessionContext.isCallerInRole(ADMIN_ROLE))
        {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            long userId = p.getUserId();
            if (!studioService.checkSubmissionPermission(submissionId, false, userId))
            {
                throw new PersistenceException("No write permission on contest");
            }
        }

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

        if (!sessionContext.isCallerInRole(ADMIN_ROLE))
        {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            long userId = p.getUserId();
            if (!studioService.checkContestPermission(contestId, false, userId))
            {
                throw new PersistenceException("No write permission on contest");
            }
        }

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

        if (!sessionContext.isCallerInRole(ADMIN_ROLE))
        {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            long userId = p.getUserId();
            if (!studioService.checkContestPermission(contestId, false, userId))
            {
                throw new PersistenceException("No write permission on contest");
            }
        }


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
                
                String poNumber = ((TCPurhcaseOrderPaymentData) paymentData).getPoNumber();
                if (!sessionContext.isCallerInRole(ADMIN_ROLE))
                {
                    UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
                    String userName = p.getName();
                    if (!billingProjectDAO.checkPoNumberPermission(userName, poNumber))
                    {
                        throw new ContestServiceException("No permission on poNumber " + poNumber);
                    }
                }

                // processing purchase order is not in scope of this assembly.
                result = new PaymentResult();
                result.setReferenceNumber(poNumber);
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
            contestPaymentResult.setContestData(tobeUpdatedCompetition.getContestData());

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
            
            //mark ready for spec review
            markReadyForReview(tobeUpdatedCompetition.getId(), true);


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
                tobeUpdatedCompetition =
                    createSoftwareContest(competition, competition.getProjectHeader().getTcDirectProjectId());
            } else {
                competition.setProjectHeaderReason("User Update");
                tobeUpdatedCompetition = 
                    updateSoftwareContest(competition, competition.getProjectHeader().getTcDirectProjectId());                
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

                String poNumber = ((TCPurhcaseOrderPaymentData) paymentData).getPoNumber();
                if (!sessionContext.isCallerInRole(ADMIN_ROLE))
                {
                    UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
                    String userName = p.getName();
                    if (!billingProjectDAO.checkPoNumberPermission(userName, poNumber))
                    {
                        throw new ContestServiceException("No permission on poNumber " + poNumber);
                    }
                }
                // processing purchase order is not in scope of this assembly.
                result = new PaymentResult();
                result.setReferenceNumber(poNumber);
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

            boolean isDevContest = competition.getProjectHeader().getProjectCategory().getId() == DEVELOPMENT_PROJECT_CATEGORY_ID;

            boolean hasEligibility = contestEligibilityManager.haveEligibility(new Long[]{tobeUpdatedCompetition.getProjectHeader().getId()}, false).size() > 0;
    
            // if creating contest, eligiblity is not commited, so above will not get back result
            if (getBillingProjectId(tobeUpdatedCompetition) != 0
                 && getEligibilityGroupId(getBillingProjectId(tobeUpdatedCompetition)) != null)
            {
                hasEligibility = true;
            }
            

            // no need for dev that has design, so all non-dev and dev only will have spec review 
            // and dont create for private
            if ((!isDevContest || projectServices.isDevOnly(tobeUpdatedCompetition.getProjectHeader().getId()))
                  && !hasEligibility) 
            {
                 //create spec review project
                FullProjectData specReview = this.createSpecReview(tobeUpdatedCompetition.getProjectHeader().getId());
                logger.info("spec review project for contest " + specReview.getProjectHeader().getId() + " is created.");

                com.topcoder.project.phases.Phase[] allPhases = specReview.getAllPhases();
                com.topcoder.project.phases.Project projectPhases = new com.topcoder.project.phases.Project();
                projectPhases.setStartDate(new Date());
                projectPhases.setId(specReview.getProjectHeader().getId());

                // open submission and registration
                for (int i = 0; i < allPhases.length; i++) {
                    if (allPhases[i].getPhaseType().getName().equals(PhaseType.SUBMISSION)
                         || allPhases[i].getPhaseType().getName().equals(PhaseType.REGISTRATION))
                    {
                        allPhases[i].setPhaseStatus(PhaseStatus.OPEN);
                    }
                    projectPhases.addPhase(allPhases[i]);
                }    

                projectServices.updatePhases(projectPhases, Long.toString(p.getUserId()));


                 // prepare mock file for upload
                DataHandler dataHandler = new DataHandler(new FileDataSource(mockSubmissionFilePath +
                       mockSubmissionFileName));

                uploadSubmission(specReview.getId(), mockSubmissionFileName, dataHandler);
                logger.info("spec review project ready for reivew ");

                projectPhases.setStartDate(new Date());
                // set submission and registration back to scheduled
                allPhases = projectPhases.getAllPhases();
                for (int i = 0; i < allPhases.length; i++) {
                    if (allPhases[i].getPhaseType().getName().equals(PhaseType.SUBMISSION)
                         || allPhases[i].getPhaseType().getName().equals(PhaseType.REGISTRATION))
                    {
                        allPhases[i].setPhaseStatus(PhaseStatus.SCHEDULED);
                    }
                }    

                projectServices.updatePhases(projectPhases, Long.toString(p.getUserId()));

                //  now turn on auto pilot
                specReview.getProjectHeader().setProperty(ProjectPropertyType.AUTOPILOT_OPTION_PROJECT_PROPERTY_KEY,
                    PROJECT_TYPE_INFO_AUTOPILOT_OPTION_VALUE_ON);
                projectServices.updateProject(specReview.getProjectHeader(), "Turn on auto pilot", Long.toString(p.getUserId()));  

            }

            sendActivateContestReceiptEmail(toAddr, purchasedByUser,
                paymentData, competitionType,
                tobeUpdatedCompetition.getProjectHeader()
                                      .getProperty(ProjectPropertyType.PROJECT_NAME_PROJECT_PROPERTY_KEY),
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

                String poNumber = ((TCPurhcaseOrderPaymentData) paymentData).getPoNumber();
                if (!sessionContext.isCallerInRole(ADMIN_ROLE))
                {
                    UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
                    String userName = p.getName();
                    if (!billingProjectDAO.checkPoNumberPermission(userName, poNumber))
                    {
                        throw new ContestServiceException("No permission on poNumber " + poNumber);
                    }
                }

                result = new PaymentResult();
                result.setReferenceNumber(poNumber);
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
                result.getReferenceNumber(), contestData.getMilestonePrizeData());

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
     * Checks the permission for the given tc-direct-project-id for the current caller.
     * 
     * @param tcDirectProjectId the project id
     * @throws ContestServiceException if user(not admin) does not have the permission
     */
    private void checkSoftwareProjectPermission(long tcDirectProjectId, boolean readOnly) throws ContestServiceException {
        if (!sessionContext.isCallerInRole(ADMIN_ROLE)) {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            long userId = p.getUserId();
            if (!projectServices.checkProjectPermission(tcDirectProjectId, readOnly, userId)) {
                if (readOnly)
                {
                    throw new ContestServiceException("No read permission on project");
                }
                else
                {
                    throw new ContestServiceException("No write permission on project");
                }
            }
        }
    }

     /**
     * Checks the permission for the given tc-direct-project-id for the current caller.
     * 
     * @param tcDirectProjectId the project id
     * @throws PersistenceException if user(not admin) does not have the permission
     */
    private void checkStudioProjectPermission(long tcDirectProjectId) throws PersistenceException {
        if (!sessionContext.isCallerInRole(ADMIN_ROLE)) {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            long userId = p.getUserId();
            if (!studioService.checkProjectPermission(tcDirectProjectId, true, userId)) {
                throw new PersistenceException("No read permission on project");
            }
        }
    }

    /**
     * Checks the permission for the given contestId for the current caller.
     * 
     * @param contestId the project id
     * @throws ContestServiceException if user(not admin) does not have the permission
     */
    private void checkSoftwareContestPermission(long contestId, boolean readOnly) throws ContestServiceException {
        if (!sessionContext.isCallerInRole(ADMIN_ROLE)) {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            long userId = p.getUserId();
            if (!projectServices.checkContestPermission(contestId, readOnly, userId)) {
                if (readOnly)
                {
                    throw new ContestServiceException("No read permission on project");
                }
                else
                {
                    throw new ContestServiceException("No write permission on project");
                }
            }
        }
    }

     /**
     * Checks the billing project permission of the given contest for the current caller.
     * 
     * @param contest the contest to check
     * @throws ContestServiceException if user(not admin) does not have the permission
     */
    private void checkStudioBillingProjectPermission(ContestData contestData) throws PersistenceException, DAOException {
        if (!sessionContext.isCallerInRole(ADMIN_ROLE)) {
            UserProfilePrincipal principal = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            String userName = principal.getName();
            if (contestData.getBillingProject() > 0) {
                if (!billingProjectDAO.checkClientProjectPermission(userName, contestData.getBillingProject())) {
                    throw new PersistenceException("No permission on billing project " + contestData.getBillingProject());
                }
            }
        }
    }

    /**
     * Checks the billing project permission of the given contest for the current caller.
     * 
     * @param contest the contest to check
     * @return billing project id, if it is -1, then no billing project
     * @throws ContestServiceException if user(not admin) does not have the permission
     */
    private void checkBillingProjectPermission(SoftwareCompetition contest) throws ContestServiceException, DAOException {
        if (!sessionContext.isCallerInRole(ADMIN_ROLE)) {
            String billingProject = contest.getProjectHeader().getProperty(ProjectPropertyType.BILLING_PROJECT_PROJECT_PROPERTY_KEY);
            if (billingProject != null  && !billingProject.equals("") && !billingProject.equals("0")) {
                UserProfilePrincipal principal = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
                String userName = principal.getName();
                long clientProjectId = Long.parseLong(billingProject);
                if (!billingProjectDAO.checkClientProjectPermission(userName, clientProjectId)) {
                    throw new ContestServiceException("No permission on billing project " + clientProjectId);
                }
            }
        }
    }

     /**
     * get billing project id
     * 
     * @param contest the contest to check
     * @return billing project id, if it is 0, then no billing project
     */
    private long getBillingProjectId(SoftwareCompetition contest)  {

        String billingProject = contest.getProjectHeader().getProperty(ProjectPropertyType.BILLING_PROJECT_PROJECT_PROPERTY_KEY);

        if (billingProject != null  && !billingProject.equals("") && !billingProject.equals("0")) {
            long clientProjectId = Long.parseLong(billingProject);
            return clientProjectId;
        }
        return 0;

    }

    /**
     * Checks if the contest is development contest.
     * @param contest the contest
     * @return true if yes
     */
    private boolean isDevContest(SoftwareCompetition contest) {
        return contest.getProjectHeader().getProjectCategory().getId() == DEVELOPMENT_PROJECT_CATEGORY_ID;        
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
     * Updated for Version1.5
     *        the code is refactored by the logic:
     *        1. check the permission
     *        2. update or create the asset
     *        3. set default resources
     *        4. create project
     *        5. prepare the return value
     *        6. persist the eligility
     *
     * @param contest the <code>SoftwareCompetition</code> to create as a contest
     * @param tcDirectProjectId the TC direct project id.
     *            a <code>long</code> providing the ID of a client the new
     *            competition belongs to.
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
    public SoftwareCompetition createSoftwareContest(SoftwareCompetition contest, long tcDirectProjectId)
        throws ContestServiceException {
        logger.debug("createSoftwareContest with information : [tcDirectProjectId ="
                + tcDirectProjectId + "]");

        try {
            ExceptionUtils.checkNull(contest, null, null, "The contest to create is null.");
            ExceptionUtils.checkNull(contest.getProjectHeader(), null, null, "The contest#ProjectHeader to create is null.");
            
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            //check the permission
            checkSoftwareProjectPermission(tcDirectProjectId, true);
            //check the billing project permission
            long billingProjectId = getBillingProjectId(contest);
            
            //check whether we need to auto-create-development contest for design
            boolean creatingDevContest = shouldAutoCreateDevContest(contest);

            //copy the data from design to development if it is going to do auto-dev-creating
            SoftwareCompetition devContest = null;
            if (creatingDevContest) {
                devContest = (SoftwareCompetition)contest.clone();
            }

            //update the AssetDTO and update corresponding properties
            createUpdateAssetDTO(contest);
            
            //create contest resources
            contest.setProjectResources(createContestResources(contest, billingProjectId));

            //set the tc-direct-project-id
            contest.getProjectHeader().setTcDirectProjectId(tcDirectProjectId);

            //create project now
            FullProjectData projectData = projectServices.createProjectWithTemplate(contest.getProjectHeader(),
                        contest.getProjectPhases(), contest.getProjectResources(), String.valueOf(p.getUserId()));

            if (contest.getProjectHeader().getProjectCategory().getId() == DEVELOPMENT_PROJECT_CATEGORY_ID) {
		        projectServices.linkDevelopmentToDesignContest(projectData.getProjectHeader().getId());
	        }

            //preparing the result
            com.topcoder.project.phases.Phase[] allPhases = projectData.getAllPhases();
            // for now have to do these to avoid cycle
            for (int i = 0; i < allPhases.length; i++) {
                allPhases[i].setProject(null);
                allPhases[i].clearDependencies();
            }

            contest.setProjectHeader(projectData.getProjectHeader());
            contest.setProjectPhases(projectData);
            contest.setProjectResources(projectData.getResources());
            contest.setProjectData(projectData);
            contest.setId(projectData.getProjectHeader().getId());

            if (contest.getAssetDTO() != null) {
    	        // set null to avoid cycle
                contest.getAssetDTO().setDependencies(null);
                if (contest.getAssetDTO().getForum() != null) {
                    contest.getAssetDTO().getForum().setCompVersion(null);
                }
                if (contest.getAssetDTO().getLink() != null) {
                    contest.getAssetDTO().getLink().setCompVersion(null);
                }
    
                // need to remove loops before returning
                removeDocumentationLoops(contest);
    
                // set project start date in production date
                contest.getAssetDTO().setProductionDate(getXMLGregorianCalendar(contest.getProjectPhases().getStartDate()));
            }

            if (billingProjectId > 0) {
                persistContestEligility(contest.getProjectHeader().getId(), billingProjectId , null, false);            
            }

	        if (creatingDevContest) {
                autoCreateDevContest(contest, tcDirectProjectId, devContest);
            }

            
            return contest;
        } catch (Exception e) {
            sessionContext.setRollbackOnly();
            logger.error("Operation failed in the contest service facade.", e);
            throw new ContestServiceException("Operation failed in the contest service facade.",
                e);
        } finally {
            logger.debug("Exit createSoftwareContest, the newly create contest id = " + contest.getId());
        }
    }

    /**
     * <p>
     * Detects whether the auto creating development contest is on.
     * </p>
     * @param contest the contest
     * @return true if yes
     */
    private boolean shouldAutoCreateDevContest(SoftwareCompetition contest) {
        return contest.getDevelopmentProjectHeader() != null
                                     && contest.getDevelopmentProjectHeader().getProperties() != null
                                     && contest.getDevelopmentProjectHeader().getProperties().size() != 0;        
    }

    /**
     * Create or updating the AssetDTO for the contest. If the AssetDTO already exists for development contest,
     * we need to create dev-component. Also, creating forum if necessary.
     * @param contest the contest
     * @throws EntityNotFoundException if any error occurs
     * @throws com.topcoder.catalog.service.PersistenceException if any error occurs
     */
    private void createUpdateAssetDTO(SoftwareCompetition contest) throws EntityNotFoundException,
            com.topcoder.catalog.service.PersistenceException {
        UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
        //check if it is going to create development contest
        boolean isDevContest = isDevContest(contest);
        XMLGregorianCalendar productionDate = null;
        if (contest.getAssetDTO() != null) {
            AssetDTO assetDTO = contest.getAssetDTO();
            boolean useExistingAsset = false;
            if (isDevContest && assetDTO.getVersionNumber()!= null && assetDTO.getVersionNumber().longValue() != 1) {
                useExistingAsset = true;
                productionDate = assetDTO.getProductionDate();                    
                assetDTO = catalogService.getAssetByVersionId(assetDTO.getVersionNumber());
                // for dev, we need to insert a row in comp version dates
                catalogService.createDevComponent(assetDTO);
            }
            
            if (!useExistingAsset) {
                productionDate = assetDTO.getProductionDate() == null ? nextDay():assetDTO.getProductionDate();
                assetDTO.setProductionDate(null);
                if (contest.getProjectHeader() != null) {
                    // comp development, set phase to dev, otherwise to design
                    assetDTO.setPhase(isDevContest?"Development":"Design");                            
                }
                assetDTO = this.catalogService.createAsset(assetDTO);
                contest.setAssetDTO(assetDTO);
            }
            long forumId = 0;
            // create forum
            if (createForum) {
                if (useExistingAsset && assetDTO.getForum() != null) {
                    forumId = assetDTO.getForum().getJiveCategoryId();
                } else {
                    forumId = createForum(assetDTO, p.getUserId(), contest.getProjectHeader().getProjectCategory().getId());
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
            contest.setAssetDTO(assetDTO);

            contest.getProjectHeader().setProperty(ProjectPropertyType.EXTERNAL_REFERENCE_PROJECT_PROPERTY_KEY, assetDTO.getCompVersionId().toString());
            contest.getProjectHeader().setProperty(ProjectPropertyType.COMPONENT_ID_PROJECT_PROPERTY_KEY, assetDTO.getId().toString());
            contest.getProjectHeader().setProperty(ProjectPropertyType.SVN_MODULE_PROJECT_PROPERTY_KEY, "");
            contest.getProjectHeader().setProperty(ProjectPropertyType.NOTES_PROJECT_PROPERTY_KEY, "");

            if (forumId > 0) {
                contest.getProjectHeader().setProperty(ProjectPropertyType.DEVELOPER_FORUM_ID_PROJECT_PROPERTY_KEY, String.valueOf(forumId));
            }

            contest.getProjectPhases().setStartDate(getDate(productionDate));
        }
    }

    /**
     * <p>
     * If the auto creating development contest is switch on, we need to prepare the contest here. 
     * </p>
     * @param designContest the design contest
     * @param tcDirectProjectId tc-direct-project-id
     * @param devContest the development contest to create
     * @throws DatatypeConfigurationException if any error occurs
     * @throws ContestServiceException if any error occurs
     */
    private void autoCreateDevContest(SoftwareCompetition designContest, long tcDirectProjectId,
            SoftwareCompetition devContest) throws DatatypeConfigurationException, ContestServiceException {
        devContest.setAssetDTO(designContest.getAssetDTO());
        devContest.getProjectHeader().getProperties().putAll(designContest.getDevelopmentProjectHeader().getProperties());
        devContest.setDevelopmentProjectHeader(null);
        devContest.getProjectHeader().getProjectCategory().setId(DEVELOPMENT_PROJECT_CATEGORY_ID);        
        devContest.getAssetDTO().setProductionDate(nextDevProdDay(devContest.getAssetDTO().getProductionDate()));
        devContest.setProjectHeaderReason("Create corresponding development contest");
        createSoftwareContest(devContest, tcDirectProjectId);
    }
    /**
     * <p>
     * Persists the GroupContestEligibility for the contest and client. If the eligiblity is not null, then the
     * information will be copied from it.
     * </p>
     * @param contestId the contest id
     * @param billingProjectId the billingProjectId
     * @param eligiblity, the existing ContestEligibility, could be null
     * @param isStudio true for studio
     * @throws ContestEligibilityPersistenceException if any error occurs
     */
    private void persistContestEligility(long contestId, long billingProjectId, ContestEligibility eligiblity, boolean isStudio)
        throws ContestEligibilityPersistenceException {

        Long eligibilityGroupId = null;
        if (eligiblity == null)
        {

            eligibilityGroupId = getEligibilityGroupId(billingProjectId);
        }
        else 
        {
            eligibilityGroupId = ((GroupContestEligibility)eligiblity).getGroupId();
        }
           
        if (eligibilityGroupId != null) {
            GroupContestEligibility contestEligibility = new GroupContestEligibility();
            contestEligibility.setContestId(contestId);
            contestEligibility.setStudio(isStudio);
            contestEligibility.setDeleted(eligiblity==null?false:eligiblity.isDeleted());
            contestEligibility.setGroupId(eligibilityGroupId);
            contestEligibilityManager.create(contestEligibility); 
        }
    }

    /**
     * <p>
     * Adding the contest resources when creating contest. manager or observer or client-manager will be added.
     * </p>
     * @param contest the contest to create
     * @param billingProjectId the billing project id
     * @return resource array
     */
    private com.topcoder.management.resource.Resource[] createContestResources(SoftwareCompetition contest, 
            long billingProjectId) {
        UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
        com.topcoder.management.resource.Resource[] resources = new com.topcoder.management.resource.Resource[2];
        resources[0] = new com.topcoder.management.resource.Resource();
        resources[0].setId(com.topcoder.management.resource.Resource.UNSET_ID);

        ResourceRole managerRole = new ResourceRole();
        managerRole.setId(ResourceRole.RESOURCE_ROLE_MANAGER_ID);
        managerRole.setName(ResourceRole.RESOURCE_ROLE_MANAGER_NAME);
        managerRole.setDescription(ResourceRole.RESOURCE_ROLE_MANAGER_DESC);

        ResourceRole observerRole = new ResourceRole();
        observerRole.setId(ResourceRole.RESOURCE_ROLE_OBSERVER_ID);
        observerRole.setName(ResourceRole.RESOURCE_ROLE_OBSERVER_NAME);
        observerRole.setDescription(ResourceRole.RESOURCE_ROLE_OBSERVER_DESC);

        ResourceRole clientManagerRole = new ResourceRole();
        clientManagerRole.setId(ResourceRole.RESOURCE_ROLE_CLIENT_MANAGER_ID);
        clientManagerRole.setName(ResourceRole.RESOURCE_ROLE_CLIENT_MANAGER_NAME);
        clientManagerRole.setDescription(ResourceRole.RESOURCE_ROLE_CLIENT_MANAGER_DESC);

        boolean tcstaff = sessionContext.isCallerInRole(TC_STAFF_ROLE);
        // tc staff add as manager, other as observer
        if (tcstaff) {
            resources[0].setResourceRole(managerRole);
        } else if (getEligibilityName(billingProjectId).trim().length() > 0) {
            resources[0].setResourceRole(clientManagerRole);
        } else {
            resources[0].setResourceRole(observerRole);
        }

        resources[0].setProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID, String.valueOf(p.getUserId()));
        resources[0].setProperty(RESOURCE_INFO_HANDLE, String.valueOf(p.getName()));
        resources[0].setProperty(RESOURCE_INFO_PAYMENT_STATUS, RESOURCE_INFO_PAYMENT_STATUS_NA);
        
        // design/dev, add Components
        if (contest.getProjectHeader().getProjectCategory().getId() == DEVELOPMENT_PROJECT_CATEGORY_ID 
             || contest.getProjectHeader().getProjectCategory().getId() == DESIGN_PROJECT_CATEGORY_ID) {

            resources[1] = new com.topcoder.management.resource.Resource();
            resources[1].setId(com.topcoder.management.resource.Resource.UNSET_ID);
            resources[1].setResourceRole(managerRole);
            resources[1].setProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID, Long.toString(components_user_id));
            resources[1].setProperty(RESOURCE_INFO_HANDLE, RESOURCE_INFO_HANDLE_COMPONENTS);
            resources[1].setProperty(RESOURCE_INFO_PAYMENT_STATUS, RESOURCE_INFO_PAYMENT_STATUS_NA);
        }
        // else add Applications
        else {
            resources[1] = new com.topcoder.management.resource.Resource();
            resources[1].setId(com.topcoder.management.resource.Resource.UNSET_ID);
            resources[1].setResourceRole(managerRole);
            resources[1].setProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID, Long.toString(applications_user_id));
            resources[1].setProperty(RESOURCE_INFO_HANDLE, RESOURCE_INFO_HANDLE_APPLICATIONS);
            resources[1].setProperty(RESOURCE_INFO_PAYMENT_STATUS, RESOURCE_INFO_PAYMENT_STATUS_NA);
        }
        
        return resources;
    }

    /**
     * <p>
     * Updates a <code>SoftwareCompetition</code> in the persistence.
     * </p>
     * 
     * <p>
     * Update in version 1.5, reduce the code redundancy in permission checking.
     * <p>
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
                contest.getAssetDTO().setCompVersionId(contest.getAssetDTO().getVersionNumber());
                contest.setAssetDTO(this.catalogService.updateAsset(contest.getAssetDTO()));
            }

            if (contest.getProjectHeader() != null) {

                UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();

                //check the permissions
                checkSoftwareContestPermission(contest.getProjectHeader().getId(), false);
                checkBillingProjectPermission(contest);
                
                Set<com.topcoder.project.phases.Phase> phaseset = contest.getProjectPhases().getPhases();
                com.topcoder.project.phases.Phase[] phases =  phaseset.toArray(new com.topcoder.project.phases.Phase[phaseset.size()]);

                // add back project on phase
                for (int i = 0; i < phases.length; i++) {
                    phases[i].setProject(contest.getProjectPhases());
                }

                contest.getProjectPhases().setStartDate(getDate(productionDate));

                contest.getProjectHeader()
                       .setTcDirectProjectId(tcDirectProjectId);

                // update name in project info in case name is changed.
                contest.getProjectHeader().setProperty(ProjectPropertyType.PROJECT_NAME_PROJECT_PROPERTY_KEY, contest.getAssetDTO().getName());

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

                long forumId = projectServices.getForumId(projectData.getProjectHeader().getId());
                if (forumId > 0 && createForum)
                {
                    updateForumName(forumId, contest.getAssetDTO().getName());
                }

                com.topcoder.project.phases.Phase[] allPhases = projectData.getAllPhases();

                // this is to avoid cycle
                for (int i = 0; i < allPhases.length; i++) {
                    allPhases[i].setProject(null);
                    allPhases[i].clearDependencies();
                }

                // billing projct can change, set or unset
                // so for now easy way is removing current, and add if any
                List<ContestEligibility> contestEligibilities = 
                 contestEligibilityManager.getContestEligibility(contest.getProjectHeader().getId(), false); 
                for (ContestEligibility ce:contestEligibilities){   
                    contestEligibilityManager.remove(ce);
                }
                long billingProjectId = getBillingProjectId(contest);
                if (billingProjectId > 0) {
                    persistContestEligility(contest.getProjectHeader().getId(), billingProjectId , null, false);            
                }




		        //BugRace3074
                if (contest.getProjectHeader().getProjectCategory().getId() == DESIGN_PROJECT_CATEGORY_ID) {
                    long rst = projectServices.getDevelopmentContestId(contest.getId());
                    if (rst != 0) {
                        Duration twoDay = DatatypeFactory.newInstance().newDurationDayTime(true, 2, 0, 0, 0);
			            //Since they are already sorted, just get the latest one's end time.
                        XMLGregorianCalendar twoDaysLater =
                            (getXMLGregorianCalendar(allPhases[allPhases.length - 1].getScheduledEndDate()));
                        twoDaysLater.add(twoDay);

                        SoftwareCompetition developmentContest = getSoftwareContestByProjectId(rst);
                        developmentContest.getProjectPhases().setStartDate(getDate(twoDaysLater));
                        phaseset = developmentContest.getProjectPhases().getPhases();
                        phases = (com.topcoder.project.phases.Phase[]) phaseset.toArray(new com.topcoder.project.phases.Phase[phaseset.size()]);
                        // add back project on phase
                        for (int i = 0; i < phases.length; i++) {
                             phases[i].setProject(developmentContest.getProjectPhases());
                        }

                        developmentContest.getProjectHeader().setProperty(ProjectPropertyType.PROJECT_NAME_PROJECT_PROPERTY_KEY, contest.getAssetDTO().getName());

                        developmentContest.setProjectHeaderReason("Cascade update from corresponding design contest");
                        projectServices.updateProject(developmentContest.getProjectHeader(),
                                developmentContest.getProjectHeaderReason(),
                                developmentContest.getProjectPhases(),
                                developmentContest.getProjectResources(),
                                String.valueOf(p.getUserId()));


                    }
                }
            } 

            // set project start date in production date
            contest.getAssetDTO()
                   .setProductionDate(getXMLGregorianCalendar(
                    contest.getProjectPhases().getStartDate()));

            // need to remove loops before returning
            removeDocumentationLoops(contest);

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

            forums.createCategoryWatch(userId, forumId);
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

            if (!sessionContext.isCallerInRole(ADMIN_ROLE))
            {
                UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
                long userId = p.getUserId();
                if (!studioService.checkSubmissionPermission(submissionId, false, userId))
                {
                    throw new PersistenceException("No write permission on contest");
                }
            }


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
		newData.setSpecReviewProjectId(data.getSpecReviewProjectId());
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
            newData.setSpecReviewProjectId(data.getSpecReviewProjectId());
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
		newData.setSpecReviewProjectId(data.getSpecReviewProjectId());
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
            newData.setSpecReviewProjectId(data.getSpecReviewProjectId());
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
    public SoftwareCompetition getSoftwareContestByProjectId(long projectId)
        throws ContestServiceException {
        logger.debug("getSoftwareContestByProjectId (" + projectId + ")");

        SoftwareCompetition contest = new SoftwareCompetition();

        try {
            
            checkSoftwareContestPermission(projectId, true);
            
            FullProjectData fullProjectData = this.projectServices.getFullProjectData(projectId);
            Long compVersionId = Long.parseLong(fullProjectData.getProjectHeader()
                                                               .getProperty(ProjectPropertyType.EXTERNAL_REFERENCE_PROJECT_PROPERTY_KEY));
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

            // need to remove loops before returning
            removeDocumentationLoops(contest);

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

        try
        {
                UserProfilePrincipal principal = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
                if (!sessionContext.isCallerInRole(ADMIN_ROLE))
                {
                    long userId = principal.getUserId();

                    List<CommonProjectPermissionData> userPermissions = getCommonProjectPermissionDataForUser(userId);

                    for (Permission p : permissions)
                    {
                        boolean hasFullPermission = false;

                        for (CommonProjectPermissionData data :  userPermissions)
                        {

                            if (p.getResourceId().longValue() == data.getProjectId())
                            {
                                if (data.getPfull() > 0)
                                {
                                    hasFullPermission = true;
                                    break;
                                }
                            }
                            else
                            {
                                if (p.getResourceId().longValue() == data.getContestId() && (p.isStudio() == data.isStudio()))
                                {
                                    if (data.getPfull() > 0 || data.getCfull() > 0)
                                    {
                                        hasFullPermission = true;
                                        break;
                                    }
                                }
                            }
                        }

                        if (!hasFullPermission)
                        {
                            throw new PermissionServiceException("No full permission on resource "+p.getResourceId());
                        }
                    }
                }

            // when add/remove permission, we need to add/remvoe observer
            for (Permission per : permissions) {
                // if add permission
                if ((per.getPermissionId() == null || per.getPermissionId() <= 0) 
                      && per.getPermissionType() != null && per.getPermissionType().getName() != null
                      && !per.getPermissionType().getName().equals("")) 
                {

                    List<Long> projectIds = new ArrayList<Long>();

                    // if permission is project, get its OR projects
                    if (per.getPermissionType().getPermissionTypeId() >= PermissionType.PERMISSION_TYPE_PROJECT_READ
                         && per.getPermissionType().getPermissionTypeId() <= PermissionType.PERMISSION_TYPE_PROJECT_FULL)
                    {
                        projectIds = projectServices.getProjectIdByTcDirectProject(per.getResourceId());
                    }
                    else if (!per.isStudio())
                    {
                        projectIds.add(per.getResourceId());
                    }   
                    
                    if (projectIds != null && projectIds.size() >0)
                    {
                        // for each OR project, find all observers
                        for (Long pid : projectIds)
                        {

                            com.topcoder.management.resource.Resource[] resources = projectServices.searchResources(pid, RESOURCE_ROLE_OBSERVER_ID);

                            boolean found = false;
                            // check if user is already a observer
                            if (resources != null && resources.length > 0)
                            {
                                for (com.topcoder.management.resource.Resource resource : resources )
                                {
                                    if (resource.hasProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID)
                                         && resource.getProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID).equals(String.valueOf(per.getUserId())))
                                    {
                                        found = true;
                                        break;
                                    }
                                }
                            }

                            // if not found && user agreed terms (if any), add resource
                            if (!found && checkTerms(pid, per.getUserId(), new int[]{(int)RESOURCE_ROLE_OBSERVER_ID}))
                            {
                                 
                                 com.topcoder.management.resource.Resource newRes = new com.topcoder.management.resource.Resource();
                                 newRes.setId(com.topcoder.management.resource.Resource.UNSET_ID);
                                 newRes.setProject(pid);

                                 ResourceRole observer_role = new ResourceRole();
                                 observer_role.setId(RESOURCE_ROLE_OBSERVER_ID);
                                 observer_role.setName(RESOURCE_ROLE_OBSERVER_NAME);
                                 observer_role.setDescription(RESOURCE_ROLE_OBSERVER_DESC);

                                 newRes.setResourceRole(observer_role);
                
                                 newRes.setProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID,
                                    String.valueOf(per.getUserId()));
                                 newRes.setProperty(RESOURCE_INFO_HANDLE,
                                    String.valueOf(userService.getUserHandle(per.getUserId())));
                                 newRes.setProperty(RESOURCE_INFO_PAYMENT_STATUS,
                                    RESOURCE_INFO_PAYMENT_STATUS_NA);

                                 projectServices.updateResource(newRes, String.valueOf(principal.getUserId()));

                                 // create forum watch
                                long forumId = projectServices.getForumId(pid);
          
                                if (forumId > 0 && createForum)
                                {
                                    createForumWatchAndRole(forumId, per.getUserId());
                                }
                            }

                        }
                        
                    }
                    
                    
                }
                // if remove permission, we need to remove observer
                else if (per.getPermissionType() == null || per.getPermissionType().getName() == null 
						                  || per.getPermissionType().getName().equals("")) 
                {

                    List<Permission> ps = this.getPermissions(per.getUserId(), per.getResourceId());
                    Permission toDelete = null;
                    if (ps != null && ps.size() > 0) 
                    {
                        toDelete = ps.get(0);
                    }

                    if (toDelete != null)
                    {
                        List<Long> projectIds = new ArrayList<Long>();
                        boolean isTCProject = false;

                        // if permission is project, get its OR projects
                        if (toDelete.getPermissionType().getPermissionTypeId() >= PermissionType.PERMISSION_TYPE_PROJECT_READ
                             && toDelete.getPermissionType().getPermissionTypeId() <= PermissionType.PERMISSION_TYPE_PROJECT_FULL)
                        {
                            projectIds = projectServices.getProjectIdByTcDirectProject(per.getResourceId());
                            isTCProject = true;
                        }
                        else if (!toDelete.isStudio())
                        {
                            projectIds.add(per.getResourceId());
                        }   
                        
                        if (projectIds != null && projectIds.size() >0)
                        {
                            for (Long pid : projectIds)
                            {
                                // if we are removing project permission but user still has contest permission, 
                                // or if we are removing contest permission but user still has project permission
                                // we will not remove observer
                                if ((!projectServices.hasContestPermission(pid, toDelete.getUserId()) && isTCProject)
                                     || (!projectServices.checkProjectPermission(projectServices.getTcDirectProject(pid), true, toDelete.getUserId()) && !isTCProject))
                                {
                                    com.topcoder.management.resource.Resource[] resources = projectServices.searchResources(pid, RESOURCE_ROLE_OBSERVER_ID);

                                    com.topcoder.management.resource.Resource delRes = null;
                        
                                    // check if user is already a observer
                                    if (resources != null && resources.length > 0)
                                    {
                                        for (com.topcoder.management.resource.Resource resource : resources )
                                        {
                                            if (resource.hasProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID)
                                                 && resource.getProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID).equals(String.valueOf(toDelete.getUserId())))
                                            {
                                                delRes = resource;
                                                break;
                                            }
                                        }
                                    }

                                    if (delRes != null)
                                    {
                                        projectServices.removeResource(delRes, String.valueOf(principal.getUserId()));
                                    }

                                    // delete forum watch
                                    long forumId = projectServices.getForumId(pid);
                                    if (forumId > 0 && createForum)
                                    {
                                        deleteForumWatchAndRole(forumId, per.getUserId());
                                    }
                                }
                            }
                        }
                    }   
                   
                }
            }

            this.permissionService.updatePermissions(permissions);
        }
        catch (PersistenceException e)
        {
            sessionContext.setRollbackOnly();
            throw new PermissionServiceException(e.getMessage(), e);
        }
        catch (UserServiceException e)
        {
            sessionContext.setRollbackOnly();
            throw new PermissionServiceException(e.getMessage(), e);
        }

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
        SubmissionPaymentData[] subPaymentDatas, String orderNumber, MilestonePrizeData milestonePrize)
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

            if (submissionPaymentData.isPurchased() || submissionPaymentData.getAwardMilestonePrize()) {
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

                if (submissionPaymentData.isPurchased())
                {
                    totalCost += submissionPaymentData.getAmount();
                }

                if (submissionPaymentData.getAwardMilestonePrize() && milestonePrize != null)
                {
                    totalCost += milestonePrize.getAmount().doubleValue();
                }


                
                 if (submissionPaymentData.isPurchased())
                {
                     sb.append(Long.toString(submissionId)).append(" - ")
                  .append(submissionPaymentData.getAmount());

                     if (j > 0) {
                        sb.append("\n");
                    }
                }

                if (submissionPaymentData.getAwardMilestonePrize() && milestonePrize != null)
                {
                     sb.append(Long.toString(submissionId)).append(" - ")
                  .append(milestonePrize.getAmount().doubleValue());
                     if (j > 0) {
                        sb.append("\n");
                    }
                }

               

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

            if (!sessionContext.isCallerInRole(ADMIN_ROLE))
            {
                UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
                long userId = p.getUserId();
                if (!studioService.checkContestPermission(contestId, true, userId))
                {
                    throw new PersistenceException("No read permission on contest");
                }
            }

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

            if (!sessionContext.isCallerInRole(ADMIN_ROLE))
            {
                UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
                long userId = p.getUserId();
                if (!studioService.checkContestPermission(contestId, true, userId))
                {
                    throw new PersistenceException("No read permission on contest");
                }
            }
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

            if (!sessionContext.isCallerInRole(ADMIN_ROLE))
            {
                UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
                long userId = p.getUserId();
                if (!studioService.checkSubmissionPermission(submissionId, false, userId))
                {
                    throw new PersistenceException("No write permission on contest");
                }
            }

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

    /**
     * Get all design components.
     *
     * @throws ContestServiceException
     *             if any other error occurs
     * @since 1.1
     */
    public List<DesignComponents> getDesignComponents() throws ContestServiceException {
        String methodName = "getDesignComponents";
        logger.info("Enter: " + methodName);

        try {
            return projectServices.getDesignComponents(0);
        } catch (ProjectServicesException pe) {
            logger.error(pe.getMessage(), pe);
            throw new ContestServiceException(pe.getMessage(), pe);
        } finally{
            logger.info("Exit: " + methodName);
	}
    }

    
    /**
     * Returns whether a user is eligible for a particular contest.
     *
     * @param userId
     *            The user id
     * @param contestId
     *            The contest id
     * @param isStudio
     *            true if the contest is a studio contest, false otherwise.
     * @return true if the user is eligible for the specified contest, false otherwise.
     * 
     * @throws ContestServiceException
     *             if any other error occurs
     * @since 1.2.2
     */
    public boolean isEligible(long userId, long contestId, boolean isStudio) throws ContestServiceException {
        String methodName = "isEligible";
        logger.info("Enter: " + methodName);

        boolean eligible = false;
    	
    	try {
			List<ContestEligibility> eligibilities = contestEligibilityManager.getContestEligibility(contestId, isStudio);
			eligible = contestEligibilityValidationManager.validate(userId, eligibilities);
		} catch (ContestEligibilityPersistenceException e) {
            logger.error(e.getMessage(), e);
            throw new ContestServiceException(e.getMessage(), e);
		} catch (ContestEligibilityValidationManagerException e) {
            logger.error(e.getMessage(), e);
            throw new ContestServiceException(e.getMessage(), e);
		}
    	    	
        logger.info("Exit: " + methodName);
    	return eligible;
    }

    /**
     * Find eligibility name for the billing project.
     * 
     * @param billingProjectId;
     * 			The ID of the billing project.
     * @return
     * 			The name of the eligibility group.
     * @since 1.2.3
     */
    public String getEligibilityName(long billingProjectId) {
        String methodName = "getEligibilityName : billing project id = "+ billingProjectId;
        logger.info("Enter: " + methodName);
        try {
            ConfigManager cfgMgr = ConfigManager.getInstance();
            Property rootProperty = cfgMgr.getPropertyObject(CONTEST_ELIGIBILITY_MAPPING_NAMESPACE,
                CONTEST_ELIGIBILITY_MAPPING_PREFIX);
            Property eligibility = rootProperty.getProperty(Long.toString(billingProjectId));
            if (eligibility != null && !eligibility.equals("")) {

                return (String)(eligibility.getValue(ELIGIBILITY_NAME));
            }
            return "";
        } catch (Exception e) {
            logger.error("Cannot retrieve eligibility name.");
            return "";
        } finally {
            logger.info("Exit: " + methodName);
        }
    }

    
    /**
     * Find eligibility group id for the client.
     * 
     * @param billingProjectId;
     * 			The ID of the billingProjectId.
     * @return
     * 			The id of the eligibility group.
     * @since 1.2.3
     */
    private Long getEligibilityGroupId(long billingProjectId) {
        String methodName = "getEligibilityGroupId";
        logger.info("Enter: " + methodName);
        try {
            ConfigManager cfgMgr = ConfigManager.getInstance();
            Property rootProperty = cfgMgr.getPropertyObject(CONTEST_ELIGIBILITY_MAPPING_NAMESPACE,
                CONTEST_ELIGIBILITY_MAPPING_PREFIX);
            Property eligibility = rootProperty.getProperty(Long.toString(billingProjectId));
            if (eligibility != null) {
                return Long.valueOf((String)(eligibility.getValue(ELIGIBILITY_ID)));
            }
            return null;
        } catch (Exception e) {
            logger.error("Cannot retrieve eligibility id.");
            return null;
        } finally {
            logger.info("Exit: " + methodName);
        }
    }

    /**
     * Private helper method to remove loops within documentation collection in AssetDTO
     *
     * @param contest the contest which needs loops removal
     *
     * @since 1.3.4
     */
    private void removeDocumentationLoops(SoftwareCompetition contest) {
        if (contest.getAssetDTO().getDocumentation() != null && contest.getAssetDTO().getDocumentation().size() > 0) {
            for (CompDocumentation doc : contest.getAssetDTO().getDocumentation()) {
                doc.setCompVersion(null);
            }
        }
    }

    /**
     * Returns whether the contest is private.
     *
     * @param contestId
     *            The contest id
     * @param isStudio
     *            true if the contest is a studio contest, false otherwise.
     * @return true if the contest is a private one, false otherwise.
     * 
     * @throws ContestServiceException
     *             if any other error occurs
     * @since 1.2.3
     */
    public boolean isPrivate(long contestId, boolean isStudio) throws ContestServiceException {
        String methodName = "isPrivate";
        logger.info("Enter: " + methodName);
        
        List<ContestEligibility> eligibilities;
        try {
            eligibilities = contestEligibilityManager.getContestEligibility(contestId, isStudio);
        } catch (ContestEligibilityPersistenceException e) {
            logger.error(e.getMessage(), e);
            throw new ContestServiceException(e.getMessage(), e);
        }
                
        logger.info("Exit: " + methodName);
        return !eligibilities.isEmpty();
    } 

     /**
     * This method creates a Specification Review project associated to a project determined by parameter.
     *
     * @param projectId the project id to create a Specification Review for
     * @return the created project
     *
     * @throws ContestServiceException if any unexpected error occurs in the underlying services
     *
     * @since 1.4
     */
    public FullProjectData createSpecReview(long projectId) throws ContestServiceException {
        String method = "createSpecReview(" + projectId + ")";
        logger.info("Enter: " + method);

        FullProjectData specReview = null;
        try {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            specReview = projectServices.createSpecReview(projectId, specReviewPrize, String.valueOf(p.getUserId()));
        } catch (ProjectServicesException e) {
            logger.error("Operation failed in ProjectServices.", e);
            throw new ContestServiceException("Operation failed in Project Services.", e);
        } finally {
            logger.info("Exit: " + method);
        }

        return specReview;
    }

    /**
     * This method retrieves scorecard and review information associated to a project determined by parameter.
     * Note: a single reviewer / review is assumed.
     *
     * @param projectId the project id to search for
     * @return the aggregated scorecard and review data
     *
     * @throws ContestServiceException if any unexpected error occurs in the underlying services
     *
     * @since 1.4
     */
    public ScorecardReviewData getScorecardAndReview(long projectId) throws ContestServiceException {
        String method = "getScorecardAndReview(" + projectId + ")";
        logger.info("Enter: " + method);

        ScorecardReviewData scorecardReviewData =  null;
        try {
            scorecardReviewData =  projectServices.getScorecardAndReview(projectId);
        } catch (ProjectServicesException e) {
            logger.error("Operation failed in Project Services.", e);
            throw new ContestServiceException("Operation failed in Project Services.", e);
        } finally {
            logger.info("Exit: " + method);
        }

        return scorecardReviewData;
    }

    /**
     * This method uploads a mock file to the corresponding specification review project of the specified project
     * id, so that it can continue with review. Regular submission or final fix will be uploaded according to the
     * open phase.
     *
     * @param projectId the project id of the original project
     *
     * @throws ContestServiceException if any unexpected error occurs in the underlying services, if the associated
     * specification review project id cannot be found or if neither submission or final fixes phase are open.
     *
     * @since 1.4
     */
    public void markSoftwareContestReadyForReview(long projectId) throws ContestServiceException {
        String method = "markSoftwareContestReadyForReview(" + projectId + ")";
        logger.info("Enter: " + method);

        try {
            // get associated specification review project id
            long specReviewProjectId = projectServices.getSpecReviewProjectId(projectId);
            if (specReviewProjectId < 0) {
                throw new ContestServiceException("Failed to get associated specification review.");
            }

            // get associated specification review open phases
            Set<String> openPhases = projectServices.getOpenPhases(specReviewProjectId);

            // prepare mock file for upload
            DataHandler dataHandler = new DataHandler(new FileDataSource(mockSubmissionFilePath +
                   mockSubmissionFileName));

            // upload regular submission or final fix according to open phase
            if (openPhases.contains(PROJECT_SUBMISSION_PHASE_NAME)) {
                uploadSubmission(specReviewProjectId, mockSubmissionFileName, dataHandler);
            } else if (openPhases.contains(PROJECT_FINAL_FIX_PHASE_NAME)) {
                uploadFinalFix(specReviewProjectId, mockSubmissionFileName, dataHandler);
            } else {
                throw new ContestServiceException("Submission or Final Fix phase should be open.");
            }
        } catch (ProjectServicesException e) {
            logger.error("Operation failed in Project Services.", e);
            throw new ContestServiceException("Operation failed in Project Services.", e);
        } finally {
            logger.info("Exit: " + method);
        }
    }

    /**
     * This method adds a review comment to a review. It simply delegates all logic to underlying services.
     *
     * @param reviewId the review id to add the comment to
     * @param comment the review comment to add
     *
     * @throws ContestServiceException if any unexpected error occurs in the underlying services.
     * @throws IllegalArgumentException if comment is null
     *
     * @since 1.4
     */
    public void addReviewComment(long reviewId, Comment comment) throws ContestServiceException {
        if (comment == null) {
            throw new IllegalArgumentException("The comment cannot be null");
        }

        String method = "addReviewComment(" + reviewId + ", " + comment + ")";
        logger.info("Enter: " + method);

        try {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            projectServices.addReviewComment(reviewId, comment, String.valueOf(p.getUserId()));
        } catch (ProjectServicesException e) {
            logger.error("Operation failed in Project Services.", e);
            throw new ContestServiceException("Operation failed in Project Services.", e);
        } finally {
            logger.info("Exit: " + method);
        }
    }
    
    /**
     * create forum watch with given parameters. It will lookup the ForumsHome
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
    private void createForumWatchAndRole(long forumId, long userId) {
        logger.debug("createForumWatch (" + forumId + ", " + userId + ")");

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

            String roleId = "Software_Users_" + forumId;
            forums.createCategoryWatch(userId, forumId);
            forums.assignRole(userId, roleId);

            logger.debug("Exit createForumWatch (" + forumId + ", " + userId + ")");

        } catch (Exception e) {
            logger.error("*** Could not create a forum watch for " + forumId + ", " + userId );
            logger.error(e);
        }
    }

    /**
     * delete forum watch with given parameters. It will lookup the ForumsHome
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
    private void deleteForumWatchAndRole(long forumId, long userId) {
        logger.info("deleteForumWatch (" + forumId + ", " + userId + ")");

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

            String roleId = "Software_Users_" + forumId;
            forums.deleteCategoryWatch(userId, forumId);
            forums.removeRole(userId, roleId);
            logger.debug("Exit deleteForumWatch (" + forumId + ", " + userId + ")");

        } catch (Exception e) {
            logger.error("*** Could not delete forum watch for " + forumId + ", " + userId );
            logger.error(e);
        }
    }


    /**
     * update forum name
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
    private void updateForumName(long forumId, String name) {
        logger.info("updateForumName (" + forumId + ", " + name + ")");

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

            forums.updateComponentName(forumId, name);
            logger.debug("Exit updateForumName (" + forumId + ", " + name + ")");

        } catch (Exception e) {
            logger.error("*** Could not updateForumName for " + forumId + ", " + name );
            logger.error(e);
        }
    }


    /**
     * check if user agrees the term(s) associate with the project (if any)
     * by role
     *
     * @param projectId
     *            OR project id
     * @param userId
     *            userId The user id to use
     * @param roleId
     *            role id
     * @return true if user agreed terms or no term associated with project
     */
    private boolean checkTerms(long projectId, long userId, int[] roleIds) {
        logger.info("checkTerms (" + projectId + ", " + userId + ", " + roleIds + ")");

        try {
            Properties p = new Properties();
            p.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.jnp.interfaces.NamingContextFactory");
            p.put(Context.URL_PKG_PREFIXES,
                "org.jboss.naming:org.jnp.interfaces");
            p.put(Context.PROVIDER_URL, userBeanProviderUrl);

            Context c = new InitialContext(p);
            UserTermsOfUseHome userTermsOfUseHome = (UserTermsOfUseHome) c.lookup(UserTermsOfUseHome.EJB_REF_NAME);

            UserTermsOfUse userTerm = userTermsOfUseHome.create();

            Properties p2 = new Properties();
            p2.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.jnp.interfaces.NamingContextFactory");
            p2.put(Context.URL_PKG_PREFIXES,
                "org.jboss.naming:org.jnp.interfaces");
            p2.put(Context.PROVIDER_URL, projectBeanProviderUrl);

            Context c2 = new InitialContext(p2);
            ProjectRoleTermsOfUseHome projectRoleTermsOfUseHome = (ProjectRoleTermsOfUseHome) c2.lookup(ProjectRoleTermsOfUseHome.EJB_REF_NAME);

            ProjectRoleTermsOfUse projectTerm = projectRoleTermsOfUseHome.create();

            List<Long>[] necessaryTerms = projectTerm.getTermsOfUse((int)projectId, roleIds, "java:/DS");

            // if project does not have term
            if (necessaryTerms == null || necessaryTerms.length == 0)
            {
                return true;
            }

            for (int i = 0; i < necessaryTerms.length; i++) 
            {
                if (necessaryTerms[i] != null) 
                {
                    for (int j = 0; j < necessaryTerms[i].size(); j++) 
                    {
                        Long termId = necessaryTerms[i].get(j);
                        // if user has not agreed
                        if (!userTerm.hasTermsOfUse(userId, termId, "java:/DS"))
                        {
                            return false;
                        }
                    }
                }
            }

            logger.debug("checkTerms (" + projectId + ", " + userId + ", " + roleIds + ")");
            return true;

        } catch (Exception e) {
            logger.error("*** eorr in checkTerms (" + projectId + ", " + userId + ", " + roleIds + ")");

            logger.error(e);
            return false;
        }
    }
    /**
     * The next day for production-date.
     * @return new date
     */
    private XMLGregorianCalendar nextDay(){
        GregorianCalendar startDate = new GregorianCalendar();
        startDate.setTime(new Date());
        startDate.add(Calendar.HOUR, 24 * 14); // BUGR-1789
        int m = startDate.get(Calendar.MINUTE);
        startDate.add(Calendar.MINUTE, m + (15 - m % 15) % 15);
        return getXMLGregorianCalendar(startDate.getTime());
    }
    /**
     * The next production-date for the re-open and new release contest.
     * @return new date
     */
    private XMLGregorianCalendar nextReOpenNewReleaseDay(){
        GregorianCalendar startDate = new GregorianCalendar();
        startDate.setTime(new Date());
        startDate.add(Calendar.HOUR, 24);
        int m = startDate.get(Calendar.MINUTE);
        startDate.add(Calendar.MINUTE, m + (15 - m % 15) % 15);
        return getXMLGregorianCalendar(startDate.getTime());
    }
    /**
     * Finds the next development production date for the design.
     * @param date date to calcuate base on
     * @return the next development prod date
     * @throws DatatypeConfigurationException if any error occurs
     */
    private XMLGregorianCalendar nextDevProdDay(XMLGregorianCalendar date) throws DatatypeConfigurationException{
        Duration elevenDay = DatatypeFactory.newInstance().newDurationDayTime(true, 11, 0, 0, 0);        
        date.add(elevenDay);
        return date;
    }
    /**
     * <p>
     * Create new version for design or development contest. (project_status_id = 4-10 in tcs_catalog:project_status_lu).
     * </p>
     * @param projectId the project to create new version
     * @param tcDirectProjectId tc direct project id
     * @param autoDevCreating if it is true and it is design contest, then will create development too
     * @param startDate the start date for the new version contest
     * @return newly version contest id
     * @throws ContestServiceException if any error occurs
     */
    private long createNewVersionForDesignDevContest(long projectId, long tcDirectProjectId, 
            boolean autoDevCreating, XMLGregorianCalendar startDate, boolean minorVersion) throws ContestServiceException {
        try {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            //0.check the permission first
            checkSoftwareProjectPermission(tcDirectProjectId, true);
            //1. for now, only completed can create new version
            FullProjectData contest = this.projectServices.getFullProjectData(projectId);  
            // if auto dev creating, dont check, since we pass the new design project id
            if (!autoDevCreating && contest.getProjectHeader().getProjectStatus().getId() != ProjectStatus.COMPLETED_STATUS_ID) {
                throw new ProjectServicesException("The design project or its corresponding development project is not completed."
                        + " You can not create new version for it.");
            }
            boolean isDevContest = 
                contest.getProjectHeader().getProjectCategory().getId() == DEVELOPMENT_PROJECT_CATEGORY_ID;
            
            //2.create new version
            Long compVersionId = Long.parseLong(contest.getProjectHeader().getProperty(ProjectPropertyType.EXTERNAL_REFERENCE_PROJECT_PROPERTY_KEY));
            AssetDTO dto = catalogService.getAssetByVersionId(compVersionId);
            //create minor or major version
            dto.setToCreateMinorVersion(minorVersion);            
            
            //if it is dev only, or design, create new version here
            if (!isDevContest || !autoDevCreating) {
		//clear the version
            	dto.setCompVersionId(null);
                dto.setForum(null);
                dto.setDocumentation(new ArrayList<CompDocumentation>());

                dto = catalogService.createVersion(dto);
            }
            //if it is auto-creating-dev and is creating dev now
            else if (autoDevCreating && isDevContest) {
                // need to get the latest verion by component id
                dto = catalogService.getAssetById(dto.getId(), false);
                dto = catalogService.createDevComponent(dto);                
            }

            contest.getProjectHeader().setProperty(ProjectPropertyType.EXTERNAL_REFERENCE_PROJECT_PROPERTY_KEY, String.valueOf(dto.getVersionNumber()));    
            contest.getProjectHeader().setProperty(ProjectPropertyType.PROJECT_VERSION_PROJECT_PROPERTY_KEY, String.valueOf(dto.getVersionText()));    
            contest.getProjectHeader().setProperty(ProjectPropertyType.VERSION_ID_PROJECT_PROPERTY_KEY, String.valueOf(dto.getVersion()));    
            
            long forumId = 0;
            // create forum
            if (createForum) {
                forumId = createForum(dto, p.getUserId(), contest.getProjectHeader().getProjectCategory().getId());
            }

            // if forum created
            if (forumId > 0 && dto.getForum() == null)
            {
                // create a comp forum
                CompForum compForum = new CompForum();
                compForum.setJiveCategoryId(forumId);
                dto.setForum(compForum);
                dto = this.catalogService.updateAsset(dto);
                // avoid cycle
                dto.getForum().setCompVersion(null);
            }

            if (forumId > 0) {
                contest.getProjectHeader().setProperty(ProjectPropertyType.DEVELOPER_FORUM_ID_PROJECT_PROPERTY_KEY, String.valueOf(forumId));
            }

            contest.setStartDate(getDate(startDate));
            //3.create the project
            FullProjectData newVersionORProject = projectServices.createNewVersionContest(contest, String.valueOf(p.getUserId()));

            List<ContestEligibility> contestEligibilities = 
                 contestEligibilityManager.getContestEligibility(contest.getProjectHeader().getId(), false); 
            for (ContestEligibility ce:contestEligibilities){   
                persistContestEligility(newVersionORProject.getProjectHeader().getId(), 0, ce, false);
            }
            
            //4.if also auto-dev-creating for design, create it
            if (autoDevCreating && !isDevContest) {
                long developmentProjectId = projectServices.getDevelopmentContestId(projectId);
                if (developmentProjectId > 0){
                    logger.debug("create new version development project, the dev project id is :" + developmentProjectId);
                    createNewVersionForDesignDevContest(developmentProjectId, tcDirectProjectId, true, nextDevProdDay(startDate), minorVersion);
                }
            }
            logger.debug("Exit createNewVersionForDesignDevContest");
            return newVersionORProject.getProjectHeader().getId();
        } catch (Exception e) {
            sessionContext.setRollbackOnly();
            logger.error("Operation failed in the contest service facade.", e);
            throw new ContestServiceException("Operation failed in the contest service facade.",
                e);
        }
    }
    /**
     * <p>
     * Create new version for design or development contest. (project_status_id = 4-10 in tcs_catalog:project_status_lu).
     * </p>
     * <p>
     * since version 1.5.
     * </p>
     * @param projectId the project to create new version
     * @param tcDirectProjectId tc direct project id
     * @param autoDevCreating if it is true and it is design contest, then will create development too
     * @return newly version contest id
     * @throws ContestServiceException if any error occurs
     */
    public long createNewVersionForDesignDevContest(long projectId, long tcDirectProjectId, boolean autoDevCreating,
            boolean minorVersion) throws ContestServiceException {
        logger.debug("createNewVersionForDesignDevContest with parameter [projectId =" + projectId
                     + ", tcDirectProjectId =" +tcDirectProjectId+", autoDevCreating="+ autoDevCreating +"].");
       
        return createNewVersionForDesignDevContest(projectId, tcDirectProjectId, autoDevCreating,nextReOpenNewReleaseDay(), minorVersion);       
    }

    /**
     * <p>
     * Reopen the software contest.
     * </p>
     * <p>
     * since version 1.5.
     * </p>
     * @param projectId the project to repost
     * @param tcDirectProjectId the tc direct project id
     * @return the newly created OR project id
     * @throws ContestServiceException if any error occurs during repost
     */
    public long reOpenSoftwareContest(long projectId, long tcDirectProjectId) throws ContestServiceException {
        logger.debug("reOpenSoftwareContest with parameter [projectId =" + projectId + ", tcDirectProjectId =" +tcDirectProjectId+"].");

        long reOpenContestId = 0;
        try {
            
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            //0.check the permission first
            checkSoftwareProjectPermission(tcDirectProjectId, true);
            
            //1.make sure it is failed status and can be re-opened.
            FullProjectData contest = projectServices.getFullProjectData(projectId);
            if (contest == null) {
                throw new ContestServiceException("The project does not exist.");
            }
            if (contest.getProjectHeader().getProjectStatus().getId() != ProjectStatus.CANCELLED_FAILED_REVIEW_ID
                && contest.getProjectHeader().getProjectStatus().getId() != ProjectStatus.CANCELLED_FAILED_SCREENING_ID
                && contest.getProjectHeader().getProjectStatus().getId() != ProjectStatus.CANCELLED_ZERO_SUBMISSION_ID
                && contest.getProjectHeader().getProjectStatus().getId() != ProjectStatus.CANCELLED_WINNER_UNRESPONSIVE_ID) {
                throw new ProjectServicesException("The project is not failed. You can not re-open it.");
            }
            contest.setStartDate(getDate(nextReOpenNewReleaseDay()));
            //2.create the project
            FullProjectData reOpendedProject = 
                projectServices.createReOpenContest(contest, String.valueOf(p.getUserId()));
            
            //3. keep terms and eligibility
            List<ContestEligibility> contestEligibilities = 
                 contestEligibilityManager.getContestEligibility(contest.getProjectHeader().getId(), false); 
            for (ContestEligibility ce:contestEligibilities){   
                persistContestEligility(reOpendedProject.getProjectHeader().getId(), 0, ce, false);
            }

            reOpenContestId = reOpendedProject.getProjectHeader().getId();
            return reOpenContestId;
        } catch (Exception e) {
            sessionContext.setRollbackOnly();
            logger.error("Operation failed in the contest service facade.", e);
            throw new ContestServiceException("Operation failed in the contest service facade.",
                e);
        } finally {
            logger.debug("Exit reOpenSoftwareContest with the new contest " + reOpenContestId);
        }
    }
}
