/**
 * Do_3ResultEvent.as
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
    
    public class Do_3ResultEvent extends Event
    {
        /**
         * The event type value
         */
        public static var Do_3_RESULT:String="Do_3_result";
        /**
         * Constructor for the new event type
         */
        public function Do_3ResultEvent()
        {
            super(Do_3_RESULT,false,false);
        }
        
        private var _headers:Object;
        private var _result:Do_3Response;
         public function get result():Do_3Response
        {
            return _result;
        }
        
        public function set result(value:Do_3Response):void
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