
package com.topcoder.timetracker.common;
import java.io.Serializable;
import java.util.Date;

/**
 * <p><strong>Usage:</strong> Bean base class used to represent all entities of the Time Tracker Application, which it persisted in to the database. It contains all the properties which are common to these entities (id, creation date, modification date, creation user, modification user and changed flag). It is also Serializable to support network transfer, and state persistence.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li><strong>implements</strong> Serializable.</li>
 * </ul>
 * <p><strong>Thread Safety:</strong> This class is mutable, and not thread-safe. Multiple threads are advised to work with their own instance.</p>
 * 
 */
public abstract class TimeTrackerBean implements Serializable {

/**
 * <p><strong>Usage: </strong>Represents the date when this entity was initially defined within the Time Tracker data store. It may be null when a blank bean is defined for initial data population, but it must always have a value when the entiy is retrieved from the data store. Initialized in: setCreationDate, modified in: setCreationDate, accessed in: getCreationDate.</p>
 * <p><strong>Valid values:</strong> can not be set to null.</p>
 * 
 */
    private Date creationDate = null;

/**
 * <p><strong>Usage: </strong>Represents the date when any of the fields of this entity was modified within Time Tracker data store. It may be null when a blank bean is defined for initial data population, but it must always have a value when the entiy is retrieved from the data store. If the entity has not been modified since its initial definition, then the modificationDate would be equal to creationDate. Initialized in: setModificationDate, modified in: setModificationDate, accessed in: getModificationDate.</p>
 * <p><strong>Valid values:</strong> can not be set to null.</p>
 * 
 */
    private Date modificationDate = null;

/**
 * <p><strong>Usage: </strong>Represents the user that initially defined this entity within the Time Tracker data store. It may be null when a blank bean is defined for initial data population, but it must always have a value when the entiy is retrieved from the data store. Initialized in: setCreationUser, modified in: setCreationUser, accessed in: getCreationUser.</p>
 * <p><strong>Valid values:</strong> can not be set to null.</p>
 * 
 */
    private String creationUser = null;

/**
 * <p><strong>Usage: </strong>Represents the user that modified this entity within the Time Tracker data store. It may be null when a blank bean is defined for initial data population, but it must always have a value when the entiy is retrieved from the data store. If the entity has not been modified since its initial definition, then the modificationUser would be equal to creationUser. Initialized in: setModificationUser, modified in: setModificationUser, accessed in: getModificationUser.</p>
 * <p><strong>Valid values:</strong> can not be set to null or empty string.</p>
 * 
 */
    private String modificationUser = null;

/**
 * <p><strong>Usage: </strong>Represent a unique identifier that distinguishes each entity within the Time Tracker application. May be -1 to notifi that is not set already. An id is automatically assigned by the manager of this component when the entity is initially placed into the data store. When the entity has not yet been placed in the data store, then it will not have/need an id. Initialized in: setId, modified in: setId, accessed in: getId.</p>
 * <p><strong>Valid values:</strong> can not be &lt;=0.</p>
 * 
 */
    private long id = -1;

/**
 * <p><strong>Usage: </strong>Represent the flag used by application to determine if the objects values have changed. Most in this application it will end up being set to true given the nature of the application. This variable represents whether any of the additional bean data has changed. This variable is used to assist in determining whether the modification date and modification user for this bean needs to be modified during persistence. Only implementors of the DAO, or subclasses of TimeTrackerBean need to concern themselves with this. Initialized in: declaration, modified in: setChanged, accessed in: isChanged.</p>
 * 
 */
    private boolean changed = true;

/**
 * <p><strong>Usage: </strong>Default constructor.</p>
 * <p><strong>Implementation notes: </strong></p>
 * <ul type="disc">
 * <li>does nothing.</li>
 * </ul>
 * 
 */
    protected  TimeTrackerBean() {        
        // empty
    } 

/**
 * <p><strong>Usage: </strong>Retrieves the date when this entity was initially defined within the Time Tracker data store. It may be null when a blank bean is defined for initial data population, but it must always have a value when the entiy is retrieved from the data store.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li><em>return this.creationDate;</em></li>
 * </ul>
 * 
 * 
 * @return the date when this entity was initially defined within the Time Tracker data store.
 */
    public Date getCreationDate() {        
        return creationDate;
    } 

/**
 * <p><strong>Usage:</strong> Sets the date when this entity was initially defined within the Time Tracker data store. It may be null when a blank bean is defined for initial data population, but it must always have a value when the entiy is retrieved from the data store.</p>
 * <p>Note that it is not recommended for this method to be called by normal code. This method is likely to be called only by the Time Tracker DAO and Manager classes, and isn't something that the user of this component should concern themselves with. Manager should check the attribute and should throw IllegalArgumentException if creationDate is null.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li>
 * <p><em>this.creationDate = creationDate;</em></p>
 * </li>
 * </ul>
 * 
 * 
 * @param creationDate the date when this entity was initially defined within the Time Tracker data store.
 */
    public void setCreationDate(Date creationDate) {        
        if (creationDate == null) {
            throw new IllegalArgumentException("creationDate cannot be null.");
        }
        this.creationDate = creationDate;
    } 

/**
 * <p><strong>Usage: </strong>Retrieves the date when any of the fields of this entity was modified within Time Tracker data store. It may be null when a blank bean is defined for initial data population, but it must always have a value when the entiy is retrieved from the data store. If the entity has not been modified since its initial definition, then the modificationDate would be equal to creationDate.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li><em>return this.modificationDate;</em></li>
 * </ul>
 * 
 * 
 * @return the date when any of the fields of this entity was modified within Time Tracker data store.
 */
    public Date getModificationDate() {        
        return modificationDate;
    } 

/**
 * <p><strong>Usage: </strong>Sets the date when any of the fields of this entity was modified within Time Tracker data store. It may be null when a blank bean is defined for initial data population, but it must always have a value when the entiy is retrieved from the data store. If the entity has not been modified since its initial definition, then the modificationDate would be equal to creationDate.</p>
 * <p>Note that it is not recommended for this method to be called by normal code. This method is likely to be called only by the Time Tracker DAO and Manager classes, and isn't something that the user of this component should concern themselves with. Manager should check the attribute and should throw IllegalArgumentException if modificationDate is null.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li><em>this.modificationDate = modificationDate;</em></li>
 * </ul>
 * 
 * 
 * @param modificationDate the date when any of the fields of this entity was modified within Time Tracker data
 * store.
 */
    public void setModificationDate(Date modificationDate) {        
        if (modificationDate == null) {
            throw new IllegalArgumentException("modificationDate cannot be null.");
        }
        this.modificationDate = modificationDate;
    } 

/**
 * <p><strong>Usage: </strong>Retrieves the user that initially defined this entity within the Time Tracker data store. It may be null when a blank bean is defined for initial data population, but it must always have a value when the entiy is retrieved from the data store.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li><em>return this.creationUser;</em></li>
 * </ul>
 * <p></p>
 * 
 * 
 * @return the user that initially defined this entity within the Time Tracker data store.
 */
    public String getCreationUser() {        
        return creationUser;
    } 

/**
 * <p><strong>Usage: </strong>This represents the user that initially defined this entity within the Time Tracker data store. It may be null when a blank bean is defined for initial data population, but it must always have a value when the entiy is retrieved from the data store.</p>
 * <p>Note that it is not recommended for this method to be called by normal code. This method is likely to be called only by the Time Tracker DAO and Manager classes, and isn't something that the user of this component should concern themselves with. Manager should check the attribute and should throw IllegalArgumentException if creationUser is null or empty.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li>
 * <p><em>this.creationUser = creationUser;</em></p>
 * </li>
 * </ul>
 * 
 * 
 * @param creationUser the user that initially defined this entity within the Time Tracker data store.
 */
    public void setCreationUser(String creationUser) {        
        if (creationUser == null) {
            throw new IllegalArgumentException("creationUser cannot be null.");
        }
        this.creationUser = creationUser;
    } 

/**
 * <p><strong>Usage: </strong>Retrieves the user that most recently modified this entity within the Time Tracker data store. It may be null when a blank bean is defined for initial data population, but it must always have a value when the entiy is retrieved from the data store. If the entity has not been modified since its initial definition, then the modificationUser would be equal to creationUser.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li><em>return this.modificationUser;</em></li>
 * </ul>
 * 
 * 
 * @return the user that most recently modified this entity within the Time Tracker data store.
 */
    public String getModificationUser() {        
        return modificationUser;
    } 

/**
 * <p><strong>Usage: </strong>Sets the user that most recently modified this entity within the Time Tracker data store. It may be null when a blank bean is defined for initial data population, but it must always have a value when the entiy is retrieved from the data store. If the entity has not been modified since its initial definition, then the modificationUser would be equal to creationUser.</p>
 * <p>Note that it is not recommended for this method to be called by normal code. This method is likely to be called only by the Time Tracker DAO and Manager classes, and isn't something that the user of this component should concern themselves with. Manager should check the attribute and should throw IllegalArgumentException if modificationUser is null or empty.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li>
 * <p><em>this.modificationUser = modificationUser;</em></p>
 * </li>
 * </ul>
 * 
 * 
 * @param modificationUser the user that most recently modified this entity within the Time Tracker data store.
 */
    public void setModificationUser(String modificationUser) {        
        if (modificationUser == null) {
            throw new IllegalArgumentException("modificationUser cannot be null.");
        }
        this.modificationUser = modificationUser;
    } 

/**
 * <p><strong>Usage:</strong> Retrieves the unique identifier that distinguishes each entity within the Time Tracker application.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li><em>return this.id;</em></li>
 * </ul>
 * 
 * 
 * @return the unique identifier that distinguishes each entity within the Time Tracker application
 */
    public long getId() {        
        return id;
    } 

/**
 * <p><strong>Usage: </strong>Sets the unique identifier that distinguishes each entity within the Time Tracker application.</p>
 * <p>Note that it is not recommended for this method to be called by normal code. This method is likely to be called only by the Time Tracker DAO and Manager classes, and isn't something that the user of this component should concern themselves with. Manager should check the attribute and should throw IllegalArgumentException if id&lt;=0.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li><em>this.id = id;</em></li>
 * </ul>
 * 
 * 
 * @param id the unique identifier that distinguishes each entity within the Time Tracker application
 */
    public void setId(long id) {        
        if (id <= 0) {
            throw new IllegalArgumentException("The id must be positive.");
        }
        this.id = id;
    } 

/**
 * <p><strong>Usage: </strong>Checks whether any of the additional bean data has changed.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li><em>return this.changed;</em></li>
 * </ul>
 * 
 * 
 * @return whether any of the additional bean data has changed.
 */
    public boolean isChanged() {        
        return changed;
    } 

/**
 * <p><strong>Usage: </strong>Sets whether any of the additional bean data has changed, and needs the modificationDate and user to be updated or not. This variable is used to assist in determining whether the modification date and modification user for this bean needs to be modified during persistence. Only implementors of the DAO, or subclasses of TimeTrackerBean need to concern themselves with this.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li><em>this.changed = changed;</em></li>
 * </ul>
 * 
 * 
 * @param changed true if the bean is changed; false otherwise
 */
    public void setChanged(boolean changed) {        
        this.changed = changed;
    } 

/**
 * <p><strong>Usage: </strong>Indicates whether this bean is equal to the specified object. They are considered equal if the other object is also a TimeTrackerBean and they have identical ids.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li><em>if (!(obj instanceof TimeTrackerBean)) {</em></li>
 * <li><em>&nbsp;&nbsp;&nbsp; return false;</em></li>
 * <li><em>}</em></li>
 * <li><em>return id == ((TimeTrackerBean) o).id;</em></li>
 * </ul>
 * 
 * 
 * @return True if the 2 objects are equal. False if the other object is a null or is otherwise not considered
 * equal.
 * @param obj The other object to compare to.
 */
    public boolean equals(Object obj) {        
        if (!(obj instanceof TimeTrackerBean)) {
            return false;
        }
        return id == ((TimeTrackerBean) obj).id;
    } 

/**
 * <p><strong>Usage: </strong>Returns a hashCode based on the id of this bean.</p>
 * <p><strong>Implementation Notes: </strong></p>
 * <p>An exclusive OR of the 2 halves of this long may be one way of generating a good hashcode. (See java.lang.Long#hash.Code())</p>
 * <ul type="disc">
 * <li><em>return (int)(id ^ (id &gt;&gt;&gt; 32));</em></li>
 * </ul>
 * 
 * 
 * @return A hashcode based on the id of this bean.
 */
    public int hashCode() {        
        return (int)(id ^ (id >>> 32));
    } 
 }
