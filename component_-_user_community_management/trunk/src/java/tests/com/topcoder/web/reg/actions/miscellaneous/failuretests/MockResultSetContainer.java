/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.failuretests;

import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;
import com.topcoder.shared.dataAccess.resultSet.TCIntResult;
import com.topcoder.shared.dataAccess.resultSet.TCResultItem;


/**
 * Mock class for failure tests.
 *
 * @author gjw99
 * @version 1.0
 */
public class MockResultSetContainer extends ResultSetContainer {
    /**
     * Always return false.
     *
     * @return false.
     */
    public boolean isEmpty() {
        return false;
    }

    /**
     * Returns the item at the specified location
     *
     * @param iRow The row index of the item
     * @param sCol The column name from which to retrieve the item
     *
     * @return The specified item
     */
    public TCResultItem getItem(int iRow, String sCol) {
        return new TCIntResult(2);
    }

    /**
     * Always return 1.
     *
     * @param iRow The row index of the item
     * @param sCol The column name from which to retrieve the item
     *
     * @return The specified item
     */
    public int getIntItem(int iRow, String sCol) {
        return 1;
    }
}
