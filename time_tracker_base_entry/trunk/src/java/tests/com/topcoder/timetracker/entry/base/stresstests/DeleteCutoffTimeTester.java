/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base.stresstests;

import com.topcoder.timetracker.entry.base.CutoffTimeBean;
import com.topcoder.timetracker.entry.base.ejb.EntrySessionBean;

/**
 * This thread is used to test the deleteCutoffTime method.
 *
 * @author vividmxx
 * @version 3.2
 */
public class DeleteCutoffTimeTester extends Thread {

    /**
     * Represents the EntrySessionBean instance used to test against.
     */
    private EntrySessionBean bean;

    /**
     * Represents the CutoffTimeBean instances used for test.
     */
    private CutoffTimeBean[] cutoffTimeBeans;

    /**
     * Represents the flag indicating whether the test is failed.
     */
    private boolean failed = false;

    /**
     * Creates a DeleteCutoffTimeTester instance.
     *
     * @param bean
     *            the EntrySessionBean instance used to test against
     * @param cutoffTimeBeans
     *            the CutoffTimeBean instances used for test
     */
    public DeleteCutoffTimeTester(EntrySessionBean bean, CutoffTimeBean[] cutoffTimeBeans) {
        this.bean = bean;
        this.cutoffTimeBeans = cutoffTimeBeans;
    }

    /**
     * Runs the test.
     */
    public void run() {
        for (int i = 0; i < StressTestHelper.RUN_NUMBER / StressTestHelper.THREAD_NUMBER; i++) {
            try {
                bean.deleteCutoffTime(cutoffTimeBeans[i], true);
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
