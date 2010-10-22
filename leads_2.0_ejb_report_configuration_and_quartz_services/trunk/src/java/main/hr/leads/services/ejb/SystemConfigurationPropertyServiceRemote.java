/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.ejb;

import hr.leads.services.SystemConfigurationPropertyService;

import javax.ejb.Remote;

/**
 * <p>
 * This interface represents the remote interface for
 * SystemConfigurationPropertyService session bean.
 * </p>
 * <p>
 * It extends SystemConfigurationPropertyService interface and provides no
 * additional methods.
 * </p>
 * <p>
 * <b> Thread Safety: </b> Implementations of this interface must be thread safe
 * when their configurable properties do not change after initialization.
 * </p>
 *
 * @author semi_sleep, TCSDEVELOPER
 * @version 1.0
 */
@Remote
public interface SystemConfigurationPropertyServiceRemote extends SystemConfigurationPropertyService {
}

