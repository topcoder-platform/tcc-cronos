/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 *
 * TCS Template Selector 1.0 (Demo)
 */
package com.topcoder.buildutility;

import junit.framework.TestCase;
import com.topcoder.buildutility.component.ComponentVersion;
import com.topcoder.buildutility.template.Template;
import com.topcoder.buildutility.template.TemplateHierarchy;


/**
 * <p>Component demonstration for Template Selector.</p>
 *
 * <p>This component is used to locate all the templates corresponding to specified
 * component version using the supplied template hierarchy as a start point.</p>
 *
 * @author isv
 * @author TCSDEVELOPER
 *
 * @version 1.0
 */
public class Demo extends TestCase {

    /**
     * A TemplateHierarchy instance for testing.
     */
    private TemplateHierarchy root = null;

    /**
     * A TemplateHierarchy instance for testing.
     */
    private TemplateHierarchy javaNode = null;

    /**
     * A TemplateHierarchy instance for testing.
     */
    private TemplateHierarchy dotNetNode = null;

    /**
     * A TemplateHierarchy instance for testing.
     */
    private TemplateHierarchy webServerNode = null;

    /**
     * A TemplateHierarchy instance for testing.
     */
    private TemplateHierarchy weblogicNode = null;

    /**
     * A TemplateHierarchy instance for testing.
     */
    private TemplateHierarchy debugNode = null;

    /**
     * A TemplateHierarchy instance for testing.
     */
    private TemplateHierarchy debugOnNode = null;

    /**
     * A TemplateHierarchy instance for testing.
     */
    private TemplateHierarchy debugOffNode = null;


    /**
     * Initializes the template hierarchies used for demonstration.
     */
    protected void setUp() {

        // Create following template hierarchies
        //                      Start
        //                      /    \
        //                    Java    .NET
        //                    /   \
        //             WebServer Weblogic
        //                  /
        //                Debug
        //                /   \
        //            DebugOn DebugOff


        // build the hierarchies
        long id = 1;
        root = new TemplateHierarchy(id, "start", id);
        root.addTemplate(new Template(id, "start", "foo", "foo", "foo"));
        javaNode = addChild(root, "Java", ++id);
        dotNetNode = addChild(root, ".NET", ++id);
        webServerNode = addChild(javaNode, "WebServer", ++id);
        weblogicNode = addChild(javaNode, "Weblogic", ++id);
        debugNode = addChild(webServerNode, "Debug", ++id);
        debugOnNode = addChild(debugNode, "DebugOn", ++id);
        debugOffNode = addChild(debugNode, "DebugOff", ++id);

    }

    /**
     * Add a child template hierarchy to the given parent with the given name and id.
     * The added template hierarchy has only one template and its name is same with the template hierarchy's.
     *
     * @param parent the parent template hierarchy
     * @param name the name used to create the child template hierarchy
     * @param id the id used to create the child template hierarchy
     * @return the added child template hierarchy
     */
    private TemplateHierarchy addChild(TemplateHierarchy parent, String name, long id) {
        TemplateHierarchy child = new TemplateHierarchy(id, name, parent.getId());
        child.addTemplate(new Template(id, name, "foo", "foo", "foo"));
        parent.addNestedHierarchy(child);
        return child;
    }

    /**
     * <p>Shows how to create the TemplateSelector instance and locate all the templates corresponding to specified
     * component version using the supplied template hierarchy as a start point.</p>
     */
    public void testTemplateSelectorDemo() {

        // following template hierarchies are used to show how this component works.
        //                      Start
        //                      /    \
        //                    Java    .NET
        //                    /   \
        //             WebServer Weblogic
        //                  /
        //                Debug
        //                /   \
        //            DebugOn DebugOff

        // create a new TemplateSelector instance
        TemplateSelector selector = new TemplateSelector();

        // create a ComponentVersion instance used to select the required templates
        ComponentVersion componentVersion = new ComponentVersion(1);

        // the componentVersion contains no attribute and technology type, an empty array should be returned.
        Template[] templates = selector.selectTemplates(componentVersion, root);
        verifyTemplates(templates, root);

        // set a new technology type - Java
        componentVersion.setTechnologyType("Java");

        // The Java templates should be returned
        templates = selector.selectTemplates(componentVersion, root);
        verifyTemplates(templates, javaNode);

        // add an attribute pair - (AppServer, WebServer)
        componentVersion.setAttribute("AppServer", "WebServer");

        // The WebServer templates should be returned
        templates = selector.selectTemplates(componentVersion, root);
        verifyTemplates(templates, webServerNode);

        // add another attribute pair - (Debug, DebugOn)
        componentVersion.setAttribute("Debug", "DebugOn");

        // The DebugOn templates should be returned,
        // since both attribute name and value are used to selecte the templates
        templates = selector.selectTemplates(componentVersion, root);
        verifyTemplates(templates, debugOnNode);

        // add another attribute pair - (Language, .NET)
        componentVersion.setAttribute("Language", ".NET");

        // The .NET templates should be returned,
        // since the priority of attribute is higher than technology type
        templates = selector.selectTemplates(componentVersion, root);
        verifyTemplates(templates, dotNetNode);
    }

    /**
     * Verifies whether the template array belongs to the given TemplateHierarchy instance.
     *
     * @param templates the template array to be verified.
     * @param hierarchy the TemplateHierarchy instance which the templates belong to
     */
    private void verifyTemplates(Template[] templates, TemplateHierarchy hierarchy) {

        // the array should has only 1 element
        assertEquals("the template array length is incorrect", 1, templates.length);

        // the template's name should be same with the template hierarchy's
        assertEquals("the returned template is incorrect", hierarchy.getName(), templates[0].getName());

    }

}
