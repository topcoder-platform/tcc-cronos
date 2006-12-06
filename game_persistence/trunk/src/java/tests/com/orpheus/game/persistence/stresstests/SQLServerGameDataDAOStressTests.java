/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.stresstests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import com.orpheus.game.persistence.BallColor;
import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.GameDataDAO;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.ImageInfo;
import com.orpheus.game.persistence.dao.SQLServerGameDataDAO;
import com.orpheus.game.persistence.entities.BallColorImpl;
import com.orpheus.game.persistence.entities.DomainImpl;
import com.orpheus.game.persistence.entities.GameImpl;
import com.orpheus.game.persistence.entities.HostingBlockImpl;
import com.orpheus.game.persistence.entities.ImageInfoImpl;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;


/**
 * <p>Stress test fixture for <code>SQLServerGameDataDAO</code> class.</p>
 *
 * @author Thinfox
 * @version 1.0
 */
public class SQLServerGameDataDAOStressTests extends TestCase {
    /** The number of invocations for stress test. */
    private static final int TOTAL_INVOCATION_COUNT = 50;

    /** database factory namespace. */
    private static final String DB_FACTORY_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /** namespace for SQLServerGameDataDao. */
    private static final String DAO_NAMESPACE = "com.orpheus.game.persistence.SQLServerGameDataDAO";

    /** constant represents the dbfactory config file. */
    private static final String DBFACTORY_CONFIG_XML = "stress/DBFactory.xml";

    /** config file for the component. */
    private static final String GAME_PERSISTENCE_CONFIG_FILE = "stress/Game_Persistence_Config.xml";

    /** constant represents the objectFactory config file. */
    private static final String OBJECT_FACTORY_CONFIG_XML = "stress/ObjectFactory.xml";

    /** The game startDate used for tests. */
    private Date gameStartDate = null;

    /** The Domain instance used for tests. */
    private Domain domain = null;

    /** The Game instance used for tests. */
    private Game game = null;

    /** The SQLServerGameDataDAO instance on which to perform tests. */
    private GameDataDAO dao = null;

    /** the ballColor list used for tests. */
    private List colors = null;

    /** The bid ids used for tests. */
    private long[] bidIds = null;

    /** The keyCount used for tests. */
    private int keyCount = 1;

    /** The slotId used for tests. */
    private long slotId = 1;

    /**
     * <p>Test the addBlock method.</p>
     *
     * @throws Exception into Junit
     */
    public void testAddBlock() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.addBlock(game.getId().longValue(), 1000);
        }

        System.out.println("Testing addBlock method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * <p>Test the createDomain method.</p>
     *
     * @throws Exception into Junit
     */
    public void testCreateDomain() throws Exception {
        ImageInfo[] images = getImages((BallColor[]) colors.toArray(new BallColor[0]));

        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.createDomain(getDomain(1, "domainName", new Boolean(true), images));
        }

        System.out.println("Testing createDomain method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * Test the createGame(game) method.
     *
     * @throws Exception into Junit
     */
    public void testCreateGame() throws Exception {
        HostingBlock[] blocks = getBlocks();

        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.createGame(getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    blocks));
        }

        System.out.println("Testing createGame method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * <p>Test createSlots(blockId,bidIds) method.</p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateSlot() throws Exception {
        long blockId = game.getBlocks()[0].getId().longValue();

        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.createSlots(blockId, bidIds);
        }

        System.out.println("Testing createSlot method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * test the deleteSlot method. it is an accuracy test case.
     *
     * @throws Exception into Junit
     */
    public void testDeleteSlot() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.deleteSlot(slotId);
        }

        System.out.println("Testing deleteSlot method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * <p>Test the findActiveDomains method.</p>
     *
     * @throws Exception to JUnit
     */
    public void testFindActiveDomains() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.findActiveDomains();
        }

        System.out.println("Testing findActiveDomains method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * <p>Test the findAllBallColors method.</p>
     *
     * @throws Exception to JUnit
     */
    public void testFindAllBallColors() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.findAllBallColors();
        }

        System.out.println("Testing findAllBallColors method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * <p>test the findCompletedSlots method.</p>
     *
     * @throws Exception to JUnit
     */
    public void testFindCompletedSlots() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.findCompletedSlots(game.getId().longValue());
        }

        System.out.println("Testing findCompletedSlots method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * <p>Test the findDomainsForSponsor method.</p>
     *
     * @throws Exception to JUnit
     */
    public void testFindDomainsForSponsor() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.findDomainsForSponsor(i + 1);
        }

        System.out.println("Testing findDomainsForSponsor method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * Test the findGameRegistrations method.
     *
     * @throws Exception to JUnit
     */
    public void testFindGameRegistrations() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.findGameRegistrations(i + 1);
        }

        System.out.println("Testing findGameRegistrations method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * Test findGames(isStarted,isEnded) method.
     *
     * @throws Exception to JUnit
     */
    public void testFindGames() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.findGames(null, null);
        }

        System.out.println("Testing findGames method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * <p>Test the findGamesByDomain method.</p>
     *
     * @throws Exception to JUnit
     */
    public void testFindGamesByDomain() throws Exception {
        dao.createDomain(getDomain(1, "domainName", new Boolean(true),
                getImages((BallColor[]) colors.toArray(new BallColor[0]))));

        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.findGamesByDomain("domainName", i + 1);
        }

        System.out.println("Testing findGamesByDomain method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * <p>Test the findSlotCompletions method.</p>
     *
     * @throws Exception into Junit
     */
    public void testFindSlotCompletions() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.findSlotCompletions(game.getId().longValue(), slotId);
        }

        System.out.println("Testing findSlotCompletions method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * <p>Test findSlotForDomain(long gameId, long playerId, String domain) method.</p>
     *
     * @throws Exception to JUnit
     */
    public void testFindSlotForDomain() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.findSlotForDomain(game.getId().longValue(), i + 1, "domainName");
        }

        System.out.println("Testing findSlotForDomain method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * <p>Test getBlock method.</p>
     *
     * @throws Exception into Junit
     */
    public void testGetBlock() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.getBlock(game.getBlocks()[0].getId().longValue());
        }

        System.out.println("Testing getBlock method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * <p>Test the getDomain(domainId) method.</p>
     *
     * @throws Exception into Junit
     */
    public void testGetDomain() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.getDomain(domain.getId().longValue());
        }

        System.out.println("Testing getDomain method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * <p>Test the getDownloadData method.</p>
     *
     * @throws Exception to JUnit
     */
    public void testGetDownloadData() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.getDownloadData(((BallColor) this.colors.get(0)).getImageId());
        }

        System.out.println("Testing getDownloadData method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * <p>Test the getGame method.</p>
     *
     * @throws Exception into Junit
     */
    public void testGetGame() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.getGame(game.getId().longValue());
        }

        System.out.println("Testing getGame method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * <p>Test getKeysForPlayer(playerId, slotIds) method.</p>
     *
     * @throws Exception into Junit
     */
    public void testGetKeysForPlayer() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.getKeysForPlayer(1, new long[] {slotId});
        }

        System.out.println("Testing getKeysForPlayer method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * <p>Test getPuzzle(puzzleId) method.</p>
     *
     * @throws Exception into Junit
     */
    public void testGetPuzzle() throws Exception {
        long puzzleId = persistPuzzle(((BallColor) this.colors.get(0)).getImageId(), "puzzleName");

        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.getPuzzle(puzzleId);
        }

        System.out.println("Testing getPuzzle method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * <p>Test the getSlot method.</p>
     *
     * @throws Exception into Junit
     */
    public void testGetSlot() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.getSlot(slotId);
        }

        System.out.println("Testing getSlot method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * <p>Test the recordBinaryObject method.</p>
     *
     * @throws Exception to JUnit
     */
    public void testRecordBinaryObject() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.recordBinaryObject("name", "type", "content".getBytes());
        }

        System.out.println("Testing recordBinaryObject method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * Test the recordGameCompletion(playerId, gameId) method.
     *
     * @throws Exception to JUnit
     */
    public void testRecordGameCompletion() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.recordGameCompletion(i + 1, game.getId().longValue());
        }

        System.out.println("Testing recordGameCompletion method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * <p>Test the recordPluginDownload method.</p>
     *
     * @throws Exception to JUnit
     */
    public void testRecordPluginDownload() throws Exception {
        persistPluginDownload("pluginName");

        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.recordPluginDownload("pluginName");
        }

        System.out.println("Testing recordPluginDownload method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * Test the recordRegistration(playerId, gameId) method.
     *
     * @throws Exception to JUnit
     */
    public void testRecordRegistration() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.recordRegistration(i + 1, game.getId().longValue());
        }

        System.out.println("Testing recordRegistration method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * <p>Test recordSlotCompletion(playerId, slotId, date).</p>
     *
     * @throws Exception to JUnit
     */
    public void testRecordSlotCompletion() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.recordSlotCompletion(i + 1, slotId, new Date());
        }

        System.out.println("Testing recordSlotCompletion method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * test updateSlots(HostingSlot[] slots) method.
     *
     * @throws Exception into Junit
     */
    public void testUpateSlot() throws Exception {
        HostingSlot slot = dao.getSlot(slotId);

        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.updateSlots(new HostingSlot[] {slot});
        }

        System.out.println("Testing updateSlot method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * <p>Test the updateDomain(domain) method.</p>
     *
     * @throws Exception into Junit
     */
    public void testUpdateDomain() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            dao.updateDomain(domain);
        }

        System.out.println("Testing updateDomain method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * Sets up the test environment.
     *
     * @throws Exception into Junit
     */
    protected void setUp() throws Exception {
        clearConfigManager();

        addConfigFile(DBFACTORY_CONFIG_XML);
        addConfigFile(OBJECT_FACTORY_CONFIG_XML);
        addConfigFile(GAME_PERSISTENCE_CONFIG_FILE);

        List results = prepareDatabase();

        colors = (List) results.get(0);
        gameStartDate = new Date();

        dao = new SQLServerGameDataDAO(DAO_NAMESPACE);

        HostingBlock[] blocks = getBlocks();
        game = dao.createGame(getGameImplInstance((BallColor) colors.get(0), keyCount,
                    gameStartDate, blocks));

        ImageInfo[] images = getImages((BallColor[]) colors.toArray(new BallColor[0]));

        domain = dao.createDomain(getDomain(1, "domainName", new Boolean(true), images));

        bidIds = persistBid(game.getBlocks()[0].getId().longValue(),
                domain.getImages()[0].getId().longValue(), 1, new Date());

        long downloadId = ((BallColor) this.colors.get(0)).getImageId();

        long puzzleId = persistPuzzle(downloadId, "puzzleName");

        Date hostingStart = new Date();
        slotId = persistSlot(bidIds[0], 1, hostingStart, puzzleId, downloadId);
    }

    /**
     * Cleans up the environement.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        clearDatabase();
        clearConfigManager();
    }

    /**
     * <p>Add the config file.</p>
     *
     * @param fileName the config file to add
     *
     * @throws Exception into JUnit
     */
    private void addConfigFile(String fileName) throws Exception {
        ConfigManager.getInstance().add(fileName);
    }

    /**
     * This method clears all the namespaces from ConfigManager.
     *
     * @throws Exception if any error occurs when clearing ConfigManager
     */
    private void clearConfigManager() throws Exception {
        ConfigManager manager = ConfigManager.getInstance();

        for (Iterator iter = manager.getAllNamespaces(); iter.hasNext();) {
            manager.removeNamespace((String) iter.next());
        }
    }

    /**
     * clear the database.
     *
     * @param conn database connection
     *
     * @throws SQLException fail to update database
     */
    private void clearDB(Connection conn) throws SQLException {
        conn.createStatement().execute("DELETE FROM plugin_downloads");
        conn.createStatement().execute("DELETE FROM target_object");
        conn.createStatement().execute("DELETE FROM brn_tsr_for_slot");
        conn.createStatement().execute("DELETE FROM puzzle_for_slot");
        conn.createStatement().execute("DELETE FROM plyr_compltd_slot");
        conn.createStatement().execute("DELETE FROM plyr_compltd_game");
        conn.createStatement().execute("DELETE FROM plyr_regstrd_game");
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
        conn.createStatement().execute("DELETE FROM admin");
        conn.createStatement().execute("DELETE FROM contact_info");
        conn.createStatement().execute("DELETE FROM any_user");
    }

    /**
     * clear the database.
     *
     * @throws Exception into JUnit
     */
    private void clearDatabase() throws Exception {
        Connection conn = null;

        try {
            DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(DB_FACTORY_NAMESPACE);
            conn = dbFactory.createConnection();
            clearDB(conn);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * create instance of HostingBlock array.
     *
     * @return blocks
     */
    private HostingBlock[] getBlocks() {
        HostingBlock[] blocks = new HostingBlock[2];
        blocks[0] = new HostingBlockImpl(null, 1, new HostingSlot[0], 1000);

        blocks[1] = new HostingBlockImpl(null, 1, new HostingSlot[0], 2000);

        return blocks;
    }

    /**
     * create an instance of Domain.
     *
     * @param sponsorId sponsorId
     * @param domainName domainName
     * @param isApproved isApproved
     * @param images images
     *
     * @return instance of domain
     */
    private Domain getDomain(long sponsorId, String domainName, Boolean isApproved,
        ImageInfo[] images) {
        return new DomainImpl(null, sponsorId, domainName, isApproved, images);
    }

    /**
     * create instance of game for testing.
     *
     * @param color color
     * @param keyCount keys_required
     * @param startDate start_date
     * @param blocks blocks
     *
     * @return instance of game
     */
    private Game getGameImplInstance(BallColor color, int keyCount, Date startDate,
        HostingBlock[] blocks) {
        return new GameImpl(null, color, keyCount, startDate, null, blocks);
    }

    /**
     * create an array of ImageInfo.
     *
     * @param colors array of BallColor
     *
     * @return array of ImageInfo
     */
    private ImageInfo[] getImages(BallColor[] colors) {
        ImageInfo[] images = new ImageInfo[2];
        images[0] = new ImageInfoImpl(null, colors[0].getImageId(), "image1", new Boolean(true));
        images[1] = new ImageInfoImpl(null, colors[1].getImageId(), "image2", new Boolean(true));

        return images;
    }

    /**
     * add a record into the any_user table with the given id.
     *
     * @param conn database connection
     * @param id the id
     *
     * @return the id
     *
     * @throws SQLException fails to update database
     */
    private long persistAnyUser(Connection conn, long id)
        throws SQLException {
        PreparedStatement stmt;
        // add record into 'any_user' table
        stmt = conn.prepareStatement(
                "INSERT INTO any_user(handle,e_mail,passwd,is_active,id) VALUES(?,?,?,?,?)");
        stmt.setString(1, "ivern");
        stmt.setString(2, "ivern@topcoder.com");
        stmt.setString(3, "ivern");
        stmt.setBoolean(4, true);
        stmt.setLong(5, id);
        stmt.execute();

        return id;
    }

    /**
     * <p>It is going to persist a record into the 'bid' table.</p>
     *
     * @param blockId the block id which the bid refers to
     * @param imageId image_id
     * @param maxAmount max_amount
     * @param date start_date
     *
     * @return a array contains the bid id
     *
     * @throws Exception fails to persist the bid
     */
    private long[] persistBid(long blockId, long imageId, int maxAmount, Date date)
        throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(DB_FACTORY_NAMESPACE);
            conn = dbFactory.createConnection();
            stmt = conn.prepareStatement(
                    "INSERT INTO auction(hosting_block_id,start_time,end_time,min_bid,bid_increment,item_count)"
                    + " VALUES(?,?,?,?,?,?)");
            stmt.setLong(1, blockId);
            stmt.setDate(2, new java.sql.Date(date.getTime()));
            stmt.setDate(3, new java.sql.Date(date.getTime()));
            stmt.setInt(4, 1);
            stmt.setInt(5, 1);
            stmt.setInt(6, 1);
            stmt.execute();
            stmt.close();

            stmt = conn.prepareStatement(
                    "INSERT INTO bid(auction_id, image_id, max_amount, time) VALUES(?,?,?,?)");
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
        }
    }

    /**
     * persist two records into the ball_color table.
     *
     * @param conn database connection
     * @param downloadId List of download ids
     *
     * @return List of BallColor
     *
     * @throws SQLException fails to update database
     */
    private List persistColor(Connection conn, List downloadId)
        throws SQLException {
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
            colors.add(new BallColorImpl(new Long(rs.getLong("id")), rs.getString("name"),
                    rs.getLong("download_obj_id")));
        }

        rs.close();
        stmt.close();

        return colors;
    }

    /**
     * <p>Insert a record into the 'contact_info' table.</p>
     *
     * @param conn Database connection
     *
     * @return the new added record's id
     *
     * @throws Exception fails to update database
     */
    private long persistContactInfo(Connection conn) throws Exception {
        // insert a record into the contact_info table
        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO contact_info(first_name,last_name,address_1,city,state,postal_code, telephone)"
                + " VALUES(?,?,?,?,?,?,?)");
        stmt.setString(1, "javier");
        stmt.setString(2, "invern");
        stmt.setString(3, "Topcoder");
        stmt.setString(4, "Boston");
        stmt.setString(5, "MM");
        stmt.setString(6, "93823-234");
        stmt.setString(7, "02928-2383");
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
     * <p>Persist the two records into the 'download_obj' table.</p>
     *
     * @param conn database connection
     *
     * @return List of downloadId
     *
     * @throws SQLException fails to update database
     */
    private List persistDownloadObj(Connection conn) throws SQLException {
        PreparedStatement stmt;
        stmt = conn.prepareStatement(
                "INSERT INTO download_obj(media_type,suggested_name,content) VALUES(?,?,?)");
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
     * Insert a record into 'player' table.
     *
     * @param conn database connection
     * @param contactInfoId 'contact_info_id'
     * @param anyUserId 'any_user_id'
     *
     * @throws Exception fail to update database
     */
    private void persistPlayer(Connection conn, long contactInfoId, long anyUserId)
        throws Exception {
        // add record into 'player' table
        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO player(any_user_id,contact_info_id,payment_pref) VALUES(?,?,?)");
        stmt.setLong(2, contactInfoId);
        stmt.setLong(1, anyUserId);
        stmt.setString(3, "wire transfer");
        stmt.execute();
        stmt.close();
    }

    /**
     * insert a record into 'plugin_download' table.
     *
     * @param pluginName plugin_name table
     *
     * @throws Exception fail to update database
     */
    private void persistPluginDownload(String pluginName)
        throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(DB_FACTORY_NAMESPACE);
            conn = dbFactory.createConnection();
            // insert a record into the plugin_download table
            stmt = conn.prepareStatement(
                    "INSERT INTO plugin_downloads(plugin_name, count) VALUES(?,?)");
            stmt.setString(1, pluginName);
            stmt.setLong(2, 1);

            stmt.execute();
            stmt.close();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * Persist a record into 'puzzle' table.
     *
     * @param downloadId download_obj_id
     * @param puzzleName name of puzzle
     *
     * @return puzzleid
     *
     * @throws Exception fail to update database
     */
    private long persistPuzzle(long downloadId, String puzzleName)
        throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
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
            stmt = conn.prepareStatement(
                    "INSERT INTO puzzle_attribute(puzzle_id,name,value)VALUES(?,?,?)");
            stmt.setLong(1, puzzleId);
            stmt.setString(2, "att1");
            stmt.setString(3, "value1");

            stmt.execute();
            stmt.close();

            // insert a record into the puzzle_resource table
            stmt = conn.prepareStatement(
                    "INSERT INTO puzzle_resource(puzzle_id,name,download_obj_id)VALUES(?,?,?)");
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
     *
     * @return slot_id
     *
     * @throws Exception fails to update database
     */
    private long persistSlot(long bidId, int sequenceNumber, java.util.Date date, long puzzleId,
        long downloadId) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(DB_FACTORY_NAMESPACE);
            conn = dbFactory.createConnection();
            // insert a record into the puzzle table
            stmt = conn.prepareStatement(
                    "INSERT INTO hosting_slot(bid_id,sequence_number,hosting_start) VALUES(?,?,?)");
            stmt.setLong(1, bidId);
            stmt.setInt(2, 1);
            stmt.setTimestamp(3, new Timestamp(date.getTime()));

            stmt.execute();
            stmt.close();

            // find the added record's id
            long slotId = 0;
            stmt = conn.prepareStatement("SELECT * FROM hosting_slot");

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                slotId = rs.getLong("id");
            }

            // insert a record into 'effective_bid' table
            stmt = conn.prepareStatement(
                    "INSERT INTO effective_bid(bid_id,current_amount)VALUES(?,?)");
            stmt.setLong(1, bidId);
            stmt.setInt(2, 2);
            stmt.execute();
            stmt.close();

            // inset a record into 'brn_tsr_for_slot' table
            stmt = conn.prepareStatement(
                    "INSERT INTO brn_tsr_for_slot(hosting_slot_id,sequence_number,puzzle_id)VALUES(?,?,?)");
            stmt.setLong(1, slotId);
            stmt.setInt(2, 1);
            stmt.setLong(3, puzzleId);
            stmt.execute();
            stmt.close();

            // insert a record into 'puzzle_for_slot' table
            stmt = conn.prepareStatement(
                    "INSERT INTO puzzle_for_slot(hosting_slot_id, puzzle_id)VALUES(?,?)");
            stmt.setLong(1, slotId);
            stmt.setLong(2, puzzleId);
            stmt.execute();
            stmt.close();

            // insert a record into the 'target_object' table
            stmt = conn.prepareStatement(
                    "INSERT INTO target_object (hosting_slot_id,sequence_number,uri_path,identifier_text,"
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
        }
    }

    /**
     * <p>Insert a record into the the sponsor table. and return the new added record id.</p>
     *
     * @param conn database connection
     * @param contactInfoId contact_info_id
     * @param anyUserId any_user_id
     *
     * @return the sponsorId
     *
     * @throws SQLException fail to query database
     */
    private long persistSponsor(Connection conn, long contactInfoId, long anyUserId)
        throws SQLException {
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
     * insert data for testing.
     *
     * @return List of data used in test
     *
     * @throws Exception into Junit
     */
    private List prepareDatabase() throws Exception {
        Connection conn = null;
        List results = new ArrayList();

        try {
            DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(DB_FACTORY_NAMESPACE);
            conn = dbFactory.createConnection();
            // clear the database first
            clearDB(conn);

            // add record into 'contact_info' table
            long contactInfoId = persistContactInfo(conn);

            // add record into 'any_user' table
            for (int i = 0; i < 50; i++) {
                long anyUserId = persistAnyUser(conn, i + 1);
                persistPlayer(conn, contactInfoId, anyUserId);
                persistSponsor(conn, contactInfoId, anyUserId);
            }

            // insert into the download_obj table
            List downloadId = persistDownloadObj(conn);

            // insert into the ball_Color table
            results.add(persistColor(conn, downloadId));

            return results;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}
