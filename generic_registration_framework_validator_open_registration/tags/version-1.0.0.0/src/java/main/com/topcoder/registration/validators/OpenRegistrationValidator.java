/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validators;

import java.util.Date;
import java.util.MissingResourceException;

import com.topcoder.registration.InsufficientDataException;
import com.topcoder.registration.RegistrationValidator;
import com.topcoder.registration.ValidationResult;
import com.topcoder.registration.entities.Contest;
import com.topcoder.registration.entities.ContestRole;
import com.topcoder.registration.entities.User;
import com.topcoder.util.errorhandling.ExceptionUtils;
import com.topcoder.util.ext.ReloadingBundle;
import com.topcoder.util.ext.ResourceManager;

/**
 * <p>
 * This validator validates both registration and unregistration to ensure that the window for registration for the
 * given role is still open. This validator makes sure to use both the contest's registration start and end dates,
 * applying any offsets from the contest role. When comparing the dates, it is important to include the offsets of the
 * role registration times.
 * </p>
 * <p>
 * For instance, a reviewer position may open 24 hours after the contest's registration starts, so the start time is
 * actually <code>contest.registrationStart + role.registrationStartOffset</code>.
 * </p>
 * <p>
 * The same applies to the registration end offset. The date comparison is inclusive of the current time, to ensure
 * registration is open and available at the exact moment specified in the contest and role information.
 * </p>
 * <p>
 * If there is not sufficient data to determine if registration is allowed or not, due to missing or null dates, a
 * InsufficientDataException from the Registration Framework component will be thrown.
 * </p>
 * <p>
 * This validator will utilize a resource bundle when possible to fetch the specific validation messages (using the
 * default locale). Utilization of the bundle is optional and if the bundle is not found or present then the validator
 * will simply fall back on the default messages.
 * </p>
 * <p>
 * The resource bundle file can contain the following keys (all keys are optional and the <i>Default Value</i> column
 * indicates the message that will be used in case the key is missing or the whole bundle file is not found):
 * </p>
 * <table border=1>
 * <tr>
 * <th>Key</th>
 * <th>Description</th>
 * <th>Default Value</th>
 * </tr>
 * <tr>
 * <td>validation.registration.open.registration.success</td>
 * <td>Specific success message for successful registration validation.</td>
 * <td>Registration is validated by OpenRegistrationValidator</td>
 * </tr>
 * <tr>
 * <td>validation.registration.open.registration.failure.early</td>
 * <td>Specific failure message for an unsuccessful registration validation (current date is earlier than
 * registration start date + offset).</td>
 * <td>Registration validation failed because registration is not open yet for the contest and role submitted</td>
 * </tr>
 * <tr>
 * <td>validation.registration.open.registration.failure.late</td>
 * <td>Specific failure message for an unsuccessful registration validation (current date is later than registration
 * end date + offset).</td>
 * <td>Registration validation failed because registration is closed for the contest and role submitted</td>
 * </tr>
 * <tr>
 * <td>validation.registration.open.unregistration.success</td>
 * <td>Specific success message for successful unregistration validation.</td>
 * <td>Unregistration is validated by OpenRegistrationValidator</td>
 * </tr>
 * <tr>
 * <td>validation.registration.open.unregistration.failure.early</td>
 * <td>Specific failure message for an unsuccessful unregistration validation (current date is earlier than
 * registration start date + offset).</td>
 * <td>Unregistration validation failed because registration is not open yet for the contest and role submitted</td>
 * </tr>
 * <tr>
 * <td>validation.registration.open.unregistration.failure.late</td>
 * <td>Specific failure message for an unsuccessful unregistration validation (current date is later than
 * registration end date + offset).</td>
 * <td>Unregistration validation failed because registration is closed for the contest and role submitted</td>
 * </tr>
 * </table>
 * <p>
 * A sample properties file (<code>RegistrationFrameworkBundle.properties</code>) would look like this:
 * </p>
 *
 * <pre>
 * validation.registration.open.registration.success = Registration successful
 * validation.registration.open.registration.failure.early = Registration too early
 * validation.registration.open.registration.failure.late = Registration too late
 * validation.unregistration.open.registration.success = Unregistration successful
 * validation.unregistration.open.registration.failure.early = Unregistration too early
 * validation.unregistration.open.registration.failure.late = Unregistration too late
 * </pre>
 *
 * <p>
 * The example bellow shows how this validator would return a failure result (earlier registration) for a reviewer
 * role:
 * </p>
 *
 * <pre>
 * long DAYS = ... ; // number of milliseconds in a day
 *
 * long current = System.currentTimeMillis();
 * Contest contest = new Contest();
 *
 * // The context started 2 days ago and will end 10 days from now
 * contest.setRegistrationStart(new Date(current - 2 * DAYS));
 * contest.setRegistrationEnd(new Date(current + 10 * DAYS));
 *
 * // The reviewer role offset is tomorrow (or 3 DAYS from registration)
 * ContestRole reviewerRole = new ContestRole();
 * reviewerRole.setRegistrationStartOffset(3 * DAYS);
 * reviewerRole.setRegistrationEndOffset(DAYS);
 *
 * OpenRegistrationValidator validator = new OpenRegistrationValidator();
 * ValidationResult result = validator.validateRegistration(contest, null, reviewerRole);
 *
 * System.out.println(result.isSuccess());
 * System.out.println(result.getDescription());
 *
 * // Output:
 * // false
 * // Registration too early -&gt; if the above properties file is used.
 * </pre>
 *
 * <p>
 * <b>Thread-safety</b>: This class is immutable and stateless, therefore it is thread safe.
 * </p>
 *
 * @author AleaActaEst, romanoTC
 * @version 1.0
 */
public class OpenRegistrationValidator implements RegistrationValidator {

    /**
     * <p>
     * Default resource bundle name that will be used for this specific validator:
     * <code>"RegistrationFrameworkBundle"</code>.
     * </p>
     *
     * @see #OpenRegistrationValidator()
     */
    public static final String DEFAULT_BUNDLE_NAME = "RegistrationFrameworkBundle";

    /**
     * <p>
     * A bundle key is composed by (prefix).(identifier).(suffix). This is the prefix used by all keys in this
     * validator.
     * </p>
     */
    private static final String KEY_PREFIX = "validation.";

    /**
     * <p>
     * A bundle key is composed by (prefix).(identifier).(suffix). This is the suffix used by the 'success' message
     * keys.
     * </p>
     */
    private static final String SUCCESS_KEY_SUFFIX = ".open.registration.success";

    /**
     * <p>
     * A bundle key is composed by (prefix).(identifier).(suffix). This is the suffix used by the 'early' message
     * keys.
     * </p>
     */
    private static final String EARLY_KEY_SUFFIX = ".open.registration.failure.early";

    /**
     * <p>
     * A bundle key is composed by (prefix).(identifier).(suffix). This is the suffix used by the 'late' message keys.
     * </p>
     */
    private static final String LATE_KEY_SUFFIX = ".open.registration.failure.late";

    /**
     * <p>
     * A bundle key is composed by (prefix).(identifier).(suffix). This is the identifier of all registration keys.
     * </p>
     */
    private static final String REGISTRATION_KEY_IDENTIFIER = "registration";

    /**
     * <p>
     * A bundle key is composed by (prefix).(identifier).(suffix). This is the identifier of all unregistration keys.
     * </p>
     */
    private static final String UNREGISTRATION_KEY_IDENTIFIER = "unregistration";

    /**
     * <p>
     * The default 'success' message is composed of (Action) + message, where (Action) is 'Registration' or
     * 'Unregistration'. This constant holds the complement of the 'success' message.
     * </p>
     */
    private static final String DEFAULT_SUCCESS_MESSAGE = " is validated by "
        + OpenRegistrationValidator.class.getSimpleName();

    /**
     * <p>
     * The default 'early' message is composed of (Action) + message, where (Action) is 'Registration' or
     * 'Unregistration'. This constant holds the complement of the 'early' message.
     * </p>
     */
    private static final String DEFAULT_EARLY_MESSAGE =
        " validation failed because registration is not open yet for the contest and role submitted";

    /**
     * <p>
     * The default 'late' message is composed of (Action) + message, where (Action) is 'Registration' or
     * 'Unregistration'. This constant holds the complement of the 'late' message.
     * </p>
     */
    private static final String DEFAULT_LATE_MESSAGE =
        " validation failed because registration is closed for the contest and role submitted";

    /**
     * <p>
     * This constant holds the name of the validation action used in default registration messages ('Registration').
     * </p>
     */
    private static final String REGISTRATION_ACTION = "Registration";

    /**
     * <p>
     * This constant holds the name of the validation action used in default unregistration messages
     * ('Unregistration').
     * </p>
     */
    private static final String UNREGISTRATION_ACTION = "Unregistration";

    /**
     * <p>
     * This is a resource bundle name which would be used for the specific validation messages. This is initialized in
     * the constructors and will not be null/empty. If the bundle pointed by this name does not exist, the validator
     * will not stop working and will supply a default value for the message. This is used in both
     * <code>validate</code> methods.
     * </p>
     */
    private final String resourceBundleName;

    /**
     * <p>
     * Creates a validator using {@link #DEFAULT_BUNDLE_NAME} as the bundle name.
     * </p>
     */
    public OpenRegistrationValidator() {
        this(DEFAULT_BUNDLE_NAME);
    }

    /**
     * <p>
     * Creates a validator using the given bundle name to load the validation result messages.
     * </p>
     *
     * @param bundleName the name of the resource bundle to use.
     * @throws IllegalArgumentException if the bundle name is null or empty.
     */
    public OpenRegistrationValidator(String bundleName) {
        ExceptionUtils.checkNullOrEmpty(bundleName, null, null, "The [bundleName] argument cannot be null/empty.");
        this.resourceBundleName = bundleName;
    }

    /**
     * <p>
     * Validates the registration and return the result which indicates if the validation is failed or not. This
     * method validates the registration to ensure that the window for registration for the given role is still open.
     * </p>
     * <p>
     * This method uses the contest's registration start and end date, applying any offsets from the contest role, to
     * validate if current date is within the allowed interval, which is
     * <code>[registration start date + role start offset, registration end date + role end offset]</code>.
     * </p>
     * <p>
     * If current date is valid, a <code>ValidationResult</code> indicating success will be returned. It will
     * contain the message loaded from a resource bundle file (or a default success message in case a problem occurs
     * while loading bundle file information).
     * </p>
     * <p>
     * If current date is earlier or later, a <code>ValidationResult</code> indicating failure will be returned. It
     * will contain a specific message loaded from the same resource bundle file. A default failure message will be
     * used in case a problem occurs while loading bundle file information.
     * </p>
     *
     * @param contest the contest used for the registration.
     * @param user the user used for the registration, can be null.
     * @param role the contest's role used for the registration.
     * @return a validation result object indicating success or failure (in case the current time is earlier or later
     *         than the allowed interval).
     *
     * @throws IllegalArgumentException if <code>contest</code> or <code>role</code> argument is null.
     * @throws InsufficientDataException if the contest's start and end registration dates are not set (i.e. they are
     *             <code>null</code>), or if the content role's offsets are not set (i.e. they are <code>-1</code>).
     */
    public ValidationResult validateRegistration(Contest contest, User user, ContestRole role)
        throws InsufficientDataException {

        return validate(contest, role, REGISTRATION_ACTION, REGISTRATION_KEY_IDENTIFIER);
    }

    /**
     * <p>
     * Validates the unregistration and return the result which indicates if the validation is failed or not. This
     * method validates the unregistration to ensure that the window for registration for the given role is still
     * open.
     * </p>
     * <p>
     * This method uses the contest's registration start and end date, applying any offsets from the contest role, to
     * validate if current date is within the allowed interval, which is
     * <code>[registration start date + role start offset, registration end date + role end offset]</code>.
     * </p>
     * <p>
     * If current date is valid, a <code>ValidationResult</code> indicating success will be returned. It will
     * contain the message loaded from a resource bundle file (or a default success message in case a problem occurs
     * while loading bundle file information).
     * </p>
     * <p>
     * If current date is earlier or later, a <code>ValidationResult</code> indicating failure will be returned. It
     * will contain a specific message loaded from the same resource bundle file. A default failure message will be
     * used in case a problem occurs while loading bundle file information.
     * </p>
     *
     * @param contest the contest used for the registration.
     * @param user the user used for the registration, can be null.
     * @param role the contest's role used for the registration.
     * @return a validation result object indicating success or failure (in case the current time is earlier or later
     *         than the allowed interval).
     *
     * @throws IllegalArgumentException if <code>contest</code> or <code>role</code> argument is null.
     * @throws InsufficientDataException if the contest's start and end registration dates are not set (i.e. they are
     *             <code>null</code>), or if the content role's offsets are not set (i.e. they are <code>-1</code>).
     */
    public ValidationResult validateUnregistration(Contest contest, User user, ContestRole role)
        throws InsufficientDataException {

        return validate(contest, role, UNREGISTRATION_ACTION, UNREGISTRATION_KEY_IDENTIFIER);
    }

    /**
     * <p>
     * Validates the action (registration or unregistration).
     * </p>
     *
     * @param contest contest the contest used for the action.
     * @param role the contest's role used for the registration.
     * @param action the action of the validation (e.g. 'Registration').
     * @param keyIdentifier the identifier used to compose the key to extract the message from the bundle file (e.g.
     *            'registration').
     * @return a validation result object indicating success or failure (in case the current time is earlier or later
     *         than the allowed interval).
     *
     * @throws IllegalArgumentException if <code>contest</code> or <code>role</code> argument is null.
     * @throws InsufficientDataException if the contest's start and end registration dates are not set (i.e. they are
     *             <code>null</code>), or if the content role's offsets are not set (i.e. they are <code>-1</code>).
     */
    private ValidationResult validate(Contest contest, ContestRole role, String action, String keyIdentifier)
        throws InsufficientDataException {

        ExceptionUtils.checkNull(contest, null, null, "The [contest] argument cannot be null.");
        ExceptionUtils.checkNull(role, null, null, "The [role] argument cannot be null.");

        // Validate the registration start and end dates - they cannot be null
        Date registrationStart = contest.getRegistrationStart();
        if (registrationStart == null) {
            throw new InsufficientDataException("Contest registration start date is not defined.");
        }
        Date registrationEnd = contest.getRegistrationEnd();
        if (registrationEnd == null) {
            throw new InsufficientDataException("Contest registration end date is not defined.");
        }

        // Validate the registration start and end offsets - they cannot be -1
        long startOffset = role.getRegistrationStartOffset();
        if (startOffset == -1) {
            throw new InsufficientDataException("Contest role registration start offset is not defined.");
        }
        long endOffset = role.getRegistrationEndOffset();
        if (endOffset == -1) {
            throw new InsufficientDataException("Contest role registration end offset is not defined.");
        }

        // Add role's offset to start and end dates
        long start = registrationStart.getTime() + startOffset;
        long end = registrationEnd.getTime() + endOffset;
        long current = System.currentTimeMillis();

        boolean tooEarly = (current < start);
        boolean tooLate = (current > end);

        String keyPrefix = KEY_PREFIX + keyIdentifier;

        if (!tooEarly && !tooLate) {
            // This is a valid situation
            return new ValidationResult(true, getBundleMessage(keyPrefix + SUCCESS_KEY_SUFFIX, action
                + DEFAULT_SUCCESS_MESSAGE));
        } else if (tooEarly) {
            return new ValidationResult(false, getBundleMessage(keyPrefix + EARLY_KEY_SUFFIX, action
                + DEFAULT_EARLY_MESSAGE));
        } else {
            // If not too early, it must mean too late
            return new ValidationResult(false, getBundleMessage(keyPrefix + LATE_KEY_SUFFIX, action
                + DEFAULT_LATE_MESSAGE));
        }
    }

    /**
     * <p>
     * Loads the resource bundle and retrieves the value associated with the given key. If the value is not defined or
     * any problem occurs while loading the bundle, the default message is returned.
     * </p>
     *
     * @param key the key for the desired string.
     * @param defaultMessage the default message used when the key is not defined or any error occurs while loading
     *            the bundle.
     * @return the value associated with the given key or the default message if any error occurs while loading the
     *         bundle.
     */
    private String getBundleMessage(String key, String defaultMessage) {
        ReloadingBundle bundle = ResourceManager.getBundle(resourceBundleName);

        if (bundle == null) {
            // This is a safety check. ResourceManager is not supposed to return a null ResourceBundle.
            return defaultMessage;
        }

        try {
            String message = bundle.getString(key);
            return (message == null || message.trim().length() == 0) ? defaultMessage : message;

        } catch (MissingResourceException ex) {
            // if no object for the given key can be found, simply return the default message
            return defaultMessage;

        } catch (ClassCastException ex) {
            // if the object found for the given key is not a string, simply return the default message
            return defaultMessage;
        }
    }
}
