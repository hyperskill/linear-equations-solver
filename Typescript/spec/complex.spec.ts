import { Complex } from "../src/complex";

describe("complex tests", () => {
    it("equals1", () => {
        const a = new Complex(1, 2);
        const b = new Complex(1, 2);

        expect(a.equals(b)).toBe(true);
    });

    it("equals2", () => {
        const a = new Complex(1, 2);
        const b = new Complex(1.1, 2);

        expect(a.equals(b)).toBe(false);
    });

    it("equals3", () => {
        const a = new Complex(1, 2.1);
        const b = new Complex(1, 2);

        expect(a.equals(b)).toBe(false);
    });

    it("toString1", () => {
        const a = new Complex(1, 0);

        expect("1").toEqual(a.toString());
    });

    it("toString2", () => {
        const a = new Complex(0, 1);

        expect("1i").toEqual(a.toString());
    });

    it("toString3", () => {
        const a = new Complex(-2.4, 1.5);

        expect("-2.4+1.5i").toEqual(a.toString());
    });

    it("toString4", () => {
        const a = new Complex(2.4, -1.5);

        expect("2.4-1.5i").toEqual(a.toString());
    });

    it("toString5", () => {
        const a = new Complex(0, 0);

        expect("0").toEqual(a.toString());
    });

    it("defaultConstructor", () => {
        const c = new Complex();

        expect(0).toBeCloseTo(c.getReal());
        expect(0).toBeCloseTo(c.getImag());
    });

    it("constructor", () => {
        const c = new Complex(1, 0);

        expect(1).toBeCloseTo(c.getReal());
        expect(0).toBeCloseTo(c.getImag());
    });

    it("fromString1", () => {
        const s = "-1.3";
        const c = Complex.fromString(s);

        expect(-1.3).toBeCloseTo(c.getReal());
        expect(0).toBeCloseTo(c.getImag());
    });

    it("fromString2", () => {
        const s = "2.5i";
        const c = Complex.fromString(s);

        expect(0).toBeCloseTo(c.getReal());
        expect(2.5).toBeCloseTo(c.getImag());
    });

    it("fromString3", () => {
        const s = "1.3-2.5i";
        const c = Complex.fromString(s);

        expect(1.3).toBeCloseTo(c.getReal());
        expect(-2.5).toBeCloseTo(c.getImag());
    });

    it("fromString4", () => {
        const s = "1";
        const c = Complex.fromString(s);

        expect(1).toBeCloseTo(c.getReal());
        expect(0).toBeCloseTo(c.getImag());
    });

    it("fromString5", () => {
        const f = () => Complex.fromString("gdc");
        expect(f).toThrow();
    });

    it("fromString6", () => {
        const f = () => Complex.fromString("1.3-2.5");
        expect(f).toThrow();
    });

    it("fromString7", () => {
        const f = () => Complex.fromString("1.3i-2.5");
        expect(f).toThrow();
    });

    it("add", () => {
        const a = new Complex(3, -5);
        const b = new Complex(4, 2);
        const c = Complex.add(a, b);

        expect(7).toBeCloseTo(c.getReal());
        expect(-3).toBeCloseTo(c.getImag());
    });

    it("multiply", () => {
        const a = new Complex(3, 2);
        const b = new Complex(1, 7);
        const c = Complex.multiply(a, b);

        expect(-11).toBeCloseTo(c.getReal());
        expect(23).toBeCloseTo(c.getImag());
    });

    it("conjugate", () => {
        const a = new Complex(3, 2);
        const c = a.conjugate();

        expect(3).toBeCloseTo(c.getReal());
        expect(-2).toBeCloseTo(c.getImag());
    });

    it("divide", () => {
        const a = new Complex(2, 3);
        const b = new Complex(4, -5);
        const c = Complex.divide(a, b);

        expect(-7 / 41).toBeCloseTo(c.getReal());
        expect(22 / 41).toBeCloseTo(c.getImag());
    });
});
