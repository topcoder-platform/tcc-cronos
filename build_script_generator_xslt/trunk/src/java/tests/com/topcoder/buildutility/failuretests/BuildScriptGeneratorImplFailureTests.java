/*
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.buildutility.failuretests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

import com.topcoder.buildutility.BuildScriptGeneratorImpl;
import com.topcoder.buildutility.GenerationProcessException;
import com.topcoder.buildutility.component.ComponentVersion;
import com.topcoder.buildutility.component.TechnologyType;

/**
 * <p>
 * Failure tests on <code>BuildScriptGeneratorImpl</code>.
 * </p>
 * @author albertwang
 * @version 1.0
 */
public class BuildScriptGeneratorImplFailureTests extends TestCase {
    /** Instance of BuildScriptGeneratorImpl used in this test. */
    private BuildScriptGeneratorImpl generator = null;
    /** Instance of ComponentVersion used in this test. */
    private ComponentVersion component1 = null, component2 = null;
    /** XSL file to initialize a Transformer. */
    private static final String XSL_FILE = "test_files/failuretests/build_ant.xsl";
    /** Output file. */
    private static final String OUTPUT_FILE = "test_files/failuretests/build.xml";
    /** XML file as the component source. */
    private static final String COMPONENT_XML_FILE = "test_files/failuretests/component.xml";
    /**
     * Set up environment.
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer(new StreamSource(new FileInputStream(XSL_FILE)));
        generator = TestsHelper.createBuildScriptGeneratorImpl(transformer);
        component1 = new ComponentVersion(8888, "Demo component", "Just a demo", "1.0");
        component2 = new ComponentVersion(9999, "Base component", "Base", "1.0");
        component2.addTechnologyType(new TechnologyType(1111, "Java", "Java", false));
        component2.addTechnologyType(new TechnologyType(1112, "JSP", "JSP", false));
        component1.addComponentDependency(component2);
        component1.addTechnologyType(new TechnologyType(1111, "Java", "Java", false));
    }
    /**
     * Clear the environment.
     */
    public void tearDown() {
        new File(OUTPUT_FILE).delete();
    }
    /**
     * Failure test on method generate(InputStream source, OutputStream result)
     * with null source.
     * @throws Exception to JUnit
     */
    public void testGenerate1_NullSource() throws Exception {
        try {
            generator.generate((InputStream) null, new ByteArrayOutputStream());
            fail("NullPointerException expected.");
        } catch (NullPointerException ex) {
            // expected
        }
    }
    /**
     * Failure test on method generate(InputStream source, OutputStream result)
     * with null closed source stream.
     * @throws Exception to JUnit
     */
    public void testGenerate1_SourceStreamClosed() throws Exception {
        InputStream source = new FileInputStream(COMPONENT_XML_FILE);
        source.close();
        try {
            generator.generate(source, new ByteArrayOutputStream());
            fail("GenerationProcessException expected.");
        } catch (GenerationProcessException ex) {
            // expected
        }
    }
    
    /**
     * Failure test on method generate(InputStream source, OutputStream result)
     * with null closed result stream.
     * @throws Exception to JUnit
     */
    public void testGenerate1_ResultStreamClosed() throws Exception {
        InputStream source = new FileInputStream(COMPONENT_XML_FILE);
        OutputStream result = new FileOutputStream(OUTPUT_FILE);
        result.close();
        try {
            generator.generate(source, result);
            fail("GenerationProcessException expected.");
        } catch (GenerationProcessException ex) {
            // expected
        }
    }
    
    /**
     * Failure test on method generate(InputStream source, OutputStream result)
     * with null invalid source stream.
     * @throws Exception to JUnit
     */
    public void testGenerate1_InvalidSource() throws Exception {
        InputStream source = new ByteArrayInputStream("build script generator xslt".getBytes());
        OutputStream result = new ByteArrayOutputStream();
        try {
            generator.generate(source, result);
            fail("GenerationProcessException expected.");
        } catch (GenerationProcessException ex) {
            // expected
        }
    }
    
    /**
     * Failure test on method generate(InputStream source, OutputStream result)
     * with null result.
     * @throws Exception
     */
    public void testGenerate1_NullResult() throws Exception {
        try {
            generator.generate(new FileInputStream(COMPONENT_XML_FILE), null);
            fail("NullPointerException expected.");
        } catch (NullPointerException ex) {
            // expected
        }
    }
    
    /**
     * Failure test on method generate(ComponentVersion component, OutputStream result)
     * with null component.
     * @throws Exception to JUnit
     */
    public void testGenerate2_NullComponent() throws Exception {
        try {
            generator.generate((ComponentVersion) null, new ByteArrayOutputStream());
            fail("NullPointerException expected.");
        } catch (NullPointerException ex) {
            // expected
        }    
    }
    /**
     * Failure test on method generate(ComponentVersion component, OutputStream result)
     * with null result.
     * @throws Exception to JUnit
     */
    public void testGenerate2_NullResult() throws Exception {
        try {
            generator.generate(component1, (OutputStream) null);
            fail("NullPointerException expected.");
        } catch (NullPointerException ex) {
            // expected
        }    
    }
    
    /**
     * Failure test on method generate(ComponentVersion component, OutputStream result)
     * with closed result stream.
     * @throws Exception to JUnit
     */
    public void testGenerate2_ResultStreamClosed() throws Exception {
        OutputStream result = new FileOutputStream(OUTPUT_FILE);
        result.close();
        try {
            generator.generate(component1, result);
            fail("GenerationProcessException expected.");
        } catch (GenerationProcessException ex) {
            // expected
        }    
    }
    
    /**
     * Failure test on method generate(ComponentVersion component, Result result) with
     * null component.
     * @throws Exception to JUnit
     */
    public void testGenerate3_NullComponent() throws Exception {
        try {
            generator.generate((ComponentVersion) null, new StreamResult(new ByteArrayOutputStream()));
            fail("NullPointerException expected.");
        } catch (NullPointerException ex) {
            // expected
        }
    }
    
    /**
     * Failure test on method generate(ComponentVersion component, Result result) with
     * null result.
     * @throws Exception to JUnit
     */
    public void testGenerate3_NullResult() throws Exception {
        try {
            generator.generate(component1, (Result) null);
            fail("NullPointerException expected.");
        } catch (NullPointerException ex) {
            // expected
        }
    }
    
    /**
     * Failure test on method generate(ComponentVersion component, Result result) with
     * invalid result in which the stream is closed.
     * @throws Exception to JUnit
     */
    public void testGenerate3_ResultStreamClosed() throws Exception {
        OutputStream result = new FileOutputStream(OUTPUT_FILE);
        result.close();
        try {
            generator.generate(component1, new StreamResult(result));
            fail("GenerationProcessException expected.");
        } catch (GenerationProcessException ex) {
            // expected
        }
    }
    
    /**
     * Failure test on method generate(Source source, Result result) with null source.
     * @throws Exception to JUnit
     */
    public void testGenerate4_NullSource() throws Exception {
        try {
            generator.generate((Source) null, new StreamResult(new ByteArrayOutputStream()));
            fail("NullPointerException expected.");
        } catch (NullPointerException ex) {
            // expected
        }
    }
    
    /**
     * Failure test on method generate(Source source, Result result) with null result.
     * @throws Exception to JUnit
     */
    public void testGenerate4_NullResult() throws Exception {
        try {
            generator.generate(new StreamSource(COMPONENT_XML_FILE), (Result) null);
            fail("NullPointerException expected.");
        } catch (NullPointerException ex) {
            // expected
        }
    }
    
    /**
     * Failure test on method generate(Source source, Result result) with invalid source
     * in which the stream is closed.
     * @throws Exception to JUnit
     */
    public void testGenerate4_SourceStreamClosed() throws Exception {
        InputStream source = new FileInputStream(COMPONENT_XML_FILE);
        OutputStream result = new FileOutputStream(OUTPUT_FILE);
        source.close();
        try {
            generator.generate(new StreamSource(source), new StreamResult(result));
            fail("GenerationProcessException expected.");
        } catch (GenerationProcessException ex) {
            // expected
        }
    }
    
    /**
     * Failure test on method generate(Source source, Result result) with invalid source.
     * @throws Exception to JUnit
     */
    public void testGenerate4_InvalidSourceStream() throws Exception {
        InputStream source = new ByteArrayInputStream("invalid".getBytes());
        OutputStream result = new FileOutputStream(OUTPUT_FILE);
        try {
            generator.generate(new StreamSource(source), new StreamResult(result));
            fail("GenerationProcessException expected.");
        } catch (GenerationProcessException ex) {
            // expected
        }
    }
    
    /**
     * Failure test on method generate(Source source, Result result) with invalid result
     * in which the stream is closed.
     * @throws Exception to JUnit
     */
    public void testGenerate4_ResultStreamClosed() throws Exception {
        InputStream source = new FileInputStream(COMPONENT_XML_FILE);
        OutputStream result = new FileOutputStream(OUTPUT_FILE);
        result.close();
        try {
            generator.generate(new StreamSource(source), new StreamResult(result));
            fail("GenerationProcessException expected.");
        } catch (GenerationProcessException ex) {
            // expected
        }
    }
}