/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service.bean;

import javax.ejb.Remote;

import com.liquid.portal.service.LiquidPortalService;

/**
 * <p>
 * The remote EJB interface that simply extends the LiquidPortalService
 * interface, with no additional facilities.
 * </p>
 * <p>
 * <b>Thread Safety:</b> The container will assume all responsibility for
 * thread-safety.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@Remote
public interface LiquidPortalServiceRemote extends LiquidPortalService {
}
