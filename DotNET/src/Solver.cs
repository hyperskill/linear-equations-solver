// This is an independent project of an individual developer. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
using System;
using System.Linq;
using System.IO;
using System.Diagnostics;
namespace src
{
    public class Solver
    {
        private static readonly Complex ZERO = new Complex(0.0, 0.0);
        private static readonly Complex ONE = new Complex(1.0, 0.0);
        private static readonly Complex MINUS_ONE = new Complex(-1.0, 0.0);

        private readonly int numberEquations;
        private readonly int numberVariables;
        private readonly Complex[,] matrix;
        private readonly Complex[] solutionPartial;
        private string[] solutionGeneral;
        private readonly int[] solutionIndexes;
        private readonly bool verbose;
        public NumberSolutions NumberSolutions { get; private set; }


        public Solver(string s, bool verbose = false)
        {
            NumberSolutions = NumberSolutions.ONE;
            var args = s.Split(new char[] { '\r', '\n', ' ', '\t' }, StringSplitOptions.RemoveEmptyEntries);
            int argsI = 0;
            numberVariables = int.Parse(args[argsI++]);
            int realNumberEquations = int.Parse(args[argsI++]);
            if (numberVariables < 1 || realNumberEquations < 1) {
                throw new FormatException("wrong input data");
            }
            numberEquations = (realNumberEquations < numberVariables) ? numberVariables : realNumberEquations;
            matrix = new Complex[numberEquations, numberVariables + 1];
            for (int i = 0; i < realNumberEquations; ++i) {
                for (int j = 0; j < numberVariables + 1; ++j) {
                    matrix[i, j] = new Complex(args[argsI++]);
                }
            }
            for (int i = realNumberEquations; i < numberEquations; ++i) {
                for (int j = 0; j < numberVariables + 1; ++j) {
                    matrix[i, j] = new Complex();
                }
            }
            solutionPartial = new Complex[numberVariables];
            solutionGeneral = new String[numberVariables];
            solutionIndexes = Enumerable.Range(0, numberVariables).ToArray<int>();
            this.verbose = verbose;
        }

        public string[] GetSolutionGeneral()
        {
            if (NumberSolutions != NumberSolutions.MANY)
            {
                return null;
            }
            return (string[])solutionGeneral.Clone();
        }

        public Complex[] GetSolutionPartial()
        {
            if (NumberSolutions == NumberSolutions.NONE)
            {
                return null;
            }
            return (Complex[])solutionPartial.Clone();
        }

        public void Solve()
        {
            if (verbose)
            {
                Console.WriteLine("Start solving the equation.");
                Console.WriteLine("Rows manipulation:");
            }
            GausFirstStep();
            if (NumberSolutions != NumberSolutions.NONE)
            {
                GausSecondStep();
                GenerateSolutions();
                CheckThatSolutionIsSane();
            }
            PrintSolution();
        }

        public void WriteSolutionToFile(string outfile)
        {
            StreamWriter printWriter = new StreamWriter(outfile);
            PrintSolutionInternal(printWriter);
            printWriter.Close();
            if (verbose) {
                Console.WriteLine("Saved to file {0}", outfile);
            }
        }

        private void AddKRow1ToRow2(Complex k, int row1, int row2)
        {
            if (verbose)
            {
                Console.WriteLine("{0} * R{1} +R{2} -> R{2}", k, row1 + 1, row2 + 1);
            }
            for (int i = 0; i < numberVariables + 1; ++i)
            {
                Complex temp = k * matrix[row1, i];
                matrix[row2, i] += temp;
            }
        }

        private void CheckThatSolutionIsSane()
        {
            for (int i = numberVariables; i < numberEquations; ++i)
            {
                Complex sum = new Complex(0.0, 0.0);
                for (int j = 0; j < numberVariables; ++j)
                {
                    Complex temp = solutionPartial[solutionIndexes[j]] * matrix[i, solutionIndexes[j]];
                    sum += temp;
                }
                if (sum != matrix[i, numberVariables])
                {
                    NumberSolutions = NumberSolutions.NONE;
                    return;
                }
            }
        }

        private void DivideRow(int row, Complex k)
        {
            if (verbose)
            {
                Console.WriteLine("R{0} / {1} -> R{0}", row + 1, k);
            }
            for (int i = 0; i < numberVariables + 1; ++i)
            {
                matrix[row, i] /= k;
            }
        }

        private void GausFirstStep()
        {
            for (int i = 0; i < numberVariables; ++i)
            {
                if (matrix[i, i] == ZERO)
                {
                    bool foundNonZeroElement = false;
                    for (int j = i + 1; j < numberEquations; ++j)
                    {
                        if (matrix[j, i] != ZERO)
                        {
                            SwapRows(i, j);
                            foundNonZeroElement = true;
                            break;
                        }
                    }
                    if (!foundNonZeroElement)
                    {
                        for (int j = i + 1; j < numberVariables; ++j)
                        {
                            if (matrix[i, j] != ZERO)
                            {
                                SwapColumns(i, j);
                                foundNonZeroElement = true;
                                break;
                            }
                        }
                    }

                    if (!foundNonZeroElement)
                    {
                        for (int k = i + 1; !foundNonZeroElement && k < numberVariables; ++k)
                        {
                            for (int j = i + 1; j < numberEquations; ++j)
                            {
                                if (matrix[j, k] != ZERO)
                                {
                                    SwapColumns(k, i);
                                    SwapRows(j, i);
                                    foundNonZeroElement = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (!foundNonZeroElement)
                    {
                        if (matrix[i, numberVariables] == ZERO)
                        {
                            NumberSolutions = NumberSolutions.MANY;
                            continue;
                        }
                        else
                        {
                            NumberSolutions = NumberSolutions.NONE;
                            return;
                        }
                    }
                }

                if (matrix[i, i] != ONE)
                {
                    DivideRow(i, matrix[i, i]);
                }
                for (int j = i + 1; j < numberEquations && j < numberVariables; ++j)
                {
                    Complex k = MINUS_ONE * matrix[j, i];
                    if (k != ZERO)
                    {
                        AddKRow1ToRow2(k, i, j);
                    }
                }
            }
        }

        private void GausSecondStep()
        {
            for (int i = numberVariables - 1; i >= 0; --i)
            {
                for (int j = i - 1; j >= 0; --j)
                {
                    Complex k = MINUS_ONE * matrix[j, i];
                    if (k != ZERO)
                    {
                        AddKRow1ToRow2(k, i, j);
                    }
                }
            }
        }

        private void GenerateSolutions()
        {
            for (int i = 0; i < numberEquations && i < numberVariables; ++i)
            {
                solutionPartial[solutionIndexes[i]] = matrix[i, numberVariables];
                if (matrix[i, i] == ZERO)
                {
                    solutionGeneral[solutionIndexes[i]] = "x" + (solutionIndexes[i] + 1);
                }
                else
                {
                    solutionGeneral[solutionIndexes[i]] = matrix[i, numberVariables].ToString();
                    for (int j = i + 1; j < numberVariables; ++j)
                    {
                        if (matrix[i, j] == ONE)
                        {
                            solutionGeneral[solutionIndexes[i]] = solutionGeneral[solutionIndexes[i]] + " - x" +
                                    (solutionIndexes[j] + 1);
                        }
                        else if (matrix[i, j] != ZERO)
                        {
                            solutionGeneral[solutionIndexes[i]] = solutionGeneral[solutionIndexes[i]] + " - x" +
                                    (solutionIndexes[j] + 1) + " * (" + matrix[i, j] + ")";
                        }
                    }
                }
            }
        }

        private void PrintSolution()
        {
            if (verbose)
            {
                var writer = new StreamWriter(Console.OpenStandardOutput())
                {
                    AutoFlush = true
                };
                PrintSolutionInternal(writer);
            }
        }

        private void PrintSolutionInternal(StreamWriter printWriter)
        {
            switch (NumberSolutions)
            {
                case NumberSolutions.NONE:
                    printWriter.WriteLine("There are no solutions");
                    break;
                case NumberSolutions.ONE:
                    printWriter.Write("({0}", solutionPartial[0]);
                    for (int i = 1; i < solutionPartial.Length; ++i)
                    {
                        printWriter.Write(", {0}", solutionPartial[i]);
                    }
                    printWriter.WriteLine(")");
                    break;
                case NumberSolutions.MANY:
                    printWriter.Write("({0}", solutionGeneral[0]);
                    for (int i = 1; i < solutionGeneral.Length; ++i)
                    {
                        printWriter.Write(", {0}", solutionGeneral[i]);
                    }
                    printWriter.WriteLine(")");
                    break;
                default:
                    Trace.Assert(false);
                    break;
            }
            printWriter.Flush();
        }

        private void SwapColumns(int column1, int column2)
        {
            if (verbose)
            {
                Console.WriteLine("C{0} <-> C{1}", column1 + 1, column2 + 1);
            }
            for (int i = 0; i < numberEquations; ++i)
            {
                Complex temp1 = matrix[i, column1];
                matrix[i, column1] = matrix[i, column2];
                matrix[i, column2] = temp1;
            }
            int temp2 = solutionIndexes[column1];
            solutionIndexes[column1] = solutionIndexes[column2];
            solutionIndexes[column2] = temp2;
        }

        private void SwapRows(int row1, int row2)
        {
            if (verbose)
            {
                Console.WriteLine("R{0} <-> R{1}", row1 + 1, row2 + 1);
            }
            for (int i = 0; i < numberVariables + 1; ++i)
            {
                Complex temp = matrix[row1, i];
                matrix[row1, i] = matrix[row2, i];
                matrix[row2, i] = temp;
            }
        }
    }
}

