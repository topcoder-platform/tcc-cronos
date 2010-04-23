/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.accuracytests;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.Phase;
import com.topcoder.catalog.entity.Technology;
import com.topcoder.catalog.service.AssetDTO;

import com.topcoder.clients.model.ProjectContestFee;

import com.topcoder.management.project.DesignComponents;
import com.topcoder.management.review.data.Comment;

import com.topcoder.project.service.FullProjectData;

import com.topcoder.security.TCSubject;

import com.topcoder.service.facade.contest.CommonProjectContestData;
import com.topcoder.service.facade.contest.CommonProjectPermissionData;
import com.topcoder.service.facade.contest.ContestPaymentResult;
import com.topcoder.service.facade.contest.ContestServiceException;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.facade.contest.ContestServiceFilter;
import com.topcoder.project.service.ScorecardReviewData;
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

import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;


/**
 * Mock implementation of ContestServiceFacade.
 *
 * @author onsky
 * @version 1.0
 */
public class MockContestServiceFacade implements ContestServiceFacade {
    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param history This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public void addChangeHistory(TCSubject tcSubject, List<ChangeHistoryData> history)
        throws PersistenceException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param documentId This is a mock.
     * @param contestId This is a mock.
     *
     * @throws PersistenceException This is a mock.
     * @throws ContestNotFoundException This is a mock.
     */
    public void addDocumentToContest(TCSubject tcSubject, long documentId, long contestId)
        throws PersistenceException, ContestNotFoundException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param type This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PermissionServiceException This is a mock.
     */
    public PermissionType addPermissionType(TCSubject tcSubject, PermissionType type)
        throws PermissionServiceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param reviewId This is a mock.
     * @param comment This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public void addReviewComment(TCSubject tcSubject, long reviewId, Comment comment)
        throws ContestServiceException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param projectId This is a mock.
     * @param userId This is a mock.
     *
     * @return This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public long addSubmitter(TCSubject tcSubject, long projectId, long userId)
        throws ContestServiceException {
        return 0;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param userId This is a mock.
     * @param assetId This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public void assignUserToAsset(TCSubject tcSubject, long userId, long assetId)
        throws ContestServiceException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param contest This is a mock.
     * @param tcDirectProjectId This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public StudioCompetition createContest(TCSubject tcSubject, StudioCompetition contest, long tcDirectProjectId)
        throws PersistenceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param projectId This is a mock.
     * @param tcDirectProjectId This is a mock.
     * @param autoDevCreating This is a mock.
     * @param minorVersion This is a mock.
     *
     * @return This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public long createNewVersionForDesignDevContest(TCSubject tcSubject, long projectId, long tcDirectProjectId,
        boolean autoDevCreating, boolean minorVersion)
        throws ContestServiceException {
        return 0;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param contest This is a mock.
     * @param tcDirectProjectId This is a mock.
     *
     * @return This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public SoftwareCompetition createSoftwareContest(TCSubject tcSubject, SoftwareCompetition contest,
        long tcDirectProjectId) throws ContestServiceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param projectId This is a mock.
     *
     * @return This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public FullProjectData createSpecReview(TCSubject tcSubject, long projectId)
        throws ContestServiceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param contestId This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public void deleteContest(TCSubject tcSubject, long contestId)
        throws PersistenceException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     *
     * @return This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public SoftwareCompetition[] findAllTcDirectProjects(TCSubject tcSubject)
        throws ContestServiceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param operator This is a mock.
     *
     * @return This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public SoftwareCompetition[] findAllTcDirectProjectsForUser(TCSubject tcSubject, String operator)
        throws ContestServiceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     *
     * @return This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public List<Category> getActiveCategories(TCSubject tcSubject)
        throws ContestServiceException {
        return new ArrayList<Category>();
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     *
     * @return This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public List<Technology> getActiveTechnologies(TCSubject tcSubject)
        throws ContestServiceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public List<StudioCompetition> getAllContestHeaders(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public List<ContestTypeData> getAllContestTypes(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public List<StudioCompetition> getAllContests(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public List<DocumentType> getAllDocumentTypes(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public List<MediumData> getAllMediums(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PermissionServiceException This is a mock.
     */
    public List<PermissionType> getAllPermissionType(TCSubject tcSubject)
        throws PermissionServiceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public List<StudioFileType> getAllStudioFileTypes(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param contestId This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public List<ChangeHistoryData> getChangeHistory(TCSubject tcSubject, long contestId)
        throws PersistenceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public List<CommonProjectContestData> getCommonProjectContestData(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param pid This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public List<CommonProjectContestData> getCommonProjectContestDataByPID(TCSubject tcSubject, long pid)
        throws PersistenceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param createdUser This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public List<CommonProjectPermissionData> getCommonProjectPermissionDataForUser(TCSubject tcSubject, long createdUser)
        throws PersistenceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param contestId This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     * @throws ContestNotFoundException This is a mock.
     */
    public StudioCompetition getContest(TCSubject tcSubject, long contestId)
        throws PersistenceException, ContestNotFoundException {
        return new StudioCompetition();
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public List<SimpleContestData> getContestDataOnly(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param pid This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public List<SimpleContestData> getContestDataOnlyByPID(TCSubject tcSubject, long pid)
        throws PersistenceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param projectId This is a mock.
     *
     * @return This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public List<ProjectContestFee> getContestFeesByProject(TCSubject tcSubject, long projectId)
        throws ContestServiceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param tcDirectProjectId This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     * @throws ProjectNotFoundException This is a mock.
     */
    public List<StudioCompetition> getContestsForProject(TCSubject tcSubject, long tcDirectProjectId)
        throws PersistenceException, ProjectNotFoundException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     *
     * @return This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public List<DesignComponents> getDesignComponents(TCSubject tcSubject)
        throws ContestServiceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param billingProjectId This is a mock.
     *
     * @return This is a mock.
     */
    public String getEligibilityName(TCSubject tcSubject, long billingProjectId) {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param contestId This is a mock.
     *
     * @return This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public List<SubmissionData> getFinalSubmissionsForContest(TCSubject tcSubject, long contestId)
        throws ContestServiceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param projectId This is a mock.
     *
     * @return This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public SoftwareCompetition getFullProjectData(TCSubject tcSubject, long projectId)
        throws ContestServiceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param contestId This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public List<ChangeHistoryData> getLatestChanges(TCSubject tcSubject, long contestId)
        throws PersistenceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param contestId This is a mock.
     *
     * @return This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public List<SubmissionData> getMilestoneSubmissionsForContest(TCSubject tcSubject, long contestId)
        throws ContestServiceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param contentType This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public long getMimeTypeId(TCSubject tcSubject, String contentType)
        throws PersistenceException {
        return 0;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param userid This is a mock.
     * @param projectid This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PermissionServiceException This is a mock.
     */
    public List<Permission> getPermissions(TCSubject tcSubject, long userid, long projectid)
        throws PermissionServiceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param projectid This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PermissionServiceException This is a mock.
     */
    public List<Permission> getPermissionsByProject(TCSubject tcSubject, long projectid)
        throws PermissionServiceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param userid This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PermissionServiceException This is a mock.
     */
    public List<Permission> getPermissionsByUser(TCSubject tcSubject, long userid)
        throws PermissionServiceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     *
     * @return This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public List<Phase> getPhases(TCSubject tcSubject) throws ContestServiceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param projectId This is a mock.
     *
     * @return This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public ScorecardReviewData getScorecardAndReview(TCSubject tcSubject, long projectId)
        throws ContestServiceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public List<SimpleContestData> getSimpleContestData(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param pid This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public List<SimpleContestData> getSimpleContestDataByPID(TCSubject tcSubject, long pid)
        throws PersistenceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public List<SimpleProjectContestData> getSimpleProjectContestData(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param pid This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public List<SimpleProjectContestData> getSimpleProjectContestDataByPID(TCSubject tcSubject, long pid)
        throws PersistenceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param projectId This is a mock.
     *
     * @return This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public SoftwareCompetition getSoftwareContestByProjectId(TCSubject tcSubject, long projectId)
        throws ContestServiceException {
        SoftwareCompetition comp = new SoftwareCompetition();
        comp.setAssetDTO(new AssetDTO());

        return comp;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param contestId This is a mock.
     * @param studio This is a mock.
     *
     * @return This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public SpecReview getSpecReviews(TCSubject tcSubject, long contestId, boolean studio)
        throws ContestServiceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public List<ContestStatusData> getStatusList(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public String getSubmissionFileTypes(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param userName This is a mock.
     *
     * @return This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public List<StudioCompetition> getUserContests(TCSubject tcSubject, String userName)
        throws ContestServiceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param userId This is a mock.
     * @param contestId This is a mock.
     * @param isStudio This is a mock.
     *
     * @return This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public boolean isEligible(TCSubject tcSubject, long userId, long contestId, boolean isStudio)
        throws ContestServiceException {
        return false;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param contestId This is a mock.
     * @param isStudio This is a mock.
     *
     * @return This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public boolean isPrivate(TCSubject tcSubject, long contestId, boolean isStudio)
        throws ContestServiceException {
        return false;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param submissionId This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public void markForPurchase(TCSubject tcSubject, long submissionId)
        throws PersistenceException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param contestId This is a mock.
     * @param studio This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public void markReadyForReview(TCSubject tcSubject, long contestId, boolean studio)
        throws ContestServiceException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param commentId This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public void markReviewCommentSeen(TCSubject tcSubject, long commentId)
        throws ContestServiceException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param contestId This is a mock.
     * @param contestName This is a mock.
     * @param studio This is a mock.
     * @param tcDirectProjectId This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public void markReviewDone(TCSubject tcSubject, long contestId, String contestName, boolean studio,
        long tcDirectProjectId) throws ContestServiceException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param projectId This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public void markSoftwareContestReadyForReview(TCSubject tcSubject, long projectId)
        throws ContestServiceException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param competition This is a mock.
     * @param paymentData This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     * @throws PaymentException This is a mock.
     * @throws ContestNotFoundException This is a mock.
     */
    public ContestPaymentResult processContestCreditCardPayment(TCSubject tcSubject, StudioCompetition competition,
        CreditCardPaymentData paymentData) throws PersistenceException, PaymentException, ContestNotFoundException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param competition This is a mock.
     * @param paymentData This is a mock.
     *
     * @return This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public SoftwareContestPaymentResult processContestCreditCardSale(TCSubject tcSubject,
        SoftwareCompetition competition, CreditCardPaymentData paymentData)
        throws ContestServiceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param competition This is a mock.
     * @param paymentDat This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     * @throws PaymentException This is a mock.
     * @throws ContestNotFoundException This is a mock.
     */
    public ContestPaymentResult processContestPurchaseOrderPayment(TCSubject tcSubject, StudioCompetition competition,
        TCPurhcaseOrderPaymentData paymentDat) throws PersistenceException, PaymentException, ContestNotFoundException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param competition This is a mock.
     * @param paymentData This is a mock.
     *
     * @return This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public SoftwareContestPaymentResult processContestPurchaseOrderSale(TCSubject tcSubject,
        SoftwareCompetition competition, TCPurhcaseOrderPaymentData paymentData)
        throws ContestServiceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param contestId This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public void processMissingPayments(TCSubject tcSubject, long contestId)
        throws PersistenceException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param completedContestData This is a mock.
     * @param paymentData This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PaymentException This is a mock.
     * @throws PersistenceException This is a mock.
     */
    public PaymentResult processSubmissionCreditCardPayment(TCSubject tcSubject,
        CompletedContestData completedContestData, CreditCardPaymentData paymentData)
        throws PaymentException, PersistenceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param completedContestData This is a mock.
     * @param paymentData This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PaymentException This is a mock.
     * @throws PersistenceException This is a mock.
     */
    public PaymentResult processSubmissionPurchaseOrderPayment(TCSubject tcSubject,
        CompletedContestData completedContestData, TCPurhcaseOrderPaymentData paymentData)
        throws PaymentException, PersistenceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param submissionId This is a mock.
     * @param payPalOrderId This is a mock.
     * @param securityToken This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public void purchaseSubmission(TCSubject tcSubject, long submissionId, String payPalOrderId, String securityToken)
        throws PersistenceException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param submissionId This is a mock.
     * @param submissionPaymentData This is a mock.
     * @param securityToken This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public void purchaseSubmission(TCSubject tcSubject, long submissionId, SubmissionPaymentData submissionPaymentData,
        String securityToken) throws PersistenceException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param submissionIdsInRankOrder This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public boolean rankSubmissions(TCSubject tcSubject, long[] submissionIdsInRankOrder)
        throws PersistenceException {
        return false;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param projectId This is a mock.
     * @param tcDirectProjectId This is a mock.
     *
     * @return This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public long reOpenSoftwareContest(TCSubject tcSubject, long projectId, long tcDirectProjectId)
        throws ContestServiceException {
        return 0;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param documentId This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public boolean removeDocument(TCSubject tcSubject, long documentId)
        throws PersistenceException {
        return false;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param document This is a mock.
     *
     * @throws PersistenceException This is a mock.
     * @throws DocumentNotFoundException This is a mock.
     */
    public void removeDocumentFromContest(TCSubject tcSubject, UploadedDocument document)
        throws PersistenceException, DocumentNotFoundException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param submissionId This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public void removeSubmission(TCSubject tcSubject, long submissionId)
        throws PersistenceException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param userId This is a mock.
     * @param assetId This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public void removeUserFromAsset(TCSubject tcSubject, long userId, long assetId)
        throws ContestServiceException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param contestId This is a mock.
     * @param contestName This is a mock.
     * @param studio This is a mock.
     * @param reviewerUserId This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public void resubmitForReview(TCSubject tcSubject, long contestId, String contestName, boolean studio,
        long reviewerUserId) throws ContestServiceException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param submissionId This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public SubmissionData retrieveSubmission(TCSubject tcSubject, long submissionId)
        throws PersistenceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param contestId This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     * @throws ContestNotFoundException This is a mock.
     */
    public List<SubmissionData> retrieveSubmissionsForContest(TCSubject tcSubject, long contestId)
        throws PersistenceException, ContestNotFoundException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param contestId This is a mock.
     * @param studio This is a mock.
     * @param sectionName This is a mock.
     * @param comment This is a mock.
     * @param role This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public void saveReviewComment(TCSubject tcSubject, long contestId, boolean studio, String sectionName,
        String comment, String role) throws ContestServiceException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param contestId This is a mock.
     * @param studio This is a mock.
     * @param sectionName This is a mock.
     * @param comment This is a mock.
     * @param isPass This is a mock.
     * @param role This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public void saveReviewStatus(TCSubject tcSubject, long contestId, boolean studio, String sectionName,
        String comment, boolean isPass, String role) throws ContestServiceException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param filter This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public List<StudioCompetition> searchContests(TCSubject tcSubject, ContestServiceFilter filter)
        throws PersistenceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param key This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public List<User> searchUser(TCSubject tcSubject, String key)
        throws PersistenceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param submissionId This is a mock.
     * @param milestonePrizeId This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public void setSubmissionMilestonePrize(TCSubject tcSubject, long submissionId, long milestonePrizeId)
        throws ContestServiceException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param submissionId This is a mock.
     * @param placement This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public void setSubmissionPlacement(TCSubject tcSubject, long submissionId, int placement)
        throws PersistenceException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param submissionId This is a mock.
     * @param prizeId This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public void setSubmissionPrize(TCSubject tcSubject, long submissionId, long prizeId)
        throws PersistenceException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param submissionId This is a mock.
     * @param submissionStatusId This is a mock.
     * @param operator This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public void setSubmissionStatus(TCSubject tcSubject, long submissionId, long submissionStatusId, String operator)
        throws ContestServiceException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param contest This is a mock.
     *
     * @throws PersistenceException This is a mock.
     * @throws ContestNotFoundException This is a mock.
     */
    public void updateContest(TCSubject tcSubject, StudioCompetition contest)
        throws PersistenceException, ContestNotFoundException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param contestId This is a mock.
     * @param newStatusId This is a mock.
     *
     * @throws PersistenceException This is a mock.
     * @throws StatusNotAllowedException This is a mock.
     * @throws StatusNotFoundException This is a mock.
     * @throws ContestNotFoundException This is a mock.
     */
    public void updateContestStatus(TCSubject tcSubject, long contestId, long newStatusId)
        throws PersistenceException, StatusNotAllowedException, StatusNotFoundException, ContestNotFoundException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param type This is a mock.
     *
     * @throws PermissionServiceException This is a mock.
     */
    public void updatePermissionType(TCSubject tcSubject, PermissionType type)
        throws PermissionServiceException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param permissions This is a mock.
     *
     * @throws PermissionServiceException This is a mock.
     */
    public void updatePermissions(TCSubject tcSubject, Permission[] permissions)
        throws PermissionServiceException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param contest This is a mock.
     * @param tcDirectProjectId This is a mock.
     *
     * @return This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public SoftwareCompetition updateSoftwareContest(TCSubject tcSubject, SoftwareCompetition contest,
        long tcDirectProjectId) throws ContestServiceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param submission This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public void updateSubmission(TCSubject tcSubject, SubmissionData submission)
        throws PersistenceException {
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param submissionId This is a mock.
     * @param rank This is a mock.
     * @param isRankingMilestone This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public boolean updateSubmissionUserRank(TCSubject tcSubject, long submissionId, int rank, Boolean isRankingMilestone)
        throws PersistenceException {
        return false;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param feedbacks This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public boolean updateSubmissionsFeedback(TCSubject tcSubject, SubmissionFeedback[] feedbacks)
        throws PersistenceException {
        return false;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param uploadedDocument This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     */
    public UploadedDocument uploadDocument(TCSubject tcSubject, UploadedDocument uploadedDocument)
        throws PersistenceException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param uploadedDocument This is a mock.
     *
     * @return This is a mock.
     *
     * @throws PersistenceException This is a mock.
     * @throws ContestNotFoundException This is a mock.
     */
    public UploadedDocument uploadDocumentForContest(TCSubject tcSubject, UploadedDocument uploadedDocument)
        throws PersistenceException, ContestNotFoundException {
        return null;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param projectId This is a mock.
     * @param filename This is a mock.
     * @param finalFix This is a mock.
     *
     * @return This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public long uploadFinalFix(TCSubject tcSubject, long projectId, String filename, DataHandler finalFix)
        throws ContestServiceException {
        return 0;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param projectId This is a mock.
     * @param filename This is a mock.
     * @param submission This is a mock.
     *
     * @return This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public long uploadSubmission(TCSubject tcSubject, long projectId, String filename, DataHandler submission)
        throws ContestServiceException {
        return 0;
    }

    /**
     * This is a mock.
     *
     * @param tcSubject This is a mock.
     * @param projectId This is a mock.
     * @param filename This is a mock.
     * @param testCases This is a mock.
     *
     * @return This is a mock.
     *
     * @throws ContestServiceException This is a mock.
     */
    public long uploadTestCases(TCSubject tcSubject, long projectId, String filename, DataHandler testCases)
        throws ContestServiceException {
        return 0;
    }
}
