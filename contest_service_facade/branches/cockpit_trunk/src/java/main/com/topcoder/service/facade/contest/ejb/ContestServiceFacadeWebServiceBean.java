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
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.specreview.SpecReview;
import com.topcoder.service.studio.ChangeHistoryData;
import com.topcoder.service.studio.CompletedContestData;
import com.topcoder.service.studio.ContestNotFoundException;
import com.topcoder.service.studio.ContestStatusData;
import com.topcoder.service.studio.ContestTypeData;
import com.topcoder.service.studio.DocumentNotFoundException;
import com.topcoder.service.studio.IllegalArgumentWSException;
import com.topcoder.service.studio.MediumData;
import com.topcoder.service.studio.PersistenceException;
import com.topcoder.service.studio.ProjectNotFoundException;
import com.topcoder.service.studio.StatusNotAllowedException;
import com.topcoder.service.studio.StatusNotFoundException;
import com.topcoder.service.studio.SubmissionData;
import com.topcoder.service.studio.SubmissionFeedback;
import com.topcoder.service.studio.SubmissionPaymentData;
import com.topcoder.service.studio.UploadedDocument;
import com.topcoder.service.studio.UserNotAuthorizedException;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.DocumentType;
import com.topcoder.service.studio.contest.SimpleContestData;
import com.topcoder.service.studio.contest.SimpleProjectContestData;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.contest.User;
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
     * Adds the specified list of history data for the associated contest.
     * </p>
     *
     * @param history a <code>List</code> of history data for a contest.
     * @throws PersistenceException if any other error occurs.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public void addChangeHistory(List<ChangeHistoryData> history) throws PersistenceException {
        try
        {
            facade.addChangeHistory(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), history);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

    /**
     * <p>
     * Associates the specified document with specified contest.
     * </p>
     *
     * @param documentId a <code>long</code> providing the ID of a document to be associated with specified contest.
     * @param contestId a <code>long</code> providing the ID of a contest to associate the specified document with.
     * @throws PersistenceException if some persistence errors occur.
     * @throws ContestNotFoundException if any of requested contest or document is not found.
     * @throws GeneralSecurityException if any error occurs during security checking
     * @throws AuthenticationException if any error occurs during authentication
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if any of specified IDs is negative.
     */
    public void addDocumentToContest(long documentId, long contestId) throws PersistenceException,
            ContestNotFoundException {
        
        try
        {
             facade.addDocumentToContest(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), documentId,
                contestId);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (PermissionServiceException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
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
     * Creates new contest for specified project. Upon creation an unique ID is generated and assigned to returned
     * contest.
     * </p>
     *
     * @param contest a <code>StudioCompetition</code> providing the data for new contest to be created.
     * @param tcDirectProjectId a <code>long</code> providing the ID of a project the new competition belongs to.
     * @return a <code>StudioCompetition</code> providing the data for created contest and having the ID auto-generated.
     * @throws PersistenceException if some persistence errors occur.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if specified <code>competition</code> is <code>null</code> or if the specified
     *             <code>tcDirectProjectId</code> is negative.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public StudioCompetition createContest(StudioCompetition contest, long tcDirectProjectId)
            throws PersistenceException {
         try
        {
             return facade.createContest(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), contest,
                tcDirectProjectId);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (PermissionServiceException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
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
     * Deletes the contest referenced by the specified ID.
     * </p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to delete.
     * @throws PersistenceException if any other error occurs.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public void deleteContest(long contestId) throws PersistenceException {
         try
        {
          facade.deleteContest(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), contestId);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (PermissionServiceException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
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
     * Gets the list of all existing contests.
     * </p>
     *
     * @return a <code>List</code> listing all existing contests. Empty list is returned if there are no contests found.
     * @throws PersistenceException if any error occurs when getting contest.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<StudioCompetition> getAllContestHeaders() throws PersistenceException {
        try
        {
          return facade.getAllContestHeaders(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext));
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

    /**
     * <p>
     * Gets the list of all existing contests.
     * </p>
     *
     * @return a <code>List</code> listing all existing contests. Empty list is returned if there are no contests found.
     * @throws PersistenceException if any error occurs when getting contest.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<StudioCompetition> getAllContests() throws PersistenceException {
        try
        {
          return facade.getAllContests(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext));
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

    /**
     * <p>
     * Gets existing contest types.
     * </p>
     *
     * @return a <code>List</code> listing available contest types. Empty list is returned if there are no types.
     * @throws PersistenceException if some persistence errors occur.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<ContestTypeData> getAllContestTypes() throws PersistenceException {
        try
        {
          return facade.getAllContestTypes(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext));
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
    }    
    
    /**
     * <p>
     * Get all the DocumentType objects.
     * </p>
     *
     * @return the list of all available DocumentType
     * @throws ContestManagementException if any error occurs when getting contest
     * @throws PersistenceException if any error occurs
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<DocumentType> getAllDocumentTypes() throws PersistenceException {
        try
        {
          return facade.getAllDocumentTypes(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext));
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

    /**
     * <p>
     * Gets existing medium types.
     * </p>
     *
     * @return a <code>List</code> listing available medium types. Empty list is returned if there are no types.
     * @throws PersistenceException if some persistence errors occur.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<MediumData> getAllMediums() throws PersistenceException {
        try
        {
          return facade.getAllMediums(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext));
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
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
     * <p>
     * Gets all studio file types to return. If no studio file type exists, return an empty list
     * </p>
     *
     * @return a list of studio file types
     * @throws PersistenceException if any error occurs when getting studio file types.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<StudioFileType> getAllStudioFileTypes() throws PersistenceException{
        try
        {
          return facade.getAllStudioFileTypes(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext));
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

    /**
     * <p>
     * Gets the history data for the specified contest.
     * </p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to get history data for.
     * @return a <code>List</code> of history data for specified contest.
     * @throws PersistenceException if any other error occurs.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<ChangeHistoryData> getChangeHistory(long contestId) throws PersistenceException {
        try
        {
          return facade.getChangeHistory(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), contestId);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

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
    public List<CommonProjectContestData> getCommonProjectContestData() throws PersistenceException {
        try
        {
          return facade.getCommonProjectContestData(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext));
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

    /**
     * <p>
     * Gets the list of all existing contests related to given project for my project widget.
     * </p>
     *
     * @param pid given project id
     * @return a <code>List</code> listing all existing contests. Empty list is returned if there are no contests found.
     * @throws PersistenceException if any error occurs when getting contest.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<CommonProjectContestData> getCommonProjectContestDataByPID(long pid) throws PersistenceException {
        try
        {
          return facade.getCommonProjectContestDataByPID(LoginUtil.login(loginBeanURL, loginBeanDSJndiName,
                sessionContext), pid);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
    }

    /**
     * <p>
     * Gets the list of project and their permissions (including permissions for the parent tc project)
     * </p>
     *
     * @param createdUser user for which to get the permissions
     * @return list of project and their permissions.
     * @throws PersistenceException if any persitence error occurs
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<CommonProjectPermissionData> getCommonProjectPermissionDataForUser(long createdUser)
            throws PersistenceException{
        try
        {
          return facade.getCommonProjectPermissionDataForUser(LoginUtil.login(loginBeanURL, loginBeanDSJndiName,
                sessionContext), createdUser);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

    /**
     * <p>
     * Gets the contest referenced by the specified ID.
     * </p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to get details for.
     * @return a <code>StudioCompetition</code> providing the data for the requested contest.
     * @throws PersistenceException if some persistence errors occur.
     * @throws ContestNotFoundException if requested contest is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if specified <code>contestId</code> is negative.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public StudioCompetition getContest(long contestId) throws PersistenceException, ContestNotFoundException {
        try
        {
          return facade.getContest(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), contestId);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (PermissionServiceException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

    /**
     * <p>
     * This is going to fetch all the currently available contests for my project widget.
     * </p>
     *
     * @return the list of all available contents (or empty if none found)
     * @throws PersistenceException if any error occurs when getting contest.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<SimpleContestData> getContestDataOnly() throws PersistenceException {
         try
        {
          return facade.getContestDataOnly(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext));
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

    /**
     * <p>
     * This is going to fetch all the currently available contests for my project widget related to given project.
     * </p>
     *
     * @param pid given project id
     * @return the list of all available contents (or empty if none found)
     * @throws PersistenceException if any error occurs when getting contest.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<SimpleContestData> getContestDataOnlyByPID(long pid) throws PersistenceException {
        try
        {
          return facade.getContestDataOnlyByPID(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), pid);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
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
     * <p>
     * Gets the contests for the given project.
     * </p>
     *
     * @param tcDirectProjectId a <code>long</code> providing the ID of a project to get the list of associated contests
     *            for.
     * @return a <code>List</code> providing the contests for specified project. Empty list is returned if there are no
     *         such contests found.
     * @throws PersistenceException if some persistence errors occur.
     * @throws ProjectNotFoundException if requested project is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if specified <code>tcDirectProjectId</code> is negative.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<StudioCompetition> getContestsForProject(long tcDirectProjectId) throws PersistenceException,
            ProjectNotFoundException {
        try
        {
          return facade.getContestsForProject(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext),
                tcDirectProjectId);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
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

    /*
     * (non-Javadoc)
     * @see com.topcoder.service.facade.contest.ContestServiceFacadeWebService#getFinalSubmissionsForContest(long)
     */
    public List<SubmissionData> getFinalSubmissionsForContest(long contestId) throws ContestServiceException
    {
        return null;
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
     * Gets the most history data for the most recent changes to specified contest.
     * </p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to get history data for.
     * @return a <code>List</code> of history data for the most recent change for specified contest.
     * @throws PersistenceException if any other error occurs.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<ChangeHistoryData> getLatestChanges(long contestId) throws PersistenceException {
        return null;
    }

    /**
     * get milestone submissions for contest
     *
     * @return empty list of none submission found for the given contest id.
     * @param contestId The contest id to get the milestone submissions.
     * @throws IllegalArgumentException if long argument is negative
     * @throws ContestServiceException if any other error occurs
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<SubmissionData> getMilestoneSubmissionsForContest(long contestId) throws ContestServiceException {
        return null;
    }

    /**
     * <p>
     * Gest the MIME type matching the specified context type.
     * </p>
     *
     * @param contentType a <code>String</code> providing the content type to get the matching MIME type for.
     * @return a <code>long</code> providing the ID of MIME type matching the specified content type.
     * @throws PersistenceException if some persistence errors occur.
     * @throws IllegalArgumentWSException if the specified <code>contentType</code> is <code>null</code> or empty.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public long getMimeTypeId(String contentType) throws PersistenceException {
        try
        {
            return facade.getMimeTypeId(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), contentType);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
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
     * Gets the list of all existing contests for contest monitor widget.
     * </p>
     *
     * @return a <code>List</code> listing all existing contests. Empty list is returned if there are no contests found.
     * @throws PersistenceException if any error occurs when getting contest.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<SimpleContestData> getSimpleContestData() throws PersistenceException {
        try
        {
            return facade.getSimpleContestData(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext));
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

    /**
     * <p>
     * Gets the list of all existing contests related to given project for contest monitor widget .
     * </p>
     *
     * @param pid the given project id
     * @return a <code>List</code> listing all existing contests. Empty list is returned if there are no contests found.
     * @throws PersistenceException if any error occurs when getting contest.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<SimpleContestData> getSimpleContestDataByPID(long pid) throws PersistenceException {
        try
        {
            return facade
                .getSimpleContestDataByPID(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), pid);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

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
    public List<SimpleProjectContestData> getSimpleProjectContestData() throws PersistenceException {
        try
        {
            return facade.getSimpleProjectContestData(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext));
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
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
     * <p>
     * Gets existing contest statuses.
     * </p>
     *
     * @return a <code>List</code> listing available contest statuses. Empty list is returned if there are no statuses.
     * @throws PersistenceException if some persistence errors occur.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<ContestStatusData> getStatusList() throws PersistenceException {
        try
        {
            return facade.getStatusList(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext));
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

    /**
     * <p>
     * Gets the list of existing submission types.
     * </p>
     *
     * @return a <code>String</code> providing the comma-separated list of submission types.
     * @throws PersistenceException if some persistence errors occur.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public String getSubmissionFileTypes() throws PersistenceException {
         try
        {
            return facade.getSubmissionFileTypes(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext));
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

    /**
     * Get the user contest by user name Return empty list if none found
     *
     * @param userName the user name to get the user contest
     * @return a list of matching studio competitions
     * @throws IllegalArgumentException if userName is null or empty
     * @throws ContestServiceException if any other error occurs
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<StudioCompetition> getUserContests(String userName) throws ContestServiceException {
        try
        {
            return facade.getUserContests(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), userName);
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
     * <p>
     * Marks the specified submission for purchase.
     * </p>
     *
     * @param submissionId a <code>long</code> providing the ID of a submission to be marked for purchase.
     * @throws PersistenceException if any error occurs when marking for purchase.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public void markForPurchase(long submissionId) throws PersistenceException {
        try
        {
            facade.markForPurchase(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), submissionId);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (PermissionServiceException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
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
     * Processes the contest payment. It does following steps:
     * <ul>
     * <li>Checks contest id to decide whether to create new contest or update existing contest</li>
     * <li>If payment type is credit card then it processes the payment through <code>PaymentProcessor</code></li>
     * <li>Right-now this method doesn't process PO payments.</li>
     * <li>On successful processing -
     * <ul>
     * <li>set contests to CONTEST_STATUS_ACTIVE_PUBLIC = 2</li>
     * <li>set detailed contests to CONTEST_DETAILED_STATUS_ACTIVE_PUBLIC = 2</li>
     * <li>set payment reference number and type</li>
     * <li>Creates new forum for the contest, forum name being contest name. It uses studio service for doing the same.</li>
     * </ul>
     * </li>
     * </ul>
     * </p>
     *
     * @param competition <code>ContestData</code> data that recognizes a contest.
     * @param paymentData <code>PaymentData</code> payment information (credit card/po details) that need to be
     *            processed.
     * @return a <code>PaymentResult</code> result of the payment processing.
     * @throws PersistenceException if any error occurs when getting contest.
     * @throws ContestNotFoundException if contest is not found while update.
     * @throws PaymentException if any errors occurs in processing the payment.
     * @throws IllegalArgumentException if specified <code>filter</code> is <code>null</code> or if it is not supported
     *             by implementor.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public ContestPaymentResult processContestCreditCardPayment(StudioCompetition competition,
            CreditCardPaymentData paymentData) throws PersistenceException, PaymentException, ContestNotFoundException {
        try
        {
            return facade.processContestCreditCardPayment(LoginUtil
                .login(loginBeanURL, loginBeanDSJndiName, sessionContext), competition, paymentData);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (PermissionServiceException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
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
     * Returns ContestPaymentResult instead of PaymentResult
     *
     * @param competition the StudioCompetition instance to process
     * @param paymentData the payment data
     * @return ContestPaymentResult the payment result
     * @throws PersistenceException persistence error occurs
     * @throws PaymentException the payment error occurs
     * @throws ContestNotFoundException if the contest does not exist
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public ContestPaymentResult processContestPurchaseOrderPayment(StudioCompetition competition,
            TCPurhcaseOrderPaymentData paymentData) throws PersistenceException, PaymentException,
            ContestNotFoundException {
        try
        {
            return facade.processContestPurchaseOrderPayment(LoginUtil.login(loginBeanURL, loginBeanDSJndiName,
                sessionContext), competition, paymentData);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (PermissionServiceException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
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
     * Sends payments to <code>PACTS</code> application for all unpaid submissions with a prize already assigned. This
     * service is not atomic. If it fails, you'll have to check what payments where actually done trough the
     * <code>submission.paid</code> flag.
     * </p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to process missing payments for.
     * @throws PersistenceException if any error occurs when processing a payment.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public void processMissingPayments(long contestId) throws PersistenceException {
        try
        {
             facade.processMissingPayments(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), contestId);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (PermissionServiceException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

    /**
     * <p>
     * Purchases the specified submission. E.g. records a fact that submission referenced by specified ID has been paid
     * </p>
     *
     * @param submissionId a <code>long</code> providing the ID of a submission which has been paid for.
     * @param submissionPaymentData a <code>SubmissionPaymentData</code> providing the data of successfully purchased
     *            submission.
     * @param securityToken a <code>String</code> providing the security token to be used for tracking the payment and
     *            prevent fraud.
     * @throws PersistenceException if any error occurs when purchasing submission.
     * @throws IllegalArgumentWSException if specified <code>submissionId</code> is negative.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public void purchaseSubmission(long submissionId, SubmissionPaymentData submissionPaymentData, String securityToken)
            throws PersistenceException {
        try
        {
             facade.purchaseSubmission(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), submissionId,
                submissionPaymentData, securityToken);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (PermissionServiceException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

    /**
     * <p>
     * Ranks the submissions, given submission identifiers in the rank order.
     * </p>
     *
     * @param submissionIdsInRankOrder an array of long submission identifier in the rank order.
     * @return a <code>boolean</code> true if successful, else false.
     * @throws PersistenceException if any error occurs when retrieving/updating the data.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public boolean rankSubmissions(long[] submissionIdsInRankOrder) throws PersistenceException {
        try
        {
             return facade.rankSubmissions(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext),
                submissionIdsInRankOrder);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (PermissionServiceException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

    /**
     * <p>
     * Removes the document referenced by the specified ID.
     * </p>
     *
     * @param documentId a <code>long</code> providing the ID of a document to remove.
     * @return <code>true</code> if requested document was removed successfully; <code>false</code> otherwise.
     * @throws PersistenceException if some persistence errors occur.
     * @throws IllegalArgumentWSException if specified <code>documentId</code> is negative.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public boolean removeDocument(long documentId) throws PersistenceException {
        try
        {
             return facade.removeDocument(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), documentId);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (PermissionServiceException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

    /**
     * <p>
     * Removes the specified document from specified contest.
     * </p>
     *
     * @param document an <code>UploadedDocument</code> representing the document to be removed.
     * @throws PersistenceException if some persistence errors occur.
     * @throws DocumentNotFoundException if the specified document is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the specified argument is <code>null</code>.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public void removeDocumentFromContest(UploadedDocument document) throws PersistenceException,
            DocumentNotFoundException {
        try
        {
             facade.removeDocumentFromContest(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), document);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (PermissionServiceException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

    /**
     * <p>
     * Removes specified submission.
     * </p>
     *
     * @param submissionId a <code>long</code> providing the ID of a submission to remove.
     * @throws PersistenceException if some persistence errors occur.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the <code>submissionId</code> is negative.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public void removeSubmission(long submissionId) throws PersistenceException {
        try
        {
             facade.removeSubmission(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), submissionId);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (PermissionServiceException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
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
     * <p>
     * Gets the submission referenced by the specified ID.
     * </p>
     *
     * @param submissionId a <code>long</code> providing the ID of a submission to get details for.
     * @return a <code>SubmissionData</code> providing details for the submission referenced by the specified ID or
     *         <code>null</code> if such a submission is not found.
     * @throws PersistenceException if any error occurs during the retrieval.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public SubmissionData retrieveSubmission(long submissionId) throws PersistenceException {
        try
        {
             return facade.retrieveSubmission(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext),
                submissionId);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (PermissionServiceException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

    /**
     * <p>
     * Retrieves the list of submissions for the specified contest.
     * </p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to get the list of submissions for.
     * @return a <code>List</code> providing the details for the submissions associated with the specified contest.
     *         Empty list is returned if there are no submissions found.
     * @throws PersistenceException if some persistence errors occur.
     * @throws ContestNotFoundException if requested contest is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the specified ID is negative.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<SubmissionData> retrieveSubmissionsForContest(long contestId) throws PersistenceException,
            ContestNotFoundException {
        try
        {
             return facade.retrieveSubmissionsForContest(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext),
                contestId);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (PermissionServiceException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
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
     * Gets the list of existing contests matching the specified criteria.
     * </p>
     *
     * @param filter a <code>Filter</code> providing the criteria for searching for contests.
     * @return a <code>List</code> listing all existing contests matching the specified criteria. Empty list is returned
     *         if there are no matching contests found.
     * @throws PersistenceException if any error occurs when getting contest.
     * @throws IllegalArgumentException if specified <code>filter</code> is <code>null</code> or if it is not supported
     *             by implementor.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<StudioCompetition> searchContests(ContestServiceFilter filter) throws PersistenceException {
        try
        {
             return facade.searchContests(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), filter);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

    /**
     * <p>
     * Searches the user with the given key.
     * </p>
     *
     * @return list of matching users, empty list if none matches.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<User> searchUser(String key) throws PersistenceException {
        try
        {
             return facade.searchUser(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), key);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

    /**
     * set submission milestone prize If given submission has already been associated with the given milestone prize
     * before, ContestServiceException will be thrown. It's required that the contest field of the submission of given
     * id is one of the contests set of the milestone prize, otherwise, ContestServiceException will be thrown. If the
     * MilestonePrize with given id has reached the max number of submissions, ContestServiceException will be thrown.
     *
     * @param submissionId The submission id
     * @param milestonePrizeId The milestone prize id
     * @throws IllegalArgumentException if long argument is negative
     * @throws ContestServiceException if any other error occurs
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public void setSubmissionMilestonePrize(long submissionId, long milestonePrizeId) throws ContestServiceException {
        try
        {
             facade.setSubmissionMilestonePrize(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext),
                submissionId, milestonePrizeId);
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
     * Sets the placement for the specified submission.
     * </p>
     *
     * @param submissionId a <code>long</code> providing the ID of a submission to set the placement for.
     * @param placement an <code>int</code> providing the submission placement.
     * @throws PersistenceException if any error occurs when setting placement.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public void setSubmissionPlacement(long submissionId, int placement) throws PersistenceException {
        try
        {
             facade.setSubmissionPlacement(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), submissionId,
                placement);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (PermissionServiceException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

    /**
     * <p>
     * Associates the specified submission with the specified prize.
     * </p>
     *
     * @param submissionId a <code>long</code> providing the ID of a submission.
     * @param prizeId a <code>long</code> providing the ID of a prize.
     * @throws PersistenceException if any error occurs when setting submission prize.
     */
    public void setSubmissionPrize(long submissionId, long prizeId) throws PersistenceException {
        try
        {
             facade.setSubmissionPrize(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), submissionId,
                prizeId);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (PermissionServiceException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
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
     * Updates the specified contest.
     * </p>
     *
     * @param contest a <code>StudioCompetition</code> providing the contest data to update.
     * @throws PersistenceException if some persistence errors occur.
     * @throws ContestNotFoundException if requested contest is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if specified <code>contest</code> is <code>null</code>.
     */
    public void updateContest(StudioCompetition contest) throws PersistenceException, ContestNotFoundException {
        try
        {
             facade.updateContest(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), contest);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (PermissionServiceException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

    /**
     * <p>
     * Sets the status of contest referenced by the specified ID to specified value.
     * </p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to update status for.
     * @param newStatusId a <code>long</code> providing the ID of a new contest status to set.
     * @throws PersistenceException if some persistence errors occur.
     * @throws StatusNotAllowedException if the specified status is not allowed to be set for specified contest.
     * @throws StatusNotFoundException if specified status is not found.
     * @throws ContestNotFoundException if specified contest is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if any of specified IDs is negative.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public void updateContestStatus(long contestId, long newStatusId) throws PersistenceException,
            StatusNotAllowedException, StatusNotFoundException,
            ContestNotFoundException {
        try
        {
             facade.updateContestStatus(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), contestId,
                newStatusId);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (PermissionServiceException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
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
     * Updates specified submission.
     * </p>
     *
     * @param submission a <code>SubmissionData</code> providing the data for submission to be updated.
     * @throws PersistenceException if some persistence errors occur.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the specified argument is <code>null</code>.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public void updateSubmission(SubmissionData submission) throws PersistenceException {
        try
        {
             facade.updateSubmission(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), submission);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (PermissionServiceException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

    /**
     * <p>
     * Updates the submission feedback.
     * </p>
     *
     * @param feedbacks an array of <code>SubmissionFeedback</code>
     * @return a <code>boolean</code> true if successful, else false.
     * @throws PersistenceException if any error occurs when retrieving/updating the data.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public boolean updateSubmissionsFeedback(SubmissionFeedback[] feedbacks) throws PersistenceException {
        try
        {
             return facade.updateSubmissionsFeedback(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext),
                feedbacks);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (PermissionServiceException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

    /**
     * <p>
     * Ranks the submissions, given submission identifiers and the rank. If the isRankingMilestone flag is true, the
     * rank will target milestone submissions.
     * </p>
     *
     * @param submissionId identifier of the submission.
     * @param rank rank of the submission.
     * @param isRankingMilestone true if the user is ranking milestone submissions.
     * @return a <code>boolean</code> true if successful, else false.
     * @throws PersistenceException if any error occurs when retrieving/updating the data.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public boolean updateSubmissionUserRank(long submissionId, int rank, Boolean isRankingMilestone)
            throws PersistenceException {
        try
        {
             return facade.updateSubmissionUserRank(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext),
                submissionId, rank, isRankingMilestone);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (PermissionServiceException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

    /**
     * <p>
     * Uploads the specified document without associating it with any contest.
     * </p>
     *
     * @param uploadedDocument an <code>UploadedDocument</code> providing the data for the uploaded document.
     * @return an <code>UploadedDocument</code> passed as argument and having the document ID auto-generated and set.
     * @throws PersistenceException if some persistence errors occur.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the argument is <code>null</code>.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public UploadedDocument uploadDocument(UploadedDocument uploadedDocument) throws PersistenceException {
        try
        {
             return facade.uploadDocument(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext),
                uploadedDocument);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        
    }

    /**
     * <p>
     * Uploads the specified document and associates it with assigned contest.
     * </p>
     *
     * @param uploadedDocument an <code>UploadedDocument</code> providing the data for the uploaded document.
     * @return an <code>UploadedDocument</code> passed as argument and having the document ID auto-generated and set.
     * @throws PersistenceException if some persistence errors occur.
     * @throws ContestNotFoundException if requested contest is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the argument is <code>null</code>.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public UploadedDocument uploadDocumentForContest(UploadedDocument uploadedDocument) throws PersistenceException,
            ContestNotFoundException {
        try
        {
             return facade.uploadDocumentForContest(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext),
                uploadedDocument);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
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
    /**
     * <p>
     * Gets the list of all existing contests related to given project for my
     * project widget.
     * </p>
     * @param pid
     *            given project id
     * @return a <code>List</code> listing all existing contests. Empty list is
     *         returned if there are no contests found.
     * @throws PersistenceException if any error occurs when getting contest.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
	 * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */    
    public List<SimpleProjectContestData> getSimpleProjectContestDataByPID(long pid) throws PersistenceException {
         try
        {
            return facade.getSimpleProjectContestDataByPID(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), pid);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceException(e.getMessage(), e, e.getMessage());
        }
    	
    }

}
