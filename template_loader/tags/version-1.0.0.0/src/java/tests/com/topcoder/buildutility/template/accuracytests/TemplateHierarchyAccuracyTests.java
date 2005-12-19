/**
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.buildutility.template.accuracytests;

import com.topcoder.buildutility.template.Template;
import com.topcoder.buildutility.template.TemplateHierarchy;

import junit.framework.TestCase;


/**
 * Accuracy tests for TemplateHierarchy class.
 * @author zjq
 * @version 1.0
 */
public class TemplateHierarchyAccuracyTests extends TestCase {
    /**
     * A TemplateHierarchy instance to test.
     */
    private TemplateHierarchy root = null;

    /**
     * A TemplateHierarchy instance to test. One child of root.
     */
    private TemplateHierarchy child1 = null;

    /**
     * A TemplateHierarchy instance to test. One child of root.
     */
    private TemplateHierarchy child2 = null;

    /**
     * A TemplateHierarchy instance to test. One child of child1.
     */
    private TemplateHierarchy child11 = null;

    /**
     * A Template instance to test.
     */
    private Template template1 = null;

    /**
     * A Template instance to test.
     */
    private Template template2 = null;

    /**
     * A Template instance to test.
     */
    private Template template3 = null;

    /**
     * A Template instance to test.
     */
    private Template template4 = null;

    /**
     * Initialize the TemplateHierarchy instances for testing.
     */
    protected void setUp() {
        // create some templates
        template1 = new Template(0, "t1", "belong to root", "file_name", "test.txt");
        template2 = new Template(1, "t2", "belong to child2", "file_name", "test.txt");
        template3 = new Template(2, "t3", "belong to child2", "file_name", "test.txt");
        template4 = new Template(4, "t3", "belong to child11", "file_name", "test.txt");

        // creates the hierarchies
        //       root
        //      /    \
        //   child1  child2
        //    /
        // child11
        root = new TemplateHierarchy(0, "root", 0);
        child1 = new TemplateHierarchy(1, "the first child of root", 0);
        child2 = new TemplateHierarchy(2, "the second child of root", 0);
        child11 = new TemplateHierarchy(3, "the child of child1", 1);

        root.addNestedHierarchy(child1);
        root.addNestedHierarchy(child2);
        child1.addNestedHierarchy(child11);

        // add the templates to the hierarchies.
        root.addTemplate(template1);
        child2.addTemplate(template2);
        child2.addTemplate(template3);
        child11.addTemplate(template4);
    }

    /**
     * <p>Tests whether the TemplateHierarchy class could be instantiated with the valid arguments.</p>
     */
    public void testTemplateHierarchy() {
        TemplateHierarchy hierarchy = new TemplateHierarchy(1, "name", 2);

        assertNotNull("Unable to instantiate TemplateHierarchy.", hierarchy);
        assertEquals("id is incorrect", 1, hierarchy.getId());
        assertEquals("name is incorrect", "name", hierarchy.getName());
        assertEquals("parent id is incorrect", 2, hierarchy.getParentId());
    }

    /**
     * <p>Tests the getId method.</p>
     */
    public void testGetId1() {
        assertEquals("id is incorrect", 0, root.getId());
    }

    /**
     * <p>Tests the getId method.</p>
     */
    public void testGetId2() {
        assertEquals("id is incorrect", 3, child11.getId());
    }

    /**
     * <p>Tests the getName method.</p>
     */
    public void testGetName1() {
        assertEquals("name is incorrect", "root", root.getName());
    }

    /**
     * <p>Tests the getName method.</p>
     */
    public void testGetName2() {
        assertEquals("name is incorrect", "the child of child1", child11.getName());
    }

    /**
     * <p>Tests the getParentId method.</p>
     */
    public void testGetParentId1() {
        assertEquals("id is incorrect", 0, root.getParentId());
    }

    /**
     * <p>Tests the getParentId method.</p>
     */
    public void testGetParentId2() {
        assertEquals("id is incorrect", 1, child11.getParentId());
    }

    /**
     * <p>Tests the getTemplates method.</p>
     * <p>Validates whether the templates was retrieved properly. Root node has 1 template.</p>
     */
    public void testGetTemplates1() {
        Template[] children = root.getTemplates();
        assertEquals("the array length is incorrect", 1, children.length);
        assertEquals("the child template is incorrect", template1, children[0]);
    }

    /**
     * <p>Tests the getTemplates method.</p>
     * <p>Validates whether the templates was retrieved properly. Chlid1 node has no template.</p>
     */
    public void testGetTemplates2() {
        Template[] children = child1.getTemplates();
        assertEquals("the array length is incorrect", 0, children.length);
    }

    /**
     * <p>Tests the getChildren method.</p>
     * <p>Validates whether the children was retrieved properly. Child1 node has 1 children.</p>
     */
    public void testGetChildren1() {
        TemplateHierarchy[] children = child1.getChildren();
        assertEquals("the array length is incorrect", 1, children.length);
        assertEquals("the child is incorrect", child11, children[0]);
    }

    /**
     * <p>Tests the getChildren method.</p>
     * <p>Validates whether the children was retrieved properly. Child11 node has no children.</p>
     */
    public void testGetChildren2() {
        TemplateHierarchy[] children = child11.getChildren();
        assertEquals("the array length is incorrect", 0, children.length);
    }

    /**
     * <p>Tests the hasChildren method.</p>
     * <p>If the current hierarchy has child, true should be returned.</p>
     */
    public void testHasChildren1() {
        assertTrue("true should be returned", root.hasChildren());
    }

    /**
     * <p>Tests the hasChildren method.</p>
     * <p>If the current hierarchy has no child, false should be returned.</p>
     */
    public void testHasChildren2() {
        assertFalse("false should be returned", child2.hasChildren());
    }

    /**
     * <p>Tests the hasTemplates method.</p>
     * <p>If the current hierarchy has templates, true should be returned.</p>
     */
    public void testHasTemplates1() {
        assertTrue("true should be returned", root.hasTemplates());
    }

    /**
     * <p>Tests the hasTemplates method.</p>
     * <p>If the current hierarchy has no template, false should be returned.</p>
     */
    public void testHasTemplates2() {
        assertFalse("false should be returned", child1.hasTemplates());
    }

    /**
     * <p>Tests the addTemplate method.</p>
     * <p>Validates whether the template was add properly.</p>
     */
    public void testAddTemplate() {
        child1.addTemplate(template1);

        // the child1 node should have one template now
        assertEquals("the array length is incorrect", 1, child1.getTemplates().length);
        assertEquals("the child is incorrect", template1, child1.getTemplates()[0]);
    }

    /**
     * <p>Tests the addNestedHierarchy method.</p>
     * <p>Validates whether the child was add properly.</p>
     */
    public void testAddNestedHierarchy() {
        TemplateHierarchy child21 = new TemplateHierarchy(-1, "foo", child2.getId());

        child2.addNestedHierarchy(child21);

        // the child2 node should have one child now
        assertEquals("the array length is incorrect", 1, child2.getChildren().length);
        assertEquals("the child is incorrect", child21, child2.getChildren()[0]);
    }
}
