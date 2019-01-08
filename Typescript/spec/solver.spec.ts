import { Complex } from "../src/complex";
import { NumberSolutions } from "../src/numberSolutions";
import { Solver } from "../src/solver";

describe("solver tests", () => {
    it("constructor1", () => {
        const f = () => new Solver("1 1\n2 2");
        expect(f).not.toThrow();
    });

    it("constructor2", () => {
        const f = () => new Solver("1 1\nab 2");
        expect(f).toThrow();
    });

    it("constructor3", () => {
        const f = () => new Solver("-1 1\n2 2");
        expect(f).toThrow();
    });

    it("constructor4", () => {
        const f = () => new Solver("1 -1\n2 2");
        expect(f).toThrow();
    });

    it("solve: allow multiple delimiters", () => {
        const s = "1   1 \n\n 2 4";
        const p = new Solver(s);
        p.solve();

        const expectedPartialSolution = [new Complex(2, 0)];

        const actualPartialSolution = p.getSolutionPartial();
        const actualGeneralSolution = p.getSolutionGeneral();

        if (actualPartialSolution === undefined || actualGeneralSolution !== undefined) {
            fail();
        } else {
            expect(NumberSolutions.ONE).toBe(p.getNumberSolutions());
            expect(expectedPartialSolution.length).toBe(actualPartialSolution.length);
            for (let i = 0; i < expectedPartialSolution.length; i += 1) {
                expect(expectedPartialSolution[i].equals(actualPartialSolution[i])).toBe(true);
            }
            expect(undefined).toBe(actualGeneralSolution);
        }
    });

    it("solve1", () => {
        const s = "1 1\n2 4";
        const p = new Solver(s);
        p.solve();

        const expectedPartialSolution = [new Complex(2, 0)];

        const actualPartialSolution = p.getSolutionPartial();
        const actualGeneralSolution = p.getSolutionGeneral();

        if (actualPartialSolution === undefined || actualGeneralSolution !== undefined) {
            fail();
        } else {
            expect(NumberSolutions.ONE).toBe(p.getNumberSolutions());
            expect(expectedPartialSolution.length).toBe(actualPartialSolution.length);
            for (let i = 0; i < expectedPartialSolution.length; i += 1) {
                expect(expectedPartialSolution[i].equals(actualPartialSolution[i])).toBe(true);
            }
            expect(undefined).toBe(actualGeneralSolution);
        }
    });

    it("solve2", () => {
        const s = "2 2\n1 2 3\n4 5 6";
        const p = new Solver(s);
        p.solve();

        const expectedPartialSolution = [new Complex(-1, 0), new Complex(2, 0)];

        const actualPartialSolution = p.getSolutionPartial();
        const actualGeneralSolution = p.getSolutionGeneral();

        if (actualPartialSolution === undefined || actualGeneralSolution !== undefined) {
            fail();
        } else {
            expect(NumberSolutions.ONE).toBe(p.getNumberSolutions());
            expect(expectedPartialSolution.length).toBe(actualPartialSolution.length);
            for (let i = 0; i < expectedPartialSolution.length; i += 1) {
                expect(expectedPartialSolution[i].equals(actualPartialSolution[i])).toBe(true);
            }
            expect(undefined).toBe(actualGeneralSolution);
        }
    });

    it("solve3", () => {
        const s = "2 2\n4 5 7\n3 9 9";
        const p = new Solver(s);
        p.solve();

        const expectedPartialSolution = [new Complex(0.85714, 0), new Complex(0.71429, 0)];

        const actualPartialSolution = p.getSolutionPartial();
        const actualGeneralSolution = p.getSolutionGeneral();

        if (actualPartialSolution === undefined || actualGeneralSolution !== undefined) {
            fail();
        } else {
            expect(NumberSolutions.ONE).toBe(p.getNumberSolutions());
            expect(expectedPartialSolution.length).toBe(actualPartialSolution.length);
            for (let i = 0; i < expectedPartialSolution.length; i += 1) {
                expect(expectedPartialSolution[i].equals(actualPartialSolution[i])).toBe(true);
            }
            expect(undefined).toBe(actualGeneralSolution);
        }
    });

    it("solve4", () => {
        const s = "3 3\n1 1 2 9\n2 4 -3 1\n3 6 -5 0";
        const p = new Solver(s);
        p.solve();

        const expectedPartialSolution = [new Complex(1, 0), new Complex(2, 0), new Complex(3, 0)];

        const actualPartialSolution = p.getSolutionPartial();
        const actualGeneralSolution = p.getSolutionGeneral();

        if (actualPartialSolution === undefined || actualGeneralSolution !== undefined) {
            fail();
        } else {
            expect(NumberSolutions.ONE).toBe(p.getNumberSolutions());
            expect(expectedPartialSolution.length).toBe(actualPartialSolution.length);
            for (let i = 0; i < expectedPartialSolution.length; i += 1) {
                expect(expectedPartialSolution[i].equals(actualPartialSolution[i])).toBe(true);
            }
            expect(undefined).toBe(actualGeneralSolution);
        }
    });

    it("solve5", () => {
        const s = "2 2\n0 1 1\n1 0 1";
        const p = new Solver(s);
        p.solve();

        const expectedPartialSolution = [new Complex(1, 0), new Complex(1, 0)];

        const actualPartialSolution = p.getSolutionPartial();
        const actualGeneralSolution = p.getSolutionGeneral();

        if (actualPartialSolution === undefined || actualGeneralSolution !== undefined) {
            fail();
        } else {
            expect(NumberSolutions.ONE).toBe(p.getNumberSolutions());
            expect(expectedPartialSolution.length).toBe(actualPartialSolution.length);
            for (let i = 0; i < expectedPartialSolution.length; i += 1) {
                expect(expectedPartialSolution[i].equals(actualPartialSolution[i])).toBe(true);
            }
            expect(undefined).toBe(actualGeneralSolution);
        }
    });

    it("solve6", () => {
        const s = "2 2\n0 1 1\n0 2 2";
        const p = new Solver(s);
        p.solve();

        const expectedPartialSolution = [new Complex(0, 0), new Complex(1, 0)];
        const expectedGeneralSolution = ["x1", "1"];

        const actualPartialSolution = p.getSolutionPartial();
        const actualGeneralSolution = p.getSolutionGeneral();

        if (actualPartialSolution === undefined || actualGeneralSolution === undefined) {
            fail();
        } else {
            expect(NumberSolutions.MANY).toBe(p.getNumberSolutions());
            expect(expectedPartialSolution.length).toBe(actualPartialSolution.length);
            for (let i = 0; i < expectedPartialSolution.length; i += 1) {
                expect(expectedPartialSolution[i].equals(actualPartialSolution[i])).toBe(true);
            }
            expect(expectedGeneralSolution).toEqual(actualGeneralSolution);
        }
    });

    it("solve7", () => {
        const s = "2 2\n0 1 1\n0 2 3";
        const p = new Solver(s);
        p.solve();

        const actualPartialSolution = p.getSolutionPartial();
        const actualGeneralSolution = p.getSolutionGeneral();

        if (actualPartialSolution !== undefined || actualGeneralSolution !== undefined) {
            fail();
        } else {
            expect(NumberSolutions.NONE).toBe(p.getNumberSolutions());
        }
    });

    it("solve8", () => {
        const s = "3 4\n0 1 2 9\n0 1 3 1\n1 0 6 0\n2 0 2 0";
        const p = new Solver(s);
        p.solve();

        const actualPartialSolution = p.getSolutionPartial();
        const actualGeneralSolution = p.getSolutionGeneral();

        if (actualPartialSolution !== undefined || actualGeneralSolution !== undefined) {
            fail();
        } else {
            expect(NumberSolutions.NONE).toBe(p.getNumberSolutions());
        }
    });

    it("solve9", () => {
        const s = "3 1\n1 1 2 9";
        const p = new Solver(s);
        p.solve();

        const expectedPartialSolution = [new Complex(9, 0), new Complex(0, 0), new Complex(0, 0)];
        const expectedGeneralSolution = ["9 - x2 - x3 * (2)", "x2", "x3"];

        const actualPartialSolution = p.getSolutionPartial();
        const actualGeneralSolution = p.getSolutionGeneral();

        if (actualPartialSolution === undefined || actualGeneralSolution === undefined) {
            fail();
        } else {
            expect(NumberSolutions.MANY).toBe(p.getNumberSolutions());
            expect(expectedPartialSolution.length).toBe(actualPartialSolution.length);
            for (let i = 0; i < expectedPartialSolution.length; i += 1) {
                expect(expectedPartialSolution[i].equals(actualPartialSolution[i])).toBe(true);
            }
            expect(expectedGeneralSolution).toEqual(actualGeneralSolution);
        }
    });

    it("solve10", () => {
        const s = "4 4\n1 0 0 5 0\n0 0 0 0 0\n0 0 1 4 6\n0 0 5 5 5";
        const p = new Solver(s);
        p.solve();

        const expectedPartialSolution =
            [new Complex(-8.3333333, 0), new Complex(0, 0), new Complex(-0.6666667, 0), new Complex(1.6666667, 0)];
        const expectedGeneralSolution = ["-8.3333", "x2", "-0.6667", "1.6667"];

        const actualPartialSolution = p.getSolutionPartial();
        const actualGeneralSolution = p.getSolutionGeneral();

        if (actualPartialSolution === undefined || actualGeneralSolution === undefined) {
            fail();
        } else {
            expect(NumberSolutions.MANY).toBe(p.getNumberSolutions());
            expect(expectedPartialSolution.length).toBe(actualPartialSolution.length);
            for (let i = 0; i < expectedPartialSolution.length; i += 1) {
                expect(expectedPartialSolution[i].equals(actualPartialSolution[i])).toBe(true);
            }
            expect(expectedGeneralSolution).toEqual(actualGeneralSolution);
        }
    });

    it("solve11", () => {
        const s = "4 4\n2 3 -1 1 1\n8 12 -9 8 3\n4 6 3 -2 3\n2 3 9 -7 3";
        const p = new Solver(s);
        p.solve();

        const expectedPartialSolution =
            [new Complex(0.6, 0), new Complex(0, 0), new Complex(0.2, 0), new Complex(0, 0)];
        const expectedGeneralSolution =
            ["0.6 - x2 * (1.5) - x4 * (0.1)", "x2", "0.2 - x4 * (-0.8)", "x4"];

        const actualPartialSolution = p.getSolutionPartial();
        const actualGeneralSolution = p.getSolutionGeneral();

        if (actualPartialSolution === undefined || actualGeneralSolution === undefined) {
            fail();
        } else {
            expect(NumberSolutions.MANY).toBe(p.getNumberSolutions());
            expect(expectedPartialSolution.length).toBe(actualPartialSolution.length);
            for (let i = 0; i < expectedPartialSolution.length; i += 1) {
                expect(expectedPartialSolution[i].equals(actualPartialSolution[i])).toBe(true);
            }
            expect(expectedGeneralSolution).toEqual(actualGeneralSolution);
        }
    });

    it("solve12", () => {
        const s = "3 3\n1+2i -1.5-1.1i 2.12 91+5i\n-1+3i 1.2+3.5i -3.3 1+15i\n12.31 1.3-5i 12.3i -78.3i";
        const p = new Solver(s);
        p.solve();

        const expectedPartialSolution = [new Complex(6.73335286, -22.99754223),
                                         new Complex(-1.7976071, 2.08404919), new Complex(15.69938581, 7.3960106)];

        const actualPartialSolution = p.getSolutionPartial();
        const actualGeneralSolution = p.getSolutionGeneral();

        if (actualPartialSolution === undefined || actualGeneralSolution !== undefined) {
            fail();
        } else {
            expect(NumberSolutions.ONE).toBe(p.getNumberSolutions());
            expect(expectedPartialSolution.length).toBe(actualPartialSolution.length);
            for (let i = 0; i < expectedPartialSolution.length; i += 1) {
                expect(expectedPartialSolution[i].equals(actualPartialSolution[i])).toBe(true);
            }
            expect(undefined).toBe(actualGeneralSolution);
        }
    });
});
