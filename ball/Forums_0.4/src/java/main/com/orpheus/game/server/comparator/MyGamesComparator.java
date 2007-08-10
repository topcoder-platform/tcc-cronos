/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.comparator;

import com.topcoder.web.tag.paging.SortListListComparator;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.server.OrpheusFunctions;

import javax.servlet.ServletContext;
import java.io.Serializable;

/**
 * <p>A comparator for the list of active games listed by <code>My Games</code> page to be used for sorting the
 * records by the values from selected column. Such a comparator is passed to a <code>Data Paging Tag</code> which is
 * used for paginating the list of games displayed by the page.</p>
 *
 * @author isv
 * @version 1.0
 */
public class MyGamesComparator extends SortListListComparator implements Serializable {

    /**
     * <p>A <code>ServletContext</code> providing the context surrounding the <code>Data Paging Tag</code>.</p>
     */
    private final ServletContext context;

    /**
     * <p>Constructs new <code>MyGamesComparator</code> instance. This implementation does nothing.</p>
     *
     * @param context a <code>ServletContext</code> providing the context surrounding the <code>Data Paging Tag</code>.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public MyGamesComparator(ServletContext context) {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        this.context = context;
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
            case (3) : { // Minimum payout
                comparisonResult = new Double(OrpheusFunctions.getMinimumPayout(g1, this.context)).compareTo(
                    new Double(OrpheusFunctions.getMinimumPayout(g2, this.context)));
                break;
            }
            case (4) : { // Game status
                comparisonResult = OrpheusFunctions.getGameStatus(g1).compareTo(OrpheusFunctions.getGameStatus(g2));
                break;
            }
        }
        if (SORT_ORDER_DESCENDING.equals(getSortOrder())) {
            comparisonResult = -comparisonResult;
        }
        return comparisonResult;
    }
}
