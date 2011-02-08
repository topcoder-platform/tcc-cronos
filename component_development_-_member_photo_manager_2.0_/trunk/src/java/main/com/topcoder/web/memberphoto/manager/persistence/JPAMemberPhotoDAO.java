/*
 * Copyright (C) 2008, 2011 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.web.memberphoto.manager.persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.topcoder.util.errorhandling.ExceptionUtils;
import com.topcoder.web.memberphoto.entities.Image;
import com.topcoder.web.memberphoto.entities.MemberImage;
import com.topcoder.web.memberphoto.manager.Helper;
import com.topcoder.web.memberphoto.manager.MemberPhotoDAO;
import com.topcoder.web.memberphoto.manager.MemberPhotoDAOException;
import com.topcoder.web.memberphoto.manager.MemberPhotoNotFoundException;
import com.topcoder.web.memberphoto.manager.PagedResult;

/**
 * <p>
 * This class is a JPA realization of the <code>{@link MemberPhotoDAO}</code> interface. This class implements the
 * methods available for the MemberPhotoDAO interface: retrieve member photo, save member photo and remove member
 * photo. JPA is used to perform the needed operations with the persistence.
 * </p>
 * <p>
 * Spring IoC (inversion of control) container is used to inject dependencies into this object. The clients of this
 * component most likely will handle transactions using declarative transaction handling provided by Spring framework.
 * So this component does not deal with transaction, i.e. methods of manager class assume that they are called inside
 * of transaction.
 * </p>
 *
 * <p>
 * Changes in 2.0:
 * <ol>
 * <li>Added byUser parameter to saveMemberPhoto() and removeMemberPhoto() methods (to be used for auditing).</li>
 * <li>Added getMemberPhotos() method for retrieving recently added/updated member images.</li>
 * <li>Updated getMemberPhoto() and removeMemberPhoto() methods to support logical removal of MemberImage entities.</li>
 * <li>Updated saveMemberPhoto() and removeMemberPhoto() methods to support MemberImage auditing fields.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Thread Safety: This class can be considered as thread safe because its fields are injected once by Spring Framework
 * and it ensure that will not be changed afterwards and after the injection the Spring Framework will provide thread
 * safety. Also thread safety depend on the configured EntityManager. Used in a multi-threaded environment, the
 * received arguments could be changed by external threads but we can assume this will not happen and that the
 * component will be used from the Spring Framework in a thread safe manner. Under such conditions this class becomes
 * thread-safe.
 * </p>
 *
 * @author Mafy, cyberjag, saarixx, sparemax
 * @version 2.0
 */
public class JPAMemberPhotoDAO implements MemberPhotoDAO {
    /**
     * <p>
     * Represents the SQL string to query the member image.
     * </p>
     */
    private static final String SQL_QUERY_MEMBER_IMAGE = "SELECT mi FROM MemberImage mi WHERE mi.memberId = :memberId";

    /**
     * <p>
     * Represents the SQL string to query the member image which is not removed.
     * </p>
     */
    private static final String SQL_QUERY_MEMBER_IMAGE_NOT_REMOVED =
        "SELECT mi FROM MemberImage mi WHERE mi.memberId = :memberId AND mi.removed = :removed";

    /**
     * <p>
     * Represents the SQL string to query the image which is not removed.
     * </p>
     */
    private static final String SQL_QUERY_IMAGE_NOT_REMOVED =
        "SELECT mi.image FROM MemberImage mi WHERE mi.memberId = :memberId AND mi.removed = :removed";

    /**
     * <p>
     * Represents the SQL string to query the count of images which are not removed.
     * </p>
     */
    private static final String SQL_QUERY_MEMBER_IMAGE_NOT_REMOVED_COUNT =
        "SELECT COUNT(mi) FROM MemberImage mi WHERE mi.removed = :removed";

    /**
     * <p>
     * Represents the SQL string to query the images which are not removed.
     * </p>
     */
    private static final String SQL_QUERY_MEMBER_IMAGES_NOT_REMOVED =
        "SELECT mi FROM MemberImage mi WHERE mi.removed = :removed ORDER BY mi.updatedDate DESC";

    /**
     * <p>
     * Represents an instance of the <code>EntityManager</code>, which will be used for performing the needed
     * JPA actions. Used by all CRUD operations. It is not expected to be set more than once, only initial
     * configuration is expected here.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not assigned. It is accessed through the
     * corresponding getter/setter methods. It should be a concrete instance for performing database related
     * methods of this class. The IoC should handle the validity if the passed value.
     * </p>
     */
    private EntityManager entityManager;

    /**
     * Default no-arg constructor. Constructs a new <code>JPAMemberPhotoDAO</code> instance.
     */
    public JPAMemberPhotoDAO() {
        // Empty
    }

    /**
     * Constructs a new JPAMemberPhotoDAO instance. Initialize the entityManager with the given argument.
     *
     * @param entityManager
     *            the instance of the entityManager, which will be used for obtaining sessions and perform JPA
     *            actions. The IoC should handle the validity of the passed value.
     */
    public JPAMemberPhotoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of a member photo using the given member id from the
     * persistence.
     * </p>
     *
     * <p>
     * <em>Changes in 2.0:</em>
     * <ol>
     * <li>Using a query to retrieve MemberImage instance instead of entityManager.find().</li>
     * <li>Method returns null instead of throwing MemberPhotoNotFoundException.</li>
     * </ol>
     * </p>
     *
     * @param memberId
     *            the identifier of the member that photo should be retrieved from the persistence. Can be any value.
     *
     * @return the member image corresponding to the given member id retrieved from the persistence (null if doesn't
     *         exist or is removed).
     *
     * @throws MemberPhotoDAOException
     *             if any error occurs while performing this operation.
     * @throws IllegalStateException
     *             if the entityManager is not set (or it is set to a null value).
     */
    public Image getMemberPhoto(long memberId) throws MemberPhotoDAOException {
        Helper.checkState(entityManager, "entityManager");
        try {
            return getSingleResult(SQL_QUERY_IMAGE_NOT_REMOVED, memberId, false);
        } catch (PersistenceException e) {
            throw new MemberPhotoDAOException("Failed to find the memeber image.", e);
        } catch (IllegalStateException e) {
            throw new MemberPhotoDAOException(
                "Failed to find the memeber image. This EntityManager has been closed.", e);
        }
    }

    /**
     * <p>
     * Saves a member photo for the given image and member id in the persistence.
     * </p>
     *
     * <p>
     * <em>Changes in 2.0:</em>
     * <ol>
     * <li>Added byUser parameter.</li>
     * <li>Added steps for initializing auditing properties.</li>
     * <li>Added steps for retrieving an existing or removed MemberImage from persistence.</li>
     * </ol>
     * </p>
     *
     * @param memberId
     *            the identifier of the member that photo should be saved in the persistence. Can be any value.
     * @param image
     *            the image that should be saved for the member with the given id in the persistence. Should not be
     *            <code>null</code>.
     * @param byUser
     *            the username of the user that performs the operation. Cannot be null/empty.
     *
     * @throws IllegalStateException
     *             if the entityManager is not set (or it is set to a <code>null</code> value).
     * @throws IllegalArgumentException
     *             if image is <code>null</code> or byUser is null/empty.
     * @throws MemberPhotoDAOException
     *             if any error occurs while performing this operation.
     */
    public void saveMemberPhoto(long memberId, Image image, String byUser) throws MemberPhotoDAOException {
        ExceptionUtils.checkNull(image, null, null, "The parameter 'image' cannot be null.");
        ExceptionUtils.checkNullOrEmpty(byUser, null, null, "The parameter 'byUser' cannot be null or empty.");
        Helper.checkState(entityManager, "entityManager");
        try {
            Date curDate = new Date(); // NEW in 2.0
            MemberImage memberImage = getSingleResult(SQL_QUERY_MEMBER_IMAGE, memberId, null); // NEW in 2.0

            if (memberImage == null) { // NEW in 2.0
                memberImage = new MemberImage();
                memberImage.setMemberId(memberId);
                memberImage.setCreatedBy(byUser); // NEW in 2.0
                memberImage.setCreatedDate(curDate); // NEW in 2.0
            } else {
                memberImage.setRemoved(false); // NEW in 2.0
            }
            memberImage.setImage(image);
            memberImage.setUpdatedBy(byUser); // NEW in 2.0
            memberImage.setUpdatedDate(curDate); // NEW in 2.0

            // Save the entity
            entityManager.merge(memberImage);
        } catch (PersistenceException e) {
            throw new MemberPhotoDAOException("Failed to save member image.", e);
        } catch (IllegalStateException e) {
            throw new MemberPhotoDAOException("Failed to save member image. This EntityManager has been closed.", e);
        }
    }

    /**
     * <p>
     * Saves a member photo for the given file name and member id in the persistence.
     * </p>
     *
     * <p>
     * <em>Changes in 2.0:</em>
     * <ol>
     * <li>Added byUser parameter.</li>
     * <li>Added steps for initializing auditing properties.</li>
     * <li>Added steps for retrieving an existing or removed MemberImage from persistence.</li>
     * </ol>
     * </p>
     *
     * @param memberId
     *            the identifier of the member that photo should be saved in the persistence. Can be any value.
     * @param fileName
     *            the file name that should be saved for the member with the given id in the persistence. Should
     *            not be <code>null</code> or empty String.
     * @param byUser
     *            the username of the user that performs the operation. Cannot be null/empty.
     *
     * @throws IllegalStateException
     *             if the entityManager is not set (or it is set to a <code>null</code> value).
     * @throws IllegalArgumentException
     *             if fileName or byUser is <code>null</code> or empty String.
     * @throws MemberPhotoDAOException
     *             if any error occurs while performing this operation.
     */
    public void saveMemberPhoto(long memberId, String fileName, String byUser) throws MemberPhotoDAOException {
        ExceptionUtils.checkNullOrEmpty(fileName, null, null, "The parameter 'fileName' cannot be null or empty.");
        ExceptionUtils.checkNullOrEmpty(byUser, null, null, "The parameter 'byUser' cannot be null.");
        Helper.checkState(entityManager, "entityManager");

        Image image = new Image();
        image.setFileName(fileName);

        // Delegate to saveMemberPhoto(long, Image, String)
        saveMemberPhoto(memberId, image, byUser);
    }

    /**
     * <p>
     * Performs the deletion of a member photo for the given member id in the persistence.
     * </p>
     *
     * <p>
     * <em>Changes in 2.0:</em>
     * <ol>
     * <li>Added byUser parameter.</li>
     * <li>Using a query to retrieve MemberImage instance instead of entityManager.find().</li>
     * <li>MemberPhotoNotFoundException is thrown if member photo doesn't exist or is already removed.</li>
     * <li>Logical removal is used instead of physical one.</li>
     * </ol>
     * </p>
     *
     * @param memberId
     *            the identifier of the member that photo should be deleted from the persistence. Can be any value.
     * @param byUser
     *            the username of the user that performs the operation. Cannot be null/empty.
     *
     * @throws IllegalStateException
     *             if the entityManager is not set (or it is set to a <code>null</code> value).
     * @throws IllegalArgumentException
     *             if byUser is <code>null</code> or empty String.
     * @throws MemberPhotoNotFoundException
     *             if an member photo does not exists or is removed for the given member memberId in the persistence.
     * @throws MemberPhotoDAOException
     *             if any error occurs while performing this operation.
     */
    public void removeMemberPhoto(long memberId, String byUser) throws MemberPhotoNotFoundException,
        MemberPhotoDAOException {
        ExceptionUtils.checkNullOrEmpty(byUser, null, null, "The parameter 'byUser' cannot be null.");
        Helper.checkState(entityManager, "entityManager");
        try {
            MemberImage memberImage = getSingleResultRequired(SQL_QUERY_MEMBER_IMAGE_NOT_REMOVED, memberId, false);

            memberImage.setRemoved(true);
            memberImage.setUpdatedBy(byUser);
            memberImage.setUpdatedDate(new Date());

            entityManager.merge(memberImage);
        } catch (PersistenceException e) {
            throw new MemberPhotoDAOException("Failed to remove image.", e);
        } catch (IllegalStateException e) {
            throw new MemberPhotoDAOException("Failed to remove image. This EntityManager has been closed.", e);
        }
    }

    /**
     * <p>
     * Retrieves the member photos with optional pagination.
     * </p>
     *
     * @param pageSize
     *            the number of member images on the page (ignored if pageNo == 0).
     * @param pageNo
     *            the number of the page to be retrieved (0 if all member photos should be retrieved).
     *
     * @return the retrieved member photos (not null).
     *
     * @throws IllegalStateException
     *             if the entityManager is not set (or it is set to a <code>null</code> value).
     * @throws IllegalArgumentException
     *             if pageNo &lt; 0, (pageNo &gt; 0 and pageSize &lt;= 0).
     * @throws MemberPhotoDAOException
     *             if any error occurs while performing this operation.
     *
     * @since 2.0
     */
    @SuppressWarnings("unchecked")
    public PagedResult<MemberImage> getMemberPhotos(int pageNo, int pageSize) throws MemberPhotoDAOException {
        if (pageNo < 0) {
            throw new IllegalArgumentException("'pageNo' < 0.");
        }
        if ((pageNo > 0) && (pageSize <= 0)) {
            throw new IllegalArgumentException("'pageNo' > 0 and 'pageSize' <= 0.");
        }
        Helper.checkState(entityManager, "entityManager");

        try {
            // Execute the query:
            long totalRecords = (Long) getSingleResult(SQL_QUERY_MEMBER_IMAGE_NOT_REMOVED_COUNT, null, false);
            // Create query for retrieving the requested member images:
            Query query = entityManager.createQuery(SQL_QUERY_MEMBER_IMAGES_NOT_REMOVED);
            query.setParameter("removed", false);
            if (pageNo != 0) {
                // Set the index of the first member image on the page:
                query.setFirstResult((pageNo - 1) * pageSize);
                // Set the maximum number of member images to be retrieved:
                query.setMaxResults(pageSize);
            }
            // Execute the query:
            List<MemberImage> memberImages = query.getResultList();

            // Create result instance:
            PagedResult<MemberImage> result = new PagedResult<MemberImage>();
            // Set member images to the result:
            result.setRecords(memberImages);
            // Set the total number of member images to the result:
            result.setTotalRecords((int) totalRecords);

            return result;
        } catch (PersistenceException e) {
            throw new MemberPhotoDAOException("Failed to find the memeber images.", e);
        } catch (IllegalStateException e) {
            throw new MemberPhotoDAOException(
                "Failed to find the memeber images. This EntityManager has been closed.", e);
        }
    }

    /**
     * Gets the 'entityManager' property. Please refer to the related 'entityManager' field for more information.
     *
     * @return the value of the 'entityManager' property. It can be null.
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Sets the 'entityManager' property. Please refer to the related 'entityManager' field for more information.
     *
     * @param entityManager
     *            the new entityManager to be used for 'entityManager' property. The IoC should handle the validity
     *            if the passed value.
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * <p>
     * Gets a single result from the persistence.
     * </p>
     *
     * @param <T>
     *            the result type.
     * @param sql
     *            the SQL string.
     * @param memberId
     *            the member id.
     * @param removed
     *            the removed flag.
     *
     * @return the result.
     *
     * @throws IllegalStateException
     *             if the transaction is not active.
     * @throws MemberPhotoDAOException
     *             if more than one result found.
     * @throws PersistenceException
     *             if any error occurs while performing this operation.
     *
     * @since 2.0
     */
    @SuppressWarnings("unchecked")
    private <T> T getSingleResult(String sql, Long memberId, Boolean removed) throws MemberPhotoDAOException {
        Query query = entityManager.createQuery(sql);

        if (memberId != null) {
            query.setParameter("memberId", memberId);
        }
        if (removed != null) {
            query.setParameter("removed", removed);
        }

        List list = query.getResultList();

        int size = list.size();
        if (size == 0) {
            return null;
        }

        if (size > 1) {
            throw new MemberPhotoDAOException("More than one result found.");
        }

        return (T) list.get(0);
    }

    /**
     * <p>
     * Gets a single result from the persistence.
     * </p>
     *
     * @param <T>
     *            the result type.
     * @param sql
     *            the SQL string.
     * @param memberId
     *            the member id.
     * @param removed
     *            the removed flag.
     *
     * @return the result.
     *
     * @throws IllegalStateException
     *             if the transaction is not active.
     * @throws MemberPhotoNotFoundException
     *             if an member photo does not exists or is removed for the given member memberId in the persistence.
     * @throws MemberPhotoDAOException
     *             if more than one result found.
     * @throws PersistenceException
     *             if any error occurs while performing this operation.
     *
     * @since 2.0
     */
    @SuppressWarnings("unchecked")
    private <T> T getSingleResultRequired(String sql, Long memberId, Boolean removed)
        throws MemberPhotoNotFoundException, MemberPhotoDAOException {
        T result = (T) getSingleResult(sql, memberId, removed);

        if (result == null) {
            throw new MemberPhotoNotFoundException("There is no result found.", memberId);
        }

        return result;
    }
}
