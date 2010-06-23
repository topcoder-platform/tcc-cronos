package com.topcoder.service.facade.direct.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.Phase;
import com.topcoder.catalog.entity.Status;
import com.topcoder.catalog.entity.Technology;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.management.project.DesignComponents;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectPropertyType;
import com.topcoder.management.review.data.Comment;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.CommonProjectContestData;
import com.topcoder.service.facade.contest.CommonProjectPermissionData;
import com.topcoder.service.facade.contest.ContestPaymentResult;
import com.topcoder.service.facade.contest.ContestServiceException;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.facade.contest.SoftwareContestPaymentResult;
import com.topcoder.service.payment.CreditCardPaymentData;
import com.topcoder.service.payment.PaymentException;
import com.topcoder.service.payment.PaymentResult;
import com.topcoder.service.payment.TCPurhcaseOrderPaymentData;
import com.topcoder.service.permission.Permission;
import com.topcoder.service.permission.PermissionServiceException;
import com.topcoder.service.permission.PermissionType;
import com.topcoder.service.project.CompetionType;
import com.topcoder.service.project.CompetitionPrize;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.specreview.SpecReview;
import com.topcoder.service.studio.ChangeHistoryData;
import com.topcoder.service.studio.CompletedContestData;
import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.ContestMultiRoundInformationData;
import com.topcoder.service.studio.ContestNotFoundException;
import com.topcoder.service.studio.ContestPaymentData;
import com.topcoder.service.studio.ContestStatusData;
import com.topcoder.service.studio.ContestTypeData;
import com.topcoder.service.studio.DocumentNotFoundException;
import com.topcoder.service.studio.MediumData;
import com.topcoder.service.studio.MilestonePrizeData;
import com.topcoder.service.studio.PersistenceException;
import com.topcoder.service.studio.ProjectNotFoundException;
import com.topcoder.service.studio.StatusNotAllowedException;
import com.topcoder.service.studio.StatusNotFoundException;
import com.topcoder.service.studio.SubmissionData;
import com.topcoder.service.studio.SubmissionFeedback;
import com.topcoder.service.studio.SubmissionPaymentData;
import com.topcoder.service.studio.UploadedDocument;
import com.topcoder.service.studio.contest.SimpleContestData;
import com.topcoder.service.studio.contest.User;

public class MockContestServiceFacade implements ContestServiceFacade {

    public MockContestServiceFacade() {
    }

    public void addChangeHistory(TCSubject tcSubject, List<ChangeHistoryData> history) throws PersistenceException {
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

    public void addReviewComment(TCSubject tcSubject, long reviewId, Comment comment) throws ContestServiceException {
        // TODO Auto-generated method stub

    }

    public long addSubmitter(TCSubject tcSubject, long projectId, long userId) throws ContestServiceException {
        // TODO Auto-generated method stub
        return 0;
    }

    public void assignUserToAsset(TCSubject tcSubject, long userId, long assetId) throws ContestServiceException {
        // TODO Auto-generated method stub

    }

    public StudioCompetition createContest(TCSubject tcSubject, StudioCompetition contest, long tcDirectProjectId)
            throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public long createNewVersionForDesignDevContest(TCSubject tcSubject, long projectId, long tcDirectProjectId,
            boolean autoDevCreating, boolean minorVersion) throws ContestServiceException {
        return projectId + 1;
    }

    public SoftwareCompetition createSoftwareContest(TCSubject tcSubject, SoftwareCompetition contest,
            long tcDirectProjectId) throws ContestServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public FullProjectData createSpecReview(TCSubject tcSubject, long projectId) throws ContestServiceException {
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

    public List<MediumData> getAllMediums(TCSubject tcSubject) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<PermissionType> getAllPermissionType(TCSubject tcSubject) throws PermissionServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<ChangeHistoryData> getChangeHistory(TCSubject tcSubject, long contestId) throws PersistenceException {
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
        List<CommonProjectContestData> datas = new ArrayList<CommonProjectContestData>();

        CommonProjectContestData data = new CommonProjectContestData();
        data.setContestId(1L);

        datas.add(data);
        return datas;
    }

    public List<CommonProjectPermissionData> getCommonProjectPermissionDataForUser(TCSubject tcSubject,
            long createdUser) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public StudioCompetition getContest(TCSubject tcSubject, long contestId) throws PersistenceException,
            ContestNotFoundException {
        if (contestId == 400) {
            throw new PersistenceException("for test");
        }
        if (contestId == 500) {
            throw new ContestNotFoundException("for test", null);
        }

        ContestData contestData = getContestData();
        StudioCompetition competition = new StudioCompetition(contestData);
        competition.setAdminFee(500.0);
        competition.setCategory("category");
        competition.setClientApproval(true);
        competition.setClientName("clientName");
        competition.setCompetitionId(1L);
        competition.setConfidence(123);

        competition.setContestFee(500);
        competition.setCreatorUserId(1);
        competition.setDrPoints(123);
        competition.setEligibility("eligibility");
        competition.setHasDependentCompetitions(true);
        competition.setHasWikiSpecification(true);
        competition.setId(1);
        competition.setNotes("notes");
        competition.setPassedSpecReview(true);
        competition.setPricingApproval(true);

        List<CompetitionPrize> prizes = new ArrayList<CompetitionPrize>();
        CompetitionPrize prize = new CompetitionPrize();
        prize.setPlace(1);
        prize.setAmount(123);
        prizes.add(prize);
        competition.setPrizes(prizes);

        competition.setProject(new com.topcoder.service.project.Project());
        competition.setReviewPayment(500);
        competition.setShortSummary("shortSummary");
        competition.setSpecificationReviewPayment(123);
        competition.setStatus(new ContestStatusData());
        competition.setType(CompetionType.ARCHITECTURE);
        competition.setWasReposted(true);

        competition.setStartTime(TestHelper.getXMLGregorianCalendar(new Date()));
        competition.setEndTime(TestHelper.getXMLGregorianCalendar(new Date()));

        return competition;
    }

    private ContestData getContestData() {
        ContestData contestData = new ContestData();
        contestData.setBillingProject(1);
        contestData.setContestAdministrationFee(1);
        contestData.setContestDescriptionAndRequirements("contestDescriptionAndRequirements");

        ContestMultiRoundInformationData multiRoundData = new ContestMultiRoundInformationData();
        multiRoundData.setMilestoneDate(TestHelper.getXMLGregorianCalendar(new Date()));
        contestData.setMultiRoundData(multiRoundData);

        MilestonePrizeData milestonePrizeData = new MilestonePrizeData();
        milestonePrizeData.setNumberOfSubmissions(1);
        milestonePrizeData.setAmount(123.45);
        contestData.setMilestonePrizeData(milestonePrizeData);
        List<ContestPaymentData> payments = new ArrayList<ContestPaymentData>();
        ContestPaymentData data = new ContestPaymentData();
        data.setPaymentReferenceId("paymentReferenceId");
        payments.add(data);
        contestData.setPayments(payments);

        return contestData;
    }

    public List<SimpleContestData> getContestDataOnly(TCSubject tcSubject) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<SimpleContestData> getContestDataOnlyByPID(TCSubject tcSubject, long pid) throws PersistenceException {
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

    public SoftwareCompetition getFullProjectData(TCSubject tcSubject, long projectId) throws ContestServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<ChangeHistoryData> getLatestChanges(TCSubject tcSubject, long contestId) throws PersistenceException {
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

    public List<Permission> getPermissionsByUser(TCSubject tcSubject, long userid) throws PermissionServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Phase> getPhases(TCSubject tcSubject) throws ContestServiceException {
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

    public SoftwareCompetition getSoftwareContestByProjectId(TCSubject tcSubject, long projectId)
            throws ContestServiceException {
        SoftwareCompetition competition = new SoftwareCompetition();
        competition.setClientName("clientName");
        competition.setType(CompetionType.COMPONENT_DEVELOPMENT);
        competition.setStatus(Status.ACTIVE);

        Project projectHeader = new Project();
        projectHeader.setId(projectId);
        projectHeader.setProperty(ProjectPropertyType.BILLING_PROJECT_PROJECT_PROPERTY_KEY, "123");
        competition.setProjectHeader(projectHeader);

        com.topcoder.project.phases.Project projectPhases = new com.topcoder.project.phases.Project();
        projectPhases.setStartDate(new Date());
        if (projectId == 3) {
            com.topcoder.project.phases.Phase phase = new com.topcoder.project.phases.Phase();
            phase.setPhaseType(new PhaseType(projectId, PhaseType.REGISTRATION));
            phase.setLength(1);
            phase.setActualEndDate(new Date(5000));
            phase.setPhaseStatus(PhaseStatus.CLOSED);
            projectPhases.addPhase(phase);
        } else if (projectId == 4) {
            com.topcoder.project.phases.Phase phase = new com.topcoder.project.phases.Phase();
            phase.setPhaseType(new PhaseType(projectId, PhaseType.SUBMISSION));
            phase.setLength(1);
            projectPhases.addPhase(phase);
        } else if (projectId == 103) {
            com.topcoder.project.phases.Phase phase = new com.topcoder.project.phases.Phase();
            phase.setPhaseType(new PhaseType(projectId, PhaseType.REGISTRATION));
            projectPhases.addPhase(phase);
        }

        competition.setProjectPhases(projectPhases);

        FullProjectData projectData = new FullProjectData();
        competition.setProjectData(projectData);
        competition.setId(projectId);

        return competition;
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

    public boolean isPrivate(TCSubject tcSubject, long contestId, boolean isStudio) throws ContestServiceException {
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

    public void markSoftwareContestReadyForReview(TCSubject tcSubject, long projectId) throws ContestServiceException {
        // TODO Auto-generated method stub

    }

    public ContestPaymentResult processContestCreditCardPayment(TCSubject tcSubject, StudioCompetition competition,
            CreditCardPaymentData paymentData) throws PersistenceException, PaymentException,
            ContestNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    public SoftwareContestPaymentResult processContestCreditCardSale(TCSubject tcSubject,
            SoftwareCompetition competition, CreditCardPaymentData paymentData) throws ContestServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public ContestPaymentResult processContestPurchaseOrderPayment(TCSubject tcSubject,
            StudioCompetition competition, TCPurhcaseOrderPaymentData paymentDat) throws PersistenceException,
            PaymentException, ContestNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    public SoftwareContestPaymentResult processContestPurchaseOrderSale(TCSubject tcSubject,
            SoftwareCompetition competition, TCPurhcaseOrderPaymentData paymentData) throws ContestServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public void processMissingPayments(TCSubject tcSubject, long contestId) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    public PaymentResult processSubmissionCreditCardPayment(TCSubject tcSubject,
            CompletedContestData completedContestData, CreditCardPaymentData paymentData) throws PaymentException,
            PersistenceException {
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

    public boolean rankSubmissions(TCSubject tcSubject, long[] submissionIdsInRankOrder) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    public long reOpenSoftwareContest(TCSubject tcSubject, long projectId, long tcDirectProjectId)
            throws ContestServiceException {
        return projectId + 1;
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

    public void removeUserFromAsset(TCSubject tcSubject, long userId, long assetId) throws ContestServiceException {
        // TODO Auto-generated method stub

    }

    public void resubmitForReview(TCSubject tcSubject, long contestId, String contestName, boolean studio,
            long reviewerUserId) throws ContestServiceException {
        // TODO Auto-generated method stub

    }

    public SubmissionData retrieveSubmission(TCSubject tcSubject, long submissionId) throws PersistenceException {
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

    public void setSubmissionPrize(TCSubject tcSubject, long submissionId, long prizeId) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    public void setSubmissionStatus(TCSubject tcSubject, long submissionId, long submissionStatusId, String operator)
            throws ContestServiceException {
        // TODO Auto-generated method stub

    }

    public void updateContest(TCSubject tcSubject, StudioCompetition contest) throws PersistenceException,
            ContestNotFoundException {
        // TODO Auto-generated method stub

    }

    public void updateContestStatus(TCSubject tcSubject, long contestId, long newStatusId)
            throws PersistenceException, StatusNotAllowedException, StatusNotFoundException, ContestNotFoundException {
        // TODO Auto-generated method stub

    }

    public void updatePermissionType(TCSubject tcSubject, PermissionType type) throws PermissionServiceException {
        // TODO Auto-generated method stub

    }

    public void updatePermissions(TCSubject tcSubject, Permission[] permissions) throws PermissionServiceException {
        // TODO Auto-generated method stub

    }

    public SoftwareCompetition updateSoftwareContest(TCSubject tcSubject, SoftwareCompetition contest,
            long tcDirectProjectId) throws ContestServiceException {
        // TODO Auto-generated method stub
        return null;
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
