<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Gym Management System</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 0;
      padding: 0;
      background-color: #f4f4f4;
    }
    .container {
      max-width: 800px;
      margin: 20px auto;
      padding: 20px;
      background-color: #fff;
      border-radius: 5px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }
    table {
      width: 100%;
      border-collapse: collapse;
    }
    th, td {
      padding: 8px;
      border-bottom: 1px solid #ddd;
      text-align: left;
    }
    th {
      background-color: #f2f2f2;
    }
    .btn {
      padding: 8px 16px;
      border: none;
      border-radius: 5px;
      background-color: #007bff;
      color: #fff;
      cursor: pointer;
    }
    .btn:hover {
      background-color: #0056b3;
    }
    /* Modal styles */
    .modal {
      display: none;
      position: fixed;
      z-index: 1;
      left: 0;
      top: 0;
      width: 100%;
      height: 100%;
      overflow: auto;
      background-color: rgba(0,0,0,0.4);
    }
    .modal-content {
      background-color: #fefefe;
      margin: 10% auto;
      padding: 20px;
      border: 1px solid #888;
      border-radius: 5px;
      width: 60%;
    }
    .close {
      color: #aaa;
      float: right;
      font-size: 28px;
      font-weight: bold;
    }
    .close:hover,
    .close:focus {
      color: black;
      text-decoration: none;
      cursor: pointer;
    }
  </style>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>

<body>

<div class="container">
  <h2>Member List</h2>
  <button class="btn" id="btnOpenModal">Add New Member</button>
  <br><br>
  <table>
    <thead>
    <tr>
      <th>Sr. No</th>
      <th>Member Name</th>
      <th>Mobile No</th>
      <th>Trainer Name</th>
    </tr>
    </thead>
    <tbody id="memberdata">
    <!-- Table rows will be dynamically added here -->
    </tbody>
  </table>
</div>

<!-- The Modal -->
<div id="myModal" class="modal">
  <!-- Modal content -->
  <div class="modal-content">
    <span class="close">&times;</span>
    <h2>Add New Member</h2>
      <label for="memberName">Member Name:</label><br>
      <input type="text" id="memberName" name="memberName" required><br>
      <label for="mobileNo">Mobile No:</label><br>
      <input type="text" id="mobileNo" name="mobileNo" required><br>
      <label for="trainerName">Trainer Name:</label><br>
      <input type="text" id="trainerName" name="trainerName" required><br>
      <label for="email">Email:</label><br>
      <input type="email" id="email" name="email"><br><br>
      <button onclick="onSave()">Save</button>
      <button type="button" class="cancel">Cancel</button>
  </div>
</div>

<script>


  $(document).ready(function () {
    onLoad();
  });


  function onLoad(){
    let data = {
    };

    let domain = "http://localhost:9001" + "/rest/gym/member_list";

    $.ajax({
      type: "GET",
      url: domain,
      contentType: 'application/json',
      headers: getHeaders("GET"),
      data: data,
      success: function (response) {
        console.log(response);
        let res = response;
        let memdata = "";
        let count = 0;
        if(res != null && res != ""){
          for(var i in res){
            console.log(i);
            console.log(res);
            let obj = res[i];

            memdata += '<tr>'+
                    '<th>'+count++ +'</th>'+
            '<th>'+obj.username+'</th>'+
           ' <th>'+obj.mobileno+'</th>'+
            '<th>'+obj.emailid+'</th>'+
          '</tr>'
          }
          $('#memberdata').html(memdata);
        }

      }, error: function (error) {
        if(!error.responseJSON.success){
          showValidationMessage("ERROR", "error", error.responseJSON.message);
        }

      }
    });
  }

  function onSave(){
    let memberName = $('#memberName').val();
    let mobileNo = $('#mobileNo').val();
    let email = $('#email').val();
    let trainerName = $('#trainerName').val();

    let data = {
      'memberName':memberName,
      'mobileNo': mobileNo,
      'email': email,
      'trainerName': trainerName
    };

    let domain = "http://localhost:9001" + "/rest/gym/create_member";

    $.ajax({
      type: "POST",
      url: domain,
      contentType: 'application/json',
      headers: getHeaders("POST"),
      data: JSON.stringify(data),
      success: function (response) {
        //console.log(response);
        if(response.success){
          window.location.reload();
        }

      }, error: function (error) {
        if(!error.responseJSON.success){
          showValidationMessage("ERROR", "error", error.responseJSON.message);
        }

      }
    });
  }


  // Get the modal
  var modal = document.getElementById("myModal");

  // Get the button that opens the modal
  var btn = document.getElementById("btnOpenModal");

  // Get the <span> element that closes the modal
  var span = document.getElementsByClassName("close")[0];

  // When the user clicks on the button, open the modal
  btn.onclick = function() {
    modal.style.display = "block";
  }

  // When the user clicks on <span> (x), close the modal
  span.onclick = function() {
    modal.style.display = "none";
  }

  // When the user clicks anywhere outside of the modal, close it
  window.onclick = function(event) {
    if (event.target == modal) {
      modal.style.display = "none";
    }
  }

  // Get the form element
  var form = document.getElementById("addMemberForm");

  // Handle form submission
  form.addEventListener("submit", function(event) {
    event.preventDefault(); // Prevent the default form submission

    // Get the form data
    var formData = new FormData(form);

    // Extract the form values
    var memberName = formData.get("memberName");
    var mobileNo = formData.get("mobileNo");
    var trainerName = formData.get("trainerName");
    var email = formData.get("email");

    // Here you can handle the form submission, e.g., send the data to a server

    // Close the modal
    modal.style.display = "none";

    // Clear the form fields
    form.reset();
  });

  // Handle cancel button click
  var cancelButton = document.querySelector(".cancel");
  cancelButton.addEventListener("click", function() {
    modal.style.display = "none";
    form.reset(); // Clear the form fields
  });
</script>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</body>
</html>
