/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.photo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.memberphoto.manager.MemberPhotoManager;

/**
 * This class is a base for the member photo Struts actions. It encapsulates common properties and their validation
 *  routine.
 *
 * Thread Safety:
 * This class is mutable and technically not thread safe. However, it will not affect the thread safety of subclasses
 *  when used as Spring bean (which is the expected usage).
 *
 * @author gevak, mumujava
 * @version 1.0
 */
public abstract class BaseMemberPhotoAction extends ActionSupport implements ServletRequestAware {
    /**
     * <p>
     * The buffer size.
     * </p>
     */
    private static final int BUFFER_SIZE = 1024;

    /**
    * <p>
    * The serial version uid.
    * </p>
    */
    private static final long serialVersionUID = -263535122670049431L;

    /**
     * Key in HTTP session, under which the authentication data will be looked for. Expected to be used in execute()
     * method of subclasses. Mutable, has getter and setter. Technically can be any value. Expected to be injected by
     *  Spring and @PostConstruct method will ensure that it's not null and not empty.
     */
    private String authenticationSessionKey;

    /**
     * DAO used to get user info by it's ID (retrieved from authentication info contained in the HTTP session).
     * Expected to be used in execute() method of subclasses.  Mutable, has getter and setter. Technically can be any
     *  value. Expected to be injected by Spring and @PostConstruct method will ensure that it's not null.
     */
    private UserDAO userDAO;

    /**
     * DAO used to perform auditing. Expected to be used in execute() method of subclasses.
     * Mutable, has getter and setter. Technically can be any value. Expected to be injected by Spring and
     * @PostConstruct method will ensure that it's not null.
     */
    private AuditDAO auditDAO;

    /**
     * Manager used to manage member profile photo. Expected to be used in execute() method of subclasses.
     * Mutable, has getter and setter. Technically can be any value. Expected to be injected by Spring and
     * @PostConstruct method will ensure that it's not null.
     */
    private MemberPhotoManager memberPhotoManager;

    /**
     * Logger used to perform logging. Expected to be used in methods of subclasses. If null, then logging should not
     * be performed. Null be default. Mutable, has getter and setter. Technically can be any value. Expected to be
     * injected by Spring and @PostConstruct method will allow it to be any value.
     */
    private Log log;

    /**
     * Path of the directory where the current member profile photo images are stored. Expected to be used in
     *  execute() method of subclasses. Mutable, has getter and setter. Technically can be any value. Expected to be
injected by Spring and @PostConstruct method will ensure that it's not null and not empty.
     */
    private String photoStoredDirectory;

    /**
     * Default empty constructor.
     */
    protected BaseMemberPhotoAction() {
    }

    /**
     * This method will be called by Spring framework after initialization (configuration) of this bean.
     * It validates the configuration parameters.
     *
     * @throws MemberPhotoActionConfigurationException if any configuration parameter has
     *  invalid value as per class variable docs.
     */
    @PostConstruct
    public void checkParameters() {
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(authenticationSessionKey,
                "authenticationSessionKey", MemberPhotoActionConfigurationException.class);
        ValidationUtility.checkNotNull(userDAO, "userDAO", MemberPhotoActionConfigurationException.class);
        ValidationUtility.checkNotNull(auditDAO, "auditDAO", MemberPhotoActionConfigurationException.class);
        ValidationUtility.checkNotNull(memberPhotoManager, "memberPhotoManager",
                MemberPhotoActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(photoStoredDirectory, "photoStoredDirectory",
                MemberPhotoActionConfigurationException.class);
    }

    /**
     * Gets Key in HTTP session, under which the authentication data will be looked for.
     *
     * @return Key in HTTP session, under which the authentication data will be looked for.
     */
    public String getAuthenticationSessionKey() {
        return authenticationSessionKey;
    }

    /**
     * Sets Parameter name (i.e. a key in the HTTP session) under which the authentication data will be looked for.
     *
     * @param authenticationSessionKey Parameter name (i.e. a key in the HTTP session) under which the
     *  authentication data will be looked for.
     */
    public void setAuthenticationSessionKey(String authenticationSessionKey) {
        this.authenticationSessionKey = authenticationSessionKey;
    }

    /**
     * Gets DAO used to get user info by it's ID (retrieved from authentication info contained in the HTTP session).
     *
     * @return DAO used to get user info by it's ID (retrieved from authentication info contained in the HTTP session).
     */
    public UserDAO getUserDAO() {
        return userDAO;
    }

    /**
     * Sets DAO used to get user info by it's ID (retrieved from authentication info contained in the HTTP session).
     *
     * @param userDAO DAO used to get user info by it's ID (retrieved from authentication info contained
     *  in the HTTP session).
     */
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Gets DAO used to perform auditing.
     *
     * @return DAO used to perform auditing.
     */
    public AuditDAO getAuditDAO() {
        return auditDAO;
    }

    /**
     * Sets DAO used to perform auditing.
     *
     * @param auditDAO DAO used to perform auditing.
     */
    public void setAuditDAO(AuditDAO auditDAO) {
        this.auditDAO = auditDAO;
    }

    /**
     * Gets Manager used to manage member profile photo.
     *
     * @return Manager used to manage member profile photo.
     */
    public MemberPhotoManager getMemberPhotoManager() {
        return memberPhotoManager;
    }

    /**
     * Sets Manager used to manage member profile photo.
     *
     * @param memberPhotoManager Manager used to manage member profile photo.
     */
    public void setMemberPhotoManager(MemberPhotoManager memberPhotoManager) {
        this.memberPhotoManager = memberPhotoManager;
    }

    /**
     * Gets Logger used to perform logging.
     *
     * @return Logger used to perform logging.
     */
    public Log getLog() {
        return log;
    }

    /**
     * Sets Logger used to perform logging.
     *
     * @param log Logger used to perform logging.
     */
    public void setLog(Log log) {
        this.log = log;
    }

    /**
     * Gets Path of the directory where the current member profile photo images are stored.
     *
     * @return Path of the directory where the current member profile photo images are stored.
     */
    public String getPhotoStoredDirectory() {
        return photoStoredDirectory;
    }

    /**
     * Sets Path of the directory where the current member profile photo images are stored.
     *
     * @param photoStoredDirectory Path of the directory where the current member profile photo images are stored.
     */
    public void setPhotoStoredDirectory(String photoStoredDirectory) {
        this.photoStoredDirectory = photoStoredDirectory;
    }

    /**
     * <p>
     * Moves a file to a new path.
     * </p>
     * @param oldPath the old path.
     * @param newPath the new path.
     * @throws IOException when move file.
     * @return whether the moving is succesfully done
     */
    static boolean move(String oldPath, String newPath) throws IOException {
        InputStream is = null;
        FileOutputStream os = null;
        try {
            int byteread = 0;
            File oldFile = new File(oldPath);
            File newFile = new File(newPath);
            if (oldFile.exists()) {
                is = new FileInputStream(oldFile);
                os = new FileOutputStream(newFile);
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((byteread = is.read(buffer)) != -1) {
                    os.write(buffer, 0, byteread);
                }
                if (is != null) {
                    is.close();
                }
                oldFile.delete();
                return true;
            }
            return false;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } finally {
                if (os != null) {
                    os.close();
                }
            }
        }
    }

    /**
     * Log the request parameter.
     *
     * @param request the http request
     * */
    void logRequestParameter(HttpServletRequest request) {
        if (log != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("[Input parameters[");
            boolean first = true;
            for (Object key : request.getParameterMap().keySet()) {
                if (!first) {
                    sb.append(",");
                }
                sb.append("{" + key + "}:{" + request.getParameter((String) key) + "}");
                first = false;
            }
            sb.append("]]");
            log.log(Level.DEBUG, sb.toString());
        }
    }

}
