/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.database.statustracker;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * This class represents an entity type in the database. The entity type can be tracked by
 * this component.
 * </p>
 *
 * <p>
 * This class includes an id, an entity name, a list of primary key columns for an instance of
 * the entity and a list of valid statuses for this entity type.
 * </p>
 *
 * <p>
 * The corresponding tables defined for this class are <b>entity</b>, <b>entity_id_columns</b>
 * and <b>valid_entity_statuses</b>.
 * </p>
 *
 * <p>
 * An example of an Entity would be an Order.
 * An Order might have two primary key columns, such as "order_number" and "customer_number",
 * and any number of statuses, such as "Open", "Submitted", "Shipped", "Backordered" or "completed".
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is mutable, the valid status instances in this class are mutable so
 * this class is not thread-safe. Users are required to use this class in a thread-safe way.
 * </p>
 *
 * @author dplass, TCSDEVELOPER
 * @version 1.0
 * @see Status
 * @see EntityStatus
 */
public class Entity {
    /**
     * <p>
     * This variable represents the unique identifier of this entity.
     * </p>
     *
     * <p>
     * It is set in the constructor only and immutable.
     * The value for this variable will never be negative.
     * </p>
     */
    private final long id;

    /**
     * <p>
     * This variable represents the unique name of this entity type.
     * </p>
     *
     * <p>
     * It is set in the constructor and immutable.
     * The value for this variable will never be null or empty after trim.
     * </p>
     */
    private final String name;

    /**
     * <p>
     * This variable represents the primary key columns that uniquely identify instances of
     * this entity.
     * </p>
     *
     * <p>
     * It is set in the constructor and immutable.
     * Will never be null, and must always contain at least one entry. Any entries will
     * be non-null and non-empty after trim.
     * </p>
     */
    private final String[] pkColumns;

    /**
     * <p>
     * This variable represents the Statuses objects which are the valid statuses for this entity type.
     * </p>
     *
     * <p>
     * It is set in in the constructor.
     * Will never be null, and must always contain at least one entry. No entry will be null.
     * </p>
     *
     * <p>
     * Note, the Status instances in this array are mutable.
     * </p>
     */
    private final Status[] validStatuses;

    /**
     * <p>
     * Construct a new Entity type with the given initial parameters.
     * The initials parameters include id, name, primary key columns and valid statuses.
     * </p>
     *
     * @param id the unique id of this Entity
     * @param name the unique name of this entity
     * @param primaryKeyCols represent the primary key columns that uniquely identify instances of this entity type.
     * @param statuses the valid statuses for this entity type
     *
     * @throws IllegalArgumentException if id is less than 0, or if name is null or empty after trim,
     * or if primaryKeyCols is null, or of size 0, or if any String in primaryKeyCols is null or empty
     * after trim, or if statuses is null or of size 0, or if there are any nulls or duplicates in either array.
     */
    public Entity(long id, String name, String[] primaryKeyCols, Status[] statuses) {
        if (id < 0) {
            throw new IllegalArgumentException("The id parameter is negative.");
        }
        Util.checkString(name, "name");
        checkPrimaryKeyCols(primaryKeyCols, "primaryKeyCols");
        checkStatuses(statuses, "statuses");

        this.id = id;
        this.name = name;

        // note, the arrays are shallow copied
        this.pkColumns = (String[]) primaryKeyCols.clone();
        this.validStatuses = (Status[]) statuses.clone();
    }

    /**
     * <p>
     * Returns the unique id of this Entity type.
     * </p>
     *
     * @return Returns the id. Will never be negative
     */
    public long getId() {
        return id;
    }

    /**
     * <p>
     * Returns the name of this Entity type.
     * </p>
     *
     * @return Returns the name of this entity type.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Returns the primary key column names which are used to uniquely identify entity instances of this
     * entity type.
     * </p>
     *
     * @return Returns the primary key columns.
     */
    public String[] getPrimaryKeyColumns() {
        // no copy is needed
        return pkColumns;
    }

    /**
     * <p>
     * Returns the valid Status objects that are associated with this Entity type.
     * </p>
     *
     * @return Returns a List of Status objects. Will never be null, and will always have at least
     * one entry.
     */
    public Status[] getValidStatuses() {
        // no copy is needed
        return validStatuses;
    }

    /**
     * <p>
     * This method implements the equals method, to be able to compare Entities for uniqueness by
     * their ids.
     * </p>
     *
     * @param obj the other object to compare for equality
     * @return true if 'obj' is an Entity with the same id as this Entity, false otherwise.
     */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        // compare the class names instead of using instanceof operator
        if (obj.getClass().getName().equals(this.getClass().getName())) {
            return (((Entity) obj).id == id);
        }

        return false;
    }

    /**
     * <p>
     * This method implements the hashCode method, to allow this class to be easily used in hash
     * tables, etc.
     * </p>
     *
     * @return the id of this Entity as its hash code.
     */
    public int hashCode() {
        return (int) id;
    }

    /**
     * <p>
     * This method is used as argument checking for primary key columns in the constructor.
     * </p>
     *
     * @param arg the primary key columns
     * @param name the name of the primary key columns in the constructor
     *
     * @throws IllegalArgumentException if primaryKeyCols is null, or of size 0, or if any String in
     * primaryKeyCols is null or empty after trim, or if there are duplicate elements in the array.
     */
    private void checkPrimaryKeyCols(String[] arg, String name) {
        checkArray(arg, name);
        Set visitedElements = new HashSet();
        for (int i = 0; i < arg.length; i++) {
            if (arg[i] == null) {
                throw new IllegalArgumentException("The primary key columns contains null element.");
            }
            if (arg[i].trim().length() == 0) {
                throw new IllegalArgumentException("The primary key columns contains empty element.");
            }
            // duplicate strings are not allowed
            if (visitedElements.contains(arg[i])) {
                throw new IllegalArgumentException("The primary key columns contains duplicate element.");
            } else {
                visitedElements.add(arg[i]);
            }
        }
    }

    /**
     * <p>
     * This method is used as argument checking for valid statuses in the constructor.
     * </p>
     *
     * @param statuses the valid status array
     * @param name the name of the valid status array in the constructor
     *
     * @throws IllegalArgumentException statuses is null or of size 0, or if there are duplicate elements
     * in the array.
     */
    private void checkStatuses(Status[] statuses, String name) {
        checkArray(statuses, name);
        Set visitedStatuses = new HashSet();
        for (int i = 0; i < statuses.length; i++) {
            if (statuses[i] == null) {
                throw new IllegalArgumentException("The valid statuses contains null element.");
            }
            // duplicate statuses are not allowed
            if (visitedStatuses.contains(statuses[i])) {
                throw new IllegalArgumentException("The valid statuses contains duplicate element.");
            } else {
                visitedStatuses.add(statuses[i]);
            }
        }
    }

    /**
     * <p>
     * Checks whether the given array is null or empty (of size zero).
     * </p>
     *
     * @param arg the argument to check
     * @param name the name of the argument to check
     *
     * @throws IllegalArgumentException if arg is null or of size zero.
     */
    private void checkArray(Object[] arg, String name) {
        Util.checkNull(arg, name);
        if (arg.length == 0) {
            throw new IllegalArgumentException("The length of the " + name + " array is zero.");
        }
    }
}
