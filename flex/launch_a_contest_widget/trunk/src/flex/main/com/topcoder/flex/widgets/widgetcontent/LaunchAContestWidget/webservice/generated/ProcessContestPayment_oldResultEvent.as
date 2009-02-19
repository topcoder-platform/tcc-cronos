/**
 * ProcessContestPayment_oldResultEvent.as
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
    
    public class ProcessContestPayment_oldResultEvent extends Event
    {
        /**
         * The event type value
         */
        public static var ProcessContestPayment_old_RESULT:String="ProcessContestPayment_old_result";
        /**
         * Constructor for the new event type
         */
        public function ProcessContestPayment_oldResultEvent()
        {
            super(ProcessContestPayment_old_RESULT,false,false);
        }
        
        private var _headers:Object;
        private var _result:ProcessContestPayment_oldResponse;
         public function get result():ProcessContestPayment_oldResponse
        {
            return _result;
        }
        
        public function set result(value:ProcessContestPayment_oldResponse):void
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