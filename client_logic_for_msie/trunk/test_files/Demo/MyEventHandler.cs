/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * 
 * MyEventHandler.cs
 */
using System;
using Orpheus.Plugin.InternetExplorer.EventsManagers;

namespace Orpheus.Plugin.InternetExplorer
{
	/// <summary>
	/// This class shows how to define user owne event hanlder.<br />
	/// 
	/// All uer-defined event handlers must implement the <c>IExtentionEventHandler</c>
	/// interface. Event handlers are added to the configuration file, and invoked by 
	/// the component when the event they are configured for is fired.
	/// 
	/// <author>TCSDESIGNER</author>
	/// <author>TCSDEVELOPER</author>
	/// <version>1.0</version>
	/// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
	/// </summary>
	public class MyEventHandler :IExtensionEventHandler
	{
		/// <summary>
		/// Constructor.
		/// </summary>
		public MyEventHandler()
		{
			// you can add your code, here
			// such like get config values to initialize this handler
		}
		
		/// <summary>
		/// You event handler, you can add your implementation here.
		/// </summary>
		/// 
		/// <param name="sender">the sender</param>
		/// <param name="args">the args</param>
		public void HandleEvent(object sender, ExtensionEventArgs args)
		{
			// here just output the event name to console.
			System.Console.WriteLine(args.EventName);
		}
	}
}