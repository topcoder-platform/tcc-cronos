/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import com.topcoder.util.puzzle.PuzzleData;
import com.topcoder.util.puzzle.PuzzleResource;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * Demonstrates the use of the message functionality of the <code>Administration Persistence</code> component.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class AdminDataDemo extends AdminTestBase {
    /**
     * Demonstrates the admin data functionality.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_demo_admin_data() throws Exception {
        // obtain remote interface: the details are omitted.
        AdminDataBean admin = new AdminDataBean();

        // we might begin by storing some new puzzles
        PuzzleData[] puzzles = new PuzzleData[] {createPuzzle()};
        long[] ids = admin.storePuzzles(puzzles);
        // at this point, all puzzles are persisted

        // we have some images and domains to approve and reject
        long imageId1 = addImage(1);
        long imageId2 = addImage(2);
        long domainId1 = addDomain(1, "some domain URL");
        long domainId2 = addDomain(2, "some other domain URL");

        admin.setDomainApproval(domainId1, true); // accept this domain
        admin.setDomainApproval(domainId2, false); // reject this domain
        admin.setImageApproval(imageId1, true); // accept this image
        admin.setImageApproval(imageId2, false); // reject this image

        addPendingWinner(3);
        addPendingWinner(4);

        // we now get some information on pending winners and sponsors and active
        // games by getting a summary
        AdminSummary summary = admin.getAdminSummary();
        // we now get some detailed information on all pending winners
        PendingWinner[] pendingWinners = admin.getPendingWinners();

        // We can approve and reject some pending winners form above
        PendingWinner pendingWinner1 = pendingWinners[0];
        PendingWinner pendingWinner2 = pendingWinners[1];

        admin.approveWinner(pendingWinner1, new Date());
        admin.rejectWinner(pendingWinner2);
    }

    /**
     * Returns a sample puzzle.
     *
     * @return a sample puzzle
     */
    private static PuzzleData createPuzzle() {
        Map attributes = new TreeMap();
        attributes.put(PuzzleData.PUZZLE_TYPE_ATTRIBUTE, "puzzletype");
        attributes.put("some other attribute", "some value");
        Map resources = new TreeMap();
        PuzzleResource resource = new PuzzleResourceImpl("some resource", "GIF", new byte[] {1, 2, 3, 4, 5});
        resources.put("some resource", resource);

        return new PuzzleDataImpl(attributes, resources);
    }

    /**
     * Adds a new sponsor and image to the database.
     *
     * @param sponsorID the ID of the sponsor to add
     * @return the ID of the newly-added image
     * @throws Exception if an unexpected exception occurs
     */
    private long addImage(long sponsorID) throws Exception {
        addSponsor(sponsorID, null);
        long downloadObjectID = addDownloadObject("GIF", "");
        long domainID = addDomain(sponsorID, "URL");
        return addImage(domainID, downloadObjectID, "an image");
    }

    /**
     * Adds a player as a pending winner.
     *
     * @param playerID the player ID to add
     * @throws Exception if an unexpected exception occurs
     */
    private void addPendingWinner(long playerID) throws Exception {
        long game = addGame(new Date());
        addPlayer(playerID);
        addPlayerCompleted(game, playerID);
    }
}
