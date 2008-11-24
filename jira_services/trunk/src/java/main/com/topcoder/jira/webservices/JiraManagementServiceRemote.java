/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.webservices;

import javax.ejb.Remote;

/**
 * The remote EJB interface that simply extends the Reports interface, with no additional facilities. This
 * interface should be marked with @Remote annotation representing it's a remote interface.
 *
 * Thread Safety The container will assume all responsibility for thread-safety.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@Remote
public interface JiraManagementServiceRemote extends JiraManagementService {
}

