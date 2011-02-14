/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.cloudvm;

/**
 * This class represents the audit record when launching or terminating the VM instance.
 *
 * Thread-safety: Mutable and not thread-safe.
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
public class VMInstanceAudit extends AbstractIdEntity {
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 104320439457983L;

    /**
     * Represents the VM instance id. It has getter & setter. It can be any value.
     */
    private long instanceId;

    /**
     * Represents the action It has getter & setter. It can be any value.
     */
    private String action;

    /**
     * Empty constructor.
     */
    public VMInstanceAudit() {
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public long getInstanceId() {
        return instanceId;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param instanceId value to set
     */
    public void setInstanceId(long instanceId) {
        this.instanceId = instanceId;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public String getAction() {
        return action;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param action value to set
     */
    public void setAction(String action) {
        this.action = action;
    }
}

