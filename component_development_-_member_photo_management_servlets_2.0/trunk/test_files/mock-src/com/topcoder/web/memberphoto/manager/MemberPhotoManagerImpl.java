package com.topcoder.web.memberphoto.manager;

import com.topcoder.web.memberphoto.entities.Image;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
 */
public class MemberPhotoManagerImpl implements MemberPhotoManager {
    /**
     * DOCUMENT ME!
     *
     * @param memberId DOCUMENT ME!
     * @param fileName DOCUMENT ME!
     *
     * @throws MemberPhotoManagementException DOCUMENT ME!
     */
    public void saveMemberPhoto(long memberId, String fileName)
        throws MemberPhotoManagementException {
        if (memberId < 0) {
            throw new MemberPhotoManagementException("MemberPhotoManagementException");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param memberId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws MemberPhotoManagementException DOCUMENT ME!
     * @throws MemberPhotoNotFoundException DOCUMENT ME!
     */
    public Image getMemberPhoto(long memberId) throws MemberPhotoManagementException, MemberPhotoNotFoundException {
        Image image = new Image();
        image.setFileName("photo");

        if (memberId == 0) {
            return null;
        } else if (memberId == -1) {
            throw new MemberPhotoManagementException("Member photo mangement exception occurs.");
        } else if (memberId == -2) {
            throw new MemberPhotoNotFoundException("Member photo mangement exception occurs.", memberId);
        } else {
            return image;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param memberId DOCUMENT ME!
     *
     * @throws MemberPhotoManagementException DOCUMENT ME!
     */
    public void removeMemberPhoto(long memberId) throws MemberPhotoManagementException {
    }
}
