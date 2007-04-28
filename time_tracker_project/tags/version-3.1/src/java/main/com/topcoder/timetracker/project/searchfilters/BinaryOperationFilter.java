/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.searchfilters;

/**
 * <p>
 * This class represents a search filter which combines two other search filters, using a specific BinaryOperation such
 * as "AND" or "OR". The entity is accepted if the operation returns true value, and is not accepted otherwise.
 * </p>
 *
 * <p>
 * This class is immutable, i.e. thread-safe.
 * </p>
 *
 * @author real_vg, TCSDEVELOPER
 * @version 1.1
 */
public class BinaryOperationFilter implements Filter {
    /**
     * <p>
     * The left operand of the binary operation filter represented by this class. It is initialized in constructor and
     * then never changed. It cannot be null. Can be retrieved by the like-named getter method.
     * </p>
     */
    private final Filter leftOperand;

    /**
     * <p>
     * The right operand of the binary operation filter represented by this class. It is initialized in constructor and
     * then never changed. It cannot be null. Can be retrieved by the like-named getter method.
     * </p>
     */
    private final Filter rightOperand;

    /**
     * <p>
     * Represents a binary logical operation to be applied to the operand filters. It is initialized in constructor and
     * then never changed. It cannot be null. Can be retrieved by the like-named getter method.
     * </p>
     */
    private final BinaryOperation operation;

    /**
     * <p>
     * Constructs an instance of the class with the specified  BinaryOperation and operand filters.
     * </p>
     *
     * @param operation a binary logical operation to be applied to the operand filters.
     * @param leftOperand the left operand of the binary operation.
     * @param rightOperand the right operand of the binary operation.
     *
     * @throws IllegalArgumentException if any parameter is null
     */
    public BinaryOperationFilter(BinaryOperation operation, Filter leftOperand, Filter rightOperand) {
        if (operation == null) {
            throw new IllegalArgumentException("operation is null");
        }
        if (leftOperand == null) {
            throw new IllegalArgumentException("leftOperand is null");
        }
        if (rightOperand == null) {
            throw new IllegalArgumentException("rightOperand is null");
        }

        this.operation = operation;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    /**
     * <p>
     * Returns the left operand of the binary operation filter represented by this class.
     * </p>
     *
     * @return the left operand.
     */
    public Filter getLeftOperand() {
        return leftOperand;
    }

    /**
     * <p>
     * Returns the right operand of the binary operation filter represented by this class.
     * </p>
     *
     * @return the right operand.
     */
    public Filter getRightOperand() {
        return rightOperand;
    }

    /**
     * <p>
     * Returns the binary logical operation to be applied to the operand filters.
     * </p>
     *
     * @return the binary logical operation.
     */
    public BinaryOperation getOperation() {
        return operation;
    }
}
