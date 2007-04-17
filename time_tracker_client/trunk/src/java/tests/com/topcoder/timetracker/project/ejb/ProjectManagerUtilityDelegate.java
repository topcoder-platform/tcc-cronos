
/* 
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 *
 * ProjectManagerUtilitySessionBean.java
 */
package com.topcoder.timetracker.project.ejb;
import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.timetracker.project.ProjectManager;
import com.topcoder.timetracker.project.ProjectManagerFilterFactory;
import com.topcoder.timetracker.project.ProjectManagerUtility;
import com.topcoder.timetracker.project.ejb.ProjectManagerUtilityLocalHome;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * This is a Business Delegate/Service Locator that may be used within
 * a J2EE application.  It is responsible for looking up the local interface
 * of the SessionBean for ProjectManagerUtility, and delegating any calls to
 * the bean.
 * </p>
 * <p>
 * Thread Safety:
 *    - This class is thread safe, since all state is modified at construction.
 * </p>
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m54bf]
 */
public class ProjectManagerUtilityDelegate implements ProjectManagerUtility {

/**
 * <p>
 * This is the local interface for the ProjectManagerUtility business services.
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
 * Utilized In: All ProjectManagerUtility methods
 * </p>
 * <p>
 * Valid Values: Non-null ProjectManagerUtility (after init)
 * </p>
 * 
 * @poseidon-object-id [I45874971m11110c434f3mm1e8b]
 */
    private final com.topcoder.timetracker.project.ejb.ProjectManagerUtilityLocal projectManagerUtility;

/**
 * <p>
 * Constructor that accepts configuration from the specified ConfigManager
 * namespace.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Load the 'contextName' property (if any) and create an InitialContext from
 *      given property (Using JNDIUtils).
 *    - Use the given context to find and create the Home object to use.
 *    - Use the home object to create the local object and store it as instance variable.
 * </p>
 * 
 * @poseidon-object-id [I780d54b3m111107f05b4mm1d2c]
 * @param namespace The namespace to use.
 * @throws IllegalArgumentException if namespace is null or an empty String.
 * @throws ConfigurationException if a problem occurs while constructing the instance.
 */
    public  ProjectManagerUtilityDelegate(String namespace) {        
        this.projectManagerUtility = null;
    } 

/**
 * <p>
 * This is a business method inherited from ProjectManagerUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m54d5]
 * @param manager The manager for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws IllegalArgumentException if manager is null.
 * @see com.topcoder.timetracker.project.ProjectManagerUtility#addProjectManager(com.topcoder.timetracker.project.ProjectManager, boolean)
 */
    public void addProjectManager(ProjectManager manager, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This is a business method inherited from ProjectManagerUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m54de]
 * @param manager The manager for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws IllegalArgumentException if manager is null.
 * @throws UnrecognizedEntityException if a manager with the given id was not found in the persistent store.
 * @see com.topcoder.timetracker.project.ProjectManagerUtility#updateProjectManager(com.topcoder.timetracker.project.ProjectManager, boolean)
 */
    public void updateProjectManager(ProjectManager manager, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This is a business method inherited from ProjectManagerUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m54e7]
 * @param managerId The id of the manager to remove.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws IllegalArgumentException if managerId is a value <= 0.
 * @throws UnrecognizedEntityException if a manager with the given id was not found in the persistent store.
 * @see com.topcoder.timetracker.project.ProjectManagerUtility#removeProjectManager(long, boolean)
 */
    public void removeProjectManager(long managerId, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This is a business method inherited from ProjectManagerUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m54ef]
 * @return An array of managers satisfying the criteria.
 * @param filter The filter containing the search criteria.
 * @throws IllegalArgumentException if filter is null.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @see com.topcoder.timetracker.project.ProjectManagerUtility#searchProjectManagers(null)
 */
    public ProjectManager[] searchProjectManagers(com.topcoder.search.builder.filter.Filter filter) {        
        return null;
    } 

/**
 * <p>
 * This is a business method inherited from ProjectManagerUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m54f6]
 * @return An array of managers currently in the persistent store.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @see com.topcoder.timetracker.project.ProjectManagerUtility#enumerateProjectManagers()
 */
    public ProjectManager[] enumerateProjectManagers() {        
        return null;
    } 

/**
 * <p>
 * This is a business method inherited from ProjectManagerUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m54ff]
 * @param managers An array of managers for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws IllegalArgumentException if managers is null or contains null elements.
 * @see com.topcoder.timetracker.project.ProjectManagerUtility#addProjectManagers(com.topcoder.timetracker.project.ProjectManager[], boolean)
 */
    public void addProjectManagers(ProjectManager[] managers, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This is a business method inherited from ProjectManagerUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m5508]
 * @param managers An array of managers for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws IllegalArgumentException if managers is null or contains null elements.
 * @throws UnrecognizedEntityException if a manager with the given id was not found in the persistent store.
 * @see com.topcoder.timetracker.project.ProjectManagerUtility#updateProjectManagers(com.topcoder.timetracker.project.ProjectManager[], boolean)
 */
    public void updateProjectManagers(ProjectManager[] managers, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This is a business method inherited from ProjectManagerUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m5511]
 * @param managerIds An array of managerIds for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws IllegalArgumentException if the aray is null or contains values <=0.
 * @throws UnrecognizedEntityException if a manager with the given id was not found in the persistent store.
 * @see com.topcoder.timetracker.project.ProjectManagerUtility#removeProjectManagers(long[], boolean)
 */
    public void removeProjectManagers(long[] managerIds, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This is a business method inherited from ProjectManagerUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m5518]
 * @return The ProjectManagerFilterFactory used for building search filters.
 * @see com.topcoder.timetracker.project.ProjectManagerUtility#getProjectManagerFilterFactory()
 */
    public ProjectManagerFilterFactory getProjectManagerFilterFactory() {        
        return null;
    }
 }
