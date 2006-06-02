/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense.search;

/**
 * <p>
 * This class represents a basic type of criteria for exact matching of a field with a given value. Both the field and
 * value are given by the user (the where clause expression will look like "field=value"). The field must be fully
 * qualified (table.field) because the search query joins more tables. The class defines 5 constants and 5 static
 * methods for the fields the requirements specifically mention. This is done in an effort to provide the simplest
 * possible API for the user.
 * </p>
 *
 * <p>
 * Changes in 2.0: the static fields changed according to field name.
 * </p>
 *
 * <p>
 * Thread safety: Immutable class so there are no thread safety issues.
 * </p>
 *
 * @author adic, TCSDEVELOPER
 * @version 2.0
 */
public class FieldMatchCriteria implements Criteria {
    /** Represents the constant for the expense type id field in the expense entry table. */
    public static final String EXPENSE_TYPE_FIELD = "expense_entry.expense_type_id";

    /** Represents the constant for the expense status id field in the expense entry table. */
    public static final String EXPENSE_STATUS_FIELD = "expense_entry.expense_status_id";

    /** Represents the constant for the creation user field in the expense entry table. */
    public static final String CREATION_USER_FIELD = "expense_entry.creation_user";

    /** Represents the constant for the modification user field in the expense entry table. */
    public static final String MODIFICATION_USER_FIELD = "expense_entry.modification_user";

    /** Represents the constant for the billable field in the expense entry table. */
    public static final String BILLABLE_FIELD = "expense_entry.billable";

    /**
     * Represents the constant for the active field in the expense type table.
     * @since 2.0
     */
    public static final String EXPENSE_TYPE_ACTIVE_FIELD = "expense_type.active";

    /**
     * Represents the constant for the company_id field in the expense entry table.
     * @since 2.0
     */
    public static final String EXPENSE_ENTRY_COMPANY_ID_FIELD = "expense_entry.company_id";

    /**
     * Represesnts the constant for the company_id field in the expense type table.
     * @since 2.0
     */
    public static final String EXPENSE_TYPE_COMPANY_ID_FIELD = "comp_exp_type.company_id";

    /**
     * Represents the constant for the creation_user field in the expense status table.
     * @since 2.0
     */
    public static final String EXPENSE_STATUS_CREATION_USER = "expense_status.creation_user";

    /**
     * Represents the constant for the modification_user field in the expense status table.
     * @since 2.0
     */
    public static final String EXPENSE_STATUS_MODIFICATION_USER = "expense_status.modification_user";

    /**
     * Represents the constant for the creation_user field in the expense type table.
     * @since 2.0
     */
    public static final String EXPENSE_TYPE_CREATION_USER = "expense_type.creation_user";

    /**
     * Represents the constant for the modification_user field in the expense type table.
     * @since 2.0
     */
    public static final String EXPENSE_TYPE_MODIFICATION_USER = "expense_type.modification_user";

    /**
     * Represents the field the match should be applied on. The field must be fully qualified (table.field) because the
     * search query joins more tables.
     */
    private final String field;

    /**
     * Represents the value that the field should be matched against (using =). Can be <code>null</code> (for saying a
     * field value should be <code>null</code>).
     */
    private final Object value;

    /** Represents the where clause of this criteria. */
    private final String whereClause;

    /** Represents the parameters of this criteria. */
    private final Object[] parameters;

    /**
     * <p>
     * Creates a new instance of <code>FieldMatchCriteria</code> class with given field and value. The field must be
     * fully qualified (table.field) because the search query joins more tables.
     * </p>
     *
     * @param field the field the match should be applied on.
     * @param value the value that the field should be matched against
     *
     * @throws IllegalArgumentException if <code>field</code> is <code>null</code>, empty or all spaces
     */
    public FieldMatchCriteria(String field, Object value) {
        // arguments validation
        if (field == null) {
            throw new IllegalArgumentException("field can not be null.");
        }

        if (field.trim().length() == 0) {
            throw new IllegalArgumentException("field can not be empty string.");
        }

        // assign the private fields
        this.field = field;
        this.value = value;
        this.whereClause = field + "=?";
        this.parameters = new Object[] {value};
    }

    /**
     * Gets the field the match should be applied on.
     *
     * @return the field the match should be applied on.
     */
    public String getField() {
        return field;
    }

    /**
     * Gets the value that the field should be matched against.
     *
     * @return the value that the field should be matched against.
     */
    public Object getValue() {
        return value;
    }

    /**
     * Gets the where clause expression that can be used in an SQL query to perform the filtering. The expression is
     * expected to contain ? characters where the criteria parameters should be inserted. This is needed because
     * <code>PreparedStatement</code> can be used to execute the query.
     *
     * @return the where clause expression that can be used in an SQL query to perform the filtering.
     */
    public String getWhereClause() {
        return whereClause;
    }

    /**
     * Gets the parameter values that should be used to fill the where clause expression returned by the
     * <code>getWhereClause</code> method. The parameter values should be inserted in place of the ? characters in the
     * where clause expression. The number of parameters should be equal to the number of ? characters in the where
     * clause expression.
     *
     * @return the parameters used in the expression.
     */
    public Object[] getParameters() {
        return parameters;
    }

    /**
     * Static shortcut method for creating an expense type id field match criteria.
     *
     * @param expenseTypeId the expense type id.
     *
     * @return the created expense type id field match criteria instance.
     */
    public static FieldMatchCriteria getExpenseTypeMatchCriteria(int expenseTypeId) {
        return new FieldMatchCriteria(EXPENSE_TYPE_FIELD, new Integer(expenseTypeId));
    }

    /**
     * Static shortcut method for creating an expense status id field match criteria.
     *
     * @param expenseStatusId the expense status id.
     *
     * @return the created expense status id field match criteria instance.
     */
    public static FieldMatchCriteria getExpenseStatusMatchCriteria(int expenseStatusId) {
        return new FieldMatchCriteria(EXPENSE_STATUS_FIELD, new Integer(expenseStatusId));
    }

    /**
     * Static shortcut method for creating an company id field match criteria.
     *
     * @param companyId the company id.
     *
     * @return the created expense entry's company  id field match criteria instance.
     */
    public static FieldMatchCriteria getExpenseEntryCompanyIdMatchCriteria(int companyId) {
        return new FieldMatchCriteria(EXPENSE_ENTRY_COMPANY_ID_FIELD, new Integer(companyId));
    }

    /**
     * Static shortcut method for creating an company id field match criteria.
     *
     * @param companyId the company id.
     *
     * @return  the created expense type's company  id field match criteria instance.
     */
    public static FieldMatchCriteria getExpenseTypeCompanyIdMatchCriteria(int companyId) {
        return new FieldMatchCriteria(EXPENSE_TYPE_COMPANY_ID_FIELD, new Integer(companyId));
    }

    /**
     * Static shortcut method for creating a creation user field match criteria.
     *
     * @param user the creation user.
     *
     * @return the created creation user field match criteria instance.
     *
     * @throws IllegalArgumentException if the argument is <code>null</code>, empty or all spaces
     */
    public static FieldMatchCriteria getCreationUserMatchCriteria(String user) {
        // arguments validation
        if (user == null) {
            throw new IllegalArgumentException("user can not be null.");
        }

        if (user.trim().length() == 0) {
            throw new IllegalArgumentException("user can not be empty string.");
        }

        return new FieldMatchCriteria(CREATION_USER_FIELD, user);
    }

    /**
     * Static shortcut method for creating a modification user field match criteria.
     *
     * @param user the modification user.
     *
     * @return the created modification user field match criteria instance.
     *
     * @throws IllegalArgumentException if the argument is <code>null</code>, empty or all spaces
     */
    public static FieldMatchCriteria getModificationUserMatchCriteria(String user) {
        // arguments validation
        if (user == null) {
            throw new IllegalArgumentException("user can not be null.");
        }

        if (user.trim().length() == 0) {
            throw new IllegalArgumentException("user can not be empty string.");
        }

        return new FieldMatchCriteria(MODIFICATION_USER_FIELD, user);
    }

    /**
     * Static shortcut method for creating a modification user field match criteria for expense status.
     *
     * @param user the modification user.
     *
     * @return the created modification user field match criteria instance.
     *
     * @throws IllegalArgumentException if the argument is <code>null</code>, empty or all spaces
     * @since 2.0
     */
    public static FieldMatchCriteria getExpenseStatusModificationUserMatchCriteria(String user) {
        // arguments validation
        if (user == null) {
            throw new IllegalArgumentException("user can not be null.");
        }

        if (user.trim().length() == 0) {
            throw new IllegalArgumentException("user can not be empty string.");
        }

        return new FieldMatchCriteria(EXPENSE_STATUS_MODIFICATION_USER, user);
    }

    /**
     * Static shortcut method for creating a creation user field match criteria for expense status.
     *
     * @param user the creation user.
     *
     * @return the created modification user field match criteria instance.
     *
     * @throws IllegalArgumentException if the argument is <code>null</code>, empty or all spaces
     */
    public static FieldMatchCriteria getExpenseStatusCreationUserMatchCriteria(String user) {
        // arguments validation
        if (user == null) {
            throw new IllegalArgumentException("user can not be null.");
        }

        if (user.trim().length() == 0) {
            throw new IllegalArgumentException("user can not be empty string.");
        }

        return new FieldMatchCriteria(EXPENSE_STATUS_CREATION_USER, user);
    }

    /**
     * Static shortcut method for creating a modification user field match criteria for expense type.
     *
     * @param user the modification user.
     *
     * @return the created modification user field match criteria instance.
     *
     * @throws IllegalArgumentException if the argument is <code>null</code>, empty or all spaces
     * @since 2.0
     */
    public static FieldMatchCriteria getExpenseTypeModificationUserMatchCriteria(String user) {
        // arguments validation
        if (user == null) {
            throw new IllegalArgumentException("user can not be null.");
        }

        if (user.trim().length() == 0) {
            throw new IllegalArgumentException("user can not be empty string.");
        }

        return new FieldMatchCriteria(EXPENSE_TYPE_MODIFICATION_USER, user);
    }

    /**
     * Static shortcut method for creating a creation user field match criteria for expense type.
     *
     * @param user the creation user.
     *
     * @return the created modification user field match criteria instance.
     *
     * @throws IllegalArgumentException if the argument is <code>null</code>, empty or all spaces
     */
    public static FieldMatchCriteria getExpenseTypeCreationUserMatchCriteria(String user) {
        // arguments validation
        if (user == null) {
            throw new IllegalArgumentException("user can not be null.");
        }

        if (user.trim().length() == 0) {
            throw new IllegalArgumentException("user can not be empty string.");
        }

        return new FieldMatchCriteria(EXPENSE_TYPE_CREATION_USER, user);
    }

    /**
     * Static shortcut method for creating a billable field match criteria.
     *
     * @param billable the billable value.
     *
     * @return the created billable field match criteria instance.
     */
    public static FieldMatchCriteria getBillableMatchCriteria(boolean billable) {
        return new FieldMatchCriteria(BILLABLE_FIELD, new Short((short) (billable ? 1 : 0)));
    }

    /**
     * Static shortcut method for creating a active field match criteria.
     *
     * @param active the active value.
     *
     * @return the created active field match criteria instance.
     */
    public static FieldMatchCriteria getExpenseTypeActiveMatchCriteria(boolean active) {
        return new FieldMatchCriteria(EXPENSE_TYPE_ACTIVE_FIELD, new Short((short) (active ? 1 : 0)));
    }
}
