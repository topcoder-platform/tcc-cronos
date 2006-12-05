package com.topcoder.web.frontcontroller;

/**
 * <p> Result interface defines the contract to generate user response. The Action object contains a map of Result objects, and only one of them can be executed at last. Which one to execute depends on the resultCode returned by configured handlers in the Action, and if all handlers return null, a 'success' Result will be executed at last. We can configure unlimited number of Result for an Aciton object, and finally only one is picked to be executed. Its implementation must be thread-safe, since it is used to generate response for all incoming user requests for the action it belongs to. </p>
 * 
 */
public interface Result {

    /**
     * <p>Generate user response. Its implementation would generally write response to the HttpServletResponse object. </p>
     * 
     * 
     * @param context the ActionContext object.
     * @throws IllegalArgumentException if the context is null.
     * @throws ResultExecutionException if fail to genearte the response.
     */
    public void execute(com.topcoder.web.frontcontroller.ActionContext context);
}
