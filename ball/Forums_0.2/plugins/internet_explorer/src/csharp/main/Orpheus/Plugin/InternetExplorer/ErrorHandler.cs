using System;
using System.Windows.Forms;
using TopCoder.Util.ConfigurationManager;

namespace Orpheus.Plugin.InternetExplorer
{
	/// <summary>
	/// This class represents single entry point for error handling. In this version it can be configured to
	/// show the error to user (useful when testing).
	/// </summary>
        /// <author>kr00tki</author>
        /// <version>1.0</version>
        /// <copyright>Copyright (C) 2007 TopCoder Inc., All Rights Reserved.</copyright>
	public class ErrorHandler
	{
        /// <summary>
        /// The configuration namespace.
        /// </summary>
        private const string NAMESPACE = "Orpheus.Plugin.InternetExplorer.ErrorHandler";
        
        /// <summary>
        /// The singleton instance of this class. It is initialized in Instance property on first call.
        /// </summary>
        private static ErrorHandler instance = null;

        /// <summary>
        /// Flag which indicates if the error message box should be shown.
        /// </summary>
        private readonly bool isEnabled;

        /// <summary>
        /// The getter property for singleton ErrorHandler instance.
        /// </summary>
        /// <value>ErrorHandler instance.</value>
        public static ErrorHandler Instance 
        {
            get
            {
                if (instance == null) 
                {
                    instance = new ErrorHandler();
                }

                return instance;
            }
        }

        /// <summary>
        /// Private constructor. It loads the configuration parameter:
        /// "enabled" - it indicates if error box should be presented to user.
        /// </summary>
		private ErrorHandler()
		{
			ConfigManager cm = ConfigManager.GetInstance();
            isEnabled = bool.Parse(cm.GetValue(NAMESPACE, "enabled"));
		}

        /// <summary>
        /// Reports the error to the user.
        /// </summary>
        /// <param name="exception">the exception to be shown.</param>
        public void ReportError(Exception exception)
        {
            ReportError(exception, "");
        }

        /// <summary>
        /// Reports the error to the user.
        /// </summary>
        /// <param name="exception">the exception to be shown.</param>
        /// <param name="message">the error message</param>
        public void ReportError(Exception exception, String message)
        {
            if (isEnabled) 
            {
                MessageBox.Show(message + exception, "The Ball Plug-in Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

	}
}
