/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.buildutility;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.Assert;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.topcoder.buildutility.component.ComponentVersion;
import com.topcoder.buildutility.component.ExternalComponentVersion;
import com.topcoder.buildutility.component.TechnologyType;
import com.topcoder.buildutility.template.PersistenceException;
import com.topcoder.buildutility.template.Template;
import com.topcoder.buildutility.template.TemplateHierarchy;
import com.topcoder.buildutility.template.TemplateHierarchyPersistence;
import com.topcoder.buildutility.template.TemplateLoader;

/**
 * This class contains various helper methods and some fields that are used by some test cases.
 *
 * @author marijnk
 * @version 1.0
 * @copyright &copy; 2005, TopCoder, Inc. All rights reserved.
 */
final class UnitTestHelper {
    /** A Component to create the build script for. */
    static final ComponentVersion COMPONENT = createComponent();

    /** A Template to use to generate the build script. */
    static final Template ANT_TEMPLATE;

    /** An erronous template. */
    static final Template ERROR_TEMPLATE;

    /** A Template that produces a very simple xml file. */
    static final Template SIMPLE_TEMPLATE;

    /** The loaded hierarchy of templates that is used to generate the build script. */
    static final TemplateHierarchy HIERARCHY;

    static {
        // Create a template loader, using the stub hierarchy persistence
        TemplateLoader loader = new TemplateLoader(new TemplateHierarchyPersistenceStub(), ".");
        // Load the template hierarchy
        TemplateHierarchy hier = null;
        try {
            hier = loader.loadTemplateHierarchy("demo");
        } catch (PersistenceException e) {
            // Should never happen because of implementation of HierarchyPersistence
        }
        HIERARCHY = hier;
        // Retrieve the various templates that are used by the various tests
        ANT_TEMPLATE = getTemplate(HIERARCHY, "ant");
        ERROR_TEMPLATE = getTemplate(HIERARCHY, "error");
        SIMPLE_TEMPLATE = getTemplate(HIERARCHY, "simple");
    }

    /**
     * Private constructor, to prevent the class from being instantiated.
     */
    private UnitTestHelper() {
        // Empty constructor
    }

    /**
     * This method is used in the static initialization code, to search for a template with a given name in a hierarchy
     * of templates.
     *
     * @param hier the hierarchy to search in
     * @param name the name of the template to find
     * @return a Template with the given name, or null if no template is found
     */
    private static Template getTemplate(TemplateHierarchy hier, String name) {
        // Iterate over all templates
        Template[] templates = hier.getTemplates();
        for (int i = 0; i < templates.length; i++) {
            // If the name is correct, return the template
            if (name.equals(templates[i].getName())) {
                return templates[i];
            }
        }

        // None found yet, try all the child-hierarchies
        TemplateHierarchy[] children = hier.getChildren();
        for (int i = 0; i < children.length; i++) {
            Template t = getTemplate(children[i], name);
            // If a template is found, return it
            if (t != null) {
                return t;
            }
        }

        // No templates found, return null
        return null;
    }

    /**
     * Create a ComponentVersion instance that is used by a lot of tests. This method creates the component and adds
     * various attributes, technology types and dependencies.
     *
     * @return a ComponentVersion object
     */
    private static ComponentVersion createComponent() {
        // Create a component
        ComponentVersion comp = new ComponentVersion(1, "Build Script Generator XSLT", null, "1.0");
        comp.addAttribute("package", "com.topcoder.buildutility");
        comp.addAttribute("customattr", "some random value");
        // set technology types
        comp.addTechnologyType(new TechnologyType(1, "java", "java component", false));
        comp.addTechnologyType(new TechnologyType(2, "ant", null, false));
        comp.addTechnologyType(new TechnologyType(3, "distribution", null, false));
        // set dependencies, first component dependencies
        ComponentVersion dep = new ComponentVersion(2, "Template Selector", null, "1.0");
        dep.addAttribute("package", "com.topcoder.buildutility");
        comp.addComponentDependency(dep);
        // add an external dependency
        ExternalComponentVersion ext = new ExternalComponentVersion(1, "xalan", null, "2.7.0", "xalan_j_2_7_0.jar");
        comp.addExternalComponentDependency(ext);
        // return the component
        return comp;
    }

    /**
     * This method validates that two files represent the same XML structure. Both files are parsed, and all the nodes
     * are check for differences. By using this method instead of direct string comparisions for example the order of
     * attributes in the xml files does not matter.
     *
     * @param expectedFileName the name of the file containing the correct xml tree
     * @param generatedFileName the name of the file containing the xml tree to validate
     * @throws Exception to JUnit
     */
    public static void validateOutput(String expectedFileName, String generatedFileName) throws Exception {
        // Create a document builder
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        // Parse both xml files
        Document generatedDoc = docBuilder.parse(new File(generatedFileName));
        Document expectedDoc = docBuilder.parse(new File(expectedFileName));
        // Validate both xml documents are the same
        assertNodesAreEqual(generatedDoc, expectedDoc, "");
    }

    /**
     * This method validates that the xml tree, rooted by the first tag in the xml file is the same as that represented
     * by the node.
     *
     * @param expectedFileName the name of the file containing the correct xml tree
     * @param generatedNode a DOM node, that is the root node of the xml tree to check
     * @throws Exception to JUnit
     */
    public static void validateOutputNode(String expectedFileName, Node generatedNode) throws Exception {
        // Create a document builder
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        // Parse the expected xml tree
        Document expectedDoc = docBuilder.parse(new File(expectedFileName));
        // Validate the node against the first child of the document read from the file
        assertNodesAreEqual(generatedNode, expectedDoc.getFirstChild(), "");
    }

    /**
     * Asserts the two given nodes are equal. The name, type and value of the nodes should match. The attributes of
     * both nodes should also be the same (but the order does not matter). This method calls itself recursively on the
     * child nodes of both nodes (If two nodes don't have the same number of child nodes it will also fail).
     *
     * @param value the Node to check for validaty
     * @param wanted the required value of the Node
     * @param path used to give detailed information where two nodes are different. Can simple be an empty string.
     */
    private static void assertNodesAreEqual(Node value, Node wanted, String path) {
        // Validate the name, type and value of the node
        Assert.assertEquals("Name of nodes doesn't match at " + path, wanted.getNodeName(), value.getNodeName());
        Assert.assertEquals("Type of nodes doesn't match at " + path, wanted.getNodeType(), value.getNodeType());
        Assert.assertEquals("Value of nodes doesn't match at " + path,
                        trim(wanted.getNodeValue()), trim(value.getNodeValue()));

        // Validate the attributes
        Assert.assertEquals("Attributes don't match at " + path, getAttributes(wanted), getAttributes(value));

        // Get the lists of child nodes
        List valChildren = getChildNodes(value);
        List wantChildren = getChildNodes(wanted);
        // First check if the nodes that exist in both trees are equal
        for (int i = 0; i < Math.min(wantChildren.size(), valChildren.size()); i++) {
            // Calculate the new path
            String newPath = path + " -> " + i + "(" + ((Node) wantChildren.get(i)).getNodeName() + ")";
            // Validate the child nodes
            assertNodesAreEqual((Node) valChildren.get(i), (Node) wantChildren.get(i), newPath);
        }
        // Secondly check if the number of child nodes is correct
        Assert.assertEquals("Number of child nodes does not match at " + path, wantChildren.size(), valChildren.size());
    }

    /**
     * Returns a java.util.List containing all the child nodes of the given node. Child nodes that are TEXT_NODEs and
     * have an empty string (after trimming) value, are not included, because they have no meaning in xml.
     *
     * @param n the node from wich to return the child nodes
     * @return a List containing all the children of n
     */
    private static List getChildNodes(Node n) {
        // Create an empty list
        List children = new ArrayList();
        // Retrieve all child nodes
        NodeList nodes = n.getChildNodes();
        // Iterate over all nodes
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            // Check if the node should be added
            if (!(node.getNodeType() == Node.TEXT_NODE && node.getNodeValue().trim().length() == 0)) {
                // Add the node
                children.add(node);
            }
        }
        // Return the filled list
        return children;
    }

    /**
     * Returns a trimmed version of the String s, or null if s is null. This is used to avoid having to check for null
     * values when for example comparing Node values.
     *
     * @param s the string to trim
     *
     * @return s.trim(), or null if s is null
     */
    private static String trim(String s) {
        if (s == null) {
            return null;
        } else {
            return s.trim();
        }
    }

    /**
     * Returns a java.util.Map containing the attributes of node n. This method is used to check equality of the
     * attribute sets of two nodes.
     *
     * @param n the node from wich to return the attributes
     *
     * @return a Map with all the attributes
     */
    private static Map getAttributes(Node n) {
        // Create an empty map
        Map map = new HashMap();

        // Check if any attributes are set
        if (!n.hasAttributes()) {
            // No attributes, so return the empty map
            return map;
        }

        // Retrieve the attributes
        NamedNodeMap attrs = n.getAttributes();
        // Iterate over the attributes
        for (int i = 0; i < attrs.getLength(); i++) {
            Node attr = attrs.item(i);
            // Add the attribute to the map
            map.put(attr.getNodeName(), attr.getNodeValue());
        }
        // Return the filled map
        return map;
    }

    /**
     * Stub implementation of the TemplateHierarchyPersistence interface, to avoid having to use JDBC to load
     * templates.
     *
     * @author marijnk
     */
    private static class TemplateHierarchyPersistenceStub extends Object implements TemplateHierarchyPersistence {
        /**
         * <p>Gets the top-level template hierarchy corresponding to specified name.</p>
         *
         * @param name a <code>String</code> specifying the name of the requested top-level template hierarchy.
         * @return a <code>TemplateHierarchy</code> corresponding to specified name.
         */
        public TemplateHierarchy getTemplateHierarchy(String name) {
            // create ant/nant template object
            Template ant = new Template(1, "ant", null, "build.xml", "test_files/build_ant.xslt");
            Template nant = new Template(2, "nant", null, "default.build", "test_files/build_nant.xslt");
            // create a template using a non-existing file
            Template errorTemplate = new Template(3, "error", null, "outputname", "test_files/does_not_exists.xslt");
            // create a template tha result in a simple xml file
            Template simpleTemplate = new Template(4, "simple", null, "outputname", "test_files/test.xslt");

            // create TemplateHierarchy objects
            TemplateHierarchy hierarchy = new TemplateHierarchy(1, "java", -1);
            TemplateHierarchy antH = new TemplateHierarchy(2, "ant", 1);
            TemplateHierarchy nantH = new TemplateHierarchy(3, "nant", 1);
            // Add templates to the hierarchies
            antH.addTemplate(ant);
            nantH.addTemplate(nant);
            hierarchy.addTemplate(errorTemplate);
            hierarchy.addTemplate(simpleTemplate);
            // Add the sub-hierachies to the main hierarchy
            hierarchy.addNestedHierarchy(antH);
            hierarchy.addNestedHierarchy(nantH);

            // Return the hierarchy
            return hierarchy;
        }
    }
}
