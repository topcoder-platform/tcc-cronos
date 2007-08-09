/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.comparator;

import com.topcoder.web.tag.paging.SortListListComparator;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.server.OrpheusFunctions;

import java.io.Serializable;

/**
 * <p>A comparator for the list of active games listed by <code>Active Games</code> page to be used for sorting the
 * records by the values from selected column. Such a comparator is passed to a <code>Data Paging Tag</code> which is
 * used for paginating the list of games displayed by the page.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ActiveGamesComparator extends SortListListComparator implements Serializable {

    /**
     * <p>A <code>Game</code> array listing the games which the current player is already registered to.</p>
     */
    private final Game[] playerGames;

    /**
     * <p>Constructs new <code>ActiveGamesComparator</code> instance with specified list of games which the current
     * player is registered to.</p>
     *
     * @param playerGames a <code>Game</code> array listing the games which the current player is registered to.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public ActiveGamesComparator(Game[] playerGames) {
        if (playerGames == null) {
            throw new IllegalArgumentException("The parameter [playerGames] is NULL");
        }
        this.playerGames = playerGames;
    }

    /**
     * <p>Compares the specified objects which are expected to be {@link Game} instances.</p>
     *
     * @param param1 the first game to compare.
     * @param param2 the second game to compare.
     * @return negative if game1 &lt; game2, zero if game1 == game2, positive if game1 &gt; game2.
     */
    public int compare(Object param1, Object param2) {
        Game g1 = (Game) param1;
        Game g2 = (Game) param2;

        int comparisonResult = 0;
        int sortIndex = getIndex();
        switch (sortIndex) {
            case (0) :   // Date
            case (1) : { // Time
                comparisonResult = g1.getStartDate().compareTo(g2.getStartDate());
                break;
            }
            case (2) : { // Name
                comparisonResult = g1.getName().compareTo(g2.getName());
                break;
            }
            case (3) : { // Starting URL
                String startingUrl1 = OrpheusFunctions.getStartingUrl(g1);
                String startingUrl2 = OrpheusFunctions.getStartingUrl(g2);
                if (startingUrl1 == null) {
                    comparisonResult = -1;
                } else if (startingUrl2 == null) {
                    comparisonResult = 1;
                } else {
                    comparisonResult = startingUrl1.compareTo(startingUrl2);
                }
                break;
            }
            case (4) : { // Minimum payout
                comparisonResult = new Double(OrpheusFunctions.getMinimumPayout(g1)).compareTo(
                    new Double(OrpheusFunctions.getMinimumPayout(g2)));
                break;
            }
            case (5) : { // User Status
                String playerStatus1 = OrpheusFunctions.getPlayerStatus(g1, this.playerGames);
                String playerStatus2 = OrpheusFunctions.getPlayerStatus(g2, this.playerGames);
                comparisonResult = playerStatus1.compareTo(playerStatus2);
                break;
            }
        }
        if (SORT_ORDER_DESCENDING.equals(getSortOrder())) {
            comparisonResult = -comparisonResult;
        }
        return comparisonResult;
    }
}
