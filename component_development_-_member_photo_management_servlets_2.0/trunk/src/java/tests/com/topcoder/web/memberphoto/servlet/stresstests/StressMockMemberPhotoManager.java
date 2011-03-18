/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet.stresstests;

import java.util.ArrayList;

import com.topcoder.web.memberphoto.entities.Image;
import com.topcoder.web.memberphoto.entities.MemberImage;
import com.topcoder.web.memberphoto.manager.MemberPhotoManagementException;
import com.topcoder.web.memberphoto.manager.MemberPhotoManager;
import com.topcoder.web.memberphoto.manager.PagedResult;

/**
 * Mock for MemberPhotoManager interface.
 * @author orange_cloud, mumujava
 * @version 2.0
 */
public class StressMockMemberPhotoManager implements MemberPhotoManager {
    /**
     * Not implemented method.
     * @param l unused
     * @param s unused
     */
    public void saveMemberPhoto(long l, String s) {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Not implemented method.
     * @param memberId not used
     * @param image not used
     */
    public void saveMemberPhoto(long memberId, Image image) {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Retrieves photo by the given id.
     * @param id photo id
     * @return photo retrieved
     */
    public Image getMemberPhoto(long id) {
        Image result = new Image();
        result.setFileName(id + ".jpeg");
        result.setId(id);
        return result;
    }

    /**
     * Supposed to remove photo, does nothing actually.
     * @param l not used
     */
    public void removeMemberPhoto(long l) {
    }

    /**
     * GetMemberPhotos.
     * @return pagedResult
     * @param arg0 NOT USED.
     * @param arg1 NOT USED.
     * @throws MemberPhotoManagementException never
     */
    public PagedResult<MemberImage> getMemberPhotos(int arg0, int arg1) throws MemberPhotoManagementException {

        PagedResult<MemberImage> pagedResult = new PagedResult<MemberImage>();
        ArrayList<MemberImage> arrayList = new ArrayList<MemberImage>();
        MemberImage o = new MemberImage();
        o.setCreatedBy("created by");
        arrayList.add(o);
        pagedResult.setRecords(arrayList);
        return pagedResult;
    }

    /**
     * Do nothing.
     * @param arg0 NOT USED.
     * @param arg1 NOT USED.
     * @throws MemberPhotoManagementException never
     */
    public void removeMemberPhoto(long arg0, String arg1) throws MemberPhotoManagementException {

    }

    /**
     * Do nothing.
     * @param arg0 NOT USED.
     * @param arg1 NOT USED.
     * @param arg2 NOT USED.
     * @throws MemberPhotoManagementException never
     */
    public void saveMemberPhoto(long arg0, Image arg1, String arg2) throws MemberPhotoManagementException {

    }

    /**
     * Do nothing.
     * @param arg0 NOT USED.
     * @param arg1 NOT USED.
     * @param arg2 NOT USED.
     * @throws MemberPhotoManagementException never
     */
    public void saveMemberPhoto(long arg0, String arg1, String arg2) throws MemberPhotoManagementException {

    }
}
