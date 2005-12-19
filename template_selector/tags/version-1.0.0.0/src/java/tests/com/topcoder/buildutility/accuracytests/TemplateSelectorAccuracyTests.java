package com.topcoder.buildutility.accuracytests;

import junit.framework.TestCase;
import com.topcoder.buildutility.component.ComponentVersion;
import com.topcoder.buildutility.template.Template;
import com.topcoder.buildutility.template.TemplateHierarchy;
import com.topcoder.buildutility.TemplateSelector;
import com.topcoder.buildutility.TemplateSelectionAlgorithm;


/**
 * <p>Accuracy test cases for TemplateSelector.</p>
 *
 * @author southwang
 *
 * @version 1.0
 */
public class TemplateSelectorAccuracyTests extends TestCase {

    /**
     * A TemplateSelector instance.
     */
    private TemplateSelector selector = new TemplateSelector();

    /**
     * A TemplateHierarchy instance.
     */
    private TemplateHierarchy node1 = null;

    /**
     * A TemplateHierarchy instance.
     */
    private TemplateHierarchy node2 = null;

    /**
     * A TemplateHierarchy instance.
     */
    private TemplateHierarchy node3 = null;

    /**
     * A TemplateHierarchy instance.
     */
    private TemplateHierarchy node4 = null;

    /**
     * A TemplateHierarchy instance.
     */
    private TemplateHierarchy node5 = null;

    /**
     * A TemplateHierarchy instance.
     */
    private TemplateHierarchy node6 = null;

    /**
     * A TemplateHierarchy instance.
     */
    private TemplateHierarchy node7 = null;

    /**
     * A TemplateHierarchy instance.
     */
    private TemplateHierarchy node8 = null;

    /**
     * A ComponentVersion instance.
     */
    private ComponentVersion componentVersion = null;

    /**
     * Set up.
     */
    protected void setUp() {

        componentVersion = new ComponentVersion(1);

        node1 = new TemplateHierarchy(1, "Node1", 1);
        node1.addTemplate(new Template(1, "Node1_T", "node1", "node1", "node1"));
        node2 = addChildHierarchy(node1, "Node2", 2);
        node3 = addChildHierarchy(node1, "Node3", 3);
        node4 = addChildHierarchy(node2, "Node4", 4);
        node5 = addChildHierarchy(node2, "Node5", 5);
        node6 = addChildHierarchy(node4, "Node6", 6);
        node7 = addChildHierarchy(node6, "Node7", 7);
        node8 = addChildHierarchy(node6, "Node8", 8);

    }


    /**
     * Add a child template hierarchy to the given parent.
     *
     * @param parent the parent template hierarchy
     * @param name the name of the template hierarchy
     * @param id the id of the template hierarchy
     * @return the child template hierarchy
     */
    private TemplateHierarchy addChildHierarchy(TemplateHierarchy parent, String name, long id) {
        TemplateHierarchy child = new TemplateHierarchy(id, name, parent.getId());
        parent.addNestedHierarchy(child);

        child.addTemplate(new Template(id, name + "_T", name, name, name));
        return child;
    }

    /**
     * Test TemplateSelector().
     */
    public void testTemplateSelector() {
        assertTrue("testTemplateSelector fails", new TemplateSelector() instanceof TemplateSelectionAlgorithm);
    }

    /**
     * Tests selectTemplates().
     */
    public void testSelectTemplates1() {

        node1 = new TemplateHierarchy(0, "foo", 0);
        node1.addTemplate(new Template(0, "foo_T", "foo", "foo", "foo"));

        checkTemplates(selector.selectTemplates(componentVersion, node1), node1);
    }

    /**
     * Tests selectTemplates().
     */
    public void testSelectTemplates2() {

        componentVersion.setTechnologyType("Node3");

        checkTemplates(selector.selectTemplates(componentVersion, node1), node3);
    }

    /**
     * Tests selectTemplates().
     */
    public void testSelectTemplates3() {

        componentVersion.setTechnologyType("Node2");
        componentVersion.setAttribute("Node", "Node5");

        checkTemplates(selector.selectTemplates(componentVersion, node1), node5);
    }

    /**
     * Tests selectTemplates().
     */
    public void testSelectTemplates4() {

        componentVersion.setTechnologyType("Node2");
        componentVersion.setAttribute("Node", "Node4");
        componentVersion.setAttribute("Node6", "Node8");

        checkTemplates(selector.selectTemplates(componentVersion, node1), node8);
    }

    /**
     * Tests selectTemplates().
     */
    public void testSelectTemplates5() {

        componentVersion.setTechnologyType("Node2");
        componentVersion.setAttribute("Node", "Node5");
        componentVersion.setAttribute("Node6", "Node8");

        checkTemplates(selector.selectTemplates(componentVersion, node1), node5);

    }

    /**
     * Tests selectTemplates().
     */
    public void testSelectTemplates6() {

        componentVersion.setTechnologyType("Node2");
        componentVersion.setAttribute("Node", "Node4");
        componentVersion.setAttribute("Node6", "Node8");
        componentVersion.setAttribute("Att", "Node3");

        checkTemplates(selector.selectTemplates(componentVersion, node1), node3);

    }

    /**
     * Checks the returned the templates.
     *
     * @param templates the template array to check.
     * @param hierarchy the parent template hierarchy
     */
    private void checkTemplates(Template[] templates, TemplateHierarchy parent) {

        assertEquals("checkTemplates fails", 1, templates.length);
        assertEquals("checkTemplates fails", parent.getName() + "_T", templates[0].getName());
    }

}
