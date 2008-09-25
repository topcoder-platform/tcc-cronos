/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.topcoder.clients.model.Company;

/**
 * <p>
 *  This interface represents the CompanyService web service endpoint interface.
 *  This interface defines the methods available for the CompanyService web service:
 *  create, update and delete company.
 * </p>
 *
 * <p>
 *  <strong>Thread-safety:</strong>
 *  Implementations of this interface should be thread safe.
 * </p>
 *
 * @author  Mafy, CaDenza
 * @version 1.0
 */
@WebService
public interface CompanyService {

    /**
     * This static final String field represents the 'BEAN_NAME' property
     * of the BsSyncHotelFunctions web service endpoint interface.
     */
    public static final String BEAN_NAME = "CompanyServiceBean";

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
    @WebMethod
    public Company createCompany(Company company) throws CompanyServiceException;

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
    @WebMethod
    public Company updateCompany(Company company) throws CompanyServiceException;

    /**
     * Defines the operation that performs the deletion of the given company from the persistence.
     *
     * @param company the company that should be deleted. Should not be null.
     * @return the company deleted from the persistence.
     *
     * @throws IllegalArgumentException if the company is null.
     * @throws CompanyServiceException if any error occurs while performing this operation.
     */
    @WebMethod
    public Company deleteCompany(Company company) throws CompanyServiceException;

}

