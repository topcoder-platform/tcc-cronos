/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base.stresstests;

import com.topcoder.timetracker.entry.base.ejb.EntrySessionBean;

/**
 * This thread is used to test the fetchCutoffTimeByCompanyID method.
 *
 * @author vividmxx
 * @version 3.2
 */
public class FetchCutoffTimeByCompanyIDTester extends Thread {

    /**
     * Represents the EntrySessionBean instance used to test against.
     */
    private EntrySessionBean bean;

    /**
     * Represents the ids to fetch.
     */
    private long[] ids;

    /**
     * Represents the flag indicating whether the test is failed.
     */
    private boolean failed = false;

    /**
     * Creates a FetchCutoffTimeByCompanyIDTester instance.
     *
     * @param bean
     *            the EntrySessionBean instance used to test against
     * @param ids
     *            the ids to fetch
     */
    public FetchCutoffTimeByCompanyIDTester(EntrySessionBean bean, long[] ids) {
        this.bean = bean;
        this.ids = ids;
    }

    /**
     * Runs the test.
     */
    public void run() {
        for (int i = 0; i < StressTestHelper.RUN_NUMBER / StressTestHelper.THREAD_NUMBER; i++) {
            try {
                if (bean.fetchCutoffTimeByCompanyID(ids[i]) == null) {
                    failed = true;
                }
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
