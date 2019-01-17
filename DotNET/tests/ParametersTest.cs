using NUnit.Framework;
using src;

namespace tests
{
    [TestFixture()]
    public class ParametersTest
    {
        [Test()]
        public void DefaultParameters()
        {
            Parameters p = new Parameters(new string[] { });
            Assert.AreEqual("input.txt", p.Input);
            Assert.AreEqual("output.txt", p.Output);
            Assert.False(p.Verbose);
        }

        [Test()]
        public void Input()
        {
            Parameters p = new Parameters(new string[] { "-in", "in.txt" });
            Assert.AreEqual("in.txt", p.Input);
        }

        [Test()]
        public void Output()
        {
            Parameters p = new Parameters(new string[] { "-out", "out2.txt" });
            Assert.AreEqual("out2.txt", p.Output);
        }

        [Test()]
        public void Verbose()
        {
            Parameters p = new Parameters(new string[] { "-verbose" });
            Assert.True(p.Verbose);
        }
    }
}
