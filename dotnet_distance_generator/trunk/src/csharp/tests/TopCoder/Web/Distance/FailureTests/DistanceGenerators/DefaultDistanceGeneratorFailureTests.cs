/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */

using System;
using System.Collections.Generic;
using System.Configuration;
using NUnit.Framework;

using TopCoder.Configuration;

using TopCoder.Web.Distance;
using TopCoder.Web.Distance.Data;
using TopCoder.Web.Distance.DistanceCalculators;
using TopCoder.Web.Distance.DistanceGenerators;
using TopCoder.Web.Distance.XmlGenerators;

namespace TopCoder.Web.Distance.FailureTests.DistanceGenerators
{
    /// <summary>
    /// Failure tests for <code>DefaultDistanceGenerator</code>.
    /// </summary>
    ///
    /// <author>ivern</author>
    /// <author>TheCois</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2008 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class DefaultDistanceGeneratorFailureTests
    {
        /// <summary>
        /// Represents the <code>MemberDataAccess</code> instance used for testing.
        /// </summary>
        private IMemberDataAccess dataAccess;

        /// <summary>
        /// Represents the <code>IDictionary&lt;DistanceTypes,IDistanceCalculator&gt;</code> instance used for testing.
        /// </summary>
        private IDictionary<DistanceTypes, IDistanceCalculator> calculators;

        /// <summary>
        /// Represents the <code>XmlGenerator</code> instance used for testing.
        /// </summary>
        private IXmlGenerator xmlGenerator;

        /// <summary>
        /// Represents the <code>IDictionary&lt;DistanceTypes,float&gt;</code> instance used for testing.
        /// </summary>
        private IDictionary<DistanceTypes, float> weights;

        /// <summary>
        /// Sets up the test environment.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            dataAccess = new FlatFileMemberDataAccess(@"..\..\test_files\failure\");
            calculators = new Dictionary<DistanceTypes, IDistanceCalculator>();
            xmlGenerator = new DefaultXmlGenerator();
            weights = new Dictionary<DistanceTypes, float>();
        }

        /// <summary>
        /// Cleans-up the test environment after running each test.
        /// </summary>
        [TearDown]
        public void TearDown()
        {
            calculators.Clear();
            weights.Clear();
        }

        /// <summary>
        /// Test the one-argument constructor with a null configuration object.
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestConstructor1WithNullConfiguration()
        {
            new DefaultDistanceGenerator(null);
        }

        /// <summary>
        /// Test the one-argument constructor with an invalid configuration object.
        /// ConfigurationErrorsException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ConfigurationErrorsException))]
        public void TestConstructor1WithInvalidConfiguration()
        {
            new DefaultDistanceGenerator(new DefaultConfiguration("foo"));
        }

        /// <summary>
        /// Test the three-argument constructor with a null member data access object.
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestConstructor2WithNullMemberDataAccess()
        {
            new DefaultDistanceGenerator(null, calculators, xmlGenerator);
        }

        /// <summary>
        /// Test the three-argument constructor with a null calculator list.
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestConstructor2WithNullCalculators()
        {
            new DefaultDistanceGenerator(dataAccess, null, xmlGenerator);
        }

        /// <summary>
        /// Test the three-argument constructor with an empty calculator list.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestConstructor2WithEmptyCalculators()
        {
            new DefaultDistanceGenerator(dataAccess,
                                         new Dictionary<DistanceTypes, IDistanceCalculator>(),
                                         xmlGenerator);
        }

        /// <summary>
        /// Test the three-argument constructor with an invalid calculator (multiple distance types) in the list.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestConstructor2WithInvalidCalculators1()
        {
            calculators.Add(DistanceTypes.Overlap | DistanceTypes.Country, new OverlapDistanceCalculator());
            new DefaultDistanceGenerator(dataAccess, calculators, xmlGenerator);
        }

        /// <summary>
        /// Test the three-argument constructor with an invalid calculator (null <code>IDistanceCalculator</code>) in the list.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestConstructor2WithInvalidCalculators2()
        {
            calculators.Add(DistanceTypes.Overlap, null);
            new DefaultDistanceGenerator(dataAccess, calculators, xmlGenerator);
        }

        /// <summary>
        /// Test the three-argument constructor with a null XML generator.
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestConstructor2WithNullXmlGenerator()
        {
            new DefaultDistanceGenerator(dataAccess, calculators, null);
        }

        /// <summary>
        /// Test the four-argument constructor with a null member data access object.
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestConstructor3WithNullMemberDataAccess()
        {
            new DefaultDistanceGenerator(null, calculators, xmlGenerator, weights);
        }

        /// <summary>
        /// Test the four-argument constructor with a null calculator list.
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestConstructor3WithNullCalculators()
        {
            new DefaultDistanceGenerator(dataAccess, null, xmlGenerator, weights);
        }

        /// <summary>
        /// Test the four-argument constructor with an empty calculator list.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestConstructor3WithEmptyCalculators()
        {
            new DefaultDistanceGenerator(dataAccess,
                                         new Dictionary<DistanceTypes, IDistanceCalculator>(),
                                         xmlGenerator,
                                         weights);
        }

        /// <summary>
        /// Test the four-argument constructor with an invalid calculator (multiple distance types) in the list.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestConstructor3WithInvalidCalculators1()
        {
            calculators.Add(DistanceTypes.Overlap | DistanceTypes.Country, new OverlapDistanceCalculator());
            new DefaultDistanceGenerator(dataAccess, calculators, xmlGenerator, weights);
        }

        /// <summary>
        /// Test the four-argument constructor with an invalid calculator (null <code>IDistanceCalculator</code>) in the list.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestConstructor3WithInvalidCalculators2()
        {
            calculators.Add(DistanceTypes.Overlap, null);
            new DefaultDistanceGenerator(dataAccess, calculators, xmlGenerator, weights);
        }

        /// <summary>
        /// Test the four-argument constructor with a null XML generator.
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestConstructor3WithNullXmlGenerator()
        {
            new DefaultDistanceGenerator(dataAccess, calculators, null, weights);
        }

        /// <summary>
        /// Test the four-argument constructor with a null weights list.
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestConstructor3WithNullWeights()
        {
            // NOTE: added a calculator to prevent amibiguity between the two exceptions of
            // having empty calculators and having null weights.
            calculators.Add(DistanceTypes.Overlap, new OverlapDistanceCalculator());
            new DefaultDistanceGenerator(dataAccess, calculators, xmlGenerator, null);
        }

        /// <summary>
        /// Test the four-argument constructor with an empty weights list.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestConstructor3WithEmptyWeights()
        {
            new DefaultDistanceGenerator(dataAccess,
                                         calculators,
                                         xmlGenerator,
                                         new Dictionary<DistanceTypes, float>());
        }

        /// <summary>
        /// Test the four-argument constructor with an invalid weight (multiple distance types) in the list.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestConstructor3WithInvalidWeights1()
        {
            weights.Add(DistanceTypes.Overlap | DistanceTypes.Country, 100.0f);
            new DefaultDistanceGenerator(dataAccess, calculators, xmlGenerator, weights);
        }

        /// <summary>
        /// Test the four-argument constructor with an invalid weight (negative weight) in the list.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestConstructor3WithInvalidWeights2()
        {
            weights.Add(DistanceTypes.Overlap, -1.0f);
            new DefaultDistanceGenerator(dataAccess, calculators, xmlGenerator, weights);
        }

        /// <summary>
        /// Test the four-argument constructor with an invalid weight list (sum of weights > 100).
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestConstructor3WithInvalidWeights3()
        {
            weights.Add(DistanceTypes.Overlap, 50.0f);
            weights.Add(DistanceTypes.Country, 51.0f);
            new DefaultDistanceGenerator(dataAccess, calculators, xmlGenerator, weights);
        }

        /// <summary>
        /// Test the four-argument constructor with an invalid weight list (sum of weights &lt; 100).
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestConstructor3WithInvalidWeights4()
        {
            weights.Add(DistanceTypes.Overlap, 50.0f);
            weights.Add(DistanceTypes.Country, 49.0f);
            new DefaultDistanceGenerator(dataAccess, calculators, xmlGenerator, weights);
        }

        /// <summary>
        /// Test <code>GenerateDistanceXml</code> with a negative coder id.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateDistanceXml1WithNegativeCoderId()
        {
            calculators.Add(DistanceTypes.Overlap, new OverlapDistanceCalculator());
            DefaultDistanceGenerator distance = new DefaultDistanceGenerator(dataAccess, calculators, xmlGenerator);

            distance.GenerateDistanceXml(-1, DistanceTypes.Overlap, CompetitionTypes.Algorithm);
        }

        /// <summary>
        /// Test <code>GenerateDistanceXml</code> with a coder id of zero.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateDistanceXml1WithZeroCoderId()
        {
            calculators.Add(DistanceTypes.Overlap, new OverlapDistanceCalculator());
            DefaultDistanceGenerator distance = new DefaultDistanceGenerator(dataAccess, calculators, xmlGenerator);

            distance.GenerateDistanceXml(0, DistanceTypes.Overlap, CompetitionTypes.Algorithm);
        }

        /// <summary>
        /// Test <code>GenerateDistanceXml</code> with an invalid distance type.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateDistanceXml1WithInvalidDistanceType()
        {
            calculators.Add(DistanceTypes.Overlap, new OverlapDistanceCalculator());
            DefaultDistanceGenerator distance = new DefaultDistanceGenerator(dataAccess, calculators, xmlGenerator);

            distance.GenerateDistanceXml(144400, (DistanceTypes) 563, CompetitionTypes.Algorithm);
        }

        /// <summary>
        /// Test <code>GenerateDistanceXml</code> with an invalid competition type.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateDistanceXml1WithInvalidCompetitionType()
        {
            calculators.Add(DistanceTypes.Overlap, new OverlapDistanceCalculator());
            DefaultDistanceGenerator distance = new DefaultDistanceGenerator(dataAccess, calculators, xmlGenerator);

            distance.GenerateDistanceXml(144400, DistanceTypes.Overlap, (CompetitionTypes) 893);
        }

        /// <summary>
        /// Test <code>GenerateDistanceXml</code> with a negative coder id.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateDistanceXml2WithNegativeCoderId()
        {
            calculators.Add(DistanceTypes.Overlap, new OverlapDistanceCalculator());
            weights.Add(DistanceTypes.Overlap, 100.0f);
            DefaultDistanceGenerator distance = new DefaultDistanceGenerator(dataAccess, calculators, xmlGenerator);

            distance.GenerateDistanceXml(-1, DistanceTypes.Overlap, CompetitionTypes.Algorithm, weights);
        }

        /// <summary>
        /// Test <code>GenerateDistanceXml</code> with a coder id of zero.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateDistanceXml2WithZeroCoderId()
        {
            calculators.Add(DistanceTypes.Overlap, new OverlapDistanceCalculator());
            weights.Add(DistanceTypes.Overlap, 100.0f);
            DefaultDistanceGenerator distance = new DefaultDistanceGenerator(dataAccess, calculators, xmlGenerator);

            distance.GenerateDistanceXml(0, DistanceTypes.Overlap, CompetitionTypes.Algorithm, weights);
        }

        /// <summary>
        /// Test <code>GenerateDistanceXml</code> with an invalid distance type.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateDistanceXml2WithInvalidDistanceType()
        {
            calculators.Add(DistanceTypes.Overlap, new OverlapDistanceCalculator());
            weights.Add(DistanceTypes.Overlap, 100.0f);
            DefaultDistanceGenerator distance = new DefaultDistanceGenerator(dataAccess, calculators, xmlGenerator);

            distance.GenerateDistanceXml(144400, (DistanceTypes) 563, CompetitionTypes.Algorithm, weights);
        }

        /// <summary>
        /// Test <code>GenerateDistanceXml</code> with an invalid competition type.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateDistanceXml2WithInvalidCompetitionType()
        {
            calculators.Add(DistanceTypes.Overlap, new OverlapDistanceCalculator());
            weights.Add(DistanceTypes.Overlap, 100.0f);
            DefaultDistanceGenerator distance = new DefaultDistanceGenerator(dataAccess, calculators, xmlGenerator);

            distance.GenerateDistanceXml(144400, DistanceTypes.Overlap, (CompetitionTypes)893, weights);
        }

        /// <summary>
        /// Test <code>GenerateDistanceXml</code> with null weights.
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestGenerateDistanceXml2WithNullWeights()
        {
            calculators.Add(DistanceTypes.Overlap, new OverlapDistanceCalculator());
            DefaultDistanceGenerator distance = new DefaultDistanceGenerator(dataAccess, calculators, xmlGenerator);

            distance.GenerateDistanceXml(144400, DistanceTypes.Overlap, CompetitionTypes.Algorithm, null);
        }

        /// <summary>
        /// Test <code>GenerateDistanceXml</code> with empty weights.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateDistanceXml2WithEmptyWeights()
        {
            calculators.Add(DistanceTypes.Overlap, new OverlapDistanceCalculator());
            DefaultDistanceGenerator distance = new DefaultDistanceGenerator(dataAccess, calculators, xmlGenerator);

            distance.GenerateDistanceXml(144400, DistanceTypes.Overlap, CompetitionTypes.Algorithm, weights);
        }

        /// <summary>
        /// Test <code>GenerateDistanceXml</code> with  invalid weights.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateDistanceXml2WithInvalidWeights1()
        {
            calculators.Add(DistanceTypes.Overlap, new OverlapDistanceCalculator());
            weights.Add(DistanceTypes.Overlap | DistanceTypes.Country, 100.0f);
            DefaultDistanceGenerator distance = new DefaultDistanceGenerator(dataAccess, calculators, xmlGenerator);

            distance.GenerateDistanceXml(144400, DistanceTypes.Overlap, CompetitionTypes.Algorithm, weights);
        }

        /// <summary>
        /// Test <code>GenerateDistanceXml</code> with  invalid weights.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateDistanceXml2WithInvalidWeights2()
        {
            calculators.Add(DistanceTypes.Overlap, new OverlapDistanceCalculator());
            weights.Add((DistanceTypes) 839, 100.0f);
            DefaultDistanceGenerator distance = new DefaultDistanceGenerator(dataAccess, calculators, xmlGenerator);

            distance.GenerateDistanceXml(144400, DistanceTypes.Overlap, CompetitionTypes.Algorithm, weights);
        }

        /// <summary>
        /// Test <code>GenerateDistanceXml</code> with  invalid weights.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateDistanceXml2WithInvalidWeights3()
        {
            calculators.Add(DistanceTypes.Overlap, new OverlapDistanceCalculator());
            weights.Add((DistanceTypes) 839, 100.0f);
            DefaultDistanceGenerator distance = new DefaultDistanceGenerator(dataAccess, calculators, xmlGenerator);

            distance.GenerateDistanceXml(144400, DistanceTypes.Overlap, CompetitionTypes.Algorithm, weights);
        }

        /// <summary>
        /// Test <code>GenerateDistanceXml</code> with  invalid weights.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateDistanceXml2WithInvalidWeights4()
        {
            calculators.Add(DistanceTypes.Overlap, new OverlapDistanceCalculator());
            weights.Add(DistanceTypes.Overlap, 101.0f);
            weights.Add(DistanceTypes.Country, -1.0f);
            DefaultDistanceGenerator distance = new DefaultDistanceGenerator(dataAccess, calculators, xmlGenerator);

            distance.GenerateDistanceXml(144400, DistanceTypes.Overlap, CompetitionTypes.Algorithm, weights);
        }

        /// <summary>
        /// Test <code>GenerateDistanceXml</code> with  invalid weights.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateDistanceXml2WithInvalidWeights5()
        {
            calculators.Add(DistanceTypes.Overlap, new OverlapDistanceCalculator());
            weights.Add(DistanceTypes.Overlap, 50.0f);
            weights.Add(DistanceTypes.Country, 51.0f);
            DefaultDistanceGenerator distance = new DefaultDistanceGenerator(dataAccess, calculators, xmlGenerator);

            distance.GenerateDistanceXml(144400, DistanceTypes.Overlap, CompetitionTypes.Algorithm, weights);
        }

        /// <summary>
        /// Test <code>GenerateDistanceXml</code> with  invalid weights.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateDistanceXml2WithInvalidWeights6()
        {
            calculators.Add(DistanceTypes.Overlap, new OverlapDistanceCalculator());
            weights.Add(DistanceTypes.Overlap, 50.0f);
            weights.Add(DistanceTypes.Overlap, 49.0f);
            DefaultDistanceGenerator distance = new DefaultDistanceGenerator(dataAccess, calculators, xmlGenerator);

            distance.GenerateDistanceXml(144400, DistanceTypes.Overlap, CompetitionTypes.Algorithm, weights);
        }
    }
}
