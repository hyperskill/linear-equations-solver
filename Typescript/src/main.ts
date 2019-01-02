import * as fs from "fs";
import { Parameters } from "./parameters";
import { Solver } from "./solver";
try {
    const p = new Parameters(process.argv);
    const s = new Solver(fs.readFileSync(p.in, "utf8"), p.verbose);
    s.solve();
    s.writeSolutionToFile(p.out);
} catch (e) {
    console.error(`An exception occurs ${(e as Error).message}`);
}
