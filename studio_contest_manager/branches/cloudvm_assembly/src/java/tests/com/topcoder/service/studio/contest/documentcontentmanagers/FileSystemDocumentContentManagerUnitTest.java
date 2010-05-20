/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.documentcontentmanagers;

import com.topcoder.service.studio.contest.DocumentContentManagementException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Unit tests for <code>FileSystemDocumentContentManager</code> class.
 * </p>
 *
 * @author superZZ
 * @version 1.0
 */
public class FileSystemDocumentContentManagerUnitTest extends TestCase {
    /**
     * the FileSystemDocumentContentManager.
     */
    private FileSystemDocumentContentManager manager;

    /**
     * <p>
     * Sets up the environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void setUp() throws Exception {
        manager = new FileSystemDocumentContentManager();
    }

    /**
     * <p>
     * Tear down the environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void tearDown() throws Exception {
        File file = new File("test_files/test");

        if (file.exists()) {
            file.delete();
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
        return new TestSuite(FileSystemDocumentContentManagerUnitTest.class);
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>SocketDocumentContentManager(Map)</code>.
     * </p>
     *
     * <p>
     * Verify that the document content manager can be created successfully.
     * </p>
     */
    public void testCtorMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("path", "c:");

        new FileSystemDocumentContentManager(map);
    }

    /**
     * <p>
     * Failure test for <code>FileSystemDocumentContentManager(Map)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the map is null, <code>IllegalArgumentException</code>
     * is thrown.
     * </p>
     */
    public void testCtorMap_Failure1() {
        try {
            new FileSystemDocumentContentManager((String) null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>FileSystemDocumentContentManager(Map)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the map doesn't contain key "serverAddress",
     * <code>IllegalArgumentException</code> is thrown.
     * </p>
     */
    public void testCtorMap_Failure2() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serverPort", new Integer(10000));
            map.put("unknown", new Integer(10000));

            new FileSystemDocumentContentManager(map);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>FileSystemDocumentContentManager(Map)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the map doesn't contain key "serverPort",
     * <code>IllegalArgumentException</code> is thrown.
     * </p>
     */
    public void testCtorMap_Failure3() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serverAddress", "127.0.0.1");
            map.put("unknown", new Integer(10000));

            new FileSystemDocumentContentManager(map);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>FileSystemDocumentContentManager(Map)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the map contains additional pairs,
     * <code>IllegalArgumentException</code> is thrown.
     * </p>
     */
    public void testCtorMap_Failure4() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serverAddress", "127.0.0.1");
            map.put("serverPort", new Integer(10000));
            map.put("unknown", "sss");

            new FileSystemDocumentContentManager(map);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>FileSystemDocumentContentManager(Map)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the value for key "serverAddress" isn't instance of
     * String, <code>IllegalArgumentException</code> is thrown.
     * </p>
     */
    public void testCtorMap_Failure5() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serverAddress", new Object());
            map.put("serverPort", new Integer(10000));

            new FileSystemDocumentContentManager(map);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>FileSystemDocumentContentManager(Map)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the value for key "serverAddress" is null String,
     * <code>IllegalArgumentException</code> is thrown.
     * </p>
     */
    public void testCtorMap_Failure6() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serverAddress", null);
            map.put("serverPort", new Integer(10000));

            new FileSystemDocumentContentManager(map);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>FileSystemDocumentContentManager(Map)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the value for key "serverAddress" is empty String,
     * <code>IllegalArgumentException</code> is thrown.
     * </p>
     */
    public void testCtorMap_Failure7() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serverAddress", "   ");
            map.put("serverPort", new Integer(10000));

            new FileSystemDocumentContentManager(map);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>FileSystemDocumentContentManager(Map)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the value for key "serverPort" isn't instance of
     * Integer, <code>IllegalArgumentException</code> is thrown.
     * </p>
     */
    public void testCtorMap_Failure8() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serverAddress", "127.0.0.1");
            map.put("serverPort", new Object());

            new FileSystemDocumentContentManager(map);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>FileSystemDocumentContentManager(Map)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the value for key "serverPort" is less than 0,
     * <code>IllegalArgumentException</code> is thrown.
     * </p>
     */
    public void testCtorMap_Failure9() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serverAddress", "127.0.0.1");
            map.put("serverPort", new Integer(-1));

            new FileSystemDocumentContentManager(map);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>FileSystemDocumentContentManager(Map)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the value for key "serverPort" is bigger than 65535,
     * <code>IllegalArgumentException</code> is thrown.
     * </p>
     */
    public void testCtorMap_Failure10() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serverAddress", "127.0.0.1");
            map.put("serverPort", new Integer(65536));

            new FileSystemDocumentContentManager(map);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>FileSystemDocumentContentManager(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the document content manager can be created successfully.
     * </p>
     */
    public void testCtorString() {
        manager = new FileSystemDocumentContentManager("c:");
    }

    /**
     * <p>
     * Accuracy test for method <code>saveDocumentContent(String, byte[])</code>.
     * </p>
     *
     * <p>
     * Verify that the content was written to the file with specified path.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSaveDocumentContent() throws Exception {
        File file = new File("test_files/test");

        if (file.exists()) {
            file.delete();
        }

        String path = file.getAbsolutePath();

        byte[] content = "saveDocumentContent".getBytes();

        manager.saveDocumentContent(path, content);

        assertTrue("The document should be created.", file.exists());
    }

    /**
     * <p>
     * Failure test for method <code>saveDocumentContent(String, byte[])</code>.
     * </p>
     *
     * <p>
     * Failure cause: The file with specified path is a directory,
     * <code>DocumentContentManagementException</code> will be thrown.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSaveDocumentContent_Failure() throws Exception {
        String path = new File("test_files").getAbsolutePath();

        byte[] content = "saveDocumentContent".getBytes();

        try {
            manager.saveDocumentContent(path, content);
        } catch (DocumentContentManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>saveDocumentContent(String, byte[])</code>.
     * </p>
     *
     * <p>
     * If path is empty, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSaveDocumentContest_Failure2() throws Exception {
        try {
            manager.saveDocumentContent("  ", new byte[] {1});
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>saveDocumentContent(String, byte[])</code>.
     * </p>
     *
     * <p>
     * If path is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSaveDocumentContest_Failure3() throws Exception {
        try {
            manager.saveDocumentContent(null, new byte[] {1});
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>getDocumentContent(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the document content with specified path will be returned
     * successfully.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetDocumentContent1() throws Exception {
        String path = new File("test_files/test4").getAbsolutePath();

        byte[] content = manager.getDocumentContent(path);

        assertEquals("The content should match.", "saveDocumentContent",
                new String(content));
    }

    /**
     * <p>
     * Accuracy test for method <code>getDocumentContent(String)</code>.
     * </p>
     *
     * <p>
     * If the document content doesn't exist, null is returned.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetDocumentContent2() throws Exception {
        String path = new File("test_files/unexist").getAbsolutePath();

        byte[] content = manager.getDocumentContent(path);

        assertNull("The result bytes should be null.", content);
    }

    /**
     * <p>
     * Accuracy test for method <code>getDocumentContent(String)</code>.
     * </p>
     *
     * <p>
     * If the document content is empty, returned bytes is an empty array.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetDocumentContent3() throws Exception {
        String path = new File("test_files/test5").getAbsolutePath();

        byte[] content = manager.getDocumentContent(path);

        assertEquals("The content should be empty.", 0, content.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>existDocumentContent(String)</code>.
     * </p>
     *
     * <p>
     * If the document content exist, return true.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testExistDocumentContent1() throws Exception {
        String path = new File("test_files/test6").getAbsolutePath();

        boolean existed = manager.existDocumentContent(path);

        assertTrue("The document content should exist.", existed);
    }

    /**
     * <p>
     * Accuracy test for method <code>existDocumentContent(String)</code>.
     * </p>
     *
     * <p>
     * If the document content doesn't exist, return false.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testExistDocumentContent2() throws Exception {
        String path = new File("test_files/unexist").getAbsolutePath();

        boolean existed = manager.existDocumentContent(path);

        assertFalse("The document content should not exist.", existed);
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>moveDocumentToContestFolder(String, long)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testMoveDocumentToContestFolder() throws Exception {
        File file = new File("test_files/test");
        if (file.exists()) {
            file.delete();
        }
        String path = file.getAbsolutePath();
        byte[] content = "saveDocumentContent".getBytes();
        manager.saveDocumentContent(path, content);
        assertTrue("The document should be created.", file.exists());

        manager.moveDocumentToContestFolder(path, 2013);
        assertTrue("Document should be moved.",
                new File("test_files/2013/test").exists());

        file = new File("test_files/2013/test");
        if (file.exists()) {
            file.delete();
        }
    }
}
