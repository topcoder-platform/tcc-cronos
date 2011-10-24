/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.topcoder.accounting.entities.dto.BillingCostReportCriteria;
import com.topcoder.accounting.entities.dto.BillingCostReportEntry;
import com.topcoder.accounting.entities.dto.PagedResult;
import com.topcoder.accounting.entities.dto.PaymentIdentifier;
import com.topcoder.accounting.service.BillingCostConfigurationException;
import com.topcoder.accounting.service.BillingCostDataService;
import com.topcoder.accounting.service.BillingCostServiceException;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.TCSubject;

/**
 * <p>
 * This class is an implementation of BillingCostDataService that uses Hibernate to get and export billing data.
 * Logs with the Log from the Logging Wrapper.
 * </p>
 * <p>
 * Thread Safety: This class is mutable but effectively thread-safe.
 * </p>
 * <b>Sample Config:</b>
 * <p>
 *
 * <pre>
 *  &lt;bean id="billingCostDataService"
 *         class="com.topcoder.accounting.service.impl.BillingCostDataServiceImpl"&gt;
 *         &lt;property name="hibernateTemplate" ref="hibernateTemplate" /&gt;
 *         &lt;property name="logger" ref="logger" /&gt;
 *         &lt;property name="projectCategoryIds"&gt;
 *             &lt;list&gt;
 *                 &lt;value&gt;1&lt;/value&gt;
 *                 &lt;value&gt;2&lt;/value&gt;
 *             &lt;/list&gt;
 *         &lt;/property&gt;
 *         &lt;property name="studioProjectCategoryIds"&gt;
 *             &lt;list&gt;
 *                 &lt;value&gt;11&lt;/value&gt;
 *                 &lt;value&gt;12&lt;/value&gt;
 *             &lt;/list&gt;
 *         &lt;/property&gt;
 *         &lt;property name="statusMapping"&gt;
 *             &lt;map&gt;
 *                 &lt;entry key="pending"&gt;
 *                     &lt;value&gt;1&lt;/value&gt;
 *                 &lt;/entry&gt;
 *                 &lt;entry key="active"&gt;
 *                     &lt;value&gt;2&lt;/value&gt;
 *                 &lt;/entry&gt;
 *             &lt;/map&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 * </pre>
 *
 * *
 * </p>
 * <b>Usage:</b>
 * <p>
 *
 * <pre>
 * // Retrieve a billing cost report for a project for a specific stretch of time, getting the first page of
 * // the results
 * BillingCostReportCriteria billingCostReportCriteria = new BillingCostReportCriteria();
 * billingCostReportCriteria.setProjectId(1L);
 * Calendar calendar = Calendar.getInstance();
 * // August 1, 2011
 * calendar.set(2011, 7, 1);
 * billingCostReportCriteria.setStartDate(calendar.getTime());
 * // August 30, 2011
 * calendar.set(2011, 7, 30);
 * billingCostReportCriteria.setEndDate(calendar.getTime());
 * PagedResult&lt;BillingCostReportEntry&gt; billingCostReportEntries = billingCostDataService.getBillingCostReport(
 *     billingCostReportCriteria, 1, 10);
 * // This result would get the first 10 entries in the report for a specific project in the month of august,
 * // as part of a monthly billing report
 *
 * // Export the above-retrieved entries
 * List&lt;PaymentIdentifier&gt; paymentIds = new ArrayList&lt;PaymentIdentifier&gt;();
 * PaymentIdentifier pid = new PaymentIdentifier();
 * pid.setContestId(1L);
 * pid.setPaymentDetailId(2L);
 * pid.setProjectInfoTypeId(3L);
 * paymentIds.add(pid);
 * // the current user
 * TCSubject user = new TCSubject(3L);
 * // assume this is the area of payment we want, such as studio
 * long paymentAreaId = 1L;
 * billingCostDataService.exportBillingCostData(paymentIds, paymentAreaId, user);
 * </pre>
 *
 * </p>
 *
 * @author argolite, stevenfrog
 * @version 1.0
 */
public class BillingCostDataServiceImpl extends BaseService implements BillingCostDataService {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = BillingCostDataServiceImpl.class.getName();

    /**
     * Represents a list of category IDs for projects. It is managed with a getter and setter. It may have any
     * value, but is expected to be set to a non-null/empty list of IDs, all non null. It is fully mutable, but not
     * expected to change after dependency injection.
     */
    private List<Long> projectCategoryIds;

    /**
     * Represents a list of category IDs for studio projects. It is managed with a getter and setter. It may have
     * any value, but is expected to be set to a non-null/empty list of IDs, all non null. It is fully mutable, but
     * not expected to change after dependency injection.
     */
    private List<Long> studioProjectCategoryIds;

    /**
     * Represents a map of the names of statuses to their IDs. It is managed with a getter and setter. It may have
     * any value, but is expected to be set to a non-null map of non-null/empty keys and values. It is fully
     * mutable, but not expected to change after dependency injection.
     */
    private Map<String, Long> statusMapping;

    /**
     * Empty constructor.
     */
    public BillingCostDataServiceImpl() {
        // Empty
    }

    /**
     * This method gets the billing cost report by the search criteria. If none found, returns an empty list in the
     * PagedResult entity. <br>
     * The results will be pages as requested. pageNo is 1-based, and if it is -1 then all pages are returned. If
     * paging is requested, then pageSize is used to set the size of the page
     *
     * @param criteria
     *            the search criteria: If null/empty, there is no filtering.
     * @param pageNo
     *            the 1-based number of the page to return (if -1, then all pages are returned)
     * @param pageSize
     *            the size of the page to return (ignored if pageNo=-1)
     * @return the billing costs that meet the search criteria
     * @throws IllegalArgumentException
     *             If pageNo = 0 or <-1 or pageSize < 1 unless pageNo = -1
     * @throws BillingCostServiceException
     *             If there are any errors during the execution of this method
     */
    public PagedResult<BillingCostReportEntry> getBillingCostReport(BillingCostReportCriteria criteria,
        int pageNo, int pageSize) throws BillingCostServiceException {
        String signature = CLASS_NAME + ".getBillingCostReport(BillingCostReportCriteria, int, int)";
        // log entrance
        LoggingWrapperUtility.logEntrance(getLogger(), signature, new String[] {"criteria", "pageNo", "pageSize"},
            new Object[] {Helper.getString(criteria), pageNo, pageSize});

        try {
            // Check parameter
            Helper.checkPageAndPageSize(pageNo, pageSize);

            PagedResult<BillingCostReportEntry> searchResults = new PagedResult<BillingCostReportEntry>();
            // 1. Obtain a list of BillingCostReportEntry from the dashboard database in a manner similar to the
            // way it
            // is done in the com.topcoder.direct.services.view.util.DataProvider.getDashboardBillingCostReport.
            // There
            // will be differences.
            // 1.1. Prepare a new map of entry records: records:List<BillingCostReportEntry> = new
            // ArrayList<BillingCostReportEntry>()
            // 1.2. Prepare the parameters:
            // 1.2.1. projectCategoryIDsList:String = ""; studioProjectCategoryIdsList:String = ""
            // 1.2.2. Prepare project and studio project categories: for each contestTypeId:long in
            // criteria.contestTypeIds. Here we need to separate these contest type IDs into project and studio
            // project
            // type IDs, and then concatenate them, like existing code @ 2960
            // 1.2.2.1. If contestTypeId in projectCategoryIds: projectCategoryIDsList += " contestTypeId + ", "
            // (unless at the end)
            // 1.2.2.1. If contestTypeId in studioProjectCategoryIds: studioProjectCategoryIdsList +=
            // " contestTypeId + ", " (unless at the end)
            //
            // 1.3. Assemble request:
            // 1.3.1. Create new request: request:Request = new Request();
            // 1.3.2. Create new data access: dataAccessor:DataAccess = new
            // DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME)
            // 1.3.3. If provided set the project ID: request.setProperty("tcdirectid", criteria.projectId);
            // 1.3.4. If provided, set the billing account ID: request.setProperty("billingaccountid",
            // criteria.billingAccountId);
            // 1.3.5. If provided, set the client ID: request.setProperty("clientid", customerId);
            // 1.3.6. Create dae formatter: dateFormatter:DateFormat = new SimpleDateFormat("yyyy-MM-dd");
            // 1.3.7. If provided set the start date: request.setProperty("sdt",
            // criteria.dateFormatter.format(criteria.startDate));
            // 1.3.8. If provided set the end date: request.setProperty("edt",
            // dateFormatter.format(criteria.endDate));
            // 1.3.9. Set category IDs: request.setProperty("pcids", projectCategoryIDsList);
            // 1.3.10. Set studio category IDs: request.setProperty("scids", studioProjectCategoryIdsList);
            // 1.3.11. The query name: queryName:String = "dashboard_billing_cost_report"
            //

            // concatenate the filters
            // StringBuffer projectCategoryIDsList = new StringBuffer();
            // StringBuffer studioProjectCategoryIdsList = new StringBuffer();
            // long[] contestTypeIds = criteria.getContestTypeIds();
            // for (int i = 0; i < contestTypeIds.length; i++) {
            // if(projectCategoryIds.contains(contestTypeIds[i])){
            // projectCategoryIDsList.append(contestTypeIds[i]);
            // if(i < contestTypeIds.length -1){
            // projectCategoryIDsList.append(", ");
            // }
            // }
            // if(studioProjectCategoryIds.contains(contestTypeIds[i])){
            // studioProjectCategoryIdsList.append(contestTypeIds[i]);
            // if(i < contestTypeIds.length -1){
            // studioProjectCategoryIdsList.append(", ");
            // }
            // }
            // }

            // if (projectCategoryIds != null && projectCategoryIds.size() > 0) {
            // projectCategoryIDsList = concatenate(projectCategoryIds, ", ");
            // }
            // if (studioProjectCategoryIds != null && studioProjectCategoryIds.length > 0) {
            // studioProjectCategoryIdsList = concatenate(studioProjectCategoryIds, ", ");
            // }

            // String clientIdsList = concatenate(clientIds, ", ");
            // String billingAccountIdsList = concatenate(billingAccountIds, ", ");
            // String projectIDsList = concatenate(projectIds, ", ");

            // Create dae formatter:
            // DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            // String[] names = new String[]{"pcids", "clientid", "billingaccountid", "tcdirectid", "sdt", "edt"};
            // Object[] params = new Object[]{projectCategoryIDsList.toString(),criteria.getCustomerId(),
            // criteria.getBillingAccountId(),
            // criteria.getProjectId(),dateFormatter.format(criteria.getStartDate())+" 00:00:00",
            // dateFormatter.format(criteria.getEndDate())+" 23:59:59"};
            // System.out.println("================================================="+contestTypeIds);
            // System.out.println("================================================="+projectCategoryIDsList.toString());
            // List result =getHibernateTemplate().findByNamedQueryAndNamedParam("dashboard_billing_cost_report",
            // names, params);
            // System.out.println("gggggggggggggggggggggggggggggggggggg");
            //
            // ApplicationContext APP_CONTEXT = new ClassPathXmlApplicationContext("beans.xml");
            // // .getHibernateTemplate().
            // DataSource dataSource = (DataSource)APP_CONTEXT.getBean("dataSource");
            //
            // // DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
            // // DBMS.TCS_OLTP_DATASOURCE_NAME = "tttttttttttt";
            // System.out.println("ddddddddddddd"+DBMS.TCS_OLTP_DATASOURCE_NAME);
            // DataAccess dataAccessor = new DataAccess(dataSource);
            // Request request = new Request();
            //
            // String queryName = "dashboard_billing_cost_report";
            //
            // request.setContentHandle(queryName);
            // request.setProperty("sdt", dateFormatter.format(criteria.getStartDate()));
            // request.setProperty("edt", dateFormatter.format(criteria.getEndDate()));
            // request.setProperty("pcids", projectCategoryIDsList.toString());
            // request.setProperty("scids", studioProjectCategoryIdsList.toString());
            // request.setProperty("clientid", criteria.getCustomerId().toString());
            // request.setProperty("billingaccountids", criteria.getBillingAccountId().toString());
            // request.setProperty("tcdirectids", criteria.getProjectId().toString());
            //
            //
            // final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);
            // System.out.println(resultSetContainer);

            // 1.4. Run query: resultSetContainer:ResultSetContainer = dataAccessor.getData(request).get(queryName)
            //
            // 1.5. Filter results by status IDs, if provided:
            // 1.5.1. Set<Long> statusFilter = new HashSet<Long>();
            // 1.5.2. for each statusId:long in statusIds) add to set: statusFilter.add(statusId)
            //
            // 1.6. Map results of query to BillingCostReportEntry properties: For each row:ResultSetRow in
            // resultSetContainer
            // 1.6.1. Filter by status: If
            // !statusFilter.contains(statusMapping.get(row.getStringItem("contest_status"))), then ignore this row
            // 1.6.2. Since row is ok, then total:int++;
            // - note to only grab the valid rows that are from the requested page
            // 1.6.3. Map to the properties:
            // -paymentDate:Date = row.getTimestampItem("payment_date")
            // -customer:String = row.getStringItem("client")
            // -billingAccount:String = row.getStringItem("billing_project_name")
            // -projectName:String = row.getStringItem("direct_project_name")
            // -contestName:String = row.getStringItem("contest_name")
            // -contestId:long = row.getLongItem("contest_id")
            // -referenceId:String = row.getStringItem("reference_id")
            // -category:String = row.getStringItem("category")
            // -status:String = row.getStringItem("contest_status")
            // -launchDate:Date = row.getTimestampItem("launch_date")
            // -completionDate:Date = row.getTimestampItem("completion_date")
            // -paymentType:String = row.getStringItem("line_item_category")
            // -amount:float = row.getDoubleItem("line_item_amount")
            // -projectInfoTypeId:Long = row.getLongItem("project_info_type_id")
            // -paymentDetailId:Long = row.getLongItem("payment_details_id")
            //
            // 1.6.4. Get export field:
            // 1.6.4.1. Create query string
            // 1.6.4.1.1. If paymentDetailId provided: queryString:String =
            // "FROM BillingCostExportDetail billingCostExportDetail WHERE
            // billingCostExportDetail.paymentDetailId = :paymentDetailId"
            // 1.6.4.1.2. Else queryString:String =
            // "FROM BillingCostExportDetail billingCostExportDetail WHERE billingCostExportDetail.contestId
            // = :contestId AND billingCostExportDetail.projectInfoTypeId = :projectInfoTypeId"
            // 1.6.4.2 Prepare parameters
            // 1.6.4.2.1. Prepare parameters: If paymentDetailId provided: parameters:Object[] = {paymentDetailId}
            // 1.6.4.2.2. Else parameters:Object[] = {contestId,projectInfoTypeId}
            // 1.6.4.3. Execute query: results:List = hibernateTemplate.find(queryString,parameters)
            // 1.6.4.4. Get first entry: billingCostExportDetail:BillingCostExportDetail = results.get(0);
            // 1.6.4.5. Map entry property values:
            // -exported:boolean = true if billingCostExportDetail is available, false otherwise
            // -invoiceNumber:String = billingCostExportDetail.invoiceNumber (but only if available)
            //
            // 2. Create result object: searchResults:PagedResult<BillingCostReportEntry> = new
            // PagedResult<BillingCostReportEntry>()
            // 2.1. Set page: searchResults.pageNo = pageNo
            // 2.2. Set page size: searchResults.pageSize = pageSize
            // 2.3. Set total pages: searchResults.totalRecords = total
            // 2.4. Set page count: Set total page count: If page > 0, then searchResults.totalPages=
            // (int)Math.ceil(searchResults.totalRecords/searchResults.pageSize); else searchResults.totalPages = 1
            // 2.5. Set result list: searchResults.records = records
            //
            // 3. Return searchResults
            // log exit
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {searchResults.toJSONString()});
            // return searchResults
            return searchResults;
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <p>
     * Build a string concatenating the specified values separated with specified delimiter.
     * </p>
     *
     * @param items
     *            a <code>long</code> array providing the values to be concatenated.
     * @param delimiter
     *            a <code>String</code> providing the delimiter to be inserted between concatenated items.
     * @return a <code>String</code> providing the concatenated item values.
     * @since 2.1.9
     */
    private static String concatenate(long[] items, String delimiter) {
        StringBuilder b = new StringBuilder();
        for (Long id : items) {
            if (b.length() > 0) {
                b.append(delimiter);
            }
            b.append(id);
        }
        return b.toString();
    }

    /**
     * This method exports the selected records from billing cost report, as identified by the list of payments
     * identifiers.
     *
     * @param paymentAreaId
     *            the ID of the payment area
     * @param paymentIds
     *            the billing entry identifiers
     * @param user
     *            the TCSubject
     * @throws IllegalArgumentException
     *             If paymentIds is null/empty or contains null entries, or user is null
     * @throws BillingCostServiceException
     *             If there are any errors during the execution of this method
     */
    public void exportBillingCostData(List<PaymentIdentifier> paymentIds, long paymentAreaId, TCSubject user)
        throws BillingCostServiceException {
        String signature = CLASS_NAME + ".exportBillingCostData(List, long, TCSubject)";
        // log entrance
        LoggingWrapperUtility.logEntrance(getLogger(), signature, new String[] {"paymentIds", "paymentAreaId",
            "user"}, new Object[] {Helper.getListString(paymentIds), paymentAreaId, user});

        try {
            // Check parameter
            ParameterCheckUtility.checkNotNullNorEmpty(paymentIds, "paymentIds");
            ParameterCheckUtility.checkNotNullElements(paymentIds, "paymentIds");
            ParameterCheckUtility.checkNotNull(user, "user");
            // Create and fill new BillingCostExport
            // BillingCostExport export = new BillingCostExport();
            // PaymentArea paymentArea = new PaymentArea();
            // paymentArea.setId(paymentAreaId);
            // export.setPaymentArea(paymentArea);
            // export.setRecordsCount(paymentIds.size());
            // export.setTimestamp(new Date());
            // export.setAccountantName(""+user.getUserId());
            //
            // Create export:
            // getHibernateTemplate().persist(export);
            //
            // 3. For each paymentId:PaymentIdentifier in paymentIDs
            // Get the corresponding BillingCostReportEntry:
            BillingCostReportEntry entry = this.getBillingCostReport(paymentIds.get(0));
            // 3.2. Create a new detail:BillingCostExportDetail and fill it with data from the entry
            // 3.3. Persist detail: hibernateTemplate.persist(detail)
            // log exit
            LoggingWrapperUtility.logExit(getLogger(), signature, null);
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        }

    }

    /**
     * This method gets the billing cost report entry by the payment ID. If none found, returns null.
     *
     * @param paymentId
     *            the identifier of the report entry
     * @return the billing cost report entry
     * @throws BillingCostServiceException
     *             If there are any errors during the execution of this method
     */
    @SuppressWarnings("unused")
    private BillingCostReportEntry getBillingCostReport(PaymentIdentifier paymentId)
        throws BillingCostServiceException {
        // // 1. Assemble request:
        // // 1.1. Create new request
        // try {
        // Request request= new Request();
        // // 1.2. Create new data access:
        // DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        // // 1.3. If provided, set the contest ID:
        // request.setProperty("contestId", ""+paymentId.getContestId());
        // // 1.4. If provided, set the project type info ID:
        // request.setProperty("projectInfoTypeId", ""+paymentId.getProjectInfoTypeId());
        // // 1.5. If provided, set the payment detail ID:
        // request.setProperty("paymentDetailId", ""+paymentId.getPaymentDetailId());
        // // 1.6. The query name:
        // String queryName = "dashboard_billing_cost_report";
        // //
        // // Run query:
        // ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);
        // List result =getHibernateTemplate().findByNamedQueryAndNamedParam("dashboard_billing_cost_report");

        //
        // 3. Map results of query to entry:BillingCostReportEntry properties: In the first row:ResultSetRow in
        // resultSetContainer
        // 3.1. Map to the properties:
        // -paymentDate:Date = row.getTimestampItem("payment_date")
        // -customer:String = row.getStringItem("client")
        // -billingAccount:String = row.getStringItem("billing_project_name")
        // -projectName:String = row.getStringItem("direct_project_name")
        // -contestName:String = row.getStringItem("contest_name")
        // -contestId:long = row.getLongItem("contest_id")
        // -referenceId:String = row.getStringItem("reference_id")
        // -category:String = row.getStringItem("category")
        // -status:String = row.getStringItem("contest_status")
        // -launchDate:Date = row.getTimestampItem("launch_date")
        // -completionDate:Date = row.getTimestampItem("completion_date")
        // -paymentType:String = row.getStringItem("line_item_category")
        // -amount:float = row.getDoubleItem("line_item_amount")
        // -projectInfoTypeId:Long = row.getLongItem("project_info_type_id")
        // -paymentDetailId:Long = row.getLongItem("payment_details_id")
        //
        // 4. Return entry
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        return null;
    }

    /**
     * This method checks that all required injection fields are in fact provided.
     *
     * @throws BillingCostConfigurationException
     *             If there are required injection fields that are not injected
     */
    @Override
    public void afterPropertiesSet() {
        // Call base method
        super.afterPropertiesSet();
        // Check that the following fields have required values (see variable docs for requirements)
        ValidationUtility.checkNotNullNorEmpty(projectCategoryIds, "projectCategoryIds",
            BillingCostConfigurationException.class);
        ValidationUtility.checkNotNullElements(projectCategoryIds, "projectCategoryIds",
            BillingCostConfigurationException.class);
        ValidationUtility.checkNotNullNorEmpty(studioProjectCategoryIds, "studioProjectCategoryIds",
            BillingCostConfigurationException.class);
        ValidationUtility.checkNotNullElements(studioProjectCategoryIds, "studioProjectCategoryIds",
            BillingCostConfigurationException.class);
        ValidationUtility.checkNotNullNorEmpty(statusMapping, "statusMapping",
            BillingCostConfigurationException.class);
        ValidationUtility
            .checkNotNullKeys(statusMapping, "statusMapping", BillingCostConfigurationException.class);
        ValidationUtility.checkNotEmptyKeys(statusMapping, true, "statusMapping",
            BillingCostConfigurationException.class);
        ValidationUtility.checkNotNullValues(statusMapping, "statusMapping",
            BillingCostConfigurationException.class);
    }

    /**
     * <p>
     * Getter method for projectCategoryIds, simply return the namesake instance variable.
     * </p>
     *
     * @return the projectCategoryIds
     */
    public List<Long> getProjectCategoryIds() {
        return projectCategoryIds;
    }

    /**
     * <p>
     * Setter method for projectCategoryIds, simply assign the value to the instance variable.
     * </p>
     *
     * @param projectCategoryIds
     *            the projectCategoryIds to set
     */
    public void setProjectCategoryIds(List<Long> projectCategoryIds) {
        this.projectCategoryIds = projectCategoryIds;
    }

    /**
     * <p>
     * Getter method for studioProjectCategoryIds, simply return the namesake instance variable.
     * </p>
     *
     * @return the studioProjectCategoryIds
     */
    public List<Long> getStudioProjectCategoryIds() {
        return studioProjectCategoryIds;
    }

    /**
     * <p>
     * Setter method for studioProjectCategoryIds, simply assign the value to the instance variable.
     * </p>
     *
     * @param studioProjectCategoryIds
     *            the studioProjectCategoryIds to set
     */
    public void setStudioProjectCategoryIds(List<Long> studioProjectCategoryIds) {
        this.studioProjectCategoryIds = studioProjectCategoryIds;
    }

    /**
     * <p>
     * Getter method for statusMapping, simply return the namesake instance variable.
     * </p>
     *
     * @return the statusMapping
     */
    public Map<String, Long> getStatusMapping() {
        return statusMapping;
    }

    /**
     * <p>
     * Setter method for statusMapping, simply assign the value to the instance variable.
     * </p>
     *
     * @param statusMapping
     *            the statusMapping to set
     */
    public void setStatusMapping(Map<String, Long> statusMapping) {
        this.statusMapping = statusMapping;
    }

}
