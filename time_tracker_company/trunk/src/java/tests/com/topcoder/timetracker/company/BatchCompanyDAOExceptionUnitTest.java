/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company;

import com.topcoder.util.errorhandling.BaseException;

import junit.framework.TestCase;


/**
 * <p>
 * Unit test cases for <code>BatchCompanyDAOException</code>.
 * </p>
 *
 * <p>
 * This class is pretty simple. The test cases simply verifies the exception can be instantiated with the error message
 * and cause properly propagated, and that it comes with correct inheritance.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class BatchCompanyDAOExceptionUnitTest extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * <p>
     * The problem companies used for testing.
     * </p>
     */
    private static final Company[] COMPANIES = new Company[] {new Company()};

    /**
     * <p>
     * The problem causes used for testing.
     * </p>
     */
    private static final Throwable[] CAUSES = new Throwable[] {new IllegalArgumentException("test")};

    /**
     * <p>
     * Creation test.
     * </p>
     *
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testBatchCompanyDAOException1() {
        BatchCompanyDAOException ce = new BatchCompanyDAOException(ERROR_MESSAGE, COMPANIES);

        assertNotNull("Unable to instantiate BatchCompanyDAOException.", ce);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, ce.getMessage());

        Company[] companies = ce.getProblemCompanies();
        assertEquals("Problem companies is not properly set.", COMPANIES.length, companies.length);

        for (int i = 0; i < companies.length; i++) {
            assertEquals("Problem companies is not properly set.", COMPANIES[i], companies[i]);
        }

        Throwable[] causes = ce.getCauses();
        assertEquals("Problem causes is not properly set.", COMPANIES.length, causes.length);

        for (int i = 0; i < causes.length; i++) {
            assertEquals("Problem causes is not properly set.", null, causes[i]);
        }
    }

    /**
     * <p>
     * Creation test.
     * </p>
     *
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testBatchCompanyDAOException2() {
        BatchCompanyDAOException ce = new BatchCompanyDAOException(ERROR_MESSAGE, CAUSES, COMPANIES);

        assertNotNull("Unable to instantiate BatchCompanyDAOException.", ce);

        Company[] companies = ce.getProblemCompanies();
        assertEquals("Problem companies is not properly set.", COMPANIES.length, companies.length);

        for (int i = 0; i < companies.length; i++) {
            assertEquals("Problem companies is not properly set.", COMPANIES[i], companies[i]);
        }

        Throwable[] causes = ce.getCauses();
        assertEquals("Problem causes is not properly set.", CAUSES.length, causes.length);

        for (int i = 0; i < causes.length; i++) {
            assertEquals("Problem causes is not properly set.", CAUSES[i], causes[i]);
        }
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies BatchCompanyDAOException subclasses BaseException.
     * </p>
     */
    public void testBatchCompanyDAOExceptionInheritance1() {
        assertTrue("BatchCompanyDAOException does not subclass BaseException.",
            new BatchCompanyDAOException(ERROR_MESSAGE, COMPANIES) instanceof BaseException);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies BatchCompanyDAOException subclasses BaseException.
     * </p>
     */
    public void testBatchCompanyDAOExceptionInheritance2() {
        assertTrue("BatchCompanyDAOException does not subclass BaseException.",
            new BatchCompanyDAOException(ERROR_MESSAGE, CAUSES, COMPANIES) instanceof BaseException);
    }

    /**
     * <p>
     * Test the accuracy of method getProblemCompanies() to ensure the return value is shallow copied.
     * </p>
     */
    public void testGetProblemCompanies_ShallowCopyAccuracy() {
        BatchCompanyDAOException ce = new BatchCompanyDAOException(ERROR_MESSAGE, CAUSES, COMPANIES);

        // get the old one
        Company[] oldCompanies = ce.getProblemCompanies();

        // update it
        oldCompanies[0] = null;

        // re-check
        Company[] companies = ce.getProblemCompanies();
        assertEquals("Problem companies should be shallow copied.", COMPANIES.length, companies.length);

        for (int i = 0; i < companies.length; i++) {
            assertEquals("Problem companies should be shallow copied.", COMPANIES[i], companies[i]);
        }
    }
}
