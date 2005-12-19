/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.buildutility;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import junit.framework.TestCase;

import org.w3c.dom.Node;

import com.topcoder.buildutility.component.ComponentVersion;

/**
 * Unit tests for the <code>BuildScriptGeneratorImpl</code> class. Most of these tests use an identity transformer
 * (one that simple copies its input to its output), but also two tests are included that use the actual ant/nant xslt
 * transformations.
 *
 * @author marijnk
 * @version 1.0
 * @copyright &copy; 2005, TopCoder, Inc. All rights reserved.
 */
public class BuildScriptGeneratorImplTests extends TestCase {
    /** Filename of the file containing an XML representation of a component. */
    private static final String COMPONENTXML_FILENAME = "test_files/component.xml";

    /** The name of the file used to write the build scripts to. */
    private static final String OUTPUT_FILENAME = "test_files/testout.xml";

    /** A Transformer to be used in some tests. */
    private Transformer transformer;

    /** An InputStream used in some tests. */
    private InputStream input;

    /** An OutputStream used in some tests. */
    private OutputStream output;

    /** A Result instance used in some tests. */
    private Result result;

    /** A Source instance used in some tests. */
    private Source source;

    /** A BuildScriptGenerator to run the tests on. */
    private BuildScriptGeneratorImpl generator;

    /** A BuildScriptGenerator based on a transformation that will always terminate. */
    private BuildScriptGeneratorImpl terminatingGenerator;

    /**
     * Set up the testing environment. This instantiates all the fields.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        transformer = TransformerFactory.newInstance().newTransformer();
        generator = new BuildScriptGeneratorImpl(transformer);
        input = new FileInputStream(COMPONENTXML_FILENAME);
        source = new StreamSource(input);
        output = new FileOutputStream(OUTPUT_FILENAME);
        result = new StreamResult(output);
        terminatingGenerator = new BuildScriptGeneratorImpl(TransformerFactory.newInstance().newTransformer(
                new StreamSource("test_files/terminating.xslt")));
    }

    /**
     * Tear down the testing environment. Remove the output file.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        new File(OUTPUT_FILENAME).delete();
    }

    /**
     * Verify behavior of the constructor. Check for <code>NullPointerException</code> if the transformer is null.
     */
    public void testConstructor_NullTransformer() {
        try {
            new BuildScriptGeneratorImpl(null);
            fail("No NullPointerException thrown, but transformer is null");
        } catch (NullPointerException e) {
            // Is expected
        }
    }

    /**
     * Verify behavior of the constructor. Check if the generator is instantiated.
     */
    public void testConstructor() {
        assertNotNull("Generator is not instantiated", generator);
    }

    /**
     * Verify behavior of the <code>generate(InputStream, OutputStream)</code> method. Check for
     * <code>NullPointerException</code> if the input is null.
     *
     * @throws GenerationProcessException to JUnit
     */
    public void testGenerateInputStreamOutputStream_NullInputStream() throws GenerationProcessException {
        try {
            generator.generate((InputStream) null, output);
            fail("No NullPointerException thrown, but input stream is null");
        } catch (NullPointerException e) {
            // Is expected
        }
    }

    /**
     * Verify behavior of the <code>generate(InputStream, OutputStream)</code> method. Check for
     * <code>NullPointerException</code> if the output is null.
     *
     * @throws GenerationProcessException to JUnit
     */
    public void testGenerateInputStreamOutputStream_NullOutputStream() throws GenerationProcessException {
        try {
            generator.generate(input, (OutputStream) null);
            fail("No NullPointerException thrown, but output stream is null");
        } catch (NullPointerException e) {
            // Is expected
        }
    }

    /**
     * Verify behavior of the <code>generate(InputStream, OutputStream)</code> method. Check for
     * <code>GenerationProcessException</code> if the input is not valid xml.
     */
    public void testGenerateInputStreamOutputStream_EmptyInput()  {
        try {
            generator.generate(new ByteArrayInputStream(new byte[0]), output);
            fail("No GenerationProcessException thrown, but input is not valid xml");
        } catch (GenerationProcessException e) {
            // Is expected
        }
    }

    /**
     * Verify behavior of the <code>generate(InputStream, OutputStream)</code> method. Check for
     * <code>GenerationProcessException</code> if the xslt template is terminated.
     */
    public void testGenerateInputStreamOutputStream_TemplateTermination() {
        try {
            terminatingGenerator.generate(input, output);
            fail("No GenerationProcessException thrown, but xslt template terminated");
        } catch (GenerationProcessException e) {
            // Is expected
        }
    }

    /**
     * Verify behavior of the <code>generate(InputStream, OutputStream)</code> method. Check if the output is correct.
     * The input stream that is used contains a valid xml representation of a component. As the transformer that is
     * used is an identity transformation, the output is validated against the file from wich the input was read.
     *
     * @throws Exception to JUnit
     */
    public void testGenerateInputStreamOutputStream_CheckOutput() throws Exception {
        // Generate the build script
        generator.generate(input, output);
        // Validate the output
        UnitTestHelper.validateOutput(COMPONENTXML_FILENAME, OUTPUT_FILENAME);
    }

    /**
     * Verify behavior of the <code>generate(ComponentVersion, OutputStream)</code> method. Check for
     * <code>NullPointerException</code> if the component is null.
     *
     * @throws GenerationProcessException to JUnit
     */
    public void testGenerateComponentVersionOutputStream_NullComponent() throws GenerationProcessException {
        try {
            generator.generate((ComponentVersion) null, output);
            fail("No NullPointerException thrown, but component is null");
        } catch (NullPointerException e) {
            // Is expected
        }
    }

    /**
     * Verify behavior of the <code>generate(ComponentVersion, OutputStream)</code> method. Check for
     * <code>NullPointerException</code> if the output is null.
     *
     * @throws GenerationProcessException to JUnit
     */
    public void testGenerateComponentVersionOutputStream_NullOutputStream() throws GenerationProcessException {
        try {
            generator.generate(UnitTestHelper.COMPONENT, (OutputStream) null);
            fail("No NullPointerException thrown, but output stream is null");
        } catch (NullPointerException e) {
            // Is expected
        }
    }

    /**
     * Verify behavior of the <code>generate(ComponentVersion, OutputStream)</code> method. Check for
     * <code>GenerationProcessException</code> if the xslt template is terminated.
     */
    public void testGenerateComponentVersionOutputStream_TemplateTermination() {
        try {
            terminatingGenerator.generate(UnitTestHelper.COMPONENT, output);
            fail("No GenerationProcessException thrown, but xslt template terminated");
        } catch (GenerationProcessException e) {
            // Is expected
        }
    }

    /**
     * Verify behavior of the <code>generate(ComponentVersion, OutputStream)</code> method. Check if the output is
     * correct. As the transformer that is used is an identity transformation, the output is validated against a file
     * containing a correct xml representation of the component that is used by this test.
     *
     * @throws Exception to JUnit
     */
    public void testGenerateComponentVersionOutputStream_CheckOutput() throws Exception {
        // Generate the build script
        generator.generate(UnitTestHelper.COMPONENT, output);
        // Validate the output
        UnitTestHelper.validateOutput(COMPONENTXML_FILENAME, OUTPUT_FILENAME);
    }

    /**
     * Verify behavior of the <code>generate(ComponentVersion, Result)</code> method. Check for
     * <code>NullPointerException</code> if the component is null.
     *
     * @throws GenerationProcessException to JUnit
     */
    public void testGenerateComponentVersionResult_NullComponent() throws GenerationProcessException {
        try {
            generator.generate((ComponentVersion) null, result);
            fail("No NullPointerException thrown, but component is null");
        } catch (NullPointerException e) {
            // Is expected
        }
    }

    /**
     * Verify behavior of the <code>generate(ComponentVersion, Result)</code> method. Check for
     * <code>NullPointerException</code> if the result is null.
     *
     * @throws GenerationProcessException to JUnit
     */
    public void testGenerateComponentVersionResult_NullResult() throws GenerationProcessException {
        try {
            generator.generate(UnitTestHelper.COMPONENT, (Result) null);
            fail("No NullPointerException thrown, but result is null");
        } catch (NullPointerException e) {
            // Is expected
        }
    }

    /**
     * Verify behavior of the <code>generate(ComponentVersion, Result)</code> method. Check for
     * <code>GenerationProcessException</code> if the xslt template is terminated.
     */
    public void testGenerateComponentVersionResult_TemplateTermination() {
        try {
            terminatingGenerator.generate(UnitTestHelper.COMPONENT, result);
            fail("No GenerationProcessException thrown, but xslt template terminated");
        } catch (GenerationProcessException e) {
            // Is expected
        }
    }

    /**
     * Verify behavior of the <code>generate(ComponentVersion, Result)</code> method. Check if the output is correct.
     * As the transformer that is used is an identity transformation, the output is validated against a file
     * containing a correct xml representation of the component that is used by this test.
     *
     * @throws Exception to JUnit
     */
    public void testGenerateComponentVersionResult_CheckOutput() throws Exception {
        // Generate the build script
        generator.generate(UnitTestHelper.COMPONENT, result);
        // Validate the output
        UnitTestHelper.validateOutput(COMPONENTXML_FILENAME, OUTPUT_FILENAME);
    }

    /**
     * Verify behavior of the <code>generate(Source, Result)</code> method. Check for <code>NullPointerException</code>
     * if the source is null.
     *
     * @throws GenerationProcessException to JUnit
     */
    public void testGenerateSourceResult_NullSource() throws GenerationProcessException {
        try {
            generator.generate((Source) null, result);
            fail("No NullPointerException thrown, but source is null");
        } catch (NullPointerException e) {
            // Is expected
        }
    }

    /**
     * Verify behavior of the <code>generate(Source, Result)</code> method. Check for <code>NullPointerException</code>
     * if the result is null.
     *
     * @throws GenerationProcessException to JUnit
     */
    public void testGenerateSourceResult_NullResult() throws GenerationProcessException {
        try {
            generator.generate(source, (Result) null);
            fail("No NullPointerException thrown, but result is null");
        } catch (NullPointerException e) {
            // Is expected
        }
    }

    /**
     * Verify behavior of the <code>generate(Source, Result)</code> method. Check for
     * <code>GenerationProcessException</code> if the xslt template is terminated.
     */
    public void testGenerateSourceResult_TemplateTermination() {
        try {
            terminatingGenerator.generate(source, result);
            fail("No GenerationProcessException thrown, but xslt template terminated");
        } catch (GenerationProcessException e) {
            // Is expected
        }
    }

    /**
     * Verify behavior of the <code>generate(Source, Result)</code> method. Check if the output is correct.
     * As the transformer that is used is an identity transformation, the output is validated against the xml file
     * that is used as input for the <code>StreamSource</code>, used as the source paramter.
     *
     * @throws Exception to JUnit
     */
    public void testGenerateSourceResult_CheckOutput() throws Exception {
        generator.generate(source, result);
        UnitTestHelper.validateOutput(COMPONENTXML_FILENAME, OUTPUT_FILENAME);
    }

    /**
     * Verify behavior of the <code>generate(ComponentVersion, Result)</code> method using the transformation to
     * create an ANT buildfile.
     *
     * @throws Exception to JUnit
     */
    public void testGenerateAntBuildFileFromComponentVersion() throws Exception {
        // Instantiate the generator with the proper transformation
        generator = new BuildScriptGeneratorImpl(TransformerFactory.newInstance().newTransformer(new StreamSource(
                new FileInputStream("test_files/build_ant.xslt"))));
        // Generate the build script
        generator.generate(UnitTestHelper.COMPONENT, result);
        // Validate the output
        UnitTestHelper.validateOutput("test_files/build.xml", OUTPUT_FILENAME);
    }

    /**
     * Verify behavior of the <code>generate(ComponentVersion, Result)</code> method using the transformation to create
     * a NANT buildfile.
     *
     * @throws Exception to JUnit
     */
    public void testGenerateNantBuildFileFromComponentVersion() throws Exception {
        // Instantiate the generator with the proper transformation
        generator = new BuildScriptGeneratorImpl(TransformerFactory.newInstance().newTransformer(new StreamSource(
                new FileInputStream("test_files/build_nant.xslt"))));
        // Generate the build script
        generator.generate(UnitTestHelper.COMPONENT, result);
        // Validate the output
        UnitTestHelper.validateOutput("test_files/default.build", OUTPUT_FILENAME);
    }

    /**
     * Verify behavior of the <code>transform</code> method. Check for <code>NullPointerException</code> if the
     * component is null.
     *
     * @throws GenerationProcessException to JUnit
     */
    public void testTransform_NullComponent() throws GenerationProcessException {
        try {
            generator.transform(null);
            fail("No NullPointerException thrown, but component is null");
        } catch (NullPointerException e) {
            // Is expected
        }
    }

    /**
     * Verify behavior of the <code>transform</code> method. Check if xml corresponding with the component object is
     * generated.
     *
     * @throws Exception to JUnit
     */
    public void testTransform() throws Exception {
        Node outputNode = generator.transform(UnitTestHelper.COMPONENT);
        UnitTestHelper.validateOutputNode(COMPONENTXML_FILENAME, outputNode);
    }
}