package com.topcoder.management.project;

/**
 */
public interface ApplicationsManager {
    public ReviewApplication create(ReviewApplication reviewApplication) throws ApplicationsManagerException;
    public ReviewApplication update(ReviewApplication reviewApplication) throws ApplicationsManagerException;
    public ReviewApplication retrieve(long id) throws ApplicationsManagerException;
    public boolean delete(long id) throws ApplicationsManagerException;

    public ReviewApplication[] getPrimaryApplications(long projectId) throws ApplicationsManagerException;
    public ReviewApplication[] getSecondaryApplications(long projectId) throws ApplicationsManagerException;
    public ReviewApplication[] getAllApplications(long projectId) throws ApplicationsManagerException;
}