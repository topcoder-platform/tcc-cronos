/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.cloudvm;

/**
 * This class represents the VM instance for the Amazon EC2. It contains the Amazon instance id, instance status,
 * associated image id, tc member handle, SVN branch, contest id and contest type.
 *
 * Thread-safety: Mutable and not thread-safe.
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
public class VMInstance extends AbstractIdEntity {
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 5743291495081523L;

    /**
     * Represents the Amazon instance id. It has getter & setter. It can be any value.
     */
    private String awsInstanceId;

    /**
     * Represents the image reference id. It has getter & setter. It can be any value.
     */
    private long vmImageId;

    /**
     * Represents the tc member handle. It has getter & setter. It can be any value.
     */
    private String tcMemberHandle;

    /**
     * Represents the SVN branch. It has getter & setter. It can be any value.
     */
    private String svnBranch;

    /**
     * Represents the contest id. It has getter & setter. It can be any value.
     */
    private long contestId;

    /**
     * Represents the contest name. It has getter & setter. It can be any value.
     */
    private String contestName;

    /**
     * Represents the contest type id. It has getter & setter. It can be any value.
     */
    private long contestTypeId;

    /**
     * Represents the VM account id. It has getter & setter. It can be any value.
     */
    private long accountId;
    
    /**
     * Represents VM public IP address.
     * @since BUGR-3932
     */
    private String publicIP;

    /**
     * Is set to true if VM is terminated
     * @since BUGR-3930
     */
    private boolean terminated;
    
    /**
     * Represents user data properties in the form:
     *  key1=value1 
     *  key2=value2
     *  
     *  @since BUGR-3931 
     */
    private String userData;
    
    /**
     * Empty constructor.
     */
    public VMInstance() {
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public String getAwsInstanceId() {
        return awsInstanceId;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param awsInstanceId value to set
     */
    public void setAwsInstanceId(String awsInstanceId) {
        this.awsInstanceId = awsInstanceId;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public long getVmImageId() {
        return vmImageId;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param vmImageId value to set
     */
    public void setVmImageId(long vmImageId) {
        this.vmImageId = vmImageId;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param contestId value to set
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public String getContestName() {
        return contestName;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param contestId value to set
     */
    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public String getTcMemberHandle() {
        return tcMemberHandle;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param tcMemberHandle value to set
     */
    public void setTcMemberHandle(String tcMemberHandle) {
        this.tcMemberHandle = tcMemberHandle;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public String getSvnBranch() {
        return svnBranch;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param svnBranch value to set
     */
    public void setSvnBranch(String svnBranch) {
        this.svnBranch = svnBranch;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public long getContestTypeId() {
        return contestTypeId;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param contestTypeId value to set
     */
    public void setContestTypeId(long contestTypeId) {
        this.contestTypeId = contestTypeId;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public long getAccountId() {
        return accountId;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param accountId value to set
     */
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
    
    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     * @since BUGR-3930
     */
    public boolean isTerminated() {
        return terminated;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param terminated value to set
     * @since BUGR-3930
     */
    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }
    
    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     * @since BUGR-3931
     */
    public String getUserData() {
        return userData;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param userData value to set
     * @since BUGR-3931
     */
    public void setUserData(String userData) {
        this.userData = userData;
    }
    
    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     * @since BUGR-3932
     */
    public String getPublicIP() {
        return publicIP;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param accountId value to set
     * @since BUGR-3932
     */
    public void setPublicIP(String publicIP) {
        this.publicIP = publicIP;
    }
}
