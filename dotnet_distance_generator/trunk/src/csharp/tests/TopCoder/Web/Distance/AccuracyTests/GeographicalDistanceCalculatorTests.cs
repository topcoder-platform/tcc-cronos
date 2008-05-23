using System;
using System.Collections.Generic;
using System.Text;
using NUnit.Framework;
using TopCoder.Web.Distance.Data;
using TopCoder.Web.Distance.DistanceCalculators;


namespace TopCoder.Web.Distance.AccuracyTests
{
    [TestFixture, CoverageExclude]
    public class GeographicalDistanceCalculatorTests
    {
        /// <summary>
        /// Tests for both defined distances
        /// </summary>
        [Test]
        public void TestBothDefined()
        {
            IDistanceCalculator calculator = new GeographicalDistanceCalculator();
            IList<float> list = calculator.CalculateDistance(AccuracyTestHelper
                    .GetTestDataAccess().GetMember(AccuracyTestHelper.MEMBER_IDS[0]),
                    AccuracyTestHelper.GetTestDataAccess().GetRelatedMembers(
                            AccuracyTestHelper.MEMBER_IDS[0], AccuracyTestHelper.GetAllEnum()), AccuracyTestHelper
                            .GetAlgoritmEnum());

            Assert.AreEqual((float)(0.256 / 0.257), list[0]);
        }

        /// <summary>
        /// Tests for defined/undefined distances
        /// </summary>
        [Test]
        public void TestDefinedUndefined()
        {
            IDistanceCalculator calculator = new GeographicalDistanceCalculator();
            IList<float> list = calculator.CalculateDistance(AccuracyTestHelper
                    .GetTestDataAccess().GetMember(AccuracyTestHelper.MEMBER_IDS[0]),
                    AccuracyTestHelper.GetTestDataAccess().GetRelatedMembers(
                            AccuracyTestHelper.MEMBER_IDS[0], AccuracyTestHelper.GetAllEnum()), AccuracyTestHelper
                            .GetAlgoritmEnum());

            Assert.IsTrue(0.0f >= list[2]);
        }


        /// <summary>
        /// Tests for defined/undefined distances
        /// </summary>
        [Test]
        public void TestUndefinedDefined()
        {
            IDistanceCalculator calculator = new GeographicalDistanceCalculator();
            IList<float> list = calculator.CalculateDistance(AccuracyTestHelper
                    .GetTestDataAccess().GetMember(AccuracyTestHelper.MEMBER_IDS[4]),
                    AccuracyTestHelper.GetTestDataAccess().GetRelatedMembers(
                            AccuracyTestHelper.MEMBER_IDS[4], AccuracyTestHelper.GetAllEnum()), AccuracyTestHelper
                            .GetAlgoritmEnum());

            //System.Console.WriteLine(AccuracyTestHelper
            //       .GetTestDataAccess().GetMember(AccuracyTestHelper.MEMBER_IDS[4]).GeographicalDistance);
            Assert.IsTrue(0.0f >= list[0]);
        }

        /// <summary>
        /// Tests for both undefined distances
        /// </summary>
        [Test]
        public void TestBothUndefined()
        {
            IDistanceCalculator calculator = new GeographicalDistanceCalculator();
            IList<float> list = calculator.CalculateDistance(AccuracyTestHelper
                    .GetTestDataAccess().GetMember(AccuracyTestHelper.MEMBER_IDS[4]),
                    AccuracyTestHelper.GetTestDataAccess().GetRelatedMembers(
                            AccuracyTestHelper.MEMBER_IDS[4], AccuracyTestHelper.GetAllEnum()), AccuracyTestHelper
                            .GetAlgoritmEnum());

            Assert.IsTrue(0.0f >= list[2]);
        }

        /// <summary>
        /// Tests for zero max distance
        /// </summary>
        [Test]
        public void TestZeroMaxDistance()
        {
            IDistanceCalculator calculator = new GeographicalDistanceCalculator();
            IList<float> list = calculator.CalculateDistance(
                AccuracyTestHelper.GetTestDataAccess().GetMember(156859),
                AccuracyTestHelper.GetTestDataAccess().GetRelatedMembers(156859,
                AccuracyTestHelper.GetAllEnum()),
                AccuracyTestHelper.GetAlgoritmEnum());
            Assert.AreEqual(0.0f, list[1]);
            Assert.IsTrue(0.0f >= list[1]);
        }

        /// <summary>
        /// Tests for undef max distance
        /// </summary>
        [Test]
        public void TestUndefMaxDistance()
        {
            IDistanceCalculator calculator = new GeographicalDistanceCalculator();
            IList<float> list = calculator.CalculateDistance(AccuracyTestHelper
                    .GetTestDataAccess().GetMember(156860),
                    AccuracyTestHelper.GetTestDataAccess().GetRelatedMembers(
                            156860, AccuracyTestHelper.GetAllEnum()), AccuracyTestHelper
                            .GetAlgoritmEnum());

            Assert.IsTrue(0.0f >= list[1]);
        }

    }
}
