/**
 * GetAllDocumentTypesResponse.as
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

	public class GetAllDocumentTypesResponse extends ArrayCollection
	{
		/**
		 * Constructor - initializes the array collection based on a source array
		 */
        
		public function GetAllDocumentTypesResponse(source:Array = null)
		{
			super(source);
		}
        
        
		public function addDocumentTypeAt(item:DocumentType,index:int):void 
		{
			addItemAt(item,index);
		}

		public function addDocumentType(item:DocumentType):void 
		{
			addItem(item);
		} 

		public function getDocumentTypeAt(index:int):DocumentType 
		{
			return getItemAt(index) as DocumentType;
		}

		public function getDocumentTypeIndex(item:DocumentType):int 
		{
			return getItemIndex(item);
		}

		public function setDocumentTypeAt(item:DocumentType,index:int):void 
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
