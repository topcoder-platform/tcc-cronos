/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.ejb;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.orpheus.user.persistence.impl.Admin;
import com.orpheus.user.persistence.impl.ContactInfo;
import com.orpheus.user.persistence.impl.Player;
import com.orpheus.user.persistence.impl.Sponsor;
import com.orpheus.user.persistence.impl.UserProfileTranslator;

/**
 * <p>
 * A serializable data transfer object that is used to transfer the information
 * in the <code>UserProfile</code> class between the EJB client and the EJB
 * session bean.
 * </p>
 * <p>
 * This class stores its properties by mapping keys to information objects. The
 * keys should be one of the constants defined in this class. The value should
 * be an information object, such as a <code>Player</code>,
 * <code>Admin</code>, <code>Sponsor</code> or <code>ContactInfo</code>
 * instance.
 * </p>
 * <p>
 * The {@link UserProfileTranslator} can be used to convert this class to the
 * corresponding <code>UserProfile</code> instance and vice-versa.
 * </p>
 * <p>
 * <b>Thread-safety:</b><br> This class is mutable and, thus, not thread-safe.
 * </p>
 *
 * @author argolite, mpaulse
 * @version 1.0
 * @see UserProfileTranslator
 */
public class UserProfileDTO implements Serializable {

    /**
     * <p>
     * Represents the key which maps to a {@link Player} object.
     * </p>
     */
    public static final String PLAYER_KEY = "player";

    /**
     * <p>
     * Represents the key which maps to an {@link Admin} object.
     * </p>
     */
    public static final String ADMIN_KEY = "admin";

    /**
     * <p>
     * Represents the key which maps to a {@link Sponsor} object.
     * </p>
     */
    public static final String SPONSOR_KEY = "sponsor";

    /**
     * <p>
     * Represents the key which maps to a {@link ContactInfo} object.
     * </p>
     */
    public static final String CONTACT_INFO_KEY = "contact_info";

    /**
     * <p>
     * A map of information objects that are associated to profile types, The
     * keys should be one of the constants defined in this class. The value
     * should be an information object, such as a <code>Player</code>,
     * <code>Admin</code>, <code>Sponsor</code> or <code>ContactInfo</code>
     * instance.
     * </p>
     * <p>
     * This field is set to an empty HashMap when this class is constructed.
     * Entries may be inserted and retrieved using the put(String, Object) and
     * get(String) methods, respectively. The contains(String) method can be
     * used to check if the map contains an entry.
     * </p>
     */
    private final Map profileTypes = new HashMap();

    /**
     * <p>
     * Creates a new <code>UserProfileDTO</code> instance.
     * </p>
     */
    public UserProfileDTO() {
        // Empty constructor.
    }

    /**
     * <p>
     * Puts the given value with the specified key in the user profile DTO. The
     * key should be one of the constants defined in this class. The value
     * should be an information object, such as a <code>Player</code>,
     * <code>Admin</code>, <code>Sponsor</code> or <code>ContactInfo</code>
     * instance.
     * </p>
     * <p>
     * This method does not validate the argument values.
     * </p>
     *
     * @param key the key which maps to the information object
     * @param value the information object
     * @see #get(String)
     */
    public void put(String key, Object value) {
        profileTypes.put(key, value);
    }

    /**
     * <p>
     * Gets the value which to which the specified key maps. The key should be
     * one of the constants defined in this class. The value will usually be an
     * information object, such as a <code>Player</code>, <code>Admin</code>,
     * <code>Sponsor</code> or <code>ContactInfo</code> instance.
     * </p>
     * <p>
     * This method does not validate the argument value.
     * </p>
     *
     * @param key the key which maps to the information object
     * @return the information object
     * @see #put(String, Object)
     * @see #contains(String)
     */
    public Object get(String key) {
        return profileTypes.get(key);
    }

    /**
     * <p>
     * Returns whether the user profile DTO contains a value corresponding to
     * the specified key. The key should be one of the constants defined in this
     * class. The value will usually be an information object, such as a
     * <code>Player</code>, <code>Admin</code>, <code>Sponsor</code> or
     * <code>ContactInfo</code> instance.
     * </p>
     * <p>
     * This method does not validate the argument value.
     * </p>
     *
     * @param key the key which maps to the information object
     * @return <code>true</code> if the key maps to an information object in
     *         the user profile DTO; <code>false</code> otherwise
     * @see #get(String)
     */
    public boolean contains(String key) {
        return profileTypes.containsKey(key);
    }

}
