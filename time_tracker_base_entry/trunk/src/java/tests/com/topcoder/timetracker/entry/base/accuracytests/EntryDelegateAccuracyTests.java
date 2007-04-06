/*
 * Copyright (C) 2006 Topcoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base.accuracytests;

import com.mockrunner.mock.ejb.MockUserTransaction;

import com.topcoder.timetracker.entry.base.BaseEntry;
import com.topcoder.timetracker.entry.base.CutoffTimeBean;
import com.topcoder.timetracker.entry.base.EntryManagerException;
import com.topcoder.timetracker.entry.base.ejb.EntryDelegate;
import com.topcoder.timetracker.entry.base.ejb.EntryLocalHome;
import com.topcoder.timetracker.entry.base.ejb.EntryLocalObject;
import com.topcoder.timetracker.entry.base.ejb.EntrySessionBean;

import junit.framework.TestCase;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;

import org.mockejb.jndi.MockContextFactory;

import java.sql.Connection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;


/**
 * Test cases for the EntryDelegate.
 *
 * @author waits
 * @since Apr 1, 2007
 * @version 1.0
 */
public class EntryDelegateAccuracyTests extends TestCase {
    /** EntryDelegate instance for testing. */
    private EntryDelegate delegate = null;

    /** State of this test case. These variables are initialized by setUp method */
    private Context context;

    /** mock user trnasaction. */
    private MockUserTransaction mockTransaction;
    /**
     * Database connection.
     */
    private Connection conn = null;
    /**
     * Setup the environement for testing.
     */
    protected void setUp() throws Exception {
        // clear the talble and setup the environment.
    	TestHelper.clearConfiguration();
        TestHelper.setUpConfiguration();
        conn = TestHelper.getConnection();
        TestHelper.clearTable(conn);
        TestHelper.insertData(conn);

        /*
         * We need to set MockContextFactory as our JNDI provider. This method sets the necessary system properties.
         */
        MockContextFactory.setAsInitial();

        // create the initial context that will be used for binding EJBs
        Map env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, MockContextFactory.class.getName());
        //context = new InitialContext((Hashtable) env);
        context = new InitialContext();

        // Create an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        /*
         * Create deployment descriptor of our sample bean. MockEjb uses it instead of XML deployment descriptors
         */
        SessionBeanDescriptor sampleServiceDescriptor = new SessionBeanDescriptor("timetracker/entryejb",
                EntryLocalHome.class, EntryLocalObject.class, new EntrySessionBean());
        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);

        this.delegate = new EntryDelegate(TestHelper.NAMESPACE);

        mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);
    }

    /**
     * Test the updateCutoff time method.
     *
     * @throws Exception into Junit
     */
    public void testUpdateCutoffTime() throws Exception {
        // create cutoff time
        CutoffTimeBean bean = TestHelper.createCutoffTimeBean(1, new Date(10000));
        bean.setChanged(true);

        delegate.createCutoffTime(bean, false);

        // fectch it from database and update it
        CutoffTimeBean persisted = delegate.fetchCutoffTimeByCompanyID(bean.getCompanyId());

        persisted.setChanged(true);
        persisted.setCutoffTime(new Date(20000));
        persisted.setModificationUser("ivern");

        // update it
        delegate.updateCutoffTime(persisted, true);

        // re-fetch it
        CutoffTimeBean updated = delegate.fetchCutoffTimeById(persisted.getId());

        // verify it
        TestHelper.assertCutoffTimeBean(persisted, updated);
        assertFalse("The changed flag is invalid.", updated.isChanged());

        //delete it
        this.delegate.deleteCutoffTime(updated, true);

        try {
            this.delegate.deleteCutoffTime(updated, false);
            fail("The bean does not exist.");
        } catch (EntryManagerException e) {
            //good
        }
    }

    /**
     * Test the CanSubmitEntry method.
     *
     * @throws Exception into Junit
     */
    public void testCanSubmitEntry() throws Exception {
        // create cutofftime bean, the cutofftime is 2 weeks before
        CutoffTimeBean bean = TestHelper.createCutoffTimeBean(1, new Date(new Date().getTime() - 2*7*24*60*60*1000));
        bean.setChanged(true);
        this.delegate.createCutoffTime(bean, false);

        //one weeks ago entry,
        assertFalse("Can not submit.",
            this.delegate.canSubmitEntry(new BaseEntry() {
                public long getCompanyId() {
                    return 1;
                }

                public Date getDate() {
                    return new Date(new Date().getTime() - 7*24*60*60*1000); 
                }
            }));

    }

    /**
     * Clear the environment.
     *
     * @throws Exception into Junit
     */
    protected void tearDown() throws Exception {
    	TestHelper.clearTable(conn);
	   	 if( conn != null){
	   		 conn.close();
	   	 }
	   	 TestHelper.clearConfiguration();
    }
}
