package com.topcoder.controlpanel.clientassociations.dao;

import java.util.List;

public interface ClientAssociationDAO {
    void assignComponent(long componentId, int clientId) throws ClientAssociationDAOException;

    void assignUser(long userId, int clientId, boolean isAdmin) throws ClientAssociationDAOException;

    void removeComponent(long componentId, int clientId) throws ClientAssociationDAOException;

    void removeUser(long userId, int clientId) throws ClientAssociationDAOException;

    List<Long> getComponentsByClient(int clientId) throws ClientAssociationDAOException;

    List<Long> getUsers(int clientId) throws ClientAssociationDAOException;

    boolean isAdmin(long userId, int clientId) throws ClientAssociationDAOException;

    List<Integer> getClientsByComponent(long componentId) throws ClientAssociationDAOException;

    List<Integer> getClientsByUser(long userId) throws ClientAssociationDAOException;

    List<Long> getComponentsByUser(long userId) throws ClientAssociationDAOException;
}