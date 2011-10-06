/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting;

import java.io.FileReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis2.context.ServiceContext;
import org.apache.axis2.description.AxisService;

import com.intuit.developer.ArrayOfString;
import com.intuit.developer.Authenticate;
import com.intuit.developer.AuthenticateResponse;
import com.intuit.developer.ClientVersion;
import com.intuit.developer.CloseConnection;
import com.intuit.developer.ConnectionError;
import com.intuit.developer.GetLastError;
import com.intuit.developer.ReceiveResponseXML;
import com.intuit.developer.ReceiveResponseXMLResponse;
import com.intuit.developer.SendRequestXML;
import com.intuit.developer.SendRequestXMLResponse;
import com.intuit.developer.ServerVersion;
import com.topcoder.accounting.entities.dao.BillingCostExportDetail;
import com.topcoder.accounting.entities.dto.PagedResult;
import com.topcoder.accounting.entities.dto.QuickBooksImportUpdate;
import com.topcoder.accounting.mock.ServiceContextMock;
import com.topcoder.accounting.service.mock.BillingCostAuditServiceMockImpl;
import com.topcoder.util.algorithm.hash.HashAlgorithmManager;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for <code> QBWCImportInvoicesServiceSkeleton </code>.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class QBWCImportInvoicesServiceSkeletonUnitTests extends TestCase {
    /**
     * <p>
     * Test files folder.
     * </p>
     */
    private static final String TEST_FILES = "test_files/";
    /**
     * <p>
     * The instance to be tested.
     * </p>
     */
    private QBWCImportInvoicesServiceSkeleton instance;
    /**
     * <p>
     * The billing cost audit service used in tests.
     * </p>
     */
    private BillingCostAuditServiceMockImpl billingCostAuditService;
    /**
     * <p>
     * The service context used in tests.
     * </p>
     */
    private ServiceContextMock serviceContext;
    /**
     * <p>
     * The axis service used in tests.
     * </p>
     */
    private AxisService axisService;

    /**
     * <p>
     * Setup the test environment.
     * </p>
     */
    public void setUp() {
        this.billingCostAuditService = new BillingCostAuditServiceMockImpl();
        this.axisService = new AxisService();
        this.serviceContext = new ServiceContextMock();
        this.serviceContext.setAxisService(this.axisService);

        this.instance = new QBWCImportInvoicesServiceSkeleton();
        this.instance.setBillingCostAuditService(this.billingCostAuditService);
        this.instance.setServiceContext(this.serviceContext);
    }

    /**
     * <p>
     * Release resources.
     * </p>
     */
    public void tearDown() {
        this.instance = null;
    }

    /**
     * <p>
     * Accuracy test for public QBWCImportInvoicesServiceSkeleton().
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtorAccuracy() throws Exception {
        this.instance = new QBWCImportInvoicesServiceSkeleton();
        assertNotNull("Instance should be created.", this.instance);
    }

    /**
     * <p>
     * Accuracy test for public AuthenticateResponse authenticate(Authenticate authenticate). If the username and
     * password match with the configured ones, authentication result should be true.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAuthenticateAccuracy_Success() throws Exception {
        String userName = "user";
        String password = "pass";
        Authenticate authenticate = new Authenticate();
        authenticate.setStrUserName(userName);
        authenticate.setStrPassword(password);
        this.instance.setHashAlgorithmName("md5");
        this.instance.setUserName(userName);
        this.instance.setHashedPassword(HashAlgorithmManager.getInstance().getAlgorithm("md5").hashToString(password));
        AuthenticateResponse response = this.instance.authenticate(authenticate);
        ArrayOfString result = response.getAuthenticateResult();
        assertEquals("Should find 2 strings.", 2, result.getString().length);
        assertEquals("Authentication result should be true.", "true", result.getString()[1]);
    }

    /**
     * <p>
     * Accuracy test for public AuthenticateResponse authenticate(Authenticate authenticate). If the username does not
     * match with the configured one, authentication result should be false.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAuthenticateAccuracy_UsernameNotMatch() throws Exception {
        String userName = "user";
        String password = "pass";
        Authenticate authenticate = new Authenticate();
        authenticate.setStrUserName(userName);
        authenticate.setStrPassword(password);
        this.instance.setHashAlgorithmName("md5");
        this.instance.setUserName("other");
        this.instance.setHashedPassword(HashAlgorithmManager.getInstance().getAlgorithm("md5").hashToString(password));
        AuthenticateResponse response = this.instance.authenticate(authenticate);
        ArrayOfString result = response.getAuthenticateResult();
        assertEquals("Should find 2 strings.", 2, result.getString().length);
        assertEquals("Authentication result should match.", "false", result.getString()[1]);
    }

    /**
     * <p>
     * Accuracy test for public AuthenticateResponse authenticate(Authenticate authenticate). If the password does not
     * match with the configured one, authentication result should be false.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAuthenticateAccuracy_PasswordNotMatch() throws Exception {
        String userName = "user";
        String password = "pass";
        Authenticate authenticate = new Authenticate();
        authenticate.setStrUserName(userName);
        authenticate.setStrPassword("other");
        this.instance.setHashAlgorithmName("md5");
        this.instance.setUserName(userName);
        this.instance.setHashedPassword(HashAlgorithmManager.getInstance().getAlgorithm("md5").hashToString(password));
        AuthenticateResponse response = this.instance.authenticate(authenticate);
        ArrayOfString result = response.getAuthenticateResult();
        assertEquals("Should find 2 strings.", 2, result.getString().length);
        assertEquals("Authentication result should match.", "false", result.getString()[1]);
    }

    /**
     * <p>
     * Accuracy test for public AuthenticateResponse authenticate(Authenticate authenticate). If the password provided
     * is null, authentication result should be false.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAuthenticateAccuracy_NullPassword() throws Exception {
        String userName = "user";
        Authenticate authenticate = new Authenticate();
        authenticate.setStrUserName(userName);
        authenticate.setStrPassword(null);
        this.instance.setHashAlgorithmName("md5");
        this.instance.setUserName(userName);
        this.instance.setHashedPassword("somehash");
        AuthenticateResponse response = this.instance.authenticate(authenticate);
        ArrayOfString result = response.getAuthenticateResult();
        assertEquals("Should find 2 strings.", 2, result.getString().length);
        assertEquals("Authentication result should match.", "false", result.getString()[1]);
    }

    /**
     * <p>
     * Accuracy test for public AuthenticateResponse authenticate(Authenticate authenticate). If the hash algorithm
     * cannot be found, authentication result should be false.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAuthenticateAccuracy_NoHashAlgorithm() throws Exception {
        String userName = "user";
        String password = "pass";
        Authenticate authenticate = new Authenticate();
        authenticate.setStrUserName(userName);
        authenticate.setStrPassword("other");
        this.instance.setHashAlgorithmName("NoSuchAlgorithm");
        this.instance.setUserName(userName);
        this.instance.setHashedPassword(HashAlgorithmManager.getInstance().getAlgorithm("md5").hashToString(password));
        AuthenticateResponse response = this.instance.authenticate(authenticate);
        assertNull("Authenticate result should be null.", response.getAuthenticateResult());
        assertNotNull("Error message should be set.", serviceContext.getAxisService().getParameterValue("errorMsg"));
    }

    /**
     * <p>
     * Accuracy test for public SendRequestXMLResponse sendRequestXML(SendRequestXML sendRequestXML). Use the latest
     * invoice number retrieved from billing cost audit service.
     * </p>
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    public void testSendRequestXMLAccuracy_AuditServiceLatestInvoiceNumberUsed() throws Exception {
        List<BillingCostExportDetail> records = new ArrayList<BillingCostExportDetail>();
        records.add(prepareExportDetail(1, "customer1", 1.1f));
        records.add(prepareExportDetail(2, "customer1", 1.2f));
        records.add(prepareExportDetail(3, "customer2", 1.3f));

        PagedResult<BillingCostExportDetail> billingCostExportDetails = new PagedResult<BillingCostExportDetail>();
        billingCostExportDetails.setRecords(records);

        this.billingCostAuditService.setBillingCostExportDetails(billingCostExportDetails);
        this.billingCostAuditService.setLatestInvoiceNumber("511-110");

        SendRequestXMLResponse response = this.instance.sendRequestXML(new SendRequestXML());
        assertMatchFile("SendRequestXML should match.",
                TEST_FILES + "request1.xml", response.getSendRequestXMLResult());

        Map<String, long[]> updates = (Map<String, long[]>) this.serviceContext.getProperty("QuickBooksImportUpdates");
        assertEquals("Should find updates about 2 customer.", 2, updates.size());
        assertLongArrayMatch("Should find 2 updates for customer1.", new long[] {1, 2}, updates.get("511-111"));
        assertLongArrayMatch("Should find 1 updates for customer2.", new long[] {3}, updates.get("511-112"));
    }

    /**
     * <p>
     * Accuracy test for public SendRequestXMLResponse sendRequestXML(SendRequestXML sendRequestXML). Use the latest
     * invoice number retrieved set to service skeleton since the one retrieved from billing cost audit service is null.
     * </p>
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    public void testSendRequestXMLAccuracy_SkeletonLatestInvoiceNumberUsed() throws Exception {
        List<BillingCostExportDetail> records = new ArrayList<BillingCostExportDetail>();
        records.add(prepareExportDetail(1, "customer1", 1.1f));
        records.add(prepareExportDetail(2, "customer1", 1.2f));
        records.add(prepareExportDetail(3, "customer2", 1.3f));

        PagedResult<BillingCostExportDetail> billingCostExportDetails = new PagedResult<BillingCostExportDetail>();
        billingCostExportDetails.setRecords(records);

        this.billingCostAuditService.setBillingCostExportDetails(billingCostExportDetails);
        this.instance.setLastInvoiceNumber("511-110");

        SendRequestXMLResponse response = this.instance.sendRequestXML(new SendRequestXML());
        assertMatchFile("SendRequestXML should match.", TEST_FILES + "request1.xml",
                response.getSendRequestXMLResult());

        Map<String, long[]> updates = (Map<String, long[]>) this.serviceContext.getProperty("QuickBooksImportUpdates");
        assertEquals("Should find updates about 2 customer.", 2, updates.size());
        assertLongArrayMatch("Should find 2 updates for customer1.", new long[] {1, 2}, updates.get("511-111"));
        assertLongArrayMatch("Should find 1 updates for customer2.", new long[] {3}, updates.get("511-112"));
    }

    /**
     * <p>
     * Accuracy test for public SendRequestXMLResponse sendRequestXML(SendRequestXML sendRequestXML). Test whether the
     * request XML is correct when export details records is an empty list.
     * </p>
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    public void testSendRequestXMLAccuracy_EmptyRecords() throws Exception {
        PagedResult<BillingCostExportDetail> billingCostExportDetails = new PagedResult<BillingCostExportDetail>();
        billingCostExportDetails.setRecords(new ArrayList<BillingCostExportDetail>());

        this.billingCostAuditService.setBillingCostExportDetails(billingCostExportDetails);
        this.instance.setLastInvoiceNumber("511-110");

        SendRequestXMLResponse response = this.instance.sendRequestXML(new SendRequestXML());
        assertMatchFile("SendRequestXML should match.", TEST_FILES + "request2.xml",
                response.getSendRequestXMLResult());

        Map<String, long[]> updates = (Map<String, long[]>) this.serviceContext.getProperty("QuickBooksImportUpdates");
        assertEquals("Should find updates about 0 customer.", 0, updates.size());
    }

    /**
     * <p>
     * Accuracy test for receiveResponseXML().
     * </p>
     * @throws Exception to JUnit
     */
    public void testReceiveResponseXMLAccuracy() throws Exception {
        Map<String, long[]> map = new HashMap<String, long[]>();
        map.put("550-111", new long[] {1, 2});
        map.put("550-112", new long[] {3});
        this.serviceContext.setProperty("QuickBooksImportUpdates", map);
        ReceiveResponseXMLResponse response = this.instance.receiveResponseXML(new ReceiveResponseXML());
        assertEquals("Updates size should match.", 2, response.getReceiveResponseXMLResult());
        List<QuickBooksImportUpdate> updates = this.billingCostAuditService.getUpdates();
        assertEquals("Update QuickBooksImportUpdate size should match.", 2, updates.size());
        QuickBooksImportUpdate update1 = updates.get(0);
        QuickBooksImportUpdate update2 = updates.get(1);
        if (update1.getInvoiceNumber().equals("550-112")) {
            update2 = update1;
            update1 = updates.get(1);
        }
        assertEquals("Invoice number of update 1 should match.", "550-111", update1.getInvoiceNumber());
        assertLongArrayMatch("Invoice Ids of update 1 should match.", map.get("550-111"),
                update1.getBillingCostExportDetailIds());
        assertEquals("Invoice number of update 2 should match.", "550-112", update2.getInvoiceNumber());
        assertLongArrayMatch("Invoice Ids of update 2 should match.", map.get("550-112"),
                update2.getBillingCostExportDetailIds());
    }

    /**
     * <p>
     * Accuracy test for receiveResponseXML(). If no update map in service context, 0 should be returned as result.
     * </p>
     * @throws Exception to JUnit
     */
    public void testReceiveResponseXMLAccuracy_NoUpdateMap() throws Exception {
        ReceiveResponseXMLResponse response = this.instance.receiveResponseXML(new ReceiveResponseXML());
        assertEquals("Updates size should match.", 0, response.getReceiveResponseXMLResult());
    }

    /**
     * <p>
     * Accuracy test for public void init(ServiceContext serviceContext).
     * </p>
     * @throws Exception to JUnit
     */
    public void testInitAccuracy() throws Exception {
        this.serviceContext = new ServiceContextMock();
        this.instance.init(this.serviceContext);
        assertEquals("Service context should be set.", this.serviceContext, this.instance.getServiceContext());
    }

    /**
     * <p>
     * Accuracy test for public void destroy(ServiceContext serviceContext).
     * </p>
     * @throws Exception to JUnit
     */
    public void testDestroyAccuracy() throws Exception {
        this.serviceContext.setProperty("QuickBooksImportUpdates", new HashMap<String, long[]>());
        this.instance.destroy(this.serviceContext);
        assertNull("Updates should be removed from context.",
                this.serviceContext.getProperty("QuickBooksImportUpdates"));
    }

    /**
     * <p>
     * Accuracy test for public ClientVersionResponse clientVersion(ClientVersion clientVersion).
     * </p>
     * @throws Exception to JUnit
     */
    public void testClientVersionAccuracy() throws Exception {
        assertEquals("ClientVersionResult should match.", "", this.instance.clientVersion(new ClientVersion())
                .getClientVersionResult());
    }

    /**
     * <p>
     * Accuracy test for public ServerVersionResponse serverVersion(ServerVersion serverVersion).
     * </p>
     * @throws Exception to JUnit
     */
    public void testServerVersionAccuracy() throws Exception {
        assertEquals("ServerVersionResult should match.", "", this.instance.serverVersion(new ServerVersion())
                .getServerVersionResult());
    }

    /**
     * <p>
     * Accuracy test for public GetLastErrorResponse getLastError(GetLastError getLastError).
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetLastErrorAccuracy() throws Exception {
        String lastError = "lastError";
        this.serviceContext.getAxisService().addParameter("errorMsg", lastError);
        assertEquals("Error message should match.", lastError, this.instance.getLastError(new GetLastError())
                .getGetLastErrorResult());
    }

    /**
     * <p>
     * Accuracy test for public CloseConnectionResponse closeConnection(CloseConnection closeConnection).
     * </p>
     * @throws Exception to JUnit
     */
    public void testCloseConnectionAccuracy() throws Exception {
        assertEquals("Result should match.", "ok", this.instance.closeConnection(new CloseConnection())
                .getCloseConnectionResult());
    }

    /**
     * <p>
     * Accuracy test for public ConnectionErrorResponse connectionError(ConnectionError connectionError).
     * </p>
     * @throws Exception to JUnit
     */
    public void testConnectionErrorAccuracy() throws Exception {
        assertEquals("Result should match.", "done", this.instance.connectionError(new ConnectionError())
                .getConnectionErrorResult());
    }

    /**
     * <p>
     * Accuracy test for public ServiceContext getServiceContext().
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetServiceContextAccuracy() throws Exception {
        ServiceContext context = new ServiceContext();
        this.instance.setServiceContext(context);
        assertEquals("ServiceContext should match.", context, this.instance.getServiceContext());
    }

    /**
     * <p>
     * Accuracy test for public void setServiceContext(ServiceContext serviceContext).
     * </p>
     * @throws Exception to JUnit
     */
    public void testSetServiceContextAccuracy() throws Exception {
        ServiceContext context = new ServiceContext();
        this.instance.setServiceContext(context);
        assertEquals("ServiceContext should match.", context, this.instance.getServiceContext());
    }

    /**
     * <p>
     * Accuracy test for public int getPageSize().
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetPageSizeAccuracy() throws Exception {
        int pageSize = 123;
        this.instance.setPageSize(pageSize);
        assertEquals("Page size should match.", pageSize, this.instance.getPageSize());
    }

    /**
     * <p>
     * Accuracy test for public void setPageSize(int pageSize).
     * </p>
     * @throws Exception to JUnit
     */
    public void testSetPageSizeAccuracy() throws Exception {
        int pageSize = 123;
        this.instance.setPageSize(pageSize);
        assertEquals("Page size should match.", pageSize, this.instance.getPageSize());
    }

    /**
     * <p>
     * Accuracy test for public int getPageNumber().
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetPageNumberAccuracy() throws Exception {
        int pageNumber = 123;
        this.instance.setPageNumber(pageNumber);
        assertEquals("PageNumber should match.", pageNumber, this.instance.getPageNumber());
    }

    /**
     * <p>
     * Accuracy test for public void setPageNumber(int pageNumber).
     * </p>
     * @throws Exception to JUnit
     */
    public void testSetPageNumberAccuracy() throws Exception {
        int pageNumber = 123;
        this.instance.setPageNumber(pageNumber);
        assertEquals("PageNumber should match.", pageNumber, this.instance.getPageNumber());
    }

    /**
     * <p>
     * Accuracy test for public BillingCostAuditService getBillingCostAuditService().
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetBillingCostAuditServiceAccuracy() throws Exception {
        this.billingCostAuditService = new BillingCostAuditServiceMockImpl();
        this.instance.setBillingCostAuditService(billingCostAuditService);
        assertEquals("BillingCostAuditService should match.", billingCostAuditService,
                this.instance.getBillingCostAuditService());
    }

    /**
     * <p>
     * Accuracy test for public void setBillingCostAuditService(BillingCostAuditService billingCostAuditService).
     * </p>
     * @throws Exception to JUnit
     */
    public void testSetBillingCostAuditServiceAccuracy() throws Exception {
        this.billingCostAuditService = new BillingCostAuditServiceMockImpl();
        this.instance.setBillingCostAuditService(billingCostAuditService);
        assertEquals("BillingCostAuditService should match.", billingCostAuditService,
                this.instance.getBillingCostAuditService());
    }

    /**
     * <p>
     * Accuracy test for public String getHashAlgorithmName().
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetHashAlgorithmNameAccuracy() throws Exception {
        String hashAlgorithmName = "hash";
        this.instance.setHashAlgorithmName(hashAlgorithmName);
        assertEquals("HashAlgorithmName should match.", hashAlgorithmName, this.instance.getHashAlgorithmName());
    }

    /**
     * <p>
     * Accuracy test for public void setHashAlgorithmName(String hashAlgorithmName).
     * </p>
     * @throws Exception to JUnit
     */
    public void testSetHashAlgorithmNameAccuracy() throws Exception {
        String hashAlgorithmName = "hash";
        this.instance.setHashAlgorithmName(hashAlgorithmName);
        assertEquals("HashAlgorithmName should match.", hashAlgorithmName, this.instance.getHashAlgorithmName());
    }

    /**
     * <p>
     * Accuracy test for public String getUserName().
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetUserNameAccuracy() throws Exception {
        String userName = "user";
        this.instance.setUserName(userName);
        assertEquals("UserName should match.", userName, this.instance.getUserName());
    }

    /**
     * <p>
     * Accuracy test for public void setUserName(String userName).
     * </p>
     * @throws Exception to JUnit
     */
    public void testSetUserNameAccuracy() throws Exception {
        String userName = "user";
        this.instance.setUserName(userName);
        assertEquals("UserName should match.", userName, this.instance.getUserName());
    }

    /**
     * <p>
     * Accuracy test for public String getHashedPassword().
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetHashedPasswordAccuracy() throws Exception {
        String hashedPassword = "hash";
        this.instance.setHashedPassword(hashedPassword);
        assertEquals("HashedPassword should match.", hashedPassword, this.instance.getHashedPassword());
    }

    /**
     * <p>
     * Accuracy test for public void setHashedPassword(String hashedPassword).
     * </p>
     * @throws Exception to JUnit
     */
    public void testSetHashedPasswordAccuracy() throws Exception {
        String hashedPassword = "hash";
        this.instance.setHashedPassword(hashedPassword);
        assertEquals("HashedPassword should match.", hashedPassword, this.instance.getHashedPassword());
    }

    /**
     * <p>
     * Accuracy test for public String getLastInvoiceNumber().
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetLastInvoiceNumberAccuracy() throws Exception {
        String lastInvoiceNumber = "last";
        this.instance.setLastInvoiceNumber(lastInvoiceNumber);
        assertEquals("LastInvoiceNumber should match.", lastInvoiceNumber, this.instance.getLastInvoiceNumber());
    }

    /**
     * <p>
     * Accuracy test for public void setLastInvoiceNumber(String lastInvoiceNumber).
     * </p>
     * @throws Exception to JUnit
     */
    public void testSetLastInvoiceNumberAccuracy() throws Exception {
        String lastInvoiceNumber = "last";
        this.instance.setLastInvoiceNumber(lastInvoiceNumber);
        assertEquals("LastInvoiceNumber should match.", lastInvoiceNumber, this.instance.getLastInvoiceNumber());
    }

    /**
     * <p>
     * Accuracy test for public String getLoggerName().
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetLoggerNameAccuracy() throws Exception {
        String loggerName = "logger";
        this.instance.setLoggerName(loggerName);
        assertEquals("LoggerName should match.", loggerName, this.instance.getLoggerName());
    }

    /**
     * <p>
     * Accuracy test for public void setLoggerName(String loggerName).
     * </p>
     * @throws Exception to JUnit
     */
    public void testSetLoggerNameAccuracy() throws Exception {
        String loggerName = "logger";
        this.instance.setLoggerName(loggerName);
        assertEquals("LoggerName should match.", loggerName, this.instance.getLoggerName());
    }

    /**
     * <p>
     * Assert the given content match the given file.
     * </p>
     * @param errorMessage the error message if it does not match
     * @param expectedFile the expected file
     * @param actualContent the content
     * @throws Exception to JUnit
     */
    private static void assertMatchFile(String errorMessage, String expectedFile, String actualContent)
        throws Exception {
        FileReader reader = new FileReader(expectedFile);
        StringBuilder sb = new StringBuilder();
        try {
            char[] buffer = new char[4096];
            int len = reader.read(buffer);
            while (len > 0) {
                sb.append(buffer, 0, len);
                len = reader.read(buffer);
            }
        } finally {
            reader.close();
        }

        assertEquals(errorMessage, sb.toString(), actualContent);
    }

    /**
     * <p>
     * Assert two long array match.
     * </p>
     * @param message the error message if it does not match
     * @param expected the expected array
     * @param actual the actual array
     */
    private static void assertLongArrayMatch(String message, long[] expected, long[] actual) {
        assertEquals(message, expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(message, expected[i], actual[i]);
        }
    }

    /**
     * <p>
     * Prepare an instance of BillingCostExportDetail.
     * </p>
     * @param id the id to set
     * @param customer the customer to set
     * @param billingAmount the billing amount to set
     * @return a new instance of BillingCostExportDetail populated with the given values
     */
    private static BillingCostExportDetail prepareExportDetail(long id, String customer, float billingAmount) {
        BillingCostExportDetail billingCostExportDetail = new BillingCostExportDetail();
        billingCostExportDetail.setId(id);
        billingCostExportDetail.setCustomer(customer);
        billingCostExportDetail.setBillingAmount(billingAmount);
        return billingCostExportDetail;
    }
}
