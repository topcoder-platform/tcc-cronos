/*
 * Copyright (C) 2008, 2011 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.web.memberphoto.manager;

import com.topcoder.web.memberphoto.entities.Image;
import com.topcoder.web.memberphoto.entities.MemberImage;

/**
 * <p>
 * This interface represents the MemberPhotoDAO layer interface. This is a pluggable strategy supporting any kind of
 * the concrete implementation (like different databases, JPA, XML based persistence and so on).
 * </p>
 *
 * <p>
 * This interface defines the specific methods available for the MemberPhotoDAO interface: retrieve member photo, save
 * member photo and remove member photo.
 * </p>
 *
 * <p>
 * <em>Changes in 2.0:</em>
 * <ol>
 * <li>Added byUser parameter to saveMemberPhoto() and removeMemberPhoto() methods (to be used for auditing).</li>
 * <li>Added getMemberPhotos() method for retrieving recently added/updated member images.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Thread Safety: Implementations of this interface should be thread safe.
 * </p>
 *
 * @author Mafy, cyberjag, saarixx, sparemax
 * @version 2.0
 */
public interface MemberPhotoDAO {

    /**
     * <p>
     * Defines the operation that performs the retrieval of a member photo using the given member id from the
     * persistence.
     * </p>
     *
     * <p>
     * <em>Changes in 2.0:</em>
     * <ol>
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
     */
    public Image getMemberPhoto(long memberId) throws MemberPhotoDAOException;

    /**
     * <p>
     * Defines the operation that performs the save of a member photo for the given image and member id in the
     * persistence.
     * </p>
     *
     * <p>
     * <em>Changes in 2.0:</em>
     * <ol>
     * <li>Added byUser parameter.</li>
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
     * @throws IllegalArgumentException
     *             if image is <code>null</code> or byUser is null/empty.
     * @throws MemberPhotoDAOException
     *             if any error occurs while performing this operation.
     */
    public void saveMemberPhoto(long memberId, Image image, String byUser) throws MemberPhotoDAOException;

    /**
     * <p>
     * Defines the operation that performs the save of a member photo for the given file name and member id in the
     * persistence.
     * </p>
     *
     * <p>
     * <em>Changes in 2.0:</em>
     * <ol>
     * <li>Added byUser parameter.</li>
     * </ol>
     * </p>
     *
     * @param memberId
     *            the identifier of the member that photo should be saved in the persistence. Can be any value.
     * @param fileName
     *            the file name that should be saved for the member with the given id in the persistence. Should not
     *            be <code>null</code> or empty String.
     * @param byUser
     *            the username of the user that performs the operation. Cannot be null/empty.
     *
     * @throws IllegalArgumentException
     *             if fileName or byUser is <code>null</code> or empty String.
     * @throws MemberPhotoDAOException
     *             if any error occurs while performing this operation.
     */
    public void saveMemberPhoto(long memberId, String fileName, String byUser) throws MemberPhotoDAOException;

    /**
     * <p>
     * Defines the operation that performs the deletion of a member photo for the given member id in the persistence.
     * </p>
     *
     * <p>
     * <em>Changes in 2.0:</em>
     * <ol>
     * <li>Added byUser parameter.</li>
     * <li>Method can throw MemberPhotoNotFoundException.</li>
     * </ol>
     * </p>
     *
     * @param memberId
     *            the identifier of the member that photo should be deleted from the persistence. Can be any value.
     * @param byUser
     *            the username of the user that performs the operation. Cannot be null/empty.
     *
     * @throws IllegalArgumentException
     *             if byUser is <code>null</code> or empty String.
     * @throws MemberPhotoNotFoundException
     *             if an member photo does not exists or is removed for the given member memberId in the persistence.
     * @throws MemberPhotoDAOException
     *             if any error occurs while performing this operation.
     */
    public void removeMemberPhoto(long memberId, String byUser) throws MemberPhotoNotFoundException,
        MemberPhotoDAOException;

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
     * @throws IllegalArgumentException
     *             if pageNo &lt; 0, (pageNo &gt; 0 and pageSize &lt;= 0).
     * @throws MemberPhotoDAOException
     *             if any error occurs while performing this operation.
     *
     * @since 2.0
     */
    public PagedResult<MemberImage> getMemberPhotos(int pageNo, int pageSize) throws MemberPhotoDAOException;
}
