/*
 * Copyright (C) 2010,2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.cloudvm.service;

import com.topcoder.direct.services.view.dto.cloudvm.VMUsage;
import com.topcoder.direct.services.view.dto.cloudvm.VMContestType;
import com.topcoder.direct.services.view.dto.cloudvm.VMImage;
import com.topcoder.direct.services.view.dto.cloudvm.VMInstance;
import com.topcoder.direct.services.view.dto.cloudvm.VMInstanceData;
import com.topcoder.direct.services.view.dto.cloudvm.VMInstanceStatus;
import com.topcoder.security.TCSubject;

import java.util.List;

/**
 * This interface defines the contract to launch VM instance, terminate VM instance, get VM instances, get VM images and
 * get VM contest types. It will exposed as web service.
 *
 * Thread-safety: handled by the container.
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
public interface CloudVMService {
    /**
     * Launch VM instance.
     *
     * @param tcSubject the currently logged-in user info.
     * @param instance  the VM instance to launch.
     * @return the launched VM instance.
     * @throws IllegalArgumentException if any argument is null.
     * @throws CloudVMServiceException  if any error occurs.
     */
    public List<VMInstanceData> launchVMInstance(TCSubject tcSubject, VMInstance instance) throws CloudVMServiceException;

    /**
     * Terminate VM instance.
     *
     * @param tcSubject  the currently logged-in user info.
     * @param instanceId the VM instance to terminate.
     * @return the changed instance status
     * @throws IllegalArgumentException if the tcSubject argument is null.
     * @throws CloudVMServiceException  if any error occurs.
     */
    public VMInstanceStatus terminateVMInstance(TCSubject tcSubject, long instanceId) throws CloudVMServiceException;

    /**
     * Get VM instances for the given user.
     *
     * @param tcSubject the currently logged-in user info.
     * @return the vm instances.
     * @throws IllegalArgumentException if the tcSubject argument is null.
     * @throws CloudVMServiceException  if any error occurs.
     */
    public List<VMInstanceData> getVMInstances(TCSubject tcSubject) throws CloudVMServiceException;

    /**
     * Get VM images.
     *
     * @param tcSubject the currently logged-in user info.
     * @return the vm images.
     * @throws IllegalArgumentException if the tcSubject argument is null.
     * @throws CloudVMServiceException  if any error occurs.
     */
    public List<VMImage> getVMImages(TCSubject tcSubject) throws CloudVMServiceException;

    /**
     * Get VM contest types.
     *
     * @param tcSubject the currently logged-in user info.
     * @return the vm contest types.
     * @throws IllegalArgumentException if the tcSubject argument is null.
     * @throws CloudVMServiceException  if any error occurs.
     */
    public List<VMContestType> getVMContestTypes(TCSubject tcSubject) throws CloudVMServiceException;

    /**
     * Get VM usages.
     *
     * @param tcSubject the currently logged-in user info.
     * @return the vm usages.
     * @throws IllegalArgumentException if the tcSubject argument is null.
     * @throws CloudVMServiceException  if any error occurs.
     */
    public List<VMUsage> getVMUsages(TCSubject tcSubject) throws CloudVMServiceException;
}

