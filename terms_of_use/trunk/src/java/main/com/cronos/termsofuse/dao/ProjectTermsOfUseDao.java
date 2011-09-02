/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.termsofuse.dao;

import java.util.List;
import java.util.Map;

import com.cronos.termsofuse.model.TermsOfUse;

/**
 * <p>
 * This interface defines the dao for manipulating the TermsOfUse to Project links. It simply provides create/delete
 * and search operations on the links.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> The implementations are required to be thread safe.
 * </p>
 *
 * @author faeton, sparemax
 * @version 1.0
 */
public interface ProjectTermsOfUseDao {
    /**
     * This method will create a project role terms of use association.
     *
     * @param groupIndex
     *            the group index to associate.
     * @param resourceRoleId
     *            the role id to associate.
     * @param sortOrder
     *            the association sort order.
     * @param termsOfUseId
     *            the terms of use id to associate.
     * @param projectId
     *            the project id to associate.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public void createProjectRoleTermsOfUse(int projectId, int resourceRoleId, long termsOfUseId, int sortOrder,
        int groupIndex) throws TermsOfUsePersistenceException;

    /**
     * This method will remove a project role terms of use association.
     *
     * @param groupIndex
     *            the group index to associate.
     * @param resourceRoleId
     *            the role id to associate.
     * @param termsOfUseId
     *            the terms of use id to associategroupIndex groupIndex to associate.
     * @param projectId
     *            the project id to associate.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     * @throws EntityNotFoundException
     *             if the entity was not found in the database.
     */
    public void removeProjectRoleTermsOfUse(int projectId, int resourceRoleId, long termsOfUseId, int groupIndex)
        throws EntityNotFoundException, TermsOfUsePersistenceException;

    /**
     * This method retrieves terms of use for specific pair of user and resource roles and groups it by terms of use
     * groups. It optionally can ignore non member agreeable terms of use.
     *
     * @param includeNonMemberAgreeable
     *            the flag specifying whether to include non memeber agreeable terms of use.
     * @param resourceRoleIds
     *            the role ids to associate.
     * @param projectId
     *            the project id to associate.
     *
     * @return Map of lists of terms of use entities. The key of the map is the group index, the value is the list of
     *         terms for this group.
     *
     * @throws IllegalArgumentException
     *             if resourceRoleIds is null or empty array.
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public Map<Integer, List<TermsOfUse>> getTermsOfUse(int projectId, int[] resourceRoleIds,
        boolean includeNonMemberAgreeable) throws TermsOfUsePersistenceException;

    /**
     * This method will remove all project role terms of use association for a given project.
     *
     * @param projectId
     *            the project id to remove.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public void removeAllProjectRoleTermsOfUse(int projectId) throws TermsOfUsePersistenceException;
}
