$gameDir = "W:\SteamLibrary\steamapps\common\SlayTheSpire"
$java = "C:\Program Files\Java\jdk-1.8\bin\java.exe"
$launcher = Join-Path $gameDir "mts-launcher.jar"

if (!(Test-Path $gameDir)) {
    Write-Error "游戏目录不存在：$gameDir"
    exit 1
}
if (!(Test-Path $java)) {
    Write-Error "找不到 Java 8：$java"
    exit 1
}
if (!(Test-Path $launcher)) {
    Write-Error "找不到 mts-launcher.jar：$launcher"
    exit 1
}

Push-Location $gameDir
& $java -jar $launcher
Pop-Location
