/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common.failuretests;

import java.io.File;
import java.sql.Connection;
import java.util.Date;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.common.CommonManagerConfigurationException;
import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.common.PaymentTermDAO;
import com.topcoder.timetracker.common.PaymentTermDAOException;
import com.topcoder.timetracker.common.PaymentTermNotFoundException;
import com.topcoder.timetracker.common.persistence.DatabasePaymentTermDAO;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link DatabasePaymentTermDAO}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class DatabasePaymentTermDAOFailureTests extends TestCase {

    /**
     * <p>
     * Represents the payment term dao.
     * </p>
     */
    private PaymentTermDAO paymentTermDAO;

    private DBConnectionFactory connectionFactory;

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.clearNamespaces();
        FailureTestHelper.loadNamesapces("DBConnectionFactory.xml");
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "DatabasePaymentTermDAO" + File.separator
            + "DefaultNS.xml");

        connectionFactory = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");

        Connection conn = connectionFactory.createConnection();
        clearTables(conn);
        conn.close();
        paymentTermDAO = new DatabasePaymentTermDAO("DatabasePaymentTermDAO");
    }

    /**
     * <p>
     * teardown the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();

        FailureTestHelper.clearNamespaces();
    }

    /**
     * <p>
     * Clear the table.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    private void clearTables(Connection conn) throws Exception {
        conn.createStatement().execute("DELETE FROM payment_terms");
    }

    /**
     * <p>
     * Create payment term.
     * </p>
     * @return the payment term instance.
     */
    private PaymentTerm createPaymentTerm() {
        PaymentTerm paymentTerm = new PaymentTerm();

        paymentTerm.setDescription("test");
        Date now = new Date();
        paymentTerm.setCreationDate(now);
        paymentTerm.setCreationUser("Topcoder");
        paymentTerm.setModificationDate(now);
        paymentTerm.setModificationUser("TopCoder");
        paymentTerm.setTerm(11);
        paymentTerm.setActive(true);
        paymentTerm.setId(10);

        return paymentTerm;
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#DatabasePaymentTermDAO(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDatabasePaymentTermDAONSNullNamespace() throws Exception {
        try {
            new DatabasePaymentTermDAO(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#DatabasePaymentTermDAO(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDatabasePaymentTermDAONSEmtpyNamespace() throws Exception {
        try {
            new DatabasePaymentTermDAO("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#DatabasePaymentTermDAO(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDatabasePaymentTermDAONSTrimmedEmtpyNamespace() throws Exception {
        try {
            new DatabasePaymentTermDAO("  ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#DatabasePaymentTermDAO(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDatabasePaymentTermDAONSConfigurationProblem1() throws Exception {
        FailureTestHelper.clearNamespaces();
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "DatabasePaymentTermDAO" + File.separator
            + "DefaultNS1.xml");

        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO");
            fail("expect throw CommonManagerConfigurationException.");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#DatabasePaymentTermDAO(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDatabasePaymentTermDAONSConfigurationProblem2() throws Exception {
        FailureTestHelper.clearNamespaces();
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "DatabasePaymentTermDAO" + File.separator
            + "DefaultNS2.xml");

        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO");
            fail("expect throw CommonManagerConfigurationException.");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#DatabasePaymentTermDAO(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDatabasePaymentTermDAONSConfigurationProblem3() throws Exception {
        FailureTestHelper.clearNamespaces();
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "DatabasePaymentTermDAO" + File.separator
            + "DefaultNS3.xml");

        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO");
            fail("expect throw CommonManagerConfigurationException.");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#DatabasePaymentTermDAO(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDatabasePaymentTermDAONSConfigurationProblem4() throws Exception {
        FailureTestHelper.clearNamespaces();
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "DatabasePaymentTermDAO" + File.separator
            + "DefaultNS4.xml");

        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO");
            fail("expect throw CommonManagerConfigurationException.");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#DatabasePaymentTermDAO(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDatabasePaymentTermDAONSConfigurationProblem5() throws Exception {
        FailureTestHelper.clearNamespaces();
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "DatabasePaymentTermDAO" + File.separator
            + "DefaultNS5.xml");

        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO");
            fail("expect throw CommonManagerConfigurationException.");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#DatabasePaymentTermDAO(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDatabasePaymentTermDAONSConfigurationProblem6() throws Exception {
        FailureTestHelper.clearNamespaces();
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "DatabasePaymentTermDAO" + File.separator
            + "DefaultNS6.xml");

        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO");
            fail("expect throw CommonManagerConfigurationException.");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#DatabasePaymentTermDAO(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDatabasePaymentTermDAONSConfigurationProblem7() throws Exception {
        FailureTestHelper.clearNamespaces();
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "DatabasePaymentTermDAO" + File.separator
            + "DefaultNS7.xml");

        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO");
            fail("expect throw CommonManagerConfigurationException.");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#DatabasePaymentTermDAO(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDatabasePaymentTermDAONSConfigurationProblem8() throws Exception {
        FailureTestHelper.clearNamespaces();
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "DatabasePaymentTermDAO" + File.separator
            + "DefaultNS8.xml");

        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO");
            fail("expect throw CommonManagerConfigurationException.");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#DatabasePaymentTermDAO(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDatabasePaymentTermDAONSConfigurationProblem9() throws Exception {
        FailureTestHelper.clearNamespaces();
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "DatabasePaymentTermDAO" + File.separator
            + "DefaultNS9.xml");

        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO");
            fail("expect throw CommonManagerConfigurationException.");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#DatabasePaymentTermDAO(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDatabasePaymentTermDAONSConfigurationProblem11() throws Exception {
        FailureTestHelper.clearNamespaces();
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "DatabasePaymentTermDAO" + File.separator
            + "DefaultNS11.xml");

        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO");
            fail("expect throw CommonManagerConfigurationException.");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#DatabasePaymentTermDAO(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDatabasePaymentTermDAONSConfigurationProblem12() throws Exception {
        FailureTestHelper.clearNamespaces();
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "DatabasePaymentTermDAO" + File.separator
            + "DefaultNS12.xml");

        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO");
            fail("expect throw CommonManagerConfigurationException.");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#DatabasePaymentTermDAO(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDatabasePaymentTermDAONSConfigurationProblem13() throws Exception {
        FailureTestHelper.clearNamespaces();
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "DatabasePaymentTermDAO" + File.separator
            + "DefaultNS13.xml");

        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO");
            fail("expect throw CommonManagerConfigurationException.");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DatabasePaymentTermDAO#DatabasePaymentTermDAO(com.topcoder.db.connectionfactory.DBConnectionFactory, String, String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDatabasePaymentTermDAONotNSIAE1() throws Exception {
        try {
            new DatabasePaymentTermDAO(null, "Informix", "PaymentTermGenerator");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DatabasePaymentTermDAO#DatabasePaymentTermDAO(com.topcoder.db.connectionfactory.DBConnectionFactory, String, String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDatabasePaymentTermDAONotNSIAE2() throws Exception {
        try {
            new DatabasePaymentTermDAO(new DBConnectionFactoryImpl(), null, "PaymentTermGenerator");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DatabasePaymentTermDAO#DatabasePaymentTermDAO(com.topcoder.db.connectionfactory.DBConnectionFactory, String, String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDatabasePaymentTermDAONotNSIAE3() throws Exception {
        try {
            new DatabasePaymentTermDAO(new DBConnectionFactoryImpl(), "", "PaymentTermGenerator");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DatabasePaymentTermDAO#DatabasePaymentTermDAO(com.topcoder.db.connectionfactory.DBConnectionFactory, String, String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDatabasePaymentTermDAONotNSIAE4() throws Exception {
        try {
            new DatabasePaymentTermDAO(new DBConnectionFactoryImpl(), "  ", "PaymentTermGenerator");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DatabasePaymentTermDAO#DatabasePaymentTermDAO(com.topcoder.db.connectionfactory.DBConnectionFactory, String, String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDatabasePaymentTermDAONotNSIAE5() throws Exception {
        try {
            new DatabasePaymentTermDAO(new DBConnectionFactoryImpl(), "Informix", null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DatabasePaymentTermDAO#DatabasePaymentTermDAO(com.topcoder.db.connectionfactory.DBConnectionFactory, String, String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDatabasePaymentTermDAONotNSIAE6() throws Exception {
        try {
            new DatabasePaymentTermDAO(new DBConnectionFactoryImpl(), "Informix", "");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DatabasePaymentTermDAO#DatabasePaymentTermDAO(com.topcoder.db.connectionfactory.DBConnectionFactory, String, String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDatabasePaymentTermDAONotNSIAE7() throws Exception {
        try {
            new DatabasePaymentTermDAO(new DBConnectionFactoryImpl(), "Informix", " ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DatabasePaymentTermDAO#DatabasePaymentTermDAO(com.topcoder.db.connectionfactory.DBConnectionFactory, String, String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDatabasePaymentTermDAONotNSIAE8() throws Exception {
        try {
            new DatabasePaymentTermDAO(new DBConnectionFactoryImpl(), "Informix", "invalid");
            fail("expect throw CommonManagerConfigurationException.");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermNullPaymentTerm() throws Exception {
        try {
            paymentTermDAO.addPaymentTerm(null);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermNegativeTerm() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setTerm(-1);
        try {
            paymentTermDAO.addPaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermZeroTerm() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setTerm(0);
        try {
            paymentTermDAO.addPaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermNullCreationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setCreationUser(null);
        try {
            paymentTermDAO.addPaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermEmptyCreationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setCreationUser("");
        try {
            paymentTermDAO.addPaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermTrimmedEmptyCreationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setCreationUser("  ");
        try {
            paymentTermDAO.addPaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermTooLongCreationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setCreationUser(FailureTestHelper.LONG_STRING);
        try {
            paymentTermDAO.addPaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermNullDescription() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setDescription(null);
        try {
            paymentTermDAO.addPaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermEmptyDescription() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setDescription("");
        try {
            paymentTermDAO.addPaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermTrimmedEmptyDescription() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setDescription("  ");
        try {
            paymentTermDAO.addPaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermTooLongDescription() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setDescription(FailureTestHelper.LONG_STRING);
        try {
            paymentTermDAO.addPaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermEmptyModificationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setModificationUser("");
        try {
            paymentTermDAO.addPaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermTrimmedEmptyModificationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setModificationUser("  ");
        try {
            paymentTermDAO.addPaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermTooLongModificationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setModificationUser(FailureTestHelper.LONG_STRING);
        try {
            paymentTermDAO.addPaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermNullPaymentTerm() throws Exception {
        try {
            paymentTermDAO.updatePaymentTerm(null);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermNegativeId() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setId(-1);
        try {
            paymentTermDAO.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermZeroId() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setId(0);
        try {
            paymentTermDAO.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermNegativeTerm() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setTerm(-1);
        try {
            paymentTermDAO.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermZeroTerm() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setTerm(0);
        try {
            paymentTermDAO.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermNullCreationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setCreationUser(null);
        try {
            paymentTermDAO.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermEmptyCreationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setCreationUser("");
        try {
            paymentTermDAO.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermTrimmedEmptyCreationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setCreationUser("  ");
        try {
            paymentTermDAO.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermTooLongCreationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setCreationUser(FailureTestHelper.LONG_STRING);
        try {
            paymentTermDAO.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermNullDescription() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setDescription(null);
        try {
            paymentTermDAO.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermEmptyDescription() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setDescription("");
        try {
            paymentTermDAO.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermTrimmedEmptyDescription() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setDescription("  ");
        try {
            paymentTermDAO.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermTooLongDescription() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setDescription(FailureTestHelper.LONG_STRING);
        try {
            paymentTermDAO.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermNullModificationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setModificationUser(null);
        try {
            paymentTermDAO.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermEmptyModificationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setModificationUser("");
        try {
            paymentTermDAO.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermTrimmedEmptyModificationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setModificationUser("  ");
        try {
            paymentTermDAO.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermTooLongModificationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setModificationUser(FailureTestHelper.LONG_STRING);
        try {
            paymentTermDAO.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermNullCreationDate() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setCreationDate(null);
        try {
            paymentTermDAO.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermNullModificationDate() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setModificationDate(null);
        try {
            paymentTermDAO.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermModificationDateBeforeCreationDate() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setModificationDate(new Date(paymentTerm.getCreationDate().getTime() - 100000));
        try {
            paymentTermDAO.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermPaymentTermDAOException() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setCreationDate(new Date(paymentTerm.getCreationDate().getTime() - 1000));
        try {
            paymentTermDAO.updatePaymentTerm(paymentTerm);
            fail("expect throw PaymentTermNotFoundException");
        } catch (PaymentTermNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#retrievePaymentTerm(long)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrievePaymentTermNegativeId() throws Exception {
        try {
            paymentTermDAO.retrievePaymentTerm(-1);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#retrievePaymentTerm(long)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrievePaymentTermZeroId() throws Exception {
        try {
            paymentTermDAO.retrievePaymentTerm(0);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#retrievePaymentTerm(long[])}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrievePaymentTermsNegativeId() throws Exception {
        try {
            paymentTermDAO.retrievePaymentTerms(new long[] {-1});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#retrievePaymentTerm(long[])}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrievePaymentTermsZeroId() throws Exception {
        try {
            paymentTermDAO.retrievePaymentTerms(new long[] {0});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#retrieveRecentlyCreatedPaymentTerms(int)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrieveRecentlyCreatedPaymentTermsWithArgIAE1() throws Exception {
        try {
            paymentTermDAO.retrieveRecentlyCreatedPaymentTerms(-100);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#retrieveRecentlyCreatedPaymentTerms(int)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrieveRecentlyCreatedPaymentTermsWithArgIAE2() throws Exception {
        try {
            paymentTermDAO.retrieveRecentlyCreatedPaymentTerms(0);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#retrieveRecentlyModifiedPaymentTerms(int)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrieveARecentlyModifiedPaymentTermsWithArgIAE1() throws Exception {
        try {
            paymentTermDAO.retrieveRecentlyModifiedPaymentTerms(-100);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#retrieveRecentlyModifiedPaymentTerms(int)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrieveARecentlyModifiedPaymentTermsWithArgIAE2() throws Exception {
        try {
            paymentTermDAO.retrieveRecentlyModifiedPaymentTerms(0);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#deletePaymentTerm(long)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeletePaymentTermNegativeId() throws Exception {
        try {
            paymentTermDAO.deletePaymentTerm(-1);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#deletePaymentTerm(long)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeletePaymentTermZeroId() throws Exception {
        try {
            paymentTermDAO.deletePaymentTerm(0);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#deletePaymentTerm(long)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeletePaymentTermPaymentTermDAOException() throws Exception {
        try {
            paymentTermDAO.deletePaymentTerm(1);
            fail("expect throw PaymentTermDAOException");
        } catch (PaymentTermDAOException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#deletePaymentTerms(long[])}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeletePaymentTermsNegativeId() throws Exception {
        try {
            paymentTermDAO.deletePaymentTerms(new long[] {-1});
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#deletePaymentTerms(long[])}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeletePaymentTermsZeroId() throws Exception {
        try {
            paymentTermDAO.deletePaymentTerms(new long[] {0});
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DatabasePaymentTermDAO#deletePaymentTerms(long[])}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeletePaymentTermsPaymentTermDAOException() throws Exception {
        try {
            paymentTermDAO.deletePaymentTerms(new long[] {1});
            fail("expect throw PaymentTermDAOException");
        } catch (PaymentTermDAOException e) {
            // expected.
        }
    }
}
