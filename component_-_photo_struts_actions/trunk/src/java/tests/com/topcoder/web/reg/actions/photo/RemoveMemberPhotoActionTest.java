/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.photo;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.DocumentGeneratorFactory;
import com.topcoder.util.log.LogManager;

/**
 * <p> Unit test case of {@link RemoveMemberPhotoAction}. </p>
 *
 * @author mumujava
 * @version 1.0
 */
public class RemoveMemberPhotoActionTest extends BaseUnitTest {
    /**
     * <p>
     * The RemoveMemberPhotoAction instance to test.
     * </p>
     * */
    private RemoveMemberPhotoAction instance;

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        instance = new RemoveMemberPhotoAction();
        instance.setAuditDAO(new MockAuditDAO());
        DocumentGenerator generator = DocumentGeneratorFactory
                .getDocumentGenerator(new ConfigurationFileManager("test_files/DocumentManager.xml")
                        .getConfiguration("com.topcoder.util.file").getChild("com.topcoder.util.file"));
        instance.setDocumentGenerator(generator);
        instance.setEmailBodyTemplateFileName("test_files/emailTemplate.txt");
        instance.setEmailSendFlag(true);
        instance.setFromEmailAddress("from@topcoder.com");
        instance.setMemberPhotoManager(new MockMemberPhotoManager());
        instance.setUserDAO(new MockUserDAO());
        instance.setRemovalReasonParameterName("removalReason");
        instance.setPhotoStoredDirectory("test_files/stored");
        instance.setPhotoRemovedDirectory("test_files/removed");
        instance.setLog(LogManager.getLog());
        instance.setAuthenticationSessionKey("authenticationkey");
        instance.setEmailSubject("Email subject");
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
        TestSuite suite = new TestSuite(RemoveMemberPhotoActionTest.class);
        return suite;
    }

    /**
     * <p>
     * Accuracy test for constructor RemoveMemberPhotoAction.
     * It verifies instance is correctly created.
     * </p>
     * @throws Exception to Junit
     */
    public void test_RemoveMemberPhotoAction0() throws Exception {
        assertNotNull("The instance should not be null", instance);
    }

    /**
     * <p>
     * Accuracy test for method execute.
     * It verifies the member photo is removed.
     * </p>
     * @throws Exception to Junit
     */
    public void test_execute() throws Exception {
        //restore the image for removing
        fileCopy("test_files/stored/image100.bak.gif", "test_files/stored/image100.gif");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        request.setParameter("removalReason", "I just want to remove it");
        session.setAttribute("authenticationkey", new MockBasicAuthentication());
        instance.setServletRequest(request);

        String res = instance.execute();

        assertEquals("Incorrect result", "success", res);
        assertFalse("The stored image should be moved to dir 'removed'", new File(
                "test_files/stored/image100.gif").isFile());
        assertTrue("The stored image should be moved to dir 'removed'", new File(
                "test_files/removed/image100.gif").isFile());
    }

    /**
     * <p>
     * Failure test for method execute().
     *
     * Expects {@link MemberPhotoRemovalException} if BasicAuthentication is null.
     * </p>
     * @throws Exception to Junit
     */
    public void test_execute2() throws Exception {
        //restore the image for removing
        fileCopy("test_files/stored/image100.bak.gif", "test_files/stored/image100.gif");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        request.setParameter("removalReason", "I just want to remove it");
        instance.setServletRequest(request);

        try {
            instance.execute();
            fail("Expects an Exception");
        } catch (MemberPhotoRemovalException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for method execute().
     *
     * Expects {@link MemberPhotoRemovalException} if from address is invalid.
     * </p>
     * @throws Exception to Junit
     */
    public void test_execute3() throws Exception {
        //restore the image for removing
        fileCopy("test_files/stored/image100.bak.gif", "test_files/stored/image100.gif");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        request.setParameter("removalReason", "I just want to remove it");
        session.setAttribute("authenticationkey", new MockBasicAuthentication());
        instance.setServletRequest(request);
        instance.setFromEmailAddress(":?+/\\");

        try {
            instance.execute();
            fail("Expects an Exception");
        } catch (MemberPhotoRemovalException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for method execute().
     *
     * Expects {@link MemberPhotoRemovalException} if emailBodyTemplateFile is not found.
     * </p>
     * @throws Exception to Junit
     */
    public void test_execute4() throws Exception {
        //restore the image for removing
        fileCopy("test_files/stored/image100.bak.gif", "test_files/stored/image100.gif");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        request.setParameter("removalReason", "I just want to remove it");
        session.setAttribute("authenticationkey", new MockBasicAuthentication());
        instance.setServletRequest(request);
        instance.setEmailBodyTemplateFileName("the file doesn't exist");

        try {
            instance.execute();
            fail("Expects an Exception");
        } catch (MemberPhotoRemovalException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for method execute().
     *
     * Expects {@link MemberPhotoRemovalException} if emailBodyTemplateFile is invalid.
     * </p>
     * @throws Exception to Junit
     */
    public void test_execute5() throws Exception {
        //restore the image for removing
        fileCopy("test_files/stored/image100.bak.gif", "test_files/stored/image100.gif");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        request.setParameter("removalReason", "I just want to remove it");
        session.setAttribute("authenticationkey", new MockBasicAuthentication());
        instance.setServletRequest(request);
        instance.setEmailBodyTemplateFileName("test_files/invalidEmailTemplate.txt");

        try {
            instance.execute();
            fail("Expects an Exception");
        } catch (MemberPhotoRemovalException e) {
            // pass
        }
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
     * Expects {@link MemberPhotoActionConfigurationException} if DocumentGenerator is null.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters1() throws Exception {
        instance.setDocumentGenerator(null);
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
     * Expects {@link MemberPhotoActionConfigurationException} if EmailBodyTemplateFileName is null.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters2() throws Exception {
        instance.setEmailBodyTemplateFileName(null);
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
     * Expects {@link MemberPhotoActionConfigurationException} if EmailBodyTemplateFileName is empty.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters3() throws Exception {
        instance.setEmailBodyTemplateFileName(EMPTY);
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
     * Expects {@link MemberPhotoActionConfigurationException} if RemovalReasonParameterName is null.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters4() throws Exception {
        instance.setRemovalReasonParameterName(null);
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
     * Expects {@link MemberPhotoActionConfigurationException} if RemovalReasonParameterName is empty.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters5() throws Exception {
        instance.setRemovalReasonParameterName(EMPTY);
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
     * Expects {@link MemberPhotoActionConfigurationException} if PhotoRemovedDirectory is null.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters6() throws Exception {
        instance.setPhotoRemovedDirectory(null);
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
     * Expects {@link MemberPhotoActionConfigurationException} if PhotoRemovedDirectory is empty.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters7() throws Exception {
        instance.setPhotoRemovedDirectory(EMPTY);
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
     * Expects {@link MemberPhotoActionConfigurationException} if EmailSubject is null.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters11() throws Exception {
        instance.setEmailSubject(null);
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
     * Expects {@link MemberPhotoActionConfigurationException} if EmailSubject is empty.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters12() throws Exception {
        instance.setEmailSubject(EMPTY);
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
     * Expects {@link MemberPhotoActionConfigurationException} if FromEmailAddress is null.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters8() throws Exception {
        instance.setFromEmailAddress(null);
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
     * Expects {@link MemberPhotoActionConfigurationException} if FromEmailAddress is empty.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters9() throws Exception {
        instance.setFromEmailAddress(EMPTY);
        try {
            instance.checkParameters();
            fail("Expects an Exception");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method for {@link RemoveMemberPhotoAction#getDocumentGenerator()}.
     * It verifies the returned value is correct.
     * </p>
     */
    public void testGetDocumentGenerator() {
        // set a value
        DocumentGenerator obj = new DocumentGenerator();
        instance.setDocumentGenerator(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getDocumentGenerator());
    }

    /**
     * <p>
     * Test method for {@link RemoveMemberPhotoAction#setDocumentGenerator()}. It verifies the assigned value is
     * correct.
     * </p>
     */
    public void testSetDocumentGenerator() {
        // set a value
        DocumentGenerator obj = new DocumentGenerator();
        instance.setDocumentGenerator(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getDocumentGenerator());
    }

    /**
     * <p>
     * Test method for {@link RemoveMemberPhotoAction#getRemovalReasonParameterName()}.
     * It verifies the returned value is correct.
     * </p>
     */
    public void testGetRemovalReasonParameterName() {
        // set a value
        String obj = "test";
        instance.setRemovalReasonParameterName(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getRemovalReasonParameterName());
    }

    /**
     * <p>
     * Test method for {@link RemoveMemberPhotoAction#setRemovalReasonParameterName()}.
     *  It verifies the assigned value is
     * correct.
     * </p>
     */
    public void testSetRemovalReasonParameterName() {
        // set a value
        String obj = "test";
        instance.setRemovalReasonParameterName(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getRemovalReasonParameterName());
    }

    /**
     * <p>
     * Test method for {@link RemoveMemberPhotoAction#isEmailSendFlag()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetEmailSendFlag() {
        // set a value
        instance.setEmailSendFlag(true);
        assertEquals("Incorrect value after set a new one", true, instance.isEmailSendFlag());
    }

    /**
     * <p>
     * Test method for {@link RemoveMemberPhotoAction#setEmailSendFlag()}. It verifies the assigned value is
     * correct.
     * </p>
     */
    public void testSetEmailSendFlag() {
        // set a value
        instance.setEmailSendFlag(true);
        assertEquals("Incorrect value after set a new one", true, instance.isEmailSendFlag());
    }

    /**
     * <p>
     * Test method for {@link RemoveMemberPhotoAction#getPhotoRemovedDirectory()}.
     * It verifies the returned value is correct.
     * </p>
     */
    public void testGetPhotoRemovedDirectory() {
        // set a value
        String obj = "test";
        instance.setPhotoRemovedDirectory(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getPhotoRemovedDirectory());
    }

    /**
     * <p>
     * Test method for {@link RemoveMemberPhotoAction#setPhotoRemovedDirectory()}. It verifies the assigned value is
     * correct.
     * </p>
     */
    public void testSetPhotoRemovedDirectory() {
        // set a value
        String obj = "test";
        instance.setPhotoRemovedDirectory(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getPhotoRemovedDirectory());
    }

    /**
     * <p>
     * Test method for {@link RemoveMemberPhotoAction#getEmailBodyTemplateFileName()}.
     * It verifies the returned value is correct.
     * </p>
     */
    public void testGetEmailBodyTemplateFileName() {
        // set a value
        String obj = "test";
        instance.setEmailBodyTemplateFileName(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getEmailBodyTemplateFileName());
    }

    /**
     * <p>
     * Test method for {@link RemoveMemberPhotoAction#setEmailBodyTemplateFileName()}. It verifies the assigned value is
     * correct.
     * </p>
     */
    public void testSetEmailBodyTemplateFileName() {
        // set a value
        String obj = "test";
        instance.setEmailBodyTemplateFileName(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getEmailBodyTemplateFileName());
    }

    /**
     * <p>
     * Test method for {@link RemoveMemberPhotoAction#getEmailSubject()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetEmailSubject() {
        // set a value
        String obj = "test";
        instance.setEmailSubject(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getEmailSubject());
    }

    /**
     * <p>
     * Test method for {@link RemoveMemberPhotoAction#setEmailSubject()}. It verifies the assigned value is
     * correct.
     * </p>
     */
    public void testSetEmailSubject() {
        // set a value
        String obj = "test";
        instance.setEmailSubject(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getEmailSubject());
    }

    /**
     * <p>
     * Test method for {@link RemoveMemberPhotoAction#getFromEmailAddress()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetFromEmailAddress() {
        // set a value
        String obj = "test";
        instance.setFromEmailAddress(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getFromEmailAddress());
    }

    /**
     * <p>
     * Test method for {@link RemoveMemberPhotoAction#setFromEmailAddress()}. It verifies the assigned value is
     * correct.
     * </p>
     */
    public void testSetFromEmailAddress() {
        // set a value
        String obj = "test";
        instance.setFromEmailAddress(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getFromEmailAddress());
    }

    /**
     * <p>
     * Test method for {@link RemoveMemberPhotoAction#setServletRequest()}. It verifies the assigned value is
     * correct.
     * </p>
     * @throws Exception to junit
     */
    public void test_setServletRequest() throws Exception {
        // set a value
        HttpServletRequest request = new MockHttpServletRequest();
        instance.setServletRequest(request);
        assertEquals("Incorrect object after set a new one", request, outject(instance.getClass(), instance,
                "request"));
    }
}
