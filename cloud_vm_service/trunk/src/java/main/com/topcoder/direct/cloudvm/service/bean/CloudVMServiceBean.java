/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.cloudvm.service.bean;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.AvailabilityZone;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceState;
import com.amazonaws.services.ec2.model.Placement;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.amazonaws.services.ec2.model.TerminateInstancesResult;
import com.topcoder.direct.cloudvm.service.CloudVMServiceException;
import com.topcoder.direct.cloudvm.service.CloudVMServiceLocal;
import com.topcoder.direct.cloudvm.service.CloudVMServiceRemote;
import com.topcoder.direct.services.view.dto.cloudvm.VMAccount;
import com.topcoder.direct.services.view.dto.cloudvm.VMAvailabilityZone;
import com.topcoder.direct.services.view.dto.cloudvm.VMContestType;
import com.topcoder.direct.services.view.dto.cloudvm.VMImage;
import com.topcoder.direct.services.view.dto.cloudvm.VMInstance;
import com.topcoder.direct.services.view.dto.cloudvm.VMInstanceAudit;
import com.topcoder.direct.services.view.dto.cloudvm.VMInstanceData;
import com.topcoder.direct.services.view.dto.cloudvm.VMInstanceStatus;
import com.topcoder.direct.services.view.dto.cloudvm.VMUserData;
import com.topcoder.security.RolePrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.service.user.UserService;
import com.topcoder.service.user.UserServiceException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import org.apache.commons.codec.binary.Base64;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class implements the CloudVMServiceLocal and CloudVMServiceRemote interfaces. And it's implemented as a
 * stateless session bean. It will use the Amazon WebService SDK for Java to launch or terminate instances. The basic
 * Amazon image and instance data is stored in local database, but we need to get the Amazon instance status from the
 * web service. It will also log the invocation details.
 *
 * Thread-safety: Immutable and thread-safe.
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@DeclareRoles({"Administrator", "VMManager"})
@RolesAllowed({"Administrator", "VMManager"})
public class CloudVMServiceBean implements CloudVMServiceRemote, CloudVMServiceLocal {

    /**
     * Represents the sessionContext of the EJB.
     */
    @Resource
    private SessionContext sessionContext;

    /**
     * The default VM image id to store the default VM image configuration. Can be any value. Will be injected by the
     * container.
     */
    @Resource(name = "defaultVMImageId")
    private long defaultVMImageId;

    /**
     * The from email address. Will be injected by the container. Must be non-null, non-empty string.
     */
    @Resource(name = "fromEmailAddress")
    private String fromEmailAddress;

    /**
     * The encryption algorithm name. Will be injected by the container. Must be non-null, non-empty string.
     */
    @Resource(name = "encryptionAlgorithmName")
    private String encryptionAlgorithmName;

    /**
     * Persistence unit name. Will be injected by the container. Must be non-null, non-empty string.
     */
    @Resource(name = "unitName")
    private String unitName;

    /**
     * The user service to retrieve the user email. Will be injected by the container. Must be non-null.
     */
    @EJB(name = "ejb/UserService")
    private UserService userService;

    /**
     * The logger to log invocation details. Initialized in constructor and never changed afterwards. Must be non-null.
     */
    private final Log logger;

    /**
     * Constructor.
     */
    public CloudVMServiceBean() {
        logger = LogManager.getLog(this.getClass().getName());
    }

    /**
     * Launch VM instance.
     *
     * @param tcSubject  the currently logged-in user info.
     * @param vmInstance the VM instance to launch.
     * @return the launched VM instance.
     * @throws IllegalArgumentException if any argument is null.
     * @throws CloudVMServiceException  if any error occurs (wrap the underlying exceptions).
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public VMInstanceData launchVMInstance(TCSubject tcSubject, VMInstance vmInstance) throws CloudVMServiceException {
        logEnter("launchVMInstance");
        checkNull("tcSubject", tcSubject);
        checkNull("vmInstance", vmInstance);
        try {
            EntityManager entityManager = getEntityManager();

            verifySecurityKey(entityManager, vmInstance.getTcMemberHandle());

            VMAccount vmAccount = getVMAccount(tcSubject.getUserId());
            AmazonEC2Client client = new AmazonEC2Client(
                new BasicAWSCredentials(vmAccount.getAwsAccessKeyId(), vmAccount.getAwsSecurityAccessKey()));
            VMImage vmImage = getVMImage(vmInstance.getVmImageId());
            if (vmImage == null) {
                throw new CloudVMServiceException("No vm image with id " + vmInstance.getVmImageId());
            }
            VMImage defaultImage = getVMImage(defaultVMImageId);

            // create and populate request
            RunInstancesRequest request = new RunInstancesRequest();
            if (vmImage.getSecurityGroup() == null) {
                request.setSecurityGroups(Arrays.asList(defaultImage.getSecurityGroup().getName()));
            } else {
                request.setSecurityGroups(Arrays.asList(vmImage.getSecurityGroup().getName()));
            }

            // process time zones
            VMAvailabilityZone requestZone = vmImage.getAvailabilityZone() == null ? defaultImage.getAvailabilityZone()
                : vmImage.getAvailabilityZone();
            Set<String> availableZones = new HashSet<String>();
            for (AvailabilityZone zone : client.describeAvailabilityZones().getAvailabilityZones()) {
                availableZones.add(zone.getZoneName());
            }
            if (availableZones.contains(requestZone.getName())) {
                request.setPlacement(new Placement().withAvailabilityZone(requestZone.getName()));
            } else {
                request.setPlacement(new Placement().withAvailabilityZone(availableZones.iterator().next()));
            }

            request.setInstanceType(vmImage.getInstanceType() == null ? defaultImage.getInstanceType().getName()
                : vmImage.getInstanceType().getName());
            request.setKeyName(
                vmImage.getKeyPair() == null ? defaultImage.getKeyPair().getName() : vmImage.getKeyPair().getName());

            // process user data
            StringBuilder userData = new StringBuilder();
            for (VMUserData data : vmImage.getUserData()) {
                userData.append(data.getKey()).append("=").append(data.getValue()).append("\n");
            }
            userData.append("email=").append(userService.getEmailAddress(tcSubject.getUserId())).append("\n");
            userData.append("branch=").append(vmInstance.getSvnBranch()).append("\n");
            userData.append("handle=").append(vmInstance.getTcMemberHandle()).append("\n");
            request.setUserData(new String(Base64.encodeBase64(userData.toString().getBytes())));

            request.setImageId(vmImage.getAwsImageId());
            request.setMinCount(1);
            request.setMaxCount(1);

            // send request
            RunInstancesResult result = client.runInstances(request);
            Instance instance = result.getReservation().getInstances().get(0);

            vmInstance.setAwsInstanceId(instance.getInstanceId());
            vmInstance.setAccountId(vmAccount.getId());
            vmInstance = entityManager.merge(vmInstance);

            // create audit record
            VMInstanceAudit audit = new VMInstanceAudit();
            audit.setAction("launched");
            audit.setInstanceId(vmInstance.getId());
            audit.setCreateDate(new Date());
            entityManager.merge(audit);

            // create and return DTO object 
            VMInstanceData data = new VMInstanceData();
            data.setInstance(vmInstance);
            data.setStatus(convertState(instance.getState()));
            return data;

        } catch (UserServiceException e) {
            throw logError(new CloudVMServiceException("Unable to fetch user.", e));
        } catch (Exception e) {
            throw logError(
                new CloudVMServiceException("Unable to launch vm image with id " + vmInstance.getVmImageId(), e));
        } finally {
            logExit("launchVMInstance");
        }
    }

    /**
     * Verifies that given user has security key in place.
     *
     * @param entityManager entity manager to access database
     * @param tcMemberHandle user to verify
     * @throws CloudVMServiceException if verification failed
     */
    private void verifySecurityKey(EntityManager entityManager, String tcMemberHandle) throws CloudVMServiceException {
        Query query = entityManager.createNativeQuery("select count(security_key) from user_security_key where user_id=(select u.user_id from user u where handle = ?)");
        query.setParameter(1, tcMemberHandle);
        Object result = query.getSingleResult().toString();
        if (Integer.parseInt(result.toString()) == 0) {
            throw new CloudVMServiceException("User [" + tcMemberHandle + "] doesn't have security key in place.");
        }
    }

    /**
     * Terminate VM instance.
     *
     * @param tcSubject  the currently logged-in user info.
     * @param instanceId the VM instance to terminate.
     * @return the changed instance status
     * @throws IllegalArgumentException if the tcSubject argument is null.
     * @throws CloudVMServiceException  if any error occurs.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public VMInstanceStatus terminateVMInstance(TCSubject tcSubject, long instanceId) throws CloudVMServiceException {
        logEnter("terminateVMInstance");
        checkNull("tcSubject", tcSubject);
        try {
            EntityManager entityManager = getEntityManager();

            // create and populate request
            VMAccount vmAccount = getVMAccount(tcSubject.getUserId());
            AmazonEC2Client client = new AmazonEC2Client(
                new BasicAWSCredentials(vmAccount.getAwsAccessKeyId(), vmAccount.getAwsSecurityAccessKey()));
            VMInstance vmInstance = entityManager.find(VMInstance.class, instanceId);
            TerminateInstancesRequest request =
                new TerminateInstancesRequest().withInstanceIds(vmInstance.getAwsInstanceId());

            // send request
            TerminateInstancesResult result = client.terminateInstances(request);

            // create audit record
            VMInstanceAudit audit = new VMInstanceAudit();
            audit.setAction("terminated");
            audit.setInstanceId(vmInstance.getId());
            audit.setCreateDate(new Date());
            entityManager.merge(audit);

            // return new vm state
            return convertState(result.getTerminatingInstances().get(0).getCurrentState());
        } catch (Exception e) {
            throw logError(
                new CloudVMServiceException("Unable to terminate vm instance with id" + instanceId + ".", e));
        } finally {
            logExit("terminateVMInstance");
        }
    }

    /**
     * Converts amazon vm InstanceState to topcoder VMInstanceStatus.
     *
     * @param state value to convert
     * @return conversion result
     * @throws CloudVMServiceException if conversion fails
     */
    private VMInstanceStatus convertState(InstanceState state) throws CloudVMServiceException {
        if (state.getName().equalsIgnoreCase("pending")) {
            return VMInstanceStatus.PENDING;
        } else if (state.getName().equalsIgnoreCase("running")) {
            return VMInstanceStatus.RUNNING;
        } else if (state.getName().equalsIgnoreCase("shutting-down")) {
            return VMInstanceStatus.SHUTTING_DOWN;
        } else if (state.getName().equalsIgnoreCase("terminated")) {
            return VMInstanceStatus.TERMINATED;
        } else {
            throw logError(new CloudVMServiceException("Unable to convert state [" + state.getName() + "]"));
        }
    }

    /**
     * Get VM instances for the given user.
     *
     * @param tcSubject the currently logged-in user info.
     * @return the vm instances.
     * @throws IllegalArgumentException if the tcSubject argument is null.
     * @throws CloudVMServiceException  if any error occurs.
     */
    public List<VMInstanceData> getVMInstances(TCSubject tcSubject) throws CloudVMServiceException {
        logEnter("getVMInstances");
        checkNull("tcSubject", tcSubject);
        try {
            EntityManager entityManager = getEntityManager();
            VMAccount vmAccount = getVMAccount(tcSubject.getUserId());

            // make db query
            Query q;
            if (!inRole(tcSubject, "Administrator")) {
                q = entityManager.createQuery("select a from VMInstance a where a.accountId=:accountId order by a.contestId desc");
                q.setParameter("accountId", vmAccount.getId());
            } else {
                q = entityManager.createQuery("select a from VMInstance a order by a.contestId desc");
            }
            List<VMInstance> instances = q.getResultList();

            // populate result with data from db
            AmazonEC2Client client = new AmazonEC2Client(
                new BasicAWSCredentials(vmAccount.getAwsAccessKeyId(), vmAccount.getAwsSecurityAccessKey()));

            // map to reference by instance id, list to preserve order
            List<VMInstanceData> data = new ArrayList<VMInstanceData>();
            Map<String, VMInstanceData> dataMap = new HashMap<String, VMInstanceData>();

            // create VMInstanceData objects
            for (VMInstance instance : instances) {
                VMInstanceData instanceData = new VMInstanceData();
                instanceData.setInstance(instance);
                data.add(instanceData);
                dataMap.put(instance.getAwsInstanceId(), instanceData);
                instanceData.setStatus(VMInstanceStatus.TERMINATED);
            }

            // make remote call and fetch information about vm statuses
            DescribeInstancesResult result =
                client.describeInstances(new DescribeInstancesRequest().withInstanceIds(dataMap.keySet()));
            for (Reservation reservation : result.getReservations()) {
                for (Instance instance : reservation.getInstances()) {
                    if (dataMap.get(instance.getInstanceId()) != null) {
                        dataMap.get(instance.getInstanceId()).setStatus(convertState(instance.getState()));
                    }
                }
            }

            Collection<VMInstanceData> vm_data = new ArrayList<VMInstanceData>(data);
            for (VMInstanceData instanceData : vm_data) {
                if (instanceData.getStatus() == VMInstanceStatus.TERMINATED) {
                     data.remove(instanceData);
                }
            }
	
            // remove terminated instances from the database
            /*
            for (VMInstanceData instanceData : data) {
                if (instanceData.getStatus() == VMInstanceStatus.TERMINATED) {
                     entityManager.remove(instanceData.getInstance());
                }
            }
            */

            // for administrators, fetch vm managers also
            if (inRole(tcSubject, "Administrator")) {
                for (VMInstanceData instanceData : data) {
                    VMAccount account = entityManager.find(VMAccount.class, instanceData.getInstance().getAccountId());
                    instanceData.setManagerHandle(userService.getUserHandle(account.getUserId()));
                }
            }

            return new ArrayList<VMInstanceData>(data);
        } catch (UserServiceException e) {
            throw logError(new CloudVMServiceException("Unable to fetch user handle.", e));
        } catch (Exception e) {
            throw logError(new CloudVMServiceException("Unable to fetch vm instances.", e));
        } finally {
            logExit("getVMInstances");
        }
    }

    /**
     * Get VM images.
     *
     * @param tcSubject the currently logged-in user info.
     * @return the vm images.
     * @throws IllegalArgumentException if the tcSubject argument is null.
     * @throws CloudVMServiceException  if any error occurs.
     */
    public List<VMImage> getVMImages(TCSubject tcSubject) throws CloudVMServiceException {
        logEnter("getVMImages");
        checkNull("tcSubject", tcSubject);
        try {
            EntityManager entityManager = getEntityManager();
            return entityManager.createQuery("select i from VMImage i ").getResultList();
        } catch (Exception e) {
            throw logError(new CloudVMServiceException("Unable to fetch vm vm images.", e));
        } finally {
            logExit("getVMImages");
        }
    }

    /**
     * Get VM contest types.
     *
     * @param tcSubject the currently logged-in user info.
     * @return the vm contest types.
     * @throws IllegalArgumentException if the tcSubject argument is null.
     * @throws CloudVMServiceException  if any error occurs.
     */
    public List<VMContestType> getVMContestTypes(TCSubject tcSubject) throws CloudVMServiceException {
        logEnter("getVMContestTypes");
        checkNull("tcSubject", tcSubject);
        try {
            EntityManager entityManager = getEntityManager();
            return entityManager.createQuery("select c from VMContestType c ").getResultList();
        } catch (Exception e) {
            throw logError(new CloudVMServiceException("Unable to fetch vm contest types.", e));
        } finally {
            logExit("getVMContestTypes");
        }
    }

    /**
     * Get VM account by user id.
     *
     * @param userId the user id.
     * @return the vm account.
     * @throws CloudVMServiceException if any error occurs.
     */
    private VMAccount getVMAccount(long userId) throws CloudVMServiceException {
        try {
            EntityManager entityManager = getEntityManager();
            Query q = entityManager.createQuery("select a from VMAccount a where a.userId=:userId");
            q.setParameter("userId", userId);
            VMAccount result = (VMAccount) q.getSingleResult();
            if (result == null) {
                throw logError(new CloudVMServiceException("No VM account for user with id [" + userId + "]"));
            }
            return result;
        } catch (Exception e) {
            throw logError(new CloudVMServiceException("Unable to fetch vm accounts.", e));
        }
    }

    /**
     * Get VM image by id.
     *
     * @param vmImageId the VM image id.
     * @return the vm image.
     * @throws CloudVMServiceException if any error occurs.
     */
    private VMImage getVMImage(long vmImageId) throws CloudVMServiceException {
        EntityManager entityManager = getEntityManager();
        return entityManager.find(VMImage.class, vmImageId);
    }


    /**
     * Checks if the user has given role.
     *
     * @param tcSubject TCSubject instance for user
     * @param roleName  role name
     * @return whether user has such role
     */
    private static boolean inRole(TCSubject tcSubject, String roleName) {
        Set<RolePrincipal> roles = tcSubject.getPrincipals();
        if (roles != null) {
            for (RolePrincipal role : roles) {
                if (role.getName().equalsIgnoreCase(roleName)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * <p> Returns the <code>EntityManager</code> looked up from the session context. </p>
     *
     * @return the EntityManager looked up from the session context
     * @throws CloudVMServiceException if fail to get the EntityManager from the sessionContext.
     */
    private EntityManager getEntityManager() throws CloudVMServiceException {
        try {
            Object obj = sessionContext.lookup(unitName);

            if (obj == null) {
                throw logError(new CloudVMServiceException("No entity manager."));
            }

            return (EntityManager) obj;
        } catch (ClassCastException e) {
            throw logError(new CloudVMServiceException("No entity manager.", e));
        }
    }

    /**
     * Checks method parameter for null value.
     *
     * @param name parameter name
     * @param data parameter value
     * @throws IllegalArgumentException if data is null
     */
    private static void checkNull(String name, Object data) {
        if (data == null) {
            throw new IllegalArgumentException(name + " is not supposed to be null");
        }
    }

    /**
     * Logs method entry.
     *
     * @param methodName method name
     */
    private void logEnter(String methodName) {
        logger.log(Level.DEBUG, "Entering method " + methodName);
    }

    /**
     * Logs method exit.
     *
     * @param methodName method name
     */
    private void logExit(String methodName) {
        logger.log(Level.DEBUG, "Leaving method " + methodName);
    }

    /**
     * Logs error.
     *
     * @param error exception instance
     * @return same as error parameter (for rethrowing)
     */
    private CloudVMServiceException logError(CloudVMServiceException error) {
        logger.log(Level.ERROR, error, "service reports error");
        return error;
    }
}

