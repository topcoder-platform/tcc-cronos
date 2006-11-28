/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Base class containing common helper methods used by the administrative tests.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public abstract class AdminTestBase extends DAOTestBase {
    /**
     * The number of milliseconds in 25 hours.
     */
    public static final int TWENTY_FIVE_HOURS = 25 * 60 * 60 * 1000;

    /**
     * Flag indicating whether {@link #setUp setUp} has been called before. It is initially <code>true</code> and
     * will be set to <code>false</code> the first time <code>setUp</code> runs.
     */
    private boolean firstTime = true;

    /**
     * Pre-test setup: initializes the config manager and database connection for the next step.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void setUp() throws Exception {
        if (firstTime) {
            removeAllNamespaces();
        }

        getConfigManager().add("database_config.xml");
        getConfigManager().add("admin_dao_tests.xml");
        getConfigManager().add("dao_factory_config.xml");

        super.setUp();

        if (firstTime) {
            clearAllTables();
            firstTime = false;
        }
    }

    /**
     * Post-test cleanup: clears all database tables, clears the config manager, and closes the connection.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void tearDown() throws Exception {
        clearAllTables();
        super.tearDown();
    }

    /**
     * Clears all database tables associated with the admin tests.
     *
     * @throws Exception if an unexpected exception occurs
     */
    private void clearAllTables() throws Exception {
        // note that because of foreign key constraints, the order of clearing the tables is significant
        clearTable("effective_bid");
        clearTable("hosting_slot");
        clearTable("bid");
        clearTable("auction");
        clearTable("hosting_block");
        clearTable("puzzle_resource");
        clearTable("puzzle_attribute");
        clearTable("puzzle");
        clearTable("image");
        clearTable("domain");
        clearTable("sponsor");
        clearTable("plyr_compltd_game");
        clearTable("plyr_won_game");
        clearTable("game");
        clearTable("player");
        clearTable("any_user");
        clearTable("download_obj");
    }

    /**
     * Adds a row to the <code>hosting_slot</code> table with the specified values.
     *
     * @param bidID the value for the <code>bid_id</code> column
     * @param sequenceNumber the value for the <code>sequence_number</code> column
     * @param hostingStart the value for the <code>hosting_start</code> column
     * @return the ID of the newly-inserted row
     * @throws Exception if a database error occurs
     */
    protected long addHostingSlot(long bidID, long sequenceNumber, Date hostingStart) throws Exception {
        CallableStatement statement = getConnection().prepareCall("{call InsertHostingSlot(?, ?, ?, ?)}");
        try {
            statement.setLong(1, bidID);
            statement.setLong(2, sequenceNumber);
            statement.setDate(3, new java.sql.Date(hostingStart.getTime()));
            statement.registerOutParameter(4, Types.BIGINT);
            statement.execute();
            return statement.getLong(4);
        } finally {
            statement.close();
        }
    }

    /**
     * Inserts a new row into the <code>effective_bid</code> table with the specified values.
     *
     * @param bidID the value for the <code>bid_id</code> column
     * @param amount the value for the <code>current_amount</code> column
     * @throws Exception if a database error occurs
     */
    protected void addEffectiveBid(long bidID, long amount) throws Exception {
        PreparedStatement statement =
            getConnection().prepareStatement("INSERT INTO effective_bid (bid_id, current_amount) VALUES (?, ?)");

        try {
            statement.setLong(1, bidID);
            statement.setLong(2, amount);

            assertEquals("should have inserted 1 item", 1, statement.executeUpdate());
        } finally {
            statement.close();
        }
    }

    /**
     * Adds a new row to the <code>auction</code> table with the specified values.
     *
     * @param hostingBlockID the value for the <code>hosting_block_id</code> column
     * @param startTime the value for the <code>start_time</code> column
     * @param endTime the value for the <code>end_time</code> column
     * @param minBid the value for the <code>min_bid</code> column
     * @param increment the value for the <code>increment</code> column
     * @param items the value for the <code>items</code> column
     * @throws Exception if a database error occurs
     */
    protected void addAuction(long hostingBlockID, Date startTime, Date endTime, long minBid, long increment,
                              long items) throws Exception {
        PreparedStatement statement =
            getConnection().prepareStatement("INSERT INTO auction (hosting_block_id, start_time, end_time, min_bid, "
                                        + "bid_increment, item_count) VALUES (?, ?, ?, ?, ?, ?)");

        try {
            statement.setLong(1, hostingBlockID);
            statement.setDate(2, new java.sql.Date(startTime.getTime()));
            statement.setDate(3, new java.sql.Date(endTime.getTime()));
            statement.setLong(4, minBid);
            statement.setLong(5, increment);
            statement.setLong(6, items);

            assertEquals("should have inserted 1 row", 1, statement.executeUpdate());
        } finally {
            statement.close();
        }
    }

    /**
     * Adds a new row to the <code>hosting_block</code> table with the specified values.
     *
     * @param gameID the value for the <code>game_id</code> column
     * @return the ID of the newly-inserted hosting block
     * @throws Exception if a database error occurs
     */
    protected long addHostingBlock(long gameID) throws Exception {
        CallableStatement statement = getConnection().prepareCall("{call InsertHostingBlock(?, ?)}");
        try {
            statement.setLong(1, gameID);
            statement.registerOutParameter(2, Types.BIGINT);
            statement.execute();
            return statement.getLong(2);
        } finally {
            statement.close();
        }
    }

    /**
     * Inserts a new row into the <code>bid</code> table with the specified values.
     *
     * @param imageID the value for the <code>image_id</code> column
     * @param auctionID the value for the <code>auction_id</code> column
     * @return the ID of the newly-inserted bid
     * @throws Exception if a database error occurs
     */
    protected long addBid(long imageID, long auctionID) throws Exception {
        CallableStatement statement = getConnection().prepareCall("{call InsertBid(?, ?, ?)}");
        try {
            statement.setLong(1, imageID);
            statement.setLong(2, auctionID);
            statement.registerOutParameter(3, Types.BIGINT);
            statement.execute();
            return statement.getLong(3);
        } finally {
            statement.close();
        }
    }

    /**
     * Inserts a new row into the <code>image</code> table with the specified values.
     *
     * @param domainID the value for the <code>domain_id</code> column
     * @param downloadObjectID the value for the <code>download_object_id</code> column
     * @param description the value for the <code>description</code> column
     * @return the ID of the newly-inserted image
     * @throws Exception if a database error occurs
     */
    protected long addImage(long domainID, long downloadObjectID, String description) throws Exception {
        CallableStatement statement = getConnection().prepareCall("{call InsertImage(?, ?, ?, ?)}");
        try {
            statement.setLong(1, domainID);
            statement.setLong(2, downloadObjectID);
            statement.setString(3, description);
            statement.registerOutParameter(4, Types.BIGINT);
            statement.execute();
            return statement.getLong(4);
        } finally {
            statement.close();
        }
    }

    /**
     * Adds a new entry to the <code>download_obj</code> table.
     *
     * @param media the media
     * @param content the content
     * @return the ID of the newly-inserted download object
     * @throws Exception if an unexpected exception occurs
     */
    protected long addDownloadObject(String media, String content) throws Exception {
        CallableStatement statement = getConnection().prepareCall("{call InsertDownloadObject(?, ?, ?)}");
        try {
            statement.setString(1, media);
            statement.setBytes(2, content.getBytes());
            statement.registerOutParameter(3, Types.BIGINT);
            statement.execute();
            return statement.getLong(3);
        } finally {
            statement.close();
        }
    }

    /**
     * Adds a new entry to the <code>game</code> table.
     *
     * @param date the start date of the game
     * @return the ID of the newly-inserted game
     * @throws Exception if <code>date</code> is <code>null</code>
     */
    protected long addGame(Date date) throws Exception {
        CallableStatement statement = getConnection().prepareCall("{call InsertGame(?, ?)}");
        try {
            statement.setDate(1, new java.sql.Date(date.getTime()));
            statement.registerOutParameter(2, Types.BIGINT);
            statement.execute();
            return statement.getLong(2);
        } finally {
            statement.close();
        }
    }

    /**
     * Adds a new player to the <code>any_user</code> and <code>player</code> tables.
     *
     * @param id the player ID
     * @throws Exception if an unexpected exception occurs
     */
    protected void addPlayer(long id) throws Exception {
        PreparedStatement statement = getConnection().prepareStatement("INSERT INTO any_user (id) VALUES (?)");
        try {
            statement.setLong(1, id);
            assertEquals("should have inserted 1 player", 1, statement.executeUpdate());
        } finally {
            statement.close();
        }

        statement = getConnection().prepareStatement("INSERT INTO player (any_user_id) VALUES (?)");
        try {
            statement.setLong(1, id);
            assertEquals("should have inserted 1 player", 1, statement.executeUpdate());
        } finally {
            statement.close();
        }
    }

    /**
     * Adds an entry into the <code>plyr_won_game</code> table.
     *
     * @param gameID the game ID
     * @param playerID the player ID
     * @param date the date
     * @param payout the payout amount
     * @throws Exception if an unexpected exception occurs
     */
    protected void addPlayerWon(long gameID, long playerID, Date date, long payout) throws Exception {
        PreparedStatement statement =
            getConnection().prepareStatement("INSERT INTO plyr_won_game (game_id, player_id, date, payout) VALUES "
                                        + "(?, ?, ?, ?)");
        try {
            statement.setLong(1, gameID);
            statement.setLong(2, playerID);
            statement.setDate(3, new java.sql.Date(date.getTime()));
            statement.setLong(4, payout);

            assertEquals("should have insert 1 player", 1, statement.executeUpdate());
        } finally {
            statement.close();
        }
    }

    /**
     * Returns the current <code>is_approved</code> setting for the specified table entry.
     *
     * @param table the table name
     * @param id the domain ID
     * @return the <code>is_approved</code> setting for the specified domain
     * @throws Exception if an unexpected exception occurs
     * @throws junit.framework.AssertionFailedError if the query returns an unexpected result
     */
    protected boolean getApproval(String table, long id) throws Exception {
        PreparedStatement statement =
            getConnection().prepareStatement("SELECT is_approved FROM " + table + " WHERE id = ?");
        try {
            statement.setLong(1, id);

            ResultSet results = statement.executeQuery();
            try {
                assertTrue("query did not return any results", results.next());
                String val = results.getString(1);
                if (val.equals("Y")) {
                    return true;
                } else if (val.equals("N")) {
                    return false;
                } else {
                    fail("invalid value for is_approved: " + val);
                    return false;
                }
            } finally {
                results.close();
            }
        } finally {
            statement.close();
        }
    }

    /**
     * Adds a new entry in the <code>domain</code> table.
     *
     * @param sponsorID the sponsor ID
     * @param url the base URL
     * @return the ID of the newly-inserted domain
     * @throws Exception if an unexpected exception occurs
     */
    protected long addDomain(long sponsorID, String url) throws Exception {
        CallableStatement statement = getConnection().prepareCall("{call InsertDomain(?, ?, ?)}");
        try {
            statement.setLong(1, sponsorID);
            statement.setString(2, url);
            statement.registerOutParameter(3, Types.BIGINT);
            statement.execute();

            return statement.getLong(3);
        } finally {
            statement.close();
        }
    }

    /**
     * Inserts a new entry into the <code>plyr_compltd_game</code> table.
     *
     * @param gameID the game ID
     * @param playerID the player ID
     * @throws Exception if an unexpected exception occurs
     */
    protected void addPlayerCompleted(long gameID, long playerID) throws Exception {
        PreparedStatement statement =
            getConnection().prepareStatement("INSERT INTO plyr_compltd_game (game_id, player_id) VALUES (?, ?)");
        try {
            statement.setLong(1, gameID);
            statement.setLong(2, playerID);

            assertEquals("should have inserted 1 player", 1, statement.executeUpdate());
        } finally {
            statement.close();
        }
    }

    /**
     * Adds an entry to the <code>sponsor</code> table.
     *
     * @param id the user ID of the sponsor
     * @param approved the value for <code>is_approved</code> or <code>null</code> to leave it as a <code>null</code>
     * @throws Exception if an unexpected exception occurs
     */
    protected void addSponsor(long id, Boolean approved) throws Exception {
        addPlayer(id);
        if (approved != null) {
            PreparedStatement statement =
                getConnection().prepareStatement("INSERT INTO sponsor (any_user_id, is_approved) VALUES (?, ?)");

            try {
                statement.setLong(1, id);
                statement.setString(2, (approved.booleanValue() ? "Y" : "N"));
                assertEquals("should have inserted 1 sponsor", 1, statement.executeUpdate());
            } finally {
                statement.close();
            }
        } else {
            PreparedStatement statement =
                getConnection().prepareStatement("INSERT INTO sponsor (any_user_id) VALUES (?)");
            try {
                statement.setLong(1, id);
                assertEquals("should have inserted 1 sponsor", 1, statement.executeUpdate());
            } finally {
                statement.close();
            }
        }
    }

    /**
     * Returns a map of the stored attributes for the specified puzzle.
     *
     * @param puzzleID the ID of the puzzle for which to retrieve the stored attributes
     * @return a map of the stored attributes for the specified puzzle
     * @throws Exception if a database error occurs
     */
    protected Map readStoredAttributes(long puzzleID) throws Exception {
        PreparedStatement statement = getConnection().prepareStatement("SELECT name, value FROM puzzle_attribute WHERE "
                                                                  + "puzzle_id = ?");

        try {
            statement.setLong(1, puzzleID);

            ResultSet results = statement.executeQuery();
            Map attrs = new TreeMap();
            try {
                while (results.next()) {
                    attrs.put(results.getString(1), results.getString(2));
                }

                return attrs;
            } finally {
                results.close();
            }
        } finally {
            statement.close();
        }
    }

    /**
     * Returns a set of the names of the puzzles stored in the <code>puzzle</code> table.
     *
     * @return a set of the names of the puzzles stored in the <code>puzzle</code> table
     * @throws Exception if a database error occurs
     */
    protected Set readStoredPuzzles() throws Exception {
        PreparedStatement statement = getConnection().prepareStatement("SELECT name FROM puzzle");

        try {
            Set names = new TreeSet();
            ResultSet results = statement.executeQuery();
            try {
                while (results.next()) {
                    names.add(results.getString(1));
                }

                return names;
            } finally {
                results.close();
            }
        } finally {
            statement.close();
        }
    }

    /**
     * Returns a map of <code>String</code> names to <code>PuzzleResource</code> instances representing the
     * resources that are stored in the <code>puzzle_resource</code> and <code>download_obj</code> tables for the
     * specified puzzle.
     *
     * @param puzzleID the ID of the puzzle for which to retrieve the resources
     * @return the resources stored for the specified puzzle
     * @throws Exception if an unexpected exception occurs
     */
    protected Map readStoredResources(long puzzleID) throws Exception {
        PreparedStatement statement =
            getConnection().prepareStatement("SELECT name, media_type, content FROM puzzle_resource, download_obj "
                                             + "WHERE puzzle_resource.download_obj_id = download_obj.id AND "
                                             + "puzzle_resource.puzzle_id = ?");

        try {
            statement.setLong(1, puzzleID);

            ResultSet results = statement.executeQuery();
            try {
                Map resources = new TreeMap();
                while (results.next()) {
                    String name = results.getString(1);
                    String media = results.getString(2);
                    java.io.InputStream stream = results.getBinaryStream(3);
                    byte[] data = new byte[stream.available()];
                    stream.read(data);
                    resources.put(name, new PuzzleResourceImpl(name, media, data));
                }

                return resources;
            } finally {
                results.close();
            }
        } finally {
            statement.close();
        }
    }

    /**
     * Adds some pending winners and some non-pending winners to the database. Used to test
     * <code>getPendingWinners</code> and <code>getPendingWinnerCount</code>.
     *
     * @return an array of pairs of longs where the first value in each pair is the game ID and the second value is
     *   the player ID for a pending winner
     * @throws Exception if an unexpected exception occurs
     */
    protected long[][] createPendingWinners() throws Exception {
        Date now = new Date();

        // game 1 will have no player completed and no player won
        long game1 = addGame(now);

        // these 3 games will have a player completed but no player won
        int playerNum = 1;

        long[][] pendingWinners = new long[3][2];

        for (int i = 0; i < 3; ++i) {
            long game = addGame(now);
            addPlayer(playerNum);
            addPlayerCompleted(game, playerNum);
            pendingWinners[i][0] = game;
            pendingWinners[i][1] = playerNum++;
        }

        // these 3 games will have a player completed and won
        for (int i = 0; i < 3; ++i) {
            long game = addGame(now);
            addPlayer(playerNum);
            addPlayerCompleted(game, playerNum);
            addPlayerWon(game, playerNum++, now, 100);
        }

        // add one game 25 hours in the future (which should not count toward the total)
        long game = addGame(new Date(System.currentTimeMillis() + TWENTY_FIVE_HOURS));
        addPlayer(playerNum);
        addPlayerCompleted(game, playerNum++);

        return pendingWinners;
    }

    /**
     * Verifies that the specified pending winner has been handled.
     *
     * @param winner the pending winner to check
     * @throws Exception if a database error occurs
     * @throws junit.framework.AssertionFailedError if the pending winner does not exist or has not been handled
     */
    protected void assertHandled(PendingWinner winner) throws Exception {
        PreparedStatement statement = getConnection().prepareStatement("SELECT is_handled FROM plyr_compltd_game WHERE "
                                                                  + "game_id = ? AND player_id = ?");

        try {
            statement.setLong(1, winner.getGameId());
            statement.setLong(2, winner.getPlayerId());

            ResultSet results = statement.executeQuery();
            try {
                assertTrue("the winner does not exist", results.next());
                assertEquals("the winner should have handled", "Y", results.getString(1));
            } finally {
                results.close();
            }
        } finally {
            statement.close();
        }
    }

    /**
     * Asserts that the specified winner has an entry in the <code>plyr_won_game</code> table.
     *
     * @param winner the winner
     * @param time the time of the win
     * @throws Exception if a database error occurs
     * @throws junit.framework.AssertionFailedError if there is no entry in the table
     */
    protected void assertPlayerWon(PendingWinner winner, Date time) throws Exception {
        PreparedStatement statement =
            getConnection().prepareStatement("SELECT COUNT(*) FROM plyr_won_game WHERE game_id = ? AND player_id = ? "
                                             + "AND date = ?");

        try {
            statement.setLong(1, winner.getGameId());
            statement.setLong(2, winner.getPlayerId());
            statement.setTimestamp(3, new Timestamp(time.getTime()));

            ResultSet results = statement.executeQuery();
            try {
                if (!results.next()) {
                    fail("query failed to produce any results");
                }

                assertEquals("there should be 1 matching entry in plyr_won_game", 1, results.getLong(1));
            } finally {
                results.close();
            }
        } finally {
            statement.close();
        }
    }
}
