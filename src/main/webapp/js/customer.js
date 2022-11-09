function getAllCustomer(){
    fetch('./api/customers')
        .then(response => {
            if (response.status === 404){
                alert('Error: Invalid Request ' + response.status + ' Not found');
                return;
            }
            response.json().then(data => {
                console.log(data);
                let html = '<tr><th>ID</th><th>NAME</th><th>ADDRESS</th><th>SALES</th></tr>';
                data.forEach(d => {html +=
                    '<tr>' + '<td>' + d.id +
                    '</td>' + '<td>' + d.name +
                    '</td>' + '<td>' + d.address +
                    '</td>' + '<td>' + d.sales +
                    '</td>' + '</tr>'});

                document.getElementById('allCustomers').innerHTML = html;
            })
        })
}

function getCustomerById () {
    let id = document.getElementById('idSingleCustomer').value;
    if (id === ''){
        return;
    }
    console.log(id);
    fetch('./api/customers/' + id)
        .then(response => {
            if (response.status === 404) {
                alert('Error: Invalid Request ' + response.status + ' Not found');
                return;
            }
            response.json().then(data => {
                console.log(data);
                let html = '<tr><th>ID</th><th>NAME</th><th>ADDRESS</th><th>SALES</th></tr>';
                html +=
                    '<tr>' + '<td>' + data.id +
                    '</td>' + '<td>' + data.name +
                    '</td>' + '<td>' + data.address +
                    '</td>' + '<td>' + data.sales +
                    '</td>' + '</tr>';

                document.getElementById('singleCustomer').innerHTML = html;
            })
        })
}

function addCustomer () {
    let data = {
        id: document.getElementById('addCustomerID').value,
        name: document.getElementById('addCustomerName').value,
        address: document.getElementById('addCustomerAddress').value,
        sales: document.getElementById('addCustomerSales').value
    };

    console.log(data);

    fetch('./api/customers', {
        method:"POST",
        headers:{"Content-Type":"application/json"},
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.status === 409){
                alert('Invalid Request ' + response.status + ' Conflict');
                return;
            }
            alert(response.headers.get("Location"));
            response.json().then(data => {
                console.log(data);
            })
        })
}

function deleteCustomer () {
    let id = document.getElementById('deleteID').value;
    if (id === ''){
        return;
    }

    fetch('./api/customers/' + id, {method:"DELETE"})
        .then(response => {
            alert(response.status + " " + response.statusText);
        })
}

function editCustomer () {
    let data = {
        id: document.getElementById('addCustomerID').value,
        name: document.getElementById('addCustomerName').value,
        address: document.getElementById('addCustomerAddress').value,
        sales: document.getElementById('addCustomerSales').value
    };

    console.log(data);

    fetch('./api/customers/', {
        method:"PUT",
        headers:{"Content-Type":"application/json"},
        body: JSON.stringify(data)
    })
        .then(response => {
            alert(response.status + " " + response.statusText);
        })
}