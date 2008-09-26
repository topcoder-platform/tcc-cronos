package com.jivesoftware.forum.mock;

import com.jivesoftware.base.UnauthorizedException;

import com.jivesoftware.forum.Attachment;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;


public class MockAttachment implements Attachment {
    public long getID() {
        // TODO Auto-generated method stub
        return 0;
    }

    public String getContentType() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setName(String arg0) throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public long getSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    public InputStream getData() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    public Date getCreationDate() {
        // TODO Auto-generated method stub
        return null;
    }

    public Date getModificationDate() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getProperty(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public Collection getProperties(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public void setProperty(String arg0, String arg1)
        throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public void deleteProperty(String arg0) throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public Iterator getPropertyNames() {
        // TODO Auto-generated method stub
        return null;
    }
}
