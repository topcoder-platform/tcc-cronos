using System;
using System.Collections.Generic;
using System.Text;
using System.Xml;
using NUnit.Framework;
using TopCoder.Web.Distance.Data;
using TopCoder.Web.Distance.DistanceCalculators;
using TopCoder.Web.Distance.DistanceGenerators;
using TopCoder.Web.Distance.XmlGenerators;
using TopCoder.Web.Distance;

namespace TopCoder.Web.Distance.AccuracyTests
{
    [TestFixture, CoverageExclude]
    public class XmlDistanceGeneratorTests
    {
        private DefaultXmlGenerator xmlGen = new DefaultXmlGenerator();

        private IDictionary<DistanceTypes, float> w = null;

        private IDictionary<DistanceTypes, IDistanceCalculator> calculators = new Dictionary<DistanceTypes, IDistanceCalculator>();


        [SetUp]
        protected void SetUp()
        {
            calculators.Clear();
            calculators.Add(DistanceTypes.Rating, new RatingDistanceCalculator());
            calculators.Add(DistanceTypes.Country, new GeographicalDistanceCalculator());
            calculators.Add(DistanceTypes.Overlap, new OverlapDistanceCalculator());

            w = new Dictionary<DistanceTypes, float>();
            w.Add(DistanceTypes.Country, 33.3333333f);
            w.Add(DistanceTypes.Overlap, 33.3333333f);
            w.Add(DistanceTypes.Rating, 33.3333333f);
        }
        /**
         * <p>
         * Tests all competition types
         * </p>
         */
        [Test]
        public void TestAllTypes()
        {
            DefaultDistanceGenerator generator =
                new DefaultDistanceGenerator(AccuracyTestHelper.GetTestDataAccess(),
                calculators, xmlGen);

            String result = generator.GenerateDistanceXml(144400, AccuracyTestHelper.GetAllDistanceEnum(),
                AccuracyTestHelper.GetAllEnum());
            CheckResult(result, "144400-allfloat");
        }

        /**
         * <p>
         * Tests all competition types
         * </p>
         */
        [Test]
        public void TestAllTypesWithWeighting()
        {
            DefaultDistanceGenerator generator =
                new DefaultDistanceGenerator(AccuracyTestHelper.GetTestDataAccess(),
                calculators, xmlGen, w);

            String result = generator.GenerateDistanceXml(144400, AccuracyTestHelper.GetAllDistanceEnum(),
                AccuracyTestHelper.GetAllEnum());
            CheckResult(result, "144400-allfloat");
        }

	 /**
	 * <p>
	 * Tests geo distance type
	 * </p>
	 */
        [Test]
        public void TestGeoType()
        {
            DefaultDistanceGenerator generator =
                new DefaultDistanceGenerator(AccuracyTestHelper.GetTestDataAccess(),
                calculators, xmlGen, w);


            String result = generator.GenerateDistanceXml(144400, DistanceTypes.Country,
                AccuracyTestHelper.GetAllEnum());
            CheckResult(result, "144400-geofloat");
        }


        /**
        * <p>
        * Tests overlap type
        * </p>
        */
        [Test]
        public void TestOverlapType()
        {
            DefaultDistanceGenerator generator =
                new DefaultDistanceGenerator(AccuracyTestHelper.GetTestDataAccess(),
                calculators, xmlGen, w);


            String result = generator.GenerateDistanceXml(144400, DistanceTypes.Overlap,
                AccuracyTestHelper.GetAllEnum());
            CheckResult(result, "144400-overlapfloat");
        }


        /**
        * <p>
        * Tests rating type
        * </p>
        */
        [Test]
        public void TestRatingType()
        {
            DefaultDistanceGenerator generator =
                new DefaultDistanceGenerator(AccuracyTestHelper.GetTestDataAccess(),
                calculators, xmlGen, w);


            String result = generator.GenerateDistanceXml(144400, DistanceTypes.Rating,
                AccuracyTestHelper.GetAllEnum());
            CheckResult(result, "144400-ratingfloat");
        }

        /**
        * <p>
        * Tests rating algorithm type
        * </p>
        */
        [Test]
        public void TestRatingTypeAlg()
        {
            DefaultDistanceGenerator generator =
                new DefaultDistanceGenerator(AccuracyTestHelper.GetTestDataAccess(),
                calculators, xmlGen, w);


            String result = generator.GenerateDistanceXml(144400, DistanceTypes.Rating,
                CompetitionTypes.Algorithm);
            CheckResult(result, "144400-alg_ratingfloat");
        }



        /**
        * <p>
        * Tests rating algorithm and design type
        * </p>
        */
        [Test]
        public void TestRatingTypeAlgDesign()
        {
            DefaultDistanceGenerator generator =
                new DefaultDistanceGenerator(AccuracyTestHelper.GetTestDataAccess(),
                calculators, xmlGen, w);


            String result = generator.GenerateDistanceXml(144400, DistanceTypes.Rating,
                CompetitionTypes.Algorithm | CompetitionTypes.Design);
            CheckResult(result, "144400-alg_design_ratingfloat");
        }

        /**
        * <p>
        * Tests rating algorithm and design type
        * </p>
        */
        [Test]
        public void TestRatingTypeAlgDesign2()
        {
            DefaultDistanceGenerator generator =
                new DefaultDistanceGenerator(AccuracyTestHelper.GetTestDataAccess(),
                calculators, xmlGen, w);


            String result = generator.GenerateDistanceXml(144400, DistanceTypes.Rating,
                CompetitionTypes.Algorithm | CompetitionTypes.Design | CompetitionTypes.HighSchool);
            CheckResult(result, "144400-alg_design_ratingfloat");
        }

	/**
	 * <p>
	 * Tests rating distance type with algorithm & design competition type
	 * </p>
	 */
        public void TestRatingTypeUndefCompetition()
        {
            DefaultDistanceGenerator generator =
                new DefaultDistanceGenerator(AccuracyTestHelper.GetTestDataAccess(),
                calculators, xmlGen, w);


            String result = generator.GenerateDistanceXml(144400, DistanceTypes.Rating, CompetitionTypes.HighSchool);
            CheckResult(result, "144400-none");
        }




        private void CheckResult(String result, String file)
        {
            XmlDocument xdocExpected = new XmlDocument();
            xdocExpected.LoadXml(AccuracyTestHelper.GetContents(file));

            XmlDocument xdocActual = new XmlDocument();
            xdocActual.LoadXml(result);

            Assert.AreEqual(xdocExpected.DocumentElement.OuterXml, xdocActual.DocumentElement.OuterXml, "Incorrect XML.");

        }



    }
}
