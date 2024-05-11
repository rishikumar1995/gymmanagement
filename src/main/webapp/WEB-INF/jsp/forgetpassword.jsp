<%@ include file="/WEB-INF/jsp/header.jsp"%>
<title>Forget Password</title>
<link id="manifest"  rel="manifest" crossorigin="use-credentials" href="/manifest.json">
<style>

</style>
<body>
<div class="container">
    <h2>Forget Password</h2>
    <input type="text" placeholder="Username" id="username" required>
    <input type="number" placeholder="Mobile No" id="mobileno" required>
    <input type="email" placeholder="Email ID" id="emailid" required>
    <div style="display: none" id="newpassword">

    </div>
    <button id="verify_button" onclick="verifyAccount()">Verify Account</button>
    <button style="display: none" id="save_new_password" onclick="saveNewPassword()">Save New Password</button>
    <div class="btn-container">
        <p style="margin-top: 20px;">Create New Account</p>
        <button type="button" onclick="signupClick()">Sign-Up</button>
    </div>

</div>


<script>
    function signupClick(){
        window.location.href = window.location.origin;
    }
    $(document).ready(function () {
        localStorageUsernameAndTokenValidation();
    });

    function verifyAccount(){
        let username = $('#username').val();
        let mobileno = $('#mobileno').val();
        let emailid = $('#emailid').val();
        if(username==undefined || username==null || username==""){
            return showValidationMessage("ERROR", "error", "Username cannot be blank");
        }
        if(mobileno==undefined || mobileno==null || mobileno==""){
            return showValidationMessage("ERROR", "error", "Mobile No. cannot be blank");
        }
        if(emailid==undefined || emailid==null || emailid==""){
            return showValidationMessage("ERROR", "error", "Email Id cannot be blank");
        }

        let data = {
            'username':username,
            'mobileno': mobileno,
            'emailid': emailid
        };

        let domain = getDomain() + "/rest/authentication/forgetpassword";

        $.ajax({
            type: "POST",
            url: domain,
            contentType: 'application/json',
            headers: getHeaders("POST"),
            data: JSON.stringify(data),
            success: function (response) {
                //console.log(response);
                if(response.success){
                    $('#newpassword').css('display', '');

                    let newpassworddiv = '<p>Type your New Password</p>'
                        + '<div class="password-icon">'
                        +'<input type="text" id="password" placeholder="New Password">'
                        +'<i class="fas fa-eye" onclick="showPassword()"></i>'
                        +'</div>'

                    $('#newpassword').css('display', '');
                    $('#newpassword').html(newpassworddiv);
                    $('#verify_button').css('display', 'none');
                    $('#save_new_password').css('display', '');

                }

            }, error: function (error) {
                if(!error.responseJSON.success){
                    showValidationMessage("ERROR", "error", error.responseJSON.message);
                }

            }
        });
    }

    function saveNewPassword(){
        let username = $('#username').val();
        let mobileno = $('#mobileno').val();
        let emailid = $('#emailid').val();
        let newpassword = $('#password').val();
        if(username==undefined || username==null || username==""){
            return showValidationMessage("ERROR", "error", "Username cannot be blank");
        }
        if(mobileno==undefined || mobileno==null || mobileno==""){
            return showValidationMessage("ERROR", "error", "Mobile No. cannot be blank");
        }
        if(emailid==undefined || emailid==null || emailid==""){
            return showValidationMessage("ERROR", "error", "Email Id cannot be blank");
        }
        if(newpassword==undefined || newpassword==null || newpassword==""){
            return showValidationMessage("ERROR", "error", "New Password cannot be blank");
        }
        if(newpassword.length < 8){
            return showValidationMessage("ERROR", "error", "New Password must be at least 8 characters");
        }

        let data = {
            'username':username,
            'mobileno': mobileno,
            'emailid': emailid,
            'password': newpassword
        };

        let domain = getDomain() + "/rest/authentication/forgetpassword";

        $.ajax({
            type: "POST",
            url: domain,
            contentType: 'application/json',
            headers: getHeaders("POST"),
            data: JSON.stringify(data),
            success: function (response) {
                if(response.success){
                    showValidationMessage("Success", "success", "Password Changed -- Now your can Login");
                    setTimeout(function () {
                        //window.location.reload();
                        window.location.href="/login";
                    }, 2000);

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
