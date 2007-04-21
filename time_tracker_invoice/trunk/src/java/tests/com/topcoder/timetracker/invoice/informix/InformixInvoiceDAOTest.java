/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.informix;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.timetracker.invoice.InvoiceConfigurationException;
import com.topcoder.timetracker.invoice.InvoiceSearchDepth;
import com.topcoder.timetracker.invoice.InvoiceStatus;
import com.topcoder.timetracker.invoice.InvoiceUnrecognizedEntityException;
import com.topcoder.timetracker.invoice.informix.filterfactory.InformixInvoiceFilterFactory;
import com.topcoder.timetracker.invoice.servicedetail.InvoiceServiceDetail;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for <code>InformixInvoiceDAO</code>.
 *
 * @author enefem21
 * @version 1.0
 */
public class InformixInvoiceDAOTest extends TestCase {

    /** Unit under test. */
    private InformixInvoiceDAO informixInvoiceDAO;

    /**
     * <p>
     * Return the suite for this unit test.
     * </p>
     *
     * @return the suite
     */
    public static Test suite() {
        return new TestSuite(InformixInvoiceDAOTest.class);
    }

    /**
     * Sets the unit test up.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        TestHelper.loadConfiguration("config-InformixInvoiceDAO.xml");

        informixInvoiceDAO = new InformixInvoiceDAO();

        TestHelper.clearNamespaces();
    }

    /**
     * Tears the unit test down.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearNamespaces();

        super.tearDown();
    }

    /**
     * Test constructor for failure. Condition: namespace is null. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixInvoiceDAOWithNamespaceNull() throws Exception {
        try {
            new InformixInvoiceDAO(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: namespace is empty string. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixInvoiceDAOWithNamespaceEmptyString() throws Exception {
        try {
            new InformixInvoiceDAO("  ");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for accuracy. Condition: there is no audit manager key. Expect:
     * <code>InvoiceConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixInvoiceDAOWithNoAuditManagerKey() throws Exception {
        TestHelper.loadConfiguration("config-InformixInvoiceDAO-noAuditManagerKey.xml");
        try {
            new InformixInvoiceDAO("com.topcoder.timetracker.invoice.informix.InformixInvoiceDAO");
            fail("Should throw InvoiceConfigurationException");
        } catch (InvoiceConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for accuracy. Condition: there is no common manager key. Expect:
     * <code>InvoiceConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixInvoiceDAOWithNoCommonManagerKey() throws Exception {
        TestHelper.loadConfiguration("config-InformixInvoiceDAO-noCommonManagerKey.xml");
        try {
            new InformixInvoiceDAO("com.topcoder.timetracker.invoice.informix.InformixInvoiceDAO");
            fail("Should throw InvoiceConfigurationException");
        } catch (InvoiceConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for accuracy. Condition: there is no database connection factory key. Expect:
     * <code>InvoiceConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixInvoiceDAOWithNoDbConnectionFactoryKey() throws Exception {
        TestHelper.loadConfiguration("config-InformixInvoiceDAO-noDbConnectionFactoryKey.xml");
        try {
            new InformixInvoiceDAO("com.topcoder.timetracker.invoice.informix.InformixInvoiceDAO");
            fail("Should throw InvoiceConfigurationException");
        } catch (InvoiceConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for accuracy. Condition: there is no expense manager key. Expect:
     * <code>InvoiceConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixInvoiceDAOWithNoExpenseManagerKey() throws Exception {
        TestHelper.loadConfiguration("config-InformixInvoiceDAO-noExpenseManagerKey.xml");
        try {
            new InformixInvoiceDAO("com.topcoder.timetracker.invoice.informix.InformixInvoiceDAO");
            fail("Should throw InvoiceConfigurationException");
        } catch (InvoiceConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for accuracy. Condition: there is no fixed billing manager key. Expect:
     * <code>InvoiceConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixInvoiceDAOWithNoFixedBillingManagerKey() throws Exception {
        TestHelper.loadConfiguration("config-InformixInvoiceDAO-noFixedBillingManagerKey.xml");
        try {
            new InformixInvoiceDAO("com.topcoder.timetracker.invoice.informix.InformixInvoiceDAO");
            fail("Should throw InvoiceConfigurationException");
        } catch (InvoiceConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for accuracy. Condition: there is no id generator key. Expect:
     * <code>InvoiceConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixInvoiceDAOWithNoIdGeneratorKey() throws Exception {
        TestHelper.loadConfiguration("config-InformixInvoiceDAO-noIdGeneratorKey.xml");
        try {
            new InformixInvoiceDAO("com.topcoder.timetracker.invoice.informix.InformixInvoiceDAO");
            fail("Should throw InvoiceConfigurationException");
        } catch (InvoiceConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for accuracy. Condition: there is no object factory namespace. Expect:
     * <code>InvoiceConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixInvoiceDAOWithNoObjectFactoryNamespace() throws Exception {
        TestHelper.loadConfiguration("config-InformixInvoiceDAO-noObjectFactoryNamespace.xml");
        try {
            new InformixInvoiceDAO("com.topcoder.timetracker.invoice.informix.InformixInvoiceDAO");
            fail("Should throw InvoiceConfigurationException");
        } catch (InvoiceConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for accuracy. Condition: there is no project utility key. Expect:
     * <code>InvoiceConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixInvoiceDAOWithNoProjectUtilityKey() throws Exception {
        TestHelper.loadConfiguration("config-InformixInvoiceDAO-noProjectUtilityKey.xml");
        try {
            new InformixInvoiceDAO("com.topcoder.timetracker.invoice.informix.InformixInvoiceDAO");
            fail("Should throw InvoiceConfigurationException");
        } catch (InvoiceConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for accuracy. Condition: there is no search bundle manager namespace. Expect:
     * <code>InvoiceConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixInvoiceDAOWithNoSearchBundleManagerNamespace() throws Exception {
        TestHelper.loadConfiguration("config-InformixInvoiceDAO-noSearchBundleManagerNamespace.xml");
        try {
            new InformixInvoiceDAO("com.topcoder.timetracker.invoice.informix.InformixInvoiceDAO");
            fail("Should throw InvoiceConfigurationException");
        } catch (InvoiceConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for accuracy. Condition: there is no service detail manager key. Expect:
     * <code>InvoiceConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixInvoiceDAOWithNoServiceDetailManagerKey() throws Exception {
        TestHelper.loadConfiguration("config-InformixInvoiceDAO-noServiceDetailManagerKey.xml");
        try {
            new InformixInvoiceDAO("com.topcoder.timetracker.invoice.informix.InformixInvoiceDAO");
            fail("Should throw InvoiceConfigurationException");
        } catch (InvoiceConfigurationException e) {
            // expected
        }
    }

    /**
     * Test <code>addInvoice</code> for failure. Condition: invoice is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddInvoice() throws Exception {
        try {
            informixInvoiceDAO.addInvoice(null, true);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addInvoice</code> for accuracy. Condition: normal. Expect: the invoice is added to the
     * database.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddInvoiceAccuracy() throws Exception {
        TestHelper.loadConfiguration("config-InformixInvoiceDAO.xml");
        Invoice invoice = new Invoice();

        invoice.setCreationDate(new Date());
        invoice.setCreationUser("tc");
        invoice.setModificationDate(new Date());
        invoice.setModificationUser("tc");
        invoice.setCompanyId(5);
        invoice.setProjectId(8);
        invoice.setInvoiceNumber("invoiceNumber");
        invoice.setPaid(true);
        invoice.setInvoiceDate(new Date());
        invoice.setDueDate(new Date());
        invoice.setPurchaseOrderNumber("purchaseOrderNumber");
        invoice.setSalesTax(new BigDecimal(5));

        ExpenseEntry entry = new ExpenseEntry();
        entry.setAmount(new BigDecimal(5));
        ExpenseEntry[] expenseEntries = new ExpenseEntry[] {entry};
        invoice.setExpenseEntries(expenseEntries);

        PaymentTerm paymentTerm = new PaymentTerm();
        paymentTerm.setId(3);
        invoice.setPaymentTerm(paymentTerm);

        FixedBillingEntry fixedBillingEntry = new FixedBillingEntry();
        FixedBillingEntry[] fixedBillingEntries = new FixedBillingEntry[] {fixedBillingEntry};
        invoice.setFixedBillingEntries(fixedBillingEntries);

        InvoiceStatus invoiceStatus = new InvoiceStatus(3, "description", "user", "user", new Date(), new Date());
        invoice.setInvoiceStatus(invoiceStatus);

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setAmount(new BigDecimal(8));
        InvoiceServiceDetail[] serviceDetails = new InvoiceServiceDetail[] {detail};
        invoice.setServiceDetails(serviceDetails);

        informixInvoiceDAO.addInvoice(invoice, true);

        DBConnectionFactoryImpl factoryImpl = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = factoryImpl.createConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select * from invoice where invoice_id=" + invoice.getId());
            resultSet.next();
            assertEquals("The inserted invoice is not correct", "tc", resultSet.getString("creation_user"));
            assertEquals("The inserted invoice is not correct", "tc", resultSet.getString("modification_user"));
            assertEquals("The inserted invoice is not correct", "invoiceNumber", resultSet
                .getString("invoice_number"));
            assertEquals("The inserted invoice is not correct", "purchaseOrderNumber", resultSet
                .getString("po_number"));
            assertEquals("The inserted invoice is not correct", 5, resultSet.getInt("company_id"));
            assertEquals("The inserted invoice is not correct", 8, resultSet.getInt("project_id"));
            assertEquals("The inserted invoice is not correct", 1, resultSet.getInt("paid"));
            assertEquals("The inserted invoice is not correct", 3, resultSet.getInt("payment_terms_id"));
            assertEquals("The inserted invoice is not correct", 3, resultSet.getInt("invoice_status_id"));

            statement.execute("delete from invoice where invoice_id=" + invoice.getId());

            assertEquals("Error adding invoice", 1, statement.getUpdateCount());
        } finally {
            DBUtil.closeResultSet(resultSet);
            DBUtil.closeStatement(statement);
            DBUtil.closeConnection(connection);
        }

    }

    /**
     * Test <code>addInvoice</code> for failure. Condition: invoice already has is. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddInvoiceAlreadyHasId() throws Exception {
        Invoice invoice = new Invoice();

        invoice.setId(10);
        invoice.setCreationDate(new Date());
        invoice.setCreationUser("tc");
        invoice.setModificationDate(new Date());
        invoice.setModificationUser("tc");
        invoice.setCompanyId(5);
        invoice.setProjectId(8);
        invoice.setInvoiceNumber("invoiceNumber");
        invoice.setPaid(true);
        invoice.setInvoiceDate(new Date());
        invoice.setDueDate(new Date());
        invoice.setPurchaseOrderNumber("purchaseOrderNumber");
        invoice.setSalesTax(new BigDecimal(5));

        ExpenseEntry entry = new ExpenseEntry();
        entry.setAmount(new BigDecimal(5));
        ExpenseEntry[] expenseEntries = new ExpenseEntry[] {entry};
        invoice.setExpenseEntries(expenseEntries);

        PaymentTerm paymentTerm = new PaymentTerm();
        paymentTerm.setId(3);
        invoice.setPaymentTerm(paymentTerm);

        FixedBillingEntry fixedBillingEntry = new FixedBillingEntry();
        FixedBillingEntry[] fixedBillingEntries = new FixedBillingEntry[] {fixedBillingEntry};
        invoice.setFixedBillingEntries(fixedBillingEntries);

        InvoiceStatus invoiceStatus = new InvoiceStatus(3, "description", "user", "user", new Date(), new Date());
        invoice.setInvoiceStatus(invoiceStatus);

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setAmount(new BigDecimal(8));
        InvoiceServiceDetail[] serviceDetails = new InvoiceServiceDetail[] {detail};
        invoice.setServiceDetails(serviceDetails);

        try {
            informixInvoiceDAO.addInvoice(invoice, true);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addInvoice</code> for failure. Condition: creation date is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddInvoiceCreationDateNull() throws Exception {
        Invoice invoice = new Invoice();

        invoice.setCreationUser("tc");
        invoice.setModificationDate(new Date());
        invoice.setModificationUser("tc");
        invoice.setCompanyId(5);
        invoice.setProjectId(8);
        invoice.setInvoiceNumber("invoiceNumber");
        invoice.setPaid(true);
        invoice.setInvoiceDate(new Date());
        invoice.setDueDate(new Date());
        invoice.setPurchaseOrderNumber("purchaseOrderNumber");
        invoice.setSalesTax(new BigDecimal(5));

        ExpenseEntry entry = new ExpenseEntry();
        entry.setAmount(new BigDecimal(5));
        ExpenseEntry[] expenseEntries = new ExpenseEntry[] {entry};
        invoice.setExpenseEntries(expenseEntries);

        PaymentTerm paymentTerm = new PaymentTerm();
        paymentTerm.setId(3);
        invoice.setPaymentTerm(paymentTerm);

        FixedBillingEntry fixedBillingEntry = new FixedBillingEntry();
        FixedBillingEntry[] fixedBillingEntries = new FixedBillingEntry[] {fixedBillingEntry};
        invoice.setFixedBillingEntries(fixedBillingEntries);

        InvoiceStatus invoiceStatus = new InvoiceStatus(3, "description", "user", "user", new Date(), new Date());
        invoice.setInvoiceStatus(invoiceStatus);

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setAmount(new BigDecimal(8));
        InvoiceServiceDetail[] serviceDetails = new InvoiceServiceDetail[] {detail};
        invoice.setServiceDetails(serviceDetails);

        try {
            informixInvoiceDAO.addInvoice(invoice, true);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addInvoice</code> for failure. Condition: creation user is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddInvoiceCreationUserNull() throws Exception {
        Invoice invoice = new Invoice();

        invoice.setCreationDate(new Date());
        invoice.setModificationDate(new Date());
        invoice.setModificationUser("tc");
        invoice.setCompanyId(5);
        invoice.setProjectId(8);
        invoice.setInvoiceNumber("invoiceNumber");
        invoice.setPaid(true);
        invoice.setInvoiceDate(new Date());
        invoice.setDueDate(new Date());
        invoice.setPurchaseOrderNumber("purchaseOrderNumber");
        invoice.setSalesTax(new BigDecimal(5));

        ExpenseEntry entry = new ExpenseEntry();
        entry.setAmount(new BigDecimal(5));
        ExpenseEntry[] expenseEntries = new ExpenseEntry[] {entry};
        invoice.setExpenseEntries(expenseEntries);

        PaymentTerm paymentTerm = new PaymentTerm();
        paymentTerm.setId(3);
        invoice.setPaymentTerm(paymentTerm);

        FixedBillingEntry fixedBillingEntry = new FixedBillingEntry();
        FixedBillingEntry[] fixedBillingEntries = new FixedBillingEntry[] {fixedBillingEntry};
        invoice.setFixedBillingEntries(fixedBillingEntries);

        InvoiceStatus invoiceStatus = new InvoiceStatus(3, "description", "user", "user", new Date(), new Date());
        invoice.setInvoiceStatus(invoiceStatus);

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setAmount(new BigDecimal(8));
        InvoiceServiceDetail[] serviceDetails = new InvoiceServiceDetail[] {detail};
        invoice.setServiceDetails(serviceDetails);

        try {
            informixInvoiceDAO.addInvoice(invoice, true);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addInvoice</code> for failure. Condition: modification date is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddInvoiceModificationDateNull() throws Exception {
        Invoice invoice = new Invoice();

        invoice.setCreationDate(new Date());
        invoice.setCreationUser("tc");
        invoice.setModificationUser("tc");
        invoice.setCompanyId(5);
        invoice.setProjectId(8);
        invoice.setInvoiceNumber("invoiceNumber");
        invoice.setPaid(true);
        invoice.setInvoiceDate(new Date());
        invoice.setDueDate(new Date());
        invoice.setPurchaseOrderNumber("purchaseOrderNumber");
        invoice.setSalesTax(new BigDecimal(5));

        ExpenseEntry entry = new ExpenseEntry();
        entry.setAmount(new BigDecimal(5));
        ExpenseEntry[] expenseEntries = new ExpenseEntry[] {entry};
        invoice.setExpenseEntries(expenseEntries);

        PaymentTerm paymentTerm = new PaymentTerm();
        paymentTerm.setId(3);
        invoice.setPaymentTerm(paymentTerm);

        FixedBillingEntry fixedBillingEntry = new FixedBillingEntry();
        FixedBillingEntry[] fixedBillingEntries = new FixedBillingEntry[] {fixedBillingEntry};
        invoice.setFixedBillingEntries(fixedBillingEntries);

        InvoiceStatus invoiceStatus = new InvoiceStatus(3, "description", "user", "user", new Date(), new Date());
        invoice.setInvoiceStatus(invoiceStatus);

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setAmount(new BigDecimal(8));
        InvoiceServiceDetail[] serviceDetails = new InvoiceServiceDetail[] {detail};
        invoice.setServiceDetails(serviceDetails);

        try {
            informixInvoiceDAO.addInvoice(invoice, true);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addInvoice</code> for failure. Condition: modification user is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddInvoiceModifucationUserNull() throws Exception {
        Invoice invoice = new Invoice();

        invoice.setCreationDate(new Date());
        invoice.setCreationUser("tc");
        invoice.setModificationDate(new Date());
        invoice.setCompanyId(5);
        invoice.setProjectId(8);
        invoice.setInvoiceNumber("invoiceNumber");
        invoice.setPaid(true);
        invoice.setInvoiceDate(new Date());
        invoice.setDueDate(new Date());
        invoice.setPurchaseOrderNumber("purchaseOrderNumber");
        invoice.setSalesTax(new BigDecimal(5));

        ExpenseEntry entry = new ExpenseEntry();
        entry.setAmount(new BigDecimal(5));
        ExpenseEntry[] expenseEntries = new ExpenseEntry[] {entry};
        invoice.setExpenseEntries(expenseEntries);

        PaymentTerm paymentTerm = new PaymentTerm();
        paymentTerm.setId(3);
        invoice.setPaymentTerm(paymentTerm);

        FixedBillingEntry fixedBillingEntry = new FixedBillingEntry();
        FixedBillingEntry[] fixedBillingEntries = new FixedBillingEntry[] {fixedBillingEntry};
        invoice.setFixedBillingEntries(fixedBillingEntries);

        InvoiceStatus invoiceStatus = new InvoiceStatus(3, "description", "user", "user", new Date(), new Date());
        invoice.setInvoiceStatus(invoiceStatus);

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setAmount(new BigDecimal(8));
        InvoiceServiceDetail[] serviceDetails = new InvoiceServiceDetail[] {detail};
        invoice.setServiceDetails(serviceDetails);

        try {
            informixInvoiceDAO.addInvoice(invoice, true);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addInvoice</code> for failure. Condition: company id is not set. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddInvoiceCompanyIdNotSet() throws Exception {
        Invoice invoice = new Invoice();

        invoice.setCreationDate(new Date());
        invoice.setCreationUser("tc");
        invoice.setModificationDate(new Date());
        invoice.setModificationUser("tc");
        invoice.setProjectId(8);
        invoice.setInvoiceNumber("invoiceNumber");
        invoice.setPaid(true);
        invoice.setInvoiceDate(new Date());
        invoice.setDueDate(new Date());
        invoice.setPurchaseOrderNumber("purchaseOrderNumber");
        invoice.setSalesTax(new BigDecimal(5));

        ExpenseEntry entry = new ExpenseEntry();
        entry.setAmount(new BigDecimal(5));
        ExpenseEntry[] expenseEntries = new ExpenseEntry[] {entry};
        invoice.setExpenseEntries(expenseEntries);

        PaymentTerm paymentTerm = new PaymentTerm();
        paymentTerm.setId(3);
        invoice.setPaymentTerm(paymentTerm);

        FixedBillingEntry fixedBillingEntry = new FixedBillingEntry();
        FixedBillingEntry[] fixedBillingEntries = new FixedBillingEntry[] {fixedBillingEntry};
        invoice.setFixedBillingEntries(fixedBillingEntries);

        InvoiceStatus invoiceStatus = new InvoiceStatus(3, "description", "user", "user", new Date(), new Date());
        invoice.setInvoiceStatus(invoiceStatus);

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setAmount(new BigDecimal(8));
        InvoiceServiceDetail[] serviceDetails = new InvoiceServiceDetail[] {detail};
        invoice.setServiceDetails(serviceDetails);

        try {
            informixInvoiceDAO.addInvoice(invoice, true);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addInvoice</code> for failure. Condition: project id is not set. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddInvoiceProjectIdNotSet() throws Exception {
        Invoice invoice = new Invoice();

        invoice.setCreationDate(new Date());
        invoice.setCreationUser("tc");
        invoice.setModificationDate(new Date());
        invoice.setModificationUser("tc");
        invoice.setCompanyId(5);
        invoice.setInvoiceNumber("invoiceNumber");
        invoice.setPaid(true);
        invoice.setInvoiceDate(new Date());
        invoice.setDueDate(new Date());
        invoice.setPurchaseOrderNumber("purchaseOrderNumber");
        invoice.setSalesTax(new BigDecimal(5));

        ExpenseEntry entry = new ExpenseEntry();
        entry.setAmount(new BigDecimal(5));
        ExpenseEntry[] expenseEntries = new ExpenseEntry[] {entry};
        invoice.setExpenseEntries(expenseEntries);

        PaymentTerm paymentTerm = new PaymentTerm();
        paymentTerm.setId(3);
        invoice.setPaymentTerm(paymentTerm);

        FixedBillingEntry fixedBillingEntry = new FixedBillingEntry();
        FixedBillingEntry[] fixedBillingEntries = new FixedBillingEntry[] {fixedBillingEntry};
        invoice.setFixedBillingEntries(fixedBillingEntries);

        InvoiceStatus invoiceStatus = new InvoiceStatus(3, "description", "user", "user", new Date(), new Date());
        invoice.setInvoiceStatus(invoiceStatus);

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setAmount(new BigDecimal(8));
        InvoiceServiceDetail[] serviceDetails = new InvoiceServiceDetail[] {detail};
        invoice.setServiceDetails(serviceDetails);

        try {
            informixInvoiceDAO.addInvoice(invoice, true);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addInvoice</code> for failure. Condition: due date is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddInvoiceDueDateNull() throws Exception {
        Invoice invoice = new Invoice();

        invoice.setCreationDate(new Date());
        invoice.setCreationUser("tc");
        invoice.setModificationDate(new Date());
        invoice.setModificationUser("tc");
        invoice.setCompanyId(5);
        invoice.setProjectId(8);
        invoice.setInvoiceNumber("invoiceNumber");
        invoice.setPaid(true);
        invoice.setInvoiceDate(new Date());
        invoice.setPurchaseOrderNumber("purchaseOrderNumber");
        invoice.setSalesTax(new BigDecimal(5));

        ExpenseEntry entry = new ExpenseEntry();
        entry.setAmount(new BigDecimal(5));
        ExpenseEntry[] expenseEntries = new ExpenseEntry[] {entry};
        invoice.setExpenseEntries(expenseEntries);

        PaymentTerm paymentTerm = new PaymentTerm();
        paymentTerm.setId(3);
        invoice.setPaymentTerm(paymentTerm);

        FixedBillingEntry fixedBillingEntry = new FixedBillingEntry();
        FixedBillingEntry[] fixedBillingEntries = new FixedBillingEntry[] {fixedBillingEntry};
        invoice.setFixedBillingEntries(fixedBillingEntries);

        InvoiceStatus invoiceStatus = new InvoiceStatus(3, "description", "user", "user", new Date(), new Date());
        invoice.setInvoiceStatus(invoiceStatus);

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setAmount(new BigDecimal(8));
        InvoiceServiceDetail[] serviceDetails = new InvoiceServiceDetail[] {detail};
        invoice.setServiceDetails(serviceDetails);

        try {
            informixInvoiceDAO.addInvoice(invoice, true);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addInvoice</code> for failure. Condition: invoice date is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddInvoiceInvoiceDateIsNull() throws Exception {
        Invoice invoice = new Invoice();

        invoice.setCreationDate(new Date());
        invoice.setCreationUser("tc");
        invoice.setModificationDate(new Date());
        invoice.setModificationUser("tc");
        invoice.setCompanyId(5);
        invoice.setProjectId(8);
        invoice.setInvoiceNumber("invoiceNumber");
        invoice.setPaid(true);
        invoice.setDueDate(new Date());
        invoice.setPurchaseOrderNumber("purchaseOrderNumber");
        invoice.setSalesTax(new BigDecimal(5));

        ExpenseEntry entry = new ExpenseEntry();
        entry.setAmount(new BigDecimal(5));
        ExpenseEntry[] expenseEntries = new ExpenseEntry[] {entry};
        invoice.setExpenseEntries(expenseEntries);

        PaymentTerm paymentTerm = new PaymentTerm();
        paymentTerm.setId(3);
        invoice.setPaymentTerm(paymentTerm);

        FixedBillingEntry fixedBillingEntry = new FixedBillingEntry();
        FixedBillingEntry[] fixedBillingEntries = new FixedBillingEntry[] {fixedBillingEntry};
        invoice.setFixedBillingEntries(fixedBillingEntries);

        InvoiceStatus invoiceStatus = new InvoiceStatus(3, "description", "user", "user", new Date(), new Date());
        invoice.setInvoiceStatus(invoiceStatus);

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setAmount(new BigDecimal(8));
        InvoiceServiceDetail[] serviceDetails = new InvoiceServiceDetail[] {detail};
        invoice.setServiceDetails(serviceDetails);

        try {
            informixInvoiceDAO.addInvoice(invoice, true);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addInvoice</code> for failure. Condition: sales tax is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddInvoiceSalesTaxIsNull() throws Exception {
        Invoice invoice = new Invoice();

        invoice.setCreationDate(new Date());
        invoice.setCreationUser("tc");
        invoice.setModificationDate(new Date());
        invoice.setModificationUser("tc");
        invoice.setCompanyId(5);
        invoice.setProjectId(8);
        invoice.setInvoiceNumber("invoiceNumber");
        invoice.setPaid(true);
        invoice.setDueDate(new Date());
        invoice.setInvoiceDate(new Date());
        invoice.setPurchaseOrderNumber("purchaseOrderNumber");

        ExpenseEntry entry = new ExpenseEntry();
        entry.setAmount(new BigDecimal(5));
        ExpenseEntry[] expenseEntries = new ExpenseEntry[] {entry};
        invoice.setExpenseEntries(expenseEntries);

        PaymentTerm paymentTerm = new PaymentTerm();
        paymentTerm.setId(3);
        invoice.setPaymentTerm(paymentTerm);

        FixedBillingEntry fixedBillingEntry = new FixedBillingEntry();
        FixedBillingEntry[] fixedBillingEntries = new FixedBillingEntry[] {fixedBillingEntry};
        invoice.setFixedBillingEntries(fixedBillingEntries);

        InvoiceStatus invoiceStatus = new InvoiceStatus(3, "description", "user", "user", new Date(), new Date());
        invoice.setInvoiceStatus(invoiceStatus);

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setAmount(new BigDecimal(8));
        InvoiceServiceDetail[] serviceDetails = new InvoiceServiceDetail[] {detail};
        invoice.setServiceDetails(serviceDetails);

        try {
            informixInvoiceDAO.addInvoice(invoice, true);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addInvoice</code> for failure. Condition: expense entries is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddInvoiceExpenseEntriesIsNull() throws Exception {
        Invoice invoice = new Invoice();

        invoice.setCreationDate(new Date());
        invoice.setCreationUser("tc");
        invoice.setModificationDate(new Date());
        invoice.setModificationUser("tc");
        invoice.setCompanyId(5);
        invoice.setProjectId(8);
        invoice.setInvoiceNumber("invoiceNumber");
        invoice.setPaid(true);
        invoice.setDueDate(new Date());
        invoice.setInvoiceDate(new Date());
        invoice.setPurchaseOrderNumber("purchaseOrderNumber");
        invoice.setSalesTax(new BigDecimal(5));

        PaymentTerm paymentTerm = new PaymentTerm();
        paymentTerm.setId(3);
        invoice.setPaymentTerm(paymentTerm);

        FixedBillingEntry fixedBillingEntry = new FixedBillingEntry();
        FixedBillingEntry[] fixedBillingEntries = new FixedBillingEntry[] {fixedBillingEntry};
        invoice.setFixedBillingEntries(fixedBillingEntries);

        InvoiceStatus invoiceStatus = new InvoiceStatus(3, "description", "user", "user", new Date(), new Date());
        invoice.setInvoiceStatus(invoiceStatus);

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setAmount(new BigDecimal(8));
        InvoiceServiceDetail[] serviceDetails = new InvoiceServiceDetail[] {detail};
        invoice.setServiceDetails(serviceDetails);

        try {
            informixInvoiceDAO.addInvoice(invoice, true);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addInvoice</code> for failure. Condition: payment terms is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddInvoicePaymentTermsIsNull() throws Exception {
        Invoice invoice = new Invoice();

        invoice.setCreationDate(new Date());
        invoice.setCreationUser("tc");
        invoice.setModificationDate(new Date());
        invoice.setModificationUser("tc");
        invoice.setCompanyId(5);
        invoice.setProjectId(8);
        invoice.setInvoiceNumber("invoiceNumber");
        invoice.setPaid(true);
        invoice.setDueDate(new Date());
        invoice.setInvoiceDate(new Date());
        invoice.setPurchaseOrderNumber("purchaseOrderNumber");
        invoice.setSalesTax(new BigDecimal(5));

        ExpenseEntry entry = new ExpenseEntry();
        entry.setAmount(new BigDecimal(5));
        ExpenseEntry[] expenseEntries = new ExpenseEntry[] {entry};
        invoice.setExpenseEntries(expenseEntries);

        FixedBillingEntry fixedBillingEntry = new FixedBillingEntry();
        FixedBillingEntry[] fixedBillingEntries = new FixedBillingEntry[] {fixedBillingEntry};
        invoice.setFixedBillingEntries(fixedBillingEntries);

        InvoiceStatus invoiceStatus = new InvoiceStatus(3, "description", "user", "user", new Date(), new Date());
        invoice.setInvoiceStatus(invoiceStatus);

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setAmount(new BigDecimal(8));
        InvoiceServiceDetail[] serviceDetails = new InvoiceServiceDetail[] {detail};
        invoice.setServiceDetails(serviceDetails);

        try {
            informixInvoiceDAO.addInvoice(invoice, true);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addInvoice</code> for failure. Condition: fixed billing entries is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddInvoiceFixedBillingEntriesIsNull() throws Exception {
        Invoice invoice = new Invoice();

        invoice.setCreationDate(new Date());
        invoice.setCreationUser("tc");
        invoice.setModificationDate(new Date());
        invoice.setModificationUser("tc");
        invoice.setCompanyId(5);
        invoice.setProjectId(8);
        invoice.setInvoiceNumber("invoiceNumber");
        invoice.setPaid(true);
        invoice.setDueDate(new Date());
        invoice.setInvoiceDate(new Date());
        invoice.setPurchaseOrderNumber("purchaseOrderNumber");
        invoice.setSalesTax(new BigDecimal(5));

        ExpenseEntry entry = new ExpenseEntry();
        entry.setAmount(new BigDecimal(5));
        ExpenseEntry[] expenseEntries = new ExpenseEntry[] {entry};
        invoice.setExpenseEntries(expenseEntries);

        PaymentTerm paymentTerm = new PaymentTerm();
        paymentTerm.setId(3);
        invoice.setPaymentTerm(paymentTerm);

        InvoiceStatus invoiceStatus = new InvoiceStatus(3, "description", "user", "user", new Date(), new Date());
        invoice.setInvoiceStatus(invoiceStatus);

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setAmount(new BigDecimal(8));
        InvoiceServiceDetail[] serviceDetails = new InvoiceServiceDetail[] {detail};
        invoice.setServiceDetails(serviceDetails);

        try {
            informixInvoiceDAO.addInvoice(invoice, true);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addInvoice</code> for failure. Condition: detail is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddInvoiceDetailIsNull() throws Exception {
        Invoice invoice = new Invoice();

        invoice.setCreationDate(new Date());
        invoice.setCreationUser("tc");
        invoice.setModificationDate(new Date());
        invoice.setModificationUser("tc");
        invoice.setCompanyId(5);
        invoice.setProjectId(8);
        invoice.setInvoiceNumber("invoiceNumber");
        invoice.setPaid(true);
        invoice.setDueDate(new Date());
        invoice.setInvoiceDate(new Date());
        invoice.setPurchaseOrderNumber("purchaseOrderNumber");
        invoice.setSalesTax(new BigDecimal(5));

        ExpenseEntry entry = new ExpenseEntry();
        entry.setAmount(new BigDecimal(5));
        ExpenseEntry[] expenseEntries = new ExpenseEntry[] {entry};
        invoice.setExpenseEntries(expenseEntries);

        PaymentTerm paymentTerm = new PaymentTerm();
        paymentTerm.setId(3);
        invoice.setPaymentTerm(paymentTerm);

        FixedBillingEntry fixedBillingEntry = new FixedBillingEntry();
        FixedBillingEntry[] fixedBillingEntries = new FixedBillingEntry[] {fixedBillingEntry};
        invoice.setFixedBillingEntries(fixedBillingEntries);

        InvoiceStatus invoiceStatus = new InvoiceStatus(3, "description", "user", "user", new Date(), new Date());
        invoice.setInvoiceStatus(invoiceStatus);

        try {
            informixInvoiceDAO.addInvoice(invoice, true);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addInvoice</code> for failure. Condition: invoice status is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddInvoiceInvoiceStatusIsNull() throws Exception {
        Invoice invoice = new Invoice();

        invoice.setCreationDate(new Date());
        invoice.setCreationUser("tc");
        invoice.setModificationDate(new Date());
        invoice.setModificationUser("tc");
        invoice.setCompanyId(5);
        invoice.setProjectId(8);
        invoice.setInvoiceNumber("invoiceNumber");
        invoice.setPaid(true);
        invoice.setDueDate(new Date());
        invoice.setInvoiceDate(new Date());
        invoice.setPurchaseOrderNumber("purchaseOrderNumber");
        invoice.setSalesTax(new BigDecimal(5));

        ExpenseEntry entry = new ExpenseEntry();
        entry.setAmount(new BigDecimal(5));
        ExpenseEntry[] expenseEntries = new ExpenseEntry[] {entry};
        invoice.setExpenseEntries(expenseEntries);

        PaymentTerm paymentTerm = new PaymentTerm();
        paymentTerm.setId(3);
        invoice.setPaymentTerm(paymentTerm);

        FixedBillingEntry fixedBillingEntry = new FixedBillingEntry();
        FixedBillingEntry[] fixedBillingEntries = new FixedBillingEntry[] {fixedBillingEntry};
        invoice.setFixedBillingEntries(fixedBillingEntries);

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setAmount(new BigDecimal(8));
        InvoiceServiceDetail[] serviceDetails = new InvoiceServiceDetail[] {detail};
        invoice.setServiceDetails(serviceDetails);

        try {
            informixInvoiceDAO.addInvoice(invoice, true);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>updateInvoice</code> for failure. Condition: invoice is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateInvoice() throws Exception {
        try {
            informixInvoiceDAO.updateInvoice(null, true);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>updateInvoice</code> for failure. Condition: id is not set. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateInvoiceIdNotSet() throws Exception {
        Invoice invoice = new Invoice();

        invoice.setCreationDate(new Date());
        invoice.setCreationUser("tc");
        invoice.setModificationDate(new Date());
        invoice.setModificationUser("tc");
        invoice.setCompanyId(5);
        invoice.setProjectId(8);
        invoice.setInvoiceNumber("invoiceNumber");
        invoice.setPaid(true);
        invoice.setDueDate(new Date());
        invoice.setInvoiceDate(new Date());
        invoice.setPurchaseOrderNumber("purchaseOrderNumber");
        invoice.setSalesTax(new BigDecimal(5));

        ExpenseEntry entry = new ExpenseEntry();
        entry.setAmount(new BigDecimal(5));
        ExpenseEntry[] expenseEntries = new ExpenseEntry[] {entry};
        invoice.setExpenseEntries(expenseEntries);

        PaymentTerm paymentTerm = new PaymentTerm();
        paymentTerm.setId(3);
        invoice.setPaymentTerm(paymentTerm);

        FixedBillingEntry fixedBillingEntry = new FixedBillingEntry();
        FixedBillingEntry[] fixedBillingEntries = new FixedBillingEntry[] {fixedBillingEntry};
        invoice.setFixedBillingEntries(fixedBillingEntries);

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setAmount(new BigDecimal(8));
        detail.setId(4);
        InvoiceServiceDetail[] serviceDetails = new InvoiceServiceDetail[] {detail};
        invoice.setServiceDetails(serviceDetails);

        InvoiceStatus invoiceStatus = new InvoiceStatus(4, "description", "user", "user", new Date(), new Date());
        invoice.setInvoiceStatus(invoiceStatus);

        try {
            informixInvoiceDAO.updateInvoice(invoice, true);
            fail("Should throw InvoiceUnrecognizedEntityException");
        } catch (InvoiceUnrecognizedEntityException e) {
            // expected
        }
    }

    /**
     * Test <code>updateInvoice</code> for accuracy. Condition: invoice is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateInvoiceAccuracy() throws Exception {
        Invoice invoice = new Invoice();

        invoice.setId(4);
        invoice.setCreationDate(new Date());
        invoice.setCreationUser("tc");
        invoice.setModificationDate(new Date());
        invoice.setModificationUser("tc");
        invoice.setCompanyId(5);
        invoice.setProjectId(8);
        invoice.setInvoiceNumber("invoiceNumber");
        invoice.setPaid(true);
        invoice.setDueDate(new Date());
        invoice.setInvoiceDate(new Date());
        invoice.setPurchaseOrderNumber("purchaseOrderNumber");
        invoice.setSalesTax(new BigDecimal(5));

        ExpenseEntry entry = new ExpenseEntry();
        entry.setAmount(new BigDecimal(5));
        ExpenseEntry[] expenseEntries = new ExpenseEntry[] {entry};
        invoice.setExpenseEntries(expenseEntries);

        PaymentTerm paymentTerm = new PaymentTerm();
        paymentTerm.setId(3);
        invoice.setPaymentTerm(paymentTerm);

        FixedBillingEntry fixedBillingEntry = new FixedBillingEntry();
        FixedBillingEntry[] fixedBillingEntries = new FixedBillingEntry[] {fixedBillingEntry};
        invoice.setFixedBillingEntries(fixedBillingEntries);

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setAmount(new BigDecimal(8));
        detail.setId(4);
        InvoiceServiceDetail[] serviceDetails = new InvoiceServiceDetail[] {detail};
        invoice.setServiceDetails(serviceDetails);

        InvoiceStatus invoiceStatus = new InvoiceStatus(4, "description", "user", "user", new Date(), new Date());
        invoice.setInvoiceStatus(invoiceStatus);

        informixInvoiceDAO.updateInvoice(invoice, true);

        TestHelper.loadConfiguration("config-InformixInvoiceDAO.xml");
        DBConnectionFactoryImpl factoryImpl = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = factoryImpl.createConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select * from invoice where invoice_id=" + invoice.getId());
            resultSet.next();
            assertEquals("The inserted invoice is not correct", "tc", resultSet.getString("creation_user"));
            assertEquals("The inserted invoice is not correct", "tc", resultSet.getString("modification_user"));
            assertEquals("The inserted invoice is not correct", "invoiceNumber", resultSet
                .getString("invoice_number"));
            assertEquals("The inserted invoice is not correct", "purchaseOrderNumber", resultSet
                .getString("po_number"));
            assertEquals("The inserted invoice is not correct", 5, resultSet.getInt("company_id"));
            assertEquals("The inserted invoice is not correct", 8, resultSet.getInt("project_id"));
            assertEquals("The inserted invoice is not correct", 1, resultSet.getInt("paid"));
            assertEquals("The inserted invoice is not correct", 3, resultSet.getInt("payment_terms_id"));
            assertEquals("The inserted invoice is not correct", 4, resultSet.getInt("invoice_status_id"));

        } finally {
            DBUtil.closeResultSet(resultSet);
            DBUtil.closeStatement(statement);
            DBUtil.closeConnection(connection);
        }

    }

    /**
     * Test <code>getInvoice</code> for failure. Condition: invoice id is not in the database. Expect:
     * <code>InvoiceUnrecognizedEntityException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetInvoice() throws Exception {
        try {
            informixInvoiceDAO.getInvoice(7);
            fail("Should throw InvoiceUnrecognizedEntityException");
        } catch (InvoiceUnrecognizedEntityException e) {
            // expected
        }
    }

    /**
     * Test <code>getInvoice</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetInvoiceAccuracy() throws Exception {
        Invoice invoice = informixInvoiceDAO.getInvoice(3);

        assertEquals("The returned value is not as expected", 3, invoice.getId());
        assertEquals("The returned value is not as expected", 4, invoice.getInvoiceStatus().getId());

    }

    /**
     * Test <code>getAllInvoices</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllInvoices() throws Exception {
        Invoice[] allInvoices = informixInvoiceDAO.getAllInvoices();

        assertEquals("The number of returned elements is not correct", 5, allInvoices.length);
    }

    /**
     * Test <code>canCreateInvoice</code> for failure. Condition: projectId is negative. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCanCreateInvoice() throws Exception {
        try {
            informixInvoiceDAO.canCreateInvoice(-1);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>canCreateInvoice</code> for accuracy. Condition: normal. Expect: everything is ok.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCanCreateInvoiceAccuracy() throws Exception {
        assertEquals("The result is not as expected", true, informixInvoiceDAO.canCreateInvoice(5));

    }

    /**
     * Test <code>searchInvoice</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchInvoiceAccuracy1() throws Exception {
        Invoice[] i =
            informixInvoiceDAO.searchInvoices(InformixInvoiceFilterFactory.createInvoiceStatusIdFilter(1),
                InvoiceSearchDepth.INVOICE_ALL);
        assertEquals("the returned valus is not as expected", 0, i.length);

    }

    /**
     * Test <code>searchInvoice</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchInvoiceAccuracy2() throws Exception {
        Invoice[] i =
            informixInvoiceDAO.searchInvoices(InformixInvoiceFilterFactory.createInvoiceStatusIdFilter(1),
                InvoiceSearchDepth.INVOICE_ONLY);

        assertEquals("the returned valus is not as expected", 1, i.length);
        assertEquals("the returned valus is not as expected", 1, i[0].getId());
    }

    /**
     * Test <code>searchInvoice</code> for failure. Condition: filter is null. Expect: returned value is as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchInvoiceFilterNull() throws Exception {
        try {
            informixInvoiceDAO.searchInvoices(null, InvoiceSearchDepth.INVOICE_ALL);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>searchInvoice</code> for failure. Condition: depth is null. Expect: returned value is as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchInvoiceDepthNull() throws Exception {
        try {
            informixInvoiceDAO.searchInvoices(InformixInvoiceFilterFactory.createClientIdFilter(1), null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
