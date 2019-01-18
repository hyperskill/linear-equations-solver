// This is an independent project of an individual developer. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
using System;
namespace src
{
    public class Complex
    {
        public static readonly double EPSILON = 0.00001;

        public static bool operator ==(Complex a, Complex b)
        {
            return !(a is null) && !(b is null) && Math.Abs(a.Imag - b.Imag) < EPSILON && Math.Abs(a.Real - b.Real) < EPSILON;
        }

        public static bool operator !=(Complex a, Complex b)
        {
            return !(a == b);
        }

        public static Complex operator +(Complex a, Complex b)
        {
            return new Complex(a.Real + b.Real, a.Imag + b.Imag);
        }

        public static Complex operator /(Complex a, Complex b)
        {
            Complex bConjugate = b.Conjugate();
            Complex a1 = a * bConjugate;
            Complex b1 = b * bConjugate;

            return new Complex(a1.Real / b1.Real, a1.Imag / b1.Real);
        }

        public static Complex operator *(Complex a, Complex b)
        {
            return new Complex(a.Real * b.Real - a.Imag * b.Imag, a.Real * b.Imag + a.Imag * b.Real);
        }

        public double Real { get; private set; }
        public double Imag { get; private set; }

        public Complex(double real = 0.0, double imag = 0.0)
        {
            Real = real;
            Imag = imag;
        }

        public Complex(string s)
        {
            IFormatProvider iFormatProvider = new System.Globalization.CultureInfo("en-US");
            String[] strs = Split(s);
            Real = Double.Parse(strs[0], iFormatProvider);
            Imag = Double.Parse(strs[1], iFormatProvider);
        }

        public Complex Conjugate()
        {
            return new Complex(Real, -Imag);
        }

        public override string ToString()
        {
            IFormatProvider iFormatProvider = new System.Globalization.CultureInfo("en-US");
            if (Math.Abs(Imag) < EPSILON)
            {
                return string.Format(iFormatProvider, "{0:0.####}", Real);
            }
            if (Math.Abs(Real) < EPSILON)
            {
                return string.Format(iFormatProvider, "{0:0.####}i", Imag);
            }
            string prefix = (Imag > 0) ? "+" : "";
            return string.Format(iFormatProvider, "{0:0.####}{1}{2:0.####}i", Real, prefix, Imag);
        }

        private string[] Split(string s)
        {
            string realString = "0";
            string imagString = "0";
            int i = 1;
            for (; i < s.Length; ++i)
            {
                if (s[i] == '+' || s[i] == '-')
                {
                    realString = s.Substring(0, i);
                    imagString = s.Substring(i, s.Length - i - 1);
                    if (s[s.Length - 1] != 'i')
                    {
                        throw new FormatException("can't parse complex");
                    }
                    break;
                }
                if (s[i] == 'i')
                {
                    if (i != s.Length - 1)
                    {
                        throw new FormatException("can't parse complex");
                    }
                    imagString = s.Substring(0, i);
                    break;
                }
            }
            if (i == s.Length)
            {
                realString = s;
            }
            return new string[] { realString, imagString };
        }

        public override bool Equals(object obj)
        {
            return obj is Complex && this == (Complex)obj;
        }

        public override int GetHashCode()
        {
            return ToString().GetHashCode();
        }
    }
}
