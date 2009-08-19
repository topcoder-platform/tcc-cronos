/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.failuretests;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import javax.persistence.EntityManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.project.Project;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestGeneralInfo;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.ContestMultiRoundInformation;
import com.topcoder.service.studio.contest.ContestSpecifications;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.Document;
import com.topcoder.service.studio.contest.DocumentType;
import com.topcoder.service.studio.contest.EntityAlreadyExistsException;
import com.topcoder.service.studio.contest.EntityNotFoundException;
import com.topcoder.service.studio.contest.FilePath;
import com.topcoder.service.studio.contest.MimeType;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.contest.bean.ContestManagerBean;
import com.topcoder.service.studio.contest.bean.DBUtil;
import com.topcoder.service.studio.contest.documentcontentservers.SocketDocumentContentServer;
import com.topcoder.service.studio.contest.failuretests.mock.MockEntityManager;
import com.topcoder.service.studio.contest.failuretests.mock.MockSessionContext;
import com.topcoder.service.studio.submission.MilestonePrize;
import com.topcoder.service.studio.submission.Prize;
import com.topcoder.service.studio.submission.PrizeType;


/**
 * <p>
 * Failure tests for ContestManagerBean class.
 * </p>
 *
 * @author liuliquan
 * @version 1.3
 */
public class ContestManagerBeanFailureTest extends TestCase {
    /**
     * <p>
     * The ContestManagerBean instance for testing.
     * </p>
     */
    private ContestManagerBean beanUnderTest;

    /**
     * <p>
     * The mock SessionContext for simulating the ejb environment.
     * </p>
     */
    private MockSessionContext context;

    /**
     * <p>
     * The mock EntityManager for testing.
     * </p>
     */
    private MockEntityManager entityManager;

    /**
     * <p>
     * Sets up the environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        beanUnderTest = new ContestManagerBean();

        context = new MockSessionContext();

        Field field = beanUnderTest.getClass().getDeclaredField("sessionContext");
        field.setAccessible(true);
        field.set(beanUnderTest, context);

        entityManager = new MockEntityManager();
        DBUtil.clearDatabase();
        DBUtil.initDatabase();
    }

    /**
     * <p>
     * Destroy the environment.
     * </p>
     */
    protected void tearDown() {
        beanUnderTest = null;
        context = null;
        if (entityManager.isOpen()) {
            //clearTables();
            entityManager.close();
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
        return new TestSuite(ContestManagerBeanFailureTest.class);
    }

    /**
     * <p>
     * Initialize the context.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    private void initContext() throws Exception {
        context.addEntry("auditChange", new Boolean(false));
        context.addEntry("unitName", "contestManager");
        context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", new Long(1));
        //context.addEntry("loggerName", "contestManager");
        context.addEntry("documentContentManagerClassName",
            "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = beanUnderTest.getClass()
                                     .getDeclaredMethod("initialize",
                new Class[0]);

        method.setAccessible(true);
        method.invoke(beanUnderTest, new Object[0]);

        context.addEntry("contestManager", entityManager);
    }

    /**
     * <p>
     * Failure test for private method initialize().
     * </p>
     *
     * <p>
     * Failure cause: If the unitName is missing, ContestManagementException will be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_Initialize_Failure1_miss_unitName()
        throws Exception {
        context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", new Long(1));
        context.addEntry("loggerName", "contestManager");
        context.addEntry("documentContentManagerClassName",
            "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = beanUnderTest.getClass()
                                     .getDeclaredMethod("initialize",
                new Class[0]);

        method.setAccessible(true);

        try {
            method.invoke(beanUnderTest, new Object[0]);
            fail("ContestManagementException is expected.");
        } catch (InvocationTargetException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for private method initialize().
     * </p>
     *
     * <p>
     * Failure cause: If the unitName isn't instance of String, ContestManagementException will be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_Initialize_Failure2_unitName_notString()
        throws Exception {
        context.addEntry("unitName", new Object());
        context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", new Long(1));
        context.addEntry("loggerName", "contestManager");
        context.addEntry("documentContentManagerClassName",
            "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = beanUnderTest.getClass()
                                     .getDeclaredMethod("initialize",
                new Class[0]);

        method.setAccessible(true);

        try {
            method.invoke(beanUnderTest, new Object[0]);
            fail("ContestManagementException is expected.");
        } catch (InvocationTargetException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for private method initialize().
     * </p>
     *
     * <p>
     * Failure cause: If the unitName is empty string, ContestManagementException will be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_Initialize_Failure3_empty_unitName()
        throws Exception {
        context.addEntry("unitName", "  ");
        context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", new Long(1));
        context.addEntry("loggerName", "contestManager");
        context.addEntry("documentContentManagerClassName",
            "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = beanUnderTest.getClass()
                                     .getDeclaredMethod("initialize",
                new Class[0]);

        method.setAccessible(true);

        try {
            method.invoke(beanUnderTest, new Object[0]);
            fail("ContestManagementException is expected.");
        } catch (InvocationTargetException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for private method initialize().
     * </p>
     *
     * <p>
     * Failure cause: If the activeContestStatusId is missing, ContestManagementException will be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_Initialize_Failure4_miss_activeContestStatusId()
        throws Exception {
        context.addEntry("unitName", "contestManager");
        // context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", new Long(1));
        context.addEntry("documentContentManagerClassName",
            "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = beanUnderTest.getClass()
                                     .getDeclaredMethod("initialize",
                new Class[0]);

        method.setAccessible(true);

        try {
            method.invoke(beanUnderTest, new Object[0]);
            fail("ContestManagementException is expected.");
        } catch (InvocationTargetException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for private method initialize().
     * </p>
     *
     * <p>
     * Failure cause: If the activeContestStatusId isn't a Long value, ContestManagementException will be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_Initialize_Failure5_activeContestStatusId_notLong()
        throws Exception {
        context.addEntry("unitName", "contestManager");
        context.addEntry("activeContestStatusId", new Object());
        context.addEntry("defaultDocumentPathId", new Long(1));
        context.addEntry("documentContentManagerClassName",
            "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = beanUnderTest.getClass()
                                     .getDeclaredMethod("initialize",
                new Class[0]);

        method.setAccessible(true);

        try {
            method.invoke(beanUnderTest, new Object[0]);
            fail("ContestManagementException is expected.");
        } catch (InvocationTargetException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for private method initialize().
     * </p>
     *
     * <p>
     * Failure cause: If the defaultDocumentPathId is missing, ContestManagementException will be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_Initialize_Failure6_miss_defaultDocumentPathId()
        throws Exception {
        context.addEntry("unitName", "contestManager");
        context.addEntry("activeContestStatusId", new Long(1));
        // context.addEntry("defaultDocumentPathId", new Long(1));
        context.addEntry("documentContentManagerClassName",
            "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = beanUnderTest.getClass()
                                     .getDeclaredMethod("initialize",
                new Class[0]);

        method.setAccessible(true);

        try {
            method.invoke(beanUnderTest, new Object[0]);
            fail("ContestManagementException is expected.");
        } catch (InvocationTargetException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for private method initialize().
     * </p>
     *
     * <p>
     * Failure cause: If the defaultDocumentPathId isn't a Long value, ContestManagementException will be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_Initialize_Failure7_defaultDocumentPathId_notLong()
        throws Exception {
        context.addEntry("unitName", "contestManager");
        context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", new Object());
        context.addEntry("documentContentManagerClassName",
            "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = beanUnderTest.getClass()
                                     .getDeclaredMethod("initialize",
                new Class[0]);

        method.setAccessible(true);

        try {
            method.invoke(beanUnderTest, new Object[0]);
            fail("ContestManagementException is expected.");
        } catch (InvocationTargetException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for private method initialize().
     * </p>
     *
     * <p>
     * Failure cause: If the loggerName is an empty string, ContestManagementException will be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_Initialize_Failure8_loggerName_empty()
        throws Exception {
        context.addEntry("unitName", "contestManager");
        context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", new Object());
        context.addEntry("loggerName", " ");
        context.addEntry("documentContentManagerClassName",
            "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = beanUnderTest.getClass()
                                     .getDeclaredMethod("initialize",
                new Class[0]);

        method.setAccessible(true);

        try {
            method.invoke(beanUnderTest, new Object[0]);
            fail("ContestManagementException is expected.");
        } catch (InvocationTargetException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for private method initialize().
     * </p>
     *
     * <p>
     * Failure cause: If the documentContentManagerClassName's class doesn't exist, ContestManagementException will be
     * thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_Initialize_Failure9_miss_documentManagerClassName()
        throws Exception {
        context.addEntry("unitName", "contestManager");
        context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", new Long(1));
        context.addEntry("documentContentManagerClassName",
            "SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = beanUnderTest.getClass()
                                     .getDeclaredMethod("initialize",
                new Class[0]);

        method.setAccessible(true);

        try {
            method.invoke(beanUnderTest, new Object[0]);
            fail("ContestManagementException is expected.");
        } catch (InvocationTargetException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for private method initialize().
     * </p>
     *
     * <p>
     * Failure cause: If the documentContentManagerClassName's class is not a DocumentContentManager,
     * ContestManagementException will be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_Initialize_Failure10_documentContentManagerClassName_type_wrong()
        throws Exception {
        context.addEntry("unitName", "contestManager");
        context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", new Long(1));
        context.addEntry("documentContentManagerClassName", "java.lang.String");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = beanUnderTest.getClass()
                                     .getDeclaredMethod("initialize",
                new Class[0]);

        method.setAccessible(true);

        try {
            method.invoke(beanUnderTest, new Object[0]);
            fail("ContestManagementException is expected.");
        } catch (InvocationTargetException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for private method initialize().
     * </p>
     *
     * <p>
     * Failure cause: If the documentContentManagerClassName's class doesn't have a constructor using parameter
     * Map&ltString, Object&gt, ContestManagementException will be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_Initialize_Failure11_documentContentManagerClassName_ctor_wrong()
        throws Exception {
        context.addEntry("unitName", "contestManager");
        context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", new Long(1));
        context.addEntry("documentContentManagerClassName",
            "com.topcoder.service.studio.contest.failuretests.mock.SocketDocumentContentManagerCtorWrong");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = beanUnderTest.getClass()
                                     .getDeclaredMethod("initialize",
                new Class[0]);

        method.setAccessible(true);

        try {
            method.invoke(beanUnderTest, new Object[0]);
            fail("ContestManagementException is expected.");
        } catch (InvocationTargetException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for private method initialize().
     * </p>
     *
     * <p>
     * Failure cause: The attributes for document content manager is invalid, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_Initialize_Failure12_SocketDocumentContentManager_attr_miss()
        throws Exception {
        context.addEntry("unitName", "contestManager");
        context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", new Long(1));
        context.addEntry("documentContentManagerClassName",
            "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");

        // context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = beanUnderTest.getClass()
                                     .getDeclaredMethod("initialize",
                new Class[0]);

        method.setAccessible(true);

        try {
            method.invoke(beanUnderTest, new Object[0]);
            fail("ContestManagementException is expected.");
        } catch (InvocationTargetException e) {
            // success
        }
    }

    /**
     * Sets all the required values studio file type.
     *
     * @param entity
     *            the entity to set
     */
    private static void populateStudioFileType(StudioFileType entity) {
        entity.setDescription("description");
        entity.setExtension("extension");
        entity.setImageFile(true);
        entity.setSort(10);
    }

    /**
     * Sets all the required values for contest category.
     *
     * @param entity
     *            the entity to be set
     */
    private static void populateContestChannel(ContestChannel entity) {
        entity.setDescription("description");
        entity.setContestChannelId(18L);
    }

    /**
     * Sets all the required values for contest type.
     *
     * @param contestType
     *            the entity to be set.
     */
    private static void populateContestType(ContestType contestType) {
        contestType.setDescription("description");
        contestType.setRequirePreviewFile(true);
        contestType.setRequirePreviewImage(true);
        contestType.setContestType(1L);
    }

    /**
     * <p>
     * Creates a contest for testing.
     * </p>
     *
     * @return a contest for testing.
     */
    private Contest createContestForTest() {
        EntityManager em = com.topcoder.service.studio.contest.bean.MockEntityManager.EMF.createEntityManager();
        em.getTransaction().begin();

        StudioFileType fileType = new StudioFileType();
        populateStudioFileType(fileType);
        em.persist(fileType);

        ContestChannel channel = new ContestChannel();
        populateContestChannel(channel);
        em.persist(channel);

        ContestType contestType = new ContestType();
        populateContestType(contestType);
        contestType.setContestType(1L);
        em.persist(contestType);

        ContestStatus status = new ContestStatus();
        status.setDescription("description");
        status.setName("Name");
        status.setContestStatusId(10L);
        status.setStatusId(1L);
        em.persist(status);

        Date date = new Date();
        ContestGeneralInfo generalInfo = new ContestGeneralInfo();
        generalInfo.setBrandingGuidelines("guideline");
        generalInfo.setDislikedDesignsWebsites("disklike");
        generalInfo.setGoals("goal");
        generalInfo.setOtherInstructions("instruction");
        generalInfo.setTargetAudience("target audience");
        generalInfo.setWinningCriteria("winning criteria");

        ContestMultiRoundInformation multiRoundInformation = new ContestMultiRoundInformation();
        multiRoundInformation.setMilestoneDate(new Date());
        multiRoundInformation.setRoundOneIntroduction("round one");
        multiRoundInformation.setRoundTwoIntroduction("round two");

        ContestSpecifications specifications = new ContestSpecifications();
        specifications.setAdditionalRequirementsAndRestrictions("none");
        specifications.setColors("white");
        specifications.setFonts("Arial");
        specifications.setLayoutAndSize("10px");

        PrizeType prizeType = new PrizeType();
        prizeType.setDescription("Good");
        prizeType.setPrizeTypeId(1L);
        em.persist(prizeType);

        MilestonePrize milestonePrize = new MilestonePrize();
        milestonePrize.setAmount(10.0);
        milestonePrize.setCreateDate(new Date());
        milestonePrize.setNumberOfSubmissions(1);
        milestonePrize.setType(prizeType);

        Contest entity = new Contest();

        entity.setContestChannel(channel);
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
        entity.setTcDirectProjectId(1L);
        entity.setWinnerAnnoucementDeadline(date);
        entity.setGeneralInfo(generalInfo);
        entity.setSpecifications(specifications);
        entity.setMultiRoundInformation(multiRoundInformation);
        entity.setMilestonePrize(milestonePrize);

        em.getTransaction().commit();

        em.close();
        return entity;
    }

    /**
     * <p>
     * Failure test for createContest(Contest).
     * </p>
     *
     * <p>
     * If the contest is null, IllegalArgumentException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_CreateContest_Failure1_null() throws Exception {
        try {
            beanUnderTest.createContest(null);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for createContest(Contest).
     * </p>
     *
     * <p>
     * If the contest is already in the persistence, EntityAlreadyExistsException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_CreateContest_Failure2_EntityAlreadyExistsException()
        throws Exception {
        try {
            initContext();

            Contest contest = createContestForTest();

            beanUnderTest.createContest(contest);

            // Persist it again.
            beanUnderTest.createContest(contest);

            fail("EntityAlreadyExistsException is expected.");
        } catch (EntityAlreadyExistsException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for createContest(Contest).
     * </p>
     *
     * <p>
     * If the EntityManager is closed, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_CreateContest_Failure3_EntityManagerClosed()
        throws Exception {
        try {
            initContext();

            entityManager.close();

            Contest contest = createContestForTest();

            beanUnderTest.createContest(contest);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for createContest(Contest).
     * </p>
     *
     * <p>
     * If any persistence exception is thrown, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_CreateContest_Failure4_PersistenceException()
        throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);

            Contest contest = createContestForTest();

            beanUnderTest.createContest(contest);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for createContest(Contest).
     * </p>
     *
     * <p>
     * If any transaction exception is thrown, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_CreateContest_Failure5_TransactionException()
        throws Exception {
        try {
            initContext();

            entityManager.enableTransactionException(true);

            Contest contest = createContestForTest();

            beanUnderTest.createContest(contest);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for getContest(long).
     * </p>
     *
     * <p>
     * If the EntityManager has been closed, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_GetContest_Failure1_EntityManagerClosed()
        throws Exception {
        initContext();

        EntityManager em = (EntityManager) context.lookup("contestManager");
        em.close();

        try {
            beanUnderTest.getContest(1000);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for getContest(long).
     * </p>
     *
     * <p>
     * If any persistence exception is thrown from EntityManager, ContestManagementException is thrown instead.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_GetContest_Failure2_PersistenceException()
        throws Exception {
        initContext();

        entityManager.enablePersistenceException(true);

        try {
            beanUnderTest.getContest(1000);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for getContestsForProject(long).
     * </p>
     *
     * <p>
     * If the entity manager is closed, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_GetContestsForProject_Failure1_EMClosed()
        throws Exception {
        initContext();

        entityManager.close();

        try {
            beanUnderTest.getContestsForProject(1);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for getContestsForProject(long).
     * </p>
     *
     * <p>
     * If any persistence exception is thrown from EntityManager, ContestManagementException is thrown instead.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_GetContestsForProject_Failure2_PersistenceException()
        throws Exception {
        initContext();

        entityManager.enablePersistenceException(true);

        try {
            beanUnderTest.getContestsForProject(1);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for updateContest(Contest contest).
     * </p>
     *
     * <p>
     * If the contest status doesn't exists, EntityNotFoundException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_updateContest_Failure1() throws Exception {
        try {
            initContext();

            Contest contest = createContestForTest();
            beanUnderTest.updateContest(contest, 1, "2", true);

            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for updateContest(Contest contest).
     * </p>
     *
     * <p>
     * If the entityManager is closed, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_updateContest_Failure2() throws Exception {
        try {
            initContext();

            Contest contest = createContestForTest();
            beanUnderTest.createContest(contest);

            entityManager.close();

            contest.setName("sss");
            beanUnderTest.updateContest(contest, 1, "2", true);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for updateContest(Contest contest).
     * </p>
     *
     * <p>
     * If any transaction exception occurs, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_updateContest_Failure3() throws Exception {
        try {
            initContext();

            Contest contest = createContestForTest();
            beanUnderTest.createContest(contest);

            entityManager.enableTransactionException(true);

            contest.setName("sss");
            beanUnderTest.updateContest(contest, 1, "2", true);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for updateContest(Contest contest).
     * </p>
     *
     * <p>
     * If any persistence exception occurs, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_updateContest_Failure4() throws Exception {
        try {
            initContext();

            Contest contest = createContestForTest();
            beanUnderTest.createContest(contest);

            entityManager.enablePersistenceException(true);

            contest.setName("sss");
            beanUnderTest.updateContest(contest, 1, "2", true);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for getClientForContest(long).
     * </p>
     *
     * <p>
     * If the entity manager is closed, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_GetClientForContest_Failure1() throws Exception {
        try {
            initContext();

            entityManager.close();

            beanUnderTest.getClientForContest(1);
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for getClientForContest(long).
     * </p>
     *
     * <p>
     * If any persistence exception occurs, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_GetClientForContest_Failure2() throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);

            beanUnderTest.getClientForContest(1);
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for getClientForContest(long).
     * </p>
     *
     * <p>
     * If the contest doesn't exist, EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_GetClientForContest_Failure3() throws Exception {
        try {
            initContext();

            beanUnderTest.getClientForContest(1);
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for getClientForContest(long).
     * </p>
     *
     * <p>
     * If the project doesn't exist, EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_GetClientForContest_Failure4() throws Exception {
        try {
            initContext();

            Contest contest = createContestForTest();
            beanUnderTest.createContest(contest);

            beanUnderTest.getClientForContest(contest.getContestId());
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for getClientForProject(long).
     * </p>
     *
     * <p>
     * If the project doesn't exist, EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_GetClientForProject_Failure1() throws Exception {
        try {
            initContext();

            beanUnderTest.getClientForProject(999);
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for getClientForProject(long).
     * </p>
     *
     * <p>
     * If the entityManager is closed, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_GetClientForProject_Failure2() throws Exception {
        try {
            initContext();

            Project project = new Project();
            project.setCreateDate(new Date());
            project.setDescription("desc");
            project.setModifyDate(new Date());
            project.setName("name");
            project.setUserId(1);
            entityManager.persist(project);

            entityManager.close();

            beanUnderTest.getClientForProject(project.getProjectId());
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for getClientForProject(long).
     * </p>
     *
     * <p>
     * If any persistence exception is thrown, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_GetClientForProject_Failure3() throws Exception {
        try {
            initContext();

            Project project = new Project();
            project.setCreateDate(new Date());
            project.setDescription("desc");
            project.setModifyDate(new Date());
            project.setName("name");
            project.setUserId(1);
            entityManager.persist(project);

            entityManager.enablePersistenceException(true);

            beanUnderTest.getClientForProject(project.getProjectId());
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for addContestStatus(ContestStatus).
     * </p>
     *
     * <p>
     * If the contest status already exists, EntityAlreadyExistException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_AddContestStatus_Failure1() throws Exception {
        try {
            initContext();

            ContestStatus status = new ContestStatus();
            status.setDescription("description");
            status.setName("Name");
            status.setContestStatusId(10L);
            status.setStatusId(1L);
            beanUnderTest.addContestStatus(status);

            beanUnderTest.addContestStatus(status);
            fail("EntityAlreadyExistsException is expected.");
        } catch (EntityAlreadyExistsException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for addContestStatus(ContestStatus).
     * </p>
     *
     * <p>
     * If the entityManager is closed, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_AddContestStatus_Failure2() throws Exception {
        try {
            initContext();

            entityManager.close();

            ContestStatus status = new ContestStatus();
            status.setDescription("description");
            status.setName("Name");
            status.setContestStatusId(10L);
            status.setStatusId(1L);
            beanUnderTest.addContestStatus(status);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for addContestStatus(ContestStatus).
     * </p>
     *
     * <p>
     * If any transaction exception occurs, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_AddContestStatus_Failure3() throws Exception {
        try {
            initContext();

            entityManager.enableTransactionException(true);

            ContestStatus status = new ContestStatus();
            status.setDescription("description");
            status.setName("Name");
            status.setContestStatusId(10L);
            status.setStatusId(1L);
            beanUnderTest.addContestStatus(status);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for addContestStatus(ContestStatus).
     * </p>
     *
     * <p>
     * If any persistence exception occurs, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_AddContestStatus_Failure4() throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);

            ContestStatus status = new ContestStatus();
            status.setDescription("description");
            status.setName("Name");
            status.setContestStatusId(10L);
            status.setStatusId(1L);
            beanUnderTest.addContestStatus(status);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for updateContestStatus(ContestStatus).
     * </p>
     *
     * <p>
     * If the contest status doesn't exists, EntityNotFoundException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_UpdateContestStatus_Failure1() throws Exception {
        try {
            initContext();

            ContestStatus status = new ContestStatus();
            status.setDescription("description");
            status.setName("Name");
            status.setContestStatusId(10L);
            status.setStatusId(1L);
            beanUnderTest.updateContestStatus(status);

            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for updateContestStatus(ContestStatus).
     * </p>
     *
     * <p>
     * If the entityManager is closed, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_UpdateContestStatus_Failure2() throws Exception {
        try {
            initContext();

            ContestStatus status = new ContestStatus();
            status.setDescription("description");
            status.setName("Name");
            status.setContestStatusId(10L);
            status.setStatusId(1L);
            beanUnderTest.addContestStatus(status);

            entityManager.close();

            status.setName("sss");
            beanUnderTest.updateContestStatus(status);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for updateContestStatus(ContestStatus).
     * </p>
     *
     * <p>
     * If any transaction exception occurs, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_UpdateContestStatus_Failure3() throws Exception {
        try {
            initContext();

            ContestStatus status = new ContestStatus();
            status.setDescription("description");
            status.setName("Name");
            status.setContestStatusId(10L);
            status.setStatusId(1L);
            beanUnderTest.addContestStatus(status);

            entityManager.enableTransactionException(true);

            status.setName("sss");
            beanUnderTest.updateContestStatus(status);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for updateContestStatus(ContestStatus).
     * </p>
     *
     * <p>
     * If any persistence exception occurs, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_UpdateContestStatus_Failure4() throws Exception {
        try {
            initContext();

            ContestStatus status = new ContestStatus();
            status.setDescription("description");
            status.setName("Name");
            status.setContestStatusId(10L);
            status.setStatusId(1L);
            beanUnderTest.addContestStatus(status);

            entityManager.enablePersistenceException(true);

            status.setName("sss");
            beanUnderTest.updateContestStatus(status);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for removeContestStatus(long).
     * </p>
     *
     * <p>
     * If the entityManager is closed, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_RemoveContestStatus_Failure1() throws Exception {
        try {
            initContext();

            entityManager.close();
            beanUnderTest.removeContestStatus(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for removeContestStatus(long).
     * </p>
     *
     * <p>
     * If any transaction exception occurs, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_RemoveContestStatus_Failure2() throws Exception {
        try {
            initContext();

            entityManager.enableTransactionException(true);
            beanUnderTest.removeContestStatus(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for removeContestStatus(long).
     * </p>
     *
     * <p>
     * If any persistence exception occurs, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_RemoveContestStatus_Failure3() throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);
            beanUnderTest.removeContestStatus(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for getContestStatus(long).
     * </p>
     *
     * <p>
     * If the entityManager is closed, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_GetContestStatus_Failure1() throws Exception {
        try {
            initContext();

            entityManager.close();
            beanUnderTest.getContestStatus(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for removeContestStatus(long).
     * </p>
     *
     * <p>
     * If any persistence exception occurs, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_GetContestStatus_Failure2() throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);
            beanUnderTest.getContestStatus(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * Create document for test.
     * @return document
     */
    private Document createDocumentForTest() {

        EntityManager em = com.topcoder.service.studio.contest.bean.MockEntityManager.EMF.createEntityManager();
        em.getTransaction().begin();

        FilePath path = new FilePath();
        path.setModifyDate(new Date());
        path.setPath("path");

        StudioFileType studioFileType = new StudioFileType();
        studioFileType.setDescription("description");
        studioFileType.setExtension("extension");
        studioFileType.setImageFile(true);
        studioFileType.setSort(1);
        em.persist(studioFileType);

        MimeType mimeType = new MimeType();
        mimeType.setDescription("description");
        mimeType.setStudioFileType(studioFileType);
        mimeType.setMimeTypeId(1L);
        em.persist(mimeType);

        DocumentType type = new DocumentType();
        type.setDescription("description");
        type.setDocumentTypeId(1L);
        em.persist(type);

        em.getTransaction().commit();
        em.close();

        Document document = new Document();
        document.setOriginalFileName("originalFileName");
        document.setSystemFileName("systemFileName");
        document.setPath(path);
        document.setMimeType(mimeType);
        document.setType(type);
        document.setCreateDate(new Date());

        return document;
    }

    /**
     * <p>
     * Failure test for addDocument(Document).
     * </p>
     *
     * <p>
     * If the document already exist, EntityAlreadyExistsException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_AddDocument_Failure1() throws Exception {
        try {
            initContext();

            Document document = createDocumentForTest();
            beanUnderTest.addDocument(document);
            beanUnderTest.addDocument(document);
            fail("EntityAlreadyExistsException is expected.");
        } catch (EntityAlreadyExistsException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for addDocument(Document).
     * </p>
     *
     * <p>
     * If entity manager is closed, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_AddDocument_Failure2() throws Exception {
        try {
            initContext();

            entityManager.close();

            Document document = createDocumentForTest();

            beanUnderTest.addDocument(document);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for addDocument(Document).
     * </p>
     *
     * <p>
     * If any transaction exception occurs, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_AddDocument_Failure3() throws Exception {
        try {
            initContext();

            entityManager.enableTransactionException(true);

            Document document = createDocumentForTest();

            beanUnderTest.addDocument(document);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for addDocument(Document).
     * </p>
     *
     * <p>
     * If any persistence exception occurs, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_AddDocument_Failure4() throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);

            Document document = createDocumentForTest();

            beanUnderTest.addDocument(document);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for updateDocument(Document).
     * </p>
     *
     * <p>
     * If the document does not exist, EntityNotFoundException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_UpdateDocument_Failure1() throws Exception {
        try {
            initContext();

            Document document = createDocumentForTest();

            beanUnderTest.updateDocument(document);

            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for updateDocument(Document).
     * </p>
     *
     * <p>
     * If the entityManager is closed, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_UpdateDocument_Failure2() throws Exception {
        try {
            initContext();


            Document document = createDocumentForTest();
            beanUnderTest.addDocument(document);
            entityManager.close();

            beanUnderTest.updateDocument(document);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for updateDocument(Document).
     * </p>
     *
     * <p>
     * If any transaction exception occurs, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_UpdateDocument_Failure3() throws Exception {
        try {
            initContext();

            Document document = createDocumentForTest();
            beanUnderTest.addDocument(document);

            entityManager.enableTransactionException(true);

            beanUnderTest.updateDocument(document);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for updateDocument(Document).
     * </p>
     *
     * <p>
     * If any persistence exception occurs, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_UpdateDocument_Failure4() throws Exception {
        try {
            initContext();

            Document document = createDocumentForTest();
            beanUnderTest.addDocument(document);

            entityManager.enablePersistenceException(true);
            beanUnderTest.updateDocument(document);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for getDocument(long).
     * </p>
     *
     * <p>
     * If the entityManager is closed, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_GetDocument_Failure1() throws Exception {
        try {
            initContext();

            entityManager.close();
            beanUnderTest.getDocument(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for removeDocument(long).
     * </p>
     *
     * <p>
     * If any persistence exception occurs, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_GetDocument_Failure2() throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);
            beanUnderTest.getDocument(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for removeDocument(long).
     * </p>
     *
     * <p>
     * If the entityManager is closed, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_RemoveDocument_Failure1() throws Exception {
        try {
            initContext();

            entityManager.close();
            beanUnderTest.removeDocument(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for removeDocument(long).
     * </p>
     *
     * <p>
     * If any transaction exception occurs, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_RemoveDocument_Failure2() throws Exception {
        try {
            initContext();

            entityManager.enableTransactionException(true);
            beanUnderTest.removeDocument(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for removeDocument(long).
     * </p>
     *
     * <p>
     * If any persistence exception occurs, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_RemoveDocument_Failure3() throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);
            beanUnderTest.removeDocument(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for addDocumentToContest(long, long).
     * </p>
     *
     * <p>
     * If the document doesn't exist, EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_AddDocumentToContest_Failure1() throws Exception {
        try {
            initContext();

            Contest contest = createContestForTest();
            beanUnderTest.createContest(contest);

            beanUnderTest.addDocumentToContest(1000, contest.getContestId());
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for addDocumentToContest(long, long).
     * </p>
     *
     * <p>
     * If the contest doesn't exist, EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_AddDocumentToContest_Failure2() throws Exception {
        try {

            this.initContext();
            Document document = this.createDocumentForTest();

            beanUnderTest.addDocument(document);

            beanUnderTest.addDocumentToContest(document.getDocumentId(), 1000);
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for addDocumentToContest(long, long).
     * </p>
     *
     * <p>
     * If the entityManager is closed, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_AddDocumentToContest_Failure3() throws Exception {
        try {
            initContext();

            entityManager.close();
            beanUnderTest.addDocumentToContest(1, 1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for addDocumentToContest(long, long).
     * </p>
     *
     * <p>
     * If any transaction exception occurs, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_AddDocumentToContest_Failure4() throws Exception {
        try {
            initContext();

            entityManager.enableTransactionException(true);
            beanUnderTest.addDocumentToContest(1, 1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for addDocumentToContest(long, long)(long).
     * </p>
     *
     * <p>
     * If any persistence exception occurs, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_AddDocumentToContest_Failure5() throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);
            beanUnderTest.addDocumentToContest(1, 1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for removeDocumentFromContest(long, long).
     * </p>
     *
     * <p>
     * If the document doesn't exist, EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_RemoveDocumentFromContest_Failure1()
        throws Exception {
        try {
            initContext();

            Contest contest = createContestForTest();
            beanUnderTest.createContest(contest);

            beanUnderTest.removeDocumentFromContest(1000, contest.getContestId());
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for removeDocumentFromContest(long, long).
     * </p>
     *
     * <p>
     * If the contest doesn't exist, EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_RemoveDocumentFromContest_Failure2()
        throws Exception {
        try {
            this.initContext();
            Document document = this.createDocumentForTest();

            beanUnderTest.addDocument(document);

            beanUnderTest.removeDocumentFromContest(document.getDocumentId(),
                1000);
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for removeDocumentFromContest(long, long).
     * </p>
     *
     * <p>
     * If the entityManager is closed, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_removeDocumentFromContest_Failure3()
        throws Exception {
        try {
            initContext();

            entityManager.close();
            beanUnderTest.removeDocumentFromContest(1, 1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for removeDocumentFromContest(long, long).
     * </p>
     *
     * <p>
     * If any transaction exception occurs, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_removeDocumentFromContest_Failure4()
        throws Exception {
        try {
            initContext();

            entityManager.enableTransactionException(true);
            beanUnderTest.removeDocumentFromContest(1, 1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for removeDocumentFromContest(long, long)(long).
     * </p>
     *
     * <p>
     * If any persistence exception occurs, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_removeDocumentFromContest_Failure5()
        throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);
            beanUnderTest.removeDocumentFromContest(1, 1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * Create ContestChannel for test.
     * @return ContestChannel
     */
    private ContestChannel createContestChannelForTest() {

        ContestChannel channel = new ContestChannel();
        channel.setContestChannelId(1L);
        channel.setDescription("desc");
        return channel;
    }

    /**
     * <p>
     * Failure test for addContestChannel(ContestChannel).
     * </p>
     *
     * <p>
     * If the ContestChannel already exist, EntityAlreadyExistsException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_AddContestChannel_Failure1() throws Exception {
        try {
            initContext();

            ContestChannel channel = createContestChannelForTest();

            beanUnderTest.addContestChannel(channel);

            beanUnderTest.addContestChannel(channel);
            fail("EntityAlreadyExistsException is expected.");
        } catch (EntityAlreadyExistsException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for addContestChannel(ContestChannel).
     * </p>
     *
     * <p>
     * If entity manager is closed, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_AddContestChannel_Failure3() throws Exception {
        try {
            initContext();

            ContestChannel channel = createContestChannelForTest();

            entityManager.close();

            beanUnderTest.addContestChannel(channel);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for addContestChannel(ContestChannel).
     * </p>
     *
     * <p>
     * If any transaction exception occurs, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_AddContestChannel_Failure4() throws Exception {
        try {
            initContext();

            ContestChannel channel = createContestChannelForTest();
            entityManager.enableTransactionException(true);

            beanUnderTest.addContestChannel(channel);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for addContestChannel(ContestChannel).
     * </p>
     *
     * <p>
     * If any persistence exception occurs, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_AddContestChannel_Failure5() throws Exception {
        try {
            initContext();

            ContestChannel channel = createContestChannelForTest();

            entityManager.enablePersistenceException(true);
            beanUnderTest.addContestChannel(channel);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for updateContestChannel(ContestChannel).
     * </p>
     *
     * <p>
     * If the ContestChannel doesn't exists, EntityNotFoundException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_UpdateContestChannel_Failure1() throws Exception {
        try {
            initContext();

            ContestChannel channel = createContestChannelForTest();

            // bean.addContestChannel(channel);
            beanUnderTest.updateContestChannel(channel);

            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for updateContestChannel(ContestChannel).
     * </p>
     *
     * <p>
     * If the entityManager is closed, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_UpdateContestChannel_Failure2() throws Exception {
        try {
            initContext();

            ContestChannel channel = createContestChannelForTest();
            beanUnderTest.addContestChannel(channel);

            entityManager.close();

            beanUnderTest.updateContestChannel(channel);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for updateContestChannel(ContestChannel).
     * </p>
     *
     * <p>
     * If any transaction exception occurs, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_UpdateContestChannel_Failure3() throws Exception {
        try {
            initContext();

            ContestChannel channel = createContestChannelForTest();

            beanUnderTest.addContestChannel(channel);

            entityManager.enableTransactionException(true);

            beanUnderTest.updateContestChannel(channel);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for updateContestChannel(ContestChannel).
     * </p>
     *
     * <p>
     * If any persistence exception occurs, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_UpdateContestChannel_Failure4() throws Exception {
        try {
            initContext();

            ContestChannel channel = createContestChannelForTest();

            beanUnderTest.addContestChannel(channel);

            entityManager.enablePersistenceException(true);
            beanUnderTest.updateContestChannel(channel);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for getContestChannel(long).
     * </p>
     *
     * <p>
     * If the entityManager is closed, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_GetContestChannel_Failure1() throws Exception {
        try {
            initContext();

            entityManager.close();
            beanUnderTest.getContestChannel(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for removeContestChannel(long).
     * </p>
     *
     * <p>
     * If any persistence exception occurs, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_GetContestChannel_Failure2() throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);
            beanUnderTest.getContestChannel(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for removeContestChannel(long).
     * </p>
     *
     * <p>
     * If the entityManager is closed, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_RemoveContestChannel_Failure1() throws Exception {
        try {
            initContext();

            entityManager.close();
            beanUnderTest.removeContestChannel(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for removeContestChannel(long).
     * </p>
     *
     * <p>
     * If any transaction exception occurs, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_RemoveContestChannel_Failure2() throws Exception {
        try {
            initContext();

            entityManager.enableTransactionException(true);
            beanUnderTest.removeContestChannel(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for removeContestChannel(long).
     * </p>
     *
     * <p>
     * If any persistence exception occurs, ContestManagementException is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_RemoveContestChannel_Failure3() throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);
            beanUnderTest.removeContestChannel(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for saveDocumentContent(long, byte[]).
     * </p>
     *
     * <p>
     * If the content is empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_SaveDocumentContent_Failure1() throws Exception {
        SocketDocumentContentServer server = new SocketDocumentContentServer(40000,
                0);
        server.start();

        try {

            this.initContext();
            Document document = this.createDocumentForTest();

            beanUnderTest.addDocument(document);

            byte[] content = new byte[0];

            // It should process successfully.
            beanUnderTest.saveDocumentContent(document.getDocumentId(), content);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        } finally {
            server.stop();
            Thread.sleep(1000);
        }
    }

    /**
     * <p>
     * Failure test for saveDocumentContent(long, byte[]).
     * </p>
     *
     * <p>
     * If the document with specified id doesn't exist, EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_SaveDocumentContent_Failure2() throws Exception {
        try {
            initContext();

            byte[] content = new byte[4];
            content[0] = 1;
            content[1] = 1;
            content[2] = 1;
            content[3] = 1;

            // It should process successfully.
            beanUnderTest.saveDocumentContent(100, content);
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for saveDocumentContent(long, byte[]).
     * </p>
     *
     * <p>
     * If the entity manager is closed, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_SaveDocumentContent_Failure3() throws Exception {
        try {
            initContext();

            Document document = this.createDocumentForTest();

            beanUnderTest.addDocument(document);

            byte[] content = new byte[1];
            content[0] = 1;

            entityManager.close();

            // It should process successfully.
            beanUnderTest.saveDocumentContent(document.getDocumentId(), content);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for saveDocumentContent(long, byte[]).
     * </p>
     *
     * <p>
     * If the any transaction exception occurs, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_SaveDocumentContent_Failure4() throws Exception {
        try {
            initContext();

            Document document = this.createDocumentForTest();
            beanUnderTest.addDocument(document);

            byte[] content = new byte[1];
            content[0] = 1;

            entityManager.enableTransactionException(true);

            // It should process successfully.
            beanUnderTest.saveDocumentContent(document.getDocumentId(), content);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for saveDocumentContent(long, byte[]).
     * </p>
     *
     * <p>
     * If the any persistence exception occurs, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_SaveDocumentContent_Failure5() throws Exception {
        try {
            initContext();

            Document document = this.createDocumentForTest();

            beanUnderTest.addDocument(document);

            byte[] content = new byte[1];
            content[0] = 1;

            entityManager.enablePersistenceException(true);

            // It should process successfully.
            beanUnderTest.saveDocumentContent(document.getDocumentId(), content);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for saveDocumentContent(long, byte[]).
     * </p>
     *
     * <p>
     * If the server doesn't open, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_SaveDocumentContent_Failure6() throws Exception {
        try {
            initContext();

            Document document = this.createDocumentForTest();

            beanUnderTest.addDocument(document);

            byte[] content = new byte[1];
            content[0] = 1;

            // It should process successfully.
            beanUnderTest.saveDocumentContent(document.getDocumentId(), content);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure1 test for getAllContestStatuses().
     * </p>
     *
     * <p>
     * If the entity manager is closed, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_GetAllContestStatuses_Failure1() throws Exception {
        try {
            initContext();

            entityManager.close();

            beanUnderTest.getAllContestStatuses();
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure1 test for getAllContestStatuses().
     * </p>
     *
     * <p>
     * If any transaction exception throws, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_GetAllContestStatuses_Failure2() throws Exception {
        try {
            initContext();

            entityManager.enableTransactionException(true);

            beanUnderTest.getAllContestStatuses();
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure1 test for getAllContestStatuses().
     * </p>
     *
     * <p>
     * If any persistence exception throws, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_GetAllContestStatuses_Failure3() throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);

            beanUnderTest.getAllContestStatuses();
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure1 test for getAllContestChannels().
     * </p>
     *
     * <p>
     * If the entity manager is closed, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_GetAllContestChannels_Failure1() throws Exception {
        try {
            initContext();

            entityManager.close();

            beanUnderTest.getAllContestChannels();
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure1 test for getAllContestChannels().
     * </p>
     *
     * <p>
     * If any persistence exception throws, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_GetAllContestChannels_Failure2() throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);

            beanUnderTest.getAllContestChannels();
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure1 test for getAllStudioFileTypes().
     * </p>
     *
     * <p>
     * If the entity manager is closed, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_GetAllStudioFileTypes_Failure1() throws Exception {
        try {
            initContext();

            entityManager.close();

            beanUnderTest.getAllStudioFileTypes();
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure1 test for getAllStudioFileTypes().
     * </p>
     *
     * <p>
     * If any persistence exception throws, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_GetAllStudioFileTypes_Failure2() throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);

            beanUnderTest.getAllStudioFileTypes();
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for addPrizeToContest(long, long).
     * </p>
     *
     * <p>
     * If the prize with specified id doesn't exist, EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_AddPrizeToContest_Failure1() throws Exception {
        try {
            initContext();

            Contest contest = createContestForTest();
            beanUnderTest.createContest(contest);

            // It should process successfully.
            beanUnderTest.addPrizeToContest(contest.getContestId(), 1000);

            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * Create Prize for test.
     * @return Prize
     */
    private Prize createPrizeForTest() {

        EntityManager em = com.topcoder.service.studio.contest.bean.MockEntityManager.EMF.createEntityManager();
        em.getTransaction().begin();

        PrizeType prizeType = new PrizeType();
        prizeType.setDescription("desc");
        prizeType.setPrizeTypeId(2L);
        em.persist(prizeType);

        Prize prize = new Prize();
        prize.setAmount(new Double(100));
        prize.setCreateDate(new Date());
        prize.setPlace(new Integer(1));
        prize.setType(prizeType);
        em.persist(prize);
        em.getTransaction().commit();
        em.close();

        return prize;
    }

    /**
     * <p>
     * Failure test for addPrizeToContest(long, long).
     * </p>
     *
     * <p>
     * If the contest with specified id doesn't exist, EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_AddPrizeToContest_Failure2() throws Exception {
        try {
            initContext();

            Prize prize = createPrizeForTest();

            // It should process successfully.
            beanUnderTest.addPrizeToContest(100, prize.getPrizeId());

            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for addPrizeToContest(long, long).
     * </p>
     *
     * <p>
     * If entityManager is closed, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_AddPrizeToContest_Failure3() throws Exception {
        try {
            initContext();

            Contest contest = createContestForTest();
            beanUnderTest.createContest(contest);

            Prize prize = createPrizeForTest();

            entityManager.close();
            // It should process successfully.
            beanUnderTest.addPrizeToContest(contest.getContestId(),
                prize.getPrizeId());

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for addPrizeToContest(long, long).
     * </p>
     *
     * <p>
     * If any transaction exception occurs, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_AddPrizeToContest_Failure4() throws Exception {
        try {
            initContext();

            Contest contest = createContestForTest();
            beanUnderTest.createContest(contest);

            Prize prize = createPrizeForTest();

            entityManager.enableTransactionException(true);

            // It should process successfully.
            beanUnderTest.addPrizeToContest(contest.getContestId(),
                prize.getPrizeId());

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for addPrizeToContest(long, long).
     * </p>
     *
     * <p>
     * If any persistence exception occurs, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_AddPrizeToContest_Failure5() throws Exception {
        try {
            initContext();

            Contest contest = createContestForTest();
            beanUnderTest.createContest(contest);

            Prize prize = createPrizeForTest();

            entityManager.enablePersistenceException(true);
            // It should process successfully.
            beanUnderTest.addPrizeToContest(contest.getContestId(),
                prize.getPrizeId());

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for removePrizeFromContest(long, long).
     * </p>
     *
     * <p>
     * If the prize with specified id doesn't exist, EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_RemovePrizeFromContest_Failure1()
        throws Exception {
        try {
            initContext();

            Contest contest = createContestForTest();
            beanUnderTest.createContest(contest);

            // It should process successfully.
            beanUnderTest.removePrizeFromContest(contest.getContestId(), 1000);

            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for removePrizeFromContest(long, long).
     * </p>
     *
     * <p>
     * If the contest with specified id doesn't exist, EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_RemovePrizeFromContest_Failure2()
        throws Exception {
        try {
            initContext();

            Prize prize = createPrizeForTest();

            // It should process successfully.
            beanUnderTest.removePrizeFromContest(100, prize.getPrizeId());

            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for removePrizeFromContest(long, long).
     * </p>
     *
     * <p>
     * If entityManager is closed, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_RemovePrizeFromContest_Failure3()
        throws Exception {
        try {
            initContext();

            Contest contest = createContestForTest();
            beanUnderTest.createContest(contest);

            Prize prize = createPrizeForTest();

            entityManager.close();
            // It should process successfully.
            beanUnderTest.removePrizeFromContest(contest.getContestId(),
                prize.getPrizeId());

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for removePrizeFromContest(long, long).
     * </p>
     *
     * <p>
     * If any transaction exception occurs, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_RemovePrizeFromContest_Failure4()
        throws Exception {
        try {
            initContext();

            Contest contest = createContestForTest();
            beanUnderTest.createContest(contest);

            Prize prize = createPrizeForTest();

            entityManager.enableTransactionException(true);

            // It should process successfully.
            beanUnderTest.removePrizeFromContest(contest.getContestId(),
                prize.getPrizeId());

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for removePrizeFromContest(long, long).
     * </p>
     *
     * <p>
     * If any persistence exception occurs, ContestManagementException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_RemovePrizeFromContest_Failure5()
        throws Exception {
        try {
            initContext();

            Contest contest = createContestForTest();
            beanUnderTest.createContest(contest);

            Prize prize = createPrizeForTest();

            entityManager.enablePersistenceException(true);
            // It should process successfully.
            beanUnderTest.removePrizeFromContest(contest.getContestId(),
                prize.getPrizeId());

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for getContestPrizes(long).
     * </p>
     *
     * <p>
     * There is a bug in Contest And Submission Entities for relation between Prize<->Contest, this would lead to the
     * method's implementation doesn't work.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_GetContestPrizes_Failure() throws Exception {
        // FIXME
    }

    /**
     * <p>
     * Failure test for <code>getDocumentContent(long, byte[])</code>.
     * </p>
     *
     * <p>
     * If the document with specified id doesn't exist, <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_GetDocumentContent_Failure1() throws Exception {
        try {
            initContext();

            // It should process successfully.
            beanUnderTest.getDocumentContent(100);
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>existDocumentContent(long)</code>.
     * </p>
     *
     * <p>
     * If the document doesn't exist, <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_ExistDocumentContent_Failure() throws Exception {
        try {
            initContext();

            // It should process successfully.
            beanUnderTest.existDocumentContent(100);
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }
}
