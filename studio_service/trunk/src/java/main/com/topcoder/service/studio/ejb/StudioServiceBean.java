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
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.security.auth.module.UserProfilePrincipal;
import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.ContestNotFoundException;
import com.topcoder.service.studio.ContestPayload;
import com.topcoder.service.studio.ContestPaymentData;
import com.topcoder.service.studio.ContestStatusData;
import com.topcoder.service.studio.ContestTypeData;
import com.topcoder.service.studio.DocumentNotFoundException;
import com.topcoder.service.studio.IllegalArgumentWSException;
import com.topcoder.service.studio.PersistenceException;
import com.topcoder.service.studio.PrizeData;
import com.topcoder.service.studio.StatusNotAllowedException;
import com.topcoder.service.studio.StatusNotFoundException;
import com.topcoder.service.studio.StudioService;
import com.topcoder.service.studio.StudioServiceException;
import com.topcoder.service.studio.SubmissionData;
import com.topcoder.service.studio.UploadedDocument;
import com.topcoder.service.studio.UserNotAuthorizedException;
import com.topcoder.service.studio.contest.Contest;
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
import com.topcoder.service.studio.contest.EntityAlreadyExistsException;
import com.topcoder.service.studio.contest.EntityNotFoundException;
import com.topcoder.service.studio.contest.FilePath;
import com.topcoder.service.studio.contest.MimeType;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.submission.PaymentStatus;
import com.topcoder.service.studio.submission.Prize;
import com.topcoder.service.studio.submission.PrizeType;
import com.topcoder.service.studio.submission.Submission;
import com.topcoder.service.studio.submission.SubmissionManagementException;
import com.topcoder.service.studio.submission.SubmissionManagerLocal;
import com.topcoder.service.studio.submission.SubmissionPayment;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

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

    /**
     * <p>
     * Represents the draft status used to retrieve the check the draft status.
     * </p>
     */
    @Resource(name = "draftStatusId")
    private long draftStatusId;

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
     * Represents the base path for the documents. Should be configured like
     * /studiofiles/documents.
     * 
     * [BUG TCCC-134]
     */
    @Resource(name = "documentBasePath")
    private String documentBasePath = DEFAULT_DOCUMENT_BASE_PATH;

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

        // authorization
        authorizeWithProject(tcDirectProjectId);

        // access is granted, create contest
        try {
            Contest contest = convertContestData(contestData);
            contest.setTcDirectProjectId(tcDirectProjectId);

            // use the logged user [27074484-16]
            contest.setCreatedUser(((UserProfilePrincipal) sessionContext.getCallerPrincipal()).getUserId());

            contest = contestManager.createContest(contest);

            // FIX [TCCC-146]
            for (PrizeData prizeData : contestData.getPrizes()) {
                Prize prize = new Prize();
                prize.setAmount(prizeData.getAmount());
                prize.setPlace(prizeData.getPlace());
                prize.setCreateDate(new Date());
                PrizeType type = new PrizeType();
                // TODO: Use 1 as "Contest" temporarily since there is no method
                // supports retrieve PrizeType now.
                type.setPrizeTypeId(1L);
                prize.setType(type);
                Set<Contest> contests = prize.getContests();
                contests.add(contest);
                prize.setContests(contests);

                contestManager.createPrize(prize);
                logError("Prize created. Id: " + prize.getPrizeId());
            }

            List<UploadedDocument> documents = new ArrayList<UploadedDocument>();
            for (UploadedDocument doc : contestData.getDocumentationUploads()) {
                documents.add(uploadDocument(doc, contest));
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

        // authorization
        authorizeWithContest(contestId);

        try {
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

        Contest c = null;
        try {
            contestManager.updateContest(convertContestData(contestData));
            c = contestManager.getContest(contestData.getContestId());
        } catch (EntityNotFoundException e) {
            handleContestNotFoundError(e, contestData.getContestId());
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while updating contest.", e);
        }

        // persist documents
        for (UploadedDocument doc : contestData.getDocumentationUploads()) {
            uploadDocument(doc, c);
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

        // authorization
        authorizeAdmin();

        try {
            if (contestManager.getContest(contestId) == null) {
                handleContestNotFoundError(null, contestId);
            }
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

        // authorization
        authorizeWithContest(uploadedDocument.getContestId());

        // retrieve contest (for farther usage also)
        Contest c = null;
        try {
            c = contestManager.getContest(uploadedDocument.getContestId());
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while fetching contest.", e);
        }

        if (c == null) {
            // no contest, error
            handleContestNotFoundError(null, uploadedDocument.getContestId());
        }

        // last authentication check
        if (sessionContext.isCallerInRole(USER_ROLE)) {
            if (c.getStatus() == null || c.getStatus().getContestStatusId() != draftStatusId) {
                handleAuthorizationError("draft check was failed.");
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

        // authorization
        authorizeWithContest(document.getContestId());

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
            logExit("retrieveSubmissionsForContest", result);
            return result;
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManager reports error while retrieving submissions for contest.", e);
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

        // authorization
        authorizeWithContest(submission.getContestId());

        try {
            submissionManager.updateSubmission(convertSubmissionData(submission));
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManager reports error while updating submission.", e);
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
            result.setEndDate(new Date((long) (startDate.getTime() + 60l * 60 * data.getDurationInHours())));
        }

        addContestConfig(result, contestPropertyShortSummaryId, data.getShortSummary());

        addContestConfig(result, contestPropertyContestOverviewTextId, data.getContestDescriptionAndRequirements());

        addContestConfig(result, contestPropertyColorRequirementsId, data.getRequiredOrRestrictedColors());

        addContestConfig(result, contestPropertyFontRequirementsId, data.getRequiredOrRestrictedFonts());

        addContestConfig(result, contestPropertySizeRequirementsId, data.getSizeRequirements());

        addContestConfig(result, contestPropertyOtherRequirementsId, data.getOtherRequirementsOrRestrictions());

        addContestConfig(result, contestPropertyFinalFileFormatId, data.getFinalFileFormat());

        addContestConfig(result, contestPropertyOtherFileFormatsId, data.getOtherFileFormats());

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
        ContestData contestData = new ContestData();

        contestData.setLaunchDateAndTime(getXMLGregorianCalendar(contest.getStartDate()));

        contestData.setContestId(unbox(contest.getContestId()));
        contestData.setCreatorUserId(unbox(contest.getCreatedUser()));
        contestData.setName(contest.getName());
        contestData.setProjectId(unbox(contest.getProjectId()));
        contestData.setWinnerAnnoucementDeadline(getXMLGregorianCalendar(contest.getWinnerAnnoucementDeadline()));
        contestData.setStatusId(contest.getStatus().getContestStatusId());

        // [27074484-20]
        ContestType contestType = contest.getContestType();
        contestData.setContestTypeId(contestType == null ? -1 : unbox(contestType.getContestType()));

        if (contest.getSubmissions() != null) {
            contestData.setSubmissionCount(contest.getSubmissions().size());
        }

        // Since 1.0.3, Bug Fix 27074484-14
        for (ContestConfig cc : contest.getConfig()) {

            if (cc.getId().getProperty().getPropertyId() == contestPropertyShortSummaryId)
                contestData.setShortSummary(cc.getValue());
            else if (cc.getId().getProperty().getPropertyId() == contestPropertyFinalFileFormatId)
                contestData.setFinalFileFormat(cc.getValue());
            else if (cc.getId().getProperty().getPropertyId() == contestPropertyOtherFileFormatsId)
                contestData.setOtherFileFormats(cc.getValue());
            else if (cc.getId().getProperty().getPropertyId() == contestPropertyColorRequirementsId)
                contestData.setRequiredOrRestrictedColors(cc.getValue());
            else if (cc.getId().getProperty().getPropertyId() == contestPropertyFontRequirementsId)
                contestData.setRequiredOrRestrictedFonts(cc.getValue());
            else if (cc.getId().getProperty().getPropertyId() == contestPropertyContestOverviewTextId)
                contestData.setContestDescriptionAndRequirements(cc.getValue());
            else if (cc.getId().getProperty().getPropertyId() == contestPropertySizeRequirementsId)
                contestData.setSizeRequirements(cc.getValue());
            else if (cc.getId().getProperty().getPropertyId() == contestPropertyOtherRequirementsId)
                contestData.setOtherRequirementsOrRestrictions(cc.getValue());
        }

        List<UploadedDocument> documents = new ArrayList<UploadedDocument>();
        for (Document doc : contest.getDocuments()) {
            documents.add(convertDocument(doc));
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

        for (Prize prize : contestManager.getContestPrizes(contest.getContestId())) {
            PrizeData prizeData = new PrizeData();
            prizeData.setAmount(prize.getAmount());
            prizeData.setPlace(prize.getPlace());
            prizes.add(prizeData);
        }

        contestData.setPrizes(prizes);

        return contestData;
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

        try {
            to.setFile(contestManager.getDocumentContent(to.getDocumentId()));
        } catch (ContestManagementException e) {
            // no need to propagate exception
            logError(e, "ContestManager reports error during getDocumentContent call.");
        }

        return to;
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

        // [BUG TCCC-134]
        fp.setPath(documentBasePath + File.separator + contest.getContestId());

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
            sd.setPlacement(unbox(s.getRank()));
            sd.setSubmissionId(unbox(s.getSubmissionId()));
            sd.setSubmittedDate(getXMLGregorianCalendar(s.getSubmissionDate()));
            sd.setSubmitterId(unbox(s.getSubmitterId()));

            // compute price
            double prizeAmount = 0;
            for (Prize p : s.getPrizes()) {
                prizeAmount += p.getAmount();
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
                sd.setPassedScreening(s.getStatus().getSubmissionStatusId() == submissionPassedStatus);
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
        submission.setRank(submissionData.getPlacement());

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
            log.log(Level.DEBUG, "Enter method {0} with parameters {1}.", method, Arrays.deepToString(params));
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
    private void authorizeWithContest(long id) throws PersistenceException {
        // TODO UNCOMMENT ME
        // if (sessionContext.isCallerInRole(USER_ROLE)) {
        // try {
        // authorizeUser(contestManager.getClientForContest(id));
        // } catch (ContestManagementException e) {
        // handlePersistenceError(
        // "ContestManager reports error while retrieving client for contest.",
        // e);
        // }
        // }
    }

    /**
     * Implements statdard algorithm of authorization based on fetching client
     * id from project id. Only admin and client may pass.
     * 
     * @param id
     *            project id to identify client (if necessary)
     * @throws PersistenceException
     *             when ContestManager reports some error
     * @throws UserNotAuthorizedException
     *             if access was denied
     */
    private void authorizeWithProject(long id) throws PersistenceException {
        // TODO UNCOMMENT ME.
        // if (sessionContext.isCallerInRole(USER_ROLE)) {
        // try {
        // authorizeUser(contestManager.getClientForProject(id));
        // } catch (ContestManagementException e) {
        // handlePersistenceError(
        // "ContestManager reports error while retrieving client for project.",
        // e);
        // }
        // }
    }

    /**
     * Endpoint of authorizeWithContest and authorizeWithProject methods,
     * authorize current user only if he is project/contest client.
     * 
     * @param clientId
     *            id of client to compare
     * @throws UserNotAuthorizedException
     *             if access was denied
     */
    private void authorizeUser(long clientId) {
        UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
        long userId = p.getUserId();
        if (userId != clientId) {
            logError("Access was denied to the user.");
            UserNotAuthorizedFault bean = new UserNotAuthorizedFault();
            bean.setUserIdNotAuthorized(userId);
            throw new UserNotAuthorizedException("Access was denied to the user.", bean);
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
            List<Contest> contests = contestManager.getAllContests();
            ArrayList<ContestData> result = new ArrayList<ContestData>();
            for (Contest contest : contests) {
                result.add(convertContest(contest));
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
            EqualToFilter filter = new EqualToFilter("clientId", clientId);
            List<Contest> contests = contestManager.searchContests(filter);

            ArrayList<ContestData> result = new ArrayList<ContestData>();
            for (Contest contest : contests) {
                result.add(convertContest(contest));
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
                    payload.setName(cfg.getId().getProperty().getDescription());

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
     *            the id of submission to remove
     * @param price
     *            Price of submission.
     * @param payPalOrderId
     *            PayPal order id.
     * @throws PersistenceException
     *             if any error occurs when purchasing submission.
     * @throws IllegalArgumentWSException
     *             if the submissionId is less than 0 or price is negative.
     */
    public void purchaseSubmission(long submissionId, double price, long payPalOrderId) throws PersistenceException {
        logEnter("purchaseSubmission", submissionId, price);
        checkParameter("submissionId", submissionId);
        checkParameter("payPalOrderId", payPalOrderId);
        checkParameter("price", price);

        try {
            SubmissionPayment submissionPayment = new SubmissionPayment();
            Submission submission = submissionManager.getSubmission(submissionId);
            if (submission == null) {
                handleIllegalWSArgument("Submission with id " + submissionId + " is not found");
            }

            submissionPayment.setSubmission(submission);
            submissionPayment.setPrice(price);
            // [ TCCC-125 ]
            submissionPayment.setPayPalOrderId(payPalOrderId);

            PaymentStatus status = new PaymentStatus();

            // NOTE Use 1 temporarily till submission manager provides the
            // method of
            // retrieving payment statuses.
            // TODO FIX ME
            status.setPaymentStatusId(1L);

            submissionPayment.setStatus(status);
            submissionManager.addSubmissionPayment(submissionPayment);
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManager reports error.", e);
        }
        logExit("purchaseSubmission");
    }

    /**
     * <p>
     * Select winner.
     * </p>
     * <p>
     * 1, set the placement field of submission. 2, set the price of submission
     * (I'm not sure about this). 3, create an entry at submission payment table
     * </p>
     * 
     * @param submissionId
     *            the id of submission to remove
     * @param place
     *            place of submission.
     * @param payPalOrderId
     *            PayPal order id.
     * 
     * @throws PersistenceException
     *             if any error occurs when selecting winner.
     * @throws IllegalArgumentWSException
     *             if the submissionId is less than 0 or place is not positive.
     */
    public void selectWinner(long submissionId, int place, long payPalOrderId) throws PersistenceException {
        logEnter("selectWinner", submissionId, place);
        checkParameter("submissionId", submissionId);
        checkParameter("payPalOrderId", payPalOrderId);
        checkParameter("place", place);

        try {
            Submission submission = submissionManager.getSubmission(submissionId);
            if (submission == null) {
                handleIllegalWSArgument("Submission with id " + submissionId + " is not found");
            }
            // set the placement field of submission.
            submission.setRank(place);

            submissionManager.updateSubmission(submission);

            // create an entry at submission payment table
            // If the submission is anything other than 1st place, the price
            // should be the 2nd place payment.
            for (Prize prize : submission.getPrizes()) {
                if (place == 1) {
                    if (prize.getPlace().equals(1L)) {
                        purchaseSubmission(submissionId, prize.getAmount(), payPalOrderId);
                        break;
                    }
                } else {
                    if (prize.getPlace().equals(2L)) {
                        purchaseSubmission(submissionId, prize.getAmount(), payPalOrderId);
                        break;
                    }
                }
            }
        } catch (SubmissionManagementException e) {
            handlePersistenceError("SubmissionManager reports error.", e);
        }

        logExit("selectWinner");
    }

    /**
     * <p>
     * Creates a new contest payment and returns the created contest payment.
     * </p>
     * 
     * @param contestPaymentData
     *            the contest payment to create
     * @return the created contest payment.
     * 
     * @throws IllegalArgumentException
     *             if the arg is null.
     * @throws PersistenceException
     *             if any other error occurs.
     */
    public ContestPaymentData createContestPayment(ContestPaymentData contestPaymentData) throws PersistenceException {
        logEnter("createContestPayment", contestPaymentData);
        checkParameter("contestPaymentData", contestPaymentData);

        // authorization
        authorizeWithContest(contestPaymentData.getContestId());

        // access is granted, create contest
        try {
            ContestPayment contestPayment = null;//convertContestData(contestData
                                                 // );
            contestPayment = contestManager.createContestPayment(contestPayment);
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while creating new ContestPayment.", e);
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
            // authorization
            authorizeWithContest(contestPayment.getContest().getContestId());
            if (contestPayment == null) {
                // handleContestNotFoundError(null, contestPaymentId);
            }

            ContestPaymentData result = null;// convertContest(contest);
            logExit("getContestPayment", result);
            return result;
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while retrieving contest.", e);
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
        authorizeWithContest(contestPayment.getContestId());

        Contest c = null;
        try {
            contestManager.editContestPayment(null);// convertContestData(
                                                    // contestData));
        } catch (EntityNotFoundException e) {
            // handleContestNotFoundError(e, contestData.getContestId());
        } catch (ContestManagementException e) {
            handlePersistenceError("ContestManager reports error while updating contest.", e);
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
}
