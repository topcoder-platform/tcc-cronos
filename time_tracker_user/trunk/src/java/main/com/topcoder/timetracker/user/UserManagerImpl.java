/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.topcoder.security.authenticationfactory.AuthenticateException;
import com.topcoder.security.authenticationfactory.AuthenticationFactory;
import com.topcoder.security.authenticationfactory.Authenticator;
import com.topcoder.security.authorization.AuthorizationPersistence;
import com.topcoder.security.authorization.AuthorizationPersistenceException;
import com.topcoder.security.authorization.Principal;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.security.authorization.SecurityRole;

/**
 * <p>
 * This is a default implementation of the <code>UserManager</code> interface.
 * </p>
 * <p>
 * It utilizes instances of the <code>UserDAO</code> in order to fulfill the necessary CRUDE and search
 * operations defined for the Time Tracker User.
 * </p>
 * <p>
 * It also maintains instances of <code>AuthorizationPersistence</code> to manage the Security Roles for each
 * user, and <code>Authenticator</code> to manage the authentication of a user.
 * </p>
 * <p>
 * Thread safety: Thread safety is dependent on the DAO implementation and <code>AuthorizationPersistence</code>,
 * and <code>Authenticator</code>. Since the DAO and <code>Authenticator</code> are required to be
 * thread-safe, this class is thread safe in those aspects. For <code>AuthorizationPersistence</code>, it is not
 * thread safe but synchronized is used to ensure the thread safety.
 * </p>
 *
 * @author ShindouHikaru, biotrail, George1, enefem21
 * @version 3.2.1
 * @since 3.2
 */
public class UserManagerImpl implements UserManager {

    /**
     * <p>
     * This is the DAO instance that is used to retrieve data from and modify the persistent store when dealing
     * with the Time Tracker User Data.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It will never be null.
     * </p>
     */
    private final UserDAO userDao;

    /**
     * <p>
     * This is an instance of the <code>AuthorizationPersistence</code> from the TC Authorization component that
     * is used to perform operations with regards to a Time Tracker User's security roles.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It will never be null and is synchronized when accessing.
     * </p>
     */
    private final AuthorizationPersistence authPersistence;

    /**
     * <p>
     * This is an instance of the <code>Authenticator</code> that is used to authenticate the username and
     * password combination against the data in the datastore.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It will never be null.
     * </p>
     */
    private final Authenticator authenticator;

    /**
     * <p>
     * Creates a <code>UserManagerImpl</code> with the <code>userDao</code>, <code>authPersistence</code>
     * and <code>authenticatorName</code> given.
     * </p>
     *
     * @param userDao
     *            The user DAO to use.
     * @param authPersistence
     *            The Authorization Persistence to use.
     * @param authenticatorName
     *            The authenticator to use.
     *
     * @throws IllegalArgumentException
     *             if any of the arguments are null or authenticatorName is empty.
     * @throws ConfigurationException
     *             if fails to initialize the <code>Authenticator</code> instance using the given authentication
     *             name
     */
    public UserManagerImpl(UserDAO userDao, AuthorizationPersistence authPersistence, String authenticatorName)
        throws ConfigurationException {
        Util.checkNull(userDao, "userDao");
        Util.checkNull(authPersistence, "authPersistence");
        Util.checkString(authenticatorName, "authenticatorName");

        this.userDao = userDao;
        this.authPersistence = authPersistence;

        try {
            this.authenticator = AuthenticationFactory.getInstance().getAuthenticator(authenticatorName);

            if (this.authenticator == null) {
                throw new ConfigurationException("The authenticator with name [" + authenticatorName
                    + "] is not defined.");
            }
        } catch (com.topcoder.security.authenticationfactory.ConfigurationException e) {
            throw new ConfigurationException("Failed to instantiate the Authenticator instance.", e);
        }
    }

    /**
     * <p>
     * Defines a user to be recognized within the persistent store managed by this manager.
     * </p>
     * <p>
     * A unique user id will automatically be generated and assigned to the user.
     * </p>
     * <p>
     * There is also the option to perform an audit.
     * </p>
     * <p>
     * This implementation will set the User's creation and modification details to the current date. These
     * creation/modification details will also reflect in the persistent store.
     * </p>
     *
     * @param user
     *            The user for which the operation should be performed.
     * @param audit
     *            Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException
     *             if user is null.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public void createUser(User user, boolean audit) throws DataAccessException {
        User[] users = new User[] {user};
        Util.updateDates(users, true);
        try {
            userDao.addUsers(users, audit);
        } catch (BatchOperationException e) {
            throw Util.convertBatchExceptionToSingleException(e, "Failed to create the user.");
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
     * This implementation will set the User's creation and modification details to the current date. These
     * creation/modification details will also reflect in the persistent store.
     * </p>
     *
     * @param user
     *            The user for which the operation should be performed.
     * @param audit
     *            Indicates whether an audit should be performed.
     * @throws IllegalArgumentException
     *             if user is null.
     *
     * @throws UnrecognizedEntityException
     *             if a user with the provided id was not found in the data store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public void updateUser(User user, boolean audit) throws DataAccessException {
        User[] users = new User[] {user};
        Util.updateDates(users, false);

        try {
            userDao.updateUsers(users, audit);
        } catch (BatchOperationException e) {
            throw Util.convertBatchExceptionToSingleException(e, "Failed to update the user.");
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
     * @param userId
     *            The userId for which the operation should be performed.
     * @param audit
     *            Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException
     *             if userId &lt;= 0.
     * @throws UnrecognizedEntityException
     *             if a user with the provided id was not found in the data store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public void removeUser(long userId, boolean audit) throws DataAccessException {
        try {
            userDao.removeUsers(new long[] {userId}, audit);
        } catch (BatchOperationException e) {
            throw Util.convertBatchExceptionToSingleException(e, "Failed to remove the user.");
        }
    }

    /**
     * <p>
     * Retrieves a User object that reflects the data in the persistent store on the Time Tracker User with the
     * specified userId.
     * </p>
     *
     * @return The user with specified id.
     * @param usertId
     *            The id of the user to retrieve.
     *
     * @throws UnrecognizedEntityException
     *             if a project with the provided id was not found in the data store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public User getUser(long usertId) throws DataAccessException {
        try {
            User[] users = userDao.getUsers(new long[] {usertId});
            return users[0];
        } catch (BatchOperationException e) {
            throw Util.convertBatchExceptionToSingleException(e, "Failed to get the user.");
        }
    }

    /**
     * <p>
     * Searches the persistent store for any users that satisfy the criteria that was specified in the provided
     * search filter.
     * </p>
     *
     * <p>
     * The provided filter should be created using either the filters that are created using the
     * <code>UserFilterFactory</code> returned by {@link UserManager#getUserFilterFactory()}, or a composite
     * Search Filters (such as <code>AndFilter</code>, <code>OrFilter</code> and <code>NotFilter</code> from
     * Search Builder component) that combines the filters created using <code>UserFilterFactory</code>.
     * </p>
     *
     * @return The projects satisfying the conditions in the search filter.
     * @param filter
     *            The filter used to search for projects.
     *
     * @throws IllegalArgumentException
     *             if filter is null.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public User[] searchUsers(Filter filter) throws DataAccessException {
        return userDao.searchUsers(filter);
    }

    /**
     * <p>
     * This is a batch version of the {@link UserManagerImpl#createUser(User, boolean)} method.
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
     * @throws BatchOperationException
     *             if a problem occurs during the batch operation.
     */
    public void addUsers(User[] users, boolean audit) throws DataAccessException {
        Util.updateDates(users, true);
        userDao.addUsers(users, audit);
    }

    /**
     * <p>
     * This is a batch version of the {@link UserManagerImpl#updateUser(User, boolean)} method.
     * </p>
     *
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a user with the provided id was not found in the persistence
     * store.
     * </p>
     *
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
     * @throws BatchOperationException
     *             if a problem occurs during the batch operation.
     */
    public void updateUsers(User[] users, boolean audit) throws DataAccessException {
        Util.updateDates(users, false);
        userDao.updateUsers(users, audit);
    }

    /**
     * <p>
     * This is a batch version of the {@link UserManagerImpl#removeUser(long, boolean)} method.
     * </p>
     *
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a user with the provided id was not found in the persistence
     * store.
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
     * @throws BatchOperationException
     *             if a problem occurs during the batch operation.
     */
    public void removeUsers(long[] userIds, boolean audit) throws DataAccessException {
        userDao.removeUsers(userIds, audit);
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
     * @return The users corresponding to the provided ids.
     * @param userIds
     *            An array of userIds for which users should be retrieved.
     *
     * @throws IllegalArgumentException
     *             if userIds is null, empty or contains values &lt;= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException
     *             if a problem occurs during the batch operation.
     */
    public User[] getUsers(long[] userIds) throws DataAccessException {
        return userDao.getUsers(userIds);
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
     * @param user
     *            The user for which the operation should be performed.
     * @param role
     *            The authorization role to assign to the user.
     *
     * @throws IllegalArgumentException
     *             if any parameter is null.
     * @throws UnrecognizedEntityException
     *             if a user with the provided id was not found in the data store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public void addRoleToUser(User user, SecurityRole role) throws DataAccessException {
        Util.checkNull(user, "user");
        Util.checkNull(role, "role");

        synchronized (authPersistence) {
            for (Iterator it = searchPrincipals(user).iterator(); it.hasNext();) {
                Principal principal = (Principal) it.next();
                try {
                    authPersistence.addRoleForPrincipal(principal, role);
                } catch (AuthorizationPersistenceException e) {
                    throw new DataAccessException("Failed to add role for principal.", e);
                }
            }
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
     * @param user
     *            The user for which the operation should be performed.
     * @param role
     *            The authorization role to remove.
     *
     * @throws IllegalArgumentException
     *             if either argument is null.
     * @throws UnrecognizedEntityException
     *             if a role or user was not found in the data store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public void removeRoleFromUser(User user, SecurityRole role) throws DataAccessException {
        Util.checkNull(user, "user");
        Util.checkNull(role, "role");

        synchronized (authPersistence) {
            for (Iterator it = searchPrincipals(user).iterator(); it.hasNext();) {
                Principal principal = (Principal) it.next();
                try {
                    authPersistence.removeRoleForPrincipal(principal, role);
                } catch (AuthorizationPersistenceException e) {
                    throw new DataAccessException("Failed to remove role for principal.", e);
                }
            }
        }
    }

    /**
     * <p>
     * Retrieves all the authorization roles for the given user.
     * </p>
     *
     * @return An array of SecurityRoles that were assigned to the user (may be empty array)
     * @param user
     *            The user for which the operation should be performed.
     *
     * @throws IllegalArgumentException
     *             if the user is null.
     * @throws UnrecognizedEntityException
     *             if the given user was not found in the data store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public SecurityRole[] retrieveRolesForUser(User user) throws DataAccessException {
        Util.checkNull(user, "user");
        List roles = new ArrayList();

        synchronized (authPersistence) {
            for (Iterator it = searchPrincipals(user).iterator(); it.hasNext();) {
                Principal principal = (Principal) it.next();
                try {
                    roles.addAll(authPersistence.getRolesForPrincipal(principal));
                } catch (AuthorizationPersistenceException e) {
                    throw new DataAccessException("Failed to get roles for principal.", e);
                }
            }
        }

        return (SecurityRole[]) roles.toArray(new SecurityRole[roles.size()]);
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
     * @param user
     *            The user whose roles are to be cleared.
     *
     * @throws IllegalArgumentException
     *             if the user is null.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws UnrecognizedEntityException
     *             if a user was not found in the data store.
     */
    public void clearRolesFromUser(User user) throws DataAccessException {
        SecurityRole[] roles = retrieveRolesForUser(user);

        synchronized (authPersistence) {
            for (Iterator it = searchPrincipals(user).iterator(); it.hasNext();) {
                Principal principal = (Principal) it.next();
                for (int i = 0; i < roles.length; i++) {
                    try {
                        authPersistence.removeRoleForPrincipal(principal, roles[i]);
                    } catch (AuthorizationPersistenceException e) {
                        throw new DataAccessException("Failed to clear roles for principal.", e);
                    }
                }
            }
        }
    }

    /**
     * <p>
     * This method searches all the principals with the give user name.
     * </p>
     *
     * @param user
     *            the <code>User</code> instance to get the user name
     * @return a collection of principals that have the given user name
     *
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    private Collection searchPrincipals(User user) throws DataAccessException {
        try {
            Collection principals = authPersistence.searchPrincipals(user.getUsername());

            if (principals.size() == 0) {
                throw new UnrecognizedEntityException(user.getId(), "Principal for user name ["
                    + user.getUsername() + "] can not be found.");
            }

            return principals;
        } catch (AuthorizationPersistenceException e) {
            throw new DataAccessException("Failed to search the principals.", e);
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
     * @return true if the password matches; false otherwise.
     * @param username
     *            The username to authenticate.
     * @param password
     *            The password provided by the user.
     *
     * @throws IllegalArgumentException
     *             if username or password is null or empty string
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public boolean authenticateUser(String username, String password) throws DataAccessException {
        Util.checkString(username, "username");
        Util.checkString(password, "password");

        com.topcoder.security.authenticationfactory.Principal principal =
            new com.topcoder.security.authenticationfactory.Principal(username);
        principal.addMapping(UserAuthenticator.USERNAME_KEY, username);
        principal.addMapping(UserAuthenticator.PASSWORD_KEY, password);

        try {
            return authenticator.authenticate(principal).isSuccessful();
        } catch (AuthenticateException e) {
            throw new DataAccessException("Failed to authenticate the user.", e);
        }
    }

    /**
     * <p>
     * Retrieves all the users that are currently in the persistent store.
     * </p>
     *
     * @return An array of users retrieved from the persistent store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public User[] getAllUsers() throws DataAccessException {
        return userDao.getAllUsers();
    }

    /**
     * <p>
     * Retrieves the <code>UserFilterFactory</code> that is capable of creating SearchFilters to use when
     * searching for users.
     * </p>
     *
     * <p>
     * This is used to conveniently specify the search criteria that should be used. The filters returned by the
     * given factory should be used with {@link UserManagerImpl#searchUsers(Filter)} method.
     * </p>
     *
     * @return the UserFilterFactory that is capable of creating SearchFilters to use when searching for users.
     */
    public UserFilterFactory getUserFilterFactory() {
        return userDao.getUserFilterFactory();
    }
}
