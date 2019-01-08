namespace Util
{
    string ? read_next (FileStream in)
    {
        int c;
        StringBuilder ? ret = null;
        while (true)
        {
            c = in.getc();
            if (c == FileStream.EOF)
            {
                break;
            }
            if (((char) c).isspace ())
            {
                if (ret != null)
                {
                    break;
                }
                continue;
            }
            else
            {
                if (ret == null)
                {
                    ret = new StringBuilder ();
                }
                ((!) (ret)).append_c ((char) c);
            }
        }
        if (ret == null)
        {
            return null;
        }
        return ((!) (ret)).str;
    }

    double round (double x)
    {
        return x >= 0.0 ? Math.floor (x + 0.5) : Math.ceil (x - 0.5);
    }
}
