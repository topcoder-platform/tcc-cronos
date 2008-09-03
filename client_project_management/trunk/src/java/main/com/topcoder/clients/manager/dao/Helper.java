/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.dao;

import com.topcoder.clients.manager.ManagerConfigurationException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;

import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;

import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigurationObjectSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * This class is used by this component only. Provide common utility methods.
 * </p>
 * <p>
 * Thread safety: the class is thread-safe.
 * </p>
 *
 * @author Chenhong
 * @version 1.0
 */
final class Helper {
    /**
     * Hold a SimpleDateFormat instance to format the date time.
     */
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * <p>
     * Private constructor.
     * </p>
     */
    private Helper() {
    }

    /**
     * <p>
     * Checks whether the parameter is null.
     * </p>
     *
     * @param param
     *            the parameter object to check
     * @param paramName
     *            the name of the parameter
     *
     * @throws IllegalArgumentException
     *             if the parameter is null
     */
    static void checkNull(final Object param, final String paramName) {
        if (param == null) {
            throw new IllegalArgumentException("The parameter '" + paramName + "' should not be null.");
        }
    }

    /**
     * Check if a parameter String is empty.
     *
     * @param parameter
     *            the parameter to be validated, can be null
     * @param paramName
     *            the name of the parameter
     * @throws IllegalArgumentException
     *             if the parameter is an empty String
     */
    static void checkEmpty(String parameter, String paramName) {
        if (parameter != null && parameter.trim().length() == 0) {
            throw new IllegalArgumentException("The parameter with " + paramName + " should not be empty string.");
        }
    }

    /**
     * Validate a String parameter.
     *
     * @param param
     *            the String param instance to be validated
     * @param paramName
     *            the name of the parameter
     */
    static void checkString(String param, String paramName) {
        checkNull(param, paramName);

        if (param.trim().length() == 0) {
            throw new IllegalArgumentException("The parameter with " + paramName + " should not be empty string.");
        }
    }

    /**
     * Validate the given id.
     *
     * @param id
     *            the id to be validated
     * @throws IllegalArgumentException
     *             if the given id is negative
     */
    static void checkId(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("The parameter id should not be negative.");
        }
    }

    /**
     * Validate the client.
     *
     * <p>
     * A client is not valid if it is null, its startDate &lt; endDate , its name is null or empty string,
     * or its isDeleted value is true.
     * </p>
     *
     * @param client
     *            the client to be validated
     * @throws IllegalArgumentException
     */
    static void validateClient(Client client) {
        checkNull(client, "client");

        if (client.getStartDate() == null) {
            throw new IllegalArgumentException("The startDate of client should be set.");
        }

        if (client.getEndDate() == null) {
            throw new IllegalArgumentException("The endDate of client should be set.");
        }

        // the start date must before end date
        if (!client.getStartDate().before(client.getEndDate())) {
            throw new IllegalArgumentException("The startDate of client must < endDate.");
        }

        if (isNullOrEmptyString(client.getName())) {
            throw new IllegalArgumentException("The Client should have a non-null and non-empty name.");
        }

        if (client.isDeleted()) {
            throw new IllegalArgumentException("The client should not be deleted.");
        }
    }

    /**
     * Validate the ClientStatus against the rule.
     * <p>
     * A ClientStatus is valid if its name is non-null, non-empty, description is non-null, non-empty,
     * and isDeleted is false.
     * </p>
     *
     * @param status
     *            the ClientStatus to be validated
     * @throws IllegalArgumentException
     *             if the ClientStatus is not valid (null, its name null or empty, description or empty, isDeleted is
     *             true)
     */
    static void validateClientStatus(ClientStatus status) {
        checkNull(status, "status");

        if (isNullOrEmptyString(status.getName())) {
            throw new IllegalArgumentException("The ClientStatus should have a non-null and non-empty name.");
        }

        if (isNullOrEmptyString(status.getDescription())) {
            throw new IllegalArgumentException("The ClientStatus should have a non-null and non-empty description.");
        }

        if (status.isDeleted()) {
            throw new IllegalArgumentException("The ClientStatus should not be deleted.");
        }
    }

    /**
     * Validate a Project instance.
     *
     * <p>
     * A valid Project must have salesTax greater than zero, non-null string description, non-null and non-empty name,
     * and isDeleted is false.
     * </p>
     *
     * @param project
     *            the project to be validated, a project is valid if salesTax is greater than zero, description is
     *            non-null string, name is Non-null and non-empty string , and isDeleted is false
     *
     * @throws IllegalArgumentException
     *             if the project is valid (null or salesTax is greater than zero, its description is Non-null string,
     *             its name is Non-null and non-empty string , its isDeleted is false)
     */
    static void validateProject(Project project) {
        checkNull(project, "project");

        if (project.getSalesTax() <= 0) {
            throw new IllegalArgumentException("The project's salesTax should be greater than zero.");
        }

        if (project.getDescription() == null) {
            throw new IllegalArgumentException("The project's description should not be null.");
        }

        if (isNullOrEmptyString(project.getName())) {
            throw new IllegalArgumentException("The project's name should not be null or empty string (trimmed).");
        }

        if (project.isDeleted()) {
            throw new IllegalArgumentException("The project isDeleted value should be false.");
        }
    }

    /**
     * Validate the ProjectStatus.
     *
     * <p>
     * A ProjectStatus is valid, if its name is non-null, non-empty String, description is non-null, non-empty, and
     * isDeleted is false.
     * </p>
     *
     * @param status
     *            the ProjectStatus to be validated
     * @throws IllegalArgumentException
     *             if the ProjectStatus is not valid, null or name null, empty, description null or empty,
     *              or isDeleted is true
     */
    static void validateProjectStatus(ProjectStatus status) {
        checkNull(status, "status");

        if (isNullOrEmptyString(status.getName())) {
            throw new IllegalArgumentException("The ProjectStatus should have a non-null, non-empty name.");
        }

        if (isNullOrEmptyString(status.getDescription())) {
            throw new IllegalArgumentException("The ProjectStatus should have a non-null, non-empty description.");
        }

        if (status.isDeleted()) {
            throw new IllegalArgumentException("The ProjectStatus should not be deleted.");
        }
    }

    /**
     * Check if a String instance is null or empty.
     *
     * @param str
     *            the string instance to check
     * @return true if the given String is null or empty (trimmed), otherwise false
     */
    private static boolean isNullOrEmptyString(String str) {
        if ((str == null) || (str.trim().length() == 0)) {
            return true;
        }

        return false;
    }

    /**
     * Validate company.
     *
     * <p>
     * A valid company will not be null, have non-null and non-empty passcode and isDeleted is false.
     * </p>
     *
     * @param company
     *            the company to validate
     * @throws IllegalArgumentException
     *             if the given param company is not valid (null, passcode null or empty, or isDeleted is true
     */
    static void validateCompany(Company company) {
        checkNull(company, "company");

        if (isNullOrEmptyString(company.getPasscode())) {
            throw new IllegalArgumentException("The passcode of the company should not be null or empty (trimmed)");
        }

        if (company.isDeleted()) {
            throw new IllegalArgumentException("The company isDelete value should not be true.");
        }
    }

    /**
     * <p>
     * Formats the given date time.
     * </p>
     *
     * @param date
     *            The date time.
     *
     * @return The current date time in string format.
     */
    private static String formatDate(Date date) {
        return FORMAT.format(date);
    }

    /**
     * <p>
     * Logs methods entrance info.
     * </p>
     *
     * @param logger
     *            the logger to log method entrance, must not be null
     *
     * @param klass
     *            the name of the class to log
     * @param method
     *            the method to log
     * @param paramNames
     *            an array of name of each kind of parameter
     * @param paramValues
     *            an array contain coresponding value for parameter
     *
     */
    static void logEntranceInfo(Log logger, String klass, String method, String[] paramNames, String[] paramValues) {
        StringBuilder builder = new StringBuilder();
        builder.append("[Entering method <");
        builder.append(klass);
        builder.append(".");
        builder.append(method);
        builder.append(">; Parameters:{");

        // build the parameters information
        for (int i = 0; i < paramNames.length; i++) {
            if (i > 0) {
                builder.append(",");
            }
            builder.append(" ParamName<" + paramNames[i] + ">;");
            builder.append(" ParamValue: <" + paramValues[i] + "> ");
        }

        builder.append(" } ");

        builder.append(" at " + formatDate(new Date()) + " ]");

        // the logger must not be null
        logger.log(Level.DEBUG, builder.toString());
    }

    /**
     * <p>
     * Logs methods exit info.
     * </p>
     *
     *
     * @param logger
     *            the logger to log method entrance, must not be null
     * @param className
     *            the class log is performed
     * @param method
     *            the method log is performed
     * @param returnValue
     *            the return value which needs to be logged (String format)
     */
    static void logExitInfo(Log logger, String className, String method, String returnValue) {
        // [Exiting method <className.methodName>; Return value: <returnValue>]
        StringBuilder builder = new StringBuilder();

        builder.append("[Exiting method <" + className + "." + method + ">;");
        builder.append(" Return value:<");
        builder.append(returnValue);

        builder.append(">]  at " + formatDate(new Date()));

        // the logger will not be null
        logger.log(Level.DEBUG, builder.toString());
    }

    /**
     * <p>
     * Logs the Exception.
     * </p>
     *
     * @param e
     *            The Exception caught to be logged.
     * @param logger
     *            the logger to log exception, must not be null
     * @param className
     *            the class log is performed
     * @param method
     *            the method log is performed
     */
    static void logException(Log logger, String className, String method, Exception e) {
        StringBuilder builder = new StringBuilder();
        builder.append("[Error occurred in method <" + className + "." + method + ">,");
        builder.append(" cause by :");

        // the logger will not be null
        logger.log(Level.WARN, e, builder.toString());
    }

    /**
     * <p>
     * Gets the child ConfigurationObject from the given configurationObject.
     * </p>
     *
     * @param configuration
     *            the ConfigurationObject to get child ConfigurationObject from
     * @param name
     *            the name of the child ConfigurationObject to get
     *
     * @return the retrieved child ConfigurationObject
     *
     * @throws ManagerConfigurationException
     *             if any error occurs while retrieving the child from configurationObject
     */
    static ConfigurationObject getChildConfigurationObject(ConfigurationObject configuration, String name)
        throws ManagerConfigurationException {
        try {
            ConfigurationObject objectFactoryChild = configuration.getChild(name);

            if (objectFactoryChild == null) {
                throw new ManagerConfigurationException("There must be a child configuration with name=" + name);
            }

            return objectFactoryChild;
        } catch (ConfigurationAccessException e) {
            throw new ManagerConfigurationException("Failed to get the child configuration with name=" + name, e);
        }
    }

    /**
     * Create ObjectFactory instance from object factory ConfigurationObject instance.
     *
     * @param objectConfigurationObject
     *            the ConfigurationObject to create ObjectFactory
     * @return ObjectFacory created
     * @throws ManagerConfigurationException
     *             if any error occurs while creating ObjectFactory instance
     */
    static ObjectFactory createObjectFactory(ConfigurationObject objectConfigurationObject)
        throws ManagerConfigurationException {
        try {
            return new ObjectFactory(new ConfigurationObjectSpecificationFactory(objectConfigurationObject));
        } catch (IllegalReferenceException e) {
            throw new ManagerConfigurationException("IllegalReferenceException occurs"
                    + " while creating ConfigurationObjectSpecificationFactory, caused by: " + e.getMessage(), e);
        } catch (SpecificationConfigurationException e) {
            throw new ManagerConfigurationException("SpecificationConfigurationException occurs"
                    + " while creating ConfigurationObjectSpecificationFactory, caused by:" + e.getMessage(), e);
        }
    }

    /**
     * Get the String configuration value from ConfigurationObject with given key.
     *
     * @param configuration
     *            the ConfigurationObject instance to get value from
     * @param key
     *            the name of the property whose value to get
     * @param required
     *            indicates whether the key'value is required or optional
     * @return the retrieved value
     * @throws ManagerConfigurationException
     *             if any error occurs while retrieving the value from configurationObject ( no configuration for
     *             required key, invalid configuration object type...).
     */
    static String getConfigurationParameter(ConfigurationObject configuration, String key, boolean required)
        throws ManagerConfigurationException {
        try {
            String value = (String) configuration.getPropertyValue(key);

            if (required && (value == null)) {
                throw new ManagerConfigurationException("No configuration value for required key=" + key);
            }

            if (isEmpty(value)) {
                throw new ManagerConfigurationException("The configuration value or " + key
                        + " can not be empty string.");
            }

            return value;
        } catch (ConfigurationAccessException e) {
            throw new ManagerConfigurationException("Failed to get the configuration parameter for " + key, e);
        } catch (ClassCastException cce) {
            throw new ManagerConfigurationException("The configuration value for " + key + " must be a string.", cce);
        }
    }

    /**
     * <p>
     * Creates object by ObjectFactory with the value, which get from ConfigurationObject by key.
     * </p>
     *
     * @param objectFactory
     *            create object from
     * @param key
     *            the key to get value
     * @param type
     *            the correct type of the property
     *
     * @return the created object
     *
     * @throws ManagerConfigurationException
     *             if any error occurs while creating Object via ObjectFactory
     */
    static Object createObject(ObjectFactory objectFactory, String key, Class type)
        throws ManagerConfigurationException {
        Object object;

        try {
            object = objectFactory.createObject(key);
        } catch (InvalidClassSpecificationException e) {
            throw new ManagerConfigurationException("InvalidClassSpecificationException occurs. Caused by: "
                    + e.getMessage(), e);
        }

        // check the created object's type
        if (!type.isInstance(object)) {
            throw new ManagerConfigurationException("The created object should be " + type.getName());
        }

        return object;
    }

    /**
     * Check if the given String value is empty string. Return true if the given value is
     * not null but empty.
     * @param value the String value to be validated
     * @return true if given string is empty and not null
     */
    private static boolean isEmpty(String value) {
        if (value != null && value.trim().length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * Format the client for log.
     * <p>
     * For a given client, the format will be [id, name]
     * </p>
     *
     * @param client
     *            the Client entity to format
     * @return a String format of Client
     */
    static String formatClient(Client client) {
        if (client == null) {
            return "null";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append(" id=" + client.getId());
        builder.append(" name=" + client.getName());
        builder.append(" codeName=" + client.getCodeName());
        builder.append(" salesTax=" + client.getSalesTax());
        builder.append(" isDeleted=" + client.isDeleted());

        // format the company attribute
        builder.append(" company=" + formatCompany(client.getCompany()));

        // format the ClientStatus attribute
        builder.append(" clientStatus=" + formatClientStatus(client.getClientStatus()));

        builder.append("]");

        return builder.toString();
    }

    /**
     * Format a Company for log.
     * <p>
     * The main information of id, name and passcode will be format to String.
     * </p>
     *
     * @param company
     *            the company to format
     * @return String format of Company
     */
    static String formatCompany(Company company) {
        if (company == null) {
            return "null";
        }
        StringBuilder builder = new StringBuilder();

        builder.append("[");
        builder.append(" id=" + company.getId());
        builder.append(" name=" + company.getName());
        builder.append(" passcode=" + company.getPasscode());
        builder.append(" isDeleted=" + company.isDeleted());

        builder.append("]");
        return builder.toString();
    }

    /**
     * Format the ClientStatus for log.
     * <p>
     * </p>
     *
     * @param status
     *            the ClientStatus to format
     * @return a String format of ClientStauts
     */
    static String formatClientStatus(ClientStatus status) {
        if (status == null) {
            return "null";
        }
        StringBuilder builder = new StringBuilder();

        builder.append("[");
        builder.append(" id=" + status.getId());
        builder.append(" name=" + status.getName());
        builder.append(" description=" + status.getDescription());
        builder.append(" isDeleted=" + status.isDeleted());
        builder.append("]");

        return builder.toString();
    }

    /**
     * Format a list of Client entities for log.
     *
     * @param clients
     *            the list of client to be format
     * @return a String format contain all clients main information
     */
    static String formatClientList(List<Client> clients) {
        if (clients == null) {
            return "null";
        }

        StringBuilder builder = new StringBuilder();

        builder.append("[");

        for (Client client : clients) {
            builder.append(formatClient(client));
            builder.append(" ");
        }

        builder.append("]");

        return builder.toString();
    }

    /**
     * Format a list of ClientStatus entities for log.
     *
     * @param clientStatuses
     *            the list of ClientStatus to be format
     * @return a String format contain all ClientStatus's main information
     */
    static String formatClientStatusList(List<ClientStatus> clientStatuses) {
        if (clientStatuses == null) {
            return "null";
        }

        StringBuilder builder = new StringBuilder();

        builder.append("[");

        for (ClientStatus status : clientStatuses) {
            builder.append(formatClientStatus(status));
            builder.append(" ");
        }

        builder.append("]");

        return builder.toString();
    }

    /**
     * Format a list of Company for log.
     *
     * @param companies
     *            the list of company instances to be formated.
     * @return String format of a list of Company
     */
    static String formatCompanyList(List<Company> companies) {
        if (companies == null) {
            return "null";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("[");

        for (Company company : companies) {
            builder.append(formatCompany(company));
            builder.append(" ");
        }

        builder.append("]");

        return builder.toString();
    }

    /**
     * Format the Project entity for log.
     *
     * @param project
     *            the project entity to format
     * @return the String format of Project entity , containing main information
     */
    static String formatProject(Project project) {
        if (project == null) {
            return "null";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append(" id=" + project.getId());
        builder.append(" name=" + project.getName());
        builder.append(" description=" + project.getDescription());
        builder.append(" salesTax=" + project.getSalesTax());

        // log the client.
        builder.append(" client=" + formatClient(project.getClient()));

        // log the company
        builder.append(" company=" + formatCompany(project.getCompany()));

        // log the child projects
        builder.append(" childProjects=" + formatProjectList(project.getChildProjects()));

        builder.append("]");

        return builder.toString();
    }

    /**
     * Format a list of Project for log.
     *
     * @param projects
     *            the list of Project to be formated
     * @return String format of Project
     */
    static String formatProjectList(List<Project> projects) {
        if (projects == null) {
            return "null";
        }

        StringBuilder builder = new StringBuilder();

        builder.append("[");

        for (Project p : projects) {
            builder.append(formatProject(p));
            builder.append(" ");
        }

        builder.append("]");

        return builder.toString();
    }

    /**
     * Format the ProjectStatus for log.
     *
     * @param status
     *            the ProjectStatus to format
     * @return a String format of ProjectStatus
     */
    static String formatProjectStatus(ProjectStatus status) {
        if (status == null) {
            return "null";
        }
        StringBuilder builder = new StringBuilder();

        builder.append("[");
        builder.append(" id=" + status.getId());
        builder.append(" name=" + status.getName());
        builder.append(" description=" + status.getDescription());
        builder.append(" isDeleted=" + status.isDeleted());
        builder.append("]");

        return builder.toString();
    }

    /**
     * Format a list of ProjectStatus entities for log.
     *
     * @param clientStatuses
     *            the list of ProjectStatus to be format
     * @return a String format contain all ProjectStatus's main information
     */
    static String formatProjectStatusList(List<ProjectStatus> statuses) {
        if (statuses == null) {
            return "null";
        }

        StringBuilder builder = new StringBuilder();

        builder.append("[");

        for (ProjectStatus status : statuses) {
            builder.append(formatProjectStatus(status));
            builder.append(" ");
        }

        builder.append("]");

        return builder.toString();
    }
}
