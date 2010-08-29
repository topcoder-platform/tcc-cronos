/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */



package finance.tools.asia_pacific.salesperformance.assessment.viewdetails.accuracytests;

import finance.tools.asia_pacific.salesperformance.assessment.model.TransactionSummary;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.TransactionAssessmentViewDetailsDao;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.exception
    .TransactionAssessmentViewDetailsDaoException;

public class MockDao implements TransactionAssessmentViewDetailsDao {
    @Override
    public TransactionSummary retrieveTransactionDetails(TransactionSummary transaction)
            throws TransactionAssessmentViewDetailsDaoException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TransactionSummary[] retrieveTransactionsByEmployeeIncentiveElementKeys(
            long[] eligibleEmployeeIncentiveElementKeys)
            throws TransactionAssessmentViewDetailsDaoException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TransactionSummary[] retrieveTransactionsByTransactionNumbers(short[] transactionNumbers)
            throws TransactionAssessmentViewDetailsDaoException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TransactionSummary[] retrieveTransactionsDetailsByEmployeeIncentiveElementKeys(
            long[] eligibleEmployeeIncentiveElementKeys)
            throws TransactionAssessmentViewDetailsDaoException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TransactionSummary[] retrieveTransactionsDetailsByTransactionNumbers(short[] transactionNumbers)
            throws TransactionAssessmentViewDetailsDaoException {
        // TODO Auto-generated method stub
        return null;
    }
}
