/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.persistence.failuretests;

import com.orpheus.game.persistence.PersistenceException;
import com.orpheus.game.persistence.ejb.GameDataBean;

import junit.framework.TestCase;

/**
 * Test case for <code>GameDataBean</code>.
 * The persistence exceptions will be tested in SQLServerGameDataDAOTests.
 *
 * @author assistant
 * @version 1.0
 */
public class GameDataBeanTest extends TestCase {

    /**
     * Represents the bean to test.
     */
    private GameDataBean bean;

    /**
     * Set up the environment.
     */
    protected void setUp() throws Exception {
        super.setUp();
        bean = new GameDataBean();
    }

    /**
     * Clean up the environment.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test method for {@link com.orpheus.game.persistence.ejb.GameDataBean#createGame(com.orpheus.game.persistence.Game)}.
     * In this case, the game is null.
     * @throws Exception to JUnit
     */
    public void testCreateGame_NullGame() throws Exception {
        try {
            bean.createGame(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createSlots(long, long[]).
     * In this case, bidIds is null.
     * @throws Exception to JUnit
     */
    public void testCreateSlots_NullBidIds() throws Exception {
        try {
            bean.createSlots(1, null);
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
            bean.createDomain(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for getKeysForPlayer(long, long[]).
     * In this case, the slotIds is null.
     * @throws Exception to JUnit
     */
    public void testGetKeysForPlayer_NullSlotIds() throws Exception {
        try {
            bean.getKeysForPlayer(1, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
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
            bean.recordPluginDownload(null);
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
            bean.recordPluginDownload(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for recordSlotCompletion(long, long, java.util.Date).
     * @throws Exception to JUnit
     */
    public void testRecordSlotCompletion_NullDate() throws Exception {
        try {
            bean.recordSlotCompletion(1, 1, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for recordBinaryObject(java.lang.String, java.lang.String, byte[]).
     * In this case, the mediaType is null.
     * @throws Exception to JUnit
     */
    public void testRecordBinaryObject_NullName() throws Exception {
        try {
            bean.recordBinaryObject(null, "text", new byte[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for recordBinaryObject(java.lang.String, java.lang.String, byte[]).
     * In this case, the mediaType is empty.
     * @throws Exception to JUnit
     */
    public void testRecordBinaryObject_EmptyName() throws Exception {
        try {
            bean.recordBinaryObject(" ", "text", new byte[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for recordBinaryObject(java.lang.String, java.lang.String, byte[]).
     * In this case, the mediaType is null.
     * @throws Exception to JUnit
     */
    public void testRecordBinaryObject_NullType() throws Exception {
        try {
            bean.recordBinaryObject("name", null, new byte[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for recordBinaryObject(java.lang.String, java.lang.String, byte[]).
     * In this case, the mediaType is empty.
     * @throws Exception to JUnit
     */
    public void testRecordBinaryObject_EmptyType() throws Exception {
        try {
            bean.recordBinaryObject("name", " ", new byte[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for recordBinaryObject(java.lang.String, java.lang.String, byte[]).
     * In this case, the content is null.
     * @throws Exception to JUnit
     */
    public void testRecordBinaryObject_NullContent() throws Exception {
        try {
            bean.recordBinaryObject("name", "text", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for updateSlots(com.orpheus.game.persistence.HostingSlot[]).
     * In this case, the slots is null.
     * @throws Exception to JUnit
     */
    public void testUpdateSlots_NullSlots() throws Exception {
        try {
            bean.updateSlots(null);
            fail("IllegalArgmentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for updateDomain(com.orpheus.game.persistence.Domain).
     * In this case, the domain is null.
     * @throws Exception to JUnit
     */
    public void testUpdateDomain_Null() throws Exception {
        try {
            bean.updateDomain(null);
            fail("IllegalArgmentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for findGamesByDomain(java.lang.String, long).
     * In this case, the domain is null.
     * @throws Exception to JUnit
     */
    public void testFindGamesByDomain_NullDomain() throws Exception {
        try {
            bean.findGamesByDomain(null, 1);
            fail("IllegalArgumentExcepton expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for findGamesByDomain(java.lang.String, long).
     * In this case, the domain is null.
     * @throws Exception to JUnit
     */
    public void testFindGamesByDomain_EmptyDomain() throws Exception {
        try {
            bean.findGamesByDomain(" ", 1);
            fail("IllegalArgumentExcepton expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for findSlotForDomain(long, long, java.lang.String).
     * In this case, the domain is null.
     * @throws Exception to JUnit
     */
    public void testFindSlotForDomain_NullDomain() throws Exception {
        try {
            bean.findSlotForDomain(1, 1, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for findSlotForDomain(long, long, java.lang.String).
     * In this case, the domain is empty.
     * @throws Exception to JUnit
     */
    public void testFindSlotForDomain_EmptyDomain() throws Exception {
        try {
            bean.findSlotForDomain(1, 1, " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

}
