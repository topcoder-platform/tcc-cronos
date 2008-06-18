/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validators.failuretests;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.registration.InsufficientDataException;
import com.topcoder.registration.entities.Contest;
import com.topcoder.registration.entities.ContestRole;
import com.topcoder.registration.entities.User;
import com.topcoder.registration.validators.OpenRegistrationValidator;

/**
 * <p>
 * Failure test cases for <code>OpenRegistrationValidator</code> class.
 * </p>
 *
 * @author romanoTC
 * @version 1.0
 */
public class OpenRegistrationValidatorFailureTest extends TestCase {

    /**
     * <p>
     * Failure test for constructor: {@link OpenRegistrationValidator#OpenRegistrationValidator(String)} <br>
     * Failure cause: Argument is null. <br>
     * Exception: IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_NullArgument() throws Exception {
        try {
            new OpenRegistrationValidator(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [bundleName] argument cannot be null/empty.", ex
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure test for constructor: {@link OpenRegistrationValidator#OpenRegistrationValidator(String)} <br>
     * Failure cause: Argument is empty. <br>
     * Exception: IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_EmptyArgument() throws Exception {
        try {
            new OpenRegistrationValidator("");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [bundleName] argument cannot be null/empty.", ex
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure test for constructor: {@link OpenRegistrationValidator#OpenRegistrationValidator(String)} <br>
     * Failure cause: Argument is trimmed empty. <br>
     * Exception: IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_TrimmedEmptyArgument() throws Exception {
        try {
            new OpenRegistrationValidator("  ");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [bundleName] argument cannot be null/empty.", ex
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure test for method: {@link OpenRegistrationValidator#validateRegistration(Contest, User, ContestRole)}
     * <br>
     * Failure cause: Contest argument is null. <br>
     * Exception: IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateRegistration_NullContest() throws Exception {
        OpenRegistrationValidator validator = new OpenRegistrationValidator("failure.Complete");

        try {
            validator.validateRegistration(null, new User(), createRole(0, 0));
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [contest] argument cannot be null.", ex
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure test for method: {@link OpenRegistrationValidator#validateRegistration(Contest, User, ContestRole)}
     * <br>
     * Failure cause: Role argument is null. <br>
     * Exception: IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateRegistration_NullRole() throws Exception {
        OpenRegistrationValidator validator = new OpenRegistrationValidator("failure.Complete");

        try {
            validator.validateRegistration(createContest(0, 0), new User(), null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [role] argument cannot be null.", ex.getMessage());
        }
    }

    /**
     * <p>
     * Failure test for method: {@link OpenRegistrationValidator#validateRegistration(Contest, User, ContestRole)}
     * <br>
     * Failure cause: Contest registration start date is not defined. <br>
     * Exception: InsufficientDataException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateRegistration_ContestRegistrationStartDateNotSet() throws Exception {
        OpenRegistrationValidator validator = new OpenRegistrationValidator("failure.Complete");
        Contest contest = new Contest();
        contest.setRegistrationEnd(new Date());

        try {
            validator.validateRegistration(contest, new User(), createRole(0, 0));
            fail("InsufficientDataException is expected.");
        } catch (InsufficientDataException ex) {
            assertEquals("Exception message is incorrect", "Contest registration start date is not defined.", ex
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure test for method: {@link OpenRegistrationValidator#validateRegistration(Contest, User, ContestRole)}
     * <br>
     * Failure cause: Contest registration end date is not defined. <br>
     * Exception: InsufficientDataException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateRegistration_ContestRegistrationEndDateNotSet() throws Exception {
        OpenRegistrationValidator validator = new OpenRegistrationValidator("failure.Complete");
        Contest contest = new Contest();
        contest.setRegistrationStart(new Date());

        try {
            validator.validateRegistration(contest, new User(), createRole(0, 0));
            fail("InsufficientDataException is expected.");
        } catch (InsufficientDataException ex) {
            assertEquals("Exception message is incorrect", "Contest registration end date is not defined.", ex
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure test for method: {@link OpenRegistrationValidator#validateRegistration(Contest, User, ContestRole)}
     * <br>
     * Failure cause: Contest role registration start offset is not defined. <br>
     * Exception: InsufficientDataException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateRegistration_ContestRoleRegistrationStartOffsetNotSet() throws Exception {
        OpenRegistrationValidator validator = new OpenRegistrationValidator("failure.Complete");

        try {
            validator.validateRegistration(createContest(-1000, 1000), new User(), createRole(-1, 0));
            fail("InsufficientDataException is expected.");
        } catch (InsufficientDataException ex) {
            assertEquals("Exception message is incorrect", "Contest role registration start offset is not defined.", ex
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure test for method: {@link OpenRegistrationValidator#validateRegistration(Contest, User, ContestRole)}
     * <br>
     * Failure cause: Contest role registration end offset is not defined. <br>
     * Exception: InsufficientDataException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateRegistration_ContestRoleRegistrationEndOffsetNotSet() throws Exception {
        OpenRegistrationValidator validator = new OpenRegistrationValidator("failure.Complete");

        try {
            validator.validateRegistration(createContest(-1000, 1000), new User(), createRole(10, -1));
            fail("InsufficientDataException is expected.");
        } catch (InsufficientDataException ex) {
            assertEquals("Exception message is incorrect", "Contest role registration end offset is not defined.", ex
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure test for method: {@link OpenRegistrationValidator#validateUnregistration(Contest, User, ContestRole)}
     * <br>
     * Failure cause: Contest argument is null. <br>
     * Exception: IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateUnregistration_NullContest() throws Exception {
        OpenRegistrationValidator validator = new OpenRegistrationValidator("failure.Complete");

        try {
            validator.validateUnregistration(null, new User(), createRole(0, 0));
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [contest] argument cannot be null.", ex
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure test for method: {@link OpenRegistrationValidator#validateUnregistration(Contest, User, ContestRole)}
     * <br>
     * Failure cause: Role argument is null. <br>
     * Exception: IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateUnregistration_NullRole() throws Exception {
        OpenRegistrationValidator validator = new OpenRegistrationValidator("failure.Complete");

        try {
            validator.validateUnregistration(createContest(0, 0), new User(), null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [role] argument cannot be null.", ex.getMessage());
        }
    }

    /**
     * <p>
     * Failure test for method: {@link OpenRegistrationValidator#validateUnregistration(Contest, User, ContestRole)}
     * <br>
     * Failure cause: Contest registration start date is not defined. <br>
     * Exception: InsufficientDataException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateUnregistration_ContestRegistrationStartDateNotSet() throws Exception {
        OpenRegistrationValidator validator = new OpenRegistrationValidator("failure.Complete");
        Contest contest = new Contest();
        contest.setRegistrationEnd(new Date());

        try {
            validator.validateUnregistration(contest, new User(), createRole(0, 0));
            fail("InsufficientDataException is expected.");
        } catch (InsufficientDataException ex) {
            assertEquals("Exception message is incorrect", "Contest registration start date is not defined.", ex
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure test for method: {@link OpenRegistrationValidator#validateUnregistration(Contest, User, ContestRole)}
     * <br>
     * Failure cause: Contest registration end date is not defined. <br>
     * Exception: InsufficientDataException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateUnregistration_ContestRegistrationEndDateNotSet() throws Exception {
        OpenRegistrationValidator validator = new OpenRegistrationValidator("failure.Complete");
        Contest contest = new Contest();
        contest.setRegistrationStart(new Date());

        try {
            validator.validateUnregistration(contest, new User(), createRole(0, 0));
            fail("InsufficientDataException is expected.");
        } catch (InsufficientDataException ex) {
            assertEquals("Exception message is incorrect", "Contest registration end date is not defined.", ex
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure test for method: {@link OpenRegistrationValidator#validateUnregistration(Contest, User, ContestRole)}
     * <br>
     * Failure cause: Contest role registration start offset is not defined. <br>
     * Exception: InsufficientDataException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateUnregistration_ContestRoleRegistrationStartOffsetNotSet() throws Exception {
        OpenRegistrationValidator validator = new OpenRegistrationValidator("failure.Complete");

        try {
            validator.validateUnregistration(createContest(-1000, 1000), new User(), createRole(-1, 0));
            fail("InsufficientDataException is expected.");
        } catch (InsufficientDataException ex) {
            assertEquals("Exception message is incorrect", "Contest role registration start offset is not defined.", ex
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure test for method: {@link OpenRegistrationValidator#validateUnregistration(Contest, User, ContestRole)}
     * <br>
     * Failure cause: Contest role registration end offset is not defined. <br>
     * Exception: InsufficientDataException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateUnregistration_ContestRoleRegistrationEndOffsetNotSet() throws Exception {
        OpenRegistrationValidator validator = new OpenRegistrationValidator("failure.Complete");

        try {
            validator.validateUnregistration(createContest(-1000, 1000), new User(), createRole(10, -1));
            fail("InsufficientDataException is expected.");
        } catch (InsufficientDataException ex) {
            assertEquals("Exception message is incorrect", "Contest role registration end offset is not defined.", ex
                .getMessage());
        }
    }


    /**
     * <p>
     * Creates a <code>Contest</code> that starts at current date - startOffset and ends at current date + end
     * offset.
     *
     * @param startOffset the offset (positive means past) from current date.
     * @param endOffset the offset (positive means future) from current date.
     * @return the <code>Contest</code> with the registration start and end date set.
     */
    private static Contest createContest(long startOffset, long endOffset) {
        Contest contest = new Contest();

        long current = System.currentTimeMillis();

        contest.setRegistrationStart(new Date(current - startOffset));
        contest.setRegistrationEnd(new Date(current + endOffset));

        return contest;
    }

    /**
     * <p>
     * Creates a <code>ContestRole</code> with the given offsets.
     * </p>
     *
     * @param startOffset the role start offset.
     * @param endOffset the role end offset.
     * @return the <code>ContestRole</code> with the given offsets.
     */
    private static ContestRole createRole(long startOffset, long endOffset) {
        ContestRole role = new ContestRole();

        role.setRegistrationStartOffset(startOffset);
        role.setRegistrationEndOffset(endOffset);

        return role;
    }
}
