/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.impl;

import com.orpheus.user.persistence.UserConstants;
import com.orpheus.user.persistence.UserProfileDAO;
import com.orpheus.user.persistence.ejb.UserProfileDTO;

import java.io.Serializable;

/**
 * <p>
 * Stores the player preferences information. This class corresponds to the
 * {@link UserConstants#PREFERENCES_TYPE_NAME} and <code>BaseProfileType.BASE_NAME</code> user profile types. It is
 * wrapped inside {@link UserProfileDTO} objects using the {@link UserProfileDTO#PREFERENCES_INFO_KEY} key, and
 * transported between the EJB client and the {@link UserProfileDAO} to facilitate the persistence of the player
 * preferences information.
 * </p>
 * <p>
 * This class stores the following information. The constants shown in brackets
 * are the names of the corresponding properties in a <code>UserProfile</code>
 * object.
 * </p>
 * <ul>
 * <li>the preferences information ID</li>
 * <li>the selected option for sound to be played when an event is triggered {@link UserConstants#PREFS_SOUND})</li>
 * <li>the selected option for receiving general notifications ({@link UserConstants#PREFS_GENERAL_NOTIFICATION})</li>
 * </ul>
 * <p>
 * <b>Thread-safety:</b><br> This class is mutable and, thus, not thread-safe.
 * </p>
 *
 * @author isv
 * @version 1.0
 * @see UserProfileDTO
 */
public class PlayerPreferencesInfo implements Serializable {

    /**
     * <p>
     * The preferences information ID.
     * </p>
     * <p>
     * This field is set and accessed in the setId(long) and getId()
     * methods, respectively It can be any value.
     * </p>
     */
    private long id;

    /**
     * <p>
     * The selected sound option.
     * </p>
     * <p>
     * This field is set and accessed in the setSoundOption(int) and getSoundOption()
     * methods, respectively It can be any value.
     * </p>
     */
    private int soundOption;

    /**
     * <p>
     * The flag indicating whether the player has opt-in or opt-out receiving general notifications.
     * </p>
     * <p>
     * This field is set and accessed in the setId(long) and getId()
     * methods, respectively It can be any value.
     * </p>
     */
    private boolean generalNotificationsOptIn;

    /**
     * <p>
     * Creates a new <code>PlayerPreferencesInfo</code> object with the specified ID.
     * </p>
     *
     * @param id the ID of the contact information
     */
    public PlayerPreferencesInfo(long id) {
        setId(id);
    }

    /**
     * <p>
     * Gets the ID of the preferences information.
     * </p>
     *
     * @return the ID of the preferences information
     * @see #setId(long)
     */
    public long getId() {
        return id;
    }

    /**
     * <p>
     * Sets the ID of the preferences information.
     * </p>
     *
     * @param id the ID of the preferences information
     * @see #getId()
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>
     * Gets the sound option selected by the player.
     * </p>
     *
     * @return the selected sound option
     * @see #setSoundOption(int)
     */
    public int getSoundOption() {
        return soundOption;
    }

    /**
     * <p>
     * Sets the sound option selected by the player.
     * </p>
     *
     * @param soundOption the selected sound option or -1 if player has selected no sound
     * @see #getSoundOption()
     */
    public void setSoundOption(int soundOption) {
        this.soundOption = soundOption;
    }

    /**
     * <p>
     * Gets the flag indicating whether the player has opted-in or opted-out receiving general notifications.
     * </p>
     *
     * @return <code>true</code> if player has chosen to opt-in; <code>false</code> otherwise.
     * @see #setGeneralNotificationsOptIn(boolean)
     */
    public boolean getGeneralNotificationsOptIn() {
        return generalNotificationsOptIn;
    }

    /**
     * <p>
     * Sets the flag indicating whether the player has opted-in or opted-out receiving general notifications.
     * </p>
     *
     * @param generalNotificationsOptIn <code>true</code> if player has chosen to opt-in; <code>false</code> otherwise.
     * @see #getGeneralNotificationsOptIn()
     */
    public void setGeneralNotificationsOptIn(boolean generalNotificationsOptIn) {
        this.generalNotificationsOptIn = generalNotificationsOptIn;
    }
}
