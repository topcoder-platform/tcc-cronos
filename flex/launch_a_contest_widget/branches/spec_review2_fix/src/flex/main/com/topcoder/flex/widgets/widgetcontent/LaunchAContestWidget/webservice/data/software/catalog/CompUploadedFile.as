/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.catalog
{
    import mx.utils.ObjectProxy;
    import flash.utils.ByteArray;
    import mx.rpc.soap.types.*;

    /**
    * CompUploadedFile DTO class.
    *
    * It stores information about the file being uploaded
    *
    * Version 1.1 (Cockpit Upload Attachment) Change Notes:
    *    - uploadedFileDesc and uploadedFileType attributes were added
    *
    * @author pulky
    *
    * @version 1.1
    * @since BUGR-1600
    */
    public class CompUploadedFile
    {
        /**
         * Constructor, initializes the type class
         */
        public function CompUploadedFile() {}

        public var fileData:flash.utils.ByteArray;
        public var uploadedFileName:String;

        /**
         * Uploaded file description
         *
         * @since 1.1
         */
        public var uploadedFileDesc:String;

        /**
         * Uploaded file type id
         *
         * @since 1.1
         */
        public var uploadedFileType:Number;
    }
}
