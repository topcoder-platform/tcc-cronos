/*
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.buildutility.failuretests;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import com.topcoder.buildutility.BuildScriptGeneratorFactoryImpl;
import com.topcoder.buildutility.GeneratorCreationException;
import com.topcoder.buildutility.component.ComponentVersion;
import com.topcoder.buildutility.template.Template;
import com.topcoder.buildutility.template.TemplateHierarchy;

import junit.framework.TestCase;

/**
 * Failure tests on <code>BuildScriptGeneratorFactoryImpl</code>.
 * @author albertwang
 * @version 1.0
 */
public class BuildScriptGeneratorFactoryImplFailureTests extends TestCase {
    /** Instance of BuildScriptGeneratorFactoryImpl used in this test. */
    private BuildScriptGeneratorFactoryImpl factory = null;
    /** Instance of TemplateHierarchy used in this test. */
    private TemplateHierarchy hierarchy = null;
    /** XSL file to initialize a Transformer. */
    private static final String XSL_FILE = "test_files/failuretests/build_ant.xsl";
    /**
     * Set up environment.
     */
    public void setUp() {
        factory = new BuildScriptGeneratorFactoryImpl();
        hierarchy = new TemplateHierarchy(1, "h1", -1);
        hierarchy.addTemplate(new Template(41, "template_ant", "ant template", "test_files/failuretests/build_ant.xsl", "build_ant.xsl"));
    }
    
    /**
     * Failure test on method createGenerator(InputStream source) with null source.
     * @throws Exception to JUnit
     */
    public void testCreateGenerator1_NullSource() throws Exception {
        try {
            factory.createGenerator((InputStream) null);
            fail("NullPointerException expected.");
        } catch (NullPointerException ex) {
            // expected
        }
    }
    /**
     * Failure test on method createGenerator(InputStream source) with closed source.
     * @throws Exception to JUnit
     */
    public void testCreateGenerator1_SourceStreamClosed() throws Exception {
        InputStream source = new FileInputStream(XSL_FILE);
        source.close();
        try {
            factory.createGenerator(source);
            fail("GeneratorCreationException expected.");
        } catch (GeneratorCreationException ex) {
            // expected
        }
    }
    
    /**
     * Failure test on method createGenerator(InputStream source) with invalid source.
     * @throws Exception to JUnit
     */
    public void testCreateGenerator1_InvalidSource() throws Exception {
        InputStream source = new ByteArrayInputStream("invalid".getBytes());
        try {
            factory.createGenerator(source);
            fail("GeneratorCreationException expected.");
        } catch (GeneratorCreationException ex) {
            // expected
        }
    }
    
    /**
     * Failure test on method createGenerator(Template template) with null template.
     * @throws Exception to JUnit
     */
    public void testCreateGenerator2_NullTemplate() throws Exception {
        try {
            factory.createGenerator((Template) null);
            fail("NullPointerException expected.");
        } catch (NullPointerException ex) {
            // expected
        }
    }
    
    /**
     * Failure test on method createGenerator(Template template) with invalid template.
     * @throws Exception to JUnit
     */
    public void testCreateGenerator2_InvalidTemplate() throws Exception {
        try {
            Template template = new Template(888, "description", "name", "foo", "uri");
            factory.createGenerator(template);
            fail("GeneratorCreationException expected.");
        } catch (GeneratorCreationException ex) {
            // expected
        }
    }
    /**
     * Failure test on method createGenerator(TemplateHierarchy templateHierarchy,ComponentVersion component)
     * with null templateHierarchy.
     * @throws Exception to JUnit
     */
    public void testCreateGenerator3_NullTemplateHierarchy() throws Exception {
        try {
            factory.createGenerator(null, new ComponentVersion(888, "name", "desc", "1.0"));
            fail("NullPointerException expected.");
        } catch (NullPointerException ex) {
            // expected
        }
    }
    
    /**
     * Failure test on method createGenerator(TemplateHierarchy templateHierarchy,ComponentVersion component)
     * with null component.
     * @throws Exception to JUnit
     */
    public void testCreateGenerator3_NullComponent() throws Exception {
        try {
            factory.createGenerator(hierarchy, null);
            fail("NullPointerException expected.");
        } catch (NullPointerException ex) {
            // expected
        }
    }
    /**
     * Failure test on method createGenerator(TemplateHierarchy templateHierarchy,ComponentVersion component)
     * with invalid hierarchy.
     * @throws Exception to JUnit
     */
    public void testCreateGenerator3_InvalidHierarchy() throws Exception {
        try {
            factory.createGenerator(new TemplateHierarchy(1, "hie", -1), new ComponentVersion(888, "name", "desc", "1.0"));
            fail("GeneratorCreationException expected.");
        } catch (GeneratorCreationException ex) {
            // expected
        }
    }
    
}
