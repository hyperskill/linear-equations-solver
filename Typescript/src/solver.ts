import * as fs from "fs";
import * as os from "os";
import { Complex } from "./complex";
import { NumberSolutions } from "./numberSolutions";

export class Solver {
    private static readonly MINUS_ONE = new Complex(-1, 0);
    private static readonly ONE = new Complex(1, 0);
    private static readonly ZERO = new Complex(0, 0);

    private readonly matrix: Complex[][];
    private readonly numberEquations: number;
    private numberSolutions = NumberSolutions.ONE;
    private readonly numberVariables: number;
    private readonly solutionGeneral: string[];
    private readonly solutionIndexes: number[];
    private readonly solutionPartial: Complex[];
    private readonly verbose: boolean;

    public constructor(s: string, verbose = false) {
        const args = s.split(new RegExp("\r\n|\r|\n| |\t")).filter((x: string) => x.length !== 0);
        let argsI = 0;
        this.numberVariables = Number.parseInt(args[argsI], 10);
        argsI += 1;
        const realNumberEquations = Number.parseInt(args[argsI], 10);
        argsI += 1;
        if (this.numberVariables < 1 || realNumberEquations < 1) {
            throw new Error("wrong data");
        }
        this.numberEquations = (realNumberEquations < this.numberVariables) ? this.numberVariables
            : realNumberEquations;
        this.matrix = [];
        for (let i = 0; i < realNumberEquations; i += 1) {
            this.matrix.push(new Array<Complex>(this.numberVariables + 1));
            for (let j = 0; j < this.numberVariables + 1; j += 1) {
                this.matrix[i][j] = Complex.fromString(args[argsI]);
                argsI += 1;
            }
        }
        for (let i = realNumberEquations; i < this.numberEquations; i += 1) {
            this.matrix.push(new Array<Complex>(this.numberVariables + 1));
            for (let j = 0; j < this.numberVariables + 1; j += 1) {
                this.matrix[i][j] = new Complex();
            }
        }
        this.solutionPartial = new Array<Complex>(this.numberVariables);
        this.solutionGeneral = new Array<string>(this.numberVariables);
        this.solutionIndexes = new Array<number>(this.numberVariables);
        for (let i = 0; i < this.numberVariables; i += 1) {
            this.solutionIndexes[i] = i;
        }
        this.verbose = verbose;
    }

    public readonly getNumberSolutions = () => this.numberSolutions;

    public readonly getSolutionGeneral = (): string[] | undefined => {
        if (this.numberSolutions !== NumberSolutions.MANY) {
            return undefined;
        }

        return this.solutionGeneral;
    }

    public readonly getSolutionPartial = (): Complex[] | undefined => {
        if (this.numberSolutions === NumberSolutions.NONE) {
            return undefined;
        }

        return this.solutionPartial;
    }

    public readonly solve = (): void => {
        if (this.verbose) {
            console.log("Start solving the equation.");
            console.log("Rows manipulation:");
        }
        this.gausFirstStep();
        if (this.numberSolutions !== NumberSolutions.NONE) {
            this.gausSecondStep();
            this.generateSolutions();
            this.checkThatSolutionIsSane();
        }
        this.printSolution();
    }

    public readonly writeSolutionToFile = (out: string): void => {
        const file = fs.createWriteStream(out);
        this.printSolutionInternal(file);
        file.close();
        if (this.verbose) {
            console.log(`Saved to file ${out}`);
        }
    }

    private readonly addKRow1ToRow2 = (k: Complex, row1: number, row2: number): void => {
        if (this.verbose) {
            console.log(`${k.toString()} * R${row1 + 1} +R${row2 + 1} -> R${row2 + 1}`);
        }
        for (let i = 0; i < this.numberVariables + 1; i += 1) {
            const temp = Complex.multiply(k, this.matrix[row1][i]);
            this.matrix[row2][i] = Complex.add(temp, this.matrix[row2][i]);
        }
    }

    private readonly checkThatSolutionIsSane = (): void => {
        for (let i = this.numberVariables; i < this.numberEquations; i += 1) {
            let sum = new Complex(0, 0);
            for (let j = 0; j < this.numberVariables; j += 1) {
                const temp = Complex.multiply(
                    this.solutionPartial[this.solutionIndexes[j]],
                    this.matrix[i][this.solutionIndexes[j]]);
                sum = Complex.add(sum, temp);
            }
            if (!sum.equals(this.matrix[i][this.numberVariables])) {
                this.numberSolutions = NumberSolutions.NONE;

                return;
            }
        }
    }

    private readonly divideRow = (row: number, k: Complex): void => {
        if (this.verbose) {
            console.log(`R${row + 1} / ${k.toString()} -> R${row + 1}`);
        }
        const n = this.matrix[row].length;
        for (let i = 0; i < n; i += 1) {
            this.matrix[row][i] = Complex.divide(this.matrix[row][i], k);
        }
    }

    private readonly gausFirstStep = (): void => {
        for (let i = 0; i < this.numberVariables; i += 1) {
            if (this.matrix[i][i].equals(Solver.ZERO)) {
                let foundNonZeroElement = false;
                for (let j = i + 1; j < this.numberEquations; j += 1) {
                    if (!this.matrix[j][i].equals(Solver.ZERO)) {
                        this.swapRows(i, j);
                        foundNonZeroElement = true;
                        break;
                    }
                }
                if (!foundNonZeroElement) {
                    for (let j = i + 1; j < this.numberEquations; j += 1) {
                        if (!this.matrix[i][j].equals(Solver.ZERO)) {
                            this.swapColumns(i, j);
                            foundNonZeroElement = true;
                            break;
                        }
                    }
                }

                if (!foundNonZeroElement) {
                    for (let k = i + 1; !foundNonZeroElement && k < this.numberVariables; k += 1) {
                        for (let j = i + 1; j < this.numberEquations; j += 1) {
                            if (!this.matrix[j][k].equals(Solver.ZERO)) {
                                this.swapColumns(k, i);
                                this.swapRows(j, i);
                                foundNonZeroElement = true;
                                break;
                            }
                        }
                    }
                }

                if (!foundNonZeroElement) {
                    if (this.matrix[i][this.numberEquations].equals(Solver.ZERO)) {
                        this.numberSolutions = NumberSolutions.MANY;
                        continue;
                    } else {
                        this.numberSolutions = NumberSolutions.NONE;

                        return;
                    }
                }
            }

            if (!this.matrix[i][i].equals(Solver.ONE)) {
                this.divideRow(i, this.matrix[i][i]);
            }
            for (let j = i + 1; j < this.numberEquations && j < this.numberVariables; j += 1) {
                const k = Complex.multiply(Solver.MINUS_ONE, this.matrix[j][i]);
                if (!k.equals(Solver.ZERO)) {
                    this.addKRow1ToRow2(k, i, j);
                }
            }
        }
    }

    private readonly gausSecondStep = (): void => {
        for (let i = this.numberVariables - 1; i >= 0; i -= 1) {
            for (let j = i - 1; j >= 0; j -= 1) {
                const k = Complex.multiply(Solver.MINUS_ONE, this.matrix[j][i]);
                if (!k.equals(Solver.ZERO)) {
                    this.addKRow1ToRow2(k, i, j);
                }
            }
        }
    }

    private readonly generateSolutions = (): void => {
        for (let i = 0; i < this.numberEquations && i < this.numberVariables; i += 1) {
            this.solutionPartial[this.solutionIndexes[i]] = this.matrix[i][this.numberVariables];
            if (this.matrix[i][i].equals(Solver.ZERO)) {
                this.solutionGeneral[this.solutionIndexes[i]] = `x${this.solutionIndexes[i] + 1}`;
            } else {
                this.solutionGeneral[this.solutionIndexes[i]] = this.matrix[i][this.numberVariables].toString();
                for (let j = i + 1; j < this.numberVariables; j += 1) {
                    if (this.matrix[i][j].equals(Solver.ONE)) {
                        this.solutionGeneral[this.solutionIndexes[i]] =
                            `${this.solutionGeneral[this.solutionIndexes[i]]} - x${this.solutionIndexes[j] + 1}`;
                    } else if (!this.matrix[i][j].equals(Solver.ZERO)) {
                        this.solutionGeneral[this.solutionIndexes[i]] =
                        // tslint:disable-next-line:max-line-length
                        `${this.solutionGeneral[this.solutionIndexes[i]]} - x${this.solutionIndexes[j] + 1} * (${this.matrix[i][j].toString()})`;
                    }
                }
            }
        }
    }

    private readonly printSolution = (): void => {
        if (this.verbose) {
            this.printSolutionInternal();
        }
    }

    private readonly printSolutionInternal = (writer: fs.WriteStream | undefined = undefined): void => {
        let result: string;
        switch (this.numberSolutions) {
            case NumberSolutions.NONE:
                result = "There are no solutions";
                break;
            case NumberSolutions.ONE:
                result = `(${this.solutionPartial[0].toString()}`;
                for (let i = 1; i < this.solutionPartial.length; i += 1) {
                    result += `, ${this.solutionPartial[i].toString()}`;
                }
                result += ")";
                break;
            case NumberSolutions.MANY:
                result = `(${this.solutionGeneral[0]}`;
                for (let i = 1; i < this.solutionPartial.length; i += 1) {
                    result += `, ${this.solutionGeneral[i]}`;
                }
                result +=  ")";
                break;
            default:
                throw new Error("Never should happend");
        }
        if (writer instanceof fs.WriteStream) {
            writer.end(`${result}${os.EOL}`);
        } else {
            console.log(result);
        }
    }

    private readonly swapColumns = (column1: number, column2: number): void => {
        if (this.verbose) {
            console.log(`C${column1 + 1} <-> C${column2 + 1}`);
        }
        const n = this.matrix.length;
        for (let i = 0; i < n; i += 1) {
            const temp1 = this.matrix[i][column1];
            this.matrix[i][column1] = this.matrix[i][column2];
            this.matrix[i][column2] = temp1;
        }
        const temp2 = this.solutionIndexes[column1];
        this.solutionIndexes[column1] = this.solutionIndexes[column2];
        this.solutionIndexes[column2] = temp2;
    }

    private readonly swapRows = (row1: number, row2: number): void => {
        if (this.verbose) {
            console.log(`R${row1 + 1} <-> R${row2 + 1}`);
        }
        for (let i = 0; i < this.numberVariables + 1; i += 1) {
            const temp = this.matrix[row1][i];
            this.matrix[row1][i] = this.matrix[row2][i];
            this.matrix[row2][i] = temp;
        }
    }
}
