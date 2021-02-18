const createTask =()=>{
    const taskName = document.querySelector('#task-name')
    const taskDescription = document.querySelector('#task-description')
    const taskToDoList = document.querySelector('#task-to-do-list')
    const taskToDoListID = taskToDoList.value
    const body = {
        "name": taskName.value,
        "description": taskDescription.value,
    }
    fetch(`http://localhost:8393/task/create/${taskToDoListID}`, {
        method: "POST",
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify(body)
    })
        .then((response)=>{
            (response.status !== 201) ? console.error(`Status is ${response.status}`) :
                response.json()
                    .then((data)=>{
                        console.log(data)
                        taskTableHead()
                        taskTableBody(data)
                    })
        })
        .catch((error)=>console.error(`Error is ${error}`))
};

const deleteItem =(params)=>{
    fetch("http://localhost:8393/task/delete", {
        method: "DELETE",
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify(params)
    })
        .then((response)=>{
            (response.status !== 204) ? console.error(`Status is ${response.status}`) :
                console.log("Successfully deleted!")
        })
};

const deleteTaskByID =()=>{
    const taskID = document.querySelector('#task-delete-id')
    fetch(`http://localhost:8393/task/delete/${taskID}`,  {
        method: "DELETE"
    })
        .then((response)=>{
            (response.status !== 204) ? console.error(`Status is ${response.status}`) :
                console.log("Successfully deleted!")
        }).finally(taskTableEmpty)
};

const readAllTask =()=>{
    fetch("http://localhost:8393/task/read")
        .then((response)=>{
            (response.status !== 200) ? console.error(`Status is ${response.status}`) :
                response.json()
                    .then((data)=>{console.log(data)
                        taskTableHead()
                        taskTableBody(data)})
        }).catch((error)=>console.error(`Error is ${error}`))
};

const readByIDTask =()=>{
    const taskID = document.querySelector('#task-read-id').value
    fetch(`http://localhost:8393/task/read/${taskID}`)
        .then((response)=>{
            (response.status !== 200) ? console.error(`Status is ${response.status}`) :
                response.json()
                    .then((data)=>{console.log(data)
                        taskTableHead()
                        taskTableBody(data)})
        }).catch((error)=>console.error(`Error is ${error}`))
};

const updateItem =()=>{
    const taskID = document.querySelector('#task-update-id').value
    const taskNewName = document.querySelector('#task-new-name')
    const body = {
        "name": taskNewName.value
    }
    fetch(`http://localhost:8393/task/update/${taskID}`, {
        method: "PUT",
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify(body)
    }).then((response)=>{
        (response.status !== 202) ? console.error(`Status is ${response.status}`) :
            response.json()
                .then((data)=>{console.log(data)
                taskTableHead()
                taskTableBody(data)})
    }).catch((error)=>console.error(`Error is ${error}`))
};

const taskTableHead =()=>{

    const thead = document.querySelector('#table-head')
    thead.innerHTML = ""

    const tr = document.createElement('tr')

    const thNum = document.createElement('th')
    thNum.innerHTML = "#"
    const thtaskID = document.createElement('th')
    thtaskID.innerHTML = "Task ID"
    const thtaskName = document.createElement('th')
    thtaskName.innerHTML = "Task Name"
    const thtaskDescription = document.createElement('th')
    thtaskDescription.innerHTML = "Task Description"

    tr.append(thNum, thtaskID, thtaskName, thtaskDescription)
    thead.append(tr)
}

const taskTableBody =(data)=>{

    if (Array.isArray(data) === false) {
        data = [data]
    }

    const tbody = document.querySelector('#table-body')
    tbody.innerHTML = ""

    let count = 1;
    for(let element in data) {
        console.error(data[element])
        let tr = document.createElement('tr')
        let thNum = document.createElement('th')
        thNum.innerHTML = count.toString()
        let thtaskID = document.createElement('td')
        thtaskID.innerHTML = data[element]["tid"]
        let thtaskName = document.createElement('td')
        thtaskName.innerHTML = data[element]["name"]
        const thtaskDescription = document.createElement('td')
        thtaskDescription.innerHTML = data[element]["description"]
        tr.append(thNum, thtaskID, thtaskName, thtaskDescription)
        tbody.append(tr)
        count += 1
    }
}

const taskTableEmpty =()=>{
    const thead = document.querySelector('#table-head')
    const tbody = document.querySelector('#table-body')
    thead.innerHTML = ""
    tbody.innerHTML = ""
}

