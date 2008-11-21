package com.topcoder.clients.webservices;


import com.topcoder.clients.model.Company;

public class CompanyService {

    /**
     * Defines the operation that performs the creation of the given company in the persistence.
     *
     * @param company the company that should be created. Should not be null.
     * @return the company created in the persistence.
     *
     * @throws IllegalArgumentException
     *      if the company is null.
     * @throws CompanyServiceException
     *      if any error occurs while performing this operation.
     */
    public Company createCompany(Company company) throws CompanyServiceException{
        return company;
    }

    /**
     * Defines the operation that performs the update of the given company in the persistence.
     *
     * @param company
     *      the company that should be updated. Should not be null.
     * @return the company updated in the persistence.
     *
     * @throws IllegalArgumentException if the company is null.
     * @throws CompanyServiceException if any error occurs while performing this operation.
     */
    public Company updateCompany(Company company) throws CompanyServiceException{
        return company;
    }

    /**
     * Defines the operation that performs the deletion of the given company from the persistence.
     *
     * @param company the company that should be deleted. Should not be null.
     * @return the company deleted from the persistence.
     *
     * @throws IllegalArgumentException if the company is null.
     * @throws CompanyServiceException if any error occurs while performing this operation.
     */
    public Company deleteCompany(Company company) throws CompanyServiceException{
        return company;
    }
}
