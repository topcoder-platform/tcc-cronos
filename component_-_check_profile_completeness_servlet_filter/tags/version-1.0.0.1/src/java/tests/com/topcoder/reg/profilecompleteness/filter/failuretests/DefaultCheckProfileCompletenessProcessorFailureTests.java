/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.failuretests;

import com.topcoder.reg.profilecompleteness.filter.CheckProfileCompletenessConfigurationException;
import com.topcoder.reg.profilecompleteness.filter.impl.DefaultCheckProfileCompletenessProcessor;
import com.topcoder.reg.profilecompleteness.filter.impl.ProfileCompletenessChecker;
import com.topcoder.util.log.log4j.Log4jLogFactory;
import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Arrays;

import static org.junit.Assert.assertNotNull;

/**
 * <p>Failure tests for {@link DefaultCheckProfileCompletenessProcessor} class.</p>
 *
 * @author jmn
 * @version 1.0
 */
public class DefaultCheckProfileCompletenessProcessorFailureTests {

    /**
     * <p>Represents the instance of the tested class.</p>
     */
    private PublicDefaultCheckProfileCompletenessProcessor instance;

    /**
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DefaultCheckProfileCompletenessProcessorFailureTests.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        instance = new PublicDefaultCheckProfileCompletenessProcessor();
    }

    /**
     * <p>Tests {@link DefaultCheckProfileCompletenessProcessor#DefaultCheckProfileCompletenessProcessor()}
     * constructor.</p>
     */
    @Test
    public void testCtor() {

        instance = new PublicDefaultCheckProfileCompletenessProcessor();
        assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests {@link DefaultCheckProfileCompletenessProcessor#checkInitialization()} method.</p>
     *
     * <p>No exception is expected.</p>
     *
     * @throws Exception if any error occurs during test
     */
    @Test
    public void testCheckInitializationFailure1() throws Exception {

        configureProperties();

        instance.setLog(null);

        invokeCheckInitialization();
    }

    /**
     * <p>Tests {@link DefaultCheckProfileCompletenessProcessor#checkInitialization()} method.</p>
     *
     * <p>{@link CheckProfileCompletenessConfigurationException} is expected.</p>
     *
     * @throws Exception if any error occurs during test
     */
    @Test(expected = CheckProfileCompletenessConfigurationException.class)
    public void testCheckInitializationFailure2() throws Exception {

        configureProperties();

        instance.setRequestURISessionKey(null);

        invokeCheckInitialization();
    }

    /**
     * <p>Tests {@link DefaultCheckProfileCompletenessProcessor#checkInitialization()} method.</p>
     *
     * <p>{@link CheckProfileCompletenessConfigurationException} is expected.</p>
     *
     * @throws Exception if any error occurs during test
     */
    @Test(expected = CheckProfileCompletenessConfigurationException.class)
    public void testCheckInitializationFailure3() throws Exception {

        configureProperties();

        instance.setRequestURISessionKey(" ");

        invokeCheckInitialization();
    }

    /**
     * <p>Tests {@link DefaultCheckProfileCompletenessProcessor#checkInitialization()} method.</p>
     *
     * <p>{@link CheckProfileCompletenessConfigurationException} is expected.</p>
     *
     * @throws Exception if any error occurs during test
     */
    @Test(expected = CheckProfileCompletenessConfigurationException.class)
    public void testCheckInitializationFailure4() throws Exception {

        configureProperties();

        instance.setMatchUriPatterns(null);

        invokeCheckInitialization();
    }

    /**
     * <p>Tests {@link DefaultCheckProfileCompletenessProcessor#checkInitialization()} method.</p>
     *
     * <p>{@link CheckProfileCompletenessConfigurationException} is expected.</p>
     *
     * @throws Exception if any error occurs during test
     */
    @Test(expected = CheckProfileCompletenessConfigurationException.class)
    public void testCheckInitializationFailure5() throws Exception {

        configureProperties();

        instance.setMatchUriPatterns(Arrays.asList("matchUrl1", "marchUrl2"));

        invokeCheckInitialization();
    }

    /**
     * <p>Tests {@link DefaultCheckProfileCompletenessProcessor#checkInitialization()} method.</p>
     *
     * <p>{@link CheckProfileCompletenessConfigurationException} is expected.</p>
     *
     * @throws Exception if any error occurs during test
     */
    @Test(expected = CheckProfileCompletenessConfigurationException.class)
    public void testCheckInitializationFailure6() throws Exception {

        configureProperties();

        instance.setCompleteDataURIs(null);

        invokeCheckInitialization();
    }

    /**
     * <p>Tests {@link DefaultCheckProfileCompletenessProcessor#checkInitialization()} method.</p>
     *
     * <p>{@link CheckProfileCompletenessConfigurationException} is expected.</p>
     *
     * @throws Exception if any error occurs during test
     */
    @Test(expected = CheckProfileCompletenessConfigurationException.class)
    public void testCheckInitializationFailure7() throws Exception {

        configureProperties();

        instance.setCompleteDataURIs(Arrays.asList("dataUrl1", "dataUrl2"));

        invokeCheckInitialization();
    }

    /**
     * <p>Tests {@link DefaultCheckProfileCompletenessProcessor#checkInitialization()} method.</p>
     *
     * <p>{@link CheckProfileCompletenessConfigurationException} is expected.</p>
     *
     * @throws Exception if any error occurs during test
     */
    @Test(expected = CheckProfileCompletenessConfigurationException.class)
    public void testCheckInitializationFailure8() throws Exception {

        configureProperties();

        instance.setProfileCompletenessCheckers(null);

        invokeCheckInitialization();
    }

    /**
     * <p>Tests {@link DefaultCheckProfileCompletenessProcessor#checkInitialization()} method.</p>
     *
     * <p>{@link CheckProfileCompletenessConfigurationException} is expected.</p>
     *
     * @throws Exception if any error occurs during test
     */
    @Test(expected = CheckProfileCompletenessConfigurationException.class)
    public void testCheckInitializationFailure9() throws Exception {

        configureProperties();

        instance.setUserIdRetriever(null);

        invokeCheckInitialization();
    }

    /**
     * <p>Tests {@link DefaultCheckProfileCompletenessProcessor#checkInitialization()} method.</p>
     *
     * <p>{@link CheckProfileCompletenessConfigurationException} is expected.</p>
     *
     * @throws Exception if any error occurs during test
     */
    @Test(expected = CheckProfileCompletenessConfigurationException.class)
    public void testCheckInitializationFailure10() throws Exception {

        configureProperties();

        instance.setUserDAO(null);

        invokeCheckInitialization();
    }

    /**
     * <p>Tests {@link DefaultCheckProfileCompletenessProcessor# process(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)} method.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws Exception if any error occurs during test
     */
    @Test(expected = IllegalArgumentException.class)
    public void testProcessNull1() throws Exception {

        configureProperties();

        instance.process(null, new MockHttpServletResponse(), new MockFilterChain());
    }

    /**
     * <p>Tests {@link DefaultCheckProfileCompletenessProcessor# process(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)} method.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws Exception if any error occurs during test
     */
    @Test(expected = IllegalArgumentException.class)
    public void testProcessNull2() throws Exception {

        configureProperties();

        instance.process(new MockHttpServletRequest(), null, new MockFilterChain());
    }

    /**
     * <p>Tests {@link DefaultCheckProfileCompletenessProcessor# process(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)} method.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws Exception if any error occurs during test
     */
    @Test(expected = IllegalArgumentException.class)
    public void testProcessNull3() throws Exception {

        configureProperties();

        instance.process(new MockHttpServletRequest(), new MockHttpServletResponse(), null);
    }

    /**
     * <p>Sets the properties on the tested object instance.</p>
     */
    private void configureProperties() {

        instance.setLog(new Log4jLogFactory().createLog("test"));
        instance.setRequestURISessionKey("sessionKey");
        instance.setMatchUriPatterns(Arrays.asList("matchUrl"));
        instance.setCompleteDataURIs(Arrays.asList("dataUrl"));
        instance.setProfileCompletenessCheckers(
                Arrays.<ProfileCompletenessChecker>asList(new MockBaseProfileCompletenessChecker()));
        instance.setUserIdRetriever(new MockBaseUserIdRetriever());
        instance.setUserDAO(new MockUserDAO());
    }

    /**
     * <p>Invokes protected method {@link DefaultCheckProfileCompletenessProcessor#checkInitialization()} on tested
     * object instance.</p>
     *
     * @throws Exception if any error occurs
     */
    private void invokeCheckInitialization() throws Exception {

        instance.checkInitialization();
    }

    /**
     * <p>This class extends {@link DefaultCheckProfileCompletenessProcessor} in other to change the visibility of the
     * checkInitialization method.</p>
     *
     * @author jmn
     * @version 1.0
     */
    private static class PublicDefaultCheckProfileCompletenessProcessor extends DefaultCheckProfileCompletenessProcessor {

        /**
         * {@inheritDoc}
         */
        @Override
        public void checkInitialization() {
            super.checkInitialization();
        }
    }
}
