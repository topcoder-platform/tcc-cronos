/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail;

import java.io.FileInputStream;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.timetracker.invoice.servicedetail.ejb.LocalServiceDetail;
import com.topcoder.timetracker.invoice.servicedetail.ejb.LocalServiceDetailHome;
import com.topcoder.timetracker.invoice.servicedetail.ejb.ServiceDetailBean;

/**
 * Demo for TimeTracker Service Detail 3.2.
 *
 * @author enefem21
 * @version 1.0
 */
public class Demo extends DBTestCase {

    /** Context used in this unit test. */
    private Context context;

    /**
     * Constructor of the unit test.
     *
     * @param name
     *            test name
     * @throws Exception
     *             to JUnit
     */
    public Demo(String name) throws Exception {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.informix.jdbc.IfxDriver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
            "jdbc:informix-sqli://192.168.1.101:1526/tt_project:informixserver=topcoder");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "informix");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "123456");

        // database connection
        Class.forName("com.informix.jdbc.IfxDriver");

    }

    /**
     * <p>
     * Return the suite for this unit test.
     * </p>
     *
     * @return the suite
     */
    public static Test suite() {
        return new TestSuite(Demo.class);
    }

    /**
     * Sets the unit test up.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        TestHelper.loadConfiguration("service_detail_manager_accuracy.xml");
        TestHelper.loadConfiguration("service_detail_bean_accuracy.xml");
        TestHelper.loadConfiguration("service_detail_dao_accuracy.xml");

        MockContextFactory.setAsInitial();

        context = new InitialContext();

        // creates an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        // creates deployment descriptor of our sample bean. MockEjb does not support XML descriptors.
        SessionBeanDescriptor localServiceDetailDescriptor =
            new SessionBeanDescriptor("sessionBean", LocalServiceDetailHome.class, LocalServiceDetail.class,
                ServiceDetailBean.class);
        // Deploy operation simply creates Home and binds it to JNDI
        mockContainer.deploy(localServiceDetailDescriptor);

        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

    }

    /**
     * Tears the unit test down.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {

        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");

        TestHelper.clearNamespaces();
        super.tearDown();
    }

    /**
     * Demo.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDemo() throws Exception {
        // create instance of manager
        ServiceDetailManager manager = new ServiceDetailManagerImpl();

        // get the details
        InvoiceServiceDetail firstDetail = manager.retrieveServiceDetail(1L);
        InvoiceServiceDetail secondDetail = manager.retrieveServiceDetail(2L);

        // Or by using batch operation; result should be the same
        long[] detailsArray = new long[2];
        detailsArray[0] = 1;
        detailsArray[1] = 2;
        InvoiceServiceDetail[] details = manager.retrieveServiceDetails(detailsArray);
        firstDetail = details[0];
        secondDetail = details[1];

        // we may create some Detail
        InvoiceServiceDetail thirdDetail = new InvoiceServiceDetail();

        Invoice invoice = new Invoice();
        invoice.setId(20);
        thirdDetail.setInvoice(invoice);
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setId(121);
        timeEntry.setHours(10);
        timeEntry.setCreationUser("testuser");
        thirdDetail.setTimeEntry(timeEntry);
        thirdDetail.setCreationUser("test");
        thirdDetail.setModificationUser("test");

        // lets set it attributes like in firstDetail by using just simple setters.
        // add new Detail to data store and perform audit
        manager.addServiceDetail(thirdDetail, true);

        // now we may ensure that dao generate new id for this Detail
        System.out.println("" + thirdDetail.getId());

        // Now it is possible to delete it and perform audit
        manager.deleteServiceDetail(thirdDetail.getId(), true);

        // It is possible to update something in details
        firstDetail.getInvoice().setId(10);

        /*
         * We may also look for details by invoice id. This array should contain detail with the same id as in
         * firstDetail
         */
        InvoiceServiceDetail[] details1 = manager.retrieveServiceDetails(10);

        /*
         * except retrieve, delete and update one or several details we have methods which allows to retrieve or
         * delete all details
         */
        // retrieve all details
        InvoiceServiceDetail[] details2 = manager.retrieveAllServiceDetails();

        // delete all details and do not perform audit
        manager.deleteAllServiceDetails(false);

    }

    /**
     * The DataSet loader used by DBUnit.
     *
     * @return the data set
     *
     * @throws Exception
     *             to JUnit
     */
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSet(new FileInputStream("test_files/partial.xml"));
    }
}
