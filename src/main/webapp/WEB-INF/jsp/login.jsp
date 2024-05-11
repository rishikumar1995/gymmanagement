
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<title>Log-In</title>
<link id="manifest"  rel="manifest" crossorigin="use-credentials" href="/manifest.json">
<style>

</style>

<body>
<div class="container">
    <h2>Log In</h2>
    <input type="text" placeholder="Username" id="username">
    <div class="password-icon">
        <input type="password" id="password" placeholder="Password">
        <i class="fas fa-eye" onclick="showPassword()"></i>
    </div>
    <button onclick="loginButton()">Login</button>
    <div class="btn-container">
        <a class="login-link" onclick="forgetPasswordClick()">Forget Password?</a>
        <p style="margin-top: 20px;">Create New Account</p>
        <button type="button" onclick="signupClick()">Sign-Up</button>
    </div>

</div>


<script>

    function signupClick(){
        window.location.href = window.location.origin;
    }

    function forgetPasswordClick(){
        window.location.href = "/forgetpassword";
    }
    $(document).ready(function () {
        localStorageUsernameAndTokenValidation();
    });

    function loginButton(){
        let username = $('#username').val();
        let password = $('#password').val();
        if(username==undefined || username==null || username==""){
            return showValidationMessage("ERROR", "error", "Username cannot be blank");
        }
        if(password==undefined || password==null || password==""){
            return showValidationMessage("ERROR", "error", "Password cannot be blank");
        }

        let data = {
            'username':username,
            'password': password
        };

        let domain = getDomain() + "/rest/authentication/login";

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
