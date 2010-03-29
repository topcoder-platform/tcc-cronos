/*
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */

using System;
using System.ServiceModel;
using TopCoder.Services.WCF;

namespace Toro.TurfGuard.WebService
{
    /// <summary>
    /// This is the interface of the WCF Service for managing devices, device groups, and getting device readings.
    /// </summary>
    ///
    /// <remarks>
    /// It's decorated by the <c>ServiceContract</c> attribute meaning its implementations will be hosted as
    /// WCF services.
    /// </remarks>
    ///
    /// <threadsafety>
    /// <para>
    /// Implementations are expected to be thread-safe.
    /// </para>
    /// </threadsafety>
    ///
    /// <author>argolite</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2010, TopCoder, Inc. All rights reserved.</copyright>
    [ServiceContract(SessionMode = SessionMode.Allowed)]
    public interface IDeviceService
    {
        /// <summary>
        /// Adds the device in the <paramref name="input"/>.
        /// </summary>
        ///
        /// <param name="username">
        /// The username for the user.
        /// </param>
        ///
        /// <param name="password">
        /// The password for the user.
        /// </param>
        ///
        /// <param name="siteID">
        /// The ID of the site.
        /// </param>
        ///
        /// <param name="input">
        /// The XML with the device.
        /// </param>
        ///
        /// <exception cref="FaultException{TCFaultException}">
        /// <para>If any error occurs, <see cref="TCFaultException"/> will be created from:</para>
        /// <para><see cref="ArgumentNullException"/> if any string parameter is null.</para>
        /// <para><see cref="ArgumentException"/> if any string parameter is empty.</para>
        /// <para><see cref="InvalidDataInputException"/> if there is some problem with the input XML.</para>
        /// <para><see cref="AuthenticationException"/> if user cannot be authenticated.</para>
        /// <para><see cref="DeviceServiceException"/> if some unknown error occurs, such as error
        /// in service call to a repository, etc.</para>
        /// </exception>
        [OperationContract]
        [TransactionFlow(TransactionFlowOption.Allowed)]
        [FaultContract(typeof(TCFaultException))]
        void AddDevice(string username, string password, int siteID, string input);

        /// <summary>
        /// Updates the device in the <paramref name="input"/>.
        /// </summary>
        ///
        /// <param name="username">
        /// The username for the user.
        /// </param>
        ///
        /// <param name="password">
        /// The password for the user.
        /// </param>
        ///
        /// <param name="siteID">
        /// The ID of the site.
        /// </param>
        ///
        /// <param name="input">
        /// The XML with the device.
        /// </param>
        ///
        /// <exception cref="FaultException{TCFaultException}">
        /// <para>If any error occurs, <see cref="TCFaultException"/> will be created from:</para>
        /// <para><see cref="ArgumentNullException"/> if any string parameter is null.</para>
        /// <para><see cref="ArgumentException"/> if any string parameter is empty.</para>
        /// <para><see cref="InvalidDataInputException"/> if there is some problem with the input XML.</para>
        /// <para><see cref="AuthenticationException"/> if user cannot be authenticated.</para>
        /// <para><see cref="DeviceServiceException"/> if some unknown error occurs, such as error
        /// in service call to a repository, etc.</para>
        /// </exception>
        [OperationContract]
        [TransactionFlow(TransactionFlowOption.Allowed)]
        [FaultContract(typeof(TCFaultException))]
        void UpdateDevice(string username, string password, int siteID, string input);

        /// <summary>
        /// Gets all devices for the given <paramref name="siteID"/>.
        /// </summary>
        ///
        /// <param name="username">
        /// The username for the user.
        /// </param>
        ///
        /// <param name="password">
        /// The password for the user.
        /// </param>
        ///
        /// <param name="siteID">
        /// The ID of the site.
        /// </param>
        ///
        /// <returns>
        /// An XML string with the devices.
        /// </returns>
        ///
        /// <exception cref="FaultException{TCFaultException}">
        /// <para>If any error occurs, <see cref="TCFaultException"/> will be created from:</para>
        /// <para><see cref="ArgumentNullException"/> if any string parameter is null.</para>
        /// <para><see cref="ArgumentException"/> if any string parameter is empty.</para>
        /// <para><see cref="AuthenticationException"/> if user cannot be authenticated.</para>
        /// <para><see cref="DeviceServiceException"/> if some unknown error occurs, such as error
        /// in service call to a repository, etc.</para>
        /// </exception>
        [OperationContract]
        [TransactionFlow(TransactionFlowOption.Allowed)]
        [FaultContract(typeof(TCFaultException))]
        string GetAllDevices(string username, string password, int siteID);

        /// <summary>
        /// Adds the device group in the <paramref name="input"/>.
        /// </summary>
        ///
        /// <param name="username">
        /// The username for the user.
        /// </param>
        ///
        /// <param name="password">
        /// The password for the user.
        /// </param>
        ///
        /// <param name="siteID">
        /// The ID of the site.
        /// </param>
        ///
        /// <param name="input">
        /// The XML with the device group.
        /// </param>
        ///
        /// <exception cref="FaultException{TCFaultException}">
        /// <para>If any error occurs, <see cref="TCFaultException"/> will be created from:</para>
        /// <para><see cref="ArgumentNullException"/> if any string parameter is null.</para>
        /// <para><see cref="ArgumentException"/> if any string parameter is empty.</para>
        /// <para><see cref="InvalidDataInputException"/> if there is some problem with the input XML.</para>
        /// <para><see cref="AuthenticationException"/> if user cannot be authenticated.</para>
        /// <para><see cref="DeviceServiceException"/> if some unknown error occurs, such as error
        /// in service call to a repository, etc.</para>
        /// </exception>
        [OperationContract]
        [TransactionFlow(TransactionFlowOption.Allowed)]
        [FaultContract(typeof(TCFaultException))]
        void AddDeviceGroup(string username, string password, int siteID, string input);

        /// <summary>
        /// Updates the device group in the <paramref name="input"/>.
        /// </summary>
        ///
        /// <param name="username">
        /// The username for the user.
        /// </param>
        ///
        /// <param name="password">
        /// The password for the user.
        /// </param>
        ///
        /// <param name="siteID">
        /// The ID of the site.
        /// </param>
        ///
        /// <param name="input">
        /// The XML with the device group.
        /// </param>
        ///
        /// <exception cref="FaultException{TCFaultException}">
        /// <para>If any error occurs, <see cref="TCFaultException"/> will be created from:</para>
        /// <para><see cref="ArgumentNullException"/> if any string parameter is null.</para>
        /// <para><see cref="ArgumentException"/> if any string parameter is empty.</para>
        /// <para><see cref="InvalidDataInputException"/> if there is some problem with the input XML.</para>
        /// <para><see cref="AuthenticationException"/> if user cannot be authenticated.</para>
        /// <para><see cref="DeviceServiceException"/> if some unknown error occurs, such as error
        /// in service call to a repository, etc.</para>
        /// </exception>
        [OperationContract]
        [TransactionFlow(TransactionFlowOption.Allowed)]
        [FaultContract(typeof(TCFaultException))]
        void UpdateDeviceGroup(string username, string password, int siteID, string input);

        /// <summary>
        /// Gets all device groups for the given <paramref name="siteID"/> and <paramref name="deviceGroupType"/>.
        /// </summary>
        ///
        /// <param name="username">
        /// The username for the user.
        /// </param>
        ///
        /// <param name="password">
        /// The password for the user.
        /// </param>
        ///
        /// <param name="siteID">
        /// The ID of the site.
        /// </param>
        ///
        /// <param name="deviceGroupType">
        /// The device group type.
        /// </param>
        ///
        /// <returns>
        /// The XML with the device groups.
        /// </returns>
        ///
        /// <exception cref="FaultException{TCFaultException}">
        /// <para>If any error occurs, <see cref="TCFaultException"/> will be created from:</para>
        /// <para><see cref="ArgumentNullException"/> if any string parameter is null.</para>
        /// <para><see cref="ArgumentException"/> if any string parameter is empty.</para>
        /// <para><see cref="AuthenticationException"/> if user cannot be authenticated.</para>
        /// <para><see cref="DeviceServiceException"/> if some unknown error occurs, such as error
        /// in service call to a repository, etc.</para>
        /// </exception>
        [OperationContract]
        [TransactionFlow(TransactionFlowOption.Allowed)]
        [FaultContract(typeof(TCFaultException))]
        string GetAllDeviceGroups(string username, string password, int siteID, int deviceGroupType);

        /// <summary>
        /// Gets all device readings for the given parameters.
        /// </summary>
        ///
        /// <param name="username">
        /// The username for the user.
        /// </param>
        ///
        /// <param name="password">
        /// The password for the user.
        /// </param>
        ///
        /// <param name="siteID">
        /// The ID of the site.
        /// </param>
        ///
        /// <param name="input">
        /// The XML with the device or device group.
        /// </param>
        ///
        /// <param name="readingDepth">
        /// The reading depth id.
        /// </param>
        ///
        /// <param name="startDate">
        /// The start date of the search range.
        /// </param>
        ///
        /// <param name="endDate">
        /// The end date of the search range.
        /// </param>
        ///
        /// <param name="scale">
        /// The scale of the search.
        /// </param>
        ///
        /// <returns>
        /// The XML with the device readings.
        /// </returns>
        ///
        /// <exception cref="FaultException{TCFaultException}">
        /// <para>If any error occurs, <see cref="TCFaultException"/> will be created from:</para>
        /// <para><see cref="ArgumentNullException"/> if any string parameter is null.</para>
        /// <para><see cref="ArgumentException"/> if any string parameter is empty, if scale is not a
        /// valid value, or if start date is greater than end date.</para>
        /// <para><see cref="InvalidDataInputException"/> if there is some problem with the input XML.</para>
        /// <para><see cref="AuthenticationException"/> if user cannot be authenticated.</para>
        /// <para><see cref="DeviceServiceException"/> if some unknown error occurs, such as error
        /// in service call to a repository, etc.</para>
        /// </exception>
        [OperationContract]
        [TransactionFlow(TransactionFlowOption.Allowed)]
        [FaultContract(typeof(TCFaultException))]
        string GetDeviceReading(string username, string password, int siteID, string input, int readingDepth,
            DateTime startDate, DateTime endDate, string scale);
    }
}

