/**
 * Copyright (C) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.buildutility.stresstests;

import com.topcoder.buildutility.BuildScriptGenerator;
import com.topcoder.buildutility.BuildScriptGeneratorFactoryImpl;
import com.topcoder.buildutility.component.ComponentVersion;
import com.topcoder.buildutility.component.ExternalComponentVersion;
import com.topcoder.buildutility.component.TechnologyType;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


/**
 * Stress test for BuildScriptGenertorImpl.
 *
 * @author mgmg
 * @version 1.0
 */
public class BuildScriptGeneratorImplStressTest extends TestCase {
    /**
     * Output filename.
     */
    private static final String outputFilename = "test_files/stresstests/output.xml";

    /**
     * Input filename.
     */
    private static final String inputFilename = "test_files/stresstests/input.xml";

    /**
     * The test instance.
     */
    private BuildScriptGenerator instance = null;

    /**
     * The transformer.
     */
    private InputStream transformer = null;

    /**
     * The input xml.
     */
    private InputStream input = null;

    /**
     * The output script.
     */
    private OutputStream output = null;

    /**
     * The test count.
     */
    private int testCount = 200;

    /**
     * The sub item count.
     */
    private int subItemCount = 9990;

    /**
     * Test instance.
     */
    private ComponentVersion componentVersion = null;

    /**
     * Aggregate all tests for this class.
     *
     * @return
     */
    public static Test suite() {
        return new TestSuite(BuildScriptGeneratorImplStressTest.class);
    }

    /**
     * Create the test instance.
     */
    public void setUp() throws Exception {
        // Create large test file.
        Document document = DocumentBuilderFactory.newInstance()
                                                  .newDocumentBuilder()
                                                  .newDocument();
        Element root = document.createElement("component");
        root.setAttribute("id", "0");
        root.setAttribute("name", "component_name");
        root.setAttribute("description", "component_description");
        root.setAttribute("version", "1.0");
        root.setAttribute("package", "test.component");
        document.appendChild(root);

        Element dependencies = document.createElement("dependencies");

        for (int i = 0; i < subItemCount; i++) {
            Element component = document.createElement("dependent_component");
            component.setAttribute("id", String.valueOf(i + 1));
            component.setAttribute("name", "component" + (i + 1));
            component.setAttribute("description", "description" + (i + 1));
            component.setAttribute("version",
                "" + ((i % 10) + 1) + "." + ((i + 2) % 7));
            component.setAttribute("package", "test.component" + (i + 1));
            dependencies.appendChild(component);
        }

        root.appendChild(dependencies);

        Element externalDependencies = document.createElement(
                "external_dependencies");

        for (int i = 0; i < subItemCount; i++) {
            Element external = document.createElement("external_dependency");
            external.setAttribute("id", String.valueOf(i + 10000));
            external.setAttribute("description", "external" + (i + 10000));
            external.setAttribute("version",
                "" + (((i + 3) % 10) + 1) + "." + ((i + 5) % 7));
            external.setAttribute("filename", "external" + (i + 10000) +
                ".jar");
            externalDependencies.appendChild(external);
        }

        root.appendChild(externalDependencies);

        Element technologyType = document.createElement("technology_types");

        for (int i = 0; i < 11; i++) {
            Element technology = document.createElement("technology_type");
            technology.setAttribute("id", String.valueOf(i + 20000));
            technology.setAttribute("name", "technology" + (i + 20000));
            technology.setAttribute("description",
                "technology_description" + (i + 20000));
            technology.setAttribute("deprecated_status",
                ((i % 2) == 0) ? "true" : "false");
            technologyType.appendChild(technology);
        }

        root.appendChild(technologyType);

        Source source = new DOMSource(document);
        FileWriter writer = new FileWriter(inputFilename);
        Result target = new StreamResult(writer);
        Transformer output_transformer = TransformerFactory.newInstance()
                                                           .newTransformer();
        output_transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        output_transformer.transform(source, target);
        writer.close();

        // Create ComponentVersion instance.
        componentVersion = new ComponentVersion(0, "component_name",
                "component_description", "1.0");

        for (int i = 0; i < subItemCount; i++) {
            componentVersion.addComponentDependency(new ComponentVersion(i + 1,
                    "component" + (i + 1), "description" + (i + 1), "2.3"));
        }

        for (int i = 0; i < subItemCount; i++) {
            componentVersion.addExternalComponentDependency(new ExternalComponentVersion(i +
                    10000, "external" + (i + 10000),
                    "description" + (i + 10000), "3.5",
                    "external" + (i + 10000) + ".jar"));
        }

        for (int i = 0; i < 13; i++) {
            componentVersion.addTechnologyType(new TechnologyType(i + 20000,
                    "technology" + (i + 20000),
                    "technology_description" + (i + 20000), false));
        }

        // Create transformer, input source and output stream.
        transformer = new FileInputStream(
                "test_files/stresstests/build_ant.xsl");
        input = new FileInputStream(inputFilename);
        output = new FileOutputStream(outputFilename);
        instance = new BuildScriptGeneratorFactoryImpl().createGenerator(transformer);
    }

    /**
     * Clean the environment.
     */
    public void tearDown() throws Exception {
        try {
            transformer.close();
        } catch (Exception e) {
        }

        try {
            input.close();
        } catch (Exception e) {
        }

        try {
            output.close();
        } catch (Exception e) {
        }

        new File(inputFilename).delete();
        new File(outputFilename).delete();
    }

    /**
     * Stress test for generate(InputStream, OutputStream).
     *
     */
    public void testGenerator1() throws Exception {
        long start = System.currentTimeMillis();

        instance.generate(input, output);

        System.out.println("Time for generate(InputStream, OutputStream)  " +
            (System.currentTimeMillis() - start) + " ms");
    }

    /**
     * Stress test for generate(ComponentVersion, OutputStream).
     *
     */
    public void testGenerator2() throws Exception {
        long start = System.currentTimeMillis();

        instance.generate(componentVersion, output);

        System.out.println(
            "Time for generate(ComponentVersion, OutputStream)  " +
            (System.currentTimeMillis() - start) + " ms");
    }
}
