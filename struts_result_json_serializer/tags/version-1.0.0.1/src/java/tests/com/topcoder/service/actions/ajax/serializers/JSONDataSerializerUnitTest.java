/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.ajax.serializers;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.actions.ajax.AJAXDataSerializationException;

/**
 * <p>
 * Unit tests for <code>JSONDataSerializer</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class JSONDataSerializerUnitTest extends TestCase {

    /**
     * <p>
     * The JSONDataSerializer instance for test.
     * </p>
     */
    private JSONDataSerializer instance;

    /**
     * <p>
     * Set up the environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        instance = new JSONDataSerializer();
    }

    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(JSONDataSerializerUnitTest.class);
    }

    /**
     * <p>
     * Accuracy test for the constructor, make sure it could create the JSONDataSerializer instance
     * successfully.
     * </p>
     */
    public void testConstructor_Accuracy() {
        assertNotNull("The JSONDataSerializer instance should be created.", instance);
        assertNull("The formatData should be null.", instance.getFormatData());
        assertEquals("The jsonResultTemplate should match",
                "{\"result\": {\"name\": \"$[action]\",\"return\": $[result]}}", instance
                        .getJsonResultTemplate());
        assertEquals("The jsonErrorTemplate should match",
                "{\"error\": {\"name\": \"$[action]\",\"errorMessage\": \"$[error]\"}}", instance
                        .getJsonErrorTemplate());
    }

    /**
     * <p>
     * Failure test for <code>serializeData</code>, if actionName is empty, IllegalArgumentException should be
     * thrown.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSerializeData_Failure_1() throws Exception {
        try {
            instance.serializeData("\t\n   ", new Object());
            fail("The IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for <code>serializeData</code>, if data is null, IllegalArgumentException should be
     * thrown.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSerializeData_Failure_2() throws Exception {
        instance.setJsonResultTemplate("$[action]:$[result]");
        assertEquals("The result should match.", "name:\"\"", instance.serializeData("name", null));
    }

    /**
     * <p>
     * Failure test for <code>serializeData</code>, if issue occurs when serializing the object to JSON
     * object,AJAXDataSerializationException should be thrown.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSerializeData_Failure_3() throws Exception {
        try {
            instance.serializeData("name", "not_supported_string");
            fail("The AJAXDataSerializationException should be thrown");
        } catch (AJAXDataSerializationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test for <code>serializeData</code>, make sure it could serialize if actionName is null, and
     * data is an Exception.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSerializeData_Accuracy_1() throws Exception {
        String message = "it's error message.";
        String result = instance.serializeData(null, new Exception(message));
        assertEquals("The result should match.",
                "{\"error\": {\"name\": \"\",\"errorMessage\": \"it's error message.\"}}", result);
    }

    /**
     * <p>
     * Accuracy test for <code>serializeData</code>, make sure it could serialize if actionName is not
     * null/empty, and data is an Exception.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSerializeData_Accuracy_2() throws Exception {
        String message = "it's error message.";
        String result = instance.serializeData("error_name", new Exception(message));
        assertEquals("The result should match.",
                "{\"error\": {\"name\": \"error_name\",\"errorMessage\": \"it's error message.\"}}", result);
    }

    /**
     * <p>
     * Accuracy test for <code>serializeData</code>, make sure it could serialize if actionName is null, and
     * data is not an Exception.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSerializeData_Accuracy_3() throws Exception {
        Map<String, String> data = new HashMap<String, String>();
        data.put("key", "value");
        String result = instance.serializeData(null, data);
        assertEquals("The result should match.",
                "{\"result\": {\"name\": \"\",\"return\": {\"key\":\"value\"}}}", result);
    }

    /**
     * <p>
     * Accuracy test for <code>serializeData</code>, make sure it could serialize if actionName is not
     * null/empty, and data is not an Exception.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSerializeData_Accuracy_4() throws Exception {
        Map<String, String> data = new HashMap<String, String>();
        data.put("key", "value");
        String result = instance.serializeData("map_name", data);
        assertEquals("The result should match.",
                "{\"result\": {\"name\": \"map_name\",\"return\": {\"key\":\"value\"}}}", result);
    }

    /**
     * <p>
     * Accuracy test for <code>serializeData</code>, make sure it could serialize if the error message is
     * null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSerializeData_Accuracy_5() throws Exception {
        String result = instance.serializeData(null, new Exception());
        assertEquals("The result should match.", "{\"error\": {\"name\": \"\",\"errorMessage\": \"\"}}",
                result);
    }

    /**
     * <p>
     * Accuracy test for <code>serializeData</code>, make sure it could serialize if the jsonErrorTemplate is
     * set.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSerializeData_Accuracy_6() throws Exception {
        instance.setJsonErrorTemplate("$[error]");
        String result = instance.serializeData(null, new Exception("It's an error"));
        assertEquals("The result should match.", "It's an error", result);
    }

    /**
     * <p>
     * Accuracy test for <code>serializeData</code>, make sure it could serialize if the jsonResultTemplate is
     * set.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSerializeData_Accuracy_7() throws Exception {
        instance.setJsonResultTemplate("$[result]");
        Map<String, String> data = new HashMap<String, String>();
        data.put("key", "value");
        String result = instance.serializeData("map_name", data);
        assertEquals("The result should match.", "{\"key\":\"value\"}", result);
    }

    /**
     * <p>
     * Accuracy test for <code>serializeData</code>, make sure it could serialize if the formatData is set.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSerializeData_Accuracy_8() throws Exception {
        instance.setJsonResultTemplate("$[format_1]:$[format_2]:$[result]");
        Map<String, String> format = new HashMap<String, String>();
        format.put("format_1", "value_1");
        format.put("format_2", "value_2");
        instance.setFormatData(format);
        Map<String, String> data = new HashMap<String, String>();
        data.put("key", "value");
        String result = instance.serializeData("map_name", data);
        assertEquals("The result should match.", "value_1:value_2:{\"key\":\"value\"}", result);
    }

    /**
     * <p>
     * Accuracy test for <code>serializeData</code>, make sure it could serialize not replacement happens.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSerializeData_Accuracy_9() throws Exception {
        instance.setJsonResultTemplate("no_replacement_happens");
        Map<String, String> data = new HashMap<String, String>();
        data.put("key", "value");
        String result = instance.serializeData("map_name", data);
        assertEquals("The result should match.", "no_replacement_happens", result);
    }

    /**
     * <p>
     * Accuracy test for <code>serializeData</code>, make sure it could serialize not replacement happens.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSerializeData_Accuracy_10() throws Exception {
        instance.setJsonErrorTemplate("no_replacement_happens");
        Map<String, String> data = new HashMap<String, String>();
        data.put("key", "value");
        String result = instance.serializeData("exception", new Exception("error"));
        assertEquals("The result should match.", "no_replacement_happens", result);
    }

    /**
     * <p>
     * Failure test for <code>setFormatData</code>, if data is null, IllegalArgumentException should be
     * thrown.
     * </p>
     */
    public void testSetFormatData_Failure_1() {
        try {
            instance.setFormatData(null);
            fail("The IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for <code>setFormatData</code>, if data contains null key, IllegalArgumentException should
     * be thrown.
     * </p>
     */
    public void testSetFormatData_Failure_2() {
        Map<String, String> data = new HashMap<String, String>();
        data.put(null, "value");
        try {
            instance.setFormatData(data);
            fail("The IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for <code>setFormatData</code>, if data contains empty key, IllegalArgumentException
     * should be thrown.
     * </p>
     */
    public void testSetFormatData_Failure_3() {
        Map<String, String> data = new HashMap<String, String>();
        data.put("\t\n   ", "value");
        try {
            instance.setFormatData(data);
            fail("The IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for <code>setFormatData</code>, if data contains null value, IllegalArgumentException
     * should be thrown.
     * </p>
     */
    public void testSetFormatData_Failure_4() {
        Map<String, String> data = new HashMap<String, String>();
        data.put("null", null);
        try {
            instance.setFormatData(data);
            fail("The IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test for <code>setFormatData</code>, make sure it could set the a empty map correctly.
     * </p>
     */
    public void testSetFormatData_Accuracy_1() {
        Map<String, String> data = new HashMap<String, String>();
        instance.setFormatData(data);
        assertTrue("The data should match", data == instance.getFormatData());
    }

    /**
     * <p>
     * Accuracy test for <code>setFormatData</code>, make sure it could set the map with empty value
     * correctly.
     * </p>
     */
    public void testSetFormatData_Accuracy_2() {
        Map<String, String> data = new HashMap<String, String>();
        data.put("empty", "");
        instance.setFormatData(data);
        assertTrue("The data should match", data == instance.getFormatData());
    }

    /**
     * <p>
     * Accuracy test for <code>getFormatData</code>, make sure it could get the correct format data.
     * </p>
     */
    public void testGetFormatData_Accuracy_1() {
        assertNull("The data should be null", instance.getFormatData());
        Map<String, String> data = new HashMap<String, String>();
        data.put("empty", "");
        instance.setFormatData(data);
        assertTrue("The data should match", data == instance.getFormatData());
    }

    /**
     * <p>
     * Failure test for <code>setJsonResultTemplate</code>, if the input argument is null,
     * IllegalArgumentException should be thrown.
     * </p>
     */
    public void testSetJsonResultTemplate_Failure_1() {
        try {
            instance.setJsonResultTemplate(null);
            fail("The IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for <code>setJsonResultTemplate</code>, if the input argument is empty,
     * IllegalArgumentException should be thrown.
     * </p>
     */
    public void testSetJsonResultTemplate_Failure_2() {
        try {
            instance.setJsonResultTemplate("\n\t  ");
            fail("The IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test for <code>setJsonResultTemplate</code>, make sure it could set the jsonResultTemplate
     * correctly.
     * </p>
     */
    public void testSetJsonResultTemplate_Accuracy_1() {
        String format = "right format";
        instance.setJsonResultTemplate(format);
        assertEquals("The jsonResultTemplate should match.", format, instance.getJsonResultTemplate());
    }

    /**
     * <p>
     * Accuracy test for <code>getJsonResultTemplate</code>, make sure it could get the jsonResultTemplate
     * correctly.
     * </p>
     */
    public void testGetJsonResultTemplate_Accuracy_1() {
        assertEquals("The jsonResultTemplate should match.",
                "{\"result\": {\"name\": \"$[action]\",\"return\": $[result]}}", instance
                        .getJsonResultTemplate());
        String format = "right format";
        instance.setJsonResultTemplate(format);
        assertEquals("The jsonResultTemplate should match.", format, instance.getJsonResultTemplate());
    }

    /**
     * <p>
     * Failure test for <code>setJsonErrorTemplate</code>, if the input argument is null,
     * IllegalArgumentException should be thrown.
     * </p>
     */
    public void testSetJsonErrorTemplate_Failure_1() {
        try {
            instance.setJsonErrorTemplate(null);
            fail("The IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for <code>setJsonErrorTemplate</code>, if the input argument is empty,
     * IllegalArgumentException should be thrown.
     * </p>
     */
    public void testSetJsonErrorTemplate_Failure_2() {
        try {
            instance.setJsonErrorTemplate("\n\t  ");
            fail("The IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test for <code>setJsonErrorTemplate</code>, make sure it could set the jsonErrorTemplate
     * correctly.
     * </p>
     */
    public void testSetJsonErrorTemplate_Accuracy_1() {
        String format = "right format";
        instance.setJsonErrorTemplate(format);
        assertEquals("The jsonErrorTemplate should match.", format, instance.getJsonErrorTemplate());
    }

    /**
     * <p>
     * Accuracy test for <code>getJsonErrorTemplate</code>, make sure it could get the jsonErrorTemplate
     * correctly.
     * </p>
     */
    public void testgetJsonResultTemplate_Accuracy_1() {
        assertEquals("The jsonErrorTemplate should match.",
                "{\"error\": {\"name\": \"$[action]\",\"errorMessage\": \"$[error]\"}}", instance
                        .getJsonErrorTemplate());
        String format = "right format";
        instance.setJsonErrorTemplate(format);
        assertEquals("The jsonErrorTemplate should match.", format, instance.getJsonErrorTemplate());
    }

}
