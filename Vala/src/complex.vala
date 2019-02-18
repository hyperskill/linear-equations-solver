// не можем использовать имя Complex, так как _Complex - это ключевое слово в С
public class ComplexNumber {
    public const double EPSILON = 0.00001;

    public static ComplexNumber add ( ComplexNumber a, ComplexNumber b )
    requires ( a != null )
    requires ( b != null ) {
        return new ComplexNumber ( a.real + b.real, a.imag + b.imag );
    }

    public static ComplexNumber divide ( ComplexNumber a, ComplexNumber b )
    requires ( a != null )
    requires ( b != null ) {
        ComplexNumber bConjugate = b.conjugate ();
        ComplexNumber a1 = ComplexNumber.multiply ( a, bConjugate );
        ComplexNumber b1 = ComplexNumber.multiply ( b, bConjugate );

        return new ComplexNumber ( a1.real / b1.real, a1.imag / b1.real );
    }

    public static ComplexNumber multiply ( ComplexNumber a, ComplexNumber b )
    requires ( a != null )
    requires ( b != null ) {
        return new ComplexNumber ( a.real * b.real - a.imag * b.imag, a.real * b.imag + a.imag * b.real );
    }

    public double real {get; private set; }
    public double imag {get; private set; }

    public ComplexNumber ( double real = 0.0, double imag = 0.0 ) {
        this.real = real;
        this.imag = imag;
    }

    public ComplexNumber.from_string ( string s ) throws Error
    requires ( s != null ) {
        string c = "__";
        double real = 0.0;
        double imag = 0.0;
        int result = s.scanf ( "%lg%lg%2s", &real, &imag, c );
        if ( result != 3 ) {
            real = 0.0;
            result = s.scanf ( "%lg%2s", &imag, c );
            if ( result != 2 ) {
                c = "__";
                imag = 0.0;
                result = s.scanf ( "%lg", &real );
                if ( result == 1 ) {
                    c = "i";
                } else {
                    result = s.scanf ( "%2s", c );
                    if ( result == 1 ) {
                        if ( c == "i" || c == "+i" ) {
                            imag = 1.0;
                            c = "i";
                        } else if ( c == "-i" ) {
                            imag = -1.0;
                            c = "i";
                        }
                    }
                }
            } else {
                if ( c == "+i" ) {
                    real = imag;
                    imag = 1.0;
                    c = "i";
                } else if ( c == "-i" ) {
                    real = imag;
                    imag = -1.0;
                    c = "i";
                }
            }
        }
        if ( c != "i" ) {
            throw new Error.PARSE_COMPLEX ( "can't parse complex" );
        }
        this.real = real;
        this.imag = imag;
    }

    public ComplexNumber conjugate () {
        return new ComplexNumber ( real, -imag );
    }

    public bool equals ( ComplexNumber o )
    requires ( o != null ) {
        return Math.fabs ( o.imag - imag ) < EPSILON && Math.fabs ( o.real - real ) < EPSILON;
    }

    public string to_string () {
        string result = "";
        double real = Util.round ( this.real * 10000.0 ) / 10000.0;
        double imag = Util.round ( this.imag * 10000.0 ) / 10000.0;
        if ( Math.fabs ( imag ) < EPSILON ) {
            result = "%g".printf ( real );
        } else if ( Math.fabs ( real ) < EPSILON ) {
            result = "%gi".printf ( imag );
        } else {
            unowned string prefix = ( imag > 0 ) ? "+" : "";
            result = "%g%s%gi".printf ( real, prefix, imag );
        }

        return result;
    }
}
