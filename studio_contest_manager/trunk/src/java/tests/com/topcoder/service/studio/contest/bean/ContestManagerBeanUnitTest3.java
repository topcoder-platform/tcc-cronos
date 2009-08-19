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

import com.topcoder.service.studio.contest.Document;
import com.topcoder.service.studio.contest.EntityNotFoundException;
import com.topcoder.service.studio.contest.FilePath;
import com.topcoder.service.studio.contest.MimeType;
import com.topcoder.service.studio.contest.TestHelper;
import com.topcoder.service.studio.contest.documentcontentservers.SocketDocumentContentServer;


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
     * <p>
     * entities created during tests. They will be removed in the end of test.
     * </p>
     */
    private List entities = new ArrayList();

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
        context.addEntry("auditChange", new Boolean(false));
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
     * Accuracy test for <code>getDocumentContent(long)</code>.
     * </p>
     *
     * <p>
     * Verify that the content is added to the document.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetDocumentContent() throws Exception {
        SocketDocumentContentServer server = new SocketDocumentContentServer(40000,
                0);
        server.start();
        Thread.sleep(500);

        try {
            entityManager = new MockEntityManager();

            FilePath filePath = new FilePath();
            filePath.setModifyDate(new Date());
            filePath.setPath("c:");
            entityManager.persist(filePath);
            entities.add(filePath);
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
            mt.setMimeTypeId(1L);
            mt.setDescription("description");
            document.setMimeType(mt);
            entityManager.persist(mt);
            entities.add(mt);

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
    public void testGetDocumentContent_Failure() throws Exception {
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
     * If the document content doesn't exist, return false.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void tes1tExistDocumentContent() throws Exception {
        SocketDocumentContentServer server = new SocketDocumentContentServer(40000,
                0);
        server.start();

        try {
            entityManager = new MockEntityManager();

            FilePath filePath = new FilePath();
            filePath.setModifyDate(new Date());
            filePath.setPath("test_files");
            entityManager.persist(filePath);
            entities.add(filePath);

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
            document.setMimeType(mt);
            entityManager.persist(mt);
            entities.add(mt);

            bean.addDocument(document);

            boolean result = bean.existDocumentContent(document.getDocumentId());
            assertFalse("The result doesn't exist.", result);
        } finally {
            server.stop();
        }
    }

    /**
     * <p>
     * Failure test for <code>searchContests(Filter)</code>.
     * </p>
     * <p>
     * Passes in null filter value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testSearchContests_Failure1() throws Exception {
        try {
            bean.searchContests(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
}
