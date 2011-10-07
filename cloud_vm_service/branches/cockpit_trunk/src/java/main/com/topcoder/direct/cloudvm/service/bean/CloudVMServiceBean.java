/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.cloudvm.service.bean;

import com.amazonaws.AmazonServiceException;
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
import com.topcoder.direct.services.view.dto.cloudvm.VMAccountUser;
import com.topcoder.direct.services.view.dto.cloudvm.VMAvailabilityZone;
import com.topcoder.direct.services.view.dto.cloudvm.VMContestType;
import com.topcoder.direct.services.view.dto.cloudvm.VMUsage;
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
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.facade.contest.ContestServiceException;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Collections;
import java.util.Comparator;

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

    private static final DateFormat DATE_FORMATTER = new SimpleDateFormat("MM/dd/yyyy HH:mm");
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
     * The user service to retrieve the user email. Will be injected by the container. Must be non-null.
     * @since BUGR-3932
     */
    @EJB(name = "ejb/ContestServiceFacade")
    private ContestServiceFacade contestServiceFacade;

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
     * @param vmInstanceMain the VM instance to launch.
     * @return the launched VM instance.
     * @throws IllegalArgumentException if any argument is null.
     * @throws CloudVMServiceException  if any error occurs (wrap the underlying exceptions).
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<VMInstanceData> launchVMInstance(TCSubject tcSubject, VMInstance vmInstanceMain) throws CloudVMServiceException {
        logEnter("launchVMInstance");
        checkNull("tcSubject", tcSubject);
        checkNull("vmInstance", vmInstanceMain);
        List<VMInstanceData> res = new LinkedList<VMInstanceData>();
        try {
            EntityManager entityManager = getEntityManager();

            String[] userHandles = vmInstanceMain.getTcMemberHandle().split(";");
            for(String tcMemberHandle : userHandles) {
                VMInstance vmInstance = new VMInstance();
                vmInstance.setVmImageId(vmInstanceMain.getVmImageId());
                vmInstance.setContestId(vmInstanceMain.getContestId());
                vmInstance.setUsageId(vmInstanceMain.getUsageId());
                vmInstance.setContestTypeId(vmInstanceMain.getContestTypeId());
                vmInstance.setSvnBranch(vmInstanceMain.getSvnBranch());
                vmInstance.setTcMemberHandle(tcMemberHandle);
                vmInstance.setUserData(vmInstanceMain.getUserData());
                vmInstance.setCreationTime(new Date());
                
                String contestName = "";
                if (vmInstanceMain.getContestTypeId() == SOFTWARE_CONTEST_TYPE_ID || vmInstanceMain.getContestTypeId() == STUDIO_CONTEST_TYPE_ID ) {
                    contestName = contestServiceFacade.getSoftwareContestByProjectId(tcSubject, vmInstanceMain.getContestId()).getAssetDTO().getName();
                } else if (vmInstanceMain.getContestTypeId() == BUG_RACE_TYPE_ID) {
                    contestName = "Bug Race";
                    // no check for bug races
                }
                vmInstance.setContestName(contestName);

                tcMemberHandle = tcMemberHandle.trim();
                logger.log(Level.DEBUG, "Launching for " + tcMemberHandle);
                verifySecurityKey(entityManager, tcMemberHandle);

                VMImage vmImage = getVMImage(vmInstance.getVmImageId(), tcSubject);
                if (vmImage == null) {
                    throw new CloudVMServiceException("No vm image with id " + vmInstance.getVmImageId());
                }

                VMAccount vmAccount = vmImage.getVmAccount();
                AmazonEC2Client client = new AmazonEC2Client(
                    new BasicAWSCredentials(vmAccount.getAwsAccessKeyId(), vmAccount.getAwsSecurityAccessKey()));

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
                userData.append("handle=").append(tcMemberHandle).append("\n");
                userData.append("user_email=").append(userService.getEmailAddress(tcMemberHandle)).append("\n"); // BUGR-3931
                logger.log(Level.DEBUG, "user data is: " + vmInstance.getUserData());
                // append entered user data
                if(vmInstance.getUserData() != null) {
                    String[] userDataLines = vmInstance.getUserData().split("\n");
                    for(String userDataLine : userDataLines) {
                        userDataLine = userDataLine.trim();
                        if(userDataLine.length() == 0) continue;
                        int idx = userDataLine.indexOf('=');
                        if(idx == -1 || idx == 0 || idx == userDataLine.length() - 1) {
                            throw new CloudVMServiceException("User data should be in key=value format");
                        }
                        userData.append(userDataLine).append("\n");
                    }
                }
                request.setUserData(new String(Base64.encodeBase64(userData.toString().getBytes())));
    
                request.setImageId(vmImage.getAwsImageId());
                request.setMinCount(1);
                request.setMaxCount(1);
    
                // send request
                RunInstancesResult result = client.runInstances(request);
                Instance instance = result.getReservation().getInstances().get(0);
    
                vmInstance.setAwsInstanceId(instance.getInstanceId());
                vmInstance.setVmAccountUserId(getVMAccountUser(tcSubject.getUserId(), vmAccount.getId()).getId());
                logger.log(Level.DEBUG, "state=" + instance.getState() + ", public ip= " + instance.getPublicIpAddress());
                vmInstance.setPublicIP(instance.getPublicIpAddress()); // BUGR-3932
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
                fillInstanceData(tcSubject, vmInstance, data);
                res.add(data);
            }
            return new ArrayList<VMInstanceData>(res);

        } catch (UserServiceException e) {
            throw logError(new CloudVMServiceException("Unable to fetch user.", e));
        } catch (Exception e) {
            throw logError(
                new CloudVMServiceException("Unable to launch vm image with id " + vmInstanceMain.getVmImageId(), e));
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


            VMInstance vmInstance = entityManager.find(VMInstance.class, instanceId);
            TerminateInstancesRequest request =
                new TerminateInstancesRequest().withInstanceIds(vmInstance.getAwsInstanceId());

            // create and populate request
            VMAccount vmAccount = getVMAccount(tcSubject, vmInstance.getVmAccountUserId());
            AmazonEC2Client client = new AmazonEC2Client(
                new BasicAWSCredentials(vmAccount.getAwsAccessKeyId(), vmAccount.getAwsSecurityAccessKey()));

            // send request
            TerminateInstancesResult result = client.terminateInstances(request);

            // create audit record
            VMInstanceAudit audit = new VMInstanceAudit();
            audit.setAction("terminated");
            audit.setInstanceId(vmInstance.getId());
            audit.setCreateDate(new Date());
            entityManager.merge(audit);
            
            vmInstance.setTerminated(true);
            entityManager.merge(vmInstance);

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
     * Contest type id for software contests.
     * @since BUGR-3932
     */
    private static final int SOFTWARE_CONTEST_TYPE_ID = 1;

    /**
     * Contest type id for studio contests.
     * @since BUGR-3932
     */
    private static final int STUDIO_CONTEST_TYPE_ID = 2;

    /**
     * Contest type id for bug race contests.
     * @since BUGR-3932
     */
    private static final int BUG_RACE_TYPE_ID = 3;
    
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
            List<VMAccount> vmAccounts;
            try {
                vmAccounts = getVMAccounts(tcSubject);
            } catch (CloudVMServiceException e) { // return empty list
                return new ArrayList<VMInstanceData>();
            }

            List<VMInstanceData> results = new ArrayList<VMInstanceData>();

            for (VMAccount vmAccount : vmAccounts) {
                // make db query
                Query q;
                if (!inRole(tcSubject, "Administrator")) {
                    q = entityManager.createQuery("select a from VMInstance a, VMAccountUser b where a.terminated=:terminated and a.vmAccountUserId = b.id and b.vmAccountId=:accountId and b.userId=:userId order by a.contestId desc");
                    q.setParameter("accountId", vmAccount.getId());
                    q.setParameter("userId", tcSubject.getUserId());
                    q.setParameter("terminated", false);
                } else {
                    q = entityManager.createQuery("select a from VMInstance a, VMImage b where a.terminated=:terminated and a.vmImageId = b.id and b.vmAccount.id=:accountId order by a.contestId desc");
                    q.setParameter("accountId", vmAccount.getId());
                    q.setParameter("terminated", false);
                }
                List<VMInstance> instances = q.getResultList();

                // map to reference by instance id, list to preserve order
                List<VMInstanceData> data = new ArrayList<VMInstanceData>();
                Map<String, VMInstanceData> dataMap = new HashMap<String, VMInstanceData>();

                // populate result with data from db
                AmazonEC2Client client = new AmazonEC2Client(
                    new BasicAWSCredentials(vmAccount.getAwsAccessKeyId(), vmAccount.getAwsSecurityAccessKey()));

                // create VMInstanceData objects
                for (VMInstance instance : instances) {
                    VMInstanceData instanceData = new VMInstanceData();
                    instanceData.setInstance(instance);
                    data.add(instanceData);
                    dataMap.put(instance.getAwsInstanceId(), instanceData);
                    // TODO: Comment ?!?
                    instanceData.setStatus(VMInstanceStatus.TERMINATED);

                }

                // make remote call and fetch information about vm statuses
                DescribeInstancesResult result = null;
                try {
                 result =
                    client.describeInstances(new DescribeInstancesRequest().withInstanceIds(dataMap.keySet()));
                }
                catch(AmazonServiceException  aws) {
                    logger.log(Level.DEBUG, aws.getErrorCode());
                    if("InvalidInstanceID.NotFound".equals(aws.getErrorCode())) {
                        logger.log(Level.DEBUG, "AWS error");
                    } else {
                        throw logError(new CloudVMServiceException("Unable to describe instances", aws));
                    }
                } catch (Exception e) {
                    throw logError(new CloudVMServiceException("Unable to describe instances", e));
                }
                for (Reservation reservation : result.getReservations()) {
                    for (Instance instance : reservation.getInstances()) {
                        if (dataMap.get(instance.getInstanceId()) != null) {
                            VMInstanceData inst = dataMap.get(instance.getInstanceId());
                            inst.setStatus(convertState(instance.getState()));
                            logger.log(Level.DEBUG, inst.getInstance() + " " + instance.getState().getName());
                            // BUGR-3981
                            if(inst.getStatus() == VMInstanceStatus.TERMINATED) {
                                markAsTerminated(inst, instance.getLaunchTime());
                                data.remove(inst);
                            }

                            // set public IP if it is not set yet
                            if(inst.getInstance().getPublicIP() == null ||instance.getPublicIpAddress() != null) {
                                VMInstance toBeSaved = inst.getInstance();
                                toBeSaved.setPublicIP(instance.getPublicIpAddress());
                                toBeSaved = entityManager.merge(toBeSaved);
                                data.get(data.indexOf(inst)).setInstance(toBeSaved);
                            }
                            dataMap.remove(instance.getInstanceId());
                        }
                    }
               }

               for (VMInstanceData inst : dataMap.values()) { // terminated
                    logger.log(Level.DEBUG, "here for " + inst.getInstance().getId());
                    markAsTerminated(inst, new Date());
                    data.remove(inst);
               }

               for (VMInstanceData instanceData : data) {
                   fillInstanceData(tcSubject, instanceData.getInstance(), instanceData);
               }

                results.addAll(data);
            }

            // for administrators, fetch vm managers also
            if (inRole(tcSubject, "Administrator")) {
                for (VMInstanceData instanceData : results) {
                    VMAccountUser accountUser = entityManager.find(VMAccountUser.class, instanceData.getInstance().getVmAccountUserId());
                    instanceData.setManagerHandle(userService.getUserHandle(accountUser.getUserId()));
                }
            }

            List list = new ArrayList<VMInstanceData>(results);
            Collections.sort(list, new Comparator<VMInstanceData>() {
                public int compare(VMInstanceData o1, VMInstanceData o2) {
                    long delta = o1.getInstance().getContestId() - o2.getInstance().getContestId();
                    if (delta > 0) {
                        return 1;
                    } else if (delta < 0) {
                        return -1;
                    }

                    return 0;
                }
            });
            return list;
        } catch (UserServiceException e) {
            throw logError(new CloudVMServiceException("Unable to fetch user handle.", e));
        } catch (Exception e) {
            throw logError(new CloudVMServiceException("Unable to fetch vm instances.", e));
        } finally {
            logExit("getVMInstances");
        }
    }

    /**
     * Set insrance as terminated.
     * @param inst instance to be marked as terminated
     * @since BUGR-3981
     */
	private void markAsTerminated(VMInstanceData inst, Date when) throws CloudVMServiceException {
		try{
			logger.log(Level.DEBUG, "setting as terminated " + inst.getInstance().getId());
			VMInstance toBeTerminated = inst.getInstance();
	    	toBeTerminated.setTerminated(true);
	    	toBeTerminated = getEntityManager().merge(toBeTerminated);
	    	
	        // create audit record
	        VMInstanceAudit audit = new VMInstanceAudit();
	        audit.setAction("terminated");
	        audit.setInstanceId(toBeTerminated.getId());
	        audit.setCreateDate(when);
	        getEntityManager().merge(audit);
		}
        catch (Exception e) {
        	throw logError(new CloudVMServiceException("unable to mark terminated", e));
        }
        
	}
    /**
     * Fills contest name and vm image TC name.
     * 
     * @param tcSubject
     * @param instance
     * @param instanceData
     * @since BUGR-3932
     */
    private void fillInstanceData(TCSubject tcSubject, VMInstance instance, VMInstanceData instanceData) throws CloudVMServiceException {
        logEnter("fillInstanceData");
        try {
            // query VM image TC name
            Query q = getEntityManager().createQuery("select a from VMImage a where a.id=:vmImageId");
            q.setParameter("vmImageId", instance.getVmImageId());
            VMImage img = (VMImage) q.getResultList().get(0);
            instanceData.setVmImageTcName(img.getTcName());
            instanceData.setAccountName(img.getVmAccount().getAccountName());
            instanceData.setVmCreationTime(DATE_FORMATTER.format(instance.getCreationTime()));
            instanceData.setUsage(getVMUsage(instance.getUsageId()).getName());
            
            /*
            String contestName = "";
            if (instance.getContestTypeId() == SOFTWARE_CONTEST_TYPE_ID) {
                contestName = contestServiceFacade.getSoftwareContestByProjectId(tcSubject, instance.getContestId()).getAssetDTO().getName();
            } else if (instance.getContestTypeId() == STUDIO_CONTEST_TYPE_ID) {
                contestName = contestServiceFacade.getContest(tcSubject, instance.getContestId()).getContestData().getName();
            } else if (instance.getContestTypeId() == BUG_RACE_TYPE_ID) {
                contestName = "Bug Race";
                // no check for bug races
            }
            */

            instanceData.setContestName(instance.getContestName());
        } catch (Exception e) {
            throw logError(
                new CloudVMServiceException("Unable to fill instance data", e));
        } finally {
            logExit("fillInstanceData");
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
            if (inRole(tcSubject, "Administrator")) {
                return entityManager.createQuery("select i from VMImage i ").getResultList();
            } else {
                Query q = entityManager.createQuery("select i from VMImage i, VMAccountUser b where i.vmAccount.id = b.vmAccountId and b.userId=:userId");
                q.setParameter("userId", tcSubject.getUserId());
                return q.getResultList();
            }
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
     * Get VM account by user id and vm account user id.
     *
     * @param tcSubject the currently logged-in user info.
     * @param vmAccountUserId the vm account user id.
     * @return the vm account.
     * @throws CloudVMServiceException if any error occurs.
     */
    private VMAccount getVMAccount(TCSubject tcSubject, long vmAccountUserId) throws CloudVMServiceException {
        try {
            EntityManager entityManager = getEntityManager();

            Query q;
            if (!inRole(tcSubject, "Administrator")) {
                q = entityManager.createQuery("select a from VMAccount a, VMAccountUser b where a.id = b.vmAccountId AND b.userId=:userId AND b.id =:vmAccountUserId");
                q.setParameter("userId", tcSubject.getUserId());
                q.setParameter("vmAccountUserId", vmAccountUserId);
            } else {
                q = entityManager.createQuery("select a from VMAccount a, VMAccountUser b where a.id = b.vmAccountId AND b.id =:vmAccountUserId");
                q.setParameter("vmAccountUserId", vmAccountUserId);
            }

            VMAccount result = (VMAccount) q.getSingleResult();
            if (result == null) {
                throw logError(new CloudVMServiceException("No VM account for user with id [" + tcSubject.getUserId() + "]"));
            }
            return result;
        } catch (Exception e) {
            throw logError(new CloudVMServiceException("You have not set up a VM Account!"));
        }
    }

     /**
     * Get VM usages.
     *
     * @param tcSubject the currently logged-in user info.
     * @return the vm usages.
     * @throws IllegalArgumentException if the tcSubject argument is null.
     * @throws CloudVMServiceException  if any error occurs.
     */
    public List<VMUsage> getVMUsages(TCSubject tcSubject) throws CloudVMServiceException {
        logEnter("getVMUsages");
        checkNull("tcSubject", tcSubject);
        try {
            EntityManager entityManager = getEntityManager();
            return entityManager.createQuery("select c from VMUsage c ").getResultList();
        } catch (Exception e) {
            throw logError(new CloudVMServiceException("Unable to fetch vm usages.", e));
        } finally {
            logExit("getVMUsages");
        }
    }


        /**
     * Get VM account user by user id and account id.
     *
     * @param userId the user id.
     * @param accountId the account id.
     * @return the vm account user.
     * @throws CloudVMServiceException if any error occurs.
     */
    private VMAccountUser getVMAccountUser(long userId, long accountId) throws CloudVMServiceException {
        try {
            EntityManager entityManager = getEntityManager();
            Query q = entityManager.createQuery("select a from VMAccountUser a where a.userId=:userId AND a.vmAccountId =:accountId");
            q.setParameter("userId", userId);
            q.setParameter("accountId", accountId);

            VMAccountUser result = (VMAccountUser) q.getSingleResult();
            if (result == null) {
                throw logError(new CloudVMServiceException("No VM account user for user with id [" + userId + "]  and vm account with id [" + accountId + "]" ));
            }
            return result;
        } catch (Exception e) {
            throw logError(new CloudVMServiceException("You have not set up a VM Account User!"));
        }
    }

     /**
     * Get VM usage by id.
     *
     * @param vmUsageId the VM usage id.
     * @return the vm usage.
     * @throws CloudVMServiceException if any error occurs.
     */
    private VMUsage getVMUsage(long vmUsageId) throws CloudVMServiceException {
        EntityManager entityManager = getEntityManager();
        return entityManager.find(VMUsage.class, vmUsageId);
    }


    /**
     * Get VM accounts by user id.
     *
     * @param userId the user id.
     * @return the vm accounts.
     * @throws CloudVMServiceException if any error occurs.
     */
    private List<VMAccount> getVMAccounts(TCSubject tcSubject) throws CloudVMServiceException {
        try {
            EntityManager entityManager = getEntityManager();
            List<VMAccount> result = new ArrayList<VMAccount>();

            if (inRole(tcSubject, "Administrator")) {

                Query q = entityManager.createQuery("select a from VMAccount a ");
                result = (List<VMAccount>) q.getResultList();
                
            }  else {

                Query q = entityManager.createQuery("select a from VMAccount a, VMAccountUser b where a.id = b.vmAccountId AND b.userId=:userId");
                q.setParameter("userId", tcSubject.getUserId());

                result = (List<VMAccount>) q.getResultList();
                if (result.isEmpty()) {
                    throw logError(new CloudVMServiceException("No VM account for user with id [" + tcSubject.getUserId() + "]"));
                }
            }

            return result;
        } catch (Exception e) {
            throw logError(new CloudVMServiceException("You have not set up a VM Account!"));
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
     * Get VM image by id and user id.
     *
     * @param vmImageId the VM image id.
     * @param tcSubject TCSubject instance for user
     * @return the vm image.
     * @throws CloudVMServiceException if any error occurs.
     */
    private VMImage getVMImage(long vmImageId, TCSubject tcSubject) throws CloudVMServiceException {
        EntityManager entityManager = getEntityManager();
        if (inRole(tcSubject, "Administrator")) {
            Query q = entityManager.createQuery("select i from VMImage i where i.id=:id");
            q.setParameter("id", vmImageId);
            return (VMImage) q.getSingleResult();
        } else {
            Query q = entityManager.createQuery("select i from VMImage i, VMAccountUser b where i.vmAccount.id = b.vmAccountId and b.userId=:userId and i.id=:id");
            q.setParameter("id", vmImageId);
            q.setParameter("userId", tcSubject.getUserId());
            return (VMImage) q.getSingleResult();
        }
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

