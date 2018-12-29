package solver;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Complex {
    private static final double epsilon = 0.00001;
    private double real;
    private double imag;

    public Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    public Complex() {
        this(0.0, 0.0);
    }

    @Override
    public String toString() {
        final DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        final DecimalFormat realFormat = new DecimalFormat("0.####", symbols);
        final DecimalFormat imagFormat = new DecimalFormat("0.####i", symbols);
        if (Math.abs(imag) < epsilon) {
            return realFormat.format(real);
        }
        if (Math.abs(real) < epsilon) {
            return imagFormat.format(imag);
        }
        imagFormat.setPositivePrefix("+");
        return String.format("%s%s", realFormat.format(real), imagFormat.format(imag));
    }

    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (!(other instanceof Complex)) {
            return false;
        }

        Complex o = (Complex) other;
        if (Math.abs(o.imag - imag) < epsilon && Math.abs(o.real - real) < epsilon) {
            return true;
        }
        return false;
    }

    @NotNull
    @Contract("_, _ -> new")
    public static Complex add(@NotNull Complex a, @NotNull Complex b) {
        return new Complex(a.real + b.real, a.imag + b.imag);
    }

    @NotNull
    @Contract("_, _ -> new")
    public static Complex multiply(@NotNull Complex a, @NotNull Complex b) {
        return new Complex(a.real * b.real - a.imag * b.imag, a.real * b.imag + a.imag * b.real);
    }

    @NotNull
    @Contract("_, _ -> new")
    public static Complex divide(@NotNull Complex a, @NotNull Complex b) {
        final Complex bConjugate = b.conjugate();
        final Complex a1 = Complex.multiply(a, bConjugate);
        final Complex b1 = Complex.multiply(b, bConjugate);
        assert (Math.abs(b1.imag) < epsilon);
        return new Complex(a1.real / b1.real, a1.imag / b1.real);
    }

    @NotNull
    @Contract("_ -> new")
    private String[] split(@NotNull String s) {
        String realString = "0";
        String imagString = "0";
        int i = 1;
        for (; i < s.length(); ++i) {
            if (s.charAt(i) == '+' || s.charAt(i) == '-') {
                realString = s.substring(0, i);
                imagString = s.substring(i, s.length() - 1);
                break;
            }
            if (s.charAt(i) == 'i') {
                imagString = s.substring(0, i);
                break;
            }
        }
        if (i == s.length()) {
            realString = s;
        }
        return new String[]{realString, imagString};
    }

    public Complex(@NotNull String s) {
        final String[] strs = split(s);
        real = Double.parseDouble(strs[0]);
        imag = Double.parseDouble(strs[1]);
    }

    public double getReal() {
        return real;
    }

    public double getImag() {
        return imag;
    }

    public Complex conjugate() {
        return new Complex(real, -imag);
    }
}
