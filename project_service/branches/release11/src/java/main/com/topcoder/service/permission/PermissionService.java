package com.topcoder.service.permission;

import java.util.List;

import javax.ejb.Remote;

/**
 * <p>
 * It provides CRUD on permission object.
 * </p>
 * 
 * @author TCSASSEMBLER
 * 
 * @since Cockpit Project Admin Release Assembly v1.0
 * @version 1.0
 */
@Remote
public interface PermissionService {

	/**
     * <p>
     * This method retrieve all the permissions that the user owned for any projects. Returns empty list if no
     * permission found.
     * </p>
     *
     * @param userid user id to look for
     *
     * @return all the permissions that the user owned for any projects.
     *
     * @throws IllegalArgumentWSException if the argument is invalid
     * @throws PermissionServiceException if any error occurs when getting permissions.
     *
     * @since Cockpit Project Admin Release Assembly v1.0
     */
    public List<Permission> getPermissionsByUser(long userid) throws PermissionServiceException;
    
    /**
     * <p>
     * This method retrieve all the permissions that various users own for a given project. Returns empty list if no
     * permission found.
     * </p>
     *
     * @param projectid project id to look for
     *
     * @return all the permissions that various users own for a given project.
     *
     * @throws ContestManagementException if any error occurs when getting permissions.
     *
     * @since Cockpit Share Submission Integration
     */
    public List<Permission> getPermissionsByProject(long projectid) throws PermissionServiceException;

    /**
     * <p>
     * This method retrieve all the permissions that the user own for a given project. Returns empty list if no
     * permission found.
     * </p>
     *
     * @param userid user id to look for
     * @param projectid project id to look for
     *
     * @return all the permissions that the user own for a given project.
     *
     * @throws IllegalArgumentWSException if the argument is invalid
     * @throws PermissionServiceException if any error occurs when getting permissions.
     *
     * @since Cockpit Project Admin Release Assembly v1.0
     */
    public List<Permission> getPermissions(long userid, long projectid) throws PermissionServiceException;

    /**
     * <p>
     * This method retrieve all the permission types.
     * </p>
     *
     * @return all the permission types.
     *
     * @throws PermissionServiceException if any error occurs when getting permission types.
     *
     * @since Cockpit Project Admin Release Assembly v1.0
     */
    public List<PermissionType> getAllPermissionType() throws PermissionServiceException;

    /**
     * <p>
     * This method will add a permission type, and return the added type entity.
     * </p>
     *
     * @param type the permission type to add.
     *
     * @return the added permission type entity
     *
     * @throws IllegalArgumentWSException if the argument is invalid
     * @throws PermissionServiceException if any error occurs when adding the permission type.
     *
     * @since Cockpit Project Admin Release Assembly v1.0
     */
    public PermissionType addPermissionType(PermissionType type) throws PermissionServiceException;



    /**
     * <p>
     * This method will update permission type data.
     * </p>
     *
     * @param type the permission type to update.
     *
     * @throws IllegalArgumentWSException if the argument is invalid
     * @throws PermissionServiceException if any error occurs when updating the permission type.
     *
     * @since Cockpit Project Admin Release Assembly v1.0
     */
    public void updatePermissionType(PermissionType type) throws PermissionServiceException;



    /**
     * <p>
     * This method will update permission type data, return true if the permission type data exists and removed
     * successfully, return false if it doesn't exist.
     * </p>
     *
     * @param typeid the permission type to delete.
     *
     * @return true if the permission type data exists and removed successfully.
     *
     * @throws IllegalArgumentWSException if the argument is invalid
     * @throws PermissionServiceException if any error occurs when deleting the permission.
     *
     * @since Cockpit Project Admin Release Assembly v1.0
     */
    public boolean deletePermissionType(long typeid) throws PermissionServiceException;


    
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
    public void updatePermissions(Permission[] permissions) throws PermissionServiceException;


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
    public Permission getPermissionsById(long id) throws PermissionServiceException;
}
