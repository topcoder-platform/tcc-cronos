/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.failuretests;

import com.topcoder.reg.profilecompleteness.filter.CheckProfileCompletenessConfigurationException;
import com.topcoder.reg.profilecompleteness.filter.impl.retrievers.BaseUserIdRetriever;
import com.topcoder.util.log.log4j.Log4jLogFactory;
import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * <p>Failure tests for {@link BaseUserIdRetriever} class.</p>
 *
 * @author jmn
 * @version 1.0
 */
public class BaseUserIdRetrieverFailureTests {

    /**
     * <p>Represents the user session key.</p>
     */
    private static final String USER_SESSION = "userSession";

    /**
     * <p>Represents the empty string used for tests.</p>
     */
    private static final String EMPTY_STRING = " ";

    /**
     * <p>Represents the instance of the tested class.</p>
     */
    private MockBaseUserIdRetriever instance;

    /**
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BaseUserIdRetrieverFailureTests.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        instance = new MockBaseUserIdRetriever();
    }

    /**
     * <p>Tests <code>{@link BaseUserIdRetriever#BaseUserIdRetriever()}</code> constructor.</p>
     */
    @Test
    public void testCtor() {

        instance = new MockBaseUserIdRetriever();
        assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests {@link BaseUserIdRetriever#checkInitialization()} method.</p>
     *
     * <p>No exception is expected.</p>
     *
     * @throws Exception if any error occurs during test
     */
    @Test
    public void testCheckInitializationFailure1() throws Exception {

        instance.setUserSessionKey(USER_SESSION);

        instance.checkInitialization();
    }

    /**
     * <p>Tests {@link BaseUserIdRetriever#checkInitialization()} method.</p>
     *
     * <p>{@link CheckProfileCompletenessConfigurationException} is expected.</p>
     *
     * @throws Exception if any error occurs during test
     */
    @Test(expected = CheckProfileCompletenessConfigurationException.class)
    public void testCheckInitializationFailure2() throws Exception {

        instance.setLog(new Log4jLogFactory().createLog("test"));
        instance.setUserSessionKey(null);

        instance.checkInitialization();
    }

    /**
     * <p>Tests {@link BaseUserIdRetriever#checkInitialization()} method.</p>
     *
     * <p>{@link CheckProfileCompletenessConfigurationException} is expected.</p>
     *
     * @throws Exception if any error occurs during test
     */
    @Test(expected = CheckProfileCompletenessConfigurationException.class)
    public void testCheckInitializationFailure3() throws Exception {

        instance.setLog(new Log4jLogFactory().createLog("test"));
        instance.setUserSessionKey(EMPTY_STRING);

        instance.checkInitialization();
    }
}
