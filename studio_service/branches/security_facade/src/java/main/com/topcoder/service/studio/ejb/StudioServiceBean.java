/*
 * Copyright (C) 2009-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.ejb;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.CreateException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.security.RolePrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.service.studio.contest.SimpleProjectPermissionData;
import com.topcoder.service.studio.ChangeHistoryData;
import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.ContestGeneralInfoData;
import com.topcoder.service.studio.ContestMultiRoundInformationData;
import com.topcoder.service.studio.ContestNotFoundException;
import com.topcoder.service.studio.ContestPayload;
import com.topcoder.service.studio.ContestPaymentData;
import com.topcoder.service.studio.ContestSpecificationsData;
import com.topcoder.service.studio.ContestStatusData;
import com.topcoder.service.studio.ContestTypeData;
import com.topcoder.service.studio.DocumentNotFoundException;
import com.topcoder.service.studio.IllegalArgumentWSException;
import com.topcoder.service.studio.MediumData;
import com.topcoder.service.studio.MilestonePrizeData;
import com.topcoder.service.studio.PaymentType;
import com.topcoder.service.studio.PersistenceException;
import com.topcoder.service.studio.PrizeData;
import com.topcoder.service.studio.StatusNotAllowedException;
import com.topcoder.service.studio.StatusNotFoundException;
import com.topcoder.service.studio.StudioService;
import com.topcoder.service.studio.SubmissionData;
import com.topcoder.service.studio.SubmissionFeedback;
import com.topcoder.service.studio.SubmissionPaymentData;
import com.topcoder.service.studio.UploadedDocument;
import com.topcoder.service.studio.UserNotAuthorizedException;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChangeHistory;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestGeneralInfo;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.ContestManagerLocal;
import com.topcoder.service.studio.contest.ContestMultiRoundInformation;
import com.topcoder.service.studio.contest.ContestPayment;
import com.topcoder.service.studio.contest.ContestProperty;
import com.topcoder.service.studio.contest.ContestSpecifications;
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
import com.topcoder.service.studio.contest.SimplePipelineData;
import com.topcoder.service.studio.contest.SimpleContestData;
import com.topcoder.service.studio.contest.SimpleProjectContestData;
import com.topcoder.service.studio.contest.StudioCapacityData;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.contest.User;
import com.topcoder.service.studio.submission.ContestResult;
import com.topcoder.service.studio.submission.MilestonePrize;
import com.topcoder.service.studio.submission.PaymentStatus;
import com.topcoder.service.studio.submission.Prize;
import com.topcoder.service.studio.submission.PrizeType;
import com.topcoder.service.studio.submission.Submission;
import com.topcoder.service.studio.submission.SubmissionManagementException;
import com.topcoder.service.studio.submission.SubmissionManagerLocal;
import com.topcoder.service.studio.submission.SubmissionPayment;
import com.topcoder.service.studio.submission.SubmissionReview;
import com.topcoder.service.user.UserService;
import com.topcoder.util.errorhandling.ExceptionUtils;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.ejb.forums.Forums;
import com.topcoder.web.ejb.forums.ForumsHome;
import com.topcoder.web.ejb.pacts.BasePayment;

/**
 * <p>
 * This is the EJB implementation of the StudioService interface.
 * </p>
 * <p>
 * It allows getting, updating, and creating contest data; get, remove and update submission data; get some additional
 * information like content's categories, statuses and file types.
 * </p>
 * <p>
 * It uses an instance of ContestManager and an instance of SubmissionManager (injected as EJB) to perform the logic of
 * the methods. The webservices annotations are presents in the endpoint interface, this bean contains only the
 * annotation to connect the endpoint interface and this implementation. The security is provided programmatically and
 * also with annotations. The exceptions are constructed using the fault bean because the fault message can be consumed
 * as SOAP message, this is necessary because it's a webservices. This implementations is designed to be used by the
 * related interface and also by a different webservices client: all the response, request and exceptions are
 * automatically transformed to SOAP element. The webservice function is removed in old version.
 * </p>
 * <p>
 * Changes for Complex Submission Viewer Assembly - Part 2 -- - Added new resource based parameter
 * 'submissionSiteBaseUrl' - Added new resource based parameter 'submissionSiteFilePath' - From mock implementations of
 * 'artifactCount' and 'submissionUrl' in SubmissionData moved to actual implementations. Several new methods related to
 * the permission and permission type are added.
 * </p>
 * <p>
 * Module Cockpit Share Submission Integration Assembly change: Added method to retrieve all permissions by projectId.
 * </p>
 * <p>
 * All the methods that does CRUD on permission have been commented for Cockpit Project Admin Release Assembly v1.0.
 * </p>
 * <p>
 * Changes in v1.3: Added methods getUserContests(), getMilestoneSubmissionsForContest(),
 * getFinalSubmissionsForContest() and setSubmissionMilestonePrize(). Private methods for converting Contest to
 * ContestData and back were updated to support new Contest fields. The same for conversion of Submission to
 * SubmissionData.
 * </p>
 * <p>
 * Changes in v1.4 (Studio Multi-Rounds Assembly - Launch Contest): Added support for new/updated attributes in
 * ContestData, MilestonePrizeData and ContestMultiRoundInformationData.
 * </p>
 * <p>
 * Version 1.4.1 (Cockpit Pipeline Release Assembly 2 - Capacity) changelog: - added service that retrieves a list of
 * capacity data (date, number of scheduled contests) starting from tomorrow for a given contest type
 * </p>
 * <p>
 * Code example:
 * </p>
 *
 * <pre>
 * StudioService studioService = test.target;
 * // Create the competition and set its values
 * ContestData contestData = new ContestData();
 * contestData.setName(&quot;contestName&quot;);
 * contestData.setContestId(20);
 * contestData.setShortSummary(&quot;ShortSummary&quot;);
 * contestData.setContestDescriptionAndRequirements(&quot;contestDescriptionAndRequirements&quot;);
 * contestData.setFinalFileFormat(&quot;zip&quot;);
 * contestData.setContestTypeId(99990);
 * contestData.setLaunchDateAndTime(getXMLGregorianCalendar(new Date()));
 * ContestSpecificationsData specData = new ContestSpecificationsData();
 * contestData.setSpecifications(specData);
 * contestData.setMultiRound(true);
 * MilestonePrizeData prizeData = new MilestonePrizeData();
 * contestData.setMilestonePrizeData(prizeData);
 * ContestMultiRoundInformationData infoData = new ContestMultiRoundInformationData();
 * contestData.setMultiRoundData(infoData);
 * ContestGeneralInfoData generalInfoData = new ContestGeneralInfoData();
 * contestData.setGeneralInfo(generalInfoData);
 * // Create the upload documentation
 * List&lt;UploadedDocument&gt; documentationUpload = new ArrayList&lt;UploadedDocument&gt;();
 * UploadedDocument document = new UploadedDocument();
 * document.setContestId(20);
 * document.setDescription(&quot;Design Studio Distribution for Kink DAO&quot;);
 * document.setFile(&quot;designDistribution&quot;.getBytes());
 * documentationUpload.add(document);
 * // create the competition associated with a tc project
 * studioService.createContest(contestData, 40);
 * // now the competition is created and persisted
 * // Get this competition back
 * contestData = studioService.getContest(20);
 * // the competition is retrieved
 * // You can get the previous values through the getters, the getters are
 * // the same as the setters
 * // get the previous competition which is related with the project with id=40
 * List&lt;ContestData&gt; contestDatas = studioService.getContestsForProject(40);
 * // the competition is retrieved (only 1 item)
 * // get the file types
 * String fileTypes = studioService.getSubmissionFileTypes();
 * // it now contains &quot;pdf&quot;,&quot;rtf&quot;,&quot;jpg&quot;,&quot;png&quot;,etc... separated by commas
 * // remove the previous created document from the related competition
 * // the design distribution is removed now
 * // New features of the version 1.3
 * // Get all contests for which test_user is a resource
 * List&lt;ContestData&gt; testUserContests = studioService.getUserContests(&quot;test_user&quot;);
 * // Get milestone submissions for contest with ID=1
 * List&lt;SubmissionData&gt; milestoneSubmissions = studioService.getMilestoneSubmissionsForContest(1);
 * // Get final submissions for contest with ID=1
 * List&lt;SubmissionData&gt; finalSubmissions = studioService.getFinalSubmissionsForContest(1);
 * // Set milestone prize with ID=1 to submission with ID=2
 * studioService.setSubmissionMilestonePrize(2, 1);
 * </pre>
 * <p>
 * Changes in v1.5 (Prototype Conversion Studio Multi-Rounds Assembly - Submission Viewer UI): - Added a flag to
 * updateSubmissionUserRank method to support ranking milestone submissions. - Since submission date is not maintained
 * in the database, submission date attribute in SubmissionData will get create date value instead. - Added support to
 * award milestone prizes
 * </p>
 * <p>
 * Changes in v1.5.1(Cockpit Security Facade V1.0)
 *  - Methods add paremeter TCSubject in order to replacing the current permission checking security info.
 * </p>
 * <p>
 * Thread safety: this class is thread safe if the managers used are thread safe. Considering that probably the managers
 * beans will use the transactions, this stateless bean is thread safe
 * </p>
 *
 * @author fabrizyo, saarixx, pulky
 * @version 1.5.1
 * @since 1.0
 */
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
    //private static final String USER_ROLE = "Cockpit User";

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
     * Represents default mimetype that should be used when no match is found (in getMimeTypeId()).
     * </p>
     */
    @Resource(name = "defaultMimeType")
    private String defaultMimeType;

    /**
     * <p>
     * Represents the loggerName used to retrieve the logger.
     * </p>
     */
    @Resource(name = "logName")
    private String logName;



    /**
     * <p>
     * Represents the log used to log the methods logic of this class.
     * </p>
     */
    private Log log;

    /**
     * <p>
     * Represents the contest Manager instance used by this bean to perform all methods related to Contests.
     * </p>
     */
    @EJB
    private ContestManagerLocal contestManager;

    /**
     * <p>
     * Represents the submission Manager instance used by this bean to perform all methods related to Submissions.
     * </p>
     */
    @EJB
    private SubmissionManagerLocal submissionManager;

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
     * Represents the unactive status used to retrieve the check the draft status.
     * </p>
     */
    @Resource(name = "unactiveStatusId")
    private long unactiveStatusId;

    /**
     * <p>
     * Represents the active status.
     * </p>
     */
    @Resource(name = "activeStatusId")
    private long activeStatusId;

    /**
     * <p>
     * Represents the id of status of a submission paid.
     * </p>
     */
    @Resource(name = "submissionPaidStatusId")
    private long submissionPaidStatusId;

    /**
     * <p>
     * Represents the id of status of a submission unpaid.
     * </p>
     */
    @Resource(name = "submissionUnpaidStatusId")
    private long submissionUnpaidStatusId;

    /**
     * <p>
     * Represents the id of status of a submission marked for purchase.
     * </p>
     */
    @Resource(name = "submissionMarkedForPurchaseStatusId")
    private long submissionMarkedForPurchaseStatusId;

    /**
     * <p>
     * Represents the id of submission status of a submission that passed the review.
     * </p>
     */
    @Resource(name = "submissionPassedStatus")
    private long submissionPassedStatus;

    /**
     * <p>
     * Represents the id of review status of a submission that failed the screen.
     * </p>
     */
    @Resource(name = "reviewFailedStatusId")
    private long reviewFailedStatusId;

    /**
     * <p>
     * Represents the base URI used to construct the submission content.
     * </p>
     */
    @Resource(name = "submissionContentBaseURI")
    private String submissionContentBaseURI;

    /**
     * <p>
     * Represents the parameter name of the submission id, it will be used in the construction of submission URI
     * content.
     * </p>
     */
    @Resource(name = "submissionContentSubmissionIdParameterName")
    private String submissionContentSubmissionIdParameterName;

    /**
     * <p>
     * Represents the parameter name of the submission type, it will be used in the construction of submission URI
     * content.
     * </p>
     */
    @Resource(name = "submissionContentSubmissionTypeParameterName")
    private String submissionContentSubmissionTypeParameterName;

    /**
     * <p>
     * Represents the parameter value of the submission paid parameter, it will be used in the construction of
     * submission URI content.
     * </p>
     */
    @Resource(name = "submissionContentPaidParameterValue")
    private String submissionContentPaidParameterValue;

    /**
     * <p>
     * Represents the parameter value of the submission unpaid parameter, it will be used in the construction of
     * submission URI content.
     * </p>
     */
    @Resource(name = "submissionContentUnpaidParameterValue")
    private String submissionContentUnpaidParameterValue;

    /**
     * <p>
     * Represents the id for the Contest property "Short Summary".
     *
     * @since 1.0.3
     */
    @Resource(name = "contestPropertyShortSummaryId")
    private long contestPropertyShortSummaryId;

    /**
     * <p>
     * Represents the id for the Contest property "Contest Overview Text".
     *
     * @since 1.0.3
     */
    @Resource(name = "contestPropertyContestOverviewTextId")
    private long contestPropertyContestOverviewTextId;

    /**
     * <p>
     * Represents the id for the Contest property "Color Requirements".
     * </p>
     *
     * @since 1.0.3
     */
    @Resource(name = "contestPropertyColorRequirementsId")
    private long contestPropertyColorRequirementsId;

    /**
     * <p>
     * Represents the id for the Contest property "Font Requirements".
     * <p>
     *
     * @since 1.0.3
     */
    @Resource(name = "contestPropertyFontRequirementsId")
    private long contestPropertyFontRequirementsId;

    /**
     * <p>
     * Represents the id for the Contest property "Size Requirements".
     * <p>
     *
     * @since 1.0.3
     */
    @Resource(name = "contestPropertySizeRequirementsId")
    private long contestPropertySizeRequirementsId;

    /**
     * <p>
     * Represents the id for the Contest property "Other Requirements".
     * <p>
     *
     * @since 1.0.3
     */
    @Resource(name = "contestPropertyOtherRequirementsId")
    private long contestPropertyOtherRequirementsId;

    /**
     * Represents the id for the Contest property "Final File Format".
     *
     * @since 1.0.3
     */
    @Resource(name = "contestPropertyFinalFileFormatId")
    private long contestPropertyFinalFileFormatId;

    /**
     * Represents the id for the Contest property "Other File Formats".
     *
     * @since 1.0.3
     */
    @Resource(name = "contestPropertyOtherFileFormatsId")
    private long contestPropertyOtherFileFormatsId;

    /**
     * Represents the id for the Contest property "Create User Handle".
     */
    @Resource(name = "contestPropertyCreateUserHandleId")
    private long contestPropertyCreateUserHandleId;

    /**
     * Represents the id for the Contest property "Requires Preview Image".
     *
     * @since TCCC-284
     */
    @Resource(name = "contestPropertyRequiresPreviewImageId")
    private long contestPropertyRequiresPreviewImageId;

    /**
     * Represents the id for the Contest property "Requires Preview File".
     *
     * @since TCCC-284
     */
    @Resource(name = "contestPropertyRequiresPreviewFileId")
    private long contestPropertyRequiresPreviewFileId;

    /**
     * Represents the id for the Contest property "Maximum Submissions".
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
     * Represents the default text for the Contest property "Notes on Winner Selection".
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
     * Represents the default text for the Contest property "*Notes on Submission File(s)".
     *
     * @since TCCC-384
     */
    @Resource(name = "defaultContestNotesOnSubmissionFiles")
    private String defaultContestNotesOnSubmissionFiles;

    /**
     * Represents the id for the Contest property "Contest PrizeType Id".
     *
     * @since TCCC-351
     */
    @Resource(name = "contestPrizeTypeId")
    private long contestPrizeTypeId;

    /**
     * Represents the id for the Contest property "Client Selection PrizeType Id".
     *
     * @since TCCC-351
     */
    @Resource(name = "clientSelectionPrizeTypeId")
    private long clientSelectionPrizeTypeId;

    /**
     * Represents the base path for the documents. Should be configured like /studiofiles/documents. [BUG TCCC-134]
     */
    @Resource(name = "documentBasePath")
    private String documentBasePath = DEFAULT_DOCUMENT_BASE_PATH;

    /**
     * Represents the additionalSubmissionPurchasePriceRatio. Used in purchaseSubmission method.
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
     * Represents the id for the Contest property "Billing Project".
     *
     * @since TCCC-499
     */
    @Resource(name = "contestPropertyBillingProjectId")
    private long contestPropertyBillingProjectId;

    /**
     * Represents the id for the Contest property "Viewable Submissions".
     */
    @Resource(name = "contestPropertyViewableSubmissionsId")
    private long contestPropertyViewableSubmissionsId;

    /**
     * Represents the id for the Contest property "Contest Administration Fee ".
     *
     * @since BUGR-456
     */
    @Resource(name = "autoPaymentsEnabled")
    private boolean autoPaymentsEnabled = false;

    /**
     * Represents the location of the PACTS services for submission auto-payments.
     *
     * @since BUGR-456
     */
    @Resource(name = "pactsServiceLocation")
    private String pactsServiceLocation = "jnp://localhost:1099";

    /**
     * Represents the jive forum VersionId.
     *
     * @since TCCC-287
     */
    @Resource(name = "forumBeanProviderUrl")
    private String forumBeanProviderUrl;

    /**
     * Represents the submission site base url.
     *
     * @since Complex Submission Viewer Assembly - Part 2
     */
    @Resource(name = "submissionSiteBaseUrl")
    private String submissionSiteBaseUrl;

    /**
     * Represents the submission site file path.
     *
     * @since Complex Submission Viewer Assembly - Part 2
     */
    @Resource(name = "submissionSiteFilePath")
    private String submissionSiteFilePath;

    /**
     * <p>
     * A <code>UserService</code> providing access to available <code>User Service EJB</code>.
     * </p>
     * @since v1.0.2 adding TCSubject
     */
    @EJB(name = "ejb/UserService")
    private UserService userService = null;
    /**
     * <p>
     * This is the default constructor. It does nothing.
     * </p>
     */
    public StudioServiceBean() {
    }

    /**
     * Returns base path for document files. [BUG TCCC-134]
     *
     * @return the documentBasePath
     */
    public String getDocumentBasePath() {
        return documentBasePath;
    }

    /**
     * Sets the base path for document files. [BUG TCCC-134]
     *
     * @param documentBasePath the documentBasePath to set.
     */
    public void setDocumentBasePath(String documentBasePath) {
        this.documentBasePath = documentBasePath;
    }

    /**
     * <p>
     * This is method is performed after the construction of the bean, at this point all the bean's resources will be
     * ready.
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
        // first record in logger
        logExit("init");
    }

    /**
     * <p>
     * Create contest for project. Return contest populated with id.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestData the contestData used to create the Contest
     * @param tcDirectProjectId the tc project id set to Contest
     * @return the ContestData persisted and polulated with the new id
     * @throws IllegalArgumentWSException if the contestData is null or if the tcDirectProjectId is less than 0
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to perform this method
     */
    public ContestData createContest(TCSubject tcSubject, ContestData contestData, long tcDirectProjectId)
            throws PersistenceException {

        logEnter("createContest", contestData, tcDirectProjectId);
        checkParameter("tcSubject", tcSubject);
        checkParameter("contestData", contestData);
        checkParameter("tcDirectProjectId", tcDirectProjectId);

        try {
            Contest contest = convertContestData(tcSubject, contestData);

            contest.setTcDirectProjectId(tcDirectProjectId);

            contest.setCreatedUser(tcSubject.getUserId());

            contest = contestManager.createContest(contest);
            List<UploadedDocument> documents = new ArrayList<UploadedDocument>();
            for (UploadedDocument doc : contestData.getDocumentationUploads()) {
                if (doc != null) {
                    documents.add(uploadDocument(doc, contest));
                }
            }
            contestData = convertContest(contest);
            contestData.setDocumentationUploads(documents);
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while creating new contest.", e);
        }

        logExit("createContest", contestData);
        return contestData;
    }

    /**
     * Create the forum with name and user id.
     *
     * @param name the name used to create the forum.
     * @param userId the user id to create the forum.
     */
    public long createForum(String name, long userId) {
        try {
            Properties p = new Properties();
            p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
            p.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
            p.put(Context.PROVIDER_URL, forumBeanProviderUrl);

            Context c = new InitialContext(p);
            ForumsHome forumsHome = (ForumsHome) c.lookup(ForumsHome.EJB_REF_NAME);

            Forums forums = forumsHome.create();

            long forumId = forums.createStudioForum(name);
            if (forumId < 0)
                throw new Exception("createStudioForum returned -1");

            logDebug("Created forum " + forumId + " for " + name);

            forums.createForumWatch(userId, forumId);

            return forumId;
        } catch (Exception e) {
            logError("*** Could not create a forum for " + name);
            logError(e);
            return -1;
        }
    }

    /**
     * <p>
     * Get contest by id
     * </p>
     *
     * @param contestId the id used to retrieve the contest
     * @return the ContestData retrieved
     * @throws IllegalArgumentWSException if the contestId is less than 0
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to perform this method
     * @throws ContestNotFoundException if the contest is not found
     */
    public ContestData getContest(long contestId) throws PersistenceException,
            ContestNotFoundException {
        logEnter("getContest", contestId);
        checkParameter("contestId", contestId);

        try {
            // check for the authorization, which also returns the contest.
            Contest contest = contestManager.getContest(contestId);
            if (contest == null) {
                handleContestNotFoundError(null, contestId);
            }

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
     * Get contests for given project. Return an empty list if there are no contests.
     * </p>
     *
     * @param tcDirectProjectId the tc Direct ProjectId used to retrieve the ContestData
     * @return the contest data which represents the contests
     * @throws IllegalArgumentWSException if the tcDirectProjectId is less than 0
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to perform this method
     */
    public List<ContestData> getContestsForProject(long tcDirectProjectId)
            throws PersistenceException {
        logEnter("getContestsForProject", tcDirectProjectId);
        checkParameter("tcDirectProjectId", tcDirectProjectId);

        try {
            List<Contest> contests = contestManager.getContestsForProject(tcDirectProjectId);
            List<ContestData> result = convertContests(contests);

            logExit("getContestsForProject", result.size());
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
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestData the contest data to update
     * @throws IllegalArgumentWSException if the argument is null
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to perform this method
     * @throws ContestNotFoundException if the contest is not found
     */
    public void updateContest(TCSubject tcSubject, ContestData contestData) throws PersistenceException,
            ContestNotFoundException {
        logEnter("updateContest", tcSubject.getUserId(), contestData);
        checkParameter("tcSubject", tcSubject);
        checkParameter("contestData", contestData);

        // check existing
        getContest(contestData.getContestId());

        try {
            long timestamp = System.currentTimeMillis() * 100 + RANDOM.nextInt(1000);
            contestManager.updateContest(convertContestData(tcSubject, contestData), timestamp, getUserName(tcSubject),
                    isRole(tcSubject, ADMIN_ROLE));
        } catch (EntityNotFoundException e) {
            handleContestNotFoundError(e, contestData.getContestId());
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while updating contest.", e);
        }

        logExit("updateContest");
    }

    /**
     * <p>
     * Checks if the login user is given role.
     * </p>
     *
     * @param tcSubject TCSubject instance for login user
     * @return true if it is admin
     */
    private static boolean isRole(TCSubject tcSubject, String roleName) {
        Set<RolePrincipal> roles = tcSubject.getPrincipals();
        if (roles != null) {
            for (RolePrincipal role : roles) {
                if (role.getName().equalsIgnoreCase(roleName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <p>
     * Checks if the contest with the given id exists or not.
     * </p>
     *
     * @param contestId the given contest id
     * @return Contest instance
     * @throws PersistenceException fail to retrieve the contest
     * @throws ContestNotFoundException if the contest id does not exist
     */
    private Contest getContst(long contestId) throws PersistenceException, ContestNotFoundException {
        Contest contest = null;
        try {
            contest = this.contestManager.getContest(contestId);
            if (contest == null) {
                handleContestNotFoundError(null, contestId);
            }
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while updating contest.", e);
        }
        return contest;
    }

    /**
     * <p>
     * Update contest status
     * </p>
     *
     * @param contestId the id of contest to what the status will be updated
     * @param newStatusId the id of the new status
     * @throws IllegalArgumentWSException if any id is less than 0
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to perform this method
     * @throws StatusNotAllowedException if the status is not allowed
     * @throws StatusNotFoundException if the status is not found
     * @throws ContestNotFoundException if the contest is not found
     */
    public void updateContestStatus(long contestId, long newStatusId) throws PersistenceException,
            ContestNotFoundException, StatusNotFoundException, StatusNotAllowedException {
        logEnter("updateContestStatus", contestId, newStatusId);
        checkParameter("contestId", contestId);
        checkParameter("newStatusId", newStatusId);

        getContst(contestId);

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
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param uploadedDocument the uploadDocument to update
     * @return the same instance passed in argument with the documentId updated
     * @throws IllegalArgumentWSException if the argument is null
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to perform this method
     * @throws ContestNotFoundException if the contest is not found
     */
    public UploadedDocument uploadDocumentForContest(TCSubject tcSubject, UploadedDocument uploadedDocument)
            throws PersistenceException, ContestNotFoundException {
        logEnter("uploadDocumentForContest", tcSubject.getUserId(), uploadedDocument);
        checkParameter("tcSubject", tcSubject);
        checkParameter("uploadedDocument", uploadedDocument);

        // retrieve contest and authorize
        Contest c = getContst(uploadedDocument.getContestId());

            // [TCCC-385]
            if (c.getStatus() == null
                    || (c.getStatus().getContestStatusId() != draftStatusId
                            && c.getStatus().getContestStatusId() != unactiveStatusId
                            && c.getStatus().getContestStatusId() != activeStatusId && c.getStatus()
                            .getContestStatusId() != scheduledStatusId)) {
                handleAuthorizationError("contest must be in draft status or unactive status.");
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
     * @param document the document to remove
     * @throws IllegalArgumentWSException if the argument is null
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to perform this method
     * @throws DocumentNotFoundException if the document is not found
     */
    public void removeDocumentFromContest(UploadedDocument document) throws PersistenceException,
            DocumentNotFoundException {
        logEnter("removeDocumentFromContest", document);
        checkParameter("document", document);

        try {

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
        }

        logExit("removeDocumentFromContest");
    }

    /**
     * <p>
     * Retrieve submission data. return an empty list if there are no submission data items.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId the contest if used
     * @return the submission data of contest
     * @throws IllegalArgumentWSException if the argument id less than 0
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to perform this method
     * @throws ContestNotFoundException if the contest is not found
     */
    public List<SubmissionData> retrieveSubmissionsForContest(TCSubject tcSubject, long contestId)
            throws PersistenceException, ContestNotFoundException {
        logEnter("retrieveSubmissionsForContest", tcSubject.getUserId(), contestId);
        checkParameter("tcSubject", tcSubject);
        checkParameter("contestId", contestId);

        boolean selectFullSubmission = isRole(tcSubject, ADMIN_ROLE);

        try {
            Contest contest = getContst(contestId);
            // Retrieve max submissions per user.
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

            logDebug("Max Submissions for contest is " + maxSubmissionsPerUser + ", contest id:" + contestId);

            List<Submission> submissions = submissionManager.getSubmissionsForContest(contestId, selectFullSubmission,
                    maxSubmissionsPerUser);
            List<SubmissionPayment> submissionPayments = submissionManager.getSubmissionPaymentsForContest(contestId);
            List<SubmissionData> result = convertSubmissions(submissions, submissionPayments);

            logExit("retrieveSubmissionsForContest", result.size());

            return result;
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManager reports error while retrieving submissions for contest.", e);
        }

        // never reached
        return null;
    }

    /**
     * <p>
     * Retrieve submission by member. return an empty list if there are no submission data
     * </p>
     *
     * @param userId the user id used to retrieve the submissions
     * @return the submissions by member
     * @throws IllegalArgumentWSException if the id is less than 0
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to perform this method
     */
    public List<SubmissionData> retrieveAllSubmissionsByMember(long userId)
            throws PersistenceException {
        logEnter("retrieveAllSubmissionsByMember", userId);
        checkParameter("userId", userId);

        try {
            List<SubmissionData> result = convertSubmissions(submissionManager.getAllSubmissionsByMember(userId), null);
            logExit("retrieveAllSubmissionsByMember", result.size());
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
     * @param submission the submission to update
     * @throws IllegalArgumentWSException if the submission is null
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to perform this method
     */
    public void updateSubmission(SubmissionData submission) throws PersistenceException {
        logEnter("updateSubmission", submission);
        checkParameter("submission", submission);

        try {
            submissionManager.updateSubmission(convertSubmissionData(submission));
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManager reports error while updating submission.", e);

        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while getting contest in updating submission.", e);
        }

        logExit("updateSubmission");
    }

    /**
     * <p>
     * Remove submission
     * </p>
     *
     * @param submissionId the id of submission to remove
     * @throws IllegalArgumentWSException if the submissionId is less than 0
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to perform this method
     */
    public void removeSubmission(long submissionId) throws PersistenceException {
        logEnter("removeSubmission", submissionId);
        checkParameter("submissionId", submissionId);

        try {
            submissionManager.removeSubmission(submissionId);
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManager reports error while removing submission.", e);
        }

        logExit("removeSubmission");
    }

    /**
     * <p>
     * Get contest statuses. Return an empty list if there are no ContestStatusData
     * </p>
     *
     * @return the list of status
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to perform this method
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

            logExit("getStatusList", result.size());
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
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to perform this method
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
     * Return the log. Don't perform the logging in this method.
     * </p>
     *
     * @return the log
     */
    protected Log getLog() {
        return log;
    }

    /**
     * <p>
     * Return the contestManager. Don't perform the logging in this method.
     * </p>
     *
     * @return the contestManager
     */
    protected ContestManagerLocal getContestManager() {
        return contestManager;
    }

    /**
     * <p>
     * Return the submissionManager. Don't perform the logging in this method.
     * </p>
     *
     * @return the submissionManager
     */
    protected SubmissionManagerLocal getSubmissionManager() {
        return submissionManager;
    }

    /**
     * <p>
     * This method used to convert ContestData object into Contest object.
     * </p>
     * <p>
     * Updated for Cockpit Release Assembly for Receipts - Added TC Direct Project Name
     * </p>
     * <p>
     * Changes in v1.3: The following properties of data: ContestData must be converted and set to the returned Contest
     * instance: generalInfo, specifications, multiRoundData, milestonePrizeData and nonWinningSubmissionsPurchased. All
     * XxxData entities must be converted to Xxx entities defined in Contest and Submission Entities component (e.g.
     * ContestGeneralInfoData -> ContestGeneralInfo). Such conversion is done by creating an instance of Xxx and setting
     * all properties from XxxData to Xxx instance.
     * </p>
     *
     * @param data ContestData object to convert
     * @return converted Contest instance
     * @throws PersistenceException when error reported by manager
     * @throws ContestManagementException when error reported by manager
     */
    private Contest convertContestData(TCSubject tcSubject, ContestData data) throws PersistenceException,
            ContestManagementException {
        Contest result = new Contest();

        // StartDate and endDate: the startDate is the launchDateAndTime, the
        // endDate is launchDateTime+durationInHours
        Date startDate = getDate(data.getLaunchDateAndTime());

        result.setStartDate(startDate);
        if (startDate != null) {
            result.setEndDate(new Date((long) (startDate.getTime() + 60l * 60 * 1000 * data.getDurationInHours())));
        }

        data.setShortSummary(data.getShortSummary().replaceAll("&lt;", "<").replaceAll("&amp;", "&"));
        data.setContestDescriptionAndRequirements(data.getContestDescriptionAndRequirements().replaceAll("&lt;", "<")
                .replaceAll("&amp;", "&"));

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
        addContestConfig(result, contestPropertyCreateUserHandleId, getUserName(tcSubject));

        // [TCCC-284]
        addContestConfig(result, contestPropertyRequiresPreviewFileId, String.valueOf(data.isRequiresPreviewFile()));
        addContestConfig(result, contestPropertyRequiresPreviewImageId, String.valueOf(data.isRequiresPreviewImage()));
        addContestConfig(result, contestPropertyMaximumSubmissionsId, String.valueOf(data.getMaximumSubmissions()));

        // [TCCC-283]
        addContestConfig(result, contestPropertyEligibilityId, defaultContestEligibilityText);
        addContestConfig(result, contestPropertyNotesOnWinnerSelectionId, defaultContestNotesOnWinnerSelectionText);
        addContestConfig(result, contestPropertyPrizeDescriptionId, defaultContestPrizeDescriptionText);

        addContestConfig(result, contestPropertyBillingProjectId, String.valueOf(data.getBillingProject()));

        // default viewable submissions to true
        addContestConfig(result, contestPropertyViewableSubmissionsId, "true");

        result.setContestId(data.getContestId());
        result.setName(data.getName());
        result.setProjectId(data.getProjectId());
        result.setWinnerAnnoucementDeadline(getDate(data.getWinnerAnnoucementDeadline()));

        logInfo("ContestData.status: " + data.getDetailedStatusId());
        ContestStatus contestStatus = contestManager.getContestStatus(data.getDetailedStatusId());
        logInfo(MessageFormat.format("Retrieved contest status: desc:[{0}] name:[{1}] id:[{2}]", contestStatus
                .getDescription(), contestStatus.getName(), contestStatus.getContestStatusId()));

        result.setStatus(contestStatus);
        result.setStatusId(data.getStatusId());

        result.setTcDirectProjectId(data.getTcDirectProjectId());

        // Cockpit Release Assembly for Receipts.
        result.setTcDirectProjectName(data.getTcDirectProjectName());
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

        // added in v1.4
        result.setMultiRound(data.isMultiRound());

        // changed in v1.3
        if (data.getSpecifications() != null) {
            result.setSpecifications(createContestSpecifications(data.getSpecifications()));
        }
        if (data.getGeneralInfo() != null) {
            result.setGeneralInfo(createContestGeneralInfo(data.getGeneralInfo()));
        }

        if (data.getMilestonePrizeData() != null) {
            result.setMilestonePrize(createMilestonePrize(data.getMilestonePrizeData()));
        }

        if (data.getMultiRoundData() != null) {
            result.setMultiRoundInformation(createContestMultiRoundInformation(data.getMultiRoundData()));
        }
        result.setNonWinningSubmissionsPurchased(data.isNonWinningSubmissionsPurchased());

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
        if (data.getForumId() < 0) {
            result.setForumId(null);
        } else {
            result.setForumId(data.getForumId());
        }
        return result;
    }

    /**
     * Create the new ContestGeneralInfo from ContestGeneralInfoData object.
     *
     * @param data the ContestGeneralInfoData having the properties to create new ContestGeneralInfo.
     * @return the newly ContestGeneralInfo.
     * @since 1.3
     */
    private static ContestGeneralInfo createContestGeneralInfo(ContestGeneralInfoData data) {
        ContestGeneralInfo info = new ContestGeneralInfo();
        info.setBrandingGuidelines(data.getBrandingGuidelines());
        info.setDislikedDesignsWebsites(data.getDislikedDesignsWebsites());
        info.setGoals(data.getGoals());
        info.setOtherInstructions(data.getOtherInstructions());
        info.setTargetAudience(data.getTargetAudience());
        info.setWinningCriteria(data.getWinningCriteria());
        return info;
    }

    /**
     * Create new MilestonePrize object from MilestonePrizeData object. Note: added support for new attributes in v1.4.
     *
     * @param data the MilestonePrizeData having the properties to create new MilestonePrize.
     * @return the newly MilestonePrize.
     * @throws ContestManagementException if an error occurs while getting the prize type
     * @since 1.3
     */
    private MilestonePrize createMilestonePrize(MilestonePrizeData data) throws ContestManagementException {
        MilestonePrize prize = new MilestonePrize();

        prize.setMilestonePrizeId(data.getId());
        if (data.getAmount() != null && data.getAmount().isNaN()) {
            prize.setAmount(null);
        } else {
            prize.setAmount(data.getAmount());
        }

        prize.setNumberOfSubmissions(data.getNumberOfSubmissions());

        if (data.getCreateDate() != null) {
            prize.setCreateDate(getDate(data.getCreateDate()));
        } else {
            prize.setCreateDate(new Date());
        }

        PrizeType type = contestManager.getPrizeType(PrizeType.MILESTONE);
        prize.setType(type);

        return prize;
    }

    /**
     * Create new ContestSpecifications object from ContestSpecificationsData object.
     *
     * @param data the ContestSpecificationsData having the properties to create new ContestSpecifications.
     * @return the newly ContestSpecifications.
     * @since 1.3
     */
    private static ContestSpecifications createContestSpecifications(ContestSpecificationsData data) {
        ContestSpecifications spec = new ContestSpecifications();
        spec.setAdditionalRequirementsAndRestrictions(data.getAdditionalRequirementsAndRestrictions());
        spec.setColors(data.getColors());
        spec.setFonts(data.getFonts());
        spec.setLayoutAndSize(data.getLayoutAndSize());
        return spec;
    }

    /**
     * Create new ContestMultiRoundInformation object from ContestMultiRoundInformationData object. Note: added support
     * for new attributes in v1.4.
     *
     * @param data the ContestMultiRoundInformationData having the properties to create new
     *            ContestMultiRoundInformation.
     * @return the newly ContestMultiRoundInformation.
     * @since 1.3
     */
    private ContestMultiRoundInformation createContestMultiRoundInformation(ContestMultiRoundInformationData data) {
        ContestMultiRoundInformation info = new ContestMultiRoundInformation();
        info.setContestMultiRoundInformationId(data.getId());
        info.setMilestoneDate(getDate(data.getMilestoneDate()));
        info.setRoundOneIntroduction(data.getRoundOneIntroduction());
        info.setRoundTwoIntroduction(data.getRoundTwoIntroduction());
        info.setSubmittersLockedBetweenRounds(data.getSubmittersLockedBetweenRounds());
        return info;
    }

    /**
     * Get the ContestType object from persistence by its id
     *
     * @param contestTypeId id to retrieve
     * @return a ContestType object with that id, or null if not found.
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
     * @param contest Contest instance to convert
     * @return converted ContestDate object
     * @throws ContestManagementException error occurred from ContestManager
     */
    private ContestData convertContest(Contest contest) throws ContestManagementException {
        return convertContest(contest, true);
    }

    /**
     * <p>
     * This method converts Contest object into ContestData object.
     * </p>
     * <p>
     * Updated for Cockpit Release Assembly for Receipts - Added TC Direct Project Name
     * </p>
     * <p>
     * Changes in v1.3: The following properties of returned ContestData must be initialized from namesake properties of
     * contest:Contest: generalInfo, specifications, multiRoundData, milestonePrizeData and
     * nonWinningSubmissionsPurchased. All newly defined Xxx entities must be converted to XxxData entities defined in
     * this component (e.g. ContestGeneralInfo to ContestGeneralInfoData). Such conversion is done by creating an
     * instance of XxxData and setting all properties from Xxx to XxxData instance.
     * </p>
     *
     * @param contest Contest instance to convert
     * @param Load forum info or not.
     * @return converted ContestDate object
     * @throws ContestManagementException error occurred from ContestManager
     */
    private ContestData convertContest(Contest contest, boolean loadForumInfo) throws ContestManagementException {
        ContestData contestData = new ContestData();

        contestData.setLaunchDateAndTime(getXMLGregorianCalendar(contest.getStartDate()));

        contestData.setContestId(unbox(contest.getContestId()));
        contestData.setCreatorUserId(unbox(contest.getCreatedUser()));
        contestData.setName(contest.getName());
        contestData.setProjectId(unbox(contest.getProjectId()));
        contestData.setWinnerAnnoucementDeadline(getXMLGregorianCalendar(contest.getWinnerAnnoucementDeadline()));

        // change v1.3 checking the status and adding the unbox operation.
        if (contest.getStatus() != null) {
            contestData.setDetailedStatusId(unbox(contest.getStatus().getContestStatusId()));
        }

        contestData.setStatusId(unbox(contest.getStatusId()));
		//TCCC-1879 TODO
		//for (ContestRegistration contestRegistration : contest.getContestRegistrations()) {
		//    contestData.getContestRegistrations.add((ContestRegistration)(contestRegistration.clone()));
		//}
        contestData.setNumberOfRegistrants(contest.getContestRegistrations().size());
        if (contest.isLaunchImmediately() != null) {
            contestData.setLaunchImmediately(contest.isLaunchImmediately());
        }

        // [TCCC-585]
        if (contest.getTcDirectProjectId() != null) {
            contestData.setTcDirectProjectId(contest.getTcDirectProjectId());
        }

        // Cockpit Release Assembly for Receipts.
        contestData.setTcDirectProjectName(contest.getTcDirectProjectName());

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
            else if (propertyId == contestPropertyBillingProjectId) {
                contestData.setBillingProject(Long.parseLong(value));
            }
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

        if (contest.getPrizes() != null) {
            for (Prize prize : contest.getPrizes()) {
                PrizeData prizeData = new PrizeData();
                prizeData.setAmount(prize.getAmount() == null ? 0 : prize.getAmount());
                prizeData.setPlace(unbox(prize.getPlace()));
                prizes.add(prizeData);
            }
        }

        contestData.setPrizes(prizes);

        if (contest.getEndDate() != null && contest.getStartDate() != null) {
            // TCCC-293
            double durationInHours = (contest.getEndDate().getTime() - contest.getStartDate().getTime())
                    / (60.0 * 60 * 1000);

            contestData.setDurationInHours(durationInHours);
        }

        // TCCC-299 Exception when Editing project
        if (contest.getContestChannel() != null) {
            contestData.setContestChannelId(contest.getContestChannel().getContestChannelId());
        }

        List<MediumData> mediums = new ArrayList<MediumData>();

        if (contest.getMedia() != null) {
            for (Medium medium : contest.getMedia()) {
                MediumData mediumData = new MediumData();
                mediumData.setDescription(medium.getDescription());
                mediumData.setMediumId(medium.getMediumId());
                mediums.add(mediumData);
            }
        }

        contestData.setMedia(mediums);

        // change in v1.4
        if (contest.isMultiRound() != null) {
            contestData.setMultiRound(contest.isMultiRound());
        }
        // change in v1.3
        if (contest.getGeneralInfo() != null) {
            contestData.setGeneralInfo(createContestGeneralInfoData(contest.getGeneralInfo()));
        }
        if (contest.getSpecifications() != null) {
            contestData.setSpecifications(createContestSpecificationsData(contest.getSpecifications()));
        }
        if (contest.getMilestonePrize() != null) {
            contestData.setMilestonePrizeData(createMilestonePrizeData(contest.getMilestonePrize()));
        }
        if (contest.getMultiRoundInformation() != null) {
            contestData.setMultiRoundData(createContestMultiRoundInformationData(contest.getMultiRoundInformation()));
        }

        if (contest.isNonWinningSubmissionsPurchased() != null) {
            contestData.setNonWinningSubmissionsPurchased(contest.isNonWinningSubmissionsPurchased());
        }

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
     * Create the new ContestGeneralInfoData from ContestGeneralInfo object.
     *
     * @param info the ContestGeneralInfo having the properties to create new ContestGeneralInfoData.
     * @return the newly ContestGeneralInfoData.
     * @since 1.3
     */
    private static ContestGeneralInfoData createContestGeneralInfoData(ContestGeneralInfo info) {
        ContestGeneralInfoData data = new ContestGeneralInfoData();
        data.setBrandingGuidelines(info.getBrandingGuidelines());
        data.setDislikedDesignsWebsites(info.getDislikedDesignsWebsites());
        data.setGoals(info.getGoals());
        data.setOtherInstructions(info.getOtherInstructions());
        data.setTargetAudience(info.getTargetAudience());
        data.setWinningCriteria(info.getWinningCriteria());
        return data;
    }

    /**
     * Create new MilestonePrizeData object from MilestonePrize object. Note: added support for new attributes in v1.4.
     *
     * @param prize the MilestonePrize having the properties to create new MilestonePrize.
     * @return the newly MilestonePrizeData.
     * @since 1.3
     */
    private MilestonePrizeData createMilestonePrizeData(MilestonePrize prize) {
        MilestonePrizeData data = new MilestonePrizeData();
        data.setId(prize.getMilestonePrizeId());
        data.setAmount(prize.getAmount());
        data.setNumberOfSubmissions(prize.getNumberOfSubmissions());
        data.setCreateDate(getXMLGregorianCalendar(prize.getCreateDate()));
        return data;
    }

    /**
     * Create new ContestSpecificationsData object from ContestSpecifications object.
     *
     * @param spec the ContestSpecifications having the properties to create new ContestSpecificationsData.
     * @return the newly ContestSpecificationsData.
     * @since 1.3
     */
    private static ContestSpecificationsData createContestSpecificationsData(ContestSpecifications spec) {
        ContestSpecificationsData data = new ContestSpecificationsData();
        data.setAdditionalRequirementsAndRestrictions(spec.getAdditionalRequirementsAndRestrictions());
        data.setColors(spec.getColors());
        data.setFonts(spec.getFonts());
        data.setLayoutAndSize(spec.getLayoutAndSize());
        return data;
    }

    /**
     * Create new ContestMultiRoundInformationData object from ContestMultiRoundInformation object. Note: added support
     * for new attributes in v1.4.
     *
     * @param info the ContestMultiRoundInformation having the properties to create new
     *            ContestMultiRoundInformationData.
     * @return the newly ContestMultiRoundInformationData.
     * @since 1.3
     */
    private ContestMultiRoundInformationData createContestMultiRoundInformationData(ContestMultiRoundInformation info) {
        ContestMultiRoundInformationData data = new ContestMultiRoundInformationData();
        data.setId(info.getContestMultiRoundInformationId());
        data.setMilestoneDate(getXMLGregorianCalendar(info.getMilestoneDate()));
        data.setRoundOneIntroduction(info.getRoundOneIntroduction());
        data.setRoundTwoIntroduction(info.getRoundTwoIntroduction());
        data.setSubmittersLockedBetweenRounds(info.isSubmittersLockedBetweenRounds());
        return data;
    }

    /**
     * This method converts Contest object into ContestData object.
     *
     * @param contest Contest instance to convert
     * @param Load forum info or not.
     * @return converted ContestDate object
     * @throws ContestManagementException error occurred from ContestManager
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
        if (contest.getTcDirectProjectId() != null) {
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
     * Converts Document object into UploadedDocument instance. Simply sets basic attributes and retrieves document data
     * from contestManager. As required by designer, errors are suppressed.
     *
     * @param from Document to convert
     * @param setDocumentContent If true, load document content.
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
     * Converts Document object into UploadedDocument instance. Simply sets basic attributes and retrieves document data
     * from contestManager. As required by designer, errors are suppressed.
     *
     * @param from Document to convert
     * @return converted UploadedDocument object
     */
    private UploadedDocument convertDocument(Document from) {
        return convertDocument(from, true);
    }

    /**
     * Converts list of Contest objects into list of ContestData objects, calling convertContest repeatly.
     *
     * @param contests list of contests to convert
     * @return converted list of contests
     * @throws ContestManagementException if any other error occurs.
     */
    private List<ContestData> convertContests(List<Contest> contests) throws ContestManagementException {
        List<ContestData> result = new ArrayList<ContestData>(contests.size());
        for (Contest contest : contests) {
            result.add(convertContest(contest));
        }
        return result;
    }

    /**
     * Converts standard java Date object into XMLGregorianCalendar instance. Returns null if parameter is null.
     *
     * @param date Date object to convert
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
     * Converts XMLGregorianCalendar date into standard java Date object. Returns null if argument is null.
     *
     * @param calendar calendar instance to convert
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
     * @param contest contest to add config to
     * @param contestPropertyId Contest Property Id of the property
     * @param value value to add
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
     * This method used to upload document in persistence. It will save both document's data and it's body (byte[]
     * contents).
     *
     * @param data document to upload
     * @param contest contest to which document belongs
     * @return uploaded document
     * @throws PersistenceException if ContestManager reports any error
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
     * <p>
     * This method converts list of Submission objects into list of SubmissionData objects. It's logic is derived from
     * CS and designer's comments.
     * </p>
     * <p>
     * Change in 1.3: the Submission Type property of Submission is set.
     * </p>
     * <p>
     * Changes v1.5 since submission date is not maintained in the database, create date will be used to fill that
     * value. AwardMilestonePrize is also supported.
     * </p>
     *
     * @param submissions list of submissions to convert
     * @param submissionPayments list of submission payments to be used in submission.
     * @return converted list of SubmissionData entities
     * @throws PersistenceException if SubmissionManager reports error
     */
    private List<SubmissionData> convertSubmissions(List<Submission> submissions,
            List<SubmissionPayment> submissionPayments) throws PersistenceException {
        List<SubmissionData> result = new ArrayList<SubmissionData>();

        Map<Long, SubmissionPayment> submissionPaymentMap = null;

        if (submissionPayments != null) {
            submissionPaymentMap = new HashMap<Long, SubmissionPayment>();
            for (SubmissionPayment sp : submissionPayments) {
                submissionPaymentMap.put(sp.getSubmission().getSubmissionId(), sp);
            }
        }

        for (Submission s : submissions) {
            SubmissionData sd = new SubmissionData();

            // Changes in v1.3:
            if (s.getType() != null) {
                sd.setSubmissionType(s.getType().getDescription());
            }

            if (s.getContest() != null) {
                sd.setContestId(unbox(s.getContest().getContestId()));
            }

            // Set rank.
            sd.setRank(s.getRank());
            sd.setSubmissionId(unbox(s.getSubmissionId()));

            // since submission date is not maintained in the database, create date will be used to fill that value
            sd.setSubmittedDate(getXMLGregorianCalendar(s.getCreateDate()));
            sd.setAwardMilestonePrize(s.isAwardMilestonePrize());

            sd.setSubmitterId(unbox(s.getSubmitterId()));

            // [TCCC-411]
            sd.setRemoved(unbox(s.getStatus().getSubmissionStatusId()) == submissionRemovedStatusId);

            // compute price
            double prizeAmount = 0;
            if (s.getPrizes() != null) {
                for (Prize p : s.getPrizes()) {
                    prizeAmount += p.getAmount();

                    if (p.getPlace() != null && p.getPlace() > 0) {
                        sd.setPlacement(p.getPlace());
                    }
                }
            }
            sd.setPrice(prizeAmount);

            // retrieve SubmissionPayment and check it
            SubmissionPayment p = null;
            try {
                if (submissionPaymentMap != null) {
                    p = submissionPaymentMap.get(s.getSubmissionId());

                    // set this submission as the reference.
                    if (p != null) {
                        p.setSubmission(s);
                    }
                } else {
                    p = submissionManager.getSubmissionPayment(sd.getSubmissionId());
                }
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

                if (s.getReview() != null) {
                    for (SubmissionReview review : s.getReview()) {
                        if (review.getStatus().getReviewStatusId() == reviewFailedStatusId) {
                            sd.setPassedScreening(false);
                            break;
                        }
                    }
                }
            }

            // create content
            String content = submissionContentBaseURI;
            content += "&" + submissionContentSubmissionIdParameterName + "=" + sd.getSubmissionId();
            content += "&" + submissionContentSubmissionTypeParameterName + "="
                    + (sd.isPaidFor() ? submissionContentPaidParameterValue : submissionContentUnpaidParameterValue);
            sd.setSubmissionContent(content);

            // Added: Flex Submission Viewer Overhaul Assembly.
            if (s.getFeedbackText() != null) {
                sd.setFeedbackText(s.getFeedbackText());
            }

            if (s.getFeedbackThumb() != null) {
                sd.setFeedbackThumb(s.getFeedbackThumb());
            }

            // since - TCCC-1219
            if (s.getUserRank() != null) {
                sd.setUserRank(s.getUserRank());
            }

            //
            // @since Complex Submission Viewer Assembly - Part 2
            //
            if (s.getArtifactCount() != null) {
                sd.setArtifactCount(s.getArtifactCount());
            }

            //
            // only for HTML Prototype, Flash, Flex and Widget application
            // submission url need to be constructed.
            // @since Complex Submission Viewer Assembly - Part 2
            //
            String contestType = s.getContest().getContestType().getDescription();
            if (contestType != null
                    && (contestType.equalsIgnoreCase("Prototype") || contestType.equalsIgnoreCase("FLASH")
                            || contestType.equalsIgnoreCase("Widget") || contestType.equalsIgnoreCase("Flex"))) {
                sd.setSubmissionUrl(submissionSiteBaseUrl + "/" + sd.getSubmissionId() + "/" + submissionSiteFilePath);
            }

            result.add(sd);
        }

        return result;
    }

    /**
     * This simple routing used to convert SubmissionData object into Submission.
     *
     * @param submissionData entity to convert
     * @return converted entity
     */
    private Submission convertSubmissionData(SubmissionData submissionData) throws ContestManagementException {
        Submission submission = new Submission();

        submission.setSubmissionId(submissionData.getSubmissionId());
        submission.setSubmitterId(submissionData.getSubmitterId());
        submission.setSubmissionDate(getDate(submissionData.getSubmittedDate()));
        submission.setRank(submissionData.getRank());
        submission.setContest(contestManager.getContest(submissionData.getContestId()));

        // Added: Flex Submission Viewer Overhaul Assembly.
        submission.setFeedbackText(submissionData.getFeedbackText());
        submission.setFeedbackThumb(submissionData.getFeedbackThumb());

        // Added: TCCC-1219
        submission.setUserRank(submissionData.getUserRank());

        return submission;
    }

    /**
     * Method used to create new error regarding to persistence fault in managers. It will log necessary information,
     * create appropriate exception and throw it. In fact, method processes all types of errors except of special
     * (processed by other handlers or in main code).
     *
     * @param message string describing error
     * @param cause error cause, if any
     * @throws PersistenceException always
     */
    private void handlePersistenceError(String message, Throwable cause) throws PersistenceException {
        logError(cause, message);
        PersistenceFault faultBean = new PersistenceFault();
        faultBean.setPersistenceMessage(cause.getMessage());
        throw new PersistenceException(message, faultBean, cause);
    }

    /**
     * Method used to create new error regarding to failed authorization. It will log necessary information, create
     * appropriate exception and throw it. This method will not use used id, related type of error will be processed in
     * other place.
     *
     * @param message string describing error
     * @throws UserNotAuthorizedException always
     */
    private void handleAuthorizationError(String message) {
        logError(message);
        UserNotAuthorizedFault faultBean = new UserNotAuthorizedFault();
        faultBean.setUserIdNotAuthorized(-1);
        throw new UserNotAuthorizedException(message, faultBean);
    }

    /**
     * Method used to create new error regarding to wrong method parameter. It will log necessary information, create
     * appropriate exception and throw it.
     *
     * @param message string describing error
     * @throws IllegalArgumentWSException always
     */
    private void handleIllegalWSArgument(String message) {
        logError(message);
        IllegalArgumentWSFault faultBean = new IllegalArgumentWSFault();
        faultBean.setIllegalArgumentMessage(message);
        throw new IllegalArgumentWSException(message, faultBean);
    }

    /**
     * Method used to create new error regarding to wrong contest id. It will log necessary information, create
     * appropriate exception and throw it.
     *
     * @param cause error cause, if any
     * @param id id of the missed contest
     * @throws ContestNotFoundException always
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
     * This method used to log enter in method. It will persist both method name and it's parameters if any.
     * </p>
     *
     * @param method name of the entered method
     * @param params array containing parameters used to invoke method
     */
    private void logEnter(String method, Object... params) {
        if (log != null) {
            log.log(Level.DEBUG, "Enter method StudioServiceBean.{0} with parameters {1}.", method, Arrays
                    .deepToString(params));
        }
    }

    /**
     * Log the given information with the debug level.
     *
     * @param msg the information to log.
     */
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
     * @param method name of the leaved method
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
     * @param method name of the leaved method
     * @param returnValue value returned from the method
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
     * @param error exception describing error
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
     * @param error exception describing error
     * @param message additional message information
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
     * @param message additional message information
     */
    private void logError(String message) {
        if (log != null) {
            log.log(Level.ERROR, message);
        }
    }

    /**
     * <p>
     * This method used to log arbitrary info.
     * </p>
     *
     * @param message additional message information
     */
    private void logInfo(String message) {
        if (log != null) {
            log.log(Level.INFO, message);
        }
    }

    /**
     * Simple helper to unbox Long value to long. If parameter is null, return -1.
     *
     * @param value Long value to unbox.
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
     * Simple helper to unbox Integer value to int. If parameter is null, return -1.
     *
     * @param value Integer value to unbox.
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
     * Checks web service parameter for obeying given rules (objects are not null and integers are not negative). Throws
     * exception otherwise.
     *
     * @param name name of the parameter to check
     * @param data parameter itself
     * @throws IllegalArgumentWSException when data parameter is null or (data is Long and negative)
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
     * <p>
     * This is going to fetch all the currently available contests.
     * </p>
     *  <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return the list of all available contents (or empty if none found)
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<ContestData> getAllContests(TCSubject tcSubject) throws PersistenceException {
        logEnter("getAllContests", tcSubject.getUserId());
        checkParameter("tcSubject", tcSubject);

        try {
            List<ContestData> result = new ArrayList<ContestData>();
            List<Contest> contests = null;
            if (isRole(tcSubject, ADMIN_ROLE)) {
                logInfo("User is admin.");
                contests = contestManager.getAllContests();
            } else  {
                logInfo("User is user.");
                contests = contestManager.getContestsForUser(tcSubject.getUserId());
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

            logExit("getAllContests", result.size());
            return result;
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while retrieving contest.", e);
        }

        return null;
    }

    /**
     * <p>
     * This is going to fetch all the currently available contests for contest monitor widget.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return the list of all available contents (or empty if none found)
     * @throws PersistenceException if any error occurs when getting contest.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SimpleContestData> getSimpleContestData(TCSubject tcSubject) throws PersistenceException {
        logEnter("getSimpleContestData", tcSubject.getUserId());
        checkParameter("tcSubject", tcSubject);
        try {

            List<SimpleContestData> contests = null;
            if (isRole(tcSubject, ADMIN_ROLE)) {
                logInfo("User is admin.");
                contests = contestManager.getSimpleContestData();
            } else {
                logInfo("User is user.");
                contests = contestManager.getSimpleContestDataForUser(tcSubject.getUserId());
            }
            if (contests == null) {
                contests = new ArrayList<SimpleContestData>();
            }

            logExit("getSimpleContestData", contests.size());

            return contests;

        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while retrieving contest.", e);
        }

        return null;
    }

    /**
     * <p>
     * This is going to fetch all the currently available contests related given project.
     * </p>
     *
     * @param pid the given project id
     * @return the list of all available contents (or empty if none found)
     * @throws PersistenceException if any error occurs when getting contest.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SimpleContestData> getSimpleContestData(long pid) throws PersistenceException {
        logEnter("getSimpleContestData(pid)");

        try {

            List<SimpleContestData> contests;
            contests = contestManager.getSimpleContestData(pid);
            if (contests == null)
                contests = new ArrayList<SimpleContestData>();

            logExit("getSimpleContestData(pid)", contests.size());

            return contests;

        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while retrieving contest.", e);
        }

        return null;
    }

    /**
     * <p>
     * This is going to fetch all the currently available contests for my project widget.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return the list of all available contents (or empty if none found)
     * @throws PersistenceException if any error occurs when getting contest.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SimpleProjectContestData> getSimpleProjectContestData(TCSubject tcSubject) throws PersistenceException {
        logEnter("getSimpleProjectContestData", tcSubject.getUserId());
        checkParameter("tcSubject", tcSubject);
        try {

            List<SimpleProjectContestData> contests = null;

            if (isRole(tcSubject, ADMIN_ROLE)) {
                logInfo("User is admin.");
                contests = contestManager.getSimpleProjectContestData();
            } else  {
                logInfo("User is user.");
                contests = contestManager.getSimpleProjectContestDataForUser(tcSubject.getUserId());
            }

            if (contests == null) {
                contests = new ArrayList<SimpleProjectContestData>();
            }

            logExit("getSimpleProjectContestData", contests.size());

            return contests;

        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while retrieving contest.", e);
        }

        return null;
    }

    /**
     * <p>
     * This is going to fetch all the currently available contests related to given project id.
     * </p>
     *
     * @param pid the given project id
     * @return the list of all available contents (or empty if none found)
     * @throws PersistenceException if any error occurs when getting contest.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SimpleProjectContestData> getSimpleProjectContestData(long pid)
            throws PersistenceException {
        logEnter("getSimpleProjectContestData(pid)");

        try {

            List<SimpleProjectContestData> contests;

            contests = contestManager.getSimpleProjectContestData(pid);

            if (contests == null) {
                contests = new ArrayList<SimpleProjectContestData>();
            }

            logExit("getSimpleProjectContestData(pid)", contests.size());
            return contests;

        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while retrieving contest.", e);
        }

        return null;
    }

    /**
     * <p>
     * This is going to fetch all the currently available contests for my project widget.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return the list of all available contents (or empty if none found)
     * @throws PersistenceException if any error occurs when getting contest.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SimpleContestData> getContestDataOnly(TCSubject tcSubject) throws PersistenceException {
        logEnter("getContestDataOnly", tcSubject.getUserId());
        checkParameter("tcSubject", tcSubject);
        try {
            List<SimpleContestData> contests = null;

            if (isRole(tcSubject, ADMIN_ROLE)) {
                logInfo("User is admin.");
                contests = contestManager.getContestDataOnly();
            } else {
                logInfo("User is user");
                contests = contestManager.getContestDataOnlyForUser(tcSubject.getUserId());
            }
            if (contests == null) {
                contests = new ArrayList<SimpleContestData>();
            }

            logExit("getContestDataOnly", contests.size());
            return contests;

        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while getContestDataOnly.", e);
        }

        return null;
    }

    /**
     * <p>
     * This is going to fetch only contest id and contest name related to given project.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param pid the given project id
     * @return the list of all available contents (only id and name) (or empty if none found)
     * @throws ContestManagementException if any error occurs when getting contest
     * @since 1.1
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SimpleContestData> getContestDataOnly(TCSubject tcSubject, long pid) throws PersistenceException {
        logEnter("getContestDataOnly(tcSubject, pid)");
        checkParameter("tcSubject", tcSubject);
        try {
            List<SimpleContestData> contests = null;
            if (isRole(tcSubject, ADMIN_ROLE)) {
                logInfo("User is admin.");
                contests = contestManager.getContestDataOnly(-1, pid);
            } else {
                logInfo("User is user role.");
                contests = contestManager.getContestDataOnly(tcSubject.getUserId(), pid);
            }

            if (contests == null) {
                contests = new ArrayList<SimpleContestData>();
            }

            logExit("getContestDataOnly(pid)", contests.size());
            return contests;

        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while getContestDataOnly.", e);
        }

        return null;
    }

    /**
     * <p>
     * This is going to get all the matching contest entities that fill the input criteria.
     * </p>
     *
     * @param filter a search filter used as criteria for contests.
     * @return a list (possibly empty) of all the matched contest entities.
     * @throws IllegalArgumentException if the input filter is null or filter is not supported for searching
     * @throws PersistenceException if any error occurs when getting contest.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ContestData> searchContests(Filter filter) throws PersistenceException {
        logEnter("searchContests");

        try {
            List<Contest> contests = contestManager.searchContests(filter);

            List<ContestData> result = new ArrayList<ContestData>();
            for (Contest contest : contests) {
                result.add(convertContest(contest));
            }

            logExit("searchContests", result.size());
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
     * @param submissionId The id of the submission to get
     * @return the submission with the given id, or null if not found
     * @throws PersistenceException If any error occurs during the retrieval
     */
    public SubmissionData retrieveSubmissionData(long submissionId) throws PersistenceException {
        logEnter("retrieveSubmissionData");

        try {
            Submission submission = submissionManager.getSubmission(submissionId);

            ArrayList<Submission> submissions = new ArrayList<Submission>();
            submissions.add(submission);
            SubmissionData result = convertSubmissions(submissions, null).get(0);

            logExit("retrieveSubmissionData", result);
            return result;
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManager reports error while retrieving submission.", e);
        }

        return null;
    }

    /**
     * <p>
     * Gets the submission with the given id.
     * </p>
     *
     * @param submissionId The id of the submission to get
     * @return the submission with the given id, or null if not found
     * @throws PersistenceException If any error occurs during the retrieval
     */
    public Submission retrieveSubmission(long submissionId) throws PersistenceException {
        logEnter("retrieveSubmission");

        try {
            Submission submission = submissionManager.getSubmission(submissionId);

            logExit("retrieveSubmission", submission);
            return submission;
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
     * @throws PersistenceException if any error occurs when getting contest.
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

            logExit("getAllContestTypes", result.size());
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
     * @param documentId document id, which document will be linked.
     * @param contestId contest id, which contest will be linked.
     * @throws IllegalArgumentWSException if any id is less than 0
     * @throws PersistenceException if some persistence errors occur
     * @throws ContestNotFoundException if the contest is not found.
     */
    public void addDocumentToContest(long documentId, long contestId) throws PersistenceException,
            ContestNotFoundException {
        logEnter("addDocumentToContest");

        checkParameter("contestId", contestId);
        checkParameter("documentId", documentId);

        try {
            getContest(contestId);

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
     * @param uploadedDocument the uploadDocument to update
     * @return the same instance passed in argument with the documentId updated
     * @throws IllegalArgumentWSException if the argument is null
     * @throws PersistenceException if some persistence errors occur
     */
    public UploadedDocument uploadDocument(UploadedDocument uploadedDocument)
            throws PersistenceException {
        logEnter("uploadDocument(UploadedDocument data)");

        checkParameter("uploadedDocument", uploadedDocument);

        Document doc = new Document();

        doc.setDocumentId(uploadedDocument.getDocumentId());
        doc.setOriginalFileName(uploadedDocument.getFileName());

        // set file path
        FilePath fp = new FilePath();

        // [BUG TCCC-134]
        fp.setPath(documentBasePath);

        doc.setPath(fp);

        try {

            // set mime type [BUG 27074484-15]
            MimeType mt = contestManager.getMimeType(uploadedDocument.getMimeTypeId());
            doc.setMimeType(mt);

            // set document type [BUG 27074484-15]
            DocumentType dt = contestManager.getDocumentType(uploadedDocument.getDocumentTypeId());
            doc.setType(dt);
            doc.setDescription(uploadedDocument.getDescription());

            // save document
            doc = contestManager.addDocument(doc);
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while adding document in persistence.", e);
        }
        // persist document content where necessary
        try {
            if (!contestManager.existDocumentContent(unbox(doc.getDocumentId()))) {
                contestManager.saveDocumentContent(doc.getDocumentId(), uploadedDocument.getFile());
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
     * @param documentId the id of document to remove
     * @throws IllegalArgumentWSException if the documentId is less than 0
     * @throws PersistenceException if some persistence errors occur
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
     * @param contentType .
     * @return the matched MimeType id. Id for defaultMimeType if not found. -1 if there is no such type as
     *         defaultMimeType
     * @throws PersistenceException if any error occurs when getting MimeType.
     * @throws IllegalArgumentWSException if the argument is null or empty
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
                } // BUGR-662
                else if (defaultMimeType != null && mime.getDescription().equals(defaultMimeType)) {
                    ret = mime.getMimeTypeId();
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
     * @param submissionId the id of submission to purchase.
     * @param payPalOrderId PayPal order id.
     * @param username the username.
     * @throws PersistenceException if any error occurs when purchasing submission.
     * @throws IllegalArgumentWSException if the submissionId is less than 0 or price is negative.
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

            submissionPayment.setPrice(calculateSubmissionPaymentAmount(contest, submission));
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
     * @param contestPaymentData the contest payment to create
     * @param username username.
     * @return the created contest payment.
     * @throws IllegalArgumentException if the arg is null.
     * @throws PersistenceException if any other error occurs.
     */
    public ContestPaymentData createContestPayment(ContestPaymentData contestPaymentData,
            String username) throws PersistenceException {
        logEnter("createContestPayment", contestPaymentData);
        checkParameter("contestPaymentData", contestPaymentData);
        checkParameter("username", username);

        try {
            ContestPayment contestPayment = convertContestPaymentData(contestPaymentData);
            contestPayment = contestManager.createContestPayment(contestPayment);
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while creating new ContestPayment.", e);
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManager reports error while creating new ContestPayment.", e);
        }

        logExit("createContestPayment", contestPaymentData);
        return contestPaymentData;
    }

    /**
     * <p>
     * Gets contest payment by id, and return the retrieved contest payment. If the contest payment doesn't exist, null
     * is returned.
     * </p>
     *
     * @param contestPaymentId the contest payment id
     * @return the retrieved contest, or null if id doesn't exist
     * @throws PersistenceException if any error occurs when getting contest.
     * @since BUGR-1363 changed method signature
     */
    public List<ContestPaymentData> getContestPayments(long contestId) throws PersistenceException {
        logEnter("getContestPayments", contestId);
        checkParameter("contestPaymentId", contestId);

        try {
            List<ContestPayment> contestPayment = contestManager.getContestPayments(contestId);
            if (contestPayment == null) {
                throw new EntityNotFoundException("ContestPayment with id " + contestId + " is not found.");
            }

            List<ContestPaymentData> result = new ArrayList<ContestPaymentData>();
            for (ContestPayment cp : contestPayment) {
                result.add(convertContestPayment(cp));
            }
            logExit("getContestPayments", result);
            return result;
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while retrieving contest payment.", e);
        }

        // never reached
        return null;
    }

    /**
     * <p>
     * Updates contest payment data.
     * </p>
     *
     * @param contestPayment the contest payment to update
     * @throws IllegalArgumentException if the argument is null.
     * @throws EntityNotFoundException if the contest payment doesn't exist in persistence.
     * @throws ContestManagementException if any error occurs when updating contest payment.
     */
    public void editContestPayment(ContestPaymentData contestPayment) throws PersistenceException {
        logEnter("editContestPayment", contestPayment);
        checkParameter("contestPayment", contestPayment);

        try {
            contestManager.editContestPayment(convertContestPaymentData(contestPayment));

        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while updating contest payment.", e);
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManager reports error while updating contest payment.", e);
        }

        logExit("editContestPayment");
    }

    /**
     * <p>
     * Removes contest payment, return true if the contest payment exists and removed successfully, return false if it
     * doesn't exist.
     * </p>
     *
     * @param contestPaymentId the contest payment id
     * @return true if the contest payment exists and removed successfully, return false if it doesn't exist
     * @throws PersistenceException if any error occurs.
     */
    public boolean removeContestPayment(long contestPaymentId) throws PersistenceException {
        logEnter("removeContestPayment", contestPaymentId);
        checkParameter("contestPaymentId", contestPaymentId);

        try {
            boolean ret = contestManager.removeContestPayment(contestPaymentId);
            logExit("removeContestPayment");
            return ret;
        } catch (EntityNotFoundException ex) {
            // no such document in persistence
            String message = "can't find contest payment with id " + contestPaymentId + " in persistence.";
            logError(ex, message);
            handlePersistenceError("can't find contest payment with id " + contestPaymentId + " in persistence.", ex);
        } catch (ContestManagementException ex) {
            handlePersistenceError("ContestManager reports error while removing document from contest.", ex);
        }

        return false;
    }

    /**
     * This method used to convert ContestPaymentData object into ContestPayment object.
     *
     * @param data ContestPaymentData object to convert.
     * @return converted ContestPayment instance
     * @throws PersistenceException when error reported by manager
     * @throws ContestManagementException when error reported by manager
     * @throws SubmissionManagementException when error reported by manager
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
        // BUGR-1076
        result.setPaymentReferenceId(data.getPaymentReferenceId());
        PaymentType paymentType = contestManager.getPaymentType(data.getPaymentTypeId());
        result.setPaymentType(paymentType);

        return result;
    }

    /**
     * This method converts ContestPayment object into ContestPaymentData object.
     *
     * @param contest ContestPayment instance to convert
     * @return converted ContestPaymentDate object
     * @throws ContestManagementException error occurred from ContestManager
     */
    private ContestPaymentData convertContestPayment(ContestPayment contestPayment) throws ContestManagementException {
        ContestPaymentData contestPaymentData = new ContestPaymentData();
        contestPaymentData.setContestId(contestPayment.getContestId());
        contestPaymentData.setPaymentStatusId(contestPayment.getStatus().getPaymentStatusId());
        contestPaymentData.setPaypalOrderId(contestPayment.getPayPalOrderId());
        contestPaymentData.setPrice(contestPayment.getPrice());
        // BUGR-1076
        contestPaymentData.setPaymentReferenceId(contestPayment.getPaymentReferenceId());
        contestPaymentData.setPaymentTypeId(contestPayment.getPaymentType().getPaymentTypeId());
        return contestPaymentData;
    }

    /**
     * <p>
     * This is going to fetch all the currently available media.
     * </p>
     *
     * @return the list of all available mediums (or empty if none found)
     * @throws PersistenceException if any error occurs when getting medium.
     */
    public List<MediumData> getAllMediums() throws PersistenceException {
        logEnter("getAllMediums");
        try {
            List<MediumData> result = new ArrayList<MediumData>();
            for (Medium medium : contestManager.getAllMedia()) {
                MediumData data = new MediumData();
                data.setDescription(medium.getDescription());
                data.setMediumId(medium.getMediumId());

                result.add(data);
            }

            logExit("getAllMediums", result.size());
            return result;
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManagement reports error while retrieving Mediums.", e);
        }

        return null;
    }

    /**
     * Set submission placement.
     *
     * @param submissionId Submission Id.
     * @param placement placement
     * @throws PersistenceException if any error occurs when setting placement.
     * @since TCCC-353
     */
    public void setSubmissionPlacement(long submissionId, int placement)
            throws PersistenceException {
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
     * @param submission Submission.
     * @param placement placement
     * @throws PersistenceException if any error occurs when setting placement.
     * @since TCCC-353
     */
    private void setSubmissionPlacement(Submission submission, int placement) throws PersistenceException {
        logEnter("setSubmissionPlacement", submission, placement);
        checkParameter("submission", submission);

        Prize prizeToAdd = new Prize();
        prizeToAdd.setAmount(0d);
        prizeToAdd.setPlace(placement);

        setSubmissionPlacement(submission, prizeToAdd);
    }

    /**
     * Set submission placement.
     *
     * @param submissionId Submission Id.
     * @param prizeId prize id.
     * @throws PersistenceException if any error occurs when setting placement.
     * @since BUGR-455
     */
    public void setSubmissionPlacement(long submissionId, long prizeId)
            throws PersistenceException {
        logEnter("setSubmissionPlacement", submissionId, prizeId);
        checkParameter("submissionId", submissionId);
        checkParameter("prizeId", prizeId);

        try {
            Prize prize = submissionManager.getPrize(prizeId);
            Submission submission = submissionManager.getSubmission(submissionId);
            if (prize == null) {
                String message = "Prize not found. prize id: " + prizeId;
                logDebug(message);
                logExit("setSubmissionPlacement");
                throw new PersistenceException(message, message);
            }
            if (submission == null) {
                String message = "submission not found. submission id: " + submissionId;
                logDebug(message);
                logExit("setSubmissionPlacement");
                throw new PersistenceException(message, message);
            }
            if (!prize.getContests().isEmpty()) {
                Long contestIdAssociatedToPrize = prize.getContests().toArray(new Contest[] {})[0].getContestId();
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
                    logDebug(MessageFormat.format("Prize has been associated to another submission. "
                            + "prize id: {0}, another submission id: {1}", prizeId, s.getSubmissionId()));
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
                String message = "submission not found. submission id: " + submissionId;
                logDebug(message);
                logExit("setSubmissionPlacement");
                throw new PersistenceException(message, message);
            }

            this.setSubmissionPlacement(submission, prize);

            logExit("setSubmissionPlacement");
            return;
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManagement reports error.", e);
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManagement reports error.", e);
        }
    }

    /**
     * Set submission placement.
     *
     * @param submissionId submission id.
     * @param prize prize.
     * @throws PersistenceException if any error occurs when setting placement.
     */
    protected void setSubmissionPlacement(Submission submission, Prize prize) throws PersistenceException {
        // Need to create new prize when prizeId is null.
        Long prizeId = prize.getPrizeId();
        try {
            if (prizeId == null) {
                PrizeType prizeType = contestManager.getPrizeType(this.contestPrizeTypeId);
                prize.setType(prizeType);
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

            if (contestManager.findContestResult(submission.getSubmissionId(), contestId) == null) {
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
                    submissionManager.addPrizeToSubmission(submission.getSubmissionId(), prizeToAdd.getPrizeId());

                    // For topCoder direct, payment is done automatically
                    // STUDIO-217
                    if (contest.getContestChannel().getContestChannelId() == 2) {
                        addPayment(submission, prizeToAdd);
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
            submissionManager.addPrizeToSubmission(submission.getSubmissionId(), prize.getPrizeId());

            // For topCoder direct, payment is done automatically
            // STUDIO-217
            if (contest.getContestChannel().getContestChannelId() == 2) {
                addPayment(submission, prize);
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
     * Awards milestone prize to submission.
     *
     * @param submission the submission to award the prize
     * @throws PersistenceException if any error occurs when awarding prize.
     * @since 1.5
     */
    private void awardMilestonePrize(Submission submission) throws PersistenceException {
        logEnter("awardMilestonePrize", submission.getSubmissionId());

        try {
            // If the submission is already associated to a milestone prize, we are all set.
            Set<MilestonePrize> prizes = submission.getMilestonePrizes();
            if (prizes.size() > 0) {
                logDebug("Milestone prize already found in submission " + submission.getSubmissionId());
                logExit("awardMilestonePrize");
                return;
            }

            // get the contest for the submission
            Contest contest = submission.getContest();

            // get milestone prize reference
            if (contest.getMilestonePrize() == null) {
                String message = "Milestone prize not found for contest id: " + contest.getContestId();
                logDebug(message);
                logExit("awardMilestonePrize");
                throw new PersistenceException(message, message);
            }

            // add prize
            submissionManager.addMilestonePrizeToSubmission(submission.getSubmissionId(), contest.getMilestonePrize()
                    .getMilestonePrizeId());

            // For topCoder direct, payment is done automatically
            if (contest.getContestChannel().getContestChannelId() == 2) {
                addMilestonePayment(submission);
            }

            logExit("awardMilestonePrize");
            return;
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManagement reports error.", e);
        }
    }

    /**
     * Marks submission for purchase.
     *
     * @param submissionId Submission Id.
     * @throws PersistenceException if any error occurs when marking for purchase.
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
     * @param history Change history entity to be added.
     * @throws PersistenceException if any other error occurs.
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
     * @param contestId contest id to search for.
     * @return Change history entities match the contest id.
     * @throws PersistenceException if any other error occurs.
     */
    public List<ChangeHistoryData> getChangeHistory(long contestId) throws PersistenceException {
        logEnter("getChangeHistory");
        try {
            ArrayList<ChangeHistoryData> result = new ArrayList<ChangeHistoryData>();
            for (ContestChangeHistory ch : contestManager.getChangeHistory(contestId)) {
                ChangeHistoryData data = getChangeHistoryData(ch);
                result.add(data);
            }

            logExit("getChangeHistory", result.size());
            return result;
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManagement reports error while retrieving ChangeHistories.", e);
        }

        return null;
    }

    /**
     * Returns latest change history entity list.
     *
     * @param contestId contest id to search for.
     * @return Latest change history entities match the contest id and transaction id.
     * @throws PersistenceException if any other error occurs.
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

            logExit("getLatestChanges", result.size());
            return result;
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManagement reports error while retrieving latest ChangeHistories.", e);
        }

        return null;
    }

    /**
     * get the change history data.
     *
     * @param ch the ContestChangeHistory.
     * @return the ChangeHistoryData.
     */
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

    /**
     * Get the change history with the given ChangeHistoryData.
     *
     * @param ch the ChangeHistoryData.
     * @return the ContestChangeHistory.
     */
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
     * Delete contest.
     *
     * @param contestId contest id to delete.
     * @throws PersistenceException if any other error occurs.
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
     * This is going to fetch all the currently available contests. This method only return values used in my project
     * widget.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return the list of all available contents (or empty if none found)
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<ContestData> getAllContestHeaders(TCSubject tcSubject) throws PersistenceException {
        logEnter("getAllContestHeaders", tcSubject.getUserId());
        checkParameter("tcSubject", tcSubject);
        try {
            List<ContestData> result = new ArrayList<ContestData>();
            List<Contest> contests = new ArrayList<Contest>();
            if (isRole(tcSubject, ADMIN_ROLE)) {
                logInfo("User is admin.");
                contests = contestManager.getAllContests();
            } else {
                logInfo("User  is non-admin.");
                contests = contestManager.getContestsForUser(tcSubject.getUserId());
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

            logExit("getAllContestHeaders", result.size());
            return result;
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while retrieving contest.", e);
        }

        return null;
    }

    /**
     * Add payment.
     *
     * @param submission Submission.
     * @param prize prize.
     * @throws PersistenceException if any error occurs when adding payment.
     * @since BUGR-456
     */
    protected void addPayment(Submission submission, Prize prize) throws PersistenceException {
        logEnter("addPayment");
        checkParameter("submission", submission);
        checkParameter("prize", prize);

        if (prize.getAmount() == 0) {
            logInfo("Payments with amount of 0 are not sent to PACTS");
            logExit("addPayment");
            return;
        }

        if (autoPaymentsEnabled) {
            PactsServicesLocator.setProviderUrl(pactsServiceLocation);

            try {
                BasePayment payment = BasePayment.createPayment(com.topcoder.web.ejb.pacts.Constants.TC_STUDIO_PAYMENT,
                        submission.getSubmitterId(), prize.getAmount(), submission.getContest().getContestId(), prize
                                .getPlace() == null ? 0 : prize.getPlace());

                String client = "";
                for (ContestConfig config : submission.getContest().getConfig()) {
                    if (config.getId().getProperty().getPropertyId() == contestPropertyCreateUserHandleId) {
                        client = (config.getValue());
                        break;
                    }
                }

                payment.setClient(client);

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
     * Adds a milestone prize payment.
     *
     * @param submission the submission being paid the milestone prize.
     * @throws PersistenceException if any error occurs when adding milestone payment.
     * @since 1.5
     */
    private void addMilestonePayment(Submission submission) throws PersistenceException {
        logEnter("addMilestonePayment");
        checkParameter("submission", submission);

        if (submission.getContest().getMilestonePrize().getAmount() == 0) {
            logInfo("Payments with amount of 0 are not sent to PACTS");
            logExit("addMilestonePayment");
            return;
        }

        if (autoPaymentsEnabled) {
            PactsServicesLocator.setProviderUrl(pactsServiceLocation);

            try {
                BasePayment payment = BasePayment.createPayment(com.topcoder.web.ejb.pacts.Constants.TC_STUDIO_PAYMENT,
                        submission.getSubmitterId(), submission.getContest().getMilestonePrize().getAmount(),
                        submission.getContest().getContestId(), 0);

                String client = "";
                for (ContestConfig config : submission.getContest().getConfig()) {
                    if (config.getId().getProperty().getPropertyId() == contestPropertyCreateUserHandleId) {
                        client = (config.getValue());
                        break;
                    }
                }

                payment.setClient(client);

                payment = PactsServicesLocator.getService().addPayment(payment);

                submission.setPaymentId(payment.getId());
                submissionManager.updateSubmission(submission);

                logExit("addMilestonePayment");
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
     * Send payments to PACTS for all unpaid submissions with a prize already assigned This service is not atomic. If it
     * fails, you'll have to check what payments where actually done trough the submission.paid flag.
     *
     * @param contestId Contest Id.
     * @throws PersistenceException if any error occurs when processing a payment.
     * @since STUDIO-217
     */
    public void processMissingPayments(long contestId) throws PersistenceException {
        logEnter("processMissingPayments", contestId);
        checkParameter("contestId", contestId);

        try {

            List<Submission> submissionsForContest = submissionManager.getSubmissionsForContest(contestId, true);

            for (Submission submission : submissionsForContest) {
                if (submission.getPaymentId() == null && !submission.getPrizes().isEmpty()) {
                    Prize[] prizes = new Prize[10];
                    Prize unpaidPrize = submission.getPrizes().toArray(prizes)[0];
                    addPayment(submission, unpaidPrize);
                }
            }
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManagement reports error.", e);
        }
    }

    /**
     * <p>
     * Gets all studio file types to return. If no studio file type exists, return an empty list
     * </p>
     *
     * @return a list of studio file types
     * @throws PersistenceException if any error occurs when getting studio file types.
     */
    public List<StudioFileType> getAllStudioFileTypes() throws PersistenceException {

        logEnter("getAllStudioFileTypes");

        try {
            return contestManager.getAllStudioFileTypes();

        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManagementException reports error.", e);
        }

        return null;
    }

    /**
     * <p>
     * Get all the DocumentType objects.
     * </p>
     *
     * @return the list of all available DocumentType
     * @throws PersistenceException if any error occurs when getting contest
     * @since 1.1.2
     */
    public List<DocumentType> getAllDocumentTypes() throws PersistenceException {

        logEnter("getAllDocumentTypes");

        try {
            return contestManager.getAllDocumentTypes();

        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManagementException reports error.", e);
        }

        return null;
    }

    /**
     * Get all payment types.
     *
     * @return the list of all payment types.
     * @throws PersistenceException if any error occurs when getting all payment types
     */
    public List<PaymentType> getAllPaymentTypes() throws PersistenceException {
        logEnter("getAllPaymentTypes");

        try {
            return contestManager.getAllPaymentTypes();

        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManagementException reports error.", e);
        }

        return null;
    }

    /**
     * <p>
     * Purchase submission.
     * </p>
     * <p>
     * set the price of submission. create an entry at submission payment table
     * </p>
     *
     * @param submissionId the id of submission to purchase.
     * @param payment the submission payment data
     * @param username the username.
     * @throws PersistenceException if any error occurs when purchasing submission.
     * @throws IllegalArgumentWSException if the submissionId is less than 0 or price is negative.
     */
    public void purchaseSubmission(long submissionId, SubmissionPaymentData payment,
            String username) throws PersistenceException {
        logEnter("purchaseSubmission", submissionId, payment);
        checkParameter("submissionId", submissionId);
        checkParameter("payment", payment);
        checkParameter("username", username);

        try {
            Submission submission = submissionManager.getSubmission(submissionId);
            // get the contest that the submission belongs to
            if (submission == null) {
                handleIllegalWSArgument("Submission with id " + submissionId + " is not found.");
            }

            this.purchaseSubmission(submission, payment, username);
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManager reports error.", e);
        }

        logExit("purchaseSubmission");
    }

    /**
     * <p>
     * Purchase submission.
     * </p>
     * <p>
     * set the price of submission. create an entry at submission payment table
     * </p>
     *
     * @param submission the submission to purchase.
     * @param payment the submission payment data
     * @param username the username.
     * @throws PersistenceException if any error occurs when purchasing submission.
     * @throws IllegalArgumentWSException if the submissionId is less than 0 or price is negative.
     */
    private void purchaseSubmission(Submission submission, SubmissionPaymentData payment, String username)
            throws PersistenceException {
        logEnter("purchaseSubmission", submission, payment);
        checkParameter("submission", submission);
        checkParameter("payment", payment);
        checkParameter("username", username);

        try {

            Contest contest = submission.getContest();

            SubmissionPayment submissionPayment = submissionManager.getSubmissionPayment(submission.getSubmissionId());
            submissionPayment.setPrice(calculateSubmissionPaymentAmount(contest, submission));
            submissionPayment.setPayPalOrderId(payment.getPaymentReferenceNumber());
            submissionPayment.setPaymentReferenceId(payment.getPaymentReferenceNumber());
            PaymentType type = contestManager.getPaymentType(payment.getPaymentTypeId());
            if (type == null) {
                throw new ContestManagementException("PaymentType with id " + payment.getPaymentTypeId()
                        + " is missing.");
            }
            submissionPayment.setPaymentType(type);
            PaymentStatus status = contestManager.getPaymentStatus(payment.getPaymentStatusId());
            if (status == null) {
                throw new ContestManagementException("PaymentStatus with id " + payment.getPaymentStatusId()
                        + " is missing.");
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
     * Calculates the payment amount for the specified submission.
     *
     * @param contest the contest the submission belongs to
     * @param submission the submission to calculate
     * @return the payment amount
     * @throws PersistenceException when error reported by manager
     * @throws ContestManagementException when error reported by manager
     * @throws SubmissionManagementException when error reported by manager
     */
    private double calculateSubmissionPaymentAmount(Contest contest, Submission submission)
            throws ContestManagementException, SubmissionManagementException, PersistenceException {
        // get the first place prize amount for the contest
        Double firstPlacePrize = null;

        for (Prize prize : contestManager.getContestPrizes(contest.getContestId())) {
            if (prize.getPlace() != null && prize.getPlace().equals(1)) {
                firstPlacePrize = prize.getAmount();
                break;
            }
        }

        double amount = 0;

        if (firstPlacePrize == null) {
            throw new ContestManagementException("There must be a first placement prize for the contest. Contest id: "
                    + contest.getContestId());
        }

        // check if the submission has a winner prize
        boolean isWinner = false;
        for (Prize p : submission.getPrizes()) {
            if (p.getType().getPrizeTypeId() == contestPrizeTypeId && p.getAmount() > 0) {
                isWinner = true;
                amount = p.getAmount();
                break;

            }
        }

        // if it doesn't have a winner prize, it's an additional purchase.
        if (!isWinner) {
            // BUGR-1328
            // get the second place prize amount for the contest
            Double secondPlacePrize = null;

            for (Prize prize : contestManager.getContestPrizes(contest.getContestId())) {
                if (prize.getPlace() != null && prize.getPlace().equals(2)) {
                    secondPlacePrize = prize.getAmount();
                    break;
                }
            }

            if (secondPlacePrize == null) {
                throw new ContestManagementException(
                        "There must be a second placement prize for the contest. Contest id: " + contest.getContestId());
            }

            amount = secondPlacePrize;

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

            submissionManager.addPrizeToSubmission(submission.getSubmissionId(), prize.getPrizeId());

            // BUGR-1328
            // for not winner, need to call pacts
            if (contest.getContestChannel().getContestChannelId() == 2) {
                addPayment(submission, prize);
            }
        }
        return amount;
    }

    /**
     * <p>
     * This method ranks and purchases submission. If placement > 0, then it ranks the submission. If paymentData !=
     * null, then it purchases the submission. This method will also award milestone prize when corresponds.
     * </p>
     *
     * @param submissionId a <code>long</code> id of submission
     * @param placement a <code>int</code> placement
     * @param paymentData a <code>SubmissionPaymentData</code>
     * @param userId a <code>String</code> user identifier.
     * @throws PersistenceException when error reported by manager
     */
    public void rankAndPurchaseSubmission(long submissionId, int placement,
            SubmissionPaymentData paymentData, String userId) throws PersistenceException {
        try {
            Submission submission = submissionManager.getSubmission(submissionId);
            if (submission == null) {
                String message = "submission not found. submission id: " + submissionId;
                logDebug(message);
                logExit("rankAndPurchaseSubmission");
                throw new PersistenceException(message, message);
            }

            if (paymentData != null) {
                // award milestone prize when corresponds
                if (paymentData.getAwardMilestonePrize()) {
                    this.awardMilestonePrize(submission);
                }
            }

            if (placement > 0) {
                this.setSubmissionPlacement(submission, placement);
            }

            if (paymentData != null) {

                // purchase submission when corresponds
                if (paymentData.isPurchased()) {
                    this.purchaseSubmission(submission, paymentData, userId);
                }
            }

            logExit("rankAndPurchaseSubmission");
            return;
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManagement reports error.", e);
        }
    }

    /**
     * <p>
     * Updates the submission feedback
     * </p>
     *
     * @param feedback a <code>SubmissionFeedback</code> the feedback
     * @throws PersistenceException when error reported by manager
     */
    public void updateSubmissionFeedback(SubmissionFeedback feedback) throws PersistenceException {
        try {
            long submissionId = feedback.getSubmissionId();

            Submission submission = submissionManager.getSubmission(submissionId);
            if (submission == null) {
                String message = "submission not found. submission id: " + submissionId;
                logDebug(message);
                logExit("rankAndPurchaseSubmission");
                throw new PersistenceException(message, message);
            }

            this.submissionManager.updateSubmissionFeedback(feedback.getSubmissionId(), feedback.getFeedbackText(),
                    feedback.getFeedbackThumb());
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManagement reports error.", e);
        }
    }

    /**
     * <p>
     * Updates the submission user rank. If the isRankingMilestone flag is true, the rank will target milestone
     * submissions.
     * </p>
     *
     * @param submissionId a <code>long</code> id of the submission
     * @param rank a <code>int</code> rank of the submission.
     * @param isRankingMilestone true if the user is ranking milestone submissions.
     * @throws PersistenceException when error reported by manager
     * @since TCCC-1219
     */
    public void updateSubmissionUserRank(long submissionId, int rank, Boolean isRankingMilestone)
            throws PersistenceException {
        try {
            Submission submission = submissionManager.getSubmission(submissionId);
            if (submission == null) {
                String message = "submission not found. submission id: " + submissionId;
                logDebug(message);
                logExit("updateSubmissionUserRank");
                throw new PersistenceException(message, message);
            }
            logDebug("Updating submission user rank for submission id: " + submissionId + ", rank: " + rank);
            this.submissionManager.updateSubmissionUserRank(submissionId, rank, isRankingMilestone);
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManagement reports error.", e);
        }
    }

    /**
     * <p>
     * Gets the list of project, contest and their read/write/full permissions.
     * </p>
     * Comment added for Cockpit Project Admin Release Assembly v1.0
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param createdUser the specified user for which to get the permission
     * @return the list of project, contest and their read/write/full permissions.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SimpleProjectPermissionData> getSimpleProjectPermissionDataForUser(TCSubject tcSubject, long createdUser)
            throws PersistenceException {
        logEnter("SimpleProjectPermissionDataForUser", tcSubject.getUserId());
        checkParameter("tcSubject", tcSubject);
        try {
            List<SimpleProjectPermissionData> contests = null;
            if (createdUser < 0) {
                // retrieve data for current user
                if (isRole(tcSubject, ADMIN_ROLE)) {
                    logInfo("User is admin.");
                    contests = contestManager.getSimpleProjectPermissionDataForUser(-1);
                } else {
                    logInfo("User  is non-admin.");
                    contests = contestManager.getSimpleProjectPermissionDataForUser(tcSubject.getUserId());
                }
            } else {
                contests = contestManager.getSimpleProjectPermissionDataForUser(createdUser);
            }
            if (contests == null)
                contests = new ArrayList<SimpleProjectPermissionData>();

            logExit("SimpleProjectPermissionDataForUser", contests.size());
            return contests;

        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while getSimpleProjectPermission.", e);
        }

        return null;
    }

    /**
     * <p>
     * Retrieves the list of users whose handle contains the specified key.
     * </p>
     * Comment added for Cockpit Project Admin Release Assembly v1.0
     *
     * @param specified key to search for.
     * @return the list of users.
     */
    public List<User> searchUser(String key) throws PersistenceException {
        logEnter("searchUser(" + key + ")");

        try {
            List<User> users = contestManager.searchUser(key);
            if (users == null)
                users = new ArrayList<User>();

            logExit("searchUser(" + key + ")");
            return users;

        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while search user.", e);
        }

        return null;
    }

    /**
     * Retrieves the list of data for contests for which the user with the given name is a resource. Returns an empty
     * list if no contests are found.
     *
     * @throws IllegalArgumentException if username is null or empty
     * @throws PersistenceException when any other error occurs
     * @param username the name of the user
     * @return the list of found contests data (empty list of none found).
     * @since 1.3
     */
    public List<ContestData> getUserContests(String username) throws PersistenceException {
        logEnter("getUserContests(username)");
        List<ContestData> result = new ArrayList<ContestData>();
        try {
            ExceptionUtils.checkNullOrEmpty(username, null, null, "username can not be null or empty.");
            List<Contest> contests = contestManager.getUserContests(username);

            for (Contest contest : contests) {
                ContestData contestData = convertContest(contest);
                result.add(contestData);
            }

        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManagementException reports error.", e);
        }
        logExit("getUserContests(username)");
        return result;
    }

    /**
     * Gets the milestone submissions for the given contest. Returns an empty list if none found.
     *
     * @throws PersistenceException if any error occurs during the retrieval
     * @param contestId the id of the contest for which milestone submission data should be retrieved
     * @return the list of retrieved submission data, an empty list if none found.
     * @since 1.3
     */
    public List<SubmissionData> getMilestoneSubmissionsForContest(long contestId)
            throws PersistenceException {
        logEnter("getMilestoneSubmissionsForContest(contestId)");
        List<Submission> submissions = null;
        List<SubmissionPayment> submissionPayments = null;
        try {
            submissions = submissionManager.getMilestoneSubmissionsForContest(contestId);
            submissionPayments = submissionManager.getSubmissionPaymentsForContest(contestId);

        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManagementException reports error.", e);
        }

        // convert the submissions.
        List<SubmissionData> result = convertSubmissions(submissions, submissionPayments);
        logExit("getMilestoneSubmissionsForContest(contestId)");
        return result;
    }

    /**
     * Gets the final submissions for the given contest. Returns an empty list if none found.
     *
     * @throws PersistenceException if any error occurs during the retrieval
     * @param contestId the id of the contest for which submission data should be retrieved
     * @return the list of retrieved submission data, an empty list if none found.
     * @since 1.3
     */
    public List<SubmissionData> getFinalSubmissionsForContest(long contestId)
            throws PersistenceException {
        logEnter("getFinalSubmissionsForContest(contestId)");
        List<Submission> submissions = null;
        List<SubmissionPayment> submissionPayments = null;
        try {
            submissions = submissionManager.getFinalSubmissionsForContest(contestId);
            submissionPayments = submissionManager.getSubmissionPaymentsForContest(contestId);

        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManagementException reports error.", e);
        }

        List<SubmissionData> result = convertSubmissions(submissions, submissionPayments);
        ;

        logExit("getFinalSubmissionsForContest(contestId)");
        return result;
    }

    /**
     * Sets the specified milestone prize for submission with the given ID.
     *
     * @param submissionId the ID of the submission.
     * @param milestonePrizeId the ID of the milestone prize.
     * @throws PersistenceException if any error occurs.
     * @throws IllegalArgumentException If the given submission has already been associated with the given milestone
     *             prize before, OR the submission type is not milestone submission.
     * @since 1.3
     */
    public void setSubmissionMilestonePrize(long submissionId, long milestonePrizeId)
            throws PersistenceException {

        logEnter("setSubmissionMilestonePrize(submissionId, milestonePrizeId)");
        try {
            submissionManager.setSubmissionMilestonePrize(submissionId, milestonePrizeId);
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManagementException reports error.", e);
        }
        logExit("setSubmissionMilestonePrize(submissionId, milestonePrizeId)");
    }

    /**
     * Gets the list of simple pipeline data within between specified start and end date.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param startDate the start of date range within which pipeline data for contests need to be fetched.
     * @param endDate the end of date range within which pipeline data for contests need to be fetched.
     * @param overdueContests whether to include overdue contests or not.
     * @return the list of simple pipeline data for specified user id and between specified start and end date.
     * @throws ContestManagementException if error during retrieval from database.
     * @since 1.0.1
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SimplePipelineData> getSimplePipelineData(TCSubject tcSubject, Date startDate, Date endDate,
            boolean overdueContests) throws PersistenceException {
        logEnter("getSimplePipelineData", startDate, endDate, overdueContests);
        checkParameter("tcSubject", tcSubject);
        try {
            List<SimplePipelineData> pipelineDatas = contestManager.getSimplePipelineData(tcSubject.getUserId(),
                    startDate, endDate, overdueContests);

            logExit("getSimplePipelineData", pipelineDatas.size());
            return pipelineDatas;

        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while getSimplePipelineData.", e);
        }

        return null;
    }

    /**
     * Retrieves a list of capacity data (date, number of scheduled contests) for the given contest type starting from
     * tomorrow.
     *
     * @param contestType the contest type
     * @return the list of capacity data
     * @throws PersistenceException if any error occurs during retrieval of information.
     * @since 1.3.1
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<StudioCapacityData> getCapacity(int contestType) throws PersistenceException {
        logEnter("getCapacity(" + contestType + ")");

        try {
            logExit("getCapacity()");
            return contestManager.getCapacity(contestType);
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while getting capacity.", e);
        }

        return null;
    }

    /**
     * check contest permission, check if a user has permission (read or write) on a contest
     *
     * @param contestId the contest id
     * @param projectId tc direct project id
     * @param readonly check read or write permission
     * @param userId user id
     * @return true/false
     */
    public boolean checkContestPermission(long contestId, long projectId, boolean readonly, long userId)
        throws PersistenceException {
        logEnter("checkContestPermission(" + contestId + ", " + projectId + ", " + readonly + ", " + userId + ")");
        try {
            logExit("checkContestPermission()");
            return contestManager.checkContestPermission(contestId, projectId, readonly, userId);
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while getting capacity.", e);
        }

        return false;
    }

    /**
     * check contest permission, check if a user has permission (read or write) on a contest
     *
     * @param contestId the contest id
     * @param readonly check read or write permission
     * @param userId user id
     * @return true/false
     */
    public boolean checkContestPermission(long contestId, boolean readonly, long userId)
            throws PersistenceException {
        logEnter("checkContestPermission(" + contestId + ", " + readonly + ", " + userId + ")");
        try {
            logExit("checkContestPermission()");
            return contestManager.checkContestPermission(contestId, readonly, userId);
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while getting capacity.", e);
        }

        return false;
    }

    /**
     * check submission permission, check if a user has permission (read or write) on a submission's contest
     *
     * @param contestId the contest id
     * @param readonly check read or write permission
     * @param userId user id
     * @return true/false
     */
    public boolean checkSubmissionPermission(long submissionId, boolean readonly, long userId)
            throws PersistenceException {
        logEnter("checkSubmissionPermission(" + submissionId + ", " + readonly + ", " + userId + ")");
        try {
            logExit("checkSubmissionPermission()");
            return contestManager.checkSubmissionPermission(submissionId, readonly, userId);
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while getting capacity.", e);
        }

        return false;
    }

    /**
     * check contest permission, check if a user has permission (read or write) on a project
     *
     * @param projectId the tc direct project id
     * @param readonly check read or write permission
     * @param userId user id
     * @return true/false
     */
    public boolean checkProjectPermission(long projectId, boolean readonly, long userId)
            throws PersistenceException {

        logEnter("checkProjectPermission(" + projectId + ", " + readonly + ", " + userId + ")");
        try {
            logExit("checkProjectPermission()");
            return contestManager.checkProjectPermission(projectId, readonly, userId);
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while getting capacity.", e);
        }

        return false;
    }
    /**
     * <p>
     * Get the user-name for the login user represented by TCSubject.
     * </p>
     * @param tcSubject for login user info
     * @return user name
     * @throws DAOException if any error occurs
     */
    private String getUserName(TCSubject tcSubject)throws PersistenceException {
        try {
            return this.userService.getUserHandle(tcSubject.getUserId());
        } catch (Exception e) {
            handlePersistenceError("Fail to get the user-name by user-id" + tcSubject.getUserId(), e);
        }
        return null;
    }
}
