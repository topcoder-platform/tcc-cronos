/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * Helper.cs
 */

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
	}
}
