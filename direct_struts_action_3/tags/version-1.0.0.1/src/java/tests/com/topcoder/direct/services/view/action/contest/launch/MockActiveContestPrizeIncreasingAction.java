/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * The mock <code>ActiveContestPrizeIncreasingAction</code> used in the unit tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockActiveContestPrizeIncreasingAction extends ActiveContestPrizeIncreasingAction {

    /**
     * Overrides the prepare method for unit testing.
     *
     * @throws Exception if any error occurs preparing action
     */
    @SuppressWarnings("unchecked")
    @Override
    public void prepare() throws Exception {
        super.prepare();

        // initialize with valid values
        setDirectServiceFacade(new MockDirectServiceFacade());
        setContestId(1);
        List<Double> contestPrizes = new ArrayList<Double>();
        contestPrizes.add(100.0);
        contestPrizes.add(55.00);
        setContestPrizes(contestPrizes);
        List<Double> milestonePrizes = new ArrayList<Double>();
        milestonePrizes.add(1942.44);
        milestonePrizes.add(891.22);
        setMilestonePrizes(milestonePrizes);

        // set invalid values, if required
        Map<String, Object> validations = TestHelper.getFieldValues();
        if (validations.size() != 0) {
            for (String key : validations.keySet()) {
                Object value = validations.get(key);

                if (key.equals("contestId")) {
                    setContestId(Long.parseLong(value.toString()));
                } else if (key.equals("contestPrizes")) {
                    setContestPrizes((List<Double>) value);
                } else if (key.equals("milestonePrizes")) {
                    setMilestonePrizes((List<Double>) value);
                }
            }
        }
    }
}
