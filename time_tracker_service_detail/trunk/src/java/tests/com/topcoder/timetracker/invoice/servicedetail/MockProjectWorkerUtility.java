/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.project.DataAccessException;
import com.topcoder.timetracker.project.ProjectWorker;
import com.topcoder.timetracker.project.ProjectWorkerFilterFactory;
import com.topcoder.timetracker.project.ProjectWorkerUtility;

/**
 * Mock for <code>ProjectWorkerUtility</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockProjectWorkerUtility implements ProjectWorkerUtility {

    /**
     * Mock method.
     *
     * @param worker
     *            not used
     * @param audit
     *            not used
     */
    public void addProjectWorker(ProjectWorker worker, boolean audit) {
        // nothing to do

    }

    /**
     * Mock method.
     *
     * @param workers
     *            not used
     * @param audit
     *            not used
     */
    public void addProjectWorkers(ProjectWorker[] workers, boolean audit) {
        // nothing to do

    }

    /**
     * Mock method.
     *
     * @return null
     */
    public ProjectWorker[] enumerateProjectWorkers() {
        return null;
    }

    /**
     * Mock method.
     *
     * @return null
     */
    public ProjectWorkerFilterFactory getProjectWorkerFilterFactory() {
        return new MockProjectWorkerFilterFactory();
    }

    /**
     * Mock method.
     *
     * @param projectWorkerId
     *            not used
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not happens
     */
    public void removeProjectWorker(long projectWorkerId, boolean audit) throws DataAccessException {
        // nothing to do

    }

    /**
     * Mock method.
     *
     * @param projectWorkerIds
     *            not used
     * @param audit
     *            not used
     */
    public void removeProjectWorkers(long[] projectWorkerIds, boolean audit) {
        // nothing to do

    }

    /**
     * Mock method.
     *
     * @param filter
     *            not used
     *
     * @return defined array of <code>ProjectWorker</code>
     */
    public ProjectWorker[] searchProjectWorkers(Filter filter) {
        ProjectWorker worker1 = new ProjectWorker();
        worker1.setUserId(1);
        ProjectWorker worker2 = new ProjectWorker();
        worker2.setUserId(2);
        worker2.setPayRate(100);
        return new ProjectWorker[] {worker1, worker2};
    }

    /**
     * Mock method.
     *
     * @param worker
     *            not used
     * @param audit
     *            not used
     */
    public void updateProjectWorker(ProjectWorker worker, boolean audit) {
        // nothing to do

    }

    /**
     * Mock method.
     *
     * @param workers
     *            not used
     * @param audit
     *            not used
     */
    public void updateProjectWorkers(ProjectWorker[] workers, boolean audit) {
        // nothing to do

    }

}
