/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit;

import java.io.Serializable;

import java.sql.Timestamp;


/**
 * <p>
 * This class is a simple bean class for the storage of information about changes to details in the Time Tracker
 * application. It stores its unique ID, as well as multiple id values for items it is related to, as well as creation
 * data, linked names, the area of the application that it is related to, the table of information being changed, the
 * action performed, and the values changed.
 * </p>
 *
 * <p>
 * As a bean class, it provides a no-argument constructor, as well as setXXX/getXXX methods named for each member. This
 * class performs no error checking on parameters in setXXX methods, it is left to the handling application to define
 * what constraints are put on the bean's members.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>There is no thread-safety protection placed on the members of this bean - however, in the
 * absense of static members, it is safe to use in its intended web environment.
 * </p>
 *
 * @author sql_lall, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class AuditHeader implements Serializable {
    /**
     * <p>
     * Represents the identification number for this audit header.
     * </p>
     *
     * <p>
     * Initially -1, any valid long value is allowed.
     * </p>
     */
    private long id = -1;

    /**
     * <p>
     * Represents the ID of the entity this audit is related to.
     * </p>
     *
     * <p>
     * Initially -1, any valid long value is allowed.
     * </p>
     */
    private long entityId = -1;

    /**
     * <p>
     * Represents the Timestamp of when the audit record was created.
     * </p>
     *
     * <p>
     * Initially null, any Timestamp or null is allowed.
     * </p>
     */
    private Timestamp creationDate = null;

    /**
     * <p>
     * Represents the name of the table this audit record is for.
     * </p>
     *
     * <p>
     * Initially null, any String value or null is allowed.
     * </p>
     */
    private String tableName = null;

    /**
     * <p>
     * Represents the ID of the company this audit is related to.
     * </p>
     *
     * <p>
     * Initially -1, any valid long value is allowed.
     * </p>
     */
    private long companyId = -1;

    /**
     * <p>
     * Represents the name of the user who created this audit record.
     * </p>
     *
     * <p>
     * Initially null, any String value or null is allowed.
     * </p>
     */
    private String creationUser = null;

    /**
     * <p>
     * Represents the action type id taken when this audit record was created.
     * </p>
     *
     * <p>
     * Initially -1, any valid int value is allowed.
     * </p>
     */
    private int actionType = -1;

    /**
     * <p>
     * Represents the ID of the client this audit is related to.
     * </p>
     *
     * <p>
     * Initially -1, any valid long value is allowed.
     * </p>
     */
    private long clientId = -1;

    /**
     * <p>
     * Represents the ID of the project this audit is related to.
     * </p>
     *
     * <p>
     * Initially -1, any valid long value is allowed.
     * </p>
     */
    private long projectId = -1;

    /**
     * <p>
     * Represents the ID of the resource this audit is related to.
     * </p>
     *
     * <p>
     * Initially -1, any valid long value is allowed.
     * </p>
     */
    private long resourceId = -1;

    /**
     * <p>
     * Represents the name of the client who this audit record is for - when loaded this is the name linked to the
     * clientId.
     * </p>
     *
     * <p>
     * Initially null, any String value or null is allowed.
     * </p>
     */
    private String clientName = null;

    /**
     * <p>
     * Represents the name of the project who this audit record is for - when loaded this is the name linked to the
     * clientId.
     * </p>
     *
     * <p>
     * Initially null, any String value or null is allowed.
     * </p>
     */
    private String projectName = null;

    /**
     * <p>
     * Represents all details connected to this audit record.
     * </p>
     *
     * <p>
     * Initially null, this can be an array of any number (0....) of audit details or null. Note that by design the
     * details are set in bulk - individual details cannot be added or removed.
     * </p>
     */
    private AuditDetail[] details = null;

    /**
     * <p>
     * Represents the application area this audit was placed from.
     * </p>
     *
     * <p>
     * This is initially null, and any valid ApplicationArea enumeration value is allowed, or null.
     * </p>
     */
    private ApplicationArea applicationArea = null;

    /**
     * <p>
     * Represents the flag indicating whether this header has been successfully persisted.
     * </p>
     *
     * <p>
     * This is initially false, set to true when persisted or loaded from persistence.
     * </p>
     */
    private boolean persisted = false;

    /**
     * <p>
     * No-arg constructor for the Audit Header bean.
     * </p>
     */
    public AuditHeader() {
    }

    /**
     * <p>
     * Gets the identification number for this audit header.
     * </p>
     *
     * @return the identification number for this audit header.
     */
    public long getId() {
        return this.id;
    }

    /**
     * <p>
     * Sets the identification number for this audit header.
     * </p>
     *
     * @param id the identification number for this audit header.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>
     * Gets the identification number for this audit header.
     * </p>
     *
     * @return the ID of the entity this audit is related to.
     */
    public long getEntityId() {
        return this.entityId;
    }

    /**
     * <p>
     * Sets the identification number for this audit header.
     * </p>
     *
     * @param id the ID of the entity this audit is related to.
     */
    public void setEntityId(long id) {
        this.entityId = id;
    }

    /**
     * <p>
     * Gets the Timestamp of when the audit record was created.
     * </p>
     *
     * @return the Timestamp of when the audit record was created.
     */
    public Timestamp getCreationDate() {
        return this.creationDate;
    }

    /**
     * <p>
     * Sets the Timestamp of when the audit record was created.
     * </p>
     *
     * @param date the Timestamp of when the audit record was created.
     */
    public void setCreationDate(Timestamp date) {
        this.creationDate = date;
    }

    /**
     * <p>
     * Gets the name of the table this audit record is for.
     * </p>
     *
     * @return the name of the table this audit record is for.
     */
    public String getTableName() {
        return this.tableName;
    }

    /**
     * <p>
     * Sets the name of the table this audit record is for.
     * </p>
     *
     * @param name the name of the table this audit record is for.
     */
    public void setTableName(String name) {
        this.tableName = name;
    }

    /**
     * <p>
     * Gets the ID of the company this audit is related to.
     * </p>
     *
     * @return the ID of the company this audit is related to.
     */
    public long getCompanyId() {
        return this.companyId;
    }

    /**
     * <p>
     * Sets the ID of the company this audit is related to.
     * </p>
     *
     * @param id the ID of the company this audit is related to.
     */
    public void setCompanyId(long id) {
        this.companyId = id;
    }

    /**
     * <p>
     * Gets the name of the user who created this audit record.
     * </p>
     *
     * @return the name of the user who created this audit record.
     */
    public String getCreationUser() {
        return this.creationUser;
    }

    /**
     * <p>
     * Sets the name of the user who created this audit record.
     * </p>
     *
     * @param user the name of the user who created this audit record.
     */
    public void setCreationUser(String user) {
        this.creationUser = user;
    }

    /**
     * <p>
     * Gets the action type id taken when this audit record was created.
     * </p>
     *
     * @return the action type id taken when this audit record was created.
     */
    public int getActionType() {
        return this.actionType;
    }

    /**
     * <p>
     * Sets the action type id taken when this audit record was created.
     * </p>
     *
     * @param actionType the action type id taken when this audit record was created.
     */
    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    /**
     * <p>
     * Gets the ID of the client this audit is related to.
     * </p>
     *
     * @return the ID of the client this audit is related to.
     */
    public long getClientId() {
        return this.clientId;
    }

    /**
     * <p>
     * Sets the ID of the client this audit is related to.
     * </p>
     *
     * @param id the ID of the client this audit is related to.
     */
    public void setClientId(long id) {
        this.clientId = id;
    }

    /**
     * <p>
     * Gets the ID of the project this audit is related to.
     * </p>
     *
     * @return the ID of the project this audit is related to.
     */
    public long getProjectId() {
        return this.projectId;
    }

    /**
     * <p>
     * Sets the ID of the project this audit is related to.
     * </p>
     *
     * @param id the ID of the project this audit is related to.
     */
    public void setProjectId(long id) {
        this.projectId = id;
    }

    /**
     * <p>
     * Gets the ID of the resource this audit is related to.
     * </p>
     *
     * @return the ID of the resource this audit is related to.
     */
    public long getResourceId() {
        return this.resourceId;
    }

    /**
     * <p>
     * Sets the ID of the resource this audit is related to.
     * </p>
     *
     * @param id the ID of the resource this audit is related to.
     */
    public void setResourceId(long id) {
        this.resourceId = id;
    }

    /**
     * <p>
     * Gets the name of the client who this audit record is for.
     * </p>
     *
     * @return the name of the client who this audit record is for.
     */
    public String getClientName() {
        return this.clientName;
    }

    /**
     * <p>
     * Sets the name of the client who this audit record is for.
     * </p>
     *
     * @param name the name of the client who this audit record is for.
     */
    public void setClientName(String name) {
        this.clientName = name;
    }

    /**
     * <p>
     * Gets the name of the project who this audit record is for.
     * </p>
     *
     * @return the name of the project who this audit record is for.
     */
    public String getProjectName() {
        return this.projectName;
    }

    /**
     * <p>
     * Sets the name of the project who this audit record is for.
     * </p>
     *
     * @param name the name of the project who this audit record is for.
     */
    public void setProjectName(String name) {
        this.projectName = name;
    }

    /**
     * <p>
     * Gets all details connected to this audit record.
     * </p>
     *
     * @return all details connected to this audit record.
     */
    public AuditDetail[] getDetails() {
        return this.details;
    }

    /**
     * <p>
     * Sets all details connected to this audit record.
     * </p>
     *
     * @param details all details connected to this audit record.
     */
    public void setDetails(AuditDetail[] details) {
        this.details = details;
    }

    /**
     * <p>
     * Gets the application area this audit was placed from.
     * </p>
     *
     * @return the application area this audit was placed from.
     */
    public ApplicationArea getApplicationArea() {
        return this.applicationArea;
    }

    /**
     * <p>
     * Sets the application area this audit was placed from.
     * </p>
     *
     * @param area the application area this audit was placed from.
     */
    public void setApplicationArea(ApplicationArea area) {
        this.applicationArea = area;
    }

    /**
     * <p>
     * Gets the flag indicating whether this header has been successfully persisted.
     * </p>
     *
     * @return whether this header has been successfully persisted.
     */
    public boolean isPersisted() {
        return this.persisted;
    }

    /**
     * <p>
     * Sets the flag indicating whether this header has been successfully persisted.
     * </p>
     *
     * @param persisted whether this header has been successfully persisted.
     */
    public void setPersisted(boolean persisted) {
        this.persisted = persisted;
    }
}
