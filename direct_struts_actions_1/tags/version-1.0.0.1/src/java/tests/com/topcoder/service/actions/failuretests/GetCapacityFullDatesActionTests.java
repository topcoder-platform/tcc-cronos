/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.failuretests;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.topcoder.service.actions.GetCapacityFullDatesAction;


/**
 * Failure cases of GetCapacityFullDatesAction.
 *
 * @author onsky
 * @version 1.0
 */
public class GetCapacityFullDatesActionTests extends TestCase {
    /**
     * <p>Represents GetCapacityFullDatesAction instance for testing.</p>
     */
    private GetCapacityFullDatesAction instance;

    /**
     * <p>Sets up the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void setUp() throws Exception {
        instance = new GetCapacityFullDatesAction();
    }

    /**
     * <p>Tears down the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Failure test for method setPipelineServiceFacade.
     */
    public void test_setPipelineServiceFacade() {
        try {
            instance.setPipelineServiceFacade(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Failure test for method setContestTypeIdByContestType.
     */
    public void test_setContestTypeIdByContestType1() {
        try {
            instance.setContestTypeIdByContestType(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Failure test for method setContestTypeIdByContestType.
     */
    public void test_setContestTypeIdByContestType2() {
        try {
            Map<String, Long> map = new HashMap<String, Long>();
            map.put(null, 1L);
            instance.setContestTypeIdByContestType(map);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Failure test for method setContestTypeIdByContestType.
     */
    public void test_setContestTypeIdByContestType3() {
        try {
            Map<String, Long> map = new HashMap<String, Long>();
            map.put("1", null);
            instance.setContestTypeIdByContestType(map);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
}
