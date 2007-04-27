/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.accuracytests;

import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.entry.fixedbilling.StringMatchType;
import com.topcoder.timetracker.entry.fixedbilling.j2ee.FixedBillingEntryManagerDelegate;


/**
 * <p>
 * Accuracy test for <code>FixedBillingEntryManagerDelegate</code>.
 * </p>
 *
 * @author liuliquan
 * @version 1.0
 */
public class FixedBillingEntryManagerAccuracyDelegateTest extends BaseTestCase {
    /**
     * FixedBillingEntryManagerDelegate to test.
     */
    private FixedBillingEntryManagerDelegate delegate = null;

    /**
     * <p>
     * Set up test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        this.setupDB();
        this.delegate = new FixedBillingEntryManagerDelegate("FixedBillingEntryManagerDelegateAccuracyTest");
    }

    /**
     * <p>
     * Tear down test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        this.delegate = null;
        this.wrapDB();
        super.tearDown();
    }

   /**
    * Test ctor.
    * @throws Exception to JUnit
    */
    public void testFixedBillingEntryManagerDelegate() throws Exception {
        assertNotNull(this.delegate);
    }

    /**
     * Test createFixedBillingEntry with out audit.
     * @throws Exception to JUnit
     */
    public void testCreateFixedBillingEntry_WithOutAudit()
        throws Exception {
        this.delegate.createFixedBillingEntry(this.getFixedBillingEntries()[0], false);
        assertEquals(1, this.delegate.getAllFixedBillingEntries().length);
    }

    /**
     * Test createFixedBillingEntry with audit.
     * @throws Exception to JUnit
     */
    public void testCreateFixedBillingEntry_WithAudit()
        throws Exception {
        this.delegate.createFixedBillingEntry(this.getFixedBillingEntries()[0], true);
        assertEquals(1, this.delegate.getAllFixedBillingEntries().length);
        assertEquals(1, MockAccuracyAuditManager.getEntyAuditManager().searchAudit(null).length);
    }

    /**
     * Test updateFixedBillingEntries with audit.
     * @throws Exception to JUnit
     */
    public void testUpdateFixedBillingEntries_WithAudit_1()
        throws Exception {
        FixedBillingEntry[] entries = this.getFixedBillingEntries();
        this.delegate.createFixedBillingEntries(entries, false);

        MockAccuracyAuditManager.getEntyAuditManager().rollbackAuditRecord(0);

        for (int i = 0; i < entries.length; i++) {
            entries[i].setDescription("new desc");
        }

        this.delegate.updateFixedBillingEntries(entries, true);
        assertEquals(3, MockAccuracyAuditManager.getEntyAuditManager().searchAudit(null).length);
    }

    /**
     * Test updateFixedBillingEntries with audit.
     * @throws Exception to JUnit
     */
    public void testUpdateFixedBillingEntries_WithAudit_2()
        throws Exception {
        FixedBillingEntry[] entries = this.getFixedBillingEntries();
        this.delegate.createFixedBillingEntries(entries, false);

        MockAccuracyAuditManager.getEntyAuditManager().rollbackAuditRecord(0);

        for (int i = 0; i < entries.length; i++) {
            entries[i].setDescription("new desc");
        }

        entries[0].setChanged(false);

        this.delegate.updateFixedBillingEntries(entries, true);
        assertEquals(2, MockAccuracyAuditManager.getEntyAuditManager().searchAudit(null).length);
    }

    /**
     * Test updateFixedBillingEntries with audit.
     * @throws Exception to JUnit
     */
    public void testUpdateFixedBillingEntries_WithAudit_3()
        throws Exception {
        FixedBillingEntry[] entries = this.getFixedBillingEntries();
        this.delegate.createFixedBillingEntries(entries, false);

        MockAccuracyAuditManager.getEntyAuditManager().rollbackAuditRecord(0);

        for (int i = 0; i < entries.length; i++) {
            entries[i].setDescription("new desc");
            entries[i].setChanged(false);
        }

        this.delegate.updateFixedBillingEntries(entries, true);
        assertEquals(0, MockAccuracyAuditManager.getEntyAuditManager().searchAudit(null).length);
    }

    /**
     * Test updateFixedBillingEntries with audit.
     * @throws Exception to JUnit
     */
    public void testUpdateFixedBillingEntries_WithAudit_4()
        throws Exception {
        FixedBillingEntry[] entries = this.getFixedBillingEntries();
        this.delegate.createFixedBillingEntries(entries, false);

        MockAccuracyAuditManager.getEntyAuditManager().rollbackAuditRecord(0);

        FixedBillingEntry[] entriesToUpdate = new FixedBillingEntry[4];

        for (int i = 0; i < entries.length; i++) {
            entriesToUpdate[i] = entries[i];
            entries[i].setDescription("new desc");
        }

        //Duplication, there should be only 3 audit records
        entriesToUpdate[3] = entries[0];
        try {
            this.delegate.updateFixedBillingEntries(entriesToUpdate, true);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
        	//success
        }
    }

    /**
     * Test deleteFixedBillingEntry without audit.
     * @throws Exception to JUnit
     */
    public void testDeleteFixedBillingEntry_WithoutAudit()
        throws Exception {
        FixedBillingEntry[] entries = this.getFixedBillingEntries();
        this.delegate.createFixedBillingEntries(entries, false);

        this.delegate.deleteFixedBillingEntry(entries[0].getId(), false);

        assertEquals(2, this.delegate.getAllFixedBillingEntries().length);

        this.delegate.deleteFixedBillingEntry(entries[1].getId(), false);

        assertEquals(1, this.delegate.getAllFixedBillingEntries().length);

        this.delegate.deleteFixedBillingEntry(entries[2].getId(), false);

        assertEquals(0, this.delegate.getAllFixedBillingEntries().length);
    }

    /**
     * Test deleteFixedBillingEntries with audit.
     * @throws Exception to JUnit
     */
    public void testDeleteFixedBillingEntries_WithAudit_1()
        throws Exception {
        FixedBillingEntry[] entries = this.getFixedBillingEntries();
        this.delegate.createFixedBillingEntries(entries, false);

        MockAccuracyAuditManager.getEntyAuditManager().rollbackAuditRecord(0);

        try {
            //Duplication, there should be only one audit record
	        this.delegate.deleteFixedBillingEntries(new long[] {
	                entries[0].getId(), entries[0].getId(), entries[0].getId() }, true);
        } catch (IllegalArgumentException iae) {
        	//success
        }
    }

    /**
     * Test deleteFixedBillingEntries with audit.
     * @throws Exception to JUnit
     */
    public void testDeleteFixedBillingEntries_WithAudit_2()
        throws Exception {
        FixedBillingEntry[] entries = this.getFixedBillingEntries();
        this.delegate.createFixedBillingEntries(entries, false);

        MockAccuracyAuditManager.getEntyAuditManager().rollbackAuditRecord(0);

        this.delegate.deleteFixedBillingEntries(new long[] {
                entries[0].getId(), entries[1].getId(), entries[2].getId() }, true);

        assertEquals(3, MockAccuracyAuditManager.getEntyAuditManager().searchAudit(null).length);
    }

    /**
     * Test getFixedBillingEntry.
     * @throws Exception to JUnit
     */
    public void testGetFixedBillingEntry() throws Exception {
        FixedBillingEntry[] entries = this.getFixedBillingEntries();
        this.delegate.createFixedBillingEntries(entries, false);

        assertNotNull(this.delegate.getFixedBillingEntry(entries[0].getId()));
        assertNotNull(this.delegate.getFixedBillingEntry(entries[1].getId()));
        assertNotNull(this.delegate.getFixedBillingEntry(entries[2].getId()));
    }

    /**
     * Test searchFixedBillingEntries.
     * @throws Exception to JUnit
     */
    public void testSearchFixedBillingEntries_1() throws Exception {
        FixedBillingEntry[] entries = this.getFixedBillingEntries();
        this.delegate.createFixedBillingEntries(entries, false);

        assertEquals(3,
            this.delegate.searchFixedBillingEntries(
                   delegate.getFixedBillingEntryFilterFactory().createCompanyIdFilter(100)).length);
    }

    /**
     * Test searchFixedBillingEntries.
     * @throws Exception to JUnit
     */
    public void testSearchFixedBillingEntries_2() throws Exception {
        FixedBillingEntry[] entries = this.getFixedBillingEntries();
        this.delegate.createFixedBillingEntries(entries, false);

        assertEquals(1,
            this.delegate.searchFixedBillingEntries(
            		delegate.getFixedBillingEntryFilterFactory().createModificationUserFilter(
            				entries[0].getModificationUser(), StringMatchType.EXACT_MATCH)).length);
    }

    /**
     * Test searchFixedBillingEntries with out audit.
     * @throws Exception to JUnit
     */
    public void testCreateFixedBillingEntries_WithoutAudit_1()
        throws Exception {
        this.delegate.createFixedBillingEntries(this.getFixedBillingEntries(), false);
        assertEquals(3, this.delegate.getAllFixedBillingEntries().length);
    }

    /**
     * Test searchFixedBillingEntries with out audit.
     * @throws Exception to JUnit
     */
    public void testCreateFixedBillingEntries_WithoutAudit_2()
        throws Exception {
        FixedBillingEntry[] entries = this.getFixedBillingEntries();

        FixedBillingEntry[] entriesToCreate = new FixedBillingEntry[4];

        for (int i = 0; i < entries.length; i++) {
            entriesToCreate[i] = entries[i];
        }

        //duplication, there should be only 3 entries created
        entriesToCreate[3] = entriesToCreate[2];
        try {
            this.delegate.createFixedBillingEntries(entriesToCreate, false);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
        	//success
        }
    }

    /**
     * Test searchFixedBillingEntries with out audit.
     * @throws Exception to JUnit
     */
    public void testCreateFixedBillingEntries_WithAudit_1()
        throws Exception {
        this.delegate.createFixedBillingEntries(this.getFixedBillingEntries(), true);
        assertEquals(3, this.delegate.getAllFixedBillingEntries().length);
    }

    /**
     * Test searchFixedBillingEntries with out audit.
     * @throws Exception to JUnit
     */
    public void testCreateFixedBillingEntries_WithAudit_2()
        throws Exception {
        FixedBillingEntry[] entries = this.getFixedBillingEntries();

        FixedBillingEntry[] entriesToCreate = new FixedBillingEntry[4];

        for (int i = 0; i < entries.length; i++) {
            entriesToCreate[i] = entries[i];
        }

        //duplication, there should be only 3 entries created
        entriesToCreate[3] = entriesToCreate[2];
        try {
            this.delegate.createFixedBillingEntries(entriesToCreate, true);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
        	//success
        }
    }

    /**
     * Test searchFixedBillingEntries with out audit.
     * @throws Exception to JUnit
     */
    public void testGetFixedBillingEntries() throws Exception {
        FixedBillingEntry[] entries = this.getFixedBillingEntries();
        this.delegate.createFixedBillingEntries(entries, false);

        FixedBillingEntry[] got = this.delegate.getFixedBillingEntries(new long[] {
                    entries[0].getId(), entries[1].getId(), entries[2].getId()});
        assertEquals(3, got.length);
    }

    /**
     * Test addRejectReasonToEntry with out audit.
     * @throws Exception to JUnit
     */
    public void testAddRejectReasonToEntry_WithoutAudit() throws Exception {
        FixedBillingEntry[] entries = this.getFixedBillingEntries();
        delegate.createFixedBillingEntries(entries, false);
        delegate.addRejectReasonToEntry(entries[0], 100, false);

        long[] rejectReasonIds = delegate.getAllRejectReasonsForEntry(entries[0]);
        assertEquals(1, rejectReasonIds.length);
        assertEquals(100, rejectReasonIds[0]);
    }

    /**
     * Test addRejectReasonToEntry with audit.
     * @throws Exception to JUnit
     */
    public void testAddRejectReasonToEntry_WithAudit() throws Exception {
        FixedBillingEntry[] entries = this.getFixedBillingEntries();
        delegate.createFixedBillingEntries(entries, false);
        delegate.addRejectReasonToEntry(entries[0], 100, true);

        assertEquals(1, MockAccuracyAuditManager.getRejectAuditManager().searchAudit(null).length);
    }

    /**
     * Test removeRejectReasonFromEntry with out audit.
     * @throws Exception to JUnit
     */
    public void testRemoveRejectReasonFromEntry_WithoutAudit() throws Exception {
        FixedBillingEntry[] entries = this.getFixedBillingEntries();
        delegate.createFixedBillingEntries(entries, false);
        delegate.addRejectReasonToEntry(entries[0], 100, false);
        delegate.removeRejectReasonFromEntry(entries[0], 100, false);

        long[] rejectReasonIds = delegate.getAllRejectReasonsForEntry(entries[0]);
        assertEquals(0, rejectReasonIds.length);
    }

    /**
     * Test removeRejectReasonFromEntry with audit.
     * @throws Exception to JUnit
     */
    public void testRemoveRejectReasonFromEntry_WithAudit() throws Exception {
        FixedBillingEntry[] entries = this.getFixedBillingEntries();
        delegate.createFixedBillingEntries(entries, false);
        delegate.addRejectReasonToEntry(entries[0], 100, false);

        MockAccuracyAuditManager.getRejectAuditManager().rollbackAuditRecord(0);

        delegate.removeRejectReasonFromEntry(entries[0], 100, true);

        assertEquals(1, MockAccuracyAuditManager.getRejectAuditManager().searchAudit(null).length);

        long[] rejectReasonIds = delegate.getAllRejectReasonsForEntry(entries[0]);
        assertEquals(0, rejectReasonIds.length);
    }
    /**
     * Test removeAllRejectReasonsFromEntry with out audit.
     * @throws Exception to JUnit
     */
    public void testRemoveAllRejectReasonsFromEntry_WithoutAudit() throws Exception {
        FixedBillingEntry[] entries = this.getFixedBillingEntries();
        delegate.createFixedBillingEntries(entries, false);
        delegate.addRejectReasonToEntry(entries[0], 100, false);

        MockAccuracyAuditManager.getRejectAuditManager().rollbackAuditRecord(0);

        delegate.removeAllRejectReasonsFromEntry(entries[0], false);

        assertEquals(0, MockAccuracyAuditManager.getRejectAuditManager().searchAudit(null).length);

        long[] rejectReasonIds = delegate.getAllRejectReasonsForEntry(entries[0]);
        assertEquals(0, rejectReasonIds.length);
    }

    /**
     * Test removeAllRejectReasonsFromEntry with audit.
     * @throws Exception to JUnit
     */
    public void testRemoveAllRejectReasonsFromEntry_WithAudit() throws Exception {
        FixedBillingEntry[] entries = this.getFixedBillingEntries();
        delegate.createFixedBillingEntries(entries, false);
        delegate.addRejectReasonToEntry(entries[0], 100, false);

        MockAccuracyAuditManager.getRejectAuditManager().rollbackAuditRecord(0);

        delegate.removeAllRejectReasonsFromEntry(entries[0], true);

        assertEquals(1, MockAccuracyAuditManager.getRejectAuditManager().searchAudit(null).length);

        long[] rejectReasonIds = delegate.getAllRejectReasonsForEntry(entries[0]);
        assertEquals(0, rejectReasonIds.length);
    }
    /**
     * Test getAllRejectReasonsForEntry.
     * @throws Exception to JUnit
     */
    public void testGetAllRejectReasonsForEntry_1() throws Exception {
        FixedBillingEntry[] entries = this.getFixedBillingEntries();
        delegate.createFixedBillingEntries(entries, false);

        long[] rejectReasonIds = delegate.getAllRejectReasonsForEntry(entries[0]);
        assertEquals(0, rejectReasonIds.length);
    }
    /**
     * Test getAllRejectReasonsForEntry.
     * @throws Exception to JUnit
     */
    public void testGetAllRejectReasonsForEntry_2() throws Exception {
        FixedBillingEntry[] entries = this.getFixedBillingEntries();
        delegate.createFixedBillingEntries(entries, false);
        delegate.addRejectReasonToEntry(entries[0], 100, false);

        long[] rejectReasonIds = delegate.getAllRejectReasonsForEntry(entries[0]);
        assertEquals(1, rejectReasonIds.length);
    }
    /**
     * Test getAllFixedBillingEntries.
     * @throws Exception to JUnit
     */
    public void testGetAllFixedBillingEntries() throws Exception {
        assertEquals(0, this.delegate.getAllFixedBillingEntries().length);
    }
}
