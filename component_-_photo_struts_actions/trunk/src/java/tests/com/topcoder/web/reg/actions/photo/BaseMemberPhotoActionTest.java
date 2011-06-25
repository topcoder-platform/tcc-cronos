/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.photo;

import java.io.File;
import java.io.IOException;

import org.springframework.mock.web.MockHttpServletRequest;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.memberphoto.manager.MemberPhotoManager;

/**
 * <p> Unit test case of {@link BaseMemberPhotoAction}. </p>
 *
 * @author mumujava
 * @version 1.0
 */
public class BaseMemberPhotoActionTest extends BaseUnitTest {
    /**
     * <p>
     * The BaseMemberPhotoAction instance to test.
     * </p>
     * */
    private BaseMemberPhotoAction instance;

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        instance = new MockBaseMemberPhotoAction();
        instance.setAuditDAO(new MockAuditDAO());
        instance.setMemberPhotoManager(new MockMemberPhotoManager());
        instance.setUserDAO(new MockUserDAO());
        instance.setPhotoStoredDirectory("test_files/stored");
        instance.setLog(LogManager.getLog());
        instance.setAuthenticationSessionKey("authenticationkey");
        instance.checkParameters();
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p> Creates a test suite for the tests in this test case. </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(BaseMemberPhotoActionTest.class);
        return suite;
    }

    /**
     * <p>
     * Accuracy test for constructor BaseMemberPhotoAction.
     * It verifies instance is correctly created.
     * </p>
     * @throws Exception to Junit
     */
    public void test_BaseMemberPhotoAction0() throws Exception {
        assertNotNull("The instance should not be null", instance);
    }

    /**
     * <p>
     * Accuracy test for method checkParameters().
     * No error occurs if the instance is configured well.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters0() throws Exception {
        instance.checkParameters();
    }

    /**
     * <p>
     * Failure test for method checkParameters against invalid configuration.
     * Expects {@link MemberPhotoActionConfigurationException} if AuditDAO is null.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters1() throws Exception {
        instance.setAuditDAO(null);
        try {
            instance.checkParameters();
            fail("Expects an Exception");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for method checkParameters against invalid configuration.
     * Expects {@link MemberPhotoActionConfigurationException} if AuthenticationSessionKey is null.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters2() throws Exception {
        instance.setAuthenticationSessionKey(null);
        try {
            instance.checkParameters();
            fail("Expects an Exception");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for method checkParameters against invalid configuration.
     * Expects {@link MemberPhotoActionConfigurationException} if AuthenticationSessionKey is empty.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters3() throws Exception {
        instance.setAuthenticationSessionKey(EMPTY);
        try {
            instance.checkParameters();
            fail("Expects an Exception");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for method checkParameters against invalid configuration.
     * Expects {@link MemberPhotoActionConfigurationException} if userDAO is null.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters4() throws Exception {
        instance.setUserDAO(null);
        try {
            instance.checkParameters();
            fail("Expects an Exception");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for method checkParameters against invalid configuration.
     * Expects {@link MemberPhotoActionConfigurationException} if memberPhotoManager is null.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters5() throws Exception {
        instance.setMemberPhotoManager(null);
        try {
            instance.checkParameters();
            fail("Expects an Exception");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for method checkParameters against invalid configuration.
     * Expects {@link MemberPhotoActionConfigurationException} if PhotoStoredDirectory is null.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters6() throws Exception {
        instance.setPhotoStoredDirectory(null);
        try {
            instance.checkParameters();
            fail("Expects an Exception");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for method checkParameters against invalid configuration.
     * Expects {@link MemberPhotoActionConfigurationException} if PhotoStoredDirectory is empty.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters7() throws Exception {
        instance.setPhotoStoredDirectory(EMPTY);
        try {
            instance.checkParameters();
            fail("Expects an Exception");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method for {@link BaseMemberPhotoAction#getAuthenticationSessionKey()}.
     * It verifies the returned value is correct.
     * </p>
     */
    public void testGetAuthenticationSessionKey() {
        // set a value
        String obj = "test";
        instance.setAuthenticationSessionKey(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getAuthenticationSessionKey());
    }

    /**
     * <p>
     * Test method for {@link BaseMemberPhotoAction#setAuthenticationSessionKey()}. It verifies the assigned value is
     * correct.
     * </p>
     */
    public void testSetAuthenticationSessionKey() {
        // set a value
        String obj = "test";
        instance.setAuthenticationSessionKey(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getAuthenticationSessionKey());
    }

    /**
     * <p>
     * Test method for {@link BaseMemberPhotoAction#getUserDAO()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetUserDAO() {
        // set a value
        UserDAO obj = new MockUserDAO();
        instance.setUserDAO(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getUserDAO());
    }

    /**
     * <p>
     * Test method for {@link BaseMemberPhotoAction#setUserDAO()}. It verifies the assigned value is
     * correct.
     * </p>
     */
    public void testSetUserDAO() {
        // set a value
        UserDAO obj = new MockUserDAO();
        instance.setUserDAO(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getUserDAO());
    }

    /**
     * <p>
     * Test method for {@link BaseMemberPhotoAction#getAuditDAO()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetAuditDAO() {
        // set a value
        AuditDAO obj = new MockAuditDAO();
        instance.setAuditDAO(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getAuditDAO());
    }

    /**
     * <p>
     * Test method for {@link BaseMemberPhotoAction#setAuditDAO()}. It verifies the assigned value is
     * correct.
     * </p>
     */
    public void testSetAuditDAO() {
        // set a value
        AuditDAO obj = new MockAuditDAO();
        instance.setAuditDAO(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getAuditDAO());
    }

    /**
     * <p>
     * Test method for {@link BaseMemberPhotoAction#getMemberPhotoManager()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetMemberPhotoManager() {
        // set a value
        MemberPhotoManager obj = new MockMemberPhotoManager();
        instance.setMemberPhotoManager(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getMemberPhotoManager());
    }

    /**
     * <p>
     * Test method for {@link BaseMemberPhotoAction#setMemberPhotoManager()}. It verifies the assigned value is
     * correct.
     * </p>
     */
    public void testSetMemberPhotoManager() {
        // set a value
        MemberPhotoManager obj = new MockMemberPhotoManager();
        instance.setMemberPhotoManager(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getMemberPhotoManager());
    }

    /**
     * <p>
     * Test method for {@link BaseMemberPhotoAction#getLog()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetLog() {
        // set a value
        Log obj = LogManager.getLog();
        instance.setLog(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getLog());
    }

    /**
     * <p>
     * Test method for {@link BaseMemberPhotoAction#setLog()}. It verifies the assigned value is
     * correct.
     * </p>
     */
    public void testSetLog() {
        // set a value
        Log obj = LogManager.getLog();
        instance.setLog(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getLog());
    }

    /**
     * <p>
     * Test method for {@link BaseMemberPhotoAction#getPhotoStoredDirectory()}.
     * It verifies the returned value is correct.
     * </p>
     */
    public void testGetPhotoStoredDirectory() {
        // set a value
        String obj = "test";
        instance.setPhotoStoredDirectory(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getPhotoStoredDirectory());
    }

    /**
     * <p>
     * Test method for {@link BaseMemberPhotoAction#setPhotoStoredDirectory()}. It verifies the assigned value is
     * correct.
     * </p>
     */
    public void testSetPhotoStoredDirectory() {
        // set a value
        String obj = "test";
        instance.setPhotoStoredDirectory(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getPhotoStoredDirectory());
    }

    /**
     * <p>
     * Test method for {@link BaseMemberPhotoAction#move()}. It verifies the file is correctly moved.
     * </p>
     * @throws IOException if any error occurs
     */
    public void test_move() throws IOException {
        //prepare the file to move
        fileCopy("test_files/EmailEngine.xml", "test_files/removed/EmailEngine.xml");
        BaseMemberPhotoAction.move("test_files/removed/EmailEngine.xml",
                "test_files/removed/newEmailEngine.xml");
        assertFalse("The file should be correct moved", new File("test_files/removed/EmailEngine.xml")
                .isFile());
        assertTrue("The file should be correct moved", new File("test_files/removed/newEmailEngine.xml")
                .isFile());
    }

    /**
     * <p>
     * Test method for {@link BaseMemberPhotoAction#logRequestParameter()}.
     * No error occurs.
     * </p>
     */
    public void test_logRequestParameter() {
        instance.logRequestParameter(new MockHttpServletRequest());
    }
}
