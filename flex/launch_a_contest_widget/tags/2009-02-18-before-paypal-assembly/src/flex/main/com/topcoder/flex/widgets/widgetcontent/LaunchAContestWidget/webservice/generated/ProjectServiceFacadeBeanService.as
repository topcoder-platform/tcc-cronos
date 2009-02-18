/**
 * ProjectServiceFacadeBeanServiceService.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */
 /**
  * Usage example: to use this service from within your Flex application you have two choices:
  * Use it via Actionscript only
  * Use it via MXML tags
  * Actionscript sample code:
  * Step 1: create an instance of the service; pass it the LCDS destination string if any
  * var myService:ProjectServiceFacadeBeanService= new ProjectServiceFacadeBeanService();
  * Step 2: for the desired operation add a result handler (a function that you have already defined previously)  
  * myService.addcreateProjectEventListener(myResultHandlingFunction);
  * Step 3: Call the operation as a method on the service. Pass the right values as arguments:
  * myService.createProject(mycreateProject,myarg0);
  *
  * MXML sample code:
  * First you need to map the package where the files were generated to a namespace, usually on the <mx:Application> tag, 
  * like this: xmlns:srv="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.*"
  * Define the service and within its tags set the request wrapper for the desired operation
  * <srv:ProjectServiceFacadeBeanService id="myService">
  *   <srv:createProject_request_var>
  *		<srv:CreateProject_request createProject=myValue,arg0=myValue/>
  *   </srv:createProject_request_var>
  * </srv:ProjectServiceFacadeBeanService>
  * Then call the operation for which you have set the request wrapper value above, like this:
  * <mx:Button id="myButton" label="Call operation" click="myService.createProject_send()" />
  */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated
{
	import mx.rpc.AsyncToken;
	import flash.events.EventDispatcher;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.events.FaultEvent;
	import flash.utils.ByteArray;
	import mx.rpc.soap.types.*;

    /**
     * Dispatches when a call to the operation createProject completes with success
     * and returns some data
     * @eventType CreateProjectResultEvent
     */
    [Event(name="CreateProject_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.CreateProjectResultEvent")]
    
    /**
     * Dispatches when a call to the operation deleteProject completes with success
     * and returns some data
     * @eventType DeleteProjectResultEvent
     */
    [Event(name="DeleteProject_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.DeleteProjectResultEvent")]
    
    /**
     * Dispatches when a call to the operation getAllProjects completes with success
     * and returns some data
     * @eventType GetAllProjectsResultEvent
     */
    [Event(name="GetAllProjects_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetAllProjectsResultEvent")]
    
    /**
     * Dispatches when a call to the operation getProject completes with success
     * and returns some data
     * @eventType GetProjectResultEvent
     */
    [Event(name="GetProject_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetProjectResultEvent")]
    
    /**
     * Dispatches when a call to the operation getProjectsForUser completes with success
     * and returns some data
     * @eventType GetProjectsForUserResultEvent
     */
    [Event(name="GetProjectsForUser_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetProjectsForUserResultEvent")]
    
    /**
     * Dispatches when a call to the operation updateProject completes with success
     * and returns some data
     * @eventType UpdateProjectResultEvent
     */
    [Event(name="UpdateProject_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.UpdateProjectResultEvent")]
    
	/**
	 * Dispatches when the operation that has been called fails. The fault event is common for all operations
	 * of the WSDL
	 * @eventType mx.rpc.events.FaultEvent
	 */
    [Event(name="fault", type="mx.rpc.events.FaultEvent")]

	public class ProjectServiceFacadeBeanService extends EventDispatcher implements IProjectServiceFacadeBeanService
	{
    	private var _baseService:BaseProjectServiceFacadeBeanService;
        
        /**
         * Constructor for the facade; sets the destination and create a baseService instance
         * @param The LCDS destination (if any) associated with the imported WSDL
         */  
        public function ProjectServiceFacadeBeanService(destination:String=null,rootURL:String=null)
        {
        	_baseService = new BaseProjectServiceFacadeBeanService(destination,rootURL);
        }
        
		//stub functions for the createProject operation
          

        /**
         * @see IProjectServiceFacadeBeanService#createProject()
         */
        public function createProject(createProject:CreateProject):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.createProject(createProject);
            _internal_token.addEventListener("result",_createProject_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IProjectServiceFacadeBeanService#createProject_send()
		 */    
        public function createProject_send():AsyncToken
        {
        	return createProject(_createProject_request.createProject);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _createProject_request:CreateProject_request;
		/**
		 * @see IProjectServiceFacadeBeanService#createProject_request_var
		 */
		[Bindable]
		public function get createProject_request_var():CreateProject_request
		{
			return _createProject_request;
		}
		
		/**
		 * @private
		 */
		public function set createProject_request_var(request:CreateProject_request):void
		{
			_createProject_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _createProject_lastResult:CreateProjectResponse;
		[Bindable]
		/**
		 * @see IProjectServiceFacadeBeanService#createProject_lastResult
		 */	  
		public function get createProject_lastResult():CreateProjectResponse
		{
			return _createProject_lastResult;
		}
		/**
		 * @private
		 */
		public function set createProject_lastResult(lastResult:CreateProjectResponse):void
		{
			_createProject_lastResult = lastResult;
		}
		
		/**
		 * @see IProjectServiceFacadeBeanService#addcreateProject()
		 */
		public function addcreateProjectEventListener(listener:Function):void
		{
			addEventListener(CreateProjectResultEvent.CreateProject_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _createProject_populate_results(event:ResultEvent):void
		{
			var e:CreateProjectResultEvent = new CreateProjectResultEvent();
		            e.result = event.result as CreateProjectResponse;
		                       e.headers = event.headers;
		             createProject_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the deleteProject operation
          

        /**
         * @see IProjectServiceFacadeBeanService#deleteProject()
         */
        public function deleteProject(deleteProject:DeleteProject):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.deleteProject(deleteProject);
            _internal_token.addEventListener("result",_deleteProject_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IProjectServiceFacadeBeanService#deleteProject_send()
		 */    
        public function deleteProject_send():AsyncToken
        {
        	return deleteProject(_deleteProject_request.deleteProject);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _deleteProject_request:DeleteProject_request;
		/**
		 * @see IProjectServiceFacadeBeanService#deleteProject_request_var
		 */
		[Bindable]
		public function get deleteProject_request_var():DeleteProject_request
		{
			return _deleteProject_request;
		}
		
		/**
		 * @private
		 */
		public function set deleteProject_request_var(request:DeleteProject_request):void
		{
			_deleteProject_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _deleteProject_lastResult:DeleteProjectResponse;
		[Bindable]
		/**
		 * @see IProjectServiceFacadeBeanService#deleteProject_lastResult
		 */	  
		public function get deleteProject_lastResult():DeleteProjectResponse
		{
			return _deleteProject_lastResult;
		}
		/**
		 * @private
		 */
		public function set deleteProject_lastResult(lastResult:DeleteProjectResponse):void
		{
			_deleteProject_lastResult = lastResult;
		}
		
		/**
		 * @see IProjectServiceFacadeBeanService#adddeleteProject()
		 */
		public function adddeleteProjectEventListener(listener:Function):void
		{
			addEventListener(DeleteProjectResultEvent.DeleteProject_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _deleteProject_populate_results(event:ResultEvent):void
		{
			var e:DeleteProjectResultEvent = new DeleteProjectResultEvent();
		            e.result = event.result as DeleteProjectResponse;
		                       e.headers = event.headers;
		             deleteProject_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the getAllProjects operation
          

        /**
         * @see IProjectServiceFacadeBeanService#getAllProjects()
         */
        public function getAllProjects(getAllProjects:GetAllProjects):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.getAllProjects(getAllProjects);
            _internal_token.addEventListener("result",_getAllProjects_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IProjectServiceFacadeBeanService#getAllProjects_send()
		 */    
        public function getAllProjects_send():AsyncToken
        {
        	return getAllProjects(_getAllProjects_request.getAllProjects);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _getAllProjects_request:GetAllProjects_request;
		/**
		 * @see IProjectServiceFacadeBeanService#getAllProjects_request_var
		 */
		[Bindable]
		public function get getAllProjects_request_var():GetAllProjects_request
		{
			return _getAllProjects_request;
		}
		
		/**
		 * @private
		 */
		public function set getAllProjects_request_var(request:GetAllProjects_request):void
		{
			_getAllProjects_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _getAllProjects_lastResult:GetAllProjectsResponse;
		[Bindable]
		/**
		 * @see IProjectServiceFacadeBeanService#getAllProjects_lastResult
		 */	  
		public function get getAllProjects_lastResult():GetAllProjectsResponse
		{
			return _getAllProjects_lastResult;
		}
		/**
		 * @private
		 */
		public function set getAllProjects_lastResult(lastResult:GetAllProjectsResponse):void
		{
			_getAllProjects_lastResult = lastResult;
		}
		
		/**
		 * @see IProjectServiceFacadeBeanService#addgetAllProjects()
		 */
		public function addgetAllProjectsEventListener(listener:Function):void
		{
			addEventListener(GetAllProjectsResultEvent.GetAllProjects_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _getAllProjects_populate_results(event:ResultEvent):void
		{
			var e:GetAllProjectsResultEvent = new GetAllProjectsResultEvent();
		            e.result = event.result as GetAllProjectsResponse;
		                       e.headers = event.headers;
		             getAllProjects_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the getProject operation
          

        /**
         * @see IProjectServiceFacadeBeanService#getProject()
         */
        public function getProject(getProject:GetProject):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.getProject(getProject);
            _internal_token.addEventListener("result",_getProject_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IProjectServiceFacadeBeanService#getProject_send()
		 */    
        public function getProject_send():AsyncToken
        {
        	return getProject(_getProject_request.getProject);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _getProject_request:GetProject_request;
		/**
		 * @see IProjectServiceFacadeBeanService#getProject_request_var
		 */
		[Bindable]
		public function get getProject_request_var():GetProject_request
		{
			return _getProject_request;
		}
		
		/**
		 * @private
		 */
		public function set getProject_request_var(request:GetProject_request):void
		{
			_getProject_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _getProject_lastResult:GetProjectResponse;
		[Bindable]
		/**
		 * @see IProjectServiceFacadeBeanService#getProject_lastResult
		 */	  
		public function get getProject_lastResult():GetProjectResponse
		{
			return _getProject_lastResult;
		}
		/**
		 * @private
		 */
		public function set getProject_lastResult(lastResult:GetProjectResponse):void
		{
			_getProject_lastResult = lastResult;
		}
		
		/**
		 * @see IProjectServiceFacadeBeanService#addgetProject()
		 */
		public function addgetProjectEventListener(listener:Function):void
		{
			addEventListener(GetProjectResultEvent.GetProject_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _getProject_populate_results(event:ResultEvent):void
		{
			var e:GetProjectResultEvent = new GetProjectResultEvent();
		            e.result = event.result as GetProjectResponse;
		                       e.headers = event.headers;
		             getProject_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the getProjectsForUser operation
          

        /**
         * @see IProjectServiceFacadeBeanService#getProjectsForUser()
         */
        public function getProjectsForUser(getProjectsForUser:GetProjectsForUser):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.getProjectsForUser(getProjectsForUser);
            _internal_token.addEventListener("result",_getProjectsForUser_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IProjectServiceFacadeBeanService#getProjectsForUser_send()
		 */    
        public function getProjectsForUser_send():AsyncToken
        {
        	return getProjectsForUser(_getProjectsForUser_request.getProjectsForUser);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _getProjectsForUser_request:GetProjectsForUser_request;
		/**
		 * @see IProjectServiceFacadeBeanService#getProjectsForUser_request_var
		 */
		[Bindable]
		public function get getProjectsForUser_request_var():GetProjectsForUser_request
		{
			return _getProjectsForUser_request;
		}
		
		/**
		 * @private
		 */
		public function set getProjectsForUser_request_var(request:GetProjectsForUser_request):void
		{
			_getProjectsForUser_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _getProjectsForUser_lastResult:GetProjectsForUserResponse;
		[Bindable]
		/**
		 * @see IProjectServiceFacadeBeanService#getProjectsForUser_lastResult
		 */	  
		public function get getProjectsForUser_lastResult():GetProjectsForUserResponse
		{
			return _getProjectsForUser_lastResult;
		}
		/**
		 * @private
		 */
		public function set getProjectsForUser_lastResult(lastResult:GetProjectsForUserResponse):void
		{
			_getProjectsForUser_lastResult = lastResult;
		}
		
		/**
		 * @see IProjectServiceFacadeBeanService#addgetProjectsForUser()
		 */
		public function addgetProjectsForUserEventListener(listener:Function):void
		{
			addEventListener(GetProjectsForUserResultEvent.GetProjectsForUser_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _getProjectsForUser_populate_results(event:ResultEvent):void
		{
			var e:GetProjectsForUserResultEvent = new GetProjectsForUserResultEvent();
		            e.result = event.result as GetProjectsForUserResponse;
		                       e.headers = event.headers;
		             getProjectsForUser_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the updateProject operation
          

        /**
         * @see IProjectServiceFacadeBeanService#updateProject()
         */
        public function updateProject(updateProject:UpdateProject):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.updateProject(updateProject);
            _internal_token.addEventListener("result",_updateProject_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IProjectServiceFacadeBeanService#updateProject_send()
		 */    
        public function updateProject_send():AsyncToken
        {
        	return updateProject(_updateProject_request.updateProject);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _updateProject_request:UpdateProject_request;
		/**
		 * @see IProjectServiceFacadeBeanService#updateProject_request_var
		 */
		[Bindable]
		public function get updateProject_request_var():UpdateProject_request
		{
			return _updateProject_request;
		}
		
		/**
		 * @private
		 */
		public function set updateProject_request_var(request:UpdateProject_request):void
		{
			_updateProject_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _updateProject_lastResult:UpdateProjectResponse;
		[Bindable]
		/**
		 * @see IProjectServiceFacadeBeanService#updateProject_lastResult
		 */	  
		public function get updateProject_lastResult():UpdateProjectResponse
		{
			return _updateProject_lastResult;
		}
		/**
		 * @private
		 */
		public function set updateProject_lastResult(lastResult:UpdateProjectResponse):void
		{
			_updateProject_lastResult = lastResult;
		}
		
		/**
		 * @see IProjectServiceFacadeBeanService#addupdateProject()
		 */
		public function addupdateProjectEventListener(listener:Function):void
		{
			addEventListener(UpdateProjectResultEvent.UpdateProject_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _updateProject_populate_results(event:ResultEvent):void
		{
			var e:UpdateProjectResultEvent = new UpdateProjectResultEvent();
		            e.result = event.result as UpdateProjectResponse;
		                       e.headers = event.headers;
		             updateProject_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//service-wide functions
		/**
		 * @see IProjectServiceFacadeBeanService#getWebService()
		 */
		public function getWebService():BaseProjectServiceFacadeBeanService
		{
			return _baseService;
		}
		
		/**
		 * Set the event listener for the fault event which can be triggered by each of the operations defined by the facade
		 */
		public function addProjectServiceFacadeBeanServiceFaultEventListener(listener:Function):void
		{
			addEventListener("fault",listener);
		}
		
		/**
		 * Internal function to re-dispatch the fault event passed on by the base service implementation
		 * @private
		 */
		 
		 private function throwFault(event:FaultEvent):void
		 {
		 	dispatchEvent(event);
		 }
    }
}
