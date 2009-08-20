/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.Serializable;

/**
 * <p>
 * Represents the entity class for db table <i>document_type_lu</i>.
 * </p>
 * <p>
 * Currently the possible types are SPECIFICTION and TEMPLATE.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author tushak, cyberjag
 * @version 1.0
 */
public class DocumentType implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -6095415984714358360L;

    /**
     * Represents the entity id.
     */
    private Long documentTypeId;

    /**
     * Represents the description of document type.
     */
    private String description;

    /**
     * Default constructor.
     */
    public DocumentType() {
        // empty
    }

    /**
     * Returns the documentTypeId.
     *
     * @return the documentTypeId.
     */
    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    /**
     * Updates the documentTypeId with the specified value.
     *
     * @param documentTypeId
     *            the documentTypeId to set.
     */
    public void setDocumentTypeId(Long documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    /**
     * Returns the description.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Updates the description with the specified value.
     *
     * @param description
     *            the description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Compares this object with the passed object for equality. Only the id will be compared.
     *
     * @param obj
     *            the {@code Object} to compare to this one
     * @return true if this object is equal to the other, {@code false} if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DocumentType) {
            return getDocumentTypeId() == ((DocumentType) obj).getDocumentTypeId();
        }
        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's
     * {@link #equals(Object)}} method.
     *
     * @return a hash code for this {@code DocumentType}
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(documentTypeId);
    }
}
