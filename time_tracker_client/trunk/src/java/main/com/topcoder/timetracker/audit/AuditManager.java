
package com.topcoder.timetracker.audit;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditConfigurationException;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManagerException;
/**
 * <p>This interface defines the common contract that AuditDelegate, AuditSessionBean, and AuditLocalObject all implement. These common methods are the main public functionality of the component.</p>
 * <p> The methods defined by allow a user to audit database operations, rollback audits, and to find existing audits.</p>
 * <p>Implementations of this interface are required to be thread safe.</p>
 * 
 */
public interface AuditManager {
/**
 * Adds an audit record header, and all of its details, to persistence. This method is delegated to the persistence layer - if any errors occur persisting the add, an AuditManagerException is thrown. For more details on usage, see the Informix Add Audit Sequence Diagram
 * 
 * @param record The audit record header containing information to be added to the database - cannot be null
 * @throws AuditManagerException If there are problems in adding the information
 * @throws IllegalArgumentException If the record parameter is null
 */
    public void createAuditRecord(com.topcoder.timetracker.audit.AuditHeader record) throws AuditManagerException;
/**
 * Removes an audit header, and all of its details, from persistence. This method is delegated to the persistence layer - if any errors occur while searching the audits, an AuditManagerException is thrown. For more details on usage, see the Informix Remove Audit Sequence Diagram
 * 
 * @param auditHeaderId The ID of the audit header to remove
 * @return boolean - true if anything was removed from the database, otherwise false.
 * @throws AuditManagerException If there are problems in removing the data
 */
    public boolean rollbackAuditRecord(long auditHeaderId) throws AuditManagerException;
/**
 * Searches through the audits, and returns an array of AuditHeader objects which pass the given filters. This method is delegated to the persistence layer - if any errors occur while searching the audits, an AuditManagerException is thrown. For more details on usage, see the Informix Search Audits Sequence Diagram
 * 
 * @param filter A filter describing the search constraints against which the audits are to be tested. Can be null if no filter is to be used.
 * @return An array of AuditHeader objects that match the given filter. This array may be empty if no matches are found, but will never be null.
 * @throws AuditManagerException If there are any problems searching the audits
 */
    public AuditHeader[] searchAudit(Filter filter) throws AuditManagerException;
}


