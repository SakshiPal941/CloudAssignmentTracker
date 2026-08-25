# CloudAssignmentTracker

# CloudAssignmentTracker — Backend

Spring Boot backend for the Cloud Assignment Tracker project.

## Prerequisites

- Java 25
- Maven (via the included wrapper — no local Maven install needed)
- PostgreSQL running locally, with a database created for this project

## Quick Start

### 1. One-time setup

From the `backend` folder, run:

```powershell
.\setup.ps1
```

This registers a permanent Git alias called `run`, so you can start the backend with `git run` from **any** directory inside the repo — not just `backend`.

### 2. Start the app

```powershell
git run
```

This runs `mvnw.cmd spring-boot:run` under the hood and starts the Spring Boot app on `http://localhost:8080`.

## How `git run` works

`setup.ps1` registers a global Git alias:

```powershell
git config --global alias.run "!powershell.exe -NoProfile -ExecutionPolicy Bypass -File \"<path-to-setup.ps1>\" -Start"
```

Git aliases prefixed with `!` always execute with the **repo root** as the working directory, regardless of the folder you were in when you typed the command. Because of that, `setup.ps1` uses `$PSScriptRoot` (the folder the script physically lives in) rather than `Get-Location`, to reliably find and run `mvnw.cmd` in `backend/` no matter where `git run` is invoked from.

If you ever see an error like:

```
.\mvnw.cmd : The term '.\mvnw.cmd' is not recognized...
```

it usually means the alias was registered with the wrong path. Fix it by re-running `.\setup.ps1` from inside `backend`, or check the stored path with:

```powershell
git config --get alias.run
```