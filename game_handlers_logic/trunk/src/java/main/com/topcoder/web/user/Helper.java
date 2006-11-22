/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This is a common utility class used in this component.
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
class Helper {
    /**
     * <p>
     * This private constructor prevents to create a new instance.
     * </p>
     */
    private Helper() {
    }

    /**
     * Checks whether the given Object is null.
     *
     * @param arg
     *            the argument to check
     * @param name
     *            the name of the argument to check
     *
     * @throws IllegalArgumentException
     *             if the given Object is null
     */
    static final void checkNull(Object arg, String name) {
        if (arg == null) {
            throw new IllegalArgumentException("The '" + name
                    + "' should not be null.");
        }
    }

    /**
     * Checks whether the given String is null/empty.
     *
     * @param arg
     *            the String to check
     * @param name
     *            the name of the argument
     * @throws IllegalArgumentException
     *             if the given string is empty or given string is null
     * @return checked value back
     */
    static final String checkString(String arg, String name) {
        checkNull(arg, name);

        if (arg.trim().length() == 0) {
            throw new IllegalArgumentException(name + " should not be empty.");
        }

        return arg;
    }

    /**
     * Checks whether the given array is null/empty or contains null element.
     *
     * @param arg
     *            the argument to check
     * @param name
     *            the name of the argument
     * @throws IllegalArgumentException
     *             if the given array is null/empty or contains null element.
     */
    static final void checkArrayNullEmptyContainsNullEmpty(String[] arg,
            String name) {
        checkNull(arg, name);

        if (arg.length == 0) {
            throw new IllegalArgumentException(name + " should not be empty.");
        }

        for (int i = 0; i < arg.length; i++) {
            checkString(arg[i], "element in '" + name + "' indexed " + i);
        }
    }

    /**
     * Gets the value of specified element.
     *
     * @param element
     *            the element to get value (guaranteed to be not null)
     *
     * @return the text value of specified element
     * @throws IllegalArgumentException
     *             if element contains no content or the node content is empty
     */
    static final String getValue(Element element) {
        // The first child text_node be treat as value of element
        NodeList children = element.getChildNodes();

        for (int i = 0; i < children.getLength(); ++i) {
            Node child = children.item(i);

            if (child.getNodeType() == Node.TEXT_NODE) {
                String value = child.getNodeValue();
                if (value.trim().length() == 0) {
                    throw new IllegalArgumentException(
                            "The element contains empty value.");
                }
                return value;
            }
        }

        throw new IllegalArgumentException("The element contains no content.");
    }

    /**
     * Get the xpath name for element node or attribute node.
     *
     * @param node
     *            the node to get full xpath name
     *
     * @return the full xpath name, for example:
     *         /handler/allowed_profile_types/value
     */
    private static final String getXPathName(Node node) {
        // Ignore non-element or non-attribute node
        if ((node == null)
                || (!(node.getNodeType() == Node.ELEMENT_NODE) && !(node
                        .getNodeType() == Node.ATTRIBUTE_NODE))) {
            return "";
        }

        // Retireve the name of this node
        String name = null;
        Node parent = null;

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            // is Element node
            name = ((Element) node).getTagName();
            parent = node.getParentNode();
        } else {
            // is Attr node
            Attr attr = (Attr) node;
            name = attr.getName();

            // Attribute's parent is owner element
            parent = attr.getOwnerElement();
        }

        // use "/" to separate every node name
        name = "/" + name;

        if (parent == null) {
            return name;
        }

        return getXPathName(parent) + name;
    }

    /**
     * Get the element with given xpath.
     *
     * @param xpath
     *            the xpath for required element
     * @param element
     *            the element to get from
     *
     * @return the element corresponding to given xpath, null if can not find
     */
    private static final Element getElement(Element element, String xpath) {
        // Compare with given element's xpath
        String fullName = getXPathName(element);

        if (fullName.equals(xpath)) {
            // find it
            return element;
        }

        NodeList nodes = element.getChildNodes();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element find = getElement((Element) node, xpath);

                // Find it, simply return
                if (find != null) {
                    return find;
                }
            }
        }

        // can not find
        return null;
    }

    /**
     * This method gets element value according to xpath.
     *
     * <pre>
     *   &lt;handler type=&quot;x&quot;&gt;
     *       &lt;allowed_profile_types&gt;
     *           &lt;value&gt;teacher&lt;/value&gt;
     *           &lt;value&gt;student&lt;/value&gt;
     *       &lt;/allowed_profile_types&gt;
     *
     *       &lt;failure_result&gt;auth_failed_result&lt;/failure_result&gt;
     *   &lt;/handler&gt;
     * </pre>
     *
     * The xpath is: the xpath is:"/handler/failure_result". This method will
     * return "auth_failed_result".
     *
     * @param element
     *            the root element
     * @param xpath the xpath
     * @return value of the given xpath
     * @throws IllegalArgumentException
     *             if the value retrieve is empty, or the element is missed or
     *             contains no content
     */
    static final String getValue(Element element, String xpath) {
        Element targetElement = getElement(element, xpath);
        // IllegalArgumentException will be thrown if can not find
        if (targetElement == null) {
            throw new IllegalArgumentException("The xml is invalid. " + xpath
                    + " is missed.");
        }
        return getValue(targetElement);
    }

    /**
     * This method gets values of given xpath. For example, the handlerElement's
     * structure is as following:
     *
     * <pre>
     *   &lt;handler type=&quot;x&quot;&gt;
     *       &lt;allowed_profile_types&gt;
     *           &lt;value&gt;teacher&lt;/value&gt;
     *           &lt;value&gt;student&lt;/value&gt;
     *       &lt;/allowed_profile_types&gt;
     *
     *       &lt;failure_result&gt;auth_failed_result&lt;/failure_result&gt;
     *   &lt;/handler&gt;
     * </pre>
     *
     * The xpath is: the xpath is:"/handler/allowed_profile_types". This method
     * will return {"teacher", "student"}
     *
     * @param element
     *            the root element
     * @param xpath
     *            the xpath used to retrieve values
     * @return values of the given xpath
     * @throws IllegalArgumentException
     *             if value find, or value is empty, or value contains no
     *             content
     */
    static final String[] getValues(Element element, String xpath) {
        Element targetElement = getElement(element, xpath);
        if (targetElement == null) {
            throw new IllegalArgumentException("Can not find element by xpath:"
                    + xpath + ".");
        }
        // list used to temporarily store values
        List list = new ArrayList();
        NodeList nodeList = targetElement.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE
                    && node.getNodeName().equals("value")) {
                list.add(getValue((Element) node));
            }
        }
        // if no value found, throw IllegalArgumentException
        if (list.size() == 0) {
            throw new IllegalArgumentException("There is no value node under "
                    + xpath + ".");
        }
        return (String[]) list.toArray(new String[0]);
    }

    /**
     * This method returns values according to the given xpath and type. For
     * example, the handlerElement's structure is as following:
     *
     * <pre>
     *   &lt;handler type=&quot;x&quot;&gt;
     *       &lt;allowed_profile_types&gt;
     *           &lt;value type='b'&gt;teacher&lt;/value&gt;
     *           &lt;value type='a'&gt;student&lt;/value&gt;
     *       &lt;/allowed_profile_types&gt;
     *
     *       &lt;failure_result&gt;auth_failed_result&lt;/failure_result&gt;
     *   &lt;/handler&gt;
     * </pre>
     *
     * The type is: "a". The xpath is:"/handler/allowed_profile_types". This
     * method will return {"student"}
     *
     * @param handlerElement
     *            the root element
     * @param xpath
     *            the xpath used to retrive values
     * @param type
     *            the value type
     * @return values of the given xpath
     * @throws IllegalArgumentException
     *             if value find, or value is empty, or value contains no
     *             content
     */
    static final String[] getValues(Element handlerElement, String xpath,
            String type) {
        Element targetElement = getElement(handlerElement, xpath);
        if (targetElement == null) {
            throw new IllegalArgumentException("Can not find element by xpath:"
                    + xpath + ".");
        }
        // list used to temporarily store values
        List list = new ArrayList();
        NodeList nodeList = targetElement.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE
                    && node.getNodeName().equals("value")
                    && checkType((Element) node, type)) {
                list.add(getValue((Element) node));
            }
        }

        // if no value found, throw IllegalArgumentException
        if (list.size() == 0) {
            throw new IllegalArgumentException("No value find via xpath:"
                    + xpath + " and type:" + type + ".");
        }
        return (String[]) list.toArray(new String[0]);
    }

    /**
     * This method check if the element's type attribute equals to the given
     * attribute.
     *
     *
     * @param element
     *            the element to check
     * @param type
     *            the required type
     * @return true if equal, or return false.
     */
    private static final boolean checkType(Element element, String type) {
        String currentType = element.getAttribute("type");
        return type.equals(currentType);
    }

    /**
     * This method return a shallow copy String array.
     *
     * @param src
     *            the source
     * @return a shallow copy of source
     */
    static final String[] copy(String[] src) {
        String[] dest = new String[src.length];
        System.arraycopy(src, 0, dest, 0, src.length);
        return dest;
    }

    /**
     * Get the value of 'key' from 'attrs'. The value type should be as same as
     * 'type'.
     *
     * @param attrs
     *            the attribute map
     * @param key
     *            the key of attribute
     * @param type
     *            the expected class type, should not be null
     * @return the value of of attribute
     * @throws IllegalArgumentException
     *             if the value type is incorrect, or the value is invalid.
     */
    static final Object getAttribute(Map attrs, String key, Class type) {
        Object value = attrs.get(key);
        if (value == null) {
            throw new IllegalArgumentException("The '" + key
                    + "' does not exist.");
        }
        if (type.isAssignableFrom(value.getClass())) {
            if (type.isAssignableFrom(String.class)) {
                // check String
                checkString((String) value, key);
                return value;
            } else if (type.isAssignableFrom(String[].class)) {
                // check String array
                checkArrayNullEmptyContainsNullEmpty((String[]) value, key);
                return copy((String[]) value);
            } else {
                // check others
                checkNull(value, key);
                return value;
            }
        } else {
            throw new IllegalArgumentException("The value type of '" + key
                    + "' should be " + type.getName() + ".");
        }
    }
}
