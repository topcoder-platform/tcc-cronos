/*
 * @(#)UMLUseCaseDiagram.java
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.util.xmi.parser.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * <p>
 * <code>UMLUseCaseDiagram</code> class represents the UseCase diagrams. It
 * provides convenient methods to get elements belonging specifically to
 * UseCase diagrams like UseCase, Actor etc.
 * </p>
 *
 * <p>
 * Please note that only two methods are suggested and only getUseCases() is
 * the relevant one for the current requirement. More methods related to this
 * diagram can be added during while the component is enhanced.
 * </p>
 *
 * @author TCSDESIGNER
 * @author smell
 * @version 1.0
 */
public class UMLUseCaseDiagram extends UMLDiagram {
    /**
     * <p>
     * Create a UMLUseCaseDiagram.
     * </p>
     */
    public UMLUseCaseDiagram() {
        setDiagramType(UMLDiagramTypes.UML_USECASE_DIAGRAM);
    }

    /**
     * <p>
     * Returns a list of UMLUseCase class objects. Returns empty list, if no
     * use case are found.
     * </p>
     *
     * @return the list of UMLUseCase if found, empty list otherwise
     */
    public List getUseCases() {
        List list = new ArrayList();

        Iterator it = getElements().iterator();

        while (it.hasNext()) {
            UMLElement e = (UMLElement) it.next();

            if (UMLElementTypes.UML_USE_CASE.equals(e.getElementType())) {
                list.add(e);
            }
        }

        return list;
    }

    /**
     * <p>
     * Returns a list of UMLActor class objects.
     * </p>
     *
     * <p>
     * Returns empty list for this requirement as UMLActor need not be
     * implemented.
     * </p>
     *
     * @return an empty list
     */
    public List getActors() {
        Iterator elements = getElements().iterator();
        List actors = new ArrayList();
        while(elements.hasNext()) {
            UMLElement element = (UMLElement) elements.next();
            if (UMLElementTypes.UML_ACTOR.equals(element.getElementType())) {
                actors.add(element);
            }
        }
        return actors;
    }
}
