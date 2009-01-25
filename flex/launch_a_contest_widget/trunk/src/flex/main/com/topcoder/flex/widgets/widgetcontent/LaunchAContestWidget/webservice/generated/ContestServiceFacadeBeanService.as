/**
 * ContestServiceFacadeBeanServiceService.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */
 /**
  * Usage example: to use this service from within your Flex application you have two choices:
  * Use it via Actionscript only
  * Use it via MXML tags
  * Actionscript sample code:
  * Step 1: create an instance of the service; pass it the LCDS destination string if any
  * var myService:ContestServiceFacadeBeanService= new ContestServiceFacadeBeanService();
  * Step 2: for the desired operation add a result handler (a function that you have already defined previously)  
  * myService.addaddChangeHistoryEventListener(myResultHandlingFunction);
  * Step 3: Call the operation as a method on the service. Pass the right values as arguments:
  * myService.addChangeHistory(myaddChangeHistory,myarg0);
  *
  * MXML sample code:
  * First you need to map the package where the files were generated to a namespace, usually on the <mx:Application> tag, 
  * like this: xmlns:srv="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.*"
  * Define the service and within its tags set the request wrapper for the desired operation
  * <srv:ContestServiceFacadeBeanService id="myService">
  *   <srv:addChangeHistory_request_var>
  *		<srv:AddChangeHistory_request addChangeHistory=myValue,arg0=myValue/>
  *   </srv:addChangeHistory_request_var>
  * </srv:ContestServiceFacadeBeanService>
  * Then call the operation for which you have set the request wrapper value above, like this:
  * <mx:Button id="myButton" label="Call operation" click="myService.addChangeHistory_send()" />
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
     * Dispatches when a call to the operation addChangeHistory completes with success
     * and returns some data
     * @eventType AddChangeHistoryResultEvent
     */
    [Event(name="AddChangeHistory_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.AddChangeHistoryResultEvent")]
    
    /**
     * Dispatches when a call to the operation addDocumentToContest completes with success
     * and returns some data
     * @eventType AddDocumentToContestResultEvent
     */
    [Event(name="AddDocumentToContest_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.AddDocumentToContestResultEvent")]
    
    /**
     * Dispatches when a call to the operation createContest completes with success
     * and returns some data
     * @eventType CreateContestResultEvent
     */
    [Event(name="CreateContest_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.CreateContestResultEvent")]
    
    /**
     * Dispatches when a call to the operation createContestPayment completes with success
     * and returns some data
     * @eventType CreateContestPaymentResultEvent
     */
    [Event(name="CreateContestPayment_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.CreateContestPaymentResultEvent")]
    
    /**
     * Dispatches when a call to the operation deleteContest completes with success
     * and returns some data
     * @eventType DeleteContestResultEvent
     */
    [Event(name="DeleteContest_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.DeleteContestResultEvent")]
    
    /**
     * Dispatches when a call to the operation editContestPayment completes with success
     * and returns some data
     * @eventType EditContestPaymentResultEvent
     */
    [Event(name="EditContestPayment_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.EditContestPaymentResultEvent")]
    
    /**
     * Dispatches when a call to the operation getAllContestHeaders completes with success
     * and returns some data
     * @eventType GetAllContestHeadersResultEvent
     */
    [Event(name="GetAllContestHeaders_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetAllContestHeadersResultEvent")]
    
    /**
     * Dispatches when a call to the operation getAllContestTypes completes with success
     * and returns some data
     * @eventType GetAllContestTypesResultEvent
     */
    [Event(name="GetAllContestTypes_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetAllContestTypesResultEvent")]
    
    /**
     * Dispatches when a call to the operation getAllContests completes with success
     * and returns some data
     * @eventType GetAllContestsResultEvent
     */
    [Event(name="GetAllContests_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetAllContestsResultEvent")]
    
    /**
     * Dispatches when a call to the operation getAllDocumentTypes completes with success
     * and returns some data
     * @eventType GetAllDocumentTypesResultEvent
     */
    [Event(name="GetAllDocumentTypes_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetAllDocumentTypesResultEvent")]
    
    /**
     * Dispatches when a call to the operation getAllMediums completes with success
     * and returns some data
     * @eventType GetAllMediumsResultEvent
     */
    [Event(name="GetAllMediums_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetAllMediumsResultEvent")]
    
    /**
     * Dispatches when a call to the operation getAllStudioFileTypes completes with success
     * and returns some data
     * @eventType GetAllStudioFileTypesResultEvent
     */
    [Event(name="GetAllStudioFileTypes_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetAllStudioFileTypesResultEvent")]
    
    /**
     * Dispatches when a call to the operation getChangeHistory completes with success
     * and returns some data
     * @eventType GetChangeHistoryResultEvent
     */
    [Event(name="GetChangeHistory_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetChangeHistoryResultEvent")]
    
    /**
     * Dispatches when a call to the operation getContest completes with success
     * and returns some data
     * @eventType GetContestResultEvent
     */
    [Event(name="GetContest_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetContestResultEvent")]
    
    /**
     * Dispatches when a call to the operation getContestPayment completes with success
     * and returns some data
     * @eventType GetContestPaymentResultEvent
     */
    [Event(name="GetContestPayment_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetContestPaymentResultEvent")]
    
    /**
     * Dispatches when a call to the operation getContestsForClient completes with success
     * and returns some data
     * @eventType GetContestsForClientResultEvent
     */
    [Event(name="GetContestsForClient_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetContestsForClientResultEvent")]
    
    /**
     * Dispatches when a call to the operation getContestsForProject completes with success
     * and returns some data
     * @eventType GetContestsForProjectResultEvent
     */
    [Event(name="GetContestsForProject_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetContestsForProjectResultEvent")]
    
    /**
     * Dispatches when a call to the operation getLatestChanges completes with success
     * and returns some data
     * @eventType GetLatestChangesResultEvent
     */
    [Event(name="GetLatestChanges_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetLatestChangesResultEvent")]
    
    /**
     * Dispatches when a call to the operation getMimeTypeId completes with success
     * and returns some data
     * @eventType GetMimeTypeIdResultEvent
     */
    [Event(name="GetMimeTypeId_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetMimeTypeIdResultEvent")]
    
    /**
     * Dispatches when a call to the operation getStatusList completes with success
     * and returns some data
     * @eventType GetStatusListResultEvent
     */
    [Event(name="GetStatusList_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetStatusListResultEvent")]
    
    /**
     * Dispatches when a call to the operation getSubmissionFileTypes completes with success
     * and returns some data
     * @eventType GetSubmissionFileTypesResultEvent
     */
    [Event(name="GetSubmissionFileTypes_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetSubmissionFileTypesResultEvent")]
    
    /**
     * Dispatches when a call to the operation markForPurchase completes with success
     * and returns some data
     * @eventType MarkForPurchaseResultEvent
     */
    [Event(name="MarkForPurchase_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.MarkForPurchaseResultEvent")]
    
    /**
     * Dispatches when a call to the operation processMissingPayments completes with success
     * and returns some data
     * @eventType ProcessMissingPaymentsResultEvent
     */
    [Event(name="ProcessMissingPayments_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.ProcessMissingPaymentsResultEvent")]
    
    /**
     * Dispatches when a call to the operation purchaseSubmission completes with success
     * and returns some data
     * @eventType PurchaseSubmissionResultEvent
     */
    [Event(name="PurchaseSubmission_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.PurchaseSubmissionResultEvent")]
    
    /**
     * Dispatches when a call to the operation removeContestPayment completes with success
     * and returns some data
     * @eventType RemoveContestPaymentResultEvent
     */
    [Event(name="RemoveContestPayment_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.RemoveContestPaymentResultEvent")]
    
    /**
     * Dispatches when a call to the operation removeDocument completes with success
     * and returns some data
     * @eventType RemoveDocumentResultEvent
     */
    [Event(name="RemoveDocument_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.RemoveDocumentResultEvent")]
    
    /**
     * Dispatches when a call to the operation removeDocumentFromContest completes with success
     * and returns some data
     * @eventType RemoveDocumentFromContestResultEvent
     */
    [Event(name="RemoveDocumentFromContest_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.RemoveDocumentFromContestResultEvent")]
    
    /**
     * Dispatches when a call to the operation removeSubmission completes with success
     * and returns some data
     * @eventType RemoveSubmissionResultEvent
     */
    [Event(name="RemoveSubmission_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.RemoveSubmissionResultEvent")]
    
    /**
     * Dispatches when a call to the operation retrieveAllSubmissionsByMember completes with success
     * and returns some data
     * @eventType RetrieveAllSubmissionsByMemberResultEvent
     */
    [Event(name="RetrieveAllSubmissionsByMember_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.RetrieveAllSubmissionsByMemberResultEvent")]
    
    /**
     * Dispatches when a call to the operation retrieveSubmission completes with success
     * and returns some data
     * @eventType RetrieveSubmissionResultEvent
     */
    [Event(name="RetrieveSubmission_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.RetrieveSubmissionResultEvent")]
    
    /**
     * Dispatches when a call to the operation retrieveSubmissionsForContest completes with success
     * and returns some data
     * @eventType RetrieveSubmissionsForContestResultEvent
     */
    [Event(name="RetrieveSubmissionsForContest_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.RetrieveSubmissionsForContestResultEvent")]
    
    /**
     * Dispatches when a call to the operation searchContests completes with success
     * and returns some data
     * @eventType SearchContestsResultEvent
     */
    [Event(name="SearchContests_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.SearchContestsResultEvent")]
    
    /**
     * Dispatches when a call to the operation setSubmissionPlacement completes with success
     * and returns some data
     * @eventType SetSubmissionPlacementResultEvent
     */
    [Event(name="SetSubmissionPlacement_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.SetSubmissionPlacementResultEvent")]
    
    /**
     * Dispatches when a call to the operation setSubmissionPrize completes with success
     * and returns some data
     * @eventType SetSubmissionPrizeResultEvent
     */
    [Event(name="SetSubmissionPrize_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.SetSubmissionPrizeResultEvent")]
    
    /**
     * Dispatches when a call to the operation updateContest completes with success
     * and returns some data
     * @eventType UpdateContestResultEvent
     */
    [Event(name="UpdateContest_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.UpdateContestResultEvent")]
    
    /**
     * Dispatches when a call to the operation updateContestStatus completes with success
     * and returns some data
     * @eventType UpdateContestStatusResultEvent
     */
    [Event(name="UpdateContestStatus_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.UpdateContestStatusResultEvent")]
    
    /**
     * Dispatches when a call to the operation updateSubmission completes with success
     * and returns some data
     * @eventType UpdateSubmissionResultEvent
     */
    [Event(name="UpdateSubmission_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.UpdateSubmissionResultEvent")]
    
    /**
     * Dispatches when a call to the operation uploadDocument completes with success
     * and returns some data
     * @eventType UploadDocumentResultEvent
     */
    [Event(name="UploadDocument_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.UploadDocumentResultEvent")]
    
    /**
     * Dispatches when a call to the operation uploadDocumentForContest completes with success
     * and returns some data
     * @eventType UploadDocumentForContestResultEvent
     */
    [Event(name="UploadDocumentForContest_result", type="com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.UploadDocumentForContestResultEvent")]
    
	/**
	 * Dispatches when the operation that has been called fails. The fault event is common for all operations
	 * of the WSDL
	 * @eventType mx.rpc.events.FaultEvent
	 */
    [Event(name="fault", type="mx.rpc.events.FaultEvent")]

	public class ContestServiceFacadeBeanService extends EventDispatcher implements IContestServiceFacadeBeanService
	{
    	private var _baseService:BaseContestServiceFacadeBeanService;
        
        /**
         * Constructor for the facade; sets the destination and create a baseService instance
         * @param The LCDS destination (if any) associated with the imported WSDL
         */  
        public function ContestServiceFacadeBeanService(destination:String=null,rootURL:String=null)
        {
        	_baseService = new BaseContestServiceFacadeBeanService(destination,rootURL);
        }
        
		//stub functions for the addChangeHistory operation
          

        /**
         * @see IContestServiceFacadeBeanService#addChangeHistory()
         */
        public function addChangeHistory(addChangeHistory:AddChangeHistory):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.addChangeHistory(addChangeHistory);
            _internal_token.addEventListener("result",_addChangeHistory_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#addChangeHistory_send()
		 */    
        public function addChangeHistory_send():AsyncToken
        {
        	return addChangeHistory(_addChangeHistory_request.addChangeHistory);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _addChangeHistory_request:AddChangeHistory_request;
		/**
		 * @see IContestServiceFacadeBeanService#addChangeHistory_request_var
		 */
		[Bindable]
		public function get addChangeHistory_request_var():AddChangeHistory_request
		{
			return _addChangeHistory_request;
		}
		
		/**
		 * @private
		 */
		public function set addChangeHistory_request_var(request:AddChangeHistory_request):void
		{
			_addChangeHistory_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _addChangeHistory_lastResult:AddChangeHistoryResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#addChangeHistory_lastResult
		 */	  
		public function get addChangeHistory_lastResult():AddChangeHistoryResponse
		{
			return _addChangeHistory_lastResult;
		}
		/**
		 * @private
		 */
		public function set addChangeHistory_lastResult(lastResult:AddChangeHistoryResponse):void
		{
			_addChangeHistory_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addaddChangeHistory()
		 */
		public function addaddChangeHistoryEventListener(listener:Function):void
		{
			addEventListener(AddChangeHistoryResultEvent.AddChangeHistory_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _addChangeHistory_populate_results(event:ResultEvent):void
		{
			var e:AddChangeHistoryResultEvent = new AddChangeHistoryResultEvent();
		            e.result = event.result as AddChangeHistoryResponse;
		                       e.headers = event.headers;
		             addChangeHistory_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the addDocumentToContest operation
          

        /**
         * @see IContestServiceFacadeBeanService#addDocumentToContest()
         */
        public function addDocumentToContest(addDocumentToContest:AddDocumentToContest):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.addDocumentToContest(addDocumentToContest);
            _internal_token.addEventListener("result",_addDocumentToContest_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#addDocumentToContest_send()
		 */    
        public function addDocumentToContest_send():AsyncToken
        {
        	return addDocumentToContest(_addDocumentToContest_request.addDocumentToContest);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _addDocumentToContest_request:AddDocumentToContest_request;
		/**
		 * @see IContestServiceFacadeBeanService#addDocumentToContest_request_var
		 */
		[Bindable]
		public function get addDocumentToContest_request_var():AddDocumentToContest_request
		{
			return _addDocumentToContest_request;
		}
		
		/**
		 * @private
		 */
		public function set addDocumentToContest_request_var(request:AddDocumentToContest_request):void
		{
			_addDocumentToContest_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _addDocumentToContest_lastResult:AddDocumentToContestResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#addDocumentToContest_lastResult
		 */	  
		public function get addDocumentToContest_lastResult():AddDocumentToContestResponse
		{
			return _addDocumentToContest_lastResult;
		}
		/**
		 * @private
		 */
		public function set addDocumentToContest_lastResult(lastResult:AddDocumentToContestResponse):void
		{
			_addDocumentToContest_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addaddDocumentToContest()
		 */
		public function addaddDocumentToContestEventListener(listener:Function):void
		{
			addEventListener(AddDocumentToContestResultEvent.AddDocumentToContest_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _addDocumentToContest_populate_results(event:ResultEvent):void
		{
			var e:AddDocumentToContestResultEvent = new AddDocumentToContestResultEvent();
		            e.result = event.result as AddDocumentToContestResponse;
		                       e.headers = event.headers;
		             addDocumentToContest_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the createContest operation
          

        /**
         * @see IContestServiceFacadeBeanService#createContest()
         */
        public function createContest(createContest:CreateContest):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.createContest(createContest);
            _internal_token.addEventListener("result",_createContest_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#createContest_send()
		 */    
        public function createContest_send():AsyncToken
        {
        	return createContest(_createContest_request.createContest);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _createContest_request:CreateContest_request;
		/**
		 * @see IContestServiceFacadeBeanService#createContest_request_var
		 */
		[Bindable]
		public function get createContest_request_var():CreateContest_request
		{
			return _createContest_request;
		}
		
		/**
		 * @private
		 */
		public function set createContest_request_var(request:CreateContest_request):void
		{
			_createContest_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _createContest_lastResult:CreateContestResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#createContest_lastResult
		 */	  
		public function get createContest_lastResult():CreateContestResponse
		{
			return _createContest_lastResult;
		}
		/**
		 * @private
		 */
		public function set createContest_lastResult(lastResult:CreateContestResponse):void
		{
			_createContest_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addcreateContest()
		 */
		public function addcreateContestEventListener(listener:Function):void
		{
			addEventListener(CreateContestResultEvent.CreateContest_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _createContest_populate_results(event:ResultEvent):void
		{
			var e:CreateContestResultEvent = new CreateContestResultEvent();
		            e.result = event.result as CreateContestResponse;
		                       e.headers = event.headers;
		             createContest_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the createContestPayment operation
          

        /**
         * @see IContestServiceFacadeBeanService#createContestPayment()
         */
        public function createContestPayment(createContestPayment:CreateContestPayment):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.createContestPayment(createContestPayment);
            _internal_token.addEventListener("result",_createContestPayment_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#createContestPayment_send()
		 */    
        public function createContestPayment_send():AsyncToken
        {
        	return createContestPayment(_createContestPayment_request.createContestPayment);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _createContestPayment_request:CreateContestPayment_request;
		/**
		 * @see IContestServiceFacadeBeanService#createContestPayment_request_var
		 */
		[Bindable]
		public function get createContestPayment_request_var():CreateContestPayment_request
		{
			return _createContestPayment_request;
		}
		
		/**
		 * @private
		 */
		public function set createContestPayment_request_var(request:CreateContestPayment_request):void
		{
			_createContestPayment_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _createContestPayment_lastResult:CreateContestPaymentResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#createContestPayment_lastResult
		 */	  
		public function get createContestPayment_lastResult():CreateContestPaymentResponse
		{
			return _createContestPayment_lastResult;
		}
		/**
		 * @private
		 */
		public function set createContestPayment_lastResult(lastResult:CreateContestPaymentResponse):void
		{
			_createContestPayment_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addcreateContestPayment()
		 */
		public function addcreateContestPaymentEventListener(listener:Function):void
		{
			addEventListener(CreateContestPaymentResultEvent.CreateContestPayment_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _createContestPayment_populate_results(event:ResultEvent):void
		{
			var e:CreateContestPaymentResultEvent = new CreateContestPaymentResultEvent();
		            e.result = event.result as CreateContestPaymentResponse;
		                       e.headers = event.headers;
		             createContestPayment_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the deleteContest operation
          

        /**
         * @see IContestServiceFacadeBeanService#deleteContest()
         */
        public function deleteContest(deleteContest:DeleteContest):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.deleteContest(deleteContest);
            _internal_token.addEventListener("result",_deleteContest_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#deleteContest_send()
		 */    
        public function deleteContest_send():AsyncToken
        {
        	return deleteContest(_deleteContest_request.deleteContest);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _deleteContest_request:DeleteContest_request;
		/**
		 * @see IContestServiceFacadeBeanService#deleteContest_request_var
		 */
		[Bindable]
		public function get deleteContest_request_var():DeleteContest_request
		{
			return _deleteContest_request;
		}
		
		/**
		 * @private
		 */
		public function set deleteContest_request_var(request:DeleteContest_request):void
		{
			_deleteContest_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _deleteContest_lastResult:DeleteContestResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#deleteContest_lastResult
		 */	  
		public function get deleteContest_lastResult():DeleteContestResponse
		{
			return _deleteContest_lastResult;
		}
		/**
		 * @private
		 */
		public function set deleteContest_lastResult(lastResult:DeleteContestResponse):void
		{
			_deleteContest_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#adddeleteContest()
		 */
		public function adddeleteContestEventListener(listener:Function):void
		{
			addEventListener(DeleteContestResultEvent.DeleteContest_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _deleteContest_populate_results(event:ResultEvent):void
		{
			var e:DeleteContestResultEvent = new DeleteContestResultEvent();
		            e.result = event.result as DeleteContestResponse;
		                       e.headers = event.headers;
		             deleteContest_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the editContestPayment operation
          

        /**
         * @see IContestServiceFacadeBeanService#editContestPayment()
         */
        public function editContestPayment(editContestPayment:EditContestPayment):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.editContestPayment(editContestPayment);
            _internal_token.addEventListener("result",_editContestPayment_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#editContestPayment_send()
		 */    
        public function editContestPayment_send():AsyncToken
        {
        	return editContestPayment(_editContestPayment_request.editContestPayment);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _editContestPayment_request:EditContestPayment_request;
		/**
		 * @see IContestServiceFacadeBeanService#editContestPayment_request_var
		 */
		[Bindable]
		public function get editContestPayment_request_var():EditContestPayment_request
		{
			return _editContestPayment_request;
		}
		
		/**
		 * @private
		 */
		public function set editContestPayment_request_var(request:EditContestPayment_request):void
		{
			_editContestPayment_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _editContestPayment_lastResult:EditContestPaymentResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#editContestPayment_lastResult
		 */	  
		public function get editContestPayment_lastResult():EditContestPaymentResponse
		{
			return _editContestPayment_lastResult;
		}
		/**
		 * @private
		 */
		public function set editContestPayment_lastResult(lastResult:EditContestPaymentResponse):void
		{
			_editContestPayment_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addeditContestPayment()
		 */
		public function addeditContestPaymentEventListener(listener:Function):void
		{
			addEventListener(EditContestPaymentResultEvent.EditContestPayment_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _editContestPayment_populate_results(event:ResultEvent):void
		{
			var e:EditContestPaymentResultEvent = new EditContestPaymentResultEvent();
		            e.result = event.result as EditContestPaymentResponse;
		                       e.headers = event.headers;
		             editContestPayment_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the getAllContestHeaders operation
          

        /**
         * @see IContestServiceFacadeBeanService#getAllContestHeaders()
         */
        public function getAllContestHeaders(getAllContestHeaders:GetAllContestHeaders):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.getAllContestHeaders(getAllContestHeaders);
            _internal_token.addEventListener("result",_getAllContestHeaders_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#getAllContestHeaders_send()
		 */    
        public function getAllContestHeaders_send():AsyncToken
        {
        	return getAllContestHeaders(_getAllContestHeaders_request.getAllContestHeaders);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _getAllContestHeaders_request:GetAllContestHeaders_request;
		/**
		 * @see IContestServiceFacadeBeanService#getAllContestHeaders_request_var
		 */
		[Bindable]
		public function get getAllContestHeaders_request_var():GetAllContestHeaders_request
		{
			return _getAllContestHeaders_request;
		}
		
		/**
		 * @private
		 */
		public function set getAllContestHeaders_request_var(request:GetAllContestHeaders_request):void
		{
			_getAllContestHeaders_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _getAllContestHeaders_lastResult:GetAllContestHeadersResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#getAllContestHeaders_lastResult
		 */	  
		public function get getAllContestHeaders_lastResult():GetAllContestHeadersResponse
		{
			return _getAllContestHeaders_lastResult;
		}
		/**
		 * @private
		 */
		public function set getAllContestHeaders_lastResult(lastResult:GetAllContestHeadersResponse):void
		{
			_getAllContestHeaders_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addgetAllContestHeaders()
		 */
		public function addgetAllContestHeadersEventListener(listener:Function):void
		{
			addEventListener(GetAllContestHeadersResultEvent.GetAllContestHeaders_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _getAllContestHeaders_populate_results(event:ResultEvent):void
		{
			var e:GetAllContestHeadersResultEvent = new GetAllContestHeadersResultEvent();
		            e.result = event.result as GetAllContestHeadersResponse;
		                       e.headers = event.headers;
		             getAllContestHeaders_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the getAllContestTypes operation
          

        /**
         * @see IContestServiceFacadeBeanService#getAllContestTypes()
         */
        public function getAllContestTypes(getAllContestTypes:GetAllContestTypes):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.getAllContestTypes(getAllContestTypes);
            _internal_token.addEventListener("result",_getAllContestTypes_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#getAllContestTypes_send()
		 */    
        public function getAllContestTypes_send():AsyncToken
        {
        	return getAllContestTypes(_getAllContestTypes_request.getAllContestTypes);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _getAllContestTypes_request:GetAllContestTypes_request;
		/**
		 * @see IContestServiceFacadeBeanService#getAllContestTypes_request_var
		 */
		[Bindable]
		public function get getAllContestTypes_request_var():GetAllContestTypes_request
		{
			return _getAllContestTypes_request;
		}
		
		/**
		 * @private
		 */
		public function set getAllContestTypes_request_var(request:GetAllContestTypes_request):void
		{
			_getAllContestTypes_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _getAllContestTypes_lastResult:GetAllContestTypesResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#getAllContestTypes_lastResult
		 */	  
		public function get getAllContestTypes_lastResult():GetAllContestTypesResponse
		{
			return _getAllContestTypes_lastResult;
		}
		/**
		 * @private
		 */
		public function set getAllContestTypes_lastResult(lastResult:GetAllContestTypesResponse):void
		{
			_getAllContestTypes_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addgetAllContestTypes()
		 */
		public function addgetAllContestTypesEventListener(listener:Function):void
		{
			addEventListener(GetAllContestTypesResultEvent.GetAllContestTypes_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _getAllContestTypes_populate_results(event:ResultEvent):void
		{
			var e:GetAllContestTypesResultEvent = new GetAllContestTypesResultEvent();
		            e.result = event.result as GetAllContestTypesResponse;
		                       e.headers = event.headers;
		             getAllContestTypes_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the getAllContests operation
          

        /**
         * @see IContestServiceFacadeBeanService#getAllContests()
         */
        public function getAllContests(getAllContests:GetAllContests):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.getAllContests(getAllContests);
            _internal_token.addEventListener("result",_getAllContests_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#getAllContests_send()
		 */    
        public function getAllContests_send():AsyncToken
        {
        	return getAllContests(_getAllContests_request.getAllContests);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _getAllContests_request:GetAllContests_request;
		/**
		 * @see IContestServiceFacadeBeanService#getAllContests_request_var
		 */
		[Bindable]
		public function get getAllContests_request_var():GetAllContests_request
		{
			return _getAllContests_request;
		}
		
		/**
		 * @private
		 */
		public function set getAllContests_request_var(request:GetAllContests_request):void
		{
			_getAllContests_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _getAllContests_lastResult:GetAllContestsResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#getAllContests_lastResult
		 */	  
		public function get getAllContests_lastResult():GetAllContestsResponse
		{
			return _getAllContests_lastResult;
		}
		/**
		 * @private
		 */
		public function set getAllContests_lastResult(lastResult:GetAllContestsResponse):void
		{
			_getAllContests_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addgetAllContests()
		 */
		public function addgetAllContestsEventListener(listener:Function):void
		{
			addEventListener(GetAllContestsResultEvent.GetAllContests_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _getAllContests_populate_results(event:ResultEvent):void
		{
			var e:GetAllContestsResultEvent = new GetAllContestsResultEvent();
		            e.result = event.result as GetAllContestsResponse;
		                       e.headers = event.headers;
		             getAllContests_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the getAllDocumentTypes operation
          

        /**
         * @see IContestServiceFacadeBeanService#getAllDocumentTypes()
         */
        public function getAllDocumentTypes(getAllDocumentTypes:GetAllDocumentTypes):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.getAllDocumentTypes(getAllDocumentTypes);
            _internal_token.addEventListener("result",_getAllDocumentTypes_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#getAllDocumentTypes_send()
		 */    
        public function getAllDocumentTypes_send():AsyncToken
        {
        	return getAllDocumentTypes(_getAllDocumentTypes_request.getAllDocumentTypes);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _getAllDocumentTypes_request:GetAllDocumentTypes_request;
		/**
		 * @see IContestServiceFacadeBeanService#getAllDocumentTypes_request_var
		 */
		[Bindable]
		public function get getAllDocumentTypes_request_var():GetAllDocumentTypes_request
		{
			return _getAllDocumentTypes_request;
		}
		
		/**
		 * @private
		 */
		public function set getAllDocumentTypes_request_var(request:GetAllDocumentTypes_request):void
		{
			_getAllDocumentTypes_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _getAllDocumentTypes_lastResult:GetAllDocumentTypesResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#getAllDocumentTypes_lastResult
		 */	  
		public function get getAllDocumentTypes_lastResult():GetAllDocumentTypesResponse
		{
			return _getAllDocumentTypes_lastResult;
		}
		/**
		 * @private
		 */
		public function set getAllDocumentTypes_lastResult(lastResult:GetAllDocumentTypesResponse):void
		{
			_getAllDocumentTypes_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addgetAllDocumentTypes()
		 */
		public function addgetAllDocumentTypesEventListener(listener:Function):void
		{
			addEventListener(GetAllDocumentTypesResultEvent.GetAllDocumentTypes_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _getAllDocumentTypes_populate_results(event:ResultEvent):void
		{
			var e:GetAllDocumentTypesResultEvent = new GetAllDocumentTypesResultEvent();
		            e.result = event.result as GetAllDocumentTypesResponse;
		                       e.headers = event.headers;
		             getAllDocumentTypes_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the getAllMediums operation
          

        /**
         * @see IContestServiceFacadeBeanService#getAllMediums()
         */
        public function getAllMediums(getAllMediums:GetAllMediums):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.getAllMediums(getAllMediums);
            _internal_token.addEventListener("result",_getAllMediums_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#getAllMediums_send()
		 */    
        public function getAllMediums_send():AsyncToken
        {
        	return getAllMediums(_getAllMediums_request.getAllMediums);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _getAllMediums_request:GetAllMediums_request;
		/**
		 * @see IContestServiceFacadeBeanService#getAllMediums_request_var
		 */
		[Bindable]
		public function get getAllMediums_request_var():GetAllMediums_request
		{
			return _getAllMediums_request;
		}
		
		/**
		 * @private
		 */
		public function set getAllMediums_request_var(request:GetAllMediums_request):void
		{
			_getAllMediums_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _getAllMediums_lastResult:GetAllMediumsResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#getAllMediums_lastResult
		 */	  
		public function get getAllMediums_lastResult():GetAllMediumsResponse
		{
			return _getAllMediums_lastResult;
		}
		/**
		 * @private
		 */
		public function set getAllMediums_lastResult(lastResult:GetAllMediumsResponse):void
		{
			_getAllMediums_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addgetAllMediums()
		 */
		public function addgetAllMediumsEventListener(listener:Function):void
		{
			addEventListener(GetAllMediumsResultEvent.GetAllMediums_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _getAllMediums_populate_results(event:ResultEvent):void
		{
			var e:GetAllMediumsResultEvent = new GetAllMediumsResultEvent();
		            e.result = event.result as GetAllMediumsResponse;
		                       e.headers = event.headers;
		             getAllMediums_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the getAllStudioFileTypes operation
          

        /**
         * @see IContestServiceFacadeBeanService#getAllStudioFileTypes()
         */
        public function getAllStudioFileTypes(getAllStudioFileTypes:GetAllStudioFileTypes):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.getAllStudioFileTypes(getAllStudioFileTypes);
            _internal_token.addEventListener("result",_getAllStudioFileTypes_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#getAllStudioFileTypes_send()
		 */    
        public function getAllStudioFileTypes_send():AsyncToken
        {
        	return getAllStudioFileTypes(_getAllStudioFileTypes_request.getAllStudioFileTypes);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _getAllStudioFileTypes_request:GetAllStudioFileTypes_request;
		/**
		 * @see IContestServiceFacadeBeanService#getAllStudioFileTypes_request_var
		 */
		[Bindable]
		public function get getAllStudioFileTypes_request_var():GetAllStudioFileTypes_request
		{
			return _getAllStudioFileTypes_request;
		}
		
		/**
		 * @private
		 */
		public function set getAllStudioFileTypes_request_var(request:GetAllStudioFileTypes_request):void
		{
			_getAllStudioFileTypes_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _getAllStudioFileTypes_lastResult:GetAllStudioFileTypesResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#getAllStudioFileTypes_lastResult
		 */	  
		public function get getAllStudioFileTypes_lastResult():GetAllStudioFileTypesResponse
		{
			return _getAllStudioFileTypes_lastResult;
		}
		/**
		 * @private
		 */
		public function set getAllStudioFileTypes_lastResult(lastResult:GetAllStudioFileTypesResponse):void
		{
			_getAllStudioFileTypes_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addgetAllStudioFileTypes()
		 */
		public function addgetAllStudioFileTypesEventListener(listener:Function):void
		{
			addEventListener(GetAllStudioFileTypesResultEvent.GetAllStudioFileTypes_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _getAllStudioFileTypes_populate_results(event:ResultEvent):void
		{
			var e:GetAllStudioFileTypesResultEvent = new GetAllStudioFileTypesResultEvent();
		            e.result = event.result as GetAllStudioFileTypesResponse;
		                       e.headers = event.headers;
		             getAllStudioFileTypes_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the getChangeHistory operation
          

        /**
         * @see IContestServiceFacadeBeanService#getChangeHistory()
         */
        public function getChangeHistory(getChangeHistory:GetChangeHistory):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.getChangeHistory(getChangeHistory);
            _internal_token.addEventListener("result",_getChangeHistory_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#getChangeHistory_send()
		 */    
        public function getChangeHistory_send():AsyncToken
        {
        	return getChangeHistory(_getChangeHistory_request.getChangeHistory);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _getChangeHistory_request:GetChangeHistory_request;
		/**
		 * @see IContestServiceFacadeBeanService#getChangeHistory_request_var
		 */
		[Bindable]
		public function get getChangeHistory_request_var():GetChangeHistory_request
		{
			return _getChangeHistory_request;
		}
		
		/**
		 * @private
		 */
		public function set getChangeHistory_request_var(request:GetChangeHistory_request):void
		{
			_getChangeHistory_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _getChangeHistory_lastResult:GetChangeHistoryResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#getChangeHistory_lastResult
		 */	  
		public function get getChangeHistory_lastResult():GetChangeHistoryResponse
		{
			return _getChangeHistory_lastResult;
		}
		/**
		 * @private
		 */
		public function set getChangeHistory_lastResult(lastResult:GetChangeHistoryResponse):void
		{
			_getChangeHistory_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addgetChangeHistory()
		 */
		public function addgetChangeHistoryEventListener(listener:Function):void
		{
			addEventListener(GetChangeHistoryResultEvent.GetChangeHistory_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _getChangeHistory_populate_results(event:ResultEvent):void
		{
			var e:GetChangeHistoryResultEvent = new GetChangeHistoryResultEvent();
		            e.result = event.result as GetChangeHistoryResponse;
		                       e.headers = event.headers;
		             getChangeHistory_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the getContest operation
          

        /**
         * @see IContestServiceFacadeBeanService#getContest()
         */
        public function getContest(getContest:GetContest):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.getContest(getContest);
            _internal_token.addEventListener("result",_getContest_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#getContest_send()
		 */    
        public function getContest_send():AsyncToken
        {
        	return getContest(_getContest_request.getContest);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _getContest_request:GetContest_request;
		/**
		 * @see IContestServiceFacadeBeanService#getContest_request_var
		 */
		[Bindable]
		public function get getContest_request_var():GetContest_request
		{
			return _getContest_request;
		}
		
		/**
		 * @private
		 */
		public function set getContest_request_var(request:GetContest_request):void
		{
			_getContest_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _getContest_lastResult:GetContestResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#getContest_lastResult
		 */	  
		public function get getContest_lastResult():GetContestResponse
		{
			return _getContest_lastResult;
		}
		/**
		 * @private
		 */
		public function set getContest_lastResult(lastResult:GetContestResponse):void
		{
			_getContest_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addgetContest()
		 */
		public function addgetContestEventListener(listener:Function):void
		{
			addEventListener(GetContestResultEvent.GetContest_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _getContest_populate_results(event:ResultEvent):void
		{
			var e:GetContestResultEvent = new GetContestResultEvent();
		            e.result = event.result as GetContestResponse;
		                       e.headers = event.headers;
		             getContest_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the getContestPayment operation
          

        /**
         * @see IContestServiceFacadeBeanService#getContestPayment()
         */
        public function getContestPayment(getContestPayment:GetContestPayment):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.getContestPayment(getContestPayment);
            _internal_token.addEventListener("result",_getContestPayment_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#getContestPayment_send()
		 */    
        public function getContestPayment_send():AsyncToken
        {
        	return getContestPayment(_getContestPayment_request.getContestPayment);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _getContestPayment_request:GetContestPayment_request;
		/**
		 * @see IContestServiceFacadeBeanService#getContestPayment_request_var
		 */
		[Bindable]
		public function get getContestPayment_request_var():GetContestPayment_request
		{
			return _getContestPayment_request;
		}
		
		/**
		 * @private
		 */
		public function set getContestPayment_request_var(request:GetContestPayment_request):void
		{
			_getContestPayment_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _getContestPayment_lastResult:GetContestPaymentResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#getContestPayment_lastResult
		 */	  
		public function get getContestPayment_lastResult():GetContestPaymentResponse
		{
			return _getContestPayment_lastResult;
		}
		/**
		 * @private
		 */
		public function set getContestPayment_lastResult(lastResult:GetContestPaymentResponse):void
		{
			_getContestPayment_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addgetContestPayment()
		 */
		public function addgetContestPaymentEventListener(listener:Function):void
		{
			addEventListener(GetContestPaymentResultEvent.GetContestPayment_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _getContestPayment_populate_results(event:ResultEvent):void
		{
			var e:GetContestPaymentResultEvent = new GetContestPaymentResultEvent();
		            e.result = event.result as GetContestPaymentResponse;
		                       e.headers = event.headers;
		             getContestPayment_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the getContestsForClient operation
          

        /**
         * @see IContestServiceFacadeBeanService#getContestsForClient()
         */
        public function getContestsForClient(getContestsForClient:GetContestsForClient):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.getContestsForClient(getContestsForClient);
            _internal_token.addEventListener("result",_getContestsForClient_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#getContestsForClient_send()
		 */    
        public function getContestsForClient_send():AsyncToken
        {
        	return getContestsForClient(_getContestsForClient_request.getContestsForClient);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _getContestsForClient_request:GetContestsForClient_request;
		/**
		 * @see IContestServiceFacadeBeanService#getContestsForClient_request_var
		 */
		[Bindable]
		public function get getContestsForClient_request_var():GetContestsForClient_request
		{
			return _getContestsForClient_request;
		}
		
		/**
		 * @private
		 */
		public function set getContestsForClient_request_var(request:GetContestsForClient_request):void
		{
			_getContestsForClient_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _getContestsForClient_lastResult:GetContestsForClientResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#getContestsForClient_lastResult
		 */	  
		public function get getContestsForClient_lastResult():GetContestsForClientResponse
		{
			return _getContestsForClient_lastResult;
		}
		/**
		 * @private
		 */
		public function set getContestsForClient_lastResult(lastResult:GetContestsForClientResponse):void
		{
			_getContestsForClient_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addgetContestsForClient()
		 */
		public function addgetContestsForClientEventListener(listener:Function):void
		{
			addEventListener(GetContestsForClientResultEvent.GetContestsForClient_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _getContestsForClient_populate_results(event:ResultEvent):void
		{
			var e:GetContestsForClientResultEvent = new GetContestsForClientResultEvent();
		            e.result = event.result as GetContestsForClientResponse;
		                       e.headers = event.headers;
		             getContestsForClient_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the getContestsForProject operation
          

        /**
         * @see IContestServiceFacadeBeanService#getContestsForProject()
         */
        public function getContestsForProject(getContestsForProject:GetContestsForProject):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.getContestsForProject(getContestsForProject);
            _internal_token.addEventListener("result",_getContestsForProject_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#getContestsForProject_send()
		 */    
        public function getContestsForProject_send():AsyncToken
        {
        	return getContestsForProject(_getContestsForProject_request.getContestsForProject);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _getContestsForProject_request:GetContestsForProject_request;
		/**
		 * @see IContestServiceFacadeBeanService#getContestsForProject_request_var
		 */
		[Bindable]
		public function get getContestsForProject_request_var():GetContestsForProject_request
		{
			return _getContestsForProject_request;
		}
		
		/**
		 * @private
		 */
		public function set getContestsForProject_request_var(request:GetContestsForProject_request):void
		{
			_getContestsForProject_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _getContestsForProject_lastResult:GetContestsForProjectResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#getContestsForProject_lastResult
		 */	  
		public function get getContestsForProject_lastResult():GetContestsForProjectResponse
		{
			return _getContestsForProject_lastResult;
		}
		/**
		 * @private
		 */
		public function set getContestsForProject_lastResult(lastResult:GetContestsForProjectResponse):void
		{
			_getContestsForProject_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addgetContestsForProject()
		 */
		public function addgetContestsForProjectEventListener(listener:Function):void
		{
			addEventListener(GetContestsForProjectResultEvent.GetContestsForProject_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _getContestsForProject_populate_results(event:ResultEvent):void
		{
			var e:GetContestsForProjectResultEvent = new GetContestsForProjectResultEvent();
		            e.result = event.result as GetContestsForProjectResponse;
		                       e.headers = event.headers;
		             getContestsForProject_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the getLatestChanges operation
          

        /**
         * @see IContestServiceFacadeBeanService#getLatestChanges()
         */
        public function getLatestChanges(getLatestChanges:GetLatestChanges):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.getLatestChanges(getLatestChanges);
            _internal_token.addEventListener("result",_getLatestChanges_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#getLatestChanges_send()
		 */    
        public function getLatestChanges_send():AsyncToken
        {
        	return getLatestChanges(_getLatestChanges_request.getLatestChanges);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _getLatestChanges_request:GetLatestChanges_request;
		/**
		 * @see IContestServiceFacadeBeanService#getLatestChanges_request_var
		 */
		[Bindable]
		public function get getLatestChanges_request_var():GetLatestChanges_request
		{
			return _getLatestChanges_request;
		}
		
		/**
		 * @private
		 */
		public function set getLatestChanges_request_var(request:GetLatestChanges_request):void
		{
			_getLatestChanges_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _getLatestChanges_lastResult:GetLatestChangesResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#getLatestChanges_lastResult
		 */	  
		public function get getLatestChanges_lastResult():GetLatestChangesResponse
		{
			return _getLatestChanges_lastResult;
		}
		/**
		 * @private
		 */
		public function set getLatestChanges_lastResult(lastResult:GetLatestChangesResponse):void
		{
			_getLatestChanges_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addgetLatestChanges()
		 */
		public function addgetLatestChangesEventListener(listener:Function):void
		{
			addEventListener(GetLatestChangesResultEvent.GetLatestChanges_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _getLatestChanges_populate_results(event:ResultEvent):void
		{
			var e:GetLatestChangesResultEvent = new GetLatestChangesResultEvent();
		            e.result = event.result as GetLatestChangesResponse;
		                       e.headers = event.headers;
		             getLatestChanges_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the getMimeTypeId operation
          

        /**
         * @see IContestServiceFacadeBeanService#getMimeTypeId()
         */
        public function getMimeTypeId(getMimeTypeId:GetMimeTypeId):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.getMimeTypeId(getMimeTypeId);
            _internal_token.addEventListener("result",_getMimeTypeId_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#getMimeTypeId_send()
		 */    
        public function getMimeTypeId_send():AsyncToken
        {
        	return getMimeTypeId(_getMimeTypeId_request.getMimeTypeId);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _getMimeTypeId_request:GetMimeTypeId_request;
		/**
		 * @see IContestServiceFacadeBeanService#getMimeTypeId_request_var
		 */
		[Bindable]
		public function get getMimeTypeId_request_var():GetMimeTypeId_request
		{
			return _getMimeTypeId_request;
		}
		
		/**
		 * @private
		 */
		public function set getMimeTypeId_request_var(request:GetMimeTypeId_request):void
		{
			_getMimeTypeId_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _getMimeTypeId_lastResult:GetMimeTypeIdResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#getMimeTypeId_lastResult
		 */	  
		public function get getMimeTypeId_lastResult():GetMimeTypeIdResponse
		{
			return _getMimeTypeId_lastResult;
		}
		/**
		 * @private
		 */
		public function set getMimeTypeId_lastResult(lastResult:GetMimeTypeIdResponse):void
		{
			_getMimeTypeId_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addgetMimeTypeId()
		 */
		public function addgetMimeTypeIdEventListener(listener:Function):void
		{
			addEventListener(GetMimeTypeIdResultEvent.GetMimeTypeId_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _getMimeTypeId_populate_results(event:ResultEvent):void
		{
			var e:GetMimeTypeIdResultEvent = new GetMimeTypeIdResultEvent();
		            e.result = event.result as GetMimeTypeIdResponse;
		                       e.headers = event.headers;
		             getMimeTypeId_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the getStatusList operation
          

        /**
         * @see IContestServiceFacadeBeanService#getStatusList()
         */
        public function getStatusList(getStatusList:GetStatusList):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.getStatusList(getStatusList);
            _internal_token.addEventListener("result",_getStatusList_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#getStatusList_send()
		 */    
        public function getStatusList_send():AsyncToken
        {
        	return getStatusList(_getStatusList_request.getStatusList);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _getStatusList_request:GetStatusList_request;
		/**
		 * @see IContestServiceFacadeBeanService#getStatusList_request_var
		 */
		[Bindable]
		public function get getStatusList_request_var():GetStatusList_request
		{
			return _getStatusList_request;
		}
		
		/**
		 * @private
		 */
		public function set getStatusList_request_var(request:GetStatusList_request):void
		{
			_getStatusList_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _getStatusList_lastResult:GetStatusListResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#getStatusList_lastResult
		 */	  
		public function get getStatusList_lastResult():GetStatusListResponse
		{
			return _getStatusList_lastResult;
		}
		/**
		 * @private
		 */
		public function set getStatusList_lastResult(lastResult:GetStatusListResponse):void
		{
			_getStatusList_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addgetStatusList()
		 */
		public function addgetStatusListEventListener(listener:Function):void
		{
			addEventListener(GetStatusListResultEvent.GetStatusList_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _getStatusList_populate_results(event:ResultEvent):void
		{
			var e:GetStatusListResultEvent = new GetStatusListResultEvent();
		            e.result = event.result as GetStatusListResponse;
		                       e.headers = event.headers;
		             getStatusList_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the getSubmissionFileTypes operation
          

        /**
         * @see IContestServiceFacadeBeanService#getSubmissionFileTypes()
         */
        public function getSubmissionFileTypes(getSubmissionFileTypes:GetSubmissionFileTypes):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.getSubmissionFileTypes(getSubmissionFileTypes);
            _internal_token.addEventListener("result",_getSubmissionFileTypes_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#getSubmissionFileTypes_send()
		 */    
        public function getSubmissionFileTypes_send():AsyncToken
        {
        	return getSubmissionFileTypes(_getSubmissionFileTypes_request.getSubmissionFileTypes);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _getSubmissionFileTypes_request:GetSubmissionFileTypes_request;
		/**
		 * @see IContestServiceFacadeBeanService#getSubmissionFileTypes_request_var
		 */
		[Bindable]
		public function get getSubmissionFileTypes_request_var():GetSubmissionFileTypes_request
		{
			return _getSubmissionFileTypes_request;
		}
		
		/**
		 * @private
		 */
		public function set getSubmissionFileTypes_request_var(request:GetSubmissionFileTypes_request):void
		{
			_getSubmissionFileTypes_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _getSubmissionFileTypes_lastResult:GetSubmissionFileTypesResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#getSubmissionFileTypes_lastResult
		 */	  
		public function get getSubmissionFileTypes_lastResult():GetSubmissionFileTypesResponse
		{
			return _getSubmissionFileTypes_lastResult;
		}
		/**
		 * @private
		 */
		public function set getSubmissionFileTypes_lastResult(lastResult:GetSubmissionFileTypesResponse):void
		{
			_getSubmissionFileTypes_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addgetSubmissionFileTypes()
		 */
		public function addgetSubmissionFileTypesEventListener(listener:Function):void
		{
			addEventListener(GetSubmissionFileTypesResultEvent.GetSubmissionFileTypes_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _getSubmissionFileTypes_populate_results(event:ResultEvent):void
		{
			var e:GetSubmissionFileTypesResultEvent = new GetSubmissionFileTypesResultEvent();
		            e.result = event.result as GetSubmissionFileTypesResponse;
		                       e.headers = event.headers;
		             getSubmissionFileTypes_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the markForPurchase operation
          

        /**
         * @see IContestServiceFacadeBeanService#markForPurchase()
         */
        public function markForPurchase(markForPurchase:MarkForPurchase):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.markForPurchase(markForPurchase);
            _internal_token.addEventListener("result",_markForPurchase_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#markForPurchase_send()
		 */    
        public function markForPurchase_send():AsyncToken
        {
        	return markForPurchase(_markForPurchase_request.markForPurchase);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _markForPurchase_request:MarkForPurchase_request;
		/**
		 * @see IContestServiceFacadeBeanService#markForPurchase_request_var
		 */
		[Bindable]
		public function get markForPurchase_request_var():MarkForPurchase_request
		{
			return _markForPurchase_request;
		}
		
		/**
		 * @private
		 */
		public function set markForPurchase_request_var(request:MarkForPurchase_request):void
		{
			_markForPurchase_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _markForPurchase_lastResult:MarkForPurchaseResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#markForPurchase_lastResult
		 */	  
		public function get markForPurchase_lastResult():MarkForPurchaseResponse
		{
			return _markForPurchase_lastResult;
		}
		/**
		 * @private
		 */
		public function set markForPurchase_lastResult(lastResult:MarkForPurchaseResponse):void
		{
			_markForPurchase_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addmarkForPurchase()
		 */
		public function addmarkForPurchaseEventListener(listener:Function):void
		{
			addEventListener(MarkForPurchaseResultEvent.MarkForPurchase_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _markForPurchase_populate_results(event:ResultEvent):void
		{
			var e:MarkForPurchaseResultEvent = new MarkForPurchaseResultEvent();
		            e.result = event.result as MarkForPurchaseResponse;
		                       e.headers = event.headers;
		             markForPurchase_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the processMissingPayments operation
          

        /**
         * @see IContestServiceFacadeBeanService#processMissingPayments()
         */
        public function processMissingPayments(processMissingPayments:ProcessMissingPayments):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.processMissingPayments(processMissingPayments);
            _internal_token.addEventListener("result",_processMissingPayments_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#processMissingPayments_send()
		 */    
        public function processMissingPayments_send():AsyncToken
        {
        	return processMissingPayments(_processMissingPayments_request.processMissingPayments);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _processMissingPayments_request:ProcessMissingPayments_request;
		/**
		 * @see IContestServiceFacadeBeanService#processMissingPayments_request_var
		 */
		[Bindable]
		public function get processMissingPayments_request_var():ProcessMissingPayments_request
		{
			return _processMissingPayments_request;
		}
		
		/**
		 * @private
		 */
		public function set processMissingPayments_request_var(request:ProcessMissingPayments_request):void
		{
			_processMissingPayments_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _processMissingPayments_lastResult:ProcessMissingPaymentsResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#processMissingPayments_lastResult
		 */	  
		public function get processMissingPayments_lastResult():ProcessMissingPaymentsResponse
		{
			return _processMissingPayments_lastResult;
		}
		/**
		 * @private
		 */
		public function set processMissingPayments_lastResult(lastResult:ProcessMissingPaymentsResponse):void
		{
			_processMissingPayments_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addprocessMissingPayments()
		 */
		public function addprocessMissingPaymentsEventListener(listener:Function):void
		{
			addEventListener(ProcessMissingPaymentsResultEvent.ProcessMissingPayments_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _processMissingPayments_populate_results(event:ResultEvent):void
		{
			var e:ProcessMissingPaymentsResultEvent = new ProcessMissingPaymentsResultEvent();
		            e.result = event.result as ProcessMissingPaymentsResponse;
		                       e.headers = event.headers;
		             processMissingPayments_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the purchaseSubmission operation
          

        /**
         * @see IContestServiceFacadeBeanService#purchaseSubmission()
         */
        public function purchaseSubmission(purchaseSubmission:PurchaseSubmission):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.purchaseSubmission(purchaseSubmission);
            _internal_token.addEventListener("result",_purchaseSubmission_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#purchaseSubmission_send()
		 */    
        public function purchaseSubmission_send():AsyncToken
        {
        	return purchaseSubmission(_purchaseSubmission_request.purchaseSubmission);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _purchaseSubmission_request:PurchaseSubmission_request;
		/**
		 * @see IContestServiceFacadeBeanService#purchaseSubmission_request_var
		 */
		[Bindable]
		public function get purchaseSubmission_request_var():PurchaseSubmission_request
		{
			return _purchaseSubmission_request;
		}
		
		/**
		 * @private
		 */
		public function set purchaseSubmission_request_var(request:PurchaseSubmission_request):void
		{
			_purchaseSubmission_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _purchaseSubmission_lastResult:PurchaseSubmissionResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#purchaseSubmission_lastResult
		 */	  
		public function get purchaseSubmission_lastResult():PurchaseSubmissionResponse
		{
			return _purchaseSubmission_lastResult;
		}
		/**
		 * @private
		 */
		public function set purchaseSubmission_lastResult(lastResult:PurchaseSubmissionResponse):void
		{
			_purchaseSubmission_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addpurchaseSubmission()
		 */
		public function addpurchaseSubmissionEventListener(listener:Function):void
		{
			addEventListener(PurchaseSubmissionResultEvent.PurchaseSubmission_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _purchaseSubmission_populate_results(event:ResultEvent):void
		{
			var e:PurchaseSubmissionResultEvent = new PurchaseSubmissionResultEvent();
		            e.result = event.result as PurchaseSubmissionResponse;
		                       e.headers = event.headers;
		             purchaseSubmission_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the removeContestPayment operation
          

        /**
         * @see IContestServiceFacadeBeanService#removeContestPayment()
         */
        public function removeContestPayment(removeContestPayment:RemoveContestPayment):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.removeContestPayment(removeContestPayment);
            _internal_token.addEventListener("result",_removeContestPayment_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#removeContestPayment_send()
		 */    
        public function removeContestPayment_send():AsyncToken
        {
        	return removeContestPayment(_removeContestPayment_request.removeContestPayment);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _removeContestPayment_request:RemoveContestPayment_request;
		/**
		 * @see IContestServiceFacadeBeanService#removeContestPayment_request_var
		 */
		[Bindable]
		public function get removeContestPayment_request_var():RemoveContestPayment_request
		{
			return _removeContestPayment_request;
		}
		
		/**
		 * @private
		 */
		public function set removeContestPayment_request_var(request:RemoveContestPayment_request):void
		{
			_removeContestPayment_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _removeContestPayment_lastResult:RemoveContestPaymentResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#removeContestPayment_lastResult
		 */	  
		public function get removeContestPayment_lastResult():RemoveContestPaymentResponse
		{
			return _removeContestPayment_lastResult;
		}
		/**
		 * @private
		 */
		public function set removeContestPayment_lastResult(lastResult:RemoveContestPaymentResponse):void
		{
			_removeContestPayment_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addremoveContestPayment()
		 */
		public function addremoveContestPaymentEventListener(listener:Function):void
		{
			addEventListener(RemoveContestPaymentResultEvent.RemoveContestPayment_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _removeContestPayment_populate_results(event:ResultEvent):void
		{
			var e:RemoveContestPaymentResultEvent = new RemoveContestPaymentResultEvent();
		            e.result = event.result as RemoveContestPaymentResponse;
		                       e.headers = event.headers;
		             removeContestPayment_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the removeDocument operation
          

        /**
         * @see IContestServiceFacadeBeanService#removeDocument()
         */
        public function removeDocument(removeDocument:RemoveDocument):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.removeDocument(removeDocument);
            _internal_token.addEventListener("result",_removeDocument_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#removeDocument_send()
		 */    
        public function removeDocument_send():AsyncToken
        {
        	return removeDocument(_removeDocument_request.removeDocument);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _removeDocument_request:RemoveDocument_request;
		/**
		 * @see IContestServiceFacadeBeanService#removeDocument_request_var
		 */
		[Bindable]
		public function get removeDocument_request_var():RemoveDocument_request
		{
			return _removeDocument_request;
		}
		
		/**
		 * @private
		 */
		public function set removeDocument_request_var(request:RemoveDocument_request):void
		{
			_removeDocument_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _removeDocument_lastResult:RemoveDocumentResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#removeDocument_lastResult
		 */	  
		public function get removeDocument_lastResult():RemoveDocumentResponse
		{
			return _removeDocument_lastResult;
		}
		/**
		 * @private
		 */
		public function set removeDocument_lastResult(lastResult:RemoveDocumentResponse):void
		{
			_removeDocument_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addremoveDocument()
		 */
		public function addremoveDocumentEventListener(listener:Function):void
		{
			addEventListener(RemoveDocumentResultEvent.RemoveDocument_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _removeDocument_populate_results(event:ResultEvent):void
		{
			var e:RemoveDocumentResultEvent = new RemoveDocumentResultEvent();
		            e.result = event.result as RemoveDocumentResponse;
		                       e.headers = event.headers;
		             removeDocument_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the removeDocumentFromContest operation
          

        /**
         * @see IContestServiceFacadeBeanService#removeDocumentFromContest()
         */
        public function removeDocumentFromContest(removeDocumentFromContest:RemoveDocumentFromContest):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.removeDocumentFromContest(removeDocumentFromContest);
            _internal_token.addEventListener("result",_removeDocumentFromContest_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#removeDocumentFromContest_send()
		 */    
        public function removeDocumentFromContest_send():AsyncToken
        {
        	return removeDocumentFromContest(_removeDocumentFromContest_request.removeDocumentFromContest);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _removeDocumentFromContest_request:RemoveDocumentFromContest_request;
		/**
		 * @see IContestServiceFacadeBeanService#removeDocumentFromContest_request_var
		 */
		[Bindable]
		public function get removeDocumentFromContest_request_var():RemoveDocumentFromContest_request
		{
			return _removeDocumentFromContest_request;
		}
		
		/**
		 * @private
		 */
		public function set removeDocumentFromContest_request_var(request:RemoveDocumentFromContest_request):void
		{
			_removeDocumentFromContest_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _removeDocumentFromContest_lastResult:RemoveDocumentFromContestResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#removeDocumentFromContest_lastResult
		 */	  
		public function get removeDocumentFromContest_lastResult():RemoveDocumentFromContestResponse
		{
			return _removeDocumentFromContest_lastResult;
		}
		/**
		 * @private
		 */
		public function set removeDocumentFromContest_lastResult(lastResult:RemoveDocumentFromContestResponse):void
		{
			_removeDocumentFromContest_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addremoveDocumentFromContest()
		 */
		public function addremoveDocumentFromContestEventListener(listener:Function):void
		{
			addEventListener(RemoveDocumentFromContestResultEvent.RemoveDocumentFromContest_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _removeDocumentFromContest_populate_results(event:ResultEvent):void
		{
			var e:RemoveDocumentFromContestResultEvent = new RemoveDocumentFromContestResultEvent();
		            e.result = event.result as RemoveDocumentFromContestResponse;
		                       e.headers = event.headers;
		             removeDocumentFromContest_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the removeSubmission operation
          

        /**
         * @see IContestServiceFacadeBeanService#removeSubmission()
         */
        public function removeSubmission(removeSubmission:RemoveSubmission):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.removeSubmission(removeSubmission);
            _internal_token.addEventListener("result",_removeSubmission_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#removeSubmission_send()
		 */    
        public function removeSubmission_send():AsyncToken
        {
        	return removeSubmission(_removeSubmission_request.removeSubmission);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _removeSubmission_request:RemoveSubmission_request;
		/**
		 * @see IContestServiceFacadeBeanService#removeSubmission_request_var
		 */
		[Bindable]
		public function get removeSubmission_request_var():RemoveSubmission_request
		{
			return _removeSubmission_request;
		}
		
		/**
		 * @private
		 */
		public function set removeSubmission_request_var(request:RemoveSubmission_request):void
		{
			_removeSubmission_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _removeSubmission_lastResult:RemoveSubmissionResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#removeSubmission_lastResult
		 */	  
		public function get removeSubmission_lastResult():RemoveSubmissionResponse
		{
			return _removeSubmission_lastResult;
		}
		/**
		 * @private
		 */
		public function set removeSubmission_lastResult(lastResult:RemoveSubmissionResponse):void
		{
			_removeSubmission_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addremoveSubmission()
		 */
		public function addremoveSubmissionEventListener(listener:Function):void
		{
			addEventListener(RemoveSubmissionResultEvent.RemoveSubmission_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _removeSubmission_populate_results(event:ResultEvent):void
		{
			var e:RemoveSubmissionResultEvent = new RemoveSubmissionResultEvent();
		            e.result = event.result as RemoveSubmissionResponse;
		                       e.headers = event.headers;
		             removeSubmission_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the retrieveAllSubmissionsByMember operation
          

        /**
         * @see IContestServiceFacadeBeanService#retrieveAllSubmissionsByMember()
         */
        public function retrieveAllSubmissionsByMember(retrieveAllSubmissionsByMember:RetrieveAllSubmissionsByMember):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.retrieveAllSubmissionsByMember(retrieveAllSubmissionsByMember);
            _internal_token.addEventListener("result",_retrieveAllSubmissionsByMember_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#retrieveAllSubmissionsByMember_send()
		 */    
        public function retrieveAllSubmissionsByMember_send():AsyncToken
        {
        	return retrieveAllSubmissionsByMember(_retrieveAllSubmissionsByMember_request.retrieveAllSubmissionsByMember);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _retrieveAllSubmissionsByMember_request:RetrieveAllSubmissionsByMember_request;
		/**
		 * @see IContestServiceFacadeBeanService#retrieveAllSubmissionsByMember_request_var
		 */
		[Bindable]
		public function get retrieveAllSubmissionsByMember_request_var():RetrieveAllSubmissionsByMember_request
		{
			return _retrieveAllSubmissionsByMember_request;
		}
		
		/**
		 * @private
		 */
		public function set retrieveAllSubmissionsByMember_request_var(request:RetrieveAllSubmissionsByMember_request):void
		{
			_retrieveAllSubmissionsByMember_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _retrieveAllSubmissionsByMember_lastResult:RetrieveAllSubmissionsByMemberResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#retrieveAllSubmissionsByMember_lastResult
		 */	  
		public function get retrieveAllSubmissionsByMember_lastResult():RetrieveAllSubmissionsByMemberResponse
		{
			return _retrieveAllSubmissionsByMember_lastResult;
		}
		/**
		 * @private
		 */
		public function set retrieveAllSubmissionsByMember_lastResult(lastResult:RetrieveAllSubmissionsByMemberResponse):void
		{
			_retrieveAllSubmissionsByMember_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addretrieveAllSubmissionsByMember()
		 */
		public function addretrieveAllSubmissionsByMemberEventListener(listener:Function):void
		{
			addEventListener(RetrieveAllSubmissionsByMemberResultEvent.RetrieveAllSubmissionsByMember_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _retrieveAllSubmissionsByMember_populate_results(event:ResultEvent):void
		{
			var e:RetrieveAllSubmissionsByMemberResultEvent = new RetrieveAllSubmissionsByMemberResultEvent();
		            e.result = event.result as RetrieveAllSubmissionsByMemberResponse;
		                       e.headers = event.headers;
		             retrieveAllSubmissionsByMember_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the retrieveSubmission operation
          

        /**
         * @see IContestServiceFacadeBeanService#retrieveSubmission()
         */
        public function retrieveSubmission(retrieveSubmission:RetrieveSubmission):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.retrieveSubmission(retrieveSubmission);
            _internal_token.addEventListener("result",_retrieveSubmission_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#retrieveSubmission_send()
		 */    
        public function retrieveSubmission_send():AsyncToken
        {
        	return retrieveSubmission(_retrieveSubmission_request.retrieveSubmission);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _retrieveSubmission_request:RetrieveSubmission_request;
		/**
		 * @see IContestServiceFacadeBeanService#retrieveSubmission_request_var
		 */
		[Bindable]
		public function get retrieveSubmission_request_var():RetrieveSubmission_request
		{
			return _retrieveSubmission_request;
		}
		
		/**
		 * @private
		 */
		public function set retrieveSubmission_request_var(request:RetrieveSubmission_request):void
		{
			_retrieveSubmission_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _retrieveSubmission_lastResult:RetrieveSubmissionResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#retrieveSubmission_lastResult
		 */	  
		public function get retrieveSubmission_lastResult():RetrieveSubmissionResponse
		{
			return _retrieveSubmission_lastResult;
		}
		/**
		 * @private
		 */
		public function set retrieveSubmission_lastResult(lastResult:RetrieveSubmissionResponse):void
		{
			_retrieveSubmission_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addretrieveSubmission()
		 */
		public function addretrieveSubmissionEventListener(listener:Function):void
		{
			addEventListener(RetrieveSubmissionResultEvent.RetrieveSubmission_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _retrieveSubmission_populate_results(event:ResultEvent):void
		{
			var e:RetrieveSubmissionResultEvent = new RetrieveSubmissionResultEvent();
		            e.result = event.result as RetrieveSubmissionResponse;
		                       e.headers = event.headers;
		             retrieveSubmission_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the retrieveSubmissionsForContest operation
          

        /**
         * @see IContestServiceFacadeBeanService#retrieveSubmissionsForContest()
         */
        public function retrieveSubmissionsForContest(retrieveSubmissionsForContest:RetrieveSubmissionsForContest):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.retrieveSubmissionsForContest(retrieveSubmissionsForContest);
            _internal_token.addEventListener("result",_retrieveSubmissionsForContest_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#retrieveSubmissionsForContest_send()
		 */    
        public function retrieveSubmissionsForContest_send():AsyncToken
        {
        	return retrieveSubmissionsForContest(_retrieveSubmissionsForContest_request.retrieveSubmissionsForContest);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _retrieveSubmissionsForContest_request:RetrieveSubmissionsForContest_request;
		/**
		 * @see IContestServiceFacadeBeanService#retrieveSubmissionsForContest_request_var
		 */
		[Bindable]
		public function get retrieveSubmissionsForContest_request_var():RetrieveSubmissionsForContest_request
		{
			return _retrieveSubmissionsForContest_request;
		}
		
		/**
		 * @private
		 */
		public function set retrieveSubmissionsForContest_request_var(request:RetrieveSubmissionsForContest_request):void
		{
			_retrieveSubmissionsForContest_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _retrieveSubmissionsForContest_lastResult:RetrieveSubmissionsForContestResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#retrieveSubmissionsForContest_lastResult
		 */	  
		public function get retrieveSubmissionsForContest_lastResult():RetrieveSubmissionsForContestResponse
		{
			return _retrieveSubmissionsForContest_lastResult;
		}
		/**
		 * @private
		 */
		public function set retrieveSubmissionsForContest_lastResult(lastResult:RetrieveSubmissionsForContestResponse):void
		{
			_retrieveSubmissionsForContest_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addretrieveSubmissionsForContest()
		 */
		public function addretrieveSubmissionsForContestEventListener(listener:Function):void
		{
			addEventListener(RetrieveSubmissionsForContestResultEvent.RetrieveSubmissionsForContest_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _retrieveSubmissionsForContest_populate_results(event:ResultEvent):void
		{
			var e:RetrieveSubmissionsForContestResultEvent = new RetrieveSubmissionsForContestResultEvent();
		            e.result = event.result as RetrieveSubmissionsForContestResponse;
		                       e.headers = event.headers;
		             retrieveSubmissionsForContest_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the searchContests operation
          

        /**
         * @see IContestServiceFacadeBeanService#searchContests()
         */
        public function searchContests(searchContests:SearchContests):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.searchContests(searchContests);
            _internal_token.addEventListener("result",_searchContests_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#searchContests_send()
		 */    
        public function searchContests_send():AsyncToken
        {
        	return searchContests(_searchContests_request.searchContests);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _searchContests_request:SearchContests_request;
		/**
		 * @see IContestServiceFacadeBeanService#searchContests_request_var
		 */
		[Bindable]
		public function get searchContests_request_var():SearchContests_request
		{
			return _searchContests_request;
		}
		
		/**
		 * @private
		 */
		public function set searchContests_request_var(request:SearchContests_request):void
		{
			_searchContests_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _searchContests_lastResult:SearchContestsResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#searchContests_lastResult
		 */	  
		public function get searchContests_lastResult():SearchContestsResponse
		{
			return _searchContests_lastResult;
		}
		/**
		 * @private
		 */
		public function set searchContests_lastResult(lastResult:SearchContestsResponse):void
		{
			_searchContests_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addsearchContests()
		 */
		public function addsearchContestsEventListener(listener:Function):void
		{
			addEventListener(SearchContestsResultEvent.SearchContests_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _searchContests_populate_results(event:ResultEvent):void
		{
			var e:SearchContestsResultEvent = new SearchContestsResultEvent();
		            e.result = event.result as SearchContestsResponse;
		                       e.headers = event.headers;
		             searchContests_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the setSubmissionPlacement operation
          

        /**
         * @see IContestServiceFacadeBeanService#setSubmissionPlacement()
         */
        public function setSubmissionPlacement(setSubmissionPlacement:SetSubmissionPlacement):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.setSubmissionPlacement(setSubmissionPlacement);
            _internal_token.addEventListener("result",_setSubmissionPlacement_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#setSubmissionPlacement_send()
		 */    
        public function setSubmissionPlacement_send():AsyncToken
        {
        	return setSubmissionPlacement(_setSubmissionPlacement_request.setSubmissionPlacement);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _setSubmissionPlacement_request:SetSubmissionPlacement_request;
		/**
		 * @see IContestServiceFacadeBeanService#setSubmissionPlacement_request_var
		 */
		[Bindable]
		public function get setSubmissionPlacement_request_var():SetSubmissionPlacement_request
		{
			return _setSubmissionPlacement_request;
		}
		
		/**
		 * @private
		 */
		public function set setSubmissionPlacement_request_var(request:SetSubmissionPlacement_request):void
		{
			_setSubmissionPlacement_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _setSubmissionPlacement_lastResult:SetSubmissionPlacementResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#setSubmissionPlacement_lastResult
		 */	  
		public function get setSubmissionPlacement_lastResult():SetSubmissionPlacementResponse
		{
			return _setSubmissionPlacement_lastResult;
		}
		/**
		 * @private
		 */
		public function set setSubmissionPlacement_lastResult(lastResult:SetSubmissionPlacementResponse):void
		{
			_setSubmissionPlacement_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addsetSubmissionPlacement()
		 */
		public function addsetSubmissionPlacementEventListener(listener:Function):void
		{
			addEventListener(SetSubmissionPlacementResultEvent.SetSubmissionPlacement_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _setSubmissionPlacement_populate_results(event:ResultEvent):void
		{
			var e:SetSubmissionPlacementResultEvent = new SetSubmissionPlacementResultEvent();
		            e.result = event.result as SetSubmissionPlacementResponse;
		                       e.headers = event.headers;
		             setSubmissionPlacement_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the setSubmissionPrize operation
          

        /**
         * @see IContestServiceFacadeBeanService#setSubmissionPrize()
         */
        public function setSubmissionPrize(setSubmissionPrize:SetSubmissionPrize):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.setSubmissionPrize(setSubmissionPrize);
            _internal_token.addEventListener("result",_setSubmissionPrize_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#setSubmissionPrize_send()
		 */    
        public function setSubmissionPrize_send():AsyncToken
        {
        	return setSubmissionPrize(_setSubmissionPrize_request.setSubmissionPrize);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _setSubmissionPrize_request:SetSubmissionPrize_request;
		/**
		 * @see IContestServiceFacadeBeanService#setSubmissionPrize_request_var
		 */
		[Bindable]
		public function get setSubmissionPrize_request_var():SetSubmissionPrize_request
		{
			return _setSubmissionPrize_request;
		}
		
		/**
		 * @private
		 */
		public function set setSubmissionPrize_request_var(request:SetSubmissionPrize_request):void
		{
			_setSubmissionPrize_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _setSubmissionPrize_lastResult:SetSubmissionPrizeResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#setSubmissionPrize_lastResult
		 */	  
		public function get setSubmissionPrize_lastResult():SetSubmissionPrizeResponse
		{
			return _setSubmissionPrize_lastResult;
		}
		/**
		 * @private
		 */
		public function set setSubmissionPrize_lastResult(lastResult:SetSubmissionPrizeResponse):void
		{
			_setSubmissionPrize_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addsetSubmissionPrize()
		 */
		public function addsetSubmissionPrizeEventListener(listener:Function):void
		{
			addEventListener(SetSubmissionPrizeResultEvent.SetSubmissionPrize_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _setSubmissionPrize_populate_results(event:ResultEvent):void
		{
			var e:SetSubmissionPrizeResultEvent = new SetSubmissionPrizeResultEvent();
		            e.result = event.result as SetSubmissionPrizeResponse;
		                       e.headers = event.headers;
		             setSubmissionPrize_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the updateContest operation
          

        /**
         * @see IContestServiceFacadeBeanService#updateContest()
         */
        public function updateContest(updateContest:UpdateContest):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.updateContest(updateContest);
            _internal_token.addEventListener("result",_updateContest_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#updateContest_send()
		 */    
        public function updateContest_send():AsyncToken
        {
        	return updateContest(_updateContest_request.updateContest);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _updateContest_request:UpdateContest_request;
		/**
		 * @see IContestServiceFacadeBeanService#updateContest_request_var
		 */
		[Bindable]
		public function get updateContest_request_var():UpdateContest_request
		{
			return _updateContest_request;
		}
		
		/**
		 * @private
		 */
		public function set updateContest_request_var(request:UpdateContest_request):void
		{
			_updateContest_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _updateContest_lastResult:UpdateContestResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#updateContest_lastResult
		 */	  
		public function get updateContest_lastResult():UpdateContestResponse
		{
			return _updateContest_lastResult;
		}
		/**
		 * @private
		 */
		public function set updateContest_lastResult(lastResult:UpdateContestResponse):void
		{
			_updateContest_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addupdateContest()
		 */
		public function addupdateContestEventListener(listener:Function):void
		{
			addEventListener(UpdateContestResultEvent.UpdateContest_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _updateContest_populate_results(event:ResultEvent):void
		{
			var e:UpdateContestResultEvent = new UpdateContestResultEvent();
		            e.result = event.result as UpdateContestResponse;
		                       e.headers = event.headers;
		             updateContest_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the updateContestStatus operation
          

        /**
         * @see IContestServiceFacadeBeanService#updateContestStatus()
         */
        public function updateContestStatus(updateContestStatus:UpdateContestStatus):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.updateContestStatus(updateContestStatus);
            _internal_token.addEventListener("result",_updateContestStatus_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#updateContestStatus_send()
		 */    
        public function updateContestStatus_send():AsyncToken
        {
        	return updateContestStatus(_updateContestStatus_request.updateContestStatus);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _updateContestStatus_request:UpdateContestStatus_request;
		/**
		 * @see IContestServiceFacadeBeanService#updateContestStatus_request_var
		 */
		[Bindable]
		public function get updateContestStatus_request_var():UpdateContestStatus_request
		{
			return _updateContestStatus_request;
		}
		
		/**
		 * @private
		 */
		public function set updateContestStatus_request_var(request:UpdateContestStatus_request):void
		{
			_updateContestStatus_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _updateContestStatus_lastResult:UpdateContestStatusResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#updateContestStatus_lastResult
		 */	  
		public function get updateContestStatus_lastResult():UpdateContestStatusResponse
		{
			return _updateContestStatus_lastResult;
		}
		/**
		 * @private
		 */
		public function set updateContestStatus_lastResult(lastResult:UpdateContestStatusResponse):void
		{
			_updateContestStatus_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addupdateContestStatus()
		 */
		public function addupdateContestStatusEventListener(listener:Function):void
		{
			addEventListener(UpdateContestStatusResultEvent.UpdateContestStatus_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _updateContestStatus_populate_results(event:ResultEvent):void
		{
			var e:UpdateContestStatusResultEvent = new UpdateContestStatusResultEvent();
		            e.result = event.result as UpdateContestStatusResponse;
		                       e.headers = event.headers;
		             updateContestStatus_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the updateSubmission operation
          

        /**
         * @see IContestServiceFacadeBeanService#updateSubmission()
         */
        public function updateSubmission(updateSubmission:UpdateSubmission):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.updateSubmission(updateSubmission);
            _internal_token.addEventListener("result",_updateSubmission_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#updateSubmission_send()
		 */    
        public function updateSubmission_send():AsyncToken
        {
        	return updateSubmission(_updateSubmission_request.updateSubmission);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _updateSubmission_request:UpdateSubmission_request;
		/**
		 * @see IContestServiceFacadeBeanService#updateSubmission_request_var
		 */
		[Bindable]
		public function get updateSubmission_request_var():UpdateSubmission_request
		{
			return _updateSubmission_request;
		}
		
		/**
		 * @private
		 */
		public function set updateSubmission_request_var(request:UpdateSubmission_request):void
		{
			_updateSubmission_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _updateSubmission_lastResult:UpdateSubmissionResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#updateSubmission_lastResult
		 */	  
		public function get updateSubmission_lastResult():UpdateSubmissionResponse
		{
			return _updateSubmission_lastResult;
		}
		/**
		 * @private
		 */
		public function set updateSubmission_lastResult(lastResult:UpdateSubmissionResponse):void
		{
			_updateSubmission_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#addupdateSubmission()
		 */
		public function addupdateSubmissionEventListener(listener:Function):void
		{
			addEventListener(UpdateSubmissionResultEvent.UpdateSubmission_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _updateSubmission_populate_results(event:ResultEvent):void
		{
			var e:UpdateSubmissionResultEvent = new UpdateSubmissionResultEvent();
		            e.result = event.result as UpdateSubmissionResponse;
		                       e.headers = event.headers;
		             updateSubmission_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the uploadDocument operation
          

        /**
         * @see IContestServiceFacadeBeanService#uploadDocument()
         */
        public function uploadDocument(uploadDocument:UploadDocument):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.uploadDocument(uploadDocument);
            _internal_token.addEventListener("result",_uploadDocument_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#uploadDocument_send()
		 */    
        public function uploadDocument_send():AsyncToken
        {
        	return uploadDocument(_uploadDocument_request.uploadDocument);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _uploadDocument_request:UploadDocument_request;
		/**
		 * @see IContestServiceFacadeBeanService#uploadDocument_request_var
		 */
		[Bindable]
		public function get uploadDocument_request_var():UploadDocument_request
		{
			return _uploadDocument_request;
		}
		
		/**
		 * @private
		 */
		public function set uploadDocument_request_var(request:UploadDocument_request):void
		{
			_uploadDocument_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _uploadDocument_lastResult:UploadDocumentResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#uploadDocument_lastResult
		 */	  
		public function get uploadDocument_lastResult():UploadDocumentResponse
		{
			return _uploadDocument_lastResult;
		}
		/**
		 * @private
		 */
		public function set uploadDocument_lastResult(lastResult:UploadDocumentResponse):void
		{
			_uploadDocument_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#adduploadDocument()
		 */
		public function adduploadDocumentEventListener(listener:Function):void
		{
			addEventListener(UploadDocumentResultEvent.UploadDocument_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _uploadDocument_populate_results(event:ResultEvent):void
		{
			var e:UploadDocumentResultEvent = new UploadDocumentResultEvent();
		            e.result = event.result as UploadDocumentResponse;
		                       e.headers = event.headers;
		             uploadDocument_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the uploadDocumentForContest operation
          

        /**
         * @see IContestServiceFacadeBeanService#uploadDocumentForContest()
         */
        public function uploadDocumentForContest(uploadDocumentForContest:UploadDocumentForContest):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.uploadDocumentForContest(uploadDocumentForContest);
            _internal_token.addEventListener("result",_uploadDocumentForContest_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IContestServiceFacadeBeanService#uploadDocumentForContest_send()
		 */    
        public function uploadDocumentForContest_send():AsyncToken
        {
        	return uploadDocumentForContest(_uploadDocumentForContest_request.uploadDocumentForContest);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _uploadDocumentForContest_request:UploadDocumentForContest_request;
		/**
		 * @see IContestServiceFacadeBeanService#uploadDocumentForContest_request_var
		 */
		[Bindable]
		public function get uploadDocumentForContest_request_var():UploadDocumentForContest_request
		{
			return _uploadDocumentForContest_request;
		}
		
		/**
		 * @private
		 */
		public function set uploadDocumentForContest_request_var(request:UploadDocumentForContest_request):void
		{
			_uploadDocumentForContest_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _uploadDocumentForContest_lastResult:UploadDocumentForContestResponse;
		[Bindable]
		/**
		 * @see IContestServiceFacadeBeanService#uploadDocumentForContest_lastResult
		 */	  
		public function get uploadDocumentForContest_lastResult():UploadDocumentForContestResponse
		{
			return _uploadDocumentForContest_lastResult;
		}
		/**
		 * @private
		 */
		public function set uploadDocumentForContest_lastResult(lastResult:UploadDocumentForContestResponse):void
		{
			_uploadDocumentForContest_lastResult = lastResult;
		}
		
		/**
		 * @see IContestServiceFacadeBeanService#adduploadDocumentForContest()
		 */
		public function adduploadDocumentForContestEventListener(listener:Function):void
		{
			addEventListener(UploadDocumentForContestResultEvent.UploadDocumentForContest_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _uploadDocumentForContest_populate_results(event:ResultEvent):void
		{
			var e:UploadDocumentForContestResultEvent = new UploadDocumentForContestResultEvent();
		            e.result = event.result as UploadDocumentForContestResponse;
		                       e.headers = event.headers;
		             uploadDocumentForContest_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//service-wide functions
		/**
		 * @see IContestServiceFacadeBeanService#getWebService()
		 */
		public function getWebService():BaseContestServiceFacadeBeanService
		{
			return _baseService;
		}
		
		/**
		 * Set the event listener for the fault event which can be triggered by each of the operations defined by the facade
		 */
		public function addContestServiceFacadeBeanServiceFaultEventListener(listener:Function):void
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
