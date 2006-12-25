
package com.topcoder.util.rssgenerator.impl;

import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * Purpose: This exceptions is thrown by various methods of classes of this package and its sub-packages when decoration fails due to the underlying RSSObject containing invalid properties. The conditions for it being thrown are discussed in the CS.
 * <p>Implementation: It extends BaseRuntimeException, as it is a runtime exception. It adds constructors for easy construction of the exception on the lines of its super class.</p>
 * <p>Thread Safety: This class is thread safe as it has no state and its super class is thread safe.</p>
 * 
 */
public class InvalidRSSObjectException extends BaseRuntimeException {

 }
