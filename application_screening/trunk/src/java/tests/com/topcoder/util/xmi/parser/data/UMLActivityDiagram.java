package com.topcoder.util.xmi.parser.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a UML activity diagram. Note that this class keeps no additional information.
 * All necessary information is extracted from the map of UMLElements maintained by the base class.
 * The class extends the mutable and not thread-safe base (UMLDiagram).
 */
public class UMLActivityDiagram extends UMLDiagram {

    /**
     * Constructor. Creates an activity diagram.
     */
    public UMLActivityDiagram() {
        super();
        setDiagramType(UMLDiagramTypes.UML_ACTIVITY_DIAGRAM);
    }

    /**
     * This method will return a list of all action states as described below:
     * 1. create the result list
     * 2. call getElements() to retrieve all diagram elements
     * 3. iterate over retrieved elements and add all UML_ACTION_STATE elements to the result list
     * 4. return the result
     *
     * @return the list of all action states
     */
    public java.util.List getStates() {
        List result = new ArrayList();

        Iterator elements = getElements().iterator();
        while(elements.hasNext()) {
            UMLElement element = (UMLElement) elements.next();
            if (element.getElementType().equals(UMLElementTypes.UML_ACTION_STATE)) {
                result.add(element);
            }
        }

        return result;
    }

    /**
     * This method will return a list of all initial states as described below:
     * 1. create the result list
     * 2. call getElements() to retrieve all diagram elements
     * 3. iterate over retrieved elements and add all UML_PSEUDO_STATE elements to the result list
     * 4. return the result
     *
     * @return the list of all initial states
     */
    public List getInitialStates() {
        List result = new ArrayList();

        Iterator elements = getElements().iterator();
        while(elements.hasNext()) {
            UMLElement element = (UMLElement) elements.next();
            if (element.getElementType().equals(UMLElementTypes.UML_PSEUDO_STATE)) {
                result.add(element);
            }
        }

        return result;
    }

    /**
     * This method will return a list of all final states as described below:
     * 1. create the result list
     * 2. call getElements() to retrieve all diagram elements
     * 3. iterate over retrieved elements and add all UML_FINAL_STATE elements to the result list
     * 4. return the result
     *
     * @return the list of all final states
     */
    public java.util.List getFinalStates() {
        List result = new ArrayList();

        Iterator elements = getElements().iterator();
        while(elements.hasNext()) {
            UMLElement element = (UMLElement) elements.next();
            if (element.getElementType().equals(UMLElementTypes.UML_FINAL_STATE)) {
                result.add(element);
            }
        }

        return result;
    }

    /**
     * This method will return a list of all transitions starting at the given node as described below:
     * 1. create the result list
     * 2. call getElements() to retrieve all diagram elements
     * 3. iterate over retrieved elements and add all UML_TRANSITION elements such that
     * UMLTransition.getFromId().equals(fromId)  to the result list
     * 4. return the result
     *
     * @param fromId the XMI id of the transition source
     * @return the list of transitions starting from the source
     */
    public java.util.List getTransitionsFrom(String fromId) {
        List result = new ArrayList();

        Iterator elements = getElements().iterator();
        while(elements.hasNext()) {
            UMLElement element = (UMLElement) elements.next();
            if (element.getElementType().equals(UMLElementTypes.UML_TRANSITION)
                    && ((UMLTransition) element).getFromId().equals(fromId)) {
                result.add(element);
            }
        }

        return result;
    }

    /**
     * This method will return a list of all transitions ending at the given node as described below:
     * 1. create the result list
     * 2. call getElements() to retrieve all diagram elements
     * 3. iterate over retrieved elements and add all UML_TRANSITION elements such that
     * UMLTransition.getToId().equals(toId)  to the result list
     * 4. return the result
     *
     * @param toId the XMI id of the transition target
     * @return the list of transitions ending in the target
     */
    public java.util.List getTransitionsTo(String toId) {
        List result = new ArrayList();

        Iterator elements = getElements().iterator();
        while(elements.hasNext()) {
            UMLElement element = (UMLElement) elements.next();
            if (element.getElementType().equals(UMLElementTypes.UML_TRANSITION)
                    || ((UMLTransition) element).getToId().equals(toId)) {
                result.add(element);
            }
        }

        return result;
    }

    /**
     * This method will return a list of all transitions as described below:
     * 1. create the result list
     * 2. call getElements() to retrieve all diagram elements
     * 3. iterate over retrieved elements and add all UML_TRANSITION elements to the result list
     * 4. return the result
     *
     * @return the list of all transitions
     */
    public java.util.List getTransitions() {
        List result = new ArrayList();

        Iterator elements = getElements().iterator();
        while(elements.hasNext()) {
            UMLElement element = (UMLElement) elements.next();
            if (element.getElementType().equals(UMLElementTypes.UML_TRANSITION)) {
                result.add(element);
            }
        }

        return result;
    }

    /**
     * This method will return a list of all states as described below:
     * 1. create the result list
     * 2. call getElements() to retrieve all diagram elements
     * 3. iterate over retrieved elements and add all UML_ACTION_STATE, UML_PSEUDO_STATE and
     * UML_FINAL_STATE elements to the result list
     * 4. return the result
     *
     *
     * @return the list of all states
     */
    public java.util.List getAllStates() {
        List result = new ArrayList();

        Iterator elements = getElements().iterator();
        while(elements.hasNext()) {
            UMLElement element = (UMLElement) elements.next();
            if (element.getElementType().equals(UMLElementTypes.UML_PSEUDO_STATE)
                    || element.getElementType().equals(UMLElementTypes.UML_ACTION_STATE)
                    || element.getElementType().equals(UMLElementTypes.UML_FINAL_STATE)) {
                result.add(element);
            }
        }

        return result;
    }
}