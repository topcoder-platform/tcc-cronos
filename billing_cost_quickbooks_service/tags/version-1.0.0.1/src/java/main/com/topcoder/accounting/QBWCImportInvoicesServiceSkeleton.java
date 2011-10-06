/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting;

import java.io.ByteArrayOutputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ServiceContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.intuit.developer.ArrayOfString;
import com.intuit.developer.Authenticate;
import com.intuit.developer.AuthenticateResponse;
import com.intuit.developer.ClientVersion;
import com.intuit.developer.ClientVersionResponse;
import com.intuit.developer.CloseConnection;
import com.intuit.developer.CloseConnectionResponse;
import com.intuit.developer.ConnectionError;
import com.intuit.developer.ConnectionErrorResponse;
import com.intuit.developer.GetLastError;
import com.intuit.developer.GetLastErrorResponse;
import com.intuit.developer.QBWebConnectorSvcSkeletonInterface;
import com.intuit.developer.ReceiveResponseXML;
import com.intuit.developer.ReceiveResponseXMLResponse;
import com.intuit.developer.SendRequestXML;
import com.intuit.developer.SendRequestXMLResponse;
import com.intuit.developer.ServerVersion;
import com.intuit.developer.ServerVersionResponse;
import com.topcoder.accounting.entities.dao.AccountingAuditRecord;
import com.topcoder.accounting.entities.dao.BillingCostExportDetail;
import com.topcoder.accounting.entities.dto.PagedResult;
import com.topcoder.accounting.entities.dto.QuickBooksImportUpdate;
import com.topcoder.accounting.service.BillingCostAuditService;
import com.topcoder.accounting.service.BillingCostServiceException;
import com.topcoder.util.algorithm.hash.HashAlgorithmManager;
import com.topcoder.util.algorithm.hash.algorithm.HashAlgorithm;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * QBWCImportInvoicesServiceSkeleton represents a web service used to authenticate user against the configured user name
 * and hashed password, generate the invoice number and invoice information and update the billing cost details and the
 * invoice number map in the persistence. The other methods are left the same as the proof-assembly.
 * </p>
 * <p>
 * It will also log the errors and events(see the CS for detail).
 * </p>
 * <p>
 * <b> Configuration: </b> All the fields are injected, please see the class diagram for detail.
 * </p>
 * <p>
 * <b> Usage: </b>
 *
 * <pre>
 * // create web service stub
 * QBWebConnectorSvcStub stub = new QBWebConnectorSvcStub(loadEndPointUrl());
 *
 * // authenticate
 * Authenticate authenticate = new Authenticate();
 * AuthenticateResponse authenticateResponse = stub.authenticate(authenticate);
 *
 * // send request xml
 * SendRequestXMLResponse sendRequestXMLResponse = stub.sendRequestXML(new SendRequestXML());
 * String response = sendRequestXMLResponse.getSendRequestXMLResult();
 *
 * // receive response xml
 * ReceiveResponseXMLResponse receiveResponseXMLResponse = stub.receiveResponseXML(new ReceiveResponseXML());
 *
 * </pre>
 *
 * </p>
 * <p>
 * <b> Thread Safety: </b> QBWCImportInvoicesServiceSkeleton is mutable and not thread safe. In the apache AXIS 2
 * framework, it acts a state-full web service, and the framework will guarantee that it��s used in the thread safe
 * model.
 * </p>
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class QBWCImportInvoicesServiceSkeleton implements QBWebConnectorSvcSkeletonInterface {
    /**
     * <p>
     * Constant string represents the name of node Rate.
     * </p>
     */
    private static final String RATE_NODE_NAME = "Rate";
    /**
     * <p>
     * Constant string represents the name of node Quantity.
     * </p>
     */
    private static final String QUANTITY_NODE_NAME = "Quantity";
    /**
     * <p>
     * Constant string represents the name of node InvoiceLineAdd.
     * </p>
     */
    private static final String INVOICE_LINE_ADD_NODE_NAME = "InvoiceLineAdd";
    /**
     * <p>
     * Constant string represents the name of node RefNumber.
     * </p>
     */
    private static final String REF_NUMBER_NODE_NAME = "RefNumber";
    /**
     * <p>
     * Constant string represents the name of node InvoiceAdd.
     * </p>
     */
    private static final String INVOICE_ADD_NODE_NAME = "InvoiceAdd";
    /**
     * <p>
     * Constant string represents the value of attribute onError.
     * </p>
     */
    private static final String ON_ERROR_ATTRIBUTE_VALUE = "continueOnError";
    /**
     * <p>
     * Constant string represents the name of attribute onError.
     * </p>
     */
    private static final String ON_ERROR_ATTRIBUTE_NAME = "onError";
    /**
     * <p>
     * Constant string represents the name of node QBXMLMsgsRq.
     * </p>
     */
    private static final String QBXML_MSGS_RQ_NODE_NAME = "QBXMLMsgsRq";
    /**
     * <p>
     * Constant string represents the name of node QBXML.
     * </p>
     */
    private static final String QBXML_NODE_NAME = "QBXML";
    /**
     * <p>
     * Constant string represents the key of error message parameter in axis service.
     * </p>
     */
    private static final String ERROR_MESSAGE_PARAMETER_KEY = "errorMsg";
    /**
     * <p>
     * Constant string represents the key of quick books import updates in service context.
     * </p>
     */
    private static final String QUICK_BOOKS_IMPORT_UPDATES_PROPERTY_KEY = "QuickBooksImportUpdates";
    /**
     * <p>
     * Constant string represents the name of method {@link #connectionError(ConnectionError)}.
     * </p>
     */
    private static final String CONNECTION_ERROR_METHOD_NAME = "connectionError";
    /**
     * <p>
     * Constant string represents the name of method {@link #closeConnection(CloseConnection)}.
     * </p>
     */
    private static final String CLOSE_CONNECTION_METHOD_NAME = "closeConnection";
    /**
     * <p>
     * Constant string represents the name of method {@link #getLastError(GetLastError)}.
     * </p>
     */
    private static final String GET_LAST_ERROR_METHOD_NAME = "getLastError";
    /**
     * <p>
     * Constant string represents the name of method {@link #serverVersion(ServerVersion)}.
     * </p>
     */
    private static final String SERVER_VERSION_METHOD_NAME = "serverVersion";
    /**
     * <p>
     * Constant string represents the name of method {@link #clientVersion(ClientVersion)}.
     * </p>
     */
    private static final String CLIENT_VERSION_METHOD_NAME = "clientVersion";
    /**
     * <p>
     * Constant string represents the name of method {@link #destroy(ServiceContext)}.
     * </p>
     */
    private static final String DESTROY_METHOD_NAME = "destroy";
    /**
     * <p>
     * Constant string represents the name of method {@link #init(ServiceContext)}.
     * </p>
     */
    private static final String INIT_METHOD_NAME = "init";
    /**
     * <p>
     * Constant string represents the name of method {@link #receiveResponseXML(ReceiveResponseXML)}.
     * </p>
     */
    private static final String RECEIVE_RESPONSE_XML_METHOD_NAME = "receiveResponseXML";
    /**
     * <p>
     * Constant string represents the name of method {@link #sendRequestXML(SendRequestXML)}.
     * </p>
     */
    private static final String SEND_REQUEST_XML_METHOD_NAME = "sendRequestXML";
    /**
     * <p>
     * Constant string represents the name of method {@link #authenticate(Authenticate)}.
     * </p>
     */
    private static final String AUTHENTICATE_METHOD_NAME = "authenticate";
    /**
     * <p>
     * Constant string represents the name of this class.
     * </p>
     */
    private static final String CLASS_NAME = QBWCImportInvoicesServiceSkeleton.class.getName();
    /**
     * <p>
     * Constant string represents the invoice add request xml title.
     * </p>
     */
    private static final String INVOICE_ADD_REQUEST_XML =
            "<?xml version=\"1.0\" encoding=\"utf-8\"?><?qbxml version=\"2.0\" ?>";
    /**
     * <p>
     * The service context. It uses to store the QuickBooksImportUpdate in the sendRequestXML method, and the
     * reciveResponseXML method will consume the updates. It's also used in the getLastError method. It's injected. Not
     * null after set.
     * </p>
     */
    private ServiceContext serviceContext;
    /**
     * <p>
     * PageSize is used to get the billing cost export details from the service. It's injected. Not negative.
     * </p>
     */
    private int pageSize;
    /**
     * <p>
     * PageNumber is used to get the billing cost export details from the service. It's injected. Not negative.
     * </p>
     */
    private int pageNumber;
    /**
     * <p>
     * BillingCostAuditService is used to get/update the billing cost information. It's injected. Not null after set.
     * </p>
     */
    private BillingCostAuditService billingCostAuditService;
    /**
     * <p>
     * It's used to get the hash algorithm name from the hash algorithm manager. It's injected. Not null and not empty
     * after set.
     * </p>
     */
    private String hashAlgorithmName;
    /**
     * UserName is used to represent configured user name. It's injected. Not null and not empty after set.
     */
    private String userName;
    /**
     * HashedPassword is used to represent the configured hashed password. It's injected. Not null and not empty after
     * set.
     */
    private String hashedPassword;
    /**
     * LastInvoiceNumber is used to represent the configured last invoice number. It's injected. Not null and not empty
     * after set.
     */
    private String lastInvoiceNumber;
    /**
     * LoggerName is used to represent the logger name to get the logger. Please note that the class does not hold the
     * logger instance, but by LoggerManager.getLogger(loggerName) to get the logger to log the errors.Usage. It's
     * injected. Not null and not empty after set.
     */
    private String loggerName;

    /**
     * <p>
     * Create the instance. Empty constructor.
     * </p>
     */
    public QBWCImportInvoicesServiceSkeleton() {
    }

    /**
     * <p>
     * Process authenticate callback. It will compare the username and password to the configured ones.
     * </p>
     * @param authenticate the authenticate instance
     * @return AuthenticateResponse with new token string
     */
    public AuthenticateResponse authenticate(Authenticate authenticate) {
        logMethodEntranceAndAudit(
                AUTHENTICATE_METHOD_NAME,
                "authenticate",
                new String[] {"strUserName", "strPassword"},
                authenticate == null ? null : new String[] {authenticate.getStrUserName(),
                        authenticate.getStrPassword()});

        // create response
        AuthenticateResponse res = new AuthenticateResponse();
        try {
            String strUserName = authenticate.getStrUserName();
            String strPassword = authenticate.getStrPassword();

            // generate an random UUID to the response strings
            ArrayOfString strs = new ArrayOfString();
            strs.addString(UUID.randomUUID().toString());

            // if password is null, then authentication failed
            if (strPassword == null) {
                strs.addString(Boolean.toString(false));
            } else {
                // hash the password
                HashAlgorithmManager manager = HashAlgorithmManager.getInstance();
                HashAlgorithm algorithm = manager.getAlgorithm(this.hashAlgorithmName);
                // if we cannot find the hash algorithm, store error message and return
                if (algorithm == null) {
                    error("There is no hash algorithm '" + this.hashAlgorithmName + "'.", null);
                    return res;
                }

                String hashedInputPassword = algorithm.hashToString(strPassword);

                // compare strUserName and hashed password to the configured userName and hashedPassword
                // if they are equal, add "true", otherwise "false", if any exception is caught, set it to false.
                strs.addString(Boolean.toString(this.userName.equals(strUserName)
                        && this.hashedPassword.equals(hashedInputPassword)));
            }

            res.setAuthenticateResult(strs);
        } catch (Exception e) {
            error(e);
        } finally {
            logMethodExit(AUTHENTICATE_METHOD_NAME, "AuthenticateResponse", "authenticateResult",
                    res.getAuthenticateResult() == null
                            ? null : Arrays.asList(res.getAuthenticateResult().getString()));
        }

        return res;
    }

    /**
     * <p>
     * Process sendRequestXML callback. It will get the billing cost from the service, generate the invoice number for
     * each custom in the cost detail, and construct the xml response
     * </p>
     * @param sendRequestXML the send request xml instance
     * @return SendRequestXMLResponse with the request xml to import invoices, if any exception is caught, the empty
     *         string will be set the request response, and error message should be saved in serviceContext.
     */
    public SendRequestXMLResponse sendRequestXML(SendRequestXML sendRequestXML) {
        logMethodEntranceAndAudit(
                SEND_REQUEST_XML_METHOD_NAME,
                "sendRequestXML",
                new String[] {"qbXMLCountry", "qbXMLMajorVers", "qbXMLMinorVers", "strCompanyFileName",
                    "strHCPResponse", "ticket"},
                sendRequestXML == null ? null : new Object[] {sendRequestXML.getQbXMLCountry(),
                        sendRequestXML.getQbXMLMajorVers(), sendRequestXML.getQbXMLMinorVers(),
                        sendRequestXML.getStrCompanyFileName(), sendRequestXML.getStrHCPResponse(),
                        sendRequestXML.getTicket()});

        SendRequestXMLResponse res = new SendRequestXMLResponse();
        res.setSendRequestXMLResult("");

        try {
            PagedResult<BillingCostExportDetail> result =
                    this.billingCostAuditService.getBillingCostExportDetails(false, this.pageNumber, this.pageSize);
            List<BillingCostExportDetail> records = result.getRecords();

            // create xml document
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element rootElement = append(document, document, QBXML_NODE_NAME);
            Element requestElement = append(document, rootElement, QBXML_MSGS_RQ_NODE_NAME);
            requestElement.setAttribute(ON_ERROR_ATTRIBUTE_NAME, ON_ERROR_ATTRIBUTE_VALUE);

            // we will get a list of records, and the invoice number generated above.
            // we should store them in the service context
            Map<String, long[]> updates = new HashMap<String, long[]>();

            // build InvoiceAdd nodes according to the records, please refer to the doc/request.xml for the xml
            // structure
            buildInvoiceAddNodes(records, updates, document, requestElement);

            // get the xml string
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            try {
                StreamResult streamResult = new StreamResult(stream);
                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
                transformer.transform(new DOMSource(document), streamResult);
            } finally {
                stream.close();
            }

            // set the request xml, if any exception is caught, set it to empty string.
            res.setSendRequestXMLResult(INVOICE_ADD_REQUEST_XML + stream.toString());

            // put the updates map in the service context
            this.serviceContext.setProperty(QUICK_BOOKS_IMPORT_UPDATES_PROPERTY_KEY, updates);
        } catch (Exception e) {
            error(e);
        }

        logMethodExit(SEND_REQUEST_XML_METHOD_NAME, "SendRequestXMLResponse", "sendRequestXMLResult",
                res.getSendRequestXMLResult());
        return res;
    }

    /**
     * <p>
     * Process receiveResponseXML callback. It will update the invoice number for each custom.
     * </p>
     * @param receiveResponseXML the receive response xml instance
     * @return ReceiveResponseXMLResponse with result value of the size of the update billing list, if any exception
     *         caught, the result will be of value 0 and and error message should be saved in serviceContext.
     */
    @SuppressWarnings("unchecked")
    public ReceiveResponseXMLResponse receiveResponseXML(ReceiveResponseXML receiveResponseXML) {
        logMethodEntranceAndAudit(
                RECEIVE_RESPONSE_XML_METHOD_NAME,
                "receiveResponseXML",
                new String[] {"hresult", "message", "response", "ticket"},
                receiveResponseXML == null ? null : new Object[] {receiveResponseXML.getHresult(),
                        receiveResponseXML.getMessage(), receiveResponseXML.getResponse(),
                        receiveResponseXML.getTicket()});

        // we should parse and check receveRsponseXML.getResponse() that there is no error node in the doc
        // but at current phase, the DTD file for the response is not given, so we just skip the error node detecting
        // and perform the updates anyway.

        ReceiveResponseXMLResponse res = new ReceiveResponseXMLResponse();
        try {
            Map<String, long[]> map =
                    (Map<String, long[]>) serviceContext.getProperty(QUICK_BOOKS_IMPORT_UPDATES_PROPERTY_KEY);
            // if there is no QuickBooksImportUpdate in service context, we simply return value 0 as result
            if (map == null) {
                return res;
            }

            List<QuickBooksImportUpdate> updates = new ArrayList<QuickBooksImportUpdate>();
            for (Entry<String, long[]> entry : map.entrySet()) {
                QuickBooksImportUpdate update = new QuickBooksImportUpdate();
                update.setInvoiceNumber(entry.getKey()); // the key in the map
                update.setBillingCostExportDetailIds(entry.getValue()); // the corresponding value in the map
                updates.add(update);
            }

            // then update the list
            this.billingCostAuditService.updateBillingCostExportDetails(updates);

            // set the response
            res.setReceiveResponseXMLResult(updates.size());
        } catch (Exception e) {
            error(e);
        } finally {
            logMethodExit(RECEIVE_RESPONSE_XML_METHOD_NAME, "ReceiveResponseXMLResponse", "receiveResponseXMLResult",
                    res.getReceiveResponseXMLResult());
        }

        return res;
    }

    /**
     * <p>
     * Sets the services context.
     * </p>
     * @param serviceContext the service context
     */
    public void init(ServiceContext serviceContext) {
        logMethodEntranceAndAudit(INIT_METHOD_NAME, "serviceContext", new String[] {"Property#QuickBooksImportUpdate",
            "axisService"},
                serviceContext == null ? null : new Object[] {serviceContext.getProperty("QuickBooksImportUpdate"),
                        serviceContext.getAxisService()});

        this.serviceContext = serviceContext;

        logMethodExit(INIT_METHOD_NAME, null, null, null);
    }

    /**
     * <p>
     * clear the services context.
     * </p>
     * @param serviceContext the service context
     */
    public void destroy(ServiceContext serviceContext) {
        logMethodEntranceAndAudit(DESTROY_METHOD_NAME, "serviceContext", new String[] {
            "Property#QuickBooksImportUpdate", "axisService"}, serviceContext == null ? null : new Object[] {
                serviceContext.getProperty("QuickBooksImportUpdate"), serviceContext.getAxisService()});

        serviceContext.removeProperty(QUICK_BOOKS_IMPORT_UPDATES_PROPERTY_KEY);

        logMethodExit(DESTROY_METHOD_NAME, null, null, null);
    }

    /**
     * <p>
     * Process clientVersion callback.
     * </p>
     * @param clientVersion the client version
     * @return ClientVersionResponse with the empty string result
     */
    public ClientVersionResponse clientVersion(ClientVersion clientVersion) {
        logMethodEntranceAndAudit(CLIENT_VERSION_METHOD_NAME, "clientVersion", new String[] {"strVersion"},
                clientVersion == null ? null : new String[] {clientVersion.getStrVersion()});

        ClientVersionResponse res = new ClientVersionResponse();
        res.setClientVersionResult("");

        logMethodExit(CLIENT_VERSION_METHOD_NAME, "ClientVersionResponse", "clientVersionResult",
                res.getClientVersionResult());
        return res;
    }

    /**
     * <p>
     * Process serverVersion callback.
     * </p>
     * @param serverVersion the server version
     * @return ServerVersionResponse with the empty string result
     */
    public ServerVersionResponse serverVersion(ServerVersion serverVersion) {
        logMethodEntranceAndAudit(SERVER_VERSION_METHOD_NAME, "serverVersion", new String[] {"strVersion"},
                serverVersion == null ? null : new String[] {serverVersion.getStrVersion()});

        ServerVersionResponse res = new ServerVersionResponse();
        res.setServerVersionResult("");

        logMethodExit(SERVER_VERSION_METHOD_NAME, "ServerVersionResponse", "serverVersionResult",
                res.getServerVersionResult());
        return res;
    }

    /**
     * <p>
     * Process getLastError callback.
     * </p>
     * @param getLastError the last error instance
     * @return GetLastErrorResponse with mock description of last error
     */
    public GetLastErrorResponse getLastError(GetLastError getLastError) {
        logMethodEntranceAndAudit(GET_LAST_ERROR_METHOD_NAME, "getLastError", new String[] {"ticket"},
                getLastError == null ? null : new String[] {getLastError.getTicket()});

        GetLastErrorResponse res = new GetLastErrorResponse();
        try {
            res.setGetLastErrorResult((String) serviceContext.getAxisService().getParameterValue(
                    ERROR_MESSAGE_PARAMETER_KEY));
        } catch (ClassCastException e) {
            // ignore
        } catch (NullPointerException e) {
            // ignore
        }

        logMethodExit(GET_LAST_ERROR_METHOD_NAME, "GetLastErrorResponse", "getLastErrorResult",
                res.getGetLastErrorResult());
        return res;
    }

    /**
     * <p>
     * Process closeConnection callback.
     * </p>
     * @param closeConnection the close connection instance
     * @return CloseConnectionResponse with result string of "ok"
     */
    public CloseConnectionResponse closeConnection(CloseConnection closeConnection) {
        logMethodEntranceAndAudit(CLOSE_CONNECTION_METHOD_NAME, "closeConnection",
                new String[] {CLOSE_CONNECTION_METHOD_NAME}, closeConnection == null ? null
                        : new String[] {closeConnection.getTicket()});

        CloseConnectionResponse res = new CloseConnectionResponse();
        res.setCloseConnectionResult("ok");

        logMethodExit(CLOSE_CONNECTION_METHOD_NAME, "CloseConnectionResponse", "closeConnectionResult",
                res.getCloseConnectionResult());
        return res;
    }

    /**
     * <p>
     * Process connectionError callback.
     * </p>
     * @param connectionError the connection error instance
     * @return ConnectionErrorResponse with result string of "done"
     */
    public ConnectionErrorResponse connectionError(ConnectionError connectionError) {
        logMethodEntranceAndAudit(
                CONNECTION_ERROR_METHOD_NAME,
                "connectionError",
                new String[] {"hresult", "message", "ticket"},
                connectionError == null ? null : new String[] {connectionError.getHresult(),
                        connectionError.getMessage(), connectionError.getTicket()});

        ConnectionErrorResponse res = new ConnectionErrorResponse();
        res.setConnectionErrorResult("done");

        logMethodExit(CONNECTION_ERROR_METHOD_NAME, "ConnectionErrorResponse", "connectionErrorResult",
                res.getConnectionErrorResult());
        return res;
    }

    /**
     * <p>
     * Get service context.
     * </p>
     * @return the service context
     */
    public ServiceContext getServiceContext() {
        return this.serviceContext;
    }

    /**
     * <p>
     * Set service context.
     * </p>
     * @param serviceContext the service context to set to the QBWCImportInvoicesServiceSkeleton
     */
    public void setServiceContext(ServiceContext serviceContext) {
        this.serviceContext = serviceContext;
    }

    /**
     * <p>
     * Get page size.
     * </p>
     * @return the page size
     */
    public int getPageSize() {
        return this.pageSize;
    }

    /**
     * <p>
     * Set page size.
     * </p>
     * @param pageSize the page size to set to the QBWCImportInvoicesServiceSkeleton
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * <p>
     * Get page number.
     * </p>
     * @return the page number
     */
    public int getPageNumber() {
        return this.pageNumber;
    }

    /**
     * <p>
     * Set page number.
     * </p>
     * @param pageNumber the page number to set to the QBWCImportInvoicesServiceSkeleton
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * <p>
     * Get billing cost audit service.
     * </p>
     * @return the billing cost audit service
     */
    public BillingCostAuditService getBillingCostAuditService() {
        return this.billingCostAuditService;
    }

    /**
     * <p>
     * Set billing cost audit service.
     * </p>
     * @param billingCostAuditService the billing cost audit service to set to the QBWCImportInvoicesServiceSkeleton
     */
    public void setBillingCostAuditService(BillingCostAuditService billingCostAuditService) {
        this.billingCostAuditService = billingCostAuditService;
    }

    /**
     * <p>
     * Get hash algorithm name.
     * </p>
     * @return the hash algorithm name
     */
    public String getHashAlgorithmName() {
        return this.hashAlgorithmName;
    }

    /**
     * <p>
     * Set hash algorithm name.
     * </p>
     * @param hashAlgorithmName the hash algorithm name to set to the QBWCImportInvoicesServiceSkeleton
     */
    public void setHashAlgorithmName(String hashAlgorithmName) {
        this.hashAlgorithmName = hashAlgorithmName;
    }

    /**
     * <p>
     * Get user name.
     * </p>
     * @return the user name
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * <p>
     * Set user name.
     * </p>
     * @param userName the user name to set to the QBWCImportInvoicesServiceSkeleton
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * <p>
     * Get hashed password.
     * </p>
     * @return the hashed password
     */
    public String getHashedPassword() {
        return this.hashedPassword;
    }

    /**
     * <p>
     * Set hashed password.
     * </p>
     * @param hashedPassword the hashed password to set to the QBWCImportInvoicesServiceSkeleton
     */
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    /**
     * <p>
     * Get last invoice number.
     * </p>
     * @return the last invoice number
     */
    public String getLastInvoiceNumber() {
        return this.lastInvoiceNumber;
    }

    /**
     * <p>
     * Set last invoice number.
     * </p>
     * @param lastInvoiceNumber the last invoice number to set to the QBWCImportInvoicesServiceSkeleton
     */
    public void setLastInvoiceNumber(String lastInvoiceNumber) {
        this.lastInvoiceNumber = lastInvoiceNumber;
    }

    /**
     * <p>
     * Get logger name.
     * </p>
     * @return the logger name
     */
    public String getLoggerName() {
        return this.loggerName;
    }

    /**
     * <p>
     * Set logger name.
     * </p>
     * @param loggerName the logger name to set to the QBWCImportInvoicesServiceSkeleton
     */
    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    /**
     * <p>
     * Build InvoiceAdd nodes in the XML document according to the export detail records.
     * </p>
     * @param records the export detail records
     * @param updates the update map
     * @param document the XML document
     * @param requestElement the parent node of all InvoiceAdd nodes
     * @throws BillingCostServiceException if error occurred while getting latest invoice number from audit service
     */
    private void buildInvoiceAddNodes(List<BillingCostExportDetail> records, Map<String, long[]> updates,
            Document document, Element requestElement) throws BillingCostServiceException {
        BillingCostExportDetail lastRecord = null;
        Element invoiceAdd = null;
        List<BillingCostExportDetail> updateRecords = new ArrayList<BillingCostExportDetail>();
        // get lastInvoiceNumber
        String invoiceNumber = this.billingCostAuditService.getLatestInvoiceNumber();
        // use the configured one if null returned.
        if (invoiceNumber == null) {
            invoiceNumber = this.lastInvoiceNumber;
        }

        // for each record in the records(sorted by custom)
        for (BillingCostExportDetail record : records) {
            // a new invoiceAdd node should be created for each new customer
            // if this record#customer is different than the last one, then this is a new customer
            if (lastRecord == null ? true : !lastRecord.getCustomer().equals(record.getCustomer())) {
                // put ids of updateRecords in updates map for the last customer
                if (!updateRecords.isEmpty()) {
                    putIntoUpdatesMap(updates, invoiceNumber, updateRecords);
                    updateRecords = new ArrayList<BillingCostExportDetail>();
                }

                // create new invoiceAdd node
                invoiceAdd = append(document, requestElement, INVOICE_ADD_NODE_NAME);

                // create RefNumber node
                Element refNumber = append(document, invoiceAdd, REF_NUMBER_NODE_NAME);

                // parse the number part after the character '-'
                // then for each customer, increase the number by 1, and set the invoice number to the <RefNumber>
                String[] invoiceNumberTokens = invoiceNumber.split("-");
                invoiceNumber =
                        invoiceNumberTokens[0] + "-" + Integer.toString(Integer.parseInt(invoiceNumberTokens[1]) + 1);
                refNumber.setTextContent(invoiceNumber);
            }

            // add the record to updateRecords
            updateRecords.add(record);

            // InvoiceLineAdd node should be constructed for each associated record for this customer.
            Element invoiceLineAdd = append(document, invoiceAdd, INVOICE_LINE_ADD_NODE_NAME);

            // add Quantity node, the value is always 1
            Element quantity = append(document, invoiceLineAdd, QUANTITY_NODE_NAME);
            quantity.setTextContent("1");

            // add Rate node
            Element rate = append(document, invoiceLineAdd, RATE_NODE_NAME);
            rate.setTextContent(Float.toString(record.getBillingAmount()));

            // refresh last record
            lastRecord = record;
        }

        // put the last customer into updates map if necessary
        if (!updateRecords.isEmpty()) {
            putIntoUpdatesMap(updates, invoiceNumber, updateRecords);
        }
    }

    /**
     * <p>
     * Put the ids of the given update records into update map.
     * </p>
     * @param updates the update map
     * @param invoiceNumber the invoice number of the update records
     * @param updateRecords the update records
     */
    private static void putIntoUpdatesMap(Map<String, long[]> updates, String invoiceNumber,
            List<BillingCostExportDetail> updateRecords) {
        long[] ids = new long[updateRecords.size()];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = updateRecords.get(i).getId();
        }
        updates.put(invoiceNumber, ids);
    }

    /**
     * <p>
     * Create a new node and append it to a parent node.
     * </p>
     * @param document the XML document
     * @param parent the parent node
     * @param newNodeName the name of the new node
     * @return the created new node
     */
    private static Element append(Document document, Node parent, String newNodeName) {
        Element element = document.createElement(newNodeName);
        parent.appendChild(element);

        return element;
    }

    /**
     * <p>
     * Audit method execution via billing cost audit service.
     * </p>
     * @param methodName the name of the method to be audited
     */
    private void audit(String methodName) {
        AccountingAuditRecord record = new AccountingAuditRecord();
        record.setAction(methodName);
        record.setTimestamp(new Date());
        record.setUserName("QuickBooks Web Service");

        try {
            this.billingCostAuditService.auditAccountingAction(record);
        } catch (BillingCostServiceException e) {
            // log and ignore
            LogManager.getLog(this.loggerName).log(Level.ERROR, e);
        }
    }

    /**
     * <p>
     * Log method entrance and audit method execution.
     * </p>
     * @param methodName the name of the method
     * @param paramName the name of the input parameter
     * @param paramFieldNames the names of the fields of the input parameter
     * @param paramFieldValues the values of the fields of the input parameter
     */
    private void logMethodEntranceAndAudit(String methodName, String paramName, String[] paramFieldNames,
            Object[] paramFieldValues) {
        StringBuilder sb = new StringBuilder();
        sb.append("Enter method ").append(CLASS_NAME).append(".").append(methodName).append(". ");
        sb.append("Input parameters: ").append(paramName).append(" = ");
        if (paramFieldValues == null) {
            sb.append("null");
        } else {
            sb.append("[");
            for (int i = 0; i < paramFieldNames.length; i++) {
                sb.append(paramFieldNames[i]).append(" = ").append(paramFieldValues[i]).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length() - 1);
            sb.append("]");
        }

        LogManager.getLog(this.loggerName).log(Level.DEBUG, sb.toString());

        audit(methodName);
    }

    /**
     * <p>
     * Log method exit.
     * </p>
     * @param methodName the name of the method
     * @param returnObjectName the name of the return object
     * @param returnFieldsName the field name of the return object
     * @param returnFieldValue the field value of the return object
     */
    private void logMethodExit(String methodName, String returnObjectName, String returnFieldsName,
            Object returnFieldValue) {
        StringBuilder sb = new StringBuilder();
        sb.append("Exit method ").append(CLASS_NAME).append(".").append(methodName).append(". ");
        if (returnObjectName != null) {
            sb.append("Return ").append(returnObjectName).append(" = [").append(returnFieldsName).append(" = ")
                    .append(returnFieldValue).append("]");
        } else {
            sb.append("No return.");
        }

        LogManager.getLog(this.loggerName).log(Level.DEBUG, sb.toString());
    }

    /**
     * <p>
     * Log the given exception and store the error message.
     * </p>
     * @param exception the exception
     */
    private void error(Exception exception) {
        error(exception.getMessage(), exception);
    }

    /**
     * <p>
     * Log the given exception, if the exception is null, the message will be logged. Also the message will be stored in
     * service context.
     * </p>
     * @param message the error message
     * @param exception the exception to be logged
     */
    private void error(String message, Exception exception) {
        if (exception != null) {
            LogManager.getLog(this.loggerName).log(Level.ERROR, exception);
        } else {
            LogManager.getLog(this.loggerName).log(Level.ERROR, message);
        }

        try {
            serviceContext.getAxisService().addParameter(ERROR_MESSAGE_PARAMETER_KEY, message);
        } catch (AxisFault e) {
            e.printStackTrace();
        }
    }
}
