/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

/**
 * This class contains the unit tests unique to {@link AbstractReport}.
 * <p/>
 * The functionality of all {@link AbstractReport} subclasses is tested in their individual test classes.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AbstractReportTest extends AbstractTestcaseForReports {
    /**
     * This is the value returned as rendering result and used as format value by the anonymous inner {@link
     * AbstractReport} instances created by this test class.
     */
    protected static final String STRING = "String";

    /**
     * This method tests {@link AbstractReport#AbstractReport(ReportCategory, String)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: category is <tt>null</tt>
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailNullCategory() throws Exception {
        try {
            new AbstractReport(null, STRING) {

                protected String executeReport(final ReportConfiguration config) {
                    return STRING;
                }
            };
            fail("should throw");
        } catch (NullPointerException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link AbstractReport#AbstractReport(ReportCategory, String)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: format is <tt>null</tt>
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailNullFormat() throws Exception {
        try {
            new AbstractReport(ReportCategory.EXPENSE, null) {

                protected String executeReport(final ReportConfiguration config) {
                    return STRING;
                }
            };
            fail("should throw");
        } catch (NullPointerException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link AbstractReport#AbstractReport(ReportCategory, String)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: format is empty String
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailEmptyFormat() throws Exception {
        try {
            new AbstractReport(ReportCategory.EXPENSE, null) {

                protected String executeReport(final ReportConfiguration config) {
                    return STRING;
                }
            };
            fail("should throw");
        } catch (NullPointerException expected) {
            //expected
        }
    }

    /**
     * This method returns an instance of the {@link AbstractReport} implementation to be tested.
     *
     * @return the {@link AbstractReport} implementation to be tested, a dummy anonymous inner class extending
     *         AbstractReport in this case
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    protected AbstractReport getImplementation() throws Exception {
        return new AbstractReport(ReportCategory.EXPENSE, STRING) {

            protected String executeReport(final ReportConfiguration config) {
                if (config == null) {
                    throw new NullPointerException("The parameter named [config] was null.");
                }

                return STRING;
            }
        };
    }

    /**
     * This method calls the constructor of the to-be-tested {@link AbstractReport} subclass, a dummy anonymous inner
     * class extending AbstractReport in this case.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    protected void callConstructor() throws Exception {
        new AbstractReport(ReportCategory.EXPENSE, STRING) {

            protected String executeReport(final ReportConfiguration config) {
                return STRING;
            }
        };
    }

    /**
     * This method returns the relative path to the directory that contains the expected render results as XML files
     * here.
     *
     * @return the relative path to the directory containing the expected render result files
     */
    protected String getRelativePathToExpectedResultsDir() {
        return "expectedDummyAbstractReportResults/";
    }
}