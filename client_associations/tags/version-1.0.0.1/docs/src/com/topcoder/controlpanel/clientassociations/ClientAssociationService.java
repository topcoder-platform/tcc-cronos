package com.topcoder.controlpanel.clientassociations;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface ClientAssociationService {
    void assignComponent(long componentId, int clientId) throws ClientAssociationServiceException;

    void assignUser(long userId, int clientId, boolean isAdmin) throws ClientAssociationServiceException;

    void removeComponent(long componentId, int clientId) throws ClientAssociationServiceException;

    void removeUser(long userId, int clientId) throws ClientAssociationServiceException;

    List<Long> getComponentsByClient(int clientId) throws ClientAssociationServiceException;

    List<Long> getUsers(int clientId) throws ClientAssociationServiceException;

    boolean isAdmin(long userId, int clientId) throws ClientAssociationServiceException;

    List<Integer> getClientsByComponent(long componentId) throws ClientAssociationServiceException;

    List<Integer> getClientsByUser(long userId) throws ClientAssociationServiceException;

    List<Long> getComponentsByUser(long userId) throws ClientAssociationServiceException;
}