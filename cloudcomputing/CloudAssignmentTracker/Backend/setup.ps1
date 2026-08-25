# Use the script's own folder, not the shell's current directory —
# Git resets cwd to the repo root before running alias commands.
$currentDir = $PSScriptRoot.Replace('\', '/')

git config --global alias.run "!powershell.exe -NoProfile -ExecutionPolicy Bypass -File `"$currentDir/setup.ps1`" -Start"

if ($args -contains "-Start") {
    Set-Location $PSScriptRoot
    .\mvnw.cmd spring-boot:run
} else {
    Write-Host "🎉 Permanent git alias configured! You can now use 'git run' from any folder." -ForegroundColor Green
}