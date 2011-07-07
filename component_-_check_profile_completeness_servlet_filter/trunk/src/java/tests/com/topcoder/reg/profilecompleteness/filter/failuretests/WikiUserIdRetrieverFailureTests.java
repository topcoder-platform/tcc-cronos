/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.failuretests;

import com.topcoder.reg.profilecompleteness.filter.impl.retrievers.DirectUserIdRetriever;
import com.topcoder.util.log.log4j.Log4jLogFactory;
import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.Assert.assertNotNull;

/**
 * <p>Failure tests for {@link DirectUserIdRetriever} class.</p>
 *
 * @author jmn
 * @version 1.0
 */
public class WikiUserIdRetrieverFailureTests {

    /**
     * <p>Represents the user session key.</p>
     */
    private static final String USER_SESSION = "userSession";

    /**
     * <p>Represents the instance of the tested class.</p>
     */
    private DirectUserIdRetriever instance;

    /**
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DirectUserIdRetrieverFailureTests.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        instance = new DirectUserIdRetriever();
        instance.setLog(new Log4jLogFactory().createLog("test"));
        instance.setUserSessionKey(USER_SESSION);
    }

    /**
     * <p>Tests <code>{@link DirectUserIdRetriever#DirectUserIdRetriever()}</code> constructor.</p>
     */
    @Test
    public void testCtor() {

        instance = new DirectUserIdRetriever();
        assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests {@link DirectUserIdRetriever#getUserId(javax.servlet.http.HttpServletRequest)} method when request is
     * null.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws Exception if any error occurs during test
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetUserIdNull() throws Exception {

        instance.getUserId(null);
    }

    /**
     * <p>Tests {@link DirectUserIdRetriever#getUserId(javax.servlet.http.HttpServletRequest)} method.</p>
     *
     * <p>No exception is expected.</p>
     *
     * @throws Exception if any error occurs during test
     */
    @Test
    public void testGetUserIdFailure() throws Exception {

        instance.getUserId(new MockHttpServletRequest());
    }
}
