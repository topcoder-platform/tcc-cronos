/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence.rolecategories;

/**
 * <p>Defines the contract to the object responsible for performing role and category retrievals and category updates.
 *
 * <p><strong>Thread Safety</strong>: Implementations of this interface are <i>not</i> required to be thread safe.
 *
 * @author codedoc, TCSDEVELOPER
 * @version 1.0
 */

public interface RoleCategoryPersistence {
    /**
     * Retrieves all users matching the specified role.
     *
     * @param role the role for which to retrieve users
     * @return a possibly empty array of all the users for the specified role
     * @throws IllegalArgumentException if <code>role</code> is <code>null</code> or an empty string
     * @throws RoleNotFoundException if the role is not found in persistence
     * @throws RoleCategoryPersistenceException if a database error occurs other than the role not being found
     */
    public User[] getUsers(String role) throws RoleCategoryPersistenceException;

    /**
     * Retrieves all categories in the database.
     *
     * @return a possibly empty array of all the categories in the database
     * @throws RoleCategoryPersistenceException if a database error occurs
     */
    public Category[] getAllCategories() throws RoleCategoryPersistenceException;

    /**
     * Similar to {@link #getAllCategories() getAllCategories()}, except only retrieves categories matching the
     * specified chattable flag.
     *
     * @param chattable the chattable flag used to filter the results
     * @return a possibly empty array of categories with the specified chattable flag
     * @throws RoleCategoryPersistenceException if a database error occurs
     */
    public Category[] getCategories(boolean chattable) throws RoleCategoryPersistenceException;

    /**
     * Returns all categories with the specified manager.
     *
     * @param manager the manager to use to filter the results
     * @return a possibly empty array of all the categories with the specified manager
     * @throws IllegalArgumentException if <code>manager</code> is <code>null</code> or an empty string
     * @throws ManagerNotFoundException if the manager is not found in the database
     * @throws RoleCategoryPersistenceException if a database error occurs
     */
    public Category[] getCategories(String manager) throws RoleCategoryPersistenceException;

    /**
     * Associates the specified categories with the specified manager, replacing any existing associations.
     *
     * @param manager the manager
     * @param categories the categories to associate with the manager
     * @throws IllegalArgumentException if either argument is <code>null</code> or <code>manager</code> is an empty
     *   string or <code>categories</code> contains <code>null</code> elements
     * @throws ManagerNotFoundException if the manager does not exist in the database
     * @throws CategoryValidationException if one or more categories fail validation
     * @throws CategoryNotFoundException if one or more categories do not exist in the database
     * @throws RoleCategoryPersistenceException if a database error occurs unrelated to the above exceptions
     */
    public void updateCategories(String manager, Category[] categories) throws RoleCategoryPersistenceException;
}
