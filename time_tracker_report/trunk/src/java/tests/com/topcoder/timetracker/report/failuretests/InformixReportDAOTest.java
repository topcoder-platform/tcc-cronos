/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.failuretests;

import com.topcoder.timetracker.report.ReportConfigException;
import com.topcoder.timetracker.report.informix.InformixReportDAO;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure test for <code>InformixReportDAO</code>.
 *
 * @author enefem21
 * @version 3.1
 */
public class InformixReportDAOTest extends TestCase {

    /** Unit under test. */
    private InformixReportDAO informixReportDAO;

    /**
     * <p>
     * Return the suite for this unit test.
     * </p>
     *
     * @return the suite
     */
    public static Test suite() {
        return new TestSuite(InformixReportDAOTest.class);
    }

    /**
     * Sets the unit test up.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {

        TestHelper.loadConfiguration("failure/config.xml");

        informixReportDAO = new InformixReportDAO("InformixReportDAO");

        super.setUp();
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

    public void testInformixReportDAONotAvailableNamespace() {
        try {
            new InformixReportDAO();
            fail("Should throw ReportConfigException");
        } catch (ReportConfigException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: there is no reportsSearchBuilders. Expect:
     * <code>ReportConfigException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixReportDAONoReportsSearchBuilders() throws Exception {
        TestHelper.loadConfiguration("failure/config_no_reportsSearchBuilders.xml");

        try {
            new InformixReportDAO();
            fail("Should throw ReportConfigException");
        } catch (ReportConfigException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: some reportsSearchBuilders are missing. Expect:
     * <code>ReportConfigException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixReportDAOMissSomeReportsSearchBuilders() throws Exception {
        TestHelper.loadConfiguration("failure/config_miss_some_reportsSearchBuilders.xml");

        try {
            new InformixReportDAO();
            fail("Should throw ReportConfigException");
        } catch (ReportConfigException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: some reportsSearchBuilders are missing. Expect:
     * <code>ReportConfigException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixReportDAOMissSomeReportsSearchBuilders2() throws Exception {
        TestHelper.loadConfiguration("failure/config_miss_some_reportsSearchBuilders2.xml");

        try {
            new InformixReportDAO();
            fail("Should throw ReportConfigException");
        } catch (ReportConfigException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: some reportsSearchBuilders are missing. Expect:
     * <code>ReportConfigException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixReportDAOMissSomeReportsSearchBuilders3() throws Exception {
        TestHelper.loadConfiguration("failure/config_miss_some_reportsSearchBuilders3.xml");

        try {
            new InformixReportDAO();
            fail("Should throw ReportConfigException");
        } catch (ReportConfigException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: some reportsSearchBuilders are wrong class. Expect:
     * <code>ReportConfigException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixReportDAOReportBuilderWrongClass() throws Exception {
        TestHelper.loadConfiguration("failure/config_reportBuilder_wrong_class.xml");

        try {
            new InformixReportDAO();
            fail("Should throw ReportConfigException");
        } catch (ReportConfigException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: namespace is null. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixReportDAOWithNamespaceNull() throws Exception {
        try {
            new InformixReportDAO(null);
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
    public void testInformixReportDAOWithNamespaceEmpty() throws Exception {
        try {
            new InformixReportDAO("   \n");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getExpenseEntriesReport</code> for failure. Condition: the sortingColumns contains null.
     * Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetExpenseEntriesReportContainNull() throws Exception {
        try {
            informixReportDAO.getExpenseEntriesReport(null, new String[] {null}, new boolean[] {false});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getExpenseEntriesReport</code> for failure. Condition: the sortingColumns contains empty
     * string. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetExpenseEntriesReportContainEmptyString() throws Exception {
        try {
            informixReportDAO.getExpenseEntriesReport(null, new String[] {"  \n"}, new boolean[] {false});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getExpenseEntriesReport</code> for failure. Condition: the sortingColumns and ascendingOrders
     * have different length. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetExpenseEntriesReportNotSameLength() throws Exception {
        try {
            informixReportDAO.getExpenseEntriesReport(null, new String[] {"test"}, new boolean[0]);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getExpenseEntriesReport</code> for failure. Condition: the sortingColumns and ascendingOrders
     * have different length. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetExpenseEntriesReportNotSameLength2() throws Exception {
        try {
            informixReportDAO.getExpenseEntriesReport(null, new String[] {"test"}, null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getExpenseEntriesReport</code> for failure. Condition: the sortingColumns and ascendingOrders
     * have different length. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetExpenseEntriesReportNotSameLength3() throws Exception {
        try {
            informixReportDAO.getExpenseEntriesReport(null, null, new boolean[] {false});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getFixedBillingEntriesReport</code> for failure. Condition: the sortingColumns contains null.
     * Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetFixedBillingEntriesReportContainNull() throws Exception {
        try {
            informixReportDAO.getFixedBillingEntriesReport(null, new String[] {null}, new boolean[] {false});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getFixedBillingEntriesReport</code> for failure. Condition: the sortingColumns contains empty
     * string. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetFixedBillingEntriesReportContainEmptyString() throws Exception {
        try {
            informixReportDAO.getFixedBillingEntriesReport(null, new String[] {"  \n"}, new boolean[] {false});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getFixedBillingEntriesReport</code> for failure. Condition: the sortingColumns and
     * ascendingOrders have different length. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetFixedBillingEntriesReportNotSameLength() throws Exception {
        try {
            informixReportDAO.getFixedBillingEntriesReport(null, new String[] {"test"}, new boolean[0]);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getFixedBillingEntriesReport</code> for failure. Condition: the sortingColumns and
     * ascendingOrders have different length. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetFixedBillingEntriesReportNotSameLength2() throws Exception {
        try {
            informixReportDAO.getFixedBillingEntriesReport(null, new String[] {"test"}, null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getFixedBillingEntriesReport</code> for failure. Condition: the sortingColumns and
     * ascendingOrders have different length. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetFixedBillingEntriesReportNotSameLength3() throws Exception {
        try {
            informixReportDAO.getFixedBillingEntriesReport(null, null, new boolean[] {false});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getTimeEntriesReport</code> for failure. Condition: the sortingColumns contains null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetTimeEntriesReportContainNull() throws Exception {
        try {
            informixReportDAO.getTimeEntriesReport(null, new String[] {null}, new boolean[] {false});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getTimeEntriesReport</code> for failure. Condition: the sortingColumns contains empty string.
     * Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetTimeEntriesReportContainEmptyString() throws Exception {
        try {
            informixReportDAO.getTimeEntriesReport(null, new String[] {"  \n"}, new boolean[] {false});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getTimeEntriesReport</code> for failure. Condition: the sortingColumns and ascendingOrders have
     * different length. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetTimeEntriesReportNotSameLength() throws Exception {
        try {
            informixReportDAO.getTimeEntriesReport(null, new String[] {"test"}, new boolean[0]);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getTimeEntriesReport</code> for failure. Condition: the sortingColumns and ascendingOrders have
     * different length. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetTimeEntriesReportNotSameLength2() throws Exception {
        try {
            informixReportDAO.getTimeEntriesReport(null, new String[] {"test"}, null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getTimeEntriesReport</code> for failure. Condition: the sortingColumns and ascendingOrders have
     * different length. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetTimeEntriesReportNotSameLength3() throws Exception {
        try {
            informixReportDAO.getTimeEntriesReport(null, null, new boolean[] {false});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getReport</code> for failure. Condition: type is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetReportTypeIsNull() throws Exception {
        try {
            informixReportDAO.getReport(null, null, new String[] {"test"}, new boolean[] {false});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getReport</code> for failure. Condition: type is empty string. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetReportTypeIsEmptyString() throws Exception {
        try {
            informixReportDAO.getReport(null, null, new String[] {"  "}, new boolean[] {false});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getReport</code> for failure. Condition: the sortingColumns contains null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetReportContainNull() throws Exception {
        try {
            informixReportDAO.getReport(InformixReportDAO.TIME_ENTRIES, null, new String[] {null},
                new boolean[] {false});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getReport</code> for failure. Condition: the sortingColumns contains empty string. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetReportContainEmptyString() throws Exception {
        try {
            informixReportDAO.getReport(InformixReportDAO.TIME_ENTRIES, null, new String[] {"  \n"},
                new boolean[] {false});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getReport</code> for failure. Condition: the sortingColumns and ascendingOrders have different
     * length. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetReportNotSameLength() throws Exception {
        try {
            informixReportDAO.getReport(InformixReportDAO.TIME_ENTRIES, null, new String[] {"test"},
                new boolean[0]);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getReport</code> for failure. Condition: the sortingColumns and ascendingOrders have different
     * length. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetReportNotSameLength2() throws Exception {
        try {
            informixReportDAO.getReport(InformixReportDAO.TIME_ENTRIES, null, new String[] {"test"}, null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getReport</code> for failure. Condition: the sortingColumns and ascendingOrders have different
     * length. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetReportNotSameLength3() throws Exception {
        try {
            informixReportDAO.getReport(InformixReportDAO.TIME_ENTRIES, null, null, new boolean[] {false});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
