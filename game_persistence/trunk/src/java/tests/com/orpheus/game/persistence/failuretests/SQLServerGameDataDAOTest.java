/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.persistence.failuretests;

import java.util.Date;

import junit.framework.TestCase;

import com.orpheus.game.persistence.BallColor;
import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.DuplicateEntryException;
import com.orpheus.game.persistence.EntryNotFoundException;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.ImageInfo;
import com.orpheus.game.persistence.InstantiationException;
import com.orpheus.game.persistence.PersistenceException;
import com.orpheus.game.persistence.dao.SQLServerGameDataDAO;
import com.orpheus.game.persistence.entities.BallColorImpl;
import com.orpheus.game.persistence.entities.DomainImpl;
import com.orpheus.game.persistence.entities.GameImpl;
import com.orpheus.game.persistence.entities.ImageInfoImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * Test case for <code>SQLServerGameDataDAO</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class SQLServerGameDataDAOTest extends TestCase {

    /**
     * Represents the dao to test.
     */
    private SQLServerGameDataDAO dao;

    /**
     * Set up the environment.
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.loadConfig();
        FailureTestHelper.loadData();
        dao = new SQLServerGameDataDAO("com.orpheus.game.persistence.SQLServerGameDataDAO");
    }

    /**
     * Clean up the environment.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        FailureTestHelper.unloadData();
        FailureTestHelper.unloadConfig();
    }

    /**
     * Test method for SQLServerGameDataDAO(java.lang.String).
     * In this case, the namespace is null.
     * @throws Exception to JUnit
     */
    public void testSQLServerGameDataDAO_NullNamespace() throws Exception {
        try {
            new SQLServerGameDataDAO(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for SQLServerGameDataDAO(java.lang.String).
     * In this case, the namespace is empty.
     * @throws Exception to JUnit
     */
    public void testSQLServerGameDataDAO_EmptyNamespace() throws Exception {
        try {
            new SQLServerGameDataDAO(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for SQLServerGameDataDAO(java.lang.String).
     * In this case, the namespace contains no colors definition.
     * @throws Exception to JUnit
     */
    public void testSQLServerGameDataDAO_NoColorsDefinition() throws Exception {
        try {
            FailureTestHelper.unloadConfig();

            ConfigManager cm = ConfigManager.getInstance();
            cm.add("failuretests/db_connection_factory.xml");
            cm.add("failuretests/game_persistence.xml");
            cm.add("failuretests/object_factory.xml");
            cm.add("failuretests/random_string_image_invalid1.xml");
            cm.add("failuretests/colors.xml");

            new SQLServerGameDataDAO("com.orpheus.game.persistence.SQLServerGameDataDAO");
            fail("InstantiationException expected.");
        } catch (InstantiationException e) {
            // should land here
        }
    }

    /**
     * Test method for SQLServerGameDataDAO(java.lang.String).
     * In this case, the namespace contains no image definition.
     * @throws Exception to JUnit
     */
    public void testSQLServerGameDataDAO_NoImageDefinition() throws Exception {
        try {
            FailureTestHelper.unloadConfig();

            ConfigManager cm = ConfigManager.getInstance();
            cm.add("failuretests/db_connection_factory.xml");
            cm.add("failuretests/game_persistence.xml");
            cm.add("failuretests/object_factory.xml");
            cm.add("failuretests/random_string_image_invalid2.xml");
            cm.add("failuretests/colors.xml");

            new SQLServerGameDataDAO("com.orpheus.game.persistence.SQLServerGameDataDAO");
            fail("InstantiationException expected.");
        } catch (InstantiationException e) {
            // should land here
        }
    }

    /**
     * Test method for SQLServerGameDataDAO(java.lang.String).
     * In this case, the namespace contains no ojbect factory definition.
     * @throws Exception to JUnit
     */
    public void testSQLServerGameDataDAO_NoObjectFactoryDefinition() throws Exception {
        try {
            FailureTestHelper.unloadConfig();

            ConfigManager cm = ConfigManager.getInstance();
            cm.add("failuretests/db_connection_factory.xml");
            cm.add("failuretests/game_persistence.xml");
//            cm.add("failuretests/object_factory.xml");
            cm.add("failuretests/random_string_image.xml");
            cm.add("failuretests/colors.xml");

            new SQLServerGameDataDAO("com.orpheus.game.persistence.SQLServerGameDataDAO");
            fail("InstantiationException expected.");
        } catch (InstantiationException e) {
            // should land here
        }
    }

    /**
     * Test method for createGame(com.orpheus.game.persistence.Game).
     * In this case, the game is null.
     * @throws Exception to JUnit
     */
    public void testCreateGame_NullGame() throws Exception {
        try {
            dao.createGame(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createGame(com.orpheus.game.persistence.Game).
     * In this case, there is no color.
     * @throws Exception to JUnit
     */
    public void testCreateGame_GameNoColor() throws Exception {
        try {
            GameImpl game = new GameImpl(
                    new Long(1),
                    new BallColorImpl(new Long(100), "test", 1),
                    1, new Date(10), new Date(100), new HostingBlock[0]);
            dao.createGame(game);
            fail("EntryNotFoundException expected.");
        } catch (EntryNotFoundException e) {
            // should land here
        }
    }

    /**
     * Test method for createGame(com.orpheus.game.persistence.Game).
     * In this case, the game has been persisted.
     * @throws Exception to JUnit
     */
    public void testCreateGame_GameDuplicated() throws Exception {
        try {
            long ballid = FailureTestHelper.getId("ball_color");
            GameImpl game = new GameImpl(
                    new Long(1),
                    new BallColorImpl(new Long(ballid), "test", 1),
                    1, new Date(10), new Date(100), new HostingBlock[0]);
            Game g = dao.createGame(game);
            dao.createGame(g);
            fail("DuplicateEntryException expected.");
        } catch (DuplicateEntryException e) {
            // should land here
        }
    }

    /**
     * Test method for createSlots(long, long[]).
     * In this case, the bidids is null.
     * @throws Exception to JUnit
     */
    public void testCreateSlots_NullBidIds() throws Exception {
        try {
            dao.createSlots(1, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createDomain(com.orpheus.game.persistence.Domain).
     * In this case, the domain is null.
     * @throws Exception to JUnit
     */
    public void testCreateDomain_NullDomain() throws Exception {
        try {
            dao.createDomain(null);
            fail("IllegalArgumentException expeced");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createDomain(com.orpheus.game.persistence.Domain).
     * In this case, the image info is not persisted.
     * @throws Exception to JUnit
     */
    public void testCreateDomain_NoImageInfo() throws Exception {
        try {
            Domain dom = new DomainImpl(new Long(1), 1, "test", Boolean.FALSE,
                    new ImageInfo[] {new ImageInfoImpl(new Long(1), 1, "test", Boolean.FALSE)});
            dao.createDomain(dom);
            fail("EntryNotFoundException expeced");
        } catch (EntryNotFoundException e) {
            // should land here
        }
    }

    /**
     * Test method for addBlock(long, int).
     * In this case, the game is not persisted.
     * @throws Exception to JUnit
     */
    public void testAddBlock_NoGame() throws Exception {
        try {
            dao.addBlock(1, 1);
            fail("EntryNotFoundException expected");
        } catch (EntryNotFoundException e) {
            // should land here
        }
    }

    /**
     * Test method for getGame(long).
     * In this case, the game is not persisted.
     * @throws Exception to JUnit
     */
    public void testGetGame() throws Exception {
        try {
            dao.getGame(1);
            fail("EntryNotFoundException expected");
        } catch (EntryNotFoundException e) {
            // should land here
        }
    }

    /**
     * Test method for getBlock(long).
     * In this case, the block is not persisted.
     * @throws Exception to JUnit
     */
    public void testGetBlock_NoBlock() throws Exception {
        try {
            dao.getBlock(1);
            fail("EntryNotFoundException expected");
        } catch (EntryNotFoundException e) {
            // should land here
        }
    }

    /**
     * Test method for getSlot(long).
     * In this case, the slot is not persisted.
     * @throws Exception to JUnit
     */
    public void testGetSlot_NoSlot() throws PersistenceException {
        try {
            dao.getSlot(1);
            fail("EntryNotFoundException expected");
        } catch (EntryNotFoundException e) {
            // should land here
        }
    }

    /**
     * Test method for getDownloadData(long).
     * In this case, the data is not persisted.
     * @throws Exception to JUnit
     */
    public void testGetDownloadData_NoData() throws Exception {
        try {
            FailureTestHelper.unloadData();
            dao.getDownloadData(1);
            fail("EntryNotFoundException expected");
        } catch (EntryNotFoundException e) {
            // should land here
        }
    }

    /**
     * Test method for getDomain(long).
     * In this case, the domain is not persisted.
     * @throws Exception to JUnit
     */
    public void testGetDomain_NoDomain() throws PersistenceException {
        try {
            dao.getDomain(1);
            fail("EntryNotFoundException expected");
        } catch (EntryNotFoundException e) {
            // should land here
        }
    }

    /**
     * Test method for getKeysForPlayer(long, long[]).
     * In this case, the ids is null.
     * @throws Exception to JUnit
     */
    public void testGetKeysForPlayer_NullIds() throws Exception {
        try {
            dao.getKeysForPlayer(1, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for getKeysForPlayer(long, long[]).
     * In this case, the player is not persisted.
     * @throws Exception to JUnit
     */
    public void testGetKeysForPlayer_NoPlayer() throws Exception {
        try {
            dao.getKeysForPlayer(1, new long[0]);
            fail("EntryNotFoundException expected.");
        } catch (EntryNotFoundException e) {
            // should land here
        }
    }

    /**
     * Test method for getPuzzle(long).
     * In this case, the player is not persisted.
     * @throws Exception to JUnit
     */
    public void testGetPuzzle_NoPuzzle() throws PersistenceException {
        try {
            dao.getPuzzle(1);
            fail("EntryNotFoundException expected.");
        } catch (EntryNotFoundException e) {
            // should land here
        }
    }

    /**
     * Test method for recordPluginDownload(java.lang.String).
     * In this case, the name is null.
     * @throws Exception to JUnit
     */
    public void testRecordPluginDownload_NullName() throws Exception {
        try {
            dao.recordPluginDownload(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for recordPluginDownload(java.lang.String).
     * In this case, the name is empty.
     * @throws Exception to JUnit
     */
    public void testRecordPluginDownload_EmptyName() throws Exception {
        try {
            dao.recordPluginDownload(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for recordPluginDownload(java.lang.String).
     * In this case, the name is not persisted.
     * @throws Exception to JUnit
     */
    public void testRecordPluginDownload_NonPersistedName() throws Exception {
        try {
            dao.recordPluginDownload("test");
            fail("EntryNotFoundException expected");
        } catch (EntryNotFoundException e) {
            // should land here
        }
    }

    /**
     * Test method for updateSlots(com.orpheus.game.persistence.HostingSlot[]).
     * In this case, the input is null.
     * @throws PersistenceException to JUnit
     */
    public void testUpdateSlots_NullSlots() throws PersistenceException {
        try {
            dao.updateSlots(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for updateDomain(com.orpheus.game.persistence.Domain).
     * In this case, the domain is null.
     * @throws PersistenceException to JUnit
     */
    public void testUpdateDomain_NullDomain() throws PersistenceException {
        try {
            dao.updateDomain(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

}
