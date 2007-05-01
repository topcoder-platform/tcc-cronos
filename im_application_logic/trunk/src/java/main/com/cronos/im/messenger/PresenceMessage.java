/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger;

import java.util.Iterator;
import java.util.Map;

/**
 * <p>
 * This is the message to indicate if a user is present or absent. Besides the attributes defined by
 * <c>XMLMessage</c>, it includes:
 * <ul>
 * <li>User profile attributes of the person who changed the presence (only username is required in XML).</li>
 * <li>Absence / Presence</li>
 * </ul>
 * </p>
 * <p>
 * <b>Thread safety</b>: This class is not thread safe since it’s mutable.
 * </p>
 *
 * @author woodjhon, marius_neo
 * @version 1.0
 */
public class PresenceMessage extends XMLMessage {

    /**
     * This field is indicating if the user is present.
     * It is set and accessed by set/get methods corresponding to the field's name.
     */
    private boolean present;

    /**
     * The user profile properties of the person who changed the presence.
     * It is set and accessed by set/get methods corresponding to the field's name.
     * The key and value must be non-null and non-empty string.
     */
    private Map chatProfileProperties;

    /**
     * Creates the message instance.
     */
    public PresenceMessage() {
    }

    /**
     * Return whether user is present.
     *
     * @return A boolean indicating if user is present.
     */
    public boolean isPresent() {
        return present;
    }

    /**
     * Change the state for the field that indicates whether the user is present.
     *
     * @param present A boolean indicating if user is present.
     */
    public void setPresent(boolean present) {
        this.present = present;
    }

    /**
     * Get the properties of the User profile of the person who changed the presence.
     *
     * @return User profile of the person who changed the presence
     */
    public Map getChatProfileProperties() {
        return chatProfileProperties;
    }

    /**
     * Set the properties of the User profile of the person who changed the presence.
     *
     * @param properties the profile properties
     * @throws IllegalArgumentException if argument is null, empty or it contains null or empty key
     *                                  or contains null or empty value.
     */
    public void setChatProfileProperties(Map properties) {
        Helper.validateProfileProperties(properties, "properties");
        chatProfileProperties = properties;
    }

    /**
     * Return the xml representation of the message.
     * The xml string must follow the xsd scheme located in docs/PresenceMessage.xsd.
     * If any required field is missing, an exception of type <c>IllegalStateException</c>
     * will be thrown.
     *
     * @param context Holder for the date format attributes like date pattern, time zone, locale, etc.
     * @return The xml string representation of this message.
     * @throws IllegalStateException          If any required field is missing.
     * @throws IllegalArgumentException       If argument is null
     * @throws ChatMessageFormattingException Wraps any other exception that might appear during this operation.
     */
    public String toXMLString(DateFormatContext context)
        throws ChatMessageFormattingException {
        Helper.validateNotNull(context, "context");
        try {
            StringBuffer xmlStringBuffer = new StringBuffer();
            // Build the xml by respecting the structure defined in docs/AskForChatMessage.xsd
            xmlStringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                .append("<message type=\"PresenceMessage\">");
            // Add to xmlStringBuffer the basic informations about this message.
            XMLMessageHelper.addXmlForBasicXMLMessage(xmlStringBuffer, context, this);

            // Add chat user profile properties
            xmlStringBuffer.append("<chat_user_profile_properties>");
            if (chatProfileProperties == null) {
                throw new IllegalStateException("chatProfileProperties are missing for the message");
            }
            for (Iterator i = chatProfileProperties.entrySet().iterator(); i.hasNext();) {
                Map.Entry entry = (Map.Entry) i.next();
                XMLMessageHelper.addXmlForAttribute(xmlStringBuffer
                    , (String) entry.getKey(), (String[]) entry.getValue());
            }
            xmlStringBuffer.append("</chat_user_profile_properties>");

            // Add presence attribute for the message.
            xmlStringBuffer.append("<presence value=\"").append(present).append("\"/>");

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
