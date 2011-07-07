/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.impl.retrievers;

import java.security.MessageDigest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

import org.mockito.Mockito;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.mock.web.MockHttpServletRequest;

import com.topcoder.reg.profilecompleteness.filter.MockLog;
import com.topcoder.security.UserPrincipal;
import com.topcoder.security.admin.PrincipalMgrLocal;
import com.topcoder.security.admin.PrincipalMgrLocalHome;
import com.topcoder.shared.dataAccess.DataAccessConstants;
import com.topcoder.shared.util.ApplicationServer;

/**
 * Unit tests for <code>DirectUserIdRetriever</code> class.
 *
 * @author nowind_lee
 * @version 1.0
 */
public class DirectUserIdRetrieverTest extends TestCase {

    /**
     * Represents the <code>DirectUserIdRetriever</code> instance used to test against.
     */
    private DirectUserIdRetriever t;

    /**
     * Represents the <code>Log</code> instance used to test against.
     */
    private MockLog log;

    /**
     * Represents the <code>HttpServletRequest</code> instance used to test against.
     */
    private HttpServletRequest request;

    /**
     * Sets up the fixture. This method is called before a test is executed.
     *
     * @throws Exception
     *             if any error occurs.
     */
    @Override
    protected void setUp() throws Exception {
        t = new DirectUserIdRetriever();
        log = new MockLog();
        request = new MockHttpServletRequest();
    }

    /**
     * Tears down the fixture. This method is called after a test is executed.
     *
     * @throws Exception
     *             if any error occurs.
     */
    @Override
    protected void tearDown() throws Exception {
        t = null;
        log = null;
        request = null;
    }

    /**
     * Accuracy test for constructor <code>DirectUserIdRetriever()</code>.
     *
     * Uses the constructor to create a non-null instance. No exception should be thrown.
     */
    public void testCtor_Accuracy() {
        assertNotNull("Instance should be created.", t);
    }

    /**
     * Accuracy test for <code>getUserId(HttpServletRequest request)</code>.
     *
     * UserId should be got if the underlying BasicAuthentication returns a valid User
     *
     * @throws Exception
     *             to jUnit
     */
    public void testGetUserId_Accuracy1() throws Exception {
        t.setLog(log);
        long uid = 2;
        String password = "password";
        String status = "status";

        // create the hash
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] plain = ("GKDKJF80dbdc541fe829898aa01d9e30118bab5d6b9fe94fd052a40069385f5628" + uid
            + password + status)
            .getBytes();
        byte[] raw = md.digest(plain);
        StringBuffer hex = new StringBuffer();
        for (byte aRaw : raw) {
            hex.append(Integer.toHexString(aRaw & 0xff));
        }
        String userHash = hex.toString();

        // set up the cookie so the user with uid 2 is deemed as logged
        Cookie cookie = new Cookie("direct_user_id_" + ApplicationServer.ENVIRONMENT, uid + "|"
            + userHash);
        ((MockHttpServletRequest) request).setCookies(new Cookie[] {cookie });

        // bind a mock PrincipalManager EJB to mock JNDI context
        SimpleNamingContextBuilder contextBuilder = SimpleNamingContextBuilder
            .emptyActivatedContextBuilder();
        PrincipalMgrLocalHome mockEJB = Mockito.mock(PrincipalMgrLocalHome.class);
        PrincipalMgrLocal mockMgr = Mockito.mock(PrincipalMgrLocal.class);
        Mockito.when(mockEJB.create()).thenReturn(mockMgr);

        // return a mock Principal
        Mockito.when(mockMgr.getUser(uid)).thenReturn(new UserPrincipal("user", uid));
        contextBuilder.bind("java:/" + PrincipalMgrLocal.class.getName() + "Home", mockEJB);

        DataAccessConstants.COMMAND = "command";

        Long userId = t.getUserId(request);
        assertNotNull(userId);

        // check the log
        String logMessage = log.getLogMessages();
        assertTrue(logMessage
            .contains("[Entering method {DirectUserIdRetriever.getUserId}]"));
        assertTrue(logMessage
            .contains("[Exiting method {DirectUserIdRetriever.getUserId}]"));
        assertTrue(logMessage.contains("[Output parameter {2}]"));
    }

    /**
     * Accuracy test for <code>getUserId(HttpServletRequest request)</code>.
     *
     * Null should be got if the underlying BasicAuthentication doesn't return a User
     */
    public void testGetUserId_Accuracy2() {
        // tell the mock BasicAuthentication return a valid User
        request.setAttribute("action", "noUser");
        t.setLog(log);
        Long userId = t.getUserId(request);
        assertEquals(null, userId);
    }

    /**
     * Accuracy test for <code>getUserId(HttpServletRequest request)</code>.
     *
     * Null should be got if the underlying BasicAuthentication throws exception
     */
    public void testGetUserId_Accuracy3() {
        // tell the mock BasicAuthentication return a valid User
        request.setAttribute("action", "exception");
        t.setLog(log);
        Long userId = t.getUserId(request);
        assertNull(userId);
    }

    /**
     * Failure test for method <code>getUserId(HttpServletRequest request)</code>.
     *
     * If request is null, <code>IllegalArgumentException</code> will be thrown
     */
    public void testGetUserId_Failure_IllegalArgumentException() {
        t.setLog(log);
        try {
            t.getUserId(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
            System.out.println(e.toString());
        }
    }

}
