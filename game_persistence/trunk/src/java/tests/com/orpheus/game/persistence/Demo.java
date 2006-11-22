/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;

import com.mockrunner.mock.ejb.MockUserTransaction;

import com.orpheus.game.persistence.ejb.GameDataBean;
import com.orpheus.game.persistence.entities.DomainImpl;
import com.orpheus.game.persistence.entities.DomainTargetImpl;
import com.orpheus.game.persistence.entities.HostingSlotImpl;

import com.topcoder.util.puzzle.PuzzleData;

import com.topcoder.web.frontcontroller.results.DownloadData;

import junit.framework.TestCase;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;

import org.mockejb.jndi.MockContextFactory;

import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.rmi.PortableRemoteObject;


/**
 * <p>
 * Demo show how to use this component.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends TestCase {
    /** State of this test case. These variables are initialized by setUp method */
    private Context context;

    /** mock user trnasaction. */
    private MockUserTransaction mockTransaction;

    /** the ballColor lists. */
    private List colors = null;

    /** keyCount. */
    private int keyCount = 1;

    /** game startDate. */
    private Date gameStartDate = null;

    /** sponsorId. */
    private long sponsorId = 0;

    /** playerId. */
    private long playerId = 0;

    /**
     * Deploys and creates EJBs needed for our tests.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfigManager();

        //prepared database/data
        List results = TestHelper.prepareDatabase();
        playerId = ((Long) results.get(0)).longValue();
        sponsorId = ((Long) results.get(1)).longValue();
        colors = (List) results.get(2);
        gameStartDate = new Date();

        //add config files
        TestHelper.addConfigFile(TestHelper.DBFACTORY_CONFIG_XML);
        TestHelper.addConfigFile(TestHelper.OBJECT_FACTORY_CONFIG_XML);
        TestHelper.addConfigFile(TestHelper.GAME_PERSISTENCE_CONFIG_FILE);

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
        SessionBeanDescriptor sampleServiceDescriptor = new SessionBeanDescriptor("game/GameDataBean",
                GameDataHome.class, GameData.class, new GameDataBean());
        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);

        mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);
    }

    /**
     * Return the GameDataBean's remote interface.
     *
     * @return GameData
     *
     * @throws Exception into Junit
     */
    private GameData getRemoteEJB() throws Exception {
        //      Lookup the home
        Object gameDataHomeObj = context.lookup("game/GameDataBean");

        // PortableRemoteObject does not do anything in MockEJB but it does no harm to call it
        GameDataHome gameDataHome = (GameDataHome) PortableRemoteObject.narrow(gameDataHomeObj, GameDataHome.class);

        // create the bean
        return gameDataHome.create();
    }

    /**
     * <p>
     * Demo shows the usage of this component. Management of Game,Domain, Slot,Block, and to register game, to
     * complete a slot, to delete, update,create new slot, new block, new domain, new game.
     * </p>
     *
     * @throws Exception into JUNit
     */
    public void testDemo() throws Exception {
        //obtain remote interface: the details are omitted.
        GameData gameDataAdmin = getRemoteEJB();

        //we might begin by creating some games, 
        //first creating the block that the game use
        HostingBlock[] blocks = TestHelper.getBlocks();
        Game game = TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate, blocks);

        //create game now
        Game createdGame = gameDataAdmin.createGame(game);

        //at this point, the game is created and id was assigned
        //we create some slots for a block and bids
        long blockId = createdGame.getBlocks()[0].getId().longValue();

        //now create a domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));
        Domain toCreate = TestHelper.getDomain(sponsorId, "domainName", new Boolean(true), images);
        Domain createdDomain = gameDataAdmin.createDomain(toCreate);

        //get the already existed createdDomain's image id
        long imageId = createdDomain.getImages()[0].getId().longValue();

        //persist a bid record for testing
        long[] bidIds = TestHelper.persistBid(blockId, imageId, 1, new Date());

        //create slots
        HostingSlot[] createdSlots = gameDataAdmin.createSlots(blockId, bidIds);

        //we create a hosting block
        int slotMaxHostingTime = 1000;
        gameDataAdmin.addBlock(createdGame.getId().longValue(), 1000);

        //the download id that can be used
        long downloadId = ((BallColor) this.colors.get(0)).getImageId();

        //persist the puzzle
        long puzzleId = TestHelper.persistPuzzle(downloadId, "puzzleName");

        //persist the slot for testing.//TODO, it would be removed as it should be created in the createSlot method
        int sequenceNumber = 1;
        Date hostingStart = new Date();
        long slotId = TestHelper.persistSlot(bidIds[0], sequenceNumber, hostingStart, puzzleId, downloadId);

        //we get a game
        Game getGame = gameDataAdmin.getGame(createdGame.getId().longValue());
        //we get a block
        blockId = getGame.getBlocks()[0].getId().longValue();

        HostingBlock block = gameDataAdmin.getBlock(blockId);

        //we get a slot
        HostingSlot slot = gameDataAdmin.getSlot(slotId);

        //we get a download object
        DownloadData data = gameDataAdmin.getDownloadData(downloadId);

        //we get a domain
        long domainId = createdDomain.getId().longValue();
        Domain domain = gameDataAdmin.getDomain(domainId);

        //we get key texts for a player
        long[] slotIds = new long[] {slotId};
        String[] keys = gameDataAdmin.getKeysForPlayer(playerId, slotIds);

        //we get puzzle data
        PuzzleData puzzle = gameDataAdmin.getPuzzle(puzzleId);

        //persist the download
        TestHelper.persistPluginDownload("pluginName");

        //we record a plugin download count increase
        String pluginName = "pluginName";
        gameDataAdmin.recordPluginDownload(pluginName);

        //we record a plugin download count increase
        gameDataAdmin.recordPluginDownload(pluginName);

        //we record player registration in a game
        long gameId = createdGame.getId().longValue();
        gameDataAdmin.recordRegistration(playerId, gameId);

        // we record slot completion for a player
        Date date = new Date();
        gameDataAdmin.recordSlotCompletion(playerId, slotId, date);

        //we record game completion by a player
        gameDataAdmin.recordGameCompletion(playerId, gameId);

        //we add a new binary object in the database
        String name = "name";
        String mediaType = "image/png";
        byte[] content = "content".getBytes();
        gameDataAdmin.recordBinaryObject(name, mediaType, content);

        //update slots
        DomainTarget[] targets = slot.getDomainTargets();
        targets[0] = new DomainTargetImpl(null, targets[0].getSequenceNumber(), targets[0].getUriPath(),
                targets[0].getIdentifierText(), targets[0].getIdentifierHash(), targets[0].getClueImageId());

        HostingSlot toUpdate = new HostingSlotImpl(slot.getId(), slot.getDomain(), slot.getImageId(),
                slot.getBrainTeaserIds(), slot.getPuzzleId(), slot.getSequenceNumber() + 1, targets,
                slot.getWinningBid(), slot.getHostingStart(), slot.getHostingEnd());

        HostingSlot[] slots = new HostingSlot[] {toUpdate};

        //update the slot in ejb
        HostingSlot[] updatedSlots = gameDataAdmin.updateSlots(slots);

        //update domain
        Domain toUpdatedDomain = new DomainImpl(createdDomain.getId(), sponsorId, "domainName", new Boolean(false),
                images);
        gameDataAdmin.updateDomain(domain);

        //find active domains
        Domain[] domains = gameDataAdmin.findActiveDomains();

        // find games for player in domain
        Game[] games = gameDataAdmin.findGamesByDomain("domainName", playerId);

        //find completed slots
        HostingSlot[] completedSlots = gameDataAdmin.findCompletedSlots(gameId);

        //find slot completions for game and slot
        SlotCompletion[] slotCompletions = gameDataAdmin.findSlotCompletions(gameId, slotId);

        //find games
        Boolean isStarted = new Boolean(true);
        Boolean isEnded = null;
        Game[] findGames = gameDataAdmin.findGames(isStarted, isEnded);

        //find games that the player is registered in
        long[] gameIds = gameDataAdmin.findGameRegistrations(playerId);

        //find a sponsor¡¯s domains
        Domain[] sponsorDomain = gameDataAdmin.findDomainsForSponsor(sponsorId);

        //find first slot this player did not finish
        gameDataAdmin.findSlotForDomain(gameId, playerId, "domainName");

        //get all ball colors
        BallColor[] colorss = gameDataAdmin.findAllBallColors();
    }

    /**
     * remove the environment.
     *
     * @throws Exception into Junit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfigManager();
        TestHelper.clearDatabase();
    }
}
