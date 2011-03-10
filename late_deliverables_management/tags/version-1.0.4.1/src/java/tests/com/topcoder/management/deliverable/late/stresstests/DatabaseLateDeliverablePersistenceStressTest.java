/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.late.stresstests;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import org.junit.Test;

import com.topcoder.management.deliverable.late.LateDeliverable;
import com.topcoder.management.deliverable.late.impl.persistence.DatabaseLateDeliverablePersistence;

/**
 * Stress test for DatabaseLateDeliverablePersistence class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DatabaseLateDeliverablePersistenceStressTest extends TestCase {
    /**
     * Represents the instance of DatabaseLateDeliverablePersistence used in test.
     */
    private DatabaseLateDeliverablePersistence instance;

    /**
     * Represents the exception thrown by threads.
     */
    private Throwable error;

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void setUp() throws Exception {
        StressTestHelper.addConfig();
        StressTestHelper.addTestData();

        instance = new DatabaseLateDeliverablePersistence();
        instance.configure(StressTestHelper.getLateDeliverableManagerImplConfig().getChild("persistenceConfig"));
        error = null;
    }

    /**
     * <p>
     * Cleans up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void tearDown() throws Exception {
        StressTestHelper.clearTestData();
        StressTestHelper.closeConnection();
        StressTestHelper.removeConfig();
    }

    /**
     * Stress test for update(LateDeliverable lateDeliverable) in single thread.
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testUpdate1() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            LateDeliverable lateDeliverable = new LateDeliverable();
            lateDeliverable.setId(i + 1);
            lateDeliverable.setCreateDate(createDate(2010, 11, 1, 1, 2, 3));
            lateDeliverable.setDeadline(createDate(2010, 11, 2, 1, 2, 3));
            lateDeliverable.setDelay(2L);
            lateDeliverable.setDeliverableId(14L);
            lateDeliverable.setExplanation("new exp");
            lateDeliverable.setForgiven(!lateDeliverable.isForgiven());
            lateDeliverable.setLastNotified(createDate(2010, 11, 3, 1, 2, 3));
            lateDeliverable.setProjectPhaseId(102);
            lateDeliverable.setResourceId(1002);
            lateDeliverable.setResponse("new response" + i);

            instance.update(lateDeliverable);

            String sql = "Select count(*) from late_deliverable WHERE late_deliverable_id=" + (i + 1)
                + " AND project_phase_id = 102"
                + " AND resource_id=1002 AND deliverable_id = 14 AND delay=2 AND explanation='new exp'"
                + " AND TO_CHAR(deadline, '%Y-%m-%d %H:%M:%S') = '2010-12-02 01:02:03' AND response='new response" + i
                + "' AND TO_CHAR(create_date, '%Y-%m-%d %H:%M:%S') = '2010-12-01 01:02:03'" + " AND forgive_ind = "
                + (lateDeliverable.isForgiven() ? 1 : 0)
                + " AND TO_CHAR(last_notified, '%Y-%m-%d %H:%M:%S') = '2010-12-03 01:02:03'";

            assertEquals("The update is incorrect.", 1, StressTestHelper.count(sql));
        }
        long spent = System.currentTimeMillis() - start;
        System.out.println("Run DatabaseLateDeliverablePersistence#update 100 times, spent " + spent + "ms.");
    }

    /**
     * Stress test for update(LateDeliverable lateDeliverable) in multi threads.
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testUpdate2() throws Exception {
        Thread[] threads = new Thread[10];

        long start = System.currentTimeMillis();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Worker(i);
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
        long spent = System.currentTimeMillis() - start;
        System.out.println("Run DatabaseLateDeliverablePersistence#update 100 times multi-thread, spent " + spent
            + "ms.");
    }

    /**
     * Gets the Date.
     *
     * @param year
     *            the year of date.
     * @param month
     *            the month of date.
     * @param day
     *            the day of date.
     * @param hour
     *            the hour of date.
     * @param minute
     *            the minute of date.
     * @param second
     *            the second of date.
     * @return the date expected.
     */
    private Date createDate(int year, int month, int day, int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);

        return cal.getTime();
    }

    /**
     * The worker thread
     *
     * @author TCSDEVLOPER
     * @version 1.0
     */
    class Worker extends Thread {
        /**
         * The thread index.
         */
        private int index;

        /**
         * Create new thread.
         *
         * @param index
         *            the thread index.
         */
        public Worker(int index) {
            super();
            this.index = index;
        }

        /**
         * Do the update.
         */
        public void run() {
            try {
                for (int i = 1; i <= 10; i++) {
                    LateDeliverable lateDeliverable = new LateDeliverable();
                    lateDeliverable.setId(i);
                    lateDeliverable.setCreateDate(createDate(2010, 11, 1, 1, 2, 3));
                    lateDeliverable.setDeadline(createDate(2010, 11, 2, 1, 2, 3));
                    lateDeliverable.setLastNotified(createDate(2010, 11, 3, 1, 2, 3));
                    lateDeliverable.setDelay((long) i + index);
                    lateDeliverable.setDeliverableId(10L + i + index);
                    lateDeliverable.setProjectPhaseId(102 + i + index);
                    lateDeliverable.setResourceId(1002 + i + index);
                    lateDeliverable.setExplanation("new exp" + i + index);
                    lateDeliverable.setResponse("new response" + i + index);
                    lateDeliverable.setForgiven(!lateDeliverable.isForgiven());
                    instance.update(lateDeliverable);

                    String sql = "Select count(*) from late_deliverable WHERE late_deliverable_id=" + i
                        + " AND substr(explanation, 8) = substr(response, 13) AND"
                        + " deliverable_id - delay = 10 AND project_phase_id - delay = 102 AND"
                        + " resource_id - delay = 1002";

                    assertEquals("The update is incorrect.", 1, StressTestHelper.count(sql));
                }
            } catch (Throwable e) {
                error = e;
            }
        }
    }
}