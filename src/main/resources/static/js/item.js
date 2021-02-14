const createItem =()=>{
    const itemName = document.querySelector('#item-name')
    const itemPrice = document.querySelector('#item-price')
    const itemBasket = document.querySelector('#item-basket')
    const body = {
        "name": itemName.value,
        "price": itemPrice.value,
        "basket": {
            "b_id": itemBasket.value
        }
    }
    fetch("http://localhost:9092/item/create", {
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
                        itemTableHead()
                        itemTableBody(data)
                    })
        })
        .catch((error)=>console.error(`Error is ${error}`))
};

const deleteItem =(params)=>{
    fetch("http://localhost:9092/item/delete", {
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

const deleteItemByID =()=>{
    const itemID = document.querySelector('#item-delete-id')
    fetch(`http://localhost:9092/item/delete/${itemID}`,  {
        method: "DELETE"
    })
        .then((response)=>{
            (response.status !== 204) ? console.error(`Status is ${response.status}`) :
                console.log("Successfully deleted!")
        }).finally(itemTableEmpty)
};

const readAllItem =()=>{
    fetch("http://localhost:9092/item/read")
        .then((response)=>{
            (response.status !== 200) ? console.error(`Status is ${response.status}`) :
                response.json()
                    .then((data)=>{console.log(data)
                    itemTableHead()
                    itemTableBody(data)})
        }).catch((error)=>console.error(`Error is ${error}`))
};

const readByIDItem =()=>{
    const itemID = document.querySelector('#item-delete-id').value
    fetch(`http://localhost:9092/item/read/${itemID}`)
        .then((response)=>{
            (response.status !== 200) ? console.error(`Status is ${response.status}`) :
                response.json()
                    .then((data)=>{console.log(data)
                    itemTableHead()
                    itemTableBody(data)})
        }).catch((error)=>console.error(`Error is ${error}`))
};

const updateItem =()=>{
    const itemID = document.querySelector('#item-update-id').value
    const itemNewName = document.querySelector('#item-new-name')
    const body = {
        "name": itemNewName.value
    }
    fetch(`http://localhost:9092/item/update/${itemID}`, {
        method: "PUT",
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify(body)
    }).then((response)=>{
        (response.status !== 202) ? console.error(`Status is ${response.status}`) :
            response.json()
                .then((data)=>{console.log(data)
                itemTableHead()
                itemTableBody(data)})
    }).catch((error)=>console.error(`Error is ${error}`))
};

const itemTableHead =()=>{

    const thead = document.querySelector('#table-head')
    thead.innerHTML = ""

    const tr = document.createElement('tr')

    const thNum = document.createElement('th')
    thNum.innerHTML = "#"
    const thItemID = document.createElement('th')
    thItemID.innerHTML = "Item ID"
    const thItemName = document.createElement('th')
    thItemName.innerHTML = "Item Name"
    const thItemPrice = document.createElement('th')
    thItemPrice.innerHTML = "Item Price"

    tr.append(thNum, thItemID, thItemName, thItemPrice)
    thead.append(tr)
}

const itemTableBody =(data)=>{

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
        let thItemID = document.createElement('th')
        thItemID.innerHTML = data[element]["i_id"]
        let thItemName = document.createElement('th')
        thItemName.innerHTML = data[element]["name"]
        const thItemPrice = document.createElement('th')
        thItemPrice.innerHTML = data[element]["price"]
        tr.append(thNum, thItemID, thItemName, thItemPrice)
        tbody.append(tr)
        count += 1
    }
}

const itemTableEmpty =()=>{
    const thead = document.querySelector('#table-head')
    const tbody = document.querySelector('#table-body')
    thead.innerHTML = ""
    tbody.innerHTML = ""
}