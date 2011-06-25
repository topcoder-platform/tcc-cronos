/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.photo;

import com.topcoder.web.memberphoto.entities.Image;
import com.topcoder.web.memberphoto.entities.MemberImage;
import com.topcoder.web.memberphoto.manager.MemberPhotoManagementException;
import com.topcoder.web.memberphoto.manager.MemberPhotoManager;
import com.topcoder.web.memberphoto.manager.PagedResult;
/**
 * <p>
 * A simple mock of {@link MemberPhotoManager}.
 * </p>
 *
 * @author mumujava
 * @version 1.0
 */
public class MockMemberPhotoManager implements MemberPhotoManager {
    /**
     * Returns a Image.
     * @param  arg0 not used
     * @throws MemberPhotoManagementException not implemented.
     * @return a Image.
     */
    public Image getMemberPhoto(long arg0) throws MemberPhotoManagementException {
        Image image = new Image();
        image.setFileName("image" + arg0 + ".gif");
        return image;
    }
    /**
     * Does nothing.
     * @param  arg0 not used
     * @param  arg1 not used
     * @throws MemberPhotoManagementException not implemented.
     * @return null
     */
    public PagedResult<MemberImage> getMemberPhotos(int arg0, int arg1) throws MemberPhotoManagementException {
        return null;
    }
    /**
     * Does nothing.
     * @param  arg0 not used
     * @param  arg1 not used
     * @throws MemberPhotoManagementException not implemented.
     */
    public void removeMemberPhoto(long arg0, String arg1) throws MemberPhotoManagementException {
    }
    /**
     * Does nothing.
     * @param  arg0 not used
     * @param  arg1 not used
     * @param  arg2 not used
     * @throws MemberPhotoManagementException not implemented.
     */
    public void saveMemberPhoto(long arg0, Image arg1, String arg2) throws MemberPhotoManagementException {
    }
    /**
     * Does nothing.
     * @param  arg0 not used
     * @param  arg1 not used
     * @param  arg2 not used
     * @throws MemberPhotoManagementException not implemented.
     */
    public void saveMemberPhoto(long arg0, String arg1, String arg2) throws MemberPhotoManagementException {
    }

}
