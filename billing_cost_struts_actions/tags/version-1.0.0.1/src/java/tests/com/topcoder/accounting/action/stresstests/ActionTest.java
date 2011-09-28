/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action.stresstests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.StrutsStatics;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockServletContext;

import com.opensymphony.xwork2.ActionContext;
import com.topcoder.accounting.action.AuditHistoryAction;
import com.topcoder.accounting.action.BillingCostExportDetailsAction;
import com.topcoder.accounting.action.BillingCostExportHistoryAction;
import com.topcoder.accounting.action.BillingCostReportAction;
import com.topcoder.accounting.action.BillingCostReportQuickBooksExportAction;
import com.topcoder.accounting.action.MockBillingCostAuditService;
import com.topcoder.accounting.action.MockBillingCostDataService;
import com.topcoder.accounting.action.converter.DateConverter;
import com.topcoder.accounting.entities.dto.PaymentIdentifier;

/**
 * <p>
 * The base class for stress tests.
 * </p>
 *
 * @author wz12
 * @version 1.0
 */
public class ActionTest {
    /**
     * <p>The testcase count.</p>
     */
    private final int testCount = 100;
    /**
     * <p>
     * The method help to test AuditHistoryAction.
     * </p>
     * @throws Exception
     *        To JUnit.
     */
    @Test
    public void testAuditHistoryAction() throws Exception {
        AuditHistoryAction auditHistoryAction = new AuditHistoryAction();
        MockBillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        billingCostAuditService.setFlag(false);
        auditHistoryAction.setBillingCostAuditService(billingCostAuditService);
        auditHistoryAction.setServletRequest(new MockHttpServletRequest());
        auditHistoryAction.setAction("Post");
        long ans = System.currentTimeMillis();
        for (int i = 0; i < testCount; ++i) {
            assertEquals(AuditHistoryAction.SUCCESS,
                auditHistoryAction.execute());
        }
        ans = System.currentTimeMillis() - ans;
        System.out.println("Calls " + testCount + " times AuditHistoryAction costs " + ans + " ms.");
    }
    /**
     * <p>
     * The method help to test BillingCostExportDetailsAction.
     * </p>
     * @throws Exception
     *        To JUnit.
     */
    @Test
    public void testABillingCostExportDetailsAction() throws Exception {
        MockBillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        BillingCostExportDetailsAction billingCostExportDetailsAction = new BillingCostExportDetailsAction();
        billingCostExportDetailsAction.setBillingCostAuditService(billingCostAuditService);
        billingCostAuditService.setFlag(false);
        billingCostExportDetailsAction.setBillingCostExportId(1);
        billingCostExportDetailsAction.setServletRequest(new MockHttpServletRequest());
        billingCostExportDetailsAction.setAction("Post");
        long ans = System.currentTimeMillis();
        for (int i = 0; i < testCount; ++i) {
            assertEquals(BillingCostExportDetailsAction.SUCCESS,
                billingCostExportDetailsAction.execute());
        }
        ans = System.currentTimeMillis() - ans;
        System.out.println("Calls " + testCount + " times BillingCostExportDetailsAction costs " + ans + " ms.");
    }
    /**
     * <p>
     * The method help to test BillingCostExportHistoryAction.
     * </p>
     * @throws Exception
     *        To JUnit.
     */
    @Test
    public void testBillingCostExportHistoryAction() throws Exception {
        BillingCostExportHistoryAction billingCostExportHistoryAction = new BillingCostExportHistoryAction();
        MockBillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        billingCostAuditService.setFlag(false);
        billingCostExportHistoryAction.setBillingCostAuditService(billingCostAuditService);
        billingCostExportHistoryAction.setServletRequest(new MockHttpServletRequest());
        billingCostExportHistoryAction.setAction("Post");
        long ans = System.currentTimeMillis();
        for (int i = 0; i < testCount; ++i) {
            assertEquals(BillingCostExportHistoryAction.SUCCESS,
                billingCostExportHistoryAction.execute());
        }
        ans = System.currentTimeMillis() - ans;
        System.out.println("Calls " + testCount + " times BillingCostExportHistoryAction costs " + ans + " ms.");
    }
    /**
     * <p>
     * The method help to test BillingCostReportAction and BillingCostReportQuickBooksExportAction.
     * </p>
     * @throws Exception
     *        To JUnit.
     */
    @Test
    public void  testBillingCostReportAction() throws Exception {
        MockBillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        BillingCostReportAction billingCostReportAction = new BillingCostReportAction();

        billingCostAuditService.setFlag(false);
        billingCostReportAction.setBillingCostAuditService(billingCostAuditService);
        billingCostReportAction.setPageNumber(7);
        billingCostReportAction.setBillingCostDataService(new MockBillingCostDataService());

        billingCostReportAction.setServletRequest(new MockHttpServletRequest());
        billingCostReportAction.setAction("Post");

        long ans = System.currentTimeMillis();
        for (int i = 0; i < testCount; ++i) {
            assertEquals(BillingCostReportAction.SUCCESS,
                billingCostReportAction.execute());
        }
        ans = System.currentTimeMillis() - ans;
        System.out.println("Calls " + testCount + " times BillingCostReportAction costs " + ans + " ms.");

        BillingCostReportQuickBooksExportAction billingCostReportQuickBooksExportAction
            = new BillingCostReportQuickBooksExportAction();
        billingCostReportQuickBooksExportAction.setBillingCostAuditService(billingCostAuditService);
        billingCostReportQuickBooksExportAction.setPaymentAreaId(7);
        billingCostReportQuickBooksExportAction.setPaymentIds(new ArrayList<PaymentIdentifier>());
        billingCostReportQuickBooksExportAction.setBillingCostDataService(new MockBillingCostDataService());
        billingCostReportQuickBooksExportAction.setServletRequest(new MockHttpServletRequest());
        billingCostReportQuickBooksExportAction.setAction("Post");

        ans = System.currentTimeMillis();
        for (int i = 0; i < testCount; ++i) {
            assertEquals(BillingCostReportQuickBooksExportAction.SUCCESS,
                billingCostReportQuickBooksExportAction.execute());
        }
        ans = System.currentTimeMillis() - ans;
        System.out.println("Calls " + testCount
            + " times BillingCostReportQuickBooksExportAction costs " + ans + " ms.");
    }
    /**
     * <p>
     * The method help to test DateConverter.
     * </p>
     * @throws Exception
     *        To JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testDateConverter() throws Exception {
        DateConverter dateConverter = new DateConverter();
        Map<String, Object> context = new HashMap<String, Object>();
        MockServletContext servletContext = new MockServletContext();
        servletContext.addInitParameter("datePattern", "yyyy.MM.dd");
        ActionContext actionContext = new ActionContext(new HashMap());
        actionContext.put(StrutsStatics.SERVLET_CONTEXT, servletContext);
        ActionContext.setContext(actionContext);
        context.put(StrutsStatics.SERVLET_CONTEXT, servletContext);
        Date date = new Date(0);

        String[] value = new String[100];
        value[0] = "1970.01.01";

        long ans = System.currentTimeMillis();
        for (int i = 0; i < testCount; ++i) {
            assertEquals("1970.01.01", dateConverter
                .convertValue(context, date, String.class));

            Date dates = (Date) dateConverter
                .convertValue(context, value, Date.class);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dates);
            assertEquals("must be '1970'", 1970, calendar.get(Calendar.YEAR));
            assertEquals("must be '0'", 0, calendar.get(Calendar.MONTH));
            assertEquals("must be '1'", 1, calendar.get(Calendar.DATE));

            assertNull(dateConverter.convertValue(context, new Date(0), Integer.class));
        }
        ans = System.currentTimeMillis() - ans;
        System.out.println("Calls " + 3 * testCount + " times DateConverter costs " + ans + " ms.");
    }
}
