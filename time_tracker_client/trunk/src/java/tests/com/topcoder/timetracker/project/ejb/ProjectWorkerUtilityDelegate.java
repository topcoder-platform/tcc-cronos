
/* 
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 *
 * ProjectWorkerUtilitySessionBean.java
 */
package com.topcoder.timetracker.project.ejb;
import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.timetracker.project.ProjectWorker;
import com.topcoder.timetracker.project.ProjectWorkerFilterFactory;
import com.topcoder.timetracker.project.ProjectWorkerUtility;
import com.topcoder.timetracker.project.ejb.ProjectWorkerUtilityLocalHome;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * This is a Business Delegate/Service Locator that may be used within
 * a J2EE application.  It is responsible for looking up the local interface
 * of the SessionBean for ProjectWorkerUtility, and delegating any calls to
 * the bean.
 * </p>
 * <p>
 * Thread Safety:
 *    - This class is thread safe, since all state is modified at construction.
 * </p>
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m54bd]
 */
public class ProjectWorkerUtilityDelegate implements ProjectWorkerUtility {

/**
 * <p>
 * This is the local interface for the ProjectWorkerUtility business services.
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
 * Utilized In: All ProjectWorkerUtility methods
 * </p>
 * <p>
 * Valid Values: Non-null ProjectWorkerUtility (after init)
 * </p>
 * 
 * @poseidon-object-id [I45874971m11110c434f3mm1e7a]
 */
    private final com.topcoder.timetracker.project.ejb.ProjectWorkerUtilityLocal projectWorkerUtility;

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
 * @poseidon-object-id [I780d54b3m111107f05b4mm1d0a]
 * @param namespace The namespace to use.
 * @throws IllegalArgumentException if namespace is null or an empty String.
 * @throws ConfigurationException if a problem occurs while constructing the instance.
 */
    public ProjectWorkerUtilityDelegate(String namespace) {        
        this.projectWorkerUtility = null;
    } 

/**
 * <p>
 * This is a business method inherited from ProjectManagerUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m55ab]
 * @param worker The worker for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws IllegalArgumentException if worker is null.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @see com.topcoder.timetracker.project.ProjectWorkerUtility#addProjectWorker(com.topcoder.timetracker.project.ProjectWorker, boolean)
 */
    public void addProjectWorker(ProjectWorker worker, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This is a business method inherited from ProjectManagerUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m55b4]
 * @param worker The worker for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws UnrecognizedEntityException if a worker with the given id was not found in the datastore.
 * @throws IllegalArgumentException if worker is null.
 * @see com.topcoder.timetracker.project.ProjectWorkerUtility#updateProjectWorker(com.topcoder.timetracker.project.ProjectWorker, boolean)
 */
    public void updateProjectWorker(ProjectWorker worker, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This is a business method inherited from ProjectManagerUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m55bd]
 * @param projectWorkerId The id of the worker to remove.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws UnrecognizedEntityException if a worker with the given id was not found in the datastore.
 * @throws IllegalArgumentExceptino if the id is <= 0.
 * @see com.topcoder.timetracker.project.ProjectWorkerUtility#removeProjectWorker(long, boolean)
 */
    public void removeProjectWorker(long projectWorkerId, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This is a business method inherited from ProjectManagerUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m55c5]
 * @return An array of workers satisfying the criteria.
 * @param filter The filter containing the search criteria.
 * @throws IllegalArgumentException if filter is null.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @see com.topcoder.timetracker.project.ProjectWorkerUtility#searchProjectWorkers(null)
 */
    public ProjectWorker[] searchProjectWorkers(com.topcoder.search.builder.filter.Filter filter) {        
        return null;
    } 

/**
 * <p>
 * This is a business method inherited from ProjectManagerUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m55cc]
 * @return An array of workers currently in the persistent store.
 * @exception DataAccessException if a problem occurs while accessing the data store.
 * @see com.topcoder.timetracker.project.ProjectWorkerUtility#enumerateProjectWorkers()
 */
    public ProjectWorker[] enumerateProjectWorkers() {        
        return null;
    } 

/**
 * <p>
 * This is a business method inherited from ProjectManagerUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m55d5]
 * @param workers An array of workers for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws IllegalArgumentException if workers is null or contains null elements.
 * @see com.topcoder.timetracker.project.ProjectWorkerUtility#addProjectWorkers(com.topcoder.timetracker.project.ProjectWorker[], boolean)
 */
    public void addProjectWorkers(ProjectWorker[] workers, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This is a business method inherited from ProjectManagerUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m55de]
 * @param workers An array of workers for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws UnrecognizedEntityException if a worker with the given id was not found in the datastore.
 * @throws IllegalArgumentException if workers is null or contains null elements.
 * @see com.topcoder.timetracker.project.ProjectWorkerUtility#updateProjectWorkers(com.topcoder.timetracker.project.ProjectWorker[], boolean)
 */
    public void updateProjectWorkers(ProjectWorker[] workers, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This is a business method inherited from ProjectManagerUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m55e7]
 * @param projectWorkerIds An array of workerIds for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws UnrecognizedEntityException if a worker with the given id was not found in the datastore.
 * @throws IllegalArgumentException if the aray is null or contains values <=0.
 * @see com.topcoder.timetracker.project.ProjectWorkerUtility#removeProjectWorkers(long[], boolean)
 */
    public void removeProjectWorkers(long[] projectWorkerIds, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This is a business method inherited from ProjectManagerUtility.
 * This method simply delegates to the local object.
 * </p>
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m55ee]
 * @return The ProjectWorkerFilterFactory used for building search filters.
 * @see com.topcoder.timetracker.project.ProjectWorkerUtility#getProjectWorkerFilterFactory()
 */
    public ProjectWorkerFilterFactory getProjectWorkerFilterFactory() {        
        return null;
    } 
 }
