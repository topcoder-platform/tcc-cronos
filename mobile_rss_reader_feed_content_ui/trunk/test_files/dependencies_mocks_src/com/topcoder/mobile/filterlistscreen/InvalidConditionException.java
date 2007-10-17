
package com.topcoder.mobile.filterlistscreen;

/**
 * <p>This exception is thrown when a problem occurs while the condition is applies to a column with an inapposite type.</p>
 * <p>For example, if user wants to apply the OP_CONTAINS filtering operation to an integer column, this exception should be thrown.</p>
 * <p>Thread Safety : This class doesn't contains attributes and its super class is thread safety so it is thread safety.</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7e52]
 */
public class InvalidConditionException extends FilterListException {

/**
 * <p>Represents the invalid filter condition that causes this exception.</p>
 * <p>It is set in the constrcutor and will never be null.</p>
 * 
 * @poseidon-object-id [I68e43c59m11402866dcemm594f]
 */
    private final com.topcoder.mobile.filterlistscreen.FilterCondition condition;

/**
 * <p>Constructs a InvalidConditionException with the message and invaid condition given.</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7baf]
 * @param message The message of the exception.
 * @param condition The invalid filter condition that causes this exception.
 */
    public  InvalidConditionException(String message, com.topcoder.mobile.filterlistscreen.FilterCondition condition) {        
        super(message);
        this.condition = condition;
    } 

/**
 * <p>Returns the invalid FilterCondition that causes this exception.</p>
 * 
 * @poseidon-object-id [Im1d7ece3am1140810d810mm5cbf]
 * @return The invalid FilterCondition that causes this exception.
 */
    public com.topcoder.mobile.filterlistscreen.FilterCondition getFilterCondition() {        
        // your code here
        return null;
    } 
 }
