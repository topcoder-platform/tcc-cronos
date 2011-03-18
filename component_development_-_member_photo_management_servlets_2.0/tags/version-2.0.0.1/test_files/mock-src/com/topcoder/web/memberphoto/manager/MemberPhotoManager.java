package com.topcoder.web.memberphoto.manager;

import com.topcoder.web.memberphoto.entities.Image;


public interface MemberPhotoManager {
	public void saveMemberPhoto(long memberId, String fileName) throws MemberPhotoManagementException;
	public Image getMemberPhoto(long memberId) throws MemberPhotoManagementException,MemberPhotoNotFoundException;
	public void removeMemberPhoto(long memberId) throws MemberPhotoManagementException;
}
