/**
 * RetrieveSubmissionResultEvent.as
 * This file was auto-generated from WSDL
 * Any change made to this file will be overwritten when the code is re-generated.
*/
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated
{
    import mx.utils.ObjectProxy;
    import flash.events.Event;
    import flash.utils.ByteArray;
    import mx.rpc.soap.types.*;
    /**
     * Typed event handler for the result of the operation
     */
    
    public class RetrieveSubmissionResultEvent extends Event
    {
        /**
         * The event type value
         */
        public static var RetrieveSubmission_RESULT:String="RetrieveSubmission_result";
        /**
         * Constructor for the new event type
         */
        public function RetrieveSubmissionResultEvent()
        {
            super(RetrieveSubmission_RESULT,false,false);
        }
        
        private var _headers:Object;
        private var _result:RetrieveSubmissionResponse;
         public function get result():RetrieveSubmissionResponse
        {
            return _result;
        }
        
        public function set result(value:RetrieveSubmissionResponse):void
        {
            _result = value;
        }

        public function get headers():Object
	    {
            return _headers;
	    }
			
	    public function set headers(value:Object):void
	    {
            _headers = value;
	    }
    }
}