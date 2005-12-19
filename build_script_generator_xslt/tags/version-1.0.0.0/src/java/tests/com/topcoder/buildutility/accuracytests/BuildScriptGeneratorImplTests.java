/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 *
 * TCS Build Script Generator XSLT 1.0 (Accuracy Test)
 */
package com.topcoder.buildutility.accuracytests;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;

import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import junit.framework.TestCase;

import com.topcoder.buildutility.BuildScriptGeneratorImpl;
import com.topcoder.buildutility.component.ComponentVersion;
import com.topcoder.buildutility.component.ExternalComponentVersion;
import com.topcoder.buildutility.component.TechnologyType;

/**
 * <p>Unit test case for BuildScriptGeneratorImpl class.</p>
 * 
 * @author matmis
 * @version 1.0
 */
public class BuildScriptGeneratorImplTests extends TestCase {

    /**
     * Simple transform that is used to normalize and test templates.
     */
    private static final Transformer TRANSFORMER = TestHelper.loadTransformer("test_files/accuracy/trans.xslt");

    /**
     * Accessibility wrapper for package-private BuildScriptGeneratorImpl constructor.
     * 
     * @return BuildScriptGeneratorImpl instance
     */
    private BuildScriptGeneratorImpl newBuildScriptGeneratorImpl(Transformer transformer) {
        try {
            Constructor ctor = BuildScriptGeneratorImpl.class
                .getDeclaredConstructor(new Class[] {Transformer.class});
            ctor.setAccessible(true);
            Object object = ctor.newInstance(new Object[] {transformer});
            ctor.setAccessible(false);
            return (BuildScriptGeneratorImpl) object;
        } catch (Exception e) {
            fail("Failed to create BuildScriptGeneratorImpl, due to: " + e.getMessage());
            return null;
        }
    }

    /**
     * Test method generate(InputStream, OutputStream).
     * 
     * @throws Exception to JUnit
     */
    public void testGenerateInputStreamOutputStream() throws Exception {
        BuildScriptGeneratorImpl gen = newBuildScriptGeneratorImpl(TRANSFORMER);
        FileInputStream in = new FileInputStream("test_files/accuracy/input.xml");
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        gen.generate(in, out);
        
        // TestHelper.writeFile(new String(out.toByteArray()), "test_files/accuracy/result.xml");
        
        assertXmlEquals("the result is not transformed accurately", TestHelper
            .readFile("test_files/accuracy/result.xml"), new String(out.toByteArray()));
    }

    /**
     * Test method generate(Source, Result).
     * 
     * @throws Exception to JUnit
     */
    public void testGenerateSourceResult() throws Exception {
        BuildScriptGeneratorImpl gen = newBuildScriptGeneratorImpl(TRANSFORMER);
        FileInputStream in = new FileInputStream("test_files/accuracy/input.xml");
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        gen.generate(new StreamSource(in), new StreamResult(out));

        assertXmlEquals("the result is not transformed accurately", TestHelper
            .readFile("test_files/accuracy/result.xml"), new String(out.toByteArray()));
    }

    /**
     * Test method generate(Component, OutputStream).
     * 
     * @throws Exception to JUnit
     */
    public void testGenerateComponentOutputStream() throws Exception {
        BuildScriptGeneratorImpl gen = newBuildScriptGeneratorImpl(TRANSFORMER);
        ComponentVersion component = createComponent();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        gen.generate(component, out);
        
        // TestHelper.writeFile(new String(out.toByteArray()), "test_files/accuracy/result2.xml");

        assertXmlEquals("the result is not transformed accurately", TestHelper
            .readFile("test_files/accuracy/result2.xml"), new String(out.toByteArray()));
    }

    /**
     * Test method generate(Component, Result).
     * 
     * @throws Exception to JUnit
     */
    public void testGenerateComponentResult() throws Exception {
        BuildScriptGeneratorImpl gen = newBuildScriptGeneratorImpl(TRANSFORMER);
        ComponentVersion component = createComponent();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        gen.generate(component, new StreamResult(out));

        assertXmlEquals("the result is not transformed accurately", TestHelper
            .readFile("test_files/accuracy/result2.xml"), new String(out.toByteArray()));
    }

    /**
     * Creates and returns a test ComponentVersion instance.
     * 
     * @return ComponentVersion instance for test input
     */
    private ComponentVersion createComponent() {
        ComponentVersion cv = new ComponentVersion(1, "test component name", "test component description", "1.0a");
        cv.addAttribute("aName1", "aValue1");
        cv.addAttribute("aName2", "aValue2");
        
        cv.addTechnologyType(new TechnologyType(31, "tech1", "tech1 descr", false));
        cv.addTechnologyType(new TechnologyType(32, "tech2", null, true));

        ComponentVersion dep1 = new ComponentVersion(11, "test dep1 name", null, "1.1");
        dep1.addAttribute("aName11", "aValue11");
        cv.addComponentDependency(dep1);
        
        ComponentVersion dep2 = new ComponentVersion(12, "test dep2 name", "test dep2 descr", "1.2");
        dep2.addAttribute("aName12", "aValue12");
        dep1.addComponentDependency(dep2);
        
        ExternalComponentVersion extDep1 = new ExternalComponentVersion(21, "test extdep1",
            "test extdep1 descr", "3.1", "test extdep1 file");
        cv.addExternalComponentDependency(extDep1);

        ExternalComponentVersion extDep2 = new ExternalComponentVersion(22, "test extdep2",
            null, "3.2", "test extdep2 file");
        cv.addExternalComponentDependency(extDep2);

        return cv;
    }

    /**
     * <p>Compares two xml documents. Order of subelements, order of attributes does not matter.</p>
     * 
     * @param message The assertion message
     * @param xml1 first xml document to compare
     * @param xml2 second xml document to compare
     */
    private void assertXmlEquals(String message, String xml1, String xml2) {
        assertEquals(message, TestHelper.normalizeXml(xml1), TestHelper.normalizeXml(xml2));
    }

}
