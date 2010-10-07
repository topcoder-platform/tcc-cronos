/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.projectadminwidget.qs.vo
{
	[Bindable]
	public class ValueObject
	{
		public function getFromXML(xml:XML):void {
			this.xml = xml;
		}
		protected var xml:XML;
		protected function fetchNode(tag:String, target:String):void {
			var xmlList:XMLList = xml.child(tag);
			if (checkList(xmlList)) {
				this[target] = xmlList[0];
			}
		}
		
		protected function fetchAttribute(tag:String, target:String):void {
			var xmlList:XMLList = xml.attribute(tag);
			if (checkList(xmlList)) {
				this[target] = xmlList[0];
			}
		}
		
		protected function checkList(xmlList:XMLList):Boolean {
			return (xmlList && xmlList.length() > 0);
		}
		
		protected function fetchDateNode(tag:String, target:String):void {
			var xmlList:XMLList = xml.child(tag);
			if (checkList(xmlList)) {
				
				var d:String = xmlList[0];
				var date:Date = new Date(d);
				if (date) {
					this[target] = date;
				}
			}
		}
	}
}