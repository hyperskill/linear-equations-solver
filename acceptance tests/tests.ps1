Describe JavaAcceptanceTests { 
    It 'single solution' { 
        Remove-Item out.txt -ErrorAction SilentlyContinue
        Start-Process -FilePath "java.exe" -argumentlist '-classpath "..\out\production\project" solver.Main -in in.txt -out out.txt' -NoNewWindow -Wait
        [String[]]$lines = [System.IO.File]::ReadLines("out.txt")
        $lines.Count | Should Be 1
        $lines[0] | Should Be "(1, 2, 3)"
    }

    It 'many solutions' { 
        Remove-Item out2.txt -ErrorAction SilentlyContinue
        Start-Process -FilePath "java.exe" -argumentlist '-classpath "..\out\production\project" solver.Main -in in2.txt -out out2.txt' -NoNewWindow -Wait
        [String[]]$lines = [System.IO.File]::ReadLines("out2.txt")
        $lines.Count | Should Be 1
        $lines[0] | Should Be "(-8.3333, x2, -0.6667, 1.6667)"
    }

    It 'no solution' { 
        Remove-Item out1.txt -ErrorAction SilentlyContinue
        Start-Process -FilePath "java.exe" -argumentlist '-classpath "..\out\production\project" solver.Main -in in1.txt -out out1.txt' -NoNewWindow -Wait
        [String[]]$lines = [System.IO.File]::ReadLines("out1.txt")
        $lines.Count | Should Be 1
        $lines[0] | Should Be "There are no solutions"
    }

    It 'single solution complex numbers' { 
        Remove-Item out3.txt -ErrorAction SilentlyContinue
        Start-Process -FilePath "java.exe" -argumentlist '-classpath "..\out\production\project" solver.Main -in in3.txt -out out3.txt' -NoNewWindow -Wait
        [String[]]$lines = [System.IO.File]::ReadLines("out3.txt")
        $lines.Count | Should Be 1
        $lines[0] | Should Be "(6.7334-22.9975i, -1.7976+2.084i, 15.6994+7.396i)"
    }
} 