/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import java.util.Map;

/**
 * <p>
 * The mock <code>ActiveContestPrizeRetrievalAction</code> used in the unit tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockActiveContestPrizeRetrievalAction extends ActiveContestPrizeRetrievalAction {

    /**
     * Overrides the prepare method for unit testing.
     *
     * @throws Exception if any error occurs preparing action
     */
    @Override
    public void prepare() throws Exception {
        super.prepare();

        // initialize with valid values
        setDirectServiceFacade(new MockDirectServiceFacade());
        setContestId(1);

        // set invalid values, if required
        Map<String, Object> validations = TestHelper.getFieldValues();
        if (validations.size() != 0) {
            for (String key : validations.keySet()) {
                Object value = validations.get(key);

                if (key.equals("contestId")) {
                    setContestId(Long.parseLong(value.toString()));
                }
            }
        }

    }

}
