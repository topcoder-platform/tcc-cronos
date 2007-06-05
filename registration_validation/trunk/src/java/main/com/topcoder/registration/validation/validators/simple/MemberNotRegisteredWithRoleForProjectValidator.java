/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.validators.simple;

import com.topcoder.registration.validation.AbstractConfigurableValidator;
import com.topcoder.registration.validation.ValidationProcessingException;
import com.topcoder.registration.validation.RegistrationValidationHelper;
import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.util.datavalidator.BundleInfo;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.Level;

/**
 * A simple validator that checks if a member is not registered with a given
 * role for a project.
 * <p>
 * This class extends AbstractConfigurableValidator that implements most of the
 * validation methods, so this class only needs to implement the getMessage
 * method.
 * </p>
 *
 * <p>
 * The DataValidationRegistrationValidator manager, available via the
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
 *                    &lt;Property name=&quot;roleId&quot;&gt;
 *                        &lt;Value&gt;2&lt;/Value&gt;
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
public class MemberNotRegisteredWithRoleForProjectValidator extends
        AbstractConfigurableValidator {
    /**
     * <p>
     * Represents the class name used to log.
     * </p>
     *
     */
    private static final String CLASS_NAME = "MemberNotRegisteredWithRoleForProjectValidator";

    /**
     * <p>
     * Name of the property that stores the role ID that the member should not
     * have.
     * </p>
     * <p>
     * This property is required. This must can be parsed into a non-negative
     * long value.
     * </p>
     *
     */
    private static final String ROLE_ID = "roleId";

    /**
     * <p>
     * Represents the ID of the role that a member should not be registered
     * with.
     * </p>
     * <p>
     * It is set in the constructor to a non-negative value, and will never
     * change.
     * </p>
     *
     */
    private final long roleId;

    /**
     * Initializes the resource bundle and the registration role ID based on the
     * input parameters.
     *
     *
     * @param bundleInfo
     *            The resource bundle info
     * @param roleId
     *            the ID of the role that a member should not be registered with
     * @throws IllegalArgumentException
     *             if any of the parameters in bundleInfo is null or an empty
     *             string (for string parameters)
     * @throws IllegalArgumentException
     *             if roleId is negative
     */
    public MemberNotRegisteredWithRoleForProjectValidator(
            BundleInfo bundleInfo, long roleId) {
        super(bundleInfo);
        RegistrationValidationHelper.validateNotNegative(roleId, "roleId");
        this.roleId = roleId;
    }

    /**
     * Initializes the resource bundle and the registration role ID from
     * configuration information from the <b>ConfigManager</b>.
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
    public MemberNotRegisteredWithRoleForProjectValidator(String namespace) {
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

        // set the registration role ID
        this.roleId = RegistrationValidationHelper.getLong(namespace, ROLE_ID)
                .intValue();

    }

    /**
     * <p>
     * Checks that a member has not been already registered with a given role.
     * </p>
     *
     *
     * @return null if obj is valid. Otherwise, an error message is returned.
     * @param obj
     *            Object to validate.
     * @throws IllegalArgumentException
     *             If obj is null or not a ValidationInfo object
     * @throws ValidationProcessingException
     *             If an unexpected system error occurs
     */
    public String getMessage(Object obj) {

        String methodName = CLASS_NAME + ".getMessage(Object obj)";
        Log logger = getRegistrationValidator().getLog();

        // log entrance
        RegistrationValidationHelper.log(logger, Level.INFO, "enter " + methodName);

        try {
            RegistrationValidationHelper.validateIsValidationInfo(obj,
                    "Object to validate");

            ValidationInfo validationInfo = (ValidationInfo) obj;
            // Gets the roleId that the member is registered with.
            long registrationRoleId = validationInfo.getRegistration()
                    .getRoleId();

            // Initializes the message to be null.
            // The message will be filled if the validation is failed.
            String message = null;

            // Fills the message if the member is registered with this.roleId
            if (registrationRoleId == this.roleId) {
                String messageTemplate = this.getValidationMessage();
                String data = RegistrationValidationHelper
                        .buildStandInfo(validationInfo);
                data = RegistrationValidationHelper.appendInnerDataValue(data,
                        "ROLE_ID", "" + this.roleId);
                // Log variable value
                RegistrationValidationHelper.log(logger, Level.DEBUG,
                    "the value of data stirng is [" + data + "]");

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

}
