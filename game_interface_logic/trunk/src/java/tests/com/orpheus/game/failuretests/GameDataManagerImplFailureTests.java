/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.failuretests;

import com.orpheus.game.GameDataException;
import com.orpheus.game.GameDataManagerImpl;
import com.orpheus.game.MockPuzzleTypeSource;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.GameDataLocal;
import com.orpheus.game.persistence.HostingSlot;

import junit.framework.TestCase;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;

import org.mockejb.jndi.MockContextFactory;

import javax.naming.Context;
import javax.naming.InitialContext;


/**
 * <p>
 * Failure test the GameDataManagerImpl class.
 * </p>
 */
public class GameDataManagerImplFailureTests extends TestCase {
    /** GameDataManagerImpl instance to test against. */
    private GameDataManagerImpl manager = null;

    /** State of this test case. These variables are initialized by setUp method */
    private Context context;

    /**
     * create instance.
     */
    protected void setUp() throws Exception {
        //add config file
        TestHelper.addConfigFile(TestHelper.GAME_INTERFACE_LOGIC_CONFIG_FILE);

        this.bindBothJNDI();
        //create instance
        manager = new GameDataManagerImpl(new MockPuzzleTypeSource());
    }

    /**
     * testUpComingDomain , the hostingSlot is null, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testTestUpcomingDomain_nullSlot() throws Exception {
        try {
            manager.testUpcomingDomain(null);
            fail("The hostingslot is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * testUpComingDomain , the hostingSlot's domain'name is invalid. GameDataException expected.
     *
     * @throws Exception into Junit
     */
    public void testTestUpcomingDomain_errorDomainName() throws Exception {
        HostingSlot slot = TestHelper.buildHostingSlot();

        try {
            manager.testUpcomingDomain(slot);
            fail("The domain name is invalid.");
        } catch (GameDataException e) {
            //good
        }
    }

    /**
     * testUpComingDomain , the manager is stopped. IllegalStateException expected.
     *
     * @throws Exception into Junit
     */
    public void testTestUpcomingDomain() throws Exception {
        HostingSlot slot = TestHelper.buildHostingSlot();
        TestHelper.setPrivateField(GameDataManagerImpl.class, manager, "stopped", new Boolean(true));

        try {
            manager.testUpcomingDomain(slot);
            fail("The manager is stopped.");
        } catch (IllegalStateException e) {
            //good
        }
    }

    /**
     * <p>
     * Test recordWinningBids(long blockId, long[] bidIds), the bidIds is null, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRecordWinningBids_nullBids() throws Exception {
        try {
            manager.recordWinningBids(1L, null);
            fail("The bids array is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test recordWinningBids(long blockId, long[] bidIds), persist error in ejb, GameDataException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRecordWinningBids_persistError() throws Exception {
        try {
            manager.recordWinningBids(1L, new long[] { 1 });
            fail("Persist error in ejb.");
        } catch (GameDataException e) {
            //good
        }
    }

    /**
     * <p>
     * Test recordWinningBids(long blockId, long[] bidIds), the manager is stopped, IllegalStateException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRecordWinningBids_stopped() throws Exception {
        TestHelper.setPrivateField(GameDataManagerImpl.class, manager, "stopped", new Boolean(true));

        try {
            manager.recordWinningBids(1L, new long[] { 1 });
            fail("The manager is stopped.");
        } catch (IllegalStateException e) {
            //good
        }
    }

    /**
     * <p>
     * Test advanceHostingSlot(long gameId), the game does not exist, GameDataException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testAdvanceHostingSlot_noGame() throws Exception {
        try {
            this.manager.advanceHostingSlot(1L);
            fail("The game does not exist.");
        } catch (GameDataException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the gameStatusChangedToStarted method , iae expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGameStatusChangedToStarted_nullGame() throws Exception {
        try {
            this.manager.gameStatusChangedToStarted(null);
            fail("The game is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the newGameAvailable method , iae expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testNewGameAvailable_nullGame() throws Exception {
        try {
            this.manager.newGameAvailable(null);
            fail("The game is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }
    /**
     * <p>Test the getAllCurrentNotStartedGames method, the manager is stopped,IllegalStateException expected.</p>
     * @throws Exception into Junit
     */
    public void testGetAllCurrentNotStartedGames() throws Exception{
    	TestHelper.setPrivateField(GameDataManagerImpl.class, manager, "stopped", new Boolean(true));
    	try{ 
    		this.manager.getAllCurrentNotStartedGames();
    		fail("The manager is stopped.");
    	}catch(IllegalStateException e){
    		//good
    	}
    }
    
    /**
     * <p>Test the setCurrentNotStartedGames method, the game is null, iae expected.</p>
     * @throws Exception into JUnit
     */
    public void testSetCurrentNotStartedGames_nullGame() throws Exception{
    	try{ 
    		this.manager.setCurrentNotStartedGames(null);
    		fail("The game array is null.");
    	}catch(IllegalArgumentException e){
    		//good
    	}
    }
    
    /**
     * <p>Test the setCurrentNotStartedGames method, the game array contains null element, iae expected.</p>
     * @throws Exception into JUnit
     */
    public void testSetCurrentNotStartedGames_nullGameElement() throws Exception{
    	try{ 
    		this.manager.setCurrentNotStartedGames(new Game[]{null});
    		fail("The game array contains null element.");
    	}catch(IllegalArgumentException e){
    		//good
    	}
    }
    
    /**
     * <p>Test the addNewNotStartedGame, the game is null, iae expected.</p>
     * @throws Exception into Junit
     */
    public void testAddNewNotStartedGame_nullGame() throws Exception{
    	try{ 
    		manager.addNewNotStartedGame(null);
    		fail("The game is null, iae expected.");
    	}catch(IllegalArgumentException e){
    		//good
    	}
    }
    

    /**
     * clear the environment.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfigManager();
    }

    /**
     * Bind both the local and remote jndi ejb for testing.
     *
     * @throws Exception into Junit
     */
    private void bindBothJNDI() throws Exception {
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
        SessionBeanDescriptor gameDataLocalDescriptor = new SessionBeanDescriptor("game/GameDataBean",
                GameDataLocal.class, GameDataLocal.class, new GameDataBean());
        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(gameDataLocalDescriptor);

        /* Create deployment descriptor of our sample bean.
         * MockEjb uses it instead of XML deployment descriptors
         */
        SessionBeanDescriptor gameDataRemoteDescriptor = new SessionBeanDescriptor("java:comp/env/ejb/game/GameDataEJB",
                GameDataHome.class, GameData.class, new GameDataBean());
        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(gameDataRemoteDescriptor);
    }
}
