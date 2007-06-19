/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;

import com.topcoder.timetracker.client.Client;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.user.User;
import com.topcoder.timetracker.user.Status;

import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseStatus;
import com.topcoder.timetracker.entry.expense.ExpenseType;

import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.TimeStatus;
import com.topcoder.timetracker.entry.time.TaskType;

import com.topcoder.timetracker.project.ProjectWorker;

/**
 * <p>
 * This class is a helper class which is used to to simplify the argument checking, string parsing,
 * getting property value from Config Manager component, and creating <code>ObjectFactory</code>.
 * </p>
 *
 * @author fabrizyo, rinoavd
 * @version 3.1
 */
public final class Helper {

    /**
     * <p>
     * Private constructor to prevent instantiation of this class.
     * </p>
     */
    private Helper() {
        // empty
    }

    /**
     * <p>
     * Checks the given <code>obj</code> to ensure that it is not null.
     * </p>
     *
     * @param obj The object to check.
     * @param paramName The parameter name of the argument.
     *
     * @throws IllegalArgumentException if <code>obj</code> is null.
     */
    public static void checkNotNull(Object obj, String paramName) {
        if (obj == null) {
            throw new IllegalArgumentException("Parameter argument '" + paramName + "' can not be null!");
        }
    }

    /**
     * <p>
     * Checks the given <code>String</code> to ensure that it is not null, and not empty.
     * </p>
     *
     * @param str The String to check.
     * @param paramName The parameter name of the argument.
     *
     * @throws IllegalArgumentException if <code>str</code> is null or empty string after being
     *         trimmed.
     */
    public static void checkString(String str, String paramName) {
        // check null
        checkNotNull(str, paramName);

        // check empty
        if (str.trim().length() == 0) {
            throw new IllegalArgumentException("Parameter argument '"
                    + paramName
                    + "' can not be empty string (trimmed)!");
        }
    }

    /**
     * <p>
     * Checks the given array of Objects to ensure that it is not null and does not contain any null
     * element.
     * </p>
     *
     * @param objArr The array of Objects to check.
     * @param paramName The parameter name of the argument.
     *
     * @throws IllegalArgumentException if <code>objArr</code> is null or contains null element.
     */
    public static void checkArrayNotNull(Object[] objArr, String paramName) {
        checkNotNull(objArr, paramName);
        for (int i = 0; i < objArr.length; i++) {
            checkNotNull(objArr[i], paramName + "[" + i + "]");
        }
    }

    /**
     * <p>
     * Checks the given array of String to ensure that it is not null and does not contain any null
     * or empty element.
     * </p>
     *
     * @param arrStr The array of String to check.
     * @param paramName The parameter name of the argument.
     *
     * @throws IllegalArgumentException if <code>objArr</code> is null or contains null element.
     */
    public static void checkArrayString(String[] arrStr, String paramName) {
        checkNotNull(arrStr, paramName);
        for (int i = 0; i < arrStr.length; i++) {
            checkString(arrStr[i], paramName + "[" + i + "]");
        }
    }

    /**
     * <p>
     * Checks the given array of Object to ensure that it is not null and does not contain any null
     * or empty element. It cannot be empty as well.
     * </p>
     *
     * @param arrObj The array of String to check.
     * @param paramName The parameter name of the argument.
     *
     * @throws IllegalArgumentException if <code>arrObj</code> is null or contains null element,
     *         or if it is empty.
     */
    public static void checkArrayNotEmptyNotNull(Object[] arrObj, String paramName) {
        checkArrayNotNull(arrObj, paramName);
        if (arrObj.length == 0) {
            throw new IllegalArgumentException("The array '" + paramName + "' cannot be empty.");
        }
    }

    /**
     * <p>
     * Checks the given array of long numbers to ensure that it is not null and not empty.
     * </p>
     *
     * @param arr The array of long numbers to check.
     * @param paramName The parameter name of the argument.
     *
     * @throws IllegalArgumentException if <code>objArr</code> is null or contains any
     *         not-positive element.
     */
    public static void checkArrayNotEmpty(long[] arr, String paramName) {
        checkNotNull(arr, paramName);
        if (arr.length == 0) {
            throw new IllegalArgumentException("The array '" + paramName + "' cannot be empty.");
        }
    }

    /**
     * <p>
     * Returns the value of the property in the given namespace.
     * </p>
     *
     * <p>
     * If the given property is required and it is missing or with an empty(trimmed) value, then it
     * will cause an <code>ReportConfigException</code>. A non-required property with empty value
     * will also cause <code>ReportConfigException</code>.
     * </p>
     *
     * @param namespace the namespace to get the property value from.
     * @param propertyName the name of property
     * @param isRequired indicates if the property is required.
     *
     * @return the value of the property. It can be null (if it is not required and missing). If it
     *         is not null, it will never be empty.
     *
     * @throws IllegalArgumentException if <code>namespace</code> or <code>propertyName</code>
     *         is null or empty(trimmed).
     * @throws ReportConfigException if failed to load the config values.
     */
    public static String getPropertyValue(String namespace, String propertyName, boolean isRequired)
        throws ReportConfigException {
        checkString(namespace, "namespace");
        checkString(propertyName, "propertyName");

        try {
            String property = ConfigManager.getInstance().getString(namespace, propertyName);

            // Property is missed
            if (isRequired && property == null) {
                throw new ReportConfigException("Missed property '" + propertyName + "'.");
            }

            // Empty property value is not allowed
            if (property != null) {
                property = property.trim();
                if (property.length() == 0) {
                    throw new ReportConfigException("The value for property '" + propertyName + "' is empty.");
                }
            }

            return property;
        } catch (UnknownNamespaceException ex) {
            throw new ReportConfigException("The namespace '" + namespace + "' is unknown.", ex);
        }
    }

    /**
     * <p>
     * Instantiates an <code>ObjectFactory</code>.
     * </p>
     *
     * @param namespace The namespace which contains the <em>objectFactoryNS</em> property.
     * @param objectFactoryNS The name of the property which gives the namespace of
     *        <code>ConfigManagerSpecificationFactory</code>.
     *
     * @return instance of <code>ObjectFactory</code> created.
     *
     * @throws IllegalArgumentException if <code>namespace</code> or <code>objectFactoryNS</code>
     *         is null or empty(trimmed).
     * @throws ReportConfigException If failed to load the config values or error occurred while
     *         creating <code>ObjectFactory</code>.
     */
    public static ObjectFactory getObjectFactory(String namespace, String objectFactoryNS)
        throws ReportConfigException {

        String specNamespace = null;
        try {
            specNamespace = getPropertyValue(namespace, objectFactoryNS, true);
            ConfigManagerSpecificationFactory cmsf = new ConfigManagerSpecificationFactory(specNamespace);
            return new ObjectFactory(cmsf);
        } catch (SpecificationConfigurationException ex) {
            throw new ReportConfigException("The namespace '"
                    + specNamespace
                    + "' of ObjectFactory contains invalid configuration.", ex);
        } catch (IllegalReferenceException ex) {
            throw new ReportConfigException("The namespace '"
                    + specNamespace
                    + "' of ObjectFactory contains invalid configuration.", ex);
        }
    }

    /**
     * <p>
     * Constructs a <code>Client</code> from the current row of a <code>CustomResultSet</code>.
     * It is assumed that the result set is valid, and its current row is valid as well.
     * </p>
     *
     * @param resultSet an object of <code>CustomResultSet</code>. This is assumed to be valid.
     *
     * @return instance of <code>Client</code> which holds all provided information by the current
     *         row of the result set.
     *
     * @throws ReportDataAccessException if some error occurs while accessing the data of the result
     *         set.
     */
    public static Client createClient(CustomResultSet resultSet) throws ReportDataAccessException {
        Client client = new Client();
        try {
            client.setId(resultSet.getLong("client_client_id"));
            client.setName(resultSet.getString("client_name"));
            client.setCompanyId(resultSet.getLong("client_company_id"));
            client.setCreationDate(resultSet.getTimestamp("client_creation_date"));
            client.setCreationUser(resultSet.getString("client_creation_user"));
            client.setModificationDate(resultSet.getTimestamp("client_modification_date"));
            client.setModificationUser(resultSet.getString("client_modification_user"));
        } catch (InvalidCursorStateException e) {
            throw new ReportDataAccessException("Exception when creating a Client object.", e);
        }

        return client;
    }

    /**
     * <p>
     * Constructs a <code>Project</code> from the current row of a <code>CustomResultSet</code>.
     * It is assumed that the result set is valid, and its current row is valid as well.
     * </p>
     *
     * @param resultSet an object of <code>CustomResultSet</code>. This is assumed to be valid.
     *
     * @return instance of <code>Project</code> which holds all provided information by the
     *         current row of the result set.
     *
     * @throws ReportDataAccessException if some error occurs while accessing the data of the result
     *         set.
     */
    public static Project createProject(CustomResultSet resultSet) throws ReportDataAccessException {
        Project project = new Project();
        try {
            project.setId(resultSet.getLong("project_project_id"));
            project.setName(resultSet.getString("project_name"));
            project.setCompanyId(resultSet.getLong("project_company_id"));
            project.setDescription(resultSet.getString("project_description"));
            project.setStartDate(resultSet.getTimestamp("project_start_date"));
            project.setEndDate(resultSet.getTimestamp("project_end_date"));
            project.setCreationDate(resultSet.getTimestamp("project_creation_date"));
            project.setCreationUser(resultSet.getString("project_creation_user"));
            project.setModificationDate(resultSet.getTimestamp("project_modification_date"));
            project.setModificationUser(resultSet.getString("project_modification_user"));
        } catch (InvalidCursorStateException e) {
            throw new ReportDataAccessException("Exception when creating a Project object.", e);
        }

        return project;
    }

    /**
     * <p>
     * Constructs an <code>User</code> from the current row of a <code>CustomResultSet</code>.
     * It is assumed that the result set is valid, and its current row is valid as well.
     * </p>
     *
     * @param resultSet an object of <code>CustomResultSet</code>. This is assumed to be valid.
     *
     * @return instance of <code>User</code> which holds all provided information by the current
     *         row of the result set.
     *
     * @throws ReportDataAccessException if some error occurs while accessing the data of the result
     *         set.
     */
    public static User createUser(CustomResultSet resultSet) throws ReportDataAccessException {
        User user = new User();
        try {
            user.setId(resultSet.getLong("user_account_user_account_id"));
            user.setCompanyId(resultSet.getLong("user_account_company_id"));
            user.setUsername(resultSet.getString("user_account_user_name"));
            user.setPassword(resultSet.getString("user_account_password"));

            // create status object
            long accountStatusId = resultSet.getLong("user_account_account_status_id");
            switch ((int) accountStatusId) {
            case 1:
                user.setStatus(Status.ACTIVE);
                break;
            case 0:
                user.setStatus(Status.INACTIVE);
                break;
            case 2:
                user.setStatus(Status.LOCKED);
                break;
            default:
                break;
            }

            user.setCreationDate(resultSet.getTimestamp("user_account_creation_date"));
            user.setCreationUser(resultSet.getString("user_account_creation_user"));
            user.setModificationDate(resultSet.getTimestamp("user_account_modification_date"));
            user.setModificationUser(resultSet.getString("user_account_modification_user"));
        } catch (InvalidCursorStateException e) {
            throw new ReportDataAccessException("Exception when creating an User object.", e);
        }

        return user;
    }

    /**
     * <p>
     * Constructs a <code>FixedBillingStatus</code> object from the current row of a
     * <code>CustomResultSet</code>. It is assumed that the result set is valid, and its current
     * row is valid as well.
     * </p>
     *
     * @param resultSet an object of <code>CustomResultSet</code>. This is assumed to be valid.
     *
     * @return instance of <code>FixedBillingStatus</code> which holds all provided information by
     *         the current row of the result set.
     *
     * @throws ReportDataAccessException if some error occurs while accessing the data of the result
     *         set.
     */
    public static FixedBillingStatus createFixedBillingStatus(CustomResultSet resultSet)
        throws ReportDataAccessException {
        FixedBillingStatus status = new FixedBillingStatus();
        try {
            status.setId(resultSet.getLong("fix_bill_status_fix_bill_status_id"));
            status.setDescription(resultSet.getString("fix_bill_status_description"));
            status.setCreationDate(resultSet.getTimestamp("fix_bill_status_creation_date"));
            status.setCreationUser(resultSet.getString("fix_bill_status_creation_user"));
            status.setModificationDate(resultSet.getTimestamp("fix_bill_status_modification_date"));
            status.setModificationUser(resultSet.getString("fix_bill_status_modification_user"));
        } catch (InvalidCursorStateException e) {
            throw new ReportDataAccessException("Exception when creating a FixedBillingStatus object.", e);
        }

        return status;
    }

    /**
     * <p>
     * Constructs a <code>FixedBillingEntry</code> object from the current row of a
     * <code>CustomResultSet</code>. It is assumed that the result set is valid, and its current
     * row is valid as well. A valid FixedBillingStatus object is also given.
     * </p>
     *
     * @param resultSet an object of <code>CustomResultSet</code>. This is assumed to be valid.
     * @param status a<code>FixedBillingStatus</code> associated to this entry.
     *
     * @return instance of <code>FixedBillingEntry</code> which holds all provided information by
     *         the current row of the result set.
     *
     * @throws ReportDataAccessException if some error occurs while accessing the data of the result
     *         set.
     */
    public static FixedBillingEntry createFixedBillingEntry(CustomResultSet resultSet,
            FixedBillingStatus status) throws ReportDataAccessException {
        FixedBillingEntry entry = new FixedBillingEntry();
        try {

            entry.setId(resultSet.getLong("fix_bill_entry_fix_bill_entry_id"));
            entry.setCompanyId(resultSet.getLong("fix_bill_entry_company_id"));
			if (resultSet.getObject("fix_bill_entry_invoice_id") != null)	{
				entry.setInvoiceId(resultSet.getLong("fix_bill_entry_invoice_id"));
			}

            entry.setFixedBillingStatus(status);
            entry.setDescription(resultSet.getString("fix_bill_entry_description"));
            entry.setDate(resultSet.getDate("fix_bill_entry_entry_date"));
            entry.setAmount(resultSet.getDouble("fix_bill_entry_amount"));

            entry.setCreationDate(resultSet.getTimestamp("fix_bill_entry_creation_date"));
            entry.setCreationUser(resultSet.getString("fix_bill_entry_creation_user"));
            entry.setModificationDate(resultSet.getTimestamp("fix_bill_entry_modification_date"));
            entry.setModificationUser(resultSet.getString("fix_bill_entry_modification_user"));
        } catch (InvalidCursorStateException e) {
            throw new ReportDataAccessException("Exception when creating a FixedBillingEntry object.", e);
        }

        return entry;
    }

    /**
     * <p>
     * Constructs a <code>ExpenseStatus</code> object from the current row of a
     * <code>CustomResultSet</code>. It is assumed that the result set is valid, and its current
     * row is valid as well.
     * </p>
     *
     * @param resultSet an object of <code>CustomResultSet</code>. This is assumed to be valid.
     *
     * @return instance of <code>ExpenseStatus</code> which holds all provided information by the
     *         current row of the result set.
     *
     * @throws ReportDataAccessException if some error occurs while accessing the data of the result
     *         set.
     */
    public static ExpenseStatus createExpenseStatus(CustomResultSet resultSet)
        throws ReportDataAccessException {
        ExpenseStatus status = new ExpenseStatus();
        try {
            status.setId(resultSet.getLong("expense_status_expense_status_id"));
            status.setDescription(resultSet.getString("expense_status_description"));
            status.setCreationDate(resultSet.getTimestamp("expense_status_creation_date"));
            status.setCreationUser(resultSet.getString("expense_status_creation_user"));
            status.setModificationDate(resultSet.getTimestamp("expense_status_modification_date"));
            status.setModificationUser(resultSet.getString("expense_status_modification_user"));
        } catch (InvalidCursorStateException e) {
            throw new ReportDataAccessException("Exception when creating a ExpenseStatus object.", e);
        }

        return status;
    }

    /**
     * <p>
     * Constructs a <code>ExpenseType</code> object from the current row of a
     * <code>CustomResultSet</code>. It is assumed that the result set is valid, and its current
     * row is valid as well.
     * </p>
     *
     * @param resultSet an object of <code>CustomResultSet</code>. This is assumed to be valid.
     *
     * @return instance of <code>ExpenseType</code> which holds all provided information by the
     *         current row of the result set.
     *
     * @throws ReportDataAccessException if some error occurs while accessing the data of the result
     *         set.
     */
    public static ExpenseType createExpenseType(CustomResultSet resultSet) throws ReportDataAccessException {
        ExpenseType type = new ExpenseType();
        try {
            type.setId(resultSet.getLong("expense_type_expense_type_id"));
            type.setDescription(resultSet.getString("expense_type_description"));
            type.setActive(resultSet.getByte("expense_type_active") != 0);
            type.setCreationDate(resultSet.getTimestamp("expense_type_creation_date"));
            type.setCreationUser(resultSet.getString("expense_type_creation_user"));
            type.setModificationDate(resultSet.getTimestamp("expense_type_modification_date"));
            type.setModificationUser(resultSet.getString("expense_type_modification_user"));
        } catch (InvalidCursorStateException e) {
            throw new ReportDataAccessException("Exception when creating a ExpenseType object.", e);
        }

        return type;
    }

    /**
     * <p>
     * Constructs a <code>ExpenseEntry</code> object from the current row of a
     * <code>CustomResultSet</code>. It is assumed that the result set is valid, and its current
     * row is valid as well. A valid FixedBillingStatus object is also given.
     * </p>
     *
     * @param resultSet an object of <code>CustomResultSet</code>. This is assumed to be valid.
     * @param status a<code>ExpenseStatus</code> associated to this entry.
     * @param type a<code>ExpenseType</code> associated to this entry.
     *
     * @return instance of <code>ExpenseEntry</code> which holds all provided information by the
     *         current row of the result set.
     *
     * @throws ReportDataAccessException if some error occurs while accessing the data of the result
     *         set.
     */
    public static ExpenseEntry createExpenseEntry(CustomResultSet resultSet, ExpenseStatus status,
            ExpenseType type) throws ReportDataAccessException {
        ExpenseEntry entry = new ExpenseEntry();
        try {

            entry.setId(resultSet.getLong("expense_entry_expense_entry_id"));
            entry.setCompanyId(resultSet.getLong("expense_entry_company_id"));
            // fbEntry.setInvoiceId(resultSet.getLong("fix_bill_entry_invoice_id"));

            entry.setStatus(status);
            entry.setExpenseType(type);

            entry.setDescription(resultSet.getString("expense_entry_description"));
            entry.setDate(resultSet.getDate("expense_entry_entry_date"));
            entry.setAmount(resultSet.getBigDecimal("expense_entry_amount"));
            entry.setBillable(resultSet.getByte("expense_entry_billable") != 0);
            entry.setCreationDate(resultSet.getTimestamp("expense_entry_creation_date"));
            entry.setCreationUser(resultSet.getString("expense_entry_creation_user"));
            entry.setModificationDate(resultSet.getTimestamp("expense_entry_modification_date"));
            entry.setModificationUser(resultSet.getString("expense_entry_modification_user"));
        } catch (InvalidCursorStateException e) {
            throw new ReportDataAccessException("Exception when creating a ExpenseEntry object.", e);
        }

        return entry;
    }

    /**
     * <p>
     * Constructs a <code>TimeStatus</code> object from the current row of a
     * <code>CustomResultSet</code>. It is assumed that the result set is valid, and its current
     * row is valid as well.
     * </p>
     *
     * @param resultSet an object of <code>CustomResultSet</code>. This is assumed to be valid.
     *
     * @return instance of <code>TimeStatus</code> which holds all provided information by the
     *         current row of the result set.
     *
     * @throws ReportDataAccessException if some error occurs while accessing the data of the result
     *         set.
     */
    public static TimeStatus createTimeStatus(CustomResultSet resultSet) throws ReportDataAccessException {
        TimeStatus status = new TimeStatus();
        try {
            status.setId(resultSet.getLong("time_status_time_status_id"));
            status.setDescription(resultSet.getString("time_status_description"));
            status.setCreationDate(resultSet.getTimestamp("time_status_creation_date"));
            status.setCreationUser(resultSet.getString("time_status_creation_user"));
            status.setModificationDate(resultSet.getTimestamp("time_status_modification_date"));
            status.setModificationUser(resultSet.getString("time_status_modification_user"));
        } catch (InvalidCursorStateException e) {
            throw new ReportDataAccessException("Exception when creating a TimeStatus object.", e);
        }

        return status;
    }

    /**
     * <p>
     * Constructs a <code>TaskType</code> object from the current row of a
     * <code>CustomResultSet</code>. It is assumed that the result set is valid, and its current
     * row is valid as well.
     * </p>
     *
     * @param resultSet an object of <code>CustomResultSet</code>. This is assumed to be valid.
     *
     * @return instance of <code>TaskType</code> which holds all provided information by the
     *         current row of the result set.
     *
     * @throws ReportDataAccessException if some error occurs while accessing the data of the result
     *         set.
     */
    public static TaskType createTaskType(CustomResultSet resultSet) throws ReportDataAccessException {
        TaskType type = new TaskType();
        try {
            type.setId(resultSet.getLong("task_type_task_type_id"));
            type.setDescription(resultSet.getString("task_type_description"));
            type.setActive(resultSet.getByte("task_type_active") != 0);
            type.setCreationDate(resultSet.getTimestamp("task_type_creation_date"));
            type.setCreationUser(resultSet.getString("task_type_creation_user"));
            type.setModificationDate(resultSet.getTimestamp("task_type_modification_date"));
            type.setModificationUser(resultSet.getString("task_type_modification_user"));
        } catch (InvalidCursorStateException e) {
            throw new ReportDataAccessException("Exception when creating a TaskType object.", e);
        }

        return type;
    }

    /**
     * <p>
     * Constructs a <code>TimeEntry</code> object from the current row of a
     * <code>CustomResultSet</code>. It is assumed that the result set is valid, and its current
     * row is valid as well. A valid FixedBillingStatus object is also given.
     * </p>
     *
     * @param resultSet an object of <code>CustomResultSet</code>. This is assumed to be valid.
     * @param status a<code>TimeStatus</code> associated to this entry.
     * @param type a<code>TaskType</code> associated to this entry.
     *
     * @return instance of <code>TimeEntry</code> which holds all provided information by the
     *         current row of the result set.
     *
     * @throws ReportDataAccessException if some error occurs while accessing the data of the result
     *         set.
     */
    public static TimeEntry createTimeEntry(CustomResultSet resultSet, TimeStatus status, TaskType type)
        throws ReportDataAccessException {
        TimeEntry entry = new TimeEntry();
        try {

            entry.setId(resultSet.getLong("time_entry_time_entry_id"));
            entry.setCompanyId(resultSet.getLong("time_entry_company_id"));
            // fbEntry.setInvoiceId(resultSet.getLong("fix_bill_entry_invoice_id"));

            entry.setStatus(status);
            entry.setTaskType(type);

            // needed because query returns null sometimes for the time_entry_description
            String description = resultSet.getString("time_entry_description");
            if (description == null)    {
                description = "";
            }
            entry.setDescription(description);
            entry.setDate(resultSet.getDate("time_entry_entry_date"));
            entry.setHours(resultSet.getDouble("time_entry_hours"));
            entry.setBillable(resultSet.getByte("time_entry_billable") != 0);
            entry.setCreationDate(resultSet.getTimestamp("time_entry_creation_date"));
            entry.setCreationUser(resultSet.getString("time_entry_creation_user"));
            entry.setModificationDate(resultSet.getTimestamp("time_entry_modification_date"));
            entry.setModificationUser(resultSet.getString("time_entry_modification_user"));
        } catch (InvalidCursorStateException e) {
            throw new ReportDataAccessException("Exception when creating a TimeEntry object.", e);
        }

        return entry;
    }

    /**
     * <p>
     * Constructs a <code>ProjectWorker</code> object from the current row of a
     * <code>CustomResultSet</code>. It is assumed that the result set is valid, and its current
     * row is valid as well.
     * </p>
     *
     * @param resultSet an object of <code>CustomResultSet</code>. This is assumed to be valid.
     *
     * @return instance of <code>ProjectWorker</code> which holds all provided information by the
     *         current row of the result set.
     *
     * @throws ReportDataAccessException if some error occurs while accessing the data of the result
     *         set.
     */
    public static ProjectWorker createProjectWorker(CustomResultSet resultSet)
        throws ReportDataAccessException {
        ProjectWorker worker = new ProjectWorker();
        try {
            worker.setProjectId(resultSet.getLong("project_worker_project_id"));
            worker.setUserId(resultSet.getLong("project_worker_user_account_id"));
            worker.setStartDate(resultSet.getTimestamp("project_worker_start_date"));
            worker.setEndDate(resultSet.getTimestamp("project_worker_end_date"));
            worker.setPayRate(resultSet.getDouble("project_worker_pay_rate"));
            worker.setCreationDate(resultSet.getTimestamp("project_worker_creation_date"));
            worker.setCreationUser(resultSet.getString("project_worker_creation_user"));
            worker.setModificationDate(resultSet.getTimestamp("project_worker_modification_date"));
            worker.setModificationUser(resultSet.getString("project_worker_modification_user"));
        } catch (InvalidCursorStateException e) {
            throw new ReportDataAccessException("Exception when creating a ProjectWorker object.", e);
        }

        return worker;
    }
}
