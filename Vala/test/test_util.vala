FileStream generate_filestream ( string s ) {
    string tmp_filename = Environment.get_tmp_dir () + "/testXXXXXX";
    int handle_out = FileUtils.mkstemp ( tmp_filename );
    FileStream f = FileStream.fdopen ( handle_out, "w+" );
    f.puts ( s );
    f.rewind ();
    return f;
}
