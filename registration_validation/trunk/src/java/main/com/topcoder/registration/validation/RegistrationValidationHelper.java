/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation;

import com.topcoder.util.datavalidator.BundleInfo;
import com.cronos.onlinereview.external.ExternalUser;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.InvalidConfigException;
import com.topcoder.util.file.Template;
import com.topcoder.util.file.TemplateDataFormatException;
import com.topcoder.util.file.TemplateFormatException;
import com.topcoder.util.file.templatesource.TemplateSourceException;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.project.Project;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.Level;

import java.util.Locale;

/**
 * <p>
 * This helper class contains methods for this component to use.
 * </p>
 * <p>
 * Thread safety: This class is thread safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class RegistrationValidationHelper {
    /**
     * <p>
     * Name of the property that stores the bundle name.
     * </p>
     * <p>
     * This property is required if defaultMessage is null.
     * </p>
     *
     */
    private static final String BUNDLE_NAME_PROPERTYNAME = "bundleName";

    /**
     * <p>
     * Name of the property that stores the language portion of the Locale to
     * use with bundle.
     * </p>
     * <p>
     * This property is optional and use default locale if not given.
     * </p>
     *
     */
    private static final String BUNDLE_LOCAL_LANGUAGE_PROPERTYNAME = "bundleLocaleLanguage";

    /**
     * <p>
     * Name of the property that stores the Country portion of the Locale to use
     * with bundle.
     * </p>
     * <p>
     * This property is optional and only applicable if bundleLocaleLanguage is
     * provided.
     * </p>
     *
     */
    private static final String BUNDLE_LOCAL_COUNTRY_PROPERTYNAME = "bundleLocaleCountry";

    /**
     * <p>
     * Name of the property that stores the variant portion of the variant
     * portion of the Locale to use with bundle.
     * </p>
     * <p>
     * This property is optional and only applicable if bundleLocaleCountry is
     * provided.
     * </p>
     *
     */
    private static final String BUNDLE_LOCAL_VARIANT_PROPERTYNAME = "bundleLocaleVariant";

    /**
     * <p>
     * Name of the property that stores the key to use to get message from
     * resource bundle.
     * </p>
     * <p>
     * This property is Required if bundleName is used. The value of this
     * property should be any non-null/empty key
     * </p>
     *
     */
    private static final String MESSAGE_KEY_PROPERTYNAME = "messageKey";

    /**
     * <p>
     * Name of the property that stores the default template name to use if
     * there is no bundle or if the bundle does not have an entry.
     * </p>
     * <p>
     * This property is required.
     * </p>
     *
     */
    private static final String DEFAULT_MESSAGE_PROPERTYNAME = "defaultMessage";

    /**
     * <p>
     * Private constructor to prevent this class being instantiated.
     * </p>
     */
    private RegistrationValidationHelper() {
        // empty
    }

    /**
     * <p>
     * Validates whether the given Object is not null.
     * </p>
     *
     * @param obj
     *            the argument to check
     * @param name
     *            the name of the argument
     *
     * @throws IllegalArgumentException
     *             if the given Object(obj) is null
     */
    public static void validateNotNull(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException("[" + name
                    + "] should not be null.");
        }
    }

    /**
     * <p>
     * Validates whether the given Object is not null.
     * </p>
     *
     * @param objArray
     *            the argument to check
     * @param name
     *            the name of the argument
     *
     * @throws IllegalArgumentException
     *             if the given Object(obj) is null
     */
    public static void validateArrayNotNullOrContainsNull(Object[] objArray,
            String name) {
        validateNotNull(objArray, name);
        for (int i = 0; i < objArray.length; i++) {
            validateNotNull(objArray[i], name + "[" + i + "]");
        }
    }

    /**
     * <p>
     * Validates whether the given String is null or empty after trim.
     * </p>
     *
     * @param arg
     *            the String to check
     * @param name
     *            the name of the String argument to check
     * @return the original object to check
     *
     * @throws IllegalArgumentException
     *             if the given string is null or empty after trim.
     */
    public static String validateStringNotNullOrEmpty(String arg, String name) {
        validateNotNull(arg, name);
        if (arg.trim().length() == 0) {
            throw new IllegalArgumentException("[" + name
                    + "] should not be empty after trim.");
        }
        return arg;
    }

    /**
     * <p>
     * Validates whether the given int is negative.
     * </p>
     *
     * @param arg
     *            the value to check
     * @param name
     *            the name of the value argument to check
     * @return the original object to check
     *
     * @throws IllegalArgumentException
     *             if the given value is negative.
     */
    public static int validateNotNegative(int arg, String name) {
        if (arg < 0) {
            String message = "[" + name + "] should not be negative.";
            throw new IllegalArgumentException(message);
        }
        return arg;
    }

    /**
     * <p>
     * Validates whether the given double is negative.
     * </p>
     *
     * @param arg
     *            the value to check
     * @param name
     *            the name of the value argument to check
     * @return the original object to check
     *
     * @throws IllegalArgumentException
     *             if the given value is negative.
     */
    public static double validateNotNegative(double arg, String name) {
        validateNotNegative(new Double(arg).compareTo(new Double(0)), "name");
        return arg;
    }

    /**
     * <p>
     * Validates whether the given long is negative.
     * </p>
     *
     * @param arg
     *            the value to check
     * @param name
     *            the name of the value argument to check
     * @return the original object to check
     *
     * @throws IllegalArgumentException
     *             if the given value is negative.
     */
    public static long validateNotNegative(long arg, String name) {
        if (arg < 0) {
            String message = "[" + name + "] should not be negative.";
            throw new IllegalArgumentException(message);
        }
        return arg;
    }

    /**
     * <p>
     * Validates whether the given Object is not ValidationInfo.
     * </p>
     *
     * @param obj
     *            the argument to check
     * @param name
     *            the name of the argument
     *
     * @throws IllegalArgumentException
     *             if the given Object(obj) is not ValidationInfo
     */
    public static void validateIsValidationInfo(Object obj, String name) {
        validateNotNull(obj, name);
        if (!(obj instanceof ValidationInfo)) {
            String message = "[" + name
                    + "] should be a ValidationInfo object.";
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * <p>
     * Find resource in validationInfo.project.resources whose custom property
     * &quot;External Reference ID&quot; is equal to
     * validationInfo.registrationInfo.userId.
     * </p>
     *
     * @param validationInfo
     *            the validationInfo which contain the information used to find
     *            the resource
     * @param logger
     *            the logger used to log
     * @return the found resource, or null if no resource is found
     *
     * @throws ValidationProcessingException
     *             If an unexpected system error occurs
     */
    public static Resource findResource(ValidationInfo validationInfo,
            Log logger) {

        Resource[] resources = validationInfo.getProject().getResources();
        long userId = validationInfo.getRegistration().getUserId();
        // Log variable value
        RegistrationValidationHelper.log(logger, Level.DEBUG,
            "the value of validationInfo.registrationInfo.userId is [" + userId + "]");

        Resource resource = null;
        for (int i = 0; i < resources.length; i++) {
            Long externalReferenceID = (Long) resources[i]
                    .getProperty("External Reference ID");

            // Log variable value
            RegistrationValidationHelper.log(logger, Level.DEBUG,
                "the value ofvalidationInfo.project.resource[" + i
                + "].externalReferenceID is [" + externalReferenceID + "]");
            if ((new Long(userId)).compareTo(externalReferenceID) == 0) {
                resource = resources[i];
                break;
            }
        }
        return resource;
    }

    /**
     * <p>
     * Validates whether the given string array contains null or empty elements.
     * </p>
     *
     * @param successful
     *            a flag as to whether the operation was successful
     * @param errors
     *            the error messages, potentially, if the operation was not
     *            successful
     * @throws IllegalArgumentException
     *             if errors is a non-empty array but successful is true
     * @throws IllegalArgumentException
     *             if errors contains null/empty elements in the array
     */
    public static void validateErrorMessages(boolean successful, String[] errors) {
        if (errors != null) {
            for (int i = 0; i < errors.length; i++) {
                validateStringNotNullOrEmpty(errors[i], "the [" + i
                        + "] element of errors");
            }
        }

        if (successful) {
            if ((errors != null) && (errors.length > 0)) {
                throw new IllegalArgumentException(
                        " errors is a non-empty array but successful is true");
            }
        }
    }

    /**
     * <p>
     * Creates an object using given key by the ObjectFatory.
     * </p>
     *
     * @param namespace
     *            the configuration namespace
     * @param propertyName
     *            the name of property
     * @param factory
     *            the Object factory which is used to create an object
     * @param type
     *            the expected created type
     * @param required
     *            the flag to identify whether the property is required or not
     * @return the created Object. When the property can not be found in the
     *         namespace, return null if required flag is false or throw
     *         RegistrationValidationConfigurationException if required flag is
     *         true.
     * @throws RegistrationValidationConfigurationException
     *             if the property does not exist and the required flag is true ,
     *             the created object is not of the expected type or
     *             ObjectFactory fail to create object using the key
     */
    public static Object createObjectFromObjectFactory(String namespace,
            String propertyName, ObjectFactory factory, Class type,
            boolean required) {
        try {
            String key = getString(namespace, propertyName, required);
            if (key == null) {
                return null;
            }
            Object createdObject = factory.createObject(key);

            // makes sure the created object is the expected type
            if (!type.isInstance(createdObject)) {
                throw new RegistrationValidationConfigurationException(
                        "The class created is not correct: It should be ["
                                + type.getName() + "], but now it is ["
                                + createdObject.getClass().getName() + "].");
            }
            return createdObject;
        } catch (InvalidClassSpecificationException icse) {
            throw new RegistrationValidationConfigurationException(
                    "Failed to create [" + type.getName()
                            + "] by the ObjectFactory", icse);
        }
    }


    /**
     * <p>
     * Creates an BundleInfo instance using given namespace.
     * </p>
     *
     * @param namespace
     *            the configuration namespace
     * @return the created BundleInfo instance
     * @throws RegistrationValidationConfigurationException
     *             if fail to create BundleInfo instance using the namespace
     */
    public static BundleInfo createBundleInfo(String namespace) {
        // get the bundle name
        String bundleName = RegistrationValidationHelper.getString(namespace,
                BUNDLE_NAME_PROPERTYNAME, false);

        // get the bundle locale language
        String bundleLocaleLanguage = RegistrationValidationHelper.getString(
                namespace, BUNDLE_LOCAL_LANGUAGE_PROPERTYNAME, false);
        // get the bundle locale country
        String bundleLocaleCountry = RegistrationValidationHelper.getString(
                namespace, BUNDLE_LOCAL_COUNTRY_PROPERTYNAME, false);
        // get the bundle locale variant
        String bundleLocaleVariant = RegistrationValidationHelper.getString(
                namespace, BUNDLE_LOCAL_VARIANT_PROPERTYNAME, false);
        // get the message key
        String messageKey = RegistrationValidationHelper.getString(namespace,
                MESSAGE_KEY_PROPERTYNAME, false);

        // get the default message
        String defaultMessage = RegistrationValidationHelper.getString(
                namespace, DEFAULT_MESSAGE_PROPERTYNAME, false);

        BundleInfo bundleInfo = new BundleInfo();

        Locale local = null;
        if ((bundleLocaleLanguage != null) && (bundleLocaleCountry != null)
                && (bundleLocaleVariant != null)) {
            // create local setting
            local = new Locale(bundleLocaleLanguage, bundleLocaleCountry,
                    bundleLocaleVariant);
        } else if ((bundleLocaleLanguage != null)
                && (bundleLocaleCountry != null)) {
            local = new Locale(bundleLocaleLanguage, bundleLocaleCountry);
        } else if ((bundleLocaleLanguage != null)) {
            local = new Locale(bundleLocaleLanguage);
        }

        if ((bundleName == null) && (defaultMessage == null)) {
            throw new RegistrationValidationConfigurationException(
                    " both of bundleName and defaultMessage are missing in the namespace["
                            + namespace + "]");
        }
        if (bundleName != null) {
            if (local != null) {
                // set the bundleName and local
                bundleInfo.setBundle(bundleName, local);
            } else {
                bundleInfo.setBundle(bundleName);
            }
            if (messageKey == null) {
                throw new RegistrationValidationConfigurationException(
                        "messageKey is required when bundleName is used in the namespace["
                                + namespace + "]");
            }
            // set the message key
            bundleInfo.setMessageKey(messageKey);

        }
        if (defaultMessage != null) {
            // set the default message
            bundleInfo.setDefaultMessage(defaultMessage);
        }

        return bundleInfo;
    }

    /**
     * <p>
     * Gets the value of the property in the given namespace and return is as an
     * Integer.
     * </p>
     *
     * <p>
     * If the property is missing in the configuration or the property value
     * cannot be parse as an Integer, then
     * <code>RegistrationValidationConfigurationException</code> will be
     * thrown.
     * </p>
     *
     * @param namespace
     *            the configuration namespace
     * @param propertyName
     *            the name of property
     *
     * @return the parsed property value
     * @throws RegistrationValidationConfigurationException
     *             in case the property does not exist, or the property value
     *             cannot be parse as an Integer, or the value is negative
     */
    public static Integer getInteger(String namespace, String propertyName) {
        String value = getString(namespace, propertyName, true);

        // parse the attribute value as an integer
        try {
            Integer parsedInteger = new Integer(Integer.parseInt(value));
            if (parsedInteger.intValue() < 0) {
                throw new RegistrationValidationConfigurationException(
                        "the value of the property[" + propertyName
                                + "] should not be negative.");
            }
            return parsedInteger;
        } catch (NumberFormatException e) {
            throw new RegistrationValidationConfigurationException("The value["
                    + value + "] of the property[" + propertyName
                    + "] is not an Integer.", e);
        }
    }

    /**
     * <p>
     * Gets the value of the property in the given namespace and return is as a
     * Long.
     * </p>
     *
     * <p>
     * If the property is missing in the configuration or the property value
     * cannot be parse as a Long, then
     * <code>RegistrationValidationConfigurationException</code> will be
     * thrown.
     * </p>
     *
     * @param namespace
     *            the configuration namespace
     * @param propertyName
     *            the name of property
     *
     * @return the parsed property value
     * @throws RegistrationValidationConfigurationException
     *             in case the property does not exist, or the property value
     *             cannot be parse as a Long, or the value is negative
     */
    public static Long getLong(String namespace, String propertyName) {
        String value = getString(namespace, propertyName, true);

        // parse the attribute value as an integer
        try {
            Long parsedLong = new Long(Long.parseLong(value));
            if (parsedLong.longValue() < 0) {
                throw new RegistrationValidationConfigurationException(
                        "the value of the property[" + propertyName
                                + "] should not be negative.");
            }
            return parsedLong;
        } catch (NumberFormatException e) {
            throw new RegistrationValidationConfigurationException("The value["
                    + value + "] of the property[" + propertyName
                    + "] is not a Long.", e);
        }

    }

    /**
     * <p>
     * Gets the value of the property in the given namespace and return is as a
     * Long.
     * </p>
     *
     * <p>
     * If the property is missing in the configuration or the property value
     * cannot be parse as a Long, then
     * <code>RegistrationValidationConfigurationException</code> will be
     * thrown.
     * </p>
     *
     * @param namespace
     *            the configuration namespace
     * @param propertyName
     *            the name of property
     * @return the parsed property value
     * @throws RegistrationValidationConfigurationException
     *             in case the property does not exist, or the property value
     *             cannot be parse to a Boolean
     */
    public static boolean getBoolean(String namespace, String propertyName) {
        // Gets the  property value
        // Throws RegistrationValidationConfigurationException
        // if the property does not exist
        String value = getString(namespace, propertyName, true);
        if ((value.compareToIgnoreCase("true") == 0)) {
            // Return true if the property value equals to "true", ignoring case.
            return true;
        } else if ((value.compareToIgnoreCase("false") == 0)) {
            // Return false if the property value equals to "false", ignoring case.
            return false;
        } else {
            // Throws RegistrationValidationConfigurationException
            // if the property value cannot be parse to a Boolean
            throw new RegistrationValidationConfigurationException("The value["
                    + value + "] of the property[" + propertyName
                    + "] is not a boolean.");
        }
    }

    /**
     * <p>
     * Gets the value of the property in the given namespace and return is as a
     * Double.
     * </p>
     *
     * <p>
     * If the property is missing in the configuration or the property value
     * cannot be parse as a Long, then
     * <code>RegistrationValidationConfigurationException</code> will be
     * thrown.
     * </p>
     *
     * @param namespace
     *            the configuration namespace
     * @param propertyName
     *            the name of property
     *
     * @return the value of the property
     *
     * @throws RegistrationValidationConfigurationException
     *             in case the property does not exist, or the property value
     *             cannot be parse as a Double, or the value is negative
     */
    public static Double getDouble(String namespace, String propertyName) {
        String value = getString(namespace, propertyName, true);

        try {
            Double parsedDouble = new Double(Double.parseDouble(value));

            if (parsedDouble.compareTo(new Double(0)) < 0) {
                throw new RegistrationValidationConfigurationException(
                        "the value of the property[" + propertyName
                                + "] should not be negative.");
            }
            return parsedDouble;
        } catch (NumberFormatException e) {
            throw new RegistrationValidationConfigurationException("The value["
                    + value + "] of the property[" + propertyName
                    + "] is not a Double.", e);
        }

    }

    /**
     * <p>
     * Returns the value of the property in the given namespace.
     * </p>
     *
     * <p>
     * If the property is missing in the configuration, then
     * <code>RegistrationValidationConfigurationException</code> will be
     * thrown.
     * </p>
     *
     * @param namespace
     *            the namespace to get
     * @param propertyName
     *            the name of property
     * @param required
     *            the flag to identify whether the property is required or not
     *
     * @return the value of the property
     *
     * @throws RegistrationValidationConfigurationException
     *             if fail to load the config values
     */
    public static String getString(String namespace, String propertyName,
            boolean required) {
        try {
            String property = ConfigManager.getInstance().getString(namespace,
                    propertyName);

            // this property is missed
            if (property == null) {
                if (required) {
                    throw new RegistrationValidationConfigurationException(
                            "Property [" + propertyName
                                    + "] is missing in namespace[" + namespace
                                    + "].");
                } else {
                    return null;
                }
            }
            // empty property value is not allowed
            if ((property.trim().length() == 0)) {
                if (required) {
                    throw new RegistrationValidationConfigurationException(
                            "Property value of [" + propertyName
                                    + "] is empty in namespace[" + namespace
                                    + "].");
                } else {
                    return null;
                }
            }

            return property.trim();

        } catch (UnknownNamespaceException e) {
            throw new RegistrationValidationConfigurationException(
                    "UnknownNamespaceException occurs when accessing ConfigManager.",
                    e);
        }

    }

    /**
     * <p>
     * Appends inner data value at the end of the input original data.
     * </p>
     *
     * <p>
     * If the input inner data value is null, using empty string as inner data
     * value.
     * </p>
     *
     * @param originalData
     *            the original data string
     * @param innerDataName
     *            the inner data name
     * @param innerDataValue
     *            the inner data value
     * @return the data string after append the inner data value with the inner
     *         data name
     *
     */
    public static String appendInnerDataValue(String originalData,
            String innerDataName, String innerDataValue) {
        if (innerDataValue == null) {
            // if the input innerDataValue is null, then use empty string.
            innerDataValue = "";
        }

        StringBuffer sb = new StringBuffer(originalData);
        StringBuffer innerData = new StringBuffer();
        innerData.append("<");
        innerData.append(innerDataName);
        innerData.append(">");
        innerData.append(innerDataValue);
        innerData.append("</");
        innerData.append(innerDataName);
        innerData.append(">");

        sb.insert(sb.lastIndexOf("</"), innerData);
        sb.insert(sb.lastIndexOf("</"), "\n");
        return sb.toString();
    }

    /**
     * <p>
     * Appends inner data at the end of the input original data.
     * </p>
     *
     * @param originalData
     *            the original data string
     * @param innerData
     *            the inner data, including inner data value and inner data name
     * @return the data string after append the inner data
     *
     */
    private static String appendData(String originalData, String innerData) {
        StringBuffer sb = new StringBuffer(originalData);

        sb.insert(sb.lastIndexOf("</"), innerData);
        sb.insert(sb.lastIndexOf("</"), "\n");
        return sb.toString();
    }

    /**
     * <p>
     * Builds the standard information. The standard information is made
     * available, corresponding to the ValidationInfo stuff.
     * </p>
     *
     * @param validationInfo
     *            the validationInfo stuff
     * @return the standard information
     */
    public static String buildStandInfo(ValidationInfo validationInfo) {
        String standardInfo = "<DATA>\n</DATA>";
        ExternalUser user = validationInfo.getUser();
        standardInfo = appendData(standardInfo, buildUserInfo(user));
        FullProjectData project = validationInfo.getProject();
        standardInfo = appendData(standardInfo, buildFullProjectInfo(project));
        return standardInfo;
    }

    /**
     * <p>
     * Builds the user information. The user information is made available,
     * corresponding to the user stuff.
     * </p>
     *
     * @param user
     *            the user stuff
     * @return the user information
     */
    private static String buildUserInfo(ExternalUser user) {
        StringBuffer sb = new StringBuffer();
        sb.append("<USER>\n<HANDLE>");
        sb.append(user.getHandle());
        sb.append("</HANDLE>\n<FIRST_NAME>");
        sb.append(user.getFirstName());
        sb.append("</FIRST_NAME>\n<LAST_NAME>");
        sb.append(user.getLastName());
        sb.append("</LAST_NAME>\n<EMAIL>");
        sb.append(user.getEmail());
        sb.append("</EMAIL>\n");

        String[] alternativeEmails = user.getAlternativeEmails();
        for (int i = 0; i < alternativeEmails.length; i++) {
            sb.append("<ALTERNATE_EMAIL>\n<EMAIL_VALUE>");
            sb.append(alternativeEmails[i]);
            sb.append("</EMAIL_VALUE>\n</ALTERNATE_EMAIL>\n");
        }

        sb.append(buildUserRatingInfo(user));
        sb.append("\n</USER>");


        return sb.toString();
    }

    /**
     * <p>
     * Builds the user rating information. The user rating information is made available,
     * corresponding to the user stuff.
     * </p>
     *
     * @param user
     *            the user stuff
     * @return the user information
     */
    private static String buildUserRatingInfo(ExternalUser user) {
        StringBuffer sb = new StringBuffer();
        sb.append("<DESIGN_RATING>");
        sb.append(user.getDesignRating());
        sb.append("</DESIGN_RATING>\n<DESIGN_RELIABILITY>");
        sb.append(user.getDesignReliability());
        sb.append("</DESIGN_RELIABILITY>\n<DESIGN_VOLATILITY>");
        sb.append(user.getDevVolatility());
        sb.append("</DESIGN_VOLATILITY>\n<DESIGN_NUM_RATINGS>");
        sb.append(user.getDesignNumRatings());
        sb.append("</DESIGN_NUM_RATINGS><DEV_RATING>");
        sb.append(user.getDevRating());
        sb.append("</DEV_RATING>\n<DEV_RELIABILITY>");
        sb.append(user.getDevReliability());
        sb.append("</DEV_RELIABILITY>\n<DEV_VOLATILITY>");
        sb.append(user.getDevVolatility());
        sb.append("</DEV_VOLATILITY>\n<DEV_NUM_RATINGS>");
        sb.append(user.getDesignNumRatings());
        sb.append("</DEV_NUM_RATINGS>\n<DEV_NUM_RATINGS>");
        sb.append(user.getDevVolatility());
        sb.append("</DEV_NUM_RATINGS>");
        return sb.toString();
    }



    /**
     * <p>
     * Builds the project information. The project information is made
     * available, corresponding to the project stuff.
     * </p>
     *
     * @param project
     *            the project stuff
     * @return the project information
     */
    private static String buildFullProjectInfo(FullProjectData project) {
        StringBuffer sb = new StringBuffer();
        sb.append("<FULL_PROJECT_DATA>\n");

        Resource[] resources = project.getResources();
        if (resources != null) {
            for (int i = 0; i < resources.length; i++) {
                sb.append("<RESOURCE>\n<ROLE>");
                sb.append(resources[i].getResourceRole().getName());
                sb.append("</ROLE>\n</RESOURCE>\n");
            }
        }

        Project projectHeader = project.getProjectHeader();
        sb.append("<PROJECT_HEADER>\n<NAME>");
        sb.append(projectHeader.getProperty("Project Name"));
        sb.append("</NAME>\n<CATEGORY>");
        sb.append(projectHeader.getProjectCategory().getName());
        sb.append("</CATEGORY>\n<STATUS>");
        sb.append(projectHeader.getProjectStatus().getName());
        sb.append("</STATUS>\n</PROJECT_HEADER>");

        TeamHeader[] teams = project.getTeams();
        if (teams != null) {
            for (int i = 0; i < teams.length; i++) {
                sb.append(buildTeamHeaderInfo(teams[i]));
            }
        }

        String[] technologies = project.getTechnologies();
        for (int i = 0; i < technologies.length; i++) {
            sb.append("<TECHNOLOGY>\n<NAME>");
            sb.append(technologies[i]);
            sb.append("</NAME>\n</TECHNOLOGY>\n");
        }
        sb.append("</FULL_PROJECT_DATA>");
        return sb.toString();
    }

    /**
     * <p>
     * Builds the team information. The team information is made available,
     * corresponding to the user stuff.
     * </p>
     *
     * @param team
     *            the team stuff
     * @return the user information
     */
    private static String buildTeamHeaderInfo(TeamHeader team) {
        StringBuffer sb = new StringBuffer();
        sb.append("<TEAM_HEADER>\n<NAME>");
        sb.append(team.getName());
        sb.append("</NAME>\n<FINALIZED>");
        sb.append(team.isFinalized());
        sb.append("</FINALIZED>\n<CAPTAIN_RESOURCE_ID>");
        sb.append(team.getCaptainResourceId());
        sb.append("</CAPTAIN_RESOURCE_ID>\n<CAPTAIN_PAYMENT_PERCENTAGE>");
        sb.append(team.getCaptainPaymentPercentage());
        sb.append("</CAPTAIN_PAYMENT_PERCENTAGE>\n<DESCRIPTION>");
        sb.append(team.getDescription());
        sb.append("</DESCRIPTION>\n</TEAM_HEADER>");
        return sb.toString();
    }

    /**
     * Gets a template from the default template source.
     *
     * <ul>
     * <li> Valid args: non-null/empty arg, present default source, existing
     * template, valid template format </li>
     * <li> Invalid args: null/empty arg, missing default source, inexistence
     * template, invalid template format </li>
     * </ul>
     *
     *
     * @param templateName
     *            the name of the template
     * @param data
     *            the template data (in XML format)
     * @param logger
     *            the logger used to log
     * @param methodName
     *            the method name which call this helper method
     *
     * @return the generated text document
     *
     * @exception ValidationProcessingException
     *                If an unexpected system error occurs
     */
    public static String fillMessage(String templateName, String data,
        Log logger, String methodName) {
        try {
            // Gets the DocumentGenerator instance.
            RegistrationValidationHelper.log(logger, Level.DEBUG,
                "Starts calling DocumentGenerator#getInstance()");
            DocumentGenerator documentGenerator = DocumentGenerator
                    .getInstance();
            RegistrationValidationHelper.log(logger, Level.DEBUG,
                "Finishes calling DocumentGenerator#getInstance()");

            // Gets the template according to the template name
            RegistrationValidationHelper.log(logger, Level.DEBUG,
                "Starts calling DocumentGenerator#getTemplate(String)");
            Template template = documentGenerator.getTemplate(templateName);
            RegistrationValidationHelper.log(logger, Level.DEBUG,
                "Finishes calling DocumentGenerator#getTemplate(String)");

            // Generates the text document by applying template to the given
            // template data.
            RegistrationValidationHelper.log(logger, Level.DEBUG,
                "Starts calling DocumentGenerator#applyTemplate(Template, String)");
            String message = documentGenerator.applyTemplate(template, data);
            RegistrationValidationHelper.log(logger, Level.DEBUG,
                "Finishes calling DocumentGenerator#applyTemplate(Template, String)");

            return message;

        } catch (TemplateFormatException e) {
            RegistrationValidationHelper.log(logger, Level.ERROR, "Error[" + e.getClass().getName()
                + "] occurs in " + methodName + ", cause:" + e.getMessage());
            throw new ValidationProcessingException(
                    "an error occurs while filling the message", e);
        } catch (IllegalArgumentException e) {
            RegistrationValidationHelper.log(logger, Level.ERROR, "Error[" + e.getClass().getName()
                + "] occurs in " + methodName + ", cause:" + e.getMessage());
            throw new ValidationProcessingException(
                    "an error occurs while filling the message", e);
        } catch (ConfigManagerException e) {
            RegistrationValidationHelper.log(logger, Level.ERROR, "Error[" + e.getClass().getName()
                + "] occurs in " + methodName + ", cause:" + e.getMessage());
            throw new ValidationProcessingException(
                    "an error occurs while filling the message", e);
        } catch (InvalidConfigException e) {
            RegistrationValidationHelper.log(logger, Level.ERROR, "Error[" + e.getClass().getName()
                + "] occurs in " + methodName + ", cause:" + e.getMessage());
            throw new ValidationProcessingException(
                    "an error occurs while filling the message", e);
        } catch (TemplateSourceException e) {
            RegistrationValidationHelper.log(logger, Level.ERROR, "Error[" + e.getClass().getName()
                + "] occurs in " + methodName + ", cause:" + e.getMessage());
            throw new ValidationProcessingException(
                    "an error occurs while filling the message", e);
        } catch (TemplateDataFormatException e) {
            RegistrationValidationHelper.log(logger, Level.ERROR, "Error[" + e.getClass().getName()
                + "] occurs in " + methodName + ", cause:" + e.getMessage());
            throw new ValidationProcessingException(
                    "an error occurs while filling the message", e);
        }
    }

    /**
     *
     * Log message about enter one method.
     *
     * @param logger
     *            the logger to log this event
     * @param logLevel
     *            the Level of this event
     * @param logInfo
     *            the information to log
     *
     */
    public static void log(Log logger, Level logLevel, String logInfo) {
        if (logger != null) {
            logger.log(logLevel, logInfo);
        }
    }
}
