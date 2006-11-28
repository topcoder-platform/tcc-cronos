/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.persistence.accuracytests;

import com.mockrunner.mock.ejb.MockUserTransaction;

import com.orpheus.administration.persistence.AdminData;
import com.orpheus.administration.persistence.AdminDataBean;
import com.orpheus.administration.persistence.AdminDataHome;
import com.orpheus.administration.persistence.AdminSummary;
import com.orpheus.administration.persistence.PendingWinner;

import com.topcoder.util.puzzle.PuzzleData;

import junit.framework.TestCase;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;

import org.mockejb.jndi.MockContextFactory;

import java.sql.Connection;

import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.rmi.PortableRemoteObject;

/**
 * <p>Accuracy test cases for class AdminDataBean.</p>
 * @author waits
 * @version 1.0
 */
public class AdminDataBeanAccTests extends TestCase {
    /** State of this test case. These variables are initialized by setUp method */
    private Context context;

    /** Represents the connection instance for testing. */
    private Connection connection = null;

    /** mock user trnasaction. */
    private MockUserTransaction mockTransaction;

    /** AdminData interface. */
    AdminData remote = null;

    /**
     * create instance.
     *
     * @throws Exception into Junit
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfigManager();

        //add config files
        TestHelper.addConfigFile(TestHelper.DBFACTORY_CONFIG_XML);
        TestHelper.addConfigFile(TestHelper.OBJECT_FACTORY_CONFIG_XML);
        TestHelper.addConfigFile(TestHelper.ADMINISTRATION_PERSISTENCE_CONFIG_FILE);
        TestHelper.addConfigFile(TestHelper.SEARCH_BUNDLE_CONFIG_FILE);
        //      insert data to database for testing
        connection = TestHelper.getConnection();

        //clear the database
        TestHelper.clearDatabase(connection);

        /* We need to set MockContextFactory as our JNDI provider.
         * This method sets the necessary system properties.
         */
        MockContextFactory.setAsInitial();

        // create the initial context that will be used for binding EJBs
        context = new InitialContext();

        // Create an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        /* Create deployment descriptor of our sample bean.
         * MockEjb uses it instead of XML deployment descriptors
         */
        SessionBeanDescriptor adminRemoteDes = new SessionBeanDescriptor("admin/AdminData", AdminDataHome.class,
                AdminData.class, new AdminDataBean());
        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(adminRemoteDes);

        //      Lookup the home
        Object msgHomeObj = context.lookup("admin/AdminData");

        // PortableRemoteObject does not do anything in MockEJB but it does no harm to call it
        AdminDataHome adminHome = (AdminDataHome) PortableRemoteObject.narrow(msgHomeObj, AdminDataHome.class);

        // create the bean
        remote = adminHome.create();

        mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);
    }

    /**
     * Test storePuzzle(puzzledata) method.
     *
     * @throws Exception into JUnit
     */
    public void testStorePuzzle() throws Exception {
        PuzzleData[] toStore = TestHelper.buildPuzzleData(TestHelper.PUZZLE_DATA_COUNT);

        //store the puzzle
        long[] puzzleIds = remote.storePuzzles(toStore);

        assertEquals("The size of puzzleIds is invalid.", puzzleIds.length, TestHelper.PUZZLE_DATA_COUNT);

        //verify
        PuzzleData[] stored = TestHelper.getPuzzle(connection, puzzleIds);
        TestHelper.assertEquals(toStore, stored);
    }

    /**
     * Test setDomainApproval method.
     *
     * @throws Exception into Junit
     */
    public void testSetDomainApproval() throws Exception {
        long domainId = TestHelper.persistDomain(connection);
        remote.setDomainApproval(domainId, true);
        assertTrue("it is approved now.", TestHelper.getDomainApproval(connection, domainId));
    }

    /**
     * Test method setImageApproval.
     *
     * @throws Exception into Junit
     */
    public void testSetImageApproval() throws Exception {
        long domainId = TestHelper.persistDomain(connection);

        long imageId = TestHelper.persistImage(connection, domainId);
        remote.setImageApproval(imageId, true);
        assertTrue("it is approved now.", TestHelper.getImageApproval(connection, imageId));
    }

    /**
     * Test approveWinner(PendingWinner winner, Date date) method.
     *
     * @throws Exception into JUnit
     */
    public void testApproveWinner() throws Exception {
        PendingWinner winner = TestHelper.persistGame(connection);
        remote.approveWinner(winner, new Date(4000));

        List ret = TestHelper.getPayout(connection, winner);

        assertEquals("The payout is invalid.", (int) ((1.0 - 0.15) * 100), ((Long) ret.get(1)).longValue());
        assertEquals("The date is invalid.", 4000, ((Date) ret.get(0)).getTime());
    }

    /**
     * Test rejectWinner method.
     *
     * @throws Exception into JUnit
     */
    public void testRejectWinner() throws Exception {
        PendingWinner winner = TestHelper.persistGame(connection);
        remote.rejectWinner(winner);

        assertTrue("it is approved.", TestHelper.getWinnerApprove(connection, winner));
    }

    /**
     * Test getPendingWinners method.
     *
     * @throws Exception into Junit
     */
    public void testGetPendingWinners() throws Exception {
        PendingWinner winner = TestHelper.persistGame(connection);
        PendingWinner[] winners = remote.getPendingWinners();
        assertEquals("The size of pengding winners is wrong.", 1, winners.length);
        assertEquals("The winner's gameId is invalid.", winner.getGameId(), winners[0].getGameId());
        assertEquals("The winner's playerId is invalid.", winner.getPlayerId(), winners[0].getPlayerId());
        assertEquals("The winner's payout is invalid.", winner.getPayout(), winners[0].getPayout());
    }

    /**
     * <p>
     * Test the getAdminSummary method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetAdminSummary() throws Exception {
        TestHelper.persistGame(connection);

        AdminSummary adminSummary = remote.getAdminSummary();
        assertEquals("The pendingWinner count is wrong.", 1, adminSummary.getPendingWinnerCount());
        assertEquals("The activeGame count is wrong.", 1, adminSummary.getActiveGameCount());
        assertEquals("The pending sponsor count is wrong.", 0, adminSummary.getPendingSponsorCount());
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared. The test data in the table will
     * be cleared.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        try {
            TestHelper.clearConfigManager();
            TestHelper.clearDatabase(connection);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
