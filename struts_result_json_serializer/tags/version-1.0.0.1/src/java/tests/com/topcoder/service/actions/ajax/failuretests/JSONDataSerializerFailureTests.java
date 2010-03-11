/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.ajax.failuretests;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.actions.ajax.serializers.JSONDataSerializer;

/**
 * <p>
 * Failure test for <code>{@link JSONDataSerializer}</code> class.
 * </p>
 *
 * @author FireIce
 * @version 1.0
 */
public class JSONDataSerializerFailureTests extends TestCase {

    /**
     * <p>
     * Represents the <code>JSONDataSerializer</code> instance.
     * </p>
     */
    private JSONDataSerializer jsonDataSerializer;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(JSONDataSerializerFailureTests.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        jsonDataSerializer = new JSONDataSerializer();
    }

    /**
     * <p>
     * Failure test for <code>serializeData(String, Object)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSerializeData_actionName_empty() throws Exception {
        try {
            jsonDataSerializer.serializeData("", null);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>serializeData(String, Object)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSerializeData_actionName_trimmedEmpty() throws Exception {
        try {
            jsonDataSerializer.serializeData("  ", null);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>setFormatData(Map)</code> method.
     */
    public void testSetFormatData_null() {
        try {
            jsonDataSerializer.setFormatData(null);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>setFormatData(Map)</code> method.
     */
    public void testSetFormatData_key_null() {
        Map<String, String> data = new HashMap<String, String>();
        data.put(null, "value");
        try {
            jsonDataSerializer.setFormatData(data);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>setFormatData(Map)</code> method.
     */
    public void testSetFormatData_key_empty() {
        Map<String, String> data = new HashMap<String, String>();
        data.put("", "value");
        try {
            jsonDataSerializer.setFormatData(data);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>setFormatData(Map)</code> method.
     */
    public void testSetFormatData_key_trimmedEmpty() {
        Map<String, String> data = new HashMap<String, String>();
        data.put("  ", "value");
        try {
            jsonDataSerializer.setFormatData(data);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>setFormatData(Map)</code> method.
     */
    public void testSetFormatData_value_null() {
        Map<String, String> data = new HashMap<String, String>();
        data.put("key", null);
        try {
            jsonDataSerializer.setFormatData(data);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>setJsonResultTemplate(String)</code> method.
     * </p>
     */
    public void testSetJsonResultTemplate_null() {
        try {
            jsonDataSerializer.setJsonResultTemplate(null);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>setJsonResultTemplate(String)</code> method.
     * </p>
     */
    public void testSetJsonResultTemplate_empty() {
        try {
            jsonDataSerializer.setJsonResultTemplate(null);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>setJsonResultTemplate(String)</code> method.
     * </p>
     */
    public void testSetJsonResultTemplate_trimmedEmpty() {
        try {
            jsonDataSerializer.setJsonResultTemplate(" ");

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>setJsonErrorTemplate(String)</code> method.
     * </p>
     */
    public void testSetJsonErrorTemplate_null() {
        try {
            jsonDataSerializer.setJsonErrorTemplate(null);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>setJsonErrorTemplate(String)</code> method.
     * </p>
     */
    public void testSetJsonErrorTemplate_empty() {
        try {
            jsonDataSerializer.setJsonErrorTemplate(null);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>setJsonErrorTemplate(String)</code> method.
     * </p>
     */
    public void testSetJsonErrorTemplate_trimmedEmpty() {
        try {
            jsonDataSerializer.setJsonErrorTemplate(" ");

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
