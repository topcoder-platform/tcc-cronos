/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.j2ee;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.topcoder.timetracker.user.ConfigurationException;
import com.topcoder.timetracker.user.DataAccessException;
import com.topcoder.timetracker.user.UserManager;
import com.topcoder.timetracker.user.UserFilterFactory;
import com.topcoder.timetracker.user.User;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.security.authorization.SecurityRole;
import com.topcoder.timetracker.user.UserManagerFactory;

/**
 * <p>
 * This is a Stateless <code>SessionBean</code> that is used to provided business services to manage Users within
 * the Time Tracker Application.
 * </p>
 *
 * <p>
 * It contains the same methods as <code>UserManager</code>, and delegates to an instance of
 * <code>UserManager</code>.
 * </p>
 *
 * <p>
 * The instance of <code>UserManager</code> to use is retrieved from the <code>UserManagerFactory</code>.
 * </p>
 *
 * <p>
 * Thread Safety: The UserManager interface implementations are required to be thread-safe, and so this stateless
 * session bean is thread-safe also.
 * </p>
 *
 * @author ShindouHikaru, biotrail, enefem21
 * @version 3.2.1
 * @since 3.2
 */
public class UserManagerSessionBean implements SessionBean, UserManager {

    /**
     * Automatically generated unique ID for use with serialization.
     */
    private static final long serialVersionUID = 871493435671064630L;

    /**
     * <p>
     * This is the instance of <code>SessionContext</code> that was provided by the EJB container.
     * </p>
     *
     * <p>
     * It is stored and made available to subclasses. It is also used when performing a rollback in the case that
     * an exception occurred.
     * </p>
     *
     * <p>
     * It is null initially and will be set by EJB container.
     * </p>
     *
     * <p>
     * Getter and setter are provided.
     * </p>
     */
    private SessionContext sessionContext;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public UserManagerSessionBean() {
        // empty
    }

    /**
     * <p>
     * Defines a user to be recognized within the persistent store managed by this manage.
     * </p>
     *
     * <p>
     * A unique user id will automatically be generated and assigned to the user.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * <p>
     * The implementation will set the User's creation and modification date to the current date. These
     * creation/modification details will also reflect in the persistent store. The creation and modification user
     * is the responsibility of the calling application.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will be used to rollback
     * the transaction.
     * </p>
     *
     * @param user
     *            The user for which the operation should be performed.
     * @param audit
     *            Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException
     *             if user is null or user contains null property which is required when persisting.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public void createUser(User user, boolean audit) throws DataAccessException {
        try {
            getUserManager().createUser(user, audit);
        } catch (DataAccessException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided User parameter.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * <p>
     * The implementation will set the User's creation and modification details to the current date. These
     * creation/modification details will also reflect in the persistent store. The creation and modification user
     * is the responsibility of the calling application.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will be used to rollback
     * the transaction.
     * </p>
     *
     * @param user
     *            The user for which the operation should be performed.
     * @param audit
     *            Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException
     *             if user is null.
     * @throws com.topcoder.timetracker.user.UnrecognizedEntityException
     *             if a user with the provided id was not found in the data store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public void updateUser(User user, boolean audit) throws DataAccessException {
        try {
            getUserManager().updateUser(user, audit);
        } catch (DataAccessException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Modifies the persistent store so that it no longer contains data on the user with the specified userId.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will be used to rollback
     * the transaction.
     * </p>
     *
     * @param userId
     *            The userId for which the operation should be performed.
     * @param audit
     *            Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException
     *             if userId &lt;= 0.
     * @throws com.topcoder.timetracker.user.UnrecognizedEntityException
     *             if a user with the provided id was not found in the data store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public void removeUser(long userId, boolean audit) throws DataAccessException {
        try {
            getUserManager().removeUser(userId, audit);
        } catch (DataAccessException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves a <code>User</code> object that reflects the data in the persistent store on the Time Tracker
     * User with the specified <code>userId</code>.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will be used to rollback
     * the transaction.
     * </p>
     *
     * @param usertId
     *            The id of the user to retrieve.
     * @return The user with specified id.
     *
     * @throws com.topcoder.timetracker.user.UnrecognizedEntityException
     *             if a user with the provided id was not found in the data store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public User getUser(long usertId) throws DataAccessException {
        try {
            return getUserManager().getUser(usertId);
        } catch (DataAccessException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Searches the persistent store for any users that satisfy the criteria that was specified in the provided
     * search filter.
     * </p>
     * <p>
     * The provided filter should be created using either the filters that are created using the
     * <code>UserFilterFactory</code> returned by {@link UserManager#getUserFilterFactory()}, or a composite
     * Search Filters (such as <code>AndFilter</code>, <code>OrFilter</code> and <code>NotFilter</code> from
     * Search Builder component) that combines the filters created using <code>UserFilterFactory</code>.
     * </p>
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will be used to rollback
     * the transaction.
     * </p>
     *
     * @param filter
     *            The filter used to search for projects.
     * @return The projects satisfying the conditions in the search filter.
     *
     * @throws IllegalArgumentException
     *             if filter is null.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public User[] searchUsers(Filter filter) throws DataAccessException {
        try {
            return getUserManager().searchUsers(filter);
        } catch (DataAccessException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This is a batch version of the {@link UserManager#createUser(User, boolean)} method.
     * </p>
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will be used to rollback
     * the transaction.
     * </p>
     *
     * @param users
     *            An array of users for which the operation should be performed.
     * @param audit
     *            Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException
     *             if users is null, empty or contains null values, or some user contains null property which is
     *             required when persisting.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws com.topcoder.timetracker.user.BatchOperationException
     *             if a problem occurs during the batch operation.
     */
    public void addUsers(User[] users, boolean audit) throws DataAccessException {
        try {
            getUserManager().addUsers(users, audit);
        } catch (DataAccessException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This is a batch version of the {@link UserManager#updateUser(User, boolean)} method.
     * </p>
     *
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a user with the provided id was not found in the persistence
     * store.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will be used to rollback
     * the transaction.
     * </p>
     *
     * @param users
     *            An array of users for which the operation should be performed.
     * @param audit
     *            Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException
     *             if users is null, empty or contains null values, or some user contains null property which is
     *             required when persisting.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws com.topcoder.timetracker.user.BatchOperationException
     *             if a problem occurs during the batch operation.
     */
    public void updateUsers(User[] users, boolean audit) throws DataAccessException {
        try {
            getUserManager().updateUsers(users, audit);
        } catch (DataAccessException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This is a batch version of the {@link UserManager#removeUser(long, boolean)} method.
     * </p>
     *
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a user with the provided id was not found in the persistence
     * store.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will be used to rollback
     * the transaction.
     * </p>
     *
     * @param userIds
     *            An array of userIds for which the operation should be performed.
     * @param audit
     *            Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException
     *             if userIds is null, empty or contains values &lt;= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws com.topcoder.timetracker.user.BatchOperationException
     *             if a problem occurs during the batch operation.
     */
    public void removeUsers(long[] userIds, boolean audit) throws DataAccessException {
        try {
            getUserManager().removeUsers(userIds, audit);
        } catch (DataAccessException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This is a batch version of the {@link UserManager#getUser(long)} method.
     * </p>
     *
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a user with the provided id was not found in the persistence
     * store.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will be used to rollback
     * the transaction.
     * </p>
     *
     * @param userIds
     *            An array of userIds for which users should be retrieved.
     * @return The users corresponding to the provided ids.
     *
     * @throws IllegalArgumentException
     *             if userIds is null, empty or contains values &lt;= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws com.topcoder.timetracker.user.BatchOperationException
     *             if a problem occurs during the batch operation.
     */
    public User[] getUsers(long[] userIds) throws DataAccessException {
        try {
            return getUserManager().getUsers(userIds);
        } catch (DataAccessException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This adds an Authorization role to the specified user.
     * </p>
     *
     * <p>
     * The User will now be authorized to perform any actions assigned to that <code>Role</code>.
     * </p>
     *
     * <p>
     * The roles to assign may be retrieved from the Authorization Component.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will be used to rollback
     * the transaction.
     * </p>
     *
     * @param user
     *            The user for which the operation should be performed.
     * @param role
     *            The authorization role to assign to the user.
     *
     * @throws IllegalArgumentException
     *             if any parameter is null.
     * @throws com.topcoder.timetracker.user.UnrecognizedEntityException
     *             if a user with the provided id was not found in the data store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public void addRoleToUser(User user, SecurityRole role) throws DataAccessException {
        try {
            getUserManager().addRoleToUser(user, role);
        } catch (DataAccessException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This removes an Authorization Role from the specified user.
     * </p>
     *
     * <p>
     * The User will no longer be authorized to perform any actions assigned to that <code>Role</code>.
     * </p>
     *
     * <p>
     * The roles to remove may be retrieved from the Authorization Component.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will be used to rollback
     * the transaction.
     * </p>
     *
     * @param user
     *            The user for which the operation should be performed.
     * @param role
     *            The authorization role to remove.
     *
     * @throws IllegalArgumentException
     *             if either argument is null.
     * @throws com.topcoder.timetracker.user.UnrecognizedEntityException
     *             if a role or user was not found in the data store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public void removeRoleFromUser(User user, SecurityRole role) throws DataAccessException {
        try {
            getUserManager().removeRoleFromUser(user, role);
        } catch (DataAccessException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves all the authorization roles for the given user.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will be used to rollback
     * the transaction.
     * </p>
     *
     * @param user
     *            The user for which the operation should be performed.
     * @return An array of SecurityRoles that were assigned to the user (may be empty array)
     *
     * @throws IllegalArgumentException
     *             if the user is null.
     * @throws com.topcoder.timetracker.user.UnrecognizedEntityException
     *             if the given user was not found in the data store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public SecurityRole[] retrieveRolesForUser(User user) throws DataAccessException {
        try {
            return getUserManager().retrieveRolesForUser(user);
        } catch (DataAccessException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Removes all the Authorization roles for the given user.
     * </p>
     *
     * <p>
     * The user will no longer be able to perform any actions that require authorization.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will be used to rollback
     * the transaction.
     * </p>
     *
     * @param user
     *            The user whose roles are to be cleared.
     *
     * @throws IllegalArgumentException
     *             if the user is null.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws com.topcoder.timetracker.user.UnrecognizedEntityException
     *             if a user was not found in the data store.
     */
    public void clearRolesFromUser(User user) throws DataAccessException {
        try {
            getUserManager().clearRolesFromUser(user);
        } catch (DataAccessException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Authenticates a provided user name against the login information that is in the data store.
     * </p>
     *
     * <p>
     * The given password will be compared against the one that is stored in persistence, and the results of the
     * comparison will be returned.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will be used to rollback
     * the transaction.
     * </p>
     *
     * @param username
     *            The user name to authenticate.
     * @param password
     *            The password provided by the user.
     * @return true if the password matches; false otherwise.
     *
     * @throws IllegalArgumentException
     *             if user name or password is null or empty string
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public boolean authenticateUser(String username, String password) throws DataAccessException {
        try {
            return getUserManager().authenticateUser(username, password);
        } catch (DataAccessException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves all the users that are currently in the persistent store.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will be used to rollback
     * the transaction.
     * </p>
     *
     * @return An array of users retrieved from the persistent store.
     *
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public User[] getAllUsers() throws DataAccessException {
        try {
            return getUserManager().getAllUsers();
        } catch (DataAccessException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves the <code>UserFilterFactory</code> that is capable of creating SearchFilters to use when
     * searching for users.
     * </p>
     *
     * <p>
     * This is used to conveniently specify the search criteria that should be used. The filters returned by the
     * given factory should be used with {@link UserManager#searchUsers(Filter)} method.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will be used to rollback
     * the transaction and <code>null</code> will be returned.
     * </p>
     *
     * @return the <code>UserFilterFactory</code> that is capable of creating SearchFilters to use when searching
     *         for users.
     */
    public UserFilterFactory getUserFilterFactory() {
        try {
            return getUserManager().getUserFilterFactory();
        } catch (DataAccessException e) {
            sessionContext.setRollbackOnly();
            return null;
        }
    }

    /**
     * <p>
     * This method implements <code>ejbCreate</code> method to complete the <code>SessionBean</code> interface.
     * </p>
     *
     * <p>
     * It has an empty method body.
     * </p>
     */
    public void ejbCreate() {
        // empty
    }

    /**
     * <p>
     * This method has simply been included to complete the <code>SessionBean</code> interface.
     * </p>
     *
     * <p>
     * It has an empty method body.
     * </p>
     */
    public void ejbActivate() {
        // empty
    }

    /**
     * <p>
     * This method has simply been included to complete the <code>SessionBean</code> interface.
     * </p>
     *
     * <p>
     * It has an empty method body.
     * </p>
     */
    public void ejbPassivate() {
        // empty
    }

    /**
     * <p>
     * This method has simply been included to complete the <code>SessionBean</code> interface.
     * </p>
     *
     * <p>
     * It has an empty method body.
     * </p>
     */
    public void ejbRemove() {
        // empty
    }

    /**
     * <p>
     * Sets the <code>SessionContext</code> to use for this session.
     * </p>
     *
     * <p>
     * This method is included to comply with the <code>SessionBean</code> interface.
     * </p>
     *
     * @param ctx
     *            The <code>SessionContext</code> to use
     */
    public void setSessionContext(SessionContext ctx) {
        this.sessionContext = ctx;
    }

    /**
     * <p>
     * Protected method that allows subclasses to access the session context if necessary.
     * </p>
     *
     * @return The session context provided by the EJB container.
     */
    protected SessionContext getSessionContext() {
        return sessionContext;
    }

    /**
     * <p>
     * This method gets the current user manager in the factory.
     * </p>
     *
     * @return the current user manager in the factory
     *
     * @throws DataAccessException
     *             to wrap the <code>ConfigurationException</code> thrown by <code>UserManagerFactory</code>
     */
    private UserManager getUserManager() throws DataAccessException {
        try {
            return UserManagerFactory.getUserManager();
        } catch (ConfigurationException e) {
            throw new DataAccessException("ConfigurationException occurs when getting the user manager.", e);
        }
    }
}