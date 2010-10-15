/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.latetracker.accuracytests;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import com.topcoder.management.deliverable.latetracker.utility.LateDeliverablesTrackingUtility;

/**
 * Accuracy tests for LateDeliverablesTrackingUtility.
 * @author mumujava
 * @version 1.0
 */
public class LateDeliverablesTrackingUtilityAccTests extends AccuracyHelper {

    /**
     * <p>Sets up the unit tests.</p>
     */
    public void setUp() throws Exception {
    	super.setUp();
    }

    /**
     * <p>Cleans up the unit tests.</p>
     * @throws Exception to junit
     */
    public void tearDown() throws Exception {
    	super.tearDown();
    }

    /**
     * Accuracy test for method main. 
     * 
     * 
     * @throws Exception to junit
     */
    public void test_main6() throws Exception  {
    	final PipedOutputStream out = new PipedOutputStream();
		InputStream in = new PipedInputStream(out);
		InputStream org = System.in;
		System.setIn(in);
		try {
			new Thread() {
				public void run() {
					try {
						Thread.sleep(10*1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			    	//write a char
			    	try {
						out.write(1);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();
			
	    	LateDeliverablesTrackingUtility.main(new String[]{});
	    	
	    	//no exception is thrown
		} finally {
    		System.setIn(org);
    	}
    }
}
