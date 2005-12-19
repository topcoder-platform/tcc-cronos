/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.buildutility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;

import junit.framework.TestCase;

/**
 * This class implements various stress tests for the <code>BuildScriptGenerator</code>. This class uses the
 * <code>BuildScriptGeneratorFactoryImpl</code> to create the generator.
 *
 * @author marijnk
 * @version 1.0
 * @copyright &copy; 2005, TopCoder, Inc. All rights reserved.
 */
public class BuildScriptGeneratorStressTests extends TestCase {
    /** The number of times each test should be executed. */
    private static final int NUM_ITERATIONS = 1000;

    /** A BuildScriptGenerator to run the tests on. */
    private BuildScriptGenerator generator;

    /**
     * Set up the testing environment, this creates the <code>BuildScriptGenerator</code> to run the tests on.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        generator = new BuildScriptGeneratorFactoryImpl().createGenerator(
                new FileInputStream("test_files/build_ant.xslt"));
    }

    /**
     * Measure the running time of the <code>generate(InputStream, OutputStream)</code> method. This uses a
     * <code>FileInputStream</code> to read from, and a <code>ByteArrayOutputStream</code> to write to.
     *
     * @throws Exception to JUnit
     */
    public void testStress_GenerateInputStreamOutputStream() throws Exception {
        long time = System.currentTimeMillis();
        // Generate the build scripts
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            generator.generate(new FileInputStream("test_files/component.xml"), new ByteArrayOutputStream());
        }
        time = System.currentTimeMillis() - time;
        // Display the resulting time
        System.out.println("generate(InputStream, OutputStream) took " + ((double) time / NUM_ITERATIONS) + " ms.");
    }

    /**
     * Measure the running time of the <code>generate(ComponentVersion, OutputStream)</code> method. This uses a
     * <code>ByteArrayOutputStream</code> to write the resulting build script to.
     *
     * @throws Exception to JUnit
     */
    public void testStress_GenerateComponentVersionOutputStream() throws Exception {
        long time = System.currentTimeMillis();
        // Generate the build scripts
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            generator.generate(UnitTestHelper.COMPONENT, new ByteArrayOutputStream());
        }
        time = System.currentTimeMillis() - time;
        // Display the resulting time
        System.out.println("generate(ComponentVersion, OutputStream) took " + ((double) time / NUM_ITERATIONS)
                + " ms.");
    }

    /**
     * Measure the running time of the <code>generate(Componentversion, Result)</code> method. This uses an empty
     * <code>DOMResult</code> instance to write the resulting build script to. Using a <code>StreamResult</code> would
     * have been faster, but in applications using a <code>DOMResult</code> would be more common.
     *
     * @throws Exception to JUnit
     */
    public void testStress_GenerateComponentVersionResult() throws Exception {
        long time = System.currentTimeMillis();
        // Generate the build scripts
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            generator.generate(UnitTestHelper.COMPONENT, new DOMResult());
        }
        time = System.currentTimeMillis() - time;
        // Display the resulting time
        System.out.println("generate(ComponentVersion, Result) took " + ((double) time / NUM_ITERATIONS) + " ms.");
    }

    /**
     * Measure the running time of the <code>generate(Source, Result)</code> method. This uses a <code>DOMSource</code>
     * as source, and an empty <code>DOMResult</code> as result parameter.
     *
     * @throws Exception to JUnit
     */
    public void testStress_GenerateSourceResult() throws Exception {
        // Convert the source xml file to a DOMSource
        Source source = new DOMSource(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                new File("test_files/component.xml")));
        long time = System.currentTimeMillis();
        // Generate the build scripts
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            generator.generate(source, new DOMResult());
        }
        time = System.currentTimeMillis() - time;
        // Display the resulting time
        System.out.println("generate(Source, Result) took " + ((double) time / NUM_ITERATIONS) + " ms.");
    }
}
