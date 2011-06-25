package com.topcoder.web.reg.actions.photo.failuretests;

import com.topcoder.web.memberphoto.entities.Image;
import com.topcoder.web.memberphoto.entities.MemberImage;
import com.topcoder.web.memberphoto.manager.MemberPhotoManagementException;
import com.topcoder.web.memberphoto.manager.MemberPhotoManager;
import com.topcoder.web.memberphoto.manager.PagedResult;

public class FailureMemeberPhotoManager implements MemberPhotoManager {
    private Image image;
    /**
     * @param image the image to set
     */
    public void setImage(Image image) {
        this.image = image;
    }

    public Image getMemberPhoto(long arg0)
            throws MemberPhotoManagementException {
        if (image == null) {
            throw new MemberPhotoManagementException("unable to find photo");
        }
        return image;
    }

    public PagedResult<MemberImage> getMemberPhotos(int arg0, int arg1)
            throws MemberPhotoManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public void removeMemberPhoto(long arg0, String arg1)
            throws MemberPhotoManagementException {
        // TODO Auto-generated method stub

    }

    public void saveMemberPhoto(long arg0, Image arg1, String arg2)
            throws MemberPhotoManagementException {
        // TODO Auto-generated method stub

    }

    public void saveMemberPhoto(long arg0, String arg1, String arg2)
            throws MemberPhotoManagementException {
        // TODO Auto-generated method stub

    }

}
