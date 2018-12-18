Describe JavaAcceptanceTests { 
    It 'shoud solve linear equations1' { 
        Remove-Item out.txt -ErrorAction SilentlyContinue
        Start-Process -FilePath "java.exe" -argumentlist '-classpath "..\out\production\project" solver.Main -in in.txt -out out.txt' -NoNewWindow -Wait
        [String[]]$lines = [System.IO.File]::ReadLines("out.txt")
        $lines.Count | Should Be 1
        $lines[0] | Should Be "(1,000000, 2,000000, 3,000000)"
    }  
} 