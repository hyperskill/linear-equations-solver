package solver;

/**
 * Created by DIMA, on 16.01.2019
 */
public class Complex {
    private double realPart;
    private double imaginaryPart;

    public Complex(double realPart, double imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    public Complex(String item){
        String[] items = item.split("[-,+]");
        int len = items.length;
        double real = 0;
        double imaginary = 0;
        switch (len){
            case 3:
                String[] items1 = item.split("\\+");
                if(items1[0].endsWith("i")){
                    // -real && -imaginary
                    real = Double.parseDouble(items[1]) * -1;
                    imaginary = Double.parseDouble(items[2].substring(0, items[2].length()-1)) * -1;
                }else {
                    // -real && imaginary
                    real = Double.parseDouble(items[1]) * -1;
                    imaginary = Double.parseDouble(items[2].substring(0, items[2].length()-1));
                }
                break;
            case 2:
                if(items[0].equals("")){
                    // -real || -imaginary
                    if(items[1].endsWith("i")){
                        // -imaginary
                        imaginary = Double.parseDouble(items[1].substring(0, items[1].length()-1)) * -1;
                        real = 0;
                    }else {
                        // -real
                        real = Double.parseDouble(items[1]) * -1;
                        imaginary = 0;
                    }
                }else {
                    // real && imaginary
                    items1 = item.split("\\+");
                    if(items1[0].endsWith("i")){
                        // real && -imaginary
                        real = Double.parseDouble(items[0]);
                        imaginary = Double.parseDouble(items[1].substring(0, items[1].length()-1)) * -1;
                    }else {
                        // real && imaginary
                        real = Double.parseDouble(items[0]);
                        imaginary = Double.parseDouble(items[1].substring(0, items[1].length()-1));
                    }

                }
                break;
            case 1:
                if(items[0].endsWith("i")){
                    // imaginary
                    imaginary = Double.parseDouble(items[0].substring(0, items[0].length()-1));
                    real = 0;
                }else {
                    // real
                    real = Double.parseDouble(items[0]);
                    imaginary = 0;
                }
                break;

        }
        this.realPart = real;
        this.imaginaryPart = imaginary;
    }

    public double getRealPart() {
        return realPart;
    }

    public double getImaginaryPart() {
        return imaginaryPart;
    }

    @Override
    public String toString() {
        String s = "";
        if(realPart != 0){
            s += realPart;
        }
        if(imaginaryPart > 0 && realPart != 0){
            return s + "+" + imaginaryPart + "i";
        }else if(imaginaryPart > 0 && realPart == 0){
            return imaginaryPart + "i";
        }else if(imaginaryPart < 0){
            return s + imaginaryPart + "i";
        }else if(realPart !=0 ){
            return s;
        }else {
            return "0";
        }
    }

    public Complex adding(Complex c){
        double realC = c.realPart;
        double imaginaryC = c.imaginaryPart;
        double realResult = realPart + realC;
        double imaginaryResult = imaginaryPart + imaginaryC;
        return new Complex(realResult, imaginaryResult);
    }

    public Complex subtracting(Complex c){
        double realC = c.realPart;
        double imaginaryC = c.imaginaryPart;
        double realResult = realPart - realC;
        double imaginaryResult = imaginaryPart - imaginaryC;
        return new Complex(realResult, imaginaryResult);
    }



    public Complex multiply(Complex c){
        double realC = c.realPart;
        double imaginaryC = c.imaginaryPart;
        double realResult = realPart*realC - imaginaryPart*imaginaryC;
        double imaginaryResult = realPart*imaginaryC + imaginaryPart*realC;
        return new Complex(realResult, imaginaryResult);
    }

    private Complex conjugating(Complex c){
        double realC = c.realPart;
        double imaginaryC = c.imaginaryPart;
        double imaginaryCResult = imaginaryC * (-1);
        return new Complex(realC, imaginaryCResult);
    }

    Complex divide(Complex divisor){
        Complex conjugate = conjugating(divisor);
        Complex newDivident = multiply(conjugate);
        double newDivisor = divisor.realPart*divisor.realPart + divisor.imaginaryPart*divisor.imaginaryPart;
        return new Complex(newDivident.realPart/newDivisor, newDivident.imaginaryPart/newDivisor);
    }

    Complex changeSign(){
        return multiply(new Complex(-1, 0));
    }
}
