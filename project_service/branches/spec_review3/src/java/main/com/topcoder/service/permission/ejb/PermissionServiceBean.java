/*
 * Copyright (C) 2008-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.permission.ejb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import com.topcoder.service.permission.Helper;
import com.topcoder.service.permission.Permission;
import com.topcoder.service.permission.PermissionServiceRemote;
import com.topcoder.service.permission.PermissionServiceLocal;
import com.topcoder.service.permission.PermissionServiceException;
import com.topcoder.service.permission.PermissionType;
import com.topcoder.service.permission.ProjectPermission;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * It provides CRUD on permission object.
 * </p>
 * <p>
 * Version 1.1(Cockpit Security Facade V1.0)
 * - Remove RunAs("Cockpit Administrator"), RolesAllowed("Cockpit User")
 *   and DeclareRoles( { "Cockpit User", "Cockpit Administrator" })
 * </p>
 *
 *
 * <p>
 * Version 1.2 (Direct Permissions Setting Back-end and Integration Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #deletePermission(long, long, EntityManager)} method.</li>
 *     <li>Added {@link #getProjectPermissions(long)} method.</li>
 *     <li>Added {@link #updateProjectPermissions(java.util.List, long)} method.</li>
 *     <li>Added {@link #getProjectLevelFullPermissions(long, EntityManager)} method.</li>
 *     <li>Added {@link #getProjectLevelPermissions(long, EntityManager)} method.</li>
 *     <li>Added {@link #PROJECT_PERMISSION_TYPES} constant.</li>
 *   </ol>
 * </p>

 * @author waits, TCSDEVELOPER
 *
 * @since Cockpit Project Admin Release Assembly v1.0
 * @version 1.2
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Stateless
public class PermissionServiceBean implements PermissionServiceRemote,  PermissionServiceLocal{

    /**
     * <p>A <code>String</code> array listing literal presentations of the supported project permission types.</p>
     *
     * @since 1.2
     */
    private static String[] PROJECT_PERMISSION_TYPES = {"read", "write", "full"};

    /**
     * <p>
     * Represents the sessionContext of the EJB.
     * </p>
     */
    @Resource
    private SessionContext sessionContext;

    /**
     * <p>
     * This field represents the persistence unit name to lookup the <code>EntityManager</code> from the
     * <code>SessionContext</code>. It is initialized in the <code>initialize</code> method, and never changed
     * afterwards. It must be non-null, non-empty string.
     * </p>
     */
    @Resource(name = "unitName")
    private String unitName;

    /**
     * <p>
     * Represents the loggerName used to retrieve the logger.
     * </p>
     */
    @Resource(name = "logName")
    private String logName;


    /**
     * <p>
     * Represents the log used to log the methods logic of this class.
     * </p>
     */
    private Log logger;

    /**
     * A default empty constructor.
     */
    public PermissionServiceBean() {
    }

    /**
     * <p>
     * This is method is performed after the construction of the bean, at this point all the bean's resources will be
     * ready.
     * </p>
     */
    @PostConstruct
    public void init() {
        if (logName != null) {
            if (logName.trim().length() == 0) {
                throw new IllegalStateException("logName parameter not supposed to be empty.");
            }

            logger = LogManager.getLog(logName);
        }

        // first record in logger
        logExit("init");
    }

    /**
     * <p>
     * This method retrieve all the permissions that the user owned for any projects. Returns empty list if no
     * permission found.
     * </p>
     *
     * @param userid
     *            user id to look for
     *
     * @return all the permissions that the user owned for any projects.
     *
     * @throws PermissionServiceException
     *             if any error occurs when getting permissions.
     *
     * @since Cockpit Project Admin Release Assembly v1.0
     */
    public List<Permission> getPermissionsByUser(long userid) throws PermissionServiceException {
        try {
            logEnter("getPermissionsByUser(userid)");
            logOneParameter(userid);

            EntityManager em = getEntityManager();

            Query query = em.createQuery("select p from com.topcoder.service.permission.Permission p where p.userId = " + userid);

            List<Permission> result = query.getResultList();

            return result;
        } catch (IllegalStateException e) {
            throw wrapPermissionServiceException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapPermissionServiceException(e, "There are errors while retrieving the permissions.");
        } finally {
            logExit("getPermissionsByUser(userid)");
        }
    }

    /**
     * <p>
     * This method retrieve all the permissions that the user own for a given project. Returns empty list if no
     * permission found.
     * </p>
     *
     * @param userid
     *            user id to look for
     * @param resourceId
     *            project id to look for
     *
     * @return all the permissions that the user own for a given project.
     *
     * @throws PermissionServiceException
     *             if any error occurs when getting permissions.
     *
     * @since Cockpit Project Admin Release Assembly v1.0
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Permission> getPermissions(long userid, long resourceId) throws PermissionServiceException {
        try {
            logEnter("getPermissions(userid, resourceId)");
            logTwoParameters(userid, resourceId);

            EntityManager em = getEntityManager();

            return getPermissions(userid, resourceId, em);
        } catch (IllegalStateException e) {
            throw wrapPermissionServiceException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapPermissionServiceException(e, "There are errors while retrieving the permissions.");
        } finally {
            logExit("getPermissions(userid, resourceId)");
        }
    }


    /**
     * <p>
     * This method retrieve all the permissions that the user own for a given project. Returns empty list if no
     * permission found.
     * </p>
     *
     * @param userid
     *            user id to look for
     * @param resourceId
     *            project id to look for
     *
     * @return all the permissions that the user own for a given project.
     *
     * @throws PermissionServiceException
     *             if any error occurs when getting permissions.
     *
     * @since Cockpit Project Admin Release Ass
     */
    private List<Permission> getPermissions(long userid, long resourceId, EntityManager em) throws PermissionServiceException {
        try {
            logEnter("getPermissions(userid, resourceId)");
            logTwoParameters(userid, resourceId);

            Query query = em.createQuery("select p from com.topcoder.service.permission.Permission p where p.userId = " + userid
                    + " and p.resourceId = " + resourceId);

            List<Permission> result = query.getResultList();

            return result;
        } catch (IllegalStateException e) {
            throw wrapPermissionServiceException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapPermissionServiceException(e, "There are errors while retrieving the permissions.");
        } finally {
            logExit("getPermissions(userid, resourceId)");
        }
    }

    /**
     * <p>
     * This method retrieve all the permissions that various users own for a given project. Returns empty list if no
     * permission found.
     * </p>
     *
     * @param resourceId
     *            project id to look for
     *
     * @return all the permissions that various users own for a given project.
     *
     * @throws PermissionServiceException
     *             if any error occurs when getting permissions.
     *
     * @since Cockpit Share Submission Integration
     */
    public List<Permission> getPermissionsByProject(long resourceId) throws PermissionServiceException {
        try {
            logEnter("getPermissionsByProject(resourceId)");
            logOneParameter(resourceId);

            EntityManager em = getEntityManager();

            Query query = em.createQuery("select p from com.topcoder.service.permission.Permission p where p.resourceId = " + resourceId);

            List<Permission> result = query.getResultList();

            return result;
        } catch (IllegalStateException e) {
            throw wrapPermissionServiceException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapPermissionServiceException(e, "There are errors while retrieving the permissions.");
        } finally {
            logExit("getPermissionsByProject(resourceId)");
        }
    }

    /**
     * <p>
     * This method retrieve all the permission types.
     * </p>
     *
     * @return all the permission types.
     *
     * @throws PermissionServiceException
     *             if any error occurs when getting permission types.
     *
     * @since Cockpit Project Admin Release Assembly v1.0
     */

    public List<PermissionType> getAllPermissionType() throws PermissionServiceException {
        try {
            logEnter("getAllPermissionType()");

            return getAll(PermissionType.class);

        } finally {
            logExit("getAllPermissionType()");
        }
    }

    /**
     * <p>
     * This method will add a permission type, and return the added type entity.
     * </p>
     *
     * @param type
     *            the permission type to add.
     *
     * @return the added permission type entity
     *
     * @throws IllegalArgumentException
     *             if the arg is null.
     * @throws EntityAlreadyExistsException
     *             if the entity already exists in the persistence.
     * @throws PermissionServiceException
     *             if any error occurs when adding the permission type.
     *
     * @since Cockpit Project Admin Release Assembly v1.0
     */
    public PermissionType addPermissionType(PermissionType type) throws PermissionServiceException {
        try {
            logEnter("addPermissionType(type)");
            Helper.checkNull(type, "type");
            logOneParameter(type.getPermissionTypeId());

            EntityManager em = getEntityManager();

            if (type.getPermissionTypeId() != null && em.find(PermissionType.class, type.getPermissionTypeId()) != null) {
                throw wrapPermissionServiceException("The permission type with id '" + type.getPermissionTypeId()
                        + "' already exists.");
            }

            em.persist(type);

            return type;
        } catch (IllegalStateException e) {
            throw wrapPermissionServiceException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapPermissionServiceException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapPermissionServiceException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("addPermissionType(type)");
        }
    }

    /**
     * <p>
     * This method will add permission data, and return the added permission data.
     * </p>
     *
     * @param permission
     *            the permission to add.
     *
     * @return the added permission entity
     *
     * @throws IllegalArgumentException
     *             if the arg is null.
     * @throws EntityAlreadyExistsException
     *             if the entity already exists in the persistence.
     * @throws PermissionServiceException
     *             if any error occurs when adding the permission.
     *
     * @since Cockpit Project Admin Release Assembly v1.0
     */
    private Permission addPermission(Permission permission, EntityManager em) throws PermissionServiceException {
        try {
            logEnter("addPermission(permission)");
            Helper.checkNull(permission, "permission");
            logOneParameter(permission.getPermissionId());

            if (permission.getPermissionId() != null && em.find(Permission.class, permission.getPermissionId()) != null) {
                throw wrapPermissionServiceException("The permission with id '" + permission.getPermissionId()
                        + "' already exists.");
            }

            em.persist(permission);

            return permission;
        } catch (IllegalStateException e) {
            throw wrapPermissionServiceException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapPermissionServiceException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapPermissionServiceException(e, "There are errors while persisting the entity.");
        } finally {
            logExit("addPermission(permission)");
        }
    }

    /**
     * <p>
     * This method will update permission type data.
     * </p>
     *
     * @param type
     *            the permission type to update.
     *
     * @throws IllegalArgumentException
     *             if the arg is null.
     * @throws EntityNotFoundException
     *             if the entity parameter doesn't exist in persistence.
     * @throws PermissionServiceException
     *             if any error occurs when updating the permission type.
     *
     * @since Cockpit Project Admin Release Assembly v1.0
     */
    public void updatePermissionType(PermissionType type) throws PermissionServiceException {
        try {
            logEnter("updatePermissionType(type)");
            Helper.checkNull(type, "type");
            logOneParameter(type.getPermissionTypeId());

            EntityManager em = getEntityManager();

            if (type.getPermissionTypeId() == null || em.find(PermissionType.class, type.getPermissionTypeId()) == null) {
                throw wrapPermissionServiceException("The permission type with id '" + type.getPermissionTypeId()
                        + "' doesn't exist");
            }

            em.merge(type);
        } catch (IllegalStateException e) {
            throw wrapPermissionServiceException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapPermissionServiceException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapPermissionServiceException(e, "There are errors while updating the entity.");
        } finally {
            logExit("updatePermissionType(type)");
        }
    }

    /**
     * <p>
     * This method will update permission data.
     * </p>
     *
     * @param permission
     *            the permission to update.
     *
     * @throws IllegalArgumentException
     *             if the arg is null.
     * @throws EntityNotFoundException
     *             if the entity parameter doesn't exist in persistence.
     * @throws PermissionServiceException
     *             if any error occurs when updating the permission.
     *
     * @since Cockpit Project Admin Release Assembly v1.0
     */
    private void updatePermission(Permission permission, EntityManager em) throws PermissionServiceException {
        try {
            logEnter("updatePermission(permission)");
            Helper.checkNull(permission, "permission");
            logOneParameter(permission.getPermissionId());

            if (permission.getPermissionId() == null || em.find(Permission.class, permission.getPermissionId()) == null) {
                throw wrapPermissionServiceException("The permission with id '" + permission.getPermissionId()
                        + "' doesn't exist");
            }

            em.merge(permission);
        } catch (IllegalStateException e) {
            throw wrapPermissionServiceException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapPermissionServiceException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapPermissionServiceException(e, "There are errors while updating the entity.");
        } finally {
            logExit("updatePermission(permission)");
        }
    }

    /**
     * <p>
     * This method will update permission type data, return true if the permission type data exists and removed
     * successfully, return false if it doesn't exist.
     * </p>
     *
     * @param typeid
     *            the permission type to delete.
     *
     * @return true if the permission type data exists and removed successfully.
     *
     * @throws PermissionServiceException
     *             if any error occurs when deleting the permission.
     *
     * @since Cockpit Project Admin Release Assembly v1.0
     */
    public boolean deletePermissionType(long typeid) throws PermissionServiceException {
        try {
            logEnter("removePermissionType(typeid)");
            logOneParameter(typeid);

            EntityManager em = getEntityManager();

            PermissionType type = em.find(PermissionType.class, new Long(typeid));

            if (type == null) {
                return false;
            }

            em.remove(type);

            return true;
        } catch (IllegalStateException e) {
            throw wrapPermissionServiceException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapPermissionServiceException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapPermissionServiceException(e, "There are errors while deleting the entity.");
        } finally {
            logExit("removePermissionType(typeid)");
        }
    }

    /**
     * <p>
     * This method will remove permission data, return true if the permission data exists and removed successfully,
     * return false if it doesn't exist.
     * </p>
     *
     * @param permissionid
     *            the permission to delete.
     *
     * @return true if the permission data exists and removed successfully.
     *
     * @throws PermissionServiceException
     *             if any error occurs when deleting the permission.
     *
     * @since Cockpit Project Admin Release Assembly v1.0
     */
    private boolean deletePermission(long permissionid,  EntityManager em) throws PermissionServiceException {
         try {
            logEnter("removePermission(permissionid)");
            logOneParameter(permissionid);

            Query query = em.createNativeQuery("delete from user_permission_grant where user_permission_grant_id=:permissionId");
            query.setParameter("permissionId", permissionid);

            int result = query.executeUpdate();
            if (result > 0) {
                    return true;
            } else {
                 return false;
            }
        } catch (IllegalStateException e) {
            throw wrapPermissionServiceException(e, "The EntityManager is closed.");
        } catch (TransactionRequiredException e) {
            throw wrapPermissionServiceException(e, "This method is required to run in transaction.");
        } catch (PersistenceException e) {
            throw wrapPermissionServiceException(e, "There are errors while deleting the entity.");
        } finally {
            logExit("removePermission(permissionid)");
        }
    }

    /**
     * Generic method for getting all the entities
     *
     * @param clazz
     *            class to get all the entities.
     * @return a list with the all required entities
     * @throws ContestManagementException
     */
    @SuppressWarnings("unchecked")
    private <T> List<T> getAll(Class<T> clazz) throws PermissionServiceException {
        try {
            EntityManager em = getEntityManager();

            Query query = em.createQuery("from " + clazz.getName());

            List<T> list = query.getResultList();

            List<T> result = new ArrayList<T>();
            result.addAll(list);
            return result;

        } catch (IllegalStateException e) {
            throw wrapPermissionServiceException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapPermissionServiceException(e, "There are errors while persisting the entity.");
        }
    }

    /**
     * <p>
     * Returns the <code>EntityManager</code> looked up from the session context.
     * </p>
     *
     * @return the EntityManager looked up from the session context
     * @throws ContestManagementException
     *             if fail to get the EntityManager from the sessionContext.
     */
    private EntityManager getEntityManager() throws PermissionServiceException {
        try {
            Object obj = sessionContext.lookup(unitName);

            if (obj == null) {
                throw wrapPermissionServiceException("The object for jndi name '" + unitName + "' doesn't exist.");
            }

            return (EntityManager) obj;
        } catch (ClassCastException e) {
            throw wrapPermissionServiceException(e, "The jndi name for '" + unitName
                    + "' should be EntityManager instance.");
        }
        //return entityManager;
    }

    /**
     * <p>
     * Log the entrance of a method.
     * </p>
     *
     * @param methodName
     *            the method name
     */
    private void logEnter(String methodName) {
        if (logger != null) {
            logger.log(Level.DEBUG, "[Enter method: PermissionServiceBean." + methodName + "]");
        }
    }

    /**
     * <p>
     * Log the exit of a method.
     * </p>
     *
     * @param methodName
     *            the method name
     */
    private void logExit(String methodName) {
        if (logger != null) {
            logger.log(Level.DEBUG, "[Exit method: " + methodName + "]");
        }
    }

    /**
     * <p>
     * Log the parameter.
     * </p>
     *
     * @param param
     *            the parameter value
     */
    private void logOneParameter(Object param) {
        if (logger != null) {
            logger.log(Level.DEBUG, "[param1: {0}]", param);
        }
    }

    /**
     * <p>
     * Log the parameters.
     * </p>
     *
     * @param param1
     *            the first parameter values
     * @param param2
     *            the second parameter value
     */
    private void logTwoParameters(Object param1, Object param2) {
        if (logger != null) {
            logger.log(Level.DEBUG, "[param1: {0}, param2: {1}]", param1, param2);
        }
    }

    /**
     * <p>
     * Log the exception.
     * </p>
     *
     * @param e
     *            the exception to log
     * @param message
     *            the string message
     */
    private void logException(Throwable e, String message) {
        if (logger != null) {
            // This will log the message and StackTrace of the exception.
            logger.log(Level.ERROR, e, message);

            while (e != null) {
                logger.log(Level.ERROR, "INNER: " + e.getMessage());
                e = e.getCause();
            }
        }
    }

    /**
     * <p>
     * Creates a <code>ContestManagementException</code> with inner exception and message. It will log the exception,
     * and set the sessionContext to rollback only.
     * </p>
     *
     * @param e
     *            the inner exception
     * @param message
     *            the error message
     * @return the created exception
     */
    private PermissionServiceException wrapPermissionServiceException(Exception e, String message) {
        PermissionServiceException ce = new PermissionServiceException(message, e);
        logException(ce, message);
        sessionContext.setRollbackOnly();

        return ce;
    }

    /**
     * <p>
     * Creates a <code>ContestManagementException</code> with inner exception and message. It will log the exception,
     * and set the sessionContext to rollback only.
     * </p>
     *
     * @param e
     *            the inner exception
     * @param message
     *            the error message
     * @return the created exception
     */
    private PermissionServiceException wrapPermissionServiceException(String message) {
        PermissionServiceException ce = new PermissionServiceException(message);
        logException(ce, message);
        sessionContext.setRollbackOnly();

        return ce;
    }

    /**
     * <p>
     * This method updates array of permissions to the persistence.
     * </p>
     *
     * @param permissions the permissions to update.
     *
     * @throws IllegalArgumentWSException if the argument is invalid
     * @throws PermissionServiceException if any error occurs when updating the permission.
     *
     * @since Cockpit Project Admin Release Assembly.
     */
    public void updatePermissions(Permission[] permissions) throws PermissionServiceException {

        EntityManager em = getEntityManager();

        for (Permission p : permissions) {

            if (p.getPermissionId() == null || p.getPermissionId() <= 0) {
                // do add.

                List<Permission> ps = this.getPermissions(p.getUserId(), p.getResourceId(), em);
                if (ps == null || ps.size() == 0) {
                    this.addPermission(p, em);
                } else {
                    // let's assume there would be only one permission record for given resource id and userId.
                    p.setPermissionId(ps.get(0).getPermissionId());

                     if (p.getPermissionType() == null || p.getPermissionType().getName() == null
                                          || p.getPermissionType().getName().equals(""))
                     {
                        // do delete
                        this.deletePermission(p.getPermissionId(), em);
                    } else {
                        // otherwise update.
                        this.updatePermission(p, em);
                    }
                }
            } else {
                if (p.getPermissionType() == null) {
                    // do delete
                    this.deletePermission(p.getPermissionId(), em);
                } else {
                    // otherwise update.
                    this.updatePermission(p, em);
                }
            }
        }
    }


   /**
     * <p>
     * Get permission by id
     * </p>
     *
     * @param id
     *            id to look for
     *
     * @return permission
     *
     * @throws PermissionServiceException
     *             if any error occurs when getting permissions.
     */
    public Permission getPermissionsById(long id) throws PermissionServiceException {
        try {
            logEnter("getPermissionsById(id)");
            logOneParameter(id);

            EntityManager em = getEntityManager();

            Query query = em.createQuery("select p from com.topcoder.service.permission.Permission p where p.permissionId = " + id);


            Permission result = null;

            if (query.getSingleResult() != null)
            {
                result =  (Permission) query.getSingleResult();
            }

            return result;
        } catch (IllegalStateException e) {
            throw wrapPermissionServiceException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapPermissionServiceException(e, "There are errors while retrieving the permissions.");
        } finally {
            logExit("getPermissionsByUser(userid)");
        }
    }

    /**
     * <p>Gets the permissions set for projects which specified user has <code>Full Access</code> permission set for.
     * </p>
     *
     * @param userId a <code>long</code> providing the ID of a user to get project permissions for.
     * @return a <code>List</code> listing the project permissions set for projects which specified user has <code>Full
     *         Access</code> permission set for.
     * @throws PermissionServiceException if an unexpected error occurs.
     * @since 1.2
     */
    public List<ProjectPermission> getProjectPermissions(long userId) throws PermissionServiceException {
        try {
            logEnter("getProjectPermissions(userId)");
            logOneParameter(userId);
            EntityManager em = getEntityManager();
            List<ProjectPermission> result = new ArrayList<ProjectPermission>();
            List<Permission> userFullProjectPermissions = getProjectLevelFullPermissions(userId, em);
            for (Permission userFullProjectPermission : userFullProjectPermissions) {
                Long resourceId = userFullProjectPermission.getResourceId();
                List<Permission> permissions = getProjectLevelPermissions(resourceId, em);
                for (Permission permission : permissions) {
                    ProjectPermission projectPermission = new ProjectPermission();
                    projectPermission.setHandle(permission.getUserHandle());
                    projectPermission.setPermission(PROJECT_PERMISSION_TYPES[
                        (int) (permission.getPermissionType().getPermissionTypeId() - 1)]);
                    projectPermission.setProjectId(permission.getResourceId());
                    projectPermission.setProjectName(permission.getResourceName());
                    projectPermission.setUserId(permission.getUserId());
                    projectPermission.setStudio(permission.isStudio());
                    result.add(projectPermission);
                }
            }
            return result;
        } finally {
            logExit("getProjectPermissions(userId)");
        }
    }

    /**
     * <p>Updates the permissions for specified user for accessing the projects.</p>
     *
     * @param projectPermissions a <code>List</code> listing the permissions to be set for specified user for accessing
     *        projects.
     * @param userId a <code>long</code> providing the ID of a user to update permissions for.
     * @throws PermissionServiceException if an unexpected error occurs.
     * @throws IllegalArgumentException if specified <code>projectPermissions</code> list is <code>null</code> or
     *         contains <code>null</code> element. 
     * @since 1.2
     */
    public void updateProjectPermissions(List<ProjectPermission> projectPermissions, long userId)
        throws PermissionServiceException {
        try {
            logEnter("updateProjectPermissions(projectPermissions, userId)");
            logTwoParameters(projectPermissions, userId);

            if (projectPermissions == null) {
                throw new IllegalArgumentException("The parameter [projectPermissions] is NULL");
            }
            for (ProjectPermission permission : projectPermissions) {
                if (permission == null) {
                    throw new IllegalArgumentException("The parameter [projectPermissions] contains NULL element");
                }
            }

            EntityManager em = getEntityManager();

            // Get existing permission types and build the mapping from names to objects
            Map<String, PermissionType> permissionTypesMap = new HashMap<String, PermissionType>();
            List<PermissionType> permissionTypes = getAllPermissionType();
            for (PermissionType permissionType : permissionTypes) {
                permissionTypesMap.put(permissionType.getName(), permissionType);
            }

            // Build the list of IDs of projects which user is granted full access to in order to authorize user for
            // updating requested permissions
            Set<Long> fullAccessProjectIds = new HashSet<Long>();
            List<Permission> projectsFullPermissions = getProjectLevelFullPermissions(userId, em);
            for (Permission fullPermission : projectsFullPermissions) {
                fullAccessProjectIds.add(fullPermission.getResourceId());
            }


            // Update requested project permissions
            for (ProjectPermission permission : projectPermissions) {
                if (fullAccessProjectIds.contains(permission.getProjectId())) {
                    deletePermission(permission.getUserId(), permission.getProjectId(), em);
                    String permissionType = permission.getPermission();
                    if ((permissionType != null) && (permissionType.trim().length() > 0)) {
                        String permissionTypeKey = "project_" + permissionType;
                        if (permissionTypesMap.containsKey(permissionTypeKey)) {
                            PermissionType newPermissionType = permissionTypesMap.get(permissionTypeKey);
                            Permission newPermission = new Permission();
                            newPermission.setPermissionType(newPermissionType);
                            newPermission.setResourceId(permission.getProjectId());
                            newPermission.setResourceName(permission.getProjectName());
                            newPermission.setStudio(permission.getStudio());
                            newPermission.setUserHandle(permission.getHandle());
                            newPermission.setUserId(permission.getUserId());
                            addPermission(newPermission, em);
                        } else {
                            throw new PermissionServiceException("Wrong permission type: " + permissionType);
                        }
                    }
                } else {
                    throw new PermissionServiceException("User " + userId + " is not granted FULL permission for "
                                                         + "project " + permission.getProjectId());
                }
            }
        } finally {
            logExit("updateProjectPermissions(projectPermissions, userId)");
        }
    }

    /**
     * <p>Deletes the project level permissions associated with specified user/project pair.</p>
     *
     * @param userId a <code>long</code> providing the ID for the user associated with permission.
     * @param projectId a <code>long</code> providing the ID for the project associated with permission.
     * @param em an <code>EntityManager</code> to be used for getting the permissions.
     * @throws PermissionServiceException if an unexpected error occurs.
     * @since 1.2
     */
    private void deletePermission(long userId, long projectId, EntityManager em) throws PermissionServiceException {
        try {
           logEnter("deletePermission(userId, projectId)");
           logTwoParameters(userId, projectId);

           Query query = em.createNativeQuery("DELETE FROM user_permission_grant WHERE " +
                                              "user_id = :userId AND resource_id = :projectId");
           query.setParameter("userId", userId);
           query.setParameter("projectId", projectId);

           query.executeUpdate();
       } catch (IllegalStateException e) {
           throw wrapPermissionServiceException(e, "The EntityManager is closed.");
       } catch (TransactionRequiredException e) {
           throw wrapPermissionServiceException(e, "This method is required to run in transaction.");
       } catch (PersistenceException e) {
           throw wrapPermissionServiceException(e, "There are errors while deleting the entity.");
       } finally {
           logExit("deletePermission(userId, projectId)");
       }
    }

    /**
     * <p>Gets the list of <code>Full</code> permissions for projects for specified user.</p>
     *
     * @param userId a <code>long</code> providing the ID for user to check permission for.
     * @param em an <code>EntityManager</code> to be used for getting the permissions.
     * @return <code>true</code> if specified user has <code>Full Access</code> permission set for specified project;
     *         <code>false</code> otherwise.
     * @throws PermissionServiceException if an unexpected error occurs.
     * @since 1.2
     */
    @SuppressWarnings("unchecked")
    private List<Permission> getProjectLevelFullPermissions(long userId, EntityManager em)
        throws PermissionServiceException {
        try {
            logEnter("getProjectLevelFullPermissions(userId)");
            logOneParameter(userId);

            Query query = em.createQuery("select p "
                                         + "from com.topcoder.service.permission.Permission p "
                                         + "where p.userId = :userId "
                                         + " and p.permissionType.id = :permissionTypeId");
            query.setParameter("userId", userId);
            query.setParameter("permissionTypeId", PermissionType.PERMISSION_TYPE_PROJECT_FULL);

            return (List<Permission>) query.getResultList();
        } catch (IllegalStateException e) {
            throw wrapPermissionServiceException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapPermissionServiceException(e, "There are errors while retrieving the permissions.");
        } finally {
            logExit("getProjectLevelFullPermissions(userId)");
        }
    }

    /**
     * <p>Gets the list of project level permissions granted to any users for accessing the specified project.</p>
     *
     * @param resourceId a <code>long</code> providing the ID of a project to look project level permissions for.
     * @param em an <code>EntityManager</code> to be used for getting the permissions.
     * @return a <code>List</code> listing project level permissions granted to any users for accessing the specified
     *         project.
     * @throws PermissionServiceException if an unexpected error occurs.
     * @since 1.2
     */
    @SuppressWarnings("unchecked")
    private List<Permission> getProjectLevelPermissions(long resourceId, EntityManager em)
        throws PermissionServiceException {
        try {
            logEnter("getProjectLevelPermissions(resourceId)");
            logOneParameter(resourceId);

            Query query = em.createQuery("select p "
                                         + "from com.topcoder.service.permission.Permission p "
                                         + "where p.resourceId = :resourceId "
                                         + "and p.permissionType.id >= 1 "
                                         + "and p.permissionType.id <= 3");
            query.setParameter("resourceId", resourceId);
            return (List<Permission>) query.getResultList();
        } catch (IllegalStateException e) {
            throw wrapPermissionServiceException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapPermissionServiceException(e, "There are errors while retrieving the permissions.");
        } finally {
            logExit("getProjectLevelPermissions(resourceId)");
        }
    }
}
