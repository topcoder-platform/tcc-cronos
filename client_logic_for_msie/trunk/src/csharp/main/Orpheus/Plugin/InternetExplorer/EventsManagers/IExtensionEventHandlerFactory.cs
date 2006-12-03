/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * IExtensionEventHandlerFactory.cs
 */

using System;

namespace Orpheus.Plugin.InternetExplorer.EventsManagers
{
    /// <summary>
    /// This interface defines the contract that is required for any event handler
    /// factory to implement. A event handler factory is responsible for creating
    /// the <c>IExtensionEventHandler</c> implementation based on a specified event name.
    /// The event name is simply used here to uniquely identify the required handler(s)
    /// implementation that needs to be created. <br />
    ///
    /// This component provides one implementation of this interface which uses the name
    /// to get from the configuration file the key, and the Object Factory component to
    /// create, using the key, the specified implementation(s). <br />
    ///
    /// <strong>Thread safety</strong>: Implementations of this interface are expected
    /// to be thread safe.
    /// </summary>
    ///
    /// <author>TCSDESIGNER</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    public interface IExtensionEventHandlerFactory
    {
        /// <summary>
        ///  This is the declaration of the factory method which based on a event name
        /// should create the appropriate <c>IExtensionEventHandler</c> implementation
        /// to be used.
        /// </summary>
        ///
        /// <exception cref="ArgumentNullException">if parameter is null.</exception>
        /// <exception cref="ArgumentException">if parameter is empty string.</exception>
        /// <exception cref="EventHandlerCreationException">to signal problems
        /// if can not create the event handlers.</exception>
        ///
        /// <param name="eventName">The name of the event for which to create the handlers.</param>
        /// <returns>The event handler instance.</returns>
        IExtensionEventHandler[] CreateHandlers(string eventName);
    }
}
