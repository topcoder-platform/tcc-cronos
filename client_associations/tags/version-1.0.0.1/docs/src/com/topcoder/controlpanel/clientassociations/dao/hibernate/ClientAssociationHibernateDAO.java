package com.topcoder.controlpanel.clientassociations.dao.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.topcoder.controlpanel.clientassociations.dao.ClientAssociationDAO;
import com.topcoder.controlpanel.clientassociations.dao.ClientAssociationDAOException;

public class ClientAssociationHibernateDAO implements ClientAssociationDAO {


    public ClientAssociationHibernateDAO() {
    }

    public void assignComponent(long componentId, int clientId) throws ClientAssociationDAOException {
        CompClientPK ccpk = new CompClientPK(componentId, clientId);
        CompClient cc = new CompClient(ccpk);

        Session session = HibernateHelper.getSessionFactory().openSession();
        session.save(cc);
        session.flush();
        session.close();
    }

    public void assignUser(long userId, int clientId, boolean isAdmin) throws ClientAssociationDAOException {
        UserClientPK ucpk = new UserClientPK(userId, clientId);
        UserClient uc = new UserClient(ucpk);
        uc.setAdminInd(isAdmin ? 1 : 0);

        Session session = HibernateHelper.getSessionFactory().openSession();
        session.save(uc);
        session.flush();
        session.close();
    }

    public List<Integer> getClientsByComponent(long componentId) throws ClientAssociationDAOException {
        Session session = HibernateHelper.getSessionFactory().openSession();
        Query query = session.createQuery("from CompClient cc where cc.comp_id.componentId=:id");
        query.setLong("id", componentId);
        List<Integer> clientIds = new ArrayList<Integer>();
        List res = query.list();
        for (Iterator iter = res.iterator(); iter.hasNext();) {
            clientIds.add(((CompClient) iter.next()).getClient().getClientId());
        }
        session.close();
        return clientIds;
    }

    public List<Integer> getClientsByUser(long userId) throws ClientAssociationDAOException {
        Session session = HibernateHelper.getSessionFactory().openSession();
        Query query = session.createQuery("from UserClient cc where cc.comp_id.userId=:id");
        query.setLong("id", userId);
        List<Integer> clientIds = new ArrayList<Integer>();
        List res = query.list();
        for (Iterator iter = res.iterator(); iter.hasNext();) {
            clientIds.add(((UserClient) iter.next()).getClient().getClientId());
        }
        session.close();
        return clientIds;
    }

    public List<Long> getComponentsByClient(int clientId) throws ClientAssociationDAOException {
        Session session = HibernateHelper.getSessionFactory().openSession();
        Query query = session.createQuery("from CompClient cc where cc.client.clientId=:id");
        query.setInteger("id", clientId);
        List<Long> componentIds = new ArrayList<Long>();
        List res = query.list();
        for (Iterator iter = res.iterator(); iter.hasNext();) {
            componentIds.add(((CompClient) iter.next()).getComp_id().getComponentId());
        }
        session.close();
        return componentIds;
    }

    public List<Long> getComponentsByUser(long userId) throws ClientAssociationDAOException {
        Session session = HibernateHelper.getSessionFactory().openSession();
        Query query = session.createQuery("select cc from CompClient cc, UserClient uc where cc.client.clientId = uc.client.clientId and uc.comp_id.userId=:id");
        query.setLong("id", userId);
        List<Long> componentIds = new ArrayList<Long>();
        List res = query.list();
        for (Iterator iter = res.iterator(); iter.hasNext();) {
            componentIds.add(((CompClient) iter.next()).getComp_id().getComponentId());
        }
        session.close();
        return componentIds;
    }

    public List<Long> getUsers(int clientId) throws ClientAssociationDAOException {
        Session session = HibernateHelper.getSessionFactory().openSession();
        Query query = session.createQuery("from UserClient uc where uc.client.clientId=:id");
        query.setInteger("id", clientId);
        List<Long> userIds = new ArrayList<Long>();
        List res = query.list();
        for (Iterator iter = res.iterator(); iter.hasNext();) {
            userIds.add(((UserClient) iter.next()).getComp_id().getUserId());
        }
        session.close();
        return userIds;
    }

    public boolean isAdmin(long userId, int clientId) throws ClientAssociationDAOException {
        Session session = HibernateHelper.getSessionFactory().openSession();
        Query query = session.createQuery("from UserClient uc where uc.client.clientId=:clientId and uc.comp_id.userId=:userId");
        query.setInteger("clientId", clientId);
        query.setLong("userId", userId);
        List res = query.list();
        session.close();
        if (res.size() == 1) {
            return ((UserClient) res.get(0)).getAdminInd() == 1? true : false;
        }
        throw new IllegalArgumentException();
    }

    public void removeComponent(long componentId, int clientId) throws ClientAssociationDAOException {
        CompClientPK ccpk = new CompClientPK(componentId, clientId);
        CompClient cc = new CompClient(ccpk);

        Session session = HibernateHelper.getSessionFactory().openSession();
        session.delete(cc);
        session.flush();
        session.close();
    }

    public void removeUser(long userId, int clientId) throws ClientAssociationDAOException {
        UserClientPK ucpk = new UserClientPK(userId, clientId);
        UserClient uc = new UserClient(ucpk);

        Session session = HibernateHelper.getSessionFactory().openSession();
        session.delete(uc);
        session.flush();
        session.close();
    }
}
