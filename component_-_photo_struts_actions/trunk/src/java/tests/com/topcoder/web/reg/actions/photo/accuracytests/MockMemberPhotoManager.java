/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.photo.accuracytests;

import com.topcoder.web.memberphoto.entities.Image;
import com.topcoder.web.memberphoto.entities.MemberImage;
import com.topcoder.web.memberphoto.manager.MemberPhotoManagementException;
import com.topcoder.web.memberphoto.manager.MemberPhotoManager;
import com.topcoder.web.memberphoto.manager.PagedResult;

/**
 * Mock class for test.
 *
 * @author extra
 * @version 1.0
 */
public class MockMemberPhotoManager implements MemberPhotoManager {
    public Image getMemberPhoto(long arg0) throws MemberPhotoManagementException {
        Image image = new Image();
        image.setFileName("brandingLogo.png");
        image.setId(arg0);
        return image;
    }

    public PagedResult<MemberImage> getMemberPhotos(int arg0, int arg1) throws MemberPhotoManagementException {
        return null;
    }

    public void removeMemberPhoto(long arg0, String arg1) throws MemberPhotoManagementException {
    }

    public void saveMemberPhoto(long arg0, Image arg1, String arg2) throws MemberPhotoManagementException {
    }

    public void saveMemberPhoto(long arg0, String arg1, String arg2) throws MemberPhotoManagementException {
    }

}
