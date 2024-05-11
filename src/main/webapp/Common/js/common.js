function viewopen(str) {
    window.location = str;
}

function valuecheck(value) {
    if (value == undefined || value == null || value.length == "" || value == "null" || value == null) {
        value = "";
    }
    return value;
}

function printpagediv(printpage) {
    var headstr = "<html><head><title></title></head><body>";
    var footstr = "</body>";
    var newstr = document.all.item(printpage).innerHTML;
    var oldstr = document.body.innerHTML;
    document.body.innerHTML = headstr + newstr + footstr;
    window.print();
    document.body.innerHTML = oldstr;
    return false;
}

function showValidationMessage(messageHeader, messageType, message) {
    $.toast({
        heading: messageHeader,
        text: message,
        position: 'top-right',
        showHideTransition: 'plain',
        loaderBg: '#ff6849',
        icon: messageType,
        hideAfter: 1500

    });
}




var Base64 = {
    _keyStr: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",

    encode: function (input) {
        var output = "";
        var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
        var i = 0;
        input = Base64._utf8_encode(input);
        while (i < input.length) {
            chr1 = input.charCodeAt(i++);
            chr2 = input.charCodeAt(i++);
            chr3 = input.charCodeAt(i++);

            enc1 = chr1 >> 2;
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
            enc4 = chr3 & 63;

            if (isNaN(chr2)) {
                enc3 = enc4 = 64;
            } else if (isNaN(chr3)) {
                enc4 = 64;
            }

            output = output + this._keyStr.charAt(enc1) + this._keyStr.charAt(enc2) + this._keyStr.charAt(enc3) + this._keyStr.charAt(enc4);
        }
        return output;
    },



    decode: function (input) {
        var output = "";
        var chr1, chr2, chr3;
        var enc1, enc2, enc3, enc4;
        var i = 0;

        input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
        while (i < input.length) {
            enc1 = this._keyStr.indexOf(input.charAt(i++));
            enc2 = this._keyStr.indexOf(input.charAt(i++));
            enc3 = this._keyStr.indexOf(input.charAt(i++));
            enc4 = this._keyStr.indexOf(input.charAt(i++));

            chr1 = (enc1 << 2) | (enc2 >> 4);
            chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
            chr3 = ((enc3 & 3) << 6) | enc4;

            output = output + String.fromCharCode(chr1);

            if (enc3 != 64) {
                output = output + String.fromCharCode(chr2);
            }
            if (enc4 != 64) {
                output = output + String.fromCharCode(chr3);
            }
        }

        output = Base64._utf8_decode(output);
        return output;
    },

    _utf8_encode: function (string) {
        string = string.replace(/\r\n/g, "\n");
        var utftext = "";

        for (var n = 0; n < string.length; n++) {
            var c = string.charCodeAt(n);
            if (c < 128) {
                utftext += String.fromCharCode(c);
            }
            else if ((c > 127) && (c < 2048)) {
                utftext += String.fromCharCode((c >> 6) | 192);
                utftext += String.fromCharCode((c & 63) | 128);
            }
            else {
                utftext += String.fromCharCode((c >> 12) | 224);
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                utftext += String.fromCharCode((c & 63) | 128);
            }
        }
        return utftext;
    },

    _utf8_decode: function (utftext) {
        var string = "";
        var i = 0;
        var c = c1 = c2 = 0;
        while (i < utftext.length) {
            c = utftext.charCodeAt(i);
            if (c < 128) {
                string += String.fromCharCode(c);
                i++;
            }
            else if ((c > 191) && (c < 224)) {
                c2 = utftext.charCodeAt(i + 1);
                string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
                i += 2;
            }
            else {
                c2 = utftext.charCodeAt(i + 1);
                c3 = utftext.charCodeAt(i + 2);
                string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                i += 3;
            }
        }
        return string;
    }
}

function showPassword() {
    var passwordField = document.getElementById("password");
    var icon = document.querySelector(".password-icon i");
    if (passwordField.type === "password") {
        passwordField.type = "text";
        icon.classList.remove("fa-eye");
        icon.classList.add("fa-eye-slash");
    } else {
        passwordField.type = "password";
        icon.classList.remove("fa-eye-slash");
        icon.classList.add("fa-eye");
    }
}

function toggleMenu() {
    var slider = document.getElementById("slider");
    if (slider.style.width === "250px") {
        slider.style.width = "0";
    } else {
        slider.style.width = "250px";
    }
}

function getHeaders(method) {
    var headers = {
        "Connection-Type": "_read"
    };
    if (method == "GET" || method == "get") {
        headers["Connection-Type"] = "_read"
    }

    if (method == "POST" || method == "post" || method == "delete" || method == "DELETE" || method == "PUT" || method == "put") {
        headers["Connection-Type"] = "_write"
    }
    let jwttoken = localStorage.getItem("token");

    if(jwttoken!= null && jwttoken != ""){
        headers["Authorization"] = "Bearer " + jwttoken;
    }
    return headers;
}

function localStorageUsernameAndTokenValidation(){
    let jwttoken = localStorage.getItem("token");
    let username = localStorage.getItem("username");

    if(jwttoken == null && jwttoken ==""){
        return;
    }

    let data = {
        'username':username,
        'token': jwttoken
    };
    let domain = getDomain() + "/rest/authentication/token_authenticate";
    $.ajax({
        type: "POST",
        url: domain,
        contentType: 'application/json',
        headers: getHeaders("POST"),
        data: JSON.stringify(data),
        success: function (response) {
            if(response.success){
                window.location.href="/gym/homepage";
            }
        }, error: function (error) {
            //console.log("Welcome");
            if(error.responseJSON.message === "JWT Token Expire"){

                if(window.location.pathname != "/login" && window.location.pathname != "/forgetpassword"){
                    showValidationMessage("ERROR", "error", "Kindly Login Again");
                    setTimeout(function () {
                    //window.location.href="/login";
                    }, 1500);
                }
                /*if(window.location.pathname == "/login"){
                    showValidationMessage("ERROR", "error", "Token Expire - Login Again");
                }*/

            }

        }
    });
}

function showAdminDashboardProfile(){
    let jwttoken = localStorage.getItem("token");
    let username = localStorage.getItem("username");

    if(jwttoken == null && jwttoken ==""){
        return;
    }

    let data = {
        'username':username,
        'token': jwttoken
    };
    let domain = getDomain() + "/rest/authentication/token_authenticate";
    $.ajax({
        type: "POST",
        url: domain,
        contentType: 'application/json',
        headers: getHeaders("POST"),
        data: JSON.stringify(data),
        success: function (response) {
            if(response.success){
                window.location.href="/gym/homepage";
            }
        }, error: function (error) {
            console.log("Welcome");
            /*if(!error.responseJSON.success){
                showValidationMessage("ERROR", "error", error.responseJSON.message);
            }*/

        }
    });
}

function logoutProfile(){
/*    var urlSearchParams = new URLSearchParams(window.location.search);
    var encryptedData = urlSearchParams.get('__code__');
    var decodedData = atob(encryptedData);

    let decorderdate_split = decodedData.split("&");
    var storing_decordeddata_obj = {};
    for(var i in decorderdate_split){
        let key_value_both = decorderdate_split[i].split("=");
        let key = key_value_both[0];
        let value = key_value_both[1];
        storing_decordeddata_obj[key]=value;
    }
    var userid = storing_decordeddata_obj.userid;*/
    var userid = $('#userid').val();

    let data = {
        'userid':userid,
    };
    let domain = getDomain() + "/rest/authentication/logout";
    $.ajax({
        type: "POST",
        url: domain,
        contentType: 'application/json',
        headers: getHeaders("POST"),
        data: JSON.stringify(data),
        success: function (response) {
            if(response.success){
                localStorage.clear();
                showValidationMessage("Success","succes",response.message);
                setTimeout(function () {
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


function getDomain(){

    return "http://localhost:9001";
}

function convertArrayTCommaSeparatedString(data_array) {
    var data_string = "";
    if (data_array != null && data_array > 0) {
        for (let i = 0; i < data_array.length; i++) {
            if (i == data_array.length - 1) {
                data_string = data_string + data_array[i] + "";
            } else {
                data_string = data_string + data_array[i] + ",";
            }
        }

        return data_string;
    }
}