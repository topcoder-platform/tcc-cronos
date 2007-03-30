/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger;

import junit.framework.TestCase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Test the methods of <c>XMLMessageHelper</c> class for accuracy.
 * Since the failure test cases for <c>addXmlForBasicXMLMessage(StringBuffer, DateFormatContext, XMLMessage)</c>
 * are made in <c>XMLMessage</c> class which uses <c>XMLMessageHelper</c> class, they
 * will not be repeated here.
 *
 * @author marius_neo
 * @version 1.0
 */
public class XMLMessageHelperTestCase extends TestCase {

    /**
     * Tests the accuracy for <c>formatDate(DateFormatContext, Date)</c> method.
     *
     * @throws Exception Shouldn't be thrown.
     */
    public void testFormatDate() throws Exception {
        // Set up context field.
        String localeString = Locale.US.toString();
        String timeZoneID = TimeZone.getTimeZone("America/Los_Angeles").getID();
        String pattern = "hh:mm:ss";

        // Add attributes to the context
        DateFormatContext context = new DateFormatContext();
        context.addAttribute(DateFormatContext.DATE_FORMAT_KEY, pattern);
        context.addAttribute(DateFormatContext.TIMEZONE_KEY, timeZoneID);
        context.addAttribute(DateFormatContext.LOCALE_KEY, localeString);

        Date timestamp = new Date();
        String result = XMLMessageHelper.formatDate(context, timestamp);

        SimpleDateFormat sdf = new SimpleDateFormat(pattern, new Locale(localeString));
        sdf.setTimeZone(TimeZone.getTimeZone(timeZoneID));
        String expectedResult = sdf.format(timestamp);

        assertEquals("Should be equal", expectedResult, result);
    }

    /**
     * Tests for failure for <c>formatDate(DateFormatContext, Date)</c> method
     * when the <c>context</c> argument of the method contains invalid values
     * (like an invalid date format). <c>IllegalArgumentException</c> is expected
     * to be thrown.
     *
     * @throws Exception Shouldn't be thrown.
     */
    public void testFormatDateFailure() throws Exception {
        // Set up context field.
        String pattern = "hh:mm:ss invalid context";

        // Add attributes to the context
        DateFormatContext context = new DateFormatContext();
        context.addAttribute(DateFormatContext.DATE_FORMAT_KEY, pattern);

        Date timestamp = new Date();
        try {
            XMLMessageHelper.formatDate(context, timestamp);

            fail("Should have thrown IllegalArgumentException because of invalid date format in the context");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests for accuracy the method <c>addXmlForAttribute(StringBuffer,String,String[])</c>.
     */
    public void testAddXmlForAttribute() {
        StringBuffer xmlStringBuffer = new StringBuffer();
        String attributeName = "key1";
        String[] attributeValues = new String[]{"value1_1", "value1_2"};
        XMLMessageHelper.addXmlForAttribute(xmlStringBuffer, attributeName, attributeValues);

        String expectedResult =
            "<attribute><name>key1</name><value>value1_1</value><value>value1_2</value></attribute>";

        assertEquals("The xml string representations should be equal", expectedResult, xmlStringBuffer.toString());
    }

    /**
     * Tests for failure the method <c>addXmlForAttribute(StringBuffer,String,String[])</c>
     * when the <c>values</c> array passed to the method is null.
     */
    public void testAddXmlForAttributeNullValues() {
        StringBuffer xmlStringBuffer = new StringBuffer();
        String attributeName = "key1";
        try {
            XMLMessageHelper.addXmlForAttribute(xmlStringBuffer, attributeName, null);

            fail("IllegalStateException should be thrown because of missing attribute values");
        } catch (IllegalStateException e) {
            // Success
        }

    }

    /**
     * Tests for failure the method <c>addXmlForAttribute(StringBuffer,String,String[])</c>
     * when the <c>values</c> array passed to the method is an empty array.
     */
    public void testAddXmlForAttributeEmptyValues() {
        StringBuffer xmlStringBuffer = new StringBuffer();
        String attributeName = "key1";
        try {
            XMLMessageHelper.addXmlForAttribute(xmlStringBuffer, attributeName, new String[]{});

            fail("IllegalStateException should be thrown because of missing attribute values");
        } catch (IllegalStateException e) {
            // Success
        }
    }

    /**
     * Tests the accuracy for the method <c>addXmlForBasicXMLMessage(StringBuffer,DateFormatContext, XMLMessage)</c>.
     */
    public void testAddXmlForBasicMessage() {
        XMLMessage xmlMessage = new EnterChatMessage();
        StringBuffer xmlStringBuffer = new StringBuffer();
        DateFormatContext context = new DateFormatContext();

        Object sender = new Long(123);
        Date timestamp = new Date();
        Map attributes = new HashMap();
        attributes.put("key1", "value1");
        attributes.put("key2", "value2");
        long chatSessionId = 1234;
        Object[] recipients = new Long[]{new Long(123), new Long(124), new Long(125)};

        // Set the required fields on the message for calling without
        // problems the method <c>toXMLString(DateFormatContext)</c>.
        xmlMessage.setSender(sender);
        xmlMessage.setTimestamp(timestamp);
        for (Iterator i = attributes.entrySet().iterator(); i.hasNext();) {
            Map.Entry entry = (Map.Entry) i.next();
            xmlMessage.setAttribute((String) entry.getKey(), entry.getValue());
        }
        for (int i = 0; i < recipients.length; i++) {
            xmlMessage.addRecipient(recipients[i]);
        }
        xmlMessage.setChatSessionId(chatSessionId);

        XMLMessageHelper.addXmlForBasicXMLMessage(xmlStringBuffer, context, xmlMessage);

        String formattedTimestamp = XMLMessageHelper.formatDate(context, xmlMessage.getTimestamp());
        String expectedResult = "<timestamp>" + formattedTimestamp + "</timestamp><sender>123</sender>"
            + "<recipients><recipient>124</recipient><recipient>123</recipient>"
            + "<recipient>125</recipient></recipients>"
            + "<attribute><name>key1</name><value>value1</value></attribute>"
            + "<attribute><name>key2</name><value>value2</value></attribute>"
            + "<chat_session_id>1234</chat_session_id>";

        assertEquals("The xml string representations should be equal", expectedResult, xmlStringBuffer.toString());

        // Test the accuracy of the method after removing the attributes of the message
        xmlMessage.removeAllAttributes();
        xmlStringBuffer = new StringBuffer();
        XMLMessageHelper.addXmlForBasicXMLMessage(xmlStringBuffer, context, xmlMessage);

        expectedResult = "<timestamp>" + formattedTimestamp + "</timestamp><sender>123</sender>"
            + "<recipients><recipient>124</recipient><recipient>123</recipient>"
            + "<recipient>125</recipient></recipients>"
            + "<chat_session_id>1234</chat_session_id>";
        assertEquals("The xml string representations should be equal", expectedResult, xmlStringBuffer.toString());
    }
}
