/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.buildutility;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

import junit.framework.TestCase;

/**
 * Implementation of the Demo in the component specification. The loading of the templates and template hierarchies is
 * beyond the scope of this component, so it is not included in this demo.
 *
 * @author dmks, marijnk
 * @version 1.0
 * @copyright &copy; 2005, TopCoder, Inc. All rights reserved.
 */
public class Demo extends TestCase {
    /**
     * Demonstration of the creation of BuildScriptGenerator instances.
     *
     * @throws Exception to JUnit
     */
    public void testCreateGenerator() throws Exception {
        BuildScriptGeneratorFactory fac = new BuildScriptGeneratorFactoryImpl();
        // 1. create BuildScriptGenerator via input stream
        fac.createGenerator(new FileInputStream("test_files/build_ant.xslt"));
        // 2. create BuildScriptGenerator via template
        fac.createGenerator(UnitTestHelper.ANT_TEMPLATE);
        // 3. create BuildScriptGenerator via template hierarchy
        fac.createGenerator(UnitTestHelper.HIERARCHY, UnitTestHelper.COMPONENT);
    }

    /**
     * Demonstration of the generation of build scripts. ByteArrayOutputStream's are used to remove the need to delete
     * temporary files after the tests have been runned.
     *
     * @throws Exception to JUnit
     */
    public void testGenerateScript() throws Exception {
        // Create a script generator
        BuildScriptGenerator generator = new BuildScriptGeneratorFactoryImpl().createGenerator(
                new FileInputStream("test_files/build_ant.xslt"));
        // 1. generate script from input stream to output stream
        generator.generate(new FileInputStream("test_files/component.xml"), new ByteArrayOutputStream());
        // 2. generate script for component version object
        generator.generate(UnitTestHelper.COMPONENT, new ByteArrayOutputStream());
        // 3. generate script as a DOM Document
        generator.generate(UnitTestHelper.COMPONENT, new DOMResult());
        // 4. generate script from a Source instance
        generator.generate(new StreamSource(new FileInputStream("test_files/component.xml")), new DOMResult());
    }
}
