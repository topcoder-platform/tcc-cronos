/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 *
 * TCS Template Selector 1.0 (Unit Test)
 */
package com.topcoder.buildutility;

import junit.framework.TestCase;
import com.topcoder.buildutility.component.ComponentVersion;
import com.topcoder.buildutility.template.Template;
import com.topcoder.buildutility.template.TemplateHierarchy;

import java.util.Date;

/**
 * <p>Unit test cases for TemplateSelector.</p>
 *
 * @author TCSDEVELOPER
 *
 * @version 1.0
 */
public class TemplateSelectorTests extends TestCase {

    /**
     * The number of iteration for stress test.
     */
    private static final int ITERATION_NUMBER = 300;

    /**
     * A TemplateSelector instance to test.
     */
    private TemplateSelector selector = new TemplateSelector();

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
     * A ComponentVersion instance for testing.
     */
    private ComponentVersion componentVersion = null;

    /**
     * Initialize the TemplateHierarchy and ComponentVersion instances used for testing.
     * Each TemplateHierarchy has only one Template, and the Template'name is same with the TemplateHierarchy's.
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

        // create the ComponentVersion instance
        componentVersion = new ComponentVersion(0);

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
     * <p>Tests whether the TemplateSelector class could be instantiated properly.</p>
     */
    public void testTemplateSelector1() {
        assertNotNull("Unable to instantiate TemplateSelector.", new TemplateSelector());
    }

    /**
     * <p>Tests whether the TemplateSelector implements TemplateSelectionAlgorithm interface.</p>
     */
    public void testTemplateSelector2() {
        assertTrue("Unable to instantiate TemplateSelector.",
            new TemplateSelector() instanceof TemplateSelectionAlgorithm);
    }

    /**
     * <p>Tests the selectTemplates method.</p>
     *
     * <p>The componentVersion is null, NullPointerException should be thrown.</p>
     */
    public void testSelectTemplatesNullComponentVersion() {
        try {
            // componentVersion is null, NullPointerException should be thrown
            selector.selectTemplates(null, root);
            fail("NullPointerException should be thrown");
        } catch (NullPointerException e) {
            // success
        }
    }

    /**
     * <p>Tests the selectTemplates method.</p>
     *
     * <p>The templateHierarchy is null, NullPointerException should be thrown.</p>
     */
    public void testSelectTemplatesNullTemplateHierarchy() {
        try {
            // templateHierarchy is null, NullPointerException should be thrown
            selector.selectTemplates(componentVersion, null);
            fail("NullPointerException should be thrown");
        } catch (NullPointerException e) {
            // success
        }
    }

    /**
     * <p>Tests the selectTemplates method.</p>
     *
     * <p>If the TemplateHierarchy has no child, the root's templates should be returned.</p>
     */
    public void testSelectTemplates1() {

        // root has no child node
        root = new TemplateHierarchy(0, "root", 0);
        root.addTemplate(new Template(0, "root", "foo", "foo", "foo"));

        // the TemplateHierarchy has no child, root's templates should be returned
        Template[] templates = selector.selectTemplates(componentVersion, root);

        // verifies the returned template array
        verifyTemplates(templates, root);

    }

    /**
     * <p>Tests the selectTemplates method.</p>
     *
     * <p>The technology type is Java, so the templates of javaNode will be returned.</p>
     */
    public void testSelectTemplates2() {

        componentVersion.setTechnologyType("Java");

        // the technology type is Java, so the templates of javaNode will be returned
        Template[] templates = selector.selectTemplates(componentVersion, root);

        // verifies the returned template array
        verifyTemplates(templates, javaNode);

    }

    /**
     * <p>Tests the selectTemplates method.</p>
     *
     * <p>The technology type is .NET, so the templates of dotNetNode will be returned.</p>
     */
    public void testSelectTemplates3() {

        componentVersion.setTechnologyType(".NET");

        // the technology type is .NET, so the templates of dotNetNode will be returned
        Template[] templates = selector.selectTemplates(componentVersion, root);

        // verifies the returned template array
        verifyTemplates(templates, dotNetNode);

    }

    /**
     * <p>Tests the selectTemplates method.</p>
     *
     * <p>Tests whether the templates could be retrieved properly.</p>
     */
    public void testSelectTemplates4() {

        componentVersion.setTechnologyType("Java");
        componentVersion.setAttribute("AppServer", "WebServer");

        // the templates of webServerNode should be returned
        Template[] templates = selector.selectTemplates(componentVersion, root);

        // verifies the returned template array
        verifyTemplates(templates, webServerNode);

    }

    /**
     * <p>Tests the selectTemplates method.</p>
     *
     * <p>Tests whether the templates could be retrieved properly.</p>
     */
    public void testSelectTemplates5() {

        componentVersion.setTechnologyType("Java");
        componentVersion.setAttribute("AppServer", "WebServer");
        componentVersion.setAttribute("Debug", "DebugOn");

        // the templates of debugOnNode should be returned
        Template[] templates = selector.selectTemplates(componentVersion, root);

        // verifies the returned template array
        verifyTemplates(templates, debugOnNode);

    }

    /**
     * <p>Tests the selectTemplates method.</p>
     *
     * <p>Tests whether the templates could be retrieved properly.</p>
     */
    public void testSelectTemplates6() {

        componentVersion.setTechnologyType("Java");
        componentVersion.setAttribute("AppServer", "Weblogic");
        componentVersion.setAttribute("Debug", "DebugOn");

        // the templates of weblogicNode should be returned
        Template[] templates = selector.selectTemplates(componentVersion, root);

        // verifies the returned template array
        verifyTemplates(templates, weblogicNode);

    }

    /**
     * <p>Tests the selectTemplates method.</p>
     *
     * <p>Tests whether the templates could be retrieved properly.</p>
     */
    public void testSelectTemplates7() {

        componentVersion.setTechnologyType("Java");
        componentVersion.setAttribute("Language", ".NET");
        componentVersion.setAttribute("AppServer", "Weblogic");
        componentVersion.setAttribute("Debug", "DebugOn");

        // the templates of dotNetNode should be returned,
        // since the priority of attribute is higher than technology type
        Template[] templates = selector.selectTemplates(componentVersion, root);

        // verifies the returned template array
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

    /**
     * <p>Stress tests for selectTemplates method.</p>
     */
    public void testSelectTemplatesStress() {

        // build the hierarchies like
        //                              root
        //                       ________|___________
        //                      |        |           |
        //                     V_1     I_1_1  ...  I_1_N
        //              ________|___________
        //             |        |           |
        //            V_2     I_2_1  ...  I_2_N
        //             |
        //            ...

        long id = 1;
        root = new TemplateHierarchy(id, "root", id);
        TemplateHierarchy current = root;
        for (int i = 0; i < ITERATION_NUMBER; ++i) {
            for (int j = 0; j < ITERATION_NUMBER; ++j) {
                addChild(current, "invalid_" + i + "_" + j, ++id);
            }
            current = addChild(current, "valid_" + i, ++id);
            componentVersion.setAttribute("key_" + i, "valid_" + i);
        }

        // starts stress test
        long start = new Date().getTime();

        // get the templates
        // current O(MlogN) algorithm is about 20 times faster than O(MN) algorithm in this test case
        Template[] templates = selector.selectTemplates(componentVersion, root);

        // ends stress test
        System.out.println("Stress test for selectTemplates method - " + (new Date().getTime() - start) + "ms");

        // the templates of last valid node should be returned
        verifyTemplates(templates, current);

    }

}
