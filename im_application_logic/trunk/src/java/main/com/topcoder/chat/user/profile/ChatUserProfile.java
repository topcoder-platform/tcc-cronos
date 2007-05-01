/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.chat.user.profile;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * A <code>ChatUserProfile</code> is a simple data entity composed solely of a unique identifier
 * (either a (username, type) pair or a (username, type) pair and an id) and a set of dynamically
 * defined properties.
 * </p>
 *
 * <p>
 * Thread safety: This class is mutable and so is not thread-safe.
 * Generally speaking, this class should only be used for read-only access by multiple
 * threads or write-access should be managed via external synchronization, including
 * when operations are performed on an instance of <code>ChatUserProfile</code>.
 * </p>
 *
 * @author duner, TCSDEVELOPER
 * @version 2.0
 */
public class ChatUserProfile {
    /**
     * <p>
     * This value represents a ProfileKey that uniquely identifies a user on the system.
     * </p>
     *
     * <p>
     * A unique identification in this system is either a (username, type) pair or a long id.
     * </p>
     *
     * <p>
     * This class will only accept a ProfileKey instance that contains both the (username, type)
     * pair and long id (non-negative) but also has a constructor which accepts only a
     * (username, type) pair so that the user can construct and persist profiles on their own.
     * </p>
     *
     * <p>
     * This value is guaranteed to be non-null and contain all values of a
     * ProfileKey (namely: non-negative id, non-null, non-empty username and
     * non-null type except when created directly by the user with no id).
     * </p>
     *
     * <p>
     * It is set in the constructor and can be changed via the {@link ChatUserProfile#setProfileKey(ProfileKey)}
     * method.
     * </p>
     *
     * <p>
     * It is directly accessible via {@link ChatUserProfile#getProfileKey()} method
     * and its values are also accessible via {@link ChatUserProfile#getId()}, {@link ChatUserProfile#getUsername()},
     * and {@link ChatUserProfile#getType()}.
     * </p>
     */
    private ProfileKey key;

    /**
     * <p>
     * This value represents a mapping between property names and property values.
     * </p>
     *
     * <p>
     * The key is always a non-null, non-empty (trimmed) string. The value is a non-null
     * List with non-null String entries.
     * </p>
     *
     * <p>
     * This is set in the constructor to a Map instance and cannot be changed afterwards once set.
     * </p>
     *
     * <p>
     * It is never directly accessed, but its values and keys are accessed via the following methods
     * <ul>
     * <li>{@link ChatUserProfile#containsProperty(String)}<li>
     * <li>{@link ChatUserProfile#addProperty(String, String)}</li>
     * <li>{@link ChatUserProfile#setProperty(String, String)}</li>
     * <li>{@link ChatUserProfile#removeProperty(String)}</li>
     * <li>{@link ChatUserProfile#getPropertyValue(String)}</li>
     * <li>{@link ChatUserProfile#getPropertyNames()}</li>
     * <li>{@link ChatUserProfile#clearProperties()}</li>
     * </ul>
     * </p>
     */
    private final Map properties;

    /**
     * <p>
     * Initializes this instance of <code>ChatUserProfile</code>.
     * </p>
     *
     * @param key A non-null ProfileKey instance used to uniquely identify this profile.
     * It must conform to all rules of ProfileKey and is expected to have a non-negative id.
     *
     * @throws IllegalArgumentException if key is null or if the key's id is negative.
     */
    public ChatUserProfile(ProfileKey key) {
        Util.checkNull(key, "key");
        if (key.getId() < 0) {
            throw new IllegalArgumentException("The key's id [" + key.getId() + "] is negative.");
        }

        this.key = key;
        this.properties = new HashMap();
    }

    /**
     * <p>
     * Initializes this instance of <code>ChatUserProfile</code>.
     * </p>
     *
     * @param username A non-null, non-empty username that identifies a user within a given system.
     * @param type A non-null type that identifies the system context of the given the username.
     *
     * @throws IllegalArgumentException if username is null or empty (trimmed) or if type is null.
     */
    public ChatUserProfile(String username, String type) {
        this.key = new ProfileKey(username, type);
        this.properties = new HashMap();
    }

    /**
     * <p>
     * Gets the profile id of this <code>ChatUserProfile</code> instance.
     * </p>
     *
     * <p>
     * This will be -1 to indicate that no id has been set or non-negative to
     * indicate a valid id value.
     * </p>
     *
     * @return the profile id of this <code>ChatUserProfile</code> instance.
     */
    public long getId() {
        return this.key.getId();
    }

    /**
     * <p>
     * Gets the user name of this <code>ChatUserProfile</code> instance.
     * </p>
     *
     * <p>
     * This will be non-null and non-empty (trimmed).
     * </p>
     *
     * @return the user name of this <code>ChatUserProfile</code> instance.
     */
    public String getUsername() {
        return this.key.getUsername();
    }

    /**
     * <p>
     * Gets the type of this <code>ChatUserProfile</code> instance.
     * </p>
     *
     * <p>
     * This will be non-null.
     * </p>
     *
     * @return the type of this <code>ChatUserProfile</code> instance.
     */
    public String getType() {
        return this.key.getType();
    }

    /**
     * <p>
     * This method checks whether the property name specified by <code>propName</code>
     * is contained in this <code>ChatUserProfile</code> instance.
     * </p>
     *
     * @param propName The non-null, non-empty property name to check existence
     * in this <code>ChatUserProfile</code> instance.
     * @return true if propName is in this <code>ChatUserProfile</code> instance, false otherwise.
     *
     * @throws IllegalArgumentException if propName is null or empty (trimmed)
     */
    public boolean containsProperty(String propName) {
        Util.checkString(propName, "propName");

        return this.properties.containsKey(propName);
    }

    /**
     * <p>
     * Adds the property name specified by <code>propName</code> and the value specified by
     * <code>propValue</code> to this <code>ChatUserProfile</code> instance.
     * </p>
     *
     * <p>
     * Note, multiple values for a property name are allowed, but if duplicate property values
     * are added for a property name, then only one copy of the property values will be kept.
     * </p>
     *
     * @param propName A non-null, non-empty String representing the name of the property to add.
     * @param propValue A non-null String representing the value of this property.
     *
     * @throws IllegalArgumentException if propName is null or empty (trimmed) or
     * if propValue is null.
     */
    public void addProperty(String propName, String propValue) {
        Util.checkString(propName, "propName");
        Util.checkNull(propValue, "propValue");

        Set values = (Set) this.properties.get(propName);
        if (values == null) {
            values = new HashSet();
            this.properties.put(propName, values);
        }

        values.add(propValue);
    }

    /**
     * <p>
     * Sets the property name specified by <code>propName</code> to the property value specified by
     * <code>propValue</code>.
     * </p>
     *
     * <p>
     * This method replaces any value(s) currently in this <code>ChatUserProfile</code> whose property
     * name is <code>propName</code>.
     * </p>
     *
     * @param propName The non-null, non-empty property name whose value is to be set.
     * @param propValue The non-null property value corresponding to the property name.
     *
     * @throws IllegalArgumentException if propName is null or empty (trimmed) or if propValue is null.
     */
    public void setProperty(String propName, String propValue) {
        Util.checkString(propName, "propName");
        Util.checkNull(propValue, "propValue");

        Set values = new HashSet();
        values.add(propValue);

        this.properties.put(propName, values);
    }

    /**
     * <p>
     * Removes the property name specified by <code>propName</code> from this <code>ChatUserProfile</code>
     * instance.
     * </p>
     *
     * <p>
     * The method will return old values of the property, if there is no such property, an empty array
     * will be returned here.
     * </p>
     *
     * @param propName The non-null, non-empty property name which needs to be removed.
     * @return the old string values of the property. If the property did not exist,
     * an empty array will be returned.
     *
     * @throws IllegalArgumentException if propName is null or empty (trimmed).
     */
    public String[] removeProperty(String propName) {
        Util.checkString(propName, "propName");

        Set values = (Set) this.properties.remove(propName);
        return (String[]) ((values == null) ? new String[0] : values.toArray(new String[values.size()]));
    }

    /**
     * <p>
     * Gets the property values contained by this <code>ChatUserProfile</code> where the key is
     * specified by <code>propName</code>.
     * </p>
     *
     * <p>
     * If no such property name exists, an empty array will be returned.
     * </p>
     *
     * @param propName The non-null, non-empty property name which is to be retrieved.
     * @return String[] containing the values contained by this <code>ChatUserProfile</code>.
     * If the property did not exist, an empty array will be returned.
     *
     * @throws IllegalArgumentException if propName is null or empty (trimmed).
     */
    public String[] getPropertyValue(String propName) {
        Util.checkString(propName, "propName");

        Set values = (Set) this.properties.get(propName);
        return (String[]) ((values == null) ? new String[0] : values.toArray(new String[values.size()]));
    }

    /**
     * <p>
     * Gets all the property names in this <code>ChatUserProfile</code> instance.
     * </p>
     *
     * <p>
     * If no properties are currently in this <code>ChatUserProfile</code> instance, then an empty array
     * will be returned.
     * </p>
     *
     * @return A String[] containing all property names in this <code>ChatUserProfile</code> instance.
     */
    public String[] getPropertyNames() {
        Set keySet = this.properties.keySet();

        return (String[]) keySet.toArray(new String[keySet.size()]);
    }

    /**
     * <p>
     * Clears the map of all properties and values.
     * </p>
     */
    public void clearProperties() {
        this.properties.clear();
    }

    /**
     * <p>
     * Sets the <code>ProfileKey</code> instance of this <code>ChatUserProfile</code> instance.
     * </p>
     *
     * @param key The non-null <code>ProfileKey</code> instance that uniquely identifies this
     * <code>ChatUserProfile</code>.
     *
     * @throws IllegalArgumentException if key is null.
     */
    public void setProfileKey(ProfileKey key) {
        Util.checkNull(key, "key");

        this.key = key;
    }

    /**
     * <p>
     * Gets the <code>ProfileKey</code> instance associated with this <code>ChatUserProfile</code> instance.
     * </p>
     *
     * <p>
     * It Will not be null.
     * </p>
     *
     * @return the <code>ProfileKey</code> instance associated with this <code>ChatUserProfile</code> instance.
     */
    public ProfileKey getProfileKey() {
        return this.key;
    }

    /**
     * <p>
     * Determines if this instance is equal to the passed object.
     * </p>
     *
     * <p>
     * This returns true iff the following conditions are true:
     * </p>
     * <p>
     * <ol>
     * <li><code>obj</code> is non-null.</li>
     * <li><code>obj</code> is an instance of <code>ChatUserProfile</code>.</li>
     * <li>The type of this <code>ChatUserProfile</code> instance is same as the type of the given
     * <code>ChatUserProfile</code> instance.</li>
     * <li>The user name of this <code>ChatUserProfile</code> instance is same as the user name of
     * the given <code>ChatUserProfile</code> instance.</li>
     * </ol>
     * </p>
     *
     * <p>
     * If at least one of the above is not true, then the two objects are not equal, otherwise,
     * they are equal.
     * </p>
     *
     * @param obj An object to check for equality.
     * @return true if obj is equivalent, false if it is not.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof ChatUserProfile)) {
            return false;
        }

        // the object to compare is an instance of ChatUserProfile
        ChatUserProfile another = (ChatUserProfile) obj;

        // the type and user name must match
        return this.getType().equals(another.getType()) && this.getUsername().equals(another.getUsername());
    }

    /**
     * <p>
     * Returns the hash code for this particular <code>ChatUserProfile</code> instance.
     * </p>
     *
     * <p>
     * Since a <code>ChatUserProfile</code> instance is identified by the type and user name,
     * these two attributes are used to calculate the hash code.
     * </p>
     *
     * @return the hash code for this instance
     */
    public int hashCode() {
        return key.getUsername().hashCode() ^ key.getType().hashCode();
    }
}
