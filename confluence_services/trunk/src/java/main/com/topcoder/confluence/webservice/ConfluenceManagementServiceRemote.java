/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice;

import javax.ejb.Remote;

/**
 * <p>
 * The remote EJB interface that simply extends the ConfluenceManagementService interface, with no additional
 * facilities.
 * </p>
 * <p>
 * <b>Thread Safety:</b> The container will assume all responsibility for thread-safety.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@Remote
public interface ConfluenceManagementServiceRemote extends ConfluenceManagementService {
}
