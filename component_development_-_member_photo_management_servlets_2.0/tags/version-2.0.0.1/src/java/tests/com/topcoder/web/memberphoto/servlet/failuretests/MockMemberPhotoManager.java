/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet.failuretests;

import com.topcoder.web.memberphoto.entities.Image;
import com.topcoder.web.memberphoto.entities.MemberImage;

import com.topcoder.web.memberphoto.manager.MemberPhotoManagementException;
import com.topcoder.web.memberphoto.manager.MemberPhotoManager;
import com.topcoder.web.memberphoto.manager.PagedResult;

/**
 * Mock implementation of MemberPhotoManager interface.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockMemberPhotoManager implements MemberPhotoManager {
    /**
     * Default ctor.
     */
    public MockMemberPhotoManager() {
        // Empty.
    }

    /**
     * Mock implementation.
     *
     * @param memberId
     *            the identifier of the member that photo should be retrieved from the persistence.
     *
     * @return the member image corresponding to the given member id retrieved from the persistence.
     *
     * @throws MemberPhotoManagementException
     *             if memberId is 101.
     */
    public Image getMemberPhoto(long memberId) throws MemberPhotoManagementException {
        if (memberId == 101) {
            throw new MemberPhotoManagementException("");
        }
        Image image = new Image();
        image.setId(memberId);
        image.setFileName("test_files/failure/submitted/1.jpeg");
        return image;
    }

    /**
     * Mock implementation.
     *
     * @param memberId
     *            the identifier of the member that photo should be saved in the persistence.
     * @param image
     *            the image that should be saved for the member with the given id in the persistence.
     * @param byUser
     *            the user name of the user that performs the operation.
     *
     * @throws MemberPhotoManagementException
     *             if memberId is 101.
     */
    public void saveMemberPhoto(long memberId, Image image, String byUser) throws MemberPhotoManagementException {
        if (memberId == 101) {
            throw new MemberPhotoManagementException("");
        }
    }

    /**
     * Mock implementation.
     *
     * @param memberId
     *            the identifier of the member that photo should be saved in the persistence.
     * @param fileName
     *            the file name that should be saved for the member with the given id in the persistence.
     * @param byUser
     *            the user name of the user that performs the operation.
     *
     * @throws MemberPhotoManagementException
     *             if memberId is 101.
     */
    public void saveMemberPhoto(long memberId, String fileName, String byUser) throws MemberPhotoManagementException {
        if (memberId == 101) {
            throw new MemberPhotoManagementException("");
        }
    }

    /**
     * Mock implementation.
     *
     * @param memberId
     *            the identifier of the member that photo should be deleted from the persistence.
     * @param byUser
     *            the user name of the user that performs the operation.
     *
     * @throws MemberPhotoManagementException
     *             if memberId is 101.
     */
    public void removeMemberPhoto(long memberId, String byUser) throws MemberPhotoManagementException {
        if (memberId == 101) {
            throw new MemberPhotoManagementException("");
        }
    }

    /**
     * Mock implementation.
     *
     * @param pageNo
     *            the number of the page to be retrieved.
     * @param pageSize
     *            the number of member images on the page.
     *
     * @return the retrieved member photos (not null).
     *
     * @throws MemberPhotoManagementException
     *             if pageNo is 101.
     */
    public PagedResult<MemberImage> getMemberPhotos(int pageNo, int pageSize) throws MemberPhotoManagementException {
        if (pageNo == 101) {
            throw new MemberPhotoManagementException("");
        }
        return null;
    }
}