const createItem =(params)=>{
    fetch("http://localhost:9092/item/create", {
        method: "POST",
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify(params)
    })
        .then((response)=>{
            (response.status !== 201) ? console.error(`Status is ${response.status}`) :
                response.json()
                    .then((data)=>{
                        console.log(data)
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

const deleteItemByID =(params)=>{
    fetch(`http://localhost:9092/item/delete/${params}`,  {
        method: "DELETE"
    })
        .then((response)=>{
            (response.status !== 204) ? console.error(`Status is ${response.status}`) :
                console.log("Successfully deleted!")
        })
};

const readAllItem =()=>{
    fetch("http://localhost:9092/item/read")
        .then((response)=>{
            (response.status !== 200) ? console.error(`Status is ${response.status}`) :
                response.json()
                    .then((data)=>console.log(data))
        }).catch((error)=>console.error(`Error is ${error}`))
};

const readByIDItem =(params)=>{
    fetch(`http://localhost:9092/item/read/${params}`)
        .then((response)=>{
            (response.status !== 200) ? console.error(`Status is ${response.status}`) :
                response.json()
                    .then((data)=>console.log(data))
        }).catch((error)=>console.error(`Error is ${error}`))
};

const updateItem =(params)=>{
    fetch(`http://localhost:9092/item/update/${params["i_id"]}`, {
        method: "PUT",
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify(params["item"]["name"])
    }).then((response)=>{
        (response.status !== 202) ? console.error(`Status is ${response.status}`) :
            response.json()
                .then((data)=>console.log(data))
    }).catch((error)=>console.error(`Error is ${error}`))
};