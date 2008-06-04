/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestStatusTransitionException;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.ContestTypeConfig;
import com.topcoder.service.studio.contest.Document;
import com.topcoder.service.studio.contest.EntityAlreadyExistsException;
import com.topcoder.service.studio.contest.EntityNotFoundException;
import com.topcoder.service.studio.contest.FilePath;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.contest.documentcontentservers.SocketDocumentContentServer;
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
    }

    /**
     * <p>Destroy the environment.</p>
     */
    protected void tearDown() {
    }

    /**
     * <p>Returns the test suite of this class.</p>
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
        contestChannel.setName("name");
        contestChannel.setFileType(fileType);

        ContestType contestType = new ContestType();
        contestType.setDescription("desc");
        contestType.setRequirePreviewFile(false);
        contestType.setRequirePreviewImage(false);
        entityManager.persist(contestType);

        ContestStatus status = new ContestStatus();
        status.setDescription("description");
        status.setName("name");

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
    public void testAddContestChannel() throws Exception {
        initContext();

        StudioFileType fileType = new StudioFileType();
        fileType.setDescription("desc");
        fileType.setExtension("ext");
        fileType.setImageFile(true);
        fileType.setSort(new Integer(1));
        entityManager.persist(fileType);

        ContestChannel channel = new ContestChannel();
        channel.setDescription("desc");
        channel.setName("sss");
        channel.setParentChannelId(new Long(1));
        channel.setFileType(fileType);

        bean.addContestChannel(channel);

        ContestChannel persist = entityManager.find(ContestChannel.class,
                channel.getContestChannelId());

        assertEquals("The channel should match.", "desc",
            persist.getDescription());
    }

    /**
     * <p>
     * Failure test for <code>addContestChannel(ContestChannel)</code>.
     * </p>
     *
     * <p>
     * If the ContestChannel already exist, <code>EntityAlreadyExistsException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddContestChannel_Failure1() throws Exception {
        try {
            initContext();

            StudioFileType fileType = new StudioFileType();
            fileType.setDescription("desc");
            fileType.setExtension("ext");
            fileType.setImageFile(true);
            fileType.setSort(new Integer(1));
            entityManager.persist(fileType);

            ContestChannel channel = new ContestChannel();
            channel.setDescription("desc");
            channel.setName("sss");
            channel.setParentChannelId(new Long(1));
            channel.setFileType(fileType);

            bean.addContestChannel(channel);

            bean.addContestChannel(channel);
            fail("EntityAlreadyExistsException is expected.");
        } catch (EntityAlreadyExistsException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>addContestChannel(ContestChannel)</code>.
     * </p>
     *
     * <p>
     * If entity manager is closed,
     * <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddContestChannel_Failure3() throws Exception {
        try {
            initContext();

            StudioFileType fileType = new StudioFileType();
            fileType.setDescription("desc");
            fileType.setExtension("ext");
            fileType.setImageFile(true);
            fileType.setSort(new Integer(1));
            entityManager.persist(fileType);

            ContestChannel channel = new ContestChannel();
            channel.setDescription("desc");
            channel.setName("sss");
            channel.setParentChannelId(new Long(1));
            channel.setFileType(fileType);

            entityManager.close();

            bean.addContestChannel(channel);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
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
    public void testAddContestChannel_Failure4() throws Exception {
        try {
            initContext();

            StudioFileType fileType = new StudioFileType();
            fileType.setDescription("desc");
            fileType.setExtension("ext");
            fileType.setImageFile(true);
            fileType.setSort(new Integer(1));
            entityManager.persist(fileType);

            ContestChannel channel = new ContestChannel();
            channel.setDescription("desc");
            channel.setName("sss");
            channel.setParentChannelId(new Long(1));
            channel.setFileType(fileType);

            entityManager.enableTransactionException(true);

            bean.addContestChannel(channel);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
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
    public void testAddContestChannel_Failure5() throws Exception {
        try {
            initContext();

            StudioFileType fileType = new StudioFileType();
            fileType.setDescription("desc");
            fileType.setExtension("ext");
            fileType.setImageFile(true);
            fileType.setSort(new Integer(1));
            entityManager.persist(fileType);

            ContestChannel channel = new ContestChannel();
            channel.setDescription("desc");
            channel.setName("sss");
            channel.setParentChannelId(new Long(1));
            channel.setFileType(fileType);

            entityManager.enablePersistenceException(true);
            bean.addContestChannel(channel);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
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
    public void testUpdateContestChannel() throws Exception {
        initContext();

        StudioFileType fileType = new StudioFileType();
        fileType.setDescription("desc");
        fileType.setExtension("ext");
        fileType.setImageFile(true);
        fileType.setSort(new Integer(1));
        entityManager.persist(fileType);

        ContestChannel channel = new ContestChannel();
        channel.setDescription("desc");
        channel.setName("sss");
        channel.setParentChannelId(new Long(1));
        channel.setFileType(fileType);

        bean.addContestChannel(channel);

        channel.setDescription("desc1");

        bean.updateContestChannel(channel);

        ContestChannel persist = bean.getContestChannel(channel.getContestChannelId());

        assertEquals("The persistence should match.", "desc1",
            persist.getDescription());
    }

    /**
     * <p>
     * Failure test for <code>updateContestChannel(ContestChannel)</code>.
     * </p>
     *
     * <p>
     * If the ContestChannel doesn't exists, <code>EntityNotFoundException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContestChannel_Failure1() throws Exception {
        try {
            initContext();

            StudioFileType fileType = new StudioFileType();
            fileType.setDescription("desc");
            fileType.setExtension("ext");
            fileType.setImageFile(true);
            fileType.setSort(new Integer(1));
            entityManager.persist(fileType);

            ContestChannel channel = new ContestChannel();
            channel.setDescription("desc");
            channel.setName("sss");
            channel.setParentChannelId(new Long(1));
            channel.setFileType(fileType);

            //bean.addContestChannel(channel);
            bean.updateContestChannel(channel);

            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateContestChannel(ContestChannel)</code>.
     * </p>
     *
     * <p>
     * If the entityManager is closed, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContestChannel_Failure2() throws Exception {
        try {
            initContext();

            StudioFileType fileType = new StudioFileType();
            fileType.setDescription("desc");
            fileType.setExtension("ext");
            fileType.setImageFile(true);
            fileType.setSort(new Integer(1));
            entityManager.persist(fileType);

            ContestChannel channel = new ContestChannel();
            channel.setDescription("desc");
            channel.setName("sss");
            channel.setParentChannelId(new Long(1));
            channel.setFileType(fileType);

            bean.addContestChannel(channel);

            entityManager.close();

            bean.updateContestChannel(channel);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateContestChannel(ContestChannel)</code>.
     * </p>
     *
     * <p>
     * If any transaction exception occurs, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContestChannel_Failure3() throws Exception {
        try {
            initContext();

            StudioFileType fileType = new StudioFileType();
            fileType.setDescription("desc");
            fileType.setExtension("ext");
            fileType.setImageFile(true);
            fileType.setSort(new Integer(1));
            entityManager.persist(fileType);

            ContestChannel channel = new ContestChannel();
            channel.setDescription("desc");
            channel.setName("sss");
            channel.setParentChannelId(new Long(1));
            channel.setFileType(fileType);

            bean.addContestChannel(channel);

            entityManager.enableTransactionException(true);

            bean.updateContestChannel(channel);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateContestChannel(ContestChannel)</code>.
     * </p>
     *
     * <p>
     * If any persistence exception occurs, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContestChannel_Failure4() throws Exception {
        try {
            initContext();

            StudioFileType fileType = new StudioFileType();
            fileType.setDescription("desc");
            fileType.setExtension("ext");
            fileType.setImageFile(true);
            fileType.setSort(new Integer(1));
            entityManager.persist(fileType);

            ContestChannel channel = new ContestChannel();
            channel.setDescription("desc");
            channel.setName("sss");
            channel.setParentChannelId(new Long(1));
            channel.setFileType(fileType);

            bean.addContestChannel(channel);

            entityManager.enablePersistenceException(true);
            bean.updateContestChannel(channel);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
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
    public void testGetContestChannel() throws Exception {
        initContext();

        StudioFileType fileType = new StudioFileType();
        fileType.setDescription("desc");
        fileType.setExtension("ext");
        fileType.setImageFile(true);
        fileType.setSort(new Integer(1));
        entityManager.persist(fileType);

        ContestChannel channel = new ContestChannel();
        channel.setDescription("desc");
        channel.setName("sss");
        channel.setParentChannelId(new Long(1));
        channel.setFileType(fileType);

        bean.addContestChannel(channel);

        ContestChannel persist = bean.getContestChannel(channel.getContestChannelId());

        assertEquals("The persistence should match.", persist.getDescription(),
            channel.getDescription());
    }

    /**
     * <p>
     * Failure test for <code>getContestChannel(long)</code>.
     * </p>
     *
     * <p>
     * If the entityManager is closed, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetContestChannel_Failure1() throws Exception {
        try {
            initContext();

            entityManager.close();
            bean.getContestChannel(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>removeContestChannel(long)</code>.
     * </p>
     *
     * <p>
     * If any persistence exception occurs, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetContestChannel_Failure2() throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);
            bean.getContestChannel(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>removeContestChannel(long)</code>.
     * </p>
     *
     * <p>
     * Verify that if the ContestChannel with specified id doesn't exist, return false.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveContestChannel() throws Exception {
        initContext();

        boolean result = bean.removeContestChannel(1000);
        assertFalse("The ContestChannel doesn't exist.", result);
    }

    /**
     * <p>
     * Accuracy test for <code>removeContestChannel(long)</code>.
     * </p>
     *
     * <p>
     * Verify that if the contest status with specified id exists, return true.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveContestChannel1() throws Exception {
        initContext();

        StudioFileType fileType = new StudioFileType();
        fileType.setDescription("desc");
        fileType.setExtension("ext");
        fileType.setImageFile(true);
        fileType.setSort(new Integer(1));
        entityManager.persist(fileType);

        ContestChannel channel = new ContestChannel();
        channel.setDescription("desc");
        channel.setName("sss");
        channel.setParentChannelId(new Long(1));
        channel.setFileType(fileType);

        bean.addContestChannel(channel);

        boolean result = bean.removeContestChannel(channel.getContestChannelId());
        assertTrue("The ContestChannel exists.", result);
    }

    /**
     * <p>
     * Failure test for <code>removeContestChannel(long)</code>.
     * </p>
     *
     * <p>
     * If the entityManager is closed, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveContestChannel_Failure1() throws Exception {
        try {
            initContext();

            entityManager.close();
            bean.removeContestChannel(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>removeContestChannel(long)</code>.
     * </p>
     *
     * <p>
     * If any transaction exception occurs, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveContestChannel_Failure2() throws Exception {
        try {
            initContext();

            entityManager.enableTransactionException(true);
            bean.removeContestChannel(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>removeContestChannel(long)</code>.
     * </p>
     *
     * <p>
     * If any persistence exception occurs, <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveContestChannel_Failure3() throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);
            bean.removeContestChannel(1);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>addConfig(ContestConfig)</code>.
     * </p>
     *
     * <p>
     * Verify that the Config is added to the persistence.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddConfig() throws Exception {
        initContext();

        ContestConfig config = new ContestConfig();
        config.setValue("desc");

        bean.addConfig(config);

        ContestConfig persist = bean.getConfig(config.getContestConfigId());
        assertEquals("The contest config should match.", "desc", persist.getValue());
    }

    /**
     * <p>
     * Failure test for <code>addConfig(ContestConfig)</code>.
     * </p>
     *
     * <p>
     * If the contest config already exists,
     * <code>EntityAlreadyExistsException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddConfig_Failure2() throws Exception {
        try {
            initContext();

            ContestConfig config = new ContestConfig();
            config.setValue("desc");

            bean.addConfig(config);

            ContestConfig newConfig = new ContestConfig();
            newConfig.setContestConfigId(config.getContestConfigId());
            newConfig.setValue("new desc");

            bean.addConfig(newConfig);

            fail("EntityAlreadyExistsException is expected.");
        } catch (EntityAlreadyExistsException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>addConfig(ContestConfig)</code>.
     * </p>
     *
     * <p>
     * If entity manager is closed,
     * <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddConfig_Failure3() throws Exception {
        try {
            initContext();

            entityManager.close();

            ContestConfig config = new ContestConfig();

            config.setValue("desc");

            bean.addConfig(config);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>addConfig(ContestConfig)</code>.
     * </p>
     *
     * <p>
     * If any transaction exception occurs,
     * <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddConfig_Failure4() throws Exception {
        try {
            initContext();

            entityManager.enableTransactionException(true);

            ContestConfig config = new ContestConfig();

            config.setValue("desc");

            bean.addConfig(config);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>addConfig(ContestConfig)</code>.
     * </p>
     *
     * <p>
     * If any persistence exception occurs,
     * <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddConfig_Failure5() throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);

            ContestConfig config = new ContestConfig();

            config.setValue("desc");

            bean.addConfig(config);

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>updateConfig(ContestConfig)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateConfig() throws Exception {
        initContext();

        ContestConfig config = new ContestConfig();
        config.setValue("desc");

        bean.addConfig(config);

        config.setValue("new desc");
        bean.updateConfig(config);

        ContestConfig persist = bean.getConfig(config.getContestConfigId());
        assertEquals("The config should be updated.", "new desc", persist.getValue());
    }

    /**
     * <p>
     * Failure test for <code>updateConfig(ContestConfig)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateConfig_Failure1() throws Exception {
        try {
            initContext();

            ContestConfig config = new ContestConfig();
            config.setValue("desc");

            entityManager.close();
            bean.updateConfig(config);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateConfig(ContestConfig)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateConfig_Failure2() throws Exception {
        try {
            initContext();

            ContestConfig config = new ContestConfig();
            config.setValue("desc");

            entityManager.enablePersistenceException(true);
            bean.updateConfig(config);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateConfig(ContestConfig)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateConfig_Failure3() throws Exception {
        try {
            initContext();

            ContestConfig config = new ContestConfig();
            config.setValue("desc");

            entityManager.enableTransactionException(true);
            bean.updateConfig(config);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateConfig(ContestConfig)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateConfig_Failure4() throws Exception {
        try {
            initContext();

            ContestConfig config = new ContestConfig();
            config.setContestConfigId(1000);
            config.setValue("desc");

            bean.updateConfig(config);
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getConfig(long)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetConfig() throws Exception {
        initContext();

        ContestConfig config = new ContestConfig();
        config.setValue("desc");

        bean.addConfig(config);

        ContestConfig persist = bean.getConfig(config.getContestConfigId());
        assertEquals("The contest config should match.", "desc", persist.getValue());
    }

    /**
     * <p>
     * Failure test for <code>getConfig(long)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetConfig_Failure1() throws Exception {
        try {
            initContext();

            ContestConfig config = new ContestConfig();
            config.setValue("desc");
            bean.addConfig(config);

            entityManager.close();
            bean.getConfig(config.getContestConfigId());

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>getConfig(long)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetConfig_Failure2() throws Exception {
        try {
            initContext();

            ContestConfig config = new ContestConfig();
            config.setValue("desc");
            bean.addConfig(config);

            entityManager.enablePersistenceException(true);
            bean.getConfig(config.getContestConfigId());

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>getConfig(long)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetConfig_Failure3() throws Exception {
        try {
            initContext();

            ContestConfig config = new ContestConfig();
            config.setValue("desc");

            bean.addConfig(config);

            entityManager.enableTransactionException(true);
            bean.getConfig(config.getContestConfigId());

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>saveDocumentContent(long, byte[])</code>.
     * </p>
     *
     * <p>
     * Verify that the content is added to the document.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSaveDocumentContent() throws Exception {
        SocketDocumentContentServer server = new SocketDocumentContentServer(40000,
                0);
        server.start();

        try {
            entityManager = new MockEntityManager();

            FilePath filePath = new FilePath();
            filePath.setModifyDate(new Date());
            filePath.setPath("test_files");
            entityManager.persist(filePath);

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

            bean.addDocument(document);

            byte[] content = new byte[4];
            content[0] = 1;
            content[1] = 1;
            content[2] = 1;
            content[3] = 1;

            // It should process successfully.
            bean.saveDocumentContent(document.getDocumentId(), content);
        } finally {
            server.stop();
        }
    }

    /**
     * <p>
     * Failure test for <code>saveDocumentContent(long, byte[])</code>.
     * </p>
     *
     * <p>
     * If the content is empty, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSaveDocumentContent_Failure1() throws Exception {
        SocketDocumentContentServer server = new SocketDocumentContentServer(40001,
                0);
        server.start();

        try {
            entityManager = new MockEntityManager();

            FilePath filePath = new FilePath();
            filePath.setModifyDate(new Date());
            filePath.setPath("test_files");
            entityManager.persist(filePath);

            context.addEntry("unitName", "contestManager");
            context.addEntry("activeContestStatusId", new Long(1));
            context.addEntry("defaultDocumentPathId", filePath.getFilePathId());
            context.addEntry("loggerName", "contestManager");
            context.addEntry("documentContentManagerClassName",
                "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
            context.addEntry("documentContentManagerAttributeKeys",
                "serverAddress,serverPort");
            context.addEntry("serverAddress", "127.0.0.1");
            context.addEntry("serverPort", new Integer(40001));

            Method method = bean.getClass()
                                .getDeclaredMethod("initialize", new Class[0]);

            method.setAccessible(true);
            method.invoke(bean, new Object[0]);

            context.addEntry("contestManager", entityManager);

            Document document = new Document();
            document.setCreateDate(new Date());

            bean.addDocument(document);

            byte[] content = new byte[0];

            // It should process successfully.
            bean.saveDocumentContent(document.getDocumentId(), content);
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
     * Failure test for <code>saveDocumentContent(long, byte[])</code>.
     * </p>
     *
     * <p>
     * If the document with specified id doesn't exist, <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSaveDocumentContent_Failure2() throws Exception {
        try {
            initContext();

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
     * If the entity manager is closed, <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSaveDocumentContent_Failure3() throws Exception {
        try {
            initContext();

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
     * If the any transaction exception occurs, <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSaveDocumentContent_Failure4() throws Exception {
        try {
            initContext();

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
     * If the any persistence exception occurs, <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSaveDocumentContent_Failure5() throws Exception {
        try {
            initContext();

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
     * If the server doesn't open, <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSaveDocumentContent_Failure6() throws Exception {
        try {
            initContext();

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
     * Accuracy test for <code>getAllContestStatuses()</code>.
     * </p>
     *
     * <p>
     * Verify that all statuses are returned.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllContestStatuses() throws Exception {
        initContext();

        entityManager.close();

        entityManager = new MockEntityManager();
        context.addEntry("contestManager", entityManager);

        entityManager.getTransaction().begin();

        ContestStatus status = new ContestStatus();
        status.setDescription("A new status");
        status.setName("StatusA");
        bean.addContestStatus(status);
        entityManager.getTransaction().commit();

        List<ContestStatus> list = bean.getAllContestStatuses();

        assertEquals("The size should be 1.", 1, list.size());

        entityManager.getTransaction().begin();
        entityManager.remove(status);
        entityManager.getTransaction().commit();
    }

    /**
     * <p>
     * Failure1 test for <code>getAllContestStatuses()</code>.
     * </p>
     *
     * <p>
     * If the entity manager is closed, <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllContestStatuses_Failure1() throws Exception {
        try {
            initContext();

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
     * If any transaction exception throws, <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllContestStatuses_Failure2() throws Exception {
        try {
            initContext();

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
     * If any persistence exception throws, <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllContestStatuses_Failure3() throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);

            bean.getAllContestStatuses();
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getAllContestChannels()</code>.
     * </p>
     *
     * <p>
     * Verify that all contest channels are returned.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllContestChannels() throws Exception {
        initContext();

        entityManager.getTransaction().begin();

        StudioFileType fileType = new StudioFileType();
        fileType.setDescription("desc");
        fileType.setExtension("ext");
        fileType.setImageFile(false);
        fileType.setSort(new Integer(1));
        entityManager.persist(fileType);

        ContestChannel channel = new ContestChannel();
        channel.setDescription("desc");
        channel.setName("name");
        channel.setFileType(fileType);
        bean.addContestChannel(channel);
        entityManager.getTransaction().commit();

        List<ContestChannel> list = bean.getAllContestChannels();

        assertEquals("The size should be 1.", 1, list.size());

        entityManager.getTransaction().begin();
        entityManager.remove(channel);
        entityManager.remove(fileType);
        entityManager.getTransaction().commit();
    }

    /**
     * <p>
     * Failure1 test for <code>getAllContestChannels()</code>.
     * </p>
     *
     * <p>
     * If the entity manager is closed, <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllContestChannels_Failure1() throws Exception {
        try {
            initContext();

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
     * If any persistence exception throws, <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllContestChannels_Failure3() throws Exception {
        try {
            initContext();

            entityManager.enablePersistenceException(true);

            bean.getAllContestChannels();
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getAllStudioFileTypes()</code>.
     * </p>
     *
     * <p>
     * Verify that all studio file types are returned.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllStudioFileTypes() throws Exception {
        initContext();

        entityManager.getTransaction().begin();

        StudioFileType fileType = new StudioFileType();
        fileType.setDescription("desc");
        fileType.setExtension("ext");
        fileType.setImageFile(false);
        fileType.setSort(new Integer(1));
        entityManager.persist(fileType);
        entityManager.getTransaction().commit();

        List<StudioFileType> list = bean.getAllStudioFileTypes();

        assertFalse("The list should not be empty.", list.isEmpty());

        entityManager.getTransaction().begin();
        entityManager.remove(fileType);
        entityManager.getTransaction().commit();
    }

    /**
     * <p>
     * Failure1 test for <code>getAllStudioFileTypes()</code>.
     * </p>
     *
     * <p>
     * If the entity manager is closed, <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllStudioFileTypes_Failure1() throws Exception {
        try {
            initContext();

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
     * If any persistence exception throws, <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllStudioFileTypes_Failure3() throws Exception {
        try {
            initContext();

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
    public void testAddContestTypeConfig() throws Exception {
        initContext();

        ContestTypeConfig config = new ContestTypeConfig();
        config.setPropertyValue("desc");

        bean.addContestTypeConfig(config);

        ContestTypeConfig persist = bean.getContestTypeConfig(config.getContestTypeConfigId());
        assertEquals("The contest type config should be match.", "desc", persist.getPropertyValue());
    }

    /**
     * <p>
     * Failure test for <code>addContestTypeConfig(ContestTypeConfig)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddContestTypeConfig_Failure1() throws Exception {
        try {
            initContext();

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
            initContext();

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
            initContext();

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
    public void testAddContestTypeConfig_Failure4() throws Exception {
        try {
            initContext();

            ContestTypeConfig config = new ContestTypeConfig();
            config.setPropertyValue("desc");

            bean.addContestTypeConfig(config);

            ContestTypeConfig tc = new ContestTypeConfig();
            tc.setContestTypeConfigId(config.getContestTypeConfigId());
            tc.setPropertyValue("new desc");

            bean.addContestTypeConfig(tc);
            fail("EntityAlreadyExistsException is expected.");
        } catch (EntityAlreadyExistsException e) {
            // success
        }
    }

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
    public void testUpdateContestTypeConfig() throws Exception {
        initContext();

        ContestTypeConfig config = new ContestTypeConfig();
        config.setPropertyValue("desc");

        bean.addContestTypeConfig(config);

        config.setPropertyValue("new desc");
        bean.updateContestTypeConfig(config);

        ContestTypeConfig persist = bean.getContestTypeConfig(config.getContestTypeConfigId());
        assertEquals("The contest type config should match.", "new desc", persist.getPropertyValue());
    }

    /**
     * <p>
     * Failure test for <code>updateContestTypeConfig(ContestTypeConfig)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContestTypeConfig_Failure1() throws Exception {
        try {
            initContext();

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
            initContext();

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
            initContext();

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
     * Failure test for <code>updateContestTypeConfig(ContestTypeConfig)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContestTypeConfig_Failure4() throws Exception {
        try {
            initContext();

            ContestTypeConfig config = new ContestTypeConfig();
            config.setPropertyValue("desc");
            config.setContestTypeConfigId(1000);

            bean.updateContestTypeConfig(config);
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getContestTypeConfig(long)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetContestTypeConfig() throws Exception {
        initContext();

        ContestTypeConfig config = new ContestTypeConfig();
        config.setPropertyValue("desc");

        bean.addContestTypeConfig(config);

        ContestTypeConfig persist = bean.getContestTypeConfig(config.getContestTypeConfigId());
        assertEquals("The contest type config should be match.", "desc", persist.getPropertyValue());
    }

    /**
     * <p>
     * Failure test for <code>getContestTypeConfig(long)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetContestTypeConfig_Failure1() throws Exception {
        try {
            initContext();

            ContestTypeConfig config = new ContestTypeConfig();
            config.setPropertyValue("desc");

            bean.addContestTypeConfig(config);

            entityManager.close();
            bean.getContestTypeConfig(config.getContestTypeConfigId());
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>getContestTypeConfig(long)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetContestTypeConfig_Failure2() throws Exception {
        try {
            initContext();

            ContestTypeConfig config = new ContestTypeConfig();
            config.setPropertyValue("desc");

            bean.addContestTypeConfig(config);

            entityManager.enablePersistenceException(true);
            bean.getContestTypeConfig(config.getContestTypeConfigId());
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>addPrizeToContest(long, long)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddPrizeToContest() throws Exception {
        initContext();

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

        // It should process successfully.
        bean.addPrizeToContest(contest.getContestId(), prize.getPrizeId());

        Prize persist = entityManager.find(Prize.class, prize.getPrizeId());

        assertTrue("The prize should be added to the contest.",
            persist.getContests().contains(contest));
    }

    /**
     * <p>
     * Failure test for <code>addPrizeToContest(long, long)</code>.
     * </p>
     *
     * <p>
     * If the prize with specified id doesn't exist, <code>EntityNotFoundException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddPrizeToContest_Failure1() throws Exception {
        try {
            initContext();

            Contest contest = createContestForTest();
            bean.createContest(contest);

            // It should process successfully.
            bean.addPrizeToContest(contest.getContestId(), 1000);

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
     * If the contest with specified id doesn't exist, <code>EntityNotFoundException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddPrizeToContest_Failure2() throws Exception {
        try {
            initContext();

            PrizeType prizeType = new PrizeType();
            prizeType.setDescription("desc");
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
     * If entityManager is closed, <code>ContestManagementException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddPrizeToContest_Failure3() throws Exception {
        try {
            initContext();

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
     * If any transaction exception occurs, <code>ContestManagementException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddPrizeToContest_Failure4() throws Exception {
        try {
            initContext();

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
     * If any persistence exception occurs, <code>ContestManagementException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddPrizeToContest_Failure5() throws Exception {
        try {
            initContext();

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
     * Accuracy test for <code>removePrizeFromContest(long, long)</code>.
     * </p>
     *
     * <p>
     * If the prize is already in the prize, return true.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemovePrizeFromContest() throws Exception {
        initContext();

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

        // It should process successfully.
        bean.addPrizeToContest(contest.getContestId(), prize.getPrizeId());

        boolean result = bean.removePrizeFromContest(contest.getContestId(),
                prize.getPrizeId());
        assertTrue("The prize is successfully removed from the contest.", result);
    }

    /**
     * <p>
     * Accuracy test for <code>removePrizeFromContest(long, long)</code>.
     * </p>
     *
     * <p>
     * If the prize isn't already in the prize, return false.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemovePrizeFromContest2() throws Exception {
        initContext();

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

        // It should process successfully.
        //bean.addPrizeToContest(contest.getContestId(), prize.getPrizeId());
        boolean result = bean.removePrizeFromContest(contest.getContestId(),
                prize.getPrizeId());
        assertFalse("The prize isn't in the contest.", result);
    }

    /**
     * <p>
     * Failure test for <code>removePrizeFromContest(long, long)</code>.
     * </p>
     *
     * <p>
     * If the prize with specified id doesn't exist, <code>EntityNotFoundException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemovePrizeFromContest_Failure1() throws Exception {
        try {
            initContext();

            Contest contest = createContestForTest();
            bean.createContest(contest);

            // It should process successfully.
            bean.removePrizeFromContest(contest.getContestId(), 1000);

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
     * If the contest with specified id doesn't exist, <code>EntityNotFoundException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemovePrizeFromContest_Failure2() throws Exception {
        try {
            initContext();

            PrizeType prizeType = new PrizeType();
            prizeType.setDescription("desc");
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
     * If entityManager is closed, <code>ContestManagementException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemovePrizeFromContest_Failure3() throws Exception {
        try {
            initContext();

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
            bean.removePrizeFromContest(contest.getContestId(),
                prize.getPrizeId());

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
     * If any transaction exception occurs, <code>ContestManagementException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemovePrizeFromContest_Failure4() throws Exception {
        try {
            initContext();

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
            bean.removePrizeFromContest(contest.getContestId(),
                prize.getPrizeId());

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
     * If any persistence exception occurs, <code>ContestManagementException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemovePrizeFromContest_Failure5() throws Exception {
        try {
            initContext();

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
            bean.removePrizeFromContest(contest.getContestId(),
                prize.getPrizeId());

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getContestPrizes(long)</code>.
     * </p>
     *
     * <p>
     * There is a bug in Contest And Submission Entities for relation between Prize<->Contest,
     * this would lead to the method's implementation doesn't work.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetContestPrizes() throws Exception {
        initContext();
        entityManager.close();
        initContext();

        entityManager.getTransaction().begin();

        StudioFileType fileType = new StudioFileType();
        fileType.setDescription("desc");
        fileType.setExtension("ext");
        fileType.setImageFile(false);
        fileType.setSort(new Integer(10));
        entityManager.persist(fileType);

        ContestChannel contestChannel = new ContestChannel();
        contestChannel.setDescription("desc");
        contestChannel.setName("name");
        contestChannel.setFileType(fileType);
        contestChannel.setParentChannelId(new Long(1));
        entityManager.persist(contestChannel);

        ContestType contestType = new ContestType();
        contestType.setDescription("desc");
        contestType.setRequirePreviewFile(false);
        contestType.setRequirePreviewImage(false);
        entityManager.persist(contestType);

        ContestStatus status = new ContestStatus();
        status.setDescription("description");
        status.setName("name");
        entityManager.persist(status);

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
        contest.setStatus(status);
        entityManager.persist(contest);

        PrizeType prizeType = new PrizeType();
        prizeType.setDescription("desc");
        entityManager.persist(prizeType);

        Prize prize = new Prize();
        prize.setAmount(new Double(100));
        prize.setCreateDate(new Date());
        prize.setPlace(new Integer(1));
        prize.setType(prizeType);
        entityManager.persist(prize);

        // It should process successfully.
        bean.addPrizeToContest(contest.getContestId(), prize.getPrizeId());
        entityManager.getTransaction().commit();

        List<Prize> prizes = bean.getContestPrizes(contest.getContestId());

        assertEquals("The contest should have 1 prizes.", 1, prizes.size());

        entityManager.getTransaction().begin();
        entityManager.remove(prize);
        entityManager.remove(prizeType);
        entityManager.remove(contest);
        entityManager.remove(status);
        entityManager.remove(contestType);
        entityManager.remove(contestChannel);
        entityManager.remove(fileType);
        entityManager.getTransaction().commit();
    }

    /**
     * <p>
     * Accuracy test for <code>updateContest(Contest)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContest() throws Exception {
        // So the status won't be active.
        context.addEntry("activeContestStatusId", new Long(1000));
        initContext();

        ContestStatus status = new ContestStatus();
        status.setDescription("desc1");
        status.setName("s1");
        entityManager.persist(status);

        Contest contest = createContestForTest();
        contest.setStatus(status);
        entityManager.persist(contest);

        contest.setName("new name");
        bean.updateContest(contest);

        Contest persist = bean.getContest(contest.getContestId());
        assertEquals("The name should be updated.", "new name",
            persist.getName());
    }

    /**
     * <p>
     * Accuracy test for <code>updateContest(Contest)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContest1() throws Exception {
        entityManager = new MockEntityManager();

        ContestStatus status = new ContestStatus();
        status.setDescription("desc1");
        status.setName("s1");
        entityManager.persist(status);

        context.addEntry("unitName", "contestManager");
        context.addEntry("activeContestStatusId", status.getContestStatusId());
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

        context.addEntry("contestManager", entityManager);

        Contest contest = createContestForTest();
        contest.setConfig(new HashSet<ContestConfig>());
        contest.setStatus(status);
        entityManager.persist(contest);

        Date newDate = new Date();
        contest.setEndDate(newDate);
        bean.updateContest(contest);

        Contest persist = bean.getContest(contest.getContestId());
        assertEquals("The date should be updated.", newDate,
            persist.getEndDate());
    }

    /**
     * <p>
     * Failure test for <code>updateContest(Contest)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContest_Failure1() throws Exception {
        try {
            entityManager = new MockEntityManager();

            ContestStatus status = new ContestStatus();
            status.setDescription("desc1");
            status.setName("s1");
            entityManager.persist(status);

            context.addEntry("unitName", "contestManager");
            context.addEntry("activeContestStatusId",
                status.getContestStatusId());
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

            context.addEntry("contestManager", entityManager);

            Contest contest = createContestForTest();
            contest.setConfig(new HashSet<ContestConfig>());
            contest.setStatus(status);
            entityManager.persist(contest);

            Date newDate = new Date();
            contest.setEndDate(newDate);
            contest.setContestId(new Long(1000));
            bean.updateContest(contest);
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateContest(Contest)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContest_Failure2() throws Exception {
        try {
            entityManager = new MockEntityManager();

            ContestStatus status = new ContestStatus();
            status.setDescription("desc1");
            status.setName("s1");
            entityManager.persist(status);

            context.addEntry("unitName", "contestManager");
            context.addEntry("activeContestStatusId",
                status.getContestStatusId());
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

            context.addEntry("contestManager", entityManager);

            Contest contest = createContestForTest();
            contest.setConfig(new HashSet<ContestConfig>());
            contest.setStatus(status);
            entityManager.persist(contest);

            Date newDate = new Date();
            contest.setEndDate(newDate);
            contest.setContestChannel(null);
            bean.updateContest(contest);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateContest(Contest)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContest_Failure3() throws Exception {
        try {
            entityManager = new MockEntityManager();

            ContestStatus status = new ContestStatus();
            status.setDescription("desc1");
            status.setName("s1");
            entityManager.persist(status);

            context.addEntry("unitName", "contestManager");
            context.addEntry("activeContestStatusId",
                status.getContestStatusId());
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

            context.addEntry("contestManager", entityManager);

            Contest contest = createContestForTest();
            contest.setConfig(new HashSet<ContestConfig>());
            contest.setStatus(status);
            entityManager.persist(contest);

            Date newDate = new Date();
            contest.setEndDate(newDate);
            contest.setContestType(null);
            bean.updateContest(contest);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateContest(Contest)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContest_Failure4() throws Exception {
        try {
            entityManager = new MockEntityManager();

            ContestStatus status = new ContestStatus();
            status.setDescription("desc1");
            status.setName("s1");
            entityManager.persist(status);

            context.addEntry("unitName", "contestManager");
            context.addEntry("activeContestStatusId",
                status.getContestStatusId());
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

            context.addEntry("contestManager", entityManager);

            Contest contest = createContestForTest();
            contest.setConfig(new HashSet<ContestConfig>());
            contest.setStatus(status);
            entityManager.persist(contest);

            Contest newContest = new Contest();
            newContest.setContestChannel(contest.getContestChannel());
            newContest.setContestId(contest.getContestId());
            newContest.setContestType(contest.getContestType());
            newContest.setCreatedUser(new Long(1000));

            bean.updateContest(newContest);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateContest(Contest)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContest_Failure5() throws Exception {
        try {
            entityManager = new MockEntityManager();

            ContestStatus status = new ContestStatus();
            status.setDescription("desc1");
            status.setName("s1");
            entityManager.persist(status);

            context.addEntry("unitName", "contestManager");
            context.addEntry("activeContestStatusId",
                status.getContestStatusId());
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

            context.addEntry("contestManager", entityManager);

            Contest contest = createContestForTest();
            contest.setConfig(new HashSet<ContestConfig>());
            contest.setStatus(status);
            entityManager.persist(contest);

            Contest newContest = new Contest();
            newContest.setContestChannel(contest.getContestChannel());
            newContest.setContestId(contest.getContestId());
            newContest.setContestType(contest.getContestType());
            newContest.setCreatedUser(contest.getCreatedUser());
            newContest.setEventId(new Long(1000));

            bean.updateContest(newContest);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateContest(Contest)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContest_Failure6() throws Exception {
        try {
            entityManager = new MockEntityManager();

            ContestStatus status = new ContestStatus();
            status.setDescription("desc1");
            status.setName("s1");
            entityManager.persist(status);

            context.addEntry("unitName", "contestManager");
            context.addEntry("activeContestStatusId",
                status.getContestStatusId());
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

            context.addEntry("contestManager", entityManager);

            Contest contest = createContestForTest();
            contest.setConfig(new HashSet<ContestConfig>());
            contest.setStatus(status);
            entityManager.persist(contest);

            Contest newContest = new Contest();
            newContest.setContestChannel(contest.getContestChannel());
            newContest.setContestId(contest.getContestId());
            newContest.setContestType(contest.getContestType());
            newContest.setCreatedUser(contest.getCreatedUser());
            newContest.setEventId(contest.getEventId());
            newContest.setForumId(new Long(1000));

            bean.updateContest(newContest);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateContest(Contest)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContest_Failure7() throws Exception {
        try {
            entityManager = new MockEntityManager();

            ContestStatus status = new ContestStatus();
            status.setDescription("desc1");
            status.setName("s1");
            entityManager.persist(status);

            context.addEntry("unitName", "contestManager");
            context.addEntry("activeContestStatusId",
                status.getContestStatusId());
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

            context.addEntry("contestManager", entityManager);

            Contest contest = createContestForTest();
            contest.setConfig(new HashSet<ContestConfig>());
            contest.setStatus(status);
            entityManager.persist(contest);

            Contest newContest = new Contest();
            newContest.setContestChannel(contest.getContestChannel());
            newContest.setContestId(contest.getContestId());
            newContest.setContestType(contest.getContestType());
            newContest.setCreatedUser(contest.getCreatedUser());
            newContest.setEventId(contest.getEventId());
            newContest.setForumId(contest.getForumId());
            newContest.setName("new name...");

            bean.updateContest(newContest);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateContest(Contest)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContest_Failure8() throws Exception {
        try {
            entityManager = new MockEntityManager();

            ContestStatus status = new ContestStatus();
            status.setDescription("desc1");
            status.setName("s1");
            entityManager.persist(status);

            context.addEntry("unitName", "contestManager");
            context.addEntry("activeContestStatusId",
                status.getContestStatusId());
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

            context.addEntry("contestManager", entityManager);

            Contest contest = createContestForTest();
            contest.setConfig(new HashSet<ContestConfig>());
            contest.setStatus(status);
            entityManager.persist(contest);

            Contest newContest = new Contest();
            newContest.setContestChannel(contest.getContestChannel());
            newContest.setContestId(contest.getContestId());
            newContest.setContestType(contest.getContestType());
            newContest.setCreatedUser(contest.getCreatedUser());
            newContest.setEventId(contest.getEventId());
            newContest.setForumId(contest.getForumId());
            newContest.setName(contest.getName());
            newContest.setProjectId(contest.getProjectId());

            Date date = new Date();
            date.setTime(100);
            newContest.setStartDate(date);

            bean.updateContest(newContest);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateContest(Contest)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContest_Failure9() throws Exception {
        try {
            entityManager = new MockEntityManager();

            ContestStatus status = new ContestStatus();
            status.setDescription("desc1");
            status.setName("s1");
            entityManager.persist(status);

            context.addEntry("unitName", "contestManager");
            context.addEntry("activeContestStatusId",
                status.getContestStatusId());
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

            context.addEntry("contestManager", entityManager);

            Contest contest = createContestForTest();
            contest.setConfig(new HashSet<ContestConfig>());
            contest.setStatus(status);
            entityManager.persist(contest);

            Contest newContest = new Contest();
            newContest.setContestChannel(contest.getContestChannel());
            newContest.setContestId(contest.getContestId());
            newContest.setContestType(contest.getContestType());
            newContest.setCreatedUser(contest.getCreatedUser());
            newContest.setEventId(contest.getEventId());
            newContest.setForumId(contest.getForumId());
            newContest.setName(contest.getName());
            newContest.setProjectId(contest.getProjectId());
            newContest.setStartDate(contest.getStartDate());
            newContest.setStatus(null);

            bean.updateContest(newContest);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateContest(Contest)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContest_Failure10() throws Exception {
        try {
            entityManager = new MockEntityManager();

            ContestStatus status = new ContestStatus();
            status.setDescription("desc1");
            status.setName("s1");
            entityManager.persist(status);

            context.addEntry("unitName", "contestManager");
            context.addEntry("activeContestStatusId",
                status.getContestStatusId());
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

            context.addEntry("contestManager", entityManager);

            Contest contest = createContestForTest();
            contest.setConfig(new HashSet<ContestConfig>());
            contest.setStatus(status);
            entityManager.persist(contest);

            Contest newContest = new Contest();
            newContest.setContestChannel(contest.getContestChannel());
            newContest.setContestId(contest.getContestId());
            newContest.setContestType(contest.getContestType());
            newContest.setCreatedUser(contest.getCreatedUser());
            newContest.setEventId(contest.getEventId());
            newContest.setForumId(contest.getForumId());
            newContest.setName(contest.getName());
            newContest.setProjectId(contest.getProjectId());
            newContest.setStartDate(contest.getStartDate());
            newContest.setStatus(contest.getStatus());
            newContest.setTcDirectProjectId(new Long(1000));

            bean.updateContest(newContest);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateContest(Contest)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContest_Failure11() throws Exception {
        try {
            entityManager = new MockEntityManager();

            ContestStatus status = new ContestStatus();
            status.setDescription("desc1");
            status.setName("s1");
            entityManager.persist(status);

            context.addEntry("unitName", "contestManager");
            context.addEntry("activeContestStatusId",
                status.getContestStatusId());
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

            context.addEntry("contestManager", entityManager);

            Contest contest = createContestForTest();
            contest.setConfig(new HashSet<ContestConfig>());
            contest.setStatus(status);
            entityManager.persist(contest);

            Contest newContest = new Contest();
            newContest.setContestChannel(contest.getContestChannel());
            newContest.setContestId(contest.getContestId());
            newContest.setContestType(contest.getContestType());
            newContest.setCreatedUser(contest.getCreatedUser());
            newContest.setEventId(contest.getEventId());
            newContest.setForumId(contest.getForumId());
            newContest.setName(contest.getName());
            newContest.setProjectId(contest.getProjectId());
            newContest.setStartDate(contest.getStartDate());
            newContest.setStatus(contest.getStatus());
            newContest.setTcDirectProjectId(contest.getTcDirectProjectId());

            Date date = new Date();
            date.setTime(100);
            newContest.setWinnerAnnoucementDeadline(date);

            bean.updateContest(newContest);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateContest(Contest)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContest_Failure12() throws Exception {
        try {
            entityManager = new MockEntityManager();

            ContestStatus status = new ContestStatus();
            status.setDescription("desc1");
            status.setName("s1");
            entityManager.persist(status);

            context.addEntry("unitName", "contestManager");
            context.addEntry("activeContestStatusId",
                status.getContestStatusId());
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

            context.addEntry("contestManager", entityManager);

            Contest contest = createContestForTest();
            contest.setConfig(new HashSet<ContestConfig>());
            contest.setStatus(status);
            entityManager.persist(contest);

            Contest newContest = new Contest();
            newContest.setContestChannel(contest.getContestChannel());
            newContest.setContestId(contest.getContestId());
            newContest.setContestType(contest.getContestType());
            newContest.setCreatedUser(contest.getCreatedUser());
            newContest.setEventId(contest.getEventId());
            newContest.setForumId(contest.getForumId());
            newContest.setName(contest.getName());
            newContest.setProjectId(new Long(1000));
            newContest.setStartDate(contest.getStartDate());
            newContest.setStatus(contest.getStatus());
            newContest.setTcDirectProjectId(contest.getTcDirectProjectId());

            Date date = new Date();
            date.setTime(100);
            newContest.setWinnerAnnoucementDeadline(date);

            bean.updateContest(newContest);
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>updateContestStatus(long, long)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContestStatus() throws Exception {
        initContext();

        List<ContestStatus> list = new ArrayList<ContestStatus>();
        ContestStatus newStatus = new ContestStatus();
        newStatus.setDescription("desc2");
        newStatus.setName("s2");
        entityManager.persist(newStatus);
        list.add(newStatus);

        ContestStatus status = new ContestStatus();
        status.setDescription("desc");
        status.setName("s1");
        status.setStatuses(list);
        entityManager.persist(status);

        Contest contest = createContestForTest();
        contest.setStatus(status);
        bean.createContest(contest);

        bean.updateContestStatus(contest.getContestId(),
            newStatus.getContestStatusId());

        ContestStatus n = contest.getStatus();
        assertEquals("The status should be updated to s2", "s2", n.getName());
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
            initContext();

            bean.updateContestStatus(100, 100);
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
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
    public void testUpdateContestStatus_Failure2() throws Exception {
        try {
            initContext();

            Contest contest = createContestForTest();
            bean.createContest(contest);

            bean.updateContestStatus(contest.getContestId(), 100);
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateContestStatus(long, long)</code>.
     * </p>
     *
     * <p>
     * The contest can't be updated the specified status, <code>ContestStatusTransitionException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContestStatus_Failure3() throws Exception {
        try {
            initContext();

            ContestStatus status = new ContestStatus();
            status.setDescription("desc");
            status.setName("s1");
            status.setStatuses(new ArrayList<ContestStatus>());
            entityManager.persist(status);

            Contest contest = createContestForTest();
            contest.setStatus(status);

            bean.createContest(contest);

            ContestStatus newStatus = new ContestStatus();
            newStatus.setDescription("desc2");
            newStatus.setName("s2");
            entityManager.persist(newStatus);

            bean.updateContestStatus(contest.getContestId(),
                newStatus.getContestStatusId());
            fail("ContestStatusTransitionException is expected.");
        } catch (ContestStatusTransitionException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>updateContestStatus(long, long)</code>.
     * </p>
     *
     * <p>
     * The entitymanager is closed, <code>ContestManagementException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContestStatus_Failure4() throws Exception {
        try {
            initContext();

            ContestStatus status = new ContestStatus();
            status.setDescription("desc");
            status.setName("s1");
            status.setStatuses(new ArrayList<ContestStatus>());
            entityManager.persist(status);

            Contest contest = createContestForTest();
            contest.setStatus(status);

            bean.createContest(contest);

            ContestStatus newStatus = new ContestStatus();
            newStatus.setDescription("desc2");
            newStatus.setName("s2");
            entityManager.persist(newStatus);

            entityManager.close();

            bean.updateContestStatus(contest.getContestId(),
                newStatus.getContestStatusId());
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
     * <p>
     * The entitymanager throws persistence exception, <code>ContestManagementException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContestStatus_Failure5() throws Exception {
        try {
            initContext();

            ContestStatus status = new ContestStatus();
            status.setDescription("desc");
            status.setName("s1");
            status.setStatuses(new ArrayList<ContestStatus>());
            entityManager.persist(status);

            Contest contest = createContestForTest();
            contest.setStatus(status);

            bean.createContest(contest);

            ContestStatus newStatus = new ContestStatus();
            newStatus.setDescription("desc2");
            newStatus.setName("s2");
            entityManager.persist(newStatus);

            entityManager.enablePersistenceException(true);

            bean.updateContestStatus(contest.getContestId(),
                newStatus.getContestStatusId());
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
     * <p>
     * The entitymanager throws transaction exception, <code>ContestManagementException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateContestStatus_Failure6() throws Exception {
        try {
            initContext();

            ContestStatus status = new ContestStatus();
            status.setDescription("desc");
            status.setName("s1");
            status.setStatuses(new ArrayList<ContestStatus>());
            entityManager.persist(status);

            Contest contest = createContestForTest();
            contest.setStatus(status);

            bean.createContest(contest);

            ContestStatus newStatus = new ContestStatus();
            newStatus.setDescription("desc2");
            newStatus.setName("s2");
            entityManager.persist(newStatus);

            entityManager.enableTransactionException(true);

            bean.updateContestStatus(contest.getContestId(),
                newStatus.getContestStatusId());
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }
}
