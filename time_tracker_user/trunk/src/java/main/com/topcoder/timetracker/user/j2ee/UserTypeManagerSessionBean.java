/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.j2ee;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.user.ConfigurationException;
import com.topcoder.timetracker.user.DataAccessException;
import com.topcoder.timetracker.user.UserManagerFactory;
import com.topcoder.timetracker.user.UserType;
import com.topcoder.timetracker.user.UserTypeManager;

/**
 * <p>
 * This is a Stateless SessionBean that is used to provide business services to manage UserTypes within the Time
 * Tracker Application. It contains the same methods as UserTypeManager, and delegates to an instance of
 * UserTypeManager.
 * </p>
 * <p>
 * The instance of UserTypeManager to use is retrieved from ManagerFactory.
 * </p>
 * <p>
 * Thread Safety: The UserTypeManager interface implementations are required to be thread-safe, and so this
 * stateless session bean is thread-safe also.
 * </p>
 *
 * @author George1, enefem21
 * @version 3.2.1
 * @since 3.2.1
 */
public class UserTypeManagerSessionBean implements UserTypeManager, SessionBean {

    /** Serial version UID. */
    private static final long serialVersionUID = 5581147175638625164L;

    /**
     * <p>
     * This is the instance of SessionContext that was provided by the EJB container. It is stored and made
     * available to subclasses. It is also used when performing a rollback in the case that an exception occurred.
     * </p>
     * <p>
     * Initial Value: Null
     * </p>
     * <p>
     * Accessed In: getSessionContext();
     * </p>
     * <p>
     * Modified In: setSessionContext
     * </p>
     * <p>
     * Utilized In: All business methods of this class
     * </p>
     * <p>
     * Valid Values: sessionContext objects (possibly null)
     * </p>
     */
    private SessionContext sessionContext;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public UserTypeManagerSessionBean() {
        // nothing to do
    }

    /**
     * <p>
     * Defines a user type to be recognized within the persistent store managed by this utility. A unique user type
     * id will automatically be generated and assigned to the user type.
     * </p>
     *
     * @param userType
     *            The type for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if type is null or has id != 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public void createUserType(UserType userType) throws DataAccessException {
        try {
            getUserTypeManager().createUserType(userType);
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
     * Modifies the persistent store so that it now reflects the data in the provided UserType parameter.
     * </p>
     * <p>
     * The implementation will set the UserType' modification details to the current date. These modification
     * details will also reflect in the persistent store. The modification user is the responsibility of the
     * calling application.
     * </p>
     * <p>
     * The given entity should have an id specified (id &gt; 0), or else IllegalArgumentException will be thrown.
     * </p>
     *
     * @param userType
     *            The type for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if type is null or has id <=0
     * @throws com.topcoder.timetracker.user.UnrecognizedEntityException
     *             if a type with the provided id was not found in the data store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public void updateUserType(UserType userType) throws DataAccessException {
        try {
            getUserTypeManager().updateUserType(userType);
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
     * Modifies the persistent store so that it no longer contains data on the user type with the specified id.
     * </p>
     * <p>
     * The given entity should have an id specified (id &gt; 0), or else IllegalArgumentException will be thrown.
     * </p>
     *
     * @param userTypeId
     *            The id of the type for which the operation should be performed.
     * @throws com.topcoder.timetracker.user.UnrecognizedEntityException
     *             if a type with the provided id was not found in the data store.
     * @throws IllegalArgumentException
     *             if typeId <= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public void removeUserType(long userTypeId) throws DataAccessException {
        try {
            getUserTypeManager().removeUserType(userTypeId);
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
     * Retrieves a UserType object that reflects the data in the persistent store on the Time Tracker UserType with
     * the specified userTypeId.
     * </p>
     *
     * @param userTypeId
     *            The id of the type to retrieve.
     * @return The type with specified id.
     * @throws IllegalArgumentException
     *             if userTypeId <= 0
     * @throws com.topcoder.timetracker.user.UnrecognizedEntityException
     *             if a type with the provided id was not found in the data store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public UserType getUserType(long userTypeId) throws DataAccessException {
        try {
            return getUserTypeManager().getUserType(userTypeId);
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
     * Searches the persistent store for any user types that satisfy the criteria that was specified in the
     * provided search filter. The provided filter should be created using either the filters that are created
     * using the UserTypeFilterFactory or a composite Search Filters (such as AndFilter, OrFilter and NotFilter
     * from Search Builder component) that combines the filters created using UserTypeFilterFactory.
     * </p>
     *
     * @param filter
     *            The filter used to search for types.
     * @return The types satisfying the conditions in the search filter.
     * @throws IllegalArgumentException
     *             if filter is null.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public UserType[] searchUserTypes(Filter filter) throws DataAccessException {
        try {
            return getUserTypeManager().searchUserTypes(filter);
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
     * This is a batch version of the createUserType method.
     * </p>
     * <p>
     * The creation and modification user is the responsibility of the calling application.
     * </p>
     *
     * @param userTypes
     *            An array of user types for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if userTypes is null or contains null values or has ids != -1.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws com.topcoder.timetracker.user.BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public void addUserTypes(UserType[] userTypes) throws DataAccessException {
        try {
            getUserTypeManager().addUserTypes(userTypes);
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
     * This is a batch version of the updateUserType method.
     * </p>
     * <p>
     * The creation and modification user is the responsibility of the calling application.
     * </p>
     *
     * @param userTypes
     *            An array of user types for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if userTypes is null or contains null values or has id <= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws com.topcoder.timetracker.user.BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public void updateUserTypes(UserType[] userTypes) throws DataAccessException {
        try {
            getUserTypeManager().updateUserTypes(userTypes);
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
     * This is a batch version of the removeUserType method.
     * </p>
     *
     * @param userTypeIds
     *            An array of ids for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if userTypeIds is null or contains values <= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws com.topcoder.timetracker.user.BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public void removeUserTypes(long[] userTypeIds) throws DataAccessException {
        try {
            getUserTypeManager().removeUserTypes(userTypeIds);
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
     * This is a batch version of the getUserType method.
     * </p>
     *
     * @param userTypeIds
     *            An array of userTypeIds for which user type should be retrieved.
     * @return The UserTypes corresponding to the provided ids.
     * @throws IllegalArgumentException
     *             if userTypeIds is null or contains values <= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws com.topcoder.timetracker.user.BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public UserType[] getUserTypes(long[] userTypeIds) throws DataAccessException {
        try {
            return getUserTypeManager().getUserTypes(userTypeIds);
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
     * Retrieves all the UserTypes that are currently in the persistent store.
     * </p>
     *
     * @return An array of user types retrieved from the persistent store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public UserType[] getAllUserTypes() throws DataAccessException {
        try {
            return getUserTypeManager().getAllUserTypes();
        } catch (DataAccessException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * ejbCreate method. Empty implementation.
     * </p>
     */
    public void ejbCreate() {
        // nothing to do
    }

    /**
     * <p>
     * This method has simply been included to complete the SessionBean interface. It has an empty method body.
     * </p>
     */
    public void ejbActivate() {
        // nothing to do
    }

    /**
     * <p>
     * This method has simply been included to complete the SessionBean interface. It has an empty method body.
     * </p>
     */
    public void ejbPassivate() {
        // nothing to do
    }

    /**
     * <p>
     * This method has simply been included to complete the SessionBean interface. It has an empty method body.
     * </p>
     */
    public void ejbRemove() {
        // nothing to do
    }

    /**
     * <p>
     * Sets the SessionContext to use for this session. This method is included to comply with the SessionBean
     * interface.
     * </p>
     *
     * @param ctx
     *            The SessionContext to use
     */
    public void setSessionContext(SessionContext ctx) {
        this.sessionContext = ctx;
    }

    /**
     * <p>
     * Protected method that allows subclasses to access the session context if necessary.
     * </p>
     *
     * @return The session context provided by the EJB container
     */
    protected SessionContext getSessionContext() {
        return sessionContext;
    }

    /**
     * <p>
     * This method gets the current user type manager in the factory.
     * </p>
     *
     * @return the current user type manager in the factory
     *
     * @throws DataAccessException
     *             to wrap the <code>ConfigurationException</code> thrown by <code>UserManagerFactory</code>
     */
    private UserTypeManager getUserTypeManager() throws DataAccessException {
        try {
            return UserManagerFactory.getUserTypeManager();
        } catch (ConfigurationException e) {
            throw new DataAccessException(
                "ConfigurationException occurs when getting the user type manager.", e);
        }
    }
}
