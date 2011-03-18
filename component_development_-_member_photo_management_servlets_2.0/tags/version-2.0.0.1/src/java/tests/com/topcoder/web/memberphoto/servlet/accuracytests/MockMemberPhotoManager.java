/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet.accuracytests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.web.memberphoto.entities.Image;
import com.topcoder.web.memberphoto.entities.MemberImage;
import com.topcoder.web.memberphoto.manager.MemberPhotoManagementException;
import com.topcoder.web.memberphoto.manager.MemberPhotoManager;
import com.topcoder.web.memberphoto.manager.MemberPhotoNotFoundException;
import com.topcoder.web.memberphoto.manager.PagedResult;

/**
 * Mock implementation of <code>MemberPhotoManager</code> interface.
 * @author TCSDEVELOPER, sokol
 * @version 2.0
 */
public class MockMemberPhotoManager implements MemberPhotoManager {

    /**
     * Get member photo by member id.
     * @param memberId The member id.
     * @return The photo of the member.
     * @throws MemberPhotoManagementException If error occurred
     * @throws MemberPhotoNotFoundException If cannot find
     */
    public Image getMemberPhoto(long memberId) throws MemberPhotoManagementException {
        if (memberId == 1021) {
            throw new MemberPhotoNotFoundException("Cannot find", memberId);
        }
        if (memberId == 1022) {
            throw new MemberPhotoManagementException("Error");
        }
        if (memberId == 1031) {
            return null;
        }
        Image image = new Image();
        image.setFileName(memberId + ".jpeg");
        return image;
    }

    /**
     * <p>
     * This is mock implementation. Do nothing.
     * </p>
     * @param pageNo the page number
     * @param pageSize the page size
     * @return null
     * @throws MemberPhotoManagementException never
     */
    public PagedResult<MemberImage> getMemberPhotos(int pageNo, int pageSize)
        throws MemberPhotoManagementException {
        PagedResult<MemberImage> result = new PagedResult<MemberImage>();
        List<MemberImage> records = new ArrayList<MemberImage>();
        MemberImage image = new MemberImage();
        image.setMemberId(1L);
        image.setImage(new Image());
        records.add(image);
        result.setRecords(records);
        return result;
    }

    /**
     * <p>
     * This is mock implementation. Do nothing.
     * </p>
     * @param memberId
     * @param byUser
     * @throws MemberPhotoManagementException never
     */
    public void removeMemberPhoto(long memberId, String byUser) throws MemberPhotoManagementException {
    }

    /**
     * <p>
     * This is mock implementation. Do nothing.
     * </p>
     * @param memberId the member id
     * @param image the image
     * @param byUser the user
     * @throws MemberPhotoManagementException never
     */
    public void saveMemberPhoto(long memberId, Image image, String byUser)
        throws MemberPhotoManagementException {
    }

    /**
     * <p>
     * This is mock implementation. Do nothing.
     * </p>
     * @param memberId the member id
     * @param fileName the file name
     * @param byUser the user
     * @throws MemberPhotoManagementException never
     */
    public void saveMemberPhoto(long memberId, String fileName, String byUser)
        throws MemberPhotoManagementException {
    }
}