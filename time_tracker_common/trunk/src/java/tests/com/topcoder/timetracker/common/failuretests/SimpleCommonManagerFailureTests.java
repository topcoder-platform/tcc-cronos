/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common.failuretests;

import java.io.File;
import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.timetracker.common.CommonManager;
import com.topcoder.timetracker.common.CommonManagerConfigurationException;
import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.common.PaymentTermDAO;
import com.topcoder.timetracker.common.PaymentTermDAOException;
import com.topcoder.timetracker.common.SimpleCommonManager;

/**
 * <p>
 * Failure test for <code>{@link SimpleCommonManager}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class SimpleCommonManagerFailureTests extends TestCase {

    /**
     * <p>
     * Dummy implementation of the PaymentTermDAO interface, only for testing purpose.
     * </p>
     */
    private class DummyPaymentTermDAO implements PaymentTermDAO {

        public void addPaymentTerm(PaymentTerm paymentTerm) throws PaymentTermDAOException {
            throw new PaymentTermDAOException("Mock");
        }

        public void deleteAllPaymentTerms() throws PaymentTermDAOException {
            throw new PaymentTermDAOException("Mock");
        }

        public void deletePaymentTerm(long id) throws PaymentTermDAOException {
            throw new PaymentTermDAOException("Mock");
        }

        public void deletePaymentTerms(long[] ids) throws PaymentTermDAOException {
            throw new PaymentTermDAOException("Mock");
        }

        public PaymentTerm[] retrieveActivePaymentTerms() throws PaymentTermDAOException {
            throw new PaymentTermDAOException("Mock");
        }

        public PaymentTerm[] retrieveAllPaymentTerms() throws PaymentTermDAOException {
            throw new PaymentTermDAOException("Mock");
        }

        public PaymentTerm retrievePaymentTerm(long id) throws PaymentTermDAOException {
            throw new PaymentTermDAOException("Mock");
        }

        public PaymentTerm[] retrievePaymentTerms(long[] ids) throws PaymentTermDAOException {
            throw new PaymentTermDAOException("Mock");
        }

        public PaymentTerm[] retrieveRecentlyCreatedPaymentTerms(int recentDays) throws PaymentTermDAOException {
            throw new PaymentTermDAOException("Mock");
        }

        public PaymentTerm[] retrieveRecentlyModifiedPaymentTerms(int recentDays) throws PaymentTermDAOException {
            throw new PaymentTermDAOException("Mock");
        }

        public void updatePaymentTerm(PaymentTerm paymentTerm) throws PaymentTermDAOException {
            throw new PaymentTermDAOException("Mock");
        }

    }

    private PaymentTermDAO paymentTermDAO;

    /**
     * <p>
     * Represents the CommonManager instance used in tests.
     * </p>
     */
    private CommonManager commonManager;

    /**
     * setup the testing environment.
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        FailureTestHelper.clearNamespaces();
        FailureTestHelper.loadNamesapces("DBConnectionFactory.xml");
        paymentTermDAO = new DummyPaymentTermDAO();
        commonManager = new SimpleCommonManager(paymentTermDAO, 1);
    }

    /**
     * <p>
     * Tear down the testing environment.
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
     * Failure test for <code>{@link SimpleCommonManager#SimpleCommonManager()}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerDefaultNSCommonManagerConfigurationException1() throws Exception {
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "SimpleCommonManager" + File.separator
            + "DefaultNS1.xml");

        try {
            new SimpleCommonManager();
            fail("expect throw CommonManagerConfigurationException");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#SimpleCommonManager()}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerDefaultNSCommonManagerConfigurationException2() throws Exception {
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "SimpleCommonManager" + File.separator
            + "DefaultNS2.xml");

        try {
            new SimpleCommonManager();
            fail("expect throw CommonManagerConfigurationException");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#SimpleCommonManager()}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerDefaultNSCommonManagerConfigurationException3() throws Exception {
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "SimpleCommonManager" + File.separator
            + "DefaultNS3.xml");

        try {
            new SimpleCommonManager();
            fail("expect throw CommonManagerConfigurationException");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#SimpleCommonManager()}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerDefaultNSCommonManagerConfigurationException4() throws Exception {
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "SimpleCommonManager" + File.separator
            + "DefaultNS4.xml");

        try {
            new SimpleCommonManager();
            fail("expect throw CommonManagerConfigurationException");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#SimpleCommonManager()}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerDefaultNSCommonManagerConfigurationException5() throws Exception {
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "SimpleCommonManager" + File.separator
            + "DefaultNS5.xml");

        try {
            new SimpleCommonManager();
            fail("expect throw CommonManagerConfigurationException");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#SimpleCommonManager()}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerDefaultNSCommonManagerConfigurationException6() throws Exception {
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "SimpleCommonManager" + File.separator
            + "DefaultNS6.xml");

        try {
            new SimpleCommonManager();
            fail("expect throw CommonManagerConfigurationException");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#SimpleCommonManager()}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerDefaultNSCommonManagerConfigurationException7() throws Exception {
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "SimpleCommonManager" + File.separator
            + "DefaultNS7.xml");

        try {
            new SimpleCommonManager();
            fail("expect throw CommonManagerConfigurationException");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#SimpleCommonManager()}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerDefaultNSCommonManagerConfigurationException8() throws Exception {
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "SimpleCommonManager" + File.separator
            + "DefaultNS8.xml");

        try {
            new SimpleCommonManager();
            fail("expect throw CommonManagerConfigurationException");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#SimpleCommonManager()}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerDefaultNSCommonManagerConfigurationException9() throws Exception {
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "SimpleCommonManager" + File.separator
            + "DefaultNS9.xml");

        try {
            new SimpleCommonManager();
            fail("expect throw CommonManagerConfigurationException");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#SimpleCommonManager()}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerDefaultNSCommonManagerConfigurationException10() throws Exception {
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "SimpleCommonManager" + File.separator
            + "DefaultNS10.xml");

        try {
            new SimpleCommonManager();
            fail("expect throw CommonManagerConfigurationException");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#SimpleCommonManager()}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerDefaultNSCommonManagerConfigurationException11() throws Exception {
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "SimpleCommonManager" + File.separator
            + "DefaultNS11.xml");

        try {
            new SimpleCommonManager();
            fail("expect throw CommonManagerConfigurationException");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#SimpleCommonManager(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerCustomizedNSIAE1() throws Exception {
        try {
            new SimpleCommonManager(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#SimpleCommonManager(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerCustomizedNSIAE2() throws Exception {
        try {
            new SimpleCommonManager("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#SimpleCommonManager(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerCustomizedNSIAE3() throws Exception {
        try {
            new SimpleCommonManager("  ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#SimpleCommonManager(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerCustomizedNSCommonManagerConfigurationException1() throws Exception {
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "SimpleCommonManager" + File.separator
            + "DefaultNS1.xml");

        try {
            new SimpleCommonManager("com.topcoder.timetracker.common.SimpleCommonManager");
            fail("expect throw CommonManagerConfigurationException");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#SimpleCommonManager(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerCustomizedNSCommonManagerConfigurationException2() throws Exception {
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "SimpleCommonManager" + File.separator
            + "DefaultNS2.xml");

        try {
            new SimpleCommonManager("com.topcoder.timetracker.common.SimpleCommonManager");
            fail("expect throw CommonManagerConfigurationException");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#SimpleCommonManager(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerCustomizedNSCommonManagerConfigurationException3() throws Exception {
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "SimpleCommonManager" + File.separator
            + "DefaultNS3.xml");

        try {
            new SimpleCommonManager("com.topcoder.timetracker.common.SimpleCommonManager");
            fail("expect throw CommonManagerConfigurationException");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#SimpleCommonManager(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerCustomizedNSCommonManagerConfigurationException4() throws Exception {
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "SimpleCommonManager" + File.separator
            + "DefaultNS4.xml");

        try {
            new SimpleCommonManager("com.topcoder.timetracker.common.SimpleCommonManager");
            fail("expect throw CommonManagerConfigurationException");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#SimpleCommonManager(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerCustomizedNSCommonManagerConfigurationException5() throws Exception {
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "SimpleCommonManager" + File.separator
            + "DefaultNS5.xml");

        try {
            new SimpleCommonManager("com.topcoder.timetracker.common.SimpleCommonManager");
            fail("expect throw CommonManagerConfigurationException");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#SimpleCommonManager(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerCustomizedNSCommonManagerConfigurationException6() throws Exception {
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "SimpleCommonManager" + File.separator
            + "DefaultNS6.xml");

        try {
            new SimpleCommonManager("com.topcoder.timetracker.common.SimpleCommonManager");
            fail("expect throw CommonManagerConfigurationException");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#SimpleCommonManager(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerCustomizedNSCommonManagerConfigurationException7() throws Exception {
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "SimpleCommonManager" + File.separator
            + "DefaultNS7.xml");

        try {
            new SimpleCommonManager("com.topcoder.timetracker.common.SimpleCommonManager");
            fail("expect throw CommonManagerConfigurationException");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#SimpleCommonManager(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerCustomizedNSCommonManagerConfigurationException8() throws Exception {
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "SimpleCommonManager" + File.separator
            + "DefaultNS8.xml");

        try {
            new SimpleCommonManager("com.topcoder.timetracker.common.SimpleCommonManager");
            fail("expect throw CommonManagerConfigurationException");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#SimpleCommonManager(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerCustomizedNSCommonManagerConfigurationException9() throws Exception {
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "SimpleCommonManager" + File.separator
            + "DefaultNS9.xml");

        try {
            new SimpleCommonManager("com.topcoder.timetracker.common.SimpleCommonManager");
            fail("expect throw CommonManagerConfigurationException");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#SimpleCommonManager(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerCustomizedNSCommonManagerConfigurationException10() throws Exception {
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "SimpleCommonManager" + File.separator
            + "DefaultNS10.xml");

        try {
            new SimpleCommonManager("com.topcoder.timetracker.common.SimpleCommonManager");
            fail("expect throw CommonManagerConfigurationException");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#SimpleCommonManager(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerCustomizedNSCommonManagerConfigurationException11() throws Exception {
        FailureTestHelper.loadNamesapces("failuretests" + File.separator + "SimpleCommonManager" + File.separator
            + "DefaultNS11.xml");

        try {
            new SimpleCommonManager("com.topcoder.timetracker.common.SimpleCommonManager");
            fail("expect throw CommonManagerConfigurationException");
        } catch (CommonManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link SimpleCommonManager#SimpleCommonManager(com.topcoder.timetracker.common.PaymentTermDAO, int))}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerNotUseNSIAE1() throws Exception {
        try {
            new SimpleCommonManager(null, -1);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link SimpleCommonManager#SimpleCommonManager(com.topcoder.timetracker.common.PaymentTermDAO, int))}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerNotUseNSIAE2() throws Exception {
        try {
            new SimpleCommonManager(paymentTermDAO, -100);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link SimpleCommonManager#SimpleCommonManager(com.topcoder.timetracker.common.PaymentTermDAO, int))}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSimpleCommonManagerNotUseNSIAE3() throws Exception {
        try {
            new SimpleCommonManager(paymentTermDAO, -100);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermNullPaymentTerm() throws Exception {
        try {
            commonManager.addPaymentTerm(null);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermNegativeTerm() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setTerm(-1);
        try {
            commonManager.addPaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermZeroTerm() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setTerm(0);
        try {
            commonManager.addPaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermNullCreationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setCreationUser(null);
        try {
            commonManager.addPaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermEmptyCreationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setCreationUser("");
        try {
            commonManager.addPaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermTrimmedEmptyCreationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setCreationUser("  ");
        try {
            commonManager.addPaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermTooLongCreationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setCreationUser(FailureTestHelper.LONG_STRING);
        try {
            commonManager.addPaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermNullDescription() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setDescription(null);
        try {
            commonManager.addPaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermEmptyDescription() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setDescription("");
        try {
            commonManager.addPaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermTrimmedEmptyDescription() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setDescription("  ");
        try {
            commonManager.addPaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermTooLongDescription() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setDescription(FailureTestHelper.LONG_STRING);
        try {
            commonManager.addPaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermEmptyModificationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setModificationUser("");
        try {
            commonManager.addPaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermTrimmedEmptyModificationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setModificationUser("  ");
        try {
            commonManager.addPaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermTooLongModificationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setModificationUser(FailureTestHelper.LONG_STRING);
        try {
            commonManager.addPaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#addPaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPaymentTermPaymentTermDAOException() throws Exception {
        try {
            commonManager.addPaymentTerm(createPaymentTerm());
            fail("expect throw PaymentTermDAOException");
        } catch (PaymentTermDAOException e) {
            // expected
        }
    }

    // updatePaymenTerm
    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermNullPaymentTerm() throws Exception {
        try {
            commonManager.updatePaymentTerm(null);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermNegativeId() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setId(-1);
        try {
            commonManager.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermZeroId() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setId(0);
        try {
            commonManager.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermNegativeTerm() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setTerm(-1);
        try {
            commonManager.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermZeroTerm() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setTerm(0);
        try {
            commonManager.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermNullCreationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setCreationUser(null);
        try {
            commonManager.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermEmptyCreationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setCreationUser("");
        try {
            commonManager.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermTrimmedEmptyCreationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setCreationUser("  ");
        try {
            commonManager.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermTooLongCreationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setCreationUser(FailureTestHelper.LONG_STRING);
        try {
            commonManager.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermNullDescription() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setDescription(null);
        try {
            commonManager.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermEmptyDescription() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setDescription("");
        try {
            commonManager.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermTrimmedEmptyDescription() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setDescription("  ");
        try {
            commonManager.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermTooLongDescription() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setDescription(FailureTestHelper.LONG_STRING);
        try {
            commonManager.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermNullModificationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setModificationUser(null);
        try {
            commonManager.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermEmptyModificationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setModificationUser("");
        try {
            commonManager.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermTrimmedEmptyModificationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setModificationUser("  ");
        try {
            commonManager.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermTooLongModificationUser() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setModificationUser(FailureTestHelper.LONG_STRING);
        try {
            commonManager.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermNullCreationDate() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setCreationDate(null);
        try {
            commonManager.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermNullModificationDate() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setModificationDate(null);
        try {
            commonManager.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermModificationDateBeforeCreationDate() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setModificationDate(new Date(paymentTerm.getCreationDate().getTime() - 100000));
        try {
            commonManager.updatePaymentTerm(paymentTerm);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#updatePaymentTerm(PaymentTerm)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePaymentTermPaymentTermDAOException() throws Exception {
        PaymentTerm paymentTerm = createPaymentTerm();
        paymentTerm.setCreationDate(new Date(paymentTerm.getCreationDate().getTime() - 1000));
        try {
            commonManager.updatePaymentTerm(paymentTerm);
            fail("expect throw PaymentTermDAOException");
        } catch (PaymentTermDAOException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#retrievePaymentTerm(long)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrievePaymentTermNegativeId() throws Exception {
        try {
            commonManager.retrievePaymentTerm(-1);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#retrievePaymentTerm(long)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrievePaymentTermZeroId() throws Exception {
        try {
            commonManager.retrievePaymentTerm(0);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#retrievePaymentTerm(long)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrievePaymentTermPaymentTermDAOException() throws Exception {
        try {
            commonManager.retrievePaymentTerm(100);
            fail("expect throw PaymentTermDAOException.");
        } catch (PaymentTermDAOException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#retrievePaymentTerm(long[])}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrievePaymentTermsNegativeId() throws Exception {
        try {
            commonManager.retrievePaymentTerms(new long[] {-1});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#retrievePaymentTerm(long[])}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrievePaymentTermsZeroId() throws Exception {
        try {
            commonManager.retrievePaymentTerms(new long[] {0});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#retrievePaymentTerm(long[])}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrievePaymentTermsPaymentTermDAOException() throws Exception {
        try {
            commonManager.retrievePaymentTerms(new long[] {100});
            fail("expect throw PaymentTermDAOException.");
        } catch (PaymentTermDAOException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#retrieveAllPaymentTerms()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllPaymentTermsPaymentTermDAOException() throws Exception {
        try {
            commonManager.retrieveAllPaymentTerms();
            fail("expect throw PaymentTermDAOException.");
        } catch (PaymentTermDAOException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#retrieveRecentlyCreatedPaymentTerms()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrieveARecentlyCreatedPaymentTermsTermDAOException() throws Exception {
        try {
            commonManager.retrieveRecentlyCreatedPaymentTerms();
            fail("expect throw PaymentTermDAOException.");
        } catch (PaymentTermDAOException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#retrieveRecentlyCreatedPaymentTerms(int)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrieveARecentlyCreatedPaymentTermsWithArgIAE1() throws Exception {
        try {
            commonManager.retrieveRecentlyCreatedPaymentTerms(-100);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#retrieveRecentlyCreatedPaymentTerms(int)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrieveARecentlyCreatedPaymentTermsWithArgIAE2() throws Exception {
        try {
            commonManager.retrieveRecentlyCreatedPaymentTerms(0);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#retrieveRecentlyCreatedPaymentTerms(int)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrieveARecentlyCreatedPaymentTermsWithArgPaymentTermDAOException() throws Exception {
        try {
            commonManager.retrieveRecentlyCreatedPaymentTerms(10);
            fail("expect throw PaymentTermDAOException.");
        } catch (PaymentTermDAOException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#retrieveRecentlyModifiedPaymentTerms()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrieveARecentlyModifiedPaymentTermsTermDAOException() throws Exception {
        try {
            commonManager.retrieveRecentlyModifiedPaymentTerms();
            fail("expect throw PaymentTermDAOException.");
        } catch (PaymentTermDAOException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#retrieveRecentlyModifiedPaymentTerms(int)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrieveARecentlyModifiedPaymentTermsWithArgIAE1() throws Exception {
        try {
            commonManager.retrieveRecentlyModifiedPaymentTerms(-100);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#retrieveRecentlyModifiedPaymentTerms(int)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrieveARecentlyModifiedPaymentTermsWithArgIAE2() throws Exception {
        try {
            commonManager.retrieveRecentlyModifiedPaymentTerms(0);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#retrieveRecentlyModifiedPaymentTerms(int)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrieveARecentlyModifiedPaymentTermsWithArgPaymentTermDAOException() throws Exception {
        try {
            commonManager.retrieveRecentlyModifiedPaymentTerms(10);
            fail("expect throw PaymentTermDAOException.");
        } catch (PaymentTermDAOException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#deletePaymentTerm(long)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeletePaymentTermNegativeId() throws Exception {
        try {
            commonManager.deletePaymentTerm(-1);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#deletePaymentTerm(long)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeletePaymentTermZeroId() throws Exception {
        try {
            commonManager.deletePaymentTerm(0);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#deletePaymentTerm(long)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeletePaymentTermPaymentTermDAOException() throws Exception {
        try {
            commonManager.deletePaymentTerm(1);
            fail("expect throw PaymentTermDAOException");
        } catch (PaymentTermDAOException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#deletePaymentTerms(long[])}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeletePaymentTermsNegativeId() throws Exception {
        try {
            commonManager.deletePaymentTerms(new long[] {-1});
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#deletePaymentTerms(long[])}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeletePaymentTermsZeroId() throws Exception {
        try {
            commonManager.deletePaymentTerms(new long[] {0});
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link SimpleCommonManager#deletePaymentTerms(long[])}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeletePaymentTermsPaymentTermDAOException() throws Exception {
        try {
            commonManager.deletePaymentTerms(new long[] {1});
            fail("expect throw PaymentTermDAOException");
        } catch (PaymentTermDAOException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Failure test for {@link SimpleCommonManager#deleteAllPaymentTerms()} method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteAllPaymentTermsPaymentTErmDAOException() throws Exception {
        try {
            commonManager.deleteAllPaymentTerms();
            fail("expect throw PaymentTermDAOException");
        } catch (PaymentTermDAOException e) {
            // expected
        }
    }
}
