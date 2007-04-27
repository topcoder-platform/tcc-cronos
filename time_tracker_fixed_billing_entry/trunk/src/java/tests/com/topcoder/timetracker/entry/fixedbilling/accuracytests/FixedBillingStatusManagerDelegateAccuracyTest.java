/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.accuracytests;

import java.util.Date;

import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;
import com.topcoder.timetracker.entry.fixedbilling.StringMatchType;
import com.topcoder.timetracker.entry.fixedbilling.j2ee.FixedBillingStatusManagerDelegate;


/**
 * <p>
 * Accuracy test for <code>FixedBillingStatusManagerDelegate</code>.
 * </p>
 *
 * @author liuliquan
 * @version 1.0
 */
public class FixedBillingStatusManagerDelegateAccuracyTest extends BaseTestCase {

    /**
     * FixedBillingStatusManagerDelegate to test.
     */
    private FixedBillingStatusManagerDelegate delegate = null;

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
        this.delegate = new FixedBillingStatusManagerDelegate("FixedBillingStatusManagerDelegateAccuracyTest");
    }

    /**
     * <p>
     * Tear down test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        this.assertTransactionCommited(this.getTransaction());
        this.delegate = null;
        this.wrapDB();
        super.tearDown();
    }
    /**
     * Test ctor.
     * @throws Exception to JUnit
     */
    public void testFixedBillingStatusManagerDelegate() throws Exception {
        assertNotNull(this.delegate);
    }

    /**
     * Test createFixedBillingStatus.
     * @throws Exception to JUnit
     */
    public void testCreateFixedBillingStatus() throws Exception {
        this.delegate.createFixedBillingStatus(this.getFixedBillingStatus()[0]);
        assertEquals(1, this.delegate.getAllFixedBillingStatuses().length);
    }

    /**
     * Test createFixedBillingStatuses.
     * @throws Exception to JUnit
     */
    public void testCreateFixedBillingStatuses_1() throws Exception {
        this.delegate.createFixedBillingStatuses(this.getFixedBillingStatus());
        assertEquals(3, this.delegate.getAllFixedBillingStatuses().length);
    }

    /**
     * Test updateFixedBillingStatus.
     * @throws Exception to JUnit
     */
    public void testUpdateFixedBillingStatus_1() throws Exception {
        FixedBillingStatus[] statuses = this.getFixedBillingStatus();
        delegate.createFixedBillingStatus(statuses[0]);
        String oldDes = statuses[0].getDescription();
        statuses[0].setDescription("new new new");

        //Changed is false, status should not be updated
        statuses[0].setChanged(false);
        delegate.updateFixedBillingStatus(statuses[0]);

        assertEquals(oldDes, delegate.getFixedBillingStatus(statuses[0].getId()).getDescription());
    }

    /**
     * Test updateFixedBillingStatus.
     * @throws Exception to JUnit
     */
    public void testUpdateFixedBillingStatus_2() throws Exception {
        FixedBillingStatus[] statuses = this.getFixedBillingStatus();
        delegate.createFixedBillingStatus(statuses[0]);

        Date oldDate = statuses[0].getModificationDate();
        //Changed is true, status should not be updated
        statuses[0].setChanged(true);
        Thread.sleep(1000);
        delegate.updateFixedBillingStatus(statuses[0]);

        assertTrue(oldDate.before(delegate.getFixedBillingStatus(statuses[0].getId()).getModificationDate()));
    }

    /**
     * Test updateFixedBillingStatuses.
     * @throws Exception to JUnit
     */
    public void testUpdateFixedBillingStatuses() throws Exception {
        FixedBillingStatus[] statuses = this.getFixedBillingStatus();
        this.delegate.createFixedBillingStatuses(statuses);

        for (int i = 0; i < statuses.length; i++) {
            statuses[i].setDescription("new new new");
        }

        delegate.updateFixedBillingStatuses(statuses);

        FixedBillingStatus[] updatedStatuses = this.delegate.getAllFixedBillingStatuses();
        for (int i = 0; i < updatedStatuses.length; i++) {
            assertEquals("new new new", updatedStatuses[i].getDescription());
        }
    }
    /**
     * Test deleteFixedBillingStatus.
     * @throws Exception to JUnit
     */
    public void testDeleteFixedBillingStatus() throws Exception {
        FixedBillingStatus[] statuses = this.getFixedBillingStatus();
        this.delegate.createFixedBillingStatuses(statuses);

        this.delegate.deleteFixedBillingStatus(statuses[0].getId());
        assertEquals(2, this.delegate.getAllFixedBillingStatuses().length);
        this.delegate.deleteFixedBillingStatus(statuses[1].getId());
        assertEquals(1, this.delegate.getAllFixedBillingStatuses().length);
        this.delegate.deleteFixedBillingStatus(statuses[2].getId());
        assertEquals(0, this.delegate.getAllFixedBillingStatuses().length);
    }
    /**
     * Test deleteFixedBillingStatuses.
     * @throws Exception to JUnit
     */
    public void testDeleteFixedBillingStatuses() throws Exception {
        FixedBillingStatus[] statuses = this.getFixedBillingStatus();
        this.delegate.createFixedBillingStatuses(statuses);

        this.delegate.deleteFixedBillingStatuses(
            new long[]{statuses[0].getId(), statuses[1].getId(), statuses[2].getId()});
        assertEquals(0, this.delegate.getAllFixedBillingStatuses().length);
    }

    /**
     * Test getFixedBillingStatus.
     * @throws Exception to JUnit
     */
    public void testGetFixedBillingStatus() throws Exception {
        FixedBillingStatus[] statuses = this.getFixedBillingStatus();
        this.delegate.createFixedBillingStatuses(statuses);
        assertEquals(statuses[0].getDescription(),
            this.delegate.getFixedBillingStatus(statuses[0].getId()).getDescription());
        assertEquals(statuses[1].getDescription(),
                this.delegate.getFixedBillingStatus(statuses[1].getId()).getDescription());
        assertEquals(statuses[2].getDescription(),
                this.delegate.getFixedBillingStatus(statuses[2].getId()).getDescription());
    }
    /**
     * Test getFixedBillingStatuses.
     * @throws Exception to JUnit
     */
    public void testGetFixedBillingStatuses() throws Exception {
        FixedBillingStatus[] statuses = this.getFixedBillingStatus();
        this.delegate.createFixedBillingStatuses(statuses);
        assertEquals(3, this.delegate.getFixedBillingStatuses(
            new long[]{statuses[0].getId(), statuses[1].getId(), statuses[2].getId()}).length);
    }
    /**
     * Test searchFixedBillingStatuses.
     * @throws Exception to JUnit
     */
    public void testSearchFixedBillingStatuses_1() throws Exception {
        FixedBillingStatus[] statuses = this.getFixedBillingStatus();
        this.delegate.createFixedBillingStatuses(statuses);
        FixedBillingStatus[] results =
            this.delegate.searchFixedBillingStatuses(
            		delegate.getFixedBillingStatusFilterFactory().createModificationUserFilter(
            				statuses[0].getModificationUser(), StringMatchType.EXACT_MATCH));
        assertEquals(1, results.length);
    }
}
