/**
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.contest.coo.stresstests;

/**
 * <p>
 * This class is used to run the specific operation in the thread.
 * </p>
 *
 * <p>
 * It implements <code>Runnable</code>.
 * </p>
 *
 * <p>
 * This class is used for stress test.
 * </p>
 *
 * @author yuanyeyuanye
 * @version 1.0
 */
public class ActionThreadRunner implements Runnable {
    /**
     * Instance of <code>Action</code>.
     */
    private Action action;

    /**
     * Times to execute the specific action.
     */
    private int times;

    /**
     * Create the instance.
     *
     * @param action Action to be invoked.
     * @param times times to execute the operation.
     */
    public ActionThreadRunner(Action action, int times) {
        this.action = action;
        this.times = times;
    }

    /**
     * Run the specific operation.
     */
    public void run() {
        for (int i = 0; i < times; i++) {
            try {
                action.act();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
