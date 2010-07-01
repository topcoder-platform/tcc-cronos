/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;

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
import com.topcoder.service.payment.PaymentResult;
import com.topcoder.service.payment.TCPurhcaseOrderPaymentData;
import com.topcoder.service.permission.Permission;
import com.topcoder.service.permission.PermissionType;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.specreview.SpecReview;
import com.topcoder.service.studio.ChangeHistoryData;
import com.topcoder.service.studio.CompletedContestData;
import com.topcoder.service.studio.ContestNotFoundException;
import com.topcoder.service.studio.ContestStatusData;
import com.topcoder.service.studio.ContestTypeData;
import com.topcoder.service.studio.MediumData;
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
 * <p>
 * The mock <code>ContestServiceFacade</code> used in the unit tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockContestServiceFacade implements ContestServiceFacade {

    /**
     * A flag used in unit testing to indicate an exception should be thrown when getting project data.
     */
    private static boolean failWhenGettingProjectData;

    /**
     * Represents studio competitions used for unit testing.
     */
    private static Map<Long, StudioCompetition> studioCompetitions = new HashMap<Long, StudioCompetition>();

    /**
     * Represents software competitions used for unit testing.
     */
    private static Map<Long, SoftwareCompetition> softwareCompetitions = new HashMap<Long, SoftwareCompetition>();

    /**
     * Creates a new instance of this class.
     */
    public MockContestServiceFacade() {
    }

    /**
     * Getter for studio competitions used for unit testing.
     *
     * @return studio competitions used for unit testing
     */
    public static Map<Long, StudioCompetition> getStudioCompetitions() {
        return studioCompetitions;
    }

    /**
     * Getter for software competitions used for unit testing.
     *
     * @return software competitions used for unit testing
     */
    public static Map<Long, SoftwareCompetition> getSoftwareCompetitions() {
        return softwareCompetitions;
    }

    /**
     * Sets the flag to indicate an exception should be thrown when getting project data (if true).
     *
     * @param val the flag
     */
    public static void setFailWhenGettingProjectData(boolean val) {
        failWhenGettingProjectData = val;
    }

    /**
     * Prepares the mock studio competitions.
     */
    private static void prepareStudioComps() {
        StudioCompetition studioComp = new StudioCompetition();
        studioComp.setId(1);
        studioComp.setCategory("studio category 1");
        studioComp.getContestData().setName("studio comp1");
        studioCompetitions.put(studioComp.getId(), studioComp);

        studioComp = new StudioCompetition();
        studioComp.setId(2);
        studioComp.setCategory("studio category 2");
        studioComp.getContestData().setName("studio comp2");
        studioCompetitions.put(studioComp.getId(), studioComp);

        studioComp = new StudioCompetition();
        studioComp.setId(3);
        studioComp.setCategory("studio category 3");
        studioCompetitions.put(studioComp.getId(), studioComp);

        studioComp = new StudioCompetition();
        studioComp.setId(4);
        studioComp.getContestData().setName("studio comp4");
        studioCompetitions.put(studioComp.getId(), studioComp);
    }

    /**
     * Prepares the mock software competitions.
     */
    private static void prepareSoftwareComps() {
        SoftwareCompetition softwareComp = new SoftwareCompetition();
        softwareComp.setId(1);
        AssetDTO assetDTO = new AssetDTO();
        assetDTO.setName("software comp1");
        softwareComp.setAssetDTO(assetDTO);
        Category cat = new Category();
        cat.setName("software category 1");
        softwareComp.setCategory(cat);
        softwareCompetitions.put(softwareComp.getId(), softwareComp);

        softwareComp = new SoftwareCompetition();
        softwareComp.setId(2);
        assetDTO = new AssetDTO();
        assetDTO.setName("software comp2");
        softwareComp.setAssetDTO(assetDTO);
        cat = new Category();
        cat.setName("software category 2");
        softwareComp.setCategory(cat);
        softwareCompetitions.put(softwareComp.getId(), softwareComp);

        softwareComp = new SoftwareCompetition();
        softwareComp.setId(3);
        cat = new Category();
        cat.setName("software category 3");
        softwareComp.setCategory(cat);
        softwareCompetitions.put(softwareComp.getId(), softwareComp);

        softwareComp = new SoftwareCompetition();
        softwareComp.setId(4);
        assetDTO = new AssetDTO();
        assetDTO.setName("software comp4");
        softwareComp.setAssetDTO(assetDTO);
        softwareCompetitions.put(softwareComp.getId(), softwareComp);
    }

    /**
     * Prepares the mock data to use for unit testing.
     */
    public static void initMockData() {
        prepareStudioComps();
        prepareSoftwareComps();
        failWhenGettingProjectData = false;
    }

    /**
     * Clears mock data used for unit testing.
     */
    public static void clearMockData() {
        studioCompetitions.clear();
        softwareCompetitions.clear();
    }

    /**
     * <p>
     * Adds the specified list of history data for the associated contest.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param history a <code>List</code> of history data for a contest.
     */
    public void addChangeHistory(TCSubject tcSubject, List<ChangeHistoryData> history) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Associates the specified document with specified contest.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param documentId
     *            a <code>long</code> providing the ID of a document to be
     *            associated with specified contest.
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to associate
     *            the specified document with.
     */
    public void addDocumentToContest(TCSubject tcSubject, long documentId, long contestId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * This method will add a permission type, and return the added type entity.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param type
     *            the permission type to add.
     *
     * @return the added permission type entity
     */
    public PermissionType addPermissionType(TCSubject tcSubject, PermissionType type) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method adds a review comment to a review. It simply delegates all logic to underlying services.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param reviewId the review id to add the comment to
     * @param comment the review comment to add
     */
    public void addReviewComment(TCSubject tcSubject, long reviewId, Comment comment) {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds the given user as a new submitter to the given project id.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId
     *            the project to which the user needs to be added
     * @param userId
     *            the user to be added
     *
     * @return the added resource id
     */
    public long addSubmitter(TCSubject tcSubject, long projectId, long userId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Assigns a specified user to a specified <code>assetDTO</code>.
     * </p>
     *
     * <p>
     * If the user already assigned to the asset, this method simply does nothing.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param userId
     *            the id of the user
     * @param assetId
     *            the id of the assetDTO
     *
     */
    public void assignUserToAsset(TCSubject tcSubject, long userId, long assetId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Creates new contest for specified project. Upon creation an unique ID is generated and assigned to
     * returned contest.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contest
     *            a <code>StudioCompetition</code> providing the data for new
     *            contest to be created.
     * @param tcDirectProjectId
     *            a <code>long</code> providing the ID of a project the new
     *            competition belongs to.
     * @return a <code>StudioCompetition</code> providing the data for created
     *         contest and having the ID auto-generated.
     */
    public StudioCompetition createContest(TCSubject tcSubject, StudioCompetition contest, long tcDirectProjectId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Create new version for design or development contest.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectId the project to create new version
     * @param tcDirectProjectId TC direct project id
     * @param autoDevCreating if it is true and it is design contest, then will create development too
     * @param minorVersion true for minor, false for major
     * @return newly version contest id
     */
    public long createNewVersionForDesignDevContest(TCSubject tcSubject, long projectId, long tcDirectProjectId,
        boolean autoDevCreating, boolean minorVersion) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Creates a new <code>SoftwareCompetition</code> in the persistence.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param contest
     *            the <code>SoftwareCompetition</code> to create as a contest
     * @param tcDirectProjectId
     *            the TC direct project id.
     *
     * @return the created <code>SoftwareCompetition</code> as a contest
     */
    public SoftwareCompetition createSoftwareContest(TCSubject tcSubject, SoftwareCompetition contest,
        long tcDirectProjectId) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method creates a Specification Review project associated to a project determined by parameter.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId the project id to create a Specification Review for
     * @return the created project
     */
    public FullProjectData createSpecReview(TCSubject tcSubject, long projectId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Deletes the contest referenced by the specified ID.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to delete.
     */
    public void deleteContest(TCSubject tcSubject, long contestId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * This method finds all TC direct projects. Returns empty array if no projects found.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @return Project array with project info, or empty array if none found
     */
    public SoftwareCompetition[] findAllTcDirectProjects(TCSubject tcSubject) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * This method finds all given user TC direct projects . Returns empty array if no projects found.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param operator
     *            The user to search for projects
     *
     * @return Project array with project info, or empty array if none found
     *
     */
    public SoftwareCompetition[] findAllTcDirectProjectsForUser(TCSubject tcSubject, String operator) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Returns a list containing all active <code>Categories</code>.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @return a list containing all active <code>Categories</code>. It can be
     *         empty if no objects found.
     */
    public List<Category> getActiveCategories(TCSubject tcSubject) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Returns a list containing all active <code>Categories</code>.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @return a list containing all active <code>Categories</code>. It can be
     *         empty if no objects found.
     */
    public List<Technology> getActiveTechnologies(TCSubject tcSubject) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Returns a list containing all active <code>Technologies</code>.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @return a list containing all active <code>Categories</code>. It can be
     *         empty if no objects found.
     */
    public List<StudioCompetition> getAllContestHeaders(TCSubject tcSubject) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Gets the list of all existing contests.
     * </p>
     *
     *<p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return a <code>List</code> listing all existing contests. Empty list is
     *         returned if there are no contests found.
     */
    public List<ContestTypeData> getAllContestTypes(TCSubject tcSubject) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Gets the list of all existing contests.
     * </p>
     *
     * @param tcSubject the tcSubject
     * @return a <code>List</code> listing all existing contests. Empty list is
     *         returned if there are no contests found.
     */
    public List<StudioCompetition> getAllContests(TCSubject tcSubject) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Gets existing contest types.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @return a <code>List</code> listing available contest types. Empty list
     *         is returned if there are no types.
     */
    public List<DocumentType> getAllDocumentTypes(TCSubject tcSubject) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Gets existing medium types.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @return a <code>List</code> listing available medium types. Empty list is
     *         returned if there are no types.
     */
    public List<MediumData> getAllMediums(TCSubject tcSubject) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * This method retrieve all the permission types.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @return all the permission types.
     */
    public List<PermissionType> getAllPermissionType(TCSubject tcSubject) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Gets all studio file types to return. If no studio file type exists, return an empty list
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @return a list of studio file types
     */
    public List<StudioFileType> getAllStudioFileTypes(TCSubject tcSubject) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Gets the history data for the specified contest.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to get
     *            history data for.
     * @return a <code>List</code> of history data for specified contest.
     */
    public List<ChangeHistoryData> getChangeHistory(TCSubject tcSubject, long contestId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Gets the list of all existing contests for my project widget.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @return a <code>List</code> listing all existing contests. Empty list is
     *         returned if there are no contests found.
     */
    public List<CommonProjectContestData> getCommonProjectContestData(TCSubject tcSubject) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Gets the list of all existing contests related to given project for my project widget.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param pid
     *            given project id
     * @return a <code>List</code> listing all existing contests. Empty list is
     *         returned if there are no contests found.
     */
    public List<CommonProjectContestData> getCommonProjectContestDataByPID(TCSubject tcSubject, long pid) {
        List<CommonProjectContestData> list = new ArrayList<CommonProjectContestData>();

        // prepare the common project contest data
        CommonProjectContestData data = new CommonProjectContestData();
        data.setCname("contest1");
        list.add(data);
        data = new CommonProjectContestData();
        data.setCname("contest2");
        list.add(data);

        return list;
    }

    /**
     * <p>
     * Gets the list of project and their permissions (TCSubject tcSubject,including permissions for the
     * parent TC project)
     * </p>
     *
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param createdUser
     *            user for which to get the permissions
     * @return list of project and their permissions.
     */
    public List<CommonProjectPermissionData> getCommonProjectPermissionDataForUser(TCSubject tcSubject,
        long createdUser) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Gets the contest referenced by the specified ID.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to get
     *            details for.
     * @return a <code>StudioCompetition</code> providing the data for the
     *         requested contest.
     * @throws ContestNotFoundException
     *             if requested contest is not found.
     * @tested
     */
    public StudioCompetition getContest(TCSubject tcSubject, long contestId) throws ContestNotFoundException {
        if (studioCompetitions.containsKey(contestId)) {
            return studioCompetitions.get(contestId);
        }
        throw new ContestNotFoundException("test", contestId);
    }

    /**
     * <p>
     * This is going to fetch all the currently available contests for my project widget.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @return the list of all available contents (TCSubject tcSubject,or empty if none found)
     */
    public List<SimpleContestData> getContestDataOnly(TCSubject tcSubject) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * This is going to fetch all the currently available contests for my project widget related to given
     * project.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param pid
     *            given project id
     * @return the list of all available contents (TCSubject tcSubject,or empty if none found)
     */
    public List<SimpleContestData> getContestDataOnlyByPID(TCSubject tcSubject, long pid) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets all contest fees by billing project id.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId the billing project id
     * @return the list of project contest fees for the given project id
     */
    public List<ProjectContestFee> getContestFeesByProject(TCSubject tcSubject, long projectId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Gets the contests for the given project.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param tcDirectProjectId
     *            a <code>long</code> providing the ID of a project to get the
     *            list of associated contests for.
     * @return a <code>List</code> providing the contests for specified project.
     *         Empty list is returned if there are no such contests found.
     */
    public List<StudioCompetition> getContestsForProject(TCSubject tcSubject, long tcDirectProjectId) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get all design components.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return list of design components
     */
    public List<DesignComponents> getDesignComponents(TCSubject tcSubject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Find eligibility name for the client.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param billingProjectId
     *            The ID of the billingProject.
     * @return
     *         The name of the eligibility group.
     * @since 1.2.1
     */
    public String getEligibilityName(TCSubject tcSubject, long billingProjectId) {
        throw new UnsupportedOperationException();
    }

    /**
     * get final submissions for contest
     *<p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return empty list of none submission found for the given contest id.
     * @param contestId
     *            The contest id to get the final submissions
     */
    public List<SubmissionData> getFinalSubmissionsForContest(TCSubject tcSubject, long contestId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * This method retrieves the project along with all known associated information. Returns null if not
     * found.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId
     *            The ID of the project to retrieve
     *
     * @return the project along with all known associated information
     *
     */
    public SoftwareCompetition getFullProjectData(TCSubject tcSubject, long projectId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Gets the most history data for the most recent changes to specified contest.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to get
     *            history data for.
     * @return a <code>List</code> of history data for the most recent change
     *         for specified contest.
     */
    public List<ChangeHistoryData> getLatestChanges(TCSubject tcSubject, long contestId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * get milestone submissions for contest
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return empty list of none submission found for the given contest id.
     * @param contestId
     *            The contest id to get the milestone submissions.
     */
    public List<SubmissionData> getMilestoneSubmissionsForContest(TCSubject tcSubject, long contestId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Gets the MIME type matching the specified context type.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param contentType
     *            a <code>String</code> providing the content type to get the
     *            matching MIME type for.
     * @return a <code>long</code> providing the ID of MIME type matching the
     *         specified content type.
     */
    public long getMimeTypeId(TCSubject tcSubject, String contentType) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the notification information for the given user id. The notification information will be
     * returned as a list of ProjectNotification instance.
     *
     * @param subject the TCSubject instance.
     * @param userId the id of the user.
     * @return a list of ProjectNotification instances.
     */
    public List<ProjectNotification> getNotificationsForUser(TCSubject subject, long userId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * This method retrieve all the permissions that the user own for a given project. Returns empty list if
     * no permission found.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param userid
     *            user id to look for
     * @param projectid
     *            project id to look for
     *
     * @return all the permissions that the user own for a given project.
     */
    public List<Permission> getPermissions(TCSubject tcSubject, long userid, long projectid) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * This method retrieve all the permissions that various users own for a given project. Returns empty list
     * if no permission found.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectid
     *            project id to look for
     *
     * @return all the permissions that various users own for a given project.
     */
    public List<Permission> getPermissionsByProject(TCSubject tcSubject, long projectid) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * This method retrieve all the permissions that the user owned for any projects. Returns empty list if no
     * permission found.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param userid
     *            user id to look for
     *
     * @return all the permissions that the user owned for any projects.
     */
    public List<Permission> getPermissionsByUser(TCSubject tcSubject, long userid) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Returns a list containing all <code>Phases</code>.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @return a list containing all active <code>Categories</code>. It can be
     *         empty if no objects found.
     */
    public List<Phase> getPhases(TCSubject tcSubject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets all project data with aggregated statistics data for each type of contest status.
     *
     * @param tcSubject <code>TCSubject</code> object
     * @return a list of <code>ProjectSummaryData</code> objects
     * @throws ContestServiceException if some error occurs getting project data
     */
    public List<ProjectSummaryData> getProjectData(TCSubject tcSubject) throws ContestServiceException {
        if (failWhenGettingProjectData) {
            throw new ContestServiceException("test");
        }

        // prepare the project summary data to return
        List<ProjectSummaryData> list = new ArrayList<ProjectSummaryData>();

        ProjectSummaryData data = new ProjectSummaryData();
        data.setProjectName("project1");
        list.add(data);

        data = new ProjectSummaryData();
        data.setProjectName("project2");
        list.add(data);

        data = new ProjectSummaryData();
        data.setProjectName("project3");
        list.add(data);

        return list;
    }

    /**
     * This method retrieves score card and review information associated to a project determined by
     * parameter.
     * Note: a single reviewer / review is assumed.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId the project id to search for
     * @return the aggregated score card and review data
     */
    public ScorecardReviewData getScorecardAndReview(TCSubject tcSubject, long projectId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Gets the list of all existing contests for contest monitor widget.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @return a <code>List</code> listing all existing contests. Empty list is
     *         returned if there are no contests found.
     */
    public List<SimpleContestData> getSimpleContestData(TCSubject tcSubject) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Gets the list of all existing contests related to given project for contest monitor widget .
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param pid
     *            the given project id
     * @return a <code>List</code> listing all existing contests. Empty list is
     *         returned if there are no contests found.
     */
    public List<SimpleContestData> getSimpleContestDataByPID(TCSubject tcSubject, long pid) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Gets the list of all existing contests for my project widget.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @return a <code>List</code> listing all existing contests. Empty list is
     *         returned if there are no contests found.
     */
    public List<SimpleProjectContestData> getSimpleProjectContestData(TCSubject tcSubject) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Gets the list of all existing contests related to given project for my project widget.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param pid
     *            given project id
     * @return a <code>List</code> listing all existing contests. Empty list is
     *         returned if there are no contests found.
     */
    public List<SimpleProjectContestData> getSimpleProjectContestDataByPID(TCSubject tcSubject, long pid) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * BURG-1716: We need to add a method to get software contest by project id, the method will get all OR
     * project related data, then from project property to get competition version id then to call
     * getAssetByVersionId to get assetDTO, please check create software contest to see what data need to be
     * returned.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId
     *            the OR Project Id
     * @return SoftwareCompetition
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     */
    public SoftwareCompetition getSoftwareContestByProjectId(TCSubject tcSubject, long projectId)
        throws ContestServiceException {

        if (softwareCompetitions.containsKey(projectId)) {
            return softwareCompetitions.get(projectId);
        }
        throw new ContestServiceException("test");
    }

    /**
     * Gets the specification review for specified contest id.
     *
     * Updated for specification Reviews Finishing Touches v1.0
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param contestId
     *            the contest id
     * @param studio
     *            indicates whether the specified contest id is for studio contests.
     *
     * @return the specification review that matches the specified contest id.
     */
    public SpecReview getSpecReviews(TCSubject tcSubject, long contestId, boolean studio) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Gets existing contest statuses.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @return a <code>List</code> listing available contest statuses. Empty
     *         list is returned if there are no statuses.
     */
    public List<ContestStatusData> getStatusList(TCSubject tcSubject) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Gets the list of existing submission types.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @return a <code>String</code> providing the comma-separated list of
     *         submission types.
     */
    public String getSubmissionFileTypes(TCSubject tcSubject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get the user contest by user name Return empty list if none found
     *<p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param userName
     *            the user name to get the user contest
     * @return a list of matching studio competitions
     * @throws IllegalArgumentException
     *             if userName is null or empty
     */
    public List<StudioCompetition> getUserContests(TCSubject tcSubject, String userName) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns whether a user is eligible for a particular contest.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param userId
     *            The user id
     * @param contestId
     *            The contest id
     * @param isStudio
     *            true if the contest is a studio contest, false otherwise.
     * @return true if the user is eligible for the specified contest, false otherwise.
     *
     */
    public boolean isEligible(TCSubject tcSubject, long userId, long contestId, boolean isStudio) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns whether the contest is private.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId
     *            The contest id
     * @param isStudio
     *            true if the contest is a studio contest, false otherwise.
     * @return true if the contest is a private one, false otherwise.
     */
    public boolean isPrivate(TCSubject tcSubject, long contestId, boolean isStudio) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Marks the specified submission for purchase.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param submissionId
     *            a <code>long</code> providing the ID of a submission to be
     *            marked for purchase.
     */
    public void markForPurchase(TCSubject tcSubject, long submissionId) {
        throw new UnsupportedOperationException();
    }

    /**
     * Marks 'ready for review' by the writer of the specifications for specified contest.
     * Persistence is updated, on update the specifications would appear as review opportunity on TC site.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId
     *            the specified contest id.
     * @param studio
     *            whether contest is studio or not.
     */
    public void markReadyForReview(TCSubject tcSubject, long contestId, boolean studio) {
        throw new UnsupportedOperationException();
    }

    /**
     * Mark review comment with specified comment id as seen.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param commentId
     *            the comment id
     */
    public void markReviewCommentSeen(TCSubject tcSubject, long commentId) {
        throw new UnsupportedOperationException();
    }

    /**
     * Marks 'review done' by reviewer of the specifications for specified contest.
     * Persistence is updated and all end users having write/full permission on the contest are notified by
     * email.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId
     *            the specified contest id.
     * @param contestName
     *            the contest name.
     * @param studio
     *            whether contest is studio or not.
     * @param tcDirectProjectId
     *            the TC direct project id.
     */
    public void markReviewDone(TCSubject tcSubject, long contestId, String contestName, boolean studio,
        long tcDirectProjectId) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method uploads a mock file to the corresponding specification review project of the specified
     * project
     * id, so that it can continue with review. Regular submission or final fix will be uploaded according to
     * the
     * open phase.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId the project id of the original project
     */
    public void markSoftwareContestReadyForReview(TCSubject tcSubject, long projectId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Processes the contest payment. It does following steps:
     * <ul>
     * <li>Checks contest id to decide whether to create new contest or update existing contest</li>
     * <li>If payment type is credit card then it processes the payment through <code>PaymentProcessor</code></li>
     * <li>Right-now this method doesn't process PO payments.</li>
     * <li>
     * On successful processing -
     * <ul>
     * <li>set contests to CONTEST_STATUS_ACTIVE_PUBLIC = 2</li>
     * <li>set detailed contests to CONTEST_DETAILED_STATUS_ACTIVE_PUBLIC = 2</li>
     * <li>set payment reference number and type</li>
     * <li>Creates new forum for the contest, forum name being contest name. It uses studio service for doing
     * the same.</li>
     * </ul>
     * </li>
     * </ul>
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param competition
     *            <code>ContestData</code> data that recognizes a contest.
     * @param paymentData
     *            <code>PaymentData</code> payment information (TCSubject tcSubject,credit card/purchase order
     *            details) that need to be processed.
     * @return a <code>PaymentResult</code> result of the payment processing.
     */
    public ContestPaymentResult processContestCreditCardPayment(TCSubject tcSubject, StudioCompetition competition,
        CreditCardPaymentData paymentData) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Processes the contest sale.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param competition
     *            data that recognizes a contest.
     * @param paymentData
     *            payment information (TCSubject tcSubject,credit card/PO details) that need to be
     *            processed.
     *
     * @return a <code>PaymentResult</code> result of the payment processing.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     * @since BUGR-1682 changed return value
     */
    public SoftwareContestPaymentResult processContestCreditCardSale(TCSubject tcSubject,
        SoftwareCompetition competition, CreditCardPaymentData paymentData) {
        throw new UnsupportedOperationException();
    }

    /**
     * @since BUGR-1494 returns ContestPaymentResult instead of PaymentResult
     *        <p>
     *        Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     *        </p>
     * @param tcSubject the tcSubject
     * @param competition the competition
     * @param paymentData the payment data
     * @return paymentData the payment data
     */
    public ContestPaymentResult processContestPurchaseOrderPayment(TCSubject tcSubject,
        StudioCompetition competition, TCPurhcaseOrderPaymentData paymentData) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Processes the contest sale.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param competition
     *            data that recognizes a contest.
     * @param paymentData
     *            payment information (TCSubject tcSubject,credit card/PO details) that need to be
     *            processed.
     *
     * @return a <code>PaymentResult</code> result of the payment processing.
     */
    public SoftwareContestPaymentResult processContestPurchaseOrderSale(TCSubject tcSubject,
        SoftwareCompetition competition, TCPurhcaseOrderPaymentData paymentData) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Sends payments to <code>PACTS</code> application for all unpaid submissions with a prize already
     * assigned. This service is not atomic. If it fails, you'll have to check what payments where actually
     * done trough the <code>submussion.paid</code> flag.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to process
     *            missing payments for.
     */
    public void processMissingPayments(TCSubject tcSubject, long contestId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Processes the submissions payment. It does following steps:
     * <ul>
     * <li>Checks submissionIds to see if is available, if not then it throws PaymentException.</li>
     * <li>It processes the payment through <code>PaymentProcessor</code></li>
     * <li>Right-now this method doesn't process PO payments.</li>
     * <li>On successful processing -
     * <ul>
     * <li>it calls <code>this.purchaseSubmission(TCSubject tcSubject,...)</code></li>
     * </ul>
     * </li>
     * </ul>
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param completedContestData
     *            data of completed contest.
     * @param paymentData
     *            a <code>PaymentData</code> payment information (TCSubject tcSubject,credit card/PO
     *            details) that need to be processed.
     * @return a <code>PaymentResult</code> result of the payment processing.
     */
    public PaymentResult processSubmissionCreditCardPayment(TCSubject tcSubject,
        CompletedContestData completedContestData, CreditCardPaymentData paymentData) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Processes the submission payment. It does following steps:
     * <ul>
     * <li>Checks submissionIds to see if is available, if not then it throws PaymentException.</li>
     * <li>Right-now this method doesn't process PO payments.</li>
     * <li>On successful processing -
     * <ul>
     * <li>it calls <code>this.purchaseSubmission(TCSubject tcSubject,...)</code></li>
     * </ul>
     * </li>
     * </ul>
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param completedContestData
     *            data of completed contest.
     * @param paymentData
     *            a <code>PaymentData</code> payment information (TCSubject tcSubject,credit card/PO
     *            details) that need to be processed.
     * @return a <code>PaymentResult</code> result of the payment processing.
     */
    public PaymentResult processSubmissionPurchaseOrderPayment(TCSubject tcSubject,
        CompletedContestData completedContestData, TCPurhcaseOrderPaymentData paymentData) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Purchases the specified submission. E.g. records a fact that submission referenced by specified ID has
     * been paid.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
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
     */
    public void purchaseSubmission(TCSubject tcSubject, long submissionId,
        SubmissionPaymentData submissionPaymentData, String securityToken) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Ranks the submissions, given submission identifiers in the rank order.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param submissionIdsInRankOrder
     *            an array of long submission identifier in the rank order.
     * @return a <code>boolean</code> true if successful, else false.
     */
    public boolean rankSubmissions(TCSubject tcSubject, long[] submissionIdsInRankOrder) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Re-Open the software project in status of (TCSubject tcSubject,project_status_id = 4-6, 8-10 in
     * tcs_catalog:project_status_lu).
     * </p>
     *<p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectId the project id to open
     * @param tcDirectProjectId the TC-direct-project-id
     * @return returns the newly created contest id
     */
    public long reOpenSoftwareContest(TCSubject tcSubject, long projectId, long tcDirectProjectId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Removes the document referenced by the specified ID.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param documentId
     *            a <code>long</code> providing the ID of a document to remove.
     * @return <code>true</code> if requested document was removed successfully; <code>false</code> otherwise.
     */
    public boolean removeDocument(TCSubject tcSubject, long documentId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Removes the specified document from specified contest.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param document
     *            an <code>UploadedDocument</code> representing the document to
     *            be removed.
     */
    public void removeDocumentFromContest(TCSubject tcSubject, UploadedDocument document) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Removes specified submission.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param submissionId
     *            a <code>long</code> providing the ID of a submission to
     *            remove.
     */
    public void removeSubmission(TCSubject tcSubject, long submissionId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Removes a specified user from a specified <code>assetDTO</code>.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param userId
     *            the id of the user
     * @param assetId
     *            the id of the asset
     */
    public void removeUserFromAsset(TCSubject tcSubject, long userId, long assetId) {
        throw new UnsupportedOperationException();
    }

    /**
     * Marks 'resubmit for review' by the writer of the specifications for specified contest.
     * Persistence is updated. Reviewer (TCSubject tcSubject,if any) is notified about the updates.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId
     *            the specified contest id.
     * @param contestName
     *            the contest name.
     * @param studio
     *            whether contest is studio or not.
     * @param reviewerUserId
     *            reviewer user id.
     */
    public void resubmitForReview(TCSubject tcSubject, long contestId, String contestName, boolean studio,
        long reviewerUserId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Gets the submission referenced by the specified ID.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param submissionId
     *            a <code>long</code> providing the ID of a submission to get
     *            details for.
     * @return a <code>SubmissionData</code> providing details for the
     *         submission referenced by the specified ID or <code>null</code> if
     *         such a submission is not found.
     */
    public SubmissionData retrieveSubmission(TCSubject tcSubject, long submissionId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Retrieves the list of submissions for the specified contest.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to get the
     *            list of submissions for.
     * @return a <code>List</code> providing the details for the submissions
     *         associated with the specified contest. Empty list is returned if
     *         there are no submissions found.
     */
    public List<SubmissionData> retrieveSubmissionsForContest(TCSubject tcSubject, long contestId) {
        throw new UnsupportedOperationException();
    }

    /**
     * Save specified review comment for specified section and specified contest id to persistence.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
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
     */
    public void saveReviewComment(TCSubject tcSubject, long contestId, boolean studio, String sectionName,
        String comment, String role) {
        throw new UnsupportedOperationException();
    }

    /**
     * Save specified review comment and review status for specified section and specified contest id to
     * persistence.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
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
     */
    public void saveReviewStatus(TCSubject tcSubject, long contestId, boolean studio, String sectionName,
        String comment, boolean isPass, String role) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Gets the list of existing contests matching the specified criteria.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param filter
     *            a <code>Filter</code> providing the criteria for searching for
     *            contests.
     * @return a <code>List</code> listing all existing contests matching the
     *         specified criteria. Empty list is returned if there are no
     *         matching contests found.
     */
    public List<StudioCompetition> searchContests(TCSubject tcSubject, ContestServiceFilter filter) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Searches the user with the given key.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param key the key
     *
     * @return list of matching users, empty list if none matches.
     * @since TCCC-1329
     */
    public List<User> searchUser(TCSubject tcSubject, String key) {
        throw new UnsupportedOperationException();
    }

    /**
     * set submission milestone prize If given submission has already been
     * associated with the given milestone prize before, ContestServiceException
     * will be thrown. It's required that the contest field of the submission of
     * given id is one of the contests set of the milestone prize, otherwise,
     * ContestServiceException will be thrown. If the MilestonePrize with given
     * id has reached the max number of submissions, ContestServiceException
     * will be thrown.
     *<p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param submissionId
     *            The submission id
     * @param milestonePrizeId
     *            The milestone prize id
     */
    public void setSubmissionMilestonePrize(TCSubject tcSubject, long submissionId, long milestonePrizeId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Sets the placement for the specified submission.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param submissionId
     *            a <code>long</code> providing the ID of a submission to set
     *            the placement for.
     * @param placement
     *            an integer providing the submission placement.
     */
    public void setSubmissionPlacement(TCSubject tcSubject, long submissionId, int placement) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Associates the specified submission with the specified prize.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param submissionId
     *            a <code>long</code> providing the ID of a submission.
     * @param prizeId
     *            a <code>long</code> providing the ID of a prize.
     */
    public void setSubmissionPrize(TCSubject tcSubject, long submissionId, long prizeId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Sets the status of a existing submission.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param submissionId
     *            the submission's id
     * @param submissionStatusId
     *            the submission status id
     * @param operator
     *            the operator which execute the operation
     *
     */
    public void setSubmissionStatus(TCSubject tcSubject, long submissionId, long submissionStatusId, String operator) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Updates the specified contest.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param contest
     *            a <code>StudioCompetition</code> providing the contest data to
     *            update.
     */
    public void updateContest(TCSubject tcSubject, StudioCompetition contest) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Sets the status of contest referenced by the specified ID to specified
     * value.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to update
     *            status for.
     * @param newStatusId
     *            a <code>long</code> providing the ID of a new contest status
     *            to set.
     */
    public void updateContestStatus(TCSubject tcSubject, long contestId, long newStatusId) {
        throw new UnsupportedOperationException();
    }

    /**
     * Updates the notifications for the given user, the notifications which need to update are
     * passed in as a list of ProjectNotification instances.
     *
     * @param subject the TCSubject instance.
     * @param userId the id of the user.
     * @param notifications a list of ProjectNotification instances to update.
     */
    public void updateNotificationsForUser(TCSubject subject, long userId, List<ProjectNotification> notifications) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * This method will update permission type data.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param type
     *            the permission type to update.
     *
     */
    public void updatePermissionType(TCSubject tcSubject, PermissionType type) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * This method updates array of permissions to the persistence.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param permissions
     *            the permissions to update.
     */
    public void updatePermissions(TCSubject tcSubject, Permission[] permissions) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Updates a <code>SoftwareCompetition</code> in the persistence.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param contest
     *            the <code>SoftwareCompetition</code> to update as a contest
     * @param tcDirectProjectId
     *            the TC direct project id.
     * @return software competition
     *
     */
    public SoftwareCompetition updateSoftwareContest(TCSubject tcSubject, SoftwareCompetition contest,
        long tcDirectProjectId) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Updates specified submission.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param submission
     *            a <code>SubmissionData</code> providing the data for
     *            submission to be updated.
     */
    public void updateSubmission(TCSubject tcSubject, SubmissionData submission) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Ranks the submissions, given submission identifiers and the rank. If the isRankingMilestone flag is true,
     * the rank will target milestone submissions.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param submissionId
     *            identifier of the submission.
     * @param rank
     *            rank of the submission.
     * @param isRankingMilestone
     *            true if the user is ranking milestone submissions.
     *
     * @return a <code>boolean</code> true if successful, else false.
     */
    public boolean updateSubmissionUserRank(TCSubject tcSubject, long submissionId, int rank,
        Boolean isRankingMilestone) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Updates the submission feedback.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param feedbacks
     *            an array of <code>SubmissionFeedback</code>
     * @return a <code>boolean</code> true if successful, else false.
     */
    public boolean updateSubmissionsFeedback(TCSubject tcSubject, SubmissionFeedback[] feedbacks) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Uploads the specified document without associating it with any contest.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param uploadedDocument
     *            an <code>UploadedDocument</code> providing the data for the
     *            uploaded document.
     * @return an <code>UploadedDocument</code> passed as argument and having
     *         the document ID auto-generated and set.
     */
    public UploadedDocument uploadDocument(TCSubject tcSubject, UploadedDocument uploadedDocument) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Uploads the specified document and associates it with assigned contest.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param uploadedDocument
     *            an <code>UploadedDocument</code> providing the data for the
     *            uploaded document.
     * @return an <code>UploadedDocument</code> passed as argument and having
     *         the document ID auto-generated and set.
     */
    public UploadedDocument uploadDocumentForContest(TCSubject tcSubject, UploadedDocument uploadedDocument) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Adds a new final fix upload for an user in a particular project. This
     * submission always overwrite the previous ones.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId
     *            the project's id
     * @param filename
     *            the file name to use
     * @param finalFix
     *            the final fix file data
     *
     * @return the id of the created final fix submission
     */
    public long uploadFinalFix(TCSubject tcSubject, long projectId, String filename, DataHandler finalFix) {
        throw new UnsupportedOperationException();
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
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId
     *            the project's id
     * @param filename
     *            the file name to use
     * @param submission
     *            the submission file data
     *
     * @return the id of the new submission
     */
    public long uploadSubmission(TCSubject tcSubject, long projectId, String filename, DataHandler submission) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Adds a new test case upload for an user in a particular project. This
     * submission always overwrite the previous ones.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId
     *            the project's id
     * @param filename
     *            the file name to use
     * @param testCases
     *            the test cases data
     *
     * @return the id of the created test cases submission
     */
    public long uploadTestCases(TCSubject tcSubject, long projectId, String filename, DataHandler testCases) {
        throw new UnsupportedOperationException();
    }

}
