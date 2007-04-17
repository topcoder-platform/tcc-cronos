
/* 
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 *
 * ProjectUtilitySessionBean.java
 */
package com.topcoder.timetracker.project.ejb;
import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.timetracker.project.EntryType;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.ProjectFilterFactory;
import com.topcoder.timetracker.project.ProjectUtility;
import com.topcoder.timetracker.project.ejb.ProjectUtilityLocalHome;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * This is a Business Delegate/Service Locator that may be used within
 * a J2EE application.  It is responsible for looking up the local interface
 * of the SessionBean for ProjectUtility, and delegating any calls to
 * the bean.
 * </p>
 * <p>
 * Thread Safety:
 *    - This class is thread safe, since all state is modified at construction.
 * </p>
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m54be]
 */
public class ProjectUtilityDelegate implements ProjectUtility {

/**
 * <p>
 * This is the local interface for the ProjectUtility business services.
 * All business calls are delegated here.
 * </p>
 * <p>
 * Initial Value: Initialized in Constructor
 * </p>
 * <p>
 * Accessed In: Not Accessed
 * </p>
 * <p>
 * Modified In: Not modified after initialization
 * </p>
 * <p>
 * Utilized In: All ProjectUtility methods
 * </p>
 * <p>
 * Valid Values: Non-null ProjectUtility (after init)
 * </p>
 * 
 * @poseidon-object-id [I45874971m11110c434f3mm1e9c]
 */
    private final com.topcoder.timetracker.project.ejb.ProjectUtilityLocal projectUtility;

/**
 * <p>
 * Constructor that accepts configuration from the specified ConfigManager
 * namespace.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Load the 'context_name' property (if any) and create an InitialContext from
 *      given property (Using JNDIUtils).
 *    - Load the 'jndi_home' property
 *    - Use the given context to find and create the Home object to use.
 *    - Use the home object to create the local object and store it as instance variable.
 * </p>
 * 
 * @poseidon-object-id [I780d54b3m111107f05b4mm2170]
 * @param namespace The namespace to use.
 * @throws IllegalArgumentException if namespace is null or an empty String.
 * @throws ConfigurationException if a problem occurs while constructing the instance.
 */
    public  ProjectUtilityDelegate(String namespace) {        
        this.projectUtility = null;
    } 

/**
 * <p>
 * This is a business method inherited from ProjectUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m5524]
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
 * This is a business method inherited from ProjectUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m552d]
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
 * This is a business method inherited from ProjectUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m5536]
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
 * This is a business method inherited from ProjectUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m553e]
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
 * This is a business method inherited from ProjectUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m5546]
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
 * This is a business method inherited from ProjectUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m554f]
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
 * This is a business method inherited from ProjectUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m5558]
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
 * This is a business method inherited from ProjectUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m5561]
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
 * This is a business method inherited from ProjectUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m5569]
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
 * This is a business method inherited from ProjectUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m5574]
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
 * This is a business method inherited from ProjectUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m557f]
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
 * This is a business method inherited from ProjectUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m5588]
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
 * This is a business method inherited from ProjectUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m5591]
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
 * This is a business method inherited from ProjectUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m5598]
 * @return the ProjectFilterFactory that is capable of creating SearchFilters to use when searching for projects.
 * @see com.topcoder.timetracker.project.ProjectUtility#getProjectFilterFactory()
 */
    public ProjectFilterFactory getProjectFilterFactory() {        
        return null;
    } 

/**
 * <p>
 * This is a business method inherited from ProjectUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m559f]
 * @return An array of projects retrieved from the persistent store.
 *  * @throws DataAccessException if a problem occurs while accessing the persistent store.
 * @see com.topcoder.timetracker.project.ProjectUtility#enumerateProjects()
 */
    public Project[] enumerateProjects() {        
        return null;
    }
 }
