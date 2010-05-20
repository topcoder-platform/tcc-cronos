package com.topcoder.service.studio.accuracytests;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.studio.PaymentType;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChangeHistory;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestGeneralInfo;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.ContestManagerLocal;
import com.topcoder.service.studio.contest.ContestMultiRoundInformation;
import com.topcoder.service.studio.contest.ContestPayment;
import com.topcoder.service.studio.contest.ContestProperty;
import com.topcoder.service.studio.contest.ContestSpecifications;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestStatusTransitionException;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.ContestTypeConfig;
import com.topcoder.service.studio.contest.Document;
import com.topcoder.service.studio.contest.DocumentType;
import com.topcoder.service.studio.contest.EntityAlreadyExistsException;
import com.topcoder.service.studio.contest.EntityNotFoundException;
import com.topcoder.service.studio.contest.Medium;
import com.topcoder.service.studio.contest.MimeType;
import com.topcoder.service.studio.contest.SimpleContestData;
import com.topcoder.service.studio.contest.SimpleProjectContestData;
import com.topcoder.service.studio.contest.SimpleProjectPermissionData;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.contest.User;
import com.topcoder.service.studio.contest.ContestConfig.Identifier;
import com.topcoder.service.studio.submission.ContestResult;
import com.topcoder.service.studio.submission.MilestonePrize;
import com.topcoder.service.studio.submission.PaymentStatus;
import com.topcoder.service.studio.submission.Prize;
import com.topcoder.service.studio.submission.PrizeType;

public class ContestManagerImpl implements ContestManagerLocal {
    public static Map<Long, Contest> contests;

    public static Map<Long, Document> documents;

    public static Map<Long, Set<Long>> documentsForContest;

    public static Map<Long, byte[]> documentsContent;

    public static boolean errorRequested;

    public static String lastCall;

    public ContestManagerImpl() {
        contests = new HashMap<Long, Contest>();
        documents = new HashMap<Long, Document>();
        documentsForContest = new HashMap<Long, Set<Long>>();
        documentsContent = new HashMap<Long, byte[]>();
        lastCall = null;
        errorRequested = false;
    }

    private void checkErrorRequest() throws ContestManagementException {
        if (errorRequested) {
            errorRequested = false;
            throw new ContestManagementException("exception was requested");
        }
    }

    /**
     * Create new contest, and return the created contest. #Paramcontest - the contest to
     * create. #Return - the created contest. #exception throw IllegalArgumentException if
     * the arg is null. throw EntityAlreadyExistsException if the entity already exists in
     * the persistence. throw ContestManagementException if any other error occurs.
     */
    public Contest createContest(Contest contest)
            throws ContestManagementException {
        contest.setStatus(new ContestStatus());
        contests.put(contest.getContestId(), contest);
        return contest;
    }

    /**
     * Get contest by id, and return the retrieved contest. If the contest doesn't exist,
     * null is returned. #Param contestId - the contest id. #Return - the retrieved
     * contest, or null if it doesn't exist. #exception throw ContestManagementException
     * if any error occurs when getting contest.
     */
    public Contest getContest(long contestId) throws ContestManagementException {
        checkErrorRequest();
        if (1L == contestId) {
            Contest c = new Contest();
            return c;
        }
        Contest c = contests.get(new Long(contestId));
        if (c == null) {
            return c;
        }

        c.setDocuments(new HashSet<Document>());
        if (documentsForContest.get(contestId) == null) {
            documentsForContest.put(contestId, new HashSet<Long>());
        }
        for (Long docId : documentsForContest.get(contestId)) {
            c.getDocuments().add(documents.get(docId));
        }

        System.err.println(c.getName());
        return c;
    }

    /**
     * Get projects's contests by the project id. A list of contests associated with the
     * given tcDirectProjectId should be returned. If there is no such contests, an empty
     * list should be returned. #Param tcDirectProjectId - the project id. #Return - a
     * list of associated contests. #exception throw ContestManagementException if any
     * error occurs when getting contests.
     */
    public List<Contest> getContestsForProject(long tcDirectProjectId)
            throws ContestManagementException {
        List<Contest> result = new ArrayList<Contest>();
        for (Contest contest : contests.values()) {
            if (contest.getTcDirectProjectId() == tcDirectProjectId) {
                result.add(contest);
            }
        }
        return result;
    }

    /**
     * Update contest data. Note that all data can be updated only if contest is not
     * active. If contest is active it is possible to increase prize amount and duration.
     * #Param contest - the contest to update. #exception throw IllegalArgumentException
     * if the argument is null. throw EntityNotFoundException if the contest doesn't exist
     * in persistence. throw ContestManagementException if any error occurs when updating
     * contest.
     * 
     * @param contest
     */
    public void updateContest(Contest contest)
            throws ContestManagementException {
        contests.clear();
        contests.put(contest.getContestId(), contest);
    }

    /**
     * Update contest status to the given value. #Param contestId - the contest id.
     * newStatusId - the new status id. #exception throw EntityNotFoundException if there
     * is no corresponding Contest or ContestStatus in persistence. throw
     * ContestStatusTransitionException if it's not allowed to update the contest to the
     * given status. throw ContestManagementException if any error occurs when updating
     * contest's status.
     * 
     * @param contestId
     * @param newStatusId
     */
    public void updateContestStatus(long contestId, long newStatusId)
            throws ContestManagementException {
        if (newStatusId % 2 == 0) {
            throw new EntityNotFoundException("No even stutus ids.");
        }
        if (contests.get(contestId) == null) {
            throw new EntityNotFoundException("Contest not found.");
        }
        if (newStatusId % 3 == 0) {
            throw new ContestStatusTransitionException("Can't set such status.");
        }
        lastCall = "updateContestStatus(" + contestId + ", " + newStatusId
                + ")";
    }

    /**
     * Get client for contest, the client id is returned. #Param contestId - the contest
     * id. #Return - the client id. #exception throw EntityNotFoundException if there is
     * no corresponding contest (or project) in persistence. throw
     * ContestManagementException if any error occurs when retrieving the client id.
     * 
     * @param contestId
     * @return
     */
    public long getClientForContest(long contestId)
            throws ContestManagementException {
        checkErrorRequest();
        if (contestId == 101) {
            throw new EntityNotFoundException("");
        }
        if (contestId == 102) {
            throw new ContestManagementException("");
        }
        return 33;
    }

    /**
     * Get client for project, and return the retrieved client id. #Param projectId - the
     * project id. #Return - the client id. #exception throw EntityNotFoundException if
     * there is no corresponding project in persistence. throw ContestManagementException
     * if any error occurs when retrieving the client id.
     */
    public long getClientForProject(long projectId)
            throws ContestManagementException {
        checkErrorRequest();
        if (projectId == 101) {
            throw new EntityNotFoundException("");
        }
        if (projectId == 102) {
            throw new ContestManagementException("");
        }
        return 33;
    }

    /**
     * Add contest status, and return the added contest status. #Param contestStatus - the
     * contest status to add. #return - the added contest status. #exception throw
     * IllegalArgumentException if the arg is null. throw EntityAlreadyExistsException if
     * the entity already exists in the persistence. throw ContestManagementException if
     * any other error occurs.
     * 
     * @param contestStatus
     * @return
     */
    public ContestStatus addContestStatus(ContestStatus contestStatus)
            throws ContestManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    /**
     * Update contest status. #Param contestStatus - the contest status to update.
     * #exception throw IllegalArgumentException if the arg is null. throw
     * EntityNotFoundException if the contestStatus doesn't exist in persistence. throw
     * ContestManagementException if any other error occurs.
     * 
     * @param contestStatus
     */
    public void updateContestStatus(ContestStatus contestStatus)
            throws ContestManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    /**
     * Remove contest status, return true if the contest status exists and removed
     * successfully, return false if it doesn't exist. #Param contestStatusId - the
     * contest status id. #Return - true if the contest status exists and removed
     * successfully, return false if it doesn't exist. #exception throw
     * ContestManagementException if any error occurs.
     * 
     * @param contestStatusId
     * @return
     */
    public boolean removeContestStatus(long contestStatusId)
            throws ContestManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    /**
     * Get contest status, and return the retrieved contest status. Return null if it
     * doesn't exist. #Param contestStatusId - the contest status id. #Return - the
     * retrieved contest status, or null if it doesn't exist. #exception throw
     * ContestManagementException if any error occurs when getting contest status.
     * 
     * @param contestStatusId
     * @return
     */
    public ContestStatus getContestStatus(long contestStatusId)
            throws ContestManagementException {
        return new ContestStatus();
        // throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Add new document, and return the added document. #Param document - the document to
     * add. #Return - the added document. #exception throw IllegalArgumentException if the
     * arg is null. throw EntityAlreadyExistsException if the entity already exists in the
     * persistence. throw ContestManagementException if any other error occurs.
     */
    public Document addDocument(Document document)
            throws ContestManagementException {
        checkErrorRequest();
        if (documents.get(document.getDocumentId()) != null) {
            throw new EntityAlreadyExistsException("Duplicated entity.");
        }
        documents.put(document.getDocumentId(), document);
        return document;
    }

    /**
     * Update document. #Param document - the document to update. #exception throw
     * IllegalArgumentException if the arg is null. throw EntityNotFoundException if the
     * document doesn't exist in persistence. throw ContestManagementException if any
     * other error occurs.
     * 
     * @param document
     */
    public void updateDocument(Document document)
            throws ContestManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    /**
     * Get document by id, and return the retrieved document. Return null if the document
     * doesn't exist. #Param documentId - the document id. #Return - the retrieved
     * document, or null if it doesn't exist. #exception throw ContestManagementException
     * if any error occurs when getting document.
     * 
     * @param documentId
     * @return
     */
    public Document getDocument(long documentId)
            throws ContestManagementException {
        return new Document();
    }

    /**
     * Remove document, return true if the document exists and removed successfully,
     * return false if it doesn't exist. #Param documentId - the document id. #Return -
     * true if the document exists and removed successfully, return false if it doesn't
     * exist. #exception throw ContestManagementException if any error occurs.
     * 
     * @param documentId
     * @return
     */
    public boolean removeDocument(long documentId)
            throws ContestManagementException {
        return true;
    }

    /**
     * Add document to contest. Nothing happens if the document already exists in contest.
     * #Param documentId - the document id. contestId - the contest id. #exception throw
     * EntityNotFoundException if there is no corresponding document or contest in
     * persistence. throw ContestManagementException if any other error occurs.
     */
    public void addDocumentToContest(long documentId, long contestId)
            throws ContestManagementException {
        checkErrorRequest();
        if (documents.get(documentId) == null) {
            throw new EntityNotFoundException("no such document");
        }
        if (contests.get(contestId) == null) {
            throw new EntityNotFoundException("no such contest");
        }

        if (documentsForContest.get(contestId) == null) {
            documentsForContest.put(contestId, new HashSet<Long>());
        }
        documentsForContest.get(contestId).add(documentId);
    }

    /**
     * Remove document from contest. Return true if the document exists in the contest and
     * removed successfully, return false if it doesn't exist in contest. #Param
     * documentId - the document id. contestId - the contest id. #Return - true if the
     * document exists in the contest and removed successfully, return false if it doesn't
     * exist in contest. #exception throw EntityNotFoundException if there is no
     * corresponding document or contest in persistence. throw ContestManagementException
     * if any other error occurs.
     */
    public boolean removeDocumentFromContest(long documentId, long contestId)
            throws ContestManagementException {
        checkErrorRequest();
        if (documents.get(documentId) == null) {
            throw new EntityNotFoundException("no such document");
        }
        if (contests.get(contestId) == null) {
            throw new EntityNotFoundException("no such contest");
        }

        return documentsForContest.get(contestId).remove(documentId);
    }

    /**
     * Add contest category, and return the added contest category. #Param contestCategory -
     * the contest category to add. #return - the added contest category. #exception throw
     * IllegalArgumentException if the arg is null. throw EntityAlreadyExistsException if
     * the entity already exists in the persistence. throw ContestManagementException if
     * any other error occurs.
     * 
     * @param contestCategory
     * @return
     */
    public ContestChannel addContestCategory(ContestChannel contestCategory)
            throws ContestManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    /**
     * Update contest category #Param contestCategory - the contest category to update.
     * #exception throw IllegalArgumentException if the arg is null. throw
     * EntityNotFoundException if the contestCategory doesn't exist in persistence. throw
     * ContestManagementException if any other error occurs.
     * 
     * @param contestCategory
     */
    public void updateContestCategory(ContestChannel contestCategory)
            throws ContestManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    /**
     * Remove contest category, return true if the contest category exists and removed
     * successfully, return false if it doesn't exist. #Param contestCategoryId - the
     * contest category id. #Return - true if the contest category exists and removed
     * successfully, return false if it doesn't exist. #exception throw
     * ContestManagementException if fail to remove the contest category when it exists.
     * 
     * @param contestCategoryId
     */
    public boolean removeContestCategory(long contestCategoryId)
            throws ContestManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    /**
     * Get contest category, and return the retrieved contest category. Return null if it
     * doesn't exist. #Param contestCategoryId - the contest category id. #Return - the
     * retrieved contest category, or null if it doesn't exist. #exception throw
     * ContestManagementException if any error occurs when getting contest category.
     * 
     * @param contestCategoryId
     * @return
     */
    public ContestChannel getContestCategory(long contestCategoryId)
            throws ContestManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    /**
     * Add contest configuration parameter, and return the added contest configuration
     * parameter. #Param contestConfig - the contest configuration parameter to add.
     * #return - the added contest configuration parameter. #exception throw
     * IllegalArgumentException if the arg is null. throw EntityAlreadyExistsException if
     * the entity already exists in the persistence. throw ContestManagementException if
     * any other error occurs.
     * 
     * @param contestConfig
     * @return
     */
    public ContestConfig addConfig(ContestConfig contestConfig)
            throws ContestManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    /**
     * Update contest configuration parameter #Param contestConfig - the contest
     * configuration parameter to update. #exception throw IllegalArgumentException if the
     * arg is null. throw EntityNotFoundException if the contest configuration parameter
     * doesn't exist in persistence. throw ContestManagementException if any other error
     * occurs.
     * 
     * @param contestConfig
     */
    public void updateConfig(ContestConfig contestConfig)
            throws ContestManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    /**
     * Get contest configuration parameter by id, and return the retrieved contest
     * configuration parameter. Return null if it doesn't exist. #Param contestConfigId -
     * the contest configuration parameter id. #Return - the retrieved contest
     * configuration parameter, or null if it doesn't exist. #exception throw
     * ContestManagementException if any error occurs when getting contest configuration
     * parameter
     * 
     * @param contestConfigId
     * @return
     */
    public ContestConfig getConfig(long contestConfigId)
            throws ContestManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    /**
     * Save document content in file system. This method should call
     * DocumentContentManager.saveDocumentContent to save the document content. #param
     * documentId - the document documentContent - the file data of the document to save.
     * #exception throw IllegalArgumentException if fileData argument is null or empty
     * array. throw EntityNotFoundException if the document doesn't exist in persistence.
     * throw ContestManagementException if any other error occurs. throw - let exceptions
     * from DocumentContentManager.saveDocumentContent propagate.
     */
    public void saveDocumentContent(long documentId, byte[] documentContent)
            throws ContestManagementException {
        checkErrorRequest();

        if (documentContent == null || documentContent.length == 0) {
            throw new IllegalArgumentException("Bad content");
        }

        if (documents.get(documentId) == null) {
            throw new EntityNotFoundException("no such document");
        }

        documentsContent.put(documentId, documentContent);
    }

    /**
     * Get document content to return. If the document is not saved, null is returned. It
     * will use DocumentContentManager to get document content. It can also return empty
     * array if the document content is empty. #Param documentId - the document id.
     * #Return - the document content in byte array. If the document is not saved, null is
     * returned. #exception throw EntityNotFoundException if the document doesn't exist in
     * persistence. throw ContestManagementException if any other error occurs. throw -
     * let exceptions from DocumentContentManager.getDocumentContent propagate.
     */
    public byte[] getDocumentContent(long documentId)
            throws ContestManagementException {
        checkErrorRequest();
        if (documents.get(documentId) == null) {
            throw new EntityNotFoundException("no such document");
        }
        return documentsContent.get(documentId);
    }

    /**
     * Check the document's content exists or not. Return true if it exists, return false
     * otherwise. It will use DocumentContentManager to check document content's
     * existence. #Param document - the document. #Return - true if the document content
     * exists, return false otherwise. #exception throw EntityNotFoundException if the
     * document doesn't exist in persistence. throw ContestManagementException if any
     * other error occurs. throw - let exceptions from
     * DocumentContentManager.existDocumentContent propagate.
     */
    public boolean existDocumentContent(long documentId)
            throws ContestManagementException {
        return getDocumentContent(documentId) != null;
    }

    /**
     * Get all contest statuses to return. If no contest status exists, return an empty
     * list. #Return - a list of contest statuses. #exception throw
     * ContestManagementException if any error occurs when getting contest statuses.
     */
    public List<ContestStatus> getAllContestStatuses()
            throws ContestManagementException {
        checkErrorRequest();

        List<ContestStatus> result = new ArrayList<ContestStatus>();

        for (int i = 0; i < 3; ++i) {
            ContestStatus st = new ContestStatus();

            st.setContestStatusId(0l + i);
            st.setDescription("desc" + i);
            st.setName("name" + i);
            List<ContestStatus> status = new ArrayList<ContestStatus>();
            st.setStatuses(status);
            result.add(st);
        }

        return result;
    }

    /**
     * Get all contest categories to return. If no contest category exists, return an
     * empty list. #Return - a list of contest categories. #exception throw
     * ContestManagementException if any error occurs when getting contest categories.
     */
    public List<ContestChannel> getAllContestCategories()
            throws ContestManagementException {
        checkErrorRequest();

        List<ContestChannel> result = new ArrayList<ContestChannel>();

        for (int i = 0; i < 3; ++i) {
            ContestChannel ch = new ContestChannel();

            ch.setContestChannelId(0l + i);
            ch.setDescription("desc" + i);
            // ch.setName("name" + i);
            // ch.setParentChannelId(10l + i);

            result.add(ch);
        }

        return result;
    }

    /**
     * Get all studio file types to return. If no studio file type exists, return an empty
     * list. #Return - a list of studio file types #exception throw
     * ContestManagementException if any error occurs when getting studio file types.
     */
    public List<StudioFileType> getAllStudioFileTypes()
            throws ContestManagementException {

        List<StudioFileType> result = new ArrayList<StudioFileType>();

        for (int i = 0; i < 3; ++i) {
            StudioFileType ft = new StudioFileType();
            ft.setExtension("ex" + i);
            result.add(ft);
        }

        StudioFileType type = new StudioFileType();
        type.setExtension("zip");
        result.add(type);

        return result;
    }

    /**
     * Add contest type configuration parameter, and return the added contest type
     * configuration parameter. #Param contestTypeConfig - the contest type configuration
     * parameter to add. #return - the added contest type configuration parameter.
     * #exception throw IllegalArgumentException if the arg is null. throw
     * EntityAlreadyExistsException if the entity already exists in the persistence. throw
     * ContestManagementException if any other error occurs.
     * 
     * @param contestTypeConfig
     * @return
     */
    public ContestTypeConfig addContestTypeConfig(
            ContestTypeConfig contestTypeConfig)
            throws ContestManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    /**
     * Update contest type configuration parameter #Param contestTypeConfig - the contest
     * type configuration parameter to update. #exception throw IllegalArgumentException
     * if the arg is null. throw EntityNotFoundException if the contest type configuration
     * parameter doesn't exist in persistence. throw ContestManagementException if any
     * other error occurs.
     * 
     * @param contestTypeConfig
     */
    public void updateContestTypeConfig(ContestTypeConfig contestTypeConfig)
            throws ContestManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    /**
     * Get contest type configuration parameter by id, and return the retrieved contest
     * type configuration parameter. Return null if it doesn't exist. #Param
     * contestTypeConfigId - the contest type configuration parameter id. #Return - the
     * retrieved contest type configuration parameter, or null if it doesn't exist.
     * #exception throw ContestManagementException if any error occurs when getting
     * contest type configuration parameter
     * 
     * @param contestTypeConfigId
     * @return
     */
    public ContestTypeConfig getContestTypeConfig(long contestTypeConfigId)
            throws ContestManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    /**
     * Add prize to the given contest. Nothing happens if the prize already exists in
     * contest. #Param contestId - the contest id. prizeId - the prize id. #exception
     * throw EntityNotFoundException if there is no corresponding prize or contest in
     * persistence. throw ContestManagementException if any other error occurs.
     * 
     * @param contestId
     * @param prizeId
     */
    public void addPrizeToContest(long contestId, long prizeId)
            throws ContestManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    /**
     * Remove prize from contest. Return true if the prize exists in the contest and
     * removed successfully, return false if it doesn't exist in contest. #Param prizeId -
     * the prize id. contestId - the contest id. #Return - true if the prize exists in the
     * contest and removed successfully, return false if it doesn't exist in contest.
     * #exception throw EntityNotFoundException if there is no corresponding prize or
     * contest in persistence. throw ContestManagementException if any other error occurs.
     * 
     * @param contestId
     * @param prizeId
     * @return
     */
    public boolean removePrizeFromContest(long contestId, long prizeId)
            throws ContestManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    /**
     * Retrieve all prizes in the given contest to return. An empty list is returned if
     * there is no such prizes. #Param contestId - the contest id. #Return - a list of
     * prizes. #exception throw EntityNotFoundException if there is no corresponding
     * contest in persistence. throw ContestManagementException if any other error occurs.
     * 
     * @param contestId
     * @return
     */
    public List<Prize> getContestPrizes(long contestId)
            throws ContestManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    public void addChangeHistory(List<ContestChangeHistory> history)
            throws ContestManagementException {
    }

    public ContestChannel addContestChannel(ContestChannel contestChannel)
            throws ContestManagementException {
        return null;
    }

    public ContestPayment createContestPayment(ContestPayment contestPayment)
            throws ContestManagementException {
        return null;
    }

    public ContestResult createContestResult(ContestResult contestResult)
            throws ContestManagementException {
        return null;
    }

    public Prize createPrize(Prize prize) throws ContestManagementException {
        return null;
    }

    public void deleteContest(long contestId) throws ContestManagementException {

    }

    public void editContestPayment(ContestPayment contestPayment)
            throws ContestManagementException {
    }

    public ContestResult findContestResult(long submissionId, long contestId)
            throws ContestManagementException {
        return null;
    }

    public List<ContestChannel> getAllContestChannels()
            throws ContestManagementException {

        return null;
    }

    public List<ContestProperty> getAllContestProperties()
            throws ContestManagementException {
        return null;
    }

    public List<ContestType> getAllContestTypes()
            throws ContestManagementException {
        List<ContestType> types = new ArrayList<ContestType>();
        return types;
    }

    public List<Contest> getAllContests() throws ContestManagementException {
        return new ArrayList<Contest>();
    }

    public List<DocumentType> getAllDocumentTypes()
            throws ContestManagementException {
        return null;
    }

    public List<Medium> getAllMedia() throws ContestManagementException {
        return null;
    }

    public List<MimeType> getAllMimeTypes() throws ContestManagementException {
        return null;
    }

    public List<PaymentStatus> getAllPaymentStatuses()
            throws ContestManagementException {
        return null;
    }

    public List<PaymentType> getAllPaymentTypes()
            throws ContestManagementException {
        return null;
    }

    public List<PrizeType> getAllPrizeTypes() throws ContestManagementException {
        return null;
    }

    public List<ContestChangeHistory> getChangeHistory(long contestId)
            throws ContestManagementException {
        return null;
    }

    public List<ContestChangeHistory> getChangeHistory(long contestId,
            long transactionId) throws ContestManagementException {
        return null;
    }

    public ContestConfig getConfig(Identifier compositeId)
            throws ContestManagementException {
        return null;
    }

    public ContestChannel getContestChannel(long contestChannelId)
            throws ContestManagementException {
        return null;
    }

    public List<SimpleContestData> getContestDataOnly()
            throws ContestManagementException {
        return null;
    }

    public List<SimpleContestData> getContestDataOnly(long createdUser, long pid)
            throws ContestManagementException {
        return null;
    }

    public List<SimpleContestData> getContestDataOnlyForUser(long createdUser)
            throws ContestManagementException {
        return null;
    }

    public List<ContestPayment> getContestPayments(long contestId)
            throws ContestManagementException {
        return null;
    }

    public int getContestPostCount(long forumId)
            throws ContestManagementException {
        return 0;
    }

    public Map<Long, Long> getContestPostCount(List<Long> forumIds)
            throws ContestManagementException {
        return new HashMap<Long, Long>();
    }

    public ContestProperty getContestProperty(long contestPropertyId)
            throws ContestManagementException {
        ContestProperty prop = new ContestProperty();
        prop.setPropertyId(3);
        return prop;
    }

    public List<Contest> getContestsForUser(long createdUser)
            throws ContestManagementException {
        return new ArrayList<Contest>(contests.values());
    }

    public DocumentType getDocumentType(long documentTypeId)
            throws ContestManagementException {
        return new DocumentType();
    }

    public Long getLatestTransactionId(long contestId)
            throws ContestManagementException {
        return null;
    }

    public MimeType getMimeType(long mimeTypeId)
            throws ContestManagementException {
        return new MimeType();
    }

    public PaymentStatus getPaymentStatus(long paymentStatusId)
            throws ContestManagementException {
        return null;
    }

    public PaymentType getPaymentType(long paymentTypeId)
            throws ContestManagementException {
        return null;
    }

    public PrizeType getPrizeType(long prizeTypeId)
            throws ContestManagementException {
        return null;
    }

    public List<SimpleContestData> getSimpleContestData()
            throws ContestManagementException {
        return null;
    }

    public List<SimpleContestData> getSimpleContestData(long pid)
            throws ContestManagementException {
        return null;
    }

    public List<SimpleContestData> getSimpleContestDataForUser(long createdUser)
            throws ContestManagementException {
        return null;
    }

    public List<SimpleProjectContestData> getSimpleProjectContestData()
            throws ContestManagementException {
        return null;
    }

    public List<SimpleProjectContestData> getSimpleProjectContestData(long pid)
            throws ContestManagementException {
        return null;
    }

    public List<SimpleProjectContestData> getSimpleProjectContestDataForUser(
            long createdUser) throws ContestManagementException {
        return null;
    }

    public List<SimpleProjectPermissionData> getSimpleProjectPermissionDataForUser(
            long createdUser) throws ContestManagementException {
        return null;
    }

    public List<Contest> getUserContests(String username)
            throws ContestManagementException {
        List<Contest> result = new ArrayList<Contest>();
        if (!"myxgyy".equals(username)) {
            return result;
        }

        Contest c = new Contest();
        c.setContestId(100l);
        ContestType type = new ContestType();
        type.setDescription("development");
        c.setContestType(type);
        c.setStatusId(1001L);
        ContestStatus status = new ContestStatus();
        status.setContestStatusId(1002L);
        c.setStatus(status);
        ContestGeneralInfo info = new ContestGeneralInfo();
        info.setWinningCriteria("winningCriteria");
        info.setBrandingGuidelines("brandingGuidelines");
        info.setDislikedDesignsWebsites("dislikedDesignsWebsites");
        info.setGoals("goals");
        info.setOtherInstructions("otherInstructions");
        info.setTargetAudience("targetAudience");
        c.setGeneralInfo(info);
        ContestMultiRoundInformation multi = new ContestMultiRoundInformation();
        multi.setContestMultiRoundInformationId(1001L);
        multi.setMilestoneDate(new Date());
        multi.setRoundOneIntroduction("round one");
        multi.setRoundTwoIntroduction("round two");
        multi.setSubmittersLockedBetweenRounds(true);
        c.setMultiRoundInformation(multi);
        ContestSpecifications spe = new ContestSpecifications();
        spe.setAdditionalRequirementsAndRestrictions("not null");
        spe.setColors("red");
        spe.setContestSpecificationsId(1001L);
        spe.setFonts("Arial");
        spe.setLayoutAndSize("10px");
        c.setSpecifications(spe);
        MilestonePrize prize = new MilestonePrize();
        prize.setAmount(20.02d);
        prize.setCreateDate(new Date());
        prize.setMilestonePrizeId(1001L);
        prize.setNumberOfSubmissions(10);
        PrizeType pt = new PrizeType();
        pt.setDescription("payment");
        pt.setPrizeTypeId(1001L);
        prize.setType(pt);
        c.setMilestonePrize(prize);
        result.add(c);
        return result;
    }

    public boolean removeContestChannel(long contestChannelId)
            throws ContestManagementException {
        return false;
    }

    public boolean removeContestPayment(long contestPaymentId)
            throws ContestManagementException {
        return false;
    }

    public List<Contest> searchContests(Filter filter)
            throws ContestManagementException {
        return null;
    }

    public List<User> searchUser(String key) throws ContestManagementException {
        return null;
    }

    public void updateContest(Contest contest, long transactionId,
            String username, boolean userAdmin)
            throws ContestManagementException {
        updateContest(contest);
    }

    public void updateContestChannel(ContestChannel contestChannel)
            throws ContestManagementException {

    }
}
