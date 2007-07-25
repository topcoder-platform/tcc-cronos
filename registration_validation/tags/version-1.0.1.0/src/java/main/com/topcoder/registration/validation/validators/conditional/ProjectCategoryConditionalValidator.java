/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.validators.conditional;

import com.topcoder.management.project.Project;
import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.registration.validation.AbstractConfigurableValidator;
import com.topcoder.registration.validation.RegistrationValidationHelper;
import com.topcoder.registration.validation.ConfigurableValidator;
import com.topcoder.registration.validation.DataValidationRegistrationValidator;
import com.topcoder.registration.validation.RegistrationValidationConfigurationException;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.Level;

/**
 * A conditional validator that checks if the project is of a given category to
 * see if validation should be performed. The validation only proceeds to an
 * inner validator if it is.
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
 * component, the <b>ConfigManager</b> and <b>ObjectFactory</b> components are
 * used.
 * </p>
 *
 * <p>
 * Here is the sample configuration file for this class.
 *
 * <pre>
 *                &lt;Config name=&quot;test.Validator;&gt;
 *                    &lt;Property name=&quot;specNamespace&quot;&gt;
 *                        &lt;Value&gt;com.topcoder.specify&lt;/Value&gt;
 *                    &lt;/Property&gt;
 *                    &lt;Property name=&quot;innerValidator&quot;&gt;
 *                        &lt;Value&gt;myValidator&lt;/Value&gt;
 *                    &lt;/Property&gt;
 *                    &lt;Property name=&quot;projectCategoryId&quot;&gt;
 *                        &lt;Value&gt;3&lt;/Value&gt;
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
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class ProjectCategoryConditionalValidator extends
        AbstractConfigurableValidator {
    /**
     * <p>
     * Represents the class name used to log.
     * </p>
     *
     */
    private static final String CLASS_NAME = "ProjectCategoryConditionalValidator";

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
     * Name of the property that stores the ID of a project category the project
     * would have to perform the validation.
     * </p>
     * <p>
     * This property is required. This must can be parsed into a non-negative
     * long value.
     * </p>
     *
     */
    private static final String PROJECT_CATEGORY_ID_PROPERTYNAME = "projectCategoryId";

    /**
     * <p>
     * Represents the validator that will be called if the condition in this
     * validator is meet.
     * </p>
     * <p>
     * It is set in the constructor to a non-null value, and will never change.
     * </p>
     *
     */
    private final ConfigurableValidator innerValidator;

    /**
     * <p>
     * Represents the ID of a project category the project would have to perform
     * the validation.
     * </p>
     * <p>
     * It is set in the constructor to a non-negative value, and will never
     * change.
     * </p>
     *
     */
    private final long projectCategoryId;

    /**
     * Initializes the inner validator and the project category ID based on the
     * input parameters.
     *
     * @param innerValidator
     *            the validator that will be called if the condition in this
     *            validator is meet
     * @param projectCategoryId
     *            the ID of a project category the project would have to perform
     *            the validation
     * @throws IllegalArgumentException
     *             if innerValidator is null or projectCategoryId is negative
     */
    public ProjectCategoryConditionalValidator(
            ConfigurableValidator innerValidator, long projectCategoryId) {
        super("temporary");
        RegistrationValidationHelper.validateNotNull(innerValidator,
                "innerValidator");
        RegistrationValidationHelper.validateNotNegative(projectCategoryId,
                "projectCategoryId");

        this.innerValidator = innerValidator;
        this.projectCategoryId = projectCategoryId;
    }

    /**
     * Initializes the inner validator and the project category ID from
     * configuration information from the <b>ConfigManager</b>.
     *
     * @param namespace
     *            The configuration namespace
     * @throws IllegalArgumentException
     *             If namespace is null/empty
     * @throws RegistrationValidationConfigurationException
     *             If any configuration error occurs, such as unknown namespace
     *             or missing required values, or an inability to create the
     *             validator
     */
    public ProjectCategoryConditionalValidator(String namespace) {
        super("temporary");
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

            // Creates innerValidator using the ObjectFactory
            this.innerValidator = (ConfigurableValidator) RegistrationValidationHelper
                    .createObjectFromObjectFactory(namespace,
                            INNER_VALIDATOR_KEY_PROPERTYNAME, factory,
                            ConfigurableValidator.class, true);

            // Gets the project category id from the corresponding property
            this.projectCategoryId = RegistrationValidationHelper.getLong(
                    namespace, PROJECT_CATEGORY_ID_PROPERTYNAME).longValue();

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
     * Checks if the project is of a given category to see if validation should
     * be performed.
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

            // Initializes the message to be null.
            // The message will be set to be the message of the inner validation
            // if the condition is true.
            String message = null;

            // Gets the project category
            Project project = ((ValidationInfo) obj).getProject()
                    .getProjectHeader();
            long id = project.getProjectCategory().getId();

            // Performs the inner validation if the project is of the
            // given category
            if (id == this.projectCategoryId) {
                message = innerValidator.getMessage(obj);
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
     * <p>
     * Sets the DataValidationRegistrationValidator so both of this validator
     * and inner validator have access to the services of this
     * RegistrationValidator.
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
        super.setRegistrationValidator(dataValidationRegistrationValidator);
        innerValidator
                .setRegistrationValidator(dataValidationRegistrationValidator);

    }

}
