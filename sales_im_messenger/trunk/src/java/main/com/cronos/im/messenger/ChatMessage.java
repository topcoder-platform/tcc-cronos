/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.messenger;

import java.util.Iterator;
import java.util.Map;

/**
 * This is the message typed by a user in a chat room. It should include:
 * <ul>
 * <li>User profile of the person who typed the text (only username is required in XML).</li>
 * <li>Chat text.</li>
 * <li>The xml string representation of this message has an extra element to keep  the formatted chat text.</li>
 * </ul>
 * <p>
 * The formatted chat text takes the following standard format:<br>[Name]: [Timestamp]: [Text Message]
 * <ul>
 * <li>Name: the user name</li>
 * <li>Timestamp: the message timestamp. The format for the time should be configurable
 * with the default being the following format: hh:mm:ss PM Time zone. For example, 05:34:30 PM EST</li>
 * <li>Text Message: the chat text.</li>
 * </ul>
 * </p>
 * <p>
 * The font and color for each of the above parts in configurable. In particular, the color for text
 * message is different for client and manager. This allows easier differentiation between the two.
 * For now, assume the &quote;Role&quote; property returns either &quote;Client&quote; or
 * &quote;Manager&quote;.
 * </p>
 * <p>
 * Furthermore, if a text message includes a link to a URL, then the URL text will be displayed as a
 * hyperlink. Below are the rules for determining if text should be generated as a hyperlink:
 * <ul>
 * <li>If the text begins with a http://, https://, ftp:// (not case sensitive)</li>
 * <li>If the text follows the pattern: www.[Text].[Top Level Domain], where [text] section may include dots,
 * and [Top Level Domain] is defined in http://data.iana.org/TLD/tlds-alpha-by-domain.txt</li>
 * </ul>
 * <p>
 * <b>Thread safety</b>: This class is not thread safe since it’s mutable.
 * </p>
 *
 * @author woodjhon, marius_neo
 * @version 1.0
 */
public class ChatMessage extends XMLMessage {

    /**
     * The user profile properties of the person who typed the text.
     * It is set and accessed by set/get methods corresponding to field's name.
     * The keys and values must be non-null and non-empty strings.
     */
    private Map chatProfileProperties;

    /**
     * Represents the chat text typed by user.It's set and accessed by set/get methods
     * corresponding to field's name.It has to be non-null.
     */
    private String chatText;

    /**
     * Creates the message instance.
     */
    public ChatMessage() {
    }

    /**
     * Retrieves the properties of the User profile of the person who typed the text.
     * Note that if the chat profile properties are not set yet, this method will return
     * a <c>null</c> value.
     *
     * @return The profile properties.
     */
    public Map getChatProfileProperties() {
        return chatProfileProperties;
    }

    /**
     * Sets the properties of the User profile of the person who typed the text.
     *
     * @param properties The profile properties.
     * @throws IllegalArgumentException If argument is null, empty, contains null or empty key
     *                                  or contains null or empty value.
     */
    public void setChatProfileProperties(Map properties) {
        Helper.validateProfileProperties(properties, "properties");
        this.chatProfileProperties = properties;
    }

    /**
     * Get the chat text for this message.
     *
     * @return The chat text.
     */
    public String getChatText() {
        return chatText;
    }

    /**
     * Set the chat text for this message.
     *
     * @param text The chat text for the message.
     * @throws IllegalArgumentException If argument is null.
     */
    public void setChatText(String text) {
        Helper.validateNotNull(text, "text");
        chatText = text;
    }

    /**
     * <p>
     * Returns the xml representation of the message.
     * </p>
     * <p>
     * The xml string must follow the xsd scheme located in docs/ChatMessage.xsd. If any required field is
     * missing, an exception of type <c>IllegalStateException</c> will be thrown.
     * </p>
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
            xmlStringBuffer.append("<message type=\"ChatMessage\">");
            // Add to xmlStringBuffer the basic informations about this message.
            XMLMessageHelper.addXmlForBasicXMLMessage(xmlStringBuffer, context, this);

            // Add chat user profile properties
            xmlStringBuffer.append("<chat_user_profile_properties>");
            if (chatProfileProperties == null) {
                throw new IllegalStateException("chat user profile properties are not set for the message");
            }
            for (Iterator i = chatProfileProperties.entrySet().iterator(); i.hasNext();) {
                Map.Entry entry = (Map.Entry) i.next();
                XMLMessageHelper.addXmlForAttribute(xmlStringBuffer
                    , (String) entry.getKey(), (String[]) entry.getValue());
            }
            xmlStringBuffer.append("</chat_user_profile_properties>");

            // Add chat text for the message.
            xmlStringBuffer.append("<chat_text>");
            if (chatText == null) {
                throw new IllegalStateException("The chat text is not set for the message");
            }
            xmlStringBuffer.append(chatText);
            xmlStringBuffer.append("</chat_text>");

            // Add formatted chat text for the message.We use CDATA selection to avoid
            // having problems when parsing the xml.
            xmlStringBuffer.append("<formatted_chat_text><![CDATA[");
            FormatterLoader formatterLoader = FormatterLoader.getInstance();
            String roleName = formatterLoader.getRolePropertyName();
            String userName = formatterLoader.getUserPropertyName();
            ChatMessageFormatter cmf = formatterLoader.getChatMessageFormatter(roleName);
            xmlStringBuffer.append(cmf.format(userName
                , XMLMessageHelper.formatDate(context, getTimestamp()), chatText));
            xmlStringBuffer.append("]]></formatted_chat_text>");

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
