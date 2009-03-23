package com.topcoder.flex.widgets.widgetcontent.submissionviewerwidget.imagezoomviewer.interfaces
{
	public interface ICommand
	{
		function execute():void;
		function isExecuting():Boolean;
		
		function cancel():void;
	}
}