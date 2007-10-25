/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation;

import com.topcoder.util.datavalidator.AbstractObjectValidator;
import com.topcoder.util.datavalidator.BundleInfo;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.Level;

/**
 * <p>
 * An adapter for all simple and conditional validators that implements most of
 * the methods in the ConfigurableValidator. Most of the work is done by the
 * extended AbstractObjectValidator from Data Validation. This extension allows
 * the simple and conditional validators to concentrate on the getMessage
 * validation method to perform the specific validation. Future custom
 * validators can take advantage of this adapter.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and thus is not thread-safe, but the
 * actual properties and managers used to validate are immutable, so this class
 * will be effectively thread-safe in the context of its usage in this
 * component.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public abstract class AbstractConfigurableValidator extends
        AbstractObjectValidator implements ConfigurableValidator {

    /**
     * <p>
     * Represents the class name used to log.
     * </p>
     *
     */
    private static final String CLASS_NAME = "AbstractConfigurableValidator";

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
     * <p>
     * It is made available to implementing simple validators via the protected
     * getRegistrationValidator method.
     * </p>
     *
     */
    private DataValidationRegistrationValidator registrationValidator = null;

    /**
     * This constructor will initialize the resource bundle based on the input
     * parameters.
     *
     *
     * @param bundleInfo
     *            The resource bundle info
     * @throws IllegalArgumentException
     *             if any of the parameters in bundleInfo is null or an empty
     *             string (for string parameters)
     */
    protected AbstractConfigurableValidator(BundleInfo bundleInfo) {
        super(bundleInfo);
    }

    /**
     * Initializes the defaultMessage to the input string. the defaultMessage
     * can not be null or empty.
     *
     *
     * @param validationMessage
     *            String This is the validation message to use for the
     *            underlying validator.
     * @throws IllegalArgumentException
     *             if the input parameter is null or empty
     */
    protected AbstractConfigurableValidator(String validationMessage) {
        super(validationMessage);
    }

    /**
     * <p>
     * Sets the DataValidationRegistrationValidator so each validator has access
     * to the services of the RegistrationValidator. This may include access to
     * a Log, or other managers and services required to perform the validation.
     * </p>
     *
     *
     * @param dataValidationRegistrationValidator
     *            DataValidationRegistrationValidator
     * @throws IllegalArgumentException
     *             If dataValidationRegistrationValidator is null
     */
    public void setRegistrationValidator(
            DataValidationRegistrationValidator dataValidationRegistrationValidator) {
        RegistrationValidationHelper.validateNotNull(
                dataValidationRegistrationValidator,
                "dataValidationRegistrationValidator");
        this.registrationValidator = dataValidationRegistrationValidator;
    }

    /**
     * <p>
     * Provides the DataValidationRegistrationValidator instance to the
     * validator so it can access services. It will not be null.
     * </p>
     *
     *
     * @return The DataValidationRegistrationValidator instance with services
     *         such as logging
     */
    protected DataValidationRegistrationValidator getRegistrationValidator() {
        return registrationValidator;
    }

    /**
     * <p>
     * Determines if the given Object is valid.
     * </p>
     *
     *
     * @return true if obj is valid; false otherwise.
     * @param obj
     *            Object to validate.
     * @throws IllegalArgumentException
     *             If obj is null and not a ValidationInfo object
     * @throws ValidationProcessingException
     *             If an unexpected system error occurs
     */
    public boolean valid(Object obj) {
        String methodName = CLASS_NAME
                + "#valid(Object obj)";
        Log logger = getRegistrationValidator().getLog();

        // log entrance
        RegistrationValidationHelper.log(logger, Level.INFO, "enter " + methodName);
        try {
            String message = this.getMessage(obj);
            boolean result = (message == null) ? true : false;

            // Log return value
            RegistrationValidationHelper.log(logger, Level.DEBUG,
                "return value is [" + result + "]");

            // Log exit
            RegistrationValidationHelper.log(logger, Level.INFO, "exit " + methodName);
            return result;
        } catch (IllegalArgumentException e) {
            RegistrationValidationHelper.log(logger, Level.ERROR, "Error[" + e.getClass().getName()
                + "] occurs in " + methodName + ", cause:" + e.getMessage());
            throw e;
        }

    }

    /**
     * <p>
     * Gives error information about the given object being validated.
     * </p>
     *
     *
     * @return String[] a non-empty but possibly null array of failure messages
     * @param obj
     *            Object the object to validate
     * @throws IllegalArgumentException
     *             If obj is null and not a ValidationInfo object
     * @throws ValidationProcessingException
     *             If an unexpected system error occurs
     */
    public String[] getMessages(Object obj) {
        String methodName = CLASS_NAME + "#getMessages(Object obj)";
        Log logger = getRegistrationValidator().getLog();

        // log entrance
        RegistrationValidationHelper.log(logger, Level.INFO, "enter " + methodName);

        try {
            // Defers to the getMassage(object) method
            String message = this.getMessage(obj);

            // Wraps the returned string into array.
            String[] messages = (message == null) ? null
                    : new String[] {message};

            // Log exit
            RegistrationValidationHelper.log(logger, Level.INFO, "exit " + methodName);
            return messages;
        } catch (IllegalArgumentException e) {
            RegistrationValidationHelper.log(logger, Level.ERROR, "Error[" + e.getClass().getName()
                + "] occurs in " + methodName + ", cause:" + e.getMessage());
            throw e;
        }

    }

    /**
     * <p>
     * This is the same method concept as the getMessage except that this method
     * will evaluate the whole validator tree and return all the messages from
     * any validators that failed the object.
     * </p>
     *
     *
     * @return String[] a non-empty but possibly null array of failure messages
     * @param obj
     *            Object the object to validate
     * @throws IllegalArgumentException
     *             If obj is null and not a ValidationInfo object
     * @throws ValidationProcessingException
     *             If an unexpected system error occurs
     */
    public String[] getAllMessages(Object obj) {
        String methodName = CLASS_NAME + "#getAllMessages(Object obj)";
        Log logger = getRegistrationValidator().getLog();

        // log entrance
        RegistrationValidationHelper.log(logger, Level.INFO, "enter " + methodName);
        try {
            // Since this validator is not composite, this method will just
            // defer to the getMessages(Object) method.
            String[] messages = getMessages(obj);

            // Log exit
            RegistrationValidationHelper.log(logger, Level.INFO, "exit " + methodName);
            return messages;
        } catch (IllegalArgumentException e) {
            RegistrationValidationHelper.log(logger, Level.ERROR, "Error[" + e.getClass().getName()
                + "] occurs in " + methodName + ", cause:" + e.getMessage());
            throw e;
        }
    }

    /**
     * <p>
     * This is the same method concept as the getMessage except that this method
     * will evaluate the whole validator tree and return all the messages from
     * any validators that failed the object.
     * </p>
     *
     *
     * @return String[] a non-empty but possibly null array of failure messages
     * @param obj
     *            Object the object to validate
     * @param messageLimit
     *            the max number of messages. This must be greater-than-or-equal
     *            to 0. Since this validator is not composite, this method will
     *            just defer to the getMassages(object) method and ignore the
     *            messageLimit parameter.
     * @throws IllegalArgumentException
     *             If obj is null and not a ValidationInfo object
     * @throws ValidationProcessingException
     *             If an unexpected system error occurs
     */
    public String[] getAllMessages(Object obj, int messageLimit) {
        String methodName = CLASS_NAME
                + "#getAllMessages(Object obj, int messageLimit)";
        DataValidationRegistrationValidator rgistrationValidator = getRegistrationValidator();
        Log logger = rgistrationValidator.getLog();

        // log entrance
        RegistrationValidationHelper.log(logger, Level.INFO, "enter " + methodName);
        try {

            // Since this validator is not composite, this method will just
            // defer to the getMessages(Object) method and ignore the
            // messageLimit parameter.
            String[] messages = getMessages(obj);

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
