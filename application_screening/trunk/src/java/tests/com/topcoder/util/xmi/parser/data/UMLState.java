package com.topcoder.util.xmi.parser.data;

/**
 * This class represents a single UML activity graph state. Each state is described by the following
 * information:
 * 1. an XMI id
 * 2. a state type
 * 3. a state name
 * Below is an example of the state element taken from the XMI file:
 * <UML:FinalState xmi.id = 'I1aa8b42m10480f27c47mm7c91' name = 'end' visibility = 'public'
 * isSpecification = 'false'>
 * <UML:StateVertex.incoming>
 * <UML:Transition xmi.idref = 'I1c882dbm10485cc4cacmm7630'/>
 * <UML:Transition xmi.idref = 'I1c882dbm10485cc4cacmm7627'/>
 * </UML:StateVertex.incoming>
 * </UML:FinalState>
 * This state has the following properties:
 * 1. XMI id is  'I1aa8b42m10480f27c47mm7c91'
 * 2. type is UMLStateType.FINAL_STATE
 * 3. name is 'end'.
 * The class extends the mutable and not thread-safe base (UMLElement).
 */
public class UMLState extends UMLElement {

    /**
     * Represents an XMI state id. It is assigned in the constructor and not changed afterwards.
     * It can never be null or an empty string. The value can be retrieved using getId() method.
     */
    private String id;

    /**
     * Represents an XMI state type. It is assigned in the constructor and not changed afterwards.
     * It can never be null. The value can be retrieved using getType() method.
     */
    private UMLStateType type;

    /**
     * Represents an XMI state name. It is assigned in the constructor and not changed afterwards.
     * It can never be null but it can be an empty string. The value can be retrieved using getName() method.
     */
//    private String name;

    /**
     * Constructor. Creates a state with the given parameters.
     *
     * @param id   the XMI id to use
     * @param type the type to use
     * @param name the name to use
     * @throws NullPointerException     if any parameter is null
     * @throws IllegalArgumentException if id is an empty string
     */
    public UMLState(String id, UMLStateType type, String name) {
        this.type = type;
        setElementName(name);
        setElementId(id);
//        if (type.equals(UMLStateType.ACTION_STATE)) {
//            setElementType(UMLElementTypes.UML_ACTION_STATE);
//        } else if (type.equals(UMLStateType.INITIAL_STATE)) {
//            setElementType(UMLElementTypes.UML_PSEUDO_STATE);
//        } else if (type.equals(UMLStateType.FINAL_STATE)) {
//            setElementType(UMLElementTypes.UML_FINAL_STATE);
//        }
    }

    /**
     * A public getter for the state id.
     *
     * @return the node id
     */
    public String getId() {
        return getElementId();
    }

    /**
     * A public getter for the state type.
     *
     * @return the state type
     */
    public UMLStateType getType() {
        return type;
    }

    /**
     * A public getter for the state name.
     *
     *
     * @return the state name
     */
    public String getName() {
        return getElementName();
    }
}