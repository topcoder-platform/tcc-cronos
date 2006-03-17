/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.security.authenticationfactory.AuthenticateException;
import com.topcoder.security.authenticationfactory.Response;
import com.topcoder.security.authorization.AuthorizationPersistence;
import com.topcoder.security.authorization.AuthorizationPersistenceException;
import com.topcoder.security.authorization.SecurityRole;
import com.topcoder.security.authorization.persistence.GeneralSecurityRole;
import com.topcoder.security.authorization.persistence.SQLAuthorizationPersistence;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;


/**
 * <p>
 * This is the central class of the Time Tracker User component. It provides the ability to import
 * users from various user stores into a centralized persistent store, from which those imported
 * users can be authenticated.
 * </p>
 *
 * <p>
 * When users are imported, an internal ID is generated (using the ID Generator component) and the
 * original user name is replicated locally for performance. Also, this class provides
 * authentication of users with a provided password using the UserStore.authenticate method. This
 * supporting component can be backed by a database or other persistence mechanism (such as LDAP or
 * Active Directory.)
 * </p>
 *
 * <p>
 * The class uses the Authorization component to store and retrieve users (as Principals) and their
 * Roles. There are some pre-defined roles that are required to be supported by the authorization
 * database. Since there are no defined permissions for this component, this class provides access
 * to the Authorization persistence mechanism (getAuthPersistence method), so the Time Tracker
 * application can define permissions on its managed resources and apply them for imported users
 * based on their assigned roles.
 * </p>
 *
 * <p>
 * For the Time Tracker application, users can only have one assigned role, so this class provides a
 * simplified API for managing roles than the Authorization component (setUserRole, getUserRole
 * methods).
 * </p>
 *
 * <p>
 * Database logic is separated from business logic of the component. UserPersistence and
 * AuthorizationPersistence instances are used to store and load user roles and imported users.
 * These persistence layers are configurable and pluggable so they can be backed by any database
 * system or other data source such as LDAP or Active Directory.
 * </p>
 *
 * <p>
 * See the constructors for more details on how to configure this class.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 * @see #UserManager()
 * @see #UserManager(UserPersistence, AuthorizationPersistence, UserStoreManager)
 */
public class UserManager {

    /**
     * The configuration namespace that is used to configure this class in the default constructor.
     * It is public so that calling clients can set up the configuration before construction..
     */
    public static final String CONFIG_NAMESPACE = "com.topcoder.timetracker.user";


    /**
     * This security role constant represents the "Super Administrator" Role. It must be defined in
     * whatever Authorization persistence is used by this component.
     */
    public static final SecurityRole SUPER_ADMIN = new GeneralSecurityRole(1, "Super Administrator");


    /**
     * This security role constant represents the "Human Resource" Role. It must be defined in
     * whatever Authorization persistence is used by this component.
     */
    public static final SecurityRole HUMAN_RESOURCE = new GeneralSecurityRole(2, "Human Resource");


    /**
     * This security role constant represents the "Account Manager" Role. It must be defined in
     * whatever Authorization persistence is used by this component.
     */
    public static final SecurityRole ACCOUNT_MANAGER = new GeneralSecurityRole(3, "Account Manager");


    /**
     * This security role constant represents the "Project Manager" Role. It must be defined in
     * whatever Authorization persistence is used by this component.
     */
    public static final SecurityRole PROJECT_MANAGER = new GeneralSecurityRole(4, "Project Manager");


    /**
     * This security role constant represents the "Internal Employee" Role. It must be defined in
     * whatever Authorization persistence is used by this component.
     */
    public static final SecurityRole EMPLOYEE = new GeneralSecurityRole(5, "Employee");


    /**
     * This security role constant represents the "Contractor" Role. It must be defined in whatever
     * Authorization persistence is used by this component.
     */
    public static final SecurityRole CONTRACTOR = new GeneralSecurityRole(6, "Contractor");


    /**
     * The id generator name used for generating unique IDs for imported users.
     */
    private static final String ID_GENERATOR_NAME = "TimeTrackerUser";


    /**
     * The AuthorizationPersistence instance used to retrieve and store Principals and user roles.
     * It is set by constructors and retrieved by getAuthPersistence method. Will never be null.
     */
    private final AuthorizationPersistence authPersistence;


    /**
     * The UserPersistence instance used to import and retrieve imported users. It is set in the
     * constructor and used by the importUser, removeUser methods. Note that the getUser method does
     * not use this instance; all the users are pre-loaded into the 'users' map, and are retrieved
     * from there throughout this class.
     */
    private final UserPersistence userPersistence;


    /**
     * The UserStoreManager instance used to maintain, in memory, a list of available user stores.
     * This instance is set by the constructors, and used by the importUser and authenticate
     * methods. It can be retrieved by the getUserStores() method.
     */
    private final UserStoreManager storeManager;


    /**
     * The IDGenerator instance used to generate unique IDs for imported users. Instantiated in the
     * constructors using the ID_GENERATOR_NAME. It is used in the importUser method only and cannot
     * be retrieved by any public method.
     */
    private final IDGenerator idGenerator;


    /**
     * This is a cache of all imported users. Each key is a username String, and each value is a
     * User instance which stores the unique ID, username and the name of user store the user was
     * originally imported from. This Map is populated by the constructors to include all
     * previousloy imported users (via the UserPersistence object), and is modified by the
     * importUser and removeUser metohds. Users can be retrieved from this map by the getUser
     * method.
     */
    private final Map users = new Hashtable();


    /**
     * <p>
     * Creates a new UserManager instance using objects constructed with the default configuration
     * namespace (CONFIG_NAMESPACE). The following objects will be constructed here:
     * </p>
     * <ul>
     * <li>An AuthorizationPersistence, using
     * <code>new SQLAuthorizationPersistence(CONFIG_NAMESPACE)</code></li>
     * <li>A UserStoreManager, using <code>new UserStoreManagerImpl(CONFIG_NAMESPACE)</code>
     * </li>
     * <li>A UserPersistence, using <code>new UserPersistenceImpl(CONFIG_NAMESPACE)</code></li>
     * </ul>
     *
     * <p>
     * This implies that the CONFIG_NAMESPACE must be defined before calling this constructor, to
     * contain the proper configuration parameters for a SQLAuthorizationPersistence object, a
     * UserStoreManagerImpl object and a UserPersistenceImpl object. See those classes for more
     * details on the configuration.
     * </p>
     *
     * <p>
     * Also, the namespace "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl" must be
     * defined as appropriate, for the IDGenerator 3.0 component used to generate user ID numbers.
     * </p>
     *
     * <p>
     * In addition, this constructor will load all previously imported users into an internal cache
     * of users, using the UserPersistence implementation above.
     * </p>
     *
     * @throws ConfigurationException if any error occured during initialization. This exception
     *             will wrap the actual exception that occurred, such as SQLException or an
     *             exception from instantiating one of the subcomponents.
     * @see UserPersistenceImpl
     * @see UserStoreManagerImpl
     */
    public UserManager() throws ConfigurationException {

        try {
            // note, this must be defined here, and not refactored, because
            // idGenerator is a final
            this.idGenerator = IDGeneratorFactory.getIDGenerator(ID_GENERATOR_NAME);
        } catch (IDGenerationException e) {
            throw new ConfigurationException("Could not configure IDGenerator.", e);
        }

        try {
            this.authPersistence = new SQLAuthorizationPersistence(CONFIG_NAMESPACE);
        } catch (com.topcoder.security.authorization.ConfigurationException e) {
            throw new ConfigurationException("Could not configure AuthorizationPersistence.", e);
        }
        this.storeManager = new UserStoreManagerImpl(CONFIG_NAMESPACE);
        this.userPersistence = new UserPersistenceImpl(CONFIG_NAMESPACE);

        // preload all the users we know about.
        try {
            populateUsers();
        } catch (PersistenceException e) {
            throw new ConfigurationException("Could not load users.", e);
        }
    }


    /**
     * <p>
     * Creates a new UserManager instance with the provided user persistence, authorization
     * persistence and user store manager implementations. To support the IDGenerator 3.0 component,
     * the namespace "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl" must be defined as
     * appropriate.
     * </p>
     *
     * <p>
     * In addition, this constructor will load all previously imported users into an internal cache
     * of users, using the given UserPersistence implementation.
     * </p>
     *
     * @param userPersistence the UserPersistence instance that will be used to retrieve/store
     *            imported users
     * @param authPersistence the AuthorizationPersistence instance that will be used to
     *            retrieve/store Principals and user roles
     * @param storeManager the UserStoreManager instance that will maintain (in memory) the
     *            supported UserStores
     * @throws NullPointerException if userPersistence, authPersistence or storeManager is null
     * @throws PersistenceException if any error occurred during initialization. This exception will
     *             wrap the actual exception that occurred, such as SQLException or an exception
     *             from instantiating one of the subcomponents.
     */
    public UserManager(UserPersistence userPersistence, AuthorizationPersistence authPersistence,
            UserStoreManager storeManager) throws PersistenceException {
        if (userPersistence == null) {
            throw new NullPointerException("name cannot be null.");
        }
        if (authPersistence == null) {
            throw new NullPointerException("authPersistence cannot be null.");
        }
        if (storeManager == null) {
            throw new NullPointerException("storeManager cannot be null.");
        }


        try {
            // note, this must be defined here, and not refactored, because
            // idGenerator is a final
            this.idGenerator = IDGeneratorFactory.getIDGenerator(ID_GENERATOR_NAME);
        } catch (IDGenerationException e) {
            throw new PersistenceException("Could not configure IDGenerator.", e);
        }
        this.authPersistence = authPersistence;
        this.storeManager = storeManager;
        this.userPersistence = userPersistence;

        // preload all the users we know about.
        populateUsers();
    }


    /**
     * Populate the previously-imported users into the internal cache (users Map) using the
     * userPersistence object.
     *
     * @throws PersistenceException If the users could not be loaded.
     */
    private void populateUsers() throws PersistenceException {
        Collection userList = userPersistence.getUsers();
        for (Iterator i = userList.iterator(); i.hasNext();) {
            User user = (User) i.next();
            users.put(user.getName(), user);
        }
    }


    /**
     * <p>
     * Returns the AuthorizationPersistence as defined in one of the constructors. Client programs
     * may use this instance for more fine-grained control over the Authorization Persistence of
     * Users in the system.
     * </p>
     *
     * @return authPersistence instance as defined in one of the constructors.
     */
    public AuthorizationPersistence getAuthPersistence() {
        return this.authPersistence;
    }


    /**
     * <p>
     * Returns a userManager which maintains (in memory) the supported UserStore implementations, as
     * defined in one of the constructors. Client programs may use this instance for more
     * fine-grained control over the set of UserStores that are managed, or to manipulate the users
     * via one of the UserStores directly.
     * </p>
     *
     * @return supported user stores in the guise of a UserStoreManager instance
     */
    public UserStoreManager getUserStores() {
        return this.storeManager;
    }


    /**
     * <p>
     * Imports the given user from the given user store. The method does the following:
     * <ul>
     * <li>If the given user name has already been imported, return false.</li>
     * <li>Generate a unique (numeric) ID for this user using the IdGenerator component</li>
     * <li>Create User instance with generated ID, name and userStore</li>
     * <li>Add the user via the userPersistence object defined in the constructor</li>
     * <li>Add the user via the authPersistence (as a principal) object defined in the constructor
     * </li>
     * <li>Add the user to the internal cache of users</li>
     * </ul>
     * </p>
     *
     * @return true if user iswas imported, false if user already existed according to the
     *         UserPersistence
     * @param name unique name of the user to import
     * @param userStore name of user store from which to import this user
     * @throws PersistenceException if any error occurred during user persistence; this will wrap
     *             the original exception (e.g., SQLException, AuthorizationPersistenceException)
     * @throws UnknownUserStoreException if the requested userStore is not present within the
     *             UserStoreManager defined in the constructor
     * @throws UnknownUserException if the requested user doesn't exist according to the requested
     *             userStore (as managed by the UserStoreManager)
     * @throws NullPointerException if name or userStore is null
     * @throws IllegalArgumentException if name or userStore is the empty String
     */
    public boolean importUser(String name, String userStore) throws UnknownUserException,
            UnknownUserStoreException, PersistenceException {
        if (name == null) {
            throw new NullPointerException("name cannot be null.");
        }
        if (name.length() == 0) {
            throw new IllegalArgumentException("name cannot be empty.");
        }
        if (userStore == null) {
            throw new NullPointerException("userStore cannot be null.");
        }
        if (userStore.length() == 0) {
            throw new IllegalArgumentException("userStore cannot be empty.");
        }

        // user was already imported.
        if (getUser(name) != null) {
            return false;
        }

        // throws UnknownUserStoreException for us
        UserStore store = storeManager.getUserStore(userStore);
        // make sure user is in store
        if (!store.contains(name)) {
            throw new UnknownUserException("User " + name + " not found in user store " + userStore + ".");
        }

        // generate ID
        long id = 0;
        try {
            id = idGenerator.getNextID();
        } catch (IDGenerationException e) {
            throw new PersistenceException("Could not generate ID.", e);
        }

        // Generate a new User object.
        // cast the ID to int so it is compatable with the rest of the Time Tracker project
        User user = new User((int) id, name, userStore);

        // add user to the User store (this is probably the database table "Users")
        userPersistence.addUser(user);

        try {
            // add the user as a principal
            authPersistence.addPrincipal(user);
        } catch (AuthorizationPersistenceException e) {
            throw new PersistenceException("Could not add principal.", e);
        }

        // add it to the cache.
        users.put(name, user);
        return true;
    }


    /**
     * Returns all usernames that have been imported into the UserPersistence store, in this session
     * and all previous sessions.
     *
     * @return an unmodifiable Collection all imported usernames as Strings
     */
    public Collection getNames() {

        return Collections.unmodifiableSet(users.keySet());
    }


    /**
     * <p>
     * Returns a User instance for given username from the internal cache of imported Users. If no
     * such user has been imported, null is returned.
     * </p>
     *
     * @param name username of user to retrieve
     * @return User instance for the given username. If no such user exists, null is returned.
     * @throws NullPointerException if name is null
     * @throws IllegalArgumentException if name is the empty String
     */
    public User getUser(String name) {
        if (name == null) {
            throw new NullPointerException("name cannot be null.");
        }
        if (name.length() == 0) {
            throw new IllegalArgumentException("name cannot be empty.");
        }

        return (User) users.get(name);
    }


    /**
     * <p>
     * Removes the user with the given username from this UserManager. It is actually removed from
     * three places (if it existed at all): the internal cache of imported users, the
     * userPersistence implementation defined in the constructor, and the AuthorizationPersistence
     * implementation also defined in the onstructor. The latter two will remove the user from their
     * respective underlying storage (e.g., a database.)
     * </p>
     *
     * @param name username of te user to remove
     * @return true if the user was removed, false if the user didnt't exist in the first place
     * @throws NullPointerException if name is null
     * @throws IllegalArgumentException if name is the empty String
     * @throws PersistenceException if any error occurred while removing from any of the locations
     *             mentioned above. This exception will wrap the underlying exception (e.g.,
     *             SQLException)
     */
    public boolean removeUser(String name) throws PersistenceException {
        if (name == null) {
            throw new NullPointerException("name cannot be null.");
        }
        if (name.length() == 0) {
            throw new IllegalArgumentException("name cannot be empty.");
        }

        // user was never imported.
        User user = getUser(name);
        if (user == null) {
            return false;
        }

        try {
            authPersistence.removePrincipal(user);
        } catch (AuthorizationPersistenceException e) {
            throw new PersistenceException("Could not remove principal.", e);
        }

        userPersistence.removeUser(user);
        users.remove(name);
        return true;
    }


    /**
     * <p>
     * Returns the first defined security role for this given user, as reported by the
     * AuthorizationPersistence implementation defined in the onstructor. This method is actually a
     * facade to the call
     *
     * <pre>
     * authPersistence.getRolesForPrincipal(getUser(name))
     * </pre>
     *
     * and returns the first element from the resulting list, because a user can only have one role
     * in the Time Tracker Application. This method returns null if no role was assigned for the
     * given user.
     * </p>
     *
     * <p>
     * Note that even though there are Security roles defined in this class as final static entries,
     * the SecurityRole returned here may not in fact be one of them. Also, the object returned may
     * be "equal" but not "==" to one of those predefined roles.
     * </p>
     *
     * @param name the username of user whose role we are interested in.
     * @return security role for the given user.
     * @throws NullPointerException if name is null
     * @throws IllegalArgumentException if name is the empty String
     * @throws PersistenceException if any error occurs while getting the role from the
     *             Authorization Persistence; it will wrap the underlying exception.
     * @throws UnknownUserException if the requested user was never imported.
     */
    public SecurityRole getUserRole(String name) throws PersistenceException, UnknownUserException {
        if (name == null) {
            throw new NullPointerException("name cannot be null.");
        }
        if (name.length() == 0) {
            throw new IllegalArgumentException("name cannot be empty.");
        }

        // find user
        User user = getUser(name);
        if (user == null) {
            throw new UnknownUserException("User " + name + " was never imported.");
        }

        // get all roles from the Authentication component.
        try {
            Collection roles = authPersistence.getRolesForPrincipal(user);

            // this is the first role we find (if any)
            SecurityRole role = null;
            if (roles != null) {
                Iterator iterator = roles.iterator();
                if (iterator.hasNext()) {
                    // only get the role if the iterator says we can.
                    role = (SecurityRole) iterator.next();
                }
            }

            // return the role (possibly none, if there were none)
            return role;
        } catch (AuthorizationPersistenceException e) {
            throw new PersistenceException("Could not retrieve roles.", e);
        }
    }


    /**
     * <p>
     * Assigns a security role for the given user using the Authorization Persistence implementation
     * defined in the constructor. This method is actually a facade to
     *
     * <pre>
     * authPersistence.addRoleForPrincipal(getUser(name), role)
     * </pre>
     *
     * If this user had <b>any </b> existing roles, they are removed before adding this role. This
     * preserves the contract that a User can only have one role.
     * </p>
     *
     * @param name username of the user to whom we are setting a role.
     * @param role the security role to set for given user.
     * @throws NullPointerException if name or role is null
     * @throws IllegalArgumentException if name is the empty String
     * @throws PersistenceException if any error occurs while removing the old roles, or adding the
     *             new role. This exception will wrap the underlying exception
     * @throws UnknownUserException if the requested user was never imported.
     */
    public void setUserRole(String name, SecurityRole role) throws PersistenceException,
            UnknownUserException {
        if (name == null) {
            throw new NullPointerException("name cannot be null.");
        }
        if (role == null) {
            throw new NullPointerException("role cannot be null.");
        }
        if (name.length() == 0) {
            throw new IllegalArgumentException("name cannot be empty.");
        }

        // find user
        User user = getUser(name);
        if (user == null) {
            throw new UnknownUserException("User " + name + " was never imported.");
        }

        try {
            // remove all old roles, if they existed.
            Collection roles = authPersistence.getRolesForPrincipal(user);
            if (roles != null) {
                for (Iterator i = roles.iterator(); i.hasNext();) {
                    SecurityRole oldRole = (SecurityRole) i.next();
                    authPersistence.removeRoleForPrincipal(user, oldRole);
                }
            }
        } catch (AuthorizationPersistenceException e) {
            throw new PersistenceException("Could not remove old roles from user " + name + ".", e);
        }

        try {
            // add the one new role
            authPersistence.addRoleForPrincipal(user, role);
        } catch (AuthorizationPersistenceException e) {
            throw new PersistenceException("Could not add new role to user " + name + ".", e);
        }
    }


    /**
     * <p>
     * Authenticate the user with the given username against the provided password. Note that the
     * password has Object type, because it can have different structures for various user stores
     * (for example, an encrypted password may be presented as array of bytes). This method is a
     * facade to the UserStore.authenticate method; the userStore is retrieved from the
     * UserStoreManager defined in the constructor and the given User.
     * </p>
     *
     * @return a Response instance (from Authentication Factory component) with the 'successful
     *         flag' set to true if authentication was successful, false otherwise. Nothing else in
     *         the Response object is required to be filled in.
     * @param name username of user to authenticate
     * @param password the password of the user to authenticate with
     * @throws NullPointerException if name or password is null
     * @throws IllegalArgumentException if name is the empty String
     * @throws AuthenticateException if any error occurs during authentication, which will wrap the
     *             underlying exception (e.g., SQLException)
     * @throws UnknownUserStoreException if the given user contains a user store name that is not
     *             recognized by the userManager defined in the constructor.
     * @see UserStore#authenticate(String, Object)
     */
    public Response authenticate(String name, Object password) throws UnknownUserStoreException,
            AuthenticateException {
        if (name == null) {
            throw new NullPointerException("name cannot be null.");
        }
        if (name.length() == 0) {
            throw new IllegalArgumentException("name cannot be empty.");
        }
        if (password == null) {
            throw new NullPointerException("password cannot be null.");
        }

        // find user
        User user = (User) users.get(name);
        if (user == null) {
            throw new AuthenticateException("User " + name + " was never imported.");
        }

        // will throw the UnknownUserStoreException for us
        UserStore store = storeManager.getUserStore(user.getUserStoreName());

        // will throw AuthenticateException for us (sometimes)
        return store.authenticate(name, password);
    }
}