/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.failuretests;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager;

/**
 * <p>
 * Failure tests for SocketDocumentContentManager class.
 * </p>
 * 
 * @author kaqi072821
 * @version 1.0
 */
public class SocketDocumentContentManagerFailureTest extends TestCase {
    /**
     * <p>
     * The server port to connect to.
     * </p>
     */
    private static int PORT = 20000;

    /**
     * <p>
     * The SocketDocumentContentManager instance for testing.
     * </p>
     */
    private SocketDocumentContentManager managerUnderTest;

    /**
     * <p>
     * Sets up the environment.
     * </p>
     * 
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {

        managerUnderTest = new SocketDocumentContentManager("127.0.0.1", PORT);

    }

    /**
     * <p>
     * Destroys the environment.
     * </p>
     */
    protected void tearDown() {
    }

    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     * 
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(SocketDocumentContentManagerFailureTest.class);
    }

    /**
     * <p>
     * Failure test for SocketDocumentContentManager(Map).
     * </p>
     * 
     * <p>
     * Failure cause: If the map is null, IllegalArgumentException is thrown.
     * </p>
     */
    public void test_CtorMap_Failure1() {
        try {
            new SocketDocumentContentManager(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for SocketDocumentContentManager(Map).
     * </p>
     * 
     * <p>
     * Failure cause: If the map doesn't contain key "serverAddress", IllegalArgumentException is thrown.
     * </p>
     */
    public void test_CtorMap_Failure2() {
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
     * Failure test for SocketDocumentContentManager(Map).
     * </p>
     * 
     * <p>
     * Failure cause: If the map doesn't contain key "serverPort", IllegalArgumentException is thrown.
     * </p>
     */
    public void test_CtorMap_Failure3() {
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
     * Failure test for SocketDocumentContentManager(Map).
     * </p>
     * 
     * <p>
     * Failure cause: If the map contains additional pairs, IllegalArgumentException is thrown.
     * </p>
     */
    public void test_CtorMap_Failure4() {
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
     * Failure test for SocketDocumentContentManager(Map).
     * </p>
     * 
     * <p>
     * Failure cause: If the value for key "serverAddress" isn't instance of String, IllegalArgumentException is thrown.
     * </p>
     */
    public void test_CtorMap_Failure5() {
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
     * Failure test for SocketDocumentContentManager(Map).
     * </p>
     * 
     * <p>
     * Failure cause: If the value for key "serverAddress" is null String, IllegalArgumentException is thrown.
     * </p>
     */
    public void test_CtorMap_Failure6() {
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
     * Failure test for SocketDocumentContentManager(Map).
     * </p>
     * 
     * <p>
     * Failure cause: If the value for key "serverAddress" is empty String, IllegalArgumentException is thrown.
     * </p>
     */
    public void test_CtorMap_Failure7() {
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
     * Failure test for SocketDocumentContentManager(Map).
     * </p>
     * 
     * <p>
     * Failure cause: If the value for key "serverPort" isn't instance of Integer, IllegalArgumentException is thrown.
     * </p>
     */
    public void test_CtorMap_Failure8() {
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
     * Failure test for SocketDocumentContentManager(Map).
     * </p>
     * 
     * <p>
     * Failure cause: If the value for key "serverPort" is less than 0, IllegalArgumentException is thrown.
     * </p>
     */
    public void test_CtorMap_Failure9() {
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
     * Failure test for SocketDocumentContentManager(Map).
     * </p>
     * 
     * <p>
     * Failure cause: If the value for key "serverPort" is bigger than 65535, IllegalArgumentException is thrown.
     * </p>
     */
    public void test_CtorMap_Failure10() {
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
     * Failure test for constructor SocketDocumentContentManager(String, int).
     * </p>
     * 
     * <p>
     * Failure cause: if the serverAddress is null, IllegalArgumentException is thrown.
     * </p>
     */
    public void test_CtorStringInt_Failure1() {
        try {
            new SocketDocumentContentManager(null, PORT);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for constructor SocketDocumentContentManager(String, int).
     * </p>
     * 
     * <p>
     * Failure cause: if the serverAddress is empty, IllegalArgumentException is thrown.
     * </p>
     */
    public void test_CtorStringInt_Failure2() {
        try {
            new SocketDocumentContentManager("   ", PORT);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for constructor SocketDocumentContentManager(String, int).
     * </p>
     * 
     * <p>
     * Failure cause: if the serverPort is less than 0, IllegalArgumentException is thrown.
     * </p>
     */
    public void test_CtorStringInt_Failure3() {
        try {
            new SocketDocumentContentManager("127.0.0.1", -1);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for constructor SocketDocumentContentManager(String, int).
     * </p>
     * 
     * <p>
     * Failure cause: if the serverPort is bigger than 65535, IllegalArgumentException is thrown.
     * </p>
     */
    public void test_CtorStringInt_Failure4() {
        try {
            new SocketDocumentContentManager("127.0.0.1", 65536);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method saveDocumentContent(String, byte[]).
     * </p>
     * 
     * <p>
     * If content is empty, IllegalArgumentException is expected.
     * </p>
     * 
     * @throws Exception to JUnit.
     */
    public void test_SaveDocumentContest_Failure1() throws Exception {
        File file = new File("test_files/test");

        if (file.exists()) {
            file.delete();
        }
        try {
            managerUnderTest.saveDocumentContent(file.getAbsolutePath(), new byte[0]);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method saveDocumentContent(String, byte[]).
     * </p>
     * 
     * <p>
     * If path is empty, IllegalArgumentException is expected.
     * </p>
     * 
     * @throws Exception to JUnit.
     */
    public void test_SaveDocumentContest_Failure2() throws Exception {
        try {
            managerUnderTest.saveDocumentContent("  ", new byte[] { 1 });
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method saveDocumentContent(String, byte[]).
     * </p>
     * 
     * <p>
     * If path is null, IllegalArgumentException is expected.
     * </p>
     * 
     * @throws Exception to JUnit.
     */
    public void test_SaveDocumentContest_Failure3() throws Exception {
        try {
            managerUnderTest.saveDocumentContent(null, new byte[] { 1 });
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method getDocumentContent(String).
     * </p>
     * 
     * <p>
     * IllegalArgumentException expected when parameter is null.
     * </p>
     * 
     * @throws Exception to JUnit.
     */
    public void test_GetDocumentContest_Failure_null() throws Exception {

        try {
            managerUnderTest.getDocumentContent(null);
            fail("DocumentContentManagementException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method getDocumentContent(String).
     * </p>
     * 
     * <p>
     * IllegalArgumentException expected when parameter is empty.
     * </p>
     * 
     * @throws Exception to JUnit.
     */
    public void test_GetDocumentContest_Failure_empty() throws Exception {

        try {
            managerUnderTest.getDocumentContent(" ");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method existDocumentContent(String).
     * </p>
     * 
     * <p>
     * IllegalArgumentException expected when the parameter is null.
     * </p>
     * 
     * @throws Exception to JUnit.
     */
    public void test_ExistDocumentContest_null() throws Exception {

        try {
            managerUnderTest.existDocumentContent(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method existDocumentContent(String).
     * </p>
     * 
     * <p>
     * IllegalArgumentException expected when the parameter is empty.
     * </p>
     * 
     * @throws Exception to JUnit.
     */
    public void test_ExistDocumentContest_empty() throws Exception {

        try {
            managerUnderTest.existDocumentContent(" ");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

}
