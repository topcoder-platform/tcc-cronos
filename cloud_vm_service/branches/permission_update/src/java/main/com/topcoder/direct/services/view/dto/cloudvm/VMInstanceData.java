/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.cloudvm;

import java.io.Serializable;

/**
 * This class represents the VM instance data. It contains the vm instance, status, and VM Manager's handle.
 *
 * Thread-safety: Mutable and not thread-safe.
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
public class VMInstanceData implements Serializable {
    /**
     * Represents the vm instance status. It has getter & setter. It can be any value.
     */
    private VMInstanceStatus status;

    /**
     * Represents the VM instance. It has getter & setter. It can be any value.
     */
    private VMInstance instance;

    /**
     * Represents the VM manager's handle. It has getter & setter. It can be any value.
     */
    private String managerHandle;
    
    /**
     * Stores contest name for readability.
     * @since BUGR-3232
     */
    private String contestName;
    
    /**
     * Stores tc image name for readability.
     * @since BUGR-3232
     */
    private String vmImageTcName;
    
    /**
     * Empty constructor.
     */
    public VMInstanceData() {
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public VMInstanceStatus getStatus() {
        return status;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param status value to set
     */
    public void setStatus(VMInstanceStatus status) {
        this.status = status;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public VMInstance getInstance() {
        return instance;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param instance value to set
     */
    public void setInstance(VMInstance instance) {
        this.instance = instance;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public String getManagerHandle() {
        return managerHandle;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param managerHandle value to set
     */
    public void setManagerHandle(String managerHandle) {
        this.managerHandle = managerHandle;
    }
   
    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     * @since BUGR-3932
     */
    public String getContestName() {
        return contestName;
    }
    
    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param managerHandle value to set
     * @since BUGR-3932
     */
    public void setContestName(String contestName) {
        this.contestName = contestName;
    }
    
    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     * @since BUGR-3932
     */
    public String getVmImageTcName() {
        return vmImageTcName;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param managerHandle value to set
     * @since BUGR-3932
     */
    public void setVmImageTcName(String vmImageTcName) {
        this.vmImageTcName = vmImageTcName;
    }
    
    
}

