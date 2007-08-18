
package com.topcoder.mobile.filterlistscreen.conditions;
import com.topcoder.mobile.filterlistscreen.*;
import com.topcoder.mobile.filterlistscreen.FilterCondition;
import com.topcoder.mobile.filterlistscreen.FilterListEntry;
import com.topcoder.mobile.filterlistscreen.FilterListOperator;
import com.topcoder.mobile.filterlistscreen.InvalidDataTypeException;

/**
 * <p>
 * This class implements the FilterCondition interface and so is used to filter the entries on some
 * integer columns.
 * </p>
 * 
 * <p>
 * In this class, a condition is defined as (operation, value, index), the operation means the filter
 * operation, it can be operations such as equals, less than. The value means an integer value to compare.
 * The index means the column index of an entry, the column value at the index will be used. If the index
 * is negative, then this condition will be applied to all the columns.
 * </p>
 * 
 * <p>
 * Thread Safety : This class is immutable and so is thread safe.
 * </p>
 * 
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7e49]
 */
public class IntFilterCondition implements FilterCondition {

/**
 * <p>
 * This variable specifies what operation is used in the filter operation.
 * </p>
 * 
 * <p>
 * For this condition, only OP_EQUALS, OP_GREATER, OP_LESS and OP_NOTEQUALS are accepted.
 * For other unknown conditions that accept integer type, the isValid() method will return false.
 * </p>
 * 
 * <p>
 * Initial Value: It is immutable and set in constructor
 * </p>
 * <p>
 * Accessed In: getFilterListOperator
 * </p>
 * <p>
 * Modified In: None
 * </p>
 * <p>
 * Utilized In: isValid
 * </p>
 * <p>
 * Valid Values: Non-null FilterListOperator
 * </p>
 * 
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7af7]
 */
    private final FilterListOperator operator;

/**
 * <p>
 * Represents the integer value to be compared with the corresponding column value of an entry.
 * </p>
 * 
 * <p>
 * Initial Value: It is immutable and set in constructor
 * </p>
 * <p>
 * Accessed In: getValue
 * </p>
 * <p>
 * Modified In: None
 * </p>
 * <p>
 * Utilized In: isValid
 * </p>
 * <p>
 * Valid Values: any integer value
 * </p>
 * 
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7af2]
 */
    private final int value;

/**
 * <p>
 * Represents the index of the column in an entry to be compared.
 * </p>
 * 
 * <p>
 * If the column index is negative, then it means all the columns will be used to be compared.
 * </p>
 * 
 * <p>
 * Initial Value: It is immutable and set in constructor
 * </p>
 * 
 * <p>
 * Accessed In: getColumnIndex
 * </p>
 * 
 * <p>
 * Modified In: None
 * </p>
 * 
 * <p>
 * Utilized In: isValid
 * </p>
 * 
 * <p>
 * Valid Values: any integer value that is less than the column size of an entity
 * </p>
 * 
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7aed]
 */
    private final int columnIndex;

/**
 * <p>
 * Constructor with operator, value and column index given.
 * </p>
 * 
 * <p>
 * Implementation Note:
 * check the arguments;
 * get all the allowed types of the operator via operator#getSupportedTypes, check whether int.class is one
 * of the supported types, if not, InvalidConditionException should be thrown.
 * this.operator = operator;
 * this.value = value;
 * this.columnIndex = columnIndex;
 * </p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7ae4]
 * @param operator the filter operation
 * @param value the value to be compared with corresponding column value
 * @param columnIndex the index of column to be compared
 * @throws IllegalArgumentException if operator is null
 * @throws InvalidConditionException if the operator cannot be applied to integer type
 */
    public  IntFilterCondition(FilterListOperator operator, int value, int columnIndex) {        
        this.operator = operator;
        this.value = value;
        this.columnIndex = columnIndex;
    } 

/**
 * <p>
 * This method is used to determine whether the given entry satisfies this condition.
 * It is used for integer columns.
 * </p>
 * 
 * <p>
 * If it satisfied, true will be returned, otherwise it will return false.
 * </p>
 * 
 * <p>
 * This implementations will not modify the given entry. The validation result is returned instead
 * of updating the entry visibility attribute. It is the duty of user to update the entry instance.
 * </p>
 * 
 * <p>
 * Note, if the column index is negative, then all the columns of an entry will be used.
 * </p>
 * 
 * <p>
 * Implementation Note:
 * if (columnIndex less than 0) {
 * for (int i = 0; i less than entry.getColumnSize(); i++) {
 * if (!isValid(entry.getColumn(i))) {
 * return false;
 * }
 * }
 * 
 * return true;
 * } else {
 * return isValid(entry.getColumn(columnIndex));
 * }
 * </p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7ad8]
 * @return true if the entry satisfies this condition, false otherwise
 * @param entry the entry to validate
 * @throws IllegalArgumentException if entry is null
 * @throws IndexOutOfBoundsException if the index &gt;= column size of an entry.
 * @throws InvalidDataTypeException if the column of the entry to be compared cannot be converted an integer
 */
    public boolean isValid(FilterListEntry entry) {        
        // your code here
        return false;
    } 

/**
 * <p>
 * Retrieves the filer operation.
 * </p>
 * 
 * <p>
 * Implementation Note:
 * return this.operator;
 * </p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7acd]
 * @return the filer operation.
 */
    public FilterListOperator getFilterListOperator() {        
        // your code here
        return null;
    } 

/**
 * <p>
 * Retrieves the index of column to be used in the filtering.
 * </p>
 * 
 * <p>
 * Implementation Note:
 * return this.columnIndex;
 * </p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7ac6]
 * @return the index of column to be used in the filtering.
 */
    public int getColumnIndex() {        
        // your code here
        return 0;
    } 

/**
 * <p>
 * Retrieves the integer value to be compared with the corresponding column value of an entry.
 * </p>
 * 
 * <p>
 * Implementation Note:
 * return this.value;
 * </p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7abf]
 * @return the integer value to be compared with the corresponding column value of an entry.
 */
    public int getValue() {        
        // your code here
        return 0;
    } 

/**
 * <p>Validates the column value is valid against this condition.</p>
 * <p>Implementation Note: try { int columnValue = Integer.parseInt(column); if (operator == FilterListOperator.OP_EQUALS) { return columnValue == value; } else if (operator == FilterListOperator.OP_GREATER) { return columnValue &gt; value; } else if (operator == FilterListOperator.OP_LESS) { return columnValue &lt; value; } else if (operator == FilterListOperator.OP_NOTEQUALS) { return columnValue != value; } else { return false; } } catch (NumberFormatException e) { throw new InvalidDataTypeException(&quot;...&quot;, e); }</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7ab7]
 * @return true if the value is valid according to the condition, false otherwise
 * @param column the value of a column to compare
 * @throws InvalidDataTypeException if the given column value cannot be converted to an integer
 */
    private boolean isValid(String column) {        
        // your code here
        return false;
    } 
 }
