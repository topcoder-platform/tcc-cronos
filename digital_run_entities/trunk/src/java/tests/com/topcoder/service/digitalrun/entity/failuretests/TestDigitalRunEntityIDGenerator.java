/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.digitalrun.entity.failuretests;


import java.util.Properties;

import org.hibernate.HibernateException;

import com.topcoder.service.digitalrun.entity.idgenerator.DigitalRunEntityIDGenerator;

import junit.framework.TestCase;


/**
 * <p>
 * This class contains the failure test cases for DigitalRunEntityIDGenerator
 * class.
 * </p>
 *
 * @author akhil_bansal
 * @version 1.0
 */
public class TestDigitalRunEntityIDGenerator extends TestCase {

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
     *         throws exception if any, raise it to JUnit.
     */
    @Override
    public void setUp() throws Exception {
        this.digitalRunEntityIDGenerator = new DigitalRunEntityIDGenerator();
    }

    /**
     * <p>
     * This method tests the configure() method to throw IllegalArgumentException
     * for null properties.
     * </p>
     */
    public void testConfigureNullParams() {
        try {
            this.digitalRunEntityIDGenerator.configure(null, null, null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the configure() method to throw IllegalArgumentException
     * for missing properties.
     * </p>
     */
    public void testConfigureMissingProps() {
        try {
            this.digitalRunEntityIDGenerator.configure(null, new Properties(), null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the configure() method to throw IllegalArgumentException
     * for empty property value.
     * </p>
     */
    public void testConfigureEmptyPropsValues() {
        try {
            Properties props = new Properties();
            props.put("id_generator_name", " \t \n");
            this.digitalRunEntityIDGenerator.configure(null, props, null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the configure() method to throw IllegalArgumentException
     * for key with wrong data type.
     * </p>
     */
    public void testConfigureWrongPropsValues() {
        try {
            Properties props = new Properties();
            props.put("id_generator_name", new Long(1));
            this.digitalRunEntityIDGenerator.configure(null, props, null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the generate method to throw HibernateException for
     * invalid ID generator that cannot be used.
     * </p>
     */
    public void testGenerateInvalidGenerator() {
        try {
            Properties props = new Properties();
            props.put("id_generator_name", "invalid_generator");
            this.digitalRunEntityIDGenerator.configure(null, props, null);
            this.digitalRunEntityIDGenerator.generate(null, null);
            fail("HibernateException Expected.");
        } catch (HibernateException e) {
            // pass
        }
    }

}
