/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl;

import com.orpheus.administration.persistence.AdminTestBase;
import com.orpheus.administration.persistence.EntryNotFoundException;
import com.orpheus.administration.persistence.InstantiationException;
import com.orpheus.administration.persistence.InvalidEntryException;
import com.orpheus.administration.persistence.PendingWinner;
import com.orpheus.administration.persistence.PuzzleDataImpl;
import com.orpheus.administration.persistence.PuzzleResourceImpl;

import com.topcoder.util.puzzle.PuzzleData;
import com.topcoder.util.puzzle.PuzzleResource;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Unit tests for the <code>SQLServerAdminDataDAO</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class SQLServerAdminDataDAOTests extends AdminTestBase {
    /**
     * The <code>SQLServerAdminDataDAO</code> instance to use for the test. This member is initialized in {@link
     * #setUp setUp} to be a new instance for each test.
     */
    private SQLServerAdminDataDAO dao;

    /**
     * Pre-test setup: initializes the <code>SQLServerAdminDataDAO</code> instance, config manager, and database
     * connection for the next step.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void setUp() throws Exception {
        super.setUp();

        dao = new SQLServerAdminDataDAO("admin.dao.config");
    }

    /**
     * Tests that the constructor throws <code>IllegalArgumentException</code> when passed a <code>null</code>
     * namespace.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor_null_namespace() throws Exception {
        try {
            new SQLServerAdminDataDAO(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the constructor throws <code>IllegalArgumentException</code> when passed an empty namespace.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor_empty_namespace() throws Exception {
        try {
            new SQLServerAdminDataDAO("   ");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the constructor throws <code>InstantiationException</code> when passed an empty namespace.
     */
    public void test_ctor_missing_namespace() {
        try {
            new SQLServerAdminDataDAO("no.such.namespace");
            fail("should have thrown InstantiationException");
        } catch (InstantiationException ex) {
            // pass
        }
    }

    /**
     * Tests that the constructor throws <code>InstantiationException</code> when the configuration has an invalid
     * <code>adminFee</code>.
     */
    public void test_ctor_invalid_adminFee() {
        try {
            new SQLServerAdminDataDAO("invalid.adminFee");
            fail("should have thrown InstantiationException");
        } catch (InstantiationException ex) {
            // pass
        }
    }

    /**
     * Tests that the constructor throws <code>InstantiationException</code> when the configuration has an invalid
     * <code>specNamespace</code>.
     */
    public void test_ctor_invalid_specNamespace() {
        try {
            new SQLServerAdminDataDAO("invalid.specNamespace");
            fail("should have thrown InstantiationException");
        } catch (InstantiationException ex) {
            // pass
        }
    }

    // the normal operation of the constructor is tested elsewhere, so no need for a special test

    /**
     * Tests that the <code>getAdminSummary</code> method computes the pending winner count correctly.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getAdminSummary_pending_winner_count() throws Exception {
        createPendingWinners();

        assertEquals("there should be 3 pending winners", 3, dao.getAdminSummary().getPendingWinnerCount());
    }

    /**
     * Tests that the <code>getAdminSummary</code> method computes the pending sponsor count correctly.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getAdminSummary_pending_sponsor_count() throws Exception {
        // add 4 sponsors
        addSponsor(1, Boolean.TRUE); // an approved sponsor
        addSponsor(2, Boolean.FALSE); // a rejected sponsor
        addSponsor(3, null); // a pending sponsor
        addSponsor(4, null); // a pending sponsor

        assertEquals("there should be 2 pending sponsors", 2, dao.getAdminSummary().getPendingSponsorCount());
    }

    /**
     * Tests that the <code>getAdminSummary</code> method computes the active game count correctly.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getAdminSummary_active_game_count() throws Exception {
        Date now = new Date();

        // add a game with no winner (this should count towards the active game total)
        addGame(now);

        // add a game with a winner (this should not count towards the total)
        long id = addGame(now);
        addPlayer(1);
        addPlayerWon(id, 1, now, 100);

        // add a game 25 hours in the future (this should not count towards the total)
        addGame(new Date(System.currentTimeMillis() + TWENTY_FIVE_HOURS));

        assertEquals("there should be 1 active game", 1, dao.getAdminSummary().getActiveGameCount());
    }

    /**
     * Tests that the <code>setDomainApproval</code> method throws <code>EntryNotFoundException</code> is the
     * specified domain does not exist.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_setDomainApproval_no_domain() throws Exception {
        try {
            dao.setDomainApproval(1, true);
            fail("should have thrown EntryNotFoundException");
        } catch (EntryNotFoundException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>setDomainApproval</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_setDomainApproval() throws Exception {
        addSponsor(1, null);
        long id = addDomain(1, "URL");

        dao.setDomainApproval(id, true);
        assertTrue("domain approval should be true", getApproval("domain", id));

        dao.setDomainApproval(id, false);
        assertFalse("domain approval should be true", getApproval("domain", id));
    }

    /**
     * Tests that the <code>setImageApproval</code> method throws <code>EntryNotFoundException</code> when the
     * image does not exist.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_setImageApproval_no_image() throws Exception {
        try {
            dao.setImageApproval(1, true);
            fail("should have thrown EntryNotFoundException");
        } catch (EntryNotFoundException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>setImageApproval</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_setImageApproval() throws Exception {
        addSponsor(1, null);
        long downloadObjectID = addDownloadObject("GIF", "");
        long domainID = addDomain(1, "URL");
        long id = addImage(domainID, downloadObjectID, "an image");

        dao.setImageApproval(id, true);
        assertTrue("image approval should be true", getApproval("image", id));

        dao.setImageApproval(id, false);
        assertFalse("image approval should be false", getApproval("image", id));
    }

    /**
     * Tests that <code>storePuzzles</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> puzzle array.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_storePuzzles_null_puzzles() throws Exception {
        try {
            dao.storePuzzles(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>storePuzzles</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> puzzle element.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_storePuzzles_null_puzzle_element() throws Exception {
        try {
            dao.storePuzzles(new PuzzleData[] {null});
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>storePuzzles</code> throws <code>IllegalArgumentException</code> when passed an empty
     * puzzle array.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_storePuzzles_empty_puzzles() throws Exception {
        try {
            dao.storePuzzles(new PuzzleData[0]);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>storePuzzles</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_storePuzzles() throws Exception {
        Map attributes = new TreeMap();
        attributes.put(PuzzleData.PUZZLE_TYPE_ATTRIBUTE, "puzzletype");
        attributes.put("some other attribute", "some value");
        Map resources = new TreeMap();
        PuzzleResource resource = new PuzzleResourceImpl("some resource", "GIF", new byte[] {1, 2, 3, 4, 5});
        resources.put("some resource", resource);

        PuzzleData puzzle = new PuzzleDataImpl(attributes, resources);

        long[] ids = dao.storePuzzles(new PuzzleData[] {puzzle});
        assertEquals("one ID should be returned", 1, ids.length);

        Set puzzles = readStoredPuzzles();
        assertEquals("one puzzle should be stored", 1, puzzles.size());
        assertTrue("the puzzle name should be 'puzzletype'", puzzles.contains("puzzletype"));

        assertEquals("stored attributes should match the puzzle attributes", attributes, readStoredAttributes(ids[0]));

        Map storedResources = readStoredResources(ids[0]);
        assertEquals("one resource should be stored", 1, storedResources.size());
        PuzzleResource storedResource = (PuzzleResource) storedResources.values().iterator().next();

        assertEquals("the stored resource should equal the original resource",
                     storedResource.getName(), resource.getName());
        assertEquals("the stored resource should equal the original resource",
                     storedResource.getMediaType(), resource.getMediaType());
        assertTrue("the stored resource should equal the original resource",
                   Arrays.equals(storedResource.getData(), resource.getData()));
    }

    /**
     * Tests the <code>getPendingWinners</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getPendingWinners() throws Exception {
        long[][] pendingWinners = createPendingWinners();

        PendingWinner[] winners = dao.getPendingWinners();

        assertEquals("there should be " + pendingWinners.length + " pending winners",
                     pendingWinners.length, winners.length);

        for (int idx = 0; idx < pendingWinners.length; ++idx) {
            boolean found = false;
            for (int widx = 0; widx < winners.length && !found; ++widx) {
                PendingWinner winner = winners[widx];
                if (pendingWinners[idx][0] == winner.getGameId() && pendingWinners[idx][1] == winner.getPlayerId()) {
                    found = true;
                }
            }

            if (!found) {
                fail("getPendingWinners should return the pending winners");
            }
        }
    }

    /**
     * Tests that <code>approveWinner</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> winner.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_approveWinner_null_winner() throws Exception {
        try {
            dao.approveWinner(null, new Date());
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>approveWinner</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> date.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_approveWinner_null_date() throws Exception {
        try {
            dao.approveWinner(new PendingWinnerImpl(1, 2, 3), null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>approveWinner</code> throws <code>EntryNotFoundException</code> when the entry does not
     * exist.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_approveWinner_no_entry() throws Exception {
        try {
            dao.approveWinner(new PendingWinnerImpl(1, 2, 3), new Date());
            fail("should have thrown EntryNotFoundException");
        } catch (EntryNotFoundException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>approveWinner</code> throws <code>InvalidEntryException</code> when the entry has already
     * been handled.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_approveWinner_invalid() throws Exception {
        Date now = new Date();

        // add a pending winner
        long game = addGame(now);
        addPlayer(1);
        addPlayerCompleted(game, 1);

        PendingWinner winner = new PendingWinnerImpl(1, game, 100);

        // the first time should succeed
        dao.approveWinner(winner, now);

        // the second time should throw InvalidEntryException, since the winner has already been handled
        try {
            dao.approveWinner(winner, now);
            fail("should have thrown InvalidEntryException");
        } catch (InvalidEntryException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>approveWinner</code> throws <code>InvalidEntryException</code> if the game already has a
     * winner.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_approveWinner_winner_already_exists() throws Exception {
        Date now = new Date();

        // add a pending winner
        long game = addGame(now);
        addPlayer(1);
        addPlayerCompleted(game, 1);

        PendingWinner winner1 = new PendingWinnerImpl(1, game, 100);

        // the first time should succeed
        dao.approveWinner(winner1, now);

        addPlayer(2);
        addPlayerCompleted(game, 2);

        PendingWinner winner2 = new PendingWinnerImpl(2, game, 100);

        try {
            dao.approveWinner(winner2, now);
            fail("should have thrown InvalidEntryException");
        } catch (InvalidEntryException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>approveWinner</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_approveWinner() throws Exception {
        Date now = new Date();

        // add a pending winner
        long game = addGame(now);
        addPlayer(1);
        addPlayerCompleted(game, 1);

        PendingWinner winner = new PendingWinnerImpl(1, game, 100);

        dao.approveWinner(winner, now);

        assertHandled(winner);
        assertPlayerWon(winner, now);
    }

    /**
     * Tests that <code>rejectWinner</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> winner.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_rejectWinner_null_winner() throws Exception {
        try {
            dao.rejectWinner(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>rejectWinner</code> throws <code>EntryNotFoundException</code> when the entry does not
     * exist.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_rejectWinner_no_entry() throws Exception {
        try {
            dao.rejectWinner(new PendingWinnerImpl(1, 2, 3));
            fail("should have thrown EntryNotFoundException");
        } catch (EntryNotFoundException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>rejectWinner</code> throws <code>InvalidEntryException</code> when the entry has already
     * been handled.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_rejectWinner_invalid() throws Exception {
        // add a pending winner
        long game = addGame(new Date());
        addPlayer(1);
        addPlayerCompleted(game, 1);

        PendingWinner winner = new PendingWinnerImpl(1, game, 100);

        // the first time should succeed
        dao.rejectWinner(winner);

        // the second time should throw InvalidEntryException, since the winner has already been handled
        try {
            dao.rejectWinner(winner);
            fail("should have thrown InvalidEntryException");
        } catch (InvalidEntryException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>rejectWinner</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_rejectWinner() throws Exception {
        // add a pending winner
        long game = addGame(new Date());
        addPlayer(1);
        addPlayerCompleted(game, 1);

        PendingWinner winner = new PendingWinnerImpl(1, game, 100);

        dao.rejectWinner(winner);

        assertHandled(winner);
    }

    /**
     * Tests the <code>calculatePayout</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_calculatePayout() throws Exception {
        Date now = new Date();
        long gameID = addGame(now);
        long hostingBlockID = addHostingBlock(gameID);
        addAuction(hostingBlockID, now, now, 0, 1, 1);
        long downloadObjectID = addDownloadObject("GIF", "");
        addSponsor(1, null);
        long domainID = addDomain(1, "URL");
        long imageID = addImage(domainID, downloadObjectID, "an image");

        int totalGrossPayment = 0;
        for (int i = 0; i < 5; ++i) {
            long bidID = addBid(imageID, hostingBlockID);
            long hostingSlotID = addHostingSlot(bidID, 1, now);
            addEffectiveBid(bidID, 100);
            totalGrossPayment += 100;
        }

        int netPayment = totalGrossPayment * 85 / 100; // 15% admin fee

        assertEquals("payment should be " + netPayment, netPayment, dao.calculatePayout(gameID));
    }
}
