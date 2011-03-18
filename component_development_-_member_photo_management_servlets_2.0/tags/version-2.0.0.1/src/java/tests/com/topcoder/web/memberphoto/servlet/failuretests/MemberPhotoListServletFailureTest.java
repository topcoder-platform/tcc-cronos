/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet.failuretests;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.topcoder.web.memberphoto.servlet.MemberPhotoListServlet;
import com.topcoder.web.memberphoto.servlet.MemberPhotoListingException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

/**
 * Failure test for MemberPhotoListServlet class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MemberPhotoListServletFailureTest extends TestCase {
    /**
     * Represents the instance of MemberPhotoListServlet used in unit test.
     */
    private MemberPhotoListServlet instance;

    /**
     * Represents the HttpServletRequest instance.
     */
    private MockHttpServletRequest request;

    /**
     * Represents the HttpServletResponse instance.
     */
    private HttpServletResponse response;

    /**
     * Represents the HttpSession instance.
     */
    private HttpSession session;

    /**
     * Returns the test suite of this class.
     * @return the test suite.
     */
    public static Test suite() {
        return new TestSuite(MemberPhotoListServletFailureTest.class);
    }

    /**
     * Set up for each test cases.
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        response = new MockHttpServletResponse();

        session = new MockHttpSession();
        session.setAttribute("isAdministrator", Boolean.TRUE);

        request = new MockHttpServletRequest();
        request.setSession(session);
        request.setParameter("memberId", "1");

        instance = new MemberPhotoListServlet();
        instance.setIsAdministratorSessionKey("isAdministrator");
        instance.setMemberIdParameterName("memberId");
        instance.setMemberPhotoManager(new MockMemberPhotoManager());
        instance.setNotAdministratorErrorName("notAdministrator");
        instance.setPageNumberParameterName("pageNumber");
        instance.setPageSizeParameterName("pageSize");
        instance.setPhotoListName("photoList");
        instance.setSuccessResultForwardUrl("successResultForwardUrl");
        instance.setValidationErrorForwardUrl("validationErrorForwardUrl");
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When request is null.
     * @throws Exception to JUnit
     */
    public void testDoPost_RequestIsNull() throws Exception {
        try {
            instance.doPost(null, response);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When response is null.
     * @throws Exception to JUnit
     */
    public void testDoPost_ResponseIsNull() throws Exception {
        try {
            instance.doPost(request, null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * memberIdParameterName is not
     * set.
     * @throws Exception to JUnit
     */
    public void testDoPost_MemberIdParameterNameIsNotSet() throws Exception {
        FailureTestHelper.setField(instance, "memberIdParameterName", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * pageNumberParameterName is
     * not set.
     * @throws Exception to JUnit
     */
    public void testDoPost_PageNumberParameterNameIsNotSet() throws Exception {
        FailureTestHelper.setField(instance, "pageNumberParameterName", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * pageSizeParameterName is not
     * set.
     * @throws Exception to JUnit
     */
    public void testDoPost_PageSizeParameterNameIsNotSet() throws Exception {
        FailureTestHelper.setField(instance, "pageSizeParameterName", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * isAdministratorSessionKey is
     * not set.
     * @throws Exception to JUnit
     */
    public void testDoPost_IsAdministratorSessionKeyIsNotSet() throws Exception {
        FailureTestHelper.setField(instance, "isAdministratorSessionKey", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * notAdministratorErrorName is
     * not set.
     * @throws Exception to JUnit
     */
    public void testDoPost_NotAdministratorErrorNameIsNotSet() throws Exception {
        FailureTestHelper.setField(instance, "notAdministratorErrorName", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * validationErrorForwardUrl is
     * not set.
     * @throws Exception to JUnit
     */
    public void testDoPost_ValidationErrorForwardUrlIsNotSet() throws Exception {
        FailureTestHelper.setField(instance, "validationErrorForwardUrl", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * successResultForwardUrl is
     * not set.
     * @throws Exception to JUnit
     */
    public void testDoPost_SuccessResultForwardUrlIsNotSet() throws Exception {
        FailureTestHelper.setField(instance, "successResultForwardUrl", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When memberPhotoManager
     * is not
     * set.
     * @throws Exception to JUnit
     */
    public void testDoPost_MemberPhotoManagerIsNotSet() throws Exception {
        FailureTestHelper.setField(instance, "memberPhotoManager", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When photoListName is
     * not set.
     * @throws Exception to JUnit
     */
    public void testDoPost_PhotoListNameIsNotSet() throws Exception {
        FailureTestHelper.setField(instance, "photoListName", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * memberIdParameterName is
     * invalid.
     * @throws Exception to JUnit
     */
    public void testDoPost_MemberIdParameterNameIsInvalid() throws Exception {
        request.setParameter("memberId", "A");

        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (MemberPhotoListingException ex) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * isAdministratorSessionKey is
     * invalid.
     * @throws Exception to JUnit
     */
    public void testDoPost_PageNumberParameterNameIsInvalid() throws Exception {
        request.getSession().setAttribute("isAdministrator", "A");

        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (MemberPhotoListingException ex) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When memberPhotoManager
     * throw
     * exception.
     * @throws Exception to JUnit
     */
    public void testDoPost_MemberPhotoManagerIsInvalid() throws Exception {
        request.setParameter("pageNumber", "101");
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (MemberPhotoListingException ex) {
            // OK
        }
    }

    /**
     * Failure test for setMemberIdParameterName(String). When memberIdParameterName is null.
     */
    public void testSetMemberIdParameterName_MemberIdParameterNameIsNull() {
        try {
            instance.setMemberIdParameterName(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setMemberIdParameterName(String). When memberIdParameterName is empty.
     */
    public void testSetMemberIdParameterName_MemberIdParameterNameIsEmpty() {
        try {
            instance.setMemberIdParameterName(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setPageNumberParameterName(String). When pageNumberParameterName is null.
     */
    public void testSetPageNumberParameterName_PageNumberParameterNameIsNull() {
        try {
            instance.setPageNumberParameterName(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setPageNumberParameterName(String). When pageNumberParameterName is empty.
     */
    public void testSetPageNumberParameterName_PageNumberParameterNameIsEmpty() {
        try {
            instance.setPageNumberParameterName(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setPageSizeParameterName(String). When pageSizeParameterName is null.
     */
    public void testSetIsAdministratorSessionKey_PageSizeParameterNameIsNull() {
        try {
            instance.setPageSizeParameterName(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setPageSizeParameterName(String). When pageSizeParameterName is empty.
     */
    public void testSetIsAdministratorSessionKey_PageSizeParameterNameIsEmpty() {
        try {
            instance.setPageSizeParameterName(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setIsAdministratorSessionKey(String). When isAdministratorSessionKey is null.
     */
    public void testSetIsAdministratorSessionKey_IsAdministratorSessionKeyIsNull() {
        try {
            instance.setIsAdministratorSessionKey(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setIsAdministratorSessionKey(String). When isAdministratorSessionKey is empty.
     */
    public void testSetIsAdministratorSessionKey_IsAdministratorSessionKeyIsEmpty() {
        try {
            instance.setIsAdministratorSessionKey(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setNotAdministratorErrorName(String). When notAdministratorErrorName is null.
     */
    public void testSetNotAdministratorErrorName_NotAdministratorErrorNameIsNull() {
        try {
            instance.setNotAdministratorErrorName(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setNotAdministratorErrorName(String). When notAdministratorErrorName is empty.
     */
    public void testSetNotAdministratorErrorName_NotAdministratorErrorNameIsEmpty() {
        try {
            instance.setNotAdministratorErrorName(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for ValidationErrorForwardUrl(String). When validationErrorForwardUrl is null.
     */
    public void testSetValidationErrorForwardUrl_SuccessResultForwardUrlIsNull() {
        try {
            instance.setSuccessResultForwardUrl(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for ValidationErrorForwardUrl(String). When validationErrorForwardUrl is empty.
     */
    public void testSetValidationErrorForwardUrl_SuccessResultForwardUrlIsEmpty() {
        try {
            instance.setValidationErrorForwardUrl(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setSuccessResultForwardUrl(String). When successResultForwardUrl is null.
     */
    public void testSetSuccessResultForwardUrl_SuccessResultForwardUrlIsNull() {
        try {
            instance.setSuccessResultForwardUrl(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setSuccessResultForwardUrl(String). When successResultForwardUrl is empty.
     */
    public void testSetSuccessResultForwardUrl_SuccessResultForwardUrlIsEmpty() {
        try {
            instance.setSuccessResultForwardUrl(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setMemberPhotoManager(MemberPhotoManager). When memberPhotoManager is null.
     */
    public void testSetMemberPhotoManager_MemberPhotoManagerIsNull() {
        try {
            instance.setMemberPhotoManager(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setPhotoListName(String). When photoListName is null.
     */
    public void testSetPhotoListName_PhotoListNameIsNull() {
        try {
            instance.setPhotoListName(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setPhotoListName(String). When photoListName is empty.
     */
    public void testSetPhotoListName_PhotoListNameIsEmpty() {
        try {
            instance.setPhotoListName(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }
}