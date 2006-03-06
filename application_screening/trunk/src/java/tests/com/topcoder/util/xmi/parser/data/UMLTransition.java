package com.topcoder.util.xmi.parser.data;

/**
 * This class represents a single UML activity graph transition. The transition is described by the following
 * data:
 * 0. XMI id of the transition
 * 1. XMI id of the from node
 * 2. XMI id of the to node
 * 3. transition name;
 * 4. guard condition for transition to be executed
 * 5. guard effect for the executed transition
 * Below is an example of the transition element taken from the XMI file.
 * <UML:Transition xmi.id = 'I1aa8b42m10480f27c47mm7c1a' name = 'no account''
 * isSpecification = 'false'>
 * <UML:Transition.guard>
 * <UML:Guard xmi.id = 'I1971f66m108e9b617c5mm76cd' name = 'no account exists for email provided'
 * visibility = 'public' isSpecification = 'false'>
 * <UML:Guard.expression>
 * <UML:BooleanExpression xmi.id = 'I1971f66m108e9b617c5mm76cc' language = 'java'
 * body = ''/>
 * </UML:Guard.expression>
 * </UML:Guard>
 * </UML:Transition.guard>
 * <UML:Transition.effect>
 * <UML:CallAction xmi.id = 'I1971f66m108e9b617c5mm76c3' name = 'guard condition effect'
 * isSpecification = 'false' isAsynchronous = 'false'>
 * <UML:Action.script>
 * <UML:ActionExpression xmi.id = 'I1971f66m108e9b617c5mm76c2' language = 'java'
 * body = ''/>
 * </UML:Action.script>
 * </UML:CallAction>
 * </UML:Transition.effect>
 * <UML:Transition.source>
 * <UML:ActionState xmi.idref = 'I1aa8b42m10480f27c47mm7c83'/>
 * </UML:Transition.source>
 * <UML:Transition.target>
 * <UML:ActionState xmi.idref = 'I1aa8b42m10480f27c47mm7c24'/>
 * </UML:Transition.target>
 * </UML:Transition>
 * <UML:Transition xmi.id = 'Id9cc1am10486c9faaemm7623' name = '[else]' isSpecification = 'false'>
 * <UML:Transition.source>
 * <UML:ActionState xmi.idref = 'I1aa8b42m10480f27c47mm7c83'/>
 * </UML:Transition.source>
 * <UML:Transition.target>
 * <UML:ActionState xmi.idref = 'I1aa8b42m10480f27c47mm7c4f'/>
 * </UML:Transition.target>
 * </UML:Transition>
 * This element has the following properties:
 * 0. transition id is "I1aa8b42m10480f27c47mm7c1a"
 * 1. fromId (i.e. the transition source) is "I1aa8b42m10480f27c47mm7c83"
 * 2. toId (i.e. the transition target) is "I1aa8b42m10480f27c47mm7c24"
 * 3. name is "no account"
 * 4. guard condition is "no account exists for email provided"
 * 5. guard effect is "guard condition effect"
 * Such elements will be parsed by the DefaultXMIHandler and converted to UMLTransition
 * instances.
 * The class extends the mutable and not thread-safe base (UMLTransition).
 */
public class UMLTransition extends UMLElement {

    /**
     * Represents an XMI id of the transition. It is assigned in the constructor and not changed afterwards.
     * The value can never be null or an empty string, The value can be retrieved using geId() method.
     */
    private String id;

    /**
     * Represents an XMI id of the source node. It is assigned in the constructor and not changed afterwards.
     * The value can never be null or an empty string, The value can be retrieved using getFromId() method.
     */
    private final String fromId;

    /**
     * Represents an XMI id of the target node. It is assigned in the constructor and not changed afterwards.
     * The value can never be null or an empty string, The value can be retrieved using getToId() method.
     */
    private final String toId;

    /**
     * Represents the transition name. It is assigned in the constructor and not changed afterwards.
     * The value can never be null but it can be an empty string.
     * The value can be retrieved using getName() method.
     */
//    private final String name;

    /**
     * Represents the guard condition name. It is assigned in the constructor and not changed afterwards.
     * The value can never be null but it can be an empty string.
     * The value can be retrieved using getGuardCondition() method.
     */
    private final String guardCondition;

    /**
     * Represents the guard effect name. It is assigned in the constructor and not changed afterwards.
     * The value can never be null but it can be an empty string.
     * The value can be retrieved using getGuardEffect() method.
     */
    private final String guardEffect;

    /**
     * Creates a transition object with the given parameters.
     *
     * @param id             the transition id to use
     * @param fromId         the source node to use
     * @param toId           the target node to use
     * @param name           the transition name to use
     * @param guardCondition the guard condition name to use
     * @param guardEffect    the guard effect name to use
     * @throws NullPointerException     if any parameter is null
     * @throws IllegalArgumentException if id or fromId or toId is an empty string
     */
    public UMLTransition(String id,
                         String fromId,
                         String toId,
                         String name,
                         String guardCondition,
                         String guardEffect) {
        this.fromId = fromId;
        this.toId = toId;
        setElementId(id);
        setElementName(name);
        this.guardCondition = guardCondition;
        this.guardEffect = guardEffect;
        setElementType(UMLElementTypes.UML_TRANSITION);
    }

    /**
     * A simple getter for the transition XMI id.
     *
     * @return the transition XMI id
     */
    public String getId() {
        return getElementId();
    }

    /**
     * A simple getter for the source node XMI id.
     *
     * @return the source node id
     */
    public String getFromId() {
        return fromId;
    }

    /**
     * A simple getter for the target node XMI id.
     *
     * @return the target node id
     */
    public String getToId() {
        return toId;
    }

    /**
     * A simple getter for the transition name.
     *
     * @return the transition name
     */
    public String getName() {
        return getElementName();
    }

    /**
     * A simple getter for the guard condition.
     *
     * @return the guard condition
     */
    public String getGuardCondition() {
        return guardCondition;
    }

    /**
     * A simple getter for the guard effect.
     *
     *
     * @return the guard effect
     */
    public String getGuardEffect() {
        return guardEffect;
    } 
}