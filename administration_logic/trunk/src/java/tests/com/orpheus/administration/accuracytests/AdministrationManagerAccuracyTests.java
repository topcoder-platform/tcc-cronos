/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests;

import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.orpheus.administration.AdministrationManager;
import com.orpheus.administration.entities.PuzzleTypeEnum;
import com.orpheus.administration.persistence.AdminData;
import com.orpheus.administration.persistence.AdminDataHome;
import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataHome;
import com.topcoder.util.puzzle.PuzzleTypeSource;

import junit.framework.TestCase;

/**
 * Accuracy test for AdministrationManager.
 *
 * @author TCSDEVELOPER
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
        target = new AdministrationManager(NAMESPACE);
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

//    /**
//     * Tests the regeneratePuzzle(long slotId) method.
//     *
//     * @throws Exception to JUnit
//     */
//    public void testRegeneratePuzzleAccuracy() throws Exception {
//        target.regeneratePuzzle(1);
//        assertNotNull("check regeneratePuzzle method", DataProvider.puzzles);
//        assertEquals("check regeneratePuzzle method", DataProvider.puzzles.length, 1);
//        assertEquals("check regeneratePuzzle method",
//                DataProvider.puzzles[0].getAttribute("width"),
//                "120");
//        assertEquals("check regeneratePuzzle method",
//                DataProvider.puzzles[0].getAttribute("height"),
//                "100");
//        assertNotNull("check regeneratePuzzle method",
//                DataProvider.puzzles[0].getAttribute("image"));
//
//        assertEquals("check regeneratePuzzle method",
//                DataProvider.slots.length, 1);
//        assertEquals("check regeneratePuzzle method",
//                DataProvider.slots[0].getId(), new Long(1));
//    }

//    /**
//     * Tests the regenerateBrainTeaser(long slotId) method.
//     *
//     * @throws Exception to JUnit
//     */
//    public void testRegenerateBrainTeaserAccuracy() throws Exception {
//        target.regenerateBrainTeaser(1);
//        assertNotNull("check regenerateBrainTeaser method", DataProvider.puzzles);
//        assertEquals("check regenerateBrainTeaser method", DataProvider.puzzles.length, 1);
//        assertEquals("check regenerateBrainTeaser method",
//                DataProvider.puzzles[0].getAttribute("text"),
//                "identifier text");
//
//        assertEquals("check regenerateBrainTeaser method",
//                DataProvider.slots.length, 1);
//        assertEquals("check regenerateBrainTeaser method",
//                DataProvider.slots[0].getId(), new Long(1));
//    }

    /**
     * Tests the initializeSlotsForBlock(long blockId) method.
     *
     * @throws Exception to JUnit
     */
    public void testInitializeSlotsForBlockAccuracy() throws Exception {
        target.initializeSlotsForBlock(1001);
        assertNotNull("check initializeSlotsForBlock method", DataProvider.puzzles);
        assertEquals("check initializeSlotsForBlock method", DataProvider.puzzles.length, 1);
        assertEquals("check initializeSlotsForBlock method",
                DataProvider.puzzles[0].getAttribute("text"),
                "identifier text");
        assertEquals("check initializeSlotsForBlock method",
                DataProvider.slots.length, 1);
        assertEquals("check regeneratePuzzle method",
                DataProvider.slots[0].getId(), new Long(1001));

        assertEquals("check initializeSlotsForBlock method",
                DataProvider.slots[0].getDomainTargets().length, 5);
        DomainTarget[] targets = DataProvider.slots[0].getDomainTargets();
        for (int i = 0; i < targets.length; i++) {
            DomainTarget target = targets[i];
            assertNotNull("check initializeSlotsForBlock method",
                    target.getUriPath());
            assertNotNull("check initializeSlotsForBlock method",
                    target.getIdentifierText());
        }
    }

    /**
     * Tests the generateHuntTargets(long slotId) method.
     * @throws Exception to JUnit
     */
    public void testGenerateHuntTargetsAccuracy() throws Exception {
        target.generateHuntTargets(1);

        assertEquals("check generateHuntTargets method",
                DataProvider.slots[0].getDomainTargets().length, 5);
        DomainTarget[] targets = DataProvider.slots[0].getDomainTargets();
        for (int i = 0; i < targets.length; i++) {
            DomainTarget target = targets[i];
            assertNotNull("check generateHuntTargets method",
                    target.getUriPath());
            assertNotNull("check generateHuntTargets method",
                    target.getIdentifierText());
        }
    }
}
