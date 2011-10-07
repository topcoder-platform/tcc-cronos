/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.contest.ejb;

import java.util.List;
import java.util.ResourceBundle;

import javax.activation.DataHandler;
import javax.annotation.PostConstruct;
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
import javax.jws.WebService;

import org.jboss.ws.annotation.EndpointConfig;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.Phase;
import com.topcoder.catalog.entity.Technology;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.management.project.DesignComponents;
import com.topcoder.management.review.data.Comment;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.project.service.ScorecardReviewData;
import com.topcoder.security.GeneralSecurityException;
import com.topcoder.security.login.AuthenticationException;
import com.topcoder.service.facade.contest.CommonProjectContestData;
import com.topcoder.service.facade.contest.CommonProjectPermissionData;
import com.topcoder.service.facade.contest.ContestPaymentResult;
import com.topcoder.service.facade.contest.ContestServiceException;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.facade.contest.ContestServiceFilter;
import com.topcoder.service.facade.contest.SoftwareContestPaymentResult;
import com.topcoder.service.payment.CreditCardPaymentData;
import com.topcoder.service.payment.PaymentException;
import com.topcoder.service.payment.PaymentResult;
import com.topcoder.service.payment.TCPurhcaseOrderPaymentData;
import com.topcoder.service.permission.Permission;
import com.topcoder.service.permission.PermissionServiceException;
import com.topcoder.service.permission.PermissionType;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.specreview.SpecReview;
import com.topcoder.service.util.LoginUtil;

/**
 * <p>
 * This is an implementation of <code>ContestServiceFacadeWebService</code> web service in form of stateless session
 * EJB. It holds a reference to {@link ContestServiceFacade} which is delegated the fulfillment of requests.
 * </p>
 * <p>
 * This interface is a copy of the old ContestServiceFacade interface. ContestServiceFacade is no longer a web service
 * point. The security part is covered in this new web-service component. This bean's methods create this TCSubject
 * instance by do the login with LoginBean class and simply call the corresponding ContestServiceFacade method. This web
 * service must now be used instead of ContestServiceFacade by web service clients.
 * </p>
 * <p>
 * It is stateless session bean and using CMT. It has the annotations: &#064;Stateless &#064;WebService
 * &#064;EndpointConfig(configName = "Standard WSSecurity Endpoint") &#064;DeclareRoles( { "Cockpit User" })
 * &#064;RolesAllowed( { "Cockpit User" }) &#064;TransactionManagement(TransactionManagementType.CONTAINER)
 * &#064;TransactionAttribute(TransactionAttributeType.REQUIRED)
 * </p>
 *
 * @author waits
 * @version 1.0
 * @since Cockpit Security Facade Assembly V1.0
 */
@Stateless
@WebService
@EndpointConfig(configName = "Standard WSSecurity Endpoint")
@DeclareRoles( { "Cockpit User" })
@RolesAllowed( { "Cockpit User" })
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ContestServiceFacadeWebServiceBean implements ContestServiceFacadeWebServiceLocal,
        ContestServiceFacadeWebServiceRemote {
    /**
     * <p>
     * Represents the contest service facade bean to which calls to this bean will be redirected to.
     * </p>
     */
    @EJB(name = "ejb/ContestServiceFacade")
    private ContestServiceFacade facade;
    /**
     * <p>
     * The name of the resource bundle where the login bean URL is.
     * </p>
     */
    @Resource(name = "loginBeanResourceBundleBaseName")
    private String loginBeanResourceBundleBaseName;

    /**
     * <p>
     * The name of the resource bundle property which contains the login bean PROVIDER_URL.
     * </p>
     */
    @Resource(name = "loginBeanUrlPropertyName")
    private String loginBeanUrlPropertyName;
    /**
     * <p>
     * The Data-Source JNDI name of LoginBean.
     * </p>
     */
    @Resource(name = "loginBeanDSJndiName")
    private String loginBeanDSJndiName;
    /**
     * <p>
     * Represents the session context of this bean.
     * </p>
     * <p>
     * It is a resource injected by the EJB container and will not be null while client calls are being executed.
     * </p>
     */
    @Resource
    private SessionContext sessionContext;
    /**
     * <p>
     * This is the login bean URL. It is read in the initialize method.
     * </p>
     */
    private String loginBeanURL;

    /**
     * Default Empty constructor.
     */
    public ContestServiceFacadeWebServiceBean() {
    }

    /**
     * <p>
     * Checks the resource injection and loginBean URL configuration as initialization.
     * </p>
     *
     * @throws IllegalStateException if any resource or loginBeanUrl is not injected or not configed properly
     */
    @PostConstruct
    protected void init() {
        checkResource(loginBeanResourceBundleBaseName, "loginBeanResourceBundleBaseName");
        checkResource(loginBeanUrlPropertyName, "loginBeanUrlPropertyName");
        checkResource(loginBeanDSJndiName, "loginBeanDSJndiName");
        try {
            ResourceBundle rb = ResourceBundle.getBundle(loginBeanResourceBundleBaseName);
            loginBeanURL = rb.getString(loginBeanUrlPropertyName);
        } catch (Exception e) {
            throw new IllegalStateException("Fail to init the EJB as: " + e.getMessage());
        }
        checkResource(loginBeanURL, "loginBeanURL");
    }

    /**
     * Checks if the value is not null and not empty.
     *
     * @param value the value to check
     * @param name the name of the value
     * @throws IllegalStateException if the value is null or empty
     */
    private static void checkResource(String value, String name) {
        if (value == null || value.trim().length() == 0) {
            throw new IllegalStateException("The resource " + name + " does not injected or configed properly.");
        }
    }


    /**
     * <p>
     * This method will add a permission type, and return the added type entity.
     * </p>
     *
     * @param type the permission type to add.
     * @return the added permission type entity
     * @throws IllegalArgumentWSException if the argument is invalid
     * @throws PermissionServiceException if any error occurs when adding the permission type.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public PermissionType addPermissionType(PermissionType type) throws PermissionServiceException {
        try
        {
             return facade.addPermissionType(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), type);
        }
        catch (AuthenticationException e)
        {
            throw new PermissionServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new PermissionServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * This method adds a review comment to a review. It simply delegates all logic to underlying services.
     *
     * @param reviewId the review id to add the comment to
     * @param comment the review comment to add
     * @throws ContestServiceException if any unexpected error occurs in the underlying services.
     * @throws IllegalArgumentException if comment is null
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public void addReviewComment(long reviewId, Comment comment) throws ContestServiceException {
        try
        {
             facade.addReviewComment(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), reviewId, comment);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
        
    }

    /**
     * Adds the given user as a new submitter to the given project id.
     *
     * @param projectId the project to which the user needs to be added
     * @param userId the user to be added
     * @return the added resource id
     * @throws IllegalArgumentException if any id is &lt; 0
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     */
    public long addSubmitter(long projectId, long userId) throws ContestServiceException {
         try
        {
              return facade.addSubmitter(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), projectId,
                userId);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
       
    }

    /**
     * <p>
     * Assigns a specified user to a specified <code>assetDTO</code>.
     * </p>
     * <p>
     * If the user already assigned to the asset, this method simply does nothing.
     * </p>
     *
     * @param userId the id of the user
     * @param assetId the id of the assetDTO
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public void assignUserToAsset(long userId, long assetId) throws
            ContestServiceException {
        try
        {
              facade.assignUserToAsset(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), userId, assetId);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * <p>
     * Re-Open the software project in status of (project_status_id = 4-6, 8-10 in tcs_catalog:project_status_lu).
     * </p>
     *
     * @param projectId the project id to open
     * @param tcDirectProjectId the tc-direct-project-id
     * @return returns the newly created contest id
     * @throws ContestServiceException if any problem occurs
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public long createNewVersionForDesignDevContest(long projectId, long tcDirectProjectId, boolean autoDevCreating,
            boolean minorVersion) throws ContestServiceException {
         try
        {
             return facade.createNewVersionForDesignDevContest(LoginUtil.login(loginBeanURL, loginBeanDSJndiName,
                sessionContext), projectId, tcDirectProjectId, autoDevCreating, minorVersion);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (PermissionServiceException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * <p>
     * Creates a new <code>SoftwareCompetition</code> in the persistence.
     * </p>
     *
     * @param contest the <code>SoftwareCompetition</code> to create as a contest
     * @param tcDirectProjectId the TC direct project id.
     * @return the created <code>SoftwareCompetition</code> as a contest
     * @throws IllegalArgumentException if the input argument is invalid.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     */
    public SoftwareCompetition createSoftwareContest(SoftwareCompetition contest, long tcDirectProjectId)
            throws ContestServiceException {
        try
        {
            return facade.createSoftwareContest(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext),
                contest, tcDirectProjectId);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (PermissionServiceException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * This method creates a Specification Review project associated to a project determined by parameter.
     *
     * @param projectId the project id to create a Specification Review for
     * @return the created project
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     * @throws ContestServiceException if any unexpected error occurs in the underlying services
     */
    public FullProjectData createSpecReview(long projectId) throws ContestServiceException {
         try
        {
           return facade.createSpecReview(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), projectId);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
    }
        
    /**
     * <p>
     * This method finds all tc direct projects. Returns empty array if no projects found.
     * </p>
     *
     * @return Project array with project info, or empty array if none found
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public SoftwareCompetition[] findAllTcDirectProjects() throws 
            ContestServiceException {
         try
        {
          return facade.findAllTcDirectProjects(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext));
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * <p>
     * This method finds all given user tc direct projects . Returns empty array if no projects found.
     * </p>
     *
     * @param operator The user to search for projects
     * @return Project array with project info, or empty array if none found
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public SoftwareCompetition[] findAllTcDirectProjectsForUser(String operator) throws ContestServiceException {
        try
        {
          return facade.findAllTcDirectProjects(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext));
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
        
    }

    /**
     * <p>
     * Returns a list containing all active <code>Categories</code>.
     * </p>
     *
     * @return a list containing all active <code>Categories</code>. It can be empty if no objects found.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<Category> getActiveCategories() throws ContestServiceException {
         try
        {
          return facade.getActiveCategories(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext));
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * <p>
     * Returns a list containing all active <code>Technologies</code>.
     * </p>
     *
     * @return a list containing all active <code>Categories</code>. It can be empty if no objects found.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<Technology> getActiveTechnologies() throws 
            ContestServiceException {
        try
        {
          return facade.getActiveTechnologies(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext));
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }


    /**
     * <p>
     * This method retrieve all the permission types.
     * </p>
     *
     * @return all the permission types.
     * @throws IllegalArgumentWSException if the argument is invalid
     * @throws PermissionServiceException if any error occurs when getting permission types.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<PermissionType> getAllPermissionType() throws 
            PermissionServiceException {
        try
        {
          return facade.getAllPermissionType(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext));
        }
        catch (AuthenticationException e)
        {
            throw new PermissionServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new PermissionServiceException(e.getMessage(), e);
        }
        
    }

    /**
    /**
     * <p>
     * Gets the list of all existing contests for my project widget.
     * </p>
     *
     * @return a <code>List</code> listing all existing contests. Empty list is returned if there are no contests found.
     * @throws PersistenceException if any error occurs when getting contest.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<CommonProjectContestData> getCommonProjectContestData() throws ContestServiceException {
        try
        {
          return facade.getCommonProjectContestData(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext));
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }
    
    /**
     * Gets all contest fees by billing project id.
     *
     * @param projectId the billing project id
     * @return the list of project contest fees for the given project id
     * @throws ContestServiceException if any persistence or other error occurs
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<ProjectContestFee> getContestFeesByProject(long projectId) throws ContestServiceException {
        try
        {
          return facade.getContestFeesByProject(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext),
                projectId);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (PermissionServiceException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * Get all design components.
     *
     * @throws ContestServiceException if any other error occurs
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<DesignComponents> getDesignComponents() throws ContestServiceException {
        try
        {
            return facade.getDesignComponents(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext));
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * Find eligibility name for the client.
     *
     * @param billingProjectId; The ID of the billingProject.
     * @return The name of the eligibility group.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public String getEligibilityName(long billingProjectId)  {
        try
        {
               return facade.getEligibilityName(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext),
                billingProjectId);
        }
        catch (AuthenticationException e)
        {
            return "";
        }
        catch (GeneralSecurityException e)
        {
            return "";
        }
        
     
    }

    /**
     * <p>
     * This method retrieves the project along with all known associated information. Returns null if not found.
     * </p>
     *
     * @param projectId The ID of the project to retrieve
     * @return the project along with all known associated information
     * @throws IllegalArgumentException If projectId is negative
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public SoftwareCompetition getFullProjectData(long projectId) throws ContestServiceException {
        try
        {
            return facade.getFullProjectData(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), projectId);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (PermissionServiceException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * <p>
     * This method retrieve all the permissions that the user own for a given project. Returns empty list if no
     * permission found.
     * </p>
     *
     * @param userid user id to look for
     * @param projectid project id to look for
     * @return all the permissions that the user own for a given project.
     * @throws IllegalArgumentWSException if the argument is invalid
     * @throws PermissionServiceException if any error occurs when getting permissions.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<Permission> getPermissions(long userid, long projectid) throws PermissionServiceException {
        try
        {
            return facade.getPermissions(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), userid,
                projectid);
        }
        catch (AuthenticationException e)
        {
            throw new PermissionServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new PermissionServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * <p>
     * This method retrieve all the permissions that various users own for a given project. Returns empty list if no
     * permission found.
     * </p>
     *
     * @param projectid project id to look for
     * @return all the permissions that various users own for a given project.
     * @throws IllegalArgumentWSException if the argument is invalid
     * @throws PermissionServiceException if any error occurs when getting permissions.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<Permission> getPermissionsByProject(long projectid) throws PermissionServiceException {
        try
        {
            return facade.getPermissionsByProject(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext),
                projectid);
        }
        catch (AuthenticationException e)
        {
            throw new PermissionServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new PermissionServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * <p>
     * This method retrieve all the permissions that the user owned for any projects. Returns empty list if no
     * permission found.
     * </p>
     *
     * @param userid user id to look for
     * @return all the permissions that the user owned for any projects.
     * @throws IllegalArgumentWSException if the argument is invalid
     * @throws PermissionServiceException if any error occurs when getting permissions.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<Permission> getPermissionsByUser(long userid) throws PermissionServiceException {
        try
        {
            return facade.getPermissionsByUser(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), userid);
        }
        catch (AuthenticationException e)
        {
            throw new PermissionServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new PermissionServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * <p>
     * Returns a list containing all <code>Phases</code>.
     * </p>
     *
     * @return a list containing all active <code>Categories</code>. It can be empty if no objects found.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<Phase> getPhases() throws ContestServiceException {
        try
        {
            return facade.getPhases(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext));
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * This method retrieves scorecard and review information associated to a project determined by parameter. Note: a
     * single reviewer / review is assumed.
     *
     * @param projectId the project id to search for
     * @return the aggregated scorecard and review data
     * @throws ContestServiceException if any unexpected error occurs in the underlying services
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     * @throws ContestServiceException if any unexpected error occurs in the underlying services
     */
    public ScorecardReviewData getScorecardAndReview(long projectId) throws ContestServiceException {
        try
        {
            return facade.getScorecardAndReview(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext),
                projectId);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * <p>
     * BURG-1716: We need to add a method to get software contest by project id, the method will get all OR project
     * related data, then from project property to get comp version id then to call getAssetByVersionId to get assetDTO,
     * please check create software contest to see what data need to be returned.
     * </p>
     *
     * @param projectId the OR Project Id
     * @return SoftwareCompetition the softwareCompetition instance
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     */
    public SoftwareCompetition getSoftwareContestByProjectId(long projectId) throws ContestServiceException {
        try
        {
            return facade.getSoftwareContestByProjectId(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext),
                projectId);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (PermissionServiceException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * Gets the spec review for specified contest id.
     *
     * @param contestId the contest id
     * @param studio indicates whether the specified contest id is for studio contests.
     * @return the spec review that matches the specified contest id.
     * @throws ContestServiceException if any error during retrieval/save from persistence
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public SpecReview getSpecReviews(long contestId, boolean studio) throws ContestServiceException {
        try
        {
            return facade.getSpecReviews(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), contestId,
                studio);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * Returns whether a user is eligible for a particular contest.
     *
     * @param userId The user id
     * @param contestId The contest id
     * @param isStudio true if the contest is a studio contest, false otherwise.
     * @return true if the user is eligible for the specified contest, false otherwise.
     * @throws ContestServiceException if any other error occurs
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public boolean isEligible(long userId, long contestId, boolean isStudio) throws ContestServiceException
    {
        try
        {
            return facade.isEligible(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), userId, contestId,
                isStudio);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * Marks 'ready for review' by the writer of the specs for specified contest. Persistence is updated, on update the
     * spec would appear as review opportunity on tc site.
     *
     * @param contestId the specified contest id.
     * @param studio whether contest is studio or not.
     * @throws ContestServiceException if any error during retrieval/save from persistence
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public void markReadyForReview(long contestId, boolean studio) throws ContestServiceException {
        try
        {
            facade
                .markReadyForReview(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), contestId,
                        studio);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * Mark review comment with specified comment id as seen.
     *
     * @param commentId the comment id
     * @throws ContestServiceException if any error during retrieval/save from persistence
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public void markReviewCommentSeen(long commentId) throws ContestServiceException {
        try
        {
            facade.markReviewCommentSeen(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), commentId);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * Marks 'review done' by reviewer of the specs for specified contest. Persistence is updated and all end users
     * having write/full permission on the contest are notified by email.
     *
     * @param contestId the specified contest id.
     * @param contestName the contest name.
     * @param studio whether contest is studio or not.
     * @param tcDirectProjectId the tc direct project id.
     * @throws ContestServiceException if any error during retrieval/save from persistence
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public void markReviewDone(long contestId, String contestName, boolean studio, long tcDirectProjectId)
            throws ContestServiceException {
        try
        {
            facade.markReviewDone(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), contestId,
                contestName, studio, tcDirectProjectId);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * This method uploads a mock file to the corresponding specification review project of the specified project id, so
     * that it can continue with review. Regular submission or final fix will be uploaded according to the open phase.
     *
     * @param projectId the project id of the original project
     * @throws ContestServiceException if any unexpected error occurs in the underlying services, if the associated
     *             specification review project id cannot be found or if neither submission or final fixes phase are
     *             open.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public void markSoftwareContestReadyForReview(long projectId) throws ContestServiceException {
        try
        {
            facade.markSoftwareContestReadyForReview(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext),
                projectId);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }


    /**
     * <p>
     * Processes the contest sale.
     * </p>
     *
     * @param competition data that recognizes a contest.
     * @param paymentData payment information (credit card/po details) that need to be processed.
     * @return a <code>PaymentResult</code> result of the payment processing.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public SoftwareContestPaymentResult processContestCreditCardSale(SoftwareCompetition competition,
            CreditCardPaymentData paymentData) throws ContestServiceException {
        try
        {
            return facade.processContestCreditCardSale(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext),
                competition, paymentData);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (PermissionServiceException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * <p>
     * Processes the contest sale.
     * </p>
     *
     * @param competition data that recognizes a contest.
     * @param paymentData payment information (credit card/po details) that need to be processed.
     * @return a <code>PaymentResult</code> result of the payment processing.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public SoftwareContestPaymentResult processContestPurchaseOrderSale(SoftwareCompetition competition,
            TCPurhcaseOrderPaymentData paymentData) throws ContestServiceException {
        try
        {
             return facade.processContestPurchaseOrderSale(LoginUtil
                .login(loginBeanURL, loginBeanDSJndiName, sessionContext), competition, paymentData);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (PermissionServiceException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
       
    }

    /**
     * <p>
     * Removes a specified user from a specified <code>assetDTO</code>.
     * </p>
     *
     * @param userId the id of the user
     * @param assetId the id of the asset
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     */
    public void removeUserFromAsset(long userId, long assetId) throws ContestServiceException {
        try
        {
            facade.removeUserFromAsset(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), userId, assetId);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * <p>
     * Re-Open the software project in status of (project_status_id = 4-6, 8-10 in tcs_catalog:project_status_lu).
     * </p>
     *
     * @param projectId the project id to open
     * @param tcDirectProjectId the tc-direct-project-id
     * @return returns the newly created contest id
     * @throws ContestServiceException if any problem occurs
     */
    public long reOpenSoftwareContest(long projectId, long tcDirectProjectId) throws ContestServiceException {
        try
        {
             return facade.reOpenSoftwareContest(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext),
                projectId, tcDirectProjectId);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (PermissionServiceException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * Marks 'resubmit for review' by the writer of the specs for specified contest. Persistence is updated. Reviewer
     * (if any) is notified about the updates.
     *
     * @param contestId the specified contest id.
     * @param contestName the contest name.
     * @param studio whether contest is studio or not.
     * @param reviewerUserId reviewer user id.
     * @throws ContestServiceException if any error during retrieval/save from persistence
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public void resubmitForReview(long contestId, String contestName, boolean studio, long reviewerUserId)
            throws ContestServiceException {
        try
        {
             facade.resubmitForReview(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), contestId,
                contestName, studio, reviewerUserId);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }

        
    }

    /**
     * Save specified review comment for specified section and specified contest id to persistence.
     *
     * @param contestId the contest id
     * @param studio indicates whether the specified contest id is for studio contests.
     * @param sectionName the section name
     * @param comment the comment
     * @param role the user role type
     * @throws ContestServiceException if any error during retrieval/save from persistence
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public void saveReviewComment(long contestId, boolean studio, String sectionName, String comment, String role)
            throws ContestServiceException {
        try
        {
             facade.saveReviewComment(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), contestId, studio,
                sectionName, comment, role);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * Save specified review comment and review status for specified section and specified contest id to persistence.
     *
     * @param contestId the contest id
     * @param studio indicates whether the specified contest id is for studio contests.
     * @param sectionName the section name
     * @param comment the comment
     * @param isPass the is pass
     * @param role the user role type
     * @throws ContestServiceException if any error during retrieval/save from persistence
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public void saveReviewStatus(long contestId, boolean studio, String sectionName, String comment, boolean isPass,
            String role) throws ContestServiceException {
        try
        {
             facade.saveReviewStatus(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), contestId, studio,
                sectionName, comment, isPass, role);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * <p>
     * Sets the status of a existing submission.
     * </p>
     *
     * @param submissionId the submission's id
     * @param submissionStatusId the submission status id
     * @param operator the operator which execute the operation
     * @throws IllegalArgumentException if any id is &lt; 0 or if operator is null or trim to empty
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     */
    public void setSubmissionStatus(long submissionId, long submissionStatusId, String operator)
            throws ContestServiceException {
        try
        {
             facade.setSubmissionStatus(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), submissionId,
                submissionStatusId, operator);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (PermissionServiceException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * <p>
     * This method updates array of permissions to the persistence.
     * </p>
     *
     * @param permissions the permissions to update.
     * @throws IllegalArgumentWSException if the argument is invalid
     * @throws PermissionServiceException if any error occurs when updating the permission.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public void updatePermissions(Permission[] permissions) throws PermissionServiceException {
        try
        {
             facade.updatePermissions(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), permissions);
        }
        catch (AuthenticationException e)
        {
            throw new PermissionServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new PermissionServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * <p>
     * This method will update permission type data.
     * </p>
     *
     * @param type the permission type to update.
     * @throws IllegalArgumentWSException if the argument is invalid
     * @throws PermissionServiceException if any error occurs when updating the permission type.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public void updatePermissionType(PermissionType type) throws PermissionServiceException {
        try
        {
             facade.updatePermissionType(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), type);
        }
        catch (AuthenticationException e)
        {
            throw new PermissionServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new PermissionServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * <p>
     * Updates a <code>SoftwareCompetition</code> in the persistence.
     * </p>
     *
     * @param contest the <code>SoftwareCompetition</code> to update as a contest
     * @param tcDirectProjectId the TC direct project id.
     * @throws IllegalArgumentException if the input argument is invalid.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public SoftwareCompetition updateSoftwareContest(SoftwareCompetition contest, long tcDirectProjectId)
            throws ContestServiceException {
        try
        {
             return facade.updateSoftwareContest(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext),
                contest, tcDirectProjectId);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (PermissionServiceException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * <p>
     * Adds a new final fix upload for an user in a particular project. This submission always overwrite the previous
     * ones.
     * </p>
     *
     * @param projectId the project's id
     * @param filename the file name to use
     * @param finalFix the final fix file data
     * @return the id of the created final fix submission
     * @throws IllegalArgumentException if any id is &lt; 0, if any argument is <code>null</code> or trim to empty
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public long uploadFinalFix(long projectId, String filename, DataHandler finalFix) throws ContestServiceException {
        try
        {
             return facade.uploadFinalFix(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), projectId,
                filename, finalFix);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * <p>
     * Adds a new submission for an user in a particular project.
     * </p>
     * <p>
     * If the project allows multiple submissions for users, it will add the new submission and return. If multiple
     * submission are not allowed for the project, firstly it will add the new submission, secondly mark previous
     * submissions as deleted and then return.
     * </p>
     *
     * @param projectId the project's id
     * @param filename the file name to use
     * @param submission the submission file data
     * @return the id of the new submission
     * @throws IllegalArgumentException if any id is &lt; 0, if any argument is <code>null</code> or trim to empty
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public long uploadSubmission(long projectId, String filename, DataHandler submission)
            throws ContestServiceException {
        try
        {
             return facade.uploadSubmission(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), projectId,
                filename, submission);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * <p>
     * Adds a new test case upload for an user in a particular project. This submission always overwrite the previous
     * ones.
     * </p>
     *
     * @param projectId the project's id
     * @param filename the file name to use
     * @param testCases the test cases data
     * @return the id of the created test cases submission
     * @throws IllegalArgumentException if any id is &lt; 0, if any argument is <code>null</code> or trim to empty
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public long uploadTestCases(long projectId, String filename, DataHandler testCases) throws ContestServiceException {
        try
        {
             return facade.uploadTestCases(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), projectId,
                filename, testCases);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * Returns whether the contest is private.
     *
     * @param contestId The contest id
     * @param isStudio true if the contest is a studio contest, false otherwise.
     * @return true if the contest is a private one, false otherwise.
     * @throws ContestServiceException if any other error occurs
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public boolean isPrivate(long contestId, boolean isStudio) throws ContestServiceException {
        try
        {
             return facade
                .isPrivate(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), contestId, isStudio);
        }
        catch (AuthenticationException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestServiceException(e.getMessage(), e);
        }
        
    }
}
