/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.pipeline.webservice {
    import mx.rpc.xml.IXMLSchemaInstance;
    
    /**
     * <p>
     * This is the date search criteria for loading the contests and pipeline info from backend.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author snow01
     * @version 1.0
     */
    public class DateSearchCriteria implements IXMLSchemaInstance {
        
        /**
         * A default empty constructor.
         */ 
        public function DateSearchCriteria() {
        }
        
        /**
         * Gets The xsi type.
         * 
         * @return the xsi type.
         */ 
        public function get xsiType():QName {
            return _xsiType;
        }
        
        /**
         * Sets The xsi type.
         * 
         * @param t the xsi type.
         */
        public function set xsiType(t:QName):void {
            _xsiType=t;
        }
        
        /**
         * The xsi type.
         */ 
        private var _xsiType:QName;
        
        /**
         * The start date.
         */
        public var startDate:Date;
        
        /**
         * The end date.
         */
        public var endDate:Date;
        
        /**
         * True if overdue contests to be considered.
         */
        public var overdueContests:Boolean;
    
    }
}