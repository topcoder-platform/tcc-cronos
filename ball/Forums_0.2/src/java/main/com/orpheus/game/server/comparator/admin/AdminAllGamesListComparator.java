/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.comparator.admin;

import com.orpheus.game.persistence.Game;
import com.orpheus.game.server.admin.OrpheusAdminFunctions;
import com.topcoder.web.tag.paging.SortListListComparator;

import java.util.Comparator;

/**
 * <p>A comparator to be used for sorting the games displayed by <code>View All Games</code> screen displayed to <code>
 * Orpheus Game Server</code> administrator. Such a comparator is passed to a <code>Data Paging Tag</code> which is
 * used for paginating the list of games displayed by the page.</p>
 *
 * @author isv
 * @version 1.0
 */
public class AdminAllGamesListComparator extends SortListListComparator implements Comparator {

    /**
     * <p>Constructs new <code>AdminAllGamesListComparator</code> instance. This implementation does nothing.</p>
     */
    public AdminAllGamesListComparator() {
    }

    /**
     * <p>Compares the games.</p>
     *
     * @param param1 the first game to compare.
     * @param param2 the second game to compare.
     * @return negative if param1 &lt; param2, zero if param1 == param2, positive if param1 &gt; param2.
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
            case (3) : { // Current host
                comparisonResult = String.valueOf(OrpheusAdminFunctions.getCurrentHost(g1)).
                    compareTo(String.valueOf(OrpheusAdminFunctions.getCurrentHost(g2)));
                break;
            }
            case (4) : { // Game status
                comparisonResult
                    = OrpheusAdminFunctions.getGameStatus(g1).compareTo(OrpheusAdminFunctions.getGameStatus(g2));
                break;
            }
            case (5) : { // Keys count
                comparisonResult
                    = g1.getKeyCount() - g2.getKeyCount();
                break;
            }
        }
        if (SORT_ORDER_DESCENDING.equals(getSortOrder())) {
            comparisonResult = -comparisonResult;
        }
        return comparisonResult;
    }

}
