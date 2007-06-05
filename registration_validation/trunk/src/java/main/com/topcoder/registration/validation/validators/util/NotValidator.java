/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.validators.util;

import com.topcoder.util.datavalidator.AbstractObjectValidator;
import com.topcoder.registration.validation.ConfigurableValidator;
import com.topcoder.registration.validation.RegistrationValidationConfigurationException;
import com.topcoder.registration.validation.ValidationProcessingException;
import com.topcoder.registration.validation.RegistrationValidationHelper;
import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.util.datavalidator.BundleInfo;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;
import com.topcoder.registration.validation.DataValidationRegistrationValidator;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.Level;

/**
 * A replacement of the Data Validation&rsquo;s NotValidator so the inner
 * validator can be set from configuration. It behaves exactly the same way.
 * <p>
 * This class implements ConfigurableValidator so it can be set with the
 * DataValidationRegistrationValidator manager, available via the
 * getRegistrationValidator method, provides all necessary managers and services
 * for this validator. It also provides the Log for logging.
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
 *                    &lt;Property name=&quot;innerValidator&quot;&gt;
 *                        &lt;Value&gt;myValidator&lt;/Value&gt;
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
 * <p>
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class NotValidator extends AbstractObjectValidator implements
        ConfigurableValidator {
    /**
     * <p>
     * Represents the class name used to log.
     * </p>
     *
     */
    private static final String CLASS_NAME = "NotValidator";

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
     * Name of the property that stores the inner configurable validator key to
     * pass to the object factory.
     * </p>
     * <p>
     * This property is required. The value of this property should be any valid
     * object factory key.
     * </p>
     *
     */
    private static final String INNER_VALIDATOR_KEY_PROPERTYNAME = "innerValidator";

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
     * Represents the validator that will be negated in this validator.
     * </p>
     * <p>
     * It is set in the constructor to a non-null value, and will never change.
     * </p>
     *
     */
    private final ConfigurableValidator innerValidator;

    /**
     * Creates a new <code>NotValidator</code> that uses
     * <code>validator</code> as the underlying
     * <code>ConfigurableValidator</code> and is initialized with a specified
     * resource bundle information.
     *
     *
     * @param validator
     *            the underlying <code>ObjectValidator</code> to use.
     * @param bundleInfo
     *            The resource bundle info
     * @throws IllegalArgumentException
     *             if <code>validator</code> is <code>null</code>.
     */
    public NotValidator(ConfigurableValidator validator, BundleInfo bundleInfo) {
        super(bundleInfo);
        RegistrationValidationHelper.validateNotNull(validator, "validator");
        this.innerValidator = validator;
    }

    /**
     * Initializes this instance from configuration info from the Config
     * Manager. Uses object factory to get instance of innerValidator.
     *
     *
     * @param namespace
     *            The configuration namespace
     * @throws IllegalArgumentException
     *             If namespace is null/empty
     * @throws RegistrationValidationConfigurationException
     *             If any configuration error occurs, such as unknown namespace
     *             or missing required values.
     */
    public NotValidator(String namespace) {
        super("temporary");
        RegistrationValidationHelper.validateStringNotNullOrEmpty(namespace,
                "namespace");

        // Creates BundleInfo from configuration information
        BundleInfo bundleInfo = RegistrationValidationHelper
                .createBundleInfo(namespace);
        // Sets the BundleInfo
        // If the bundleName is null, then only sets the defaultMessage,
        // Else sets bundleInfo using the
        // super.setResourceBundleInfo(BundleInfo)
        if (bundleInfo.getBundleName() == null) {
            getBundleInfo().setDefaultMessage(bundleInfo.getDefaultMessage());
        } else {
            setResourceBundleInfo(bundleInfo);
        }

        try {
            // loads namespace for object factory
            String objectFactoryNamespace = RegistrationValidationHelper
                    .getString(namespace, OBJECT_FACTORY_PROPERTYNAME, true);

            // creates object factory from the given namespace
            ObjectFactory factory = new ObjectFactory(
                    new ConfigManagerSpecificationFactory(
                            objectFactoryNamespace));

            this.innerValidator = (ConfigurableValidator) RegistrationValidationHelper
                    .createObjectFromObjectFactory(namespace,
                            INNER_VALIDATOR_KEY_PROPERTYNAME, factory,
                            ConfigurableValidator.class, true);
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
     * Also sets it to all its child.
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
        innerValidator.setRegistrationValidator(registrationValidator);
    }

    /**
     * Validates the given <code>Object</code>.
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
        try {
            boolean result = !(innerValidator.valid(obj));

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
        String methodName = CLASS_NAME + ".getMessage(Object obj)";
        Log logger = registrationValidator.getLog();
        // log entrance
        RegistrationValidationHelper.log(logger, Level.INFO, "enter " + methodName);

        try {
            String innerMessage = innerValidator.getMessage(obj);
            String message = null;
            if (innerMessage == null) {
                RegistrationValidationHelper.validateIsValidationInfo(obj,
                        "Object to validate");
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
        } catch (ValidationProcessingException e) {
            RegistrationValidationHelper.log(logger, Level.ERROR, "Error[" + e.getClass().getName()
                + "] occurs in " + methodName + ", cause:" + e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            RegistrationValidationHelper.log(logger, Level.ERROR, "Error[" + e.getClass().getName()
                + "] occurs in " + methodName + ", cause:" + e.getMessage());
            throw e;
        }
    }

    /**
     * <p>
     * Gives error information about the given object being validated. This
     * method will return possibly a number of messages produced for this object
     * by a number of validators if the validator is composite.
     * </p>
     *
     * @return String[] a non-empty but possibly null array of failure messages
     * @param obj
     *            the object to validate
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

        String message = this.getMessage(obj);
        String[] messages = (message == null) ? null : new String[] {message };

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
     *
     * @return String[] a non-empty but possibly null array of failure messages
     * @param obj
     *            the object to validate
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
        String[] messages = getMessages(obj);

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
     *
     * @return String[] a non-empty but possibly null array of failure messages
     * @param obj
     *            the object to validate
     * @param messageLimit
     *            the max number of messages. This must be greater-than-or-equal
     *            to 0.this method will just defer to the getMassages(object) method
     *            and ignore the messageLimit parameter.
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

            // This method will just defer to the getMessages(Object) method
            //and ignore the messageLimit parameter.
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