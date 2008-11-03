/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit.accuracytests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.service.studio.contest.ContestPayment;
import com.topcoder.service.studio.contest.ContestProperty;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.ContestTypeConfig;
import com.topcoder.service.studio.contest.Document;
import com.topcoder.service.studio.contest.DocumentType;
import com.topcoder.service.studio.contest.Medium;
import com.topcoder.service.studio.contest.MimeType;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.contest.ContestConfig.Identifier;
import com.topcoder.service.studio.submission.ContestResult;
import com.topcoder.service.studio.submission.PaymentStatus;
import com.topcoder.service.studio.submission.Prize;
import com.topcoder.service.studio.submission.PrizeType;
import com.topcoder.service.studio.submission.Submission;

/**
 * The mock implementation of ContestManager for testing use.
 *
 * @author LostHunter
 * @version 1.0
 *
 */
public class MockContestManager implements ContestManager {

    /**
     * The map to hold the contest.
     */
    private final Map<Long, Contest> contests = new HashMap<Long, Contest>();

    /**
     * The list to hold all the statuses.
     */
    private final List<ContestStatus> statuses = new ArrayList<ContestStatus>();

    /**
     * The Default constructor. Add some entries into the manager.
     *
     */
    public MockContestManager() {

        initialzieContestStatus();

        Contest contest = new Contest();

        Long id = new Long(1);
        contests.put(id, contest);

        // set the fields
        contest.setContestChannel(new ContestChannel());
        contest.setContestId(id);
        contest.setContestType(new ContestType());
        contest.setCreatedUser(new Long(1));
        contest.setDocuments(Collections.singleton(new Document()));
        contest.setEndDate(new Date());
        contest.setEventId(new Long(1));
        contest.setFileTypes(Collections.singleton(new StudioFileType()));
        contest.setForumId(new Long(2));
        contest.setName("name");
        contest.setProjectId(new Long(1));
        contest.setResults(Collections.singleton(new ContestResult()));
        contest.setStartDate(new Date());
        contest.setSubmissions(Collections.singleton(new Submission()));
        contest.setTcDirectProjectId(new Long(1));
        contest.setWinnerAnnoucementDeadline(new Date());

        // set contest config
        initializeConfig(contest);

        // set the status
        contest.setStatus(statuses.get(0));
    }

    public void updateContestStatus(long contestId, long newStatusId) throws ContestManagementException {
        Contest contest = contests.get(new Long(contestId));
        List<ContestStatus> statuses = this.getAllContestStatuses();
        for (ContestStatus status: statuses) {
            if (status.getContestStatusId().longValue() == newStatusId) {
                contest.setStatus(status);
                break;
            }
        }
    }


    public Contest getContest(long contestId) throws ContestManagementException {
        return contests.get(new Long(contestId));
    }

    public ContestConfig addConfig(ContestConfig contestConfig) throws ContestManagementException {
        return null;
    }

    public ContestChannel addContestChannel(ContestChannel contestChannel) throws ContestManagementException {
        return null;
    }

    public ContestStatus addContestStatus(ContestStatus contestStatus) throws ContestManagementException {
        return null;
    }

    public ContestTypeConfig addContestTypeConfig(ContestTypeConfig contestTypeConfig)
        throws ContestManagementException {
        return null;
    }

    public Document addDocument(Document document) throws ContestManagementException {
        return null;
    }

    public void addDocumentToContest(long documentId, long contestId) throws ContestManagementException {

    }

    public void addPrizeToContest(long contestId, long prizeId) throws ContestManagementException {
    }

    public Contest createContest(Contest contest) throws ContestManagementException {
        return null;
    }

    public boolean existDocumentContent(long documentId) throws ContestManagementException {
        return false;
    }

    public List<ContestChannel> getAllContestChannels() throws ContestManagementException {
        return null;
    }

    public List<ContestStatus> getAllContestStatuses() throws ContestManagementException {
        return statuses;
    }

    public List<StudioFileType> getAllStudioFileTypes() throws ContestManagementException {
        return null;
    }

    public long getClientForContest(long contestId) throws ContestManagementException {
        return 0;
    }

    public long getClientForProject(long projectId) throws ContestManagementException {
        return 0;
    }

    public ContestConfig getConfig(long contestConfigId) throws ContestManagementException {
        return null;
    }

    public ContestChannel getContestChannel(long contestChannelId) throws ContestManagementException {
        return null;
    }

    public List<Prize> getContestPrizes(long contestId) throws ContestManagementException {
        return null;
    }

    public ContestStatus getContestStatus(long contestStatusId) throws ContestManagementException {
        return null;
    }

    public ContestTypeConfig getContestTypeConfig(long contestTypeConfigId) throws ContestManagementException {
        return null;
    }

    public List<Contest> getContestsForProject(long tcDirectProjectId) throws ContestManagementException {
        return null;
    }

    public Document getDocument(long documentId) throws ContestManagementException {
        return null;
    }

    public byte[] getDocumentContent(long documentId) throws ContestManagementException {
        return null;
    }

    public boolean removeContestChannel(long contestChannelId) throws ContestManagementException {
        return false;
    }

    public boolean removeContestStatus(long contestStatusId) throws ContestManagementException {
        return false;
    }

    public boolean removeDocument(long documentId) throws ContestManagementException {
        return false;
    }

    public boolean removeDocumentFromContest(long documentId, long contestId) throws ContestManagementException {
        return false;
    }

    public boolean removePrizeFromContest(long contestId, long prizeId) throws ContestManagementException {
        return false;
    }

    public void saveDocumentContent(long documentId, byte[] documentContent) throws ContestManagementException {
    }

    public void updateConfig(ContestConfig contestConfig) throws ContestManagementException {
    }

    public void updateContest(Contest contest) throws ContestManagementException {
    }

    public void updateContestChannel(ContestChannel contestChannel) throws ContestManagementException {

    }

    public void updateContestStatus(ContestStatus contestStatus) throws ContestManagementException {
    }

    public void updateContestTypeConfig(ContestTypeConfig contestTypeConfig) throws ContestManagementException {
    }

    public void updateDocument(Document document) throws ContestManagementException {
    }

    /**
     * Method to initialize the contest statuses.
     *
     */
    private void initialzieContestStatus() {
        statuses.clear();
        String names[] = {"Draft", "Scheduled", "Active", "Action Required", "In Danger",
            "Insufficient Submissions - ReRun Possible", "Extended", "Repost", "Insufficient Submissions",
            "No Winner Chosen"};

        ContestStatus oldStatus = null;
        for (int i = 0; i < names.length; ++i) {
            ContestStatus status = new ContestStatus();
            status.setContestStatusId(new Long(i));
            status.setName(names[i]);
            status.setDescription("des" + names[i]);
            if (oldStatus != null) {
                status.setStatuses(Collections.singletonList(oldStatus));
            } else {
                status.setStatuses(new ArrayList<ContestStatus>());
            }
            oldStatus = status;

            // add the status
            statuses.add(status);
        }
    }

    /**
     * Method to initialize the config for the contest.
     *
     */
    private void initializeConfig(Contest contest) {
        Set<ContestConfig> configs = new HashSet<ContestConfig>();
        ContestConfig config = new ContestConfig();
        config.setValue("testConfig");
        ContestProperty property = new ContestProperty();
        property.setPropertyId(1);
        property.setDescription("description1");
        Identifier id = new Identifier();
        id.setContest(contest);
        config.setId(id);
        config.getId().setProperty(property);
        configs.add(config);

        config = new ContestConfig();
        config.setValue("testConfig");
        property = new ContestProperty();
        property.setPropertyId(2);
        property.setDescription("description2");
        id = new Identifier();
        id.setContest(contest);
        config.setId(id);
        config.getId().setProperty(property);
        configs.add(config);
        contest.setConfig(configs);
    }

    public ContestPayment createContestPayment(ContestPayment contestPayment) throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public Prize createPrize(Prize prize) throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public void editContestPayment(ContestPayment contestPayment) throws ContestManagementException {
        // TODO Auto-generated method stub
        
    }

    public List<ContestProperty> getAllContestProperties() throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<ContestType> getAllContestTypes() throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Contest> getAllContests() throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<DocumentType> getAllDocumentTypes() throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Medium> getAllMedia() throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<MimeType> getAllMimeTypes() throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<PaymentStatus> getAllPaymentStatuses() throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<PrizeType> getAllPrizeTypes() throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public ContestPayment getContestPayment(long contestPaymentId) throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public ContestProperty getContestProperty(long contestPropertyId) throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Contest> getContestsForUser(long createdUser) throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public DocumentType getDocumentType(long documentTypeId) throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public MimeType getMimeType(long mimeTypeId) throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public PaymentStatus getPaymentStatus(long paymentStatusId) throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public PrizeType getPrizeType(long prizeTypeId) throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean removeContestPayment(long contestPaymentId) throws ContestManagementException {
        // TODO Auto-generated method stub
        return false;
    }

    public List<Contest> searchContests(Filter filter) throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }
}
