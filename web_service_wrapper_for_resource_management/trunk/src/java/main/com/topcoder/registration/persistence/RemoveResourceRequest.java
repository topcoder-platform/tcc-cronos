/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 * This class represents the request of <code>removeResource()</code> method.
 * </p>
 *
 * <p>
 * It will be automatically used by the web services to handle the request of the related operation,
 * for this reason the checking is not performed.
 * </p>
 *
 * <p>
 * The reason to introduce it is to use the <code>CopiedResource</code> type to override the <code>
 * Resource</code> type declared by the <code>removeResource()</code> method.
 * </p>
 *
 * <p>
 * This class directly extends <code>UpdateResourceRequest</code> because the request parameters of
 * <code>removeResource()</code> and <code>updateResource()</code> are essentially same from the aspect
 * of web service.
 * </p>
 *
 * <p>
 *   <strong>Thread Safety:</strong>
 *   This class is not thread safe because it's highly mutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 * @see CopiedResource
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "removeResourceRequest")
public class RemoveResourceRequest extends UpdateResourceRequest {

}
