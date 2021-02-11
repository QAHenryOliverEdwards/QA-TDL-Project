const createBasket =(params)=>{
    fetch("http://localhost:9092/basket/create", {
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

const deleteBasket =(params)=>{
    fetch("http://localhost:9092/basket/delete", {
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

const deleteBasketByID =(params)=>{
    fetch(`http://localhost:9092/basket/delete/${params}`, {
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

const readAllBasket =()=>{
    fetch("http://localhost:9092/basket/read")
        .then((response)=>{
            (response.status !== 200) ? console.error(`Status is ${response.status}`) :
                response.json()
                    .then((data)=>console.log(data))
        }).catch((error)=>console.error(`Error is ${error}`))
};

const readByIDBasket =(params)=>{
    fetch(`http://localhost:9092/basket/read/${params}`)
        .then((response)=>{
            (response.status !== 200) ? console.error(`Status is ${response.status}`) :
                response.json()
                    .then((data)=>console.log(data))
        }).catch((error)=>console.error(`Error is ${error}`))
};

const updateBasket =(params)=>{
    fetch(`http://localhost:9092/basket/update/${params["b_id"]}`, {
        method: "PUT",
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify(params["basket"]["name"])
    }).then((response)=>{
        (response.status !== 202) ? console.error(`Status is ${response.status}`) :
            response.json()
                .then((data)=>console.log(data))
    }).catch((error)=>console.error(`Error is ${error}`))
};

