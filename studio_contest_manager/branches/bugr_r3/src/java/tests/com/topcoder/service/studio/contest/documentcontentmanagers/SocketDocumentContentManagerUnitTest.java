/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.documentcontentmanagers;

import com.topcoder.service.studio.contest.DocumentContentManagementException;
import com.topcoder.service.studio.contest.documentcontentservers.SocketDocumentContentServer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * Unit tests for <code>SocketDocumentContentManager</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SocketDocumentContentManagerUnitTest extends TestCase {
    /**
     * <p>The server port to connect to.</p>
     */
    private static int PORT = 20000;

    /**
     * <p>The <code>SocketDocumentContentManager</code> instance for testing.</p>
     */
    private SocketDocumentContentManager manager;

    /**
     * <p>The <code>SocketDocumentContentServer</code> instance for testing.</p>
     */
    private SocketDocumentContentServer server;

    /**
     * <p>The <code>MockServer</code> instance for testing.</p>
     */
    private MockServer mockServer;

    /**
     * <p>Sets up the environment.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        server = new SocketDocumentContentServer(++PORT, 0);

        manager = new SocketDocumentContentManager("127.0.0.1", PORT);

        mockServer = new MockServer(PORT, 0);
    }

    /**
     * <p>Destroys the environment.</p>
     */
    protected void tearDown() {
        server.stop();

        mockServer.stop();
    }

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(SocketDocumentContentManagerUnitTest.class);
    }

    /**
     * <p>
     * Accuracy test for constructor <code>SocketDocumentContentManager(Map)</code>.
     * </p>
     *
     * <p>
     * Verify that the document content manager can be created successfully.
     * </p>
     */
    public void testCtorMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("serverAddress", "127.0.0.1");
        map.put("serverPort", new Integer(10000));

        manager = new SocketDocumentContentManager(map);
        assertNotNull("Unable to create SocketDocumentContentManager instance.",
            manager);
    }

    /**
     * <p>
     * Failure test for <code>SocketDocumentContentManager(Map)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the map is null, <code>IllegalArgumentException</code>
     * is thrown.
     * </p>
     */
    public void testCtorMap_Failure1() {
        try {
            new SocketDocumentContentManager(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>SocketDocumentContentManager(Map)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the map doesn't contain key "serverAddress", <code>IllegalArgumentException</code>
     * is thrown.
     * </p>
     */
    public void testCtorMap_Failure2() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serverPort", new Integer(10000));
            map.put("unknown", new Integer(10000));

            new SocketDocumentContentManager(map);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>SocketDocumentContentManager(Map)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the map doesn't contain key "serverPort", <code>IllegalArgumentException</code>
     * is thrown.
     * </p>
     */
    public void testCtorMap_Failure3() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serverAddress", "127.0.0.1");
            map.put("unknown", new Integer(10000));

            new SocketDocumentContentManager(map);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>SocketDocumentContentManager(Map)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the map contains additional pairs, <code>IllegalArgumentException</code>
     * is thrown.
     * </p>
     */
    public void testCtorMap_Failure4() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serverAddress", "127.0.0.1");
            map.put("serverPort", new Integer(10000));
            map.put("unknown", "sss");

            new SocketDocumentContentManager(map);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>SocketDocumentContentManager(Map)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the value for key "serverAddress" isn't instance of String,
     * <code>IllegalArgumentException</code> is thrown.
     * </p>
     */
    public void testCtorMap_Failure5() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serverAddress", new Object());
            map.put("serverPort", new Integer(10000));

            new SocketDocumentContentManager(map);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>SocketDocumentContentManager(Map)</code>.
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

            new SocketDocumentContentManager(map);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>SocketDocumentContentManager(Map)</code>.
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

            new SocketDocumentContentManager(map);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>SocketDocumentContentManager(Map)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the value for key "serverPort" isn't instance of Integer,
     * <code>IllegalArgumentException</code> is thrown.
     * </p>
     */
    public void testCtorMap_Failure8() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serverAddress", "127.0.0.1");
            map.put("serverPort", new Object());

            new SocketDocumentContentManager(map);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>SocketDocumentContentManager(Map)</code>.
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

            new SocketDocumentContentManager(map);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>SocketDocumentContentManager(Map)</code>.
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

            new SocketDocumentContentManager(map);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for constructor <code>SocketDocumentContentManager(String, int)</code>.
     * </p>
     *
     * <p>
     * Verify that the document content manager can be created successfully.
     * </p>
     */
    public void testCtorStringInt() {
        manager = new SocketDocumentContentManager("127.0.0.1", PORT);
        assertNotNull("Unable to create SocketDocumentContentManager instance.",
            manager);

        manager = new SocketDocumentContentManager("127.0.0.1", 0);
        assertNotNull("Unable to create SocketDocumentContentManager instance.",
            manager);

        manager = new SocketDocumentContentManager("127.0.0.1", 65535);
        assertNotNull("Unable to create SocketDocumentContentManager instance.",
            manager);
    }

    /**
     * <p>
     * Failure test for constructor <code>SocketDocumentContentManager(String, int)</code>.
     * </p>
     *
     * <p>
     * Failure cause: if the serverAddress is null, <code>IllegalArgumentException</code>
     * is thrown.
     * </p>
     */
    public void testCtorStringInt_Failure1() {
        try {
            new SocketDocumentContentManager(null, PORT);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for constructor <code>SocketDocumentContentManager(String, int)</code>.
     * </p>
     *
     * <p>
     * Failure cause: if the serverAddress is empty, <code>IllegalArgumentException</code>
     * is thrown.
     * </p>
     */
    public void testCtorStringInt_Failure2() {
        try {
            new SocketDocumentContentManager("   ", PORT);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for constructor <code>SocketDocumentContentManager(String, int)</code>.
     * </p>
     *
     * <p>
     * Failure cause: if the serverPort is less than 0, <code>IllegalArgumentException</code>
     * is thrown.
     * </p>
     */
    public void testCtorStringInt_Failure3() {
        try {
            new SocketDocumentContentManager("127.0.0.1", -1);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for constructor <code>SocketDocumentContentManager(String, int)</code>.
     * </p>
     *
     * <p>
     * Failure cause: if the serverPort is bigger than 65535, <code>IllegalArgumentException</code>
     * is thrown.
     * </p>
     */
    public void testCtorStringInt_Failure4() {
        try {
            new SocketDocumentContentManager("127.0.0.1", 65536);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
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
     * @throws Exception to JUnit.
     */
    public void testSaveDocumentContent() throws Exception {

        server.start();

        // Wait the worker thread to begin.
        Thread.sleep(500);

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
     * Failure cause: The file with specified path is a directory, <code>DocumentContentManagementException</code>
     * will be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSaveDocumentContent_Failure() throws Exception {

        server.start();

        // Wait the worker thread to begin.
        Thread.sleep(500);

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
     * If content is empty, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSaveDocumentContest_Failure1() throws Exception {
        try {
            manager.saveDocumentContent("unknow", new byte[0]);
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
     * If path is empty, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
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
     * @throws Exception to JUnit.
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
     * Failure test for method <code>saveDocumentContent(String, byte[])</code>.
     * </p>
     *
     * <p>
     * If first byte of response isn't 0 or 1, <code>DocumentContentManagementException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSaveDocumentContest_Failure4() throws Exception {

        // Construct a wrong response.
        byte[] msg = new byte[2];

        // The first can only be 1 or 0.
        msg[0] = 10;

        mockServer.setMsg(msg);
        mockServer.start();
        // Wait the worker thread to begin.
        Thread.sleep(500);

        try {
            manager.saveDocumentContent("test_files/test", new byte[] {1});
            fail("DocumentContentManagementException is expected.");
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
     * If response is success, but it has additional ending bytes,
     * <code>DocumentContentManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSaveDocumentContest_Failure5() throws Exception {

        // Construct a wrong response.
        byte[] msg = new byte[2];

        // The first can only be 1 or 0.
        msg[0] = 1;
        msg[1] = 2;

        mockServer.setMsg(msg);
        mockServer.start();
        // Wait the worker thread to begin.
        Thread.sleep(500);

        try {
            manager.saveDocumentContent("test_files/test", new byte[] {1});
            fail("DocumentContentManagementException is expected.");
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
     * If response fails, but it doesn't have 2 bytes to indicates the length of error message,
     * <code>DocumentContentManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSaveDocumentContest_Failure6() throws Exception {

        // Construct a wrong response.
        byte[] msg = new byte[2];

        // The first can only be 1 or 0.
        msg[0] = 0;
        msg[1] = 2;

        mockServer.setMsg(msg);
        mockServer.start();
        // Wait the worker thread to begin.
        Thread.sleep(500);

        try {
            manager.saveDocumentContent("test_files/test", new byte[] {1});
            fail("DocumentContentManagementException is expected.");
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
     * If response fails, but the length flag doesn't match the error message's length,
     * <code>DocumentContentManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSaveDocumentContest_Failure7() throws Exception {

        // Construct a wrong response.
        byte[] msg = new byte[40];

        msg[0] = 0;
        msg[1] = 0;
        msg[2] = 20;

        mockServer.setMsg(msg);
        mockServer.start();
        // Wait the worker thread to begin.
        Thread.sleep(500);

        try {
            manager.saveDocumentContent("test_files/test", new byte[] {1});
            fail("DocumentContentManagementException is expected.");
        } catch (DocumentContentManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>getDocumentContent(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the document content with specified path will be returned successfully.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetDocumentContent1() throws Exception {

        server.start();

        // Wait the worker thread to begin.
        Thread.sleep(500);

        String path = new File("test_files/test4").getAbsolutePath();

        manager.getDocumentContent(path);
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
     * @throws Exception to JUnit.
     */
    public void testGetDocumentContent2() throws Exception {

        server.start();

        // Wait the worker thread to begin.
        Thread.sleep(500);

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
     * @throws Exception to JUnit.
     */
    public void testGetDocumentContent3() throws Exception {

        server.start();

        // Wait the worker thread to begin.
        Thread.sleep(500);

        String path = new File("test_files/test5").getAbsolutePath();

        manager.getDocumentContent(path);
    }

    /**
     * <p>
     * Failure test for method <code>getDocumentContent(String)</code>.
     * </p>
     *
     * <p>
     * If response fails, but it doesn't have 2 bytes to indicate the message's length,
     * <code>DocumentContentManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetDocumentContest_Failure1() throws Exception {

        // Construct a wrong response.
        byte[] msg = new byte[2];

        msg[0] = 0;
        msg[1] = 1;

        mockServer.setMsg(msg);
        mockServer.start();
        // Wait the worker thread to begin.
        Thread.sleep(500);

        try {
            manager.getDocumentContent("test_files/test");
            fail("DocumentContentManagementException is expected.");
        } catch (DocumentContentManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>getDocumentContent(String)</code>.
     * </p>
     *
     * <p>
     * If response fails, and its message length is less than 2 bytes,
     * <code>DocumentContentManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetDocumentContest_Failure2() throws Exception {

        // Construct a wrong response.
        byte[] msg = new byte[3];

        msg[0] = 0;
        msg[1] = 0;
        msg[2] = 2;

        mockServer.setMsg(msg);
        mockServer.start();
        // Wait the worker thread to begin.
        Thread.sleep(500);

        try {
            manager.getDocumentContent("test_files/test");
            fail("DocumentContentManagementException is expected.");
        } catch (DocumentContentManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>getDocumentContent(String)</code>.
     * </p>
     *
     * <p>
     * If response fails, and its message length is less than 2 bytes,
     * <code>DocumentContentManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetDocumentContest_Failure3() throws Exception {

        // Construct a wrong response.
        byte[] msg = new byte[5];

        msg[0] = 0;
        msg[1] = 0;
        msg[2] = 2;

        mockServer.setMsg(msg);
        mockServer.start();
        Thread.sleep(500);

        try {
            manager.getDocumentContent("test_files/test");
            fail("DocumentContentManagementException is expected.");
        } catch (DocumentContentManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>getDocumentContent(String)</code>.
     * </p>
     *
     * <p>
     * If response fails, and its message length is bigger than 2 bytes,
     * <code>DocumentContentManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetDocumentContest_Failure4() throws Exception {

        // Construct a wrong response.
        byte[] msg = new byte[10];

        msg[0] = 0;
        msg[1] = 0;
        msg[2] = 7;

        mockServer.setMsg(msg);
        mockServer.start();
        Thread.sleep(500);

        try {
            manager.getDocumentContent("test_files/test");
            fail("DocumentContentManagementException is expected.");
        } catch (DocumentContentManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>getDocumentContent(String)</code>.
     * </p>
     *
     * <p>
     * If response succeeds, and it doesn't have 4 bytes to indicates the content's length,
     * <code>DocumentContentManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetDocumentContest_Failure5() throws Exception {

        // Construct a wrong response.
        byte[] msg = new byte[4];

        msg[0] = 1;
        msg[1] = 0;

        mockServer.setMsg(msg);
        mockServer.start();
        Thread.sleep(500);

        try {
            manager.getDocumentContent("test_files/test");
            fail("DocumentContentManagementException is expected.");
        } catch (DocumentContentManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>getDocumentContent(String)</code>.
     * </p>
     *
     * <p>
     * If the first byte of response isn't 0 either 1,
     * <code>DocumentContentManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetDocumentContest_Failure6() throws Exception {

        // Construct a wrong response.
        byte[] msg = new byte[5];

        msg[0] = 2;

        mockServer.setMsg(msg);
        mockServer.start();
        Thread.sleep(500);

        try {
            manager.getDocumentContent("test_files/test");
            fail("DocumentContentManagementException is expected.");
        } catch (DocumentContentManagementException e) {
            // success
        }
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
     * @throws Exception to JUnit.
     */
    public void testExistDocumentContent1() throws Exception {

        server.start();

        // Wait the worker thread to begin.
        Thread.sleep(500);

        String path = new File("test_files/test6").getAbsolutePath();

        manager.existDocumentContent(path);
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
     * @throws Exception to JUnit.
     */
    public void testExistDocumentContent2() throws Exception {

        server.start();

        // Wait the worker thread to begin.
        Thread.sleep(500);

        String path = new File("test_files/unexist").getAbsolutePath();

        boolean existed = manager.existDocumentContent(path);

        assertFalse("The document content should not exist.", existed);
    }

    /**
     * <p>
     * Failure test for method <code>existDocumentContent(String)</code>.
     * </p>
     *
     * <p>
     * If the first byte of response isn't 0 either 1,
     * <code>DocumentContentManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testExistDocumentContest_Failure1() throws Exception {

        // Construct a wrong response.
        byte[] msg = new byte[5];

        msg[0] = 2;

        mockServer.setMsg(msg);
        mockServer.start();
        Thread.sleep(500);

        try {
            manager.existDocumentContent("test_files/test");
            fail("DocumentContentManagementException is expected.");
        } catch (DocumentContentManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>existDocumentContent(String)</code>.
     * </p>
     *
     * <p>
     * If the response fails,
     * <code>DocumentContentManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testExistDocumentContest_Failure2() throws Exception {

        // Construct a wrong response.
        byte[] msg = new byte[5];

        msg[0] = 0;

        mockServer.setMsg(msg);
        mockServer.start();
        Thread.sleep(500);

        try {
            manager.existDocumentContent("test_files/test");
            fail("DocumentContentManagementException is expected.");
        } catch (DocumentContentManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>existDocumentContent(String)</code>.
     * </p>
     *
     * <p>
     * If the response succeeds, but the second byte isn't 0 or 1,
     * <code>DocumentContentManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testExistDocumentContest_Failure3() throws Exception {

        // Construct a wrong response.
        byte[] msg = new byte[2];

        msg[0] = 0;
        msg[1] = 2;

        mockServer.setMsg(msg);
        mockServer.start();
        Thread.sleep(500);

        try {
            manager.existDocumentContent("test_files/test");
            fail("DocumentContentManagementException is expected.");
        } catch (DocumentContentManagementException e) {
            // success
        }
    }
}
