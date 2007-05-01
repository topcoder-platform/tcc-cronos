/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.chat.user.profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * This class is the default, and expected to be the only implementation of
 * <code>ChatUserProfileManager</code>.
 * </p>
 *
 * <p>
 * It lightly manages the interaction between all data sources and the <code>ProfileKeyManager</code>.
 * </p>
 *
 * <p>
 * It serves as a centralized source of data for all services across a given application or
 * application domain while delegating to <code>ProfileKeyManager</code> to provide
 * application-wide identification regardless of service-level identification (user names).
 * </p>
 *
 * <p>
 * Thread-safety: This class is thread-safe. This is accomplished in a couple of ways.
 * </p>
 *
 * <p>
 * Firstly, the internal state is immutable, so there is no danger of internal data integrity
 * issues. The internal state of this class will remain consistent at all times.
 * </p>
 *
 * <p>
 * Secondly, since <code>ChatUserProfilePersistence</code> is thread-safe and the
 * <code>ProfileKeyManager</code> instance is synchronized when accessing it.
 * </p>
 *
 * @author duner, TCSDEVELOPER
 * @version 2.0
 */
public class ChatUserProfileManagerImpl implements ChatUserProfileManager {
    /**
     * <p>
     * Represents a mapping between a <b>type</b> ({@link ChatUserProfile#getType()})
     * and {@link ProfileKey#getType()}) and a data source.
     * </p>
     *
     * <p>
     * Each key is a non-null String and each value is a non-null ChatUserProfilePersistence instance.
     * </p>
     *
     * <p>
     * This is set and filled in the constructor, once set, this value cannot be changed nor
     * can its elements.
     * </p>
     *
     * <p>
     * At a minimum, there will be at least one type, data source pair registered.
     * </p>
     *
     * <p>
     * This variable is accessed in all methods to delegate to the appropriate data source.
     * </p>
     */
    private final Map dataSources;

    /**
     * <p>
     * This is the ProfileKeyManager used to perform <b>(username, type) -&gt; id</b> and
     * <b>id -&gt; (username, type)</b> lookups.
     * </p>
     *
     * <p>
     * It is set in the constructor to a non-null value and cannot be changed afterwards.
     * </p>
     *
     * <p>
     * It is used in all of the persistence method in order to determine to which data source the
     * request must be directed.
     * </p>
     *
     * <p>
     * This variable is synchronized when it is used for thread safety consideration.
     * </p>
     */
    private final ProfileKeyManager keyManager;

    /**
     * <p>
     * This method is responsible for initializing the <code>ChatUserProfileManager</code> instance.
     * </p>
     *
     * <p>
     * Note, each element in <code>sourceTypes</code> corresponds to the same indexed element in
     * <code>sources</code>.
     * </p>
     *
     * @param sourceTypes A non-null array of String representing the &quot;type&quot; of each item
     * in <code>sources</code>.
     * Each element in <code>sourceTypes</code> corresponds to the same indexed element in
     * <code>sources</code>. Each element is expected to be non-null.
     * @param sources A non-null array of ChatUserProfilePersistence instances representing the
     * data sources. Each element of <code>sources</code> corresponds to the same indexed element of
     * <code>sourceTypes</code>. Each element in expected to be non-null.
     * @param keyManager The non-null <code>ProfileKeyManager</code> instance used to perform
     * (username, type) to id and id to (username, type) lookups.
     *
     * @throws IllegalArgumentException if any argument is null or any array element is null or if
     * the two arrays are not the same length or if there are duplicates in sourceTypes or if sourceTypes
     * has no elements.
     */
    public ChatUserProfileManagerImpl(String[] sourceTypes, ChatUserProfilePersistence[] sources,
        ProfileKeyManager keyManager) {
        Util.checkNull(keyManager, "keyManager");

        this.dataSources = createDataSourceMap(sourceTypes, sources);
        this.keyManager = keyManager;
    }

    /**
     * <p>
     * This method creates a <code>Map</code> instance according to the given <code>sourceTypes</code>
     * and <code>sources</code>.
     * </p>
     *
     * <p>
     * Note, each element in <code>sourceTypes</code> corresponds to the same indexed element in
     * <code>sources</code>.
     * </p>
     *
     * @param sourceTypes A non-null array of String representing the &quot;type&quot; of each item
     * in <code>sources</code>.
     * Each element in <code>sourceTypes</code> corresponds to the same indexed element in
     * <code>sources</code>. Each element is expected to be non-null.
     * @param sources A non-null array of ChatUserProfilePersistence instances representing the
     * data sources. Each element of <code>sources</code> corresponds to the same indexed element of
     * <code>sourceTypes</code>. Each element in expected to be non-null.
     * @return the <code>Map</code> instance according to the given <code>sourceTypes</code>
     * and <code>sources</code>, the key is a source type string and the value is the
     * <code>ChatUserProfilePersistence</code> instance
     *
     * @throws IllegalArgumentException if any argument is null or any array element is null or if
     * the two arrays are not the same length or if there are duplicates in sourceTypes or if sourceTypes
     * has no elements.
     */
    private Map createDataSourceMap(String[] sourceTypes, ChatUserProfilePersistence[] sources) {
        Util.checkNull(sourceTypes, "sourceTypes");
        Util.checkNull(sources, "sources");

        // check the length
        if (sourceTypes.length != sources.length) {
            throw new IllegalArgumentException("The length of sourceTypes [" + sourceTypes.length
                + "] is not the same as the length of the sources [" + sources.length + "].");
        }

        // zero length is not allowed
        if (sourceTypes.length == 0) {
            throw new IllegalArgumentException("There is no source type, at least one source type should be provided.");
        }

        Map map = new HashMap();
        for (int i = 0; i < sourceTypes.length; i++) {
            Util.checkNull(sourceTypes[i], "element in sourceTypes");
            Util.checkNull(sources[i], "element in sources");

            // no duplicate source type
            if (map.containsKey(sourceTypes[i])) {
                throw new IllegalArgumentException("The given sourceTypes argument contains duplicate source type ["
                    + sourceTypes[i] + "].");
            }

            // save the mapping to the map instance
            map.put(sourceTypes[i], sources[i]);
        }

        return map;
    }

    /**
     * <p>
     * This method is responsible for creating a profile in the persistence and potentially creating a
     * <code>ProfileKey</code> entry for the passed <code>ChatUserProfile</code>.
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
     * @throws UnrecognizedDataSourceTypeException if the data source type does not exist in this manager.
     */
    public void createProfile(ChatUserProfile profile) throws ProfileKeyManagerPersistenceException,
        ChatUserProfilePersistenceException, DuplicateProfileException, UnrecognizedDataSourceTypeException {
        Util.checkNull(profile, "profile");

        ProfileKey key = getProfileKey(profile.getUsername(), profile.getType());
        profile.setProfileKey(key);

        ChatUserProfilePersistence persistence = getChatUserProfilePersistence(profile.getType());
        persistence.createProfile(profile);
    }

    /**
     * <p>
     * This method gets the corresponding <code>ChatUserProfilePersistence</code> instance for the given
     * <code>type</code>.
     * </p>
     *
     * <p>
     * If the corresponding <code>ChatUserProfilePersistence</code> instance can not be found, it will throw
     * <code>UnrecognizedDataSourceTypeException</code>.
     * </p>
     *
     * @param type The non-null type to search for
     * @return the corresponding <code>ChatUserProfilePersistence</code> instance for the given type in
     * this manager
     *
     * @throws UnrecognizedDataSourceTypeException if the corresponding <code>ChatUserProfilePersistence</code>
     * instance can not be found
     */
    private ChatUserProfilePersistence getChatUserProfilePersistence(String type)
        throws UnrecognizedDataSourceTypeException {
        ChatUserProfilePersistence persistence = (ChatUserProfilePersistence) dataSources.get(type);
        if (persistence == null) {
            throw new UnrecognizedDataSourceTypeException("The data source type [" + type
                + "] does not exist in this manager");
        }

        return persistence;
    }

    /**
     * <p>
     * This method is responsible for deleting a profile from the persistence.
     * </p>
     *
     * <p>
     * Note, The id mapping is not removed by this method from <code>ProfileKeyManager</code>
     * in this implementation as the two persistence implementations (<code>ProfileKey</code> and
     * <code>ChatUserProfile</code>) are not connected and deleting this here may cause a data
     * inconsistency if there is a failure in either persistence.
     * </p>
     *
     * <p>
     * As a result, the user is left to delete this manually if he/she wishes or it can remain
     * in the key mapping persistence until such time that the user is read. This allows
     * the greatest flexibility as the user can either remove the key manually or let it persist
     * and retain the same id depending on his/her needs while ensuring consistency for all
     * delete operations.
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
        ChatUserProfilePersistenceException, ProfileKeyManagerPersistenceException, UnrecognizedDataSourceTypeException {
        ProfileKey key = getProfileKey(id);
        ChatUserProfilePersistence persistence = getChatUserProfilePersistence(key.getType());
        persistence.deleteProfile(key.getUsername());
    }

    /**
     * <p>
     * This method gets the corresponding <code>ProfileKey</code> instance for the given id from
     * <code>ProfileKeyManager</code>.
     * </p>
     *
     * <p>
     * If no <code>ProfileKey</code> instance can be found for the given id, it will throw
     * <code>ProfileKeyNotFoundException</code>.
     * </p>
     *
     * @param id A non-negative id representing the application-wide identified used to
     * identify the user to be deleted.
     * @return the corresponding <code>ProfileKey</code> instance for the given id.
     *
     * @throws IllegalArgumentException if id is negative (&lt; 0).
     * @throws ProfileKeyNotFoundException if the profile key could not be found for this id.
     * @throws ProfileKeyManagerPersistenceException if there is an error in the
     * persistence unrelated to whether or not a ProfileKey could or could not
     * be found in the persistence.  This is generally used for failures in the
     * persistence, such as the inability to connect to the database or a file
     * that could not be found.
     */
    private ProfileKey getProfileKey(long id) throws ProfileKeyManagerPersistenceException, ProfileKeyNotFoundException {
        ProfileKey key;

        synchronized (this.keyManager) {
            key = this.keyManager.getProfileKey(id);
        }

        if (key == null) {
            throw new ProfileKeyNotFoundException("The profile key could not be found for the id [" + id + "].");
        }

        return key;
    }

    /**
     * <p>
     * This method gets the corresponding <code>ProfileKey</code> instance for the given
     * (username, type) pair from <code>ProfileKeyManager</code>.
     * </p>
     *
     * <p>
     * If no <code>ProfileKey</code> instance can be found for the given (username, type) pair,
     * it will create it in <code>ProfileKeyNotFoundException</code>.
     * </p>
     *
     * @param username A non-null, non-empty (trimmed) username representing
     * the user to retrieve.
     * @param type A non-null data source from which to retrieve the user.
     * @return the corresponding <code>ProfileKey</code> instance for the given username and
     * type pair.
     *
     * @throws ProfileKeyManagerPersistenceException if there is an error in the
     * persistence unrelated to whether or not a ProfileKey could or could not
     * be found in the persistence.  This is generally used for failures in the
     * persistence, such as the inability to connect to the database or a file
     * that could not be found.
     */
    private ProfileKey getProfileKey(String username, String type) throws ProfileKeyManagerPersistenceException {
        ProfileKey key;

        synchronized (this.keyManager) {
            key = this.keyManager.getProfileKey(username, type);
            if (key == null) {
                try {
                    key = this.keyManager.createProfileKey(new ProfileKey(username, type));
                } catch (DuplicateProfileKeyException e) {
                    // this will not happen because we have verified it before calling the create method
                    // for safe, the getProfileKey(String, String) is called again for it
                    key = this.keyManager.getProfileKey(username, type);
                }
            }
        }

        return key;
    }

    /**
     * <p>
     * This method is responsible for updating a profile given a <code>ChatUserProfile</code> instance.
     * </p>
     *
     * <p>
     * The sole responsibility of this method is to delegate to the relevant persistence.
     * </p>
     *
     * @param profile A non-null ChatUserProfile instance to update.
     *
     * @throws IllegalArgumentException if profile is null.
     * @throws ProfileNotFoundException if the relevant profile could not be located within
     * the persistence.
     * @throws ChatUserProfilePersistenceException if there is an error in the persistence
     * unrelated to whether or not a profile could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to connect
     * to the database or a file that could not be found.
     * @throws UnrecognizedDataSourceTypeException if the data source type does not exist in this manager.
     */
    public void updateProfile(ChatUserProfile profile) throws ProfileNotFoundException,
        ChatUserProfilePersistenceException, UnrecognizedDataSourceTypeException {
        Util.checkNull(profile, "profile");

        ChatUserProfilePersistence persistence = getChatUserProfilePersistence(profile.getType());
        persistence.updateProfile(profile);
    }

    /**
     * <p>
     * Retrieves a <code>ChatUserProfile</code> instance using the given id.
     * </p>
     *
     * @param id A non-negative id used to identify the username, type pair to retrieve.
     * @return A <code>ChatUserProfile</code> instance corresponding to the relevant user.
     * Will not be null.
     *
     * @throws IllegalArgumentException if id is negative (&lt; 0).
     * @throws ProfileNotFoundException if the relevant profile could not be located
     * within the persistence.
     * @throws ProfileKeyNotFoundException if the relevant ProfileKey could not be
     * located within the persistence.
     * @throws ProfileKeyManagerPersistenceException if there is an error in the
     * persistence unrelated to whether or not a ProfileKey could or could not be
     * found in the persistence.  This is generally used for failures in the persistence,
     * such as the inability to connect to the database or a file that could not be found.
     * @throws ChatUserProfilePersistenceException if there is an error in the persistence
     * unrelated to whether or not a profile could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to
     * connect to the database or a file that could not be found.
     * @throws UnrecognizedDataSourceTypeException if the data source type does not exist in this manager.
     */
    public ChatUserProfile getProfile(long id) throws ProfileNotFoundException, ProfileKeyNotFoundException,
        ProfileKeyManagerPersistenceException, ChatUserProfilePersistenceException, UnrecognizedDataSourceTypeException {
        ProfileKey key = getProfileKey(id);

        ChatUserProfilePersistence persistence = getChatUserProfilePersistence(key.getType());

        ChatUserProfile profile = persistence.getProfile(key.getUsername());
        profile.setProfileKey(key);

        return profile;
    }

    /**
     * <p>
     * Retrieves a <code>ChatUserProfile</code> instance using the given (username, type) pair.
     * </p>
     *
     * @param username A non-null, non-empty (trimmed) username representing
     * the user to retrieve.
     * @param type A non-null data source from which to retrieve the user.
     *
     * @throws IllegalArgumentException if username or type is null or if
     * username is empty (trimmed).
     * @throws ProfileNotFoundException if the relevant profile could not be
     * located within the persistence.
     * @throws ChatUserProfilePersistenceException if there is an error in the
     * persistence unrelated to whether or not a profile could or could not
     * be found in the persistence.  This is generally used for failures in
     * the persistence, such as the inability to connect to the database or
     * a file that could not be found.
     * @throws ProfileKeyManagerPersistenceException if there is an error in
     * the persistence unrelated to whether or not a ProfileKey could or could
     * not be found in the persistence.  This is generally used for failures
     * in the persistence, such as the inability to connect to the database
     * or a file that could not be found.
     * @throws UnrecognizedDataSourceTypeException if the data source
     * type does not exist in this manager.
     */
    public ChatUserProfile getProfile(String username, String type) throws ProfileNotFoundException,
        ChatUserProfilePersistenceException, ProfileKeyManagerPersistenceException, UnrecognizedDataSourceTypeException {
        // the null and empty checking for username is done by the persistence
        Util.checkString(type, "type");
        ChatUserProfilePersistence persistence = getChatUserProfilePersistence(type);

        ChatUserProfile profile = persistence.getProfile(username);

        ProfileKey key = getProfileKey(username, type);
        profile.setProfileKey(key);

        return profile;
    }

    /**
     * <p>
     * This method is a batch version of {@link #getProfile(String, String)}.
     * </p>
     *
     * <p>
     * Retrieves all the <code>ChatUserProfile</code> instances using the given user names and
     * the type.
     * </p>
     *
     * <p>
     * Note, if there is no <code>ProfileKey</code> for any username and the type, then the
     * corresponding <code>ProfileKey</code> instance will be created in
     * <code>ProfileKeyManager</code>.
     * </p>
     *
     * @param usernames An non-null array of username strings.
     *  Each element must be non-null and non-empty (trimmed).
     * @param type A non-null type indicating the data source from which to load the usernames.
     * @return An array of ChatUserProfile instances from the datasource
     * specified by type.  Please see the method docs for more info about the return format.
     *
     * @throws IllegalArgumentException if usernames or type are null or if any element
     * of usernames is null or empty (trimmed), or it has zero element
     * @throws ProfileNotFoundException if the relevant profile could not be located
     * within the persistence as described in the method docs.
     * @throws ChatUserProfilePersistenceException if there is an error in the persistence
     * unrelated to whether or not a profile could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to
     * connect to the database or a file that could not be found.
     * @throws ProfileKeyManagerPersistenceException if there is an error in the persistence
     * unrelated to whether or not a ProfileKey could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to
     * connect to the database or a file that could not be found.
     * @throws UnrecognizedDataSourceTypeException if the data source type does not
     * exist in this manager.
     */
    public ChatUserProfile[] getProfiles(String[] usernames, String type) throws ProfileNotFoundException,
        ChatUserProfilePersistenceException, ProfileKeyManagerPersistenceException, UnrecognizedDataSourceTypeException {

        // Get all the ProfileKey instances for the given usernames, if there is no ProfileKey instance for
        // any user name and the type, then the corresponding ProfileKey instance will be created in
        // the ProfileKeyManager instance
        ProfileKey[] keys;
        synchronized (this.keyManager) {
            keys = this.keyManager.getProfileKeys(usernames, type);
            if (keys == null) {
                keys = new ProfileKey[usernames.length];
            }

            for (int i = 0; i < usernames.length; i++) {
                if (keys[i] == null) {
                    try {
                        keys[i] = this.keyManager.createProfileKey(new ProfileKey(usernames[i], type));
                    } catch (DuplicateProfileKeyException e) {
                        // this will not happen because we have verified it before calling the create method
                        // for safe, the getProfileKey(String, String) is called again for it
                        keys[i] = this.keyManager.getProfileKey(usernames[i], type);
                    }
                }
            }
        }

        ChatUserProfilePersistence persistence = getChatUserProfilePersistence(type);
        ChatUserProfile[] profiles = persistence.getProfiles(usernames);
        for (int i = 0; i < usernames.length; i++) {
            if (profiles[i] != null) {
                profiles[i].setProfileKey(keys[i]);
            }
        }

        return profiles;
    }

    /**
     * <p>
     * This method is responsible for searching for all profiles whose properties
     * match all the properties of the given criteria in the <code>Map</code>.
     * </p>
     *
     * <p>
     * This method only performs the search on users registered in <code>ProfileKeyManager</code>,
     * If at least one property cannot be found for a user (or in the persistence at all),
     * then no result will be returned for that user.
     * </p>
     *
     * @param criteria A non-null Map composed of non-null String keys and non-null
     * String elements (or non-null List elements with non-null Strings as values).
     * @return An array of ChatUserProfile instances corresponding to the users found
     * in the persistence matching the specified criteria.
     *
     * @throws IllegalArgumentException if criteria does not meet the requirements
     * specified in the docs for that argument (above).
     * @throws ChatUserProfilePersistenceException if there is an error in the
     * persistence unrelated to whether or not a profile could or could not be
     * found in the persistence.  This is generally used for failures in the
     * persistence, such as the inability to connect to the database or a file
     * that could not be found.
     * @throws ProfileKeyManagerPersistenceException if there is an error in
     * the persistence unrelated to whether or not a ProfileKey could or could
     * not be found in the persistence.  This is generally used for failures
     * in the persistence, such as the inability to connect to the database
     * or a file that could not be found.
     */
    public ChatUserProfile[] searchProfiles(Map criteria) throws ChatUserProfilePersistenceException,
        ProfileKeyManagerPersistenceException {
        Set keySet = this.dataSources.keySet();
        String[] types = (String[]) keySet.toArray(new String[keySet.size()]);

        // get all the profile keys for the all the types in this manager
        ProfileKey[][] keys;
        try {
            synchronized (keyManager) {
                keys = this.keyManager.getProfileKeys(types);
            }
        } catch (ProfileKeyNotFoundException e) {
            // this exception is thrown when no profile keys can be found for all the types
            return new ChatUserProfile[0];
        }

        List result = new ArrayList();

        for (int i = 0; i < types.length; i++) {
            // the type at position i is unknown
            if (keys[i] == null) {
                continue;
            }

            ChatUserProfilePersistence persistence = (ChatUserProfilePersistence) this.dataSources.get(types[i]);

            Map mapping = new HashMap();

            // save the user name and profile key mappings
            for (int j = 0; j < keys[i].length; j++) {
                mapping.put(keys[i][j].getUsername(), keys[i][j]);
            }

            String[] usernames = (String[]) mapping.keySet().toArray(new String[keys[i].length]);

            // delegate the search to the persistence
            ChatUserProfile[] profiles = persistence.searchProfiles(criteria, usernames);

            // update the profile key for each profiles
            for (int k = 0; k < profiles.length; k++) {
                profiles[k].setProfileKey((ProfileKey) mapping.get(profiles[k].getUsername()));
                result.add(profiles[k]);
            }
        }

        return (ChatUserProfile[]) result.toArray(new ChatUserProfile[result.size()]);
    }

    /**
     * <p>
     * The method generates a <code>ProfileKey</code> for a given (username, type) pair.
     * in <code>ProfileKeyManager</code>.
     * </p>
     *
     * <p>
     * This is provided in case a user is unregistered in <code>ProfileKeyManager</code>
     * but exists in the data source.
     * </p>
     *
     * <p>
     * This gives the user an explicit way to generate a ProfileKey in the persistence for
     * the given (username, type) pair.
     * </p>
     *
     * @param username A non-null, non-empty username string to add.
     * @param type A non-null service type to add.
     *
     * @throws IllegalArgumentException if username is null or empty (trimmed) or if type is null.
     * @throws DuplicateProfileKeyException if the (username, type) pair already exists in the persistence.
     * @throws ProfileKeyManagerPersistenceException if there is an error in the persistence
     * unrelated to whether or not a ProfileKey could or could not be found in the persistence.
     * This is generally used for failures in the persistence, such as the inability to connect
     * to the database or a file that could not be found.
     * @throws UnrecognizedDataSourceTypeException if the data source type does not exist in this manager.
     */
    public void generateProfileKey(String username, String type) throws DuplicateProfileKeyException,
        ProfileKeyManagerPersistenceException, UnrecognizedDataSourceTypeException {
        Util.checkNull(type, "type");

        if (!dataSources.containsKey(type)) {
            throw new UnrecognizedDataSourceTypeException("The data source type [" + type
                + "] does not exist in this manager");
        }

        synchronized (keyManager) {
            this.keyManager.createProfileKey(new ProfileKey(username, type));
        }
    }

}
