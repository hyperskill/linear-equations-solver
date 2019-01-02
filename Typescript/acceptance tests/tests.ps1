#!/usr/bin/env pwsh
Describe TypescriptAcceptanceTests { 
    It 'single solution' { 
        Remove-Item "$PSScriptRoot/out.txt" -ErrorAction SilentlyContinue
        Remove-Item "$PSScriptRoot/stdout.txt" -ErrorAction SilentlyContinue

        node ../src/main.js -in in.txt -out out.txt -verbose > "$PSScriptRoot/stdout.txt"

        [String[]]$lines = [System.IO.File]::ReadLines("$PSScriptRoot/out.txt")
        $lines.Count | Should Be 1
        $lines[0] | Should Be "(1, 2, 3)"

        [String[]]$stdoutLines = [System.IO.File]::ReadLines("$PSScriptRoot/stdout.txt")
        [String[]]$expectedOutput = [String[]]("Start solving the equation.",
        "Rows manipulation:", "-2 * R1 +R2 -> R2", "-3 * R1 +R3 -> R3",
        "R2 / 2 -> R2", "-3 * R2 +R3 -> R3", "R3 / -0.5 -> R3",
        "3.5 * R3 +R2 -> R2", "-2 * R3 +R1 -> R1", "-1 * R2 +R1 -> R1",
        "(1, 2, 3)", "Saved to file out.txt")
        [bool]$areEqual = @(Compare-Object $expectedOutput $stdoutLines -SyncWindow 0).Length -eq 0
        $areEqual | Should Be $true
    }

    It 'no solution' { 
        Remove-Item "$PSScriptRoot/out1.txt" -ErrorAction SilentlyContinue
        Remove-Item "$PSScriptRoot/stdout1.txt" -ErrorAction SilentlyContinue

        node ../src/main.js -in in1.txt -out out1.txt -verbose > "$PSScriptRoot/stdout1.txt"

        [String[]]$lines = [System.IO.File]::ReadLines("$PSScriptRoot/out1.txt")
        $lines.Count | Should Be 1
        $lines[0] | Should Be "There are no solutions"

        [String[]]$stdoutLines = [System.IO.File]::ReadLines("$PSScriptRoot/stdout1.txt")
        [String[]]$expectedOutput = [String[]]("Start solving the equation.",
        "Rows manipulation:", "R1 <-> R3", "-1 * R2 +R3 -> R3", "R3 / -1 -> R3",
        "-3 * R3 +R2 -> R2", "-6 * R3 +R1 -> R1", "There are no solutions", "Saved to file out1.txt")
        [bool]$areEqual = @(Compare-Object $expectedOutput $stdoutLines -SyncWindow 0).Length -eq 0
        $areEqual | Should Be $true
    }

    It 'many solutions' { 
        Remove-Item "$PSScriptRoot/out2.txt" -ErrorAction SilentlyContinue
        Remove-Item "$PSScriptRoot/stdout2.txt" -ErrorAction SilentlyContinue

        node ../src/main.js -in in2.txt -out out2.txt -verbose > "$PSScriptRoot/stdout2.txt"

        [String[]]$lines = [System.IO.File]::ReadLines("$PSScriptRoot/out2.txt")
        $lines.Count | Should Be 1
        $lines[0] | Should Be "(-8.3333, x2, -0.6667, 1.6667)"

        [String[]]$stdoutLines = [System.IO.File]::ReadLines("$PSScriptRoot/stdout2.txt")
        [String[]]$expectedOutput = [String[]]("Start solving the equation.",
        "Rows manipulation:", "C3 <-> C2", "R3 <-> R2", "-5 * R2 +R4 -> R4",
        "C4 <-> C3", "R4 <-> R3", "R3 / -15 -> R3", "-4 * R3 +R2 -> R2",
        "-5 * R3 +R1 -> R1", "(-8.3333, x2, -0.6667, 1.6667)","Saved to file out2.txt")
        [bool]$areEqual = @(Compare-Object $expectedOutput $stdoutLines -SyncWindow 0).Length -eq 0
        $areEqual | Should Be $true
    }

    It 'single solution complex numbers' { 
        Remove-Item "$PSScriptRoot/out3.txt" -ErrorAction SilentlyContinue
        Remove-Item "$PSScriptRoot/stdout3.txt" -ErrorAction SilentlyContinue

        node ../src/main.js -in in3.txt -out out3.txt -verbose > "$PSScriptRoot/stdout3.txt"

        [String[]]$lines = [System.IO.File]::ReadLines("$PSScriptRoot/out3.txt")
        $lines.Count | Should Be 1
        $lines[0] | Should Be "(6.7334-22.9975i, -1.7976+2.084i, 15.6994+7.396i)"

        [String[]]$stdoutLines = [System.IO.File]::ReadLines("$PSScriptRoot/stdout3.txt")
        [String[]]$expectedOutput = [String[]]("Start solving the equation.",
        "Rows manipulation:", "R1 / 1+2i -> R1", "1-3i * R1 +R2 -> R2", "-12.31 * R1 +R3 -> R3", 
        "R2 / 1.6+6.1i -> R2", "-10.4094+9.6778i * R2 +R3 -> R3", "R3 / -6.7848+9.7158i -> R3",
        "0.5432-0.746i * R3 +R2 -> R2", "-0.424+0.848i * R3 +R1 -> R1", "0.74-0.38i * R2 +R1 -> R1",
        "(6.7334-22.9975i, -1.7976+2.084i, 15.6994+7.396i)", "Saved to file out3.txt")
        [bool]$areEqual = @(Compare-Object $expectedOutput $stdoutLines -SyncWindow 0).Length -eq 0
        $areEqual | Should Be $true
    }

    It 'no output without -verbose' { 
        Remove-Item "$PSScriptRoot/out4.txt" -ErrorAction SilentlyContinue
        Remove-Item "$PSScriptRoot/stdout4.txt" -ErrorAction SilentlyContinue

        node ../src/main.js -in in.txt -out out4.txt > "$PSScriptRoot/stdout4.txt"

        [String[]]$stdoutLines = [System.IO.File]::ReadLines("$PSScriptRoot/stdout4.txt")
        $stdoutLines.Count | Should Be 0
    }
} 
