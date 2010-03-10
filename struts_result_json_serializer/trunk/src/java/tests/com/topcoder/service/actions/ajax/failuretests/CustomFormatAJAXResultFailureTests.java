/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.ajax.failuretests;

import java.nio.charset.IllegalCharsetNameException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.opensymphony.xwork2.mock.MockActionInvocation;
import com.topcoder.service.actions.AbstractAction;
import com.topcoder.service.actions.ajax.AJAXDataPostProcessingException;
import com.topcoder.service.actions.ajax.AJAXDataPreProcessingException;
import com.topcoder.service.actions.ajax.AJAXDataSerializationException;
import com.topcoder.service.actions.ajax.AJAXDataSerializer;
import com.topcoder.service.actions.ajax.AJAXResultPostProcessor;
import com.topcoder.service.actions.ajax.AJAXResultPreProcessor;
import com.topcoder.service.actions.ajax.CustomFormatAJAXResult;

/**
 * <p>
 * Failure test for <code>{@link CustomFormatAJAXResult}</code> class.
 * </p>
 *
 * @author FireIce
 * @version 1.0
 */
public class CustomFormatAJAXResultFailureTests extends TestCase {

    /**
     * <p>
     * Represents the <code>CustomFormatAJAXResult</code> instance.
     * </p>
     */
    private CustomFormatAJAXResult customFormatAJAXResult;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(CustomFormatAJAXResultFailureTests.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        customFormatAJAXResult = new CustomFormatAJAXResult();
    }

    /**
     * <p>
     * Failure test for <code>execute(ActionInvocation)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testExecute_null() throws Exception {
        try {
            customFormatAJAXResult.execute(null);
            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>execute(ActionInvocation)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testExecute_AJAXDataPreProcessingException() throws Exception {
        AJAXResultPreProcessor preProcessor = new AJAXResultPreProcessor() {
            public Object preProcessData(Object data) throws AJAXDataPreProcessingException {
                throw new AJAXDataPreProcessingException("Failure");
            }
        };
        customFormatAJAXResult.setDataPreProcessor(preProcessor);
        MockActionInvocation actionInvocation = new MockActionInvocation();
        actionInvocation.setAction(new AbstractAction() {
        });
        try {
            customFormatAJAXResult.execute(actionInvocation);
            fail("expect AJAXDataPreProcessingException");
        } catch (AJAXDataPreProcessingException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>execute(ActionInvocation)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testExecute_AJAXDataSerializationException() throws Exception {
        AJAXDataSerializer serializer = new AJAXDataSerializer() {
            public String serializeData(String actionName, Object data) throws AJAXDataSerializationException {
                throw new AJAXDataSerializationException("Failure");
            }
        };
        customFormatAJAXResult.setDataSerializer(serializer);
        MockActionInvocation actionInvocation = new MockActionInvocation();
        actionInvocation.setAction(new AbstractAction() {
        });
        try {
            customFormatAJAXResult.execute(actionInvocation);
            fail("expect AJAXDataSerializationException");
        } catch (AJAXDataSerializationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>execute(ActionInvocation)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testExecute_AJAXDataPostProcessingException() throws Exception {
        AJAXResultPostProcessor postProcessor = new AJAXResultPostProcessor() {
            public String postProcessData(String data) throws AJAXDataPostProcessingException {
                throw new AJAXDataPostProcessingException("Failure");
            }
        };
        customFormatAJAXResult.setDataPostProcessor(postProcessor);
        MockActionInvocation actionInvocation = new MockActionInvocation();
        actionInvocation.setAction(new AbstractAction() {
        });
        try {
            customFormatAJAXResult.execute(actionInvocation);
            fail("expect AJAXDataPostProcessingException");
        } catch (AJAXDataPostProcessingException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>setDataSerializer(AJAXDataSerializer)</code> method.
     * </p>
     */
    public void testSetDataSerializer_null() {

        try {
            customFormatAJAXResult.setDataSerializer(null);
            fail("expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>setCharset(String)</code> method.
     * </p>
     */
    public void testSetCharset_null() {
        try {
            customFormatAJAXResult.setCharset(null);
            fail("expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>setCharset(String)</code> method.
     * </p>
     */
    public void testSetCharset_NotSupported() {
        try {
            customFormatAJAXResult.setCharset("Failure");
            fail("expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>setCharset(String)</code> method.
     * </p>
     */
    public void testSetCharset_Illegal() {
        try {
            customFormatAJAXResult.setCharset("+xx+");
            fail("expect IllegalArgumentException or IllegalCharsetNameException.");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException || e instanceof IllegalCharsetNameException);
        }
    }

    /**
     * <p>
     * Failure test for <code>setContentType(String)</code> method.
     * </p>
     */
    public void testSetContentType_null() {
        try {
            customFormatAJAXResult.setContentType(null);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>setContentType(String)</code> method.
     * </p>
     */
    public void testSetContentType_empty() {
        try {
            customFormatAJAXResult.setContentType(null);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>setContentType(String)</code> method.
     * </p>
     */
    public void testSetContentType_trimmedEmpty() {
        try {
            customFormatAJAXResult.setContentType(" ");

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
