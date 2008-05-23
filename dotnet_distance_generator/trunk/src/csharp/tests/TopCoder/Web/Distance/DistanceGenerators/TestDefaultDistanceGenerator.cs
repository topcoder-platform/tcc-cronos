/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */

using System;
using System.Collections.Generic;
using System.Configuration;
using System.Xml;
using NUnit.Framework;
using TopCoder.Configuration;
using TopCoder.Web.Distance.Data;
using TopCoder.Web.Distance.DistanceCalculators;
using TopCoder.Web.Distance.XmlGenerators;

namespace TopCoder.Web.Distance.DistanceGenerators
{
    /// <summary>
    /// Unit tests for the <c>DefaultDistanceGenerator</c> class.
    /// This test fixture contains tests that validate the different methods
    /// of the <c>DefaultDistanceGenerator</c> class under normal and
    /// failure conditions.
    /// </summary>
    /// <author>hotblue</author>
    /// <copyright>Copyright (c) 2008, TopCoder, Inc. All rights reserved.</copyright>
    /// <version>1.0</version>
    [TestFixture, CoverageExclude]
    public class TestDefaultDistanceGenerator
    {
        /// <summary>
        /// An instance of <see cref="DefaultDistanceGenerator"/>
        /// used in the tests.
        /// </summary>
        private DefaultDistanceGenerator generator;

        /// <summary>
        /// An instance of <see cref="Member"/> used in the tests.
        /// </summary>
        private Member member;

        /// <summary>
        /// A list of <see cref="Member"/> instances used in the tests.
        /// </summary>
        private IList<Member> relatedMembers;

        /// <summary>
        /// A list of distances for each member.
        /// </summary>
        private IList<float> distances;

        /// <summary>
        /// The root configuration object.
        /// </summary>
        private IConfiguration config;

        /// <summary>
        /// Configuration object holding object definitions.
        /// </summary>
        private IConfiguration objectDefChild;

        /// <summary>
        /// An instance of an XML generator.
        /// </summary>
        private IXmlGenerator xmlGenerator;

        /// <summary>
        /// The distance calculators.
        /// </summary>
        private IDictionary<DistanceTypes, IDistanceCalculator> distanceCalculators;

        /// <summary>
        /// The data access.
        /// </summary>
        private IMemberDataAccess memberDataAccess;

        /// <summary>
        /// The weights.
        /// </summary>
        private IDictionary<DistanceTypes, float> weights;

        /// <summary>
        /// Sets-up the test environment prior to running each test.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            // Set up the configuration object that defines the objects.
            objectDefChild = new DefaultConfiguration("objectDefChild");

            IConfiguration objectConfig = new DefaultConfiguration("object_1");
            objectConfig.SetSimpleAttribute("name", "fileBasedDataAccess");
            IConfiguration typeNameConfig = new DefaultConfiguration("type_name");
            typeNameConfig.SetSimpleAttribute("value", typeof(MyDataAccess).AssemblyQualifiedName);
            objectConfig.AddChild(typeNameConfig);
            objectDefChild.AddChild(objectConfig);

            objectConfig = new DefaultConfiguration("object_1.parameters");
            objectConfig.SetSimpleAttribute("name", "fileBasedDataAccess");

            objectConfig = new DefaultConfiguration("object_2");
            objectConfig.SetSimpleAttribute("name", "myXmlGenerator");
            typeNameConfig = new DefaultConfiguration("type_name");
            typeNameConfig.SetSimpleAttribute("value", typeof(DefaultXmlGenerator).AssemblyQualifiedName);
            objectConfig.AddChild(typeNameConfig);
            objectDefChild.AddChild(objectConfig);

            objectConfig = new DefaultConfiguration("object_3");
            objectConfig.SetSimpleAttribute("name", "rCal");
            typeNameConfig = new DefaultConfiguration("type_name");
            typeNameConfig.SetSimpleAttribute("value", typeof(RatingDistanceCalculator).AssemblyQualifiedName);
            objectConfig.AddChild(typeNameConfig);
            objectDefChild.AddChild(objectConfig);

            objectConfig = new DefaultConfiguration("object_4");
            objectConfig.SetSimpleAttribute("name", "oCal");
            typeNameConfig = new DefaultConfiguration("type_name");
            typeNameConfig.SetSimpleAttribute("value", typeof(OverlapDistanceCalculator).AssemblyQualifiedName);
            objectConfig.AddChild(typeNameConfig);
            objectDefChild.AddChild(objectConfig);

            objectConfig = new DefaultConfiguration("object_5");
            objectConfig.SetSimpleAttribute("name", "gCal");
            typeNameConfig = new DefaultConfiguration("type_name");
            typeNameConfig.SetSimpleAttribute("value", typeof(GeographicalDistanceCalculator).AssemblyQualifiedName);
            objectConfig.AddChild(typeNameConfig);
            objectDefChild.AddChild(objectConfig);

            // Set up the configuration object used by DefaultDistanceGenerator class.
            config = new DefaultConfiguration("root");
            config.SetSimpleAttribute("dataAccessKey", "fileBasedDataAccess");
            config.SetSimpleAttribute("xmlGeneratorKey", "myXmlGenerator");
            string[] calculatorKeys = new string[] { "Rating:rCal", "Overlap:oCal", "Country:gCal" };
            config.SetSimpleAttribute("calculatorKeys", calculatorKeys);

            // Add the config should that contains the object definitions.
            config.AddChild(objectDefChild);

            generator = new DefaultDistanceGenerator(config);

            Dictionary<CompetitionTypes, int> ratings =
                new Dictionary<CompetitionTypes,int>();
            ratings.Add(CompetitionTypes.Algorithm, 1000);
            ratings.Add(CompetitionTypes.Design, 1000);
            ratings.Add(CompetitionTypes.Development, 1000);

            member = new Member(1975, "ivern", ratings, "US", "ivern.jpg", 0, 0);

            relatedMembers = new List<Member>();
            ratings =
                new Dictionary<CompetitionTypes, int>();
            ratings.Add(CompetitionTypes.Algorithm, 1750);
            ratings.Add(CompetitionTypes.Design, 1000);
            ratings.Add(CompetitionTypes.Development, 1000);
            relatedMembers.Add(new Member(1981, "mess", ratings, "US", "mess.jpg", 10, 10));

            ratings =
                new Dictionary<CompetitionTypes, int>();
            ratings.Add(CompetitionTypes.Algorithm, 1000);
            ratings.Add(CompetitionTypes.Design, 2000);
            ratings.Add(CompetitionTypes.Development, 2000);
            relatedMembers.Add(new Member(1999, "topc", ratings, "US", "topc.jpg", 20, 20));

            distances = new List<float>();
            distances.Add(0.5f);
            distances.Add(0.37f);


            // Create the distance calculators.
            distanceCalculators = new Dictionary<DistanceTypes, IDistanceCalculator>();
            distanceCalculators.Add(DistanceTypes.Rating, new RatingDistanceCalculator());
            distanceCalculators.Add(DistanceTypes.Country, new GeographicalDistanceCalculator());
            distanceCalculators.Add(DistanceTypes.Overlap, new OverlapDistanceCalculator());

            // Create the XML Generator.
            xmlGenerator = new DefaultXmlGenerator();

            // Create a data access.
            memberDataAccess = new FlatFileMemberDataAccess("../../test_files/");

            // Create the weights.
            weights = new Dictionary<DistanceTypes, float>();

        }

        /// <summary>
        /// Cleans-up the test environment after running each test.
        /// </summary>
        [TearDown]
        public void TearDown()
        {
            relatedMembers.Clear();
            distances.Clear();
        }

        /// <summary>
        /// Tests that the class definition of <see cref="DefaultDistanceGenerator"/>
        /// is correct. The class is expected to implement the <see cref="IDistanceGenerator"/>
        /// interface.
        /// </summary>
        [Test]
        public void TestClassDefinition()
        {
            Assert.IsTrue(typeof(IDistanceGenerator).IsAssignableFrom(
                typeof(DefaultDistanceGenerator)),
                "DefaultDistanceGenerator should implement IDistanceGenerator.");
        }

        /// <summary>
        /// Tests the <c>DefaultDistanceGenerator(IConfiguration)</c> constructor when
        /// passed valid values.
        /// An instance of the class is expected to be created with no exceptions thrown.
        /// </summary>
        [Test]
        public void TestCtor1()
        {
            Assert.IsNotNull(generator, "Instance should have been created.");
        }

        /// <summary>
        /// Tests the <c>DefaultDistanceGenerator(IConfiguration)</c> constructor when
        /// passed a null value.
        /// An ArgumentNullException is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor1WithNull()
        {
            new DefaultDistanceGenerator(null);
        }


        /// <summary>
        /// Tests the <c>DefaultDistanceGenerator(IConfiguration)</c> constructor when
        /// passed a configuration that is missing the child nodes.
        /// A ConfigurationErrorsException is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ConfigurationErrorsException))]
        public void TestCtor1WithMissingChildren()
        {
            config.ClearChildren();
            new DefaultDistanceGenerator(config);
        }



        /// <summary>
        /// Tests the <c>DefaultDistanceGenerator(IConfiguration)</c> constructor when
        /// passed a configuration that is missing a required configuration item.
        /// A ConfigurationErrorsException is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ConfigurationErrorsException))]
        public void TestCtor1WithNoDataAccessKey()
        {
            config.RemoveAttribute("dataAccessKey");
            new DefaultDistanceGenerator(config);
        }

        /// <summary>
        /// Tests the <c>DefaultDistanceGenerator(IConfiguration)</c> constructor when
        /// passed a configuration that is missing a required configuration item.
        /// A ConfigurationErrorsException is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ConfigurationErrorsException))]
        public void TestCtor1WithNoCalculatorKeys()
        {
            config.RemoveAttribute("calculatorKeys");
            new DefaultDistanceGenerator(config);
        }


        /// <summary>
        /// Tests the <c>DefaultDistanceGenerator(IConfiguration)</c> constructor when
        /// passed a configuration that has an invalid configuration item.
        /// A ConfigurationErrorsException is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ConfigurationErrorsException))]
        public void TestCtor1WithInvalidCalculatorKeys()
        {
            config.RemoveAttribute("calculatorKeys");
            config.SetSimpleAttribute("calculatorKeys", 129);
            new DefaultDistanceGenerator(config);
        }



        /// <summary>
        /// Tests the <c>DefaultDistanceGenerator(IConfiguration)</c> constructor when
        /// passed a configuration that has an invalid configuration item.
        /// A ConfigurationErrorsException is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ConfigurationErrorsException))]
        public void TestCtor1WithInvalidWeights()
        {
            config.RemoveAttribute("weights");
            config.SetSimpleAttribute("weights", 129);
            new DefaultDistanceGenerator(config);
        }



        /// <summary>
        /// Tests the <c>DefaultDistanceGenerator(IConfiguration)</c> constructor when
        /// passed a configuration that has a missing object factory configuration.
        /// A ConfigurationErrorsException is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ConfigurationErrorsException))]
        public void TestCtor1WithMissingObjFactoryConfig()
        {
            config.RemoveChild(objectDefChild.Name);
            new DefaultDistanceGenerator(config);
        }


        /// <summary>
        /// Tests the <c>DefaultDistanceGenerator(IMemberDataAccess,
        /// IDictionary&lt;DistanceTypes, IDistanceCalculator&gt; calculators,
        /// IXmlGenerator)</c> constructor when
        /// passed a null member data access.
        /// An ArgumentNullException is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor2WithNullMemberDataAccess()
        {
            new DefaultDistanceGenerator(null, distanceCalculators, xmlGenerator);
        }


        /// <summary>
        /// Tests the <c>DefaultDistanceGenerator(IMemberDataAccess,
        /// IDictionary&lt;DistanceTypes, IDistanceCalculator&gt; calculators,
        /// IXmlGenerator)</c> constructor when passed a null calculators.
        /// An ArgumentNullException is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor2WithNullCalculators()
        {
            new DefaultDistanceGenerator(memberDataAccess, null, xmlGenerator);
        }


        /// <summary>
        /// Tests the <c>DefaultDistanceGenerator(IMemberDataAccess,
        /// IDictionary&lt;DistanceTypes, IDistanceCalculator&gt; calculators,
        /// IXmlGenerator)</c> constructor when passed a null XML generator.
        /// An ArgumentNullException is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor2WithNullGenerator()
        {
            new DefaultDistanceGenerator(memberDataAccess, distanceCalculators, null);
        }


        /// <summary>
        /// Tests the <c>DefaultDistanceGenerator(IMemberDataAccess,
        /// IDictionary&lt;DistanceTypes, IDistanceCalculator&gt; calculators,
        /// IXmlGenerator, IDictionary&lt;DistanceTypes, float&gt;)</c>
        /// constructor when passed a null member data access.
        /// An ArgumentNullException is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor3WithNullMemberDataAccess()
        {
            new DefaultDistanceGenerator(null, distanceCalculators, xmlGenerator, weights);
        }


        /// <summary>
        /// Tests the <c>DefaultDistanceGenerator(IMemberDataAccess,
        /// IDictionary&lt;DistanceTypes, IDistanceCalculator&gt; calculators,
        /// IXmlGenerator, IDictionary&lt;DistanceTypes, float&gt;)</c>
        /// constructor when passed a null calculators.
        /// An ArgumentNullException is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor3WithNullCalculators()
        {
            new DefaultDistanceGenerator(memberDataAccess, null, xmlGenerator, weights);
        }


        /// <summary>
        /// Tests the <c>DefaultDistanceGenerator(IMemberDataAccess,
        /// IDictionary&lt;DistanceTypes, IDistanceCalculator&gt; calculators,
        /// IXmlGenerator, IDictionary&lt;DistanceTypes, float&gt;)</c>
        /// constructor when passed an empty dictionary of calculators.
        /// An ArgumentException is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestCtor3WithEmptyCalculators()
        {
            distanceCalculators.Clear();
            new DefaultDistanceGenerator(memberDataAccess, distanceCalculators, xmlGenerator, weights);
        }


        /// <summary>
        /// Tests the <c>DefaultDistanceGenerator(IMemberDataAccess,
        /// IDictionary&lt;DistanceTypes, IDistanceCalculator&gt; calculators,
        /// IXmlGenerator, IDictionary&lt;DistanceTypes, float&gt;)</c>
        /// constructor when passed a dictionary of calculators with an invalid key.
        /// An ArgumentException is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestCtor3WithInvalidKeyInCalculators()
        {
            distanceCalculators[DistanceTypes.Country | DistanceTypes.Overlap] =
                distanceCalculators[DistanceTypes.Rating];
            new DefaultDistanceGenerator(memberDataAccess, distanceCalculators, xmlGenerator, weights);
        }


        /// <summary>
        /// Tests the <c>DefaultDistanceGenerator(IMemberDataAccess,
        /// IDictionary&lt;DistanceTypes, IDistanceCalculator&gt; calculators,
        /// IXmlGenerator, IDictionary&lt;DistanceTypes, float&gt;)</c>
        /// constructor when passed a null XML generator.
        /// An ArgumentNullException is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor3WithNullGenerator()
        {
            new DefaultDistanceGenerator(memberDataAccess, distanceCalculators, null, weights);
        }


        /// <summary>
        /// Tests the <c>DefaultDistanceGenerator(IMemberDataAccess,
        /// IDictionary&lt;DistanceTypes, IDistanceCalculator&gt; calculators,
        /// IXmlGenerator, IDictionary&lt;DistanceTypes, float&gt;)</c>
        /// constructor when passed a null weights dictionary.
        /// An ArgumentNullException is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor3WithNullWeights()
        {
            new DefaultDistanceGenerator(memberDataAccess, distanceCalculators, xmlGenerator, null);
        }





        /// <summary>
        /// Tests the <c>GenerateDistanceXml(long, DistanceTypes, CompetitionTypes)</c> method
        /// when passed a negative coder ID. An <c>ArgumentException</c> is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateDistanceXmlWithNegativeCoderId()
        {
            generator.GenerateDistanceXml(-1975, DistanceTypes.Country, CompetitionTypes.Algorithm);
        }

        /// <summary>
        /// Tests the <c>GenerateDistanceXml(long, DistanceTypes, CompetitionTypes)</c> method
        /// when passed an invalid distance type. An <c>ArgumentException</c> is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateDistanceXmlWithInvalidDistanceTypes()
        {
            generator.GenerateDistanceXml(1975, (DistanceTypes)(-1), CompetitionTypes.Algorithm);
        }

        /// <summary>
        /// Tests the <c>GenerateDistanceXml(long, DistanceTypes, CompetitionTypes)</c> method
        /// when passed an invalid competition type. An <c>ArgumentException</c> is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateDistanceXmlWithInvalidCompetitionTypes()
        {
            generator.GenerateDistanceXml(1975, DistanceTypes.Country, (CompetitionTypes)(-1));
        }



        /// <summary>
        /// Tests the <c>GenerateDistanceXml(long, DistanceTypes, CompetitionTypes
        /// IDictionary&lt;DistanceTypes, float&gt;)</c> method when passed a negative coder ID.
        /// An <c>ArgumentException</c> is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateDistanceXml2WithNegativeCoderId()
        {
            generator.GenerateDistanceXml(-1975, DistanceTypes.Country, CompetitionTypes.Algorithm, weights);
        }

        /// <summary>
        /// Tests the <c>GenerateDistanceXml(long, DistanceTypes, CompetitionTypes
        /// IDictionary&lt;DistanceTypes, float&gt;)</c> method when passed an
        /// invalid distance type. An <c>ArgumentException</c> is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateDistanceXml2WithInvalidDistanceTypes()
        {
            generator.GenerateDistanceXml(1975, (DistanceTypes)(-1), CompetitionTypes.Algorithm, weights);
        }

        /// <summary>
        /// Tests the <c>GenerateDistanceXml(long, DistanceTypes, CompetitionTypes,
        /// IDictionary&lt;DistanceTypes, float&gt;)</c> method when passed an
        /// invalid competition type. An <c>ArgumentException</c> is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateDistanceXml2WithInvalidCompetitionTypes()
        {
            generator.GenerateDistanceXml(1975, DistanceTypes.Country, (CompetitionTypes)(-1), weights);
        }

        /// <summary>
        /// Tests the <c>GenerateDistanceXml(long, DistanceTypes, CompetitionTypes,
        /// IDictionary&lt;DistanceTypes, float&gt;)</c> method when passed a null
        /// dictionary of weights. An <c>ArgumentNullException</c> is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestGenerateDistanceXml2WithNullWeights()
        {
            generator.GenerateDistanceXml(1975, DistanceTypes.Country, CompetitionTypes.Algorithm, null);
        }

        /// <summary>
        /// Tests the <c>GenerateDistanceXml(long, DistanceTypes, CompetitionTypes,
        /// IDictionary&lt;DistanceTypes, float&gt;)</c> method when passed an empty
        /// dictionary of weights. An <c>ArgumentException</c> is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateDistanceXml2WithEmptyWeights()
        {
            weights.Clear();
            generator.GenerateDistanceXml(1975, DistanceTypes.Country, CompetitionTypes.Algorithm, weights);
        }


        /// <summary>
        /// Tests the <c>GenerateDistanceXml(long, DistanceTypes, CompetitionTypes,
        /// IDictionary&lt;DistanceTypes, float&gt;)</c> method when passed a
        /// dictionary of weights containing an invalid key.
        /// An <c>ArgumentException</c> is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateDistanceXml2WithInvalidKeyInWeights()
        {
            weights[DistanceTypes.Rating | DistanceTypes.Overlap] = 0f;
            generator.GenerateDistanceXml(1975, DistanceTypes.Country, CompetitionTypes.Algorithm, weights);
        }


        /// <summary>
        /// Tests the <c>GenerateDistanceXml(long, DistanceTypes, CompetitionTypes,
        /// IDictionary&lt;DistanceTypes, float&gt;)</c> method when passed a
        /// dictionary of weights whose values do not sum to 100.
        /// An <c>ArgumentException</c> is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateDistanceXml2WithInvalidSumInWeights()
        {
            weights[DistanceTypes.Rating] = 0f;
            generator.GenerateDistanceXml(1975, DistanceTypes.Country, CompetitionTypes.Algorithm, weights);
        }



        /// <summary>
        /// Tests the <c>GenerateDistanceXml(long, DistanceTypes, CompetitionTypes)</c> method
        /// when passed valid values. The XML should be generated.
        /// </summary>
        [Test]
        public void TestGenerateDistanceXml()
        {
            // Calculate only for one competition type.
            string results = generator.GenerateDistanceXml(1975, DistanceTypes.Rating,
                CompetitionTypes.Algorithm);
            Assert.IsNotNull(results, "Should not be null.");
            XmlDocument xdoc = new XmlDocument();
            xdoc.LoadXml(results);

            XmlDocument xdocExpected = new XmlDocument();
            xdocExpected.Load("../../test_files/ExpectedOutput3.xml");

            Assert.AreEqual(xdocExpected.OuterXml, xdoc.OuterXml,
                "Incorrect output.");

        }



        /// <summary>
        /// Tests the <c>GenerateDistanceXml(long, DistanceTypes, CompetitionTypes,
        /// IDictionary&lt;DistanceTypes, float&gt;)</c> method when passed valid values.
        /// The XML should be generated.
        /// </summary>
        [Test]
        public void TestGenerateDistanceXmlWithWeights()
        {
            weights.Add(DistanceTypes.Country, 100f / 3f);
            weights.Add(DistanceTypes.Overlap, 100f / 3f);
            weights.Add(DistanceTypes.Rating, 100f / 3f);

            // Calculate only for one competition type, and ratings distance.
            string results = generator.GenerateDistanceXml(1975, DistanceTypes.Rating,
                CompetitionTypes.Algorithm, weights);

            VerifyXmlResult("../../test_files/ExpectedOutput3.xml", results);

        }


        /// <summary>
        /// Tests the <c>GenerateDistanceXml(long, DistanceTypes, CompetitionTypes,
        /// IDictionary&lt;DistanceTypes, float&gt;)</c> method when passed valid values.
        /// The XML should be generated.
        /// </summary>
        [Test]
        public void TestGenerateDistanceXmlWithWeightsRatingOnly()
        {
            SetTestWeights();

            // Calculate only for one competition type, one distance type.
            string results = generator.GenerateDistanceXml(1975, DistanceTypes.Rating,
                CompetitionTypes.Algorithm, weights);

            VerifyXmlResult("../../test_files/ExpectedOutputRatingOnly.xml", results);
        }

        /// <summary>
        /// Tests the <c>GenerateDistanceXml(long, DistanceTypes, CompetitionTypes,
        /// IDictionary&lt;DistanceTypes, float&gt;)</c> method when passed valid values.
        /// The XML should be generated.
        /// </summary>
        [Test]
        public void TestGenerateDistanceXmlWithWeightsOverlapOnly()
        {
            SetTestWeights();

            // Calculate only for one competition type, one distance type.
            string results = generator.GenerateDistanceXml(1975, DistanceTypes.Overlap,
                CompetitionTypes.Algorithm, weights);

            VerifyXmlResult("../../test_files/ExpectedOutputOverlapOnly.xml", results);
        }

        /// <summary>
        /// Tests the <c>GenerateDistanceXml(long, DistanceTypes, CompetitionTypes,
        /// IDictionary&lt;DistanceTypes, float&gt;)</c> method when passed valid values.
        /// The XML should be generated.
        /// </summary>
        [Test]
        public void TestGenerateDistanceXmlWithWeightsCountryOnly()
        {
            SetTestWeights();

            // Calculate only for one competition type, one distance type.
            string results = generator.GenerateDistanceXml(1975, DistanceTypes.Country,
                CompetitionTypes.Algorithm, weights);

            VerifyXmlResult("../../test_files/ExpectedOutputCountryOnly.xml", results);
        }


        /// <summary>
        /// Tests the <c>GenerateDistanceXml(long, DistanceTypes, CompetitionTypes,
        /// IDictionary&lt;DistanceTypes, float&gt;)</c> method when passed valid values.
        /// The XML should be generated.
        /// </summary>
        [Test]
        public void TestGenerateDistanceXmlWithWeightsRatingAndCountry()
        {
            SetTestWeights();

            // Calculate only for one competition type, two distance types.
            string results = generator.GenerateDistanceXml(1975, DistanceTypes.Rating | DistanceTypes.Country,
                CompetitionTypes.Algorithm, weights);

            VerifyXmlResult("../../test_files/ExpectedOutputRatingAndCountry.xml", results);
        }


        /// <summary>
        /// Tests the <c>GenerateDistanceXml(long, DistanceTypes, CompetitionTypes,
        /// IDictionary&lt;DistanceTypes, float&gt;)</c> method when passed valid values.
        /// The XML should be generated.
        /// </summary>
        [Test]
        public void TestGenerateDistanceXmlWithWeightsRatingAndOverlap()
        {
            SetTestWeights();

            // Calculate only for one competition type, two distance types.
            string results = generator.GenerateDistanceXml(1975, DistanceTypes.Rating | DistanceTypes.Overlap,
                CompetitionTypes.Algorithm, weights);

            VerifyXmlResult("../../test_files/ExpectedOutputRatingAndOverlap.xml", results);
        }


        /// <summary>
        /// Tests the <c>GenerateDistanceXml(long, DistanceTypes, CompetitionTypes,
        /// IDictionary&lt;DistanceTypes, float&gt;)</c> method when passed valid values.
        /// The XML should be generated.
        /// </summary>
        [Test]
        public void TestGenerateDistanceXmlWithWeightsOverlapAndCountry()
        {
            SetTestWeights();

            // Calculate only for one competition type, two distance types.
            string results = generator.GenerateDistanceXml(1975, DistanceTypes.Overlap | DistanceTypes.Country,
                CompetitionTypes.Algorithm, weights);

            VerifyXmlResult("../../test_files/ExpectedOutputOverlapAndCountry.xml", results);
        }


        /// <summary>
        /// Tests the <c>GenerateDistanceXml(long, DistanceTypes, CompetitionTypes,
        /// IDictionary&lt;DistanceTypes, float&gt;)</c> method when passed valid values.
        /// The XML should be generated.
        /// </summary>
        [Test]
        public void TestGenerateDistanceXmlWithWeightsAllDistanceTypes()
        {
            SetTestWeights();

            // Calculate only for one competition type, all distance types.
            string results = generator.GenerateDistanceXml(1975,
                DistanceTypes.Country | DistanceTypes.Overlap | DistanceTypes.Rating,
                CompetitionTypes.Algorithm, weights);

            VerifyXmlResult("../../test_files/ExpectedOutputAllDistanceTypes.xml", results);
        }

        /// <summary>
        /// Verifies that the actual results match the contents of the given expected results file.
        /// </summary>
        /// <param name="expectedResultsFile">
        /// The path to the file containing the expected results.
        /// </param>
        /// <param name="actualResults">
        /// The actual results.
        /// </param>
        private void VerifyXmlResult(string expectedResultsFile, string actualResults)
        {
            Assert.IsNotNull(actualResults, "Should not be null.");
            XmlDocument xdoc = new XmlDocument();
            xdoc.LoadXml(actualResults);

            XmlDocument xdocExpected = new XmlDocument();
            xdocExpected.Load(expectedResultsFile);

            Assert.AreEqual(xdocExpected.OuterXml, xdoc.OuterXml,
                "Incorrect output.");
        }

        /// <summary>
        /// Sets the weights.
        /// </summary>
        private void SetTestWeights()
        {
            weights.Add(DistanceTypes.Country, 25f);
            weights.Add(DistanceTypes.Overlap, 25f);
            weights.Add(DistanceTypes.Rating, 50f);
        }
    }
}
