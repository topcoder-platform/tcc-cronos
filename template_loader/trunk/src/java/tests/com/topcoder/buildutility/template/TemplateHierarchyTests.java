/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 *
 * TCS Template Loader 1.0 (Unit Test)
 */
package com.topcoder.buildutility.template;

import junit.framework.TestCase;

/**
 * <p>Unit test cases for TemplateHierarchy.</p>
 *
 * @author oldbig
 *
 * @version 1.0
 */
public class TemplateHierarchyTests extends TestCase {

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
     * <p>Tests the constructor of TemplateHierarchy.</p>
     * <p>name is null, NullPointerException should be thrown.</p>
     */
    public void testTemplateHierarchy1() {
        try {
            // name is null, NullPointerException should be thrown
            new TemplateHierarchy(0, null, 0);
            fail("NullPointerException should be thrown");
        } catch (NullPointerException e) {
            // success
        }
    }

    /**
     * <p>Tests the constructor of TemplateHierarchy.</p>
     * <p>name is empty, IllegalArgumentException should be thrown.</p>
     */
    public void testTemplateHierarchy2() {
        try {
            // name is empty, IllegalArgumentException should be thrown
            new TemplateHierarchy(0, " ", 0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>Tests whether the TemplateHierarchy class could be instantiated with the valid arguments.</p>
     */
    public void testTemplateHierarchy3() {

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
     * <p>Tests the setFileServerUri method.</p>
     * <p>Argument is null, NullPointerException should be thrown.</p>
     */
    public void testSetFileServerUri1() {
        try {
            // argument is null, NullPointerException should be thrown
            root.setFileServerUri(null);
            fail("NullPointerException should be thrown");
        } catch (NullPointerException e) {
            // success
        }
    }

    /**
     * <p>Tests the setFileServerUri method.</p>
     * <p>Argument is empty, IllegalArgumentException should be thrown.</p>
     */
    public void testSetFileServerUri2() {
        try {
            // argument is empty, IllegalArgumentException should be thrown
            root.setFileServerUri(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>Tests the setFileServerUri method.</p>
     * <p>Validates whether the file server uri was set properly on root.</p>
     *
     * @throws Exception to JUnit
     */
    public void testSetFileServerUri3() throws Exception {

        root.setFileServerUri("test_files/");

        // set the file server uri on the root, all templates should be set.
        UnitTestHelper.checkFileServerUriSet(template1);
        UnitTestHelper.checkFileServerUriSet(template2);
        UnitTestHelper.checkFileServerUriSet(template3);
        UnitTestHelper.checkFileServerUriSet(template4);

    }

    /**
     * <p>Tests the setFileServerUri method.</p>
     * <p>Validates whether the file server uri was set properly on child1.</p>
     *
     * @throws Exception to JUnit
     */
    public void testSetFileServerUri4() throws Exception {
        child1.setFileServerUri("test_files/");

        // set the file server uri on the child1, only template4 should be set.
        UnitTestHelper.checkFileServerUriSet(template4);
    }

    /**
     * <p>Tests the setFileServerUri method.</p>
     * <p>Validates whether the file server uri was set properly on child2.</p>
     *
     * @throws Exception to JUnit
     */
    public void testSetFileServerUri5() throws Exception {
        child2.setFileServerUri("test_files/");

        // set the file server uri on the child2, template2 and template3 should be set.
        UnitTestHelper.checkFileServerUriSet(template2);
        UnitTestHelper.checkFileServerUriSet(template3);
    }

    /**
     * <p>Tests the setFileServerUri method.</p>
     * <p>Validates whether the file server uri was set properly on child11.</p>
     *
     * @throws Exception to JUnit
     */
    public void testSetFileServerUri6() throws Exception {
        child11.setFileServerUri("test_files/");

        // set the file server uri on the child11, only template4 should be set.
        UnitTestHelper.checkFileServerUriSet(template4);
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
     * <p>Argument is null, NullPointerException should be thrown.</p>
     */
    public void testAddTemplate1() {
        try {
            // argument is null, NullPointerException should be thrown
            root.addTemplate(null);
            fail("NullPointerException should be thrown");
        } catch (NullPointerException e) {
            // success
        }
    }

    /**
     * <p>Tests the addTemplate method.</p>
     * <p>If the template is already added, DuplicateObjectException should be thrown.</p>
     */
    public void testAddTemplate2() {
        try {
            // the template is already added, DuplicateObjectException should be thrown
            root.addTemplate(template1);
            fail("DuplicateObjectException should be thrown");
        } catch (DuplicateObjectException e) {
            // success
        }
    }

    /**
     * <p>Tests the addTemplate method.</p>
     * <p>Validates whether the template was add properly.</p>
     */
    public void testAddTemplate3() {
        child1.addTemplate(template1);
        // the child1 node should have one template now
        assertEquals("the array length is incorrect", 1, child1.getTemplates().length);
        assertEquals("the child is incorrect", template1, child1.getTemplates()[0]);
    }

    /**
     * <p>Tests the addNestedHierarchy method.</p>
     * <p>Argument is null, NullPointerException should be thrown.</p>
     */
    public void testAddNestedHierarchy1() {
        try {
            // argument is null, NullPointerException should be thrown
            root.addNestedHierarchy(null);
            fail("NullPointerException should be thrown");
        } catch (NullPointerException e) {
            // success
        }
    }

    /**
     * <p>Tests the addNestedHierarchy method.</p>
     * <p>If the child is already added, DuplicateObjectException should be thrown.</p>
     */
    public void testAddNestedHierarchy2() {
        try {
            // the child is already added, DuplicateObjectException should be thrown
            root.addNestedHierarchy(child1);
            fail("DuplicateObjectException should be thrown");
        } catch (DuplicateObjectException e) {
            // success
        }
    }

    /**
     * <p>Tests the addNestedHierarchy method.</p>
     * <p>If the child is at top level, IllegalArgumentException should be thrown.</p>
     */
    public void testAddNestedHierarchy3() {
        try {
            // the child is already added, IllegalArgumentException should be thrown
            root.addNestedHierarchy(new TemplateHierarchy(-1, "foo", -1));
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>Tests the addNestedHierarchy method.</p>
     * <p>If the child's parent id does not equal to the id of current hierarchy,
     * IllegalArgumentException should be thrown.</p>
     */
    public void testAddNestedHierarchy4() {
        try {
            // the child's parent id does not equal to the id of current hierarchy,
            // IllegalArgumentException should be thrown
            root.addNestedHierarchy(new TemplateHierarchy(-1, "foo", -2));
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>Tests the addNestedHierarchy method.</p>
     * <p>Validates whether the child was add properly.</p>
     */
    public void testAddNestedHierarchy5() {
        TemplateHierarchy child21 = new TemplateHierarchy(-1, "foo", child2.getId());

        child2.addNestedHierarchy(child21);
        // the child2 node should have one child now
        assertEquals("the array length is incorrect", 1, child2.getChildren().length);
        assertEquals("the child is incorrect", child21, child2.getChildren()[0]);
    }

    /**
     * <p>Tests the addNestedHierarchy method.</p>
     *
     * <p>The hierarchy to be added causes a loop, llegalArgumentException should be thrown.</p>
     */
    public void testAddNestedHierarchy6() {
        TemplateHierarchy hierarchy1 = new TemplateHierarchy(1, "foo", 2);
        TemplateHierarchy hierarchy2 = new TemplateHierarchy(2, "bar", 1);
        hierarchy1.addNestedHierarchy(hierarchy2);

        try {
            // The hierarchy to be added causes a loop, llegalArgumentException should be thrown.
            hierarchy2.addNestedHierarchy(hierarchy1);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>Tests the addNestedHierarchy method.</p>
     *
     * <p>The hierarchy to be added causes a loop, llegalArgumentException should be thrown.</p>
     */
    public void testAddNestedHierarchy7() {
        TemplateHierarchy hierarchy1 = new TemplateHierarchy(1, "foo", 2);
        TemplateHierarchy hierarchy2 = new TemplateHierarchy(2, "bar", 3);
        TemplateHierarchy hierarchy3 = new TemplateHierarchy(3, "bar", 4);
        TemplateHierarchy hierarchy4 = new TemplateHierarchy(4, "bar", 1);

        hierarchy2.addNestedHierarchy(hierarchy1);
        hierarchy3.addNestedHierarchy(hierarchy2);
        hierarchy4.addNestedHierarchy(hierarchy3);


        try {
            // The hierarchy to be added causes a loop, llegalArgumentException should be thrown.
            hierarchy1.addNestedHierarchy(hierarchy4);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

}
