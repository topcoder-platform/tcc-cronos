/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.comparator.plugin;

import com.topcoder.web.tag.paging.SortListListComparator;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.server.OrpheusFunctions;

import java.io.Serializable;

/**
 * <p>A comparator tobe used for sorting the list of games listed by plugin <code>My Games</code> page by the values
 * from selected column. Such a comparator is passed to a <code>Data Paging Tag</code> which is used for paginating the
 * list of games displayed by the page.</p>
 *
 * @author isv
 * @version 1.0
 */
public class MyGamesListSorter extends SortListListComparator implements Serializable {

    /**
     * <p>Constructs new <code>MyGamesListSorter</code> instance. This implementation does nothing.</p>
     */
    public MyGamesListSorter() {
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
            case (0) : {  // Game name
                comparisonResult = g1.getName().compareTo(g2.getName());
                break;
            }
            case (1) : {  // Date
                comparisonResult = g1.getStartDate().compareTo(g2.getStartDate());
                break;
            }
            case (2) : { // Status
                comparisonResult
                    = OrpheusFunctions.getPluginGameStatus(g1).compareTo(OrpheusFunctions.getPluginGameStatus(g2));
                break;
            }
            case (3) : { // Last unlocked domain Currently unavailable
            }
        }
        if (SORT_ORDER_DESCENDING.equals(getSortOrder())) {
            comparisonResult = -comparisonResult;
        }
        return comparisonResult;
    }
}
