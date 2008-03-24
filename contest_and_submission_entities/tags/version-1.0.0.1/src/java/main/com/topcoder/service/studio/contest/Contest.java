/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.topcoder.service.studio.submission.ContestResult;
import com.topcoder.service.studio.submission.Submission;

/**
 * <p>
 * Represents the entity class for db table <i>contest</i>.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author tushak, cyberjag
 * @version 1.0
 */
public class Contest implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -6991488651979864246L;

    /**
     * Represents the contest id.
     */
    private Long contestId;

    /**
     * Represents the contest name.
     */
    private String name;

    /**
     * Represents the contest channel.
     */
    private ContestChannel contestChannel;

    /**
     * Represents the project id.
     */
    private Long projectId;

    /**
     * Represents the TC Direct project id.
     */
    private Long tcDirectProjectId;

    /**
     * Represents the contest status.
     */
    private ContestStatus status;

    /**
     * Represents the forum id.
     */
    private Long forumId;

    /**
     * Represents the event id.
     */
    private Long eventId;

    /**
     * Represents the set of submissions.
     */
    private Set<Submission> submissions = new HashSet<Submission>();

    /**
     * Represents the list of file types.
     */
    private Set<StudioFileType> fileTypes = new HashSet<StudioFileType>();

    /**
     * Represents the list of contest results.
     */
    private Set<ContestResult> results = new TreeSet<ContestResult>();

    /**
     * Represents the list of documents.
     */
    private Set<Document> documents = new HashSet<Document>();

    /**
     * Represents the list of contest config.
     */
    private Set<ContestConfig> config = new HashSet<ContestConfig>();

    /**
     * Represents the contest type.
     */
    private ContestType contestType;

    /**
     * Represents the start date.
     */
    private Date startDate;

    /**
     * Represents the end date.
     */
    private Date endDate;

    /**
     * Represents the winner announcement deadline.
     */
    private Date winnerAnnoucementDeadline;

    /**
     * Represents the created user id.
     */
    private Long createdUser;

    /**
     * Default constructor.
     */
    public Contest() {
        // empty
    }

    /**
     * Returns the contestId.
     *
     * @return the contestId.
     */
    public Long getContestId() {
        return contestId;
    }

    /**
     * Updates the contestId with the specified value.
     *
     * @param contestId
     *            the contestId to set.
     */
    public void setContestId(Long contestId) {
        this.contestId = contestId;
    }

    /**
     * Returns the name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the name with the specified value.
     *
     * @param name
     *            the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the contestChannel.
     *
     * @return the contestChannel.
     */
    public ContestChannel getContestChannel() {
        return contestChannel;
    }

    /**
     * Updates the contestChannel with the specified value.
     *
     * @param contestChannel
     *            the contestChannel to set.
     */
    public void setContestChannel(ContestChannel contestChannel) {
        this.contestChannel = contestChannel;
    }

    /**
     * Returns the projectId.
     *
     * @return the projectId.
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * Updates the projectId with the specified value.
     *
     * @param projectId
     *            the projectId to set.
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * Returns the tcDirectProjectId.
     *
     * @return the tcDirectProjectId.
     */
    public Long getTcDirectProjectId() {
        return tcDirectProjectId;
    }

    /**
     * Updates the tcDirectProjectId with the specified value.
     *
     * @param tcDirectProjectId
     *            the tcDirectProjectId to set.
     */
    public void setTcDirectProjectId(Long tcDirectProjectId) {
        this.tcDirectProjectId = tcDirectProjectId;
    }

    /**
     * Returns the status.
     *
     * @return the status.
     */
    public ContestStatus getStatus() {
        return status;
    }

    /**
     * Updates the status with the specified value.
     *
     * @param status
     *            the status to set.
     */
    public void setStatus(ContestStatus status) {
        this.status = status;
    }

    /**
     * Returns the forumId.
     *
     * @return the forumId.
     */
    public Long getForumId() {
        return forumId;
    }

    /**
     * Updates the forumId with the specified value.
     *
     * @param forumId
     *            the forumId to set.
     */
    public void setForumId(Long forumId) {
        this.forumId = forumId;
    }

    /**
     * Returns the eventId.
     *
     * @return the eventId.
     */
    public Long getEventId() {
        return eventId;
    }

    /**
     * Updates the eventId with the specified value.
     *
     * @param eventId
     *            the eventId to set.
     */
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    /**
     * Returns the submissions.
     *
     * @return the submissions.
     */
    public Set<Submission> getSubmissions() {
        return submissions;
    }

    /**
     * Updates the submissions with the specified value.
     *
     * @param submissions
     *            the submissions to set.
     */
    public void setSubmissions(Set<Submission> submissions) {
        this.submissions = submissions;
    }

    /**
     * Returns the fileTypes.
     *
     * @return the fileTypes.
     */
    public Set<StudioFileType> getFileTypes() {
        return fileTypes;
    }

    /**
     * Updates the fileTypes with the specified value.
     *
     * @param fileTypes
     *            the fileTypes to set.
     */
    public void setFileTypes(Set<StudioFileType> fileTypes) {
        this.fileTypes = fileTypes;
    }

    /**
     * Returns the results.
     *
     * @return the results.
     */
    public Set<ContestResult> getResults() {
        return results;
    }

    /**
     * Updates the results with the specified value.
     *
     * @param results
     *            the results to set.
     */
    public void setResults(Set<ContestResult> results) {
        this.results = results;
    }

    /**
     * Returns the documents.
     *
     * @return the documents.
     */
    public Set<Document> getDocuments() {
        return documents;
    }

    /**
     * Updates the documents with the specified value.
     *
     * @param documents
     *            the documents to set.
     */
    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    /**
     * Returns the config.
     *
     * @return the config.
     */
    public Set<ContestConfig> getConfig() {
        return config;
    }

    /**
     * Updates the config with the specified value.
     *
     * @param config
     *            the config to set.
     */
    public void setConfig(Set<ContestConfig> config) {
        this.config = config;
    }

    /**
     * Returns the contestType.
     *
     * @return the contestType.
     */
    public ContestType getContestType() {
        return contestType;
    }

    /**
     * Updates the contestType with the specified value.
     *
     * @param contestType
     *            the contestType to set.
     */
    public void setContestType(ContestType contestType) {
        this.contestType = contestType;
    }

    /**
     * Returns the startDate.
     *
     * @return the startDate.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Updates the startDate with the specified value.
     *
     * @param startDate
     *            the startDate to set.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Returns the endDate.
     *
     * @return the endDate.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Updates the endDate with the specified value.
     *
     * @param endDate
     *            the endDate to set.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Returns the winnerAnnoucementDeadline.
     *
     * @return the winnerAnnoucementDeadline.
     */
    public Date getWinnerAnnoucementDeadline() {
        return winnerAnnoucementDeadline;
    }

    /**
     * Updates the winnerAnnoucementDeadline with the specified value.
     *
     * @param winnerAnnoucementDeadline
     *            the winnerAnnoucementDeadline to set.
     */
    public void setWinnerAnnoucementDeadline(Date winnerAnnoucementDeadline) {
        this.winnerAnnoucementDeadline = winnerAnnoucementDeadline;
    }

    /**
     * Returns the createdUser.
     *
     * @return the createdUser.
     */
    public Long getCreatedUser() {
        return createdUser;
    }

    /**
     * Updates the createdUser with the specified value.
     *
     * @param createdUser
     *            the createdUser to set.
     */
    public void setCreatedUser(Long createdUser) {
        this.createdUser = createdUser;
    }

    /**
     * Compares this object with the passed object for equality. Only the id will be compared.
     *
     * @param obj
     *            the {@code Object} to compare to this one
     * @return true if this object is equal to the other, {@code false} if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Contest) {
            return getContestId() == ((Contest) obj).getContestId();
        }
        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's
     * {@link #equals(Object)}} method.
     *
     * @return a hash code for this {@code Contest}
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(contestId);
    }
}
