public class Parameters
{
    public string in {get; private set; default = "input.txt";}
    public string out {get; private set; default = "output.txt";}
    public bool verbose {get; private set; default = false;}
    public Parameters (string[] args)
    requires (args != null)
    {
        bool need_assign_in = true;
        bool need_assign_out = true;
        for (int i = 0; i < args.length; ++i)
        {
            if (need_assign_in && ("-in" == args[i]))
            {
                if (i < args.length - 1)
                {
                    in = args[i + 1];
                    need_assign_in = false;
                    ++i;
                }
            }
            else if (need_assign_out && ("-out" == args[i]))
            {
                if (i < args.length - 1)
                {
                    out = args[i + 1];
                    need_assign_out = false;
                    ++i;
                }
            }
            else if (!verbose && ("-verbose" == args[i]))
            {
                verbose = true;
            }
        }
    }
}
