/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox;

import netscape.javascript.JSObject;

import java.util.Calendar;


/**
 * <p>
 * This interface defines the contract for persistence of properties used by this component, and indirectly the FireFox
 * extension that uses this component.
 * </p>
 *
 * <p>
 * As this code is run on the client side, the persistence implementations should save and read from an accessible
 * location. Because the code is run in a security sandbox, implementations need to be aware of the security
 * restrictions put on them. The current implementation provided is a mechanism that saves the values to cookies on
 * the client side, allowing the values to persist between browser restarts, provided the user doesn't clear out their
 * cookies.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>Implementations of this interface do not need to be thread safe.
 * </p>
 *
 * @author Ghostar, TCSDEVELOPER
 * @version 1.0
 */
public interface ExtensionPersistence {
    /**
     * <p>
     * Sets the timestamp of the latest feed from the Orpheus server. The value of the timestamp is updated to the
     * value of the date given.
     * </p>
     *
     * @param timestamp The time of the latest feed.
     *
     * @throws IllegalArgumentException if the given value is null.
     * @throws FirefoxExtensionPersistenceException if errors occur while saving the value in the persistent store.
     */
    void setFeedTimestamp(Calendar timestamp) throws FirefoxExtensionPersistenceException;

    /**
     * <p>
     * Gets the timestamp of the latest feed retrieved from the Orpheus server. Returns null if the value doesn't exist
     * in the persistent store.
     * </p>
     *
     * @return The latest feed timestamp.
     *
     * @throws FirefoxExtensionPersistenceException if errors occur reading from the persistent store.
     */
    Calendar getFeedTimestamp() throws FirefoxExtensionPersistenceException;

    /**
     * <p>
     * Sets the ID of the working game in the persistent store to the value given.
     * </p>
     *
     * @param id The ID of the current working game.
     *
     * @throws IllegalArgumentException if the given value is negative.
     * @throws FirefoxExtensionPersistenceException if errors occur while saving the value in the persistent store.
     */
    void setWorkingGameID(long id) throws FirefoxExtensionPersistenceException;

    /**
     * <p>
     * Gets the ID of the working game from the persistent store.
     * </p>
     *
     * @return The ID of the working game.
     *
     * @throws FirefoxExtensionPersistenceException if errors occur while retrieving the value in the persistent store.
     */
    long getWorkingGameID() throws FirefoxExtensionPersistenceException;

    /**
     * <p>
     * Sets the target ID to the value of the string given, in the persistent store implementation. The string given
     * will be a 40 character SHA-1 hash of the current target identifier, but we only validate that the string is not
     * null or empty.
     * </p>
     *
     * @param id The ID of the current target.
     * @param sequenceNumber The sequence number to use.
     *
     * @throws IllegalArgumentException if the given value is null or an empty string, or if the sequence number is
     *         negative.
     * @throws FirefoxExtensionPersistenceException if errors occur while saving the value in the persistent store.
     */
    void setCurrentTargetID(String id, int sequenceNumber) throws FirefoxExtensionPersistenceException;

    /**
     * <p>
     * Get the current target ID from the persistent store.
     * </p>
     *
     * @return The current target ID.
     *
     * @throws FirefoxExtensionPersistenceException if errors occur while retrieving the value in the persistent store.
     */
    String getCurrentTargetID() throws FirefoxExtensionPersistenceException;

    /**
     * <p>
     * Gets the current target ID sequence number from the persistent store.
     * </p>
     *
     * @return The current sequence number.
     *
     * @throws FirefoxExtensionPersistenceException if errors occur while retrieving the value in the persistent store.
     */
    int getSequenceNumber() throws FirefoxExtensionPersistenceException;

    /**
     * <p>
     * Sets the JSObject used to reference the page's Javascript "window" value.
     * </p>
     *
     * @param clientWindow The JSObject that points to the current Javascript "window" object.
     *
     * @throws IllegalArgumentException if the given value is null.
     * @throws FirefoxExtensionPersistenceException if errors occur while setting the value in the persistent store.
     */
    void setClientWindow(JSObject clientWindow) throws FirefoxExtensionPersistenceException;
}
