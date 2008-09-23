package com.topcoder.controlpanel.clientassociations;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.topcoder.controlpanel.clientassociations.dao.ClientAssociationDAO;
import com.topcoder.controlpanel.clientassociations.dao.hibernate.ClientAssociationHibernateDAO;
import com.topcoder.naming.jndiutility.JNDIUtils;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ClientAssociationServiceBean implements ClientAssociationService {
    //@Resource(name="dao/clientAssociationDAO")
    private ClientAssociationDAO dao;
    
    public ClientAssociationServiceBean() {
    }

    @PostConstruct
    protected void initialize() {
    	try {
    		dao = (ClientAssociationDAO) JNDIUtils.getObject("dao/clientAssociationDAO");
    	} catch (Exception e) {
    		// ignore
    	}
    	if (dao == null) {
    		dao = new ClientAssociationHibernateDAO();
    	}
    }
    @PreDestroy
    protected void cleanUp() {
    	dao = null;
    }
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void assignComponent(long componentId, int clientId) throws ClientAssociationServiceException {
        dao.assignComponent(componentId, clientId);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void assignUser(long userId, int clientId, boolean isAdmin) throws ClientAssociationServiceException {
        dao.assignUser(userId, clientId, isAdmin);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Integer> getClientsByComponent(long componentId) throws ClientAssociationServiceException {
        return dao.getClientsByComponent(componentId);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Integer> getClientsByUser(long userId) throws ClientAssociationServiceException {
        return dao.getClientsByUser(userId);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Long> getComponentsByClient(int clientId) throws ClientAssociationServiceException {
        return dao.getComponentsByClient(clientId);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Long> getComponentsByUser(long userId) throws ClientAssociationServiceException {
        return dao.getComponentsByUser(userId);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Long> getUsers(int clientId) throws ClientAssociationServiceException {
        return dao.getUsers(clientId);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean isAdmin(long userId, int clientId) throws ClientAssociationServiceException {
        return dao.isAdmin(userId, clientId);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void removeComponent(long componentId, int clientId) throws ClientAssociationServiceException {
        dao.removeComponent(componentId, clientId);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void removeUser(long userId, int clientId) throws ClientAssociationServiceException {
        dao.removeUser(userId, clientId);
    }
}
