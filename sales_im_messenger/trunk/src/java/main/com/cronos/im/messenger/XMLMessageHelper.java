/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Defines helper methods used by classes which inherit <c>XMLMessage</c> class.
 *
 * @author marius_neo
 * @version 1.0
 */
final class XMLMessageHelper {
    /**
     * Private constructor which avoids instantiation of the class.
     */
    private XMLMessageHelper() {

    }

    /**
     * This method retrieves the string representation of the specified <c>timestamp</c>
     * by taking into account the informations retrieved from specified <c>dateFormatContext</c>.
     * <p>
     * Since this is a method in a package private class it is assumed that the parameters
     * passed to the method are not null. The exceptions can occur because of the invalid
     * parameter values found in <c>dateFormatContext</c>.
     * </p>
     * <p>
     * Note: IllegalArgumentException is thrown here even if it will wrap another IllegalArgumentException
     * because IllegalStateException is to be thrown only when some message property is missing.
     * </p>
     *
     * @param dateFormatContext Holder for attributes related to date formatting.
     * @param timestamp         The <c>Date</c> object to be formatted.
     * @return The string representation of the <c>timestamp</c> object formatted by
     *         taking into account informations from <c>dateFormatContext</c>.
     * @throws IllegalArgumentException Wraps all the exceptions that may occur during
     *                                  this method call.
     */
    public static String formatDate(DateFormatContext dateFormatContext, Date timestamp) {
        try {
            String dateFormat = dateFormatContext.getAttribute(DateFormatContext.DATE_FORMAT_KEY);
            if (dateFormat == null) {
                dateFormat = "hh:mm:ss a z";
            }
            String timeZoneID = dateFormatContext.getAttribute(DateFormatContext.TIMEZONE_KEY);
            TimeZone timeZone;
            if (timeZoneID == null) {
                timeZoneID = TimeZone.getDefault().getID();
            }
            timeZone = TimeZone.getTimeZone(timeZoneID);

            String localeString = dateFormatContext.getAttribute(DateFormatContext.TIMEZONE_KEY);
            Locale locale;
            if (localeString == null) {
                localeString = Locale.getDefault().getLanguage();
            }
            locale = new Locale(localeString);

            DateFormat formatter = new SimpleDateFormat(dateFormat, locale);
            formatter.setTimeZone(timeZone);
            return formatter.format(timestamp);
        } catch (IllegalArgumentException e) {
            StringBuffer sbMessage = new StringBuffer();
            sbMessage.append("Exception occurred when formatting the timestamp: ").append(e.getMessage());
            throw new IllegalArgumentException(sbMessage.toString());
        }
    }

    /**
     * Comfort method used internally in this package to add &lt;attribute&gt; tags
     * in a string buffer which holds the xml string representation for a message.
     *
     * @param xmlStringBuffer Holds xml string representation for a message.
     * @param name            Name of the attribute.
     * @param values          Value of the attribute. Cannot be null.
     * @throws IllegalStateException If the <c>values</c> array is null or empty.
     */
    public static void addXmlForAttribute(StringBuffer xmlStringBuffer, String name, String[] values) {
        xmlStringBuffer.append("<attribute>");
        xmlStringBuffer.append("<name>").append(name).append("</name>");
        if (values == null || values.length == 0) {
            throw new IllegalStateException("The values array corresponding to the attribute name '"
                + name + "' is null or empty");
        }
        for (int i = 0; i < values.length; i++) {
            xmlStringBuffer.append("<value>");
            if (values[i] != null) {
                xmlStringBuffer.append(values[i]);
            }
            xmlStringBuffer.append("</value>");
        }
        xmlStringBuffer.append("</attribute>");
    }

    /**
     * Comfort method used to add the basic properties for a XML message.
     * The order in which those properties are added is the following:
     * <ol>
     * <li>Timestamp when the message is created</li>
     * <li>Sender of the message</li>
     * <li>Recipients for the message</li>
     * <li>Attributes of the message</li>
     * <li>Chat session id</li>
     * </ol>
     *
     * @param xmlStringBuffer Holds xml string representation for a message.
     * @param context         Holder for attributes related to date formatting.
     * @param xmlMessage      Contains the properties to be added in <c>xmlStringBuffer</c>.
     * @throws IllegalStateException    If any required field is missing.
     * @throws IllegalArgumentException If there is a problem in formatting the timestamp of the message.
     */
    public static void addXmlForBasicXMLMessage(StringBuffer xmlStringBuffer
        , DateFormatContext context, XMLMessage xmlMessage) {
        // Timestamp of when the message is created (it is alwais set).
        xmlStringBuffer.append("<timestamp>");
        xmlStringBuffer.append(XMLMessageHelper.formatDate(context, xmlMessage.getTimestamp()));
        xmlStringBuffer.append("</timestamp>");

        xmlStringBuffer.append("<sender>");
        Object sender = xmlMessage.getSender();
        if (sender == null) {
            throw new IllegalStateException("The sender of the message is missing for the message");
        }

        xmlStringBuffer.append(sender);
        xmlStringBuffer.append("</sender>");

        // Add the recipient user ids.
        xmlStringBuffer.append("<recipients>");
        Object[] recipients = xmlMessage.getAllRecipients();
        if (recipients.length == 0) {
            throw new IllegalStateException("The message has no recipients");
        }
        for (int i = 0; i < recipients.length; i++) {
            xmlStringBuffer.append("<recipient>")
                .append(recipients[i]).append("</recipient>");
        }
        xmlStringBuffer.append("</recipients>");

        // Add the attributes for the message
        Map attributes = xmlMessage.getAllAttributes();
        for (Iterator i = attributes.entrySet().iterator(); i.hasNext();) {
            Map.Entry entry = (Map.Entry) i.next();
            XMLMessageHelper.addXmlForAttribute(xmlStringBuffer
                , (String) entry.getKey(), new String[]{(String) entry.getValue()});
        }

        // Add chat session id
        xmlStringBuffer.append("<chat_session_id>");
        long chatSessionId = xmlMessage.getChatSessionId();
        if (chatSessionId == 0) {
            throw new IllegalStateException("The chat session id id missing for the message");
        }
        xmlStringBuffer.append(chatSessionId);
        xmlStringBuffer.append("</chat_session_id>");
    }
}
