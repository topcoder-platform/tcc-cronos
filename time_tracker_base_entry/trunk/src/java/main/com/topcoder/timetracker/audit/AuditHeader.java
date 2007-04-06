
package com.topcoder.timetracker.audit;

/**
 * AuditHeader is a simple bean class for the storage of information about changes to details in the Time Tracker application. It stores its unique ID, as well as multiple id values for items it is related to, as well as creation data, linked names, the area of the application that it is related to, the table of information being changed, the action performed, and the values changed. As a bean class, it provides a no-argument constructor, as well as setXXX/getXXX methods named for each member. By design, this class performs no error checking on parameters in setXXX methods, it is left to the handling application to define what constraints are put on the bean's members. Additionally, there is no thread-safety protection placed on the members of this bean - however, in the absense of static members, it is safe to use in its intended web environment.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7d4b]
 */
public class AuditHeader {

/**
 * The unique ID for the audit header. Initially -1, any valid long value is allowed.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7f31]
 */
    private int id = -1;

/**
 * Represents the ID of the entity this audit is related to. Initially -1, any long is allowed.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7f2b]
 */
    private long entitiyId = -1;

/**
 * Represents the Timestemp of when the audit record was created. Initially null, any Timestamp or null is allowed.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7f25]
 */
    private java.sql.Timestamp creationDate = null;

/**
 * Represents the name of the table this audit record is for. Initially null, any String value or null is allowed
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7f1f]
 */
    private String tableName = null;

/**
 * Represents the ID of the company this audit is related to. Initially -1, any long is allowed.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7f19]
 */
    private long companyId = -1;

/**
 * Represents the name of the user who created this audit record. Initially null, any String value or null is allowed.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7f13]
 */
    private String creationUser = null;

/**
 * Represents the action taken when this audit record was created. Initially null, any String value or null is allowed.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7f0d]
 */
    private int actionType;

/**
 * Represents the ID of the client this audit is related to. Initially -1, any long is allowed.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7f07]
 */
    private long clientId = -1;

/**
 * Represents the ID of the project this audit is related to. Initially -1, any long is allowed.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7f01]
 */
    private long projectId = -1;

/**
 * Represents the ID of the resource this audit is related to. Initially -1, any long is allowed.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7efb]
 */
    private long resourceId = -1;

/**
 * Represents the name of the client who this audit record is for - when loaded this is the name linked to the clientId. Initially null, any String value or null is allowed
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7ef5]
 */
    private String clientName = null;

/**
 * Represents the name of the project who this audit record is for - when loaded this is the name linked to the projectId. Initially null, any String value or null is allowed
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7eef]
 */
    private String projectName = null;

/**
 * Represents all details connected to this audit record. This can be an array of any number (0....*) of audit details or null. Note that by design the details are set in bulk - individual details cannot be added or removed.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7ee9]
 */
    private AuditDetail[] details = null;

/**
 * Represents the application area this audit was placed from. This is initially null, and any valid ApplicationArea enumeration value is allowed, or null.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7edb]
 */
    private ApplicationArea applicationArea = null;

/**
 * Flag indicating whether this header has been successfully persisted. This is initially false, set to true when persisted or loaded from persistence.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm2363]
 */
    private boolean persisted = false;

/**
 * No-arg constructor for the Audit Header bean.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7ee5]
 */
    public  AuditHeader() {        
        // your code here
    } 

/**
 * Returns the unique ID for the audit header.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7ed3]
 * @return this.id
 */
    public int getId() {        
        return id;
    } 

/**
 * Sets the ID to use for the header.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7ec3]
 * @param id the new ID to use
 */
    public void setId(int id) {        
        this.id = id;
    } 

/**
 * Returns the id of the entity this header is related to.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7eb7]
 * @return this.entityId
 */
    public long getEntityId() {        
        // your code here
        return entitiyId;
    } 

/**
 * Sets the Entity ID to use
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7ea7]
 * @param id New value to use for entityId
 */
    public void setEntityId(long id) {        
        this.entitiyId = id;
    } 

/**
 * Returns the creation date of the audit record
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7e9b]
 * @return this.creationDate
 */
    public java.sql.Timestamp getCreationDate() {        
        return creationDate;
    } 

/**
 * Changes the creation date to a new timestamp.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7e8b]
 * @param date The new creation timestamp to use.
 */
    public void setCreationDate(java.sql.Timestamp date) {        
        this.creationDate = date;
    } 

/**
 * Returns the name of the table the audit had recorded a change to.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7e7f]
 * @return this.tableName;
 */
    public String getTableName() {        
        // your code here
        return tableName;
    } 

/**
 * Sets the name of the table the audit is recording a change to.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7e6f]
 * @param name The new name of the table to use
 */
    public void setTableName(String name) {        
        this.tableName = name;
    } 

/**
 * Returns the company ID related to this audit header.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7e63]
 * @return this.companyId;
 */
    public long getCompanyId() {        
        return companyId;
    } 

/**
 * Sets the company Id to relate to the header
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7e53]
 * @param id new ID to use
 */
    public void setCompanyId(long id) {        
        this.companyId = id;
    } 

/**
 * Sets the name of the user who created this audit record
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7e45]
 * @return 
 */
    public String getCreationUser() {        
        return this.creationUser;
    } 

/**
 * Sets the name for the user who created the audit record.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7e35]
 * @param user The new user name to use.
 */
    public void setCreationUser(String user) {        
        this.creationUser = user;
    } 

/**
 * Returns the type of action that was performed
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7e29]
 * @return return this.actionType
 */
    public int getActionType() {        
        return actionType;
    } 

/**
 * sets the type of action that was used
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7e19]
 * @param type the new type to use
 */
    public void setActionType(int type) {        
        this.actionType = type;
    } 

/**
 * Returns the client ID related to this audit header.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7e0d]
 * @return this.clientId;
 */
    public long getClientId() {        
        // your code here
        return 0;
    } 

/**
 * Sets the client Id to relate to the header
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7dfd]
 * @param id new ID to use
 */
    public void setClientId(long id) {        
        // your code here
    } 

/**
 * Returns the project ID related to this audit header.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7df1]
 * @return this.projectId;
 */
    public long getProjectId() {        
        // your code here
        return 0;
    } 

/**
 * Sets the project Id to relate to the header
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7de1]
 * @param id new ID to use
 */
    public void setProjectId(long id) {        
        // your code here
    } 

/**
 * Returns the resource ID related to this audit header.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7dd5]
 * @return this.resourceId;
 */
    public long getResourceId() {        
        // your code here
        return 0;
    } 

/**
 * Sets the resource Id to relate to the header
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7dc5]
 * @param id new ID to use
 */
    public void setResourceId(long id) {        
        // your code here
    } 

/**
 * Returns the name of the client who caused this audit to be made.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7db9]
 * @return this.clientName
 */
    public String getClientName() {        
        // your code here
        return null;
    } 

/**
 * Changes the name of the client who caused this audit to be made.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7da9]
 * @param name new name to use
 */
    public void setClientName(String name) {        
        // your code here
    } 

/**
 * Returns the name of the project in which the change occurred causing the audit to be made.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7d9d]
 * @return this.projectName
 */
    public String getProjectName() {        
        // your code here
        return null;
    } 

/**
 * Changes the name of the project in which the change occurred causing the audit to be made.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7d8d]
 * @param name the new name to use
 */
    public void setProjectName(String name) {        
        // your code here
    } 

/**
 * Returns the array of all details connected to this audit header.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7d81]
 * @return this.details;
 */
    public AuditDetail[] getDetails() {        
        return details;
    } 

/**
 * Sets the collection of details to associate to the audit header.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7d6f]
 * @param detail 
 */
    public void setDetails(AuditDetail[] details) {        
        this.details = details;
    } 

/**
 * Returns the area of the application this audit comes from.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7d61]
 * @return 
 */
    public ApplicationArea getApplicationArea() {        
        return this.applicationArea;
    } 

/**
 * Sets the area of the application that this audit is to be from
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7d51]
 * @param area The new area the audit is from.
 */
    public void setApplicationArea(ApplicationArea area) {        
        this.applicationArea = area;
    } 

/**
 * Returns whether or not the header has been persisted.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm2344]
 * @return this.persisted;
 */
    public boolean isPersisted() {        
        // your code here
        return false;
    } 

/**
 * Sets the persistence status of the header.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm231f]
 * @param persisted The new value to use for the flag
 */
    public void setPersisted(boolean persisted) {        
        // your code here
    } 
 }
