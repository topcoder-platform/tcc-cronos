/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.entity.idgenerator;

import java.util.Properties;

import org.hibernate.HibernateException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link DigitalRunEntityIDGenerator} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DigitalRunEntityIDGeneratorTest extends TestCase {

    /**
     * Represents the <code>DigitalRunEntityIDGenerator</code> instance to test.
     */
    private DigitalRunEntityIDGenerator digitalRunEntityIDGenerator = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    @Override
    protected void setUp() throws Exception {
        digitalRunEntityIDGenerator = new DigitalRunEntityIDGenerator();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    @Override
    protected void tearDown() throws Exception {
        digitalRunEntityIDGenerator = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(DigitalRunEntityIDGeneratorTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link DigitalRunEntityIDGenerator#DigitalRunEntityIDGenerator()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_DigitalRunEntityIDGenerator() {
        // check for null
        assertNotNull("DigitalRunEntityIDGenerator creation failed", digitalRunEntityIDGenerator);
    }

    /**
     * <p>
     * Accuracy test for {@link DigitalRunEntityIDGenerator#configure(Type, Properties, Dialect)} method.
     * </p>
     * <p>
     * No error expected.
     * </p>
     *
     */
    public void test_accuracy_configure() {
        Properties properties = new Properties();
        properties.put("id_generator_name", "test");
        digitalRunEntityIDGenerator.configure(null, properties, null);
    }

    /**
     * <p>
     * Failure test for {@link DigitalRunEntityIDGenerator#configure(Type, Properties, Dialect)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      Type type : Valid value
     *      Properties params : null value
     *      Dialect dialect : Valid value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_configure1() throws Exception {
        try {
            digitalRunEntityIDGenerator.configure(null, null, null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for {@link DigitalRunEntityIDGenerator#configure(Type, Properties, Dialect)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      Type type : Valid value
     *      Properties params : key missing
     *      Dialect dialect : Valid value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_configure2() throws Exception {
        try {
            Properties properties = new Properties();
            digitalRunEntityIDGenerator.configure(null, properties, null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for {@link DigitalRunEntityIDGenerator#configure(Type, Properties, Dialect)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      Type type : Valid value
     *      Properties params : key empty
     *      Dialect dialect : Valid value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_configure4() throws Exception {
        try {
            Properties properties = new Properties();
            properties.put("id_generator_name", "  ");
            digitalRunEntityIDGenerator.configure(null, properties, null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for {@link DigitalRunEntityIDGenerator#configure(Type, Properties, Dialect)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      Type type : Valid value
     *      Properties params : key different type
     *      Dialect dialect : Valid value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_configure5() throws Exception {
        try {
            Properties properties = new Properties();
            properties.put("id_generator_name", 1);
            digitalRunEntityIDGenerator.configure(null, properties, null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for {@link DigitalRunEntityIDGenerator#generate(SessionImplementor, Object)} method.
     * </p>
     * <p>
     * Expect a valid long number
     * </p>
     *
     */
    public void test_accuracy_generate() {
        Properties properties = new Properties();
        properties.put("id_generator_name", "UnitTest_IDGenerator");
        digitalRunEntityIDGenerator.configure(null, properties, null);
        Long l = (Long) digitalRunEntityIDGenerator.generate(null, null);
        // see the console
        System.out.println("generated id >> " + l);
    }

    /**
     * <p>
     * Failure test for {@link DigitalRunEntityIDGenerator#generate(SessionImplementor, Object)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      SessionImplementor session : Valid Value
     *      Object obj : Valid Value
     * </pre>
     *
     * <p>
     * NoSuchIDException will occur
     * </p>
     * <p>
     * Expected {@link HibernateException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_generate() throws Exception {
        try {
            Properties properties = new Properties();
            properties.put("id_generator_name", "invalid");
            digitalRunEntityIDGenerator.configure(null, properties, null);
            digitalRunEntityIDGenerator.generate(null, null);
            fail("HibernateException Expected.");
        } catch (HibernateException e) {
            // As expected
        }
    }
}
