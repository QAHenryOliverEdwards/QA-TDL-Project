console.error("This is an error message"); 
const createToDoList =()=>{
    const toDoListName = document.querySelector('#to-do-list-name')
    const body = {
        "name": toDoListName.value
    }
    fetch("http://localhost:8393/to-do-list/create", {
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
                        console.log(`Item ${JSON.stringify(data)} has been successfully created`)
                        toDoListTableHead()
                        toDoListTableBody(data)
            })
        })
        .catch((error)=>console.error(`Error is ${error}`))
};

const deleteBasket =(params)=>{
    fetch("http://localhost:8393/to-do-list/delete", {
        method: "DELETE",
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify(params)
    })
        .then((response)=>{
            (response.status !== 204) ? console.error(`Status is ${response.status}`) :
                console.log(`Successfully deleted!`)
        })
}

const deleteToDoListByID =()=>{
    const toDoListID = document.querySelector('#to-do-list-delete-id').value
    fetch(`http://localhost:8393/to-do-list/delete/${toDoListID}`, {
        method: "DELETE"
    })
        .then((response)=>{
            (response.status !== 204) ? console.error(`Status is ${response.status}`) :
                console.log("Successfully deleted!")
        }).finally(toDoListTableEmpty)
};

const readAllToDoList =()=>{
    fetch("http://localhost:8393/to-do-list/read")
        .then((response)=>{
            (response.status !== 200) ? console.error(`Status is ${response.status}`) :
                response.json()
                    .then((data)=>{
                        console.log(data)
                        toDoListTableHead()
                        toDoListTableBody(data)
                    })
        }).catch((error)=>console.error(`Error is ${error}`))
};

const readByIDToDoList =()=>{
    const toDoListID = document.querySelector('#to-do-list-read-id').value
    fetch(`http://localhost:8393/to-do-list/read/${toDoListID}`)
        .then((response)=>{
            (response.status !== 200) ? console.error(`Status is ${response.status}`) :
                response.json()
                    .then((data)=>{console.log(data)
                    toDoListTableHead()
                    toDoListTableBody(data)})
        }).catch((error)=>console.error(`Error is ${error}`))
};

const updateToDoList =()=>{
    const toDoListID = document.querySelector('#to-do-list-update-id').value
    const toDoListNewName = document.querySelector('#to-do-list-new-name')
    const body = {
        "name": toDoListNewName.value
    }
    fetch(`http://localhost:8393/to-do-list/update/${toDoListID}`, {
        method: "PUT",
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify(body)
    }).then((response)=>{
        (response.status !== 202) ? console.error(`Status is ${response.status}`) :
            response.json()
                .then((data)=>{console.log(data)
                toDoListTableHead()
                toDoListTableBody(data)})
    }).catch((error)=>console.error(`Error is ${error}`))
};

const toDoListTableHead =()=>{

    const thead = document.querySelector('#table-head')
    thead.innerHTML = ""

    const tr = document.createElement('tr')

    const thNum = document.createElement('th')
    thNum.innerHTML = "#"
    const thToDoListID = document.createElement('th')
    thToDoListID.innerHTML = "To Do List ID"
    const thToDoListName = document.createElement('th')
    thToDoListName.innerHTML = "To Do List Name"
    const thToDoListContents = document.createElement('th')
    thToDoListContents.innerHTML = "To Do List Contents"

    tr.append(thNum, thToDoListID, thToDoListName, thToDoListContents)
    thead.append(tr)
}

const toDoListTableBody =(data)=>{

    if (Array.isArray(data) === false) {
        data = [data]
    }

    const tbody = document.querySelector('#table-body')
    tbody.innerHTML = ""

    let count = 1;
    for(let element in data) {
        let tr = document.createElement('tr')
        let thNum = document.createElement('th')
        thNum.innerHTML = count.toString()
        let thToDoListID = document.createElement('td')
        thToDoListID.innerHTML = data[element]["tdlId"]
        let thToDoListName = document.createElement('td')
        thToDoListName.innerHTML = data[element]["name"]
        let thToDoListContents = document.createElement('td')

        if (data[element]["taskList"] !== null) {
            for (let content in data[element]["taskList"]) {
                thToDoListContents.innerHTML += data[element]["taskList"][content]["name"] + ", "
            }
            thToDoListContents.innerHTML = thToDoListContents.innerHTML.slice(0, -2)
        } else {
            thToDoListContents.innerHTML = "None"
        }

        tr.append(thNum, thToDoListID, thToDoListName, thToDoListContents)
        tbody.append(tr)
        count += 1
    }
}

const toDoListTableEmpty =()=>{
    const thead = document.querySelector('#table-head')
    const tbody = document.querySelector('#table-body')
    thead.innerHTML = ""
    tbody.innerHTML = ""
}