using System;
using System.Reflection;
using Microsoft.Win32;
using TopCoder.Util.ConfigurationManager;

namespace Orpheus.Plugin.InternetExplorer.AccuracyTests
{
    /// <summary>
    /// This class contains helper methods for accuracy test.
    /// </summary>
    public class AccuracyHelper
    {
        /// <summary>
        /// Private constructor to prevent initialiazation.
        /// </summary>
        private AccuracyHelper()
        {
        }

        /// <summary>
        /// Load all the neccessary config files for accuracy test.
        /// </summary>
        public static void LoadConfiguration()
        {
            ClearConfiguration();

            ConfigManager cm = ConfigManager.GetInstance();
            cm.LoadFile("../../test_files/accuracy/config.xml");
        }

        /// <summary>
        /// Clear all configuration namespaces.
        /// </summary>
        public static void ClearConfiguration()
        {
            ConfigManager cm = ConfigManager.GetInstance();
            cm.Clear(false);
            ClearKeys();
        }

        /// <summary>
        /// Gets the internal field value using reflection.
        /// </summary>
        ///
        /// <param name="obj">The instance to get field value from.</param>
        /// <param name="fieldName">The field name</param>
        /// <returns>The field value.</returns>
        public static object GetPrivateFieldValue(object obj, string fieldName)
        {
            FieldInfo field = obj.GetType().GetField(fieldName,
                                                     BindingFlags.NonPublic | BindingFlags.Instance);
            return field.GetValue(obj);
        }

        /// <summary>
        /// Clear the Orpheus registry key.
        /// </summary>
        private static void ClearKeys()
        {
            try
            {
                Registry.CurrentUser.DeleteSubKeyTree("Software\\Orpheus");
            }
            catch (Exception)
            {
                // ignore
            }
        }
    }
}