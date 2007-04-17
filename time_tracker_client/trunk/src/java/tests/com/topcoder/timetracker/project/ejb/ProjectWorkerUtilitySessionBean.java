
/* 
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 *
 * ProjectWorkerUtilitySessionBean.java
 */
package com.topcoder.timetracker.project.ejb;
import com.topcoder.db.connectionfactory.*;
import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.timetracker.project.ProjectWorker;
import com.topcoder.timetracker.project.ProjectWorkerFilterFactory;
import com.topcoder.timetracker.project.ProjectWorkerUtility;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.objectfactory.*;

/**
 * <p>
 * This is a Stateless SessionBean that is used to provided business
 * services to manage Project Workers within the Time Tracker Application.
 * It contains the same methods as ProjectWorkerUtility, and delegates to
 * an instance of ProjectWorkerUtility. Transactions for this bean are handled by the EJB Container.
 * It is expected that the transaction level declared in the deployment descriptor for this
 * class will be REQUIRED.
 * </p>
 * <p>
 * All method calls on methods in ProjectWorkerUtility interface
 * are delegated to an instance of ProjectWorkerUtility through the getImpl() method. 
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
 * @poseidon-object-id [I693c885bm1110ffc43d4m1989]
 */
public class ProjectWorkerUtilitySessionBean implements ProjectWorkerUtility, javax.ejb.SessionBean {

/**
 * <p>
 * This is the instance of ProjectWorkerUtility that this session bean delegates
 * all the work to.
 * </p>
 * <p>
 * Initial Value: Initialized in getImpl
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
 * Valid Values: Null values (before init), and non-null ProjectWorkerUtility (after init)
 * </p>
 * 
 * @poseidon-object-id [I780d54b3m111107f05b4mm1d7f]
 */
    private com.topcoder.timetracker.project.ProjectWorkerUtility impl;

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
 * @poseidon-object-id [I780d54b3m111107f05b4mm1d6e]
 */
    private javax.ejb.SessionContext sessionContext;

/**
 * <p>Default Constructor.</p>
 * 
 * @poseidon-object-id [Im572ad119m11110310bf9mm1cec]
 */
    public  ProjectWorkerUtilitySessionBean() {        
        // your code here
    } 

/**
 * <p>
 * This is a business method inherited from ProjectWorkerUtility.  It will
 * simply delegate to the ProjectWorkerUtility class assigned to this.
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
 * @poseidon-object-id [I693c885bm1110ffc43d4m1a79]
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
 * This is a business method inherited from ProjectWorkerUtility.  It will
 * simply delegate to the ProjectWorkerUtility class assigned to this.
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
 * @poseidon-object-id [I693c885bm1110ffc43d4m1a82]
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
 * This is a business method inherited from ProjectWorkerUtility.  It will
 * simply delegate to the ProjectWorkerUtility class assigned to this.
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
 * @poseidon-object-id [I693c885bm1110ffc43d4m1a8b]
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
 * This is a business method inherited from ProjectWorkerUtility.  It will
 * simply delegate to the ProjectWorkerUtility class assigned to this.
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
 * @poseidon-object-id [I693c885bm1110ffc43d4m1a93]
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
 * This is a business method inherited from ProjectWorkerUtility.  It will
 * simply delegate to the ProjectWorkerUtility class assigned to this.
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
 * @poseidon-object-id [I693c885bm1110ffc43d4m1a9a]
 * @return An array of workers currently in the persistent store.
 * @exception DataAccessException if a problem occurs while accessing the data store.
 * @see com.topcoder.timetracker.project.ProjectWorkerUtility#enumerateProjectWorkers()
 */
    public ProjectWorker[] enumerateProjectWorkers() {        
        return null;
    } 

/**
 * <p>
 * This is a business method inherited from ProjectWorkerUtility.  It will
 * simply delegate to the ProjectWorkerUtility class assigned to this.
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
 * @poseidon-object-id [I693c885bm1110ffc43d4m1aa3]
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
 * This is a business method inherited from ProjectWorkerUtility.  It will
 * simply delegate to the ProjectWorkerUtility class assigned to this.
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
 * @poseidon-object-id [I693c885bm1110ffc43d4m1aac]
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
 * This is a business method inherited from ProjectWorkerUtility.  It will
 * simply delegate to the ProjectWorkerUtility class assigned to this.
 * </p>
 * <p>
 * Implementation Notes: Delegate to implementation object with
 * similar method signature.
 * </p>
 * <p>If an exception is thrown, call sessionContext.setRollbackOnly()</p>
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m1ab5]
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
 * This is a business method inherited from ProjectWorkerUtility.  It will
 * simply delegate to the ProjectWorkerUtility class assigned to this.
 * </p>
 * <p>
 * Implementation Notes: Delegate to implementation object with
 * similar method signature.
 * </p>
 * <p>If an exception is thrown, call sessionContext.setRollbackOnly().</p>
 * 
 * @poseidon-object-id [I693c885bm1110ffc43d4m1abc]
 * @return The ProjectWorkerFilterFactory used for building search filters.
 * @see com.topcoder.timetracker.project.ProjectWorkerUtility#getProjectWorkerFilterFactory()
 */
    public ProjectWorkerFilterFactory getProjectWorkerFilterFactory() {        
        return null;
    } 

/**
 * <p>
 * This method has simply been included to complete the SessionBean
 * interface. It has an empty method body.
 * </p><p>There should be two configuration properties in the ConfigManager namespace for this class' fully qualified class name: of_namespace and impl_key.</p>
 * <p> Use impl_key to retrieve the proper object for the impl field from ObjectFactory. </p>
 * 
 * @poseidon-object-id [Im572ad119m11110310bf9mm1d4c]
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
 * @poseidon-object-id [Im572ad119m11110310bf9mm1d34]
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
 * @poseidon-object-id [Im572ad119m11110310bf9mm1d1c]
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
 * @poseidon-object-id [Im572ad119m11110310bf9mm1d04]
 */
    public void ejbRemove() {        
        // your code here
    } 

/**
 * <p>
 * Sets the SessionContext to use for this session.  Called by the EJB container.
 * </p>
 * 
 * @poseidon-object-id [Im572ad119m11110310bf9mmcb5]
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
 * @poseidon-object-id [I780d54b3m111107f05b4mm1d5d]
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
 * @poseidon-object-id [Im138cad73m1113d124762mm1a23]
 * @return impl. Instantiates impl if null
 * @throws DataAccessException if an exception occurs while constructiging the impl variable
 */
    protected com.topcoder.timetracker.project.ProjectWorkerUtility getImpl() {        
        // your code here
        return null;
    } 
 }
