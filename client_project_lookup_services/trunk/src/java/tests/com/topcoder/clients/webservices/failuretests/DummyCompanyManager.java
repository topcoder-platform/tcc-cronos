package com.topcoder.clients.webservices.failuretests;

import java.util.List;

import com.topcoder.clients.manager.CompanyEntityNotFoundException;
import com.topcoder.clients.manager.CompanyManager;
import com.topcoder.clients.manager.CompanyManagerException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.search.builder.filter.Filter;

public class DummyCompanyManager implements CompanyManager {

    public Company createCompany(Company company) throws CompanyManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public Company deleteCompany(Company company) throws CompanyManagerException, CompanyEntityNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Client> getClientsForCompany(Company company) throws CompanyManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Project> getProjectsForCompany(Company company) throws CompanyManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Company> retrieveAllCompanies() throws CompanyManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public Company retrieveCompany(long id) throws CompanyManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Company> searchCompanies(Filter filter) throws CompanyManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Company> searchCompaniesByName(String name) throws CompanyManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public Company updateCompany(Company company) throws CompanyEntityNotFoundException, CompanyManagerException {
        // TODO Auto-generated method stub
        return null;
    }

}
