/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit.ejb;

import com.topcoder.timetracker.audit.AuditManager;

import javax.ejb.EJBLocalObject;


/**
 * <p>
 * This the local interface used to talk to the <code>AuditSessionBean</code>. It contains exactly the same methods as
 * <code>AuditManager</code> interface and supports all client operations.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>The container assumes all responsibility for thread-safety of these implementations.
 * </p>
 *
 * @author sql_lall, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public interface AuditLocalObject extends EJBLocalObject, AuditManager {
}
