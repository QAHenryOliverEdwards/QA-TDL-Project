console.error("This is an error message"); 
const createBasket =()=>{
    const basketName = document.querySelector('#basket-name')
    const body = {
        "name": basketName.value
    }
    fetch("http://localhost:8393/basket/create", {
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
                        basketTableHead()
                        basketTableBody(data)
            })
        })
        .catch((error)=>console.error(`Error is ${error}`))
};

const deleteBasket =(params)=>{
    fetch("http://localhost:8393/basket/delete", {
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

const deleteBasketByID =()=>{
    const basketID = document.querySelector('#basket-delete-id').value
    fetch(`http://localhost:8393/basket/delete/${basketID}`, {
        method: "DELETE"
    })
        .then((response)=>{
            (response.status !== 204) ? console.error(`Status is ${response.status}`) :
                console.log("Successfully deleted!")
        }).finally(basketTableEmpty)
};

const readAllBasket =()=>{
    fetch("http://localhost:8393/basket/read")
        .then((response)=>{
            (response.status !== 200) ? console.error(`Status is ${response.status}`) :
                response.json()
                    .then((data)=>{
                        console.log(data)
                        basketTableHead()
                        basketTableBody(data)
                    })
        }).catch((error)=>console.error(`Error is ${error}`))
};

const readByIDBasket =()=>{
    const basketID = document.querySelector('#basket-read-id').value
    fetch(`http://localhost:8393/basket/read/${basketID}`)
        .then((response)=>{
            (response.status !== 200) ? console.error(`Status is ${response.status}`) :
                response.json()
                    .then((data)=>{console.log(data)
                    basketTableHead()
                    basketTableBody(data)})
        }).catch((error)=>console.error(`Error is ${error}`))
};

const updateBasket =()=>{
    const basketID = document.querySelector('#basket-update-id').value
    const basketNewName = document.querySelector('#basket-new-name')
    const body = {
        "name": basketNewName.value
    }
    fetch(`http://localhost:8393/basket/update/${basketID}`, {
        method: "PUT",
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify(body)
    }).then((response)=>{
        (response.status !== 202) ? console.error(`Status is ${response.status}`) :
            response.json()
                .then((data)=>{console.log(data)
                basketTableHead()
                basketTableBody(data)})
    }).catch((error)=>console.error(`Error is ${error}`))
};

const basketTableHead =()=>{

    const thead = document.querySelector('#table-head')
    thead.innerHTML = ""

    const tr = document.createElement('tr')

    const thNum = document.createElement('th')
    thNum.innerHTML = "#"
    const thBasketID = document.createElement('th')
    thBasketID.innerHTML = "Basket ID"
    const thBasketName = document.createElement('th')
    thBasketName.innerHTML = "Basket Name"
    const thBasketContents = document.createElement('th')
    thBasketContents.innerHTML = "Basket Contents"

    tr.append(thNum, thBasketID, thBasketName, thBasketContents)
    thead.append(tr)
}

const basketTableBody =(data)=>{

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
        let thBasketID = document.createElement('td')
        thBasketID.innerHTML = data[element]["bid"]
        let thBasketName = document.createElement('td')
        thBasketName.innerHTML = data[element]["name"]
        let thBasketContents = document.createElement('td')

        if (data[element]["itemList"] !== null) {
            for (let content in data[element]["itemList"]) {
                thBasketContents.innerHTML += data[element]["itemList"][content]["name"] + ", "
            }
            thBasketContents.innerHTML = thBasketContents.innerHTML.slice(0, -2)
        } else {
            thBasketContents.innerHTML = "None"
        }

        tr.append(thNum, thBasketID, thBasketName, thBasketContents)
        tbody.append(tr)
        count += 1
    }
}

const basketTableEmpty =()=>{
    const thead = document.querySelector('#table-head')
    const tbody = document.querySelector('#table-body')
    thead.innerHTML = ""
    tbody.innerHTML = ""
}