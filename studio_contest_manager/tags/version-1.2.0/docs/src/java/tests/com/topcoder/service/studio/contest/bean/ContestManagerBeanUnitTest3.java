/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.bean;

import com.topcoder.service.studio.contest.Document;
import com.topcoder.service.studio.contest.EntityNotFoundException;
import com.topcoder.service.studio.contest.FilePath;
import com.topcoder.service.studio.contest.documentcontentservers.SocketDocumentContentServer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.Date;


/**
 * <p>
 * Unit tests for <code>ContestManagerBean</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestManagerBeanUnitTest3 extends TestCase {
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
        return new TestSuite(ContestManagerBeanUnitTest3.class);
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
     * Accuracy test for <code>getDocumentContent(long)</code>.
     * </p>
     *
     * <p>
     * Verify that the content is added to the document.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void tes1tGetDocumentContent() throws Exception {
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

            byte[] result = bean.getDocumentContent(document.getDocumentId());
            assertEquals("The result should match.", 4, result.length);
            assertEquals("The first byte should be 1.", 1, result[0]);
        } finally {
            server.stop();
        }
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
    public void testGetDocumentContent_Failure2() throws Exception {
        try {
            initContext();

            bean.getDocumentContent(100);
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>existDocumentContent(long)</code>.
     * </p>
     *
     * <p>
     * If the document content exist, return true.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void te1stExistDocumentContent1() throws Exception {
        SocketDocumentContentServer server = new SocketDocumentContentServer(40000,
                0);
        Thread.sleep(1000);
        server.start();
        Thread.sleep(1000);

        try {
            initContext();

            FilePath filePath = new FilePath();
            filePath.setModifyDate(new Date());
            filePath.setPath("test_files");
            entityManager.persist(filePath);

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

            boolean result = bean.existDocumentContent(document.getDocumentId());
            assertTrue("The result should exist.", result);
        } finally {
            server.stop();
        }
    }

    /**
     * <p>
     * Accuracy test for <code>existDocumentContent(long)</code>.
     * </p>
     *
     * <p>
     * If the document content doesn't exist, return false.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testExistDocumentContent2() throws Exception {
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

            boolean result = bean.existDocumentContent(document.getDocumentId());
            assertFalse("The result doesn't exist.", result);
        } finally {
            server.stop();
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
    public void te1stExistDocumentContent_Failure() throws Exception {
        try {
            initContext();

            // It should process successfully.
            bean.existDocumentContent(100);
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // success
        }
    }
}
