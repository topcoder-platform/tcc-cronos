/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
using System;
using Orpheus.Plugin.InternetExplorer.EventsManagers;

namespace Orpheus.Plugin.InternetExplorer.FailureTests
{
    /// <summary>
    /// A mock event handler used in unit test.
    /// Just record the argument passed in HandleEvent.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    public class MockExtensionEventHandler : IExtensionEventHandler
    {
        /// <summary>
        /// The sender record argument set in HandleEvent
        /// </summary>
        public object Sender = null;

        /// <summary>
        /// The args record argument set in HandleEvent
        /// </summary>
        public ExtensionEventArgs Args = null;

        /// <summary>
        /// Empty constructor.
        /// </summary>
        public MockExtensionEventHandler()
        {
        }

        /// <summary>
        /// Handle event, just record the argument.
        /// </summary>
        ///
        /// <param name="sender">the sender</param>
        /// <param name="args">the args</param>
        public void HandleEvent(object sender, ExtensionEventArgs args)
        {
            Sender = sender;
            Args = args;
        }
    }
}
