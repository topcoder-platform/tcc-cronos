/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base;

import com.topcoder.timetracker.entry.base.ejb.EntryDelegate;

import junit.framework.TestCase;

import java.util.Date;


/**
 * Demo for this component. Sample config can be found as test_files\SampleConfig.xml.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DemoTest extends TestCase {
    /** Represents the config for this demo. */
    private static final String SAMPLE_CONFIG = "SampleConfig.xml";

    /**
     * Demo for EntryDelegate. This includes how to:
     *  <ul>
     *      <li>Create a CutoffTimeBean</li>
     *      <li>Update a CutoffTimeBean</li>
     *      <li>Fetch a CutoffTimeBean for a specific company</li>
     *      <li>Fetch a CutoffTimeBean with a given id</li>
     *      <li>Delete a CutoffTimeBean</li>
     *      <li>Checks whether a BaseEntry can be submitted for a specified CutoffTime</li>
     *  </ul>
     *
     * @throws Exception to junit
     */
    public void testDemo() throws Exception {
        //create a new EntryDelegate
        EntryDelegate delegate = new EntryDelegate("com.topcoder.timetracker.entry.base");

        //create a new CutoffTime to store the minimal data
        //for the creation, cutoffTime, companyId, creationUser should be set as minimal requirement
        CutoffTimeBean cotBean = new CutoffTimeBean();
        cotBean.setCutoffTime(new Date());
        cotBean.setCompanyId(1);
        cotBean.setCreationUser("some user");

        //Create an entry of cutoff time for a specific company with id "1"
        //Audit this operation using Timetracker Audit Component
        delegate.createCutoffTime(cotBean, true);

        //create another CutoffTime with same companyId
        cotBean.setId(cotBean.getId() + 1);

        try {
            delegate.createCutoffTime(cotBean, true);
            fail("each company has at most 1 cutoff time, creation will be failed");
        } catch (EntryManagerException e) {
            //success
        }

        //get the cutoff time for a specific company
        CutoffTimeBean cutofftime = delegate.fetchCutoffTimeByCompanyID(1);

        //we can make some change to the retrieved cutoff time, and update it
        long cutoffId = cutofftime.getId();
        //change the cutoffTime
        cutofftime.setCutoffTime(new Date(System.currentTimeMillis() - 1000));

        //set to true so that update can take effect
        cutofftime.setChanged(true);
        delegate.updateCutoffTime(cutofftime, true);

        //we can retrieve the cutoff time by id
        cutofftime = delegate.fetchCutoffTimeById(cutoffId);

        //assuming that we have some BaseEntry based entry we check if the entry can be submitted
        BaseEntry entry = new BaseEntry() {
            };

        entry.setCompanyId(1);
        entry.setDate(new Date());

        boolean canSubmitFlag = delegate.canSubmitEntry(entry);

        //delete operations may be optionally audited
        delegate.deleteCutoffTime(cutofftime, true);
    }

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.loadDefaultConfig();
        TestHelper.loadConfig("DBConnectionFactory.xml");
        TestHelper.loadConfig(SAMPLE_CONFIG);
        TestHelper.initDB();
        TestHelper.initJNDI();
    }

    /**
     * Clears test environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        TestHelper.restoreJNDI();
        TestHelper.clearConfig();
    }
}
