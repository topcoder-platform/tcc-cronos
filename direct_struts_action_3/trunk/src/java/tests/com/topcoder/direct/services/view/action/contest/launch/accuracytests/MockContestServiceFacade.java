/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch.accuracytests;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.Phase;
import com.topcoder.catalog.entity.Technology;
import com.topcoder.catalog.service.AssetDTO;

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
import com.topcoder.service.studio.ContestData;
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
 * DOCUMENT ME!
 *
 * @author KLW
 * @version 1.0
 */
public class MockContestServiceFacade implements ContestServiceFacade {
    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contest DOCUMENT ME!
     * @param tcDirectProjectId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public StudioCompetition createContest(TCSubject tcSubject, StudioCompetition contest, long tcDirectProjectId)
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contestId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     * @throws ContestNotFoundException DOCUMENT ME!
     */
    
    public StudioCompetition getContest(TCSubject tcSubject, long contestId)
        throws PersistenceException, ContestNotFoundException {
        if (contestId == 1) {
            ContestData data = new ContestData();
            data.setName("testcontest");

            StudioCompetition competition = new StudioCompetition(data);
            competition.setCategory("Java");

            return competition;
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param tcDirectProjectId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     * @throws ProjectNotFoundException DOCUMENT ME!
     */
    
    public List<StudioCompetition> getContestsForProject(TCSubject tcSubject, long tcDirectProjectId)
        throws PersistenceException, ProjectNotFoundException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contest DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     * @throws ContestNotFoundException DOCUMENT ME!
     */
    
    public void updateContest(TCSubject tcSubject, StudioCompetition contest)
        throws PersistenceException, ContestNotFoundException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contestId DOCUMENT ME!
     * @param newStatusId DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     * @throws StatusNotAllowedException DOCUMENT ME!
     * @throws StatusNotFoundException DOCUMENT ME!
     * @throws ContestNotFoundException DOCUMENT ME!
     */
    
    public void updateContestStatus(TCSubject tcSubject, long contestId, long newStatusId)
        throws PersistenceException, StatusNotAllowedException, StatusNotFoundException, ContestNotFoundException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param uploadedDocument DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     * @throws ContestNotFoundException DOCUMENT ME!
     */
    
    public UploadedDocument uploadDocumentForContest(TCSubject tcSubject, UploadedDocument uploadedDocument)
        throws PersistenceException, ContestNotFoundException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param uploadedDocument DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public UploadedDocument uploadDocument(TCSubject tcSubject, UploadedDocument uploadedDocument)
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param documentId DOCUMENT ME!
     * @param contestId DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     * @throws ContestNotFoundException DOCUMENT ME!
     */
    
    public void addDocumentToContest(TCSubject tcSubject, long documentId, long contestId)
        throws PersistenceException, ContestNotFoundException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param document DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     * @throws DocumentNotFoundException DOCUMENT ME!
     */
    
    public void removeDocumentFromContest(TCSubject tcSubject, UploadedDocument document)
        throws PersistenceException, DocumentNotFoundException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contestId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     * @throws ContestNotFoundException DOCUMENT ME!
     */
    
    public List<SubmissionData> retrieveSubmissionsForContest(TCSubject tcSubject, long contestId)
        throws PersistenceException, ContestNotFoundException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param submission DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public void updateSubmission(TCSubject tcSubject, SubmissionData submission)
        throws PersistenceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param submissionId DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public void removeSubmission(TCSubject tcSubject, long submissionId)
        throws PersistenceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public List<ContestStatusData> getStatusList(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public String getSubmissionFileTypes(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public List<StudioCompetition> getAllContests(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public List<SimpleContestData> getSimpleContestData(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param pid DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public List<SimpleContestData> getSimpleContestDataByPID(TCSubject tcSubject, long pid)
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public List<SimpleProjectContestData> getSimpleProjectContestData(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public List<CommonProjectContestData> getCommonProjectContestData(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param pid DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public List<CommonProjectContestData> getCommonProjectContestDataByPID(TCSubject tcSubject, long pid)
        throws PersistenceException {
        if (pid == 1) {
            List<CommonProjectContestData> list = new ArrayList<CommonProjectContestData>();
            CommonProjectContestData data = new CommonProjectContestData();
            data.setProjectId(1L);
            list.add(data);

            return list;
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public List<ProjectSummaryData> getProjectData(TCSubject tcSubject)
        throws ContestServiceException {
        List<ProjectSummaryData> list = new ArrayList<ProjectSummaryData>();
        ProjectSummaryData data1 = new ProjectSummaryData();
        data1.setProjectId(1L);
        list.add(data1);

        ProjectSummaryData data2 = new ProjectSummaryData();
        data2.setProjectId(1L);
        list.add(data2);

        return list;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param filter DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public List<StudioCompetition> searchContests(TCSubject tcSubject, ContestServiceFilter filter)
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param submissionId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public SubmissionData retrieveSubmission(TCSubject tcSubject, long submissionId)
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public List<ContestTypeData> getAllContestTypes(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param documentId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public boolean removeDocument(TCSubject tcSubject, long documentId)
        throws PersistenceException {
        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contentType DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public long getMimeTypeId(TCSubject tcSubject, String contentType)
        throws PersistenceException {
        return 0;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param submissionId DOCUMENT ME!
     * @param submissionPaymentData DOCUMENT ME!
     * @param securityToken DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public void purchaseSubmission(TCSubject tcSubject, long submissionId, SubmissionPaymentData submissionPaymentData,
        String securityToken) throws PersistenceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public List<MediumData> getAllMediums(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param submissionId DOCUMENT ME!
     * @param placement DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public void setSubmissionPlacement(TCSubject tcSubject, long submissionId, int placement)
        throws PersistenceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param submissionId DOCUMENT ME!
     * @param prizeId DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public void setSubmissionPrize(TCSubject tcSubject, long submissionId, long prizeId)
        throws PersistenceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param submissionId DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public void markForPurchase(TCSubject tcSubject, long submissionId)
        throws PersistenceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param history DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public void addChangeHistory(TCSubject tcSubject, List<ChangeHistoryData> history)
        throws PersistenceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contestId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public List<ChangeHistoryData> getChangeHistory(TCSubject tcSubject, long contestId)
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contestId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public List<ChangeHistoryData> getLatestChanges(TCSubject tcSubject, long contestId)
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contestId DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public void deleteContest(TCSubject tcSubject, long contestId)
        throws PersistenceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public List<StudioCompetition> getAllContestHeaders(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contestId DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public void processMissingPayments(TCSubject tcSubject, long contestId)
        throws PersistenceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public List<DocumentType> getAllDocumentTypes(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public List<StudioFileType> getAllStudioFileTypes(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public List<SimpleContestData> getContestDataOnly(TCSubject tcSubject)
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param pid DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public List<SimpleContestData> getContestDataOnlyByPID(TCSubject tcSubject, long pid)
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param userid DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PermissionServiceException DOCUMENT ME!
     */
    
    public List<Permission> getPermissionsByUser(TCSubject tcSubject, long userid)
        throws PermissionServiceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param projectid DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PermissionServiceException DOCUMENT ME!
     */
    
    public List<Permission> getPermissionsByProject(TCSubject tcSubject, long projectid)
        throws PermissionServiceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param userid DOCUMENT ME!
     * @param projectid DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PermissionServiceException DOCUMENT ME!
     */
    
    public List<Permission> getPermissions(TCSubject tcSubject, long userid, long projectid)
        throws PermissionServiceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PermissionServiceException DOCUMENT ME!
     */
    
    public List<PermissionType> getAllPermissionType(TCSubject tcSubject)
        throws PermissionServiceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param type DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PermissionServiceException DOCUMENT ME!
     */
    
    public PermissionType addPermissionType(TCSubject tcSubject, PermissionType type)
        throws PermissionServiceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param type DOCUMENT ME!
     *
     * @throws PermissionServiceException DOCUMENT ME!
     */
    
    public void updatePermissionType(TCSubject tcSubject, PermissionType type)
        throws PermissionServiceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param competition DOCUMENT ME!
     * @param paymentData DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     * @throws PaymentException DOCUMENT ME!
     * @throws ContestNotFoundException DOCUMENT ME!
     */
    
    public ContestPaymentResult processContestCreditCardPayment(TCSubject tcSubject, StudioCompetition competition,
        CreditCardPaymentData paymentData) throws PersistenceException, PaymentException, ContestNotFoundException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param competition DOCUMENT ME!
     * @param paymentDat DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     * @throws PaymentException DOCUMENT ME!
     * @throws ContestNotFoundException DOCUMENT ME!
     */
    
    public ContestPaymentResult processContestPurchaseOrderPayment(TCSubject tcSubject, StudioCompetition competition,
        TCPurhcaseOrderPaymentData paymentDat) throws PersistenceException, PaymentException, ContestNotFoundException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param competition DOCUMENT ME!
     * @param paymentData DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public SoftwareContestPaymentResult processContestCreditCardSale(TCSubject tcSubject,
        SoftwareCompetition competition, CreditCardPaymentData paymentData)
        throws ContestServiceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param competition DOCUMENT ME!
     * @param paymentData DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public SoftwareContestPaymentResult processContestPurchaseOrderSale(TCSubject tcSubject,
        SoftwareCompetition competition, TCPurhcaseOrderPaymentData paymentData)
        throws ContestServiceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param completedContestData DOCUMENT ME!
     * @param paymentData DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PaymentException DOCUMENT ME!
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public PaymentResult processSubmissionCreditCardPayment(TCSubject tcSubject,
        CompletedContestData completedContestData, CreditCardPaymentData paymentData)
        throws PaymentException, PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param completedContestData DOCUMENT ME!
     * @param paymentData DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PaymentException DOCUMENT ME!
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public PaymentResult processSubmissionPurchaseOrderPayment(TCSubject tcSubject,
        CompletedContestData completedContestData, TCPurhcaseOrderPaymentData paymentData)
        throws PaymentException, PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param submissionIdsInRankOrder DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public boolean rankSubmissions(TCSubject tcSubject, long[] submissionIdsInRankOrder)
        throws PersistenceException {
        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param feedbacks DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public boolean updateSubmissionsFeedback(TCSubject tcSubject, SubmissionFeedback[] feedbacks)
        throws PersistenceException {
        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public List<Category> getActiveCategories(TCSubject tcSubject)
        throws ContestServiceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public List<Technology> getActiveTechnologies(TCSubject tcSubject)
        throws ContestServiceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public List<Phase> getPhases(TCSubject tcSubject) throws ContestServiceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contest DOCUMENT ME!
     * @param tcDirectProjectId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public SoftwareCompetition createSoftwareContest(TCSubject tcSubject, SoftwareCompetition contest,
        long tcDirectProjectId) throws ContestServiceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param projectId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public SoftwareCompetition getSoftwareContestByProjectId(TCSubject tcSubject, long projectId)
        throws ContestServiceException {
        if (projectId == 1) {
            SoftwareCompetition competition = new SoftwareCompetition();
            AssetDTO assetDTO = new AssetDTO();
            assetDTO.setName("testcontest");
            competition.setAssetDTO(assetDTO);

            Category category = new Category();
            category.setName("Java");
            competition.setCategory(category);

            return competition;
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contest DOCUMENT ME!
     * @param tcDirectProjectId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public SoftwareCompetition updateSoftwareContest(TCSubject tcSubject, SoftwareCompetition contest,
        long tcDirectProjectId) throws ContestServiceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param userId DOCUMENT ME!
     * @param assetId DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public void assignUserToAsset(TCSubject tcSubject, long userId, long assetId)
        throws ContestServiceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param userId DOCUMENT ME!
     * @param assetId DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public void removeUserFromAsset(TCSubject tcSubject, long userId, long assetId)
        throws ContestServiceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public SoftwareCompetition[] findAllTcDirectProjects(TCSubject tcSubject)
        throws ContestServiceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param operator DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public SoftwareCompetition[] findAllTcDirectProjectsForUser(TCSubject tcSubject, String operator)
        throws ContestServiceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param projectId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public SoftwareCompetition getFullProjectData(TCSubject tcSubject, long projectId)
        throws ContestServiceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param projectId DOCUMENT ME!
     * @param filename DOCUMENT ME!
     * @param submission DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public long uploadSubmission(TCSubject tcSubject, long projectId, String filename, DataHandler submission)
        throws ContestServiceException {
        return 0;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param projectId DOCUMENT ME!
     * @param filename DOCUMENT ME!
     * @param finalFix DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public long uploadFinalFix(TCSubject tcSubject, long projectId, String filename, DataHandler finalFix)
        throws ContestServiceException {
        return 0;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param projectId DOCUMENT ME!
     * @param filename DOCUMENT ME!
     * @param testCases DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public long uploadTestCases(TCSubject tcSubject, long projectId, String filename, DataHandler testCases)
        throws ContestServiceException {
        return 0;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param submissionId DOCUMENT ME!
     * @param submissionStatusId DOCUMENT ME!
     * @param operator DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public void setSubmissionStatus(TCSubject tcSubject, long submissionId, long submissionStatusId, String operator)
        throws ContestServiceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param projectId DOCUMENT ME!
     * @param userId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public long addSubmitter(TCSubject tcSubject, long projectId, long userId)
        throws ContestServiceException {
        return 0;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param submissionId DOCUMENT ME!
     * @param rank DOCUMENT ME!
     * @param isRankingMilestone DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public boolean updateSubmissionUserRank(TCSubject tcSubject, long submissionId, int rank, Boolean isRankingMilestone)
        throws PersistenceException {
        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param createdUser DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public List<CommonProjectPermissionData> getCommonProjectPermissionDataForUser(TCSubject tcSubject, long createdUser)
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param key DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public List<User> searchUser(TCSubject tcSubject, String key)
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param permissions DOCUMENT ME!
     *
     * @throws PermissionServiceException DOCUMENT ME!
     */
    
    public void updatePermissions(TCSubject tcSubject, Permission[] permissions)
        throws PermissionServiceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param projectId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public List<ProjectContestFee> getContestFeesByProject(TCSubject tcSubject, long projectId)
        throws ContestServiceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contestId DOCUMENT ME!
     * @param studio DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public SpecReview getSpecReviews(TCSubject tcSubject, long contestId, boolean studio)
        throws ContestServiceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contestId DOCUMENT ME!
     * @param studio DOCUMENT ME!
     * @param sectionName DOCUMENT ME!
     * @param comment DOCUMENT ME!
     * @param isPass DOCUMENT ME!
     * @param role DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public void saveReviewStatus(TCSubject tcSubject, long contestId, boolean studio, String sectionName,
        String comment, boolean isPass, String role) throws ContestServiceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contestId DOCUMENT ME!
     * @param studio DOCUMENT ME!
     * @param sectionName DOCUMENT ME!
     * @param comment DOCUMENT ME!
     * @param role DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public void saveReviewComment(TCSubject tcSubject, long contestId, boolean studio, String sectionName,
        String comment, String role) throws ContestServiceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param commentId DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public void markReviewCommentSeen(TCSubject tcSubject, long commentId)
        throws ContestServiceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contestId DOCUMENT ME!
     * @param contestName DOCUMENT ME!
     * @param studio DOCUMENT ME!
     * @param tcDirectProjectId DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public void markReviewDone(TCSubject tcSubject, long contestId, String contestName, boolean studio,
        long tcDirectProjectId) throws ContestServiceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contestId DOCUMENT ME!
     * @param studio DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public void markReadyForReview(TCSubject tcSubject, long contestId, boolean studio)
        throws ContestServiceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contestId DOCUMENT ME!
     * @param contestName DOCUMENT ME!
     * @param studio DOCUMENT ME!
     * @param reviewerUserId DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public void resubmitForReview(TCSubject tcSubject, long contestId, String contestName, boolean studio,
        long reviewerUserId) throws ContestServiceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param userName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public List<StudioCompetition> getUserContests(TCSubject tcSubject, String userName)
        throws ContestServiceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contestId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public List<SubmissionData> getMilestoneSubmissionsForContest(TCSubject tcSubject, long contestId)
        throws ContestServiceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contestId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public List<SubmissionData> getFinalSubmissionsForContest(TCSubject tcSubject, long contestId)
        throws ContestServiceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param submissionId DOCUMENT ME!
     * @param milestonePrizeId DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public void setSubmissionMilestonePrize(TCSubject tcSubject, long submissionId, long milestonePrizeId)
        throws ContestServiceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public List<DesignComponents> getDesignComponents(TCSubject tcSubject)
        throws ContestServiceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param userId DOCUMENT ME!
     * @param contestId DOCUMENT ME!
     * @param isStudio DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public boolean isEligible(TCSubject tcSubject, long userId, long contestId, boolean isStudio)
        throws ContestServiceException {
        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param billingProjectId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    
    public String getEligibilityName(TCSubject tcSubject, long billingProjectId) {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param projectId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public FullProjectData createSpecReview(TCSubject tcSubject, long projectId)
        throws ContestServiceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param projectId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public ScorecardReviewData getScorecardAndReview(TCSubject tcSubject, long projectId)
        throws ContestServiceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param projectId DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public void markSoftwareContestReadyForReview(TCSubject tcSubject, long projectId)
        throws ContestServiceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param reviewId DOCUMENT ME!
     * @param comment DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public void addReviewComment(TCSubject tcSubject, long reviewId, Comment comment)
        throws ContestServiceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param projectId DOCUMENT ME!
     * @param tcDirectProjectId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public long reOpenSoftwareContest(TCSubject tcSubject, long projectId, long tcDirectProjectId)
        throws ContestServiceException {
        return 0;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param projectId DOCUMENT ME!
     * @param tcDirectProjectId DOCUMENT ME!
     * @param autoDevCreating DOCUMENT ME!
     * @param minorVersion DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public long createNewVersionForDesignDevContest(TCSubject tcSubject, long projectId, long tcDirectProjectId,
        boolean autoDevCreating, boolean minorVersion)
        throws ContestServiceException {
        return 0;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param pid DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    
    public List<SimpleProjectContestData> getSimpleProjectContestDataByPID(TCSubject tcSubject, long pid)
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contestId DOCUMENT ME!
     * @param isStudio DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public boolean isPrivate(TCSubject tcSubject, long contestId, boolean isStudio)
        throws ContestServiceException {
        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param subject DOCUMENT ME!
     * @param userId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public List<ProjectNotification> getNotificationsForUser(TCSubject subject, long userId)
        throws ContestServiceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param subject DOCUMENT ME!
     * @param userId DOCUMENT ME!
     * @param notifications DOCUMENT ME!
     *
     * @throws ContestServiceException DOCUMENT ME!
     */
    
    public void updateNotificationsForUser(TCSubject subject, long userId, List<ProjectNotification> notifications)
        throws ContestServiceException {
    }
}
