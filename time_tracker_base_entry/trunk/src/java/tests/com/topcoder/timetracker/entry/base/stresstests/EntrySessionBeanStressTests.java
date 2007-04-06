/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base.stresstests;

import java.util.Date;

import com.topcoder.timetracker.entry.base.BaseEntry;
import com.topcoder.timetracker.entry.base.CutoffTimeBean;
import com.topcoder.timetracker.entry.base.ejb.EntrySessionBean;

import junit.framework.TestCase;

/**
 * Stress tests for <code>EntrySessionBean</code> class.
 *
 * @author vividmxx
 * @version 3.2
 */
public class EntrySessionBeanStressTests extends TestCase {

    /**
     * Represents the <code>EntrySessionBean</code> instance used to test against.
     */
    private EntrySessionBean bean = null;

    /**
     * Represents the entry used for test.
     */
    private BaseEntry entry = null;

    /**
     * Represents the CutoffTimeBean instances to create.
     */
    private CutoffTimeBean[] beansToCreate;

    /**
     * Represents the CutoffTimeBean instances to update and delete.
     */
    private CutoffTimeBean[] beansToUpdateAndDelete;

    /**
     * Sets up the test environment.
     *
     * @throws Exception
     *             throws to JUnit
     */
    protected void setUp() throws Exception {
        StressTestHelper.loadConfig();
        StressTestHelper.setUpDatabase();
        bean = new EntrySessionBean();
        bean.ejbCreate();

        entry = new BaseEntry() {};
        entry.setDate(new Date());
        entry.setCompanyId(41000);

        beansToCreate = new CutoffTimeBean[StressTestHelper.RUN_NUMBER];
        for (int i = 0; i < beansToCreate.length; i++) {
            beansToCreate[i] = new CutoffTimeBean();
            beansToCreate[i].setId(41100 + i);
            beansToCreate[i].setCompanyId(41100 + i);
            beansToCreate[i].setCutoffTime(new Date());
            beansToCreate[i].setCreationUser("Tester");
            beansToCreate[i].setModificationUser("Tester");
            beansToCreate[i].setCreationDate(new Date());
            beansToCreate[i].setModificationDate(new Date());
            beansToCreate[i].setChanged(true);
        }

        beansToUpdateAndDelete = new CutoffTimeBean[StressTestHelper.RUN_NUMBER];
        for (int i = 0; i < beansToUpdateAndDelete.length; i++) {
            beansToUpdateAndDelete[i] = new CutoffTimeBean();
            beansToUpdateAndDelete[i].setId(41000 + i);
            beansToUpdateAndDelete[i].setCompanyId(41000 + i);
            beansToUpdateAndDelete[i].setCutoffTime(new Date());
            beansToUpdateAndDelete[i].setCreationUser("Tester");
            beansToUpdateAndDelete[i].setModificationUser("Tester");
            beansToUpdateAndDelete[i].setCreationDate(new Date());
            beansToUpdateAndDelete[i].setModificationDate(new Date());
            beansToUpdateAndDelete[i].setChanged(true);
        }
    }

    /**
     * Tears down the test environment.
     *
     * @throws Exception
     *             throws to JUnit
     */
    protected void tearDown() throws Exception {
        StressTestHelper.tearDownDatabase();
        StressTestHelper.clearConfig();
    }

    /**
     * Stress test for the method <code>canSubmitEntry</code> with single thread.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testCanSubmitEntrySingleThread() throws Exception {
        StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
            bean.canSubmitEntry(entry);
        }
        StressTestHelper.printResultMulTimes("canSubmitEntry", StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method <code>createCutoffTime</code> with single thread.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testCreateCutoffTimeSingleThread() throws Exception {
        StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
            bean.createCutoffTime(beansToCreate[i], true);
        }
        StressTestHelper.printResultMulTimes("createCutoffTime", StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method <code>updateCutoffTime</code> with single thread.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testUpdateCutoffTimeSingleThread() throws Exception {
        StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
            bean.updateCutoffTime(beansToUpdateAndDelete[i], true);
        }
        StressTestHelper.printResultMulTimes("updateCutoffTime", StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method <code>deleteCutoffTime</code> with single thread.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testDeleteCutoffTimeSingleThread() throws Exception {
        StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
            bean.deleteCutoffTime(beansToUpdateAndDelete[i], true);
        }
        StressTestHelper.printResultMulTimes("deleteCutoffTime", StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method <code>fetchCutoffTimeByCompanyID</code> with single thread.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testFetchCutoffTimeByCompanyIDSingleThread() throws Exception {
        StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
            assertNotNull("Test failed.", bean.fetchCutoffTimeByCompanyID(41000 + i));
        }
        StressTestHelper.printResultMulTimes("fetchCutoffTimeByCompanyID", StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method <code>fetchCutoffTimeById</code> with single thread.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testFetchCutoffTimeByIdSingleThread() throws Exception {
        StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
            assertNotNull("Test failed.", bean.fetchCutoffTimeById(41000 + i));
        }
        StressTestHelper.printResultMulTimes("fetchCutoffTimeById", StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method <code>canSubmitEntry</code> with multiple threads.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testCanSubmitEntryMultipleThreads() throws Exception {
        CanSubmitEntryTester[] threads = new CanSubmitEntryTester[StressTestHelper.THREAD_NUMBER];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new CanSubmitEntryTester(bean, entry);
        }
        StressTestHelper.startTimer();
        // start threads
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        // join threads
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        // validate result
        for (int i = 0; i < threads.length; i++) {
            assertFalse("Thread failed.", threads[i].isFailed());
        }
        StressTestHelper.printResultMulThreads("canSubmitEntry", StressTestHelper.THREAD_NUMBER);
    }

    /**
     * Stress test for the method <code>createCutoffTime</code> with multiple threads.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testCreateCutoffTimeMultipleThreads() throws Exception {
        CreateCutoffTimeTester[] threads = new CreateCutoffTimeTester[StressTestHelper.THREAD_NUMBER];
        int runNumPerThread = StressTestHelper.RUN_NUMBER / StressTestHelper.THREAD_NUMBER;
        for (int i = 0; i < threads.length; i++) {
            CutoffTimeBean[] beans = new CutoffTimeBean[runNumPerThread];
            for (int j = 0; j < beans.length; j++) {
                beans[j] = beansToCreate[i * runNumPerThread + j];
            }
            threads[i] = new CreateCutoffTimeTester(bean, beans);
        }
        StressTestHelper.startTimer();
        // start threads
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        // join threads
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        // validate result
        for (int i = 0; i < threads.length; i++) {
            assertFalse("Thread failed.", threads[i].isFailed());
        }
        StressTestHelper.printResultMulThreads("createCutoffTime", StressTestHelper.THREAD_NUMBER);
    }

    /**
     * Stress test for the method <code>updateCutoffTime</code> with multiple threads.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testUpdateCutoffTimeMultipleThreads() throws Exception {
        UpdateCutoffTimeTester[] threads = new UpdateCutoffTimeTester[StressTestHelper.THREAD_NUMBER];
        int runNumPerThread = StressTestHelper.RUN_NUMBER / StressTestHelper.THREAD_NUMBER;
        for (int i = 0; i < threads.length; i++) {
            CutoffTimeBean[] beans = new CutoffTimeBean[runNumPerThread];
            for (int j = 0; j < beans.length; j++) {
                beans[j] = beansToUpdateAndDelete[i * runNumPerThread + j];
            }
            threads[i] = new UpdateCutoffTimeTester(bean, beans);
        }
        StressTestHelper.startTimer();
        // start threads
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        // join threads
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        // validate result
        for (int i = 0; i < threads.length; i++) {
            assertFalse("Thread failed.", threads[i].isFailed());
        }
        StressTestHelper.printResultMulThreads("updateCutoffTime", StressTestHelper.THREAD_NUMBER);
    }

    /**
     * Stress test for the method <code>deleteCutoffTime</code> with multiple threads.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testDeleteCutoffTimeMultipleThreads() throws Exception {
        DeleteCutoffTimeTester[] threads = new DeleteCutoffTimeTester[StressTestHelper.THREAD_NUMBER];
        int runNumPerThread = StressTestHelper.RUN_NUMBER / StressTestHelper.THREAD_NUMBER;
        for (int i = 0; i < threads.length; i++) {
            CutoffTimeBean[] beans = new CutoffTimeBean[runNumPerThread];
            for (int j = 0; j < beans.length; j++) {
                beans[j] = beansToUpdateAndDelete[i * runNumPerThread + j];
            }
            threads[i] = new DeleteCutoffTimeTester(bean, beans);
        }
        StressTestHelper.startTimer();
        // start threads
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        // join threads
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        // validate result
        for (int i = 0; i < threads.length; i++) {
            assertFalse("Thread failed.", threads[i].isFailed());
        }
        StressTestHelper.printResultMulThreads("deleteCutoffTime", StressTestHelper.THREAD_NUMBER);
    }

    /**
     * Stress test for the method <code>fetchCutoffTimeByCompanyID</code> with multiple threads.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testFetchCutoffTimeByCompanyIDMultipleThreads() throws Exception {
        FetchCutoffTimeByCompanyIDTester[] threads =
            new FetchCutoffTimeByCompanyIDTester[StressTestHelper.THREAD_NUMBER];
        int runNumPerThread = StressTestHelper.RUN_NUMBER / StressTestHelper.THREAD_NUMBER;
        for (int i = 0; i < threads.length; i++) {
            long[] ids = new long[runNumPerThread];
            for (int j = 0; j < ids.length; j++) {
                ids[j] = 41000 + i * runNumPerThread + j;
            }
            threads[i] = new FetchCutoffTimeByCompanyIDTester(bean, ids);
        }
        StressTestHelper.startTimer();
        // start threads
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        // join threads
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        // validate result
        for (int i = 0; i < threads.length; i++) {
            assertFalse("Thread failed.", threads[i].isFailed());
        }
        StressTestHelper.printResultMulThreads("fetchCutoffTimeByCompanyID", StressTestHelper.THREAD_NUMBER);
    }

    /**
     * Stress test for the method <code>fetchCutoffTimeById</code> with multiple threads.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testFetchCutoffTimeByIdMultipleThreads() throws Exception {
        FetchCutoffTimeByIdTester[] threads =
            new FetchCutoffTimeByIdTester[StressTestHelper.THREAD_NUMBER];
        int runNumPerThread = StressTestHelper.RUN_NUMBER / StressTestHelper.THREAD_NUMBER;
        for (int i = 0; i < threads.length; i++) {
            long[] ids = new long[runNumPerThread];
            for (int j = 0; j < ids.length; j++) {
                ids[j] = 41000 + i * runNumPerThread + j;
            }
            threads[i] = new FetchCutoffTimeByIdTester(bean, ids);
        }
        StressTestHelper.startTimer();
        // start threads
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        // join threads
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        // validate result
        for (int i = 0; i < threads.length; i++) {
            assertFalse("Thread failed.", threads[i].isFailed());
        }
        StressTestHelper.printResultMulThreads("fetchCutoffTimeById", StressTestHelper.THREAD_NUMBER);
    }
}
