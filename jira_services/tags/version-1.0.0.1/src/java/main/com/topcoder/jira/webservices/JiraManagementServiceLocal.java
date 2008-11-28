/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.webservices;

import javax.ejb.Local;

/**
 * The local EJB interface that simply extends the Reports interface, with no additional facilities. This
 * interface should be marked with @Local annotation representing it's a local interface.
 *
 * Thread Safety The container will assume all responsibility for thread-safety.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@Local
public interface JiraManagementServiceLocal extends JiraManagementService {
}

