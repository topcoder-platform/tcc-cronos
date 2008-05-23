/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */

using System;
using System.Collections.Generic;
using TopCoder.Web.Distance.Data;

namespace TopCoder.Web.Distance
{
    /// <summary>
    /// Helper class that contains methods for parameter checking.
    /// </summary>
    /// <author>hotblue</author>
    /// <copyright>Copyright (c) 2008, TopCoder, Inc. All rights reserved.</copyright>
    /// <version>1.0</version>
    internal class Helper
    {

        /// <summary>
        /// Validates that <c>value</c> is not null.
        /// </summary>
        /// <param name="name">
        /// The name to use with any exception message.
        /// </param>
        /// <param name="value">
        /// The value to validate.
        /// </param>
        /// <exception cref="ArgumentNullException">
        /// If <paramref name="value"/> is null.
        /// </exception>
        public static void ValidateNotNull(string name, object value)
        {
            if (value == null)
            {
                throw new ArgumentNullException(name, string.Format("[{0}] cannot be null.", name));
            }
        }

        /// <summary>
        /// Validates that an error condition for an argument has not been encountered.
        /// </summary>
        /// <param name="name">
        /// The name to use with any exception message.
        /// </param>
        /// <param name="isErrorCondition">
        /// Indicates if the error condition was hit or not.
        /// </param>
        /// <param name="errorMessage">
        /// The error message to include in the exception thrown.
        /// </param>
        /// <param name="errorArgs">
        /// The arguments to the error message.
        /// </param>
        /// <exception cref="ArgumentException">
        /// If <paramref name="isErrorCondition"/> is true.
        /// </exception>
        public static void ValidateArgument(string name, bool isErrorCondition, string errorMessage,
            params object[] errorArgs)
        {
            if (isErrorCondition)
            {
                throw new ArgumentException(string.Format(errorMessage, errorArgs), name);
            }
        }


        /// <summary>
        /// Validates that <c>value</c> is not null.
        /// </summary>
        /// <param name="name">
        /// The name to use with any exception message.
        /// </param>
        /// <param name="value">
        /// The value to validate.
        /// </param>
        /// <param name="isEmptyListAllowed">
        /// Indicates if an empty list is allowed or note.
        /// </param>
        /// <exception cref="ArgumentNullException">
        /// If <paramref name="value"/> is null.
        /// </exception>
        /// <exception cref="ArgumentException">
        /// If <paramref name="value"/> contains a null element or
        /// if it is empty when an empty list is not allowed.
        /// </exception>
        public static void ValidateList<T>(string name, IList<T> value, bool isEmptyListAllowed)
        {
            ValidateNotNull(name, value);
            ValidateArgument(name, (!isEmptyListAllowed && (value.Count == 0)),
                "[{0}] cannot be empty.", name);

            foreach (T obj in value)
            {
                ValidateArgument(name, (obj == null), "[{0}] cannot contain null elements.", name);
            }
        }

        /// <summary>
        /// Validates that <paramref name="distanceType"/> is a valid combination
        /// of flag values from the <see cref="DistanceTypes"/> enumeration.
        /// </summary>
        /// <param name="name">
        /// The name to use with any exception message.
        /// </param>
        /// <param name="distanceType">
        /// The enumeration value to validate.
        /// </param>
        /// <exception cref="ArgumentException">
        /// If <paramref name="distanceType"/> is not a valid combination
        /// of flag values from the <see cref="DistanceTypes"/> enumeration.
        /// </exception>
        public static void ValidateDistanceType(string name, DistanceTypes distanceType)
        {
            ValidateArgument("distanceType", ((int)distanceType) <= 0,
                "[{0}] is not a valid combination of values from the DistanceTypes enumeration.", name);

            DistanceTypes[] dts = (DistanceTypes[])Enum.GetValues(typeof(DistanceTypes));
            DistanceTypes dtMaxCombined = 0;
            for (int idx = 0; idx < dts.Length; idx++)
            {
                dtMaxCombined |= dts[idx];
            }

            ValidateArgument("distanceType", (((int)distanceType) > ((int)dtMaxCombined)),
                "[{0}] is not a valid combination of values from the DistanceTypes enumeration.", name);
        }


        /// <summary>
        /// Validates that <paramref name="competitionType"/> is a valid combination
        /// of flag values from the <see cref="CompetitionTypes"/> enumeration.
        /// </summary>
        /// <param name="name">
        /// The name to use with any exception message.
        /// </param>
        /// <param name="competitionType">
        /// The enumeration value to validate.
        /// </param>
        /// <exception cref="ArgumentException">
        /// If <paramref name="competitionType"/> is not a valid combination
        /// of flag values from the <see cref="CompetitionTypes"/> enumeration.
        /// </exception>
        public static void ValidateCompetitionType(string name, CompetitionTypes competitionType)
        {
            ValidateArgument("competitionType", ((int)competitionType) <= 0,
                "[{0}] is not a valid combination of values from the CompetitionTypes enumeration.", name);

            CompetitionTypes[] dts = (CompetitionTypes[])Enum.GetValues(typeof(CompetitionTypes));
            CompetitionTypes dtMaxCombined = 0;
            for (int idx = 0; idx < dts.Length; idx++)
            {
                dtMaxCombined |= dts[idx];
            }

            ValidateArgument("competitionType", (((int)competitionType) > ((int)dtMaxCombined)),
                "[{0}] is not a valid combination of values from the CompetitionTypes enumeration.", name);
        }
    }
}
