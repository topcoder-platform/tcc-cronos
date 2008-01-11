package com.jivesoftware.forum.mock;

import com.jivesoftware.base.AuthToken;
import com.jivesoftware.base.UnauthorizedException;
import com.jivesoftware.base.User;

import com.jivesoftware.forum.Attachment;
import com.jivesoftware.forum.AttachmentException;
import com.jivesoftware.forum.Forum;
import com.jivesoftware.forum.ForumMessage;
import com.jivesoftware.forum.ForumThread;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class MockForumMessage implements ForumMessage {
    public long getID() {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getForumIndex() {
        // TODO Auto-generated method stub
        return 0;
    }

    public Date getCreationDate() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setCreationDate(Date arg0) throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public Date getModificationDate() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setModificationDate(Date arg0) throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public String getSubject() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getUnfilteredSubject() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setSubject(String arg0) throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public String getBody() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getUnfilteredBody() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setBody(String arg0) throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public User getUser() {
        // TODO Auto-generated method stub
        return null;
    }

    public ForumMessage getParentMessage() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attachment createAttachment(String arg0, String arg1,
        InputStream arg2) throws AttachmentException, UnauthorizedException {
        // TODO Auto-generated method stub
        return null;
    }

    public int getAttachmentCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void deleteAttachment(Attachment arg0)
        throws AttachmentException, UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public Iterator getAttachments() {
        List<Attachment> list = new ArrayList<Attachment>();
        list.add(new MockAttachment());

        return list.iterator();
    }

    public int getModerationValue() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setModerationValue(int arg0, AuthToken arg1)
        throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public String getProperty(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public Collection getProperties(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getUnfilteredProperty(String arg0) {
        return null;
    }

    public void setProperty(String arg0, String arg1)
        throws UnauthorizedException {
    }

    public void deleteProperty(String arg0) throws UnauthorizedException {
    }

    public Iterator getPropertyNames() {
        List<String> list = new ArrayList<String>();
        list.add("sss");

        return list.iterator();
    }

    public boolean isAnonymous() {
        return false;
    }

    public ForumThread getForumThread() {
        return null;
    }

    public Forum getForum() {
        return null;
    }

    public boolean isAuthorized(long arg0) {
        return false;
    }

    public boolean isHtml() {
        return false;
    }
}
