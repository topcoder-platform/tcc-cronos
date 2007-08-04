/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;

import com.orpheus.game.persistence.entities.BallColorImpl;
import com.orpheus.game.persistence.entities.DomainImpl;
import com.orpheus.game.persistence.entities.GameImpl;
import com.orpheus.game.persistence.entities.HostingBlockImpl;
import com.orpheus.game.persistence.entities.HostingBlockImplTests;
import com.orpheus.game.persistence.entities.ImageInfoImpl;
import com.orpheus.game.persistence.entities.ImageInfoImplUnitTests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.config.ConfigManager;

import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Defines helper methods used in unit tests of this component.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class TestHelper {

    /** constant represents the dbfactory config file. */
    public static final String DBFACTORY_CONFIG_XML = "DBFactory.xml";

    /** constant represents the objectFactory config file. */
    public static final String OBJECT_FACTORY_CONFIG_XML = "ObjectFactory.xml";

    /** config file for the component. */
    public static final String GAME_PERSISTENCE_CONFIG_FILE = "Game_Persistence_Config.xml";

    /** namespace for SQLServerGameDataDao. */
    public static final String DAO_NAMESPACE = "com.orpheus.game.persistence.SQLServerGameDataDAO";

    /**
     * local custom download namespace.
     */
    public static final String LOCAL_NAMESPACE = "com.orpheus.game.persistence.CustomDownloadSource.local";

    /**
     * remote custom download namespace.
     */
    public static final String REMOTE_NAMESPACE = "com.orpheus.game.persistence.CustomDownloadSource.remote";

    /**
     * database factory namespace.
     */
    public static final String DB_FACTORY_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * This private constructor prevents the creation of a new instance.
     */
    private TestHelper() {
    }

    /**
     * Gets the value of a private field in the given class. The field has the given name. The value is retrieved from
     * the given instance. If the instance is null, the field is a static field. If any error occurs, null is returned.
     * 
     * @param type
     *            the class which the private field belongs to
     * @param instance
     *            the instance which the private field belongs to
     * @param name
     *            the name of the private field to be retrieved
     * 
     * @return the value of the private field
     */
    public static Object getPrivateField(Class type, Object instance, String name) {
        Field field = null;
        Object obj = null;

        try {
            // get the reflection of the field
            field = type.getDeclaredField(name);

            // set the field accessible.
            field.setAccessible(true);

            // get the value
            obj = field.get(instance);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // reset the accessibility
                field.setAccessible(false);
            }
        }

        return obj;
    }

    /**
     * Sets the value of a private field in the given class.
     * 
     * @param type
     *            the class which the private field belongs to
     * @param instance
     *            the instance which the private field belongs to
     * @param name
     *            the name of the private field to be retrieved
     * @param value
     *            the value to set
     */
    public static void setPrivateField(Class type, Object instance, String name, Object value) {
        Field field = null;

        try {
            // get the reflection of the field
            field = type.getDeclaredField(name);

            // set the field accessible.
            field.setAccessible(true);

            // set the value
            field.set(instance, value);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // reset the accessibility
                field.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Add the config file.
     * </p>
     * 
     * @param fileName
     *            the config file to add
     * 
     * @throws Exception
     *             into JUnit
     */
    public static void addConfigFile(String fileName) throws Exception {
        ConfigManager.getInstance().add(fileName);
    }

    /**
     * This method clears all the namespaces from ConfigManager.
     * 
     * @throws Exception
     *             if any error occurs when clearing ConfigManager
     */
    public static void clearConfigManager() throws Exception {
        ConfigManager manager = ConfigManager.getInstance();

        for (Iterator iter = manager.getAllNamespaces(); iter.hasNext();) {
            manager.removeNamespace((String) iter.next());
        }
    }

    /**
     * clear the database.
     * 
     * @throws Exception
     *             into JUnit
     */
    public static void clearDatabase() throws Exception {
        Connection conn = null;

        try {
            clearConfigManager();
            addConfigFile(DBFACTORY_CONFIG_XML);

            DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(DB_FACTORY_NAMESPACE);
            conn = dbFactory.createConnection();
            clearDB(conn);
        } finally {
            if (conn != null) {
                conn.close();
            }

            clearConfigManager();
        }
    }

    /**
     * clear the database.
     * 
     * @param conn
     *            database connection
     * 
     * @throws SQLException
     *             fail to update database
     */
    private static void clearDB(Connection conn) throws SQLException {
        conn.createStatement().execute("DELETE FROM plugin_downloads");
        conn.createStatement().execute("DELETE FROM target_object");
        conn.createStatement().execute("DELETE FROM brn_tsr_for_slot");
        conn.createStatement().execute("DELETE FROM puzzle_for_slot");
        conn.createStatement().execute("DELETE FROM plyr_compltd_slot");
        conn.createStatement().execute("DELETE FROM plyr_compltd_game");
        conn.createStatement().execute("DELETE FROM plyr_regstrd_game");
        conn.createStatement().execute("DELETE FROM bid_for_slot");
        conn.createStatement().execute("DELETE FROM hosting_slot");
        conn.createStatement().execute("DELETE FROM effective_bid");
        conn.createStatement().execute("DELETE FROM bid");
        conn.createStatement().execute("DELETE FROM [image]");
        conn.createStatement().execute("DELETE FROM auction");
        conn.createStatement().execute("DELETE FROM hosting_block");
        conn.createStatement().execute("DELETE FROM game");
        conn.createStatement().execute("DELETE FROM ball_color");
        conn.createStatement().execute("DELETE FROM puzzle_attribute");
        conn.createStatement().execute("DELETE FROM puzzle_resource");
        conn.createStatement().execute("DELETE FROM puzzle");
        conn.createStatement().execute("DELETE FROM download_obj");
        conn.createStatement().execute("DELETE FROM domain");
        conn.createStatement().execute("DELETE FROM sponsor");
        conn.createStatement().execute("DELETE FROM player");
        conn.createStatement().execute("DELETE FROM contact_info");
        conn.createStatement().execute("DELETE FROM any_user");
    }

    /**
     * <p>
     * Insert a record into the 'contact_info' table.
     * </p>
     * 
     * @param conn
     *            Database connection
     * 
     * @return the new added record's id
     * 
     * @throws Exception
     *             fails to update database
     */
    public static long persistContactInfo(Connection conn) throws Exception {
        // insert a record into the contact_info table
        PreparedStatement stmt = conn
                .prepareStatement("INSERT INTO contact_info(first_name,last_name,address_1,city,state,postal_code, telephone,id,country)"
                        + " VALUES(?,?,?,?,?,?,?,?,?)");
        stmt.setString(1, "javier");
        stmt.setString(2, "invern");
        stmt.setString(3, "Topcoder");
        stmt.setString(4, "Boston");
        stmt.setString(5, "MM");
        stmt.setString(6, "93823-234");
        stmt.setString(7, "02928-2383");
        stmt.setLong(8, 1);
        stmt.setString(9, "USA");
        stmt.execute();

        stmt.close();

        // find the new added record's id
        long contactInfoId = 0;
        stmt = conn.prepareStatement("SELECT id FROM contact_info");

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            contactInfoId = rs.getLong("id");
        }

        rs.close();
        stmt.close();

        return contactInfoId;
    }

    /**
     * add a record into the any_user table with the given id.
     * 
     * @param conn
     *            database connection
     * @param id
     *            the id
     * 
     * @return the id
     * 
     * @throws SQLException
     *             fails to update database
     */
    public static long persistAnyUser(Connection conn, long id) throws SQLException {
        PreparedStatement stmt;
        // add record into 'any_user' table
        stmt = conn.prepareStatement("INSERT INTO any_user(handle,e_mail,passwd,is_active,id) VALUES(?,?,?,?,?)");
        stmt.setString(1, "ivern");
        stmt.setString(2, "ivern@topcoder.com");
        stmt.setString(3, "ivern");
        stmt.setBoolean(4, true);
        stmt.setLong(5, id);
        stmt.execute();

        return id;
    }

    /**
     * Insert a record into 'player' table.
     * 
     * @param conn
     *            database connection
     * @param contactInfoId
     *            'contact_info_id'
     * @param anyUserId
     *            'any_user_id'
     * 
     * @throws Exception
     *             fail to update database
     */
    public static void persistPlayer(Connection conn, long contactInfoId, long anyUserId) throws Exception {
        // add record into 'player' table
        PreparedStatement stmt = conn
                .prepareStatement("INSERT INTO player(any_user_id,contact_info_id,payment_pref) VALUES(?,?,?)");
        stmt.setLong(2, contactInfoId);
        stmt.setLong(1, anyUserId);
        stmt.setString(3, "wire transfer");
        stmt.execute();
        stmt.close();
    }

    /**
     * <p>
     * Insert a record into the the sponsor table. and return the new added record id.
     * </p>
     * 
     * @param conn
     *            database connection
     * @param contactInfoId
     *            contact_info_id
     * @param anyUserId
     *            any_user_id
     * 
     * @return the sponsorId
     * 
     * @throws SQLException
     *             fail to query database
     */
    public static long persistSponsor(Connection conn, long contactInfoId, long anyUserId) throws SQLException {
        PreparedStatement stmt;
        stmt = conn.prepareStatement("INSERT INTO sponsor(any_user_id,contact_info_id)VALUES(?,?)");
        stmt.setLong(1, anyUserId);
        stmt.setLong(2, contactInfoId);
        stmt.execute();
        stmt.close();

        stmt = conn.prepareStatement("SELECT * FROM sponsor");

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getLong("any_user_id");
        }

        return 0;
    }

    /**
     * <p>
     * Persist the two records into the 'download_obj' table.
     * </p>
     * 
     * @param conn
     *            database connection
     * 
     * @return List of downloadId
     * 
     * @throws SQLException
     *             fails to update database
     */
    public static List persistDownloadObj(Connection conn) throws SQLException {
        PreparedStatement stmt;
        stmt = conn.prepareStatement("INSERT INTO download_obj(media_type,suggested_name,content) VALUES(?,?,?)");
        stmt.setString(1, "html/text");
        stmt.setString(2, "html");
        stmt.setBytes(3, "This is the content.".getBytes());
        stmt.execute();

        stmt.setString(1, "html/text");
        stmt.setString(2, "logo");
        stmt.setBytes(3, "This is the logo.".getBytes());
        stmt.execute();
        stmt.close();

        List downloadId = new ArrayList();
        stmt = conn.prepareStatement("SELECT id FROM download_obj");

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            downloadId.add(new Long(rs.getLong("id")));
        }

        rs.close();
        stmt.close();

        return downloadId;
    }

    /**
     * persist two records into the ball_color table.
     * 
     * @param conn
     *            database connection
     * @param downloadId
     *            List of download ids
     * 
     * @return List of BallColor
     * 
     * @throws SQLException
     *             fails to update database
     */
    public static List persistColor(Connection conn, List downloadId) throws SQLException {
        PreparedStatement stmt;
        stmt = conn.prepareStatement("INSERT INTO ball_color(name,download_obj_id)VALUES(?,?)");

        for (int i = 0; i < downloadId.size(); i++) {
            stmt.setString(1, "colorName" + i);
            stmt.setLong(2, ((Long) downloadId.get(i)).longValue());
            stmt.execute();
        }

        stmt.close();

        // query database to get the colors
        List colors = new ArrayList();
        stmt = conn.prepareStatement("SELECT * FROM ball_color");

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            colors.add(new BallColorImpl(new Long(rs.getLong("id")), rs.getString("name"), rs
                    .getLong("download_obj_id")));
        }

        rs.close();
        stmt.close();

        return colors;
    }

    /**
     * insert data for testing.
     * 
     * @return List of data used in test
     * 
     * @throws Exception
     *             into Junit
     */
    public static List prepareDatabase() throws Exception {
        Connection conn = null;
        List results = new ArrayList();

        try {
            clearConfigManager();
            addConfigFile(DBFACTORY_CONFIG_XML);

            DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(DB_FACTORY_NAMESPACE);
            conn = dbFactory.createConnection();
            // clear the database first
            clearDB(conn);

            // add record into 'contact_info' table
            long contactInfoId = persistContactInfo(conn);

            // add record into 'any_user' table
            long anyUserId = persistAnyUser(conn, 1);

            // add record into 'player' table
            persistPlayer(conn, contactInfoId, anyUserId);

            results.add(new Long(anyUserId));

            // add record into 'sponsor' table
            results.add(new Long(persistSponsor(conn, contactInfoId, anyUserId)));

            // insert into the download_obj table
            List downloadId = persistDownloadObj(conn);

            // insert into the ball_Color table
            results.add(persistColor(conn, downloadId));

            results.add(downloadId);
            return results;
        } finally {
            if (conn != null) {
                conn.close();
            }

            clearConfigManager();
        }
    }

    /**
     * <p>
     * It is going to persist a record into the 'bid' table.
     * </p>
     * 
     * @param blockId
     *            the block id which the bid refers to
     * @param imageId
     *            image_id
     * @param maxAmount
     *            max_amount
     * @param date
     *            start_date
     * 
     * @return a array contains the bid id
     * 
     * @throws Exception
     *             fails to persist the bid
     */
    public static long[] persistBid(long blockId, long imageId, int maxAmount, Date date) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            clearConfigManager();
            addConfigFile(DBFACTORY_CONFIG_XML);

            DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(DB_FACTORY_NAMESPACE);
            conn = dbFactory.createConnection();
            stmt = conn
                    .prepareStatement("INSERT INTO auction(hosting_block_id,start_time,end_time,min_bid,item_count)"
                            + " VALUES(?,?,?,?,?)");
            stmt.setLong(1, blockId);
            stmt.setDate(2, new java.sql.Date(date.getTime()));
            stmt.setDate(3, new java.sql.Date(date.getTime()));
            stmt.setInt(4, 1);
            stmt.setInt(5, 1);
            stmt.execute();
            stmt.close();

            stmt = conn.prepareStatement("INSERT INTO bid(auction_id, image_id, max_amount, time) VALUES(?,?,?,?)");
            stmt.setLong(1, blockId);
            stmt.setLong(2, imageId);
            stmt.setInt(3, maxAmount);
            stmt.setDate(4, new java.sql.Date(date.getTime()));

            stmt.execute();
            stmt.close();

            List ids = new ArrayList();
            stmt = conn.prepareStatement("SELECT * FROM bid");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ids.add(new Long(rs.getLong("id")));
            }

            rs.close();
            stmt.close();

            long[] bidIds = new long[ids.size()];

            for (int i = 0; i < bidIds.length; i++) {
                bidIds[i] = ((Long) ids.get(i)).longValue();
            }

            return bidIds;
        } finally {
            if (conn != null) {
                conn.close();
            }

            clearConfigManager();
        }
    }

    /**
     * Persist a record into 'puzzle' table.
     * 
     * @param downloadId
     *            download_obj_id
     * @param puzzleName
     *            name of puzzle
     * 
     * @return puzzleid
     * 
     * @throws Exception
     *             fail to update database
     */
    public static long persistPuzzle(long downloadId, String puzzleName) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            clearConfigManager();
            addConfigFile(DBFACTORY_CONFIG_XML);

            DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(DB_FACTORY_NAMESPACE);
            conn = dbFactory.createConnection();
            // insert a record into the puzzle table
            stmt = conn.prepareStatement("INSERT INTO puzzle(name) VALUES(?)");
            stmt.setString(1, puzzleName);

            stmt.execute();
            stmt.close();

            long puzzleId = 0;
            stmt = conn.prepareStatement("SELECT * FROM puzzle");

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                puzzleId = rs.getLong("id");
            }

            rs.close();
            stmt.close();

            // insert a record into the puzzle_attribute table
            stmt = conn.prepareStatement("INSERT INTO puzzle_attribute(puzzle_id,name,value)VALUES(?,?,?)");
            stmt.setLong(1, puzzleId);
            stmt.setString(2, "att1");
            stmt.setString(3, "value1");

            stmt.execute();
            stmt.close();

            // insert a record into the puzzle_resource table
            stmt = conn.prepareStatement("INSERT INTO puzzle_resource(puzzle_id,name,download_obj_id)VALUES(?,?,?)");
            stmt.setLong(1, puzzleId);
            stmt.setString(2, "resource1");
            stmt.setLong(3, downloadId);
            stmt.execute();
            stmt.close();

            return puzzleId;
        } finally {
            if (conn != null) {
                conn.close();
            }

            clearConfigManager();
        }
    }

    /**
     * insert a record into 'plugin_download' table.
     * 
     * @param pluginName
     *            plugin_name table
     * 
     * @throws Exception
     *             fail to update database
     */
    public static void persistPluginDownload(String pluginName) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            clearConfigManager();
            addConfigFile(DBFACTORY_CONFIG_XML);

            DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(DB_FACTORY_NAMESPACE);
            conn = dbFactory.createConnection();
            // insert a record into the plugin_download table
            stmt = conn.prepareStatement("INSERT INTO plugin_downloads(plugin_name, count) VALUES(?,?)");
            stmt.setString(1, pluginName);
            stmt.setLong(2, 1);

            stmt.execute();
            stmt.close();
        } finally {
            if (conn != null) {
                conn.close();
            }

            clearConfigManager();
        }
    }

    /**
     * insert a record into 'hosting_slot' table.
     * 
     * @param bidId bid_id
     * @param sequenceNumber sequence_number
     * @param date hosting_start
     * @param puzzleId puzzle_id
     * @param downloadId download_id
     * @param blockId hosting_block_id
     * @param imageId image_id
     * @return slot_id
     * @throws Exception fails to update database
     */
    public static long persistSlot(long bidId, int sequenceNumber, java.util.Date date, long puzzleId, long downloadId,
                                   long blockId, long imageId)
            throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            clearConfigManager();
            addConfigFile(DBFACTORY_CONFIG_XML);

            DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(DB_FACTORY_NAMESPACE);
            conn = dbFactory.createConnection();
            // insert a record into the puzzle table
            stmt = conn.prepareStatement("INSERT INTO hosting_slot (hosting_block_id,sequence_number,hosting_start,is_deleted,image_id,hosting_payment) " +
                                         "VALUES (?,?,?,0,?,?)");
            stmt.setLong(1, blockId);
            stmt.setInt(2, sequenceNumber);
            stmt.setTimestamp(3, new Timestamp(date.getTime()));
            stmt.setLong(4, imageId);
            stmt.setInt(5, 2);

            stmt.execute();
            stmt.close();

            // find the added record's id
            long slotId = 0;
            stmt = conn.prepareStatement("SELECT * FROM hosting_slot");

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                slotId = rs.getLong("id");
            }

            // insert a record into 'bid_for_slot' table
            stmt = conn.prepareStatement("INSERT INTO bid_for_slot(bid_id,hosting_slot_id)VALUES(?,?)");
            stmt.setLong(1, bidId);
            stmt.setLong(2, slotId);
            stmt.execute();
            stmt.close();

            // insert a record into 'effective_bid' table
            stmt = conn.prepareStatement("INSERT INTO effective_bid(bid_id,current_amount)VALUES(?,?)");
            stmt.setLong(1, bidId);
            stmt.setInt(2, 2);
            stmt.execute();
            stmt.close();

            // inset a record into 'brn_tsr_for_slot' table
            stmt = conn
                    .prepareStatement("INSERT INTO brn_tsr_for_slot(hosting_slot_id,sequence_number,puzzle_id)VALUES(?,?,?)");
            stmt.setLong(1, slotId);
            stmt.setInt(2, 1);
            stmt.setLong(3, puzzleId);
            stmt.execute();
            stmt.close();

            // insert a record into 'puzzle_for_slot' table
            stmt = conn.prepareStatement("INSERT INTO puzzle_for_slot(hosting_slot_id, puzzle_id)VALUES(?,?)");
            stmt.setLong(1, slotId);
            stmt.setLong(2, puzzleId);
            stmt.execute();
            stmt.close();

            // insert a record into the 'target_object' table
            stmt = conn
                    .prepareStatement("INSERT INTO target_object (hosting_slot_id,sequence_number,uri_path,identifier_text,"
                            + "identifier_hash,clue_img_id)VALUES(?,?,?,?,?,?)");
            stmt.setLong(1, slotId);
            stmt.setInt(2, 1);
            stmt.setString(3, "path");
            stmt.setString(4, "text");
            stmt.setString(5, "hash");
            stmt.setLong(6, downloadId);
            stmt.execute();
            stmt.close();

            return slotId;
        } finally {
            if (conn != null) {
                conn.close();
            }

            clearConfigManager();
        }
    }

    /**
     * Return the ids of the table name.
     * 
     * @param tableName
     *            table name
     * @return the id of the table name
     */
    public static List getIds(String tableName) throws Exception {
        List ids = new ArrayList();
        Connection conn = null;
        try {
            clearConfigManager();
            addConfigFile(DBFACTORY_CONFIG_XML);

            DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(DB_FACTORY_NAMESPACE);
            conn = dbFactory.createConnection();

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select id from " + tableName);
            while (rs.next()) {
                ids.add(new Long(rs.getLong("id")));
            }
            rs.close();
            stmt.close();
            return ids;
        } finally {
            if (conn != null) {
                conn.close();
            }
            clearConfigManager();
        }

    }

    /**
     * create an array of ImageInfo.
     * 
     * @param colors
     *            array of BallColor
     * 
     * @return array of ImageInfo
     */
    public static ImageInfo[] getImages(BallColor[] colors) {
        ImageInfo[] images = new ImageInfo[2];
        images[0] = new ImageInfoImpl(null, colors[0].getImageId(), ImageInfoImplUnitTests.DESCRIPTION, new Boolean(
                true));
        images[1] = new ImageInfoImpl(null, colors[1].getImageId(), ImageInfoImplUnitTests.DESCRIPTION, null);

        return images;
    }

    /**
     * create an instance of Domain.
     * 
     * @param sponsorId
     *            sponsorId
     * @param domainName
     *            domainName
     * @param isApproved
     *            isApproved
     * @param images
     *            images
     * 
     * @return instance of domain
     */
    public static Domain getDomain(long sponsorId, String domainName, Boolean isApproved, ImageInfo[] images) {
        return new DomainImpl(null, new Long(sponsorId), domainName, isApproved, images);
    }

    /**
     * create instance of HostingBlock array.
     * 
     * @return blocks
     */
    public static HostingBlock[] getBlocks() {
        HostingBlock[] blocks = new HostingBlock[2];
        blocks[0] = new HostingBlockImpl(null, HostingBlockImplTests.SEQUENCE_NUMBER, new HostingSlot[0],
                HostingBlockImplTests.MAX_HOSTING_TIME_PER_SLOT);

        blocks[1] = new HostingBlockImpl(null, HostingBlockImplTests.SEQUENCE_NUMBER, new HostingSlot[0],
                HostingBlockImplTests.MAX_HOSTING_TIME_PER_SLOT + 1);

        return blocks;
    }

    /**
     * create instance of game for testing.
     * 
     * @param color
     *            color
     * @param keyCount
     *            keys_required
     * @param startDate
     *            start_date
     * @param blocks
     *            blocks
     * 
     * @return instance of game
     */
    public static Game getGameImplInstance(BallColor color, int keyCount, Date startDate, HostingBlock[] blocks) {
        return new GameImpl(null, color, keyCount, startDate, null, blocks, 1, 2, 3);
    }
}
