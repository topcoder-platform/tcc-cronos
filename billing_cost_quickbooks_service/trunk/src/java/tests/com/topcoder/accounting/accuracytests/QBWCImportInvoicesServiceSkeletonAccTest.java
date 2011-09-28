/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis2.context.ServiceContext;
import org.apache.axis2.description.AxisService;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.intuit.developer.ArrayOfString;
import com.intuit.developer.Authenticate;
import com.intuit.developer.AuthenticateResponse;
import com.intuit.developer.ClientVersionResponse;
import com.intuit.developer.CloseConnectionResponse;
import com.intuit.developer.ConnectionErrorResponse;
import com.intuit.developer.GetLastErrorResponse;
import com.intuit.developer.QBWebConnectorSvcSkeletonInterface;
import com.intuit.developer.ReceiveResponseXML;
import com.intuit.developer.ReceiveResponseXMLResponse;
import com.intuit.developer.SendRequestXML;
import com.intuit.developer.SendRequestXMLResponse;
import com.intuit.developer.ServerVersionResponse;
import com.topcoder.accounting.QBWCImportInvoicesServiceSkeleton;
import com.topcoder.accounting.entities.dao.BillingCostExportDetail;
import com.topcoder.accounting.entities.dto.PagedResult;
import com.topcoder.accounting.service.BillingCostAuditService;
import com.topcoder.util.algorithm.hash.HashAlgorithmManager;
import com.topcoder.util.algorithm.hash.algorithm.HashAlgorithm;

/**
 * <p>
 * Accuracy tests for class <code>QBWCImportInvoicesServiceSkeleton</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class QBWCImportInvoicesServiceSkeletonAccTest {

    /**
     * Represents the <code>QBWCImportInvoicesServiceSkeletonAcc</code> instance used to test
     * against.
     */
    private QBWCImportInvoicesServiceSkeleton impl;

    /**
     * Creates a test suite for unit tests in this test case.
     */
    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(QBWCImportInvoicesServiceSkeletonAccTest.class);
    }

    /**
     * Set up the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        impl = new QBWCImportInvoicesServiceSkeleton();
        impl.setUserName("test");
        HashAlgorithmManager manager = HashAlgorithmManager.getInstance();
        HashAlgorithm hp = manager.getAlgorithm("md5");
        impl.setHashedPassword(hp.hashToString("pass"));
        impl.setLastInvoiceNumber("511-113");
        impl.setServiceContext(EasyMock.createNiceMock(ServiceContext.class));
        impl.setHashAlgorithmName("md5");
        impl.setBillingCostAuditService(EasyMock.createNiceMock(BillingCostAuditService.class));
        impl.setLoggerName("test");
        impl.setPageNumber(1);
        impl.setPageSize(1);
    }

    /**
     * Tear down the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @After
    public void tearDown() throws Exception {
        impl = null;
    }

    /**
     * Inheritance test, verifies <code>QBWCImportInvoicesServiceSkeleton</code> subclasses should
     * be correct.
     */
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.",
            impl instanceof QBWebConnectorSvcSkeletonInterface);
    }

    /**
     * Accuracy test for the constructor <code>QBWCImportInvoicesServiceSkeleton()</code>.<br>
     * . Instance should be created successfully.
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * Accuracy test for the method <code>authenticate(Authenticate)</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testAuthenticate() throws Exception {
        Authenticate authenticate = new Authenticate();
        authenticate.setStrUserName("userName");
        authenticate.setStrPassword("password");
        AuthenticateResponse response = impl.authenticate(authenticate);
        assertNotNull("The return value should be not null", response);
        ArrayOfString result = response.getAuthenticateResult();
        assertEquals("Should find 2 strings.", 2, result.getString().length);
        assertEquals("Authentication result should be true.", "false", result.getString()[1]);

        impl.setUserName("userName");
        impl.setHashedPassword(HashAlgorithmManager.getInstance().getAlgorithm("md5")
            .hashToString("password"));
        response = impl.authenticate(authenticate);
        assertNotNull("The return value should be not null", response);
        result = response.getAuthenticateResult();
        assertEquals("Should find 2 strings.", 2, result.getString().length);
        assertEquals("Authentication result should be true.", "true", result.getString()[1]);
    }

    /**
     * Accuracy test for the method <code>sendRequestXML(SendRequestXML)</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testSendRequestXML() throws Exception {
        BillingCostAuditService bsas = EasyMock.createNiceMock(BillingCostAuditService.class);
        impl.setBillingCostAuditService(bsas);
        PagedResult<BillingCostExportDetail> result = new PagedResult<BillingCostExportDetail>();
        List<BillingCostExportDetail> detail = new ArrayList<BillingCostExportDetail>();
        BillingCostExportDetail billingCostExportDetail = new BillingCostExportDetail();
        billingCostExportDetail.setId(4);
        billingCostExportDetail.setCustomer("bob");
        billingCostExportDetail.setBillingAmount(5.5f);
        detail.add(billingCostExportDetail);
        result.setRecords(detail);

        EasyMock.expect(
            bsas.getBillingCostExportDetails(EasyMock.anyBoolean(), EasyMock.anyInt(),
                EasyMock.anyInt())).andReturn(result);
        EasyMock.replay(bsas);

        SendRequestXML sendRequestXML = new SendRequestXML();
        SendRequestXMLResponse response = impl.sendRequestXML(sendRequestXML);
        assertNotNull("The return value should be not null", response);
        String xmlResult = response.getSendRequestXMLResult();
        assertTrue("The response should contains expect string",
            xmlResult.indexOf("qbxml version=\"2.0\"") > 1);
        assertTrue("The response should contains expect string",
            xmlResult.indexOf("<RefNumber>511-114</RefNumber>") > 1);
        assertTrue("The response should contains expect string",
            xmlResult.indexOf("<Rate>5.5</Rate>") > 1);
    }

    /**
     * Accuracy test for the method <code>receiveResponseXML(ReceiveResponseXML)</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testReceiveResponseXML() throws Exception {
        ServiceContext serviceContext = EasyMock.createNiceMock(ServiceContext.class);
        impl.setServiceContext(serviceContext);
        Map<String, long[]> map = new HashMap<String, long[]>();
        map.put("510-111", new long[] { 1, 2, 3 });
        map.put("510-112", new long[] { 4, 5 });
        map.put("510-113", new long[] { 6 });
        EasyMock.expect(serviceContext.getProperty("QuickBooksImportUpdates")).andReturn(map);
        EasyMock.replay(serviceContext);

        ReceiveResponseXMLResponse response = impl.receiveResponseXML(new ReceiveResponseXML());
        assertEquals("The response result should be same as ", 3,
            response.getReceiveResponseXMLResult());
    }

    /**
     * Accuracy test for the method <code>init(ServiceContext)</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testInit() throws Exception {
        ServiceContext serviceContext = EasyMock.createNiceMock(ServiceContext.class);
        Map<String, long[]> map = new HashMap<String, long[]>();
        map.put("510-111", new long[] { 1, 2, 3 });
        map.put("510-112", new long[] { 4, 5 });
        map.put("510-113", new long[] { 6 });
        EasyMock.expect(serviceContext.getProperty("QuickBooksImportUpdates")).andReturn(map);
        EasyMock.replay(serviceContext);

        impl.init(serviceContext);
        assertEquals("Service context should be set.", serviceContext, impl.getServiceContext());
    }

    /**
     * Accuracy test for the method <code>clientVersion(ClientVersion)</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testClientVersion() throws Exception {
        ClientVersionResponse response = impl.clientVersion(null);
        assertEquals("ClientVersionResult should match.", "", response.getClientVersionResult());
    }

    /**
     * Accuracy test for the method <code>serverVersion(ServerVersion)</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testServerVersion() throws Exception {
        ServerVersionResponse response = impl.serverVersion(null);
        assertEquals("ServerVersionResult should match.", "", response.getServerVersionResult());
    }

    /**
     * Accuracy test for the method <code>getLastError(GetLastError)</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testGetLastError() throws Exception {
        ServiceContext serviceContext = EasyMock.createNiceMock(ServiceContext.class);
        impl.setServiceContext(serviceContext);
        AxisService as = new AxisService();
        as.addParameter("errorMsg", "abc");
        EasyMock.expect(serviceContext.getAxisService()).andReturn(as);
        EasyMock.replay(serviceContext);

        GetLastErrorResponse response = impl.getLastError(null);
        assertEquals("The response result should be same as ", "abc",
            response.getGetLastErrorResult());
    }

    /**
     * Accuracy test for the method <code>closeConnection(CloseConnection)</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testCloseConnection() throws Exception {
        CloseConnectionResponse response = impl.closeConnection(null);
        assertEquals("The response result should be same as ", "ok",
            response.getCloseConnectionResult());
    }

    /**
     * Accuracy test for the method <code>connectionError(ConnectionError)</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testConnectionError() throws Exception {
        ConnectionErrorResponse response = impl.connectionError(null);
        assertEquals("The response result should be same as ", "done",
            response.getConnectionErrorResult());
    }

}
