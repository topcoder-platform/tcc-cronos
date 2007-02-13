/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import junit.framework.TestCase;

import java.util.Map;


/**
 * <p>
 * Tests functionality and error cases of <code>ExpenseEntry</code> class. The functionality and error cases which are
 * already tested in V1.0 are not repeated here.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class V1Dot1ExpenseEntryUnitTest extends TestCase {
    /** Represents the <code>ExpenseEntry</code> instance used in tests. */
    private ExpenseEntry entry;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     */
    protected void setUp() {
        entry = new ExpenseEntry();
    }

    /**
     * <p>
     * Tests the method <code>addRejectReason(ExpenseEntryRejectReason)</code> when the given rejectReason is <code>
     * null</code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testAddRejectReason_NullRejectReason() {
        try {
            entry.addRejectReason(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>addRejectReason(ExpenseEntryRejectReason)</code> when the given rejectReason
     * does not exist.
     * </p>
     */
    public void testAddRejectReason_NotExistRejectReasonAccuracy() {
        ExpenseEntryRejectReason reason = new ExpenseEntryRejectReason(1);

        // add first
        assertTrue("The reject reason should be added properly.", entry.addRejectReason(reason));

        // check
        Map rejectReasons = (Map) V1Dot1TestHelper.getPrivateField(entry.getClass(), entry, "rejectReasons");
        assertEquals("The reject reason should be added properly.", 1, rejectReasons.size());
        assertEquals("The reject reason should be added properly.", reason, rejectReasons.get(new Integer(1)));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>addRejectReason(ExpenseEntryRejectReason)</code> when the given rejectReason
     * does exist.
     * </p>
     */
    public void testAddRejectReason_ExistRejectReasonAccuracy() {
        ExpenseEntryRejectReason reason = new ExpenseEntryRejectReason(1);

        // add first
        assertTrue("The reject reason should be added properly.", entry.addRejectReason(reason));

        // add again
        assertFalse("The reject reason should be added properly.", entry.addRejectReason(reason));
    }

    /**
     * <p>
     * Tests the method <code>addRejectReasons(ExpenseEntryRejectReason[])</code> when the given rejectReasons is
     * <code>null</code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testAddRejectReasons_NullRejectReasons() {
        try {
            entry.addRejectReasons(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>addRejectReasons(ExpenseEntryRejectReason[])</code> when the given rejectReasons is
     * empty. Expect IllegalArgumentException.
     * </p>
     */
    public void testAddRejectReasons_EmptyRejectReasons() {
        try {
            entry.addRejectReasons(new ExpenseEntryRejectReason[0]);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>addRejectReasons(ExpenseEntryRejectReason[])</code> when the given rejectReasons contains
     * <code>null</code> element. Expect IllegalArgumentException.
     * </p>
     */
    public void testAddRejectReasons_RejectReasonsContainsNullElement() {
        try {
            entry.addRejectReasons(new ExpenseEntryRejectReason[] {null});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>addRejectReasons(ExpenseEntryRejectReason[])</code> when the given
     * rejectReasons contains duplicate elements.
     * </p>
     */
    public void testAddRejectReasons_RejectReasonsContainsDuplicateElementsAccuracy() {
        ExpenseEntryRejectReason[] rejectReasons = new ExpenseEntryRejectReason[4];
        rejectReasons[0] = new ExpenseEntryRejectReason(1);
        rejectReasons[1] = new ExpenseEntryRejectReason(1);
        rejectReasons[2] = new ExpenseEntryRejectReason(2);
        rejectReasons[3] = new ExpenseEntryRejectReason(1);

        ExpenseEntryRejectReason[] duplicate = entry.addRejectReasons(rejectReasons);

        // check nothing is added into the rejectReasons map
        Map rejectReasonsMap = (Map) V1Dot1TestHelper.getPrivateField(entry.getClass(), entry, "rejectReasons");
        assertEquals("The reject reasons should not be added.", 0, rejectReasonsMap.size());

        // check the duplicate result
        assertNotNull("The duplicate result should be correct.", duplicate);
        V1Dot1TestHelper.assertEquals("The duplicate result should be correct.",
            new ExpenseEntryRejectReason[] {rejectReasons[0], rejectReasons[1], rejectReasons[3]}, duplicate);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>addRejectReasons(ExpenseEntryRejectReason[])</code> when the given
     * rejectReasons contains duplicate elements for rejectReasons map.
     * </p>
     */
    public void testAddRejectReasons_RejectReasonsContainsDuplicateElementsForRejectReasonsMapAccuracy() {
        ExpenseEntryRejectReason[] rejectReasons = new ExpenseEntryRejectReason[3];
        rejectReasons[0] = new ExpenseEntryRejectReason(1);
        rejectReasons[1] = new ExpenseEntryRejectReason(2);
        rejectReasons[2] = new ExpenseEntryRejectReason(3);
        entry.addRejectReason(new ExpenseEntryRejectReason(1));

        ExpenseEntryRejectReason[] duplicate = entry.addRejectReasons(rejectReasons);

        // check nothing is added into the rejectReasons map
        Map rejectReasonsMap = (Map) V1Dot1TestHelper.getPrivateField(entry.getClass(), entry, "rejectReasons");
        assertEquals("The reject reasons should not be added.", 1, rejectReasonsMap.size());

        // check the duplicate result
        assertNotNull("The duplicate result should be correct.", duplicate);
        V1Dot1TestHelper.assertEquals("The duplicate result should be correct.",
            new ExpenseEntryRejectReason[] {rejectReasons[0]}, duplicate);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>addRejectReasons(ExpenseEntryRejectReason[])</code> when the given
     * rejectReasons contains many duplicate elements.
     * </p>
     */
    public void testAddRejectReasons_RejectReasonsContainsManyDuplicateElementsAccuracy() {
        ExpenseEntryRejectReason[] rejectReasons = new ExpenseEntryRejectReason[4];
        rejectReasons[0] = new ExpenseEntryRejectReason(1);
        rejectReasons[1] = new ExpenseEntryRejectReason(3);
        rejectReasons[2] = new ExpenseEntryRejectReason(3);
        rejectReasons[3] = new ExpenseEntryRejectReason(2);
        entry.addRejectReason(new ExpenseEntryRejectReason(1));

        ExpenseEntryRejectReason[] duplicate = entry.addRejectReasons(rejectReasons);

        // check nothing is added into the rejectReasons map
        Map rejectReasonsMap = (Map) V1Dot1TestHelper.getPrivateField(entry.getClass(), entry, "rejectReasons");
        assertEquals("The reject reasons should not be added.", 1, rejectReasonsMap.size());

        // check the duplicate result
        assertNotNull("The duplicate result should be correct.", duplicate);
        V1Dot1TestHelper.assertEquals("The duplicate result should be correct.",
            new ExpenseEntryRejectReason[] {rejectReasons[0], rejectReasons[1], rejectReasons[2]}, duplicate);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>addRejectReasons(ExpenseEntryRejectReason[])</code>.
     * </p>
     */
    public void testAddRejectReasons_Accuracy() {
        ExpenseEntryRejectReason[] rejectReasons = new ExpenseEntryRejectReason[3];
        rejectReasons[0] = new ExpenseEntryRejectReason(1);
        rejectReasons[1] = new ExpenseEntryRejectReason(3);
        rejectReasons[2] = new ExpenseEntryRejectReason(2);

        ExpenseEntryRejectReason[] duplicate = entry.addRejectReasons(rejectReasons);

        // check nothing is added into the rejectReasons map
        Map rejectReasonsMap = (Map) V1Dot1TestHelper.getPrivateField(entry.getClass(), entry, "rejectReasons");
        assertEquals("The reject reasons should be added.", 3, rejectReasonsMap.size());
        assertTrue("The reject reasons should be added.", rejectReasonsMap.containsKey(new Integer(1)));
        assertTrue("The reject reasons should be added.", rejectReasonsMap.containsKey(new Integer(2)));
        assertTrue("The reject reasons should be added.", rejectReasonsMap.containsKey(new Integer(3)));

        // check the duplicate result
        assertNull("The duplicate result should be correct.", duplicate);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>deleteRejectReason(int)</code> when the given rejectReason does not exist.
     * </p>
     */
    public void testDeleteRejectReason_RejectReasonNotExistAccuracy() {
        entry.addRejectReason(new ExpenseEntryRejectReason(1));

        assertFalse("The reject reason should be deleted properly.", entry.deleteRejectReason(2));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>deleteRejectReason(int)</code> when the given rejectReason does exist.
     * </p>
     */
    public void testDeleteRejectReason_RejectReasonExistAccuracy() {
        entry.addRejectReason(new ExpenseEntryRejectReason(1));

        assertTrue("The reject reason should be deleted properly.", entry.deleteRejectReason(1));
    }

    /**
     * <p>
     * Tests the method <code>deleteRejectReasons(int[])</code> when the given rejectReasonIds is <code>null</code>.
     * Expect IllegalArgumentException.
     * </p>
     */
    public void testDeleteRejectReasons_NullRejectReasonsIds() {
        try {
            entry.deleteRejectReasons(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>deleteRejectReasons(int[])</code> when the given rejectReasonIds is empty. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testDeleteRejectReasons_EmptyRejectReasonsIds() {
        try {
            entry.deleteRejectReasons(new int[0]);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>deleteRejectReasons(int[])</code> when the given rejectReasonIds contains
     * nonExisting elements.
     * </p>
     */
    public void testDeleteRejectReasons_RejectReasonsIdsContainsNonExistingElementsAccuracy1() {
        int[] rejectReasonIds = new int[] {1, 3, 3};

        entry.addRejectReason(new ExpenseEntryRejectReason(1));

        int[] nonExisting = entry.deleteRejectReasons(rejectReasonIds);

        // check nothing is deleted into the rejectReasons map
        Map rejectReasonsMap = (Map) V1Dot1TestHelper.getPrivateField(entry.getClass(), entry, "rejectReasons");
        assertEquals("The reject reasons should not be deleted.", 1, rejectReasonsMap.size());

        // check the nonExisting result
        assertNotNull("The nonExisting result should be correct.", nonExisting);
        assertEquals("The nonExisting result should be correct.", 2, nonExisting.length);
        assertEquals("The nonExisting result should be correct.", 3, nonExisting[0]);
        assertEquals("The nonExisting result should be correct.", 3, nonExisting[1]);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>deleteRejectReasons(int[])</code> when the given rejectReasonIds contains
     * nonExisting elements.
     * </p>
     */
    public void testDeleteRejectReasons_RejectReasonsIdsContainsNonExistingElementsAccuracy2() {
        int[] rejectReasonIds = new int[] {3, 3, 1};

        int[] nonExisting = entry.deleteRejectReasons(rejectReasonIds);

        // check nothing is deleted into the rejectReasons map
        Map rejectReasonsMap = (Map) V1Dot1TestHelper.getPrivateField(entry.getClass(), entry, "rejectReasons");
        assertEquals("The reject reasons should not be deleted.", 0, rejectReasonsMap.size());

        // check the nonExisting result
        assertNotNull("The nonExisting result should be correct.", nonExisting);
        assertEquals("The nonExisting result should be correct.", 3, nonExisting.length);
        assertEquals("The nonExisting result should be correct.", 3, nonExisting[0]);
        assertEquals("The nonExisting result should be correct.", 3, nonExisting[1]);
        assertEquals("The nonExisting result should be correct.", 1, nonExisting[2]);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>deleteRejectReasons(int[])</code>.
     * </p>
     */
    public void testDeleteRejectReasons_Accuracy() {
        int[] rejectReasonIds = new int[] {1, 3};

        entry.addRejectReason(new ExpenseEntryRejectReason(1));
        entry.addRejectReason(new ExpenseEntryRejectReason(3));

        int[] nonExisting = entry.deleteRejectReasons(rejectReasonIds);

        // check nothing is deleted into the rejectReasons map
        Map rejectReasonsMap = (Map) V1Dot1TestHelper.getPrivateField(entry.getClass(), entry, "rejectReasons");
        assertEquals("The reject reasons should be deleted.", 0, rejectReasonsMap.size());

        // check the nonExisting result
        assertNull("The nonExisting result should be correct.", nonExisting);
    }

    /**
     * <p>
     * Tests the method <code>updateRejectReason(ExpenseEntryRejectReason)</code> when the given rejectReason is <code>
     * null</code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testUpdateRejectReason_NullRejectReason() {
        try {
            entry.updateRejectReason(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>updateRejectReason(ExpenseEntryRejectReason)</code> when the given
     * rejectReason does not exist.
     * </p>
     */
    public void testUpdateRejectReason_NotExistRejectReasonAccuracy() {
        ExpenseEntryRejectReason reason = new ExpenseEntryRejectReason(1);

        assertFalse("The reject reason should not be updated.", entry.updateRejectReason(reason));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>updateRejectReason(ExpenseEntryRejectReason)</code> when the given
     * rejectReason does exist.
     * </p>
     */
    public void testUpdateRejectReason_ExistRejectReasonAccuracy() {
        ExpenseEntryRejectReason reason = new ExpenseEntryRejectReason(1);
        entry.addRejectReason(reason);

        ExpenseEntryRejectReason reasonNew = new ExpenseEntryRejectReason(1);
        assertTrue("The reject reason should be updated.", entry.updateRejectReason(reasonNew));

        // check whether is updated properly
        Map rejectReasonsMap = (Map) V1Dot1TestHelper.getPrivateField(entry.getClass(), entry, "rejectReasons");
        assertEquals("The reject reason should be updated.", 1, rejectReasonsMap.size());
        assertEquals("The reject reason should be updated.", reasonNew, rejectReasonsMap.get(new Integer(1)));
    }

    /**
     * <p>
     * Tests the method <code>updateRejectReasons(ExpenseEntryRejectReason[])</code> when the given rejectReasons is
     * <code>null</code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testUpdateRejectReasons_NullRejectReasons() {
        try {
            entry.updateRejectReasons(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>updateRejectReasons(ExpenseEntryRejectReason[])</code> when the given rejectReasons is
     * empty. Expect IllegalArgumentException.
     * </p>
     */
    public void testUpdateRejectReasons_EmptyRejectReasons() {
        try {
            entry.updateRejectReasons(new ExpenseEntryRejectReason[0]);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>updateRejectReasons(ExpenseEntryRejectReason[])</code> when the given rejectReasons
     * contains <code>null</code> element. Expect IllegalArgumentException.
     * </p>
     */
    public void testUpdateRejectReasons_RejectReasonsContainsNullElement() {
        try {
            entry.updateRejectReasons(new ExpenseEntryRejectReason[] {null});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>updateRejectReasons(ExpenseEntryRejectReason[])</code> when the given
     * rejectReasons contains nonExisting elements.
     * </p>
     */
    public void testUpdateRejectReasons_RejectReasonsContainsNonExistingElementsAccuracy1() {
        ExpenseEntryRejectReason[] rejectReasons = new ExpenseEntryRejectReason[3];
        rejectReasons[0] = new ExpenseEntryRejectReason(1);
        rejectReasons[1] = new ExpenseEntryRejectReason(2);
        rejectReasons[2] = new ExpenseEntryRejectReason(2);

        entry.addRejectReason(new ExpenseEntryRejectReason(1));

        ExpenseEntryRejectReason[] nonExisting = entry.updateRejectReasons(rejectReasons);

        // check nothing is added into the rejectReasons map
        Map rejectReasonsMap = (Map) V1Dot1TestHelper.getPrivateField(entry.getClass(), entry, "rejectReasons");
        assertEquals("The reject reasons should not be added.", 1, rejectReasonsMap.size());

        // check the nonExisting result
        assertNotNull("The nonExisting result should be correct.", nonExisting);
        V1Dot1TestHelper.assertEquals("The duplicate result should be correct.",
            new ExpenseEntryRejectReason[] {rejectReasons[1], rejectReasons[2]}, nonExisting);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>updateRejectReasons(ExpenseEntryRejectReason[])</code> when the given
     * rejectReasons contains nonExisting elements.
     * </p>
     */
    public void testUpdateRejectReasons_RejectReasonsContainsNonExistingElementsAccuracy2() {
        ExpenseEntryRejectReason[] rejectReasons = new ExpenseEntryRejectReason[3];
        rejectReasons[0] = new ExpenseEntryRejectReason(2);
        rejectReasons[1] = new ExpenseEntryRejectReason(2);
        rejectReasons[2] = new ExpenseEntryRejectReason(1);

        ExpenseEntryRejectReason[] nonExisting = entry.updateRejectReasons(rejectReasons);

        // check nothing is added into the rejectReasons map
        Map rejectReasonsMap = (Map) V1Dot1TestHelper.getPrivateField(entry.getClass(), entry, "rejectReasons");
        assertEquals("The reject reasons should not be added.", 0, rejectReasonsMap.size());

        // check the nonExisting result
        assertNotNull("The nonExisting result should be correct.", nonExisting);
        V1Dot1TestHelper.assertEquals("The duplicate result should be correct.",
            new ExpenseEntryRejectReason[] {rejectReasons[0], rejectReasons[1], rejectReasons[2]}, nonExisting);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>updateRejectReasons(ExpenseEntryRejectReason[])</code>.
     * </p>
     */
    public void testUpdateRejectReasons_Accuracy() {
        ExpenseEntryRejectReason[] rejectReasons = new ExpenseEntryRejectReason[1];
        rejectReasons[0] = new ExpenseEntryRejectReason(1);

        entry.addRejectReason(new ExpenseEntryRejectReason(1));

        ExpenseEntryRejectReason[] nonExisting = entry.updateRejectReasons(rejectReasons);

        // check it should be updated in the rejectReasons map
        Map rejectReasonsMap = (Map) V1Dot1TestHelper.getPrivateField(entry.getClass(), entry, "rejectReasons");
        assertEquals("The reject reasons should be updated.", 1, rejectReasonsMap.size());
        assertEquals("The reject reasons should be updated.", rejectReasons[0], rejectReasonsMap.get(new Integer(1)));

        // check the duplicate result
        assertNull("The nonExisting result should be correct.", nonExisting);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getRejectReasons()</code>.
     * </p>
     */
    public void testGetRejectReasons_Accuracy() {
        ExpenseEntryRejectReason[] rejectReasons = entry.getRejectReasons();
        assertEquals("The reject reasons should be got properly.", 0, rejectReasons.length);

        ExpenseEntryRejectReason rejectReason = new ExpenseEntryRejectReason(1);
        entry.addRejectReason(rejectReason);

        rejectReasons = entry.getRejectReasons();
        assertEquals("The reject reasons should be got properly.", 1, rejectReasons.length);
        assertEquals("The reject reasons should be got properly.", rejectReason, rejectReasons[0]);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getRejectReasonIds()</code>.
     * </p>
     */
    public void testGetRejectReasonIds_Accuracy() {
        int[] rejectReasonIds = entry.getRejectReasonIds();
        assertEquals("The reject reason Ids should be got properly.", 0, rejectReasonIds.length);

        ExpenseEntryRejectReason rejectReason = new ExpenseEntryRejectReason(1);
        entry.addRejectReason(rejectReason);

        rejectReasonIds = entry.getRejectReasonIds();
        assertEquals("The reject reason Ids should be got properly.", 1, rejectReasonIds.length);
        assertEquals("The reject reason Ids should be got properly.", 1, rejectReasonIds[0]);
    }
}
