/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet;

import java.lang.reflect.Field;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import com.topcoder.web.memberphoto.manager.MemberPhotoManager;
import com.topcoder.web.memberphoto.manager.MemberPhotoManagerImpl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit tests for <code>MemberPhotoListServlet</code> class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MemberPhotoListServletTest extends TestCase {
    /**
     * <p>
     * String constant for testing.
     * </p>
     */
    private static final String TEST = "the_test_string";


    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(MemberPhotoListServletTest.class);
    }

    /**
     * <p>
     * Set up the environment.
     * </p>
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
    }

    /**
     * <p>
     * Destroy the environment.
     * </p>
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.shutDownRunner();
    }

    /**
     * <p>
     * Use reflect to get private field value.
     * </p>
     * @param instance the instance
     * @param fieldName the field name
     * @return the field value
     */
    private String getStringValue(Object instance, String fieldName) {
        try {
            Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            return (String) field.get(instance);
        } catch (SecurityException e) {
            return null;
        } catch (NoSuchFieldException e) {
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    /**
     * <p>
     * Use reflect to get private field value.
     * </p>
     * @param instance the instance
     * @param fieldName the field name
     * @return the field value
     */
    private Object getValue(Object instance, String fieldName) {
        try {
            Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            return field.get(instance);
        } catch (SecurityException e) {
            return null;
        } catch (NoSuchFieldException e) {
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    /**
     * <p>
     * Accuracy test for constructor <code>MemberPhotoListServlet()</code>.
     * </p>
     * <p>
     * Verify that the instance can be created successfully.
     * </p>
     */
    public void testEmptyCtorAccuracy() {
        MemberPhotoListServlet instance = new MemberPhotoListServlet();
        assertNotNull("Unable to create MemberPhotoListServlet instance.",
                instance);
        assertTrue("Should be instance of MemberPhotoListServlet.",
                instance instanceof MemberPhotoListServlet);
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setMemberIdParameterName(String memberIdParameterName)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetMemberIdParameterNameAccuracy() {
        MemberPhotoListServlet instance = new MemberPhotoListServlet();
        instance.setMemberIdParameterName(TEST);

        String value = getStringValue(instance, "memberIdParameterName");
        assertEquals("'memberIdParameterName' should be the same.", TEST, value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setMemberIdParameterName(String memberIdParameterName)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetMemberIdParameterNameFailure1() {
        MemberPhotoListServlet instance = new MemberPhotoListServlet();

        try {
            instance.setMemberIdParameterName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setMemberIdParameterName(String memberIdParameterName)</code>.
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetMemberIdParameterNameFailure2() {
        MemberPhotoListServlet instance = new MemberPhotoListServlet();

        try {
            instance.setMemberIdParameterName(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setIsAdministratorSessionKey(String isAdministratorSessionKey)</code>
     * .
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetIsAdministratorSessionKeyAccuracy() {
        MemberPhotoListServlet instance = new MemberPhotoListServlet();
        instance.setIsAdministratorSessionKey(TEST);

        String value = getStringValue(instance, "isAdministratorSessionKey");
        assertEquals("'isAdministratorSessionKey' should be the same.", TEST,
                value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setIsAdministratorSessionKey(String isAdministratorSessionKey)</code>
     * .
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetIsAdministratorSessionKeyFailure1() {
        MemberPhotoListServlet instance = new MemberPhotoListServlet();

        try {
            instance.setIsAdministratorSessionKey(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setIsAdministratorSessionKey(String isAdministratorSessionKey)</code>
     * .
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetIsAdministratorSessionKeyFailure2() {
        MemberPhotoListServlet instance = new MemberPhotoListServlet();

        try {
            instance.setIsAdministratorSessionKey(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setMemberPhotoManager(memberPhotoManager)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetMemberPhotoManagerAccuracy() {
        MemberPhotoListServlet instance = new MemberPhotoListServlet();
        MemberPhotoManager manager = new MemberPhotoManagerImpl();
        instance.setMemberPhotoManager(manager);

        MemberPhotoManager value =
                (MemberPhotoManager) getValue(instance, "memberPhotoManager");
        assertEquals("'memberPhotoManager' should be the same.", manager, value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setMemberPhotoManager(memberPhotoManager)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetMemberPhotoManagerFailure1() {
        MemberPhotoListServlet instance = new MemberPhotoListServlet();

        try {
            instance.setMemberPhotoManager(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setNotAdministratorErrorName(notAdministratorErrorName)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetNotAdministratorErrorNameAccuracy() {
        MemberPhotoListServlet instance = new MemberPhotoListServlet();
        instance.setNotAdministratorErrorName(TEST);

        String value = getStringValue(instance, "notAdministratorErrorName");
        assertEquals("'notAdministratorErrorName' should be the same.", TEST,
                value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setNotAdministratorErrorName(notAdministratorErrorName)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetNotAdministratorErrorNameFailure1() {
        MemberPhotoListServlet instance = new MemberPhotoListServlet();
        try {
            instance.setNotAdministratorErrorName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setNotAdministratorErrorName(notAdministratorErrorName)</code>.
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetNotAdministratorErrorNameFailure2() {
        MemberPhotoListServlet instance = new MemberPhotoListServlet();
        try {
            instance.setNotAdministratorErrorName(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setPageNumberParameterName(String pageNumberParameterName)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetPageNumberParameterNameAccuracy() {
        MemberPhotoListServlet instance = new MemberPhotoListServlet();
        instance.setPageNumberParameterName(TEST);

        String value = getStringValue(instance, "pageNumberParameterName");
        assertEquals("'pageNumberParameterName' should be the same.", TEST,
                value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setPageNumberParameterName(String pageNumberParameterName)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetPageNumberParameterNameFailure1() {
        MemberPhotoListServlet instance = new MemberPhotoListServlet();
        try {
            instance.setPageNumberParameterName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setPageNumberParameterName(String pageNumberParameterName)</code>.
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetPageNumberParameterNameFailure2() {
        MemberPhotoListServlet instance = new MemberPhotoListServlet();
        try {
            instance.setPageNumberParameterName(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setPageSizeParameterName(String pageSizeParameterName)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetPageSizeParameterNameAccuracy() {
        MemberPhotoListServlet instance = new MemberPhotoListServlet();
        instance.setPageSizeParameterName(TEST);

        String value = getStringValue(instance, "pageSizeParameterName");
        assertEquals("'pageSizeParameterName' should be the same.", TEST, value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setPageSizeParameterName(String pageSizeParameterName)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetPageSizeParameterNameFailure1() {
        MemberPhotoListServlet instance = new MemberPhotoListServlet();
        try {
            instance.setPageSizeParameterName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setPageSizeParameterName(String pageSizeParameterName)</code>.
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetPageSizeParameterNameFailure2() {
        MemberPhotoListServlet instance = new MemberPhotoListServlet();
        try {
            instance.setPageSizeParameterName(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setPhotoListName(String photoListName)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetPhotoListNameAccuracy() {
        MemberPhotoListServlet instance = new MemberPhotoListServlet();
        instance.setPhotoListName(TEST);

        String value = getStringValue(instance, "photoListName");
        assertEquals("'photoListName' should be the same.", TEST, value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setPhotoListName(String photoListName)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetPhotoListNameFailure1() {
        MemberPhotoListServlet instance = new MemberPhotoListServlet();
        try {
            instance.setPhotoListName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setPhotoListName(String photoListName)</code>.
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetPhotoListNameFailure2() {
        MemberPhotoListServlet instance = new MemberPhotoListServlet();
        try {
            instance.setPhotoListName(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setSuccessResultForwardUrl(successResultForwardUrl)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetSuccessResultForwardUrlAccuracy() {
        MemberPhotoListServlet instance = new MemberPhotoListServlet();
        instance.setSuccessResultForwardUrl(TEST);

        String value = getStringValue(instance, "successResultForwardUrl");
        assertEquals("'successResultForwardUrl' should be the same.", TEST,
                value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setSuccessResultForwardUrl(successResultForwardUrl)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetSuccessResultForwardUrlFailure1() {
        MemberPhotoListServlet instance = new MemberPhotoListServlet();

        try {
            instance.setSuccessResultForwardUrl(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setSuccessResultForwardUrl(successResultForwardUrl)</code>.
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetSuccessResultForwardUrlFailure2() {
        MemberPhotoListServlet instance = new MemberPhotoListServlet();

        try {
            instance.setSuccessResultForwardUrl(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setValidationErrorForwardUrl(validationErrorForwardUrl)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetValidationErrorForwardUrlAccuracy() {
        MemberPhotoListServlet instance = new MemberPhotoListServlet();
        instance.setValidationErrorForwardUrl(TEST);

        String value = getStringValue(instance, "validationErrorForwardUrl");
        assertEquals("'validationErrorForwardUrl' should be the same.", TEST,
                value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setValidationErrorForwardUrl(validationErrorForwardUrl)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetValidationErrorForwardUrlFailure1() {
        MemberPhotoListServlet instance = new MemberPhotoListServlet();

        try {
            instance.setValidationErrorForwardUrl(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setValidationErrorForwardUrl(validationErrorForwardUrl)</code>.
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetValidationErrorForwardUrlFailure2() {
        MemberPhotoListServlet instance = new MemberPhotoListServlet();

        try {
            instance.setValidationErrorForwardUrl(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * Verify that can handle the request and response correctly.
     * </p>
     * <p>
     * isAdministratorSessionKey is null, validate error.
     * </p>
     * <p>
     * forward to error URL.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostAccuracy1() throws Exception {
        MemberPhotoListServlet bean = TestHelper.genListServletBean();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("member_id", "717");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("is_admin_session_key", null);
        request.setSession(session);

        bean.doPost(request, response);
        assertEquals("error message should be the same.",
                "The user is not an administrator.",
                request.getAttribute("not_admin_error_name").toString());
        assertEquals("forward url should be the same.",
                "validation_error_forward_url", response.getForwardedUrl());
    }

    /**
     * <p>
     * Accuracy test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * The user is not an administrator, forward to error URL.
     * </p>
     * <p>
     * forward to error URL.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostAccuracy2() throws Exception {
        MemberPhotoListServlet bean = TestHelper.genListServletBean();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("member_id", "717");
        request.setParameter("page_number_parameter_name", "0");
        request.setParameter("page_size_parameter_name", "50");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("is_admin_session_key", new Boolean(false));
        request.setSession(session);

        bean.doPost(request, response);
        assertEquals("error message should be same.",
                "The user is not an administrator.",
                request.getAttribute("not_admin_error_name"));
        assertEquals("forward url should be the same.",
                "validation_error_forward_url", response.getForwardedUrl());
    }

    /**
     * <p>
     * Accuracy test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * All right
     * </p>
     * <p>
     * forward to error URL.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostAccuracy3() throws Exception {
        MemberPhotoListServlet bean = TestHelper.genListServletBean();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("member_id", "717");
        request.setParameter("page_number_parameter_name", "0");
        request.setParameter("page_size_parameter_name", "50");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("is_admin_session_key", new Boolean(true));
        request.setSession(session);

        bean.doPost(request, response);
        assertEquals("forward url should be the same.",
                "success_result_forward_url", response.getForwardedUrl());
    }

    /**
     * <p>
     * Failure test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * 'request' is null, IAE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure1() throws Exception {
        MemberPhotoListServlet bean = TestHelper.genListServletBean();
        MockHttpServletResponse response = new MockHttpServletResponse();

        try {
            bean.doPost(null, response);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * 'response' is null, IAE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure2() throws Exception {
        MemberPhotoListServlet bean = TestHelper.genListServletBean();
        MockHttpServletRequest request = new MockHttpServletRequest();

        try {
            bean.doPost(request, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * 'memberPhotoManager' does not exist, ISE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure3() throws Exception {
        MemberPhotoListServlet bean =
                TestHelper.genListServletBean("list_invalid_1");
        MockHttpServletRequest request = new MockHttpServletRequest();

        MockHttpServletResponse response = new MockHttpServletResponse();

        try {
            bean.doPost(request, response);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // success
        }
    }
}
