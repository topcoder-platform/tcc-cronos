/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.stresstests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.project.ProjectWorker;
import com.topcoder.timetracker.project.ProjectWorkerFilterFactory;
import com.topcoder.timetracker.project.ProjectWorkerUtility;

/**
 * This is a mock implementation of <code>ProjectWorkerUtility</code>. It is only used in stress tests.
 *
 * @author vividmxx
 * @version 3.2
 */
public class MockProjectWorkerUtility implements ProjectWorkerUtility {

    /**
     * The default constructor.
     */
    public MockProjectWorkerUtility() {
    }

    /**
     * Returns directly.
     *
     * @param worker
     *            ignored
     * @param audit
     *            ignored
     */
    public void addProjectWorker(ProjectWorker worker, boolean audit) {
    }

    /**
     * Returns directly.
     *
     * @param worker
     *            ignored
     * @param audit
     *            ignored
     */
    public void updateProjectWorker(ProjectWorker worker, boolean audit) {
    }

    /**
     * Returns directly.
     *
     * @param projectWorkerId
     *            ignored
     * @param audit
     *            ignored
     */
    public void removeProjectWorker(long projectWorkerId, boolean audit) {
    }

    /**
     * Returns an array of <code>ProjectWorker</code>.
     *
     * @param filter
     *            ignored
     * @return an array of <code>ProjectWorker</code>
     */
    public ProjectWorker[] searchProjectWorkers(Filter filter) {
        ProjectWorker[] works = new ProjectWorker[200];
        for (int i = 0; i < works.length; i++) {
            works[i] = new ProjectWorker();
            works[i].setUserId(41000 + i);
            works[i].setPayRate(2);
        }
        return works;
    }

    /**
     * Returns null.
     *
     * @return null
     */
    public ProjectWorker[] enumerateProjectWorkers() {
        return null;
    }

    /**
     * Returns directly.
     *
     * @param workers
     *            ignored
     * @param audit
     *            ignored
     */
    public void addProjectWorkers(ProjectWorker[] workers, boolean audit) {
    }

    /**
     * Returns directly.
     *
     * @param workers
     *            ignored
     * @param audit
     *            ignored
     */
    public void updateProjectWorkers(ProjectWorker[] workers, boolean audit) {
    }

    /**
     * Returns directly.
     *
     * @param projectWorkerIds
     *            ignored
     * @param audit
     *            ignored
     */
    public void removeProjectWorkers(long[] projectWorkerIds, boolean audit) {
    }

    /**
     * Returns a <code>ProjectWorkerFilterFactory</code>.
     *
     * @return a <code>ProjectWorkerFilterFactory</code>
     */
    public ProjectWorkerFilterFactory getProjectWorkerFilterFactory() {
        return new MockProjectWorkerFilterFactory();
    }
}
