/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl;

import com.orpheus.administration.persistence.AdminDataDAO;
import com.orpheus.administration.persistence.AdminSummary;
import com.orpheus.administration.persistence.EntryNotFoundException;
import com.orpheus.administration.persistence.ObjectFactoryHelpers;
import com.orpheus.administration.persistence.InstantiationException;
import com.orpheus.administration.persistence.InvalidEntryException;
import com.orpheus.administration.persistence.ParameterHelpers;
import com.orpheus.administration.persistence.PendingWinner;
import com.orpheus.administration.persistence.PersistenceException;
import com.orpheus.game.server.framework.prize.PrizeCalculatorTypeSource;
import com.orpheus.game.server.framework.prize.PrizeException;
import com.orpheus.game.server.framework.prize.PrizeCalculatorType;
import com.orpheus.game.server.framework.prize.PrizeCalculator;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.GameDataLocalHome;
import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataLocal;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.config.ConfigManagerException;

import com.topcoder.util.puzzle.PuzzleData;
import com.topcoder.util.puzzle.PuzzleResource;
import com.topcoder.naming.jndiutility.JNDIUtils;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.ejb.CreateException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.rmi.RemoteException;

/**
 * <p>An implementation of <code>AdminDataDAO</code> that uses SQL Server as the persistent storage.
 *
 * <p>This class has four configuration parameters.
 *
 * <ul>
 *   <li><strong>specNamespace</strong> (required): the object factory specification namespace to use when creating
 *     the DB connection factory</li>
 *   <li><strong>factoryKey</strong> (required): the object factory key to use when creating the DB connection
 *     factory</li>
 *   <li><strong>name</strong> (optional): the name of the database connection to use (if not present, the default
 *     connection is used)</li>
 *   <li><strong>fee</strong> (required): a number between 0.0 and 1.0, inclusive, representing the percentage of
 *     the payout to be deducted for the admin fee</li>
 * </ul>
 *
 * <p><strong>Thread Safety</strong>: This class is immutable and therefore thread safe.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public class SQLServerAdminDataDAO implements AdminDataDAO {
    /**
     * The query used to determine the pre-fee payout for a specified game.
     */
/*
    private static final String PAYOUT_QUERY =
        "SELECT SUM(current_amount) FROM hosting_block, auction, hosting_slot, bid, effective_bid WHERE "
        + "hosting_block.game_id = ? AND auction.hosting_block_id = hosting_block.id AND bid.auction_id = "
        + "auction.hosting_block_id AND hosting_slot.bid_id = bid.id AND hosting_slot.bid_id = effective_bid.bid_id "
        + "AND hosting_slot.hosting_start IS NOT NULL";
*/
    private static final String PAYOUT_QUERY =
        "SELECT SUM(hosting_slot.hosting_payment) FROM hosting_block, hosting_slot WHERE "
        + "hosting_block.game_id = ? AND hosting_slot.hosting_block_id = hosting_block.id "
        + "AND hosting_slot.hosting_start IS NOT NULL";

    /**
     * The query used to retrieve the set of pending winners.
     */
    private static final String PENDING_WINNER_QUERY =
//        "SELECT t1.player_id, t1.game_id FROM plyr_compltd_game t1 WHERE t1.is_handled = 0 ORDER BY t1.sequence_number";
        "SELECT t1.player_id, t1.game_id, t1.sequence_number, count(t2.game_id) " +
        "FROM plyr_compltd_game t1 " +
        "LEFT JOIN plyr_won_game t2 ON t1.game_id = t2.game_id " +
        "WHERE t1.is_handled = 0 " +
        "GROUP BY t1.player_id, t1.game_id, t1.sequence_number " +
        "ORDER BY t1.game_id,t1.sequence_number";
/*
    private static final String PENDING_WINNER_QUERY = "SELECT t1.player_id, t1.game_id "
        + "FROM plyr_compltd_game t1 INNER JOIN "
            + "(SELECT t2.game_id AS compltd_game_id, MIN(t2.sequence_number) as min_sequence_number "
                   + "FROM plyr_compltd_game t2 LEFT JOIN plyr_won_game t3 ON (t2.game_id = t3.game_id) "
            + "WHERE (t3.player_id IS NULL AND t2.is_handled = 0) "
            + "GROUP BY t2.game_id) t4 "
        + "ON (t1.game_id = t4.compltd_game_id AND t1.sequence_number = t4.min_sequence_number) "
        + "ORDER BY t1.sequence_number ";
*/

    /**
     * The query used to determine the pending winner count.
     */
    private static final String PENDING_WINNER_COUNT_QUERY =
        "SELECT COUNT(game.id) FROM (game INNER JOIN plyr_compltd_game ON game.id = plyr_compltd_game.game_id) LEFT "
        + "OUTER JOIN plyr_won_game ON game.id = plyr_won_game.game_id WHERE game.start_date <= GETDATE() AND "
        + "plyr_won_game.player_id IS NULL AND is_handled = 0";

    /**
     * The query used to determine the active game count.
     */
    private static final String ACTIVE_GAME_COUNT_QUERY =
        "SELECT COUNT(*) FROM game LEFT OUTER JOIN plyr_won_game ON plyr_won_game.game_id = game.id WHERE "
        + "game.start_date <= GETDATE() AND plyr_won_game.player_id IS NULL";

    /**
     * The query used to determine the pending sponsor count.
     */
    private static final String PENDING_SPONSOR_COUNT_QUERY =
        "SELECT COUNT(*) FROM sponsor WHERE is_approved IS NULL";

    /**
     * The update statement used to set the domain approval.
     */
    private static final String UPDATE_DOMAIN_APPROVAL = "UPDATE domain SET is_approved = ? WHERE id = ?";

    /**
     * The update statement used to set the domain approval.
     */
    private static final String UPDATE_IMAGE_APPROVAL = "UPDATE image SET is_approved = ? WHERE id = ?";

    /**
     * The update statement used to insert a player won game.
     */
    private static final String INSERT_PLAYER_WON_GAME =
        "INSERT INTO plyr_won_game (game_id, player_id, date, payout) VALUES (?, ?, ?, ?)";

    /**
     * The update statement used to set the winner handled.
     */
    private static final String UPDATE_WINNER_HANDLED =
        "SELECT is_handled FROM plyr_compltd_game WHERE game_id = ? AND player_id = ?";

    /**
     * The statement used check for an existing winner.
     */
    private static final String CHECK_EXISTING_WINNER
        = "SELECT COUNT(*) FROM plyr_won_game WHERE game_id = ? AND player_id = ?";

    /**
     * The statement used to insert a puzzle resource.
     */
    private static final String INSERT_PUZZLE_RESOURCE =
        "INSERT INTO puzzle_resource (puzzle_id, name, download_obj_id) VALUES (?, ?, ?)";

    /**
     * The statement used to call <code>InsertDownloadObject</code>.
     */
    private static final String INSERT_DOWNLOAD_OBJECT = "{call InsertDownloadObject(?, ?, ?)}";

    /**
     * The statement used to insert a puzzle attribute.
     */
    private static final String INSERT_PUZZLE_ATTRIBUTE =
        "INSERT INTO puzzle_attribute (puzzle_id, name, value) VALUES (?, ?, ?)";

    /**
     * The statement used to insert a puzzle.
     */
    private static final String INSERT_PUZZLE = "{call InsertPuzzle (?,?)}";

    /**
     * The insert statement used to insert a new ball color.
     */
    private static final String INSERT_BALL_COLOR = "INSERT INTO ball_color (name, download_obj_id) VALUES (?, ?)";

    /**
     * The database connection factory used to obtain connections for performing the requested operations. This
     * member is initialized in the constructor, is immutable, and will never be <code>null</code>.
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * The name to use when retrieving connections from the {@link #connectionFactory connection factory}. If
     * <code>null</code>, then the default connection will be retrieved.
     */
    private final String connectionName;

    /**
     * A number between 0 and 1 (inclusive) representing the percentage of winnings deducting as the admin
     * fee. This member is initialized in the constructor and is immutable.
     */
    private final float adminFee;

    /**
     * <p>A <code>PrizeCalculatorTypeSource</code> to be used for obtaining the prize calculation types based on the
     * game settings.</p>
     */
    private final PrizeCalculatorTypeSource prizeTypeSource;

    /**
     * <p>A <code>String</code> providing the name referencing the <code>Game Data EJB</code> in <code>JNDI</code>
     * context.</p>
     */
    private final String gameDataEJBName;

    /**
     * <p>A <code>boolean</code> indicating whether remote or local interface must be usef for communicating to <code>
     * Game Data EJB</code>.</p> 
     */
    private final boolean useRemote;

    /**
     * <p>A <code>String</code> providing the name referencing the <code>JNDI</code> context for locating the <code>
     * Game Data EJB</code> .</p>
     */
    private final String jndiContextName;

    /**
     * Creates a new <code>SQLServerAdminDAO</code> configured using the specified namespace. See the class
     * documentation for a description of the configuration parameters.
     *
     * @param namespace the configuration namespace
     * @throws IllegalArgumentException if <code>namespace</code> is <code>null</code> or an empty string
     * @throws InstantiationException if an error occurs while configuring the instance
     */
    public SQLServerAdminDataDAO(String namespace) throws InstantiationException {
        ParameterHelpers.checkString(namespace, "SQL server admin DAO namespace");

        connectionFactory = (DBConnectionFactory)
            ObjectFactoryHelpers.instantiateObject(namespace, "specNamespace", "factoryKey",
                                                   DBConnectionFactory.class);
        this.prizeTypeSource
            = (PrizeCalculatorTypeSource) ObjectFactoryHelpers.instantiateObject(namespace, "specNamespace",
                                                                                 "prizeTypeSourceKey",
                                                                                 PrizeCalculatorTypeSource.class);
        ConfigManager manager = ConfigManager.getInstance();
        try {
            connectionName = manager.getString(namespace, "name");
            this.gameDataEJBName = manager.getString(namespace, "gameDataEJB.name");
            this.jndiContextName = manager.getString(namespace, "gameDataEJB.context");
            this.useRemote = Boolean.valueOf(manager.getString(namespace, "gameDataEJB.remote")).booleanValue();
            String fee = ObjectFactoryHelpers.getProperty(namespace, "adminFee");
            try {
                this.adminFee = Float.parseFloat(fee);
            } catch (NumberFormatException ex) {
                throw new InstantiationException("invalid admin fee value '" + fee + "'");
            }
            if (adminFee < 0.0 || adminFee > 1.0) {
                throw new InstantiationException("admin fee '" + fee + "' not in the required range [0.0,1.0]");
            }
        } catch (UnknownNamespaceException ex) {
            throw new InstantiationException("unknown namespace '" + namespace + "'");
        }
    }

    /**
     * Retrieves the administrative data summary from the persistent store.
     *
     * @return the administrative data summary
     * @throws PersistenceException if a persistence error occurs
     */
    public AdminSummary getAdminSummary() throws PersistenceException {
        try {
            Connection connection = getConnection();
            PreparedStatement statement = null;
            ResultSet results = null;

            try {
                // query the pending winners
                statement = connection.prepareStatement(PENDING_WINNER_COUNT_QUERY);
                results = statement.executeQuery();
                if (!results.next()) {
                    throw new PersistenceException("query did not return any results");
                }

                int pendingWinnerCount = results.getInt(1);
                results.close();
                statement.close();

                // query the pending sponsors
                statement = connection.prepareStatement(PENDING_SPONSOR_COUNT_QUERY);
                results = statement.executeQuery();
                if (!results.next()) {
                    throw new PersistenceException("query did not return any results");
                }
                int pendingSponsorCount = results.getInt(1);
                results.close();
                statement.close();

                // query the active games
                statement = connection.prepareStatement(ACTIVE_GAME_COUNT_QUERY);
                results = statement.executeQuery();
                if (!results.next()) {
                    throw new PersistenceException("query did not return any results");
                }
                int activeGameCount = results.getInt(1);

                return new AdminSummaryImpl(pendingSponsorCount, pendingWinnerCount, activeGameCount);
            } finally {
                if (results != null) {
                    results.close();
                }

                if (statement != null) {
                    statement.close();
                }

                connection.close();
            }
        } catch (SQLException ex) {
            throw new PersistenceException("SQL error computing pending winner count: " + ex.getMessage(), ex);
        }
    }

    /**
     * Sets the approval flag for the specified domain.
     *
     * @param domainId the domain ID
     * @param approved the approval flag
     * @throws EntryNotFoundException if no such domain exists
     * @throws PersistenceException if a persistence error occurs
     */
    public void setDomainApproval(long domainId, boolean approved) throws PersistenceException {
        try {
            Connection connection = getConnection();

            try {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_DOMAIN_APPROVAL);
                try {
                    stmt.setInt(1, approved ? 1 : 0);
                    stmt.setLong(2, domainId);

                    if (stmt.executeUpdate() < 1) {
                        throw new EntryNotFoundException("no such domain '" + domainId + "'", new Long(domainId));
                    }
                } finally {
                    stmt.close();
                }
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            throw new PersistenceException("SQL error setting domain approval: " + ex.getMessage(), ex);
        }
    }

    /**
     * Sets the approval flag for the specified image.
     *
     * @param imageId the image ID
     * @param approved the approval flag
     * @throws EntryNotFoundException if no such image exists
     * @throws PersistenceException if a persistence error occurs
     */
    public void setImageApproval(long imageId, boolean approved) throws PersistenceException {
        try {
            Connection connection = getConnection();

            try {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_IMAGE_APPROVAL);
                try {
                    stmt.setInt(1, approved ? 1 : 0);
                    stmt.setLong(2, imageId);

                    if (stmt.executeUpdate() < 1) {
                        throw new EntryNotFoundException("no such image '" + imageId + "'", new Long(imageId));
                    }
                } finally {
                    stmt.close();
                }
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            throw new PersistenceException("SQL error setting image approval: " + ex.getMessage(), ex);
        }
    }

    /**
     * Records the puzzle data, including all attributes and resources, in the persistent storage.
     *
     * @param puzzles an array of puzzles to store
     * @return unique IDs for each puzzle in the persistent storage (elements in the returned array correspond to
     *   similarly-positioned elements in the <code>puzzles</code> argument)
     * @throws IllegalArgumentException if <code>puzzles</code> is <code>null</code>, empty, or contains any
     *   <code>null</code> elements
     * @throws PersistenceException if an error occurs while accessing the persistent storage
     */
    public long[] storePuzzles(PuzzleData[] puzzles) throws PersistenceException {
        ParameterHelpers.checkArray(puzzles, "puzzle");

        Connection connection = getConnection();

        try {
            long[] ret = new long[puzzles.length];
            for (int idx = 0; idx < puzzles.length; ++idx) {
                PuzzleData puzzle = puzzles[idx];
                long puzzleID = storePuzzle(connection, puzzle);
                // store each puzzle attribute
                for (Iterator it = puzzle.getAllAttributes().entrySet().iterator(); it.hasNext();) {
                    Map.Entry entry = (Map.Entry) it.next();
                    storePuzzleAttribute(connection, puzzleID, (String) entry.getKey(), entry.getValue().toString());
                }
                // store each puzzle resource
                for (Iterator it = puzzle.getAllResources().values().iterator(); it.hasNext();) {
                    storePuzzleResource(connection, puzzleID, (PuzzleResource) it.next());
                }
                ret[idx] = puzzleID;
            }

            return ret;
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new PersistenceException("failed to close connection: " + ex.getMessage(), ex);
            }
        }
    }

    /**
     * Retrieves the current set of pending winners, in game completion order.
     *
     * @return the current set of pending winners
     * @throws PersistenceException if a persistence error occurs
     */
    public PendingWinner[] getPendingWinners() throws PersistenceException {
        Connection connection = getConnection();

        try {
            try {
                PreparedStatement statement = connection.prepareStatement(PENDING_WINNER_QUERY);
                try {
                    ResultSet results = statement.executeQuery();

                    try {
                        List ret = new ArrayList();
                        long currentGameId = -1;
                        int placement = 0;
                        Map gamesCache = new HashMap();
                        while (results.next()) {
                            long playerId = results.getLong(1);
                            Long gameID = new Long(results.getLong(2));
                            if (gameID.longValue() != currentGameId) {
                                placement = results.getInt(4);
                                currentGameId = gameID.longValue();
                            }
                            if (!gamesCache.containsKey(gameID)) {
                                gamesCache.put(gameID, getGame(gameID.longValue()));
                            }
                            Game game = (Game) gamesCache.get(gameID);
                            placement++;
                            PrizeCalculatorType prizeType
                                = this.prizeTypeSource.getPrizeCalculatorType(game.getPrizeCalculationType());
                            PrizeCalculator prizeCalc = prizeType.getCalculator();
                            double payout = prizeCalc.calculatePrizeAmount(game, playerId, placement);
                            ret.add(new PendingWinnerImpl(playerId, gameID.longValue(), (int) payout, placement));
                        }

                        return (PendingWinner[]) ret.toArray(new PendingWinner[ret.size()]);
                    } finally {
                        results.close();
                    }
                } finally {
                    statement.close();
                }
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            throw new PersistenceException("error querying pending winners: " + ex.getMessage(), ex);
        } catch (PrizeException ex) {
            throw new PersistenceException("error calculating prize payout: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new PersistenceException("error while obtaining game detailst: " + ex.getMessage(), ex);
        }
    }

    /**
     * Approves the specified pending winner as the winner of the game in question.
     *
     * @param winner the pending winner
     * @param date the date of the win
     * @throws PersistenceException if a persistence error occurs
     * @throws EntryNotFoundException if the specified game ID/player ID combination does not exist
     * @throws InvalidEntryException if the game already has a winner, or the pending winner has already been handled
     * @throws IllegalArgumentException if either argument is <code>null</code>
     */
    public void approveWinner(PendingWinner winner, Date date) throws PersistenceException {
        if (winner == null) {
            throw new IllegalArgumentException("pending winner must not be null");
        }

        if (date == null) {
            throw new IllegalArgumentException("date must not be null");
        }

        Connection connection = getConnection();

        try {
            try {
                long gameID = winner.getGameId();
                long playerID = winner.getPlayerId();
                int prize = winner.getPayout();
                // According to latest requirements there may be several winners per game so the check is checking
                // against player ID also
                checkForExistingWinner(connection, gameID, playerID);
                setWinnerHandled(connection, winner, gameID, playerID);
//                insertPlayerWonGame(connection, gameID, playerID, date, calculatePayout(gameID));
                insertPlayerWonGame(connection, gameID, playerID, date, prize);
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            throw new PersistenceException("error approving winner: " + ex.getMessage(), ex);
        }
    }

    /**
     * Rejects the specified pending winner's win.
     *
     * @param winner the pending winner to reject
     * @throws IllegalArgumentException if <code>winner</code> is <code>null</code>
     * @throws PersistenceException if a persistence error occurs
     * @throws EntryNotFoundException if the specified game ID/player ID combination does not exist
     * @throws InvalidEntryException if the pending winner has already been handled
     */
    public void rejectWinner(PendingWinner winner) throws PersistenceException {
        if (winner == null) {
            throw new IllegalArgumentException("pending winner must not be null");
        }

        Connection connection = getConnection();

        try {
            try {
                setWinnerHandled(getConnection(), winner, winner.getGameId(), winner.getPlayerId());
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            throw new PersistenceException("error rejecting winner: " + ex.getMessage(), ex);
        }
    }

    /**
     * Creates new Ball color with specified name and associated with the specified image providing the Ball color icon.
     *
     * @param colorName a <code>String</code> providing the name for the new Ball color.
     * @param imageId a <code>long</code> providing the ID of an image associated with new Ball color.
     * @return a <code>long</code> providing the unique ID for the create Ball color.
     * @throws PersistenceException if an error occurs while accessing the persistent storage.
     * @throws IllegalArgumentException if specified <code>colorName</code> is <code>null</code> or empty.
     */
    public long createBallColor(String colorName, long imageId) throws PersistenceException {
        if ((colorName == null) || (colorName.trim().length() == 0)) {
            throw new IllegalArgumentException("The parameter [colorName] is not valid. [" + colorName + "]");
        }

        Connection connection = getConnection();

        try {
            PreparedStatement statement
                = connection.prepareStatement(INSERT_BALL_COLOR, Statement.RETURN_GENERATED_KEYS);
            try {
                statement.setString(1, colorName);
                statement.setLong(2, imageId);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted != 1) {
                    throw new PersistenceException("Invalid number of rows inserted when creating ball color ["
                                                   + colorName + "]: " + rowsInserted);
                }
                ResultSet keys = statement.getGeneratedKeys();
                if (keys.next()) {
                    return keys.getLong(1);
                } else {
                    throw new PersistenceException("The ID for created ball color [" + colorName + "] is not returned");
                }
            } finally {
                statement.close();
                connection.close();
            }
        } catch (SQLException ex) {
            throw new PersistenceException("error creating ball color [" + colorName + "]: " + ex.getMessage(), ex);
        }
    }

    /**
     * Calculates the winner payout for the specified game.
     *
     * @param gameId the ID of the game for which to calculate the payout
     * @return the payout for the specified game
     * @throws PersistenceException if a persistence exception occurs
     */
    private int calculatePayout(long gameId) throws PersistenceException {
        // note that this method was initially private in the design, but I made it package-private to allow it
        // to be unit tested
        Connection connection = getConnection();

        try {
            try {
                PreparedStatement statement = connection.prepareStatement(PAYOUT_QUERY);

                try {
                    statement.setLong(1, gameId);

                    ResultSet results = statement.executeQuery();
                    try {
                        if (!results.next()) {
                            throw new PersistenceException("payout query did not return any results");
                        }

                        // deduct the admin fee from the bid amount
                        return (int) ((1 - adminFee) * results.getLong(1));
                    } finally {
                        results.close();
                    }
                } finally {
                    statement.close();
                }
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            throw new PersistenceException("error calculating payout: " + ex.getMessage());
        }
    }

    /**
     * Retrieves the named database connection from the DB connection factory, or the default connection if the
     * connection name is <code>null</code>.
     *
     * @return the named database connection from the DB connection factory, or the default connection if the
     *   connection name is <code>null</code>
     * @throws PersistenceException if an error occurs while retrieving the connection
     */
    private Connection getConnection() throws PersistenceException {
        try {
            if (connectionName == null) {
                return connectionFactory.createConnection();
            } else {
                return connectionFactory.createConnection(connectionName);
            }
        } catch (DBConnectionException ex) {
            throw new PersistenceException("error creating database connection: " + ex.getMessage(), ex);
        }
    }

    /**
     * Inserts an entry into the <code>plyr_won_game</code> with the specified attributes.
     *
     * @param connection the database connection
     * @param gameID the game ID
     * @param playerID the player ID
     * @param date the date
     * @param payout the payout
     * @throws SQLException if a database error occurs
     * @throws PersistenceException if the update query failed to insert a row
     */
    private static void insertPlayerWonGame(Connection connection, long gameID, long playerID, Date date, long payout)
        throws SQLException, PersistenceException {
        PreparedStatement statement = connection.prepareStatement(INSERT_PLAYER_WON_GAME);

        try {
            int idx = 1;
            statement.setLong(idx++, gameID);
            statement.setLong(idx++, playerID);
            statement.setTimestamp(idx++, new java.sql.Timestamp(date.getTime()));
            statement.setLong(idx++, payout);

            if (statement.executeUpdate() != 1) {
                throw new PersistenceException("failed to insert player into plyr_won_game");
            }
        } finally {
            statement.close();
        }
    }

    /**
     * Sets the <code>is_handled</code> flag in the <code>plyr_compltd_game</code> table to <code>true</code> for
     * the specified player in the specified game.
     *
     * @param connection the database connection
     * @param winner the pending winner
     * @param gameID the game ID
     * @param playerID the player ID
     * @throws SQLException if a database error occurs
     * @throws InvalidEntryException if the <code>is_handled</code> field is already set for the specified player
     * @throws EntryNotFoundException if no entry is found for the specified game ID/player ID combination
     */
    private static void setWinnerHandled(Connection connection, PendingWinner winner, long gameID, long playerID)
        throws SQLException, InvalidEntryException, EntryNotFoundException {
        PreparedStatement statement =
            connection.prepareStatement(UPDATE_WINNER_HANDLED, ResultSet.TYPE_FORWARD_ONLY,
                                        ResultSet.CONCUR_UPDATABLE);
        try {
            statement.setLong(1, gameID);
            statement.setLong(2, playerID);

            ResultSet results = statement.executeQuery();
            try {
                if (!results.next()) {
                    throw new EntryNotFoundException("pending winner does not exist", winner);
                }

                if (results.getInt(1) == 1) {
                    throw new InvalidEntryException("winner is already handled", winner);
                }

                results.updateInt(1, 1);
                results.updateRow();
            } finally {
                results.close();
            }
        } finally {
            statement.close();
        }
    }

    /**
     * Verifies that there is not already the specified winner for the specified game ID.
     *
     * @param connection the database connection
     * @param gameID the game ID
     * @param playerID the player ID
     * @throws SQLException if a database error occurs
     * @throws PersistenceException if the query does not return any results
     * @throws InvalidEntryException if a winner already exists for the specified game ID
     */
    private static void checkForExistingWinner(Connection connection, long gameID, long playerID)
        throws SQLException, PersistenceException {
        PreparedStatement statement = connection.prepareStatement(CHECK_EXISTING_WINNER);

        try {
            statement.setLong(1, gameID);
            statement.setLong(2, playerID);
            ResultSet results = statement.executeQuery();
            try {
                if (!results.next()) {
                    throw new PersistenceException("plyr_won_game query did not return any results");
                }

                if (results.getLong(1) > 0) {
                    throw new InvalidEntryException("game '" + gameID + "' already has a winner '" + playerID + "'",
                                                    new Long(gameID));
                }
            } finally {
                results.close();
            }
        } finally {
            statement.close();
        }
    }

    /**
     * Stores the specified puzzle resource in the <code>puzzle_resource</code> table.
     *
     * @param connection the database connection
     * @param puzzleID the puzzle ID
     * @param resource the puzzle resource
     * @throws PersistenceException if a database error occurs
     */
    private static void storePuzzleResource(Connection connection, long puzzleID, PuzzleResource resource)
        throws PersistenceException {
        long downloadID = storeDownloadObject(connection, resource.getMediaType(), resource.getData());

        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_PUZZLE_RESOURCE);
            try {
                int idx = 1;
                statement.setLong(idx++, puzzleID);
                statement.setString(idx++, resource.getName());
                statement.setLong(idx++, downloadID);
                if (statement.executeUpdate() != 1) {
                    throw new PersistenceException("failed to insert puzzle resource");
                }
            } finally {
                statement.close();
            }
        } catch (SQLException ex) {
            throw new PersistenceException("error inserting puzzle resource: " + ex.getMessage(), ex);
        }
    }

    /**
     * Adds a new entry to the <code>download_obj</code> table.
     *
     * @param connection the database connection
     * @param mediaType the media type
     * @param data the binary data
     * @return the ID of the newly-inserted download object
     * @throws PersistenceException if a database error occurs
     */
    private static long storeDownloadObject(Connection connection, String mediaType, byte[] data)
        throws PersistenceException {
        try {
            CallableStatement statement = connection.prepareCall(INSERT_DOWNLOAD_OBJECT);

            try {
                int idx = 1;
                statement.setString(idx++, mediaType);
                statement.setBytes(idx++, data);
                statement.registerOutParameter(idx, Types.BIGINT);
                statement.execute();
                return statement.getLong(idx);
            } finally {
                statement.close();
            }
        } catch (SQLException ex) {
            throw new PersistenceException("error inserting download object: " + ex.getMessage(), ex);
        }
    }

    /**
     * Adds a new entry to the <code>puzzle_attribute</code> table.
     *
     * @param connection the database connection
     * @param puzzleID the puzzle ID
     * @param name the attribute name
     * @param value the attribute value
     * @throws PersistenceException if a database error occurs
     */
    private static void storePuzzleAttribute(Connection connection, long puzzleID, String name, String value)
        throws PersistenceException {
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_PUZZLE_ATTRIBUTE);
            try {
                int idx = 1;
                statement.setLong(idx++, puzzleID);
                statement.setString(idx++, name);
                statement.setString(idx++, value);

                if (statement.executeUpdate() != 1) {
                    throw new PersistenceException("failed to insert puzzle attribute");
                }
            } finally {
                statement.close();
            }
        } catch (SQLException ex) {
            throw new PersistenceException("error storing puzzle attribute: " + ex.getMessage(), ex);
        }
    }

    /**
     * Stores the specified puzzle in the <code>puzzle</code> table.
     *
     * @param connection the database connection
     * @param data the puzzle to store
     * @return the ID of the newly-inserted puzzle
     * @throws PersistenceException if a database error occurs
     */
    private static long storePuzzle(Connection connection, PuzzleData data) throws PersistenceException {
        try {
            CallableStatement statement = connection.prepareCall(INSERT_PUZZLE);

            try {
                statement.setString(1, data.getName());
                statement.registerOutParameter(2, Types.BIGINT);
                statement.execute();
                return statement.getLong(2);
            } finally {
                statement.close();
            }
        } catch (SQLException ex) {
            throw new PersistenceException("error storing puzzle name: " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Gets the details for the game referenced by the specified ID.</p>
     *
     * @param gameId a <code>long</code> providing the ID of a game.
     * @return a <code>Game</code> referenced by the specified ID.
     * @throws NamingException if an unexpected error occurs.
     * @throws ConfigManagerException if an unexpected error occurs.
     * @throws RemoteException if an unexpected error occurs.
     * @throws CreateException if an unexpected error occurs.
     * @throws com.orpheus.game.persistence.PersistenceException if an unexpected error occurs.
     */
    private Game getGame(long gameId) throws NamingException, ConfigManagerException, RemoteException, CreateException,
                                             com.orpheus.game.persistence.PersistenceException {
        Context context = JNDIUtils.getContext(this.jndiContextName);
        Object obj = JNDIUtils.getObject(context, this.gameDataEJBName);
        if (this.useRemote) {
            GameDataHome home = (GameDataHome) PortableRemoteObject.narrow(obj, GameDataHome.class);
            GameData gameDataEJB = home.create();
            return gameDataEJB.getGame(gameId);
        } else {
            GameDataLocalHome home = (GameDataLocalHome) obj;
            GameDataLocal gameDataEJB = home.create();
            return gameDataEJB.getGame(gameId);
        }
    }
}
