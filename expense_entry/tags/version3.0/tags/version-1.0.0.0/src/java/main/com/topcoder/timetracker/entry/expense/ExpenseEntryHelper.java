/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import com.topcoder.util.generator.guid.UUIDType;
import com.topcoder.util.generator.guid.UUIDUtility;

import java.lang.reflect.InvocationTargetException;

/**
 * <p>
 * Defines utilities used in this component.
 * </p>
 *
 * @author visualage
 * @version 1.0
 */
final class ExpenseEntryHelper {
    /** Represents the base of a hex number. */
    private static final int HEX_BASE = 16;

    /**
     * <p>
     * Creates a new instance of <code>ExpenseEntryHelper</code> class. This private constructor prevents the creation
     * of a new instance.
     * </p>
     */
    private ExpenseEntryHelper() {
    }

    /**
     * <p>
     * Validates the value of a variable with type <code>Object</code>. The value cannot be <code>null</code>.
     * </p>
     *
     * @param value value of the variable.
     * @param name name of the variable.
     * @throws NullPointerException if <code>value</code> is <code>null</code>.
     */
    public static void validateNotNull(Object value, String name) {
        if (value == null) {
            throw new NullPointerException(name + " cannot be null.");
        }
    }

    /**
     * <p>
     * Validates the value of a string variable. The value cannot be empty string, but may be <code>null</code>.
     * </p>
     *
     * @param value value of the variable.
     * @param name name of the variable.
     * @throws IllegalArgumentException if <code>value</code> is empty string.
     */
    public static void validateNotEmpty(String value, String name) {
        if (value != null) {
            if (value.trim().length() == 0) {
                throw new IllegalArgumentException(name + " cannot be empty string.");
            }
        }
    }

    /**
     * <p>
     * Validates the value of a string variable. The value cannot be <code>null</code> and empty string.
     * </p>
     *
     * @param value value of the variable.
     * @param name name of the variable.
     * @throws NullPointerException if <code>value</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>value</code> is empty string.
     */
    public static void validateNotNullOrEmpty(String value, String name) {
        validateNotNull(value, name);
        validateNotEmpty(value, name);
    }

    /**
     * <p>
     * Creates a new instance of the given class, which is an expense persistence. The created instance is expected to
     * have the given type. If the given <code>connectionProducerName</code> is <code>null</code>, constructor with
     * one string argument is used. Otherwise, the constructor with two string arguments is used.
     * </p>
     *
     * @param className the qualified class name to create a new instance.
     * @param connectionProducerName the connection producder name in DB connection factory to create connections.
     * @param namespace the namespace to create DB connection factory.
     * @param type the type of the instance to be expected.
     * @return a new instance of the given class.
     * @throws ConfigurationException if the instance of the given class name cannot be created; or the created instance
     *         is not the given type; or the namespace to create DB connection factory is <code>null</code> or empty
     *         string.
     */
    public static Object createInstance(String className, String connectionProducerName, String namespace, Class type)
        throws ConfigurationException {
        if (namespace == null) {
            throw new ConfigurationException("Namespace to create DB connection factory is missing.");
        }

        if (namespace.trim().length() == 0) {
            throw new ConfigurationException("Namespace to create DB connection factory is null.");
        }

        Object obj;

        try {
            Class cls = Class.forName(className);

            if (connectionProducerName == null) {
                obj = cls.getConstructor(new Class[] {String.class}).newInstance(new Object[] {namespace});
            } else {
                obj = cls.getConstructor(new Class[] {String.class, String.class}).newInstance(
                    new Object[] {connectionProducerName, namespace});
            }
        } catch (NullPointerException e) {
            throw new ConfigurationException("The class is missing.", e);
        } catch (ClassNotFoundException e) {
            throw new ConfigurationException("The class cannot be found.", e);
        } catch (LinkageError e) {
            throw new ConfigurationException("Linkage fails.", e);
        } catch (InstantiationException e) {
            throw new ConfigurationException("The class cannot be instantiated.", e);
        } catch (IllegalAccessException e) {
            throw new ConfigurationException("The class constructor cannot be accessed.", e);
        } catch (InvocationTargetException e) {
            throw new ConfigurationException("The class constructor throws exception.", e);
        } catch (NoSuchMethodException e) {
            throw new ConfigurationException("Proper constructor of the class cannot be found.", e);
        } catch (SecurityException e) {
            throw new ConfigurationException("Security error occurs.", e);
        }

        if (!type.isInstance(obj)) {
            throw new ConfigurationException("The class has a wrong type.");
        }

        return obj;
    }

    /**
     * <p>
     * Validates the value of a <code>CommonInfo</code> variable. The creation date and modification date of the
     * variable should be <code>null</code>.
     * </p>
     *
     * @param value the value of a <code>CommonInfo</code> variable.
     * @param name the name of a <code>CommonInfo</code> variable.
     * @throws IllegalArgumentException if creation date or modification date of the given variable is not
     *         <code>null</code>.
     */
    public static void validateNewInfo(CommonInfo value, String name) {
        if ((value.getCreationDate() != null) || (value.getModificationDate() != null)) {
            throw new IllegalArgumentException("The creation date or modification date of " + name + " is not null.");
        }
    }

    /**
     * <p>
     * Validates the data of a <code>CommonInfo</code> instance. All properties exception creation date and
     * modification date should not be <code>null</code>.
     * </p>
     *
     * @param value the <code>CommonInfo</code> instance to validate.
     * @throws InsufficientDataException if any property other than creation date and modification date is
     *         <code>null</code>.
     */
    public static void validateCommonInfoData(CommonInfo value) throws InsufficientDataException {
        if (value.getDescription() == null) {
            throw new InsufficientDataException("Description is missing.");
        }

        if (value.getCreationUser() == null) {
            throw new InsufficientDataException("Creation user is missing.");
        }

        if (value.getModificationUser() == null) {
            throw new InsufficientDataException("Modiciation user is missing.");
        }
    }

    /**
     * <p>
     * Validates the data of an <code>ExpenseEntry</code> instance. All properties exception creation date and
     * modification date should not be <code>null</code>. The ID of expense type and expense status should not be -1.
     * </p>
     *
     * @param value the <code>ExpenseEntry</code> instance to validate.
     * @throws InsufficientDataException if any property other than creation date and modification date is
     *         <code>null</code>.
     * @throws IllegalArgumentException if the ID of expense type or expense status is -1.
     */
    public static void validateExpenseEntryData(ExpenseEntry value) throws InsufficientDataException {
        validateCommonInfoData(value);

        if (value.getAmount() == null) {
            throw new InsufficientDataException("Expense amount is missing.");
        }

        if (value.getDate() == null) {
            throw new InsufficientDataException("Expense date is missing.");
        }

        if (value.getExpenseType() == null) {
            throw new InsufficientDataException("Expense type is missing.");
        }

        if (value.getStatus() == null) {
            throw new InsufficientDataException("Expense status is missing.");
        }

        if (value.getExpenseType().getId() == -1) {
            throw new IllegalArgumentException("The ID of expense type cannot be -1.");
        }

        if (value.getStatus().getId() == -1) {
            throw new IllegalArgumentException("The ID of expense status cannot be -1.");
        }
    }

    /**
     * <p>
     * Validates the data of an <code>ExpenseEntryStatus</code> instance. All properties exception creation date and
     * modification date should not be <code>null</code>.
     * </p>
     *
     * @param value the <code>ExpenseEntryStatus</code> instance to validate.
     * @throws InsufficientDataException if any property other than creation date and modification date is
     *         <code>null</code>.
     */
    public static void validateExpenseEntryStatusData(ExpenseEntryStatus value) throws InsufficientDataException {
        // This method is created to leave space to add more validation for future properties.
        validateCommonInfoData(value);
    }

    /**
     * <p>
     * Validates the data of an <code>ExpenseEntryType</code> instance. All properties exception creation date and
     * modification date should not be <code>null</code>.
     * </p>
     *
     * @param value the <code>ExpenseEntryType</code> instance to validate.
     * @throws InsufficientDataException if any property other than creation date and modification date is
     *         <code>null</code>.
     */
    public static void validateExpenseEntryTypeData(ExpenseEntryType value) throws InsufficientDataException {
        // This method is created to leave space to add more validation for future properties.
        validateCommonInfoData(value);
    }

    /**
     * <p>
     * Generates a random ID for expense entry, expense entry status, or expense entry type. The generated ID cannot be
     * -1.
     * </p>
     *
     * @return a random ID, which is not -1.
     */
    public static int generateId() {
        int id;

        do {
            // Integer is not enough, since the generated ID is an unsigned integer.
            id = (int) Long.parseLong(UUIDUtility.getNextUUID(UUIDType.TYPEINT32).toString(), HEX_BASE);
        } while (id == -1);

        return id;
    }

    /**
     * <p>
     * Validates the ID. It cannot be -1.
     * </p>
     *
     * @param id the unique ID to be validated.
     * @throws IllegalArgumentException if <code>id</code> is -1.
     */
    public static void validateId(int id) {
        if (id == -1) {
            throw new IllegalArgumentException("-1 cannot be a valid ID.");
        }
    }
}



