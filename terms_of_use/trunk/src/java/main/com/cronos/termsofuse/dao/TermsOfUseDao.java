/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.termsofuse.dao;

import java.util.List;

import com.cronos.termsofuse.model.TermsOfUse;
import com.cronos.termsofuse.model.TermsOfUseType;

/**
 * <p>
 * This interface defines the dao for manipulating the TermsOfUse entity. It simply provides CRUD operations on this
 * entity and retrieval operation by the terms of use type. It also provides retrieval and update operation for terms
 * of use type.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> The implementations are required to be thread safe.
 * </p>
 *
 * @author faeton, sparemax
 * @version 1.0
 */
public interface TermsOfUseDao {
    /**
     * Creates terms of use entity with the terms text.
     *
     * @param termsOfUse
     *            a TermsOfUse containing required information for creation.
     * @param termsText
     *            the terms text to create.
     *
     * @return a TermsOfUse with created id attribute.
     *
     * @throws IllegalArgumentException
     *             if termsOfUse is null.
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public TermsOfUse createTermsOfUse(TermsOfUse termsOfUse, String termsText) throws TermsOfUsePersistenceException;

    /**
     * Updates terms of use entity.
     *
     * @param termsOfUse
     *            a TermsOfUse containing required information for update.
     *
     * @return a TermsOfUse with updated id attribute.
     *
     * @throws IllegalArgumentException
     *             if termsOfUse is null.
     * @throws EntityNotFoundException
     *             if the entity was not found in the database.
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public TermsOfUse updateTermsOfUse(TermsOfUse termsOfUse) throws EntityNotFoundException,
        TermsOfUsePersistenceException;

    /**
     * Retrieves a terms of use entity from the database.
     *
     * @param termsOfUseId
     *            a long containing the terms of use id to retrieve.
     *
     * @return a TermsOfUse with the requested terms of use or null if not found.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public TermsOfUse getTermsOfUse(long termsOfUseId) throws TermsOfUsePersistenceException;

    /**
     * Deletes a terms of use entity from the database.
     *
     * @param termsOfUseId
     *            a long containing the terms of use id to delete.
     *
     * @throws EntityNotFoundException
     *             if the entity was not found in the database.
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public void deleteTermsOfUse(long termsOfUseId) throws EntityNotFoundException, TermsOfUsePersistenceException;

    /**
     * Retrieves a terms of use entities by the terms of use type id from the database.
     *
     * @param termsOfUseTypeId
     *            an int containing the terms of use type id to retrieve.
     *
     * @return a list of TermsOfUse entities with the requested terms of use or empty list if not found.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public List<TermsOfUse> getTermsOfUseByTypeId(int termsOfUseTypeId) throws TermsOfUsePersistenceException;

    /**
     * Retrieves all terms of use entities from the database.
     *
     * @return a list of all TermsOfUse entities.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public List<TermsOfUse> getAllTermsOfUse() throws TermsOfUsePersistenceException;

    /**
     * Gets terms of use type by id.
     *
     * @param termsOfUseTypeId
     *            terms of use type id.
     *
     * @return terms of use type.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public TermsOfUseType getTermsOfUseType(int termsOfUseTypeId) throws TermsOfUsePersistenceException;

    /**
     * Update terms of use type.
     *
     * @param termsType
     *            the terms of use type to be updated.
     *
     * @return updated terms of use type.
     *
     * @throws IllegalArgumentException
     *             if termsType is null.
     * @throws EntityNotFoundException
     *             if the entity was not found in the database.
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public TermsOfUseType updateTermsOfUseType(TermsOfUseType termsType) throws EntityNotFoundException,
        TermsOfUsePersistenceException;

    /**
     * Gets terms of use text by terms of use id.
     *
     * @param termsOfUseId
     *            terms of use id.
     *
     * @return text of terms of use.
     *
     * @throws EntityNotFoundException
     *             if the entity was not found in the database.
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public String getTermsOfUseText(long termsOfUseId) throws EntityNotFoundException, TermsOfUsePersistenceException;

    /**
     * Sets terms of use text.
     *
     * @param termsOfUseId
     *            terms of use id.
     * @param text
     *            text of terms of use.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     * @throws EntityNotFoundException
     *             if the entity was not found in the database.
     */
    public void setTermsOfUseText(long termsOfUseId, String text) throws TermsOfUsePersistenceException,
        EntityNotFoundException;
}
