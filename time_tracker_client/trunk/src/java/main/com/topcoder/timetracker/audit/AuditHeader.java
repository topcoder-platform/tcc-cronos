package com.topcoder.timetracker.audit;

/**
 * AuditHeader is a simple bean class for the storage of information about changes to details in the Time Tracker
 * application. It stores its unique ID, as well as multiple id values for items it is related to, as well as creation
 * data, linked names, the area of the application that it is related to, the table of information being changed, the
 * action performed, and the values changed. As a bean class, it provides a no-argument constructor, as well as
 * setXXX/getXXX methods named for each member. By design, this class performs no error checking on parameters in
 * setXXX methods, it is left to the handling application to define what constraints are put on the bean's members.
 * Additionally, there is no thread-safety protection placed on the members of this bean - however, in the absense of
 * static members, it is safe to use in its intended web environment.
 */
public class AuditHeader {
    /** The unique ID for the audit header. Initially -1, any valid long value is allowed. */
    private long id = -1;

    /** Represents the ID of the entity this audit is related to. Initially -1, any long is allowed. */
    private long entitiyId = -1;

    /**
     * Represents the Timestamp of when the audit record was created. Initially null, any Timestamp or null is allowed.
     */
    private java.sql.Timestamp creationDate = null;

    /** Represents the name of the table this audit record is for. Initially null, any String value or null is allowed */
    private String tableName = null;

    /** Represents the ID of the company this audit is related to. Initially -1, any long is allowed. */
    private long companyId = -1;

    /**
     * Represents the name of the user who created this audit record. Initially null, any String value or null is
     * allowed.
     */
    private String creationUser = null;

    /**
     * Represents the action taken when this audit record was created. Initially null, any String value or null is
     * allowed.
     */
    private int actionType = -1;

    /** Represents the ID of the client this audit is related to. Initially -1, any long is allowed. */
    private long clientId = -1;

    /** Represents the ID of the project this audit is related to. Initially -1, any long is allowed. */
    private long projectId = -1;

    /** Represents the ID of the resource this audit is related to. Initially -1, any long is allowed. */
    private long resourceId = -1;

    /**
     * Represents the name of the client who this audit record is for - when loaded this is the name linked to the
     * clientId. Initially null, any String value or null is allowed
     */
    private String clientName = null;

    /**
     * Represents the name of the project who this audit record is for - when loaded this is the name linked to the
     * projectId. Initially null, any String value or null is allowed
     */
    private String projectName = null;

    /**
     * Represents all details connected to this audit record. This can be an array of any number (0....) of audit
     * details or null. Note that by design the details are set in bulk - individual details cannot be added or
     * removed.
     */
    private AuditDetail[] details = null;

    /**
     * Represents the application area this audit was placed from. This is initially null, and any valid
     * ApplicationArea enumeration value is allowed, or null.
     */
    private ApplicationArea applicationArea = null;

    /**
     * Flag indicating whether this header has been successfully persisted. This is initially false, set to true when
     * persisted or loaded from persistence.
     */
    private boolean persisted = false;

    /**
     * No-arg constructor for the Audit Header bean.
     */
    public AuditHeader() {
        // your code here
    }

    /**
     * Returns the unique ID for the audit header.
     *
     * @return this.id
     */
    public long getId() {
        // your code here
        return 0;
    }

    /**
     * Sets the ID to use for the header.
     *
     * @param id the new ID to use
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the id of the entity this header is related to.
     *
     * @return this.entityId
     */
    public long getEntityId() {
        return this.entitiyId;
    }

    /**
     * Sets the Entity ID to use
     *
     * @param id New value to use for entityId
     */
    public void setEntityId(long id) {
        this.entitiyId = id;
    }

    /**
     * Returns the creation date of the audit record
     *
     * @return this.creationDate
     */
    public java.sql.Timestamp getCreationDate() {
        return this.creationDate;
    }

    /**
     * Changes the creation date to a new timestamp.
     *
     * @param date The new creation timestamp to use.
     */
    public void setCreationDate(java.sql.Timestamp date) {
        this.creationDate = date;
    }

    /**
     * Returns the name of the table the audit had recorded a change to.
     *
     * @return this.tableName;
     */
    public String getTableName() {
        return this.tableName;
    }

    /**
     * Sets the name of the table the audit is recording a change to.
     *
     * @param name The new name of the table to use
     */
    public void setTableName(String name) {
        this.tableName = name;
    }

    /**
     * Returns the company ID related to this audit header.
     *
     * @return this.companyId;
     */
    public long getCompanyId() {
        return this.companyId;
    }

    /**
     * Sets the company Id to relate to the header
     *
     * @param id new ID to use
     */
    public void setCompanyId(long id) {
        this.companyId = id;
    }

    /**
     * Sets the name of the user who created this audit record
     *
     * @return
     */
    public String getCreationUser() {
        return this.creationUser;
    }

    /**
     * Sets the name for the user who created the audit record.
     *
     * @param user The new user name to use.
     */
    public void setCreationUser(String user) {
        this.creationUser = user;
    }

    /**
     * Returns the type of action that was performed
     *
     * @return return this.actionType
     */
    public int getActionType() {
        return this.actionType;
    }

    /**
     * sets the type of action that was used
     *
     * @param type the new type to use
     */
    public void setActionType(int type) {
        this.actionType = type;
    }

    /**
     * Returns the client ID related to this audit header.
     *
     * @return this.clientId;
     */
    public long getClientId() {
        return this.clientId;
    }

    /**
     * Sets the client Id to relate to the header
     *
     * @param id new ID to use
     */
    public void setClientId(long id) {
        this.clientId = id;
    }

    /**
     * Returns the project ID related to this audit header.
     *
     * @return this.projectId;
     */
    public long getProjectId() {
        return this.projectId;
    }

    /**
     * Sets the project Id to relate to the header
     *
     * @param id new ID to use
     */
    public void setProjectId(long id) {
        this.projectId = id;
    }

    /**
     * Returns the resource ID related to this audit header.
     *
     * @return this.resourceId;
     */
    public long getResourceId() {
        return this.resourceId;
    }

    /**
     * Sets the resource Id to relate to the header
     *
     * @param id new ID to use
     */
    public void setResourceId(long id) {
        this.resourceId = id;
    }

    /**
     * Returns the name of the client who caused this audit to be made.
     *
     * @return this.clientName
     */
    public String getClientName() {
        return this.clientName;
    }

    /**
     * Changes the name of the client who caused this audit to be made.
     *
     * @param name new name to use
     */
    public void setClientName(String name) {
        this.clientName = name;
    }

    /**
     * Returns the name of the project in which the change occurred causing the audit to be made.
     *
     * @return this.projectName
     */
    public String getProjectName() {
        return this.projectName;
    }

    /**
     * Changes the name of the project in which the change occurred causing the audit to be made.
     *
     * @param name the new name to use
     */
    public void setProjectName(String name) {
        this.projectName = name;
    }

    /**
     * Returns the array of all details connected to this audit header.
     *
     * @return this.details;
     */
    public AuditDetail[] getDetails() {
        return this.details;
    }

    /**
     * Sets the collection of details to associate to the audit header.
     *
     * @param detail
     */
    public void setDetails(AuditDetail[] detail) {
        this.details = detail;
    }

    /**
     * Returns the area of the application this audit comes from.
     *
     * @return
     */
    public ApplicationArea getApplicationArea() {
        return this.applicationArea;
    }

    /**
     * Sets the area of the application that this audit is to be from
     *
     * @param area The new area the audit is from.
     */
    public void setApplicationArea(ApplicationArea area) {
        this.applicationArea = area;
    }

    /**
     * Returns whether or not the header has been persisted.
     *
     * @return this.persisted;
     */
    public boolean isPersisted() {
        return this.persisted;
    }

    /**
     * Sets the persistence status of the header.
     *
     * @param persisted The new value to use for the flag
     */
    public void setPersisted(boolean persisted) {
        this.persisted = persisted;
    }
}
