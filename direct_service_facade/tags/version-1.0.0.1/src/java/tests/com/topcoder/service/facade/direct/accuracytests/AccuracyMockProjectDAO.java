package com.topcoder.service.facade.direct.accuracytests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.dao.ProjectDAO;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.search.builder.filter.Filter;

public class AccuracyMockProjectDAO implements ProjectDAO {

    public AccuracyMockProjectDAO() {
    }

    public void addUserToBillingProjects(String arg0, long[] arg1) throws DAOException {
        // TODO Auto-generated method stub

    }

    public boolean checkClientProjectPermission(String arg0, long arg1) throws DAOException {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean checkPoNumberPermission(String arg0, String arg1) throws DAOException {
        // TODO Auto-generated method stub
        return false;
    }

    public List<ProjectContestFee> getContestFeesByProject(long arg0) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Project> getProjectsByClientId(long arg0) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Project> getProjectsByUser(String arg0) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<String> getUsersByProject(long arg0) {
        List<String> users = new ArrayList<String>();
        users.add("test@mydomain.com");
        return users;
    }

    public void removeUserFromBillingProjects(String arg0, long[] arg1) throws DAOException {
        // TODO Auto-generated method stub

    }

    public List<Project> retrieveAll(boolean arg0) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Project> retrieveAllProjectsOnly() throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    public void saveContestFees(List<ProjectContestFee> arg0, long arg1) throws DAOException {
        // TODO Auto-generated method stub

    }

    public List<Project> searchProjectsByClientName(String arg0) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Project> searchProjectsByProjectName(String arg0) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    public void updateProjectBudget(long arg0, double arg1) {
        // TODO Auto-generated method stub

    }

    public void delete(Project arg0) throws EntityNotFoundException, DAOException {
        // TODO Auto-generated method stub

    }

    public List<Project> retrieveAll() throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    public Project retrieveById(Long arg0) throws EntityNotFoundException, DAOException {
        return retrieveById(arg0, true);
    }
    
    public Project retrieveById(Long arg0, boolean arg1) throws EntityNotFoundException, DAOException {
        Project project = new Project();
        project.setBudget(99.5);
        
        Client c = new Client();
        Company company = new Company();
        company.setName("myc");
        c.setCompany(company);
        project.setClient(c);
        
        return project;
    }

    public Project save(Project arg0) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Project> search(Filter arg0) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Project> searchByName(String arg0) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

}
