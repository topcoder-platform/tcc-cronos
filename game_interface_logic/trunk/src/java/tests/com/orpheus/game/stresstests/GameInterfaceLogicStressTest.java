/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.stresstests;

import java.io.File;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.TestCase;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.orpheus.administration.persistence.AdminDataLocal;
import com.orpheus.administration.persistence.AdminDataLocalHome;
import com.orpheus.game.MockAdminDataBean;
import com.orpheus.game.GameDataManagerImpl;
import com.orpheus.game.GameDataUtility;
import com.orpheus.game.persistence.BallColor;
import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.GameDataLocal;
import com.orpheus.game.persistence.GameDataLocalHome;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.PersistenceException;
import com.orpheus.game.persistence.SlotCompletion;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.puzzle.PuzzleData;
import com.topcoder.web.frontcontroller.results.DownloadData;

/**
 * The stress test suite of Game Interface Component 1.0 .
 *
 * @author still
 * @version 1.0
 */
public class GameInterfaceLogicStressTest extends TestCase {
    /** The max time of the test: 1000 milliseconds. */
    private static final long MAX_TIME = 1000;

    /**
     * <p>
     * The max iteration of the stress test.
     * </p>
     * <p>
     * Because GameDataManagerImpl class will create two threads, too many of GameDataManagerImpl will create many
     * threads, stop such many threads is a time-consuming work, and GameDataManagerImpl is always as a single
     * instance, here the test count is little in amount.
     * </p>
     */
    private static final int TEST_COUNT = 10;

    /**
     * <p>
     * The max thread number of tests in multi-threaded test.
     * </p>
     */
    private static final int THREAD_COUNT = 20;

    /**
     * <p>
     * All the test thread classes.
     * </p>
     */
    private static Class[] testThreadClasses = new Class[] {
        TestThread_AddNewNotStartedGame.class,
        TestThread_AdvanceHostingSlot.class,
        TestThread_GameStatusChangedToStarted.class,
        TestThread_IsStopped.class,
        TestThread_NewGameAvailable.class,
        TestThread_RecordWinningBids.class,
        TestThread_RemoveStartedGameFromNotStartedList.class,
        TestThread_SetCurrentNotStartedGames.class

    };

    /**
     * Sets up the environment for testing.
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void setUp() throws Exception {

        MockContextFactory.setAsInitial();
        Context context = new InitialContext();
        MockContainer container = new MockContainer(context);
        SessionBeanDescriptor localDescriptor = new SessionBeanDescriptor("GameDataLocal",
                GameDataLocalHome.class, GameDataLocal.class, new MockGameDataBean());
        SessionBeanDescriptor remoteDescriptor = new SessionBeanDescriptor("GameDataRemote", GameDataHome.class,
                GameData.class, new MockGameDataBean());
        SessionBeanDescriptor adminDescriptor = new SessionBeanDescriptor("localAdminDataBean",
                AdminDataLocalHome.class, AdminDataLocal.class, new MockAdminDataBean());

        container.deploy(localDescriptor);
        container.deploy(remoteDescriptor);
        container.deploy(adminDescriptor);
    }

    /**
     * Tears down the environment.
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void tearDown() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator iterator = cm.getAllNamespaces();

        while (iterator.hasNext()) {
            cm.removeNamespace((String) iterator.next());
        }
        MockContextFactory.revertSetAsInitial();
    }

    /**
     * Stress test of GameDataManager local creation.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateGameDataManager_Local() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(new File("test_files/stresstest/stress_config1.xml").getAbsolutePath());
        for (int i = 0; i < TEST_COUNT; i++) {
            GameDataManagerImpl manager = (GameDataManagerImpl) GameDataUtility
                    .getConfiguredGameDataManagerInstance("GameDataManager");
            manager.stopManager();
        }

        int count = Thread.activeCount();
        if (count > 0) {
            Thread[] tarray = new Thread[count];
            Thread.enumerate(tarray);
            for (int i = 0; i < tarray.length; i++) {
                if (tarray[i] != null && tarray[i] != Thread.currentThread()
                        && tarray[i].getName().startsWith("Thread-")) {
                }
            }
        }
    }

    /**
     * Stress test of GameDataManager remote creation.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateGameDataManager_Remote() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(new File("test_files/stresstest/stress_config2.xml").getAbsolutePath());
        for (int i = 0; i < TEST_COUNT; i++) {
            GameDataManagerImpl manager = (GameDataManagerImpl) GameDataUtility
                    .getConfiguredGameDataManagerInstance("GameDataManager");
            manager.stopManager();
        }

        int count = Thread.activeCount();
        if (count > 0) {
            Thread[] tarray = new Thread[count];
            Thread.enumerate(tarray);
            for (int i = 0; i < tarray.length; i++) {
                if (tarray[i] != null && tarray[i] != Thread.currentThread()
                        && tarray[i].getName().startsWith("Thread-")) {
                }
            }
        }
    }

    /**
     * Stress test of GameDataManager operation.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGameDataManager() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(new File("test_files/stresstest/stress_config1.xml").getAbsolutePath());
        GameDataManagerImpl manager = (GameDataManagerImpl) GameDataUtility
                .getConfiguredGameDataManagerInstance("GameDataManager");
        for (int i = 0; i < testThreadClasses.length; i++) {
            BaseTestThread thread = newTestThread(testThreadClasses[i], manager);
            long t = System.currentTimeMillis();
            for (int j = 0; j < TEST_COUNT; j++) {
                thread.run();
            }
            t = System.currentTimeMillis() - t;
            assertTrue("Should complete in less than 1 second.", t < MAX_TIME);
        }
        manager.stopManager();
    }

    /**
     * Stress test of GameDataManager operation multi-threaded.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGameDataManager_Multithread() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(new File("test_files/stresstest/stress_config1.xml").getAbsolutePath());
        GameDataManagerImpl manager =
            (GameDataManagerImpl) GameDataUtility.getConfiguredGameDataManagerInstance("GameDataManager");
        BaseTestThread[] threads = new BaseTestThread[THREAD_COUNT];
        Random rand = new Random();

        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = newTestThread(rand, manager);
        }
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i].start();
        }
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i].join();
        }
        manager.stopManager();

        int count = Thread.activeCount();
        if (count > 0) {
            Thread[] tarray = new Thread[count];
            Thread.enumerate(tarray);

            for (int i = 0; i < tarray.length; i++) {
                if (tarray[i] != null && tarray[i] != Thread.currentThread()
                        && tarray[i].getName().startsWith("Thread-")) {
                }
            }
        }
    }

    /**
     * <p>
     * Create new test thread instance with randomly type.
     * </p>
     *
     * @param rand
     *            the random instance
     * @param manager
     *            the GameDataManagerImpl manager passed to created test thread.
     * @return the created test thread
     * @throws Exception
     *             to JUnit
     */
    private BaseTestThread newTestThread(Random rand, GameDataManagerImpl manager) throws Exception {

        Class testThreadClass = testThreadClasses[rand.nextInt(testThreadClasses.length)];
        return newTestThread(testThreadClass, manager);

    }

    /**
     * <p>
     * Create new test thread instance with expected class type.
     * </p>
     *
     * @param testThreadClass
     *            the class of the create test thread
     * @param manager
     *            the GameDataManagerImpl manager passed to created test thread.
     * @return the created test thread
     * @throws Exception
     *             to JUnit
     */
    private BaseTestThread newTestThread(Class testThreadClass, GameDataManagerImpl manager) throws Exception {
        if (TestThread_AddNewNotStartedGame.class.equals(testThreadClass)) {
            return new TestThread_AddNewNotStartedGame(manager);
        }

        if (TestThread_AdvanceHostingSlot.class.equals(testThreadClass)) {
            return new TestThread_AdvanceHostingSlot(manager);
        }

        if (TestThread_GameStatusChangedToStarted.class.equals(testThreadClass)) {
            return new TestThread_GameStatusChangedToStarted(manager);
        }

        if (TestThread_IsStopped.class.equals(testThreadClass)) {
            return new TestThread_IsStopped(manager);
        }

        if (TestThread_NewGameAvailable.class.equals(testThreadClass)) {
            return new TestThread_NewGameAvailable(manager);
        }

        if (TestThread_RecordWinningBids.class.equals(testThreadClass)) {
            return new TestThread_RecordWinningBids(manager);
        }

        if (TestThread_RemoveStartedGameFromNotStartedList.class.equals(testThreadClass)) {
            return new TestThread_RemoveStartedGameFromNotStartedList(manager);
        }

        if (TestThread_SetCurrentNotStartedGames.class.equals(testThreadClass)) {
            return new TestThread_SetCurrentNotStartedGames(manager);
        }

        return null;
    }

    /**
     * Base class for thread class which is used to test GameDataManagerImpl.
     *
     * @author still
     * @version 1.0
     */
    private class BaseTestThread extends Thread {
        /** The GameDataManagerImpl instance for testing GameDataManagerImpl. */
        private final GameDataManagerImpl manager;

        /**
         * Constructor of BaseTestThread.
         *
         * @param manager
         *            the GameDataManagerImpl instance used in this test thread
         */
        public BaseTestThread(GameDataManagerImpl manager) {
            this.manager = manager;
        }

        /**
         * Get the GameDataManagerImpl instance.
         * @return the GameDataManagerImpl instance
         */
        public GameDataManagerImpl getGameDataManager() {
            return manager;
        }
    }

    /**
     * Thread class which is used to test GameDataManagerImpl#addNewNotStartedGame.
     *
     * @author still
     * @version 1.0
     */
    private class TestThread_AddNewNotStartedGame extends BaseTestThread {
        /**
         * Constructor of TestThread_AddNewNotStartedGame.
         *
         * @param manager
         *            the GameDataManagerImpl instance used in this test thread
         */
        public TestThread_AddNewNotStartedGame(GameDataManagerImpl manager) {
            super(manager);
        }
        /**
         * The main run body of the thread.
         */
        public void run() {
            try {
                sleep(0);
                getGameDataManager().addNewNotStartedGame(new MockGame(new Random().nextInt(10000000)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Thread class which is used to test GameDataManagerImpl#advanceHostingSlot.
     *
     * @author still
     * @version 1.0
     */
    private class TestThread_AdvanceHostingSlot extends BaseTestThread {
        /**
         * Constructor of TestThread_AdvanceHostingSlot.
         *
         * @param manager
         *            the GameDataManagerImpl instance used in this test thread
         */
        public TestThread_AdvanceHostingSlot(GameDataManagerImpl manager) {
            super(manager);
        }
        /**
         * The main run body of the thread.
         */
        public void run() {
            try {
                sleep(0);
                getGameDataManager().advanceHostingSlot(100);
            } catch (Exception e) {
                // ignore
                e.printStackTrace();
            }

        }
    }

    /**
     * Thread class which is used to test GameDataManagerImpl#recordWinningBids.
     *
     * @author still
     * @version 1.0
     */
    private class TestThread_RecordWinningBids extends BaseTestThread {
        /**
         * Constructor of TestThread_RecordWinningBids.
         *
         * @param manager
         *            the GameDataManagerImpl instance used in this test thread
         */
        public TestThread_RecordWinningBids(GameDataManagerImpl manager) {
            super(manager);
        }
        /**
         * The main run body of the thread.
         */
        public void run() {
            try {
                sleep(0);
                getGameDataManager().recordWinningBids(1, new long[] {1, 100});
            } catch (Exception e) {
                // ignore
                e.printStackTrace();
            }

        }
    }

    /**
     * Thread class which is used to test GameDataManagerImpl#newGameAvailable.
     *
     * @author still
     * @version 1.0
     */
    private class TestThread_NewGameAvailable extends BaseTestThread {
        /**
         * Constructor of TestThread_NewGameAvailable.
         *
         * @param manager
         *            the GameDataManagerImpl instance used in this test thread
         */
        public TestThread_NewGameAvailable(GameDataManagerImpl manager) {
            super(manager);
        }
        /**
         * The main run body of the thread.
         */
        public void run() {
            try {
                sleep(0);
                getGameDataManager().newGameAvailable(new MockGame(new Random().nextInt(10000000)));
            } catch (Exception e) {
                // ignore
                e.printStackTrace();
            }

        }
    }

    /**
     * Thread class which is used to test GameDataManagerImpl#gameStatusChangedToStarted.
     *
     * @author still
     * @version 1.0
     */
    private class TestThread_GameStatusChangedToStarted extends BaseTestThread {
        /**
         * Constructor of TestThread_GameStatusChangedToStarted.
         *
         * @param manager
         *            the GameDataManagerImpl instance used in this test thread
         */
        public TestThread_GameStatusChangedToStarted(GameDataManagerImpl manager) {
            super(manager);
        }
        /**
         * The main run body of the thread.
         */
        public void run() {
            try {
                sleep(0);
                getGameDataManager().gameStatusChangedToStarted(new MockGame(new Random().nextInt(10000000)));
            } catch (Exception e) {
                // ignore
                e.printStackTrace();
            }

        }
    }

    /**
     * Thread class which is used to test GameDataManagerImpl#setCurrentNotStartedGames.
     *
     * @author still
     * @version 1.0
     */
    private class TestThread_SetCurrentNotStartedGames extends BaseTestThread {
        /**
         * Constructor of TestThread_SetCurrentNotStartedGames.
         *
         * @param manager
         *            the GameDataManagerImpl instance used in this test thread
         */
        public TestThread_SetCurrentNotStartedGames(GameDataManagerImpl manager) {
            super(manager);
        }
        /**
         * The main run body of the thread.
         */
        public void run() {
            try {
                sleep(0);
                getGameDataManager().setCurrentNotStartedGames(new Game[] {
                    new MockGame(new Random().nextInt(10000000)),
                    new MockGame(new Random().nextInt(10000000)),
                    new MockGame(new Random().nextInt(10000000)),
                    new MockGame(new Random().nextInt(10000000)),
                    new MockGame(new Random().nextInt(10000000)) });
            } catch (Exception e) {
                // ignore
                e.printStackTrace();
            }

        }
    }

    /**
     * Thread class which is used to test GameDataManagerImpl#removeStartedGameFromNotStartedList.
     *
     * @author still
     * @version 1.0
     */
    private class TestThread_RemoveStartedGameFromNotStartedList extends BaseTestThread {
        /**
         * Constructor of TestThread_RemoveStartedGameFromNotStartedList.
         *
         * @param manager
         *            the GameDataManagerImpl instance used in this test thread
         */
        public TestThread_RemoveStartedGameFromNotStartedList(GameDataManagerImpl manager) {
            super(manager);
        }
        /**
         * The main run body of the thread.
         */
        public void run() {
            try {
                sleep(0);
                Game game = new MockGame(new Random().nextInt(10000000));
                getGameDataManager().addNewNotStartedGame(game);
                getGameDataManager().removeStartedGameFromNotStartedList(game.getId().longValue());
            } catch (Exception e) {
                // ignore
                e.printStackTrace();
            }

        }
    }

    /**
     * Thread class which is used to test GameDataManagerImpl#isStopped.
     *
     * @author still
     * @version 1.0
     */
    private class TestThread_IsStopped extends BaseTestThread {
        /**
         * Constructor of TestThread_IsStopped.
         *
         * @param manager
         *            the GameDataManagerImpl instance used in this test thread
         */
        public TestThread_IsStopped(GameDataManagerImpl manager) {
            super(manager);
        }
        /**
         * The main run body of the thread.
         */
        public void run() {
            try {
                getGameDataManager().isStopped();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Mock SessionBean of GameData.
     *
     * @author still
     * @version 1.0
     */
    private class MockGameDataBean implements SessionBean {
        /**
         * Represents the session context of this bean.
         */
        private SessionContext sessionContext = null;

        /**
         * <p>
         * Empty constructor.
         * </p>
         */
        public MockGameDataBean() {

        }

        /**
         * <p>
         * Creates the bean.
         * </p>
         *
         */
        public void ejbCreate() {
        }

        /**
         * <p>
         * Removes the bean.
         * </p>
         *
         */
        public void ejbRemove() {
        }

        /**
         * <p>
         * Activates the bean.
         * </p>
         *
         */
        public void ejbActivate() {
        }

        /**
         * <p>
         * Passivates the bean.
         * </p>
         *
         */
        public void ejbPassivate() {
        }

        /**
         * <p>
         * Sets the session context.
         * </p>
         *
         * @param sessionContext
         *            The session context
         * @throws EJBException never
         * @throws RemoteException never
         */
        public void setSessionContext(SessionContext sessionContext) throws RemoteException {
            this.sessionContext = sessionContext;
        }

        /**
         * <p>
         * Creates a new game entity in the persistent store, along with associated hosting blocks. Any game or
         * blockIDs that are null will be automatically assigned acceptable values. No hosting slots are created
         * for the game at this time. The returned Game object will represent the persisted data, including any IDs
         * assigned to the game and blocks.
         * </p>
         *
         * @param game
         *            the game
         *
         * @return the game with the id
         *
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         * @throws IllegalArgumentException
         *             If game is null
         */
        public Game createGame(Game game) throws PersistenceException {
            return game;
        }

        /**
         * <p>
         * Creates hosting slots associates with the specified Bid IDs in the specified hosting block Uses the
         * GameDataDAOFActory to obtain the GameDataDAO to perform this action.
         * </p>
         *
         * @param blockId
         *            the block id
         * @param bidIds
         *            the bid ids
         *
         * @return array of hosting slots
         *
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         * @throws IllegalArgumentException
         *             If bidIds is null
         */
        public HostingSlot[] createSlots(long blockId, long[] bidIds) throws PersistenceException {
            return new HostingSlot[0];
        }

        /**
         * <p>
         * Creates a new persistent domain representation with the data from the provided Domain object and its
         * nested ImageInfo objects. Any null Domain or ImageIndo IDs are assigned appropriate values. The returned
         * Domain will reflect the persistent representation, including any automatically assigned IDs.
         * </p>
         *
         * @param domain
         *            the domain
         *
         * @return the domain with id
         *
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         * @throws IllegalArgumentException
         *             If domain is null
         */
        public Domain createDomain(Domain domain) throws PersistenceException {
            return domain;
        }

        /**
         * <p>
         * Creates a new, persistent hosting block for the specified game. The block will having an auto-assigned
         * ID, the next available sequence number after those of the game's existing blocks (or 1 if there are no
         * other blocks), no hosting slots, and the specified maximum hosting time per slot. It returns a
         * HostingBlock object representing the new block.
         * </p>
         *
         * @param gameId
         *            the game id
         * @param slotMaxHostingTime
         *            the max hosting time for the slot
         *
         * @return the hosting block
         *
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         */
        public HostingBlock addBlock(long gameId, int slotMaxHostingTime) throws PersistenceException {
            return new HostingBlock() {
                public Long getId() {
                    return new Long(100);
                }

                public int getMaxHostingTimePerSlot() {
                    return 0;
                }

                public int getSequenceNumber() {
                    return 0;
                }

                public HostingSlot[] getSlots() {
                    return new HostingSlot[0];
                }

            };
        }

        /**
         * <p>
         * Retrieves a Game object representing the Game having the specified ID.
         * </p>
         *
         * @param gameId
         *            the game id
         *
         * @return the game
         *
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         */
        public Game getGame(long gameId) throws PersistenceException {
            MockGame game = new MockGame(gameId);
            return game;
        }

        /**
         * <p>
         * Retrieves a HostingBlock object representing the hosting block having the specified ID.
         * </p>
         *
         * @param blockId
         *            the block id
         *
         * @return the block
         *
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         */
        public HostingBlock getBlock(long blockId) throws PersistenceException {
            return new HostingBlock() {
                public Long getId() {
                    return new Long(100);
                }

                public int getMaxHostingTimePerSlot() {
                    return 0;
                }

                public int getSequenceNumber() {
                    return 0;
                }

                public HostingSlot[] getSlots() {
                    return new HostingSlot[0];
                }

            };
        }

        /**
         * <p>
         * Retrieves a HostingSlot object representing the slot having the specified ID.
         * </p>
         *
         * @param slotId
         *            the slot id
         *
         * @return the slot
         *
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         */
        public HostingSlot getSlot(long slotId) throws PersistenceException {
            return null;
        }

        /**
         * <p>
         * Retrieves the DownloadData object corresponding to the specified ID.
         * </p>
         *
         * @param id
         *            the download id
         *
         * @return the download data
         *
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         */
        public DownloadData getDownloadData(long id) throws PersistenceException {
            return null;
        }

        /**
         * <p>
         * Retrieves a Domain object representing the domain corresponding to the specified ID.
         * </p>
         *
         * @param domainId
         *            the domain id
         *
         * @return the domain
         *
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         */
        public Domain getDomain(long domainId) throws PersistenceException {
            return null;
        }

        /**
         * <p>
         * Returns the key text for the specified player's completions of the specified slots. The length of the
         * returned array is the same as the length of the slotIds argument, and their elements correspond: each
         * string in the returned array is the key text associated with the slot completion by the specified player
         * of the slot whose ID appears at the same index in the input slotIds. If the specified player has not
         * completed any particular slot specified among the slot IDs then the corresponding element or the
         * returned array is null.
         * </p>
         *
         * @param playerId
         *            the player id
         * @param slotIds
         *            the slot ids
         *
         * @return the player's keys
         *
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         * @throws IllegalArgumentException
         *             If slotIds is null
         */
        public String[] getKeysForPlayer(long playerId, long[] slotIds) throws PersistenceException {
            return null;
        }

        /**
         * <p>
         * Retrieves the PuzzleData associated with the specified puzzle ID.
         * </p>
         *
         * @param puzzleId
         *            the puzzle id
         *
         * @return the puzzle data
         *
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         */
        public PuzzleData getPuzzle(long puzzleId) throws PersistenceException {
            return null;
        }

        /**
         * <p>
         * Increments the download count for the plugin identified by the specified name.
         * </p>
         *
         * @param pluginName
         *            the plugin name
         *
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         * @throws IllegalArgumentException
         *             If pluginName is null/empty
         */
        public void recordPluginDownload(String pluginName) throws PersistenceException {
        }

        /**
         * <p>
         * Records the specified player's registration for the specified game.
         * </p>
         *
         * @param playerId
         *            the player id
         * @param gameId
         *            the game id
         *
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         */
        public void recordRegistration(long playerId, long gameId) throws PersistenceException {
        }

        /**
         * <p>
         * Records the completion of the specified slot by the specified player at the specified date and time, and
         * generates a key for the player to associate with the completion.
         * </p>
         *
         * @param playerId
         *            the player id
         * @param slotId
         *            the slot id
         * @param date
         *            date of completion
         *
         * @return the slot completion entity
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         * @throws IllegalArgumentException
         *             If date is null
         */
        public SlotCompletion recordSlotCompletion(long playerId, long slotId, java.util.Date date)
            throws PersistenceException {
            return null;
        }

        /**
         * <p>
         * Records the fact that the specified player has completed the specified game. Whether or not such a
         * player actually wins the game depends on whether others have already completed the game, and on
         * administrative establishment of winner eligibility.
         * </p>
         *
         * @param playerId
         *            the player id
         * @param gameId
         *            the game id
         *
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         */
        public void recordGameCompletion(long playerId, long gameId) throws PersistenceException {
        }

        /**
         * <p>
         * Records a binary object in the database, such as might later be retrieved by the custom DownloadSource.
         * The ID assigned to the binary object is returned.
         * </p>
         *
         * @param name
         *            the name of the object
         * @param mediaType
         *            the media type
         * @param content
         *            the content as a byte array
         *
         * @return the binary object
         *
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         * @throws IllegalArgumentException
         *             If name or mediaType is null/empty, or content is null
         */
        public long recordBinaryObject(String name, String mediaType, byte[] content) throws PersistenceException {
            return 0;
        }

        /**
         * <p>
         * Updates the persistent hosting slot information for the existing slots represented by the specified
         * HostingSlot objects, so that the persistent representation matches the provided objects. Nested
         * DomainTarget objects may or may not already be recorded in persistence; the component assumes that
         * DomainTarget's with null IDs are new, and that others already exist in the database. The component will
         * assign IDs for new DomainTargets as needed. This method will also update the following additional
         * HostingSlot properties (only): sequence number, hosting start, hosting end, brain teaser IDs, puzzle ID.
         * It will return an array containing the revised hosting slots.
         * </p>
         *
         * @param slots
         *            the hosting slots to update
         *
         * @return the updated hosting slots
         *
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         * @throws IllegalArgumentException
         *             If slots is null
         */
        public HostingSlot[] updateSlots(HostingSlot[] slots) throws PersistenceException {
            return new HostingSlot[0];
        }

        /**
         * <p>
         * Updates the persistent domain information for the specified Domain object to match the Domain object,
         * where the appropriate persistent record is identified by the Domain's ID.
         * </p>
         *
         * @param domain
         *            domain to update
         *
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         * @throws IllegalArgumentException
         *             If domain is null
         */
        public void updateDomain(Domain domain) throws PersistenceException {
        }

        /**
         * <p>
         * Deletes the hosting slot having the specified ID.
         * </p>
         *
         * @param slotId
         *            slot id
         *
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         */
        public void deleteSlot(long slotId) throws PersistenceException {
        }

        /**
         * <p>
         * Looks up all distinct domains hosting any slot in any active game, and returns an array of Domain
         * objects representing the results.
         * </p>
         *
         * @return array of active domains
         *
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         */
        public Domain[] findActiveDomains() throws PersistenceException {
            return new Domain[0];
        }

        /**
         * <p>
         * Looks up all ongoing games in which a domain matching the specified string is a host in a slot that the
         * specified player has not yet completed, and returns an array of all such games.
         * </p>
         *
         * @param domain
         *            the domain
         * @param playerId
         *            the player id
         *
         * @return array of games
         *
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         * @throws IllegalArgumentException
         *             If domain is null/empty
         */
        public Game[] findGamesByDomain(String domain, long playerId) throws PersistenceException {
            return new Game[0];
        }

        /**
         * <p>
         * Looks up all hosting slots completed by any player in the specified game, and returns the results as an
         * array of HostingSlot objects. Returned slots are in ascending order by first completion time, or
         * equivalently, in ascending order by hosting block sequence number and hosting slot sequence number.
         * </p>
         *
         * @param gameId
         *            the game id
         *
         * @return array of hosting slots
         *
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         */
        public HostingSlot[] findCompletedSlots(long gameId) throws PersistenceException {
            return new HostingSlot[0];
        }

        /**
         * <p>
         * Looks up all recorded completion events that have the specified hosting slot in the specified game.
         * </p>
         *
         * @param gameId
         *            the game id
         * @param slotId
         *            the slot id
         *
         * @return array of slot competition events
         *
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         */
        public SlotCompletion[] findSlotCompletions(long gameId, long slotId) throws PersistenceException {
            return new SlotCompletion[0];
        }

        /**
         * <p>
         * Retrieves game information for games meeting the specified game status criteria.
         * </p>
         *
         * @param isStarted
         *            a Boolean having value true to restrict to games that have started or false to restrict to
         *            games that have not yet started; null to ignore whether games have started
         * @param isEnded
         *            a Boolean having value true to restrict to games that have ended or false to restrict to
         *            games that have not yet ended; null to ignore whether games have ended
         *
         * @return an array of Game objects representing the games found
         *
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         * @throws IllegalArgumentException
         *             If isStarted == false and isEnded == true (impossible combo)
         */
        public Game[] findGames(Boolean isStarted, Boolean isEnded) throws PersistenceException {
            return new Game[] {new MockGame(100)};
        }

        /**
         * <p>
         * Looks up all the games for which the specified player is registered, and returns an array of their IDs.
         * </p>
         *
         * @param playerId
         *            the player id
         *
         * @return the array of game ids
         *
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         */
        public long[] findGameRegistrations(long playerId) throws PersistenceException {
            return new long[0];
        }

        /**
         * <p>
         * Looks up all domains associated with the specified sponsor and returns an array of Domain objects
         * representing them.
         * </p>
         *
         * @param sponsorId
         *            the sponsor id
         *
         * @return array of domains
         *
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         */
        public Domain[] findDomainsForSponsor(long sponsorId) throws PersistenceException {
            return new Domain[0];
        }

        /**
         * <p>
         * Finds the first HostingSlot in the hosting sequence for the specified game that is assigned the
         * specified domain and has not yet been completed by the specified player.
         * </p>
         *
         * @param gameId
         *            the game id
         * @param playerId
         *            the player id
         * @param domain
         *            the domain
         *
         * @return hosting slot
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         * @throws IllegalArgumentException
         *             If domain is null/empty
         */
        public HostingSlot findSlotForDomain(long gameId, long playerId, String domain)
            throws PersistenceException {
            return null;
        }

        /**
         * <p>
         * Provides information about all ball colors available to the application.
         * </p>
         *
         * @return array of ball colors
         *
         * @throws PersistenceException
         *             If there is any problem in the persistence layer.
         */
        public BallColor[] findAllBallColors() throws PersistenceException {
            return new BallColor[0];
        }
    }

    /**
     * Mock implementation of game interface.
     *
     * @author still
     * @version 1.0
     */
    private class MockGame implements Game {
        /**
         * The game id.
         */
        private final Long id;

        /**
         * The start date.
         */
        private Date startDate;

        /**
         * Constructor of MockGame.
         * @param id the game id
         */
        public MockGame(long id) {
            this.id = new Long(id);
            startDate = new Date();
        }

        /**
         * Get the ID.
         *
         * @return the id
         */
        public Long getId() {
            return id;
        }

        /**
         * Get the name.
         *
         * @return the name
         */
        public String getName() {
            return "name";
        }

        /**
         * Return ball color.
         *
         * @return null
         */
        public BallColor getBallColor() {
            return null;
        }

        /**
         * Get the key count.
         *
         * @return 0
         */
        public int getKeyCount() {
            return 0;
        }

        /**
         * Set the start date.
         *
         * @param startDate
         *            the date to be set.
         */
        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        /**
         * Get the start date.
         *
         * @return the current time
         */
        public Date getStartDate() {
            return startDate;
        }

        /**
         * Get the end date.
         *
         * @return the end date
         */
        public Date getEndDate() {
            return new Date();
        }

        /**
         * Get an array of HostingBlock.
         *
         * @return an empty array of HostingBlock
         */
        public HostingBlock[] getBlocks() {
            return new HostingBlock[0];
        }

    }
}
