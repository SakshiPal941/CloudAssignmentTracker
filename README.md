# CloudAssignmentTracker

A three-tier web application for tracking university assignments, built for COSC349 (Cloud Computing Architecture) Assignment 1. It demonstrates a working frontend → backend → database deployment across three separate VMs, completed with Vagrant and VirtualBox.
 
The stack: a plain HTML/JS frontend served by **Nginx**, a **Spring Boot** (Java) REST backend, and a **PostgreSQL** database, each running on its own VM.

## 1. Repository Structure

- `CloudAssignmentTracker/` (the root - run all vagrant commands from here)
  - `Vagrantfile` - sets up and configures all three VMs
  - `setup-database.sql` - creates the table and adds seed data
  - `test-website.conf` - Nginx config (serves files + proxies `/api/`)
  - `tunnel_keys/` - SSH key used by backend to reach the database
    - `id_ed25519`, `id_ed25519.pub`
  - `Frontend/`
    - `index.html`, `style.css`
    - `app.js` - all UI logic, talks to the backend via `/api/`
  - `Backend/`
    - `pom.xml`, `mvnw` / `mvnw.cmd`
    - `src/main/java/.../assignment_tracker/`
      - `AssignmentTrackerApplication.java` - starts the Spring Boot app
      - `Assignment.java` - maps to the `assignments` table
      - `AssignmentRepository.java` - communicates to the database
      - `AssignmentService.java` - business logic
      - `AssignmentController.java` - the API endpoints (`/api/assignments`)
     

  **Where to make changes:**
 
| Change | File |
|---|---|
| UI changes | `Frontend/` |
| API or validation changes | `AssignmentController.java` / `AssignmentService.java` |
| Database changes | `setup-database.sql` |
| VM or networking changes | `Vagrantfile` |
| Frontend serving or `/api/` proxy changes | `test-website.conf` |
    


## 2. Architecture Overview

### 2.1 What Each VM Does
 
| VM          | IP             | Purpose                                               | Port          |
| ----------- | -------------- | ----------------------------------------------------- | ------------- |
| `webserver` | `192.168.2.11` | Nginx - serves the frontend and forwards API requests | `8080 → 80`   |
| `backend`   | `192.168.2.13` | Spring Boot API                                       | `8081 → 8080` |
| `dbserver`  | `192.168.2.12` | PostgreSQL database                                   | Not exposed   |
 
Each VM has a separate role. The database is only accessible by the backend through the private network.

### 2.2 How They Communicate
 
```text
Browser
   ↓
webserver (Nginx)
   ↓
backend (Spring Boot)
   ↓
dbserver (PostgreSQL)
```
The response then travels back the same path but in reverse to reach the browser.
 
* The browser only communicates with the `webserver`.
* Nginx serves the frontend and forwards `/api/` requests to the backend.
* The backend communicates with the database.
* The VMs communicate over the private `192.168.2.0/24` network.
* The database is not directly accessible from the browser or host machine.
The backend connects to PostgreSQL through an SSH tunnel. The `dbserver` creates an SSH key during setup, which the backend uses to securely connect to the database.

### 2.3 Tools used
 
* **Vagrant** - sets up and manages all three VMs.
* **VirtualBox** - runs the VMs.
* **Nginx** - serves the frontend and forwards API requests.
* **Maven** - builds the Spring Boot backend (into a `.jar` file).
* **PostgreSQL** - database used to store the application data.
  
---

# 3. Setup Requirements

| Tool | Version used | Notes |
|---|---|---|
| Host OS | Windows 10/11 | Should also work on macOS/Linux with VirtualBox |
| VirtualBox | 7.2.16 | Runs the three VMs |
| Vagrant | 2.4.9 | Sets up and manages the VMs |
| Guest box | `ubuntu/jammy64` (Ubuntu 22.04) | Used for all three VMs |
| Backend runtime (in-VM) | OpenJDK 17 + Maven | Installed automatically — no need to install Java locally |
| Git | Any recent version | To clone the repo |

**Before you start deployment, ensure you have the following:** 
- VirtualBox and Vagrant installed
- Virtualisation turned on in BIOS/UEFI (needed for VirtualBox on Windows)
- At least ~4 GB free RAM and a few GB free disk space
- A stable internet connection for the first setup (to download the Ubuntu box and Maven dependencies)



# 4. How To Destroy Cleanly
Destroying is very important for maintaining a clean environment and ensuring setup runs smoothly. Once finished using virtual machines, run these commands:

Destroy all virtual machines
- vagrant destroy -f

Delete the shared tunnel key from the host. Because dbserver writes the SSH tunnel keypair to a file on the host instead of VM-local storage, `vagrant destroy` never removes it. This affects reproducibility: one person could
end up reusing the same SSH key/environment across every rebuild, which can hide configuration issues that a truly fresh setup would expose. If someone else tries to replicate the work, they'd be starting from an environment that's effectively been built up over multiple sessions, rather than a genuinely clean one.
- Remove-Item -Recurse -Force .\tunnel_keys

Confirm the ports were released. If not confirmed, a zombie process will silently hold onto the port. This means upon next startup one of the servers may fail to bind its port and the whole environment fails to start. This was an issue we ran into multiple times, so is important to check.
- Get-NetTCPConnection -LocalPort 2210,8080,8081 -ErrorAction SilentlyContinue

Check for orphaned processes. This confirms vagrant destroy -f happened cleanly. Otherwise, there will be an old VM running, causing port conflicts, resource issues and lock errors. 
- Get-Process | Where-Object { $_.ProcessName -match "ruby|vagrant|VBoxHeadless" }

Prune Vagrant's global index. Sometimes a VM gets destroyed or removed, but Vagrant's own index still has a leftover ghost entry for it, since the index isn't always updated cleanly. This can cause confusion or conflicts when checking machine status later. Running this command refreshes Vagrant's index so it only reflects machines that actually still exist.
- vagrant global-status --prune

Finally as a sanity check, VirtualBox Manager should confirm 
cloudassignmenttracker_dbserver_,
cloudassignmenttracker_backend_,
cloudassignmenttracker_webserver_,
are gone from the VM list, and check File → Host Network Manager to confirm there are no orphaned 192.168.2.x host-only adapters. Running `vagrant global-status` should no longer show entries for webserver, backend, or dbserver from this project (it may still list VMs from other, unrelated Vagrant projects on the machine).



