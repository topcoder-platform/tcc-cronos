/**
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.buildutility.stresstests;

import com.topcoder.buildutility.TemplateSelector;
import com.topcoder.buildutility.component.ComponentVersion;
import com.topcoder.buildutility.template.Template;
import com.topcoder.buildutility.template.TemplateHierarchy;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Date;


/**
 * <p>Stress test for class TemplateSelectorStressTest.</p>
 *
 * @author mgmg
 * @version 1.0
 */
public class TemplateSelectorStressTest extends TestCase {
    public static Test suite() {
        return new TestSuite(TemplateSelectorStressTest.class);
    }

    /**
     * Test selectTemplates().
     *
     */
    public void testSelectTemplates1() {
        long id = 1;
        TemplateHierarchy root = new TemplateHierarchy(id, "start", id);
        root.addTemplate(new Template(id, "start", "foo", "foo", "foo"));

        TemplateHierarchy javaNode = addChild(root, "Java", ++id);
        TemplateHierarchy dotNetNode = addChild(root, ".NET", ++id);
        TemplateHierarchy webServerNode = addChild(javaNode, "WebServer", ++id);
        TemplateHierarchy weblogicNode = addChild(javaNode, "Weblogic", ++id);
        TemplateHierarchy debugNode = addChild(webServerNode, "Debug", ++id);
        TemplateHierarchy debugOnNode = addChild(debugNode, "DebugOn", ++id);
        TemplateHierarchy debugOffNode = addChild(debugNode, "DebugOff", ++id);
        ComponentVersion componentVersion = new ComponentVersion(0);
        componentVersion.setTechnologyType("Java");
        componentVersion.setAttribute("AppServer", "WebServer");
        componentVersion.setAttribute("Debug", "DebugOn");

        Template[] templates = null;

        long start = new Date().getTime();

        // Stress test for small data.
        for (int i = 0; i < 5000; i++) {
            templates = new TemplateSelector().selectTemplates(componentVersion, root);
        }

        System.out.println("Stress test1 for selectTemplates method - " + (new Date().getTime() - start)
            + "ms");

        assertTrue("The array returned should not be empty.", templates.length > 0);
        assertTrue("The templates returned is wrong", templates[0].getName().equals("DebugOn"));
    }

    /**
     * Test selectTemplates().
     *
     */
    public void testSelectTemplates2() {
        long id = 1;
        TemplateHierarchy root = new TemplateHierarchy(id, "root", id);
        TemplateHierarchy current = root;
        ComponentVersion componentVersion = new ComponentVersion(0);

        for (int i = 0; i < 100; ++i) {
            for (int j = 0; j < (100 + i); ++j) {
                addChild(current, "nochild_" + i + "_" + j, ++id);
            }

            current = addChild(current, "haschild_" + i, ++id);
            componentVersion.setAttribute("key" + i, "haschild_" + i);
        }

        long start = new Date().getTime();

        Template[] templates = new TemplateSelector().selectTemplates(componentVersion, root);

        System.out.println("Stress test2 for selectTemplates method - " + (new Date().getTime() - start)
            + "ms");

        assertTrue("The array returned should not be empty.", templates.length > 0);
        assertTrue("The templates returned is wrong", templates[0].getName().equals("haschild_99"));
    }

    /**
     * Add a child to the specified TemplateHierarchy.
     *
     * @param parent
     * @param name
     * @param id
     * @return
     */
    private TemplateHierarchy addChild(TemplateHierarchy parent, String name, long id) {
        TemplateHierarchy child = new TemplateHierarchy(id, name, parent.getId());
        child.addTemplate(new Template(id, name, "stress", "stress", "stress"));
        parent.addNestedHierarchy(child);

        return child;
    }
}
