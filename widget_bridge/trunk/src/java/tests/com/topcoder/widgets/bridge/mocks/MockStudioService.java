/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.widgets.bridge.mocks;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.studio.ContestCategoryData;
import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.ContestNotFoundException;
import com.topcoder.service.studio.ContestPayload;
import com.topcoder.service.studio.ContestStatusData;
import com.topcoder.service.studio.ContestTypeData;
import com.topcoder.service.studio.DocumentNotFoundException;
import com.topcoder.service.studio.IllegalArgumentWSException;
import com.topcoder.service.studio.PersistenceException;
import com.topcoder.service.studio.PrizeData;
import com.topcoder.service.studio.ProjectNotFoundException;
import com.topcoder.service.studio.StatusNotAllowedException;
import com.topcoder.service.studio.StatusNotFoundException;
import com.topcoder.service.studio.StudioService;
import com.topcoder.service.studio.SubmissionData;
import com.topcoder.service.studio.UploadedDocument;
import com.topcoder.service.studio.UserNotAuthorizedException;
import com.topcoder.widgets.bridge.TestHelper;

/**
 * Mock implementation for Studio Service.
 *
 * @author pinoydream
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockStudioService implements StudioService {
    /**
     * <p>
     * Creates contest for project. Return contest populated with id. Mock implementation.
     * </p>
     *
     * @param contestData the contestData used to create the Contest
     * @param tcDirectProjectId the tc project id set to Contest
     * @return the ContestData persisted and populated with the new id
     * @throws IllegalArgumentWSException if the contestData is null, if the tcDirectProjectId is less than 0
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to perform this method
     */
    public ContestData createContest(ContestData contestData, long tcDirectProjectId) throws PersistenceException {
        checkNull(contestData, "contest");
        checkId(tcDirectProjectId, "project id");
        if (tcDirectProjectId == 1) {
            ContestData contest1 = getContestObj(1);
            return contest1;
        } else {
            throw new PersistenceException("mock error: only allow project id of 1.", "persistence error.");
        }
    }

    /**
     * <p>
     * Gets contest by id. Mock implementation.
     * </p>
     *
     * @param contestId the id used to retrieve the contest
     * @return the ContestData retrieved
     * @throws IllegalArgumentWSException if the contestId is less than 0
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to perform this method
     * @throws ContestNotFoundException if the contest is not found
     */
    public ContestData getContest(long contestId) throws PersistenceException, ContestNotFoundException {
        checkId(contestId, "contest id");
        if (contestId == 1) {
            ContestData contest = getContestObj(1);
            return contest;
        } else if (contestId == 50) {
            throw new ContestNotFoundException("Contest not found", 50L);
        } else {
            throw new PersistenceException("mock error.", "persistence error.");
        }

    }

    /**
     * <p>
     * Gets contests for given project. Return an empty list if there are no contests.
     * </p>
     *
     * @param tcDirectProjectId the tc Direct Project Id used to retrieve the ContestData
     * @return the contest data list which represents the contests
     * @throws IllegalArgumentWSException if the tcDirectProjectId is less than 0
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to perform this method
     * @throws ProjectNotFoundException if the project is not found
     */
    public List<ContestData> getContestsForProject(long tcDirectProjectId) throws PersistenceException,
        ProjectNotFoundException {
        checkId(tcDirectProjectId, "project id");
        if (tcDirectProjectId == 1) {
            List<ContestData> contests = new ArrayList<ContestData>();
            contests.add(getContestObj(1));
            contests.add(getContestObj(2));
            return contests;
        } else if (tcDirectProjectId == 20) {
            throw new UserNotAuthorizedException("user not authorized", 20L);
        } else {
            throw new PersistenceException("mock error.", "persistence error.");
        }
    }

    /**
     * <p>
     * Updates contest. Mock Implementation.
     * </p>
     *
     * @param contest the contest data to update
     * @throws IllegalArgumentWSException if the argument is null
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to perform this method
     * @throws ContestNotFoundException if the contest is not found
     */
    public void updateContest(ContestData contest) throws PersistenceException, ContestNotFoundException {
        checkNull(contest, "contest");
        if (contest.getContestId() == 1) {
            // do nothing
        } else {
            throw new PersistenceException("mock error.", "persistence error.");
        }
    }

    /**
     * <p>
     * Updates contest status. Mock implementation.
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
        StatusNotAllowedException, StatusNotFoundException, ContestNotFoundException {
        checkId(contestId, "contest id");
        checkId(newStatusId, "new status id");
        if (contestId == 1) {
            // do nothing
        } else {
            throw new PersistenceException("mock error.", "persistence error.");
        }
    }

    /**
     * <p>
     * Upload document to contest. Return document populated with id.
     *
     * @param document the uploadDocument to update
     * @return the same instance passed in argument with the documentId updated
     * @throws IllegalArgumentWSException if the argument is null
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to perform this method
     * @throws ContestNotFoundException if the contest is not found
     */
    public UploadedDocument uploadDocumentForContest(UploadedDocument document) throws PersistenceException,
        ContestNotFoundException {
        checkNull(document, "document");
        if (document.getDocumentId() == 1) {
            UploadedDocument upDoc2 = new UploadedDocument();
            upDoc2.setContestId(1233);
            upDoc2.setDescription("description");
            upDoc2.setDocumentId(document.getDocumentId());
            upDoc2.setFileName("fileName");
            upDoc2.setPath("path");
            return upDoc2;
        } else {
            throw new PersistenceException("mock error.", "persistence error.");
        }
    }

    /**
     * <p>
     * Removes document from contest.
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
        checkNull(document, "document");
        if (document.getDocumentId() == 1) {
            // do nothing
        } else {
            throw new PersistenceException("mock error.", "persistence error.");
        }
    }

    /**
     * <p>
     * Retrieves submission data. return an empty list if there are no submission data. Mock implementation.
     * </p>
     *
     * @param contestId the contest if used
     * @return the submission data of contest
     * @throws IllegalArgumentWSException if the argument id less than 0
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to perform this method
     * @throws ContestNotFoundException if the contest is not found
     */
    public List<SubmissionData> retrieveSubmissionsForContest(long contestId) throws PersistenceException,
        ContestNotFoundException {
        checkId(contestId, "contest id");
        if (contestId == 1) {
            List<SubmissionData> submissions = new ArrayList<SubmissionData>();
            submissions.add(getSubmission(5555));
            submissions.add(getSubmission(6666));
            return submissions;
        } else {
            throw new PersistenceException("mock error.", "persistence error.");
        }
    }

    /**
     * <p>
     * Retrieves submissions by member. return an empty list if there are no submission data. Mock implementation.
     * </p>
     *
     * @param userId the user id used to retrieve the submissions
     * @return the submissions by member
     * @throws IllegalArgumentWSException if the id is less than 0
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to perform this method
     */
    public List<SubmissionData> retrieveAllSubmissionsByMember(long userId) throws PersistenceException {
        checkId(userId, "user id");
        if (userId == 1) {
            List<SubmissionData> submissions = new ArrayList<SubmissionData>();
            submissions.add(getSubmission(5555));
            submissions.add(getSubmission(6666));
            return submissions;
        } else {
            throw new PersistenceException("mock error.", "persistence error.");
        }
    }

    /**
     * <p>
     * Update submission.
     *
     * @param submission the submission to update
     * @throws IllegalArgumentWSException if the submission is null
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to perform this method
     */
    public void updateSubmission(SubmissionData submission) throws PersistenceException {
        checkNull(submission, "submission");
        if (submission.getSubmissionId() == 1) {
            // do nothing
        } else {
            throw new PersistenceException("mock error.", "persistence error.");
        }
    }

    /**
     * <p>
     * Removes submission.
     * </p>
     *
     * @param submissionId the id of submission to remove
     * @throws IllegalArgumentWSException if the submissionId is less than 0
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to perform this method
     */
    public void removeSubmission(long submissionId) throws PersistenceException {
        checkId(submissionId, "submission id");
        if (submissionId == 1) {
            // do nothing
        } else {
            throw new PersistenceException("mock error.", "persistence error.");
        }
    }

    /**
     * <p>
     * Gets contest categories. Mock implementation.
     * </p>
     *
     * @return the list of categories
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to perform this method
     */
    public List<ContestCategoryData> getContestCategories() throws PersistenceException {
        ContestCategoryData category1 = new ContestCategoryData();
        category1.setContestCategory("Category Cont1");
        category1.setContestCategoryId(111);
        category1.setContestDescription("Description");
        category1.setContestName("Contest Name1");

        ContestCategoryData category2 = new ContestCategoryData();
        category2.setContestCategory("Category Cont2");
        category2.setContestCategoryId(111222);
        category2.setContestDescription("Decription2");
        category2.setContestName("Contest Name2");

        List<ContestCategoryData> categories = new ArrayList<ContestCategoryData>();
        categories.add(category1);
        categories.add(category2);
        return categories;
    }

    /**
     * <p>
     * Get contest statuses. Return an empty list if there are no ContestStatusData.
     * </p>
     *
     * @return the list of status
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to perform this method
     */
    public List<ContestStatusData> getStatusList() throws PersistenceException {
        List<ContestStatusData> statuses = new ArrayList<ContestStatusData>();
        statuses.add(getContestStatus(1));
        statuses.add(getContestStatus(2));
        return statuses;
    }

    /**
     * <p>
     * Gets submission types. Mock implementation.
     * </p>
     *
     * @return the file types
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to perform this method
     */
    public String getSubmissionFileTypes() throws PersistenceException {
        return "jpg,gif,png";
    }

    /**
     * <p>
     * This is going to fetch all the currently available contests.
     * </p>
     *
     * @return the list of all available contents (or empty if none found)
     *
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<ContestData> getAllContests() throws PersistenceException {
        List<ContestData> contests = new ArrayList<ContestData>();
        contests.add(getContestObj(10));
        contests.add(getContestObj(20));
        return contests;
    }

    /**
     * <p>
     * This is going to get all the matching contest entities that fulfill the input criteria.
     * </p>
     *
     * @param filter a search filter used as criteria for contests.
     * @return a list (possibly empty) of all the matched contest entities.
     *
     * @throws IllegalArgumentException if the input filter is null or filter is not supported for searching
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<ContestData> searchContests(Filter filter) throws PersistenceException {
        throw new UnsupportedOperationException("not supported");
    }

    /**
     * <p>
     * Returns all contests for a particular client ID, provided as a long.
     * </p>
     *
     * @param clientId client Id to search for.
     * @return all contests for the given client ID.
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<ContestData> getContestsForClient(long clientId) throws PersistenceException {
        checkId(clientId, "client id");
        if (clientId == 1) {
            List<ContestData> contests = new ArrayList<ContestData>();
            contests.add(getContestObj(1));
            contests.add(getContestObj(2));
            return contests;
        } else {
            throw new PersistenceException("mock error.", "persistence error.");
        }
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
    public SubmissionData retrieveSubmission(long submissionId) throws PersistenceException {
        checkId(submissionId, "submission id");
        if (submissionId == 1) {
            return getSubmission(1);
        } else if (submissionId == 20) {
            return null;
        } else {
            throw new PersistenceException("mock error.", "persistence error.");
        }
    }

    /**
     * <p>
     * This is going to fetch all the currently available contest types.
     * </p>
     *
     * @return the list of all available content types (or empty if none found)
     *
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<ContestTypeData> getAllContestTypes() throws PersistenceException {
        List<ContestTypeData> types = new ArrayList<ContestTypeData>();
        types.add(getContestTypeData(1));
        types.add(getContestTypeData(2));
        return types;
    }

    /**
     * <p>
     * Creates mocked contest.
     * </p>
     *
     * @param contestID contest id
     * @return contest created manually.
     */
    private ContestData getContestObj(long contestID) {
        ContestData contest = new ContestData();
        // set simple types
        contest.setContestId(contestID);
        contest.setProjectId(32);
        contest.setName("Contest name");
        contest.setShortSummary("Short sum");
        contest.setDurationInHours(100);
        contest.setFinalFileFormat("bmp,png");
        contest.setOtherFileFormats("jpg,gif");
        // contest.setStatusId(1);
        contest.setContestCategoryId(2);
        contest.setContestDescriptionAndRequirements("contest reqs");
        contest.setRequiredOrRestrictedColors("Red");
        contest.setRequiredOrRestrictedFonts("Arial");
        contest.setSizeRequirements("Size");
        contest.setOtherRequirementsOrRestrictions("other reqs");
        contest.setTcDirectProjectId(10000);
        contest.setCreatorUserId(30000);

        try {
            contest.setLaunchDateAndTime(TestHelper.getXMLGregorianCalendar("2008-04-01 09:00"));
            contest.setWinnerAnnoucementDeadline(TestHelper.getXMLGregorianCalendar("2008-05-01 09:00"));
        } catch (ParseException e) {
            // Simply catch it here
        }

        PrizeData p1 = new PrizeData();
        p1.setAmount(1000);
        p1.setPlace(1);

        PrizeData p2 = new PrizeData();
        p2.setAmount(1000);
        p2.setPlace(1);

        List<PrizeData> prizes = new ArrayList<PrizeData>();
        prizes.add(p1);
        prizes.add(p2);
        contest.setPrizes(prizes);

        UploadedDocument upDoc1 = new UploadedDocument();
        upDoc1.setContestId(123);
        upDoc1.setDescription("desc of uploaded");
        upDoc1.setDocumentId(3344);
        upDoc1.setFileName("fileName1");
        upDoc1.setPath("path1");

        UploadedDocument upDoc2 = new UploadedDocument();
        upDoc2.setContestId(1233);
        upDoc2.setDescription("desc of222 uploaded");
        upDoc2.setDocumentId(3344);
        upDoc2.setFileName("fileName2");
        upDoc2.setPath("path2");

        List<UploadedDocument> uploadedDocs = new ArrayList<UploadedDocument>();
        uploadedDocs.add(upDoc1);
        uploadedDocs.add(upDoc2);
        contest.setDocumentationUploads(uploadedDocs);

        ContestPayload payload1 = new ContestPayload();
        payload1.setName("Name of payload1");
        payload1.setValue("Value of payload1");
        payload1.setRequired(true);

        ContestPayload payload2 = new ContestPayload();
        payload2.setName("Name of payload2");
        payload2.setValue("Value of payload2");
        payload2.setRequired(true);

        List<ContestPayload> payloads = new ArrayList<ContestPayload>();
        payloads.add(payload1);
        payloads.add(payload2);
        contest.setContestPayloads(payloads);

        return contest;
    }

    /**
     * <p>
     * Creates mock contest status.
     * </p>
     *
     * @param statusID the status to be retrieved
     * @return contest status created manually.
     */
    private ContestStatusData getContestStatus(long statusID) {
        ContestStatusData status = new ContestStatusData();
        status.setAllowableNextStatus(Arrays.asList(new Long[] {2L, 3L, 4L}));
        status.setDescription("Status Description");
        status.setName("Status Name");
        status.setStatusId(statusID);

        return status;
    }

    /**
     * <p>
     * Creates Mock submission.
     * </p>
     *
     * @param submissionID the id of submission to be retrieved
     * @return submission created manually.
     */
    private SubmissionData getSubmission(long submissionID) {
        SubmissionData submission = new SubmissionData();
        submission.setContestId(1234);
        submission.setMarkedForPurchase(true);
        submission.setPaidFor(true);
        submission.setPassedScreening(new Boolean(true));
        submission.setPlacement(2);
        submission.setPrice(234);
        submission.setSubmissionContent("content");
        submission.setSubmissionId(submissionID);
        try {
            submission.setSubmittedDate(TestHelper.getXMLGregorianCalendar("2008-04-04 09:00"));
        } catch (ParseException e) {
            // it should not occur and just catch here
        }
        submission.setSubmitterId(32221);

        return submission;
    }

    /**
     * <p>
     * Gets dummy contest type data.
     * </p>
     *
     * @param contestTypeID contest type id
     * @return mock contest type data
     */
    private ContestTypeData getContestTypeData(long contestTypeID) {
        ContestTypeData type = new ContestTypeData();
        type.setContestTypeId(contestTypeID);
        type.setDescription("type" + contestTypeID);
        type.setRequirePreviewFile(true);
        type.setRequirePreviewImage(true);

        List<ContestPayload> payloads = new ArrayList<ContestPayload>();
        ContestPayload payload1 = new ContestPayload();
        payload1.setName("Name of payload1");
        payload1.setValue("Value of payload1");
        payload1.setRequired(true);
        ContestPayload payload2 = new ContestPayload();
        payload2.setName("Name of payload2");
        payload2.setValue("Value of payload2");
        payload2.setRequired(true);
        payloads.add(payload1);
        payloads.add(payload2);

        type.setConfig(payloads);
        return type;
    }

    /**
     * <p>
     * Checks to see if object is null or not.
     * </p>
     *
     * @param object to be checked
     * @param name of the object
     * @throws IllegalArgumentWSException if object is null
     */
    private void checkNull(Object object, String name) {
        if (object == null) {
            throw new IllegalArgumentWSException(name + " should not be null.", name + " should not be null.");
        }
    }

    /**
     * <p>
     * Checks to see if id is less than 0.
     * </p>
     *
     * @param id to be checked
     * @param name of the id
     * @throws IllegalArgumentWSException if id is less than 0
     */
    private void checkId(long id, String name) {
        if (id < 0) {
            throw new IllegalArgumentWSException(name + " should not be less than 0.", name
                + " should not be less than 0.");
        }
    }
}
