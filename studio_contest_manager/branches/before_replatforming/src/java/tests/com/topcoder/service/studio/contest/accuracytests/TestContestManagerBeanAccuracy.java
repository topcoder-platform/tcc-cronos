/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.accuracytests;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import junit.framework.TestCase;

import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestGeneralInfo;
import com.topcoder.service.studio.contest.ContestMultiRoundInformation;
import com.topcoder.service.studio.contest.ContestProperty;
import com.topcoder.service.studio.contest.ContestSpecifications;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.ContestTypeConfig;
import com.topcoder.service.studio.contest.Document;
import com.topcoder.service.studio.contest.DocumentType;
import com.topcoder.service.studio.contest.FilePath;
import com.topcoder.service.studio.contest.MimeType;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.contest.ContestConfig.Identifier;
import com.topcoder.service.studio.contest.bean.ContestManagerBean;
import com.topcoder.service.studio.contest.bean.MockEntityManager;
import com.topcoder.service.studio.contest.documentcontentservers.SocketDocumentContentServer;
import com.topcoder.service.studio.submission.MilestonePrize;
import com.topcoder.service.studio.submission.Prize;
import com.topcoder.service.studio.submission.PrizeType;


/**
 * Accuracy test cases for class <code>ContestManagerBean </code>.
 *
 * @author Chenhong, myxgyy
 * @version 1.3
 * @since 1.0
 */
public class TestContestManagerBeanAccuracy extends TestCase {
    /** Represents the ContestManagerBean instance for testing. */
    private ContestManagerBean manager = null;

    /**
     * <p>
     * The mock <code>SessionContext</code> for simulating the ejb environment.
     * </p>
     */
    private MockSessionContext context;

    /** Represents the EntityManager for testing. */
    private EntityManager entityManager = null;

    /**
     * Represents the EntityTransaction instance for testing.
     */
    private EntityTransaction transaction = null;

    private int port = 40000;
    /**
     * Set up.
     *
     * @throws Exception
     *             to junit
     */
    public void setUp() throws Exception {
        DBUtil.clearDatabase();

        // create EntityManager.
        entityManager = MockEntityManager.EMF.createEntityManager();

        initialize();

        entityManager.getTransaction().begin();
        entityManager.createNativeQuery(
            "insert into contest_status_lu(contest_status_id," +
            " contest_status_desc) values(1, 'open')").executeUpdate();
        entityManager.getTransaction().commit();

    }

    private void initialize() throws Exception {

        manager = new ContestManagerBean();

        context = new MockSessionContext();

        context.addEntry("unitName", "contest_submission");
        context.addEntry("activeContestStatusId", new Long(10));
        context.addEntry("defaultDocumentPathId", new Long(1));
        //context.addEntry("loggerName", "System.out");
        context.addEntry("auditChange", new Boolean(false));

        context.addEntry("documentContentManagerClassName",
            "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(port));

        context.addEntry("contest_submission", entityManager);

        Field field = manager.getClass().getDeclaredField("sessionContext");
        field.setAccessible(true);
        field.set(manager, context);

        Method method = manager.getClass()
                               .getDeclaredMethod("initialize", new Class[0]);

        method.setAccessible(true);
        method.invoke(manager, new Object[0]);
    }

    /**
     * Tear down.
     *
     * @throws Exception
     *             to junit
     */
    public void tearDown() throws Exception {
        DBUtil.clearDatabase();
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }

    /**
     * Test the ctor.
     */
    public void testContestManagerBean() {
        assertNotNull("The ContestManagerBean should be created.", manager);
    }

    /**
     * Test the CRUD method <code>Contest createContest(Contest contest) </code>.
     * <p>
     * The contest should be persisted into the database. The transaction will be
     * commited.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateContest() throws Exception {
        transaction = entityManager.getTransaction();

        transaction.begin();

        // persist StudioFileType.
        StudioFileType fileType = new StudioFileType();
        fileType.setDescription("desc");
        fileType.setExtension("ext");
        fileType.setImageFile(false);
        fileType.setSort(new Integer(10));
        entityManager.persist(fileType);

        // Persist ContestChannel.
        ContestChannel contestChannel = new ContestChannel();
        contestChannel.setDescription("desc");
        contestChannel.setContestChannelId(1L);
        entityManager.persist(contestChannel);

        // Persist ContestType.
        ContestType contestType = new ContestType();
        contestType.setDescription("desc");
        contestType.setContestType(1L);
        contestType.setRequirePreviewFile(false);
        contestType.setRequirePreviewImage(false);
        entityManager.persist(contestType);

        // Persist ContestStatus.
        ContestStatus status = new ContestStatus();
        status.setDescription("description");
        status.setName("status");
        status.setContestStatusId(1L);
        status.setStatusId(1L);
        entityManager.persist(status);

        Date date = new Date();

        // create Contest for create.
        Contest contest = new Contest();
        contest.setName("contest1");
        contest.setCreatedUser(new Long(1));
        contest.setContestChannel(contestChannel);
        contest.setContestType(contestType);
        contest.setEndDate(date);
        contest.setEventId(new Long(1));
        contest.setForumId(new Long(1));
        contest.setProjectId(new Long(1));
        contest.setTcDirectProjectId(new Long(1));
        contest.setStartDate(new Date());
        contest.setStatus(status);
        contest.setWinnerAnnoucementDeadline(new Date());
        contest.setStatusId(1L);

        ContestSpecifications specData = new ContestSpecifications();
        specData.setAdditionalRequirementsAndRestrictions("not null");
        specData.setColors("Red");
        specData.setFonts("Arial");
        specData.setLayoutAndSize("10px");
        contest.setSpecifications(specData);

        PrizeType type = new PrizeType();
        type.setDescription("payment");
        type.setPrizeTypeId(2L);
        entityManager.persist(type);

        MilestonePrize prizeData = new MilestonePrize();
        prizeData.setType(type);
        prizeData.setCreateDate(new Date());
        prizeData.setAmount(20.00d);
        prizeData.setNumberOfSubmissions(10);
        contest.setMilestonePrize(prizeData);

        ContestMultiRoundInformation infoData = new ContestMultiRoundInformation();
        infoData.setMilestoneDate(new Date());
        infoData.setRoundOneIntroduction("one");
        infoData.setRoundTwoIntroduction("two");
        infoData.setSubmittersLockedBetweenRounds(true);
        contest.setMultiRoundInformation(infoData);

        ContestGeneralInfo generalInfoData = new ContestGeneralInfo();
        generalInfoData.setBrandingGuidelines("brandingGuidelines");
        generalInfoData.setDislikedDesignsWebsites("dislikedDesignsWebsites");
        generalInfoData.setGoals("goals");
        generalInfoData.setOtherInstructions("otherInstructions");
        generalInfoData.setTargetAudience("targetAudience");
        generalInfoData.setWinningCriteria("winningCriteria");
        contest.setGeneralInfo(generalInfoData);

        // Create the Contest in the bean.
        manager.createContest(contest);

        // try to find the Entity.
        Contest c = this.entityManager.find(Contest.class,
                contest.getContestId());
        assertNotNull("The contest must be created.", c);

        assertEquals("The name must be contest1.", "contest1", c.getName());

        assertEquals("The create User must be 1.", new Long(1),
            c.getCreatedUser());

        assertEquals("The status name must be 'status'", "status",
            c.getStatus().getName());

        // the Contest should be commited.
        entityManager.getTransaction().commit();
    }

    /**
     * Test method <code>Contest getContest(long contestId) </code>.
     * <p>
     * The contest with id 1 does not existed. Null is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetContest_1() throws Exception {
        Contest ret = manager.getContest(100L);
        assertNull("The contest does not existed.", ret);
    }

    /**
     * Test method <code>Contest getContest(long contestId) </code>.
     * <p>
     * The contest with id 1 does not existed. Null is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetContest_2() throws Exception {
        Long id = DBUtil.persisteOneContest(entityManager).getContestId();
        assertNotNull("The Contest id should not be null.", id);

        Contest ret = manager.getContest(id);

        assertNotNull("The Contest with id=" + id + " must exist.", ret);
    }

    /**
     * Test method
     * <code>List<Contest> getContestsForProject(long tcDirectProjectId) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testGetContestsForProject_1() throws Exception {
        DBUtil.persisteOneContest(entityManager);

        List<Contest> ret = manager.getContestsForProject(1);
        assertEquals("There should be one Contest with project 1.", 1,
            ret.size());
        assertEquals("The contest name is 'contest1'", "contest1",
            ret.get(0).getName());
    }

    /**
     * Test method
     * <code>List<Contest> getContestsForProject(long tcDirectProjectId) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testGetContestsForProject_2() throws Exception {
        List<Contest> ret = manager.getContestsForProject(1000);

        assertTrue("The return list should be empty.", ret.isEmpty());
    }

    /**
     * Test method <code>void updateContest(Contest contest) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateContest_1() throws Exception {
        // First persist one Contest.
        Contest contest = DBUtil.persisteOneContest(entityManager);

        contest.setCreatedUser(new Long(111));
        contest.setEventId(new Long(11));
        contest.setForumId(new Long(1111));
        contest.setStartDate(new Date());
        contest.setTcDirectProjectId(new Long(5));

        manager.updateContest(contest, 1, "admin", true);

        Contest ret = manager.getContest(contest.getContestId());

        assertEquals("Equal is expected.", new Long(111), ret.getCreatedUser());
        assertEquals("Equal is expected.", new Long(11), ret.getEventId());

        assertEquals("Equal is expected.", new Long(5),
            ret.getTcDirectProjectId());
        assertEquals("Equal is expected.", new Long(1111), ret.getForumId());
    }

    /**
     * Test method <code>void updateContest(Contest contest) </code>.
     * <p>
     * In this test case the ContestChannel is updated.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateContest_2() throws Exception {
        // First persist one Contest.
        Contest contest = DBUtil.persisteOneContest(entityManager);

        ContestChannel channel = DBUtil.persistContestChannel(entityManager);

        contest.setContestChannel(channel);

        manager.updateContest(contest, 1, "admin", true);

        Contest ret = manager.getContest(contest.getContestId());

        assertEquals("The ContestChannel name should be 'update channel'.",
            "update channel", ret.getContestChannel().getDescription());
    }

    /**
     * Test method <code>void updateContest(Contest contest) </code>.
     * <p>
     * In this test case the ContestStatus of the Contest will be updated.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateContest_3() throws Exception {
        // First persist one Contest.
        Contest contest = DBUtil.persisteOneContest(entityManager);

        ContestStatus status = DBUtil.persistContestStatus(entityManager);

        contest.setStatus(status);

        manager.updateContest(contest, 1, "admin", true);

        Contest ret = manager.getContest(contest.getContestId());

        assertEquals("The ContestStatus name should be 'update'.", "update",
            ret.getStatus().getName());
    }

    /**
     * Test method <code>void updateContest(Contest contest) </code>.
     * <p>
     * In this test case, the ContestType will be updated.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateContest_4() throws Exception {
        // First persist one Contest.
        Contest contest = DBUtil.persisteOneContest(entityManager);

        ContestType type = DBUtil.persistContestType(entityManager);

        contest.setContestType(type);

        manager.updateContest(contest, 1, "admin", true);

        Contest ret = manager.getContest(contest.getContestId());

        assertEquals("The ContestType des should be 'update'.", "update",
            ret.getContestType().getDescription());
    }

    /**
     * Test method
     * <code>ContestStatus addContestStatus(ContestStatus contestStatus) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testAddContestStatus() throws Exception {
        entityManager.getTransaction().begin();

        ContestStatus status = new ContestStatus();
        status.setDescription("new");
        status.setContestStatusId(1L);
        status.setStatusId(1L);
        status.setName("review");

        ContestStatus added = manager.addContestStatus(status);

        ContestStatus ret = entityManager.find(ContestStatus.class,
                added.getContestStatusId());

        assertNotNull("The ContestStatus should be persisted.", ret);

        assertEquals("The name should be 'review'", "review", ret.getName());
        entityManager.getTransaction().commit();
    }

    /**
     * Test method <code>updateContestStatus(ContestStatus contestStatus) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateContestStatusContestStatus()
        throws Exception {
        ContestStatus status = DBUtil.persistContestStatus(entityManager);

        entityManager.getTransaction().begin();

        status.setDescription("update");
        status.setName("screening");
        status.setContestStatusId(2L);

        manager.updateContestStatus(status);

        ContestStatus ret = entityManager.find(ContestStatus.class,
                status.getContestStatusId());

        assertEquals("Des should be 'update'", "update", ret.getDescription());

        assertEquals("Name should be 'screening'", "screening", ret.getName());
        entityManager.getTransaction().commit();
    }

    /**
     * Test method <code>boolean removeContestStatus(long contestStatusId) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testRemoveContestStatus_1() throws Exception {
        boolean ret = manager.removeContestStatus(100);

        assertFalse("The ContestStatus with id 100 does not exist.", ret);
    }

    /**
     * Test method <code>boolean removeContestStatus(long contestStatusId) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testRemoveContestStatus_2() throws Exception {
        ContestStatus status = DBUtil.persistContestStatus(entityManager);

        entityManager.getTransaction().begin();

        boolean ret = manager.removeContestStatus(status.getContestStatusId());

        assertTrue("True is expected.", ret);

        entityManager.getTransaction().commit();
    }

    /**
     * Test method <code>ContestStatus getContestStatus(long contestStatusId) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testGetContestStatus_1() throws Exception {
        assertNull("Not exist.", manager.getContestStatus(11111));
    }

    /**
     * Test method <code>ContestStatus getContestStatus(long contestStatusId) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testGetContestStatus_2() throws Exception {
        ContestStatus status = DBUtil.persistContestStatus(entityManager);

        ContestStatus ret = manager.getContestStatus(status.getContestStatusId());

        assertEquals("Equal is expected.", status.getDescription(),
            ret.getDescription());
        assertEquals("Equal is expected.", status.getName(), ret.getName());
    }

    /**
     * Test method <code>Document addDocument(Document document) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testAddDocument() throws Exception {
        entityManager.getTransaction().begin();

        // persist StudioFileType.
        StudioFileType fileType = new StudioFileType();
        fileType.setDescription("desc");
        fileType.setExtension("ext");
        fileType.setImageFile(false);
        fileType.setSort(new Integer(10));
        fileType.setStudioFileType(1);
        entityManager.persist(fileType);

        Document document = new Document();
        document.setCreateDate(new Date());

        MimeType type = new MimeType();
        type.setDescription("des");
        type.setStudioFileType(fileType);
        type.setMimeTypeId(1L);
        entityManager.persist(type);

        document.setMimeType(type);

        document.setOriginalFileName("name");

        FilePath path = new FilePath();
        path.setModifyDate(new Date());
        path.setPath("test_files");
        entityManager.persist(path);

        document.setPath(path);

        document.setSystemFileName("test");

        DocumentType documentType = new DocumentType();
        documentType.setDescription("documentType");
        documentType.setDocumentTypeId(1L);

        entityManager.persist(documentType);

        document.setType(documentType);

        Document doc = manager.addDocument(document);

        // the document must be committed into the database.
        entityManager.getTransaction().commit();

        assertNotNull("The document created should not be null.", doc);

        assertEquals("SThe system file name should be 'test'", "test",
            doc.getSystemFileName());
    }

    /**
     * Test method <code> void updateDocument(Document document) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateDocument() throws Exception {
        Document document = DBUtil.persisteDocument(entityManager);

        entityManager.getTransaction().begin();

        StudioFileType fileType = new StudioFileType();
        fileType.setDescription("desc");
        fileType.setExtension("ext");
        fileType.setImageFile(false);
        fileType.setSort(new Integer(10));
        fileType.setStudioFileType(2);
        entityManager.persist(fileType);

        MimeType type = new MimeType();
        type.setDescription("update");
        type.setStudioFileType(fileType);
        type.setMimeTypeId(3L);
        entityManager.persist(type);

        document.setMimeType(type);

        FilePath path = new FilePath();
        path.setModifyDate(new Date());
        path.setPath("update");
        entityManager.persist(path);
        document.setPath(path);

        DocumentType documentType = new DocumentType();
        documentType.setDocumentTypeId(2L);
        documentType.setDescription("update");

        entityManager.persist(documentType);

        document.setType(documentType);

        document.setOriginalFileName("update");
        document.setSystemFileName("update");

        manager.updateDocument(document);

        entityManager.getTransaction().commit();

        // validate if update succesfully.
        Document ret = entityManager.find(Document.class,
                document.getDocumentId());
        assertEquals("Equal to 'update'", "update",
            ret.getMimeType().getDescription());
        assertEquals("Equal to 'update'", "update", ret.getOriginalFileName());
        assertEquals("Equal to 'update'", "update", ret.getPath().getPath());

        assertEquals("Equal to 'update'", "update",
            ret.getType().getDescription());
    }

    /**
     * Test method <code>Document getDocument(long documentId) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testGetDocument_1() throws Exception {
        assertNull("The document does not exist.", manager.getDocument(1));
    }

    /**
     * Test method <code>Document getDocument(long documentId) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testGetDocument_2() throws Exception {
        Document document = DBUtil.persisteDocument(entityManager);

        Document ret = manager.getDocument(document.getDocumentId());

        assertNotNull("The document should exist.", ret);
    }

    /**
     * Test method <code>boolean removeDocument(long documentId) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testRemoveDocument_1() throws Exception {
        boolean ret = manager.removeDocument(1);

        assertFalse(ret);
    }

    /**
     * Test method <code>boolean removeDocument(long documentId) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testRemoveDocument_2() throws Exception {
        Document document = DBUtil.persisteDocument(entityManager);

        entityManager.getTransaction().begin();

        boolean ret = manager.removeDocument(document.getDocumentId());

        assertTrue(ret);

        entityManager.getTransaction().commit();
    }

    /**
     * Test method
     * <code>boolean removeDocumentFromContest(long documentId, long contestId) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testRemoveDocumentFromContest() throws Exception {
        Document document = DBUtil.persisteDocument(entityManager);
        Contest contest = DBUtil.persisteOneContest(entityManager);

        boolean ret = manager.removeDocumentFromContest(document.getDocumentId(),
                contest.getContestId());

        assertFalse(ret);
    }

    /**
     * Test method
     * <code>ContestChannel addContestChannel(ContestChannel contestChannel) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testAddContestChannel() throws Exception {
        entityManager.getTransaction().begin();

        StudioFileType fileType = new StudioFileType();
        fileType.setDescription("updateContest");
        fileType.setExtension("doc");
        fileType.setImageFile(false);
        fileType.setSort(new Integer(100));
        fileType.setStudioFileType(1);

        entityManager.persist(fileType);

        ContestChannel channel = new ContestChannel();
        channel.setDescription("des1");
        channel.setContestChannelId(1L);

        ContestChannel ret = manager.addContestChannel(channel);
        entityManager.getTransaction().commit();

        assertNotNull(ret);
    }

    /**
     * Test method <code>updateContestChannel(ContestChannel contestChannel) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateContestChannel() throws Exception {
        ContestChannel channel = DBUtil.persistContestChannel(entityManager);

        entityManager.getTransaction().begin();

        StudioFileType fileType = new StudioFileType();
        fileType.setDescription("updateContest");
        fileType.setExtension("doc");
        fileType.setImageFile(false);
        fileType.setSort(new Integer(100));

        entityManager.persist(fileType);

        channel.setDescription("des2");

        manager.updateContestChannel(channel);
        entityManager.getTransaction().commit();

        ContestChannel ret = entityManager.find(ContestChannel.class,
                channel.getContestChannelId());

        assertEquals("Name should be 'des2'", "des2", ret.getDescription());
    }

    /**
     * Test method <code>boolean removeContestChannel(long contestChannelId) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testRemoveContestChannel_1() throws Exception {
        assertFalse(manager.removeContestChannel(1));
    }

    /**
     * Test method <code>boolean removeContestChannel(long contestChannelId) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testRemoveContestChannel_2() throws Exception {
        ContestChannel channel = DBUtil.persistContestChannel(entityManager);

        entityManager.getTransaction().begin();

        boolean ret = manager.removeContestChannel(channel.getContestChannelId());

        assertTrue(ret);

        entityManager.getTransaction().commit();
    }

    /**
     * Test method <code>ContestChannel getContestChannel(long contestChannelId) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testGetContestChannel_1() throws Exception {
        assertNull(manager.getContestChannel(1));
    }

    /**
     * Test method <code>ContestChannel getContestChannel(long contestChannelId) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testGetContestChannel_2() throws Exception {
        ContestChannel c = DBUtil.persistContestChannel(entityManager);

        assertNotNull(manager.getContestChannel(c.getContestChannelId()));
    }

    /**
     * Test method <code> ContestConfig addConfig(ContestConfig contestConfig) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testAddConfig() throws Exception {
        ContestProperty property = new ContestProperty();
        property.setDescription("description");

        ContestConfig config = new ContestConfig();
        Identifier id = new Identifier();
        id.setProperty(property);
        config.setId(id);
        config.setValue("value");

        ContestConfig ret = manager.addConfig(config);

        assertNotNull(ret);

        assertEquals("The value should be 'value'", "value", ret.getValue());
    }

    private SocketDocumentContentServer startSocketDocumentContentServer() throws Exception {
        port++;
        SocketDocumentContentServer server = null;
        try {
            server = new SocketDocumentContentServer(port, 0);
            server.start();
        } catch (IOException e) {
            return startSocketDocumentContentServer();
        }

        Thread.sleep(500);
        return server;
    }

    /**
     * Test method
     * <code>saveDocumentContent(long documentId, byte[] documentContent) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testSaveDocumentContent() throws Exception {
        Document document = DBUtil.persisteDocument(entityManager);
        SocketDocumentContentServer server = startSocketDocumentContentServer();
        initialize();

        try {
            manager.saveDocumentContent(document.getDocumentId(),
                "abc".getBytes());
        } finally {
            server.stop();
        }
    }

    /**
     * Test method <code>byte[] getDocumentContent(long documentId) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testGetDocumentContent() throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        for (int i = 0; i < 1100; i++) {
            stream.write("abcde".getBytes());
        }

        Document document = DBUtil.persisteDocument(entityManager);
        SocketDocumentContentServer server = startSocketDocumentContentServer();
        initialize();

        try {
            byte[] content = manager.getDocumentContent(document.getDocumentId());

            assertEquals("The size should be equal", 3, content.length);
        } finally {
            server.stop();
        }
    }

    /**
     * Test method <code> public boolean existDocumentContent(long documentId) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testExistDocumentContent() throws Exception {
        Document doc = DBUtil.persisteDocument(entityManager);

        SocketDocumentContentServer server = startSocketDocumentContentServer();
        initialize();

        try {
            boolean ret = manager.existDocumentContent(doc.getDocumentId());

            assertTrue(ret);
        } finally {
            server.stop();
        }
    }

    /**
     * Test method <code>List<ContestStatus> getAllContestStatuses() </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testGetAllContestStatuses_1() throws Exception {
        List<ContestStatus> ret = manager.getAllContestStatuses();

        assertTrue(ret.isEmpty());
    }

    /**
     * Test method <code>List<ContestStatus> getAllContestStatuses() </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testGetAllContestStatuses_2() throws Exception {
        DBUtil.persistContestStatus(entityManager);

        List<ContestStatus> ret = manager.getAllContestStatuses();

        assertEquals("Equal to 1.", 1, ret.size());
    }

    /**
     * Test method <code>List<ContestChannel> getAllContestChannels() </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testGetAllContestChannels() throws Exception {
        DBUtil.persistContestChannel(entityManager);

        List<ContestChannel> ret = manager.getAllContestChannels();

        assertEquals("Equal to 1.", 1, ret.size());
    }

    /**
     * Test method <code>List<StudioFileType> getAllStudioFileTypes() </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testGetAllStudioFileTypes() throws Exception {
        DBUtil.persistContestChannel(entityManager);

        List<StudioFileType> ret = manager.getAllStudioFileTypes();

        assertEquals("Euqal to 1.", 1, ret.size());
    }

    /**
     * Test method <code> ContestTypeConfig addContestTypeConfig </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testAddContestTypeConfig() throws Exception {
        entityManager.getTransaction().begin();

        ContestProperty contestProperty = new ContestProperty();
        contestProperty.setDescription("des");
        entityManager.persist(contestProperty);

        ContestType type = new ContestType();
        type.setDescription("d");
        type.setRequirePreviewFile(true);
        type.setRequirePreviewImage(false);
        type.setContestType(1L);

        entityManager.persist(type);

        ContestTypeConfig.Identifier id = new ContestTypeConfig.Identifier();
        ContestTypeConfig config = new ContestTypeConfig();
        config.setPropertyValue("value");
        config.setRequired(true);
        id.setProperty(contestProperty);
        id.setContestType(type);
        config.setId(id);

        manager.addContestTypeConfig(config);

        entityManager.getTransaction().commit();
    }

    /**
     * Test method
     * <code> boolean removePrizeFromContest(long contestId, long prizeId) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testRemovePrizeFromContest() throws Exception {
        Contest contest = DBUtil.persisteOneContest(entityManager);

        entityManager.getTransaction().begin();

        Prize prize = new Prize();
        prize.setAmount(new Double(1000));
        prize.setCreateDate(new Date());
        prize.setPlace(new Integer(1));

        PrizeType prizeType = new PrizeType();
        prizeType.setDescription("des");
        prizeType.setPrizeTypeId(1L);
        entityManager.persist(prizeType);

        prize.setType(prizeType);

        entityManager.persist(prize);

        entityManager.getTransaction().commit();

        // The above code should be ok.
        Prize ret = entityManager.find(Prize.class, prize.getPrizeId());

        entityManager.getTransaction().begin();
        manager.addPrizeToContest(contest.getContestId(), ret.getPrizeId());

        boolean res = manager.removePrizeFromContest(contest.getContestId(),
                ret.getPrizeId());
        entityManager.getTransaction().commit();

        assertTrue("The prize should be removed.", res);
    }

    /**
     * Test method <code>List<Prize> getContestPrizes(long contestId) </code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testGetContestPrizes() throws Exception {
        Contest contest = DBUtil.persisteOneContest(entityManager);

        List<Prize> list = manager.getContestPrizes(contest.getContestId());

        assertTrue(list.isEmpty());
    }
}
