/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.accuracytests.retrievers;

import java.security.MessageDigest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.mock.web.MockHttpServletRequest;

import com.topcoder.reg.profilecompleteness.filter.impl.retrievers.DirectUserIdRetriever;
import com.topcoder.security.UserPrincipal;
import com.topcoder.security.admin.PrincipalMgrLocal;
import com.topcoder.security.admin.PrincipalMgrLocalHome;
import com.topcoder.shared.dataAccess.DataAccessConstants;
import com.topcoder.shared.util.ApplicationServer;
import com.topcoder.util.log.basic.BasicLogFactory;


/**
 * <p>
 * Accuracy test for {@link DirectUserIdRetriever}.
 * </p>
 *
 * @author gets0ul
 * @version 1.0
 */
public class DirectUserIdRetrieverAccuracyTest {
    /**
     * The DirectUserIdRetriever instance to test against.
     */
    private DirectUserIdRetriever retriever;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DirectUserIdRetrieverAccuracyTest.class);
    }

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Before
    public void setUp() throws Exception {
        retriever = new DirectUserIdRetriever();
        retriever.setLog(new BasicLogFactory().createLog("test"));
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @After
    public void tearDown() throws Exception {
        retriever = null;
    }

    /**
     * <p>
     * Tests DirectUserIdRetriever constructor.
     * </p>
     * <p>
     * Instance should be created. No exception is expected.
     * </p>
     */
    @Test
    public void testCtor() {
        Assert.assertNotNull("DirectUserIdRetriever is not instantiated", retriever);
    }

    /**
     * <p>
     * Tests {@link DirectUserIdRetriever#getUserId(javax.servlet.http.HttpServletRequest)} method when user is
     * logged.
     * </p>
     * <p>
     * Expects the correct user id is returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test
    public void testGetUserId1() throws Exception {
        long uid = 2;
        String password = "password";
        String status = "status";
        HttpServletRequest request = new MockHttpServletRequest();

        // create the hash
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] plain = ("GKDKJF80dbdc541fe829898aa01d9e30118bab5d6b9fe94fd052a40069385f5628" + uid + password + status)
            .getBytes();
        byte[] raw = md.digest(plain);
        StringBuffer hex = new StringBuffer();
        for (byte aRaw : raw) {
            hex.append(Integer.toHexString(aRaw & 0xff));
        }
        String userHash = hex.toString();

        // set up the cookie so the user with uid 2 is deemed as logged
        Cookie cookie = new Cookie("direct_user_id_" + ApplicationServer.ENVIRONMENT, uid + "|" + userHash);
        ((MockHttpServletRequest) request).setCookies(new Cookie[] {cookie});

        // bind a mock PrincipalManager EJB to mock JNDI context
        SimpleNamingContextBuilder contextBuilder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
        PrincipalMgrLocalHome mockEJB = Mockito.mock(PrincipalMgrLocalHome.class);
        PrincipalMgrLocal mockMgr = Mockito.mock(PrincipalMgrLocal.class);
        Mockito.when(mockEJB.create()).thenReturn(mockMgr);

        // return a mock Principal
        Mockito.when(mockMgr.getUser(uid)).thenReturn(new UserPrincipal("user", uid));
        contextBuilder.bind("java:/" + PrincipalMgrLocal.class.getName() + "Home", mockEJB);

        DataAccessConstants.COMMAND = "command";

        // get the user id
        Long userId = retriever.getUserId(request);

        // verify it
        Assert.assertNotNull("The user id should exist for logged user", userId);
        Assert.assertEquals("Incorrect user id", uid, userId.longValue());

        contextBuilder.clear();
    }

    /**
     * <p>
     * Tests {@link DirectUserIdRetriever#getUserId(javax.servlet.http.HttpServletRequest)} method when user is not
     * logged in.
     * </p>
     * <p>
     * Expects null is returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test
    public void testGetUserId2() throws Exception {
        HttpServletRequest request = new MockHttpServletRequest();

        // get the user id
        Long userId = retriever.getUserId(request);

        // verify it
        Assert.assertNull("The user id should not exist for anonymous user", userId);
    }
}
