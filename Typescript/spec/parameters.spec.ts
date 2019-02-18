import { Parameters } from "../src/parameters";

describe("parameters tests", () => {
    it("defaultParameters", () => {
        const p = new Parameters([]);

        expect(p.in).toBe("input.txt");
        expect(p.out).toBe("output.txt");
        expect(p.verbose).toBe(false);
    });

    it("in", () => {
        const p = new Parameters(["-in", "in.txt"]);
        expect(p.in).toBe("in.txt");
    });

    it("out", () => {
        const p = new Parameters(["-out", "out2.txt"]);
        expect(p.out).toBe("out2.txt");
    });

    it("verbose", () => {
        const p = new Parameters(["-verbose"]);
        expect(p.verbose).toBe(true);
    });
});
