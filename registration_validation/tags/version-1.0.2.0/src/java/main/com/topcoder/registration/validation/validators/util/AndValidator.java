/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.validators.util;

import com.topcoder.registration.validation.AbstractConfigurableValidator;
import com.topcoder.registration.validation.RegistrationValidationHelper;
import com.topcoder.registration.validation.RegistrationValidationConfigurationException;
import com.topcoder.registration.validation.ConfigurableValidator;
import com.topcoder.registration.validation.DataValidationRegistrationValidator;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.Level;

import java.util.Iterator;

/**
 * A wrapper around the Data Validation&rsquo;s AndValidator to provide logging
 * in the methods and allow the aggregated validators to be created from
 * configuration. It also allows for configuration of a flag to have validation
 * stop after the first error, or to provide non-short-circuited validation.
 * <p>
 * This class implements ConfigurableValidator so it can be set with the
 * DataValidationRegistrationValidator manager so it can obtain the Log to
 * perform logging.
 * </p>
 *
 * <p>
 * To provide a good view as the steps are progressing in each method, the
 * <b>Logging Wrapper</b> component is used in each method. To configure this
 * component, the <b>ConfigManager</b> component is used.
 * </p>
 *
 * <p>
 * Here is the sample configuration file for this class.
 *
 * <pre>
 *                &lt;Config name=&quot;test.Validator;&gt;
 *                    &lt;Property name=&quot;specNamespace&quot;&gt;
 *                        &lt;Value&gt;com.topcoder.specify;/Value&gt;
 *                    &lt;/Property&gt;
 *                    &lt;Property name=&quot;innerValidator1&quot;&gt;
 *                        &lt;Value&gt;myInnerValidator1&lt;/Value&gt;
 *                    &lt;/Property&gt;
 *                    &lt;Property name=&quot;innerValidator2&quot;&gt;
 *                        &lt;Value&gt;myInnerValidator2&lt;/Value&gt;
 *                    &lt;/Property&gt;
 *                    &lt;Property name=&quot;innerValidator3&quot;&gt;
 *                        &lt;Value&gt;myInnerValidator3&lt;/Value&gt;
 *                    &lt;/Property&gt;
 *                    &lt;Property name=&quot;bundleName&quot;&gt;
 *                        &lt;Value&gt;myBundle&lt;/Value&gt;
 *                    &lt;/Property&gt;
 *                    &lt;Property name=&quot;bundleLocaleLanguage&quot;&gt;
 *                        &lt;Value&gt;en&lt;/Value&gt;
 *                    &lt;/Property&gt;
 *                    &lt;Property name=&quot;bundleLocaleCountry&quot;&gt;
 *                        &lt;Value&gt;ca&lt;/Value&gt;
 *                    &lt;/Property&gt;
 *                    &lt;Property name=&quot;bundleLocaleVariant&quot;&gt;
 *                        &lt;Value&gt;Traditional_WIN&lt;/Value&gt;
 *                    &lt;/Property&gt;
 *                    &lt;Property name=&quot;messageKey&quot;&gt;
 *                        &lt;Value&gt;myMessageKey&lt;/Value&gt;
 *                    &lt;/Property&gt;
 *                    &lt;Property name=&quot;defaultMessage&quot;&gt;
 *                        &lt;Value&gt;./test_files/myTemplate.txt&lt;/Value&gt;
 *                    &lt;/Property&gt;
 *                &lt;/Config&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * Thread Safety: This class is mutable and thus is not thread-safe, but the
 * actual properties and managers used to validate are immutable, so this class
 * will be effectively thread-safe in the context of its usage in this
 * component.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class AndValidator extends com.topcoder.util.datavalidator.AndValidator
        implements ConfigurableValidator {

    /**
     * <p>
     * Represents the class name used to log.
     * </p>
     *
     */
    private static final String CLASS_NAME = "AndValidator";

    /**
     * <p>
     * Name of the property that contains namespace to load object factory
     * information from.
     * </p>
     * <p>
     * This property is required. The value of this property should be any valid
     * namespace for an Object Factory.
     * </p>
     */
    private static final String OBJECT_FACTORY_PROPERTYNAME = "specNamespace";

    /**
     * <p>
     * Name of the property that stores the flag as to whether validation should
     * stop on first error, or continue accumulating messages until all
     * validators are asked.
     *
     * </p>
     * <p>
     * This property is required. This must can be parsed into boolean.
     * </p>
     *
     */
    private static final String SHORT_CIRCUITED_PROPERTYNAME = "shortCircuited";

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
     * <p>
     * Represents a flag as to whether validation should stop on first error, or
     * continue accumulating messages until all validators are asked.
     * </p>
     *
     * <p>
     * It is set in the constructor, and will never change.
     * </p>
     *
     *
     */
    private final boolean shortCircuited;

    /**
     * Creates a new AndValidator, with initially associated to the given
     * validators. Simply adds these validators to itself using addValidator.
     *
     *
     * @param validators
     *            ConfigurableValidators to be put in this associative validator
     * @param shortCircuited
     *            true if validation should stop on first error, false if not
     * @throws IllegalArgumentException
     *             If validators is null or contains null elements
     */
    public AndValidator(ConfigurableValidator[] validators,
            boolean shortCircuited) {
        this.shortCircuited = shortCircuited;
        RegistrationValidationHelper.validateArrayNotNullOrContainsNull(
                validators, "validators");
        for (int i = 0; i < validators.length; i++) {
            addValidator(validators[i]);
        }
    }

    /**
     * Initializes all inner validators and shortCircuited flag from
     * configuration information from the <b>ConfigManager</b>.
     *
     *
     * @param namespace
     *            The configuration namespace
     * @throws IllegalArgumentException
     *             If namespace is null/empty
     * @throws RegistrationValidationConfigurationException
     *             If any configuration error occurs, such as unknown namespace
     *             or missing required values, or an inability to create the
     *             validators
     */
    public AndValidator(String namespace) {
        super();
        RegistrationValidationHelper.validateStringNotNullOrEmpty(namespace,
                "namespace");
        try {
            // Loads namespace for object factory
            String objectFactoryNamespace = RegistrationValidationHelper
                    .getString(namespace, OBJECT_FACTORY_PROPERTYNAME, true);

            // Creates object factory from the given object factory namespace
            ObjectFactory factory = new ObjectFactory(
                    new ConfigManagerSpecificationFactory(
                            objectFactoryNamespace));

            int innerValidatorNumber = 0;
            while (true) {
                // increase the innerValidatorNumber to get the key for the next innerValidator.
                innerValidatorNumber++;
                String keyPropertyName = "innerValidator" + innerValidatorNumber;
                // Creates innerValidator using the ObjectFactory
                ConfigurableValidator innerValidator = (ConfigurableValidator) RegistrationValidationHelper
                    .createObjectFromObjectFactory(namespace,
                            keyPropertyName, factory,
                            ConfigurableValidator.class, false);
                // if the created inner validator is null, then break.
                // else add the created inner validator.
                if (innerValidator == null) {
                    break;
                } else {
                    addValidator(innerValidator);
                }
            }


            // Gets the shortCircuited flag from the corresponding property
            this.shortCircuited = RegistrationValidationHelper.getBoolean(
                    namespace, SHORT_CIRCUITED_PROPERTYNAME);

        } catch (SpecificationConfigurationException sce) {
            throw new RegistrationValidationConfigurationException(
                    "Some problem occurs while creating the ConfigManagerSpecificationFactory",
                    sce);
        } catch (IllegalReferenceException ire) {
            throw new RegistrationValidationConfigurationException(
                    "Some problem occurs while creating the ConfigManagerSpecificationFactory",
                    ire);
        }
    }

    /**
     * <p>
     * Sets the DataValidationRegistrationValidator so each validator has access
     * to the services of the RegistrationValidator. This may include access to
     * a Log, or other managers and services required to perform the validation.
     * Also sets it to all its children.
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

        // Sets registrationValidator to the current validator
        this.registrationValidator = registrationValidator;

        // Sets registrationValidator to all the children validators
        for (Iterator itr = this.getValidators(); itr.hasNext();) {
            AbstractConfigurableValidator childValidator = (AbstractConfigurableValidator) itr
                    .next();
            childValidator.setRegistrationValidator(registrationValidator);
        }
    }

    /**
     * Validates the given <code>Object</code>. Note: if the list of
     * associated validators is empty, this returns true.
     *
     *
     * @return <code>true</code> if <code>obj</code> is valid;
     *         <code>false</code> otherwise.
     * @param obj
     *            <code>Object</code> to be validated.
     * @throws IllegalArgumentException
     *             If obj is null or not a ValidationInfo object
     * @throws ValidationProcessingException
     *             If an unexpected system error occurs
     */
    public boolean valid(Object obj) {
        String methodName = CLASS_NAME + ".valid(Object obj)";
        Log logger = registrationValidator.getLog();
        // log entrance
        RegistrationValidationHelper.log(logger, Level.INFO, "enter " + methodName);

        // Defers to super.valid(Object)
        boolean result = super.valid(obj);

        // Log exit
        RegistrationValidationHelper.log(logger, Level.INFO, "exit " + methodName);
        return result;
    }

    /**
     * If the given <code>Object</code> is valid, this returns
     * <code>null</code>. Otherwise, it returns an error message.
     *
     *
     * @return <code>null</code> if <code>obj</code> is valid. Otherwise an
     *         error message is returned.
     * @param obj
     *            <code>Object</code> to be validated.
     * @throws IllegalArgumentException
     *             If obj is null or not a ValidationInfo object
     * @throws ValidationProcessingException
     *             If an unexpected system error occurs
     */
    public String getMessage(Object obj) {

        String methodName = CLASS_NAME + ".getMessages(Object obj)";
        Log logger = registrationValidator.getLog();
        // log entrance
        RegistrationValidationHelper.log(logger, Level.INFO, "enter " + methodName);

        // Defers to super.getMessage(Object)
        String message = super.getMessage(obj);

        // Log return value
        RegistrationValidationHelper.log(logger, Level.DEBUG,
            "return value is [" + message + "]");
        // Log exit
        RegistrationValidationHelper.log(logger, Level.INFO, "exit " + methodName);
        return message;

    }

    /**
     * Gives error information about the given object being validated. This
     * method will return possibly a number of messages produced for this object
     * by a number of validators if the validator is composite. In general a
     * composite OrValidator should produce quite a few messages (when
     * constituent validators keep on failing - since all have to fail for the
     * OR to fail) but for an AndValidator we would only see a single message
     * (of course as we would go deeper this would be changing if there were
     * OrValidators under the AndValidator in question) This validation will
     * only return enough error messages leading to the moment in traversing the
     * composite validator tree to determine that the validator has failed. in
     * other words this method is short-circuited.
     *
     *
     * @return String[] a non-empty but possibly null array of failure messages
     * @param obj
     *            Object the object to validate
     * @throws IllegalArgumentException
     *             If obj is null or not a ValidationInfo object
     * @throws ValidationProcessingException
     *             If an unexpected system error occurs
     */
    public String[] getMessages(Object obj) {

        String methodName = CLASS_NAME + ".getMessages(Object obj)";
        Log logger = registrationValidator.getLog();
        // log entrance
        RegistrationValidationHelper.log(logger, Level.INFO, "enter " + methodName);

        // Defers to super.getMessages(Object)
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
     * non-short-circuited version of the method. This method should be used
     * with caution as for large trees it might have to go deep and thus spend
     * quite a bit of time doing it. The alternative to this is the
     * getAllMessages overload that takes a limiting max that will force the
     * method to return after so many errors were found.
     * </p>
     *
     * <p>
     * In this method the shortCircuited flag is used to decide whether
     * validation should stop on first error, or continue accumulating messages
     * until all validators are asked.
     * </p>
     *
     * @return String[] a non-empty but possibly null array of failure messages
     * @param obj
     *            Object the object to validate
     *
     * @throws IllegalArgumentException
     *             If obj is null or not a ValidationInfo object
     * @throws ValidationProcessingException
     *             If an unexpected system error occurs
     */
    public String[] getAllMessages(Object obj) {
        String methodName = CLASS_NAME + ".getAllMessages(Object obj)";
        Log logger = registrationValidator.getLog();
        // log entrance
        RegistrationValidationHelper.log(logger, Level.INFO, "enter " + methodName);
        String[] messages;
        if (this.shortCircuited) {
            messages = super.getAllMessages(obj, 1);
        } else {
            messages = super.getAllMessages(obj);
        }
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
     * </p>
     *
     * @return String[] a non-empty but possibly null array of failure messages
     * @param obj
     *            the object to validate
     * @param messageLimit
     *            the max number of messages. This must be greater-than-or-equal
     *            to 0.
     * @throws IllegalArgumentException
     *             If obj is null or not a ValidationInfo object
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
