/**
 * RetrieveAllSubmissionsByMemberResponse.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated{
    import mx.utils.ObjectProxy;
    import mx.collections.ArrayCollection;
    import mx.collections.IList;
    import mx.collections.ICollectionView;
    import mx.rpc.soap.types.*;
    /**
     * Typed array collection
     */

    public class RetrieveAllSubmissionsByMemberResponse extends ArrayCollection
    {
        /**
         * Constructor - initializes the array collection based on a source array
         */
        
        public function RetrieveAllSubmissionsByMemberResponse(source:Array = null)
        {
            super(source);
        }
        
        
        public function addSubmissionDataAt(item:SubmissionData,index:int):void {
            addItemAt(item,index);
        }
            
        public function addSubmissionData(item:SubmissionData):void {
            addItem(item);
        } 

        public function getSubmissionDataAt(index:int):SubmissionData {
            return getItemAt(index) as SubmissionData;
        }
                
        public function getSubmissionDataIndex(item:SubmissionData):int {
            return getItemIndex(item);
        }
                            
        public function setSubmissionDataAt(item:SubmissionData,index:int):void {
            setItemAt(item,index);
        }

        public function asIList():IList {
            return this as IList;
        }
        
        public function asICollectionView():ICollectionView {
            return this as ICollectionView;
        }
    }
}
