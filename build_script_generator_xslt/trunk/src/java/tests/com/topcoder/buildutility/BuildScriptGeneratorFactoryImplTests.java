/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.buildutility;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import junit.framework.TestCase;

import com.topcoder.buildutility.template.Template;
import com.topcoder.buildutility.template.TemplateHierarchy;

/**
 * Unit tests for the <code>BuildScriptGeneratorFactoryImpl</code> class. To verify if the buildscript generators
 * returned by this class have the correct transformation, those generators are used to create a build script wich is
 * validated against a hand-written output file.
 *
 * @author marijnk
 * @version 1.0
 * @copyright &copy; 2005, TopCoder, Inc. All rights reserved.
 */
public class BuildScriptGeneratorFactoryImplTests extends TestCase {
    /** The name of the file that is used to store the resulting build scripts in. */
    private static final String OUTPUT_FILENAME = "test_files/testout.xml";

    /** The name of the file containing the xslt transformation to use in some tests. */
    private static final String TEST_XSLT_FILENAME = "test_files/test.xslt";

    /** A <code>BuildScriptGeneratorFactory</code> instance to run the tests on. */
    private BuildScriptGeneratorFactory factory;

    /**
     * Set up the testing environment. This creates the factory to test.
     */
    protected void setUp()  {
        factory = new BuildScriptGeneratorFactoryImpl();
    }

    /**
     * Tear down the testing environment. Remove the output file that might have been created.
     */
    protected void tearDown() {
        new File(OUTPUT_FILENAME).delete();
    }

    /**
     * Verify behavior of the constructor. Make sure the factory was successfuly instantiated.
     */
    public void testConstructor() {
        assertNotNull("Factory is not instantiated", factory);
    }

    /**
     * Verify behavior of the <code>createGenerator(InputStream)</code> method. Check for
     * <code>NullPointerException</code> if the inputstream is null.
     *
     * @throws GeneratorCreationException to JUnit
     */
    public void testCreateGeneratorInputStream_NullSource() throws GeneratorCreationException {
        try {
            factory.createGenerator((InputStream) null);
            fail("No NullPointerException thrown, but source is null");
        } catch (NullPointerException e) {
            // Is expected
        }
    }

    /**
     * Verify behavior of the <code>createGenerator(InputStream)</code> method. Check for
     * <code>GeneratorCreationException</code> if the provided inputstream is not a valid XSLT transformation document.
     */
    public void testCreateGeneratorInputStream_InvalidXslt() {
        try {
            factory.createGenerator(new ByteArrayInputStream("foobar".getBytes()));
            fail("No GeneratorCreationException thrown, but source is not a valid xslt transformation document");
        } catch (GeneratorCreationException e) {
            // Is expected
        }
    }

    /**
     * Verify behavior of the <code>createGenerator(InputStream)</code> method. Check if a correct
     * <code>BuildScriptGenerator</code> instance is returned if a valid InputStream, with a valid xslt document
     * is passed in. As it is not really possible to check the created generator directly, the generator is used to
     * transform a component, and the resulting xml file is checked for correctness.
     *
     * @throws Exception to JUnit
     */
    public void testCreateGeneratorInputStream() throws Exception {
        BuildScriptGenerator generator = factory.createGenerator(new FileInputStream(TEST_XSLT_FILENAME));
        assertNotNull("Generator should not be null", generator);
        // Create a build script for a sample component, to verify if the transformation is correct
        generator.generate(UnitTestHelper.COMPONENT, new FileOutputStream(OUTPUT_FILENAME));
        // Verify the generated xml file
        UnitTestHelper.validateOutput("test_files/project.xml", OUTPUT_FILENAME);
    }

    /**
     * Verify behavior of the <code>createGenerator(Template)</code> method. Check for
     * <code>NullPointerException</code> if the template is null.
     *
     * @throws GeneratorCreationException to JUnit
     */
    public void testCreateGeneratorTemplate_NullTemplate() throws GeneratorCreationException {
        try {
            factory.createGenerator((Template) null);
            fail("No NullPointerException thrown, but template is null");
        } catch (NullPointerException e) {
            // Is expected
        }
    }

    /**
     * Verify behavior of the <code>createGenerator(Template)</code> method. Check for
     * <code>GeneratorCreationException</code> if the template specifies an non-existing file for the xslt document.
     */
    public void testCreateGeneratorTemplate_NonExistingFile() {
        try {
            factory.createGenerator(UnitTestHelper.ERROR_TEMPLATE);
            fail("No GeneratorCreationException thrown, but template uses a non-existing file");
        } catch (GeneratorCreationException e) {
            // Is expected
        }
    }

    /**
     * Verify behavior of the <code>createGenerator(Template)</code> method. Check if a correct generator is created
     * if a correct template is specified. As it is not really possible to check the created generator directly, the
     * generator is used to transform a component, and the resulting xml file is checked for correctness.
     *
     * @throws Exception to JUnit
     */
    public void testCreateGeneratorTemplate() throws Exception {
        BuildScriptGenerator generator = factory.createGenerator(UnitTestHelper.SIMPLE_TEMPLATE);
        assertNotNull("Generator should not be null", generator);
        // Create a build script for a sample component, to verify if the transformation is correct
        generator.generate(UnitTestHelper.COMPONENT, new FileOutputStream(OUTPUT_FILENAME));
        // Verify the generated xml file
        UnitTestHelper.validateOutput("test_files/project.xml", OUTPUT_FILENAME);
    }

    /**
     * Verify behavior of the <code>createGenerator(TemplateHierarchy, ComponentVersion)</code> method. Check for
     * <code>NullPointerException</code> if the template hierarchy is null.
     *
     * @throws GeneratorCreationException to JUnit
     */
    public void testCreateGeneratorTemplateHierarchyComponentVersion_NullHierarchy()
        throws GeneratorCreationException {
        try {
            factory.createGenerator(null, UnitTestHelper.COMPONENT);
            fail("No NullPointerException thrown, but template hierarchy is null");
        } catch (NullPointerException e) {
            // Is expected
        }
    }

    /**
     * Verify behavior of the <code>createGenerator(TemplateHierarchy, ComponentVersion)</code> method. Check for
     * <code>NullPointerException</code> if the component is null.
     *
     * @throws GeneratorCreationException to JUnit
     */
    public void testCreateGeneratorTemplateHierarchyComponentVersion_NullComponent()
        throws GeneratorCreationException {
        try {
            factory.createGenerator(UnitTestHelper.HIERARCHY, null);
            fail("No NullPointerException thrown, but component is null");
        } catch (NullPointerException e) {
            // Is expected
        }
    }

    /**
     * Verify behavior of the <code>createGenerator(TemplateHierarchy, ComponentVersion)</code> method. Check for
     * <code>GeneratorCreationException</code> if no matching templates exist in the hierarchy.
     */
    public void testCreateGeneratorTemplateHierarchyComponentVersion_EmptyHierarchy() {
        try {
            factory.createGenerator(new TemplateHierarchy(1, "name", -1), UnitTestHelper.COMPONENT);
            fail("No GeneratorCreationException thrown, but template hierarchy is empty");
        } catch (GeneratorCreationException e) {
            // Is expected
        }
    }

    /**
     * Verify behavior of the <code>createGenerator(TemplateHierarchy, ComponentVersion)</code> method. Check if a
     * correct generator is created if a correct template is specified. This also checks if the correct template will
     * be selected. As it is not really possible to check the created generator directly, the generator is used to
     * transform a component, and the resulting xml file is checked for correctness.
     *
     * @throws Exception to JUnit
     */
    public void testCreateGeneratorTemplateHierarchyComponentVersion() throws Exception {
        BuildScriptGenerator generator = factory.createGenerator(UnitTestHelper.HIERARCHY,
                UnitTestHelper.COMPONENT);
        assertNotNull("Generator should not be null", generator);
        // Create a build script for a sample component, to verify if the transformation is correct
        generator.generate(UnitTestHelper.COMPONENT, new FileOutputStream(OUTPUT_FILENAME));
        // Verify the generated xml file
        UnitTestHelper.validateOutput("test_files/build.xml", OUTPUT_FILENAME);
    }
}
