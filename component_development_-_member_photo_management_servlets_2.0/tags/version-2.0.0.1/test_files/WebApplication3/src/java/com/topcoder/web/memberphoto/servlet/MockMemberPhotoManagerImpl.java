/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet;

import com.topcoder.web.memberphoto.entities.Image;
import com.topcoder.web.memberphoto.entities.MemberImage;
import com.topcoder.web.memberphoto.manager.MemberPhotoManagementException;
import com.topcoder.web.memberphoto.manager.MemberPhotoManager;
import com.topcoder.web.memberphoto.manager.MemberPhotoNotFoundException;
import com.topcoder.web.memberphoto.manager.PagedResult;

/**
 * <p>
 * Mock implementation for <code>MemberPhotoManager</code> class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockMemberPhotoManagerImpl implements MemberPhotoManager {
    /**
     * Get member photo by member id.
     * @param memberId
     *            The member id.
     * @return The photo of the member.
     * @throws MemberPhotoManagementException
     *             If error occurred
     */
    public Image getMemberPhoto(long memberId)
        throws MemberPhotoManagementException {
        Image image = new Image();
        image.setFileName("photo");

        if (memberId == 0) {
            return null;
        } else if (memberId == -1) {
            throw new MemberPhotoManagementException(
                    "Member photo mangement exception occurs.");
        } else if (memberId == -2) {
            throw new MemberPhotoNotFoundException(
                    "Member photo mangement exception occurs.", memberId);
        } else {
            return image;
        }
    }

    /**
     * Get list.
     * @param pageNo
     *            The number of the page
     * @param pageSize the size page shows.
     * @return PagedResult list.
     * @throws MemberPhotoManagementException
     *             If error occurred
     */
    public PagedResult < MemberImage > getMemberPhotos(int pageNo, int pageSize)
        throws MemberPhotoManagementException {
        return new PagedResult < MemberImage >();
    }

    /**
     * Remove photo.
     * @param memberId
     *            The member id.
     * @param byuser the username.
     * @throws MemberPhotoManagementException
     *             If error occurred
     */
    public void removeMemberPhoto(long memberId, String byuser)
        throws MemberPhotoManagementException {
        // ignore

    }

    /**
     * Save member photo.
     * @param memberId
     *            the member id
     * @param image
     *            The file name
     * @param byuser the username.
     * @throws MemberPhotoManagementException
     *             If error occurred
     */
    public void saveMemberPhoto(long memberId, Image image, String byuser)
        throws MemberPhotoManagementException {
        if (memberId < 0) {
            throw new MemberPhotoManagementException(
                    "MemberPhotoManagementException");
        }

    }

    /**
     * Save member photo.
     * @param memberId
     *            the member id
     * @param filename
     *            The file name
     * @param byuser the username.
     * @throws MemberPhotoManagementException
     *             If error occurred
     */
    public void saveMemberPhoto(long memberId, String filename, String byuser)
        throws MemberPhotoManagementException {
        if (memberId < 0) {
            throw new MemberPhotoManagementException(
                    "MemberPhotoManagementException");
        }

    }

}
