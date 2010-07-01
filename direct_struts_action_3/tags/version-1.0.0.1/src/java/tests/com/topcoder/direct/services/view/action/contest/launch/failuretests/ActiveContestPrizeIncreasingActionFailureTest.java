/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch.failuretests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import junit.framework.TestCase;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.mock.MockActionInvocation;
import com.topcoder.direct.services.view.action.contest.launch.ActiveContestPrizeIncreasingAction;

/**
 * <p>
 * Failure tests for ActiveContestPrizeIncreasingAction.
 * </p>
 * 
 * @author Beijing2008
 * @version 1.0
 */
public class ActiveContestPrizeIncreasingActionFailureTest extends TestCase {

    /** Represents ActiveContestPrizeIncreasingAction instance for test. */
    private ActiveContestPrizeIncreasingAction instance;

    /**
     * <p>
     * Setup the environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        instance = new ActiveContestPrizeIncreasingAction();
        instance.prepare();
        ActionContext actionContext = new ActionContext(new HashMap<String, Object>());
        actionContext.setActionInvocation(new MockActionInvocation());
        ActionContext.setContext(actionContext);
    }

    /**
     * <p>
     * Clean up the environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    protected void tearDown() throws Exception {
        instance = null;
    }
    /**
     * Tests for execute() with illegal state.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_execute() throws Exception {
        try {
            instance.setContestId(1);
            List<Double> contestPrizes = new ArrayList<Double>();
            contestPrizes.add(500.0);
            contestPrizes.add(200.0);
            instance.setContestPrizes(contestPrizes);
            List<Double> milestonePrizes = new ArrayList<Double>();
            milestonePrizes.add(300.0);
            milestonePrizes.add(300.0);
            instance.setMilestonePrizes(milestonePrizes);
            instance.execute();
            fail("Expects IllegalStateException");
        } catch (IllegalStateException e) {
            // pass
        }
    }
    /**
     * Tests for setContestPrizes() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_setContestPrizes3() throws Exception {
        instance.setDirectServiceFacade(new FailureMockDirectServiceFacade());
        //empty
        instance.setContestPrizes(new ArrayList<Double>());
        instance.setMilestonePrizes(Arrays.asList(1000.0, 500.0));
        instance.execute();
        assertEquals(instance.getFieldErrors().size(), 1);
    }
    /**
     * Tests for setContestPrizes() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_setContestPrizes4() throws Exception {
        instance.setDirectServiceFacade(new FailureMockDirectServiceFacade());
        instance.setContestPrizes(Arrays.asList(1.0));
        instance.setMilestonePrizes(Arrays.asList(1000.0, 500.0));
        instance.execute();
        assertEquals(instance.getFieldErrors().size(), 1);
    }
    /**
     * Tests for setContestPrizes() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_setContestPrizes5() throws Exception {
        instance.setDirectServiceFacade(new FailureMockDirectServiceFacade());
        instance.setContestPrizes(Arrays.asList(-1.0, -2.0));
        instance.setMilestonePrizes(Arrays.asList(1000.0, 500.0));
        instance.execute();
        assertEquals(instance.getFieldErrors().size(), 1);
    }
    /**
     * Tests for setContestPrizes() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_setContestPrizes6() throws Exception {
        instance.setDirectServiceFacade(new FailureMockDirectServiceFacade());
        //second place prize must be at least 20% of first place prize
        instance.setContestPrizes(Arrays.asList(1000.0, 199.0));
        instance.setMilestonePrizes(Arrays.asList(1000.0, 500.0));
        instance.execute();
        assertEquals(instance.getFieldErrors().size(), 1);
    }
    /**
     * Tests for setContestPrizes() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_setContestPrizes7() throws Exception {
        instance.setDirectServiceFacade(new FailureMockDirectServiceFacade());
        //3 decimal places
        instance.setContestPrizes(Arrays.asList(1000.0, 500.012));
        instance.setMilestonePrizes(Arrays.asList(1000.0, 500.0));
        instance.execute();
        assertEquals(instance.getFieldErrors().size(), 1);
    }
    /**
     * Tests for setMilestonePrizes() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_setMilestonePrizes5() throws Exception {
        instance.setDirectServiceFacade(new FailureMockDirectServiceFacade());
        instance.setMilestonePrizes(Arrays.asList(-1.0, -2.0));
        instance.setContestPrizes(Arrays.asList(1000.0, 500.0));
        instance.execute();
        assertTrue(instance.getFieldErrors().size() > 0);
    }
    /**
     * Tests for setMilestonePrizes() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_setMilestonePrizes7() throws Exception {
        instance.setDirectServiceFacade(new FailureMockDirectServiceFacade());
        //3 decimal places
        instance.setMilestonePrizes(Arrays.asList(1000.0, 500.012));
        instance.setContestPrizes(Arrays.asList(1000.0, 500.0));
        instance.execute();
        assertTrue(instance.getFieldErrors().size() > 0);
    }
}
