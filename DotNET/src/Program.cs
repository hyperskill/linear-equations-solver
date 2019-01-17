using System;
using System.IO;

namespace src
{
    class Program
    {
        public static void Main(string[] args)
        {
            try
            {
                Parameters p = new Parameters(args);
                string fileIn = File.ReadAllText(p.Input);
                Solver s = new Solver(fileIn, p.Verbose);
                s.Solve();
                s.WriteSolutionToFile(p.Output);
            }
            catch (Exception e)
            {
                Console.WriteLine("An exception occurs {0}", e.Message);
            }
        }
    }
}
