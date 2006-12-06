/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.persistence.failuretests;

import com.orpheus.game.persistence.CustomDownloadSource;
import com.orpheus.game.persistence.PersistenceException;
import com.topcoder.web.frontcontroller.results.DownloadData;
import com.topcoder.web.frontcontroller.results.DownloadDataRetrievalException;

import junit.framework.TestCase;

/**
 * Test case for <code>CustomDownloadSourceTest</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class CustomDownloadSourceTest extends TestCase {

    /**
     * Test method for getDownloadData(java.lang.String).
     * In this case, there is some internal exception happens.
     */
    public void testGetDownloadData_InternalException() {
        // create a source which will always throw PersistenceException
        CustomDownloadSource source = new CustomDownloadSource() {
            protected DownloadData ejbGetDownloadData(String id) throws PersistenceException {
                throw new PersistenceException("haha");
            }};
        try {
            source.getDownloadData("test");
            fail("HandlerExecutionException expected.");
        } catch (DownloadDataRetrievalException e) {
            // should land here
        }
    }

}
