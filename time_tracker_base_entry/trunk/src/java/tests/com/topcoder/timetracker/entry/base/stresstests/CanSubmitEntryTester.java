/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base.stresstests;

import com.topcoder.timetracker.entry.base.BaseEntry;
import com.topcoder.timetracker.entry.base.ejb.EntrySessionBean;

/**
 * This thread is used to test the canSubmitEntry method.
 *
 * @author vividmxx
 * @version 3.2
 */
public class CanSubmitEntryTester extends Thread {

    /**
     * Represents the entry used for test.
     */
    private BaseEntry entry;

    /**
     * Represents the EntrySessionBean instance used to test against.
     */
    private EntrySessionBean bean;

    /**
     * Represents the flag indicating whether the test is failed.
     */
    private boolean failed = false;

    /**
     * Creates a CanSubmitEntryTester instance.
     *
     * @param bean
     *            the EntrySessionBean instance used to test against
     * @param entry
     *            the entry used for test
     */
    public CanSubmitEntryTester(EntrySessionBean bean, BaseEntry entry) {
        this.bean = bean;
        this.entry = entry;
    }

    /**
     * Runs the test.
     */
    public void run() {
        for (int i = 0; i < StressTestHelper.RUN_NUMBER / StressTestHelper.THREAD_NUMBER; i++) {
            try {
                bean.canSubmitEntry(entry);
            } catch (Exception e) {
                failed = true;
            }
        }
    }

    /**
     * Gets the flag indicating whether the test is failed.
     *
     * @return the flag indicating whether the test is failed
     */
    public boolean isFailed() {
        return failed;
    }
}
