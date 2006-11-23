/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.failuretests;

import java.io.File;

import com.orpheus.auction.persistence.EntryNotFoundException;
import com.orpheus.auction.persistence.InvalidEntryException;
import com.orpheus.auction.persistence.ObjectInstantiationException;
import com.orpheus.auction.persistence.ejb.AuctionDTO;
import com.orpheus.auction.persistence.ejb.BidDTO;
import com.orpheus.auction.persistence.impl.SQLServerAuctionDAO;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link com.orpheus.auction.persistence.impl.SQLServerAuctionDAO}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class SQLServerAuctionDAOFailureTest extends TestCase {
    /**
     * <p>
     * Represents the <code>{@link SQLServerAuctionDAO}</code> instance used in tests.
     * </p>
     */
    private SQLServerAuctionDAO sqlServerAuctionDAO;

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.loadNamespaceFromFile("failuretests" + File.separator + "SQLServerAuctionDAO.xml");
        ConfigManager.getInstance().add("DBConnectionFactory_Config.xml");

        sqlServerAuctionDAO = new SQLServerAuctionDAO(
            "com.orpheus.auction.persistence.failuretests.SQLServerAuctionDAOFailureTest");
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
     * <code>{@link com.orpheus.auction.persistence.impl.SQLServerAuctionDAO#SQLServerAuctionDAO(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSQLServerAuctionDAO_NullParam() throws Exception {
        try {
            new SQLServerAuctionDAO(null);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.impl.SQLServerAuctionDAO#SQLServerAuctionDAO(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSQLServerAuctionDAO_EmptyParam() throws Exception {
        try {
            new SQLServerAuctionDAO("");
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.impl.SQLServerAuctionDAO#SQLServerAuctionDAO(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSQLServerAuctionDAO_TrimmedEmptyParam() throws Exception {
        try {
            new SQLServerAuctionDAO(" ");
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.impl.SQLServerAuctionDAO#SQLServerAuctionDAO(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSQLServerAuctionDAO1() throws Exception {
        try {
            new SQLServerAuctionDAO("com.orpheus.auction.persistence.failuretests.SQLServerAuctionDAOFailureTest1");
            fail("expect throw ObjectInstantiationException");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.impl.SQLServerAuctionDAO#SQLServerAuctionDAO(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSQLServerAuctionDAO2() throws Exception {
        try {
            new SQLServerAuctionDAO("com.orpheus.auction.persistence.failuretests.SQLServerAuctionDAOFailureTest2");
            fail("expect throw ObjectInstantiationException");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.impl.SQLServerAuctionDAO#SQLServerAuctionDAO(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSQLServerAuctionDAO3() throws Exception {
        try {
            new SQLServerAuctionDAO("com.orpheus.auction.persistence.failuretests.SQLServerAuctionDAOFailureTest3");
            fail("expect throw ObjectInstantiationException");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.impl.SQLServerAuctionDAO#SQLServerAuctionDAO(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSQLServerAuctionDAO4() throws Exception {
        try {
            new SQLServerAuctionDAO("com.orpheus.auction.persistence.failuretests.SQLServerAuctionDAOFailureTest4");
            fail("expect throw ObjectInstantiationException");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.impl.SQLServerAuctionDAO#SQLServerAuctionDAO(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSQLServerAuctionDAO5() throws Exception {
        try {
            new SQLServerAuctionDAO("com.orpheus.auction.persistence.failuretests.SQLServerAuctionDAOFailureTest5");
            fail("expect throw ObjectInstantiationException");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.impl.SQLServerAuctionDAO#SQLServerAuctionDAO(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSQLServerAuctionDAO6() throws Exception {
        try {
            new SQLServerAuctionDAO("com.orpheus.auction.persistence.failuretests.SQLServerAuctionDAOFailureTest6");
            fail("expect throw ObjectInstantiationException");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.impl.SQLServerAuctionDAO#SQLServerAuctionDAO(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSQLServerAuctionDAO7() throws Exception {
        try {
            new SQLServerAuctionDAO("com.orpheus.auction.persistence.failuretests.SQLServerAuctionDAOFailureTest7");
            fail("expect throw ObjectInstantiationException");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.impl.SQLServerAuctionDAO#SQLServerAuctionDAO(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSQLServerAuctionDAO8() throws Exception {
        try {
            new SQLServerAuctionDAO("com.orpheus.auction.persistence.failuretests.SQLServerAuctionDAOFailureTest8");
            fail("expect throw ObjectInstantiationException");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.impl.SQLServerAuctionDAO#SQLServerAuctionDAO(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSQLServerAuctionDAO9() throws Exception {
        try {
            new SQLServerAuctionDAO("com.orpheus.auction.persistence.failuretests.SQLServerAuctionDAOFailureTest9");
            fail("expect throw ObjectInstantiationException");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.impl.SQLServerAuctionDAO#SQLServerAuctionDAO(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSQLServerAuctionDAO10() throws Exception {
        try {
            new SQLServerAuctionDAO("com.orpheus.auction.persistence.failuretests.SQLServerAuctionDAOFailureTest10");
            fail("expect throw ObjectInstantiationException");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.impl.SQLServerAuctionDAO#SQLServerAuctionDAO(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSQLServerAuctionDAO11() throws Exception {
        try {
            new SQLServerAuctionDAO("com.orpheus.auction.persistence.failuretests.SQLServerAuctionDAOFailureTest11");
            fail("expect throw ObjectInstantiationException");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.impl.SQLServerAuctionDAO#SQLServerAuctionDAO(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSQLServerAuctionDAO12() throws Exception {
        try {
            new SQLServerAuctionDAO("com.orpheus.auction.persistence.failuretests.SQLServerAuctionDAOFailureTest12");
            fail("expect throw ObjectInstantiationException");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.impl.SQLServerAuctionDAO#SQLServerAuctionDAO(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSQLServerAuctionDAO13() throws Exception {
        try {
            new SQLServerAuctionDAO("com.orpheus.auction.persistence.failuretests.SQLServerAuctionDAOFailureTest13");
            fail("expect throw ObjectInstantiationException");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.impl.SQLServerAuctionDAO#SQLServerAuctionDAO(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSQLServerAuctionDAO14() throws Exception {
        try {
            new SQLServerAuctionDAO("com.orpheus.auction.persistence.failuretests.SQLServerAuctionDAOFailureTest14");
            fail("expect throw ObjectInstantiationException");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link com.orpheus.auction.persistence.impl.SQLServerAuctionDAO#SQLServerAuctionDAO(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSQLServerAuctionDAO15() throws Exception {
        try {
            new SQLServerAuctionDAO("com.orpheus.auction.persistence.failuretests.SQLServerAuctionDAOFailureTest15");
            fail("expect throw ObjectInstantiationException");
        } catch (ObjectInstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SQLServerAuctionDAO#createAuction(AuctionDTO)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateAuction1() throws Exception {
        try {
            sqlServerAuctionDAO.createAuction(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SQLServerAuctionDAO#createAuction(AuctionDTO)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateAuction2() throws Exception {
        AuctionDTO auction = FailureTestHelper.createAuctionDTO(1, 5);
        auction.setMinimumBid(-1);

        try {
            sqlServerAuctionDAO.createAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SQLServerAuctionDAO#createAuction(AuctionDTO)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateAuction3() throws Exception {
        AuctionDTO auction = FailureTestHelper.createAuctionDTO(1, 5);
        auction.setItemCount(-1);

        try {
            sqlServerAuctionDAO.createAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SQLServerAuctionDAO#createAuction(AuctionDTO)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateAuction4() throws Exception {
        AuctionDTO auction = FailureTestHelper.createAuctionDTO(1, 5);
        auction.setBids(null);

        try {
            sqlServerAuctionDAO.createAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SQLServerAuctionDAO#createAuction(AuctionDTO)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateAuction5() throws Exception {
        AuctionDTO auction = FailureTestHelper.createAuctionDTO(1, 5);
        auction.setBids(new BidDTO[] {null});

        try {
            sqlServerAuctionDAO.createAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SQLServerAuctionDAO#createAuction(AuctionDTO)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateAuction6() throws Exception {
        AuctionDTO auction = FailureTestHelper.createAuctionDTO(1, 5);
        (auction.getBids())[0].setImageId(-1);

        try {
            sqlServerAuctionDAO.createAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SQLServerAuctionDAO#createAuction(AuctionDTO)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateAuction7() throws Exception {
        AuctionDTO auction = FailureTestHelper.createAuctionDTO(1, 5);
        (auction.getBids())[0].setMaxAmount(-1);

        try {
            sqlServerAuctionDAO.createAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SQLServerAuctionDAO#createAuction(AuctionDTO)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateAuction8() throws Exception {
        AuctionDTO auction = FailureTestHelper.createAuctionDTO(1, 5);
        (auction.getBids())[0].setEffectiveAmount(new Integer(-1));

        try {
            sqlServerAuctionDAO.createAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SQLServerAuctionDAO#createAuction(AuctionDTO)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateAuction9() throws Exception {
        AuctionDTO auction = FailureTestHelper.createAuctionDTO(1, 5);
        auction.setId(null);

        try {
            sqlServerAuctionDAO.createAuction(auction);
            fail("InvalidEntryException should be thrown.");
        } catch (InvalidEntryException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link com.orpheus.auction.persistence.impl.SQLServerAuctionDAO#getAuction(long)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAuction1() throws Exception {
        try {
            sqlServerAuctionDAO.getAuction(11111);
            fail("expect throw EntryNotFoundException.");
        } catch (EntryNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SQLServerAuctionDAO#updateAuction(AuctionDTO)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateAuction1() throws Exception {
        try {
            sqlServerAuctionDAO.updateAuction(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SQLServerAuctionDAO#updateAuction(AuctionDTO)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateAuction2() throws Exception {
        AuctionDTO auction = FailureTestHelper.createAuctionDTO(1, 5);
        auction.setMinimumBid(-1);

        try {
            sqlServerAuctionDAO.updateAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SQLServerAuctionDAO#updateAuction(AuctionDTO)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateAuction3() throws Exception {
        AuctionDTO auction = FailureTestHelper.createAuctionDTO(1, 5);
        auction.setItemCount(-1);

        try {
            sqlServerAuctionDAO.updateAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SQLServerAuctionDAO#updateAuction(AuctionDTO)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateAuction4() throws Exception {
        AuctionDTO auction = FailureTestHelper.createAuctionDTO(1, 5);
        auction.setBids(null);

        try {
            sqlServerAuctionDAO.updateAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SQLServerAuctionDAO#updateAuction(AuctionDTO)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateAuction5() throws Exception {
        AuctionDTO auction = FailureTestHelper.createAuctionDTO(1, 5);
        auction.setBids(new BidDTO[] {null});

        try {
            sqlServerAuctionDAO.updateAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SQLServerAuctionDAO#updateAuction(AuctionDTO)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateAuction6() throws Exception {
        AuctionDTO auction = FailureTestHelper.createAuctionDTO(1, 5);
        (auction.getBids())[0].setImageId(-1);

        try {
            sqlServerAuctionDAO.updateAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SQLServerAuctionDAO#updateAuction(AuctionDTO)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateAuction7() throws Exception {
        AuctionDTO auction = FailureTestHelper.createAuctionDTO(1, 5);
        (auction.getBids())[0].setMaxAmount(-1);

        try {
            sqlServerAuctionDAO.updateAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SQLServerAuctionDAO#updateAuction(AuctionDTO)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateAuction8() throws Exception {
        AuctionDTO auction = FailureTestHelper.createAuctionDTO(1, 5);
        (auction.getBids())[0].setEffectiveAmount(new Integer(-1));

        try {
            sqlServerAuctionDAO.updateAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SQLServerAuctionDAO#updateAuction(AuctionDTO)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateAuction9() throws Exception {
        AuctionDTO auction = FailureTestHelper.createAuctionDTO(1, 5);
        auction.setId(null);

        try {
            sqlServerAuctionDAO.updateAuction(auction);
            fail("InvalidEntryException should be thrown.");
        } catch (InvalidEntryException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SQLServerAuctionDAO#updateBids(long, BidDTO[])}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateBids1() throws Exception {
        try {
            sqlServerAuctionDAO.updateBids(1, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SQLServerAuctionDAO#updateBids(long, BidDTO[])}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateBids2() throws Exception {
        BidDTO[] bids = new BidDTO[] {null};

        try {
            sqlServerAuctionDAO.updateBids(1, bids);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SQLServerAuctionDAO#updateBids(long, BidDTO[])}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateBids3() throws Exception {
        BidDTO[] bids = FailureTestHelper.createBidDTOs(1);
        bids[0].setImageId(-1);

        try {
            sqlServerAuctionDAO.updateBids(1, bids);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SQLServerAuctionDAO#updateBids(long, BidDTO[])}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateBids4() throws Exception {
        BidDTO[] bids = FailureTestHelper.createBidDTOs(1);
        bids[0].setMaxAmount(-1);

        try {
            sqlServerAuctionDAO.updateBids(1, bids);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SQLServerAuctionDAO#updateBids(long, BidDTO[])}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateBids5() throws Exception {
        BidDTO[] bids = FailureTestHelper.createBidDTOs(1);
        bids[0].setEffectiveAmount(new Integer(-1));

        try {
            sqlServerAuctionDAO.updateBids(1, bids);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SQLServerAuctionDAO#updateBids(long, BidDTO[])}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateBids6() throws Exception {
        BidDTO[] bids = FailureTestHelper.createBidDTOs(2);

        try {
            sqlServerAuctionDAO.updateBids(1, bids);
            fail("EntryNotFoundException should be thrown.");
        } catch (EntryNotFoundException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SQLServerAuctionDAO#deleteAuction(long)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteAuction_NotExistAuctionId() throws Exception {
        AuctionDTO auction = FailureTestHelper.createAuctionDTO(1, 5);

        try {
            sqlServerAuctionDAO.deleteAuction(auction.getId().longValue());
            fail("EntryNotFoundException is expected.");
        } catch (EntryNotFoundException e) {
            // good
        }
    }

}
