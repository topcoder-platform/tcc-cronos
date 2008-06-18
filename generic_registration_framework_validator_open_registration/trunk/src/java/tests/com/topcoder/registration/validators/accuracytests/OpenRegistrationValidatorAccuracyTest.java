/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validators.accuracytests;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.registration.ValidationResult;
import com.topcoder.registration.entities.Contest;
import com.topcoder.registration.entities.ContestRole;
import com.topcoder.registration.entities.User;
import com.topcoder.registration.validators.OpenRegistrationValidator;

/**
 * <p>
 * Accuracy test cases for <code>OpenRegistrationValidator</code> class.
 * </p>
 *
 * @author romanoTC
 * @version 1.0
 */
public class OpenRegistrationValidatorAccuracyTest extends TestCase {

    /**
     * <p>
     * The expected default 'success' registration message.
     * </p>
     */
    private static final String DEFAULT_REGISTRATION_SUCCESS_MESSAGE =
        "Registration is validated by OpenRegistrationValidator";

    /**
     * <p>
     * The expected default 'early' registration message.
     * </p>
     */
    private static final String DEFAULT_REGISTRATION_EARLY_MESSAGE =
        "Registration validation failed because registration is not open yet for the contest and role submitted";

    /**
     * <p>
     * The expected default 'late' registration message.
     * </p>
     */
    private static final String DEFAULT_REGISTRATION_LATE_MESSAGE =
        "Registration validation failed because registration is closed for the contest and role submitted";

    /**
     * <p>
     * The expected default 'success' unregistration message.
     * </p>
     */
    private static final String DEFAULT_UNREGISTRATION_SUCCESS_MESSAGE =
        "Unregistration is validated by OpenRegistrationValidator";

    /**
     * <p>
     * The expected default 'early' unregistration message.
     * </p>
     */
    private static final String DEFAULT_UNREGISTRATION_EARLY_MESSAGE =
        "Unregistration validation failed because registration is not open yet for the contest and role submitted";

    /**
     * <p>
     * The expected default 'late' unregistration message.
     * </p>
     */
    private static final String DEFAULT_UNREGISTRATION_LATE_MESSAGE =
        "Unregistration validation failed because registration is closed for the contest and role submitted";

    /**
     * <p>
     * Constant holding the number of milliseconds in a day.
     * </p>
     */
    private static final long DAYS = 24 * 60 * 60 * 1000;

    /**
     * <p>
     * Accuracy test for constant: {@link OpenRegistrationValidator#DEFAULT_BUNDLE_NAME} <br>
     * Target: asserts the correct constant value is defined.
     * </p>
     */
    public void testDEFAULT_BUNDLE_NAME() {
        assertEquals("DEFAULT_BUNDLE_NAME is incorrect", "RegistrationFrameworkBundle",
            OpenRegistrationValidator.DEFAULT_BUNDLE_NAME);
    }

    /**
     * <p>
     * Accuracy test for constructor: {@link OpenRegistrationValidator#OpenRegistrationValidator()} <br>
     * Target: asserts the constructor throws no exception. The result is asserted by calling one validate method.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1() throws Exception {
        ValidationResult result = callSuccessfulRegistration(new OpenRegistrationValidator());
        assertValidationResult(true, "Registration successful", result);
    }

    /**
     * <p>
     * Accuracy test for constructor: {@link OpenRegistrationValidator#OpenRegistrationValidator(String)} <br>
     * Target: asserts the constructor throws no exception. The result is asserted by calling one validate method.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2() throws Exception {
        ValidationResult result = callSuccessfulRegistration(new OpenRegistrationValidator("accuracy.Complete"));
        assertValidationResult(true, "Registration successful (Complete)", result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateUnregistration(Contest, User, ContestRole)}
     * <br>
     * Target: Success message is shown using default properties file. This is a similar test as {@link #testCtor1()},
     * expect using <code>validateUnregistration</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateUnregistration_DefaultProperties_Success() throws Exception {
        ValidationResult result = callSuccessfulUnregistration(new OpenRegistrationValidator());
        assertValidationResult(true, "Unregistration successful", result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateUnregistration(Contest, User, ContestRole)}
     * <br>
     * Target: Success message is shown using default properties file. This is a similar test as {@link #testCtor2()},
     * expect using <code>validateUnregistration</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateUnregistration_CustomProperties_Success() throws Exception {
        ValidationResult result = callSuccessfulUnregistration(new OpenRegistrationValidator("accuracy.Complete"));
        assertValidationResult(true, "Unregistration successful (Complete)", result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateRegistration(Contest, User, ContestRole)}
     * <br>
     * Target: Properties file is missing, so default success message is shown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateRegistration_MissingResourceBundle_Success() throws Exception {
        ValidationResult result = callSuccessfulRegistration(new OpenRegistrationValidator("accuracy.Missingfile"));
        assertValidationResult(true, DEFAULT_REGISTRATION_SUCCESS_MESSAGE, result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateUnregistration(Contest, User, ContestRole)}
     * <br>
     * Target: Properties file is missing, so default success message is shown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateUnregistration_MissingResourceBundle_Success() throws Exception {
        ValidationResult result = callSuccessfulUnregistration(new OpenRegistrationValidator("accuracy.Missingfile"));
        assertValidationResult(true, DEFAULT_UNREGISTRATION_SUCCESS_MESSAGE, result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateRegistration(Contest, User, ContestRole)}
     * <br>
     * Target: Properties file does not have success key, so default success message is shown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateRegistration_MissingSuccessKey() throws Exception {
        ValidationResult result = callSuccessfulRegistration(new OpenRegistrationValidator("accuracy.MissingSuccess"));
        assertValidationResult(true, DEFAULT_REGISTRATION_SUCCESS_MESSAGE, result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateUnregistration(Contest, User, ContestRole)}
     * <br>
     * Target: Properties file does not have success key, so default success message is shown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateUnregistration_MissingSuccessKey() throws Exception {
        ValidationResult result =
            callSuccessfulUnregistration(new OpenRegistrationValidator("accuracy.MissingSuccess"));
        assertValidationResult(true, DEFAULT_UNREGISTRATION_SUCCESS_MESSAGE, result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateRegistration(Contest, User, ContestRole)}
     * <br>
     * Target: Properties file has an empty success key, so default success message is shown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateRegistration_EmptySuccessKey() throws Exception {
        ValidationResult result = callSuccessfulRegistration(new OpenRegistrationValidator("accuracy.EmptySuccess"));
        assertValidationResult(true, DEFAULT_REGISTRATION_SUCCESS_MESSAGE, result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateUnregistration(Contest, User, ContestRole)}
     * <br>
     * Target: Properties file has an empty success key, so default success message is shown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateUnregistration_EmptySuccessKey() throws Exception {
        ValidationResult result = callSuccessfulUnregistration(new OpenRegistrationValidator("accuracy.EmptySuccess"));
        assertValidationResult(true, DEFAULT_UNREGISTRATION_SUCCESS_MESSAGE, result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateRegistration(Contest, User, ContestRole)}
     * <br>
     * Target: early message is shown using default properties file.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateRegistration_DefaultProperties_Early() throws Exception {
        ValidationResult result = callEarlyRegistration(new OpenRegistrationValidator());
        assertValidationResult(false, "Registration too early", result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateRegistration(Contest, User, ContestRole)}
     * <br>
     * Target: early message is shown using custom properties file.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateRegistration_CustomProperties_Early() throws Exception {
        ValidationResult result = callEarlyRegistration(new OpenRegistrationValidator("accuracy.Complete"));
        assertValidationResult(false, "Registration too early (Complete)", result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateUnregistration(Contest, User, ContestRole)}
     * <br>
     * Target: early message is shown using default properties file.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateUnregistration_DefaultProperties_Early() throws Exception {
        ValidationResult result = callEarlyUnregistration(new OpenRegistrationValidator());
        assertValidationResult(false, "Unregistration too early", result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateUnregistration(Contest, User, ContestRole)}
     * <br>
     * Target: early message is shown using custom properties file.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateUnregistration_CustomProperties_Early() throws Exception {
        ValidationResult result = callEarlyUnregistration(new OpenRegistrationValidator("accuracy.Complete"));
        assertValidationResult(false, "Unregistration too early (Complete)", result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateRegistration(Contest, User, ContestRole)}
     * <br>
     * Target: Properties file is missing, so default early message is shown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateRegistration_MissingResourceBundle_Early() throws Exception {
        ValidationResult result = callEarlyRegistration(new OpenRegistrationValidator("accuracy.Missingfile"));
        assertValidationResult(false, DEFAULT_REGISTRATION_EARLY_MESSAGE, result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateUnregistration(Contest, User, ContestRole)}
     * <br>
     * Target: Properties file is missing, so default early message is shown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateUnregistration_MissingResourceBundle_Early() throws Exception {
        ValidationResult result = callEarlyUnregistration(new OpenRegistrationValidator("accuracy.Missingfile"));
        assertValidationResult(false, DEFAULT_UNREGISTRATION_EARLY_MESSAGE, result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateRegistration(Contest, User, ContestRole)}
     * <br>
     * Target: Properties file does not have early key, so default early message is shown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateRegistration_MissingEarlyKey() throws Exception {
        ValidationResult result = callEarlyRegistration(new OpenRegistrationValidator("accuracy.MissingEarly"));
        assertValidationResult(false, DEFAULT_REGISTRATION_EARLY_MESSAGE, result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateUnregistration(Contest, User, ContestRole)}
     * <br>
     * Target: Properties file does not have early key, so default early message is shown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateUnregistration_MissingEarlyKey() throws Exception {
        ValidationResult result = callEarlyUnregistration(new OpenRegistrationValidator("accuracy.MissingEarly"));
        assertValidationResult(false, DEFAULT_UNREGISTRATION_EARLY_MESSAGE, result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateRegistration(Contest, User, ContestRole)}
     * <br>
     * Target: Properties file has an empty early key, so default early message is shown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateRegistration_EmptyEarlyKey() throws Exception {
        ValidationResult result = callEarlyRegistration(new OpenRegistrationValidator("accuracy.EmptyEarly"));
        assertValidationResult(false, DEFAULT_REGISTRATION_EARLY_MESSAGE, result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateUnregistration(Contest, User, ContestRole)}
     * <br>
     * Target: Properties file has an empty early key, so default early message is shown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateUnregistration_EmptyEarlyKey() throws Exception {
        ValidationResult result = callEarlyUnregistration(new OpenRegistrationValidator("accuracy.EmptyEarly"));
        assertValidationResult(false, DEFAULT_UNREGISTRATION_EARLY_MESSAGE, result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateRegistration(Contest, User, ContestRole)}
     * <br>
     * Target: late message is shown using default properties file.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateRegistration_DefaultProperties_Late() throws Exception {
        ValidationResult result = callLateRegistration(new OpenRegistrationValidator());
        assertValidationResult(false, "Registration too late", result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateRegistration(Contest, User, ContestRole)}
     * <br>
     * Target: late message is shown using custom properties file.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateRegistration_CustomProperties_Late() throws Exception {
        ValidationResult result = callLateRegistration(new OpenRegistrationValidator("accuracy.Complete"));
        assertValidationResult(false, "Registration too late (Complete)", result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateUnregistration(Contest, User, ContestRole)}
     * <br>
     * Target: late message is shown using default properties file.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateUnregistration_DefaultProperties_Late() throws Exception {
        ValidationResult result = callLateUnregistration(new OpenRegistrationValidator());
        assertValidationResult(false, "Unregistration too late", result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateUnregistration(Contest, User, ContestRole)}
     * <br>
     * Target: late message is shown using custom properties file.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateUnregistration_CustomProperties_Late() throws Exception {
        ValidationResult result = callLateUnregistration(new OpenRegistrationValidator("accuracy.Complete"));
        assertValidationResult(false, "Unregistration too late (Complete)", result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateRegistration(Contest, User, ContestRole)}
     * <br>
     * Target: Properties file is missing, so default late message is shown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateRegistration_MissingResourceBundle_Late() throws Exception {
        ValidationResult result = callLateRegistration(new OpenRegistrationValidator("accuracy.Missingfile"));
        assertValidationResult(false, DEFAULT_REGISTRATION_LATE_MESSAGE, result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateUnregistration(Contest, User, ContestRole)}
     * <br>
     * Target: Properties file is missing, so default late message is shown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateUnregistration_MissingResourceBundle_Late() throws Exception {
        ValidationResult result = callLateUnregistration(new OpenRegistrationValidator("accuracy.Missingfile"));
        assertValidationResult(false, DEFAULT_UNREGISTRATION_LATE_MESSAGE, result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateRegistration(Contest, User, ContestRole)}
     * <br>
     * Target: Properties file does not have late key, so default late message is shown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateRegistration_MissingLateKey() throws Exception {
        ValidationResult result = callLateRegistration(new OpenRegistrationValidator("accuracy.MissingLate"));
        assertValidationResult(false, DEFAULT_REGISTRATION_LATE_MESSAGE, result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateUnregistration(Contest, User, ContestRole)}
     * <br>
     * Target: Properties file does not have late key, so default late message is shown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateUnregistration_MissingLateKey() throws Exception {
        ValidationResult result = callLateUnregistration(new OpenRegistrationValidator("accuracy.MissingLate"));
        assertValidationResult(false, DEFAULT_UNREGISTRATION_LATE_MESSAGE, result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateRegistration(Contest, User, ContestRole)}
     * <br>
     * Target: Properties file has an empty late key, so default late message is shown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateRegistration_EmptyLateKey() throws Exception {
        ValidationResult result = callLateRegistration(new OpenRegistrationValidator("accuracy.EmptyLate"));
        assertValidationResult(false, DEFAULT_REGISTRATION_LATE_MESSAGE, result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateUnregistration(Contest, User, ContestRole)}
     * <br>
     * Target: Properties file has an empty late key, so default late message is shown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateUnregistration_EmptyLateKey() throws Exception {
        ValidationResult result = callLateUnregistration(new OpenRegistrationValidator("accuracy.EmptyLate"));
        assertValidationResult(false, DEFAULT_UNREGISTRATION_LATE_MESSAGE, result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateRegistration(Contest, User, ContestRole)}
     * <br>
     * Target: The contest starts at D-1, but the registration start offset for the ContestRole is 2 days, which will
     * cause a early message.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateRegistration_DefaultProperties_StartOffset_Early() throws Exception {
        ValidationResult result = new OpenRegistrationValidator().validateRegistration(
            createContest(DAYS, 3 * DAYS), new User(), createRole(2 * DAYS, 0));
        assertValidationResult(false, "Registration too early", result);
    }

    /**
     * <p>
     * Accuracy test for method: {@link OpenRegistrationValidator#validateRegistration(Contest, User, ContestRole)}
     * <br>
     * Target: The contest ends at D-1 (which would force a late message), but the registration end offset for the
     * ContestRole is +2 days, which will cause a success message.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testValidateRegistration_DefaultProperties_EndOffset_Success() throws Exception {
        ValidationResult result = new OpenRegistrationValidator().validateRegistration(createContest(2 * DAYS,
            -DAYS), new User(), createRole(0, 2 * DAYS));
        assertValidationResult(true, "Registration successful", result);
    }

    /**
     * <p>
     * Calls {@link OpenRegistrationValidator#validateUnregistration(Contest, User, ContestRole)} using a Contest and
     * a ContentRole that will always cause a 'late' message.
     * </p>
     *
     * @param validator the OpenRegistrationValidator used in test.
     * @return the result from the <code>validateUnregistration</code> call.
     * @throws Exception to JUnit.
     */
    private static ValidationResult callLateUnregistration(OpenRegistrationValidator validator) throws Exception {
        return validator.validateUnregistration(createContest(10 * DAYS, -DAYS), new User(), createRole(0, 0));
    }

    /**
     * <p>
     * Calls {@link OpenRegistrationValidator#validateRegistration(Contest, User, ContestRole)} using a Contest and a
     * ContentRole that will always cause a 'late' message.
     * </p>
     *
     * @param validator the OpenRegistrationValidator used in test.
     * @return the result from the <code>validateRegistration</code> call.
     * @throws Exception to JUnit.
     */
    private static ValidationResult callLateRegistration(OpenRegistrationValidator validator) throws Exception {
        return validator.validateRegistration(createContest(10 * DAYS, -DAYS), new User(), createRole(0, 0));
    }

    /**
     * <p>
     * Calls {@link OpenRegistrationValidator#validateUnregistration(Contest, User, ContestRole)} using a Contest and
     * a ContentRole that will always cause a 'early' message.
     * </p>
     *
     * @param validator the OpenRegistrationValidator used in test.
     * @return the result from the <code>validateUnregistration</code> call.
     * @throws Exception to JUnit.
     */
    private static ValidationResult callEarlyUnregistration(OpenRegistrationValidator validator) throws Exception {
        return validator.validateUnregistration(createContest(-DAYS, 10 * DAYS), new User(), createRole(0, 0));
    }

    /**
     * <p>
     * Calls {@link OpenRegistrationValidator#validateRegistration(Contest, User, ContestRole)} using a Contest and a
     * ContentRole that will always cause a 'early' message.
     * </p>
     *
     * @param validator the OpenRegistrationValidator used in test.
     * @return the result from the <code>validateRegistration</code> call.
     * @throws Exception to JUnit.
     */
    private static ValidationResult callEarlyRegistration(OpenRegistrationValidator validator) throws Exception {
        return validator.validateRegistration(createContest(-DAYS, 10 * DAYS), new User(), createRole(0, 0));
    }

    /**
     * <p>
     * Calls {@link OpenRegistrationValidator#validateUnregistration(Contest, User, ContestRole)} using a Contest and
     * a ContentRole that will always cause a 'success' message.
     * </p>
     *
     * @param validator the OpenRegistrationValidator used in test.
     * @return the result from the <code>validateUnregistration</code> call.
     * @throws Exception to JUnit.
     */
    private static ValidationResult callSuccessfulUnregistration(OpenRegistrationValidator validator)
        throws Exception {
        return validator.validateUnregistration(createContest(10 * DAYS, 10 * DAYS), new User(), createRole(0, 0));
    }

    /**
     * <p>
     * Calls {@link OpenRegistrationValidator#validateRegistration(Contest, User, ContestRole)} using a Contest and a
     * ContentRole that will always cause a 'success' message.
     * </p>
     *
     * @param validator the OpenRegistrationValidator used in test.
     * @return the result from the <code>validateRegistration</code> call.
     * @throws Exception to JUnit.
     */
    private static ValidationResult callSuccessfulRegistration(OpenRegistrationValidator validator)
        throws Exception {

        return validator.validateRegistration(createContest(10 * DAYS, 10 * DAYS), new User(), createRole(0, 0));
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

    /**
     * Asserts the <code>ValidationResult</code> object.
     *
     * @param success the expected <code>isSuccess</code> value.
     * @param description the expected validation description.
     * @param result the <code>ValidationResult</code> object to be asserted.
     */
    private static void assertValidationResult(boolean success, String description, ValidationResult result) {
        assertEquals("Validation success is incorrect", success, result.isSuccess());
        assertEquals("Validation description is incorrect", description, result.getDescription());
    }
}
