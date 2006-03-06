/*
 * @(#)DefaultXMIHandler.java
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.util.xmi.parser.handler;

import com.topcoder.util.xmi.parser.XMIHandler;
import com.topcoder.util.xmi.parser.XMIHandlerException;
import com.topcoder.util.xmi.parser.data.UMLActivityDiagram;
import com.topcoder.util.xmi.parser.data.UMLActor;
import com.topcoder.util.xmi.parser.data.UMLDiagram;
import com.topcoder.util.xmi.parser.data.UMLState;
import com.topcoder.util.xmi.parser.data.UMLStateType;
import com.topcoder.util.xmi.parser.data.UMLTransition;
import com.topcoder.util.xmi.parser.data.UMLUseCase;
import com.topcoder.util.xmi.parser.data.UMLUseCaseDiagram;
import com.topcoder.util.xmi.parser.data.UMLElementTypes;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * <p/>
 * The default handler provided. It handles the Diagram and Use Case nodes. The
 * list of diagrams is built for clients to access the data. Also implements
 * an API to access diagram information like
 * </p>
 * <p/>
 * <ul>
 * <li>
 * Diagrams of certain type
 * </li>
 * <li>
 * Diagram names of certain type
 * </li>
 * <li>
 * Concrete usages of a given Use Case diagram
 * </li>
 * <li>
 * Abstract usages of a given Use Case diagram
 * </li>
 * </ul>
 * <p/>
 * <p/>
 * The DefaultXMIHandler is configured to be the handler for all Diagrams and
 * 'UseCase' types in the default namespace's handler.properties. But it is
 * esily extensible by adding it as handler for other types in the
 * configuration and providing appropriate handler methods.
 * </p>
 * <p/>
 * <p/>
 * The handleNode checks the type of element/diagram passed to it which is the
 * second argument. It then invokes the method create&lt;nodeType&gt; using
 * reflection. In the default implementation, if the node name is
 * 'UseCaseDiagram', it calls the createUseCaseDiagram() method. If the node
 * name is 'UseCase', it calls the createUseCase(). Extensions or new
 * implementations can follow the same process for handling additional types.
 * </p>
 * <p/>
 * <p/>
 * In createUseCaseDiagram(), it creates a UMLUseCaseDiagram data object and
 * fills the information like diagram name, id and description. Then it gets
 * all the &lt;UML:UseCase&gt;&nbsp; child nodes. For each, UseCase node, it
 * adds an entry in the elementsMap of UMLUseCaseDiagram, where the key is the
 * id&nbsp; the node and the value is null. Add the UMLUseCaseDiagram to the
 * 'diagrams' list.
 * </p>
 * <p/>
 * <p/>
 * In createUseCase() method, get the id of the node passed( which the node
 * corresponding to the UseCase). Build the UMLUseCase object and fill the
 * details. Get the diagrams from 'diagrams' list which are of type
 * UMLUseCaseDiagram. From the elementsMap of this diagram object, match the
 * key to the id of the element node. Add this UMLUseCase object to the
 * elementsMap with that key. This way by end of processing the elementsMap
 * will contain all UseCase elements. Because the XMIParser first processes
 * all diagrams before the elements, there should be no scope of elements not
 * finding a suitable diagram.
 * </p>
 * <p/>
 * <p/>
 * The utility methods provided by the class allow the client application to
 * get the diagram information after it gets the handler from the parser. All
 * the information is obtained by operating on the 'diagrams' list. The
 * functionality of each method is explained in its documentation section.
 * </p>
 *
 * @author TCSDESIGNER
 * @author smell
 * @version 1.0
 */
public class DefaultXMIHandler implements XMIHandler {
//    /**
//     * The tag name of Property used in XMI files
//     */
//    private static final String PROPERTY_TAG = "UML:Property";
//
//    /**
//     * The tag name of UseCase used in XMI files
//     */
//    private static final String USE_CASE_TAG = "UML:UseCase";
//
//    /**
//     * The attribute name of name used in XMI files
//     */
//    private static final String NAME_ATTR = "name";
//
//    /**
//     * The attribute name of value used in XMI files
//     */
//    private static final String VALUE_ATTR = "value";
//
//    /**
//     * The attribute name of key used in XMI files
//     */
//    private static final String KEY_ATTR = "key";
//
//    /**
//     * The attribute name of isAbstract used in XMI files
//     */
//    private static final String IS_ABSTRACT_ATTR = "isAbstract";
//
//    /**
//     * The attribute name of ref id used in XMI files
//     */
//    private static final String IDREF_ATTR = "xmi.idref";
//
//    /**
//     * The attribute name of id used in XMI files
//     */
//    private static final String ID_ATTR = "xmi.id";
//
//    /**
//     * The string used XMI file that indicates documentation, this const is
//     * used to get description of diagram
//     */
//    private static final String DOCUMENTATION = "documentation";
//
//    /**
//     * The string used XMI file that indicates true
//     */
//    private static final String TRUE = "true";
//
//    /**
//     * Prefix of the methods "create&lt;diagram type&gt;" and
//     * "creaet&lt;element type&gt;", this prefix is used when get the methods
//     * reflectly.
//     */
//    private static final String PREFIX = "create";
//
//    /**
//     * Parameter types of the methods "create&lt;diagram type&gt;" and
//     * "creaet&lt;element type&gt;", this types is used when get the methods
//     * reflectly.
//     */
//    private static final Class[] PARAMETER_TYPES = new Class[]{
//            Node.class, String.class
//    };
//
//    /**
//     * Exception message for handleNode method.
//     */
//    private static final String EXCEPTION_MESSAGE = "Can not handle this type of node!";
//
//    /**
//     * Message returned by handleNode()
//     */
//    private static final String PROCESS_MESSAGE = " processed.";
//
//    /**
//     * Array for invoke paraters
//     */
//    private static final Object[] INVOKE_PARAMETER = new Object[2];
//
    /****************************************************************
     *  Predefined configurations
     ***************************************************************/

    /**
     * List of diagrams in the form of UMLDiagram objects in the model
     */
    private List diagrams = null;

/*
    static {
        //create use case diagram
        UMLUseCaseDiagram useCaseDiagram = new UMLUseCaseDiagram();
        useCaseDiagram.setDiagramName("UseCase diagram1");
        useCaseDiagram.setDiagramId("I808680m1098bca64b3mm7f51");
        //add actors
        UMLActor actor1 = new UMLActor();
        actor1.setElementName("Actor 1");
        actor1.setElementId("I808680m1098bca64b3mm7f4e");
        useCaseDiagram.addElement(actor1.getElementId(), actor1);

        UMLActor actor2 = new UMLActor();
        actor2.setElementName("Actor 2");
        actor2.setElementId("I808680m1098bca64b3mm7ef3");
        useCaseDiagram.addElement(actor2.getElementId(), actor2);

        //add use cases
        UMLUseCase useCase1 = new UMLUseCase();
        useCase1.setElementName("Use Case 1");
        useCase1.setElementId("I808680m1098bca64b3mm7f47");
        useCase1.setIsAbstract(false);
        useCaseDiagram.addElement(useCase1.getElementId(), useCase1);

        UMLUseCase useCase2 = new UMLUseCase();
        useCase2.setElementName("Use Case 2");
        useCase2.setElementId("I808680m1098bca64b3mm7f1d");
        useCase2.setIsAbstract(false);
        useCaseDiagram.addElement(useCase2.getElementId(), useCase2);

        UMLUseCase useCase3 = new UMLUseCase();
        useCase3.setElementName("Use Case 3");
        useCase3.setElementId("I808680m1098bca64b3mm7ed2");
        useCase3.setIsAbstract(false);
        useCaseDiagram.addElement(useCase3.getElementId(), useCase3);

        //create Activity Diagram
        UMLActivityDiagram activityDiagram = new UMLActivityDiagram();
        //create initials and final state
        UMLState init1 = new UMLState("I808680m1098bca64b3mm7ea2", UMLStateType.INITIAL_STATE, "First Initial State");
        UMLState init2 = new UMLState("I808680m1098bca64b3mm7e9d", UMLStateType.INITIAL_STATE, "Second Initial State");
        UMLState finalSt = new UMLState("I808680m1098bca64b3mm7e47", UMLStateType.FINAL_STATE, "Final State 1");
        activityDiagram.addElement(init1.getId(), init1);
        activityDiagram.addElement(init2.getId(), init2);
        activityDiagram.addElement(finalSt.getId(), finalSt);

        //add action states
        UMLState state1 = new UMLState("I808680m1098bca64b3mm7e73", UMLStateType.ACTION_STATE, "Action State 1");
        UMLState state2 = new UMLState("I808680m1098bca64b3mm7e85", UMLStateType.ACTION_STATE, "Action State 2");
        UMLState state3 = new UMLState("I808680m1098bca64b3mm7e61", UMLStateType.ACTION_STATE, "Action State 3");
        activityDiagram.addElement(state1.getId(), state1);
        activityDiagram.addElement(state2.getId(), state2);
        activityDiagram.addElement(state3.getId(), state3);

        //add transition
        UMLTransition init1_to_st1 = new UMLTransition("I808680m1098bca64b3mm7e6b", "I808680m1098bca64b3mm7ea2",
                "I808680m1098bca64b3mm7e73", "first_to_state1", "guard condition 1", "guard effect 1");
        UMLTransition init2_to_st2 = new UMLTransition("I808680m1098bca64b3mm7e7d", "I808680m1098bca64b3mm7e9d",
                "I808680m1098bca64b3mm7e85", "second_to_state2", "guard condition 2", "guard effect 2");
        UMLTransition st1_to_st3 = new UMLTransition("I808680m1098bca64b3mm7e50", "I808680m1098bca64b3mm7e73",
                "I808680m1098bca64b3mm7e61", "state1_to_state3", "guard condition 3", "guard effect 3");
        UMLTransition st2_to_st3 = new UMLTransition("I808680m1098bca64b3mm7e59", "I808680m1098bca64b3mm7e85",
                "I808680m1098bca64b3mm7e61", "state2_to_state3", "guard condition 4", "guard effect 4");
        UMLTransition state3_to_final = new UMLTransition("I808680m1098bca64b3mm7e42", "I808680m1098bca64b3mm7e61",
                "I808680m1098bca64b3mm7e47", "state3_to_final", "guard condition 5", "guard effect 5");
        activityDiagram.addElement(init1_to_st1.getId(), init1_to_st1);
        activityDiagram.addElement(init2_to_st2.getId(), init2_to_st2);
        activityDiagram.addElement(st1_to_st3.getId(), st1_to_st3);
        activityDiagram.addElement(st2_to_st3.getId(), st2_to_st3);
        activityDiagram.addElement(state3_to_final.getId(), state3_to_final);
    }
*/

    /**
     * <p/>
     * Default constructor of DefaultXMIHandler.
     * </p>
     */
    public DefaultXMIHandler() {
        diagrams = new ArrayList();
    }

    /**
     * <p/>
     * Handles the node of the given type, It delegates handling to specific
     * methods on receiving interested events. It first checks the type of
     * element/diagram passed to it which is the second argument, then invokes
     * the method create&lt;nodeType&gt; using reflection. If a type which is
     * configured but a handler method is not defined, the java.lang.Class API
     * throws an exception which is then wrapped in XMIHandlerException and
     * re-thrown.The message should be &ldquo;Method create&lt;nodeType&gt;
     * not found&rdquo; and the cause should be the original exception.
     * Similarly, java.lang.reflect API exceptions are also captured and
     * re-thrown as XMIHandlerException
     * </p>
     * <p/>
     * <p/>
     * Since the method for the a given nodeType is create&lt;nodeType&gt;,
     * extensions or new implementations can follow the same process for
     * handling additional types.
     * </p>
     * <p/>
     * * NOTE (modification necessary to handle state machine elements):
     * If the nodeType contains dots ('.') replace them by underscores before calling the parsing method
     * via reflection. This is needed to ensure that elements such as "StateMachine.top" can be
     * handled properly.
     *
     * @param node     - xmi node
     * @param nodeType - diagram/element type of the node
     * @return status string
     * @throws XMIHandlerException - if cannot successfully handle the node for
     *                             any reason, the Exception that failed the handling is wrapped
     *                             as cause of XMIHandlerException
     */
    public String handleNode(Node node, String nodeType) throws XMIHandlerException {
        if (nodeType.equals("xmistring")) {
            prepareIfXmiString();
        } else if (nodeType.equals("stream")) {
            prepareIfStream();
        } else if (nodeType.equals("file1")) {
            prepareIfFile();
        } else if (nodeType.equals("demo")) {
            prepareIfDemo();
        }
        return null;
    }

    /**
     * <p/>
     * Returns the number of diagrams of a given type. The type must be one of
     * the constants defined in <code>UMLDiagramTypes</code> class. If the
     * type is invalid or non-existent, the method returns 0 (zero).
     * </p>
     *
     * @param type - type of diagrams
     * @return count of diagrams, 0 if the type is invalid/non-existent
     */
    public int getCountOfDiagramType(String type) {
//        int count = 0;
//
//        Iterator it = diagrams.iterator();
//
//        while (it.hasNext()) {
//            UMLDiagram d = (UMLDiagram) it.next();
//
//            if (d.getDiagramType().equals(type)) {
//                count++;
//            }
//        }
//
        return 0;
    }

    /**
     * <p/>
     * Finds the abstract use cases in a given Use Case diagram from the parsed
     * diagrams.
     * </p>
     * <p/>
     * <p/>
     * The diagramName is the value of name attribute found in XMI for the
     * diagram. In case of invalid/non-existing name, the method returns empty
     * List.
     * </p>
     *
     * @param diagramName - name of the given diagram
     * @return the list of abstract use cases in the given diagram, empty list
     *         for invalid/non-existing name
     */
    public List getAbstractUseCases(String diagramName) {
//        return getUseCases(diagramName, true);
        return new ArrayList();
    }

    /**
     * <p/>
     * Finds the concrete use cases in a given Use Case diagram from the parsed
     * diagrams.
     * </p>
     * <p/>
     * <p/>
     * The diagramName is the value of name attribute found in XMI for the
     * diagram. In case of invalid/non-existing name, the method returns empty
     * List.
     * </p>
     *
     * @param diagramName -
     * @return the list of concrete use cases in the given diagram, empty list
     *         for invalid/non-existing name
     */
    public List getConcreteUseCases(String diagramName) {
//        return getUseCases(diagramName, false);
        return new ArrayList();
    }

    /**
     * <p/>
     * Returns a list of diagram names of a given type from the diagrams.
     * Procedure is similar to getDiagramsOfType, but diagram names which is
     * the attribute of the class are collected and returned.
     * </p>
     * <p/>
     * <p/>
     * For an invalid/non-existing type, empty List is returned.
     * </p>
     *
     * @param type The diagram type which is one of the constants specified by
     *             <code>UMLDiagramTypes</code>
     * @return List of names of the given type of diagrams
     */
    public List getDiagramNamesOfType(String type) {
//        List list = new ArrayList();
//
//        Iterator it = diagrams.iterator();
//
//        while (it.hasNext()) {
//            UMLDiagram d = (UMLDiagram) it.next();
//
//            if (d.getDiagramType().equals(type)) {
//                list.add(d.getDiagramName());
//            }
//        }
//
//        return list;
        return new ArrayList();
    }

    /**
     * <p/>
     * Gets a list of UMLDiagram objects of a given type from the diagrams. The
     * parameter must be of type defined in UMLDiagramTypes.
     * </p>
     * <p/>
     * <p/>
     * For an invalid/non-existing type, empty list is returned
     * </p>
     *
     * @param type - the diagram type which is one of the constants specified
     *             by <code>UMLDiagramTypes</code>
     * @return Collection of diagrams
     */
    public List getDiagramsOfType(String type) {
        List list = new ArrayList();

        Iterator it = diagrams.iterator();

        while (it.hasNext()) {
            UMLDiagram d = (UMLDiagram) it.next();

            if (d.getDiagramType().equals(type)) {
                list.add(d);
            }
        }

        return list;
    }

    /**
     * <p/>
     * Creates a UMLUseCaseDiagram data object and fills the information like
     * diagram name, id, type and description. Then it gets all the
     * &lt;UML:UseCase&gt;&nbsp; child nodes. For each, UseCase node, it adds
     * an entry in the elementsMap of UMLUseCaseDiagram, where the key is the
     * id&nbsp; the node and the value is null. Finally the UMLUseCaseDiagram
     * is added to the 'diagrams' list.
     * </p>
     * <p/>
     * <p/>
     * The method is declared protected so that sub-classes can override it.
     * </p>
     *
     * @param node     - xmi node
     * @param nodeType - type of the node
     */
    protected void createUseCaseDiagram(Node node, String nodeType) {
//        if (!(node instanceof Element)) {
//            return;
//        }
//
//        Element e = (Element) node;
//
//        UMLUseCaseDiagram diagram = new UMLUseCaseDiagram();
//
//        // set the diagram's attributes
//        diagram.setDiagramName(e.getAttribute(NAME_ATTR));
//        diagram.setDiagramId(e.getAttribute(ID_ATTR));
//        diagram.setDescription(getDescription(e));
//
//        // diagram type set by UMLUseCaseDiagram's constructor
//        Map map = diagram.getElementsMap();
//        NodeList nl = e.getElementsByTagName(USE_CASE_TAG);
//        int length = nl.getLength();
//
//        for (int i = 0; i < length; i++) {
//            Element useCase = (Element) nl.item(i);
//            map.put(useCase.getAttribute(IDREF_ATTR), null);
//        }
//
//        diagram.setElementsMap(map);
//
//        diagrams.add(diagram);
    }

    /**
     * <p/>
     * Gets the id of the UseCase node passed and builds the
     * <code>UMLUseCase</code> object and fill the details. Finally associates
     * the UseCase with the UseCaseDiagram containing it by set the value of
     * the elementsMap of the diagram to it.
     * </p>
     * <p/>
     * <p/>
     * The method is protected so that subclasses can override it.
     * </p>
     *
     * @param node     - xmi node
     * @param nodeType - type of the node
     */
    protected void createUseCase(Node node, String nodeType) {
//        if (!(node instanceof Element)) {
//            return;
//        }
//
//        Element e = (Element) node;
//
//        UMLUseCase useCase = new UMLUseCase();
//        String id = e.getAttribute(ID_ATTR);
//        useCase.setElementId(id);
//        useCase.setElementName(e.getAttribute(NAME_ATTR));
//        useCase.setIsAbstract(e.getAttribute(IS_ABSTRACT_ATTR).equals(TRUE));
//
//        // Element type already set by UMLUseCase's constructor
//        Iterator it = diagrams.iterator();
//
//        while (it.hasNext()) {
//            Map map = ((UMLDiagram) it.next()).getElementsMap();
//
//            if (map.containsKey(id)) {
//                map.put(id, useCase);
//            }
//        }
    }

    /**
     * <p/>
     * Creates a UMLDiagram data object and fills the information like diagram
     * name, id, type and description. And then the UMLDiagram is added to the
     * 'diagrams' list.
     * </p>
     * <p/>
     * <p/>
     * Note the DefaultXMIHandler just provides the simplest implementation for
     * the diagram type other than UseCaseDiagram. Enhanced handling of these
     * types may be done in a later version.
     * </p>
     * <p/>
     * <p/>
     * The method is declared protected so that sub-classes can override it.
     * </p>
     *
     * @param node     - xmi node
     * @param nodeType - type of the node
     */
    protected void createClassDiagram(Node node, String nodeType) {
//        defaultCreateDiagram(node, nodeType);
    }

    /**
     * <p/>
     * Creates a UMLDiagram data object and fills the information like diagram
     * name, id, type and description. And then the UMLDiagram is added to the
     * 'diagrams' list.
     * </p>
     * <p/>
     * <p/>
     * Note the DefaultXMIHandler just provides the simplest implementation for
     * the diagram type other than UseCaseDiagram. Enhanced handling of these
     * types may be done in a later version.
     * </p>
     * <p/>
     * <p/>
     * The method is declared protected so that sub-classes can override it.
     * </p>
     *
     * @param node     - xmi node
     * @param nodeType - type of the node
     */
//    protected void createActivityDiagram(Node node, String nodeType) {
//        defaultCreateDiagram(node, nodeType);
//    }

    /**
     * <p/>
     * Creates a UMLDiagram data object and fills the information like diagram
     * name, id, type and description. And then the UMLDiagram is added to the
     * 'diagrams' list.
     * </p>
     * <p/>
     * <p/>
     * Note the DefaultXMIHandler just provides the simplest implementation for
     * the diagram type other than UseCaseDiagram. Enhanced handling of these
     * types may be done in a later version.
     * </p>
     * <p/>
     * <p/>
     * The method is declared protected so that sub-classes can override it.
     * </p>
     *
     * @param node     - xmi node
     * @param nodeType - type of the node
     */
    protected void createStateDiagram(Node node, String nodeType) {
//        defaultCreateDiagram(node, nodeType);
    }

    /**
     * <p/>
     * Creates a UMLDiagram data object and fills the information like diagram
     * name, id, type and description. And then the UMLDiagram is added to the
     * 'diagrams' list.
     * </p>
     * <p/>
     * <p/>
     * Note the DefaultXMIHandler just provides the simplest implementation for
     * the diagram type other than UseCaseDiagram. Enhanced handling of these
     * types may be done in a later version.
     * </p>
     * <p/>
     * <p/>
     * The method is declared protected so that sub-classes can override it.
     * </p>
     *
     * @param node     - xmi node
     * @param nodeType - type of the node
     */
    protected void createDeploymentDiagram(Node node, String nodeType) {
//        defaultCreateDiagram(node, nodeType);
    }

    /**
     * <p/>
     * Creates a UMLDiagram data object and fills the information like diagram
     * name, id, type and description. And then the UMLDiagram is added to the
     * 'diagrams' list.
     * </p>
     * <p/>
     * <p/>
     * Note the DefaultXMIHandler just provides the simplest implementation for
     * the diagram type other than UseCaseDiagram. Enhanced handling of these
     * types may be done in a later version.
     * </p>
     * <p/>
     * <p/>
     * The method is declared protected so that sub-classes can override it.
     * </p>
     *
     * @param node     - xmi node
     * @param nodeType - type of the node
     */
    protected void createSequenceDiagram(Node node, String nodeType) {
//        defaultCreateDiagram(node, nodeType);
    }

    /**
     * <p/>
     * Creates a UMLDiagram data object and fills the information like diagram
     * name, id, type and description. And then the UMLDiagram is added to the
     * 'diagrams' list.
     * </p>
     * <p/>
     * <p/>
     * Note the DefaultXMIHandler just provides the simplest implementation for
     * the diagram type other than UseCaseDiagram. Enhanced handling of these
     * types may be done in a later version.
     * </p>
     * <p/>
     * <p/>
     * The method is declared protected so that sub-classes can override it.
     * </p>
     *
     * @param node     - xmi node
     * @param nodeType - type of the node
     */
    protected void createCollaborationDiagram(Node node, String nodeType) {
//        defaultCreateDiagram(node, nodeType);
    }

    /**
     * <p/>
     * Returns a list of UseCases of the given type of abstract/concrete with
     * the specified diagram name
     * </p>
     *
     * @param diagramName - the diagram name
     * @param isAbstract  - true if abstract UseCase is required, and false if
     *                    concrete UseCase is required
     * @return the list of UseCases
     */
/*
    private List getUseCases(String diagramName, boolean isAbstract) {
        List list = new ArrayList();

        Iterator it = diagrams.iterator();

        while (it.hasNext()) {
            UMLDiagram d = (UMLDiagram) it.next();

            if (!d.getDiagramName().equals(diagramName)
                    || (
                    d.getDiagramType() != UMLDiagramTypes.UML_USECASE_DIAGRAM
            )) {
                continue;
            }

            Iterator iter = ((UMLUseCaseDiagram) d).getUseCases().iterator();

            while (iter.hasNext()) {
                UMLUseCase u = (UMLUseCase) iter.next();

                if (u.isAbstract() == isAbstract) {
                    list.add(u);
                }
            }
        }

        return list;
    }
*/

    /**
     * <p/>
     * Creates UMLDiagram for uml diagram other than UseCaseDiagram.
     * </p>
     *
     * @param node     - the DOM node of the UML:Diagram element
     * @param nodeType - the type of the diagram
     */
/*
    private void defaultCreateDiagram(Node node, String nodeType) {
        if (!(node instanceof Element)) {
            return;
        }

        Element e = (Element) node;

        // fill in attributes
        UMLDiagram diagram = new UMLDiagram();
        diagram.setDiagramType(nodeType);
        diagram.setDiagramId(e.getAttribute(ID_ATTR));
        diagram.setDiagramName(e.getAttribute(NAME_ATTR));
        diagram.setDescription(getDescription(e));

        diagrams.add(diagram);
    }
*/

    /**
     * <p/>
     * Gets the description of the UML diagram with the given element node.
     * </p>
     *
     * @param e - the given node
     * @return the desciption of the diagram if any, null otherwise
     */
/*
    private String getDescription(Element e) {
        NodeList nl = e.getElementsByTagName(PROPERTY_TAG);

        for (int i = 0; i < nl.getLength(); i++) {
            Element element = (Element) nl.item(i);

            if (element.getAttribute(KEY_ATTR).equals(DOCUMENTATION)) {
                return element.getAttribute(VALUE_ATTR);
            }
        }

        return null;
    }

*/

    /**
     * This method should create a UMLActivityDiagram data object and fill the information like diagram name,
     * id, type and description.
     * The created diagram should be added to diagram list. It has no information on child elements yet.
     * The information about elements will be added later (during the activity graph parsing), however we
     * should establish binding between child elements and the diagram:
     * 1. retrieve all  child elements by the ActionState tag. For each such element add an entry to the
     *    elements map for the diagram. Use an element XMI id as the key; use null as the value (we don't have
     *    the actual state object yet but we already know its id).
     * 2. do the step 1 for all PseudoState child elements
     * 3. do the step 1 for all FinalState child elements
     * 4. do the step 1 for all Transition child elements
     * The method is declared protected so that sub-classes can override it.
     *
     * @param node     the xmi node to parse
     * @param nodeType the node type for this node
     * @throws XMIHandlerException if the node can't be parsed correctly
     */
    protected void createActivityDiagram(Node node, String nodeType) throws XMIHandlerException {
    }

    /**
     * In createActor() method, get the id of the node passed( which is the node corresponding to the use
     * case actor). Build the UMLActor object and fill the details. Get the diagrams from 'diagrams' list
     * which are of type UMLUseCaseDiagram. From the elementsMap of this diagram object, match the key to
     * the id of the element node.
     * Add this UMLActor object to the elementsMap with that key. This way by end of processing the
     * elementsMap will contain all child Actor elements. Because the XMIParser first processes all
     * diagrams before the elements, there should be no scope of elements not finding a suitable diagram.</p>
     * <p>The method is protected so that subclasses can override it.</p>
     * Note: if the corresponding diagram can't be located, throw an XMIHandlerException.
     *
     * @param node     the xmi node to parse
     * @param nodeType the node type for this node
     * @throws XMIHandlerException if the node can't be parsed correctly
     */
    protected void createActor(Node node, String nodeType) throws XMIHandlerException {
    }

    /**
     * This method is called to parse the StateMachine.top element.
     * It should do the following:
     * 1. retrieve all ActionState child elements. For each such element create an instance of the UMLState
     *    object using the information containing in the element. Find the corresponding UMLActivityDiagram
     *    for this ActionState - iterate over all activity diagrams; for each diagram check if the
     *    state.getId() exists in the elements map; if yes add the ActionState object as the value. If no
     *    corresponding activity diagram was found, the XMI data is incosistent and an XMIHandlerException
     *    should be thrown.
     * 2. do the step 1 for all Pseudostate child elements with kind attribute equal to 'initial'.
     * 3. do the step 1 for all FinalState child elements.
     *
     * @param node     the xmi node to parse
     * @param nodeType the node type for this node
     * @throws XMIHandlerException if the node can't be parsed correctly
     */
    protected void createStateMachine_top(Node node, String nodeType) throws XMIHandlerException {
    }

    /**
     * This method is called to parse the StateMachine.transitions element.
     * It should do the following:
     * retrieve all Transition child elements. For each such element create an instance of the UMLTransition
     * object using the information containing      *  the element. Find the corresponding UMLActivityDiagram
     * for this transition - iterate over all activity diagrams; for each diagram check if the
     * transition.getId() exists in the elements map; if yes add the transition object as the value.
     * If no corresponding activity diagram was found, the XMI data is incosistent and an
     * XMIHandlerException should be thrown.
     *
     * @param node     the xmi node to parse
     * @param nodeType the node type for this node
     * @throws XMIHandlerException if the node can't be parsed correctly
     */
    protected void createStateMachine_transitions(Node node, String nodeType) throws XMIHandlerException {
    }

    /********************************************************
     * diagrams when parser is called with xmi string:
     *
     * There will be:
     * - one UseCase diagram, that has 2 actors and 3 use cases;
     * - one Activity diagram, with two initial states, 3 action states,
     * 1 final state
     *
     *********************************************************/
    private void prepareIfXmiString() {
        //create use case diagram
        UMLUseCaseDiagram useCaseDiagram = new UMLUseCaseDiagram();
        useCaseDiagram.setDiagramName("UseCase diagram1");
        useCaseDiagram.setDiagramId("I808680m1098bca64b3mm7f51");
        //add actors
        UMLActor actor1 = new UMLActor();
        actor1.setElementName("Actor 1");
        actor1.setElementId("I808680m1098bca64b3mm7f4e");
        useCaseDiagram.addElement(actor1.getElementId(), actor1);

        UMLActor actor2 = new UMLActor();
        actor2.setElementName("Actor 2");
        actor2.setElementId("I808680m1098bca64b3mm7ef3");
        useCaseDiagram.addElement(actor2.getElementId(), actor2);

        //add use cases
        UMLUseCase useCase1 = new UMLUseCase();
        useCase1.setElementName("Use Case 1");
        useCase1.setElementId("I808680m1098bca64b3mm7f47");
        useCase1.setIsAbstract(false);
        useCaseDiagram.addElement(useCase1.getElementId(), useCase1);

        UMLUseCase useCase2 = new UMLUseCase();
        useCase2.setElementName("Use Case 2");
        useCase2.setElementId("I808680m1098bca64b3mm7f1d");
        useCase2.setIsAbstract(false);
        useCaseDiagram.addElement(useCase2.getElementId(), useCase2);

        UMLUseCase useCase3 = new UMLUseCase();
        useCase3.setElementName("Use Case 3");
        useCase3.setElementId("I808680m1098bca64b3mm7ed2");
        useCase3.setIsAbstract(false);
        useCaseDiagram.addElement(useCase3.getElementId(), useCase3);

        //create Activity Diagram
        UMLActivityDiagram activityDiagram = new UMLActivityDiagram();
        activityDiagram.setDiagramName("Activity diagram_1");
        activityDiagram.setDiagramId("I808680m1098bca64b3mm7ea5");
        //create initials and final state
        UMLState init1 = new UMLState("I808680m1098bca64b3mm7ea2", UMLStateType.INITIAL_STATE, "First Initial State");
        init1.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        UMLState init2 = new UMLState("I808680m1098bca64b3mm7e9d", UMLStateType.INITIAL_STATE, "Second Initial State");
        init2.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        UMLState finalSt = new UMLState("I808680m1098bca64b3mm7e47", UMLStateType.FINAL_STATE, "Final State 1");
        finalSt.setElementType(UMLElementTypes.UML_FINAL_STATE);
        activityDiagram.addElement(init1.getId(), init1);
        activityDiagram.addElement(init2.getId(), init2);
        activityDiagram.addElement(finalSt.getId(), finalSt);

        //add action states
        UMLState state1 = new UMLState("I808680m1098bca64b3mm7e73", UMLStateType.ACTION_STATE, "Action State 1");
        state1.setElementType(UMLElementTypes.UML_ACTION_STATE);
        UMLState state2 = new UMLState("I808680m1098bca64b3mm7e85", UMLStateType.ACTION_STATE, "Action State 2");
        state2.setElementType(UMLElementTypes.UML_ACTION_STATE);
        UMLState state3 = new UMLState("I808680m1098bca64b3mm7e61", UMLStateType.ACTION_STATE, "Action State 3");
        state3.setElementType(UMLElementTypes.UML_ACTION_STATE);
        activityDiagram.addElement(state1.getId(), state1);
        activityDiagram.addElement(state2.getId(), state2);
        activityDiagram.addElement(state3.getId(), state3);

        //add transition
        UMLTransition init1_to_st1 = new UMLTransition("I808680m1098bca64b3mm7e6b", "I808680m1098bca64b3mm7ea2",
                "I808680m1098bca64b3mm7e73", "first_to_state1", "guard condition 1", "guard effect 1");
        UMLTransition init2_to_st2 = new UMLTransition("I808680m1098bca64b3mm7e7d", "I808680m1098bca64b3mm7e9d",
                "I808680m1098bca64b3mm7e85", "second_to_state2", "guard condition 2", "guard effect 2");
        UMLTransition st1_to_st3 = new UMLTransition("I808680m1098bca64b3mm7e50", "I808680m1098bca64b3mm7e73",
                "I808680m1098bca64b3mm7e61", "state1_to_state3", "guard condition 3", "guard effect 3");
        UMLTransition st2_to_st3 = new UMLTransition("I808680m1098bca64b3mm7e59", "I808680m1098bca64b3mm7e85",
                "I808680m1098bca64b3mm7e61", "state2_to_state3", "guard condition 4", "guard effect 4");
        UMLTransition state3_to_final = new UMLTransition("I808680m1098bca64b3mm7e42", "I808680m1098bca64b3mm7e61",
                "I808680m1098bca64b3mm7e47", "state3_to_final", "guard condition 5", "guard effect 5");
        activityDiagram.addElement(init1_to_st1.getId(), init1_to_st1);
        activityDiagram.addElement(init2_to_st2.getId(), init2_to_st2);
        activityDiagram.addElement(st1_to_st3.getId(), st1_to_st3);
        activityDiagram.addElement(st2_to_st3.getId(), st2_to_st3);
        activityDiagram.addElement(state3_to_final.getId(), state3_to_final);

        diagrams.clear();
        diagrams.add(useCaseDiagram);
        diagrams.add(activityDiagram);
    }

    /********************************************************
     * diagrams when parser is called with fileInputStream:
     *
     * There will be:
     * - one UseCase diagram, that has 1 actor and 2 use cases;
     * - two Activity diagrams, that corresponds to use cases
     *
     *********************************************************/
    private void prepareIfStream() {
        //create use case diagram
        UMLUseCaseDiagram useCaseDiagram = new UMLUseCaseDiagram();
        useCaseDiagram.setDiagramName("UseCase diagram");
        useCaseDiagram.setDiagramId("I808680m1098bca64b3mm7dcb");
        //add actors
        UMLActor actor1 = new UMLActor();
        actor1.setElementName("User");
        actor1.setElementId("I808680m1098bca64b3mm7dc8");
        useCaseDiagram.addElement(actor1.getElementId(), actor1);

        //add use cases
        UMLUseCase useCase1 = new UMLUseCase();
        useCase1.setElementName("Diagram Validation");
        useCase1.setElementId("I808680m1098bca64b3mm7dc1");
        useCase1.setIsAbstract(false);
        useCaseDiagram.addElement(useCase1.getElementId(), useCase1);

        UMLUseCase useCase2 = new UMLUseCase();
        useCase2.setElementName("Submission Creation");
        useCase2.setElementId("I808680m1098bca64b3mm7d97");
        useCase2.setIsAbstract(false);
        useCaseDiagram.addElement(useCase2.getElementId(), useCase2);

        //create first Activity Diagram
        UMLActivityDiagram activityDiagram1 = new UMLActivityDiagram();
        activityDiagram1.setDiagramName("Diagram Validation Activity diagram");
        activityDiagram1.setDiagramId("I808680m1098bca64b3mm7cf2");
        //create initials and final state
        UMLState init1 = new UMLState("I808680m1098bca64b3mm7cef", UMLStateType.INITIAL_STATE, "Initial");
        init1.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        UMLState finalSt = new UMLState("I808680m1098bca64b3mm7cea", UMLStateType.FINAL_STATE, "Final");
        finalSt.setElementType(UMLElementTypes.UML_FINAL_STATE);
        activityDiagram1.addElement(init1.getId(), init1);
        activityDiagram1.addElement(finalSt.getId(), finalSt);

        //add action state
        UMLState state1 = new UMLState("I808680m1098bca64b3mm7ce4", UMLStateType.ACTION_STATE, "Validate Diagram");
        state1.setElementType(UMLElementTypes.UML_ACTION_STATE);
        activityDiagram1.addElement(state1.getId(), state1);

        //add transition
        UMLTransition init1_to_st1 = new UMLTransition("I808680m1098bca64b3mm7cdc", "I808680m1098bca64b3mm7cef",
                "I808680m1098bca64b3mm7ce4", "validate", "", "");
        UMLTransition st1_to_final = new UMLTransition("I808680m1098bca64b3mm7e7d", "I808680m1098bca64b3mm7ce4",
                "I808680m1098bca64b3mm7cea", "result", "", "");
        activityDiagram1.addElement(init1_to_st1.getId(), init1_to_st1);
        activityDiagram1.addElement(st1_to_final.getId(), st1_to_final);


        //create second Activity Diagram
        UMLActivityDiagram activityDiagram2 = new UMLActivityDiagram();
        activityDiagram2.setDiagramName("Submission Creation Activity");
        activityDiagram2.setDiagramId("I808680m1098bca64b3mm7cc8");
        //create initials and final state
        UMLState init2 = new UMLState("I808680m1098bca64b3mm7cc5", UMLStateType.INITIAL_STATE, "Begin");
        init2.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        UMLState fina2St = new UMLState("I808680m1098bca64b3mm7cc0", UMLStateType.FINAL_STATE, "End");
        fina2St.setElementType(UMLElementTypes.UML_FINAL_STATE);
        activityDiagram2.addElement(init2.getId(), init2);
        activityDiagram2.addElement(fina2St.getId(), fina2St);

        //add action state
        UMLState state2 = new UMLState("I808680m1098bca64b3mm7cba", UMLStateType.ACTION_STATE, "Create Submission");
        state2.setElementType(UMLElementTypes.UML_ACTION_STATE);
        activityDiagram2.addElement(state2.getId(), state2);

        //add transition
        UMLTransition init2_to_st2 = new UMLTransition("I808680m1098bca64b3mm7cb2", "I808680m1098bca64b3mm7cc5",
                "I808680m1098bca64b3mm7cba", "create", "", "");
        UMLTransition st2_to_final = new UMLTransition("I808680m1098bca64b3mm7ca9", "I808680m1098bca64b3mm7cba",
                "I808680m1098bca64b3mm7cc0", "submission", "", "");
        activityDiagram2.addElement(init2_to_st2.getId(), init2_to_st2);
        activityDiagram2.addElement(st2_to_final.getId(), st2_to_final);

        diagrams.clear();
        diagrams.add(useCaseDiagram);
        diagrams.add(activityDiagram1);
        diagrams.add(activityDiagram2);
    }

    /********************************************************
     * diagrams when parser is called with file:
     *
     * There will be:
     * - one Activity diagram, that has 2 initial and 2 final states,
     * and 2 loops.
     *
     *********************************************************/
    private void prepareIfFile() {
        //create Activity Diagram
        UMLActivityDiagram activityDiagram = new UMLActivityDiagram();
        activityDiagram.setDiagramName("Activity diagram with loops");
        activityDiagram.setDiagramId("I808680m1098bca64b3mm7c99");
        //create initials and final state
        UMLState init1 = new UMLState("I808680m1098bca64b3mm7c96", UMLStateType.INITIAL_STATE, "Initial State 1");
        init1.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        UMLState init2 = new UMLState("I808680m1098bca64b3mm7c91", UMLStateType.INITIAL_STATE, "Initial State 2");
        init2.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        UMLState finalSt1 = new UMLState("I808680m1098bca64b3mm7c29", UMLStateType.FINAL_STATE, "Final State 1");
        finalSt1.setElementType(UMLElementTypes.UML_FINAL_STATE);
        UMLState finalSt2 = new UMLState("I808680m1098bca64b3mm7c1b", UMLStateType.FINAL_STATE, "Final State 2");
        finalSt2.setElementType(UMLElementTypes.UML_FINAL_STATE);
        activityDiagram.addElement(init1.getId(), init1);
        activityDiagram.addElement(init2.getId(), init2);
        activityDiagram.addElement(finalSt1.getId(), finalSt1);
        activityDiagram.addElement(finalSt2.getId(), finalSt2);

        //add action states
        UMLState state1 = new UMLState("I808680m1098bca64b3mm7c8b", UMLStateType.ACTION_STATE, "Action State 1");
        state1.setElementType(UMLElementTypes.UML_ACTION_STATE);
        UMLState state2 = new UMLState("I808680m1098bca64b3mm7c79", UMLStateType.ACTION_STATE, "Action State 2");
        state2.setElementType(UMLElementTypes.UML_ACTION_STATE);
        UMLState state3 = new UMLState("I808680m1098bca64b3mm7c55", UMLStateType.ACTION_STATE, "Action State 3");
        state3.setElementType(UMLElementTypes.UML_ACTION_STATE);
        UMLState state4 = new UMLState("I808680m1098bca64b3mm7c0c", UMLStateType.ACTION_STATE, "Action State 4");
        state4.setElementType(UMLElementTypes.UML_ACTION_STATE);
        activityDiagram.addElement(state1.getId(), state1);
        activityDiagram.addElement(state2.getId(), state2);
        activityDiagram.addElement(state3.getId(), state3);
        activityDiagram.addElement(state4.getId(), state4);

        //add transition
        UMLTransition init1_to_st1 = new UMLTransition("I808680m1098bca64b3mm7c83", "I808680m1098bca64b3mm7c96",
                "I808680m1098bca64b3mm7c8b", "", "", "");
        UMLTransition init2_to_st1 = new UMLTransition("I808680m1098bca64b3mm7c68", "I808680m1098bca64b3mm7c91",
                "I808680m1098bca64b3mm7c8b", "", "", "");

        UMLTransition st1_to_st2 = new UMLTransition("I808680m1098bca64b3mm7c5f", "I808680m1098bca64b3mm7c8b",
                "I808680m1098bca64b3mm7c79", "", "", "");
        UMLTransition st1_to_st4 = new UMLTransition("I808680m1098bca64b3mm7c04", "I808680m1098bca64b3mm7c8b",
                "I808680m1098bca64b3mm7c0c", "", "", "");

        UMLTransition st4_to_final1 = new UMLTransition("I808680m1098bca64b3mm7bfb", "I808680m1098bca64b3mm7c0c",
                "I808680m1098bca64b3mm7c29", "", "", "");

        UMLTransition st2_to_final1 = new UMLTransition("I808680m1098bca64b3mm7c24", "I808680m1098bca64b3mm7c79",
                "I808680m1098bca64b3mm7c29", "", "", "");
        UMLTransition st2_to_st3 = new UMLTransition("I808680m1098bca64b3mm7c4d", "I808680m1098bca64b3mm7c79",
                "I808680m1098bca64b3mm7c55", "", "", "");

        UMLTransition st3_to_st1 = new UMLTransition("I808680m1098bca64b3mm7c44", "I808680m1098bca64b3mm7c55",
                "I808680m1098bca64b3mm7c8b", "", "", "");
        UMLTransition st3_to_final2 = new UMLTransition("I808680m1098bca64b3mm7c16", "I808680m1098bca64b3mm7c55",
                "I808680m1098bca64b3mm7c1b", "", "", "");

        activityDiagram.addElement(init1_to_st1.getId(), init1_to_st1);
        activityDiagram.addElement(init2_to_st1.getId(), init2_to_st1);
        activityDiagram.addElement(st1_to_st2.getId(), st1_to_st2);
        activityDiagram.addElement(st1_to_st4.getId(), st1_to_st4);
        activityDiagram.addElement(st4_to_final1.getId(), st4_to_final1);
        activityDiagram.addElement(st2_to_final1.getId(), st2_to_final1);
        activityDiagram.addElement(st2_to_st3.getId(), st2_to_st3);
        activityDiagram.addElement(st3_to_st1.getId(), st3_to_st1);
        activityDiagram.addElement(st3_to_final2.getId(), st3_to_final2);

        diagrams.clear();
        diagrams.add(activityDiagram);
    }

    /********************************************************
     * diagrams when parser is called with file and file is used for demo:
     *
     * There will be:
     * - one Activity diagram
     * - one UseCase diagram
     *
     *********************************************************/
    private void prepareIfDemo() {
        //create use case diagram
        UMLUseCaseDiagram useCaseDiagram = new UMLUseCaseDiagram();
        useCaseDiagram.setDiagramName("UseCase diagram");
        useCaseDiagram.setDiagramId("I1deb5f7m109917a3e45mm7d65");
        //add actor
        UMLActor actor = new UMLActor();
        actor.setElementName("Actor");
        actor.setElementId("I1deb5f7m109917a3e45mm7d62");
        useCaseDiagram.addElement(actor.getElementId(), actor);
        //add use case
        UMLUseCase useCase = new UMLUseCase();
        useCase.setElementName("Order Confirmation");
        useCase.setElementId("I1deb5f7m109917a3e45mm7d5b");
        useCaseDiagram.addElement(useCase.getElementId(), useCase);

        //create Activity Diagram
        UMLActivityDiagram activityDiagram = new UMLActivityDiagram();
        activityDiagram.setDiagramName("Order Confirmation activity diagram");
        activityDiagram.setDiagramId("I1deb5f7m109917a3e45mm7d2f");
        //create initials and final state
        UMLState init1 = new UMLState("I1deb5f7m109917a3e45mm7d2c", UMLStateType.INITIAL_STATE, "Begin");
        init1.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        UMLState finalSt1 = new UMLState("I1deb5f7m109917a3e45mm7cdf", UMLStateType.FINAL_STATE, "End");
        finalSt1.setElementType(UMLElementTypes.UML_FINAL_STATE);
        activityDiagram.addElement(init1.getId(), init1);
        activityDiagram.addElement(finalSt1.getId(), finalSt1);

        //add action states
        UMLState state1 = new UMLState("I1deb5f7m109917a3e45mm7d26", UMLStateType.ACTION_STATE, "Calculate Tax");
        state1.setElementType(UMLElementTypes.UML_ACTION_STATE);
        UMLState state2 = new UMLState("I1deb5f7m109917a3e45mm7d14", UMLStateType.ACTION_STATE, "Calculate Restaurant Total");
        state2.setElementType(UMLElementTypes.UML_ACTION_STATE);
        UMLState state3 = new UMLState("I1deb5f7m109917a3e45mm7d02", UMLStateType.ACTION_STATE, "Calculate Delivery Charge");
        state3.setElementType(UMLElementTypes.UML_ACTION_STATE);
        UMLState state4 = new UMLState("I1deb5f7m109917a3e45mm7cf0", UMLStateType.ACTION_STATE, "Calculate Grand Total");
        state4.setElementType(UMLElementTypes.UML_ACTION_STATE);
        activityDiagram.addElement(state1.getId(), state1);
        activityDiagram.addElement(state2.getId(), state2);
        activityDiagram.addElement(state3.getId(), state3);
        activityDiagram.addElement(state4.getId(), state4);

        //add transition
        UMLTransition tr1 = new UMLTransition("I1deb5f7m109917a3e45mm7d1e", "I1deb5f7m109917a3e45mm7d2c",
                "I1deb5f7m109917a3e45mm7d26", "", "", "");
        UMLTransition tr2 = new UMLTransition("I1deb5f7m109917a3e45mm7d0c", "I1deb5f7m109917a3e45mm7d26",
                "I1deb5f7m109917a3e45mm7d14", "", "", "");
        UMLTransition tr3 = new UMLTransition("I1deb5f7m109917a3e45mm7cfa", "I1deb5f7m109917a3e45mm7d14",
                "I1deb5f7m109917a3e45mm7d02", "", "", "");
        UMLTransition tr4 = new UMLTransition("I1deb5f7m109917a3e45mm7ce8", "I1deb5f7m109917a3e45mm7d02",
                "I1deb5f7m109917a3e45mm7cf0", "", "", "");
        UMLTransition tr5 = new UMLTransition("I1deb5f7m109917a3e45mm7cda", "I1deb5f7m109917a3e45mm7cf0",
                "I1deb5f7m109917a3e45mm7cdf", "", "", "");

        activityDiagram.addElement(tr1.getId(), tr1);
        activityDiagram.addElement(tr2.getId(), tr2);
        activityDiagram.addElement(tr3.getId(), tr3);
        activityDiagram.addElement(tr4.getId(), tr4);
        activityDiagram.addElement(tr5.getId(), tr5);

        diagrams.clear();
        diagrams.add(activityDiagram);
        diagrams.add(useCaseDiagram);
    }

}
