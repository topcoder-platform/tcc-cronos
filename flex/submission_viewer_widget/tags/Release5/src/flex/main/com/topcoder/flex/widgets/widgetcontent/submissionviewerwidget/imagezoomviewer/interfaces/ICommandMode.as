package com.topcoder.flex.widgets.widgetcontent.submissionviewerwidget.imagezoomviewer.interfaces
{
	public interface ICommandMode 
	{
		function activate():void;
		function deactivate():void;
		
		function isActivated():Boolean;
	}
}