
/**
 * BaseContestServiceFacadeBeanServiceService.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated{
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import flash.utils.getDefinitionByName;
	import flash.utils.getQualifiedClassName;
	import mx.controls.treeClasses.DefaultDataDescriptor;
	import mx.utils.ObjectUtil;
	import mx.utils.ObjectProxy;
	import mx.messaging.events.MessageFaultEvent;
	import mx.messaging.MessageResponder;
	import mx.messaging.messages.SOAPMessage;
	import mx.messaging.messages.ErrorMessage;
   	import mx.messaging.ChannelSet;
	import mx.messaging.channels.DirectHTTPChannel;
	import mx.rpc.*;
	import mx.rpc.events.*;
	import mx.rpc.soap.*;
	import mx.rpc.wsdl.*;
	import mx.rpc.xml.*;
	import mx.rpc.soap.types.*;
	import mx.collections.ArrayCollection;
	
	/**
	 * Base service implementation, extends the AbstractWebService and adds specific functionality for the selected WSDL
	 * It defines the options and properties for each of the WSDL's operations
	 */ 
	public class BaseContestServiceFacadeBeanService extends AbstractWebService
    {
		private var results:Object;
		private var schemaMgr:SchemaManager;
		private var BaseContestServiceFacadeBeanServiceService:WSDLService;
		private var BaseContestServiceFacadeBeanServicePortType:WSDLPortType;
		private var BaseContestServiceFacadeBeanServiceBinding:WSDLBinding;
		private var BaseContestServiceFacadeBeanServicePort:WSDLPort;
		private var currentOperation:WSDLOperation;
		private var internal_schema:BaseContestServiceFacadeBeanServiceSchema;
	
		/**
		 * Constructor for the base service, initializes all of the WSDL's properties
		 * @param [Optional] The LCDS destination (if available) to use to contact the server
		 * @param [Optional] The URL to the WSDL end-point
		 */
		public function BaseContestServiceFacadeBeanService(destination:String=null, rootURL:String=null)
		{
			super(destination, rootURL);
			if(destination == null)
			{
				//no destination available; must set it to go directly to the target
				this.useProxy = false;
			}
			else
			{
				//specific destination requested; must set proxying to true
				this.useProxy = true;
			}
			
			if(rootURL != null)
			{
				this.endpointURI = rootURL;
			} 
			else 
			{
				this.endpointURI = null;
			}
			internal_schema = new BaseContestServiceFacadeBeanServiceSchema();
			schemaMgr = new SchemaManager();
			for(var i:int;i<internal_schema.schemas.length;i++)
			{
				internal_schema.schemas[i].targetNamespace=internal_schema.targetNamespaces[i];
				schemaMgr.addSchema(internal_schema.schemas[i]);
			}
BaseContestServiceFacadeBeanServiceService = new WSDLService("BaseContestServiceFacadeBeanServiceService");
			BaseContestServiceFacadeBeanServicePort = new WSDLPort("BaseContestServiceFacadeBeanServicePort",BaseContestServiceFacadeBeanServiceService);
        	BaseContestServiceFacadeBeanServiceBinding = new WSDLBinding("BaseContestServiceFacadeBeanServiceBinding");
	        BaseContestServiceFacadeBeanServicePortType = new WSDLPortType("BaseContestServiceFacadeBeanServicePortType");
       		BaseContestServiceFacadeBeanServiceBinding.portType = BaseContestServiceFacadeBeanServicePortType;
       		BaseContestServiceFacadeBeanServicePort.binding = BaseContestServiceFacadeBeanServiceBinding;
       		BaseContestServiceFacadeBeanServiceService.addPort(BaseContestServiceFacadeBeanServicePort);
       		BaseContestServiceFacadeBeanServicePort.endpointURI = "http://127.0.0.1:8080/contestfacade/ContestServiceFacadeBean";
       		if(this.endpointURI == null)
       		{
       			this.endpointURI = BaseContestServiceFacadeBeanServicePort.endpointURI; 
       		} 
       		
			var requestMessage:WSDLMessage;
	        var responseMessage:WSDLMessage;
//define the WSDLOperation: new WSDLOperation(methodName)
            var addChangeHistory:WSDLOperation = new WSDLOperation("addChangeHistory");
				//input message for the operation
    	        requestMessage = new WSDLMessage("addChangeHistory");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","addChangeHistory"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","addChangeHistory")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","addChangeHistory");
                
                responseMessage = new WSDLMessage("addChangeHistoryResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","addChangeHistoryResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","addChangeHistoryResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","addChangeHistory");addChangeHistory.inputMessage = requestMessage;
	        addChangeHistory.outputMessage = responseMessage;
            addChangeHistory.schemaManager = this.schemaMgr;
            addChangeHistory.soapAction = "\"\"";
            addChangeHistory.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(addChangeHistory);//define the WSDLOperation: new WSDLOperation(methodName)
            var addDocumentToContest:WSDLOperation = new WSDLOperation("addDocumentToContest");
				//input message for the operation
    	        requestMessage = new WSDLMessage("addDocumentToContest");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","addDocumentToContest"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","addDocumentToContest")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","addDocumentToContest");
                
                responseMessage = new WSDLMessage("addDocumentToContestResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","addDocumentToContestResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","addDocumentToContestResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","addDocumentToContest");addDocumentToContest.inputMessage = requestMessage;
	        addDocumentToContest.outputMessage = responseMessage;
            addDocumentToContest.schemaManager = this.schemaMgr;
            addDocumentToContest.soapAction = "\"\"";
            addDocumentToContest.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(addDocumentToContest);//define the WSDLOperation: new WSDLOperation(methodName)
            var createContest:WSDLOperation = new WSDLOperation("createContest");
				//input message for the operation
    	        requestMessage = new WSDLMessage("createContest");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","createContest"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","createContest")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","createContest");
                
                responseMessage = new WSDLMessage("createContestResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","createContestResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","createContestResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","createContest");createContest.inputMessage = requestMessage;
	        createContest.outputMessage = responseMessage;
            createContest.schemaManager = this.schemaMgr;
            createContest.soapAction = "\"\"";
            createContest.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(createContest);//define the WSDLOperation: new WSDLOperation(methodName)
            var createContestPayment:WSDLOperation = new WSDLOperation("createContestPayment");
				//input message for the operation
    	        requestMessage = new WSDLMessage("createContestPayment");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","createContestPayment"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","createContestPayment")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","createContestPayment");
                
                responseMessage = new WSDLMessage("createContestPaymentResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","createContestPaymentResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","createContestPaymentResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","createContestPayment");createContestPayment.inputMessage = requestMessage;
	        createContestPayment.outputMessage = responseMessage;
            createContestPayment.schemaManager = this.schemaMgr;
            createContestPayment.soapAction = "\"\"";
            createContestPayment.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(createContestPayment);//define the WSDLOperation: new WSDLOperation(methodName)
            var deleteContest:WSDLOperation = new WSDLOperation("deleteContest");
				//input message for the operation
    	        requestMessage = new WSDLMessage("deleteContest");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","deleteContest"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","deleteContest")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","deleteContest");
                
                responseMessage = new WSDLMessage("deleteContestResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","deleteContestResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","deleteContestResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","deleteContest");deleteContest.inputMessage = requestMessage;
	        deleteContest.outputMessage = responseMessage;
            deleteContest.schemaManager = this.schemaMgr;
            deleteContest.soapAction = "\"\"";
            deleteContest.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(deleteContest);//define the WSDLOperation: new WSDLOperation(methodName)
            var do_1:WSDLOperation = new WSDLOperation("do_1");
				//input message for the operation
    	        requestMessage = new WSDLMessage("do_1");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","do_1"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","do_1")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","do_1");
                
                responseMessage = new WSDLMessage("do_1Response");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","do_1Response"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","do_1Response")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","do_1");do_1.inputMessage = requestMessage;
	        do_1.outputMessage = responseMessage;
            do_1.schemaManager = this.schemaMgr;
            do_1.soapAction = "\"\"";
            do_1.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(do_1);//define the WSDLOperation: new WSDLOperation(methodName)
            var do_2:WSDLOperation = new WSDLOperation("do_2");
				//input message for the operation
    	        requestMessage = new WSDLMessage("do_2");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","do_2"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","do_2")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","do_2");
                
                responseMessage = new WSDLMessage("do_2Response");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","do_2Response"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","do_2Response")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","do_2");do_2.inputMessage = requestMessage;
	        do_2.outputMessage = responseMessage;
            do_2.schemaManager = this.schemaMgr;
            do_2.soapAction = "\"\"";
            do_2.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(do_2);//define the WSDLOperation: new WSDLOperation(methodName)
            var do_3:WSDLOperation = new WSDLOperation("do_3");
				//input message for the operation
    	        requestMessage = new WSDLMessage("do_3");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","do_3"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","do_3")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","do_3");
                
                responseMessage = new WSDLMessage("do_3Response");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","do_3Response"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","do_3Response")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","do_3");do_3.inputMessage = requestMessage;
	        do_3.outputMessage = responseMessage;
            do_3.schemaManager = this.schemaMgr;
            do_3.soapAction = "\"\"";
            do_3.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(do_3);//define the WSDLOperation: new WSDLOperation(methodName)
            var editContestPayment:WSDLOperation = new WSDLOperation("editContestPayment");
				//input message for the operation
    	        requestMessage = new WSDLMessage("editContestPayment");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","editContestPayment"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","editContestPayment")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","editContestPayment");
                
                responseMessage = new WSDLMessage("editContestPaymentResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","editContestPaymentResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","editContestPaymentResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","editContestPayment");editContestPayment.inputMessage = requestMessage;
	        editContestPayment.outputMessage = responseMessage;
            editContestPayment.schemaManager = this.schemaMgr;
            editContestPayment.soapAction = "\"\"";
            editContestPayment.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(editContestPayment);//define the WSDLOperation: new WSDLOperation(methodName)
            var getAllContestHeaders:WSDLOperation = new WSDLOperation("getAllContestHeaders");
				//input message for the operation
    	        requestMessage = new WSDLMessage("getAllContestHeaders");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getAllContestHeaders"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getAllContestHeaders")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getAllContestHeaders");
                
                responseMessage = new WSDLMessage("getAllContestHeadersResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getAllContestHeadersResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getAllContestHeadersResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getAllContestHeaders");getAllContestHeaders.inputMessage = requestMessage;
	        getAllContestHeaders.outputMessage = responseMessage;
            getAllContestHeaders.schemaManager = this.schemaMgr;
            getAllContestHeaders.soapAction = "\"\"";
            getAllContestHeaders.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(getAllContestHeaders);//define the WSDLOperation: new WSDLOperation(methodName)
            var getAllContestTypes:WSDLOperation = new WSDLOperation("getAllContestTypes");
				//input message for the operation
    	        requestMessage = new WSDLMessage("getAllContestTypes");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getAllContestTypes"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getAllContestTypes")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getAllContestTypes");
                
                responseMessage = new WSDLMessage("getAllContestTypesResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getAllContestTypesResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getAllContestTypesResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getAllContestTypes");getAllContestTypes.inputMessage = requestMessage;
	        getAllContestTypes.outputMessage = responseMessage;
            getAllContestTypes.schemaManager = this.schemaMgr;
            getAllContestTypes.soapAction = "\"\"";
            getAllContestTypes.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(getAllContestTypes);//define the WSDLOperation: new WSDLOperation(methodName)
            var getAllContests:WSDLOperation = new WSDLOperation("getAllContests");
				//input message for the operation
    	        requestMessage = new WSDLMessage("getAllContests");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getAllContests"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getAllContests")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getAllContests");
                
                responseMessage = new WSDLMessage("getAllContestsResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getAllContestsResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getAllContestsResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getAllContests");getAllContests.inputMessage = requestMessage;
	        getAllContests.outputMessage = responseMessage;
            getAllContests.schemaManager = this.schemaMgr;
            getAllContests.soapAction = "\"\"";
            getAllContests.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(getAllContests);//define the WSDLOperation: new WSDLOperation(methodName)
            var getAllDocumentTypes:WSDLOperation = new WSDLOperation("getAllDocumentTypes");
				//input message for the operation
    	        requestMessage = new WSDLMessage("getAllDocumentTypes");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getAllDocumentTypes"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getAllDocumentTypes")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getAllDocumentTypes");
                
                responseMessage = new WSDLMessage("getAllDocumentTypesResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getAllDocumentTypesResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getAllDocumentTypesResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getAllDocumentTypes");getAllDocumentTypes.inputMessage = requestMessage;
	        getAllDocumentTypes.outputMessage = responseMessage;
            getAllDocumentTypes.schemaManager = this.schemaMgr;
            getAllDocumentTypes.soapAction = "\"\"";
            getAllDocumentTypes.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(getAllDocumentTypes);//define the WSDLOperation: new WSDLOperation(methodName)
            var getAllMediums:WSDLOperation = new WSDLOperation("getAllMediums");
				//input message for the operation
    	        requestMessage = new WSDLMessage("getAllMediums");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getAllMediums"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getAllMediums")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getAllMediums");
                
                responseMessage = new WSDLMessage("getAllMediumsResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getAllMediumsResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getAllMediumsResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getAllMediums");getAllMediums.inputMessage = requestMessage;
	        getAllMediums.outputMessage = responseMessage;
            getAllMediums.schemaManager = this.schemaMgr;
            getAllMediums.soapAction = "\"\"";
            getAllMediums.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(getAllMediums);//define the WSDLOperation: new WSDLOperation(methodName)
            var getAllStudioFileTypes:WSDLOperation = new WSDLOperation("getAllStudioFileTypes");
				//input message for the operation
    	        requestMessage = new WSDLMessage("getAllStudioFileTypes");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getAllStudioFileTypes"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getAllStudioFileTypes")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getAllStudioFileTypes");
                
                responseMessage = new WSDLMessage("getAllStudioFileTypesResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getAllStudioFileTypesResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getAllStudioFileTypesResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getAllStudioFileTypes");getAllStudioFileTypes.inputMessage = requestMessage;
	        getAllStudioFileTypes.outputMessage = responseMessage;
            getAllStudioFileTypes.schemaManager = this.schemaMgr;
            getAllStudioFileTypes.soapAction = "\"\"";
            getAllStudioFileTypes.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(getAllStudioFileTypes);//define the WSDLOperation: new WSDLOperation(methodName)
            var getChangeHistory:WSDLOperation = new WSDLOperation("getChangeHistory");
				//input message for the operation
    	        requestMessage = new WSDLMessage("getChangeHistory");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getChangeHistory"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getChangeHistory")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getChangeHistory");
                
                responseMessage = new WSDLMessage("getChangeHistoryResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getChangeHistoryResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getChangeHistoryResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getChangeHistory");getChangeHistory.inputMessage = requestMessage;
	        getChangeHistory.outputMessage = responseMessage;
            getChangeHistory.schemaManager = this.schemaMgr;
            getChangeHistory.soapAction = "\"\"";
            getChangeHistory.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(getChangeHistory);//define the WSDLOperation: new WSDLOperation(methodName)
            var getContest:WSDLOperation = new WSDLOperation("getContest");
				//input message for the operation
    	        requestMessage = new WSDLMessage("getContest");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getContest"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getContest")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getContest");
                
                responseMessage = new WSDLMessage("getContestResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getContestResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getContestResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getContest");getContest.inputMessage = requestMessage;
	        getContest.outputMessage = responseMessage;
            getContest.schemaManager = this.schemaMgr;
            getContest.soapAction = "\"\"";
            getContest.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(getContest);//define the WSDLOperation: new WSDLOperation(methodName)
            var getContestDataOnly:WSDLOperation = new WSDLOperation("getContestDataOnly");
				//input message for the operation
    	        requestMessage = new WSDLMessage("getContestDataOnly");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getContestDataOnly"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getContestDataOnly")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getContestDataOnly");
                
                responseMessage = new WSDLMessage("getContestDataOnlyResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getContestDataOnlyResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getContestDataOnlyResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getContestDataOnly");getContestDataOnly.inputMessage = requestMessage;
	        getContestDataOnly.outputMessage = responseMessage;
            getContestDataOnly.schemaManager = this.schemaMgr;
            getContestDataOnly.soapAction = "\"\"";
            getContestDataOnly.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(getContestDataOnly);//define the WSDLOperation: new WSDLOperation(methodName)
            var getContestDataOnlyByPID:WSDLOperation = new WSDLOperation("getContestDataOnlyByPID");
				//input message for the operation
    	        requestMessage = new WSDLMessage("getContestDataOnlyByPID");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getContestDataOnlyByPID"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getContestDataOnlyByPID")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getContestDataOnlyByPID");
                
                responseMessage = new WSDLMessage("getContestDataOnlyByPIDResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getContestDataOnlyByPIDResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getContestDataOnlyByPIDResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getContestDataOnlyByPID");getContestDataOnlyByPID.inputMessage = requestMessage;
	        getContestDataOnlyByPID.outputMessage = responseMessage;
            getContestDataOnlyByPID.schemaManager = this.schemaMgr;
            getContestDataOnlyByPID.soapAction = "\"\"";
            getContestDataOnlyByPID.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(getContestDataOnlyByPID);//define the WSDLOperation: new WSDLOperation(methodName)
            var getContestPayment:WSDLOperation = new WSDLOperation("getContestPayment");
				//input message for the operation
    	        requestMessage = new WSDLMessage("getContestPayment");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getContestPayment"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getContestPayment")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getContestPayment");
                
                responseMessage = new WSDLMessage("getContestPaymentResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getContestPaymentResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getContestPaymentResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getContestPayment");getContestPayment.inputMessage = requestMessage;
	        getContestPayment.outputMessage = responseMessage;
            getContestPayment.schemaManager = this.schemaMgr;
            getContestPayment.soapAction = "\"\"";
            getContestPayment.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(getContestPayment);//define the WSDLOperation: new WSDLOperation(methodName)
            var getContestsForClient:WSDLOperation = new WSDLOperation("getContestsForClient");
				//input message for the operation
    	        requestMessage = new WSDLMessage("getContestsForClient");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getContestsForClient"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getContestsForClient")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getContestsForClient");
                
                responseMessage = new WSDLMessage("getContestsForClientResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getContestsForClientResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getContestsForClientResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getContestsForClient");getContestsForClient.inputMessage = requestMessage;
	        getContestsForClient.outputMessage = responseMessage;
            getContestsForClient.schemaManager = this.schemaMgr;
            getContestsForClient.soapAction = "\"\"";
            getContestsForClient.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(getContestsForClient);//define the WSDLOperation: new WSDLOperation(methodName)
            var getContestsForProject:WSDLOperation = new WSDLOperation("getContestsForProject");
				//input message for the operation
    	        requestMessage = new WSDLMessage("getContestsForProject");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getContestsForProject"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getContestsForProject")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getContestsForProject");
                
                responseMessage = new WSDLMessage("getContestsForProjectResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getContestsForProjectResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getContestsForProjectResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getContestsForProject");getContestsForProject.inputMessage = requestMessage;
	        getContestsForProject.outputMessage = responseMessage;
            getContestsForProject.schemaManager = this.schemaMgr;
            getContestsForProject.soapAction = "\"\"";
            getContestsForProject.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(getContestsForProject);//define the WSDLOperation: new WSDLOperation(methodName)
            var getLatestChanges:WSDLOperation = new WSDLOperation("getLatestChanges");
				//input message for the operation
    	        requestMessage = new WSDLMessage("getLatestChanges");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getLatestChanges"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getLatestChanges")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getLatestChanges");
                
                responseMessage = new WSDLMessage("getLatestChangesResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getLatestChangesResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getLatestChangesResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getLatestChanges");getLatestChanges.inputMessage = requestMessage;
	        getLatestChanges.outputMessage = responseMessage;
            getLatestChanges.schemaManager = this.schemaMgr;
            getLatestChanges.soapAction = "\"\"";
            getLatestChanges.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(getLatestChanges);//define the WSDLOperation: new WSDLOperation(methodName)
            var getMimeTypeId:WSDLOperation = new WSDLOperation("getMimeTypeId");
				//input message for the operation
    	        requestMessage = new WSDLMessage("getMimeTypeId");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getMimeTypeId"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getMimeTypeId")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getMimeTypeId");
                
                responseMessage = new WSDLMessage("getMimeTypeIdResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getMimeTypeIdResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getMimeTypeIdResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getMimeTypeId");getMimeTypeId.inputMessage = requestMessage;
	        getMimeTypeId.outputMessage = responseMessage;
            getMimeTypeId.schemaManager = this.schemaMgr;
            getMimeTypeId.soapAction = "\"\"";
            getMimeTypeId.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(getMimeTypeId);//define the WSDLOperation: new WSDLOperation(methodName)
            var getSimpleContestData:WSDLOperation = new WSDLOperation("getSimpleContestData");
				//input message for the operation
    	        requestMessage = new WSDLMessage("getSimpleContestData");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleContestData"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleContestData")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleContestData");
                
                responseMessage = new WSDLMessage("getSimpleContestDataResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleContestDataResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleContestDataResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleContestData");getSimpleContestData.inputMessage = requestMessage;
	        getSimpleContestData.outputMessage = responseMessage;
            getSimpleContestData.schemaManager = this.schemaMgr;
            getSimpleContestData.soapAction = "\"\"";
            getSimpleContestData.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(getSimpleContestData);//define the WSDLOperation: new WSDLOperation(methodName)
            var getSimpleContestDataByPID:WSDLOperation = new WSDLOperation("getSimpleContestDataByPID");
				//input message for the operation
    	        requestMessage = new WSDLMessage("getSimpleContestDataByPID");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleContestDataByPID"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleContestDataByPID")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleContestDataByPID");
                
                responseMessage = new WSDLMessage("getSimpleContestDataByPIDResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleContestDataByPIDResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleContestDataByPIDResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleContestDataByPID");getSimpleContestDataByPID.inputMessage = requestMessage;
	        getSimpleContestDataByPID.outputMessage = responseMessage;
            getSimpleContestDataByPID.schemaManager = this.schemaMgr;
            getSimpleContestDataByPID.soapAction = "\"\"";
            getSimpleContestDataByPID.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(getSimpleContestDataByPID);//define the WSDLOperation: new WSDLOperation(methodName)
            var getSimpleProjectContestData:WSDLOperation = new WSDLOperation("getSimpleProjectContestData");
				//input message for the operation
    	        requestMessage = new WSDLMessage("getSimpleProjectContestData");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleProjectContestData"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleProjectContestData")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleProjectContestData");
                
                responseMessage = new WSDLMessage("getSimpleProjectContestDataResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleProjectContestDataResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleProjectContestDataResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleProjectContestData");getSimpleProjectContestData.inputMessage = requestMessage;
	        getSimpleProjectContestData.outputMessage = responseMessage;
            getSimpleProjectContestData.schemaManager = this.schemaMgr;
            getSimpleProjectContestData.soapAction = "\"\"";
            getSimpleProjectContestData.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(getSimpleProjectContestData);//define the WSDLOperation: new WSDLOperation(methodName)
            var getSimpleProjectContestDataByPID:WSDLOperation = new WSDLOperation("getSimpleProjectContestDataByPID");
				//input message for the operation
    	        requestMessage = new WSDLMessage("getSimpleProjectContestDataByPID");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleProjectContestDataByPID"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleProjectContestDataByPID")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleProjectContestDataByPID");
                
                responseMessage = new WSDLMessage("getSimpleProjectContestDataByPIDResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleProjectContestDataByPIDResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleProjectContestDataByPIDResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleProjectContestDataByPID");getSimpleProjectContestDataByPID.inputMessage = requestMessage;
	        getSimpleProjectContestDataByPID.outputMessage = responseMessage;
            getSimpleProjectContestDataByPID.schemaManager = this.schemaMgr;
            getSimpleProjectContestDataByPID.soapAction = "\"\"";
            getSimpleProjectContestDataByPID.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(getSimpleProjectContestDataByPID);//define the WSDLOperation: new WSDLOperation(methodName)
            var getStatusList:WSDLOperation = new WSDLOperation("getStatusList");
				//input message for the operation
    	        requestMessage = new WSDLMessage("getStatusList");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getStatusList"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getStatusList")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getStatusList");
                
                responseMessage = new WSDLMessage("getStatusListResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getStatusListResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getStatusListResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getStatusList");getStatusList.inputMessage = requestMessage;
	        getStatusList.outputMessage = responseMessage;
            getStatusList.schemaManager = this.schemaMgr;
            getStatusList.soapAction = "\"\"";
            getStatusList.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(getStatusList);//define the WSDLOperation: new WSDLOperation(methodName)
            var getSubmissionFileTypes:WSDLOperation = new WSDLOperation("getSubmissionFileTypes");
				//input message for the operation
    	        requestMessage = new WSDLMessage("getSubmissionFileTypes");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getSubmissionFileTypes"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getSubmissionFileTypes")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getSubmissionFileTypes");
                
                responseMessage = new WSDLMessage("getSubmissionFileTypesResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","getSubmissionFileTypesResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","getSubmissionFileTypesResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","getSubmissionFileTypes");getSubmissionFileTypes.inputMessage = requestMessage;
	        getSubmissionFileTypes.outputMessage = responseMessage;
            getSubmissionFileTypes.schemaManager = this.schemaMgr;
            getSubmissionFileTypes.soapAction = "\"\"";
            getSubmissionFileTypes.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(getSubmissionFileTypes);//define the WSDLOperation: new WSDLOperation(methodName)
            var markForPurchase:WSDLOperation = new WSDLOperation("markForPurchase");
				//input message for the operation
    	        requestMessage = new WSDLMessage("markForPurchase");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","markForPurchase"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","markForPurchase")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","markForPurchase");
                
                responseMessage = new WSDLMessage("markForPurchaseResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","markForPurchaseResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","markForPurchaseResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","markForPurchase");markForPurchase.inputMessage = requestMessage;
	        markForPurchase.outputMessage = responseMessage;
            markForPurchase.schemaManager = this.schemaMgr;
            markForPurchase.soapAction = "\"\"";
            markForPurchase.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(markForPurchase);//define the WSDLOperation: new WSDLOperation(methodName)
            var processContestPayment:WSDLOperation = new WSDLOperation("processContestPayment");
				//input message for the operation
    	        requestMessage = new WSDLMessage("processContestPayment");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","processContestPayment"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","processContestPayment")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","processContestPayment");
                
                responseMessage = new WSDLMessage("processContestPaymentResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","processContestPaymentResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","processContestPaymentResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","processContestPayment");processContestPayment.inputMessage = requestMessage;
	        processContestPayment.outputMessage = responseMessage;
            processContestPayment.schemaManager = this.schemaMgr;
            processContestPayment.soapAction = "\"\"";
            processContestPayment.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(processContestPayment);//define the WSDLOperation: new WSDLOperation(methodName)
            var processContestPayment_old:WSDLOperation = new WSDLOperation("processContestPayment_old");
				//input message for the operation
    	        requestMessage = new WSDLMessage("processContestPayment_old");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","processContestPayment_old"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","processContestPayment_old")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","processContestPayment_old");
                
                responseMessage = new WSDLMessage("processContestPayment_oldResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","processContestPayment_oldResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","processContestPayment_oldResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","processContestPayment_old");processContestPayment_old.inputMessage = requestMessage;
	        processContestPayment_old.outputMessage = responseMessage;
            processContestPayment_old.schemaManager = this.schemaMgr;
            processContestPayment_old.soapAction = "\"\"";
            processContestPayment_old.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(processContestPayment_old);//define the WSDLOperation: new WSDLOperation(methodName)
            var processMissingPayments:WSDLOperation = new WSDLOperation("processMissingPayments");
				//input message for the operation
    	        requestMessage = new WSDLMessage("processMissingPayments");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","processMissingPayments"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","processMissingPayments")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","processMissingPayments");
                
                responseMessage = new WSDLMessage("processMissingPaymentsResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","processMissingPaymentsResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","processMissingPaymentsResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","processMissingPayments");processMissingPayments.inputMessage = requestMessage;
	        processMissingPayments.outputMessage = responseMessage;
            processMissingPayments.schemaManager = this.schemaMgr;
            processMissingPayments.soapAction = "\"\"";
            processMissingPayments.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(processMissingPayments);//define the WSDLOperation: new WSDLOperation(methodName)
            var processSubmissionPayment:WSDLOperation = new WSDLOperation("processSubmissionPayment");
				//input message for the operation
    	        requestMessage = new WSDLMessage("processSubmissionPayment");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","processSubmissionPayment"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","processSubmissionPayment")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","processSubmissionPayment");
                
                responseMessage = new WSDLMessage("processSubmissionPaymentResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","processSubmissionPaymentResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","processSubmissionPaymentResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","processSubmissionPayment");processSubmissionPayment.inputMessage = requestMessage;
	        processSubmissionPayment.outputMessage = responseMessage;
            processSubmissionPayment.schemaManager = this.schemaMgr;
            processSubmissionPayment.soapAction = "\"\"";
            processSubmissionPayment.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(processSubmissionPayment);//define the WSDLOperation: new WSDLOperation(methodName)
            var purchaseSubmission:WSDLOperation = new WSDLOperation("purchaseSubmission");
				//input message for the operation
    	        requestMessage = new WSDLMessage("purchaseSubmission");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","purchaseSubmission"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","purchaseSubmission")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","purchaseSubmission");
                
                responseMessage = new WSDLMessage("purchaseSubmissionResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","purchaseSubmissionResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","purchaseSubmissionResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","purchaseSubmission");purchaseSubmission.inputMessage = requestMessage;
	        purchaseSubmission.outputMessage = responseMessage;
            purchaseSubmission.schemaManager = this.schemaMgr;
            purchaseSubmission.soapAction = "\"\"";
            purchaseSubmission.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(purchaseSubmission);//define the WSDLOperation: new WSDLOperation(methodName)
            var removeContestPayment:WSDLOperation = new WSDLOperation("removeContestPayment");
				//input message for the operation
    	        requestMessage = new WSDLMessage("removeContestPayment");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","removeContestPayment"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","removeContestPayment")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","removeContestPayment");
                
                responseMessage = new WSDLMessage("removeContestPaymentResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","removeContestPaymentResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","removeContestPaymentResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","removeContestPayment");removeContestPayment.inputMessage = requestMessage;
	        removeContestPayment.outputMessage = responseMessage;
            removeContestPayment.schemaManager = this.schemaMgr;
            removeContestPayment.soapAction = "\"\"";
            removeContestPayment.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(removeContestPayment);//define the WSDLOperation: new WSDLOperation(methodName)
            var removeDocument:WSDLOperation = new WSDLOperation("removeDocument");
				//input message for the operation
    	        requestMessage = new WSDLMessage("removeDocument");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","removeDocument"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","removeDocument")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","removeDocument");
                
                responseMessage = new WSDLMessage("removeDocumentResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","removeDocumentResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","removeDocumentResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","removeDocument");removeDocument.inputMessage = requestMessage;
	        removeDocument.outputMessage = responseMessage;
            removeDocument.schemaManager = this.schemaMgr;
            removeDocument.soapAction = "\"\"";
            removeDocument.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(removeDocument);//define the WSDLOperation: new WSDLOperation(methodName)
            var removeDocumentFromContest:WSDLOperation = new WSDLOperation("removeDocumentFromContest");
				//input message for the operation
    	        requestMessage = new WSDLMessage("removeDocumentFromContest");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","removeDocumentFromContest"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","removeDocumentFromContest")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","removeDocumentFromContest");
                
                responseMessage = new WSDLMessage("removeDocumentFromContestResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","removeDocumentFromContestResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","removeDocumentFromContestResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","removeDocumentFromContest");removeDocumentFromContest.inputMessage = requestMessage;
	        removeDocumentFromContest.outputMessage = responseMessage;
            removeDocumentFromContest.schemaManager = this.schemaMgr;
            removeDocumentFromContest.soapAction = "\"\"";
            removeDocumentFromContest.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(removeDocumentFromContest);//define the WSDLOperation: new WSDLOperation(methodName)
            var removeSubmission:WSDLOperation = new WSDLOperation("removeSubmission");
				//input message for the operation
    	        requestMessage = new WSDLMessage("removeSubmission");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","removeSubmission"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","removeSubmission")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","removeSubmission");
                
                responseMessage = new WSDLMessage("removeSubmissionResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","removeSubmissionResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","removeSubmissionResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","removeSubmission");removeSubmission.inputMessage = requestMessage;
	        removeSubmission.outputMessage = responseMessage;
            removeSubmission.schemaManager = this.schemaMgr;
            removeSubmission.soapAction = "\"\"";
            removeSubmission.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(removeSubmission);//define the WSDLOperation: new WSDLOperation(methodName)
            var retrieveAllSubmissionsByMember:WSDLOperation = new WSDLOperation("retrieveAllSubmissionsByMember");
				//input message for the operation
    	        requestMessage = new WSDLMessage("retrieveAllSubmissionsByMember");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","retrieveAllSubmissionsByMember"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","retrieveAllSubmissionsByMember")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","retrieveAllSubmissionsByMember");
                
                responseMessage = new WSDLMessage("retrieveAllSubmissionsByMemberResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","retrieveAllSubmissionsByMemberResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","retrieveAllSubmissionsByMemberResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","retrieveAllSubmissionsByMember");retrieveAllSubmissionsByMember.inputMessage = requestMessage;
	        retrieveAllSubmissionsByMember.outputMessage = responseMessage;
            retrieveAllSubmissionsByMember.schemaManager = this.schemaMgr;
            retrieveAllSubmissionsByMember.soapAction = "\"\"";
            retrieveAllSubmissionsByMember.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(retrieveAllSubmissionsByMember);//define the WSDLOperation: new WSDLOperation(methodName)
            var retrieveSubmission:WSDLOperation = new WSDLOperation("retrieveSubmission");
				//input message for the operation
    	        requestMessage = new WSDLMessage("retrieveSubmission");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","retrieveSubmission"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","retrieveSubmission")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","retrieveSubmission");
                
                responseMessage = new WSDLMessage("retrieveSubmissionResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","retrieveSubmissionResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","retrieveSubmissionResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","retrieveSubmission");retrieveSubmission.inputMessage = requestMessage;
	        retrieveSubmission.outputMessage = responseMessage;
            retrieveSubmission.schemaManager = this.schemaMgr;
            retrieveSubmission.soapAction = "\"\"";
            retrieveSubmission.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(retrieveSubmission);//define the WSDLOperation: new WSDLOperation(methodName)
            var retrieveSubmissionsForContest:WSDLOperation = new WSDLOperation("retrieveSubmissionsForContest");
				//input message for the operation
    	        requestMessage = new WSDLMessage("retrieveSubmissionsForContest");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","retrieveSubmissionsForContest"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","retrieveSubmissionsForContest")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","retrieveSubmissionsForContest");
                
                responseMessage = new WSDLMessage("retrieveSubmissionsForContestResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","retrieveSubmissionsForContestResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","retrieveSubmissionsForContestResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","retrieveSubmissionsForContest");retrieveSubmissionsForContest.inputMessage = requestMessage;
	        retrieveSubmissionsForContest.outputMessage = responseMessage;
            retrieveSubmissionsForContest.schemaManager = this.schemaMgr;
            retrieveSubmissionsForContest.soapAction = "\"\"";
            retrieveSubmissionsForContest.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(retrieveSubmissionsForContest);//define the WSDLOperation: new WSDLOperation(methodName)
            var searchContests:WSDLOperation = new WSDLOperation("searchContests");
				//input message for the operation
    	        requestMessage = new WSDLMessage("searchContests");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","searchContests"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","searchContests")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","searchContests");
                
                responseMessage = new WSDLMessage("searchContestsResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","searchContestsResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","searchContestsResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","searchContests");searchContests.inputMessage = requestMessage;
	        searchContests.outputMessage = responseMessage;
            searchContests.schemaManager = this.schemaMgr;
            searchContests.soapAction = "\"\"";
            searchContests.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(searchContests);//define the WSDLOperation: new WSDLOperation(methodName)
            var setSubmissionPlacement:WSDLOperation = new WSDLOperation("setSubmissionPlacement");
				//input message for the operation
    	        requestMessage = new WSDLMessage("setSubmissionPlacement");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","setSubmissionPlacement"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","setSubmissionPlacement")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","setSubmissionPlacement");
                
                responseMessage = new WSDLMessage("setSubmissionPlacementResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","setSubmissionPlacementResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","setSubmissionPlacementResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","setSubmissionPlacement");setSubmissionPlacement.inputMessage = requestMessage;
	        setSubmissionPlacement.outputMessage = responseMessage;
            setSubmissionPlacement.schemaManager = this.schemaMgr;
            setSubmissionPlacement.soapAction = "\"\"";
            setSubmissionPlacement.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(setSubmissionPlacement);//define the WSDLOperation: new WSDLOperation(methodName)
            var setSubmissionPrize:WSDLOperation = new WSDLOperation("setSubmissionPrize");
				//input message for the operation
    	        requestMessage = new WSDLMessage("setSubmissionPrize");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","setSubmissionPrize"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","setSubmissionPrize")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","setSubmissionPrize");
                
                responseMessage = new WSDLMessage("setSubmissionPrizeResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","setSubmissionPrizeResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","setSubmissionPrizeResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","setSubmissionPrize");setSubmissionPrize.inputMessage = requestMessage;
	        setSubmissionPrize.outputMessage = responseMessage;
            setSubmissionPrize.schemaManager = this.schemaMgr;
            setSubmissionPrize.soapAction = "\"\"";
            setSubmissionPrize.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(setSubmissionPrize);//define the WSDLOperation: new WSDLOperation(methodName)
            var updateContest:WSDLOperation = new WSDLOperation("updateContest");
				//input message for the operation
    	        requestMessage = new WSDLMessage("updateContest");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","updateContest"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","updateContest")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","updateContest");
                
                responseMessage = new WSDLMessage("updateContestResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","updateContestResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","updateContestResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","updateContest");updateContest.inputMessage = requestMessage;
	        updateContest.outputMessage = responseMessage;
            updateContest.schemaManager = this.schemaMgr;
            updateContest.soapAction = "\"\"";
            updateContest.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(updateContest);//define the WSDLOperation: new WSDLOperation(methodName)
            var updateContestStatus:WSDLOperation = new WSDLOperation("updateContestStatus");
				//input message for the operation
    	        requestMessage = new WSDLMessage("updateContestStatus");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","updateContestStatus"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","updateContestStatus")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","updateContestStatus");
                
                responseMessage = new WSDLMessage("updateContestStatusResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","updateContestStatusResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","updateContestStatusResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","updateContestStatus");updateContestStatus.inputMessage = requestMessage;
	        updateContestStatus.outputMessage = responseMessage;
            updateContestStatus.schemaManager = this.schemaMgr;
            updateContestStatus.soapAction = "\"\"";
            updateContestStatus.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(updateContestStatus);//define the WSDLOperation: new WSDLOperation(methodName)
            var updateSubmission:WSDLOperation = new WSDLOperation("updateSubmission");
				//input message for the operation
    	        requestMessage = new WSDLMessage("updateSubmission");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","updateSubmission"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","updateSubmission")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","updateSubmission");
                
                responseMessage = new WSDLMessage("updateSubmissionResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","updateSubmissionResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","updateSubmissionResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","updateSubmission");updateSubmission.inputMessage = requestMessage;
	        updateSubmission.outputMessage = responseMessage;
            updateSubmission.schemaManager = this.schemaMgr;
            updateSubmission.soapAction = "\"\"";
            updateSubmission.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(updateSubmission);//define the WSDLOperation: new WSDLOperation(methodName)
            var uploadDocument:WSDLOperation = new WSDLOperation("uploadDocument");
				//input message for the operation
    	        requestMessage = new WSDLMessage("uploadDocument");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","uploadDocument"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","uploadDocument")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","uploadDocument");
                
                responseMessage = new WSDLMessage("uploadDocumentResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","uploadDocumentResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","uploadDocumentResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","uploadDocument");uploadDocument.inputMessage = requestMessage;
	        uploadDocument.outputMessage = responseMessage;
            uploadDocument.schemaManager = this.schemaMgr;
            uploadDocument.soapAction = "\"\"";
            uploadDocument.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(uploadDocument);//define the WSDLOperation: new WSDLOperation(methodName)
            var uploadDocumentForContest:WSDLOperation = new WSDLOperation("uploadDocumentForContest");
				//input message for the operation
    	        requestMessage = new WSDLMessage("uploadDocumentForContest");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","uploadDocumentForContest"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","uploadDocumentForContest")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","uploadDocumentForContest");
                
                responseMessage = new WSDLMessage("uploadDocumentForContestResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.contest.facade.service.topcoder.com/","uploadDocumentForContestResponse"),null,new QName("http://ejb.contest.facade.service.topcoder.com/","uploadDocumentForContestResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.contest.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://ejb.contest.facade.service.topcoder.com/","uploadDocumentForContest");uploadDocumentForContest.inputMessage = requestMessage;
	        uploadDocumentForContest.outputMessage = responseMessage;
            uploadDocumentForContest.schemaManager = this.schemaMgr;
            uploadDocumentForContest.soapAction = "\"\"";
            uploadDocumentForContest.style = "document";
            BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.addOperation(uploadDocumentForContest);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","processContestPayment_old"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.ProcessContestPayment_old);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getContestDataOnlyByPID"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetContestDataOnlyByPID);
							SchemaTypeRegistry.getInstance().registerCollectionClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getAllContestHeadersResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetAllContestHeadersResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","setSubmissionPrizeResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.SetSubmissionPrizeResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","projectData"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.ProjectData);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","contestServiceFilter"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.ContestServiceFilter);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","processSubmissionPayment"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.ProcessSubmissionPayment);
							SchemaTypeRegistry.getInstance().registerCollectionClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getLatestChangesResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetLatestChangesResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","removeDocumentFromContest"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.RemoveDocumentFromContest);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getAllStudioFileTypes"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetAllStudioFileTypes);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","deleteContestResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.DeleteContestResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","mediumData"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.MediumData);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","paymentType"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.PaymentType);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","studioCompetition"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.StudioCompetition);
							SchemaTypeRegistry.getInstance().registerCollectionClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleProjectContestDataResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetSimpleProjectContestDataResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","retrieveSubmissionResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.RetrieveSubmissionResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getContestDataOnly"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetContestDataOnly);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","contestStatusData"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.ContestStatusData);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getContestsForProject"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetContestsForProject);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","purchaseSubmission"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.PurchaseSubmission);
							SchemaTypeRegistry.getInstance().registerCollectionClass(new QName("http://ejb.contest.facade.service.topcoder.com/","addChangeHistory"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.AddChangeHistory);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","createContestPayment"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.CreateContestPayment);
							SchemaTypeRegistry.getInstance().registerCollectionClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getContestDataOnlyByPIDResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetContestDataOnlyByPIDResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","documentType"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.DocumentType);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getSubmissionFileTypes"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetSubmissionFileTypes);
							SchemaTypeRegistry.getInstance().registerCollectionClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleProjectContestDataByPIDResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetSimpleProjectContestDataByPIDResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getMimeTypeIdResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetMimeTypeIdResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","createContestResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.CreateContestResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","updateContest"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.UpdateContest);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","retrieveSubmissionsForContest"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.RetrieveSubmissionsForContest);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","editContestPaymentResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.EditContestPaymentResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","editContestPayment"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.EditContestPayment);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","do_2Response"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.Do_2Response);
							SchemaTypeRegistry.getInstance().registerCollectionClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getAllContestsResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetAllContestsResponse);
							SchemaTypeRegistry.getInstance().registerCollectionClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getContestsForProjectResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetContestsForProjectResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","paymentData"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.PaymentData);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","prizeData"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.PrizeData);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","createContest"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.CreateContest);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","uploadDocument"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.UploadDocument);
							SchemaTypeRegistry.getInstance().registerCollectionClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getContestDataOnlyResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetContestDataOnlyResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","simpleProjectContestData"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.SimpleProjectContestData);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","simpleContestData"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.SimpleContestData);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","updateSubmission"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.UpdateSubmission);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getContestsForClient"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetContestsForClient);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","do_3Response"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.Do_3Response);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","do_3"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.Do_3);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","uploadDocumentResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.UploadDocumentResponse);
							SchemaTypeRegistry.getInstance().registerCollectionClass(new QName("http://ejb.contest.facade.service.topcoder.com/","searchContestsResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.SearchContestsResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","changeHistoryData"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.ChangeHistoryData);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getContestPayment"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetContestPayment);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getMimeTypeId"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetMimeTypeId);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","removeSubmission"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.RemoveSubmission);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","retrieveAllSubmissionsByMember"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.RetrieveAllSubmissionsByMember);
							SchemaTypeRegistry.getInstance().registerCollectionClass(new QName("http://ejb.contest.facade.service.topcoder.com/","retrieveSubmissionsForContestResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.RetrieveSubmissionsForContestResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","contestData"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.ContestData);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","addDocumentToContest"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.AddDocumentToContest);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","setSubmissionPrize"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.SetSubmissionPrize);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","removeContestPayment"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.RemoveContestPayment);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","competition"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.Competition);
							SchemaTypeRegistry.getInstance().registerCollectionClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getAllDocumentTypesResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetAllDocumentTypesResponse);
							SchemaTypeRegistry.getInstance().registerCollectionClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getAllContestTypesResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetAllContestTypesResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","purchaseSubmissionResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.PurchaseSubmissionResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","removeDocumentResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.RemoveDocumentResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","retrieveSubmission"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.RetrieveSubmission);
							SchemaTypeRegistry.getInstance().registerCollectionClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getContestsForClientResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetContestsForClientResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getChangeHistory"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetChangeHistory);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getContest"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetContest);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","removeDocument"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.RemoveDocument);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getContestPaymentResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetContestPaymentResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","studioFileType"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.StudioFileType);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getSubmissionFileTypesResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetSubmissionFileTypesResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","setSubmissionPlacement"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.SetSubmissionPlacement);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleContestDataByPID"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetSimpleContestDataByPID);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getAllMediums"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetAllMediums);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getContestResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetContestResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","removeSubmissionResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.RemoveSubmissionResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","processContestPaymentResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.ProcessContestPaymentResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","submissionData"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.SubmissionData);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","removeDocumentFromContestResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.RemoveDocumentFromContestResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","createContestPaymentResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.CreateContestPaymentResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","markForPurchase"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.MarkForPurchase);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","deleteContest"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.DeleteContest);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","markForPurchaseResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.MarkForPurchaseResponse);
							SchemaTypeRegistry.getInstance().registerCollectionClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleContestDataByPIDResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetSimpleContestDataByPIDResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","competitionPrize"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.CompetitionPrize);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","mimeType"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.MimeType);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","updateContestStatusResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.UpdateContestStatusResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","updateContestResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.UpdateContestResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","processContestPayment_oldResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.ProcessContestPayment_oldResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","addChangeHistoryResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.AddChangeHistoryResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","contestPayload"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.ContestPayload);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","processMissingPaymentsResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.ProcessMissingPaymentsResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","do_1Response"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.Do_1Response);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","tcPurhcaseOrderPaymentData"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.TcPurhcaseOrderPaymentData);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getAllContestHeaders"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetAllContestHeaders);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleProjectContestDataByPID"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetSimpleProjectContestDataByPID);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","do_2"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.Do_2);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","updateContestStatus"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.UpdateContestStatus);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","addDocumentToContestResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.AddDocumentToContestResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","contestTypeData"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.ContestTypeData);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getLatestChanges"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetLatestChanges);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","do_1"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.Do_1);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","creditCardPaymentData"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.CreditCardPaymentData);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleProjectContestData"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetSimpleProjectContestData);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getAllContests"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetAllContests);
							SchemaTypeRegistry.getInstance().registerCollectionClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getStatusListResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetStatusListResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","contestPaymentData"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.ContestPaymentData);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getAllContestTypes"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetAllContestTypes);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleContestData"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetSimpleContestData);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","competionType"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.CompetionType);
							SchemaTypeRegistry.getInstance().registerCollectionClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getChangeHistoryResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetChangeHistoryResponse);
							SchemaTypeRegistry.getInstance().registerCollectionClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getAllMediumsResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetAllMediumsResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","processSubmissionPaymentResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.ProcessSubmissionPaymentResponse);
							SchemaTypeRegistry.getInstance().registerCollectionClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getSimpleContestDataResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetSimpleContestDataResponse);
							SchemaTypeRegistry.getInstance().registerCollectionClass(new QName("http://ejb.contest.facade.service.topcoder.com/","retrieveAllSubmissionsByMemberResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.RetrieveAllSubmissionsByMemberResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","setSubmissionPlacementResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.SetSubmissionPlacementResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","paymentResult"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.PaymentResult);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getStatusList"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetStatusList);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","project"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.Project);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","processContestPayment"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.ProcessContestPayment);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getAllDocumentTypes"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetAllDocumentTypes);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","processMissingPayments"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.ProcessMissingPayments);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","updateSubmissionResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.UpdateSubmissionResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","searchContests"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.SearchContests);
							SchemaTypeRegistry.getInstance().registerCollectionClass(new QName("http://ejb.contest.facade.service.topcoder.com/","getAllStudioFileTypesResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetAllStudioFileTypesResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","uploadDocumentForContest"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.UploadDocumentForContest);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","uploadDocumentForContestResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.UploadDocumentForContestResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","removeContestPaymentResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.RemoveContestPaymentResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","PaymentException"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.PaymentException);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.contest.facade.service.topcoder.com/","uploadedDocument"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.UploadedDocument);}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param addChangeHistory
		 * @return Asynchronous token
		 */
		public function addChangeHistory(addChangeHistory:AddChangeHistory):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["addChangeHistory"] = addChangeHistory;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("addChangeHistory");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param addDocumentToContest
		 * @return Asynchronous token
		 */
		public function addDocumentToContest(addDocumentToContest:AddDocumentToContest):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["addDocumentToContest"] = addDocumentToContest;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("addDocumentToContest");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param createContest
		 * @return Asynchronous token
		 */
		public function createContest(createContest:CreateContest):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["createContest"] = createContest;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("createContest");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param createContestPayment
		 * @return Asynchronous token
		 */
		public function createContestPayment(createContestPayment:CreateContestPayment):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["createContestPayment"] = createContestPayment;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("createContestPayment");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param deleteContest
		 * @return Asynchronous token
		 */
		public function deleteContest(deleteContest:DeleteContest):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["deleteContest"] = deleteContest;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("deleteContest");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param do_1
		 * @return Asynchronous token
		 */
		public function do_1(do_1:Do_1):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["do_1"] = do_1;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("do_1");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param do_2
		 * @return Asynchronous token
		 */
		public function do_2(do_2:Do_2):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["do_2"] = do_2;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("do_2");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param do_3
		 * @return Asynchronous token
		 */
		public function do_3(do_3:Do_3):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["do_3"] = do_3;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("do_3");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param editContestPayment
		 * @return Asynchronous token
		 */
		public function editContestPayment(editContestPayment:EditContestPayment):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["editContestPayment"] = editContestPayment;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("editContestPayment");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param getAllContestHeaders
		 * @return Asynchronous token
		 */
		public function getAllContestHeaders(getAllContestHeaders:GetAllContestHeaders):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["getAllContestHeaders"] = getAllContestHeaders;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("getAllContestHeaders");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param getAllContestTypes
		 * @return Asynchronous token
		 */
		public function getAllContestTypes(getAllContestTypes:GetAllContestTypes):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["getAllContestTypes"] = getAllContestTypes;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("getAllContestTypes");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param getAllContests
		 * @return Asynchronous token
		 */
		public function getAllContests(getAllContests:GetAllContests):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["getAllContests"] = getAllContests;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("getAllContests");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param getAllDocumentTypes
		 * @return Asynchronous token
		 */
		public function getAllDocumentTypes(getAllDocumentTypes:GetAllDocumentTypes):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["getAllDocumentTypes"] = getAllDocumentTypes;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("getAllDocumentTypes");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param getAllMediums
		 * @return Asynchronous token
		 */
		public function getAllMediums(getAllMediums:GetAllMediums):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["getAllMediums"] = getAllMediums;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("getAllMediums");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param getAllStudioFileTypes
		 * @return Asynchronous token
		 */
		public function getAllStudioFileTypes(getAllStudioFileTypes:GetAllStudioFileTypes):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["getAllStudioFileTypes"] = getAllStudioFileTypes;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("getAllStudioFileTypes");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param getChangeHistory
		 * @return Asynchronous token
		 */
		public function getChangeHistory(getChangeHistory:GetChangeHistory):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["getChangeHistory"] = getChangeHistory;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("getChangeHistory");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param getContest
		 * @return Asynchronous token
		 */
		public function getContest(getContest:GetContest):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["getContest"] = getContest;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("getContest");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param getContestDataOnly
		 * @return Asynchronous token
		 */
		public function getContestDataOnly(getContestDataOnly:GetContestDataOnly):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["getContestDataOnly"] = getContestDataOnly;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("getContestDataOnly");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param getContestDataOnlyByPID
		 * @return Asynchronous token
		 */
		public function getContestDataOnlyByPID(getContestDataOnlyByPID:GetContestDataOnlyByPID):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["getContestDataOnlyByPID"] = getContestDataOnlyByPID;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("getContestDataOnlyByPID");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param getContestPayment
		 * @return Asynchronous token
		 */
		public function getContestPayment(getContestPayment:GetContestPayment):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["getContestPayment"] = getContestPayment;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("getContestPayment");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param getContestsForClient
		 * @return Asynchronous token
		 */
		public function getContestsForClient(getContestsForClient:GetContestsForClient):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["getContestsForClient"] = getContestsForClient;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("getContestsForClient");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param getContestsForProject
		 * @return Asynchronous token
		 */
		public function getContestsForProject(getContestsForProject:GetContestsForProject):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["getContestsForProject"] = getContestsForProject;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("getContestsForProject");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param getLatestChanges
		 * @return Asynchronous token
		 */
		public function getLatestChanges(getLatestChanges:GetLatestChanges):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["getLatestChanges"] = getLatestChanges;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("getLatestChanges");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param getMimeTypeId
		 * @return Asynchronous token
		 */
		public function getMimeTypeId(getMimeTypeId:GetMimeTypeId):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["getMimeTypeId"] = getMimeTypeId;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("getMimeTypeId");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param getSimpleContestData
		 * @return Asynchronous token
		 */
		public function getSimpleContestData(getSimpleContestData:GetSimpleContestData):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["getSimpleContestData"] = getSimpleContestData;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("getSimpleContestData");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param getSimpleContestDataByPID
		 * @return Asynchronous token
		 */
		public function getSimpleContestDataByPID(getSimpleContestDataByPID:GetSimpleContestDataByPID):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["getSimpleContestDataByPID"] = getSimpleContestDataByPID;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("getSimpleContestDataByPID");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param getSimpleProjectContestData
		 * @return Asynchronous token
		 */
		public function getSimpleProjectContestData(getSimpleProjectContestData:GetSimpleProjectContestData):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["getSimpleProjectContestData"] = getSimpleProjectContestData;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("getSimpleProjectContestData");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param getSimpleProjectContestDataByPID
		 * @return Asynchronous token
		 */
		public function getSimpleProjectContestDataByPID(getSimpleProjectContestDataByPID:GetSimpleProjectContestDataByPID):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["getSimpleProjectContestDataByPID"] = getSimpleProjectContestDataByPID;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("getSimpleProjectContestDataByPID");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param getStatusList
		 * @return Asynchronous token
		 */
		public function getStatusList(getStatusList:GetStatusList):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["getStatusList"] = getStatusList;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("getStatusList");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param getSubmissionFileTypes
		 * @return Asynchronous token
		 */
		public function getSubmissionFileTypes(getSubmissionFileTypes:GetSubmissionFileTypes):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["getSubmissionFileTypes"] = getSubmissionFileTypes;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("getSubmissionFileTypes");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param markForPurchase
		 * @return Asynchronous token
		 */
		public function markForPurchase(markForPurchase:MarkForPurchase):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["markForPurchase"] = markForPurchase;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("markForPurchase");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param processContestPayment
		 * @return Asynchronous token
		 */
		public function processContestPayment(processContestPayment:ProcessContestPayment):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["processContestPayment"] = processContestPayment;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("processContestPayment");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param processContestPayment_old
		 * @return Asynchronous token
		 */
		public function processContestPayment_old(processContestPayment_old:ProcessContestPayment_old):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["processContestPayment_old"] = processContestPayment_old;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("processContestPayment_old");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param processMissingPayments
		 * @return Asynchronous token
		 */
		public function processMissingPayments(processMissingPayments:ProcessMissingPayments):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["processMissingPayments"] = processMissingPayments;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("processMissingPayments");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param processSubmissionPayment
		 * @return Asynchronous token
		 */
		public function processSubmissionPayment(processSubmissionPayment:ProcessSubmissionPayment):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["processSubmissionPayment"] = processSubmissionPayment;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("processSubmissionPayment");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param purchaseSubmission
		 * @return Asynchronous token
		 */
		public function purchaseSubmission(purchaseSubmission:PurchaseSubmission):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["purchaseSubmission"] = purchaseSubmission;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("purchaseSubmission");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param removeContestPayment
		 * @return Asynchronous token
		 */
		public function removeContestPayment(removeContestPayment:RemoveContestPayment):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["removeContestPayment"] = removeContestPayment;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("removeContestPayment");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param removeDocument
		 * @return Asynchronous token
		 */
		public function removeDocument(removeDocument:RemoveDocument):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["removeDocument"] = removeDocument;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("removeDocument");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param removeDocumentFromContest
		 * @return Asynchronous token
		 */
		public function removeDocumentFromContest(removeDocumentFromContest:RemoveDocumentFromContest):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["removeDocumentFromContest"] = removeDocumentFromContest;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("removeDocumentFromContest");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param removeSubmission
		 * @return Asynchronous token
		 */
		public function removeSubmission(removeSubmission:RemoveSubmission):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["removeSubmission"] = removeSubmission;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("removeSubmission");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param retrieveAllSubmissionsByMember
		 * @return Asynchronous token
		 */
		public function retrieveAllSubmissionsByMember(retrieveAllSubmissionsByMember:RetrieveAllSubmissionsByMember):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["retrieveAllSubmissionsByMember"] = retrieveAllSubmissionsByMember;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("retrieveAllSubmissionsByMember");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param retrieveSubmission
		 * @return Asynchronous token
		 */
		public function retrieveSubmission(retrieveSubmission:RetrieveSubmission):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["retrieveSubmission"] = retrieveSubmission;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("retrieveSubmission");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param retrieveSubmissionsForContest
		 * @return Asynchronous token
		 */
		public function retrieveSubmissionsForContest(retrieveSubmissionsForContest:RetrieveSubmissionsForContest):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["retrieveSubmissionsForContest"] = retrieveSubmissionsForContest;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("retrieveSubmissionsForContest");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param searchContests
		 * @return Asynchronous token
		 */
		public function searchContests(searchContests:SearchContests):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["searchContests"] = searchContests;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("searchContests");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param setSubmissionPlacement
		 * @return Asynchronous token
		 */
		public function setSubmissionPlacement(setSubmissionPlacement:SetSubmissionPlacement):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["setSubmissionPlacement"] = setSubmissionPlacement;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("setSubmissionPlacement");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param setSubmissionPrize
		 * @return Asynchronous token
		 */
		public function setSubmissionPrize(setSubmissionPrize:SetSubmissionPrize):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["setSubmissionPrize"] = setSubmissionPrize;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("setSubmissionPrize");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param updateContest
		 * @return Asynchronous token
		 */
		public function updateContest(updateContest:UpdateContest):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["updateContest"] = updateContest;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("updateContest");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param updateContestStatus
		 * @return Asynchronous token
		 */
		public function updateContestStatus(updateContestStatus:UpdateContestStatus):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["updateContestStatus"] = updateContestStatus;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("updateContestStatus");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param updateSubmission
		 * @return Asynchronous token
		 */
		public function updateSubmission(updateSubmission:UpdateSubmission):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["updateSubmission"] = updateSubmission;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("updateSubmission");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param uploadDocument
		 * @return Asynchronous token
		 */
		public function uploadDocument(uploadDocument:UploadDocument):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["uploadDocument"] = uploadDocument;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("uploadDocument");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param uploadDocumentForContest
		 * @return Asynchronous token
		 */
		public function uploadDocumentForContest(uploadDocumentForContest:UploadDocumentForContest):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["uploadDocumentForContest"] = uploadDocumentForContest;
	            currentOperation = BaseContestServiceFacadeBeanServiceService.getPort("BaseContestServiceFacadeBeanServicePort").binding.portType.getOperation("uploadDocumentForContest");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
        /**
         * Performs the actual call to the remove server
         * It SOAP-encodes the message using the schema and WSDL operation options set above and then calls the server using 
         * an async invoker
         * It also registers internal event handlers for the result / fault cases
         * @private
         */
        private function call(operation:WSDLOperation,args:Object,token:AsyncToken,headers:Array=null):void
        {
	    	var enc:SOAPEncoder = new SOAPEncoder();
	        //enc.strictNillability = true;//FIX for xsi:nil="true"
	        var soap:Object = new Object;
	        var message:SOAPMessage = new SOAPMessage();
	        enc.wsdlOperation = operation;
	        soap = enc.encodeRequest(args,headers.concat(this.headers)); //FIX for not adding security headers
	        message.setSOAPAction(operation.soapAction);
	        message.body = soap.toString();
	        
	        trace("Message body: " + message.body);
	        
	        message.url=endpointURI;
            var inv:AsyncRequest = new AsyncRequest();
            inv.destination = super.destination;
            //we need this to handle multiple asynchronous calls 
            var wrappedData:Object = new Object();
            wrappedData.operation = currentOperation;
            wrappedData.returnToken = token;
            if(!this.useProxy)
            {
            	var dcs:ChannelSet = new ChannelSet();	
	        	dcs.addChannel(new DirectHTTPChannel("direct_http_channel"));
            	inv.channelSet = dcs;
            }                
            var processRes:AsyncResponder = new AsyncResponder(processResult,faultResult,wrappedData);
            inv.invoke(message,processRes);
		}
        
        /**
         * Internal event handler to process a successful operation call from the server
         * The result is decoded using the schema and operation settings and then the events get passed on to the actual facade that the user employs in the application 
         * @private
         */
		private function processResult(result:Object,wrappedData:Object):void
           {
           		var headers:Object;
           		var token:AsyncToken = wrappedData.returnToken;
                var currentOperation:WSDLOperation = wrappedData.operation;
                var decoder:SOAPDecoder = new SOAPDecoder();
                decoder.resultFormat = "object";
                decoder.headerFormat = "object";
                decoder.multiplePartsFormat = "object";
                decoder.ignoreWhitespace = true;
                decoder.makeObjectsBindable=false;
                decoder.wsdlOperation = currentOperation;
                decoder.schemaManager = currentOperation.schemaManager;
                var body:Object = result.message.body;
                var stringResult:String = String(body);
                if(stringResult == null  || stringResult == "")
                	return;
                var soapResult:SOAPResult = decoder.decodeResponse(result.message.body);
                if(soapResult.isFault)
                {
	                var faults:Array = soapResult.result as Array;
	                for each (var soapFault:Fault in faults)
	                {
		                var soapFaultEvent:FaultEvent = FaultEvent.createEvent(soapFault,token,null);
		                token.dispatchEvent(soapFaultEvent);
	                }
                } else {
	                result = soapResult.result;
	                headers = soapResult.headers;
	                var event:ResultEvent = ResultEvent.createEvent(result,token,null);
	                event.headers = headers;
	                token.dispatchEvent(event);
                }
           }
           	/**
           	 * Handles the cases when there are errors calling the operation on the server
           	 * This is not the case for SOAP faults, which is handled by the SOAP decoder in the result handler
           	 * but more critical errors, like network outage or the impossibility to connect to the server
           	 * The fault is dispatched upwards to the facade so that the user can do something meaningful 
           	 * @private
           	 */
			private function faultResult(error:MessageFaultEvent,token:Object):void
			{
				//when there is a network error the token is actually the wrappedData object from above	
				if(!(token is AsyncToken))
					token = token.returnToken;
				token.dispatchEvent(new FaultEvent(FaultEvent.FAULT,true,true,new Fault(error.faultCode,error.faultString,error.faultDetail)));
			}
		}
	}

	import mx.rpc.AsyncToken;
	import mx.rpc.AsyncResponder;
	import mx.rpc.wsdl.WSDLBinding;
                
    /**
     * Internal class to handle multiple operation call scheduling
     * It allows us to pass data about the operation being encoded / decoded to and from the SOAP encoder / decoder units. 
     * @private
     */
    class PendingCall
    {
		public var args:*;
		public var headers:Array;
		public var token:AsyncToken;
		
		public function PendingCall(args:Object, headers:Array=null)
		{
			this.args = args;
			this.headers = headers;
			this.token = new AsyncToken(null);
		}
	}