// This is an independent project of an individual developer. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
using NUnit.Framework;
using src;
using System;
namespace tests
{
    [TestFixture()]
    public class SolverTest
    {
        [Test()]
        public void Constructor1()
        {
            void f() { Solver p = new Solver("1 1\n1 2"); }
            Assert.DoesNotThrow(f);
        }
        [Test()]
        public void Constructor2()
        {
            void f() { Solver p = new Solver("1 1\nab 2"); }
            Assert.Throws<FormatException>(f);
        }
        [Test()]
        public void Constructor3()
        {
            void f() { Solver p = new Solver("-1 1\n2 2"); }
            Assert.Throws<FormatException>(f);
        }
        [Test()]
        public void Constructor4()
        {
            void f() { Solver p = new Solver("1 -1\n2 2"); }
            Assert.Throws<FormatException>(f);
        }

        [Test()]
        public void Constructor5()
        {
            void f() { Solver p = new Solver("1 1\n2"); }
            Assert.Throws<IndexOutOfRangeException>(f);
        }
        [Test()]
        public void Constructor6()
        {
            void f() { Solver p = new Solver("0 1\n2 2"); }
            Assert.Throws<FormatException>(f);
        }
        [Test()]
        public void Constructor7()
        {
            void f() { Solver p = new Solver("1 0\n2 2"); }
            Assert.Throws<FormatException>(f);
        }
        [Test()]
        public void Solve0()
        {
            string s = "1   1 \n\n 2 4";
            Solver p = new Solver(s);
            p.Solve();

            Complex[] expectedPartialSolution = { new Complex(2.0, 0) };

            Assert.AreEqual(NumberSolutions.ONE, p.NumberSolutions);
            Assert.AreEqual(expectedPartialSolution, p.GetSolutionPartial());
            Assert.AreEqual(null, p.GetSolutionGeneral());
        }
        [Test()]
        public void Solve1()
        {
            string s = "1 1\n2 4";
            Solver p = new Solver(s);
            p.Solve();

            Complex[] expectedPartialSolution = { new Complex(2.0, 0) };

            Assert.AreEqual(NumberSolutions.ONE, p.NumberSolutions);
            Assert.AreEqual(expectedPartialSolution, p.GetSolutionPartial());
            Assert.AreEqual(null, p.GetSolutionGeneral());
        }
        [Test()]
        public void Solve2()
        {
            string s = "2 2\n1 2 3\n4 5 6";
            Solver p = new Solver(s);
            p.Solve();

            Complex[] expectedPartialSolution = { new Complex(-1.0, 0), new Complex(2.0, 0) };

            Assert.AreEqual(NumberSolutions.ONE, p.NumberSolutions);
            Assert.AreEqual(expectedPartialSolution, p.GetSolutionPartial());
            Assert.AreEqual(null, p.GetSolutionGeneral());
        }
        [Test()]
        public void Solve3()
        {
            string s = "2 2\n4 5 7\n3 9 9";
            Solver p = new Solver(s);
            p.Solve();

            Complex[] expectedPartialSolution = { new Complex(0.85714, 0),
                new Complex(0.71429, 0) };

            Assert.AreEqual(NumberSolutions.ONE, p.NumberSolutions);
            Assert.AreEqual(expectedPartialSolution, p.GetSolutionPartial());
            Assert.AreEqual(null, p.GetSolutionGeneral());
        }
        [Test()]
        public void Solve4()
        {
            string s = "3 3\n1 1 2 9\n2 4 -3 1\n3 6 -5 0";
            Solver p = new Solver(s);
            p.Solve();

            Complex[] expectedPartialSolution = { new Complex(1.0, 0),
                new Complex(2.0, 0), new Complex(3.0, 0) };

            Assert.AreEqual(NumberSolutions.ONE, p.NumberSolutions);
            Assert.AreEqual(expectedPartialSolution, p.GetSolutionPartial());
            Assert.AreEqual(null, p.GetSolutionGeneral());
        }
        [Test()]
        public void Solve5()
        {
            string s = "2 2\n0 1 1\n1 0 1";
            Solver p = new Solver(s);
            p.Solve();

            Complex[] expectedPartialSolution = { new Complex(1.0, 0),
                new Complex(1.0, 0) };

            Assert.AreEqual(NumberSolutions.ONE, p.NumberSolutions);
            Assert.AreEqual(expectedPartialSolution, p.GetSolutionPartial());
            Assert.AreEqual(null, p.GetSolutionGeneral());
        }
        [Test()]
        public void Solve6()
        {
            string s = "2 2\n0 1 1\n0 2 2";
            Solver p = new Solver(s);
            p.Solve();

            Complex[] expectedPartialSolution = { new Complex(0.0, 0),
                new Complex(1.0, 0) };
            string[] expectedGeneralSolution = { "x1", "1" };

            Assert.AreEqual(NumberSolutions.MANY, p.NumberSolutions);
            Assert.AreEqual(expectedPartialSolution, p.GetSolutionPartial());
            Assert.AreEqual(expectedGeneralSolution, p.GetSolutionGeneral());
        }
        [Test()]
        public void Solve7()
        {
            string s = "2 2\n0 1 1\n0 2 3";
            Solver p = new Solver(s);
            p.Solve();

            Assert.AreEqual(NumberSolutions.NONE, p.NumberSolutions);
            Assert.AreEqual(null, p.GetSolutionPartial());
            Assert.AreEqual(null, p.GetSolutionGeneral());
        }
        [Test()]
        public void Solve8()
        {
            string s = "3 4\n0 1 2 9\n0 1 3 1\n1 0 6 0\n2 0 2 0";
            Solver p = new Solver(s);
            p.Solve();

            Assert.AreEqual(NumberSolutions.NONE, p.NumberSolutions);
            Assert.AreEqual(null, p.GetSolutionPartial());
            Assert.AreEqual(null, p.GetSolutionGeneral());
        }
        [Test()]
        public void Solve9()
        {
            string s = "3 1\n1 1 2 9";
            Solver p = new Solver(s);
            p.Solve();

            Complex[] expectedPartialSolution = { new Complex(9.0, 0),
                new Complex(0, 0), new Complex(0, 0) };
            string[] expectedGeneralSolution = { "9 - x2 - x3 * (2)", "x2", "x3" };

            Assert.AreEqual(NumberSolutions.MANY, p.NumberSolutions);
            Assert.AreEqual(expectedPartialSolution, p.GetSolutionPartial());
            Assert.AreEqual(expectedGeneralSolution, p.GetSolutionGeneral());
        }
        [Test()]
        public void Solve10()
        {
            string s = "4 4\n1 0 0 5 0\n0 0 0 0 0\n0 0 1 4 6\n0 0 5 5 5";
            Solver p = new Solver(s);
            p.Solve();

            Complex[] expectedPartialSolution = { new Complex(-8.3333333, 0),
                new Complex(0, 0), new Complex(-0.6666667, 0), new Complex(1.6666667, 0) };
            string[] expectedGeneralSolution = { "-8.3333", "x2", "-0.6667", "1.6667" };

            Assert.AreEqual(NumberSolutions.MANY, p.NumberSolutions);
            Assert.AreEqual(expectedPartialSolution, p.GetSolutionPartial());
            Assert.AreEqual(expectedGeneralSolution, p.GetSolutionGeneral());
        }
        [Test()]
        public void Solve11()
        {
            string s = "4 4\n2 3 -1 1 1\n8 12 -9 8 3\n4 6 3 -2 3\n2 3 9 -7 3";
            Solver p = new Solver(s);
            p.Solve();

            Complex[] expectedPartialSolution = { new Complex(0.6, 0),
                new Complex(0, 0), new Complex(0.2, 0), new Complex(0, 0) };
            string[] expectedGeneralSolution = { "0.6 - x2 * (1.5) - x4 * (0.1)", "x2",
                "0.2 - x4 * (-0.8)", "x4" };

            Assert.AreEqual(NumberSolutions.MANY, p.NumberSolutions);
            Assert.AreEqual(expectedPartialSolution, p.GetSolutionPartial());
            Assert.AreEqual(expectedGeneralSolution, p.GetSolutionGeneral());
        }
        [Test()]
        public void Solve12()
        {
            string s = "3 3\n1+2i -1.5-1.1i 2.12 91+5i\n-1+3i 1.2+3.5i -3.3 1+15i\n12.31 1.3-5i 12.3i -78.3i";
            Solver p = new Solver(s);
            p.Solve();

            Complex[] expectedPartialSolution = { new Complex(6.73335286, -22.99754223),
                new Complex(-1.7976071, 2.08404919), new Complex(15.69938581, 7.3960106) };

            Assert.AreEqual(NumberSolutions.ONE, p.NumberSolutions);
            Assert.AreEqual(expectedPartialSolution, p.GetSolutionPartial());
            Assert.AreEqual(null, p.GetSolutionGeneral());
        }
        [Test()]
        public void Solve13()
        {
            string s = "1\t1\t2\t4";
            Solver p = new Solver(s);
            p.Solve();

            Complex[] expectedPartialSolution = { new Complex(2.0, 0) };

            Assert.AreEqual(NumberSolutions.ONE, p.NumberSolutions);
            Assert.AreEqual(expectedPartialSolution, p.GetSolutionPartial());
            Assert.AreEqual(null, p.GetSolutionGeneral());
        }
    }
}
