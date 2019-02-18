// This is an independent project of an individual developer. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
using NUnit.Framework;
using src;
using System;
namespace tests
{
    [TestFixture()]
    public class ComplexTest
    {
        [Test()]
        public void Equals1()
        {
            Complex a = new Complex(1.0, 2.0);
            Complex b = new Complex(1.0, 2.0);
            Assert.AreEqual(a, b);
        }
        [Test()]
        public void Equals2()
        {
            Complex a = new Complex(1.0, 2.0);
            Assert.AreNotEqual("Hello", a);
        }

        [Test()]
        public void Equals3()
        {
            Complex a = new Complex(1.0, 2.0);
            Complex b = new Complex(1.1, 2.0);
            Assert.AreNotEqual(b, a);
        }

        [Test()]
        public void Equals4()
        {
            Complex a = new Complex(1.0, 2.1);
            Complex b = new Complex(1.0, 2.0);
            Assert.AreNotEqual(b, a);
        }
        [Test()]
        public void ToString1()
        {
            Complex a = new Complex(1.0, 0.0);
            Assert.AreEqual("1", a.ToString());
        }
        [Test()]
        public void ToString2()
        {
            Complex a = new Complex(0.0, 1.0);
            Assert.AreEqual("1i", a.ToString());
        }
        [Test()]
        public void ToString3()
        {
            Complex a = new Complex(-2.4, 1.5);
            Assert.AreEqual("-2.4+1.5i", a.ToString());
        }
        [Test()]
        public void ToString4()
        {
            Complex a = new Complex(2.4, -1.5);
            Assert.AreEqual("2.4-1.5i", a.ToString());
        }

        [Test()]
        public void ToString5()
        {
            Complex a = new Complex(0, 0);
            Assert.AreEqual("0", a.ToString());
        }
        [Test()]
        public void DefaultConstructor()
        {
            Complex a = new Complex(0.0, 0.0);
            Complex b = new Complex();
            Assert.AreEqual(a, b);
        }
        [Test()]
        public void Constructor1()
        {
            Complex c = new Complex(1.0, 0.0);
            Assert.AreEqual(1.0, c.Real, Complex.EPSILON);
            Assert.AreEqual(0.0, c.Imag, Complex.EPSILON);
        }

        [Test()]
        public void Constructor2()
        {
            TestDelegate f = () => { Complex a = new Complex("gbc"); };
            Assert.Throws<FormatException>(f);
        }
        [Test()]
        public void Constructor3()
        {
            string s = "-1.3";
            Complex c = new Complex(s);
            Assert.AreEqual(-1.3, c.Real, Complex.EPSILON);
            Assert.AreEqual(0.0, c.Imag, Complex.EPSILON);
        }

        [Test()]
        public void Constructor4()
        {
            string s = "2.5i";
            Complex c = new Complex(s);
            Assert.AreEqual(0.0, c.Real, Complex.EPSILON);
            Assert.AreEqual(2.5, c.Imag, Complex.EPSILON);
        }

        [Test()]
        public void Constructor5()
        {
            string s = "1.3-2.5i";
            Complex c = new Complex(s);
            Assert.AreEqual(1.3, c.Real, Complex.EPSILON);
            Assert.AreEqual(-2.5, c.Imag, Complex.EPSILON);
        }

        [Test()]
        public void Constructor6()
        {
            string s = "1";
            Complex c = new Complex(s);
            Assert.AreEqual(1.0, c.Real, Complex.EPSILON);
            Assert.AreEqual(0.0, c.Imag, Complex.EPSILON);
        }

        [Test()]
        public void Constructor7()
        {
            TestDelegate f = () => { Complex a = new Complex("1.3-2.5"); };
            Assert.Throws<FormatException>(f);
        }

        [Test()]
        public void Constructor8()
        {
            TestDelegate f = () => { Complex a = new Complex("1.3i-2.5"); };
            Assert.Throws<FormatException>(f);
        }
        [Test()]
        public void Constructor9()
        {
            TestDelegate f = () => { Complex a = new Complex("1.3ij"); };
            Assert.Throws<FormatException>(f);
        }

        [Test()]
        public void Constructor10()
        {
            TestDelegate f = () => { Complex a = new Complex("1.3+3.5546ij"); };
            Assert.Throws<FormatException>(f);
        }

        [Test()]
        public void Constructor11()
        {
            TestDelegate f = () => { Complex a = new Complex("1.3ji"); };
            Assert.Throws<FormatException>(f);
        }

        [Test()]
        public void Constructor12()
        {
            TestDelegate f = () => { Complex a = new Complex("1.3+3.5546ji"); };
            Assert.Throws<FormatException>(f);
        }

        [Test()]
        public void Constructor13()
        {
            string s = "i";
            Complex c = new Complex(s);
            Assert.AreEqual(0.0, c.Real, Complex.EPSILON);
            Assert.AreEqual(1.0, c.Imag, Complex.EPSILON);
        }

        [Test()]
        public void Constructor14()
        {
            string s = "-i";
            Complex c = new Complex(s);
            Assert.AreEqual(0.0, c.Real, Complex.EPSILON);
            Assert.AreEqual(-1.0, c.Imag, Complex.EPSILON);
        }

        [Test()]
        public void Constructor15()
        {
            string s = "0.5+i";
            Complex c = new Complex(s);
            Assert.AreEqual(0.5, c.Real, Complex.EPSILON);
            Assert.AreEqual(1.0, c.Imag, Complex.EPSILON);
        }

        [Test()]
        public void Constructor16()
        {
            string s = "0.5-i";
            Complex c = new Complex(s);
            Assert.AreEqual(0.5, c.Real, Complex.EPSILON);
            Assert.AreEqual(-1.0, c.Imag, Complex.EPSILON);
        }

        [Test()]
        public void Constructor17()
        {
            string s = "+i";
            Complex c = new Complex(s);
            Assert.AreEqual(0.0, c.Real, Complex.EPSILON);
            Assert.AreEqual(1.0, c.Imag, Complex.EPSILON);
        }

        [Test()]
        public void Add()
        {
            Complex a = new Complex(3.0, -5.0);
            Complex b = new Complex(4.0, 2.0);
            Complex c = a + b;
            Assert.AreEqual(7.0, c.Real, Complex.EPSILON);
            Assert.AreEqual(-3.0, c.Imag, Complex.EPSILON);
        }

        [Test()]
        public void Multiply()
        {
            Complex a = new Complex(3.0, 2.0);
            Complex b = new Complex(1.0, 7.0);
            Complex c = a* b;
            Assert.AreEqual(-11.0, c.Real, Complex.EPSILON);
            Assert.AreEqual(23.0, c.Imag, Complex.EPSILON);
        }

        [Test()]
        public void Conjugate()
        {
            Complex a = new Complex(3.0, 2.0);
            Complex c = a.Conjugate();
            Assert.AreEqual(3.0, c.Real, Complex.EPSILON);
            Assert.AreEqual(-2.0, c.Imag, Complex.EPSILON);
        }

        [Test()]
        public void Divide()
        {
            Complex a = new Complex(2.0, 3.0);
            Complex b = new Complex(4.0, -5.0);
            Complex c = a / b;
            Assert.AreEqual(-7.0 / 41.0, c.Real, Complex.EPSILON);
            Assert.AreEqual(22.0 / 41.0, c.Imag, Complex.EPSILON);
        }
    }
}
