using Orpheus.Plugin.InternetExplorer.EventsManagers;
/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

namespace Orpheus.Plugin.InternetExplorer.AccuracyTests
{
    /// <summary>
    /// This is a mock ExtensionEventHandler for testing purpose.
    ///
    /// <author>tuenm</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    /// </summary>
    public class MockExtensionEventHandler : IExtensionEventHandler
    {
        /// <summary>
        /// The sender to save when HandleEvent is called.
        /// </summary>
        private object sender = null;

        /// <summary>
        /// The args to save when HandleEvent is called.
        /// </summary>
        private ExtensionEventArgs args = null;

        /// <summary>
        /// Retrieves the saved sender.
        /// </summary>
        /// <value>The saved sender.</value>
        public object Sender
        {
            get { return sender; }
        }

        /// <summary>
        /// Retrieves the saved args.
        /// </summary>
        /// <value>The saved args.</value>
        public ExtensionEventArgs Args
        {
            get { return args; }
        }

        /// <summary>
        /// Set the member variable for testing.
        /// </summary>
        /// <param name="sender">The sender of the event</param>
        /// <param name="args">The arguments of the event</param>
        public void HandleEvent(object sender, ExtensionEventArgs args)
        {
            this.sender = sender;
            this.args = args;
        }
    }
}