/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * IExtensionEventsManager.cs
 */

using System;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// This interface defines the contract that is required for any extension events manager
    /// to implement. An events manager is responsible for managing the delegates for every
    /// specific event identified by a name and to invoke them when that specific event is fired.
    /// <br />
    ///
    /// <strong>Thread safety</strong>: Implemetations of this interface are expected to
    /// be thread safe.
    /// </summary>
    ///
    /// <author>TCSDESIGNER</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    public interface IExtensionEventsManager
    {
        /// <summary>
        /// This is the declaration of the method that registers a new delegate for the specifed event.
        /// </summary>
        ///
        /// <param name="eventName">The name of the event to add the handler to.</param>
        /// <param name="eventHandler">Event handler delegate.</param>
        /// <returns>True if handler added; false otherwise.</returns>
        ///
        /// <exception cref="ArgumentNullException">if any parameter is null.</exception>
        /// <exception cref="ArgumentException">if parameter is empty string.</exception>
        bool AddEventHandler(string eventName, ExtensionEventHandlerDelegate eventHandler);

        /// <summary>
        /// This is the declaration of the method that removes a delegate for the specifed event.
        /// </summary>
        ///
        /// <param name="eventName">The name of the event to remove the handler from.</param>
        /// <param name="eventHandler">Event handler delegate.</param>
        /// <returns>True if handler removed; false otherwise.</returns>
        ///
        /// <exception cref="ArgumentNullException">if any parameter is null.</exception>
        /// <exception cref="ArgumentException">if parameter is empty string.</exception>
        bool RemoveEventHandler(string eventName, ExtensionEventHandlerDelegate eventHandler);

        /// <summary>
        /// This is the declaration of the method that should invoke all delegates
        /// associated with the event to fire.
        /// </summary>
        ///
        /// <param name="eventName">The name of the event.</param>
        /// <returns>The array of delegates registered for the current event.
        /// Empty array is returned if none are registered.</returns>
        ///
        /// <exception cref="ArgumentNullException">if parameter is null.</exception>
        /// <exception cref="ArgumentException">if parameter is empty string.</exception>
        ExtensionEventHandlerDelegate[] GetEventHandlers(string eventName);

        /// <summary>
        /// This is the declaration of the method that should invoke all delegates
        /// associated with the event to fire.
        /// </summary>
        ///
        /// <param name="eventName">The name of the event to fire.</param>
        /// <param name="sender">The sender object to pass to handlers.</param>
        /// <param name="args">The args object to pass to handlers.</param>
        ///
        /// <exception cref="ArgumentNullException">if any parameter is null.</exception>
        /// <exception cref="ArgumentException">if parameter is empty string.</exception>
        /// <exception cref="FireEventException">
        /// if anything goes wrong when firing the event.</exception>
        void FireEvent(string eventName, object sender, ExtensionEventArgs args);
    }
}
