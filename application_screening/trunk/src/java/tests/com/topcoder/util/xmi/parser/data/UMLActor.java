package com.topcoder.util.xmi.parser.data;

/**
 * <p></p>
 */
public class UMLActor extends UMLElement {

    private String name;

    public UMLActor() {
        setElementType(UMLElementTypes.UML_ACTOR);
    }

    /**
     * <p>Does ...</p>
     *
     * @return name
     */
    public String getRoleName() {
        return name;
    }
}
