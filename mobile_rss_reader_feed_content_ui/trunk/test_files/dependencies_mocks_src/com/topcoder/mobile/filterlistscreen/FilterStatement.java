
package com.topcoder.mobile.filterlistscreen;
/**
 * <p>This interface extends FilterCondition and acts as composite conditions to filter entries.</p>
 * <p>FilterCondition is used to define single filter while FilterStatement is used to define multiple conditions either <strong>ANDed</strong>, <strong>ORed</strong> or <strong>NOTed</strong>.</p>
 * <p>Besides the method defined in FilterCondition, this method provides another method to get the underlying FilterCondition instances aggregated by this filter statement. Note, underlying FilterCondition instances can be of FilterStatement type, which means mix ANDed, ORed and NOTed operations are supported.</p>
 * <p>Thread Safety : Implementations of this interface are not required to be thread safe.</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7e42]
 */
public interface FilterStatement extends FilterCondition {
/**
 * <p>Retrieves the underlying FilterCondition instance aggregated by this filter statement.</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7bb9]
 * @return the underlying FilterCondition instance aggregated by this filter statement.
 */
    public FilterCondition[] getAggregatedConditions();
}


