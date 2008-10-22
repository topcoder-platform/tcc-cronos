/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.ejb;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
/*
import com.topcoder.forum.service.CategoryConfiguration;
import com.topcoder.forum.service.CategoryType;
import com.topcoder.forum.service.EntityType;
import com.topcoder.forum.service.JiveForumManagementException;
import com.topcoder.forum.service.ejb.JiveForumServiceRemote;
*/

import com.topcoder.web.ejb.pacts.BasePayment;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.security.auth.module.UserProfilePrincipal;
import com.topcoder.service.studio.ChangeHistoryData;
import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.ContestNotFoundException;
import com.topcoder.service.studio.ContestPayload;
import com.topcoder.service.studio.ContestPaymentData;
import com.topcoder.service.studio.ContestStatusData;
import com.topcoder.service.studio.ContestTypeData;
import com.topcoder.service.studio.DocumentNotFoundException;
import com.topcoder.service.studio.IllegalArgumentWSException;
import com.topcoder.service.studio.MediumData;
import com.topcoder.service.studio.PersistenceException;
import com.topcoder.service.studio.PrizeData;
import com.topcoder.service.studio.StatusNotAllowedException;
import com.topcoder.service.studio.StatusNotFoundException;
import com.topcoder.service.studio.StudioService;
import com.topcoder.service.studio.StudioServiceException;
import com.topcoder.service.studio.SubmissionData;
import com.topcoder.service.studio.UploadedDocument;
import com.topcoder.service.studio.UserNotAuthorizedException;
import com.topcoder.service.studio.contest.ContestChangeHistory;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.ContestManagerLocal;
import com.topcoder.service.studio.contest.ContestPayment;
import com.topcoder.service.studio.contest.ContestProperty;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestStatusTransitionException;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.ContestTypeConfig;
import com.topcoder.service.studio.contest.Document;
import com.topcoder.service.studio.contest.DocumentType;
import com.topcoder.service.studio.contest.EntityNotFoundException;
import com.topcoder.service.studio.contest.FilePath;
import com.topcoder.service.studio.contest.Medium;
import com.topcoder.service.studio.contest.MimeType;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.submission.ContestResult;
import com.topcoder.service.studio.submission.PaymentStatus;
import com.topcoder.service.studio.submission.Prize;
import com.topcoder.service.studio.submission.PrizeType;
import com.topcoder.service.studio.submission.Submission;
import com.topcoder.service.studio.submission.SubmissionManagementException;
import com.topcoder.service.studio.submission.SubmissionManagerLocal;
import com.topcoder.service.studio.submission.SubmissionPayment;
import com.topcoder.service.studio.submission.SubmissionReview;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.ejb.forums.Forums;
import com.topcoder.web.ejb.forums.ForumsHome;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.CreateException;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.naming.NamingException;

import java.rmi.RemoteException;

import java.sql.SQLException;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * This is the EJB implementation of the StudioService interface webservice
 * endpoint.
 *
 * It allows getting, updating, and creating contest data; get, remove and
 * update submission data; get some additional information like content's
 * categories, statuses and file types.
 *
 * It uses an instance of ContestManager and an instance of SubmissionManager
 * (injected as EJB) to perform the logic of the methods. The webservices
 * annotations are presents in the endpoint interface, this bean contains only
 * the annotation to connect the endpoint interface and this implementation. The
 * security is provided programmatically and also with annotations. The
 * exceptions are constructed using the fault bean because the fault message can
 * be consumed as SOAP message, this is necessary because it's a webservices.
 * This implementations is designed to be used by the related interface and also
 * by a different webservices client: all the response, request and exceptions
 * are automatically transformed to SOAP element. </p>
 *
 * <p>
 * Thread safety: this class is thread safe if the managers used are thread
 * safe. Considering that probably the managers beans will use the transactions,
 * this stateless bean is thread safe
 * </p>
 *
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.0
 */
// @WebService(endpointInterface = "com.topcoder.service.studio.StudioService")
@RunAs("Cockpit Administrator")
@RolesAllowed("Cockpit User")
@DeclareRoles( { "Cockpit User", "Cockpit Administrator" })
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Stateless
public class StudioServiceBean implements StudioService {
    /**
     * Random generator.
     */
    private static final Random RANDOM = new Random();

    /**
     * Private constant specifying user role.
     */
    private static final String USER_ROLE = "Cockpit User";

    /**
     * Private constant specifying administrator role.
     */
    private static final String ADMIN_ROLE = "Cockpit Administrator";

    /**
     * Private constant specifying default document base path.
     */
    private static final String DEFAULT_DOCUMENT_BASE_PATH = "/studiofiles/documents/";

    /**
     * <p>
     * Represents the loggerName used to retrieve the logger.
     * </p>
     */
    @Resource(name = "logName")
    private String logName;

    /**
     * <p>
     * Represents the sessionContext of the ejb.
     * </p>
     */
    @Resource
    private SessionContext sessionContext;

    /**
     * <p>
     * Represents the log used to log the methods logic of this class.
     * </p>
     */
    private Log log;

    /**
     * <p>
     * Represents the contest Manager instance used by this bean to perform all
     * methods related to Contests.
     * </p>
     */
    @EJB
    private ContestManagerLocal contestManager;

    /**
     * <p>
     * Represents the submission Manager instance used by this bean to perform
     * all methods related to Submissions.
     * </p>
     */
    @EJB
    private SubmissionManagerLocal submissionManager;

    // @EJB
    // @JndiInject(jndiName="JiveForumService/remote")
  //  private JiveForumServiceRemote jiveForumService;

    /**
     * <p>
     * Represents the draft status used to retrieve the check the draft status.
     * </p>
     */
    @Resource(name = "draftStatusId")
    private long draftStatusId;

    /**
     * <p>
     * Represents the scheduled status used to retrieve the check the draft status.
     * </p>
     */
    @Resource(name = "scheduledStatusId")
    private long scheduledStatusId;

    /**
     * <p>
     * Represents the unactive status used to retrieve the check the draft
     * status.
     * </p>
     */
    @Resource(name = "unactiveStatusId")
    private long unactiveStatusId;

    /**
     * <p>
     * Represents the active status
     * </p>
     */
    @Resource(name = "activeStatusId")
    private long activeStatusId;

    /**
     * Represents the id of status of a submission paid. </p>
     */
    @Resource(name = "submissionPaidStatusId")
    private long submissionPaidStatusId;

    /**
     * Represents the id of status of a submission unpaid. </p>
     */
    @Resource(name = "submissionUnpaidStatusId")
    private long submissionUnpaidStatusId;

    /**
     * Represents the id of status of a submission marked for purchase. </p>
     */
    @Resource(name = "submissionMarkedForPurchaseStatusId")
    private long submissionMarkedForPurchaseStatusId;

    /**
     * Represents the id of submission status of a submission that passed the
     * review. </p>
     */
    @Resource(name = "submissionPassedStatus")
    private long submissionPassedStatus;

    /**
     * Represents the id of review status of a submission that failed the
     * screen. </p>
     */
    @Resource(name = "reviewFailedStatusId")
    private long reviewFailedStatusId;

    /**
     * Represents the base URI used to construct the submission content. </p>
     */
    @Resource(name = "submissionContentBaseURI")
    private String submissionContentBaseURI;

    /**
     * Represents the parameter name of the submission id, it will be used in
     * the constructrion of submission URI content. </p>
     */
    @Resource(name = "submissionContentSubmissionIdParameterName")
    private String submissionContentSubmissionIdParameterName;

    /**
     * Represents the parameter name of the submission type, it will be used in
     * the constructrion of submission URI content. </p>
     */
    @Resource(name = "submissionContentSubmissionTypeParameterName")
    private String submissionContentSubmissionTypeParameterName;

    /**
     * Represents the parameter value of the submission paid parameter, it will
     * be used in the constructrion of submission URI content. </p>
     */
    @Resource(name = "submissionContentPaidParameterValue")
    private String submissionContentPaidParameterValue;

    /**
     * Represents the parameter value of the submission unpaid parameter, it
     * will be used in the constructrion of submission URI content. </p>
     */
    @Resource(name = "submissionContentUnpaidParameterValue")
    private String submissionContentUnpaidParameterValue;

    /**
     * Represents the id for the Contest property "Short Summary"
     *
     * @since 1.0.3
     */
    @Resource(name = "contestPropertyShortSummaryId")
    private long contestPropertyShortSummaryId;

    /**
     * Represents the id for the Contest property "Contest Overview Text"
     *
     * @since 1.0.3
     */
    @Resource(name = "contestPropertyContestOverviewTextId")
    private long contestPropertyContestOverviewTextId;

    /**
     * Represents the id for the Contest property "Color Requirements"
     *
     * @since 1.0.3
     */
    @Resource(name = "contestPropertyColorRequirementsId")
    private long contestPropertyColorRequirementsId;

    /**
     * Represents the id for the Contest property "Font Requirements"
     *
     * @since 1.0.3
     */
    @Resource(name = "contestPropertyFontRequirementsId")
    private long contestPropertyFontRequirementsId;

    /**
     * Represents the id for the Contest property "Size Requirements"
     *
     * @since 1.0.3
     */
    @Resource(name = "contestPropertySizeRequirementsId")
    private long contestPropertySizeRequirementsId;

    /**
     * Represents the id for the Contest property "Other Requirements"
     *
     * @since 1.0.3
     */
    @Resource(name = "contestPropertyOtherRequirementsId")
    private long contestPropertyOtherRequirementsId;

    /**
     * Represents the id for the Contest property "Final File Format"
     *
     * @since 1.0.3
     */
    @Resource(name = "contestPropertyFinalFileFormatId")
    private long contestPropertyFinalFileFormatId;

    /**
     * Represents the id for the Contest property "Other File Formats"
     *
     * @since 1.0.3
     */
    @Resource(name = "contestPropertyOtherFileFormatsId")
    private long contestPropertyOtherFileFormatsId;

    /**
     * Represents the id for the Contest property "Create User Handle"
     */
    @Resource(name = "contestPropertyCreateUserHandleId")
    private long contestPropertyCreateUserHandleId;

    /**
     * Represents the id for the Contest property "Requires Preview Image"
     *
     * @since TCCC-284
     */
    @Resource(name = "contestPropertyRequiresPreviewImageId")
    private long contestPropertyRequiresPreviewImageId;

    /**
     * Represents the id for the Contest property "Requires Preview File"
     *
     * @since TCCC-284
     */
    @Resource(name = "contestPropertyRequiresPreviewFileId")
    private long contestPropertyRequiresPreviewFileId;

    /**
     * Represents the id for the Contest property "Maximum Submissions"
     *
     * @since TCCC-284
     */
    @Resource(name = "contestPropertyMaximumSubmissionsId")
    private long contestPropertyMaximumSubmissionsId;

    /**
     * Represents the id for the Contest property "Eligibility".
     *
     * @since TCCC-283
     */
    @Resource(name = "contestPropertyEligibilityId")
    private long contestPropertyEligibilityId;

    /**
     * Represents the id for the Contest property "Notes on Winner Selection".
     *
     * @since TCCC-283
     */
    @Resource(name = "contestPropertyNotesOnWinnerSelectionId")
    private long contestPropertyNotesOnWinnerSelectionId;

    /**
     * Represents the id for the Contest property "Prize Description".
     *
     * @since TCCC-283
     */
    @Resource(name = "contestPropertyPrizeDescriptionId")
    private long contestPropertyPrizeDescriptionId;

    /**
     * Represents the default text for the Contest property "Eligibility".
     *
     * @since TCCC-283
     */
    @Resource(name = "defaultContestEligibilityText")
    private String defaultContestEligibilityText;

    /**
     * Represents the default text for the Contest property
     * "Notes on Winner Selection".
     *
     * @since TCCC-283
     */
    @Resource(name = "defaultContestNotesOnWinnerSelectionText")
    private String defaultContestNotesOnWinnerSelectionText;

    /**
     * Represents the default text for the Contest property "Prize Description".
     *
     * @since TCCC-283
     */
    @Resource(name = "defaultContestPrizeDescriptionText")
    private String defaultContestPrizeDescriptionText;

    /**
     * Represents the default text for the Contest property
     * "*Notes on Submission File(s)".
     *
     * @since TCCC-384
     */
    @Resource(name = "defaultContestNotesOnSubmissionFiles")
    private String defaultContestNotesOnSubmissionFiles;

    /**
     * Represents the id for the Contest property "Contest PrizeType Id"
     *
     * @since TCCC-351
     */
    @Resource(name = "contestPrizeTypeId")
    private long contestPrizeTypeId;

    /**
     * Represents the id for the Contest property
     * "Client Selection PrizeType Id"
     *
     * @since TCCC-351
     */
    @Resource(name = "clientSelectionPrizeTypeId")
    private long clientSelectionPrizeTypeId;

    /**
     * Represents the base path for the documents. Should be configured like
     * /studiofiles/documents.
     *
     * [BUG TCCC-134]
     */
    @Resource(name = "documentBasePath")
    private String documentBasePath = DEFAULT_DOCUMENT_BASE_PATH;

    /**
     * Represents the additionalSubmissionPurchasePriceRatio. Used in
     * purchaseSubmission method.
     *
     * @since TCCC-353
     */
    @Resource(name = "additionalSubmissionPurchasePriceRatio")
    private float additionalSubmissionPurchasePriceRatio;

    /**
     * Represents the id for submission status "removed".
     *
     * @since TCCC-411
     */
    @Resource(name = "submissionRemovedStatusId")
    private long submissionRemovedStatusId;

    /**
     * Represents the id for the Contest property "Digital Run Points".
     *
     * @since TCCC-499
     */
    @Resource(name = "contestPropertyDigitalRunPointsId")
    private long contestPropertyDigitalRunPointsId;

    /**
     * Represents the id for the Contest property "Contest Administration Fee ".
     *
     * @since TCCC-499
     */
    @Resource(name = "contestPropertyContestAdministrationFeeId")
    private long contestPropertyContestAdministrationFeeId;

    /**
     * Represents the id for the Contest property "Contest Administration Fee ".
     *
	 * @since BUGR-456
     */
    @Resource(name = "autoPaymentsEnabled")
    private boolean autoPaymentsEnabled = false;

    /**
     * Represents the location of the PACTS services for submission auto-paymetns
     *
	 * @since BUGR-456
     */
    @Resource(name = "pactsServiceLocation")
    private String pactsServiceLocation = "jnp://localhost:1099";

    /**
     * Represents the jive forum version text.
     *
     * @since TCCC-287
     */
//    @Resource(name = "forumVersionText")
 //   private String forumVersionText;

    /**
     * Represents the jive forum RootCategoryId.
     *
     * @since TCCC-287
     */
 //   @Resource(name = "forumRootCategoryId")
 //   private long forumRootCategoryId;

    /**
     * Represents the jive forum VersionId.
     *
     * @since TCCC-287
     */
 //   @Resource(name = "forumVersionId")
 //   private long forumVersionId;


    @Resource(name = "forumBeanProviderUrl")
    private String forumBeanProviderUrl;


    /**
     * Returns base path for document files.
     *
     * [BUG TCCC-134]
     *
     * @return the documentBasePath
     */
    public String getDocumentBasePath() {
        return documentBasePath;
    }

    /**
     * Sets the base path for document files.
     *
     * [BUG TCCC-134]
     *
     * @param documentBasePath
     *            the documentBasePath to set.
     */
    public void setDocumentBasePath(String documentBasePath) {
        this.documentBasePath = documentBasePath;
    }

    /**
     * <p>
     * This is the default constructor. It does nothing.
     * </p>
     */
    public StudioServiceBean() {
    }

    /**
     * <p>
     * This is method is performed after the construction of the bean, at this
     * point all the bean's resources will be ready.
     * </p>
     */
    @PostConstruct
    private void init() {
        if (logName != null) {
            if (logName.trim().length() == 0) {
                throw new IllegalStateException("logName parameter not supposed to be empty.");
            }
            log = LogManager.getLog(logName);
        }
/*
        try{
        InitialContext ctx = new InitialContext();
        jiveForumService = (JiveForumServiceRemote) ctx.lookup("cockpit/JiveForumService/remote");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
*/
        // first record in logger
        logExit("init");
    }

    /**
     * <p>
     * Create contest for project. Return contest populated with id.
     * </p>
     *
     * @param contestData
     *            the contestData used to create the Contest
     * @param tcDirectProjectId
     *            the tc project id set to Contest
     * @return the ContestData persisted and polulated with the new id
     * @throws IllegalArgumentWSException
     *             if the contestData is null or if the tcDirectProjectId is
     *             less than 0
     * @throws PersistenceException
     *             if some persistence errors occur
     * @throws UserNotAuthorizedException
     *             if the user is not authorized to perform this method
     */
    public ContestData createContest(ContestData contestData, long tcDirectProjectId) throws PersistenceException {
        logEnter("createContest", contestData, tcDirectProjectId);
        checkParameter("contestData", contestData);
        checkParameter("tcDirectProjectId", tcDirectProjectId);

        try {
            Contest contest = convertContestData(contestData);

            contest.setTcDirectProjectId(tcDirectProjectId);

            // use the logged user [27074484-16]
            long userId = ((UserProfilePrincipal) sessionContext.getCallerPrincipal()).getUserId();
            contest.setCreatedUser(userId);

            contest = contestManager.createContest(contest);
/*
            // [TCCC-287]
            CategoryConfiguration categoryConfiguration = new CategoryConfiguration();
            // Name: The name of the contest
            categoryConfiguration.setName(contest.getName());
            // Description:
            // "Forum for cockpit contest: " + name of contest
            categoryConfiguration.setDescription("Forum for cockpit contest: " + contest.getName());
            // ComponentID: The ID of the contest
            categoryConfiguration.setComponentId(contest.getContestId());
            // IsPublic: true categoryConfiguration.setPublic(true);
            // CategoryType: Application
            categoryConfiguration.setTemplateCategoryType(CategoryType.APPLICATION);
            categoryConfiguration.setVersionText(forumVersionText);
            categoryConfiguration.setVersionId(forumVersionId);
            categoryConfiguration.setRootCategoryId(forumRootCategoryId);

            logDebug("Create forum category: " + categoryConfiguration.getName());
            contest.setForumId(jiveForumService.createCategory(categoryConfiguration));
            logDebug("Created forum id: " + contest.getForumId());

            // TCCC-511
            jiveForumService.watch(userId, contest.getForumId(), EntityType.FORUM_CATEGORY);
*/
            /*
            long forumId = createForum(contest.getName(), userId);
            contest.setForumId(forumId);
            */

/*
            // FIX [TCCC-146]
            for (PrizeData prizeData : contestData.getPrizes()) {
                Prize prize = new Prize();
                prize.setAmount(prizeData.getAmount());
                prize.setPlace(prizeData.getPlace());
                prize.setCreateDate(new Date());

                // [TCCC-351]
                PrizeType type = contestManager.getPrizeType(contestPrizeTypeId);
                prize.setType(type);
                Set<Contest> contests = prize.getContests();
                contests.add(contest);
                prize.setContests(contests);

                contestManager.createPrize(prize);
                logError("Prize created. Id: " + prize.getPrizeId());
            }
*/
            List<UploadedDocument> documents = new ArrayList<UploadedDocument>();
            for (UploadedDocument doc : contestData.getDocumentationUploads()) {
                documents.add(uploadDocument(doc, contest));
            }
            contestData = convertContest(contest);
            contestData.setDocumentationUploads(documents);
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while creating new contest.", e);
//        } catch (JiveForumManagementException e) {
  //          handlePersistenceError("JiveForumService reports error while creating new forum. " + e.getMessage(), e);
        }

        logExit("createContest", contestData);
        return contestData;
    }

    private long createForum(String name, long userId) {
		try {
			Properties p = new Properties();
			p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			p.put(Context.URL_PKG_PREFIXES,"org.jboss.naming:org.jnp.interfaces");
			p.put(Context.PROVIDER_URL, forumBeanProviderUrl);

			Context c = new InitialContext(p);
			ForumsHome forumsHome = (ForumsHome) c.lookup(ForumsHome.EJB_REF_NAME);

			Forums forums = forumsHome.create();

			long forumId = forums.createStudioForum(name);
			if (forumId < 0) throw new Exception("createStudioForum returned -1");

			log.log(Level.DEBUG, "Created forum " + forumId + " for " + name);

			forums.createForumWatch(userId, forumId);

			return forumId;
		} catch (Exception e) {
			log.log(Level.ERROR, "*** Could not create a forum for " + name);
			logError(e);
			return -1;
		}
	}


    /**
     * <p>
     * Get contest by id
     * </p>
     *
     * @param contestId
     *            the id used to retrireve the contest
     * @return the ContestData retrieved
     * @throws IllegalArgumentWSException
     *             if the contestId is less than 0
     * @throws PersistenceException
     *             if some persistence errors occur
     * @throws UserNotAuthorizedException
     *             if the user is not authorized to perform this method
     * @throws ContestNotFoundException
     *             if the contest is not found
     */
    public ContestData getContest(long contestId) throws PersistenceException, ContestNotFoundException {
        logEnter("getContest", contestId);
        checkParameter("contestId", contestId);

        try {
            // check for the authorization, which also returns the contest.
            Contest contest = authorizeWithContest(contestId);

            ContestData result = convertContest(contest);

            logExit("getContest", result);
            return result;
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while retrieving contest.", e);
        }

        // never reached
        return null;
    }

    /**
     * <p>
     * Get contests for given project. Return an empty list if there are no
     * contests.
     * </p>
     *
     * @param tcDirectProjectId
     *            the tc Direct Projec tId used to retrieve the ContestData
     * @return the contest datas which represents the contests
     * @throws IllegalArgumentWSException
     *             if the tcDirectProjectId is less than 0
     * @throws PersistenceException
     *             if some persistence errors occur
     * @throws UserNotAuthorizedException
     *             if the user is not authorized to perform this method
     */
    public List<ContestData> getContestsForProject(long tcDirectProjectId) throws PersistenceException {
        logEnter("getContestsForProject", tcDirectProjectId);
        checkParameter("tcDirectProjectId", tcDirectProjectId);

        // no authorization applyed

        try {
            List<Contest> contests = contestManager.getContestsForProject(tcDirectProjectId);
            List<ContestData> result = convertContests(contests);

            logExit("getContestsForProject", result);
            return result;
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while retrieving contest.", e);
        }

        // never reached
        return null;
    }

    /**
     * <p>
     * Update contest
     * </p>
     *
     * @param contestData
     *            the contest data to update
     * @throws IllegalArgumentWSException
     *             if the argument is null
     * @throws PersistenceException
     *             if some persistence errors occur
     * @throws UserNotAuthorizedException
     *             if the user is not authorized to perform this method
     * @throws ContestNotFoundException
     *             if the contest is not found
     */
    public void updateContest(ContestData contestData) throws PersistenceException, ContestNotFoundException {
        logEnter("updateContest", contestData);
        checkParameter("contestData", contestData);

        // authorization
        authorizeWithContest(contestData.getContestId());

        String username = sessionContext.getCallerPrincipal().getName();
        boolean userAdmin = sessionContext.isCallerInRole(ADMIN_ROLE);

        try {
            long timestamp = System.currentTimeMillis()*100 + RANDOM.nextInt(1000);
            contestManager.updateContest(convertContestData(contestData), timestamp, username, userAdmin);
        } catch (EntityNotFoundException e) {
            handleContestNotFoundError(e, contestData.getContestId());
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while updating contest.", e);
        }

        logExit("updateContest");
    }

    /**
     * <p>
     * Update contest status
     * </p>
     *
     * @param contestId
     *            the id of contest to what the status will be updated
     * @param newStatusId
     *            the id of the new status
     * @throws IllegalArgumentWSException
     *             if any id is less than 0
     * @throws PersistenceException
     *             if some persistence errors occur
     * @throws UserNotAuthorizedException
     *             if the user is not authorized to perform this method
     * @throws StatusNotAllowedException
     *             if the status is not allowed
     * @throws StatusNotFoundException
     *             if the status is not found
     * @throws ContestNotFoundException
     *             if the contest is not found
     */
    public void updateContestStatus(long contestId, long newStatusId) throws PersistenceException,
            ContestNotFoundException, StatusNotFoundException, StatusNotAllowedException {
        logEnter("updateContestStatus", contestId, newStatusId);
        checkParameter("contestId", contestId);
        checkParameter("newStatusId", newStatusId);

        authorizeWithContest(contestId);

        try {
            contestManager.updateContestStatus(contestId, newStatusId);

        } catch (EntityNotFoundException e) {
            // since contest existance already checked, we miss status entity
            String message = "can't find Status with id " + newStatusId + " in persistence.";
            logError(e, message);
            StatusNotFoundFault fault = new StatusNotFoundFault();
            fault.setStatusIdNotFound(newStatusId);
            throw new StatusNotFoundException(message, fault, e);

        } catch (ContestStatusTransitionException e) {
            // enother error
            String message = "ContestManager reports ContestStatusTransitionException.";
            logError(e, message);
            StatusNotAllowedFault fault = new StatusNotAllowedFault();
            fault.setStatusIdNotAllowed(newStatusId);
            throw new StatusNotAllowedException(message, fault, e);

        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while updating contest status.", e);
        }

        logExit("updateContestStatus");
    }

    /**
     * <p>
     * Upload document to contest. Return document populated with id.
     * </p>
     *
     * @param uploadedDocument
     *            the uploadDocument to update
     * @return the same instance passed in argument with the documentId updated
     * @throws IllegalArgumentWSException
     *             if the argument is null
     * @throws PersistenceException
     *             if some persistence errors occur
     * @throws UserNotAuthorizedException
     *             if the user is not authorized to perform this method
     * @throws ContestNotFoundException
     *             if the contest is not found
     */
    public UploadedDocument uploadDocumentForContest(UploadedDocument uploadedDocument) throws PersistenceException,
            ContestNotFoundException {
        logEnter("uploadDocumentForContest", uploadedDocument);
        checkParameter("uploadedDocument", uploadedDocument);

        // retrieve contest and authorize
        Contest c = authorizeWithContest(uploadedDocument.getContestId());

        // last authentication check
        if (sessionContext.isCallerInRole(USER_ROLE)) {
            // [TCCC-385]
            if (c.getStatus() == null
                    || (c.getStatus().getContestStatusId() != draftStatusId
                    		&& c.getStatus().getContestStatusId() != unactiveStatusId
                    		&& c.getStatus().getContestStatusId() != activeStatusId
                    		&& c.getStatus().getContestStatusId() != scheduledStatusId)) {
                handleAuthorizationError("contest must be in draft status or unactive status.");
            }
        }

        // finally, upload document and return
        uploadedDocument = uploadDocument(uploadedDocument, c);
        logExit("uploadDocumentForContest", uploadedDocument);
        return uploadedDocument;
    }

    /**
     * <p>
     * Remove document from contest.
     * </p>
     *
     * @param document
     *            the document to remove
     * @throws IllegalArgumentWSException
     *             if the argument is null
     * @throws PersistenceException
     *             if some persistence errors occur
     * @throws UserNotAuthorizedException
     *             if the user is not authorized to perform this method
     * @throws DocumentNotFoundException
     *             if the document is not found
     */
    public void removeDocumentFromContest(UploadedDocument document) throws PersistenceException,
            DocumentNotFoundException {
        logEnter("removeDocumentFromContest", document);
        checkParameter("document", document);

        try {
            // authorization
            authorizeWithContest(document.getContestId());

            contestManager.removeDocumentFromContest(document.getDocumentId(), document.getContestId());

        } catch (EntityNotFoundException ex) {
            // no such document in persistence
            String message = "can't find document with id " + document.getDocumentId() + " in persistence.";
            logError(ex, message);
            DocumentNotFoundFault fault = new DocumentNotFoundFault();
            fault.setDocumentIdNotFound(document.getDocumentId());
            throw new DocumentNotFoundException(message, fault, ex);

        } catch (ContestManagementException ex) {
            handlePersistenceError("ContestManager reports error while removing document from contest.", ex);

        } catch (ContestNotFoundException e) {
            handlePersistenceError("Contest not found when trying to remove document from contest", e);
        }

        logExit("removeDocumentFromContest");
    }

    /**
     * <p>
     * Retrieve submission data. return an empty list if there are no submission
     * data items.
     * </p>
     *
     * @param contestId
     *            the contest if used
     * @return the submission data of contest
     * @throws IllegalArgumentWSException
     *             if the argument id less than 0
     * @throws PersistenceException
     *             if some persistence errors occur
     * @throws UserNotAuthorizedException
     *             if the user is not authorized to perform this method
     * @throws ContestNotFoundException
     *             if the contest is not found
     */
    public List<SubmissionData> retrieveSubmissionsForContest(long contestId) throws PersistenceException,
            ContestNotFoundException {
        logEnter("retrieveSubmissionsForContest", contestId);
        checkParameter("contestId", contestId);

        // authorization
        authorizeWithContest(contestId);

        boolean selectFullSubmission = sessionContext.isCallerInRole(ADMIN_ROLE);

        // check if contest with such id is presented
        try {
            if (contestManager.getContest(contestId) == null) {
                handleContestNotFoundError(null, contestId);
            }
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while retrieving contest.", e);
        }

        try {
            List<SubmissionData> result = convertSubmissions(submissionManager.getSubmissionsForContest(contestId,
                    selectFullSubmission));

            // Retrieve max submissions per user.
            Contest contest = contestManager.getContest(contestId);
            int maxSubmissionsPerUser = 0;
            for (ContestConfig config : contest.getConfig()) {
                if (config.getId().getProperty().getPropertyId() == contestPropertyMaximumSubmissionsId) {
                    try {
                        maxSubmissionsPerUser = Integer.parseInt(config.getValue());
                    } catch (NumberFormatException e) {
                        maxSubmissionsPerUser = 0;
                    }
                    break;
                }
            }

            log.log(Level.DEBUG, "Max Submissions for contest is " + maxSubmissionsPerUser + ", contest id:"
                    + contestId);
            List<SubmissionData> submissionsToBeRemoved = new ArrayList<SubmissionData>();

            // Get submissions need to be removed.
            for (SubmissionData submissionData : result) {
                if (maxSubmissionsPerUser > 0 && submissionData.getRank() != null
                        && submissionData.getRank() > maxSubmissionsPerUser) {
                    submissionsToBeRemoved.add(submissionData);
                } else if (!submissionData.isPassedScreening()) {
                    // TCCC-445
                    submissionsToBeRemoved.add(submissionData);
                }
            }

            // Remove.
            for (SubmissionData submissionData : submissionsToBeRemoved) {
                result.remove(submissionData);
            }

            logExit("retrieveSubmissionsForContest", result);

            return result;
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManager reports error while retrieving submissions for contest.", e);
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while retrieving contest.", e);
        }

        // never reached
        return null;
    }

    /**
     * <p>
     * Retrieve submission by member. return an empty list if there are no
     * submission data
     * </p>
     *
     * @param userId
     *            the user id used to retrieve the submissions
     * @return the submissions by member
     * @throws IllegalArgumentWSException
     *             if the id is less than 0
     * @throws PersistenceException
     *             if some persistence errors occur
     * @throws UserNotAuthorizedException
     *             if the user is not authorized to perform this method
     */
    public List<SubmissionData> retrieveAllSubmissionsByMember(long userId) throws PersistenceException {
        logEnter("retrieveAllSubmissionsByMember", userId);
        checkParameter("userId", userId);

        // authorization
        authorizeAdmin();

        try {
            List<SubmissionData> result = convertSubmissions(submissionManager.getAllSubmissionsByMember(userId));
            logExit("retrieveAllSubmissionsByMember", result);
            return result;
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManager reports error while fetching submissions by member.", e);
        }

        // never reached
        return null;
    }

    /**
     * <p>
     * Update submission.
     * </p>
     *
     * @param submission
     *            the submission to update
     * @throws IllegalArgumentWSException
     *             if the submission is null
     * @throws PersistenceException
     *             if some persistence errors occur
     * @throws UserNotAuthorizedException
     *             if the user is not authorized to perform this method
     */
    public void updateSubmission(SubmissionData submission) throws PersistenceException {
        logEnter("updateSubmission", submission);
        checkParameter("submission", submission);

        try {
            authorizeWithContest(submission.getContestId());

            submissionManager.updateSubmission(convertSubmissionData(submission));
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManager reports error while updating submission.", e);

        } catch (ContestNotFoundException e) {
            handlePersistenceError("Contest not found when trying to remove document from contest", e);
        }

        logExit("updateSubmission");
    }

    /**
     * <p>
     * Remove submission
     * </p>
     *
     * @param submissionId
     *            the id of submission to remove
     * @throws IllegalArgumentWSException
     *             if the submissionId is less than 0
     * @throws PersistenceException
     *             if some persistence errors occur
     * @throws UserNotAuthorizedException
     *             if the user is not authorized to perform this method
     */
    public void removeSubmission(long submissionId) throws PersistenceException {
        logEnter("removeSubmission", submissionId);
        checkParameter("submissionId", submissionId);

        // authorization
        authorizeAdmin();

        try {
            submissionManager.removeSubmission(submissionId);
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManager reports error while removing submission.", e);
        }

        logExit("removeSubmission");
    }

    /**
     * <p>
     * Get contest statuses. Return an empty list if there are no
     * ContestStatusData
     * </p>
     *
     * @return the list of status
     * @throws PersistenceException
     *             if some persistence errors occur
     * @throws UserNotAuthorizedException
     *             if the user is not authorized to perform this method
     */
    public List<ContestStatusData> getStatusList() throws PersistenceException {
        logEnter("getStatusList");

        // no authorization required

        try {
            List<ContestStatusData> result = new ArrayList<ContestStatusData>();
            for (ContestStatus status : contestManager.getAllContestStatuses()) {
                ContestStatusData data = new ContestStatusData();
                data.setDescription(status.getDescription());
                data.setName(status.getName());
                if (status.getContestStatusId() != null) {
                    // prevent NPE
                    data.setStatusId(status.getContestStatusId());
                }

                List<Long> nextStatuses = new ArrayList<Long>();
                for (ContestStatus cs : status.getStatuses()) {
                    nextStatuses.add(cs.getContestStatusId());
                }

                data.setAllowableNextStatus(nextStatuses);
                result.add(data);
            }

            logExit("getStatusList", result);
            return result;
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while retrieving contest statuses.", e);
        }

        // never reached
        return null;
    }

    /**
     * <p>
     * Get submission types
     * </p>
     *
     * @return the file types
     * @throws PersistenceException
     *             if some persistence errors occur
     * @throws UserNotAuthorizedException
     *             if the user is not authorized to perform this method
     */
    public String getSubmissionFileTypes() throws PersistenceException {
        logEnter("getSubmissionFileTypes");

        // no authorization required

        try {
            StringBuilder buf = new StringBuilder();

            for (StudioFileType fileType : contestManager.getAllStudioFileTypes()) {
                if (buf.length() != 0) {
                    buf.append(",");
                }
                buf.append(fileType.getExtension());
            }

            logExit("getSubmissionFileTypes", buf.toString());
            return buf.toString();
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while retrieving studio file types.", e);
        }

        // never reached
        return null;
    }

    /**
     * <p>
     * Return the sessionContext. Don't perfom the logging in this method.
     * </p>
     *
     * @return the sessionContext
     */
    protected SessionContext getSessionContext() {
        return sessionContext;
    }

    /**
     * <p>
     * Return the log. Don't perfom the logging in this method.
     * </p>
     *
     * @return the log
     */
    protected Log getLog() {
        return log;
    }

    /**
     * <p>
     * Return the contestManager. Don't perfom the logging in this method.
     * </p>
     *
     * @return the contestManager
     */
    protected ContestManagerLocal getContestManager() {
        return contestManager;
    }

    /**
     * <p>
     * Return the submissionManager. Don't perfom the logging in this method.
     * </p>
     *
     * @return the submissionManager
     */
    protected SubmissionManagerLocal getSubmissionManager() {
        return submissionManager;
    }

    /**
     * This method used to convert ContestData object into Contest object.
     *
     * @param data
     *            ContestData object to convert
     * @return converted Contest instance
     * @throws PersistenceException
     *             when error reported by manager
     * @throws ContestManagementException
     *             when error reported by manager
     */
    private Contest convertContestData(ContestData data) throws PersistenceException, ContestManagementException {
        Contest result = new Contest();

        // StartDate and endDate: the startDate is the launchDateAndTime, the
        // endDate is launchDateTime+durationInHours
        Date startDate = getDate(data.getLaunchDateAndTime());
        result.setStartDate(startDate);
        if (startDate != null) {
            result.setEndDate(new Date((long) (startDate.getTime() + 60l * 60 * 1000 * data.getDurationInHours())));
        }

        addContestConfig(result, contestPropertyShortSummaryId, data.getShortSummary());

        addContestConfig(result, contestPropertyContestOverviewTextId, data.getContestDescriptionAndRequirements());

        addContestConfig(result, contestPropertyColorRequirementsId, data.getRequiredOrRestrictedColors());

        addContestConfig(result, contestPropertyFontRequirementsId, data.getRequiredOrRestrictedFonts());

        addContestConfig(result, contestPropertySizeRequirementsId, data.getSizeRequirements());

        addContestConfig(result, contestPropertyOtherRequirementsId, data.getOtherRequirementsOrRestrictions());

        // [TCCC-499]
        addContestConfig(result, contestPropertyDigitalRunPointsId, String.valueOf(data.getDrPoints()));
        addContestConfig(result, contestPropertyContestAdministrationFeeId, String.valueOf(data
                .getContestAdministrationFee()));

        // [TCCC-325].
        Set<StudioFileType> fileTypes = new HashSet<StudioFileType>();
        List<StudioFileType> allFileTypes = contestManager.getAllStudioFileTypes();
        StringTokenizer tokenizer = new StringTokenizer(data.getFinalFileFormat(), ",");
        while (tokenizer.hasMoreTokens()) {
            String format = tokenizer.nextToken();
            boolean found = false;
            for (StudioFileType type : allFileTypes) {
                if (type.getExtension().equals(format)) {
                    fileTypes.add(type);
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new ContestManagementException("Unknown studio file type: " + format);
            }
        }
        result.setFileTypes(fileTypes);

        addContestConfig(result, contestPropertyOtherFileFormatsId, data.getOtherFileFormats());

        // [TCCC-384]
        addContestConfig(result, contestPropertyFinalFileFormatId, defaultContestNotesOnSubmissionFiles);

        // Create user handle.
        addContestConfig(result, contestPropertyCreateUserHandleId, ((UserProfilePrincipal) sessionContext
                .getCallerPrincipal()).getName());

        // [TCCC-284]
        addContestConfig(result, contestPropertyRequiresPreviewFileId, String.valueOf(data.isRequiresPreviewFile()));
        addContestConfig(result, contestPropertyRequiresPreviewImageId, String.valueOf(data.isRequiresPreviewImage()));
        addContestConfig(result, contestPropertyMaximumSubmissionsId, String.valueOf(data.getMaximumSubmissions()));

        // [TCCC-283]
        addContestConfig(result, contestPropertyEligibilityId, defaultContestEligibilityText);
        addContestConfig(result, contestPropertyNotesOnWinnerSelectionId, defaultContestNotesOnWinnerSelectionText);
        addContestConfig(result, contestPropertyPrizeDescriptionId, defaultContestPrizeDescriptionText);

        result.setContestId(data.getContestId());
        result.setName(data.getName());
        result.setProjectId(data.getProjectId());
        result.setWinnerAnnoucementDeadline(getDate(data.getWinnerAnnoucementDeadline()));

        logError("ContestData.status: " + data.getStatusId());
        ContestStatus contestStatus = contestManager.getContestStatus(data.getStatusId());
        logError(MessageFormat.format("Retrieved contest status: desc:[{0}] name:[{1}] id:[{2}]", contestStatus
                .getDescription(), contestStatus.getName(), contestStatus.getContestStatusId()));

        result.setStatus(contestStatus);
        result.setTcDirectProjectId(data.getTcDirectProjectId());
        result.setContestType(getContestType(data.getContestTypeId()));
        result.setContestChannel(contestManager.getContestChannel(data.getContestChannelId()));
        result.setCreatedUser(data.getCreatorUserId());
        result.setLaunchImmediately(data.isLaunchImmediately());

        Set<Medium> media = new HashSet<Medium>();
        for (MediumData mediumData : data.getMedia()) {
            Medium medium = new Medium();
            medium.setDescription(mediumData.getDescription());
            medium.setMediumId(mediumData.getMediumId());
            media.add(medium);
        }
        result.setMedia(media);

        Set<Prize> prizes = new HashSet<Prize>();
        PrizeType type = contestManager.getPrizeType(contestPrizeTypeId);
        for (PrizeData prizeData : data.getPrizes()) {
            Prize prize = new Prize();
            prize.setAmount(prizeData.getAmount());
            prize.setPlace(prizeData.getPlace());
            prize.setCreateDate(new Date());

            prizes.add(prize);
            // [TCCC-351]
            prize.setType(type);
            Set<Contest> contests = prize.getContests();
            contests.add(result);
            prize.setContests(contests);
        }
        result.setPrizes(prizes);


        return result;
    }

    /**
     * Get the ContestType object from persistence by its id
     *
     * @param contestTypeId
     *            id to retrieve
     * @return a ContestType object with that id, or null if not found.
     *
     * @throws ContestManagementException
     */
    private ContestType getContestType(long contestTypeId) throws ContestManagementException {
        List<ContestType> contestTypes = contestManager.getAllContestTypes();

        for (ContestType ct : contestTypes) {
            if (ct.getContestType() == contestTypeId) {
                return ct;
            }
        }

        return null;
    }

    /**
     * This method converts Contest object into ContestData object.
     *
     * @param contest
     *            Contest instance to convert
     * @return converted ContestDate object
     * @throws ContestManagementException
     *             error occurred from ContestManager
     */
    private ContestData convertContest(Contest contest) throws ContestManagementException {
        return convertContest(contest, true);
    }

    /**
     * This method converts Contest object into ContestData object.
     *
     * @param contest
     *            Contest instance to convert
     * @param Load forum info or not.
     * @return converted ContestDate object
     * @throws ContestManagementException
     *             error occurred from ContestManager
     */
    private ContestData convertContest(Contest contest, boolean loadForumInfo) throws ContestManagementException {
        ContestData contestData = new ContestData();

        contestData.setLaunchDateAndTime(getXMLGregorianCalendar(contest.getStartDate()));

        contestData.setContestId(unbox(contest.getContestId()));
        contestData.setCreatorUserId(unbox(contest.getCreatedUser()));
        contestData.setName(contest.getName());
        contestData.setProjectId(unbox(contest.getProjectId()));
        contestData.setWinnerAnnoucementDeadline(getXMLGregorianCalendar(contest.getWinnerAnnoucementDeadline()));
        contestData.setStatusId(contest.getStatus().getContestStatusId());
        contestData.setNumberOfRegistrants(contest.getContestRegistrations().size());
        contestData.setLaunchImmediately(contest.isLaunchImmediately());

        // [TCCC-585]
        if(contest.getTcDirectProjectId()!=null){
            contestData.setTcDirectProjectId(contest.getTcDirectProjectId());
        }
        // [27074484-20]
        ContestType contestType = contest.getContestType();
        contestData.setContestTypeId(contestType == null ? -1 : unbox(contestType.getContestType()));

        Set<Submission> submissions = contest.getSubmissions();
        if (submissions != null) {
            // [TCCC-369]
            int maxSubmissionsPerUser = 0;
            for (ContestConfig config : contest.getConfig()) {
                if (config.getId().getProperty().getPropertyId() == contestPropertyMaximumSubmissionsId) {
                    try {
                        maxSubmissionsPerUser = Integer.parseInt(config.getValue());
                    } catch (NumberFormatException e) {
                        maxSubmissionsPerUser = 0;
                    }
                    break;
                }
            }

            int submissionsToBeRemoved = 0;

            // Get submissions need to be removed.
            for (Submission submission : submissions) {
                if (maxSubmissionsPerUser > 0 && submission.getRank() != null
                        && submission.getRank() > maxSubmissionsPerUser) {
                    submissionsToBeRemoved++;
                } else if (submission.getStatus() != null
                        && submission.getStatus().getSubmissionStatusId() == submissionRemovedStatusId) {
                    // TCCC-414
                    submissionsToBeRemoved++;
                } else if (submission.getReview() != null) {
                    // TCCC-445
                    for (SubmissionReview review : submission.getReview()) {
                        if (review.getStatus().getReviewStatusId() == reviewFailedStatusId) {
                            submissionsToBeRemoved++;
                            break;
                        }
                    }
                }
            }

            contestData.setSubmissionCount(submissions.size() - submissionsToBeRemoved);
            logDebug(contestData.getSubmissionCount() + " valid submissions found for contest. contest id: "
                    + contest.getContestId() + " ; Total submission: " + submissions.size());
        }

        // [TCCC-325]
        Set<StudioFileType> fileTypes = contest.getFileTypes();
        StringBuilder finalFileFormatSB = new StringBuilder();
        for (StudioFileType fileType : fileTypes) {
            finalFileFormatSB.append(fileType.getExtension() + ",");
        }
        String finalFileFormat = finalFileFormatSB.toString();
        if (finalFileFormat.length() > 0) {
            finalFileFormat = finalFileFormat.substring(0, finalFileFormat.length() - 1);
        }
        contestData.setFinalFileFormat(finalFileFormat);

        // Since 1.0.3, Bug Fix 27074484-14
        for (ContestConfig cc : contest.getConfig()) {

            // FIX [TCCC-326]
            String value = cc.getValue();
            if (value == null)
                continue;

            long propertyId = cc.getId().getProperty().getPropertyId();
            if (propertyId == contestPropertyShortSummaryId)
                contestData.setShortSummary(value);
            else if (propertyId == contestPropertyOtherFileFormatsId)
                contestData.setOtherFileFormats(value);
            else if (propertyId == contestPropertyColorRequirementsId)
                contestData.setRequiredOrRestrictedColors(value);
            else if (propertyId == contestPropertyFontRequirementsId)
                contestData.setRequiredOrRestrictedFonts(value);
            else if (propertyId == contestPropertyContestOverviewTextId)
                contestData.setContestDescriptionAndRequirements(value);
            else if (propertyId == contestPropertySizeRequirementsId)
                contestData.setSizeRequirements(value);
            else if (propertyId == contestPropertyOtherRequirementsId)
                contestData.setOtherRequirementsOrRestrictions(value);
            // [TCCC-284]
            else if (propertyId == contestPropertyRequiresPreviewFileId)
                contestData.setRequiresPreviewFile(Boolean.parseBoolean(value));
            else if (propertyId == contestPropertyRequiresPreviewImageId)
                contestData.setRequiresPreviewImage(Boolean.parseBoolean(value));
            else if (propertyId == contestPropertyMaximumSubmissionsId)
                contestData.setMaximumSubmissions(Long.parseLong(value));
            // [TCCC-283]
            else if (propertyId == contestPropertyEligibilityId)
                contestData.setEligibility(value);
            else if (propertyId == contestPropertyNotesOnWinnerSelectionId)
                contestData.setNotesOnWinnerSelection(value);
            else if (propertyId == contestPropertyPrizeDescriptionId)
                contestData.setPrizeDescription(value);
            // [TCCC-499]
            else if (propertyId == contestPropertyDigitalRunPointsId)
                contestData.setDrPoints(Double.parseDouble(value));
            else if (propertyId == contestPropertyContestAdministrationFeeId)
                contestData.setContestAdministrationFee(Double.parseDouble(value));
        }

        List<UploadedDocument> documents = new ArrayList<UploadedDocument>();
        for (Document doc : contest.getDocuments()) {
            documents.add(convertDocument(doc, false));
        }
        contestData.setDocumentationUploads(documents);

        for (ContestPayload payload : contestData.getContestPayloads()) {
            // ContestPayload.contestTypeId map to
            // Contest.contestType.contestType
            payload.setContestTypeId(contest.getContestType().getContestType());

            // leave the ContestPayload.description field and empty string.
            payload.setDescription("");
        }

        // FIX [TCCC-146]
        List<PrizeData> prizes = new ArrayList<PrizeData>();

        for (Prize prize : contest.getPrizes()) {
            PrizeData prizeData = new PrizeData();
            prizeData.setAmount(prize.getAmount() == null ? 0 : prize.getAmount());
            prizeData.setPlace(unbox(prize.getPlace()));
            prizes.add(prizeData);
        }

        contestData.setPrizes(prizes);

        // TCCC-293
        double durationInHours = (contest.getEndDate().getTime() - contest.getStartDate().getTime()) / (60 * 60 * 1000);
        contestData.setDurationInHours(durationInHours);

        // TCCC-299 Exception when Editing project
        if (contest.getContestChannel() != null) {
            contestData.setContestChannelId(contest.getContestChannel().getContestChannelId());
        }

        List<MediumData> mediums = new ArrayList<MediumData>();

        for (Medium medium : contest.getMedia()) {
            MediumData mediumData = new MediumData();
            mediumData.setDescription(medium.getDescription());
            mediumData.setMediumId(medium.getMediumId());
            mediums.add(mediumData);
        }

        contestData.setMedia(mediums);

        // TCCC-457
        Long forumId = contest.getForumId();
        if (forumId != null) {
            contestData.setForumId(forumId);

            if (loadForumInfo) {
                contestData.setForumPostCount(contestManager.getContestPostCount(forumId));
            }
        }

        return contestData;
    }


    /**
     * This method converts Contest object into ContestData object.
     *
     * @param contest
     *            Contest instance to convert
     * @param Load forum info or not.
     * @return converted ContestDate object
     * @throws ContestManagementException
     *             error occurred from ContestManager
     */
    private ContestData convertContestHeader(Contest contest, boolean loadForumInfo) throws ContestManagementException {
        ContestData contestData = new ContestData();

        contestData.setLaunchDateAndTime(getXMLGregorianCalendar(contest.getStartDate()));

        contestData.setContestId(unbox(contest.getContestId()));
        contestData.setCreatorUserId(unbox(contest.getCreatedUser()));
        contestData.setName(contest.getName());
        contestData.setProjectId(unbox(contest.getProjectId()));
        contestData.setWinnerAnnoucementDeadline(getXMLGregorianCalendar(contest.getWinnerAnnoucementDeadline()));
        contestData.setStatusId(contest.getStatus().getContestStatusId());
        contestData.setNumberOfRegistrants(contest.getContestRegistrations().size());
        contestData.setLaunchImmediately(contest.isLaunchImmediately());

        // [TCCC-585]
        if(contest.getTcDirectProjectId()!=null){
            contestData.setTcDirectProjectId(contest.getTcDirectProjectId());
        }
        // [27074484-20]
        ContestType contestType = contest.getContestType();
        contestData.setContestTypeId(contestType == null ? -1 : unbox(contestType.getContestType()));

        Set<Submission> submissions = contest.getSubmissions();
        if (submissions != null) {
            // [TCCC-369]
            int maxSubmissionsPerUser = 0;
            for (ContestConfig config : contest.getConfig()) {
                if (config.getId().getProperty().getPropertyId() == contestPropertyMaximumSubmissionsId) {
                    try {
                        maxSubmissionsPerUser = Integer.parseInt(config.getValue());
                    } catch (NumberFormatException e) {
                        maxSubmissionsPerUser = 0;
                    }
                    break;
                }
            }

            int submissionsToBeRemoved = 0;

            // Get submissions need to be removed.
            for (Submission submission : submissions) {
                if (maxSubmissionsPerUser > 0 && submission.getRank() != null
                        && submission.getRank() > maxSubmissionsPerUser) {
                    submissionsToBeRemoved++;
                } else if (submission.getStatus() != null
                        && submission.getStatus().getSubmissionStatusId() == submissionRemovedStatusId) {
                    // TCCC-414
                    submissionsToBeRemoved++;
                } else if (submission.getReview() != null) {
                    // TCCC-445
                    for (SubmissionReview review : submission.getReview()) {
                        if (review.getStatus().getReviewStatusId() == reviewFailedStatusId) {
                            submissionsToBeRemoved++;
                            break;
                        }
                    }
                }
            }

            contestData.setSubmissionCount(submissions.size() - submissionsToBeRemoved);
            logDebug(contestData.getSubmissionCount() + " valid submissions found for contest. contest id: "
                    + contest.getContestId() + " ; Total submission: " + submissions.size());
        }

        contestData.setFinalFileFormat("");
        contestData.setShortSummary("");
        contestData.setOtherFileFormats("");
        contestData.setRequiredOrRestrictedColors("");
        contestData.setRequiredOrRestrictedFonts("");
        contestData.setContestDescriptionAndRequirements("");
        contestData.setSizeRequirements("");
        contestData.setOtherRequirementsOrRestrictions("");
        contestData.setRequiresPreviewFile(false);
        contestData.setRequiresPreviewImage(false);
        contestData.setMaximumSubmissions(0);
        contestData.setEligibility("");
        contestData.setNotesOnWinnerSelection("");
        contestData.setPrizeDescription("");
        contestData.setDrPoints(0);
        contestData.setContestAdministrationFee(0);

        contestData.setDocumentationUploads(new ArrayList<UploadedDocument>());

        contestData.setPrizes(new ArrayList<PrizeData>());

        // TCCC-293
        double durationInHours = (contest.getEndDate().getTime() - contest.getStartDate().getTime()) / (60 * 60 * 1000);
        contestData.setDurationInHours(durationInHours);

        // TCCC-299 Exception when Editing project
        if (contest.getContestChannel() != null) {
            contestData.setContestChannelId(contest.getContestChannel().getContestChannelId());
        }
        List<MediumData> mediums = new ArrayList<MediumData>();
        contestData.setMedia(mediums);
        // TCCC-457
        Long forumId = contest.getForumId();
        if (forumId != null) {
            contestData.setForumId(forumId);

            if (loadForumInfo) {
                contestData.setForumPostCount(contestManager.getContestPostCount(forumId));
            }
        }

        return contestData;
    }

    /**
     * Converts Document object into UploadedDocument instance. Simply sets
     * basic attributes and retrieves document data from contestManager. As
     * required by designer, errors are suppressed.
     *
     * @param from
     *            Document to convert
     * @param setDocumentContent
     *            If true, load document content.
     * @return converted UploadedDocument object
     */
    private UploadedDocument convertDocument(Document from, boolean setDocumentContent) {
        UploadedDocument to = new UploadedDocument();

        to.setDocumentId(unbox(from.getDocumentId()));
        to.setFileName(from.getOriginalFileName());
        to.setDescription(from.getDescription());
        if (from.getPath() != null) {
            to.setPath(from.getPath().getPath());
        }
        if (from.getMimeType() != null) {
            to.setMimeTypeId(unbox(from.getMimeType().getMimeTypeId()));
        }
        if (from.getType() != null) {
            to.setDocumentTypeId(unbox(from.getType().getDocumentTypeId()));
        }

        if (setDocumentContent) {
            try {
                to.setFile(contestManager.getDocumentContent(to.getDocumentId()));
            } catch (ContestManagementException e) {
                // no need to propagate exception
                logError(e, "ContestManager reports error during getDocumentContent call.");
            }
        }

        return to;
    }

   /**
    * Converts Document object into UploadedDocument instance. Simply sets
    * basic attributes and retrieves document data from contestManager. As
    * required by designer, errors are suppressed.
    *
    * @param from
    *            Document to convert
    * @return converted UploadedDocument object
    */
    private UploadedDocument convertDocument(Document from) {
        return convertDocument(from, true);
    }

    /**
     * Converts list of Contest objects into list of ContestData objects,
     * calling convertContest repeatly.
     *
     * @param contests
     *            list of contests to convert
     * @return converted list of contests
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    private List<ContestData> convertContests(List<Contest> contests) throws ContestManagementException {
        List<ContestData> result = new ArrayList<ContestData>(contests.size());
        for (Contest contest : contests) {
            result.add(convertContest(contest));
        }
        return result;
    }


    /**
     * Converts standard java Date object into XMLGregorianCalendar instance.
     * Returns null if parameter is null.
     *
     * @param date
     *            Date object to convert
     * @return converted calendar instance
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
            // can't create calendar, return null
            return null;
        }
    }

    /**
     * Converts XMLGregorianCalendar date into standard java Date object.
     * Returns null if argument is null.
     *
     * @param calendar
     *            calendar instance to convert
     * @return converted Date instance
     */
    private Date getDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }
        return calendar.toGregorianCalendar().getTime();
    }

    /**
     * Simple routine used to add ContestConfig object to the contest.
     *
     * @param contest
     *            contest to add config to
     * @param contestPropertyId
     *            Contest Property Id of the property
     * @param value
     *            value to add
     * @throws ContestManagementException
     */
    private void addContestConfig(Contest contest, long contestPropertyId, String value)
            throws ContestManagementException {
        if (value == null) {
            return;
        }
        ContestProperty property = contestManager.getContestProperty(contestPropertyId);

        ContestConfig.Identifier id = new ContestConfig.Identifier();
        id.setContest(contest);
        id.setProperty(property);

        ContestConfig config = new ContestConfig();
        config.setId(id);
        config.setValue(value);

        contest.getConfig().add(config);
    }

    /**
     * This method used to upload document in persistence. It will save both
     * document's data and it's body (byte[] contents).
     *
     * @param data
     *            document to upload
     * @param contest
     *            contest to which document belongs
     * @return uploaded document
     * @throws PersistenceException
     *             if ContestManager reports any error
     */
    private UploadedDocument uploadDocument(UploadedDocument data, Contest contest) throws PersistenceException {
        Document doc = new Document();

        doc.setDocumentId(data.getDocumentId());
        doc.setOriginalFileName(data.getFileName());

        doc.getContests().add(contest);

        // set file path
        FilePath fp = new FilePath();

        fp.setPath(documentBasePath);

        doc.setPath(fp);

        try {

            // set mime type [BUG 27074484-15]
            MimeType mt = contestManager.getMimeType(data.getMimeTypeId());
            if (mt == null) {
                throw new PersistenceException("Mime Type Id not found in the database: " + data.getMimeTypeId(), "");
            }
            doc.setMimeType(mt);

            // set document type [BUG 27074484-15]
            DocumentType dt = contestManager.getDocumentType(data.getDocumentTypeId());
            if (dt == null) {
                throw new PersistenceException("Document Type Id not found in the database: "
                        + data.getDocumentTypeId(), "");
            }
            doc.setType(dt);
            doc.setDescription(data.getDescription());

            // save document
            doc = contestManager.addDocument(doc);
            contestManager.addDocumentToContest(unbox(doc.getDocumentId()), unbox(contest.getContestId()));
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while adding document in persistence.", e);
        }

        // persist document content where necessary
        try {
            if (!contestManager.existDocumentContent(unbox(doc.getDocumentId()))) {
                contestManager.saveDocumentContent(doc.getDocumentId(), data.getFile());
            }
        } catch (IllegalArgumentException e) {
            // do not reproduce any errors here
            logError(e);
        } catch (ContestManagementException e) {
            logError(e);
        }

        // return uploaded document
        return convertDocument(doc);
    }

    /**
     * This methid converts list of Submission objects into list of
     * SubmissionData objects. It's logic is derived from CS and designer's
     * comments.
     *
     * @param submissions
     *            list of submissions to convert
     * @return converted list of SubmissionData entities
     * @throws PersistenceException
     *             if SubmissionManager reports error
     */
    private List<SubmissionData> convertSubmissions(List<Submission> submissions) throws PersistenceException {
        List<SubmissionData> result = new ArrayList<SubmissionData>();
        for (Submission s : submissions) {
            SubmissionData sd = new SubmissionData();
            if (s.getContest() != null) {
                sd.setContestId(unbox(s.getContest().getContestId()));
            }

            // Set rank.
            sd.setRank(s.getRank());
            sd.setSubmissionId(unbox(s.getSubmissionId()));
            sd.setSubmittedDate(getXMLGregorianCalendar(s.getSubmissionDate()));
            sd.setSubmitterId(unbox(s.getSubmitterId()));

            // [TCCC-411]
            sd.setRemoved(unbox(s.getStatus().getSubmissionStatusId()) == submissionRemovedStatusId);

            // compute price
            double prizeAmount = 0;
            for (Prize p : s.getPrizes()) {
                prizeAmount += p.getAmount();

                if (p.getPlace() != null && p.getPlace() > 0) {
                    sd.setPlacement(p.getPlace());
                }
            }
            sd.setPrice(prizeAmount);

            // retrieve SubmissionPayment and check it
            SubmissionPayment p = null;
            try {
                p = submissionManager.getSubmissionPayment(sd.getSubmissionId());
            } catch (SubmissionManagementException e) {
                handlePersistenceError("SubmissionManager reports error while retrieving payment for submission.", e);
            }
            if (p != null) {
                PaymentStatus ps = p.getStatus();
                if (ps != null) {
                    sd.setPaidFor(ps.getPaymentStatusId() == submissionPaidStatusId);
                    sd.setMarkedForPurchase(ps.getPaymentStatusId() == submissionMarkedForPurchaseStatusId);
                }
            }
            if (s.getStatus() != null) {
                // TCCC-445
                sd.setPassedScreening(true);
                for (SubmissionReview review : s.getReview()) {
                    if (review.getStatus().getReviewStatusId() == reviewFailedStatusId) {
                        sd.setPassedScreening(false);
                        break;
                    }
                }
            }

            // create content
            String content = submissionContentBaseURI;
            content += "&" + submissionContentSubmissionIdParameterName + "=" + sd.getSubmissionId();
            content += "&" + submissionContentSubmissionTypeParameterName + "="
                    + (sd.isPaidFor() ? submissionContentPaidParameterValue : submissionContentUnpaidParameterValue);
            sd.setSubmissionContent(content);

            result.add(sd);
        }

        return result;
    }

    /**
     * This simple routing used to convert SubmissionData object into
     * Submission.
     *
     * @param submissionData
     *            entity to convert
     * @return converted entity
     */
    private Submission convertSubmissionData(SubmissionData submissionData) {
        Submission submission = new Submission();

        submission.setSubmissionId(submissionData.getSubmissionId());
        submission.setSubmitterId(submissionData.getSubmitterId());
        submission.setSubmissionDate(getDate(submissionData.getSubmittedDate()));
        submission.setRank(submissionData.getRank());

        return submission;
    }

    /**
     * Method used to create new error regarding to persistence fault in
     * managers. It will log necessary information, create appropriate exception
     * and throw it. In fact, method processes all types of errors except of
     * special (processed by other handlers or in main code).
     *
     * @param message
     *            string describing error
     * @param cause
     *            error cause, if any
     * @throws PersistenceException
     *             always
     */
    private void handlePersistenceError(String message, Throwable cause) throws PersistenceException {
        logError(cause, message);
        PersistenceFault faultBean = new PersistenceFault();
        faultBean.setPersistenceMessage(cause.getMessage());
        throw new PersistenceException(message, faultBean, cause);
    }

    /**
     * Method used to create new error regarding to failed authorization. It
     * will log necessary information, create appropriate exception and throw
     * it. This method will not use used id, related type of error will be
     * processed in other place.
     *
     * @param message
     *            string describing error
     * @throws UserNotAuthorizedException
     *             always
     */
    private void handleAuthorizationError(String message) {
        logError(message);
        UserNotAuthorizedFault faultBean = new UserNotAuthorizedFault();
        faultBean.setUserIdNotAuthorized(-1);
        throw new UserNotAuthorizedException(message, faultBean);
    }

    /**
     * Method used to create new error regarding to wrong method parameter. It
     * will log necessary information, create appropriate exception and throw
     * it.
     *
     * @param message
     *            string describing error
     * @throws IllegalArgumentWSException
     *             always
     */
    private void handleIllegalWSArgument(String message) {
        logError(message);
        IllegalArgumentWSFault faultBean = new IllegalArgumentWSFault();
        faultBean.setIllegalArgumentMessage(message);
        throw new IllegalArgumentWSException(message, faultBean);
    }

    /**
     * Method used to create new error regarding to wrong contest id. It will
     * log necessary information, create appropriate exception and throw it.
     *
     * @param message
     *            string describing error
     * @param cause
     *            error cause, if any
     * @throws StudioServiceException
     *             always
     */
    private void handleError(String message, Throwable cause) throws StudioServiceException {
        logError(cause, message);
        PersistenceFault faultBean = new PersistenceFault();
        faultBean.setPersistenceMessage(cause.getMessage());
        throw new StudioServiceException(message, cause);
    }

    /**
     * Method used to create new error regarding to wrong contest id. It will
     * log necessary information, create appropriate exception and throw it.
     *
     * @param cause
     *            error cause, if any
     * @param id
     *            id of the missed contest
     * @throws ContestNotFoundException
     *             always
     */
    private void handleContestNotFoundError(Throwable cause, long id) throws ContestNotFoundException {
        String message = "No contest with id " + id + ".";
        logError(cause, message);
        ContestNotFoundFault fault = new ContestNotFoundFault();
        fault.setContestIdNotFound(id);
        throw new ContestNotFoundException(message, fault, cause);
    }

    /**
     * <p>
     * This method used to log enter in method. It will persist both method name
     * and it's parameters if any.
     * </p>
     *
     * @param method
     *            name of the entered method
     * @param params
     *            array containing parameters used to invoke method
     */
    private void logEnter(String method, Object... params) {
        if (log != null) {
            log.log(Level.DEBUG, "Enter method StudioServiceBean.{0} with parameters {1}.", method, Arrays
                    .deepToString(params));
        }
    }

    private void logDebug(String msg) {
        if (log != null) {
            log.log(Level.DEBUG, msg);
        }
    }

    /**
     * <p>
     * This method used to log leave of method. It will persist method name.
     * </p>
     *
     * @param method
     *            name of the leaved method
     */
    private void logExit(String method) {
        if (log != null) {
            log.log(Level.DEBUG, "Leave method {0}.", method);
        }
    }

    /**
     * <p>
     * This method used to log leave of method. It will persist method name.
     * </p>
     *
     * @param method
     *            name of the leaved method
     * @param returnValue
     *            value returned from the method
     */
    private void logExit(String method, Object returnValue) {
        if (log != null) {
            log.log(Level.DEBUG, "Leave method {0} with return value {1}.", method, returnValue);
        }
    }

    /**
     * <p>
     * This method used to log arbitrary error. It will persist error's data.
     * </p>
     *
     * @param error
     *            exception describing error
     */
    private void logError(Throwable error) {
        if (log != null) {
            log.log(Level.ERROR, error, "Error recognized.");
        }
    }

    /**
     * <p>
     * This method used to log arbitrary error. It will persist error's data.
     * </p>
     *
     * @param error
     *            exception describing error
     * @param message
     *            additional message information
     */
    private void logError(Throwable error, String message) {
        if (error == null) {
            logError(message);
        }
        if (log != null) {
            log.log(Level.ERROR, error, message);
        }
    }

    /**
     * <p>
     * This method used to log arbitrary error. It will persist error's data.
     * </p>
     *
     * @param message
     *            additional message information
     */
    private void logError(String message) {
        if (log != null) {
            log.log(Level.ERROR, message);
        }
    }

    /**
     * Simple helper to unbox Long value to long. If parameter is null, return
     * -1.
     *
     * @param value
     *            Long value to unbox.
     * @return unboxed long value
     */
    private static long unbox(Long value) {
        if (value == null) {
            return -1;
        } else {
            return value;
        }
    }

    /**
     * Simple helper to unbox Integer value to int. If parameter is null, return
     * -1.
     *
     * @param value
     *            Integer value to unbox.
     * @return unboxed int value
     */
    private static int unbox(Integer value) {
        if (value == null) {
            return -1;
        } else {
            return value;
        }
    }

    /**
     * Checks web service parameter for obeying given rules (objects are not
     * null and integers are not negative). Throws exception otherwise.
     *
     * @param name
     *            name of the parameter to check
     * @param data
     *            parameter itself
     * @throws IllegalArgumentWSException
     *             when data parameter is null or (data is Long and negative)
     */
    private void checkParameter(String name, Object data) {
        if (data == null) {
            handleIllegalWSArgument("Parameter " + name + " not supposed to be null.");
        }
        if (data instanceof Long) {
            if ((Long) data < 0) {
                handleIllegalWSArgument("Parameter " + name + " not supposed to be negative.");
            }
        }
    }

    /**
     * Simple routine to check whether user is admin. Simple user will not be
     * authorized.
     *
     * @throws UserNotAuthorizedException
     *             if access was denied
     */
    private void authorizeAdmin() {
        if (!sessionContext.isCallerInRole(ADMIN_ROLE)) {
            handleAuthorizationError("Only administrator can perform this action.");
        }
    }

    /**
     * Implements standard algorithm of authorization based on fetching client
     * id from contest id. Only admin and client may pass.
     *
     * @param id
     *            contest id to identify client (if necessary)
     * @throws PersistenceException
     *             when ContestManager reports some error
     * @throws UserNotAuthorizedException
     *             if access was denied
     */
    private Contest authorizeWithContest(long id) throws PersistenceException, ContestNotFoundException {

        try {
            Contest contest = contestManager.getContest(id);

            if (contest == null) {
                handleContestNotFoundError(null, id);
            }

            // Admin is always authorized
            if (sessionContext.isCallerInRole(ADMIN_ROLE)) {
                return contest;
            }

            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            long userId = p.getUserId();

            if (contest.getCreatedUser() != userId) {
                throw new UserNotAuthorizedException("Access denied for the contest " + id, userId);
            }

            return contest;

        } catch (ContestManagementException e) {
            handlePersistenceError("Error when trying to get the the contest", e);
            return null;
        }
    }

    /**
     * <p>
     * This is going to fetch all the currently available contests.
     * </p>
     *
     * @return the list of all available contents (or empty if none found)
     *
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
    public List<ContestData> getAllContests() throws PersistenceException {
        logEnter("getAllContests");

        try {
            List<ContestData> result = new ArrayList<ContestData>();
            List<Contest> contests;
            if (sessionContext.isCallerInRole(ADMIN_ROLE)) {
                logError("User is admin.");
                contests = contestManager.getAllContests();
            } else {
                UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
                logError("User " + p.getUserId() + " is non-admin.");
                contests = contestManager.getContestsForUser(p.getUserId());
            }

            List<Long> forumIds = new ArrayList<Long>();
            for (Contest contest : contests) {
                ContestData convertContest = convertContest(contest, false);
                result.add(convertContest);

                if (contest.getForumId() != null) {
                    forumIds.add(contest.getForumId());
                }
            }

            Map<Long, Long> contestPostCountMap = contestManager.getContestPostCount(forumIds);
            for (ContestData contest : result) {
                Long count = contestPostCountMap.get(contest.getForumId());
                if (count != null) {
                    contest.setForumPostCount(count.intValue());
                } else {
                	contest.setForumPostCount(0);
                }
            }

            logExit("getAllContests", result);
            return result;
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while retrieving contest.", e);
        }

        return null;
    }

    /**
     * <p>
     * This is going to get all the matching contest entities that fulfill the
     * input criteria.
     * </p>
     *
     * @param filter
     *            a search filter used as criteria for contests.
     * @return a list (possibly empty) of all the matched contest entities.
     *
     * @throws IllegalArgumentException
     *             if the input filter is null or filter is not supported for
     *             searching
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
    public List<ContestData> searchContests(Filter filter) throws PersistenceException {
        logEnter("searchContests");

        try {
            List<Contest> contests = contestManager.searchContests(filter);

            ArrayList<ContestData> result = new ArrayList<ContestData>();
            for (Contest contest : contests) {
                result.add(convertContest(contest));
            }

            logExit("searchContests", result);
            return result;
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while retrieving contest.", e);
        }

        return null;
    }

    /**
     * Returns all contests for a particular client ID, provided as a long
     *
     * @param clientId
     *            client Id to search for.
     * @return all contests for the given client ID.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
    public List<ContestData> getContestsForClient(long clientId) throws PersistenceException {
        logEnter("getContestsForClient");

        try {
            List<Contest> contests = contestManager.getAllContests();

            ArrayList<ContestData> result = new ArrayList<ContestData>();
            for (Contest contest : contests) {
                if (clientId == contestManager.getClientForContest(contest.getContestId())) {
                    result.add(convertContest(contest));
                }
            }

            logExit("getContestsForClient", result);
            return result;
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while retrieving contest.", e);
        }

        return null;
    }

    /**
     * <p>
     * Gets the submission with the given id.
     * </p>
     *
     * @param submissionId
     *            The id of the submission to get
     * @return the submission with the given id, or null if not found
     * @throws PersistenceException
     *             If any error occurs during the retrieval
     */
    public SubmissionData retrieveSubmission(long submissionId) throws PersistenceException {
        logEnter("retrieveSubmission");

        try {
            Submission submission = submissionManager.getSubmission(submissionId);

            ArrayList<Submission> submissions = new ArrayList<Submission>();
            submissions.add(submission);
            SubmissionData result = convertSubmissions(submissions).get(0);

            logExit("retrieveSubmission", result);
            return result;
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManager reports error while retrieving submission.", e);
        }

        return null;
    }

    /**
     * <p>
     * This is going to fetch all the currently available contest types.
     * </p>
     *
     * @return the list of all available content types (or empty if none found)
     *
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
    public List<ContestTypeData> getAllContestTypes() throws PersistenceException {
        logEnter("getAllContestTypes");
        try {
            ArrayList<ContestTypeData> result = new ArrayList<ContestTypeData>();
            for (ContestType type : contestManager.getAllContestTypes()) {
                ContestTypeData data = new ContestTypeData();
                data.setDescription(type.getDescription());
                data.setContestTypeId(type.getContestType());
                data.setRequirePreviewFile(type.isRequirePreviewFile());
                data.setRequirePreviewImage(type.isRequirePreviewImage());

                // Each of the ContestTypeConfigs in the ContestType retrieved
                // from the Studio Contest Manager should be used to create the
                // list of ContestPayload instances.
                ArrayList<ContestPayload> payloads = new ArrayList<ContestPayload>();
                for (ContestTypeConfig cfg : type.getConfig()) {
                    ContestPayload payload = new ContestPayload();

                    payload.setContestTypeId(cfg.getId().getContestType().getContestType());
                    payload.setDescription(cfg.getId().getProperty().getDescription());
                    payload.setValue(cfg.getPropertyValue());
                    payload.setRequired(cfg.isRequired());
                    payload.setName(cfg.getId().getProperty().getName());

                    payloads.add(payload);
                }
                data.setConfig(payloads);

                result.add(data);
            }

            logExit("getAllContestTypes", result);
            return result;
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManagement reports error while retrieving ContestTypes.", e);
        }

        return null;
    }

    /**
     * <p>
     * Links a document to a contest.
     *
     * @param documentId
     *            document id, which document will be linked.
     * @param contestId
     *            cpntest id, which contest will be linked.
     * @throws IllegalArgumentWSException
     *             if any id is less than 0
     * @throws PersistenceException
     *             if some persistence errors occur
     * @throws ContestNotFoundException
     *             if the contest is not found.
     */
    public void addDocumentToContest(long documentId, long contestId) throws PersistenceException,
            ContestNotFoundException {
        logEnter("addDocumentToContest");

        checkParameter("contestId", contestId);
        checkParameter("documentId", documentId);

        try {
            Contest contest = contestManager.getContest(contestId);
            if (contest == null) {
                handleContestNotFoundError(null, contestId);
            }

            if (contestManager.getDocument(documentId) == null) {
                handlePersistenceError("Document " + documentId + "is missing.", null);
            }

            contestManager.addDocumentToContest(documentId, contestId);
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while linking document to contest in persistence.", e);
        }
        logExit("addDocumentToContest", null);
    }

    /**
     * <p>
     * Upload document. Return document populated with id.
     *
     * @param uploadedDocument
     *            the uploadDocument to update
     * @return the same instance passed in argument with the documentId updated
     * @throws IllegalArgumentWSException
     *             if the argument is null
     * @throws PersistenceException
     *             if some persistence errors occur
     */
    public UploadedDocument uploadDocument(UploadedDocument data) throws PersistenceException {
        logEnter("uploadDocument(UploadedDocument data)");

        checkParameter("data", data);

        Document doc = new Document();

        doc.setDocumentId(data.getDocumentId());
        doc.setOriginalFileName(data.getFileName());

        // set file path
        FilePath fp = new FilePath();

        // [BUG TCCC-134]
        fp.setPath(documentBasePath);

        doc.setPath(fp);

        try {

            // set mime type [BUG 27074484-15]
            MimeType mt = contestManager.getMimeType(data.getMimeTypeId());
            doc.setMimeType(mt);

            // set document type [BUG 27074484-15]
            DocumentType dt = contestManager.getDocumentType(data.getDocumentTypeId());
            doc.setType(dt);
            doc.setDescription(data.getDescription());

            // save document
            doc = contestManager.addDocument(doc);
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while adding document in persistence.", e);
        }
        // persist document content where necessary
        try {
            if (!contestManager.existDocumentContent(unbox(doc.getDocumentId()))) {
                contestManager.saveDocumentContent(doc.getDocumentId(), data.getFile());
            }
        } catch (IllegalArgumentException e) {
            // do not reproduce any errors here
            logError(e);
        } catch (ContestManagementException e) {
            logError(e);
        }

        UploadedDocument ret = convertDocument(doc);
        logExit("uploadDocument(UploadedDocument data)", ret);
        // return uploaded document
        return ret;
    }

    /**
     * <p>
     * Remove document
     *
     * @param documentId
     *            the id of document to remove
     * @throws IllegalArgumentWSException
     *             if the documentId is less than 0
     * @throws PersistenceException
     *             if some persistence errors occur
     */
    public boolean removeDocument(long documentId) throws PersistenceException {
        logEnter("removeDocument", documentId);
        checkParameter("documentId", documentId);

        try {
            boolean ret = contestManager.removeDocument(documentId);
            logExit("removeDocument");
            return ret;
        } catch (ContestManagementException e) {
            handlePersistenceError("contestManager reports error while removing document.", e);
        }

        // never happen.
        return false;
    }

    /**
     * <p>
     * Get matched the MimeType id.
     * </p>
     *
     * @param ContentType
     *            .
     * @return the matched MimeType id. -1 if not found.
     *
     * @throws PersistenceException
     *             if any error occurs when getting MimeType.
     *
     * @throws IllegalArgumentWSException
     *             if the argument is null or empty
     */
    public long getMimeTypeId(String contentType) throws PersistenceException {
        logEnter("getMimeTypeId", contentType);
        checkParameter("contentType", contentType);
        try {
            List<MimeType> allMimeTypes = contestManager.getAllMimeTypes();
            long ret = -1;
            for (MimeType mime : allMimeTypes) {
                if (mime.getDescription().equals(contentType)) {
                    ret = mime.getMimeTypeId();
                    break;
                }
            }

            logExit("getMimeTypeId");
            return ret;
        } catch (ContestManagementException e) {
            handlePersistenceError("contestManager reports error while getAllMimeTypes.", e);
        }

        // never happen.
        return -1;
    }

    /**
     * <p>
     * Purchase submission.
     * </p>
     * <p>
     * set the price of submission. create an entry at submission payment table
     * </p>
     *
     * @param submissionId
     *            the id of submission to purchase.
     * @param payPalOrderId
     *            PayPal order id.
     * @param username
     *            the username.
     *
     * @throws PersistenceException
     *             if any error occurs when purchasing submission.
     * @throws IllegalArgumentWSException
     *             if the submissionId is less than 0 or price is negative.
     */
    public void purchaseSubmission(long submissionId, String payPalOrderId, String username)
            throws PersistenceException {
        logEnter("purchaseSubmission", submissionId, payPalOrderId);
        checkParameter("submissionId", submissionId);
        checkParameter("payPalOrderId", payPalOrderId);
        checkParameter("username", username);

        try {
            Submission submission = submissionManager.getSubmission(submissionId);
            // get the contest that the submission belongs to
            if (submission == null) {
                handleIllegalWSArgument("Submission with id " + submissionId + " is not found.");
            }

            Contest contest = submission.getContest();

//            authorizeWithUsername(username, contest);

            // There must be a payment for the submission in Marked for Purchase
            // (3) status.
            // Otherwise (no payment or another status), throw exception.
            SubmissionPayment submissionPayment = submissionManager.getSubmissionPayment(submissionId);
            if (submissionPayment == null
                    || submissionPayment.getStatus().getPaymentStatusId() != submissionMarkedForPurchaseStatusId) {
                throw new SubmissionManagementException(
                        "There must be a payment for the submission in Marked for Purchase (3) status. Submission id: "
                                + submissionId);
            }

            // get the first place prize amount for the contest
            Double firstPlacePrize = null;

            for (Prize prize : contestManager.getContestPrizes(contest.getContestId())) {
                if (prize.getPlace() != null && prize.getPlace().equals(1)) {
                    firstPlacePrize = prize.getAmount();
                    break;
                }
            }

            if (firstPlacePrize == null) {
                throw new ContestManagementException(
                        "There must be a first placement prize for the contest. Contest id: " + contest.getContestId());
            }

            double amount = firstPlacePrize;

            // check if the submission has a winner prize
            boolean isWinner = false;
            for (Prize p : submission.getPrizes()) {
                if (p.getType().getPrizeTypeId() == contestPrizeTypeId && p.getAmount() > 0) {
                    isWinner = true;
                    break;
                }
            }

            // if it doesn't have a winner prize, it's an additional purchase.
            if (!isWinner) {
                // the amount of the additional submission is a fraction of the
                // first place prize.
                amount = firstPlacePrize * additionalSubmissionPurchasePriceRatio;

                Prize prize = null;

                // If the contest has a prize with null place and prize_type_id
                // = 2, that prize will be used.
                for (Prize p : contestManager.getContestPrizes(contest.getContestId())) {
                    if (p.getPlace() == null && p.getType().getPrizeTypeId() == clientSelectionPrizeTypeId) {
                        prize = p;
                        break;
                    }
                }
                if (prize == null) {
                    // Create prize.
                    prize = new Prize();
                    prize.setAmount(amount);
                    prize.setPlace(null);
                    prize.setType(contestManager.getPrizeType(clientSelectionPrizeTypeId));

                    prize = submissionManager.addPrize(prize);
                    contestManager.addPrizeToContest(contest.getContestId(), prize.getPrizeId());
                }

                submissionManager.addPrizeToSubmission(submissionId, prize.getPrizeId());
            }

            submissionPayment.setPrice(amount);
            submissionPayment.setPayPalOrderId(payPalOrderId);
            PaymentStatus status = contestManager.getPaymentStatus(submissionPaidStatusId);
            if (status == null) {
                throw new ContestManagementException("PaymentStatus with id " + submissionPaidStatusId + " is missing.");
            }
            submissionPayment.setStatus(status);
            submissionManager.updateSubmissionPayment(submissionPayment);
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManager reports error.", e);
        } catch (ContestManagementException e) {
            handlePersistenceError("contestManager reports error.", e);
        }
        logExit("purchaseSubmission");
    }

    /**
     * <p>
     * Creates a new contest payment and returns the created contest payment.
     * </p>
     *
     * @param contestPaymentData
     *            the contest payment to create
     * @param username
     *            username.
     * @return the created contest payment.
     *
     * @throws IllegalArgumentException
     *             if the arg is null.
     * @throws PersistenceException
     *             if any other error occurs.
     */
    public ContestPaymentData createContestPayment(ContestPaymentData contestPaymentData, String username)
            throws PersistenceException {
        logEnter("createContestPayment", contestPaymentData);
        checkParameter("contestPaymentData", contestPaymentData);
        checkParameter("username", username);

        try {
            //Contest contest = authorizeWithContest(contestPaymentData.getContestId());
            //authorizeWithUsername(username, contest);

            ContestPayment contestPayment = convertContestPaymentData(contestPaymentData);
            contestPayment = contestManager.createContestPayment(contestPayment);
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while creating new ContestPayment.", e);
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManager reports error while creating new ContestPayment.", e);
//        } catch (ContestNotFoundException e) {
  //          handlePersistenceError("Contest not found when trying to remove document from contest", e);
        }

        logExit("createContestPayment", contestPaymentData);
        return contestPaymentData;
    }

    /**
     * <p>
     * Gets contest payment by id, and return the retrieved contest payment. If
     * the contest payment doesn't exist, null is returned.
     * </p>
     *
     * @param contestPaymentId
     *            the contest payment id
     * @return the retrieved contest, or null if id doesn't exist
     *
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
    public ContestPaymentData getContestPayment(long contestPaymentId) throws PersistenceException {
        logEnter("getContestPayment", contestPaymentId);
        checkParameter("contestPaymentId", contestPaymentId);

        try {
            ContestPayment contestPayment = contestManager.getContestPayment(contestPaymentId);
            if (contestPayment == null) {
                throw new EntityNotFoundException("ContestPayment with id " + contestPaymentId + " is not found.");
            }
            // authorization
            authorizeWithContest(contestPayment.getContestId());
            if (contestPayment == null) {
                // handleContestNotFoundError(null, contestPaymentId);
            }

            ContestPaymentData result = convertContestPayment(contestPayment);
            logExit("getContestPayment", result);
            return result;
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while retrieving contest payment.", e);
        } catch (ContestNotFoundException e) {
            handlePersistenceError("Contest not found when trying to remove document from contest", e);
        }

        // never reached
        return null;
    }

    /**
     * <p>
     * Updates contest payment data.
     * </p>
     *
     * @param contestPayment
     *            the contest payment to update
     * @throws IllegalArgumentException
     *             if the argument is null.
     * @throws EntityNotFoundException
     *             if the contest payment doesn't exist in persistence.
     * @throws ContestManagementException
     *             if any error occurs when updating contest payment.
     */
    public void editContestPayment(ContestPaymentData contestPayment) throws PersistenceException {
        logEnter("editContestPayment", contestPayment);
        checkParameter("contestPayment", contestPayment);

        // authorization

        try {
            authorizeWithContest(contestPayment.getContestId());

            contestManager.editContestPayment(convertContestPaymentData(contestPayment));

        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while updating contest payment.", e);
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManager reports error while updating contest payment.", e);
        } catch (ContestNotFoundException e) {
            handlePersistenceError("Contest not found when trying to remove document from contest", e);
        }

        logExit("editContestPayment");
    }

    /**
     * <p>
     * Removes contest payment, return true if the contest payment exists and
     * removed successfully, return false if it doesn't exist.
     * </p>
     *
     * @param contestPaymentId
     *            the contest payment id
     * @return true if the contest payment exists and removed successfully,
     *         return false if it doesn't exist
     * @throws PersistenceException
     *             if any error occurs.
     */
    public boolean removeContestPayment(long contestPaymentId) throws PersistenceException {
        logEnter("removeContestPayment", contestPaymentId);
        checkParameter("contestPaymentId", contestPaymentId);

        // authorization
        // authorizeWithContest(document.getContestId());

        try {
            boolean ret = contestManager.removeContestPayment(contestPaymentId);
            logExit("removeContestPayment");
            return ret;
        } catch (EntityNotFoundException ex) {
            // no such document in persistence
            String message = "can't find contest payment with id " + contestPaymentId + " in persistence.";
            logError(ex, message);
            DocumentNotFoundFault fault = new DocumentNotFoundFault();
            // fault.setDocumentIdNotFound(document.getDocumentId());
            // throw new DocumentNotFoundException(message, fault, ex);

        } catch (ContestManagementException ex) {
            handlePersistenceError("ContestManager reports error while removing document from contest.", ex);
        }

        return false;
    }

    /**
     * This method used to convert ContestPaymentData object into ContestPayment
     * object.
     *
     * @param data
     *            ContestPaymentData object to convert.
     * @return converted ContestPayment instance
     * @throws PersistenceException
     *             when error reported by manager
     * @throws ContestManagementException
     *             when error reported by manager
     * @throws SubmissionManagementException
     *             when error reported by manager
     */
    private ContestPayment convertContestPaymentData(ContestPaymentData data) throws PersistenceException,
            ContestManagementException, SubmissionManagementException {
        ContestPayment result = new ContestPayment();
        result.setContestId(data.getContestId());
        result.setPayPalOrderId(data.getPaypalOrderId());
        result.setPrice(data.getPrice());
        result.setCreateDate(data.getCreateDate());
        PaymentStatus status = submissionManager.getPaymentStatus(data.getPaymentStatusId());
        result.setStatus(status);

        return result;
    }

    /**
     * This method converts ContestPayment object into ContestPaymentData
     * object.
     *
     * @param contest
     *            ContestPayment instance to convert
     * @return converted ContestPaymentDate object
     * @throws ContestManagementException
     *             error occurred from ContestManager
     */
    private ContestPaymentData convertContestPayment(ContestPayment contestPayment) throws ContestManagementException {
        ContestPaymentData contestPaymentData = new ContestPaymentData();
        contestPaymentData.setContestId(contestPayment.getContestId());
        contestPaymentData.setPaymentStatusId(contestPayment.getStatus().getPaymentStatusId());
        contestPaymentData.setPaypalOrderId(contestPayment.getPayPalOrderId());
        contestPaymentData.setPrice(contestPayment.getPrice());

        return contestPaymentData;
    }

    /**
     * <p>
     * This is going to fetch all the currently available media.
     * </p>
     *
     * @return the list of all available mediums (or empty if none found)
     *
     * @throws PersistenceException
     *             if any error occurs when getting medium.
     */
    public List<MediumData> getAllMediums() throws PersistenceException {
        logEnter("getAllMediums");
        try {
            ArrayList<MediumData> result = new ArrayList<MediumData>();
            for (Medium medium : contestManager.getAllMedia()) {
                MediumData data = new MediumData();
                data.setDescription(medium.getDescription());
                data.setMediumId(medium.getMediumId());

                result.add(data);
            }

            logExit("getAllMediums", result);
            return result;
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManagement reports error while retrieving Mediums.", e);
        }

        return null;
    }

    /**
     * Set submission placement.
     *
     * @param submissionId
     *            Submission Id.
     * @param placement
     *            placement
     * @throws PersistenceException
     *             if any error occurs when setting placement.
     * @since TCCC-353
     */
    public void setSubmissionPlacement(long submissionId, int placement) throws PersistenceException {
        logEnter("setSubmissionPlacement", submissionId, placement);
        checkParameter("submissionId", submissionId);

        Prize prizeToAdd = new Prize();
        prizeToAdd.setAmount(0d);
        prizeToAdd.setPlace(placement);

        setSubmissionPlacement(submissionId, prizeToAdd);
    }



    /**
     * Set submission placement.
     *
     * @param submissionId
     *            Submission Id.
     * @param prizeId
     *            prize id.
     * @throws PersistenceException
     *             if any error occurs when setting placement.
     * @since BUGR-455
     */
    public void setSubmissionPlacement(long submissionId, long prizeId) throws PersistenceException {
        logEnter("setSubmissionPlacement", submissionId, prizeId);
        checkParameter("submissionId", submissionId);
        checkParameter("prizeId", prizeId);

        try {
            Prize prize = submissionManager.getPrize(prizeId);
            Submission submission = submissionManager.getSubmission(submissionId);
            if (prize == null) {
				String message="Prize not found. prize id: " + prizeId;
                logDebug(message);
                logExit("setSubmissionPlacement");
                throw new PersistenceException(message,message);
            }
            if (submission == null) {
				String message = "submission not found. submission id: " + submissionId;
                logDebug(message);
                logExit("setSubmissionPlacement");
                throw new PersistenceException(message,message);
            }
            if (!prize.getContests().isEmpty()) {
                Long contestIdAssociatedToPrize = prize.getContests().toArray(new Contest[] {})[0]
                        .getContestId();
                Long contestIdAssociatedToSubmission = submission.getContest().getContestId();
                if (!contestIdAssociatedToSubmission.equals(contestIdAssociatedToPrize)) {
                    logDebug("prize is not associated to the same contest as the submission");
                    logExit("setSubmissionPlacement");
                    return;
                }
            }

            // If the prize is already associated to another submission, a
            // PersistenceException must be thrown.
            for (Submission s : prize.getSubmissions()) {
                if (submissionId != s.getSubmissionId()) {
                    logDebug(MessageFormat
                            .format(
                                    "Prize has been associated to another submission. prize id: {0}, another submission id: {1}",
                                    prizeId, s.getSubmissionId()));
                    logExit("setSubmissionPlacement");
                    return;
                }
            }

            setSubmissionPlacement(submissionId, prize);
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManagement reports error.", e);
        }
    }


    /**
     * Set submission placement.
     *
     * @param submissionId submission id.
     * @param prize prize.
     * @throws PersistenceException if any error occurs when setting placement.
     */
    protected void setSubmissionPlacement(long submissionId, Prize prize) throws PersistenceException {
        // Need to create new prize when prizeId is null.
        Long prizeId = prize.getPrizeId();
        try {
            if (prizeId == null) {
                PrizeType prizeType = contestManager.getPrizeType(this.contestPrizeTypeId);
                prize.setType(prizeType);
            }

            Submission submission = submissionManager.getSubmission(submissionId);
            if (submission == null) {
				String message= "submission not found. submission id: " + submissionId;
                logDebug(message);
                logExit("setSubmissionPlacement");
                throw new PersistenceException(message,message);
            }

            // If the submission is already associated to a prize, a
            // PersistenceException must be thrown.
            Set<Prize> prizes = submission.getPrizes();
            if (prizes.size() > 0) {
                Prize prizeToCheck = prizes.toArray(new Prize[] {})[0];
                // if the placement is the same, return (no need to do anything,
                // since the placement is already set)
                if (prizeToCheck.getPlace() != null && prizeToCheck.getPlace().equals(prize.getPlace())) {
                    logDebug("Same placement found in submission. placement: " + prize.getPlace());
                    logExit("setSubmissionPlacement");
                    return;
                } else if (prizeId != null && prizeToCheck.getPrizeId().equals(prizeId)) {
                    logDebug("Same prize found in submission. prize id: " + prizeId);
                    logExit("setSubmissionPlacement");
                    return;
                } else {
                    // otherwise, remove the association (but not the prize)
                    logDebug("Remove prize association from submission.");
                    prizes.remove(prize);
                    submissionManager.updateSubmission(submission);
                }
            }
            // get the contest for the submission
            Contest contest = submission.getContest();
            Long contestId = contest.getContestId();
            List<Prize> contestPrizes = contestManager.getContestPrizes(contestId);

            if (contestManager.findContestResult(submissionId,contestId)==null)
            {
		        // TCCC-425
		        ContestResult cr = new ContestResult();
		        cr.setContest(contest);
		        cr.setPlaced(prize.getPlace());
		        cr.setSubmission(submission);
		
		        // TCCC-484
		        contestManager.createContestResult(cr);
            }

            for (Prize prizeToAdd : contestPrizes) {
                if (prizeToAdd.getPlace() != null && prizeToAdd.getPlace().equals(prize.getPlace())) {
                    logDebug("Same placement found in contest. placement: " + prize.getPlace() + ", contest id:"
                            + contest.getContestId());

                    // Associate with submission.
                    submissionManager.addPrizeToSubmission(submissionId, prizeToAdd.getPrizeId());

                    // For topCoder direct, payment is done automatically
                    // STUDIO-217
                    if ( contest.getContestChannel().getContestChannelId()==2 )
                    {
                    	addPayment(submission,prize);
                    }
                    logExit("setSubmissionPlacement");
                    return;
                }
            }

            // if the contest doesn't have a prize for the placement:
            // create the prize with amount=0 and associate it with the contest.
            if (prizeId == null) {
                prize = contestManager.createPrize(prize);
            }

            contestManager.addPrizeToContest(contestId, prize.getPrizeId());

            // associate the submission with the prize.
            submissionManager.addPrizeToSubmission(submissionId, prize.getPrizeId());

            // For topCoder direct, payment is done automatically
            // STUDIO-217
            if ( contest.getContestChannel().getContestChannelId()==2 )
            {
            	addPayment(submission,prize);
            }
            
            logExit("setSubmissionPlacement");
            return;
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManagement reports error.", e);
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManagement reports error.", e);
        }
    }



    /**
     * Marks submission for purchase.
     *
     * @param submissionId
     *            Submission Id.
     * @throws PersistenceException
     *             if any error occurs when marking for purchase.
     *
     * @since TCCC-353
     */
    public void markForPurchase(long submissionId) throws PersistenceException {
        // create/update a submission payment
        // (submission id is the PK) row with status Marked For Purchase (3),
        // amount=0, paypal order id = null.

        try {
            SubmissionPayment submissionPayment = submissionManager.getSubmissionPayment(submissionId);
            if (submissionPayment != null) {
                submissionPayment.setStatus(submissionManager.getPaymentStatus(submissionMarkedForPurchaseStatusId));
                submissionPayment.setPrice(0d);
                submissionPayment.setPayPalOrderId(null);
                submissionManager.updateSubmissionPayment(submissionPayment);
            } else {
                submissionPayment = new SubmissionPayment();
                submissionPayment.setSubmission(submissionManager.getSubmission(submissionId));
                submissionPayment.setStatus(submissionManager.getPaymentStatus(submissionMarkedForPurchaseStatusId));
                submissionPayment.setPrice(0d);
                submissionPayment.setPayPalOrderId(null);
                submissionManager.addSubmissionPayment(submissionPayment);
            }

            logExit("setSubmissionPlacement");
            return;
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManagement reports error.", e);
        }
    }

    /**
     * Add a change history entity.
     *
     * @param history
     *            Change history entity to be added.
     *
     * @throws PersistenceException
     *             if any other error occurs.
     */
    public void addChangeHistory(List<ChangeHistoryData> history) throws PersistenceException {
        logEnter("addChangeHistory");
        try {
            List<ContestChangeHistory> histories = new ArrayList<ContestChangeHistory>();
            for (int i = 0; i < history.size(); ++i) {
                histories.add(getChangeHistory(history.get(i)));
            }
            contestManager.addChangeHistory(histories);

            logExit("addChangeHistory");
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManagement reports error while adding ChangeHistories.", e);
        }
    }

    /**
     * Returns change history entity list.
     *
     * @param contestId
     *            contest id to search for.
     * @return Change history entities match the contest id.
     * @throws PersistenceException
     *             if any other error occurs.
     */
    public List<ChangeHistoryData> getChangeHistory(long contestId) throws PersistenceException {
        logEnter("getChangeHistory");
        try {
            ArrayList<ChangeHistoryData> result = new ArrayList<ChangeHistoryData>();
            for (ContestChangeHistory ch : contestManager.getChangeHistory(contestId)) {
                ChangeHistoryData data = getChangeHistoryData(ch);
                result.add(data);
            }

            logExit("getChangeHistory", result);
            return result;
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManagement reports error while retrieving ChangeHistories.", e);
        }

        return null;
    }

    /**
     * Returns latest change history entity list.
     *
     * @param contestId
     *            contest id to search for.
     *
     * @return Latest change history entities match the contest id and
     *         transaction id.
     * @throws PersistenceException
     *             if any other error occurs.
     */
    public List<ChangeHistoryData> getLatestChanges(long contestId) throws PersistenceException {
        logEnter("getLatestChanges");
        try {
            Long latestTransactionId = contestManager.getLatestTransactionId(contestId);

            ArrayList<ChangeHistoryData> result = new ArrayList<ChangeHistoryData>();
            if (latestTransactionId != null) {
                for (ContestChangeHistory ch : contestManager.getChangeHistory(contestId, latestTransactionId)) {
                    ChangeHistoryData data = getChangeHistoryData(ch);
                    result.add(data);
                }
            }

            logExit("getLatestChanges", result);
            return result;
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManagement reports error while retrieving latest ChangeHistories.", e);
        }

        return null;
    }

    private ChangeHistoryData getChangeHistoryData(ContestChangeHistory ch) {
        ChangeHistoryData data = new ChangeHistoryData();

        data.setContestId(ch.getContestId());
        data.setFieldName(ch.getFieldName());
        data.setNewData(ch.getNewData());
        data.setOldData(ch.getOldData());
        data.setTimestamp(getXMLGregorianCalendar(ch.getTimestamp()));
        data.setTransactionId(ch.getTransactionId());
        data.setUserAdmin(ch.isUserAdmin());
        data.setUserName(ch.getUserName());
        return data;
    }

    private ContestChangeHistory getChangeHistory(ChangeHistoryData ch) {
        ContestChangeHistory data = new ContestChangeHistory();

        data.setContestId(ch.getContestId());
        data.setFieldName(ch.getFieldName());
        data.setNewData(ch.getNewData());
        data.setOldData(ch.getOldData());
        data.setTimestamp(getDate(ch.getTimestamp()));
        data.setTransactionId(ch.getTransactionId());
        data.setUserAdmin(ch.isUserAdmin());
        data.setUserName(ch.getUserName());
        return data;
    }

    /**
     * Implements standard algorithm of authorization based on security token.
     * The token will be removed if matched.
     *
     * @param token
     *            security token.
     * @throws UserNotAuthorizedException
     *             if access was denied
     */
    private void authorizeWithUsername(String username, Contest contest) {
        UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
        long userId = p.getUserId();
        if (!username.equals(p.getName())) {
            throw new UserNotAuthorizedException("Username does not match the user assigned for current session.",
                    userId);
        }
        if (!contest.getCreatedUser().equals(userId)) {
            throw new UserNotAuthorizedException("Access denied for the contest " + userId, userId);
        }
    }

    /**
     * Delete contest.
     *
     * @param contestId
     *            contest id to delete.
     * @throws PersistenceException
     *             if any other error occurs.
     */
    public void deleteContest(long contestId) throws PersistenceException {
        logEnter("deleteContest");
        try {
            contestManager.deleteContest(contestId);

            logExit("deleteContest");
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManagement reports error while deleting contest.", e);
        }
    }


    /**
     * <p>
     * This is going to fetch all the currently available contests.
     * This method only return values used in my project widget.
     * </p>
     *
     * @return the list of all available contents (or empty if none found)
     *
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
    public List<ContestData> getAllContestHeaders() throws PersistenceException {
        logEnter("getAllContestHeaders");

        try {
            List<ContestData> result = new ArrayList<ContestData>();
            List<Contest> contests;
            if (sessionContext.isCallerInRole(ADMIN_ROLE)) {
                logError("User is admin.");
                contests = contestManager.getAllContests();
            } else {
                UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
                logError("User " + p.getUserId() + " is non-admin.");
                contests = contestManager.getContestsForUser(p.getUserId());
            }

            List<Long> forumIds = new ArrayList<Long>();
            for (Contest contest : contests) {
                ContestData convertContest = convertContestHeader(contest, false);
                result.add(convertContest);

                if (contest.getForumId() != null) {
                    forumIds.add(contest.getForumId());
                }
            }

            Map<Long, Long> contestPostCountMap = contestManager.getContestPostCount(forumIds);
            for (ContestData contest : result) {
                Long count = contestPostCountMap.get(contest.getForumId());
                if (count != null) {
                    contest.setForumPostCount(count.intValue());
                } else {
                	contest.setForumPostCount(0);
                }
            }

            logExit("getAllContestHeaders", result);
            return result;
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while retrieving contest.", e);
        }

        return null;
    }

	/**
	 * Add payment.
	 *
	 * @param submission
	 *            Submission.
	 * @param prize
	 *            prize.
	 * @throws PersistenceException
	 *             if any error occurs when adding payment.
	 * @since BUGR-456
	 */
	protected void addPayment(Submission submission, Prize prize) throws PersistenceException {
		logEnter("addPayment");
		checkParameter("submission", submission);
		checkParameter("prize", prize);
		

		if (autoPaymentsEnabled) {
			PactsServicesLocator.setProviderUrl(pactsServiceLocation);

			try{
				BasePayment payment = BasePayment.createPayment(
					com.topcoder.web.ejb.pacts.Constants.TC_STUDIO_PAYMENT,
					submission.getSubmitterId(),
					prize.getAmount(),
					prize.getContests().toArray(new Contest[]{})[0].getContestId(),
					prize.getPlace());

				payment = PactsServicesLocator.getService().addPayment(payment);

				submission.setPaymentId(payment.getId());
				submissionManager.updateSubmission(submission);

				logExit("addPayment");
			} catch (SubmissionManagementException e) {
				handlePersistenceError("SubmissionManager reports error while updateSubmission.", e);
			} catch (RemoteException e) {
				handlePersistenceError("pactsClientServices reports error while addPayment.", e);
			} catch (NamingException e) {
				handlePersistenceError("pactsClientServices reports error while addPayment.", e);
			} catch (CreateException e) {
				handlePersistenceError("pactsClientServices reports error while addPayment.", e);
			} catch (SQLException e) {
				handlePersistenceError("pactsClientServices reports error while addPayment.", e);
			}
		}
	}

    /**
     * Send payments to PACTS for all unpaid submussions with a prize already assigned
     * This service is not atomic. If it fails, you'll have to check what payments where
     * actually done trough the submussion.paid flag.
     * @param contestId
     *            Contest Id.
     * @throws PersistenceException
     *             if any error occurs when processing a payment.
     * @since STUDIO-217
     */
    public void processMissingPayments(long contestId) throws PersistenceException {
        logEnter("processMissingPayments", contestId);
        checkParameter("contestId", contestId);

        try {
        	
        	List<Submission> submissionsForContest = submissionManager.getSubmissionsForContest(contestId, true);
        	
        	for (Submission submission : submissionsForContest) {
				if (submission.getPaymentId()==null && !submission.getPrizes().isEmpty()) {
					Prize[] prizes = new Prize[10];
					Prize unpaidPrize = submission.getPrizes().toArray(prizes)[0];
					addPayment(submission, unpaidPrize);
				}
			}        	
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManagement reports error.", e);
        }
    }

}
