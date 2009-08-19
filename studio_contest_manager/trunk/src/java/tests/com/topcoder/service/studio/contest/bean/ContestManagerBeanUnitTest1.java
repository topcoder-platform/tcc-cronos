/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.bean;

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
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.Document;
import com.topcoder.service.studio.contest.EntityAlreadyExistsException;
import com.topcoder.service.studio.contest.EntityNotFoundException;
import com.topcoder.service.studio.contest.FilePath;
import com.topcoder.service.studio.contest.MimeType;
import com.topcoder.service.studio.contest.StudioFileType;


/**
 * <p>
 * Unit tests for <code>ContestManagerBean</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestManagerBeanUnitTest1 extends TestCase {
    /**
     * <p>The <code>ContestManagerBean</code> instance for testing.</p>
     */
    private ContestManagerBean bean;

    /**
     * <p>The mock <code>SessionContext</code> for simulating the ejb environment.</p>
     */
    private MockSessionContext context;

    /**
     * <p>The mock <code>EntityManager</code> for testing.</p>
     */
    private MockEntityManager entityManager;

    /**
     * <p>Sets up the environment.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        bean = new ContestManagerBean();

        context = new MockSessionContext();

        Field field = bean.getClass().getDeclaredField("sessionContext");
        field.setAccessible(true);
        field.set(bean, context);

        //new MockEntityManager().createQuery("delete from ContestChannel");
    }

    /**
     * <p>Destroy the environment.</p>
     */
    protected void tearDown() {
        new MockEntityManager().createNativeQuery("delete from contest_channel_lu");
    }

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestManagerBeanUnitTest1.class);
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

        Method method = bean.getClass()
                            .getDeclaredMethod("initialize", new Class[0]);

        method.setAccessible(true);
        method.invoke(bean, new Object[0]);

        entityManager = new MockEntityManager();
        context.addEntry("contestManager", entityManager);
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ContestManageBean()</code>.
     * </p>
     *
     * <p>
     * Verify that the ContestManagerBean can be created successfully.
     * </p>
     */
    public void testCtor() {
        bean = new ContestManagerBean();

        assertNotNull("Unable to create ContestManagerBean instance.", bean);
    }

    /**
     * <p>
     * Accuracy test for private method <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Verify that all fields are initialized properly.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize() throws Exception {
        context.addEntry("auditChange", new Boolean(false));
        context.addEntry("unitName", "contestManager");
        context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", new Long(1));
        context.addEntry("loggerName", "contestManager");
        context.addEntry("documentContentManagerClassName",
            "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = bean.getClass()
                            .getDeclaredMethod("initialize", new Class[0]);

        method.setAccessible(true);
        method.invoke(bean, new Object[0]);
    }

    /**
     * <p>
     * Failure test for private method <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the unitName is missing, <code>ContestManagementException</code> will
     * be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure1() throws Exception {
        context.addEntry("auditChange", new Boolean(false));
        context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", new Long(1));
        context.addEntry("loggerName", "contestManager");
        context.addEntry("documentContentManagerClassName",
            "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = bean.getClass()
                            .getDeclaredMethod("initialize", new Class[0]);

        method.setAccessible(true);

        try {
            method.invoke(bean, new Object[0]);
            fail("ContestManagementException is expected.");
        } catch (InvocationTargetException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for private method <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the unitName isn't instance of String, <code>ContestManagementException</code> will
     * be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure2() throws Exception {
        context.addEntry("auditChange", new Boolean(false));
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

        Method method = bean.getClass()
                            .getDeclaredMethod("initialize", new Class[0]);

        method.setAccessible(true);

        try {
            method.invoke(bean, new Object[0]);
            fail("ContestManagementException is expected.");
        } catch (InvocationTargetException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for private method <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the unitName is empty string, <code>ContestManagementException</code> will
     * be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure3() throws Exception {
        context.addEntry("auditChange", new Boolean(false));
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

        Method method = bean.getClass()
                            .getDeclaredMethod("initialize", new Class[0]);

        method.setAccessible(true);

        try {
            method.invoke(bean, new Object[0]);
            fail("ContestManagementException is expected.");
        } catch (InvocationTargetException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for private method <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the activeContestStatusId is missing, <code>ContestManagementException</code> will
     * be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure4() throws Exception {
        context.addEntry("auditChange", new Boolean(false));
        context.addEntry("unitName", "contestManager");
        //context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", new Long(1));
        context.addEntry("documentContentManagerClassName",
            "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = bean.getClass()
                            .getDeclaredMethod("initialize", new Class[0]);

        method.setAccessible(true);

        try {
            method.invoke(bean, new Object[0]);
            fail("ContestManagementException is expected.");
        } catch (InvocationTargetException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for private method <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the activeContestStatusId isn't a Long value, <code>ContestManagementException</code> will
     * be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure5() throws Exception {
        context.addEntry("auditChange", new Boolean(false));
        context.addEntry("unitName", "contestManager");
        context.addEntry("activeContestStatusId", new Object());
        context.addEntry("defaultDocumentPathId", new Long(1));
        context.addEntry("documentContentManagerClassName",
            "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = bean.getClass()
                            .getDeclaredMethod("initialize", new Class[0]);

        method.setAccessible(true);

        try {
            method.invoke(bean, new Object[0]);
            fail("ContestManagementException is expected.");
        } catch (InvocationTargetException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for private method <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the documentContentManagerClassName's class doesn't exist,
     * <code>ContestManagementException</code> will be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure6() throws Exception {
        context.addEntry("auditChange", new Boolean(false));
        context.addEntry("unitName", "contestManager");
        context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", new Long(1));
        context.addEntry("documentContentManagerClassName",
            "SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = bean.getClass()
                            .getDeclaredMethod("initialize", new Class[0]);

        method.setAccessible(true);

        try {
            method.invoke(bean, new Object[0]);
            fail("ContestManagementException is expected.");
        } catch (InvocationTargetException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for private method <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the documentContentManagerClassName's class doesn't have a
     * constructor using parameter Map&ltString, Object&gt,
     * <code>ContestManagementException</code> will be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure7() throws Exception {
        context.addEntry("auditChange", new Boolean(false));
        context.addEntry("unitName", "contestManager");
        context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", new Long(1));
        context.addEntry("documentContentManagerClassName", "java.lang.String");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = bean.getClass()
                            .getDeclaredMethod("initialize", new Class[0]);

        method.setAccessible(true);

        try {
            method.invoke(bean, new Object[0]);
            fail("ContestManagementException is expected.");
        } catch (InvocationTargetException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for private method <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: The attributes for document content manager is invalid,
     * <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure8() throws Exception {
        context.addEntry("unitName", "contestManager");
        context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", new Long(1));
        context.addEntry("documentContentManagerClassName",
            "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");

        //context.addEntry("serverAddress", "127.0.0.1");
        //context.addEntry("serverPort", new Integer(40000));
        Method method = bean.getClass()
                            .getDeclaredMethod("initialize", new Class[0]);

        method.setAccessible(true);

        try {
            method.invoke(bean, new Object[0]);
            fail("ContestManagementException is expected.");
        } catch (InvocationTargetException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for private method <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: The document content manager class is an abstract class,
     * <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure9() throws Exception {
        context.addEntry("unitName", "contestManager");
        context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", new Long(1));
        context.addEntry("documentContentManagerClassName",
            "com.topcoder.service.studio.contest.bean.MockAbstractDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = bean.getClass()
                            .getDeclaredMethod("initialize", new Class[0]);

        method.setAccessible(true);

        try {
            method.invoke(bean, new Object[0]);
            fail("ContestManagementException is expected.");
        } catch (InvocationTargetException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for private method <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: The document content manager class is not instance of DocumentContentManager,
     * <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure10() throws Exception {
        context.addEntry("unitName", "contestManager");
        context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", new Long(1));
        context.addEntry("documentContentManagerClassName",
            "com.topcoder.service.studio.contest.bean.MockContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = bean.getClass()
                            .getDeclaredMethod("initialize", new Class[0]);

        method.setAccessible(true);

        try {
            method.invoke(bean, new Object[0]);
            fail("ContestManagementException is expected.");
        } catch (InvocationTargetException e) {
            // success
        }
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

        ContestType contestType = new ContestType();
        contestType.setDescription("desc");
        contestType.setRequirePreviewFile(false);
        contestType.setRequirePreviewImage(false);

        ContestStatus status = new ContestStatus();
        status.setDescription("description");
        status.setName("name");

        Date date = new Date();

        Contest contest = new Contest();
        contest.setName("contest1");
        contest.setCreatedUser(new Long(1));
        contest.setContestChannel(contestChannel);
        contest.setContestType(contestType);
        contest.setEndDate(date);
        contest.setEventId(new Long(1));
        contest.setStatusId(1L);
        contest.setForumId(new Long(1));
        contest.setProjectId(new Long(1));
        contest.setTcDirectProjectId(new Long(1));
        contest.setStartDate(new Date());
        contest.setWinnerAnnoucementDeadline(new Date());

        return contest;
    }

    /**
     * <p>
     * Accuracy test for <code>createContest(Contest)</code>.
     * </p>
     *
     * <p>
     * Verify that the contest is added to the persistence.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateContest() throws Exception {
        initContext();

        Contest contest = createContestForTest();

        bean.createContest(contest);

        Contest persist = bean.getContest(contest.getContestId());
        assertEquals("The persist should match", "contest1", persist.getName());
    }

    /**
     * <p>
     * Failure test for <code>createContest(Contest)</code>.
     * </p>
     *
     * <p>
     * If the contest is null, <code>IllegalArgumentException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateContest_Failure1() throws Exception {
        try {
            bean.createContest(null);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>createContest(Contest)</code>.
     * </p>
     *
     * <p>
     * If the contest is already in the persistence, <code>EntityAlreadyExistsException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateContest_Failure2() throws Exception {
        try {
            initContext();

            Contest contest = createContestForTest();

            bean.createContest(contest);

            // Persist it again.
            bean.createContest(contest);

            fail("EntityAlreadyExistsException is expected.");
        } catch (EntityAlreadyExistsException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>createContest(Contest)</code>.
     * </p>
     *
     * <p>
     * If the EntityManager is closed, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateContest_Failure3() throws Exception {
        try {
            initContext();

            entityManager.close();

            Contest contest = createContestForTest();

            bean.createContest(contest);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>createContest(Contest)</code>.
     * </p>
     *
     * <p>
     * If any persistence exception is thrown, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateContest_Failure4() throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);

            Contest contest = createContestForTest();

            bean.createContest(contest);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>createContest(Contest)</code>.
     * </p>
     *
     * <p>
     * If any transaction exception is thrown, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateContest_Failure5() throws Exception {
        try {
            initContext();

            entityManager.enableTransactionException(true);

            Contest contest = createContestForTest();

            bean.createContest(contest);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getContest(long)</code>.
     * </p>
     *
     * <p>
     * If the contest with specified id doesn't exist, null is returned.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetContest() throws Exception {
        initContext();

        Contest contest = bean.getContest(1000);

        assertNull("The contest should be null if doesn't exist.", contest);
    }

    /**
     * <p>
     * Accuracy test for <code>getContest(long)</code>.
     * </p>
     *
     * <p>
     * If the contest with specified id exists, the contest should be returned successfully.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetContest1() throws Exception {
        initContext();

        Contest contest = createContestForTest();
        bean.createContest(contest);

        Contest persist = bean.getContest(contest.getContestId());

        assertEquals("The contest should match.", contest.getName(),
            persist.getName());
    }

    /**
     * <p>
     * Failure test for <code>getContest(long)</code>.
     * </p>
     *
     * <p>
     * If the EntityManager has been closed, <code>ContestManagementException</code>
     * is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetContest_Failure1() throws Exception {
        initContext();

        EntityManager em = (EntityManager) context.lookup("contestManager");
        em.close();

        try {
            bean.getContest(1000);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>getContest(long)</code>.
     * </p>
     *
     * <p>
     * If any persistence exception is thrown from EntityManager, <code>ContestManagementException</code>
     * is thrown instead.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetContest_Failure2() throws Exception {
        initContext();

        entityManager.enablePersistenceException(true);

        try {
            bean.getContest(1000);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>getContestsForProject(long)</code>.
     * </p>
     *
     * <p>
     * If the entity manager is closed, <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetContestsForProject_Failure1() throws Exception {
        initContext();

        entityManager.close();

        try {
            bean.getContestsForProject(1);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getClientForContest(long)</code>.
     * </p>
     *
     * <p>
     * Verify that the client for the specified contest is returned.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetClientForContest() throws Exception {
        initContext();

        Project project = new Project();
        project.setCreateDate(new Date());
        project.setDescription("desc");
        project.setModifyDate(new Date());
        project.setName("name");
        project.setUserId(1);
        entityManager.persist(project);

        Contest contest = createContestForTest();
        contest.setTcDirectProjectId(project.getProjectId());
        bean.createContest(contest);

        long id = bean.getClientForContest(contest.getContestId());
        assertEquals("The user should be 1.", 1, id);
    }

    /**
     * <p>
     * Failure test for <code>getClientForContest(long)</code>.
     * </p>
     *
     * <p>
     * If the entity manager is closed, <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetClientForContest_Failure1() throws Exception {
        try {
            initContext();

            entityManager.close();

            bean.getClientForContest(1);
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>getClientForContest(long)</code>.
     * </p>
     *
     * <p>
     * If any persistence exception occurs, <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetClientForContest_Failure2() throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);

            bean.getClientForContest(1);
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>getClientForContest(long)</code>.
     * </p>
     *
     * <p>
     * If the contest doesn't exist, <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetClientForContest_Failure3() throws Exception {
        try {
            initContext();

            bean.getClientForContest(1);
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>getClientForContest(long)</code>.
     * </p>
     *
     * <p>
     * If the project doesn't exist, <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetClientForContest_Failure4() throws Exception {
        try {
            initContext();

            Contest contest = createContestForTest();
            bean.createContest(contest);

            bean.getClientForContest(contest.getContestId());
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getClientForProject(long)</code>.
     * </p>
     *
     * <p>
     * Verify that the user id of the specified project is returned.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetClientForProject() throws Exception {
        initContext();

        Project project = new Project();
        project.setCreateDate(new Date());
        project.setDescription("desc");
        project.setModifyDate(new Date());
        project.setName("name");
        project.setUserId(1);
        entityManager.persist(project);

        long id = bean.getClientForProject(project.getProjectId());

        assertEquals("The user id should match.", 1, id);
    }

    /**
     * <p>
     * Failure test for <code>getClientForProject(long)</code>.
     * </p>
     *
     * <p>
     * If the project doesn't exist, <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetClientForProject_Failure1() throws Exception {
        try {
            initContext();

            bean.getClientForProject(1);
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>getClientForProject(long)</code>.
     * </p>
     *
     * <p>
     * If the entityManager is closed, <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetClientForProject_Failure2() throws Exception {
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

            bean.getClientForProject(project.getProjectId());
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>getClientForProject(long)</code>.
     * </p>
     *
     * <p>
     * If any persistence exception is thrown, <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetClientForProject_Failure3() throws Exception {
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

            bean.getClientForProject(project.getProjectId());
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>addContestStatus(ContestStatus)</code>.
     * </p>
     *
     * <p>
     * Verify that the new contest status is added to the persistence.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddContestStatus() throws Exception {
        initContext();

        ContestStatus status = new ContestStatus();
        status.setDescription("A new status");
        status.setName("StatusA");
        status.setContestStatusId(99994L);
        bean.addContestStatus(status);

        ContestStatus persist = bean.getContestStatus(status.getContestStatusId());

        assertEquals("The persistence should match.", persist.getName(),
            status.getName());
    }

    /**
     * <p>
     * Failure test for <code>addContestStatus(ContestStatus)</code>.
     * </p>
     *
     * <p>
     * If the contest status already exists, <code>EntityAlreadyExistException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddContestStatus_Failure1() throws Exception {
        try {
            initContext();

            ContestStatus status = new ContestStatus();
            status.setDescription("A new status");
            status.setName("StatusA");
            status.setContestStatusId(99994L);
            bean.addContestStatus(status);

            bean.addContestStatus(status);
            fail("EntityAlreadyExistsException is expected.");
        } catch (EntityAlreadyExistsException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>addContestStatus(ContestStatus)</code>.
     * </p>
     *
     * <p>
     * If the entityManager is closed, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddContestStatus_Failure2() throws Exception {
        try {
            initContext();

            entityManager.close();

            ContestStatus status = new ContestStatus();
            status.setDescription("A new status");
            status.setName("StatusA");
            bean.addContestStatus(status);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>addContestStatus(ContestStatus)</code>.
     * </p>
     *
     * <p>
     * If any transaction exception occurs, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddContestStatus_Failure3() throws Exception {
        try {
            initContext();

            entityManager.enableTransactionException(true);

            ContestStatus status = new ContestStatus();
            status.setDescription("A new status");
            status.setName("StatusA");
            bean.addContestStatus(status);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>addContestStatus(ContestStatus)</code>.
     * </p>
     *
     * <p>
     * If any persistence exception occurs, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddContestStatus_Failure4() throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);

            ContestStatus status = new ContestStatus();
            status.setDescription("A new status");
            status.setName("StatusA");
            bean.addContestStatus(status);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>updateContestStatus(ContestStatus)</code>.
     * </p>
     *
     * <p>
     * Verify that the new contest status is updated to the persistence.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContestStatus() throws Exception {
        initContext();

        ContestStatus status = new ContestStatus();
        status.setDescription("A new status");
        status.setName("StatusA");
        status.setContestStatusId(99994L);
        bean.addContestStatus(status);

        status.setName("StatusB");
        bean.updateContestStatus(status);

        ContestStatus persist = bean.getContestStatus(status.getContestStatusId());

        assertEquals("The persistence should match.", persist.getName(),
            status.getName());
    }

    /**
     * <p>
     * Failure test for <code>updateContestStatus(ContestStatus)</code>.
     * </p>
     *
     * <p>
     * If the contest status doesn't exists, <code>EntityNotFoundException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContestStatus_Failure1() throws Exception {
        try {
            initContext();

            ContestStatus status = new ContestStatus();
            status.setDescription("A new status");
            status.setName("StatusA");
            bean.updateContestStatus(status);

            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateContestStatus(ContestStatus)</code>.
     * </p>
     *
     * <p>
     * If the entityManager is closed, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContestStatus_Failure2() throws Exception {
        try {
            initContext();

            ContestStatus status = new ContestStatus();
            status.setDescription("A new status");
            status.setName("StatusA");
            bean.addContestStatus(status);

            entityManager.close();

            status.setName("sss");
            bean.updateContestStatus(status);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateContestStatus(ContestStatus)</code>.
     * </p>
     *
     * <p>
     * If any transaction exception occurs, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContestStatus_Failure3() throws Exception {
        try {
            initContext();

            ContestStatus status = new ContestStatus();
            status.setDescription("A new status");
            status.setName("StatusA");
            bean.addContestStatus(status);

            entityManager.enableTransactionException(true);

            status.setName("sss");
            bean.updateContestStatus(status);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateContestStatus(ContestStatus)</code>.
     * </p>
     *
     * <p>
     * If any persistence exception occurs, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContestStatus_Failure4() throws Exception {
        try {
            initContext();

            ContestStatus status = new ContestStatus();
            status.setDescription("A new status");
            status.setName("StatusA");
            bean.addContestStatus(status);

            entityManager.enablePersistenceException(true);

            status.setName("sss");
            bean.updateContestStatus(status);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>removeContestStatus(long)</code>.
     * </p>
     *
     * <p>
     * Verify that if the contest status with specified id doesn't exist, return false.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveContestStatus() throws Exception {
        initContext();

        boolean result = bean.removeContestStatus(1000);
        assertFalse("The status doesn't exist.", result);
    }

    /**
     * <p>
     * Accuracy test for <code>removeContestStatus(long)</code>.
     * </p>
     *
     * <p>
     * Verify that if the contest status with specified id exists, return true.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveContestStatus1() throws Exception {
        initContext();

        ContestStatus status = new ContestStatus();
        status.setDescription("ss");
        status.setName("ss");
        status.setContestStatusId(999933L);
        bean.addContestStatus(status);

        boolean result = bean.removeContestStatus(status.getContestStatusId());
        assertTrue("The status exists.", result);
        entityManager.remove(status);
    }

    /**
     * <p>
     * Failure test for <code>removeContestStatus(long)</code>.
     * </p>
     *
     * <p>
     * If the entityManager is closed, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveContestStatus_Failure1() throws Exception {
        try {
            initContext();

            entityManager.close();
            bean.removeContestStatus(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>removeContestStatus(long)</code>.
     * </p>
     *
     * <p>
     * If any transaction exception occurs, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveContestStatus_Failure2() throws Exception {
        try {
            initContext();

            entityManager.enableTransactionException(true);
            bean.removeContestStatus(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>removeContestStatus(long)</code>.
     * </p>
     *
     * <p>
     * If any persistence exception occurs, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveContestStatus_Failure3() throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);
            bean.removeContestStatus(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getContestStatus(long)</code>.
     * </p>
     *
     * <p>
     * Verify that the contest status with specified id will be returned.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetContestStatus() throws Exception {
        initContext();

        ContestStatus status = new ContestStatus();
        status.setDescription("A new status");
        status.setName("StatusA");
        status.setContestStatusId(99994L);
        bean.addContestStatus(status);

        ContestStatus persist = bean.getContestStatus(status.getContestStatusId());

        assertEquals("The persistence should match.", persist.getName(),
            status.getName());
    }

    /**
     * <p>
     * Failure test for <code>getContestStatus(long)</code>.
     * </p>
     *
     * <p>
     * If the entityManager is closed, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetContestStatus_Failure1() throws Exception {
        try {
            initContext();

            entityManager.close();
            bean.getContestStatus(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>removeContestStatus(long)</code>.
     * </p>
     *
     * <p>
     * If any persistence exception occurs, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetContestStatus_Failure2() throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);
            bean.getContestStatus(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>addDocument(Document)</code>.
     * </p>
     *
     * <p>
     * Verify that the document is added to the persistence.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddDocument() throws Exception {
        initContext();

        FilePath filePath = new FilePath();
        filePath.setModifyDate(new Date());
        filePath.setPath("C:/");
        entityManager.persist(filePath);

        Document document = new Document();
        document.setCreateDate(new Date());
        document.setPath(filePath);
        MimeType mt = new MimeType();
        mt.setMimeTypeId(1L);
        mt.setDescription("description");
        entityManager.persist(mt);

        document.setMimeType(mt);

        bean.addDocument(document);
    }

    /**
     * <p>
     * Failure test for <code>addDocument(Document)</code>.
     * </p>
     *
     * <p>
     * If the document already exist, <code>EntityAlreadyExistsException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddDocument_Failure1() throws Exception {
        try {
            initContext();

            FilePath filePath = new FilePath();
            filePath.setModifyDate(new Date());
            filePath.setPath("C:/");
            entityManager.persist(filePath);

            Document document = new Document();
            document.setCreateDate(new Date());
            document.setPath(filePath);

            MimeType mt = new MimeType();
            mt.setMimeTypeId(999L);
            mt.setDescription("description");
            entityManager.persist(mt);

            document.setMimeType(mt);

            bean.addDocument(document);
            bean.addDocument(document);
            fail("EntityAlreadyExistsException is expected.");
        } catch (EntityAlreadyExistsException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>addDocument(Document)</code>.
     * </p>
     *
     * <p>
     * If entity manager is closed,
     * <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddDocument_Failure3() throws Exception {
        try {
            initContext();

            entityManager.close();

            Document document = new Document();
            document.setCreateDate(new Date());

            bean.addDocument(document);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>addDocument(Document)</code>.
     * </p>
     *
     * <p>
     * If any transaction exception occurs,
     * <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddDocument_Failure4() throws Exception {
        try {
            initContext();

            entityManager.enableTransactionException(true);

            Document document = new Document();
            document.setCreateDate(new Date());

            bean.addDocument(document);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>addDocument(Document)</code>.
     * </p>
     *
     * <p>
     * If any persistence exception occurs,
     * <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddDocument_Failure5() throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);

            Document document = new Document();
            document.setCreateDate(new Date());

            bean.addDocument(document);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>updateDocument(Document)</code>.
     * </p>
     *
     * <p>
     * Verify that the new document is updated to the persistence.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateDocument() throws Exception {
        entityManager = new MockEntityManager();

        FilePath filePath = new FilePath();
        filePath.setModifyDate(new Date());
        filePath.setPath("sss");
        entityManager.persist(filePath);

        context.addEntry("unitName", "contestManager");
        context.addEntry("auditChange", new Boolean(false));
        context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", filePath.getFilePathId());
        context.addEntry("loggerName", "contestManager");
        context.addEntry("documentContentManagerClassName",
            "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = bean.getClass()
                            .getDeclaredMethod("initialize", new Class[0]);

        method.setAccessible(true);
        method.invoke(bean, new Object[0]);

        context.addEntry("contestManager", entityManager);

        Document document = new Document();
        document.setCreateDate(new Date());

        MimeType mt = new MimeType();
        mt.setMimeTypeId(1L);
        mt.setDescription("description");
        entityManager.persist(mt);

        document.setMimeType(mt);

        bean.addDocument(document);

        document.setCreateDate(new Date());

        bean.updateDocument(document);

        Document persist = bean.getDocument(document.getDocumentId());

        assertEquals("The persistence should match.", persist.getCreateDate(),
            document.getCreateDate());
    }

    /**
     * <p>
     * Failure test for <code>updateDocument(Document)</code>.
     * </p>
     *
     * <p>
     * If the document exists, <code>EntityNotFoundException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateDocument_Failure1() throws Exception {
        try {
            initContext();

            FilePath filePath = new FilePath();
            filePath.setModifyDate(new Date());
            filePath.setPath("sss");
            entityManager.persist(filePath);

            Document document = new Document();
            document.setCreateDate(new Date());

            bean.updateDocument(document);

            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateDocument(Document)</code>.
     * </p>
     *
     * <p>
     * If the entityManager is closed, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateDocument_Failure2() throws Exception {
        try {
            initContext();

            FilePath filePath = new FilePath();
            filePath.setModifyDate(new Date());
            filePath.setPath("sss");
            entityManager.persist(filePath);

            Document document = new Document();
            document.setCreateDate(new Date());
            bean.addDocument(document);
            entityManager.close();

            bean.updateDocument(document);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateDocument(Document)</code>.
     * </p>
     *
     * <p>
     * If any transaction exception occurs, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateDocument_Failure3() throws Exception {
        try {
            initContext();

            FilePath filePath = new FilePath();
            filePath.setModifyDate(new Date());
            filePath.setPath("sss");
            entityManager.persist(filePath);

            Document document = new Document();
            document.setCreateDate(new Date());
            bean.addDocument(document);

            entityManager.enableTransactionException(true);

            bean.updateDocument(document);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateDocument(Document)</code>.
     * </p>
     *
     * <p>
     * If any persistence exception occurs, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateDocument_Failure4() throws Exception {
        try {
            initContext();

            FilePath filePath = new FilePath();
            filePath.setModifyDate(new Date());
            filePath.setPath("sss");
            entityManager.persist(filePath);

            Document document = new Document();
            document.setCreateDate(new Date());
            bean.addDocument(document);

            entityManager.enablePersistenceException(true);
            bean.updateDocument(document);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getDocument(long)</code>.
     * </p>
     *
     * <p>
     * Verify that the document with specified id will be returned.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetDocument() throws Exception {
        entityManager = new MockEntityManager();

        FilePath filePath = new FilePath();
        filePath.setModifyDate(new Date());
        filePath.setPath("sss");
        entityManager.persist(filePath);

        context.addEntry("unitName", "contestManager");
        context.addEntry("auditChange", new Boolean(false));
        context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", filePath.getFilePathId());
        context.addEntry("loggerName", "contestManager");
        context.addEntry("documentContentManagerClassName",
            "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = bean.getClass()
                            .getDeclaredMethod("initialize", new Class[0]);

        method.setAccessible(true);
        method.invoke(bean, new Object[0]);

        context.addEntry("contestManager", entityManager);

        Document document = new Document();
        document.setCreateDate(new Date());

        MimeType mt = new MimeType();
        mt.setMimeTypeId(999L);
        mt.setDescription("description");
        entityManager.persist(mt);

        document.setMimeType(mt);

        bean.addDocument(document);

        Document persist = bean.getDocument(document.getDocumentId());

        assertEquals("The persistence should match.", persist.getCreateDate(),
            document.getCreateDate());
    }

    /**
     * <p>
     * Failure test for <code>getDocument(long)</code>.
     * </p>
     *
     * <p>
     * If the entityManager is closed, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetDocument_Failure1() throws Exception {
        try {
            initContext();

            entityManager.close();
            bean.getDocument(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>removeDocument(long)</code>.
     * </p>
     *
     * <p>
     * If any persistence exception occurs, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetDocument_Failure2() throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);
            bean.getDocument(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>removeDocument(long)</code>.
     * </p>
     *
     * <p>
     * Verify that if the document with specified id doesn't exist, return false.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveDocument() throws Exception {
        initContext();

        boolean result = bean.removeDocument(1000);
        assertFalse("The document doesn't exist.", result);
    }

    /**
     * <p>
     * Accuracy test for <code>removeDocument(long)</code>.
     * </p>
     *
     * <p>
     * Verify that if the contest status with specified id exists, return true.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveDocument1() throws Exception {
        entityManager = new MockEntityManager();

        FilePath filePath = new FilePath();
        filePath.setModifyDate(new Date());
        filePath.setPath("sss");
        entityManager.persist(filePath);

        context.addEntry("unitName", "contestManager");
        context.addEntry("auditChange", new Boolean(false));
        context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", filePath.getFilePathId());
        context.addEntry("loggerName", "contestManager");
        context.addEntry("documentContentManagerClassName",
            "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = bean.getClass()
                            .getDeclaredMethod("initialize", new Class[0]);

        method.setAccessible(true);
        method.invoke(bean, new Object[0]);

        context.addEntry("contestManager", entityManager);

        Document document = new Document();
        document.setCreateDate(new Date());

        MimeType mt = new MimeType();
        mt.setMimeTypeId(1L);
        mt.setDescription("description");
        entityManager.persist(mt);

        document.setMimeType(mt);
        bean.addDocument(document);

        boolean result = bean.removeDocument(document.getDocumentId());
        assertTrue("The document exists.", result);
    }

    /**
     * <p>
     * Failure test for <code>removeDocument(long)</code>.
     * </p>
     *
     * <p>
     * If the entityManager is closed, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveDocument_Failure1() throws Exception {
        try {
            initContext();

            entityManager.close();
            bean.removeDocument(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>removeDocument(long)</code>.
     * </p>
     *
     * <p>
     * If any transaction exception occurs, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveDocument_Failure2() throws Exception {
        try {
            initContext();

            entityManager.enableTransactionException(true);
            bean.removeDocument(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>removeDocument(long)</code>.
     * </p>
     *
     * <p>
     * If any persistence exception occurs, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveDocument_Failure3() throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);
            bean.removeDocument(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>addDocumentToContest(long, long)</code>.
     * </p>
     *
     * <p>
     * Verify that the document is successfully added to the contest.
     * </p>
     *
     * @throws Exception to JUnit.
     */
/*    public void testAddDocumentToContest() throws Exception {
        entityManager = new MockEntityManager();

        FilePath filePath = new FilePath();
        filePath.setModifyDate(new Date());
        filePath.setPath("sss");
        entityManager.persist(filePath);

        context.addEntry("unitName", "contestManager");
        context.addEntry("auditChange", new Boolean(false));
        context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", filePath.getFilePathId());
        context.addEntry("loggerName", "contestManager");
        context.addEntry("documentContentManagerClassName",
            "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = bean.getClass()
                            .getDeclaredMethod("initialize", new Class[0]);

        method.setAccessible(true);
        method.invoke(bean, new Object[0]);

        context.addEntry("contestManager", entityManager);

        Contest contest = createContestForTest();
        bean.createContest(contest);

        Document document = new Document();
        document.setCreateDate(new Date());
        MimeType mt = new MimeType();
        mt.setMimeTypeId(1L);
        mt.setDescription("description");
        entityManager.persist(mt);

        document.setMimeType(mt);
        bean.addDocument(document);

        // It should process successfully.
        bean.addDocumentToContest(document.getDocumentId(),
            contest.getContestId());

        Contest persist = bean.getContest(contest.getContestId());

        assertTrue("The contest should contain document.",
            persist.getDocuments().contains(document));
    }*/

    /**
     * <p>
     * Failure test for <code>addDocumentToContest(long, long)</code>.
     * </p>
     *
     * <p>
     * If the document doesn't exist, <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddDocumentToContest_Failure1() throws Exception {
        try {
            initContext();

            Contest contest = createContestForTest();
            bean.createContest(contest);

            bean.addDocumentToContest(1000, contest.getContestId());
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>addDocumentToContest(long, long)</code>.
     * </p>
     *
     * <p>
     * If the contest doesn't exist, <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddDocumentToContest_Failure2() throws Exception {
        try {
            entityManager = new MockEntityManager();

            FilePath filePath = new FilePath();
            filePath.setModifyDate(new Date());
            filePath.setPath("sss");
            entityManager.persist(filePath);

            context.addEntry("unitName", "contestManager");
            context.addEntry("auditChange", new Boolean(false));
            context.addEntry("activeContestStatusId", new Long(1));
            context.addEntry("defaultDocumentPathId", filePath.getFilePathId());
            context.addEntry("loggerName", "contestManager");
            context.addEntry("documentContentManagerClassName",
                "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
            context.addEntry("documentContentManagerAttributeKeys",
                "serverAddress,serverPort");
            context.addEntry("serverAddress", "127.0.0.1");
            context.addEntry("serverPort", new Integer(40000));

            Method method = bean.getClass()
                                .getDeclaredMethod("initialize", new Class[0]);

            method.setAccessible(true);
            method.invoke(bean, new Object[0]);

            context.addEntry("contestManager", entityManager);

            Document document = new Document();
            document.setCreateDate(new Date());
            MimeType mt = new MimeType();
            mt.setMimeTypeId(22L);
            mt.setDescription("description");
            entityManager.persist(mt);

            document.setMimeType(mt);
            bean.addDocument(document);

            bean.addDocumentToContest(document.getDocumentId(), 1000);
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>addDocumentToContest(long, long)</code>.
     * </p>
     *
     * <p>
     * If the entityManager is closed, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddDocumentToContest_Failure3() throws Exception {
        try {
            initContext();

            entityManager.close();
            bean.addDocumentToContest(1, 1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>addDocumentToContest(long, long)</code>.
     * </p>
     *
     * <p>
     * If any transaction exception occurs, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddDocumentToContest_Failure4() throws Exception {
        try {
            initContext();

            entityManager.enableTransactionException(true);
            bean.addDocumentToContest(1, 1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>addDocumentToContest(long, long)(long)</code>.
     * </p>
     *
     * <p>
     * If any persistence exception occurs, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddDocumentToContest_Failure5() throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);
            bean.addDocumentToContest(1, 1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>removeDocumentFromContest(long, long)</code>.
     * </p>
     *
     * <p>
     * Verify that the document is successfully removed from the contest.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    /*public void testRemoveDocumentFromContest() throws Exception {
        entityManager = new MockEntityManager();

        FilePath filePath = new FilePath();
        filePath.setModifyDate(new Date());
        filePath.setPath("sss");
        entityManager.persist(filePath);

        context.addEntry("unitName", "contestManager");
        context.addEntry("auditChange", new Boolean(false));
        context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", filePath.getFilePathId());
        context.addEntry("loggerName", "contestManager");
        context.addEntry("documentContentManagerClassName",
            "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = bean.getClass()
                            .getDeclaredMethod("initialize", new Class[0]);

        method.setAccessible(true);
        method.invoke(bean, new Object[0]);

        context.addEntry("contestManager", entityManager);

        Contest contest = createContestForTest();
        bean.createContest(contest);

        Document document = new Document();
        document.setCreateDate(new Date());
        MimeType mt = new MimeType();
        mt.setMimeTypeId(1111L);
        mt.setDescription("description");
        entityManager.persist(mt);

        document.setMimeType(mt);
        bean.addDocument(document);

        bean.addDocumentToContest(document.getDocumentId(),
            contest.getContestId());

        // It should process successfully.
        boolean result = bean.removeDocumentFromContest(document.getDocumentId(),
                contest.getContestId());

        assertTrue("The document should be removed from contest.", result);
    }*/

    /**
     * <p>
     * Accuracy test for <code>removeDocumentFromContest(long, long)</code>.
     * </p>
     *
     * <p>
     * Verify that the document is successfully removed from the contest.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveDocumentFromContest1() throws Exception {
        entityManager = new MockEntityManager();

        FilePath filePath = new FilePath();
        filePath.setModifyDate(new Date());
        filePath.setPath("sss");
        entityManager.persist(filePath);

        context.addEntry("unitName", "contestManager");
        context.addEntry("auditChange", new Boolean(false));
        context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", filePath.getFilePathId());
        context.addEntry("loggerName", "contestManager");
        context.addEntry("documentContentManagerClassName",
            "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = bean.getClass()
                            .getDeclaredMethod("initialize", new Class[0]);

        method.setAccessible(true);
        method.invoke(bean, new Object[0]);

        context.addEntry("contestManager", entityManager);

        Contest contest = createContestForTest();
        bean.createContest(contest);

        Document document = new Document();
        document.setCreateDate(new Date());
        MimeType mt = new MimeType();
        mt.setMimeTypeId(21L);
        mt.setDescription("description");
        entityManager.persist(mt);

        document.setMimeType(mt);
        bean.addDocument(document);

        // It should process successfully.
        boolean result = bean.removeDocumentFromContest(document.getDocumentId(),
                contest.getContestId());

        assertFalse("The document isn't removed from contest.", result);
    }

    /**
     * <p>
     * Failure test for <code>removeDocumentFromContest(long, long)</code>.
     * </p>
     *
     * <p>
     * If the document doesn't exist, <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveDocumentFromContest_Failure1()
        throws Exception {
        try {
            initContext();

            Contest contest = createContestForTest();
            bean.createContest(contest);

            bean.removeDocumentFromContest(1000, contest.getContestId());
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>removeDocumentFromContest(long, long)</code>.
     * </p>
     *
     * <p>
     * If the contest doesn't exist, <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveDocumentFromContest_Failure2()
        throws Exception {
        try {
            entityManager = new MockEntityManager();

            FilePath filePath = new FilePath();
            filePath.setModifyDate(new Date());
            filePath.setPath("sss");
            entityManager.persist(filePath);
            context.addEntry("auditChange", new Boolean(false));
            context.addEntry("unitName", "contestManager");
            context.addEntry("activeContestStatusId", new Long(1));
            context.addEntry("defaultDocumentPathId", filePath.getFilePathId());
            context.addEntry("loggerName", "contestManager");
            context.addEntry("documentContentManagerClassName",
                "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
            context.addEntry("documentContentManagerAttributeKeys",
                "serverAddress,serverPort");
            context.addEntry("serverAddress", "127.0.0.1");
            context.addEntry("serverPort", new Integer(40000));

            Method method = bean.getClass()
                                .getDeclaredMethod("initialize", new Class[0]);

            method.setAccessible(true);
            method.invoke(bean, new Object[0]);

            context.addEntry("contestManager", entityManager);

            Document document = new Document();
            document.setCreateDate(new Date());
            MimeType mt = new MimeType();
            mt.setMimeTypeId(165L);
            mt.setDescription("description");
            entityManager.persist(mt);

            document.setMimeType(mt);
            bean.addDocument(document);

            bean.removeDocumentFromContest(document.getDocumentId(), 1000);
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>removeDocumentFromContest(long, long)</code>.
     * </p>
     *
     * <p>
     * If the entityManager is closed, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testremoveDocumentFromContest_Failure3()
        throws Exception {
        try {
            initContext();

            entityManager.close();
            bean.removeDocumentFromContest(1, 1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>removeDocumentFromContest(long, long)</code>.
     * </p>
     *
     * <p>
     * If any transaction exception occurs, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testremoveDocumentFromContest_Failure4()
        throws Exception {
        try {
            initContext();

            entityManager.enableTransactionException(true);
            bean.removeDocumentFromContest(1, 1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>removeDocumentFromContest(long, long)(long)</code>.
     * </p>
     *
     * <p>
     * If any persistence exception occurs, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testremoveDocumentFromContest_Failure5()
        throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);
            bean.removeDocumentFromContest(1, 1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>createContestPayment(ContestPayment)</code>.
     * </p>
     *
     * <p>
     * Verify that the contest is added to the persistence.
     * </p>
     *
     * @throws Exception to JUnit.
     */
/*    public void testCreateContestPayment() throws Exception {
        initContext();
        Contest contest = bean.getContest(1L);
        ContestPayment entity = new ContestPayment();
        //entity.setContest(contest);
        PaymentStatus paymentStatus = new PaymentStatus();
        paymentStatus.setPaymentStatusId(1L);
        entity.setStatus(paymentStatus );
        //sentity.setPayPalOrderId(3L);
        entity.setPrice(500.0);

        bean.createContestPayment(entity);

        ContestPayment persist = bean.getContestPayment(entity.getContestId());
        assertEquals("The persist should match", 3L, persist.getPayPalOrderId());
    }*/
}
