package com.topcoder.mobile.filterlistscreen;

/**
 * <p>
 * This class defines some constants for filtering operations.
 * </p>
 * <p>
 * The filtering operations constants as below:
 * </p>
 * <ul>
 * <li>OP_EQUALS - This means Equals operation, valid for string or integer values</li>
 * <li>OP_NOTEQUALS - This means Not Equals operation, valid for string or integer values</li>
 * <li>OP_GREATER - This means Greater Than operation, valid for string or integer values</li>
 * <li>OP_LESS - This means Less Than operation, valid for string or integer values</li>
 * <li>OP_CONTAINS - This means Contains operation, valid for string values</li>
 * </ul>
 * <p>
 * In this design, each filtering operation is defined as FilterListOperator, each FilterListOperator has its name and
 * the valid classes that it applies.
 * </p>
 * <p>
 * Thread Safety : This class is thread safety since it is immutable.
 * </p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7e43]
 */
public class FilterListOperator {

    /**
     * <p>
     * Represents the Equals operation for filtering.
     * </p>
     * <p>
     * This operation applies to the types of String and integer.
     * </p>
     * <p>
     * It is static and immutable, it can be treated as an constant.
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7c0f]
     */
    public static final FilterListOperator OP_EQUALS = new FilterListOperator("op_equals");

    /**
     * <p>
     * Represents the Not Equals operation for filtering.
     * </p>
     * <p>
     * This operation applies to the types of String and integer.
     * </p>
     * <p>
     * It is static and immutable, it can be treated as an constant.
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7c09]
     */
    public static final FilterListOperator OP_NOTEQUALS = new FilterListOperator("op_notequals");

    /**
     * <p>
     * Represents the Greater Than operation for filtering.
     * </p>
     * <p>
     * This operation applies to the types of String and integer.
     * </p>
     * <p>
     * It is static and immutable, it can be treated as an constant.
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7c03]
     */
    public static final FilterListOperator OP_GREATER = new FilterListOperator("op_greater");

    /**
     * <p>
     * Represents the Less Than operation for filtering.
     * </p>
     * <p>
     * This operation applies to the types of String and integer.
     * </p>
     * <p>
     * It is static and immutable, it can be treated as an constant.
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7bfd]
     */
    public static final FilterListOperator OP_LESS = new FilterListOperator("op_less");

    /**
     * <p>
     * Represents the Contains operation for filtering.
     * </p>
     * <p>
     * This operation applies to the types of String.
     * </p>
     * <p>
     * It is static and immutable, it can be treated as an constant.
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7bf7]
     */
    public static final FilterListOperator OP_CONTAINS = new FilterListOperator("op_contains");

    /**
     * <p>
     * This is the name of the filter operation.
     * </p>
     * <p>
     * Initial Value: Set in the constructor and immutable
     * </p>
     * <p>
     * Accessed In: getName
     * </p>
     * <p>
     * Modified In: None
     * </p>
     * <p>
     * Valid Values: Strings that are non-null and non-empty
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7bf1]
     */
    private final String name;

    /**
     * <p>
     * Constructor with name and supportedTypes given.
     * </p>
     * <p>
     * This is private to prevent instances of this class being created outside of the class.
     * </p>
     * <p>
     * Implementation Note: check the arguments; this.name = name; clone the given supportedTypes to the corresponding
     * variable;
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7bdf]
     * @param name
     *            the name of the filter operation
     * @param supportedTypes
     *            the supported types of the filter operation.
     * @throws IllegalArgumentException
     *             if name is null or empty, or supportedTypes is null or contains null element
     */
    private FilterListOperator(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Retrieves the name of the filter operation.
     * </p>
     * <p>
     * Implementation Note: return name;
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7bd6]
     * @return the name of the filter operation.
     */
    public String getName() {
        // your code here
        return null;
    }
}
