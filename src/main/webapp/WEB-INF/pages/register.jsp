<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="botDetect" uri="https://captcha.com/java/jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register new</title>
    <link rel="stylesheet" href="/css/bootstrap.css">

    <script src="/js/jquery-3.3.1.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script src="/js/lib.js"></script>
    <script type="text/javascript">

        function passwordsMatch() {
            var labelPassword = document.getElementById("labelPassword");
            var labelConfirm = document.getElementById("labelConfirm");

            var inputPassword = document.getElementById("inputPassword");
            var inputConfirm = document.getElementById("inputConfirm");

            var helpPasword = document.getElementById("helpPassword");
            var helpConfirm = document.getElementById("helpConfirm");

            if ((inputPassword.value !== inputConfirm.value)
                || ((inputPassword.value === ""))) {

                if (labelPassword.className.indexOf("text-success") > -1) {
                    labelPassword.className = labelPassword.className.replace("text-success", "text-danger");
                } else {
                    if (labelPassword.className.indexOf("text-danger") === -1) {
                        labelPassword.className += " text-danger";
                    }
                }

                if (inputPassword.className.indexOf("is-valid") > -1) {
                    inputPassword.className = inputPassword.className.replace("is-valid", "is-invalid");
                } else {
                    if (inputPassword.className.indexOf("is-invalid") === -1) {
                        inputPassword.className += " is-invalid";
                    }
                }

                if (helpPasword.className.indexOf("text-success") > -1) {
                    helpPasword.className = helpPasword.className.replace("text-success", "text-danger");
                } else {
                    if (helpPasword.className.indexOf("text-danger") === -1) {
                        helpPasword.className += " text-danger";
                    }
                }

                if (inputPassword.value === "") {
                    helpPasword.innerHTML = "Password cannot be blank !";
                    helpConfirm.innerHTML = "";
                } else {
                    helpPasword.innerHTML = "";
                    helpConfirm.innerHTML = "Password do not match !";
                }

            } else {

                if (inputPassword.value !== "") {

                    labelPassword.className = labelPassword.className.replace("text-danger", "text-success");
                    inputPassword.className
                        = inputPassword.className.replace("is-invalid", "is-valid");
                    helpPasword.className
                        = helpPasword.className.replace("text-danger", "text-success");
                    helpPasword.innerHTML = "OK";
                    helpConfirm.innerHTML = "OK";

                }

            }

            labelConfirm.className = labelPassword.className;
            inputConfirm.className = inputPassword.className;
            helpConfirm.className = helpPasword.className;

        }

        function submitProfile() {

            var hasEmptyFields = false;
            Array.prototype.slice.call(document.getElementsByTagName("input")).forEach(function (elem) {
                if (elem.value === "") {
                    hasEmptyFields = true;
                    return;
                }
            });

            if (hasEmptyFields) {

                var messages = new Array({type: "warning", message: "Please fill all the fields !"});
                showAndDismissAlerts(JSON.stringify(messages));
                return false;

            } else if (document.getElementById("inputPassword").value
                !== document.getElementById("inputConfirm").value) {

                var messages = new Array({type: "warning", message: "Passwords do not match !"});
                showAndDismissAlerts(JSON.stringify(messages));
                return false;

            } else {
                return true;
            }
        }

        function load() {
            showNav("register");
            showAndDismissAlerts("${messages}");
        }


    </script>
</head>
<body onload="load()">
<div class="alert-messages text-center" style="position: fixed;
        bottom: 0;
        left: 25%;
        right: 25%;
        z-index: 7000;">
</div>
<div id="nav" align="center"></div>
<br>
<div align="center">
    <div id="registerDiv" class="w-50">
        <form action="/createuser" method="post" onsubmit="return submitProfile()">

            <div class="form-group row">
                <label for="inputLogin" class="col-sm-3 col-form-label text-right">Enter your login:</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control" id="inputLogin" name="inputLogin"
                           placeholder="Login" value="${curruser.login}" maxlength="20">
                </div>
                <div class="col-sm-3">
                    <small id="helpLogin">
                    </small>
                </div>
            </div>

            <div class="form-group row">
                <label id="labelPassword" for="inputPassword"
                       class="col-sm-3 col-form-label text-right">Password:</label>
                <div class="col-sm-6">
                    <input type="password" class="form-control" id="inputPassword" name="inputPassword"
                           placeholder="Password" maxlength="20"
                           oninput="passwordsMatch()">
                </div>
                <div class="col-sm-3">
                    <small id="helpPassword">
                    </small>
                </div>
            </div>

            <div class="form-group row">
                <label id="labelConfirm" for="inputConfirm"
                       class="col-sm-3 col-form-label text-right">Retype password:</label>
                <div class="col-sm-6">
                    <input type="password" class="form-control" id="inputConfirm" name="inputConfirm"
                           placeholder="Retype password" maxlength="20"
                           oninput="passwordsMatch()">
                </div>
                <div class="col-sm-3">
                    <small id="helpConfirm">
                    </small>
                </div>
            </div>

            <div class="form-group row">
                <label for="inputUsername" class="col-sm-3 col-form-label text-right">Your
                    name:</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control" id="inputUsername" name="inputUsername"
                           placeholder="Name" value="${curruser.name}" maxlength="45">
                </div>
                <div class="col-sm-3">
                    <small id="helpUsername">
                    </small>
                </div>
            </div>
            <div class="form-group row">
                <label for="inputEmail" class="col-sm-3 col-form-label text-right">Your email:</label>
                <div class="col-sm-6">
                    <input type="email" class="form-control" id="inputEmail" name="inputEmail"
                           placeholder="Email" maxlength="45"
                           value="${curruser.emailNew}">
                </div>
                <div class="col-sm-3">
                    <small id="helpEmail">
                    </small>
                </div>
            </div>

            <div class="form-group row">

                <div class="col-sm-3">

                </div>
                <div class="col-sm-6" align="center">
                    <botDetect:captcha id="captchaReg"/>
                    <input id="captchaCodeReg" type="text" name="captchaCodeReg"/>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    <br>
                    <input type="submit" class="btn btn-primary" value="Register">
                </div>
                <div class="col-sm-3">

                </div>
            </div>

        </form>
    </div>
</div>
</body>
</html>
