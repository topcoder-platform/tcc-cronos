/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestGeneralInfo;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.ContestMultiRoundInformation;
import com.topcoder.service.studio.contest.ContestProperty;
import com.topcoder.service.studio.contest.ContestSpecifications;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.ContestTypeConfig;
import com.topcoder.service.studio.contest.Document;
import com.topcoder.service.studio.contest.EntityNotFoundException;
import com.topcoder.service.studio.contest.FilePath;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.contest.TestHelper;
import com.topcoder.service.studio.contest.ContestConfig.Identifier;
import com.topcoder.service.studio.submission.MilestonePrize;
import com.topcoder.service.studio.submission.Prize;
import com.topcoder.service.studio.submission.PrizeType;

/**
 * <p>
 * Unit tests for <code>ContestManagerBean</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestManagerBeanUnitTest2 extends TestCase {
    /**
     * <p>
     * The <code>ContestManagerBean</code> instance for testing.
     * </p>
     */
    private ContestManagerBean bean;

    /**
     * <p>
     * The mock <code>SessionContext</code> for simulating the ejb
     * environment.
     * </p>
     */
    private MockSessionContext context;

    /**
     * <p>
     * The mock <code>EntityManager</code> for testing.
     * </p>
     */
    private MockEntityManager entityManager;

    /**
     * <p>
     * entities created during tests. They will be removed in the end of test.
     * </p>
     */
    private List entities = new ArrayList();

    /**
     * <p>
     * Sets up the environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        bean = new ContestManagerBean();

        context = new MockSessionContext();

        Field field = bean.getClass().getDeclaredField("sessionContext");
        field.setAccessible(true);
        field.set(bean, context);

        initContext();
    }

    /**
     * <p>
     * Destroy the environment.
     * </p>
     */
    protected void tearDown() {
        if (entityManager != null) {
            try {
                TestHelper.removeContests(entityManager, entities);
                entityManager.close();
            } catch (Exception e) {
                // ignore.
            }
        }
    }

    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestManagerBeanUnitTest2.class);
    }

    /**
     * <p>
     * Initialize the context.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    private void initContext() throws Exception {
        context.addEntry("unitName", "contestManager");
        context.addEntry("auditChange", new Boolean(false));
        context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", new Long(1));
        //context.addEntry("loggerName", "contestManager");
        context.addEntry("documentContentManagerClassName",
                "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys", "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = bean.getClass().getDeclaredMethod("initialize", new Class[0]);

        method.setAccessible(true);
        method.invoke(bean, new Object[0]);

        entityManager = new MockEntityManager();
        context.addEntry("contestManager", entityManager);
    }

    /**
     * <p>
     * Creates a contest for testing.
     * </p>
     *
     * @return a contest for testing.
     */
    private Contest createContestForTest() {
        StudioFileType fileType = new StudioFileType();
        fileType.setDescription("desc");
        fileType.setExtension("ext");
        fileType.setImageFile(false);
        fileType.setSort(new Integer(10));

        ContestChannel contestChannel = new ContestChannel();
        contestChannel.setDescription("desc");
        // contestChannel.setName("name");
        // contestChannel.setFileType(fileType);

        ContestType contestType = new ContestType();
        contestType.setDescription("desc");
        contestType.setRequirePreviewFile(false);
        contestType.setRequirePreviewImage(false);
        contestType.setContestType(13213L);
        entityManager.persist(contestType);

        ContestStatus status = new ContestStatus();
        status.setDescription("description");
        status.setName("name");
        status.setStatusId(1l);
        status.setContestStatusId(121L);

        Contest contest = new Contest();
        contest.setName("contest1");
        contest.setCreatedUser(new Long(1));
        contest.setContestChannel(contestChannel);
        contest.setContestType(contestType);
        contest.setEndDate(new Date());
        contest.setEventId(new Long(1));
        contest.setForumId(new Long(1));
        contest.setProjectId(new Long(1));
        contest.setTcDirectProjectId(new Long(1));
        contest.setStartDate(new Date());
        contest.setWinnerAnnoucementDeadline(new Date());

        return contest;
    }

    /**
     * <p>
     * Accuracy test for <code>addContestChannel(ContestChannel)</code>.
     * </p>
     *
     * <p>
     * Verify that the ContestChannel is added to the persistence.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    /*
     * public void testAddContestChannel() throws Exception {
     *
     *
     * StudioFileType fileType = new StudioFileType();
     * fileType.setDescription("desc"); fileType.setExtension("ext");
     * fileType.setImageFile(true); fileType.setSort(new Integer(1));
     * entityManager.persist(fileType);
     *
     * ContestChannel channel = new ContestChannel();
     * channel.setDescription("desc"); channel.setContestChannelId(88881l);
     *
     * bean.addContestChannel(channel);
     *
     * ContestChannel persist = entityManager.find(ContestChannel.class,
     * channel.getContestChannelId());
     *
     * assertEquals("The channel should match.", "desc",
     * persist.getDescription());
     *
     * entityManager.remove(channel); entityManager.remove(fileType); }
     *
     *//**
         * <p>
         * Failure test for <code>addContestChannel(ContestChannel)</code>.
         * </p>
         *
         * <p>
         * If the ContestChannel already exist,
         * <code>EntityAlreadyExistsException</code> is expected.
         * </p>
         *
         * @throws Exception to JUnit.
         */
    /*
     * public void testAddContestChannel_Failure1() throws Exception {
     * StudioFileType fileType = new StudioFileType(); try {
     *
     *
     * fileType.setDescription("desc"); fileType.setExtension("ext");
     * fileType.setImageFile(true); fileType.setSort(new Integer(2));
     * fileType.setStudioFileType(77771L); entityManager.persist(fileType);
     *
     * ContestChannel channel = new ContestChannel();
     * channel.setDescription("desc"); channel.setContestChannelId(1l);
     *
     * bean.addContestChannel(channel);
     *
     * bean.addContestChannel(channel); fail("EntityAlreadyExistsException is
     * expected."); } catch (EntityAlreadyExistsException e) { // success }
     * entityManager.remove(fileType); }
     *
     *//**
         * <p>
         * Failure test for <code>addContestChannel(ContestChannel)</code>.
         * </p>
         *
         * <p>
         * If entity manager is closed, <code>ContestManagementException</code>
         * is expected.
         * </p>
         *
         * @throws Exception to JUnit.
         */
    /*
     * public void testAddContestChannel_Failure3() throws Exception {
     * StudioFileType fileType = new StudioFileType(); try {
     *
     *
     * fileType.setDescription("desc"); fileType.setExtension("ext");
     * fileType.setImageFile(true); fileType.setSort(new Integer(3));
     * fileType.setStudioFileType(77772L); entityManager.persist(fileType);
     *
     * ContestChannel channel = new ContestChannel();
     * channel.setDescription("desc"); channel.setContestChannelId(1l);
     *
     * entityManager.close();
     *
     * bean.addContestChannel(channel);
     *
     * fail("ContestManagementException is expected."); } catch
     * (ContestManagementException e) { // success } }
     *
     *//**
         * <p>
         * Failure test for <code>addContestChannel(ContestChannel)</code>.
         * </p>
         *
         * <p>
         * If any transaction exception occurs,
         * <code>ContestManagementException</code> is expected.
         * </p>
         *
         * @throws Exception to JUnit.
         */
    /*
     * public void testAddContestChannel_Failure4() throws Exception {
     * StudioFileType fileType = new StudioFileType(); try {
     *
     *
     * fileType.setDescription("desc"); fileType.setExtension("ext");
     * fileType.setImageFile(true); fileType.setStudioFileType(77775L);
     * fileType.setSort(new Integer(4)); entityManager.persist(fileType);
     *
     * ContestChannel channel = new ContestChannel();
     * channel.setDescription("desc"); channel.setContestChannelId(1l);
     * entityManager.enableTransactionException(true);
     *
     * bean.addContestChannel(channel);
     *
     * fail("ContestManagementException is expected."); } catch
     * (ContestManagementException e) { // success } }
     *
     *//**
         * <p>
         * Failure test for <code>addContestChannel(ContestChannel)</code>.
         * </p>
         *
         * <p>
         * If any persistence exception occurs,
         * <code>ContestManagementException</code> is expected.
         * </p>
         *
         * @throws Exception to JUnit.
         */
    /*
     * public void testAddContestChannel_Failure5() throws Exception {
     * StudioFileType fileType = new StudioFileType(); try {
     *
     *
     * fileType.setDescription("desc"); fileType.setExtension("ext");
     * fileType.setImageFile(true); fileType.setStudioFileType(77776L);
     * fileType.setSort(new Integer(5)); entityManager.persist(fileType);
     *
     * ContestChannel channel = new ContestChannel();
     * channel.setDescription("desc"); channel.setContestChannelId(1l);
     *
     * entityManager.enablePersistenceException(true);
     * bean.addContestChannel(channel);
     *
     * fail("ContestManagementException is expected."); } catch
     * (ContestManagementException e) { // success } }
     *
     *//**
         * <p>
         * Accuracy test for <code>updateContestChannel(ContestChannel)</code>.
         * </p>
         *
         * <p>
         * Verify that the new ContestChannel is updated to the persistence.
         * </p>
         *
         * @throws Exception to JUnit.
         */
    /*
     * public void testUpdateContestChannel() throws Exception {
     *
     *
     * StudioFileType fileType = new StudioFileType();
     * fileType.setDescription("desc"); fileType.setExtension("ext");
     * fileType.setImageFile(true); fileType.setSort(new Integer(6));
     * fileType.setStudioFileType(77777L); entityManager.persist(fileType);
     *
     * ContestChannel channel = new ContestChannel();
     * channel.setDescription("desc"); channel.setContestChannelId(666661l);
     *
     * bean.addContestChannel(channel);
     *
     * channel.setDescription("desc1");
     *
     * bean.updateContestChannel(channel);
     *
     * ContestChannel persist =
     * bean.getContestChannel(channel.getContestChannelId());
     *
     * assertEquals("The persistence should match.", "desc1",
     * persist.getDescription());
     *
     * entityManager.remove(channel); entityManager.remove(fileType); }
     *
     *//**
         * <p>
         * Failure test for <code>updateContestChannel(ContestChannel)</code>.
         * </p>
         *
         * <p>
         * If the ContestChannel doesn't exists,
         * <code>EntityNotFoundException</code> is thrown.
         * </p>
         *
         * @throws Exception to JUnit.
         */
    /*
     * public void testUpdateContestChannel_Failure1() throws Exception {
     * StudioFileType fileType = new StudioFileType(); try {
     *
     *
     * fileType.setDescription("desc"); fileType.setExtension("ext");
     * fileType.setImageFile(true); fileType.setSort(new Integer(7));
     * fileType.setStudioFileType(77771L); entityManager.persist(fileType);
     *
     * ContestChannel channel = new ContestChannel();
     * channel.setDescription("desc"); channel.setContestChannelId(8887l);
     *
     * //bean.addContestChannel(channel); bean.updateContestChannel(channel);
     *
     * fail("EntityNotFoundException is expected."); } catch
     * (EntityNotFoundException e) { // success } }
     *
     *//**
         * <p>
         * Failure test for <code>updateContestChannel(ContestChannel)</code>.
         * </p>
         *
         * <p>
         * If the entityManager is closed,
         * <code>ContestManagementException</code> is thrown.
         * </p>
         *
         * @throws Exception to JUnit.
         */
    /*
     * public void testUpdateContestChannel_Failure2() throws Exception { try {
     *
     *
     * StudioFileType fileType = new StudioFileType();
     * fileType.setDescription("desc"); fileType.setExtension("ext");
     * fileType.setImageFile(true); fileType.setSort(new Integer(8));
     * entityManager.persist(fileType);
     *
     * ContestChannel channel = new ContestChannel();
     * channel.setDescription("desc"); channel.setContestChannelId(1l);
     *
     * bean.addContestChannel(channel);
     *
     * entityManager.close();
     *
     * bean.updateContestChannel(channel);
     *
     * fail("ContestManagementException is expected."); } catch
     * (ContestManagementException e) { // success } }
     *
     *//**
         * <p>
         * Failure test for <code>updateContestChannel(ContestChannel)</code>.
         * </p>
         *
         * <p>
         * If any transaction exception occurs,
         * <code>ContestManagementException</code> is thrown.
         * </p>
         *
         * @throws Exception to JUnit.
         */
    /*
     * public void testUpdateContestChannel_Failure3() throws Exception { try {
     *
     *
     * StudioFileType fileType = new StudioFileType();
     * fileType.setDescription("desc"); fileType.setExtension("ext");
     * fileType.setImageFile(true); fileType.setSort(new Integer(9));
     * fileType.setStudioFileType(77772L); entityManager.persist(fileType);
     *
     * ContestChannel channel = new ContestChannel();
     * channel.setDescription("desc"); channel.setContestChannelId(1l);
     *
     * bean.addContestChannel(channel);
     *
     * entityManager.enableTransactionException(true);
     *
     * bean.updateContestChannel(channel);
     *
     * fail("ContestManagementException is expected."); } catch
     * (ContestManagementException e) { // success } }
     *
     *//**
         * <p>
         * Failure test for <code>updateContestChannel(ContestChannel)</code>.
         * </p>
         *
         * <p>
         * If any persistence exception occurs,
         * <code>ContestManagementException</code> is thrown.
         * </p>
         *
         * @throws Exception to JUnit.
         */
    /*
     * public void testUpdateContestChannel_Failure4() throws Exception { try {
     *
     *
     * StudioFileType fileType = new StudioFileType();
     * fileType.setDescription("desc"); fileType.setExtension("ext");
     * fileType.setImageFile(true); fileType.setSort(new Integer(10));
     * fileType.setStudioFileType(77773L); entityManager.persist(fileType);
     *
     * ContestChannel channel = new ContestChannel();
     * channel.setDescription("desc"); channel.setContestChannelId(1l);
     *
     * bean.addContestChannel(channel);
     *
     * entityManager.enablePersistenceException(true);
     * bean.updateContestChannel(channel);
     *
     * fail("ContestManagementException is expected."); } catch
     * (ContestManagementException e) { // success } }
     *
     *//**
         * <p>
         * Accuracy test for <code>getContestChannel(long)</code>.
         * </p>
         *
         * <p>
         * Verify that the ContestChannel with specified id will be returned.
         * </p>
         *
         * @throws Exception to JUnit.
         */
    /*
     * public void testGetContestChannel() throws Exception {
     *
     *
     * StudioFileType fileType = new StudioFileType();
     * fileType.setDescription("desc"); fileType.setExtension("ext");
     * fileType.setImageFile(true); fileType.setSort(new Integer(11));
     * fileType.setStudioFileType(777765L); entityManager.persist(fileType);
     *
     * ContestChannel channel = new ContestChannel();
     * channel.setDescription("desc"); channel.setContestChannelId(199l);
     *
     * bean.addContestChannel(channel);
     *
     * ContestChannel persist =
     * bean.getContestChannel(channel.getContestChannelId());
     *
     * assertEquals("The persistence should match.", persist.getDescription(),
     * channel.getDescription()); }
     *
     *//**
         * <p>
         * Failure test for <code>getContestChannel(long)</code>.
         * </p>
         *
         * <p>
         * If the entityManager is closed,
         * <code>ContestManagementException</code> is thrown.
         * </p>
         *
         * @throws Exception to JUnit.
         */
    /*
     * public void testGetContestChannel_Failure1() throws Exception { try {
     *
     *
     * entityManager.close(); bean.getContestChannel(1);
     *
     * fail("ContestManagementException is expected."); } catch
     * (ContestManagementException e) { // success } }
     *
     *//**
         * <p>
         * Failure test for <code>removeContestChannel(long)</code>.
         * </p>
         *
         * <p>
         * If any persistence exception occurs,
         * <code>ContestManagementException</code> is thrown.
         * </p>
         *
         * @throws Exception to JUnit.
         */
    /*
     * public void testGetContestChannel_Failure2() throws Exception { try {
     *
     *
     * entityManager.enablePersistenceException(true);
     * bean.getContestChannel(1);
     *
     * fail("ContestManagementException is expected."); } catch
     * (ContestManagementException e) { // success } }
     *
     *//**
         * <p>
         * Accuracy test for <code>removeContestChannel(long)</code>.
         * </p>
         *
         * <p>
         * Verify that if the ContestChannel with specified id doesn't exist,
         * return false.
         * </p>
         *
         * @throws Exception to JUnit.
         */
    /*
     * public void testRemoveContestChannel() throws Exception {
     *
     *
     * boolean result = bean.removeContestChannel(1000); assertFalse("The
     * ContestChannel doesn't exist.", result); }
     *
     *//**
         * <p>
         * Accuracy test for <code>removeContestChannel(long)</code>.
         * </p>
         *
         * <p>
         * Verify that if the contest status with specified id exists, return
         * true.
         * </p>
         *
         * @throws Exception to JUnit.
         */
    /*
     * public void testRemoveContestChannel1() throws Exception {
     *
     *
     * StudioFileType fileType = new StudioFileType();
     * fileType.setDescription("desc"); fileType.setExtension("ext");
     * fileType.setImageFile(true); fileType.setSort(new Integer(1));
     * entityManager.persist(fileType);
     *
     * ContestChannel channel = new ContestChannel();
     * channel.setDescription("desc"); channel.setContestChannelId(991L);
     *
     * bean.addContestChannel(channel);
     *
     * boolean result =
     * bean.removeContestChannel(channel.getContestChannelId()); assertTrue("The
     * ContestChannel exists.", result); }
     *
     *//**
         * <p>
         * Failure test for <code>removeContestChannel(long)</code>.
         * </p>
         *
         * <p>
         * If the entityManager is closed,
         * <code>ContestManagementException</code> is thrown.
         * </p>
         *
         * @throws Exception to JUnit.
         */
    /*
     * public void testRemoveContestChannel_Failure1() throws Exception { try {
     *
     *
     * entityManager.close(); bean.removeContestChannel(1);
     *
     * fail("ContestManagementException is expected."); } catch
     * (ContestManagementException e) { // success } }
     *
     *//**
         * <p>
         * Failure test for <code>removeContestChannel(long)</code>.
         * </p>
         *
         * <p>
         * If any transaction exception occurs,
         * <code>ContestManagementException</code> is thrown.
         * </p>
         *
         * @throws Exception to JUnit.
         */
    /*
     * public void testRemoveContestChannel_Failure2() throws Exception { try {
     *
     *
     * entityManager.enableTransactionException(true);
     * bean.removeContestChannel(1);
     *
     * fail("ContestManagementException is expected."); } catch
     * (ContestManagementException e) { // success } }
     *
     *//**
         * <p>
         * Failure test for <code>removeContestChannel(long)</code>.
         * </p>
         *
         * <p>
         * If any persistence exception occurs,
         * <code>ContestManagementException</code> is thrown.
         * </p>
         *
         * @throws Exception to JUnit.
         */
    /*
     * public void testRemoveContestChannel_Failure3() throws Exception { try {
     *
     *
     * entityManager.enablePersistenceException(true);
     * bean.removeContestChannel(1);
     *
     * fail("ContestManagementException is expected."); } catch
     * (ContestManagementException e) { // success } }
     */

    /**
     * Sets all required values for contest.
     *
     * @param entity the contest entity
     * @param date date to set
     * @param contestChannel category to set
     * @param contestType contest type
     * @param status contest status
     * @param generalInfo general info
     * @param multiRoundInformation multi-round info
     * @param specifications contest specification
     * @param milestonePrize the milestone prize
     */
    public static void populateContest(Contest entity, Date date, ContestChannel contestChannel,
            ContestType contestType, ContestStatus status, ContestGeneralInfo generalInfo,
            ContestMultiRoundInformation multiRoundInformation, ContestSpecifications specifications,
            MilestonePrize milestonePrize) {
        entity.setContestChannel(contestChannel);
        entity.setContestType(contestType);
        entity.setCreatedUser(10L);
        entity.setEndDate(date);
        entity.setEventId(101L);
        entity.setForumId(1000L);
        entity.setName("name");
        entity.setProjectId(101L);
        entity.setStartDate(date);
        entity.setStatus(status);
        entity.setStatusId(1L);
        entity.setTcDirectProjectId(1101L);
        entity.setWinnerAnnoucementDeadline(date);
        entity.setGeneralInfo(generalInfo);
        entity.setSpecifications(specifications);
        entity.setMultiRoundInformation(multiRoundInformation);
        entity.setMilestonePrize(milestonePrize);
    }

    /**
     * Create the ContestConfig.
     *
     * @return the ContestConfig.
     */
    private ContestConfig createContestConfig() {
        Contest contest = TestHelper.initContests(entityManager, entities);

        ContestProperty property = new ContestProperty();
        property.setDescription("description");

        ContestConfig config = new ContestConfig();
        Identifier id = new Identifier();
        id.setContest(contest);
        id.setProperty(property);
        config.setId(id);
        config.setValue("value");
        return config;
    }

    /**
     * <p>
     * Failure test for <code>saveDocumentContent(long, byte[])</code>.
     * </p>
     *
     * <p>
     * If the document with specified id doesn't exist,
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSaveDocumentContent_Failure2() throws Exception {
        try {

            byte[] content = new byte[4];
            content[0] = 1;
            content[1] = 1;
            content[2] = 1;
            content[3] = 1;

            // It should process successfully.
            bean.saveDocumentContent(100, content);
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>saveDocumentContent(long, byte[])</code>.
     * </p>
     *
     * <p>
     * If the entity manager is closed, <code>ContestManagementException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSaveDocumentContent_Failure3() throws Exception {
        try {

            FilePath filePath = new FilePath();
            filePath.setModifyDate(new Date());
            filePath.setPath("test_files");
            entityManager.persist(filePath);

            Document document = new Document();
            document.setCreateDate(new Date());

            bean.addDocument(document);

            byte[] content = new byte[1];
            content[0] = 1;

            entityManager.close();

            // It should process successfully.
            bean.saveDocumentContent(document.getDocumentId(), content);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>saveDocumentContent(long, byte[])</code>.
     * </p>
     *
     * <p>
     * If the any transaction exception occurs,
     * <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSaveDocumentContent_Failure4() throws Exception {
        try {

            FilePath filePath = new FilePath();
            filePath.setModifyDate(new Date());
            filePath.setPath("test_files");
            entityManager.persist(filePath);

            Document document = new Document();
            document.setCreateDate(new Date());

            bean.addDocument(document);

            byte[] content = new byte[1];
            content[0] = 1;

            entityManager.enableTransactionException(true);

            // It should process successfully.
            bean.saveDocumentContent(document.getDocumentId(), content);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>saveDocumentContent(long, byte[])</code>.
     * </p>
     *
     * <p>
     * If the any persistence exception occurs,
     * <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSaveDocumentContent_Failure5() throws Exception {
        try {

            FilePath filePath = new FilePath();
            filePath.setModifyDate(new Date());
            filePath.setPath("test_files");
            entityManager.persist(filePath);

            Document document = new Document();
            document.setCreateDate(new Date());

            bean.addDocument(document);

            byte[] content = new byte[1];
            content[0] = 1;

            entityManager.enablePersistenceException(true);

            // It should process successfully.
            bean.saveDocumentContent(document.getDocumentId(), content);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>saveDocumentContent(long, byte[])</code>.
     * </p>
     *
     * <p>
     * If the server doesn't open, <code>ContestManagementException</code> is
     * expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSaveDocumentContent_Failure6() throws Exception {
        try {

            FilePath filePath = new FilePath();
            filePath.setModifyDate(new Date());
            filePath.setPath("test_files");
            entityManager.persist(filePath);

            Document document = new Document();
            document.setCreateDate(new Date());

            bean.addDocument(document);

            byte[] content = new byte[1];
            content[0] = 1;

            // It should process successfully.
            bean.saveDocumentContent(document.getDocumentId(), content);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure1 test for <code>getAllContestStatuses()</code>.
     * </p>
     *
     * <p>
     * If the entity manager is closed, <code>ContestManagementException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllContestStatuses_Failure1() throws Exception {
        try {

            entityManager.close();

            bean.getAllContestStatuses();
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure1 test for <code>getAllContestStatuses()</code>.
     * </p>
     *
     * <p>
     * If any transaction exception throws,
     * <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllContestStatuses_Failure2() throws Exception {
        try {

            entityManager.enableTransactionException(true);

            bean.getAllContestStatuses();
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure1 test for <code>getAllContestStatuses()</code>.
     * </p>
     *
     * <p>
     * If any persistence exception throws,
     * <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllContestStatuses_Failure3() throws Exception {
        try {

            entityManager.enablePersistenceException(true);

            bean.getAllContestStatuses();
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure1 test for <code>getAllContestChannels()</code>.
     * </p>
     *
     * <p>
     * If the entity manager is closed, <code>ContestManagementException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllContestChannels_Failure1() throws Exception {
        try {

            entityManager.close();

            bean.getAllContestChannels();
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure1 test for <code>getAllContestChannels()</code>.
     * </p>
     *
     * <p>
     * If any persistence exception throws,
     * <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllContestChannels_Failure3() throws Exception {
        try {

            entityManager.enablePersistenceException(true);

            bean.getAllContestChannels();
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure1 test for <code>getAllStudioFileTypes()</code>.
     * </p>
     *
     * <p>
     * If the entity manager is closed, <code>ContestManagementException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllStudioFileTypes_Failure1() throws Exception {
        try {

            entityManager.close();

            bean.getAllStudioFileTypes();
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure1 test for <code>getAllStudioFileTypes()</code>.
     * </p>
     *
     * <p>
     * If any persistence exception throws,
     * <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllStudioFileTypes_Failure3() throws Exception {
        try {

            entityManager.enablePersistenceException(true);

            bean.getAllStudioFileTypes();
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>addContestTypeConfig(ContestTypeConfig)</code>.
     * </p>
     *
     * <p>
     * It doesn't have id, it can't be tested.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    /*
     * public void testAddContestTypeConfig() throws Exception {
     *
     *
     * ContestTypeConfig config = new ContestTypeConfig();
     * config.setPropertyValue("desc");
     *
     * bean.addContestTypeConfig(config);
     *
     * ContestTypeConfig persist =
     * bean.getContestTypeConfig(config.getContestTypeConfigId());
     * assertEquals("The contest type config should be match.", "desc",
     * persist.getPropertyValue()); }
     */

    /**
     * <p>
     * Failure test for <code>addContestTypeConfig(ContestTypeConfig)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddContestTypeConfig_Failure1() throws Exception {
        try {

            ContestTypeConfig config = new ContestTypeConfig();
            config.setPropertyValue("desc");

            entityManager.close();
            bean.addContestTypeConfig(config);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>addContestTypeConfig(ContestTypeConfig)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddContestTypeConfig_Failure2() throws Exception {
        try {

            ContestTypeConfig config = new ContestTypeConfig();
            config.setPropertyValue("desc");

            entityManager.enablePersistenceException(true);
            bean.addContestTypeConfig(config);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>addContestTypeConfig(ContestTypeConfig)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddContestTypeConfig_Failure3() throws Exception {
        try {

            ContestTypeConfig config = new ContestTypeConfig();
            config.setPropertyValue("desc");

            entityManager.enableTransactionException(true);
            bean.addContestTypeConfig(config);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>addContestTypeConfig(ContestTypeConfig)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    /*
     * public void testAddContestTypeConfig_Failure4() throws Exception { try {
     *
     *
     * ContestTypeConfig config = new ContestTypeConfig();
     * config.setPropertyValue("desc");
     *
     * bean.addContestTypeConfig(config);
     *
     * ContestTypeConfig tc = new ContestTypeConfig();
     * tc.setContestTypeConfigId(config.getContestTypeConfigId());
     * tc.setPropertyValue("new desc");
     *
     * bean.addContestTypeConfig(tc); fail("EntityAlreadyExistsException is
     * expected."); } catch (EntityAlreadyExistsException e) { // success } }
     */

    /**
     * <p>
     * Accuracy test for <code>updateContestTypeConfig(ContestTypeConfig)</code>.
     * </p>
     *
     * <p>
     * It doesn't have id, it can't be tested.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    /*
     * public void testUpdateContestTypeConfig() throws Exception {
     *
     *
     * ContestTypeConfig config = new ContestTypeConfig();
     * config.setPropertyValue("desc");
     *
     * bean.addContestTypeConfig(config);
     *
     * config.setPropertyValue("new desc");
     * bean.updateContestTypeConfig(config);
     *
     * ContestTypeConfig persist =
     * bean.getContestTypeConfig(config.getContestTypeConfigId());
     * assertEquals("The contest type config should match.", "new desc",
     * persist.getPropertyValue()); }
     */

    /**
     * <p>
     * Failure test for <code>updateContestTypeConfig(ContestTypeConfig)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContestTypeConfig_Failure1() throws Exception {
        try {

            ContestTypeConfig config = new ContestTypeConfig();
            config.setPropertyValue("desc");

            entityManager.close();
            bean.updateContestTypeConfig(config);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateContestTypeConfig(ContestTypeConfig)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContestTypeConfig_Failure2() throws Exception {
        try {

            ContestTypeConfig config = new ContestTypeConfig();
            config.setPropertyValue("desc");

            entityManager.enablePersistenceException(true);
            bean.updateContestTypeConfig(config);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateContestTypeConfig(ContestTypeConfig)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContestTypeConfig_Failure3() throws Exception {
        try {

            ContestTypeConfig config = new ContestTypeConfig();
            config.setPropertyValue("desc");

            entityManager.enableTransactionException(true);
            bean.updateContestTypeConfig(config);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>addPrizeToContest(long, long)</code>.
     * </p>
     *
     * <p>
     * If the contest with specified id doesn't exist,
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddPrizeToContest_Failure2() throws Exception {
        try {

            PrizeType prizeType = new PrizeType();
            prizeType.setDescription("desc");
            prizeType.setPrizeTypeId(11L);
            entityManager.persist(prizeType);

            Prize prize = new Prize();
            prize.setAmount(new Double(100));
            prize.setCreateDate(new Date());
            prize.setPlace(new Integer(1));
            prize.setType(prizeType);
            entityManager.persist(prize);

            // It should process successfully.
            bean.addPrizeToContest(100, prize.getPrizeId());

            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>addPrizeToContest(long, long)</code>.
     * </p>
     *
     * <p>
     * If entityManager is closed, <code>ContestManagementException</code> is
     * expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddPrizeToContest_Failure3() throws Exception {
        try {

            Contest contest = createContestForTest();
            bean.createContest(contest);

            PrizeType prizeType = new PrizeType();
            prizeType.setDescription("desc");
            entityManager.persist(prizeType);

            Prize prize = new Prize();
            prize.setAmount(new Double(100));
            prize.setCreateDate(new Date());
            prize.setPlace(new Integer(1));
            prize.setType(prizeType);
            entityManager.persist(prize);

            entityManager.close();
            // It should process successfully.
            bean.addPrizeToContest(contest.getContestId(), prize.getPrizeId());

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>addPrizeToContest(long, long)</code>.
     * </p>
     *
     * <p>
     * If any transaction exception occurs,
     * <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddPrizeToContest_Failure4() throws Exception {
        try {

            Contest contest = createContestForTest();
            bean.createContest(contest);

            PrizeType prizeType = new PrizeType();
            prizeType.setDescription("desc");
            entityManager.persist(prizeType);

            Prize prize = new Prize();
            prize.setAmount(new Double(100));
            prize.setCreateDate(new Date());
            prize.setPlace(new Integer(1));
            prize.setType(prizeType);
            entityManager.persist(prize);

            entityManager.enableTransactionException(true);

            // It should process successfully.
            bean.addPrizeToContest(contest.getContestId(), prize.getPrizeId());

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>addPrizeToContest(long, long)</code>.
     * </p>
     *
     * <p>
     * If any persistence exception occurs,
     * <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddPrizeToContest_Failure5() throws Exception {
        try {

            Contest contest = createContestForTest();
            bean.createContest(contest);

            PrizeType prizeType = new PrizeType();
            prizeType.setDescription("desc");
            entityManager.persist(prizeType);

            Prize prize = new Prize();
            prize.setAmount(new Double(100));
            prize.setCreateDate(new Date());
            prize.setPlace(new Integer(1));
            prize.setType(prizeType);
            entityManager.persist(prize);

            entityManager.enablePersistenceException(true);
            // It should process successfully.
            bean.addPrizeToContest(contest.getContestId(), prize.getPrizeId());

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>removePrizeFromContest(long, long)</code>.
     * </p>
     *
     * <p>
     * If the contest with specified id doesn't exist,
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemovePrizeFromContest_Failure2() throws Exception {
        try {

            PrizeType prizeType = new PrizeType();
            prizeType.setDescription("desc");
            prizeType.setPrizeTypeId(1L);
            entityManager.persist(prizeType);

            Prize prize = new Prize();
            prize.setAmount(new Double(100));
            prize.setCreateDate(new Date());
            prize.setPlace(new Integer(1));
            prize.setType(prizeType);
            entityManager.persist(prize);

            // It should process successfully.
            bean.removePrizeFromContest(100, prize.getPrizeId());

            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>removePrizeFromContest(long, long)</code>.
     * </p>
     *
     * <p>
     * If entityManager is closed, <code>ContestManagementException</code> is
     * expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemovePrizeFromContest_Failure3() throws Exception {
        try {

            Contest contest = createContestForTest();
            bean.createContest(contest);

            PrizeType prizeType = new PrizeType();
            prizeType.setDescription("desc");
            entityManager.persist(prizeType);

            Prize prize = new Prize();
            prize.setAmount(new Double(100));
            prize.setCreateDate(new Date());
            prize.setPlace(new Integer(1));
            prize.setType(prizeType);
            entityManager.persist(prize);

            entityManager.close();
            // It should process successfully.
            bean.removePrizeFromContest(contest.getContestId(), prize.getPrizeId());

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>removePrizeFromContest(long, long)</code>.
     * </p>
     *
     * <p>
     * If any transaction exception occurs,
     * <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemovePrizeFromContest_Failure4() throws Exception {
        try {

            Contest contest = createContestForTest();
            bean.createContest(contest);

            PrizeType prizeType = new PrizeType();
            prizeType.setDescription("desc");
            entityManager.persist(prizeType);

            Prize prize = new Prize();
            prize.setAmount(new Double(100));
            prize.setCreateDate(new Date());
            prize.setPlace(new Integer(1));
            prize.setType(prizeType);
            entityManager.persist(prize);

            entityManager.enableTransactionException(true);

            // It should process successfully.
            bean.removePrizeFromContest(contest.getContestId(), prize.getPrizeId());

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>removePrizeFromContest(long, long)</code>.
     * </p>
     *
     * <p>
     * If any persistence exception occurs,
     * <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemovePrizeFromContest_Failure5() throws Exception {
        try {

            Contest contest = createContestForTest();
            bean.createContest(contest);

            PrizeType prizeType = new PrizeType();
            prizeType.setDescription("desc");
            entityManager.persist(prizeType);

            Prize prize = new Prize();
            prize.setAmount(new Double(100));
            prize.setCreateDate(new Date());
            prize.setPlace(new Integer(1));
            prize.setType(prizeType);
            entityManager.persist(prize);

            entityManager.enablePersistenceException(true);
            // It should process successfully.
            bean.removePrizeFromContest(contest.getContestId(), prize.getPrizeId());

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateContestStatus(long, long)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContestStatus_Failure1() throws Exception {
        try {

            bean.updateContestStatus(100, 100);
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }
}
