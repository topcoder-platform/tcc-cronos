
/* 
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 *
 * ProjectUtilitySessionBean.java
 */
package com.topcoder.timetracker.project.ejb;
import javax.ejb.SessionBean;

import com.topcoder.db.connectionfactory.*;
import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.timetracker.project.EntryType;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.ProjectFilterFactory;
import com.topcoder.timetracker.project.ProjectUtility;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.objectfactory.*;

/**
 * <p>
 * This is a Stateless SessionBean that is used to provided business
 * services to manage Projects within the Time Tracker Application.
 * It contains the same methods as ProjectUtility, and delegates to
 * an instance of ProjectUtility. Transactions for this bean are handled by the EJB Container.
 * It is expected that the transaction level declared in the deployment descriptor for this
 * class will be REQUIRED.
 * </p>
 * <p>
 *   All method calls on methods in ProjectUtility interface
 * are delegated to an instance of ProjectUtility throug the getImpl() method.
 * The getImpl() method will return the value of the impl field if it is not null,
 * or call the instantiateImpl() method to create an appropriate object from configuration
 * if it is null.
 * </p>
 * <p>
 * All configuration for this class will be read from <env-entry> tags in the deployment descriptor for this bean.
 * </p>
 * <p>
 * Thread Safety:
 *    This class is managed by the EJB Container, which will ensure that it's accessed by no more than one thread at the same time.
 * </p>
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m198a]
 */
public class ProjectUtilitySessionBean implements ProjectUtility, javax.ejb.SessionBean{

/**
 * <p>
 * This is the instance of ProjectUtility that this session bean delegates
 * all the work to.
 * </p>
 * <p>
 * Initial Value: Initialized by getImpl when necessary
 * </p>
 * <p>
 * Accessed In: getImpl
 * </p>
 * <p>
 * Modified In: Not modified after initialization
 * </p>
 * <p>
 * Utilized In: All ProjectUtility methods
 * </p>
 * <p>
 * Valid Values: Null values (before init), and non-null ProjectUtility (after init)
 * </p>
 * 
 * @poseidon-object-id [I780d54b3m111107f05b4mm1e03]
 */
    private com.topcoder.timetracker.project.ProjectUtility impl;

/**
 * <p>
 * This is the instance of SessionContext that was provided by the
 * EJB container.  It is stored and made available to subclasses.
 * </p>
 * <p>
 * Initial Value: Null
 * </p>
 * <p>
 * Accessed In: getSessionContext();
 * </p>
 * <p>
 * Modified In: setSessionContext
 * </p>
 * <p>
 * Utilized In: Not utilized in this class
 * </p>
 * <p>
 * Valid Values: sessionContext objects (possibly null)
 * </p>
 * 
 * @poseidon-object-id [I780d54b3m111107f05b4mm1df2]
 */
    private javax.ejb.SessionContext sessionContext;

/**
 * <p>
 * Default constructor.
 * </p>
 * 
 * @poseidon-object-id [Im572ad119m11110310bf9mm22e5]
 */
    public  ProjectUtilitySessionBean() {        
        // your code here
    } 

/**
 * <p>
 * This is a business method inherited from ProjectUtility.  It will
 * simply delegate to the ProjectUtility class assigned to this.
 * </p>
 * <p>
 * Implementation Notes: Delegate to implementation object with
 * similar method signature.
 * </p>
 * <p>If an exception is thrown, call sessionContext.setRollbackOnly().</p>
 * <p>
 * Modifications for version 3.2: impl must be accessed through getImpl().
 * </p>
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m19f2]
 * @param project The project for which the operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws IllegalArgumentException if project is null.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 * @see com.topcoder.timetracker.project.ProjectUtility#addProject(com.topcoder.timetracker.project.Project, boolean)
 */
    public void addProject(Project project, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This is a business method inherited from ProjectUtility.  It will
 * simply delegate to the ProjectUtility class assigned to this.
 * </p>
 * <p>
 * Implementation Notes: Delegate to implementation object with
 * similar method signature.
 * </p>
 * <p>If an exception is thrown, call sessionContext.setRollbackOnly().</p>
 * <p>
 * Modifications for version 3.2: impl must be accessed through getImpl().
 * </p>
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m19fb]
 * @param project The project for which the operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws IllegalArgumentException if project is null.
 * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 * @see com.topcoder.timetracker.project.ProjectUtility#updateProject(com.topcoder.timetracker.project.Project, boolean)
 */
    public void updateProject(Project project, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This is a business method inherited from ProjectUtility.  It will
 * simply delegate to the ProjectUtility class assigned to this.
 * </p>
 * <p>
 * Implementation Notes: Delegate to implementation object with
 * similar method signature.
 * </p>
 * <p>If an exception is thrown, call sessionContext.setRollbackOnly().</p>
 * <p>
 * Modifications for version 3.2: impl must be accessed through getImpl().
 * </p>
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m1a04]
 * @param projectId The projectId for which the operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
 * @throws IllegalArgumentException if projectId <= 0.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 * @see com.topcoder.timetracker.project.ProjectUtility#removeProject(long, boolean)
 */
    public void removeProject(long projectId, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This is a business method inherited from ProjectUtility.  It will
 * simply delegate to the ProjectUtility class assigned to this.
 * </p>
 * <p>
 * Implementation Notes: Delegate to implementation object with
 * similar method signature.
 * </p>
 * <p>If an exception is thrown, call sessionContext.setRollbackOnly().</p>
 * <p>
 * Modifications for version 3.2: impl must be accessed through getImpl().
 * </p>
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m1a0c]
 * @return The project with specified id.
 * @param projectId The id of the project to retrieve.
 * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 * @see com.topcoder.timetracker.project.ProjectUtility#getProject(long)
 */
    public Project getProject(long projectId) {        
        return null;
    } 

/**
 * <p>
 * This is a business method inherited from ProjectUtility.  It will
 * simply delegate to the ProjectUtility class assigned to this.
 * </p>
 * <p>
 * Implementation Notes: Delegate to implementation object with
 * similar method signature.
 * </p>
 * <p>If an exception is thrown, call sessionContext.setRollbackOnly().</p>
 * <p>
 * Modifications for version 3.2: impl must be accessed through getImpl().
 * </p>
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m1a14]
 * @return The projects satisfying the conditions in the search filter.
 * @param filter The filter used to search for projects.
 * @throws IllegalArgumentException if filter is null.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 * @see com.topcoder.timetracker.project.ProjectUtility#searchProjects(null)
 */
    public Project[] searchProjects(com.topcoder.search.builder.filter.Filter filter) {        
        return null;
    } 

/**
 * <p>
 * This is a business method inherited from ProjectUtility.  It will
 * simply delegate to the ProjectUtility class assigned to this.
 * </p>
 * <p>
 * Implementation Notes: Delegate to implementation object with
 * similar method signature.
 * </p>
 * <p>If an exception is thrown, call sessionContext.setRollbackOnly().</p>
 * <p>
 * Modifications for version 3.2: impl must be accessed through getImpl().
 * </p>
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m1a1d]
 * @param projects An array of projects for which the operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DuplicateEntityException if a project with the same id has already been found.
 * @throws IllegalArgumentExceptionif projects is null or contains null values.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 * @see com.topcoder.timetracker.project.ProjectUtility#addProjects(com.topcoder.timetracker.project.Project[], boolean)
 */
    public void addProjects(Project[] projects, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This is a business method inherited from ProjectUtility.  It will
 * simply delegate to the ProjectUtility class assigned to this.
 * </p>
 * <p>
 * Implementation Notes: Delegate to implementation object with
 * similar method signature.
 * </p>
 * <p>If an exception is thrown, call sessionContext.setRollbackOnly().</p>
 * <p>
 * Modifications for version 3.2: impl must be accessed through getImpl().
 * </p>
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m1a26]
 * @param projects An array of projects for which the operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
 * @throws IllegalArgumentExceptionif projects is null or contains null values.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 * @see com.topcoder.timetracker.project.ProjectUtility#updateProjects(com.topcoder.timetracker.project.Project[], boolean)
 */
    public void updateProjects(Project[] projects, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This is a business method inherited from ProjectUtility.  It will
 * simply delegate to the ProjectUtility class assigned to this.
 * </p>
 * <p>
 * Implementation Notes: Delegate to implementation object with
 * similar method signature.
 * </p>
 * <p>If an exception is thrown, call sessionContext.setRollbackOnly().</p>
 * <p>
 * Modifications for version 3.2: impl must be accessed through getImpl().
 * </p>
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m1a2f]
 * @param projectIds An array of projectIds for which the operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
 * @throws IllegalArgumentException if projectIds is null or contains values <= 0.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 * @see com.topcoder.timetracker.project.ProjectUtility#removeProjects(long[], boolean)
 */
    public void removeProjects(long[] projectIds, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This is a business method inherited from ProjectUtility.  It will
 * simply delegate to the ProjectUtility class assigned to this.
 * </p>
 * <p>
 * Implementation Notes: Delegate to implementation object with
 * similar method signature.
 * </p>
 * <p>If an exception is thrown, call sessionContext.setRollbackOnly().</p>
 * <p>
 * Modifications for version 3.2: impl must be accessed through getImpl().
 * </p>
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m1a37]
 * @return The projects corresponding to the provided ids.
 * @param projectsIds An array of projectIds for which projects should be retrieved.
 * @throws IllegalArgumentException if projectIds is null or contains values <= 0.
 * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 * @see com.topcoder.timetracker.project.ProjectUtility#getProjects(long[])
 */
    public Project[] getProjects(long[] projectsIds) {        
        return null;
    } 

/**
 * <p>
 * This is a business method inherited from ProjectUtility.  It will
 * simply delegate to the ProjectUtility class assigned to this.
 * </p>
 * <p>
 * Implementation Notes: Delegate to implementation object with
 * similar method signature.
 * </p>
 * <p>If an exception is thrown, call sessionContext.setRollbackOnly().</p>
 * <p>
 * Modifications for version 3.2: impl must be accessed through getImpl().
 * </p>
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m1a42]
 * @param projectId The project for which the operation should be performed.
 * @param entryId The id of the entry for which the operation should be performed.
 * @param type The type of the entry.
 * @param audit Indicates whether an audit should be performed.
 * @throws InvalidCompanyException if the entry and project company ids do not match.
 * @throws IllegalArgumentException if any id has a value <= 0.
 * @throws UnrecognizedEntityException if a project or entry with the provided id was not found in the data store.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 * @throws DuplicateEntityException if the entry has already been associated with the project.
 * @see com.topcoder.timetracker.project.ProjectUtility#addEntryToProject(long, long, com.topcoder.timetracker.project.EntryType, boolean)
 */
    public void addEntryToProject(long projectId, long entryId, EntryType type, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This is a business method inherited from ProjectUtility.  It will
 * simply delegate to the ProjectUtility class assigned to this.
 * </p>
 * <p>
 * Implementation Notes: Delegate to implementation object with
 * similar method signature.
 * </p>
 * <p>If an exception is thrown, call sessionContext.setRollbackOnly().</p>
 * <p>
 * Modifications for version 3.2: impl must be accessed through getImpl().
 * </p>
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m1a4d]
 * @param projectId The project for which the operation should be performed.
 * @param entryId The id of the entry for which the operation should be performed.
 * @param type The type of the entry.
 * @param audit Indicates whether an audit should be performed.
 * @throws UnrecognizedEntityException if a project or entry with the provided id was not found in the data store.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 * @throws IllegalArgumentException if either id is <= 0.
 * @see com.topcoder.timetracker.project.ProjectUtility#removeEntryFromProject(long, long, com.topcoder.timetracker.project.EntryType, boolean)
 */
    public void removeEntryFromProject(long projectId, long entryId, EntryType type, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This is a business method inherited from ProjectUtility.  It will
 * simply delegate to the ProjectUtility class assigned to this.
 * </p>
 * <p>
 * Implementation Notes: Delegate to implementation object with
 * similar method signature.
 * </p>
 * <p>If an exception is thrown, call sessionContext.setRollbackOnly().</p>
 * <p>
 * Modifications for version 3.2: impl must be accessed through getImpl().
 * </p>
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m1a56]
 * @return An array of long identifiers of the Entries corresponding to the given project id.
 * @param projectId The id of the project for which the operation should be performed.
 * @param type The type of Entry to be retrieved.
 * @throws IllegalArgumentException if the projectId is <= 0 or if type is null.
 * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 * @see com.topcoder.timetracker.project.ProjectUtility#retrieveEntriesForProject(long, com.topcoder.timetracker.project.EntryType)
 */
    public long[] retrieveEntriesForProject(long projectId, EntryType type) {        
        return null;
    } 

/**
 * <p>
 * This is a business method inherited from ProjectUtility.  It will
 * simply delegate to the ProjectUtility class assigned to this.
 * </p>
 * <p>
 * Implementation Notes: Delegate to implementation object with
 * similar method signature.
 * </p>
 * <p>If an exception is thrown, call sessionContext.setRollbackOnly().</p>
 * <p>
 * Modifications for version 3.2: impl must be accessed through getImpl().
 * </p>
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m1a5f]
 * @return An array of long identifiers of the Entries corresponding to the given client id.
 * @param clientId The id of the client for which this operation should be performed.
 * @param type The type of Entry to be retrieved.
 * @throws IllegalArgumentException if the clientId is <= 0 or if type is null.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 * @see com.topcoder.timetracker.project.ProjectUtility#retrieveEntriesForClient(long, com.topcoder.timetracker.project.EntryType)
 */
    public long[] retrieveEntriesForClient(long clientId, EntryType type) {        
        return null;
    } 

/**
 * <p>
 * This is a business method inherited from ProjectUtility.  It will
 * simply delegate to the ProjectUtility class assigned to this.
 * </p>
 * <p>
 * Implementation Notes: Delegate to implementation object with
 * similar method signature.
 * </p>
 * <p>If an exception is thrown, call sessionContext.setRollbackOnly().</p>
 * <p>
 * Modifications for version 3.2: impl must be accessed through getImpl().
 * </p>
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m1a66]
 * @return the ProjectFilterFactory that is capable of creating SearchFilters to use when searching for projects.
 * @see com.topcoder.timetracker.project.ProjectUtility#getProjectFilterFactory()
 */
    public ProjectFilterFactory getProjectFilterFactory() {        
        return null;
    } 

/**
 * <p>
 * This is a business method inherited from ProjectUtility.  It will
 * simply delegate to the ProjectUtility class assigned to this.
 * </p>
 * <p>
 * Implementation Notes: Delegate to implementation object with
 * similar method signature.
 * </p>
 * <p>If an exception is thrown, call sessionContext.setRollbackOnly().</p>
 * <p>
 * Modifications for version 3.2: impl must be accessed through getImpl().
 * </p>
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m1a6d]
 * @return An array of projects retrieved from the persistent store.
 *  * @throws DataAccessException if a problem occurs while accessing the persistent store.
 * @see com.topcoder.timetracker.project.ProjectUtility#enumerateProjects()
 */
    public Project[] enumerateProjects() {        
        return null;
    } 

/**
 * <p>
 * This method prepares the bean for use by instantiating the impl field as specified in the default configuration file.
 * </p>
 * <p>There should be two configuration properties in the ConfigManager namespace for this class' fully qualified class name: of_namespace and impl_key.</p>
 * <p> Use impl_key to retrieve the proper object for the impl field from ObjectFactory. </p>
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m2029]
 * @throws CreateException if there's a problem instantiating the SessionBean
 */
    public void ejbCreate() {        
        // your code here
    } 

/**
 * <p>
 * This method has simply been included to complete the SessionBean
 * interface. It has an empty method body.
 * </p>
 * 
 * @poseidon-object-id [Im572ad119m11110310bf9mm232d]
 */
    public void ejbActivate() {        
        // your code here
    } 

/**
 * <p>
 * This method has simply been included to complete the SessionBean
 * interface. It has an empty method body.
 * </p>
 * 
 * @poseidon-object-id [Im572ad119m11110310bf9mm2315]
 */
    public void ejbPassivate() {        
        // your code here
    } 

/**
 * <p>
 * This method has simply been included to complete the SessionBean
 * interface. It has an empty method body.
 * </p>
 * 
 * @poseidon-object-id [Im572ad119m11110310bf9mm1cd4]
 */
    public void ejbRemove() {        
        // your code here
    } 

/**
 * <p>
 * Sets the SessionContext to use for this session.  Called by the EJB container.
 * </p>
 * 
 * @poseidon-object-id [Im572ad119m11110310bf9mm22fd]
 * @param ctx The SessionContext to use.
 */
    public void setSessionContext(javax.ejb.SessionContext ctx) {        
        // your code here
    } 

/**
 * <p>
 * Protected method that allows subclasses to access the session context
 * if necessary. 
 * </p>
 * 
 * @poseidon-object-id [I780d54b3m111107f05b4mm1ddf]
 * @return The session context provided by the EJB container.
 */
    protected javax.ejb.SessionContext getSessionContext() {        
        // your code here
        return null;
    } 

/**
 * <p>All access to the impl field should be through this method.</p>
 * <p>Return impl.</p>
 * 
 * 
 * @poseidon-object-id [Im138cad73m1113d124762mm1abb]
 * @return impl. Instantiates impl if null
 * @throws DataAccessException if an exception occurs while constructiging the impl variable
 */
    protected com.topcoder.timetracker.project.ProjectUtility getImpl() {        
        // your code here
        return null;
    } 
 }
