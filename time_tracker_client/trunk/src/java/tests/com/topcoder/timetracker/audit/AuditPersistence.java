
package com.topcoder.timetracker.audit;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditPersistenceException;
/**
 * Interface defining the required methods for any persistence layer to be plugged into the AuditManager. The three methods have the same definitions as the Create/Retrieve/Delete methods within the manager. All implementations of this interface should handle their own thread safety, and must also be able to be constructed through using the Object Factory - the intention is that they never need to be constructed directly.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7c1b]
 */
public interface AuditPersistence {
/**
 * Persists information about a new audit record, including all of its contained 'details'.
 * In addition, the implementation should log any records or details that cannot be added, before throwing an AuditPersistenceException
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7c45]
 * @param record The record header and details data that is to be added to persistence. Cannot be null.
 * @throws AuditPersistenceException If there are problems persisting the data.
 * @throws IllegalArgumentException If the
 */
    public void createAuditRecord(com.topcoder.timetracker.audit.AuditHeader record);
/**
 * Searches through persistent information for audit records which fill certain criteria. They are collected then returned as a (possibly empty) array of AuditHeaders.
 * In addition, if there are any problems searching, the implementation should log the search critera before throwing an AuditPersistenceException
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7c33]
 * @param filter The criteria that all values in the return must pass - or null if all audit records are to be obtained.
 * @return An array (non-null, possibly empty) of Audit records which match the criteria.
 * @throws AuditPersistenceException If there are problems reading from the persisted data.
 */
    public AuditHeader[] searchAudit(com.topcoder.search.builder.filter.Filter filter);
/**
 * Removes an audit header, and all of its details, from persistence, the return value will be: -) TRUE, If the audit header is there and deleted successfully -) FALSE, If the audit header is not there. otherwise AuditPersistenceException will be thrown if the audit header is there but an error is encountered during deletion.
 * In addition, the implementation should log whenever the audit / details cannot be removed, before throwing an AuditPersistenceException
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7c21]
 * @param auditHeaderId The unique ID of the audit record to be removed
 * @return Whether anything was removed from persistence or not.
 * @throws AuditPersistenceException If there are problems deleting the record from persistence.
 */
    public boolean rollbackAuditRecord(long auditHeaderId);
}


