/*
 * Copyright (C) 2010 TopCoder Inc., All rights reserved.
 */

using System;
using System.Collections.Generic;
using System.Data;
using Toro.TurfGuard.Common.Core.Domain.Model;
using Toro.TurfGuard.WebService;
using Toro.TurfGuard.Common.Core.Domain;

namespace Toro.TurfGuard.WebService.StressTests
{
    /// <summary>
    /// <para>
    /// This class is a mock implementation of <see cref="IReadingRepository"/>.
    /// </para>
    /// </summary>
    ///
    /// <author>assistant</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2010, TopCoder, Inc. All rights reserved.</copyright>
    [CoverageExclude]
    public class MockReadingRepository : IReadingRepository
    {
        /// <summary>
        /// Flag indicating whether to return corrupt table for unit tests.
        /// </summary>
        public static bool ReturnCorruptTable;

        /// <summary>
        /// The mock repository used for unit tests.
        /// </summary>
        public static IDictionary<string, DataTable> Repository = new Dictionary<string, DataTable>();

        /// <summary>
        /// This method isn't implemented.
        /// </summary>
        ///
        /// <returns>List of reading scales.</returns>
        ///
        /// <exception cref="NotImplementedException">Always thrown since method isn't implemented.</exception>
        public List<string> GetReadingScale()
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// Gets the readings for given <paramref name="query"/>.
        /// </summary>
        ///
        /// <param name="query">The query to use when fetching the readings.</param>
        ///
        /// <returns>The readings in a data table.</returns>
        public DataTable GetReadings(ReadingQuery query)
        {
            // get data table for reading type and copy it
            DataTable table = Repository[query.Type.Name].Copy();

            // remove any rows that don't fall in query date range
            for (int i = table.Rows.Count - 1; i >= 0; --i)
            {
                DateTime rowDate = Convert.ToDateTime(table.Rows[i][UnitTestHelper.COLUMN_READING_DT]);
                if (!(rowDate >= query.StartDate && rowDate <= query.EndDate))
                {
                    table.Rows.RemoveAt(i);
                }
            }

            if (ReturnCorruptTable && query.Type.Name == "Percentage")
            {
                // create bad table to use for failure testing
                DataTable badTable = new DataTable();
                badTable.Columns.Add(new DataColumn(UnitTestHelper.COLUMN_READING_DT, typeof(int)));
                badTable.Columns.Add(new DataColumn(UnitTestHelper.COLUMN_READING_VAL, typeof(Single)));
                DataRow row = badTable.NewRow();
                row[UnitTestHelper.COLUMN_READING_DT] = 1234;
                row[UnitTestHelper.COLUMN_READING_VAL] = 3;
                badTable.Rows.Add(row);
                return badTable;
            }
            return table;
        }

        /// <summary>
        /// This method isn't implemented.
        /// </summary>
        ///
        /// <param name="siteId">The site ID.</param>
        /// <param name="groupTypeId">The group type ID.</param>
        /// <param name="restrictGroupId">The restrict group ID.</param>
        /// <param name="type">The type.</param>
        /// <param name="depth">The depth.</param>
        ///
        /// <returns>The current readings.</returns>
        ///
        /// <exception cref="NotImplementedException">Always thrown since method isn't implemented.</exception>
        public DataTable GetCurrentReadings(int siteId, int groupTypeId, int restrictGroupId, ReadingType type,
            ReadingDepth depth)
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// This method isn't implemented.
        /// </summary>
        ///
        /// <param name="deviceInstall">The device install.</param>
        /// <param name="typeId">The type ID.</param>
        /// <param name="depthId">The depth ID.</param>
        ///
        /// <returns>The device install.</returns>
        ///
        /// <exception cref="NotImplementedException">Always thrown since method isn't implemented.</exception>
        public DeviceInstall GetCurrentReadings(DeviceInstall deviceInstall, int typeId, int depthId)
        {
            throw new NotImplementedException();
        }
    }
}
