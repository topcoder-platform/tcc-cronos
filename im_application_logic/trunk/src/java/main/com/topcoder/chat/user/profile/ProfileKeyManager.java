/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.chat.user.profile;

/**
 * <p>
 * This interface defines a means for abstracting the assignment and storage of long ids
 * corresponding to unique (username, type) pairs.
 * </p>
 *
 * <p>
 * It allows for the creation, deletion and retrieval of ProfileKeys in a variety of ways.
 * This is used strictly by ChatUserProfileManager to delegate the task of managing ids since
 * the ids are application-wide identifiers and the data sources (ChatUserProfilePersistence
 * implementations) are service-level identifiers.
 * </p>
 *
 * <p>
 * Thread safety: Implementations of this class are not required to be thread-safe.
 * <code>ChatUserProfileManager</code> is required to be thread-safe and will utilize implementations of
 * this class in a thread-safe manner. However, users are cautioned not to use instances of
 * this class concurrent to ChatUserProfileManager as ChatUserProfileManager will
 * assume ownership of the manager.
 * </p>
 *
 * @author duner, TCSDEVELOPER
 * @version 2.0
 */
public interface ProfileKeyManager {
    /**
     * <p>
     * This method is responsible for creating the <code>ProfileKey</code>
     * (that is, a mapping from a username,type pair to an id).
     * </p>
     *
     * <p>
     * Generally speaking, this method will simply generate an id and store the id, username and type
     * in the persistence.
     * </p>
     *
     * <p>
     * If the <code>ProfileKey</code> already exists in the persistence, it is expected that this
     * method will throw <code>DuplicateProfileKeyException</code>.
     * </p>
     *
     * @param key A non-null ProfileKey instance. This is guaranteed to have both the username and type filled in.
     * If the key also has a valid id, this should be ignored.
     * @return A non-null ProfileKey instance representing the username, type pair from the passed instance
     * plus the newly assigned id.
     *
     * @throws IllegalArgumentException if key is null.
     * @throws DuplicateProfileKeyException if the (username, type) pair already exists in the persistence.
     * @throws ProfileKeyManagerPersistenceException if there is an error in the persistence unrelated to
     * whether or not a ProfileKey could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to connect to the database
     * or a file that could not be found.
     */
    public ProfileKey createProfileKey(ProfileKey key) throws DuplicateProfileKeyException,
        ProfileKeyManagerPersistenceException;

    /**
     * <p>
     * This method is responsible for deleting the ProfileKey with the given long id.
     * </p>
     *
     * <p>
     * If the <code>ProfileKey</code> does not already exist in the persistence, it is expected
     * that this method will throw <code>ProfileKeyNotFoundException</code>.
     * </p>
     *
     * @param id A non-negative id indicating the <code>ProfileKey</code> to delete from the persistence.
     *
     * @throws IllegalArgumentException if id is negative (&lt; 0).
     * @throws ProfileKeyNotFoundException if the relevant ProfileKey could not be located within the persistence.
     * @throws ProfileKeyManagerPersistenceException if there is an error in the persistence unrelated to whether
     * or not a ProfileKey could or could not be found in the persistence.  This is generally used for failures
     * in the persistence, such as the inability to connect to the database or a file that could not be found.
     */
    public void deleteProfileKey(long id) throws ProfileKeyNotFoundException, ProfileKeyManagerPersistenceException;

    /**
     * <p>
     * This method is responsible for retrieving the <code>ProfileKey</code> with the given long id.
     * </p>
     *
     * <p>
     * If the <code>ProfileKey</code> does not already exist in the persistence, it is expected that
     * this method will return null rather than throw an exception.
     * </p>
     *
     * @param id A non-negative id indicating the id to search for.
     * @return A ProfileKey instance corresponding to the passed id. May be null.
     *
     * @throws IllegalArgumentException if id is negative (&lt; 0).
     * @throws ProfileKeyManagerPersistenceException if there is an error in the
     * persistence unrelated to whether or not a ProfileKey could or could not
     * be found in the persistence. This is generally used for failures in the
     * persistence, such as the inability to connect to the database or a file
     * that could not be found.
     */
    public ProfileKey getProfileKey(long id) throws ProfileKeyManagerPersistenceException;

    /**
     * <p>
     * This method is responsible for retrieving the <code>ProfileKey</code> instances with the
     * given long ids.
     * </p>
     *
     * <p>
     * This is simply a batch method of {@link #getProfileKey(long)}.
     * </p>
     *
     * <p>
     * The return values will be stored such that the return value at position <b>i</b> will correspond
     * to the ids input as position <b>i</b>. If no match was found at a position, then the corresponding
     * position of the return values should be null.
     * </p>
     *
     * <p>
     * The returned array should thus be the same size as the input array.
     * </p>
     *
     * <p>
     * Note, if no <code>ProfileKey</code> exists in the persistence among the given ids, it is expected
     * that this method return null to indicate that no keys could be found.
     * </p>
     *
     * @param ids A non-null list of long ids indicating which ProfileKeys to retrieve.
     * Each element should be non-negative.
     * @return An array of <code>ProfileKey</code> instances corresponding to the input array.
     *
     * @throws IllegalArgumentException if ids is null or is ids contains a negative (< 0) element,
     * or it doesn't have any element
     * @throws ProfileKeyManagerPersistenceException if there is an error in the persistence
     * unrelated to whether or not a ProfileKey could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to connect
     * to the database or a file that could not be found.
     */
    public ProfileKey[] getProfileKeys(long[] ids) throws ProfileKeyManagerPersistenceException;

    /**
     * <p>
     * This method is responsible for retrieving the <code>ProfileKey</code> with the given username and type.
     * </p>
     *
     * <p>
     * If no <code>ProfileKey</code> exists in the persistence for the given username, it is expected
     * that this method return null to indicate that no keys could be found.
     * </p>
     *
     * @param username A non-null, non-empty (trimmed) String representing the username.
     * @param type A non-null String representing the type to search for.
     * @return A non-null ProfileKey instance corresponding to the username and type.
     * This is guaranteed to be unique within the manager.
     *
     * @throws IllegalArgumentException if either argument is null or if username is empty (trimmed).
     * @throws ProfileKeyManagerPersistenceException if there is an error in the persistence
     * unrelated to whether or not a ProfileKey could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to connect
     * to the database or a file that could not be found.
     */
    public ProfileKey getProfileKey(String username, String type) throws ProfileKeyManagerPersistenceException;

    /**
     * <p>
     * This method is responsible for retrieving the <code>ProfileKey</code> instances with the given
     * user names and type.
     * </p>
     *
     * <p>
     * This is simply a batch method of {@link #getProfileKey(String, String)}.
     * </p>
     *
     * <p>
     * The return values will be stored such that the return value at position <b>i</b> will
     * correspond to the usernames input as position <b>i</b>.
     * Note, if no match was found at a position, then the corresponding position of the
     * return values will be null.
     * </p>
     *
     * <p>
     * The returned array should thus be the same size as the input array.
     * </p>
     *
     * <p>
     * If no <code>ProfileKey</code> exists in the persistence among the given usernames,
     * it is expected that this method return null to indicate that no keys could be found.
     * </p>
     *
     * @param usernames A non-null list of usernames to search for.
     * Each element must be non-null and non-empty (trimmed).
     * @param type A non-null type to use as a context for looking up usernames.
     * @return An array of ProfileKey instances corresponding to the input array and type.
     * Please see the method docs for more info about the structure of the return value.
     *
     * @throws IllegalArgumentException if type or usernames is null or if usernames
     * contains null or empty (trimmed) elements, or it doesn't have any element
     * @throws ProfileKeyManagerPersistenceException if there is an error in the persistence
     * unrelated to whether or not a ProfileKey could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to connect
     * to the database or a file that could not be found.
     */
    public ProfileKey[] getProfileKeys(String[] usernames, String type) throws ProfileKeyManagerPersistenceException;

    /**
     * <p>
     * This method is used to retrieve all <code>ProfileKey</code> instances corresponding
     * to a given type.
     * </p>
     *
     * <p>
     * This method is expected to throw <code>ProfileKeyNotFoundException</code> in the case
     * where there is no type in the persistence matching the type.
     * </p>
     *
     * <p>
     * Note that this method differs from the others getXXX() methods in that no
     * results here *is* an exceptional conditional and so <code>ProfileKeyNotFoundException</code>
     * is thrown while it is not thrown in those methods.
     * </p>
     *
     * @param type The non-null type to search for.
     * @return A ProfileKey array corresponding to users that are registered
     * under this type.
     *
     * @throws IllegalArgumentException if type is null.
     * @throws ProfileKeyNotFoundException if the relevant ProfileKey could not be
     * located within the persistence.
     * @throws ProfileKeyManagerPersistenceException if there is an error in the
     * persistence unrelated to whether or not a ProfileKey could or could not be
     * found in the persistence.  This is generally used for failures in the persistence,
     * such as the inability to connect to the database or a file that could not be found.
     */
    public ProfileKey[] getProfileKeys(String type) throws ProfileKeyNotFoundException,
        ProfileKeyManagerPersistenceException;

    /**
     * <p>
     * This method is used to retrieve all <code>ProfileKey</code> instances corresponding
     * to a set of given types.
     * </p>
     *
     * <p>
     * This is a batch version of {@link #getProfileKeys(String)} and is used to more
     * efficiently handle retrieval of multiple types at once.
     * </p>
     *
     * <p>
     * This method is expected to throw <code>ProfileKeyNotFoundException</code> in the case
     * where there are no types in the persistence matching any of the types.
     * </p>
     *
     * <p>
     * Note that this method differs from the others getXXX() methods in that no results here
     * is an exceptional conditional and so <code>ProfileKeyNotFoundException</code> is thrown
     * while it is not thrown in those methods.
     * </p>
     *
     * <p>
     * The return values will be stored such that the return value at position <b>i</b> will
     * correspond to the types input as position <b>i</b>.  If no match was found at a position,
     * then the corresponding position of the return values should be null.
     * </p>
     *
     * <p>
     * The returned array should thus be the same size as the input array.
     * </p>
     *
     * @param types A non-null array containing the types to retrieve.
     * Each element of types must be non-null.
     * @return A 2D array representing the results for each type.
     *
     * @throws IllegalArgumentException if types is null or has any null elements, or it doesn't
     * have any element
     * @throws ProfileKeyNotFoundException there are no types in the persistence matching any of
     * the types.
     * @throws ProfileKeyManagerPersistenceException if there is an error in the persistence
     * unrelated to whether or not a ProfileKey could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to connect
     * to the database or a file that could not be found.
     */
    public ProfileKey[][] getProfileKeys(String[] types) throws ProfileKeyNotFoundException,
        ProfileKeyManagerPersistenceException;
}
