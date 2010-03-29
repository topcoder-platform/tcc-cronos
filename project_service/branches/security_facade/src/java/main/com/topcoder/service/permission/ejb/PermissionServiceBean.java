/*
 * Copyright (C) 2008-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.permission.ejb;

import java.util.ArrayList;
import java.util.List;

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
 * @author waits
 *
 * @since Cockpit Project Admin Release Assembly v1.0
 * @version 1.1
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Stateless
public class PermissionServiceBean implements PermissionServiceRemote,  PermissionServiceLocal{
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

}
