/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests.mock;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.SlotCompletion;

/**
 * Mock Bean for testing purpose.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockBean implements SessionBean {
    /**
     * Gets the game for the given game id.
     * 
     * @param gameId
     *            game id
     * @return a mock game.
     */
    public Game getGame(long gameId) {
        if (gameId < 0) {
            return null;
        }
        return new MockGame(new Long(gameId));
    }

    /**
     * Retrieves game information for games meeting the specified game status
     * criteria.
     * 
     * @param isStarted
     *            a Boolean having value true to restrict to games that have
     *            started or false to restrict to games that have not yet
     *            started; null to ignore whether games have started
     * @param isEnded
     *            a Boolean having value true to restrict to games that have
     *            ended or false to restrict to games that have not yet ended;
     *            null to ignore whether games have ended
     * @return a pre-defined set of games
     */
    public Game[] findGames(Boolean isStarted, Boolean isEnded) {
        return getMockGames();
    }

    /**
     * Looks up all ongoing games in which a domain matching the specified
     * string is a host in a slot that the specified player has not yet
     * completed, and returns an array of all such games.
     * 
     * @param domain
     *            domain
     * @param playerId
     *            player id
     * @return a set of mock games.
     */
    public Game[] findGamesByDomain(String domain, long playerId) {
        return getMockGames();
    }

    /**
     * Looks up all the games for which the specified player is registered, and
     * returns an array of their IDs.
     * 
     * @param playerId
     *            player id
     * @return a predefined game ids.
     */
    public long[] findGameRegistrations(long playerId) {
        long[] gameids = new long[] {playerId, playerId + 1};
        return gameids;
    }

    /**
     * Finds the first HostingSlot in the hosting sequence for the specified
     * game that is assigned the specified domain and has not yet been completed
     * by the specified player.
     * 
     * @param gameId
     *            game id
     * @param playerId
     *            player id
     * @param domain
     *            domain
     * @return a mock HostingSlot
     */
    public HostingSlot findSlotForDomain(long gameId, long playerId,
            String domain) {
        return new MockHostingSlot(gameId, gameId, (int) gameId);
    }

    /**
     * Looks up all hosting slots completed by any player in the specified game,
     * and returns the results as an array of HostingSlot objects. Returned
     * slots are in ascending order by first completion time, or equivalently,
     * in ascending order by hosting block sequence number and hosting slot
     * sequence number.
     * 
     * @param gameId
     *            game id
     * @return predefined HostingSlots
     */
    public HostingSlot[] findCompletedSlots(long gameId) {
        if (gameId > 3) {
            return new HostingSlot[0];
        }
        int[] seq_ids = new int[] {10, 4, 7};
        HostingSlot[] slots = new HostingSlot[2];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = new MockHostingSlot(seq_ids[(int)gameId - 1] + i, 
                    seq_ids[(int)gameId - 1] + i, seq_ids[(int)gameId - 1] + i);
        }
        return slots;
    }

    /**
     * Mock implementation which will return a predefined SlotCompletions array.
     * 
     * @param gameId
     *            game id
     * @param slotId
     *            slot id
     * @return predefined slot completions.
     * @throws RemoteException
     * @throws GameDataException
     */
    public SlotCompletion[] findSlotCompletions(long gameId, long slotId) {
        SlotCompletion[] completions = null;
        switch ((int) slotId) {
        case 10:
        case 11:
        case 12:
            completions = new SlotCompletion[0];
            break;
        case 4:
        case 5:
        case 6:
            completions = new SlotCompletion[2];
            completions[0] = new MockSlotCompletion(1);
            completions[1] = new MockSlotCompletion(2);
            break;
        case 7:
        case 8:
        case 9:
            completions = new SlotCompletion[5];
            completions[0] = new MockSlotCompletion(1);
            completions[1] = new MockSlotCompletion(2);
            completions[2] = new MockSlotCompletion(3);
            completions[3] = new MockSlotCompletion(4);
            completions[4] = new MockSlotCompletion(5);
            break;
        default:
            break;
        }
        return completions;
    }

    /**
     * No implementation.
     */
    public void ejbCreate() {
        // empty
    }

    /**
     * No implementation.
     * 
     * @param arg0
     *            session context
     */
    public void setSessionContext(SessionContext arg0) {
        // empty
    }

    /**
     * No implementation.
     */
    public void ejbRemove() {
        // empty
    }

    /**
     * No implementation.
     */
    public void ejbActivate() {
        // empty
    }

    /**
     * No implementation.
     */
    public void ejbPassivate() {
        // empty
    }

    /**
     * Returns an array of mock games.
     * 
     * @return mock games array
     */
    private Game[] getMockGames() {
        final int COUNT = 3;
        Game[] games = new Game[COUNT];
        for (int i = 0; i < COUNT; i++) {
            games[i] = new MockGame(new Long(i + 1));
        }
        return games;
    }
}