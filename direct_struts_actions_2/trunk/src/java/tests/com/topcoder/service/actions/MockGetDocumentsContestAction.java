/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

/**
 * <p>
 * The mock GetDocumentsContestAction used in the unit tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockGetDocumentsContestAction extends GetDocumentsContestAction {

    /**
     * Overrides the prepare method for unit testing.
     */
    @Override
    public void prepare() {
        super.prepare();

        // initialize with valid values
        setContestServiceFacade(new MockContestServiceFacade());
        setContestId(1);
    }
}
