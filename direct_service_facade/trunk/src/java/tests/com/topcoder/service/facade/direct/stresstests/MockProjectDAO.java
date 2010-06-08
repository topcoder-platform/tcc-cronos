package com.topcoder.service.facade.direct.stresstests;

import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.dao.ProjectDAO;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.search.builder.filter.Filter;

import java.util.ArrayList;
import java.util.List;

public class MockProjectDAO implements ProjectDAO {

    public MockProjectDAO() {
    }

    public void addUserToBillingProjects(String arg0, long[] arg1) throws DAOException {
    }

    public boolean checkClientProjectPermission(String arg0, long arg1) throws DAOException {
        return false;
    }

    public boolean checkPoNumberPermission(String arg0, String arg1) throws DAOException {
        return false;
    }

    public List<ProjectContestFee> getContestFeesByProject(long arg0) throws DAOException {
        return null;
    }

    public List<Project> getProjectsByClientId(long arg0) throws DAOException {
        return null;
    }

    public List<Project> getProjectsByUser(String arg0) throws DAOException {
        return null;
    }

    public List<String> getUsersByProject(long arg0) {
        List<String> users = new ArrayList<String>();
        users.add("test@mydomain.com");
        return users;
    }

    public void removeUserFromBillingProjects(String arg0, long[] arg1) throws DAOException {
    }

    public List<Project> retrieveAll(boolean arg0) throws DAOException {
        return null;
    }

    public List<Project> retrieveAllProjectsOnly() throws DAOException {
        return null;
    }

    public Project retrieveById(Long arg0, boolean arg1) throws EntityNotFoundException, DAOException {
        Project project = new Project();
        Client client = new Client();
        Company company = new Company();
        company.setName("name");
        client.setCompany(company);
        project.setClient(client);
        project.setCompany(company);
        return project;
    }

    public void saveContestFees(List<ProjectContestFee> arg0, long arg1) throws DAOException {
    }

    public List<Project> searchProjectsByClientName(String arg0) throws DAOException {
        return null;
    }

    public List<Project> searchProjectsByProjectName(String arg0) throws DAOException {
        return null;
    }

    public void updateProjectBudget(long arg0, double arg1) {
    }

    public void delete(Project arg0) throws EntityNotFoundException, DAOException {
    }

    public List<Project> retrieveAll() throws DAOException {
        return null;
    }

    public Project retrieveById(Long arg0) throws EntityNotFoundException, DAOException {
        Project project = new Project();
        Client client = new Client();
        project.setClient(client);
        return project;
    }

    public Project save(Project arg0) throws DAOException {
        return null;
    }

    public List<Project> search(Filter arg0) throws DAOException {
        return null;
    }

    public List<Project> searchByName(String arg0) throws DAOException {
        return null;
    }

}