/*
 * @(#)UMLDiagram.java
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.util.xmi.parser.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * The base class of all UML Diagrams. This is a data object which represents a
 * UML Diagram. An instance of this or one of the subclasses is created for
 * each Diagram node that is handled by the handler. It also holds the
 * <code>UMLElement</code> objects that are contained in this diagram.
 * </p>
 *
 * @author TCSDESIGNER
 * @author smell
 * @version 1.0
 */
public class UMLDiagram {
    /** Map of elements in the diagram. */
    private Map elementsMap = null;

    /** The name of the diagram. */
    private String diagramName = null;

    /** The id of the diagram. */
    private String diagramId = null;

    /** Description of the diagram, if any. */
    private String description = null;

    /**
     * The type of the diagram, it will be one of the constant values specified
     * in <code>UMLDiagramTypes</code> class.
     */
    private String diagramType;

    /**
     * <p>
     * Creates a UMLDiagram.
     * </p>
     */
    public UMLDiagram() {
        elementsMap = new HashMap();
    }

    /**
     * <p>
     * Adds a UMLElement to the elements map with element id as key. The
     * element is added if the no element with the id has been added,
     * otherwise the old element is replaced with the new one.
     * </p>
     *
     * @param elemId - element id
     * @param element - the element
     */
    public void addElement(String elemId, UMLElement element) {
        if (elementsMap == null) {
            elementsMap = new HashMap();
        }

        elementsMap.put(elemId, element);
    }

    /**
     * <p>
     * Returns the collection of UMLElements contained in this diagram.
     * </p>
     *
     * @return the collection of elements
     */
    public Collection getElements() {
        if (elementsMap != null) {
            return elementsMap.values();
        }

        return new ArrayList();
    }

    /**
     * <p>
     * Returns diagram name.
     * </p>
     *
     * @return the diagram name
     */
    public String getDiagramName() {
        return diagramName;
    }

    /**
     * <p>
     * Sets the diagram name.
     * </p>
     *
     * @param diagramName - the new diagram name
     */
    public void setDiagramName(String diagramName) {
        this.diagramName = diagramName;
    }

    /**
     * <p>
     * Returns the diagram id.
     * </p>
     *
     * @return diagram id
     */
    public String getDiagramId() {
        return diagramId;
    }

    /**
     * <p>
     * Sets the diagram id.
     * </p>
     *
     * @param diagramId - the new diagram id
     */
    public void setDiagramId(String diagramId) {
        this.diagramId = diagramId;
    }

    /**
     * <p>
     * Returns the description
     * </p>
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * Sets the description of the diagram.
     * </p>
     *
     * @param description - the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>
     * Returns a string that identifies the type of the diagram. The string
     * description is specified in <code>UMLDiagramTypes</code> class.
     * </p>
     *
     * @return the type of the diagram
     */
    public String getDiagramType() {
        return diagramType;
    }

    /**
     * <p>
     * Sets the type of the diagram. The types are specified in
     * <code>UMLDiagramTypes</code>. The method does not complain in case the
     * specification is not met.
     * </p>
     *
     * @param diagramType - the new type
     */
    public void setDiagramType(String diagramType) {
        this.diagramType = diagramType;
    }

    /**
     * <p>
     * Return the elements map.
     * </p>
     *
     * @return the elements map
     */
    public Map getElementsMap() {
        return elementsMap;
    }

    /**
     * <p>
     * Set the elements map.
     * </p>
     *
     * @param elementsMap - the new map
     */
    public void setElementsMap(Map elementsMap) {
        this.elementsMap = elementsMap;
    }
}
