/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.failuretests;

import java.io.File;

import com.orpheus.auction.persistence.AuctionDAOFactory;
import com.orpheus.auction.persistence.ObjectInstantiationException;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test for <code>{@link com.orpheus.auction.persistence.AuctionDAOFactory}</code> class.
 * </p>
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class AuctionDAOFactoryFailureTest extends TestCase {

    /**
     * <p>
     * Represents the directory where testing config files put.
     * </p>
     */
    private static final String DIR = "failuretests" + File.separator + "AuctionDAOFactory" + File.separator;

    /**
     * <p>
     * Clean the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        FailureTestHelper.releaseNamespace();
    }

    /**
     * <p>
     * Failure test for <code>{@link com.orpheus.auction.persistence.AuctionDAOFactory#getAuctionDAO()}</code> method.
     * </p>
     */
    public void testGetAuctionDAOFailure1() {
        try {
            AuctionDAOFactory.getAuctionDAO();
            fail("expected ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link com.orpheus.auction.persistence.AuctionDAOFactory#getAuctionDAO()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAuctionDAOFailure2() throws Exception {
        FailureTestHelper.loadNamespaceFromFile(DIR + "2.xml");

        try {
            AuctionDAOFactory.getAuctionDAO();
            fail("expected ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link com.orpheus.auction.persistence.AuctionDAOFactory#getAuctionDAO()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAuctionDAOFailure3() throws Exception {
        FailureTestHelper.loadNamespaceFromFile(DIR + "3.xml");

        try {
            AuctionDAOFactory.getAuctionDAO();
            fail("expected ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link com.orpheus.auction.persistence.AuctionDAOFactory#getAuctionDAO()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAuctionDAOFailure4() throws Exception {
        FailureTestHelper.loadNamespaceFromFile(DIR + "4.xml");

        try {
            AuctionDAOFactory.getAuctionDAO();
            fail("expected ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link com.orpheus.auction.persistence.AuctionDAOFactory#getAuctionDAO()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAuctionDAOFailure5() throws Exception {
        FailureTestHelper.loadNamespaceFromFile(DIR + "5.xml");

        try {
            AuctionDAOFactory.getAuctionDAO();
            fail("expected ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link com.orpheus.auction.persistence.AuctionDAOFactory#getAuctionDAO()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAuctionDAOFailure6() throws Exception {
        FailureTestHelper.loadNamespaceFromFile(DIR + "6.xml");

        try {
            AuctionDAOFactory.getAuctionDAO();
            fail("expected ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link com.orpheus.auction.persistence.AuctionDAOFactory#getAuctionDAO()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAuctionDAOFailure7() throws Exception {
        FailureTestHelper.loadNamespaceFromFile(DIR + "7.xml");

        try {
            AuctionDAOFactory.getAuctionDAO();
            fail("expected ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link com.orpheus.auction.persistence.AuctionDAOFactory#getAuctionDAO()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAuctionDAOFailure8() throws Exception {
        FailureTestHelper.loadNamespaceFromFile(DIR + "8.xml");

        try {
            AuctionDAOFactory.getAuctionDAO();
            fail("expected ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link com.orpheus.auction.persistence.AuctionDAOFactory#getAuctionDAO()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAuctionDAOFailure9() throws Exception {
        FailureTestHelper.loadNamespaceFromFile(DIR + "9.xml");

        try {
            AuctionDAOFactory.getAuctionDAO();
            fail("expected ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link com.orpheus.auction.persistence.AuctionDAOFactory#getAuctionDAO()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAuctionDAOFailure10() throws Exception {
        FailureTestHelper.loadNamespaceFromFile(DIR + "10.xml");

        try {
            AuctionDAOFactory.getAuctionDAO();
            fail("expected ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link com.orpheus.auction.persistence.AuctionDAOFactory#getAuctionDAO()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAuctionDAOFailure11() throws Exception {
        FailureTestHelper.loadNamespaceFromFile(DIR + "11.xml");

        try {
            AuctionDAOFactory.getAuctionDAO();
            fail("expected ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link com.orpheus.auction.persistence.AuctionDAOFactory#getAuctionDAO()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAuctionDAOFailure12() throws Exception {
        FailureTestHelper.loadNamespaceFromFile(DIR + "12.xml");

        try {
            AuctionDAOFactory.getAuctionDAO();
            fail("expected ObjectInstantiationException.");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

}
