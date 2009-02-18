
/**
 * Service.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated{
	import mx.rpc.AsyncToken;
	import flash.utils.ByteArray;
	import mx.rpc.soap.types.*;
               
    public interface IProjectServiceFacadeBeanService
    {
    	//Stub functions for the createProject operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param createProject
    	 * @param arg0
    	 * @return An AsyncToken
    	 */
    	function createProject(createProject:CreateProject):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function createProject_send():AsyncToken;
        
        /**
         * The createProject operation lastResult property
         */
        function get createProject_lastResult():CreateProjectResponse;
		/**
		 * @private
		 */
        function set createProject_lastResult(lastResult:CreateProjectResponse):void;
       /**
        * Add a listener for the createProject operation successful result event
        * @param The listener function
        */
       function addcreateProjectEventListener(listener:Function):void;
       
       
        /**
         * The createProject operation request wrapper
         */
        function get createProject_request_var():CreateProject_request;
        
        /**
         * @private
         */
        function set createProject_request_var(request:CreateProject_request):void;
                   
    	//Stub functions for the deleteProject operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param arg0
    	 * @param deleteProject
    	 * @return An AsyncToken
    	 */
    	function deleteProject(deleteProject:DeleteProject):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function deleteProject_send():AsyncToken;
        
        /**
         * The deleteProject operation lastResult property
         */
        function get deleteProject_lastResult():DeleteProjectResponse;
		/**
		 * @private
		 */
        function set deleteProject_lastResult(lastResult:DeleteProjectResponse):void;
       /**
        * Add a listener for the deleteProject operation successful result event
        * @param The listener function
        */
       function adddeleteProjectEventListener(listener:Function):void;
       
       
        /**
         * The deleteProject operation request wrapper
         */
        function get deleteProject_request_var():DeleteProject_request;
        
        /**
         * @private
         */
        function set deleteProject_request_var(request:DeleteProject_request):void;
                   
    	//Stub functions for the getAllProjects operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param getAllProjects
    	 * @return An AsyncToken
    	 */
    	function getAllProjects(getAllProjects:GetAllProjects):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function getAllProjects_send():AsyncToken;
        
        /**
         * The getAllProjects operation lastResult property
         */
        function get getAllProjects_lastResult():GetAllProjectsResponse;
		/**
		 * @private
		 */
        function set getAllProjects_lastResult(lastResult:GetAllProjectsResponse):void;
       /**
        * Add a listener for the getAllProjects operation successful result event
        * @param The listener function
        */
       function addgetAllProjectsEventListener(listener:Function):void;
       
       
        /**
         * The getAllProjects operation request wrapper
         */
        function get getAllProjects_request_var():GetAllProjects_request;
        
        /**
         * @private
         */
        function set getAllProjects_request_var(request:GetAllProjects_request):void;
                   
    	//Stub functions for the getProject operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param arg0
    	 * @param getProject
    	 * @return An AsyncToken
    	 */
    	function getProject(getProject:GetProject):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function getProject_send():AsyncToken;
        
        /**
         * The getProject operation lastResult property
         */
        function get getProject_lastResult():GetProjectResponse;
		/**
		 * @private
		 */
        function set getProject_lastResult(lastResult:GetProjectResponse):void;
       /**
        * Add a listener for the getProject operation successful result event
        * @param The listener function
        */
       function addgetProjectEventListener(listener:Function):void;
       
       
        /**
         * The getProject operation request wrapper
         */
        function get getProject_request_var():GetProject_request;
        
        /**
         * @private
         */
        function set getProject_request_var(request:GetProject_request):void;
                   
    	//Stub functions for the getProjectsForUser operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param getProjectsForUser
    	 * @param arg0
    	 * @return An AsyncToken
    	 */
    	function getProjectsForUser(getProjectsForUser:GetProjectsForUser):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function getProjectsForUser_send():AsyncToken;
        
        /**
         * The getProjectsForUser operation lastResult property
         */
        function get getProjectsForUser_lastResult():GetProjectsForUserResponse;
		/**
		 * @private
		 */
        function set getProjectsForUser_lastResult(lastResult:GetProjectsForUserResponse):void;
       /**
        * Add a listener for the getProjectsForUser operation successful result event
        * @param The listener function
        */
       function addgetProjectsForUserEventListener(listener:Function):void;
       
       
        /**
         * The getProjectsForUser operation request wrapper
         */
        function get getProjectsForUser_request_var():GetProjectsForUser_request;
        
        /**
         * @private
         */
        function set getProjectsForUser_request_var(request:GetProjectsForUser_request):void;
                   
    	//Stub functions for the updateProject operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param updateProject
    	 * @param arg0
    	 * @return An AsyncToken
    	 */
    	function updateProject(updateProject:UpdateProject):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function updateProject_send():AsyncToken;
        
        /**
         * The updateProject operation lastResult property
         */
        function get updateProject_lastResult():UpdateProjectResponse;
		/**
		 * @private
		 */
        function set updateProject_lastResult(lastResult:UpdateProjectResponse):void;
       /**
        * Add a listener for the updateProject operation successful result event
        * @param The listener function
        */
       function addupdateProjectEventListener(listener:Function):void;
       
       
        /**
         * The updateProject operation request wrapper
         */
        function get updateProject_request_var():UpdateProject_request;
        
        /**
         * @private
         */
        function set updateProject_request_var(request:UpdateProject_request):void;
                   
        /**
         * Get access to the underlying web service that the stub uses to communicate with the server
         * @return The base service that the facade implements
         */
        function getWebService():BaseProjectServiceFacadeBeanService;
	}
}