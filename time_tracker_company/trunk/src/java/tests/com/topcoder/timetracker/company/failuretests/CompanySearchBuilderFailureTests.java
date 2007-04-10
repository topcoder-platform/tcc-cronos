/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.failuretests;

import java.util.Date;

import com.topcoder.timetracker.company.CompanySearchBuilder;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link CompanySearchBuilder}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class CompanySearchBuilderFailureTests extends TestCase {

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder}</code> instance.
     * </p>
     */
    private CompanySearchBuilder companySearchBuilder = new CompanySearchBuilder();

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#hasCompanyName(String)}</code> method.
     * </p>
     */
    public void testHasCompanyName_NullComanyName() {
        try {
            companySearchBuilder.hasCompanyName(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#hasCompanyName(String)}</code> method.
     * </p>
     */
    public void testHasCompanyName_EmptyComanyName() {
        try {
            companySearchBuilder.hasCompanyName("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#hasCompanyName(String)}</code> method.
     * </p>
     */
    public void testHasCompanyName_TrimmedEmptyComanyName() {
        try {
            companySearchBuilder.hasCompanyName(" ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#hasContactFirstName(String)}</code> method.
     * </p>
     */
    public void testHasContactFirstName_NullContactFirstName() {
        try {
            companySearchBuilder.hasContactFirstName(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#hasContactFirstName(String)}</code> method.
     * </p>
     */
    public void testHasContactFirstName_EmptyContactFirstName() {
        try {
            companySearchBuilder.hasContactFirstName("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#hasContactFirstName(String)}</code> method.
     * </p>
     */
    public void testHasContactFirstName_TrimmedEmptyContactFirstName() {
        try {
            companySearchBuilder.hasContactFirstName(" ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#hasContactLastName(String)}</code> method.
     * </p>
     */
    public void testHasContactLastName_NullContactLastName() {
        try {
            companySearchBuilder.hasContactLastName(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#hasContactLastName(String)}</code> method.
     * </p>
     */
    public void testHasContactLastName_EmptyContactLastName() {
        try {
            companySearchBuilder.hasContactLastName("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#hasContactLastName(String)}</code> method.
     * </p>
     */
    public void testHasContactLastName_TrimmedEmptyContactLastName() {
        try {
            companySearchBuilder.hasContactLastName(" ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#hasContactPhoneNumber(String)}</code> method.
     * </p>
     */
    public void testHasContactPhoneNumber_NullContactPhoneNumber() {
        try {
            companySearchBuilder.hasContactPhoneNumber(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#hasContactPhoneNumber(String)}</code> method.
     * </p>
     */
    public void testHasContactPhoneNumber_EmptyContactPhoneNumber() {
        try {
            companySearchBuilder.hasContactPhoneNumber("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#hasContactPhoneNumber(String)}</code> method.
     * </p>
     */
    public void testHasContactPhoneNumber_TrimmedEmptyContactPhoneNumber() {
        try {
            companySearchBuilder.hasContactPhoneNumber(" ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#hasContactEmail(String)}</code> method.
     * </p>
     */
    public void testHasContactEmail_NullContactEmail() {
        try {
            companySearchBuilder.hasContactEmail(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#hasContactEmail(String)}</code> method.
     * </p>
     */
    public void testHasContactEmail_EmptyContactEmail() {
        try {
            companySearchBuilder.hasContactEmail("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#hasContactEmail(String)}</code> method.
     * </p>
     */
    public void testHasContactEmail_TrimmedEmptyContactEmail() {
        try {
            companySearchBuilder.hasContactEmail(" ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#hasStreetAddress(String)}</code> method.
     * </p>
     */
    public void testHasStreetAddress_NullStreetAddress() {
        try {
            companySearchBuilder.hasStreetAddress(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#hasStreetAddress(String)}</code> method.
     * </p>
     */
    public void testHasStreetAddress_EmptyStreetAddress() {
        try {
            companySearchBuilder.hasStreetAddress("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#hasStreetAddress(String)}</code> method.
     * </p>
     */
    public void testHasStreetAddress_TrimmedEmptyStreetAddress() {
        try {
            companySearchBuilder.hasStreetAddress(" ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#hasCity(String)}</code> method.
     * </p>
     */
    public void testHasCity_NullCity() {
        try {
            companySearchBuilder.hasCity(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#hasCity(String)}</code> method.
     * </p>
     */
    public void testHasCity_EmptyCity() {
        try {
            companySearchBuilder.hasCity("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#hasCity(String)}</code> method.
     * </p>
     */
    public void testHasCity_TrimmedEmptyCity() {
        try {
            companySearchBuilder.hasCity(" ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#hasState(String)}</code> method.
     * </p>
     */
    public void testHasState_NullState() {
        try {
            companySearchBuilder.hasState(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#hasState(String)}</code> method.
     * </p>
     */
    public void testHasState_EmptyState() {
        try {
            companySearchBuilder.hasState("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#hasState(String)}</code> method.
     * </p>
     */
    public void testHasState_TrimmedEmptyState() {
        try {
            companySearchBuilder.hasState(" ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#hasZipCode(String)}</code> method.
     * </p>
     */
    public void testHasZipCode_NullZipCode() {
        try {
            companySearchBuilder.hasZipCode(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#hasZipCode(String)}</code> method.
     * </p>
     */
    public void testHasZipCode_EmptyZipCode() {
        try {
            companySearchBuilder.hasZipCode("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#hasZipCode(String)}</code> method.
     * </p>
     */
    public void testHasZipCode_TrimmedEmptyZipCode() {
        try {
            companySearchBuilder.hasZipCode(" ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#createdByUser(String)}</code> method.
     * </p>
     */
    public void testCreatedByUser_NullUserName() {
        try {
            companySearchBuilder.createdByUser(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#createdByUser(String)}</code> method.
     * </p>
     */
    public void testCreatedByUser_EmptyUserName() {
        try {
            companySearchBuilder.createdByUser("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#createdByUser(String)}</code> method.
     * </p>
     */
    public void testCreatedByUser_TrimmedEmptyUserName() {
        try {
            companySearchBuilder.createdByUser(" ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#modifiedByUser(String)}</code> method.
     * </p>
     */
    public void testModifiedByUser_NullUserName() {
        try {
            companySearchBuilder.modifiedByUser(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#modifiedByUser(String)}</code> method.
     * </p>
     */
    public void testModifiedByUser_EmptyUserName() {
        try {
            companySearchBuilder.modifiedByUser("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#modifiedByUser(String)}</code> method.
     * </p>
     */
    public void testModifiedByUser_TrimmedEmptyUserName() {
        try {
            companySearchBuilder.modifiedByUser(" ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#createdWithinDateRange(Date, Date)}</code> method.
     * </p>
     */
    public void testCreatedWithinDateRange_BothNull() {
        try {
            companySearchBuilder.createdWithinDateRange(null, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#createdWithinDateRange(Date, Date)}</code> method.
     * </p>
     */
    public void testCreatedWithinDateRange_EndBeforeStart() {
        try {
            companySearchBuilder.createdWithinDateRange(new Date(), new Date(System.currentTimeMillis() - 10000));
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#modifiedWithinDateRange(Date, Date)}</code> method.
     * </p>
     */
    public void testModifiedWithinDateRange_BothNull() {
        try {
            companySearchBuilder.modifiedWithinDateRange(null, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link CompanySearchBuilder#modifiedWithinDateRange(Date, Date)}</code> method.
     * </p>
     */
    public void testModifiedWithinDateRange_EndBeforeStart() {
        try {
            companySearchBuilder.modifiedWithinDateRange(new Date(), new Date(System.currentTimeMillis() - 10000));
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
