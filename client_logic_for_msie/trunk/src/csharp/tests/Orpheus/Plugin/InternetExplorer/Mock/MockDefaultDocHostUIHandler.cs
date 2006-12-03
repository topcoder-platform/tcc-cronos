/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * MockDefaultWebBrowserWindowNavigator.cs
 */
using System;
using System.IO;
using MsHtmHstInterop;
using SHDocVw;
using Orpheus.Plugin.InternetExplorer.WindowNavigators;
using Orpheus.Plugin.InternetExplorer.Persistence;
using Orpheus.Plugin.InternetExplorer.EventsManagers;
using TopCoder.Util.BloomFilter;

namespace Orpheus.Plugin.InternetExplorer.Mock
{
    /// <summary>
    /// A mock class extended from DefaultDocHostUIHandler, for test only.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    public class MockDefaultDocHostUIHandler : DefaultDocHostUIHandler
    {
        /// <summary>
        ///
        /// </summary>
        public MockDefaultDocHostUIHandler() : base(
            new MsieClientLogic(new WebBrowserClass(), new BloomFilter(100, 0.5f),
            new RegistryPersistence(), new MockDefaultWebBrowserWindowNavigator(),
            new DefaultExtensionEventsManager(), new MockDefaultDocHostUIHandler(), new object()))
        {
        }
    }
}
