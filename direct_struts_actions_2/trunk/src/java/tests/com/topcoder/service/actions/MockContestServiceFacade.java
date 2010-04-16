/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.CompDocumentation;
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

/**
 * <p>
 * The mock ContestServiceFacade used in the unit tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockContestServiceFacade implements ContestServiceFacade {

    /**
     * Represents whether to cause failure for unit testing when retrieving categories.
     */
    private static boolean failGetCategories;

    /**
     * Represents whether to cause failure for unit testing when retrieving technologies.
     */
    private static boolean failGetTechnologies;

    /**
     * Represents documents for studio competitions used for unit testing.
     */
    private static Map<Long, UploadedDocument> studioCompetitionDocuments = new HashMap<Long, UploadedDocument>();

    /**
     * Represents documents for software competitions used for unit testing.
     */
    private static Map<Long, CompDocumentation> softwareCompetitionDocuments = new HashMap<Long, CompDocumentation>();

    /**
     * Represents studio competitions used for unit testing.
     */
    private static Map<Long, StudioCompetition> studioCompetitions = new HashMap<Long, StudioCompetition>();

    /**
     * Represents software competitions used for unit testing.
     */
    private static Map<Long, SoftwareCompetition> softwareCompetitions = new HashMap<Long, SoftwareCompetition>();

    /**
     * Clears documents used for unit testing.
     */
    public static void clearDocuments() {
        studioCompetitionDocuments.clear();
        softwareCompetitionDocuments.clear();
    }

    /**
     * Clears studio competitions used for unit testing.
     */
    public static void clearStudioCompetitions() {
        studioCompetitions.clear();
    }

    /**
     * Adds the studio competition to the map for unit testing.
     *
     * @param competition the competition
     */
    public static void addStudioCompetition(StudioCompetition competition) {
        studioCompetitions.put(competition.getId(), competition);
    }

    /**
     * Gets the studio competition from the map for unit testing.
     *
     * @param key the key
     *
     * @return the studio competition from the map for unit testing
     */
    public static StudioCompetition getStudioCompetition(long key) {
        return studioCompetitions.get(key);
    }

    /**
     * Clears software competitions used for unit testing.
     */
    public static void clearSoftwareCompetitions() {
        softwareCompetitions.clear();
    }

    /**
     * Adds the software competition to the map for unit testing.
     *
     * @param competition the competition
     */
    public static void addSoftwareCompetition(SoftwareCompetition competition) {
        softwareCompetitions.put(competition.getId(), competition);
    }

    /**
     * Gets the software competition from the map for unit testing.
     *
     * @param key the key
     *
     * @return the software competition from the map for unit testing
     */
    public static SoftwareCompetition getSoftwareCompetition(long key) {
        return softwareCompetitions.get(key);
    }

    /**
     * Adds the studio competition document to the map for unit testing.
     *
     * @param doc the document
     */
    public static void addDocument(UploadedDocument doc) {
        if (doc.getDocumentId() == -1) {
            doc.setDocumentId(studioCompetitionDocuments.size() + 1);
        }
        studioCompetitionDocuments.put(doc.getDocumentId(), doc);
    }

    /**
     * Adds the software competition document to the map for unit testing.
     *
     * @param doc the document
     */
    public static void addDocument(CompDocumentation doc) {
        if (doc.getId() == -1) {
            doc.setId((long) softwareCompetitionDocuments.size() + 1);
        }
        softwareCompetitionDocuments.put(doc.getId(), doc);
    }

    /**
     * Gets the studio competition document from the map for unit testing.
     *
     * @param key the key
     *
     * @return the document from the map for unit testing
     */
    public static UploadedDocument getStudioCompetitionDocument(long key) {
        return studioCompetitionDocuments.get(key);
    }

    /**
     * Gets the software competition document from the map for unit testing.
     *
     * @param key the key
     *
     * @return the document from the map for unit testing
     */
    public static CompDocumentation getSoftwareCompetitionDocument(long key) {
        return softwareCompetitionDocuments.get(key);
    }

    /**
     * Returns whether to cause a failure for unit testing when retrieving technologies.
     *
     * @param fail whether to cause a failure for unit testing when retrieving technologies
     */
    public static void setFailGetTechnologies(boolean fail) {
        failGetTechnologies = fail;
    }

    /**
     * Returns whether to cause a failure for unit testing when retrieving categories.
     *
     * @param fail whether to cause a failure for unit testing when retrieving categories
     */
    public static void setFailGetCategories(boolean fail) {
        failGetCategories = fail;
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
     * @throws PersistenceException if any other error occurs.
     * @tested
     */
    public void addChangeHistory(TCSubject tcSubject, List<ChangeHistoryData> history) throws PersistenceException {

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
     * @param documentId a <code>long</code> providing the ID of a document to be associated with specified
     *            contest.
     * @param contestId a <code>long</code> providing the ID of a contest to associate the specified document
     *            with.
     * @throws PersistenceException if some persistence errors occur.
     * @throws ContestNotFoundException if any of requested contest or document is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if any of specified IDs is negative.
     * @tested
     */
    public void addDocumentToContest(TCSubject tcSubject, long documentId, long contestId)
        throws PersistenceException, ContestNotFoundException {

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
     * @param type the permission type to add.
     *
     * @return the added permission type entity
     *
     * @throws IllegalArgumentWSException if the argument is invalid
     * @throws PermissionServiceException if any error occurs when adding the permission type.
     *
     * @since Module Cockpit Contest Service Enhancement Assembly
     */
    public PermissionType addPermissionType(TCSubject tcSubject, PermissionType type)
        throws PermissionServiceException {

        return null;
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
     *
     * @throws ContestServiceException if any unexpected error occurs in the underlying services.
     * @throws IllegalArgumentException if comment is null
     *
     * @since 1.4
     */
    public void addReviewComment(TCSubject tcSubject, long reviewId, Comment comment) throws ContestServiceException {

    }

    /**
     * Adds the given user as a new submitter to the given project id.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId the project to which the user needs to be added
     * @param userId the user to be added
     *
     * @return the added resource id
     *
     * @throws IllegalArgumentException if any id is &lt; 0
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public long addSubmitter(TCSubject tcSubject, long projectId, long userId) throws ContestServiceException {

        return 0;
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
     * @param userId the id of the user
     * @param assetId the id of the assetDTO
     *
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public void assignUserToAsset(TCSubject tcSubject, long userId, long assetId) throws ContestServiceException {
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
     * @param contest a <code>StudioCompetition</code> providing the data for new contest to be created.
     * @param tcDirectProjectId a <code>long</code> providing the ID of a project the new competition belongs
     *            to.
     * @return a <code>StudioCompetition</code> providing the data for created contest and having the ID
     *         auto-generated.
     * @throws PersistenceException if some persistence errors occur.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if specified <code>competition</code> is <code>null</code> or if the
     *             specified <code>tcDirectProjectId</code> is negative.
     * @tested
     */
    public StudioCompetition createContest(TCSubject tcSubject, StudioCompetition contest, long tcDirectProjectId)
        throws PersistenceException {

        return null;
    }

    /**
     * <p>
     * Create new version for design or development contest. (TCSubject tcSubject,project_status_id = 4-10 in
     * tcs_catalog:project_status_lu).
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectId the project to create new version
     * @param tcDirectProjectId tc direct project id
     * @param autoDevCreating if it is true and it is design contest, then will create development too
     * @param minorVersion true for minor, false for major
     * @return newly version contest id
     * @throws ContestServiceException if any error occurs
     */
    public long createNewVersionForDesignDevContest(TCSubject tcSubject, long projectId, long tcDirectProjectId,
        boolean autoDevCreating, boolean minorVersion) throws ContestServiceException {

        return 0;
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
     * @param contest the <code>SoftwareCompetition</code> to create as a contest
     * @param tcDirectProjectId the TC direct project id.
     *
     * @return the created <code>SoftwareCompetition</code> as a contest
     *
     * @throws IllegalArgumentException if the input argument is invalid.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition createSoftwareContest(TCSubject tcSubject, SoftwareCompetition contest,
        long tcDirectProjectId) throws ContestServiceException {

        return null;
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
     *
     * @throws ContestServiceException if any unexpected error occurs in the underlying services
     *
     * @since 1.4
     */
    public FullProjectData createSpecReview(TCSubject tcSubject, long projectId) throws ContestServiceException {

        return null;
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
     * @param contestId a <code>long</code> providing the ID of a contest to delete.
     * @throws PersistenceException if any other error occurs.
     * @tested
     */
    public void deleteContest(TCSubject tcSubject, long contestId) throws PersistenceException {

    }

    /**
     * <p>
     * This method finds all tc direct projects. Returns empty array if no projects found.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @return Project array with project info, or empty array if none found
     *
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition[] findAllTcDirectProjects(TCSubject tcSubject) throws ContestServiceException {

        return null;
    }

    /**
     * <p>
     * This method finds all given user tc direct projects . Returns empty array if no projects found.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param operator The user to search for projects
     *
     * @return Project array with project info, or empty array if none found
     *
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition[] findAllTcDirectProjectsForUser(TCSubject tcSubject, String operator)
        throws ContestServiceException {

        return null;
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
     * @return a list containing all active <code>Categories</code>. It can be empty if no objects found.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public List<Category> getActiveCategories(TCSubject tcSubject) throws ContestServiceException {
        if (failGetCategories) {
            throw new ContestServiceException("mock failure");
        }
        List<Category> list = new ArrayList<Category>();
        Category cat = new Category();
        cat.setId(1L);
        cat.setDescription("category1");
        list.add(cat);

        cat = new Category();
        cat.setId(2L);
        cat.setDescription("category2");
        list.add(cat);

        return list;
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
     * @return a list containing all active <code>Categories</code>. It can be empty if no objects found.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public List<Technology> getActiveTechnologies(TCSubject tcSubject) throws ContestServiceException {
        if (failGetTechnologies) {
            throw new ContestServiceException("mock failure");
        }
        List<Technology> list = new ArrayList<Technology>();
        Technology tech = new Technology();
        tech.setId(1L);
        tech.setDescription("technology1");
        list.add(tech);

        tech = new Technology();
        tech.setId(2L);
        tech.setDescription("technology2");
        list.add(tech);

        return list;
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
     * @return a <code>List</code> listing all existing contests. Empty list is returned if there are no
     *         contests found.
     * @throws PersistenceException if any error occurs when getting contest.
     * @tested
     */
    public List<StudioCompetition> getAllContestHeaders(TCSubject tcSubject) throws PersistenceException {

        return null;
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
     * @return a <code>List</code> listing available contest types. Empty list is returned if there are no
     *         types.
     * @throws PersistenceException if some persistence errors occur.
     * @tested
     */
    public List<ContestTypeData> getAllContestTypes(TCSubject tcSubject) throws PersistenceException {

        return null;
    }

    /**
     * <p>
     * Gets the list of all existing contests.
     * </p>
     *
     * @return a <code>List</code> listing all existing contests. Empty list is returned if there are no
     *         contests found.
     * @throws PersistenceException if any error occurs when getting contest.
     * @tested
     */
    public List<StudioCompetition> getAllContests(TCSubject tcSubject) throws PersistenceException {

        return null;
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
     * @return a <code>List</code> listing available medium types. Empty list is returned if there are no
     *         types.
     * @throws PersistenceException if some persistence errors occur.
     * @tested
     */
    public List<MediumData> getAllMediums(TCSubject tcSubject) throws PersistenceException {

        return null;
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
     * @return a <code>List</code> listing available medium types. Empty list is returned if there are no
     *         types.
     * @throws PersistenceException if some persistence errors occur.
     * @tested
     */
    public List<PermissionType> getAllPermissionType(TCSubject tcSubject) throws PermissionServiceException {

        return null;
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
     * @param contestId a <code>long</code> providing the ID of a contest to get history data for.
     * @return a <code>List</code> of history data for specified contest.
     * @throws PersistenceException if any other error occurs.
     * @tested
     */
    public List<ChangeHistoryData> getChangeHistory(TCSubject tcSubject, long contestId) throws PersistenceException {

        return null;
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
     * @return a <code>List</code> listing all existing contests. Empty list is returned if there are no
     *         contests found.
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<CommonProjectContestData> getCommonProjectContestData(TCSubject tcSubject)
        throws PersistenceException {

        return null;
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
     * @param pid given project id
     * @return a <code>List</code> listing all existing contests. Empty list is returned if there are no
     *         contests found.
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<CommonProjectContestData> getCommonProjectContestDataByPID(TCSubject tcSubject, long pid)
        throws PersistenceException {

        return null;
    }

    /**
     * <p>
     * Gets the list of project and their permissions (TCSubject tcSubject,including permissions for the
     * parent tc project)
     * </p>
     *
     * <p>
     * Updated for Cockpit Project Admin Release Assembly v1.0 - software projects also included.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param createdUser user for which to get the permissions
     * @return list of project and their permissions.
     *
     * @since TCCC-1329
     */
    public List<CommonProjectPermissionData> getCommonProjectPermissionDataForUser(TCSubject tcSubject,
        long createdUser) throws PersistenceException {

        return null;
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
     * @param contestId a <code>long</code> providing the ID of a contest to get details for.
     * @return a <code>StudioCompetition</code> providing the data for the requested contest.
     * @throws PersistenceException if some persistence errors occur.
     * @throws ContestNotFoundException if requested contest is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if specified <code>contestId</code> is negative.
     * @tested
     */
    public StudioCompetition getContest(TCSubject tcSubject, long contestId) throws PersistenceException,
        ContestNotFoundException {
        StudioCompetition contest = studioCompetitions.get(contestId);
        if (contest == null) {
            throw new ContestNotFoundException("The contest could not be found.", contestId);
        }
        return contest;

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
     *
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<SimpleContestData> getContestDataOnly(TCSubject tcSubject) throws PersistenceException {

        return null;
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
     * @param pid given project id
     * @return the list of all available contents (TCSubject tcSubject,or empty if none found)
     *
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<SimpleContestData> getContestDataOnlyByPID(TCSubject tcSubject, long pid) throws PersistenceException {

        return null;
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
     * @throws ContestServiceException if any persistence or other error occurs
     * @since 1.0.1
     */
    public List<ProjectContestFee> getContestFeesByProject(TCSubject tcSubject, long projectId)
        throws ContestServiceException {

        return null;
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
     * @param tcDirectProjectId a <code>long</code> providing the ID of a project to get the list of
     *            associated contests for.
     * @return a <code>List</code> providing the contests for specified project. Empty list is returned if
     *         there are no such contests found.
     * @throws PersistenceException if some persistence errors occur.
     * @throws ProjectNotFoundException if requested project is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if specified <code>tcDirectProjectId</code> is negative.
     * @tested
     */
    public List<StudioCompetition> getContestsForProject(TCSubject tcSubject, long tcDirectProjectId)
        throws PersistenceException, ProjectNotFoundException {

        return null;
    }

    /**
     * Get all design components.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @throws ContestServiceException if any other error occurs
     * @since 1.1.1
     */
    public List<DesignComponents> getDesignComponents(TCSubject tcSubject) throws ContestServiceException {

        return null;
    }

    /**
     * Find eligibility name for the client.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param billingProjectId; The ID of the billingProject.
     * @return The name of the eligibility group.
     * @since 1.2.1
     */
    public String getEligibilityName(TCSubject tcSubject, long billingProjectId) {

        return null;
    }

    /**
     * get final submissions for contest
     *<p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return empty list of none submission found for the given contest id.
     * @param contestId The contest id to get the final submissions
     * @throws IllegalArgumentException if long argument is negative
     * @throws ContestServiceException if any other error occurs
     * @since 1.1
     */
    public List<SubmissionData> getFinalSubmissionsForContest(TCSubject tcSubject, long contestId)
        throws ContestServiceException {

        return null;
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
     * @param projectId The ID of the project to retrieve
     *
     * @return the project along with all known associated information
     *
     * @throws IllegalArgumentException If projectId is negative
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition getFullProjectData(TCSubject tcSubject, long projectId) throws ContestServiceException {

        return null;
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
     * @param contestId a <code>long</code> providing the ID of a contest to get history data for.
     * @return a <code>List</code> of history data for the most recent change for specified contest.
     * @throws PersistenceException if any other error occurs.
     * @tested
     */
    public List<ChangeHistoryData> getLatestChanges(TCSubject tcSubject, long contestId) throws PersistenceException {

        return null;
    }

    /**
     * get milestone submissions for contest
     *<p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return empty list of none submission found for the given contest id.
     * @param contestId The contest id to get the milestone submissions.
     * @throws IllegalArgumentException if long argument is negative
     * @throws ContestServiceException if any other error occurs
     * @since 1.1
     */
    public List<SubmissionData> getMilestoneSubmissionsForContest(TCSubject tcSubject, long contestId)
        throws ContestServiceException {

        return null;
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
     * @param contentType a <code>String</code> providing the content type to get the matching MIME type for.
     * @return a <code>long</code> providing the ID of MIME type matching the specified content type.
     * @throws PersistenceException if some persistence errors occur.
     * @throws IllegalArgumentWSException if the specified <code>contentType</code> is <code>null</code> or
     *             empty.
     * @tested
     */
    public long getMimeTypeId(TCSubject tcSubject, String contentType) throws PersistenceException {

        return 0;
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
     * @param userid user id to look for
     *
     * @return all the permissions that the user owned for any projects.
     *
     * @throws IllegalArgumentWSException if the argument is invalid
     * @throws PermissionServiceException if any error occurs when getting permissions.
     *
     * @since Module Cockpit Contest Service Enhancement Assembly
     */
    public List<Permission> getPermissions(TCSubject tcSubject, long userid, long projectid)
        throws PermissionServiceException {

        return null;
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
     * @param projectid project id to look for
     *
     * @return all the permissions that various users own for a given project.
     *
     * @throws IllegalArgumentWSException if the argument is invalid
     * @throws PermissionServiceException if any error occurs when getting permissions.
     *
     * @since Cockpit Share Submission Integration
     */
    public List<Permission> getPermissionsByProject(TCSubject tcSubject, long projectid)
        throws PermissionServiceException {

        return null;
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
     * @param userid user id to look for
     *
     * @return all the permissions that the user owned for any projects.
     *
     * @throws IllegalArgumentWSException if the argument is invalid
     * @throws PermissionServiceException if any error occurs when getting permissions.
     *
     * @since Module Cockpit Contest Service Enhancement Assembly
     */
    public List<Permission> getPermissionsByUser(TCSubject tcSubject, long userid) throws PermissionServiceException {

        return null;
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
     * @return a list containing all active <code>Categories</code>. It can be empty if no objects found.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public List<Phase> getPhases(TCSubject tcSubject) throws ContestServiceException {

        return null;
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
     * @return a <code>List</code> listing all existing contests. Empty list is returned if there are no
     *         contests found.
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<SimpleContestData> getSimpleContestData(TCSubject tcSubject) throws PersistenceException {

        return null;
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
     * @param pid the given project id
     * @return a <code>List</code> listing all existing contests. Empty list is returned if there are no
     *         contests found.
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<SimpleContestData> getSimpleContestDataByPID(TCSubject tcSubject, long pid)
        throws PersistenceException {

        return null;
    }

    /**
     * <p>
     * BURG-1716: We need to add a method to get software contest by project id, the method wil get all OR
     * project related data, then from project property to get comp version id then to call
     * getAssetByVersionId to get assetDTO, please check create software contest to see what data need to be
     * returned.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId the OR Project Id
     * @return SoftwareCompetition
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since BURG-1716
     */
    public SoftwareCompetition getSoftwareContestByProjectId(TCSubject tcSubject, long projectId)
        throws ContestServiceException {
        SoftwareCompetition competition = softwareCompetitions.get(projectId);
        if (competition == null) {
            throw new ContestServiceException("could not find competition for projectId = " + projectId);
        }
        return competition;
    }

    /**
     * Gets the spec review for specified contest id.
     *
     * Updated for Spec Reviews Finishing Touches v1.0
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param contestId the contest id
     * @param studio indicates whether the specified contest id is for studio contests.
     *
     * @return the spec review that matches the specified contest id.
     *
     * @throws ContestServiceException if any error during retrieval/save from persistence
     * @since Cockpit Launch Contest - Inline Spec Review Part 2
     */
    public SpecReview getSpecReviews(TCSubject tcSubject, long contestId, boolean studio)
        throws ContestServiceException {

        return null;
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
     * @return a <code>List</code> listing available contest statuses. Empty list is returned if there are no
     *         statuses.
     * @throws PersistenceException if some persistence errors occur.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @tested
     */
    public List<ContestStatusData> getStatusList(TCSubject tcSubject) throws PersistenceException {

        return null;
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
     * @return a <code>String</code> providing the comma-separated list of submission types.
     * @throws PersistenceException if some persistence errors occur.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @tested
     */
    public String getSubmissionFileTypes(TCSubject tcSubject) throws PersistenceException {

        return null;
    }

    /**
     * Get the user contest by user name Return empty list if none found
     *<p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param userName the user name to get the user contest
     * @return a list of matching studio competitions
     * @throws IllegalArgumentException if userName is null or empty
     * @throws ContestServiceException if any other error occurs
     * @since 1.1
     */
    public List<StudioCompetition> getUserContests(TCSubject tcSubject, String userName)
        throws ContestServiceException {

        return null;
    }

    /**
     * Returns whether a user is eligible for a particular contest.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param userId The user id
     * @param contestId The contest id
     * @param isStudio true if the contest is a studio contest, false otherwise.
     * @return true if the user is eligible for the specified contest, false otherwise.
     *
     * @throws ContestServiceException if any other error occurs
     * @since 1.2
     */
    public boolean isEligible(TCSubject tcSubject, long userId, long contestId, boolean isStudio)
        throws ContestServiceException {

        return false;
    }

    /**
     * Returns whether the contest is private.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId The contest id
     * @param isStudio true if the contest is a studio contest, false otherwise.
     * @return true if the contest is a private one, false otherwise.
     *
     * @throws ContestServiceException if any other error occurs
     * @since 1.2.3
     */
    public boolean isPrivate(TCSubject tcSubject, long contestId, boolean isStudio) throws ContestServiceException {

        return false;
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
     * @param submissionId a <code>long</code> providing the ID of a submission to be marked for purchase.
     * @throws PersistenceException if any error occurs when marking for purchase.
     * @tested
     */
    public void markForPurchase(TCSubject tcSubject, long submissionId) throws PersistenceException {

    }

    /**
     * Marks 'ready for review' by the writer of the specs for specified contest. Persistence is updated, on
     * update the spec would appear as review opportunity on tc site.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId the specified contest id.
     * @param studio whether contest is studio or not.
     * @throws ContestServiceException if any error during retrieval/save from persistence
     * @since 1.0.1
     */
    public void markReadyForReview(TCSubject tcSubject, long contestId, boolean studio)
        throws ContestServiceException {

    }

    /**
     * Mark review comment with specified comment id as seen.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param commentId the comment id
     *
     * @throws ContestServiceException if any error during retrieval/save from persistence
     * @since Cockpit Launch Contest - Inline Spec Review Part 2
     */
    public void markReviewCommentSeen(TCSubject tcSubject, long commentId) throws ContestServiceException {

    }

    /**
     * Marks 'review done' by reviewer of the specs for specified contest. Persistence is updated and all end
     * users having write/full permission on the contest are notified by email.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId the specified contest id.
     * @param contestName the contest name.
     * @param studio whether contest is studio or not.
     * @param tcDirectProjectId the tc direct project id.
     * @throws ContestServiceException if any error during retrieval/save from persistence
     * @since 1.0.1
     */
    public void markReviewDone(TCSubject tcSubject, long contestId, String contestName, boolean studio,
        long tcDirectProjectId) throws ContestServiceException {

    }

    /**
     * This method uploads a mock file to the corresponding specification review project of the specified
     * project id, so that it can continue with review. Regular submission or final fix will be uploaded
     * according to the open phase.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId the project id of the original project
     *
     * @throws ContestServiceException if any unexpected error occurs in the underlying services, if the
     *             associated specification review project id cannot be found or if neither submission or
     *             final fixes phase are open.
     *
     * @since 1.4
     */
    public void markSoftwareContestReadyForReview(TCSubject tcSubject, long projectId) throws ContestServiceException {

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
     * @param competition <code>ContestData</code> data that recognizes a contest.
     * @param paymentData <code>PaymentData</code> payment information (TCSubject tcSubject,credit card/po
     *            details) that need to be processed.
     * @return a <code>PaymentResult</code> result of the payment processing.
     * @throws PersistenceException if any error occurs when getting contest.
     * @throws ContestNotFoundException if contest is not found while update.
     * @throws PaymentException if any errors occurs in processing the payment.
     * @throws IllegalArgumentException if specified <code>filter</code> is <code>null</code> or if it is not
     *             supported by implementor.
     * @since BUGR-1494 returns ContestPaymentResult instead of PaymentResult
     */
    public ContestPaymentResult processContestCreditCardPayment(TCSubject tcSubject, StudioCompetition competition,
        CreditCardPaymentData paymentData) throws PersistenceException, PaymentException, ContestNotFoundException {

        PaymentResult paymentResult = new PaymentResult();
        paymentResult.setReferenceNumber("ref3");

        ContestPaymentResult result = new ContestPaymentResult();
        result.setPaymentResult(paymentResult);

        return result;

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
     * @param competition data that recognizes a contest.
     * @param paymentData payment information (TCSubject tcSubject,credit card/po details) that need to be
     *            processed.
     *
     * @return a <code>PaymentResult</code> result of the payment processing.
     *
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     * @since BUGR-1682 changed return value
     */
    public SoftwareContestPaymentResult processContestCreditCardSale(TCSubject tcSubject,
        SoftwareCompetition competition, CreditCardPaymentData paymentData) throws ContestServiceException {

        PaymentResult paymentResult = new PaymentResult();
        paymentResult.setReferenceNumber("ref4");

        SoftwareContestPaymentResult result = new SoftwareContestPaymentResult();
        result.setPaymentResult(paymentResult);

        return result;

    }

    /**
     * @since BUGR-1494 returns ContestPaymentResult instead of PaymentResult
     *        <p>
     *        Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     *        </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param competition
     * @param paymentData
     * @return ContestPaymentResult
     * @throws PersistenceException
     * @throws PaymentException
     * @throws ContestNotFoundException
     */
    public ContestPaymentResult processContestPurchaseOrderPayment(TCSubject tcSubject,
        StudioCompetition competition, TCPurhcaseOrderPaymentData paymentDat) throws PersistenceException,
        PaymentException, ContestNotFoundException {

        PaymentResult paymentResult = new PaymentResult();
        paymentResult.setReferenceNumber("ref1");

        ContestPaymentResult result = new ContestPaymentResult();
        result.setPaymentResult(paymentResult);

        return result;
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
     * @param competition data that recognizes a contest.
     * @param paymentData payment information (TCSubject tcSubject,credit card/po details) that need to be
     *            processed.
     *
     * @return a <code>PaymentResult</code> result of the payment processing.
     *
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     * @since BUGR-1682 changed return value
     */
    public SoftwareContestPaymentResult processContestPurchaseOrderSale(TCSubject tcSubject,
        SoftwareCompetition competition, TCPurhcaseOrderPaymentData paymentData) throws ContestServiceException {

        PaymentResult paymentResult = new PaymentResult();
        paymentResult.setReferenceNumber("ref2");

        SoftwareContestPaymentResult result = new SoftwareContestPaymentResult();
        result.setPaymentResult(paymentResult);

        return result;
    }

    /**
     * <p>
     * Sends payments to <code>PACTS</code> application for all unpaid submussions with a prize already
     * assigned. This service is not atomic. If it fails, you'll have to check what payments where actually
     * done trough the <code>submussion.paid</code> flag.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param contestId a <code>long</code> providing the ID of a contest to process missing payments for.
     * @throws PersistenceException if any error occurs when processing a payment.
     */
    public void processMissingPayments(TCSubject tcSubject, long contestId) throws PersistenceException {

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
     * @param completedContestData data of completed contest.
     * @param paymentData a <code>PaymentData</code> payment information (TCSubject tcSubject,credit card/po
     *            details) that need to be processed.
     * @return a <code>PaymentResult</code> result of the payment processing.
     * @throws PaymentException if any errors occurs in processing the payment or submission is not valid.
     * @throws PersistenceException if any error occurs when retrieving the submission.
     */
    public PaymentResult processSubmissionCreditCardPayment(TCSubject tcSubject,
        CompletedContestData completedContestData, CreditCardPaymentData paymentData) throws PaymentException,
        PersistenceException {

        return null;
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
     * @param completedContestData data of completed contest.
     * @param paymentData a <code>PaymentData</code> payment information (TCSubject tcSubject,credit card/po
     *            details) that need to be processed.
     * @return a <code>PaymentResult</code> result of the payment processing.
     * @throws PaymentException if any errors occurs in processing the payment or submission is not valid.
     * @throws PersistenceException if any error occurs when retrieving the submission.
     */
    public PaymentResult processSubmissionPurchaseOrderPayment(TCSubject tcSubject,
        CompletedContestData completedContestData, TCPurhcaseOrderPaymentData paymentData) throws PaymentException,
        PersistenceException {

        return null;
    }

    /**
     * <p>
     * Purchases the specified submission. E.g. records a fact that submission referenced by specified ID has
     * been paid for in the course of payment transaction referenced by the specified <code>PayPal</code>
     * order ID.
     * </p>
     *
     *
     * @param submissionId a <code>long</code> providing the ID of a submission which has been paid for.
     * @param payPalOrderId a <code>String</code> providing the <code>PayPal</code> order ID referencing the
     *            payment transaction.
     * @param securityToken a <code>String</code> providing the security token to be used for tracking the
     *            payment and prevent fraud.
     * @throws PersistenceException if any error occurs when purchasing submission.
     * @throws IllegalArgumentWSException if specified <code>submissionId</code> is negative.
     * @tested
     */
    public void purchaseSubmission(TCSubject tcSubject, long submissionId,
        SubmissionPaymentData submissionPaymentData, String securityToken) throws PersistenceException {

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
     * @param submissionIdsInRankOrder an array of long submission identifier in the rank order.
     * @return a <code>boolean</code> true if successful, else false.
     * @throws PersistenceException if any error occurs when retrieving/updating the data.
     */
    public boolean rankSubmissions(TCSubject tcSubject, long[] submissionIdsInRankOrder) throws PersistenceException {

        return false;
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
     * @param tcDirectProjectId the tc-direct-project-id
     * @return returns the newly created contest id
     * @throws ContestServiceException if any problem occurs
     */
    public long reOpenSoftwareContest(TCSubject tcSubject, long projectId, long tcDirectProjectId)
        throws ContestServiceException {

        return 0;
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
     * @param document an <code>UploadedDocument</code> representing the document to be removed.
     * @throws PersistenceException if some persistence errors occur.
     * @throws DocumentNotFoundException if the specified document is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the specified argument is <code>null</code>.
     * @tested
     */
    public boolean removeDocument(TCSubject tcSubject, long documentId) throws PersistenceException {

        return false;
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
     * @param document an <code>UploadedDocument</code> representing the document to be removed.
     * @throws PersistenceException if some persistence errors occur.
     * @throws DocumentNotFoundException if the specified document is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the specified argument is <code>null</code>.
     * @tested
     */
    public void removeDocumentFromContest(TCSubject tcSubject, UploadedDocument document)
        throws PersistenceException, DocumentNotFoundException {
        if (!studioCompetitionDocuments.containsKey(document.getDocumentId())) {
            throw new DocumentNotFoundException("could not find document", document.getDocumentId());
        }
        studioCompetitionDocuments.remove(document.getDocumentId());

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
     * @param submissionId a <code>long</code> providing the ID of a submission to remove.
     * @throws PersistenceException if some persistence errors occur.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the <code>submissionId</code> is negative.
     * @tested
     */
    public void removeSubmission(TCSubject tcSubject, long submissionId) throws PersistenceException {

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
     * @param userId the id of the user
     * @param assetId the id of the asset
     *
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public void removeUserFromAsset(TCSubject tcSubject, long userId, long assetId) throws ContestServiceException {

    }

    /**
     * Marks 'resubmit for review' by the writer of the specs for specified contest. Persistence is updated.
     * Reviewer (TCSubject tcSubject,if any) is notified about the updates.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId the specified contest id.
     * @param contestName the contest name.
     * @param studio whether contest is studio or not.
     * @param reviewerUserId reviewer user id.
     * @throws ContestServiceException if any error during retrieval/save from persistence
     * @since 1.0.1
     */
    public void resubmitForReview(TCSubject tcSubject, long contestId, String contestName, boolean studio,
        long reviewerUserId) throws ContestServiceException {

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
     * @param contestId a <code>long</code> providing the ID of a contest to get the list of submissions for.
     * @return a <code>List</code> providing the details for the submissions associated with the specified
     *         contest. Empty list is returned if there are no submissions found.
     * @throws PersistenceException if some persistence errors occur.
     * @throws ContestNotFoundException if requested contest is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the specified ID is negative.
     * @tested
     */
    public SubmissionData retrieveSubmission(TCSubject tcSubject, long submissionId) throws PersistenceException {

        return null;
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
     * @param contestId a <code>long</code> providing the ID of a contest to get the list of submissions for.
     * @return a <code>List</code> providing the details for the submissions associated with the specified
     *         contest. Empty list is returned if there are no submissions found.
     * @throws PersistenceException if some persistence errors occur.
     * @throws ContestNotFoundException if requested contest is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the specified ID is negative.
     * @tested
     */
    public List<SubmissionData> retrieveSubmissionsForContest(TCSubject tcSubject, long contestId)
        throws PersistenceException, ContestNotFoundException {

        return null;
    }

    /**
     * Save specified review comment for specified section and specified contest id to persistence.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId the contest id
     * @param studio indicates whether the specified contest id is for studio contests.
     * @param sectionName the section name
     * @param comment the comment
     * @param role the user role type
     *
     * @throws ContestServiceException if any error during retrieval/save from persistence
     * @since Cockpit Launch Contest - Inline Spec Review Part 2
     */
    public void saveReviewComment(TCSubject tcSubject, long contestId, boolean studio, String sectionName,
        String comment, String role) throws ContestServiceException {

    }

    /**
     * Save specified review comment and review status for specified section and specified contest id to
     * persistence.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId the contest id
     * @param studio indicates whether the specified contest id is for studio contests.
     * @param sectionName the section name
     * @param comment the comment
     * @param isPass the is pass
     * @param role the user role type
     *
     * @throws ContestServiceException if any error during retrieval/save from persistence
     * @since Cockpit Launch Contest - Inline Spec Review Part 2
     */
    public void saveReviewStatus(TCSubject tcSubject, long contestId, boolean studio, String sectionName,
        String comment, boolean isPass, String role) throws ContestServiceException {

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
     * @param filter a <code>Filter</code> providing the criteria for searching for contests.
     * @return a <code>List</code> listing all existing contests matching the specified criteria. Empty list
     *         is returned if there are no matching contests found.
     * @throws PersistenceException if any error occurs when getting contest.
     * @throws IllegalArgumentException if specified <code>filter</code> is <code>null</code> or if it is not
     *             supported by implementor.
     */
    public List<StudioCompetition> searchContests(TCSubject tcSubject, ContestServiceFilter filter)
        throws PersistenceException {

        return null;
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
     *
     * @return list of matching users, empty list if none matches.
     * @since TCCC-1329
     */
    public List<User> searchUser(TCSubject tcSubject, String key) throws PersistenceException {

        return null;
    }

    /**
     * set submission milestone prize If given submission has already been associated with the given milestone
     * prize before, ContestServiceException will be thrown. It's required that the contest field of the
     * submission of given id is one of the contests set of the milestone prize, otherwise,
     * ContestServiceException will be thrown. If the MilestonePrize with given id has reached the max number
     * of submissions, ContestServiceException will be thrown.
     *<p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param submissionId The submission id
     * @param milestonePrizeId The milestone prize id
     * @throws IllegalArgumentException if long argument is negative
     * @throws ContestServiceException if any other error occurs
     * @since 1.1
     */
    public void setSubmissionMilestonePrize(TCSubject tcSubject, long submissionId, long milestonePrizeId)
        throws ContestServiceException {

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
     * @param submissionId a <code>long</code> providing the ID of a submission to set the placement for.
     * @param placement an <code>int</code> providing the submission placement.
     * @throws PersistenceException if any error occurs when setting placement.
     * @tested
     */
    public void setSubmissionPlacement(TCSubject tcSubject, long submissionId, int placement)
        throws PersistenceException {

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
     * @param submissionId a <code>long</code> providing the ID of a submission.
     * @param prizeId a <code>long</code> providing the ID of a prize.
     * @throws PersistenceException if any error occurs when setting submission prize.
     * @tested
     */
    public void setSubmissionPrize(TCSubject tcSubject, long submissionId, long prizeId) throws PersistenceException {

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
     * @param submissionId the submission's id
     * @param submissionStatusId the submission status id
     * @param operator the operator which execute the operation
     *
     * @throws IllegalArgumentException if any id is &lt; 0 or if operator is null or trim to empty
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public void setSubmissionStatus(TCSubject tcSubject, long submissionId, long submissionStatusId, String operator)
        throws ContestServiceException {

    }

    /**
     * <p>
     * Updates the specified contest.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param contest a <code>StudioCompetition</code> providing the contest data to update.
     * @throws PersistenceException if some persistence errors occur.
     * @throws ContestNotFoundException if requested contest is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if specified <code>contest</code> is <code>null</code>.
     * @tested
     */
    public void updateContest(TCSubject tcSubject, StudioCompetition contest) throws PersistenceException,
        ContestNotFoundException {

    }

    /**
     * <p>
     * Sets the status of contest referenced by the specified ID to specified value.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param contestId a <code>long</code> providing the ID of a contest to update status for.
     * @param newStatusId a <code>long</code> providing the ID of a new contest status to set.
     * @throws PersistenceException if some persistence errors occur.
     * @throws StatusNotAllowedException if the specified status is not allowed to be set for specified
     *             contest.
     * @throws StatusNotFoundException if specified status is not found.
     * @throws ContestNotFoundException if specified contest is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if any of specified IDs is negative.
     * @tested
     */
    public void updateContestStatus(TCSubject tcSubject, long contestId, long newStatusId)
        throws PersistenceException, StatusNotAllowedException, StatusNotFoundException, ContestNotFoundException {

    }

    /**
     * <p>
     * This method will update permission type data.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param type the permission type to update.
     *
     * @throws IllegalArgumentWSException if the argument is invalid
     * @throws PermissionServiceException if any error occurs when updating the permission type.
     *
     * @since Module Cockpit Contest Service Enhancement Assembly
     */
    public void updatePermissionType(TCSubject tcSubject, PermissionType type) throws PermissionServiceException {

    }

    /**
     * <p>
     * This method updates array of permissions to the persistence.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param permissions the permissions to update.
     *
     * @throws IllegalArgumentWSException if the argument is invalid
     * @throws PermissionServiceException if any error occurs when updating the permission.
     *
     * @since Cockpit Project Admin Release Assembly.
     */
    public void updatePermissions(TCSubject tcSubject, Permission[] permissions) throws PermissionServiceException {

    }

    /**
     * <p>
     * Updates a <code>SoftwareCompetition</code> in the persistence.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param contest the <code>SoftwareCompetition</code> to update as a contest
     * @param tcDirectProjectId the TC direct project id.
     *
     * @throws IllegalArgumentException if the input argument is invalid.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition updateSoftwareContest(TCSubject tcSubject, SoftwareCompetition contest,
        long tcDirectProjectId) throws ContestServiceException {

        return null;
    }

    /**
     * <p>
     * Updates specified submission.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param submission a <code>SubmissionData</code> providing the data for submission to be updated.
     * @throws PersistenceException if some persistence errors occur.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the specified argument is <code>null</code>.
     * @tested
     */
    public void updateSubmission(TCSubject tcSubject, SubmissionData submission) throws PersistenceException {

    }

    /**
     * <p>
     * Ranks the submissions, given submission identifiers and the rank. If the isRankingMilestone flag is
     * true, the rank will target milestone submissions.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param submissionId identifier of the submission.
     * @param rank rank of the submission.
     * @param isRankingMilestone true if the user is ranking milestone submissions.
     *
     * @return a <code>boolean</code> true if successful, else false.
     * @throws PersistenceException if any error occurs when retrieving/updating the data.
     * @since TCCC-1219
     */
    public boolean updateSubmissionUserRank(TCSubject tcSubject, long submissionId, int rank,
        Boolean isRankingMilestone) throws PersistenceException {

        return false;
    }

    /**
     * <p>
     * Updates the submission feedback.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param feedbacks an array of <code>SubmissionFeedback</code>
     * @return a <code>boolean</code> true if successful, else false.
     * @throws PersistenceException if any error occurs when retrieving/updating the data.
     */
    public boolean updateSubmissionsFeedback(TCSubject tcSubject, SubmissionFeedback[] feedbacks)
        throws PersistenceException {

        return false;
    }

    /**
     * <p>
     * Uploads the specified document and associates it with assigned contest.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param uploadedDocument an <code>UploadedDocument</code> providing the data for the uploaded document.
     * @return an <code>UploadedDocument</code> passed as argument and having the document ID auto-generated
     *         and set.
     * @throws PersistenceException if some persistence errors occur.
     * @throws ContestNotFoundException if requested contest is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the argument is <code>null</code>.
     * @tested
     */
    public UploadedDocument uploadDocument(TCSubject tcSubject, UploadedDocument uploadedDocument)
        throws PersistenceException {

        return null;
    }

    /**
     * <p>
     * Uploads the specified document and associates it with assigned contest.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param uploadedDocument an <code>UploadedDocument</code> providing the data for the uploaded document.
     * @return an <code>UploadedDocument</code> passed as argument and having the document ID auto-generated
     *         and set.
     * @throws PersistenceException if some persistence errors occur.
     * @throws ContestNotFoundException if requested contest is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the argument is <code>null</code>.
     * @tested
     */
    public UploadedDocument uploadDocumentForContest(TCSubject tcSubject, UploadedDocument uploadedDocument)
        throws PersistenceException, ContestNotFoundException {
        if (uploadedDocument.getContestId() == 100) {
            throw new ContestNotFoundException("The contest could not be found", uploadedDocument.getDocumentId());
        }
        addDocument(uploadedDocument);
        return uploadedDocument;
    }

    /**
     * <p>
     * Adds a new final fix upload for an user in a particular project. This submission always overwrite the
     * previous ones.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId the project's id
     * @param filename the file name to use
     * @param finalFix the final fix file data
     *
     * @return the id of the created final fix submission
     *
     * @throws IllegalArgumentException if any id is &lt; 0, if any argument is <code>null</code> or trim to
     *             empty
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public long uploadFinalFix(TCSubject tcSubject, long projectId, String filename, DataHandler finalFix)
        throws ContestServiceException {

        return 0;
    }

    /**
     * <p>
     * Adds a new submission for an user in a particular project.
     * </p>
     *
     * <p>
     * If the project allows multiple submissions for users, it will add the new submission and return. If
     * multiple submission are not allowed for the project, firstly it will add the new submission, secondly
     * mark previous submissions as deleted and then return.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId the project's id
     * @param filename the file name to use
     * @param submission the submission file data
     *
     * @return the id of the new submission
     *
     * @throws IllegalArgumentException if any id is &lt; 0, if any argument is <code>null</code> or trim to
     *             empty
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public long uploadSubmission(TCSubject tcSubject, long projectId, String filename, DataHandler submission)
        throws ContestServiceException {

        return 0;
    }

    /**
     * <p>
     * Adds a new test case upload for an user in a particular project. This submission always overwrite the
     * previous ones.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId the project's id
     * @param filename the file name to use
     * @param testCases the test cases data
     *
     * @return the id of the created test cases submission
     *
     * @throws IllegalArgumentException if any id is &lt; 0, if any argument is <code>null</code> or trim to
     *             empty
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public long uploadTestCases(TCSubject tcSubject, long projectId, String filename, DataHandler testCases)
        throws ContestServiceException {

        return 0;
    }

    /**
     * <p>
     * Get all the DocumentType objects.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @return the list of all available DocumentType
     *
     * @throws ContestManagementException if any error occurs when getting contest
     *
     * @throws PersistenceException if any error occurss
     * @since 1.1.2
     */
    public List<DocumentType> getAllDocumentTypes(TCSubject tcSubject) throws PersistenceException {

        return null;
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
     * @throws PersistenceException if any error occurs when getting studio file types.
     */
    public List<StudioFileType> getAllStudioFileTypes(TCSubject tcSubject) throws PersistenceException {

        return null;
    }

    /**
     * This method retrieves scorecard and review information associated to a project determined by parameter.
     * Note: a single reviewer / review is assumed.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId the project id to search for
     * @return the aggregated scorecard and review data
     *
     * @throws ContestServiceException if any unexpected error occurs in the underlying services
     *
     * @since 1.4
     */
    public ScorecardReviewData getScorecardAndReview(TCSubject tcSubject, long projectId)
        throws ContestServiceException {

        return null;
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
     * @return a <code>List</code> listing all existing contests. Empty list is returned if there are no
     *         contests found.
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<SimpleProjectContestData> getSimpleProjectContestData(TCSubject tcSubject)
        throws PersistenceException {

        return null;
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
     * @param pid given project id
     * @return a <code>List</code> listing all existing contests. Empty list is returned if there are no
     *         contests found.
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<SimpleProjectContestData> getSimpleProjectContestDataByPID(TCSubject tcSubject, long pid)
        throws PersistenceException {

        return null;
    }

    /**
     * <p>
     * Purchases the specified submission. E.g. records a fact that submission referenced by specified ID has
     * been paid for in the course of payment transaction referenced by the specified <code>PayPal</code>
     * order ID.
     * </p>
     *
     *
     * @param submissionId a <code>long</code> providing the ID of a submission which has been paid for.
     * @param payPalOrderId a <code>String</code> providing the <code>PayPal</code> order ID referencing the
     *            payment transaction.
     * @param securityToken a <code>String</code> providing the security token to be used for tracking the
     *            payment and prevent fraud.
     * @throws PersistenceException if any error occurs when purchasing submission.
     * @throws IllegalArgumentWSException if specified <code>submissionId</code> is negative.
     * @tested
     */
    public void purchaseSubmission(TCSubject tcSubject, long submissionId, String payPalOrderId, String securityToken)
        throws PersistenceException {

    }
}
