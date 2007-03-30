/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.messenger;

/**
 * <p>
 * This is the message to notify a user that the session is unavailable.
 * The sender of this message must be the system.
 * </p>
 * <p>
 * <b>Thread safety</b>: This class is not thread safe since it’s mutable.
 * </p>
 *
 * @author woodjhon, marius_neo
 * @version 1.0
 */
public class SessionUnavailableMessage extends XMLMessage {

    /**
     * Create a new message instance.
     */
    public SessionUnavailableMessage() {
    }

    /**
     * Return the xml representation of the message.
     * The xml string must follow the xsd scheme located in docs/SessionUnavailaleMessage.xsd.
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
                .append("<message type=\"SessionUnavailableMessage\">");
            // Add to xmlStringBuffer the basic informations about this message.
            XMLMessageHelper.addXmlForBasicXMLMessage(xmlStringBuffer, context, this);

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
