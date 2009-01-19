/**
 * UpdateSubmissionResultEvent.as
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
    
    public class UpdateSubmissionResultEvent extends Event
    {
        /**
         * The event type value
         */
        public static var UpdateSubmission_RESULT:String="UpdateSubmission_result";
        /**
         * Constructor for the new event type
         */
        public function UpdateSubmissionResultEvent()
        {
            super(UpdateSubmission_RESULT,false,false);
        }
        
        private var _headers:Object;
        private var _result:UpdateSubmissionResponse;
         public function get result():UpdateSubmissionResponse
        {
            return _result;
        }
        
        public function set result(value:UpdateSubmissionResponse):void
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