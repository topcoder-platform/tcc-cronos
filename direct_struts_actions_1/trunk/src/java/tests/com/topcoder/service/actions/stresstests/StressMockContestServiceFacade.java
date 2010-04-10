package com.topcoder.service.actions.stresstests;

import java.util.List;

import javax.activation.DataHandler;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.Phase;
import com.topcoder.catalog.entity.Technology;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.management.project.DesignComponents;
import com.topcoder.management.review.data.Comment;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.project.service.ScorecardReviewData;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.CommonProjectContestData;
import com.topcoder.service.facade.contest.CommonProjectPermissionData;
import com.topcoder.service.facade.contest.ContestPaymentResult;
import com.topcoder.service.facade.contest.ContestServiceException;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.facade.contest.ContestServiceFilter;
import com.topcoder.service.facade.contest.SoftwareContestPaymentResult;
import com.topcoder.service.payment.CreditCardPaymentData;
import com.topcoder.service.payment.PaymentException;
import com.topcoder.service.payment.PaymentResult;
import com.topcoder.service.payment.TCPurhcaseOrderPaymentData;
import com.topcoder.service.permission.Permission;
import com.topcoder.service.permission.PermissionServiceException;
import com.topcoder.service.permission.PermissionType;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.specreview.SpecReview;
import com.topcoder.service.studio.ChangeHistoryData;
import com.topcoder.service.studio.CompletedContestData;
import com.topcoder.service.studio.ContestNotFoundException;
import com.topcoder.service.studio.ContestStatusData;
import com.topcoder.service.studio.ContestTypeData;
import com.topcoder.service.studio.DocumentNotFoundException;
import com.topcoder.service.studio.MediumData;
import com.topcoder.service.studio.PersistenceException;
import com.topcoder.service.studio.ProjectNotFoundException;
import com.topcoder.service.studio.StatusNotAllowedException;
import com.topcoder.service.studio.StatusNotFoundException;
import com.topcoder.service.studio.SubmissionData;
import com.topcoder.service.studio.SubmissionFeedback;
import com.topcoder.service.studio.SubmissionPaymentData;
import com.topcoder.service.studio.UploadedDocument;
import com.topcoder.service.studio.contest.DocumentType;
import com.topcoder.service.studio.contest.SimpleContestData;
import com.topcoder.service.studio.contest.SimpleProjectContestData;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.contest.User;

public class StressMockContestServiceFacade implements ContestServiceFacade {

    public void addChangeHistory(TCSubject tcSubject, List<ChangeHistoryData> history)
            throws PersistenceException {
        // TODO Auto-generated method stub

    }

    public void addDocumentToContest(TCSubject tcSubject, long documentId, long contestId)
            throws PersistenceException, ContestNotFoundException {
        // TODO Auto-generated method stub

    }

    public PermissionType addPermissionType(TCSubject tcSubject, PermissionType type)
            throws PermissionServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public void addReviewComment(TCSubject tcSubject, long reviewId, Comment comment)
            throws ContestServiceException {
        // TODO Auto-generated method stub

    }

    public long addSubmitter(TCSubject tcSubject, long projectId, long userId) throws ContestServiceException {
        // TODO Auto-generated method stub
        return 0;
    }

    public void assignUserToAsset(TCSubject tcSubject, long userId, long assetId)
            throws ContestServiceException {
        // TODO Auto-generated method stub

    }

    public StudioCompetition createContest(TCSubject tcSubject, StudioCompetition contest,
            long tcDirectProjectId) throws PersistenceException {
        // TODO Auto-generated method stub
        return contest;
    }

    public long createNewVersionForDesignDevContest(TCSubject tcSubject, long projectId,
            long tcDirectProjectId, boolean autoDevCreating, boolean minorVersion)
            throws ContestServiceException {
        // TODO Auto-generated method stub
        return 0;
    }

    public SoftwareCompetition createSoftwareContest(TCSubject tcSubject, SoftwareCompetition contest,
            long tcDirectProjectId) throws ContestServiceException {
        // TODO Auto-generated method stub
        return contest;
    }

    public FullProjectData createSpecReview(TCSubject tcSubject, long projectId)
            throws ContestServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public void deleteContest(TCSubject tcSubject, long contestId) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    public SoftwareCompetition[] findAllTcDirectProjects(TCSubject tcSubject) throws ContestServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public SoftwareCompetition[] findAllTcDirectProjectsForUser(TCSubject tcSubject, String operator)
            throws ContestServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Category> getActiveCategories(TCSubject tcSubject) throws ContestServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Technology> getActiveTechnologies(TCSubject tcSubject) throws ContestServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<StudioCompetition> getAllContestHeaders(TCSubject tcSubject) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<ContestTypeData> getAllContestTypes(TCSubject tcSubject) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<StudioCompetition> getAllContests(TCSubject tcSubject) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<DocumentType> getAllDocumentTypes(TCSubject tcSubject) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<MediumData> getAllMediums(TCSubject tcSubject) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<PermissionType> getAllPermissionType(TCSubject tcSubject) throws PermissionServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<StudioFileType> getAllStudioFileTypes(TCSubject tcSubject) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<ChangeHistoryData> getChangeHistory(TCSubject tcSubject, long contestId)
            throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<CommonProjectContestData> getCommonProjectContestData(TCSubject tcSubject)
            throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<CommonProjectContestData> getCommonProjectContestDataByPID(TCSubject tcSubject, long pid)
            throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<CommonProjectPermissionData> getCommonProjectPermissionDataForUser(TCSubject tcSubject,
            long createdUser) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public StudioCompetition getContest(TCSubject tcSubject, long contestId) throws PersistenceException,
            ContestNotFoundException {
        StudioCompetition studioCompetition = new StudioCompetition();
        studioCompetition.setId(contestId);

        return studioCompetition;
    }

    public List<SimpleContestData> getContestDataOnly(TCSubject tcSubject) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<SimpleContestData> getContestDataOnlyByPID(TCSubject tcSubject, long pid)
            throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<ProjectContestFee> getContestFeesByProject(TCSubject tcSubject, long projectId)
            throws ContestServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<StudioCompetition> getContestsForProject(TCSubject tcSubject, long tcDirectProjectId)
            throws PersistenceException, ProjectNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<DesignComponents> getDesignComponents(TCSubject tcSubject) throws ContestServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public String getEligibilityName(TCSubject tcSubject, long billingProjectId) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<SubmissionData> getFinalSubmissionsForContest(TCSubject tcSubject, long contestId)
            throws ContestServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public SoftwareCompetition getFullProjectData(TCSubject tcSubject, long projectId)
            throws ContestServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<ChangeHistoryData> getLatestChanges(TCSubject tcSubject, long contestId)
            throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<SubmissionData> getMilestoneSubmissionsForContest(TCSubject tcSubject, long contestId)
            throws ContestServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public long getMimeTypeId(TCSubject tcSubject, String contentType) throws PersistenceException {
        // TODO Auto-generated method stub
        return 0;
    }

    public List<Permission> getPermissions(TCSubject tcSubject, long userid, long projectid)
            throws PermissionServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Permission> getPermissionsByProject(TCSubject tcSubject, long projectid)
            throws PermissionServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Permission> getPermissionsByUser(TCSubject tcSubject, long userid)
            throws PermissionServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Phase> getPhases(TCSubject tcSubject) throws ContestServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public ScorecardReviewData getScorecardAndReview(TCSubject tcSubject, long projectId)
            throws ContestServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<SimpleContestData> getSimpleContestData(TCSubject tcSubject) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<SimpleContestData> getSimpleContestDataByPID(TCSubject tcSubject, long pid)
            throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<SimpleProjectContestData> getSimpleProjectContestData(TCSubject tcSubject)
            throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<SimpleProjectContestData> getSimpleProjectContestDataByPID(TCSubject tcSubject, long pid)
            throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public SoftwareCompetition getSoftwareContestByProjectId(TCSubject tcSubject, long projectId)
            throws ContestServiceException {
        SoftwareCompetition softwareCompetition = new SoftwareCompetition();
        softwareCompetition.setId(projectId);

        return softwareCompetition;
    }

    public SpecReview getSpecReviews(TCSubject tcSubject, long contestId, boolean studio)
            throws ContestServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<ContestStatusData> getStatusList(TCSubject tcSubject) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public String getSubmissionFileTypes(TCSubject tcSubject) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<StudioCompetition> getUserContests(TCSubject tcSubject, String userName)
            throws ContestServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isEligible(TCSubject tcSubject, long userId, long contestId, boolean isStudio)
            throws ContestServiceException {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isPrivate(TCSubject tcSubject, long contestId, boolean isStudio)
            throws ContestServiceException {
        // TODO Auto-generated method stub
        return false;
    }

    public void markForPurchase(TCSubject tcSubject, long submissionId) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    public void markReadyForReview(TCSubject tcSubject, long contestId, boolean studio)
            throws ContestServiceException {
        // TODO Auto-generated method stub

    }

    public void markReviewCommentSeen(TCSubject tcSubject, long commentId) throws ContestServiceException {
        // TODO Auto-generated method stub

    }

    public void markReviewDone(TCSubject tcSubject, long contestId, String contestName, boolean studio,
            long tcDirectProjectId) throws ContestServiceException {
        // TODO Auto-generated method stub

    }

    public void markSoftwareContestReadyForReview(TCSubject tcSubject, long projectId)
            throws ContestServiceException {
        // TODO Auto-generated method stub

    }

    public ContestPaymentResult processContestCreditCardPayment(TCSubject tcSubject,
            StudioCompetition competition, CreditCardPaymentData paymentData) throws PersistenceException,
            PaymentException, ContestNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    public SoftwareContestPaymentResult processContestCreditCardSale(TCSubject tcSubject,
            SoftwareCompetition competition, CreditCardPaymentData paymentData)
            throws ContestServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public ContestPaymentResult processContestPurchaseOrderPayment(TCSubject tcSubject,
            StudioCompetition competition, TCPurhcaseOrderPaymentData paymentDat)
            throws PersistenceException, PaymentException, ContestNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    public SoftwareContestPaymentResult processContestPurchaseOrderSale(TCSubject tcSubject,
            SoftwareCompetition competition, TCPurhcaseOrderPaymentData paymentData)
            throws ContestServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public void processMissingPayments(TCSubject tcSubject, long contestId) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    public PaymentResult processSubmissionCreditCardPayment(TCSubject tcSubject,
            CompletedContestData completedContestData, CreditCardPaymentData paymentData)
            throws PaymentException, PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public PaymentResult processSubmissionPurchaseOrderPayment(TCSubject tcSubject,
            CompletedContestData completedContestData, TCPurhcaseOrderPaymentData paymentData)
            throws PaymentException, PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public void purchaseSubmission(TCSubject tcSubject, long submissionId,
            SubmissionPaymentData submissionPaymentData, String securityToken) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    public boolean rankSubmissions(TCSubject tcSubject, long[] submissionIdsInRankOrder)
            throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    public long reOpenSoftwareContest(TCSubject tcSubject, long projectId, long tcDirectProjectId)
            throws ContestServiceException {
        // TODO Auto-generated method stub
        return 0;
    }

    public boolean removeDocument(TCSubject tcSubject, long documentId) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    public void removeDocumentFromContest(TCSubject tcSubject, UploadedDocument document)
            throws PersistenceException, DocumentNotFoundException {
        // TODO Auto-generated method stub

    }

    public void removeSubmission(TCSubject tcSubject, long submissionId) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    public void removeUserFromAsset(TCSubject tcSubject, long userId, long assetId)
            throws ContestServiceException {
        // TODO Auto-generated method stub

    }

    public void resubmitForReview(TCSubject tcSubject, long contestId, String contestName, boolean studio,
            long reviewerUserId) throws ContestServiceException {
        // TODO Auto-generated method stub

    }

    public SubmissionData retrieveSubmission(TCSubject tcSubject, long submissionId)
            throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<SubmissionData> retrieveSubmissionsForContest(TCSubject tcSubject, long contestId)
            throws PersistenceException, ContestNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    public void saveReviewComment(TCSubject tcSubject, long contestId, boolean studio, String sectionName,
            String comment, String role) throws ContestServiceException {
        // TODO Auto-generated method stub

    }

    public void saveReviewStatus(TCSubject tcSubject, long contestId, boolean studio, String sectionName,
            String comment, boolean isPass, String role) throws ContestServiceException {
        // TODO Auto-generated method stub

    }

    public List<StudioCompetition> searchContests(TCSubject tcSubject, ContestServiceFilter filter)
            throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<User> searchUser(TCSubject tcSubject, String key) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public void setSubmissionMilestonePrize(TCSubject tcSubject, long submissionId, long milestonePrizeId)
            throws ContestServiceException {
        // TODO Auto-generated method stub

    }

    public void setSubmissionPlacement(TCSubject tcSubject, long submissionId, int placement)
            throws PersistenceException {
        // TODO Auto-generated method stub

    }

    public void setSubmissionPrize(TCSubject tcSubject, long submissionId, long prizeId)
            throws PersistenceException {
        // TODO Auto-generated method stub

    }

    public void setSubmissionStatus(TCSubject tcSubject, long submissionId, long submissionStatusId,
            String operator) throws ContestServiceException {
        // TODO Auto-generated method stub

    }

    public void updateContest(TCSubject tcSubject, StudioCompetition contest) throws PersistenceException,
            ContestNotFoundException {
        // TODO Auto-generated method stub

    }

    public void updateContestStatus(TCSubject tcSubject, long contestId, long newStatusId)
            throws PersistenceException, StatusNotAllowedException, StatusNotFoundException,
            ContestNotFoundException {
        // TODO Auto-generated method stub

    }

    public void updatePermissionType(TCSubject tcSubject, PermissionType type)
            throws PermissionServiceException {
        // TODO Auto-generated method stub

    }

    public void updatePermissions(TCSubject tcSubject, Permission[] permissions)
            throws PermissionServiceException {
        // TODO Auto-generated method stub

    }

    public SoftwareCompetition updateSoftwareContest(TCSubject tcSubject, SoftwareCompetition contest,
            long tcDirectProjectId) throws ContestServiceException {
        // TODO Auto-generated method stub
        return new SoftwareCompetition();
    }

    public void updateSubmission(TCSubject tcSubject, SubmissionData submission) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    public boolean updateSubmissionUserRank(TCSubject tcSubject, long submissionId, int rank,
            Boolean isRankingMilestone) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean updateSubmissionsFeedback(TCSubject tcSubject, SubmissionFeedback[] feedbacks)
            throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    public UploadedDocument uploadDocument(TCSubject tcSubject, UploadedDocument uploadedDocument)
            throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public UploadedDocument uploadDocumentForContest(TCSubject tcSubject, UploadedDocument uploadedDocument)
            throws PersistenceException, ContestNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    public long uploadFinalFix(TCSubject tcSubject, long projectId, String filename, DataHandler finalFix)
            throws ContestServiceException {
        // TODO Auto-generated method stub
        return 0;
    }

    public long uploadSubmission(TCSubject tcSubject, long projectId, String filename, DataHandler submission)
            throws ContestServiceException {
        // TODO Auto-generated method stub
        return 0;
    }

    public long uploadTestCases(TCSubject tcSubject, long projectId, String filename, DataHandler testCases)
            throws ContestServiceException {
        // TODO Auto-generated method stub
        return 0;
    }

}
