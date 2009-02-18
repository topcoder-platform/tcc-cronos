/**
 * GetAllMediumsResponse.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated
{
	import mx.utils.ObjectProxy;
	import mx.collections.ArrayCollection;
	import mx.collections.IList;
	import mx.collections.ICollectionView;
	import mx.rpc.soap.types.*;
	/**
	 * Typed array collection
	 */

	public class GetAllMediumsResponse extends ArrayCollection
	{
		/**
		 * Constructor - initializes the array collection based on a source array
		 */
        
		public function GetAllMediumsResponse(source:Array = null)
		{
			super(source);
		}
        
        
		public function addMediumDataAt(item:MediumData,index:int):void 
		{
			addItemAt(item,index);
		}

		public function addMediumData(item:MediumData):void 
		{
			addItem(item);
		} 

		public function getMediumDataAt(index:int):MediumData 
		{
			return getItemAt(index) as MediumData;
		}

		public function getMediumDataIndex(item:MediumData):int 
		{
			return getItemIndex(item);
		}

		public function setMediumDataAt(item:MediumData,index:int):void 
		{
			setItemAt(item,index);
		}

		public function asIList():IList 
		{
			return this as IList;
		}
        
		public function asICollectionView():ICollectionView 
		{
			return this as ICollectionView;
		}
	}
}
