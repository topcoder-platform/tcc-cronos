/**
 * GetAllContestsResultEvent.as
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
    
    public class GetAllContestsResultEvent extends Event
    {
        /**
         * The event type value
         */
        public static var GetAllContests_RESULT:String="GetAllContests_result";
        /**
         * Constructor for the new event type
         */
        public function GetAllContestsResultEvent()
        {
            super(GetAllContests_RESULT,false,false);
        }
        
        private var _headers:Object;
        private var _result:GetAllContestsResponse;
         public function get result():GetAllContestsResponse
        {
            return _result;
        }
        
        public function set result(value:GetAllContestsResponse):void
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