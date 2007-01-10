/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.topcoder.timetracker.common.Utils;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.security.authenticationfactory.AbstractAuthenticator;
import com.topcoder.security.authenticationfactory.AuthenticateException;
import com.topcoder.security.authenticationfactory.Response;
import com.topcoder.security.authorization.AuthorizationPersistence;
import com.topcoder.security.authorization.AuthorizationPersistenceException;
import com.topcoder.security.authorization.Principal;
import com.topcoder.security.authorization.SecurityRole;
import com.topcoder.security.authorization.persistence.GeneralPrincipal;

/**
 * <p>
 * This class is a facade that provides access to all functions related to a Time Tracker User. It provides CRUDE
 * methods to manage the users within the persistent store. It works with the Authorization Manager component to
 * manage the Authorization Roles for a user. And finally, it is also capable of authenticating a User through a
 * username/password combination.
 * </p>
 * <p>
 * This class automatically manages the representation of the User as an authorization Role within the data store
 * of the Authorization component. When a user is created via the UserManager, the user will be defined both in the
 * Time Tracker data store (via the UserDAO), and the Authorization data store (via Authorization Persistence).
 * Updates will also reflect in both data stores.
 * </p>
 * <p>
 * Thread Safety: - Like the DAO classes, the thread safety of this class is dependent on the underlying data
 * store. The developer should be aware that this class works with the Authorization component, whose default
 * persistence is not thread safe. Threading issues may be avoided by utilizing a thread safe persistence for
 * Authorization, and proper JDBC configuration. See thread safety for UserDAO as well.
 * </p>
 *
 * @author ShindouHikaru
 * @author kr00tki
 * @version 2.0
 */
public class UserManager {

    /**
     * The 'create' operation flag. It is used in batch mode.
     */
    private static final int CREATE_OP = 1;

    /**
     * The 'delete' operation flag. It is used in batch mode.
     */
    private static final int DELETE_OP = 2;

    /**
     * The 'update' operation flag. It is used in batch mode.
     */
    private static final int UPDATE_OP = 3;

    /**
     * <p>
     * This is the Data Access Object that is used to perform CRUDE operations on the persistent store.
     * </p>
     * <p>
     * Initialized In: Constructor
     * </p>
     * <p>
     * Modified In: Not Modified
     * </p>
     * <p>
     * Accessed In: Not Accessed
     * </p>
     * <p>
     * Utilized In: createUser, retrieveUser, updateUser, deleteUser, listUsers, searchUsers createUsers,
     * retrieveUsers, updateUsers, deleteUsers
     * </p>
     *
     *
     */
    private UserDAO userDao;

    /**
     * <p>
     * This is an authenticator implementation that is used to authenticate the User given a username/password
     * combination.
     * </p>
     * <p>
     * Initialized In: Constructor
     * </p>
     * <p>
     * Modified In: Not Modified
     * </p>
     * <p>
     * Accessed In: Not Accessed
     * </p>
     * <p>
     * Utilized In: authenticateUser
     * </p>
     *
     *
     */
    private AbstractAuthenticator userAuthenticator;

    /**
     * <p>
     * The Authorization Persistence that is responsible for managing the different Roles and access privileges of
     * the Users.
     * </p>
     * <p>
     * Initialized In: Constructor
     * </p>
     * <p>
     * Modified In: Not Modified
     * </p>
     * <p>
     * Accessed In: Not Accessed
     * </p>
     * <p>
     * Utilized In: addRoleForUser, addRolesForUsers, removeRoleForUser, removeRolesForUsers, clearRolesForUser,
     * clearRolesForUsers, listRolesForUser, createUser, retrieveUser, updateUser, deleteUser, createUsers,
     * updateUsers, deleteUsers
     * </p>
     */
    private AuthorizationPersistence authorizationPersistence;

    /**
     * <p>
     * Creates a User Manager that will utilize the provided DAO for datastore access, the provided
     * AbstractAuthenticator for user authentication, and the Authorization Persistence for User-Role management.
     * </p>
     *
     *
     * @param userDAO The UserDAO implementation that will be used.
     * @param userAuthenticator The AbstractAuthenticator implementation that will be used to authenticate users
     *        against their password.
     * @param authPersistence The authorization persistence to use to manage user Roles.
     *
     * @throws IllegalArgumentException if any of the provided arguments is null.
     */
    public UserManager(UserDAO userDAO, AbstractAuthenticator userAuthenticator,
            AuthorizationPersistence authPersistence) {

        Utils.checkNull(userDAO, "userDAO");
        Utils.checkNull(userAuthenticator, "userAuthenticator");
        Utils.checkNull(authPersistence, "authPersistence");

        this.authorizationPersistence = authPersistence;
        this.userAuthenticator = userAuthenticator;
        this.userDao = userDAO;
    }

    /**
     * <p>
     * Creates a datastore entry for the given user. An id is automatically generated by the DAO and assigned to
     * the user. The User is also considered to have been created by the specified username.
     * </p>
     *
     * @return The same user Object, with an assigned id.
     * @param user The user to create within the datastore.
     * @param username The username of the user responsible for creating the User entry within the datastore.
     * @throws IllegalArgumentException if the user or username is null, or if username is an empty String.
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     */
    public User createUser(User user, String username) throws UserDAOException {
        user = userDao.createUser(user, username);
        try {
            authorizationPersistence.addPrincipal(toAuthorizationPrincipal(user));
        } catch (AuthorizationPersistenceException ex) {
            // ignore this exception
        }

        return user;
    }

    /**
     * <p>
     * Retrieves a User from the datastore with the provided id. If no User with that id exists, then a null is
     * returned.
     * </p>
     *
     * @return The User corresponding to the given id, or null if no user with given id was found.
     * @param id The id of the user to retrieve from the datastore.
     * @throws IllegalArgumentException if id is <=0
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     */
    public User retrieveUser(long id) throws UserDAOException {
        return userDao.retrieveUser(id);
    }

    /**
     * <p>
     * Updates the given User in the data store. The User is considered to have been modified by the specified
     * username.
     * </p>
     *
     * @param user The User entity to modify.
     * @param username The username of the user responsible for performing the update.
     * @throws IllegalArgumentException if the user is null, or the username is null, or the username is an empty
     *         String.
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     * @throws UserNotFoundException if the Time Tracker User to update was not found in the data store.
     */
    public void updateUser(User user, String username) throws UserNotFoundException, UserDAOException {
        userDao.updateUser(user, username);
        try {
            authorizationPersistence.updatePrincipal(toAuthorizationPrincipal(user));
        } catch (AuthorizationPersistenceException ex) {
            // ignore this exception
        }
    }

    /**
     * <p>
     * Removes the specified User from the data store.
     * </p>
     *
     * @param user The user to delete.
     * @throws IllegalArgumentException if the user is null.
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     * @throws UserNotFoundException if the Time Tracker User to delete was not found in the data store.
     */
    public void deleteUser(User user) throws UserNotFoundException, UserDAOException {
        userDao.deleteUser(user);
        try {
            authorizationPersistence.removePrincipal(toAuthorizationPrincipal(user));
        } catch (AuthorizationPersistenceException ex) {
            // ignore this exception
        }
    }

    /**
     * <p>
     * Enumerates all the Users that are present within the data store.
     * </p>
     *
     * @return A list of all the Users within the data store.
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     */
    public User[] listUsers() throws UserDAOException {
        return userDao.listUsers();
    }

    /**
     * <p>
     * Returns a list of all the users within the datastore that satisfy the filters that are provided. The filters
     * are defined using classes from the Search Builder v1.1 component and com.topcoder.timetracker. common.search
     * package.
     * </p>
     *
     * @return A list of users that satisfy the search criterion.
     * @param filter The filter that is used as criterion to facilitate the search..
     * @throws IllegalArgumentException if the filter is null.
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     */
    public User[] searchUsers(Filter filter) throws UserDAOException {
        return userDao.searchUsers(filter);
    }

    /**
     * <p>
     * Authenticates the user with the provided username/password combination. This is delegated to the configured
     * abstract authenticator.
     * </p>
     *
     * @return The Response to use.
     * @param username The username of the user being authenticated.
     * @param password The password that will be authenticated against the username.
     * @throws IllegalArgumentException if either username or password is null or an empty String.
     * @throws AuthenticateException if a problem occurs while authenticating.
     */
    public Response authenticateUser(String username, String password) throws AuthenticateException {
        Utils.checkString(username, "username", false);
        Utils.checkString(password, "password", false);
        // calculate the id to be used by the authenticator cache
        // if next request with the same user and pass will be done
        // the response will be retrieved from the cache to speed thing up
        int id = username.hashCode() ^ password.hashCode() ^ 7;
        com.topcoder.security.authenticationfactory.Principal principal = createPrincipal(id, username, password);

        return userAuthenticator.authenticate(principal);
    }

    /**
     * <p>
     * Assigns the specified Authorization Role for the given Time Tracker User.
     * </p>
     *
     * @param user The user for which a Role is to be added.
     * @param role The Role to assign to the user.
     * @throws IllegalArgumentException if the user or role is null.
     * @throws AuthorizationPersistenceException if a problem occurs while accessing Authorization persistence.
     */
    public void addRoleForUser(User user, SecurityRole role) throws AuthorizationPersistenceException {
        Utils.checkNull(user, "user");
        Utils.checkNull(role, "role");

        Principal principal = toAuthorizationPrincipal(user);
        createIfNotExist(principal);
        authorizationPersistence.addRoleForPrincipal(principal, role);
    }

    /**
     * Checks if given <code>principal</code> exists in the database and if not, creates it.
     *
     * @param principal the principal to be add.
     * @throws AuthorizationPersistenceException if any error occurs.
     * @return <code>false</code> if the principal not exists and was added to list; <code>true</code> if
     *         exists.
     */
    private boolean createIfNotExist(com.topcoder.security.authorization.Principal principal)
            throws AuthorizationPersistenceException {

        if (!authorizationPersistence.getPrincipals().contains(principal)) {
            authorizationPersistence.addPrincipal(principal);
            return false;
        }

        return true;
    }

    /**
     * Creates the authorization principal from the given user object.
     *
     * @param user the user to be converted.
     * @return the authorization principal.
     */
    private static Principal toAuthorizationPrincipal(User user) {
        return new GeneralPrincipal(user.getId(), user.getUsername());
    }

    /**
     * <p>
     * Assigns the specified set of Authorization Roles for the given set of Time Tracker Users. All the user(s)
     * will have the provided roles after this method call has finished.
     * </p>
     *
     * @param users An array of users to which the roles will be assigned.
     * @param roles The roles to assign to the users.
     * @throws IllegalArgumentException if the users or roles is null, or contains null elements.
     * @throws AuthorizationPersistenceException if a problem occurs while accessing Authorization persistence.
     */
    public void addRolesForUsers(User[] users, SecurityRole[] roles) throws AuthorizationPersistenceException {
        Utils.checkNull(users, "users");
        checkRoleArray(roles, "roles");

        // get the principals
        com.topcoder.security.authorization.Principal[] principals = toPrincipalArray(users);
        // for each principal
        for (int i = 0; i < principals.length; i++) {
            // check if principal exists and if not, add it
            createIfNotExist(principals[i]);
            // for each role
            for (int j = 0; j < roles.length; j++) {
                // add role to principal
                authorizationPersistence.addRoleForPrincipal(principals[i], roles[j]);
            }
        }
    }

    /**
     * <p>
     * Checks if given roles array contains <code>null</code>. If so, IllegalArgumentException will be thrown.
     * </p>
     *
     * @param roles the roles array.
     * @param name the name of the method argument.
     */
    private static void checkRoleArray(SecurityRole[] roles, String name) {
        Utils.checkNull(roles, name);
        for (int i = 0; i < roles.length; i++) {
            if (roles[i] == null) {
                throw new IllegalArgumentException("The " + name + " array contains null at: " + i);
            }
        }
    }

    /**
     * <p>
     * Removes the given Authorization Role for the specified User. The User will no longer be assigned that Role
     * after this method call. If the user initially did not have the role that needed to be removed, then it is
     * ignored for that Role.
     * </p>
     *
     * @param user The user whose role is to be removed.
     * @param role The Authorization role to remove from the user.
     * @throws IllegalArgumentException if user or role is null.
     * @throws AuthorizationPersistenceException if a problem occurs while accessing Authorization persistence.
     */
    public void removeRoleForUser(User user, SecurityRole role) throws AuthorizationPersistenceException {
        Utils.checkNull(user, "user");
        Utils.checkNull(role, "role");

        com.topcoder.security.authorization.Principal principal = toAuthorizationPrincipal(user);
        createIfNotExist(principal);
        authorizationPersistence.removeRoleForPrincipal(principal, role);
    }

    /**
     * <p>
     * Removes the given Authorization Roles for the specified User(s). The User(s) will no longer be assigned any
     * of the given Roles after this method call. If the user initially did not have the role that needed to be
     * removed, then it is ignored for that Role.
     * </p>
     *
     *
     * @param users The users whose roles are to be removed.
     * @param roles The roles to be removed.
     * @throws IllegalArgumentException if users or roles are null or contain null elements.
     * @throws AuthorizationPersistenceException if a problem occurs while accessing Authorization persistence.
     */
    public void removeRolesForUsers(User[] users, SecurityRole[] roles) throws AuthorizationPersistenceException {
        Utils.checkNull(users, "users");
        checkRoleArray(roles, "roles");

        // get the principals
        com.topcoder.security.authorization.Principal[] principals = toPrincipalArray(users);
        // for each principal
        for (int i = 0; i < principals.length; i++) {
            // check if principal exists and if not, add it
            createIfNotExist(principals[i]);

            // for each role
            for (int j = 0; j < roles.length; j++) {
                // add role to principal
                authorizationPersistence.removeRoleForPrincipal(principals[i], roles[j]);
            }
        }
    }

    /**
     * <p>
     * Removes all Roles for the provided User. After this method call, the User will no longer have any
     * Authorization Roles.
     * </p>
     *
     * @param user The user whose roles are to be cleared.
     * @throws IllegalArgumentException if the user is null.
     * @throws AuthorizationPersistenceException if a problem occurs while accessing Authorization persistence.
     */
    public void clearRolesForUser(User user) throws AuthorizationPersistenceException {
        Utils.checkNull(user, "user");
        com.topcoder.security.authorization.Principal principal = toAuthorizationPrincipal(user);
        // get all user roles
        Collection roles = authorizationPersistence.getRolesForPrincipal(principal);
        // and remove them
        for (Iterator it = roles.iterator(); it.hasNext();) {
            SecurityRole role = (SecurityRole) it.next();
            authorizationPersistence.removeRoleForPrincipal(principal, role);
        }
    }

    /**
     * <p>
     * Removes all Roles for the provided Users. After this method call, the User will no longer have any
     * Authorization Roles.
     * </p>
     *
     * @param users The users whose roles are to be removed.
     * @throws IllegalArgumentException if users array is null or contains null elements.
     * @throws AuthorizationPersistenceException if a problem occurs while accessing Authorization persistence.
     */
    public void clearRolesForUsers(User[] users) throws AuthorizationPersistenceException {
        Utils.checkNull(users, "users");
        com.topcoder.security.authorization.Principal[] principals = toPrincipalArray(users);

        // for each principal
        for (int i = 0; i < principals.length; i++) {
            // get all user roles
            Collection roles = authorizationPersistence.getRolesForPrincipal(principals[i]);
            // and remove them
            for (Iterator it = roles.iterator(); it.hasNext();) {
                SecurityRole role = (SecurityRole) it.next();
                authorizationPersistence.removeRoleForPrincipal(principals[i], role);
            }
        }
    }

    /**
     * <p>
     * Converts given array of users to authorization principals.
     * </p>
     *
     * @param users the <code>User</code> array to convert.
     * @return the array of principals of the same length as the input <code>user</code> array.
     * @throws IllegalArgumentException if any object in array is <code>null</code>.
     */
    private com.topcoder.security.authorization.Principal[] toPrincipalArray(User[] users) {
        com.topcoder.security.authorization.Principal[] principals =
            new com.topcoder.security.authorization.Principal[users.length];
        // convert to principals array and check if not null
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) {
                throw new IllegalArgumentException("Null element in users array at index: " + i);
            }

            principals[i] = toAuthorizationPrincipal(users[i]);
        }
        return principals;
    }

    /**
     * <p>
     * Provides a list of all the Authorization Roles that have been assigned to the provided user.
     * </p>
     *
     * @return An array of Security Roles that have been assigned to the specified user.
     * @param user The user whose roles are to be listed.
     * @throws IllegalArgumentException if the user is null.
     * @throws AuthorizationPersistenceException if a problem occurs while accessing Authorization persistence.
     */
    public SecurityRole[] listRolesForUser(User user) throws AuthorizationPersistenceException {
        Utils.checkNull(user, "user");
        Principal principal = toAuthorizationPrincipal(user);
        createIfNotExist(principal);
        return (SecurityRole[]) new ArrayList(authorizationPersistence.getRolesForPrincipal(principal))
                .toArray(new SecurityRole[0]);
    }

    /**
     * <p>
     * Creates a datastore entry for each of the given Users. An id is automatically generated by the DAO and
     * assigned to the user. The User is also considered to have been created by the specified username. While in
     * batch mode, the operation can be done atomically, or separately. If done atomically, then a failure at any
     * one of the specified entries will mean that the entire batch will be rolled back. Otherwise, only the user
     * where a failure occurred will be rolled back.
     * </p>
     *
     * @param users The users to create.
     * @param username The username of the one responsible for creating the users.
     * @param atomicBatchMode While in batch mode, the operation can be done atomically, or separately. If done
     *        atomically, then a failure at any one of the specified entries will mean that the entire batch will
     *        be rolled back. Otherwise, only the user where a failure occurred will be rolled back.
     * @throws IllegalArgumentException if users is null, or username is null, or username is an empty String.
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     * @throws BatchUserDAOException if a problem occurs with multiple entities while processing them in batch
     *         mode.
     */
    public void createUsers(User[] users, String username, boolean atomicBatchMode) throws UserDAOException,
            BatchUserDAOException {

        batchOperation(users, username, atomicBatchMode, CREATE_OP);
    }

    /**
     * </p>
     * It executes the given operation in the batch mode. It means, that depending of the
     * <code>atomicBatchMode</code> flag value, all operations are rolled back or only the
     * failure one.
     * </p>
     *
     * @param users the users to process.
     * @param username the name of the user which call this operation.
     * @param atomicBatchMode flag indicating if all rows must be processed.
     * @param operation the operation to do.
     * @throws UserDAOException if persistence error occrs.
     * @throws BatchUserDAOException if batch persistence error occurs.
     */
    private void batchOperation(User[] users, String username, boolean atomicBatchMode, int operation)
            throws UserDAOException, BatchUserDAOException {

        Throwable[] causes = null;
        boolean wasError = false;
        try {
            if (operation == CREATE_OP) {
                userDao.createUsers(users, username, atomicBatchMode);
            } else if (operation == UPDATE_OP) {
                userDao.updateUsers(users, username, atomicBatchMode);
            } else if (operation == DELETE_OP) {
                userDao.deleteUsers(users, atomicBatchMode);
            }
        } catch (BatchUserDAOException ex) {
            // if in atomic mode - the just rethrow the exception since nothing to add
            if (atomicBatchMode) {
                throw ex;
            }
            causes = ex.getCauses();
            wasError = true;
        }

        if (causes == null) {
            causes = new Throwable[users.length];
        }

        for (int i = 0; i < users.length; i++) {
            // if there were no error for the user
            if (causes[i] == null) {
                try {
                    Principal principal = toAuthorizationPrincipal(users[i]);
                    if (operation == CREATE_OP) {
                        // add it to persistence
                        authorizationPersistence.addPrincipal(principal);
                    } else if (operation == UPDATE_OP) {
                        authorizationPersistence.updatePrincipal(principal);
                    } else if (operation == DELETE_OP) {
                        authorizationPersistence.removePrincipal(principal);
                    }

                } catch (AuthorizationPersistenceException ex) {
                    causes[i] = ex;
                    if (atomicBatchMode) {
                        throw new BatchUserDAOException("Error occurs during batch operation.", causes, users);
                    }

                    wasError = true;
                }
            }
        }

        if (wasError) {
            throw new BatchUserDAOException("An error occurs during batch operation.", causes, users);
        }
    }

    /**
     * <p>
     * Retrieves the users with the specified ids from the datastore.
     * </p>
     * <p>
     * Note: The size of the returned array my be different that the <code>ids</code> array, in case, any of
     * <code>is</code> not exist in database.
     * </p>
     *
     * @return the array of user with specified id.
     * @param ids The ids of the Users to retrieve.
     * @throws IllegalArgumentException if the ids is null.
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     */
    public User[] retrieveUsers(long[] ids) throws UserDAOException {
        return userDao.retrieveUsers(ids);
    }

    /**
     * <p>
     * Updates the given Users in the data store. The companies are considered to have been modified by the
     * specified username. While in batch mode, the operation can be done atomically, or separately. If done
     * atomically, then a failure at any one of the specified entries will mean that the entire batch will be
     * rolled back. Otherwise, only the user where a failure occurred will be rolled back.
     * </p>
     *
     * @param users The Users to update.
     * @param username The username of the one responsible for modifying the users.
     * @param atomicBatchMode While in batch mode, the operation can be done atomically, or separately. If done
     *        atomically, then a failure at any one of the specified entries will mean that the entire batch will
     *        be rolled back. Otherwise, only the user where a failure occurred will be rolled back.
     * @throws IllegalArgumentException if the users is null, or the username is null or an empty String.
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     * @throws BatchUserDAOException if a problem occurs with multiple entities while processing them in batch
     *         mode.
     */
    public void updateUsers(User[] users, String username, boolean atomicBatchMode) throws UserDAOException,
        BatchUserDAOException {

        batchOperation(users, username, atomicBatchMode, UPDATE_OP);
    }

    /**
     * <p>
     * Deletes the specified Users from the data store. While in batch mode, the operation can be done atomically,
     * or separately. If done atomically, then a failure at any one of the specified entries will mean that the
     * entire batch will be rolled back. Otherwise, only the user where a failure occurred will be rolled back.
     * </p>
     *
     * @param users The users to delete.
     * @param atomicBatchMode While in batch mode, the operation can be done atomically, or separately. If done
     *        atomically, then a failure at any one of the specified entries will mean that the entire batch will
     *        be rolled back. Otherwise, only the user where a failure occurred will be rolled back.
     * @throws IllegalArgumentException if users is null.
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     * @throws BatchUserDAOException if a problem occurs with multiple entities while processing them in batch
     *         mode.
     */
    public void deleteUsers(User[] users, boolean atomicBatchMode) throws UserDAOException, BatchUserDAOException {
        batchOperation(users, null, atomicBatchMode, DELETE_OP);
    }

    /**
     * <p>
     * This is a convenience method that is provided to convert a Time Tracker User into an authorization Principal
     * It is provided so that other operations that involve the Authorization component can be conveniently done
     * without having to deal with the User identifiers and names.
     * </p>
     * </p>
     * <p>
     * The returned principal will have an id equal to that of the user, and name equal to the User's username.
     * </p>
     *
     * @return the authentication principa object.
     * @param user This is a convenience method that is provided to convert a Time Tracker User into an
     *        authorization Principal It is provided so that other operations that involve the Authorization
     *        component can be conveniently done without having to deal with the User identifiers and names.
     *        </p>
     *        <p>
     *        The returned principal will have an id equal to that of the user, and name equal to the User's
     *        username.
     * @throws IllegalArgumentException if user is null.
     */
    public com.topcoder.security.authenticationfactory.Principal convertUserToPrincipal(User user) {
        Utils.checkNull(user, "user");

        return createPrincipal(user.getId(), user.getUsername(), user.getPassword());
    }

    /**
     * <p>
     * This is a convenience method that is provided to convert a Time Tracker User into an authorization Principal
     * It is provided so that other operations that involve the Authorization component can be conveniently done
     * without having to deal with the User identifiers and names.
     * </p>
     * </p>
     * <p>
     * The returned principal will have an id equal to that of the user, and name equal to the User's username.
     * </p>
     *
     * @return the authorization principal.
     * @param id the id of the principal.
     * @param username the name of the principal.
     * @param password the password of the principal.
     */
    private com.topcoder.security.authenticationfactory.Principal createPrincipal(long id, String username,
            String password) {

        com.topcoder.security.authenticationfactory.Principal principal =
            new com.topcoder.security.authenticationfactory.Principal(new Long(id));
        principal.addMapping(DbUserAuthenticator.USERNAME_KEY, username);
        principal.addMapping(DbUserAuthenticator.PASSWORD_KEY, password);

        return principal;
    }

    /**
     * <p>
     * This is a convenience method that resolves an Authorization Principal to an actual Time Tracker User object.
     * This will make dealing with the Authorization component a bit simpler by easily allowing the User to be
     * retrieved if necessary. If the user not exists, <code>null</code> will be returned.
     * </p>
     *
     * @return The principal to convert.
     * @param principal The Principal to convert to a Time Tracker User, or <code>null</code> if user not exist.
     * @throws UserDAOException if error occrs during database operation.
     * @throws IllegalArgumentException if the principal is null.
     */
    public User convertPrincipalToUser(com.topcoder.security.authenticationfactory.Principal principal)
        throws UserDAOException {

        Utils.checkNull(principal, "principal");
        return userDao.retrieveUser(((Long) principal.getId()).longValue());
    }
}
