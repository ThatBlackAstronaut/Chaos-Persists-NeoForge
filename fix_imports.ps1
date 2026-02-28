$ErrorActionPreference = "Stop"
$base = "c:\ModProjects\Chaos Persists\src\main\java\com\astryxion\chaospersists"

$importMap = @{}
$allJava = Get-ChildItem -Path $base -Recurse -Filter "*.java" -File
foreach ($f in $allJava) {
    if ($f.DirectoryName.IndexOf("mixin") -ge 0) { continue }
    $rel = $f.FullName.Substring($base.Length + 1)
    $sep = [System.IO.Path]::DirectorySeparatorChar; $parts = $rel -split [regex]::Escape($sep)
    $fname = $parts[-1]
    $className = $fname.Replace(".java","")
    if ($parts.Length -gt 1) {
        $subpkg = $parts[0..($parts.Length - 2)] -join "."
        $importMap[$className] = $subpkg
    }
}

if (-not $importMap["RedCow"]) { $importMap["RedCow"] = "entity" }
if (-not $importMap["Lavafoam"]) { $importMap["Lavafoam"] = "item" }
if (-not $importMap["MantisClaw"]) { $importMap["MantisClaw"] = "item" }
if (-not $importMap["RatSword"]) { $importMap["RatSword"] = "item" }
if (-not $importMap["MyEntityAIAvoidEntity"]) { $importMap["MyEntityAIAvoidEntity"] = "util" }
if (-not $importMap["MyEntityAIWander"]) { $importMap["MyEntityAIWander"] = "util" }
if (-not $importMap["MyEntityAIWanderALot"]) { $importMap["MyEntityAIWanderALot"] = "util" }
if (-not $importMap["MyValentineTargetSorter"]) { $importMap["MyValentineTargetSorter"] = "util" }

$count = 0
foreach ($f in $allJava) {
    if ($f.DirectoryName.IndexOf("mixin") -ge 0) { continue }
    $content = [System.IO.File]::ReadAllText($f.FullName, [System.Text.UTF8Encoding]::new($false))
    $changed = $false
    foreach ($key in $importMap.Keys) {
        $subpkg = $importMap[$key]
        $oldImport = "import com.astryxion.chaospersists." + $key + ";"
        $newImport = "import com.astryxion.chaospersists." + $subpkg + "." + $key + ";"
        if ($content.Contains($oldImport)) {
            $content = $content.Replace($oldImport, $newImport)
            $changed = $true
        }
    }
    if ($changed) {
        [System.IO.File]::WriteAllText($f.FullName, $content, [System.Text.UTF8Encoding]::new($false))
        $count++
    }
}
Write-Host "Updated imports in $count files"
