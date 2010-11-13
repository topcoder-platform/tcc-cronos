/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.latetracker.accuracytests;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.deliverable.latetracker.LateDeliverable;
import com.topcoder.management.deliverable.latetracker.processors.LateDeliverableProcessorImpl;
import com.topcoder.management.deliverable.latetracker.retrievers.LateDeliverablesRetrieverImpl;

/**
 * Accuracy tests for LateDeliverableProcessorImpl.
 * @author mumujava
 * @version 1.0
 */
public class LateDeliverableProcessorImplAccTests extends AccuracyHelper {
    /** Represents the LateDeliverableProcessorImpl instance to test. */
    private LateDeliverableProcessorImpl instance;

    /**
     * <p>Sets up the unit tests.</p>
     */
    public void setUp() throws Exception {
    	super.setUp();
        instance = new LateDeliverableProcessorImpl();
    }

    /**
     * <p>Cleans up the unit tests.</p>
     * @throws Exception to junit
     */
    public void tearDown() throws Exception {
    	super.tearDown();
        instance = null;
    }

    /**
     * Accuracy test for method LateDeliverableProcessorImpl.
     */
    public void test_LateDeliverableProcessorImpl() {
        assertNotNull(instance);
    }

    /**
     * Accuracy test for method processLateDeliverable.
     * 
     * "Review Scorecard" deliverable is late.
     * 
     * Manually check the mails.
     * 
     * 
     * @throws Exception to junit
     */
    public void test_processLateDeliverable() throws Exception  {
    	//addTrackingRecord = true, canSendNotification = true and needToNotify = true
    	AccuracyHelper.executeSqlFile("test_files/accuracy/test.sql");

    	LateDeliverablesRetrieverImpl retriever = new LateDeliverablesRetrieverImpl();
    	ConfigurationObject config = getConfigurationObject("accuracy/config/LateDeliverablesRetrieverImpl.xml",
    			"com.topcoder.management.deliverable.latetracker.retrievers.LateDeliverablesRetrieverImpl");
    	retriever.configure(config);
    	List<LateDeliverable> res = retriever.retrieve();
    	
    	
    	config = getConfigurationObject("accuracy/config/LateDeliverableProcessorImpl.xml",
                "com.topcoder.management.deliverable.latetracker.processors.LateDeliverableProcessorImpl");
        instance.configure(config);
		instance.processLateDeliverable(res.get(0));
		//an email is expected to sent to wuyb@topcoder.com, manually check the mails please
		
		
		Object[] lateDeliverable = getLateDeliverable(112,1);
		assertEquals(lateDeliverable.length, 3);
		assertEquals(lateDeliverable[0], false);
		assertNotNull(lateDeliverable[1]);
		//should be notified
		assertEquals(lateDeliverable[2], 4);
    }
    
    /**
     * Accuracy test for method processLateDeliverable.
     * 
     * "Screening Scorecard" deliverable is late. (No need to notify)
     * 
     * 
     * @throws Exception to junit
     */
    public void test_processLateDeliverable2() throws Exception  {
    	//addTrackingRecord = true, canSendNotification = true and needToNotify = false
    	AccuracyHelper.executeSqlFile("test_files/accuracy/test_Screening_late_noneed_notify.sql");

    	LateDeliverablesRetrieverImpl retriever = new LateDeliverablesRetrieverImpl();
    	ConfigurationObject config = getConfigurationObject("accuracy/config/LateDeliverablesRetrieverImpl.xml",
    			"com.topcoder.management.deliverable.latetracker.retrievers.LateDeliverablesRetrieverImpl");
    	retriever.configure(config);
    	List<LateDeliverable> res = retriever.retrieve();
    	
    	
    	config = getConfigurationObject("accuracy/config/LateDeliverableProcessorImpl.xml",
                "com.topcoder.management.deliverable.latetracker.processors.LateDeliverableProcessorImpl");
        instance.configure(config);
		instance.processLateDeliverable(res.get(0));
		//an email is expected to sent to wuyb@topcoder.com, manually check the mails please
		
		
		Object[] lateDeliverable = getLateDeliverable(112,1);
		assertEquals(lateDeliverable.length, 3);
		assertEquals(lateDeliverable[0], false);
		assertNull(lateDeliverable[1]);
		//should not be notified
		assertEquals(lateDeliverable[2], 3);
    }
    
    /**
     * Accuracy test for method processLateDeliverable.
     * 
     * "Review Scorecard" deliverable is late.
     * The deadline is extended, however it's late again.
     * 
     * Manually check the mails.
     * 
     * 
     * @throws Exception to junit
     */
    public void test_processLateDeliverable3() throws Exception  {
    	
    	AccuracyHelper.executeSqlFile("test_files/accuracy/test.sql");

    	LateDeliverablesRetrieverImpl retriever = new LateDeliverablesRetrieverImpl();
    	ConfigurationObject config = getConfigurationObject("accuracy/config/LateDeliverablesRetrieverImpl.xml",
    			"com.topcoder.management.deliverable.latetracker.retrievers.LateDeliverablesRetrieverImpl");
    	retriever.configure(config);
    	List<LateDeliverable> res = retriever.retrieve();
    	
    	
    	config = getConfigurationObject("accuracy/config/LateDeliverableProcessorImpl.xml",
                "com.topcoder.management.deliverable.latetracker.processors.LateDeliverableProcessorImpl");
        instance.configure(config);
		instance.processLateDeliverable(res.get(0));
		//an email is expected to sent to wuyb@topcoder.com, manually check the mails please
		
		
		Object[] lateDeliverable = getLateDeliverable(112,1);
		assertEquals(lateDeliverable[0], false);
		assertEquals(lateDeliverable.length, 3);
		assertNotNull(lateDeliverable[1]);
		//should be notified
		assertEquals(lateDeliverable[2], 4);
		
		//extend the deadline
    	AccuracyHelper.executeSqlFile("test_files/accuracy/extendDeadline1.sql");
    	
    	res = retriever.retrieve();
		instance.processLateDeliverable(res.get(0));
		//an email is expected to sent to wuyb@topcoder.com, manually check the mails please
		
		//double records is obtained.
		lateDeliverable = getLateDeliverable(112,1);
		assertEquals(lateDeliverable.length, 6);
		assertEquals(lateDeliverable[0], false);
		assertNotNull(lateDeliverable[1]);
		//should be notified
		assertEquals(lateDeliverable[2], 4);
		
		assertEquals(lateDeliverable[3], false);
		assertNotNull(lateDeliverable[4]);
		//should be notified
		assertEquals(lateDeliverable[5], 4);
    }
    
    /**
     * Accuracy test for method processLateDeliverable.
     * 
     * "Review Scorecard" deliverable is late.
     * The deadline is extended and it's no longer late.
     * 
     * Manually check the mails.
     * 
     * 
     * @throws Exception to junit
     */
    public void test_processLateDeliverable4() throws Exception  {
    	
    	AccuracyHelper.executeSqlFile("test_files/accuracy/test.sql");

    	LateDeliverablesRetrieverImpl retriever = new LateDeliverablesRetrieverImpl();
    	ConfigurationObject config = getConfigurationObject("accuracy/config/LateDeliverablesRetrieverImpl.xml",
    			"com.topcoder.management.deliverable.latetracker.retrievers.LateDeliverablesRetrieverImpl");
    	retriever.configure(config);
    	List<LateDeliverable> res = retriever.retrieve();
    	
    	
    	config = getConfigurationObject("accuracy/config/LateDeliverableProcessorImpl.xml",
                "com.topcoder.management.deliverable.latetracker.processors.LateDeliverableProcessorImpl");
        instance.configure(config);
		instance.processLateDeliverable(res.get(0));
		//an email is expected to sent to wuyb@topcoder.com, manually check the mails please
		
		
		Object[] lateDeliverable = getLateDeliverable(112,1);
		assertEquals(lateDeliverable[0], false);
		assertEquals(lateDeliverable.length, 3);
		assertNotNull(lateDeliverable[1]);
		//should be notified
		assertEquals(lateDeliverable[2], 4);
		
		//extend the deadline
    	AccuracyHelper.executeSqlFile("test_files/accuracy/extendDeadline2.sql");
    	
    	
		instance.processLateDeliverable(res.get(0));
		//db is unchanged.
		Object[] lateDeliverable2 = getLateDeliverable(112,1);
		assertEquals(lateDeliverable2.length, 3);
		assertEquals(lateDeliverable[0], false);
		assertNotNull(lateDeliverable[1]);
		//should be notified
		assertEquals(lateDeliverable[2], 4);
    }
    
    
    /**
     * Accuracy test for method processLateDeliverable.
     * 
     * "Review Scorecard" deliverable is late.
     * 10 seconds later, need to notify again.
     * 
     * Manually check the mails.
     * 
     * 
     * @throws Exception to junit
     */
    public void test_processLateDeliverable5() throws Exception  {
    	
    	AccuracyHelper.executeSqlFile("test_files/accuracy/test.sql");

    	LateDeliverablesRetrieverImpl retriever = new LateDeliverablesRetrieverImpl();
    	ConfigurationObject config = getConfigurationObject("accuracy/config/LateDeliverablesRetrieverImpl.xml",
    			"com.topcoder.management.deliverable.latetracker.retrievers.LateDeliverablesRetrieverImpl");
    	retriever.configure(config);
    	List<LateDeliverable> res = retriever.retrieve();
    	
    	
    	config = getConfigurationObject("accuracy/config/LateDeliverableProcessorImpl.xml",
                "com.topcoder.management.deliverable.latetracker.processors.LateDeliverableProcessorImpl");
        instance.configure(config);
		instance.processLateDeliverable(res.get(0));
		//an email is expected to sent to wuyb@topcoder.com, manually check the mails please
		
		
		Object[] lateDeliverable = getLateDeliverable(112,1);
		assertEquals(lateDeliverable.length, 3);
		assertEquals(lateDeliverable[0], false);
		assertNotNull(lateDeliverable[1]);
		//should be notified
		assertEquals(lateDeliverable[2], 4);
		
		Thread.sleep(11*1000);
    	
    	
		instance.processLateDeliverable(res.get(0));
		//db is unchanged.
		Object[] lateDeliverable2 = getLateDeliverable(112,1);
		assertEquals(lateDeliverable2.length, 3);
		assertEquals(lateDeliverable2[0], false);
		assertNotNull(lateDeliverable2[1]);
		//should be notified
		assertEquals(lateDeliverable2[2], 4);
		
		//should update the notification datetime
		assertTrue(((Comparable)lateDeliverable2[1]).compareTo(lateDeliverable[1]) > 0);
    }
    
    

    
    
    /**
     * Accuracy test for method processLateDeliverable.
     * 
     * "Review Scorecard" deliverable is late.
     * 1 seconds later, do not need to notify again.
     * 
     * Manually check the mails.
     * 
     * 
     * @throws Exception to junit
     */
    public void test_processLateDeliverable6() throws Exception  {
    	AccuracyHelper.executeSqlFile("test_files/accuracy/test.sql");

    	LateDeliverablesRetrieverImpl retriever = new LateDeliverablesRetrieverImpl();
    	ConfigurationObject config = getConfigurationObject("accuracy/config/LateDeliverablesRetrieverImpl.xml",
    			"com.topcoder.management.deliverable.latetracker.retrievers.LateDeliverablesRetrieverImpl");
    	retriever.configure(config);
    	List<LateDeliverable> res = retriever.retrieve();
    	
    	
    	config = getConfigurationObject("accuracy/config/LateDeliverableProcessorImpl.xml",
                "com.topcoder.management.deliverable.latetracker.processors.LateDeliverableProcessorImpl");
        instance.configure(config);
		instance.processLateDeliverable(res.get(0));
		//an email is expected to sent to wuyb@topcoder.com, manually check the mails please
		
		
		Object[] lateDeliverable = getLateDeliverable(112,1);
		assertEquals(lateDeliverable.length, 3);
		assertEquals(lateDeliverable[0], false);
		assertNotNull(lateDeliverable[1]);
		//should be notified
		assertEquals(lateDeliverable[2], 4);
		
		Thread.sleep(1*1000);
    	
    	
		instance.processLateDeliverable(res.get(0));
		//db is unchanged.
		Object[] lateDeliverable2 = getLateDeliverable(112,1);
		assertEquals(lateDeliverable2.length, 3);
		assertEquals(lateDeliverable2[0], false);
		assertNotNull(lateDeliverable2[1]);
		//should be notified
		assertEquals(lateDeliverable2[2], 4);
		
		//should update the notification datetime
		assertTrue(((Comparable)lateDeliverable2[1]).compareTo(lateDeliverable[1]) == 0);
    }
    /**
     * Accuracy test for method processLateDeliverable.
     * 
     * "Review Scorecard" deliverable is late.
     * 11 seconds later, the late is forgiven, do not need to notify again.
     * 
     * Manually check the mails.
     * 
     * 
     * @throws Exception to junit
     */
    public void test_processLateDeliverable7() throws Exception  {
    	AccuracyHelper.executeSqlFile("test_files/accuracy/test.sql");

    	LateDeliverablesRetrieverImpl retriever = new LateDeliverablesRetrieverImpl();
    	ConfigurationObject config = getConfigurationObject("accuracy/config/LateDeliverablesRetrieverImpl.xml",
    			"com.topcoder.management.deliverable.latetracker.retrievers.LateDeliverablesRetrieverImpl");
    	retriever.configure(config);
    	List<LateDeliverable> res = retriever.retrieve();
    	
    	
    	config = getConfigurationObject("accuracy/config/LateDeliverableProcessorImpl.xml",
                "com.topcoder.management.deliverable.latetracker.processors.LateDeliverableProcessorImpl");
        instance.configure(config);
		instance.processLateDeliverable(res.get(0));
		//an email is expected to sent to wuyb@topcoder.com, manually check the mails please
		
		
		Object[] lateDeliverable = getLateDeliverable(112,1);
		assertEquals(lateDeliverable.length, 3);
		assertEquals(lateDeliverable[0], false);
		assertNotNull(lateDeliverable[1]);
		//should be notified
		assertEquals(lateDeliverable[2], 4);
		
		Thread.sleep(11*1000);
		//forgive it
		AccuracyHelper.executeSqlFile("test_files/accuracy/forgiveAll.sql");
    	
		instance.processLateDeliverable(res.get(0));
		//db is unchanged.
		Object[] lateDeliverable2 = getLateDeliverable(112,1);
		assertEquals(lateDeliverable2.length, 3);
		assertNotNull(lateDeliverable2[1]);
		//should be notified
		assertEquals(lateDeliverable2[2], 4);
		
		//should update the notification datetime
		assertTrue(((Comparable)lateDeliverable2[1]).compareTo(lateDeliverable[1]) == 0);
    }
    private Object[] getLateDeliverable(int projectPhaseId, int resourceId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement("select forgive_ind, last_notified, deliverable_id from late_deliverable where " +
            		"project_phase_id = ? and resource_id = ?");
            ps.setInt(1, projectPhaseId);
            ps.setInt(2, resourceId);

            rs = ps.executeQuery();
            List<Object> res = new ArrayList<Object>();
            while(rs.next()) {
            	res.add(rs.getBoolean(1));
            	res.add(rs.getDate(2));
            	res.add(rs.getInt(3));
            }
            return res.toArray();
        } finally {
        	if(rs!=null) {
        		rs.close();
        	}
        	if (ps!=null) {
        		ps.close();
        	}
        }
    }
}
