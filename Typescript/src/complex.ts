const split = (s: string): string[] => {
    let realString = "0";
    let imagString = "0";
    let i = 1;
    for (; i < s.length; i += 1) {
        if (s.charAt(i) === "+" || s.charAt(i) === "-") {
            realString = s.substring(0, i);
            imagString = s.substring(i, s.length - 1);
            if (s.charAt(s.length - 1) !== "i") {
                throw new Error("can't parse complex");
            }
            break;
        }
        if (s.charAt(i) === "i") {
            if (i !== s.length - 1) {
                throw new Error("can;t parse complex");
            }
            imagString = s.substring(0, i);
            break;
        }
    }
    if (i === s.length) {
        realString = s;
    }

    return [realString, imagString];
};

export class Complex {
    public static readonly EPSILON = 0.00001;

    public static readonly add = (a: Complex, b: Complex) =>
        new Complex(a.real + b.real, a.imag + b.imag)

    public static readonly divide = (a: Complex, b: Complex): Complex => {
        const bConjugate = b.conjugate();
        const a1 = Complex.multiply(a, bConjugate);
        const b1 = Complex.multiply(b, bConjugate);

        return new Complex(a1.real / b1.real, a1.imag / b1.real);
    }

    public static readonly fromString = (s: string): Complex => {
        const strs = split(s);
        const real = Number.parseFloat(strs[0]);
        const imag = Number.parseFloat(strs[1]);
        if (Number.isNaN(real) || Number.isNaN(imag)) {
            throw new Error("can't parse complex");
        }

        return new Complex(real, imag);
    }

    public static readonly multiply = (a: Complex, b: Complex) =>
        new Complex(a.real * b.real - a.imag * b.imag, a.real * b.imag + a.imag * b.real)

    private imag: number;
    private real: number;
    public constructor(real = 0, imag = 0) {
        this.real = real;
        this.imag = imag;
    }

    public readonly conjugate = () => new Complex(this.real, -this.imag);

    public readonly equals = (o: Complex): boolean =>
        Math.abs(o.imag - this.imag) < Complex.EPSILON &&
        Math.abs(o.real - this.real) < Complex.EPSILON

    public readonly getImag = () => this.imag;

    public readonly getReal = () => this.real;

    public readonly toString = (): string => {
        const real = (Math.round(this.real * 10000) / 10000).toString();
        const imag = (Math.round(this.imag * 10000) / 10000).toString();

        if (Math.abs(this.imag) < Complex.EPSILON) {
            return real;
        }
        if (Math.abs(this.real) < Complex.EPSILON) {
            return `${imag}i`;
        }
        const prefix = (this.imag > 0) ? "+" : "";

        return `${real}${prefix}${imag}i`;
    }
}
