/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.ejb.user;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * <p>
 * This is a remote interface to the UserBean stateless session bean.
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> Implementations of this interface must be thread safe.
 * </p>
 * @author saarixx, kalc
 * @version 1.0
 */
public interface User extends EJBObject {
    /**
     * <p>
     * Creates a new user. The ID of the user is generated automatically.
     * </p>
     * @throws RemoteException
     *             if an RMI error occurred between client and EJB container
     * @param dataSource
     *            the data source
     * @param handle
     *            the handle of the user
     * @param status
     *            the status of the user
     * @param idDataSource
     *            the data source for the IDs
     * @return the generated ID of the user
     */
    public long createNewUser(String handle, char status, String dataSource, String idDataSource)
        throws RemoteException;

    /**
     * <p>
     * Creates a user with the specified parameters.
     * </p>
     * @throws RemoteException
     *             if an RMI error occurred between client and EJB container
     * @param dataSource
     *            the data source
     * @param handle
     *            the handle of the user
     * @param status
     *            the status of the user
     * @param userId
     *            the ID of the user
     */
    public void createUser(long userId, String handle, char status, String dataSource) throws RemoteException;

    /**
     * <p>
     * Sets first name of the user.
     * </p>
     * @throws RemoteException
     *             if an RMI error occurred between client and EJB container
     * @param dataSource
     *            the data source
     * @param userId
     *            the ID of the user
     * @param firstName
     *            the first name of the user
     */
    public void setFirstName(long userId, String firstName, String dataSource) throws RemoteException;

    /**
     * <p>
     * Sets middle name of the user.
     * </p>
     * @throws RemoteException
     *             if an RMI error occurred between client and EJB container
     * @param middleName
     *            the middle name of the user
     * @param dataSource
     *            the data source
     * @param userId
     *            the ID of the user
     */
    public void setMiddleName(long userId, String middleName, String dataSource) throws RemoteException;

    /**
     * <p>
     * Sets last name of the user.
     * </p>
     * @throws RemoteException
     *             if an RMI error occurred between client and EJB container
     * @param lastName
     *            the last name of the user
     * @param dataSource
     *            the data source
     * @param userId
     *            the ID of the user
     */
    public void setLastName(long userId, String lastName, String dataSource) throws RemoteException;

    /**
     * <p>
     * Sets status of the user.
     * </p>
     * @throws RemoteException
     *             if an RMI error occurred between client and EJB container
     * @param dataSource
     *            the data source
     * @param status
     *            the status of the user
     * @param userId
     *            the ID of the user
     */
    public void setStatus(long userId, char status, String dataSource) throws RemoteException;

    /**
     * <p>
     * Sets activation code of the user.
     * </p>
     * @throws RemoteException
     *             if an RMI error occurred between client and EJB container
     * @param dataSource
     *            the data source
     * @param userId
     *            the ID of the user
     * @param code
     *            the activation code of the user
     */
    public void setActivationCode(long userId, String code, String dataSource) throws RemoteException;

    /**
     * <p>
     * Sets password of the user.
     * </p>
     * @throws RemoteException
     *             if an RMI error occurred between client and EJB container
     * @param dataSource
     *            the data source
     * @param userId
     *            the ID of the user
     * @param password
     *            the password of the user
     */
    public void setPassword(long userId, String password, String dataSource) throws RemoteException;

    /**
     * <p>
     * d Sets handle of the user.
     * </p>
     * @throws RemoteException
     *             if an RMI error occurred between client and EJB container
     * @param dataSource
     *            the data source
     * @param handle
     *            the handle of the user
     * @param userId
     *            the ID of the user
     */
    public void setHandle(long userId, String handle, String dataSource) throws RemoteException;

    /**
     * <p>
     * Retrieves the first name of the user. If user with the given ID doesn't exist, EJBException is thrown.
     * </p>
     * @throws RemoteException
     *             if an RMI error occurred between client and EJB container
     * @param dataSource
     *            the data source
     * @param userId
     *            the ID of the user
     * @return the retrieved first name of the user
     */
    public String getFirstName(long userId, String dataSource) throws RemoteException;

    /**
     * <p>
     * Retrieves the middle name of the user. If user with the given ID doesn't exist, EJBException is thrown.
     * </p>
     * @throws RemoteException
     *             if an RMI error occurred between client and EJB container
     * @param dataSource
     *            the data source
     * @param userId
     *            the ID of the user
     * @return the retrieved middle name of the user
     */
    public String getMiddleName(long userId, String dataSource) throws RemoteException;

    /**
     * <p>
     * Retrieves the last name of the user. If user with the given ID doesn't exist, EJBException is thrown.
     * </p>
     * @throws RemoteException
     *             if an RMI error occurred between client and EJB container
     * @param dataSource
     *            the data source
     * @param userId
     *            the ID of the user
     * @return the retrieved last name of the user
     */
    public String getLastName(long userId, String dataSource) throws RemoteException;

    /**
     * <p>
     * Retrieves the status of the user. If user with the given ID doesn't exist, EJBException is thrown.
     * </p>
     * @throws RemoteException
     *             if an RMI error occurred between client and EJB container
     * @param dataSource
     *            the data source
     * @param userId
     *            the ID of the user
     * @return the retrieved status of the user
     */
    public char getStatus(long userId, String dataSource) throws RemoteException;

    /**
     * <p>
     * Retrieves the activation code of the user. If user with the given ID doesn't exist, EJBException is thrown.
     * </p>
     * @throws RemoteException
     *             if an RMI error occurred between client and EJB container
     * @param dataSource
     *            the data source
     * @param userId
     *            the ID of the user
     * @return the retrieved activation code of the user
     */
    public String getActivationCode(long userId, String dataSource) throws RemoteException;

    /**
     * <p>
     * Retrieves the handle of the user. If user with the given ID doesn't exist, EJBException is thrown.
     * </p>
     * @throws RemoteException
     *             if an RMI error occurred between client and EJB container
     * @param dataSource
     *            the data source
     * @param userId
     *            the ID of the user
     * @return the retrieved handle of the user
     */
    public String getHandle(long userId, String dataSource) throws RemoteException;

    /**
     * <p>
     * Retrieves the password of the user. If user with the given ID doesn't exist, EJBException is thrown.
     * </p>
     * @throws RemoteException
     *             if an RMI error occurred between client and EJB container
     * @param dataSource
     *            the data source
     * @param userId
     *            the ID of the user
     * @return the retrieved password of the user
     */
    public String getPassword(long userId, String dataSource) throws RemoteException;

    /**
     * <p>
     * Checks if a user with the specified ID exists in the data source.
     * </p>
     * @throws RemoteException
     *             if an RMI error occurred between client and EJB container
     * @param dataSource
     *            the data source
     * @param userId
     *            the ID of the user
     * @return true if user exists, false otherwise
     */
    public boolean userExists(long userId, String dataSource) throws RemoteException;

    /**
     * <p>
     * Checks if a user with the specified handle exists in the data source.
     * </p>
     * @throws RemoteException
     *             if an RMI error occurred between client and EJB container
     * @param dataSource
     *            the data source
     * @param handle
     *            the handle of the user
     * @return true if user exists, false otherwise
     */
    public boolean userExists(String handle, String dataSource) throws RemoteException;
}
