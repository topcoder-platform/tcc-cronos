/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.dao;

import com.orpheus.game.persistence.BallColor;
import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.DuplicateEntryException;
import com.orpheus.game.persistence.EntryNotFoundException;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.GameDataDAO;
import com.orpheus.game.persistence.Helper;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.ImageInfo;
import com.orpheus.game.persistence.InstantiationException;
import com.orpheus.game.persistence.InvalidEntryException;
import com.orpheus.game.persistence.PersistenceException;
import com.orpheus.game.persistence.SlotCompletion;
import com.orpheus.game.persistence.entities.BallColorImpl;
import com.orpheus.game.persistence.entities.DomainImpl;
import com.orpheus.game.persistence.entities.DomainTargetImpl;
import com.orpheus.game.persistence.entities.DownloadDataImpl;
import com.orpheus.game.persistence.entities.GameImpl;
import com.orpheus.game.persistence.entities.HostingBlockImpl;
import com.orpheus.game.persistence.entities.HostingSlotImpl;
import com.orpheus.game.persistence.entities.ImageInfoImpl;
import com.orpheus.game.persistence.entities.SlotCompletionImpl;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.randomstringimg.RandomStringImage;

import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.puzzle.PuzzleData;
import com.topcoder.util.puzzle.baseimpl.GeneralPuzzleData;
import com.topcoder.util.puzzle.baseimpl.GeneralPuzzleResource;

import com.topcoder.web.frontcontroller.results.DownloadData;

import java.io.ByteArrayOutputStream;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedHashMap;
import java.util.Date;


/**
 * <p>
 * Implements GameDataDAO. Works with SQL Server database and most tables defined in RS 1.2.3. Most of the beans used in the
 * methods have a mostly 1-1 correspondence with the tables. It uses ConfigManager and Object Factory to configure the
 * connection factory, random string image, and the length of key text to generate as well as the media type. The last three are
 * used in the recording of the host slot completion It is expected that the connection factory will use a JNDI connection
 * provider so the Datasource is obtained from the application server.
 * </p>
 * 
 * <p>
 * This class has five configuration parameters.
 * 
 * <ul>
 * <li>
 * <strong>specNamespace</strong> (required): the object factory specification namespace to use when creating the DB connection
 * factory and RandomStringImage
 * </li>
 * <li>
 * <strong>factoryKey</strong> (required): the object factory key to use when creating the DB connection factory
 * </li>
 * <li>
 * <strong>name</strong> (optional): the name of the database connection to use (if not present, the default connection is used)
 * </li>
 * <li>
 * <strong>randomStringImageKey</strong> (required): Key for the RandomStringImage to pass to ObjectFactory.
 * </li>
 * <li>
 * <strong>keyLength</strong> (required): Length of text to generate. A positive number.
 * </li>
 * <li>
 * <strong>mediaType</strong> (required): Non null/empty media type. Such as "text/plain".
 * </li>
 * </ul>
 * </p>
 * 
 * <p>
 * <b>Thread Safety:</b>This class is mutable and thread-safe.
 * </p>
 */
public class SQLServerGameDataDAO implements GameDataDAO {
    /*
     * NOTE: The application in general, and this class in specific, distinguish
     * between games' _scheduled_ starts and their _actual_ starts.  A game is
     * scheduled to start on its recorded start date (game.start_date), but does
     * not actually start until one of its slots starts hosting
     * (hosting_slot.hosting_start IS NOT NULL).
     *
     * The application has many occasions on which it needs to get the hosting
     * slots associated with a game, which requires a minimum of three JOINs.  It
     * might be useful to create a view in the DB that provides this function,
     * both to reduce the complexity of the queries used by this class and to
     * make them more efficient.
     */
    /**
     * A String constant containing an SQL prepared statement for retrieving the IDs of the games in which a specified domain is
     * a current or past Ball host in a slot that the specified player has not yet completed
     */
/*
    private static final String SQL_PS_SELECT_PLAYER_GAMES_BY_DOMAIN = "SELECT DISTINCT t1.game_id AS gameId " +
        "FROM ((hosting_block t1 LEFT JOIN plyr_won_game t2 ON (t1.game_id = t2.game_id)) " +
        "LEFT JOIN plyr_compltd_game t3 ON (t1.game_id = t3.game_id AND t3.player_id = ?)) " +
        "INNER JOIN ((hosting_slot t4 LEFT JOIN plyr_compltd_slot t5 ON (t4.id = t5.hosting_slot_id AND t5.player_id = ?)) " +
        "INNER JOIN bid t6 ON (t4.bid_id = t6.id) " + "INNER JOIN [image] t7 ON (t6.image_id = t7.id) " +
        "INNER JOIN domain t8 ON (t7.domain_id = t8.id AND t8.base_url = ?)) " + "ON (t1.id = t6.auction_id) " +
        "WHERE (t2.player_id IS NULL AND t3.sequence_number IS NULL " +
        "AND t5.timestamp IS NULL AND t4.hosting_start IS NOT NULL AND t4.is_deleted=0)";
*/
    private static final String SQL_PS_SELECT_PLAYER_GAMES_BY_DOMAIN
        = "SELECT DISTINCT t1.game_id AS gameId " +
          "FROM (hosting_block t1 " +
                "INNER JOIN game g ON t1.game_id = g.id " +
                "LEFT JOIN plyr_compltd_game t3 ON (t1.game_id = t3.game_id AND t3.player_id = ?)) " +
          "INNER JOIN (hosting_slot t4 " +
                       "LEFT JOIN plyr_compltd_slot t5 ON (t4.id = t5.hosting_slot_id AND t5.player_id = ?) " +
                       "INNER JOIN [image] t7 ON (t4.image_id = t7.id) " +
                       "INNER JOIN domain t8 ON (t7.domain_id = t8.id AND t8.base_url = ?)) " +
          "ON (t1.id = t4.hosting_block_id) " +
          "WHERE (g.start_Date <= GETDATE() AND (g.end_date IS NULL OR g.end_date > GETDATE()) " +
          "AND t3.sequence_number IS NULL " +
          "AND t5.timestamp IS NULL " +
          "AND t4.hosting_start IS NOT NULL " +
          "AND t4.is_deleted=0)";

    /** Constant represents the sql clause to get complete slot for a game. */
    private static final String SQL_SELECT_COMPLETE_SLOT_IN_GAME =
        "SELECT t4.hosting_slot_id AS id, MIN(t1.sequence_number) s1, MIN(t3.sequence_number) s2 " +
        "FROM plyr_compltd_slot t4 " +
        "INNER JOIN hosting_slot t3 ON (t4.hosting_slot_id = t3.id AND t3.is_deleted=0) " +
        "INNER JOIN hosting_block t1 ON (t3.hosting_block_id = t1.id) " +
        "WHERE t1.game_id = ? " +
        "GROUP BY t4.hosting_slot_id ORDER BY s1, s2";

    /** Constant represents the sql clause get all complete slot with game and slot id. */
/*
    private static final String SQL_SELECT_COMPLETE_SLOT =
        "SELECT t5.* FROM hosting_block t2 INNER JOIN bid t3 ON (t2.game_id = ? AND t2.id = t3.auction_id) " +
        "INNER JOIN hosting_slot t4 ON (t3.id = t4.bid_id AND t4.id = ? AND t4.is_deleted=0) " +
        "INNER JOIN plyr_compltd_slot t5 ON (t4.id = t5.hosting_slot_id) " + "ORDER BY t5.timestamp";
*/
    private static final String SQL_SELECT_COMPLETE_SLOT =
        "SELECT t5.* " +
        "FROM hosting_block t2 " +
        "INNER JOIN hosting_slot t4 ON (t2.game_id = ? AND t2.id = t4.hosting_block_id AND t4.id = ? AND t4.is_deleted=0) " +
        "INNER JOIN plyr_compltd_slot t5 ON (t4.id = t5.hosting_slot_id) " +
        "ORDER BY t5.timestamp";

    /** Constant represents the field name. */
    private static final String FIELD_TIMESTAMP = "timestamp";

    /** Constant represents the field name. */
    private static final String FIELD_PLAYER_ID = "player_id";

    /** Constant represents the field name. */
    private static final String FIELD_HOSTING_SLOT_ID = "hosting_slot_id";

    /** Constant represents the sql clause to check if the slot id exists with the game id. */
/*
    private static final String SQL_SELECT_CHECK_GAME_ID_SLOT_ID = "SELECT t4.id FROM " +
        "hosting_block t2 INNER JOIN bid t3 ON (t2.id = t3.auction_id AND t2.game_id = ?) " +
        "INNER JOIN hosting_slot t4 ON (t3.id = t4.bid_id AND t4.id = ? AND t4.is_deleted=0) ";
*/
    private static final String SQL_SELECT_CHECK_GAME_ID_SLOT_ID
        = "SELECT t3.id " +
          "FROM hosting_block t2 " +
          "INNER JOIN hosting_slot t3 ON (t2.id = t3.hosting_block_id AND t2.game_id = ?) " +
          "WHERE t3.id = ? AND t3.is_deleted=0";

    /**
     * Constant represents the sql clause to get unstarted games. Games are judged to have not been started if none of their
     * slots have been started
     */
/*
    private static final String SQL_SELECT_NOT_START_GAME = "SELECT t2.game_id AS id, COUNT(t4.hosting_start) " +
        "FROM hosting_block t2 " + "INNER JOIN bid t3 ON (t2.id = t3.auction_id) " +
        "INNER JOIN hosting_slot t4 ON (t3.id = t4.bid_id AND t4.is_deleted=0) " +
       	"GROUP BY t2.game_id " + "HAVING (COUNT(t4.hosting_start) = 0)";
*/
    private static final String SQL_SELECT_NOT_START_GAME
        = "SELECT t2.game_id AS id, COUNT(t4.hosting_start) " +
          "FROM hosting_block t2 " +
          "INNER JOIN hosting_slot t4 ON (t2.id = t4.hosting_block_id AND t4.is_deleted=0) " +
          "GROUP BY t2.game_id " +
          "HAVING (COUNT(t4.hosting_start) = 0)";

    /** Constant represents the sql clause to get started games that have no winner. */
/*
    private static final String SQL_SELECT_START_NOT_END_GAME = "SELECT DISTINCT t2.game_id AS id " + "FROM plyr_won_game t1 " +
        "RIGHT JOIN hosting_block t2 ON (t1.game_id = t2.game_id) " + "INNER JOIN bid t3 ON (t2.id = t3.auction_id) " +
        "INNER JOIN hosting_slot t4 ON (t3.id = t4.bid_id) " +
       	"WHERE (t4.hosting_start IS NOT NULL AND t1.player_id IS NULL AND t4.is_deleted=0)";
*/
    private static final String SQL_SELECT_START_NOT_END_GAME
        = "SELECT t1.id AS id FROM game t1 " +
          "WHERE t1.start_date <= GETDATE() AND (t1.end_date IS NULL OR t1.end_date > GETDATE())";

    /**
     * <p>
     * Constant represents the sql clause get game has winner and start.
     * </p>
     * 
     * <p>
     * Note: in practice, it should be sufficient (and less work) to skip the test for games that have been started, because it
     * is inconsistent for a game to have ended but not started. The test for ending only is covered separately, however, so
     * this version tests both start and end.
     * </p>
     */
/*
    private static final String SQL_SELECT_START_END_GAME
        = "SELECT DISTINCT t1.game_id AS id " +
          "FROM plyr_won_game t1 " +
          "INNER JOIN hosting_block t2 ON (t1.game_id = t2.game_id) " +
          "INNER JOIN bid t3 ON (t2.id = t3.auction_id) " +
          "INNER JOIN hosting_slot t4 ON (t3.id = t4.bid_id) " +
          "WHERE (t4.hosting_start IS NOT NULL AND t4.is_deleted=0)";
*/
    private static final String SQL_SELECT_START_END_GAME
        = "SELECT t1.id AS id FROM game t1 WHERE (t1.start_date <= GETDATE() AND t1.end_date <= GETDATE())";

    /** Constant represents the sql clause to get games that have started. */
/*
    private static final String SQL_SELECT_START_GAME = "SELECT DISTINCT t2.game_id AS id " + "FROM hosting_block t2 " +
        "INNER JOIN bid t3 ON (t2.id = t3.auction_id) " + "INNER JOIN hosting_slot t4 ON (t3.id = t4.bid_id) " +
        "WHERE (t4.hosting_start IS NOT NULL AND t4.is_deleted=0)";
*/
    private static final String SQL_SELECT_START_GAME
        = "SELECT t1.id AS id FROM game t1 WHERE t1.start_date <= GETDATE()";

    /** Constant represents the sql clause to get games that have no winner. */
/*
    private static final String SQL_SELECT_NOT_END_GAME
        = "SELECT DISTINCT t1.id FROM game t1 " +
          "LEFT JOIN plyr_won_game t2 ON (t2.game_id = t1.id) " +
          "WHERE (t2.player_id IS NULL)";
*/
    private static final String SQL_SELECT_NOT_END_GAME
        = "SELECT t1.id FROM game t1 WHERE t1.end_date IS NULL OR t1.end_date > GETDATE()";

    /** Constant represents the sql clause to get games that have a winner. */
//    private static final String SQL_SELECT_END_GAME = "SELECT DISTINCT game_id AS id FROM plyr_won_game";
    private static final String SQL_SELECT_END_GAME
        = "SELECT g.id AS id FROM game g WHERE g.end_date <= GETDATE()";

    /** Constant represents the sql clause to get all the game id. */
    private static final String SQL_SELECT_GAME = "SELECT id FROM game";

    /** Constant represents the sql clause game has winner. */
//    private static final String SQL_SELECT_PLYR_WON_GAME = "SELECT t1.game_id FROM plyr_won_game t1";

    /** Constant represents the field name. */
    private static final String FIELD_GAME_ID = "gameId";

    /** Constant represents the field name. */
    private static final String FIELD_DOMAINID = "domainId";

    /** Constant represents the sql clause get the IDs of all active domain entities. */
/*
    private static final String SQL_SELECT_ACTIVE_DOMAINS
        = "SELECT DISTINCT t3.id AS domainId " +
          "FROM hosting_block t5 LEFT JOIN plyr_won_game t6 ON (t5.game_id = t6.game_id) " +
          "INNER JOIN bid t1 ON (t5.id = t1.auction_id) " + "INNER JOIN [image] t2 ON (t1.image_id = t2.id) " +
          "INNER JOIN domain t3 ON (t2.domain_id = t3.id) " +
          "INNER JOIN hosting_slot t4 ON (t1.id = t4.bid_id AND t4.hosting_start IS NOT NULL AND t4.is_deleted=0) " +
          "WHERE (t6.player_id IS NULL)";
*/
    private static final String SQL_SELECT_ACTIVE_DOMAINS
        = "SELECT DISTINCT t3.id AS domainId " +
          "FROM hosting_block t5 " +
          "INNER JOIN game t6 ON (t5.game_id = t6.id) " +
          "INNER JOIN hosting_slot t4 ON (t5.id = t4.hosting_block_id AND t4.hosting_start IS NOT NULL AND t4.is_deleted=0) " +
          "INNER JOIN [image] t2 ON (t4.image_id = t2.id) " +
          "INNER JOIN domain t3 ON (t2.domain_id = t3.id) " +
          "WHERE (t6.start_date <= GETDATE() AND (t6.end_date IS NULL OR t6.end_date > GETDATE()))";

    /** Constant represents the sql clause to delete slot with the id. */
    private static final String SQL_DELETE_HOSTING_SLOT = "UPDATE hosting_slot SET is_deleted=1 WHERE id = ?";

    /** Constant represents the sql clause to complete the game with date and with the id. */
    private static final String SQL_COMPLETE_GAME = "UPDATE game SET end_date=? WHERE id = ?";

    /** Constant represents the field name. */
    private static final String FIELD_KEY_IMAGE_ID = "key_image_id";

    /** Constant represents the sql clause to call stored procedure. */
    private static final String SP_CREATE_DOMAIN_TARGET = "{Call CreateDomainTarget(?,?,?,?,?,?,?,?)}";

    /** Constant represents the sql clause insert record into brn_tsr_for_slot. */
    private static final String SQL_INSERT_BRN_TSR_FOR_SLOT
        = "INSERT INTO brn_tsr_for_slot (hosting_slot_id,sequence_number,puzzle_id) VALUES (?,?,?)";

    /** Constant represents the sql clause. */
//    private static final String SQL_SELECT_BRN_TSR_FOR_SLOT = "SELECT * FROM brn_tsr_for_slot WHERE hosting_slot_id = ? AND puzzle_id = ?";

    /** Constant represents the sql clause insert record into puzzle_for_slot. */
    private static final String SQL_INSERT_PUZZLE_FOR_SLOT = "INSERT INTO puzzle_for_slot(hosting_slot_id,puzzle_id) VALUES(?,?) ";

    /** Constant represents the sql clause get slot for puzzle. */
//    private static final String SQL_SELECT_PUZZLE_FOR_SLOT = "SELECT * FROM puzzle_for_slot WHERE hosting_slot_id = ? AND puzzle_id = ?";

    /** Constant represents the sql clause update slot. */
    private static final String SQL_UPDATE_HOSTING_SLOT = "UPDATE hosting_slot SET sequence_number = ? , hosting_start = ? , hosting_end = ? WHERE id = ?";

    /** Constant represents the sql clause insert record into plyr_regstrd_game. */
    private static final String SQL_INSERT_PLYR_REGSTRD_GAME = "INSERT INTO plyr_regstrd_game(game_id,player_id) VALUES(?,?)";

    /** Constant represents the sql clause get all game that has registerd player. */
    private static final String SQL_SELECT_PLYR_REGSTRD_GAME_TWO_ID = "SELECT * FROM plyr_regstrd_game WHERE game_id = ? AND player_id = ?";

    /** Constant represents the sql clause update plugin downloads. */
    private static final String SQL_UPDATE_PLUGIN_DOWNLOADS = "UPDATE plugin_downloads SET count = count + 1 WHERE plugin_name = ?";

    /** Constant represents the sql clause get all plugin download with name. */
    private static final String SQL_SELECT_PLUGIN_DOWNLOADS = "SELECT * FROM plugin_downloads WHERE plugin_name = ?";

    /** Constant represents the sql clause get all plugin download with name. */
    private static final String SQL_SELECT_PLUGIN_DOWNLOADS_STATS = "SELECT * FROM plugin_downloads";

    /** Constant represents the sql clause insert record into plyr_compltd_game. */
    private static final String SQL_INSERT_PLYR_COMPLTD_GAME = "INSERT INTO plyr_compltd_game(game_id,player_id) VALUES(?,?)";

    /** Constant represents the sql clause to get game has given player. */
    private static final String SQL_SELECT_PLYR_COMPLTD_GAME = "SELECT * FROM plyr_compltd_game WHERE game_id = ? AND player_id = ?";

    /** Constant represents the field name. */
    private static final String FIELD_KEY_TEXT = "key_text";

    /** Constant represents the field name. */
    private static final String FIELD_SUGGESTED_NAME = "suggested_name";

    /** Constant represents the field name. */
    private static final String FIELD_CULE_IMG_ID = "clue_img_id";

    /** Constant represents the field name. */
    private static final String FIELD_IDENTIFIER_HASH = "identifier_hash";

    /** Constant represents the field name. */
    private static final String FIELD_IDENTIFIER_TEXT = "identifier_text";

    /** Constant represents the field name. */
    private static final String FIELD_URI_PATH = "uri_path";

    /** Constant represents the sql clause get target object with slot id. */
    private static final String SQL_SELECT_TARGET_OBJECT = "SELECT * FROM target_object WHERE target_object.hosting_slot_id = ? ORDER BY sequence_number";

    /** Constant represents the sql clause get puzzle id has slot. */
    private static final String SQL_SELECT_PUZZLE_ID = " SELECT puzzle_for_slot.puzzle_id as puzzleId FROM puzzle_for_slot " +
        " WHERE puzzle_for_slot.hosting_slot_id = ";

    /** Constant represents the field name. */
    private static final String FIELD_PUZZLE_ID = "puzzleId";

    /** Constant represents the sql clause get brea id. */
    private static final String SQL_SELECT_BREA_IDS = "SELECT brn_tsr_for_slot.puzzle_id as puzzleId FROM brn_tsr_for_slot " +
        "WHERE brn_tsr_for_slot.hosting_slot_id = ? ORDER BY sequence_number";

    /** Constant represents the sql clause get current amount. */
//    private static final String SQL_SELECT_WINNING_BID
//        = "SELECT current_amount FROM effective_bid t1, bid t2, hosting_slot t3 " +
//        "WHERE t3.bid_id = t2.id AND t1.bid_id = t2.id AND t3.id = ";

    /** Constant represents the sql clause insert record into image. */
    private static final String SQL_INSERT_IMAGE = "INSERT INTO image(domain_id,download_obj_id,is_approved,description) VALUES(?,?,?,?)";

    /** Constant represents the sql clause update image . */
    private static final String SQL_UPDATE_IMAGE = "UPDATE image SET is_approved = ?, description = ? WHERE id = ?";

    /** Constant represents the sql clause update domain. */
    private static final String SQL_UPDATE_DOMAIN = "UPDATE domain SET base_url = ?, is_approved = ? WHERE id = ?";

    /** Constant represents the sql clause get target object with id. */
    private static final String SQL_SELECT_TARGET_OBJECT_WITH_ID = "SELECT * FROM target_object WHERE id = ";

    /** Constant represents the sql clause to call stored procedure. */
    private static final String SP_RECORD_BINARY_OBJECT = "{Call RecordBinaryObject(?,?,?,?)}";

    /** Constant represents the sql clause insert record into plyr_compltd_slot. */
    private static final String SQL_INSERT_PLYR_COMPLTD_SLOT
        = "INSERT INTO plyr_compltd_slot(hosting_slot_id,player_id,timestamp,key_text,key_image_id)VALUES(?,?,?,?,?)";

    /** Constant represents the sql clause get puzzle resource with id. */
    private static final String SQL_SELECT_PUZZLE_RESOURCE_WITH_ID
        = "SELECT  t1.name,t2.suggested_name, t2.media_type, t2.content " +
          "FROM puzzle_resource t1 left outer join download_obj t2 ON t1.download_obj_id = t2.id WHERE t1.puzzle_id= ";

    /** Constant represents the sql clause get puzzle with id. */
    private static final String SQL_SELECT_PUZZLE_WITH_ID = "SELECT * FROM puzzle WHERE id = ";

    /** Constant represents the field name. */
    private static final String FIELD_ATTRIBUTE_VALUE = "value";

    /** Constant represents the sql clause get puzzle attribute with id. */
    private static final String SQL_SELECT_PUZZLE_ATTRIBUTE_WITH_ID
        = "SELECT * FROM puzzle_attribute WHERE puzzle_id = ";

    /** Constant represents the field name. */
    private static final String FIELD_CONTENT = "content";

    /** Constant represents the field name. */
    private static final String FIELD_MEDIA_TYPE = "media_type";

    /** Constant represents the sql clause get image with domain id. */
    private static final String SQL_SELECT_IMAGE_WITH_DOMAIN_ID = "SELECT * FROM [image] WHERE domain_id =  ";

    /** Constant represents the field name. */
    private static final String FIELD_DESCRIPTION = "description";

    /** Constant represents the field name. */
    private static final String FIELD_BASE_URL = "base_url";

    /** Constant represents the field name. */
    private static final String FIELD_IS_APPROVED = "is_approved";

    /** Constant represents the field name. */
    private static final String FIELD_SPONSOR_ID = "sponsor_id";

    /** Constant represents the sql clause get image with slot id. */
/*
    private static final String SQL_SELECT_IMAGE_DOMAIN =
        "SELECT t1.id as image_id, t1.domain_id as domain_id " +
        "FROM [image] t1, bid t2, hosting_slot t3 " +
        "WHERE t3.bid_id = t2.id AND t2.image_id = t1.id AND t3.id = ";
*/
    private static final String SQL_SELECT_IMAGE_DOMAIN =
        "SELECT t1.id as image_id, t1.domain_id as domain_id FROM [image] t1 WHERE t1.id = ";

    /** Constant represents the field name. */
    private static final String FIELD_HOSTING_END = "hosting_end";

    /** Constant represents the field name. */
    private static final String FIELD_HOSTING_START = "hosting_start";

    /** Constant represents the field name. */
    private static final String FIELD_HOSTING_BLOCK_ID = "hosting_block_id";

    /** Constant represents the sql clause get slot for a block. */
/*
    private static final String SQL_SELECT_HOSTING_SLOT_WITH_BLOCK_ID = "SELECT hosting_slot.id as id "
	    + "FROM hosting_slot INNER JOIN bid ON hosting_slot.bid_id = bid.id AND hosting_slot.is_deleted=0 "
	    + "WHERE bid.auction_id =";
*/
    private static final String SQL_SELECT_HOSTING_SLOT_WITH_BLOCK_ID = "SELECT hosting_slot.id as id "
	    + "FROM hosting_slot WHERE hosting_slot.is_deleted=0 AND hosting_slot.hosting_block_id =";

    /** Constant representing the SQL clause for ordering hosting slots by their sequence numbers */
    private static final String SQL_ORDER_SLOTS_BY_SEQUENCE = " ORDER BY hosting_slot.sequence_number";

    /** Constant represents the field name. */
    private static final String FIELD_MAX_TIME_PER_SLOT = "max_time_per_slot";

    /** Constant represents the field name. */
    private static final String FIELD_SEQUENCE_NUMBER = "sequence_number";

    /** Constant represents the sql clause get block for a game. */
    private static final String SQL_SELECT_HOSTING_BLOCK_WITH_GAME_ID = "SELECT * FROM hosting_block WHERE game_id = ";

    /** Constant representing the SQL clause for ordering hosting blocks by their sequence numbers */
    private static final String SQL_ORDER_BY_SEQUENCE = " ORDER BY sequence_number";

    /** Constant represents the field name. */
    private static final String FIELD_KEY_REQUIRED = "keys_required";

    /** Constant represents the field name. */
    private static final String FIELD_BOUNCE_CALC_TYPE = "bounce_calc_type";

    /** Constant represents the field name. */
    private static final String FIELD_PRIZE_CALC_TYPE = "prize_calc_type";

    /** Constant represents the field name. */
    private static final String FIELD_COMPLETION_TYPE = "completion_type";

    /** Constant represents the sql clause. */
    private static final String SQL_START_DATE = "start_date";

    /** Constant represents the sql clause. */
    private static final String SQL_END_DATE = "end_date";

    /** Constant represents the field name. */
    private static final String FIELD_BALL_COLOR_ID = "ball_color_id";

    /** Constant represents the sql clause to call stored procedure. */
    private static final String SP_CREATE_IMAGE_INFO = "{Call CreateImageInfo(?,?,?,?,?,?)}";

    /** Constant represents the sql clause to call stored procedure. */
    private static final String SP_CREATE_DOMAIN = "{Call CreateDomain(?,?,?,?,?)}";

    /** Constant represents the field name. */
    private static final String FIELD_CURRENT_AMOUNT = "current_amount";

    /** Constant represents the sql clause get effective bid with bid id. */
    private static final String SQL_SELECT_EFFECTIVE_BID_WITH_BID_ID = "SELECT * FROM effective_bid WHERE bid_id = ";

    /** Constant represents the field name. */
    private static final String FIELD_DOMAIN_ID = "domain_id";

    /** Constant represents the sql clause get image with id. */
    private static final String SQL_SELECT_IMAGE_WITH_ID = "SELECT * FROM [image] where id = ";

    /** Constant represents the field name. */
    private static final String FIELD_IMAGE_ID = "image_id";

    /** Constant represents the field name. */
    private static final String FIELD_HOSTING_PAYMENT = "hosting_payment";

    /** Constant represents the sql clause. */
    private static final String SQL_SELECT_BID_WITH_ID_AND_AUTION_ID = "SELECT * FROM bid WHERE id = ? and auction_id = ?";

    /** Constant represents the sql clause get bid with id. */
    private static final String SQL_SELECT_BID_WITH_ID = "SELECT * FROM bid WHERE id = ";

    /** Constant represents the table name. */
    private static final String TABLE_HOSTING_SLOT = "hosting_slot";

    /** Constant represents the sql clause get block with id. */
    private static final String SQL_SELECT_HOSTING_BLOCK_WITH_ID = "SELECT * FROM hosting_block WHERE id = ";

    /** Constant represents the sql clause get game id that has registered player. */
    private static final String SQL_SELECT_PLYR_REGSTRD_GAME = "SELECT game_id as id FROM plyr_regstrd_game WHERE plyr_regstrd_game.player_id =";
    
    /** Constant represents the sql clause get game id that is completed by the player. */
    private static final String SQL_SELECT_PLYR_COMPLETE_GAME = "SELECT game_id as id FROM plyr_compltd_game WHERE plyr_compltd_game.player_id =";

    /** Constant represents the sql clause get sponsor with id. */
    private static final String SQL_SELECT_SPONSOR_WITH_ID = "SELECT * FROM sponsor WHERE any_user_id = ";

    /** Constant represents the sql clause get domain for a sponsor. */
    private static final String SQL_SELECT_DOMAIN_WITH_SPONSOR_ID = "SELECT * FROM domain WHERE sponsor_id = ";

    /** Constant represents the sql clause get domain for a sponsor. */
    private static final String SQL_SELECT_APPROVED_DOMAINS = "SELECT * FROM domain WHERE is_approved = 1";

    /** Constant represents the sql clause get slot has complete player. */
    private static final String SQL_SELECT_PLYR_COMPLTD_SLOT = "SELECT * FROM plyr_compltd_slot WHERE hosting_slot_id  = ? AND player_id = ?";

    /**
     * An SQL prepared statement string for selecting the ID of the first hosting_slot in the hosting sequence for the specified
     * game, subject to the constraints that the specified domain be assigned to the slot and that the slot not have been
     * completed by the specified player
     */
/*
    private static final String SQL_PS_SELECT_SLOT_FOR_DOMAIN
        = "SELECT TOP (1) t5.id FROM hosting_block t1 " +
	    "INNER JOIN bid t2 ON (t1.game_id = ? AND t1.id = t2.auction_id) " +
        "INNER JOIN [image] t3 ON (t2.image_id = t3.id) " +
        "INNER JOIN domain t4 ON (t4.base_url = ? AND t3.domain_id = t4.id) " +
        "INNER JOIN hosting_slot t5 ON (t5.is_deleted = 0 AND t2.id = t5.bid_id) " +
        "LEFT JOIN plyr_compltd_slot t6 ON (t5.id = t6.hosting_slot_id AND t6.player_id = ?) " +
        "WHERE (t6.timestamp IS NULL) " +
        "ORDER BY t1.sequence_number, t5.sequence_number";
*/
    private static final String SQL_PS_SELECT_SLOT_FOR_DOMAIN
        = "SELECT TOP (1) t5.id " +
          "FROM hosting_block t1 " +
          "INNER JOIN hosting_slot t5 ON (t1.game_id = ? AND t5.is_deleted = 0 AND t1.id = t5.hosting_block_id) " +
          "INNER JOIN [image] t3 ON (t5.image_id = t3.id) " +
          "INNER JOIN domain t4 ON (t4.base_url = ? AND t3.domain_id = t4.id) " +
          "LEFT JOIN plyr_compltd_slot t6 ON (t5.id = t6.hosting_slot_id AND t6.player_id = ?) " +
          "WHERE (t6.timestamp IS NULL) " +
          "ORDER BY t1.sequence_number, t5.sequence_number";

    /** Constant represents the field name. */
    private static final String FIELD_DOWNLOAD_OBJ_ID = "download_obj_id";

    /** Constant represents the field name. */
    private static final String FIELD_NAME = "name";

    /** Constant represents the sql clause get domain with name. */
//    private static final String SQL_SELECT_DOMAIN_WITH_NAME = "SELECT * FROM domain WHERE base_url = ? ";

    /** Constant represents the sql clause get slot with id. */
    private static final String SQL_SELECT_HOSTING_SLOT_WITH_ID = "SELECT * FROM hosting_slot WHERE id = ";

    /** Constant represents the sql clause get domain with id. */
    private static final String SQL_SELECT_DOMAIN_WITH_ID = "SELECT * FROM domain WHERE id = ";

    /** Constant represents the sql clause get download obj with id. */
    private static final String SQL_SELECT_DOWNLOAD_OBJ_WITH_ID = "SELECT * FROM download_obj WHERE id = ";

    /** Constant represents the field name. */
    private static final String FIELD_ID = "id";

    /** Constant represents the sql clause to call stored procedure. */
    private static final String SP_CREATE_HOSTING_BLOCK = "{Call CreateHostingBlock(?,?,?,?)}";

    /** Constant represents the table name. */
    private static final String TABLE_HOSTING_BLOCK = "hosting_block";

    /**
     * <p>
     * SQL Clause to call the stored procedure : CreateGame.
     * </p>
     */
    private static final String SP_CREATE_GAME = "{Call CreateGame(?,?,?,?,?,?,?,?,?)}";

    /**
     * <p>
     * SQL clause to query the BALL_COLOR table with id.
     * </p>
     */
    private static final String SQL_SELECT_BALL_COLOR_WITH_ID = "SELECT * FROM ball_color WHERE id = ";

    /**
     * <p>
     * SQL clause to query the BALL_COLOR table .
     * </p>
     */
    private static final String SQL_SELECT_FROM_BALL_COLOR = "SELECT * FROM ball_color";

    /**
     * <p>
     * SQL clause to query the player table with id.
     * </p>
     */
    private static final String SQL_SELECT_PLAYER_WITH_ID = "SELECT * FROM player WHERE any_user_id = ";

    /**
     * <p>
     * SQL clause to query the game table with id.
     * </p>
     */
/*
    private static final String SQL_SELECT_GAME_WITH_ID = "SELECT t1.*, t2.player_id AS winner_id, t2.date AS end_date " +
        "FROM game t1 LEFT JOIN plyr_won_game t2 ON (t1.id = t2.game_id) " + "WHERE t1.id = ";
*/
    private static final String SQL_SELECT_GAME_WITH_ID = "SELECT t1.* FROM game t1 WHERE t1.id = ";

    /**
     * <p>
     * represents the property name in the config file.
     * </p>
     */
    private static final String MEDIA_TYPE = "mediaType";

    /**
     * <p>
     * represents the property name in the config file.
     * </p>
     */
    private static final String KEY_LENGTH = "keyLength";

    /**
     * <p>
     * represents the property name in the config file.
     * </p>
     */
    private static final String NAME = FIELD_NAME;

    /**
     * <p>
     * represents the property name in the config file.
     * </p>
     */
    private static final String FACTORY_KEY = "factoryKey";

    /**
     * <p>
     * represents the property name in the config file.
     * </p>
     */
    private static final String RANDOM_STRING_IMAGE_KEY = "randomStringImageKey";

    /**
     * <p>
     * represents the property name in the config file.
     * </p>
     */
    private static final String SEPC_NAMESPACE = "specNamespace";

    /**
     * <p>
     * Represents the connection factory that is used for performing CRUD operations. It should be backed by a JNDI connection
     * producer, which simply eases the obtaining of a connection from a datasource via JNDI.&nbsp; This is created in the
     * constructor, will not be null, and will never change.
     * </p>
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * <p>
     * Represents the name of the connection to obtain from the connection factory that is used for performing CRUD
     * operations.&nbsp; This is created in the constructor. This value is optional, and empty will be set to null, and will
     * never change. If null, then the default connection will be obtained.
     * </p>
     */
    private final String connectionName;

    /**
     * <p>
     * Represents the RandomStringImage generator that is used for generating text and images for hosting slot completion. This
     * is created in the constructor, will not be null, and will never change.
     * </p>
     */
    private final RandomStringImage randomStringImage;

    /**
     * <p>
     * Represents the length of text for the random string image generator to generate for hosting slot completion. This is
     * created in the constructor, will not be null, and will never change.
     * </p>
     */
    private final int keyLength;

    /**
     * <p>
     * Represents the media type to plug into the download object, for hosting slot completion. This is created in the
     * constructor, will not be null/empty, and will never change.
     * </p>
     */
    private final String mediaType;

    /**
     * <p>
     * Instantiates new SQLServerGameDataDAO instance from the given namespace.
     * </p>
     *
     * @param namespace configuration namespace
     *
     * @throws InstantiationException If there is an error with construction
     * @throws IllegalArgumentException If namespace is null or empty
     */
    public SQLServerGameDataDAO(String namespace) throws InstantiationException {
        Helper.checkNotNullOrEmpty(namespace, "namespace");

        try {
            ObjectFactory factory = new ObjectFactory(new ConfigManagerSpecificationFactory(Helper.getString(namespace,
                            SEPC_NAMESPACE, true)));

            randomStringImage = (RandomStringImage) factory.createObject(Helper.getString(namespace, RANDOM_STRING_IMAGE_KEY, true));

            connectionFactory = (DBConnectionFactory) factory.createObject(Helper.getString(namespace, FACTORY_KEY, true));
            // the connectioName is optional
            connectionName = Helper.getString(namespace, NAME, false);

            // test the connection
            getConnection().close();

            keyLength = Helper.getInt(namespace, KEY_LENGTH, true);
            mediaType = Helper.getString(namespace, MEDIA_TYPE, true);
        } catch (DBConnectionException e) {
            throw new InstantiationException("The database connection factory can not be instantiated.", e);
        } catch (Exception e) {
            throw new InstantiationException("Error in instantiate SQLServerGameDataDAO.", e);
        }
    }

    /**
     * <p>
     * Creates a new game entity in the persistent store, along with associated hosting blocks. Any game or block IDs that are
     * null will be automatically assigned acceptable values. No hosting slots are created for the game at this time. The
     * returned Game object will represent the persisted data, including any IDs assigned to the game and blocks.
     * </p>
     *
     * @param game the game
     *
     * @return the game with the id
     *
     * @throws EntryNotFoundException If game.ballColor.id is not found in persistence
     * @throws DuplicateEntryException If game.id is not null but already exists in persistence
     * @throws PersistenceException If there is any problem in the persistence layer
     * @throws IllegalArgumentException If game is null
     */
    public Game createGame(final Game game) throws PersistenceException {
        Helper.checkNotNull(game, "Game");

        try {
            Connection conn = getConnection();

            try {
                CallableStatement stmt;
                long gameId;

                // checks if the game already exists
                if ((game.getId() != null) && exist(conn, SQL_SELECT_GAME_WITH_ID + game.getId().longValue(), null)) {
                    throw new DuplicateEntryException("The game with id :" + game.getId() + " already exists.", game.getId());
                }

                // checks if the game's ballColor exists
                if (!exist(conn, SQL_SELECT_BALL_COLOR_WITH_ID + game.getBallColor().getId().longValue(), null)) {
                    throw new EntryNotFoundException("The game's ballColor to create does not exist.", game.getBallColor().getId());
                }

                // call the stored procedure to persist the game record into the
                // 'game' table
                stmt = callableStatement(SP_CREATE_GAME,
                        new Object[] {
                            game.getId(), game.getBallColor().getId(), new Integer(game.getKeyCount()),
                            toSQLDate(game.getStartDate()), toSQLDate(game.getEndDate()),
                            new Integer(game.getBouncePointCalculationType()),
                            new Integer(game.getPrizeCalculationType()), new Integer(game.getCompletionType()),
                            FIELD_ID
                        }, conn);

                gameId = stmt.getLong(9);
                close(stmt);

                // get the next sequence_number of the table hosting_block
                int sequenceNumber = getNextSequenceNumber(conn, TABLE_HOSTING_BLOCK);

                // persist the game's HostingBlock array into the 'hosting_block'
                // table
                HostingBlock[] blocks = game.getBlocks();
                HostingBlock[] updatedBlocks = new HostingBlock[blocks.length];

                for (int i = 0; i < blocks.length; i++) {
                    stmt = callableStatement(SP_CREATE_HOSTING_BLOCK,
                            new Object[] {
                                new Long(gameId), new Integer(sequenceNumber), new Integer(blocks[i].getMaxHostingTimePerSlot()),
                                FIELD_ID
                            }, conn);
                    updatedBlocks[i] = new HostingBlockImpl(new Long(stmt.getLong(4)), sequenceNumber++, blocks[i].getSlots(),
                            blocks[i].getMaxHostingTimePerSlot());
                    close(stmt);
                }

                // return the updated game instance
                return new GameImpl(new Long(gameId), game.getBallColor(), game.getKeyCount(), game.getStartDate(),
                    game.getEndDate(), updatedBlocks, game.getBouncePointCalculationType(),
                    game.getPrizeCalculationType(), game.getCompletionType());
            } finally {
                close(conn);
            }
        } catch(PersistenceException e){
            throw e;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error in create connection from database factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in query/update database when persisting game.", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in persisting Game instance to database.", e);
        }
    }

    /**
     * <p>
     * Creates hosting slots associates with the specified Bid IDs in the specified hosting block.  This method will persist the
     * slots in hosting_slot table and return the appropiate hosting slots.
     * </p>
     *
     * @param blockId the block id the slot add to
     * @param bidIds the bid ids
     *
     * @return array of hosting slots
     *
     * @throws EntryNotFoundException If blockId or any bidId doesn't exist in the persistence
     * @throws InvalidEntryException If any bidId does not belong to the blockId
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If bidIds is null
     */
    public HostingSlot[] createSlots(long blockId, long[] bidIds)
        throws PersistenceException {
        Helper.checkNotNull(bidIds, "bidIds");

        try {
            Connection conn = this.getConnection();

            try {
                // checks if the blockId exists in the 'hosting_block' table
                if (!exist(conn, SQL_SELECT_HOSTING_BLOCK_WITH_ID + blockId, null)) {
                    throw new EntryNotFoundException("The block with the id: " + blockId + " does not exist.", new Long(blockId));
                }

                HostingSlot[] slots = new HostingSlot[bidIds.length];

                // get the next sequenceNumber of the hosting_slot table
                int sequenceNumber = getNextSequenceNumber(conn, TABLE_HOSTING_SLOT);

                for (int i = 0; i < bidIds.length; i++) {
                    Long bidId = new Long(bidIds[i]);
                    // checks if earch bid exists in the 'bid' table
                    if (!exist(conn, SQL_SELECT_BID_WITH_ID + bidIds[i], null)) {
                        throw new EntryNotFoundException("The bid with the id: " + bidIds[i] + " does not exist.",
                                                         bidId);
                    } else {
                        long imageId = 0;

                        // checks if the 'bid.auction_id' matches the blockId
                        ResultSet rs = query(conn, SQL_SELECT_BID_WITH_ID_AND_AUTION_ID,
                                             new Object[]{bidId, new Long(blockId) });

                        try {
                            if (!rs.next()) {
                                throw new InvalidEntryException("The bid to persistence is illegal, its blockID id and "
                                                                + "the auction_id in database does not match.", bidId);
                            } else {
                                imageId = rs.getLong(FIELD_IMAGE_ID);
                            }
                        } finally {
                            close(rs);
                        }

                        // retrieve the domain_id from the 'image' table
                        Domain domain = null;

                        rs = query(conn, SQL_SELECT_IMAGE_WITH_ID + imageId, null);

                        try {
                            if (rs.next()) {
                                // will throw an exception if no such domain is found:
                                domain = getDomain(rs.getLong(FIELD_DOMAIN_ID));
                            }
                        } finally {
                            close(rs);
                        }

                        // retrieve the 'current_amount' from 'effective_bid'
                        // table
                        int currentAmount;

                        rs = query(conn, SQL_SELECT_EFFECTIVE_BID_WITH_BID_ID + bidIds[i], null);

                        try {
                            currentAmount = (rs.next() ? rs.getInt(FIELD_CURRENT_AMOUNT) : 0);
                        } finally {
                            close(rs);
                        }

                        // persist the hosting slot
                        long id = this.insert(conn,
                                              "INSERT INTO hosting_slot (hosting_block_id,sequence_number," +
                                              "hosting_start,hosting_end,is_deleted,image_id,hosting_payment) " +
                                              "values(?,?,null,null,0,?,?)",
                                              new Object[] {new Long(blockId), new Long(sequenceNumber),
                                                            new Long(imageId), new Integer(currentAmount)});
                        // insert a record for relation between
                        update(conn,
                               "INSERT INTO bid_for_slot (bid_id,hosting_slot_id) VALUES (?,?)",
                               new Object[] {bidId, new Long(id)});

                        // create the HostingSlotImpl instance
                        slots[i] = new HostingSlotImpl(new Long(id), domain, imageId, new long[0], null,
                                                       sequenceNumber++, new DomainTarget[0], currentAmount, null, null,
                                                       blockId);
                    }
                }

                return slots;
            } finally {
                close(conn);
            }
        } catch(PersistenceException e){
            throw e;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error in create connection from database factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in query/update database when creating HostingSlot.", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in creating Slot.", e);
        }
    }

    /**
     * <p>
     * Creates a new persistent domain representation with the data from the provided Domain object and its nested ImageInfo
     * objects. Any null Domain or ImageIndo IDs are assigned appropriate values. The returned Domain will reflect the
     * persistent representation, including any automatically assigned IDs.
     * </p>
     *
     * @param domain the domain
     *
     * @return the domain with id
     *
     * @throws EntryNotFoundException If imageInfo.downloadId doesn't exist in persistence
     * @throws DuplicateEntryException If id or imageInfo.id is not null but already exists in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If domain is null
     */
    public Domain createDomain(Domain domain) throws PersistenceException {
        Helper.checkNotNull(domain, "Domain");

        try {
            Connection conn = this.getConnection();

            try {
                // checks if the domain already exists
                if ((domain.getId() != null) && exist(conn, SQL_SELECT_DOMAIN_WITH_ID + domain.getId().longValue(), null)) {
                    throw new DuplicateEntryException("The domain with the id: " + domain.getId().longValue() +
                        " already exists.", domain.getId());
                }

                // checks if the domain's sponsorId exists or not
                if (domain.getSponsorId() != null) {
                    if (!exist(conn, SQL_SELECT_SPONSOR_WITH_ID + domain.getSponsorId(), null)) {
                        throw new EntryNotFoundException("The dmain.sponsorId does not exist.", domain.getSponsorId());
                    }
                }

                //checks if the (sponsor_id,base_url) are unique
                if (domain.getSponsorId() != null) {
                    if ( exist(conn,"SELECT * FROM domain WHERE sponsor_id =" + domain.getSponsorId() + " and base_url = ?" , new Object[]{domain.getDomainName()})){
                        throw new DuplicateEntryException("The sponsor_id and the base_url should be combind as unique.",domain.getId());
                    }
                } else {
                    if ( exist(conn,"SELECT * FROM domain WHERE sponsor_id IS NULL AND base_url = ?" ,
                               new Object[]{domain.getDomainName()})){
                        throw new DuplicateEntryException("The the base_url should be combined as unique.",
                                                          domain.getId());
                    }
                }

                // persist the Domain into the 'domain' table
                CallableStatement stmt = callableStatement(SP_CREATE_DOMAIN,
                        new Object[] {
                            domain.getId(), domain.getSponsorId(), domain.getDomainName(), domain.isApproved(), FIELD_ID
                        }, conn);

                long domainId = stmt.getLong(5);
                close(stmt);

                // persist the ImageInfo array into 'image' table
                ImageInfo[] images = domain.getImages();
                ImageInfo[] updatedImages = new ImageInfo[images.length];

                for (int i = 0; i < images.length; i++) {
                    if ((images[i].getId() != null) &&
                            exist(conn, SQL_SELECT_IMAGE_WITH_ID + images[i].getId().longValue(), null)) {
                        throw new DuplicateEntryException("The image with the id:" + images[i].getId().longValue() +
                            " already exists.", images[i].getId());
                    }

                    // checks if the download id exist or not,
                    checkDownloadDataNotExist(images[i].getDownloadId(), conn);

                    // persist the image into image table
                    stmt = callableStatement(SP_CREATE_IMAGE_INFO,
                            new Object[] {
                                images[i].getId(), new Long(domainId), new Long(images[i].getDownloadId()), domain.isApproved(),
                                images[i].getDescription(), FIELD_ID
                            }, conn);
                    updatedImages[i] = new ImageInfoImpl(new Long(stmt.getLong(6)), images[i].getDownloadId(),
                            images[i].getDescription(), images[i].isApproved());
                    close(stmt);
                }

                return new DomainImpl(new Long(domainId), domain.getSponsorId(), domain.getDomainName(), domain.isApproved(),
                    updatedImages);
            } finally {
                close(conn);
            }
        } catch(PersistenceException e){
            throw e;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error in create connection from database factory.", e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PersistenceException("Error in query/update database when persisting domain/images", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in creating Domain.", e);
        }
    }

    /**
     * <p>
     * Creates a new, persistent hosting block for the specified game. The block will having an auto-assigned ID, the next
     * available sequence number after those of the game's existing blocks (or 1 if there are no other blocks), no hosting
     * slots, and the specified maximum hosting time per slot. It returns a HostingBlock object representing the new block.
     * </p>
     *
     * @param gameId the game id
     * @param slotMaxHostingTime the max hosting time for the slot
     *
     * @return the hosting block
     *
     * @throws EntryNotFoundException If gameId is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public HostingBlock addBlock(long gameId, int slotMaxHostingTime)
        throws PersistenceException {
        try {
            Connection conn = this.getConnection();

            try {
                // checks if the gameid exist or not, if not throw exception
                checkGameNotExist(gameId, conn);

                // call a stored procedure to add a block; immediately close the statement
                int sequenceNumber = getNextSequenceNumber(conn, TABLE_HOSTING_BLOCK);
                CallableStatement stmt = callableStatement(SP_CREATE_HOSTING_BLOCK,
                        new Object[] { new Long(gameId), new Integer(sequenceNumber), new Integer(slotMaxHostingTime), FIELD_ID },
                        conn);

                try {
                    return new HostingBlockImpl(new Long(stmt.getLong(4)), sequenceNumber, new HostingSlot[0], slotMaxHostingTime);
                } finally {
                    close(stmt);
                }
            } finally {
                close(conn);
            }
        } catch(PersistenceException e){
            throw e;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error in create connection from database factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in query/update database when add HostingBlock to a game.", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in add HostingBlock to database.", e);
        }
    }

    /**
     * <p>
     * Retrieves a Game object representing the Game having the specified ID.
     * </p>
     *
     * @param gameId the game id
     *
     * @return the game
     *
     * @throws EntryNotFoundException If gameId is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public Game getGame(long gameId) throws PersistenceException {
        try {
            Connection conn = getConnection();

            try {
                long ballColorId;
                Timestamp startDate;
                Timestamp endDate;
                int keyCount;
                int bounceCalcType;
                int prizeCalcType;
                int gameCompletionType;

                // query the game record, if not exist, throw exception
                ResultSet rs = query(conn, SQL_SELECT_GAME_WITH_ID + gameId, null);

                try {
                    if (!rs.next()) {
                        throw new EntryNotFoundException("The game with the gameID:" + gameId + " does not exist.",
                            new Long(gameId));
                    } else {
                        ballColorId = rs.getLong(FIELD_BALL_COLOR_ID);
                        startDate = rs.getTimestamp(SQL_START_DATE);
                        endDate = rs.getTimestamp(SQL_END_DATE);
                        keyCount = rs.getInt(FIELD_KEY_REQUIRED);
                        bounceCalcType = rs.getInt(FIELD_BOUNCE_CALC_TYPE);
                        prizeCalcType = rs.getInt(FIELD_PRIZE_CALC_TYPE);
                        gameCompletionType = rs.getInt(FIELD_COMPLETION_TYPE);
                    }
                } finally {
                    close(rs);
                }

                // get the game's BallColor from the 'ball_color' table
                BallColor color;

                rs = query(conn, SQL_SELECT_BALL_COLOR_WITH_ID + ballColorId, null);

                try {
                    color = (rs.next()
                        ? new BallColorImpl(new Long(ballColorId), rs.getString(FIELD_NAME), rs.getLong(FIELD_DOWNLOAD_OBJ_ID))
                        : null);
                } finally {
                    close(rs);
                }

                // get the game's HostingBlock array from the 'hosting_block'
                // table
                List blocks = new ArrayList();

                rs = query(conn, SQL_SELECT_HOSTING_BLOCK_WITH_GAME_ID + gameId + SQL_ORDER_BY_SEQUENCE, null);

                try {
                    while (rs.next()) {
                        blocks.add(getBlock(rs.getLong(FIELD_ID)));
                    }
                } finally {
                    close(rs);
                }

                // return the game instance with the retrieved data
                return new GameImpl(new Long(gameId), color, keyCount, startDate, endDate,
                    (HostingBlock[]) blocks.toArray(new HostingBlock[blocks.size()]), bounceCalcType, prizeCalcType,
                    gameCompletionType);
            } finally {
                close(conn);
            }
        } catch(PersistenceException e){
            throw e;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error in create connection from database factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in query database when retrieve game instance", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in get game from database.", e);
        }
    }

    /**
     * <p>
     * Retrieves a HostingBlock object representing the hosting block having the specified ID.
     * </p>
     *
     * @param blockId the block id
     *
     * @return HostingBlock instance
     *
     * @throws EntryNotFoundException If blockId is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public HostingBlock getBlock(long blockId) throws PersistenceException {
        try {
            Connection conn = getConnection();

            try {
                int sequenceNumber;
                int maxHostingTimePerSlot;

                // query the hosting_block record, if not exist, throw exception
                ResultSet rs = query(conn, SQL_SELECT_HOSTING_BLOCK_WITH_ID + blockId, null);

                try {
                    if (!rs.next()) {
                        throw new EntryNotFoundException("The HostingBlock with the id:" + blockId + " does not exist.",
                            new Long(blockId));
                    } else {
                        sequenceNumber = rs.getInt(FIELD_SEQUENCE_NUMBER);
                        maxHostingTimePerSlot = rs.getInt(FIELD_MAX_TIME_PER_SLOT);
                    }
                } finally {
                    close(rs);
                }

                // get the game's HostingSlot array from the 'hosting_slot' table
                List slots = new ArrayList();

                rs = query(conn, SQL_SELECT_HOSTING_SLOT_WITH_BLOCK_ID + blockId + SQL_ORDER_SLOTS_BY_SEQUENCE, null);

                try {
                    while (rs.next()) {
                        slots.add(getSlot(rs.getLong(FIELD_ID)));
                    }
                } finally {
                    close(rs);
                }

                // return the HostingBlock instance with the retrieved data
                return new HostingBlockImpl(new Long(blockId), sequenceNumber,
                    (HostingSlot[]) slots.toArray(new HostingSlot[slots.size()]), maxHostingTimePerSlot);
            } finally {
                close(conn);
            }
        } catch(PersistenceException e){
            throw e;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error in create connection from database factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in query database when retrieve HostingBlock instance", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in get HostingBlock from database.", e);
        }
    }

    /**
     * <p>
     * Retrieves a HostingSlot object representing the slot having the specified ID.
     * </p>
     *
     * @param slotId the slot id
     *
     * @return the slot
     *
     * @throws EntryNotFoundException If slotId is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public HostingSlot getSlot(long slotId) throws PersistenceException {
        try {
            Connection conn = getConnection();

            try {
                int sequenceNumber;
                Timestamp hostingStart;
                Timestamp hostingEnd;
                long blockId;
                long imageId;
                int winningBid;

//                long bidId;
                // query the hosting_slot record, if not exist, throw exception
                ResultSet rs = query(conn, SQL_SELECT_HOSTING_SLOT_WITH_ID + slotId + " AND is_deleted = 0", null);

                try {
                    if (!rs.next()) {
                        throw new EntryNotFoundException("The HostingSlot with the id:" + slotId + " does not exist.",
                            new Long(slotId));
                    } else {
                        sequenceNumber = rs.getInt(FIELD_SEQUENCE_NUMBER);
                        hostingStart = rs.getTimestamp(FIELD_HOSTING_START);
                        hostingEnd = rs.getTimestamp(FIELD_HOSTING_END);
                        blockId = rs.getLong(FIELD_HOSTING_BLOCK_ID);
                        imageId = rs.getLong(FIELD_IMAGE_ID);
                        winningBid = rs.getInt(FIELD_HOSTING_PAYMENT);
                    }
                } finally {
                    close(rs);
                }

                // get the domain and the imageId
                Domain domain = null;
                rs = query(conn, SQL_SELECT_IMAGE_DOMAIN + imageId, null);
                try {
                    if (rs.next()) {
                        domain = getDomain(rs.getLong(FIELD_DOMAIN_ID));
                    }
                } finally {
                    close(rs);
                }

                // Get brainTeaserIds
                List brainTeaserIds = new ArrayList();

                rs = query(conn, SQL_SELECT_BREA_IDS, new Object[] { new Long(slotId) });

                try {
                    while (rs.next()) {
                        brainTeaserIds.add(new Long(rs.getLong(FIELD_PUZZLE_ID)));
                    }
                } finally {
                    close(rs);
                }

                // List to array
                long[] btids = new long[brainTeaserIds.size()];

                for (int i = 0; i < brainTeaserIds.size(); i++) {
                    btids[i] = ((Long) brainTeaserIds.get(i)).longValue();
                }

                // get the puzzleId
                Long puzzleId = null;

                rs = query(conn, SQL_SELECT_PUZZLE_ID + slotId, null);

                try {
                    if (rs.next()) {
                        puzzleId = new Long(rs.getLong(FIELD_PUZZLE_ID));
                    }
                } finally {
                    close(rs);
                }

                // Get domain targets
                List targets = new ArrayList();

                rs = query(conn, SQL_SELECT_TARGET_OBJECT, new Object[] { new Long(slotId) });

                try {
                    while (rs.next()) {
                        targets.add(new DomainTargetImpl(new Long(rs.getLong(FIELD_ID)), rs.getInt(FIELD_SEQUENCE_NUMBER),
                                rs.getString(FIELD_URI_PATH), rs.getString(FIELD_IDENTIFIER_TEXT),
                                rs.getString(FIELD_IDENTIFIER_HASH), rs.getLong(FIELD_CULE_IMG_ID)));
                    }
                } finally {
                    close(rs);
                }

                // return the HostingBlock instance with the retrieved data
                return new HostingSlotImpl(new Long(slotId), domain, imageId, btids, puzzleId, sequenceNumber,
                    (DomainTarget[]) targets.toArray(new DomainTarget[targets.size()]), winningBid, hostingStart,
                    hostingEnd, blockId);
                
            } finally {
                close(conn);
            }
        } catch(PersistenceException e){
            throw e;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error in create connection from database factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in query database when retrieve HostingSlot instance", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in get HostingSlot from database.", e);
        }
    }

    /**
     * <p>
     * Retrieves the DownloadData object corresponding to the specified ID.
     * </p>
     *
     * @param id the download id
     *
     * @return the download data
     *
     * @throws EntryNotFoundException If id is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public DownloadData getDownloadData(long id) throws PersistenceException {
        try {
            Connection conn = this.getConnection();

            try {
                DownloadData data = null;

                // query the 'download_obj' table with the downloadId, if not
                // found throw exception
                ResultSet rs = query(conn, SQL_SELECT_DOWNLOAD_OBJ_WITH_ID + id, null);

                try {
                    if (!rs.next()) {
                        throw new EntryNotFoundException("The DownloadData with the id:" + id + " does not exist.", new Long(id));
                    } else {
                        data = new DownloadDataImpl(rs.getBytes(FIELD_CONTENT), rs.getString(FIELD_MEDIA_TYPE),
                                rs.getString(FIELD_SUGGESTED_NAME));
                    }
                } finally {
                    close(rs);
                }

                return data;
            } finally {
                close(conn);
            }
        } catch(PersistenceException e){
            throw e;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error in create connection from database factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in query database when retrieve DownloadData instance", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in get DownloadData from database.", e);
        }
    }

    /**
     * <p>
     * Retrieves a Domain object representing the domain corresponding to the specified ID.
     * </p>
     *
     * @param domainId the domain id
     *
     * @return the domain
     *
     * @throws EntryNotFoundException If domainId is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public Domain getDomain(long domainId) throws PersistenceException {
        try {
            Connection conn = this.getConnection();

            try {
                Long sponsorId;
                String domainName;
                Boolean approved;

                // checks if the domain id exist or not, if not , throw exception
                ResultSet rs = query(conn, SQL_SELECT_DOMAIN_WITH_ID + domainId, null);

                try {
                    if (!rs.next()) {
                        throw new EntryNotFoundException("The domain record with the id: " + domainId + " does not exist",
                            new Long(domainId));
                    } else {
                        long sid = rs.getLong(FIELD_SPONSOR_ID);
                        if (rs.wasNull()) {
                            sponsorId = null;
                        } else {
                            sponsorId = new Long(sid);
                        }
                        domainName = rs.getString(FIELD_BASE_URL);
                        approved = (Boolean) rs.getObject(FIELD_IS_APPROVED);
                    }
                } finally {
                    close(rs);
                }

                // find all the domain's image list
                List images = new ArrayList();
                rs = query(conn, SQL_SELECT_IMAGE_WITH_DOMAIN_ID + domainId, null);

                try {
                    while (rs.next()) {
                        images.add(new ImageInfoImpl(new Long(rs.getLong(FIELD_ID)), rs.getLong(FIELD_DOWNLOAD_OBJ_ID),
                                rs.getString(FIELD_DESCRIPTION), (Boolean) rs.getObject(FIELD_IS_APPROVED)));
                    }
                } finally {
                    close(rs);
                }

                return new DomainImpl(new Long(domainId), sponsorId, domainName, approved,
                    (ImageInfo[]) images.toArray(new ImageInfo[images.size()]));
            } finally {
                close(conn);
            }
        } catch(PersistenceException e){
            throw e;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error create the connection from db connection factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in query the database while get the domain.", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in get domain with the id:" + domainId, e);
        }
    }

    /**
     * <p>
     * Returns the key text for the specified player's completions of the specified slots. The length of the returned array is
     * the same as the length of the slotIds argument, and their elements correspond: each string in the returned array is the
     * key text associated with the slot completion by the specified player of the slot whose ID appears at the same index in
     * the input slotIds. If the specified player has not completed any particular slot specified among the slot IDs then the
     * corresponding element or the returned array is null.
     * </p>
     *
     * @param playerId the player id
     * @param slotIds the slot ids
     *
     * @return the player's keys
     *
     * @throws EntryNotFoundException If playerId or any slotId is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If slotIds is null
     */
    public String[] getKeysForPlayer(long playerId, long[] slotIds)
        throws PersistenceException {
        Helper.checkNotNull(slotIds, "SlotIds");

        try {
            Connection conn = this.getConnection();

            try {
                // checks if the playerId exist or not
                checkPlayerNotExist(playerId, conn);

                // checks if the slotIds exist or not
                for (int i = 0; i < slotIds.length; i++) {
                    checkSlotNotExist(slotIds[i], conn);
                }

                // get the keyTexts, if not exist, use null value
                String[] keyTexts = new String[slotIds.length];

                for (int i = 0; i < slotIds.length; i++) {
                    ResultSet rs = query(conn, SQL_SELECT_PLYR_COMPLTD_SLOT,
                            new Object[] { new Long(slotIds[i]), new Long(playerId) });

                    try {
                        keyTexts[i] = rs.next() ? rs.getString(FIELD_KEY_TEXT) : null;
                    } finally {
                        close(rs);
                    }
                }

                return keyTexts;
            } finally {
                close(conn);
            }
        } catch(PersistenceException e){
            throw e;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error in create connection from database factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in query database when retrieve keyText for player.", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in getKeysForPlayer from database.", e);
        }
    }

    /**
     * <p>
     * Retrieves the PuzzleData associated with the specified puzzle ID.
     * </p>
     *
     * @param puzzleId the puzzle id
     *
     * @return the puzzle data
     *
     * @throws EntryNotFoundException If puzzleId is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public PuzzleData getPuzzle(long puzzleId) throws PersistenceException {
        try {
            Connection conn = this.getConnection();

            try {
                // 1.get the puzzle name, if not exist, throw exception
                String name;
                ResultSet rs = query(conn, SQL_SELECT_PUZZLE_WITH_ID + puzzleId, null);

                try {
                    if (!rs.next()) {
                        throw new EntryNotFoundException("The puzzle with the id:" + puzzleId + " does not exist.",
                            new Long(puzzleId));
                    } else {
                        name = rs.getString(FIELD_NAME);
                    }
                } finally {
                    close(rs);
                }

                // 2.get puzzle attributes map from 'puzzle_attribute' table
                Map attributes = new HashMap();

                rs = query(conn, SQL_SELECT_PUZZLE_ATTRIBUTE_WITH_ID + puzzleId, null);

                try {
                    while (rs.next()) {
                        attributes.put(rs.getString(FIELD_NAME), rs.getString(FIELD_ATTRIBUTE_VALUE));
                    }
                } finally {
                    close(rs);
                }

                // 3.get the puzzle resource map from 'puzzle_resource' table
                Map resources = new HashMap();
                rs = query(conn, SQL_SELECT_PUZZLE_RESOURCE_WITH_ID + puzzleId, null);

                try {
                    while (rs.next()) {
                        resources.put(rs.getString(FIELD_NAME),
                            new GeneralPuzzleResource(rs.getString(FIELD_NAME), rs.getString(FIELD_MEDIA_TYPE),
                                rs.getBytes(FIELD_CONTENT)));
                    }
                } finally {
                    close(rs);
                }

                // return the puzzleData instance with the name,attributes and resources
                return new GeneralPuzzleData(name, attributes, resources);
            } finally {
                close(conn);
            }
        } catch(PersistenceException e){
            throw e;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error in create connection from database factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in query database when retrieve puzzle.", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in getPuzzle from database.", e);
        }
    }

    /**
     * <p>
     * Increments the download count for the plugin identified by the specified name.
     * </p>
     *
     * @param pluginName the plugin name
     *
     * @throws EntryNotFoundException If pluginName is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If pluginName is null/empty
     */
    public void recordPluginDownload(String pluginName)
        throws PersistenceException {
        Helper.checkNotNullOrEmpty(pluginName, "PluginName");

        try {
            Connection conn = getConnection();

            try {
                // checks if the pluginName exists in the 'plugin_downloads'
                // table, if not throw exception
                if (!exist(conn, SQL_SELECT_PLUGIN_DOWNLOADS, new Object[] { pluginName })) {
                    throw new EntryNotFoundException("The plugin with the name:" + pluginName + " does not exist.", pluginName);
                }

                // increase the count filed in the 'plugin_downloads' table
                update(conn, SQL_UPDATE_PLUGIN_DOWNLOADS, new Object[] { pluginName });
            } finally {
                close(conn);
            }
        } catch(PersistenceException e){
            throw e;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error in create connection from database factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in query database when record pluginDownload.", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in recordPluginDownload in database.", e);
        }
    }

    /**
     * <p>
     * Records the specified player's registration for the specified game.
     * </p>
     *
     * @param playerId the player id
     * @param gameId the game id
     *
     * @throws EntryNotFoundException If playerID or gameId is not in persistence
     * @throws DuplicateEntryException If the player has been already registered for this game
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public void recordRegistration(long playerId, long gameId)
        throws PersistenceException {
        try {
            Connection conn = getConnection();

            try {
                // checks if the playerId exist in 'player' table, if not throw
                // exception
                checkPlayerNotExist(playerId, conn);

                // checks if the gameId exist in 'game' table ,if not throw
                // exception
                checkGameNotExist(gameId, conn);

                // checks if the gameId and the playerId already registered, if
                // yes throw exception
                if (exist(conn, SQL_SELECT_PLYR_REGSTRD_GAME_TWO_ID, new Object[] { new Long(gameId), new Long(playerId) })) {
                    throw new DuplicateEntryException("The gameId/playerId :" + gameId + "/" + playerId + " already registered.",
                        new Long(gameId));
                }

                // register the gameId/playerId into 'plyr_regstrd_game' table
                update(conn, SQL_INSERT_PLYR_REGSTRD_GAME, new Object[] { new Long(gameId), new Long(playerId) });
            } finally {
                close(conn);
            }
        } catch(PersistenceException e){
            throw e;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error in create connection from database factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in query database when record game Registration.", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in recordRegistration in database.", e);
        }
    }

    /**
     * <p>
     * Records the completion of the specified slot by the specified player at the specified date and time, and generates a key
     * for the player to associate with the completion.
     * </p>
     *
     * @param playerId the player id
     * @param slotId the slot id
     * @param date date of completion
     *
     * @return the slot completion entity
     *
     * @throws EntryNotFoundException If playerID or slotId is not in persistence
     * @throws DuplicateEntryException If the slot has already been completed for this player
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If date is null
     */
    public SlotCompletion recordSlotCompletion(long playerId, long slotId, java.util.Date date)
        throws PersistenceException {
        Helper.checkNotNull(date, "date");

        try {
            Connection conn = getConnection();

            try {
                // checks if the playerId exist in 'player' table, if not throw
                // exception
                checkPlayerNotExist(playerId, conn);

                // checks if the slotId exist in 'hosting_slot' table ,if not
                // throw exception
                checkSlotNotExist(slotId, conn);

                // check if the slot has already been completed for this player
                if (exist(conn, SQL_SELECT_PLYR_COMPLTD_SLOT, new Object[] { new Long(slotId), new Long(playerId) })) {
                    throw new DuplicateEntryException("The slot has already been completed for this player", new Long(slotId));
                }

                // generate the keyText and fill the stream
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                String name = randomStringImage.generateRandom(this.keyLength, this.keyLength, stream);
                long keyImageId = recordBinaryObject(name, mediaType, stream.toByteArray());

                // insert a record into plyr_compltd_slot to complete the
                // registration
                update(conn, SQL_INSERT_PLYR_COMPLTD_SLOT,
                    new Object[] { new Long(slotId), new Long(playerId), toSQLDate(date), name, new Long(keyImageId) });

                return new SlotCompletionImpl(slotId, playerId, date, name, keyImageId);
            } finally {
                close(conn);
            }
        } catch(PersistenceException e){
            throw e;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error in create connection from database factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in query database when record SlotCompletion.", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in recordSlotCompletion in database.", e);
        }
    }

    /**
     * <p>
     * Records the fact that the specified player has completed the specified game. Whether or not such a player actually wins
     * the game depends on whether others have already completed the game, and on administrative establishment of winner
     * eligibility.
     * </p>
     *
     * @param playerId the player id
     * @param gameId the game id
     *
     * @throws EntryNotFoundException If playerID or gameId is not in persistence
     * @throws DuplicateEntryException If the player has been already registered for this game
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public void recordGameCompletion(long playerId, long gameId)
        throws PersistenceException {
        try {
            Connection conn = getConnection();

            try {
                // checks if the playerId exist in 'player' table, if not throw
                // exception
                checkPlayerNotExist(playerId, conn);

                // checks if the gameId exist in 'game' table ,if not throw
                // exception
                checkGameNotExist(gameId, conn);

                // if the game for the player already complete(has record in
                // plyr_compltd_game table), throw exception
                if (exist(conn, SQL_SELECT_PLYR_COMPLTD_GAME, new Object[] { new Long(gameId), new Long(playerId) })) {
                    throw new DuplicateEntryException("The game for the player is complete.", new Long(gameId));
                }

                // insert a record into plyr_compltd_game
                update(conn, SQL_INSERT_PLYR_COMPLTD_GAME, new Object[] { new Long(gameId), new Long(playerId) });
            } finally {
                close(conn);
            }
        } catch(PersistenceException e){
            throw e;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error in create connection from database factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in query database when record GameCompletion.", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in recordGameCompletion in database.", e);
        }
    }

    /**
     * <p>
     * Records a binary object in the database, such as might later be retrieved by the custom DownloadSource. The ID assigned to
     * the binary object is returned.
     * </p>
     *
     * @param name the name of the object
     * @param mediaType the media type
     * @param content the content as a byte array
     *
     * @return the binary object
     *
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If name or mediaType is null/empty, or content is null
     */
    public long recordBinaryObject(String name, String mediaType, byte[] content)
        throws PersistenceException {
        Helper.checkNotNullOrEmpty(name, FIELD_NAME);
        Helper.checkNotNullOrEmpty(mediaType, FIELD_MEDIA_TYPE);
        Helper.checkNotNull(content, FIELD_CONTENT);

        try {
            Connection conn = getConnection();

            try {
                CallableStatement stmt = callableStatement(SP_RECORD_BINARY_OBJECT,
                        new Object[] { name, mediaType, content, FIELD_ID }, conn);

                try {
                    return stmt.getLong(4);
                } finally {
                    close(stmt);
                }
            } finally {
                close(conn);
            }
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error in create connection from database factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in query database when recordBinaryObject.", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in recordBinaryObject in database.", e);
        }
    }

    /**
     * <p>
     * Updates the persistent hosting slot information for the existing slots represented by the specified HostingSlot objects,
     * so that the persistent representation matches the provided objects. Nested DomainTarget objects may or may not already be
     * recorded in persistence; the component assumes that DomainTarget's with null IDs are new, and that others already exist
     * in the database. The component will assign IDs for new DomainTargets as needed. This method will also update the
     * following additional HostingSlot properties (only): sequence number, hosting start, hosting end, brain teaser IDs, puzzle
     * ID. It will return an array containing the revised hosting slots.
     * </p>
     *
     * @param slots the hosting slots to update
     *
     * @return the updated hosting slots
     *
     * @throws EntryNotFoundException If HsotingSlot.id or DomainTarget.id, if not null, is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If slots is null
     */
    public HostingSlot[] updateSlots(HostingSlot[] slots)
        throws PersistenceException {
        Helper.checkNotNullOrContainNullElement(slots, "Slots");

        // if the slot is empty, simply return
        if (slots.length == 0) {
            return slots;
        }

        try {
            Connection conn = getConnection();

            try {
                // iterate the slot array and checks if any of the slotId does
                // not exist
                for (int i = 0; i < slots.length; i++) {
                    checkSlotNotExist(slots[i].getId().longValue(), conn);
                }

                // the updated slots to return
                HostingSlot[] updatedSlots = new HostingSlot[slots.length];

                // iterate the slots and update the database
                for (int i = 0; i < slots.length; i++) {
                    // 1.update the hosting_slot table
                    update(conn, SQL_UPDATE_HOSTING_SLOT,
                        new Object[] {
                            new Long(slots[i].getSequenceNumber()), toSQLDate(slots[i].getHostingStart()),
                            toSQLDate(slots[i].getHostingEnd()), slots[i].getId()
                        });

                    // 2.update puzzle
                    this.deletePuzzle(slots[i].getId().longValue(), conn);

                    if (slots[i].getPuzzleId() != null) {
                        update(conn, SQL_INSERT_PUZZLE_FOR_SLOT, new Object[] { slots[i].getId(), slots[i].getPuzzleId() });
                    }

                    // 3. update brainteaser
                    long[] brainTeaserIds = slots[i].getBrainTeaserIds();
                    this.deleteBrainteaser(slots[i].getId().longValue(), conn);

                    if (brainTeaserIds != null) {
                        for (int j = 0; j < brainTeaserIds.length; j++) {
                            update(conn, SQL_INSERT_BRN_TSR_FOR_SLOT,
                                new Object[] { slots[i].getId(), new Long(j), new Long(brainTeaserIds[j]) });
                        }
                    }

                    // update DomainTarget array
                    DomainTarget[] targets = slots[i].getDomainTargets();
                    DomainTarget[] updatedTargets = slots[i].getDomainTargets();
                    Set targetIds = this.getTargetObjectIds(slots[i].getId().longValue(), conn);

                    for (int fix = 0; fix < targets.length; ++fix) {
                        if ((targets[fix].getId() != null) &&
                                !exist(conn, SQL_SELECT_TARGET_OBJECT_WITH_ID + targets[fix].getId().longValue(), null)) {
                            throw new EntryNotFoundException("The slot's DomainTarget with the id:" +
                                targets[fix].getId().longValue() + " does not exist.", targets[fix].getId());
                        } else {
                            targetIds.remove(targets[fix].getId());
                        }
                    }

                    // delete the remaining targets
                    this.deleteDomainTarget(targetIds, conn);

                    for (int j = 0; j < targets.length; j++) {
                        CallableStatement stmt = callableStatement(SP_CREATE_DOMAIN_TARGET,
                                new Object[] {
                                    targets[j].getId(), slots[i].getId(), new Integer(targets[j].getSequenceNumber()),
                                    targets[j].getUriPath(), targets[j].getIdentifierText(), targets[j].getIdentifierHash(),
                                    new Long(targets[j].getClueImageId()), FIELD_ID
                                }, conn);

                        try {
                            if (targets[j].getId() == null) {
                                updatedTargets[j] = new DomainTargetImpl(new Long(stmt.getLong(8)),
                                        targets[j].getSequenceNumber(), targets[j].getUriPath(), targets[j].getIdentifierText(),
                                        targets[j].getIdentifierHash(), targets[j].getClueImageId());
                            }
                        } finally {
                            close(stmt);
                        }
                    }

                    updatedSlots[i] = new HostingSlotImpl(slots[i].getId(), slots[i].getDomain(), slots[i].getImageId(),
                            slots[i].getBrainTeaserIds(), slots[i].getPuzzleId(), slots[i].getSequenceNumber(), updatedTargets,
                            slots[i].getWinningBid(), slots[i].getHostingStart(), slots[i].getHostingEnd(),
                            slots[i].getHostingBlockId());
                }

                return updatedSlots;
            } finally {
                close(conn);
            }
        } catch(PersistenceException e){
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("Failed to update slots", e);
        }
    }

    /**
     * get all target object ids with given slot id
     *
     * @param slotId the slot id
     * @param conn the connection
     *
     * @return a set of target ids for given slot id
     *
     * @throws SQLException to environment
     */
    private Set getTargetObjectIds(long slotId, Connection conn)
        throws SQLException {
        String sql = "SELECT id FROM target_object WHERE hosting_slot_id=?";
        ResultSet rs = query(conn, sql, new Object[] { new Long(slotId) });
        Set ret = new HashSet();

        try {
            while (rs.next()) {
                ret.add(new Long(rs.getLong(1)));
            }
        } finally {
            close(rs);
        }

        return ret;
    }

    /**
     * Delete the target object
     *
     * @param targetIds to delete
     * @param conn the connection
     *
     * @throws SQLException to environment
     */
    private void deleteDomainTarget(Set targetIds, Connection conn)
        throws SQLException {
        if ((targetIds == null) || targetIds.isEmpty()) {
            return;
        }

        StringBuffer sql = new StringBuffer("DELETE FROM target_object WHERE id IN (");

        for (Iterator it = targetIds.iterator(); it.hasNext();) {
            sql.append(it.next().toString());
            sql.append(",");
        }

        sql.deleteCharAt(sql.length() - 1);
        sql.append(')');

        // System.out.println(sql.toString());
        PreparedStatement ps = conn.prepareStatement(sql.toString());

        try {
            ps.execute();
        } finally {
            close(ps);
        }
    }

    /**
     * Delete the puzzle for given slot id
     *
     * @param slotId the slot id
     * @param conn the connections
     *
     * @throws SQLException Fails to query database, it gets thrown.
     */
    private void deletePuzzle(long slotId, Connection conn)
        throws SQLException {
        String sql = "delete from puzzle_for_slot where hosting_slot_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);

        try {
            ps.setLong(1, slotId);
            ps.execute();
        } finally {
            close(ps);
        }
    }

    /**
     * Delete the brainteaser for given slot id
     *
     * @param slotId the slot id
     * @param conn the connections
     *
     * @throws SQLException Fails to query database, it gets thrown.
     */
    private void deleteBrainteaser(long slotId, Connection conn)
        throws Exception {
        String sql = "delete from brn_tsr_for_slot where hosting_slot_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);

        try {
            ps.setLong(1, slotId);
            ps.execute();
        } finally {
            close(ps);
        }
    }

    /**
     * <p>
     * Updates the persistent domain information for the specified Domain object to match the Domain object, where the
     * appropriate persistent record is identified by the Domain's ID.
     * </p>
     *
     * @param domain domain to update
     *
     * @throws EntryNotFoundException If Domain.id or domain.sponsorId or ImageInfo.id, or imageInfo.downloadId if not null, is
     *         not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If domain is null or if the domain's id is null
     */
    public void updateDomain(Domain domain) throws PersistenceException {
        Helper.checkNotNull(domain, "Domain");
        Helper.checkNotNull(domain.getId(), "Domain.Id");

        try {
            Connection conn = this.getConnection();

            try {
                // checks if the domain to update exist , if not throw exception
                checkDomainNotExist(domain, conn);

                // if domain.sponsorId does not exist, throw EntryNotFoundException
                if (domain.getSponsorId() != null) {
                    if (!exist(conn, SQL_SELECT_SPONSOR_WITH_ID + domain.getSponsorId(), null)) {
                        throw new EntryNotFoundException("The sponsorId in the domain does not exist.",
                                                         domain.getSponsorId());
                    }
                }

                // update domain table
                updateWithNullApprove(conn, SQL_UPDATE_DOMAIN,
                    new Object[] { domain.getDomainName(), domain.isApproved(), domain.getId() });

                // update or insert the the imageInfo into the 'image' table
                ImageInfo[] images = domain.getImages();

                for (int i = 0; i < images.length; i++) {
                    // checks if the downloadID exist, if not, throw exception
                    checkDownloadDataNotExist(images[i].getDownloadId(), conn);

                    // if the imageId is not null, update it, else insert a new
                    // record
                    if (images[i].getId() != null) {
                        // if the image id to update does not exist, throw
                        // exception
                        if (!exist(conn, SQL_SELECT_IMAGE_WITH_ID + images[i].getId().longValue(), null)) {
                            throw new EntryNotFoundException("The image of the domain does not exist.", images[i].getId());
                        }

                        updateWithNullApprove(conn, SQL_UPDATE_IMAGE,
                            new Object[] { images[i].isApproved(), images[i].getDescription(), images[i].getId() });
                    } else {
                        updateWithNullApprove(conn, SQL_INSERT_IMAGE,
                            new Object[] {
                                domain.getId(), new Long(images[i].getDownloadId()), images[i].isApproved(),
                                images[i].getDescription()
                            });
                    }
                }
            } finally {
                close(conn);
            }
        } catch(PersistenceException e){
            throw e;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error create the connection from db connection factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in operation the database while update the domain.", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in update domain.", e);
        }
    }

    /**
     * <p>
     * Deletes the hosting slot having the specified ID.
     * </p>
     *
     * @param slotId slot id
     *
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public void deleteSlot(long slotId) throws PersistenceException {

	/*
	 * This version marks the slot as deleted, but doesn't actually remove it from the DB
	 */

        try {
            Connection conn = this.getConnection();

            try {
                Object[] param = new Object[] { new Long(slotId) };

                // delete the hosting_slot records
                update(conn, SQL_DELETE_HOSTING_SLOT, param);
            } finally {
                close(conn);
            }
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error create the connection from db connection factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in operation the database while delete the slot.", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in delete the slot.", e);
        }
    }

    /**
     * <p>
     * Looks up all distinct domains hosting any slot in any active game, and returns an array of Domain objects
     * representing the results.
     * </p>
     *
     * @return array of active domains.
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public Domain[] findActiveDomains() throws PersistenceException {
        try {
            Connection conn = getConnection();

            try {
                // get all the active domain IDs
                ResultSet rs = query(conn, SQL_SELECT_ACTIVE_DOMAINS, null);

                try {
                    // Load the Domain objects
                    List domains = new ArrayList();

                    while (rs.next()) {
                        domains.add(getDomain(rs.getLong(FIELD_DOMAINID)));
                    }

                    return (Domain[]) domains.toArray(new Domain[domains.size()]);
                } finally {
                    close(rs);
                }
            } finally {
                close(conn);
            }
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error create the connection from db connection factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in operation the database while get the active domain.", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in get the active domain.", e);
        }
    }

    /**
     * <p>
     * Looks up all ongoing games in which a domain matching the specified string is a host in a slot that the specified player
     * has not yet completed, and returns an array of all such games.
     * </p>
     *
     * @param domain the domain
     * @param playerId the player id
     *
     * @return array of games
     *
     * @throws EntryNotFoundException If there is no player with such an id in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If domain is null/empty
     */
    public Game[] findGamesByDomain(String domain, long playerId)
        throws PersistenceException {
        Helper.checkNotNullOrEmpty(domain, "Domain");

        try {
            List gameIds = new ArrayList();
            Connection conn = getConnection();
            List games;

            try {

                // checks if the player exist, if not throw exception
                checkPlayerNotExist(playerId, conn);

                ResultSet rs = query(conn, SQL_PS_SELECT_PLAYER_GAMES_BY_DOMAIN,
                        new Object[] { new Long(playerId), new Long(playerId), domain });

                try {
                    while (rs.next()) {
                        gameIds.add(new Long(rs.getLong(FIELD_GAME_ID)));
                    }
                } finally {
                    close(rs);
                }
            } finally {
                close(conn);
            }

            // get the distinct gameId and get the games array
            games = new ArrayList();

            for (Iterator it = gameIds.iterator(); it.hasNext();) {
                games.add(getGame(((Long) it.next()).longValue()));
            }

            return (Game[]) games.toArray(new Game[games.size()]);
        } catch(PersistenceException e){
            throw e;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error create the connection from db connection factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in operation the database while delete the slot.", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in delete the slot.", e);
        }
    }

    /**
     * <p>
     * Looks up all hosting slots completed by any player in the specified game,
     * and returns the results as an array of HostingSlot objects. Returned slots
     * are in ascending order by hosting block sequence number and hosting slot sequence number.
     * </p>
     *
     * @param gameId the game id
     *
     * @return array of hosting slots
     *
     * @throws EntryNotFoundException If gameId is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public HostingSlot[] findCompletedSlots(long gameId)
        throws PersistenceException {
        try {
            List slots = new ArrayList();
            Connection conn = getConnection();

            try {
                // checks if the game exists, if not throw exception
                checkGameNotExist(gameId, conn);

                // to get the distinct slotIds that has complete records with
                // the given gameId
                ResultSet rs = query(conn, SQL_SELECT_COMPLETE_SLOT_IN_GAME, new Object[] { new Long(gameId) });

                try {
                    while (rs.next()) {
                        slots.add(this.getSlot(rs.getLong(FIELD_ID)));
                    }
                } finally {
                    close(rs);
                }
            } finally {
                close(conn);
            }

            // return the records
            return (HostingSlot[]) slots.toArray(new HostingSlot[slots.size()]);
        } catch(PersistenceException e){
            throw e;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error create the connection from db connection factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in operation the database while get completed slot.", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in find Completed Slots.", e);
        }
    }

    /**
     * <p>
     * Looks up all recorded completion events that have the specified hosting slot in the specified game.
     * </p>
     *
     * @param gameId the game id
     * @param slotId the slot id
     *
     * @return array of slot competition events
     *
     * @throws EntryNotFoundException If gameId or slotId is not in persistence
     * @throws InvalidEntryException If slotId is not part of the game indicated by gameId
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public SlotCompletion[] findSlotCompletions(long gameId, long slotId)
        throws PersistenceException {
        try {
            Connection conn = getConnection();

            try {
                // checks if the gameId exists in the 'game' table
                checkGameNotExist(gameId, conn);
                // checks if the slotId exists in the 'hosting_slot' table
                checkSlotNotExist(slotId, conn);

                // checks if the slot is related to the game
                ResultSet rs = query(conn, SQL_SELECT_CHECK_GAME_ID_SLOT_ID,
                                     new Object[] { new Long(gameId), new Long(slotId) });

                try {
                    if (!rs.next()) {
                        throw new InvalidEntryException("The slotId:" + slotId +
                            " is not part of the game indicated by the gameId:" + gameId, new Long(slotId));
                    }
                } finally {
                    close(rs);
                }

                // query all Slot Completion records with the given gameId and slotId
                List slotCompletions = new ArrayList();
                rs = query(conn, SQL_SELECT_COMPLETE_SLOT, new Object[] { new Long(gameId), new Long(slotId) });

                try {
                    while (rs.next()) {
                        slotCompletions.add(new SlotCompletionImpl(rs.getLong(FIELD_HOSTING_SLOT_ID),
                                rs.getLong(FIELD_PLAYER_ID), rs.getTimestamp(FIELD_TIMESTAMP), rs.getString(FIELD_KEY_TEXT),
                                rs.getLong(FIELD_KEY_IMAGE_ID)));
                    }
                } finally {
                    close(rs);
                }

                return (SlotCompletion[]) slotCompletions.toArray(new SlotCompletion[slotCompletions.size()]);
            } finally {
                close(conn);
            }
        } catch(PersistenceException e){
            throw e;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error create the connection from db connection factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in operation the database while get completed slot.", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in find Completed Slots.", e);
        }
    }

    /**
     * <p>
     * Retrieves game information for games meeting the specified game status criteria.
     * </p>
     *
     * @param isStarted a Boolean having value true to restrict to games that have started or false to restrict to games that
     *        have not yet started; null to ignore whether games have started
     * @param isEnded a Boolean having value true to restrict to games that have ended or false to restrict to games that have
     *        not yet ended; null to ignore whether games have ended
     *
     * @return an array of Game objects representing the games found
     *
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If isStarted == false and isEnded == true (impossible combo)
     */
    public Game[] findGames(Boolean isStarted, Boolean isEnded)
        throws PersistenceException {
        if ((isStarted != null) && (isEnded != null) && !isStarted.booleanValue() && isEnded.booleanValue()) {
            throw new IllegalArgumentException("Not started but already ended. Impossible Combo.");
        }

        try {
            Connection conn = getConnection();

            try {
                // find sql to query the game ids
                String sql;

                if (isStarted == null) {
                    if (isEnded == null) {
                        sql = SQL_SELECT_GAME; // Revised
                    } else if (isEnded.booleanValue()) {
                        sql = SQL_SELECT_END_GAME; // Revised
                    } else {
                        sql = SQL_SELECT_NOT_END_GAME; // Revised
                    }
                } else if (isStarted.booleanValue()) {
                    if (isEnded == null) {
                        sql = SQL_SELECT_START_GAME; // Revised
                    } else if (isEnded.booleanValue()) {
                        sql = SQL_SELECT_START_END_GAME; // Revised
                    } else {
                        sql = SQL_SELECT_START_NOT_END_GAME; // Revised
                    }
                } else {
                    sql = SQL_SELECT_NOT_START_GAME; // Revised
                }

                // query the database
                List games = new ArrayList();
                ResultSet rs = query(conn, sql, null);

                try {
                    while (rs.next()) {
                        games.add(getGame(rs.getLong(FIELD_ID)));
                    }
                } finally {
                    close(rs);
                }

                return (Game[]) games.toArray(new Game[games.size()]);
            } finally {
                close(conn);
            }
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error create the connection from db connection factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in operation the database while find games.", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in find Games.", e);
        }
    }

    /**
     * <p>
     * Looks up all the games for which the specified player is registered, and returns an array of their IDs.
     * </p>
     *
     * @param playerId the player id
     *
     * @return the array of game ids
     *
     * @throws EntryNotFoundException If playerId is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public long[] findGameRegistrations(long playerId)
        throws PersistenceException {
        try {
            Connection conn = getConnection();

            try {
                // checks if the playerId exists in the 'player' table
                checkPlayerNotExist(playerId, conn);

                // query the registered game id from 'plyr_regstrd_game' table
                List gameIds = new ArrayList();
                ResultSet rs = query(conn, SQL_SELECT_PLYR_REGSTRD_GAME + playerId, null);

                try {
                    while (rs.next()) {
                        gameIds.add(new Long(rs.getLong(FIELD_ID)));
                    }
                } finally {
                    close(rs);
                }

                // return the game id array
                long[] ids = new long[gameIds.size()];

                for (int i = 0; i < ids.length; i++) {
                    ids[i] = ((Long) gameIds.get(i)).longValue();
                }

                return ids;
            } finally {
                close(conn);
            }
        } catch(PersistenceException e){
            throw e;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error create the connection from db connection factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in operation the database while find Game Registrations.", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in findGameRegistrations.", e);
        }
    }
    /**
     * <p>
     * Looks up all the games for which is completed by specified player , and returns an array of their IDs.
     * </p>
     *
     * @param playerId the player id
     *
     * @return the array of game ids
     *
     * @throws EntryNotFoundException If playerId is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public long[] findCompletedGameIds(long playerId) throws PersistenceException {
    	try {
            Connection conn = getConnection();

            try {
                // checks if the playerId exists in the 'player' table
                checkPlayerNotExist(playerId, conn);

                // query the registered game id from 'plyr_compltd_game' table
                List gameIds = new ArrayList();
                ResultSet rs = query(conn, SQL_SELECT_PLYR_COMPLETE_GAME + playerId, null);

                try {
                    while (rs.next()) {
                        gameIds.add(new Long(rs.getLong(FIELD_ID)));
                    }
                } finally {
                    close(rs);
                }

                // return the game id array
                long[] ids = new long[gameIds.size()];

                for (int i = 0; i < ids.length; i++) {
                    ids[i] = ((Long) gameIds.get(i)).longValue();
                }

                return ids;
            } finally {
                close(conn);
            }
        } catch(PersistenceException e){
            throw e;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error create the connection from db connection factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in operation the database while find Complete games by the player.", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in find complete games by the player.", e);
        }
	}
    
    /**
     * <p>
     * Looks up all domains associated with the specified sponsor and returns an array of Domain objects representing them.
     * </p>
     *
     * @param sponsorId the sponsor id
     *
     * @return array of domains
     *
     * @throws EntryNotFoundException If sponsorId is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public Domain[] findDomainsForSponsor(long sponsorId)
        throws PersistenceException {
        try {
            Connection conn = getConnection();

            try {
                // checks if the sponsorId exists in 'sponsor' table
                if (!exist(conn, SQL_SELECT_SPONSOR_WITH_ID + sponsorId, null)) {
                    throw new EntryNotFoundException("The sponsor with the id:" + sponsorId + " does not exist.",
                        new Long(sponsorId));
                }

                // query domain with the sponsorId
                List domains = new ArrayList();
                ResultSet rs = this.query(conn, SQL_SELECT_DOMAIN_WITH_SPONSOR_ID + sponsorId, null);

                try {
                    while (rs.next()) {
                        domains.add(getDomain(rs.getLong(FIELD_ID)));
                    }
                } finally {
                    close(rs);
                }

                return (Domain[]) domains.toArray(new Domain[domains.size()]);
            } finally {
                close(conn);
            }
        } catch(PersistenceException e){
            throw e;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error create the connection from db connection factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in query the database while find the domain for the sponsorId:." + sponsorId, e);
        } catch (Exception e) {
            throw new PersistenceException("Error in get domains with the sponsor:" + sponsorId, e);
        }
    }

    /**
     * <p>
     * Finds the first HostingSlot in the hosting sequence for the specified game that is assigned the specified domain and has
     * not yet been completed by the specified player.
     * </p>
     *
     * @param gameId the game id
     * @param playerId the player id
     * @param domain the domain
     *
     * @return the first hosting slot matching the specified criteria, or <code>null</code> if there is none
     *
     * @throws EntryNotFoundException If there is no player or game with such an id in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If domain is null/empty
     */
    public HostingSlot findSlotForDomain(long gameId, long playerId, String domain)
        throws PersistenceException {
        Helper.checkNotNullOrEmpty(domain, "Domain");

        try {
            Connection conn = getConnection();

            try {

                // check if the playerId exists in 'player' table
                checkPlayerNotExist(playerId, conn);

                // check if the gameId exists in the 'game' table
                checkGameNotExist(gameId, conn);

                ResultSet rs = query(conn, SQL_PS_SELECT_SLOT_FOR_DOMAIN,
                        new Object[] { new Long(gameId), domain, new Long(playerId) });

                try {
                    return rs.next() ? this.getSlot(rs.getLong(1)) : null;
                } finally {
                    close(rs);
                }
            } finally {
                close(conn);
            }
        } catch(PersistenceException e){
            throw e;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error create the connection from db connection factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in query the database while find the slot for the domain", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in find slot for the domain", e);
        }
    }

    /**
     * <p>
     * Provides information about all ball colors available to the application.
     * </p>
     *
     * @return array of ball colors
     *
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public BallColor[] findAllBallColors() throws PersistenceException {
        try {
            Connection conn = getConnection();

            try {
                // find all the ball_color records
                List colors = new ArrayList();
                ResultSet rs = query(conn, SQL_SELECT_FROM_BALL_COLOR, null);

                try {
                    while (rs.next()) {
                        colors.add(new BallColorImpl(new Long(rs.getLong(FIELD_ID)), rs.getString(FIELD_NAME),
                                rs.getLong(FIELD_DOWNLOAD_OBJ_ID)));
                    }

                    return (BallColor[]) colors.toArray(new BallColor[colors.size()]);
                } finally {
                    close(rs);
                }
            } finally {
                close(conn);
            }
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error create the connection from db connection factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in operation the database while find ballColor.", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in find ball color.", e);
        }
    }

    /**
     * <p>Returns the statistics for downloaded plugins. </p>
     *
     * @return a mapping from plugin name to number of plugin downloads. 
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public Map getPluginDownloadStats() throws PersistenceException {
        try {
            Connection conn = this.getConnection();
            Map stats = new LinkedHashMap();
            try {
                ResultSet rs = query(conn, SQL_SELECT_PLUGIN_DOWNLOADS_STATS, null);
                try {
                    while (rs.next()) {
                        stats.put(rs.getString("plugin_name"), new Integer(rs.getInt("count")));
                    }
                    return stats;
                } finally {
                    close(rs);
                }
            } finally {
                close(conn);
            }
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error in create connection from database factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in query database when retrieve keyText for player.", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in getKeysForPlayer from database.", e);
        }
    }

    /**
     * <p>
     * Sets the time of completion of the specified game to specified date.
     * </p>
     *
     * @param gameId the ID of a game to complete.
     * @param endDate the time of game completion.
     * @throws EntryNotFoundException if specified game does not exist.
     * @throws PersistenceException if there is any problem in the persistence layer.
     * @throws IllegalArgumentException If game completion date is null.
     */
    public void completeGame(long gameId, Date endDate) throws PersistenceException {
        Helper.checkNotNull(endDate, "End Date");

        try {
            Connection conn = this.getConnection();

            try {
                checkGameNotExist(gameId, conn);
                Object[] param = new Object[] {toSQLDate(endDate), new Long(gameId)};
                update(conn, SQL_COMPLETE_GAME, param);
            } finally {
                close(conn);
            }
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error create the connection from db connection factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in operation the database while delete the slot.", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in delete the slot.", e);
        }
    }

    /**
     * <p>
     * checks if the domain with the name exist, if not throw exception.
     * </p>
     *
     * @param domain the domain name, according to 'base_url' in the 'domain' table
     * @param conn database connection
     *
     * @throws SQLException fails to query database
     * @throws EntryNotFoundException if the domain with the name does not exist
     */
//    private void checkDomainNotExist(String domain, Connection conn)
//        throws SQLException, EntryNotFoundException {
//        if (!exist(conn, SQL_SELECT_DOMAIN_WITH_NAME, new Object[] { domain })) {
//            throw new EntryNotFoundException("The domain with the name:" + domain + " does not exist.", domain);
//        }
//    }

    /**
     * <p>
     * checks if the gameId exist in 'game' table ,if not throw exception.
     * </p>
     *
     * @param gameId the game.id
     * @param conn database connection
     *
     * @throws SQLException fail to query database
     * @throws EntryNotFoundException if the gameId does not exist in the 'game' table
     */
    private void checkGameNotExist(long gameId, Connection conn)
        throws SQLException, EntryNotFoundException {
        if (!exist(conn, SQL_SELECT_GAME_WITH_ID + gameId, null)) {
            throw new EntryNotFoundException("The game with id :" + gameId + " does not exists.", new Long(gameId));
        }
    }

    /**
     * <p>
     * checks if the playerId exist in 'player' table, if not throw exception.
     * </p>
     *
     * @param playerId the playerId
     * @param conn DB Connection
     *
     * @throws SQLException fail to query database
     * @throws EntryNotFoundException if the player does not exist
     */
    private void checkPlayerNotExist(long playerId, Connection conn)
        throws SQLException, EntryNotFoundException {
        if (!exist(conn, SQL_SELECT_PLAYER_WITH_ID + playerId, null)) {
            throw new EntryNotFoundException("The Player with the id:" + playerId + " does not exist.", new Long(playerId));
        }
    }

    /**
     * <p>
     * checks if the slot with the slotId exists, if not throw exception.
     * </p>
     *
     * @param slotId the hosting_slot.id
     * @param conn database connection
     *
     * @throws SQLException fails to query database
     * @throws EntryNotFoundException if the id does not exists
     */
    private void checkSlotNotExist(long slotId, Connection conn)
        throws SQLException, EntryNotFoundException {
        if (!exist(conn, SQL_SELECT_HOSTING_SLOT_WITH_ID + slotId, null)) {
            throw new EntryNotFoundException("The slot with the id:" + slotId + " does not exist.", new Long(slotId));
        }
    }

    /**
     * <p>
     * checks if the domain exists or not, if not throw exception.
     * </p>
     *
     * @param domain the Domain instance to check
     * @param conn database connection
     *
     * @throws SQLException fails to query database
     * @throws EntryNotFoundException if the domain does not exist
     */
    private void checkDomainNotExist(Domain domain, Connection conn)
        throws SQLException, EntryNotFoundException {
        if (!exist(conn, SQL_SELECT_DOMAIN_WITH_ID + domain.getId().longValue(), null)) {
            throw new EntryNotFoundException("The domain with the id:" + domain.getId().longValue() + " does not exist.",
                domain.getId());
        }
    }

    /**
     * <p>
     * Checks if the download id exist in the 'download_obj' table, if not ,throw exception.
     * </p>
     *
     * @param downloadId download id to check
     * @param conn database connection
     *
     * @throws SQLException fails to query database
     * @throws EntryNotFoundException if the download Id does not exist in the 'download_obj' table
     */
    private void checkDownloadDataNotExist(long downloadId, Connection conn)
        throws SQLException, EntryNotFoundException {
        if (!exist(conn, SQL_SELECT_DOWNLOAD_OBJ_WITH_ID + downloadId, null)) {
            throw new EntryNotFoundException("The downloadId of the image instance does not exist.", new Long(downloadId));
        }
    }

    /**
     * <p>
     * get the next sequence number of the given table. If not init, use the default value 1.
     * </p>
     *
     * @param conn database connection
     * @param table the table to get the next sequence number
     *
     * @return int value of next sequence number
     *
     * @throws SQLException fails to query database
     */
    private int getNextSequenceNumber(Connection conn, String table)
        throws SQLException {
        ResultSet rs = query(conn, "SELECT MAX(sequence_number) FROM " + table, null);

        try {
            return (rs.next() ? (rs.getInt(1) + 1) : 1);
        } finally {
            close(rs);
        }
    }

    /**
     * <p>
     * This method is going to convert the java.util.Date into java.sql.Date, the value should be same.
     * </p>
     *
     * @param date java.util.Date to convert
     *
     * @return java.sql.Date instance of the java.util.Date or null if the param is null
     */
    private Timestamp toSQLDate(java.util.Date date) {
        return (date == null) ? null : new Timestamp(date.getTime());
    }

    /**
     * <p>
     * Get database connection from the DBConnectionFactory,and assign it to this.connection. Since the DBConnectionFactory has
     * not developed a real connection pool as plug, it's recommended that this.connection should be shared by all the other
     * public methods that involve db operations, if it's not closed.
     * </p>
     * 
     * <p>
     * this method will be used by all the other public methods to get connection from db factory, that involve database
     * operations.
     * </p>
     *
     * @return Connection the open connection instance
     *
     * @throws DBConnectionException Fail to create connection through dbconnectionfactory, it gets thrown
     */
    private Connection getConnection() throws DBConnectionException {
        return (connectionName == null) ? connectionFactory.createConnection() : connectionFactory.createConnection(connectionName);
    }

    /**
     * <p>
     * Prepare a statement to execute. sql contains the sql statement, params contains the parameters needed to set to.
     * </p>
     *
     * @param sql the sql statement.
     * @param params parameters for the sql statement.
     * @param connection a connection to database.
     *
     * @return the PreparedStatement with the value set.
     *
     * @throws SQLException thrown by prepareStatement.
     */
    private PreparedStatement prepareStatement(String sql, Object[] params, Connection connection)
        throws SQLException {
        // create the prepareStatement with the clause
        PreparedStatement statement = connection.prepareStatement(sql);

        // set the parameter for statement
        if (params != null) {
            for (int i = 0; i < params.length; ++i) {
                if (params[i] == null) {
                    statement.setNull(i + 1, Types.TIMESTAMP);
                } else {
                    statement.setObject(i + 1, params[i]);
                }
            }
        }

        return statement;
    }

    /**
     * <p>
     * Prepare a callableStatement to execute. SQL contains the sql statement, params contains the parameters needed to set to.
     * </p>
     *
     * @param sql SQL clause to execute the stored procedure
     * @param params the parameters to set in the sql clause
     * @param connection the database Connection to execute the stored procedure
     *
     * @return prepared CallableStatement can be executed
     *
     * @throws SQLException if fails to prepared the callableStatement
     */
    private CallableStatement callableStatement(String sql, Object[] params, Connection connection)
        throws SQLException {
        CallableStatement statement = connection.prepareCall(sql);

        for (int i = 0; i < (params.length - 1); i++) {
            if (params[i] == null) {
                statement.setNull(i + 1, Types.BIGINT);
            } else {
                statement.setObject(i + 1, params[i]);
            }
        }

        statement.registerOutParameter(params.length, Types.BIGINT);
        statement.execute();

        return statement;
    }

    /**
     * <p>
     * Checks the database whether the param exists.
     * </p>
     *
     * @param conn Connection instance
     * @param sql the sql to query the database
     * @param parameters the param to check
     *
     * @return true if exists, otherwise false return
     *
     * @throws SQLException Fails to query database, it gets thrown.
     */
    private boolean exist(Connection conn, String sql, Object[] parameters)
        throws SQLException {
        ResultSet rs;

        if (parameters != null) {
            rs = prepareStatement(sql, parameters, conn).executeQuery();
        } else {
            rs = conn.createStatement().executeQuery(sql);
        }

        try {
            return rs.next();
        } finally {
            close(rs);
        }
    }

    /**
     * <p>
     * Insert a column into the database.
     * </p>
     *
     * @param conn Connection instance
     * @param sql the sql to insert into the database
     * @param parameters the param to check
     *
     * @return update result
     *
     * @throws SQLException If fail to update database, it gets thrown.
     */
    private int update(Connection conn, String sql, Object[] parameters)
        throws SQLException {
        PreparedStatement pstmt = this.prepareStatement(sql, parameters, conn);

        try {
            return pstmt.executeUpdate();
        } finally {
            close(pstmt);
        }
    }

    /**
     * <p>
     * Inserts a new record into the database and returns the auto-generated key.
     * </p>
     *
     * @param conn Connection instance
     * @param sql the sql to insert into the database
     * @param parameters the param to check
     * @return update result
     * @throws SQLException If fail to update database, it gets thrown.
     */
    private long insert(Connection conn, String sql, Object[] parameters) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        if (parameters != null) {
            for (int i = 0; i < parameters.length; ++i) {
                if (parameters[i] == null) {
                    statement.setNull(i + 1, Types.TIMESTAMP);
                } else {
                    statement.setObject(i + 1, parameters[i]);
                }
            }
        }

        ResultSet keys = null;
        try {
            statement.executeUpdate();
            keys = statement.getGeneratedKeys();
            keys.next();
            return keys.getLong(1);
        } finally {
            close(keys);
            close(statement);
        }
    }

    /**
     * <p>
     * Insert a column into the database.
     * </p>
     *
     * @param connection Connection instance
     * @param sql the sql to insert into the database
     * @param params the param to check
     *
     * @return update result
     *
     * @throws SQLException If fail to update database, it gets thrown.
     */
    private int updateWithNullApprove(Connection connection, String sql, Object[] params)
        throws SQLException {
        // create the prepareStatement with the clause
        PreparedStatement pstmt = connection.prepareStatement(sql);

        try {
            // set the parameter for statement
            if (params != null) {
                for (int i = 0; i < params.length; ++i) {
                    if (params[i] == null) {
                        pstmt.setNull(i + 1, Types.BIT);
                    } else {
                        pstmt.setObject(i + 1, params[i]);
                    }
                }
            }

            return pstmt.executeUpdate();
        } finally {
            pstmt.close();
        }
    }

    /**
     * <p>
     * Query the database with the sql and the given parameters, it will return the ResultSet.
     * </p>
     *
     * @param conn Connection instance
     * @param sql the sql clause to execute query
     * @param parameters the parameters needed by the sql
     *
     * @return ResultSet instance
     *
     * @throws SQLException If fails to execute query to database
     */
    private ResultSet query(Connection conn, String sql, Object[] parameters)
        throws SQLException {
        if (parameters != null) {
            return prepareStatement(sql, parameters, conn).executeQuery();
        } else {
            return conn.createStatement().executeQuery(sql);
        }
    }

    /**
     * <p>
     * Try to close the Resultset object if it is not null. Returns silently if any SQL error occurs.
     * </p>
     *
     * @param rs the ResultSet object.
     */
    private void close(ResultSet rs) {
        if (rs != null) {
            try {
                Statement stmt = rs.getStatement();

                rs.close();
                close(stmt);
            } catch (SQLException e) {
                // silently go on
            }
        }
    }

    /**
     * <p>
     * Try to close the statement object if it is not null. Returns silently if any SQL error occurs.
     * </p>
     *
     * @param stmt the statement objec
     */
    private void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // silently go on
            }
        }
    }

    /**
     * <p>
     * Try to close the Connection object if it is not null. Returns silently if any SQL error occurs.
     * </p>
     *
     * @param conn the Connection object.
     */
    private void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // silently go on
            }
        }
    }

    /**
     * <p>
     * Creates specified hosting slots. This method will persist the slots in hosting_slot table and return the
     * appropiate hosting slots. The domain targets provided with the specified slots will be persisted in target_object
     * table.
     * </p>
     *
     * @param slots a list of slots to create.
     * @return array of hosting slots.
     * @throws EntryNotFoundException if blockId or any bidId doesn't exist in the persistence
     * @throws InvalidEntryException if any bidId does not belong to the blockId
     * @throws PersistenceException if there is any problem in the persistence layer.
     * @throws IllegalArgumentException if bidIds is null
     */
    public HostingSlot[] createSlots(HostingSlot[] slots) throws PersistenceException {
        Helper.checkNotNull(slots, "slots");
        HostingSlot[] createdSlots = new HostingSlot[slots.length];
        try {
            Connection conn = this.getConnection();
            ResultSet rs = null;
            try {
                for (int i = 0; i < slots.length; i++) {
                    HostingSlot slot = slots[i];
                    long blockId = slot.getHostingBlockId();
                    long imageId = slot.getImageId();
                    int currentAmount = slot.getWinningBid();
                    // checks if the blockId exists in the 'hosting_block' table
                    if (!exist(conn, SQL_SELECT_HOSTING_BLOCK_WITH_ID + blockId, null)) {
                        throw new EntryNotFoundException("The block with the id: " + blockId + " does not exist.",
                                                         new Long(blockId));
                    }
                    // get the next sequenceNumber of the hosting_slot table
                    int sequenceNumber = getNextSequenceNumber(conn, TABLE_HOSTING_SLOT);
                    // retrieve the domain_id from the 'image' table
                    Domain domain = null;
                    rs = query(conn, SQL_SELECT_IMAGE_WITH_ID + imageId, null);
                    try {
                        if (rs.next()) {
                            // will throw an exception if no such domain is found:
                            domain = getDomain(rs.getLong(FIELD_DOMAIN_ID));
                        }
                    } finally {
                        close(rs);
                    }
                    // persist the hosting slot
                    long id = this.insert(conn,
                                          "INSERT INTO hosting_slot (hosting_block_id,sequence_number," +
                                          "hosting_start,hosting_end,is_deleted,image_id,hosting_payment) " +
                                          "values(?,?,null,null,0,?,?)",
                                          new Object[] {new Long(blockId), new Long(sequenceNumber),
                                                        new Long(imageId), new Integer(currentAmount)});
                    // Persist domain targets
                    DomainTarget[] targets = slot.getDomainTargets();
                    DomainTarget[] createdTargets = new DomainTarget[targets.length];
                    for (int j = 0; j < targets.length; j++) {
                        CallableStatement stmt = callableStatement(SP_CREATE_DOMAIN_TARGET,
                                new Object[] {
                                    null, new Long(id), new Integer(targets[j].getSequenceNumber()),
                                    targets[j].getUriPath(), targets[j].getIdentifierText(),
                                    targets[j].getIdentifierHash(), new Long(targets[j].getClueImageId()), FIELD_ID
                                }, conn);

                        try {
                            createdTargets[j] = new DomainTargetImpl(new Long(stmt.getLong(8)),
                                                                     targets[j].getSequenceNumber(),
                                                                     targets[j].getUriPath(),
                                                                     targets[j].getIdentifierText(),
                                                                     targets[j].getIdentifierHash(),
                                                                     targets[j].getClueImageId());
                        } finally {
                            close(stmt);
                        }
                    }
                    // create the HostingSlotImpl instance
                    createdSlots[i] = new HostingSlotImpl(new Long(id), domain, imageId, new long[0], null,
                                                          sequenceNumber++, createdTargets, currentAmount, null,
                                                          null, blockId);
                }
                return createdSlots;
            } finally {
                close(rs);
                close(conn);
            }
        } catch(PersistenceException e){
            throw e;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error in create connection from database factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in query/update database when creating HostingSlot.", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in creating Slot.", e);
        }
    }

    /**
     * <p>
     * Retrieves a ImageInfo object representing the image corresponding to the specified ID.
     * </p>
     *
     * @param imageId the image id.
     * @return the image.
     * @throws EntryNotFoundException If imageId is not in persistence.
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public ImageInfo getImage(long imageId) throws PersistenceException {
        try {
            Connection conn = this.getConnection();

            try {
                long downloadId;
                String description;
                Boolean approved;

                // checks if the image id exist or not, if not , throw exception
                ResultSet rs = query(conn, SQL_SELECT_IMAGE_WITH_ID + imageId, null);

                try {
                    if (!rs.next()) {
                        throw new EntryNotFoundException("The image record with the id: " + imageId + " does not exist",
                            new Long(imageId));
                    } else {
                        downloadId = rs.getLong(FIELD_DOWNLOAD_OBJ_ID);
                        description = rs.getString(FIELD_DESCRIPTION);
                        approved = (Boolean) rs.getObject(FIELD_IS_APPROVED);
                    }
                } finally {
                    close(rs);
                }

                return new ImageInfoImpl(new Long(imageId), downloadId, description, approved);
            } finally {
                close(conn);
            }
        } catch(PersistenceException e){
            throw e;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error create the connection from db connection factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in query the database while get the image.", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in get image with the id:" + imageId, e);
        }
    }

    /**
     * <p>
     * Looks up all approved domains and returns an array of Domain objects representing them.
     * </p>
     *
     * @return array of domains.
     * @throws EntryNotFoundException If sponsorId is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public Domain[] getApprovedDomains() throws PersistenceException {
        try {
            Connection conn = getConnection();

            try {
                // query domain with the sponsorId
                List domains = new ArrayList();
                ResultSet rs = this.query(conn, SQL_SELECT_APPROVED_DOMAINS, null);

                try {
                    while (rs.next()) {
                        domains.add(getDomain(rs.getLong(FIELD_ID)));
                    }
                } finally {
                    close(rs);
                }

                return (Domain[]) domains.toArray(new Domain[domains.size()]);
            } finally {
                close(conn);
            }
        } catch(PersistenceException e){
            throw e;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error create the connection from db connection factory.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error in query the database while find the approved domains", e);
        } catch (Exception e) {
            throw new PersistenceException("Error in get domains with the sponsor:", e);
        }
    }
}
