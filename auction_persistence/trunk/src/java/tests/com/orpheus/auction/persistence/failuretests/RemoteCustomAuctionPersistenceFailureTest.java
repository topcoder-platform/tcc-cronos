/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.failuretests;

import java.io.File;

import com.orpheus.auction.persistence.RemoteCustomAuctionPersistence;
import com.orpheus.auction.persistence.ObjectInstantiationException;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link com.orpheus.auction.persistence.RemoteCustomAuctionPersistence}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class RemoteCustomAuctionPersistenceFailureTest extends TestCase {

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.loadNamespaceFromFile("failuretests" + File.separator + "RemoteCustomAuctionPersistence.xml");

    }

    /**
     * <p>
     * tear down the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        FailureTestHelper.releaseNamespace();
    }

    /**
     * <p>
     * Unit test for <code>{@link RemoteCustomAuctionPersistence#RemoteCustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     */
    public void testRemoteCustomAuctionPersistence1() {
        try {
            new RemoteCustomAuctionPersistence("com.orpheus.auction.persistence.RemoteCustomAuctionPersistence1");
            fail("expect throw ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link RemoteCustomAuctionPersistence#RemoteCustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     */
    public void testRemoteCustomAuctionPersistence2() {
        try {
            new RemoteCustomAuctionPersistence("com.orpheus.auction.persistence.RemoteCustomAuctionPersistence2");
            fail("expect throw ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link RemoteCustomAuctionPersistence#RemoteCustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     */
    public void testRemoteCustomAuctionPersistence3() {
        try {
            new RemoteCustomAuctionPersistence("com.orpheus.auction.persistence.RemoteCustomAuctionPersistence3");
            fail("expect throw ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link RemoteCustomAuctionPersistence#RemoteCustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     */
    public void testRemoteCustomAuctionPersistence4() {
        try {
            new RemoteCustomAuctionPersistence("com.orpheus.auction.persistence.RemoteCustomAuctionPersistence4");
            fail("expect throw ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link RemoteCustomAuctionPersistence#RemoteCustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     */
    public void testRemoteCustomAuctionPersistence5() {
        try {
            new RemoteCustomAuctionPersistence("com.orpheus.auction.persistence.RemoteCustomAuctionPersistence5");
            fail("expect throw ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }
}
