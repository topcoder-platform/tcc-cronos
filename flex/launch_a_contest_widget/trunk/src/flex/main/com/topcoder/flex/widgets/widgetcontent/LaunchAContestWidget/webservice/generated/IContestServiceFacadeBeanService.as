
/**
 * Service.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated{
	import mx.rpc.AsyncToken;
	import flash.utils.ByteArray;
	import mx.rpc.soap.types.*;
               
    public interface IContestServiceFacadeBeanService
    {
    	//Stub functions for the addChangeHistory operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param addChangeHistory
    	 * @param arg0
    	 * @return An AsyncToken
    	 */
    	function addChangeHistory(addChangeHistory:AddChangeHistory):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function addChangeHistory_send():AsyncToken;
        
        /**
         * The addChangeHistory operation lastResult property
         */
        function get addChangeHistory_lastResult():AddChangeHistoryResponse;
		/**
		 * @private
		 */
        function set addChangeHistory_lastResult(lastResult:AddChangeHistoryResponse):void;
       /**
        * Add a listener for the addChangeHistory operation successful result event
        * @param The listener function
        */
       function addaddChangeHistoryEventListener(listener:Function):void;
       
       
        /**
         * The addChangeHistory operation request wrapper
         */
        function get addChangeHistory_request_var():AddChangeHistory_request;
        
        /**
         * @private
         */
        function set addChangeHistory_request_var(request:AddChangeHistory_request):void;
                   
    	//Stub functions for the addDocumentToContest operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param arg0
    	 * @param arg1
    	 * @param addDocumentToContest
    	 * @return An AsyncToken
    	 */
    	function addDocumentToContest(addDocumentToContest:AddDocumentToContest):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function addDocumentToContest_send():AsyncToken;
        
        /**
         * The addDocumentToContest operation lastResult property
         */
        function get addDocumentToContest_lastResult():AddDocumentToContestResponse;
		/**
		 * @private
		 */
        function set addDocumentToContest_lastResult(lastResult:AddDocumentToContestResponse):void;
       /**
        * Add a listener for the addDocumentToContest operation successful result event
        * @param The listener function
        */
       function addaddDocumentToContestEventListener(listener:Function):void;
       
       
        /**
         * The addDocumentToContest operation request wrapper
         */
        function get addDocumentToContest_request_var():AddDocumentToContest_request;
        
        /**
         * @private
         */
        function set addDocumentToContest_request_var(request:AddDocumentToContest_request):void;
                   
    	//Stub functions for the createContest operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param arg0
    	 * @param arg1
    	 * @param createContest
    	 * @return An AsyncToken
    	 */
    	function createContest(createContest:CreateContest):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function createContest_send():AsyncToken;
        
        /**
         * The createContest operation lastResult property
         */
        function get createContest_lastResult():CreateContestResponse;
		/**
		 * @private
		 */
        function set createContest_lastResult(lastResult:CreateContestResponse):void;
       /**
        * Add a listener for the createContest operation successful result event
        * @param The listener function
        */
       function addcreateContestEventListener(listener:Function):void;
       
       
        /**
         * The createContest operation request wrapper
         */
        function get createContest_request_var():CreateContest_request;
        
        /**
         * @private
         */
        function set createContest_request_var(request:CreateContest_request):void;
                   
    	//Stub functions for the createContestPayment operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param arg0
    	 * @param arg1
    	 * @param createContestPayment
    	 * @return An AsyncToken
    	 */
    	function createContestPayment(createContestPayment:CreateContestPayment):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function createContestPayment_send():AsyncToken;
        
        /**
         * The createContestPayment operation lastResult property
         */
        function get createContestPayment_lastResult():CreateContestPaymentResponse;
		/**
		 * @private
		 */
        function set createContestPayment_lastResult(lastResult:CreateContestPaymentResponse):void;
       /**
        * Add a listener for the createContestPayment operation successful result event
        * @param The listener function
        */
       function addcreateContestPaymentEventListener(listener:Function):void;
       
       
        /**
         * The createContestPayment operation request wrapper
         */
        function get createContestPayment_request_var():CreateContestPayment_request;
        
        /**
         * @private
         */
        function set createContestPayment_request_var(request:CreateContestPayment_request):void;
                   
    	//Stub functions for the deleteContest operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param deleteContest
    	 * @param arg0
    	 * @return An AsyncToken
    	 */
    	function deleteContest(deleteContest:DeleteContest):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function deleteContest_send():AsyncToken;
        
        /**
         * The deleteContest operation lastResult property
         */
        function get deleteContest_lastResult():DeleteContestResponse;
		/**
		 * @private
		 */
        function set deleteContest_lastResult(lastResult:DeleteContestResponse):void;
       /**
        * Add a listener for the deleteContest operation successful result event
        * @param The listener function
        */
       function adddeleteContestEventListener(listener:Function):void;
       
       
        /**
         * The deleteContest operation request wrapper
         */
        function get deleteContest_request_var():DeleteContest_request;
        
        /**
         * @private
         */
        function set deleteContest_request_var(request:DeleteContest_request):void;
                   
    	//Stub functions for the editContestPayment operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param arg0
    	 * @param editContestPayment
    	 * @return An AsyncToken
    	 */
    	function editContestPayment(editContestPayment:EditContestPayment):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function editContestPayment_send():AsyncToken;
        
        /**
         * The editContestPayment operation lastResult property
         */
        function get editContestPayment_lastResult():EditContestPaymentResponse;
		/**
		 * @private
		 */
        function set editContestPayment_lastResult(lastResult:EditContestPaymentResponse):void;
       /**
        * Add a listener for the editContestPayment operation successful result event
        * @param The listener function
        */
       function addeditContestPaymentEventListener(listener:Function):void;
       
       
        /**
         * The editContestPayment operation request wrapper
         */
        function get editContestPayment_request_var():EditContestPayment_request;
        
        /**
         * @private
         */
        function set editContestPayment_request_var(request:EditContestPayment_request):void;
                   
    	//Stub functions for the getAllContestHeaders operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param getAllContestHeaders
    	 * @return An AsyncToken
    	 */
    	function getAllContestHeaders(getAllContestHeaders:GetAllContestHeaders):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function getAllContestHeaders_send():AsyncToken;
        
        /**
         * The getAllContestHeaders operation lastResult property
         */
        function get getAllContestHeaders_lastResult():GetAllContestHeadersResponse;
		/**
		 * @private
		 */
        function set getAllContestHeaders_lastResult(lastResult:GetAllContestHeadersResponse):void;
       /**
        * Add a listener for the getAllContestHeaders operation successful result event
        * @param The listener function
        */
       function addgetAllContestHeadersEventListener(listener:Function):void;
       
       
        /**
         * The getAllContestHeaders operation request wrapper
         */
        function get getAllContestHeaders_request_var():GetAllContestHeaders_request;
        
        /**
         * @private
         */
        function set getAllContestHeaders_request_var(request:GetAllContestHeaders_request):void;
                   
    	//Stub functions for the getAllContestTypes operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param getAllContestTypes
    	 * @return An AsyncToken
    	 */
    	function getAllContestTypes(getAllContestTypes:GetAllContestTypes):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function getAllContestTypes_send():AsyncToken;
        
        /**
         * The getAllContestTypes operation lastResult property
         */
        function get getAllContestTypes_lastResult():GetAllContestTypesResponse;
		/**
		 * @private
		 */
        function set getAllContestTypes_lastResult(lastResult:GetAllContestTypesResponse):void;
       /**
        * Add a listener for the getAllContestTypes operation successful result event
        * @param The listener function
        */
       function addgetAllContestTypesEventListener(listener:Function):void;
       
       
        /**
         * The getAllContestTypes operation request wrapper
         */
        function get getAllContestTypes_request_var():GetAllContestTypes_request;
        
        /**
         * @private
         */
        function set getAllContestTypes_request_var(request:GetAllContestTypes_request):void;
                   
    	//Stub functions for the getAllContests operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param getAllContests
    	 * @return An AsyncToken
    	 */
    	function getAllContests(getAllContests:GetAllContests):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function getAllContests_send():AsyncToken;
        
        /**
         * The getAllContests operation lastResult property
         */
        function get getAllContests_lastResult():GetAllContestsResponse;
		/**
		 * @private
		 */
        function set getAllContests_lastResult(lastResult:GetAllContestsResponse):void;
       /**
        * Add a listener for the getAllContests operation successful result event
        * @param The listener function
        */
       function addgetAllContestsEventListener(listener:Function):void;
       
       
        /**
         * The getAllContests operation request wrapper
         */
        function get getAllContests_request_var():GetAllContests_request;
        
        /**
         * @private
         */
        function set getAllContests_request_var(request:GetAllContests_request):void;
                   
    	//Stub functions for the getAllDocumentTypes operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param getAllDocumentTypes
    	 * @return An AsyncToken
    	 */
    	function getAllDocumentTypes(getAllDocumentTypes:GetAllDocumentTypes):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function getAllDocumentTypes_send():AsyncToken;
        
        /**
         * The getAllDocumentTypes operation lastResult property
         */
        function get getAllDocumentTypes_lastResult():GetAllDocumentTypesResponse;
		/**
		 * @private
		 */
        function set getAllDocumentTypes_lastResult(lastResult:GetAllDocumentTypesResponse):void;
       /**
        * Add a listener for the getAllDocumentTypes operation successful result event
        * @param The listener function
        */
       function addgetAllDocumentTypesEventListener(listener:Function):void;
       
       
        /**
         * The getAllDocumentTypes operation request wrapper
         */
        function get getAllDocumentTypes_request_var():GetAllDocumentTypes_request;
        
        /**
         * @private
         */
        function set getAllDocumentTypes_request_var(request:GetAllDocumentTypes_request):void;
                   
    	//Stub functions for the getAllMediums operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param getAllMediums
    	 * @return An AsyncToken
    	 */
    	function getAllMediums(getAllMediums:GetAllMediums):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function getAllMediums_send():AsyncToken;
        
        /**
         * The getAllMediums operation lastResult property
         */
        function get getAllMediums_lastResult():GetAllMediumsResponse;
		/**
		 * @private
		 */
        function set getAllMediums_lastResult(lastResult:GetAllMediumsResponse):void;
       /**
        * Add a listener for the getAllMediums operation successful result event
        * @param The listener function
        */
       function addgetAllMediumsEventListener(listener:Function):void;
       
       
        /**
         * The getAllMediums operation request wrapper
         */
        function get getAllMediums_request_var():GetAllMediums_request;
        
        /**
         * @private
         */
        function set getAllMediums_request_var(request:GetAllMediums_request):void;
                   
    	//Stub functions for the getAllStudioFileTypes operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param getAllStudioFileTypes
    	 * @return An AsyncToken
    	 */
    	function getAllStudioFileTypes(getAllStudioFileTypes:GetAllStudioFileTypes):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function getAllStudioFileTypes_send():AsyncToken;
        
        /**
         * The getAllStudioFileTypes operation lastResult property
         */
        function get getAllStudioFileTypes_lastResult():GetAllStudioFileTypesResponse;
		/**
		 * @private
		 */
        function set getAllStudioFileTypes_lastResult(lastResult:GetAllStudioFileTypesResponse):void;
       /**
        * Add a listener for the getAllStudioFileTypes operation successful result event
        * @param The listener function
        */
       function addgetAllStudioFileTypesEventListener(listener:Function):void;
       
       
        /**
         * The getAllStudioFileTypes operation request wrapper
         */
        function get getAllStudioFileTypes_request_var():GetAllStudioFileTypes_request;
        
        /**
         * @private
         */
        function set getAllStudioFileTypes_request_var(request:GetAllStudioFileTypes_request):void;
                   
    	//Stub functions for the getChangeHistory operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param arg0
    	 * @param getChangeHistory
    	 * @return An AsyncToken
    	 */
    	function getChangeHistory(getChangeHistory:GetChangeHistory):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function getChangeHistory_send():AsyncToken;
        
        /**
         * The getChangeHistory operation lastResult property
         */
        function get getChangeHistory_lastResult():GetChangeHistoryResponse;
		/**
		 * @private
		 */
        function set getChangeHistory_lastResult(lastResult:GetChangeHistoryResponse):void;
       /**
        * Add a listener for the getChangeHistory operation successful result event
        * @param The listener function
        */
       function addgetChangeHistoryEventListener(listener:Function):void;
       
       
        /**
         * The getChangeHistory operation request wrapper
         */
        function get getChangeHistory_request_var():GetChangeHistory_request;
        
        /**
         * @private
         */
        function set getChangeHistory_request_var(request:GetChangeHistory_request):void;
                   
    	//Stub functions for the getContest operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param getContest
    	 * @param arg0
    	 * @return An AsyncToken
    	 */
    	function getContest(getContest:GetContest):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function getContest_send():AsyncToken;
        
        /**
         * The getContest operation lastResult property
         */
        function get getContest_lastResult():GetContestResponse;
		/**
		 * @private
		 */
        function set getContest_lastResult(lastResult:GetContestResponse):void;
       /**
        * Add a listener for the getContest operation successful result event
        * @param The listener function
        */
       function addgetContestEventListener(listener:Function):void;
       
       
        /**
         * The getContest operation request wrapper
         */
        function get getContest_request_var():GetContest_request;
        
        /**
         * @private
         */
        function set getContest_request_var(request:GetContest_request):void;
                   
    	//Stub functions for the getContestPayment operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param arg0
    	 * @param getContestPayment
    	 * @return An AsyncToken
    	 */
    	function getContestPayment(getContestPayment:GetContestPayment):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function getContestPayment_send():AsyncToken;
        
        /**
         * The getContestPayment operation lastResult property
         */
        function get getContestPayment_lastResult():GetContestPaymentResponse;
		/**
		 * @private
		 */
        function set getContestPayment_lastResult(lastResult:GetContestPaymentResponse):void;
       /**
        * Add a listener for the getContestPayment operation successful result event
        * @param The listener function
        */
       function addgetContestPaymentEventListener(listener:Function):void;
       
       
        /**
         * The getContestPayment operation request wrapper
         */
        function get getContestPayment_request_var():GetContestPayment_request;
        
        /**
         * @private
         */
        function set getContestPayment_request_var(request:GetContestPayment_request):void;
                   
    	//Stub functions for the getContestsForClient operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param arg0
    	 * @param getContestsForClient
    	 * @return An AsyncToken
    	 */
    	function getContestsForClient(getContestsForClient:GetContestsForClient):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function getContestsForClient_send():AsyncToken;
        
        /**
         * The getContestsForClient operation lastResult property
         */
        function get getContestsForClient_lastResult():GetContestsForClientResponse;
		/**
		 * @private
		 */
        function set getContestsForClient_lastResult(lastResult:GetContestsForClientResponse):void;
       /**
        * Add a listener for the getContestsForClient operation successful result event
        * @param The listener function
        */
       function addgetContestsForClientEventListener(listener:Function):void;
       
       
        /**
         * The getContestsForClient operation request wrapper
         */
        function get getContestsForClient_request_var():GetContestsForClient_request;
        
        /**
         * @private
         */
        function set getContestsForClient_request_var(request:GetContestsForClient_request):void;
                   
    	//Stub functions for the getContestsForProject operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param getContestsForProject
    	 * @param arg0
    	 * @return An AsyncToken
    	 */
    	function getContestsForProject(getContestsForProject:GetContestsForProject):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function getContestsForProject_send():AsyncToken;
        
        /**
         * The getContestsForProject operation lastResult property
         */
        function get getContestsForProject_lastResult():GetContestsForProjectResponse;
		/**
		 * @private
		 */
        function set getContestsForProject_lastResult(lastResult:GetContestsForProjectResponse):void;
       /**
        * Add a listener for the getContestsForProject operation successful result event
        * @param The listener function
        */
       function addgetContestsForProjectEventListener(listener:Function):void;
       
       
        /**
         * The getContestsForProject operation request wrapper
         */
        function get getContestsForProject_request_var():GetContestsForProject_request;
        
        /**
         * @private
         */
        function set getContestsForProject_request_var(request:GetContestsForProject_request):void;
                   
    	//Stub functions for the getLatestChanges operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param arg0
    	 * @param getLatestChanges
    	 * @return An AsyncToken
    	 */
    	function getLatestChanges(getLatestChanges:GetLatestChanges):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function getLatestChanges_send():AsyncToken;
        
        /**
         * The getLatestChanges operation lastResult property
         */
        function get getLatestChanges_lastResult():GetLatestChangesResponse;
		/**
		 * @private
		 */
        function set getLatestChanges_lastResult(lastResult:GetLatestChangesResponse):void;
       /**
        * Add a listener for the getLatestChanges operation successful result event
        * @param The listener function
        */
       function addgetLatestChangesEventListener(listener:Function):void;
       
       
        /**
         * The getLatestChanges operation request wrapper
         */
        function get getLatestChanges_request_var():GetLatestChanges_request;
        
        /**
         * @private
         */
        function set getLatestChanges_request_var(request:GetLatestChanges_request):void;
                   
    	//Stub functions for the getMimeTypeId operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param arg0
    	 * @param getMimeTypeId
    	 * @return An AsyncToken
    	 */
    	function getMimeTypeId(getMimeTypeId:GetMimeTypeId):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function getMimeTypeId_send():AsyncToken;
        
        /**
         * The getMimeTypeId operation lastResult property
         */
        function get getMimeTypeId_lastResult():GetMimeTypeIdResponse;
		/**
		 * @private
		 */
        function set getMimeTypeId_lastResult(lastResult:GetMimeTypeIdResponse):void;
       /**
        * Add a listener for the getMimeTypeId operation successful result event
        * @param The listener function
        */
       function addgetMimeTypeIdEventListener(listener:Function):void;
       
       
        /**
         * The getMimeTypeId operation request wrapper
         */
        function get getMimeTypeId_request_var():GetMimeTypeId_request;
        
        /**
         * @private
         */
        function set getMimeTypeId_request_var(request:GetMimeTypeId_request):void;
                   
    	//Stub functions for the getStatusList operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param getStatusList
    	 * @return An AsyncToken
    	 */
    	function getStatusList(getStatusList:GetStatusList):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function getStatusList_send():AsyncToken;
        
        /**
         * The getStatusList operation lastResult property
         */
        function get getStatusList_lastResult():GetStatusListResponse;
		/**
		 * @private
		 */
        function set getStatusList_lastResult(lastResult:GetStatusListResponse):void;
       /**
        * Add a listener for the getStatusList operation successful result event
        * @param The listener function
        */
       function addgetStatusListEventListener(listener:Function):void;
       
       
        /**
         * The getStatusList operation request wrapper
         */
        function get getStatusList_request_var():GetStatusList_request;
        
        /**
         * @private
         */
        function set getStatusList_request_var(request:GetStatusList_request):void;
                   
    	//Stub functions for the getSubmissionFileTypes operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param getSubmissionFileTypes
    	 * @return An AsyncToken
    	 */
    	function getSubmissionFileTypes(getSubmissionFileTypes:GetSubmissionFileTypes):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function getSubmissionFileTypes_send():AsyncToken;
        
        /**
         * The getSubmissionFileTypes operation lastResult property
         */
        function get getSubmissionFileTypes_lastResult():GetSubmissionFileTypesResponse;
		/**
		 * @private
		 */
        function set getSubmissionFileTypes_lastResult(lastResult:GetSubmissionFileTypesResponse):void;
       /**
        * Add a listener for the getSubmissionFileTypes operation successful result event
        * @param The listener function
        */
       function addgetSubmissionFileTypesEventListener(listener:Function):void;
       
       
        /**
         * The getSubmissionFileTypes operation request wrapper
         */
        function get getSubmissionFileTypes_request_var():GetSubmissionFileTypes_request;
        
        /**
         * @private
         */
        function set getSubmissionFileTypes_request_var(request:GetSubmissionFileTypes_request):void;
                   
    	//Stub functions for the markForPurchase operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param markForPurchase
    	 * @param arg0
    	 * @return An AsyncToken
    	 */
    	function markForPurchase(markForPurchase:MarkForPurchase):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function markForPurchase_send():AsyncToken;
        
        /**
         * The markForPurchase operation lastResult property
         */
        function get markForPurchase_lastResult():MarkForPurchaseResponse;
		/**
		 * @private
		 */
        function set markForPurchase_lastResult(lastResult:MarkForPurchaseResponse):void;
       /**
        * Add a listener for the markForPurchase operation successful result event
        * @param The listener function
        */
       function addmarkForPurchaseEventListener(listener:Function):void;
       
       
        /**
         * The markForPurchase operation request wrapper
         */
        function get markForPurchase_request_var():MarkForPurchase_request;
        
        /**
         * @private
         */
        function set markForPurchase_request_var(request:MarkForPurchase_request):void;
                   
    	//Stub functions for the processMissingPayments operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param arg0
    	 * @param processMissingPayments
    	 * @return An AsyncToken
    	 */
    	function processMissingPayments(processMissingPayments:ProcessMissingPayments):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function processMissingPayments_send():AsyncToken;
        
        /**
         * The processMissingPayments operation lastResult property
         */
        function get processMissingPayments_lastResult():ProcessMissingPaymentsResponse;
		/**
		 * @private
		 */
        function set processMissingPayments_lastResult(lastResult:ProcessMissingPaymentsResponse):void;
       /**
        * Add a listener for the processMissingPayments operation successful result event
        * @param The listener function
        */
       function addprocessMissingPaymentsEventListener(listener:Function):void;
       
       
        /**
         * The processMissingPayments operation request wrapper
         */
        function get processMissingPayments_request_var():ProcessMissingPayments_request;
        
        /**
         * @private
         */
        function set processMissingPayments_request_var(request:ProcessMissingPayments_request):void;
                   
    	//Stub functions for the purchaseSubmission operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param arg0
    	 * @param arg1
    	 * @param arg2
    	 * @param purchaseSubmission
    	 * @return An AsyncToken
    	 */
    	function purchaseSubmission(purchaseSubmission:PurchaseSubmission):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function purchaseSubmission_send():AsyncToken;
        
        /**
         * The purchaseSubmission operation lastResult property
         */
        function get purchaseSubmission_lastResult():PurchaseSubmissionResponse;
		/**
		 * @private
		 */
        function set purchaseSubmission_lastResult(lastResult:PurchaseSubmissionResponse):void;
       /**
        * Add a listener for the purchaseSubmission operation successful result event
        * @param The listener function
        */
       function addpurchaseSubmissionEventListener(listener:Function):void;
       
       
        /**
         * The purchaseSubmission operation request wrapper
         */
        function get purchaseSubmission_request_var():PurchaseSubmission_request;
        
        /**
         * @private
         */
        function set purchaseSubmission_request_var(request:PurchaseSubmission_request):void;
                   
    	//Stub functions for the removeContestPayment operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param arg0
    	 * @param removeContestPayment
    	 * @return An AsyncToken
    	 */
    	function removeContestPayment(removeContestPayment:RemoveContestPayment):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function removeContestPayment_send():AsyncToken;
        
        /**
         * The removeContestPayment operation lastResult property
         */
        function get removeContestPayment_lastResult():RemoveContestPaymentResponse;
		/**
		 * @private
		 */
        function set removeContestPayment_lastResult(lastResult:RemoveContestPaymentResponse):void;
       /**
        * Add a listener for the removeContestPayment operation successful result event
        * @param The listener function
        */
       function addremoveContestPaymentEventListener(listener:Function):void;
       
       
        /**
         * The removeContestPayment operation request wrapper
         */
        function get removeContestPayment_request_var():RemoveContestPayment_request;
        
        /**
         * @private
         */
        function set removeContestPayment_request_var(request:RemoveContestPayment_request):void;
                   
    	//Stub functions for the removeDocument operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param arg0
    	 * @param removeDocument
    	 * @return An AsyncToken
    	 */
    	function removeDocument(removeDocument:RemoveDocument):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function removeDocument_send():AsyncToken;
        
        /**
         * The removeDocument operation lastResult property
         */
        function get removeDocument_lastResult():RemoveDocumentResponse;
		/**
		 * @private
		 */
        function set removeDocument_lastResult(lastResult:RemoveDocumentResponse):void;
       /**
        * Add a listener for the removeDocument operation successful result event
        * @param The listener function
        */
       function addremoveDocumentEventListener(listener:Function):void;
       
       
        /**
         * The removeDocument operation request wrapper
         */
        function get removeDocument_request_var():RemoveDocument_request;
        
        /**
         * @private
         */
        function set removeDocument_request_var(request:RemoveDocument_request):void;
                   
    	//Stub functions for the removeDocumentFromContest operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param removeDocumentFromContest
    	 * @param arg0
    	 * @return An AsyncToken
    	 */
    	function removeDocumentFromContest(removeDocumentFromContest:RemoveDocumentFromContest):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function removeDocumentFromContest_send():AsyncToken;
        
        /**
         * The removeDocumentFromContest operation lastResult property
         */
        function get removeDocumentFromContest_lastResult():RemoveDocumentFromContestResponse;
		/**
		 * @private
		 */
        function set removeDocumentFromContest_lastResult(lastResult:RemoveDocumentFromContestResponse):void;
       /**
        * Add a listener for the removeDocumentFromContest operation successful result event
        * @param The listener function
        */
       function addremoveDocumentFromContestEventListener(listener:Function):void;
       
       
        /**
         * The removeDocumentFromContest operation request wrapper
         */
        function get removeDocumentFromContest_request_var():RemoveDocumentFromContest_request;
        
        /**
         * @private
         */
        function set removeDocumentFromContest_request_var(request:RemoveDocumentFromContest_request):void;
                   
    	//Stub functions for the removeSubmission operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param arg0
    	 * @param removeSubmission
    	 * @return An AsyncToken
    	 */
    	function removeSubmission(removeSubmission:RemoveSubmission):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function removeSubmission_send():AsyncToken;
        
        /**
         * The removeSubmission operation lastResult property
         */
        function get removeSubmission_lastResult():RemoveSubmissionResponse;
		/**
		 * @private
		 */
        function set removeSubmission_lastResult(lastResult:RemoveSubmissionResponse):void;
       /**
        * Add a listener for the removeSubmission operation successful result event
        * @param The listener function
        */
       function addremoveSubmissionEventListener(listener:Function):void;
       
       
        /**
         * The removeSubmission operation request wrapper
         */
        function get removeSubmission_request_var():RemoveSubmission_request;
        
        /**
         * @private
         */
        function set removeSubmission_request_var(request:RemoveSubmission_request):void;
                   
    	//Stub functions for the retrieveAllSubmissionsByMember operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param retrieveAllSubmissionsByMember
    	 * @param arg0
    	 * @return An AsyncToken
    	 */
    	function retrieveAllSubmissionsByMember(retrieveAllSubmissionsByMember:RetrieveAllSubmissionsByMember):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function retrieveAllSubmissionsByMember_send():AsyncToken;
        
        /**
         * The retrieveAllSubmissionsByMember operation lastResult property
         */
        function get retrieveAllSubmissionsByMember_lastResult():RetrieveAllSubmissionsByMemberResponse;
		/**
		 * @private
		 */
        function set retrieveAllSubmissionsByMember_lastResult(lastResult:RetrieveAllSubmissionsByMemberResponse):void;
       /**
        * Add a listener for the retrieveAllSubmissionsByMember operation successful result event
        * @param The listener function
        */
       function addretrieveAllSubmissionsByMemberEventListener(listener:Function):void;
       
       
        /**
         * The retrieveAllSubmissionsByMember operation request wrapper
         */
        function get retrieveAllSubmissionsByMember_request_var():RetrieveAllSubmissionsByMember_request;
        
        /**
         * @private
         */
        function set retrieveAllSubmissionsByMember_request_var(request:RetrieveAllSubmissionsByMember_request):void;
                   
    	//Stub functions for the retrieveSubmission operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param arg0
    	 * @param retrieveSubmission
    	 * @return An AsyncToken
    	 */
    	function retrieveSubmission(retrieveSubmission:RetrieveSubmission):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function retrieveSubmission_send():AsyncToken;
        
        /**
         * The retrieveSubmission operation lastResult property
         */
        function get retrieveSubmission_lastResult():RetrieveSubmissionResponse;
		/**
		 * @private
		 */
        function set retrieveSubmission_lastResult(lastResult:RetrieveSubmissionResponse):void;
       /**
        * Add a listener for the retrieveSubmission operation successful result event
        * @param The listener function
        */
       function addretrieveSubmissionEventListener(listener:Function):void;
       
       
        /**
         * The retrieveSubmission operation request wrapper
         */
        function get retrieveSubmission_request_var():RetrieveSubmission_request;
        
        /**
         * @private
         */
        function set retrieveSubmission_request_var(request:RetrieveSubmission_request):void;
                   
    	//Stub functions for the retrieveSubmissionsForContest operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param arg0
    	 * @param retrieveSubmissionsForContest
    	 * @return An AsyncToken
    	 */
    	function retrieveSubmissionsForContest(retrieveSubmissionsForContest:RetrieveSubmissionsForContest):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function retrieveSubmissionsForContest_send():AsyncToken;
        
        /**
         * The retrieveSubmissionsForContest operation lastResult property
         */
        function get retrieveSubmissionsForContest_lastResult():RetrieveSubmissionsForContestResponse;
		/**
		 * @private
		 */
        function set retrieveSubmissionsForContest_lastResult(lastResult:RetrieveSubmissionsForContestResponse):void;
       /**
        * Add a listener for the retrieveSubmissionsForContest operation successful result event
        * @param The listener function
        */
       function addretrieveSubmissionsForContestEventListener(listener:Function):void;
       
       
        /**
         * The retrieveSubmissionsForContest operation request wrapper
         */
        function get retrieveSubmissionsForContest_request_var():RetrieveSubmissionsForContest_request;
        
        /**
         * @private
         */
        function set retrieveSubmissionsForContest_request_var(request:RetrieveSubmissionsForContest_request):void;
                   
    	//Stub functions for the searchContests operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param arg0
    	 * @param searchContests
    	 * @return An AsyncToken
    	 */
    	function searchContests(searchContests:SearchContests):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function searchContests_send():AsyncToken;
        
        /**
         * The searchContests operation lastResult property
         */
        function get searchContests_lastResult():SearchContestsResponse;
		/**
		 * @private
		 */
        function set searchContests_lastResult(lastResult:SearchContestsResponse):void;
       /**
        * Add a listener for the searchContests operation successful result event
        * @param The listener function
        */
       function addsearchContestsEventListener(listener:Function):void;
       
       
        /**
         * The searchContests operation request wrapper
         */
        function get searchContests_request_var():SearchContests_request;
        
        /**
         * @private
         */
        function set searchContests_request_var(request:SearchContests_request):void;
                   
    	//Stub functions for the setSubmissionPlacement operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param arg0
    	 * @param arg1
    	 * @param setSubmissionPlacement
    	 * @return An AsyncToken
    	 */
    	function setSubmissionPlacement(setSubmissionPlacement:SetSubmissionPlacement):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function setSubmissionPlacement_send():AsyncToken;
        
        /**
         * The setSubmissionPlacement operation lastResult property
         */
        function get setSubmissionPlacement_lastResult():SetSubmissionPlacementResponse;
		/**
		 * @private
		 */
        function set setSubmissionPlacement_lastResult(lastResult:SetSubmissionPlacementResponse):void;
       /**
        * Add a listener for the setSubmissionPlacement operation successful result event
        * @param The listener function
        */
       function addsetSubmissionPlacementEventListener(listener:Function):void;
       
       
        /**
         * The setSubmissionPlacement operation request wrapper
         */
        function get setSubmissionPlacement_request_var():SetSubmissionPlacement_request;
        
        /**
         * @private
         */
        function set setSubmissionPlacement_request_var(request:SetSubmissionPlacement_request):void;
                   
    	//Stub functions for the setSubmissionPrize operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param setSubmissionPrize
    	 * @param arg0
    	 * @param arg1
    	 * @return An AsyncToken
    	 */
    	function setSubmissionPrize(setSubmissionPrize:SetSubmissionPrize):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function setSubmissionPrize_send():AsyncToken;
        
        /**
         * The setSubmissionPrize operation lastResult property
         */
        function get setSubmissionPrize_lastResult():SetSubmissionPrizeResponse;
		/**
		 * @private
		 */
        function set setSubmissionPrize_lastResult(lastResult:SetSubmissionPrizeResponse):void;
       /**
        * Add a listener for the setSubmissionPrize operation successful result event
        * @param The listener function
        */
       function addsetSubmissionPrizeEventListener(listener:Function):void;
       
       
        /**
         * The setSubmissionPrize operation request wrapper
         */
        function get setSubmissionPrize_request_var():SetSubmissionPrize_request;
        
        /**
         * @private
         */
        function set setSubmissionPrize_request_var(request:SetSubmissionPrize_request):void;
                   
    	//Stub functions for the updateContest operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param arg0
    	 * @param updateContest
    	 * @return An AsyncToken
    	 */
    	function updateContest(updateContest:UpdateContest):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function updateContest_send():AsyncToken;
        
        /**
         * The updateContest operation lastResult property
         */
        function get updateContest_lastResult():UpdateContestResponse;
		/**
		 * @private
		 */
        function set updateContest_lastResult(lastResult:UpdateContestResponse):void;
       /**
        * Add a listener for the updateContest operation successful result event
        * @param The listener function
        */
       function addupdateContestEventListener(listener:Function):void;
       
       
        /**
         * The updateContest operation request wrapper
         */
        function get updateContest_request_var():UpdateContest_request;
        
        /**
         * @private
         */
        function set updateContest_request_var(request:UpdateContest_request):void;
                   
    	//Stub functions for the updateContestStatus operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param updateContestStatus
    	 * @param arg0
    	 * @param arg1
    	 * @return An AsyncToken
    	 */
    	function updateContestStatus(updateContestStatus:UpdateContestStatus):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function updateContestStatus_send():AsyncToken;
        
        /**
         * The updateContestStatus operation lastResult property
         */
        function get updateContestStatus_lastResult():UpdateContestStatusResponse;
		/**
		 * @private
		 */
        function set updateContestStatus_lastResult(lastResult:UpdateContestStatusResponse):void;
       /**
        * Add a listener for the updateContestStatus operation successful result event
        * @param The listener function
        */
       function addupdateContestStatusEventListener(listener:Function):void;
       
       
        /**
         * The updateContestStatus operation request wrapper
         */
        function get updateContestStatus_request_var():UpdateContestStatus_request;
        
        /**
         * @private
         */
        function set updateContestStatus_request_var(request:UpdateContestStatus_request):void;
                   
    	//Stub functions for the updateSubmission operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param updateSubmission
    	 * @param arg0
    	 * @return An AsyncToken
    	 */
    	function updateSubmission(updateSubmission:UpdateSubmission):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function updateSubmission_send():AsyncToken;
        
        /**
         * The updateSubmission operation lastResult property
         */
        function get updateSubmission_lastResult():UpdateSubmissionResponse;
		/**
		 * @private
		 */
        function set updateSubmission_lastResult(lastResult:UpdateSubmissionResponse):void;
       /**
        * Add a listener for the updateSubmission operation successful result event
        * @param The listener function
        */
       function addupdateSubmissionEventListener(listener:Function):void;
       
       
        /**
         * The updateSubmission operation request wrapper
         */
        function get updateSubmission_request_var():UpdateSubmission_request;
        
        /**
         * @private
         */
        function set updateSubmission_request_var(request:UpdateSubmission_request):void;
                   
    	//Stub functions for the uploadDocument operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param uploadDocument
    	 * @param arg0
    	 * @return An AsyncToken
    	 */
    	function uploadDocument(uploadDocument:UploadDocument):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function uploadDocument_send():AsyncToken;
        
        /**
         * The uploadDocument operation lastResult property
         */
        function get uploadDocument_lastResult():UploadDocumentResponse;
		/**
		 * @private
		 */
        function set uploadDocument_lastResult(lastResult:UploadDocumentResponse):void;
       /**
        * Add a listener for the uploadDocument operation successful result event
        * @param The listener function
        */
       function adduploadDocumentEventListener(listener:Function):void;
       
       
        /**
         * The uploadDocument operation request wrapper
         */
        function get uploadDocument_request_var():UploadDocument_request;
        
        /**
         * @private
         */
        function set uploadDocument_request_var(request:UploadDocument_request):void;
                   
    	//Stub functions for the uploadDocumentForContest operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param arg0
    	 * @param uploadDocumentForContest
    	 * @return An AsyncToken
    	 */
    	function uploadDocumentForContest(uploadDocumentForContest:UploadDocumentForContest):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function uploadDocumentForContest_send():AsyncToken;
        
        /**
         * The uploadDocumentForContest operation lastResult property
         */
        function get uploadDocumentForContest_lastResult():UploadDocumentForContestResponse;
		/**
		 * @private
		 */
        function set uploadDocumentForContest_lastResult(lastResult:UploadDocumentForContestResponse):void;
       /**
        * Add a listener for the uploadDocumentForContest operation successful result event
        * @param The listener function
        */
       function adduploadDocumentForContestEventListener(listener:Function):void;
       
       
        /**
         * The uploadDocumentForContest operation request wrapper
         */
        function get uploadDocumentForContest_request_var():UploadDocumentForContest_request;
        
        /**
         * @private
         */
        function set uploadDocumentForContest_request_var(request:UploadDocumentForContest_request):void;
                   
        /**
         * Get access to the underlying web service that the stub uses to communicate with the server
         * @return The base service that the facade implements
         */
        function getWebService():BaseContestServiceFacadeBeanService;
	}
}