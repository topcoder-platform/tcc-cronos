package com.topcoder.util.xmi.parser.data;

/**
 * <p>Class containing the constants for different element types. Please note that this is not a
 * complete list, but only contains those which are reqired by the default handler currently. This
 * needs to be enhanced with all the child node names contained in the 'UML:Namespace' of 'UML:Model'
 *  node in XMI.</p>
 */
public final class UMLElementTypes {

    /**
     * <p>Represents the UML Use case node type</p>
     */
    public static final String UML_USE_CASE = "UseCase";

    /**
     * <p>Represents the UML Actor node type</p>
     */
    public static final String UML_ACTOR = "Actor";

    /**
     * Represents the action state node type.
     */
    public static final String UML_ACTION_STATE = "ActionState";

    /**
     * Represents the pseudo state node type.
     */
    public static final String UML_PSEUDO_STATE = "Pseudostate";

    /**
     * Represents the final state node type.
     */
    public static final String UML_FINAL_STATE = "FinalState";

    /**
     * Represents the transition node type.
     */
    public static final String UML_TRANSITION = "Transition";

    /**
     * Represents the state machine top element. All state information is placed under this element.
     * No actual elements of this type will be created, however this definition is used by the XMI Parser
     * to locate activity diagram states.
     */
    public static final String UML_STATE_MACHINE_TOP = "StateMachine.top";

    /**
     * Represents the state machine transitions element. All transition information is placed under this element.
     * No actual elements of this type will be created, however this definition is used by the XMI Parser
     * to locate activity diagram transitions.
     */
    public static final String UML_STATE_MACHINE_TRANSITIONS = "StateMachine.transitions";
}