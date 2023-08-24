const URL = "http://localhost:8080";
updateNavbarUsername();


function submitLogoutForm() {

    $.ajax({
      url: URL + '/customer/logout',
      type: 'PUT',
      success: function() {
        window.confirm("User Logged Out");
        $(location).attr('href',URL + '/index.html');
      },
      error: function(jqXHR, textStatus, errorThrown) {
        console.error('Error:', errorThrown);
      }
  });
};

function login(event) {
  event.preventDefault(); // Prevent the form from submitting by default

  // Retrieve form values
  var username = $('#username').val();
  var password = $('#password').val();

  // Make the API request
  $.ajax({
    url: URL + '/customer/login',
    type: 'PUT',
    data: {
      username: username,
      password: password
    },
    success: function(response) {

      window.confirm("Welcome " + username);
      $(location).attr('href',URL + '/index.html')
    },
    error: function(jqXHR, textStatus, errorThrown) {
      window.alert("Incorrect username or password");
      console.error('Error:', errorThrown);
    }
  });
}

function signup(event) {
  event.preventDefault(); // Prevent the form from submitting by default

  // Retrieve form values
  var username = $('#username').val();
  var password = $('#password').val();
  var name = $('#name').val();
  var address = $('#address').val();

  if (!username || !password || !name || !address) {
    window.alert("Please fill in all the required fields.");
    return;
  }
  // Make the API request
  $.ajax({
    url: URL + '/customer/add',
    type: 'POST',
    data: {
      username: username,
      password: password,
      name: name,
      address: address
    },
    success: function(response) {
    
      window.confirm("Welcome " + username);
      $(location).attr('href',URL + '/index.html');
    },
    error: function(jqXHR, textStatus, errorThrown) {
      window.alert("Username already exists");
      console.error('Error:', errorThrown);
    }
  });
}

  function updateNavbarUsername() {
    $.ajax({
      url: URL + '/customer/findLoggedin',
      type: 'GET',
      dataType: 'text',
      success: function(data) {
        const navbarUsernameElement = $('#navbarUsername');
        
        if (data && data.trim() !== '') {
          const customer = JSON.parse(data);
          if (customer && customer.username) {
            navbarUsernameElement.text("Welcome " + customer.username);
          } else {
            navbarUsernameElement.text("Welcome Guest");
          }
        } else {
          navbarUsernameElement.text("Welcome Guest");
        }
      },
      error: function(jqXHR, textStatus, errorThrown) {
        console.error('Error:', errorThrown);
      }
    });
  };

  function addToCart(plantid) {
    $.ajax({
      url: URL + '/customer/findLoggedin',
      type: 'GET',
      dataType: 'text',
      success: function(data) {
        
        if (data && data.trim() !== '') {
          // If the user is logged in, check the quantity of the plant
          $.ajax({
            url: URL + '/plant/getqty',
            type: 'GET',
            data: {
              plantid: plantid
            },
            success: function(plantqty) {
            
              if (plantqty > 0) {
                // If the plant's quantity is greater than 0, add it to the cart
                $.ajax({
                  url: URL + '/order/newOrder',
                  type: 'POST',
                  data: {
                    plantid: plantid,
                    qty: 1
                  },
                  success: function() {
                    window.confirm("Plant added successfully");
                  },
                  error: function(jqXHR, textStatus, errorThrown) {
                    console.error('Error:', errorThrown);
                  }
                });
              } else {
                window.confirm("This plant is out of stock");
              }
            },
            error: function(jqXHR, textStatus, errorThrown) {
              console.error('Error:', errorThrown);
            }
          });
        } else {
          window.confirm("Please sign up or log in to add a plant to your cart");
        }
      },
      error: function(jqXHR, textStatus, errorThrown) {
        console.error('Error:', errorThrown);
      }
    });
  }

function getCart() {
  $('#cartItems').empty();

  $.ajax({
    url: URL + '/order/cart',
    type: 'GET',
    success: function(data) {
      updateTable(data);
      const totalPrice = $('#totalPrice');
      getPrice(data[0].orderid)
        .then(function(price) {
          totalPrice.text("Total: " + price + "$");
        })
        .catch(function(errorThrown) {
          console.error('Error:', errorThrown);
        });
    },
    error: function(jqXHR, textStatus, errorThrown) {
      console.error('Error:', errorThrown);
    }
  });
}

function updateTable(data) {
  $('#cartItems').empty();

  data.forEach(function(item) {

    plantSale(item.plantid)
      .then(function(plant) {

      var row = $('<tr></tr>');
      row.append('<td>' + plant.name + '</td>');
      row.append('<td>' + plant.price + "$" + '</td>');
      row.append('<td>' + item.qty + '</td>');
      row.append('<td>' + (plant.price * item.qty + "$") + '</td>');

      var removeButton = $('<button class="btn btn-danger remove-btn">Remove</button>');
      removeButton.attr('value', plant.plantid); // Store the plantid value as data attribute
      row.append($('<td></td>').append(removeButton));
      $('#cartItems').append(row);

      removeButton.click(function() {
        var plantid = $(this).attr('value');
        removePlant(plantid);
      });
      })
      .catch(function(error) {
        console.error('Error:', error);
      });
  });
}


function removePlant(plantid) {
  $.ajax({
    url: URL + '/order/removeItem',
    type: 'DELETE',
    data: {
      plantid: plantid
    },
    success: function(data) {
      location.reload();
    },
    error: function(jqXHR, textStatus, errorThrown) {
      reject(errorThrown);
    }
  });
}

function plantSale(plantid) {
  return new Promise(function(resolve, reject) {
    $.ajax({
      url: URL + '/plant/getByID',
      type: 'GET',
      data: {
        plantid: plantid
      },
      success: function(data) {
        resolve(data);
      },
      error: function(jqXHR, textStatus, errorThrown) {
        reject(errorThrown);
      }
    });
  });
}

function getPrice(orderid) {
  return new Promise(function(resolve, reject) {
    $.ajax({
      url: URL + '/order/getTotalPrice',
      type: 'GET',
      data: {
        orderid: orderid
      },
      success: function(data) {
        resolve(data);
      },
      error: function(jqXHR, textStatus, errorThrown) {
        reject(errorThrown);
      }
    });
  });
}

function checkout() {
  $.ajax({
    url: URL + '/order/placeOrder',
    type: 'PUT',
    success: function(data) {
      window.confirm("Order placed, thank you!");
      $(location).attr('href',URL + '/index.html');
    },
    error: function(jqXHR, textStatus, errorThrown) {
      console.error('Error:', errorThrown);
    }
  });
}
