/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * Helper class to process dom object.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class XMLHelper {
    /**
     * Private ctor preventing this class from being instantiated.
     */
    private XMLHelper() {
        //does nothing
    }

    /**
     * Obtains the node's text value from given element.
     *
     * @param element the root node of which the sub node is belonging to
     * @param name the name of the node
     * @param required whether the node is required
     *
     * @return text value of the specified node
     *
     * @throws IllegalArgumentException if the specified node is a required one but does not exist or is empty string
     *         in the element
     */
    public static final String getNodeValue(Element element, String name, boolean required) {
        String[] values = getNodeValues(element, name);

        if (required) {
            if ((values == null) || (values.length == 0)) {
                throw new IllegalArgumentException("node is not found:" + name);
            }

            if ((values[0] == null) || (values[0].trim().length() == 0)) {
                throw new IllegalArgumentException("node is empty:" + name);
            }

            return values[0];
        } else {
            if ((values == null) || (values.length == 0)) {
                return null;
            }

            return values[0];
        }
    }

    /**
     * Obtains the nodes' text values from given element.
     *
     * @param element the root node of which the sub nodes are belonging to
     * @param name the name of the node
     *
     * @return text values of the specified node
     *
     * @throws IllegalArgumentException if the specified node does not exist
     */
    public static final String[] getNodeValues(Element element, String name) {
        NodeList list = getNodes(element, name);

        if ((list == null) || (list.getLength() == 0)) {
            throw new IllegalArgumentException("node is not found:" + name);
        }

        String[] values = new String[list.getLength()];

        for (int i = 0; i < list.getLength(); i++) {
            //values[i] = list.item(0).getFirstChild().getNodeValue();
            Node node = list.item(0).getFirstChild();

            if (node == null) {
                throw new IllegalArgumentException("node is empty:" + name);
            }

            values[i] = node.getNodeValue();
        }

        return values;
    }

    /**
     * Helper method to obtain a list of nodes from root element according to the given hierachical name.
     *
     * @param root root element from which the search starts
     * @param name hierachical name of the node, delimited by .
     *
     * @return NodeList that has the equal hierachical name as given
     */
    public static final NodeList getNodes(Element root, String name) {
        int dotIndex = name.indexOf('.');
        String currentName = name;
        String childName = null;

        //if the name represents a hierachical name, then splits it to a parent name and a subtitle name
        if (dotIndex > 0) {
            currentName = name.substring(0, dotIndex);
            childName = name.substring(dotIndex + 1, name.length());
        }

        NodeList nodeList = root.getElementsByTagName(currentName);

        if (childName == null) {
            return nodeList;
        }

        if (nodeList.getLength() == 0) {
            return null;
        }

        //search nodes from children
        return getNodes((Element) nodeList.item(0), childName);
    }
}
