/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 *
 * TCS Build Script Generator XSLT 1.0 (Accuracy Test)
 */
package com.topcoder.buildutility.accuracytests;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Comparator;

import junit.framework.TestCase;

import com.topcoder.buildutility.BuildScriptGenerator;
import com.topcoder.buildutility.BuildScriptGeneratorFactoryImpl;
import com.topcoder.buildutility.component.ComponentVersion;
import com.topcoder.buildutility.template.Template;
import com.topcoder.buildutility.template.TemplateHierarchy;
import com.topcoder.buildutility.template.TemplateHierarchyPersistence;
import com.topcoder.buildutility.template.TemplateLoader;

/**
 * <p>Unit test case for BuildScriptGeneratorFactoryImpl class.</p>
 * 
 * @author matmis
 * @version 1.0
 */
public class BuildScriptGeneratorFactoryImplTests extends TestCase {

    /** The factory instance that is tested. */
    private final BuildScriptGeneratorFactoryImpl factory = new BuildScriptGeneratorFactoryImpl();

    /**
     * Test method createGenerator(InputStream).
     * 
     * @throws Exception to JUnit
     */
    public void testCreateGeneratorInputStream() throws Exception {
        FileInputStream in = new FileInputStream("test_files/accuracy/trans.xslt");
        BuildScriptGenerator generator = factory.createGenerator(in);

        checkGenerator(generator);
    }

    /**
     * Verfiies that generator is build with test transformation.
     * 
     * @param generator the generator instance to be checked
     * @throws Exception to JUnit
     */
    private void checkGenerator(BuildScriptGenerator generator) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        generator.generate(new FileInputStream("test_files/accuracy/input.xml"), out);
        assertXmlEquals("the generator was not created accurately", TestHelper
            .readFile("test_files/accuracy/result.xml"), new String(out.toByteArray()));
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

    /**
     * Test method createGenerator(Template).
     * 
     * @throws Exception to JUnit
     */
    public void testCreateGeneratorTemplate() throws Exception {
        Template template = createTemplateLoader().loadTemplateHierarchy("name").getTemplates()[0];

        BuildScriptGenerator generator = factory.createGenerator(template);

        checkGenerator(generator);
    }

    /**
     * Test method createGenerator(TemplateHierarchy).
     * 
     * @throws Exception to JUnit
     */
    public void testCreateGeneratorTemplateHierarchy() throws Exception {
        TemplateHierarchy templateHierarchy = createTemplateLoader().loadTemplateHierarchy("name");
        ComponentVersion component = new ComponentVersion(1, "name", null, "1.0");

        BuildScriptGenerator generator = factory.createGenerator(templateHierarchy, component);

        checkGenerator(generator);
    }

    /**
     * Create and return a test TemplateLoader.
     * 
     * @return a test TemplateLoader
     */
    private TemplateLoader createTemplateLoader() {
        return new TemplateLoader(new TemplateHierarchyPersistence() {

            public TemplateHierarchy getTemplateHierarchy(String name) {
                TemplateHierarchy hierarchy = new MyTemplateHierarchy(1, "tplH1", 1);
                hierarchy.addTemplate(new Template(1, "tpl1", "description", "filename", "trans.xslt"));
                hierarchy.addTemplate(new Template(2, "tpl2", "description", "filename", "not_exist.xslt"));
                return hierarchy;
            }

        }, "test_files/accuracy/");
    }

    private final class MyTemplateHierarchy extends TemplateHierarchy {

        public MyTemplateHierarchy(long id, String name, long parentId) {
            super(id, name, parentId);
        }

        public Template[] getTemplates() {
            // impose order on returned array
            Template[] templates = super.getTemplates();
            Arrays.sort(templates, new Comparator() {

                public int compare(Object o1, Object o2) {
                    Template t1 = (Template) o1;
                    Template t2 = (Template) o2;
                    return t1.getId() < t2.getId() ? -1 : 1;
                }

            });
            return templates;
        }
    }

}
