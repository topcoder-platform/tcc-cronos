/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch.accuracytests;

import com.opensymphony.xwork2.ActionContext;

import com.topcoder.direct.services.view.action.contest.launch.ActiveContestPrizeIncreasingAction;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;

import com.topcoder.service.facade.direct.ContestPrize;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * The accuracy tests for the class {@link ActiveContestPrizeIncreasingAction}.
 *
 * @author KLW
 * @version 1.0
 */
public class ActiveContestPrizeIncreasingActionAccTest {
    /** The instance for tesing. */
    private ActiveContestPrizeIncreasingAction action;

    /**
     * sets up the test environment.
     *
     * @throws java.lang.Exception if any error occurs.
     */
    @Before
    public void setUp() throws Exception {
        action = new ActiveContestPrizeIncreasingAction();
        action.prepare();
    }

    /**
     * tears down the test environment.
     *
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        action = null;
    }

    /**
     * Test method for {@link ActiveContestPrizeIncreasingAction#executeAction()}.
     *
     * @throws Exception if any error occurs.
     */
    @Test
    public void testExecuteAction() throws Exception {
        MockDirectServiceFacade directServiceFacade = new MockDirectServiceFacade();
        action.setDirectServiceFacade(directServiceFacade);
        action.setContestId(1);

        List<Double> contestPrizes = new ArrayList<Double>();
        contestPrizes.add(1000.0);
        contestPrizes.add(950.0);
        action.setContestPrizes(contestPrizes);

        List<Double> milestonePrizes = new ArrayList<Double>();
        milestonePrizes.add(300.0);
        milestonePrizes.add(300.0);
        action.setMilestonePrizes(milestonePrizes);
        //set the context
        ActionContext.setContext(new ActionContext(new HashMap<String, Object>()));
        //run the method in the super class.
        action.execute();

        //verify the result
        ContestPrize result = directServiceFacade.getContestPrize();
        assertNotNull("The result should not be null.", result);
        assertTrue("No error should be added.", action.getFieldErrors().isEmpty());
    }

    /**
     * Test method for {@link ActiveContestPrizeIncreasingAction#executeAction()}. The prize money in action is
     * less than the old prize. it should failed.
     *
     * @throws Exception if any error occurs.
     */
    @Test
    public void testExecuteAction_1() throws Exception {
        MockDirectServiceFacade directServiceFacade = new MockDirectServiceFacade();
        action.setDirectServiceFacade(directServiceFacade);
        action.setContestId(1);

        List<Double> contestPrizes = new ArrayList<Double>();
        contestPrizes.add(100.0);
        contestPrizes.add(150.0);
        action.setContestPrizes(contestPrizes);

        List<Double> milestonePrizes = new ArrayList<Double>();
        milestonePrizes.add(100.0);
        milestonePrizes.add(100.0);
        action.setMilestonePrizes(milestonePrizes);
        //set the context
        ActionContext.setContext(new ActionContext(new HashMap<String, Object>()));
        //run the method in the super class.
        action.execute();

        //verify the result
        assertFalse("Error should be added.", action.getFieldErrors().isEmpty());
    }

    /**
     * Test method for {@link ActiveContestPrizeIncreasingAction#ActiveContestPrizeIncreasingAction()}.
     */
    @Test
    public void testActiveContestPrizeIncreasingAction() {
        assertNotNull("The action should not be null.", action);
        assertTrue("The inheritance is incorrect.", action instanceof BaseDirectStrutsAction);
    }

    /**
     * Test method for {@link ActiveContestPrizeIncreasingAction#getContestId()}.
     */
    @Test
    public void testGetContestId() {
        action.setContestId(1);
        assertEquals("The result is incorrect.", 1, action.getContestId());
    }

    /**
     * Test method for {@link ActiveContestPrizeIncreasingAction#setContestId(long)}.
     */
    @Test
    public void testSetContestId() {
        action.setContestId(1);
        assertEquals("The result is incorrect.", 1, action.getContestId());
    }

    /**
     * Test method for {@link ActiveContestPrizeIncreasingAction#isStudio()}.
     */
    @Test
    public void testIsStudio() {
        assertFalse("The result is incorrect.", action.isStudio());
        action.setStudio(true);
        assertTrue("The result is incorrect.", action.isStudio());
    }

    /**
     * Test method for {@link ActiveContestPrizeIncreasingAction#setStudio(boolean)}.
     */
    @Test
    public void testSetStudio() {
        assertFalse("The result is incorrect.", action.isStudio());
        action.setStudio(true);
        assertTrue("The result is incorrect.", action.isStudio());
    }

    /**
     * Test method for {@link ActiveContestPrizeIncreasingAction#getContestPrizes()}.
     */
    @Test
    public void testGetContestPrizes() {
        List<Double> prizes = new ArrayList<Double>();
        prizes.add(100.0);
        prizes.add(200.0);
        action.setContestPrizes(prizes);
        assertEquals("The result is incorrect.", prizes, action.getContestPrizes());
    }

    /**
     * Test method for {@link ActiveContestPrizeIncreasingAction#setContestPrizes(java.util.List)}.
     */
    @Test
    public void testSetContestPrizes() {
        List<Double> prizes = new ArrayList<Double>();
        prizes.add(100.0);
        prizes.add(200.0);
        action.setContestPrizes(prizes);
        assertEquals("The result is incorrect.", prizes, action.getContestPrizes());
    }

    /**
     * Test method for {@link ActiveContestPrizeIncreasingAction#getMilestonePrizes()}.
     */
    @Test
    public void testGetMilestonePrizes() {
        List<Double> prizes = new ArrayList<Double>();
        prizes.add(100.0);
        prizes.add(200.0);
        action.setMilestonePrizes(prizes);
        assertEquals("The result is incorrect.", prizes, action.getMilestonePrizes());
    }

    /**
     * Test method for {@link ActiveContestPrizeIncreasingAction#setMilestonePrizes(java.util.List)}.
     */
    @Test
    public void testSetMilestonePrizes() {
        List<Double> prizes = new ArrayList<Double>();
        prizes.add(100.0);
        prizes.add(200.0);
        action.setMilestonePrizes(prizes);
        assertEquals("The result is incorrect.", prizes, action.getMilestonePrizes());
    }
}
