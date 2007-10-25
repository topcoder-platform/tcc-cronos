/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.validators.util;

import com.topcoder.registration.validation.RegistrationValidationHelper;
import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.util.datavalidator.BundleInfo;
import com.topcoder.registration.validation.ConfigurableValidator;
import com.topcoder.registration.validation.DataValidationRegistrationValidator;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.Level;

/**
 * A wrapper around the Data Validation&rsquo;s NullValidator to provide logging
 * in the methods.
 * <p>
 * This class implements ConfigurableValidator so it can be set with the
 * DataValidationRegistrationValidator manager so it can obtain the Log to
 * perform logging.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and thus is not thread-safe, but the
 * actual properties and managers used to validate are immutable, so this class
 * will be effectively thread-safe in the context of its usage in this
 * component.
 * </p>
 * <p>
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class NullValidator extends
        com.topcoder.util.datavalidator.NullValidator implements
        ConfigurableValidator {
    /**
     * <p>
     * Represents the class name used to log.
     * </p>
     *
     */
    private static final String CLASS_NAME = "NullValidator";

    /**
     * <p>
     * Represents the RegistrationValidator that acts as the manager for
     * validation in this component. It not only commences the validation, but
     * also provides services such as access to a Log, or other managers and
     * services required to perform the validation. This is why this validator
     * has access to it.
     * </p>
     * <p>
     * It is set in the setRegistrationValidator method. This method will be
     * called by the DataValidationRegistrationValidator just after this
     * validator is instantiated as part of completing its initialization. Once
     * set, it will never be null.
     * </p>
     *
     */
    private DataValidationRegistrationValidator registrationValidator = null;

    /**
     * Creates a new <code>NullValidator</code>.
     *
     */
    public NullValidator() {
    }

    /**
     * Creates a new <code>NullValidator</code> with a specific resource
     * bundle information.
     *
     *
     * @param bundleInfo
     *            BundleInfo resource bundle information
     */
    public NullValidator(BundleInfo bundleInfo) {
        super(bundleInfo);
    }

    /**
     * <p>
     * Sets the DataValidationRegistrationValidator so each validator has access
     * to the services of the RegistrationValidator. This may include access to
     * a Log, or other managers and services required to perform the validation.
     * </p>
     *
     *
     * @param registrationValidator
     *            DataValidationRegistrationValidator
     * @throws IllegalArgumentException
     *             If registrationValidator is null
     */
    public void setRegistrationValidator(
            DataValidationRegistrationValidator registrationValidator) {
        RegistrationValidationHelper.validateNotNull(registrationValidator,
                "registrationValidator");
        this.registrationValidator = registrationValidator;
    }

    /**
     * Validates the given <code>Object</code>. Simply defers to super of the
     * same signature.
     *
     *
     * @param obj
     *            <code>Object</code> to be validated.
     * @return <code>true</code> if <code>obj</code> is <code>null</code>;
     *         <code>false</code> otherwise.
     */
    public boolean valid(Object obj) {
        String methodName = CLASS_NAME + ".valid(Object obj)";
        Log logger = registrationValidator.getLog();
        // log entrance
        RegistrationValidationHelper.log(logger, Level.INFO, "enter " + methodName);

        boolean result = super.valid(obj);

        // Log exit
        RegistrationValidationHelper.log(logger, Level.INFO, "exit " + methodName);
        return result;

    }

    /**
     * If the given <code>Object</code> is valid, this returns
     * <code>null</code>. Otherwise, it returns an error message. Simply
     * defers to super of the same signature.
     *
     *
     * @return <code>null</code> if <code>obj</code> is valid. Otherwise an
     *         error message is returned.
     * @param obj
     *            <code>Object</code> to be validated.
     * @throws IllegalArgumentException
     *             If obj is not null and not a ValidationInfo object
     * @throws ValidationProcessingException
     *             If an unexpected system error occurs
     */
    public String getMessage(Object obj) {
        String methodName = CLASS_NAME + ".getMessages(Object obj)";
        Log logger = registrationValidator.getLog();
        // log entrance
        RegistrationValidationHelper.log(logger, Level.INFO, "enter " + methodName);

        try {

            String message;

            if (valid(obj)) {
                message = null;
            } else {
                RegistrationValidationHelper.validateIsValidationInfo(obj,
                        "Object to validate");
                // if obj is not validationInfo
                ValidationInfo validationInfo = (ValidationInfo) obj;

                String data = RegistrationValidationHelper
                        .buildStandInfo(validationInfo);

                String messageTemplate = this.getValidationMessage();
                message = RegistrationValidationHelper.fillMessage(messageTemplate,
                        data, logger, methodName);

            }

            // Log return value
            RegistrationValidationHelper.log(logger, Level.DEBUG,
                "return value is [" + message + "]");
            // Log exit
            RegistrationValidationHelper.log(logger, Level.INFO, "exit " + methodName);
            return message;
        } catch (IllegalArgumentException e) {
            RegistrationValidationHelper.log(logger, Level.ERROR, "Error[" + e.getClass().getName()
                + "] occurs in " + methodName + ", cause:" + e.getMessage());
            throw e;
        }

    }

    /**
     * Gives error information about the given object being validated. This
     * method will return possibly a number of messages produced for this object
     * by a number of validators if the validator is composite.
     *
     *
     * @return String[] a non-empty but possibly null array of failure messages
     * @param obj
     *            the object to validate
     * @throws IllegalArgumentException
     *             If obj is not null and not a ValidationInfo object
     * @throws ValidationProcessingException
     *             If an unexpected system error occurs
     */

    public String[] getMessages(Object obj) {
        String methodName = CLASS_NAME + ".getMessages(Object obj)";
        Log logger = registrationValidator.getLog();
        // log entrance
        RegistrationValidationHelper.log(logger, Level.INFO, "enter " + methodName);

        String[] messages = super.getMessages(obj);

        // Log exit
        RegistrationValidationHelper.log(logger, Level.INFO, "exit " + methodName);
        return messages;

    }

    /**
     * This is the same method concept as the getMessage except that this method
     * will evalulat the whole validator tree and return all the messages from
     * any validators that failed the object. In other words this is a
     * non-short-circuited version of the method. Since this is a primitive
     * validator the result will always be equivalent to getting a single
     * message but as an array. Simply defers to super of the same signature.
     *
     *
     * @return String[] a non-empty but possibly null array of failure messages
     * @param obj
     *            the object to validate
     * @throws IllegalArgumentException
     *             If obj is not null and not a ValidationInfo object
     * @throws ValidationProcessingException
     *             If an unexpected system error occurs
     */
    public String[] getAllMessages(Object obj) {
        String methodName = CLASS_NAME + ".getAllMessages(Object obj)";
        Log logger = registrationValidator.getLog();
        // log entrance
        RegistrationValidationHelper.log(logger, Level.INFO, "enter " + methodName);
        String[] messages = super.getMessages(obj);

        // Log exit
        RegistrationValidationHelper.log(logger, Level.INFO, "exit " + methodName);
        return messages;
    }

    /**
     * <p>
     * This is the same method concept as the getMessage except that this method
     * will evaluate the whole validator tree and return all the messages from
     * any validators that failed the object. In other words this is a
     * non-short-circuited version of the method. Since this is a primitive
     * validator the result will always be equivalent to getting a single
     * message but as an array.
     *
     *
     * @return String[] a non-empty but possibly null array of failure messages
     * @param obj
     *            the object to validate
     * @param messageLimit
     *            the max number of messages. This must be greater-than-or-equal
     *            to 0.
     * @throws IllegalArgumentException
     *             If obj is not null and not a ValidationInfo object
     * @throws ValidationProcessingException
     *             If an unexpected system error occurs
     */
    public String[] getAllMessages(Object obj, int messageLimit) {
        String methodName = CLASS_NAME
                + ".getAllMessages(Object obj, int messageLimit)";
        Log logger = registrationValidator.getLog();
        // log entrance
        RegistrationValidationHelper.log(logger, Level.INFO, "enter " + methodName);
        try {
            RegistrationValidationHelper.validateNotNegative(messageLimit,
                    "messageLimit");

            // Defers to the super's method of the same signature
            String[] messages = super.getAllMessages(obj, messageLimit);

            // Log exit
            RegistrationValidationHelper.log(logger, Level.INFO, "exit " + methodName);
            return messages;
        } catch (IllegalArgumentException e) {
            RegistrationValidationHelper.log(logger, Level.ERROR, "Error[" + e.getClass().getName()
                + "] occurs in " + methodName + ", cause:" + e.getMessage());
            throw e;
        }
    }
}