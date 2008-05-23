/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */

using System;
using System.Collections.Generic;

namespace TopCoder.Web.Distance
{
    /// <summary>
    /// <para>This interface defines the contract to generate distances between members
    /// in XML string format. It uses either the default weighting strategy or a
    /// custom one to calculate the final aggregate result.</para>
    /// <para>Implementations of this interface should either define their own XSDs
    /// or use the existing one. Applications can save the returned xml string
    /// to local files and validate the xml against the XSD on loading to ensure
    /// the data is not corrupted or unexpectedly modified.</para>
    /// </summary>
    /// <remarks>
    /// Thread-Safety: Implementations of this interface should be thread-safe.
    /// </remarks>
    /// <author>TCSDESIGNER</author>
    /// <author>hotblue</author>
    /// <copyright>Copyright (c) 2008, TopCoder, Inc. All rights reserved.</copyright>
    /// <version>1.0</version>
    public interface IDistanceGenerator
    {
        /// <summary>
        /// Generates a distance XML as specified by the required XSD.
        /// The coderId requested must be the first element in the XML,
        /// following elements need not be ordered.
        /// </summary>
        /// <param name="coderId">The coder to generate distances from.</param>
        /// <param name="distanceType">The distance types to generate.</param>
        /// <param name="compType">The competition types to generate.</param>
        /// <returns>An XML document conforming to the component XSD, containing first
        /// the coder requested, followed by related coders with calculated distances.</returns>
        string GenerateDistanceXml(long coderId, DistanceTypes distanceType, CompetitionTypes compType);

        /// <summary>
        /// Generates a distance XML as specified by the required XSD.
        /// The coderId requested must be the first element in the XML,
        /// while the following elements need not be ordered.
        /// This method uses the given weights to calculate the final
        /// aggregated distance data.
        /// </summary>
        /// <param name="coderId">The coder to generate distances from.</param>
        /// <param name="distanceType">The distance types to generate.</param>
        /// <param name="compType">The competition types to generate.</param>
        /// <param name="weights">
        /// The weights for each distance type. Note that in this dictionary each key
        /// represents a single existing field defined in DistanceTypes.
        /// </param>
        /// <returns>An XML document conforming to the component XSD, containing first
        /// the coder requested, followed by related coders with calculated distances.</returns>
        string GenerateDistanceXml(long coderId, DistanceTypes distanceType,
            CompetitionTypes compType, IDictionary<DistanceTypes, float> weights);

    }
}
