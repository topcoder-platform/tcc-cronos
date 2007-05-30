/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>
 * This is the message to ask a user for chatting in a session created by another user.
 * The sender of this message must be the system. Besides the attributes defined by
 * <c>XMLMessage</c>, it includes:
 * <ul>
 * <li>User profile attributes of the person who created the chat message.</li>
 * <li>Timestamp of when the session is created.</li>
 * <li>Timestamp of which the user needs to acknowledge.</li>
 * </ul>
 * </p>
 * <p>
 * <b>Thread safety</b>: This class is not thread safe since it’s mutable.
 * </p>
 *
 * @author woodjhon, marius_neo
 * @version 1.0
 */
public class AskForChatMessage extends XMLMessage {

    /**
     * The user profile properties of the person who created the chat message.
     * It is set and accessed by set/get methods corresponding to field's name.
     * The keys and values must be non-null and non-empty strings.
     */
    private Map authorProfileProperties;

    /**
     * Represents the session creation time.It is set and accessed by set/get methods
     * corresponding to field's name.The valid value are non-null.
     */
    private Date sessionCreationTime;

    /**
     * Represents the acknowledge time.It is set and accessed by set/get methods
     * corresponding to field's name.The valid values are non-null.
     */
    private Date acknowledgeTime;

    /**
     * Represents the request user id. It is set and accessed by set/get methods
     * corresponding to field's name. The valid values for this field are positive.
     */
    private long requestUserId;

    /**
     * Creates a new instance of the class.
     */
    public AskForChatMessage() {
    }

    /**
     * Get the properties of the profile  who creates this message.
     *
     * @return the profile properties
     */
    public Map getAuthorProfileProperties() {
        return authorProfileProperties;
    }

    /**
     * Set the properties of the author profile who created the message.
     *
     * @param properties the profile properties
     * @throws IllegalArgumentException If argument is null, empty, contains null or empty key
     *                                  or contains null or empty value.
     */
    public void setAuthorProfileProperties(Map properties) {
        Helper.validateProfileProperties(properties, "properties");
        authorProfileProperties = properties;
    }

    /**
     * Get the session creation time.
     *
     * @return The session's creation time.
     */
    public Date getSessionCreationTime() {
        return new Date(sessionCreationTime.getTime());
    }

    /**
     * Set the session creation time.
     *
     * @param time the session creation time
     * @throws IllegalArgumentException If the specified argument is null.
     */
    public void setSessionCreationTime(Date time) {
        Helper.validateNotNull(time, "time");
        sessionCreationTime = new Date(time.getTime());
    }

    /**
     * Get the session acknowledge time.
     *
     * @return The session's acknowledge time.
     */
    public Date getAcknowledgeTime() {
        return new Date(acknowledgeTime.getTime());
    }

    /**
     * Set the session acknowledge time.
     *
     * @param time The session acknowledge time.
     * @throws IllegalArgumentException If the specified argument is null.
     */
    public void setAcknowledgeTime(Date time) {
        Helper.validateNotNull(time, "time");
        acknowledgeTime = new Date(time.getTime());
    }

    /**
     * Retrieves the request user id.
     *
     * @return The requested user id.
     */
    public long getRequestUserId() {
        return requestUserId;
    }

    /**
     * Sets the request user id.
     *
     * @param requestUserId The new request user id.
     * @throws IllegalArgumentException if <c>requestUserId</c> is non positive.
     */
    public void setRequestUserId(long requestUserId) {
        if (requestUserId <= 0) {
            throw new IllegalArgumentException("requestUserId parameter must be positive");
        }
        this.requestUserId = requestUserId;
    }

    /**
     * Return the xml representation of the message.
     * The xml string must follow the xsd scheme located in docs/AskForChatMessage.xsd.
     * If any required field is missing, an exception of type <c>IllegalStateException</c>
     * will be thrown.
     *
     * @param context Holder for the date format attributes like date pattern, time zone, locale, etc.
     * @return The xml string representation of this message.
     * @throws IllegalStateException          If any required field is missing.
     * @throws IllegalArgumentException       If argument is null.
     * @throws ChatMessageFormattingException Wraps any other exception that might appear during this operation.
     */
    public String toXMLString(DateFormatContext context)
        throws ChatMessageFormattingException {
        Helper.validateNotNull(context, "context");
        try {
            StringBuffer xmlStringBuffer = new StringBuffer();
            // Build the xml by respecting the structure defined in docs/AskForChatMessage.xsd
            xmlStringBuffer.append("<message type=\"AskForChatMessage\">");
            // Add to xmlStringBuffer the basic informations about this message.
            XMLMessageHelper.addXmlForBasicXMLMessage(xmlStringBuffer, context, this);

            // Add author profile properties
            xmlStringBuffer.append("<author_profile_properties>");
            if (authorProfileProperties == null) {
                throw new IllegalStateException("author profile properties are not set for the message");
            }
            for (Iterator i = authorProfileProperties.entrySet().iterator(); i.hasNext();) {
                Map.Entry entry = (Map.Entry) i.next();
                XMLMessageHelper.addXmlForAttribute(xmlStringBuffer
                    , (String) entry.getKey(), (String[]) entry.getValue());
            }
            xmlStringBuffer.append("</author_profile_properties>");

            // Add session creation time for the message
            xmlStringBuffer.append("<session_creation_time>");
            if (sessionCreationTime == null) {
                throw new IllegalStateException("The session creation time is missing for the message");
            }
            xmlStringBuffer.append(sessionCreationTime.getTime());
            xmlStringBuffer.append("</session_creation_time>");

            // Add acknowledge time for the message
            xmlStringBuffer.append("<acknowledge_time>");
            if (acknowledgeTime == null) {
                throw new IllegalStateException("The acknowledge time is missing for the message");
            } else {
                xmlStringBuffer.append(acknowledgeTime.getTime());
            }
            xmlStringBuffer.append("</acknowledge_time>");

            // Add acknowledge time for the message
            xmlStringBuffer.append("<request_user_id>");
            if (requestUserId == 0) {
                throw new IllegalStateException("The request user id is missing for the message");
            }
            xmlStringBuffer.append(requestUserId);
            xmlStringBuffer.append("</request_user_id>");

            xmlStringBuffer.append("</message>");
            return xmlStringBuffer.toString();
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            StringBuffer sbMessage = new StringBuffer();
            sbMessage.append("Exception occurred when building xml message: ").append(e.getMessage());
            throw new ChatMessageFormattingException(sbMessage.toString(), e, "");
        }
    }
}
