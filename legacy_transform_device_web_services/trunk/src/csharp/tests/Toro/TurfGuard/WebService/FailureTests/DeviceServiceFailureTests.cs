/*
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */

using System;
using System.ServiceModel;
using System.Collections.Generic;
using TopCoder.Services.WCF;
using Toro.TurfGuard.WebService.Impl;
using NUnit.Framework;

namespace Toro.TurfGuard.WebService.FailureTests
{
    /// <summary>
    /// <para>
    /// Failure tests for <see cref="DeviceService"/>.
    /// </para>
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    ///
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2010, TopCoder, Inc. All rights reserved.</copyright>
    [TestFixture]
    public class DeviceServiceFailureTests
    {
        /// <summary>
        /// <para>
        /// Represents the instance of <see cref="DeviceService"/>.
        /// </para>
        /// </summary>
        private DeviceService service;

        /// <summary>
        /// <para>
        /// Sets up the test environment.
        /// </para>
        /// </summary>
        [SetUp]
        protected void SetUp()
        {
            service = new DeviceService();
        }

        /// <summary>
        /// <para>
        /// Tests the <c>AddDevice</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void AddDeviceFailureTests1()
        {
            service.AddDevice(null, "abcd1234", 1, "test");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>AddDevice</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void AddDeviceFailureTests2()
        {
            service.AddDevice("", "abcd1234", 1, "test");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>AddDevice</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void AddDeviceFailureTests3()
        {
            service.AddDevice("DEV", null, 1, "test");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>AddDevice</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void AddDeviceFailureTests4()
        {
            service.AddDevice("DEV", "", 1, "test");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>AddDevice</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void AddDeviceFailureTests5()
        {
            service.AddDevice("DEV", "abcd1234", 1, "<a><c></b>");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>UpdateDevice</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void UpdateDeviceFailureTests1()
        {
            service.UpdateDevice(null, "abcd1234", 1, "<a></a>");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>UpdateDevice</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void UpdateDeviceFailureTests2()
        {
            service.UpdateDevice("", "abcd1234", 1, "<a></a>");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>UpdateDevice</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void UpdateDeviceFailureTests3()
        {
            service.UpdateDevice("DEV", null, 1, "<a></a>");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>UpdateDevice</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void UpdateDeviceFailureTests4()
        {
            service.UpdateDevice("DEV", "", 1, "<a></a>");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>UpdateDevice</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void UpdateDeviceFailureTests5()
        {
            service.UpdateDevice("user1", "password1", 1, "<a></b>");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>GetAllDevices</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void GetAllDevicesFailureTests1()
        {
            service.GetAllDevices(null, "password1", 1);
        }

        /// <summary>
        /// <para>
        /// Tests the <c>GetAllDevices</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void GetAllDevicesFailureTests2()
        {
            service.GetAllDevices("", "password1", 1);
        }

        /// <summary>
        /// <para>
        /// Tests the <c>GetAllDevices</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void GetAllDevicesFailureTests3()
        {
            service.GetAllDevices("user1", "", 1);
        }

        /// <summary>
        /// <para>
        /// Tests the <c>GetAllDevices</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void GetAllDevicesFailureTests4()
        {
            service.GetAllDevices("user1", null, 1);
        }

        /// <summary>
        /// <para>
        /// Tests the <c>AddDeviceGroup</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void AddDeviceGroupFailureTests1()
        {
            service.AddDeviceGroup(null, "password1", 1, "<a></a>");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>AddDeviceGroup</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void AddDeviceGroupFailureTests2()
        {
            service.AddDeviceGroup("", "password1", 1, "<a></a>");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>AddDeviceGroup</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void AddDeviceGroupFailureTests3()
        {
            service.AddDeviceGroup("user1", null, 1, "<a></a>");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>AddDeviceGroup</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void AddDeviceGroupFailureTests4()
        {
            service.AddDeviceGroup("user1", "", 1, "<a></a>");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>AddDeviceGroup</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void AddDeviceGroupFailureTests5()
        {
            service.AddDeviceGroup("user1", "password1", 1, "<a><b></a>");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>UpdateDeviceGroup</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void UpdateDeviceGroupFailureTests1()
        {
            service.UpdateDeviceGroup(null, "password1", 1, "<a></a>");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>UpdateDeviceGroup</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void UpdateDeviceGroupFailureTests2()
        {
            service.UpdateDeviceGroup("", "password1", 1, "<a></a>");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>UpdateDeviceGroup</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void UpdateDeviceGroupFailureTests3()
        {
            service.UpdateDeviceGroup("user1", null, 1, "<a></a>");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>UpdateDeviceGroup</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void UpdateDeviceGroupFailureTests4()
        {
            service.UpdateDeviceGroup("user1", "", 1, "<a></a>");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>UpdateDeviceGroup</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void UpdateDeviceGroupFailureTests5()
        {
            service.UpdateDeviceGroup("user1", "password1", 1, "<a><b></a>");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>GetAllDeviceGroups</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void GetAllDeviceGroupsFailureTests1()
        {
            service.GetAllDeviceGroups(null, "password1", 1, 1);
        }

        /// <summary>
        /// <para>
        /// Tests the <c>GetAllDeviceGroups</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void GetAllDeviceGroupsFailureTests2()
        {
            service.GetAllDeviceGroups("user1", "password1", 1, 1);
        }

        /// <summary>
        /// <para>
        /// Tests the <c>GetAllDeviceGroups</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void GetAllDeviceGroupsFailureTests3()
        {
            service.GetAllDeviceGroups("user1", null, 1, 1);
        }

        /// <summary>
        /// <para>
        /// Tests the <c>GetAllDeviceGroups</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void GetAllDeviceGroupsFailureTests4()
        {
            service.GetAllDeviceGroups("user1", "", 1, 1);
        }

        /// <summary>
        /// <para>
        /// Tests the <c>GetDeviceReading</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void GetDeviceReadingFailureTests1()
        {
            service.GetDeviceReading(null, "password1", 1, "<a></a>",
                1, new DateTime(2010, 3, 20), new DateTime(2010, 3, 25), "Hourly");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>GetDeviceReading</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void GetDeviceReadingFailureTests2()
        {
            service.GetDeviceReading("", "password1", 1, "<a></a>",
                1, new DateTime(2010, 3, 20), new DateTime(2010, 3, 25), "Hourly");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>GetDeviceReading</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void GetDeviceReadingFailureTests3()
        {
            service.GetDeviceReading("user1", null, 1, "<a></a>",
                1, new DateTime(2010, 3, 20), new DateTime(2010, 3, 25), "Hourly");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>GetDeviceReading</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void GetDeviceReadingFailureTests4()
        {
            service.GetDeviceReading("user1", "", 1, "<a></a>",
                1, new DateTime(2010, 3, 20), new DateTime(2010, 3, 25), "Hourly");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>GetDeviceReading</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void GetDeviceReadingFailureTests5()
        {
            service.GetDeviceReading("user1", "password1", 1, null,
                1, new DateTime(2010, 3, 20), new DateTime(2010, 3, 25), "Hourly");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>GetDeviceReading</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void GetDeviceReadingFailureTests6()
        {
            service.GetDeviceReading("user1", "password1", 1, "",
                1, new DateTime(2010, 3, 20), new DateTime(2010, 3, 25), "Hourly");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>GetDeviceReading</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void GetDeviceReadingFailureTests7()
        {
            service.GetDeviceReading("user1", "password1", 1, "<a></a>",
                1, new DateTime(2010, 4, 20), new DateTime(2010, 3, 25), "Hourly");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>GetDeviceReading</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void GetDeviceReadingFailureTests8()
        {
            service.GetDeviceReading("user1", "password1", 1, "<a></a>",
                1, new DateTime(2010, 3, 20), new DateTime(2010, 3, 25), null);
        }

        /// <summary>
        /// <para>
        /// Tests the <c>GetDeviceReading</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void GetDeviceReadingFailureTests9()
        {
            service.GetDeviceReading("user1", "password1", 1, "<a></a>",
                1, new DateTime(2010, 3, 20), new DateTime(2010, 3, 25), "");
        }

        /// <summary>
        /// <para>
        /// Tests the <c>GetDeviceReading</c> method.
        /// </para>
        /// <para>
        /// The <c>FaultException&lt;TCFaultException&gt;</c> is expected.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void GetDeviceReadingFailureTests10()
        {
            service.GetDeviceReading("user1", "password1", 1, "<a></a>",
                1, new DateTime(2010, 3, 20), new DateTime(2010, 3, 25), "Invalid");
        }
    }
}
