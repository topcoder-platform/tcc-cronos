/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * Helper.cs
 */

using MSXML2;
using System.IO;
using System.Text;
namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// A helper class define the registry key, event name.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    public sealed class Helper
    {
        /// <summary>
        /// The key for Target hash.
        /// </summary>
        public const string KEY_HASH = "hash";

        /// <summary>
        /// The key for Target sequence.
        /// </summary>
        public const string KEY_SEQUENCE = "sequence";

        /// <summary>
        /// The key for game id.
        /// </summary>
        public const string KEY_GAME_ID = "gameId";

        /// <summary>
        /// The key for serialized Bloom filter.
        /// </summary>
        public const string KEY_BLOOM_FILTER = "bloom_filter";

        /// <summary>
        /// The key used in persitece to give value of timestamp.
        /// </summary>
        public const string KEY_TIMESTAMP = "timestamp";

        /// <summary>
        /// The event name used in DocumentCompleted.
        /// </summary>
        public const string EVENT_PAGE_CHANGED = "PageChanged";

        /// <summary>
        /// The event name used in OnUpdatesPolling.or PollMessages
        /// </summary>
        public const string EVENT_POLL_UPDATES = "PollUpdates";

        /// <summary>
        /// The event name used in ScriptingObject.LoggedIn.
        /// </summary>
        public const string EVENT_LOGGEDIN = "LoggedIn";

        /// <summary>
        /// The event name used in ScriptingObject.LoggedOut.
        /// </summary>
        public const string EVENT_LOGGEDOUT = "LoggedOut";

        /// <summary>
        /// The event name used in ScriptingObject.SetWorkingGame
        /// </summary>
        public const string EVENT_GAME_CHANGED = "WorkingGameChanged";

        /// <summary>
        /// Gets the url content by xml http.
        /// </summary>
        /// <param name="url">the url to retrieve document content</param>
        /// <returns>the document content</returns>
        public static Stream GetDocumentContent(string url)
        {
            XMLHTTP xmlHttp = new XMLHTTP();
            xmlHttp.open("GET", url, false, null, null);
            xmlHttp.send(null);
            Stream stream = new MemoryStream();
            byte[] buf = Encoding.Default.GetBytes(xmlHttp.responseText);
            stream.Write(buf, 0, buf.Length);
            stream.Seek(0, SeekOrigin.Begin);
            return stream;
        }
    }
}