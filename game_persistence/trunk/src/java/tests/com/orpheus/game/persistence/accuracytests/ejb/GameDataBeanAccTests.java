/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.accuracytests.ejb;

import com.mockrunner.mock.ejb.MockUserTransaction;
import com.orpheus.game.persistence.BallColor;
import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.ImageInfo;
import com.orpheus.game.persistence.accuracytests.AccuracyHelper;
import com.orpheus.game.persistence.ejb.GameDataBean;
import com.orpheus.game.persistence.entities.BallColorImpl;
import com.orpheus.game.persistence.entities.DomainImpl;
import com.orpheus.game.persistence.entities.GameImpl;
import com.topcoder.util.puzzle.PuzzleData;
import com.topcoder.web.frontcontroller.results.DownloadData;
import junit.framework.TestCase;
import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import java.io.InputStream;

/**
 * Accuracy test cases for <code>GameDataBean</code> class.
 *
 * @author tuenm
 * @version 1.0
 */
public class GameDataBeanAccTests extends TestCase {

    /**
     * The bean to test.
     */
    private GameData gameData = null;

    /**
     * The context used in test cases.
     */
    private Context context;

    /**
     * Mock user transaction.
     */
    private MockUserTransaction mockTransaction;

    /**
     * Set up for each test case.
     */
    protected void setUp() throws Exception {
        AccuracyHelper.clearAllConfigurationNS();

        //add config files
        AccuracyHelper.loadBaseConfig();

        // create test data in the database
        AccuracyHelper.setupDatabase();

        // set up MockContextFactory
        MockContextFactory.setAsInitial();

        // initialize the context
        context = new InitialContext();

        // set up MockContainer
        MockContainer mockContainer = new MockContainer(context);

        // create descriptor
        SessionBeanDescriptor sampleServiceDescriptor = new SessionBeanDescriptor("game/GameDataBean",
                GameDataHome.class, GameData.class, new GameDataBean());

        // deploy
        mockContainer.deploy(sampleServiceDescriptor);

        // set up MockUserTransaction
        mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);

        // lookup the GameDataBean
        Object gameDataHomeObj = context.lookup("game/GameDataBean");
        GameDataHome gameDataHome = (GameDataHome) PortableRemoteObject.narrow(gameDataHomeObj, GameDataHome.class);
        gameData = gameDataHome.create();
    }

    /**
     * Clean up for each test case.
     *
     * @throws Exception into Junit
     */
    protected void tearDown() throws Exception {
        AccuracyHelper.cleanupDatabase();
        AccuracyHelper.clearAllConfigurationNS();
    }

    /**
     * Accuracy test of the <code>getDomain()</code> method. Data should be retrieved from the database
     * properly.
     *
     * @throws Exception into JUnit.
     */
    public void testGetDomain() throws Exception {
        Domain domain = gameData.getDomain(1);

        assertNotNull("Cannot retrieve domain.", domain);
        assertEquals("Not the expected id.", 1, domain.getId().longValue());
        assertEquals("Not the expected sponsor id.", 2, domain.getSponsorId());
        assertEquals("Not the expected domain name.", "url", domain.getDomainName());
    }

    /**
     * Accuracy test of the <code>getDownloadData()</code> method. Data should be retrieved from the database
     * properly.
     *
     * @throws Exception into JUnit.
     */
    public void testGetDownloadData() throws Exception {

        DownloadData data = gameData.getDownloadData(1);
        assertNotNull("Cannot retrieve download data.", data);
        assertEquals("Not the expected media type.", "html/text", data.getMediaType());
        assertEquals("Not the expected suggested name.", "name", data.getSuggestedName());

        InputStream is = data.getContent();
        assertEquals("Not the expected content.", "content", AccuracyHelper.convertISToString(is));
    }

    /**
     * Accuracy test of the <code>getGame()</code> method. Data should be retrieved from the database
     * properly.
     *
     * @throws Exception into JUnit.
     */
    public void testGetGame() throws Exception {
        Game game = gameData.getGame(1);

        assertNotNull("Cannot retrieve game.", game);
        assertEquals("Not the expected id.", 1, game.getId().longValue());
        assertEquals("Not the expected color id.", 1, game.getBallColor().getId().longValue());

        assertTrue("Not the expected start date.", AccuracyHelper.compareStringToDate("2006/1/1",
                game.getStartDate()));
    }

    /**
     * Accuracy test of the <code>getBlock()</code> method. Data should be retrieved from the database
     * properly.
     *
     * @throws Exception into JUnit.
     */
    public void testGetBlock() throws Exception {
        HostingBlock block = gameData.getBlock(1);

        assertNotNull("Cannot retrieve block.", block);
        assertEquals("Not the expected id.", 1, block.getId().longValue());
        assertEquals("Not the expected sequence number.", 1, block.getSequenceNumber());
        assertEquals("Not the expected max hosting time per slot.", 1, block.getMaxHostingTimePerSlot());
    }

    /**
     * Accuracy test of the <code>getPuzzle()</code> method. Data should be retrieved from the database
     * properly.
     *
     * @throws Exception into JUnit.
     */
    public void testGetPuzzle() throws Exception {
        PuzzleData puzzle = gameData.getPuzzle(1);

        assertNotNull("Cannot retrieve puzzle.", puzzle);
        assertEquals("Not the expected name.", "puzzle", puzzle.getName());
    }

    /**
     * Accuracy test of the <code>getSlot()</code> method. Data should be retrieved from the database
     * properly.
     *
     * @throws Exception into JUnit.
     */
    public void testGetSlot() throws Exception {
        HostingSlot slot = gameData.getSlot(1);

        assertNotNull("Cannot retrieve slot.", slot);
        assertEquals("Not the expected name.", 1, slot.getId().longValue());
        assertEquals("Not the expected sequence number.", 1, slot.getSequenceNumber());
        assertTrue("Not the expected hosting start.",
                AccuracyHelper.compareStringToDate("2006/1/1", slot.getHostingStart()));
        assertTrue("Not the expected hosting end.",
                AccuracyHelper.compareStringToDate("2006/2/2", slot.getHostingEnd()));
    }

    /**
     * Accuracy test of the <code>getKeysForPlayer()</code> method. Data should be retrieved from the database
     * properly.
     *
     * @throws Exception into JUnit.
     */
    public void testGetKeysForPlayer() throws Exception {
        String[] keys = gameData.getKeysForPlayer(1, new long[]{1});

        assertNotNull("Cannot retrieve keys.", keys);
        assertEquals("Not the expected key length.", 1, keys.length);
        assertEquals("Not the expected key value.", "key", keys[0]);
    }

    /**
     * Accuracy test of the <code>createDomain()</code> method. Data should be retrieved from the database
     * properly.
     *
     * @throws Exception into JUnit.
     */
    public void testCreateDomain() throws Exception {
        Domain domain = new DomainImpl(new Long(2), 2, "test", new Boolean(false), new ImageInfo[]{});

        // create the instance
        domain = gameData.createDomain(domain);

        // get result
        Domain result = gameData.getDomain(domain.getId().longValue());

        assertNotNull("Cannot retrieve domain.", result);
        assertEquals("Not the expected id.", domain.getId().longValue(), result.getId().longValue());
        assertEquals("Not the expected sponsor id.", 2, result.getSponsorId());
        assertEquals("Not the expected domain name.", "test", result.getDomainName());
    }

    /**
     * Accuracy test of the <code>createGame()</code> method. Data should be retrieved from the database
     * properly.
     *
     * @throws Exception into JUnit.
     */
    public void testCreateGame() throws Exception {
        BallColor color = new BallColorImpl(new Long(1), "color_name", 1);
        HostingBlock block = gameData.getBlock(1);
        HostingBlock[] blocks = new HostingBlock[]{block};

        Game game = new GameImpl(new Long(3), color, 1, AccuracyHelper.convertStringToDate("2006/1/1"),
                AccuracyHelper.convertStringToDate("2006/2/2"), blocks);

        // create the instance
        game = gameData.createGame(game);

        // get result
        Game result = gameData.getGame(game.getId().longValue());

        assertNotNull("Cannot retrieve game.", result);
        assertEquals("Not the expected id.", game.getId().longValue(), result.getId().longValue());
        assertEquals("Not the expected color id.", 1, result.getBallColor().getId().longValue());
    }

    /**
     * Accuracy test of the <code>createSlots()</code> method. Data should be retrieved from the database
     * properly.
     *
     * @throws Exception into JUnit.
     */
    public void testCreateSlots() throws Exception {

        // create the instance
        gameData.createSlots(1, new long[]{2});

        // get result
        HostingSlot result = gameData.getSlot(1);

        assertNotNull("Cannot retrieve slot.", result);
        assertEquals("Not the expected id.", 1, result.getId().longValue());
    }

    /**
     * Accuracy test of the <code>findGames()</code> method. Data should be retrieved from the database
     * properly.
     *
     * @throws Exception into JUnit.
     */
    public void testFindGames() throws Exception {

        Game[] games = gameData.findGames(new Boolean(true), new Boolean(true));
        assertEquals("Not the expected length.", 1, games.length);

        games = gameData.findGames(new Boolean(true), new Boolean(false));
        assertEquals("Not the expected length.", 1, games.length);

        games = gameData.findGames(new Boolean(false), new Boolean(false));
        assertEquals("Not the expected length.", 0, games.length);

        games = gameData.findGames(null, null);
        assertEquals("Not the expected length.", 2, games.length);

        games = gameData.findGames(new Boolean(true), null);
        assertEquals("Not the expected length.", 2, games.length);

        games = gameData.findGames(new Boolean(false), null);
        assertEquals("Not the expected length.", 0, games.length);

        games = gameData.findGames(null, new Boolean(true));
        assertEquals("Not the expected length.", 1, games.length);

        games = gameData.findGames(null, new Boolean(false));
        assertEquals("Not the expected length.", 1, games.length);
    }

    /**
     * Accuracy test of the <code>findActiveDomains()</code> method. Data should be retrieved from the database
     * properly.
     *
     * @throws Exception into JUnit.
     */
    public void testFindActiveDomains() throws Exception {

        Domain[] domains = gameData.findActiveDomains();
        assertEquals("Not the expected length.", 0, domains.length);
    }

    /**
     * Accuracy test of the <code>findAllBallColors()</code> method. Data should be retrieved from the database
     * properly.
     *
     * @throws Exception into JUnit.
     */
    public void testFindAllBallColors() throws Exception {

        BallColor[] colors = gameData.findAllBallColors();
        assertEquals("Not the expected length.", 1, colors.length);
    }

    /**
     * Accuracy test of the <code>findCompletedSlots()</code> method. Data should be retrieved from the database
     * properly.
     *
     * @throws Exception into JUnit.
     */
    public void testFindCompletedSlots() throws Exception {

        HostingSlot[] slots = gameData.findCompletedSlots(1);
        assertEquals("Not the expected length.", 1, slots.length);
    }

    /**
     * Accuracy test of the <code>findGameRegistrations()</code> method. Data should be retrieved from the database
     * properly.
     *
     * @throws Exception into JUnit.
     */
    public void testFindGameRegistrations() throws Exception {

        long[] gameIds = gameData.findGameRegistrations(1);
        assertEquals("Not the expected length.", 0, gameIds.length);
    }

    /**
     * Accuracy test of the <code>findGamesByDomain()</code> method. Data should be retrieved from the database
     * properly.
     *
     * @throws Exception into JUnit.
     */
    public void testFindGamesByDomain() throws Exception {

        Game[] games = gameData.findGamesByDomain("url", 1);
        assertEquals("Not the expected length.", 0, games.length);
    }
}
