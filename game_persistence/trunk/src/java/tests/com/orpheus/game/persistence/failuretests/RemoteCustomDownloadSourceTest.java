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
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.GameDataLocal;
import com.orpheus.game.persistence.GameDataLocalHome;
import com.orpheus.game.persistence.InstantiationException;
import com.orpheus.game.persistence.RemoteCustomDownloadSource;
import com.orpheus.game.persistence.ejb.GameDataBean;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.web.frontcontroller.results.DownloadDataRetrievalException;

/**
 * Test case for <code>RemoteCustomDownloadSourceTest</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class RemoteCustomDownloadSourceTest extends TestCase {

    /**
     * Represents the source to test.
     */
    private RemoteCustomDownloadSource source;

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
                GameDataHome.class, GameData.class, new GameDataBean());
        mockContainer.deploy(sampleServiceDescriptor);

        MockUserTransaction mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);

        source = new RemoteCustomDownloadSource("com.orpheus.game.persistence.CustomDownloadSource.remote");
    }

    /**
     * Clean up the environment.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        FailureTestHelper.unloadConfig();
    }

    /**
     * Test method for RemoteCustomDownloadSource(java.lang.String).
     * In this case, the namespace is null.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testRemoteCustomDownloadSource_NullNS() throws Exception {
        try {
            new RemoteCustomDownloadSource(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for RemoteCustomDownloadSource(java.lang.String).
     * In this case, the namespace is empty.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testRemoteCustomDownloadSource_EmptyNS() throws Exception {
        try {
            new RemoteCustomDownloadSource(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for RemoteCustomDownloadSource(java.lang.String).
     * In this case, the namespace doesn't exist.
     * Expected : {@link InstantiationException}.
     * @throws Exception to JUnit
     */
    public void testRemoteCustomDownloadSource_NonExistNS() throws Exception {
        try {
            new RemoteCustomDownloadSource("not.exist.namespace");
            fail("InstantiationException expected.");
        } catch (InstantiationException e) {
            // should land here
        }
    }

    /**
     * Test method for RemoteCustomDownloadSource(java.lang.String).
     * In this case, the ejb reference doesn't exist.
     * Expected : {@link InstantiationException}.
     * @throws Exception to JUnit
     */
    public void testRemoteCustomDownloadSource_NoEJBReference() throws Exception {
        try {
            new RemoteCustomDownloadSource("com.orpheus.game.persistence.CustomDownloadSource.remote.empty");
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
