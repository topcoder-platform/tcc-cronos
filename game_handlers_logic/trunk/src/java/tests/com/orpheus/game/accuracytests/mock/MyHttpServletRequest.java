/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests.mock;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import com.orpheus.game.accuracytests.AccuracyHelper;
import com.topcoder.user.profile.UserProfile;

public class MyHttpServletRequest extends HttpServletRequestWrapper {
    Map attributes = new Hashtable();

    Map parameters = new Hashtable();

    boolean nullSession = true;
    
    public MyHttpServletRequest(HttpServletRequest arg0) {
        super(arg0);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public String getParameter(String name) {
        return (String) parameters.get(name);
    }

    public void setParameter(String name, String value) {
        parameters.put(name, value);
    }

    public void setNullSesssion(boolean isNull) {
        nullSession = isNull;
    }
    
    public HttpSession getSession(boolean create) {
        if (nullSession) {
            return null;
        }
        return new HttpSession() {
            public Object getAttribute(String arg0) {
                if (nullSession) {
                    return null;
                }
                try {
                    return new UserProfile(new Long(AccuracyHelper.USER_PROFILE_ID));
                } catch (Exception e) {
                    return null;
                }
            }

            public Enumeration getAttributeNames() {
                return null;
            }

            public long getCreationTime() {
                return 0;
            }

            public String getId() {
                return null;
            }

            public long getLastAccessedTime() {
                return 0;
            }

            public int getMaxInactiveInterval() {
                return 0;
            }

            public ServletContext getServletContext() {
                return null;
            }

            public HttpSessionContext getSessionContext() {
                return null;
            }

            public Object getValue(String arg0) {
                return null;
            }

            public String[] getValueNames() {
                return null;
            }

            public void invalidate() {
            }

            public boolean isNew() {
                return false;
            }

            public void putValue(String arg0, Object arg1) {
            }

            public void removeAttribute(String arg0) {
            }

            public void removeValue(String arg0) {
            }

            public void setAttribute(String arg0, Object arg1) {
            }

            public void setMaxInactiveInterval(int arg0) {
            }
        };
    }
}
