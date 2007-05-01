/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.chat.user.profile;

import java.util.Map;

/**
 * <p>
 * This interface defines a service-level pluggable persistence strategy that can be used by
 * <code>ChatProfileManagerImpl</code> to persist user profiles.
 * </p>
 *
 * <p>
 * Note that this interface specifically only defines a *service-level* persistence.
 * That is, an application or domain can be composed of several services, each with its own
 * <code>ChatUserProfilePersistence</code> data store.
 * </p>
 *
 * <p>
 * <code>ChatUserProfileManagerImpl</code> can, in fact, aggregates several different sources
 * whose identity is distinguished by the service type ({@link ChatUserProfile#getType()}.
 * </p>
 *
 * <p>
 * Thread Safety: Implementations of this interface are required to be thread-safe.
 * It will be used by <code>ChatUserProfileManagerImpl</code> in a way that assumed implementations
 * are completely thread-safe.
 * </p>
 *
 * @author duner, TCSDEVELOPER
 * @version 2.0
 */
public interface ChatUserProfilePersistence {
    /**
     * <p>
     * This method is responsible for establishing a profile inside the particular persistence
     * from the given ChatUserProfile.
     * </p>
     *
     * <p>
     * If this profile already exists in the persistence, it is expected that a DuplicateProfileException
     * will be thrown to indicate failure.
     * </p>
     *
     * <p>
     * Note, the username contained in the <code>ChatUserProfile</code> is expected to uniquely identify
     * the user in the persistence. The id of <code>ChatUserProfile</code> is used application-wide and
     * should generally not be used in the persistence.
     * </p>
     *
     * @param profile The non-null ChatUserProfile instance to create in the persistence.
     *
     * @throws IllegalArgumentException if profile is null.
     * @throws DuplicateProfileException if the profile already exists in the persistence.
     * @throws ChatUserProfilePersistenceException if there is an error in the persistence
     * unrelated to whether or not a profile could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to connect
     * to the database or a file that could not be found.
     */
    public void createProfile(ChatUserProfile profile) throws DuplicateProfileException,
        ChatUserProfilePersistenceException;

    /**
     * <p>
     * This method is responsible for deleting a profile inside the particular persistence
     * from the given username.
     * </p>
     *
     * <p>
     * If this profile doesn't already exist in the persistence, it is expected that
     * a DuplicateProfileException will be thrown to indicate failure.
     * </p>
     *
     * <p>
     * Note, the username contained in the <code>ChatUserProfile</code> is expected to uniquely identify
     * the user in the persistence.
     * </p>
     *
     * @param username The non-null, non-empty (trimmed) username to delete from the persistence.
     *
     * @throws IllegalArgumentException if username is null or empty (trimmed).
     * @throws ProfileNotFoundException if the relevant profile could not be located within the persistence.
     * @throws ChatUserProfilePersistenceException if there is an error in the persistence unrelated to
     * whether or not a profile could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to connect to
     * the database or a file that could not be found.
     */
    public void deleteProfile(String username) throws ProfileNotFoundException, ChatUserProfilePersistenceException;

    /**
     * <p>
     * This method is responsible for updating an existing profile inside the particular persistence from
     * the given ChatUserProfile.
     * </p>
     *
     * <p>
     * If this profile doesn't already exist in the persistence, it is expected that a ProfileNotFoundException
     * will be thrown to indicate failure.
     * </p>
     *
     * <p>
     * Note, the username contained in the <code>ChatUserProfile</code> is expected to uniquely identify
     * the user in the persistence. The id of <code>ChatUserProfile</code> is used application-wide and
     * should generally not be used in the persistence.
     * </p>
     *
     * @param profile A non-null ChatUserProfile instance indicating the profile that needs to be updated.
     *
     * @throws IllegalArgumentException if profile is null.
     * @throws ProfileNotFoundException if the relevant profile could not be located within the persistence.
     * @throws ChatUserProfilePersistenceException if there is an error in the persistence unrelated
     * to whether or not a profile could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to connect to the
     * database or a file that could not be found.
     */
    public void updateProfile(ChatUserProfile profile) throws ProfileNotFoundException,
        ChatUserProfilePersistenceException;

    /**
     * <p>
     * This method is responsible for retrieving a profile inside the particular persistence from the given username.
     * </p>
     *
     * <p>
     * If this profile doesn't already exist in the persistence, it is expected that a ProfileNotFoundException
     * will be thrown to indicate failure.
     * </p>
     *
     * <p>
     * Note, the username contained in the <code>ChatUserProfile</code> is expected to uniquely identify
     * the user in the persistence.
     * </p>
     *
     * @param username The non-null, non-empty (trimmed) username to retrieve from the persistence.
     * @return The ChatUserProfile corresponding to the passed-in username.
     *
     * @throws IllegalArgumentException if username is null or empty (trimmed).
     * @throws ProfileNotFoundException if the relevant profile could not be located within the persistence.
     * @throws ChatUserProfilePersistenceException if there is an error in the persistence unrelated to whether or
     * not a profile could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to connect to the database
     * or a file that could not be found.
     */
    public ChatUserProfile getProfile(String username) throws ProfileNotFoundException,
        ChatUserProfilePersistenceException;

    /**
     * <p>
     * This method is responsible for retrieving a set of profiles inside the particular persistence from
     * the given usernames.
     * </p>
     *
     * <p>
     * If none of the profiles exist in the persistence, it is expected that a
     * <code>ProfileNotFoundException</code> will be thrown to indicate failure.
     * </p>
     *
     * <p>
     * The return of this method is an array of <code>ChatUserProfile</code> instances corresponding
     * to the input array. If there is no match for a username, then the corresponding position of
     * return values should be null.
     * </p>
     *
     * <p>
     * Note, the username contained in the <code>ChatUserProfile</code> is expected to uniquely identify
     * the user in the persistence.
     * </p>
     *
     * @param usernames A non-null array of strings which contain the usernames to be found.
     * No element should be null or empty (trimmed)
     * @return An array of ChatUserProfile instances corresponding to the usernames passed.
     *
     * @throws IllegalArgumentException if usernames is null or contains null or empty (trimmed) elements,
     * or if the length of the string array is zero
     * @throws ProfileNotFoundException if no profile could be located within the persistence.
     * @throws ChatUserProfilePersistenceException if there is an error in the persistence unrelated to
     * whether or not a profile could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to connect to the database
     * or a file that could not be found.
     */
    public ChatUserProfile[] getProfiles(String[] usernames) throws ProfileNotFoundException,
        ChatUserProfilePersistenceException;

    /**
     * <p>
     * This method searches all profiles for the properties given in the <code>criteria</code> Map.
     * </p>
     *
     * <p>
     * The profiles to search is limited by the registeredUsers array.
     * All returned results should only contain the usernames listed in registeredUsers.
     * </p>
     *
     * <p>
     * This is provided to allow for a more efficient search.
     * Since we are only interested in users registered with <code>ProfileKeyManager</code>, a lookup is
     * performed prior to calling this method for all registered users in the source.
     * </p>
     *
     * @param criteria A non-null Map containing a mapping from String keys representing property
     * names to values representing either a string value or a list of values.
     * The string value may be any (non-null) value, the list of strings may contain any non-null strings.
     * @param registeredUsers A non-null Array of usernames that the search should be restricted to.
     * If possible, the persistence should not perform complex search operations on anyone not in this list.
     * No element should be null or empty (trimmed).
     * @return An array of results. There is no correspondence between the input and the output.
     * The array is simply a list of results in no predefined order.
     * If there are no results, an empty array is returned, no exception is thrown.
     *
     * @throws IllegalArgumentException if criteria is null, if criteria contains null keys
     * or null values or if its values are not either a String or List or if the contained List contains
     * null string elements or if registeredUsers is null or if registeredUsers contains any null
     * or empty (trimmed) values or if registeredUsers contains zero element
     * @throws ChatUserProfilePersistenceException if there is an error in the persistence unrelated to
     * whether or not a profile could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to connect to the
     * database or a file that could not be found.
     */
    public ChatUserProfile[] searchProfiles(Map criteria, String[] registeredUsers)
        throws ChatUserProfilePersistenceException;
}
