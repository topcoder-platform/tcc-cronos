package com.orpheus.administration.persistence;

/**
 * <p>This is the local home interface for managing messages. The local client will obtain it to get the local interface.</p>
 * <p><strong>Thread Safety</strong></p>
 * <p>The container assumes all responsibility for thread-safety of these implementations.</p>
 * 
 */
public interface MessageLocalHome extends
        javax.ejb.EJBLocalHome {
    /**
     * <p>Creates the ejb. Initializes any required resources.</p>
     * <p><strong>Exception Handling</strong></p>
     * <ul type="disc">
     * <li>CreateException If any error occurs while instantiating</li>
     * </ul>
     * <p></p>
     * 
     * 
     * @return local interface
     */
    public com.orpheus.administration.persistence.MessageLocal create();
}
