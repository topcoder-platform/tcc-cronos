
/* 
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 *
 * ProjectManagerUtilitySessionBean.java
 */
package com.topcoder.timetracker.project.ejb;
import com.topcoder.db.connectionfactory.*;
import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.timetracker.project.ProjectManager;
import com.topcoder.timetracker.project.ProjectManagerFilterFactory;
import com.topcoder.timetracker.project.ProjectManagerUtility;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.objectfactory.*;

/**
 * <p>
 * This is a Stateless SessionBean that is used to provided business
 * services to manage ProjectManagers within the Time Tracker Application.
 * It contains the same methods as ProjectManagerUtility, and delegates to
 * an instance of ProjecManagertUtility. Transactions for this bean are handled by the EJB Container.
 * It is expected that the transaction level declared in the deployment descriptor for this
 * class will be REQUIRED.
 * </p>
 * <p>
 * All method calls on methods in ProjectManagerUtility interface
 * are delegated to an instance of ProjectManagerUtility through the getImpl() method.The getImpl() method will return the value of the impl field if it is not null,
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
 * @poseidon-object-id [I693c885bm1110ffc43d4m1988]
 */
public class ProjectManagerUtilitySessionBean implements ProjectManagerUtility, javax.ejb.SessionBean {

/**
 * <p>
 * This is the instance of ProjectManagerUtility that this session bean delegates
 * all the work to.
 * </p>
 * <p>
 * Initial Value: Initialized in getImpl when needed
 * </p>
 * <p>
 * Accessed In: getImpl
 * </p>
 * <p>
 * Modified In: Not modified after initialization
 * </p>
 * <p>
 * Utilized In: All ProjectManagerUtility methods
 * </p>
 * <p>
 * Valid Values: Null values (before init), and non-null ProjectManagerUtility (after init)
 * </p>
 * 
 * @poseidon-object-id [I780d54b3m111107f05b4mm1db9]
 */
    private com.topcoder.timetracker.project.ProjectManagerUtility impl;

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
 * @poseidon-object-id [I780d54b3m111107f05b4mm1da8]
 */
    private javax.ejb.SessionContext sessionContext;

/**
 * <p>Default Constructor.</p>
 * 
 * @poseidon-object-id [Im572ad119m11110310bf9mm1cbc]
 */
    public  ProjectManagerUtilitySessionBean() {        
        // your code here
    } 

/**
 * <p>
 * This is a business method inherited from ProjectManagerUtility.  It will
 * simply delegate to the ProjectManagerUtility class assigned to this.
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
 * @poseidon-object-id [I693c885bm1110ffc43d4m19a3]
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
 * This is a business method inherited from ProjectManagerUtility.  It will
 * simply delegate to the ProjectManagerUtility class assigned to this.
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
 * @poseidon-object-id [I693c885bm1110ffc43d4m19ac]
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
 * This is a business method inherited from ProjectManagerUtility.  It will
 * simply delegate to the ProjectManagerUtility class assigned to this.
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
 * @poseidon-object-id [I693c885bm1110ffc43d4m19b5]
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
 * This is a business method inherited from ProjectManagerUtility.  It will
 * simply delegate to the ProjectManagerUtility class assigned to this.
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
 * @poseidon-object-id [I693c885bm1110ffc43d4m19bd]
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
 * This is a business method inherited from ProjectManagerUtility.  It will
 * simply delegate to the ProjectManagerUtility class assigned to this.
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
 * @poseidon-object-id [I693c885bm1110ffc43d4m19c4]
 * @return An array of managers currently in the persistent store.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @see com.topcoder.timetracker.project.ProjectManagerUtility#enumerateProjectManagers()
 */
    public ProjectManager[] enumerateProjectManagers() {        
        return null;
    } 

/**
 * <p>
 * This is a business method inherited from ProjectManagerUtility.  It will
 * simply delegate to the ProjectManagerUtility class assigned to this.
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
 * @poseidon-object-id [I693c885bm1110ffc43d4m19cd]
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
 * This is a business method inherited from ProjectManagerUtility.  It will
 * simply delegate to the ProjectManagerUtility class assigned to this.
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
 * @poseidon-object-id [I693c885bm1110ffc43d4m19d6]
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
 * This is a business method inherited from ProjectManagerUtility.  It will
 * simply delegate to the ProjectManagerUtility class assigned to this.
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
 * @poseidon-object-id [I693c885bm1110ffc43d4m19df]
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
 * This is a business method inherited from ProjectManagerUtility.  It will
 * simply delegate to the ProjectManagerUtility class assigned to this.
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
 * @poseidon-object-id [I693c885bm1110ffc43d4m19e6]
 * @return The ProjectManagerFilterFactory used for building search filters.
 * @see com.topcoder.timetracker.project.ProjectManagerUtility#getProjectManagerFilterFactory()
 */
    public ProjectManagerFilterFactory getProjectManagerFilterFactory() {        
        return null;
    } 

/**
 * <p>
 * This method has simply been included to complete the SessionBean
 * interface. It has an empty method body.
 * </p>
 * <p>There should be two configuration properties in the ConfigManager namespace for this class' fully qualified class name: of_namespace and impl_key.</p>
 * <p> Use impl_key to retrieve the proper object for the impl field from ObjectFactory. </p>
 * 
 * @poseidon-object-id [Im572ad119m11110310bf9mm1dc4]
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
 * @poseidon-object-id [Im572ad119m11110310bf9mm1dac]
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
 * @poseidon-object-id [Im572ad119m11110310bf9mm1d94]
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
 * @poseidon-object-id [Im572ad119m11110310bf9mm1d7c]
 */
    public void ejbRemove() {        
        // your code here
    } 

/**
 * <p>
 * Sets the SessionContext to use for this session.  Called by the EJB container.
 * </p>
 * 
 * @poseidon-object-id [Im572ad119m11110310bf9mm1d64]
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
 * @poseidon-object-id [I780d54b3m111107f05b4mm1d97]
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
 * @poseidon-object-id [Im138cad73m1113d124762mm1a6f]
 * @return impl. Instantiates impl if null
 * @throws DataAccessException if an exception occurs while constructiging the impl variable
 */
    private com.topcoder.timetracker.project.ProjectManagerUtility getImpl() {        
        // your code here
        return null;
    } 
 }
