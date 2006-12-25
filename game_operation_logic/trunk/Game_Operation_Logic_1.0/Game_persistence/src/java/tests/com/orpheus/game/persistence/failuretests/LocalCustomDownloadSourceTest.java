/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.persistence.failuretests;

import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import junit.framework.TestCase;

import com.mockrunner.mock.ejb.MockUserTransaction;
import com.orpheus.game.persistence.GameDataLocal;
import com.orpheus.game.persistence.GameDataLocalHome;
import com.orpheus.game.persistence.InstantiationException;
import com.orpheus.game.persistence.LocalCustomDownloadSource;
import com.orpheus.game.persistence.ejb.GameDataBean;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.web.frontcontroller.results.DownloadDataRetrievalException;

/**
 * Test case for <code>LocalCustomDownloadSourceTest</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class LocalCustomDownloadSourceTest extends TestCase {

    /**
     * Represents the source to test.
     */
    private LocalCustomDownloadSource source;

    /**
     * Set up the environment.
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.loadConfig();

        MockContextFactory.setAsInitial();
        InitialContext context = new InitialContext();
        MockContainer mockContainer = new MockContainer(context);
        SessionBeanDescriptor sampleServiceDescriptor = new SessionBeanDescriptor("game/GameDataBean",
                GameDataLocalHome.class, GameDataLocal.class, new GameDataBean());
        mockContainer.deploy(sampleServiceDescriptor);

        MockUserTransaction mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);

        source = new LocalCustomDownloadSource("com.orpheus.game.persistence.CustomDownloadSource.local");
    }

    /**
     * Clean up the environment.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        FailureTestHelper.unloadConfig();
    }

    /**
     * Test method for LocalCustomDownloadSource(java.lang.String).
     * In this case, the namespace is null.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testLocalCustomDownloadSource_NullNS() throws Exception {
        try {
            new LocalCustomDownloadSource(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LocalCustomDownloadSource(java.lang.String).
     * In this case, the namespace is empty.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testLocalCustomDownloadSource_EmptyNS() throws Exception {
        try {
            new LocalCustomDownloadSource(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LocalCustomDownloadSource(java.lang.String).
     * In this case, the namespace doesn't exist.
     * Expected : {@link InstantiationException}.
     * @throws Exception to JUnit
     */
    public void testLocalCustomDownloadSource_NonExistNS() throws Exception {
        try {
            new LocalCustomDownloadSource("not.exist.namespace");
            fail("InstantiationException expected.");
        } catch (InstantiationException e) {
            // should land here
        }
    }

    /**
     * Test method for LocalCustomDownloadSource(java.lang.String).
     * In this case, the ejb reference doesn't exist.
     * Expected : {@link InstantiationException}.
     * @throws Exception to JUnit
     */
    public void testLocalCustomDownloadSource_NoEJBReference() throws Exception {
        try {
            new LocalCustomDownloadSource("com.orpheus.game.persistence.CustomDownloadSource.local.empty");
            fail("InstantiationException expected.");
        } catch (InstantiationException e) {
            // should land here
        }
    }

    /**
     * Test method for getDownloadData(java.lang.String).
     * In this case, the id is not a valid long value.
     * Expected : DownloadDataRetrievalException.
     * @throws Exception to JUnit
     */
    public void testGetDownloadData_InvalidLong() throws Exception {
        try {
            source.getDownloadData("not.a.long");
            fail("DownloadDataRetrievalException expected.");
        } catch (DownloadDataRetrievalException e) {
            // should land here
            assertTrue("The cause should be NumberFormatException.", e.getCause() instanceof NumberFormatException);
        }
    }

}
