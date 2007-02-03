/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import java.io.File;
import java.util.Date;
import java.util.Iterator;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.orpheus.game.GameDataManagerImpl;
import com.orpheus.game.MockPuzzleTypeSource;
import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.GameDataLocal;
import com.orpheus.game.persistence.GameDataLocalHome;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * <p>
 * The accuracy test for GameDataManagerImpl.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GameDataManagerImplTests extends TestCase {
	/**
	 * Represents the accuracytest folder.
	 */
	private static String PATH = "accuracytest";

	/**
	 * Represents the config file name of the GameDataManagerImpl class.
	 */
	private static final String CONFIG_FILE = PATH + File.separator + "Config.xml";

	/**
     * The GameDataManagerImpl instance for test.
     */
    private GameDataManagerImpl manager;

    /**
     * The context used to lookup EJB.
     */
    private Context context;

    /**
     * The ejb interface used for tests.
     */
    private GameDataLocal localEJB;

    /**
     * The setUp of the tests.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        // We need to set MockContextFactory as our JNDI provider.
        // This method sets the necessary system properties.
        MockContextFactory.setAsInitial();

        // create the initial context that will be used for binding EJBs
        context = new InitialContext();

        // Create an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        SessionBeanDescriptor sampleServiceDescriptor
            = new SessionBeanDescriptor("GameDataLocal",
                    GameDataLocalHome.class, GameDataLocal.class, new GameDataBean());

        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);

        // 
        sampleServiceDescriptor
            = new SessionBeanDescriptor("GameDataRemote",
                GameDataHome.class, GameData.class, new GameDataBean());

        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);

        GameDataLocalHome home = (GameDataLocalHome) context.lookup("GameDataLocal");
        localEJB = (GameDataLocal) home.create();

        clearNamespaces();

        ConfigManager.getInstance().add(CONFIG_FILE);
        manager = new GameDataManagerImpl(new MockPuzzleTypeSource());
    }

    /**
     * The tearDown of the tests.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
    	MockContextFactory.revertSetAsInitial();

        clearNamespaces();

        //stop the manager
        manager.stopManager();
    }

    /**
     * Clears all the namespaces in the ConfigManager.
     * @throws Exception
     *             to JUnit
     */
    private static void clearNamespaces() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        // iterator through to clear the namespace
        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            String namespace = (String) it.next();
            if (cm.existsNamespace(namespace)) {
                // removes the namespace that exists
                cm.removeNamespace(namespace);
            }
        }
    }

//    /**
//     * The accuracy test of the constructor with parameters.
//     *
//     * @throws Exception to JUnit
//     */
//    public void testCtorAccuracy1() throws Exception {
//        GameDataManagerImpl instance1 = new GameDataManagerImpl(
//                new String[] {"GameDataLocal"}, new String[] {"local"}, 1000, 1000, 1000, (float)0.5, "namespace", "c");
//        assertNotNull("create failed", instance1);
//        instance1.stopManager();
//
//        GameDataManagerImpl instance2 = new GameDataManagerImpl(
//                new String[] {"GameDataRemote"}, new String[] {"REMOTE"}, 2000, 2000, 1000, (float)0.5, "namespace", "c");
//        assertNotNull("create failed", instance2);
//        instance2.stopManager();
//    }

    /**
     * The accuracy test of the constructor with namespace.
     *
     * @throws Exception to JUnit
     */
    public void testCtorAccuracy2() throws Exception {
        GameDataManagerImpl instance = new GameDataManagerImpl(new MockPuzzleTypeSource(), GameDataManagerImpl.class.getName());
        assertNotNull("create failed", instance);
        instance.stopManager();
    }

    /**
     * The accuracy test of the default constructor.
     */
    public void testCtorAccuracy3() {
        assertNotNull("create failed", manager);
    }

    /**
     * The accuracy test of the method testUpcomingDomain.
     *
     * @throws Exception to JUnit
     */
    public void ttestTestUpcomingDomainAccuracy1() throws Exception {
    	AccuracyTestHostingSlot slot = new AccuracyTestHostingSlot();
    	AccuracyTestDomain domain = new AccuracyTestDomain();
    	domain.setDomainName("http://b.com");
    	slot.setDomain(domain);

        assertFalse("testUpcomingDomain method failed", manager.testUpcomingDomain(slot));

        domain.setDomainName("http://www.topcoder.com");
        assertTrue("testUpcomingDomain method failed", manager.testUpcomingDomain(slot));
    }

    /**
     * The accuracy test of the method testUpcomingDomain.
     *
     * @throws Exception to JUnit
     */
    public void ttestTestUpcomingDomainAccuracy2() throws Exception {
        AccuracyTestDomainTarget domainTarget1 = new AccuracyTestDomainTarget();
        domainTarget1.setUriPath("http://www.topcoder.com");
        AccuracyTestDomainTarget domainTarget2 = new AccuracyTestDomainTarget();
        domainTarget2.setUriPath("http://x.com");
        AccuracyTestHostingSlot slot = new AccuracyTestHostingSlot();
    	AccuracyTestDomain domain = new AccuracyTestDomain();
    	domain.setDomainName("http://www.topcoder.com");
    	slot.setDomain(domain);
    	slot.setDomainTargets(new DomainTarget[] {domainTarget1, domainTarget2});

    	assertFalse("testUpcomingDomain method failed", manager.testUpcomingDomain(slot));

    	slot.setDomainTargets(new DomainTarget[] {domainTarget1});

    	assertTrue("testUpcomingDomain method failed", manager.testUpcomingDomain(slot));
    }

    /**
     * The accuracy test of the method recordWinningBids.
     *
     * @throws Exception to JUnit
     */
    public void testRecordWinningBidsAccuracy() throws Exception {
        manager.recordWinningBids(1001, new long[] {1, 4, 5, 7, 9, 11, 23, 45, 24, 32});
        long[] bids = DataProvider.getBids();
        for (int i = 0; i < bids.length; i++) {
        	System.out.println(bids[i]);
        }
    }

    /**
     * The accuracy test of the method
     *     <code>advanceHostingSlot()</code>.
     *
     * @throws Exception to JUnit
     */
    public void testAdvanceHostingSlotAccuracy() throws Exception {
        // add a new game
    	AccuracyTestGame game = new AccuracyTestGame();
    	game.setId(new Long(1001));
    	AccuracyTestDomainTarget domainTarget1 = new AccuracyTestDomainTarget();
        domainTarget1.setUriPath("http://www.topcoder.com");
        AccuracyTestDomainTarget domainTarget2 = new AccuracyTestDomainTarget();
        domainTarget2.setUriPath("http://www.");
        AccuracyTestHostingSlot slot1 = new AccuracyTestHostingSlot();
        AccuracyTestHostingSlot slot2 = new AccuracyTestHostingSlot();
    	AccuracyTestDomain domain = new AccuracyTestDomain();
    	domain.setDomainName("http://www.topcoder.com");
    	slot1.setDomain(domain);
    	slot1.setDomainTargets(new DomainTarget[] {domainTarget1, domainTarget2});
    	slot1.setHostingStart(new Date());
        slot2.setDomain(domain);
        slot2.setDomainTargets(new DomainTarget[] {domainTarget1});
    	AccuracyTestHostingBlock block = new AccuracyTestHostingBlock();
    	block.setSlots(new HostingSlot[] {slot1, slot2});
    	game.setBlocks(new HostingBlock[] {block});
    	localEJB.createGame(game);

        manager.advanceHostingSlot(1001);

        HostingSlot[] slots = (HostingSlot[]) DataProvider.getObjects();
        assertNotNull("advanceHostingSlot method failed", slots[0].getHostingEnd());
        assertNotNull("advanceHostingSlot method failed", slots[1].getHostingStart());
        assertEquals("advanceHostingSlot method failed", slots[0].getSequenceNumber(), -1);
    }

    /**
     * Accuracy test the GameStartNotifier work thread works correctly.
     * @throws Exception to JUnit.
     */
    public void testGameStartNotifierThreadRunAccuracy() throws Exception {
    	AccuracyTestGame game1 = new AccuracyTestGame();
        game1.setId(new Long(1));
    	game1.setStartDate(new Date(new Date().getTime() + 3000));
    	AccuracyTestGame game2 = new AccuracyTestGame();
    	game2.setStartDate(new Date(new Date().getTime() + 5000));

    	manager.addNewNotStartedGame(game1);
    	manager.addNewNotStartedGame(game2);

    	assertEquals("addNewNotStartedGame method failed", manager.getAllCurrentNotStartedGames().length, 2);
    	Thread.sleep(10000);
    }

    /**
     * Accuracy tset the NewGameAvailableNotifier work thread works correctly.
     * @throws Exception to JUnit
     */
    public void testNewGameAvailableNotifierThreadRunAccuracy() throws Exception {
    	// add a new game
    	AccuracyTestGame game = new AccuracyTestGame();
    	game.setId(new Long(1003));
    	localEJB.createGame(game);

    	try {
    	    Thread.sleep(10000);
    	} catch (InterruptedException e) {
    	    throw e;
    	}

    	System.out.println(manager.getAllCurrentNotStartedGames().length);
    }

    /**
     * The accuracy test of the stop manager.
     *
     * @throws Exception to JUnit
     */
    public void testStopManagerAccuracy() throws Exception  {
        manager.stopManager();
        assertTrue("The manager can not be stopped.", manager.isStopped());
    }
}
