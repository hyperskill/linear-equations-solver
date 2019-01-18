// This is an independent project of an individual developer. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
using System;
namespace src
{
    public class Parameters
    {
        public readonly string Input;
        public readonly string Output;
        public readonly bool Verbose;
        public Parameters(string[] args)
        {
            bool needAssignedIn = true;
            string inTemp = "input.txt";
            bool needAssignedOut = true;
            string outTemp = "output.txt";
            bool verboseTemp = false;
            for (int i = 0; i < args.Length; ++i)
            {
                if (needAssignedIn && "-in".Equals(args[i]))
                {
                    if (i < args.Length - 1)
                    {
                        inTemp = args[i + 1];
                        needAssignedIn = false;
                        ++i;
                    }
                }
                else if (needAssignedOut && "-out".Equals(args[i]))
                {
                    if (i < args.Length - 1)
                    {
                        outTemp = args[i + 1];
                        needAssignedOut = false;
                        ++i;
                    }
                }
                else if (!verboseTemp && "-verbose".Equals(args[i]))
                {
                    verboseTemp = true;
                }
            }
            Input = inTemp;
            Output = outTemp;
            Verbose = verboseTemp;
        }
    }
}
