/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import java.util.Map;


/**
 * <p>
 * The mock <code>WikiLinkRetrievalAction</code> used in the unit tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockWikiLinkRetrievalAction extends WikiLinkRetrievalAction {

    /**
     * Overrides the prepare method for unit testing.
     *
     * @throws Exception if any error occurs preparing action
     */
    @Override
    public void prepare() throws Exception {
        super.prepare();

        // if running demo, don't do any additional setup
        if (TestHelper.getTestingMode().equalsIgnoreCase("demo")) {
            return;
        }

        // initialize with valid values
        setContestServiceFacade(new MockContestServiceFacade());
        setProjectId(1);
        setLinkTemplate("[contest_type] [contest_name]");

        // set invalid values, if required
        Map<String, Object> validations = TestHelper.getFieldValues();
        if (validations.size() != 0) {
            for (String key : validations.keySet()) {
                Object value = validations.get(key);

                if (key.equals("projectId")) {
                    setProjectId(Long.parseLong(value.toString()));
                } else if (key.equals("contestId")) {
                    setContestId(Long.parseLong(value.toString()));
                } else if (key.equals("studio")) {
                    setStudio(Boolean.parseBoolean(value.toString()));
                } else if (key.equals("linkTemplate")) {
                    setLinkTemplate(value == null ? null : value.toString());
                }
            }
        }

    }

}
