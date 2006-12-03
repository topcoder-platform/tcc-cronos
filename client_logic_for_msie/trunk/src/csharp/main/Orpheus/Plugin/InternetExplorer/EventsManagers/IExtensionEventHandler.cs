/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * IExtensionEventHandler.cs
 */

using System;

namespace Orpheus.Plugin.InternetExplorer.EventsManagers
{
    /// <summary>
    /// This interface defines the contract that is required for any event handler
    /// to implement. An event handler will be given a context object which holds
    /// all the details of the current extension. <br />
    ///
    /// For added flexibility to the design, a factory is used to create
    /// implementations of this interface based on a event name. <br />
    ///
    /// This component provides four implementations of this interface.
    /// Each one responsible for handling a different type of event, all sharing
    /// the same mechanism to be created and invoked. <br />
    ///
    /// <strong>Thread safety</strong>: Implementations of this interface are
    /// expected to be thread safe.
    /// </summary>
    ///
    /// <author>TCSDESIGNER</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    public interface IExtensionEventHandler
    {
        /// <summary>
        /// Implementors of this method are free to handle the specific event as needed.
        /// They can make use of the context object provided.
        /// </summary>
        ///
        /// <exception cref="ArgumentNullException">if any parameter is null.</exception>
        /// <exception cref="HandleEventException">
        /// to signal problems in handling the event.</exception>
        ///
        /// <param name="sender">Sender object.</param>
        /// <param name="args">Extension event arguments.</param>
        void HandleEvent(object sender, ExtensionEventArgs args);
    }
}
