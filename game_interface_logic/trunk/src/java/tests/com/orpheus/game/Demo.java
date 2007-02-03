/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import java.util.Date;
import java.util.Iterator;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.orpheus.game.persistence.GameDataLocal;
import com.orpheus.game.persistence.GameDataLocalHome;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;
/**
 * <p>
 * The demo usage of the component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends TestCase {
    /**
     * The setUp of the unit test.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        /* We need to set MockContextFactory as our JNDI provider.
         * This method sets the necessary system properties.
         */
        MockContextFactory.setAsInitial();

        // create the initial context that will be used for binding EJBs
        Context context = new InitialContext();

        // Create an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        /* Create deployment descriptor of our sample bean.
         * MockEjb uses it instead of XML deployment descriptors
         */
        SessionBeanDescriptor sampleServiceDescriptor
            = new SessionBeanDescriptor("localjbossBean",
                    GameDataLocalHome.class, GameDataLocal.class, new GameDataBean());
        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();

        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }
        cm.add("config.xml");
        cm.add("objectFactory.xml");
    }

    /**
     * The tearDown of the unit test.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();

        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }
    }
    /**
     * The demo usage of <code>GameDataUtility</code>.
     *
     * @throws Exception to JUnit
     */
    public void testDemo_GameDataUtility() throws Exception {
        //create with default namespace and key
        GameDataManager manager = GameDataUtility.getConfiguredGameDataManagerInstance("manager");
        ((GameDataManagerImpl) manager).stopManager();
        //create with given namespace and key
        manager = GameDataUtility.getConfiguredGameDataManagerInstance(
                "com.orpheus.game.GameDataManager_ObjectFactory", "manager");
        ((GameDataManagerImpl) manager).stopManager();
    }
    /**
     * The demo usage of the GameDataManager.
     *
     * @throws Exception to JUnit
     */
    public void testDemo_GameDataManagerImpl() throws Exception {
        //create the manager with the namespace
        GameDataManagerImpl manager = new GameDataManagerImpl(new MockPuzzleTypeSource(),GameDataManagerImpl.class.getName());

        //this is a mocked game
        GameImpl game = new GameImpl();
        game.setStartDate(new Date());
        //add a new not started game
        manager.addNewNotStartedGame(game);

        //a new game avaliable come
        manager.newGameAvailable(game);

        //change the game status to started
        manager.gameStatusChangedToStarted(game);
        //remove the game to be started with the game id
        manager.removeStartedGameFromNotStartedList(1);

        //get all the games that are not started
        manager.getAllCurrentNotStartedGames();

        //test a slot, whether it is valid or not
        boolean success = manager.testUpcomingDomain(new HostingSlotImpl());
        //record winning bids
        manager.recordWinningBids(1, new long[]{1, 2});

        //advance a hosting slot with the game which id is given
        manager.advanceHostingSlot(1);
        manager.stopManager();
    }
}
