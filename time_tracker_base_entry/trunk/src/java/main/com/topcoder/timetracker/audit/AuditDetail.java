
package com.topcoder.timetracker.audit;

/**
 * AuditDetail is a simple bean class for the storage of information about a single column change. It stores its unique ID, as well as the name of the column which was changed, and the value in the column before and after the change. As a bean class, it provides a no-argument constructor, as well as setXXX/getXXX methods named for each member. By design, this class performs no error checking on parameters in setXXX methods, it is left to the handling application to define what constraints are put on the bean's members. Additionally, there is no thread-safety protection placed on the members of this bean - however, in the absense of static members, it is safe to use in its intended web environment.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7cb7]
 */
public class AuditDetail {

/**
 * The identification number for this detail. Initially -1, any valid long value is allowed.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7d43]
 */
    private long id = -1;

/**
 * The new value stored on the detail. Initially null, any String or null is allowed.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7d3d]
 */
    private String newValue = null;

/**
 * The old value stored within the detail. Initially null, any String or null is allowed.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7d37]
 */
    private String oldValue = null;

/**
 * The name of the column that changed from the old to the new Value. Initially null, any String or null is allowed.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7d31]
 */
    private String columnName = null;

/**
 * Flag indicating whether this detail has been successfully persisted. This is initially false, set to true when persisted or loaded from persistence.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm22db]
 */
    private boolean persisted = false;

/**
 * No-arg constructor for the Audit Details bean.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7d2d]
 */
    public  AuditDetail() {        
        // your code here
    } 

/**
 * Returns the current ID of the audit detail.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7d21]
 * @return this.id;
 */
    public long getID() {        
        return id;
    } 

/**
 * Sets the ID of the audit detail.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7d11]
 * @param id the new id to use
 */
    public void setID(long id) {        
        this.id = id;
    } 

/**
 * Returns the new String value for the detail.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7d05]
 * @return this.newValue;
 */
    public String getNewValue() {        
        return this.newValue;
    } 

/**
 * Sets the new value to the parameter.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7cf5]
 * @param value The new value to use
 */
    public void setNewValue(String value) {        
        this.newValue = value;
    } 

/**
 * Returns the old String value for the detail.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7ce9]
 * @return this.oldValue
 */
    public String getOldValue() {        
        return this.oldValue;
    } 

/**
 * Sets the old value to the parameter.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7cd9]
 * @param value the value to use for oldValue.
 */
    public void setOldValue(String value) {        
        this.oldValue = value;
    } 

/**
 * Returns the name of the column the detail relates to
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7ccd]
 * @return this.columnName
 */
    public String getColumnName() {        

        return this.columnName;
    } 

/**
 * Sets the column name to the parameter.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7cbd]
 * @param name The new column name to use
 */
    public void setColumnName(String name) {        
        this.columnName = name;
    } 

/**
 * Returns whether or not the header has been persisted.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm22c4]
 * @return this.ispersisted;
 */
    public boolean isPersisted() {        
        // your code here
        return false;
    } 

/**
 * Sets the persistence status of the header.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm229f]
 * @param persisted The new value to use for the flag
 */
    public void setPersisted(boolean persisted) {        
        // your code here
    } 
 }
