/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.accuracytests;

import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
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
import com.topcoder.service.facade.contest.ProjectSummaryData;
import com.topcoder.service.facade.contest.SoftwareContestPaymentResult;
import com.topcoder.service.facade.contest.notification.ProjectNotification;
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


/**
 * Mock implementation.
 *
 * @author onsky
 * @version 1.0
 */
public class MockContestServiceFacade implements ContestServiceFacade {
    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param history A mock!
     *
     * @throws PersistenceException A mock!
     */
    public void addChangeHistory(TCSubject tcSubject, List<ChangeHistoryData> history)
        throws PersistenceException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param documentId A mock!
     * @param contestId A mock!
     *
     * @throws PersistenceException A mock!
     * @throws ContestNotFoundException A mock!
     */
    public void addDocumentToContest(TCSubject tcSubject, long documentId, long contestId)
        throws PersistenceException, ContestNotFoundException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param type A mock!
     *
     * @return A mock!
     *
     * @throws PermissionServiceException A mock!
     */
    public PermissionType addPermissionType(TCSubject tcSubject, PermissionType type)
        throws PermissionServiceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param reviewId A mock!
     * @param comment A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public void addReviewComment(TCSubject tcSubject, long reviewId, Comment comment)
        throws ContestServiceException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param projectId A mock!
     * @param userId A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public long addSubmitter(TCSubject tcSubject, long projectId, long userId)
        throws ContestServiceException {
        return 0;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param userId A mock!
     * @param assetId A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public void assignUserToAsset(TCSubject tcSubject, long userId, long assetId)
        throws ContestServiceException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param contest A mock!
     * @param tcDirectProjectId A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public StudioCompetition createContest(TCSubject tcSubject, StudioCompetition contest, long tcDirectProjectId)
        throws PersistenceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param projectId A mock!
     * @param tcDirectProjectId A mock!
     * @param autoDevCreating A mock!
     * @param minorVersion A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public long createNewVersionForDesignDevContest(TCSubject tcSubject, long projectId, long tcDirectProjectId,
        boolean autoDevCreating, boolean minorVersion)
        throws ContestServiceException {
        return 0;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param contest A mock!
     * @param tcDirectProjectId A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public SoftwareCompetition createSoftwareContest(TCSubject tcSubject, SoftwareCompetition contest,
        long tcDirectProjectId) throws ContestServiceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param projectId A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public FullProjectData createSpecReview(TCSubject tcSubject, long projectId)
        throws ContestServiceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param contestId A mock!
     *
     * @throws PersistenceException A mock!
     */
    public void deleteContest(TCSubject tcSubject, long contestId)
        throws PersistenceException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public SoftwareCompetition[] findAllTcDirectProjects(TCSubject tcSubject)
        throws ContestServiceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param operator A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public SoftwareCompetition[] findAllTcDirectProjectsForUser(TCSubject tcSubject, String operator)
        throws ContestServiceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public List<Category> getActiveCategories(TCSubject tcSubject)
        throws ContestServiceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public List<Technology> getActiveTechnologies(TCSubject tcSubject)
        throws ContestServiceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public List<StudioCompetition> getAllContestHeaders(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public List<ContestTypeData> getAllContestTypes(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public List<StudioCompetition> getAllContests(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public List<DocumentType> getAllDocumentTypes(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public List<MediumData> getAllMediums(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     *
     * @return A mock!
     *
     * @throws PermissionServiceException A mock!
     */
    public List<PermissionType> getAllPermissionType(TCSubject tcSubject)
        throws PermissionServiceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public List<StudioFileType> getAllStudioFileTypes(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param contestId A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public List<ChangeHistoryData> getChangeHistory(TCSubject tcSubject, long contestId)
        throws PersistenceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public List<CommonProjectContestData> getCommonProjectContestData(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param pid A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public List<CommonProjectContestData> getCommonProjectContestDataByPID(TCSubject tcSubject, long pid)
        throws PersistenceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param createdUser A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public List<CommonProjectPermissionData> getCommonProjectPermissionDataForUser(TCSubject tcSubject, long createdUser)
        throws PersistenceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param contestId A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     * @throws ContestNotFoundException A mock!
     */
    public StudioCompetition getContest(TCSubject tcSubject, long contestId)
        throws PersistenceException, ContestNotFoundException {
    	StudioCompetition competition = new StudioCompetition();
    	competition.setStartTime(new XMLGregorianCalendarImpl());
        return competition;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public List<SimpleContestData> getContestDataOnly(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param pid A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public List<SimpleContestData> getContestDataOnlyByPID(TCSubject tcSubject, long pid)
        throws PersistenceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param projectId A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public List<ProjectContestFee> getContestFeesByProject(TCSubject tcSubject, long projectId)
        throws ContestServiceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param tcDirectProjectId A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     * @throws ProjectNotFoundException A mock!
     */
    public List<StudioCompetition> getContestsForProject(TCSubject tcSubject, long tcDirectProjectId)
        throws PersistenceException, ProjectNotFoundException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public List<DesignComponents> getDesignComponents(TCSubject tcSubject)
        throws ContestServiceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param billingProjectId A mock!
     *
     * @return A mock!
     */
    public String getEligibilityName(TCSubject tcSubject, long billingProjectId) {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param contestId A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public List<SubmissionData> getFinalSubmissionsForContest(TCSubject tcSubject, long contestId)
        throws ContestServiceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param projectId A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public SoftwareCompetition getFullProjectData(TCSubject tcSubject, long projectId)
        throws ContestServiceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param contestId A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public List<ChangeHistoryData> getLatestChanges(TCSubject tcSubject, long contestId)
        throws PersistenceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param contestId A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public List<SubmissionData> getMilestoneSubmissionsForContest(TCSubject tcSubject, long contestId)
        throws ContestServiceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param contentType A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public long getMimeTypeId(TCSubject tcSubject, String contentType)
        throws PersistenceException {
        return 0;
    }

    /**
     * A mock!
     *
     * @param subject A mock!
     * @param userId A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public List<ProjectNotification> getNotificationsForUser(TCSubject subject, long userId)
        throws ContestServiceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param userid A mock!
     * @param projectid A mock!
     *
     * @return A mock!
     *
     * @throws PermissionServiceException A mock!
     */
    public List<Permission> getPermissions(TCSubject tcSubject, long userid, long projectid)
        throws PermissionServiceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param projectid A mock!
     *
     * @return A mock!
     *
     * @throws PermissionServiceException A mock!
     */
    public List<Permission> getPermissionsByProject(TCSubject tcSubject, long projectid)
        throws PermissionServiceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param userid A mock!
     *
     * @return A mock!
     *
     * @throws PermissionServiceException A mock!
     */
    public List<Permission> getPermissionsByUser(TCSubject tcSubject, long userid)
        throws PermissionServiceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public List<Phase> getPhases(TCSubject tcSubject) throws ContestServiceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public List<ProjectSummaryData> getProjectData(TCSubject tcSubject)
        throws ContestServiceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param projectId A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public ScorecardReviewData getScorecardAndReview(TCSubject tcSubject, long projectId)
        throws ContestServiceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public List<SimpleContestData> getSimpleContestData(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param pid A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public List<SimpleContestData> getSimpleContestDataByPID(TCSubject tcSubject, long pid)
        throws PersistenceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public List<SimpleProjectContestData> getSimpleProjectContestData(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param pid A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public List<SimpleProjectContestData> getSimpleProjectContestDataByPID(TCSubject tcSubject, long pid)
        throws PersistenceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param projectId A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public SoftwareCompetition getSoftwareContestByProjectId(TCSubject tcSubject, long projectId)
        throws ContestServiceException {
    	FullProjectData project = new FullProjectData();
    	project.setStartDate(new Date());
    	SoftwareCompetition competition = new SoftwareCompetition();
    	competition.setProjectData(project);
        return competition;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param contestId A mock!
     * @param studio A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public SpecReview getSpecReviews(TCSubject tcSubject, long contestId, boolean studio)
        throws ContestServiceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public List<ContestStatusData> getStatusList(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public String getSubmissionFileTypes(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param userName A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public List<StudioCompetition> getUserContests(TCSubject tcSubject, String userName)
        throws ContestServiceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param userId A mock!
     * @param contestId A mock!
     * @param isStudio A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public boolean isEligible(TCSubject tcSubject, long userId, long contestId, boolean isStudio)
        throws ContestServiceException {
        return false;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param contestId A mock!
     * @param isStudio A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public boolean isPrivate(TCSubject tcSubject, long contestId, boolean isStudio)
        throws ContestServiceException {
        return false;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param submissionId A mock!
     *
     * @throws PersistenceException A mock!
     */
    public void markForPurchase(TCSubject tcSubject, long submissionId)
        throws PersistenceException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param contestId A mock!
     * @param studio A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public void markReadyForReview(TCSubject tcSubject, long contestId, boolean studio)
        throws ContestServiceException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param commentId A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public void markReviewCommentSeen(TCSubject tcSubject, long commentId)
        throws ContestServiceException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param contestId A mock!
     * @param contestName A mock!
     * @param studio A mock!
     * @param tcDirectProjectId A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public void markReviewDone(TCSubject tcSubject, long contestId, String contestName, boolean studio,
        long tcDirectProjectId) throws ContestServiceException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param projectId A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public void markSoftwareContestReadyForReview(TCSubject tcSubject, long projectId)
        throws ContestServiceException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param competition A mock!
     * @param paymentData A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     * @throws PaymentException A mock!
     * @throws ContestNotFoundException A mock!
     */
    public ContestPaymentResult processContestCreditCardPayment(TCSubject tcSubject, StudioCompetition competition,
        CreditCardPaymentData paymentData) throws PersistenceException, PaymentException, ContestNotFoundException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param competition A mock!
     * @param paymentData A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public SoftwareContestPaymentResult processContestCreditCardSale(TCSubject tcSubject,
        SoftwareCompetition competition, CreditCardPaymentData paymentData)
        throws ContestServiceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param competition A mock!
     * @param paymentDat A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     * @throws PaymentException A mock!
     * @throws ContestNotFoundException A mock!
     */
    public ContestPaymentResult processContestPurchaseOrderPayment(TCSubject tcSubject, StudioCompetition competition,
        TCPurhcaseOrderPaymentData paymentDat) throws PersistenceException, PaymentException, ContestNotFoundException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param competition A mock!
     * @param paymentData A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public SoftwareContestPaymentResult processContestPurchaseOrderSale(TCSubject tcSubject,
        SoftwareCompetition competition, TCPurhcaseOrderPaymentData paymentData)
        throws ContestServiceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param contestId A mock!
     *
     * @throws PersistenceException A mock!
     */
    public void processMissingPayments(TCSubject tcSubject, long contestId)
        throws PersistenceException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param completedContestData A mock!
     * @param paymentData A mock!
     *
     * @return A mock!
     *
     * @throws PaymentException A mock!
     * @throws PersistenceException A mock!
     */
    public PaymentResult processSubmissionCreditCardPayment(TCSubject tcSubject,
        CompletedContestData completedContestData, CreditCardPaymentData paymentData)
        throws PaymentException, PersistenceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param completedContestData A mock!
     * @param paymentData A mock!
     *
     * @return A mock!
     *
     * @throws PaymentException A mock!
     * @throws PersistenceException A mock!
     */
    public PaymentResult processSubmissionPurchaseOrderPayment(TCSubject tcSubject,
        CompletedContestData completedContestData, TCPurhcaseOrderPaymentData paymentData)
        throws PaymentException, PersistenceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param submissionId A mock!
     * @param submissionPaymentData A mock!
     * @param securityToken A mock!
     *
     * @throws PersistenceException A mock!
     */
    public void purchaseSubmission(TCSubject tcSubject, long submissionId, SubmissionPaymentData submissionPaymentData,
        String securityToken) throws PersistenceException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param submissionIdsInRankOrder A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public boolean rankSubmissions(TCSubject tcSubject, long[] submissionIdsInRankOrder)
        throws PersistenceException {
        return false;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param projectId A mock!
     * @param tcDirectProjectId A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public long reOpenSoftwareContest(TCSubject tcSubject, long projectId, long tcDirectProjectId)
        throws ContestServiceException {
        return 0;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param documentId A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public boolean removeDocument(TCSubject tcSubject, long documentId)
        throws PersistenceException {
        return false;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param document A mock!
     *
     * @throws PersistenceException A mock!
     * @throws DocumentNotFoundException A mock!
     */
    public void removeDocumentFromContest(TCSubject tcSubject, UploadedDocument document)
        throws PersistenceException, DocumentNotFoundException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param submissionId A mock!
     *
     * @throws PersistenceException A mock!
     */
    public void removeSubmission(TCSubject tcSubject, long submissionId)
        throws PersistenceException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param userId A mock!
     * @param assetId A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public void removeUserFromAsset(TCSubject tcSubject, long userId, long assetId)
        throws ContestServiceException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param contestId A mock!
     * @param contestName A mock!
     * @param studio A mock!
     * @param reviewerUserId A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public void resubmitForReview(TCSubject tcSubject, long contestId, String contestName, boolean studio,
        long reviewerUserId) throws ContestServiceException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param submissionId A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public SubmissionData retrieveSubmission(TCSubject tcSubject, long submissionId)
        throws PersistenceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param contestId A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     * @throws ContestNotFoundException A mock!
     */
    public List<SubmissionData> retrieveSubmissionsForContest(TCSubject tcSubject, long contestId)
        throws PersistenceException, ContestNotFoundException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param contestId A mock!
     * @param studio A mock!
     * @param sectionName A mock!
     * @param comment A mock!
     * @param role A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public void saveReviewComment(TCSubject tcSubject, long contestId, boolean studio, String sectionName,
        String comment, String role) throws ContestServiceException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param contestId A mock!
     * @param studio A mock!
     * @param sectionName A mock!
     * @param comment A mock!
     * @param isPass A mock!
     * @param role A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public void saveReviewStatus(TCSubject tcSubject, long contestId, boolean studio, String sectionName,
        String comment, boolean isPass, String role) throws ContestServiceException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param filter A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public List<StudioCompetition> searchContests(TCSubject tcSubject, ContestServiceFilter filter)
        throws PersistenceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param key A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public List<User> searchUser(TCSubject tcSubject, String key)
        throws PersistenceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param submissionId A mock!
     * @param milestonePrizeId A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public void setSubmissionMilestonePrize(TCSubject tcSubject, long submissionId, long milestonePrizeId)
        throws ContestServiceException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param submissionId A mock!
     * @param placement A mock!
     *
     * @throws PersistenceException A mock!
     */
    public void setSubmissionPlacement(TCSubject tcSubject, long submissionId, int placement)
        throws PersistenceException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param submissionId A mock!
     * @param prizeId A mock!
     *
     * @throws PersistenceException A mock!
     */
    public void setSubmissionPrize(TCSubject tcSubject, long submissionId, long prizeId)
        throws PersistenceException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param submissionId A mock!
     * @param submissionStatusId A mock!
     * @param operator A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public void setSubmissionStatus(TCSubject tcSubject, long submissionId, long submissionStatusId, String operator)
        throws ContestServiceException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param contest A mock!
     *
     * @throws PersistenceException A mock!
     * @throws ContestNotFoundException A mock!
     */
    public void updateContest(TCSubject tcSubject, StudioCompetition contest)
        throws PersistenceException, ContestNotFoundException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param contestId A mock!
     * @param newStatusId A mock!
     *
     * @throws PersistenceException A mock!
     * @throws StatusNotAllowedException A mock!
     * @throws StatusNotFoundException A mock!
     * @throws ContestNotFoundException A mock!
     */
    public void updateContestStatus(TCSubject tcSubject, long contestId, long newStatusId)
        throws PersistenceException, StatusNotAllowedException, StatusNotFoundException, ContestNotFoundException {
    }

    /**
     * A mock!
     *
     * @param subject A mock!
     * @param userId A mock!
     * @param notifications A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public void updateNotificationsForUser(TCSubject subject, long userId, List<ProjectNotification> notifications)
        throws ContestServiceException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param type A mock!
     *
     * @throws PermissionServiceException A mock!
     */
    public void updatePermissionType(TCSubject tcSubject, PermissionType type)
        throws PermissionServiceException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param permissions A mock!
     *
     * @throws PermissionServiceException A mock!
     */
    public void updatePermissions(TCSubject tcSubject, Permission[] permissions)
        throws PermissionServiceException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param contest A mock!
     * @param tcDirectProjectId A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public SoftwareCompetition updateSoftwareContest(TCSubject tcSubject, SoftwareCompetition contest,
        long tcDirectProjectId) throws ContestServiceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param submission A mock!
     *
     * @throws PersistenceException A mock!
     */
    public void updateSubmission(TCSubject tcSubject, SubmissionData submission)
        throws PersistenceException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param submissionId A mock!
     * @param rank A mock!
     * @param isRankingMilestone A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public boolean updateSubmissionUserRank(TCSubject tcSubject, long submissionId, int rank, Boolean isRankingMilestone)
        throws PersistenceException {
        return false;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param feedbacks A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public boolean updateSubmissionsFeedback(TCSubject tcSubject, SubmissionFeedback[] feedbacks)
        throws PersistenceException {
        return false;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param uploadedDocument A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     */
    public UploadedDocument uploadDocument(TCSubject tcSubject, UploadedDocument uploadedDocument)
        throws PersistenceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param uploadedDocument A mock!
     *
     * @return A mock!
     *
     * @throws PersistenceException A mock!
     * @throws ContestNotFoundException A mock!
     */
    public UploadedDocument uploadDocumentForContest(TCSubject tcSubject, UploadedDocument uploadedDocument)
        throws PersistenceException, ContestNotFoundException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param projectId A mock!
     * @param filename A mock!
     * @param finalFix A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public long uploadFinalFix(TCSubject tcSubject, long projectId, String filename, DataHandler finalFix)
        throws ContestServiceException {
        return 0;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param projectId A mock!
     * @param filename A mock!
     * @param submission A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public long uploadSubmission(TCSubject tcSubject, long projectId, String filename, DataHandler submission)
        throws ContestServiceException {
        return 0;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param projectId A mock!
     * @param filename A mock!
     * @param testCases A mock!
     *
     * @return A mock!
     *
     * @throws ContestServiceException A mock!
     */
    public long uploadTestCases(TCSubject tcSubject, long projectId, String filename, DataHandler testCases)
        throws ContestServiceException {
        return 0;
    }
}
