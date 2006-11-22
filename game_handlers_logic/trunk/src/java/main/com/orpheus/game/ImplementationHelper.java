/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import java.util.Map;

import javax.xml.transform.TransformerException;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * <p>
 * Defines utilities for classes of this component. It is used internally only.
 * </p>
 * <p>
 * Thread Safety: This class is thread safe by having no mutable information.
 * </p>
 *
 * @author mittu
 * @version 1.0
 */
final class ImplementationHelper {

    /**
     * No instance possible.
     */
    private ImplementationHelper() {
        // empty
    }

    /**
     * <p>
     * Checks the given object is null or not.
     * </p>
     *
     * @param obj
     *            object to check.
     * @param name
     *            parameter name.
     * @throws IllegalArgumentException
     *             If the object is null.
     */
    public static void checkObjectValid(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException("The parameter '" + name + "' is null");
        }
    }

    /**
     * <p>
     * Checks the given string is null or empty.
     * </p>
     *
     * @param str
     *            string to check.
     * @param name
     *            parameter name.
     * @throws IllegalArgumentException
     *             If the string is null or empty.
     */
    public static void checkStringValid(String str, String name) {
        checkObjectValid(str, name);
        if (str.trim().length() == 0) {
            throw new IllegalArgumentException("The parameter '" + name + "' is empty");
        }
    }

    /**
     * <p>
     * Gets the text value of the specified node (key) from the given element.
     * </p>
     *
     * @param element
     *            The element to be used.
     * @param key
     *            The tag name to be extracted.
     * @return The text value of the given tag.
     * @throws IllegalArgumentException
     *             If the given key is not present, duplicate or text value is null/empty.
     */
    public static String getElement(Element element, String key) {
        NodeList nodeList;
        try {
            nodeList = XPathAPI.selectNodeList(element, "/handler/" + key);
            if (nodeList.getLength() == 0) {
                throw new IllegalArgumentException("Key '" + key + "' is missing in the element");
            }
            if (nodeList.getLength() != 1) {
                throw new IllegalArgumentException("Key '" + key + "' is occurring more than once in the element");
            }
            Node node = nodeList.item(0).getFirstChild();
            checkObjectValid(node, key);
            String value = node.getNodeValue();
            checkStringValid(value, key);
            return value;
        } catch (TransformerException exception) {
            throw new IllegalArgumentException("Failed to get the key '" + key + "'. " + exception.getMessage());
        }
    }

    /**
     * <p>
     * Gets the string value from the map for the given key.
     * </p>
     *
     * @param map
     *            map to get the value
     * @param key
     *            the key to search
     * @return the string value
     * @throws IllegalArgumentException
     *             if the string value is null or empty.
     */
    static String getString(Map map, String key) {
        String value = (String) map.get(key);
        ImplementationHelper.checkStringValid(value, key);
        return value;
    }
}
