/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * SiteSettingException.cs
 */

using System;
using System.Runtime.Serialization;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// Exception to signal any problems when setting the browser site
    /// and getting a reference to the host browser.
    /// </summary>
    ///
    /// <author>TCSDESIGNER</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [Serializable]
    public class SiteSettingException : ClientLogicExtensionException
    {
        /// <summary>
        /// Creates a new exception.
        /// </summary>
        public SiteSettingException()
            : base()
        {
        }

        /// <summary>
        /// Creates a new exception with the specified message.
        /// </summary>
        /// <param name="message">Exception message.</param>
        public SiteSettingException(string message)
            : base(message)
        {
        }

        /// <summary>
        /// Creates a new exception with the specified message and cause
        /// </summary>
        /// <param name="message">Exception message.</param>
        /// <param name="cause">Exception cause.</param>
        public SiteSettingException(string message, Exception cause)
            : base(message, cause)
        {
        }

        /// <summary>
        /// Seriaization constructor.
        /// </summary>
        /// <param name="info">Serialization info.</param>
        /// <param name="context">Streaming context</param>
        protected SiteSettingException(SerializationInfo info, StreamingContext context)
            : base(info, context)
        {
        }
    }
}
