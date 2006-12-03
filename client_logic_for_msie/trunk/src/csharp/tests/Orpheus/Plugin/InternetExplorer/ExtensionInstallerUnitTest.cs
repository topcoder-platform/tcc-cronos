/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ExtensionInstallerUnitTest.cs
 */
using System;
using System.Runtime.InteropServices;
using Microsoft.Win32;
using NUnit.Framework;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// Unit Test for <c>ExtensionInstaller</c> class.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class ExtensionInstallerUnitTest
    {
        /// <summary>
        /// Represents the format string for clsid key.
        /// </summary>
        private const string CLSID_FORMAT = "CLSID\\{0}";

        /// <summary>
        /// Registry key for toool bar.
        /// </summary>
        private const string CLSID_TOOLBAR = "{00021494-0000-0000-C000-000000000046}";

        /// <summary>
        /// The type used in register or unregister.
        /// </summary>
        private Type extension;

        /// <summary>
        /// The type used in register or unregister.
        /// </summary>
        private Type noExtension;

        /// <summary>
        /// Create type to register, and lear the key.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            extension = typeof(Orpheus.Plugin.InternetExplorer.Mock.MockExtension);
            noExtension = typeof(Orpheus.Plugin.InternetExplorer.Mock.MockNoExtension);
            TearDown();
        }

        /// <summary>
        /// Clear the keys.
        /// </summary>
        [TearDown]
        public void TearDown()
        {
            try
            {
                Registry.ClassesRoot.DeleteSubKeyTree(
                    string.Format(CLSID_FORMAT, extension.GUID.ToString("B")));
            }
            catch(Exception)
            {
            }

            try
            {
                Registry.ClassesRoot.DeleteSubKeyTree(
                    string.Format(CLSID_FORMAT, noExtension.GUID.ToString("B")));
            }
            catch(Exception)
            {
            }
        }

        /// <summary>
        /// Test Register(Type type),
        /// the method should has ComUnregisterFunctionAttribute attribute.
        /// </summary>
        [Test]
        public void TestRegisterDefinintion()
        {
            object[] attrs = typeof(ExtensionInstaller).GetMethod("Register").GetCustomAttributes(false);
            Assert.AreEqual(1, attrs.Length, "There should be ComRegisterFunctionAttribute");
            Assert.IsTrue(attrs[0] is ComRegisterFunctionAttribute,
                "The attribute should be ComRegisterFunctionAttribute");
        }

        /// <summary>
        /// Test Register(Type type),
        /// when type has Extension attribute, registry key should be created.
        /// </summary>
        [Test]
        public void TestRegister()
        {
            ExtensionInstaller.Register(extension);

            // check the register keys/value
            RegistryKey clsidKey = Registry.ClassesRoot.OpenSubKey(
                string.Format(CLSID_FORMAT, extension.GUID.ToString("B")));

            RegistryKey subKey = clsidKey.OpenSubKey("Implemented Categories");
            Assert.IsNotNull(subKey.OpenSubKey(CLSID_TOOLBAR), "the toolbar not set");

            ExtensionAttribute ext = ((ExtensionAttribute[])
                extension.GetCustomAttributes(typeof(ExtensionAttribute), true))[0];
            Assert.AreEqual(ext.Name, clsidKey.GetValue("MenuText"),
                "The MenuText is not correctly");
        }

        /// <summary>
        /// Test Register(Type type),
        /// when type has not Extension attribute, not registry key should be created.
        /// </summary>
        [Test]
        public void TestRegister_NoExtension()
        {
            ExtensionInstaller.Register(noExtension);
            Assert.IsNull(Registry.ClassesRoot.OpenSubKey(string.Format(
                CLSID_FORMAT, noExtension.GUID.ToString("B"))), "No registry key should be created.");
        }

        /// <summary>
        /// Test Register(Type type),
        /// when type is null, nothing is registered.
        /// </summary>
        [Test]
        public void TestRegister_TypeIsNull()
        {
            ExtensionInstaller.Register(null);
        }

        /// <summary>
        /// Test Unregister(Type type),
        /// when type is null, nothing is unregistered.
        /// </summary>
        [Test]
        public void TestUnregister_TypeIsNull()
        {
            ExtensionInstaller.Unregister(null);
        }

        /// <summary>
        /// Test Unregister(Type type),
        /// when type has not extension, nothing is unregistered.
        /// </summary>
        [Test]
        public void TestUnregister_NoExtension()
        {
            Registry.ClassesRoot.CreateSubKey(string.Format(
                CLSID_FORMAT, noExtension.GUID.ToString("B")));
            Assert.IsNotNull(Registry.ClassesRoot.OpenSubKey(
                string.Format(CLSID_FORMAT, noExtension.GUID.ToString("B"))),
                "Failed to register");

            ExtensionInstaller.Unregister(noExtension);

            Assert.IsNotNull(Registry.ClassesRoot.OpenSubKey(
                string.Format(CLSID_FORMAT, noExtension.GUID.ToString("B"))),
                "Failed to register");
        }

        /// <summary>
        /// Test Unregister(Type type),
        /// when type has not extension, nothing is unregistered.
        /// </summary>
        [Test]
        public void TestUnregister_Extension()
        {
            // register first
            ExtensionInstaller.Register(extension);
            Assert.IsNotNull(Registry.ClassesRoot.OpenSubKey(
                string.Format(CLSID_FORMAT, extension.GUID.ToString("B"))),
                "Failed to register");

            // unregister
            ExtensionInstaller.Unregister(extension);

            // check unregister
            Assert.IsNull(Registry.ClassesRoot.OpenSubKey(
                string.Format(CLSID_FORMAT, extension.GUID.ToString("B"))),
                "Failed to unregister");
        }
    }
}
