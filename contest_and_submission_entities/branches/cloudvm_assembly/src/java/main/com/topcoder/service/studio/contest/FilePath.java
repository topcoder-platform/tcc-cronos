/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.studio.contest;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Represents the entity class for db table <i>path</i>.
 * </p>
 *
 * <p>
 * A sample code showing the CRUD on this entity using hibernate is given below.
 * </p>
 * <p>
 * <code>
 * <pre>
 *
 *     try {
 *           HibernateUtil.getManager().getTransaction().begin();
 *           FilePath entity = new FilePath();
 *           entity.setModifyDate(new Date());
 *           entity.setPath("path");
 *           // save the entity
 *           HibernateUtil.getManager().persist(entity);
 *           // load the persisted object
 *           FilePath persisted = (FilePath) HibernateUtil.getManager().find(FilePath.class, entity.getFilePathId());
 *           // update the entity
 *           entity.setPath("new path");
 *           HibernateUtil.getManager().merge(entity);
 *           persisted = (FilePath) HibernateUtil.getManager().find(FilePath.class, entity.getFilePathId());
 *           // delete the entity
 *           HibernateUtil.getManager().remove(entity);
 *
 *      } finally {
 *           HibernateUtil.getManager().getTransaction().commit();
 *      }
 * </pre>
 * </code>
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author tushak, cyberjag
 * @version 1.0
 */
public class FilePath implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -143542280439429925L;

    /**
     * Represents the entity id.
     */
    private Long filePathId = -1l;

    /**
     * Represents the URI path.
     */
    private String path;

    /**
     * Represents the modify date.
     */
    private Date modifyDate;

    /**
     * Default constructor.
     */
    public FilePath() {
        // empty
    }

    /**
     * Returns the filePathId.
     *
     * @return the filePathId.
     */
    public Long getFilePathId() {
        return filePathId;
    }

    /**
     * Updates the filePathId with the specified value.
     *
     * @param filePathId
     *            the filePathId to set.
     */
    public void setFilePathId(Long filePathId) {
        this.filePathId = filePathId;
    }

    /**
     * Returns the path.
     *
     * @return the path.
     */
    public String getPath() {
        return path;
    }

    /**
     * Updates the path with the specified value.
     *
     * @param path
     *            the path to set.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Returns the modifyDate.
     *
     * @return the modifyDate.
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * Updates the modifyDate with the specified value.
     *
     * @param modifyDate
     *            the modifyDate to set.
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
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
        if (obj instanceof FilePath) {
            return getFilePathId() == ((FilePath) obj).getFilePathId();
        }
        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's
     * {@link #equals(Object)}} method.
     *
     * @return a hash code for this {@code FilePath}
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(filePathId);
    }
}
