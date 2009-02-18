/**
 * BaseProjectServiceFacadeBeanServiceService.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated
{
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
	public class BaseProjectServiceFacadeBeanService extends AbstractWebService
    {
		private var results:Object;
		private var schemaMgr:SchemaManager;
		private var BaseProjectServiceFacadeBeanServiceService:WSDLService;
		private var BaseProjectServiceFacadeBeanServicePortType:WSDLPortType;
		private var BaseProjectServiceFacadeBeanServiceBinding:WSDLBinding;
		private var BaseProjectServiceFacadeBeanServicePort:WSDLPort;
		private var currentOperation:WSDLOperation;
		private var internal_schema:BaseProjectServiceFacadeBeanServiceSchema;
	
		/**
		 * Constructor for the base service, initializes all of the WSDL's properties
		 * @param [Optional] The LCDS destination (if available) to use to contact the server
		 * @param [Optional] The URL to the WSDL end-point
		 */
		public function BaseProjectServiceFacadeBeanService(destination:String=null, rootURL:String=null)
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
			internal_schema = new BaseProjectServiceFacadeBeanServiceSchema();
			schemaMgr = new SchemaManager();
			for(var i:int;i<internal_schema.schemas.length;i++)
			{
				internal_schema.schemas[i].targetNamespace=internal_schema.targetNamespaces[i];
				schemaMgr.addSchema(internal_schema.schemas[i]);
			}
BaseProjectServiceFacadeBeanServiceService = new WSDLService("BaseProjectServiceFacadeBeanServiceService");
			BaseProjectServiceFacadeBeanServicePort = new WSDLPort("BaseProjectServiceFacadeBeanServicePort",BaseProjectServiceFacadeBeanServiceService);
        	BaseProjectServiceFacadeBeanServiceBinding = new WSDLBinding("BaseProjectServiceFacadeBeanServiceBinding");
	        BaseProjectServiceFacadeBeanServicePortType = new WSDLPortType("BaseProjectServiceFacadeBeanServicePortType");
       		BaseProjectServiceFacadeBeanServiceBinding.portType = BaseProjectServiceFacadeBeanServicePortType;
       		BaseProjectServiceFacadeBeanServicePort.binding = BaseProjectServiceFacadeBeanServiceBinding;
       		BaseProjectServiceFacadeBeanServiceService.addPort(BaseProjectServiceFacadeBeanServicePort);
       		BaseProjectServiceFacadeBeanServicePort.endpointURI = "http://75.101.181.246:8003/services-project_service_facade/ProjectServiceFacadeBean";
       		if(this.endpointURI == null)
       		{
       			this.endpointURI = BaseProjectServiceFacadeBeanServicePort.endpointURI; 
       		} 
       		
			var requestMessage:WSDLMessage;
			var responseMessage:WSDLMessage;
			//define the WSDLOperation: new WSDLOperation(methodName)
            var createProject:WSDLOperation = new WSDLOperation("createProject");
				//input message for the operation
    	        requestMessage = new WSDLMessage("createProject");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.project.facade.service.topcoder.com/","createProject"),null,new QName("http://ejb.project.facade.service.topcoder.com/","createProject")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.project.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
                
                responseMessage = new WSDLMessage("createProjectResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.project.facade.service.topcoder.com/","createProjectResponse"),null,new QName("http://ejb.project.facade.service.topcoder.com/","createProjectResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.project.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				createProject.inputMessage = requestMessage;
	        createProject.outputMessage = responseMessage;
            createProject.schemaManager = this.schemaMgr;
            createProject.soapAction = "\"\"";
            createProject.style = "document";
            BaseProjectServiceFacadeBeanServiceService.getPort("BaseProjectServiceFacadeBeanServicePort").binding.portType.addOperation(createProject);
			//define the WSDLOperation: new WSDLOperation(methodName)
            var deleteProject:WSDLOperation = new WSDLOperation("deleteProject");
				//input message for the operation
    	        requestMessage = new WSDLMessage("deleteProject");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.project.facade.service.topcoder.com/","deleteProject"),null,new QName("http://ejb.project.facade.service.topcoder.com/","deleteProject")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.project.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
                
                responseMessage = new WSDLMessage("deleteProjectResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.project.facade.service.topcoder.com/","deleteProjectResponse"),null,new QName("http://ejb.project.facade.service.topcoder.com/","deleteProjectResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.project.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				deleteProject.inputMessage = requestMessage;
	        deleteProject.outputMessage = responseMessage;
            deleteProject.schemaManager = this.schemaMgr;
            deleteProject.soapAction = "\"\"";
            deleteProject.style = "document";
            BaseProjectServiceFacadeBeanServiceService.getPort("BaseProjectServiceFacadeBeanServicePort").binding.portType.addOperation(deleteProject);
			//define the WSDLOperation: new WSDLOperation(methodName)
            var getAllProjects:WSDLOperation = new WSDLOperation("getAllProjects");
				//input message for the operation
    	        requestMessage = new WSDLMessage("getAllProjects");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.project.facade.service.topcoder.com/","getAllProjects"),null,new QName("http://ejb.project.facade.service.topcoder.com/","getAllProjects")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.project.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
                
                responseMessage = new WSDLMessage("getAllProjectsResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.project.facade.service.topcoder.com/","getAllProjectsResponse"),null,new QName("http://ejb.project.facade.service.topcoder.com/","getAllProjectsResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.project.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				getAllProjects.inputMessage = requestMessage;
	        getAllProjects.outputMessage = responseMessage;
            getAllProjects.schemaManager = this.schemaMgr;
            getAllProjects.soapAction = "\"\"";
            getAllProjects.style = "document";
            BaseProjectServiceFacadeBeanServiceService.getPort("BaseProjectServiceFacadeBeanServicePort").binding.portType.addOperation(getAllProjects);
			//define the WSDLOperation: new WSDLOperation(methodName)
            var getProject:WSDLOperation = new WSDLOperation("getProject");
				//input message for the operation
    	        requestMessage = new WSDLMessage("getProject");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.project.facade.service.topcoder.com/","getProject"),null,new QName("http://ejb.project.facade.service.topcoder.com/","getProject")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.project.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
                
                responseMessage = new WSDLMessage("getProjectResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.project.facade.service.topcoder.com/","getProjectResponse"),null,new QName("http://ejb.project.facade.service.topcoder.com/","getProjectResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.project.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				getProject.inputMessage = requestMessage;
	        getProject.outputMessage = responseMessage;
            getProject.schemaManager = this.schemaMgr;
            getProject.soapAction = "\"\"";
            getProject.style = "document";
            BaseProjectServiceFacadeBeanServiceService.getPort("BaseProjectServiceFacadeBeanServicePort").binding.portType.addOperation(getProject);
			//define the WSDLOperation: new WSDLOperation(methodName)
            var getProjectsForUser:WSDLOperation = new WSDLOperation("getProjectsForUser");
				//input message for the operation
    	        requestMessage = new WSDLMessage("getProjectsForUser");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.project.facade.service.topcoder.com/","getProjectsForUser"),null,new QName("http://ejb.project.facade.service.topcoder.com/","getProjectsForUser")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.project.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
                
                responseMessage = new WSDLMessage("getProjectsForUserResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.project.facade.service.topcoder.com/","getProjectsForUserResponse"),null,new QName("http://ejb.project.facade.service.topcoder.com/","getProjectsForUserResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.project.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				getProjectsForUser.inputMessage = requestMessage;
	        getProjectsForUser.outputMessage = responseMessage;
            getProjectsForUser.schemaManager = this.schemaMgr;
            getProjectsForUser.soapAction = "\"\"";
            getProjectsForUser.style = "document";
            BaseProjectServiceFacadeBeanServiceService.getPort("BaseProjectServiceFacadeBeanServicePort").binding.portType.addOperation(getProjectsForUser);
			//define the WSDLOperation: new WSDLOperation(methodName)
            var updateProject:WSDLOperation = new WSDLOperation("updateProject");
				//input message for the operation
    	        requestMessage = new WSDLMessage("updateProject");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://ejb.project.facade.service.topcoder.com/","updateProject"),null,new QName("http://ejb.project.facade.service.topcoder.com/","updateProject")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://ejb.project.facade.service.topcoder.com/";
			requestMessage.encoding.useStyle="literal";
                
                responseMessage = new WSDLMessage("updateProjectResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://ejb.project.facade.service.topcoder.com/","updateProjectResponse"),null,new QName("http://ejb.project.facade.service.topcoder.com/","updateProjectResponse")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://ejb.project.facade.service.topcoder.com/";
                responseMessage.encoding.useStyle="literal";				
				updateProject.inputMessage = requestMessage;
	        updateProject.outputMessage = responseMessage;
            updateProject.schemaManager = this.schemaMgr;
            updateProject.soapAction = "\"\"";
            updateProject.style = "document";
            BaseProjectServiceFacadeBeanServiceService.getPort("BaseProjectServiceFacadeBeanServicePort").binding.portType.addOperation(updateProject);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.project.facade.service.topcoder.com/","UserNotFoundFault"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.UserNotFoundFault);
							SchemaTypeRegistry.getInstance().registerCollectionClass(new QName("http://ejb.project.facade.service.topcoder.com/","getProjectsForUserResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetProjectsForUserResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.project.facade.service.topcoder.com/","updateProjectResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.UpdateProjectResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.project.facade.service.topcoder.com/","getAllProjects"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetAllProjects);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.project.facade.service.topcoder.com/","createProject"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.CreateProject);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.project.facade.service.topcoder.com/","createProjectResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.CreateProjectResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.project.facade.service.topcoder.com/","AuthorizationFailedFault"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.AuthorizationFailedFault);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.project.facade.service.topcoder.com/","projectData"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.ProjectData);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.project.facade.service.topcoder.com/","deleteProject"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.DeleteProject);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.project.facade.service.topcoder.com/","updateProject"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.UpdateProject);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.project.facade.service.topcoder.com/","getProjectResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetProjectResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.project.facade.service.topcoder.com/","ProjectHasCompetitionsFault"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.ProjectHasCompetitionsFault);
							SchemaTypeRegistry.getInstance().registerCollectionClass(new QName("http://ejb.project.facade.service.topcoder.com/","getAllProjectsResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetAllProjectsResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.project.facade.service.topcoder.com/","getProject"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetProject);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.project.facade.service.topcoder.com/","ProjectNotFoundFault"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.ProjectNotFoundFault);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.project.facade.service.topcoder.com/","PersistenceFault"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.PersistenceFault);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.project.facade.service.topcoder.com/","deleteProjectResponse"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.DeleteProjectResponse);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.project.facade.service.topcoder.com/","getProjectsForUser"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.GetProjectsForUser);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://ejb.project.facade.service.topcoder.com/","IllegalArgumentFault"),com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated.IllegalArgumentFault);
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param createProject
		 * @return Asynchronous token
		 */
		public function createProject(createProject:CreateProject):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["createProject"] = createProject;
	            currentOperation = BaseProjectServiceFacadeBeanServiceService.getPort("BaseProjectServiceFacadeBeanServicePort").binding.portType.getOperation("createProject");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param deleteProject
		 * @return Asynchronous token
		 */
		public function deleteProject(deleteProject:DeleteProject):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["deleteProject"] = deleteProject;
	            currentOperation = BaseProjectServiceFacadeBeanServiceService.getPort("BaseProjectServiceFacadeBeanServicePort").binding.portType.getOperation("deleteProject");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param getAllProjects
		 * @return Asynchronous token
		 */
		public function getAllProjects(getAllProjects:GetAllProjects):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["getAllProjects"] = getAllProjects;
	            currentOperation = BaseProjectServiceFacadeBeanServiceService.getPort("BaseProjectServiceFacadeBeanServicePort").binding.portType.getOperation("getAllProjects");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param getProject
		 * @return Asynchronous token
		 */
		public function getProject(getProject:GetProject):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["getProject"] = getProject;
	            currentOperation = BaseProjectServiceFacadeBeanServiceService.getPort("BaseProjectServiceFacadeBeanServicePort").binding.portType.getOperation("getProject");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param getProjectsForUser
		 * @return Asynchronous token
		 */
		public function getProjectsForUser(getProjectsForUser:GetProjectsForUser):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["getProjectsForUser"] = getProjectsForUser;
	            currentOperation = BaseProjectServiceFacadeBeanServiceService.getPort("BaseProjectServiceFacadeBeanServicePort").binding.portType.getOperation("getProjectsForUser");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param updateProject
		 * @return Asynchronous token
		 */
		public function updateProject(updateProject:UpdateProject):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["updateProject"] = updateProject;
	            currentOperation = BaseProjectServiceFacadeBeanServiceService.getPort("BaseProjectServiceFacadeBeanServicePort").binding.portType.getOperation("updateProject");
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
	        var soap:Object = new Object;
	        var message:SOAPMessage = new SOAPMessage();
	        enc.wsdlOperation = operation;
	        soap = enc.encodeRequest(args,headers.concat(this.headers));
	        message.setSOAPAction(operation.soapAction);
	        message.body = soap.toString();
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