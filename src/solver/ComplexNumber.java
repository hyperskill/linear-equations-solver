package solver;

public class ComplexNumber {

    public void set(double re, double im) {
        this.re = re;
        this.im = im;
    }

    private double re;//действительная часть
    private double im;//мнимая часть

    public double getRe() {
        return re;
    }

    public double getIm() {
        return im;
    }

    public ComplexNumber(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public ComplexNumber(String number) throws ParsingArrayException {
        try {
            if (number==null) {
                this.re = 0;
                this.im = 0;
            } else if (!number.contains("i")) {
                this.re = Double.parseDouble(number);
                this.im = 0;
            } else {
                boolean firstPositive = true;
                boolean secondPositive = true;
                if (number.charAt(0) == '-')     // See if first expr is negative
                    firstPositive = false;
                if (number.substring(1).contains("-"))
                    secondPositive = false;
                String[] split = number.split("[+-]");
                if (split[0].equals("")) {  // Handle expr beginning with `-`
                    split[0] = split[1];
                    if (split[0].contains("i")) {
                        split[0] = "0";
                        secondPositive = firstPositive;
                    }
                    if (split.length>2) split[1] = split[2];
                }
                double realPart = 0;
                double imgPart = 0;
                if (split[0].contains("i")) // Assumes input is not empty
                    imgPart = Double.parseDouble((firstPositive ? "+" : "-") +
                            split[0].substring(0,split[0].length() - 1));
                else
                    realPart = Double.parseDouble((firstPositive ? "+" : "-") + split[0]);
                if (split.length > 1) {     // Parse second part of expr if it exists
                    if (split[1].contains("i"))
                        imgPart = Double.parseDouble((secondPositive ? "+" : "-") +
                                split[1].substring(0,split[1].length() - 1));
                    else
                        realPart = Double.parseDouble((secondPositive ? "+" : "-") + split[1]);
                }
                this.re = realPart;
                this.im = imgPart;
            }
        } catch (NumberFormatException e) {
            throw new ParsingArrayException(String.format("The string '%s' cannot be parsed as complex number", number),
                    e);
        }
    }

    public static ComplexNumber sAdd(ComplexNumber n1, ComplexNumber n2){
        return new ComplexNumber(n2.re+n1.re, n1.im+n2.im);
    }

    public void add(ComplexNumber n){
        this.re+=n.re;
        this.im+=n.im;
    }

    public void mult(ComplexNumber n){
        //(a+bi)(c+di) = (ac−bd) + (ad+bc)i;
        this.re=this.re*n.re-this.im*n.im;
        this.im=this.re*n.im+this.im*n.re;
    }

    public static ComplexNumber mult(ComplexNumber n1, ComplexNumber n2){
        //(a+bi)(c+di) = (ac−bd) + (ad+bc)i;
        double re;
        double im;
        re=n1.re*n2.re-n1.im*n2.im;
        im=n1.re*n2.im+n1.im*n2.re;
        return new ComplexNumber(re,im);
    }

    public void div(ComplexNumber n){
        //a+bi/c+di = (ac+bd)/(c^2+d^2)+(bc-ad)/((c^2+d^2))i
        double a= this.re;
        double b =this.im;
        double c= n.re;
        double d= n.im;
        this.re = (a*c+b*d)/(c*c+d*d);
        this.im = (b*c-a*d)/((c*c+d*d));
    }

    public static ComplexNumber div(ComplexNumber n1,ComplexNumber n2){
        //a+bi/c+di = (ac+bd)/(c^2+d^2)+(bc-ad)/((c^2+d^2))i
        double a= n1.re;
        double b =n1.im;
        double c= n2.re;
        double d= n2.im;
        double re = (a*c+b*d)/(a*a+d*d);
        double im = (b*c-a*d)/((a*a+d*d));
        return new ComplexNumber(re, im);
    }

    @Override
    public String toString() {
        return (re==0?"":String.format("%-5.3f ",re))+((im>0&&re!=0)?"+":"")+(im==0?"":String.format("%-5.3fi ",im));
    }

    public static boolean compareComplex(ComplexNumber n1, ComplexNumber n2){
        return Matrix.compareDouble(n1.getRe(),n2.getRe()) && Matrix.compareDouble(n1.getIm(),n2.getIm());
    }

    public static boolean compareComplexToDouble(ComplexNumber n1, double n2){
        return Matrix.compareDouble(n1.getRe(),n2) && Matrix.compareDouble(n1.getIm(),0);
    }
}
