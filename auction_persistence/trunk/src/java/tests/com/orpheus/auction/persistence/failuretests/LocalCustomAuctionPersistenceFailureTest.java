/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.failuretests;

import java.io.File;

import com.orpheus.auction.persistence.LocalCustomAuctionPersistence;
import com.orpheus.auction.persistence.ObjectInstantiationException;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link com.orpheus.auction.persistence.LocalCustomAuctionPersistence}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class LocalCustomAuctionPersistenceFailureTest extends TestCase {

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.loadNamespaceFromFile("failuretests" + File.separator + "LocalCustomAuctionPersistence.xml");

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
     * Unit test for <code>{@link LocalCustomAuctionPersistence#LocalCustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     */
    public void testLocalCustomAuctionPersistence1() {
        try {
            new LocalCustomAuctionPersistence("com.orpheus.auction.persistence.LocalCustomAuctionPersistence1");
            fail("expect throw ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link LocalCustomAuctionPersistence#LocalCustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     */
    public void testLocalCustomAuctionPersistence2() {
        try {
            new LocalCustomAuctionPersistence("com.orpheus.auction.persistence.LocalCustomAuctionPersistence2");
            fail("expect throw ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link LocalCustomAuctionPersistence#LocalCustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     */
    public void testLocalCustomAuctionPersistence3() {
        try {
            new LocalCustomAuctionPersistence("com.orpheus.auction.persistence.LocalCustomAuctionPersistence3");
            fail("expect throw ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link LocalCustomAuctionPersistence#LocalCustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     */
    public void testLocalCustomAuctionPersistence4() {
        try {
            new LocalCustomAuctionPersistence("com.orpheus.auction.persistence.LocalCustomAuctionPersistence4");
            fail("expect throw ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link LocalCustomAuctionPersistence#LocalCustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     */
    public void testLocalCustomAuctionPersistence5() {
        try {
            new LocalCustomAuctionPersistence("com.orpheus.auction.persistence.LocalCustomAuctionPersistence5");
            fail("expect throw ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }
}
