/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.accuracytests.ejb;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.TestCase;

import org.mockejb.EjbBeanAccess;
import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.topcoder.timetracker.invoice.servicedetail.accuracytests.TestHelper;
import com.topcoder.timetracker.invoice.servicedetail.ejb.LocalServiceDetail;
import com.topcoder.timetracker.invoice.servicedetail.ejb.LocalServiceDetailHome;
import com.topcoder.timetracker.invoice.servicedetail.ejb.ServiceDetailBean;

/**
 * <p>
 * The accuracy tests for the class ServiceDetailBean.
 * </p>
 * @author KLW
 * @version 1.1
 */
public class AccuracyTestsServiceDetailBean extends TestCase {
    /** The ServiceDetailBean instance for accuracy test. */
    private ServiceDetailBean serviceDetailBean;

    /** JDBC connection used in the unit test. */
    private Connection jdbcConnection;

    /** Context used in this unit test. */
    private Context context;

    /**
     * <p>
     * set up the environment.
     * </p>
     * @throws Exception
     *             Thrown to the JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfiguration(TestHelper.FILE_BEAN_CONFIG);
        TestHelper.loadConfiguration(TestHelper.FILE_DAO_CONFIG);
        MockContextFactory.setAsInitial();

        context = new InitialContext();

        MockContainer mockContainer = new MockContainer(context);

        SessionBeanDescriptor localServiceDetailDescriptor = new SessionBeanDescriptor("sessionBean",
                LocalServiceDetailHome.class, LocalServiceDetail.class, ServiceDetailBean.class);

        mockContainer.deploy(localServiceDetailDescriptor);

        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        LocalServiceDetailHome sampleHome = (LocalServiceDetailHome) context.lookup("sessionBean");

        LocalServiceDetail sampleService = sampleHome.create();

        serviceDetailBean = (ServiceDetailBean) ((EjbBeanAccess) sampleService).getBean();

        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");
    }

    /**
     * <p>
     * tear down the test environment.
     * </p>
     * @throws Exception
     *             Thrown to the JUnit.
     */
    public void tearDown() throws Exception {
        MockContextFactory.revertSetAsInitial();
        TestHelper.releaseConfiguration();
    }

    /**
     * <p>
     * The accuracy test for the default constructor.
     * </p>
     */
    public void testCtor() {
        assertNotNull("The creation is failed", new ServiceDetailBean());
    }

    /**
     * <p>
     * The accuracy test for the method ejbCreate.
     * </p>
     * @throws Exception
     *             thrown to the JUnit.
     */
    public void testEjbCreate() throws Exception {
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        LocalServiceDetailHome sampleHome = (LocalServiceDetailHome) context.lookup("sessionBean");

        LocalServiceDetail sampleService = sampleHome.create();

        ((EjbBeanAccess) sampleService).getBean();
    }
}
