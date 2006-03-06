/*
 * @(#)UMLUseCase.java
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.util.xmi.parser.data;

/**
 * <p>
 * Data object for a use case. This class has field about whether the use case
 * is abstract or concrete. Use setter/getter methods to access the field.
 * </p>
 *
 * @author TCSDESIGNER
 * @author smell
 * @version 1.0
 */
public class UMLUseCase extends UMLElement {
    /**
     * <p>
     * Indicates whether the Use Case is abstract. This is set based on the
     * isAbstract attribute in UML:UseCase node
     * </p>
     */
    private boolean isAbstract = false;

    /**
     * <p>
     * Create a UMLUseCase, the new instance defaults to not abstract.
     * </p>
     */
    public UMLUseCase() {
        setElementType(UMLElementTypes.UML_USE_CASE);
    }

    /**
     * <p>
     * Returns whether the UML Use Case is abstract.
     * </p>
     *
     * @return true if the Use Case is abstract, false otherwise
     */
    public boolean isAbstract() {
        return isAbstract;
    }

    /**
     * <p>
     * Sets is whether the UML Use Case is abstract.
     * </p>
     *
     * @param isAbstract - whether the UML Use Case is abstract
     */
    public void setIsAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }
}
