
package com.topcoder.mobile.filterlistscreen;

/**
 * <p>This class defines some constants for sort types.</p>
 * <p>The sort types constants as below:</p>
 * <ul>
 * <li>INTEGER - This means the sorting is done on integer columns</li>
 * <li>STRING - This means the sorting is done on string columns</li>
 * </ul>
 * <p>In this design, each sort type is defined as SortType, each SortType has a name.</p>
 * <p>Thread Safety : This class is thread safety since it is immutable.</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7e45]
 */
public class SortType {

/**
 * <p>Represents the sorting for integer columns.</p>
 * <p>It is static and immutable, it can be treated as an constant.</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7b89]
 */
    public static final SortType INTEGER = new SortType("integer");

/**
 * <p>Represents the sorting for string columns.</p>
 * <p>It is static and immutable, it can be treated as an constant.</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7b83]
 */
    public static final SortType STRING = new SortType("string");

/**
 * <p>This is the name of the sort type.</p>
 * <p>Initial Value: Set in the constructor and immutable</p>
 * <p>Accessed In: getName</p>
 * <p>Modified In: None</p>
 * <p>Valid Values: Strings that are non-null and non-empty</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7b7d]
 */
    private final String name;

/**
 * <p>Constructor with name given.</p>
 * <p>This is private to prevent instances of this class being created outside of the class.</p>
 * <p>Implementation Note: check the argument; this.name = name;</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7b76]
 * @param name the name of the sort type
 * @throws IllegalArgumentException if name is null or empty
 */
    private  SortType(String name) {        
       this.name = name;
    } 

/**
 * <p>Retrieves the name of the filter operation.</p>
 * <p>Implementation Note: return name;</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7b6e]
 * @return the name of the filter operation.
 */
    public String getName() {        
        // your code here
        return name;
    } 
 }
