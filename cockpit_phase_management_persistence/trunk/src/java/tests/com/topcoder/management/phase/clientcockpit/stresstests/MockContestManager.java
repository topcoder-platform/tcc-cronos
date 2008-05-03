/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit.stresstests;

import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.service.studio.contest.ContestProperty;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.ContestTypeConfig;
import com.topcoder.service.studio.contest.Document;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.submission.Prize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Mock implementation of ContestManager.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockContestManager implements ContestManager {

    /**
     * This field represents the updatedContestId in the updateContest method.
     */
    private long updatedContestId = -1;

    /**
     * This field represents the updatedStatusId in the updateContest method.
     */
    private long updatedStatusId = -1;

    /**
     * Gets the updated contest's id in the updateContest method.
     *
     * @return the updated contest's id
     */
    public long getUpdatedContestId() {
        return updatedContestId;
    }

    /**
     * Gets the updated status id in the updateContest method.
     *
     * @return the updated status id
     */
    public long getUpdatedStatusId() {
        return updatedStatusId;
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @return not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public Contest createContest(Contest arg0) throws ContestManagementException {
        return null;
    }

    /**
     * Just returns a mock Contest instance.
     *
     * @param contestId
     *            the id of the contest to get
     * @return the contest with the specified id
     * @throws ContestManagementException
     *             if any error occurs
     */
    public Contest getContest(long contestId) throws ContestManagementException {

        if (contestId < 0) {
            return null;
        }

        Contest contest = new Contest();
        contest.setContestId(contestId);
        contest.setStartDate(new Date());
        contest.setContestChannel(new ContestChannel());
        contest.setContestType(new ContestType());
        contest.setCreatedUser(new Long(0));
        contest.setEndDate(new Date(new Date().getTime() + 3600000));
        contest.setEventId(new Long(0));
        contest.setForumId(new Long(1));
        contest.setName("contest");
        contest.setProjectId(new Long(0));
        contest.setTcDirectProjectId(new Long(0));
        contest.setWinnerAnnoucementDeadline(new Date());

        Set<ContestConfig> configs = new HashSet<ContestConfig>();
        ContestConfig config = new ContestConfig();
        config.setValue("config1");

        ContestProperty property = new ContestProperty();
        property.setDescription("desc");
        property.setPropertyId(1);
        config.setProperty(property);
        configs.add(config);

        contest.setConfig(configs);

        if (contestId == 0) {
            ContestStatus contestStatus = new ContestStatus();
            contestStatus.setName("unknown");
            contest.setStatus(contestStatus);
        } else if (contestId == 1) {
            ContestStatus contestStatus = new ContestStatus();
            contestStatus.setName("No Winner Chosen");
            contest.setStatus(contestStatus);
        } else {
            ContestStatus contestStatus = new ContestStatus();
            contestStatus.setName("Draft");
            contest.setStatus(contestStatus);

            contestStatus.setDescription("Draft");
            contestStatus.setStatuses(new ArrayList<ContestStatus>());
            contestStatus.setContestStatusId(new Long(0));
        }

        return contest;
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @return not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public List<Contest> getContestsForProject(long arg0) throws ContestManagementException {
        return null;
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public void updateContest(Contest arg0) throws ContestManagementException {
    }

    /**
     * Just record the updated contestid and contestStatusId.
     *
     * @param contestId
     *            the id of contest to update
     * @param contestStatusId
     *            the id of the contestStatus to update
     * @throws ContestManagementException
     *             not thrown.
     */
    public void updateContestStatus(long contestId, long contestStatusId) throws ContestManagementException {
        updatedContestId = contestId;
        updatedStatusId = contestStatusId;
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @return not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public long getClientForContest(long arg0) throws ContestManagementException {
        return 0;
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @return not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public long getClientForProject(long arg0) throws ContestManagementException {
        return 0;
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @return not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public ContestStatus addContestStatus(ContestStatus arg0) throws ContestManagementException {
        return null;
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public void updateContestStatus(ContestStatus arg0) throws ContestManagementException {
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @return not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public boolean removeContestStatus(long arg0) throws ContestManagementException {
        return false;
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @return not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public ContestStatus getContestStatus(long arg0) throws ContestManagementException {
        return null;
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @return not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public Document addDocument(Document arg0) throws ContestManagementException {
        return null;
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public void updateDocument(Document arg0) throws ContestManagementException {
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @return not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public Document getDocument(long arg0) throws ContestManagementException {
        return null;
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @return not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public boolean removeDocument(long arg0) throws ContestManagementException {
        return false;
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @param arg1
     *            not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public void addDocumentToContest(long arg0, long arg1) throws ContestManagementException {
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @param arg1
     *            not used
     * @return not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public boolean removeDocumentFromContest(long arg0, long arg1) throws ContestManagementException {
        return false;
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @return not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public ContestChannel addContestChannel(ContestChannel arg0) throws ContestManagementException {
        return null;
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public void updateContestChannel(ContestChannel arg0) throws ContestManagementException {
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @return not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public boolean removeContestChannel(long arg0) throws ContestManagementException {
        return false;
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @return not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public ContestChannel getContestChannel(long arg0) throws ContestManagementException {
        return null;
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @return not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public ContestConfig addConfig(ContestConfig arg0) throws ContestManagementException {
        return null;
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public void updateConfig(ContestConfig arg0) throws ContestManagementException {
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @return not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public ContestConfig getConfig(long arg0) throws ContestManagementException {
        return null;
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @param arg1
     *            not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public void saveDocumentContent(long arg0, byte[] arg1) throws ContestManagementException {
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @return not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public byte[] getDocumentContent(long arg0) throws ContestManagementException {
        return null;
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @return not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public boolean existDocumentContent(long arg0) throws ContestManagementException {
        return false;
    }

    /**
     * Returns an array of 10 contest statuses.
     *
     * @return an array of predefined 10 contest statuses
     * @throws ContestManagementException
     *             mock exception
     */
    public List<ContestStatus> getAllContestStatuses() throws ContestManagementException {

        ContestStatus[] statuses = new ContestStatus[10];
        statuses[0] = new ContestStatus();
        statuses[0].setContestStatusId(new Long(0));
        statuses[0].setName("Draft");
        statuses[0].setDescription("A status");
        statuses[0].setStatuses(new ArrayList<ContestStatus>());

        statuses[1] = new ContestStatus();
        statuses[1].setContestStatusId(new Long(1));
        statuses[1].setName("Scheduled");
        statuses[1].setDescription("A status");
        statuses[1].setStatuses(new ArrayList<ContestStatus>());

        statuses[2] = new ContestStatus();
        statuses[2].setContestStatusId(new Long(2));
        statuses[2].setName("Active");
        statuses[2].setDescription("A status");
        statuses[2].setStatuses(new ArrayList<ContestStatus>());

        statuses[3] = new ContestStatus();
        statuses[3].setContestStatusId(new Long(3));
        statuses[3].setName("Action Required");
        statuses[3].setDescription("A status");
        statuses[3].setStatuses(new ArrayList<ContestStatus>());

        statuses[4] = new ContestStatus();
        statuses[4].setContestStatusId(new Long(4));
        statuses[4].setName("In Danger");
        statuses[4].setDescription("A status");
        statuses[4].setStatuses(new ArrayList<ContestStatus>());

        statuses[5] = new ContestStatus();
        statuses[5].setContestStatusId(new Long(5));
        statuses[5].setName("Insufficient Submissions - ReRun Possible");
        statuses[5].setDescription("A status");
        statuses[5].setStatuses(new ArrayList<ContestStatus>());

        statuses[6] = new ContestStatus();
        statuses[6].setContestStatusId(new Long(6));
        statuses[6].setName("Extended");
        statuses[6].setDescription("A status");
        statuses[6].setStatuses(new ArrayList<ContestStatus>());

        statuses[7] = new ContestStatus();
        statuses[7].setContestStatusId(new Long(7));
        statuses[7].setName("Repost");
        statuses[7].setDescription("A status");
        statuses[7].setStatuses(new ArrayList<ContestStatus>());

        statuses[8] = new ContestStatus();
        statuses[8].setContestStatusId(new Long(8));
        statuses[8].setName("Insufficient Submissions");
        statuses[8].setDescription("A status");
        statuses[8].setStatuses(new ArrayList<ContestStatus>());

        statuses[9] = new ContestStatus();
        statuses[9].setContestStatusId(new Long(9));
        statuses[9].setName("No Winner Chosen");
        statuses[9].setDescription("A status");
        statuses[9].setStatuses(new ArrayList<ContestStatus>());

        return Arrays.asList(statuses);
    }

    /**
     * Mock method, not used.
     *
     * @return not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public List<ContestChannel> getAllContestChannels() throws ContestManagementException {
        return null;
    }

    /**
     * Mock method, not used.
     *
     * @return not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public List<StudioFileType> getAllStudioFileTypes() throws ContestManagementException {
        return null;
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @return not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public ContestTypeConfig addContestTypeConfig(ContestTypeConfig arg0) throws ContestManagementException {
        return null;
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public void updateContestTypeConfig(ContestTypeConfig arg0) throws ContestManagementException {
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @return not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public ContestTypeConfig getContestTypeConfig(long arg0) throws ContestManagementException {
        return null;
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @param arg1
     *            not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public void addPrizeToContest(long arg0, long arg1) throws ContestManagementException {
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @param arg1
     *            not used
     * @return not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public boolean removePrizeFromContest(long arg0, long arg1) throws ContestManagementException {
        return false;
    }

    /**
     * Mock method, not used.
     *
     * @param arg0
     *            not used
     * @return not used
     * @throws ContestManagementException
     *             not thrown.
     */
    public List<Prize> getContestPrizes(long arg0) throws ContestManagementException {
        return null;
    }
}
