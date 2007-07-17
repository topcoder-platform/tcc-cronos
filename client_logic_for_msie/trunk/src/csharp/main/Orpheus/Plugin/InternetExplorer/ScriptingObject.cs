/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ScriptingObject.cs
 */

using System;
using System.Runtime.InteropServices;
using MsHtmHstInterop;
using Mshtml;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// This class represents the object that will be exposed to page JavaScript code.
    /// The browser will invoke on the <c>IDocHostUIHandler.GetExternal</c> method
    /// which will provide to the browser the scripting object, which web pages will
    /// be able to access through the external object:<br/>
    /// function testScripting()
    /// {
    ///     //get the scripting object interface<br/>
    ///     var scriptingObject = window.external;<br/>
    ///     //invoke it<br/>
    ///     scriptingObject.LoggedIn();<br/>
    /// }<br/>
    ///
    /// This class provides all the methods required to be accessible from JavaScript.
    /// Scripting objects are created by the <c>MsieClientLogic</c> which uses
    /// the Object Factory to create the object using a (ExtensionContext) constructor.
    /// Scripting objects must be set the Com visible attribute set to true.
    ///
    /// <strong>Thread safety</strong>: This class has no mutable state and is thread safe.
    /// </summary>
    ///
    /// <author>TCSDESIGNER</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [ComVisible(true)]
    public class ScriptingObject
    {
        /// <summary>
        /// Represents the context of the current extension.
        /// Most scripting methods will use this context object to get access to one
        /// of the context objects.<br />
        /// This field is set in the constructor and setter. Can not be null.
        /// </summary>
        private MsieClientLogic context;

        /// <summary>
        /// This constructor is required in order for the <see cref="MsieClientLogic"/>
        /// class to be able to create instances of this class,
        /// which invokes this constructor passing the context object.
        /// </summary>
        ///
        /// <param name="context">The extension context object</param>
        ///
        /// <exception cref="ArgumentNullException">if parameter is null.</exception>
        public ScriptingObject(MsieClientLogic context)
        {
            Validator.ValidateNull(context, "context");

            this.context = context;
        }

        /// <summary>
        /// This method will allow for JavaScript code to signal to this extension
        /// that a user has logged in.
        /// </summary>
        ///
        /// <exception cref="FireEventException">
        /// propagated from the IExtensionEventsManager.</exception>
        public virtual void LoggedIn()
        {
            FireEvent(Helper.EVENT_LOGGEDIN);
        }

        /// <summary>
        /// This method will allow for JavaScript code to signal to this extension
        /// that a user has logged out.
        /// </summary>
        ///
        /// <exception cref="FireEventException">
        /// propagated from the IExtensionEventsManager.</exception>
        public virtual void LoggedOut()
        {
            FireEvent(Helper.EVENT_LOGGEDOUT);
        }

        /// <summary>
        /// This method will allow for JavaScript code to set the current game id.
        /// </summary>
        ///
        /// <param name="gameId">Game id.</param>
        ///
        /// <exception cref="PersistenceException">propagated from the
        /// <c>IPersistence</c> instance used.</exception>
        /// <exception cref="FireEventException">
        /// propagated from the IExtensionEventsManager.</exception>
        public virtual void SetWorkingGame(long gameId)
        {
            // Uses the IPersistence from the context object to store the string representation
            // of the id, using an arbitrary key.
            context.Persistence[Helper.KEY_GAME_ID] = gameId.ToString();
            FireEvent(Helper.EVENT_GAME_CHANGED);
        }

        /// <summary>
        /// This method will allow for JavaScript code to get the current game id.
        /// </summary>
        ///
        /// <returns>Working game id.</returns>
        /// <exception cref="PersistenceException">propagated from the <c>IPersistence</c
        /// > instance used.</exception>
        public virtual long GetWorkingGame()
        {
            // Uses the IPersistence from the context object to store the string
            // representation of the id, using an arbitrary key.
            try
            {
                string gameId = context.Persistence[Helper.KEY_GAME_ID];
                if (gameId.Length == 0)
                {
                    return -1;
                }
                return long.Parse(gameId);
            }
            catch (PersistenceException)
            {
                throw;
            }
            catch (Exception e)
            {
                throw new PersistenceException("Failed to get the working game id correctly.", e);
            }
        }

        /// <summary>
        /// This method will allow for JavaScript code to set the SHA-1 hash of the text
        /// of the current target identifier, the hash of the targets URL in the form of a 40-character string of
        /// hexadecimal digits, and in integer sequence number.
        /// </summary>
        ///
        /// <param name="targetHash">Tha hash code of the target object.</param>
        /// <param name="urlHash">The hash code of the target object URL.</param>
        /// <param name="sequence">Sequence number.</param>
        ///
        /// <exception cref="ArgumentNullException">if parameter is null.</exception>
        /// <exception cref="ArgumentException">if parameter is empty string.</exception>
        /// <exception cref="PersistenceException">propagated from the
        /// <c>IPersistence</c> instance used.</exception>
        public virtual void SetCurrentTarget(string targetHash, string urlHash, int sequence)
        {
            Validator.ValidateNullOrEmptyString(targetHash, "targetHash");
            Validator.ValidateNullOrEmptyString(urlHash, "urlHash");

            // Uses the IPersistence from the context object to store the string representation
            // of these values, using arbitrary keys.
            context.Persistence[Helper.KEY_HASH] = targetHash;
            context.Persistence[Helper.KEY_TARGET_URL] = urlHash.ToUpper();
            context.Persistence[Helper.KEY_SEQUENCE] = sequence.ToString();

            ExtensionEventArgs args = new ExtensionEventArgs(Helper.EVENT_NEW_TARGET_SET, context,
                new object[] { targetHash, urlHash, sequence });
            context.EventsManager.FireEvent(Helper.EVENT_NEW_TARGET_SET, this, args);
        }
        
        /// <summary>
        /// This method will allow for JavaScript code to force the poll for updates on the server.
        /// </summary>
        ///
        /// <exception cref="FireEventException">propagated from the event manager used.</exception>
        public virtual void PollMessages()
        {
            FireEvent(Helper.EVENT_POLL_UPDATES);
        }

        /// <summary>
        /// This method will allow for JavaScript code to check whether the browser
        /// window containing the current document is a popup window opened by this component.
        /// Compares the browser window that this extension is attached to, to the given window.
        /// </summary>
        ///
        /// <param name="window">Window pointer to the page the JavaScript code
        /// is calling from.</param>
        /// <returns>true for popup window, false otherwise.</returns>
        ///
        /// <exception cref="ArgumentNullException">if parameter is null.</exception>
        public virtual bool IsPopup(IHTMLWindow2 window)
        {
            Validator.ValidateNull(window, "window");

            // here we don't consider when the window is invalid
            // the design don't required.
            return window.parent != null;
        }

        /// <summary>
        /// This helper method get handlers for the given event name.
        /// and invoke the handlers to the ents.
        /// </summary>
        ///
        /// <param name="eventName">the event name</param>
        private void FireEvent(String eventName)
        {
            // Gets from the factory all the handlers for eventName
            ExtensionEventHandlerDelegate[] handlers = context.EventsManager.GetEventHandlers(eventName);
            // creates a new ExtensionEventArgs class and invokes the handlers.
            ExtensionEventArgs args = new ExtensionEventArgs(eventName, context);
            for (int i = 0; i < handlers.Length; i++)
            {
                handlers[i](this, args);
            }
        }
    }
}
