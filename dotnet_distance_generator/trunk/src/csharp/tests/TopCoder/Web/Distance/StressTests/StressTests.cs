/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 *
 * Authors: cnettel
 * Version: 1.0
 */

using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.Threading;
using TopCoder.Web.Distance;
using TopCoder.Web.Distance.Data;
using TopCoder.Web.Distance.DistanceCalculators;
using TopCoder.Web.Distance.DistanceGenerators;
using TopCoder.Web.Distance.XmlGenerators;


namespace TopCoder.Web.Distance.StressTests
{
    /// <summary>
    /// Spawn multiple threads, verify results.
    /// </summary>
    /// <author>cnettel</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2008, TopCoder, Inc. All rights reserved.</copyright>
    [TestFixture]
    public class StressTests
    {
        /// <summary>
        /// A test instance used in each test.
        /// </summary>
        private IDistanceGenerator instance;

        /// <summary>
        /// Sets up the test object.
        /// </summary>
        [SetUp]
        public void Setup()
        {
            IDictionary<DistanceTypes, IDistanceCalculator> calcs =
                new Dictionary<DistanceTypes, IDistanceCalculator>();
            calcs[DistanceTypes.Overlap] = new OverlapDistanceCalculator();
            calcs[DistanceTypes.Country] = new GeographicalDistanceCalculator();
            calcs[DistanceTypes.Rating] = new RatingDistanceCalculator();

            instance = new DefaultDistanceGenerator(new FlatFileMemberDataAccess(@"..\..\test_files\stress\"),
                calcs,
                new DefaultXmlGenerator());
        }

        /// <summary>
        /// A dictionary of correct strings.
        /// </summary>
        IDictionary<long, string> correctStrings = new Dictionary<long, string>();

        /// <summary>
        /// Helper that makes a XML generation call.
        /// </summary>
        /// <param name="id">The id.</param>
        /// <returns>The XML.</returns>
        private string MakeCall(long id)
        {
            return instance.GenerateDistanceXml(id,
                DistanceTypes.Overlap |
                DistanceTypes.Country |
                DistanceTypes.Rating,
                CompetitionTypes.Algorithm |
                CompetitionTypes.Assembly |
                CompetitionTypes.Design |
                CompetitionTypes.Development |
                CompetitionTypes.HighSchool |
                CompetitionTypes.Marathon |
                CompetitionTypes.Studio);
        }

        /// <summary>
        /// The number of threads to spawn.
        /// </summary>
        private const int ThreadCount = 150;

        /// <summary>
        /// The number of generation runs within each thread.
        /// </summary>
        private const int InEach = 10;

        /// <summary>
        /// Runs a specific thread.
        /// </summary>
        private void ThreadRunner()
        {
            for (int i = 0; i < InEach; i++)
            {
                foreach (long id in correctStrings.Keys)
                {
                    Assert.AreEqual(correctStrings[id], MakeCall(id), "Result mismatch for " + id);
                }
            }
        }

        /// <summary>
        /// Test.
        /// </summary>
        [Test]
        public void TestStress()
        {
            DateTime old = DateTime.Now;
            correctStrings[144400] = MakeCall(144400);
            correctStrings[156859] = MakeCall(156859);
            correctStrings[277356] = MakeCall(277356);
            correctStrings[297731] = MakeCall(297731);
            correctStrings[7210680] = MakeCall(7210680);

            Thread[] threads = new Thread[ThreadCount];
            for (int i = 0; i < ThreadCount; i++)
            {
                threads[i] = new Thread(ThreadRunner);
                threads[i].Start();
            }

            for (int i = 0; i < ThreadCount; i++)
            {
                threads[i].Join();
            }

            Console.WriteLine("Time used: {0}", DateTime.Now - old);
        }
    }
}