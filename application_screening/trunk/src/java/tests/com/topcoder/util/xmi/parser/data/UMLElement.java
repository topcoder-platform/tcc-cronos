/*
 * @(#)UMLElement.java
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.util.xmi.parser.data;

/**
 * <p>
 * Base class of all elements. This is a data object that represents a uml
 * element like UseCase, Association, Class contained in a diagram. The
 * <code>UMLDiagram</code> object holds a list of <code>UMLElements</code>
 * belonging to the diagram.
 * </p>
 *
 * @author TCSDESIGNER
 * @author smell
 * @version 1.0
 */
public class UMLElement {
    /** The name of the element. */
    private String elementName = null;

    /** The element id. */
    private String elementId = null;

    /**
     * The element type of this object. It will be one of the constant values
     * specified in <code>UMLElementTypes</code> class.
     */
    private String elementType = null;

    /**
     * <p>
     * Creates a UMLElement object.
     * </p>
     */
    public UMLElement() {
    }

    /**
     * <p>
     * Returns the name of the element.
     * </p>
     *
     * @return element name
     */
    public String getElementName() {
        return elementName;
    }

    /**
     * <p>
     * Sets the element name.
     * </p>
     *
     * @param elementName - the new name
     */
    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    /**
     * <p>
     * Returns the id of the element.
     * </p>
     *
     * @return the element id
     */
    public String getElementId() {
        return elementId;
    }

    /**
     * <p>
     * Sets the element id.
     * </p>
     *
     * @param elementId - the new id
     */
    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    /**
     * <p>
     * Returns the type of the element. The returned type is specified in
     * <code>UMLElementType</code> class.
     * </p>
     *
     * @return the element type
     */
    public String getElementType() {
        return elementType;
    }

    /**
     * <p>
     * Sets the element type. The types are specified in
     * <code>UMLElementType</code> class. The method does not complain in case
     * the specification is not met.
     * </p>
     *
     * @param elementType - the element type
     */
    public void setElementType(String elementType) {
        this.elementType = elementType;
    }
}
