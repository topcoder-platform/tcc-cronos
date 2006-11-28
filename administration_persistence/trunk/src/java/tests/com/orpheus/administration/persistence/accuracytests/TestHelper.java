/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.persistence.accuracytests;

import com.orpheus.administration.persistence.Message;
import com.orpheus.administration.persistence.PendingWinner;
import com.orpheus.administration.persistence.impl.MessageImpl;
import com.orpheus.administration.persistence.impl.PendingWinnerImpl;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.puzzle.PuzzleData;
import com.topcoder.util.puzzle.baseimpl.*;

import junit.framework.Assert;

import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Defines helper methods used in unit tests of this component.
 */
public final class TestHelper {
    /** constant represents the dbfactory config file. */
    public static final String DBFACTORY_CONFIG_XML = "accuracy_tests/DBFactory.xml";

    /** constant represents the objectFactory config file. */
    public static final String OBJECT_FACTORY_CONFIG_XML = "accuracy_tests/ObjectFactory.xml";

    /** config file for the component. */
    public static final String ADMINISTRATION_PERSISTENCE_CONFIG_FILE = "accuracy_tests/Administration_Persistence_Config.xml";

    /** config file for the SearchBundleManager. */
    public static final String SEARCH_BUNDLE_CONFIG_FILE = "accuracy_tests/Search_Bundle_Config.xml";

    /** namespace for SQLServerMessageDao. */
    public static final String MESSAGE_DAO_NAMESPACE = "com.orpheus.administration.persistence.impl.SQLServerMessageDAO";

    /** namespace for SQLServerAdminDataDAO. */
    public static final String ADMIN_DATA_DAO_NAMESPACE = "com.orpheus.administration.persistence.impl.SQLServerAdminDataDAO";



    /** message attribute. */
    public static final String GUID = "guid";

    /** message attribute. */
    public static final String CATEGORY = "category";

    /** message attribute. */
    public static final String CONTENT_TYPE = "type";

    /** message attribute. */
    public static final String CONTENT = "message";

    /** PuzzleData array size. */
    public static final int PUZZLE_DATA_COUNT = 4;

    /** domainID. */
    public static final long DOMAIN_ID = 1;

    /** database factory namespace. */
    private static final String DB_FACTORY_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * RemoteOrpheusMessengerPlugin namespace.
     */
	public static final String REMOTE_MESSENGER_PLUGIN_NAMESPACE = "com.orpheus.administration.persistence.RemoteOrpheusMessengerPlugin";
	/**
     * LocalOrpheusMessengerPlugin namespace.
     */
	public static final String LOCAL_MESSENGER_PLUGIN_NAMESPACE = "com.orpheus.administration.persistence.LocalOrpheusMessengerPlugin";

	 /**
     * RemoteOrpheusMessengerPlugin namespace.
     */
	public static final String REMOTE_MESSENGER_DATA_SOURCE_NAMESPACE = "com.orpheus.administration.persistence.RemoteOrpheusMessageDataStore";
	/**
     * LocalOrpheusMessengerPlugin namespace.
     */
	public static final String LOCAL_MESSENGER_DATA_SOURCE_NAMESPACE = "com.orpheus.administration.persistence.LocalOrpheusMessageDataStore";
    /**
     * This private constructor prevents the creation of a new instance.
     */
    private TestHelper() {
    }

    /**
     * Gets the value of a private field in the given class. The field has the given name. The value is retrieved
     * from the given instance. If the instance is null, the field is a static field. If any error occurs, null is
     * returned.
     *
     * @param type the class which the private field belongs to
     * @param instance the instance which the private field belongs to
     * @param name the name of the private field to be retrieved
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
     * @param type the class which the private field belongs to
     * @param instance the instance which the private field belongs to
     * @param name the name of the private field to be retrieved
     * @param value the value to set
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
     * @param fileName the config file to add
     *
     * @throws Exception into JUnit
     */
    public static void addConfigFile(String fileName) throws Exception {
        ConfigManager.getInstance().add(fileName);
    }

    /**
     * This method clears all the namespaces from ConfigManager.
     *
     * @throws Exception if any error occurs when clearing ConfigManager
     */
    public static void clearConfigManager() throws Exception {
        ConfigManager manager = ConfigManager.getInstance();

        for (Iterator iter = manager.getAllNamespaces(); iter.hasNext();) {
            manager.removeNamespace((String) iter.next());
        }
    }

    /**
     * <p>
     * Get the Connection from DBConnectionFactory.
     * </p>
     *
     * @return the connection created from DBConnectionFactory.
     *
     * @throws Exception any exception during the create connection process.
     */
    public static Connection getConnection() throws Exception {
        // get the Connection instance
        DBConnectionFactory factory = new DBConnectionFactoryImpl(DB_FACTORY_NAMESPACE);

        return factory.createConnection();
    }

    /**
     * DOCUMENT ME!
     *
     * @param guid
     * @param category
     * @param contentType
     * @param content
     * @param timestamp
     *
     * @return
     */
    public static Message buildMessage(String guid, String category, String contentType, String content,
        Date timestamp) {
        return new MessageImpl(guid, category, contentType, content, timestamp);
    }

    /**
     * <p>
     * Clears the content in database.
     * </p>
     *
     * @param connection the database connection used to access database.
     *
     * @throws SQLException error occurs when access the database.
     */
    public static void clearDatabase(Connection connection)
        throws SQLException {
        Statement statement = connection.createStatement();

        try {
            statement.execute("DELETE FROM plyr_compltd_game");
            statement.execute("DELETE FROM plyr_regstrd_game");
            statement.execute("DELETE FROM plyr_won_game");
            statement.execute("DELETE FROM hosting_slot");
            statement.execute("DELETE FROM effective_bid");
            statement.execute("DELETE FROM bid");
            statement.execute("DELETE FROM [image]");
            statement.execute("DELETE FROM auction");
            statement.execute("DELETE FROM hosting_block");
            statement.execute("DELETE FROM game");

            statement.executeUpdate("DELETE FROM message");
            statement.executeUpdate("DELETE FROM puzzle_resource");
            statement.executeUpdate("DELETE FROM puzzle_attribute");
            statement.executeUpdate("DELETE FROM puzzle");
            statement.executeUpdate("DELETE FROM image");
            statement.executeUpdate("DELETE FROM download_obj");

            statement.executeUpdate("DELETE FROM domain");
            statement.executeUpdate("DELETE FROM sponsor");
            statement.executeUpdate("DELETE FROM player");
            statement.executeUpdate("DELETE FROM any_user");
        } finally {
            statement.close();
        }
    }

    /**
     * get message from database with the given guid.
     *
     * @param connection database connection
     * @param guid guid
     *
     * @return Message from database
     *
     * @throws Exception into JUnit
     */
    public static Message getMessageFromDB(Connection connection, String guid)
        throws Exception {
        Message msg = null;
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM message where guid = '" + guid + " '");

        while (rs.next()) {
            msg = new MessageImpl(rs.getString("GUID"), rs.getString("category"), rs.getString("content_type"),
                    rs.getString("content"), new Date(rs.getTimestamp("update_time").getTime()));
        }

        rs.close();
        stmt.close();

        return msg;
    }

    /**
     * verify the msg as the same as the toPersit object's value.
     *
     * @param expected Message
     * @param actual Message
     */
    public static void assertEquals(Message expected, Message actual) {
        Assert.assertEquals("The guid is not the same.", expected.getGuid(), actual.getGuid());
        Assert.assertEquals("The category is not the same.", expected.getCategory(), actual.getCategory());
        Assert.assertEquals("The message is not the same.", expected.getContent(), actual.getContent());
        Assert.assertEquals("The content type is not the same.", expected.getContentType(), actual.getContentType());
        Assert.assertEquals("The timestamp is not the same.", expected.getTimestamp().getTime(),
            actual.getTimestamp().getTime());
    }

    /**
     * <p>
     * Get the stored puzzle data from database.
     * </p>
     *
     * @param connection Database connection
     * @param puzzleIds puzzle_ids
     *
     * @return PuzzleData arary with the length of puzzleIds
     *
     * @throws SQLException fails to query database
     */
    public static PuzzleData[] getPuzzle(Connection connection, long[] puzzleIds)
        throws SQLException {
        PuzzleData[] datas = new PuzzleData[puzzleIds.length];
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            for (int i = 0; i < puzzleIds.length; i++) {
                String puzzleName = null;
                //GET THE PUZZLE NAME
                stmt = connection.prepareStatement("SELECT * FROM puzzle WHERE id = ?");
                stmt.setLong(1, puzzleIds[i]);

                rs = stmt.executeQuery();

                if (rs.next()) {
                    puzzleName = rs.getString("name");
                }

                rs.close();
                stmt.close();

                //GET THE ATTRIBUTES
                Map attributes = new HashMap();
                stmt = connection.prepareStatement("SELECT * FROM puzzle_attribute WHERE puzzle_id = ?");
                stmt.setLong(1, puzzleIds[i]);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    attributes.put(rs.getString("name"), rs.getString("value"));
                }

                rs.close();
                stmt.close();

                //get the resources
                Map resources = new HashMap();
                stmt = connection.prepareStatement(
                        "SELECT name, media_type, content FROM puzzle_resource, download_obj " +
                        "WHERE puzzle_resource.download_obj_id = download_obj.id AND " +
                        "puzzle_resource.puzzle_id = ?");
                stmt.setLong(1, puzzleIds[i]);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    resources.put(rs.getString("name"),
                        new GeneralPuzzleResource(rs.getString("name"), rs.getString("media_type"),
                            rs.getBytes("content")));
                }

                datas[i] = new GeneralPuzzleData(puzzleName, attributes, resources);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stmt != null) {
                stmt.close();
            }
        }

        return datas;
    }

    /**
     * Build PuzzleData instance for testing.
     *
     * @param count The numbers of puzzleData to create
     *
     * @return PuzzleData instance
     */
    public static PuzzleData[] buildPuzzleData(int count) {
        PuzzleData[] datas = new PuzzleData[count];

        for (int i = 0; i < count; i++) {
            Map attributes = new HashMap();
            attributes.put("type", "develop" + i);
            attributes.put("submitter", "agloite" + i);
            attributes.put("winner", "ivern" + i);

            Map resources = new HashMap();
            resources.put("submit", new GeneralPuzzleResource("dist", "java", ("code" + i).getBytes()));

            datas[i] = new GeneralPuzzleData("admin_persistence", attributes, resources);
        }

        return datas;
    }

    /**
     * assert two puzzleData.
     *
     * @param expected puzzle data array
     * @param actual puzzle data array
     */
    public static void assertEquals(PuzzleData[] expected, PuzzleData[] actual) {
        Assert.assertEquals("The size of two arrays are not the same.", expected.length, actual.length);

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    /**
     * <p>
     * Assert two puzzle data.
     * </p>
     *
     * @param expected PuzzleData
     * @param actual PuzzleData
     */
    public static void assertEquals(PuzzleData expected, PuzzleData actual) {
        Assert.assertEquals("The name is not the same.", expected.getName(), actual.getName());
        Assert.assertEquals("The type attribute is not the same.", expected.getAttribute("type"),
            actual.getAttribute("type"));
        Assert.assertEquals("The submitter attribute is not the same.", expected.getAttribute("submitter"),
            actual.getAttribute("submitter"));
        Assert.assertEquals("The winner attribute is not the same.", expected.getAttribute("winner"),
            actual.getAttribute("winner"));

        Assert.assertEquals("The resource's attribute is not the same.", expected.getResource("submit").getName(),
            expected.getResource("submit").getName());
        Assert.assertEquals("The resource's attribute is not the same.",
            expected.getResource("submit").getMediaType(), expected.getResource("submit").getMediaType());
        Assert.assertEquals("The resource's attribute is not the same.",
            new String(expected.getResource("submit").getData()), new String(expected.getResource("submit").getData()));
    }

    /**
     * persist a domain record into database.
     *
     * @param connection
     *
     * @return domainId
     */
    public static long persistDomain(Connection connection)
        throws SQLException {
        long domainId = 0;
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO any_user (id) VALUES(1)");
        statement.execute("INSERT INTO any_user (id) VALUES(2)");
        statement.execute("INSERT INTO sponsor (any_user_id, is_approved) VALUES(1,'Y')");
        statement.execute("INSERT INTO domain (sponsor_id, base_url,is_approved) VALUES(1,'/tc','N')");

        ResultSet rs = statement.executeQuery("SELECT id FROM domain");

        while (rs.next()) {
            domainId = rs.getLong("id");
        }

        rs.close();
        statement.close();

        return domainId;
    }

    /**
     * get the is_approved attribute from database.
     *
     * @param connection connection
     * @param domainId domain_id
     *
     * @return the value of is_approved
     *
     * @throws SQLException fail to query database
     */
    public static boolean getDomainApproval(Connection connection, long domainId)
        throws SQLException {
        boolean ret = false;
        ResultSet rs = connection.createStatement()
                                 .executeQuery("select is_approved from domain where id = " + domainId);

        if (rs.next()) {
            ret = rs.getString("is_approved").equalsIgnoreCase("Y");
        }

        rs.close();

        return ret;
    }

    /**
     * <p>Persist image record into database for testing.</p>
     *
     * @param connection Database Connection
     * @param domainId domainId
     *
     * @return image_id
     *
     * @throws SQLException fails to query database
     */
    public static long persistImage(Connection connection, long domainId)
        throws SQLException {
        //insert record into download_obj table
        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO download_obj(media_type,content)VALUES(?,?)");
        stmt.setString(1, "code");
        stmt.setBytes(2, "code".getBytes());
        stmt.execute();
        stmt.close();

        //get the downloadId
        long downloadId = 0;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT id FROM download_obj");

        if (rs.next()) {
            downloadId = rs.getLong("id");
        }

        rs.close();

        //insert record into image table
        long imageId = 0;
        statement.execute("INSERT image(domain_id,download_obj_id,is_approved,description)VALUES(" + domainId + ", " +
            downloadId + " ,'N', 'desc')");

        rs = statement.executeQuery("SELECT * FROM image");

        if (rs.next()) {
            imageId = rs.getLong("id");
        }

        rs.close();
        statement.close();

        return imageId;
    }

    /**
     * <p>Checks if the image is approved.</p>
     *
     * @param connection Database Connection
     * @param imageId image_id
     *
     * @return is_approved true or false
     *
     * @throws SQLException into Junit
     */
    public static boolean getImageApproval(Connection connection, long imageId)
        throws SQLException {
        boolean ret = false;
        ResultSet rs = connection.createStatement().executeQuery("select is_approved from image where id = " +
                imageId);

        if (rs.next()) {
            ret = rs.getString("is_approved").equalsIgnoreCase("Y");
        }

        rs.close();

        return ret;
    }

    /**
     * <p>Persist the game into database for testing.</p>
     *
     * @param connection Database Connection
     *
     * @return PendingWinner instance
     *
     * @throws SQLException into Junit
     */
    public static PendingWinner persistGame(Connection connection)
        throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO GAME(start_date) VALUES(?)");
        stmt.setDate(1, new java.sql.Date(1000));
        stmt.execute();
        stmt.close();

        //get the persisted game id
        long gameId = 0;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT id FROM game");

        if (rs.next()) {
            gameId = rs.getLong("id");
        }

        rs.close();

        //insert record into hosting_block
        stmt = connection.prepareStatement("INSERT INTO hosting_block(game_id) VALUES(?)");
        stmt.setLong(1, gameId);
        stmt.execute();

        //find the blockId
        long blockId = 0;
        rs = statement.executeQuery("SELECT id FROM hosting_block");

        if (rs.next()) {
            blockId = rs.getLong("id");
        }

        rs.close();

        //persist domain/image
        long domainId = persistDomain(connection);
        long imageId = persistImage(connection, domainId);

        //persist auction record
        stmt = connection.prepareStatement(
                "INSERT INTO auction(hosting_block_id,start_time,end_time,min_bid,bid_increment," +
                "item_count)VALUES(?,?,?,?,?,?)");

        stmt.setLong(1, blockId);
        stmt.setTimestamp(2, new Timestamp(100));
        stmt.setTimestamp(3, new Timestamp(200));
        stmt.setLong(4, 1L);
        stmt.setLong(5, 1L);
        stmt.setLong(6, 1L);
        stmt.execute();
        stmt.close();

        //insert into bid
        stmt = connection.prepareStatement("INSERT INTO bid(image_id,auction_id)VALUES(?,?)");
        stmt.setLong(1, imageId);
        stmt.setLong(2, blockId);
        stmt.execute();
        stmt.close();

        //get the bid_id
        long bidId = 0;
        rs = statement.executeQuery("SELECT id FROM bid");

        if (rs.next()) {
            bidId = rs.getLong("id");
        }

        rs.close();

        //insert into effective_bid
        stmt = connection.prepareStatement("INSERT INTO effective_bid(bid_id,current_amount)VALUES(?,?)");
        stmt.setLong(1, bidId);
        stmt.setLong(2, 100);
        stmt.execute();
        stmt.close();

        //insert record into hosting_slot
        stmt = connection.prepareStatement(
                "INSERT INTO hosting_slot(bid_id,sequence_number,hosting_start, hosting_end)VALUES(?,?,?,?)");
        stmt.setLong(1, bidId);
        stmt.setLong(2, 100);
        stmt.setTimestamp(3, new Timestamp(200));
        stmt.setTimestamp(4, new Timestamp(500));
        stmt.execute();
        stmt.close();

        //insert record into player table
        statement.execute("INSERT INTO player(any_user_id)VALUES(2)");

        //insert record into plyr_compltd_game
        statement.execute("INSERT INTO plyr_compltd_game(game_id, player_id, is_handled)VALUES( " + gameId +
            ", 2, 'N')");
        statement.close();

        return new PendingWinnerImpl(2, gameId, (int) ((1.0 - 0.15) * 100));
    }

    /**
     * <p>Get the won game payout and date.</p>
     *
     * @param connection database connection
     * @param winner pendingWinnder
     *
     * @return payout and the date
     *
     * @throws Exception into JUnit
     */
    public static List getPayout(Connection connection, PendingWinner winner)
        throws Exception {
        long payout = 0;
        Date date = null;
        ResultSet rs = connection.createStatement()
                                 .executeQuery("select * from plyr_won_game where game_id = " + winner.getGameId() +
                " and player_id = 2");

        if (rs.next()) {
            payout = rs.getLong("payout");
            date = new java.util.Date(rs.getTimestamp("date").getTime());
        }

        rs.close();

        List ret = new ArrayList();
        ret.add(date);
        ret.add(new Long(payout));

        return ret;
    }

    /**
     * get winner approve info from database.
     *
     * @param connection Connection
     * @param winner PendingWinner
     *
     * @return if it is completed
     *
     * @throws SQLException into Junit
     */
    public static boolean getWinnerApprove(Connection connection, PendingWinner winner)
        throws SQLException {
        boolean ret = false;
        ResultSet rs = connection.createStatement()
                                 .executeQuery("SELECT * FROM plyr_compltd_game WHERE game_id = " +
                winner.getGameId() + " and player_id = " + winner.getPlayerId());

        if (rs.next()) {
            ret = rs.getString("is_handled").equalsIgnoreCase("Y");
        }

        rs.close();

        return ret;
    }
}
