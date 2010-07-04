/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct.ejb;

import javax.ejb.Remote;

import com.topcoder.service.facade.direct.DirectServiceFacade;

/**
 * <p>
 * This interface represents the remote interface for DirectServiceFacade session bean. It extends that interface and
 * provides no additional methods.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong> Implementations of this interface must be thread safe when entities passed to them
 * are used in thread safe manner by the caller.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
@Remote
public interface DirectServiceFacadeRemote extends DirectServiceFacade {
}
