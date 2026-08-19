console.log("APP.JS LOADED");

fetch("http://localhost:8080/api/assignments")
    .then(response => response.json())
    .then(assignments => {

        console.log("RECEIVED:", assignments);

        const table = document.getElementById("assignment-rows");

        table.innerHTML = "";

        assignments.forEach(assignment => {

            table.innerHTML += `
                <tr>
                    <td>${assignment.title}</td> 
                    <td>${assignment.status}</td>
                    <td>Active</td>
                </tr>
            `;

        });

    })
    .catch(error => {

        console.error("FETCH FAILED:", error);

        document.getElementById("assignment-rows").innerHTML = `
            <tr>
                <td colspan="2">❌ Fetch failed</td>
            </tr>
        `;

    });