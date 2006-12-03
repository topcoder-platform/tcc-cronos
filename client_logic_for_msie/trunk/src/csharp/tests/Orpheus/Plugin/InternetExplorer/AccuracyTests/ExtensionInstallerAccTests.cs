/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

using System;
using Microsoft.Win32;
using NUnit.Framework;

namespace Orpheus.Plugin.InternetExplorer.AccuracyTests
{
    /// <summary>
    /// Accuracy tests for <c>ExtensionInstaller</c> class.
    ///
    /// <author>tuenm</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    /// </summary>
    [TestFixture]
    public class ExtensionInstallerAccTests
    {
        /// <summary>
        /// Registry GUID for the toool bar.
        /// </summary>
        private const string CLSID_TOOLBAR = "{00021494-0000-0000-C000-000000000046}";

        /// <summary>
        /// The valid type used in test cases.
        /// </summary>
        private Type validExtension;

        /// <summary>
        /// The invalid type used in test case.
        /// </summary>
        private Type invalidExtension;

        /// <summary>
        /// Set up for each test case.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            ClearRegistry();
            validExtension = typeof (MockValidExtension);
            invalidExtension = typeof (MockInvalidExtension);
        }

        /// <summary>
        /// Clean up for each test case.
        /// </summary>
        [TearDown]
        public void TearDown()
        {
            ClearRegistry();
        }

        /// <summary>
        /// Accuracy test of the <code>Register()</code> method with an valid extension.
        /// The corresponding registry key should be created.
        /// </summary>
        [Test]
        public void TestRegisterValidExt()
        {
            ExtensionInstaller.Register(validExtension);

            String keyPath = string.Format("CLSID\\{0}", validExtension.GUID.ToString("B"));
            RegistryKey clsidKey = Registry.ClassesRoot.OpenSubKey(keyPath);

            // check tool bar GUID
            RegistryKey categoryKey = clsidKey.OpenSubKey("Implemented Categories");
            RegistryKey toolBarKey = categoryKey.OpenSubKey(CLSID_TOOLBAR);
            Assert.IsNotNull(toolBarKey, "Toolbar is not registered properly.");

            // check MenuText value
            ExtensionAttribute ext = ((ExtensionAttribute[])
                validExtension.GetCustomAttributes(typeof (ExtensionAttribute), true))[0];
            Assert.AreEqual(ext.Name, clsidKey.GetValue("MenuText"),
                            "The MenuText is not correctly");
        }

        /// <summary>
        /// Accuracy test of the <code>Register()</code> method with an valid extension.
        /// The corresponding registry key should be created.
        /// </summary>
        [Test]
        public void TestRegisterInvalidExt()
        {
            ExtensionInstaller.Register(validExtension);

            String keyPath = string.Format("CLSID\\{0}", invalidExtension.GUID.ToString("B"));
            RegistryKey clsidKey = Registry.ClassesRoot.OpenSubKey(keyPath);

            Assert.IsNull(clsidKey, "No registry key should be created.");
        }

        /// <summary>
        /// Accuracy test of the <code>Unregister()</code> method with an valid extension.
        /// The corresponding registry key should be removed.
        /// </summary>
        [Test]
        public void TestUnregisterValidExt()
        {
            ExtensionInstaller.Register(validExtension);

            String keyPath = string.Format("CLSID\\{0}", validExtension.GUID.ToString("B"));
            RegistryKey clsidKey = Registry.ClassesRoot.OpenSubKey(keyPath);

            // key should be created
            Assert.IsNotNull(clsidKey, "Key should be registered.");

            ExtensionInstaller.Unregister(validExtension);

            // check again
            clsidKey = Registry.ClassesRoot.OpenSubKey(keyPath);

            Assert.IsNull(clsidKey, "Key should be removed.");
        }

        /// <summary>
        /// Accuracy test of the <code>Unregister()</code> method with an in valid extension.
        /// The corresponding registry key should not be removed.
        /// </summary>
        [Test]
        public void TestUnregisterInvalidExt()
        {
            Registry.ClassesRoot.CreateSubKey(string.Format(
                "CLSID\\{0}", invalidExtension.GUID.ToString("B")));

            ExtensionInstaller.Unregister(invalidExtension);

            // key should not be removed
            String keyPath = string.Format("CLSID\\{0}", invalidExtension.GUID.ToString("B"));
            RegistryKey clsidKey = Registry.ClassesRoot.OpenSubKey(keyPath);

            Assert.IsNotNull(clsidKey, "Key should not be removed.");
        }

        /// <summary>
        /// Clear the created registry keys.
        /// </summary>
        private void ClearRegistry()
        {
            try
            {
                Registry.ClassesRoot.DeleteSubKeyTree(
                    string.Format("CLSID\\{0}", validExtension.GUID.ToString("B")));
            }
            catch
            {
                // ignore
            }

            try
            {
                Registry.ClassesRoot.DeleteSubKeyTree(
                    string.Format("CLSID\\{0}", invalidExtension.GUID.ToString("B")));
            }
            catch
            {
                // ignore
            }
        }
    }
}
