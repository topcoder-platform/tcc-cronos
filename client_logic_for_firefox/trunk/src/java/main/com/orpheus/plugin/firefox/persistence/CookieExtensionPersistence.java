/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox.persistence;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;

import netscape.javascript.JSObject;

import com.orpheus.plugin.firefox.ClientLogicForFirefoxHelper;
import com.orpheus.plugin.firefox.ExtensionPersistence;
import com.orpheus.plugin.firefox.FirefoxExtensionPersistenceException;


/**
 * <p>
 * The default implementation of the {@link ExtensionPersistence} interface of this component to save and retrieve the
 * values from a cookie saved on the client side. This class sets four cookie values: the feed timestamp; the sequence
 * number; the working game ID; and the current target ID.
 * </p>
 *
 * <p>
 * The values should be saved in a cookie that looks like this: TIMESTAMP=&lt;timestamp
 * value&gt;;WORKING_GAME_ID=&lt;working game ID&gt;;CURRENT_TARGET_ID=&lt;current target
 * ID&gt;;SEQUENCE_NUMBER=&lt;sequence number&gt;. When setting and retrieving these values, care should be taken to
 * preserve existing values so that changing one value doesn't cause the loss or corruption of one of the other two
 * values.
 * </p>
 *
 * <p>
 * This class parses out the four values in the {@link ExtensionPersistence#setClientWindow(JSObject)} method and then
 * sets them through the <code>setCookieString</code> method. The accessors and setters just have to use the member
 * variables parsed, with the setters just calling <code>setCookieString</code> after the value has been set.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is mutable, as the member variable values change after instantiation.
 * </p>
 *
 * @author Ghostar, TCSDEVELOPER
 * @version 1.0
 */
public class CookieExtensionPersistence implements ExtensionPersistence {
    /** Represents the cookie name to store the information of the timestamp. */
    private static final String TIMESTAMP = "TIMESTAMP";

    /** Represents the cookie name to store the information of the ID of the working game. */
    private static final String WORKING_GAME_ID = "WORKING_GAME_ID";

    /** Represents the cookie name to store the information of the ID string of the current target. */
    private static final String CURRENT_TARGET_ID = "CURRENT_TARGET_ID";

    /** Represents the cookie name to store the information of the persisted sequence number. */
    private static final String SEQUENCE_NUMBER = "SEQUENCE_NUMBER";

    /**
     * <p>
     * Represents the reference to the window the JavaScript code is running in. We use this value to evaluate
     * JavaScript calls that allow us to retrieve and set the document cookie property value.
     * </p>
     *
     * <p>
     * This value is initially null and is set through the {@link ExtensionPersistence#setClientWindow(JSObject)}
     * method. This value cannot be set to null.
     * </p>
     */
    private JSObject clientWindow = null;

    /**
     * <p>
     * Represents the timestamp of the latest feed read from the Orpheus server. Default value will be null.
     * </p>
     *
     * <p>
     * This value is parsed from the cookie retrieved in the {@link ExtensionPersistence#setClientWindow(JSObject)}
     * method. It is also set through {@link ExtensionPersistence#setFeedTimestamp(Calendar)} method which will update
     * the value in the cookie at the same time. Its value can be retrieved through method {@link
     * ExtensionPersistence#getFeedTimestamp()}.
     * </p>
     */
    private Calendar timestamp = null;

    /**
     * <p>
     * Represents the ID of the working game. Default value will be -1.
     * </p>
     *
     * <p>
     * This value is parsed from the cookie retrieved in the {@link ExtensionPersistence#setClientWindow(JSObject)}
     * method. It is also set through {@link ExtensionPersistence#setWorkingGameID(long)} method which will update the
     * value in the cookie at the same time. Its value can be retrieved through method {@link
     * ExtensionPersistence#getWorkingGameID()}.
     * </p>
     */
    private long workingGameID = -1;

    /**
     * <p>
     * Represents the ID string of the current target, currently a 40 character SHA-1 hash. Default value will be null.
     * </p>
     *
     * <p>
     * This value is parsed from the cookie retrieved in the {@link ExtensionPersistence#setClientWindow(JSObject)}
     * method. It is also set through {@link ExtensionPersistence#setCurrentTargetID(String, int)} method which will
     * update the value in the cookie at the same time. Its value can be retrieved through method {@link
     * ExtensionPersistence#getCurrentTargetID()}.
     * </p>
     */
    private String currentTargetID = null;

    /**
     * <p>
     * Represents the persisted sequence number. This number represents the current problem the user is working on in
     * the working game. Default value will be -1.
     * </p>
     *
     * <p>
     * This value is parsed from the cookie retrieved in the {@link ExtensionPersistence#setClientWindow(JSObject)}
     * method. It is also set through {@link ExtensionPersistence#setCurrentTargetID(String, int)} method which will
     * update the value in the cookie at the same time. Its value can be retrieved through method {@link
     * ExtensionPersistence#getSequenceNumber()}.
     * </p>
     */
    private int sequenceNumber = -1;

    /**
     * <p>
     * This empty constructor does nothing. Method of {@link ExtensionPersistence#setClientWindow(JSObject)} should be
     * called before any other methods to ensure the class has something to work with when setting cookies.
     * </p>
     */
    public CookieExtensionPersistence() {
    }

    /**
     * <p>
     * This method sets the timestamp of the latest feed from the Orpheus server. The value of the timestamp member
     * variable is updated to the value of the date given. The value in the cookie is also updated.
     * </p>
     *
     * @param timestamp The new timestamp of the Orpheus server feed.
     *
     * @throws IllegalArgumentException if the given value is null.
     * @throws FirefoxExtensionPersistenceException if errors occur while saving the value in the persistent store.
     */
    public void setFeedTimestamp(Calendar timestamp) throws FirefoxExtensionPersistenceException {
        ClientLogicForFirefoxHelper.validateNotNull(timestamp, "timestamp");
        this.timestamp = timestamp;
        setCookieString();
    }

    /**
     * <p>
     * This method returns the timestamp of the latest feed retrieved from the Orpheus server. It will return null if
     * the value hasn't been set yet and didn't exist in the cookie.
     * </p>
     *
     * @return A Calendar instance representing the last time the feed was retrieved.
     */
    public Calendar getFeedTimestamp() {
        return timestamp;
    }

    /**
     * <p>
     * This method sets the ID of the working game. The value in the cookie is also updated.
     * </p>
     *
     * @param id The ID of the new working game.
     *
     * @throws IllegalArgumentException if the given value is negative.
     * @throws FirefoxExtensionPersistenceException if errors occur while saving the value in the persistent store.
     */
    public void setWorkingGameID(long id) throws FirefoxExtensionPersistenceException {
        ClientLogicForFirefoxHelper.validateNotNegative(id, "id");
        this.workingGameID = id;
        setCookieString();
    }

    /**
     * <p>
     * This method returns the ID of the working game, as read from the persistent store implementation. It will return
     * -1 if the value hasn't been set yet and didn't exist in the cookie.
     * </p>
     *
     * @return The ID of the working game.
     */
    public long getWorkingGameID() {
        return workingGameID;
    }

    /**
     * <p>
     * This method sets the target ID to the value of the string given, in the cookie. The string given will be a 40
     * character SHA-1 hash of the current target identifier, but we only validate that the string is not null or
     * empty, to accommodate future formats for the identifier. The value in the cookie is also updated.
     * </p>
     *
     * @param id The string that represents the ID fo the current target.
     * @param sequenceNumber The sequence number to use.
     *
     * @throws IllegalArgumentException if the given value is null or an empty string, or if the sequence number is
     *         negative.
     * @throws FirefoxExtensionPersistenceException if errors occur while saving the value in the persistent store.
     */
    public void setCurrentTargetID(String id, int sequenceNumber) throws FirefoxExtensionPersistenceException {
        ClientLogicForFirefoxHelper.validateString(id, "id");
        ClientLogicForFirefoxHelper.validateNotNegative(sequenceNumber, "sequenceNumber");
        this.currentTargetID = id;
        this.sequenceNumber = sequenceNumber;
        setCookieString();
    }

    /**
     * <p>
     * This method retrieves the current target ID from the cookie. It will return null if the value hasn't been set
     * yet and didn't exist in the cookie.
     * </p>
     *
     * @return The current target ID string.
     */
    public String getCurrentTargetID() {
        return currentTargetID;
    }

    /**
     * <p>
     * This method returns the sequence number of the current target, as read from the persistent store implementation.
     * It will return -1 if the value hasn't been set yet and didn't exist in the cookie.
     * </p>
     *
     * @return The persisted sequence number.
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * <p>
     * This method sets the value of the "clientWindow" member variable to the value of the parameter given. This
     * method should also get the cookie value from the window handle given, through JavaScript. The cookie retrieved
     * should be parsed and the values of the member variables should be set to the values retrieved. The values
     * should be saved in a cookie that looks like this: TIMESTAMP=&lt;timestamp value&gt;;WORKING_GAME_ID=&lt;working
     * game ID&gt;;CURRENT_TARGET_ID=&lt;current target ID&gt;;SEQUENCE_NUMBER=&lt;sequence number&gt;.
     * </p>
     *
     * <p>
     * If any values are missing from the cookie string, the corresponding member variable value should be left at its
     * default. The timestamp value should be parsed from the serialized calendar string given.
     * </p>
     *
     * @param clientWindow The JavaScript window object to use when accessing the browser window.
     *
     * @throws IllegalArgumentException if the given value is null.
     * @throws FirefoxExtensionPersistenceException if errors occur while reading the cookie.
     */
    public void setClientWindow(JSObject clientWindow) throws FirefoxExtensionPersistenceException {
        ClientLogicForFirefoxHelper.validateNotNull(clientWindow, "clientWindow");
        this.clientWindow = clientWindow;

        // set to their initial values
        timestamp = null;
        workingGameID = -1;
        currentTargetID = null;
        sequenceNumber = -1;

        String[] cookieValues = ((JSObject) clientWindow.getMember("document")).
            getMember("cookie").toString().split(";");

        // get the real values from the document.cookie
        for (int i = 0; i < cookieValues.length; i++) {
            String[] parts = cookieValues[i].trim().split("=");

            if (parts.length < 2) {
                continue;
            }

            try {
                if (parts[0].equals(TIMESTAMP)) {
                    Object obj = parseSerializedObject(URLDecoder.decode(parts[1],
                            ClientLogicForFirefoxHelper.DEFAULT_URL_ENCODING));

                    if (!(obj instanceof Calendar)) {
                        throw new FirefoxExtensionPersistenceException(
                            "The serialized object is not instanceof Calendar.");
                    }

                    timestamp = (Calendar) obj;
                } else if (parts[0].equals(WORKING_GAME_ID)) {
                    workingGameID = Long.parseLong(parts[1]);
                } else if (parts[0].equals(CURRENT_TARGET_ID)) {
                    currentTargetID = URLDecoder.decode(parts[1], ClientLogicForFirefoxHelper.DEFAULT_URL_ENCODING);
                } else if (parts[0].equals(SEQUENCE_NUMBER)) {
                    sequenceNumber = Integer.parseInt(parts[1]);
                }
            } catch (NumberFormatException e) {
                throw new FirefoxExtensionPersistenceException("The cookie value under " + parts[0]
                    + " is not well formated as an integer.", e);
            } catch (UnsupportedEncodingException e) {
                throw new FirefoxExtensionPersistenceException("The cookie value under " + parts[0]
                    + " can not be decoded via " + ClientLogicForFirefoxHelper.DEFAULT_URL_ENCODING + ".", e);
            }
        }
    }

    /**
     * <p>
     * This method sets the cookie value to a string created with the timestamp, workingGameID, sequence number, and
     * currentTargetID member variable values in the following format: TIMESTAMP=&lt;timestamp
     * value&gt;;WORKING_GAME_ID=&lt;working game ID&gt;;CURRENT_TARGET_ID=&lt;current target
     * ID&gt;;SEQUENCE_NUMBER=&lt;sequence number&gt;. The path of the cookie should be "/", allowing all pages to
     * access the cookie. With the path added, the entire string set should look like this: TIMESTAMP=&lt;timestamp
     * value&gt;;WORKING_GAME_ID=&lt;working game ID&gt;;CURRENT_TARGET_ID=&lt;current target
     * ID&gt;;SEQUENCE_NUMBER=&lt;sequence number&gt;path=/;
     * </p>
     *
     * <p>
     * If any value is missing, for instance if "timestamp" is null, it should be omitted from the cookie string. The
     * calendar string written for the TIMESTAMP value should be able to be deserialized to the same timestamp
     * written.
     * </p>
     *
     * @throws FirefoxExtensionPersistenceException if errors occur while setting the cookie.
     */
    private void setCookieString() throws FirefoxExtensionPersistenceException {
        if (clientWindow == null) {
            return;
        }

        String timeStampStr = null;

        if (timestamp != null) {
            timeStampStr = getSerializedRepresentation(timestamp);
        }

        if (timeStampStr != null) {
            try {
                ((JSObject) clientWindow.getMember("document")).setMember("cookie", TIMESTAMP + "="
                    + URLEncoder.encode(timeStampStr, ClientLogicForFirefoxHelper.DEFAULT_URL_ENCODING) + ";path=/;");
            } catch (UnsupportedEncodingException e) {
                throw new FirefoxExtensionPersistenceException("UTF-8 encoding problem for timestamp.", e);
            }
        }

        if (workingGameID != -1) {
            ((JSObject) clientWindow.getMember("document")).setMember("cookie", WORKING_GAME_ID + "="
                + workingGameID + ";path=/;");
        }

        if (currentTargetID != null) {
            try {
                ((JSObject) clientWindow.getMember("document")).setMember("cookie", CURRENT_TARGET_ID + "="
                    + URLEncoder.encode(currentTargetID, "UTF-8") + ";path=/;");
            } catch (UnsupportedEncodingException e) {
                throw new FirefoxExtensionPersistenceException("UTF-8 encoding problem for currentTargetID.", e);
            }
        }

        if (sequenceNumber != -1) {
            ((JSObject) clientWindow.getMember("document")).setMember("cookie", SEQUENCE_NUMBER + "="
                    + sequenceNumber + ";path=/;");
        }
    }

    /**
     * <p>
     * Gets the object from the serialized representation(in String type).
     * </p>
     *
     * <p>
     * Note: the "ISO-8859-1" is the desired "BINARY" charset and it provides a 1-1 mapping between bytes and chars
     * less than 0x100.
     * </p>
     *
     * @param serialized the serialized representation(in String type) to get the object.
     *
     * @return the object from the serialized representation.
     *
     * @throws FirefoxExtensionPersistenceException if it fails to get the object from the serialized representation.
     */
    private Object parseSerializedObject(String serialized) throws FirefoxExtensionPersistenceException {
        try {
            if (serialized == null) {
                return null;
            }

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serialized.getBytes("ISO-8859-1"));
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

            return objectInputStream.readObject();
        } catch (IOException e) {
            throw new FirefoxExtensionPersistenceException("Error happens in the writing serialized bytes process.", e);
        } catch (ClassNotFoundException e) {
            throw new FirefoxExtensionPersistenceException("Error happens to the retrieve the expected class.", e);
        }
    }

    /**
     * <p>
     * Gets the serialized representation from the given object.
     * </p>
     *
     * <p>
     * Note: the "ISO-8859-1" is the desired "BINARY" charset and it provides a 1-1 mapping between bytes and chars
     * less than 0x100.
     * </p>
     *
     * @param object the given object to get the serialized representation.
     *
     * @return the serialized representation(in String type) from the given object.
     *
     * @throws FirefoxExtensionPersistenceException if it fails to get the serialized representation from the given
     *         object.
     */
    private String getSerializedRepresentation(Object object) throws FirefoxExtensionPersistenceException {
        try {
            if (object == null) {
                return null;
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);

            return new String(byteArrayOutputStream.toByteArray(), "ISO-8859-1");
        } catch (IOException e) {
            throw new FirefoxExtensionPersistenceException("Error happens in the writing "
                + "serialized representation process.", e);
        }
    }
}
