/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.validators.simple;

import com.topcoder.project.service.ProjectServices;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.management.resource.Resource;
import com.topcoder.registration.validation.AbstractConfigurableValidator;
import com.topcoder.registration.validation.ValidationProcessingException;
import com.topcoder.registration.validation.RegistrationValidationHelper;
import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.util.datavalidator.BundleInfo;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.Level;

/**
 * A simple validator that checks that a member has not exceeded the maximum
 * amount of projects one can register for. ProjectServices is used here.
 * <p>
 * This class extends AbstractConfigurableValidator that implements most of the
 * validation methods, so this class only needs to implement the getMessage
 * method.
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
 *                    &lt;Property name=&quot;maxRegistrationCount&quot;&gt;
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
 * The DataValidationRegistrationValidator manager, available via the
 * getRegistrationValidator method, provides all necessary managers and services
 * for this validator. It also provides the Log for logging.
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
public class MemberNotExceededMaxProjectRegistrationLimitValidator extends
        AbstractConfigurableValidator {
    /**
     * <p>
     * Represents the class name used to log.
     * </p>
     *
     */
    private static final String CLASS_NAME = "MemberNotExceededMaxProjectRegistrationLimitValidator";

    /**
     * <p>
     * Name of the property that stores the maximum number of projects the
     * member can be registered for.
     * </p>
     * <p>
     * This property is required. This must can be parsed into a non-negative
     * integer value.
     * </p>
     *
     */
    private static final String MAX_REGISTRATION_COUNT_PROPERTYNAME = "maxRegistrationCount";

    /**
     * <p>
     * Represents the maximum number of projects the member can be registered
     * for.
     * </p>
     *
     * <p>
     * It is set in the constructor to a non-negative value, and will never
     * change.
     * </p>
     *
     *
     */
    private final int maxRegistrationCount;

    /**
     * Initializes the resource bundle and the maximum number of projects the
     * member can be registered for based on the input parameters.
     *
     * @param bundleInfo
     *            The resource bundle info
     * @param maxRegistrationCount
     *            the maximum number of projects the member can be registered
     *            for
     * @throws IllegalArgumentException
     *             if any of the parameters in bundleInfo is null or an empty
     *             string (for string parameters)
     * @throws IllegalArgumentException
     *             if maxRegistrationCount is negative
     */
    public MemberNotExceededMaxProjectRegistrationLimitValidator(
            BundleInfo bundleInfo, int maxRegistrationCount) {
        super(bundleInfo);
        RegistrationValidationHelper.validateNotNegative(maxRegistrationCount,
                "maxRegistrationCount");
        this.maxRegistrationCount = maxRegistrationCount;
    }

    /**
     * Initializes the resource bundle and the maximum number of projects the
     * member can be registered for from configuration information from the
     * <b>ConfigManager</b>.
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
    public MemberNotExceededMaxProjectRegistrationLimitValidator(
            String namespace) {
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

        // set the maximum number of projects the member can be registered for
        this.maxRegistrationCount = RegistrationValidationHelper.getInteger(
                namespace, MAX_REGISTRATION_COUNT_PROPERTYNAME).intValue();

    }

    /**
     * <p>
     * Checks that a member has not exceeded the maximum amount of projects one
     * can register for.
     * </p>
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

            // Find resource in validationInfo.project.resources whose custom
            // property &quot;External Reference ID&quot; is equal to
            // validationInfo.registrationInfo.userId
            Resource resource = RegistrationValidationHelper.findResource(
                    validationInfo, logger);

            // Calls projectServices.findActiveProjects() and counts all
            // projects whose activeProject.resources contains this resource.id
            int registrationCount = countProjectRegistration(resource, logger);

            // Initializes the message to be null.
            // The message will be filled if the validation is failed.
            String message = null;

            // Fills the message if the count is above this.maxRegistrationCount
            if (registrationCount > this.maxRegistrationCount) {
                String messageTemplate = this.getValidationMessage();
                String data = RegistrationValidationHelper
                        .buildStandInfo(validationInfo);
                data = RegistrationValidationHelper.appendInnerDataValue(data,
                        "MAX_REGISTRATION_COUNT", ""
                                + this.maxRegistrationCount);
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

    /**
     * <p>
     * Counts all projects whose activeProject.resources contains this
     * resource.id.
     * </p>
     *
     *
     * @param resource
     *            the resource used to count the project registration
     * @param logger
     *            the logger to log nothing
     * @return the number of the registration
     * @throws IllegalArgumentException
     *             If obj is null or not a ValidationInfo object
     * @throws ValidationProcessingException
     *             If an unexpected system error occurs
     */
    private int countProjectRegistration(Resource resource, Log logger) {

        // Calls projectServices.findActiveProjects() to get the activeProjects
        ProjectServices projectServices = getRegistrationValidator()
                .getProjectServices();
        RegistrationValidationHelper.log(logger, Level.DEBUG,
            "Starts calling ProjectServices#findActiveProjects()");
        FullProjectData[] projects = projectServices.findActiveProjects();
        RegistrationValidationHelper.log(logger, Level.DEBUG,
            "Finishes calling ProjectServices#findActiveProjects()");

        // Counts all projects whose activeProject.resources contains this
        // resource.id
        int registrationCount = 0;
        if ((resource != null) && (projects != null)) {
            for (int i = 0; i < projects.length; i++) {
                Resource[] projectResources = projects[i].getResources();
                for (int j = 0; j < projectResources.length; j++) {
                    if (projectResources[j].getId() == resource.getId()) {
                        registrationCount++;
                        break;
                    }
                }
            }
        }
        return registrationCount;
    }
}
