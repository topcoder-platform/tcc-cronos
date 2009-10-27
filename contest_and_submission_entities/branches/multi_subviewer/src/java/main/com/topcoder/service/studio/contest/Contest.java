/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.topcoder.service.studio.submission.ContestResult;
import com.topcoder.service.studio.submission.MilestonePrize;
import com.topcoder.service.studio.submission.Prize;
import com.topcoder.service.studio.submission.Submission;

/**
 * <p>
 * Represents the entity class for db table <i>contest</i>.
 * </p>
 *
 * <p>
 * Added the TC Direct Project Name property for Cockpit Release Assembly for Receipts.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author tushak, cyberjag, TCSDEVELOPER
 * @version 1.2
 */
public class Contest implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -5708258423876562822L;

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
     * Represents the TC Direct project name.
     *
     * @since Cockpit Release Assembly for Receipts.
     */
    private String tcDirectProjectName;

    /**
     * Represents the contest status.
     */
    private ContestStatus status;

    /**
     * Represents the status id.
     */
    private Long statusId;

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
     * Represents the list of media.
     */
    private Set<Medium> media = new HashSet<Medium>();

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
     * Represents ContestRegistration list.
     */
    private Set<ContestRegistration> contestRegistrations = new HashSet<ContestRegistration>();

    /**
     * Represents whether the contest should be launched immediately after creation.
     */
    private Boolean launchImmediately;

    /**
     * Represents whether the contest has been deleted.
     */
    private Boolean deleted;

    /**
     * Represents the prizes.
     */
    private Set<Prize> prizes;

    /**
     * Represents the specifications.
     * @since 1.2
     */
    private ContestSpecifications specifications;

    /**
     * Represents whether the non winning submissions are purchased.
     * @since 1.2
     */
    private Boolean nonWinningSubmissionsPurchased = Boolean.FALSE;

    /**
     * Represents the multi round information.
     * @since 1.2
     */
    private ContestMultiRoundInformation multiRoundInformation;

    /**
     * Represents whether the contest is multi round.
     * @since 1.2
     */
    private Boolean multiRound = Boolean.FALSE;

    /**
     * Represents the milestone prize.
     * @since 1.2
     */
    private MilestonePrize milestonePrize;

    /**
     * Represents the general info.
     * @since 1.2
     */
    private ContestGeneralInfo generalInfo;

    /**
     * Represents the resources.
     * @since 1.2
     */
    private Set<ContestResource> resources = new HashSet<ContestResource>();

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
     * Returns the statusId.
     *
     * @return the statusId.
     */
    public Long getStatusId() {
        return statusId;
    }

    /**
     * Updates the statusId with the specified value.
     *
     * @param statusId
     *            the statusId to set.
     */
    public void setStatusId(Long statusId) {
        this.statusId = statusId;
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
        if (status != null) {
            statusId = status.getStatusId();
        }
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
     * Updates the media with the specified value.
     *
     * @param media
     *            the media to set.
     */
    public void setMedia(Set<Medium> media) {
        this.media = media;
    }

    /**
     * Returns the media.
     *
     * @return the media.
     */
    public Set<Medium> getMedia() {
        return media;
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
     * Sets contestRegistrations.
     *
     * @param contestRegistrations the contestRegistrations to set.
     */
    public void setContestRegistrations(Set<ContestRegistration> contestRegistrations) {
        this.contestRegistrations = contestRegistrations;
    }

    /**
     * Returns contestRegistrations.
     *
     * @return the contestRegistrations.
     */
    public Set<ContestRegistration> getContestRegistrations() {
        return contestRegistrations;
    }

    /**
     * Updates the LaunchImmediately with the specified value.
     *
     * @param launchImmediately
     *            the launchImmediately to set.
     */
    public void setLaunchImmediately(Boolean launchImmediately) {
        this.launchImmediately = launchImmediately == null ? false : launchImmediately.booleanValue();
    }

    /**
     * Returns the launchImmediately.
     *
     * @return the launchImmediately.
     */
    public Boolean isLaunchImmediately() {
        return launchImmediately;
    }

    /**
     * Updates the deleted with the specified value.
     *
     * @param deleted
     *            the deleted to set.
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted == null ? false : deleted.booleanValue();
    }

    /**
     * Returns the deleted.
     *
     * @return the deleted.
     */
    public Boolean isDeleted() {
        return deleted;
    }

        /**
     * Updates the prizes with the specified value.
     *
     * @param prizes
     *            the prizes to set.
     */
    public void setPrizes(Set<Prize> prizes) {
        this.prizes = prizes;
    }

    /**
     * Returns the prizes.
     *
     * @return the prizes.
     */
    public Set<Prize> getPrizes() {
        return prizes;
    }

    /**
     * <p>
     * Gets the TC Direct project name.
     * </p>
     *
     * @return the TC Direct project name.
     * @since Cockpit Release Assembly for Receipts.
     */
    public String getTcDirectProjectName() {
        return tcDirectProjectName;
    }

    /**
     * <p>
     * Sets the TC Direct project name.
     * </p>
     *
     * @param tcDirectProjectName the TC Direct project name.
     * @since Cockpit Release Assembly for Receipts.
     */
    public void setTcDirectProjectName(String tcDirectProjectName) {
        this.tcDirectProjectName = tcDirectProjectName;
    }

    /**
     * <p>
     * Gets the specifications.
     * </p>
     * @return the specifications.
     * @since 1.2
     */
    public ContestSpecifications getSpecifications() {
        return specifications;
    }

    /**
     * <p>
     * Sets the specifications.
     * </p>
     * @param specifications the specifications.
     * @since 1.2
     */
    public void setSpecifications(ContestSpecifications specifications) {
        this.specifications = specifications;
    }

    /**
     * <p>
     * Gets whether the non winning submissions are purchased.
     * </p>
     * @return whether the non winning submissions are purchased.
     * @since 1.2
     */
    public Boolean isNonWinningSubmissionsPurchased() {
        return nonWinningSubmissionsPurchased;
    }

    /**
     * <p>
     * Sets whether the non winning submissions are purchased.
     * </p>
     * @param nonWinningSubmissionsPurchased whether the non winning submissions are purchased.
     * @since 1.2
     */
    public void setNonWinningSubmissionsPurchased(Boolean nonWinningSubmissionsPurchased) {
        this.nonWinningSubmissionsPurchased = nonWinningSubmissionsPurchased == null ? Boolean.FALSE
                : nonWinningSubmissionsPurchased;
    }

    /**
     * <p>
     * Gets the multi round information.
     * </p>
     * @return the multi round information.
     * @since 1.2
     */
    public ContestMultiRoundInformation getMultiRoundInformation() {
        return multiRoundInformation;
    }

    /**
     * <p>
     * Sets the multi round information.
     * </p>
     * @param multiRoundInformation the multi round information.
     * @since 1.2
     */
    public void setMultiRoundInformation(ContestMultiRoundInformation multiRoundInformation) {
        this.multiRoundInformation = multiRoundInformation;
    }

    /**
     * <p>
     * Gets whether the contest is multi round.
     * </p>
     * @return whether the contest is multi round.
     * @since 1.2
     */
    public Boolean isMultiRound() {
        return multiRound;
    }

    /**
     * <p>
     * Sets whether the contest is multi round.
     * </p>
     * @param multiRound whether the contest is multi round.
     * @since 1.2
     */
    public void setMultiRound(Boolean multiRound) {
        this.multiRound = multiRound;
    }

    /**
     * <p>
     * Gets the milestone prize.
     * </p>
     * @return the milestone prize.
     * @since 1.2
     */
    public MilestonePrize getMilestonePrize() {
        return milestonePrize;
    }

    /**
     * <p>
     * Sets the milestone prize.
     * </p>
     * @param milestonePrize the milestone prize.
     * @since 1.2
     */
    public void setMilestonePrize(MilestonePrize milestonePrize) {
        this.milestonePrize = milestonePrize;
    }

    /**
     * <p>
     * Gets the general info.
     * </p>
     * @return the general info.
     * @since 1.2
     */
    public ContestGeneralInfo getGeneralInfo() {
        return generalInfo;
    }

    /**
     * <p>
     * Sets the general info.
     * </p>
     * @param generalInfo the general info.
     * @since 1.2
     */
    public void setGeneralInfo(ContestGeneralInfo generalInfo) {
        this.generalInfo = generalInfo;
    }

    /**
     * <p>
     * Gets the resources.
     * </p>
     * @return the resources.
     * @since 1.2
     */
    public Set<ContestResource> getResources() {
        return resources;
    }

    /**
     * <p>
     * Sets the resources.
     * </p>
     * @param resources the resources.
     * @since 1.2
     */
    public void setResources(Set<ContestResource> resources) {
        this.resources = resources;
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
