/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.ejb;

import hr.leads.services.model.jpa.IdentifiableEntity;
import hr.leads.services.model.jpa.NamedEntity;

import java.lang.reflect.Field;
import java.util.Comparator;

/**
 * <p>
 * This comparator is used by ReportServiceBean to sort report records or
 * employee profiles.
 * </p>
 * <p>
 * It implements Comparator&lt;T&gt; interface.
 * </p>
 * <p>
 * It uses given objectType and sortColumns to determine the order of the two
 * objects.
 * </p>
 * <p>
 * <b> Thread Safety: </b> This class is immutable and thread safe.
 * </p>
 *
 * @author semi_sleep, TCSDEVELOPER
 * @version 1.0
 *
 * @param <T> the type of the element to compare.
 */
public class ReportSortingComparator<T> implements Comparator<T> {

    /**
     * <p>
     * Represents the class type for the object passed to compare() method.
     * </p>
     * <p>
     * It cannot be null.
     * </p>
     * <p>
     * It should be initialized in constructor,
     * should not be modified after initialization.
     * </p>
     * <p>
     * It's used in the compare() method.
     * </p>
    */
    private final Class<T> objectType;

    /**
     * <p>
     * Represents the columns to be sorted, if values for the 1st column is the same,
     * it will check the 2nd column, and so on.
     * </P>
     * <p>
     * It cannot be null / empty, cannot contain null / empty string.
     * </p>
     * <p>
     * It should be initialized in constructor, should not be modified after initialization.
     * </p>
     * <p>
     * It's used in the compare() method.
     * </p>
    */
    private final String[] sortColumns;

    /**
     * <p>
     * Creates an instance of ReportServiceBean.
     * </p>
     *
     * @param objectType
     *            the class of object that is expected to pass to compare.
     * @param sortColumns
     *            the columns used to sort objects.
     *            method.
     *
     * @throws IllegalArgumentException
     *             if objectType is null, or if sortColumns is null / empty or
     *             contains null / empty string.
     */
    public ReportSortingComparator(Class<T> objectType, String[] sortColumns) {
        ReportHelper.checkNull(null, null, objectType, "objectType");
        ReportHelper.checkContainsNullOrEmpty(null, null, sortColumns, "sortColumns");

        this.objectType = objectType;
        this.sortColumns = sortColumns.clone();
    }

    /**
     * <p>
     * Compares its two arguments for order.
     * </p>
     * <p>
     * Returns a negative integer, zero, or a positive integer as the first
     * argument is less than, equal to, or greater than the second.
     * </p>
     * <p>
     * Note that null value should be put in the last as per requirement, so
     * null value is always greater than other values.
     *
     * And, if values for the 1st column is the same,
     * it will check the 2nd column, and so on.
     * </p>
     *
     * @param o1
     *            the first object to be compared.
     * @param o2
     *            the second object to be compared.

     * @return a negative integer, zero, or a positive integer as the first
     *         argument is less than, equal to, or greater than the second.
     *
     * @throws IllegalStateException
     *             if any sort column cannot be matched to a field defined in
     *             objectType, or if any error occurs while getting column
     *             value.
     */
    @Override
    public int compare(T o1, T o2) {

        // null object compare
        if (o1 == null || o2 == null) {
            return compareNull(o1, o2);
        }

        // sort by each field
        for (String column : sortColumns) {
            // get the field and its values
            Field field;
            Object value1;
            Object value2;
            try {
                field = objectType.getDeclaredField(column);

                field.setAccessible(true);

                value1 = field.get(o1);
                value2 = field.get(o2);

            } catch (NoSuchFieldException e) {
                throw new IllegalStateException("No this field: " + column, e);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Failed to access to the field.", e);
            }

            // use a result variable
            int result = compareValue(field, value1, value2);

            if (result == 0) {
                // this column is the same, try next column
                continue;
            } else {
                return result;
            }
        }
        // all fields are the same
        return 0;
    }

    /**
     * <p>
     * Compares two values of a specific field.
     * </p>
     * <p>
     * Helper methods to help compare two values of a specific field.
     * </p>
     *
     * @param field
     *            the field.
     * @param value1
     *            object one to compare.
     * @param value2
     *            object two to compare.
     * @return 0, equals, or -1, less, otherwise 1.
     */
    @SuppressWarnings("unchecked")
    private int compareValue(Field field, Object value1, Object value2) {
        // check as null
        if (value1 == null || value2 == null) {
            return compareNull(value1, value2);
        }

        // check as named entity, use the name
        if (NamedEntity.class.isAssignableFrom(field.getType())) {
            // use name to compare :
            return ((NamedEntity) value1).getName().compareTo(
                    ((NamedEntity) value2).getName());
        }

        // check as the identifiable entity, use the id
        if (IdentifiableEntity.class.isAssignableFrom(field.getType())) {

            // use id to compare
            return new Long(((IdentifiableEntity) value1).getId())
                    .compareTo(((IdentifiableEntity) value2).getId());

        }

        if (Comparable.class.isAssignableFrom(field.getType())) {

            // use the compareTo method provided by Comparable
            return ((Comparable) value1).compareTo(value2);
        }

        return value1.toString().compareTo(value2.toString());
    }

    /**
     * <p>
     * Compares the null object. (one of which is null.).
     * </p>
     *
     * @param o1
     *            object one to compare.
     * @param o2
     *            object two to compare.
     * @return 0, equals, or -1, less, otherwise 1.
     */
    private int compareNull(Object o1, Object o2) {
        if (o1 == null && o2 == null) {
            return 0;
        } else if (o1 == null) {
            return 1;
        } else {
            return -1;
        }
    }
}
