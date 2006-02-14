/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

/**
 * <p>
 * This class represents the Task Type entity. Extends <code>BaseDataObject</code>. Uses its primaryId member for
 * holding the <code>TaskTypesID</code> defined in the database. This entity is used in <code>TaskTypeDAO</code>.
 * As such, when the <code>DAO</code> creates this record, it will ignore the creation and modification fields and
 * assign its own values. It will also ignore the primary Id value as it will assign its own. Simlarly, the <code>
 * DAO</code> will ignore all of these fields when updating. The creation fields and the primary fields are never
 * updated, and the <code>DAO</code> will assign its own values to the modification fields. As such, from the
 * perspective of the application using this entity, these fields can be considered to be effectively read-only
 * (as assigning fresh values to these fields has no impact in the <code>DAO</code>, and thus on the
 * database. Please see <code>TaskTypeDAO</code> for more details.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class TaskType extends BaseDataObject {
    /**
     * <p>
     * Sole constructor.
     * </p>
     */
    public TaskType() {
        // empty here
    }
}
