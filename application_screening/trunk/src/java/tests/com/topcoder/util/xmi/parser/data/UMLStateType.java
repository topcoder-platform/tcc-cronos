package com.topcoder.util.xmi.parser.data;

/**
 * This class represents a UML state type. Only three types are currently defined: an initial state,
 * an action state and a final state.
 * The iniital state type corresponds to the UML:Pseudostate element with the 'kind' attribute equal to 'initial'.
 * The action state type corresponds to the UML:ActionState element.
 * The final state type corresponds to the UML:FinalState element.
 * Thread-Safety: This class is immitable and therefore thread-safe.
 */
public class UMLStateType {

    /**
     * Represents a string description of the UML state type. It is assigned in the constructor and not changed
     * afterwards. The value can be retrieved using public getType() method. It can never be null or
     * an empty string.
     */
    private final String type;

    /**
     * A public static constant for the initial state type.
     */
    public static final UMLStateType INITIAL_STATE = new UMLStateType("initial");

    /**
     * A public static constant for the action state type.
     */
    public static final UMLStateType ACTION_STATE = new UMLStateType("action");

    /**
     * A public static constant for the final state type.
     */
    public static final UMLStateType FINAL_STATE = new UMLStateType("final");

    /**
     * Creates a type with the given type string. This method is protected to ensure that adding of a new
     * type is possible but will require the class extension.
     *
     * @param type the type to use
     * @throws NullPointerException     if the parameter is null
     * @throws IllegalArgumentException if the parameter is an empty string
     */
    protected UMLStateType(String type) {
        this.type = type;
    }

    /**
     * A simple getter for the private type variable.
     *
     * @return the type string
     */
    public String getType() {
        return type;
    }

    /**
     * This method will compare this with the given object.
     * If the given object is null or not an instance of the UMLStateType, the method will return false.
     * Otherwise the method will return type.equals(((UMLStateType)obj).getType()).
     *
     * @param obj the object to compare with
     * @return true if this equals to the given object, otherwise false
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof UMLStateType)) {
            return false;
        }
        return type.equals(((UMLStateType)obj).getType());
    }

    /**
     * This method will return the hash code for the object. It simply returns the type.getHashCode().
     *
     *
     * @return the hash code value
     */
    public int getHashCode() {
        return type.hashCode();
    } 
}