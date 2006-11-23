/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.failuretests;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Date;

import com.orpheus.auction.persistence.CustomAuctionPersistence;
import com.orpheus.auction.persistence.DuplicateEntryException;
import com.orpheus.auction.persistence.InvalidEntryException;
import com.orpheus.auction.persistence.ObjectInstantiationException;
import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.Bid;
import com.topcoder.util.cache.Cache;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test for <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class CustomAuctionPersistenceFailureTest extends TestCase {
    /**
     * <p>
     * Represents the <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence}</code> instance used in
     * tests.
     * </p>
     */
    private CustomAuctionPersistence customAuctionPersistence;

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.loadNamespaceFromFile("failuretests" + File.separator + "CustomAuctionPersistence.xml");

        customAuctionPersistence = new DummyCustomAuctionPersistence(
            "com.orpheus.auction.persistence.CustomAuctionPersistence");
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
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#CustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCustomAuctionPersistence_NullParam() throws Exception {
        try {
            new DummyCustomAuctionPersistence(null);
            fail("expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#CustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCustomAuctionPersistence_TrimmedEmptyParam() throws Exception {
        try {
            new DummyCustomAuctionPersistence("  ");
            fail("expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#CustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCustomAuctionPersistence_EmptyParam() throws Exception {
        try {
            new DummyCustomAuctionPersistence("");
            fail("expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#CustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCustomAuctionPersistence1() {
        try {
            new DummyCustomAuctionPersistence("com.orpheus.auction.persistence.CustomAuctionPersistence1");
            fail("expect ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#CustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCustomAuctionPersistence2() {
        try {
            new DummyCustomAuctionPersistence("com.orpheus.auction.persistence.CustomAuctionPersistence2");
            fail("expect ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#CustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCustomAuctionPersistence3() {
        try {
            new DummyCustomAuctionPersistence("com.orpheus.auction.persistence.CustomAuctionPersistence3");
            fail("expect ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#CustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCustomAuctionPersistence4() {
        try {
            new DummyCustomAuctionPersistence("com.orpheus.auction.persistence.CustomAuctionPersistence4");
            fail("expect ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#CustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCustomAuctionPersistence5() {
        try {
            new DummyCustomAuctionPersistence("com.orpheus.auction.persistence.CustomAuctionPersistence5");
            fail("expect ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#CustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCustomAuctionPersistence6() {
        try {
            new DummyCustomAuctionPersistence("com.orpheus.auction.persistence.CustomAuctionPersistence6");
            fail("expect ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#CustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCustomAuctionPersistence7() {
        try {
            new DummyCustomAuctionPersistence("com.orpheus.auction.persistence.CustomAuctionPersistence7");
            fail("expect ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#CustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCustomAuctionPersistence8() {
        try {
            new DummyCustomAuctionPersistence("com.orpheus.auction.persistence.CustomAuctionPersistence8");
            fail("expect ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#CustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCustomAuctionPersistence9() {
        try {
            new DummyCustomAuctionPersistence("com.orpheus.auction.persistence.CustomAuctionPersistence9");
            fail("expect ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#CustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCustomAuctionPersistence10() {
        try {
            new DummyCustomAuctionPersistence("com.orpheus.auction.persistence.CustomAuctionPersistence10");
            fail("expect ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#CustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCustomAuctionPersistence11() {
        try {
            new DummyCustomAuctionPersistence("com.orpheus.auction.persistence.CustomAuctionPersistence11");
            fail("expect ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#CustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCustomAuctionPersistence12() {
        try {
            new DummyCustomAuctionPersistence("com.orpheus.auction.persistence.CustomAuctionPersistence12");
            fail("expect ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#CustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCustomAuctionPersistence13() {
        try {
            new DummyCustomAuctionPersistence("com.orpheus.auction.persistence.CustomAuctionPersistence13");
            fail("expect ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#CustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCustomAuctionPersistence14() {
        try {
            new DummyCustomAuctionPersistence("com.orpheus.auction.persistence.CustomAuctionPersistence14");
            fail("expect ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#CustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCustomAuctionPersistence15() {
        try {
            new DummyCustomAuctionPersistence("com.orpheus.auction.persistence.CustomAuctionPersistence15");
            fail("expect ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#CustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCustomAuctionPersistence16() {
        try {
            new DummyCustomAuctionPersistence("com.orpheus.auction.persistence.CustomAuctionPersistence16");
            fail("expect ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#CustomAuctionPersistence(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCustomAuctionPersistence17() {
        try {
            new DummyCustomAuctionPersistence("com.orpheus.auction.persistence.CustomAuctionPersistence17");
            fail("expect ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#createAuction(Auction)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateAution_NullParam() throws Exception {
        try {
            customAuctionPersistence.createAuction(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#createAuction(Auction)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateAution_InvalidEntry() throws Exception {
        Auction auction = new MockAuction(null, "", "", 1, 1, null, null, null);
        try {
            customAuctionPersistence.createAuction(auction);
            fail("expect throw InvalidEntryException.");
        } catch (InvalidEntryException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#createAuction(Auction)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateAution_DuplicatedEntry() throws Exception {
        Auction auction = new MockAuction(new Long(1), "", "", 1, 1, null, null, null);

        Field field = customAuctionPersistence.getClass().getSuperclass().getDeclaredField("cache");
        field.setAccessible(true);
        Cache cache = (Cache) field.get(customAuctionPersistence);

        cache.put(auction.getId(), auction);
        try {
            customAuctionPersistence.createAuction(auction);
            fail("expect throw DuplicateEntryException.");
        } catch (DuplicateEntryException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#updateAuction(Auction)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateAuction_NullParam() throws Exception {
        try {
            customAuctionPersistence.updateAuction(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#updateAuction(Auction)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateAuction_InvalidEntry() throws Exception {
        Auction auction = new MockAuction(null, "", "", 1, 1, null, null, null);
        try {
            customAuctionPersistence.updateAuction(auction);
            fail("expect throw InvalidEntryException.");
        } catch (InvalidEntryException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#updateBids(long, Bid[])}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateBids_NullParam() throws Exception {
        try {
            customAuctionPersistence.updateBids(1, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#updateBids(long, Bid[])}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateBids_ContainsNull() throws Exception {
        try {
            customAuctionPersistence.updateBids(1, new Bid[] {null});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.CustomAuctionPersistence#findAuctionsByDate(Date, Date)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testFindAuctionsByDate_IAE() throws Exception {
        Date end = new Date();
        Thread.sleep(1000);
        Date start  = new Date();
        try {
            customAuctionPersistence.findAuctionsByDate(start, end);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
