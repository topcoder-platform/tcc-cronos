/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.stresstests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.JUnit4TestAdapter;

import org.apache.axis2.context.ServiceContext;
import org.apache.axis2.description.AxisService;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.intuit.developer.Authenticate;
import com.intuit.developer.ClientVersion;
import com.intuit.developer.CloseConnection;
import com.intuit.developer.ConnectionError;
import com.intuit.developer.GetLastError;
import com.intuit.developer.ReceiveResponseXML;
import com.intuit.developer.SendRequestXML;
import com.intuit.developer.ServerVersion;
import com.topcoder.accounting.QBWCImportInvoicesServiceSkeleton;
import com.topcoder.accounting.entities.dao.AccountingAuditRecord;
import com.topcoder.accounting.entities.dao.BillingCostExportDetail;
import com.topcoder.accounting.entities.dto.PagedResult;
import com.topcoder.accounting.entities.dto.QuickBooksImportUpdate;
import com.topcoder.accounting.service.BillingCostAuditService;
import com.topcoder.util.algorithm.hash.HashAlgorithmManager;

/**
 * <p>
 * Stress tests for <code> QBWCImportInvoicesServiceSkeleton </code>.
 * </p>
 *
 * @author dingying131
 * @version 1.0
 */
public class QBWCImportInvoicesServiceSkeletonStressTests {

    /**
     * <p>
     * The stress test times.
     * </p>
     */
    private static final int TIMES = 100;

    /**
     * <p>
     * The instance to be tested.
     * </p>
     */
    private QBWCImportInvoicesServiceSkeleton instance;

    /**
     * <p>
     * The mock billing cost audit service used in tests.
     * </p>
     */
    private BillingCostAuditService billingCostAuditService;

    /**
     * <p>
     * The mock service context used in tests.
     * </p>
     */
    private MyServiceContext serviceContext;

    /**
     * <p>
     * The mock axis service used in tests.
     * </p>
     */
    private AxisService axisService;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(QBWCImportInvoicesServiceSkeletonStressTests.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Before
    public void setUp() throws Exception {
        this.billingCostAuditService = EasyMock.createMock(BillingCostAuditService.class);
        this.axisService = new AxisService();
        this.serviceContext = new MyServiceContext();
        this.serviceContext.setAxisService(this.axisService);

        this.instance = new QBWCImportInvoicesServiceSkeleton();
        this.instance.setBillingCostAuditService(billingCostAuditService);
        this.instance.setServiceContext(serviceContext);

        // Set the page number and size
        instance.setPageNumber(1);
        instance.setPageSize(10);

        // Set the authenticate properties
        instance.setHashAlgorithmName("md5");
        instance.setUserName("admin");
        instance.setHashedPassword(HashAlgorithmManager.getInstance().getAlgorithm("md5").hashToString("admin"));
    }

    /**
     * <p>
     * Stress test for {@link QBWCImportInvoicesServiceSkeleton#sendRequestXML(com.intuit.developer.SendRequestXML)} .
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSendRequestXML() throws Exception {
        long time = logStressTestEntrance("QBWCImportInvoicesServiceSkeleton"
                + "#sendRequestXML(com.intuit.developer.SendRequestXML)");

        // Create page result
        PagedResult<BillingCostExportDetail> result = new PagedResult<BillingCostExportDetail>();
        List<BillingCostExportDetail> records = new ArrayList<BillingCostExportDetail>();

        BillingCostExportDetail detail1 = new BillingCostExportDetail();
        detail1.setBillingAmount(1.0F);
        detail1.setCustomer("A");
        BillingCostExportDetail detail2 = new BillingCostExportDetail();
        detail2.setBillingAmount(2.0F);
        detail2.setCustomer("A");
        records.add(detail1);
        records.add(detail2);

        result.setRecords(records);

        // Set mock methods
        EasyMock.expect(billingCostAuditService.getBillingCostExportDetails(false, 1, 10)).andReturn(result).anyTimes();
        EasyMock.expect(billingCostAuditService.getLatestInvoiceNumber()).andReturn("533-111").anyTimes();
        billingCostAuditService.auditAccountingAction(EasyMock.isA(AccountingAuditRecord.class));
        EasyMock.expectLastCall().anyTimes();

        EasyMock.replay(billingCostAuditService);

        // Create send XML object
        SendRequestXML sendRequestXML = new SendRequestXML();

        for (int i = 0; i < 1; i++) {
            instance.sendRequestXML(sendRequestXML);
        }

        logStressTestExit("QBWCImportInvoicesServiceSkeleton#"
                + "sendRequestXML(com.intuit.developer.SendRequestXML)", time);
    }

    /**
     * <p>
     * Stress test for
     * {@link QBWCImportInvoicesServiceSkeleton#receiveResponseXML(com.intuit.developer.ReceiveResponseXML)} .
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testReceiveResponseXML() throws Exception {
        long time = logStressTestEntrance("QBWCImportInvoicesServiceSkeleton#"
               + "receiveResponseXML(com.intuit.developer.ReceiveResponseXML)");

        // Create page result
        PagedResult<BillingCostExportDetail> result = new PagedResult<BillingCostExportDetail>();
        result.setRecords(new ArrayList<BillingCostExportDetail>());

        // Set mock methods
        billingCostAuditService.updateBillingCostExportDetails(EasyMock.isA(new ArrayList<QuickBooksImportUpdate>()
                .getClass()));
        EasyMock.expectLastCall().anyTimes();

        billingCostAuditService.auditAccountingAction(EasyMock.isA(AccountingAuditRecord.class));
        EasyMock.expectLastCall().anyTimes();

        EasyMock.replay(billingCostAuditService);

        // Create send XML object
        ReceiveResponseXML receiveResponseXML = new ReceiveResponseXML();

        for (int i = 0; i < TIMES; i++) {
            instance.receiveResponseXML(receiveResponseXML);
        }

        logStressTestExit(
                "QBWCImportInvoicesServiceSkeleton#receiveResponseXML(com.intuit.developer.ReceiveResponseXML)", time);
    }

    /**
     * <p>
     * Stress test for {@link QBWCImportInvoicesServiceSkeleton#authenticate(Authenticate)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testAuthenticate() throws Exception {
        long time = logStressTestEntrance("QBWCImportInvoicesServiceSkeleton#authenticate(Authenticate)");

        Authenticate authenticate = new Authenticate();
        authenticate.setStrUserName("admin");
        authenticate.setStrPassword("admin");

        for (int i = 0; i < TIMES; i++) {
            instance.authenticate(authenticate);
        }

        logStressTestExit("QBWCImportInvoicesServiceSkeleton#authenticate(Authenticate)", time);
    }

    /**
     * <p>
     * Stress test for {@link QBWCImportInvoicesServiceSkeleton#clientVersion(com.intuit.developer.ClientVersion)} .
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testClientVersion() throws Exception {
        long time = logStressTestEntrance("QBWCImportInvoicesServiceSkeleton#"
                + "clientVersion(com.intuit.developer.ClientVersion)");

        ClientVersion cv = new ClientVersion();

        for (int i = 0; i < TIMES; i++) {
            instance.clientVersion(cv);
        }

        logStressTestExit("QBWCImportInvoicesServiceSkeleton#clientVersion(com.intuit.developer.ClientVersion)", time);
    }

    /**
     * <p>
     * Stress test for {@link QBWCImportInvoicesServiceSkeleton#serverVersion(com.intuit.developer.ServerVersion)} .
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testServerVersion() throws Exception {
        long time = logStressTestEntrance("QBWCImportInvoicesServiceSkeleton#"
                + "serverVersion(com.intuit.developer.ServerVersion)");

        ServerVersion sv = new ServerVersion();

        for (int i = 0; i < TIMES; i++) {
            instance.serverVersion(sv);
        }

        logStressTestExit("QBWCImportInvoicesServiceSkeleton#serverVersion(com.intuit.developer.ServerVersion)", time);
    }

    /**
     * <p>
     * Stress test for {@link QBWCImportInvoicesServiceSkeleton#getLastError(com.intuit.developer.GetLastError)} .
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetLastError() throws Exception {
        long time = logStressTestEntrance("QBWCImportInvoicesServiceSkeleton#"
                + "getLastError(com.intuit.developer.GetLastError)");

        GetLastError error = new GetLastError();

        for (int i = 0; i < TIMES; i++) {
            instance.getLastError(error);
        }

        logStressTestExit("QBWCImportInvoicesServiceSkeleton#getLastError(com.intuit.developer.GetLastError)", time);
    }

    /**
     * <p>
     * Stress test for {@link QBWCImportInvoicesServiceSkeleton#closeConnection(com.intuit.developer.CloseConnection)} .
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testCloseConnection() throws Exception {
        long time = logStressTestEntrance("QBWCImportInvoicesServiceSkeleton#"
                + "closeConnection(com.intuit.developer.GetLastError)");

        CloseConnection closeConnection = new CloseConnection();

        for (int i = 0; i < TIMES; i++) {
            instance.closeConnection(closeConnection);
        }

        logStressTestExit("QBWCImportInvoicesServiceSkeleton#closeConnection(com.intuit.developer.GetLastError)", time);
    }

    /**
     * <p>
     * Stress test for {@link QBWCImportInvoicesServiceSkeleton#connectionError(com.intuit.developer.ConnectionError)} .
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testConnectionError() throws Exception {
        long time = logStressTestEntrance("QBWCImportInvoicesServiceSkeleton#"
                + "connectionError(com.intuit.developer.GetLastError)");

        ConnectionError error = new ConnectionError();

        for (int i = 0; i < TIMES; i++) {
            instance.connectionError(error);
        }

        logStressTestExit("QBWCImportInvoicesServiceSkeleton#connectionError(com.intuit.developer.GetLastError)", time);
    }

    /**
     * <p>
     * Log the start of stress test method and mark the start time.
     * </p>
     *
     * @param method
     *            method name
     * @return the start time
     */
    private long logStressTestEntrance(String method) {
        System.out.println("Start stress tests for " + method + ", times is " + TIMES);
        return System.currentTimeMillis();
    }

    /**
     * <p>
     * Log the end of stress test method and mark the start time.
     * </p>
     *
     * @param method
     *            method name
     * @param start
     *            start time
     */
    private void logStressTestExit(String method, long start) {
        System.out.println("End stress tests for " + method + ", cost is " + (System.currentTimeMillis() - start)
                + " miliSeconds");
    }
}

/**
 * <p>
 * Mock ServiceContext used for test.
 * </p>
 */
class MyServiceContext extends ServiceContext {

    /**
     * <p>
     * The axis service.
     * </p>
     */
    private AxisService axisService;

    /**
     * <p>
     * Gets the axis service.
     * </p>
     *
     * @return the axis service
     */
    public org.apache.axis2.description.AxisService getAxisService() {
        return this.axisService;
    }

    /**
     * <p>
     * Setter method for the axisService.
     * </p>
     *
     * @param axisService
     *            the axisService to set
     */
    public void setAxisService(AxisService axisService) {
        this.axisService = axisService;
    }

    /**
     * <p>
     * Mock methods for test.
     * </p>
     *
     * @param string
     *            the key for property
     * @return the properties for this key
     */
    public Map<String, long[]> getProperty(String string) {
        Map<String, long[]> map = new HashMap<String, long[]>();
        map.put("1", new long[] {1L, 2L});
        map.put("2", new long[] {1L, 2L});
        return map;
    }
}