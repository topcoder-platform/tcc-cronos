/**
 * GetSimpleContestDataResultEvent.as
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
    
    public class GetSimpleContestDataResultEvent extends Event
    {
        /**
         * The event type value
         */
        public static var GetSimpleContestData_RESULT:String="GetSimpleContestData_result";
        /**
         * Constructor for the new event type
         */
        public function GetSimpleContestDataResultEvent()
        {
            super(GetSimpleContestData_RESULT,false,false);
        }
        
        private var _headers:Object;
        private var _result:GetSimpleContestDataResponse;
         public function get result():GetSimpleContestDataResponse
        {
            return _result;
        }
        
        public function set result(value:GetSimpleContestDataResponse):void
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