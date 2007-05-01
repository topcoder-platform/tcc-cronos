/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.chat.user.profile;

import java.util.Map;

/**
 * <p>
 * This interfaces defines the contract for managing a set of users in any number of services
 * using a centralized interface.
 * </p>
 *
 * <p>
 * The manager's main goal is to manage a number of data sources to direct requests and to
 * manage assignment of application-wide ids to (username, type) pairs that belong to
 * per-service data sources.
 * </p>
 *
 * <p>
 * There is one default implementation of this interface <code>ChatUserProfileManagerImpl</code>
 * in current implementation.
 * </p>
 *
 * <p>
 * Thread safety: Implementations of this interface are required to be thread-safe.
 * Since ChatUserProfilePersistence is also required to be thread-safe, this generally
 * means ensuring that all access to ProfileKeyManager and other internal state (aside
 * from the data sources) need to be synchronized in some way.
 * </p>
 *
 * @author duner, TCSDEVELOPER
 * @version 2.0
 */
public interface ChatUserProfileManager {
    /**
     * <p>
     * This method is responsible for creating a profile from the given <code>ChatUserProfile</code>.
     * </p>
     *
     * <p>
     * Generally speaking, this method will have the following responsibilities:
     * </p>
     *
     * <p>
     * <ol>
     * <li>
     * Delegate to the <code>ProfileKeyManager</code> to create a <code>ProfileKey</code>
     * with a valid id.
     * </li>
     * <li>
     * Delegate to the appropriate persistence layer in order to persist the <code>ChatUserProfile</code>.
     * </li>
     * <li>
     * Assign the id obtained in step #1 to the <code>ChatUserProfile</code> instance (the argument).
     * </li>
     * </ol>
     * </p>
     *
     * <p>
     * Note, if step 2 fails, it is up to the discretion of the <code>ChatUserProfileManager</code>
     * implementation how to handle the key created in step 1.
     * It may be removed using {@link ProfileKeyManager#deleteProfileKey(long)} or it may be kept
     * as-is in which case it is likely that step 1 should be modified to first check for existence
     * of the key and then create it if necessary.
     * </p>
     *
     * @param profile A non-null ChatUserProfile instance to be created in the appropriate persistence.
     *
     * @throws IllegalArgumentException if profile is null.
     * @throws ProfileKeyManagerPersistenceException if there is an error in the persistence
     * unrelated to whether or not a ProfileKey could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to connect
     * to the database or a file that could not be found.
     * @throws ChatUserProfilePersistenceException if there is an error in the persistence
     * unrelated to whether or not a profile could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to
     * connect to the database or a file that could not be found.  if there was an error
     * storing the profile (aside from an already existing profile in the persistence).
     * @throws DuplicateProfileException If the profile already exists in the persistence.
     * @throws DuplicateProfileKeyException (optional) - If the manager requires that the
     * key not already exist in the ProfileKeyManager then this exception may be thrown.
     * The default implementation has no such restriction.
     * @throws UnrecognizedDataSourceTypeException if the data source type does not exist in this manager.
     */
    public void createProfile(ChatUserProfile profile) throws ProfileKeyManagerPersistenceException,
        ChatUserProfilePersistenceException, DuplicateProfileException, DuplicateProfileKeyException,
        UnrecognizedDataSourceTypeException;

    /**
     * <p>
     * This method is responsible for deleting a profile from the given ChatUserProfile.
     * </p>
     *
     * <p>
     * Generally speaking, this method will have the following responsibilities:
     * </p>
     *
     * <p>
     * <ol>
     * <li>
     * Retrieve the (username, type) pair using {@link ProfileKeyManager#getProfileKey(long)}.
     * </li>
     * <li>
     * Delegate to the appropriate persistence using the just retrieved username.
     * </li>
     * <li>
     * Optional: Delete the <code>ProfileKey</code> from the <code>ProfileKeyManager</code>
     * depending on the behavior of the <code>ChatUserProfileManager</code> implementation.
     * The default implementation does not perform this step.
     * </li>
     * </ol>
     * </p>
     *
     * @param id A non-negative id representing the application-wide identified used to
     * identify the user to be deleted.
     *
     * @throws IllegalArgumentException if id is negative (&lt; 0).
     * @throws ProfileKeyNotFoundException if the profile key could not be found for this id.
     * @throws ProfileNotFoundException if the profile could not be found in the persistence.
     * @throws ChatUserProfilePersistenceException if there is an error in the persistence unrelated
     * to whether or not a profile could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to connect
     * to the database or a file that could not be found.
     * @throws ProfileKeyManagerPersistenceException if there is an error in the persistence
     * unrelated to whether or not a ProfileKey could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to connect
     * to the database or a file that could not be found.
     * @throws UnrecognizedDataSourceTypeException if the data source type does not exist in this manager.
     */
    public void deleteProfile(long id) throws ProfileKeyNotFoundException, ProfileNotFoundException,
        ChatUserProfilePersistenceException, ProfileKeyManagerPersistenceException, UnrecognizedDataSourceTypeException;

    /**
     * <p>
     * This method is responsible for updating a given profile using the passed <code>ChatUserProfile</code>
     * instance.
     * </p>
     *
     * <p>
     * Generally speaking, this method will simply delegate to the persistence and will not create an
     * id via <code>ProfileKeyManager</code>.
     * This method *may* do so if it wishes, however, the idea is that the profile in question will already
     * have had an id assigned to it by the {@link #createProfile(ChatUserProfile)} method or the
     * {@link #getProfile(long)} method.
     * </p>
     *
     * @param profile A non-null ChatUserProfile instance to update.
     *
     * @throws IllegalArgumentException if profile is null.
     * @throws ProfileNotFoundException if the relevant profile could not be located within
     * the persistence.
     * @throws ProfileKeyManagerPersistenceException (optional) if there is an error in the
     * persistence unrelated to whether or not a ProfileKey could or could not be found in
     * the persistence.  This is generally used for failures in the persistence, such as the
     * inability to connect to the database or a file that could not be found.
     * @throws ChatUserProfilePersistenceException if there is an error in the persistence
     * unrelated to whether or not a profile could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to
     * connect to the database or a file that could not be found.
     * @throws UnrecognizedDataSourceTypeException if the data source type does not exist
     * in this manager.
     */
    public void updateProfile(ChatUserProfile profile) throws ProfileNotFoundException,
        ProfileKeyManagerPersistenceException, ChatUserProfilePersistenceException, UnrecognizedDataSourceTypeException;

    /**
     * <p>
     * Retrieves a <code>ChatUserProfile</code> instance using the given id.
     * </p>
     *
     * <p>
     * Generally speaking, this method should:
     * </p>
     *
     * <p>
     * <ol>
     * <li>
     * Use the <code>ProfileKeyManager</code> to retrieve the <code>ProfileKey</code> instance
     * indicating the (username, type) pair.
     * </li>
     * <li>
     * Using the type retrieved in step #1 to direct the request to the appropriate persistence.
     * </li>
     * <li>
     * Return the <code>ChatUserProfile</code> instance returned from the persistence.
     * </li>
     * </ol>
     * </p>
     *
     * @param id A non-negative id used to identify the username, type pair to retrieve.
     * @return A ChatUserProfile instance corresponding to the relevant user. Will not be null.
     *
     * @throws IllegalArgumentException if id is negative (&lt; 0).
     * @throws ProfileNotFoundException if the relevant profile could not be located within the persistence.
     * @throws ProfileKeyNotFoundException if the relevant ProfileKey could not be located within the persistence.
     * @throws ProfileKeyManagerPersistenceException if there is an error in the persistence unrelated
     * to whether or not a ProfileKey could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to connect to
     * the database or a file that could not be found.
     * @throws ChatUserProfilePersistenceException if there is an error in the persistence unrelated
     * to whether or not a profile could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to connect
     * to the database or a file that could not be found.
     * @throws UnrecognizedDataSourceTypeException if the data source type does not exist in this manager.
     */
    public ChatUserProfile getProfile(long id) throws ProfileNotFoundException, ProfileKeyNotFoundException,
        ProfileKeyManagerPersistenceException, ChatUserProfilePersistenceException, UnrecognizedDataSourceTypeException;

    /**
     * <p>
     * Retrieves a <code>ChatUserProfile</code> instance using the given (username, type) pair.
     * </p>
     *
     * <p>
     * Generally speaking, this method should:
     * </p>
     *
     * <p>
     * <ol>
     * <li>
     * Using the type provided to direct the request to the appropriate persistence.
     * </li>
     * <li>
     * Retrieve the appropriate id from ProfileKeyManager.
     * </li>
     * </ol>
     * </p>
     *
     * @param username A non-null, non-empty (trimmed) username representing the user to retrieve.
     * @param type A non-null data source from which to retrieve the user.
     * @return A ChatUserProfile instance representing the username, type pair.
     * The id should be filled in.
     *
     * @throws IllegalArgumentException if username or type is null or if username is empty (trimmed).
     * @throws ProfileNotFoundException if the relevant profile could not be located within the persistence.
     * @throws ChatUserProfilePersistenceException if there is an error in the persistence unrelated to
     * whether or not a profile could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to connect to the
     * database or a file that could not be found.
     * @throws ProfileKeyManagerPersistenceException if there is an error in the persistence unrelated
     * to whether or not a ProfileKey could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to connect to the
     * database or a file that could not be found.
     * @throws UnrecognizedDataSourceTypeException if the data source type does not exist in this manager.
     */
    public ChatUserProfile getProfile(String username, String type) throws ProfileNotFoundException,
        ChatUserProfilePersistenceException, ProfileKeyManagerPersistenceException, UnrecognizedDataSourceTypeException;

    /**
     * <p>
     * This is a batch version of {@link #getProfile(String, String)}.
     * </p>
     *
     * <p>
     * It should do exactly the same things as that method except it should rely on the relevant
     * batch methods in the persistence and the <code>ProfileKeyManager</code>.
     * </p>
     *
     * <p>
     * The return of this method is an array of <code>ChatUserProfile</code> instances corresponding
     * to the input array. If there is no match for a username, then the corresponding position of
     * return values should be null.
     * </p>
     *
     * <p>
     * If there are no matches in the persistence for any username, then <code>ProfileNotFoundException</code>
     * is thrown (rather than return an array with all nulls).
     * </p>
     *
     * @param usernames An non-null array of username strings.
     * Each element must be non-null and non-empty (trimmed).
     * @param type A non-null type indicating the data source from which to load the usernames.
     * @return An array of ChatUserProfile instances from the datasource specified by type.
     *
     * @throws IllegalArgumentException if usernames or type are null or if any element of
     * usernames is null or empty (trimmed).
     * @throws ProfileNotFoundException if the relevant profiles could not be located within
     * the persistence as described in the method docs.
     * @throws ChatUserProfilePersistenceException if there is an error in the persistence
     * unrelated to whether or not a profile could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to connect
     * to the database or a file that could not be found.
     * @throws ProfileKeyManagerPersistenceException if there is an error in the persistence
     * unrelated to whether or not a ProfileKey could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to connect
     * to the database or a file that could not be found.
     * @throws UnrecognizedDataSourceTypeException if the data source type does not exist in this manager.
     */
    public ChatUserProfile[] getProfiles(String[] usernames, String type) throws ProfileNotFoundException,
        ChatUserProfilePersistenceException, ProfileKeyManagerPersistenceException, UnrecognizedDataSourceTypeException;

    /**
     * <p>
     * This method is responsible for searching for all profiles whose properties
     * match all of the given criteria in the Map.
     * </p>
     *
     * <p>
     * If at least one property cannot be found for that user (or in the persistence at all),
     * then no result should be returned for that user.
     * </p>
     *
     * <p>
     * This method is expected to only perform the search on users registered in <code>ProfileKeyManager</code>,
     * though that is up to the individual implementation to decide for itself.
     * </p>
     *
     * @param criteria A non-null Map composed of non-null String keys and non-null String
     * elements (or non-null List elements with non-null Strings as values).
     * @return An array of ChatUserProfile instances corresponding to the users found
     * in the persistence matching the specified criteria.
     *
     * @throws IllegalArgumentException if criteria does not meet the requirements
     * specified in the docs for that argument (above).
     * @throws ChatUserProfilePersistenceException if there is an error in the persistence
     * unrelated to whether or not a profile could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to
     * connect to the database or a file that could not be found.
     * @throws ProfileKeyManagerPersistenceException if there is an error in the persistence
     * unrelated to whether or not a ProfileKey could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to connect
     * to the database or a file that could not be found.
     */
    public ChatUserProfile[] searchProfiles(Map criteria) throws ChatUserProfilePersistenceException,
        ProfileKeyManagerPersistenceException;

    /**
     * <p>
     * The method generates a ProfileKey for a given (username, type) pair.
     * </p>
     *
     * <p>
     * This is provided in case a user is unregistered in <code>ProfileKeyManager</code> but exists
     * in the datasource.
     * </p>
     *
     * <p>
     * This gives the user an explicit way to generate a ProfileKey in the persistence for the given
     * (username, type) pair.
     * </p>
     *
     * @param username A non-null, non-empty username string to add.
     * @param type A non-null service type to add.
     *
     * @throws IllegalArgumentException if username is null or empty (trimmed) or if type is null.
     * @throws DuplicateProfileKeyException if the (username, type) pair already exists in the persistence.
     * @throws ProfileKeyManagerPersistenceException if there is an error in the persistence unrelated
     * to whether or not a ProfileKey could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to connect to
     * the database or a file that could not be found.
     * @throws UnrecognizedDataSourceTypeException if the data source type does not exist in this manager.
     */
    public void generateProfileKey(String username, String type) throws DuplicateProfileKeyException,
        ProfileKeyManagerPersistenceException, UnrecognizedDataSourceTypeException;
}
