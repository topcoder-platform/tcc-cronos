/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.payment.paypal;

import javax.annotation.Resource;

import org.jboss.logging.Logger;

import paypal.payflow.BillTo;
import paypal.payflow.CardTender;
import paypal.payflow.ClientInfo;
import paypal.payflow.Context;
import paypal.payflow.CreditCard;
import paypal.payflow.Currency;
import paypal.payflow.FraudResponse;
import paypal.payflow.Invoice;
import paypal.payflow.PayflowConnectionData;
import paypal.payflow.PayflowConstants;
import paypal.payflow.PayflowUtility;
import paypal.payflow.Response;
import paypal.payflow.SDKProperties;
import paypal.payflow.SaleTransaction;
import paypal.payflow.TransactionResponse;
import paypal.payflow.UserInfo;

import com.topcoder.service.payment.CreditCardPaymentData;
import com.topcoder.service.payment.PaymentData;
import com.topcoder.service.payment.PaymentException;
import com.topcoder.service.payment.PaymentProcessor;
import com.topcoder.service.payment.PaymentResult;

/**
 * This class uses PayFlow payment system to process Paypal payments.
 * 
 * @author Margarita
 * @version 1.0
 * @since BUGR-1239
 */
public class PayflowProPaymentProcessor implements PaymentProcessor {
    /**
     * This variable stores such info as username, password, partner and vendor info.
     */
    private final UserInfo userInfo;

    /**
     * Host address. Use pilot-payflowpro.paypal.com for testing and payflowpro.paypal.com for production.
     */
    @Resource(name = "payFlowHostAddress")
    private String hostAddress;

    /**
     * PayFlow username.
     */
    @Resource(name = "payFlowUser")
    private String user;

    /**
     * PayFlow partner name.
     */
    @Resource(name = "payFlowPartner")
    private String partner;

    /**
     * PayFlow vendor name.
     */
    @Resource(name = "payFlowVendor")
    private String vendor;

    /**
     * PayFlow password.
     */
    @Resource(name = "payFlowPassword")
    private String password;

    /**
     * Logger instance.
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * Default constructor, sets log properties.
     */
    public PayflowProPaymentProcessor() {
        // comment for non-local testing
        user = "shannontc";
        vendor = "shannontc";
        partner = "PayPal";
        password = "tctesting123";
        hostAddress = "pilot-payflowpro.paypal.com"; 
        
        logger.debug("hostAddress=" + hostAddress);
        logger.debug("user=" + user);
        logger.debug("vendor=" + vendor);
        logger.debug("password=" + password);
        logger.debug("user=" + user);

        userInfo = new UserInfo(user, vendor, partner, password);
    }

    /**
     * An implementation method of the <code>PaymentProcessor</code> interface.
     * 
     * @param payment
     *            the payment data that need to be processed.
     * @return a <code>PaymentResult</code> the payment result. In this case transaction id from payflow.
     * @throws PaymentException
     *             on payment failure, it captures Error message and Error code for the failure case.
     */
    public PaymentResult process(PaymentData payment) throws PaymentException {
        CreditCardPaymentData cardPaymentData = (CreditCardPaymentData) payment;

        SDKProperties.setHostAddress(hostAddress);
        SDKProperties.setHostPort(443);
        SDKProperties.setTimeOut(20);

        // Create the Payflow Connection data object with the required connection details.
        // The PAYFLOW_HOST and CERT_PATH are properties defined within SDKProperties.
        PayflowConnectionData connection = new PayflowConnectionData();

        // Create a new Invoice data object with the Amount, Billing Address etc. details.
        Invoice inv = new Invoice();
System.out.println("----amount = "+cardPaymentData.getAmount());
        // Set amount
        Currency amt = new Currency(new Double(cardPaymentData.getAmount()), "USD");
        inv.setAmt(amt);

        // Set the Billing Address details.
        BillTo bill = new BillTo();
        bill.setStreet(cardPaymentData.getAddress());
        bill.setZip(cardPaymentData.getZipCode());
        bill.setFirstName(cardPaymentData.getFirstName());
        bill.setLastName(cardPaymentData.getLastName());
        bill.setCity(cardPaymentData.getCity());
        bill.setBillToCountry(cardPaymentData.getCountry());
        bill.setState(cardPaymentData.getState());
        bill.setEmail(cardPaymentData.getEmail());
        inv.setBillTo(bill);

        // Create a new Payment Device - Credit Card data object.
        // The input parameters are Credit Card No. and Expiry Date for the Credit Card.
        CreditCard cc = new CreditCard(cardPaymentData.getCardNumber(), cardPaymentData.getCardExpiryMonth()
                + cardPaymentData.getCardExpiryYear());
        // Create a new Tender - Card Tender data object.
        CardTender card = new CardTender(cc);

        // Create a new Sale Transaction.
        SaleTransaction trans = new SaleTransaction(userInfo, connection, inv, card, PayflowUtility.getRequestId());

        // Submit the Transaction
        Response resp = trans.submitTransaction();

        // Display the transaction response parameters.
        if (resp != null) {
            // Get the Transaction Response parameters.
            TransactionResponse trxnResponse = resp.getTransactionResponse();

            // Create a new Client Information data object.
            ClientInfo clInfo = new ClientInfo();

            // Set the ClientInfo object of the transaction object.
            trans.setClientInfo(clInfo);

            System.out.println("--" + PayflowUtility.getStatus(resp));

            if (trxnResponse != null) {
                System.out.println("RESULT = " + trxnResponse.getResult());
                System.out.println("PNREF = " + trxnResponse.getPnref());
                System.out.println("RESPMSG = " + trxnResponse.getRespMsg());
                System.out.println("AUTHCODE = " + trxnResponse.getAuthCode());
                System.out.println("AVSADDR = " + trxnResponse.getAvsAddr());
                System.out.println("AVSZIP = " + trxnResponse.getAvsZip());
                System.out.println("IAVS = " + trxnResponse.getIavs());
                System.out.println("CVV2MATCH = " + trxnResponse.getCvv2Match());
                System.out.println("DUPLICATE = " + trxnResponse.getDuplicate());

                if (trxnResponse.getResult() == 0) { // success
                    PaymentResult paymentResult = new PaymentResult();
                    paymentResult.setReferenceNumber(trxnResponse.getPnref());
                    return paymentResult;
                }
            } else {
                throw new PaymentException("No transaction response");
            }

            // Get the Fraud Response parameters.
            FraudResponse fraudResp = resp.getFraudResponse();
            if (fraudResp != null) {
                System.out.println("PREFPSMSG = " + fraudResp.getPreFpsMsg());
                System.out.println("POSTFPSMSG = " + fraudResp.getPostFpsMsg());
            }
            // Get the Transaction Context and check for any contained SDK specific errors (optional code).
            Context transCtx = resp.getContext();
            if (transCtx != null && transCtx.getErrorCount() > 0) {
                System.out.println("\nTransaction Errors = " + transCtx.toString());
            }
            throw new PaymentException(trxnResponse.getRespMsg());
        }
        throw new PaymentException("No response");
    }

    /**
     * Main method. Contains info for testing purposes.
     * 
     * @param args
     * @throws PaymentException
     */
    public static void main(String[] args) throws PaymentException {
        PayflowProPaymentProcessor proc = new PayflowProPaymentProcessor();

        SDKProperties.setLogFileName("payflow_java.log");
        SDKProperties.setLoggingLevel(PayflowConstants.SEVERITY_WARN);
        SDKProperties.setMaxLogFileSize(100000);

        CreditCardPaymentData c = new CreditCardPaymentData();
        c.setCardNumber("5555555555554444"); // use any other to be declined
        c.setAmount("199.00"); // use more than 1000 to be declined
        c.setCardExpiryMonth("08"); // use past date to be declined
        c.setCardExpiryYear("09");
        c.setFirstName("John");
        c.setLastName("McClaine");
        c.setAddress("24285 Elm");
        c.setZipCode("00382");
        System.out.print("reference number=" + proc.process(c).getReferenceNumber());
    }

}
