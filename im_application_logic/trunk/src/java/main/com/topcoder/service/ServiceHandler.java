package com.topcoder.service;

/**
 * This interface is used for the actual service logic. It plugged in ServiceEngine to serve all categories of
 * services.
 * <p>
 * There are two phases of service logic should be provided by implementations. To-be-serviced logic happens
 * when both requester and responder is avaible for specific category. And serviced logic deals with the
 * actual serving logic.
 * </p>
 * <p>
 * Implementations of this interface are required to be thread safe. Since it will be used in a multi-threads
 * environment.
 * </p>
 * 
 */
public interface ServiceHandler {
    /**
     * Invoked when both requester and responder becomes availabe for the category. The logic in to-be-servied
     * state should be implmented here. Logic containing here may be some initialization process, or
     * handshaking process, or etc.
     * 
     * 
     * @param serviceEvent
     * @throws IllegalArgumentException
     *             if any arg is null.
     * @throws ServiceHandleException
     *             if any other service related error occurs
     */
    public void onToBeServiced(com.topcoder.service.ServiceEvent serviceEvent) throws ServiceHandleException;

    /**
     * Invoked when the service is started. The logic in serviced state should be implmented here. The actual
     * serving logic should be put here.
     * 
     * 
     * @param serviceEvent
     * @throws IllegalArgumentException
     *             if any arg is null.
     * @throws ServiceHandleException
     *             if any service related error occurs
     */
    public void onServiced(com.topcoder.service.ServiceEvent serviceEvent) throws ServiceHandleException;
}
