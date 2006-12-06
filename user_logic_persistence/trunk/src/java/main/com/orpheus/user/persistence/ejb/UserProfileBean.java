/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.ejb;

import java.util.Map;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.orpheus.user.persistence.DAOFactory;
import com.orpheus.user.persistence.ObjectInstantiationException;
import com.orpheus.user.persistence.PersistenceException;
import com.orpheus.user.persistence.UserConstants;
import com.orpheus.user.persistence.UserProfileDAO;

/**
 * <p>
 * A stateless session bean that receives requests from client to perform
 * confirmation message persistence operations. It delegates all client requests
 * to the {@link UserProfileDAO} class, which is responsible for the actual
 * persistence operations.
 * </p>
 * <p>
 * <b>Thread-safety:</b><br>
 * This session bean is stateless (immutable) and thread-safe.
 * </p>
 *
 * @author argolite, mpaulse
 * @version 1.0
 * @see UserProfileHomeLocal
 * @see UserProfileHomeRemote
 * @see UserProfileLocal
 * @see UserProfileRemote
 */
public class UserProfileBean implements SessionBean {

    /**
     * <p>
     * The context of the session bean. This field is used to rollback failed
     * transactions.
     * </p>
     * <p>
     * This field is mutable and is set in the setSessionContext(SessionContext)
     * method by the application server container.
     * </p>
     */
    private SessionContext context;

    /**
     * <p>
     * Creates a new <code>UserProfileBean</code> session bean.
     * </p>
     */
    public UserProfileBean() {
        // Empty constructor.
    }

    /**
     * <p>
     * Inserts the given user profile into the persistent store. If the
     * persistent store already contains a user profile with the same identifier
     * as that of the given user profile, a <code>DuplicateEntryException</code>
     * is thrown.
     * </p>
     *
     * @param profile a data transfer object (DTO) representing the user profile
     *        to insert
     * @throws IllegalArgumentException if the user profile DTO is
     *         <code>null</code> or contains an invalid key to object mapping
     * @throws DuplicateEntryException if the persistent store contains a user
     *         profile with the same identifier as that of the given user
     *         profile
     * @throws PersistenceException if inserting the user profile into the
     *         persistent store fails
     */
    public void insertProfile(UserProfileDTO profile) throws PersistenceException {
        try {
            UserProfileDAO dao = getDAO("Failed to insert user profile");
            dao.insertProfile(profile);
        } catch (PersistenceException e) {
            // Tell container to rollback the transaction.
            context.setRollbackOnly();

            throw e;
        }
    }

    /**
     * <p>
     * Updates the given user profile in the persistent store. If the user
     * profile does not exist in the persistent store, an
     * <code>EntryNotFoundException</code> is thrown.
     * </p>
     *
     * @param profile a data transfer object (DTO) representing the user profile
     *        to update
     * @throws IllegalArgumentException if the user profile DTO is
     *         <code>null</code> or contains an invalid key to object mapping
     * @throws EntryNotFoundException if the user profile does not exist in the
     *         persistent store
     * @throws PersistenceException if updating the user profile in the
     *         persistent store fails
     */
    public void updateProfile(UserProfileDTO profile) throws PersistenceException {
        try {
            UserProfileDAO dao = getDAO("Failed to update user profile");
            dao.updateProfile(profile);
        } catch (PersistenceException e) {
            // Tell container to rollback the transaction.
            context.setRollbackOnly();

            throw e;
        }
    }

    /**
     * <p>
     * Retrieves the user profile with the specified identifier from the
     * persistent store. If no such user profile could be found, an
     * <code>EntryNotFoundException</code> is thrown.
     * </p>
     *
     * @param id the identifier of the user profile to retrieve
     * @return a <code>UserProfileDTO</code> representing the user profile
     *         with the specified identifier
     * @throws EntryNotFoundException if no user profile with the specified
     *         identifier exists in the persistent store
     * @throws PersistenceException if retrieving the user profile from the
     *         persistent store fails
     * @see #findProfiles(Map)
     * @see #retrieveAllProfiles()
     */
    public UserProfileDTO retrieveProfile(long id) throws PersistenceException {
        try {
            UserProfileDAO dao = getDAO("Failed to retrieve user profile");
            return dao.retrieveProfile(id);
        } catch (PersistenceException e) {
            // Tell container to rollback the transaction.
            context.setRollbackOnly();

            throw e;
        }
    }

    /**
     * <p>
     * Deletes the user profile with the specified identifier from the
     * persistent store. If no such user profile could be found, an
     * <code>EntryNotFoundException</code> is thrown.
     * </p>
     *
     * @param id the identifier of the user profile to delete
     * @throws EntryNotFoundException if no user profile with the specified
     *         identifier exists in the persistent store
     * @throws PersistenceException if deleting the user profile from the
     *         persistent store fails
     */
    public void deleteProfile(long id) throws PersistenceException {
        try {
            UserProfileDAO dao = getDAO("Failed to delete user profile");
            dao.deleteProfile(id);
        } catch (PersistenceException e) {
            // Tell container to rollback the transaction.
            context.setRollbackOnly();

            throw e;
        }
    }

    /**
     * <p>
     * Retrieves all the user profiles from the persistent store that match the
     * given criteria, and returns them in an array. A user profile must match
     * all the criteria for it to be included in the returned array. If no user
     * profiles match the criteria, an empty array is returned. If the given
     * criteria map is empty, all the user profiles are retrieved.
     * </p>
     * <p>
     * The keys in the criteria map must be one of the user profile property
     * constants defined in the {@link UserConstants} interface (those that are
     * not named <code>XXX_TYPE_NAME</code>). If a key is not equal to one of
     * the property constants, an <code>IllegalArgumentException</code> is
     * thrown. Note that this means that the user's email address, first name or
     * last name cannot be used as search criteria, because they do not
     * correspond to any property constants defined in the
     * <code>UserConstants</code> interface.
     * </p>
     * <p>
     * The values in the criteria map should be <code>String</code>, because
     * that is how this component stores data in its data transfer objects.
     * However, any <code>Object</code> instance compatible with the
     * corresponding persistent store field may be used. The criteria values
     * will be compared to the corresponding persistent store fields, and, if
     * equal, the user profile containing those fields will be included in the
     * returned array. As such, the criteria map values may be <code>null</code>
     * if the persistent store allows for <code>null</code> values in the
     * corresponding fields.
     * </p>
     * <p>
     * Examples on how to use this method are shown in the code snippet below.
     * </p>
     * <pre>
     * // Create the search criteria map.
     * Map criteria = new HashMap();
     *
     * // Find the user profiles of all the active users.
     * criteria.put(UserConstants.CREDENTIALS_IS_ACTIVE, &quot;Y&quot;);
     * UserProfileDTO[] profiles = findProfiles(criteria);
     *
     * // Find the user profiles of all the active players that have &quot;Wire transfer&quot;
     * // set as their payment preference.
     * criteria.put(UserConstants.PLAYER_PAYMENT_PREF, &quot;Wire transfer&quot;);
     * profiles = findProfiles(criteria);
     *
     * // Find the user profiles of all the non-active players and sponsors who live in
     * // Los Angeles, California.
     * criteria.clear();
     * criteria.put(UserConstants.CREDENTIALS_IS_ACTIVE, &quot;N&quot;);
     * criteria.put(UserConstants.ADDRESS_CITY, &quot;Los Angeles&quot;);
     * criteria.put(UserConstants.ADDRESS_STATE, &quot;California&quot;);
     * profiles = findProfiles(criteria);
     *
     * // Find the user profiles of all the approved sponsors in California.
     * criteria.clear();
     * criteria.put(UserConstants.SPONSOR_APPROVED, Sponsor.APPROVED_TRUE);
     * criteria.put(UserConstants.ADDRESS_STATE, &quot;California&quot;);
     *
     * // An empty criteria map retrieves all the user profiles.
     * criteria.clear();
     * profiles = findProfiles(criteria);
     *
     * // Assuming no user profile contains the handle, &quot;joeuser&quot;, the following
     * // will return an empty array.
     * criteria.clear();
     * criteria.put(UserConstants.CREDENTIALS_HANDLE, &quot;joeuser&quot;);
     * profiles = findProfiles(criteria);
     *
     * // Results in an IllegalArgumentException being thrown, because &quot;InvalidCriteria&quot;
     * // does not correspond to a user profile property constant defined in the UserConstants
     * // interface.
     * criteria.put(&quot;InvalidCriteria&quot;, &quot;Some value&quot;);
     * profiles = findProfiles(criteria);
     * </pre>
     *
     * @param criteria the criteria to use when searching for user profiles in
     *        the persistent store to retrieve
     * @return a <code>UserProfile[]</code> array containing all the user
     *         profiles in the persistent store that match the given criteria;
     *         an empty array if there are no user profiles in the persistent
     *         store that match the given criteria; or, if the criteria map is
     *         empty, a <code>UserProfile[]</code> array containing all the
     *         user profiles in the persistent store
     * @throws IllegalArgumentException if the criteria map is <code>null</code>,
     *         if it contains keys that are <code>null</code>, non-<code>String</code>
     *         instances or blank strings, of if the keys are not equal to one
     *         of the property constants defined in the {@link UserConstants}
     *         interface
     * @throws PersistenceException if retrieving the user profile from
     *         the persistent store that match the given criteria fails
     * @see #retrieveProfile(long)
     * @see #retrieveAllProfiles()
     */
    public UserProfileDTO[] findProfiles(Map criteria) throws PersistenceException {
        try {
            UserProfileDAO dao = getDAO("Failed to find user profiles");
            return dao.findProfiles(criteria);
        } catch (PersistenceException e) {
            // Tell container to rollback the transaction.
            context.setRollbackOnly();

            throw e;
        }
    }

    /**
     * <p>
     * Retrieves all the user profiles from the persistent store as an array. If
     * no user profiles exist in the persistent store, an empty array is
     * returned.
     * </p>
     *
     * @return a <code>UserProfileDTO[]</code> array containing all the user
     *         profiles in the persistent store, or an empty array if there are
     *         no user profiles
     * @throws PersistenceException if retrieving all the user profiles from the
     *         persistent store fails
     * @see #retrieveProfile(long)
     * @see #findProfiles(Map)
     */
    public UserProfileDTO[] retrieveAllProfiles() throws PersistenceException {
        try {
            UserProfileDAO dao = getDAO("Failed to retrieve user profiles");
            return dao.retrieveAllProfiles();
        } catch (PersistenceException e) {
            // Tell container to rollback the transaction.
            context.setRollbackOnly();

            throw e;
        }
    }

    /**
     * <p>
     * Called by the application server container to create the session bean.
     * This is method does nothing.
     * </p>
     */
    public void ejbCreate() {
        // Does nothing.
    }

    /**
     * <p>
     * Called by the application server container before it ends the life of the
     * session object. This happens as a result of a client's invoking a remove
     * operation, or when the container decides to terminate the session object
     * after a timeout. This method does nothing.
     * </p>
     */
    public void ejbRemove() {
        // Does nothing.
    }

    /**
     * <p>
     * Called by the application server container when the session bean is
     * activated from its passive state. Since this session bean is stateless,
     * no activation is required. This method does nothing.
     * </p>
     */
    public void ejbActivate() {
        // Does nothing.
    }

    /**
     * <p>
     * Called by the application server container before the session bean enters
     * the passive state. Since this session bean is stateless, no action needs
     * to be taken. This method does nothing.
     * </p>
     */
    public void ejbPassivate() {
        // Does nothing.
    }

    /**
     * <p>
     * Set the context associated with the session bean. The application server
     * container calls this method after the session bean is created.
     * </p>
     *
     * @param context the session context
     */
    public void setSessionContext(SessionContext context) {
        this.context = context;
    }

    /**
     * <p>
     * Gets the <code>UserProfileDAO</code> instance from the
     * {@link DAOFactory}, which is then used to perform confirmation message
     * persistence operations. If retrieving the DAO instance fails, a
     * <code>PersistenceException</code> is thrown with the given error
     * message.
     * </p>
     *
     * @param errorMsg the error message for the
     *        <code>PersistenceException</code> if retrieving the
     *        <code>UserProfileDAO</code> instance fails
     * @return the <code>UserProfileDAO</code> instance
     * @throws PersistenceException if retrieving the
     *         <code>UserProfileDAO</code> instance from the
     *         <code>DAOFactory</code> fails
     */
    private UserProfileDAO getDAO(String errorMsg) throws PersistenceException {
        try {
            return DAOFactory.getUserProfileDAO();
        } catch (ObjectInstantiationException e) {
            throw new PersistenceException(errorMsg, e);
        }
    }

}
