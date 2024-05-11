<%@ include file="/WEB-INF/jsp/header.jsp"%>
<title>Sign-Up</title>
<link id="manifest"  rel="manifest" crossorigin="use-credentials" href="/manifest.json">
    <style>

    </style>
<body>
<div class="container">
    <h2>Sign Up</h2>
        <input type="text" placeholder="Username" id="username">
        <input type="text" placeholder="First Name" id="firstname">
        <input type="text" placeholder="Last Name" id="lastname">
        <input type="number" placeholder="Mobile No" id="mobileno">
        <input type="email" placeholder="Email ID" id="emailid">
        <div class="password-icon">
            <input type="password" id="password" placeholder="Password">
            <i class="fas fa-eye" onclick="showPassword()"></i>
        </div>
        <button onclick="signUpButton()">Sign Up</button>
        <div class="btn-container">
            <p style="margin-top: 20px;">Already have an account?</p>
            <button type="button" onclick="loginCLick()">Login</button>
        </div>

</div>

<script>

    function loginCLick(){
        window.location.href="/login";
    }

    $(document).ready(function () {
        localStorageUsernameAndTokenValidation();
    });



    function signUpButton(){
        let username = $('#username').val();
        let firstname = $('#firstname').val();
        let lastname = $('#lastname').val();
        let mobileno = $('#mobileno').val();
        let emailid = $('#emailid').val();
        let password = $('#password').val();
        if(username==undefined || username==null || username==""){
            return showValidationMessage("ERROR", "error", "Username cannot be blank");
        }
        if(mobileno==undefined || mobileno==null || mobileno==""){
            return showValidationMessage("ERROR", "error", "Mobile No. cannot be blank");
        }
        if(emailid==undefined || emailid==null || emailid==""){
            return showValidationMessage("ERROR", "error", "Email Id cannot be blank");
        }
        if(password==undefined || password==null || password==""){
            return showValidationMessage("ERROR", "error", "Password cannot be blank");
        }
        if(password.length < 8){
            return showValidationMessage("ERROR", "error", "Password must be at least 8 characters");
        }

        let data = {
            'username':username,
            'firstname': firstname,
            'lastname': lastname,
            'mobileno': mobileno,
            'emailid': emailid,
            'password': password
        };

        let domain = getDomain() + "/rest/authentication/signup";

        $.ajax({
            type: "POST",
            url: domain,
            contentType: 'application/json',
            headers: getHeaders("POST"),
            data: JSON.stringify(data),
            success: function (response) {
                //console.log(response);
                if(response.success){
                    let jwttoken = response.message;
                    localStorage.setItem("token", jwttoken);
                    localStorage.setItem("username", username);
                    // localStorage.setItem("mobileno", mobileno);
                    // localStorage.setItem("emailid", emailid);
                    // localStorage.setItem("firstname", firstname);
                    // localStorage.setItem("lastname", lastname);
                    //setTimeout(function () {
                    window.location.href="/gym/homepage";
                    //}, 2000);
                }

                }, error: function (error) {
                if(!error.responseJSON.success){
                    showValidationMessage("ERROR", "error", error.responseJSON.message);
                }

            }
        });
    }


</script>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</body>
</html>
