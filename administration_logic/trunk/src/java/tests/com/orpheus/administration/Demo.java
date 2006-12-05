/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration;

import junit.framework.TestCase;

import com.topcoder.util.puzzle.PuzzleTypeSource;

/**
 * <p>
 * This is a demo of this component.
 * </p>
 *
 * @author KKD
 * @version 1.0
 */
public class Demo extends TestCase {
    /**
     * <p>
     * setUp() routine. Load namespace and prepare test data.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.prepareTest();
    }

    /**
     * <p>
     * Clean up all namespace of ConfigManager.
     * </p>
     *
     * @throws Exception
     *             the exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearTestEnvironment();
    }

    /**
     * Sometimes it might be useful to create handler programmatically, when we
     * want to configure the actions in Front Controller dynamically. But we are
     * NEVER supposed to call the execute method on our own, it is expected to
     * be called by the Front Controller.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDemo1() throws Exception {
        // Creating a manager instance
        PuzzleTypeSource puzzleTypeSource = new MockPuzzleTypeSource();
        String namespace = AdministrationManager.class.getName();
        AdministrationManager mgr = new AdministrationManager(puzzleTypeSource,
                namespace);
        // Regenerating a puzzle for a slot id
        mgr.regeneratePuzzle(1);
        // Regenerating brain teasers for a slot id
        mgr.regenerateBrainTeaser(1);
    }

    /**
     * Dome of generate hunt targets.
     *
     * @throws Exception to JUnit
     */
    public void testDemo2() throws Exception {
        // Creating a manager instance
        PuzzleTypeSource puzzleTypeSource = new MockPuzzleTypeSource();
        String namespace = AdministrationManager.class.getName();
        AdministrationManager mgr = new AdministrationManager(puzzleTypeSource,
                namespace);
        MockGameDataBean.setSlots(null);
        // Regenerating mini hunt targets for a slot id
        mgr.generateHuntTargets(1);
    }

    /**
     * Demo of initialize Slots for Block.
     * @throws Exception to JUnit
     */
    public void testDemo3() throws Exception {
        // Creating a manager instance
        PuzzleTypeSource puzzleTypeSource = new MockPuzzleTypeSource();
        String namespace = AdministrationManager.class.getName();
        AdministrationManager mgr = new AdministrationManager(puzzleTypeSource,
                namespace);
        MockGameDataBean.setSlots(null);
        // Initializing all slots for a particular block id.
        mgr.initializeSlotsForBlock(1);
    }


}
