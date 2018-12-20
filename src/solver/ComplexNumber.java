import java.util.Scanner;


class ComplexNumber {
	double re;
	double im;

	public ComplexNumber(double re, double im) {
		this.re = re;
		this.im = im;
	}

	public double getRe() {
		return this.re;
	}

	public double getIm() {
		return this.im;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {return true;}
		if (obj == null) {return false;}
		if (obj instanceof ComplexNumber) {
			ComplexNumber num = (ComplexNumber) obj;
			return this.getRe() == num.getRe() && this.getIm() == num.getIm();
		}
		return false;
	} 
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	public String toString() {
		return "" + this.re + " + i*" + this.im;
	}

	ComplexNumber add(ComplexNumber num) {
		ComplexNumber result = new ComplexNumber(0, 0);
		result.re = num.re + this.re;
		result.im = num.im + this.im;
		return result;
	}

	ComplexNumber mul(ComplexNumber num) {
		ComplexNumber result = new ComplexNumber(0, 0);
		result.re = num.re * this.re - num.im * this.im;
		result.im = num.re * this.im + num.im * this.re;
		return result;
	}

	ComplexNumber inverse() {
		ComplexNumber result = new ComplexNumber(0, 0);
		double module = 0;
		module = this.re * this.re + this.im * this.im;
		result.re = this.re / module;
		result.im = -this.im / module;
		return result;
	}
}
