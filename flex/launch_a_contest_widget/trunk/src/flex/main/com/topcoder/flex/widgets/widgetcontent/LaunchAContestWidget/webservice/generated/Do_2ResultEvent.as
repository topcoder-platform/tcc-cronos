/**
 * Do_2ResultEvent.as
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
    
    public class Do_2ResultEvent extends Event
    {
        /**
         * The event type value
         */
        public static var Do_2_RESULT:String="Do_2_result";
        /**
         * Constructor for the new event type
         */
        public function Do_2ResultEvent()
        {
            super(Do_2_RESULT,false,false);
        }
        
        private var _headers:Object;
        private var _result:Do_2Response;
         public function get result():Do_2Response
        {
            return _result;
        }
        
        public function set result(value:Do_2Response):void
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