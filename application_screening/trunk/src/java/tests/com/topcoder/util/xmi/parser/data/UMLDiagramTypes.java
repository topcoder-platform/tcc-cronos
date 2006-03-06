package com.topcoder.util.xmi.parser.data;

/**
 * <p>The available diagram types. These constants can be used by client application to query the handler for diagrams. The strings here are the attribute values of 'typeInfo' attribute in &lt;UML:SimpleSemanticModelElement&gt; node directly under &lt;UML:Diagram&gt;. Notice that there are other SimpleSemanticModelElement nodes, but you have to get the one which is the direct child of the UML:Diagram</p>
 * <p>&nbsp;</p>
 */
public final class UMLDiagramTypes {

    /**
     * <p>Represents Use case diagram</p>
     */
    public static final String UML_USECASE_DIAGRAM = "UseCaseDiagram";

    /**
     * <p>Represents Class diagram</p>
     */
    public static final String UML_CLASS_DIAGRAM = "ClassDiagram";

    /**
     * <p>Represents Sequence diagram</p>
     */
    public static final String UML_SEQUENCE_DIAGRAM = "SequenceDiagram";

    /**
     * <p>Represents Collaboration diagram</p>
     */
    public static final String UML_COLLABORATION_DIAGRAM = "CollaborationDiagram";

    /**
     * <p>Represents Activity diagram</p>
     */
    public static final String UML_ACTIVITY_DIAGRAM = "ActivityDiagram";

    /**
     * <p>Represents State diagram</p>
     */
    public static final String UML_STATE_DIAGRAM = "StateDiagram";

    /**
     * <p>Represents Deployment/Component diagram</p>
     *
     */
    public static final String UML_DEPLOYMENT_DIAGRAM = "DeploymentDiagram";
 }
