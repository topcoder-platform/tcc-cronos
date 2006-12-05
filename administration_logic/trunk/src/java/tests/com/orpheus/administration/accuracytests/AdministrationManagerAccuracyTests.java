/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests;

import java.util.Map;

import com.orpheus.administration.AdministrationManager;
import com.orpheus.administration.entities.PuzzleTypeEnum;
import com.topcoder.util.puzzle.PuzzleTypeSource;

import junit.framework.TestCase;

/**
 * Accuracy test for AdministrationManager.
 *
 * @author KKD
 * @version 1.0
 */
public class AdministrationManagerAccuracyTests extends TestCase {
    /**
     * The namespace used to construct AdministrationManager instance.
     */
    private static final String NAMESPACE = AdministrationManager.class
            .getName();
    /**
     * <p>
     * An instance of <code>AdministrationManager</code> which is tested.
     * </p>
     */
    private AdministrationManager target = null;

    /**
     * PuzzleTypeSource used in tests.
     */
    private PuzzleTypeSource src = null;

    /**
     * <p>
     * setUp() routine. Creates test AdministrationManager instance and deploy ejb.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        Helper.prepareTest();
        src = new MockPuzzleTypeSource();
        target = new AdministrationManager(src, NAMESPACE);
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
        Helper.clearTestEnvironment();
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>AdministrationManager()</code> for
     * proper behavior.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorAccuracy() throws Exception {
        assertNotNull("Failed to get AdministrationManager instance.", target);
        Map puzzleConfigMap = (Map) Helper.getPrivateField(AdministrationManager.class,
                target, "puzzleConfigMap");
        assertNotNull("AdministrationManager constructor failed", puzzleConfigMap);
        assertNotNull("AdministrationManager constructor failed",
                puzzleConfigMap.get(PuzzleTypeEnum.JIGSAW));
        assertNotNull("AdministrationManager constructor failed",
                puzzleConfigMap.get(PuzzleTypeEnum.SLIDING_TILE));
        assertNotNull("AdministrationManager constructor failed",
                puzzleConfigMap.get(PuzzleTypeEnum.MISSING_LETTER));
        assertNotNull("AdministrationManager constructor failed",
                puzzleConfigMap.get(PuzzleTypeEnum.LETTER_SCRAMBLE));
    }
}
